package com.taobao.weex.ui.action;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.ui.component.richtext.WXRichText;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class GraphicActionRemoveChildFromRichtext extends BasicGraphicAction {
    private String parentRef;
    private String ref;
    private WXRichText richText;

    public GraphicActionRemoveChildFromRichtext(WXSDKInstance wXSDKInstance, String str, String str2, String str3) throws Throwable {
        super(wXSDKInstance, str3);
        this.ref = str;
        this.parentRef = str2;
        WXRichText wXRichText = (WXRichText) WXSDKManager.getInstance().getWXRenderManager().getWXComponent(wXSDKInstance.getInstanceId(), str3);
        this.richText = wXRichText;
        if (wXRichText != null) {
            wXRichText.removeChildNode(str2, str);
        }
    }

    @Override // com.taobao.weex.ui.action.IExecutable
    public void executeAction() {
    }
}
