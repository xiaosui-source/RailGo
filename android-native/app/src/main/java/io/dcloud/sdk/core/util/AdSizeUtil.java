package io.dcloud.sdk.core.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class AdSizeUtil {
    private static float a(Context context, int i) {
        return i / context.getResources().getDisplayMetrics().density;
    }

    public static float convertHeight(int i, Context context, boolean z) {
        if (i > 0) {
            return z ? a(context, i) : i;
        }
        return 0.0f;
    }

    public static float convertSize(int i, Context context, boolean z, boolean z2) {
        if (i > 0) {
            return z ? a(context, i) : i;
        }
        if (!z2) {
            return 0.0f;
        }
        int i2 = context.getResources().getDisplayMetrics().widthPixels;
        return z ? a(context, i2) : i2;
    }

    public static int pxFromDp(float f, DisplayMetrics displayMetrics) {
        return Math.round(TypedValue.applyDimension(1, f, displayMetrics));
    }
}
