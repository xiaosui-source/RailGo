package io.dcloud.common.util;

import com.taobao.weex.el.parse.Operators;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class JsEventUtil {
    public static String broadcastEvents_format(String str, String str2, boolean z, String... strArr) {
        StringBuilder sb = new StringBuilder("{evt:'%s',args:");
        sb.append(z ? "'%s'" : "%s");
        sb.append(",callbackId:'%s'}");
        return StringUtil.format(sb.toString(), str, str2, strArr);
    }

    public static String eventListener_format(String str, String str2, boolean z) {
        StringBuilder sb = new StringBuilder("{evt:'%s',args:");
        sb.append(z ? "'%s'" : "%s");
        sb.append(Operators.BLOCK_END_STR);
        return StringUtil.format(sb.toString(), str, str2);
    }
}
