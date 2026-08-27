package io.dcloud.api.custom;

import android.app.Activity;
import android.content.Context;
import io.dcloud.api.custom.base.UniAdPrivacyConfig;
import io.dcloud.api.custom.type.UniAdCustomBaseLoader;
import io.dcloud.api.custom.type.draw.UniAdCustomDrawAdLoader;
import io.dcloud.api.custom.type.feed.UniAdCustomNativeAdLoader;
import io.dcloud.api.custom.type.full.UniAdCustomFullScreenVideoLoader;
import io.dcloud.api.custom.type.interstitial.UniAdCustomInterstitialLoader;
import io.dcloud.api.custom.type.reward.UniAdCustomRewardLoader;
import io.dcloud.api.custom.type.splash.UniAdCustomSplashLoader;
import io.dcloud.p.m0;
import io.dcloud.sdk.core.DCloudAOLManager;
import io.dcloud.sdk.core.adapter.IAdAdapter;
import io.dcloud.sdk.core.entry.DCloudAOLSlot;
import io.dcloud.sdk.core.module.DCBaseAOLLoader;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public abstract class UniAdCustomAdapter implements IAdAdapter {
    private boolean a = false;
    private final List b = new CopyOnWriteArrayList();

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface a {
        void a();

        void a(int i, String str);
    }

    public final void addInitListener(a aVar) {
        if (this.b.contains(aVar)) {
            return;
        }
        this.b.add(aVar);
    }

    @Override // io.dcloud.sdk.core.adapter.IAdAdapter
    public final DCBaseAOLLoader getAd(Activity activity, DCloudAOLSlot dCloudAOLSlot) {
        UniAdCustomBaseLoader adsByType = getAdsByType(dCloudAOLSlot.getType());
        if (adsByType == null) {
            return null;
        }
        int type = dCloudAOLSlot.getType();
        if (type != 1) {
            if (type != 4) {
                if (type != 7) {
                    if (type != 15) {
                        if (type != 9) {
                            if (type != 10 || !(adsByType instanceof UniAdCustomDrawAdLoader)) {
                                return null;
                            }
                        } else if (!(adsByType instanceof UniAdCustomRewardLoader)) {
                            return null;
                        }
                    } else if (!(adsByType instanceof UniAdCustomInterstitialLoader)) {
                        return null;
                    }
                } else if (!(adsByType instanceof UniAdCustomFullScreenVideoLoader)) {
                    return null;
                }
            } else if (!(adsByType instanceof UniAdCustomNativeAdLoader)) {
                return null;
            }
        } else if (!(adsByType instanceof UniAdCustomSplashLoader)) {
            return null;
        }
        m0 m0Var = new m0(dCloudAOLSlot, activity);
        m0Var.a(adsByType);
        return m0Var;
    }

    @Override // io.dcloud.sdk.core.adapter.IAdAdapter
    public final String getAdapterSDKVersion() {
        return String.valueOf(getAdapterVersion());
    }

    public abstract int getAdapterVersion();

    public abstract UniAdCustomBaseLoader getAdsByType(int i);

    @Override // io.dcloud.sdk.core.adapter.IAdAdapter
    public abstract String getSDKVersion();

    public abstract void init(Context context, JSONObject jSONObject);

    public final boolean isInitSuccess() {
        return this.a;
    }

    @Override // io.dcloud.sdk.core.adapter.IAdAdapter
    public final boolean isSupport() {
        return true;
    }

    public final void onInitFail(int i, String str) {
        this.a = false;
        Iterator it = this.b.iterator();
        while (it.hasNext()) {
            ((a) it.next()).a(i, str);
        }
    }

    public final void onInitSuccess() {
        this.a = true;
        Iterator it = this.b.iterator();
        while (it.hasNext()) {
            ((a) it.next()).a();
        }
    }

    public final void removeInitListener(a aVar) {
        this.b.remove(aVar);
    }

    public void setPrivacyConfig(UniAdPrivacyConfig uniAdPrivacyConfig) {
    }

    @Override // io.dcloud.sdk.core.adapter.IAdAdapter
    public final void updatePrivacyConfig(DCloudAOLManager.PrivacyConfig privacyConfig) {
        setPrivacyConfig(new UniAdPrivacyConfig(privacyConfig));
    }
}
