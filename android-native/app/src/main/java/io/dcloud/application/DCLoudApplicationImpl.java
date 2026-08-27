package io.dcloud.application;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.webkit.WebView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import io.dcloud.common.DHInterface.INativeAppInfo;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.UEH;
import io.dcloud.common.ui.PrivacyManager;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.NativeCrashManager;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.RuningAcitvityUtil;
import io.dcloud.common.util.TelephonyUtil;
import io.dcloud.common.util.language.LanguageUtil;
import io.dcloud.feature.internal.sdk.SDK;
import io.dcloud.p.d1;
import io.dcloud.p.h3;
import io.dcloud.p.k;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class DCLoudApplicationImpl {
    private static DCLoudApplicationImpl mInstance;
    private ActivityCallbacks activityCallbacks;
    private Context mApplication;
    private h3 nativeAppInfo;
    private String Tag = "DCLoudApplicationImpl";
    boolean isUniMP = false;
    private boolean isInit = false;
    public ConcurrentHashMap<String, WeakReference<Activity>> topActiveMap = new ConcurrentHashMap<>();
    public Activity topActivity = null;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    private class ActivityCallbacks implements Application.ActivityLifecycleCallbacks {
        private int activityStartCount;
        private volatile boolean isBack;
        private boolean isStop;
        private List<ActivityStatusListener> listeners;

        private ActivityCallbacks() {
            this.activityStartCount = 0;
            this.isBack = false;
            this.isStop = false;
        }

        public void addListener(ActivityStatusListener activityStatusListener) {
            if (this.listeners == null) {
                this.listeners = new ArrayList();
            }
            this.listeners.add(activityStatusListener);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
            DCLoudApplicationImpl dCLoudApplicationImpl = DCLoudApplicationImpl.this;
            if (dCLoudApplicationImpl.topActivity == activity) {
                dCLoudApplicationImpl.topActivity = null;
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
            try {
                DCLoudApplicationImpl.this.topActiveMap.remove(activity.getComponentName().getClassName());
            } catch (Exception unused) {
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            try {
                DCLoudApplicationImpl.this.topActiveMap.put(activity.getComponentName().getClassName(), new WeakReference<>(activity));
                DCLoudApplicationImpl.this.topActivity = activity;
            } catch (Exception unused) {
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
            if (this.isStop) {
                this.isStop = false;
            } else if (this.activityStartCount == 0 && this.isBack) {
                this.isBack = false;
                List<ActivityStatusListener> list = this.listeners;
                if (list != null && !list.isEmpty()) {
                    for (ActivityStatusListener activityStatusListener : this.listeners) {
                        if (activityStatusListener != null) {
                            activityStatusListener.onFront();
                        }
                    }
                }
            }
            this.activityStartCount++;
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
            this.activityStartCount--;
        }

        public void onApp2Back() {
            if (this.isStop) {
                return;
            }
            if (!this.isBack) {
                Logger.d("DCloud_uniAd", "app is in back");
                List<ActivityStatusListener> list = this.listeners;
                if (list != null && !list.isEmpty()) {
                    for (ActivityStatusListener activityStatusListener : this.listeners) {
                        if (activityStatusListener != null) {
                            activityStatusListener.onBack();
                        }
                    }
                }
            }
            this.isBack = true;
        }

        public void removeListener(ActivityStatusListener activityStatusListener) {
            List<ActivityStatusListener> list = this.listeners;
            if (list != null) {
                list.remove(activityStatusListener);
            }
        }

        public void stopListener() {
            this.isStop = true;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface ActivityStatusListener {
        void onBack();

        void onFront();
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public class DynamicLanguageReceiver extends BroadcastReceiver {
        public DynamicLanguageReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (context == null) {
                return;
            }
            Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
            launchIntentForPackage.setFlags(268468224);
            context.startActivity(launchIntentForPackage);
            Process.killProcess(Process.myPid());
        }
    }

    private void initLanguageConfig(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LanguageUtil.LanguageBroadCastIntent);
        LocalBroadcastManager.getInstance(self().getContext()).registerReceiver(new DynamicLanguageReceiver(), intentFilter);
        if (Build.VERSION.SDK_INT < 26) {
            LanguageUtil.initAppLanguageForAppBeforeO(context);
        }
    }

    private void initX5(Application application, boolean z) {
        if (z) {
            AppRuntime.initX5(application, BaseInfo.allowDownloadWithoutWiFi, null);
        }
    }

    public static DCLoudApplicationImpl self() {
        if (mInstance == null) {
            mInstance = new DCLoudApplicationImpl();
        }
        return mInstance;
    }

    public void addActivityStatusListener(ActivityStatusListener activityStatusListener) {
        ActivityCallbacks activityCallbacks = this.activityCallbacks;
        if (activityCallbacks != null) {
            activityCallbacks.addListener(activityStatusListener);
        }
    }

    protected Context attachBaseContext(Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            context = LanguageUtil.updateContextLanguageAfterO(context, true, false);
        }
        if (!SDK.isUniMPSDK()) {
            k.a(context);
        }
        return context;
    }

    public Context getContext() {
        return this.mApplication;
    }

    public INativeAppInfo getNativeInfo(Application application) {
        if (this.nativeAppInfo == null) {
            this.nativeAppInfo = new h3(application);
        }
        return this.nativeAppInfo;
    }

    public void init(Application application, boolean z) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        this.isUniMP = z;
        if (z && Build.VERSION.SDK_INT >= 26) {
            LanguageUtil.updateContextLanguageAfterO(application, true);
        }
        SDK.isUniMP = this.isUniMP;
        onCreate(application);
        if (BaseInfo.isBase(application) && this.isInit) {
            webviewSetPath(application, true);
        }
        if (z) {
            DeviceInfo.initGsmCdmaCell();
            TelephonyUtil.updateIMEI(application);
        }
    }

    public boolean isInit() {
        return this.isInit;
    }

    public boolean isMainProcess(Context context, boolean z) {
        if (!z) {
            return true;
        }
        boolean zEquals = context.getPackageName().equals(RuningAcitvityUtil.getAppName(context));
        if (!zEquals && !this.isUniMP) {
            if (RuningAcitvityUtil.getAppName(context).startsWith(context.getPackageName() + ":unimp")) {
                this.isUniMP = true;
                SDK.isUniMP = true;
            }
        }
        return zEquals;
    }

    public void onApp2Back() {
        ActivityCallbacks activityCallbacks = this.activityCallbacks;
        if (activityCallbacks != null) {
            activityCallbacks.onApp2Back();
        }
    }

    public void onCreate(Application application) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (this.isInit) {
            return;
        }
        AndroidResources.initAndroidResources(application.getBaseContext());
        BaseInfo.parseControl();
        DeviceInfo.initPath(application, false);
        boolean zHasPrivacyForNotShown = AppRuntime.hasPrivacyForNotShown(application);
        boolean z = !zHasPrivacyForNotShown;
        if (!zHasPrivacyForNotShown) {
            DeviceInfo.init(application);
            DeviceInfo.initPath(application);
            NativeCrashManager.initNativeCrash(application);
        }
        boolean zIsMainProcess = isMainProcess(application, z);
        webviewSetPath(application, z);
        this.isInit = true;
        PdrUtil.closeAndroidPDialog();
        INativeAppInfo nativeInfo = getNativeInfo(application);
        d1.a(nativeInfo);
        BaseInfo.isFirstRun = true;
        AppRuntime.onCreateProcess(application, Boolean.valueOf(zIsMainProcess), Boolean.valueOf(z));
        if ((!SDK.isUniMPSDK() && zIsMainProcess) || (SDK.isUniMPSDK() && this.isUniMP)) {
            if (!BaseInfo.SyncDebug || (SDK.isUniMPSDK() && this.isUniMP)) {
                AppRuntime.initWeex(nativeInfo);
            }
            initX5(application, z);
        }
        if (!SDK.isUniMPSDK()) {
            k.a(application);
        }
        this.mApplication = application;
        setContext(application);
        UEH.catchUncaughtException(application);
        UEH.uploadNativeUncaughtException(application);
        if (PdrUtil.isSupportOaid()) {
            try {
                Method declaredMethod = Class.forName("com.bun.miitmdid.core.JLibrary").getDeclaredMethod("InitEntry", Context.class);
                if (declaredMethod != null) {
                    declaredMethod.setAccessible(true);
                    declaredMethod.invoke(null, application);
                }
            } catch (Exception unused) {
            }
        }
        ActivityCallbacks activityCallbacks = new ActivityCallbacks();
        this.activityCallbacks = activityCallbacks;
        application.registerActivityLifecycleCallbacks(activityCallbacks);
        initLanguageConfig(getContext());
        PrivacyManager.getInstance().init(self().getContext());
    }

    public void removeActivityStatusListener(ActivityStatusListener activityStatusListener) {
        ActivityCallbacks activityCallbacks = this.activityCallbacks;
        if (activityCallbacks != null) {
            activityCallbacks.removeListener(activityStatusListener);
        }
    }

    public void setContext(Context context) {
        if (this.mApplication == null) {
            this.mApplication = context;
        }
    }

    public void stopActivityStatusListener() {
        ActivityCallbacks activityCallbacks = this.activityCallbacks;
        if (activityCallbacks != null) {
            activityCallbacks.stopListener();
        }
    }

    protected void supportMultiDex(Context context) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            Class.forName("androidx.multidex.MultiDex").getMethod("install", Context.class).invoke(null, context);
        } catch (Exception unused) {
        }
    }

    public void webviewSetPath(Context context, boolean z) {
        try {
            if (Build.VERSION.SDK_INT < 28 || !SDK.isUniMPSDK() || isMainProcess(context, z)) {
                return;
            }
            WebView.setDataDirectorySuffix(RuningAcitvityUtil.getAppName(context));
        } catch (Exception unused) {
        }
    }
}
