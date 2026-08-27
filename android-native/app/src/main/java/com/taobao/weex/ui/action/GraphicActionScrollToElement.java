package com.taobao.weex.ui.action;

import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.ui.component.Scrollable;
import com.taobao.weex.ui.component.WXComponent;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class GraphicActionScrollToElement extends BasicGraphicAction {
    private final JSONObject mOptions;

    public GraphicActionScrollToElement(WXSDKInstance wXSDKInstance, String str, JSONObject jSONObject) {
        super(wXSDKInstance, str);
        this.mOptions = jSONObject;
    }

    @Override // com.taobao.weex.ui.action.IExecutable
    public void executeAction() {
        Scrollable parentScroller;
        WXComponent wXComponent = WXSDKManager.getInstance().getWXRenderManager().getWXComponent(getPageId(), getRef());
        if (wXComponent == null || (parentScroller = wXComponent.getParentScroller()) == null) {
            return;
        }
        parentScroller.scrollTo(wXComponent, this.mOptions);
    }
}
