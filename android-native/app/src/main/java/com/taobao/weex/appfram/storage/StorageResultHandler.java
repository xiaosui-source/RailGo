package com.taobao.weex.appfram.storage;

import com.taobao.weex.bridge.JSCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class StorageResultHandler {
    private static final String DATA = "data";
    private static final String RESULT = "result";
    private static final String RESULT_FAILED = "failed";
    private static final String RESULT_FAILED_INVALID_PARAM = "invalid_param";
    private static final String RESULT_FAILED_NO_HANDLER = "no_handler";
    private static final String RESULT_OK = "success";
    private static final String UNDEFINED = "undefined";

    private StorageResultHandler() {
    }

    public static Map<String, Object> getAllkeysResult(List<String> list) {
        if (list == null) {
            list = new ArrayList<>(1);
        }
        HashMap map = new HashMap(4);
        map.put("result", "success");
        map.put("data", list);
        return map;
    }

    public static Map<String, Object> getItemResult(String str) {
        HashMap map = new HashMap(4);
        map.put("result", str != null ? "success" : "failed");
        if (str == null) {
            str = "undefined";
        }
        map.put("data", str);
        return map;
    }

    public static Map<String, Object> getLengthResult(long j) {
        HashMap map = new HashMap(4);
        map.put("result", "success");
        map.put("data", Long.valueOf(j));
        return map;
    }

    public static void handleInvalidParam(JSCallback jSCallback) {
        handleResult(jSCallback, "failed", RESULT_FAILED_INVALID_PARAM);
    }

    public static void handleNoHandlerError(JSCallback jSCallback) {
        handleResult(jSCallback, "failed", RESULT_FAILED_NO_HANDLER);
    }

    private static void handleResult(JSCallback jSCallback, String str, Object obj) {
        if (jSCallback == null) {
            return;
        }
        HashMap map = new HashMap(4);
        map.put("result", str);
        map.put("data", obj);
        jSCallback.invoke(map);
    }

    public static Map<String, Object> removeItemResult(boolean z) {
        HashMap map = new HashMap(4);
        map.put("result", z ? "success" : "failed");
        map.put("data", "undefined");
        return map;
    }

    public static Map<String, Object> setItemResult(boolean z) {
        HashMap map = new HashMap(4);
        map.put("result", z ? "success" : "failed");
        map.put("data", "undefined");
        return map;
    }
}
