package io.dcloud.feature.weex_text;

import android.content.Context;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.taobao.weex.ui.IFComponentHolder;
import com.taobao.weex.ui.SimpleComponentHolder;
import io.dcloud.feature.weex.WeexInstanceMgr;
import io.dcloud.feature.weex_text.DCWXRichText;
import io.dcloud.feature.weex_text.DCWXText;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class DCWXTextPlugin {
    public static void initPlugin(Context context) {
        try {
            WXSDKEngine.registerComponent((IFComponentHolder) new SimpleComponentHolder(DCWXText.class, new DCWXText.Creator()), false, "u-text");
            WXSDKEngine.registerComponent((IFComponentHolder) new SimpleComponentHolder(DCWXRichText.class, new DCWXRichText.Creator()), false, "u-rich-text");
            WeexInstanceMgr.self().addComponentByName("text", DCWXText.class);
            WeexInstanceMgr.self().addComponentByName("rich-text", DCWXRichText.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }
}
