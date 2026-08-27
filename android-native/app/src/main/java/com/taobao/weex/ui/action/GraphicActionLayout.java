package com.taobao.weex.ui.action;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.ui.component.WXComponent;
import java.lang.reflect.InvocationTargetException;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class GraphicActionLayout extends BasicGraphicAction {
    private final boolean mIsLayoutRTL;
    private final GraphicPosition mLayoutPosition;
    private final GraphicSize mLayoutSize;

    public GraphicActionLayout(WXSDKInstance wXSDKInstance, String str, GraphicPosition graphicPosition, GraphicSize graphicSize, boolean z) {
        super(wXSDKInstance, str);
        this.mLayoutPosition = graphicPosition;
        this.mLayoutSize = graphicSize;
        this.mIsLayoutRTL = z;
    }

    @Override // com.taobao.weex.ui.action.IExecutable
    public void executeAction() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        WXComponent wXComponent = WXSDKManager.getInstance().getWXRenderManager().getWXComponent(getPageId(), getRef());
        if (wXComponent == null) {
            return;
        }
        wXComponent.setIsLayoutRTL(this.mIsLayoutRTL);
        wXComponent.setDemission(this.mLayoutSize, this.mLayoutPosition);
        wXComponent.setSafeLayout(wXComponent);
        wXComponent.setPadding(wXComponent.getPadding(), wXComponent.getBorder());
    }
}
