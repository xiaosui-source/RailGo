package com.taobao.weex.ui.view.border;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.taobao.weex.dom.CSSShorthand;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
class BorderEdge {
    private float mBorderWidth;
    private CSSShorthand.EDGE mEdge;
    private BorderCorner mPostCorner;
    private BorderCorner mPreCorner;

    BorderEdge() {
    }

    void drawEdge(Canvas canvas, Paint paint) {
        paint.setStrokeWidth(this.mBorderWidth);
        BorderCorner borderCorner = this.mPreCorner;
        borderCorner.drawRoundedCorner(canvas, paint, borderCorner.getAngleBisectorDegree());
        paint.setStrokeWidth(this.mBorderWidth);
        canvas.drawLine(this.mPreCorner.getRoundCornerEndX(), this.mPreCorner.getRoundCornerEndY(), this.mPostCorner.getRoundCornerStartX(), this.mPostCorner.getRoundCornerStartY(), paint);
        BorderCorner borderCorner2 = this.mPostCorner;
        borderCorner2.drawRoundedCorner(canvas, paint, borderCorner2.getAngleBisectorDegree() - 45.0f);
    }

    public float getBorderWidth() {
        return this.mBorderWidth;
    }

    public CSSShorthand.EDGE getEdge() {
        return this.mEdge;
    }

    BorderEdge set(BorderCorner borderCorner, BorderCorner borderCorner2, float f, CSSShorthand.EDGE edge) {
        this.mPreCorner = borderCorner;
        this.mPostCorner = borderCorner2;
        this.mBorderWidth = f;
        this.mEdge = edge;
        return this;
    }
}
