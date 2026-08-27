package io.dcloud.weex;

import android.text.TextUtils;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.util.AppConsoleLogUtil;
import io.dcloud.feature.uniapp.utils.AbsLogLevel;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class ConsoleLogUtils {
    private static boolean checkLog(String str) {
        if (str.contains("Start windmill weex-vue-plugin")) {
            return false;
        }
        if (!str.contains("has been registered already!")) {
            return true;
        }
        Logger.i(str);
        return false;
    }

    public static void consoleLog(String str, String str2, AbsLogLevel absLogLevel) {
        String strReplace;
        String str3;
        if (!TextUtils.isEmpty(str) && "jsLog".equals(str) && checkLog(str2)) {
            if (str2.endsWith("__ERROR")) {
                strReplace = str2.replace("__ERROR", "");
                str3 = "ERROR";
            } else if (str2.endsWith("__LOG")) {
                strReplace = str2.replace("__LOG", "");
                str3 = "LOG";
            } else if (str2.endsWith("__INFO")) {
                strReplace = str2.replace("__INFO", "");
                str3 = "INFO";
            } else if (str2.endsWith("__WARN")) {
                strReplace = str2.replace("__WARN", "");
                str3 = "WARN";
            } else {
                strReplace = str2.replace("__DEBUG", "");
                str3 = "DEBUG";
            }
            strReplace.getClass();
            if (strReplace.startsWith("v8performance")) {
                WXDotDataUtil.setValue("initJSEngineTime", strReplace.replace("v8performance:", ""));
            } else if (strReplace.startsWith("JSCPerformance")) {
                WXDotDataUtil.setValue("initJSEngineTime", strReplace.replace("JSCPerformance:", ""));
            } else {
                AppConsoleLogUtil.DCLog(strReplace, str3);
            }
        }
    }
}
