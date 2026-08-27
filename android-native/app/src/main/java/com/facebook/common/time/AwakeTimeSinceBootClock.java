package com.facebook.common.time;

import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class AwakeTimeSinceBootClock implements MonotonicNanoClock {
    private static final AwakeTimeSinceBootClock INSTANCE = new AwakeTimeSinceBootClock();

    @Override // com.facebook.common.time.MonotonicClock
    public /* synthetic */ long now() {
        return TimeUnit.NANOSECONDS.toMillis(nowNanos());
    }

    private AwakeTimeSinceBootClock() {
    }

    public static AwakeTimeSinceBootClock get() {
        return INSTANCE;
    }

    @Override // com.facebook.common.time.MonotonicClock
    public long nowNanos() {
        return System.nanoTime();
    }
}
