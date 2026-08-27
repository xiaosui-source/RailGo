package io.dcloud.feature.barcode2.camera;

import android.hardware.Camera;
import android.os.IBinder;
import android.util.Log;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.MobilePhoneModel;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlinx.coroutines.DebugKt;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
final class FlashlightManager {
    private static final String TAG = "FlashlightManager";
    private static final Object iHardwareService;
    private static final Method setFlashEnabledMethod;

    private FlashlightManager() {
    }

    static void disableFlashlight() {
        try {
            setFlashlight(false);
        } catch (Exception unused) {
        }
    }

    static void enableFlashlight() {
        try {
            setFlashlight(true);
        } catch (Exception unused) {
        }
    }

    private static Object getHardwareService() {
        Method methodMaybeGetMethod;
        Object objInvoke;
        Class<?> clsMaybeForName;
        Method methodMaybeGetMethod2;
        Class<?> clsMaybeForName2 = maybeForName("android.os.ServiceManager");
        if (clsMaybeForName2 == null || (methodMaybeGetMethod = maybeGetMethod(clsMaybeForName2, "getService", String.class)) == null || (objInvoke = invoke(methodMaybeGetMethod, null, "hardware")) == null || (clsMaybeForName = maybeForName("android.os.IHardwareService$Stub")) == null || (methodMaybeGetMethod2 = maybeGetMethod(clsMaybeForName, "asInterface", IBinder.class)) == null) {
            return null;
        }
        return invoke(methodMaybeGetMethod2, null, objInvoke);
    }

    private static Method getSetFlashEnabledMethod(Object obj) {
        if (obj == null) {
            return null;
        }
        return maybeGetMethod(obj.getClass(), "setFlashlightEnabled", Boolean.TYPE);
    }

    private static Object invoke(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            Log.w(TAG, "Unexpected error while invoking " + method, e);
            return null;
        } catch (RuntimeException e2) {
            Log.w(TAG, "Unexpected error while invoking " + method, e2);
            return null;
        } catch (InvocationTargetException e3) {
            Log.w(TAG, "Unexpected error while invoking " + method, e3.getCause());
            return null;
        }
    }

    private static Class<?> maybeForName(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException unused) {
            return null;
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error while finding class " + str, e);
            return null;
        }
    }

    private static Method maybeGetMethod(Class<?> cls, String str, Class<?>... clsArr) {
        try {
            return cls.getMethod(str, clsArr);
        } catch (NoSuchMethodException unused) {
            return null;
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error while finding method " + str, e);
            return null;
        }
    }

    private static void setFlashlight(boolean z) {
        Camera cameraHandler = CameraManager.get().getCameraHandler();
        Camera.Parameters parameters = cameraHandler.getParameters();
        if (z) {
            parameters.setFlashMode("torch");
        } else {
            parameters.setFlashMode(DebugKt.DEBUG_PROPERTY_VALUE_OFF);
        }
        cameraHandler.setParameters(parameters);
        DeviceInfo.sBrand.equalsIgnoreCase(MobilePhoneModel.XIAOMI);
    }

    static {
        Object hardwareService = getHardwareService();
        iHardwareService = hardwareService;
        setFlashEnabledMethod = getSetFlashEnabledMethod(hardwareService);
        if (hardwareService == null) {
            Log.v("FlashlightManager", "This device does supports control of a flashlight");
        } else {
            Log.v("FlashlightManager", "This device does not support control of a flashlight");
        }
    }
}
