package com.taobao.weex.bridge;

import android.net.Uri;
import android.text.TextUtils;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXHttpListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXHttpAdapter;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.WXErrorCode;
import com.taobao.weex.common.WXRequest;
import com.taobao.weex.common.WXResponse;
import com.taobao.weex.http.WXHttpUtil;
import com.taobao.weex.utils.WXExceptionUtils;
import com.taobao.weex.utils.WXLogUtils;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.feature.uniapp.adapter.AbsURIAdapter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Locale;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class RequestHandler {

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    class OnHttpListenerInner extends WXHttpListener {
        private long sNativeCallback;

        OnHttpListenerInner(WXSDKInstance wXSDKInstance, long j, String str) {
            super(wXSDKInstance, str);
            this.sNativeCallback = j;
        }

        @Override // com.taobao.weex.WXHttpListener
        public void onFail(WXResponse wXResponse) {
            RequestHandler.this.nativeInvokeOnFailed(this.sNativeCallback);
        }

        @Override // com.taobao.weex.WXHttpListener
        public void onSuccess(WXResponse wXResponse) {
            final String str = new String(wXResponse.originalData);
            WXBridgeManager.BundType bundleType = WXBridgeManager.getInstance().getBundleType("", str);
            final String string = bundleType == null ? "Others" : bundleType.toString();
            if ("Others".equalsIgnoreCase(string) && getInstance() != null) {
                String instanceId = getInstance().getInstanceId();
                WXErrorCode wXErrorCode = WXErrorCode.WX_KEY_EXCEPTION_NO_BUNDLE_TYPE;
                WXExceptionUtils.commitCriticalExceptionRT(instanceId, wXErrorCode, "RequestHandler.onSuccess", "eagle ->" + wXErrorCode.getErrorMsg(), null);
            }
            WXBridgeManager.getInstance().post(new Runnable() { // from class: com.taobao.weex.bridge.RequestHandler.OnHttpListenerInner.1
                @Override // java.lang.Runnable
                public void run() {
                    if (WXBridgeManager.getInstance().isJSFrameworkInit()) {
                        OnHttpListenerInner onHttpListenerInner = OnHttpListenerInner.this;
                        RequestHandler.this.nativeInvokeOnSuccess(onHttpListenerInner.sNativeCallback, str, string);
                    } else {
                        OnHttpListenerInner onHttpListenerInner2 = OnHttpListenerInner.this;
                        RequestHandler.this.nativeInvokeOnFailed(onHttpListenerInner2.sNativeCallback);
                    }
                }
            });
        }
    }

    public static RequestHandler create() {
        return new RequestHandler();
    }

    public void getBundleType(String str, final String str2, final long j) {
        WXBridgeManager.BundType bundleType = WXBridgeManager.getInstance().getBundleType("", str2);
        final String string = bundleType == null ? "Others" : bundleType.toString();
        WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
        if ("Others".equalsIgnoreCase(string) && sDKInstance != null) {
            WXErrorCode wXErrorCode = WXErrorCode.WX_KEY_EXCEPTION_NO_BUNDLE_TYPE;
            WXExceptionUtils.commitCriticalExceptionRT(str, wXErrorCode, "RequestHandler.onSuccess", "eagle ->" + wXErrorCode.getErrorMsg(), null);
        }
        WXBridgeManager.getInstance().post(new Runnable() { // from class: com.taobao.weex.bridge.RequestHandler.1
            @Override // java.lang.Runnable
            public void run() {
                if (WXBridgeManager.getInstance().isJSFrameworkInit()) {
                    RequestHandler.this.nativeInvokeOnSuccess(j, str2, string);
                } else {
                    RequestHandler.this.nativeInvokeOnFailed(j);
                }
            }
        });
    }

    native void nativeInvokeOnFailed(long j);

    native void nativeInvokeOnSuccess(long j, String str, String str2);

    public void send(String str, String str2, long j) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || j == 0 || !WXSDKManager.getInstance().getAllInstanceMap().containsKey(str)) {
            return;
        }
        WXSDKManager wXSDKManager = WXSDKManager.getInstance();
        WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
        if (sDKInstance == null) {
            return;
        }
        IWXHttpAdapter iWXHttpAdapter = WXSDKManager.getInstance().getIWXHttpAdapter();
        WXRequest wXRequest = new WXRequest();
        wXRequest.url = wXSDKManager.getURIAdapter().rewrite(sDKInstance, AbsURIAdapter.BUNDLE, Uri.parse(str2)).toString();
        if (wXRequest.paramMap == null) {
            wXRequest.paramMap = new HashMap();
        }
        wXRequest.paramMap.put(WXHttpUtil.KEY_USER_AGENT, WXHttpUtil.assembleUserAgent(sDKInstance.getContext(), WXEnvironment.getConfig()));
        wXRequest.paramMap.put("isBundleRequest", AbsoluteConst.TRUE);
        WXLogUtils.i("Eagle", String.format(Locale.ENGLISH, "Weex eagle is going to download script from %s", str2));
        iWXHttpAdapter.sendRequest(wXRequest, new OnHttpListenerInner(sDKInstance, j, str2));
    }
}
