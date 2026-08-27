package com.taobao.weex.ui.action;

import android.text.TextUtils;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXUserTrackAdapter;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.bridge.SimpleJSCallback;
import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.list.template.jni.NativeRenderObjectUtils;
import com.taobao.weex.utils.WXViewUtils;
import java.util.HashMap;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class ActionGetLayoutDirection extends BasicGraphicAction {
    private final String mCallback;

    public ActionGetLayoutDirection(WXSDKInstance wXSDKInstance, String str, String str2) {
        super(wXSDKInstance, str);
        this.mCallback = str2;
    }

    private void callbackViewport(WXSDKInstance wXSDKInstance, JSCallback jSCallback) {
        if (wXSDKInstance.getContainerView() != null) {
            HashMap map = new HashMap();
            map.put("direction", "ltr");
            map.put("result", Boolean.TRUE);
            jSCallback.invoke(map);
            return;
        }
        HashMap map2 = new HashMap();
        map2.put("result", Boolean.FALSE);
        map2.put(IWXUserTrackAdapter.MONITOR_ERROR_MSG, "Component does not exist");
        jSCallback.invoke(map2);
    }

    private float getWebPxValue(int i, int i2) {
        return WXViewUtils.getWebPxByWidth(i, i2);
    }

    @Override // com.taobao.weex.ui.action.IExecutable
    public void executeAction() {
        Object obj;
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
        int iNativeRenderObjectGetLayoutDirectionFromPathNode = NativeRenderObjectUtils.nativeRenderObjectGetLayoutDirectionFromPathNode(wXComponent.getRenderObjectPtr());
        if (iNativeRenderObjectGetLayoutDirectionFromPathNode != 0) {
            obj = "ltr";
            if (iNativeRenderObjectGetLayoutDirectionFromPathNode != 1 && iNativeRenderObjectGetLayoutDirectionFromPathNode == 2) {
                obj = Constants.Name.RTL;
            }
        } else {
            obj = "inherit";
        }
        simpleJSCallback.invoke(obj);
    }
}
