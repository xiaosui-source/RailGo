package io.dcloud.feature.internal.splash;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class a extends View {
    private static float E = 2.0f;
    Paint A;
    Path B;
    int C;
    PaintFlagsDrawFilter D;
    private DisplayMetrics a;
    Bitmap b;
    int c;
    int d;
    int e;
    int f;
    int g;
    int h;
    int i;
    int j;
    int k;
    int l;
    int m;
    int n;
    int o;
    int p;
    int q;
    int r;
    int s;
    int t;
    int u;
    int v;
    int w;
    int x;
    RectF y;
    RectF z;

    public a(Context context, boolean z) {
        super(context);
        this.b = null;
        this.A = new Paint();
        this.B = new Path();
        this.C = 0;
        this.D = null;
        if (z) {
            E = 6.0f;
        }
        this.D = new PaintFlagsDrawFilter(0, 3);
    }

    private void b(Canvas canvas) {
        this.A.reset();
        this.A.setAntiAlias(true);
        this.A.setStyle(Paint.Style.STROKE);
        this.A.setStrokeWidth(this.h);
        this.A.setColor(this.w);
        canvas.drawArc(this.z, this.t, this.u, false, this.A);
        int i = (int) (this.u + E);
        this.u = i;
        if (i > 360) {
            this.u = i - 360;
        }
        invalidate();
    }

    private void c(Canvas canvas) {
        this.A.reset();
        Bitmap bitmap = this.b;
        if (bitmap == null || bitmap.isRecycled()) {
            this.A.setColor(-1118482);
            this.A.setAntiAlias(true);
            this.A.setStyle(Paint.Style.FILL);
            RectF rectF = this.y;
            float fWidth = rectF.left + (rectF.width() / 2.0f);
            RectF rectF2 = this.y;
            canvas.drawCircle(fWidth, rectF2.top + (rectF2.height() / 2.0f), this.y.width() / 2.0f, this.A);
            return;
        }
        canvas.save();
        try {
            canvas.clipPath(this.B);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.A.setAntiAlias(true);
        canvas.setDrawFilter(this.D);
        canvas.drawBitmap(this.b, (Rect) null, this.y, this.A);
        canvas.restore();
        this.A.setStrokeWidth((this.g * 4) + 3);
        this.A.setStyle(Paint.Style.STROKE);
        this.A.setAntiAlias(true);
        this.A.setColor(this.C);
        RectF rectF3 = this.y;
        float fWidth2 = rectF3.left + (rectF3.width() / 2.0f);
        RectF rectF4 = this.y;
        canvas.drawCircle(fWidth2, rectF4.top + (rectF4.height() / 2.0f), ((this.y.width() / 2.0f) + (r0 / 2)) - (3 / 8), this.A);
    }

    float a(float f) {
        if (this.a == null) {
            this.a = getResources().getDisplayMetrics();
        }
        return TypedValue.applyDimension(1, f, this.a);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        c(canvas);
        a(canvas);
        b(canvas);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(this.c, this.d);
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        super.setBackgroundColor(i);
        this.C = i;
    }

    public void setBitmap(Bitmap bitmap) {
        Bitmap bitmap2 = this.b;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.b.recycle();
        }
        this.b = bitmap;
    }

    public void a(Bitmap bitmap, int i, int i2, int i3, int i4, int i5) {
        int left = getLeft();
        int top = getTop();
        this.b = bitmap;
        this.c = i;
        this.d = i2;
        this.g = i3;
        int i6 = i3 * 8;
        int i7 = i - i6;
        this.e = i7;
        int i8 = i2 - i6;
        this.f = i8;
        this.l = ((i - i7) / 2) + left;
        this.m = ((i2 - i8) / 2) + top;
        this.y = new RectF(this.l, this.m, r6 + this.e, r8 + this.f);
        this.B.reset();
        this.B.addRoundRect(this.y, this.l + (this.e / 2), this.m + (this.f / 2), Path.Direction.CCW);
        int i9 = this.c;
        int i10 = i9 / 2;
        int i11 = this.g;
        this.k = i10 - i11;
        this.w = i5;
        this.x = i4;
        this.i = i10 + left;
        int i12 = this.d;
        this.j = (i12 / 2) + top;
        this.t = 270;
        this.v = 270;
        this.h = i11;
        int i13 = left + i11;
        this.n = i13;
        int i14 = top + i11;
        this.o = i14;
        int i15 = i11 * 2;
        int i16 = i9 - i15;
        this.r = i16;
        int i17 = i12 - i15;
        this.s = i17;
        this.p = i13 + i16;
        this.q = i14 + i17;
        this.z = new RectF(this.n, this.o, this.p, this.q);
    }

    private void a(Canvas canvas) {
        this.A.reset();
        this.A.setStrokeWidth(this.g);
        this.A.setStyle(Paint.Style.STROKE);
        this.A.setAntiAlias(true);
        this.A.setColor(this.x);
        canvas.drawCircle(this.i, this.j, this.k, this.A);
    }
}
