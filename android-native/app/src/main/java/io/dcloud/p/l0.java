package io.dcloud.p;

import android.content.Context;
import android.text.TextUtils;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.sdk.core.DCloudAOLManager;
import java.lang.reflect.Field;
import java.util.Locale;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class l0 {
    public static Object a() throws NoSuchFieldException, SecurityException {
        try {
            String str = BaseInfo.sGlobalUserAgent;
            Field declaredField = BaseInfo.class.getDeclaredField("sBaseVersion");
            declaredField.setAccessible(true);
            return declaredField.get(BaseInfo.class);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException unused) {
            return DCloudAOLManager.getVersion();
        }
    }

    public static int c() {
        return 0;
    }

    public static String b() {
        try {
            return Locale.forLanguageTag((String) Class.forName("android.os.SystemProperties").getMethod("get", String.class).invoke(null, "persist.sys.locale")).getCountry();
        } catch (Exception unused) {
            return "";
        }
    }

    public static String a(Context context) {
        String string;
        String packageName = context.getPackageName();
        try {
            string = context.getPackageManager().getApplicationInfo(packageName, 128).metaData.getString("DCLOUD_STREAMAPP_CHANNEL");
        } catch (Exception unused) {
            string = null;
        }
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        return packageName + "|" + r0.d().b().getAppId() + "|" + r0.d().b().getAdId();
    }
}
