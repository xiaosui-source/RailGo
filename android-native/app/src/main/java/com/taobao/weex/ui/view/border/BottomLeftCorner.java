package com.taobao.weex.ui.view.border;

import android.graphics.RectF;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
class BottomLeftCorner extends BorderCorner {
    BottomLeftCorner() {
    }

    @Override // com.taobao.weex.ui.view.border.BorderCorner
    protected void prepareOval() {
        if (hasInnerCorner()) {
            setOvalLeft(getPostBorderWidth() / 2.0f);
            setOvalTop(getBorderBox().height() - ((getOuterCornerRadius() * 2.0f) - (getPreBorderWidth() / 2.0f)));
            setOvalRight((getOuterCornerRadius() * 2.0f) - (getPostBorderWidth() / 2.0f));
            setOvalBottom(getBorderBox().height() - (getPreBorderWidth() / 2.0f));
            return;
        }
        setOvalLeft(getOuterCornerRadius() / 2.0f);
        setOvalTop(getBorderBox().height() - (getOuterCornerRadius() * 1.5f));
        setOvalRight(getOuterCornerRadius() * 1.5f);
        setOvalBottom(getBorderBox().height() - (getOuterCornerRadius() / 2.0f));
    }

    @Override // com.taobao.weex.ui.view.border.BorderCorner
    protected void prepareRoundCorner() {
        if (hasOuterCorner()) {
            setRoundCornerStartX(getOuterCornerRadius());
            setRoundCornerStartY(getBorderBox().height() - (getPreBorderWidth() / 2.0f));
            setRoundCornerEndX(getPostBorderWidth() / 2.0f);
            setRoundCornerEndY(getBorderBox().height() - getOuterCornerRadius());
            return;
        }
        float postBorderWidth = getPostBorderWidth() / 2.0f;
        float fHeight = getBorderBox().height() - (getPreBorderWidth() / 2.0f);
        setRoundCornerStartX(postBorderWidth);
        setRoundCornerStartY(fHeight);
        setRoundCornerEndX(postBorderWidth);
        setRoundCornerEndY(fHeight);
    }

    void set(float f, float f2, float f3, RectF rectF) {
        set(f, f2, f3, rectF, 135.0f);
    }
}
