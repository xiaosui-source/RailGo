package io.dcloud.p;

import android.app.Activity;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.api.custom.UniAdCustomAdapter;
import io.dcloud.p.w;
import io.dcloud.sdk.core.adapter.IAdAdapter;
import io.dcloud.sdk.core.entry.DCloudAOLSlot;
import io.dcloud.sdk.core.module.DCBaseAOL;
import io.dcloud.sdk.core.module.DCBaseAOLLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class y4 implements t1, w.a {
    protected DCBaseAOL j;
    protected int o;
    private j q;
    protected final DCloudAOLSlot r;
    protected final Activity s;
    private boolean a = false;
    protected volatile boolean b = false;
    private final List c = new ArrayList();
    private final AtomicInteger d = new AtomicInteger(0);
    private final b e = new b();
    protected List f = new ArrayList();
    protected List g = new ArrayList();
    protected List h = new ArrayList();
    protected Map i = new HashMap();
    protected List k = new ArrayList();
    private final List l = new ArrayList();
    private final Map m = new HashMap();
    private final List n = new ArrayList();
    protected boolean p = false;
    protected int t = Integer.MIN_VALUE;
    private String u = "";

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    private static class b {
        private Integer a;
        private Integer b;

        private b() {
            this.a = 0;
            this.b = 0;
        }

        public boolean a(Integer num) {
            return (num.compareTo(this.a) >= 0) && (num.compareTo(this.b) <= 0);
        }

        public boolean b(Integer num) {
            return num.compareTo(this.b) <= 0;
        }

        public void c(Integer num) {
            if (a(num)) {
                return;
            }
            if (num.intValue() > this.b.intValue()) {
                this.b = num;
            } else if (num.intValue() < this.a.intValue()) {
                this.a = num;
            }
        }

        public String toString() {
            return String.format("[%s, %s]", this.a, this.b);
        }
    }

    public y4(DCloudAOLSlot dCloudAOLSlot, Activity activity) {
        this.r = dCloudAOLSlot;
        this.s = activity;
    }

    private void f(int i) {
        AtomicInteger atomicInteger = (AtomicInteger) this.m.get(Integer.valueOf(i));
        if (atomicInteger == null) {
            this.m.put(Integer.valueOf(i), new AtomicInteger(0));
        } else {
            atomicInteger.decrementAndGet();
        }
    }

    private void i() throws IllegalAccessException, InstantiationException {
        b3.d("ordered request list");
        n();
        if (g()) {
            p();
        }
    }

    private void n() throws IllegalAccessException, InstantiationException {
        if (this.p || this.b) {
            return;
        }
        if (this.c.size() <= 0) {
            p();
            return;
        }
        u4 u4Var = (u4) this.c.remove(0);
        DCBaseAOLLoader dCBaseAOLLoaderB = b(u4Var);
        if (dCBaseAOLLoaderB == null) {
            b3.b("load sub slot fail cfg:" + u4Var.toString());
            this.d.decrementAndGet();
            n();
            return;
        }
        dCBaseAOLLoaderB.a(this);
        this.h.add(dCBaseAOLLoaderB);
        dCBaseAOLLoaderB.a(u4Var);
        dCBaseAOLLoaderB.d(this.u);
        dCBaseAOLLoaderB.b((Map) null);
    }

    private void o() throws IllegalAccessException, InstantiationException {
        if (!b()) {
            ArrayList arrayList = new ArrayList();
            if (this.i.size() > 0) {
                if (this.l.size() <= 0) {
                    for (List list : this.i.values()) {
                        if (list != null) {
                            arrayList.addAll(list);
                        }
                        if (arrayList.size() >= this.r.getCount()) {
                            break;
                        }
                    }
                } else {
                    for (Integer num : this.l) {
                        for (DCBaseAOL dCBaseAOL : this.f) {
                            if (dCBaseAOL.r() == num.intValue()) {
                                List list2 = (List) this.i.get(dCBaseAOL);
                                if (list2 != null) {
                                    arrayList.addAll(list2);
                                }
                                if (arrayList.size() >= this.r.getCount()) {
                                    break;
                                }
                            }
                        }
                        if (a(num) > 0) {
                            break;
                        }
                    }
                }
            }
            if (!arrayList.isEmpty()) {
                if (arrayList.size() >= this.r.getCount()) {
                    this.k.addAll(arrayList.subList(0, this.r.getCount()));
                    q();
                } else if (g()) {
                    this.k.addAll(arrayList);
                    q();
                }
            }
        }
        r();
    }

    @Override // io.dcloud.p.t1
    public void a(u4 u4Var) {
        this.e.c(Integer.valueOf(u4Var.c()));
        this.c.add(u4Var);
    }

    @Override // io.dcloud.p.t1
    public boolean b() {
        return false;
    }

    @Override // io.dcloud.p.t1
    public int c() {
        return this.t;
    }

    @Override // io.dcloud.p.t1
    public void d(int i) {
        this.t = i;
    }

    @Override // io.dcloud.p.t1
    public boolean e(int i) {
        return this.e.b(Integer.valueOf(i));
    }

    @Override // io.dcloud.p.t1
    public boolean g() {
        return this.p || this.d.get() <= 0;
    }

    @Override // io.dcloud.p.t1
    public void h() {
        if (this.c.isEmpty()) {
            p();
            return;
        }
        if (this.p) {
            p();
            return;
        }
        b(Operators.PLUS);
        if (b()) {
            a(false);
            return;
        }
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        if (this.c.size() <= 1) {
            this.d.incrementAndGet();
            i();
            return;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (u4 u4Var : this.c) {
            this.d.incrementAndGet();
            if (u4Var.s()) {
                try {
                    int iP = u4Var.p();
                    if (iP > 0) {
                        jSONObject.put(u4Var.n(), iP);
                    }
                    int iG = u4Var.g();
                    if (iG > 0) {
                        JSONArray jSONArray = new JSONArray();
                        jSONArray.put(u4Var.l());
                        jSONArray.put(iG);
                        jSONObject2.put(u4Var.n(), jSONArray);
                    }
                } catch (Exception unused) {
                }
            }
            linkedHashMap.put(u4Var.n(), u4Var);
        }
        if (jSONObject.length() <= 0 && jSONObject2.length() <= 0) {
            if (this.a) {
                i();
                return;
            } else {
                a(false);
                return;
            }
        }
        List listA = m.a(this.s, new ArrayList(linkedHashMap.keySet()), this.r.getAdpid(), jSONObject, jSONObject2);
        this.c.clear();
        Iterator it = listA.iterator();
        while (it.hasNext()) {
            u4 u4Var2 = (u4) linkedHashMap.get((String) it.next());
            if (u4Var2 != null) {
                this.c.add(u4Var2);
            }
        }
        if (this.a) {
            i();
        } else {
            a(true);
        }
    }

    @Override // io.dcloud.p.t1
    public void j() {
        if (this instanceof x) {
            return;
        }
        this.a = true;
    }

    @Override // io.dcloud.p.t1
    public void k() {
        this.p = true;
        if (this.b) {
            return;
        }
        l();
        if (this.f.isEmpty()) {
            p();
            return;
        }
        Collections.sort(this.f, new Comparator() { // from class: io.dcloud.p.y4$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return y4.a((DCBaseAOL) obj, (DCBaseAOL) obj2);
            }
        });
        if (!m()) {
            this.j = (DCBaseAOL) this.f.get(0);
            q();
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = this.f.iterator();
        while (it.hasNext()) {
            List list = (List) this.i.get((DCBaseAOL) it.next());
            if (list != null) {
                arrayList.addAll(list);
            }
            if (arrayList.size() >= this.r.getCount()) {
                break;
            }
        }
        if (arrayList.size() <= 0) {
            p();
            return;
        }
        if (arrayList.size() > this.r.getCount()) {
            this.k.addAll(arrayList.subList(0, this.r.getCount()));
        } else {
            this.k.addAll(arrayList);
        }
        q();
    }

    protected void l() {
        if (this.h.isEmpty()) {
            return;
        }
        this.h.removeAll(this.f);
        this.h.removeAll(this.g);
        Iterator it = this.h.iterator();
        while (it.hasNext()) {
            this.n.add(((DCBaseAOLLoader) it.next()).y());
        }
        this.h.clear();
    }

    boolean m() {
        int i = this.o;
        return i == 10 || i == 4 || i == 5;
    }

    protected void p() {
        if (this.b) {
            return;
        }
        b3.a("current level load fail.level:" + this.t);
        this.b = true;
        l();
        j jVar = this.q;
        if (jVar != null) {
            jVar.b(this);
        }
    }

    protected void q() {
        if (this.b) {
            return;
        }
        if (m()) {
            b3.a("current level load success.level:" + this.t + ";count:" + this.k.size());
        } else {
            b3.a("current level load success.level:" + this.t + ";slot:" + this.j.getSlotId() + ";ss:" + this.j.r());
        }
        this.b = true;
        l();
        j jVar = this.q;
        if (jVar != null) {
            jVar.a(this);
        }
    }

    protected void r() throws IllegalAccessException, InstantiationException {
        if (g()) {
            if (this.f.isEmpty()) {
                p();
            }
        } else if (this.a) {
            i();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("print all slot cfg:level:");
        sb.append(this.t);
        sb.append(";");
        for (u4 u4Var : this.c) {
            sb.append(u4Var.l());
            sb.append(":");
            sb.append(u4Var.n());
            sb.append(":show:");
            sb.append(u4Var.m());
            sb.append(",");
        }
        return sb.toString();
    }

    protected DCBaseAOLLoader b(u4 u4Var) throws IllegalAccessException, InstantiationException {
        j jVar;
        IAdAdapter iAdAdapterB = e.b().b(u4Var.l());
        a0 a0VarA = b0.a().a(u4Var.l());
        if (iAdAdapterB == null && !e.b().a(u4Var.l())) {
            if (a0VarA != null && a0VarA.g()) {
                try {
                    Object objNewInstance = Class.forName(a0VarA.d()).newInstance();
                    if (objNewInstance instanceof UniAdCustomAdapter) {
                        e.b().a(u4Var.l(), (UniAdCustomAdapter) objNewInstance);
                        iAdAdapterB = (UniAdCustomAdapter) objNewInstance;
                    }
                } catch (Exception unused) {
                }
            } else if (this.o == 1 && (iAdAdapterB = e.b().b("dcloud")) == null) {
                iAdAdapterB = new q0();
                e.b().a("dcloud", iAdAdapterB);
            }
        }
        if (iAdAdapterB == null || !iAdAdapterB.isSupport()) {
            return null;
        }
        if (u4Var.l().equalsIgnoreCase("dcloud") && (jVar = this.q) != null) {
            DCBaseAOLLoader dCBaseAOLLoaderI = jVar.i();
            dCBaseAOLLoaderI.setPlatform(null, "dcloud");
            return dCBaseAOLLoaderI;
        }
        DCBaseAOLLoader ad = iAdAdapterB.getAd(this.s, this.r);
        if (a0VarA == null || ad == null) {
            return null;
        }
        if (a0VarA.g()) {
            ad.a(a0VarA.a(), iAdAdapterB, a0VarA.e());
        } else {
            ad.a(a0VarA.a(), a0VarA.b());
            if (a0VarA.f() != null) {
                ad.setPlatform(a0VarA.f(), a0VarA.c());
            }
        }
        return ad;
    }

    @Override // io.dcloud.p.t1
    public void c(int i) {
        Iterator it = this.c.iterator();
        while (it.hasNext()) {
            if (((u4) it.next()).c() <= i) {
                it.remove();
            }
        }
    }

    @Override // io.dcloud.p.t1
    public List d() {
        return this.n;
    }

    @Override // io.dcloud.p.t1
    public List e() {
        if (m()) {
            return this.k;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.j);
        return arrayList;
    }

    @Override // io.dcloud.p.t1
    public void a(j jVar) {
        this.q = jVar;
    }

    private void a(boolean z) throws IllegalAccessException, InstantiationException {
        AtomicInteger atomicInteger;
        int i = 0;
        this.d.set(0);
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        for (u4 u4Var : this.c) {
            DCBaseAOLLoader dCBaseAOLLoaderB = b(u4Var);
            if (dCBaseAOLLoaderB != null) {
                this.d.incrementAndGet();
                if (z) {
                    u4Var.a(i2);
                    i2++;
                }
                dCBaseAOLLoaderB.a(u4Var);
                dCBaseAOLLoaderB.a(this);
                arrayList.add(dCBaseAOLLoaderB);
                if (!this.l.contains(Integer.valueOf(u4Var.m()))) {
                    this.l.add(Integer.valueOf(u4Var.m()));
                }
                if (this.m.containsKey(Integer.valueOf(u4Var.m()))) {
                    atomicInteger = (AtomicInteger) this.m.get(Integer.valueOf(u4Var.m()));
                } else {
                    atomicInteger = new AtomicInteger(0);
                }
                atomicInteger.incrementAndGet();
                this.m.put(Integer.valueOf(u4Var.m()), atomicInteger);
            } else {
                b3.b("load sub slot fail cfg:" + u4Var.toString());
            }
        }
        b3.a("level start load.current:" + this.t + ",valid ads:" + arrayList.size());
        if (arrayList.size() > 0) {
            if (this.l.size() > 1) {
                Collections.sort(this.l);
            }
            this.h.addAll(arrayList);
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                DCBaseAOLLoader dCBaseAOLLoader = (DCBaseAOLLoader) obj;
                dCBaseAOLLoader.d(this.u);
                dCBaseAOLLoader.b((Map) null);
            }
            return;
        }
        p();
    }

    @Override // io.dcloud.p.t1
    public int a() {
        if (m()) {
            List list = this.k;
            if (list == null || list.isEmpty()) {
                return -1;
            }
            m.a(this.k);
            return ((DCBaseAOL) this.k.get(r0.size() - 1)).getBiddingECPM();
        }
        DCBaseAOL dCBaseAOL = this.j;
        if (dCBaseAOL != null) {
            return dCBaseAOL.getBiddingECPM();
        }
        return -1;
    }

    private void b(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 70; i++) {
            sb.append(str);
        }
        b3.d(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(DCBaseAOL dCBaseAOL, DCBaseAOL dCBaseAOL2) {
        return Integer.compare(dCBaseAOL.r(), dCBaseAOL2.r());
    }

    public void a(DCBaseAOLLoader dCBaseAOLLoader, List list, m1 m1Var) {
        if (!this.u.equalsIgnoreCase(dCBaseAOLLoader.q()) || this.p || this.b) {
            return;
        }
        this.d.decrementAndGet();
        this.f.add(dCBaseAOLLoader);
        this.n.add(m1Var);
        f(dCBaseAOLLoader.r());
        b3.d("level ad load success!current sub slot:" + dCBaseAOLLoader.getSlotId() + ";ss;" + dCBaseAOLLoader.r() + ";type;" + dCBaseAOLLoader.getType());
        if (m()) {
            this.i.put(dCBaseAOLLoader, list);
        }
        if (m()) {
            if (this.a) {
                this.k.addAll(list);
                q();
                r();
            } else {
                o();
            }
        } else {
            if (!b()) {
                if (this.l.size() > 1 && !this.a) {
                    for (Integer num : this.l) {
                        if (dCBaseAOLLoader.r() > num.intValue()) {
                            b3.a("check ss smaller than this.ss:" + num + ";unfinished ss count:" + a(num));
                            if (a(num) > 0) {
                                break;
                            }
                        }
                    }
                    this.j = dCBaseAOLLoader;
                    q();
                } else {
                    this.j = dCBaseAOLLoader;
                    q();
                }
            }
            r();
        }
        b(Operators.SUB);
    }

    private int a(Integer num) {
        AtomicInteger atomicInteger = (AtomicInteger) this.m.get(num);
        if (atomicInteger == null) {
            return 0;
        }
        return atomicInteger.get();
    }

    public void a(DCBaseAOLLoader dCBaseAOLLoader, m1 m1Var) {
        if (!this.u.equalsIgnoreCase(dCBaseAOLLoader.q()) || this.p || this.b) {
            return;
        }
        this.d.decrementAndGet();
        this.g.add(dCBaseAOLLoader);
        this.n.add(m1Var);
        f(dCBaseAOLLoader.r());
        b3.b("level ad load fail.current sub slot:" + dCBaseAOLLoader.getSlotId() + ";ss:" + dCBaseAOLLoader.r() + ";type:" + dCBaseAOLLoader.getType());
        if (m()) {
            o();
        } else {
            if (this.l.size() > 1 && !this.a && !b() && this.f.size() > 0) {
                for (Integer num : this.l) {
                    if (dCBaseAOLLoader.r() <= num.intValue()) {
                        Iterator it = this.f.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            DCBaseAOL dCBaseAOL = (DCBaseAOL) it.next();
                            if (dCBaseAOL.r() == num.intValue()) {
                                b3.b("check ss large than this.slot:" + dCBaseAOL.getSlotId() + ";ss:" + dCBaseAOL.r());
                                this.j = dCBaseAOL;
                                q();
                                break;
                            }
                        }
                        if (a(num) > 0) {
                            break;
                        }
                    } else if (dCBaseAOLLoader.r() > num.intValue()) {
                        b3.b("check ss smaller than this.ss:" + num + ";unfinished ss count:" + a(num));
                        if (a(num) > 0) {
                            break;
                        }
                    } else {
                        continue;
                    }
                }
            }
            r();
        }
        b(Operators.SUB);
    }

    @Override // io.dcloud.p.t1
    public void a(int i) {
        this.o = i;
    }

    @Override // io.dcloud.p.t1
    public void a(String str) {
        this.u = str;
    }
}
