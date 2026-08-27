package com.taobao.weex.performance;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public interface IWXApmMonitorAdapter {
    void addProperty(String str, Object obj);

    void addStats(String str, double d);

    void onAppear();

    void onDisappear();

    void onEnd();

    void onEvent(String str, Object obj);

    void onStage(String str, long j);

    void onStart(String str);

    void onSubProcedureEvent(String str, String str2);

    void onSubProcedureStage(String str, String str2);

    String parseReportUrl(String str);

    void setSubProcedureProperties(String str, String str2, Object obj);

    void setSubProcedureStats(String str, String str2, double d);
}
