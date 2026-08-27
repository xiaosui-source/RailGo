package com.taobao.weex;

import android.view.View;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public interface IWXRenderListener {
    void onException(WXSDKInstance wXSDKInstance, String str, String str2);

    void onRefreshSuccess(WXSDKInstance wXSDKInstance, int i, int i2);

    void onRenderSuccess(WXSDKInstance wXSDKInstance, int i, int i2);

    void onViewCreated(WXSDKInstance wXSDKInstance, View view);
}
