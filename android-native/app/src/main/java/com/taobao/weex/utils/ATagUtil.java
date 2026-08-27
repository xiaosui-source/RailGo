package com.taobao.weex.utils;

import android.net.Uri;
import android.view.View;
import com.alibaba.fastjson.JSONArray;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import io.dcloud.feature.uniapp.adapter.AbsURIAdapter;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class ATagUtil {
    public static void onClick(View view, String str, String str2) {
        WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
        if (sDKInstance == null) {
            return;
        }
        String string = sDKInstance.rewriteUri(Uri.parse(str2), AbsURIAdapter.LINK).toString();
        JSONArray jSONArray = new JSONArray();
        jSONArray.add(string);
        WXSDKManager.getInstance().getWXBridgeManager().callModuleMethod(str, "event", "openURL", jSONArray);
    }
}
