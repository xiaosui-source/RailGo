package com.taobao.weex.utils;

import android.os.Build;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class OsVersion {
    private static boolean sIsAtLeastJB_MR2;

    static {
        sIsAtLeastJB_MR2 = getApiVersion() >= 18;
    }

    public static int getApiVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static boolean isAtLeastJB_MR2() {
        return sIsAtLeastJB_MR2;
    }
}
