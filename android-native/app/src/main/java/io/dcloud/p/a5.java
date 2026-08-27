package io.dcloud.p;

import android.app.Activity;
import io.dcloud.sdk.core.entry.DCloudAOLSlot;
import io.dcloud.sdk.core.interfaces.AOLLoader;
import io.dcloud.sdk.core.module.DCBaseAOL;
import io.dcloud.sdk.core.module.DCBaseAOLLoader;
import io.dcloud.sdk.core.util.AOLErrorUtil;
import io.dcloud.sdk.core.util.MainHandlerUtil;
import java.util.List;
import org.json.JSONArray;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class a5 extends w implements AOLLoader.VAOLInteractionListener {
    protected AOLLoader.VAOLInteractionListener r;
    protected v2 s;
    protected DCBaseAOL t;
    private boolean u;
    private boolean v;
    protected boolean w;

    public a5(Activity activity, int i) {
        super(activity);
        this.u = false;
        this.v = false;
        this.w = false;
        this.d = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(v2 v2Var) {
        if (v2Var != null) {
            v2Var.onError(-5021, AOLErrorUtil.getErrorMsg(-5021), null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(int i, String str) {
        AOLLoader.VAOLInteractionListener vAOLInteractionListener = this.r;
        if (vAOLInteractionListener != null) {
            vAOLInteractionListener.onShowError(i, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void m() {
        AOLLoader.VAOLInteractionListener vAOLInteractionListener = this.r;
        if (vAOLInteractionListener != null) {
            vAOLInteractionListener.onClick();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void n() {
        AOLLoader.VAOLInteractionListener vAOLInteractionListener = this.r;
        if (vAOLInteractionListener != null) {
            vAOLInteractionListener.onClose();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void o() {
        AOLLoader.VAOLInteractionListener vAOLInteractionListener = this.r;
        if (vAOLInteractionListener != null) {
            vAOLInteractionListener.onShow();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void p() {
        AOLLoader.VAOLInteractionListener vAOLInteractionListener = this.r;
        if (vAOLInteractionListener != null) {
            vAOLInteractionListener.onSkip();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void q() {
        AOLLoader.VAOLInteractionListener vAOLInteractionListener = this.r;
        if (vAOLInteractionListener != null) {
            vAOLInteractionListener.onVideoPlayEnd();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void r() {
        AOLLoader.VAOLInteractionListener vAOLInteractionListener = this.r;
        if (vAOLInteractionListener != null) {
            vAOLInteractionListener.onShowError(-5006, AOLErrorUtil.getErrorMsg(-5006));
        }
    }

    @Override // io.dcloud.p.w
    protected void a(final int i, final String str, final JSONArray jSONArray) {
        this.t = null;
        b3.b("uniAd-loadError", "code:" + i + ";message:" + str + ";detail:" + String.valueOf(jSONArray));
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.a5$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.c(i, str, jSONArray);
            }
        });
    }

    @Override // io.dcloud.p.w
    protected void c(final List list) {
        this.t = null;
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.a5$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.d(list);
            }
        });
    }

    public void destroy() {
        DCBaseAOL dCBaseAOL = this.t;
        if (dCBaseAOL != null) {
            dCBaseAOL.destroy();
        }
    }

    public String getType() {
        return this.t != null ? e.b().c(this.t.getType()) : "";
    }

    public boolean isLoading() {
        return this.w;
    }

    public boolean l() {
        DCBaseAOL dCBaseAOL = this.t;
        return dCBaseAOL != null && dCBaseAOL.isValid();
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onClick() {
        a(a(), this.t);
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.a5$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m();
            }
        });
        Activity activityA = a();
        boolean z = this.d == 9;
        StringBuilder sb = new StringBuilder();
        sb.append(this.d);
        sb.append("_");
        DCBaseAOL dCBaseAOL = this.t;
        sb.append(dCBaseAOL != null ? dCBaseAOL.getType() : "");
        f4.a(activityA, z, sb.toString());
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onClose() {
        this.v = false;
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.a5$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.n();
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onPaidGet(long j, String str, int i) {
        a(a(), this.t, j, str, i);
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onShow() {
        this.u = true;
        this.v = true;
        b(a(), this.t);
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.a5$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.o();
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onShowError(final int i, final String str) {
        this.v = false;
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.a5$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.d(i, str);
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onSkip() {
        this.v = false;
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.a5$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.p();
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onVideoPlayEnd() {
        this.v = false;
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.a5$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.q();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(AOLLoader.GetConvertResultListener getConvertResultListener) {
        if (getConvertResultListener != null) {
            getConvertResultListener.onError(-5100, "type:" + getType() + ";code:-1;message:暂无缓存任务");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(int i, String str, JSONArray jSONArray) {
        v2 v2Var = this.s;
        if (v2Var != null) {
            v2Var.onError(i, str, jSONArray);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(List list) {
        if (list != null && list.size() > 0) {
            DCBaseAOL dCBaseAOL = (DCBaseAOL) list.get(0);
            this.t = dCBaseAOL;
            dCBaseAOL.setVideoAdCallback(this);
            v2 v2Var = this.s;
            if (v2Var != null) {
                v2Var.onLoaded();
                return;
            }
            return;
        }
        v2 v2Var2 = this.s;
        if (v2Var2 != null) {
            v2Var2.onError(-5005, AOLErrorUtil.getErrorMsg(-5005), null);
        }
    }

    public void b(final AOLLoader.RequestConvertResultListener requestConvertResultListener) {
        DCBaseAOL dCBaseAOL = this.t;
        if (dCBaseAOL != null) {
            dCBaseAOL.a(requestConvertResultListener);
        } else {
            MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.a5$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.a(requestConvertResultListener);
                }
            });
        }
    }

    public void a(Activity activity) {
        if (this.u) {
            MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.a5$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.r();
                }
            });
            return;
        }
        DCBaseAOL dCBaseAOL = this.t;
        if (dCBaseAOL instanceof DCBaseAOLLoader) {
            ((DCBaseAOLLoader) dCBaseAOL).show(activity);
        }
    }

    public void a(AOLLoader.VAOLInteractionListener vAOLInteractionListener) {
        this.r = vAOLInteractionListener;
    }

    public void a(DCloudAOLSlot dCloudAOLSlot, final v2 v2Var) {
        if (this.v) {
            MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.a5$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    a5.b(v2Var);
                }
            });
            return;
        }
        if (this.w) {
            MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.a5$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    a5.a(v2Var);
                }
            });
            return;
        }
        this.u = false;
        a(dCloudAOLSlot);
        this.s = v2Var;
        this.w = true;
        z2.a().post(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(v2 v2Var) {
        if (v2Var != null) {
            v2Var.onError(-5017, AOLErrorUtil.getErrorMsg(-5017), null);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void a(boolean z) {
        this.w = z;
    }

    public void a(final AOLLoader.GetConvertResultListener getConvertResultListener) {
        DCBaseAOL dCBaseAOL = this.t;
        if (dCBaseAOL != null) {
            dCBaseAOL.a(getConvertResultListener);
        } else {
            MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.a5$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.b(getConvertResultListener);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AOLLoader.RequestConvertResultListener requestConvertResultListener) {
        if (requestConvertResultListener != null) {
            requestConvertResultListener.onError(-5100, "type:" + getType() + ";code:-1;message:暂无缓存任务");
        }
    }
}
