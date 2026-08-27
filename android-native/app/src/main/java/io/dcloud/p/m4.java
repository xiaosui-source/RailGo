package io.dcloud.p;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.core.content.ContextCompat;
import com.taobao.weex.common.WXConfig;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.utils.tools.TimeCalculator;
import io.dcloud.sdk.poly.base.utils.PrivacyManager;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class m4 {
    private static String a = "0";
    private static String b = "1";
    private static String c = "3";
    private static String d = "4";
    private static String e = "5";
    private static String f = "6";
    private static String g = "7";
    private static boolean h = false;
    private static String i = "1";

    public static JSONObject a(Context context) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("device", b(context));
            jSONObject.put("net", c(context));
            jSONObject.put("gps", a3.a(context));
        } catch (Exception unused) {
        }
        return jSONObject;
    }

    public static JSONObject b(Context context) throws JSONException {
        String strA;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", 1);
            jSONObject.put(WXConfig.os, TimeCalculator.PLATFORM_ANDROID);
            jSONObject.put("osv", Build.VERSION.RELEASE);
            jSONObject.put("vendor", Build.MANUFACTURER);
            jSONObject.put("model", Build.MODEL);
            jSONObject.put(WXComponent.PROP_FS_WRAP_CONTENT, context.getResources().getDisplayMetrics().widthPixels);
            jSONObject.put("h", context.getResources().getDisplayMetrics().heightPixels);
            try {
                jSONObject.put("ip", t2.a(context));
            } catch (Exception unused) {
            }
            String strD = x1.d(context);
            String str = "";
            if (TextUtils.isEmpty(strD)) {
                strD = "";
            }
            jSONObject.put("mac", strD);
            jSONObject.put("dpi", context.getResources().getDisplayMetrics().densityDpi);
            jSONObject.put("density", context.getResources().getDisplayMetrics().density);
            jSONObject.put("lan", Locale.getDefault().getLanguage());
            jSONObject.put("country", l0.b());
            jSONObject.put("_nl", Locale.getDefault().getCountry());
            jSONObject.put("tz", TimeZone.getDefault().getDisplayName(false, 0));
            String[] strArrE = x1.e(context);
            jSONObject.put("imei", (strArrE == null || strArrE.length <= 0) ? "" : TextUtils.join(",", strArrE));
            String strA2 = PrivacyManager.getInstance().d() ? l3.a().a(context) : "";
            jSONObject.put("oaid", strA2);
            try {
                strA = n1.a().a(context);
            } catch (Exception unused2) {
                strA = "";
            }
            jSONObject.put("gaid", strA);
            String strA3 = x1.a(context);
            if (TextUtils.isEmpty(strA3)) {
                strA3 = "";
            }
            jSONObject.put("aid", strA3);
            String strC = x1.c(context);
            if (!TextUtils.isEmpty(strC)) {
                str = strC;
            }
            jSONObject.put("imsi", str);
            jSONObject.put("ua", e(context));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }

    public static String d(Context context) {
        if (!b0.a().a(context)) {
            return i;
        }
        if (!PrivacyManager.getInstance().d()) {
            return i;
        }
        if (ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_NETWORK_STATE") != 0) {
            return i;
        }
        if (h) {
            return i;
        }
        String str = b;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager != null && connectivityManager.getActiveNetworkInfo() != null) {
            str = a;
            if (connectivityManager.getActiveNetworkInfo().getType() != 1) {
                if (connectivityManager.getActiveNetworkInfo().getType() == 0) {
                    int subtype = connectivityManager.getActiveNetworkInfo().getSubtype();
                    switch (subtype) {
                        case 1:
                        case 2:
                        case 4:
                        case 7:
                            str = d;
                            break;
                        case 3:
                        case 8:
                            str = e;
                            break;
                        case 5:
                        case 6:
                        case 12:
                        case 14:
                            str = e;
                            break;
                        case 9:
                        case 10:
                        case 11:
                        case 13:
                        case 15:
                            str = f;
                            break;
                        case 16:
                        case 19:
                        default:
                            str = "" + subtype;
                            break;
                        case 17:
                        case 18:
                            str = e;
                            break;
                        case 20:
                            str = g;
                            break;
                    }
                }
            } else {
                str = c;
            }
        }
        i = str;
        h = true;
        return str;
    }

    public static String e(Context context) {
        String strA = d4.a(context, "dcloud-ads", "u-a");
        if (!TextUtils.isEmpty(strA) || !b0.a().a(context) || !PrivacyManager.getInstance().d()) {
            return strA;
        }
        try {
            WebView webView = new WebView(context);
            WebSettings settings = webView.getSettings();
            settings.setSavePassword(false);
            strA = settings.getUserAgentString();
            webView.destroy();
            d4.a(context, "dcloud-ads", "u-a", strA);
            return strA;
        } catch (Throwable unused) {
            return strA;
        }
    }

    public static JSONObject c(Context context) throws JSONException {
        String strSubstring = "460";
        String strSubstring2 = "10";
        if (b0.a().a(context) && PrivacyManager.getInstance().d()) {
            try {
                String networkOperator = ((TelephonyManager) context.getSystemService("phone")).getNetworkOperator();
                if (!TextUtils.isEmpty(networkOperator) && networkOperator.length() == 5) {
                    strSubstring = networkOperator.substring(0, 3);
                    strSubstring2 = networkOperator.substring(3);
                }
            } catch (Exception unused) {
            }
        }
        JSONObject jSONObject = new JSONObject(a3.a(context, strSubstring2));
        try {
            jSONObject.put("type", d(context));
            jSONObject.put("mcc", strSubstring);
            jSONObject.put("mnc", strSubstring2);
        } catch (Exception unused2) {
        }
        return jSONObject;
    }
}
