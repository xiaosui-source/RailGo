package io.dcloud.common.adapter.util;

import java.util.Map;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class EventActionInfo {
    private String evalJs;
    private String eventAction;
    private Map<String, Object> params;

    public EventActionInfo(String str) {
        this.eventAction = str;
    }

    public String getEvalJs() {
        return this.evalJs;
    }

    public String getEventAction() {
        return this.eventAction;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public EventActionInfo(String str, Map<String, Object> map) {
        this.eventAction = str;
        this.params = map;
    }

    public EventActionInfo(String str, String str2) {
        this.eventAction = str;
        this.evalJs = str2;
    }

    public EventActionInfo(String str, String str2, Map<String, Object> map) {
        this.eventAction = str;
        this.evalJs = str2;
        this.params = map;
    }
}
