package io.dcloud.p;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import io.dcloud.p.b2;
import java.lang.reflect.Array;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class c2 implements b2 {
    RectF e = new RectF();
    RectF f = new RectF();
    RectF g = new RectF();
    RectF h = new RectF();
    RectF i = new RectF();
    float[] j = new float[16];
    float[] k = new float[32];
    float[][] l = (float[][]) Array.newInstance((Class<?>) Float.TYPE, 2, 4);
    private boolean m = false;
    private boolean n = true;
    boolean o = false;
    private boolean p = false;
    Matrix q = new Matrix();
    Path r = new Path();
    Paint s;
    public int t;
    private float[] u;
    private boolean v;

    public c2() {
        Paint paint = new Paint(1);
        this.s = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.s.setStrokeCap(Paint.Cap.SQUARE);
        this.t = 0;
        this.u = new float[]{1.0f, 1.0f};
        this.v = false;
    }

    public void a(RectF rectF, float f) {
        RectF rectF2 = new RectF();
        this.q.setRotate(f, rectF.centerX(), rectF.centerY());
        this.q.mapRect(rectF2, rectF);
        c(rectF2.width(), rectF2.height());
    }

    public boolean b() {
        this.f.set(this.e);
        this.g.set(this.e);
        r2.a(this.h, this.g, 60.0f);
        boolean z = !this.g.equals(this.f);
        this.p = z;
        return z;
    }

    void c(float f, float f2) {
        c(true);
        if (this.v) {
            float[] fArr = this.u;
            float f3 = fArr[0];
            float f4 = fArr[1];
            float f5 = (f / f3) * f4;
            if (f5 > f2) {
                f = (f2 / f4) * f3;
            } else {
                f2 = f5;
            }
        }
        this.e.set(0.0f, 0.0f, f, f2);
        r2.a(this.h, this.e, 60.0f);
        this.g.set(this.e);
    }

    public void d(float f, float f2) {
        this.i.set(0.0f, 0.0f, f, f2);
        this.h.set(0.0f, this.t, f, f2 * 0.85f);
        if (this.e.isEmpty()) {
            return;
        }
        r2.a(this.h, this.e);
        this.g.set(this.e);
    }

    public void e(float f, float f2) {
        if (f <= 0.0f || f2 <= 0.0f) {
            return;
        }
        this.u = new float[]{f, f2};
        this.v = true;
    }

    public boolean f() {
        return this.n;
    }

    public boolean e() {
        return this.v;
    }

    public void a(float f) {
        if (this.p) {
            RectF rectF = this.e;
            RectF rectF2 = this.f;
            float f2 = rectF2.left;
            RectF rectF3 = this.g;
            float f3 = f2 + ((rectF3.left - f2) * f);
            float f4 = rectF2.top;
            float f5 = f4 + ((rectF3.top - f4) * f);
            float f6 = rectF2.right;
            float f7 = f6 + ((rectF3.right - f6) * f);
            float f8 = rectF2.bottom;
            rectF.set(f3, f5, f7, f8 + ((rectF3.bottom - f8) * f));
        }
    }

    public void b(boolean z) {
        this.p = z;
    }

    public RectF b(float f, float f2) {
        RectF rectF = new RectF(this.e);
        rectF.offset(f, f2);
        return rectF;
    }

    public void a(boolean z) {
        this.m = z;
    }

    public boolean d() {
        return this.p;
    }

    public RectF a() {
        return this.g;
    }

    public void d(boolean z) {
        this.o = z;
    }

    public void a(Canvas canvas) {
        if (this.n) {
            return;
        }
        int i = 0;
        float[] fArr = {this.e.width(), this.e.height()};
        for (int i2 = 0; i2 < this.l.length; i2++) {
            int i3 = 0;
            while (true) {
                float[] fArr2 = this.l[i2];
                if (i3 < fArr2.length) {
                    fArr2[i3] = fArr[i2] * b2.a[i3];
                    i3++;
                }
            }
        }
        int i4 = 0;
        while (true) {
            float[] fArr3 = this.j;
            if (i4 >= fArr3.length) {
                break;
            }
            fArr3[i4] = this.l[i4 & 1][(1935858840 >>> (i4 << 1)) & 3];
            i4++;
        }
        while (true) {
            float[] fArr4 = this.k;
            if (i < fArr4.length) {
                float f = this.l[i & 1][(179303760 >>> i) & 1];
                float[] fArr5 = b2.c;
                byte b = b2.d[i];
                fArr4[i] = f + fArr5[b & 3] + b2.b[b >> 2];
                i++;
            } else {
                RectF rectF = this.e;
                canvas.translate(rectF.left, rectF.top);
                this.s.setStyle(Paint.Style.STROKE);
                this.s.setColor(-2130706433);
                this.s.setStrokeWidth(3.0f);
                canvas.drawLines(this.j, this.s);
                RectF rectF2 = this.e;
                canvas.translate(-rectF2.left, -rectF2.top);
                this.s.setColor(-1);
                this.s.setStrokeWidth(4.0f);
                canvas.drawRect(this.e, this.s);
                RectF rectF3 = this.e;
                canvas.translate(rectF3.left, rectF3.top);
                this.s.setColor(-1);
                this.s.setStrokeWidth(10.0f);
                canvas.drawLines(this.k, this.s);
                return;
            }
        }
    }

    public boolean c() {
        return this.m;
    }

    public void c(boolean z) {
        this.n = z;
    }

    public b2.a a(float f, float f2) {
        if (!b2.a.a(this.e, -48.0f, f, f2) || b2.a.a(this.e, 48.0f, f, f2)) {
            return null;
        }
        float[] fArrA = b2.a.a(this.e, 0.0f, 0.0f);
        float[] fArr = {f, f2};
        int i = 0;
        for (int i2 = 0; i2 < fArrA.length; i2++) {
            if (Math.abs(fArrA[i2] - fArr[i2 >> 1]) < 48.0f) {
                i |= 1 << i2;
            }
        }
        b2.a aVarA = b2.a.a(i);
        if (aVarA != null) {
            this.p = false;
        }
        return aVarA;
    }

    public void a(b2.a aVar, float f, float f2) {
        boolean z = this.v;
        aVar.a(z ? this.g : this.h, this.e, f, f2, this.u, z);
    }

    public void a(int i) {
        this.t = i;
    }
}
