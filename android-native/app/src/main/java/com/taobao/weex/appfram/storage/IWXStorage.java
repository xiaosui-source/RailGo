package com.taobao.weex.appfram.storage;

import com.taobao.weex.bridge.JSCallback;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
interface IWXStorage {
    void getAllKeys(JSCallback jSCallback);

    void getItem(String str, JSCallback jSCallback);

    void length(JSCallback jSCallback);

    void removeItem(String str, JSCallback jSCallback);

    void setItem(String str, String str2, JSCallback jSCallback);

    void setItemPersistent(String str, String str2, JSCallback jSCallback);
}
