package com.taobao.weex.performance;

import android.util.Log;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.WXErrorCode;
import com.taobao.weex.common.WXJSExceptionInfo;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.list.template.TemplateDom;
import com.taobao.weex.utils.WXUtils;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXAnalyzerDataTransfer {
    private static final String GROUP = "WXAnalyzer";
    public static final String INTERACTION_TAG = "wxInteractionAnalyzer";
    private static final String MODULE_ERROR = "WXError";
    private static final String MODULE_WX_APM = "wxapm";
    public static boolean isOpenPerformance = false;
    private static boolean sOpenInteractionLog;

    public static boolean isInteractionLogOpen() {
        return sOpenInteractionLog;
    }

    public static void switchInteractionLog(boolean z) {
        if (sOpenInteractionLog == z || !WXEnvironment.JsFrameworkInit) {
            return;
        }
        sOpenInteractionLog = z;
        WXBridgeManager.getInstance().registerCoreEnv("switchInteractionLog", String.valueOf(z));
    }

    public static void transferError(WXJSExceptionInfo wXJSExceptionInfo, String str) {
        List<IWXAnalyzer> wXAnalyzerList;
        WXSDKInstance sDKInstance;
        String string;
        if (!WXEnvironment.isApkDebugable() || (wXAnalyzerList = WXSDKManager.getInstance().getWXAnalyzerList()) == null || wXAnalyzerList.size() == 0 || (sDKInstance = WXSDKManager.getInstance().getSDKInstance(str)) == null) {
            return;
        }
        WXErrorCode errCode = wXJSExceptionInfo.getErrCode();
        try {
            string = new JSONObject().put("instanceId", str).put("url", sDKInstance.getBundleUrl()).put("errorCode", errCode.getErrorCode()).put("errorMsg", errCode.getErrorMsg()).put("errorGroup", errCode.getErrorGroup()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            string = "";
        }
        Iterator<IWXAnalyzer> it = wXAnalyzerList.iterator();
        while (it.hasNext()) {
            it.next().transfer(GROUP, MODULE_ERROR, errCode.getErrorType().toString(), string);
        }
    }

    public static void transferInteractionInfo(WXComponent wXComponent) {
        List<IWXAnalyzer> wXAnalyzerList;
        if (!isOpenPerformance || (wXAnalyzerList = WXSDKManager.getInstance().getWXAnalyzerList()) == null || wXAnalyzerList.size() == 0) {
            return;
        }
        try {
            String string = new JSONObject().put("renderOriginDiffTime", WXUtils.getFixUnixTime() - wXComponent.getInstance().getWXPerformance().renderUnixTimeOrigin).put("type", wXComponent.getComponentType()).put("ref", wXComponent.getRef()).put("style", wXComponent.getStyles()).put(TemplateDom.KEY_ATTRS, wXComponent.getAttrs()).toString();
            Iterator<IWXAnalyzer> it = wXAnalyzerList.iterator();
            while (it.hasNext()) {
                it.next().transfer(MODULE_WX_APM, wXComponent.getInstanceId(), "wxinteraction", string);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void transferPerformance(String str, String str2, String str3, Object obj) {
        WXSDKInstance wXSDKInstance;
        if (isOpenPerformance) {
            if (sOpenInteractionLog && "stage".equals(str2)) {
                Log.d(INTERACTION_TAG, "[client][stage]" + str + "," + str3 + "," + obj);
            }
            List<IWXAnalyzer> wXAnalyzerList = WXSDKManager.getInstance().getWXAnalyzerList();
            if (wXAnalyzerList == null || wXAnalyzerList.size() == 0 || (wXSDKInstance = WXSDKManager.getInstance().getAllInstanceMap().get(str)) == null) {
                return;
            }
            try {
                String string = new JSONObject().put(str3, obj).toString();
                Iterator<IWXAnalyzer> it = wXAnalyzerList.iterator();
                while (it.hasNext()) {
                    it.next().transfer(MODULE_WX_APM, wXSDKInstance.getInstanceId(), str2, string);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
