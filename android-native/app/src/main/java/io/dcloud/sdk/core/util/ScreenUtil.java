package io.dcloud.sdk.core.util;

import android.content.Context;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class ScreenUtil {
    public static int dh(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int dw(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
