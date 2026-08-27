package com.taobao.weex.ui.module;

import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.utils.WXViewUtils;
import java.util.HashMap;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXDeviceInfoModule extends WXModule {
    @JSMethod(uiThread = false)
    public void enableFullScreenHeight(JSCallback jSCallback, JSONObject jSONObject) {
        WXSDKInstance wXSDKInstance = this.mWXSDKInstance;
        if (wXSDKInstance != null) {
            wXSDKInstance.setEnableFullScreenHeight(true);
            if (jSCallback != null) {
                long screenHeight = WXViewUtils.getScreenHeight(this.mWXSDKInstance.getInstanceId());
                HashMap map = new HashMap();
                map.put("fullScreenHeight", String.valueOf(screenHeight));
                jSCallback.invoke(map);
            }
        }
    }
}
