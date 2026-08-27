package com.taobao.weex.ui.action;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXVContainer;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class GraphicActionAppendTreeCreateFinish extends BasicGraphicAction {
    WXComponent component;

    public GraphicActionAppendTreeCreateFinish(WXSDKInstance wXSDKInstance, String str) {
        super(wXSDKInstance, str);
        WXComponent wXComponent = WXSDKManager.getInstance().getWXRenderManager().getWXComponent(getPageId(), str);
        this.component = wXComponent;
        if (wXComponent == null || !(wXComponent instanceof WXVContainer)) {
            return;
        }
        ((WXVContainer) wXComponent).appendTreeCreateFinish();
    }

    @Override // com.taobao.weex.ui.action.IExecutable
    public void executeAction() {
    }
}
