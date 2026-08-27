package com.taobao.weex.ui.action;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.ui.component.WXComponent;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class GraphicActionRenderSuccess extends BasicGraphicAction {
    public GraphicActionRenderSuccess(WXSDKInstance wXSDKInstance) {
        super(wXSDKInstance, "");
    }

    @Override // com.taobao.weex.ui.action.IExecutable
    public void executeAction() {
        int layoutWidth;
        int layoutHeight;
        WXSDKInstance wXSDKIntance = getWXSDKIntance();
        if (wXSDKIntance == null || wXSDKIntance.getContext() == null) {
            return;
        }
        WXComponent rootComponent = wXSDKIntance.getRootComponent();
        if (rootComponent != null) {
            layoutWidth = (int) rootComponent.getLayoutWidth();
            layoutHeight = (int) rootComponent.getLayoutHeight();
        } else {
            layoutWidth = 0;
            layoutHeight = 0;
        }
        wXSDKIntance.onRenderSuccess(layoutWidth, layoutHeight);
    }
}
