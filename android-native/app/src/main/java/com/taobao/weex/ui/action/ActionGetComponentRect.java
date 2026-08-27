package com.taobao.weex.ui.action;

import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXUserTrackAdapter;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.bridge.SimpleJSCallback;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.utils.WXViewUtils;
import io.dcloud.common.constant.AbsoluteConst;
import java.util.HashMap;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class ActionGetComponentRect extends BasicGraphicAction {
    private final String mCallback;

    public ActionGetComponentRect(WXSDKInstance wXSDKInstance, String str, String str2) {
        super(wXSDKInstance, str);
        this.mCallback = str2;
    }

    private void callbackViewport(WXSDKInstance wXSDKInstance, JSCallback jSCallback) {
        View containerView = wXSDKInstance.getContainerView();
        if (containerView == null) {
            HashMap map = new HashMap();
            map.put("result", Boolean.FALSE);
            map.put(IWXUserTrackAdapter.MONITOR_ERROR_MSG, "Component does not exist");
            jSCallback.invoke(map);
            return;
        }
        HashMap map2 = new HashMap();
        HashMap map3 = new HashMap();
        wXSDKInstance.getContainerView().getLocationOnScreen(new int[2]);
        float instanceViewPortWidthWithFloat = wXSDKInstance.getInstanceViewPortWidthWithFloat();
        map3.put("left", Float.valueOf(0.0f));
        map3.put("top", Float.valueOf(0.0f));
        map3.put("right", Float.valueOf(getWebPxValue(containerView.getWidth(), instanceViewPortWidthWithFloat)));
        map3.put("bottom", Float.valueOf(getWebPxValue(containerView.getHeight(), instanceViewPortWidthWithFloat)));
        map3.put("width", Float.valueOf(getWebPxValue(containerView.getWidth(), instanceViewPortWidthWithFloat)));
        map3.put("height", Float.valueOf(getWebPxValue(containerView.getHeight(), instanceViewPortWidthWithFloat)));
        map2.put(AbsoluteConst.JSON_KEY_SIZE, map3);
        map2.put("result", Boolean.TRUE);
        jSCallback.invoke(map2);
    }

    private float getWebPxValue(int i, float f) {
        return WXViewUtils.getWebPxByWidth(i, f);
    }

    @Override // com.taobao.weex.ui.action.IExecutable
    public void executeAction() {
        WXSDKInstance wXSDKIntance = getWXSDKIntance();
        if (wXSDKIntance == null || wXSDKIntance.isDestroy()) {
            return;
        }
        JSCallback simpleJSCallback = new SimpleJSCallback(wXSDKIntance.getInstanceId(), this.mCallback);
        if (TextUtils.isEmpty(getRef())) {
            HashMap map = new HashMap();
            map.put("result", Boolean.FALSE);
            map.put(IWXUserTrackAdapter.MONITOR_ERROR_MSG, "Illegal parameter");
            simpleJSCallback.invoke(map);
            return;
        }
        if ("viewport".equalsIgnoreCase(getRef())) {
            callbackViewport(wXSDKIntance, simpleJSCallback);
            return;
        }
        WXComponent wXComponent = WXSDKManager.getInstance().getWXRenderManager().getWXComponent(getPageId(), getRef());
        if (wXComponent == null) {
            return;
        }
        HashMap map2 = new HashMap();
        float instanceViewPortWidthWithFloat = wXSDKIntance.getInstanceViewPortWidthWithFloat();
        HashMap map3 = new HashMap();
        Rect componentSize = wXComponent.getComponentSize();
        map3.put("width", Float.valueOf(getWebPxValue(componentSize.width(), instanceViewPortWidthWithFloat)));
        map3.put("height", Float.valueOf(getWebPxValue(componentSize.height(), instanceViewPortWidthWithFloat)));
        map3.put("bottom", Float.valueOf(getWebPxValue(componentSize.bottom, instanceViewPortWidthWithFloat)));
        map3.put("left", Float.valueOf(getWebPxValue(componentSize.left, instanceViewPortWidthWithFloat)));
        map3.put("right", Float.valueOf(getWebPxValue(componentSize.right, instanceViewPortWidthWithFloat)));
        map3.put("top", Float.valueOf(getWebPxValue(componentSize.top, instanceViewPortWidthWithFloat)));
        map2.put(AbsoluteConst.JSON_KEY_SIZE, map3);
        map2.put("result", Boolean.TRUE);
        simpleJSCallback.invoke(map2);
    }
}
