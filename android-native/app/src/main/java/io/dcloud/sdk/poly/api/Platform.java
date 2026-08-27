package io.dcloud.sdk.poly.api;

import io.dcloud.common.constant.AbsoluteConst;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class Platform {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private JSONObject g;

    public String getAppid() {
        return this.f;
    }

    public String getEc() {
        return this.d;
    }

    public String getEr() {
        return this.c;
    }

    public JSONObject getRewardJson() {
        return this.g;
    }

    public String getSplash() {
        return this.b;
    }

    public String getTid() {
        return this.e;
    }

    public String getType() {
        return this.a;
    }

    public void setPlatJson(JSONObject jSONObject) {
        this.c = jSONObject.optString("er");
        this.d = jSONObject.optString("ec");
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("splash");
        if (jSONObjectOptJSONObject != null) {
            this.b = jSONObjectOptJSONObject.toString();
            this.e = jSONObjectOptJSONObject.optString("tid");
            JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject(AbsoluteConst.XML_APP);
            if (jSONObjectOptJSONObject2 != null) {
                this.f = jSONObjectOptJSONObject2.optString("app_id");
            }
        }
        this.g = jSONObject.optJSONObject("reward");
    }

    public void setType(String str) {
        this.a = str;
    }
}
