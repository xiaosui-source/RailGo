package io.dcloud.sdk.core.util;

import android.os.Handler;
import android.os.Looper;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class MainHandlerUtil {
    private static volatile Handler a = new Handler(Looper.getMainLooper());

    public static Handler getMainHandler() {
        if (a == null) {
            synchronized (MainHandlerUtil.class) {
                if (a == null) {
                    a = new Handler(Looper.getMainLooper());
                }
            }
        }
        return a;
    }
}
