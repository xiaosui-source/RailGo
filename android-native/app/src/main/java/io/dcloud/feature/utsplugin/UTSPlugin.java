package io.dcloud.feature.utsplugin;

import android.content.Context;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;

/* loaded from: classes.dex */
public class UTSPlugin {
    public static void initPlugin(Context context) {
        try {
            WXSDKEngine.registerModule("UTS-Proxy", ProxyModule.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }
}
