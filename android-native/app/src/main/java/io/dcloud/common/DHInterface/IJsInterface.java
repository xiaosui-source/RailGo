package io.dcloud.common.DHInterface;

import org.json.JSONArray;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public interface IJsInterface {
    @Deprecated
    String exec(String str, String str2, String str3);

    String exec(String str, String str2, JSONArray jSONArray);

    void forceStop(String str);

    String prompt(String str, String str2);
}
