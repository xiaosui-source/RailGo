package io.dcloud.feature.weex_input;

import android.content.Context;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.taobao.weex.ui.component.DCTextArea;
import com.taobao.weex.ui.component.DCWXInput;
import com.taobao.weex.ui.component.WXBasicComponentType;
import com.taobao.weex.ui.component.WXComponent;
import io.dcloud.feature.weex.WeexInstanceMgr;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class DCWXInputRegister {
    public static void initPlugin(Context context) {
        try {
            WXSDKEngine.registerComponent("u-input", (Class<? extends WXComponent>) DCWXInput.class);
            WXSDKEngine.registerComponent("u-textarea", (Class<? extends WXComponent>) DCTextArea.class, false);
            WeexInstanceMgr.self().addComponentByName("input", DCWXInput.class);
            WeexInstanceMgr.self().addComponentByName(WXBasicComponentType.TEXTAREA, DCTextArea.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }
}
