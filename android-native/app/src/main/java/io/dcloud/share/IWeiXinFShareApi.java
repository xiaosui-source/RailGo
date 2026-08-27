package io.dcloud.share;

import io.dcloud.common.DHInterface.IWebview;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public interface IWeiXinFShareApi extends IFShareApi {
    void launchMiniProgram(IWebview iWebview, String str, String str2);

    void openCustomerServiceChat(IWebview iWebview, String str, String str2);
}
