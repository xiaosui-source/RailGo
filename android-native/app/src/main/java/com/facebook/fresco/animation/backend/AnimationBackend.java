package com.facebook.fresco.animation.backend;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public interface AnimationBackend extends AnimationInformation {
    public static final int INTRINSIC_DIMENSION_UNSET = -1;

    public interface Listener {
        void onAnimationLoaded();
    }

    void clear();

    boolean drawFrame(Drawable drawable, Canvas canvas, int i);

    int getIntrinsicHeight();

    int getIntrinsicWidth();

    int getSizeInBytes();

    void preloadAnimation();

    void setAlpha(int i);

    void setAnimationListener(@Nullable Listener listener);

    void setBounds(Rect rect);

    void setColorFilter(@Nullable ColorFilter colorFilter);
}
