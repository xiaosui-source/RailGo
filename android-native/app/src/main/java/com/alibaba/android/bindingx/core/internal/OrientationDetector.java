package com.alibaba.android.bindingx.core.internal;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.HandlerThread;
import com.alibaba.android.bindingx.core.LogProxy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
class OrientationDetector implements SensorEventListener {
    private static OrientationDetector sSingleton;
    private final Context mAppContext;
    private boolean mDeviceOrientationIsActive;
    private boolean mDeviceOrientationIsActiveWithBackupSensors;
    private Set<Integer> mDeviceOrientationSensors;
    private float[] mDeviceRotationMatrix;
    private Handler mHandler;
    private float[] mMagneticFieldVector;
    private boolean mOrientationNotAvailable;
    private double[] mRotationAngles;
    SensorManagerProxy mSensorManagerProxy;
    private HandlerThread mThread;
    private float[] mTruncatedRotationVector;
    private static final Object sSingletonLock = new Object();
    private static final Set<Integer> DEVICE_ORIENTATION_SENSORS_A = Utils.newHashSet(15);
    private static final Set<Integer> DEVICE_ORIENTATION_SENSORS_B = Utils.newHashSet(11);
    private static final Set<Integer> DEVICE_ORIENTATION_SENSORS_C = Utils.newHashSet(1, 2);
    private final Set<Integer> mActiveSensors = new HashSet();
    private ArrayList<OnOrientationChangedListener> mListeners = new ArrayList<>();
    private final List<Set<Integer>> mOrientationSensorSets = Utils.newArrayList(DEVICE_ORIENTATION_SENSORS_A, DEVICE_ORIENTATION_SENSORS_B, DEVICE_ORIENTATION_SENSORS_C);

    interface OnOrientationChangedListener {
        void onOrientationChanged(double d, double d2, double d3);
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    private OrientationDetector(Context context) {
        this.mAppContext = context.getApplicationContext();
    }

    static OrientationDetector getInstance(Context context) {
        OrientationDetector orientationDetector;
        synchronized (sSingletonLock) {
            if (sSingleton == null) {
                sSingleton = new OrientationDetector(context);
            }
            orientationDetector = sSingleton;
        }
        return orientationDetector;
    }

    void addOrientationChangedListener(OnOrientationChangedListener onOrientationChangedListener) {
        ArrayList<OnOrientationChangedListener> arrayList = this.mListeners;
        if (arrayList == null || arrayList.contains(onOrientationChangedListener)) {
            return;
        }
        this.mListeners.add(onOrientationChangedListener);
    }

    boolean removeOrientationChangedListener(OnOrientationChangedListener onOrientationChangedListener) {
        ArrayList<OnOrientationChangedListener> arrayList = this.mListeners;
        if (arrayList == null) {
            return false;
        }
        if (onOrientationChangedListener == null) {
            arrayList.clear();
            return true;
        }
        return arrayList.remove(onOrientationChangedListener);
    }

    private boolean registerOrientationSensorsWithFallback(int i) {
        if (this.mOrientationNotAvailable) {
            return false;
        }
        if (this.mDeviceOrientationSensors != null) {
            LogProxy.d("[OrientationDetector] register sensor:" + getOrientationSensorTypeUsed());
            return registerSensors(this.mDeviceOrientationSensors, i, true);
        }
        ensureRotationStructuresAllocated();
        for (Set<Integer> set : this.mOrientationSensorSets) {
            this.mDeviceOrientationSensors = set;
            if (registerSensors(set, i, true)) {
                LogProxy.d("[OrientationDetector] register sensor:" + getOrientationSensorTypeUsed());
                return true;
            }
        }
        this.mOrientationNotAvailable = true;
        this.mDeviceOrientationSensors = null;
        this.mDeviceRotationMatrix = null;
        this.mRotationAngles = null;
        return false;
    }

    private String getOrientationSensorTypeUsed() {
        if (this.mOrientationNotAvailable) {
            return "NOT_AVAILABLE";
        }
        Set<Integer> set = this.mDeviceOrientationSensors;
        if (set == DEVICE_ORIENTATION_SENSORS_A) {
            return "GAME_ROTATION_VECTOR";
        }
        if (set == DEVICE_ORIENTATION_SENSORS_B) {
            return "ROTATION_VECTOR";
        }
        return set == DEVICE_ORIENTATION_SENSORS_C ? "ACCELEROMETER_MAGNETIC" : "NOT_AVAILABLE";
    }

    public boolean start(int i) {
        LogProxy.d("[OrientationDetector] sensor started");
        boolean zRegisterOrientationSensorsWithFallback = registerOrientationSensorsWithFallback(i);
        if (zRegisterOrientationSensorsWithFallback) {
            setEventTypeActive(true);
        }
        return zRegisterOrientationSensorsWithFallback;
    }

    void stop() {
        LogProxy.d("[OrientationDetector] sensor stopped");
        unregisterSensors(new HashSet(this.mActiveSensors));
        setEventTypeActive(false);
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        int type = sensorEvent.sensor.getType();
        float[] fArr = sensorEvent.values;
        if (type == 1) {
            if (this.mDeviceOrientationIsActiveWithBackupSensors) {
                getOrientationFromGeomagneticVectors(fArr, this.mMagneticFieldVector);
                return;
            }
            return;
        }
        if (type == 2) {
            if (this.mDeviceOrientationIsActiveWithBackupSensors) {
                if (this.mMagneticFieldVector == null) {
                    this.mMagneticFieldVector = new float[3];
                }
                float[] fArr2 = this.mMagneticFieldVector;
                System.arraycopy(fArr, 0, fArr2, 0, fArr2.length);
                return;
            }
            return;
        }
        if (type == 11) {
            if (this.mDeviceOrientationIsActive && this.mDeviceOrientationSensors == DEVICE_ORIENTATION_SENSORS_B) {
                convertRotationVectorToAngles(fArr, this.mRotationAngles);
                double[] dArr = this.mRotationAngles;
                gotOrientation(dArr[0], dArr[1], dArr[2]);
                return;
            }
            return;
        }
        if (type == 15) {
            if (this.mDeviceOrientationIsActive) {
                convertRotationVectorToAngles(fArr, this.mRotationAngles);
                double[] dArr2 = this.mRotationAngles;
                gotOrientation(dArr2[0], dArr2[1], dArr2[2]);
                return;
            }
            return;
        }
        LogProxy.e("unexpected sensor type:" + type);
    }

    private static double[] computeDeviceOrientationFromRotationMatrix(float[] fArr, double[] dArr) {
        double d;
        if (fArr.length == 9) {
            float f = fArr[8];
            if (f > 0.0f) {
                dArr[0] = Math.atan2(-fArr[1], fArr[4]);
                dArr[1] = Math.asin(fArr[7]);
                dArr[2] = Math.atan2(-fArr[6], fArr[8]);
                d = 0.0d;
            } else {
                if (f < 0.0f) {
                    d = 0.0d;
                    dArr[0] = Math.atan2(fArr[1], -fArr[4]);
                    double d2 = -Math.asin(fArr[7]);
                    dArr[1] = d2;
                    dArr[1] = d2 + (d2 < 0.0d ? 3.141592653589793d : -3.141592653589793d);
                    dArr[2] = Math.atan2(fArr[6], -fArr[8]);
                } else {
                    d = 0.0d;
                    float f2 = fArr[6];
                    if (f2 > 0.0f) {
                        dArr[0] = Math.atan2(-fArr[1], fArr[4]);
                        dArr[1] = Math.asin(fArr[7]);
                        dArr[2] = -1.5707963267948966d;
                    } else if (f2 < 0.0f) {
                        dArr[0] = Math.atan2(fArr[1], -fArr[4]);
                        double d3 = -Math.asin(fArr[7]);
                        dArr[1] = d3;
                        dArr[1] = d3 + (d3 < 0.0d ? 3.141592653589793d : -3.141592653589793d);
                        dArr[2] = -1.5707963267948966d;
                    } else {
                        dArr[0] = Math.atan2(fArr[3], fArr[0]);
                        dArr[1] = fArr[7] > 0.0f ? 1.5707963267948966d : -1.5707963267948966d;
                        dArr[2] = 0.0d;
                    }
                }
            }
            double d4 = dArr[0];
            if (d4 < d) {
                dArr[0] = d4 + 6.283185307179586d;
            }
        }
        return dArr;
    }

    private void convertRotationVectorToAngles(float[] fArr, double[] dArr) {
        if (fArr.length > 4) {
            System.arraycopy(fArr, 0, this.mTruncatedRotationVector, 0, 4);
            SensorManager.getRotationMatrixFromVector(this.mDeviceRotationMatrix, this.mTruncatedRotationVector);
        } else {
            SensorManager.getRotationMatrixFromVector(this.mDeviceRotationMatrix, fArr);
        }
        computeDeviceOrientationFromRotationMatrix(this.mDeviceRotationMatrix, dArr);
        for (int i = 0; i < 3; i++) {
            dArr[i] = Math.toDegrees(dArr[i]);
        }
    }

    private void getOrientationFromGeomagneticVectors(float[] fArr, float[] fArr2) {
        if (fArr == null || fArr2 == null || !SensorManager.getRotationMatrix(this.mDeviceRotationMatrix, null, fArr, fArr2)) {
            return;
        }
        computeDeviceOrientationFromRotationMatrix(this.mDeviceRotationMatrix, this.mRotationAngles);
        gotOrientation(Math.toDegrees(this.mRotationAngles[0]), Math.toDegrees(this.mRotationAngles[1]), Math.toDegrees(this.mRotationAngles[2]));
    }

    private SensorManagerProxy getSensorManagerProxy() {
        SensorManagerProxy sensorManagerProxy = this.mSensorManagerProxy;
        if (sensorManagerProxy != null) {
            return sensorManagerProxy;
        }
        SensorManager sensorManager = (SensorManager) this.mAppContext.getSystemService("sensor");
        if (sensorManager != null) {
            this.mSensorManagerProxy = new SensorManagerProxyImpl(sensorManager);
        }
        return this.mSensorManagerProxy;
    }

    private void setEventTypeActive(boolean z) {
        this.mDeviceOrientationIsActive = z;
        this.mDeviceOrientationIsActiveWithBackupSensors = z && this.mDeviceOrientationSensors == DEVICE_ORIENTATION_SENSORS_C;
    }

    private void ensureRotationStructuresAllocated() {
        if (this.mDeviceRotationMatrix == null) {
            this.mDeviceRotationMatrix = new float[9];
        }
        if (this.mRotationAngles == null) {
            this.mRotationAngles = new double[3];
        }
        if (this.mTruncatedRotationVector == null) {
            this.mTruncatedRotationVector = new float[4];
        }
    }

    private boolean registerSensors(Set<Integer> set, int i, boolean z) {
        HashSet<Integer> hashSet = new HashSet(set);
        hashSet.removeAll(this.mActiveSensors);
        if (hashSet.isEmpty()) {
            return true;
        }
        boolean z2 = false;
        for (Integer num : hashSet) {
            boolean zRegisterForSensorType = registerForSensorType(num.intValue(), i);
            if (!zRegisterForSensorType && z) {
                unregisterSensors(hashSet);
                return false;
            }
            if (zRegisterForSensorType) {
                this.mActiveSensors.add(num);
                z2 = true;
            }
        }
        return z2;
    }

    private void unregisterSensors(Iterable<Integer> iterable) {
        for (Integer num : iterable) {
            if (this.mActiveSensors.contains(num)) {
                getSensorManagerProxy().unregisterListener(this, num.intValue());
                this.mActiveSensors.remove(num);
            }
        }
    }

    private boolean registerForSensorType(int i, int i2) {
        SensorManagerProxy sensorManagerProxy = getSensorManagerProxy();
        if (sensorManagerProxy == null) {
            return false;
        }
        return sensorManagerProxy.registerListener(this, i, i2, getHandler());
    }

    void gotOrientation(double d, double d2, double d3) {
        ArrayList<OnOrientationChangedListener> arrayList = this.mListeners;
        if (arrayList != null) {
            try {
                Iterator<OnOrientationChangedListener> it = arrayList.iterator();
                while (it.hasNext()) {
                    double d4 = d;
                    double d5 = d2;
                    double d6 = d3;
                    it.next().onOrientationChanged(d4, d5, d6);
                    d = d4;
                    d2 = d5;
                    d3 = d6;
                }
            } catch (Throwable th) {
                LogProxy.e("[OrientationDetector] ", th);
            }
        }
    }

    private Handler getHandler() {
        if (this.mHandler == null) {
            HandlerThread handlerThread = new HandlerThread("DeviceOrientation");
            this.mThread = handlerThread;
            handlerThread.start();
            this.mHandler = new Handler(this.mThread.getLooper());
        }
        return this.mHandler;
    }
}
