package io.dcloud.sdk.core.util;

import android.content.Context;
import android.util.Base64;
import io.dcloud.p.d;
import io.dcloud.p.d4;
import io.dcloud.p.f5;
import io.dcloud.p.k3;
import io.dcloud.p.l0;
import io.dcloud.p.l3;
import io.dcloud.p.t4;
import io.dcloud.p.z2;
import java.util.HashMap;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class RequestUtils {
    public static String get(Context context, String str) {
        return d4.a(context, "dcloud-ads", str);
    }

    public static String getDid(Context context) {
        return t4.a(context, true, true);
    }

    public static String getOid(Context context) {
        return l3.a().a(context);
    }

    public static String getPost(String str, String str2, String str3) {
        return Base64.encodeToString(d.b(f5.a(str), str2, str3), 2);
    }

    public static byte[] httpGet(String str, HashMap<String, String> map) {
        return k3.a(str, (HashMap) map, true);
    }

    public static byte[] httpPost(String str, String str2, HashMap<String, String> map) {
        return k3.a(str, str2, map);
    }

    public static String mc(Context context) {
        return l0.a(context);
    }

    public static void postMessage(Runnable runnable) {
        z2.a().post(runnable);
    }

    public static void save(Context context, String str, String str2) {
        d4.a(context, "dcloud-ads", str, str2);
    }
}
