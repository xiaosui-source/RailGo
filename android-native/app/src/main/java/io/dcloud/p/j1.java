package io.dcloud.p;

import android.app.Activity;
import io.dcloud.sdk.core.entry.DCloudAOLSlot;
import io.dcloud.sdk.core.module.DCBaseAOL;
import io.dcloud.sdk.core.util.MainHandlerUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class j1 extends x4 {
    protected y1 C;

    public j1(Activity activity, int i) {
        super(activity, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e(int i, String str, JSONArray jSONArray) {
        y1 y1Var = this.C;
        if (y1Var != null) {
            y1Var.onError(i, str, jSONArray);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void g(List list) {
        y1 y1Var = this.C;
        if (y1Var != null) {
            y1Var.onLoaded(list);
        }
    }

    public void a(DCloudAOLSlot dCloudAOLSlot, y1 y1Var) {
        super.h();
        a(dCloudAOLSlot);
        this.C = y1Var;
        z2.a().post(this);
        this.w = true;
    }

    @Override // io.dcloud.p.x4
    protected void d(final int i, final String str, final JSONArray jSONArray) {
        b3.b("uniAd-loadError", "code:" + i + ";message:" + str + ";detail:" + String.valueOf(jSONArray));
        if (g()) {
            e();
        }
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.j1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.e(i, str, jSONArray);
            }
        });
    }

    protected List f(List list) {
        ArrayList arrayList = new ArrayList();
        if (list != null && list.size() > 0) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(new i1((DCBaseAOL) it.next(), a()));
            }
        }
        return arrayList;
    }

    @Override // io.dcloud.p.x4
    protected void e(List list) {
        if (g()) {
            e();
        }
        final List listF = f(list);
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.j1$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.g(listF);
            }
        });
    }
}
