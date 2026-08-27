package io.dcloud.sdk.core.v3.fd;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public interface DCFeedAOLListener {
    void onClick();

    void onClosed(String str);

    void onRenderFail();

    void onRenderSuccess();

    void onShow();

    void onShowError();
}
