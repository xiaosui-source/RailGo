package com.facebook.fresco.animation.drawable.animator;

import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import com.facebook.fresco.animation.drawable.AnimatedDrawable2;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AnimatedDrawable2ValueAnimatorHelper.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001a\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0007J \u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rH\u0007J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\nH\u0007¨\u0006\u0011"}, d2 = {"Lcom/facebook/fresco/animation/drawable/animator/AnimatedDrawable2ValueAnimatorHelper;", "", "<init>", "()V", "createValueAnimator", "Landroid/animation/ValueAnimator;", "animatedDrawable", "Lcom/facebook/fresco/animation/drawable/AnimatedDrawable2;", "maxDurationMs", "", "Landroid/graphics/drawable/Drawable;", "loopCount", "loopDurationMs", "", "createAnimatorUpdateListener", "Landroid/animation/ValueAnimator$AnimatorUpdateListener;", "drawable", "animated-drawable_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes.dex */
public final class AnimatedDrawable2ValueAnimatorHelper {
    public static final AnimatedDrawable2ValueAnimatorHelper INSTANCE = new AnimatedDrawable2ValueAnimatorHelper();

    private AnimatedDrawable2ValueAnimatorHelper() {
    }

    @JvmStatic
    public static final ValueAnimator createValueAnimator(AnimatedDrawable2 animatedDrawable, int maxDurationMs) {
        Intrinsics.checkNotNullParameter(animatedDrawable, "animatedDrawable");
        ValueAnimator valueAnimatorCreateValueAnimator = createValueAnimator(animatedDrawable, animatedDrawable.getLoopCount(), animatedDrawable.getLoopDurationMs());
        if (valueAnimatorCreateValueAnimator == null) {
            return null;
        }
        valueAnimatorCreateValueAnimator.setRepeatCount((int) Math.max(maxDurationMs / animatedDrawable.getLoopDurationMs(), 1L));
        return valueAnimatorCreateValueAnimator;
    }

    @JvmStatic
    public static final ValueAnimator createValueAnimator(Drawable animatedDrawable, int loopCount, long loopDurationMs) {
        Intrinsics.checkNotNullParameter(animatedDrawable, "animatedDrawable");
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(0, (int) loopDurationMs);
        valueAnimator.setDuration(loopDurationMs);
        if (loopCount == 0) {
            loopCount = -1;
        }
        valueAnimator.setRepeatCount(loopCount);
        valueAnimator.setRepeatMode(1);
        valueAnimator.setInterpolator(null);
        valueAnimator.addUpdateListener(createAnimatorUpdateListener(animatedDrawable));
        return valueAnimator;
    }

    @JvmStatic
    public static final ValueAnimator.AnimatorUpdateListener createAnimatorUpdateListener(final Drawable drawable) {
        Intrinsics.checkNotNullParameter(drawable, "drawable");
        return new ValueAnimator.AnimatorUpdateListener() { // from class: com.facebook.fresco.animation.drawable.animator.AnimatedDrawable2ValueAnimatorHelper$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                AnimatedDrawable2ValueAnimatorHelper.createAnimatorUpdateListener$lambda$0(drawable, valueAnimator);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createAnimatorUpdateListener$lambda$0(Drawable drawable, ValueAnimator animation) {
        Intrinsics.checkNotNullParameter(drawable, "$drawable");
        Intrinsics.checkNotNullParameter(animation, "animation");
        Object animatedValue = animation.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
        drawable.setLevel(((Integer) animatedValue).intValue());
    }
}
