package io.dcloud.common.util;

import android.os.Handler;
import android.os.Looper;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class ThrottleUtil {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable readyToRun;

    public void throttlePost(final Runnable runnable, long j) {
        Runnable runnable2 = this.readyToRun;
        if (runnable2 != null) {
            this.handler.removeCallbacks(runnable2);
        }
        Runnable runnable3 = new Runnable() { // from class: io.dcloud.common.util.ThrottleUtil.1
            @Override // java.lang.Runnable
            public void run() {
                runnable.run();
            }
        };
        this.readyToRun = runnable3;
        this.handler.postDelayed(runnable3, j);
    }
}
