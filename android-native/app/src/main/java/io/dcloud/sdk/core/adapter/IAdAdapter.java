package io.dcloud.sdk.core.adapter;

import android.app.Activity;
import io.dcloud.sdk.core.DCloudAOLManager;
import io.dcloud.sdk.core.entry.DCloudAOLSlot;
import io.dcloud.sdk.core.module.DCBaseAOLLoader;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public interface IAdAdapter {
    DCBaseAOLLoader getAd(Activity activity, DCloudAOLSlot dCloudAOLSlot);

    String getAdapterSDKVersion();

    String getSDKVersion();

    boolean isSupport();

    void setPersonalAd(boolean z);

    void updatePrivacyConfig(DCloudAOLManager.PrivacyConfig privacyConfig);
}
