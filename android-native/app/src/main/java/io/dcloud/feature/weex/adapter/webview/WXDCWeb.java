package io.dcloud.feature.weex.adapter.webview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.annotation.Component;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.ui.component.WXWeb;
import com.taobao.weex.ui.view.IWebView;
import com.taobao.weex.utils.WXResourceUtils;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaWebview;
import io.dcloud.common.adapter.ui.webview.WebViewFactory;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.feature.internal.sdk.SDK;
import io.dcloud.feature.weex.WeexInstanceMgr;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
@Component(lazyload = false)
/* loaded from: classes2.dex */
public class WXDCWeb extends WXWeb {
    IDCWebView mDCWebView;
    private JSONObject mWebStyles;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface OnDCMessageListener {
        void onMessage(Map<String, Object> map, int i);
    }

    public WXDCWeb(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, boolean z, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, z, basicComponentData);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fireEvent(String str, Object obj) {
        if (getEvents().contains("error")) {
            HashMap map = new HashMap();
            map.put("type", str);
            map.put("errorMsg", obj);
            Map<String, Object> map2 = new HashMap<>();
            map2.put("detail", map);
            fireEvent("error", map2);
        }
    }

    @Override // com.taobao.weex.ui.component.WXWeb
    protected void createWebView() {
        String str;
        Object objNewInstance;
        String scheme;
        String authority;
        try {
            Uri uri = Uri.parse(WXSDKManager.getInstance().getSDKInstance(getInstanceId()).getBundleUrl());
            scheme = uri.getScheme();
            authority = uri.getAuthority();
        } catch (Exception unused) {
        }
        if (TextUtils.isEmpty(scheme) || TextUtils.isEmpty(authority)) {
            str = null;
        } else {
            str = scheme + "://" + authority;
        }
        if (WebViewFactory.isIsOtherInitSuccess() && (objNewInstance = PlatformUtil.newInstance("io.dcloud.feature.x5.DCWXX5WebView", new Class[]{Context.class, String.class, WXDCWeb.class}, new Object[]{getInstance().getUIContext(), str, this})) != null && (objNewInstance instanceof IWebView)) {
            IDCWebView iDCWebView = (IDCWebView) objNewInstance;
            this.mDCWebView = iDCWebView;
            this.mWebView = iDCWebView;
        }
        if (this.mWebView == null) {
            DCWXWebView dCWXWebView = new DCWXWebView(getInstance().getUIContext(), str, this);
            this.mDCWebView = dCWXWebView;
            this.mWebView = dCWXWebView;
        }
    }

    @Override // com.taobao.weex.ui.component.WXWeb, com.taobao.weex.ui.component.WXComponent
    public void destroy() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.destroy();
        this.mDCWebView = null;
    }

    @JSMethod
    public void evalJS(String str) {
        evalJs(str);
    }

    @JSMethod
    public void evalJs(String str) {
        if (this.mWebView != null) {
            if (!str.startsWith("javascript:(function(){")) {
                str = "javascript:(function(){" + str + ";})();";
            }
            this.mWebView.loadUrl(str);
        }
    }

    public JSONObject getWebStyles() {
        if (this.mWebStyles == null) {
            JSONObject jSONObject = new JSONObject();
            this.mWebStyles = jSONObject;
            jSONObject.put("isProgress", (Object) Boolean.TRUE);
        }
        return this.mWebStyles;
    }

    @Override // com.taobao.weex.ui.component.WXWeb, com.taobao.weex.ui.component.WXComponent
    protected View initComponentHostView(Context context) {
        PlatformUtil.invokeMethod(this.mWebView, "setOnDCMessageListener", new Class[]{OnDCMessageListener.class}, new OnDCMessageListener() { // from class: io.dcloud.feature.weex.adapter.webview.WXDCWeb.1
            @Override // io.dcloud.feature.weex.adapter.webview.WXDCWeb.OnDCMessageListener
            public void onMessage(Map<String, Object> map, int i) {
                WXSDKInstance wXSDKInstanceFindWXSDKInstance;
                if (i == 1) {
                    WXDCWeb.this.fireEvent("onPostMessage", map);
                } else if (i == 2 && (wXSDKInstanceFindWXSDKInstance = WeexInstanceMgr.self().findWXSDKInstance("__uniapp__service")) != null) {
                    map.put("ref", WXDCWeb.this.getRef());
                    map.put("id", WXDCWeb.this.getInstance().getInstanceId());
                    wXSDKInstanceFindWXSDKInstance.fireGlobalEventCallback("WebviewPostMessage", map);
                }
            }
        });
        this.mWebView.setOnErrorListener(new IWebView.OnErrorListener() { // from class: io.dcloud.feature.weex.adapter.webview.WXDCWeb.2
            @Override // com.taobao.weex.ui.view.IWebView.OnErrorListener
            public void onError(String str, Object obj) {
                WXDCWeb.this.fireEvent(str, obj);
            }
        });
        this.mWebView.setOnPageListener(new IWebView.OnPageListener() { // from class: io.dcloud.feature.weex.adapter.webview.WXDCWeb.3
            @Override // com.taobao.weex.ui.view.IWebView.OnPageListener
            public void onPageFinish(String str, boolean z, boolean z2) {
                if (WXDCWeb.this.getEvents().contains(Constants.Event.PAGEFINISH)) {
                    HashMap map = new HashMap();
                    map.put("url", str);
                    map.put("canGoBack", Boolean.valueOf(z));
                    map.put("canGoForward", Boolean.valueOf(z2));
                    HashMap map2 = new HashMap();
                    map2.put("detail", map);
                    WXDCWeb.this.fireEvent(Constants.Event.PAGEFINISH, (Map<String, Object>) map2);
                }
            }

            @Override // com.taobao.weex.ui.view.IWebView.OnPageListener
            public void onPageStart(String str) {
                if (WXDCWeb.this.getEvents().contains(Constants.Event.PAGESTART)) {
                    HashMap map = new HashMap();
                    map.put("url", str);
                    HashMap map2 = new HashMap();
                    map2.put("detail", map);
                    WXDCWeb.this.fireEvent(Constants.Event.PAGESTART, (Map<String, Object>) map2);
                }
            }

            @Override // com.taobao.weex.ui.view.IWebView.OnPageListener
            public void onReceivedTitle(String str) {
                if (WXDCWeb.this.getEvents().contains(Constants.Event.RECEIVEDTITLE)) {
                    HashMap map = new HashMap();
                    map.put(AbsoluteConst.JSON_KEY_TITLE, str);
                    HashMap map2 = new HashMap();
                    map2.put("detail", map);
                    WXDCWeb.this.fireEvent(Constants.Event.RECEIVEDTITLE, (Map<String, Object>) map2);
                }
            }
        });
        this.mWebView.setOnMessageListener(new IWebView.OnMessageListener() { // from class: io.dcloud.feature.weex.adapter.webview.WXDCWeb.4
            @Override // com.taobao.weex.ui.view.IWebView.OnMessageListener
            public void onMessage(Map<String, Object> map) {
                HashMap map2 = new HashMap();
                map2.put("detail", map);
                WXDCWeb.this.fireEvent("message", (Map<String, Object>) map2);
            }
        });
        View view = this.mWebView.getView();
        if (!TextUtils.isEmpty(AdaWebview.sCustomUserAgent)) {
            this.mDCWebView.setUserAgent(AdaWebview.sCustomUserAgent, true);
            return view;
        }
        IWebview iWebviewFindWebview = WeexInstanceMgr.self().findWebview(getInstance());
        if (iWebviewFindWebview != null) {
            this.mDCWebView.setUserAgent(iWebviewFindWebview.obtainApp().obtainConfigProperty(IApp.ConfigProperty.CONFIG_USER_AGENT), !Boolean.parseBoolean(iWebviewFindWebview.obtainApp().obtainConfigProperty(IApp.ConfigProperty.CONFIG_CONCATENATE)));
        }
        return view;
    }

    @Override // com.taobao.weex.ui.component.WXWeb
    protected void loadUrl(String str) {
        if (!TextUtils.isEmpty(str) && str.startsWith("asset:///")) {
            str = str.replace("asset:///", SDK.ANDROID_ASSET);
        }
        super.loadUrl(str);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void onActivityResult(int i, int i2, Intent intent) {
        IWebView iWebView = this.mWebView;
        if (iWebView != null) {
            iWebView.onActivityResult(i, i2, intent);
        }
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void setBackgroundColor(String str) {
        super.setBackgroundColor(str);
        if (TextUtils.isEmpty(str) || this.mDCWebView == null) {
            return;
        }
        int color = WXResourceUtils.getColor(str);
        if (this.mDCWebView.getWebView() != null) {
            this.mDCWebView.getWebView().setBackgroundColor(color);
        }
    }

    @WXComponentProp(name = "webviewStyles")
    public void webviewStyles(String str) {
        if (this.mWebStyles == null) {
            this.mWebStyles = new JSONObject();
        }
        JSONObject object = JSON.parseObject(str);
        if (object == null || !object.containsKey("progress")) {
            return;
        }
        Object obj = object.get("progress");
        if (obj instanceof Boolean) {
            this.mWebStyles.put("isProgress", obj);
            return;
        }
        if (obj instanceof JSONObject) {
            JSONObject jSONObject = (JSONObject) obj;
            this.mWebStyles.put("isProgress", (Object) Boolean.TRUE);
            if (jSONObject.containsKey("color")) {
                this.mWebStyles.put("progressColor", (Object) jSONObject.getString("color"));
            }
        }
    }
}
