package io.dcloud.feature.nativeObj;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import pl.droidsonroids.gif.transforms.Transform;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class GIFCornerRadiusTransform implements Transform {
    private int gifViewWidth;
    private float mCornerRadius;
    private final RectF mDstRectF = new RectF();
    private Shader mShader;

    public GIFCornerRadiusTransform(float f, int i) {
        this.gifViewWidth = -1;
        setCornerRadiusSafely(f);
        this.gifViewWidth = i;
    }

    private void setCornerRadiusSafely(float f) {
        float fMax = Math.max(0.0f, f);
        if (fMax != this.mCornerRadius) {
            this.mCornerRadius = fMax;
            this.mShader = null;
        }
    }

    public RectF getBounds() {
        return this.mDstRectF;
    }

    public float getCornerRadius() {
        return this.mCornerRadius;
    }

    @Override // pl.droidsonroids.gif.transforms.Transform
    public void onBoundsChange(Rect rect) {
        this.mDstRectF.set(rect);
        this.mShader = null;
    }

    @Override // pl.droidsonroids.gif.transforms.Transform
    public void onDraw(Canvas canvas, Paint paint, Bitmap bitmap) {
        if (this.mCornerRadius == 0.0f) {
            canvas.drawBitmap(bitmap, (Rect) null, this.mDstRectF, paint);
            return;
        }
        if (this.mShader == null) {
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            this.mShader = new BitmapShader(bitmap, tileMode, tileMode);
            Matrix matrix = new Matrix();
            RectF rectF = this.mDstRectF;
            matrix.setTranslate(rectF.left, rectF.top);
            matrix.preScale(this.mDstRectF.width() / bitmap.getWidth(), this.mDstRectF.height() / bitmap.getHeight());
            this.mShader.setLocalMatrix(matrix);
        }
        paint.setShader(this.mShader);
        float fWidth = this.gifViewWidth > 0 ? this.mDstRectF.width() / this.gifViewWidth : 1.0f;
        paint.setAntiAlias(true);
        RectF rectF2 = this.mDstRectF;
        float f = this.mCornerRadius * fWidth;
        canvas.drawRoundRect(rectF2, f, f, paint);
    }

    public void setCornerRadius(float f) {
        setCornerRadiusSafely(f);
    }
}
