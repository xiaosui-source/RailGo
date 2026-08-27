package com.taobao.weex;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXGlobalEventModule extends WXModule {
    @JSMethod
    public void addEventListener(String str, String str2) {
        this.mWXSDKInstance.addEventListener(str, str2);
    }

    public void removeEventListener(String str, String str2) {
        this.mWXSDKInstance.removeEventListener(str, str2);
    }

    @Override // com.taobao.weex.common.WXModule
    public void addEventListener(String str, String str2, Map<String, Object> map) {
        super.addEventListener(str, str2, map);
        addEventListener(str, str2);
    }

    @JSMethod
    public void removeEventListener(String str) {
        this.mWXSDKInstance.removeEventListener(str);
    }
}
