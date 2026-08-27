package io.dcloud.feature.weex.extend;

import android.net.Uri;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.bridge.SimpleJSCallback;
import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.component.WXImage;
import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.util.MessageHandler;
import io.dcloud.feature.internal.sdk.SDK;
import io.dcloud.feature.uniapp.common.UniModule;
import io.dcloud.feature.weex.EnumStateCode;
import io.dcloud.feature.weex.WeexInstanceMgr;
import java.util.HashMap;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class DCUniMPModule extends UniModule {
    @JSMethod
    public void closeUniMP(final String str, final JSCallback jSCallback) {
        MessageHandler.post(new Runnable() { // from class: io.dcloud.feature.weex.extend.DCUniMPModule.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (SDK.isUniMP) {
                        JSCallback jSCallback2 = jSCallback;
                        if (jSCallback2 != null) {
                            jSCallback2.invoke(EnumStateCode.obtainMap(EnumStateCode.FAIL_BY_NO_PERMISSION));
                            return;
                        }
                        return;
                    }
                    if (TextUtils.isEmpty(str)) {
                        JSCallback jSCallback3 = jSCallback;
                        if (jSCallback3 != null) {
                            jSCallback3.invoke(EnumStateCode.obtainMap(EnumStateCode.FAIL_BY_INVALID_PARAMETER));
                            return;
                        }
                        return;
                    }
                    IWebview iWebviewFindWebview = WeexInstanceMgr.self().findWebview(DCUniMPModule.this.mWXSDKInstance);
                    if (iWebviewFindWebview != null) {
                        WeexInstanceMgr.self().doForFeature(IMgr.MgrType.FeatureMgr, 10, new Object[]{iWebviewFindWebview.obtainApp(), WeexInstanceMgr.self().getUniMPFeature(), "closeUniMP", new Object[]{str, new ICallBack() { // from class: io.dcloud.feature.weex.extend.DCUniMPModule.2.1
                            @Override // io.dcloud.common.DHInterface.ICallBack
                            public Object onCallBack(int i, Object obj) {
                                HashMap map = new HashMap();
                                if (i >= 0) {
                                    map.put("type", WXImage.SUCCEED);
                                    map.put("code", 0);
                                } else {
                                    map.put("type", Constants.Event.FAIL);
                                    map.put("code", Integer.valueOf(i));
                                    map.put("message", obj != null ? obj.toString() : "Unknown error");
                                }
                                JSCallback jSCallback4 = jSCallback;
                                if (jSCallback4 == null) {
                                    return null;
                                }
                                jSCallback4.invoke(map);
                                return null;
                            }
                        }}});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @JSMethod
    public void getUniMPVersion(String str, final JSCallback jSCallback) {
        new HashMap();
        if (SDK.isUniMPSDK()) {
            if (jSCallback != null) {
                jSCallback.invoke(EnumStateCode.obtainMap(EnumStateCode.FAIL_BY_NO_PERMISSION));
                return;
            }
            return;
        }
        if ((TextUtils.isEmpty(str) || !str.startsWith("__UNI__")) && jSCallback != null) {
            jSCallback.invoke(EnumStateCode.obtainMap(EnumStateCode.FAIL_BY_INVALID_PARAMETER));
        }
        IWebview iWebviewFindWebview = WeexInstanceMgr.self().findWebview(this.mWXSDKInstance);
        if (iWebviewFindWebview != null) {
            WeexInstanceMgr.self().doForFeature(IMgr.MgrType.FeatureMgr, 10, new Object[]{iWebviewFindWebview.obtainApp(), WeexInstanceMgr.self().getUniMPFeature(), "getUniMPVersion", new Object[]{str, new ICallBack() { // from class: io.dcloud.feature.weex.extend.DCUniMPModule.8
                @Override // io.dcloud.common.DHInterface.ICallBack
                public Object onCallBack(int i, Object obj) {
                    if (obj == null) {
                        JSCallback jSCallback2 = jSCallback;
                        if (jSCallback2 == null) {
                            return null;
                        }
                        jSCallback2.invoke(EnumStateCode.obtainMap(EnumStateCode.FAIL_BY_NO_RESOURCE_EXIST));
                        return null;
                    }
                    JSONObject object = JSON.parseObject(obj.toString());
                    JSCallback jSCallback3 = jSCallback;
                    if (jSCallback3 == null) {
                        return null;
                    }
                    jSCallback3.invoke(EnumStateCode.obtainMap(EnumStateCode.SUCCESS_NO_BODY, "versionInfo", object));
                    return null;
                }
            }}});
        }
    }

    @JSMethod
    public void hideUniMP(final String str, final JSCallback jSCallback) {
        MessageHandler.post(new Runnable() { // from class: io.dcloud.feature.weex.extend.DCUniMPModule.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (SDK.isUniMP) {
                        JSCallback jSCallback2 = jSCallback;
                        if (jSCallback2 != null) {
                            jSCallback2.invoke(EnumStateCode.obtainMap(EnumStateCode.FAIL_BY_NO_PERMISSION));
                            return;
                        }
                        return;
                    }
                    if (TextUtils.isEmpty(str)) {
                        JSCallback jSCallback3 = jSCallback;
                        if (jSCallback3 != null) {
                            jSCallback3.invoke(EnumStateCode.obtainMap(EnumStateCode.FAIL_BY_INVALID_PARAMETER));
                            return;
                        }
                        return;
                    }
                    IWebview iWebviewFindWebview = WeexInstanceMgr.self().findWebview(DCUniMPModule.this.mWXSDKInstance);
                    if (iWebviewFindWebview != null) {
                        WeexInstanceMgr.self().doForFeature(IMgr.MgrType.FeatureMgr, 10, new Object[]{iWebviewFindWebview.obtainApp(), WeexInstanceMgr.self().getUniMPFeature(), "hideUniMP", new Object[]{str, new ICallBack() { // from class: io.dcloud.feature.weex.extend.DCUniMPModule.3.1
                            @Override // io.dcloud.common.DHInterface.ICallBack
                            public Object onCallBack(int i, Object obj) {
                                HashMap map = new HashMap();
                                if (i >= 0) {
                                    map.put("type", WXImage.SUCCEED);
                                    map.put("code", 0);
                                } else {
                                    map.put("type", Constants.Event.FAIL);
                                    map.put("code", Integer.valueOf(i));
                                    map.put("message", obj != null ? obj.toString() : "Unknown error");
                                }
                                JSCallback jSCallback4 = jSCallback;
                                if (jSCallback4 == null) {
                                    return null;
                                }
                                jSCallback4.invoke(map);
                                return null;
                            }
                        }}});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @JSMethod
    public void installUniMP(String str, final JSCallback jSCallback) {
        JSONObject object;
        try {
            object = JSON.parseObject(str);
        } catch (Exception unused) {
            object = null;
        }
        if (SDK.isUniMPSDK()) {
            if (jSCallback != null) {
                jSCallback.invoke(EnumStateCode.obtainMap(EnumStateCode.FAIL_BY_NO_PERMISSION));
                return;
            }
            return;
        }
        if (object == null) {
            if (jSCallback != null) {
                jSCallback.invoke(EnumStateCode.obtainMap(EnumStateCode.FAIL_BY_INVALID_PARAMETER));
                return;
            }
            return;
        }
        String string = object.getString("appid");
        String string2 = object.getString("wgtFile");
        String string3 = object.containsKey(Constants.Value.PASSWORD) ? object.getString(Constants.Value.PASSWORD) : "";
        if (!TextUtils.isEmpty(string2)) {
            Uri uriRewriteUri = this.mUniSDKInstance.rewriteUri(Uri.parse(string2), "file");
            if (uriRewriteUri != null) {
                string2 = uriRewriteUri.getPath();
            }
        } else if (jSCallback != null) {
            jSCallback.invoke(EnumStateCode.obtainMap(EnumStateCode.FAIL_BY_NO_RESOURCE_EXIST));
        }
        ICallBack iCallBack = new ICallBack() { // from class: io.dcloud.feature.weex.extend.DCUniMPModule.6
            @Override // io.dcloud.common.DHInterface.ICallBack
            public Object onCallBack(int i, Object obj) {
                HashMap map = new HashMap();
                if (i >= 0) {
                    map.put("type", WXImage.SUCCEED);
                    map.put("code", 0);
                } else {
                    map.put("type", Constants.Event.FAIL);
                    map.put("code", Integer.valueOf(i));
                    map.put("message", obj != null ? obj.toString() : "Unknown error");
                }
                JSCallback jSCallback2 = jSCallback;
                if (jSCallback2 == null) {
                    return null;
                }
                jSCallback2.invoke(map);
                return null;
            }
        };
        IWebview iWebviewFindWebview = WeexInstanceMgr.self().findWebview(this.mWXSDKInstance);
        if (iWebviewFindWebview != null) {
            WeexInstanceMgr.self().doForFeature(IMgr.MgrType.FeatureMgr, 10, new Object[]{iWebviewFindWebview.obtainApp(), WeexInstanceMgr.self().getUniMPFeature(), "installUniMP", new Object[]{string, string2, string3, iCallBack}});
        }
    }

    @JSMethod
    public void onUniMPEventReceive(JSCallback jSCallback) {
        IWebview iWebviewFindWebview;
        if (SDK.isUniMPSDK() || (iWebviewFindWebview = WeexInstanceMgr.self().findWebview(this.mWXSDKInstance)) == null || !(jSCallback instanceof SimpleJSCallback)) {
            return;
        }
        WeexInstanceMgr.self().doForFeature(IMgr.MgrType.FeatureMgr, 10, new Object[]{iWebviewFindWebview.obtainApp(), WeexInstanceMgr.self().getUniMPFeature(), "onUniMPEventReceive", new Object[]{iWebviewFindWebview.obtainApp().obtainAppId(), this.mWXSDKInstance.getInstanceId(), ((SimpleJSCallback) jSCallback).getCallbackId()}});
    }

    @JSMethod
    public void openUniMP(final String str, final JSCallback jSCallback) {
        MessageHandler.post(new Runnable() { // from class: io.dcloud.feature.weex.extend.DCUniMPModule.5
            @Override // java.lang.Runnable
            public void run() {
                JSONObject object;
                try {
                    object = JSON.parseObject(str);
                } catch (Exception unused) {
                    object = null;
                }
                try {
                    if (SDK.isUniMP) {
                        JSCallback jSCallback2 = jSCallback;
                        if (jSCallback2 != null) {
                            jSCallback2.invoke(EnumStateCode.obtainMap(EnumStateCode.FAIL_BY_NO_PERMISSION));
                            return;
                        }
                        return;
                    }
                    if (object == null) {
                        JSCallback jSCallback3 = jSCallback;
                        if (jSCallback3 != null) {
                            jSCallback3.invoke(EnumStateCode.obtainMap(EnumStateCode.FAIL_BY_INVALID_PARAMETER));
                            return;
                        }
                        return;
                    }
                    IWebview iWebviewFindWebview = WeexInstanceMgr.self().findWebview(DCUniMPModule.this.mWXSDKInstance);
                    if (iWebviewFindWebview != null) {
                        ICallBack iCallBack = new ICallBack() { // from class: io.dcloud.feature.weex.extend.DCUniMPModule.5.1
                            @Override // io.dcloud.common.DHInterface.ICallBack
                            public Object onCallBack(int i, Object obj) {
                                HashMap map = new HashMap();
                                if (i >= 0) {
                                    map.put("type", WXImage.SUCCEED);
                                    map.put("code", 0);
                                } else {
                                    map.put("type", Constants.Event.FAIL);
                                    map.put("code", Integer.valueOf(i));
                                    map.put("message", obj != null ? obj.toString() : "Unknown error");
                                }
                                JSCallback jSCallback4 = jSCallback;
                                if (jSCallback4 == null) {
                                    return null;
                                }
                                jSCallback4.invoke(map);
                                return null;
                            }
                        };
                        object.put("appInfo", (Object) iWebviewFindWebview.obtainApp().obtainAppInfo());
                        WeexInstanceMgr.self().doForFeature(IMgr.MgrType.FeatureMgr, 10, new Object[]{iWebviewFindWebview.obtainApp(), WeexInstanceMgr.self().getUniMPFeature(), "openUniMP", new Object[]{object.toJSONString(), iCallBack}});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.alibaba.fastjson.JSONObject] */
    @JSMethod
    public void sendUniMPEvent(String str, String str2, String str3, final JSCallback jSCallback) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            if (jSCallback != null) {
                jSCallback.invoke(EnumStateCode.obtainMap(EnumStateCode.FAIL_BY_INVALID_PARAMETER));
            }
        } else {
            if (SDK.isUniMPSDK()) {
                if (jSCallback != null) {
                    jSCallback.invoke(EnumStateCode.obtainMap(EnumStateCode.FAIL_BY_NO_PERMISSION));
                    return;
                }
                return;
            }
            ?? object = JSON.parseObject(str3);
            if (object != 0) {
                str3 = object;
            }
            ICallBack iCallBack = new ICallBack() { // from class: io.dcloud.feature.weex.extend.DCUniMPModule.7
                @Override // io.dcloud.common.DHInterface.ICallBack
                public Object onCallBack(int i, Object obj) {
                    HashMap map = new HashMap();
                    if (i >= 0) {
                        map.put("type", WXImage.SUCCEED);
                        map.put("code", 0);
                    } else {
                        map.put("type", Constants.Event.FAIL);
                        map.put("code", Integer.valueOf(i));
                        map.put("message", obj != null ? obj.toString() : "Unknown error");
                    }
                    JSCallback jSCallback2 = jSCallback;
                    if (jSCallback2 == null) {
                        return null;
                    }
                    jSCallback2.invoke(map);
                    return null;
                }
            };
            IWebview iWebviewFindWebview = WeexInstanceMgr.self().findWebview(this.mWXSDKInstance);
            if (iWebviewFindWebview != null) {
                WeexInstanceMgr.self().doForFeature(IMgr.MgrType.FeatureMgr, 10, new Object[]{iWebviewFindWebview.obtainApp(), WeexInstanceMgr.self().getUniMPFeature(), "sendUniMPEvent", new Object[]{str, str2, str3, iCallBack}});
            }
        }
    }

    @JSMethod
    public void setDefaultMenuItems(String str, final JSCallback jSCallback) {
        JSONObject object;
        try {
            object = JSON.parseObject(str);
        } catch (Exception unused) {
            object = null;
        }
        if (SDK.isUniMPSDK()) {
            return;
        }
        if (object == null) {
            if (jSCallback != null) {
                jSCallback.invoke(EnumStateCode.obtainMap(EnumStateCode.FAIL_BY_INVALID_PARAMETER));
            }
        } else {
            ICallBack iCallBack = new ICallBack() { // from class: io.dcloud.feature.weex.extend.DCUniMPModule.4
                @Override // io.dcloud.common.DHInterface.ICallBack
                public Object onCallBack(int i, Object obj) {
                    JSONObject object2 = JSON.parseObject(String.valueOf(obj));
                    String string = object2.getString("appId");
                    String string2 = object2.getString("id");
                    if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                        HashMap map = new HashMap();
                        map.put("appId", string);
                        map.put("id", string2);
                        JSCallback jSCallback2 = jSCallback;
                        if (jSCallback2 != null) {
                            jSCallback2.invokeAndKeepAlive(map);
                        }
                    }
                    return null;
                }
            };
            IWebview iWebviewFindWebview = WeexInstanceMgr.self().findWebview(this.mWXSDKInstance);
            if (iWebviewFindWebview != null) {
                WeexInstanceMgr.self().doForFeature(IMgr.MgrType.FeatureMgr, 10, new Object[]{iWebviewFindWebview.obtainApp(), WeexInstanceMgr.self().getUniMPFeature(), "setDefaultMenuItems", new Object[]{str, iCallBack}});
            }
        }
    }

    @JSMethod
    public void showUniMP(final String str, final JSCallback jSCallback) {
        MessageHandler.post(new Runnable() { // from class: io.dcloud.feature.weex.extend.DCUniMPModule.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (SDK.isUniMP) {
                        JSCallback jSCallback2 = jSCallback;
                        if (jSCallback2 != null) {
                            jSCallback2.invoke(EnumStateCode.obtainMap(EnumStateCode.FAIL_BY_NO_PERMISSION));
                            return;
                        }
                        return;
                    }
                    if (TextUtils.isEmpty(str)) {
                        JSCallback jSCallback3 = jSCallback;
                        if (jSCallback3 != null) {
                            jSCallback3.invoke(EnumStateCode.obtainMap(EnumStateCode.FAIL_BY_INVALID_PARAMETER));
                            return;
                        }
                        return;
                    }
                    IWebview iWebviewFindWebview = WeexInstanceMgr.self().findWebview(DCUniMPModule.this.mWXSDKInstance);
                    if (iWebviewFindWebview != null) {
                        WeexInstanceMgr.self().doForFeature(IMgr.MgrType.FeatureMgr, 10, new Object[]{iWebviewFindWebview.obtainApp(), WeexInstanceMgr.self().getUniMPFeature(), "showUniMP", new Object[]{str, new ICallBack() { // from class: io.dcloud.feature.weex.extend.DCUniMPModule.1.1
                            @Override // io.dcloud.common.DHInterface.ICallBack
                            public Object onCallBack(int i, Object obj) {
                                HashMap map = new HashMap();
                                if (i >= 0) {
                                    map.put("type", WXImage.SUCCEED);
                                    map.put("code", 0);
                                } else {
                                    map.put("type", Constants.Event.FAIL);
                                    map.put("code", Integer.valueOf(i));
                                    map.put("message", obj != null ? obj.toString() : "Unknown error");
                                }
                                JSCallback jSCallback4 = jSCallback;
                                if (jSCallback4 == null) {
                                    return null;
                                }
                                jSCallback4.invoke(map);
                                return null;
                            }
                        }}});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
