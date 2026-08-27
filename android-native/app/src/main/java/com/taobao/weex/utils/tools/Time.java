package com.taobao.weex.utils.tools;

import com.alibaba.fastjson.annotation.JSONField;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class Time {

    @JSONField(name = "constructor")
    public long constructor;

    @JSONField(name = "destructor")
    public long destructor;

    @JSONField(name = "execTime")
    public long execTime;

    @JSONField(name = "taskEnd")
    public long taskEnd;

    @JSONField(name = "taskStart")
    public long taskStart;

    @JSONField(name = "waitTime")
    public long waitTime;

    private void destructor() {
        waitTime();
        this.destructor = System.currentTimeMillis();
    }

    protected void constructor() {
        this.constructor = System.currentTimeMillis();
    }

    public void execTime() {
        this.execTime = this.taskEnd - this.taskStart;
    }

    public void taskEnd() {
        this.taskEnd = System.currentTimeMillis();
        execTime();
        destructor();
    }

    public void taskStart() {
        this.taskStart = System.currentTimeMillis();
    }

    public String toString() {
        return "time : {constructor = '" + this.constructor + "',taskStart = '" + this.taskStart + "',execTime = '" + this.execTime + "',waitTime = '" + this.waitTime + "',destructor = '" + this.destructor + "',taskEnd = '" + this.taskEnd + "'}";
    }

    public void waitTime() {
        this.waitTime = this.taskStart - this.constructor;
    }
}
