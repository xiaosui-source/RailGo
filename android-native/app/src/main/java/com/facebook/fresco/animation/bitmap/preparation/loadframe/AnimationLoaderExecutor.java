package com.facebook.fresco.animation.bitmap.preparation.loadframe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AnimationLoaderExecutor.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0006\u001a\n \b*\u0004\u0018\u00010\u00070\u0007X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\t¨\u0006\u000e"}, d2 = {"Lcom/facebook/fresco/animation/bitmap/preparation/loadframe/AnimationLoaderExecutor;", "", "<init>", "()V", "frameThreadFactory", "Ljava/util/concurrent/ThreadFactory;", "executor", "Ljava/util/concurrent/ExecutorService;", "kotlin.jvm.PlatformType", "Ljava/util/concurrent/ExecutorService;", "execute", "", "task", "Ljava/lang/Runnable;", "animated-drawable_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes.dex */
public final class AnimationLoaderExecutor {
    public static final AnimationLoaderExecutor INSTANCE = new AnimationLoaderExecutor();
    private static final ExecutorService executor;
    private static final ThreadFactory frameThreadFactory;

    private AnimationLoaderExecutor() {
    }

    static {
        ThreadFactory threadFactory = new ThreadFactory() { // from class: com.facebook.fresco.animation.bitmap.preparation.loadframe.AnimationLoaderExecutor$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return AnimationLoaderExecutor.frameThreadFactory$lambda$0(runnable);
            }
        };
        frameThreadFactory = threadFactory;
        executor = Executors.newCachedThreadPool(threadFactory);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Thread frameThreadFactory$lambda$0(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setPriority(1);
        return thread;
    }

    public final void execute(Runnable task) {
        Intrinsics.checkNotNullParameter(task, "task");
        executor.execute(task);
    }
}
