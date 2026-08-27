package io.dcloud.p;

import android.app.Activity;
import android.text.TextUtils;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.ISysEventListener;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.util.BaseInfo;
import java.util.ArrayList;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
class a4 {
    r a;
    private ArrayList b = new ArrayList();
    private ArrayList c;

    a4(r rVar) {
        this.a = null;
        ArrayList<String> arrayList = new ArrayList<>();
        BaseInfo.sRunningApp = arrayList;
        this.c = arrayList;
        this.a = rVar;
    }

    boolean a(c5 c5Var, ISysEventListener.SysEventType sysEventType, Object obj) {
        boolean z = c5Var == null;
        int size = this.b.size();
        int i = size - 1;
        c5 c5Var2 = null;
        boolean zOnExecute = false;
        while (true) {
            if (i < 0) {
                break;
            }
            c5 c5Var3 = (c5) this.b.get(i);
            if (!z ? c5Var3 == c5Var : z) {
                zOnExecute |= c5Var3.onExecute(sysEventType, obj);
                if (zOnExecute && !c5.a(sysEventType)) {
                    c5Var2 = c5Var3;
                    break;
                }
                c5Var2 = c5Var3;
            }
            i--;
        }
        if (zOnExecute || !sysEventType.equals(ISysEventListener.SysEventType.onKeyUp) || size <= 1 || c5Var2 == null || ((Integer) ((Object[]) obj)[0]).intValue() != 4) {
            return zOnExecute;
        }
        this.a.processEvent(IMgr.MgrType.WindowMgr, 20, c5Var2);
        return true;
    }

    c5 b(String str) {
        c5 c5Var;
        ArrayList arrayList = this.b;
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                c5Var = null;
                break;
            }
            Object obj = arrayList.get(i);
            i++;
            c5Var = (c5) obj;
            if (TextUtils.equals(c5Var.obtainAppId(), str)) {
                break;
            }
        }
        Logger.d("AppCache", "removeWebApp " + c5Var + ";mAppIdList=" + this.c);
        this.b.remove(c5Var);
        this.c.remove(str);
        return c5Var;
    }

    public c5 c() {
        ArrayList arrayList = this.b;
        if (arrayList != null && arrayList.size() == 1) {
            return (c5) this.b.get(0);
        }
        ArrayList arrayList2 = this.b;
        if (arrayList2 == null || arrayList2.size() < 1) {
            return null;
        }
        c5 c5Var = (c5) this.b.get(0);
        long j = c5Var.h1;
        for (int i = 1; i < this.b.size(); i++) {
            c5 c5Var2 = (c5) this.b.get(i);
            long j2 = c5Var2.h1;
            if (j < j2) {
                c5Var = c5Var2;
                j = j2;
            }
        }
        return c5Var;
    }

    public c5 d() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        c5 c5Var = null;
        for (int i = 0; i < this.b.size(); i++) {
            c5 c5Var2 = (c5) this.b.get(i);
            long j = c5Var2.h1;
            if (j < jCurrentTimeMillis) {
                c5Var = c5Var2;
                jCurrentTimeMillis = j;
            }
        }
        return c5Var;
    }

    protected int e() {
        return this.b.size();
    }

    protected c5 b() {
        long j = 0;
        c5 c5Var = null;
        for (int size = this.b.size() - 1; size >= 0; size--) {
            c5 c5Var2 = (c5) this.b.get(size);
            if (c5Var2.u == 3) {
                long j2 = c5Var2.h1;
                if (j2 > j) {
                    c5Var = c5Var2;
                    j = j2;
                }
            }
        }
        return c5Var;
    }

    protected c5 a(String str) {
        int iIndexOf = this.c.indexOf(str);
        if (iIndexOf >= 0) {
            return (c5) this.b.get(iIndexOf);
        }
        return null;
    }

    void a(String str, c5 c5Var) {
        this.c.add(str);
        this.b.add(c5Var);
    }

    protected c5 a(Activity activity, c5 c5Var) {
        if (this.b.contains(c5Var)) {
            return null;
        }
        System.currentTimeMillis();
        if (this.b.size() >= BaseInfo.s_Runing_App_Count_Max) {
            return d();
        }
        return null;
    }

    void a() {
        this.b.clear();
        this.c.clear();
    }
}
