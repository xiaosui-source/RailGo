package io.dcloud.feature.weex.adapter;

import android.text.TextUtils;
import com.taobao.weex.adapter.IWXJSExceptionAdapter;
import com.taobao.weex.common.WXJSExceptionInfo;
import io.dcloud.common.util.AppConsoleLogUtil;
import io.dcloud.common.util.BaseInfo;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class JSExceptionAdapter implements IWXJSExceptionAdapter {
    @Override // com.taobao.weex.adapter.IWXJSExceptionAdapter
    public void onJSException(WXJSExceptionInfo wXJSExceptionInfo) {
        if (wXJSExceptionInfo == null || TextUtils.isEmpty(BaseInfo.sCurrentAppOriginalAppid) || !BaseInfo.sCurrentAppOriginalAppid.startsWith("__UNI__")) {
            return;
        }
        String strReplace = "reportJSException >>>> exception function:" + wXJSExceptionInfo.getFunction() + ", exception:" + wXJSExceptionInfo.getException();
        if (strReplace.endsWith("__ERROR")) {
            strReplace = strReplace.replace("__ERROR", "");
        }
        AppConsoleLogUtil.DCLog(strReplace, "ERROR");
    }
}
