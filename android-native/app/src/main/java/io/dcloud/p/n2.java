package io.dcloud.p;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.View;
import io.dcloud.p.p2;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class n2 implements p2, p2.a {
    private RectF a;
    private View b;
    private p2.a c;
    private boolean d = false;

    public n2(View view) {
        this.b = view;
    }

    @Override // io.dcloud.p.p2
    public boolean a() {
        return this.d;
    }

    @Override // io.dcloud.p.p2
    public boolean b() {
        if (a()) {
            return false;
        }
        this.d = true;
        a(this.b);
        return true;
    }

    public boolean c() {
        return b(this.b);
    }

    @Override // io.dcloud.p.p2
    public boolean dismiss() {
        if (!a()) {
            return false;
        }
        this.d = false;
        c(this.b);
        return true;
    }

    @Override // io.dcloud.p.p2
    public RectF getFrame() {
        if (this.a == null) {
            this.a = new RectF(0.0f, 0.0f, this.b.getWidth(), this.b.getHeight());
            float x = this.b.getX() + this.b.getPivotX();
            float y = this.b.getY() + this.b.getPivotY();
            Matrix matrix = new Matrix();
            matrix.setTranslate(this.b.getX(), this.b.getY());
            matrix.postScale(this.b.getScaleX(), this.b.getScaleY(), x, y);
            matrix.mapRect(this.a);
        }
        return this.a;
    }

    @Override // io.dcloud.p.p2
    public void a(p2.a aVar) {
        this.c = aVar;
    }

    @Override // io.dcloud.p.p2.a
    public void c(View view) {
        this.a = null;
        view.invalidate();
        p2.a aVar = this.c;
        if (aVar != null) {
            aVar.c(view);
        }
    }

    @Override // io.dcloud.p.p2.a
    public void a(View view) {
        view.invalidate();
        p2.a aVar = this.c;
        if (aVar != null) {
            aVar.a(view);
        }
    }

    @Override // io.dcloud.p.p2
    public void b(p2.a aVar) {
        this.c = null;
    }

    @Override // io.dcloud.p.p2.a
    public boolean b(View view) {
        p2.a aVar = this.c;
        return aVar != null && aVar.b(view);
    }
}
