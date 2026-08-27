package io.dcloud.common.adapter.ui.webview;

import android.content.Context;
import android.text.TextUtils;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.p.k;
import java.io.File;
import java.io.InputStream;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class WebResUtil {
    private static final String F_WT = "wap2app__template/";

    public static InputStream getEncryptionInputStream(String str, IApp iApp) {
        return iApp.getConfusionMgr().getEncryptionInputStream(str, iApp);
    }

    public static String getHBuilderPrintUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String str2 = BaseInfo.REL_PRIVATE_WWW_DIR + File.separator;
        return str.startsWith(str2) ? str.substring(str2.length()) : str;
    }

    public static String getOriginalUrl(String str) {
        return TextUtils.isEmpty(str) ? "" : str.startsWith(DeviceInfo.FILE_PROTOCOL) ? str.substring(7) : str;
    }

    public static String getRelPath(String str, IApp iApp) {
        String originalUrl = getOriginalUrl(str);
        int iIndexOf = originalUrl.indexOf(F_WT);
        return iIndexOf >= 0 ? originalUrl.substring(iIndexOf + 18) : getHBuilderPrintUrl(iApp.convert2RelPath(originalUrl));
    }

    public static String handleWap2appTemplateFilePath(String str) {
        return BaseInfo.sBaseWap2AppTemplatePath + F_WT + str;
    }

    public static boolean isWap2appTemplateFile(IApp iApp, String str) {
        return BaseInfo.containsInTemplate(iApp, str);
    }

    public static Object opAdData(Context context, String str, String str2, Object obj) {
        return k.a(context, str, str2, obj);
    }
}
