package com.taobao.weex.dom.binding;

import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.el.parse.Operators;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class JSONUtils {
    public static boolean isJSON(Object obj) {
        if (obj instanceof JSONObject) {
            return true;
        }
        if (obj instanceof String) {
            return ((String) obj).startsWith(Operators.BLOCK_START_STR);
        }
        return false;
    }

    public static JSONObject toJSON(Object obj) {
        return obj instanceof JSONObject ? (JSONObject) obj : JSONObject.parseObject(obj.toString());
    }

    public static boolean isJSON(String str) {
        return str.startsWith(Operators.BLOCK_START_STR);
    }
}
