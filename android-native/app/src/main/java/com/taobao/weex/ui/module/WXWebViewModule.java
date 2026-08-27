package com.taobao.weex.ui.module;

import com.taobao.weex.WXSDKManager;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXWeb;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXWebViewModule extends WXModule {

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    private enum Action {
        reload,
        goBack,
        goForward,
        postMessage
    }

    private void action(Action action, String str, Object obj) {
        WXComponent wXComponent = WXSDKManager.getInstance().getWXRenderManager().getWXComponent(this.mWXSDKInstance.getInstanceId(), str);
        if (wXComponent instanceof WXWeb) {
            ((WXWeb) wXComponent).setAction(action.name(), obj);
        }
    }

    @JSMethod(uiThread = true)
    public void goBack(String str) {
        action(Action.goBack, str);
    }

    @JSMethod(uiThread = true)
    public void goForward(String str) {
        action(Action.goForward, str);
    }

    @JSMethod(uiThread = true)
    public void postMessage(String str, Object obj) {
        action(Action.postMessage, str, obj);
    }

    @JSMethod(uiThread = true)
    public void reload(String str) {
        action(Action.reload, str);
    }

    private void action(Action action, String str) {
        action(action, str, null);
    }
}
