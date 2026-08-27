package io.dcloud.feature.ui.navigator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.Window;
import com.alibaba.fastjson.parser.JSONLexer;
import com.dcloud.android.widget.toast.ToastCompat;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IFeature;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.ISysEventListener;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.adapter.ui.webview.DCWebView;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.MessageHandler;
import io.dcloud.common.adapter.util.PermissionUtil;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.constant.IntentConst;
import io.dcloud.common.constant.StringConst;
import io.dcloud.common.ui.blur.DCBlurDraweeView;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.LoadAppUtils;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.ShortCutUtil;
import io.dcloud.common.util.ShortcutCreateUtil;
import io.dcloud.common.util.StringUtil;
import io.dcloud.common.util.TestUtil;
import io.dcloud.common.util.emulator.EmulatorCheckUtil;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class NavigatorUIFeatureImpl implements IFeature {
    AbsMgr a;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a extends PermissionUtil.StreamPermissionRequest {
        final /* synthetic */ IWebview a;
        final /* synthetic */ String[] b;
        final /* synthetic */ IApp c;
        final /* synthetic */ String d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(IApp iApp, IWebview iWebview, String[] strArr, IApp iApp2, String str) {
            super(iApp);
            this.a = iWebview;
            this.b = strArr;
            this.c = iApp2;
            this.d = str;
        }

        @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
        public void onDenied(String str) {
        }

        @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
        public void onGranted(String str) {
            NavigatorUIFeatureImpl.this.a(this.a, this.b, this.c, this.d);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements ISysEventListener {
        final /* synthetic */ int a;
        final /* synthetic */ IApp b;
        final /* synthetic */ IWebview c;
        final /* synthetic */ String d;
        final /* synthetic */ String e;

        b(int i, IApp iApp, IWebview iWebview, String str, String str2) {
            this.a = i;
            this.b = iApp;
            this.c = iWebview;
            this.d = str;
            this.e = str2;
        }

        @Override // io.dcloud.common.DHInterface.ISysEventListener
        public boolean onExecute(ISysEventListener.SysEventType sysEventType, Object obj) {
            Object[] objArr = (Object[]) obj;
            int iIntValue = ((Integer) objArr[0]).intValue();
            int[] iArr = (int[]) objArr[2];
            ISysEventListener.SysEventType sysEventType2 = ISysEventListener.SysEventType.onRequestPermissionsResult;
            if (sysEventType2 == sysEventType && iIntValue == this.a) {
                this.b.unregisterSysEventListener(this, sysEventType2);
                Deprecated_JSUtil.execCallback(this.c, this.e, StringUtil.format("{result:'%s'}", PermissionUtil.convert5PlusValue(iArr.length > 0 ? iArr[0] : this.c.obtainApp().checkSelfPermission(this.d, this.c.obtainApp().obtainAppName()))), JSUtil.OK, true, false);
            }
            return true;
        }
    }

    private void b(Context context, IWebview iWebview, String str, String str2) {
        String strRequestShortCut = ShortCutUtil.requestShortCut(context, str2);
        try {
            JSUtil.execCallback(iWebview, str, new JSONObject(ShortCutUtil.SHORT_CUT_EXISTING.equals(strRequestShortCut) ? StringUtil.format(DOMException.JSON_SHORTCUT_RESULT_INFO, "existing") : ShortCutUtil.SHORT_CUT_NONE.equals(strRequestShortCut) ? StringUtil.format(DOMException.JSON_SHORTCUT_RESULT_INFO, "none") : ShortCutUtil.NOPERMISSIONS.equals(strRequestShortCut) ? StringUtil.format(DOMException.JSON_SHORTCUT_RESULT_INFO, ShortCutUtil.NOPERMISSIONS) : StringUtil.format(DOMException.JSON_SHORTCUT_RESULT_INFO, "unknown")), JSUtil.OK, false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public void dispose(String str) {
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // io.dcloud.common.DHInterface.IFeature
    public String execute(IWebview iWebview, String str, String[] strArr) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, SecurityException, IOException, IllegalArgumentException, ParseException, InvocationTargetException {
        int iStringToColor;
        IApp iAppObtainApp = iWebview.obtainApp();
        String strObtainAppId = iAppObtainApp.obtainAppId();
        str.getClass();
        str.hashCode();
        char c2 = 65535;
        switch (str.hashCode()) {
            case -2079769446:
                if (str.equals("getOrientation")) {
                    c2 = 0;
                    break;
                }
                break;
            case -1980692731:
                if (str.equals("hideSystemNavigation")) {
                    c2 = 1;
                    break;
                }
                break;
            case -1921914628:
                if (str.equals("updateSplashscreen")) {
                    c2 = 2;
                    break;
                }
                break;
            case -1763010304:
                if (str.equals("hasShortcut")) {
                    c2 = 3;
                    break;
                }
                break;
            case -1294581845:
                if (str.equals("closeSplashscreen")) {
                    c2 = 4;
                    break;
                }
                break;
            case -1250806682:
                if (str.equals("getStatusBarStyle")) {
                    c2 = 5;
                    break;
                }
                break;
            case -1180327431:
                if (str.equals("isLogs")) {
                    c2 = 6;
                    break;
                }
                break;
            case -831443264:
                if (str.equals("showSystemNavigation")) {
                    c2 = 7;
                    break;
                }
                break;
            case -802912774:
                if (str.equals("isSimulator")) {
                    c2 = '\b';
                    break;
                }
                break;
            case -583672202:
                if (str.equals("removeSessionCookie")) {
                    c2 = '\t';
                    break;
                }
                break;
            case -452882469:
                if (str.equals("isImmersedStatusbar")) {
                    c2 = '\n';
                    break;
                }
                break;
            case -108255335:
                if (str.equals("getStatusBarBackground")) {
                    c2 = 11;
                    break;
                }
                break;
            case 126640486:
                if (str.equals("setCookie")) {
                    c2 = '\f';
                    break;
                }
                break;
            case 204345677:
                if (str.equals("hasSplashscreen")) {
                    c2 = '\r';
                    break;
                }
                break;
            case 301825860:
                if (str.equals("getUserAgent")) {
                    c2 = 14;
                    break;
                }
                break;
            case 341257562:
                if (str.equals("getCookie")) {
                    c2 = 15;
                    break;
                }
                break;
            case 580068706:
                if (str.equals("createShortcut")) {
                    c2 = 16;
                    break;
                }
                break;
            case 586449341:
                if (str.equals("setFullscreen")) {
                    c2 = 17;
                    break;
                }
                break;
            case 586897223:
                if (str.equals("getUiStyle")) {
                    c2 = 18;
                    break;
                }
                break;
            case 686218487:
                if (str.equals("checkPermission")) {
                    c2 = 19;
                    break;
                }
                break;
            case 746581438:
                if (str.equals("requestPermission")) {
                    c2 = 20;
                    break;
                }
                break;
            case 839078392:
                if (str.equals("isBackground")) {
                    c2 = 21;
                    break;
                }
                break;
            case 1063979522:
                if (str.equals("getSignature")) {
                    c2 = 22;
                    break;
                }
                break;
            case 1094478863:
                if (str.equals("hasNotchInScreen")) {
                    c2 = 23;
                    break;
                }
                break;
            case 1204872973:
                if (str.equals("setStatusBarBackground")) {
                    c2 = 24;
                    break;
                }
                break;
            case 1217359681:
                if (str.equals("removeAllCookie")) {
                    c2 = 25;
                    break;
                }
                break;
            case 1365206181:
                if (str.equals("isFullScreen")) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
            case 1841443122:
                if (str.equals("getStatusbarHeight")) {
                    c2 = 27;
                    break;
                }
                break;
            case 1850818488:
                if (str.equals("setUserAgent")) {
                    c2 = 28;
                    break;
                }
                break;
            case 1984754993:
                if (str.equals("setLogs")) {
                    c2 = 29;
                    break;
                }
                break;
            case 2104007794:
                if (str.equals("setStatusBarStyle")) {
                    c2 = 30;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                try {
                    int rotation = iWebview.getActivity().getWindowManager().getDefaultDisplay().getRotation();
                    return JSUtil.wrapJsVar(rotation != 1 ? rotation != 2 ? rotation != 3 ? 0 : -90 : 180 : 90);
                } catch (Exception unused) {
                    return JSUtil.wrapJsVar(0.0f);
                }
            case 1:
                Window window = iAppObtainApp.getActivity().getWindow();
                window.getDecorView().setSystemUiVisibility(window.getDecorView().getSystemUiVisibility() | 4866);
                iWebview.obtainApp().setHideNavBarState(true);
                return null;
            case 2:
                try {
                    JSONObject jSONObject = new JSONObject(strArr[0]);
                    SharedPreferences.Editor editorEdit = SP.getOrCreateBundle(iWebview.getContext(), "pdr").edit();
                    String strObtainAppId2 = iAppObtainApp.obtainAppId();
                    String strOptString = jSONObject.optString("image", null);
                    if (!TextUtils.isEmpty(strOptString)) {
                        String strConvert2AbsFullPath = iAppObtainApp.convert2AbsFullPath(iWebview.obtainFullUrl(), strOptString);
                        if (PdrUtil.isDeviceRootDir(strConvert2AbsFullPath)) {
                            DHFile.copyFile(strConvert2AbsFullPath, StringConst.STREAMAPP_KEY_ROOTPATH + "splash/" + iAppObtainApp.obtainAppId() + ".png", true, false);
                        }
                        editorEdit.putString(SP.UPDATE_SPLASH_IMG_PATH, strConvert2AbsFullPath);
                    }
                    if (!jSONObject.isNull(IApp.ConfigProperty.CONFIG_AUTOCLOSE)) {
                        editorEdit.putBoolean(strObtainAppId2 + SP.UPDATE_SPLASH_AUTOCLOSE, jSONObject.optBoolean(IApp.ConfigProperty.CONFIG_AUTOCLOSE));
                    }
                    if (!jSONObject.isNull(IApp.ConfigProperty.CONFIG_DELAY)) {
                        editorEdit.putInt(strObtainAppId2 + SP.UPDATE_SPLASH_DELAY, jSONObject.optInt(IApp.ConfigProperty.CONFIG_DELAY));
                    }
                    if (BaseInfo.isWap2AppAppid(strObtainAppId2)) {
                        if (!jSONObject.isNull(IApp.ConfigProperty.CONFIG_AUTOCLOSE_W2A)) {
                            editorEdit.putBoolean(strObtainAppId2 + SP.UPDATE_SPLASH_AUTOCLOSE_W2A, jSONObject.optBoolean(IApp.ConfigProperty.CONFIG_AUTOCLOSE_W2A));
                        }
                        if (!jSONObject.isNull(IApp.ConfigProperty.CONFIG_DELAY_W2A)) {
                            editorEdit.putInt(strObtainAppId2 + SP.UPDATE_SPLASH_DELAY_W2A, jSONObject.optInt(IApp.ConfigProperty.CONFIG_DELAY_W2A));
                        }
                    }
                    editorEdit.commit();
                    return null;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            case 3:
                AppRuntime.checkPrivacyComplianceAndPrompt(iWebview.getContext(), "Navigator-" + str);
                String str2 = strArr[0];
                String str3 = strArr[1];
                String strObtainAppName = iWebview.obtainApp().obtainAppName();
                try {
                    strObtainAppName = new JSONObject(str2).optString("name", strObtainAppName);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                b(iWebview.getContext(), iWebview, str3, strObtainAppName);
                return null;
            case 4:
                Logger.d(Logger.MAIN_TAG, "appid=" + strObtainAppId + " closeSplashscreen");
                TestUtil.print(TestUtil.START_STREAM_APP, "closeSplashscreen appid=" + strObtainAppId);
                Logger.i("download_manager", "javascript webapp task begin success appid=" + strObtainAppId + " closeSplashscreen");
                this.a.processEvent(IMgr.MgrType.WindowMgr, 11, iWebview.obtainFrameView());
                return null;
            case 5:
                return JSUtil.wrapJsVar(iAppObtainApp.obtainConfigProperty(AbsoluteConst.JSONKEY_STATUSBAR_MODE));
            case 6:
                return JSUtil.wrapJsVar(Logger.isOpen());
            case 7:
                Window window2 = iAppObtainApp.getActivity().getWindow();
                window2.getDecorView().setSystemUiVisibility(window2.getDecorView().getSystemUiVisibility() & (-515));
                iWebview.obtainApp().setHideNavBarState(false);
                return null;
            case '\b':
                AppRuntime.checkPrivacyComplianceAndPrompt(iWebview.getContext(), "Navigator-" + str);
                return JSUtil.wrapJsVar(EmulatorCheckUtil.getSingleInstance().emulatorCheck(iWebview.getContext()));
            case '\t':
                try {
                    iWebview.removeSessionCookie();
                    return null;
                } catch (Exception e3) {
                    e3.printStackTrace();
                    return null;
                }
            case '\n':
                return JSUtil.wrapJsVar(iAppObtainApp.obtainStatusBarMgr().checkImmersedStatusBar(iWebview.getActivity(), Boolean.valueOf(iAppObtainApp.obtainConfigProperty(AbsoluteConst.JSONKEY_STATUSBAR_IMMERSED)).booleanValue()));
            case 11:
                return JSUtil.wrapJsVar(PdrUtil.toHexFromColor(iAppObtainApp.getActivity().getWindow().getStatusBarColor()));
            case '\f':
                iWebview.setCookie(strArr[0], strArr[1]);
                return null;
            case '\r':
                return JSUtil.wrapJsVar(!iAppObtainApp.obtainWebAppRootView().didCloseSplash());
            case 14:
                boolean z = Boolean.parseBoolean(iAppObtainApp.obtainConfigProperty(IApp.ConfigProperty.CONFIG_funSetUA));
                String strObtainConfigProperty = iWebview.obtainApp().obtainConfigProperty(IApp.ConfigProperty.CONFIG_USER_AGENT);
                if (TextUtils.isEmpty(strObtainConfigProperty)) {
                    strObtainConfigProperty = "";
                }
                if (!z) {
                    boolean z2 = Boolean.parseBoolean(iAppObtainApp.obtainConfigProperty(IApp.ConfigProperty.CONFIG_CONCATENATE));
                    boolean z3 = Boolean.parseBoolean(iAppObtainApp.obtainConfigProperty(IApp.ConfigProperty.CONFIG_H5PLUS));
                    if (z2) {
                        String str4 = BaseInfo.sDefWebViewUserAgent + Operators.SPACE_STR + strObtainConfigProperty;
                        if (!z3) {
                            return str4;
                        }
                        return str4 + DCWebView.UserAgentExtInfo;
                    }
                }
                return strObtainConfigProperty;
            case 15:
                return iWebview.getCookie(strArr[0]);
            case 16:
                AppRuntime.checkPrivacyComplianceAndPrompt(iWebview.getContext(), "Navigator-" + str);
                PermissionUtil.usePermission(iWebview.getActivity(), IFeature.F_NAVIGATOR, "SHORTCUT", 2, new a(iAppObtainApp, iWebview, strArr, iAppObtainApp, strObtainAppId));
                return null;
            case 17:
                iAppObtainApp.setFullScreen(PdrUtil.parseBoolean(String.valueOf(strArr[0]), false, false));
                break;
            case 18:
                return JSUtil.wrapJsVar(AppRuntime.getAppDarkMode(iWebview.getContext()) ? DCBlurDraweeView.DARK : DCBlurDraweeView.LIGHT);
            case 19:
                return JSUtil.wrapJsVar(PermissionUtil.checkPermission(iWebview, strArr));
            case 20:
                String str5 = strArr[0];
                String str6 = strArr[1];
                int requestCode = PermissionUtil.getRequestCode();
                String strConvertNativePermission = PermissionUtil.convertNativePermission(str5);
                iAppObtainApp.registerSysEventListener(new b(requestCode, iAppObtainApp, iWebview, strConvertNativePermission, str6), ISysEventListener.SysEventType.onRequestPermissionsResult);
                iAppObtainApp.requestPermissions(new String[]{strConvertNativePermission}, requestCode);
                break;
            case 21:
                return JSUtil.wrapJsVar(iAppObtainApp.obtainAppStatus() == 2);
            case 22:
                return JSUtil.wrapJsVar(LoadAppUtils.getAppSignatureSHA1(iWebview.getContext()));
            case 23:
                return JSUtil.wrapJsVar(QueryNotchTool.hasNotchInScreen(iWebview.getActivity()));
            case 24:
                String str7 = strArr[0];
                if (!TextUtils.isEmpty(str7)) {
                    try {
                        iStringToColor = Color.parseColor(str7);
                    } catch (Exception unused2) {
                        iStringToColor = PdrUtil.stringToColor(str7);
                    }
                    iAppObtainApp.setConfigProperty(AbsoluteConst.JSONKEY_STATUSBAR_BC, strArr[0]);
                    iAppObtainApp.obtainStatusBarMgr().setStatusBarColor(iAppObtainApp.getActivity(), iStringToColor);
                    break;
                }
                break;
            case 25:
                try {
                    iWebview.removeAllCookie();
                    break;
                } catch (Exception e4) {
                    e4.printStackTrace();
                    break;
                }
            case 26:
                return JSUtil.wrapJsVar(iAppObtainApp.isFullScreen());
            case 27:
                DeviceInfo.updateStatusBarHeight(iWebview.getActivity());
                return JSUtil.wrapJsVar(DeviceInfo.sStatusBarHeight / iWebview.getScale());
            case 28:
                String str8 = strArr[0];
                String str9 = strArr[1];
                iAppObtainApp.setConfigProperty(IApp.ConfigProperty.CONFIG_USER_AGENT, str8);
                iAppObtainApp.setConfigProperty(IApp.ConfigProperty.CONFIG_funSetUA, AbsoluteConst.TRUE);
                iAppObtainApp.setConfigProperty(IApp.ConfigProperty.CONFIG_H5PLUS, str9);
                iWebview.setWebviewProperty(IWebview.USER_AGENT, str8);
                break;
            case 29:
                Logger.setOpen(PdrUtil.parseBoolean(String.valueOf(strArr[0]), false, false));
                break;
            case 30:
                String str10 = strArr[0];
                iAppObtainApp.setConfigProperty(AbsoluteConst.JSONKEY_STATUSBAR_MODE, str10);
                iAppObtainApp.obtainStatusBarMgr().setStatusBarMode(iAppObtainApp.getActivity(), str10);
                break;
        }
        return null;
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public void init(AbsMgr absMgr, String str) {
        this.a = absMgr;
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c implements Runnable {
        final /* synthetic */ Context a;
        final /* synthetic */ String b;
        final /* synthetic */ IWebview c;
        final /* synthetic */ String d;

        c(Context context, String str, IWebview iWebview, String str2) {
            this.a = context;
            this.b = str;
            this.c = iWebview;
            this.d = str2;
        }

        @Override // java.lang.Runnable
        public void run() {
            String str;
            if (!ShortCutUtil.SHORT_CUT_EXISTING.equals(ShortCutUtil.requestShortCutForCommit(this.a, this.b))) {
                str = AbsoluteConst.FALSE;
            } else {
                str = AbsoluteConst.TRUE;
            }
            try {
                JSUtil.execCallback(this.c, this.d, new JSONObject(StringUtil.format(DOMException.JSON_SHORTCUT_SUCCESS_INFO, str)), JSUtil.OK, false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00a5 A[Catch: Exception -> 0x00d1, TRY_LEAVE, TryCatch #6 {Exception -> 0x00d1, blocks: (B:28:0x009f, B:30:0x00a5, B:39:0x00c2, B:36:0x00bb, B:33:0x00b4), top: B:57:0x009f, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00c0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00b4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean a(io.dcloud.common.DHInterface.IWebview r16, java.lang.String[] r17, io.dcloud.common.DHInterface.IApp r18, java.lang.String r19) {
        /*
            Method dump skipped, instructions count: 221
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.ui.navigator.NavigatorUIFeatureImpl.a(io.dcloud.common.DHInterface.IWebview, java.lang.String[], io.dcloud.common.DHInterface.IApp, java.lang.String):boolean");
    }

    private String b(IApp iApp) {
        Intent intentObtainWebAppIntent = iApp.obtainWebAppIntent();
        return intentObtainWebAppIntent != null ? intentObtainWebAppIntent.getStringExtra(IntentConst.WEBAPP_ACTIVITY_APPICON) : "";
    }

    private void a(Context context, IWebview iWebview, String str, String str2) {
        MessageHandler.postDelayed(new c(context, str2, iWebview, str), Build.VERSION.SDK_INT >= 25 ? 1500 : 500);
    }

    private Bitmap a(IApp iApp) {
        String strB = b(iApp);
        if (strB != null) {
            return BitmapFactory.decodeFile(strB);
        }
        return null;
    }

    private void a(IWebview iWebview, String str, Bitmap bitmap, String str2, String str3, JSONObject jSONObject, boolean z, boolean z2, String str4) {
        String str5;
        Intent intentObtainWebAppIntent;
        IApp iAppObtainApp = iWebview.obtainApp();
        String strObtainAppId = iAppObtainApp.obtainAppId();
        Activity activity = iWebview.getActivity();
        SharedPreferences orCreateBundle = SP.getOrCreateBundle(iWebview.getContext(), "pdr");
        if (PdrUtil.isEmpty(str)) {
            str = iAppObtainApp.obtainAppName();
        }
        boolean z3 = orCreateBundle.getBoolean(strObtainAppId + SP.K_CREATED_SHORTCUT, false);
        if (TextUtils.isEmpty(str2) && (intentObtainWebAppIntent = iWebview.obtainApp().obtainWebAppIntent()) != null) {
            str2 = intentObtainWebAppIntent.getStringExtra(IntentConst.WEBAPP_SHORT_CUT_CLASS_NAME);
        }
        String str6 = str2;
        if (Build.VERSION.SDK_INT >= 25) {
            if (!ShortCutUtil.hasShortcut(activity, str) || z) {
                str5 = str;
                if (ShortCutUtil.createShortcutToDeskTop(activity, strObtainAppId, str5, bitmap, str6, jSONObject, true) && !TextUtils.isEmpty(str3)) {
                    ToastCompat.makeText(activity.getApplicationContext(), (CharSequence) str3, 1).show();
                }
            } else {
                str5 = str;
            }
        } else {
            str5 = str;
            if (ShortcutCreateUtil.isDuplicateLauncher(activity)) {
                if (ShortCutUtil.createShortcutToDeskTop(activity, strObtainAppId, str5, bitmap, str6, jSONObject, true) && !TextUtils.isEmpty(str3) && ShortcutCreateUtil.needToast(activity)) {
                    ToastCompat.makeText(activity.getApplicationContext(), (CharSequence) str3, 1).show();
                }
            } else if (!ShortCutUtil.hasShortcut(activity, str5)) {
                if (z) {
                    if (!TextUtils.isEmpty(str3) && ShortcutCreateUtil.needToast(activity)) {
                        ToastCompat.makeText(activity.getApplicationContext(), (CharSequence) str3, 1).show();
                    }
                    ShortCutUtil.createShortcutToDeskTop(activity, strObtainAppId, str5, bitmap, str6, jSONObject, true);
                } else {
                    if (z3) {
                        return;
                    }
                    if (ShortCutUtil.createShortcutToDeskTop(activity, strObtainAppId, str5, bitmap, str6, jSONObject, true) && !TextUtils.isEmpty(str3) && ShortcutCreateUtil.needToast(activity)) {
                        ToastCompat.makeText(activity.getApplicationContext(), (CharSequence) str3, 1).show();
                    }
                }
            }
        }
        a(iWebview.getContext(), iWebview, str4, str5);
    }
}
