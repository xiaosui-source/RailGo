package com.taobao.weex.common;

import android.view.View;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public interface OnWXScrollListener {
    public static final int DRAGGING = 1;
    public static final int IDLE = 0;
    public static final int SETTLING = 2;

    void onScrollStateChanged(View view, int i, int i2, int i3);

    void onScrolled(View view, int i, int i2);
}
