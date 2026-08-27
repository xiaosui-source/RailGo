package io.dcloud.feature.weex_scroller;

import android.content.Context;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.taobao.weex.ui.IFComponentHolder;
import com.taobao.weex.ui.SimpleComponentHolder;
import com.taobao.weex.ui.component.DCWXScroller;
import io.dcloud.feature.weex.WeexInstanceMgr;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class DCScrollerPluginImpl {
    public static void initPlugin(Context context) {
        try {
            WXSDKEngine.registerComponent((IFComponentHolder) new SimpleComponentHolder(DCWXScroller.class, new DCWXScroller.Creator()), false, "scroll-view");
            WeexInstanceMgr.self().addComponentByName("scroll-view", DCWXScroller.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }
}
