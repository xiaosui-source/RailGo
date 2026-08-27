package io.dcloud.p;

import com.taobao.weex.el.parse.Operators;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class f2 {
    public float a;
    public float b;
    public float c;
    public float d;

    public f2(float f, float f2, float f3, float f4) {
        this.a = f;
        this.b = f2;
        this.c = f3;
        this.d = f4;
    }

    public void a(float f, float f2, float f3, float f4) {
        this.a = f;
        this.b = f2;
        this.c = f3;
        this.d = f4;
    }

    public String toString() {
        return "IMGHoming{x=" + this.a + ", y=" + this.b + ", scale=" + this.c + ", rotate=" + this.d + Operators.BLOCK_END;
    }

    public void a(f2 f2Var) {
        this.c *= f2Var.c;
        this.a -= f2Var.a;
        this.b -= f2Var.b;
    }

    public static boolean a(f2 f2Var, f2 f2Var2) {
        return Float.compare(f2Var.d, f2Var2.d) != 0;
    }
}
