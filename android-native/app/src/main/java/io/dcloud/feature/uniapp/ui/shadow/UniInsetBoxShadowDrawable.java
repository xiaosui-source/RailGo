package io.dcloud.feature.uniapp.ui.shadow;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class UniInsetBoxShadowDrawable extends Drawable {
    private static final int BOTTOM_TO_TOP = 3;
    private static final int LEFT_TO_RIGHT = 0;
    private static final int RIGHT_TO_LEFT = 2;
    private static final int TOP_TO_BOTTOM = 1;
    private float blurRadius;
    private float height;
    private Paint paint;
    private float[] radii;
    private int shadowColor;
    private float shadowXSize;
    private float shadowYSize;
    private float width;
    private Shader[] shades = new Shader[4];
    private Path[] paths = new Path[4];

    public UniInsetBoxShadowDrawable(int i, int i2, float f, float f2, float f3, float f4, int i3, float[] fArr) {
        this.blurRadius = f3;
        this.shadowColor = i3;
        this.width = i + (f * 2.0f);
        this.height = i2 + (2.0f * f2);
        this.shadowXSize = f + f4;
        this.shadowYSize = f2 + f4;
        this.radii = fArr;
        setBounds(0, 0, i, i2);
        prepare();
    }

    private void prepare() {
        PointF pointF = new PointF(0.0f, 0.0f);
        PointF pointF2 = new PointF(this.width, 0.0f);
        PointF pointF3 = new PointF(pointF2.x, this.height);
        PointF pointF4 = new PointF(pointF.x, pointF3.y);
        PointF pointF5 = new PointF(this.shadowXSize, this.shadowYSize);
        PointF pointF6 = new PointF(pointF2.x - this.shadowXSize, pointF5.y);
        PointF pointF7 = new PointF(pointF6.x, pointF3.y - this.shadowYSize);
        PointF pointF8 = new PointF(pointF5.x, pointF7.y);
        float f = pointF5.x;
        float f2 = f - this.blurRadius;
        float f3 = pointF5.y;
        int i = this.shadowColor;
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        LinearGradient linearGradient = new LinearGradient(f2, f3, f, f3, i, 0, tileMode);
        float f4 = pointF5.x;
        float f5 = pointF5.y;
        LinearGradient linearGradient2 = new LinearGradient(f4, f5 - this.blurRadius, f4, f5, this.shadowColor, 0, tileMode);
        float f6 = pointF7.x;
        float f7 = f6 + this.blurRadius;
        float f8 = pointF7.y;
        LinearGradient linearGradient3 = new LinearGradient(f7, f8, f6, f8, this.shadowColor, 0, tileMode);
        float f9 = pointF7.x;
        float f10 = pointF7.y;
        LinearGradient linearGradient4 = new LinearGradient(f9, f10 + this.blurRadius, f9, f10, this.shadowColor, 0, tileMode);
        Shader[] shaderArr = this.shades;
        shaderArr[0] = linearGradient;
        shaderArr[1] = linearGradient2;
        shaderArr[2] = linearGradient3;
        shaderArr[3] = linearGradient4;
        Path path = new Path();
        path.moveTo(pointF.x, pointF.y);
        path.lineTo(pointF5.x, pointF5.y);
        path.lineTo(pointF8.x, pointF8.y);
        path.lineTo(pointF4.x, pointF4.y);
        path.close();
        Path path2 = new Path();
        path2.moveTo(pointF.x, pointF.y);
        path2.lineTo(pointF2.x, pointF2.y);
        path2.lineTo(pointF6.x, pointF6.y);
        path2.lineTo(pointF5.x, pointF5.y);
        path2.close();
        Path path3 = new Path();
        path3.moveTo(pointF2.x, pointF2.y);
        path3.lineTo(pointF3.x, pointF3.y);
        path3.lineTo(pointF7.x, pointF7.y);
        path3.lineTo(pointF6.x, pointF6.y);
        path3.close();
        Path path4 = new Path();
        path4.moveTo(pointF4.x, pointF4.y);
        path4.lineTo(pointF3.x, pointF3.y);
        path4.lineTo(pointF7.x, pointF7.y);
        path4.lineTo(pointF8.x, pointF8.y);
        path4.close();
        Path[] pathArr = this.paths;
        pathArr[0] = path;
        pathArr[1] = path2;
        pathArr[2] = path3;
        pathArr[3] = path4;
        Paint paint = new Paint();
        this.paint = paint;
        paint.setAntiAlias(true);
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(this.shadowColor);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Rect clipBounds = canvas.getClipBounds();
        Path path = new Path();
        path.addRoundRect(new RectF(clipBounds), this.radii, Path.Direction.CCW);
        canvas.clipPath(path);
        canvas.translate(clipBounds.left, clipBounds.top);
        for (int i = 0; i < 4; i++) {
            Shader shader = this.shades[i];
            Path path2 = this.paths[i];
            this.paint.setShader(shader);
            canvas.drawPath(path2, this.paint);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -1;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }
}
