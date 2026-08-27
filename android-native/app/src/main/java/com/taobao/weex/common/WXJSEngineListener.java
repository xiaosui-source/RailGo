package com.taobao.weex.common;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public interface WXJSEngineListener {
    void callback(boolean z, String str, String str2, String str3);

    void createInstanceFailed(String str);

    void createInstanceSuccess(String str);

    void destroyInstanceFailed(String str);

    void destroyInstanceSuccess(String str);

    void fireEvent(boolean z, String str, String str2, String str3);

    void initFramework(boolean z, String str, double d);
}
