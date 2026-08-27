package com.taobao.weex.ui.component;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.Component;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.list.WXCell;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
@Component(lazyload = false)
/* loaded from: classes.dex */
public class WXHeader extends WXCell {
    @Deprecated
    public WXHeader(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, String str, boolean z, BasicComponentData basicComponentData) {
        this(wXSDKInstance, wXVContainer, z, basicComponentData);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public boolean canRecycled() {
        return !isSticky();
    }

    @Override // com.taobao.weex.ui.component.list.WXCell, com.taobao.weex.ui.component.WXComponent
    public boolean isLazy() {
        return false;
    }

    public WXHeader(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, boolean z, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, z, basicComponentData);
        String componentType = wXVContainer.getComponentType();
        if (WXBasicComponentType.LIST.equals(componentType) || WXBasicComponentType.RECYCLE_LIST.equals(componentType)) {
            getStyles().put("position", "sticky");
            setSticky("sticky");
        }
    }
}
