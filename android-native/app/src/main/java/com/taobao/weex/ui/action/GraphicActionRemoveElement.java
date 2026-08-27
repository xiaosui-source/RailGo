package com.taobao.weex.ui.action;

import android.text.TextUtils;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXVContainer;
import java.lang.reflect.InvocationTargetException;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class GraphicActionRemoveElement extends BasicGraphicAction {
    public GraphicActionRemoveElement(WXSDKInstance wXSDKInstance, String str) {
        super(wXSDKInstance, str);
    }

    private void clearRegistryForComponent(WXComponent wXComponent) {
        WXComponent wXComponentUnregisterComponent = WXSDKManager.getInstance().getWXRenderManager().unregisterComponent(getPageId(), getRef());
        if (wXComponentUnregisterComponent != null) {
            wXComponentUnregisterComponent.removeAllEvent();
            wXComponentUnregisterComponent.removeStickyStyle();
        }
        if (wXComponent instanceof WXVContainer) {
            WXVContainer wXVContainer = (WXVContainer) wXComponent;
            for (int iChildCount = wXVContainer.childCount() - 1; iChildCount >= 0; iChildCount--) {
                clearRegistryForComponent(wXVContainer.getChild(iChildCount));
            }
        }
    }

    @Override // com.taobao.weex.ui.action.IExecutable
    public void executeAction() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        WXComponent wXComponent = WXSDKManager.getInstance().getWXRenderManager().getWXComponent(getPageId(), getRef());
        if (wXComponent == null || wXComponent.getParent() == null || wXComponent.getInstance() == null) {
            return;
        }
        clearRegistryForComponent(wXComponent);
        WXVContainer parent = wXComponent.getParent();
        if (wXComponent.getHostView() != null && !TextUtils.equals(wXComponent.getComponentType(), "video") && !TextUtils.equals(wXComponent.getComponentType(), "videoplus")) {
            wXComponent.getHostView().getLocationInWindow(new int[2]);
        }
        parent.remove(wXComponent, true);
    }
}
