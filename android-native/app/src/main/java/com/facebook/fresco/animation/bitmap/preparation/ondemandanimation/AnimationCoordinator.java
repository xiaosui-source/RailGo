package com.facebook.fresco.animation.bitmap.preparation.ondemandanimation;

import android.os.Handler;
import android.os.HandlerThread;
import com.facebook.fresco.animation.bitmap.preparation.ondemandanimation.FrameResult;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: AnimationCoordinator.kt */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u001b\u001a\u00020\u001cH\u0002J\b\u0010\u001d\u001a\u00020\u001cH\u0002J\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00102\u0006\u0010!\u001a\u00020\u0011H\u0002J\u0016\u0010\"\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00102\u0006\u0010#\u001a\u00020$R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0012\u001a\u00020\u00138BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/facebook/fresco/animation/bitmap/preparation/ondemandanimation/AnimationCoordinator;", "", "<init>", "()V", "FREQUENCY_PERFORMANCE_MS", "", "FREQUENCY_LOADERS_MS", "FPS_STEP_PERCENTAGE", "", "MIN_RENDERING_FPS_PERCENTAGE", "successCounter", "Ljava/util/concurrent/atomic/AtomicInteger;", "failuresCounter", "criticalCounter", "runningAnimations", "Ljava/util/concurrent/ConcurrentHashMap;", "Lcom/facebook/fresco/animation/bitmap/preparation/ondemandanimation/DynamicRenderingFps;", "", "handler", "Landroid/os/Handler;", "getHandler", "()Landroid/os/Handler;", "handler$delegate", "Lkotlin/Lazy;", "calculatePerformance", "Ljava/lang/Runnable;", "clearUnusedFrameLoaders", "schedulePerformance", "", "scheduleLoaders", "updateRenderingFps", "", "animation", "delta", "onRenderFrame", "frameResult", "Lcom/facebook/fresco/animation/bitmap/preparation/ondemandanimation/FrameResult;", "animated-drawable_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes.dex */
public final class AnimationCoordinator {
    private static final float FPS_STEP_PERCENTAGE = 0.2f;
    private static final long FREQUENCY_LOADERS_MS = 10000;
    private static final long FREQUENCY_PERFORMANCE_MS = 2000;
    public static final AnimationCoordinator INSTANCE;
    private static final float MIN_RENDERING_FPS_PERCENTAGE = 0.5f;
    private static final Runnable calculatePerformance;
    private static final Runnable clearUnusedFrameLoaders;
    private static final AtomicInteger criticalCounter;
    private static final AtomicInteger failuresCounter;

    /* renamed from: handler$delegate, reason: from kotlin metadata */
    private static final Lazy handler;
    private static final ConcurrentHashMap<DynamicRenderingFps, Integer> runningAnimations;
    private static final AtomicInteger successCounter;

    /* compiled from: AnimationCoordinator.kt */
    @Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FrameResult.FrameType.values().length];
            try {
                iArr[FrameResult.FrameType.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FrameResult.FrameType.NEAREST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FrameResult.FrameType.MISSING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private AnimationCoordinator() {
    }

    static {
        AnimationCoordinator animationCoordinator = new AnimationCoordinator();
        INSTANCE = animationCoordinator;
        successCounter = new AtomicInteger(0);
        failuresCounter = new AtomicInteger(0);
        criticalCounter = new AtomicInteger(0);
        runningAnimations = new ConcurrentHashMap<>();
        handler = LazyKt.lazy(new Function0() { // from class: com.facebook.fresco.animation.bitmap.preparation.ondemandanimation.AnimationCoordinator$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return AnimationCoordinator.handler_delegate$lambda$0();
            }
        });
        Runnable runnable = new Runnable() { // from class: com.facebook.fresco.animation.bitmap.preparation.ondemandanimation.AnimationCoordinator$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                AnimationCoordinator.calculatePerformance$lambda$3();
            }
        };
        calculatePerformance = runnable;
        Runnable runnable2 = new Runnable() { // from class: com.facebook.fresco.animation.bitmap.preparation.ondemandanimation.AnimationCoordinator$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AnimationCoordinator.clearUnusedFrameLoaders$lambda$4();
            }
        };
        clearUnusedFrameLoaders = runnable2;
        animationCoordinator.getHandler().post(runnable);
        animationCoordinator.getHandler().post(runnable2);
    }

    private final Handler getHandler() {
        return (Handler) handler.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Handler handler_delegate$lambda$0() {
        HandlerThread handlerThread = new HandlerThread("FrescoAnimationWorker");
        handlerThread.start();
        return new Handler(handlerThread.getLooper());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void calculatePerformance$lambda$3() {
        float andSet = successCounter.getAndSet(0);
        float andSet2 = failuresCounter.getAndSet(0);
        float andSet3 = criticalCounter.getAndSet(0);
        float f = andSet + andSet2 + andSet3;
        if (f > 0.0f) {
            float f2 = andSet / f;
            float f3 = andSet3 / f;
            if (andSet2 / f > 0.25f || f3 > 0.1f) {
                for (Map.Entry<DynamicRenderingFps, Integer> entry : runningAnimations.entrySet()) {
                    INSTANCE.updateRenderingFps(entry.getKey(), -entry.getValue().intValue());
                }
            } else if (f2 > 0.98f) {
                for (Map.Entry<DynamicRenderingFps, Integer> entry2 : runningAnimations.entrySet()) {
                    INSTANCE.updateRenderingFps(entry2.getKey(), entry2.getValue().intValue());
                }
            }
            runningAnimations.clear();
        }
        INSTANCE.schedulePerformance();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void clearUnusedFrameLoaders$lambda$4() {
        FrameLoaderFactory.INSTANCE.clearUnusedUntil(new Date(System.currentTimeMillis() - FREQUENCY_LOADERS_MS));
        INSTANCE.scheduleLoaders();
    }

    private final boolean schedulePerformance() {
        return getHandler().postDelayed(calculatePerformance, FREQUENCY_PERFORMANCE_MS);
    }

    private final boolean scheduleLoaders() {
        return getHandler().postDelayed(clearUnusedFrameLoaders, FREQUENCY_LOADERS_MS);
    }

    private final void updateRenderingFps(DynamicRenderingFps animation, int delta) {
        int iCoerceIn = RangesKt.coerceIn(animation.getRenderingFps() + delta, (int) RangesKt.coerceAtLeast(animation.getAnimationFps() * 0.5f, 1.0f), animation.getAnimationFps());
        if (iCoerceIn != animation.getRenderingFps()) {
            animation.setRenderingFps(iCoerceIn);
        }
    }

    public final void onRenderFrame(DynamicRenderingFps animation, FrameResult frameResult) {
        Intrinsics.checkNotNullParameter(animation, "animation");
        Intrinsics.checkNotNullParameter(frameResult, "frameResult");
        ConcurrentHashMap<DynamicRenderingFps, Integer> concurrentHashMap = runningAnimations;
        if (!concurrentHashMap.contains(animation)) {
            concurrentHashMap.put(animation, Integer.valueOf((int) (animation.getAnimationFps() * 0.2f)));
        }
        int i = WhenMappings.$EnumSwitchMapping$0[frameResult.getType().ordinal()];
        if (i == 1) {
            successCounter.incrementAndGet();
        } else if (i == 2) {
            failuresCounter.incrementAndGet();
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            criticalCounter.incrementAndGet();
        }
    }
}
