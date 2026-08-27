package io.dcloud.p;

import android.text.TextUtils;
import io.dcloud.sdk.poly.api.Platform;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class a0 {
    private String a;
    private String b;
    private JSONObject c;
    private JSONArray d = new JSONArray();
    private Platform e;
    private String f;
    private String g;

    public String a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public void c(String str) {
        this.f = str;
    }

    public String d() {
        return this.g;
    }

    public JSONObject e() {
        return this.c;
    }

    public Platform f() {
        return this.e;
    }

    public boolean g() {
        return !TextUtils.isEmpty(this.g);
    }

    public void a(String str) {
        this.a = str;
    }

    public void b(String str) {
        this.b = str;
    }

    public String c() {
        return this.f;
    }

    public void a(Platform platform) {
        this.e = platform;
    }

    public boolean a(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null || jSONObject.length() <= 0) {
            return false;
        }
        this.a = jSONObject.optString("appid");
        this.d = jSONObject.optJSONArray("appidh");
        this.b = jSONObject.optString("appkey");
        this.c = jSONObject.optJSONObject("ext");
        this.g = jSONObject.optString("ada");
        try {
            if (this.c != null) {
                if (g()) {
                    this.c.put("appid", this.a);
                } else {
                    if (this.e == null) {
                        this.e = new Platform();
                    }
                    this.e.setType(this.f);
                    this.e.setPlatJson(this.c);
                    return true;
                }
            } else {
                if (!g()) {
                    return true;
                }
                JSONObject jSONObject2 = new JSONObject();
                this.c = jSONObject2;
                jSONObject2.put("appid", this.a);
            }
            return true;
        } catch (JSONException unused) {
            return true;
        }
    }
}
