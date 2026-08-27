package io.dcloud.uts;

import android.os.Handler;
import android.os.Looper;
import com.taobao.weex.bridge.WXBridgeManager;
import io.dcloud.common.DHInterface.IApp;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;

/* compiled from: UTSTimer.kt */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0004\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a\u0018\u0010\u001a\u001a\u00020\u001b2\u0010\u0010\u001c\u001a\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u001d\u001a \u0010\u001a\u001a\u00020\u001b2\u0010\u0010\u001c\u001a\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u001d2\u0006\u0010\u001e\u001a\u00020\u001b\u001a \u0010\u001f\u001a\u00020\u001b2\u0010\u0010\u001c\u001a\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u001d2\u0006\u0010\u001e\u001a\u00020\u001b\u001a(\u0010\u001f\u001a\u00020\u001b2\u0010\u0010\u001c\u001a\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u001d2\u0006\u0010 \u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u001b\u001a\u000e\u0010!\u001a\u00020\u00022\u0006\u0010\"\u001a\u00020\u001b\u001a\u000e\u0010#\u001a\u00020\u00022\u0006\u0010\"\u001a\u00020\u001b\"\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\"Z\u0010\t\u001aB\u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b\u0012\f\u0012\n \f*\u0004\u0018\u00010\r0\r \f* \u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b\u0012\f\u0012\n \f*\u0004\u0018\u00010\r0\r\u0018\u00010\u000e0\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012\"Z\u0010\u0013\u001aB\u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b\u0012\f\u0012\n \f*\u0004\u0018\u00010\r0\r \f* \u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b\u0012\f\u0012\n \f*\u0004\u0018\u00010\r0\r\u0018\u00010\u000e0\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0010\"\u0004\b\u0015\u0010\u0012\"\u0011\u0010\u0016\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019*\u0016\u0010\u0000\"\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0001¨\u0006$"}, d2 = {"TimerCallback", "Lkotlin/Function0;", "", "taskDynamicId", "Ljava/util/concurrent/atomic/AtomicInteger;", "getTaskDynamicId", "()Ljava/util/concurrent/atomic/AtomicInteger;", "setTaskDynamicId", "(Ljava/util/concurrent/atomic/AtomicInteger;)V", "intervalTaskMap", "", "", "kotlin.jvm.PlatformType", "Lio/dcloud/uts/TaskFuture;", "", "getIntervalTaskMap", "()Ljava/util/Map;", "setIntervalTaskMap", "(Ljava/util/Map;)V", "timeoutTaskMap", "getTimeoutTaskMap", "setTimeoutTaskMap", "executorService", "Ljava/util/concurrent/ScheduledExecutorService;", "getExecutorService", "()Ljava/util/concurrent/ScheduledExecutorService;", "setTimeout", "", WXBridgeManager.METHOD_CALLBACK, "Lio/dcloud/uts/TimerCallback;", "timeout", "setInterval", IApp.ConfigProperty.CONFIG_DELAY, "clearTimeout", "taskId", "clearInterval", "utsplugin_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UTSTimerKt {
    private static final ScheduledExecutorService executorService;
    private static AtomicInteger taskDynamicId = new AtomicInteger(0);
    private static java.util.Map<Integer, TaskFuture> intervalTaskMap = Collections.synchronizedMap(new LinkedHashMap());
    private static java.util.Map<Integer, TaskFuture> timeoutTaskMap = Collections.synchronizedMap(new LinkedHashMap());

    static {
        ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool = Executors.newScheduledThreadPool(10);
        Intrinsics.checkNotNullExpressionValue(scheduledExecutorServiceNewScheduledThreadPool, "newScheduledThreadPool(...)");
        executorService = scheduledExecutorServiceNewScheduledThreadPool;
    }

    public static final AtomicInteger getTaskDynamicId() {
        return taskDynamicId;
    }

    public static final void setTaskDynamicId(AtomicInteger atomicInteger) {
        Intrinsics.checkNotNullParameter(atomicInteger, "<set-?>");
        taskDynamicId = atomicInteger;
    }

    public static final java.util.Map<Integer, TaskFuture> getIntervalTaskMap() {
        return intervalTaskMap;
    }

    public static final void setIntervalTaskMap(java.util.Map<Integer, TaskFuture> map) {
        intervalTaskMap = map;
    }

    public static final java.util.Map<Integer, TaskFuture> getTimeoutTaskMap() {
        return timeoutTaskMap;
    }

    public static final void setTimeoutTaskMap(java.util.Map<Integer, TaskFuture> map) {
        timeoutTaskMap = map;
    }

    public static final ScheduledExecutorService getExecutorService() {
        return executorService;
    }

    public static final synchronized Number setTimeout(Function0<Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        return setTimeout(callback, (Number) 0);
    }

    public static final synchronized Number setTimeout(final Function0<Unit> callback, Number timeout) {
        final int iIncrementAndGet;
        TaskFuture taskFuture;
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(timeout, "timeout");
        iIncrementAndGet = taskDynamicId.incrementAndGet();
        if (Looper.myLooper() == null) {
            ScheduledFuture<?> scheduledFutureSchedule = executorService.schedule(new Runnable() { // from class: io.dcloud.uts.UTSTimerKt$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    UTSTimerKt.setTimeout$lambda$0(callback, iIncrementAndGet);
                }
            }, timeout.longValue(), TimeUnit.MILLISECONDS);
            Intrinsics.checkNotNull(scheduledFutureSchedule);
            taskFuture = new TaskFuture(scheduledFutureSchedule);
        } else {
            Looper looperMyLooper = Looper.myLooper();
            Intrinsics.checkNotNull(looperMyLooper);
            Handler handler = new Handler(looperMyLooper);
            UTSRunnable uTSRunnable = new UTSRunnable() { // from class: io.dcloud.uts.UTSTimerKt$setTimeout$runnable$1
                @Override // io.dcloud.uts.UTSRunnable
                public void doSth() {
                    callback.invoke();
                    UTSTimerKt.clearTimeout(Integer.valueOf(iIncrementAndGet));
                }
            };
            handler.postDelayed(uTSRunnable, timeout.longValue());
            taskFuture = new TaskFuture(handler, uTSRunnable);
        }
        java.util.Map<Integer, TaskFuture> timeoutTaskMap2 = timeoutTaskMap;
        Intrinsics.checkNotNullExpressionValue(timeoutTaskMap2, "timeoutTaskMap");
        timeoutTaskMap2.put(Integer.valueOf(iIncrementAndGet), taskFuture);
        return Integer.valueOf(iIncrementAndGet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setTimeout$lambda$0(Function0 function0, int i) {
        function0.invoke();
        clearTimeout(Integer.valueOf(i));
    }

    public static final synchronized Number setInterval(Function0<Unit> callback, Number timeout) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(timeout, "timeout");
        return setInterval(callback, timeout, timeout);
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [T, io.dcloud.uts.UTSTimerKt$$ExternalSyntheticLambda3] */
    public static final synchronized Number setInterval(final Function0<Unit> callback, Number delay, Number timeout) {
        TaskFuture taskFuture;
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(delay, "delay");
        Intrinsics.checkNotNullParameter(timeout, "timeout");
        if (NumberKt.compareTo(delay, (Number) 0) <= 0) {
            delay = (Number) 1;
        }
        if (NumberKt.compareTo(timeout, (Number) 0) <= 0) {
            timeout = (Number) 1;
        }
        if (Looper.myLooper() == null) {
            ScheduledFuture<?> scheduledFutureScheduleAtFixedRate = executorService.scheduleAtFixedRate(new Runnable() { // from class: io.dcloud.uts.UTSTimerKt$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    callback.invoke();
                }
            }, delay.longValue(), timeout.longValue(), TimeUnit.MILLISECONDS);
            Intrinsics.checkNotNull(scheduledFutureScheduleAtFixedRate);
            taskFuture = new TaskFuture(scheduledFutureScheduleAtFixedRate);
        } else {
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            objectRef.element = new Function0() { // from class: io.dcloud.uts.UTSTimerKt$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function0
                public final java.lang.Object invoke() {
                    return UTSTimerKt.setInterval$lambda$2(callback);
                }
            };
            Looper looperMyLooper = Looper.myLooper();
            Intrinsics.checkNotNull(looperMyLooper);
            final Handler handler = new Handler(looperMyLooper);
            ScheduledFuture<?> scheduledFutureScheduleAtFixedRate2 = executorService.scheduleAtFixedRate(new Runnable() { // from class: io.dcloud.uts.UTSTimerKt$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    UTSTimerKt.setInterval$lambda$4(handler, objectRef);
                }
            }, delay.longValue(), timeout.longValue(), TimeUnit.MILLISECONDS);
            Intrinsics.checkNotNull(scheduledFutureScheduleAtFixedRate2);
            taskFuture = new TaskFuture(scheduledFutureScheduleAtFixedRate2);
        }
        taskDynamicId.getAndIncrement();
        java.util.Map<Integer, TaskFuture> intervalTaskMap2 = intervalTaskMap;
        Intrinsics.checkNotNullExpressionValue(intervalTaskMap2, "intervalTaskMap");
        intervalTaskMap2.put(Integer.valueOf(taskDynamicId.get()), taskFuture);
        return Integer.valueOf(taskDynamicId.get());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setInterval$lambda$2(Function0 function0) {
        function0.invoke();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setInterval$lambda$4(Handler handler, Ref.ObjectRef objectRef) {
        final Function0 function0 = (Function0) objectRef.element;
        handler.post(new Runnable() { // from class: io.dcloud.uts.UTSTimerKt$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                function0.invoke();
            }
        });
    }

    public static final synchronized void clearTimeout(Number taskId) {
        Intrinsics.checkNotNullParameter(taskId, "taskId");
        if (timeoutTaskMap.get(Integer.valueOf(taskId.intValue())) == null) {
            return;
        }
        TaskFuture taskFuture = timeoutTaskMap.get(Integer.valueOf(taskId.intValue()));
        Intrinsics.checkNotNull(taskFuture);
        taskFuture.cancel();
        timeoutTaskMap.remove(Integer.valueOf(taskId.intValue()));
    }

    public static final synchronized void clearInterval(Number taskId) {
        Intrinsics.checkNotNullParameter(taskId, "taskId");
        if (intervalTaskMap.get(Integer.valueOf(taskId.intValue())) == null) {
            return;
        }
        TaskFuture taskFuture = intervalTaskMap.get(Integer.valueOf(taskId.intValue()));
        Intrinsics.checkNotNull(taskFuture);
        taskFuture.cancel();
        intervalTaskMap.remove(Integer.valueOf(taskId.intValue()));
    }
}
