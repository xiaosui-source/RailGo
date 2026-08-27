package io.dcloud.p;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.CheckBox;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IActivityHandler;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.common.DHInterface.ICore;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.adapter.ui.webview.WebViewFactory;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.constant.DataInterface;
import io.dcloud.common.constant.StringConst;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.DataUtil;
import io.dcloud.common.util.ErrorDialogUtil;
import io.dcloud.common.util.IOUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import io.dcloud.common.util.ThreadPool;
import io.dcloud.feature.internal.sdk.SDK;
import io.src.dcloud.adapter.DCloudAdapterUtil;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public final class r extends AbsMgr implements IMgr.AppEvent {
    private static String j;
    c4 a;
    ArrayList b;
    ArrayList c;
    a4 d;
    Class[] e;
    private AlertDialog f;
    JSONObject g;
    private AlertDialog h;
    private AlertDialog i;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements ICallBack {
        a() {
        }

        @Override // io.dcloud.common.DHInterface.ICallBack
        public Object onCallBack(int i, Object obj) {
            String unused = r.j = String.valueOf(obj);
            return null;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c implements DialogInterface.OnClickListener {
        final /* synthetic */ Activity a;
        final /* synthetic */ String b;
        final /* synthetic */ c5 c;
        final /* synthetic */ CheckBox d;
        final /* synthetic */ String e;
        final /* synthetic */ c5 f;
        final /* synthetic */ c5 g;
        final /* synthetic */ boolean h;

        c(Activity activity, String str, c5 c5Var, CheckBox checkBox, String str2, c5 c5Var2, c5 c5Var3, boolean z) {
            this.a = activity;
            this.b = str;
            this.c = c5Var;
            this.d = checkBox;
            this.e = str2;
            this.f = c5Var2;
            this.g = c5Var3;
            this.h = z;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i != -2) {
                if (i != -3 && i == -1) {
                    if (this.d.isChecked()) {
                        SP.setBundleData(this.a, "pdr", AbsoluteConst.TEST_RUN + this.b, "__am=t");
                    }
                    r.this.a(this.a, this.b, this.e, this.c, this.f, this.g, this.h);
                    r.this.f.dismiss();
                    return;
                }
                return;
            }
            r.this.f.dismiss();
            IActivityHandler iActivityHandler = DCloudAdapterUtil.getIActivityHandler(this.a);
            if (iActivityHandler != null) {
                iActivityHandler.closeAppStreamSplash(this.b);
                BaseInfo.setLoadingLaunchePage(false, "closeSplashScreen0");
                if (r.this.d.e() == 0) {
                    this.a.finish();
                    return;
                }
                c5 c5Var = this.c;
                if (c5Var != null) {
                    c5Var.w();
                }
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.HOME");
                this.a.startActivity(intent);
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class d implements DialogInterface.OnClickListener {
        final /* synthetic */ Activity a;

        d(Activity activity) {
            this.a = activity;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse("https://ask.dcloud.net.cn/article/35627"));
            this.a.startActivity(intent);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class e implements DialogInterface.OnClickListener {
        final /* synthetic */ Activity a;

        e(Activity activity) {
            this.a = activity;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse("https://ask.dcloud.net.cn/article/35877"));
            this.a.startActivity(intent);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class f implements ICallBack {
        final /* synthetic */ c5 a;

        f(c5 c5Var) {
            this.a = c5Var;
        }

        @Override // io.dcloud.common.DHInterface.ICallBack
        public Object onCallBack(int i, Object obj) {
            if (AppRuntime.hasPrivacyForNotShown(this.a.getActivity())) {
                return null;
            }
            ((AbsMgr) r.this).mCore.onRestart(this.a.getActivity());
            return null;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class g implements ICallBack {
        final /* synthetic */ c5 a;
        final /* synthetic */ boolean b;
        final /* synthetic */ String c;
        final /* synthetic */ ICallBack d;

        g(c5 c5Var, boolean z, String str, ICallBack iCallBack) {
            this.a = c5Var;
            this.b = z;
            this.c = str;
            this.d = iCallBack;
        }

        @Override // io.dcloud.common.DHInterface.ICallBack
        public Object onCallBack(int i, Object obj) {
            if (this.a.a(this.b)) {
                this.d.onCallBack(0, null);
            } else {
                Logger.e(Logger.AppMgr_TAG, "reboot " + this.c + " app failed !!!");
            }
            return null;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class h implements ICallBack {
        final /* synthetic */ c5 a;
        final /* synthetic */ String b;
        final /* synthetic */ String c;
        final /* synthetic */ boolean d;

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class a implements ICallBack {
            a() {
            }

            @Override // io.dcloud.common.DHInterface.ICallBack
            public Object onCallBack(int i, Object obj) {
                h hVar = h.this;
                r.this.a(hVar.a, hVar.b, hVar.c, hVar.d);
                return null;
            }
        }

        h(c5 c5Var, String str, String str2, boolean z) {
            this.a = c5Var;
            this.b = str;
            this.c = str2;
            this.d = z;
        }

        @Override // io.dcloud.common.DHInterface.ICallBack
        public Object onCallBack(int i, Object obj) {
            if (!WebViewFactory.isOther() || WebViewFactory.isOtherInitialised() || WebViewFactory.isIsLoadOtherTimeOut()) {
                r.this.a(this.a, this.b, this.c, this.d);
                return null;
            }
            WebViewFactory.setOtherCallBack(new a());
            return null;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class i implements Runnable {
        i() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                DHFile.deleteFile(StringConst.STREAMAPP_KEY_ROOTPATH + "splash_temp/");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public r(ICore iCore) throws IOException {
        super(iCore, Logger.AppMgr_TAG, IMgr.MgrType.AppMgr);
        this.a = null;
        this.b = new ArrayList(1);
        this.c = new ArrayList(1);
        this.d = null;
        this.e = new Class[0];
        this.g = null;
        if (iCore != null) {
            a(iCore.obtainContext());
        }
        c();
        b();
        d();
        a();
        this.d = new a4(this);
    }

    private void d(c5 c5Var) {
        Object objNewInstance = PlatformUtil.newInstance("android.app.ActivityManager$TaskDescription", new Class[]{String.class, Bitmap.class}, new Object[]{c5Var.obtainAppName(), BitmapFactory.decodeResource(getContext().getResources(), getContext().getApplicationInfo().icon)});
        PlatformUtil.invokeMethod(c5Var.getActivity(), "setTaskDescription", new Class[]{objNewInstance.getClass()}, objNewInstance);
    }

    c5 c(String str) {
        return a((Activity) null, str);
    }

    @Override // io.dcloud.common.DHInterface.AbsMgr
    public void dispose() {
        ArrayList arrayList = this.c;
        if (arrayList != null) {
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                ((c5) obj).g();
            }
        }
        this.c.clear();
        this.b.clear();
        a4 a4Var = this.d;
        if (a4Var != null) {
            a4Var.a();
        }
        this.d = null;
        ThreadPool.self().addThreadTask(new i());
    }

    void e(c5 c5Var) {
        this.d.b(c5Var.o);
        b(c5Var);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0429  */
    @Override // io.dcloud.common.DHInterface.IMgr
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object processEvent(io.dcloud.common.DHInterface.IMgr.MgrType r19, int r20, java.lang.Object r21) {
        /*
            Method dump skipped, instructions count: 1672
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.p.r.processEvent(io.dcloud.common.DHInterface.IMgr$MgrType, int, java.lang.Object):java.lang.Object");
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements Runnable {
        final /* synthetic */ Context a;

        b(Context context) {
            this.a = context;
        }

        /* JADX WARN: Removed duplicated region for block: B:9:0x0013  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void run() {
            /*
                r4 = this;
                boolean r0 = io.dcloud.common.util.BaseInfo.SyncDebug
                if (r0 == 0) goto L13
                java.lang.String r0 = "uni-jsframework-dev.js"
                java.io.InputStream r1 = io.dcloud.common.adapter.util.PlatformUtil.getResInputStream(r0)
                if (r1 == 0) goto L13
                boolean r1 = io.dcloud.feature.internal.sdk.SDK.isUniMPSDK()
                if (r1 != 0) goto L13
                goto L15
            L13:
                java.lang.String r0 = "uni-jsframework.js"
            L15:
                java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch: java.lang.Exception -> L42
                java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch: java.lang.Exception -> L42
                android.content.Context r3 = r4.a     // Catch: java.lang.Exception -> L42
                android.content.res.AssetManager r3 = r3.getAssets()     // Catch: java.lang.Exception -> L42
                java.io.InputStream r0 = r3.open(r0)     // Catch: java.lang.Exception -> L42
                r2.<init>(r0)     // Catch: java.lang.Exception -> L42
                r1.<init>(r2)     // Catch: java.lang.Exception -> L42
                java.lang.String r0 = r1.readLine()     // Catch: java.lang.Exception -> L42
                org.json.JSONObject r1 = new org.json.JSONObject     // Catch: java.lang.Exception -> L42
                r2 = 2
                java.lang.String r0 = r0.substring(r2)     // Catch: java.lang.Exception -> L42
                r1.<init>(r0)     // Catch: java.lang.Exception -> L42
                java.lang.String r0 = "version"
                java.lang.String r0 = r1.optString(r0)     // Catch: java.lang.Exception -> L42
                android.content.Context r1 = r4.a     // Catch: java.lang.Exception -> L42
                io.dcloud.common.util.BaseInfo.setUniVersionV3(r0, r1)     // Catch: java.lang.Exception -> L42
            L42:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.dcloud.p.r.b.run():void");
        }
    }

    void b() {
        e5 e5Var;
        HashMap<String, BaseInfo.BaseAppInfo> map = BaseInfo.mBaseAppInfoSet;
        if (map == null || map.isEmpty()) {
            return;
        }
        Set<String> setKeySet = BaseInfo.mBaseAppInfoSet.keySet();
        int size = setKeySet.size();
        String[] strArr = new String[size];
        setKeySet.toArray(strArr);
        for (int i2 = 0; i2 < size; i2++) {
            String str = strArr[i2];
            BaseInfo.BaseAppInfo baseAppInfo = BaseInfo.mBaseAppInfoSet.get(str);
            if (!BaseInfo.mUnInstalledAppInfoSet.containsKey(str) && !b(str)) {
                c5 c5VarB = b(BaseInfo.sBaseResAppsPath + str, str);
                if (c5VarB != null && (e5Var = c5VarB.s) != null) {
                    if (e5Var.a) {
                        Logger.e("AppMgr", str + "  app error," + c5VarB.s);
                    } else {
                        c5VarB.t = baseAppInfo;
                        c(c5VarB);
                    }
                }
            }
        }
    }

    void c(c5 c5Var) {
        this.b.add(c5Var.obtainAppId());
        this.c.add(c5Var);
    }

    private void a() {
        if (PdrUtil.isEmpty(j)) {
            DataUtil.datToJsString(BaseInfo.sUniNViewServiceJsPath, new a());
        }
    }

    void c() {
        e5 e5Var;
        HashMap<String, BaseInfo.BaseAppInfo> map = BaseInfo.mInstalledAppInfoSet;
        if (map == null || map.isEmpty()) {
            return;
        }
        Set<String> setKeySet = BaseInfo.mInstalledAppInfoSet.keySet();
        int size = setKeySet.size();
        String[] strArr = new String[size];
        setKeySet.toArray(strArr);
        boolean z = false;
        for (int i2 = 0; i2 < size; i2++) {
            String str = strArr[i2];
            if (!BaseInfo.mUnInstalledAppInfoSet.containsKey(str) && !b(str)) {
                c5 c5VarB = b(BaseInfo.sCacheFsAppsPath + str, str);
                if (c5VarB != null && (e5Var = c5VarB.s) != null && !e5Var.a) {
                    c5VarB.deleteAppTemp();
                    if (SDK.isUniMPSDK()) {
                        c5VarB.j0 = true;
                    } else {
                        c5VarB.j0 = false;
                    }
                    c(c5VarB);
                } else {
                    BaseInfo.mInstalledAppInfoSet.get(str).clearBundleData();
                    BaseInfo.mInstalledAppInfoSet.remove(str);
                    z = true;
                }
            }
        }
        if (z) {
            BaseInfo.saveInstalledAppInfo(getContext());
        }
    }

    private void a(Context context) {
        if (TextUtils.isEmpty(BaseInfo.uniVersionV3)) {
            ThreadPool.self().addThreadTask(new b(context), true);
        }
    }

    void d() throws IOException {
        File file = new File(BaseInfo.sURDFilePath);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            DHFile.copyAssetsFile("data/dcloud_url.json", file.getAbsolutePath());
        }
        if (file.exists()) {
            try {
                this.g = new JSONObject(new String(DHFile.readAll(file)));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    private void a(c5 c5Var, String str, boolean z) {
        if (c5Var != null) {
            String strObtainConfigProperty = c5Var.obtainConfigProperty(IApp.ConfigProperty.CONFIG_UNIAPP_CONTROL);
            f fVar = new f(c5Var);
            if (!TextUtils.isEmpty(strObtainConfigProperty) && strObtainConfigProperty.equals(AbsoluteConst.UNI_V3)) {
                if (c5Var.getActivity() != null) {
                    BaseInfo.isFirstRun = false;
                    c5Var.showSplash();
                    AppRuntime.restartWeex(c5Var.getActivity().getApplication(), new g(c5Var, z, str, fVar), c5Var.o);
                    return;
                }
                return;
            }
            if (!c5Var.a(z)) {
                Logger.e(Logger.AppMgr_TAG, "reboot " + str + " app failed !!!");
                return;
            }
            fVar.onCallBack(0, null);
            return;
        }
        Logger.e(Logger.AppMgr_TAG, "not found " + str + " app!!!");
    }

    void b(c5 c5Var) {
        this.b.remove(c5Var.o);
        this.c.remove(c5Var);
    }

    private boolean b(String str) {
        return this.b.contains(str);
    }

    private c5 b(String str, String str2) {
        return a(str, str2);
    }

    private void a(c5 c5Var) {
        if (SDK.isUniMPSDK() && SDK.isEnableBackground) {
            d(c5Var);
        }
    }

    public void a(Activity activity, String str, String str2, c5 c5Var, c5 c5Var2, c5 c5Var3, boolean z) {
        Log.i("ylyl", "startOneApp " + str);
        BaseInfo.sLastRunApp = str;
        BaseInfo.CmtInfo cmitInfo = BaseInfo.getCmitInfo(str);
        if (cmitInfo.needUpdate) {
            cmitInfo.templateVersion = c5Var2.D;
            cmitInfo.rptCrs = c5Var2.M;
            cmitInfo.rptJse = c5Var2.N;
            cmitInfo.plusLauncher = BaseInfo.getLaunchType(c5Var2.obtainWebAppIntent());
            cmitInfo.sfd = DataInterface.getStreamappFrom(c5Var2.obtainWebAppIntent());
            cmitInfo.needUpdate = false;
        }
        if (!b4.c()) {
            if (c5Var2.u == 4) {
                ErrorDialogUtil.checkAppKeyErrorTips(activity);
                return;
            }
        } else if (!PdrUtil.checkIntl()) {
            q.a(activity, c5Var2);
            if (c5Var2.u == 4) {
                return;
            }
        }
        if (c5Var2.u == 3) {
            c5Var2.u = c5Var2.p() ? c5Var2.u : (byte) 2;
        }
        if (c5Var != null && c5Var != c5Var2 && c5Var != c5Var3) {
            c5Var.w();
        }
        byte b2 = c5Var2.u;
        if (b2 == 1 || ((z && !c5Var2.w) || ((c5Var2.x && c5Var2.v) || !z))) {
            Logger.d(Logger.AppMgr_TAG, str + " will unrunning change to active");
            c5Var2.a(activity);
            processEvent(IMgr.MgrType.WindowMgr, 4, new Object[]{c5Var2, str});
            c5Var2.a(new h(c5Var2, str, str2, z));
        } else if (b2 == 2) {
            Logger.d(Logger.AppMgr_TAG, str + " will unactive change to active");
            c5Var2.c();
        } else {
            Logger.d(Logger.AppMgr_TAG, str + " is active");
        }
        if (SDK.isUniMPSDK()) {
            a(c5Var2);
        }
        if (c5Var3 == null || c5Var3 == c5Var2) {
            return;
        }
        c5Var3.u();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(c5 c5Var, String str, String str2, boolean z) {
        if (this.d == null) {
            return;
        }
        boolean zF = z ? c5Var.f(str2) : c5Var.g(str2);
        if (!c5Var.w && c5Var.v) {
            c5Var.f(str2);
        }
        if (zF) {
            this.d.a(str, c5Var);
            return;
        }
        Logger.e(Logger.AppMgr_TAG, str + " run failed!!!");
    }

    c5 a(Activity activity, String str) {
        return a(activity, str, true);
    }

    private c5 a(String str, boolean z) {
        return a((Activity) null, str, z);
    }

    private c5 a(Activity activity, String str, boolean z) {
        int iIndexOf;
        c5 c5Var = (!this.b.contains(str) || (iIndexOf = this.b.indexOf(str)) < 0) ? null : (c5) this.c.get(iIndexOf);
        if (c5Var != null || !z) {
            if (c5Var != null && activity != null) {
                if (c5Var.a == null) {
                    c5Var.a = activity;
                }
                if (c5Var.a.getIntent() != null) {
                    if (c5Var.manifestBeParsed()) {
                        c5Var.setWebAppIntent(c5Var.a.getIntent());
                    }
                } else {
                    c5Var.a.setIntent(c5Var.obtainWebAppIntent());
                }
                if (!c5Var.v) {
                    c5Var.b(str, null);
                }
            }
            return c5Var;
        }
        c5 c5Var2 = new c5(this, str, (byte) 0);
        c5Var2.setAppDataPath(BaseInfo.sCacheFsAppsPath + str + DeviceInfo.sSeparatorChar + BaseInfo.REAL_PRIVATE_WWW_DIR);
        if (c5Var2.a == null) {
            c5Var2.a = activity;
        }
        if (activity != null) {
            c5Var2.setWebAppIntent(activity.getIntent());
        }
        c5Var2.b(str, null);
        if (c5Var2.s.a) {
            c5Var2.o = str;
        }
        c(c5Var2);
        return c5Var2;
    }

    c5 a(String str, String str2, String str3, byte b2) {
        c5 c5VarA = a(str, false);
        if (c5VarA == null) {
            c5VarA = new c5(this, str, b2);
            c5VarA.u = (byte) 3;
            c5VarA.o = str;
            if (!PdrUtil.isEmpty(str2)) {
                c5VarA.setAppDataPath(str2);
            }
            c5VarA.l0 = str3;
            c(c5VarA);
            this.d.a(str, c5VarA);
        }
        return c5VarA;
    }

    c5 a(String str, String str2) {
        return a(str, str2, (JSONObject) null);
    }

    c5 a(String str, String str2, JSONObject jSONObject) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Exception e2;
        c5 c5Var;
        PackageInfo apkInfo;
        Class<?> cls;
        c5 c5VarA = a(str2, false);
        if (c5VarA != null) {
            try {
                c5VarA.s.a();
            } catch (Exception e3) {
                e2 = e3;
                c5Var = c5VarA;
                e2.printStackTrace();
                Logger.e(Logger.AppMgr_TAG, "installWebApp " + str + " is Illegal path");
                return c5Var;
            }
        }
        if (!DHFile.isExist(str) && !PdrUtil.isDeviceRootDir(str)) {
            boolean zContains = str.substring(str.lastIndexOf(47)).contains(".wgt");
            resInputStream = zContains ? PlatformUtil.getResInputStream(str) : null;
            if (c5VarA == null) {
                c5VarA = new c5(this, str2, (byte) 1);
            }
            if (!zContains && resInputStream == null) {
                c5VarA.setAppDataPath(str + DeviceInfo.sSeparatorChar + BaseInfo.REAL_PRIVATE_WWW_DIR);
                c5VarA.b(str2, jSONObject);
            } else {
                c5VarA.b(resInputStream);
            }
        } else {
            boolean zIsFile = new File(str).isFile();
            if (zIsFile) {
                if (zIsFile && str.toLowerCase(Locale.ENGLISH).endsWith(".wgtu")) {
                    if (c5VarA == null) {
                        c5VarA = new c5(this, str2, (byte) 0);
                    }
                    c5VarA.a(str, jSONObject);
                    e5 e5Var = c5VarA.s;
                    e5Var.c = false;
                    e5Var.d = false;
                } else if (zIsFile && str.toLowerCase(Locale.ENGLISH).endsWith(".wgt")) {
                    boolean z = c5VarA == null;
                    c5VarA.s.d = true;
                    if (z) {
                        c5Var = new c5(this, str2, (byte) 0);
                        try {
                            c5Var.o = str2;
                            c5Var.setAppDataPath(BaseInfo.sCacheFsAppsPath + str2 + DeviceInfo.sSeparatorChar + BaseInfo.REAL_PRIVATE_WWW_DIR);
                            c5VarA = c5Var;
                        } catch (Exception e4) {
                            e2 = e4;
                            e2.printStackTrace();
                            Logger.e(Logger.AppMgr_TAG, "installWebApp " + str + " is Illegal path");
                            return c5Var;
                        }
                    }
                    boolean zC = c5VarA.c(str, jSONObject);
                    c5VarA.s.d = false;
                    if (zC && z) {
                        c(c5VarA);
                    }
                } else if (zIsFile && str.toLowerCase(Locale.ENGLISH).endsWith(StringConst.POINT_APP_EN)) {
                    try {
                        apkInfo = PlatformUtil.parseApkInfo(getContext(), str);
                    } catch (Exception e5) {
                        e5.printStackTrace();
                        c5VarA.s.b = StringUtil.format(DOMException.JSON_ERROR_INFO, 10, e5.getMessage());
                        apkInfo = null;
                    }
                    try {
                        cls = Class.forName("io.dcloud.feature.pack.FileUtils");
                    } catch (Exception unused) {
                        cls = null;
                    }
                    if (apkInfo != null && cls != null) {
                        c5VarA.s.a = false;
                        String str3 = apkInfo.versionName;
                        String str4 = apkInfo.packageName;
                        String string = getContext().getPackageManager().getApplicationLabel(apkInfo.applicationInfo).toString();
                        if (string == null) {
                            string = "";
                        }
                        c5VarA.s.b = StringUtil.format("{pname:'%s',version:'%s',name:'%s'}", str4, str3, string);
                        try {
                            cls.getDeclaredMethod("addFileToSystem", Context.class, String.class, String.class).invoke(null, getContext(), getContext().getPackageName() + ".dc.fileprovider", str);
                        } catch (Exception unused2) {
                        }
                    } else {
                        c5VarA.s.a = true;
                    }
                } else {
                    e5 e5Var2 = c5VarA.s;
                    e5Var2.a = true;
                    e5Var2.b = StringUtil.format(DOMException.JSON_ERROR_INFO, Integer.valueOf(DOMException.CODE_RUNTIME_WGT_OR_WGTU_ERROR_MALFORMED), DOMException.MSG_RUNTIME_WGT_OR_WGTU_ERROR_MALFORMED);
                }
            } else {
                if (c5VarA != null) {
                    c5VarA.b((byte) 0);
                } else {
                    c5VarA = new c5(this, str2, (byte) 0);
                }
                c5VarA.setAppDataPath(str + DeviceInfo.sSeparatorChar + BaseInfo.REAL_PRIVATE_WWW_DIR);
                c5VarA.b(str2, jSONObject);
            }
        }
        IOUtil.close(resInputStream);
        return c5VarA;
    }
}
