package io.dcloud.feature.gg.dcloud;

import android.content.Context;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.util.NetTool;
import io.dcloud.common.util.ThreadPool;
import io.dcloud.feature.gg.dcloud.ADHandler;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
class ADHandler_youdao extends ADHandler {
    ADHandler_youdao() {
    }

    static void click_youdao(Context context, ADHandler.AdData adData, String str) {
        JSONObject jSONObjectReport = adData.report();
        if (jSONObjectReport != null) {
            handleTrackers_youdao(jSONObjectReport.optJSONArray("clktrackers"), "clktrackers", adData);
        }
        ADHandler.click_base(context, adData, str);
    }

    static void dplk_youdao(Context context, ADHandler.AdData adData, String str) {
        JSONObject jSONObjectReport = adData.report();
        if (jSONObjectReport != null) {
            handleTrackers_youdao(jSONObjectReport.optJSONArray("dptrackers"), "dptrackers", adData);
        }
    }

    static void handleAdData_youdao(Context context, JSONObject jSONObject, long j) throws Exception {
        ADHandler.handleAdData_dcloud(context, jSONObject, j);
    }

    static void handleTrackers_youdao(JSONArray jSONArray, String str, ADHandler.AdData adData) {
        final HashMap map;
        if (jSONArray != null) {
            for (int i = 0; i < jSONArray.length(); i++) {
                final String strOptString = jSONArray.optString(i);
                ADHandler.log("ADHandler_youdao", str + ";url=" + strOptString);
                JSONObject jSONObjectFull = adData.full();
                if (jSONObjectFull != null && jSONObjectFull.has("ua") && jSONObjectFull.optString("ua").equalsIgnoreCase("webview")) {
                    map = new HashMap();
                    map.put(IWebview.USER_AGENT, ADHandler.get("ua-webview"));
                } else {
                    map = null;
                }
                ThreadPool.self().addThreadTask(new Runnable() { // from class: io.dcloud.feature.gg.dcloud.ADHandler_youdao.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            NetTool.httpGet(strOptString, (HashMap<String, String>) map, true);
                        } catch (Exception unused) {
                        }
                    }
                });
            }
        }
    }

    static void view_youdao(Context context, ADHandler.AdData adData, String str) {
        JSONObject jSONObjectReport = adData.report();
        if (jSONObjectReport != null) {
            handleTrackers_youdao(jSONObjectReport.optJSONArray("imptracker"), "imptracker", adData);
        }
    }
}
