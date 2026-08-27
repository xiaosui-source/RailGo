package io.dcloud.common.adapter.ui;

import android.webkit.JavascriptInterface;
import io.dcloud.common.DHInterface.IReflectAble;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.util.JSONUtil;
import io.dcloud.common.util.StringUtil;
import java.util.HashMap;
import org.json.JSONArray;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class ReceiveJSValue implements IReflectAble {
    public static String SYNC_HANDLER = "SYNC_HANDLER";
    private static HashMap<String, ReceiveJSValueCallback> arrs = new HashMap<>();
    private String android42Js = null;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface ReceiveJSValueCallback {
        String callback(JSONArray jSONArray);
    }

    public static final String registerCallback(String str, ReceiveJSValueCallback receiveJSValueCallback) {
        return registerCallback(null, str, receiveJSValueCallback);
    }

    @JavascriptInterface
    public final String __js__call__native__(String str, String str2) {
        ReceiveJSValueCallback receiveJSValueCallbackRemove = arrs.remove(str);
        Logger.d("ReceiveJSValue", "__js__call__native__ js=" + str2);
        return receiveJSValueCallbackRemove != null ? receiveJSValueCallbackRemove.callback(JSONUtil.createJSONArray(str2)) : "";
    }

    public static final void addJavascriptInterface(AdaWebview adaWebview) {
        adaWebview.mWebViewImpl.addJavascriptInterface(new ReceiveJSValue(), SYNC_HANDLER);
    }

    public static final String registerCallback(AdaWebview adaWebview, String str, ReceiveJSValueCallback receiveJSValueCallback) {
        ReceiveJSValue receiveJSValue;
        String strValueOf = String.valueOf(receiveJSValueCallback.hashCode());
        arrs.put(strValueOf, receiveJSValueCallback);
        return ((adaWebview == null || (receiveJSValue = adaWebview.mReceiveJSValue_android42) == null) ? "" : receiveJSValue.android42Js) + StringUtil.format("window.SYNC_HANDLER && " + SYNC_HANDLER + ".__js__call__native__('" + strValueOf + "',(function(){var ret = %s;var type = (typeof ret );return JSON.stringify([type,ret]);})());", str);
    }
}
