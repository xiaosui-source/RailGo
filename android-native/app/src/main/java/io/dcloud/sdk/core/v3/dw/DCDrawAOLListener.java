package io.dcloud.sdk.core.v3.dw;

import io.dcloud.sdk.core.v3.fd.DCFeedAOLListener;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public interface DCDrawAOLListener extends DCFeedAOLListener {
    @Override // io.dcloud.sdk.core.v3.fd.DCFeedAOLListener
    void onClick();

    @Override // io.dcloud.sdk.core.v3.fd.DCFeedAOLListener
    void onClosed(String str);

    void onEnd();

    void onPause();

    @Override // io.dcloud.sdk.core.v3.fd.DCFeedAOLListener
    void onRenderFail();

    @Override // io.dcloud.sdk.core.v3.fd.DCFeedAOLListener
    void onRenderSuccess();

    void onResume();

    @Override // io.dcloud.sdk.core.v3.fd.DCFeedAOLListener
    void onShow();

    @Override // io.dcloud.sdk.core.v3.fd.DCFeedAOLListener
    void onShowError();

    void onStart();
}
