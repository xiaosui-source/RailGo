package com.bun.miitmdid.a;

import android.content.Context;
import android.text.TextUtils;
import com.bun.miitmdid.a.c.c;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class a implements b {
    public C0001a a = new C0001a(this);

    /* renamed from: com.bun.miitmdid.a.a$a, reason: collision with other inner class name */
    public class C0001a {
        public com.bun.miitmdid.a.c.a a;
        public com.bun.miitmdid.a.c.b b;
        public c c;

        public C0001a(a aVar) {
        }
    }

    private a() {
    }

    public static a a(Context context) throws IOException {
        boolean z;
        JSONObject jSONObject;
        JSONObject jSONObjectOptJSONObject;
        String strA = com.bun.miitmdid.d.a.a(context, "supplierconfig.json");
        if (TextUtils.isEmpty(strA)) {
            return null;
        }
        a aVar = new a();
        try {
            jSONObject = (JSONObject) new JSONTokener(strA).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
            z = false;
        }
        if (jSONObject == null || (jSONObjectOptJSONObject = jSONObject.optJSONObject("supplier")) == null) {
            return null;
        }
        z = a(aVar, jSONObjectOptJSONObject) && c(aVar, jSONObjectOptJSONObject) && b(aVar, jSONObjectOptJSONObject);
        return z ? aVar : null;
    }

    private static boolean a(a aVar, JSONObject jSONObject) {
        if (jSONObject == null || aVar == null) {
            return false;
        }
        if (jSONObject.optJSONObject("huawei") != null) {
            aVar.a.a = new com.bun.miitmdid.a.c.a();
        }
        return aVar.a.a != null;
    }

    private static boolean b(a aVar, JSONObject jSONObject) {
        if (jSONObject == null || aVar == null) {
            return false;
        }
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("vivo");
        com.bun.miitmdid.a.c.b bVar = new com.bun.miitmdid.a.c.b();
        if (jSONObjectOptJSONObject != null) {
            bVar.a = jSONObjectOptJSONObject.optString("appid");
            aVar.a.b = bVar;
        }
        return aVar.a.b != null;
    }

    private static boolean c(a aVar, JSONObject jSONObject) {
        if (jSONObject == null || aVar == null) {
            return false;
        }
        if (jSONObject.optJSONObject("xiaomi") != null) {
            aVar.a.c = new c();
        }
        return aVar.a.c != null;
    }

    @Override // com.bun.miitmdid.a.b
    public String a() {
        com.bun.miitmdid.a.c.b bVar;
        String str;
        C0001a c0001a = this.a;
        return (c0001a == null || (bVar = c0001a.b) == null || (str = bVar.a) == null) ? "" : str;
    }
}
