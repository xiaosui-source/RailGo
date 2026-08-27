package io.dcloud.p;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.BaseFeature;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IBoot;
import io.dcloud.common.DHInterface.ICore;
import io.dcloud.common.DHInterface.IFeature;
import io.dcloud.common.DHInterface.IFrameView;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.IWaiter;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.IntentConst;
import io.dcloud.common.core.permission.PermissionControler;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.DataUtil;
import io.dcloud.common.util.ErrorDialogUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import io.dcloud.feature.internal.sdk.SDK;
import io.src.dcloud.adapter.DCloudAdapterUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class h1 extends AbsMgr implements IMgr.FeatureEvent {
    private HashMap a;
    private HashMap b;
    private HashMap c;
    private HashMap d;
    private HashMap e;
    private String f;
    private ArrayList g;
    HashMap h;

    public h1(ICore iCore) {
        super(iCore, "featuremgr", IMgr.MgrType.FeatureMgr);
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = new ArrayList();
        this.h = new HashMap();
        b();
        if (c()) {
            return;
        }
        c();
    }

    private Object a(String str, Activity activity) {
        return null;
    }

    private String a(IApp iApp, IFrameView iFrameView) {
        String str = (String) this.h.get(iApp.obtainAppId() + "_" + iApp.obtainAppVersionName());
        if (str != null) {
            return str;
        }
        String strA = a(iApp, iFrameView.obtainWebView());
        this.h.put(iApp.obtainAppId() + "_" + iApp.obtainAppVersionName(), strA);
        return strA;
    }

    private HashMap a(HashMap map) {
        return map;
    }

    private void c(String str) {
        if (((IFeature) this.c.get(str)) == null) {
            try {
                IFeature iFeature = (IFeature) Class.forName(str).newInstance();
                this.c.put(str, iFeature);
                iFeature.init(this, str);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException unused) {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x00bb A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0023 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    java.util.HashMap d() {
        /*
            Method dump skipped, instructions count: 244
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.p.h1.d():java.util.HashMap");
    }

    @Override // io.dcloud.common.DHInterface.AbsMgr
    public void dispose() {
        HashMap map = this.c;
        if (map != null) {
            Collection collectionValues = map.values();
            if (collectionValues != null) {
                Iterator it = collectionValues.iterator();
                while (it.hasNext()) {
                    ((IFeature) it.next()).dispose(null);
                }
            }
            this.c.clear();
            this.c = null;
        }
    }

    @Override // io.dcloud.common.DHInterface.IMgr
    public Object processEvent(IMgr.MgrType mgrType, int i, Object obj) {
        try {
        } catch (Throwable th) {
            Logger.w("FeatureMgr.processEvent", th);
        }
        if (!checkMgrId(mgrType)) {
            return this.mCore.dispatchEvent(mgrType, i, obj);
        }
        switch (i) {
            case 0:
                this.c = new HashMap(1);
                this.b = new HashMap(1);
                this.a = new HashMap(1);
                this.e = new HashMap();
                this.d = new HashMap(1);
                return d();
            case 1:
                Object[] objArr = (Object[]) obj;
                return AbsoluteConst.UNI_SYNC_EXEC_METHOD.equalsIgnoreCase(String.valueOf(objArr[2])) ? a(objArr) : b(objArr);
            case 2:
                Object[] objArr2 = (Object[]) obj;
                String strA = a((IApp) objArr2[0], (IFrameView) objArr2[1]);
                if (strA == null) {
                    Process.killProcess(Process.myPid());
                }
                return strA;
            case 3:
                a(String.valueOf(obj));
                return null;
            case 4:
                return this.a.get(String.valueOf(obj));
            case 5:
                String[] strArr = (String[]) obj;
                String str = strArr[0];
                String str2 = strArr[1];
                String str3 = strArr[2];
                if (!PdrUtil.isEmpty(str) && !PdrUtil.isEmpty(str2)) {
                    this.b.put(str.toLowerCase(Locale.ENGLISH), str2);
                }
                if (!PdrUtil.isEmpty(str3)) {
                    this.g.add(str3);
                }
                return null;
            case 6:
            case 7:
            default:
                return null;
            case 8:
                String[] strArr2 = (String[]) obj;
                return Boolean.valueOf(PermissionControler.checkPermission(strArr2[0], strArr2[1]));
            case 9:
                return Boolean.valueOf(b(String.valueOf(obj)));
            case 10:
                try {
                    Object[] objArr3 = (Object[]) obj;
                    Object obj2 = objArr3[0];
                    IFeature iFeatureB = b(String.valueOf(objArr3[1]), obj2 instanceof IWebview ? ((IWebview) obj2).getActivity() : obj2 instanceof IApp ? ((IApp) obj2).getActivity() : null);
                    if (iFeatureB instanceof IWaiter) {
                        return ((IWaiter) iFeatureB).doForFeature(String.valueOf(objArr3[2]), objArr3[3]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            case 11:
                c((String) ((Object[]) obj)[0]);
                return null;
        }
    }

    private String b(IApp iApp) {
        String metaValue = AndroidResources.getMetaValue("DCLOUD_UNIPUSH");
        return (PdrUtil.isEmpty(metaValue) || !Boolean.valueOf(metaValue).booleanValue()) ? "" : "window.__isUniPush__ = true;";
    }

    private synchronized String b(Object[] objArr) {
        return a(objArr);
    }

    public String a(IWebview iWebview) {
        StringBuffer stringBuffer = new StringBuffer();
        String webviewUUID = iWebview.getWebviewUUID();
        if (PdrUtil.isEmpty(webviewUUID)) {
            webviewUUID = String.valueOf(iWebview.obtainFrameView().hashCode());
        }
        stringBuffer.append("window.__HtMl_Id__= '" + webviewUUID + "';");
        if (PdrUtil.isEmpty(iWebview.obtainFrameId())) {
            stringBuffer.append("window.__WebVieW_Id__= undefined;");
        } else {
            stringBuffer.append("window.__WebVieW_Id__= '" + iWebview.obtainFrameId() + "';");
        }
        stringBuffer.append("try{window.plus.__tag__='_plus_all_'}catch(e){}");
        return stringBuffer.toString();
    }

    public boolean c() {
        try {
            if (BaseInfo.ISDEBUG && DHFile.isExist("/sdcard/dcloud/all.js")) {
                this.f = new String(PlatformUtil.getFileContent("/sdcard/dcloud/all.js", 2));
            } else {
                this.f = new String(PlatformUtil.getFileContent(DCloudAdapterUtil.getRuntimeJsPath(), 1));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    IFeature b(String str, Activity activity) {
        String str2;
        ClassNotFoundException e;
        IFeature iFeature;
        if (str.contains(",")) {
            String[] strArrSplit = str.split(",");
            String str3 = strArrSplit[0];
            str2 = strArrSplit[1];
            str = str3;
        } else {
            str2 = null;
        }
        IFeature iFeature2 = (IFeature) this.c.get(str);
        if (iFeature2 == null) {
            String str4 = (String) this.b.get(str);
            if (!PdrUtil.isEmpty(str4)) {
                str2 = str4;
            }
            if (str2 != null && this.e.containsKey(str)) {
                IBoot iBoot = (IBoot) this.e.get(str);
                if ((iBoot instanceof BaseFeature) && str2.equals(iBoot.getClass().getName())) {
                    iFeature2 = (BaseFeature) iBoot;
                }
            }
            if (iFeature2 == null) {
                if (str2 != null) {
                    try {
                        IFeature iFeatureA = f1.a(this, str);
                        if (iFeatureA == null) {
                            iFeature = (IFeature) Class.forName(str2).newInstance();
                            try {
                                this.c.put(str, iFeature);
                                iFeature.init(this, str);
                                return iFeature;
                            } catch (ClassNotFoundException e2) {
                                e = e2;
                                Logger.i("featuremgr", "loadFeature error " + e.toString());
                                try {
                                    IFeature iFeature3 = (IFeature) a(str, activity);
                                    if (iFeature3 == null) {
                                        return iFeature3;
                                    }
                                    try {
                                        this.c.put(str, iFeature3);
                                        iFeature3.init(this, str);
                                        return iFeature3;
                                    } catch (Exception e3) {
                                        e = e3;
                                        iFeature = iFeature3;
                                        e.printStackTrace();
                                        return iFeature;
                                    }
                                } catch (Exception e4) {
                                    e = e4;
                                }
                            } catch (IllegalAccessException e5) {
                                e = e5;
                                iFeature2 = iFeature;
                                e.printStackTrace();
                                return iFeature2;
                            } catch (InstantiationException e6) {
                                e = e6;
                                iFeature2 = iFeature;
                                e.printStackTrace();
                                return iFeature2;
                            }
                        } else {
                            this.c.put(str, iFeatureA);
                            return iFeatureA;
                        }
                    } catch (ClassNotFoundException e7) {
                        IFeature iFeature4 = iFeature2;
                        e = e7;
                        iFeature = iFeature4;
                    } catch (IllegalAccessException e8) {
                        e = e8;
                    } catch (InstantiationException e9) {
                        e = e9;
                    }
                } else {
                    try {
                        IFeature iFeature5 = (IFeature) a(str, activity);
                        if (iFeature5 == null) {
                            return iFeature5;
                        }
                        try {
                            this.c.put(str, iFeature5);
                            iFeature5.init(this, str);
                            return iFeature5;
                        } catch (Exception e10) {
                            e = e10;
                            iFeature2 = iFeature5;
                            e.printStackTrace();
                            return iFeature2;
                        }
                    } catch (Exception e11) {
                        e = e11;
                    }
                }
                return iFeature2;
            }
            this.c.put(str, iFeature2);
        }
        return iFeature2;
    }

    private String a(IApp iApp, IWebview iWebview) {
        String str;
        StringBuffer stringBuffer = new StringBuffer("javascript:function __load__plus__(){try{");
        int i = Build.VERSION.SDK_INT;
        if (TextUtils.isEmpty(this.f) || this.f.length() < 400) {
            return null;
        }
        stringBuffer.append("window._____isDebug_____=" + BaseInfo.ISDEBUG + ";");
        stringBuffer.append("window._____platform_____=1;");
        stringBuffer.append("window._____platform_os_version_____=" + i + ";");
        stringBuffer.append(this.f);
        if (PermissionControler.checkPermission(iApp.obtainAppId(), IFeature.F_DEVICE.toLowerCase(Locale.ENGLISH)) || !iApp.manifestBeParsed()) {
            if (PdrUtil.isEmpty(DeviceInfo.DEVICESTATUS_JS)) {
                try {
                    DeviceInfo.initGsmCdmaCell();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                DeviceInfo.getDevicestatus_js(iApp);
            }
            stringBuffer.append(DeviceInfo.DEVICESTATUS_JS);
        }
        SDK.IntegratedMode integratedMode = BaseInfo.sRuntimeMode;
        StringBuilder sb = new StringBuilder("window.__NWin_Enable__=");
        sb.append(integratedMode == SDK.IntegratedMode.WEBVIEW ? String.valueOf(false) : String.valueOf(true));
        sb.append(";");
        stringBuffer.append(sb.toString());
        if (PermissionControler.checkPermission(iApp.obtainAppId(), IFeature.F_RUNTIME) || !iApp.manifestBeParsed()) {
            String strObtainConfigProperty = iApp.obtainConfigProperty(IApp.ConfigProperty.CONFIG_LOADED_TIME);
            if (BaseInfo.ISAMU) {
                str = StringUtil.format(AbsoluteConst.JS_RUNTIME_VERSIONs, iApp.obtainAppVersionName(), "1.9.9.82603", iApp.obtainAppId(), strObtainConfigProperty);
            } else {
                str = StringUtil.format(AbsoluteConst.JS_RUNTIME_VERSIONs, AndroidResources.mApplicationInfo.versionName, "1.9.9.82603", iApp.obtainAppId(), strObtainConfigProperty);
            }
            stringBuffer.append(StringUtil.format(AbsoluteConst.JS_RUNTIME_BASE, str));
            stringBuffer.append(StringUtil.format(AbsoluteConst.JS_RUNTIME_BASE, StringUtil.format(AbsoluteConst.JS_RUNTIME_ARGUMENTS, DataUtil.utf8ToUnicode(iApp.obtainRuntimeArgs(true)))));
            String launcherData = BaseInfo.getLauncherData(iApp.obtainAppId());
            if (!PdrUtil.isEmpty(iApp.obtainWebAppIntent())) {
                String stringExtra = iApp.obtainWebAppIntent().getStringExtra(IntentConst.FROM_STREAM_OPEN_FLAG);
                if (!TextUtils.isEmpty(stringExtra) && !TextUtils.equals(stringExtra, iApp.obtainAppId())) {
                    launcherData = launcherData + ":" + stringExtra;
                }
            }
            stringBuffer.append(StringUtil.format(AbsoluteConst.JS_RUNTIME_BASE, StringUtil.format(AbsoluteConst.JS_RUNTIME_LAUNCHER, launcherData)));
            stringBuffer.append(StringUtil.format(AbsoluteConst.JS_RUNTIME_BASE, StringUtil.format(AbsoluteConst.JS_RUNTIME_CHANNEL, BaseInfo.getAnalysisChannel())));
            String bundleData = SP.getBundleData(iApp.getActivity(), "pdr", iApp.obtainAppId() + AbsoluteConst.LAUNCHTYPE);
            if (TextUtils.isEmpty(bundleData)) {
                bundleData = "default";
            }
            stringBuffer.append(StringUtil.format(AbsoluteConst.JS_RUNTIME_BASE, StringUtil.format(AbsoluteConst.JS_RUNTIME_ORIGIN, bundleData)));
            stringBuffer.append(StringUtil.format(AbsoluteConst.JS_RUNTIME_BASE, StringUtil.format(AbsoluteConst.JS_RUNTIME_STARTUPTIME, String.valueOf(BaseInfo.getStartupTimeData(iApp.obtainAppId())))));
            stringBuffer.append(StringUtil.format(AbsoluteConst.JS_RUNTIME_BASE, StringUtil.format(AbsoluteConst.JS_RUNTIME_PROCESSID, Long.valueOf(BaseInfo.sProcessId))));
            stringBuffer.append(StringUtil.format(AbsoluteConst.JS_RUNTIME_BASE, StringUtil.format("p.runtime.versionCode = %d;", Integer.valueOf(AndroidResources.versionCode))));
            if (BaseInfo.isUniAppAppid(iApp)) {
                stringBuffer.append(StringUtil.format(AbsoluteConst.JS_RUNTIME_BASE, StringUtil.format("p.runtime.uniVersion = '%s';", BaseInfo.uniVersionV3)));
            }
        }
        if (PermissionControler.checkPermission(iApp.obtainAppId(), IFeature.F_NAVIGATOR)) {
            DeviceInfo.updateStatusBarHeight(iWebview.getActivity());
            stringBuffer.append(String.format(Locale.UK, AbsoluteConst.JS_NAVIGATOR_STATUSBAR_HEIGHT, Float.valueOf(DeviceInfo.sStatusBarHeight / iWebview.getScale())));
        }
        int size = this.g.size();
        for (int i2 = 0; i2 < size; i2++) {
            stringBuffer.append((String) this.g.get(i2));
        }
        stringBuffer.append(a());
        stringBuffer.append("}catch(e){console.log('__load__plus__ function error=' + e);}}window.__load__plus__&&window.__load__plus__();");
        stringBuffer.append(a(iApp));
        stringBuffer.append(b(iApp));
        return stringBuffer.toString();
    }

    private void b() {
        try {
            g1.a(Class.forName("io.dcloud.feature.d").getConstructor(Context.class).newInstance(getContext()));
        } catch (Exception unused) {
            Logger.e("fmgr no dp");
        }
    }

    private boolean b(String str) {
        return this.b.containsKey(str);
    }

    private String a(IApp iApp) {
        if (!BaseInfo.isUniNViewBackgroud()) {
            return "";
        }
        String str = (String) processEvent(IMgr.MgrType.FeatureMgr, 10, new Object[]{iApp, "weex,io.dcloud.feature.weex.WeexFeature", "getUniNViewModules", null});
        if (PdrUtil.isEmpty(str)) {
            return "";
        }
        return "window.__NATIVE_PLUGINS__ = " + str + ";window.__NATIVE_PLUGINS_REGISTERED__ = true;";
    }

    private String a() {
        StringBuffer stringBuffer = new StringBuffer(";Object.defineProperty(plus.screen,\"resolutionHeight\",{get:function(){var e=window,l=e.__html5plus__&&e.__html5plus__.isReady?e.__html5plus__:n.plus&&n.plus.isReady?n.plus:window.plus;return l.bridge.execSync(\"Device\",\"s.resolutionHeight\",[])}}),Object.defineProperty(plus.screen,\"resolutionWidth\",{get:function(){var e=window,l=e.__html5plus__&&e.__html5plus__.isReady?e.__html5plus__:n.plus&&n.plus.isReady?n.plus:window.plus;return l.bridge.execSync(\"Device\",\"s.resolutionWidth\",[])}}),Object.defineProperty(plus.display,\"resolutionHeight\",{get:function(){var e=window,l=e.__html5plus__&&e.__html5plus__.isReady?e.__html5plus__:n.plus&&n.plus.isReady?n.plus:window.plus;return l.bridge.execSync(\"Device\",\"d.resolutionHeight\",[])}}),Object.defineProperty(plus.display,\"resolutionWidth\",{get:function(){var e=window,l=e.__html5plus__&&e.__html5plus__.isReady?e.__html5plus__:n.plus&&n.plus.isReady?n.plus:window.plus;return l.bridge.execSync(\"Device\",\"d.resolutionWidth\",[])}});;plus.webview.__test__('save');");
        stringBuffer.append("plus.webview.__test__('update');");
        return stringBuffer.toString();
    }

    private String a(Object[] objArr) {
        IWebview iWebview = (IWebview) objArr[0];
        String lowerCase = String.valueOf(objArr[1]).toLowerCase(Locale.ENGLISH);
        String strValueOf = String.valueOf(objArr[2]);
        JSONArray jSONArray = (JSONArray) objArr[3];
        String str = null;
        strArrJsonArrayToStringArr = null;
        String[] strArrJsonArrayToStringArr = null;
        str = null;
        str = null;
        str = null;
        if (iWebview != null && iWebview.obtainApp() != null && iWebview.obtainFrameView() != null && iWebview.obtainFrameView().obtainWebView() != null) {
            IFeature iFeatureB = b(lowerCase, iWebview.getActivity());
            if (iFeatureB != null) {
                if (iFeatureB instanceof BaseFeature) {
                    BaseFeature baseFeature = (BaseFeature) iFeatureB;
                    if (!baseFeature.isOldMode()) {
                        return baseFeature.execute(iWebview, strValueOf, jSONArray);
                    }
                }
                if (jSONArray != null) {
                    try {
                        strArrJsonArrayToStringArr = JSUtil.jsonArrayToStringArr(jSONArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return iFeatureB.execute(iWebview, strValueOf, strArrJsonArrayToStringArr);
            }
            str = "";
            if (iWebview.obtainFrameView().getFrameType() != 3 && !PermissionControler.checkSafePermission(iWebview.obtainApp().obtainAppId(), lowerCase)) {
                Logger.e("featuremgr", "not found " + lowerCase + " feature plugin ; action=" + strValueOf + ";" + StringUtil.format(getContext().getString(R.string.dcloud_feature_error_tips), lowerCase));
                a(iWebview, lowerCase);
            }
        }
        return str;
    }

    private void a(Iterator it) {
        if (SDK.isUniMPSDK()) {
            return;
        }
        while (it.hasNext()) {
            String strValueOf = String.valueOf(PlatformUtil.invokeMethod((String) it.next(), "getJsContent"));
            if (!PdrUtil.isEmpty(strValueOf)) {
                this.g.add(strValueOf);
            }
        }
    }

    public void a(String str) {
        String str2;
        Collection collectionValues;
        HashMap map = this.c;
        if (map != null && (collectionValues = map.values()) != null) {
            Iterator it = collectionValues.iterator();
            while (it.hasNext()) {
                try {
                    ((IFeature) it.next()).dispose(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        HashMap map2 = this.h;
        if (map2 != null) {
            Iterator it2 = map2.keySet().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    str2 = null;
                    break;
                }
                str2 = (String) it2.next();
                if (str2.startsWith(str + "_")) {
                    break;
                }
            }
            if (TextUtils.isEmpty(str2)) {
                return;
            }
            this.h.remove(str2);
        }
    }

    public void a(IWebview iWebview, String str) {
        Dialog lossDialog = ErrorDialogUtil.getLossDialog(iWebview, StringUtil.format(iWebview.getContext().getString(R.string.dcloud_feature_error_tips2) + "https://ask.dcloud.net.cn/article/283", str), "https://ask.dcloud.net.cn/article/283", str);
        if (lossDialog != null) {
            lossDialog.show();
        }
    }
}
