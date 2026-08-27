package com.taobao.weex;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.alibaba.fastjson.JSON;
import com.taobao.weex.utils.WXLogUtils;
import java.util.HashMap;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXGlobalEventReceiver extends BroadcastReceiver {
    public static final String EVENT_ACTION = "wx_global_action";
    public static final String EVENT_NAME = "eventName";
    public static final String EVENT_PARAMS = "eventParams";
    public static final String EVENT_WX_INSTANCEID = "wx_instanceid";
    private WXSDKInstance mWXSDKInstance;

    public WXGlobalEventReceiver() {
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra(EVENT_NAME);
        try {
            this.mWXSDKInstance.fireGlobalEventCallback(stringExtra, (HashMap) JSON.parseObject(intent.getStringExtra(EVENT_PARAMS), HashMap.class));
        } catch (Exception e) {
            WXLogUtils.e("global-receive", e);
        }
    }

    public WXGlobalEventReceiver(WXSDKInstance wXSDKInstance) {
        this.mWXSDKInstance = wXSDKInstance;
    }
}
