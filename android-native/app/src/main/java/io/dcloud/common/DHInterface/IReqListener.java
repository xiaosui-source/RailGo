package io.dcloud.common.DHInterface;

import java.io.InputStream;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public interface IReqListener {

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public enum NetState {
        NET_INIT,
        NET_REQUEST_BEGIN,
        NET_TIMEOUT,
        NET_CONNECTED,
        NET_ERROR,
        NET_HANDLE_BEGIN,
        NET_HANDLE_ING,
        NET_HANDLE_END,
        NET_PAUSE
    }

    void onNetStateChanged(NetState netState, boolean z);

    int onReceiving(InputStream inputStream) throws Exception;

    void onResponsing(InputStream inputStream);
}
