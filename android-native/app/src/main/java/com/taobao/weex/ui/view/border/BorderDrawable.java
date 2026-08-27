package com.taobao.weex.ui.view.border;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import com.taobao.weex.dom.CSSShorthand;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXViewUtils;
import io.dcloud.feature.uniapp.ui.shadow.UniBoxShadowData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class BorderDrawable extends Drawable {
    public static final int BORDER_BOTTOM_LEFT_RADIUS = 3;
    public static final int BORDER_BOTTOM_RIGHT_RADIUS = 2;
    public static final int BORDER_RADIUS_ALL = 5;
    public static final int BORDER_TOP_LEFT_RADIUS = 0;
    public static final int BORDER_TOP_RIGHT_RADIUS = 1;
    static final int DEFAULT_BORDER_COLOR = -16777216;
    static final float DEFAULT_BORDER_WIDTH = 0.0f;
    private static final String TAG = "Border";
    private SparseIntArray mBorderColor;
    private CSSShorthand<CSSShorthand.CORNER> mBorderRadius;
    private SparseIntArray mBorderStyle;
    private CSSShorthand<CSSShorthand.EDGE> mBorderWidth;
    private BottomLeftCorner mBottomLeftCorner;
    private BottomRightCorner mBottomRightCorner;
    private CSSShorthand<CSSShorthand.CORNER> mOverlappingBorderRadius;
    private Path mPathForBorderOutline;
    private RectF mRectBounds;
    private TopLeftCorner mTopLeftCorner;
    private TopRightCorner mTopRightCorner;
    private static final BorderStyle DEFAULT_BORDER_STYLE = BorderStyle.SOLID;
    private static BorderStyle[] sBorderStyle = BorderStyle.values();
    private final Paint mPaint = new Paint(1);
    private boolean mNeedUpdatePath = false;
    private int mColor = 0;
    private Shader mShader = null;
    private int mAlpha = 255;
    private final BorderEdge mBorderEdge = new BorderEdge();
    UniBoxShadowData mBoxShadowData = null;

    private void drawBorders(Canvas canvas) {
        int i;
        RectF rectF = this.mRectBounds;
        if (rectF == null) {
            this.mRectBounds = new RectF(getBounds());
        } else {
            rectF.set(getBounds());
        }
        UniBoxShadowData uniBoxShadowData = this.mBoxShadowData;
        if (uniBoxShadowData != null) {
            int normalTop = uniBoxShadowData.getNormalTop() > 0 ? this.mBoxShadowData.getNormalTop() / 2 : 0;
            normalLeft = this.mBoxShadowData.getNormalLeft() > 0 ? this.mBoxShadowData.getNormalLeft() / 2 : 0;
            this.mRectBounds.inset(normalLeft, normalTop);
            int i2 = normalLeft;
            normalLeft = normalTop;
            i = i2;
        } else {
            i = 0;
        }
        CSSShorthand<CSSShorthand.EDGE> cSSShorthand = this.mBorderWidth;
        if (cSSShorthand == null) {
            return;
        }
        CSSShorthand.EDGE edge = CSSShorthand.EDGE.LEFT;
        float f = cSSShorthand.get(edge);
        CSSShorthand<CSSShorthand.EDGE> cSSShorthand2 = this.mBorderWidth;
        CSSShorthand.EDGE edge2 = CSSShorthand.EDGE.TOP;
        float f2 = cSSShorthand2.get(edge2);
        CSSShorthand<CSSShorthand.EDGE> cSSShorthand3 = this.mBorderWidth;
        CSSShorthand.EDGE edge3 = CSSShorthand.EDGE.BOTTOM;
        float f3 = cSSShorthand3.get(edge3);
        CSSShorthand<CSSShorthand.EDGE> cSSShorthand4 = this.mBorderWidth;
        CSSShorthand.EDGE edge4 = CSSShorthand.EDGE.RIGHT;
        float f4 = cSSShorthand4.get(edge4);
        if (this.mTopLeftCorner == null) {
            this.mTopLeftCorner = new TopLeftCorner();
        }
        this.mTopLeftCorner.set(getBorderRadius(CSSShorthand.CORNER.BORDER_TOP_LEFT), f, f2, this.mRectBounds);
        if (this.mTopRightCorner == null) {
            this.mTopRightCorner = new TopRightCorner();
        }
        this.mTopRightCorner.set(getBorderRadius(CSSShorthand.CORNER.BORDER_TOP_RIGHT), f2, f4, this.mRectBounds);
        if (this.mBottomRightCorner == null) {
            this.mBottomRightCorner = new BottomRightCorner();
        }
        this.mBottomRightCorner.set(getBorderRadius(CSSShorthand.CORNER.BORDER_BOTTOM_RIGHT), f4, f3, this.mRectBounds);
        if (this.mBottomLeftCorner == null) {
            this.mBottomLeftCorner = new BottomLeftCorner();
        }
        this.mBottomLeftCorner.set(getBorderRadius(CSSShorthand.CORNER.BORDER_BOTTOM_LEFT), f3, f, this.mRectBounds);
        if (isDefAllDrawRoundRect(canvas, f, f2, f3, f4)) {
            return;
        }
        canvas.translate(i, normalLeft);
        drawOneSide(canvas, this.mBorderEdge.set(this.mTopLeftCorner, this.mTopRightCorner, f2, edge2));
        drawOneSide(canvas, this.mBorderEdge.set(this.mTopRightCorner, this.mBottomRightCorner, f4, edge4));
        drawOneSide(canvas, this.mBorderEdge.set(this.mBottomRightCorner, this.mBottomLeftCorner, f3, edge3));
        drawOneSide(canvas, this.mBorderEdge.set(this.mBottomLeftCorner, this.mTopLeftCorner, f, edge));
    }

    private void drawOneSide(Canvas canvas, BorderEdge borderEdge) {
        if (0.0f != borderEdge.getBorderWidth()) {
            preparePaint(borderEdge.getEdge());
            borderEdge.drawEdge(canvas, this.mPaint);
        }
    }

    private float getScaleFactor(RectF rectF) {
        CSSShorthand<CSSShorthand.CORNER> cSSShorthand = this.mBorderRadius;
        CSSShorthand.CORNER corner = CSSShorthand.CORNER.BORDER_TOP_LEFT;
        float f = cSSShorthand.get(corner);
        CSSShorthand<CSSShorthand.CORNER> cSSShorthand2 = this.mBorderRadius;
        CSSShorthand.CORNER corner2 = CSSShorthand.CORNER.BORDER_TOP_RIGHT;
        float f2 = f + cSSShorthand2.get(corner2);
        float f3 = this.mBorderRadius.get(corner2);
        CSSShorthand<CSSShorthand.CORNER> cSSShorthand3 = this.mBorderRadius;
        CSSShorthand.CORNER corner3 = CSSShorthand.CORNER.BORDER_BOTTOM_RIGHT;
        float f4 = f3 + cSSShorthand3.get(corner3);
        float f5 = this.mBorderRadius.get(corner3);
        CSSShorthand<CSSShorthand.CORNER> cSSShorthand4 = this.mBorderRadius;
        CSSShorthand.CORNER corner4 = CSSShorthand.CORNER.BORDER_BOTTOM_LEFT;
        float f6 = f5 + cSSShorthand4.get(corner4);
        float f7 = this.mBorderRadius.get(corner4) + this.mBorderRadius.get(corner);
        ArrayList arrayList = new ArrayList(4);
        updateFactor(arrayList, rectF.width(), f2);
        updateFactor(arrayList, rectF.height(), f4);
        updateFactor(arrayList, rectF.width(), f6);
        updateFactor(arrayList, rectF.height(), f7);
        if (arrayList.isEmpty()) {
            return Float.NaN;
        }
        return ((Float) Collections.min(arrayList)).floatValue();
    }

    private boolean isDefAllDrawRoundRect(Canvas canvas, float f, float f2, float f3, float f4) {
        float outerCornerRadius = this.mTopLeftCorner.getOuterCornerRadius();
        if (outerCornerRadius <= 0.0f || outerCornerRadius != this.mTopRightCorner.getOuterCornerRadius() || outerCornerRadius != this.mBottomLeftCorner.getOuterCornerRadius() || outerCornerRadius != this.mBottomRightCorner.getOuterCornerRadius() || f <= 0.0f || f != f2 || f != f3 || f != f4) {
            return false;
        }
        int iMultiplyColorAlpha = WXViewUtils.multiplyColorAlpha(getBorderColor(CSSShorthand.EDGE.LEFT), this.mAlpha);
        CSSShorthand.EDGE edge = CSSShorthand.EDGE.TOP;
        int iMultiplyColorAlpha2 = WXViewUtils.multiplyColorAlpha(getBorderColor(edge), this.mAlpha);
        int iMultiplyColorAlpha3 = WXViewUtils.multiplyColorAlpha(getBorderColor(CSSShorthand.EDGE.BOTTOM), this.mAlpha);
        int iMultiplyColorAlpha4 = WXViewUtils.multiplyColorAlpha(getBorderColor(CSSShorthand.EDGE.RIGHT), this.mAlpha);
        if (iMultiplyColorAlpha != iMultiplyColorAlpha2 || iMultiplyColorAlpha != iMultiplyColorAlpha3 || iMultiplyColorAlpha != iMultiplyColorAlpha4) {
            return false;
        }
        preparePaint(this.mBorderEdge.set(this.mTopLeftCorner, this.mTopRightCorner, f2, edge).getEdge());
        this.mPaint.setStrokeWidth(f);
        RectF rectF = new RectF();
        float f5 = f / 2.0f;
        RectF rectF2 = this.mRectBounds;
        rectF.top = rectF2.top + f5;
        rectF.bottom = rectF2.bottom - f5;
        rectF.left = rectF2.left + f5;
        rectF.right = rectF2.right - f5;
        canvas.drawRoundRect(rectF, outerCornerRadius, outerCornerRadius, this.mPaint);
        return true;
    }

    private void prepareBorderPath(int i, int i2, int i3, int i4, RectF rectF, Path path) {
        UniBoxShadowData uniBoxShadowData = this.mBoxShadowData;
        if (uniBoxShadowData != null) {
            rectF.inset(this.mBoxShadowData.getNormalLeft() > 0 ? this.mBoxShadowData.getNormalLeft() / 2 : 0, uniBoxShadowData.getNormalTop() > 0 ? this.mBoxShadowData.getNormalTop() / 2 : 0);
        }
        if (this.mBorderRadius == null) {
            path.addRect(rectF, Path.Direction.CW);
            return;
        }
        prepareBorderRadius(rectF);
        if (this.mOverlappingBorderRadius == null) {
            this.mOverlappingBorderRadius = new CSSShorthand<>();
        }
        float f = this.mOverlappingBorderRadius.get(CSSShorthand.CORNER.BORDER_TOP_LEFT);
        float f2 = this.mOverlappingBorderRadius.get(CSSShorthand.CORNER.BORDER_TOP_RIGHT);
        float f3 = this.mOverlappingBorderRadius.get(CSSShorthand.CORNER.BORDER_BOTTOM_RIGHT);
        float f4 = this.mOverlappingBorderRadius.get(CSSShorthand.CORNER.BORDER_BOTTOM_LEFT);
        float f5 = i4;
        float f6 = f - f5;
        float f7 = i;
        float f8 = f - f7;
        float f9 = i2;
        float f10 = f2 - f9;
        float f11 = f2 - f7;
        float f12 = f3 - f9;
        float f13 = i3;
        path.addRoundRect(rectF, new float[]{f6, f8, f10, f11, f12, f3 - f13, f4 - f5, f4 - f13}, Path.Direction.CW);
    }

    private void prepareBorderRadius(RectF rectF) {
        if (this.mBorderRadius != null) {
            float scaleFactor = getScaleFactor(rectF);
            if (this.mOverlappingBorderRadius == null) {
                this.mOverlappingBorderRadius = new CSSShorthand<>();
            }
            if (Float.isNaN(scaleFactor) || scaleFactor >= 1.0f) {
                CSSShorthand<CSSShorthand.CORNER> cSSShorthand = this.mOverlappingBorderRadius;
                CSSShorthand.CORNER corner = CSSShorthand.CORNER.BORDER_TOP_LEFT;
                cSSShorthand.set(corner, this.mBorderRadius.get(corner));
                CSSShorthand<CSSShorthand.CORNER> cSSShorthand2 = this.mOverlappingBorderRadius;
                CSSShorthand.CORNER corner2 = CSSShorthand.CORNER.BORDER_TOP_RIGHT;
                cSSShorthand2.set(corner2, this.mBorderRadius.get(corner2));
                CSSShorthand<CSSShorthand.CORNER> cSSShorthand3 = this.mOverlappingBorderRadius;
                CSSShorthand.CORNER corner3 = CSSShorthand.CORNER.BORDER_BOTTOM_RIGHT;
                cSSShorthand3.set(corner3, this.mBorderRadius.get(corner3));
                CSSShorthand<CSSShorthand.CORNER> cSSShorthand4 = this.mOverlappingBorderRadius;
                CSSShorthand.CORNER corner4 = CSSShorthand.CORNER.BORDER_BOTTOM_LEFT;
                cSSShorthand4.set(corner4, this.mBorderRadius.get(corner4));
                return;
            }
            CSSShorthand<CSSShorthand.CORNER> cSSShorthand5 = this.mOverlappingBorderRadius;
            CSSShorthand.CORNER corner5 = CSSShorthand.CORNER.BORDER_TOP_LEFT;
            cSSShorthand5.set(corner5, this.mBorderRadius.get(corner5) * scaleFactor);
            CSSShorthand<CSSShorthand.CORNER> cSSShorthand6 = this.mOverlappingBorderRadius;
            CSSShorthand.CORNER corner6 = CSSShorthand.CORNER.BORDER_TOP_RIGHT;
            cSSShorthand6.set(corner6, this.mBorderRadius.get(corner6) * scaleFactor);
            CSSShorthand<CSSShorthand.CORNER> cSSShorthand7 = this.mOverlappingBorderRadius;
            CSSShorthand.CORNER corner7 = CSSShorthand.CORNER.BORDER_BOTTOM_RIGHT;
            cSSShorthand7.set(corner7, this.mBorderRadius.get(corner7) * scaleFactor);
            CSSShorthand<CSSShorthand.CORNER> cSSShorthand8 = this.mOverlappingBorderRadius;
            CSSShorthand.CORNER corner8 = CSSShorthand.CORNER.BORDER_BOTTOM_LEFT;
            cSSShorthand8.set(corner8, this.mBorderRadius.get(corner8) * scaleFactor);
        }
    }

    private void preparePaint(CSSShorthand.EDGE edge) {
        float f = this.mBorderWidth.get(edge);
        int iMultiplyColorAlpha = WXViewUtils.multiplyColorAlpha(getBorderColor(edge), this.mAlpha);
        this.mPaint.setShader(sBorderStyle[getBorderStyle(edge)].getLineShader(f, iMultiplyColorAlpha, edge));
        this.mPaint.setColor(iMultiplyColorAlpha);
        this.mPaint.setStrokeCap(Paint.Cap.BUTT);
    }

    private void updateBorderOutline() {
        if (this.mNeedUpdatePath) {
            this.mNeedUpdatePath = false;
            if (this.mPathForBorderOutline == null) {
                this.mPathForBorderOutline = new Path();
            }
            this.mPathForBorderOutline.reset();
            prepareBorderPath(0, 0, 0, 0, new RectF(getBounds()), this.mPathForBorderOutline);
        }
    }

    private void updateFactor(List<Float> list, float f, float f2) {
        if (f2 != 0.0f) {
            list.add(Float.valueOf(f / f2));
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        canvas.save();
        updateBorderOutline();
        this.mPaint.setAlpha(255);
        if (this.mPathForBorderOutline != null) {
            int iMultiplyColorAlpha = WXViewUtils.multiplyColorAlpha(this.mColor, this.mAlpha);
            Shader shader = this.mShader;
            if (shader != null) {
                this.mPaint.setShader(shader);
                this.mPaint.setStyle(Paint.Style.FILL);
                canvas.drawPath(this.mPathForBorderOutline, this.mPaint);
                this.mPaint.setShader(null);
            } else if ((iMultiplyColorAlpha >>> 24) != 0) {
                this.mPaint.setColor(iMultiplyColorAlpha);
                this.mPaint.setStyle(Paint.Style.FILL);
                canvas.drawPath(this.mPathForBorderOutline, this.mPaint);
                this.mPaint.setShader(null);
            }
        }
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeJoin(Paint.Join.ROUND);
        drawBorders(canvas);
        this.mPaint.setShader(null);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mAlpha;
    }

    int getBorderColor(CSSShorthand.EDGE edge) {
        return BorderUtil.fetchFromSparseArray(this.mBorderColor, edge.ordinal(), -16777216);
    }

    public float[] getBorderInnerRadius(RectF rectF) {
        prepareBorderRadius(rectF);
        if (this.mOverlappingBorderRadius == null) {
            this.mOverlappingBorderRadius = new CSSShorthand<>();
        }
        float fMax = this.mOverlappingBorderRadius.get(CSSShorthand.CORNER.BORDER_TOP_LEFT);
        float fMax2 = this.mOverlappingBorderRadius.get(CSSShorthand.CORNER.BORDER_TOP_RIGHT);
        float fMax3 = this.mOverlappingBorderRadius.get(CSSShorthand.CORNER.BORDER_BOTTOM_RIGHT);
        float fMax4 = this.mOverlappingBorderRadius.get(CSSShorthand.CORNER.BORDER_BOTTOM_LEFT);
        CSSShorthand<CSSShorthand.EDGE> cSSShorthand = this.mBorderWidth;
        if (cSSShorthand != null) {
            CSSShorthand.EDGE edge = CSSShorthand.EDGE.TOP;
            fMax = Math.max(fMax - cSSShorthand.get(edge), 0.0f);
            fMax2 = Math.max(fMax2 - this.mBorderWidth.get(edge), 0.0f);
            CSSShorthand<CSSShorthand.EDGE> cSSShorthand2 = this.mBorderWidth;
            CSSShorthand.EDGE edge2 = CSSShorthand.EDGE.BOTTOM;
            fMax3 = Math.max(fMax3 - cSSShorthand2.get(edge2), 0.0f);
            fMax4 = Math.max(fMax4 - this.mBorderWidth.get(edge2), 0.0f);
        }
        return new float[]{fMax, fMax, fMax2, fMax2, fMax3, fMax3, fMax4, fMax4};
    }

    public float[] getBorderRadius(RectF rectF) {
        prepareBorderRadius(rectF);
        if (this.mOverlappingBorderRadius == null) {
            this.mOverlappingBorderRadius = new CSSShorthand<>();
        }
        float f = this.mOverlappingBorderRadius.get(CSSShorthand.CORNER.BORDER_TOP_LEFT);
        float f2 = this.mOverlappingBorderRadius.get(CSSShorthand.CORNER.BORDER_TOP_RIGHT);
        float f3 = this.mOverlappingBorderRadius.get(CSSShorthand.CORNER.BORDER_BOTTOM_RIGHT);
        float f4 = this.mOverlappingBorderRadius.get(CSSShorthand.CORNER.BORDER_BOTTOM_LEFT);
        return new float[]{f, f, f2, f2, f3, f3, f4, f4};
    }

    int getBorderStyle(CSSShorthand.EDGE edge) {
        return BorderUtil.fetchFromSparseArray(this.mBorderStyle, edge.ordinal(), BorderStyle.SOLID.ordinal());
    }

    float getBorderWidth(CSSShorthand.EDGE edge) {
        return this.mBorderWidth.get(edge);
    }

    public int getColor() {
        return this.mColor;
    }

    public Path getContentPath(RectF rectF) {
        Path path = new Path();
        prepareBorderPath(0, 0, 0, 0, rectF, path);
        return path;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        if (this.mShader != null) {
            return -1;
        }
        return WXViewUtils.getOpacityFromColor(WXViewUtils.multiplyColorAlpha(this.mColor, this.mAlpha));
    }

    public boolean hasImage() {
        return this.mShader != null;
    }

    public boolean isRounded() {
        CSSShorthand<CSSShorthand.CORNER> cSSShorthand = this.mBorderRadius;
        if (cSSShorthand != null) {
            return (cSSShorthand.get(CSSShorthand.CORNER.BORDER_TOP_LEFT) == 0.0f && this.mBorderRadius.get(CSSShorthand.CORNER.BORDER_TOP_RIGHT) == 0.0f && this.mBorderRadius.get(CSSShorthand.CORNER.BORDER_BOTTOM_RIGHT) == 0.0f && this.mBorderRadius.get(CSSShorthand.CORNER.BORDER_BOTTOM_LEFT) == 0.0f) ? false : true;
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mNeedUpdatePath = true;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (i != this.mAlpha) {
            this.mAlpha = i;
            invalidateSelf();
        }
    }

    public void setBorderColor(CSSShorthand.EDGE edge, int i) {
        if (this.mBorderColor == null) {
            SparseIntArray sparseIntArray = new SparseIntArray(5);
            this.mBorderColor = sparseIntArray;
            sparseIntArray.put(CSSShorthand.EDGE.ALL.ordinal(), -16777216);
        }
        if (getBorderColor(edge) != i) {
            BorderUtil.updateSparseArray(this.mBorderColor, edge.ordinal(), i);
            invalidateSelf();
        }
    }

    public void setBorderRadius(CSSShorthand.CORNER corner, float f) {
        if (this.mBorderRadius == null) {
            this.mBorderRadius = new CSSShorthand<>();
        }
        if (this.mBorderRadius.get(corner) == f) {
            if (corner != CSSShorthand.CORNER.ALL) {
                return;
            }
            if (f == this.mBorderRadius.get(CSSShorthand.CORNER.BORDER_TOP_LEFT) && f == this.mBorderRadius.get(CSSShorthand.CORNER.BORDER_TOP_RIGHT) && f == this.mBorderRadius.get(CSSShorthand.CORNER.BORDER_BOTTOM_RIGHT) && f == this.mBorderRadius.get(CSSShorthand.CORNER.BORDER_BOTTOM_LEFT)) {
                return;
            }
        }
        this.mBorderRadius.set(corner, f);
        this.mNeedUpdatePath = true;
        invalidateSelf();
    }

    public void setBorderStyle(CSSShorthand.EDGE edge, String str) {
        if (this.mBorderStyle == null) {
            SparseIntArray sparseIntArray = new SparseIntArray(5);
            this.mBorderStyle = sparseIntArray;
            sparseIntArray.put(CSSShorthand.EDGE.ALL.ordinal(), DEFAULT_BORDER_STYLE.ordinal());
        }
        try {
            int iOrdinal = BorderStyle.valueOf(str.toUpperCase(Locale.US)).ordinal();
            if (getBorderStyle(edge) != iOrdinal) {
                BorderUtil.updateSparseArray(this.mBorderStyle, edge.ordinal(), iOrdinal);
                invalidateSelf();
            }
        } catch (IllegalArgumentException e) {
            WXLogUtils.e(TAG, WXLogUtils.getStackTrace(e));
        }
    }

    public void setBorderWidth(CSSShorthand.EDGE edge, float f) {
        if (this.mBorderWidth == null) {
            this.mBorderWidth = new CSSShorthand<>();
        }
        if (this.mBorderWidth.get(edge) != f) {
            this.mBorderWidth.set(edge, f);
            this.mNeedUpdatePath = true;
            invalidateSelf();
        }
    }

    public void setColor(int i) {
        this.mColor = i;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public void setImage(Shader shader) {
        this.mShader = shader;
        invalidateSelf();
    }

    public void updateBoxShadowData(UniBoxShadowData uniBoxShadowData) {
        this.mBoxShadowData = uniBoxShadowData;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        if (this.mPathForBorderOutline == null) {
            this.mNeedUpdatePath = true;
        }
        updateBorderOutline();
        outline.setConvexPath(this.mPathForBorderOutline);
    }

    private float getBorderRadius(CSSShorthand.CORNER corner) {
        CSSShorthand<CSSShorthand.CORNER> cSSShorthand = this.mOverlappingBorderRadius;
        if (cSSShorthand != null) {
            return cSSShorthand.get(corner);
        }
        return 0.0f;
    }
}
