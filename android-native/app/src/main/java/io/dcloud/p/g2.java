package io.dcloud.p;

import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class g2 extends ValueAnimator {
    private boolean a = false;
    private h2 b;

    public g2() {
        setInterpolator(new AccelerateDecelerateInterpolator());
    }

    public void a(f2 f2Var, f2 f2Var2) {
        setObjectValues(f2Var, f2Var2);
        this.a = f2.a(f2Var, f2Var2);
    }

    @Override // android.animation.ValueAnimator
    public void setObjectValues(Object... objArr) {
        super.setObjectValues(objArr);
        if (this.b == null) {
            this.b = new h2();
        }
        setEvaluator(this.b);
    }

    public boolean a() {
        return this.a;
    }
}
