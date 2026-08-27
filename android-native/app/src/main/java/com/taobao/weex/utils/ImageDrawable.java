package com.taobao.weex.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.widget.ImageView;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class ImageDrawable extends PaintDrawable {
    private int bitmapHeight;
    private int bitmapWidth;
    private float[] radii;

    private ImageDrawable() {
    }

    public static Drawable createImageDrawable(Drawable drawable, ImageView.ScaleType scaleType, float[] fArr, int i, int i2, boolean z) {
        Bitmap bitmap;
        if (!z && i > 0 && i2 > 0) {
            if ((drawable instanceof BitmapDrawable) && (bitmap = ((BitmapDrawable) drawable).getBitmap()) != null) {
                ImageDrawable imageDrawable = new ImageDrawable();
                imageDrawable.getPaint().setFilterBitmap(true);
                imageDrawable.bitmapWidth = bitmap.getWidth();
                imageDrawable.bitmapHeight = bitmap.getHeight();
                Shader.TileMode tileMode = Shader.TileMode.CLAMP;
                BitmapShader bitmapShader = new BitmapShader(bitmap, tileMode, tileMode);
                updateShaderAndSize(scaleType, i, i2, imageDrawable, bitmapShader);
                imageDrawable.getPaint().setShader(bitmapShader);
                return imageDrawable;
            }
            if (drawable instanceof ImageDrawable) {
                ImageDrawable imageDrawable2 = (ImageDrawable) drawable;
                if (imageDrawable2.getPaint() != null && (imageDrawable2.getPaint().getShader() instanceof BitmapShader)) {
                    updateShaderAndSize(scaleType, i, i2, imageDrawable2, (BitmapShader) imageDrawable2.getPaint().getShader());
                    return imageDrawable2;
                }
            }
        }
        return drawable;
    }

    private static Matrix createShaderMatrix(ImageView.ScaleType scaleType, int i, int i2, int i3, int i4) {
        float f;
        float f2;
        float f3;
        if (i3 * i2 > i4 * i) {
            f = i2 / i4;
            f3 = (i - (i3 * f)) * 0.5f;
            f2 = 0.0f;
        } else {
            f = i / i3;
            f2 = (i2 - (i4 * f)) * 0.5f;
            f3 = 0.0f;
        }
        Matrix matrix = new Matrix();
        if (scaleType == ImageView.ScaleType.FIT_XY) {
            matrix.setScale(i / i3, i2 / i4);
            return matrix;
        }
        if (scaleType == ImageView.ScaleType.FIT_CENTER) {
            matrix.setRectToRect(new RectF(0.0f, 0.0f, i3, i4), new RectF(0.0f, 0.0f, i, i2), Matrix.ScaleToFit.CENTER);
            return matrix;
        }
        if (scaleType == ImageView.ScaleType.CENTER_CROP) {
            matrix.setScale(f, f);
            matrix.postTranslate(f3 + 0.5f, f2 + 0.5f);
        }
        return matrix;
    }

    private static void updateShaderAndSize(ImageView.ScaleType scaleType, int i, int i2, ImageDrawable imageDrawable, BitmapShader bitmapShader) {
        Matrix matrixCreateShaderMatrix = createShaderMatrix(scaleType, i, i2, imageDrawable.bitmapWidth, imageDrawable.bitmapHeight);
        if (scaleType == ImageView.ScaleType.FIT_CENTER) {
            RectF rectF = new RectF(0.0f, 0.0f, imageDrawable.bitmapWidth, imageDrawable.bitmapHeight);
            RectF rectF2 = new RectF();
            matrixCreateShaderMatrix.mapRect(rectF2, rectF);
            i = (int) rectF2.width();
            i2 = (int) rectF2.height();
            matrixCreateShaderMatrix = createShaderMatrix(scaleType, i, i2, imageDrawable.bitmapWidth, imageDrawable.bitmapHeight);
        }
        imageDrawable.setIntrinsicWidth(i);
        imageDrawable.setIntrinsicHeight(i2);
        bitmapShader.setLocalMatrix(matrixCreateShaderMatrix);
    }

    public int getBitmapHeight() {
        return this.bitmapHeight;
    }

    public int getBitmapWidth() {
        return this.bitmapWidth;
    }

    public float[] getCornerRadii() {
        return this.radii;
    }

    @Override // android.graphics.drawable.ShapeDrawable
    protected void onDraw(Shape shape, Canvas canvas, Paint paint) {
        if (Build.VERSION.SDK_INT == 21) {
            paint.setAntiAlias(false);
        }
        super.onDraw(shape, canvas, paint);
    }

    @Override // android.graphics.drawable.PaintDrawable
    public void setCornerRadii(float[] fArr) {
        this.radii = fArr;
        super.setCornerRadii(fArr);
    }
}
