package io.dcloud.p;

import android.app.Activity;
import io.dcloud.p.w;
import io.dcloud.sdk.core.adapter.IAdAdapter;
import io.dcloud.sdk.core.entry.DCloudAOLSlot;
import io.dcloud.sdk.core.interfaces.IToggle;
import io.dcloud.sdk.core.module.DCBaseAOLLoader;
import io.dcloud.sdk.core.util.AOLErrorUtil;
import io.dcloud.sdk.core.util.Const;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class x4 extends a5 implements w.a, IToggle {
    private IToggle.CADLoadListener A;
    private boolean B;
    private m1 x;
    protected boolean y;
    private List z;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a extends HashMap {
        a() {
            put(Const.DC_ADN, x4.this);
        }
    }

    public x4(Activity activity, int i) {
        super(activity, i);
        this.y = false;
        this.z = null;
        this.B = false;
    }

    private u4 b(t0 t0Var) {
        Iterator it = t0Var.d().iterator();
        while (it.hasNext()) {
            u4 u4Var = (u4) it.next();
            if (Const.TYPE_GM.equals(u4Var.l()) && u4Var.t()) {
                it.remove();
                return u4Var;
            }
        }
        return null;
    }

    private void c(t0 t0Var) {
        u4 u4VarB;
        IAdAdapter iAdAdapterB = e.b().b(Const.TYPE_GM);
        if (iAdAdapterB == null || !t0Var.b().contains(Const.TYPE_GM) || (u4VarB = b(t0Var)) == null) {
            return;
        }
        a(iAdAdapterB, u4VarB);
    }

    @Override // io.dcloud.p.w, io.dcloud.p.h4
    protected void a(t0 t0Var) {
        int i;
        if (m.f(a()) && (i = this.d) != 14 && i != 5 && i != 7) {
            c(t0Var);
        }
        super.a(t0Var);
    }

    protected void d(int i, String str, JSONArray jSONArray) {
        super.a(i, str, jSONArray);
        if (g()) {
            e();
        }
    }

    protected void e(List list) {
        super.c(list);
        if (g()) {
            e();
        }
    }

    @Override // io.dcloud.p.w
    public boolean g() {
        return this.y;
    }

    @Override // io.dcloud.sdk.core.interfaces.IToggle
    public List getSuccessAds() {
        return this.z;
    }

    protected void h() {
        this.B = false;
        this.x = null;
        this.y = false;
        this.z = null;
        this.A = null;
    }

    @Override // io.dcloud.sdk.core.interfaces.IToggle
    public void setCADLoadListener(IToggle.CADLoadListener cADLoadListener) {
        this.A = cADLoadListener;
    }

    private void a(IAdAdapter iAdAdapter, u4 u4Var) {
        this.y = true;
        DCBaseAOLLoader ad = iAdAdapter.getAd(a(), this.b);
        a0 a0VarA = b0.a().a(Const.TYPE_GM);
        if (a0VarA != null) {
            ad.a(a0VarA.a(), a0VarA.b());
        }
        ad.a(u4Var);
        ad.a(this);
        ad.d(this.n);
        this.b.setRID(this.n);
        ad.b(new a());
    }

    @Override // io.dcloud.p.a5, io.dcloud.p.w
    protected void c(List list) {
        if (this.y) {
            this.z = list;
            if (this.A != null) {
                if (list != null && !list.isEmpty()) {
                    this.A.onLoadSuccess(this.z);
                    return;
                } else {
                    this.A.onLoadFail(-5005, AOLErrorUtil.getErrorMsg(-5005));
                    return;
                }
            }
            return;
        }
        this.w = false;
        e(list);
    }

    @Override // io.dcloud.p.w.a
    public void a(DCBaseAOLLoader dCBaseAOLLoader, List list, m1 m1Var) {
        if (this.y) {
            this.x = m1Var;
            m1Var.f();
            ArrayList arrayList = new ArrayList();
            if (list == null) {
                arrayList.add(dCBaseAOLLoader);
            } else {
                arrayList.addAll(list);
            }
            this.w = false;
            e(arrayList);
        }
    }

    @Override // io.dcloud.p.w.a
    public void a(DCBaseAOLLoader dCBaseAOLLoader, m1 m1Var) {
        if (this.y) {
            this.x = m1Var;
            this.w = false;
            d(-5005, AOLErrorUtil.getErrorMsg(-5005), new JSONArray().put(m1Var.b()));
        }
    }

    @Override // io.dcloud.p.a5, io.dcloud.p.w
    protected void a(int i, String str, JSONArray jSONArray) {
        if (this.y) {
            IToggle.CADLoadListener cADLoadListener = this.A;
            if (cADLoadListener != null) {
                cADLoadListener.onLoadFail(i, jSONArray.toString());
                return;
            }
            return;
        }
        this.w = false;
        d(i, str, jSONArray);
    }

    @Override // io.dcloud.p.x3
    protected void a(int i, String str, String str2, String str3, JSONArray jSONArray, long j) {
        if (this.y) {
            m1 m1Var = this.x;
            if (m1Var == null) {
                return;
            } else {
                jSONArray.put(m1Var.g());
            }
        }
        if (this.B) {
            return;
        }
        this.B = true;
        super.a(i, str, str2, str3, jSONArray, j);
    }

    @Override // io.dcloud.p.a5
    public void a(DCloudAOLSlot dCloudAOLSlot, v2 v2Var) {
        h();
        super.a(dCloudAOLSlot, v2Var);
        this.w = true;
    }
}
