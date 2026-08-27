package io.dcloud.feature.internal.sdk;

import android.content.Context;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.ICore;
import io.dcloud.common.DHInterface.IEventCallback;
import io.dcloud.common.DHInterface.IFrameView;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaFrameItem;
import io.dcloud.common.ui.PrivacyManager;
import io.dcloud.common.util.PdrUtil;
import java.util.ArrayList;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class SDK {
    public static final String ANDROID_ASSET = "file:///android_asset/";
    public static final String DEFAULT_APPID = "Default_Appid";
    public static final String UNIMP_CAPSULE_BUTTON_CLICK = "unimp_capsule_button_click";
    public static final String UNIMP_ERROR_KEY = "UniMP_Error";
    public static final String UNIMP_EVENT_CALLBACKID = "callbackId";
    public static final String UNIMP_EVENT_CALL_INSTANCEID = "instanceId";
    public static final String UNIMP_JS_TO_NATIVE = "unimp_js_to_native";
    public static final String UNIMP_OPEN = "open_unimp";
    public static final int UNI_CODE_ERROR_APPID = -1003;
    public static final int UNI_CODE_ERROR_NOT_RES = -1001;
    public static final int UNI_CODE_ERROR_NO_V3 = -1002;
    public static String customOAID = "";
    public static String hostAppThemeDark = "";
    public static boolean isCapsule = false;
    public static boolean isEnableBackground = false;
    public static boolean isNJS = true;
    public static boolean isUniMP = false;
    public static String mHostInfo = null;
    static ICore sCore = null;
    public static String sDefaultMenuButton = null;
    public static boolean uniMPSilentMode = false;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public enum IntegratedMode {
        WEBVIEW,
        WEBAPP,
        RUNTIME
    }

    private SDK() {
    }

    public static void closeWebView(IWebview iWebview) {
        if (isUniMPSDK()) {
            return;
        }
        ((AdaFrameItem) iWebview.obtainFrameView()).getAnimOptions().mOption = (byte) 1;
        sCore.dispatchEvent(IMgr.MgrType.WindowMgr, 2, iWebview.obtainFrameView());
    }

    private static IWebview createWebView(IApp iApp, String str, JSONObject jSONObject, IFrameView iFrameView, IEventCallback iEventCallback) {
        if (isUniMPSDK()) {
            return null;
        }
        IWebview iWebviewObtainWebView = ((IFrameView) sCore.dispatchEvent(IMgr.MgrType.WindowMgr, 3, new Object[]{0, iApp, new Object[]{str, jSONObject}, iFrameView, iEventCallback})).obtainWebView();
        iWebviewObtainWebView.loadUrl(str);
        return iWebviewObtainWebView;
    }

    public static void initSDK(ICore iCore) {
        sCore = iCore;
    }

    public static boolean isAgreePrivacy(Context context) {
        if (context == null) {
            return false;
        }
        return !PrivacyManager.getInstance().isNotPrivacyAllRight(context);
    }

    public static boolean isUniMPSDK() {
        if (PdrUtil.isUniMPHostForUniApp()) {
            return isUniMP;
        }
        return false;
    }

    public static ArrayList<IWebview> obtainAllIWebview(String str) {
        ArrayList arrayList = (ArrayList) sCore.dispatchEvent(IMgr.MgrType.WindowMgr, 6, str);
        if (arrayList == null || arrayList.size() <= 0) {
            return null;
        }
        ArrayList<IWebview> arrayList2 = new ArrayList<>();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            arrayList2.add(((IFrameView) obj).obtainWebView());
        }
        return arrayList2;
    }

    public static IApp obtainCurrentApp() {
        if (isUniMPSDK()) {
            return null;
        }
        return (IApp) sCore.dispatchEvent(IMgr.MgrType.AppMgr, 6, obtainCurrentRunnbingAppId());
    }

    public static String obtainCurrentRunnbingAppId() {
        if (isUniMPSDK()) {
            return null;
        }
        return String.valueOf(sCore.dispatchEvent(IMgr.MgrType.AppMgr, 11, null));
    }

    public static IWebview obtainWebview(String str, String str2) {
        ArrayList<IWebview> arrayListObtainAllIWebview = obtainAllIWebview(str);
        int size = arrayListObtainAllIWebview.size();
        int i = 0;
        while (i < size) {
            IWebview iWebview = arrayListObtainAllIWebview.get(i);
            i++;
            IWebview iWebview2 = iWebview;
            if (PdrUtil.isEquals(str2, iWebview2.getWebviewUUID())) {
                return iWebview2;
            }
        }
        return null;
    }

    public static void popWebView(IWebview iWebview) {
        if (isUniMPSDK()) {
            return;
        }
        ((AdaFrameItem) iWebview.obtainFrameView()).getAnimOptions().mOption = (byte) 1;
        sCore.dispatchEvent(IMgr.MgrType.WindowMgr, 21, iWebview.obtainFrameView());
    }

    public static void registerJsApi(String str, String str2, String str3) {
        if (isUniMPSDK()) {
            return;
        }
        sCore.dispatchEvent(IMgr.MgrType.FeatureMgr, 5, new String[]{str, str2, str3});
    }

    public static void requestAllFeature() {
        if (isUniMPSDK()) {
            return;
        }
        sCore.dispatchEvent(IMgr.MgrType.FeatureMgr, 7, null);
    }

    public static void requestFeature(String str, String str2, boolean z) {
        if (isUniMPSDK()) {
            return;
        }
        sCore.dispatchEvent(IMgr.MgrType.FeatureMgr, 6, new String[]{str, str2, String.valueOf(z)});
    }

    public static void setAgreePrivacy(Context context, boolean z) {
        if (context == null) {
            return;
        }
        PrivacyManager.getInstance().setAgreePrivacy(context, z);
    }

    public static void setUniMPSilentMode(boolean z) {
        uniMPSilentMode = z;
    }

    public static ArrayList<IWebview> obtainAllIWebview() {
        if (isUniMPSDK()) {
            return null;
        }
        return obtainAllIWebview(obtainCurrentRunnbingAppId());
    }
}
