package com.taobao.weex.ui.action;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class GraphicSize {
    private float mHeight;
    private float mWidth;

    public GraphicSize(float f, float f2) {
        this.mWidth = f;
        this.mHeight = f2;
    }

    public float getHeight() {
        return this.mHeight;
    }

    public float getWidth() {
        return this.mWidth;
    }

    public void setHeight(float f) {
        this.mHeight = f;
    }

    public void setWidth(float f) {
        this.mWidth = f;
    }

    public void update(float f, float f2) {
        this.mWidth = f;
        this.mHeight = f2;
    }
}
