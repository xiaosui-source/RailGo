package com.taobao.weex.utils;

import android.content.Context;
import android.os.Build;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXDeviceUtils {
    public static boolean isAutoResize(Context context) {
        if (context == null) {
            return false;
        }
        return isMateX(context) || isGalaxyFold(context);
    }

    public static boolean isGalaxyFold(Context context) {
        return "samsung".equalsIgnoreCase(Build.BRAND) && "SM-F9000".equalsIgnoreCase(Build.MODEL);
    }

    public static boolean isMateX(Context context) {
        if (!"HUAWEI".equalsIgnoreCase(Build.BRAND)) {
            return false;
        }
        String str = Build.DEVICE;
        return "unknownRLI".equalsIgnoreCase(str) || "HWTAH".equalsIgnoreCase(str);
    }
}
