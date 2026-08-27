package com.facebook.fresco.animation.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import com.facebook.drawable.base.DrawableWithCaches;
import com.facebook.drawee.drawable.DrawableProperties;
import com.facebook.fresco.animation.backend.AnimationBackend;
import com.facebook.fresco.animation.frame.DropFramesFrameScheduler;
import com.facebook.fresco.animation.frame.FrameScheduler;
import com.taobao.weex.common.Constants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: KAnimatedDrawable2.kt */
@Metadata(d1 = {"\u0000m\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0013\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003:\u00014B\u000f\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0012\u0010\u0019\u001a\u00020\u00162\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u0018H\u0016J\b\u0010\u001d\u001a\u00020\u0016H\u0016J\b\u0010\u001e\u001a\u00020\u0016H\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u001f\u001a\u00020\u0016H\u0016J\u0010\u0010 \u001a\u00020\u00162\u0006\u0010!\u001a\u00020\"H\u0014J\b\u0010#\u001a\u00020\u0018H\u0016J\b\u0010$\u001a\u00020\u0018H\u0016J\u0006\u0010%\u001a\u00020\u0018J\u0006\u0010&\u001a\u00020\u0018J\u0006\u0010'\u001a\u00020\u0018J\u000e\u0010(\u001a\u00020\u00162\u0006\u0010)\u001a\u00020*J\u000e\u0010+\u001a\u00020\u00162\u0006\u0010,\u001a\u00020*J\u0010\u0010-\u001a\u00020\u00162\b\u0010.\u001a\u0004\u0018\u00010\u000bJ\u0010\u0010/\u001a\u00020\u00162\b\u0010.\u001a\u0004\u0018\u00010\rJ\u0010\u00100\u001a\u00020\u00162\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005J\u0010\u00101\u001a\u00020\u00162\u0006\u00102\u001a\u000203H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0014¨\u00065"}, d2 = {"Lcom/facebook/fresco/animation/drawable/KAnimatedDrawable2;", "Landroid/graphics/drawable/Drawable;", "Landroid/graphics/drawable/Animatable;", "Lcom/facebook/drawable/base/DrawableWithCaches;", "animationBackend", "Lcom/facebook/fresco/animation/backend/AnimationBackend;", "<init>", "(Lcom/facebook/fresco/animation/backend/AnimationBackend;)V", "animatedFrameScheduler", "Lcom/facebook/fresco/animation/drawable/AnimationFrameScheduler;", "animationListener", "Lcom/facebook/fresco/animation/drawable/AnimationListener;", "drawListener", "Lcom/facebook/fresco/animation/drawable/KAnimatedDrawable2$DrawListener;", "drawableProperties", "Lcom/facebook/drawee/drawable/DrawableProperties;", "isRunning", "", "invalidateRunnable", "com/facebook/fresco/animation/drawable/KAnimatedDrawable2$invalidateRunnable$1", "Lcom/facebook/fresco/animation/drawable/KAnimatedDrawable2$invalidateRunnable$1;", "setAlpha", "", "alpha", "", "setColorFilter", "colorFilter", "Landroid/graphics/ColorFilter;", "getOpacity", "start", Constants.Value.STOP, "dropCaches", "onBoundsChange", "bounds", "Landroid/graphics/Rect;", "getIntrinsicWidth", "getIntrinsicHeight", "loopDurationMs", "getFrameCount", "loopCount", "setFrameSchedulingDelayMs", "delayMs", "", "setFrameSchedulingOffsetMs", "offsetMs", "setAnimationListener", "listener", "setDrawListener", "setAnimationBackend", "draw", "canvas", "Landroid/graphics/Canvas;", "DrawListener", "animated-drawable_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes.dex */
public final class KAnimatedDrawable2 extends Drawable implements Animatable, DrawableWithCaches {
    private final AnimationFrameScheduler animatedFrameScheduler;
    private AnimationBackend animationBackend;
    private AnimationListener animationListener;
    private DrawListener drawListener;
    private final DrawableProperties drawableProperties;
    private final KAnimatedDrawable2$invalidateRunnable$1 invalidateRunnable;
    private volatile boolean isRunning;

    /* compiled from: KAnimatedDrawable2.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001Jh\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u000eH&¨\u0006\u0015"}, d2 = {"Lcom/facebook/fresco/animation/drawable/KAnimatedDrawable2$DrawListener;", "", "onDraw", "", "animatedDrawable", "Lcom/facebook/fresco/animation/drawable/KAnimatedDrawable2;", "frameScheduler", "Lcom/facebook/fresco/animation/frame/FrameScheduler;", "frameNumberToDraw", "", "frameDrawn", "", "isAnimationRunning", "animationStartTimeMs", "", "animationTimeMs", "lastFrameAnimationTimeMs", "actualRenderTimeStartMs", "actualRenderTimeEndMs", "startRenderTimeForNextFrameMs", "scheduledRenderTimeForNextFrameMs", "animated-drawable_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
    public interface DrawListener {
        void onDraw(KAnimatedDrawable2 animatedDrawable, FrameScheduler frameScheduler, int frameNumberToDraw, boolean frameDrawn, boolean isAnimationRunning, long animationStartTimeMs, long animationTimeMs, long lastFrameAnimationTimeMs, long actualRenderTimeStartMs, long actualRenderTimeEndMs, long startRenderTimeForNextFrameMs, long scheduledRenderTimeForNextFrameMs);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    /* JADX WARN: Type inference failed for: r3v5, types: [com.facebook.fresco.animation.drawable.KAnimatedDrawable2$invalidateRunnable$1] */
    public KAnimatedDrawable2(AnimationBackend animationBackend) {
        Intrinsics.checkNotNullParameter(animationBackend, "animationBackend");
        this.animationBackend = animationBackend;
        this.animatedFrameScheduler = new AnimationFrameScheduler(new DropFramesFrameScheduler(this.animationBackend));
        this.animationListener = new BaseAnimationListener();
        DrawableProperties drawableProperties = new DrawableProperties();
        drawableProperties.applyTo(this);
        this.drawableProperties = drawableProperties;
        this.invalidateRunnable = new Runnable() { // from class: com.facebook.fresco.animation.drawable.KAnimatedDrawable2$invalidateRunnable$1
            @Override // java.lang.Runnable
            public void run() {
                this.this$0.unscheduleSelf(this);
                this.this$0.invalidateSelf();
            }
        };
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        this.drawableProperties.setAlpha(alpha);
        this.animationBackend.setAlpha(alpha);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.drawableProperties.setColorFilter(colorFilter);
        this.animationBackend.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        if (this.animationBackend.getFrameCount() <= 0) {
            return;
        }
        this.animatedFrameScheduler.start();
        this.animationListener.onAnimationStart(this);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        this.animatedFrameScheduler.stop();
        this.animationListener.onAnimationStop(this);
        unscheduleSelf(this.invalidateRunnable);
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.animatedFrameScheduler.getRunning();
    }

    @Override // com.facebook.drawable.base.DrawableWithCaches
    public void dropCaches() {
        this.animationBackend.clear();
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect bounds) {
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        this.animationBackend.setBounds(bounds);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.animationBackend.getIntrinsicWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.animationBackend.getIntrinsicHeight();
    }

    public final int loopDurationMs() {
        return this.animationBackend.getLoopDurationMs();
    }

    public final int getFrameCount() {
        return this.animationBackend.getFrameCount();
    }

    public final int loopCount() {
        return this.animationBackend.getLoopCount();
    }

    public final void setFrameSchedulingDelayMs(long delayMs) {
        this.animatedFrameScheduler.setFrameSchedulingDelayMs(delayMs);
    }

    public final void setFrameSchedulingOffsetMs(long offsetMs) {
        this.animatedFrameScheduler.setFrameSchedulingOffsetMs(offsetMs);
    }

    public final void setAnimationListener(AnimationListener listener) {
        if (listener == null) {
            listener = this.animationListener;
        }
        this.animationListener = listener;
    }

    public final void setDrawListener(DrawListener listener) {
        this.drawListener = listener;
    }

    public final void setAnimationBackend(AnimationBackend animationBackend) {
        if (animationBackend == null) {
            return;
        }
        stop();
        animationBackend.setBounds(getBounds());
        this.drawableProperties.applyTo(this);
        this.animationBackend = animationBackend;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        int iFrameToDraw = this.animatedFrameScheduler.frameToDraw();
        if (iFrameToDraw == -1) {
            iFrameToDraw = this.animationBackend.getFrameCount() - 1;
            this.animatedFrameScheduler.setRunning(false);
            this.animationListener.onAnimationStop(this);
        } else if (iFrameToDraw == 0 && this.animatedFrameScheduler.shouldRepeatAnimation()) {
            this.animationListener.onAnimationRepeat(this);
        }
        KAnimatedDrawable2 kAnimatedDrawable2 = this;
        if (this.animationBackend.drawFrame(kAnimatedDrawable2, canvas, iFrameToDraw)) {
            this.animationListener.onAnimationFrame(kAnimatedDrawable2, iFrameToDraw);
            this.animatedFrameScheduler.setLastDrawnFrameNumber(iFrameToDraw);
        } else {
            this.animatedFrameScheduler.onFrameDropped();
        }
        long jNextRenderTime = this.animatedFrameScheduler.nextRenderTime();
        if (jNextRenderTime != -1) {
            scheduleSelf(this.invalidateRunnable, jNextRenderTime);
        } else {
            this.animationListener.onAnimationStop(kAnimatedDrawable2);
            this.animatedFrameScheduler.setRunning(false);
        }
    }
}
