package com.taobao.weex.common;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.JSMethod;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXInstanceWrap extends WXModule {
    @JSMethod
    public void error(String str, String str2, String str3) {
        WXSDKInstance parentInstance = this.mWXSDKInstance;
        if (parentInstance != null) {
            if (str3 != null && str3.contains("downgrade_to_root")) {
                while (parentInstance.getParentInstance() != null) {
                    parentInstance = parentInstance.getParentInstance();
                }
            }
            parentInstance.onRenderError(str + "|" + str2, str3);
        }
    }
}
