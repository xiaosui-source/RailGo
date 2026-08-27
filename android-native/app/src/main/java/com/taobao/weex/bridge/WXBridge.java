package com.taobao.weex.bridge;

import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXUserTrackAdapter;
import com.taobao.weex.common.IWXBridge;
import com.taobao.weex.common.WXErrorCode;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.dom.CSSShorthand;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.layout.ContentBoxMeasurement;
import com.taobao.weex.performance.WXStateRecord;
import com.taobao.weex.utils.WXExceptionUtils;
import com.taobao.weex.utils.WXJsonUtils;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXWsonJSONSwitch;
import com.taobao.weex.utils.tools.TimeCalculator;
import io.dcloud.feature.internal.sdk.SDK;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXBridge implements IWXBridge {
    public static final boolean MULTIPROCESS = true;
    public static final String TAG = "WXBridge";

    private native void nativeBindMeasurementToRenderObject(long j);

    private native String nativeDecrypt(String str, String str2, String str3, String str4);

    private native String nativeEncrypt(String str, String str2, String str3, String str4);

    private native int nativeExecJS(String str, String str2, String str3, WXJSObject[] wXJSObjectArr);

    private native int nativeExecJSService(String str);

    private native void nativeForceLayout(String str);

    private native int nativeInitFramework(String str, WXParams wXParams);

    private native int nativeInitFrameworkEnv(String str, WXParams wXParams, String str2, boolean z);

    private native void nativeMarkDirty(String str, String str2, boolean z);

    private native boolean nativeNotifyLayout(String str);

    private native void nativeOnInstanceClose(String str);

    private native String nativePreGetClientKey(String str, String str2, String str3);

    private native void nativeRefreshInstance(String str, String str2, String str3, WXJSObject[] wXJSObjectArr);

    private native void nativeRegisterCoreEnv(String str, String str2);

    private native void nativeReloadPageLayout(String str);

    private native void nativeRemoveInstanceRenderType(String str);

    private native void nativeResetWXBridge(Object obj, String str);

    private native void nativeSetDefaultHeightAndWidthIntoRootDom(String str, float f, float f2, boolean z, boolean z2);

    private native void nativeSetDeviceDisplay(String str, float f, float f2, float f3);

    private native void nativeSetDeviceDisplayOfPage(String str, float f, float f2);

    private native void nativeSetFlexDirectionDef(String str);

    private native void nativeSetInstanceRenderType(String str, String str2);

    private native void nativeSetLogType(float f, float f2);

    private native void nativeSetMargin(String str, String str2, int i, float f);

    private native void nativeSetPadding(String str, String str2, int i, float f);

    private native void nativeSetPageArgument(String str, String str2, String str3);

    private native void nativeSetPosition(String str, String str2, int i, float f);

    private native void nativeSetRenderContainerWrapContent(boolean z, String str);

    private native void nativeSetStyleHeight(String str, String str2, float f, boolean z);

    private native void nativeSetStyleWidth(String str, String str2, float f, boolean z);

    private native void nativeSetViewPortWidth(String str, float f);

    private native void nativeTakeHeapSnapshot(String str);

    private native void nativeUpdateInitFrameworkParams(String str, String str2, String str3);

    private native boolean nativeVerifyClientKeyPayload(String str, String str2, String str3);

    @Override // com.taobao.weex.common.IWXBridge
    public void bindMeasurementToRenderObject(long j) {
        nativeBindMeasurementToRenderObject(j);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int callAddChildToRichtext(String str, String str2, String str3, String str4, String str5, HashMap<String, String> map, HashMap<String, String> map2) {
        try {
            return WXBridgeManager.getInstance().callAddChildToRichtext(str, str2, str3, str4, str5, map, map2);
        } catch (Throwable th) {
            if (!WXEnvironment.isApkDebugable()) {
                return 1;
            }
            WXLogUtils.e(TAG, "callAddChildToRichtext throw exception:" + WXLogUtils.getStackTrace(th));
            return 1;
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int callAddElement(String str, String str2, String str3, int i, String str4, HashMap<String, String> map, HashMap<String, String> map2, HashSet<String> hashSet, float[] fArr, float[] fArr2, float[] fArr3, boolean z) {
        try {
            return WXBridgeManager.getInstance().callAddElement(str, str2, str3, i, str4, map, map2, hashSet, fArr, fArr2, fArr3, z);
        } catch (Throwable th) {
            if (!WXEnvironment.isApkDebugable()) {
                return 1;
            }
            th.printStackTrace();
            WXLogUtils.e(TAG, "callAddElement throw error:" + WXLogUtils.getStackTrace(th));
            return 1;
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int callAddEvent(String str, String str2, String str3) {
        try {
            return WXBridgeManager.getInstance().callAddEvent(str, str2, str3);
        } catch (Throwable th) {
            WXLogUtils.e(TAG, "callAddEvent throw exception:" + WXLogUtils.getStackTrace(th));
            return 1;
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int callAppendTreeCreateFinish(String str, String str2) {
        try {
            return WXBridgeManager.getInstance().callAppendTreeCreateFinish(str, str2);
        } catch (Throwable th) {
            WXLogUtils.e(TAG, "callAppendTreeCreateFinish throw exception:" + WXLogUtils.getStackTrace(th));
            return 1;
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void callBacthEnd(String str) {
        try {
            WXBridgeManager.getInstance().callBacthEnd(str);
        } catch (Throwable th) {
            WXLogUtils.e(TAG, "cal\n    return errorCode;\n  }lAddEvent throw exception:" + WXLogUtils.getStackTrace(th));
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void callBacthStart(String str) {
        try {
            WXBridgeManager.getInstance().callBacthStart(str);
        } catch (Throwable th) {
            WXLogUtils.e(TAG, "cal\n    return errorCode;\n  }lAddEvent throw exception:" + WXLogUtils.getStackTrace(th));
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int callCreateBody(String str, String str2, String str3, HashMap<String, String> map, HashMap<String, String> map2, HashSet<String> hashSet, float[] fArr, float[] fArr2, float[] fArr3) {
        try {
            return WXBridgeManager.getInstance().callCreateBody(str, str2, str3, map, map2, hashSet, fArr, fArr2, fArr3);
        } catch (Throwable th) {
            if (!WXEnvironment.isApkDebugable()) {
                return 1;
            }
            WXLogUtils.e(TAG, "callCreateBody throw exception:" + WXLogUtils.getStackTrace(th));
            return 1;
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int callCreateFinish(String str) {
        try {
            return WXBridgeManager.getInstance().callCreateFinish(str);
        } catch (Throwable th) {
            WXLogUtils.e(TAG, "callCreateFinish throw exception:" + WXLogUtils.getStackTrace(th));
            return 1;
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int callHasTransitionPros(String str, String str2, HashMap<String, String> map) {
        try {
            return WXBridgeManager.getInstance().callHasTransitionPros(str, str2, map);
        } catch (Throwable th) {
            if (!WXEnvironment.isApkDebugable()) {
                return 1;
            }
            WXLogUtils.e(TAG, "callHasTransitionPros throw exception:" + WXLogUtils.getStackTrace(th));
            return 1;
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int callLayout(String str, String str2, int i, int i2, int i3, int i4, int i5, int i6, boolean z, int i7) {
        try {
            return WXBridgeManager.getInstance().callLayout(str, str2, i, i2, i3, i4, i5, i6, z, i7);
        } catch (Throwable th) {
            if (!WXEnvironment.isApkDebugable()) {
                return 1;
            }
            WXLogUtils.e(TAG, "callLayout throw exception:" + WXLogUtils.getStackTrace(th));
            return 1;
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int callMoveElement(String str, String str2, String str3, int i) {
        try {
            return WXBridgeManager.getInstance().callMoveElement(str, str2, str3, i);
        } catch (Throwable th) {
            if (!WXEnvironment.isApkDebugable()) {
                return 1;
            }
            WXLogUtils.e(TAG, "callMoveElement throw exception:" + WXLogUtils.getStackTrace(th));
            return 1;
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int callNative(String str, byte[] bArr, String str2) {
        if (!"HeartBeat".equals(str2)) {
            return callNative(str, JSON.parseArray(new String(bArr)), str2);
        }
        Log.e("HeartBeat instanceId", str);
        WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
        if (sDKInstance == null) {
            return 1;
        }
        sDKInstance.createInstanceFuncHeartBeat();
        return 1;
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void callNativeComponent(String str, String str2, String str3, byte[] bArr, byte[] bArr2) {
        JSONArray jSONArray;
        WXStateRecord.getInstance().recordAction(str, "callNativeComponent:" + str3);
        try {
            WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
            if (bArr == null) {
                jSONArray = null;
            } else if (sDKInstance == null || !(sDKInstance.getRenderStrategy() == WXRenderStrategy.DATA_RENDER || sDKInstance.getRenderStrategy() == WXRenderStrategy.DATA_RENDER_BINARY)) {
                jSONArray = (JSONArray) WXWsonJSONSwitch.parseWsonOrJSON(bArr);
            } else {
                try {
                    jSONArray = (JSONArray) JSON.parse(new String(bArr, "UTF-8"));
                } catch (Exception unused) {
                    jSONArray = (JSONArray) WXWsonJSONSwitch.parseWsonOrJSON(bArr);
                }
            }
            WXBridgeManager.getInstance().callNativeComponent(str, str2, str3, jSONArray, WXWsonJSONSwitch.parseWsonOrJSON(bArr2));
        } catch (Exception e) {
            WXLogUtils.e(TAG, e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00bb A[Catch: Exception -> 0x0111, TryCatch #2 {Exception -> 0x0111, blocks: (B:3:0x0007, B:6:0x0032, B:8:0x003a, B:17:0x0064, B:36:0x00ae, B:38:0x00bb, B:40:0x00d9, B:42:0x00e1, B:53:0x0107, B:55:0x010c, B:19:0x006d, B:21:0x0077, B:23:0x0081, B:24:0x0086, B:26:0x008c, B:28:0x0094, B:30:0x009d, B:31:0x00a4, B:33:0x00a8, B:12:0x0050, B:13:0x0058, B:10:0x0042, B:45:0x00eb, B:47:0x00f1, B:49:0x00f9, B:51:0x00fc), top: B:63:0x0007, inners: #0, #1 }] */
    @Override // com.taobao.weex.common.IWXBridge
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object callNativeModule(java.lang.String r13, java.lang.String r14, java.lang.String r15, byte[] r16, byte[] r17) {
        /*
            Method dump skipped, instructions count: 286
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.bridge.WXBridge.callNativeModule(java.lang.String, java.lang.String, java.lang.String, byte[], byte[]):java.lang.Object");
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int callRefreshFinish(String str, byte[] bArr, String str2) {
        try {
            return WXBridgeManager.getInstance().callRefreshFinish(str, str2);
        } catch (Throwable th) {
            if (!WXEnvironment.isApkDebugable()) {
                return 1;
            }
            WXLogUtils.e(TAG, "callCreateFinish throw exception:" + WXLogUtils.getStackTrace(th));
            return 1;
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int callRemoveChildFromRichtext(String str, String str2, String str3, String str4) {
        try {
            return WXBridgeManager.getInstance().callRemoveChildFromRichtext(str, str2, str3, str4);
        } catch (Throwable th) {
            if (!WXEnvironment.isApkDebugable()) {
                return 1;
            }
            WXLogUtils.e(TAG, "callRemoveChildFromRichtext throw exception:" + WXLogUtils.getStackTrace(th));
            return 1;
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int callRemoveElement(String str, String str2) {
        try {
            return WXBridgeManager.getInstance().callRemoveElement(str, str2);
        } catch (Throwable th) {
            if (!WXEnvironment.isApkDebugable()) {
                return 1;
            }
            WXLogUtils.e(TAG, "callRemoveElement throw exception:" + WXLogUtils.getStackTrace(th));
            return 1;
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int callRemoveEvent(String str, String str2, String str3) {
        try {
            return WXBridgeManager.getInstance().callRemoveEvent(str, str2, str3);
        } catch (Throwable th) {
            if (!WXEnvironment.isApkDebugable()) {
                return 1;
            }
            WXLogUtils.e(TAG, "callRemoveEvent throw exception:" + WXLogUtils.getStackTrace(th));
            return 1;
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int callRenderSuccess(String str) {
        try {
            return WXBridgeManager.getInstance().callRenderSuccess(str);
        } catch (Throwable th) {
            WXLogUtils.e(TAG, "callCreateFinish throw exception:" + WXLogUtils.getStackTrace(th));
            return 1;
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int callUpdateAttrs(String str, String str2, HashMap<String, String> map) {
        try {
            return WXBridgeManager.getInstance().callUpdateAttrs(str, str2, map);
        } catch (Throwable th) {
            if (!WXEnvironment.isApkDebugable()) {
                return 1;
            }
            WXLogUtils.e(TAG, "callUpdateAttr throw exception:" + WXLogUtils.getStackTrace(th));
            return 1;
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int callUpdateFinish(String str, byte[] bArr, String str2) {
        try {
            return WXBridgeManager.getInstance().callUpdateFinish(str, str2);
        } catch (Throwable th) {
            if (!WXEnvironment.isApkDebugable()) {
                return 1;
            }
            WXLogUtils.e(TAG, "callCreateBody throw exception:" + WXLogUtils.getStackTrace(th));
            return 1;
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int callUpdateRichtextChildAttr(String str, String str2, HashMap<String, String> map, String str3, String str4) {
        try {
            return WXBridgeManager.getInstance().callUpdateRichtextChildAttr(str, str2, map, str3, str4);
        } catch (Throwable th) {
            if (!WXEnvironment.isApkDebugable()) {
                return 1;
            }
            WXLogUtils.e(TAG, "callUpdateRichtextChildAttr throw exception:" + WXLogUtils.getStackTrace(th));
            return 1;
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int callUpdateRichtextStyle(String str, String str2, HashMap<String, String> map, String str3, String str4) {
        try {
            return WXBridgeManager.getInstance().callUpdateRichtextStyle(str, str2, map, str3, str4);
        } catch (Throwable th) {
            if (!WXEnvironment.isApkDebugable()) {
                return 1;
            }
            WXLogUtils.e(TAG, "callUpdateRichtextStyle throw exception:" + WXLogUtils.getStackTrace(th));
            return 1;
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int callUpdateStyle(String str, String str2, HashMap<String, Object> map, HashMap<String, String> map2, HashMap<String, String> map3, HashMap<String, String> map4) {
        if (map != null) {
            try {
                if (map.isEmpty() && map2 != null && map2.isEmpty() && map3 != null && map3.isEmpty() && map4 != null && map4.isEmpty()) {
                    return 1;
                }
            } catch (Throwable th) {
                if (!WXEnvironment.isApkDebugable()) {
                    return 1;
                }
                WXLogUtils.e(TAG, "callUpdateStyle throw exception:" + WXLogUtils.getStackTrace(th));
                return 1;
            }
        }
        return WXBridgeManager.getInstance().callUpdateStyle(str, str2, map, map2, map3, map4);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void callVueExec(String str, final String str2, final String str3) {
        final WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
        if (sDKInstance == null || sDKInstance.isDestroy()) {
            return;
        }
        WXSDKManager.getInstance().postOnUiThread(new Runnable() { // from class: com.taobao.weex.bridge.WXBridge.1
            @Override // java.lang.Runnable
            public void run() {
                WXSDKManager.getInstance().getVueBridgeAdapter().exec(sDKInstance, str2, str3);
            }
        }, 0L);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public Object callVueExecSync(String str, String str2, String str3) {
        WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
        if (sDKInstance == null) {
            return new WXJSObject(null);
        }
        if (sDKInstance.isDestroy()) {
            return new WXJSObject(null);
        }
        String strExecSync = WXSDKManager.getInstance().getVueBridgeAdapter().execSync(sDKInstance, str2, str3);
        return strExecSync == null ? new WXJSObject(null) : new WXJSObject(3, WXJsonUtils.fromObjectToJSONString(strExecSync));
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int createInstanceContext(String str, String str2, String str3, WXJSObject[] wXJSObjectArr) {
        Log.e(TimeCalculator.TIMELINE_TAG, "createInstance :" + System.currentTimeMillis());
        WXStateRecord.getInstance().recordAction(str, "createInstanceContext:");
        return nativeCreateInstanceContext(str, str2, str3, wXJSObjectArr);
    }

    public String decrypt(String str, String str2, String str3, String str4) {
        return nativeDecrypt(str, str2, str3, str4);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int destoryInstance(String str, String str2, String str3, WXJSObject[] wXJSObjectArr) {
        WXStateRecord.getInstance().recordAction(str, "destoryInstance:");
        return nativeDestoryInstance(str, str2, str3, wXJSObjectArr);
    }

    public String encrypt(String str, String str2, String str3, String str4) {
        return nativeEncrypt(str, str2, str3, str4);
    }

    public String encryptGetClientKeyPayload(String str, String str2, String str3) {
        return nativePreGetClientKey(str, str2, str3);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int execJS(String str, String str2, String str3, WXJSObject[] wXJSObjectArr) {
        WXStateRecord.getInstance().recordAction(str, "execJS:" + str2 + "," + str3);
        return nativeExecJS(str, str2, str3, wXJSObjectArr);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public String execJSOnInstance(String str, String str2, int i) {
        WXStateRecord.getInstance().recordAction(str, "execJSOnInstance:" + i);
        return nativeExecJSOnInstance(str, str2, i);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int execJSService(String str) {
        WXStateRecord.getInstance().recordAction("execJSService", "execJSService:");
        return nativeExecJSService(str);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void execJSWithCallback(String str, String str2, String str3, WXJSObject[] wXJSObjectArr, ResultCallback resultCallback) {
        WXStateRecord.getInstance().recordAction(str, "execJSWithCallback:" + str2 + "," + str3);
        if (resultCallback == null) {
            execJS(str, str2, str3, wXJSObjectArr);
        }
        nativeExecJSWithCallback(str, str2, str3, wXJSObjectArr, ResultCallbackManager.generateCallbackId(resultCallback));
    }

    public void fireEventOnDataRenderNode(String str, String str2, String str3, String str4, String str5) {
        nativeFireEventOnDataRenderNode(str, str2, str3, str4, str5);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void forceLayout(String str) {
        nativeForceLayout(str);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public long[] getFirstScreenRenderTime(String str) {
        return nativeGetFirstScreenRenderTime(str);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public ContentBoxMeasurement getMeasurementFunc(String str, long j) {
        try {
            return WXBridgeManager.getInstance().getMeasurementFunc(str, j);
        } catch (Throwable th) {
            if (!WXEnvironment.isApkDebugable()) {
                return null;
            }
            WXLogUtils.e(TAG, "getMeasurementFunc throw exception:" + WXLogUtils.getStackTrace(th));
            return null;
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public long[] getRenderFinishTime(String str) {
        return nativeGetRenderFinishTime(str);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int initFramework(String str, WXParams wXParams) {
        return nativeInitFramework(str, wXParams);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int initFrameworkEnv(String str, WXParams wXParams, String str2, boolean z) {
        WXStateRecord.getInstance().recordAction("", "nativeInitFrameworkEnv:");
        return nativeInitFrameworkEnv(str, wXParams, str2, z);
    }

    public void invokeCallbackOnDataRender(String str, String str2, String str3, boolean z) {
        nativeInvokeCallbackOnDataRender(str, str2, str3, z);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void markDirty(String str, String str2, boolean z) {
        nativeMarkDirty(str, str2, z);
    }

    public native int nativeCreateInstanceContext(String str, String str2, String str3, WXJSObject[] wXJSObjectArr);

    public native int nativeDestoryInstance(String str, String str2, String str3, WXJSObject[] wXJSObjectArr);

    public native String nativeDumpIpcPageQueueInfo();

    public native String nativeExecJSOnInstance(String str, String str2, int i);

    public native void nativeExecJSWithCallback(String str, String str2, String str3, WXJSObject[] wXJSObjectArr, long j);

    public native byte[] nativeExecJSWithResult(String str, String str2, String str3, WXJSObject[] wXJSObjectArr);

    public native void nativeFireEventOnDataRenderNode(String str, String str2, String str3, String str4, String str5);

    public native long[] nativeGetFirstScreenRenderTime(String str);

    public native long[] nativeGetRenderFinishTime(String str);

    public native void nativeInvokeCallbackOnDataRender(String str, String str2, String str3, boolean z);

    public native void nativeOnInteractionTimeUpdate(String str);

    public native void nativeRegisterComponentOnDataRenderNode(String str);

    public native void nativeRegisterModuleOnDataRenderNode(String str);

    public native void nativeUpdateGlobalConfig(String str);

    @Override // com.taobao.weex.common.IWXBridge
    public boolean notifyLayout(String str) {
        return nativeNotifyLayout(str);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void onInstanceClose(String str) {
        nativeOnInstanceClose(str);
    }

    public void onNativePerformanceDataUpdate(String str, Map<String, String> map) {
        WXSDKInstance wXSDKInstance;
        if (TextUtils.isEmpty(str) || map == null || map.size() < 1 || (wXSDKInstance = WXSDKManager.getInstance().getAllInstanceMap().get(str)) == null || wXSDKInstance.getApmForInstance() == null) {
            return;
        }
        wXSDKInstance.getApmForInstance().updateNativePerformanceData(map);
    }

    public void onReceivedResult(long j, byte[] bArr) {
        WXStateRecord.getInstance().recordAction("onReceivedResult", SDK.UNIMP_EVENT_CALLBACKID + j);
        ResultCallback resultCallbackRemoveCallbackById = ResultCallbackManager.removeCallbackById(j);
        if (resultCallbackRemoveCallbackById != null) {
            resultCallbackRemoveCallbackById.onReceiveResult(bArr);
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void refreshInstance(String str, String str2, String str3, WXJSObject[] wXJSObjectArr) {
        WXStateRecord.getInstance().recordAction(str, "refreshInstance:" + str2 + "," + str3);
        nativeRefreshInstance(str, str2, str3, wXJSObjectArr);
    }

    public void registerComponentOnDataRenderNode(String str) {
        nativeRegisterComponentOnDataRenderNode(str);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void registerCoreEnv(String str, String str2) {
        nativeRegisterCoreEnv(str, str2);
    }

    public void registerModuleOnDataRenderNode(String str) {
        nativeRegisterModuleOnDataRenderNode(str);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void reloadPageLayout(String str) {
        nativeReloadPageLayout(str);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void removeInstanceRenderType(String str) {
        nativeRemoveInstanceRenderType(str);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void reportJSException(String str, String str2, String str3) {
        WXBridgeManager.getInstance().reportJSException(str, str2, str3);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void reportNativeInitStatus(String str, String str2) {
        if (WXErrorCode.WX_JS_FRAMEWORK_INIT_SINGLE_PROCESS_SUCCESS.getErrorCode().equals(str) || WXErrorCode.WX_JS_FRAMEWORK_INIT_FAILED.getErrorCode().equals(str)) {
            IWXUserTrackAdapter iWXUserTrackAdapter = WXSDKManager.getInstance().getIWXUserTrackAdapter();
            if (iWXUserTrackAdapter != null) {
                HashMap map = new HashMap(3);
                map.put(IWXUserTrackAdapter.MONITOR_ERROR_CODE, str);
                map.put(IWXUserTrackAdapter.MONITOR_ARG, "InitFrameworkNativeError");
                map.put(IWXUserTrackAdapter.MONITOR_ERROR_MSG, str2);
                WXLogUtils.e("reportNativeInitStatus is running and errorCode is " + str + " And errorMsg is " + str2);
                iWXUserTrackAdapter.commit(null, null, IWXUserTrackAdapter.INIT_FRAMEWORK, null, map);
                return;
            }
            return;
        }
        WXErrorCode wXErrorCode = WXErrorCode.WX_JS_FRAMEWORK_INIT_FAILED_PARAMS_NULL;
        if (wXErrorCode.getErrorCode().equals(str)) {
            WXExceptionUtils.commitCriticalExceptionRT(null, wXErrorCode, "WeexProxy::initFromParam()", wXErrorCode.getErrorMsg() + ": " + str2, null);
            return;
        }
        for (WXErrorCode wXErrorCode2 : WXErrorCode.values()) {
            if (wXErrorCode2.getErrorType().equals(WXErrorCode.ErrorType.NATIVE_ERROR) && wXErrorCode2.getErrorCode().equals(str)) {
                WXExceptionUtils.commitCriticalExceptionRT(null, wXErrorCode2, IWXUserTrackAdapter.INIT_FRAMEWORK, str2, null);
                return;
            }
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void reportServerCrash(String str, String str2) {
        WXLogUtils.e(TAG, "reportServerCrash instanceId:" + str + " crashFile: " + str2);
        try {
            WXBridgeManager.getInstance().callReportCrashReloadPage(str, str2);
        } catch (Throwable th) {
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.e(TAG, "reloadPageNative throw exception:" + WXLogUtils.getStackTrace(th));
            }
        }
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void resetWXBridge(boolean z) {
        nativeResetWXBridge(this, getClass().getName().replace(Operators.DOT, '/'));
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void setDefaultHeightAndWidthIntoRootDom(String str, float f, float f2, boolean z, boolean z2) {
        nativeSetDefaultHeightAndWidthIntoRootDom(str, f, f2, z, z2);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void setDeviceDisplay(String str, float f, float f2, float f3) {
        nativeSetDeviceDisplay(str, f, f2, f3);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void setDeviceDisplayOfPage(String str, float f, float f2) {
        nativeSetDeviceDisplayOfPage(str, f, f2);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void setFlexDirectionDef(String str) {
        nativeSetFlexDirectionDef(str);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void setInstanceRenderType(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        nativeSetInstanceRenderType(str, str2);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void setJSFrmVersion(String str) {
        if (str != null) {
            WXEnvironment.JS_LIB_SDK_VERSION = str;
        }
        WXStateRecord.getInstance().onJSFMInit();
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void setLogType(float f, boolean z) {
        Log.e("WeexCore", "setLog" + WXEnvironment.sLogLevel.getValue() + "isPerf : " + z);
        nativeSetLogType(f, z ? 1.0f : 0.0f);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void setMargin(String str, String str2, CSSShorthand.EDGE edge, float f) {
        nativeSetMargin(str, str2, edge.ordinal(), f);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void setPadding(String str, String str2, CSSShorthand.EDGE edge, float f) {
        nativeSetPadding(str, str2, edge.ordinal(), f);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void setPageArgument(String str, String str2, String str3) {
        nativeSetPageArgument(str, str2, str3);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void setPosition(String str, String str2, CSSShorthand.EDGE edge, float f) {
        nativeSetPosition(str, str2, edge.ordinal(), f);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void setRenderContainerWrapContent(boolean z, String str) {
        nativeSetRenderContainerWrapContent(z, str);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void setStyleHeight(String str, String str2, float f, boolean z) {
        nativeSetStyleHeight(str, str2, f, z);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void setStyleWidth(String str, String str2, float f, boolean z) {
        nativeSetStyleWidth(str, str2, f, z);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void setTimeoutNative(String str, String str2) {
        WXBridgeManager.getInstance().setTimeout(str, str2);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void setViewPortWidth(String str, float f) {
        nativeSetViewPortWidth(str, f);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void takeHeapSnapshot(String str) {
        nativeTakeHeapSnapshot(str);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void updateInitFrameworkParams(String str, String str2, String str3) {
        WXStateRecord.getInstance().recordAction("", "updateInitFrameworkParams:");
        nativeUpdateInitFrameworkParams(str, str2, str3);
    }

    public boolean verifyClientKeyPayload(String str, String str2, String str3) {
        return nativeVerifyClientKeyPayload(str, str2, str3);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void setStyleHeight(String str, String str2, float f) {
        nativeSetStyleHeight(str, str2, f, false);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public void setStyleWidth(String str, String str2, float f) {
        nativeSetStyleWidth(str, str2, f, false);
    }

    @Override // com.taobao.weex.common.IWXBridge
    public int callNative(String str, String str2, String str3) {
        try {
            return callNative(str, JSONArray.parseArray(str2), str3);
        } catch (Exception e) {
            WXLogUtils.e(TAG, "callNative throw exception: " + WXLogUtils.getStackTrace(e));
            return 1;
        }
    }

    private int callNative(String str, JSONArray jSONArray, String str2) {
        int iCallNative;
        long jCurrentTimeMillis = System.currentTimeMillis();
        WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
        if (sDKInstance != null) {
            sDKInstance.firstScreenCreateInstanceTime(jCurrentTimeMillis);
        }
        try {
            iCallNative = WXBridgeManager.getInstance().callNative(str, jSONArray, str2);
        } catch (Throwable th) {
            WXLogUtils.e(TAG, "callNative throw exception:" + WXLogUtils.getStackTrace(th));
            iCallNative = 1;
        }
        if (WXEnvironment.isApkDebugable() && iCallNative == -1) {
            WXLogUtils.w("destroyInstance :" + str + " JSF must stop callNative");
        }
        return iCallNative;
    }
}
