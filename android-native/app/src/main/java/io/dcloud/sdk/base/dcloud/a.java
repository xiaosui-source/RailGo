package io.dcloud.sdk.base.dcloud;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.facebook.common.util.UriUtil;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.feature.uniapp.adapter.AbsURIAdapter;
import io.dcloud.p.k3;
import io.dcloud.p.u1;
import io.dcloud.p.w4;
import io.dcloud.sdk.base.dcloud.ADHandler;
import java.net.URISyntaxException;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
abstract class a extends ADHandler {

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    /* renamed from: io.dcloud.sdk.base.dcloud.a$a, reason: collision with other inner class name */
    class C0070a implements u1 {
        final /* synthetic */ ADHandler.e a;
        final /* synthetic */ Context b;
        final /* synthetic */ String c;

        C0070a(ADHandler.e eVar, Context context, String str) {
            this.a = eVar;
            this.b = context;
            this.c = str;
        }

        @Override // io.dcloud.p.u1
        public Object onCallBack(int i, Object obj) throws JSONException {
            JSONObject jSONObjectOptJSONObject = ((JSONObject) obj).optJSONObject("data");
            String strOptString = jSONObjectOptJSONObject.optString("dstlink");
            String strOptString2 = jSONObjectOptJSONObject.optString("clickid");
            String strOptString3 = this.a.b().optString("tid");
            try {
                this.a.c().put("click_id", strOptString2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Context context = this.b;
            ADHandler.e eVar = this.a;
            a.b(context, eVar, strOptString, eVar.h, strOptString3, this.c, eVar.c());
            if (this.a.d()) {
                return null;
            }
            io.dcloud.p.c.a(this.b);
            return null;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements Runnable {
        final /* synthetic */ ADHandler.e a;
        final /* synthetic */ Context b;
        final /* synthetic */ String c;

        b(ADHandler.e eVar, Context context, String str) {
            this.a = eVar;
            this.b = context;
            this.c = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            String strOptString = this.a.b().optString("url");
            String strOptString2 = this.a.b().optString("tid");
            Context context = this.b;
            ADHandler.e eVar = this.a;
            a.b(context, eVar, strOptString, eVar.h, strOptString2, this.c, eVar.c());
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c implements Runnable {
        final /* synthetic */ JSONArray a;
        final /* synthetic */ ADHandler.e b;
        final /* synthetic */ Context c;

        c(JSONArray jSONArray, ADHandler.e eVar, Context context) {
            this.a = jSONArray;
            this.b = eVar;
            this.c = context;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            if (this.a != null) {
                for (int i = 0; i < this.a.length(); i++) {
                    try {
                        JSONObject jSONObjectOptJSONObject = this.a.optJSONObject(i);
                        int iOptInt = jSONObjectOptJSONObject.optInt("template_type");
                        JSONObject jSONObjectC = this.b.c();
                        boolean z = jSONObjectC.has("ua") && jSONObjectC.optString("ua").equalsIgnoreCase("webview");
                        jSONObjectC.put("u-a", ADHandler.a("ua-webview"));
                        String strA = ADHandler.a(jSONObjectOptJSONObject.optString("url"), jSONObjectC);
                        int iOptInt2 = jSONObjectOptJSONObject.optInt("http_method");
                        String strOptString = jSONObjectOptJSONObject.optString(UriUtil.LOCAL_CONTENT_SCHEME);
                        if (iOptInt != 1) {
                            a.b(strA, strOptString, iOptInt2, 2, false, null, "clktrackers", z);
                        } else if (this.b.d()) {
                            io.dcloud.sdk.base.dcloud.c.a(this.c, strA);
                        } else {
                            io.dcloud.p.c.e(this.c, strA);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class d implements Runnable {
        final /* synthetic */ boolean a;
        final /* synthetic */ int b;
        final /* synthetic */ String c;
        final /* synthetic */ String d;
        final /* synthetic */ boolean e;
        final /* synthetic */ int f;
        final /* synthetic */ u1 g;

        d(boolean z, int i, String str, String str2, boolean z2, int i2, u1 u1Var) {
            this.a = z;
            this.b = i;
            this.c = str;
            this.d = str2;
            this.e = z2;
            this.f = i2;
            this.g = u1Var;
        }

        @Override // java.lang.Runnable
        public void run() {
            HashMap map;
            byte[] bArrA = null;
            if (this.a) {
                map = new HashMap();
                map.put(IWebview.USER_AGENT, ADHandler.a("ua-webview"));
            } else {
                map = null;
            }
            int i = this.b;
            if (i == 0) {
                try {
                    bArrA = k3.a(this.c, map, true);
                } catch (Exception unused) {
                }
            } else if (i == 1) {
                bArrA = k3.a(this.c, this.d, map);
            }
            if (!this.e || bArrA == null) {
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(new String(bArrA));
                if (jSONObject.optInt("ret") == 0) {
                    u1 u1Var = this.g;
                    if (u1Var != null) {
                        u1Var.onCallBack(1, jSONObject);
                        return;
                    }
                    return;
                }
                String strOptString = jSONObject.optString(NotificationCompat.CATEGORY_MESSAGE);
                a.b("handleTrackers_wanka Runnable Error url=" + this.c + ";msg=" + strOptString);
                int i2 = this.f;
                if (i2 > 0) {
                    a.b(this.c, strOptString, this.b, i2, this.e, this.g, null, this.a);
                }
                k3.a(this.c, map, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Context context, ADHandler.e eVar, String str, String str2, String str3, String str4, JSONObject jSONObject) {
    }

    static void d(Context context, ADHandler.e eVar, String str) throws JSONException, URISyntaxException {
        int iOptInt = eVar.b().optInt("template", 0);
        String strOptString = eVar.b().optString("action");
        if (iOptInt == 1) {
            if (eVar.d()) {
                return;
            }
            JSONArray jSONArrayOptJSONArray = eVar.e().optJSONArray("clktrackers");
            if (AbsoluteConst.SPNAME_DOWNLOAD.equals(strOptString)) {
                a(jSONArrayOptJSONArray, eVar.c(), new C0070a(eVar, context, str), "clktrackers");
                return;
            } else {
                if ("url".equals(strOptString)) {
                    a(context, eVar, jSONArrayOptJSONArray, eVar.h, str);
                    return;
                }
                return;
            }
        }
        JSONArray jSONArrayOptJSONArray2 = eVar.e().optJSONArray("clktrackers");
        if (jSONArrayOptJSONArray2 != null) {
            a(jSONArrayOptJSONArray2, eVar.c(), "clktrackers");
        }
        if (!AbsoluteConst.SPNAME_DOWNLOAD.equals(strOptString)) {
            ADHandler.b(context, eVar, str);
            return;
        }
        String strOptString2 = eVar.b().optString(AbsURIAdapter.BUNDLE);
        if (TextUtils.isEmpty(strOptString2) || !io.dcloud.p.c.a(context, strOptString2)) {
            w4.a().a(new b(eVar, context, str));
            if (eVar.d()) {
                return;
            }
            io.dcloud.p.c.a(context);
            return;
        }
        if (eVar.d()) {
            return;
        }
        try {
            Intent uri = Intent.parseUri(eVar.b().optString("dplk"), 1);
            uri.setFlags(268435456);
            uri.setSelector(null);
            uri.setComponent(null);
            uri.addCategory("android.intent.category.BROWSABLE");
            if (context.getPackageManager().resolveActivity(uri, 65536) != null) {
                context.startActivity(uri);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    static void e(Context context, ADHandler.e eVar, String str) throws JSONException {
        JSONArray jSONArrayOptJSONArray = eVar.e().optJSONArray("dplktrackers");
        if (jSONArrayOptJSONArray != null) {
            a(jSONArrayOptJSONArray, eVar.c(), "dplktrackers");
        }
    }

    static void f(Context context, ADHandler.e eVar, String str) throws JSONException {
        JSONObject jSONObjectE = eVar.e();
        if (jSONObjectE != null) {
            a(jSONObjectE.optJSONArray("imptrackers"), eVar.c(), "imptrackers");
        }
    }

    static void b(String str) {
        ADHandler.a("wanka", str);
    }

    private static void a(Context context, ADHandler.e eVar, JSONArray jSONArray, String str, String str2) {
        w4.a().a(new c(jSONArray, eVar, context));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(String str, String str2, int i, int i2, boolean z, u1 u1Var, String str3, boolean z2) {
        int i3 = i2 - 1;
        b("handleTrackers_wanka template = " + (z ? 1 : 0) + "; t_count=" + i3 + "; tagMsg " + str3 + ";  url=" + str);
        w4.a().a(new d(z2, i, str, str2, z, i3, u1Var));
    }

    private static void a(JSONArray jSONArray, JSONObject jSONObject, String str) throws JSONException {
        a(jSONArray, jSONObject, (u1) null, str);
    }

    private static void a(JSONArray jSONArray, JSONObject jSONObject, u1 u1Var, String str) throws JSONException {
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i);
            int iOptInt = jSONObjectOptJSONObject.optInt("template_type");
            boolean z = jSONObject.has("ua") && jSONObject.optString("ua").equalsIgnoreCase("webview");
            try {
                jSONObject.put("u-a", ADHandler.a("ua-webview"));
            } catch (JSONException unused) {
            }
            b(ADHandler.a(jSONObjectOptJSONObject.optString("url"), jSONObject), jSONObjectOptJSONObject.optString(UriUtil.LOCAL_CONTENT_SCHEME), jSONObjectOptJSONObject.optInt("http_method"), 2, iOptInt == 1, u1Var, str, z);
        }
    }
}
