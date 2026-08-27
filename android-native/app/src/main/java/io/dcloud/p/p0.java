package io.dcloud.p;

import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class p0 extends JSONObject {
    public p0() {
    }

    public JSONObject a(String str) {
        Object objOpt = super.opt(str);
        if (objOpt instanceof JSONObject) {
            return (JSONObject) objOpt;
        }
        return null;
    }

    public JSONObject b(String str) {
        return a(r4.b(str));
    }

    @Override // org.json.JSONObject
    public Object get(String str) {
        return super.get(r4.b(str));
    }

    @Override // org.json.JSONObject
    public boolean getBoolean(String str) {
        return super.getBoolean(str);
    }

    @Override // org.json.JSONObject
    public double getDouble(String str) {
        return super.getDouble(str);
    }

    @Override // org.json.JSONObject
    public int getInt(String str) {
        return super.getInt(str);
    }

    @Override // org.json.JSONObject
    public JSONArray getJSONArray(String str) {
        return super.getJSONArray(str);
    }

    @Override // org.json.JSONObject
    public JSONObject getJSONObject(String str) throws JSONException {
        Object obj = get(str);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        throw a(str, obj, "JSONObject");
    }

    @Override // org.json.JSONObject
    public long getLong(String str) {
        return super.getLong(str);
    }

    @Override // org.json.JSONObject
    public String getString(String str) {
        return super.getString(str);
    }

    @Override // org.json.JSONObject
    public boolean has(String str) {
        return super.has(r4.b(str));
    }

    @Override // org.json.JSONObject
    public boolean isNull(String str) {
        return super.isNull(r4.b(str));
    }

    @Override // org.json.JSONObject
    public Object opt(String str) {
        return super.opt(r4.b(str));
    }

    @Override // org.json.JSONObject
    public boolean optBoolean(String str) {
        return super.optBoolean(str);
    }

    @Override // org.json.JSONObject
    public double optDouble(String str) {
        return super.optDouble(str);
    }

    @Override // org.json.JSONObject
    public int optInt(String str) {
        return super.optInt(str);
    }

    @Override // org.json.JSONObject
    public JSONArray optJSONArray(String str) {
        return super.optJSONArray(str);
    }

    @Override // org.json.JSONObject
    public long optLong(String str) {
        return super.optLong(str);
    }

    @Override // org.json.JSONObject
    public String optString(String str) {
        return super.optString(str);
    }

    @Override // org.json.JSONObject
    public JSONObject put(String str, boolean z) {
        return super.put(r4.b(str), z);
    }

    @Override // org.json.JSONObject
    public Object remove(String str) {
        return super.remove(r4.b(str));
    }

    @Override // org.json.JSONObject
    public JSONArray toJSONArray(JSONArray jSONArray) {
        return super.toJSONArray(jSONArray);
    }

    public p0(Map map) {
        super(map);
    }

    @Override // org.json.JSONObject
    public boolean optBoolean(String str, boolean z) {
        return super.optBoolean(str, z);
    }

    @Override // org.json.JSONObject
    public double optDouble(String str, double d) {
        return super.optDouble(str, d);
    }

    @Override // org.json.JSONObject
    public int optInt(String str, int i) {
        return super.optInt(str, i);
    }

    @Override // org.json.JSONObject
    public p0 optJSONObject(String str) {
        Object objOpt = opt(str);
        if (objOpt == null) {
            return null;
        }
        try {
            return new p0(objOpt.toString());
        } catch (JSONException unused) {
            return null;
        }
    }

    @Override // org.json.JSONObject
    public long optLong(String str, long j) {
        return super.optLong(str, j);
    }

    @Override // org.json.JSONObject
    public String optString(String str, String str2) {
        return super.optString(str, str2);
    }

    @Override // org.json.JSONObject
    public JSONObject put(String str, double d) {
        return super.put(r4.b(str), d);
    }

    public p0(String str) {
        super(str);
    }

    public static JSONException a(Object obj, Object obj2, String str) throws JSONException {
        if (obj2 == null) {
            throw new JSONException("Value at " + obj + " is null.");
        }
        throw new JSONException("Value " + obj2 + " at " + obj + " of type " + obj2.getClass().getName() + " cannot be converted to " + str);
    }

    @Override // org.json.JSONObject
    public JSONObject put(String str, int i) {
        return super.put(r4.b(str), i);
    }

    public p0(JSONObject jSONObject, String[] strArr) {
        super(jSONObject, strArr);
    }

    @Override // org.json.JSONObject
    public JSONObject put(String str, long j) {
        return super.put(r4.b(str), j);
    }

    @Override // org.json.JSONObject
    public JSONObject put(String str, Object obj) {
        return super.put(r4.b(str), obj);
    }
}
