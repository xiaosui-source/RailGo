package io.dcloud.common.util;

import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaUniWebView;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class Deprecated_JSUtil {
    Deprecated_JSUtil() {
    }

    @Deprecated
    public static void excCallbackError(IWebview iWebview, String str, String str2) {
        excCallbackError(iWebview, str, str2, false);
    }

    @Deprecated
    public static void excCallbackSuccess(IWebview iWebview, String str, String str2) {
        excCallbackSuccess(iWebview, str, str2, false);
    }

    @Deprecated
    public static void execCallback(IWebview iWebview, String str, String str2, int i, boolean z, boolean z2) {
        String string;
        if (iWebview != null) {
            if (iWebview instanceof AdaUniWebView) {
                StringBuilder sb = new StringBuilder("var result= {};result.status = %d;result.message = ");
                sb.append(z ? "%s" : "'%s'");
                sb.append(";result.keepCallback = ");
                sb.append(z2);
                sb.append(";plus.bridge.callbackFromNative('%s', result);");
                string = sb.toString();
            } else {
                StringBuilder sb2 = new StringBuilder("(function(w,n){try{var plus=((w.__html5plus__&&w.__html5plus__.isReady)?w.__html5plus__:(n.plus&&n.plus.isReady)?n.plus:window.plus);var result= {};result.status = %d;result.message = ");
                sb2.append(z ? "%s" : "'%s'");
                sb2.append(";result.keepCallback = ");
                sb2.append(z2);
                sb2.append(";plus && plus.bridge.callbackFromNative('%s', result);}catch(e){console.error(e.stack)};})(window,navigator);");
                string = sb2.toString();
            }
            iWebview.executeScript(StringUtil.format(string, Integer.valueOf(i), str2, str));
        }
    }

    @Deprecated
    public static String wrapJsVar(String str, boolean z) {
        StringBuffer stringBuffer = new StringBuffer("(function(){return ");
        if (z) {
            stringBuffer.append("'").append(str).append("';");
        } else {
            stringBuffer.append(str).append(";");
        }
        stringBuffer.append("})()");
        return stringBuffer.toString();
    }

    @Deprecated
    public static void excCallbackError(IWebview iWebview, String str, String str2, boolean z) {
        execCallback(iWebview, str, str2, JSUtil.ERROR, z, false);
    }

    @Deprecated
    public static void excCallbackSuccess(IWebview iWebview, String str, String str2, boolean z) {
        excCallbackSuccess(iWebview, str, str2, z, false);
    }

    @Deprecated
    public static void excDownloadCallBack(IWebview iWebview, String str, String str2) {
        iWebview.executeScript(StringUtil.format(iWebview instanceof AdaUniWebView ? "plus.downloader.__handlerEvt__('%s', %s);" : "((window.__html5plus__&&__html5plus__.isReady)?__html5plus__:(navigator.plus&&navigator.plus.isReady)?navigator.plus:window.plus).downloader.__handlerEvt__('%s', %s);", str2, str));
    }

    @Deprecated
    public static void excUploadCallBack(IWebview iWebview, String str, String str2) {
        iWebview.executeScript(StringUtil.format(iWebview instanceof AdaUniWebView ? "plus.uploader.__handlerEvt__('%s', %s);" : "((window.__html5plus__&&__html5plus__.isReady)?__html5plus__:(navigator.plus&&navigator.plus.isReady)?navigator.plus:window.plus).uploader.__handlerEvt__('%s', %s);", str2, str));
    }

    @Deprecated
    public static void excCallbackSuccess(IWebview iWebview, String str, String str2, boolean z, boolean z2) {
        execCallback(iWebview, str, str2, 1, z, z2);
    }
}
