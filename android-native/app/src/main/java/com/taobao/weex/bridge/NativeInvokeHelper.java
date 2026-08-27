package com.taobao.weex.bridge;

import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.bridge.SimpleJSCallback;
import com.taobao.weex.performance.WXAnalyzerDataTransfer;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXReflectionUtils;
import io.dcloud.feature.uniapp.bridge.UniJSCallback;
import java.lang.reflect.Type;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class NativeInvokeHelper {
    private String mInstanceId;

    public NativeInvokeHelper(String str) {
        this.mInstanceId = str;
    }

    public Object invoke(final Object obj, final Invoker invoker, JSONArray jSONArray) {
        final Object[] objArrPrepareArguments = prepareArguments(invoker.getParameterTypes(), jSONArray);
        if (WXAnalyzerDataTransfer.isInteractionLogOpen() && (invoker instanceof MethodInvoker)) {
            int i = 0;
            while (true) {
                if (i >= objArrPrepareArguments.length) {
                    break;
                }
                Object obj2 = objArrPrepareArguments[i];
                if (obj2 instanceof SimpleJSCallback) {
                    final String callbackId = ((SimpleJSCallback) obj2).getCallbackId();
                    StringBuilder sb = new StringBuilder("[client][callNativeModuleStart],");
                    sb.append(this.mInstanceId);
                    sb.append(",");
                    MethodInvoker methodInvoker = (MethodInvoker) invoker;
                    sb.append(methodInvoker.mMethod.getDeclaringClass());
                    sb.append(",");
                    sb.append(methodInvoker.mMethod.getName());
                    sb.append(",");
                    sb.append(callbackId);
                    Log.d(WXAnalyzerDataTransfer.INTERACTION_TAG, sb.toString());
                    ((SimpleJSCallback) objArrPrepareArguments[i]).setInvokerCallback(new SimpleJSCallback.InvokerCallback() { // from class: com.taobao.weex.bridge.NativeInvokeHelper.1
                        @Override // com.taobao.weex.bridge.SimpleJSCallback.InvokerCallback
                        public void onInvokeSuccess() {
                            Log.d(WXAnalyzerDataTransfer.INTERACTION_TAG, "[client][callNativeModuleEnd]," + NativeInvokeHelper.this.mInstanceId + "," + ((MethodInvoker) invoker).mMethod.getDeclaringClass() + "," + ((MethodInvoker) invoker).mMethod.getName() + "," + callbackId);
                        }
                    });
                    break;
                }
                i++;
            }
        }
        if (!invoker.isRunOnUIThread()) {
            return invoker.invoke(obj, objArrPrepareArguments);
        }
        WXSDKManager.getInstance().postOnUiThread(new Runnable() { // from class: com.taobao.weex.bridge.NativeInvokeHelper.2
            @Override // java.lang.Runnable
            public void run() {
                if (invoker != null) {
                    try {
                        WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(NativeInvokeHelper.this.mInstanceId);
                        if (sDKInstance != null && !sDKInstance.isDestroy()) {
                            invoker.invoke(obj, objArrPrepareArguments);
                        }
                    } catch (Exception e) {
                        WXLogUtils.e("NativeInvokeHelper", obj + " Invoker " + invoker.toString() + " exception:" + e);
                    }
                }
            }
        }, 0L);
        return null;
    }

    protected Object[] prepareArguments(Type[] typeArr, JSONArray jSONArray) throws Exception {
        Object[] objArr = new Object[typeArr.length];
        for (int i = 0; i < typeArr.length; i++) {
            Type type = typeArr[i];
            if (i < jSONArray.size()) {
                Object obj = jSONArray.get(i);
                if (type == JSONObject.class) {
                    if ((obj instanceof JSONObject) || obj == null) {
                        objArr[i] = obj;
                    } else if (obj instanceof String) {
                        objArr[i] = JSON.parseObject(obj.toString());
                    }
                } else if (JSCallback.class == type || UniJSCallback.class == type) {
                    if (!(obj instanceof String)) {
                        throw new Exception("Parameter type not match.");
                    }
                    objArr[i] = new SimpleJSCallback(this.mInstanceId, (String) obj);
                } else {
                    objArr[i] = WXReflectionUtils.parseArgument(type, obj);
                }
            } else {
                if (type.getClass().isPrimitive()) {
                    throw new Exception("[prepareArguments] method argument list not match.");
                }
                objArr[i] = null;
            }
        }
        return objArr;
    }
}
