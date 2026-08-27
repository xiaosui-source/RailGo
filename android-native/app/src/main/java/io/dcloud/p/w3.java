package io.dcloud.p;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.facebook.common.callercontext.ContextChain;
import com.taobao.weex.common.WXConfig;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.performance.WXInstanceApm;
import io.dcloud.common.util.JSUtil;
import io.dcloud.sdk.poly.base.utils.PrivacyManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class w3 {
    static HashMap a(Context context) {
        String str;
        HashMap map = new HashMap();
        map.put(ContextChain.TAG_PRODUCT, "a");
        map.put("appid", r0.d().b().getAppId());
        try {
            map.put("pname", context.getApplicationInfo().loadLabel(context.getPackageManager()));
        } catch (Exception unused) {
        }
        map.put("pn", context.getPackageName());
        try {
            str = context.getPackageManager().getPackageInfo(context.getPackageName(), 1).versionName;
        } catch (Exception e) {
            e.printStackTrace();
            str = null;
        }
        map.put("pv", str);
        map.put("imei", t4.a(context, true, true));
        map.put("md", Build.MODEL);
        map.put("vd", Build.MANUFACTURER);
        map.put(WXConfig.os, Integer.valueOf(Build.VERSION.SDK_INT));
        map.put("vb", l0.a());
        map.put("net", m4.d(context));
        map.put("mc", l0.a(context));
        map.put("paid", r0.d().b().getAdId());
        map.put("dw", Integer.valueOf(context.getResources().getDisplayMetrics().widthPixels));
        map.put("dh", Integer.valueOf(context.getResources().getDisplayMetrics().heightPixels));
        map.put("psap", "dcloud,wanka,youdao,common");
        map.put("psas", "");
        map.put("ps", 0);
        map.put("psd", 0);
        map.put("data", b(context));
        map.put("pap", "1");
        map.put("papi", "1");
        map.put("psp", TextUtils.join(",", e.b().c()));
        map.put("psaf", WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
        map.put("psdk", Integer.valueOf(l0.c()));
        map.put("mpap", "1");
        map.put("dpsp", "1");
        map.put("bm", c(context));
        map.put("um", d(context));
        map.put("aws", a() ? "1" : WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
        map.put("root", p.a() ? "1" : WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
        map.put("ud", p.b(context) ? "1" : WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
        map.put("wp", p.c(context) ? "1" : WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
        map.put("acc", p.a(context) ? "1" : WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
        map.put("emu", c1.i().a(context) ? "1" : WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
        while (map.values().remove(null)) {
        }
        while (map.values().remove("null")) {
        }
        return map;
    }

    private static String b(Context context) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("net", m4.c(context));
            jSONObject.put("device", m4.b(context));
            jSONObject.put("gps", a3.a(context));
        } catch (Exception unused) {
        }
        return jSONObject.toString();
    }

    private static String d(Context context) throws InterruptedException, IOException, NumberFormatException {
        if (!b0.a().a(context) || !PrivacyManager.getInstance().d()) {
            return "";
        }
        try {
            Process processExec = Runtime.getRuntime().exec("stat -c \"%x\" /data/data");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processExec.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            char[] cArr = new char[256];
            while (true) {
                int i = bufferedReader.read(cArr);
                if (i <= 0) {
                    break;
                }
                stringBuffer.append(cArr, 0, i);
            }
            bufferedReader.close();
            processExec.waitFor();
            String[] strArrSplit = stringBuffer.toString().replace(JSUtil.QUOTE, "").split("\\.");
            long time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strArrSplit[0]).getTime();
            String strSubstring = strArrSplit[1];
            if (strSubstring.contains(Operators.PLUS)) {
                strSubstring = strSubstring.substring(0, strSubstring.indexOf(Operators.PLUS));
            }
            return (time / 1000) + Operators.DOT_STR + Long.parseLong(strSubstring.trim());
        } catch (Exception unused) {
            return Operators.DOT_STR;
        }
    }

    private static String c(Context context) throws IOException {
        if (!b0.a().a(context) || !PrivacyManager.getInstance().d()) {
            return "";
        }
        try {
            File file = new File("/proc/sys/kernel/random/boot_id");
            if (!file.exists()) {
                return "";
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bArr = new byte[37];
            fileInputStream.read(bArr);
            String str = new String(bArr);
            try {
                fileInputStream.close();
                return str;
            } catch (Exception unused) {
                return str;
            }
        } catch (Exception unused2) {
            return "";
        }
    }

    private static boolean a() throws ClassNotFoundException {
        try {
            Class.forName("com.tencent.mm.opensdk.openapi.IWXAPI");
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }
}
