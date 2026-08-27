package com.taobao.weex.ui.view.border;

import android.graphics.RectF;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
class TopRightCorner extends BorderCorner {
    TopRightCorner() {
    }

    @Override // com.taobao.weex.ui.view.border.BorderCorner
    protected void prepareOval() {
        if (hasInnerCorner()) {
            setOvalLeft(getBorderBox().width() - ((getOuterCornerRadius() * 2.0f) - (getPostBorderWidth() / 2.0f)));
            setOvalTop(getPreBorderWidth() / 2.0f);
            setOvalRight(getBorderBox().width() - (getPostBorderWidth() / 2.0f));
            setOvalBottom((getOuterCornerRadius() * 2.0f) - (getPreBorderWidth() / 2.0f));
            return;
        }
        setOvalLeft(getBorderBox().width() - (getOuterCornerRadius() * 1.5f));
        setOvalTop(getOuterCornerRadius() / 2.0f);
        setOvalRight(getBorderBox().width() - (getOuterCornerRadius() / 2.0f));
        setOvalBottom(getOuterCornerRadius() * 1.5f);
    }

    @Override // com.taobao.weex.ui.view.border.BorderCorner
    protected void prepareRoundCorner() {
        if (hasOuterCorner()) {
            setRoundCornerStartX(getBorderBox().width() - getOuterCornerRadius());
            setRoundCornerStartY(getPreBorderWidth() / 2.0f);
            setRoundCornerEndX(getBorderBox().width() - (getPostBorderWidth() / 2.0f));
            setRoundCornerEndY(getOuterCornerRadius());
            return;
        }
        float fWidth = getBorderBox().width() - (getPostBorderWidth() / 2.0f);
        float preBorderWidth = getPreBorderWidth() / 2.0f;
        setRoundCornerStartX(fWidth);
        setRoundCornerStartY(preBorderWidth);
        setRoundCornerEndX(fWidth);
        setRoundCornerEndY(preBorderWidth);
    }

    void set(float f, float f2, float f3, RectF rectF) {
        set(f, f2, f3, rectF, 315.0f);
    }
}
