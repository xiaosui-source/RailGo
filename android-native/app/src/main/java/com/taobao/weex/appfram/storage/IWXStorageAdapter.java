package com.taobao.weex.appfram.storage;

import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public interface IWXStorageAdapter {

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface OnResultReceivedListener {
        void onReceived(Map<String, Object> map);
    }

    void close();

    void getAllKeys(OnResultReceivedListener onResultReceivedListener);

    void getItem(String str, OnResultReceivedListener onResultReceivedListener);

    void length(OnResultReceivedListener onResultReceivedListener);

    void removeItem(String str, OnResultReceivedListener onResultReceivedListener);

    void setItem(String str, String str2, OnResultReceivedListener onResultReceivedListener);

    void setItemPersistent(String str, String str2, OnResultReceivedListener onResultReceivedListener);
}
