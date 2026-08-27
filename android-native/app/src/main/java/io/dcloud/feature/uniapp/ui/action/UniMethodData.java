package io.dcloud.feature.uniapp.ui.action;

import com.alibaba.fastjson.JSONArray;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class UniMethodData {
    JSONArray args;
    String method;

    public UniMethodData(String str, JSONArray jSONArray) {
        this.method = str;
        this.args = jSONArray;
    }

    public JSONArray getArgs() {
        return this.args;
    }

    public String getMethod() {
        return this.method;
    }
}
