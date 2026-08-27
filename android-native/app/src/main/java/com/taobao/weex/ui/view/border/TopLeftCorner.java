package com.taobao.weex.ui.view.border;

import android.graphics.RectF;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
class TopLeftCorner extends BorderCorner {
    TopLeftCorner() {
    }

    @Override // com.taobao.weex.ui.view.border.BorderCorner
    protected void prepareOval() {
        if (hasInnerCorner()) {
            setOvalLeft(getPreBorderWidth() / 2.0f);
            setOvalTop(getPostBorderWidth() / 2.0f);
            setOvalRight((getOuterCornerRadius() * 2.0f) - (getPreBorderWidth() / 2.0f));
            setOvalBottom((getOuterCornerRadius() * 2.0f) - (getPostBorderWidth() / 2.0f));
            return;
        }
        setOvalLeft(getOuterCornerRadius() / 2.0f);
        setOvalTop(getOuterCornerRadius() / 2.0f);
        setOvalRight(getOuterCornerRadius() * 1.5f);
        setOvalBottom(getOuterCornerRadius() * 1.5f);
    }

    @Override // com.taobao.weex.ui.view.border.BorderCorner
    protected void prepareRoundCorner() {
        if (hasOuterCorner()) {
            setRoundCornerStartX(getPreBorderWidth() / 2.0f);
            setRoundCornerStartY(getOuterCornerRadius());
            setRoundCornerEndX(getOuterCornerRadius());
            setRoundCornerEndY(getPostBorderWidth() / 2.0f);
            return;
        }
        float preBorderWidth = getPreBorderWidth() / 2.0f;
        float postBorderWidth = getPostBorderWidth() / 2.0f;
        setRoundCornerStartX(preBorderWidth);
        setRoundCornerStartY(postBorderWidth);
        setRoundCornerEndX(preBorderWidth);
        setRoundCornerEndY(postBorderWidth);
    }

    void set(float f, float f2, float f3, RectF rectF) {
        set(f, f2, f3, rectF, 225.0f);
    }
}
