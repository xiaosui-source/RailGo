package io.dcloud.feature.weex.adapter;

import android.net.Uri;
import android.text.TextUtils;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.adapter.URIAdapter;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.feature.uniapp.adapter.AbsURIAdapter;
import io.dcloud.feature.weex.WeexInstanceMgr;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class PlusUriAdapter implements URIAdapter {
    private String standardizedURL(String str, String str2) {
        if (str2.startsWith("./")) {
            str2 = str2.substring(2);
            int iLastIndexOf = str.lastIndexOf(47);
            if (iLastIndexOf >= 0) {
                return str.substring(0, iLastIndexOf + 1) + str2;
            }
        }
        int iIndexOf = str2.indexOf("../");
        int iLastIndexOf2 = str.lastIndexOf(47);
        if (iLastIndexOf2 <= -1) {
            return str2;
        }
        String strSubstring = str.substring(0, iLastIndexOf2);
        while (iIndexOf > -1) {
            str2 = str2.substring(3);
            strSubstring = strSubstring.substring(0, strSubstring.lastIndexOf(47));
            iIndexOf = str2.indexOf("../");
        }
        return strSubstring + '/' + str2;
    }

    @Override // io.dcloud.feature.uniapp.adapter.AbsURIAdapter
    public Uri rewrite(WXSDKInstance wXSDKInstance, String str, Uri uri) {
        String strStandardizedURL;
        if (!TextUtils.isEmpty(wXSDKInstance.getBundleUrl()) && !PdrUtil.isEmpty(uri)) {
            String bundleUrl = wXSDKInstance.getBundleUrl();
            String string = uri.toString();
            if (uri.isRelative()) {
                if (uri.getEncodedPath().length() == 0) {
                    return Uri.parse(bundleUrl);
                }
                IWebview iWebviewFindWebview = WeexInstanceMgr.self().findWebview(wXSDKInstance);
                if (iWebviewFindWebview != null) {
                    byte bObtainRunningAppMode = iWebviewFindWebview.obtainApp().obtainRunningAppMode();
                    if (string.startsWith("/storage") || bObtainRunningAppMode != 1) {
                        strStandardizedURL = iWebviewFindWebview.obtainApp().convert2WebviewFullPath(bundleUrl, string);
                    } else {
                        String strConvert2AbsFullPath = iWebviewFindWebview.obtainApp().convert2AbsFullPath(bundleUrl, string);
                        if ("web".equals(str)) {
                            strConvert2AbsFullPath = iWebviewFindWebview.obtainApp().convert2WebviewFullPath(bundleUrl, string);
                        }
                        if (strConvert2AbsFullPath.startsWith("/storage/")) {
                            strStandardizedURL = iWebviewFindWebview.obtainApp().convert2WebviewFullPath(bundleUrl, string);
                        } else {
                            if (strConvert2AbsFullPath.startsWith("/")) {
                                strConvert2AbsFullPath = strConvert2AbsFullPath.substring(1, strConvert2AbsFullPath.length());
                            }
                            if (str.equals(AbsURIAdapter.FONT)) {
                                strStandardizedURL = "local:///" + strConvert2AbsFullPath;
                            } else {
                                strStandardizedURL = "asset:///" + strConvert2AbsFullPath;
                            }
                        }
                    }
                } else {
                    strStandardizedURL = standardizedURL(bundleUrl, string);
                }
                if (bundleUrl.startsWith("/storage")) {
                    strStandardizedURL = DeviceInfo.FILE_PROTOCOL + strStandardizedURL;
                } else if (bundleUrl.startsWith("storage")) {
                    strStandardizedURL = "file:///" + strStandardizedURL;
                }
                return Uri.parse(strStandardizedURL);
            }
        }
        return uri;
    }

    @Override // io.dcloud.feature.uniapp.adapter.AbsURIAdapter
    public Uri rewrite(String str, String str2, Uri uri) {
        String string = uri.toString();
        if (!uri.isRelative()) {
            return uri;
        }
        if (uri.getEncodedPath().length() == 0) {
            return Uri.parse(str);
        }
        return Uri.parse(standardizedURL(str, string));
    }
}
