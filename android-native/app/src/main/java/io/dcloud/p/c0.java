package io.dcloud.p;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import com.facebook.common.callercontext.ContextChain;
import com.taobao.weex.common.WXConfig;
import com.taobao.weex.ui.component.WXBasicComponentType;
import io.dcloud.common.util.CreateShortResultReceiver;
import io.dcloud.common.util.net.NetWork;
import io.dcloud.p.p1;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class c0 {

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a extends ArrayList {
        a() {
            addAll(q1.a);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements p1.b {
        final /* synthetic */ String a;
        final /* synthetic */ HashMap b;
        final /* synthetic */ String[] c;

        b(String str, HashMap map, String[] strArr) {
            this.a = str;
            this.b = map;
            this.c = strArr;
        }

        @Override // io.dcloud.p.p1.b
        public void a(p1.a aVar) {
        }

        @Override // io.dcloud.p.p1.b
        public boolean b(p1.a aVar) {
            byte[] bArrA = k3.a(aVar.a(), this.a, this.b, this.c);
            if (bArrA == null) {
                return false;
            }
            String str = new String(bArrA, StandardCharsets.UTF_8);
            if (TextUtils.isEmpty(str)) {
                return true;
            }
            b3.c("uniAD-Commit_A", str);
            return true;
        }

        @Override // io.dcloud.p.p1.b
        public void onNoOnePicked() {
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c extends ArrayList {
        c() {
            addAll(q1.f);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class d implements p1.b {
        final /* synthetic */ String a;
        final /* synthetic */ HashMap b;

        d(String str, HashMap map) {
            this.a = str;
            this.b = map;
        }

        @Override // io.dcloud.p.p1.b
        public void a(p1.a aVar) {
        }

        @Override // io.dcloud.p.p1.b
        public boolean b(p1.a aVar) {
            byte[] bArrA = k3.a(aVar.a(), this.a, this.b);
            if (bArrA == null) {
                return false;
            }
            String str = new String(bArrA, StandardCharsets.UTF_8);
            if (TextUtils.isEmpty(str)) {
                return true;
            }
            b3.c("uniAD-Commit_F", str);
            return true;
        }

        @Override // io.dcloud.p.p1.b
        public void onNoOnePicked() {
        }
    }

    public static void a(Context context, String str, String str2, String str3, int i, String str4) {
        a(context, null, null, str2, str, str3, i, str4, null, null, null);
    }

    private static Map b(Context context, String str, String str2, int i, String str3, HashMap map) throws NoSuchMethodException, SecurityException, UnsupportedEncodingException {
        String strEncode;
        String str4;
        try {
            strEncode = URLEncoder.encode(Build.MODEL, "utf-8");
        } catch (UnsupportedEncodingException unused) {
            strEncode = "";
        }
        try {
            str4 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception unused2) {
            str4 = "";
        }
        String strA = t4.a(context, true, true);
        HashMap map2 = new HashMap();
        map2.put(ContextChain.TAG_PRODUCT, "a");
        map2.put("appid", str);
        map2.put(CreateShortResultReceiver.KEY_VERSIONNAME, str4);
        map2.put("at", Integer.valueOf(i));
        map2.put("psdk", Integer.valueOf(l0.c()));
        map2.put(WXConfig.os, Integer.valueOf(Build.VERSION.SDK_INT));
        map2.put("adpid", str3);
        if (strA.endsWith("&ie=1")) {
            strA = strA.replace("&ie=1", "");
            map2.put("ie", 1);
        } else if (strA.endsWith("&ie=0")) {
            strA = strA.replace("&ie=0", "");
            map2.put("ie", 0);
        }
        map2.put("imei", strA);
        map2.put("md", strEncode);
        map2.put("vd", Build.MANUFACTURER);
        map2.put("net", m4.d(context));
        map2.put("vb", l0.a());
        map2.put("t", Long.valueOf(System.currentTimeMillis()));
        map2.put("mc", l0.a(context));
        map2.put("paid", str2);
        map2.put("oaid", l3.a().a(context));
        if (map != null) {
            map2.putAll(map);
        }
        return map2;
    }

    public static void a(Context context, String str, String str2, String str3, String str4, String str5, int i, String str6, HashMap map, String str7, String str8) {
        a(context, str4, str3, str5, i, null, null, null, str, str2, str6, null, map, str7, str8, null, null);
    }

    public static void a(Context context, String str, String str2, String str3, String str4, String str5, int i, String str6, HashMap map, String str7, String str8, String str9, String str10) throws NoSuchMethodException, SecurityException, UnsupportedEncodingException {
        a(context, str4, str3, str5, i, null, null, null, str, str2, str6, null, map, str7, str8, str9, str10);
    }

    public static void a(Context context, String str, String str2, String str3, int i, String str4, String str5, JSONObject jSONObject, String str6, String str7, String str8, String str9, HashMap map, String str10, String str11, String str12, String str13) throws NoSuchMethodException, SecurityException, UnsupportedEncodingException {
        String strEncode;
        JSONObject jSONObjectOptJSONObject;
        Map mapB = b(context, str, str3, i, str8, map);
        if (str10 != null) {
            mapB.put("rid", str10);
        }
        if (str11 != null) {
            mapB.put("cpm", str11);
        }
        if (str6 != null) {
            mapB.put("mediaId", str6);
        }
        if (str12 != null) {
            mapB.put("csv", str12);
        }
        if (str13 != null) {
            mapB.put("cav", str13);
        }
        if (str7 != null) {
            mapB.put("slotId", str7);
        }
        if (i == 32) {
            mapB.put("dec", str4);
            mapB.put("dem", str5);
        }
        if (i == 41 && jSONObject != null && str6 == null) {
            if (jSONObject.has(WXBasicComponentType.IMG)) {
                mapB.put(WXBasicComponentType.IMG, c3.a(jSONObject.optString(WXBasicComponentType.IMG)).toLowerCase(Locale.ENGLISH));
            }
            if (jSONObject.has("dw")) {
                mapB.put("dw", jSONObject.optString("dw"));
            }
            if (jSONObject.has("dh")) {
                mapB.put("dh", jSONObject.optString("dh"));
            }
            if (jSONObject.has("click_coord") && (jSONObjectOptJSONObject = jSONObject.optJSONObject("click_coord")) != null) {
                mapB.put("click_coord", jSONObjectOptJSONObject.toString());
            }
        }
        mapB.put("tid", str2);
        HashMap map2 = new HashMap();
        map2.put(NetWork.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");
        try {
            strEncode = URLEncoder.encode(Base64.encodeToString(io.dcloud.p.d.b(f5.a(new JSONObject(mapB).toString()), e1.b(), e1.a()), 2), "utf-8");
        } catch (UnsupportedEncodingException unused) {
            strEncode = null;
        }
        p1.a().a(context, new a(), "CAA_" + i, new b("edata=" + strEncode, map2, new String[1]));
    }

    public static void a(Context context, String str, String str2, int i, String str3, HashMap map) throws UnsupportedEncodingException {
        String strEncode;
        try {
            strEncode = URLEncoder.encode(Base64.encodeToString(io.dcloud.p.d.b(f5.a(new JSONObject(b(context, str, str2, i, str3, map)).toString()), e1.b(), e1.a()), 2), "utf-8");
        } catch (UnsupportedEncodingException unused) {
            strEncode = null;
        }
        HashMap map2 = new HashMap();
        map2.put(NetWork.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");
        p1.a().a(context, new c(), "RSP", new d("edata=" + strEncode, map2));
    }

    public static void a(Context context, String str, String str2, String str3, String str4, String str5, String str6, String str7, int i, String str8) throws UnsupportedEncodingException {
        String strEncode;
        String strEncode2;
        String str9 = "";
        try {
            strEncode = URLEncoder.encode(Build.MODEL, "utf-8");
        } catch (UnsupportedEncodingException unused) {
            strEncode = "";
        }
        try {
            str9 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception unused2) {
        }
        HashMap map = new HashMap();
        map.put(ContextChain.TAG_PRODUCT, "a");
        map.put("appid", str);
        map.put(CreateShortResultReceiver.KEY_VERSIONNAME, str9);
        map.put("psdk", Integer.valueOf(l0.c()));
        map.put(WXConfig.os, Integer.valueOf(Build.VERSION.SDK_INT));
        map.put("adpid", str2);
        map.put("md", strEncode);
        map.put("vd", Build.MANUFACTURER);
        map.put("vb", l0.a());
        map.put("t", Long.valueOf(System.currentTimeMillis()));
        map.put("mc", l0.a(context));
        map.put("paid", str3);
        if (str4 != null) {
            map.put("mediaId", str4);
        }
        if (str5 != null) {
            map.put("slotId", str5);
        }
        if (str8 != null) {
            map.put("rid", str8);
            b3.b("uniad", str8);
        }
        map.put("gcpm", str6);
        map.put("currency", str7);
        map.put("pt", Integer.valueOf(i));
        try {
            strEncode2 = URLEncoder.encode(Base64.encodeToString(io.dcloud.p.d.b(f5.a(new JSONObject(map).toString()), e1.b(), e1.a()), 2), "utf-8");
        } catch (UnsupportedEncodingException unused3) {
            strEncode2 = null;
        }
        HashMap map2 = new HashMap();
        map2.put(NetWork.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");
        byte[] bArrA = k3.a(q1.g.a(), "edata=" + strEncode2, map2);
        if (bArrA != null) {
            String str10 = new String(bArrA, StandardCharsets.UTF_8);
            if (TextUtils.isEmpty(str10)) {
                return;
            }
            b3.c("uniAD-Commit_P", str10);
        }
    }
}
