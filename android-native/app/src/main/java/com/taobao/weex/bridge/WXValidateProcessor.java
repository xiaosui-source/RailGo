package com.taobao.weex.bridge;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.ui.component.WXComponent;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public interface WXValidateProcessor {

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class WXComponentValidateResult {
        public boolean isSuccess;
        public String replacedComponent;
        public JSONObject validateInfo;
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class WXModuleValidateResult {
        public boolean isSuccess;
        public JSONObject validateInfo;
    }

    boolean needValidate(String str);

    WXComponentValidateResult onComponentValidate(WXSDKInstance wXSDKInstance, String str, WXComponent wXComponent);

    WXModuleValidateResult onModuleValidate(WXSDKInstance wXSDKInstance, String str, String str2, JSONArray jSONArray, JSONObject jSONObject);
}
