package com.facebook.fresco.animation.bitmap.preparation;

import android.graphics.Bitmap;
import com.facebook.common.references.CloseableReference;
import com.facebook.fresco.animation.backend.AnimationBackend;
import com.facebook.fresco.animation.backend.AnimationInformation;
import com.facebook.fresco.animation.bitmap.BitmapFrameCache;
import com.facebook.fresco.animation.bitmap.BitmapFrameRenderer;
import com.facebook.fresco.animation.bitmap.preparation.BitmapFramePreparationStrategy;
import com.facebook.fresco.animation.bitmap.preparation.ondemandanimation.AnimationCoordinator;
import com.facebook.fresco.animation.bitmap.preparation.ondemandanimation.DynamicRenderingFps;
import com.facebook.fresco.animation.bitmap.preparation.ondemandanimation.FrameLoader;
import com.facebook.fresco.animation.bitmap.preparation.ondemandanimation.FrameLoaderFactory;
import com.facebook.fresco.animation.bitmap.preparation.ondemandanimation.FrameResult;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: FrameLoaderStrategy.kt */
@Metadata(d1 = {"\u0000c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0019\u0018\u00002\u00020\u0001B1\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0004\b\f\u0010\rJ(\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u00020\u00102\u000e\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010 H\u0017J(\u0010!\u001a\n\u0012\u0004\u0012\u00020#\u0018\u00010\"2\u0006\u0010$\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u00020\u0010H\u0017J\b\u0010%\u001a\u00020\u001cH\u0016J\b\u0010&\u001a\u00020\u001cH\u0016J\u0018\u0010'\u001a\u00020(2\u0006\u0010\u001d\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u00020\u0010H\u0002J\f\u0010)\u001a\u00020\u0010*\u00020\u0005H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0012\u001a\u0004\u0018\u00010\u00138BX\u0082\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001a¨\u0006*"}, d2 = {"Lcom/facebook/fresco/animation/bitmap/preparation/FrameLoaderStrategy;", "Lcom/facebook/fresco/animation/bitmap/preparation/BitmapFramePreparationStrategy;", "source", "", "animationInformation", "Lcom/facebook/fresco/animation/backend/AnimationInformation;", "bitmapFrameRenderer", "Lcom/facebook/fresco/animation/bitmap/BitmapFrameRenderer;", "frameLoaderFactory", "Lcom/facebook/fresco/animation/bitmap/preparation/ondemandanimation/FrameLoaderFactory;", "downscaleFrameToDrawableDimensions", "", "<init>", "(Ljava/lang/String;Lcom/facebook/fresco/animation/backend/AnimationInformation;Lcom/facebook/fresco/animation/bitmap/BitmapFrameRenderer;Lcom/facebook/fresco/animation/bitmap/preparation/ondemandanimation/FrameLoaderFactory;Z)V", "cacheKey", "animationWidth", "", "animationHeight", "frameLoader", "Lcom/facebook/fresco/animation/bitmap/preparation/ondemandanimation/FrameLoader;", "getFrameLoader", "()Lcom/facebook/fresco/animation/bitmap/preparation/ondemandanimation/FrameLoader;", "maxAnimationFps", "currentFps", "dynamicFpsRender", "com/facebook/fresco/animation/bitmap/preparation/FrameLoaderStrategy$dynamicFpsRender$1", "Lcom/facebook/fresco/animation/bitmap/preparation/FrameLoaderStrategy$dynamicFpsRender$1;", "prepareFrames", "", "canvasWidth", "canvasHeight", "onAnimationLoaded", "Lkotlin/Function0;", "getBitmapFrame", "Lcom/facebook/common/references/CloseableReference;", "Landroid/graphics/Bitmap;", "frameNumber", "onStop", "clearFrames", "calculateFrameSize", "Lcom/facebook/fresco/animation/bitmap/preparation/FrameSize;", "fps", "animated-drawable_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes.dex */
public final class FrameLoaderStrategy implements BitmapFramePreparationStrategy {
    private final int animationHeight;
    private final AnimationInformation animationInformation;
    private final int animationWidth;
    private final BitmapFrameRenderer bitmapFrameRenderer;
    private final String cacheKey;
    private int currentFps;
    private final boolean downscaleFrameToDrawableDimensions;
    private final FrameLoaderStrategy$dynamicFpsRender$1 dynamicFpsRender;
    private FrameLoader frameLoader;
    private final FrameLoaderFactory frameLoaderFactory;
    private final int maxAnimationFps;

    /* JADX WARN: Type inference failed for: r2v5, types: [com.facebook.fresco.animation.bitmap.preparation.FrameLoaderStrategy$dynamicFpsRender$1] */
    public FrameLoaderStrategy(String str, AnimationInformation animationInformation, BitmapFrameRenderer bitmapFrameRenderer, FrameLoaderFactory frameLoaderFactory, boolean z) {
        Intrinsics.checkNotNullParameter(animationInformation, "animationInformation");
        Intrinsics.checkNotNullParameter(bitmapFrameRenderer, "bitmapFrameRenderer");
        Intrinsics.checkNotNullParameter(frameLoaderFactory, "frameLoaderFactory");
        this.animationInformation = animationInformation;
        this.bitmapFrameRenderer = bitmapFrameRenderer;
        this.frameLoaderFactory = frameLoaderFactory;
        this.downscaleFrameToDrawableDimensions = z;
        this.cacheKey = str == null ? String.valueOf(hashCode()) : str;
        this.animationWidth = animationInformation.width();
        this.animationHeight = animationInformation.height();
        int iFps = fps(animationInformation);
        this.maxAnimationFps = iFps;
        this.currentFps = iFps;
        this.dynamicFpsRender = new DynamicRenderingFps() { // from class: com.facebook.fresco.animation.bitmap.preparation.FrameLoaderStrategy$dynamicFpsRender$1
            private final int animationFps;

            {
                this.animationFps = this.this$0.maxAnimationFps;
            }

            @Override // com.facebook.fresco.animation.bitmap.preparation.ondemandanimation.DynamicRenderingFps
            public int getAnimationFps() {
                return this.animationFps;
            }

            @Override // com.facebook.fresco.animation.bitmap.preparation.ondemandanimation.DynamicRenderingFps
            public int getRenderingFps() {
                return this.this$0.currentFps;
            }

            @Override // com.facebook.fresco.animation.bitmap.preparation.ondemandanimation.DynamicRenderingFps
            public void setRenderingFps(int renderingFps) {
                if (renderingFps != this.this$0.currentFps) {
                    FrameLoaderStrategy frameLoaderStrategy = this.this$0;
                    frameLoaderStrategy.currentFps = RangesKt.coerceIn(renderingFps, 1, frameLoaderStrategy.maxAnimationFps);
                    FrameLoader frameLoader = this.this$0.getFrameLoader();
                    if (frameLoader != null) {
                        frameLoader.compressToFps(this.this$0.currentFps);
                    }
                }
            }
        };
    }

    @Override // com.facebook.fresco.animation.bitmap.preparation.BitmapFramePreparationStrategy
    public void prepareFrames(BitmapFramePreparer bitmapFramePreparer, BitmapFrameCache bitmapFrameCache, AnimationBackend animationBackend, int i, Function0<Unit> function0) {
        BitmapFramePreparationStrategy.DefaultImpls.prepareFrames(this, bitmapFramePreparer, bitmapFrameCache, animationBackend, i, function0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FrameLoader getFrameLoader() {
        if (this.frameLoader == null) {
            this.frameLoader = this.frameLoaderFactory.createBufferLoader(this.cacheKey, this.bitmapFrameRenderer, this.animationInformation);
        }
        return this.frameLoader;
    }

    @Override // com.facebook.fresco.animation.bitmap.preparation.BitmapFramePreparationStrategy
    public void prepareFrames(int canvasWidth, int canvasHeight, Function0<Unit> onAnimationLoaded) {
        if (canvasWidth <= 0 || canvasHeight <= 0 || this.animationWidth <= 0 || this.animationHeight <= 0) {
            return;
        }
        FrameSize frameSizeCalculateFrameSize = calculateFrameSize(canvasWidth, canvasHeight);
        FrameLoader frameLoader = getFrameLoader();
        if (frameLoader != null) {
            int width = frameSizeCalculateFrameSize.getWidth();
            int width2 = frameSizeCalculateFrameSize.getWidth();
            if (onAnimationLoaded == null) {
                onAnimationLoaded = new Function0() { // from class: com.facebook.fresco.animation.bitmap.preparation.FrameLoaderStrategy$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return Unit.INSTANCE;
                    }
                };
            }
            frameLoader.prepareFrames(width, width2, onAnimationLoaded);
        }
    }

    @Override // com.facebook.fresco.animation.bitmap.preparation.BitmapFramePreparationStrategy
    public CloseableReference<Bitmap> getBitmapFrame(int frameNumber, int canvasWidth, int canvasHeight) {
        FrameSize frameSizeCalculateFrameSize = calculateFrameSize(canvasWidth, canvasHeight);
        FrameLoader frameLoader = getFrameLoader();
        FrameResult frame = frameLoader != null ? frameLoader.getFrame(frameNumber, frameSizeCalculateFrameSize.getWidth(), frameSizeCalculateFrameSize.getHeight()) : null;
        if (frame != null) {
            AnimationCoordinator.INSTANCE.onRenderFrame(this.dynamicFpsRender, frame);
        }
        if (frame != null) {
            return frame.getBitmapRef();
        }
        return null;
    }

    @Override // com.facebook.fresco.animation.bitmap.preparation.BitmapFramePreparationStrategy
    public void onStop() {
        FrameLoader frameLoader = getFrameLoader();
        if (frameLoader != null) {
            frameLoader.onStop();
        }
        clearFrames();
    }

    @Override // com.facebook.fresco.animation.bitmap.preparation.BitmapFramePreparationStrategy
    public void clearFrames() {
        FrameLoader frameLoader = getFrameLoader();
        if (frameLoader != null) {
            FrameLoaderFactory.INSTANCE.saveUnusedFrame(this.cacheKey, frameLoader);
        }
        this.frameLoader = null;
    }

    private final FrameSize calculateFrameSize(int canvasWidth, int canvasHeight) {
        if (!this.downscaleFrameToDrawableDimensions) {
            return new FrameSize(this.animationWidth, this.animationHeight);
        }
        int iCoerceAtMost = this.animationWidth;
        int iCoerceAtMost2 = this.animationHeight;
        if (canvasWidth < iCoerceAtMost || canvasHeight < iCoerceAtMost2) {
            double d = iCoerceAtMost / iCoerceAtMost2;
            if (canvasHeight > canvasWidth) {
                iCoerceAtMost2 = RangesKt.coerceAtMost(canvasHeight, iCoerceAtMost2);
                iCoerceAtMost = (int) (iCoerceAtMost2 * d);
            } else {
                iCoerceAtMost = RangesKt.coerceAtMost(canvasWidth, iCoerceAtMost);
                iCoerceAtMost2 = (int) (iCoerceAtMost / d);
            }
        }
        return new FrameSize(iCoerceAtMost, iCoerceAtMost2);
    }

    private final int fps(AnimationInformation animationInformation) {
        return (int) RangesKt.coerceAtLeast(TimeUnit.SECONDS.toMillis(1L) / (animationInformation.getLoopDurationMs() / animationInformation.getFrameCount()), 1L);
    }
}
