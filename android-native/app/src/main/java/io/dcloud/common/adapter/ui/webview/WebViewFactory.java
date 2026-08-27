package io.dcloud.common.adapter.ui.webview;

import android.app.Activity;
import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.alibaba.android.bindingx.core.internal.BindingXConstants;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.performance.WXInstanceApm;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.common.DHInterface.IDCloudWebviewClientListener;
import io.dcloud.common.DHInterface.IWebViewFactory;
import io.dcloud.common.DHInterface.IWebViewInstallListener;
import io.dcloud.common.adapter.ui.AdaWebview;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.MessageHandler;
import io.dcloud.common.adapter.util.PermissionUtil;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.p.d1;
import java.util.ArrayList;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class WebViewFactory {
    public static boolean isAllowFileAccessFromFileURLs;
    private static ICallBack otherCallBack;
    private static Runnable otherDelyedRunnable;
    private static IWebViewFactory sOtherWebViewFactory;
    private static IWebViewInstallListener webViewInstallListener;
    public static ArrayList<PerWrapper> sUsePermissionWebviews = new ArrayList<>();
    public static PermissionUtil.StreamPermissionRequest sStreamPermissionRequest = null;
    private static boolean isOther = false;
    private static boolean isSysWebViewCreate = false;
    private static boolean isOtherInitSuccess = false;
    private static boolean isOtherInitialised = false;
    private static boolean isLoadOtherTimeOut = false;

    static {
        if (BaseInfo.SyncDebug) {
            WebView.enableSlowWholeDocumentDraw();
        }
        otherDelyedRunnable = new Runnable() { // from class: io.dcloud.common.adapter.ui.webview.WebViewFactory.1
            @Override // java.lang.Runnable
            public void run() {
                boolean unused = WebViewFactory.isLoadOtherTimeOut = true;
                if (WebViewFactory.otherCallBack != null) {
                    WebViewFactory.otherCallBack.onCallBack(0, null);
                    ICallBack unused2 = WebViewFactory.otherCallBack = null;
                }
            }
        };
        isAllowFileAccessFromFileURLs = true;
    }

    private static IWebViewFactory getOtherWebViewFactory() {
        IWebViewFactory iWebViewFactory;
        if (!isIsOtherInitSuccess() || (iWebViewFactory = sOtherWebViewFactory) == null || isSysWebViewCreate) {
            return null;
        }
        return iWebViewFactory;
    }

    public static DCWebView getWebView(Activity activity, AdaWebview adaWebview) {
        DCWebView webView = getOtherWebViewFactory() != null ? getOtherWebViewFactory().getWebView(activity, adaWebview) : null;
        if (webView != null) {
            return webView;
        }
        isSysWebViewCreate = true;
        return new SysWebView(activity, adaWebview);
    }

    public static IWebViewInstallListener getWebViewInstallListener() {
        return webViewInstallListener;
    }

    public static String getWebViewUserAgentVersion(Context context) {
        return getWebViewUserAgentVersion(context, null);
    }

    public static void initOther(boolean z, long j) {
        isOther = z;
        if (j != 0) {
            MessageHandler.postDelayed(otherDelyedRunnable, j);
        }
    }

    public static boolean isIsLoadOtherTimeOut() {
        return isLoadOtherTimeOut;
    }

    public static boolean isIsOtherInitSuccess() {
        return isOtherInitSuccess;
    }

    public static boolean isOther() {
        return isOther;
    }

    public static boolean isOtherInitialised() {
        return isOtherInitialised;
    }

    public static void openJSEnabled(Object obj, IApp iApp) {
        Class[] clsArr = {Boolean.TYPE};
        Object[] objArr = {Boolean.TRUE};
        String strDecodeString = iApp != null ? iApp.getConfusionMgr().decodeString("e218Qml+aVtremF4fEtpZkd4bWZfYWZsZ397SX18Z2VpfGFraWRkcQ==") : d1.a("e218Qml+aVtremF4fEtpZkd4bWZfYWZsZ397SX18Z2VpfGFraWRkcQ==");
        String strDecodeString2 = iApp != null ? iApp.getConfusionMgr().decodeString("e218Qml+aVtremF4fE1maWpkbWw=") : d1.a("e218Qml+aVtremF4fE1maWpkbWw=");
        PlatformUtil.invokeMethod(obj, strDecodeString, clsArr, objArr);
        PlatformUtil.invokeMethod(obj, strDecodeString2, clsArr, objArr);
    }

    public static void removeDelayRunnable() {
        if (MessageHandler.hasCallbacks(otherDelyedRunnable)) {
            MessageHandler.removeCallbacks(otherDelyedRunnable);
        }
    }

    public static void resetSysWebViewState() {
        isSysWebViewCreate = false;
    }

    public static void resetUA() {
        BaseInfo.sDefWebViewUserAgent = "";
    }

    public static void setFileAccess(Object obj, IApp iApp, boolean z) {
        if (obj == null || DeviceInfo.sDeviceSdkVer <= 16) {
            return;
        }
        try {
            Class[] clsArr = {Boolean.TYPE};
            Object[] objArr = {Boolean.valueOf(z)};
            String strDecodeString = iApp != null ? iApp.getConfusionMgr().decodeString("eW9+S2ZmZX1fZGN8b3h5a2ZLaWlveXlMeGVnTGNmb19YRnkqNmEzZDg4ZmEtNGJhMC00NzlmLTk0MjItZTVhYWJlMTU4OTdiNzQ=", true, 10) : d1.a("eW9+S2ZmZX1fZGN8b3h5a2ZLaWlveXlMeGVnTGNmb19YRnkqNmEzZDg4ZmEtNGJhMC00NzlmLTk0MjItZTVhYWJlMTU4OTdiNzQ=", true, 10);
            String strDecodeString2 = iApp != null ? iApp.getConfusionMgr().decodeString("eG5/SmdnZHxNYmduSmhobnh4TXlkZk1iZ25eWUd4KjZhM2Q4OGZhLTRiYTAtNDc5Zi05NDIyLWU1YWFiZTE1ODk3Yjc1", true, 11) : d1.a("eG5/SmdnZHxNYmduSmhobnh4TXlkZk1iZ25eWUd4KjZhM2Q4OGZhLTRiYTAtNDc5Zi05NDIyLWU1YWFiZTE1ODk3Yjc1", true, 11);
            String strDecodeString3 = iApp != null ? iApp.getConfusionMgr().decodeString("f2l4TWBgY3tKZWBpTW9vaX9/KjZhM2Q4OGZhLTRiYTAtNDc5Zi05NDIyLWU1YWFiZTE1ODk3Yjc2", true, 12) : d1.a("f2l4TWBgY3tKZWBpTW9vaX9/KjZhM2Q4OGZhLTRiYTAtNDc5Zi05NDIyLWU1YWFiZTE1ODk3Yjc2", true, 12);
            if (isAllowFileAccessFromFileURLs || !z) {
                PlatformUtil.invokeMethod(obj, strDecodeString, clsArr, objArr);
                PlatformUtil.invokeMethod(obj, strDecodeString2, clsArr, objArr);
            }
            PlatformUtil.invokeMethod(obj, strDecodeString3, clsArr, objArr);
        } catch (Exception unused) {
        }
    }

    public static void setOtherCallBack(ICallBack iCallBack) {
        isLoadOtherTimeOut = false;
        otherCallBack = iCallBack;
    }

    public static void setOtherState(boolean z, IWebViewFactory iWebViewFactory) {
        isOtherInitSuccess = z;
        isOtherInitialised = true;
        if (otherCallBack == null) {
            if (z) {
                sOtherWebViewFactory = iWebViewFactory;
                return;
            }
            return;
        }
        MessageHandler.removeCallbacks(otherDelyedRunnable);
        Boolean bool = (Boolean) otherCallBack.onCallBack(z ? 1 : 0, iWebViewFactory);
        if (bool != null && !bool.booleanValue()) {
            isOtherInitSuccess = false;
        } else if (isOtherInitSuccess) {
            sOtherWebViewFactory = iWebViewFactory;
        }
    }

    public static void setSslHandlerState(Object obj, int i) {
        PlatformUtil.invokeMethod(obj, i != 1 ? i != 2 ? "" : BindingXConstants.STATE_CANCEL : "proceed", new Class[0], new Object[0]);
    }

    public static void setWebViewInstallListener(IWebViewInstallListener iWebViewInstallListener) {
        webViewInstallListener = iWebViewInstallListener;
    }

    public static boolean verifyVersion(String str, String str2) {
        String[] strArrSplit = str.split("\\.");
        String[] strArrSplit2 = str2.split("\\.");
        int i = 0;
        while (true) {
            if (i >= strArrSplit.length && i >= strArrSplit2.length) {
                return true;
            }
            int i2 = i < strArrSplit.length ? Integer.parseInt(strArrSplit[i]) : 0;
            int i3 = i < strArrSplit2.length ? Integer.parseInt(strArrSplit2[i]) : 0;
            if (i2 > i3) {
                return true;
            }
            if (i2 < i3) {
                return false;
            }
            i++;
        }
    }

    public static String getDefWebViewUA(Context context) {
        String defWebViewUA = getOtherWebViewFactory() != null ? getOtherWebViewFactory().getDefWebViewUA(context) : "";
        return PdrUtil.isEmpty(defWebViewUA) ? WebSettings.getDefaultUserAgent(context) : defWebViewUA;
    }

    public static String getWebViewUserAgentVersion(Context context, String str) {
        if (PdrUtil.isEmpty(str)) {
            if (PdrUtil.isEmpty(BaseInfo.sDefWebViewUserAgent)) {
                BaseInfo.sDefWebViewUserAgent = getDefWebViewUA(context);
            }
            str = BaseInfo.sDefWebViewUserAgent;
        }
        String[] strArrSplit = str.split(Operators.SPACE_STR);
        for (int length = strArrSplit.length - 1; length > 0; length--) {
            String str2 = strArrSplit[length];
            if (PdrUtil.isContains(str2.toLowerCase(), "chrome")) {
                String[] strArrSplit2 = str2.split("/");
                return strArrSplit2.length > 1 ? strArrSplit2[1] : WXInstanceApm.VALUE_ERROR_CODE_DEFAULT;
            }
        }
        return WXInstanceApm.VALUE_ERROR_CODE_DEFAULT;
    }

    public static DCWebView getWebView(Activity activity, AdaWebview adaWebview, IDCloudWebviewClientListener iDCloudWebviewClientListener) {
        DCWebView webView = getOtherWebViewFactory() != null ? getOtherWebViewFactory().getWebView(activity, adaWebview, iDCloudWebviewClientListener) : null;
        if (webView != null) {
            return webView;
        }
        isSysWebViewCreate = true;
        return new SysWebView(activity, adaWebview, iDCloudWebviewClientListener);
    }

    public static DCWebView getWebView(Activity activity, AdaWebview adaWebview, OnPageFinishedCallack onPageFinishedCallack) {
        DCWebView webView = getOtherWebViewFactory() != null ? getOtherWebViewFactory().getWebView(activity, adaWebview, onPageFinishedCallack) : null;
        if (webView != null) {
            return webView;
        }
        isSysWebViewCreate = true;
        return new SysWebView(activity, adaWebview, onPageFinishedCallack);
    }

    public static void setFileAccess(Object obj, boolean z) {
        if (obj != null) {
            setFileAccess(obj, null, z);
        }
    }
}
