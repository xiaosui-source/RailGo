package io.dcloud.api.custom.base;

import io.dcloud.sdk.core.DCloudAOLManager;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class UniAdPrivacyConfig {
    private DCloudAOLManager.PrivacyConfig a;

    public UniAdPrivacyConfig(DCloudAOLManager.PrivacyConfig privacyConfig) {
        this.a = privacyConfig;
    }

    public boolean isAdult() {
        DCloudAOLManager.PrivacyConfig privacyConfig = this.a;
        if (privacyConfig == null) {
            return true;
        }
        return privacyConfig.isAdult();
    }

    public boolean isCanGetAndroidId() {
        DCloudAOLManager.PrivacyConfig privacyConfig = this.a;
        if (privacyConfig == null) {
            return true;
        }
        return privacyConfig.isCanGetAndroidId();
    }

    public boolean isCanGetIP() {
        DCloudAOLManager.PrivacyConfig privacyConfig = this.a;
        if (privacyConfig == null) {
            return true;
        }
        return privacyConfig.isCanGetIP();
    }

    public boolean isCanGetInstallAppList() {
        DCloudAOLManager.PrivacyConfig privacyConfig = this.a;
        if (privacyConfig == null) {
            return true;
        }
        return privacyConfig.isCanGetInstallAppList();
    }

    public boolean isCanGetMacAddress() {
        DCloudAOLManager.PrivacyConfig privacyConfig = this.a;
        if (privacyConfig == null) {
            return true;
        }
        return privacyConfig.isCanGetMacAddress();
    }

    public boolean isCanGetOAID() {
        DCloudAOLManager.PrivacyConfig privacyConfig = this.a;
        if (privacyConfig == null) {
            return true;
        }
        return privacyConfig.isCanGetOAID();
    }

    public boolean isCanGetRunningApps() {
        DCloudAOLManager.PrivacyConfig privacyConfig = this.a;
        if (privacyConfig == null) {
            return true;
        }
        return privacyConfig.isCanGetRunningApps();
    }

    public boolean isCanUseLocation() {
        DCloudAOLManager.PrivacyConfig privacyConfig = this.a;
        if (privacyConfig == null) {
            return true;
        }
        return privacyConfig.isCanUseLocation();
    }

    public boolean isCanUsePhoneState() {
        DCloudAOLManager.PrivacyConfig privacyConfig = this.a;
        if (privacyConfig == null) {
            return true;
        }
        return privacyConfig.isCanUsePhoneState();
    }

    public boolean isCanUseSensor() {
        DCloudAOLManager.PrivacyConfig privacyConfig = this.a;
        if (privacyConfig == null) {
            return true;
        }
        return privacyConfig.isCanUseSensor();
    }

    public boolean isCanUseSimOperator() {
        DCloudAOLManager.PrivacyConfig privacyConfig = this.a;
        if (privacyConfig == null) {
            return true;
        }
        return privacyConfig.isCanUseSimOperator();
    }

    public boolean isCanUseStorage() {
        DCloudAOLManager.PrivacyConfig privacyConfig = this.a;
        if (privacyConfig == null) {
            return true;
        }
        return privacyConfig.isCanUseStorage();
    }

    public boolean isCanUseWifiState() {
        DCloudAOLManager.PrivacyConfig privacyConfig = this.a;
        if (privacyConfig == null) {
            return true;
        }
        return privacyConfig.isCanUseWifiState();
    }

    public boolean isGDTAgreeStrategy() {
        DCloudAOLManager.PrivacyConfig privacyConfig = this.a;
        if (privacyConfig == null) {
            return true;
        }
        return privacyConfig.isGDTAgreeStrategy();
    }
}
