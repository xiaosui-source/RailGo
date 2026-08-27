package com.taobao.weex.ui.flat.widget;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import com.taobao.weex.ui.view.border.BorderDrawable;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public interface Widget {
    public static final String TAG = "Widget";

    void draw(Canvas canvas);

    BorderDrawable getBackgroundAndBorder();

    Rect getBorderBox();

    Point getLocInFlatContainer();

    void onDraw(Canvas canvas);

    void setBackgroundAndBorder(BorderDrawable borderDrawable);

    void setContentBox(int i, int i2, int i3, int i4);

    void setLayout(int i, int i2, int i3, int i4, int i5, int i6, Point point);
}
