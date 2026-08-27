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
public class Aolhandler_common extends ADHandler {
    static void click_common(Context context, ADHandler.AdData adData, String str) {
        JSONObject jSONObjectReport = adData.report();
        if (jSONObjectReport != null) {
            handleTrackers_common(jSONObjectReport.optJSONArray("clktracker"), "clktracker", adData);
        }
        ADHandler.click_base(context, adData, str);
    }

    static void handleAdData_common(Context context, JSONObject jSONObject, long j) throws Exception {
        ADHandler.handleAdData_dcloud(context, jSONObject, j);
    }

    static void handleTrackers_common(JSONArray jSONArray, String str, ADHandler.AdData adData) {
        final HashMap map;
        if (jSONArray != null) {
            for (int i = 0; i < jSONArray.length(); i++) {
                final String strOptString = jSONArray.optJSONObject(i).optString("url");
                JSONObject jSONObjectFull = adData.full();
                if (jSONObjectFull != null && jSONObjectFull.has("ua") && jSONObjectFull.optString("ua").equalsIgnoreCase("webview")) {
                    map = new HashMap();
                    map.put(IWebview.USER_AGENT, ADHandler.get("ua-webview"));
                } else {
                    map = null;
                }
                ThreadPool.self().addThreadTask(new Runnable() { // from class: io.dcloud.feature.gg.dcloud.Aolhandler_common.1
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

    static void handletask_common(Context context, ADHandler.AdData adData, String str, String str2) {
        JSONObject jSONObjectReport = adData.report();
        if (jSONObjectReport != null) {
            handleTrackers_common(jSONObjectReport.optJSONArray(str2), str2, adData);
        }
    }
}
