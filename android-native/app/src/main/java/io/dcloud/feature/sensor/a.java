package io.dcloud.feature.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.util.Deprecated_JSUtil;
import java.util.Locale;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class a implements SensorEventListener {
    String a;
    IWebview b;
    b c;

    a(b bVar, IWebview iWebview, String str) {
        this.c = bVar;
        this.a = str;
        this.b = iWebview;
    }

    void a(float f, float f2, float f3) {
        Deprecated_JSUtil.excCallbackSuccess(this.b, this.a, String.format(Locale.ENGLISH, "{x:%f,y:%f,z:%f}", Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3)), true, true);
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == 1) {
            float[] fArr = sensorEvent.values;
            if (fArr != null) {
                a(fArr[0], fArr[1], fArr[2]);
            } else {
                a(0, "NO Accelerometer Message");
            }
            Logger.d("accelerometer", "accelerometer-   x= " + fArr[0] + ";y=" + fArr[1] + ";z=" + fArr[2] + " at " + this.b);
        }
    }

    void a(int i, String str) {
        this.c.a(this.b);
        Deprecated_JSUtil.excCallbackError(this.b, this.a, DOMException.toJSON(i, str), true);
    }
}
