package io.dcloud.sdk.core.module;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.ViewGroup;
import com.nostra13.dcloudimageloader.core.download.BaseImageDownloader;
import io.dcloud.p.b3;
import io.dcloud.p.e;
import io.dcloud.p.m;
import io.dcloud.p.m1;
import io.dcloud.p.u4;
import io.dcloud.p.w;
import io.dcloud.p.z2;
import io.dcloud.sdk.core.adapter.IAdAdapter;
import io.dcloud.sdk.core.entry.DCloudAOLSlot;
import io.dcloud.sdk.core.util.Const;
import io.dcloud.sdk.core.util.MainHandlerUtil;
import io.dcloud.sdk.poly.api.Platform;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class DCBaseAOLLoader extends io.dcloud.sdk.core.module.a {
    private boolean E;
    private final Handler F;
    private final int G;
    private int H;
    private w.a I;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    private static class a implements Runnable {
        private final DCBaseAOLLoader a;
        private final int b;
        private final int c;
        private final String d;
        private final List e;

        public a(DCBaseAOLLoader dCBaseAOLLoader, List list, int i, int i2, String str) {
            this.a = dCBaseAOLLoader;
            this.b = i;
            this.c = i2;
            this.d = str;
            this.e = list;
            if (list != null) {
                b3.d("sub slot ads:" + list.size());
            }
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            m1 m1Var = new m1();
            m1Var.c(this.a.n()).e(this.a.getSlotId()).b(this.a.e).d(this.a.getType()).f(this.a.getTid()).a(this.a.t());
            if (this.a.s()) {
                m1Var.a(m.a(this.a)).b(m.b(this.a));
            }
            int i = this.b;
            if (i == 1) {
                m1Var.c(i);
                if (this.a.isSlotSupportBidding()) {
                    m1Var.a(this.a.getBiddingECPM());
                }
                this.a.I.a(this.a, this.e, m1Var);
                return;
            }
            if (i == 0) {
                m1Var.c(i);
                m1Var.a(this.c, this.d);
                this.a.I.a(this.a, m1Var);
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    private class b extends Handler {
        public b(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void dispatchMessage(Message message) {
            DCBaseAOLLoader.this.loadFail(-5000, "timeout");
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
        }
    }

    public DCBaseAOLLoader(DCloudAOLSlot dCloudAOLSlot, Activity activity) {
        super(dCloudAOLSlot, activity);
        this.E = false;
        this.G = 1;
        this.H = BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT;
        d(-1);
        this.F = new b(z2.a().getLooper());
    }

    private boolean w() {
        if (TextUtils.isEmpty(n())) {
            return !e.b().a(getType()) || getType().equalsIgnoreCase("dcloud") || getType().equalsIgnoreCase(Const.TYPE_WM);
        }
        if (getType().equalsIgnoreCase(Const.TYPE_SGM)) {
            return !TextUtils.isEmpty(o());
        }
        return true;
    }

    private boolean x() {
        return getSlot().getType() == 10 || getSlot().getType() == 4 || getSlot().getType() == 5;
    }

    public void a(IAdAdapter iAdAdapter, JSONObject jSONObject) {
    }

    public final void b(final Map map) {
        d(-1);
        if (!w()) {
            loadFail(-9999, "");
            return;
        }
        if (TextUtils.isEmpty(getSlotId())) {
            loadFail(-9999, "");
            return;
        }
        startLoadTime();
        if (runOnMain()) {
            MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.sdk.core.module.DCBaseAOLLoader$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.a(map);
                }
            });
        } else {
            try {
                load(map);
            } catch (Throwable th) {
                loadFail(-4002, th.getMessage());
            }
        }
        this.F.sendEmptyMessageDelayed(1, this.H);
    }

    protected abstract void init(String str, String str2);

    public abstract void load(Map<String, Object> map);

    public final void loadFail(int i, String str) {
        if (this.E) {
            return;
        }
        b(i, str);
        this.E = true;
        this.F.removeMessages(1);
        z2.a().post(new a(this, null, 0, i, str));
    }

    public final void loadSuccess() {
        if (x()) {
            loadFail(-5001, "回调接口调用失败，应该使用loadSuccess(List obj)");
        } else {
            loadSuccess(null);
        }
    }

    protected boolean runOnMain() {
        return false;
    }

    public void setPlatform(Platform platform, String str) {
    }

    public void show(Activity activity) {
    }

    public void showIn(ViewGroup viewGroup) {
    }

    public m1 y() {
        m1 m1Var = new m1();
        u();
        m1Var.c(n()).b(this.e).e(getSlotId()).d(getType()).f(getTid()).a(t());
        m1Var.c(getAdStatus());
        if (isSlotSupportBidding()) {
            m1Var.a(getBiddingECPM());
        }
        if (s()) {
            m1Var.a(m.a(this)).b(m.b(this));
        }
        return m1Var;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Map map) {
        try {
            load(map);
        } catch (Throwable th) {
            loadFail(-4002, th.getMessage());
        }
    }

    public void a(u4 u4Var) {
        g(u4Var.n());
        this.s = u4Var.l();
        b(u4Var.t());
        if (!isSlotSupportBidding()) {
            setBiddingECPM(u4Var.c());
        }
        this.d = u4Var.m();
        this.H = u4Var.o();
        this.e = u4Var.f();
        this.g = u4Var.b();
        setFeedType(u4Var.d());
        e(u4Var.h());
        a(u4Var.q());
        f(u4Var.k());
        c(u4Var.i());
        a(u4Var.j());
        a(u4Var.a());
        b(u4Var.e());
        setExpressAd(u4Var.r());
        b3.d("load sub slot cfg:" + u4Var.toString());
    }

    public final void loadSuccess(List<? extends DCBaseAOL> list) {
        if (!x()) {
            list = null;
        }
        List<? extends DCBaseAOL> list2 = list;
        if (this.E) {
            return;
        }
        if (list2 != null) {
            for (DCBaseAOL dCBaseAOL : list2) {
                dCBaseAOL.e = this.e;
                dCBaseAOL.b(isSlotSupportBidding());
                dCBaseAOL.setBiddingECPM(getBiddingECPM());
                dCBaseAOL.d = this.d;
                dCBaseAOL.g = this.g;
                dCBaseAOL.g(getSlotId());
                dCBaseAOL.b(n());
                dCBaseAOL.setFeedType(getFeedType());
                dCBaseAOL.d(q());
                dCBaseAOL.s = this.s;
            }
        }
        v();
        this.E = true;
        this.F.removeMessages(1);
        z2.a().post(new a(this, list2, 1, 0, null));
    }

    public void a(String str, String str2) {
        b(str);
        c(str2);
        init(str, str2);
    }

    public final void a(String str, IAdAdapter iAdAdapter, JSONObject jSONObject) {
        b(str);
        a(iAdAdapter, jSONObject);
    }

    public void a(w.a aVar) {
        this.I = aVar;
    }
}
