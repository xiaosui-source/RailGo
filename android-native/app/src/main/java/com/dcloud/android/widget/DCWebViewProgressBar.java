package com.dcloud.android.widget;

import android.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ProgressBar;
import com.facebook.common.statfs.StatFsHelper;
import io.dcloud.feature.gg.dcloud.ADSim;
import io.dcloud.nineoldandroids.animation.Animator;
import io.dcloud.nineoldandroids.animation.AnimatorListenerAdapter;
import io.dcloud.nineoldandroids.animation.ObjectAnimator;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class DCWebViewProgressBar extends ProgressBar {
    int alpha;
    public boolean isFinish;
    ObjectAnimator mCurrentAnmiator;

    public DCWebViewProgressBar(Context context) {
        super(context, null, R.attr.progressBarStyleHorizontal);
        this.isFinish = false;
        this.alpha = 255;
        setMax(ADSim.INTISPLSH);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ObjectAnimator getProgressAnimation(int i, int i2, Interpolator interpolator, AnimatorListenerAdapter animatorListenerAdapter) {
        ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(this, "progress", getProgress(), i * 100);
        objectAnimatorOfInt.setDuration(i2);
        objectAnimatorOfInt.setInterpolator(interpolator);
        if (animatorListenerAdapter != null) {
            objectAnimatorOfInt.addListener(animatorListenerAdapter);
        }
        return objectAnimatorOfInt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startDismissAnimation() {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "alpha", 1.0f, 0.0f);
        objectAnimatorOfFloat.setDuration(1000L);
        objectAnimatorOfFloat.setInterpolator(new DecelerateInterpolator());
        objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.dcloud.android.widget.DCWebViewProgressBar.3
            @Override // io.dcloud.nineoldandroids.animation.AnimatorListenerAdapter, io.dcloud.nineoldandroids.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                DCWebViewProgressBar.this.setProgress(0);
            }
        });
        objectAnimatorOfFloat.start();
    }

    public void finishProgress() {
        if (this.isFinish) {
            return;
        }
        this.isFinish = true;
        ObjectAnimator objectAnimator = this.mCurrentAnmiator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        ObjectAnimator progressAnimation = getProgressAnimation(100, StatFsHelper.DEFAULT_DISK_YELLOW_LEVEL_IN_MB, new AccelerateInterpolator(), new AnimatorListenerAdapter() { // from class: com.dcloud.android.widget.DCWebViewProgressBar.2
            @Override // io.dcloud.nineoldandroids.animation.AnimatorListenerAdapter, io.dcloud.nineoldandroids.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                DCWebViewProgressBar dCWebViewProgressBar = DCWebViewProgressBar.this;
                if (dCWebViewProgressBar.isFinish) {
                    dCWebViewProgressBar.startDismissAnimation();
                }
            }
        });
        this.mCurrentAnmiator = progressAnimation;
        progressAnimation.start();
    }

    public void setAlphaInt(int i) {
        this.alpha = i;
    }

    public void setColorInt(int i) {
        int iArgb = Color.argb(this.alpha, Color.red(i), Color.green(i), Color.blue(i));
        ClipDrawable clipDrawable = new ClipDrawable(new ColorDrawable(0), 3, 1);
        clipDrawable.setLevel(ADSim.INTISPLSH);
        ClipDrawable clipDrawable2 = new ClipDrawable(new ColorDrawable(iArgb), 3, 1);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{clipDrawable, clipDrawable2, clipDrawable2});
        layerDrawable.setId(0, R.id.background);
        layerDrawable.setId(1, R.id.secondaryProgress);
        layerDrawable.setId(2, R.id.progress);
        setProgressDrawable(layerDrawable);
    }

    public void startProgress() {
        setProgress(0);
        setAlpha(1.0f);
        this.isFinish = false;
        final DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
        ObjectAnimator progressAnimation = getProgressAnimation(30, 2000, decelerateInterpolator, new AnimatorListenerAdapter() { // from class: com.dcloud.android.widget.DCWebViewProgressBar.1
            @Override // io.dcloud.nineoldandroids.animation.AnimatorListenerAdapter, io.dcloud.nineoldandroids.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                DCWebViewProgressBar dCWebViewProgressBar = DCWebViewProgressBar.this;
                if (dCWebViewProgressBar.isFinish) {
                    return;
                }
                dCWebViewProgressBar.mCurrentAnmiator = dCWebViewProgressBar.getProgressAnimation(70, 2000, decelerateInterpolator, new AnimatorListenerAdapter() { // from class: com.dcloud.android.widget.DCWebViewProgressBar.1.1
                    @Override // io.dcloud.nineoldandroids.animation.AnimatorListenerAdapter, io.dcloud.nineoldandroids.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator2) {
                        super.onAnimationEnd(animator2);
                        AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                        DCWebViewProgressBar dCWebViewProgressBar2 = DCWebViewProgressBar.this;
                        if (dCWebViewProgressBar2.isFinish) {
                            return;
                        }
                        dCWebViewProgressBar2.mCurrentAnmiator = dCWebViewProgressBar2.getProgressAnimation(95, 50000, decelerateInterpolator, null);
                        DCWebViewProgressBar.this.mCurrentAnmiator.start();
                    }
                });
                DCWebViewProgressBar.this.mCurrentAnmiator.start();
            }
        });
        this.mCurrentAnmiator = progressAnimation;
        progressAnimation.start();
    }
}
