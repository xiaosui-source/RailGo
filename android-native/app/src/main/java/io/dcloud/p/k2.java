package io.dcloud.p;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class k2 {
    protected Path a;
    private int b;
    private float c;
    private j2 d;

    public k2() {
        this(new Path());
    }

    public int a() {
        return this.b;
    }

    public j2 b() {
        return this.d;
    }

    public Path c() {
        return this.a;
    }

    public float d() {
        return this.c;
    }

    public k2(Path path) {
        this(path, j2.DOODLE);
    }

    public void a(int i) {
        this.b = i;
    }

    public void b(Canvas canvas, Paint paint) {
        if (this.d == j2.MOSAIC) {
            paint.setStrokeWidth(this.c);
            canvas.drawPath(this.a, paint);
        }
    }

    public k2(Path path, j2 j2Var) {
        this(path, j2Var, -65536);
    }

    public void a(j2 j2Var) {
        this.d = j2Var;
    }

    public k2(Path path, j2 j2Var, int i) {
        this(path, j2Var, i, 72.0f);
    }

    public void a(float f) {
        this.c = f;
    }

    public k2(Path path, j2 j2Var, int i, float f) {
        this.b = -65536;
        this.c = 72.0f;
        j2 j2Var2 = j2.NONE;
        this.a = path;
        this.d = j2Var;
        this.b = i;
        this.c = f;
        if (j2Var == j2.MOSAIC) {
            path.setFillType(Path.FillType.EVEN_ODD);
        }
    }

    public void a(Canvas canvas, Paint paint) {
        if (this.d == j2.DOODLE) {
            paint.setColor(this.b);
            paint.setStrokeWidth(this.c);
            canvas.drawPath(this.a, paint);
        }
    }

    public void a(Matrix matrix) {
        this.a.transform(matrix);
    }
}
