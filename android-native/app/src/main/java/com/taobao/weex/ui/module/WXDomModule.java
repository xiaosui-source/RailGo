package com.taobao.weex.ui.module;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.ui.action.ActionInvokeMethod;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public final class WXDomModule extends WXModule {
    public static final String UPDATE_COMPONENT_DATA = "updateComponentData";
    public static final String WXDOM = "dom";
    public static final String SCROLL_TO_ELEMENT = "scrollToElement";
    public static final String ADD_RULE = "addRule";
    public static final String GET_COMPONENT_RECT = "getComponentRect";
    public static final String INVOKE_METHOD = "invokeMethod";
    public static final String GET_COMPONENT_DIRECTION = "getLayoutDirection";
    public static final String BATCH_BEGIN = "beginBatchMark";
    public static final String BATCH_END = "endBatchMark";
    public static final String[] METHODS = {SCROLL_TO_ELEMENT, ADD_RULE, GET_COMPONENT_RECT, INVOKE_METHOD, GET_COMPONENT_DIRECTION, BATCH_BEGIN, BATCH_END};

    public WXDomModule(WXSDKInstance wXSDKInstance) {
        this.mWXSDKInstance = wXSDKInstance;
        this.mUniSDKInstance = wXSDKInstance;
    }

    public void callDomMethod(JSONObject jSONObject, long... jArr) {
        if (jSONObject == null) {
            return;
        }
        callDomMethod((String) jSONObject.get("method"), (JSONArray) jSONObject.get("args"), jArr);
    }

    public void invokeMethod(String str, String str2, JSONArray jSONArray) {
        if (str == null || str2 == null) {
            return;
        }
        new ActionInvokeMethod(this.mWXSDKInstance.getInstanceId(), str, str2, jSONArray).executeAction();
    }

    /* JADX WARN: Removed duplicated region for block: B:65:0x0137 A[Catch: ClassCastException -> 0x013d, IndexOutOfBoundsException -> 0x0143, TRY_LEAVE, TryCatch #2 {ClassCastException -> 0x013d, IndexOutOfBoundsException -> 0x0143, blocks: (B:5:0x0004, B:6:0x000b, B:65:0x0137, B:8:0x0010, B:12:0x001b, B:13:0x002f, B:16:0x003a, B:19:0x0042, B:21:0x005f, B:25:0x006a, B:26:0x007e, B:30:0x0089, B:32:0x008f, B:34:0x0095, B:35:0x00a1, B:39:0x00ac, B:40:0x00c8, B:44:0x00d3, B:46:0x00d9, B:48:0x00df, B:50:0x00e5, B:52:0x00eb, B:53:0x00f6, B:57:0x0101, B:58:0x0118, B:60:0x0120, B:62:0x0126, B:64:0x012c), top: B:71:0x0004 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object callDomMethod(java.lang.String r5, com.alibaba.fastjson.JSONArray r6, long... r7) {
        /*
            Method dump skipped, instructions count: 368
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.ui.module.WXDomModule.callDomMethod(java.lang.String, com.alibaba.fastjson.JSONArray, long[]):java.lang.Object");
    }
}
