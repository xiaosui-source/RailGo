package com.taobao.weex.utils.tools;

import com.alibaba.fastjson.annotation.JSONField;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class TaskInfo {

    @JSONField(name = "args")
    public String args;

    @JSONField(name = "relateTaskId")
    public int relateTaskId;

    public String toString() {
        return "TaskInfo{args = '" + this.args + "',relateTaskId = '" + this.relateTaskId + "'}";
    }
}
