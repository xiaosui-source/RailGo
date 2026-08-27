package io.dcloud.p;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import com.taobao.weex.WXEnvironment;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class o4 {
    public static int a(Context context) throws IllegalAccessException, InstantiationException, ClassNotFoundException, NumberFormatException {
        try {
            if (Build.VERSION.SDK_INT < 29) {
                Class<?> cls = Class.forName("com.android.internal.R$dimen");
                return context.getResources().getDimensionPixelSize(Integer.parseInt(cls.getField("status_bar_height").get(cls.newInstance()).toString()));
            }
            Resources resources = context.getResources();
            int identifier = resources.getIdentifier("status_bar_height", "dimen", WXEnvironment.OS);
            if (identifier > 0) {
                return resources.getDimensionPixelSize(identifier);
            }
            return -1;
        } catch (Exception unused) {
            return -1;
        }
    }
}
