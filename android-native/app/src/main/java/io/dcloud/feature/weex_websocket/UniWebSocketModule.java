package io.dcloud.feature.weex_websocket;

import android.os.Looper;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.appfram.websocket.IWebSocketAdapter;
import com.taobao.weex.appfram.websocket.WebSocketCloseCodes;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.ui.component.WXBasicComponentType;
import com.taobao.weex.utils.WXLogUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class UniWebSocketModule extends WXSDKEngine.DestroyableModule {
    private static final String KEY_CODE = "code";
    private static final String KEY_DATA = "data";
    private static final String KEY_ID = "id";
    private static final String KEY_REASON = "reason";
    private static final String KEY_WAS_CLEAN = "wasClean";
    private static final String TAG = "WebSocketModule";
    private JSCallback callback_onclose;
    private JSCallback callback_onerror;
    private JSCallback callback_onmessage;
    private JSCallback callback_onopen;
    private String currentId;
    private Map<String, IWebSocketAdapter> webSocketAdapterMap;
    private Map<String, WebSocketEventListener> webSocketEventListenerMap;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    private class WebSocketEventListener implements IWebSocketAdapter.EventListener {
        private String id;
        private JSCallback onClose;
        private JSCallback onError;
        private JSCallback onMessage;
        private JSCallback onOpen;

        public WebSocketEventListener(String str) {
            this.id = str;
        }

        @Override // com.taobao.weex.appfram.websocket.IWebSocketAdapter.EventListener
        public void onClose(int i, String str, boolean z) {
            if (this.onClose != null) {
                HashMap map = new HashMap(4);
                map.put("id", this.id);
                map.put("code", Integer.valueOf(i));
                map.put(UniWebSocketModule.KEY_REASON, str);
                map.put(UniWebSocketModule.KEY_WAS_CLEAN, Boolean.valueOf(z));
                this.onClose.invokeAndKeepAlive(map);
                if (UniWebSocketModule.this.webSocketEventListenerMap != null) {
                    UniWebSocketModule.this.webSocketEventListenerMap.remove(this.id);
                }
            }
        }

        @Override // com.taobao.weex.appfram.websocket.IWebSocketAdapter.EventListener
        public void onError(String str) {
            if (this.onError != null) {
                HashMap map = new HashMap(2);
                map.put("id", this.id);
                map.put("data", str);
                this.onError.invokeAndKeepAlive(map);
                if (UniWebSocketModule.this.webSocketEventListenerMap != null) {
                    UniWebSocketModule.this.webSocketEventListenerMap.remove(this.id);
                }
            }
        }

        @Override // com.taobao.weex.appfram.websocket.IWebSocketAdapter.EventListener
        public void onMessage(String str) {
            if (this.onMessage != null) {
                HashMap map = new HashMap(2);
                map.put("id", this.id);
                try {
                    JSONObject object = JSON.parseObject(str);
                    if (object.containsKey("@type") && object.containsKey("base64")) {
                        map.put("data", object);
                    } else {
                        map.put("data", str);
                    }
                } catch (Exception unused) {
                    map.put("data", str);
                }
                this.onMessage.invokeAndKeepAlive(map);
            }
        }

        @Override // com.taobao.weex.appfram.websocket.IWebSocketAdapter.EventListener
        public void onOpen() {
            if (this.onOpen != null) {
                HashMap map = new HashMap();
                map.put("id", this.id);
                this.onOpen.invokeAndKeepAlive(map);
            }
        }
    }

    public UniWebSocketModule() {
        WXLogUtils.e(TAG, "create new instance");
    }

    @JSMethod(uiThread = false)
    public void WebSocket(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject object = JSON.parseObject(new String(str.getBytes(), "utf-8"));
            String string = object.getString("id");
            String string2 = object.getString("url");
            String string3 = object.getString("protocol");
            String string4 = object.getString(WXBasicComponentType.HEADER);
            if (this.webSocketAdapterMap == null) {
                this.webSocketAdapterMap = new HashMap();
                this.webSocketEventListenerMap = new HashMap();
            }
            if (this.webSocketAdapterMap.get(string) == null || this.webSocketEventListenerMap.get(string) == null) {
                if (this.webSocketAdapterMap.get(string) != null && this.webSocketEventListenerMap.get(string) == null) {
                    this.webSocketAdapterMap.remove(string);
                }
                IWebSocketAdapter wXWebSocketAdapter = this.mWXSDKInstance.getWXWebSocketAdapter();
                WebSocketEventListener webSocketEventListener = new WebSocketEventListener(string);
                webSocketEventListener.onOpen = this.callback_onopen;
                webSocketEventListener.onMessage = this.callback_onmessage;
                webSocketEventListener.onClose = this.callback_onclose;
                webSocketEventListener.onError = this.callback_onerror;
                wXWebSocketAdapter.connect(string2, string3, string4, webSocketEventListener);
                this.webSocketEventListenerMap.put(string, webSocketEventListener);
                this.currentId = string;
                this.webSocketAdapterMap.put(string, wXWebSocketAdapter);
            }
        } catch (Exception e) {
            WXLogUtils.e("[UniWebSocketModule] alert param parse error ", e);
        }
    }

    @JSMethod(uiThread = false)
    public void close(String str) throws NumberFormatException {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject object = JSON.parseObject(new String(str.getBytes(), "utf-8"));
            String string = object.getString("id");
            String string2 = object.getString("code");
            String string3 = object.getString(KEY_REASON);
            int code = WebSocketCloseCodes.CLOSE_NORMAL.getCode();
            if (string2 != null) {
                try {
                    code = Integer.parseInt(string2);
                } catch (NumberFormatException unused) {
                }
            }
            this.webSocketAdapterMap.get(string).close(code, string3);
        } catch (Exception e) {
            WXLogUtils.e("[UniWebSocketModule] alert param parse error ", e);
        }
    }

    @Override // com.taobao.weex.common.Destroyable
    public void destroy() {
        Runnable runnable = new Runnable() { // from class: io.dcloud.feature.weex_websocket.UniWebSocketModule.1
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                WXLogUtils.w(UniWebSocketModule.TAG, "close session with instance id " + UniWebSocketModule.this.mWXSDKInstance.getInstanceId());
                if (UniWebSocketModule.this.webSocketAdapterMap != null) {
                    Iterator it = UniWebSocketModule.this.webSocketAdapterMap.entrySet().iterator();
                    while (it.hasNext()) {
                        ((IWebSocketAdapter) ((Map.Entry) it.next()).getValue()).destroy();
                    }
                    UniWebSocketModule.this.webSocketAdapterMap.clear();
                    UniWebSocketModule.this.webSocketAdapterMap = null;
                }
                if (UniWebSocketModule.this.webSocketEventListenerMap != null) {
                    UniWebSocketModule.this.webSocketEventListenerMap.clear();
                }
                UniWebSocketModule.this.webSocketEventListenerMap = null;
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
        this.callback_onclose = jSCallback;
        Map<String, WebSocketEventListener> map = this.webSocketEventListenerMap;
        if (map != null) {
            Iterator<Map.Entry<String, WebSocketEventListener>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                it.next().getValue().onClose = this.callback_onclose;
            }
        }
    }

    @JSMethod(uiThread = false)
    public void onerror(JSCallback jSCallback) {
        this.callback_onerror = jSCallback;
        Map<String, WebSocketEventListener> map = this.webSocketEventListenerMap;
        if (map != null) {
            Iterator<Map.Entry<String, WebSocketEventListener>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                it.next().getValue().onError = this.callback_onerror;
            }
        }
    }

    @JSMethod(uiThread = false)
    public void onmessage(JSCallback jSCallback) {
        this.callback_onmessage = jSCallback;
        Map<String, WebSocketEventListener> map = this.webSocketEventListenerMap;
        if (map != null) {
            Iterator<Map.Entry<String, WebSocketEventListener>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                it.next().getValue().onMessage = this.callback_onmessage;
            }
        }
    }

    @JSMethod(uiThread = false)
    public void onopen(JSCallback jSCallback) {
        this.callback_onopen = jSCallback;
        Map<String, WebSocketEventListener> map = this.webSocketEventListenerMap;
        if (map != null) {
            Iterator<Map.Entry<String, WebSocketEventListener>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                it.next().getValue().onOpen = this.callback_onopen;
            }
        }
    }

    @JSMethod(uiThread = false)
    public void send(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject object = JSON.parseObject(new String(str.getBytes(), "utf-8"));
            String string = object.getString("id");
            this.webSocketAdapterMap.get(string).send(object.getString("data"));
        } catch (Exception e) {
            WXLogUtils.e("[UniWebSocketModule] alert param parse error ", e);
        }
    }
}
