package io.dcloud.p;

import android.app.Activity;
import android.view.ViewGroup;
import io.dcloud.api.custom.UniAdCustomAdapter;
import io.dcloud.api.custom.base.UniAdPrivacyConfig;
import io.dcloud.api.custom.base.UniAdSlot;
import io.dcloud.api.custom.type.UniAdCustomBaseLoader;
import io.dcloud.api.custom.type.feed.UniAdCustomNativeAd;
import io.dcloud.p.v1;
import io.dcloud.sdk.core.adapter.IAdAdapter;
import io.dcloud.sdk.core.entry.DCloudAOLSlot;
import io.dcloud.sdk.core.module.DCBaseAOLLoader;
import io.dcloud.sdk.core.util.AOLErrorUtil;
import io.dcloud.sdk.core.util.AdUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class m0 extends DCBaseAOLLoader implements v1, v1.a {
    private JSONObject J;
    private UniAdCustomAdapter K;
    private UniAdCustomBaseLoader L;

    public m0(DCloudAOLSlot dCloudAOLSlot, Activity activity) {
        super(dCloudAOLSlot, activity);
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOL
    public void biddingFail(int i, int i2, int i3) {
        UniAdCustomBaseLoader uniAdCustomBaseLoader = this.L;
        if (uniAdCustomBaseLoader != null) {
            uniAdCustomBaseLoader.onBidFail(i, i3);
        }
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOL
    public void biddingSuccess(int i, int i2) {
        UniAdCustomBaseLoader uniAdCustomBaseLoader = this.L;
        if (uniAdCustomBaseLoader != null) {
            uniAdCustomBaseLoader.onBidSuccess(i, i2);
        }
    }

    @Override // io.dcloud.p.v1
    public void d() {
        loadSuccess();
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOL
    public void destroy() {
        UniAdCustomBaseLoader uniAdCustomBaseLoader = this.L;
        if (uniAdCustomBaseLoader != null) {
            uniAdCustomBaseLoader.destroy();
        }
    }

    @Override // io.dcloud.p.v1
    public int f() {
        return isSlotSupportBidding() ? 1 : 0;
    }

    @Override // io.dcloud.p.v1.a
    public void g() throws JSONException {
        if (getVideoAdCallback() instanceof y3) {
            ((y3) getVideoAdCallback()).onReward(new JSONObject());
        }
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOL
    public int getBiddingECPM() {
        return super.getBiddingECPM();
    }

    @Override // io.dcloud.p.v1
    public void h() {
        if (getVideoAdCallback() != null) {
            getVideoAdCallback().onVideoPlayEnd();
        }
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOLLoader
    protected void init(String str, String str2) {
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOL
    public boolean isValid() {
        UniAdCustomBaseLoader uniAdCustomBaseLoader = this.L;
        return uniAdCustomBaseLoader != null && uniAdCustomBaseLoader.isReady();
    }

    @Override // io.dcloud.p.v1
    public void j() {
        if (getVideoAdCallback() != null) {
            getVideoAdCallback().onClose();
        }
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOLLoader
    public void load(Map map) {
        UniAdSlot uniAdSlot = new UniAdSlot();
        uniAdSlot.setSlotId(getSlotId());
        uniAdSlot.setExtra(getSlot().getExtra());
        uniAdSlot.setUserId(getSlot().getUserId());
        uniAdSlot.setWidth(getSlot().getWidth());
        uniAdSlot.setHeight(getSlot().getHeight());
        uniAdSlot.setAdCount(getSlot().getCount());
        a(getActivity(), uniAdSlot);
    }

    @Override // io.dcloud.p.v1
    public void onAdClicked() {
        if (getVideoAdCallback() != null) {
            getVideoAdCallback().onClick();
        }
    }

    @Override // io.dcloud.p.v1
    public void onAdShow() {
        if (getVideoAdCallback() != null) {
            getVideoAdCallback().onShow();
        }
    }

    @Override // io.dcloud.p.v1
    public void onLoadFail(int i, String str) {
        loadFail(i, str);
    }

    @Override // io.dcloud.p.v1
    public void onLoadSuccess(List list) {
        if (list == null || list.isEmpty()) {
            loadFail(-5004, AOLErrorUtil.getErrorMsg(200000));
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            UniAdCustomNativeAd uniAdCustomNativeAd = (UniAdCustomNativeAd) it.next();
            n0 n0Var = new n0(getSlot(), getActivity());
            n0Var.a(uniAdCustomNativeAd);
            if (isSlotSupportBidding()) {
                n0Var.setBiddingECPM(uniAdCustomNativeAd.getBidPrice());
            }
            arrayList.add(n0Var);
        }
        loadSuccess(arrayList);
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOL
    public boolean s() {
        return true;
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOLLoader
    public void show(Activity activity) {
        if (isValid()) {
            this.L.show(activity);
        } else if (getVideoAdCallback() != null) {
            getVideoAdCallback().onShowError(-5008, AOLErrorUtil.getErrorMsg(-5008));
        }
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOLLoader
    public void showIn(ViewGroup viewGroup) {
        if (isValid()) {
            this.L.show(viewGroup);
        } else if (getVideoAdCallback() != null) {
            getVideoAdCallback().onShowError(-5008, AOLErrorUtil.getErrorMsg(-5008));
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements UniAdCustomAdapter.a {
        final /* synthetic */ Activity a;
        final /* synthetic */ UniAdSlot b;

        a(Activity activity, UniAdSlot uniAdSlot) {
            this.a = activity;
            this.b = uniAdSlot;
        }

        @Override // io.dcloud.api.custom.UniAdCustomAdapter.a
        public void a() {
            m0.this.L.a(this.a, this.b, m0.this);
            m0.this.K.removeInitListener(this);
        }

        @Override // io.dcloud.api.custom.UniAdCustomAdapter.a
        public void a(int i, String str) {
            m0.this.loadFail(i, str);
            m0.this.K.removeInitListener(this);
        }
    }

    public void a(UniAdCustomBaseLoader uniAdCustomBaseLoader) {
        this.L = uniAdCustomBaseLoader;
    }

    @Override // io.dcloud.p.v1
    public void b() {
        if (getVideoAdCallback() != null) {
            getVideoAdCallback().onSkip();
        }
    }

    public void a(Activity activity, UniAdSlot uniAdSlot) {
        if (this.L != null) {
            UniAdCustomAdapter uniAdCustomAdapter = this.K;
            if (uniAdCustomAdapter != null) {
                if (uniAdCustomAdapter.isInitSuccess()) {
                    this.L.a(activity, uniAdSlot, this);
                    return;
                }
                this.K.addInitListener(new a(activity, uniAdSlot));
                try {
                    this.K.setPrivacyConfig(new UniAdPrivacyConfig(AdUtil.getCustomPrivacyConfig()));
                    this.K.init(getActivity(), this.J);
                    return;
                } catch (Exception unused) {
                    loadFail(-4001, AOLErrorUtil.getErrorMsg(-4001));
                    return;
                }
            }
            loadFail(-4001, AOLErrorUtil.getErrorMsg(-4001));
            return;
        }
        loadFail(-4001, AOLErrorUtil.getErrorMsg(-4001));
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOLLoader
    public void a(IAdAdapter iAdAdapter, JSONObject jSONObject) {
        this.K = (UniAdCustomAdapter) iAdAdapter;
        this.J = jSONObject;
    }

    @Override // io.dcloud.p.v1
    public void a(int i, String str) {
        if (getVideoAdCallback() != null) {
            getVideoAdCallback().onShowError(-5100, "type:" + getType() + ";code:" + i + ";message:" + str);
        }
    }
}
