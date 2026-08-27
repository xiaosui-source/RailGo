package io.dcloud.p;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.ViewGroup;
import com.nostra13.dcloudimageloader.core.download.BaseImageDownloader;
import io.dcloud.WebAppActivity;
import io.dcloud.p.u2;
import io.dcloud.sdk.base.dcloud.ADHandler;
import io.dcloud.sdk.core.entry.DCloudAOLSlot;
import io.dcloud.sdk.core.module.DCBaseAOLLoader;
import io.dcloud.sdk.core.util.AOLErrorUtil;
import io.dcloud.sdk.core.util.MainHandlerUtil;
import io.dcloud.sdk.poly.api.Platform;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class u0 extends DCBaseAOLLoader implements u2.c {
    private v4 J;
    private Platform K;
    private String L;
    private ADHandler.e M;
    private Handler N;
    private int O;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a extends Handler {
        a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            u0.this.loadFail(-9999, "");
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements n {
        b() {
        }

        @Override // io.dcloud.p.n
        public void onClicked() {
            if (u0.this.getVideoAdCallback() != null) {
                u0.this.getVideoAdCallback().onClick();
            }
        }

        @Override // io.dcloud.p.n
        public void onFinishShow() {
            if (u0.this.getVideoAdCallback() != null) {
                u0.this.getVideoAdCallback().onClose();
            }
        }

        @Override // io.dcloud.p.n
        public void onShow() {
            if (u0.this.getVideoAdCallback() != null) {
                u0.this.getVideoAdCallback().onShow();
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c implements ADHandler.g {
        c() {
        }

        @Override // io.dcloud.sdk.base.dcloud.ADHandler.g
        public void a() {
            if (u0.this.getAdStatus() != -1) {
                return;
            }
            ADHandler.e eVarB = ADHandler.b(u0.this.getActivity(), r0.d().b().getAppId());
            if (!eVarB.a()) {
                u0.this.loadFail(-9999, "");
                return;
            }
            u0.this.M = eVarB;
            u0.this.M.a(u0.this.getDCloudId());
            u0.this.loadSuccess();
        }

        @Override // io.dcloud.sdk.base.dcloud.ADHandler.g
        public void b() {
            if (u0.this.getAdStatus() != -1) {
                return;
            }
            ADHandler.e eVarB = ADHandler.b(u0.this.getActivity(), r0.d().b().getAppId());
            if (!eVarB.a()) {
                u0.this.loadFail(-9999, "");
                return;
            }
            u0.this.M = eVarB;
            u0.this.M.a(u0.this.getDCloudId());
            u0.this.loadSuccess();
        }
    }

    public u0(DCloudAOLSlot dCloudAOLSlot, Activity activity) {
        super(dCloudAOLSlot, activity);
        this.L = "";
        this.N = new a(Looper.getMainLooper());
        this.O = BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(ViewGroup viewGroup) {
        this.J.a(viewGroup);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: c, reason: merged with bridge method [inline-methods] */
    public void a(ViewGroup viewGroup) {
        new io.dcloud.sdk.base.dcloud.g(getActivity(), this.M, viewGroup, new b()).b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void z() throws JSONException, UnsupportedEncodingException {
        v4 v4VarA = s0.a(this, getActivity(), this.K.getSplash(), getSlotId(), this.K.getEr(), this.K.getEc());
        this.J = v4VarA;
        v4VarA.c();
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOL
    public void destroy() {
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOL
    public String getTid() {
        Platform platform = this.K;
        if (platform != null) {
            return platform.getTid();
        }
        ADHandler.e eVar = this.M;
        return eVar != null ? eVar.l : "";
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOL
    public String getType() {
        Platform platform = this.K;
        return platform == null ? this.L : platform.getType();
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOLLoader
    public void init(String str, String str2) {
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOL
    public boolean isValid() {
        return true;
    }

    @Override // io.dcloud.p.u2.c
    public void k() {
        if (getVideoAdCallback() != null) {
            getVideoAdCallback().onClose();
        }
    }

    @Override // io.dcloud.p.u2.c
    public void l() {
        if (getVideoAdCallback() != null) {
            getVideoAdCallback().onSkip();
        }
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOLLoader
    public void load(Map map) {
        if (TextUtils.isEmpty(this.L)) {
            loadFail(-9999, "");
            return;
        }
        if (this.L.equals("dcloud")) {
            b3.a("uniAd", "load base");
            this.N.sendEmptyMessageDelayed(this.O, WebAppActivity.SPLASH_SECOND);
        } else if (this.K == null) {
            loadFail(-9999, "");
        } else {
            MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.u0$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws JSONException, UnsupportedEncodingException {
                    this.f$0.z();
                }
            });
        }
    }

    @Override // io.dcloud.p.u2.c
    public void m() {
        loadSuccess();
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOL
    public String n() {
        Platform platform = this.K;
        return platform == null ? "" : platform.getAppid();
    }

    @Override // io.dcloud.p.u2.c
    public void onAdClicked() {
        if (getVideoAdCallback() != null) {
            getVideoAdCallback().onClick();
        }
    }

    @Override // io.dcloud.p.u2.c
    public void onAdShow() {
        if (getVideoAdCallback() != null) {
            getVideoAdCallback().onShow();
        }
    }

    @Override // io.dcloud.p.u2.c
    public void onError(int i, String str) {
        loadFail(i, str);
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOLLoader
    public boolean runOnMain() {
        return false;
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOLLoader
    public void setPlatform(Platform platform, String str) {
        this.K = platform;
        this.L = str;
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOLLoader
    public void showIn(final ViewGroup viewGroup) {
        if ((this.J == null && this.M == null) || TextUtils.isEmpty(this.L)) {
            if (getVideoAdCallback() != null) {
                getVideoAdCallback().onShowError(-5008, AOLErrorUtil.getErrorMsg(-5008));
                return;
            }
            return;
        }
        if (viewGroup == null) {
            if (getVideoAdCallback() != null) {
                getVideoAdCallback().onShowError(-5014, AOLErrorUtil.getErrorMsg(-5014));
            }
        } else {
            if (this.L.equals("dcloud")) {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    a(viewGroup);
                    return;
                } else {
                    MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.u0$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.a(viewGroup);
                        }
                    });
                    return;
                }
            }
            if (Looper.myLooper() == Looper.getMainLooper()) {
                this.J.a(viewGroup);
            } else {
                MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.u0$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.b(viewGroup);
                    }
                });
            }
        }
    }

    @Override // io.dcloud.p.u2.c
    public void a(int i, String str) {
        if (getVideoAdCallback() != null) {
            getVideoAdCallback().onShowError(-5100, "code" + i + ";message:" + str);
        }
    }

    public void a(JSONArray jSONArray, boolean z) {
        b3.a("uniAd-finish", String.valueOf(jSONArray) + "::::::" + z);
        if (this.N.hasMessages(this.O)) {
            this.N.removeMessages(this.O);
            if (z) {
                if (jSONArray != null && jSONArray.length() != 0) {
                    c cVar = new c();
                    boolean z2 = true;
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i);
                        if (jSONObjectOptJSONObject != null) {
                            ADHandler.a(getActivity(), jSONObjectOptJSONObject, System.currentTimeMillis(), cVar);
                            z2 = false;
                        }
                    }
                    if (z2) {
                        loadFail(-9999, "");
                        return;
                    }
                    return;
                }
                ADHandler.e eVarB = ADHandler.b(getActivity(), r0.d().b().getAppId());
                if (eVarB.a()) {
                    this.M = eVarB;
                    eVarB.a(getDCloudId());
                    loadSuccess();
                    return;
                }
                loadFail(-9999, "");
                return;
            }
            loadFail(-9999, "");
        }
    }
}
