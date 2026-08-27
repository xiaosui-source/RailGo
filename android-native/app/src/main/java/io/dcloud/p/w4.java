package io.dcloud.p;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class w4 {
    private static w4 b;
    ThreadPoolExecutor a = new ThreadPoolExecutor(3, 6, 1, TimeUnit.SECONDS, new LinkedBlockingQueue());

    private w4() {
    }

    public static w4 a() {
        if (b == null) {
            synchronized (w4.class) {
                if (b == null) {
                    b = new w4();
                }
            }
        }
        return b;
    }

    public void a(Runnable runnable) {
        this.a.execute(runnable);
    }
}
