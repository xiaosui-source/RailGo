package io.dcloud.feature.weex_barcode;

import android.content.Context;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.taobao.weex.ui.component.WXComponent;
import io.dcloud.feature.weex.WeexInstanceMgr;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class BarcodePlugin {
    public static void initPlugin(Context context) {
        try {
            WXSDKEngine.registerComponent("barcode", (Class<? extends WXComponent>) BarcodeComponent.class);
            WXSDKEngine.registerModule("barcodeScan", BarcodeModule.class);
            WeexInstanceMgr.self().addComponentByName("barcode", BarcodeComponent.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }
}
