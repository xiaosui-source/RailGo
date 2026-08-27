package io.dcloud.p;

import android.app.Activity;
import io.dcloud.p.m;
import io.dcloud.sdk.core.entry.DCloudAOLSlot;
import io.dcloud.sdk.core.module.DCBaseAOL;
import io.dcloud.sdk.core.module.DCBaseAOLLoader;
import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class f3 implements t1, j, y {
    private y4 a;
    private x d;
    private int g;
    private j h;
    private int i;
    protected final DCloudAOLSlot n;
    protected final Activity o;
    private boolean b = false;
    private boolean c = false;
    private boolean e = false;
    private boolean f = false;
    private boolean j = false;
    private boolean k = false;
    private boolean l = false;
    private boolean m = false;
    private final List p = new ArrayList();

    public f3(DCloudAOLSlot dCloudAOLSlot, Activity activity) {
        this.n = dCloudAOLSlot;
        this.o = activity;
    }

    private void m() {
        boolean z = this.e;
        if (z && this.b) {
            if (l()) {
                m.a aVarA = m.a(this.n.getCount(), this.d.e(), this.a.e());
                if (aVarA == null) {
                    n();
                    return;
                }
                this.k = true;
                for (DCBaseAOL dCBaseAOL : aVarA.d) {
                    if (dCBaseAOL.isSlotSupportBidding()) {
                        dCBaseAOL.biddingSuccess(aVarA.b, aVarA.c);
                    }
                }
                this.p.addAll(aVarA.d);
            } else if (this.d.a() >= this.a.a()) {
                this.j = true;
            } else {
                this.k = true;
                this.d.b(this.a.a());
            }
            o();
            return;
        }
        if (z && this.c) {
            this.j = true;
            this.p.addAll(this.d.e());
            o();
            return;
        }
        boolean z2 = this.f;
        if (z2 && this.b) {
            this.k = true;
            this.p.addAll(this.a.e());
            o();
        } else if (z2 && this.c) {
            n();
        }
    }

    @Override // io.dcloud.p.t1
    public void a(u4 u4Var) {
    }

    public void a(y4 y4Var) {
        this.a = y4Var;
    }

    @Override // io.dcloud.p.t1
    public boolean b() {
        return this.j;
    }

    @Override // io.dcloud.p.t1
    public void c(int i) {
    }

    @Override // io.dcloud.p.t1
    public void d(int i) {
        this.g = i;
    }

    @Override // io.dcloud.p.t1
    public boolean e(int i) {
        return true;
    }

    @Override // io.dcloud.p.y
    public void f() {
        if (b()) {
            this.d.f();
        }
    }

    @Override // io.dcloud.p.t1
    public boolean g() {
        y4 y4Var = this.a;
        return y4Var != null && this.d != null && y4Var.g() && this.d.g();
    }

    @Override // io.dcloud.p.t1
    public void h() {
        if (this.l) {
            n();
            return;
        }
        x xVar = this.d;
        if (xVar != null) {
            xVar.a(this);
            this.d.h();
        }
        y4 y4Var = this.a;
        if (y4Var != null) {
            y4Var.a(this);
            this.a.h();
        }
    }

    @Override // io.dcloud.p.j
    public DCBaseAOLLoader i() {
        return null;
    }

    @Override // io.dcloud.p.t1
    public void j() {
        if (this.j) {
            this.d.j();
        }
        if (this.k) {
            this.a.j();
        }
    }

    @Override // io.dcloud.p.t1
    public void k() {
        this.l = true;
        x xVar = this.d;
        if (xVar != null) {
            xVar.k();
        }
        y4 y4Var = this.a;
        if (y4Var != null) {
            y4Var.k();
        }
    }

    boolean l() {
        int i = this.i;
        return i == 10 || i == 4 || i == 5;
    }

    protected void n() {
        if (this.m) {
            return;
        }
        this.m = true;
        j jVar = this.h;
        if (jVar != null) {
            jVar.b(this);
        }
    }

    protected void o() {
        if (this.m) {
            return;
        }
        this.m = true;
        j jVar = this.h;
        if (jVar != null) {
            jVar.a(this);
        }
    }

    public String toString() {
        return "Bidding:" + this.d.toString() + ",Usual:" + this.a.toString();
    }

    public void a(x xVar) {
        this.d = xVar;
    }

    @Override // io.dcloud.p.j
    public void b(t1 t1Var) {
        if (t1Var == this.a) {
            this.c = true;
        } else if (t1Var == this.d) {
            this.f = true;
        }
        m();
    }

    @Override // io.dcloud.p.t1
    public int c() {
        return this.g;
    }

    @Override // io.dcloud.p.t1
    public List d() {
        ArrayList arrayList = new ArrayList();
        x xVar = this.d;
        if (xVar != null) {
            arrayList.addAll(xVar.d());
        }
        y4 y4Var = this.a;
        if (y4Var != null) {
            arrayList.addAll(y4Var.d());
        }
        return arrayList;
    }

    @Override // io.dcloud.p.t1
    public List e() {
        if (l()) {
            return this.p;
        }
        if (this.j) {
            return this.d.e();
        }
        if (this.k) {
            return this.a.e();
        }
        return null;
    }

    @Override // io.dcloud.p.t1
    public void a(j jVar) {
        this.h = jVar;
    }

    @Override // io.dcloud.p.t1
    public int a() {
        if (l()) {
            if (this.p.isEmpty()) {
                return -1;
            }
            m.a(this.p);
            return ((DCBaseAOL) this.p.get(r0.size() - 1)).getBiddingECPM();
        }
        if (this.j) {
            return this.d.a();
        }
        if (this.k) {
            return this.a.a();
        }
        return -1;
    }

    @Override // io.dcloud.p.y
    public void b(int i) {
        if (b()) {
            this.d.b(i);
        }
    }

    @Override // io.dcloud.p.t1
    public void a(int i) {
        this.i = i;
        x xVar = this.d;
        if (xVar != null) {
            xVar.a(i);
        }
        y4 y4Var = this.a;
        if (y4Var != null) {
            y4Var.a(i);
        }
    }

    @Override // io.dcloud.p.t1
    public void a(String str) {
        x xVar = this.d;
        if (xVar != null) {
            xVar.a(str);
        }
        y4 y4Var = this.a;
        if (y4Var != null) {
            y4Var.a(str);
        }
    }

    @Override // io.dcloud.p.j
    public void a(t1 t1Var) {
        if (t1Var == this.a) {
            this.b = true;
        } else if (t1Var == this.d) {
            this.e = true;
        }
        m();
    }
}
