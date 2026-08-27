package io.dcloud.p;

import android.animation.TypeEvaluator;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class h2 implements TypeEvaluator {
    private f2 a;

    @Override // android.animation.TypeEvaluator
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public f2 evaluate(float f, f2 f2Var, f2 f2Var2) {
        float f2 = f2Var.a;
        float f3 = f2 + ((f2Var2.a - f2) * f);
        float f4 = f2Var.b;
        float f5 = f4 + ((f2Var2.b - f4) * f);
        float f6 = f2Var.c;
        float f7 = f6 + ((f2Var2.c - f6) * f);
        float f8 = f2Var.d;
        float f9 = f8 + (f * (f2Var2.d - f8));
        f2 f2Var3 = this.a;
        if (f2Var3 == null) {
            this.a = new f2(f3, f5, f7, f9);
        } else {
            f2Var3.a(f3, f5, f7, f9);
        }
        return this.a;
    }
}
