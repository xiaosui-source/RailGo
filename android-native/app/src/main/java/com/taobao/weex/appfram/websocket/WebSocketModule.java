package com.taobao.weex.appfram.websocket;

import android.os.Looper;
import com.alibaba.fastjson.JSON;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.appfram.websocket.IWebSocketAdapter;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.utils.WXLogUtils;
import io.dcloud.common.constant.AbsoluteConst;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WebSocketModule extends WXSDKEngine.DestroyableModule {
    private static final String KEY_CODE = "code";
    private static final String KEY_DATA = "data";
    private static final String KEY_REASON = "reason";
    private static final String KEY_WAS_CLEAN = "wasClean";
    private static final String TAG = "WebSocketModule";
    private WebSocketEventListener eventListener;
    private IWebSocketAdapter webSocketAdapter;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    private class WebSocketEventListener implements IWebSocketAdapter.EventListener {
        private JSCallback onClose;
        private JSCallback onError;
        private JSCallback onMessage;
        private JSCallback onOpen;

        private WebSocketEventListener() {
        }

        @Override // com.taobao.weex.appfram.websocket.IWebSocketAdapter.EventListener
        public void onClose(int i, String str, boolean z) {
            if (this.onClose != null) {
                HashMap map = new HashMap(3);
                map.put("code", Integer.valueOf(i));
                map.put(WebSocketModule.KEY_REASON, str);
                map.put(WebSocketModule.KEY_WAS_CLEAN, Boolean.valueOf(z));
                this.onClose.invoke(map);
            }
        }

        @Override // com.taobao.weex.appfram.websocket.IWebSocketAdapter.EventListener
        public void onError(String str) {
            if (this.onError != null) {
                HashMap map = new HashMap(1);
                map.put("data", str);
                this.onError.invokeAndKeepAlive(map);
            }
        }

        @Override // com.taobao.weex.appfram.websocket.IWebSocketAdapter.EventListener
        public void onMessage(String str) {
            if (this.onMessage != null) {
                HashMap map = new HashMap(1);
                try {
                    map.put("data", JSON.parseObject(str));
                } catch (Exception unused) {
                    map.put("data", str);
                }
                this.onMessage.invokeAndKeepAlive(map);
            }
        }

        @Override // com.taobao.weex.appfram.websocket.IWebSocketAdapter.EventListener
        public void onOpen() {
            JSCallback jSCallback = this.onOpen;
            if (jSCallback != null) {
                jSCallback.invoke(new HashMap(0));
            }
        }
    }

    public WebSocketModule() {
        WXLogUtils.e(TAG, "create new instance");
    }

    private boolean reportErrorIfNoAdapter() {
        if (this.webSocketAdapter != null) {
            return false;
        }
        WebSocketEventListener webSocketEventListener = this.eventListener;
        if (webSocketEventListener != null) {
            webSocketEventListener.onError("No implementation found for IWebSocketAdapter");
        }
        WXLogUtils.e(TAG, "No implementation found for IWebSocketAdapter");
        return true;
    }

    @JSMethod(uiThread = false)
    public void WebSocket(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.webSocketAdapter != null) {
            WXLogUtils.w(TAG, AbsoluteConst.EVENTS_CLOSE);
            IWebSocketAdapter iWebSocketAdapter = this.webSocketAdapter;
            WebSocketCloseCodes webSocketCloseCodes = WebSocketCloseCodes.CLOSE_GOING_AWAY;
            iWebSocketAdapter.close(webSocketCloseCodes.getCode(), webSocketCloseCodes.name());
        }
        this.webSocketAdapter = this.mWXSDKInstance.getWXWebSocketAdapter();
        if (reportErrorIfNoAdapter()) {
            return;
        }
        WebSocketEventListener webSocketEventListener = new WebSocketEventListener();
        this.eventListener = webSocketEventListener;
        this.webSocketAdapter.connect(str, str2, webSocketEventListener);
    }

    @JSMethod(uiThread = false)
    public void close(String str, String str2) throws NumberFormatException {
        if (reportErrorIfNoAdapter()) {
            return;
        }
        int code = WebSocketCloseCodes.CLOSE_NORMAL.getCode();
        if (str != null) {
            try {
                code = Integer.parseInt(str);
            } catch (NumberFormatException unused) {
            }
        }
        this.webSocketAdapter.close(code, str2);
    }

    @Override // com.taobao.weex.common.Destroyable
    public void destroy() {
        Runnable runnable = new Runnable() { // from class: com.taobao.weex.appfram.websocket.WebSocketModule.1
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                WXLogUtils.w(WebSocketModule.TAG, "close session with instance id " + WebSocketModule.this.mWXSDKInstance.getInstanceId());
                if (WebSocketModule.this.webSocketAdapter != null) {
                    WebSocketModule.this.webSocketAdapter.destroy();
                }
                WebSocketModule.this.webSocketAdapter = null;
                WebSocketModule.this.eventListener = null;
            }
        };
        if (Looper.myLooper() == Looper.getMainLooper()) {
            WXBridgeManager.getInstance().post(runnable);
        } else {
            runnable.run();
        }
    }

    @JSMethod(uiThread = false)
    public void onclose(JSCallback jSCallback) {
        WebSocketEventListener webSocketEventListener = this.eventListener;
        if (webSocketEventListener != null) {
            webSocketEventListener.onClose = jSCallback;
        }
    }

    @JSMethod(uiThread = false)
    public void onerror(JSCallback jSCallback) {
        WebSocketEventListener webSocketEventListener = this.eventListener;
        if (webSocketEventListener != null) {
            webSocketEventListener.onError = jSCallback;
        }
    }

    @JSMethod(uiThread = false)
    public void onmessage(JSCallback jSCallback) {
        WebSocketEventListener webSocketEventListener = this.eventListener;
        if (webSocketEventListener != null) {
            webSocketEventListener.onMessage = jSCallback;
        }
    }

    @JSMethod(uiThread = false)
    public void onopen(JSCallback jSCallback) {
        WebSocketEventListener webSocketEventListener = this.eventListener;
        if (webSocketEventListener != null) {
            webSocketEventListener.onOpen = jSCallback;
        }
    }

    @JSMethod(uiThread = false)
    public void send(String str) {
        if (reportErrorIfNoAdapter()) {
            return;
        }
        this.webSocketAdapter.send(str);
    }

    @JSMethod(uiThread = false)
    public void WebSocket(String str, String str2, String str3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.webSocketAdapter != null) {
            WXLogUtils.w(TAG, AbsoluteConst.EVENTS_CLOSE);
            IWebSocketAdapter iWebSocketAdapter = this.webSocketAdapter;
            WebSocketCloseCodes webSocketCloseCodes = WebSocketCloseCodes.CLOSE_GOING_AWAY;
            iWebSocketAdapter.close(webSocketCloseCodes.getCode(), webSocketCloseCodes.name());
        }
        this.webSocketAdapter = this.mWXSDKInstance.getWXWebSocketAdapter();
        if (reportErrorIfNoAdapter()) {
            return;
        }
        WebSocketEventListener webSocketEventListener = new WebSocketEventListener();
        this.eventListener = webSocketEventListener;
        this.webSocketAdapter.connect(str, str2, str3, webSocketEventListener);
    }
}
