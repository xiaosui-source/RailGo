package com.taobao.weex.ui.view.border;

import android.graphics.RectF;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
class BottomRightCorner extends BorderCorner {
    BottomRightCorner() {
    }

    @Override // com.taobao.weex.ui.view.border.BorderCorner
    protected void prepareOval() {
        if (hasInnerCorner()) {
            setOvalLeft(getBorderBox().width() - ((getOuterCornerRadius() * 2.0f) - (getPreBorderWidth() / 2.0f)));
            setOvalTop(getBorderBox().height() - ((getOuterCornerRadius() * 2.0f) - (getPostBorderWidth() / 2.0f)));
            setOvalRight(getBorderBox().width() - (getPreBorderWidth() / 2.0f));
            setOvalBottom(getBorderBox().height() - (getPostBorderWidth() / 2.0f));
            return;
        }
        setOvalLeft(getBorderBox().width() - (getOuterCornerRadius() * 1.5f));
        setOvalTop(getBorderBox().height() - (getOuterCornerRadius() * 1.5f));
        setOvalRight(getBorderBox().width() - (getOuterCornerRadius() / 2.0f));
        setOvalBottom(getBorderBox().height() - (getOuterCornerRadius() / 2.0f));
    }

    @Override // com.taobao.weex.ui.view.border.BorderCorner
    protected void prepareRoundCorner() {
        if (hasOuterCorner()) {
            setRoundCornerStartX(getBorderBox().width() - (getPreBorderWidth() / 2.0f));
            setRoundCornerStartY(getBorderBox().height() - getOuterCornerRadius());
            setRoundCornerEndX(getBorderBox().width() - getOuterCornerRadius());
            setRoundCornerEndY(getBorderBox().height() - (getPostBorderWidth() / 2.0f));
            return;
        }
        float fWidth = getBorderBox().width() - (getPreBorderWidth() / 2.0f);
        float fHeight = getBorderBox().height() - (getPostBorderWidth() / 2.0f);
        setRoundCornerStartX(fWidth);
        setRoundCornerStartY(fHeight);
        setRoundCornerEndX(fWidth);
        setRoundCornerEndY(fHeight);
    }

    void set(float f, float f2, float f3, RectF rectF) {
        set(f, f2, f3, rectF, 45.0f);
    }
}
