package com.taobao.weex.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.common.WXRuntimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXJsonUtils {
    public static String fromObjectToJSONString(Object obj, boolean z) {
        try {
            return z ? JSON.toJSONString(obj, SerializerFeature.WriteNonStringKeyAsString) : JSON.toJSONString(obj);
        } catch (Exception e) {
            if (WXEnvironment.isApkDebugable()) {
                throw new WXRuntimeException("fromObjectToJSONString parse error!");
            }
            WXLogUtils.e("fromObjectToJSONString error:", e);
            return "{}";
        }
    }

    public static <T> List<T> getList(String str, Class<T> cls) {
        try {
            return JSONObject.parseArray(str, cls);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    public static void putAll(Map<String, Object> map, JSONObject jSONObject) {
        for (Map.Entry<String, Object> entry : jSONObject.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key != null && value != null) {
                map.put(key, value);
            }
        }
    }

    public static String fromObjectToJSONString(Object obj) {
        return fromObjectToJSONString(obj, false);
    }
}
