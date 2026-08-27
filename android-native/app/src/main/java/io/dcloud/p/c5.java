package io.dcloud.p;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import com.taobao.weex.common.WXConfig;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.ui.component.WXBasicComponentType;
import io.dcloud.WebAppActivity;
import io.dcloud.common.DHInterface.IActivityHandler;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IBoot;
import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.common.DHInterface.IConfusionMgr;
import io.dcloud.common.DHInterface.IDCloudWebviewClientListener;
import io.dcloud.common.DHInterface.IFeature;
import io.dcloud.common.DHInterface.IFrameView;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.IOnCreateSplashView;
import io.dcloud.common.DHInterface.IPdrModule;
import io.dcloud.common.DHInterface.ISysEventListener;
import io.dcloud.common.DHInterface.IWebviewStateListener;
import io.dcloud.common.DHInterface.ReceiveSystemEventVoucher;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.adapter.io.UnicodeInputStream;
import io.dcloud.common.adapter.ui.AdaFrameView;
import io.dcloud.common.adapter.ui.webview.WebViewFactory;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.MessageHandler;
import io.dcloud.common.adapter.util.PermissionUtil;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.constant.IntentConst;
import io.dcloud.common.core.permission.PermissionControler;
import io.dcloud.common.ui.PrivacyManager;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.AppStatus;
import io.dcloud.common.util.AppStatusBarManager;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.CreateShortResultReceiver;
import io.dcloud.common.util.IOUtil;
import io.dcloud.common.util.JSONUtil;
import io.dcloud.common.util.NetworkTypeUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.TestUtil;
import io.dcloud.common.util.ThreadPool;
import io.dcloud.common.util.ZipUtils;
import io.dcloud.feature.gg.dcloud.ADSim;
import io.dcloud.feature.internal.sdk.SDK;
import io.src.dcloud.adapter.DCloudAdapterUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
class c5 extends d5 implements IApp, ISysEventListener {
    public static String H1 = "webapp";
    ArrayList B0;
    private String R0;
    private String f0;
    private String g0;
    private String h0;
    private String i0;
    e5 s;
    private IConfusionMgr t1;
    private String u1;
    r y;
    BaseInfo.BaseAppInfo t = null;
    byte u = 1;
    boolean v = false;
    boolean w = false;
    boolean x = false;
    String z = null;
    String A = "";
    String B = "";
    String C = "";
    String D = null;
    String E = "";
    String F = null;
    String G = null;
    String H = null;
    String I = null;
    String J = null;
    String K = null;
    String L = null;
    boolean M = true;
    boolean N = true;
    boolean O = true;
    boolean P = false;
    boolean Q = true;
    boolean R = true;
    boolean S = false;
    private String T = null;
    boolean U = false;
    private byte V = 1;
    private boolean W = false;
    private boolean X = false;
    private boolean Y = true;
    private boolean Z = true;
    private int a0 = ADSim.INTISPLSH;
    private int b0 = 0;
    private int c0 = 0;
    private String d0 = null;
    private String e0 = null;
    boolean j0 = false;
    private String k0 = null;
    String l0 = null;
    String m0 = null;
    String n0 = null;
    boolean o0 = false;
    String p0 = "accept";
    String q0 = "file:///android_asset/data/dcloud_error.html";
    String r0 = null;
    private String s0 = null;
    String t0 = null;
    private String u0 = "-1";
    private JSONObject v0 = null;
    private String w0 = "";
    private boolean x0 = true;
    private boolean y0 = false;
    private String z0 = AbsoluteConst.UNI_V3;
    private String A0 = "fast";
    HashMap C0 = null;
    JSONObject D0 = null;
    JSONObject E0 = null;
    JSONObject F0 = null;
    JSONObject G0 = null;
    JSONObject H0 = null;
    JSONObject I0 = null;
    JSONObject J0 = null;
    JSONObject K0 = null;
    JSONObject L0 = null;
    String M0 = null;
    String N0 = null;
    Intent O0 = null;
    IApp.IAppStatusListener P0 = null;
    String Q0 = null;
    private String S0 = "none";
    boolean T0 = false;
    private boolean U0 = false;
    private boolean V0 = false;
    private String W0 = "default";
    private String X0 = null;
    private String Y0 = null;
    private String Z0 = null;
    private String a1 = "";
    protected boolean b1 = false;
    private boolean c1 = false;
    private boolean d1 = false;
    private String e1 = null;
    private String f1 = null;
    private boolean g1 = false;
    long h1 = 0;
    boolean i1 = true;
    boolean j1 = false;
    boolean k1 = false;
    ArrayList l1 = new ArrayList();
    ArrayList m1 = new ArrayList();
    String n1 = null;
    String o1 = null;
    private String p1 = "";
    private boolean q1 = false;
    private String r1 = null;
    private int s1 = 1;
    IWebviewStateListener v1 = null;
    boolean w1 = false;
    JSONObject x1 = null;
    private boolean y1 = false;
    private String z1 = "none";
    private String A1 = AbsoluteConst.INSTALL_OPTIONS_FORCE;
    private String B1 = null;
    private String C1 = null;
    boolean D1 = true;
    HashMap E1 = null;
    String F1 = null;
    boolean G1 = false;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                DHFile.deleteFile(BaseInfo.sBaseWap2AppTemplatePath + "wap2app_temp/");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements Runnable {
        b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                DHFile.deleteFile(BaseInfo.sBaseWap2AppTemplatePath + "wap2app_temp/");
                DHFile.deleteFile(BaseInfo.sBaseWap2AppTemplatePath + "wap2app__template.zip");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c implements Runnable {
        final /* synthetic */ ICallBack a;

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class a implements MessageHandler.IMessages {
            a() {
            }

            @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
            public void execute(Object obj) {
                c.this.a.onCallBack(0, null);
            }
        }

        c(ICallBack iCallBack) {
            this.a = iCallBack;
        }

        @Override // java.lang.Runnable
        public void run() throws InterruptedException {
            String str = BaseInfo.sCacheFsAppsPath + c5.this.o + DeviceInfo.sSeparatorChar + BaseInfo.APP_WWW_FS_DIR;
            long jCurrentTimeMillis = System.currentTimeMillis();
            Logger.d(c5.H1, c5.this.o + " copy resoure begin!!!");
            DHFile.delete(str);
            DHFile.copyDir(c5.this.k0, str);
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            Logger.d(c5.H1, c5.this.o + " copy resoure end!!! useTime=" + (jCurrentTimeMillis2 - jCurrentTimeMillis));
            c5.this.V = (byte) 0;
            c5.this.setAppDataPath(str);
            c5 c5Var = c5.this;
            BaseInfo.BaseAppInfo baseAppInfo = c5Var.t;
            if (baseAppInfo != null) {
                baseAppInfo.saveToBundleData(c5Var.getActivity());
            }
            MessageHandler.sendMessage(new a(), null);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class d implements Runnable {
        final /* synthetic */ String a;

        d(String str) {
            this.a = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            TestUtil.PointTime.commitTid(c5.this.getActivity(), this.a, null, c5.this.N0, 1);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class e implements Runnable {
        e() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                DHFile.deleteFile(c5.this.obtainAppTempPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    static /* synthetic */ class f {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[IApp.ConfigProperty.ThridInfo.values().length];
            a = iArr;
            try {
                iArr[IApp.ConfigProperty.ThridInfo.OverrideUrlJsonData.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[IApp.ConfigProperty.ThridInfo.OverrideResourceJsonData.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[IApp.ConfigProperty.ThridInfo.SecondWebviewJsonData.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[IApp.ConfigProperty.ThridInfo.LaunchWebviewJsonData.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[IApp.ConfigProperty.ThridInfo.TitleNViewJsonData.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[IApp.ConfigProperty.ThridInfo.SitemapJsonData.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[IApp.ConfigProperty.ThridInfo.URDJsonData.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[IApp.ConfigProperty.ThridInfo.DirectPageJsonData.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                a[IApp.ConfigProperty.ThridInfo.Tabbar.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    c5(r rVar, String str, byte b2) {
        this.s = null;
        this.y = null;
        this.B0 = null;
        this.y = rVar;
        this.o = str;
        b(b2);
        this.t1 = i0.c();
        this.s = new e5();
        this.B0 = new ArrayList(2);
        this.q = AppRuntime.isUniApp(str);
    }

    private void d() {
    }

    private void e() throws JSONException {
        JSONObject jSONObject = this.K0;
        if (jSONObject != null) {
            try {
                String strOptString = jSONObject.optString("webviewid");
                if (TextUtils.isEmpty(strOptString)) {
                    this.K0.put("webviewid", IntentConst.DIRECT_PAGE);
                }
                if (this.o.equals(strOptString)) {
                    this.J0 = this.K0.optJSONObject("titleNView");
                    return;
                }
                JSONObject jSONObjectOptJSONObject = this.K0.has("titleNView") ? this.K0.optJSONObject("titleNView") : null;
                if (jSONObjectOptJSONObject == null) {
                    jSONObjectOptJSONObject = new JSONObject();
                    this.K0.put("titleNView", jSONObjectOptJSONObject);
                }
                jSONObjectOptJSONObject.put("autoBackButton", true);
                if (jSONObjectOptJSONObject.has("homeButton")) {
                    return;
                }
                jSONObjectOptJSONObject.put("homeButton", true);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    private boolean e(String str) {
        return false;
    }

    private void f() {
        try {
            int iRename = DHFile.rename(BaseInfo.sBaseWap2AppTemplatePath + "wap2app__template/", BaseInfo.sBaseWap2AppTemplatePath + "wap2app_temp/");
            DHFile.copyDir("data/wap2app", BaseInfo.sBaseWap2AppTemplatePath + "wap2app__template/");
            if (iRename == 1) {
                ThreadPool.self().addThreadTask(new a());
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private JSONObject h() {
        JSONObject jSONObjectA = null;
        try {
            InputStream inputStreamObtainResInStream = obtainResInStream("_www/__template.json");
            if (inputStreamObtainResInStream == null) {
                return null;
            }
            jSONObjectA = a(inputStreamObtainResInStream);
            IOUtil.close(inputStreamObtainResInStream);
            return jSONObjectA;
        } catch (Exception e2) {
            e2.printStackTrace();
            return jSONObjectA;
        }
    }

    private JSONObject i() {
        ThreadPool threadPoolSelf;
        b bVar;
        JSONObject jSONObject = null;
        try {
            boolean z = true;
            boolean z2 = false;
            if (BaseInfo.sCoverApkRuning) {
                if (new File(BaseInfo.sBaseWap2AppTemplatePath + "wap2app__template/__template.json").exists()) {
                    InputStream inputStream = PlatformUtil.getInputStream(BaseInfo.sBaseConfigTemplatePath);
                    JSONObject jSONObjectA = a(inputStream);
                    String strOptString = jSONObjectA.optString("version");
                    IOUtil.close(inputStream);
                    InputStream inputStream2 = DHFile.getInputStream(BaseInfo.sBaseWap2AppTemplatePath + "wap2app__template/__template.json");
                    JSONObject jSONObjectA2 = a(inputStream2);
                    String strOptString2 = jSONObjectA2.optString("version");
                    IOUtil.close(inputStream2);
                    BaseInfo.mWap2appTemplateFiles.clear();
                    BaseInfo.mW2AE.clear();
                    if (BaseInfo.BaseAppInfo.compareVersion(strOptString, strOptString2)) {
                        f();
                        jSONObject = jSONObjectA;
                        z2 = true;
                    } else {
                        jSONObject = jSONObjectA2;
                    }
                }
            }
            if (DHFile.isExist(BaseInfo.sBaseWap2AppTemplatePath + "wap2app__template.zip")) {
                DHFile.rename(BaseInfo.sBaseWap2AppTemplatePath + "wap2app__template/", BaseInfo.sBaseWap2AppTemplatePath + "wap2app_temp/");
                try {
                    try {
                        ZipUtils.upZipFile(new File(BaseInfo.sBaseWap2AppTemplatePath + "wap2app__template.zip"), BaseInfo.sBaseWap2AppTemplatePath + "wap2app__template/");
                        threadPoolSelf = ThreadPool.self();
                        bVar = new b();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        threadPoolSelf = ThreadPool.self();
                        bVar = new b();
                        z = z2;
                    }
                    threadPoolSelf.addThreadTask(bVar);
                } catch (Throwable th) {
                    ThreadPool.self().addThreadTask(new b());
                    throw th;
                }
            } else {
                if (new File(BaseInfo.sBaseWap2AppTemplatePath + "wap2app__template/__template.json").exists()) {
                    z = z2;
                } else {
                    f();
                }
            }
            if (!z && !TextUtils.isEmpty(BaseInfo.sWap2AppTemplateVersion) && BaseInfo.mWap2appTemplateFiles.size() != 0 && this.t1.getData("__w2a__template__") != null) {
                return jSONObject;
            }
            if (!DHFile.isExist(BaseInfo.sBaseWap2AppTemplatePath + "wap2app__template/__template.json")) {
                return jSONObject;
            }
            InputStream inputStream3 = DHFile.getInputStream(BaseInfo.sBaseWap2AppTemplatePath + "wap2app__template/__template.json");
            JSONObject jSONObjectA3 = a(inputStream3);
            IOUtil.close(inputStream3);
            BaseInfo.mWap2appTemplateFiles.clear();
            BaseInfo.mW2AE.clear();
            this.t1.removeData("__w2a__template__");
            return jSONObjectA3;
        } catch (Exception e3) {
            e3.printStackTrace();
            return jSONObject;
        }
    }

    private void l() {
        String[] strArrSplit;
        this.E1 = new HashMap();
        String string = SP.getOrCreateBundle(getActivity(), this.o + "_1").getString("Authorize", null);
        this.F1 = string;
        if (string == null || (strArrSplit = string.split("&")) == null || strArrSplit.length <= 0) {
            return;
        }
        for (String str : strArrSplit) {
            if (!TextUtils.isEmpty(str)) {
                String[] strArrSplit2 = str.split("=");
                this.E1.put(strArrSplit2[0], Integer.valueOf(Integer.parseInt(strArrSplit2[1])));
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x005b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void x() {
        /*
            Method dump skipped, instructions count: 499
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.p.c5.x():void");
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void addAllFeaturePermission() {
        PermissionControler.registerRootPermission(this.o);
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void addFeaturePermission(String str) {
        this.B0.add(str.toLowerCase(Locale.ENGLISH));
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void applyMani() throws JSONException, NoSuchAlgorithmException {
        try {
            a(DHFile.getInputStream(DHFile.createFileHandler(a(BaseInfo.sConfigXML))), this.o, null);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void applySmartUpdate() {
        a(false);
    }

    void b(InputStream inputStream) {
    }

    boolean b(String str, JSONObject jSONObject) {
        boolean z;
        HashMap<String, BaseInfo.BaseAppInfo> map;
        InputStream inputStream = null;
        try {
            this.o = str;
            this.t1.removeData(str);
            k();
            if (this.V == 0 || !((map = BaseInfo.mBaseAppInfoSet) == null || map.containsKey(this.o))) {
                inputStream = DHFile.getInputStream(DHFile.createFileHandler(a(BaseInfo.sConfigXML)));
                if (inputStream == null && (inputStream = PlatformUtil.getResInputStream(a(BaseInfo.sConfigXML))) != null) {
                    this.V = (byte) 1;
                }
            } else if (this.V == 1) {
                inputStream = PlatformUtil.getResInputStream(a(BaseInfo.sConfigXML));
            }
            if (BaseInfo.isWap2AppAppid(this.o)) {
                x();
                z = false;
            } else {
                z = true;
            }
            if (inputStream == null) {
                if (BaseInfo.isWap2AppAppid(this.o) && !TextUtils.isEmpty(this.n1)) {
                    return true;
                }
                if (q() && !TextUtils.isEmpty(this.r0)) {
                    return true;
                }
                e5 e5Var = this.s;
                e5Var.a = true;
                if (e5Var.c) {
                    e5Var.b = DOMException.toJSON(DOMException.CODE_RUNTIME_WGTU_WWW_MANIFEST_NOT_EXIST, DOMException.MSG_RUNTIME_WGTU_WWW_MANIFEST_NOT_EXIST);
                } else {
                    e5Var.b = DOMException.toJSON(DOMException.CODE_RUNTIME_WGT_MANIFEST_NOT_EXIST, DOMException.MSG_RUNTIME_WGT_MANIFEST_NOT_EXIST);
                }
                return false;
            }
            boolean zA = a(inputStream, str, jSONObject);
            if (z) {
                x();
            }
            e5 e5Var2 = this.s;
            if (e5Var2 != null && e5Var2.a) {
                Logger.i("WebApp", "InstallError---msg=" + this.s.b);
            }
            IActivityHandler iActivityHandler = DCloudAdapterUtil.getIActivityHandler(getActivity());
            if (iActivityHandler != null) {
                iActivityHandler.updateSplash(this.t0);
            }
            this.v = true;
            return zA;
        } catch (Exception e2) {
            Logger.w("parseConfig", e2);
            return false;
        } finally {
            IOUtil.close((InputStream) null);
        }
    }

    void c() {
        Activity activity = this.a;
        if (activity != null && (activity instanceof WebAppActivity)) {
            ((WebAppActivity) activity).onAppActive(this.o);
            ((WebAppActivity) this.a).onAppActive(this);
        }
        diyStatusBarState();
        setStatus((byte) 3);
        this.b.onAppActive(this);
        callSysEventListener(ISysEventListener.SysEventType.onWebAppForeground, IntentConst.obtainArgs(obtainWebAppIntent(), this.o));
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public boolean callSysEventListener(ISysEventListener.SysEventType sysEventType, Object obj) {
        HashMap map = this.C0;
        boolean zOnExecute = false;
        if (map == null) {
            return false;
        }
        ArrayList arrayList = (ArrayList) map.get(sysEventType);
        ArrayList arrayList2 = (ArrayList) this.C0.get(ISysEventListener.SysEventType.AllSystemEvent);
        ArrayList arrayList3 = new ArrayList();
        if (arrayList != null) {
            arrayList3.addAll(arrayList);
        }
        if (arrayList2 != null) {
            arrayList3.addAll(arrayList2);
        }
        for (int size = arrayList3.size() - 1; size >= 0; size--) {
            ISysEventListener iSysEventListener = (ISysEventListener) arrayList3.get(size);
            if (a(iSysEventListener, sysEventType) && (zOnExecute || iSysEventListener.onExecute(sysEventType, obj)) && !a(sysEventType)) {
                return zOnExecute;
            }
        }
        return zOnExecute;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public boolean checkIsCustomPath() {
        return this.o0;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void checkOrLoadlaunchWebview() {
        r rVar = this.y;
        if (rVar != null) {
            AdaFrameView adaFrameView = (AdaFrameView) rVar.processEvent(IMgr.MgrType.WindowMgr, 46, obtainAppId());
            Logger.d("Direct_page", "checkOrLoadlaunchWebview " + manifestBeParsed() + ";adaFrameView=" + adaFrameView);
            this.w1 = manifestBeParsed() ^ true;
            if (adaFrameView == null || !manifestBeParsed()) {
                return;
            }
            adaFrameView.obtainWebView().checkIfNeedLoadOriginalUrl();
        }
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public boolean checkPrivateDir(String str) {
        return str.startsWith(obtainAppDataPath()) || str.startsWith(BaseInfo.REL_PRIVATE_WWW_DIR);
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String checkPrivateDirAndCopy2Temp(String str) throws IOException {
        if (obtainRunningAppMode() == 1 && checkPrivateDir(str)) {
            String str2 = "/" + BaseInfo.APP_WWW_FS_DIR;
            String strSubstring = str.substring(str.indexOf(str2) + str2.length());
            String str3 = this.k0 + strSubstring;
            str = obtainAppTempPath() + strSubstring;
            if (!DHFile.exists(str)) {
                DHFile.copyAssetsFile(str3, str);
            }
        }
        return str;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public boolean checkSchemeWhite(String str) {
        if (!q()) {
            return true;
        }
        if (!TextUtils.isEmpty(str)) {
            ArrayList arrayList = this.m1;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                String str2 = (String) obj;
                if (!TextUtils.equals(str2, "*")) {
                    if (str.startsWith(str2 + ":")) {
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public boolean checkWhiteUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return this.l1.contains("*") || this.l1.contains(str);
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void clearRuntimeArgs() {
        this.E = "";
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
    @Override // io.dcloud.common.DHInterface.IApp
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String convert2AbsFullPath(java.lang.String r8, java.lang.String r9) {
        /*
            Method dump skipped, instructions count: 565
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.p.c5.convert2AbsFullPath(java.lang.String, java.lang.String):java.lang.String");
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String convert2LocalFullPath(String str, String str2) throws Throwable {
        String strConvert2AbsFullPath = convert2AbsFullPath(str, str2);
        byte b2 = this.V;
        if (b2 == 1 || DeviceInfo.isPrivateDirectory) {
            InputStream resInputStream = b2 == 1 ? PlatformUtil.getResInputStream(strConvert2AbsFullPath) : PlatformUtil.getInputStream(strConvert2AbsFullPath);
            if (resInputStream != null) {
                strConvert2AbsFullPath = obtainAppTempPath() + System.currentTimeMillis();
                try {
                    DHFile.writeFile(resInputStream, strConvert2AbsFullPath);
                    resInputStream.close();
                    return strConvert2AbsFullPath;
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return strConvert2AbsFullPath;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String convert2RelPath(String str) {
        try {
            int length = obtainAppDataPath().length();
            int length2 = obtainAppDocPath().length();
            int length3 = BaseInfo.sDocumentFullPath.length();
            int length4 = BaseInfo.sDownloadFullPath.length();
            if (str.startsWith(obtainAppDataPath())) {
                return BaseInfo.REL_PRIVATE_WWW_DIR + str.substring(length - 1);
            }
            int i = length - 1;
            if (str.startsWith(obtainAppDataPath().substring(0, i))) {
                return BaseInfo.REL_PRIVATE_WWW_DIR + str.substring(i, str.length());
            }
            if (str.startsWith(obtainAppDocPath())) {
                return BaseInfo.REL_PRIVATE_DOC_DIR + str.substring(length2 - 1);
            }
            int i2 = length2 - 1;
            if (str.startsWith(obtainAppDocPath().substring(0, i2))) {
                return BaseInfo.REL_PRIVATE_DOC_DIR + str.substring(i2);
            }
            if (str.startsWith(BaseInfo.sDocumentFullPath)) {
                return BaseInfo.REL_PUBLIC_DOCUMENTS_DIR + str.substring(length3 - 1);
            }
            int i3 = length3 - 1;
            if (str.startsWith(BaseInfo.sDocumentFullPath.substring(0, i3))) {
                return BaseInfo.REL_PUBLIC_DOCUMENTS_DIR + str.substring(i3);
            }
            if (str.startsWith(BaseInfo.sDownloadFullPath)) {
                return BaseInfo.REL_PUBLIC_DOWNLOADS_DIR + str.substring(length4 - 1);
            }
            int i4 = length4 - 1;
            if (!str.startsWith(BaseInfo.sDownloadFullPath.substring(0, i4))) {
                return str;
            }
            return BaseInfo.REL_PUBLIC_DOWNLOADS_DIR + str.substring(i4);
        } catch (Exception e2) {
            e2.printStackTrace();
            return str;
        }
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String convert2WebviewFullPath(String str, String str2) {
        boolean z;
        if (!PdrUtil.isEmpty(str2)) {
            if (this.U) {
                if (!str2.startsWith(DeviceInfo.HTTP_PROTOCOL)) {
                    return this.s0 + str2;
                }
            } else if (!str2.startsWith(DeviceInfo.FILE_PROTOCOL) && !str2.startsWith(DeviceInfo.HTTP_PROTOCOL) && !str2.startsWith(DeviceInfo.HTTPS_PROTOCOL)) {
                try {
                    if (DHFile.isExist(str2)) {
                        return "file:///" + b(str2);
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                if (str2.startsWith(DeviceInfo.sDeviceRootDir)) {
                    return DeviceInfo.FILE_PROTOCOL + str2;
                }
                if (str2.startsWith("/")) {
                    z = true;
                    str2 = str2.substring(1);
                } else {
                    z = false;
                }
                if (str2.startsWith(BaseInfo.REL_PRIVATE_WWW_DIR)) {
                    return obtainWebviewBaseUrl() + b(str2.substring(4));
                }
                if (str2.startsWith(BaseInfo.REL_PUBLIC_DOCUMENTS_DIR)) {
                    return DeviceInfo.FILE_PROTOCOL + BaseInfo.sDocumentFullPath + b(str2.substring(10));
                }
                if (str2.startsWith(BaseInfo.REL_PRIVATE_DOC_DIR)) {
                    return DeviceInfo.FILE_PROTOCOL + obtainAppDocPath() + b(str2.substring(4));
                }
                if (str2.startsWith(BaseInfo.REL_PUBLIC_DOWNLOADS_DIR)) {
                    return DeviceInfo.FILE_PROTOCOL + BaseInfo.sDownloadFullPath + b(str2.substring(10));
                }
                if (str != null && !z) {
                    return PdrUtil.standardizedURL(str, str2);
                }
                String strObtainWebviewBaseUrl = obtainWebviewBaseUrl();
                if (str != null && !PdrUtil.isEquals(str, strObtainWebviewBaseUrl) && str.contains("/www/")) {
                    strObtainWebviewBaseUrl = str.substring(0, str.indexOf("/www/") + 5);
                }
                return strObtainWebviewBaseUrl + b(str2);
            }
        }
        return str2;
    }

    JSONObject d(String str) throws IOException {
        if (this.x1 == null) {
            m();
        }
        if (this.x1 == null || TextUtils.isEmpty(str)) {
            return null;
        }
        return PdrUtil.getSitemapParameters(this.x1, obtainAppId(), str);
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void deleteAppTemp() {
        ThreadPool.self().addThreadTask(new e(), true);
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void diyStatusBarState() throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        AppStatusBarManager appStatusBarManager = this.m;
        if (appStatusBarManager == null) {
            return;
        }
        if (this.i) {
            appStatusBarManager.setFullScreen(getActivity(), this.i);
        } else {
            if (appStatusBarManager.checkImmersedStatusBar(getActivity(), this.b1)) {
                BaseInfo.isImmersive = true;
                this.m.setImmersive(getActivity(), true);
            } else {
                BaseInfo.isImmersive = false;
                this.m.setImmersive(getActivity(), false);
            }
            if (getActivity() != null) {
                if (PdrUtil.isEmpty(this.X0)) {
                    this.m.setStatusBarColor(getActivity(), BaseInfo.mDeStatusBarBackground);
                } else {
                    this.m.setStatusBarColor(getActivity(), this.X0.startsWith("#") ? PdrUtil.stringToColor(this.X0) : 0);
                }
            }
            this.m.setStatusBarMode(getActivity(), this.a1);
        }
        if (this.m.isFullScreenOrImmersive()) {
            updateScreenInfo(2);
        }
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String forceShortCut() {
        return this.A1;
    }

    boolean g(String str) {
        setRuntimeArgs(str);
        setStatus((byte) 3);
        Object objProcessEvent = this.y.processEvent(IMgr.MgrType.WindowMgr, 41, new Object[]{this, convert2WebviewFullPath(null, this.r0), Boolean.valueOf(this.T0)});
        if (objProcessEvent == null) {
            return true;
        }
        return Boolean.parseBoolean(String.valueOf(objProcessEvent));
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public IConfusionMgr getConfusionMgr() {
        return this.t1;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String getDirectPage() {
        return this.n1;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public IApp.IAppStatusListener getIAppStatusListener() {
        return this.P0;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String getOriginalDirectPage() {
        return this.o1;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String getPathByType(byte b2) {
        if (b2 == 0) {
            return obtainAppDataPath();
        }
        if (b2 == 1) {
            return obtainAppDocPath();
        }
        if (b2 == 2) {
            return BaseInfo.sDocumentFullPath;
        }
        if (b2 == 3) {
            return BaseInfo.sDownloadFullPath;
        }
        if (b2 != -1) {
            return null;
        }
        return BaseInfo.sBaseResAppsPath + this.o + "/" + BaseInfo.APP_WWW_FS_DIR;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String getPopGesture() {
        return this.S0;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public int getQuitModel() {
        return this.s1;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String getSystemInfo() throws JSONException {
        try {
            if (DeviceInfo.sSystemInfo == null) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(DeviceInfo.sSystemInfo.toString());
            jSONObject.put("uniCompileVersion", this.u0);
            jSONObject.put("uniRuntimeVersion", BaseInfo.uniVersionV3);
            jSONObject.put("browserName", WebViewFactory.isOther() ? "x5webview" : "chrome");
            jSONObject.put("appId", BaseInfo.sCurrentAppOriginalAppid);
            jSONObject.put(WXConfig.appName, this.t0);
            if (SDK.isUniMP) {
                jSONObject.put(WXConfig.appVersion, this.A);
                jSONObject.put("appVersionCode", this.B);
            } else {
                jSONObject.put(WXConfig.appVersion, b(getActivity()));
                jSONObject.put("appVersionCode", a((Context) getActivity()));
            }
            jSONObject.put("appWgtVersion", this.A);
            return jSONObject.toString();
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public boolean isOnAppRunningMode() {
        return this.V == 1;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public boolean isUniApp() {
        return this.q;
    }

    public float j() {
        return (PermissionControler.checkPermission(this.o, IFeature.F_DEVICE.toLowerCase(Locale.ENGLISH)) && getActivity() != null && NetworkTypeUtil.getNetworkType(getActivity()) == 4) ? 1000.0f : 0.0f;
    }

    void k() {
        if (PdrUtil.isEmpty(this.k0) || !DeviceInfo.startsWithSdcard(this.k0)) {
            setAppDataPath(BaseInfo.sCacheFsAppsPath + this.o + "/" + BaseInfo.REAL_PRIVATE_WWW_DIR);
        }
        if (PdrUtil.isEmpty(this.B1) || !DeviceInfo.startsWithSdcard(this.B1)) {
            setAppDocPath(BaseInfo.sBaseFsAppsPath + this.o + "/" + BaseInfo.REAL_PRIVATE_DOC_DIR);
        }
        if (PdrUtil.isEmpty(this.C1) || !DeviceInfo.startsWithSdcard(this.C1)) {
            this.C1 = BaseInfo.sCacheFsAppsPath + this.o + "/" + BaseInfo.APP_WEB_CHACHE;
        }
    }

    void m() throws IOException {
        File file = new File(c(this.o));
        if (file.exists()) {
            try {
                JSONObject jSONObject = new JSONObject(IOUtil.toString(new FileInputStream(file)));
                this.x1 = jSONObject;
                this.C = jSONObject.optString("version");
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }
        }
        byte[] fileContent = PlatformUtil.getFileContent("data/sitemap/" + this.o + ".json", 0);
        if (fileContent != null) {
            DHFile.writeFile(fileContent, 0, c(this.o));
            m();
        }
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public boolean manifestBeParsed() {
        return this.v || SDK.IntegratedMode.WEBVIEW == BaseInfo.sRuntimeMode;
    }

    public boolean n() {
        if (q() && this.v) {
            return this.P;
        }
        return true;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public boolean needRefreshApp() {
        return this.k1;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public boolean needReload() {
        return this.j1;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean o() {
        /*
            r5 = this;
            java.lang.String r0 = ""
            java.lang.String r1 = io.dcloud.common.util.BaseInfo.uniVersionV3
            boolean r1 = io.dcloud.common.util.PdrUtil.isEmpty(r1)
            if (r1 == 0) goto L53
            boolean r1 = io.dcloud.common.util.BaseInfo.SyncDebug
            if (r1 == 0) goto L1d
            java.lang.String r1 = "uni-jsframework-dev.js"
            java.io.InputStream r2 = io.dcloud.common.adapter.util.PlatformUtil.getResInputStream(r1)
            if (r2 == 0) goto L1d
            boolean r2 = io.dcloud.feature.internal.sdk.SDK.isUniMPSDK()
            if (r2 != 0) goto L1d
            goto L1f
        L1d:
            java.lang.String r1 = "uni-jsframework.js"
        L1f:
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L51
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L51
            android.app.Activity r4 = r5.getActivity()     // Catch: java.lang.Throwable -> L51
            android.content.res.AssetManager r4 = r4.getAssets()     // Catch: java.lang.Throwable -> L51
            java.io.InputStream r1 = r4.open(r1)     // Catch: java.lang.Throwable -> L51
            r3.<init>(r1)     // Catch: java.lang.Throwable -> L51
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L51
            java.lang.String r1 = r2.readLine()     // Catch: java.lang.Throwable -> L51
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch: java.lang.Throwable -> L51
            r3 = 2
            java.lang.String r1 = r1.substring(r3)     // Catch: java.lang.Throwable -> L51
            r2.<init>(r1)     // Catch: java.lang.Throwable -> L51
            java.lang.String r1 = "version"
            java.lang.String r0 = r2.optString(r1)     // Catch: java.lang.Throwable -> L51
            android.app.Activity r1 = r5.getActivity()     // Catch: java.lang.Throwable -> L51
            io.dcloud.common.util.BaseInfo.setUniVersionV3(r0, r1)     // Catch: java.lang.Throwable -> L51
            goto L55
        L51:
            goto L55
        L53:
            java.lang.String r0 = io.dcloud.common.util.BaseInfo.uniVersionV3
        L55:
            java.lang.String r1 = r5.w0
            boolean r1 = io.dcloud.common.util.PdrUtil.isEmpty(r1)
            r2 = 0
            if (r1 != 0) goto L6b
            java.lang.String r1 = r5.w0
            boolean r1 = r1.contains(r0)
            if (r1 == 0) goto L6b
            boolean r1 = r5.x0
            if (r1 != 0) goto L6b
            return r2
        L6b:
            boolean r1 = r5.y0
            if (r1 != 0) goto L95
            boolean r1 = r5.v
            if (r1 == 0) goto L95
            java.lang.String r1 = r5.u0
            java.lang.String r3 = "-1"
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L95
            java.lang.String r1 = r5.u0
            java.lang.String r1 = r1.trim()
            boolean r1 = r1.equals(r0)
            if (r1 != 0) goto L95
            boolean r0 = io.dcloud.common.util.PdrUtil.isEmpty(r0)
            if (r0 != 0) goto L95
            boolean r0 = r5.q
            if (r0 == 0) goto L95
            r0 = 1
            return r0
        L95:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.p.c5.o():boolean");
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String obtainAdaptationJs() throws IOException {
        if (this.T == null && !PdrUtil.isEmpty(this.K)) {
            byte[] fileContent = PlatformUtil.getFileContent(a(this.K), obtainRunningAppMode() == 1 ? 0 : 2);
            if (fileContent != null) {
                this.T = new String(fileContent);
            } else {
                this.T = "";
            }
        }
        return this.T;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String obtainAppDataPath() {
        String str = this.k0;
        if (str != null) {
            return str;
        }
        return this.o + "/www/";
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String obtainAppDocPath() {
        return this.B1;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String obtainAppId() {
        return this.o;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String obtainAppInfo() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("appid", this.o);
            jSONObject.put("versionName", this.A);
            jSONObject.put("name", this.t0);
            jSONObject.put("versionCode", this.B);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject.toString();
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String obtainAppLog() {
        return BaseInfo.sBaseFsAppsPath + this.o + "/log/";
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String obtainAppName() {
        return this.t0;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public byte obtainAppStatus() {
        return this.u;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String obtainAppTempPath() {
        return BaseInfo.sBaseFsAppsPath + this.o + "/temp/";
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String obtainAppVersionCode() {
        return this.B;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String obtainAppVersionName() {
        return this.A;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String obtainAppWebCachePath() {
        return this.C1;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String obtainConfigProperty(String str) {
        if (PdrUtil.isEquals(str, "adid")) {
            return this.N0;
        }
        if (PdrUtil.isEquals(str, "launchError")) {
            return this.M0;
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_AUTOCLOSE)) {
            return String.valueOf(this.Y);
        }
        if (PdrUtil.isEquals(str, "timeout")) {
            return String.valueOf(this.a0);
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_DELAY)) {
            return String.valueOf(this.b0);
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_SPLASHSCREEN)) {
            return String.valueOf(this.W);
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_WAITING)) {
            return String.valueOf(this.X);
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_H5PLUS)) {
            return String.valueOf(this.R);
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_funSetUA)) {
            return String.valueOf(this.S);
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_USER_AGENT)) {
            return this.L;
        }
        if (PdrUtil.isEquals(str, "error")) {
            return this.q0;
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_FULLSCREEN)) {
            return String.valueOf(this.i);
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_UNTRUSTEDCA)) {
            return this.p0;
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_LOADED_TIME)) {
            return this.Q0;
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_RAM_CACHE_MODE)) {
            return this.R0;
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_JSERROR)) {
            return this.N + "";
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_CRASH)) {
            return this.M + "";
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_USE_ENCRYPTION)) {
            return this.U0 + "";
        }
        if (PdrUtil.isEquals(str, "w2a_delay")) {
            return String.valueOf(this.c0);
        }
        if (PdrUtil.isEquals(str, "w2a_autoclose")) {
            return String.valueOf(this.Z);
        }
        if (PdrUtil.isEquals(str, "wap2app_running_mode")) {
            return this.O + "";
        }
        if (PdrUtil.isEquals(str, "injection")) {
            return this.i1 + "";
        }
        if (PdrUtil.isEquals(str, "event")) {
            return this.d0;
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_TARGET)) {
            return this.e0;
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_LPLUSERQUIRE)) {
            return this.f0;
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_SPLUSERQUIRE)) {
            return this.g0;
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_LGEOLOCATION)) {
            return this.h0;
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_SGEOLOCATION)) {
            return this.i0;
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.JSONKEY_STATUSBAR_BC)) {
            return this.X0 + "";
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.JSONKEY_STATUSBAR_MODE)) {
            return this.a1;
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.JSONKEY_STATUSBAR_IMMERSED)) {
            return this.b1 + "";
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.JSONKEY_STATUSBAR_LAUNCH_ISSTATUS)) {
            return String.valueOf(this.c1);
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.JSONKEY_STATUSBAR_LAUNCH_STATUSBAR_COLOR)) {
            return this.e1;
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.JSONKEY_STATUSBAR_SECOND_ISATATUS)) {
            return String.valueOf(this.d1);
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.JSONKEY_STATUSBAR_SECOND_STATUSBAR_COLOR)) {
            return this.f1;
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.JSONKEY_MAP_COORD_TYPE)) {
            return this.p1;
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.UNIAPP_WEEX_JS_SERVICE)) {
            return String.valueOf(this.g1);
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.APP_UNIAPP_VERSION)) {
            return this.u0;
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_UNIAPP_CONTROL)) {
            return this.q ? this.z0 : "h5+";
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.UNI_NVUE_DATA)) {
            JSONObject jSONObject = this.v0;
            if (jSONObject == null) {
                return null;
            }
            return jSONObject.toString();
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_CONCATENATE)) {
            return this.Q + "";
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.NVUE_LAUNCH_MODE)) {
            return this.A0;
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.JSON_KEY_DEBUG_REFRESH)) {
            return this.r1;
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.UNI_RESTART_TO_DIRECT)) {
            return String.valueOf(this.q1);
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.APP_IS_UNIAPP)) {
            return String.valueOf(this.q);
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_USE_V3_ENCRYPTION)) {
            return String.valueOf(this.V0);
        }
        if (PdrUtil.isEquals(str, IntentConst.UNIMP_RUN_EXTRA_INFO)) {
            return this.u1;
        }
        return null;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public IWebviewStateListener obtainLaunchPageStateListener() {
        return this.v1;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public Object obtainMgrData(IMgr.MgrType mgrType, int i, Object[] objArr) {
        return this.y.processEvent(mgrType, i, objArr);
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String obtainOriginalAppId() {
        return this.z;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public InputStream obtainResInStream(String str, String str2) {
        String strConvert2AbsFullPath = convert2AbsFullPath(str, str2);
        byte b2 = this.V;
        if (b2 == 1) {
            if (!PdrUtil.isDeviceRootDir(strConvert2AbsFullPath)) {
                return PlatformUtil.getResInputStream(strConvert2AbsFullPath);
            }
            try {
                return DHFile.getInputStream(DHFile.createFileHandler(strConvert2AbsFullPath));
            } catch (IOException e2) {
                Logger.w("WebApp.obtainResInStream", e2);
                return null;
            }
        }
        if (b2 != 0) {
            return null;
        }
        try {
            return DHFile.getInputStream(DHFile.createFileHandler(strConvert2AbsFullPath));
        } catch (IOException e3) {
            Logger.w("WebApp.obtainResInStream", e3);
            return null;
        }
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public byte obtainRunningAppMode() {
        return this.V;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String obtainRuntimeArgs(boolean z) {
        return z ? JSONObject.quote(this.E) : this.E;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public AppStatusBarManager obtainStatusBarMgr() {
        return this.m;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public JSONObject obtainThridInfo(IApp.ConfigProperty.ThridInfo thridInfo) throws IOException {
        switch (f.a[thridInfo.ordinal()]) {
            case 1:
                return this.D0;
            case 2:
                return this.F0;
            case 3:
                return this.G0;
            case 4:
                return this.H0;
            case 5:
                return this.J0;
            case 6:
                m();
                return this.x1;
            case 7:
                return this.y.g;
            case 8:
                return this.K0;
            case 9:
                return this.E0;
            default:
                return null;
        }
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String obtainVersionSitemap() {
        return this.C;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public Intent obtainWebAppIntent() {
        return this.O0;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String obtainWebviewBaseUrl() {
        return a(this.V);
    }

    @Override // io.dcloud.common.DHInterface.ISysEventListener
    public boolean onExecute(ISysEventListener.SysEventType sysEventType, Object obj) {
        byte b2 = this.u;
        if (b2 == 3) {
            return callSysEventListener(sysEventType, obj);
        }
        if (b2 == 1 && (sysEventType == ISysEventListener.SysEventType.onWebAppStop || sysEventType == ISysEventListener.SysEventType.onStop)) {
            s();
        }
        return false;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void onSplashClosed() throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        diyStatusBarState();
    }

    public boolean p() {
        return this.u == 3;
    }

    public boolean q() {
        Intent intentObtainWebAppIntent = obtainWebAppIntent();
        if (intentObtainWebAppIntent != null) {
            boolean z = this.G1;
            this.G1 = intentObtainWebAppIntent.getBooleanExtra(IntentConst.IS_STREAM_APP, z) | z;
        }
        return this.G1;
    }

    public boolean r() {
        Logger.d(Logger.AppMgr_TAG, this.o + " onStop");
        IApp.IAppStatusListener iAppStatusListener = this.P0;
        if (iAppStatusListener != null) {
            return iAppStatusListener.onStop();
        }
        return true;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void registerSysEventListener(ISysEventListener iSysEventListener, ISysEventListener.SysEventType sysEventType) {
        if (this.C0 == null) {
            this.C0 = new HashMap(1);
        }
        ArrayList arrayList = (ArrayList) this.C0.get(sysEventType);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.C0.put(sysEventType, arrayList);
        }
        arrayList.add(iSysEventListener);
    }

    public void s() {
        this.l1.clear();
        this.m1.clear();
        Activity activity = this.a;
        if (activity != null && (activity instanceof WebAppActivity)) {
            ((WebAppActivity) activity).onAppStop(this.o);
        }
        Logger.d(Logger.AppMgr_TAG, "webapp.onStoped");
        BaseInfo.s_Runing_App_Count--;
        callSysEventListener(ISysEventListener.SysEventType.onWebAppStop, this);
        d();
        PermissionUtil.removeTempPermission(this.a, this.o);
        b();
        deleteAppTemp();
        PermissionControler.unregisterRootPermission(this.o);
        this.y.e(this);
        if (getIAppStatusListener() != null) {
            getIAppStatusListener().onStoped(false, null);
        }
        this.y.processEvent(IMgr.MgrType.WindowMgr, 25, this);
        PrivacyManager.getInstance().unRegisterPrivacyAgreeAllListener();
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void setAppDataPath(String str) {
        if (this.V == 1) {
            if (str.startsWith(BaseInfo.sBaseResAppsPath)) {
                this.k0 = str;
                return;
            }
            this.k0 = BaseInfo.sBaseResAppsPath + this.o + "/" + BaseInfo.APP_WWW_FS_DIR;
            return;
        }
        if (new File(str).exists()) {
            this.k0 = str;
            return;
        }
        if (str.startsWith(DeviceInfo.sCacheRootDir)) {
            this.k0 = str;
            return;
        }
        this.k0 = DeviceInfo.sCacheRootDir + "/" + str;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void setAppDocPath(String str) {
        this.B1 = PdrUtil.appendByDeviceRootDir(str);
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void setConfigProperty(String str, String str2) {
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_AUTOCLOSE)) {
            this.Y = PdrUtil.parseBoolean(str2, this.Y, false);
            return;
        }
        if (PdrUtil.isEquals(str, "commit")) {
            a();
            return;
        }
        if (PdrUtil.isEquals(str, "timeout")) {
            this.a0 = PdrUtil.parseInt(str2, this.a0);
            return;
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_DELAY)) {
            this.b0 = PdrUtil.parseInt(str2, this.b0);
            return;
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_SPLASHSCREEN)) {
            this.W = PdrUtil.parseBoolean(str2, this.W, false);
            return;
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_WAITING)) {
            this.X = PdrUtil.parseBoolean(str2, this.X, false);
            return;
        }
        if (PdrUtil.isEquals(str, "name")) {
            this.t0 = str2;
            return;
        }
        if (PdrUtil.isEquals(str, "name")) {
            this.G = str2;
            return;
        }
        if (PdrUtil.isEquals(str, "email")) {
            this.H = str2;
            return;
        }
        if (PdrUtil.isEquals(str, "url")) {
            this.J = str2;
            return;
        }
        if (PdrUtil.isEquals(str, "name")) {
            this.A = str2;
            BaseInfo.sLastAppVersionName = str2;
            return;
        }
        if (PdrUtil.isEquals(str, "code")) {
            this.B = str2;
            return;
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_RUNMODE_LIBERATE)) {
            this.j0 = PdrUtil.parseBoolean(str2, this.W, false);
            return;
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_H5PLUS)) {
            this.R = PdrUtil.parseBoolean(str2, true, false);
            return;
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_funSetUA)) {
            this.S = PdrUtil.parseBoolean(str2, true, false);
            return;
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_USER_AGENT)) {
            this.L = str2;
            return;
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_FULLSCREEN)) {
            this.i = PdrUtil.parseBoolean(str2, this.i, false);
            return;
        }
        if (PdrUtil.isEquals(str, "webcache_path")) {
            this.C1 = str2;
            return;
        }
        if (PdrUtil.isEquals(str, "wap2app_running_mode")) {
            this.O = PdrUtil.parseBoolean(str2, false, false);
            return;
        }
        if (PdrUtil.isEquals(str, IApp.ConfigProperty.CONFIG_LOADED_TIME)) {
            this.Q0 = str2;
            return;
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.JSONKEY_STATUSBAR_BC)) {
            this.X0 = str2;
            return;
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.JSONKEY_STATUSBAR_MODE)) {
            this.a1 = str2;
            return;
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.JSONKEY_STATUSBAR_IMMERSED)) {
            this.b1 = Boolean.valueOf(str2).booleanValue();
            return;
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.JSONKEY_STATUSBAR_LAUNCH_ISSTATUS)) {
            this.c1 = Boolean.valueOf(str2).booleanValue();
            return;
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.JSONKEY_STATUSBAR_LAUNCH_STATUSBAR_COLOR)) {
            this.e1 = str2;
            return;
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.JSONKEY_STATUSBAR_SECOND_ISATATUS)) {
            this.d1 = Boolean.valueOf(str2).booleanValue();
            return;
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.JSONKEY_STATUSBAR_SECOND_STATUSBAR_COLOR)) {
            this.f1 = str2;
            return;
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.UNIAPP_WEEX_JS_SERVICE)) {
            this.g1 = Boolean.valueOf(str2).booleanValue();
            return;
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.JSON_KEY_DEBUG_REFRESH)) {
            this.r1 = str2;
        } else if (PdrUtil.isEquals(str, IApp.ConfigProperty.UNI_RESTART_TO_DIRECT)) {
            this.q1 = Boolean.valueOf(str2).booleanValue();
        } else if (PdrUtil.isEquals(str, IntentConst.UNIMP_RUN_EXTRA_INFO)) {
            this.u1 = str2;
        }
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void setDirectPage(String str) {
        this.n1 = str;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void setHideNavBarState(boolean z) {
        this.p = z;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void setIAppStatusListener(IApp.IAppStatusListener iAppStatusListener) {
        this.P0 = iAppStatusListener;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void setLaunchPageStateListener(IWebviewStateListener iWebviewStateListener) {
        this.v1 = iWebviewStateListener;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void setNeedRefreshApp(boolean z) {
        this.k1 = z;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void setQuitModel(int i) {
        this.s1 = i;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void setRuntimeArgs(String str) {
        if (PdrUtil.isEmpty(str)) {
            return;
        }
        this.E = str;
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void setStatus(byte b2) {
        this.u = b2;
        if (b2 == 3) {
            this.h1 = System.currentTimeMillis();
        }
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void setWebAppActivity(Activity activity) {
        this.a = activity;
        a(activity);
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x0142 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:115:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x012a A[Catch: Exception -> 0x01cb, MalformedURLException -> 0x01cd, TryCatch #6 {MalformedURLException -> 0x01cd, Exception -> 0x01cb, blocks: (B:48:0x011e, B:47:0x011b, B:53:0x012f, B:55:0x0142, B:57:0x0148, B:58:0x0150, B:59:0x0157, B:61:0x0167, B:63:0x0178, B:65:0x0186, B:66:0x0188, B:67:0x018a, B:69:0x0192, B:71:0x019a, B:73:0x01a2, B:75:0x01aa, B:76:0x01b2, B:77:0x01c2, B:80:0x01c7, B:51:0x012a), top: B:110:0x0065, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0157 A[Catch: JSONException -> 0x01c6, Exception -> 0x01cb, MalformedURLException -> 0x01cd, TryCatch #2 {JSONException -> 0x01c6, blocks: (B:55:0x0142, B:57:0x0148, B:58:0x0150, B:59:0x0157, B:61:0x0167, B:63:0x0178, B:65:0x0186, B:66:0x0188, B:67:0x018a, B:69:0x0192, B:71:0x019a, B:73:0x01a2, B:75:0x01aa, B:76:0x01b2, B:77:0x01c2), top: B:105:0x0142, outer: #6 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0167 A[Catch: JSONException -> 0x01c6, Exception -> 0x01cb, MalformedURLException -> 0x01cd, TryCatch #2 {JSONException -> 0x01c6, blocks: (B:55:0x0142, B:57:0x0148, B:58:0x0150, B:59:0x0157, B:61:0x0167, B:63:0x0178, B:65:0x0186, B:66:0x0188, B:67:0x018a, B:69:0x0192, B:71:0x019a, B:73:0x01a2, B:75:0x01aa, B:76:0x01b2, B:77:0x01c2), top: B:105:0x0142, outer: #6 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01c2 A[Catch: JSONException -> 0x01c6, Exception -> 0x01cb, MalformedURLException -> 0x01cd, TRY_LEAVE, TryCatch #2 {JSONException -> 0x01c6, blocks: (B:55:0x0142, B:57:0x0148, B:58:0x0150, B:59:0x0157, B:61:0x0167, B:63:0x0178, B:65:0x0186, B:66:0x0188, B:67:0x018a, B:69:0x0192, B:71:0x019a, B:73:0x01a2, B:75:0x01aa, B:76:0x01b2, B:77:0x01c2), top: B:105:0x0142, outer: #6 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001d  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01e2  */
    @Override // io.dcloud.common.DHInterface.IApp
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void setWebAppIntent(android.content.Intent r19) {
        /*
            Method dump skipped, instructions count: 517
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.p.c5.setWebAppIntent(android.content.Intent):void");
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String shortcutQuit() {
        return this.z1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.dcloud.common.DHInterface.IApp
    public void showSplash() {
        Activity activity = getActivity();
        if (activity instanceof IOnCreateSplashView) {
            activity.setIntent(this.O0);
            ((IOnCreateSplashView) activity).onCreateSplash(activity);
        }
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public boolean startFromShortCut() {
        return this.y1;
    }

    void t() {
        PermissionControler.registerPermission(this.o, this.B0);
    }

    public String toString() {
        return this.t0 + Operators.SUB + this.o + Operators.SUB + super.toString();
    }

    public void u() {
        b(false);
        setStatus((byte) 1);
        AppStatus.setAppStatus(this.o, 0);
        this.y.processEvent(IMgr.MgrType.FeatureMgr, 3, this.o);
        Logger.d(Logger.AppMgr_TAG, this.o + " will active change to unrunning");
        this.y.processEvent(null, 0, this);
        WebViewFactory.sUsePermissionWebviews.clear();
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void unregisterSysEventListener(ISysEventListener iSysEventListener, ISysEventListener.SysEventType sysEventType) {
        ArrayList arrayList;
        HashMap map = this.C0;
        if (map == null || (arrayList = (ArrayList) map.get(sysEventType)) == null) {
            return;
        }
        arrayList.remove(iSysEventListener);
        if (arrayList.isEmpty()) {
            this.C0.remove(sysEventType);
        }
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public void updateDirectPage(String str) throws JSONException, IOException {
        if (TextUtils.isEmpty(str)) {
            str = this.n1;
        }
        JSONObject jSONObjectD = d(str);
        if (jSONObjectD != null) {
            this.K0 = jSONObjectD;
            e();
            this.y.processEvent(IMgr.MgrType.WindowMgr, 48, this);
        }
    }

    public String v() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("appid", this.o);
            jSONObject.put("version", this.A);
            jSONObject.put("name", this.t0);
            jSONObject.put("versionCode", this.B);
            jSONObject.put("description", this.F);
            jSONObject.put("author", this.G);
            jSONObject.put("email", this.H);
            jSONObject.put(IApp.ConfigProperty.CONFIG_LICENSE, this.I);
            jSONObject.put("licensehref", this.J);
            jSONObject.put(IApp.ConfigProperty.CONFIG_FEATURES, new JSONArray((Collection) this.B0));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject.toString();
    }

    void w() {
        b(true);
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String obtainAuthority(String str) {
        String str2 = BaseInfo.sGlobalAuthority;
        if ((str2 != null && TextUtils.equals("*", str2)) || !q() || TextUtils.isEmpty(str) || e(this.o)) {
            return IApp.AUTHORITY_AUTHORIZED;
        }
        JSONObject jSONObject = this.I0;
        if (jSONObject != null) {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                if (str.equalsIgnoreCase(next)) {
                    return this.I0.optString(next, IApp.AUTHORITY_UNDETERMINED);
                }
            }
        }
        return IApp.AUTHORITY_UNDETERMINED;
    }

    /* JADX WARN: Removed duplicated region for block: B:283:0x0649  */
    /* JADX WARN: Removed duplicated region for block: B:290:0x0696  */
    /* JADX WARN: Removed duplicated region for block: B:294:0x06b8  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x06d0  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x06e8  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x0735  */
    /* JADX WARN: Removed duplicated region for block: B:309:0x075b  */
    /* JADX WARN: Removed duplicated region for block: B:310:0x0777  */
    /* JADX WARN: Removed duplicated region for block: B:351:0x089e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    boolean a(java.io.InputStream r61, java.lang.String r62, org.json.JSONObject r63) throws org.json.JSONException, java.security.NoSuchAlgorithmException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 2907
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.p.c5.a(java.io.InputStream, java.lang.String, org.json.JSONObject):boolean");
    }

    public void g() {
        ArrayList arrayList = this.B0;
        if (arrayList != null) {
            arrayList.clear();
            this.B0 = null;
        }
        HashMap map = this.C0;
        if (map != null) {
            map.clear();
            this.C0 = null;
        }
        this.t1.removeData(this.o);
        this.y = null;
        this.t = null;
        this.g1 = false;
    }

    private static PackageInfo c(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 16384);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00eb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    boolean c(java.lang.String r10, org.json.JSONObject r11) {
        /*
            Method dump skipped, instructions count: 264
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.p.c5.c(java.lang.String, org.json.JSONObject):boolean");
    }

    boolean f(String str) {
        if (!this.v && this.x) {
            return false;
        }
        Logger.e("Webapp start " + this.o);
        Activity activity = this.a;
        if (activity != null && (activity instanceof WebAppActivity)) {
            ((WebAppActivity) activity).onAppStart(this.o);
            ((WebAppActivity) this.a).onAppStart(this);
        }
        BaseInfo.s_Runing_App_Count++;
        this.w = true;
        this.x = !this.v;
        setRuntimeArgs(str);
        return b(5);
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public InputStream obtainResInStream(String str) {
        return obtainResInStream(null, str);
    }

    private String c(String str) {
        return BaseInfo.sBaseFsSitMapPath + str + "/_sitemap.json";
    }

    @Override // io.dcloud.common.DHInterface.IApp
    public String convert2AbsFullPath(String str) {
        return convert2AbsFullPath(null, str);
    }

    void b(byte b2) {
        this.V = b2;
    }

    private static String b(String str) {
        return (str == null || str.length() <= 0 || str.charAt(0) != '/') ? str : b(str.substring(1));
    }

    private boolean b(int i) {
        PermissionUtil.sUseStreamAppPermissionDialogCount = 0;
        WebViewFactory.sUsePermissionWebviews.clear();
        PermissionUtil.removeTempPermission(this.a, this.o);
        Logger.e(H1, "start0 mAppid===" + this.o);
        BaseInfo.sCurrentAppOriginalAppid = this.z;
        BaseInfo.putStartupTimeData(this.o, String.valueOf(System.currentTimeMillis()));
        BaseInfo.sProcessId = Process.myPid();
        String str = H1;
        StringBuilder sb = new StringBuilder();
        sb.append(this.o);
        sb.append(this.V == 1 ? " APP_RUNNING_MODE" : " FS_RUNNING_MODE");
        Logger.i(str, sb.toString());
        t();
        setStatus((byte) 3);
        IApp.IAppStatusListener iAppStatusListener = this.P0;
        if (iAppStatusListener != null) {
            iAppStatusListener.onStart();
        }
        Logger.i(H1, "mLaunchPath=" + this.l0);
        Logger.i("download_manager", "webapp start task begin success appid=" + this.o + " mLaunchPath=" + this.l0);
        String str2 = TestUtil.START_STREAM_APP;
        StringBuilder sb2 = new StringBuilder("webapp start appid=");
        sb2.append(this.o);
        TestUtil.print(str2, sb2.toString());
        BaseInfo.setLoadingLaunchePage(true, "start0");
        String stringExtra = getActivity().getIntent().getStringExtra(IntentConst.WEBAPP_ACTIVITY_LAUNCH_PATH);
        if (stringExtra != null && !"".equals(stringExtra.trim())) {
            getActivity().getIntent().removeExtra(IntentConst.WEBAPP_ACTIVITY_LAUNCH_PATH);
            if (!"about:blank".equals(stringExtra)) {
                stringExtra = convert2WebviewFullPath(null, stringExtra);
            }
            this.o0 = true;
        } else if (BaseInfo.isWap2AppAppid(this.o) && !TextUtils.isEmpty(this.n0)) {
            stringExtra = convert2WebviewFullPath(null, this.n0);
        } else {
            stringExtra = convert2WebviewFullPath(null, this.l0);
        }
        if (a((IApp) this) && !new File(a(BaseInfo.sConfigXML)).exists()) {
            stringExtra = TextUtils.isEmpty(this.m0) ? this.o1 : this.m0;
        }
        Uri data = getActivity().getIntent().getData();
        if (data != null && data.toString().endsWith(".html")) {
            stringExtra = data.toString();
        }
        if (this.q1) {
            stringExtra = convert2WebviewFullPath(null, "__uniappview.html");
        }
        Object objProcessEvent = this.y.processEvent(IMgr.MgrType.WindowMgr, i, new Object[]{this, stringExtra, Boolean.valueOf(this.T0), this.W0});
        if (objProcessEvent == null) {
            return true;
        }
        return Boolean.parseBoolean(String.valueOf(objProcessEvent));
    }

    void b(boolean z) {
        this.b.onAppUnActive(this);
        if (z) {
            callSysEventListener(ISysEventListener.SysEventType.onWebAppPause, this);
            callSysEventListener(ISysEventListener.SysEventType.onWebAppBackground, this);
        }
        IApp.IAppStatusListener iAppStatusListener = this.P0;
        if (iAppStatusListener != null) {
            iAppStatusListener.onPause(this, null);
        }
        setStatus((byte) 2);
    }

    private void b() {
        if (q() || !s.c(getActivity())) {
            return;
        }
        ThreadPool.self().addThreadTask(new d(obtainAppId()));
    }

    public static String b(Context context) {
        return c(context).versionName;
    }

    private JSONObject a(InputStream inputStream) {
        try {
            if (!this.q) {
                inputStream = new UnicodeInputStream(inputStream, Charset.defaultCharset().name());
            }
            return new JSONObject(new String(IOUtil.getBytes(inputStream)));
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Override // io.dcloud.p.d5
    void a(Activity activity) {
        super.a(activity);
        AppStatusBarManager appStatusBarManager = this.m;
        if (appStatusBarManager != null) {
            appStatusBarManager.checkImmersedStatusBar(activity, this.b1);
            this.m.isFullScreen = isFullScreen();
        }
        this.l.mJsonViewOption = JSONUtil.createJSONObject("{}");
        this.f = PdrUtil.parseInt(SP.getBundleData(getActivity(), BaseInfo.PDR, "StatusBarHeight"), 0);
        updateScreenInfo(4);
        this.y1 = false;
        IActivityHandler iActivityHandler = DCloudAdapterUtil.getIActivityHandler(getActivity());
        if (!q() && iActivityHandler != null) {
            HashMap map = new HashMap();
            map.put(CreateShortResultReceiver.KEY_VERSIONNAME, this.A);
            map.put("appid", this.o);
            map.put("name", this.t0);
            map.put("adid", this.N0);
            map.put("bg", this.Y0);
            map.put(WXBasicComponentType.IMG, convert2AbsFullPath(this.Z0));
            k.a(getActivity(), this.o, "save", map);
        }
        Intent intent = activity.getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null && extras.containsKey(IntentConst.FROM_SHORT_CUT_STRAT) && extras.getBoolean(IntentConst.FROM_SHORT_CUT_STRAT)) {
                this.y1 = true;
            }
            if (extras != null && extras.containsKey(IntentConst.WEBAPP_ACTIVITY_CREATE_SHORTCUT)) {
                this.A1 = extras.getString(IntentConst.WEBAPP_ACTIVITY_CREATE_SHORTCUT);
            }
            if (extras != null && extras.containsKey("shortcutQuit")) {
                this.z1 = extras.getString("shortcutQuit");
            }
            if (extras != null && extras.containsKey(IntentConst.START_FORCE_SHORT_QUIT)) {
                this.z1 = extras.getString(IntentConst.START_FORCE_SHORT_QUIT);
            }
            if (intent.hasExtra(IntentConst.START_FORCE_SHORT)) {
                this.A1 = intent.getStringExtra(IntentConst.START_FORCE_SHORT);
            }
            if (TextUtils.isEmpty(this.A1)) {
                String launchType = BaseInfo.getLaunchType(intent);
                this.A1 = AbsoluteConst.INSTALL_OPTIONS_FORCE;
                if (launchType.equals("scheme")) {
                    this.A1 = "query";
                    return;
                }
                if (this.P) {
                    this.A1 = AbsoluteConst.INSTALL_OPTIONS_FORCE;
                    return;
                }
                String string = SP.getOrCreateBundle(activity, "pdr").getString(AbsoluteConst.TEST_RUN + this.o, null);
                if (!TextUtils.isEmpty(string) && string.equals("__am=t")) {
                    this.A1 = AbsoluteConst.INSTALL_OPTIONS_FORCE;
                } else {
                    this.A1 = "none";
                }
            }
        }
    }

    String a(String str) {
        return this.k0 + str;
    }

    void a(ICallBack iCallBack) {
        if ((BaseInfo.ISDEBUG || this.j0) && this.V == 1) {
            ThreadPool.self().addThreadTask(new c(iCallBack), true);
        } else {
            iCallBack.onCallBack(0, null);
        }
    }

    private boolean a(IApp iApp) {
        return (TextUtils.isEmpty(iApp.getOriginalDirectPage()) || iApp.obtainWebAppIntent().hasExtra(IntentConst.DIRECT_PAGE)) ? false : true;
    }

    boolean a(boolean z) {
        if (z) {
            this.r1 = null;
            this.y.processEvent(IMgr.MgrType.WindowMgr, 76, this);
        }
        setAppDataPath(BaseInfo.sCacheFsAppsPath + this.o + DeviceInfo.sSeparatorChar + BaseInfo.REAL_PRIVATE_WWW_DIR);
        boolean zB = b(this.o, null);
        if (!zB) {
            return zB;
        }
        setConfigProperty(IApp.ConfigProperty.CONFIG_funSetUA, String.valueOf(false));
        PermissionUtil.clearUseRejectedCache();
        showSplash();
        this.y.processEvent(IMgr.MgrType.FeatureMgr, 3, this.o);
        callSysEventListener(ISysEventListener.SysEventType.onWebAppReStart, null);
        this.g1 = false;
        TestUtil.record(AbsoluteConst.RUN_5AP_TIME_KEY);
        return b(10);
    }

    private void a() {
        IPdrModule iPdrModuleA;
        if (this.q) {
            return;
        }
        if (!SDK.isUniMPSDK()) {
            io.dcloud.p.b.g().a(this.a);
        }
        if (q() || (iPdrModuleA = q3.a().a("commit")) == null) {
            return;
        }
        String str = this.N0;
        JSONObject jSONObject = this.y.g;
        iPdrModuleA.execute("start_up", new Object[]{this, str, jSONObject != null ? jSONObject.optString("version") : "0.1"});
    }

    public static int a(Context context) {
        return c(context).versionCode;
    }

    IFrameView a(IWebviewStateListener iWebviewStateListener) {
        t();
        return (IFrameView) this.y.processEvent(IMgr.MgrType.WindowMgr, 17, new Object[]{this, convert2WebviewFullPath(null, this.l0), iWebviewStateListener});
    }

    IFrameView a(IWebviewStateListener iWebviewStateListener, IDCloudWebviewClientListener iDCloudWebviewClientListener) {
        t();
        return (IFrameView) this.y.processEvent(IMgr.MgrType.WindowMgr, 17, new Object[]{this, convert2WebviewFullPath(null, this.l0), iWebviewStateListener, iDCloudWebviewClientListener});
    }

    private boolean a(ISysEventListener iSysEventListener, ISysEventListener.SysEventType sysEventType) {
        return !(iSysEventListener instanceof IBoot) || PdrUtil.parseBoolean(String.valueOf(this.y.processEvent(null, 1, iSysEventListener)), false, false) || !(sysEventType == ISysEventListener.SysEventType.onStart || sysEventType == ISysEventListener.SysEventType.onStop || sysEventType == ISysEventListener.SysEventType.onPause || sysEventType == ISysEventListener.SysEventType.onResume) || (iSysEventListener instanceof ReceiveSystemEventVoucher);
    }

    public static boolean a(ISysEventListener.SysEventType sysEventType) {
        return (sysEventType == ISysEventListener.SysEventType.onKeyDown || sysEventType == ISysEventListener.SysEventType.onKeyUp || sysEventType == ISysEventListener.SysEventType.onKeyLongPress) ? false : true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0239 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:107:0x01d8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0202 A[Catch: Exception -> 0x0289, LOOP:0: B:71:0x0200->B:72:0x0202, LOOP_END, TryCatch #1 {Exception -> 0x0289, blocks: (B:60:0x01d8, B:62:0x01df, B:66:0x01ed, B:68:0x01f5, B:70:0x01fb, B:72:0x0202, B:73:0x0223, B:75:0x0229, B:77:0x0239, B:79:0x023e, B:80:0x0255, B:82:0x025c, B:83:0x025f, B:85:0x0279, B:64:0x01e7), top: B:107:0x01d8, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0229 A[Catch: Exception -> 0x0289, TRY_LEAVE, TryCatch #1 {Exception -> 0x0289, blocks: (B:60:0x01d8, B:62:0x01df, B:66:0x01ed, B:68:0x01f5, B:70:0x01fb, B:72:0x0202, B:73:0x0223, B:75:0x0229, B:77:0x0239, B:79:0x023e, B:80:0x0255, B:82:0x025c, B:83:0x025f, B:85:0x0279, B:64:0x01e7), top: B:107:0x01d8, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x02a6  */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v11, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r7v22 */
    /* JADX WARN: Type inference failed for: r7v23, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r7v27, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r7v5, types: [java.io.File] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    boolean a(java.lang.String r14, org.json.JSONObject r15) {
        /*
            Method dump skipped, instructions count: 697
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.p.c5.a(java.lang.String, org.json.JSONObject):boolean");
    }

    private String a(byte b2) {
        byte b3 = this.V;
        if (b3 != 1) {
            if (b3 != 0) {
                return null;
            }
            return DeviceInfo.FILE_PROTOCOL + this.k0;
        }
        return BaseInfo.sBaseResAppsFullPath + this.o + "/" + BaseInfo.APP_WWW_FS_DIR;
    }

    public void a(String str, int i) {
        this.E1.put(str, Integer.valueOf(i));
        if (TextUtils.isEmpty(this.F1)) {
            this.F1 = str + "=" + i;
        } else {
            this.F1 += "&" + str + "=" + i;
        }
        SP.getOrCreateBundle(getActivity(), this.o + "_1").edit().putString("Authorize", this.F1).commit();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean a(org.json.JSONObject r4, io.dcloud.p.e5 r5) {
        /*
            r3 = this;
            r0 = 1
            if (r4 == 0) goto L20
            java.lang.String r1 = "control"
            boolean r2 = r4.has(r1)
            if (r2 == 0) goto L20
            java.lang.String r4 = r4.optString(r1)
            boolean r1 = android.text.TextUtils.isEmpty(r4)
            if (r1 != 0) goto L1e
            java.lang.String r1 = "uni-v3"
            boolean r4 = r4.equals(r1)
            if (r4 == 0) goto L1e
            goto L20
        L1e:
            r4 = 0
            goto L21
        L20:
            r4 = 1
        L21:
            if (r4 != 0) goto L2f
            r5.a = r0
            java.lang.String r0 = io.dcloud.common.constant.DOMException.MSG_RUNTIME_COMPONENTS_MODE_NOT_SUPPORT
            r1 = 1250(0x4e2, float:1.752E-42)
            java.lang.String r0 = io.dcloud.common.constant.DOMException.toJSON(r1, r0)
            r5.b = r0
        L2f:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.p.c5.a(org.json.JSONObject, io.dcloud.p.e5):boolean");
    }
}
