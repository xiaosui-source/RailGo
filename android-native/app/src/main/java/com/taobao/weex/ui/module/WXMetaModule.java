package com.taobao.weex.ui.module;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dcloud.android.widget.toast.ToastCompat;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import io.dcloud.feature.weex.WeexInstanceMgr;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXMetaModule extends WXModule {
    public static final String DEVICE_WIDTH = "device-width";
    public static final String WIDTH = "width";

    @JSMethod(uiThread = false)
    public void getPageInfo(JSCallback jSCallback) {
        if (jSCallback == null) {
            return;
        }
        List<WXSDKInstance> allInstances = WXSDKManager.getInstance().getWXRenderManager().getAllInstances();
        HashMap map = new HashMap(4);
        for (WXSDKInstance wXSDKInstance : allInstances) {
            if (!TextUtils.isEmpty(wXSDKInstance.getBundleUrl())) {
                map.put(wXSDKInstance.getBundleUrl(), wXSDKInstance.getTemplateInfo());
            }
        }
        jSCallback.invoke(map);
    }

    @JSMethod(uiThread = false)
    public float getViewPort() {
        return this.mWXSDKInstance.getInstanceViewPortWidthWithFloat();
    }

    @JSMethod(uiThread = true)
    public void openLog(String str) {
        Application application = WXEnvironment.getApplication();
        if (application == null || (application.getApplicationInfo().flags & 2) == 0) {
            return;
        }
        if (WXUtils.getBoolean(str, Boolean.TRUE).booleanValue()) {
            WXEnvironment.setApkDebugable(true);
            WXSDKInstance wXSDKInstance = this.mWXSDKInstance;
            if (wXSDKInstance != null) {
                ToastCompat.makeText(wXSDKInstance.getContext(), (CharSequence) "log open success", 0).show();
                return;
            }
            return;
        }
        WXEnvironment.setApkDebugable(false);
        WXSDKInstance wXSDKInstance2 = this.mWXSDKInstance;
        if (wXSDKInstance2 != null) {
            ToastCompat.makeText(wXSDKInstance2.getContext(), (CharSequence) "log close success", 0).show();
        }
    }

    @JSMethod(uiThread = false)
    public void setViewport(String str) {
        if (WeexInstanceMgr.self().getComplier().equalsIgnoreCase("weex") && !TextUtils.isEmpty(str)) {
            try {
                JSONObject object = JSON.parseObject(URLDecoder.decode(str, "utf-8"));
                Context context = this.mWXSDKInstance.getContext();
                if (DEVICE_WIDTH.endsWith(object.getString("width"))) {
                    int screenWidth = (int) (WXViewUtils.getScreenWidth(context) / WXViewUtils.getScreenDensity(context));
                    this.mWXSDKInstance.setInstanceViewPortWidth(screenWidth);
                    WXLogUtils.d("[WXMetaModule] setViewport success[device-width]=" + screenWidth);
                    return;
                }
                int iIntValue = object.getInteger("width").intValue();
                if (iIntValue > 0) {
                    this.mWXSDKInstance.setInstanceViewPortWidth(iIntValue);
                }
                WXLogUtils.d("[WXMetaModule] setViewport success[width]=" + iIntValue);
            } catch (Exception e) {
                WXLogUtils.e("[WXMetaModule] alert param parse error ", e);
            }
        }
    }
}
