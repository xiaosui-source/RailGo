package io.dcloud.common.util;

import com.taobao.weex.el.parse.Operators;
import io.dcloud.common.adapter.util.Logger;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class JSONUtil {
    public static JSONObject combinJSONObject(JSONObject jSONObject, JSONObject jSONObject2) throws JSONException {
        Iterator<String> itKeys;
        if (jSONObject == null) {
            return jSONObject2;
        }
        if (jSONObject != jSONObject2 && jSONObject2 != null && (itKeys = jSONObject2.keys()) != null) {
            while (itKeys.hasNext()) {
                try {
                    String strValueOf = String.valueOf(itKeys.next());
                    Object objOpt = jSONObject.opt(strValueOf);
                    Object objOpt2 = jSONObject2.opt(strValueOf);
                    if (objOpt2 != null) {
                        if (objOpt == null) {
                            jSONObject.putOpt(strValueOf, objOpt2);
                        } else if (!(objOpt2 instanceof JSONObject)) {
                            jSONObject.putOpt(strValueOf, objOpt2);
                        } else if (objOpt instanceof JSONObject) {
                            combinJSONObject((JSONObject) objOpt, (JSONObject) objOpt2);
                        } else {
                            jSONObject.putOpt(strValueOf, objOpt2);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jSONObject;
    }

    public static JSONObject combinSitemapHtmlJSONObject(JSONObject jSONObject, JSONObject jSONObject2) {
        Iterator<String> itKeys;
        if (jSONObject == null) {
            return jSONObject2;
        }
        if (jSONObject != jSONObject2 && (itKeys = jSONObject2.keys()) != null) {
            while (itKeys.hasNext()) {
                try {
                    String strValueOf = String.valueOf(itKeys.next());
                    Object objOpt = jSONObject.opt(strValueOf);
                    Object objOpt2 = jSONObject2.opt(strValueOf);
                    if (objOpt2 != null) {
                        if (objOpt == null) {
                            jSONObject.putOpt(strValueOf, objOpt2);
                        } else if (objOpt2 instanceof JSONObject) {
                            if (objOpt instanceof JSONObject) {
                                combinSitemapHtmlJSONObject((JSONObject) objOpt, (JSONObject) objOpt2);
                            } else {
                                jSONObject.putOpt(strValueOf, objOpt2);
                            }
                        } else if ((objOpt instanceof JSONObject) && (objOpt2 instanceof Boolean)) {
                            jSONObject.remove(strValueOf);
                        } else {
                            jSONObject.putOpt(strValueOf, objOpt2);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jSONObject;
    }

    public static JSONArray createJSONArray(String str) {
        if (!str.startsWith(Operators.ARRAY_START_STR)) {
            str = Operators.ARRAY_START_STR + str;
        }
        if (!str.endsWith(Operators.ARRAY_END_STR)) {
            str = str + Operators.ARRAY_END_STR;
        }
        try {
            return new JSONArray(str);
        } catch (JSONException unused) {
            return null;
        }
    }

    public static JSONObject createJSONObject(String str) {
        if (str == null) {
            return null;
        }
        try {
            return new JSONObject(str);
        } catch (Exception unused) {
            Logger.d("jsonutil", "JSONException pJson=" + str);
            return null;
        }
    }

    public static boolean getBoolean(JSONObject jSONObject, String str) {
        if (jSONObject != null) {
            try {
                if (jSONObject.has(str)) {
                    return jSONObject.getBoolean(str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static int getInt(JSONObject jSONObject, String str) throws JSONException {
        try {
            int i = jSONObject.getInt(str);
            Integer.valueOf(i).getClass();
            return i;
        } catch (Exception unused) {
            return 0;
        }
    }

    public static JSONArray getJSONArray(JSONArray jSONArray, int i) throws JSONException {
        try {
            Object obj = jSONArray.get(i);
            if (obj instanceof JSONArray) {
                return (JSONArray) obj;
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public static JSONObject getJSONObject(JSONObject jSONObject, String str) {
        try {
            Object objOpt = jSONObject.opt(str);
            if (objOpt instanceof JSONObject) {
                return (JSONObject) objOpt;
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public static long getLong(JSONObject jSONObject, String str) {
        if (jSONObject != null) {
            try {
                if (jSONObject.has(str)) {
                    return jSONObject.getLong(str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0L;
    }

    public static String getString(JSONObject jSONObject, String str) {
        if (jSONObject == null) {
            return null;
        }
        try {
            return jSONObject.getString(str);
        } catch (Exception unused) {
            return null;
        }
    }

    public static boolean isNull(JSONObject jSONObject, String str) {
        if (jSONObject == null) {
            return true;
        }
        try {
            return jSONObject.isNull(str);
        } catch (Exception unused) {
            return true;
        }
    }

    public static String toJSONableString(String str) {
        return str != null ? JSONObject.quote(str) : "''";
    }

    public static HashMap<String, String> toMap(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        Iterator<String> itKeys = jSONObject.keys();
        HashMap<String, String> map = new HashMap<>(jSONObject.length());
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            map.put(next, jSONObject.optString(next));
        }
        return map;
    }

    public static String getString(JSONArray jSONArray, int i) throws JSONException {
        try {
            Object obj = jSONArray.get(i);
            if (PdrUtil.isEmpty(obj)) {
                return null;
            }
            return String.valueOf(obj);
        } catch (Exception unused) {
            return null;
        }
    }

    public static JSONArray getJSONArray(JSONObject jSONObject, String str) throws JSONException {
        try {
            Object obj = jSONObject.get(str);
            if (obj instanceof JSONArray) {
                return (JSONArray) obj;
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public static JSONObject getJSONObject(JSONArray jSONArray, int i) throws JSONException {
        try {
            Object obj = jSONArray.get(i);
            if (obj instanceof JSONObject) {
                return (JSONObject) obj;
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }
}
