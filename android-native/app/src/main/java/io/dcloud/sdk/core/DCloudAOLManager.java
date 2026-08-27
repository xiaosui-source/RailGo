package io.dcloud.sdk.core;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.webkit.WebView;
import io.dcloud.p.r0;
import io.dcloud.sdk.core.interfaces.AOLLoader;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
@Deprecated
/* loaded from: classes2.dex */
public class DCloudAOLManager {
    private static String a = "";
    protected static AOLLoader b;
    protected static AtomicBoolean c = new AtomicBoolean(false);
    private static AtomicBoolean d = new AtomicBoolean(false);

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class InitConfig {
        private String a;
        private String b;
        private String c;
        private String d;
        private boolean e = false;

        public String getAdId() {
            return this.b;
        }

        public String getAppId() {
            return this.a;
        }

        public String getName() {
            return this.d;
        }

        public String getVersion() {
            return this.c;
        }

        public boolean isDebug() {
            return this.e;
        }

        public InitConfig setAdId(String str) {
            this.b = str;
            return this;
        }

        public InitConfig setAppId(String str) {
            this.a = str;
            return this;
        }

        public void setDebug(boolean z) {
            this.e = z;
        }

        public InitConfig setName(String str) {
            this.d = str;
            return this;
        }

        public InitConfig setVersion(String str) {
            this.c = str;
            return this;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static abstract class PrivacyConfig {
        public boolean isAdult() {
            return true;
        }

        public boolean isCanGetAndroidId() {
            return true;
        }

        public boolean isCanGetIP() {
            return true;
        }

        public boolean isCanGetInstallAppList() {
            return true;
        }

        public boolean isCanGetMacAddress() {
            return true;
        }

        public boolean isCanGetOAID() {
            return true;
        }

        public boolean isCanGetRunningApps() {
            return true;
        }

        public boolean isCanUseLocation() {
            return true;
        }

        public boolean isCanUsePhoneState() {
            return true;
        }

        public boolean isCanUseRecordPermission() {
            return true;
        }

        public boolean isCanUseSensor() {
            return true;
        }

        public boolean isCanUseSimOperator() {
            return true;
        }

        public boolean isCanUseStorage() {
            return true;
        }

        public boolean isCanUseWifiState() {
            return true;
        }

        public boolean isGDTAgreeStrategy() {
            return true;
        }
    }

    public static boolean getPersonalAd(Context context) {
        AOLLoader aOLLoader = b;
        if (aOLLoader == null) {
            return false;
        }
        return aOLLoader.getPersonalAOL(context);
    }

    public static String getVersion() {
        return a;
    }

    public static void init(Context context, InitConfig initConfig) {
        if (context == null || initConfig == null) {
            throw new NullPointerException("context or config is null");
        }
        if (((context instanceof Activity) || (context instanceof Application)) && !c.get()) {
            c.set(true);
            if (b == null) {
                r0 r0VarD = r0.d();
                r0VarD.b(context);
                r0VarD.a(initConfig);
                b = r0VarD;
                r0VarD.e();
                r0VarD.a(context);
            }
        }
    }

    public static void initWebViewWithMultiProcess(Context context) {
        if (Build.VERSION.SDK_INT >= 28) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
                if (runningAppProcessInfo.pid == Process.myPid()) {
                    if (runningAppProcessInfo.processName.equals(context.getPackageName())) {
                        WebView.setDataDirectorySuffix(runningAppProcessInfo.processName);
                        return;
                    }
                    return;
                }
            }
        }
    }

    public static boolean isInit() {
        return c.get();
    }

    public static void setPersonalAd(Context context, boolean z) {
        AOLLoader aOLLoader = b;
        if (aOLLoader != null) {
            aOLLoader.setPersonalAOL(context, z);
        }
    }

    public static void setPrivacyConfig(PrivacyConfig privacyConfig) {
        AOLLoader aOLLoader = b;
        if (aOLLoader == null) {
            throw new RuntimeException("please init first");
        }
        if (privacyConfig == null) {
            throw new RuntimeException("config is null");
        }
        aOLLoader.setPrivacyConfig(privacyConfig);
    }

    public static void updatePrivacyConfig(Context context, JSONObject jSONObject) {
        if (jSONObject == null) {
            throw new RuntimeException("config is null");
        }
        b.updatePrivacyConfig(context, jSONObject);
    }
}
