package io.dcloud.feature.weex.adapter;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.bridge.IDCVueBridgeAdapter;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaUniWebView;
import io.dcloud.feature.weex.WeexInstanceMgr;
import org.json.JSONException;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class DCVueBridgeAdapter implements IDCVueBridgeAdapter {
    @Override // com.taobao.weex.bridge.IDCVueBridgeAdapter
    public void exec(WXSDKInstance wXSDKInstance, String str, String str2) throws JSONException {
        IWebview iWebviewFindWebview = WeexInstanceMgr.self().findWebview(wXSDKInstance);
        if (iWebviewFindWebview instanceof AdaUniWebView) {
            ((AdaUniWebView) iWebviewFindWebview).prompt(str, str2);
        }
    }

    @Override // com.taobao.weex.bridge.IDCVueBridgeAdapter
    public String execSync(WXSDKInstance wXSDKInstance, String str, String str2) {
        IWebview iWebviewFindWebview = WeexInstanceMgr.self().findWebview(wXSDKInstance);
        return iWebviewFindWebview instanceof AdaUniWebView ? ((AdaUniWebView) iWebviewFindWebview).prompt(str, str2) : "";
    }
}
