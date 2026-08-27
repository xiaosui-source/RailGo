package io.dcloud.p;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.util.Deprecated_JSUtil;
import java.util.Locale;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class m3 implements SensorEventListener {
    String a;
    IWebview b;
    private SensorManager c;
    private Sensor d;
    private Sensor e;
    private Sensor f;
    private boolean k = false;
    private float[] g = new float[3];
    private float[] h = new float[3];
    private float[] i = new float[3];
    private float[] j = new float[9];

    m3(IWebview iWebview, String str) {
        this.a = str;
        this.b = iWebview;
        SensorManager sensorManager = (SensorManager) iWebview.getContext().getSystemService("sensor");
        this.c = sensorManager;
        this.d = sensorManager.getDefaultSensor(2);
        this.e = this.c.getDefaultSensor(1);
        this.f = this.c.getDefaultSensor(3);
    }

    public void a() {
        this.c.registerListener(this, this.d, 1);
        this.c.registerListener(this, this.e, 1);
        this.c.registerListener(this, this.f, 1);
    }

    public void b() {
        this.c.unregisterListener(this);
        this.d = null;
        this.e = null;
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        int type = sensorEvent.sensor.getType();
        if (type != 1) {
            if (type == 2) {
                if (this.k) {
                    return;
                }
                this.g = (float[]) sensorEvent.values.clone();
                return;
            } else {
                if (type != 3) {
                    return;
                }
                if (sensorEvent.values == null) {
                    a(0, "NO Accelerometer Message");
                    return;
                }
                this.k = true;
                a(Math.round(r0[0] * 100.0f) / 100.0f, Math.round(sensorEvent.values[1] * 100.0f) / 100.0f, Math.round(sensorEvent.values[2] * 100.0f) / 100.0f);
                return;
            }
        }
        if (this.k) {
            return;
        }
        float[] fArr = (float[]) sensorEvent.values.clone();
        this.h = fArr;
        float[] fArr2 = this.g;
        if (fArr2 == null) {
            return;
        }
        SensorManager.getRotationMatrix(this.j, null, fArr, fArr2);
        SensorManager.getOrientation(this.j, this.i);
        if (this.i == null) {
            a(0, "NO Accelerometer Message");
            return;
        }
        float degrees = (float) Math.toDegrees(r8[0]);
        if (degrees < 0.0f) {
            degrees += 360.0f;
        }
        float degrees2 = (float) Math.toDegrees(this.i[1]);
        float f = -((float) Math.toDegrees(this.i[2]));
        if (degrees == 0.0f || degrees2 == 0.0f) {
            return;
        }
        a(degrees, degrees2, f);
    }

    void a(float f, float f2, float f3) {
        Deprecated_JSUtil.excCallbackSuccess(this.b, this.a, String.format(Locale.ENGLISH, "{alpha:%f,beta:%f,gamma:%f,magneticHeading:%f,trueHeading:%f,headingAccuracy:%f}", Float.valueOf(f), Float.valueOf(-f2), Float.valueOf(-f3), Float.valueOf(f), Float.valueOf(f), Float.valueOf(0.0f)), true, true);
    }

    void a(int i, String str) {
        b();
        Deprecated_JSUtil.excCallbackError(this.b, this.a, DOMException.toJSON(i, str), true);
    }
}
