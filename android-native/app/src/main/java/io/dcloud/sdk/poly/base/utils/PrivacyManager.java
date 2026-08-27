package io.dcloud.sdk.poly.base.utils;

import io.dcloud.sdk.core.DCloudAOLManager;
import io.dcloud.sdk.core.util.Const;
import java.util.HashMap;
import java.util.Map;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class PrivacyManager {
    private static volatile PrivacyManager c;
    private DCloudAOLManager.PrivacyConfig a;
    private final Map b = new HashMap();

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class a extends DCloudAOLManager.PrivacyConfig {
        private boolean isAllowPrivacy = true;

        public abstract String getAndroidId();

        public abstract String[] getImeis();

        public abstract String getImsi();

        public String getMacAddress() {
            return null;
        }

        public abstract boolean isAllowPrivacy();
    }

    private PrivacyManager() {
    }

    public static PrivacyManager getInstance() {
        if (c == null) {
            synchronized (PrivacyManager.class) {
                if (c == null) {
                    c = new PrivacyManager();
                }
            }
        }
        return c;
    }

    public String[] a() {
        DCloudAOLManager.PrivacyConfig privacyConfig = this.a;
        if (privacyConfig instanceof a) {
            return ((a) privacyConfig).getImeis();
        }
        return null;
    }

    public String b() {
        DCloudAOLManager.PrivacyConfig privacyConfig = this.a;
        return privacyConfig instanceof a ? ((a) privacyConfig).getImsi() : "";
    }

    public String c() {
        DCloudAOLManager.PrivacyConfig privacyConfig = this.a;
        return privacyConfig instanceof a ? ((a) privacyConfig).getAndroidId() : "";
    }

    public boolean d() {
        DCloudAOLManager.PrivacyConfig privacyConfig = this.a;
        if (privacyConfig instanceof a) {
            return ((a) privacyConfig).isAllowPrivacy();
        }
        return true;
    }

    public boolean e() {
        return this.a != null;
    }

    public Map<String, Boolean> getPrivacyMap() {
        return this.b;
    }

    public void updateConfig(DCloudAOLManager.PrivacyConfig privacyConfig) {
        this.a = privacyConfig;
        this.b.put(Const.PrivacyType.IS_ADULT, Boolean.valueOf(privacyConfig.isAdult()));
        this.b.put(Const.PrivacyType.IS_CAN_USE_PHONE_STATE, Boolean.valueOf(privacyConfig.isCanUsePhoneState()));
        this.b.put(Const.PrivacyType.IS_CAN_USE_STORAGE, Boolean.valueOf(privacyConfig.isCanUseStorage()));
        this.b.put(Const.PrivacyType.IS_CAN_USE_LOCATION, Boolean.valueOf(privacyConfig.isCanUseLocation()));
        this.b.put(Const.PrivacyType.IS_CAN_USE_WIFI_STATE, Boolean.valueOf(privacyConfig.isCanUseWifiState()));
        this.b.put(Const.PrivacyType.IS_CAN_GET_INSTALL_APP_LIST, Boolean.valueOf(privacyConfig.isCanGetInstallAppList()));
        this.b.put(Const.PrivacyType.IS_CAN_GET_RUNNING_APPS, Boolean.valueOf(privacyConfig.isCanGetRunningApps()));
        this.b.put(Const.PrivacyType.IS_CAN_GET_MAC, Boolean.valueOf(privacyConfig.isCanGetMacAddress()));
        this.b.put(Const.PrivacyType.IS_CAN_GET_ANDROID_ID, Boolean.valueOf(privacyConfig.isCanGetAndroidId()));
    }
}
