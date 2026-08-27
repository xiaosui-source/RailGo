package com.taobao.weex.common;

import java.util.HashMap;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXJSService implements IWXObject {
    private String name;
    private Map<String, Object> options = new HashMap();
    private String script;

    public String getName() {
        return this.name;
    }

    public Map<String, Object> getOptions() {
        return this.options;
    }

    public String getScript() {
        return this.script;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setOptions(Map<String, Object> map) {
        this.options = map;
    }

    public void setScript(String str) {
        this.script = str;
    }
}
