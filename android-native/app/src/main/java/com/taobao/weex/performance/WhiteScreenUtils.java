package com.taobao.weex.performance;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXConfigAdapter;
import com.taobao.weex.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WhiteScreenUtils {
    public static boolean doWhiteScreenCheck() {
        IWXConfigAdapter wxConfigAdapter = WXSDKManager.getInstance().getWxConfigAdapter();
        if (wxConfigAdapter == null) {
            return false;
        }
        double dDoubleValue = 100.0d;
        double dRandom = Math.random() * 100.0d;
        try {
            dDoubleValue = Double.valueOf(wxConfigAdapter.getConfig("wxapm", "new_ws_sampling", "100")).doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dRandom < dDoubleValue;
    }

    private static JSONObject geViewDetailTreeMsg(View view) throws JSONException {
        if (view == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("width", view.getWidth());
            jSONObject.put("height", view.getHeight());
            int[] iArr = {-1, -1};
            view.getLocationOnScreen(iArr);
            jSONObject.put(Constants.Name.X, iArr[0]);
            jSONObject.put(Constants.Name.Y, iArr[1]);
            if (!(view instanceof ViewGroup)) {
                jSONObject.put("type", view.getClass().getSimpleName());
                return jSONObject;
            }
            jSONObject.put("type", view.getClass().getSimpleName());
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                jSONObject.put("child_" + i, geViewDetailTreeMsg(viewGroup.getChildAt(i)));
            }
            return jSONObject;
        } catch (Exception e) {
            e.printStackTrace();
            return jSONObject;
        }
    }

    private static boolean hasLeafViewOrSizeIgnore(View view, int i) {
        if (!(view instanceof ViewGroup)) {
            return true;
        }
        if (i > 0) {
            if (view.getHeight() < 10 || view.getWidth() < 10) {
                return true;
            }
            i--;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
            if (hasLeafViewOrSizeIgnore(viewGroup.getChildAt(i2), i)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isInWhiteList(WXSDKInstance wXSDKInstance) {
        IWXConfigAdapter wxConfigAdapter = WXSDKManager.getInstance().getWxConfigAdapter();
        if (wxConfigAdapter == null) {
            return false;
        }
        String config = wxConfigAdapter.getConfig("wxapm", "ws_white_list", null);
        if (TextUtils.isEmpty(config)) {
            return false;
        }
        try {
            for (String str : config.split(";")) {
                if (wXSDKInstance.getBundleUrl() != null && wXSDKInstance.getBundleUrl().contains(str)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isWhiteScreen(WXSDKInstance wXSDKInstance) {
        if (wXSDKInstance == null) {
            return false;
        }
        if ((wXSDKInstance.getContainerView() instanceof ViewGroup) && !isInWhiteList(wXSDKInstance)) {
            return !hasLeafViewOrSizeIgnore(r1, 3);
        }
        return false;
    }

    public static String takeViewTreeSnapShot(WXSDKInstance wXSDKInstance) {
        if (wXSDKInstance == null) {
            return "nullInstance";
        }
        JSONObject jSONObjectGeViewDetailTreeMsg = geViewDetailTreeMsg(wXSDKInstance.getContainerView());
        return jSONObjectGeViewDetailTreeMsg != null ? jSONObjectGeViewDetailTreeMsg.toString() : "";
    }
}
