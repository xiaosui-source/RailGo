package io.dcloud.common.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;
import io.dcloud.application.DCLoudApplicationImpl;
import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.common.DHInterface.IFrameView;
import io.dcloud.common.DHInterface.INativeAppInfo;
import io.dcloud.common.DHInterface.IUniInstanceMgr;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaUniWebView;
import io.dcloud.common.adapter.ui.webview.DCWebView;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.EventActionInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.core.ui.TabBarWebview;
import io.dcloud.common.ui.PrivacyManager;
import io.dcloud.common.ui.blur.DCBlurDraweeView;
import io.dcloud.feature.internal.sdk.SDK;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class AppRuntime {
    private static int sBatteryLevel = 100;
    private static int sTemperature = 20;
    private static String sUniStatistics;

    public static void applyWebViewDarkMode(final Context context, final WebView webView) {
        Runnable runnable = new Runnable() { // from class: io.dcloud.common.util.AppRuntime.1
            @Override // java.lang.Runnable
            public void run() {
                int i = Build.VERSION.SDK_INT;
                if (i >= 29) {
                    try {
                        boolean appDarkMode = AppRuntime.getAppDarkMode(context);
                        int i2 = context.getApplicationInfo().targetSdkVersion;
                        if (i2 < 33 || !WebViewFeature.isFeatureSupported("ALGORITHMIC_DARKENING")) {
                            if (WebViewFeature.isFeatureSupported("FORCE_DARK")) {
                                if (appDarkMode) {
                                    WebSettingsCompat.setForceDark(webView.getSettings(), 2);
                                } else {
                                    WebSettingsCompat.setForceDark(webView.getSettings(), 0);
                                }
                            }
                        } else if (appDarkMode) {
                            AppCompatDelegate.setDefaultNightMode(2);
                            WebSettingsCompat.setAlgorithmicDarkeningAllowed(webView.getSettings(), false);
                        } else {
                            AppCompatDelegate.setDefaultNightMode(1);
                            WebSettingsCompat.setAlgorithmicDarkeningAllowed(webView.getSettings(), false);
                        }
                        if (i2 >= 33 || i >= 32 || !WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK_STRATEGY)) {
                            return;
                        }
                        WebSettingsCompat.setForceDarkStrategy(webView.getSettings(), 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            new Handler(Looper.getMainLooper()).post(runnable);
        } else {
            runnable.run();
        }
    }

    public static void checkPrivacyComplianceAndPrompt(Context context, String str) {
        String str2 = BaseInfo.sGlobalUserAgent;
    }

    private static List<View> findAllWebView(View view) {
        ArrayList arrayList = new ArrayList();
        if (view instanceof DCWebView) {
            arrayList.add(view);
            return arrayList;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                arrayList.addAll(findAllWebView(viewGroup.getChildAt(i)));
            }
        }
        return arrayList;
    }

    public static boolean getAppDarkMode(Context context) {
        String bundleData;
        if (Build.VERSION.SDK_INT < 29) {
            return false;
        }
        if (SDK.isUniMP) {
            bundleData = SDK.hostAppThemeDark;
        } else {
            bundleData = SP.getBundleData(context, SP.DARK_MODE_BUNDLE_FORMAT, SP.DARK_MODE_KEY);
            String metaValue = AndroidResources.getMetaValue("DCLOUD_DARK_MODE");
            if (PdrUtil.isEmpty(metaValue)) {
                metaValue = DCBlurDraweeView.LIGHT;
            }
            if (PdrUtil.isEmpty(bundleData)) {
                bundleData = metaValue;
            }
        }
        bundleData.getClass();
        return !bundleData.equals("auto") ? bundleData.equals(DCBlurDraweeView.DARK) : DeviceInfo.isSystemNightMode(context).booleanValue();
    }

    public static String getAppTheme(Context context) {
        String bundleData = SP.getBundleData(context, SP.DARK_MODE_BUNDLE_FORMAT, SP.DARK_MODE_KEY);
        return PdrUtil.isEmpty(bundleData) ? DCBlurDraweeView.LIGHT : bundleData;
    }

    public static int getBatteryLevel() {
        return sBatteryLevel;
    }

    public static String getDCloudDeviceID(Context context) {
        return TelephonyUtil.getDCloudDeviceID(context);
    }

    public static IUniInstanceMgr getInstanceMgr() {
        return (IUniInstanceMgr) PlatformUtil.invokeMethod("io.dcloud.feature.weex.WeexInstanceMgr", "self");
    }

    public static int getTemperature() {
        return sTemperature;
    }

    public static String getUniStatistics() {
        return sUniStatistics;
    }

    public static boolean hasPrivacyForNotShown(Context context) {
        return SDK.isUniMPSDK() ? SDK.uniMPSilentMode : PrivacyManager.getInstance().isNotPrivacyAllRight(context);
    }

    public static void initAppDarkMode(Context context) {
        if (context.getApplicationInfo().targetSdkVersion < 33 || Build.VERSION.SDK_INT < 32) {
            return;
        }
        if (getAppDarkMode(context)) {
            AppCompatDelegate.setDefaultNightMode(2);
        } else {
            AppCompatDelegate.setDefaultNightMode(1);
        }
    }

    public static void initUTS() throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Class<?> cls;
        Object obj;
        Method method;
        try {
            cls = Class.forName("io.dcloud.uts.android.AndroidUTSContext");
        } catch (ClassNotFoundException unused) {
            cls = null;
        }
        try {
            obj = cls.getField("INSTANCE").get(null);
        } catch (Exception e) {
            Logger.e("AppRuntime", "initUTS error " + e.toString());
            obj = null;
        }
        if (cls == null || obj == null) {
            return;
        }
        try {
            method = cls.getMethod("initApp", null);
        } catch (NoSuchMethodException unused2) {
            method = null;
        }
        if (method != null) {
            try {
                method.invoke(obj, null);
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (InvocationTargetException e3) {
                e3.printStackTrace();
            }
        }
    }

    public static void initUTSHooksClassArray(Application application) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            Class<?> cls = Class.forName("io.dcloud.uts.UTSAndroid");
            Method method = null;
            Object obj = cls.getField("INSTANCE").get(null);
            if (obj != null) {
                try {
                    Method declaredMethod = cls.getDeclaredMethod("initUTSHooksClassArray", Application.class);
                    declaredMethod.setAccessible(true);
                    method = declaredMethod;
                } catch (NoSuchMethodException unused) {
                }
                if (method != null) {
                    try {
                        method.invoke(obj, application);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        } catch (Exception unused2) {
        }
    }

    public static void initUniappPlugin(Application application) {
        IUniInstanceMgr instanceMgr = getInstanceMgr();
        if (instanceMgr != null) {
            instanceMgr.initUniappPlugin(application);
        }
    }

    public static void initWeex(INativeAppInfo iNativeAppInfo) {
        IUniInstanceMgr iUniInstanceMgr = (IUniInstanceMgr) PlatformUtil.invokeMethod("io.dcloud.feature.weex.WeexInstanceMgr", "self");
        if (iUniInstanceMgr != null) {
            iUniInstanceMgr.initWeexEnv(iNativeAppInfo);
        }
    }

    public static void initX5(Application application, boolean z, ICallBack iCallBack) {
        PlatformUtil.invokeMethod("io.dcloud.feature.x5.X5InitImpl", "init", null, new Class[]{Application.class, Boolean.class, ICallBack.class}, new Object[]{application, Boolean.valueOf(z), iCallBack});
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001b A[PHI: r7
      0x001b: PHI (r7v1 java.lang.String) = (r7v0 java.lang.String), (r7v4 java.lang.String) binds: [B:3:0x0005, B:8:0x0018] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean isAppResourcesInAssetsPath(android.content.Context r6, java.lang.String r7) {
        /*
            boolean r0 = io.dcloud.common.util.PdrUtil.isEmpty(r7)
            r1 = 0
            if (r0 == 0) goto L1b
            java.lang.String r7 = io.dcloud.common.util.BaseInfo.sDefaultBootApp
            boolean r7 = io.dcloud.common.util.PdrUtil.isEmpty(r7)
            if (r7 == 0) goto L12
            io.dcloud.common.util.BaseInfo.parseControl()
        L12:
            java.lang.String r7 = io.dcloud.common.util.BaseInfo.sDefaultBootApp
            boolean r0 = io.dcloud.common.util.PdrUtil.isEmpty(r7)
            if (r0 == 0) goto L1b
            goto L21
        L1b:
            boolean r0 = io.dcloud.common.util.BaseInfo.isBase(r6)
            if (r0 == 0) goto L22
        L21:
            return r1
        L22:
            boolean r0 = io.dcloud.common.util.BaseInfo.ISDEBUG
            r2 = 1
            if (r0 != 0) goto L30
            boolean r0 = io.dcloud.common.adapter.io.DHFile.hasFile()
            if (r0 == 0) goto L2e
            goto L30
        L2e:
            r0 = 0
            goto L31
        L30:
            r0 = 1
        L31:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "apps/"
            r3.<init>(r4)
            r3.append(r7)
            java.lang.String r4 = "/www/"
            r3.append(r4)
            java.lang.String r4 = io.dcloud.common.util.BaseInfo.sConfigXML
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            if (r0 == 0) goto L6c
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.io.File r4 = android.os.Environment.getExternalStorageDirectory()
            java.lang.String r4 = r4.getPath()
            r0.append(r4)
            java.lang.String r4 = "/Android/data/"
            r0.append(r4)
            java.lang.String r4 = r6.getPackageName()
            r0.append(r4)
            java.lang.String r0 = r0.toString()
            goto L74
        L6c:
            java.io.File r0 = r6.getFilesDir()
            java.lang.String r0 = r0.getPath()
        L74:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r0)
            java.lang.String r0 = "/"
            r4.append(r0)
            r4.append(r3)
            java.lang.String r0 = r4.toString()
            org.json.JSONObject r2 = io.dcloud.common.util.PdrUtil.getConfigData(r6, r7, r3, r2)     // Catch: java.lang.Exception -> Lbc
            java.lang.String r3 = "name"
            java.lang.String r4 = "version"
            java.lang.String r5 = ""
            if (r2 == 0) goto La4
            java.lang.String r2 = r2.getString(r4)     // Catch: java.lang.Exception -> Lbc
            org.json.JSONObject r2 = io.dcloud.common.util.JSONUtil.createJSONObject(r2)     // Catch: java.lang.Exception -> Lbc
            if (r2 == 0) goto La4
            java.lang.String r2 = r2.getString(r3)     // Catch: java.lang.Exception -> Lbc
            goto La5
        La4:
            r2 = r5
        La5:
            org.json.JSONObject r6 = io.dcloud.common.util.PdrUtil.getConfigData(r6, r7, r0, r1)     // Catch: java.lang.Exception -> Lbc
            if (r6 == 0) goto Lb7
            java.lang.String r6 = r6.getString(r4)     // Catch: java.lang.Exception -> Lbc
            org.json.JSONObject r6 = io.dcloud.common.util.JSONUtil.createJSONObject(r6)     // Catch: java.lang.Exception -> Lbc
            java.lang.String r5 = r6.getString(r3)     // Catch: java.lang.Exception -> Lbc
        Lb7:
            boolean r6 = io.dcloud.common.util.BaseInfo.BaseAppInfo.compareVersion(r2, r5)     // Catch: java.lang.Exception -> Lbc
            return r6
        Lbc:
            r6 = move-exception
            r6.printStackTrace()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.util.AppRuntime.isAppResourcesInAssetsPath(android.content.Context, java.lang.String):boolean");
    }

    public static boolean isUniApp(String str) {
        return !TextUtils.isEmpty(str) && str.startsWith("__UNI__");
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void lambda$switchAllWebViewDarkMode$0(View view) {
        if (view instanceof DCWebView) {
            ((DCWebView) view).applyWebViewDarkMode();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void lambda$switchAllWebViewDarkMode$1(IWebview iWebview) {
        ViewGroup viewGroupObtainWindowView = iWebview.obtainWindowView();
        if (iWebview instanceof TabBarWebview) {
            findAllWebView(viewGroupObtainWindowView).forEach(new Consumer() { // from class: io.dcloud.common.util.AppRuntime$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AppRuntime.lambda$switchAllWebViewDarkMode$0((View) obj);
                }
            });
        }
        if (viewGroupObtainWindowView instanceof DCWebView) {
            ((DCWebView) viewGroupObtainWindowView).applyWebViewDarkMode();
        }
    }

    public static void loadDex(Application application) {
        PlatformUtil.invokeMethod("io.dcloud.debug.DexSwap", "loadDex", null, new Class[]{Application.class}, new Object[]{application});
    }

    public static InputStream loadWeexAsset(String str, Context context) {
        if ((str.startsWith("weex-main-jsfm") || str.startsWith("uni-jsframework")) && str.endsWith(".js") && context.getPackageName().equals("io.dcloud.HBuilder") && context.getExternalCacheDir() != null) {
            File file = new File(context.getExternalCacheDir().getPath() + File.separator + str);
            if (file.exists()) {
                try {
                    return new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void onCreateProcess(Application application, Boolean bool, Boolean bool2) {
        IUniInstanceMgr instanceMgr = getInstanceMgr();
        if (instanceMgr != null) {
            if (!bool.booleanValue() || bool2.booleanValue()) {
                instanceMgr.onCreateProcess(application, bool);
            }
        }
    }

    public static void preInitX5(Application application) {
        PlatformUtil.invokeMethod("io.dcloud.feature.x5.X5InitImpl", "preInit", null, new Class[]{Application.class}, new Object[]{application});
    }

    public static void restartWeex(Application application, ICallBack iCallBack, String str) {
        IUniInstanceMgr instanceMgr = getInstanceMgr();
        if (instanceMgr != null) {
            instanceMgr.restartWeex(application, iCallBack, str);
        }
    }

    public static void setAppDarkMode(Activity activity, IWebview iWebview, String str) {
        if (Build.VERSION.SDK_INT < 29 || SDK.isUniMP || activity == null || PdrUtil.isEmpty(str)) {
            return;
        }
        if (!PdrUtil.isEquals(SP.getBundleData(activity, SP.DARK_MODE_BUNDLE_FORMAT, SP.DARK_MODE_KEY), str)) {
            if (iWebview instanceof AdaUniWebView) {
                HashMap map = new HashMap();
                map.put("uistyle", str);
                ((AdaUniWebView) iWebview).fireEvent(new EventActionInfo(AbsoluteConst.EVENTS_UISTYLECHANGE, map));
            } else {
                iWebview.loadUrl(StringUtil.format(AbsoluteConst.EVENTS_DOCUMENT_EXECUTE_TEMPLATE_PARAMETER_OF_UISTYLE, AbsoluteConst.EVENTS_UISTYLECHANGE, str));
            }
        }
        SP.setBundleData(activity, SP.DARK_MODE_BUNDLE_FORMAT, SP.DARK_MODE_KEY, str);
        switchAllWebViewDarkMode(null);
    }

    public static void setBatteryLevel(int i) {
        sBatteryLevel = i;
    }

    public static void setTemperature(int i) {
        sTemperature = i;
    }

    public static void setUniStatistics(String str) {
        sUniStatistics = str;
    }

    public static void switchAllWebViewDarkMode(ArrayList<? extends IFrameView> arrayList) {
        ArrayList<IWebview> arrayList2 = new ArrayList<>();
        if (arrayList == null || arrayList.isEmpty()) {
            arrayList2 = SDK.obtainAllIWebview();
        } else {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                IFrameView iFrameView = arrayList.get(i);
                i++;
                arrayList2.add(iFrameView.obtainWebView());
            }
        }
        if (arrayList2 == null || arrayList2.isEmpty()) {
            return;
        }
        arrayList2.forEach(new Consumer() { // from class: io.dcloud.common.util.AppRuntime$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AppRuntime.lambda$switchAllWebViewDarkMode$1((IWebview) obj);
            }
        });
    }

    public static void initWeex(Application application) {
        IUniInstanceMgr iUniInstanceMgr = (IUniInstanceMgr) PlatformUtil.invokeMethod("io.dcloud.feature.weex.WeexInstanceMgr", "self");
        if (iUniInstanceMgr != null) {
            iUniInstanceMgr.initWeexEnv(DCLoudApplicationImpl.self().getNativeInfo(application));
        }
    }
}
