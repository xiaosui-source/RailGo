package io.dcloud.share;

import io.dcloud.common.DHInterface.IReflectAble;
import io.dcloud.common.DHInterface.IWebview;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public interface IFShareApi extends IReflectAble {
    public static final String mLink = "https://ask.dcloud.net.cn/article/287";

    void authorize(IWebview iWebview, String str, String str2);

    void dispose();

    void forbid(IWebview iWebview);

    String getId();

    String getJsonObject(IWebview iWebview);

    void initConfig();

    void send(IWebview iWebview, String str, String str2);
}
