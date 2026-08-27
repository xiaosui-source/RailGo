package com.taobao.weex.ui.component;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.annotation.Component;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.view.IWebView;
import com.taobao.weex.ui.view.WXWebView;
import com.taobao.weex.utils.WXUtils;
import io.dcloud.common.constant.AbsoluteConst;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
@Component(lazyload = false)
/* loaded from: classes.dex */
public class WXWeb extends WXComponent {
    public static final String GO_BACK = "goBack";
    public static final String GO_FORWARD = "goForward";
    public static final String POST_MESSAGE = "postMessage";
    public static final String RELOAD = "reload";
    protected IWebView mWebView;

    @Deprecated
    public WXWeb(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, String str, boolean z, BasicComponentData basicComponentData) {
        this(wXSDKInstance, wXVContainer, z, basicComponentData);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fireEvent(String str, Object obj) {
        if (getEvents().contains("error")) {
            HashMap map = new HashMap();
            map.put("type", str);
            map.put("errorMsg", obj);
            fireEvent("error", (Map<String, Object>) map);
        }
    }

    private IWebView getWebView() {
        return this.mWebView;
    }

    private void loadDataWithBaseURL(String str) {
        getWebView().loadDataWithBaseURL(str);
    }

    protected void createWebView() {
        String str;
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
        this.mWebView = new WXWebView(getContext(), str);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void destroy() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.destroy();
        getWebView().destroy();
        this.mWebView = null;
    }

    @JSMethod
    public void goBack() {
        getWebView().goBack();
    }

    @JSMethod
    public void goForward() {
        getWebView().goForward();
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected View initComponentHostView(Context context) {
        this.mWebView.setOnErrorListener(new IWebView.OnErrorListener() { // from class: com.taobao.weex.ui.component.WXWeb.1
            @Override // com.taobao.weex.ui.view.IWebView.OnErrorListener
            public void onError(String str, Object obj) {
                WXWeb.this.fireEvent(str, obj);
            }
        });
        this.mWebView.setOnPageListener(new IWebView.OnPageListener() { // from class: com.taobao.weex.ui.component.WXWeb.2
            @Override // com.taobao.weex.ui.view.IWebView.OnPageListener
            public void onPageFinish(String str, boolean z, boolean z2) {
                if (WXWeb.this.getEvents().contains(Constants.Event.PAGEFINISH)) {
                    HashMap map = new HashMap();
                    map.put("url", str);
                    map.put("canGoBack", Boolean.valueOf(z));
                    map.put("canGoForward", Boolean.valueOf(z2));
                    WXWeb.this.fireEvent(Constants.Event.PAGEFINISH, (Map<String, Object>) map);
                }
            }

            @Override // com.taobao.weex.ui.view.IWebView.OnPageListener
            public void onPageStart(String str) {
                if (WXWeb.this.getEvents().contains(Constants.Event.PAGESTART)) {
                    HashMap map = new HashMap();
                    map.put("url", str);
                    WXWeb.this.fireEvent(Constants.Event.PAGESTART, (Map<String, Object>) map);
                }
            }

            @Override // com.taobao.weex.ui.view.IWebView.OnPageListener
            public void onReceivedTitle(String str) {
                if (WXWeb.this.getEvents().contains(Constants.Event.RECEIVEDTITLE)) {
                    HashMap map = new HashMap();
                    map.put(AbsoluteConst.JSON_KEY_TITLE, str);
                    WXWeb.this.fireEvent(Constants.Event.RECEIVEDTITLE, (Map<String, Object>) map);
                }
            }
        });
        this.mWebView.setOnMessageListener(new IWebView.OnMessageListener() { // from class: com.taobao.weex.ui.component.WXWeb.3
            @Override // com.taobao.weex.ui.view.IWebView.OnMessageListener
            public void onMessage(Map<String, Object> map) {
                WXWeb.this.fireEvent("message", map);
            }
        });
        return this.mWebView.getView();
    }

    protected void loadUrl(String str) {
        getWebView().loadUrl(str);
    }

    @JSMethod
    public void postMessage(Object obj) {
        getWebView().postMessage(obj);
    }

    @JSMethod
    public void reload() {
        getWebView().reload();
    }

    public void setAction(String str, Object obj) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (str.equals(GO_BACK)) {
            goBack();
            return;
        }
        if (str.equals(GO_FORWARD)) {
            goForward();
        } else if (str.equals(RELOAD)) {
            reload();
        } else if (str.equals(POST_MESSAGE)) {
            postMessage(obj);
        }
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected boolean setProperty(String str, Object obj) {
        str.getClass();
        str.hashCode();
        switch (str) {
            case "source":
                String string = WXUtils.getString(obj, null);
                if (string != null) {
                    setSource(string);
                }
                return true;
            case "src":
                String string2 = WXUtils.getString(obj, null);
                if (string2 != null) {
                    setUrl(string2);
                }
                return true;
            case "show-loading":
                Boolean bool = WXUtils.getBoolean(obj, null);
                if (bool != null) {
                    setShowLoading(bool.booleanValue());
                }
                return true;
            default:
                return super.setProperty(str, obj);
        }
    }

    @WXComponentProp(name = Constants.Name.SHOW_LOADING)
    public void setShowLoading(boolean z) {
        getWebView().setShowLoading(z);
    }

    @WXComponentProp(name = "source")
    public void setSource(String str) {
        if (TextUtils.isEmpty(str) || getHostView() == null) {
            return;
        }
        loadDataWithBaseURL(str);
    }

    @WXComponentProp(name = "src")
    public void setUrl(String str) {
        if (TextUtils.isEmpty(str) || getHostView() == null || TextUtils.isEmpty(str)) {
            return;
        }
        loadUrl(getInstance().rewriteUri(Uri.parse(str), "web").toString());
    }

    public WXWeb(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, boolean z, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, z, basicComponentData);
        createWebView();
    }
}
