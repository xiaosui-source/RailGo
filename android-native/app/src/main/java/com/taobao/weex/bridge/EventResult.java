package com.taobao.weex.bridge;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class EventResult {
    private Object result;
    private boolean success = false;

    public Object getResult() {
        return this.result;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void onCallback(Object obj) {
        this.success = true;
        this.result = obj;
    }
}
