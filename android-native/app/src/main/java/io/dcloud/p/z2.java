package io.dcloud.p;

import android.os.Handler;
import android.os.HandlerThread;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class z2 {
    private static volatile HandlerThread a = new HandlerThread("dcloud_thread", -19);
    private static volatile Handler b;

    static {
        a.start();
        b = new Handler(a.getLooper());
    }

    public static Handler a() {
        if (a == null || !a.isAlive()) {
            synchronized (z2.class) {
                if (a == null || !a.isAlive()) {
                    a = new HandlerThread("dcloud_thread", -19);
                    a.start();
                    b = new Handler(a.getLooper());
                }
            }
        }
        return b;
    }
}
