package com.taobao.weex.ui.view.border;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import com.taobao.weex.base.FloatUtil;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
abstract class BorderCorner {
    static final float SWEEP_ANGLE = 45.0f;
    protected float mAngleBisector;
    private RectF mBorderBox;
    private float mOvalBottom;
    private float mOvalLeft;
    private float mOvalRight;
    private float mOvalTop;
    private float mRoundCornerEndX;
    private float mRoundCornerEndY;
    private float mRoundCornerStartX;
    private float mRoundCornerStartY;
    private float mCornerRadius = 0.0f;
    private float mPreBorderWidth = 0.0f;
    private float mPostBorderWidth = 0.0f;
    private boolean hasInnerCorner = false;
    private boolean hasOuterCorner = false;

    BorderCorner() {
    }

    public final void drawRoundedCorner(Canvas canvas, Paint paint, float f) {
        if (!hasOuterCorner()) {
            if (getRoundCornerStartX() == getRoundCornerEndX() && getRoundCornerStartY() == getRoundCornerEndY()) {
                return;
            }
            canvas.drawLine(getRoundCornerStartX(), getRoundCornerStartY(), getRoundCornerEndX(), getRoundCornerEndY(), paint);
            return;
        }
        Paint paint2 = new Paint(paint);
        float fAbs = Math.abs(this.mOvalLeft - this.mOvalRight);
        if (paint.getStrokeWidth() > fAbs) {
            paint2.setStrokeWidth(fAbs);
        }
        paint2.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(this.mOvalLeft, this.mOvalTop, this.mOvalRight, this.mOvalBottom, f, SWEEP_ANGLE, false, paint2);
    }

    protected final float getAngleBisectorDegree() {
        return this.mAngleBisector;
    }

    protected final RectF getBorderBox() {
        return this.mBorderBox;
    }

    protected final float getOuterCornerRadius() {
        return this.mCornerRadius;
    }

    protected final float getPostBorderWidth() {
        return this.mPostBorderWidth;
    }

    protected final float getPreBorderWidth() {
        return this.mPreBorderWidth;
    }

    public final float getRoundCornerEndX() {
        return this.mRoundCornerEndX;
    }

    public final float getRoundCornerEndY() {
        return this.mRoundCornerEndY;
    }

    public final float getRoundCornerStartX() {
        return this.mRoundCornerStartX;
    }

    public final float getRoundCornerStartY() {
        return this.mRoundCornerStartY;
    }

    boolean hasInnerCorner() {
        return this.hasInnerCorner;
    }

    boolean hasOuterCorner() {
        return this.hasOuterCorner;
    }

    protected abstract void prepareOval();

    protected abstract void prepareRoundCorner();

    final void set(float f, float f2, float f3, RectF rectF, float f4) {
        RectF rectF2;
        if (FloatUtil.floatsEqual(this.mCornerRadius, f) && FloatUtil.floatsEqual(this.mPreBorderWidth, f2) && FloatUtil.floatsEqual(this.mPostBorderWidth, f3) && FloatUtil.floatsEqual(this.mAngleBisector, f4) && ((rectF2 = this.mBorderBox) == null || !rectF2.equals(rectF))) {
            return;
        }
        this.mCornerRadius = f;
        this.mPreBorderWidth = f2;
        this.mPostBorderWidth = f3;
        this.mBorderBox = rectF;
        this.mAngleBisector = f4;
        boolean z = f > 0.0f && !FloatUtil.floatsEqual(0.0f, f);
        this.hasOuterCorner = z;
        this.hasInnerCorner = z && getPreBorderWidth() >= 0.0f && getPostBorderWidth() >= 0.0f && getOuterCornerRadius() > getPreBorderWidth() && getOuterCornerRadius() > getPostBorderWidth();
        if (this.hasOuterCorner) {
            prepareOval();
        }
        prepareRoundCorner();
    }

    final void setOvalBottom(float f) {
        this.mOvalBottom = f;
    }

    final void setOvalLeft(float f) {
        this.mOvalLeft = f;
    }

    final void setOvalRight(float f) {
        this.mOvalRight = f;
    }

    final void setOvalTop(float f) {
        this.mOvalTop = f;
    }

    final void setRoundCornerEndX(float f) {
        this.mRoundCornerEndX = f;
    }

    final void setRoundCornerEndY(float f) {
        this.mRoundCornerEndY = f;
    }

    final void setRoundCornerStartX(float f) {
        this.mRoundCornerStartX = f;
    }

    final void setRoundCornerStartY(float f) {
        this.mRoundCornerStartY = f;
    }
}
