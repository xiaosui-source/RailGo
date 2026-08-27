package io.dcloud.feature.gg;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import com.taobao.weex.performance.WXInstanceApm;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.feature.gg.dcloud.ADHandler;
import io.dcloud.p.g4;
import io.dcloud.sdk.core.util.Const;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class AolSplashUtil {
    static final float Main_View_Weight = 0.8f;
    public static final String SP_AD_LIST_TYPE = "ad_list_order";
    public static final String showCountADReward = "_s_c_a_r";
    static String[] adTypes = {"adpid", Const.TYPE_CSJ, Const.TYPE_GDT};
    private static String DC_AD_TYPE_KEY = "dc_ad_type_key";
    private static boolean isShowInterstitialAd = false;

    public static String da(String str, String str2) {
        JSONObject jSONObjectOptJSONObject;
        g4 g4Var = SP.getsOrCreateBundle(ADHandler.AdTag);
        String strA = g4Var.a("cgk") ? g4Var.a("cgk", "") : g4Var.a("uniad", "");
        if (TextUtils.isEmpty(strA)) {
            String metaValue = AndroidResources.getMetaValue(str);
            return (PdrUtil.isEmpty(metaValue) || !metaValue.contains("_")) ? metaValue : Arrays.binarySearch(adTypes, metaValue.substring(0, metaValue.indexOf("_"))) != -1 ? metaValue.substring(metaValue.indexOf("_") + 1) : metaValue;
        }
        try {
            JSONObject jSONObject = new JSONObject(strA);
            if (jSONObject.has("appid") && (jSONObjectOptJSONObject = jSONObject.optJSONObject("appid")) != null && jSONObjectOptJSONObject.has(str2)) {
                return jSONObjectOptJSONObject.optString(str2);
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public static JSONArray dah(String str) {
        JSONObject jSONObjectOptJSONObject;
        String str2 = SP.getsBundleData(ADHandler.AdTag, "uniad");
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str2);
            if (jSONObject.has("appid_h") && (jSONObjectOptJSONObject = jSONObject.optJSONObject("appid_h")) != null && jSONObjectOptJSONObject.has(str)) {
                return jSONObjectOptJSONObject.getJSONArray(str);
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public static void decodeChannel(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObjectOptJSONObject;
        if (jSONObject == null) {
            return;
        }
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObject3 = new JSONObject();
        JSONObject jSONObjectOptJSONObject2 = null;
        if (jSONObject.has("uniad")) {
            jSONObjectOptJSONObject = jSONObject.optJSONObject("uniad");
            if (jSONObjectOptJSONObject != null) {
                jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject(AbsoluteConst.XML_CHANNEL);
            }
        } else {
            jSONObjectOptJSONObject = null;
        }
        if (jSONObjectOptJSONObject2 == null) {
            return;
        }
        Iterator<String> itKeys = jSONObjectOptJSONObject2.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            JSONObject jSONObjectOptJSONObject3 = jSONObjectOptJSONObject2.optJSONObject(next);
            try {
                jSONObject2.put(next, jSONObjectOptJSONObject3.optString("appid"));
                jSONObject3.put(next, jSONObjectOptJSONObject3.optJSONArray("appidh"));
            } catch (Exception unused) {
            }
        }
        try {
            jSONObjectOptJSONObject.put("appid", jSONObject2);
            jSONObjectOptJSONObject.put("appid_h", jSONObject3);
        } catch (JSONException unused2) {
        }
    }

    public static int dh(Context context) {
        return (int) (context.getResources().getDisplayMetrics().heightPixels * Main_View_Weight);
    }

    public static int dw(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static String getAL(Context context) throws JSONException {
        String str = SP.getsBundleData(context, ADHandler.AdTag, "al");
        if (PdrUtil.isEmpty(str)) {
            return "";
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.length() == 0) {
                return "";
            }
            JSONObject jSONObject2 = new JSONObject();
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                String strOptString = jSONObject.optString(next);
                try {
                    if (PdrUtil.isEmpty(strOptString)) {
                        jSONObject2.put(next, 0);
                    } else if (context.getPackageManager().getLaunchIntentForPackage(strOptString) != null) {
                        jSONObject2.put(next, 1);
                    } else {
                        jSONObject2.put(next, 0);
                    }
                } catch (Exception unused) {
                }
            }
            return jSONObject2.length() > 0 ? jSONObject2.toString() : "";
        } catch (Exception unused2) {
            return "";
        }
    }

    public static List<String> getAdOrder() {
        ArrayList arrayList = new ArrayList(Arrays.asList(SP.getBundleData(ADHandler.AdTag, SP_AD_LIST_TYPE).split(",")));
        arrayList.remove("");
        return arrayList;
    }

    public static String getAdpId(IApp iApp, String str) {
        JSONObject jSONObjectOptJSONObject;
        String str2 = SP.getsBundleData(ADHandler.AdTag, "uniad");
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str2);
            if (jSONObject.has("adpids") && (jSONObjectOptJSONObject = jSONObject.optJSONObject("adpids")) != null && jSONObjectOptJSONObject.has(str)) {
                return jSONObjectOptJSONObject.optString(str);
            }
        } catch (Exception unused) {
        }
        return null;
    }

    public static String getAppKey(String str, String str2) {
        JSONObject jSONObjectOptJSONObject;
        String str3 = SP.getsBundleData(DeviceInfo.sApplicationContext, ADHandler.AdTag, "uniad");
        if (TextUtils.isEmpty(str3)) {
            String metaValue = AndroidResources.getMetaValue(str);
            return (PdrUtil.isEmpty(metaValue) || !metaValue.contains("_")) ? metaValue : Arrays.binarySearch(adTypes, metaValue.substring(0, metaValue.indexOf("_"))) != -1 ? metaValue.substring(metaValue.indexOf("_") + 1) : metaValue;
        }
        try {
            JSONObject jSONObject = new JSONObject(str3);
            if (jSONObject.has("appid") && (jSONObjectOptJSONObject = jSONObject.optJSONObject("appid")) != null && jSONObjectOptJSONObject.has(str2)) {
                return jSONObjectOptJSONObject.optString(str2);
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getAppKey2(String str, String str2) {
        JSONObject jSONObjectOptJSONObject;
        String str3 = SP.getsBundleData(DeviceInfo.sApplicationContext, ADHandler.AdTag, "uniad");
        if (TextUtils.isEmpty(str3)) {
            String metaValue = AndroidResources.getMetaValue(str);
            return (PdrUtil.isEmpty(metaValue) || !metaValue.contains("_")) ? metaValue : Arrays.binarySearch(adTypes, metaValue.substring(0, metaValue.indexOf("_"))) != -1 ? metaValue.substring(metaValue.indexOf("_") + 1) : metaValue;
        }
        try {
            JSONObject jSONObject = new JSONObject(str3);
            if (jSONObject.has("appKey") && (jSONObjectOptJSONObject = jSONObject.optJSONObject("appKey")) != null && jSONObjectOptJSONObject.has(str2)) {
                return jSONObjectOptJSONObject.optString(str2);
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public static Drawable getApplicationIcon(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return packageManager.getApplicationIcon(packageManager.getApplicationInfo(context.getPackageName(), 0));
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getApplicationName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(context.getPackageName(), 0));
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getPlashType() {
        SharedPreferences orCreateBundle = SP.getOrCreateBundle(DC_AD_TYPE_KEY);
        return orCreateBundle != null ? orCreateBundle.getString("type", "dcloud") : "dcloud";
    }

    public static String getSplashAdpId(String str, String str2) {
        JSONObject jSONObjectOptJSONObject;
        String str3 = SP.getsBundleData(ADHandler.AdTag, "uniad");
        if (!TextUtils.isEmpty(str3)) {
            try {
                JSONObject jSONObject = new JSONObject(str3);
                if (jSONObject.has("splash") && (jSONObjectOptJSONObject = jSONObject.optJSONObject("splash")) != null && jSONObjectOptJSONObject.has(str)) {
                    return jSONObjectOptJSONObject.optString(str);
                }
            } catch (Exception unused) {
            }
            return null;
        }
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        String metaValue = AndroidResources.getMetaValue(str2);
        if (!PdrUtil.isEmpty(metaValue) && metaValue.contains("_")) {
            if (Arrays.binarySearch(adTypes, metaValue.substring(0, metaValue.indexOf("_"))) != -1) {
                metaValue = metaValue.substring(metaValue.indexOf("_") + 1);
            }
        }
        return "UNIAD_FULL_SPLASH".equals(str2) ? Boolean.parseBoolean(metaValue) ? "1" : WXInstanceApm.VALUE_ERROR_CODE_DEFAULT : metaValue;
    }

    public static JSONObject getUniad() {
        String str = SP.getsBundleData(ADHandler.AdTag, "uniad");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("adpids")) {
                return jSONObject.optJSONObject("adpids");
            }
        } catch (Exception unused) {
        }
        return null;
    }

    public static boolean isShowingInterstitialAd() {
        return isShowInterstitialAd;
    }

    public static void saveADShowCount(String str, String str2) throws JSONException {
        JSONObject jSONObject;
        SharedPreferences orCreateBundle = SP.getOrCreateBundle(showCountADReward);
        int iOptInt = 0;
        try {
            jSONObject = new JSONObject(SP.getBundleData(orCreateBundle, str));
            try {
                iOptInt = jSONObject.optInt(str2) + 1;
                jSONObject.put(str2, iOptInt);
            } catch (Exception unused) {
            }
        } catch (Exception unused2) {
            jSONObject = null;
        }
        if (jSONObject == null) {
            jSONObject = new JSONObject();
            try {
                jSONObject.put(str2, iOptInt + 1);
            } catch (Exception unused3) {
            }
        }
        SP.setBundleData(orCreateBundle, str, jSONObject.toString());
    }

    public static void saveOperate(Context context, String str, HashMap<String, String> map) {
        try {
            SharedPreferences.Editor editorEdit = SP.getOrCreateBundle(context, ADHandler.AdTag).edit();
            for (String str2 : map.keySet()) {
                editorEdit.putString(str2, map.get(str2));
            }
            editorEdit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setShowInterstitialAd(boolean z) {
        isShowInterstitialAd = z;
    }

    public static void saveOperate(HashMap<String, String> map) {
        saveOperate(null, null, map);
    }
}
