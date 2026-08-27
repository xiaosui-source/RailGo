package com.taobao.weex.ui.action;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.tracing.Stopwatch;
import com.taobao.weex.ui.component.WXComponent;
import io.dcloud.feature.uniapp.dom.AbsEvent;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class GraphicActionAddEvent extends BasicGraphicAction {
    private final String mEvent;

    public GraphicActionAddEvent(WXSDKInstance wXSDKInstance, String str, Object obj) {
        super(wXSDKInstance, str);
        this.mEvent = AbsEvent.getEventName(obj);
    }

    @Override // com.taobao.weex.ui.action.IExecutable
    public void executeAction() {
        WXComponent wXComponent = WXSDKManager.getInstance().getWXRenderManager().getWXComponent(getPageId(), getRef());
        if (wXComponent == null) {
            return;
        }
        Stopwatch.tick();
        if (!wXComponent.getEvents().contains(this.mEvent)) {
            wXComponent.getEvents().addEvent(this.mEvent);
        }
        wXComponent.addEvent(this.mEvent);
        Stopwatch.split("addEventToComponent");
    }
}
