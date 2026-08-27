package com.taobao.weex.tracing;

import com.taobao.weex.utils.WXLogUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class Stopwatch {
    private static final ThreadLocal<Stopwatch> sThreadLocal = new ThreadLocal<>();
    private List<ProcessEvent> splits = new ArrayList();
    private long startMillis;
    private long startNanos;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class ProcessEvent {
        public double duration;
        public String fname;
        public long startMillis;
    }

    public static List<ProcessEvent> getProcessEvents() {
        if (!WXTracing.isAvailable()) {
            return Collections.EMPTY_LIST;
        }
        tack();
        ThreadLocal<Stopwatch> threadLocal = sThreadLocal;
        List<ProcessEvent> list = threadLocal.get().splits;
        threadLocal.get().splits = new ArrayList();
        return list;
    }

    public static long lastTickStamp() {
        if (!WXTracing.isAvailable()) {
            return -1L;
        }
        try {
            return sThreadLocal.get().startMillis;
        } catch (Throwable th) {
            th.printStackTrace();
            return -1L;
        }
    }

    public static double millisUntilNow(long j) {
        return nanosToMillis(System.nanoTime() - j);
    }

    public static double nanosToMillis(long j) {
        return j / 1000000.0d;
    }

    private static void prepare() {
        ThreadLocal<Stopwatch> threadLocal = sThreadLocal;
        if (threadLocal.get() == null) {
            threadLocal.set(new Stopwatch());
        }
    }

    public static void split(String str) {
        if (WXTracing.isAvailable()) {
            try {
                ProcessEvent processEvent = new ProcessEvent();
                ThreadLocal<Stopwatch> threadLocal = sThreadLocal;
                long j = threadLocal.get().startMillis;
                double dTackAndTick = tackAndTick();
                processEvent.fname = str;
                processEvent.duration = dTackAndTick;
                processEvent.startMillis = j;
                threadLocal.get().splits.add(processEvent);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public static double tack() {
        if (!WXTracing.isAvailable()) {
            return -1.0d;
        }
        try {
            ThreadLocal<Stopwatch> threadLocal = sThreadLocal;
            long j = threadLocal.get().startNanos;
            if (j == 0) {
                WXLogUtils.w("Stopwatch", "Should call Stopwatch.tick() before Stopwatch.tack() called");
            }
            long jNanoTime = System.nanoTime() - j;
            threadLocal.get().startNanos = 0L;
            return nanosToMillis(jNanoTime);
        } catch (Throwable th) {
            th.printStackTrace();
            return -1.0d;
        }
    }

    public static double tackAndTick() {
        double dTack = tack();
        tick();
        return dTack;
    }

    public static void tick() {
        if (WXTracing.isAvailable()) {
            try {
                prepare();
                ThreadLocal<Stopwatch> threadLocal = sThreadLocal;
                if (threadLocal.get().startNanos != 0) {
                    WXLogUtils.w("Stopwatch", "Stopwatch is not reset");
                }
                threadLocal.get().startNanos = System.nanoTime();
                threadLocal.get().startMillis = System.currentTimeMillis();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }
}
