package com.taobao.weex.appfram.websocket;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public interface IWebSocketAdapter {
    public static final String HEADER_SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface EventListener {
        void onClose(int i, String str, boolean z);

        void onError(String str);

        void onMessage(String str);

        void onOpen();
    }

    void close(int i, String str);

    void connect(String str, String str2, EventListener eventListener);

    void connect(String str, String str2, String str3, EventListener eventListener);

    void destroy();

    void send(String str);
}
