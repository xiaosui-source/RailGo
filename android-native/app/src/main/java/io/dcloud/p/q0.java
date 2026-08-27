package io.dcloud.p;

import android.app.Activity;
import io.dcloud.sdk.core.DCloudAOLManager;
import io.dcloud.sdk.core.adapter.IAdAdapter;
import io.dcloud.sdk.core.entry.DCloudAOLSlot;
import io.dcloud.sdk.core.module.DCBaseAOLLoader;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class q0 implements IAdAdapter {
    @Override // io.dcloud.sdk.core.adapter.IAdAdapter
    public DCBaseAOLLoader getAd(Activity activity, DCloudAOLSlot dCloudAOLSlot) {
        if (dCloudAOLSlot.getType() == 1) {
            return new u0(dCloudAOLSlot, activity);
        }
        return null;
    }

    @Override // io.dcloud.sdk.core.adapter.IAdAdapter
    public String getAdapterSDKVersion() {
        return "1.9.9.82603";
    }

    @Override // io.dcloud.sdk.core.adapter.IAdAdapter
    public String getSDKVersion() {
        return "";
    }

    @Override // io.dcloud.sdk.core.adapter.IAdAdapter
    public boolean isSupport() {
        return true;
    }

    @Override // io.dcloud.sdk.core.adapter.IAdAdapter
    public void setPersonalAd(boolean z) {
    }

    @Override // io.dcloud.sdk.core.adapter.IAdAdapter
    public void updatePrivacyConfig(DCloudAOLManager.PrivacyConfig privacyConfig) {
    }
}
