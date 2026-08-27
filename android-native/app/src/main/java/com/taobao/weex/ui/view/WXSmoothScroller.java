package com.taobao.weex.ui.view;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXSmoothScroller extends Scroller {
    private double mScrollFactor;

    public WXSmoothScroller(Context context) {
        super(context);
        this.mScrollFactor = 1.0d;
    }

    public void setScrollDurationFactor(double d) {
        this.mScrollFactor = d;
    }

    @Override // android.widget.Scroller
    public void startScroll(int i, int i2, int i3, int i4, int i5) {
        super.startScroll(i, i2, i3, i4, (int) (i5 * this.mScrollFactor));
    }

    public WXSmoothScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
        this.mScrollFactor = 1.0d;
    }

    public WXSmoothScroller(Context context, Interpolator interpolator, boolean z) {
        super(context, interpolator, z);
        this.mScrollFactor = 1.0d;
    }
}
