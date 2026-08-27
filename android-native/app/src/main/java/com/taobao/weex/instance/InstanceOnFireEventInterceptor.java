package com.taobao.weex.instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public abstract class InstanceOnFireEventInterceptor {
    private List<String> listenEvents = new ArrayList();

    public void addInterceptEvent(String str) {
        if (this.listenEvents.contains(str)) {
            return;
        }
        this.listenEvents.add(str);
    }

    public List<String> getListenEvents() {
        return this.listenEvents;
    }

    public abstract void onFireEvent(String str, String str2, String str3, Map<String, Object> map, Map<String, Object> map2);

    public void onInterceptFireEvent(String str, String str2, String str3, Map<String, Object> map, Map<String, Object> map2) {
        if (map != null && this.listenEvents.contains(str3)) {
            onFireEvent(str, str2, str3, map, map2);
        }
    }
}
