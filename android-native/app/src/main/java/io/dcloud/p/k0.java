package io.dcloud.p;

import android.app.Activity;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.common.WXConfig;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IActivityHandler;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IBoot;
import io.dcloud.common.DHInterface.ICore;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.IOnCreateSplashView;
import io.dcloud.common.DHInterface.ISysEventListener;
import io.dcloud.common.adapter.ui.webview.WebViewFactory;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.common.adapter.util.AsyncTaskHandler;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.DownloadUtil;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.MessageHandler;
import io.dcloud.common.adapter.util.MobilePhoneModel;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.IntentConst;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.TestUtil;
import io.dcloud.common.util.db.DCStorage;
import io.dcloud.common.util.language.LanguageUtil;
import io.dcloud.common.util.net.NetMgr;
import io.dcloud.feature.internal.sdk.SDK;
import io.src.dcloud.adapter.DCloudAdapterUtil;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
class k0 implements ICore {
    private static k0 o;
    boolean a = false;
    Context b = null;
    Context c = null;
    AbsMgr d = null;
    AbsMgr e = null;
    AbsMgr f = null;
    AbsMgr g = null;
    String h = "CORE";
    private ICore.ICoreStatusListener i = null;
    HashMap j = null;
    final int k = 0;
    final int l = 1;
    final int m = 2;
    final int n = 3;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements MessageHandler.IMessages {
        final /* synthetic */ IApp a;
        final /* synthetic */ Activity b;
        final /* synthetic */ String c;

        a(IApp iApp, Activity activity, String str) {
            this.a = iApp;
            this.b = activity;
            this.c = str;
        }

        @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
        public void execute(Object obj) {
            IApp iApp = this.a;
            if (iApp != null) {
                k0.this.a(this.b, iApp);
            }
            Object objDispatchEvent = k0.this.dispatchEvent(IMgr.MgrType.WindowMgr, 32, new Object[]{this.b, this.c});
            if ((objDispatchEvent instanceof Boolean ? ((Boolean) objDispatchEvent).booleanValue() : false) || !TextUtils.equals(BaseInfo.sLastRunApp, this.c)) {
                return;
            }
            BaseInfo.sLastRunApp = null;
            this.b.finish();
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements AsyncTaskHandler.IAsyncTaskListener {
        final /* synthetic */ String a;
        final /* synthetic */ Activity b;
        final /* synthetic */ String c;

        b(String str, Activity activity, String str2) {
            this.a = str;
            this.b = activity;
            this.c = str2;
        }

        @Override // io.dcloud.common.adapter.util.AsyncTaskHandler.IAsyncTaskListener
        public void onCancel() {
        }

        @Override // io.dcloud.common.adapter.util.AsyncTaskHandler.IAsyncTaskListener
        public void onExecuteBegin() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.dcloud.common.adapter.util.AsyncTaskHandler.IAsyncTaskListener
        public void onExecuteEnd(Object obj) {
            k0 k0Var = k0.this;
            Activity activity = this.b;
            k0Var.a(activity, this.a, this.c, activity instanceof IOnCreateSplashView ? (IOnCreateSplashView) activity : null);
            if (!SDK.isUniMPSDK() || obj == null) {
                return;
            }
            ComponentCallbacks2 componentCallbacks2 = this.b;
            if (componentCallbacks2 instanceof IActivityHandler) {
                ((IActivityHandler) componentCallbacks2).onAsyncStartAppEnd(this.a, obj);
            }
        }

        @Override // io.dcloud.common.adapter.util.AsyncTaskHandler.IAsyncTaskListener
        public Object onExecuting() {
            try {
                String str = TextUtils.isEmpty(this.a) ? BaseInfo.sDefaultBootApp : this.a;
                DCStorage dCStorage = DCStorage.getDCStorage(this.b);
                if (dCStorage != null) {
                    dCStorage.checkSPstorageToDB(this.b, str);
                }
            } catch (Exception unused) {
            }
            if (SDK.isUniMPSDK()) {
                ComponentCallbacks2 componentCallbacks2 = this.b;
                if (componentCallbacks2 instanceof IActivityHandler) {
                    return ((IActivityHandler) componentCallbacks2).onAsyncStartAppStart(this.a);
                }
            }
            return 0;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    static /* synthetic */ class c {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;

        static {
            int[] iArr = new int[IMgr.MgrType.values().length];
            b = iArr;
            try {
                iArr[IMgr.MgrType.AppMgr.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                b[IMgr.MgrType.NetMgr.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                b[IMgr.MgrType.FeatureMgr.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                b[IMgr.MgrType.WindowMgr.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[ISysEventListener.SysEventType.values().length];
            a = iArr2;
            try {
                iArr2[ISysEventListener.SysEventType.onStart.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[ISysEventListener.SysEventType.onStop.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[ISysEventListener.SysEventType.onPause.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[ISysEventListener.SysEventType.onResume.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    private k0() {
    }

    public void b(Activity activity) {
        ISysEventListener.SysEventType sysEventType = ISysEventListener.SysEventType.onPause;
        a(activity, sysEventType, (Object) null);
        if (this.a) {
            AbsMgr absMgr = this.f;
            if (absMgr != null) {
                absMgr.onExecute(sysEventType, null);
            }
            onActivityExecute(activity, sysEventType, null);
        }
        System.gc();
    }

    public void c(Activity activity) {
        ISysEventListener.SysEventType sysEventType = ISysEventListener.SysEventType.onResume;
        a(activity, sysEventType, (Object) null);
        this.f.onExecute(sysEventType, null);
        onActivityExecute(activity, sysEventType, null);
    }

    public boolean d(Activity activity) {
        DownloadUtil.getInstance(activity).stop();
        PlatformUtil.invokeMethod("io.dcloud.feature.apsqh.QHNotifactionReceiver", "doSaveNotifications", null, new Class[]{Context.class}, new Object[]{activity.getBaseContext()});
        try {
            ISysEventListener.SysEventType sysEventType = ISysEventListener.SysEventType.onStop;
            onActivityExecute(activity, sysEventType, null);
            this.a = false;
            BaseInfo.setLoadingLaunchePage(false, "onStop");
            a(activity, sysEventType, (Object) null);
            o = null;
            this.d.dispose();
            this.d = null;
            this.e.dispose();
            this.e = null;
            this.f.dispose();
            this.f = null;
            this.g.dispose();
            this.g = null;
            Logger.e(Logger.MAIN_TAG, "core exit");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override // io.dcloud.common.DHInterface.ICore
    public Object dispatchEvent(IMgr.MgrType mgrType, int i, Object obj) {
        AbsMgr absMgr;
        if (mgrType == null) {
            return a(i, obj);
        }
        try {
            if (o == null) {
                o = this;
            }
            int i2 = c.b[mgrType.ordinal()];
            if (i2 == 1) {
                AbsMgr absMgr2 = o.e;
                if (absMgr2 != null) {
                    return absMgr2.processEvent(mgrType, i, obj);
                }
            } else if (i2 == 2) {
                AbsMgr absMgr3 = o.f;
                if (absMgr3 != null) {
                    return absMgr3.processEvent(mgrType, i, obj);
                }
            } else if (i2 == 3) {
                AbsMgr absMgr4 = o.g;
                if (absMgr4 != null) {
                    return absMgr4.processEvent(mgrType, i, obj);
                }
            } else if (i2 == 4 && (absMgr = o.d) != null) {
                return absMgr.processEvent(mgrType, i, obj);
            }
            return null;
        } catch (Exception e) {
            Logger.w("Core.dispatchEvent", e);
            return null;
        }
    }

    @Override // io.dcloud.common.DHInterface.ICore
    public Context obtainActivityContext() {
        return this.c;
    }

    @Override // io.dcloud.common.DHInterface.ICore
    public Context obtainContext() {
        return this.b;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.dcloud.common.DHInterface.ICore
    public boolean onActivityExecute(Activity activity, ISysEventListener.SysEventType sysEventType, Object obj) {
        String string;
        Bundle extras;
        if (obj instanceof IApp) {
            string = ((IApp) obj).obtainAppId();
        } else {
            string = BaseInfo.sRuntimeMode != null ? null : BaseInfo.sDefaultBootApp;
            Intent intent = activity.getIntent();
            if (intent != null && (extras = intent.getExtras()) != null && extras.containsKey("appid")) {
                string = extras.getString("appid");
            }
        }
        Object objDispatchEvent = dispatchEvent(IMgr.MgrType.AppMgr, 1, new Object[]{sysEventType, obj, string});
        ISysEventListener.SysEventType sysEventType2 = ISysEventListener.SysEventType.onKeyUp;
        if (!sysEventType.equals(sysEventType2) || objDispatchEvent == null || ((Boolean) objDispatchEvent).booleanValue() || ((Integer) ((Object[]) obj)[0]).intValue() != 4) {
            return Boolean.parseBoolean(String.valueOf(objDispatchEvent));
        }
        if (sysEventType.equals(sysEventType2)) {
            if (activity instanceof IActivityHandler) {
                ((IActivityHandler) activity).closeAppStreamSplash(string);
            }
            a(activity, activity.getIntent(), (IApp) null, string);
        }
        return true;
    }

    @Override // io.dcloud.common.DHInterface.ICore
    public void onRestart(Context context) {
        Collection<IBoot> collectionValues;
        HashMap map = this.j;
        if (map == null || (collectionValues = map.values()) == null) {
            return;
        }
        for (IBoot iBoot : collectionValues) {
            if (iBoot != null) {
                iBoot.onRestart(context);
            }
        }
    }

    @Override // io.dcloud.common.DHInterface.ICore
    public void setmCoreListener(ICore.ICoreStatusListener iCoreStatusListener) {
        this.i = iCoreStatusListener;
    }

    public static k0 a(Context context, ICore.ICoreStatusListener iCoreStatusListener) {
        if (o == null) {
            o = new k0();
        }
        k0 k0Var = o;
        k0Var.b = context;
        k0Var.i = iCoreStatusListener;
        SDK.initSDK(k0Var);
        return o;
    }

    public void a(Activity activity, Bundle bundle, SDK.IntegratedMode integratedMode) {
        this.c = activity;
        DownloadUtil.getInstance(activity);
        WebViewFactory.resetSysWebViewState();
        a();
        a(activity);
        BaseInfo.sRuntimeMode = integratedMode;
        if (integratedMode != null) {
            BaseInfo.USE_ACTIVITY_HANDLE_KEYEVENT = true;
        }
        if (this.a) {
            return;
        }
        DCloudAdapterUtil.getIActivityHandler(activity);
        a(activity.getApplicationContext());
        Logger.i("Core onInit mode=" + integratedMode);
        a(activity, ISysEventListener.SysEventType.onStart, bundle);
        Logger.i("Core onInit mCoreListener=" + this.i);
        try {
            SDK.IntegratedMode integratedMode2 = BaseInfo.sRuntimeMode;
            if (integratedMode2 != null && integratedMode2 != SDK.IntegratedMode.RUNTIME) {
                ICore.ICoreStatusListener iCoreStatusListener = this.i;
                if (iCoreStatusListener != null) {
                    iCoreStatusListener.onCoreInitEnd(this);
                    return;
                }
                return;
            }
            ICore.ICoreStatusListener iCoreStatusListener2 = this.i;
            if (iCoreStatusListener2 != null) {
                iCoreStatusListener2.onCoreInitEnd(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void a(Activity activity) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            String str = Build.MANUFACTURER;
            jSONObject.put("deviceBrand", str);
            jSONObject.put("deviceModel", Build.MODEL);
            jSONObject.put("deviceType", DeviceInfo.getSystemUIModeType(activity));
            jSONObject.put("uniPlatform", AbsoluteConst.XML_APP);
            jSONObject.put(WXConfig.osName, WXEnvironment.OS);
            jSONObject.put("osAndroidAPILevel", Build.VERSION.SDK_INT);
            jSONObject.put("osVersion", Build.VERSION.RELEASE);
            jSONObject.put("osLanguage", LanguageUtil.getDeviceDefLocalLanguage());
            jSONObject.put("romName", o0.b(str));
            jSONObject.put("romVersion", o0.c(Build.BRAND));
        } catch (Exception unused) {
        }
        DeviceInfo.sSystemInfo = jSONObject;
    }

    private void a(Context context) throws IOException, ParseException {
        TestUtil.record("core", Thread.currentThread());
        this.g = new h1(this);
        this.j = (HashMap) dispatchEvent(IMgr.MgrType.FeatureMgr, 0, this.b);
        BaseInfo.parseControl(context, this, this.i);
        Logger.initLogger(context);
        DeviceInfo.init(context);
        this.e = new r(this);
        this.d = new io.dcloud.common.core.ui.l(this);
        this.f = new NetMgr(this);
        this.a = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Activity activity, IApp iApp) {
        onActivityExecute(activity, ISysEventListener.SysEventType.onWebAppStop, iApp);
    }

    public void a(Activity activity, Intent intent, IApp iApp, String str) {
        boolean z = this.i != null ? !r7.onCoreStop() : true;
        this.c = null;
        if (iApp != null) {
            dispatchEvent(IMgr.MgrType.AppMgr, 13, iApp);
        }
        if (z) {
            IActivityHandler iActivityHandler = DCloudAdapterUtil.getIActivityHandler(activity);
            TextUtils.equals(activity.getIntent().getStringExtra("appid"), str);
            if (iActivityHandler != null && iApp == null) {
                if (MobilePhoneModel.HUAWEI.equalsIgnoreCase(Build.MANUFACTURER) && Build.VERSION.SDK_INT >= 24) {
                    if (iApp != null) {
                        a(activity, iApp);
                    }
                    Object objDispatchEvent = dispatchEvent(IMgr.MgrType.WindowMgr, 32, new Object[]{activity, str});
                    if (!(objDispatchEvent instanceof Boolean ? ((Boolean) objDispatchEvent).booleanValue() : false)) {
                        activity.finish();
                    }
                } else {
                    MessageHandler.sendMessage(new a(iApp, activity, str), 10, null);
                }
            } else {
                activity.finish();
            }
        }
        BaseInfo.sGlobalFullScreen = false;
    }

    public IApp a(Activity activity, String str, String str2, IOnCreateSplashView iOnCreateSplashView) {
        return a(activity, str, str2, iOnCreateSplashView, false);
    }

    boolean a(Intent intent, String str) {
        String str2 = BaseInfo.sCacheFsAppsPath + str + "/www/";
        if (new File(str2).exists()) {
            File file = new File(str2 + "/manifest.json");
            if (file.exists() && file.length() > 0) {
                return true;
            }
        }
        if (intent.hasExtra(IntentConst.DIRECT_PAGE) && BaseInfo.isWap2AppAppid(str)) {
            return true;
        }
        return !intent.getBooleanExtra(IntentConst.WEBAPP_ACTIVITY_HAS_STREAM_SPLASH, false);
    }

    public IApp a(Activity activity, String str, String str2, IOnCreateSplashView iOnCreateSplashView, boolean z) {
        TestUtil.record("GET_STATUS_BY_APPID");
        Logger.d("syncStartApp " + str);
        IMgr.MgrType mgrType = IMgr.MgrType.AppMgr;
        Object objDispatchEvent = dispatchEvent(mgrType, 12, str);
        byte b2 = objDispatchEvent != null ? Byte.parseByte(String.valueOf(objDispatchEvent)) : (byte) 1;
        TestUtil.print("GET_STATUS_BY_APPID");
        boolean zA = a(activity.getIntent(), str);
        if (1 == b2) {
            Logger.d("syncStartApp " + str + " STATUS_UN_RUNNING");
            if (iOnCreateSplashView != null) {
                Logger.d("syncStartApp " + str + " ShowSplash");
                iOnCreateSplashView.onCreateSplash(activity);
            }
        }
        if (!zA) {
            return null;
        }
        try {
            TestUtil.record(TestUtil.START_APP_SET_ROOTVIEW, "启动" + str);
            IApp iApp = (IApp) dispatchEvent(mgrType, 0, new Object[]{activity, str, str2});
            iApp.setOnCreateSplashView(iOnCreateSplashView);
            if (z && (3 == b2 || 2 == b2)) {
                onActivityExecute(activity, ISysEventListener.SysEventType.onNewIntent, str2);
            }
            return iApp;
        } catch (Exception unused) {
            Logger.d("syncStartApp appid=" + str);
            return null;
        }
    }

    public void a(Activity activity, String str, String str2) {
        AsyncTaskHandler.executeThreadTask(new b(str, activity, str2));
    }

    private void a(Context context, ISysEventListener.SysEventType sysEventType, Object obj) {
        Collection<IBoot> collectionValues;
        HashMap map = this.j;
        if (map == null || (collectionValues = map.values()) == null) {
            return;
        }
        for (IBoot iBoot : collectionValues) {
            if (iBoot != null) {
                try {
                    int i = c.a[sysEventType.ordinal()];
                    if (i == 1) {
                        iBoot.onStart(context, (Bundle) obj, new String[]{BaseInfo.sDefaultBootApp});
                    } else if (i == 2) {
                        iBoot.onStop();
                    } else if (i == 3) {
                        iBoot.onPause();
                    } else if (i == 4) {
                        iBoot.onResume();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object a(int i, Object obj) {
        Activity activity;
        IApp iApp;
        Intent intentObtainWebAppIntent;
        String strObtainAppId;
        if (i == -1) {
            return BaseInfo.sRuntimeMode;
        }
        if (i != 0) {
            if (i == 1) {
                return Boolean.valueOf(this.j.containsValue((IBoot) obj));
            }
            if (i != 2) {
                return null;
            }
            Object[] objArr = (Object[]) obj;
            a((Activity) objArr[0], (String) objArr[1], (String) objArr[2], (IOnCreateSplashView) objArr[3]);
            return null;
        }
        if (obj instanceof IApp) {
            iApp = (IApp) obj;
            activity = iApp.getActivity();
            intentObtainWebAppIntent = iApp.obtainWebAppIntent();
            strObtainAppId = iApp.obtainAppId();
        } else if (obj instanceof Object[]) {
            Object[] objArr2 = (Object[]) obj;
            activity = (Activity) objArr2[0];
            intentObtainWebAppIntent = (Intent) objArr2[1];
            strObtainAppId = (String) objArr2[2];
            iApp = null;
        } else {
            activity = null;
            iApp = null;
            intentObtainWebAppIntent = null;
            strObtainAppId = null;
        }
        if (!SDK.isUniMPSDK()) {
            io.dcloud.p.b.g().h();
        }
        a(activity, intentObtainWebAppIntent, iApp, strObtainAppId);
        return null;
    }

    private void a() {
        String str;
        String str2;
        String metaValue = AndroidResources.getMetaValue("DCLOUD_LOCALE");
        if (PdrUtil.isEmpty(metaValue) || metaValue.equalsIgnoreCase("default")) {
            return;
        }
        String[] strArrSplit = metaValue.split("_");
        String upperCase = "";
        String lowerCase = (strArrSplit.length <= 0 || (str2 = strArrSplit[0]) == null) ? "" : str2.toLowerCase(Locale.ENGLISH);
        if (strArrSplit.length > 1 && (str = strArrSplit[1]) != null) {
            upperCase = str.toUpperCase();
        }
        Locale.setDefault(new Locale(lowerCase, upperCase));
    }
}
