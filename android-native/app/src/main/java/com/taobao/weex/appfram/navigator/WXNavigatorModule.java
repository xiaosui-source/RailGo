package com.taobao.weex.appfram.navigator;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.utils.WXLogUtils;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXNavigatorModule extends WXModule {
    public static final String CALLBACK_MESSAGE = "message";
    public static final String CALLBACK_RESULT = "result";
    private static final String INSTANCE_ID = "instanceId";
    public static final String MSG_FAILED = "WX_FAILED";
    public static final String MSG_PARAM_ERR = "WX_PARAM_ERR";
    public static final String MSG_SUCCESS = "WX_SUCCESS";
    private static final String TAG = "Navigator";
    private static final String URL = "url";
    private static final String WEEX = "com.taobao.android.intent.category.WEEX";

    private boolean changeVisibilityOfActionBar(Context context, int i) throws ClassNotFoundException {
        ActionBar actionBar;
        try {
            Class.forName("android.support.v7.app.AppCompatActivity");
            if (this.mWXSDKInstance.getContext() instanceof AppCompatActivity) {
                androidx.appcompat.app.ActionBar supportActionBar = ((AppCompatActivity) this.mWXSDKInstance.getContext()).getSupportActionBar();
                if (supportActionBar != null) {
                    if (i == 0) {
                        supportActionBar.show();
                        return true;
                    }
                    if (i == 1) {
                        supportActionBar.hide();
                        return true;
                    }
                }
                return false;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if ((this.mWXSDKInstance.getContext() instanceof Activity) && (actionBar = ((Activity) this.mWXSDKInstance.getContext()).getActionBar()) != null) {
            if (i == 0) {
                actionBar.show();
                return true;
            }
            if (i == 1) {
                actionBar.hide();
                return true;
            }
        }
        return false;
    }

    @JSMethod(uiThread = true)
    public void clearNavBarLeftItem(String str, JSCallback jSCallback) {
        if (WXSDKEngine.getActivityNavBarSetter() == null || !WXSDKEngine.getActivityNavBarSetter().clearNavBarLeftItem(str)) {
            if (jSCallback != null) {
                jSCallback.invoke(MSG_FAILED);
            }
        } else if (jSCallback != null) {
            jSCallback.invoke(MSG_SUCCESS);
        }
    }

    @JSMethod(uiThread = true)
    public void clearNavBarMoreItem(String str, JSCallback jSCallback) {
        if (WXSDKEngine.getActivityNavBarSetter() == null || !WXSDKEngine.getActivityNavBarSetter().clearNavBarMoreItem(str)) {
            if (jSCallback != null) {
                jSCallback.invoke(MSG_FAILED);
            }
        } else if (jSCallback != null) {
            jSCallback.invoke(MSG_SUCCESS);
        }
    }

    @JSMethod(uiThread = true)
    public void clearNavBarRightItem(String str, JSCallback jSCallback) {
        if (WXSDKEngine.getActivityNavBarSetter() == null || !WXSDKEngine.getActivityNavBarSetter().clearNavBarRightItem(str)) {
            if (jSCallback != null) {
                jSCallback.invoke(MSG_FAILED);
            }
        } else if (jSCallback != null) {
            jSCallback.invoke(MSG_SUCCESS);
        }
    }

    @JSMethod(uiThread = true)
    public void close(JSONObject jSONObject, JSCallback jSCallback, JSCallback jSCallback2) {
        JSONObject jSONObject2 = new JSONObject();
        if (this.mWXSDKInstance.getContext() instanceof Activity) {
            ((Activity) this.mWXSDKInstance.getContext()).finish();
        } else {
            jSONObject2.put("result", (Object) MSG_FAILED);
            jSONObject2.put("message", (Object) "Close page failed.");
            jSCallback = jSCallback2;
        }
        if (jSCallback != null) {
            jSCallback.invoke(jSONObject2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    @com.taobao.weex.annotation.JSMethod(uiThread = true)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void open(com.alibaba.fastjson.JSONObject r7, com.taobao.weex.bridge.JSCallback r8, com.taobao.weex.bridge.JSCallback r9) {
        /*
            r6 = this;
            if (r7 == 0) goto L74
            java.lang.String r0 = "url"
            java.lang.String r0 = r7.getString(r0)
            com.alibaba.fastjson.JSONObject r1 = new com.alibaba.fastjson.JSONObject
            r1.<init>()
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            java.lang.String r3 = "message"
            java.lang.String r4 = "result"
            if (r2 != 0) goto L64
            android.net.Uri r0 = android.net.Uri.parse(r0)
            java.lang.String r2 = r0.getScheme()
            boolean r5 = android.text.TextUtils.isEmpty(r2)
            if (r5 != 0) goto L5c
            java.lang.String r5 = "http"
            boolean r5 = r5.equalsIgnoreCase(r2)
            if (r5 != 0) goto L5c
            java.lang.String r5 = "https"
            boolean r2 = r5.equalsIgnoreCase(r2)
            if (r2 == 0) goto L37
            goto L5c
        L37:
            android.content.Intent r7 = new android.content.Intent     // Catch: java.lang.Throwable -> L4d
            java.lang.String r2 = "android.intent.action.VIEW"
            r7.<init>(r2, r0)     // Catch: java.lang.Throwable -> L4d
            com.taobao.weex.WXSDKInstance r0 = r6.mWXSDKInstance     // Catch: java.lang.Throwable -> L4d
            android.content.Context r0 = r0.getContext()     // Catch: java.lang.Throwable -> L4d
            r0.startActivity(r7)     // Catch: java.lang.Throwable -> L4d
            java.lang.String r7 = "WX_SUCCESS"
            r1.put(r4, r7)     // Catch: java.lang.Throwable -> L4d
            goto L6f
        L4d:
            r7 = move-exception
            r7.printStackTrace()
            java.lang.String r7 = "WX_FAILED"
            r1.put(r4, r7)
            java.lang.String r7 = "Open page failed."
            r1.put(r3, r7)
            goto L6e
        L5c:
            java.lang.String r7 = r7.toJSONString()
            r6.push(r7, r8)
            goto L6f
        L64:
            java.lang.String r7 = "WX_PARAM_ERR"
            r1.put(r4, r7)
            java.lang.String r7 = "The URL parameter is empty."
            r1.put(r3, r7)
        L6e:
            r8 = r9
        L6f:
            if (r8 == 0) goto L74
            r8.invoke(r1)
        L74:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.appfram.navigator.WXNavigatorModule.open(com.alibaba.fastjson.JSONObject, com.taobao.weex.bridge.JSCallback, com.taobao.weex.bridge.JSCallback):void");
    }

    @JSMethod(uiThread = true)
    public void pop(String str, JSCallback jSCallback) {
        if (WXSDKEngine.getActivityNavBarSetter() != null && WXSDKEngine.getActivityNavBarSetter().pop(str)) {
            if (jSCallback != null) {
                jSCallback.invoke(MSG_SUCCESS);
            }
        } else if (this.mWXSDKInstance.getContext() instanceof Activity) {
            Activity activity = (Activity) this.mWXSDKInstance.getContext();
            if (WXSDKEngine.getNavigator() == null || !WXSDKEngine.getNavigator().pop(activity, str)) {
                if (jSCallback != null) {
                    jSCallback.invoke(MSG_SUCCESS);
                }
                ((Activity) this.mWXSDKInstance.getContext()).finish();
            } else if (jSCallback != null) {
                jSCallback.invoke(MSG_SUCCESS);
            }
        }
    }

    @JSMethod(uiThread = true)
    public void push(String str, JSCallback jSCallback) {
        if (TextUtils.isEmpty(str)) {
            if (jSCallback != null) {
                jSCallback.invoke(MSG_FAILED);
                return;
            }
            return;
        }
        if (WXSDKEngine.getActivityNavBarSetter() != null && WXSDKEngine.getActivityNavBarSetter().push(str)) {
            if (jSCallback != null) {
                jSCallback.invoke(MSG_SUCCESS);
                return;
            }
            return;
        }
        if (this.mWXSDKInstance.getContext() instanceof Activity) {
            Activity activity = (Activity) this.mWXSDKInstance.getContext();
            if (WXSDKEngine.getNavigator() != null && WXSDKEngine.getNavigator().push(activity, str)) {
                if (jSCallback != null) {
                    jSCallback.invoke(MSG_SUCCESS);
                    return;
                }
                return;
            }
        }
        try {
            String string = JSON.parseObject(str).getString("url");
            if (TextUtils.isEmpty(string)) {
                return;
            }
            Uri uri = Uri.parse(string);
            String scheme = uri.getScheme();
            Uri.Builder builderBuildUpon = uri.buildUpon();
            if (TextUtils.isEmpty(scheme)) {
                builderBuildUpon.scheme("http");
            }
            Intent intent = new Intent("android.intent.action.VIEW", builderBuildUpon.build());
            intent.addCategory(WEEX);
            intent.putExtra("instanceId", this.mWXSDKInstance.getInstanceId());
            this.mWXSDKInstance.getContext().startActivity(intent);
            if (jSCallback != null) {
                jSCallback.invoke(MSG_SUCCESS);
            }
        } catch (Exception e) {
            WXLogUtils.eTag("Navigator", e);
            if (jSCallback != null) {
                jSCallback.invoke(MSG_FAILED);
            }
        }
    }

    @JSMethod(uiThread = true)
    public void setNavBarLeftItem(String str, JSCallback jSCallback) {
        if (TextUtils.isEmpty(str) || WXSDKEngine.getActivityNavBarSetter() == null || !WXSDKEngine.getActivityNavBarSetter().setNavBarLeftItem(str)) {
            if (jSCallback != null) {
                jSCallback.invoke(MSG_FAILED);
            }
        } else if (jSCallback != null) {
            jSCallback.invoke(MSG_SUCCESS);
        }
    }

    @JSMethod(uiThread = true)
    public void setNavBarMoreItem(String str, JSCallback jSCallback) {
        if (TextUtils.isEmpty(str) || WXSDKEngine.getActivityNavBarSetter() == null || !WXSDKEngine.getActivityNavBarSetter().setNavBarMoreItem(str)) {
            if (jSCallback != null) {
                jSCallback.invoke(MSG_FAILED);
            }
        } else if (jSCallback != null) {
            jSCallback.invoke(MSG_SUCCESS);
        }
    }

    @JSMethod(uiThread = true)
    public void setNavBarRightItem(String str, JSCallback jSCallback) {
        if (TextUtils.isEmpty(str) || WXSDKEngine.getActivityNavBarSetter() == null || !WXSDKEngine.getActivityNavBarSetter().setNavBarRightItem(str)) {
            if (jSCallback != null) {
                jSCallback.invoke(MSG_FAILED);
            }
        } else if (jSCallback != null) {
            jSCallback.invoke(MSG_SUCCESS);
        }
    }

    @JSMethod(uiThread = true)
    public void setNavBarTitle(String str, JSCallback jSCallback) {
        if (TextUtils.isEmpty(str) || WXSDKEngine.getActivityNavBarSetter() == null || !WXSDKEngine.getActivityNavBarSetter().setNavBarTitle(str)) {
            if (jSCallback != null) {
                jSCallback.invoke(MSG_FAILED);
            }
        } else if (jSCallback != null) {
            jSCallback.invoke(MSG_SUCCESS);
        }
    }

    @JSMethod
    public void setNavBarHidden(String str, String str2) {
        String str3;
        try {
        } catch (JSONException e) {
            WXLogUtils.e("Navigator", WXLogUtils.getStackTrace(e));
        }
        if (changeVisibilityOfActionBar(this.mWXSDKInstance.getContext(), JSON.parseObject(str).getInteger("hidden").intValue())) {
            str3 = MSG_SUCCESS;
        } else {
            str3 = MSG_FAILED;
        }
        WXBridgeManager.getInstance().callback(this.mWXSDKInstance.getInstanceId(), str2, str3);
    }
}
