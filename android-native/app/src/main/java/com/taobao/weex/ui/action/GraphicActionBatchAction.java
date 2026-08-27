package com.taobao.weex.ui.action;

import com.taobao.weex.WXSDKInstance;
import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class GraphicActionBatchAction extends BasicGraphicAction {
    private List<BasicGraphicAction> mActions;

    public GraphicActionBatchAction(WXSDKInstance wXSDKInstance, String str, List<BasicGraphicAction> list) {
        super(wXSDKInstance, str);
        this.mActions = new ArrayList(list);
    }

    @Override // com.taobao.weex.ui.action.IExecutable
    public void executeAction() {
        for (int i = 0; i < this.mActions.size(); i++) {
            this.mActions.get(i).executeAction();
        }
    }
}
