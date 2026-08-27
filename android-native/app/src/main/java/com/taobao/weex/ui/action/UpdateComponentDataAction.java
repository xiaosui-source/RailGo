package com.taobao.weex.ui.action;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.bridge.SimpleJSCallback;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.list.template.CellDataManager;
import com.taobao.weex.ui.component.list.template.WXRecyclerTemplateList;
import com.taobao.weex.utils.WXLogUtils;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class UpdateComponentDataAction extends BasicGraphicAction {
    private String callback;
    private JSONObject data;
    private String virtualComponentId;

    public UpdateComponentDataAction(WXSDKInstance wXSDKInstance, String str, JSONObject jSONObject, String str2) {
        super(wXSDKInstance, CellDataManager.getListRef(str));
        this.virtualComponentId = str;
        this.data = jSONObject;
        this.callback = str2;
    }

    @Override // com.taobao.weex.ui.action.IExecutable
    public void executeAction() {
        if (TextUtils.isEmpty(getRef())) {
            WXLogUtils.e("wrong virtualComponentId split error " + this.virtualComponentId);
            return;
        }
        WXComponent wXComponent = WXSDKManager.getInstance().getWXRenderManager().getWXComponent(getPageId(), getRef());
        if (!(wXComponent instanceof WXRecyclerTemplateList)) {
            WXLogUtils.e("recycler-list wrong virtualComponentId " + this.virtualComponentId);
        } else {
            WXRecyclerTemplateList wXRecyclerTemplateList = (WXRecyclerTemplateList) wXComponent;
            wXRecyclerTemplateList.getCellDataManager().updateVirtualComponentData(this.virtualComponentId, this.data);
            wXRecyclerTemplateList.notifyUpdateList();
            new SimpleJSCallback(wXComponent.getInstanceId(), this.callback).invoke(Boolean.TRUE);
        }
    }
}
