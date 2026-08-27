package com.taobao.weex;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import com.taobao.weex.common.Constants;
import com.taobao.weex.common.WXConfig;
import com.taobao.weex.utils.FontDO;
import com.taobao.weex.utils.LogLevel;
import com.taobao.weex.utils.TypefaceUtil;
import com.taobao.weex.utils.WXFileUtils;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXSoInstallMgrSdk;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import dalvik.system.PathClassLoader;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.feature.uniapp.utils.AbsLogLevel;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXEnvironment {
    public static boolean AUTO_ADJUST_ENV_DEVICE_WIDTH = false;
    public static boolean AUTO_UPDATE_APPLICATION_SCREEN_SIZE = false;
    private static String COPY_SO_DES_DIR = null;
    public static final String CORE_JSB_SO_NAME = "weexjsb";
    public static String CORE_JSB_SO_PATH = null;
    public static final String CORE_JSC_SO_NAME = "jsc";
    private static String CORE_JSC_SO_PATH = null;
    private static String CORE_JSS_ICU_PATH = null;
    public static String CORE_JSS_RUNTIME_SO_PATH = null;
    public static final String CORE_JSS_SO_NAME = "weexjss";
    private static String CORE_JSS_SO_PATH = null;
    public static final String CORE_JST_SO_NAME = "weexjst";
    public static final String CORE_SO_NAME = "weexcore";
    public static final String DEV_Id;
    public static final String EAGLE = "eagle";
    public static final String ENVIRONMENT = "environment";
    public static String JS_LIB_SDK_VERSION = null;
    public static volatile boolean JsFrameworkInit = false;
    private static String LIB_LD_PATH = null;
    public static final String OS = "android";
    public static final String SETTING_EXCLUDE_X86SUPPORT = "env_exclude_x86";
    public static boolean SETTING_FORCE_VERTICAL_SCREEN = false;
    public static final String SYS_MODEL;
    public static String SYS_VERSION = null;
    public static final String WEEX_CURRENT_KEY = "wx_current_url";
    public static String WXSDK_VERSION = null;
    private static boolean isApkDebug = false;
    public static boolean isPerf = false;
    public static volatile boolean isWsFixMode = false;
    private static float mViewProt = 0.0f;
    private static WXDefaultSettings mWXDefaultSettings = null;
    private static boolean openDebugLog = false;
    private static Map<String, String> options = null;
    public static Application sApplication = null;
    public static long sComponentsAndModulesReadyTime = 0;
    private static boolean sDebugFlagInit = false;
    public static boolean sDebugMode = false;
    public static boolean sDebugNetworkEventReporterEnable = false;
    public static boolean sDebugServerConnectable = false;
    public static String sDebugWsUrl = null;

    @Deprecated
    public static int sDefaultWidth = 0;
    public static boolean sDynamicMode = false;
    public static String sDynamicUrl = null;
    public static final boolean sForceEnableDevTool = true;
    private static String sGlobalFontFamily;
    public static boolean sInAliWeex;
    public static long sJSFMStartListenerTime;
    public static long sJSLibInitTime;
    public static AbsLogLevel sLogLevel;
    public static boolean sRemoteDebugMode;
    public static String sRemoteDebugProxyUrl;
    public static long sSDKInitExecuteTime;
    public static long sSDKInitInvokeTime;
    public static long sSDKInitStart;
    public static long sSDKInitTime;
    public static volatile boolean sUseRunTimeApi;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class WXDefaultSettings {
        private String configName = "weex_default_settings";
        private SharedPreferences sharedPreferences;

        public WXDefaultSettings(Application application) {
            this.sharedPreferences = null;
            if (application != null) {
                this.sharedPreferences = application.getSharedPreferences("weex_default_settings", 0);
            }
        }

        public synchronized String getValue(String str, String str2) {
            if (this.sharedPreferences != null && !TextUtils.isEmpty(str)) {
                String string = this.sharedPreferences.getString(str, str2);
                WXLogUtils.i("get default settings " + str + " : " + string);
                return string;
            }
            WXLogUtils.i("get default settings " + str + " return default value :" + str2);
            return str2;
        }

        public synchronized void saveValue(String str, String str2) {
            if (this.sharedPreferences != null && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                WXLogUtils.i("save default settings " + str + ":" + str2);
                SharedPreferences.Editor editorEdit = this.sharedPreferences.edit();
                editorEdit.putString(str, str2);
                editorEdit.apply();
            }
        }
    }

    static {
        String str = Build.VERSION.RELEASE;
        SYS_VERSION = str;
        if (str != null && str.toUpperCase(Locale.ROOT).equals("P")) {
            SYS_VERSION = "9.0.0";
        }
        String str2 = SYS_VERSION;
        if (str2 != null && str2.toUpperCase(Locale.ROOT).equals("Q")) {
            SYS_VERSION = "10.0.0";
        }
        SYS_MODEL = Build.MODEL;
        JS_LIB_SDK_VERSION = BuildConfig.buildJavascriptFrameworkVersion;
        WXSDK_VERSION = BuildConfig.buildVersion;
        DEV_Id = getDevId();
        sDefaultWidth = 750;
        JsFrameworkInit = false;
        SETTING_FORCE_VERTICAL_SCREEN = false;
        AUTO_ADJUST_ENV_DEVICE_WIDTH = true;
        AUTO_UPDATE_APPLICATION_SCREEN_SIZE = true;
        sUseRunTimeApi = false;
        sDebugMode = false;
        sDebugWsUrl = "";
        sDebugServerConnectable = false;
        sRemoteDebugMode = false;
        sRemoteDebugProxyUrl = "";
        sDebugNetworkEventReporterEnable = false;
        sJSLibInitTime = 0L;
        sSDKInitStart = 0L;
        sSDKInitInvokeTime = 0L;
        sSDKInitExecuteTime = 0L;
        sSDKInitTime = 0L;
        sJSFMStartListenerTime = 0L;
        isWsFixMode = true;
        sComponentsAndModulesReadyTime = 0L;
        sInAliWeex = false;
        sLogLevel = LogLevel.DEBUG;
        isApkDebug = true;
        isPerf = false;
        sDebugFlagInit = false;
        openDebugLog = true;
        CORE_JSS_SO_PATH = null;
        CORE_JSS_RUNTIME_SO_PATH = null;
        CORE_JSS_ICU_PATH = null;
        CORE_JSC_SO_PATH = null;
        CORE_JSB_SO_PATH = null;
        COPY_SO_DES_DIR = null;
        LIB_LD_PATH = null;
        mViewProt = 750.0f;
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        options = concurrentHashMap;
        concurrentHashMap.put(WXConfig.os, OS);
        options.put(WXConfig.osName, OS);
        sDynamicMode = false;
        sDynamicUrl = "";
    }

    public static void addCustomOptions(String str, String str2) {
        options.put(str, str2);
    }

    public static String copySoDesDir() {
        File file;
        try {
            if (TextUtils.isEmpty(COPY_SO_DES_DIR)) {
                if (sApplication == null) {
                    WXLogUtils.e("sApplication is null, so copy path will be null");
                    return null;
                }
                String path = getApplication().getApplicationContext().getCacheDir().getPath();
                if (TextUtils.isEmpty(path)) {
                    file = new File("/data/data/" + sApplication.getPackageName() + "/cache/weex/libs");
                } else {
                    file = new File(path, "/cache/weex/libs");
                }
                if (!file.exists()) {
                    file.mkdirs();
                }
                COPY_SO_DES_DIR = file.getAbsolutePath();
            }
        } catch (Throwable th) {
            WXLogUtils.e(WXLogUtils.getStackTrace(th));
        }
        return COPY_SO_DES_DIR;
    }

    public static boolean extractSo() {
        ArrayList arrayList = new ArrayList();
        String[] strArr = getApplication().getApplicationContext().getApplicationInfo().splitSourceDirs;
        if (strArr != null) {
            for (String str : strArr) {
                if (str.contains(Build.CPU_ABI)) {
                    arrayList.add(0, str);
                } else {
                    arrayList.add(str);
                }
            }
        }
        File file = new File(getApplication().getApplicationContext().getApplicationInfo().sourceDir);
        if (file.exists()) {
            arrayList.add(file.getAbsolutePath());
        }
        String strCopySoDesDir = copySoDesDir();
        if (arrayList.size() > 0 && !TextUtils.isEmpty(strCopySoDesDir)) {
            try {
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    if (WXFileUtils.extractSo((String) obj, strCopySoDesDir)) {
                        return true;
                    }
                }
                return true;
            } catch (IOException e) {
                WXLogUtils.e("extractSo error " + e.getMessage());
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x004f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.String findIcuPath() throws java.lang.Throwable {
        /*
            java.io.File r0 = new java.io.File
            java.lang.String r1 = "/proc/self/maps"
            r0.<init>(r1)
            r1 = 0
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f
            java.io.FileReader r3 = new java.io.FileReader     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f
            r3.<init>(r0)     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f
        L12:
            java.lang.String r0 = r2.readLine()     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3b
            if (r0 == 0) goto L32
            java.lang.String r3 = "icudt"
            boolean r3 = r0.contains(r3)     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3b
            if (r3 == 0) goto L12
            r3 = 47
            int r3 = r0.indexOf(r3)     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3b
            java.lang.String r0 = r0.substring(r3)     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3b
            java.lang.String r0 = r0.trim()     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3b
            r2.close()     // Catch: java.io.IOException -> L31
        L31:
            return r0
        L32:
            r2.close()     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3b
            r2.close()     // Catch: java.io.IOException -> L49
            goto L49
        L39:
            r0 = move-exception
            goto L4d
        L3b:
            r0 = move-exception
            goto L41
        L3d:
            r0 = move-exception
            goto L4c
        L3f:
            r0 = move-exception
            r2 = r1
        L41:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L4a
            if (r2 == 0) goto L49
            r2.close()     // Catch: java.io.IOException -> L49
        L49:
            return r1
        L4a:
            r0 = move-exception
            r1 = r2
        L4c:
            r2 = r1
        L4d:
            if (r2 == 0) goto L52
            r2.close()     // Catch: java.io.IOException -> L52
        L52:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.WXEnvironment.findIcuPath():java.lang.String");
    }

    public static String findSoPath(String str) {
        String strFindLibrary = ((PathClassLoader) WXEnvironment.class.getClassLoader()).findLibrary(str);
        if (!TextUtils.isEmpty(strFindLibrary)) {
            File file = new File(strFindLibrary);
            if (file.exists()) {
                WXLogUtils.e(str + "'s Path is" + strFindLibrary);
                return file.getAbsolutePath();
            }
            WXLogUtils.e(str + "'s Path is " + strFindLibrary + " but file does not exist");
        }
        String str2 = "lib" + str + ".so";
        String cacheDir = getCacheDir();
        if (TextUtils.isEmpty(cacheDir)) {
            WXLogUtils.e("cache dir is null");
            return "";
        }
        if (cacheDir.indexOf("/cache") > 0) {
            strFindLibrary = new File(cacheDir.replace("/cache", "/lib"), str2).getAbsolutePath();
        }
        if (!new File(strFindLibrary).exists()) {
            File file2 = new File(copySoDesDir(), str2);
            return file2.exists() ? file2.getAbsolutePath() : extractSo() ? new File(getCacheDir(), str2).getAbsolutePath() : strFindLibrary;
        }
        WXLogUtils.e(str + "use lib so");
        return strFindLibrary;
    }

    public static Application getApplication() {
        return sApplication;
    }

    public static String getCacheDir() {
        Application application = getApplication();
        if (application == null || application.getApplicationContext() == null) {
            return null;
        }
        return application.getApplicationContext().getCacheDir().getPath();
    }

    public static Map<String, String> getConfig() {
        Application application;
        HashMap map = new HashMap();
        map.put(WXConfig.os, OS);
        map.put(WXConfig.appVersion, getAppVersionName());
        map.put(WXConfig.cacheDir, getAppCacheFile());
        map.put(WXConfig.devId, DEV_Id);
        map.put(WXConfig.sysVersion, SYS_VERSION);
        map.put(WXConfig.sysModel, SYS_MODEL);
        map.put(WXConfig.weexVersion, String.valueOf(WXSDK_VERSION));
        if (sRemoteDebugMode) {
            map.put(WXConfig.logLevel, "log");
        } else {
            map.put(WXConfig.logLevel, sLogLevel.getName());
        }
        try {
            map.put(WXConfig.layoutDirection, isLayoutDirectionRTL() ? Constants.Name.RTL : "ltr");
        } catch (Exception unused) {
            map.put(WXConfig.layoutDirection, "ltr");
        }
        try {
            if (isApkDebugable()) {
                addCustomOptions(WXConfig.debugMode, AbsoluteConst.TRUE);
            }
            addCustomOptions("scale", Float.toString(sApplication.getResources().getDisplayMetrics().density));
            addCustomOptions(WXConfig.androidStatusBarHeight, Float.toString(WXViewUtils.getStatusBarHeight(sApplication)));
        } catch (NullPointerException e) {
            WXLogUtils.e("WXEnvironment scale Exception: ", e);
        }
        map.putAll(getCustomOptions());
        if (map.get(WXConfig.appName) == null && (application = sApplication) != null) {
            map.put(WXConfig.appName, application.getPackageName());
        }
        return map;
    }

    @Deprecated
    public static Map<String, String> getCustomOptions() {
        return options;
    }

    public static synchronized String getDefaultSettingValue(String str, String str2) {
        WXDefaultSettings wXDefaultSettings = getWXDefaultSettings();
        if (wXDefaultSettings != null && !TextUtils.isEmpty(str)) {
            return wXDefaultSettings.getValue(str, str2);
        }
        return str2;
    }

    private static String getDevId() {
        return "";
    }

    public static String getDiskCacheDir(Context context) {
        if (context == null) {
            return null;
        }
        try {
            if (!"mounted".equals(Environment.getExternalStorageState()) && Environment.isExternalStorageRemovable()) {
                return context.getCacheDir().getPath();
            }
            return context.getExternalCacheDir().getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFilesDir(Context context) {
        if (context == null) {
            return "";
        }
        File filesDir = context.getFilesDir();
        if (filesDir != null) {
            return filesDir.getPath();
        }
        return (getApplication().getApplicationInfo().dataDir + File.separator) + "files";
    }

    public static String getGlobalFontFamilyName() {
        return sGlobalFontFamily;
    }

    public static String getLibJScRealPath() {
        return "";
    }

    public static String getLibJssIcuPath() {
        if (TextUtils.isEmpty(CORE_JSS_ICU_PATH)) {
            CORE_JSS_ICU_PATH = findIcuPath();
        }
        return CORE_JSS_ICU_PATH;
    }

    public static String getLibJssRealPath() {
        if (sUseRunTimeApi && !TextUtils.isEmpty(CORE_JSS_RUNTIME_SO_PATH)) {
            WXLogUtils.d("test-> findLibJssRuntimeRealPath " + CORE_JSS_RUNTIME_SO_PATH);
            return CORE_JSS_RUNTIME_SO_PATH;
        }
        if (TextUtils.isEmpty(CORE_JSS_SO_PATH)) {
            CORE_JSS_SO_PATH = findSoPath(CORE_JSS_SO_NAME);
            WXLogUtils.d("test-> findLibJssRealPath " + CORE_JSS_SO_PATH);
        }
        return CORE_JSS_SO_PATH;
    }

    public static String getLibLdPath() {
        if (TextUtils.isEmpty(LIB_LD_PATH)) {
            ClassLoader classLoader = WXEnvironment.class.getClassLoader();
            try {
                LIB_LD_PATH = (String) classLoader.getClass().getMethod("getLdLibraryPath", null).invoke(classLoader, null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
            } catch (InvocationTargetException e3) {
                e3.printStackTrace();
            }
        }
        if (TextUtils.isEmpty(LIB_LD_PATH)) {
            try {
                String property = System.getProperty("java.library.path");
                String libJScRealPath = getLibJScRealPath();
                if (!TextUtils.isEmpty(libJScRealPath)) {
                    LIB_LD_PATH = new File(libJScRealPath).getParent() + ":" + property;
                }
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
        WXLogUtils.d("getLibLdPath is " + LIB_LD_PATH);
        return LIB_LD_PATH;
    }

    public static float getViewProt() {
        return mViewProt;
    }

    public static synchronized WXDefaultSettings getWXDefaultSettings() {
        if (mWXDefaultSettings == null && getApplication() != null) {
            mWXDefaultSettings = new WXDefaultSettings(getApplication());
        }
        return mWXDefaultSettings;
    }

    public static boolean isApkDebugable() {
        return isApkDebugable(sApplication);
    }

    public static boolean isCPUSupport() {
        boolean z = WXSoInstallMgrSdk.isX86() && AbsoluteConst.TRUE.equals(getCustomOptions().get(SETTING_EXCLUDE_X86SUPPORT));
        boolean z2 = WXSoInstallMgrSdk.isCPUSupport() && !z;
        if (isApkDebugable()) {
            WXLogUtils.d("WXEnvironment.sSupport:" + z2 + "isX86AndExclueded: " + z);
        }
        return z2;
    }

    @Deprecated
    public static boolean isHardwareSupport() {
        if (isApkDebugable()) {
            WXLogUtils.d("isTableDevice:" + WXUtils.isTabletDevice());
        }
        return isCPUSupport();
    }

    public static boolean isOpenDebugLog() {
        return openDebugLog;
    }

    public static boolean isPerf() {
        return isPerf;
    }

    @Deprecated
    public static boolean isSupport() {
        boolean zIsInitialized = WXSDKEngine.isInitialized();
        if (!zIsInitialized) {
            WXLogUtils.e("WXSDKEngine.isInitialized():" + zIsInitialized);
        }
        return isHardwareSupport() && zIsInitialized;
    }

    public static void setApkDebugable(boolean z) {
        isApkDebug = z;
        if (z) {
            return;
        }
        openDebugLog = false;
    }

    public static void setGlobalFontFamily(String str, Typeface typeface) {
        WXLogUtils.d("GlobalFontFamily", "Set global font family: " + str);
        sGlobalFontFamily = str;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (typeface == null) {
            TypefaceUtil.removeFontDO(str);
            return;
        }
        TypefaceUtil.putFontDO(new FontDO(str, typeface));
        WXLogUtils.d("TypefaceUtil", "Add new font: " + str);
    }

    public static void setOpenDebugLog(boolean z) {
        openDebugLog = z;
    }

    public static void setViewProt(float f) {
        mViewProt = f;
    }

    public static synchronized void writeDefaultSettingsValue(String str, String str2) {
        WXDefaultSettings wXDefaultSettings = getWXDefaultSettings();
        if (wXDefaultSettings != null && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            wXDefaultSettings.saveValue(str, str2);
        }
    }

    public void initMetrics() {
    }

    public static String getCustomOptions(String str) {
        return options.get(str);
    }

    public static boolean isApkDebugable(Application application) {
        if (application == null || isPerf) {
            return false;
        }
        if (sDebugFlagInit) {
            return isApkDebug;
        }
        if (!isApkDebug && BaseInfo.SyncDebug) {
            isApkDebug = true;
        } else if (isApkDebug && !BaseInfo.SyncDebug) {
            isApkDebug = false;
        }
        sDebugFlagInit = true;
        return isApkDebug;
    }

    public static boolean isLayoutDirectionRTL() {
        return sApplication.getApplicationContext().getResources().getBoolean(R.bool.weex_is_right_to_left);
    }

    private static String getAppCacheFile() {
        try {
            return sApplication.getApplicationContext().getCacheDir().getPath();
        } catch (Exception e) {
            WXLogUtils.e("WXEnvironment getAppCacheFile Exception: ", e);
            return "";
        }
    }

    public static String getCrashFilePath(Context context) {
        File dir;
        return (context == null || (dir = context.getDir(IApp.ConfigProperty.CONFIG_CRASH, 0)) == null) ? "" : dir.getAbsolutePath();
    }

    public static String getAppVersionName() {
        try {
            return sApplication.getPackageManager().getPackageInfo(sApplication.getPackageName(), 0).versionName;
        } catch (Exception e) {
            WXLogUtils.e("WXEnvironment getAppVersionName Exception: ", e);
            return "";
        }
    }
}
