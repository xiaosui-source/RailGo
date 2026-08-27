package io.dcloud.feature.gg.dcloud;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.facebook.common.util.UriUtil;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.common.DHInterface.ILoadCallBack;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.ADUtils;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.LoadAppUtils;
import io.dcloud.common.util.NetTool;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.RuningAcitvityUtil;
import io.dcloud.common.util.ThreadPool;
import io.dcloud.feature.gg.AolSplashUtil;
import io.dcloud.feature.gg.dcloud.ADHandler;
import io.dcloud.feature.uniapp.adapter.AbsURIAdapter;
import java.net.URISyntaxException;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
class ADHandler_wanka extends ADHandler {
    ADHandler_wanka() {
    }

    static void click_wanka(final Context context, final ADHandler.AdData adData, final String str) {
        int iOptInt = adData.data().optInt("template", 0);
        String strOptString = adData.data().optString("action");
        if (iOptInt == 1) {
            if (adData.isEClick()) {
                return;
            }
            JSONArray jSONArrayOptJSONArray = adData.report().optJSONArray("clktrackers");
            if (AbsoluteConst.SPNAME_DOWNLOAD.equals(strOptString)) {
                handleTrackers_wanka(jSONArrayOptJSONArray, adData.full(), new ICallBack() { // from class: io.dcloud.feature.gg.dcloud.ADHandler_wanka.1
                    @Override // io.dcloud.common.DHInterface.ICallBack
                    public Object onCallBack(int i, Object obj) throws JSONException {
                        JSONObject jSONObjectOptJSONObject = ((JSONObject) obj).optJSONObject("data");
                        String strOptString2 = jSONObjectOptJSONObject.optString("dstlink");
                        String strOptString3 = jSONObjectOptJSONObject.optString("clickid");
                        String strOptString4 = adData.data().optString("tid");
                        try {
                            adData.full().put("click_id", strOptString3);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Context context2 = context;
                        ADHandler.AdData adData2 = adData;
                        ADHandler_wanka.click_wanka_other(context2, adData2, strOptString2, adData2.mOriginalAppid, strOptString4, str, adData2.full());
                        if (adData.isEClick()) {
                            return null;
                        }
                        ADUtils.loadAppTip(context);
                        return null;
                    }
                }, "clktrackers");
                return;
            } else {
                if ("url".equals(strOptString)) {
                    click_wanka_url(context, adData, jSONArrayOptJSONArray, adData.mOriginalAppid, str);
                    return;
                }
                return;
            }
        }
        JSONArray jSONArrayOptJSONArray2 = adData.report().optJSONArray("clktrackers");
        if (jSONArrayOptJSONArray2 != null) {
            handleTrackers_wanka(jSONArrayOptJSONArray2, adData.full(), "clktrackers");
        }
        if (!AbsoluteConst.SPNAME_DOWNLOAD.equals(strOptString)) {
            ADHandler.click_base(context, adData, str);
            return;
        }
        String strOptString2 = adData.data().optString(AbsURIAdapter.BUNDLE);
        if (TextUtils.isEmpty(strOptString2) || !LoadAppUtils.isAppLoad(context, strOptString2)) {
            ThreadPool.self().addThreadTask(new Runnable() { // from class: io.dcloud.feature.gg.dcloud.ADHandler_wanka.2
                @Override // java.lang.Runnable
                public void run() throws JSONException {
                    String strOptString3 = adData.data().optString("url");
                    String strOptString4 = adData.data().optString("tid");
                    Context context2 = context;
                    ADHandler.AdData adData2 = adData;
                    ADHandler_wanka.click_wanka_other(context2, adData2, strOptString3, adData2.mOriginalAppid, strOptString4, str, adData2.full());
                }
            });
            if (adData.isEClick()) {
                return;
            }
            ADUtils.loadAppTip(context);
            return;
        }
        if (adData.isEClick()) {
            return;
        }
        try {
            Intent uri = Intent.parseUri(adData.data().optString("dplk"), 1);
            uri.setFlags(268435456);
            if (BaseInfo.isDefense) {
                uri.setSelector(null);
                uri.setComponent(null);
                uri.addCategory("android.intent.category.BROWSABLE");
            }
            if (context.getPackageManager().resolveActivity(uri, 65536) != null) {
                context.startActivity(uri);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void click_wanka_other(Context context, ADHandler.AdData adData, String str, String str2, String str3, String str4, final JSONObject jSONObject) throws JSONException {
        String str5;
        final JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("report");
        JSONArray jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray("dwnlsts");
        if (jSONArrayOptJSONArray != null) {
            handleTrackers_wanka(jSONArrayOptJSONArray, jSONObject, "dwnlsts");
        }
        String strOptString = jSONObject.optJSONObject("data").optString(AbsURIAdapter.BUNDLE);
        if (ADUtils.getDdDataForUrl(str) != null) {
            return;
        }
        String str6 = Operators.DOT_STR + context.getString(R.string.in_package);
        if (TextUtils.isEmpty(strOptString)) {
            str5 = null;
        } else {
            str5 = strOptString + Operators.DOT_STR + context.getString(R.string.in_package);
        }
        String fileNameByUrl = PdrUtil.getFileNameByUrl(str, str6, str5);
        String strOptString2 = adData.full() != null ? adData.full().optString("ua") : "";
        if (adData.isEClick()) {
            ADSim.dwApp(context, str2, str3, str4, str, strOptString, new ILoadCallBack() { // from class: io.dcloud.feature.gg.dcloud.ADHandler_wanka.5
                @Override // io.dcloud.common.DHInterface.ILoadCallBack
                public Object onCallBack(int i, Context context2, Object obj) throws JSONException {
                    if (i != 0) {
                        return null;
                    }
                    JSONArray jSONArrayOptJSONArray2 = jSONObjectOptJSONObject.optJSONArray("dwnltrackers");
                    if (jSONArrayOptJSONArray2 != null) {
                        ADHandler_wanka.handleTrackers_wanka(jSONArrayOptJSONArray2, jSONObject, "dwnltrackers");
                    }
                    return Boolean.TRUE;
                }
            }, strOptString2);
            return;
        }
        long jLoadADFile = ADUtils.loadADFile(context, str2, str3, str4, fileNameByUrl, strOptString, str, "application/vnd.android", new ILoadCallBack() { // from class: io.dcloud.feature.gg.dcloud.ADHandler_wanka.6
            @Override // io.dcloud.common.DHInterface.ILoadCallBack
            public Object onCallBack(int i, Context context2, Object obj) throws JSONException {
                if (i != 0) {
                    return null;
                }
                JSONArray jSONArrayOptJSONArray2 = jSONObjectOptJSONObject.optJSONArray("dwnltrackers");
                if (jSONArrayOptJSONArray2 != null) {
                    ADHandler_wanka.handleTrackers_wanka(jSONArrayOptJSONArray2, jSONObject, "dwnltrackers");
                }
                ADHandler_wanka.runApp(context2, jSONObject, (Intent) obj);
                return Boolean.TRUE;
            }
        }, true, strOptString2);
        ADUtils.ADLoadData aDLoadData = new ADUtils.ADLoadData();
        aDLoadData.name = AolSplashUtil.getApplicationName(context);
        aDLoadData.pname = strOptString;
        aDLoadData.url = str;
        aDLoadData.appid = str2;
        aDLoadData.tid = str3;
        aDLoadData.adid = str4;
        aDLoadData.type = "wanka";
        aDLoadData.id = jLoadADFile;
        aDLoadData.ua = strOptString2;
        try {
            ADUtils.saveLoadData(aDLoadData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void click_wanka_url(final Context context, final ADHandler.AdData adData, final JSONArray jSONArray, String str, String str2) {
        ThreadPool.self().addThreadTask(new Runnable() { // from class: io.dcloud.feature.gg.dcloud.ADHandler_wanka.3
            @Override // java.lang.Runnable
            public void run() throws JSONException {
                if (jSONArray != null) {
                    for (int i = 0; i < jSONArray.length(); i++) {
                        try {
                            JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i);
                            int iOptInt = jSONObjectOptJSONObject.optInt("template_type");
                            JSONObject jSONObjectFull = adData.full();
                            boolean z = jSONObjectFull.has("ua") && jSONObjectFull.optString("ua").equalsIgnoreCase("webview");
                            jSONObjectFull.put("u-a", ADHandler.get("ua-webview"));
                            String url = ADHandler.formatUrl(jSONObjectOptJSONObject.optString("url"), jSONObjectFull);
                            int iOptInt2 = jSONObjectOptJSONObject.optInt("http_method");
                            String strOptString = jSONObjectOptJSONObject.optString(UriUtil.LOCAL_CONTENT_SCHEME);
                            if (iOptInt != 1) {
                                ADHandler_wanka.handleTrackers_wanka(url, strOptString, iOptInt2, 2, false, null, "clktrackers", z);
                            } else if (adData.isEClick()) {
                                ADSim.openUrl(context, url);
                            } else {
                                ADUtils.openUrl(context, url);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    static void dplk_wanka(Context context, ADHandler.AdData adData, String str) {
        JSONArray jSONArrayOptJSONArray = adData.report().optJSONArray("dplktrackers");
        if (jSONArrayOptJSONArray != null) {
            handleTrackers_wanka(jSONArrayOptJSONArray, adData.full(), "dplktrackers");
        }
    }

    static void handleAdData_wanka(Context context, JSONObject jSONObject, long j) throws Exception {
        ADHandler.handleAdData_dcloud(context, jSONObject, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void handleTrackers_wanka(JSONArray jSONArray, JSONObject jSONObject, String str) throws JSONException {
        handleTrackers_wanka(jSONArray, jSONObject, null, str);
    }

    static void log(String str) {
        ADHandler.log("wanka", str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void runApp(Context context, JSONObject jSONObject, Intent intent) {
        RuningAcitvityUtil.StartActivity(context, intent);
    }

    static void view_wanka(Context context, ADHandler.AdData adData, String str) {
        JSONObject jSONObjectReport = adData.report();
        if (jSONObjectReport != null) {
            handleTrackers_wanka(jSONObjectReport.optJSONArray("imptrackers"), adData.full(), "imptrackers");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void handleTrackers_wanka(final String str, final String str2, final int i, int i2, final boolean z, final ICallBack iCallBack, String str3, final boolean z2) {
        final int i3 = i2 - 1;
        log("handleTrackers_wanka template = " + (z ? 1 : 0) + "; t_count=" + i3 + "; tagMsg " + str3 + ";  url=" + str);
        ThreadPool.self().addThreadTask(new Runnable() { // from class: io.dcloud.feature.gg.dcloud.ADHandler_wanka.4
            @Override // java.lang.Runnable
            public void run() {
                HashMap map;
                byte[] bArrHttpGet = null;
                if (z2) {
                    map = new HashMap();
                    map.put(IWebview.USER_AGENT, ADHandler.get("ua-webview"));
                } else {
                    map = null;
                }
                int i4 = i;
                if (i4 == 0) {
                    try {
                        bArrHttpGet = NetTool.httpGet(str, (HashMap<String, String>) map, true);
                    } catch (Exception unused) {
                    }
                } else if (i4 == 1) {
                    bArrHttpGet = NetTool.httpPost(str, str2, map);
                }
                if (!z || bArrHttpGet == null) {
                    return;
                }
                try {
                    JSONObject jSONObject = new JSONObject(new String(bArrHttpGet));
                    if (jSONObject.optInt("ret") == 0) {
                        ICallBack iCallBack2 = iCallBack;
                        if (iCallBack2 != null) {
                            iCallBack2.onCallBack(1, jSONObject);
                            return;
                        }
                        return;
                    }
                    String strOptString = jSONObject.optString(NotificationCompat.CATEGORY_MESSAGE);
                    ADHandler_wanka.log("handleTrackers_wanka Runnable Error url=" + str + ";msg=" + strOptString);
                    int i5 = i3;
                    if (i5 > 0) {
                        ADHandler_wanka.handleTrackers_wanka(str, strOptString, i, i5, z, iCallBack, null, z2);
                    }
                    NetTool.httpGet(str, (HashMap<String, String>) map, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void handleTrackers_wanka(JSONArray jSONArray, JSONObject jSONObject, ICallBack iCallBack, String str) throws JSONException {
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i);
            int iOptInt = jSONObjectOptJSONObject.optInt("template_type");
            boolean z = jSONObject.has("ua") && jSONObject.optString("ua").equalsIgnoreCase("webview");
            try {
                jSONObject.put("u-a", ADHandler.get("ua-webview"));
            } catch (JSONException unused) {
            }
            handleTrackers_wanka(ADHandler.formatUrl(jSONObjectOptJSONObject.optString("url"), jSONObject), jSONObjectOptJSONObject.optString(UriUtil.LOCAL_CONTENT_SCHEME), jSONObjectOptJSONObject.optInt("http_method"), 2, iOptInt == 1, iCallBack, str, z);
        }
    }
}
