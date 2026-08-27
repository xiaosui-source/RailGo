package com.taobao.weex.http;

import android.content.Context;
import android.text.TextUtils;
import dc.squareup.HttpConstants;
import io.dcloud.common.util.BaseInfo;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXHttpUtil {
    public static final String KEY_USER_AGENT = "user-agent";

    public static String assembleUserAgent() {
        String defaultUA = HttpConstants.getDefaultUA();
        if (!TextUtils.isEmpty(defaultUA)) {
            return defaultUA;
        }
        HttpConstants.setUA(BaseInfo.sDefWebViewUserAgent);
        return HttpConstants.getDefaultUA();
    }

    public static String assembleUserAgent(Context context, Map<String, String> map) {
        return assembleUserAgent();
    }
}
