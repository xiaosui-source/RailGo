package io.dcloud.common.constant;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import com.facebook.common.callercontext.ContextChain;
import com.taobao.weex.common.WXConfig;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.MobilePhoneModel;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.CreateShortResultReceiver;
import io.dcloud.common.util.NetworkTypeUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.TelephonyUtil;
import java.util.HashMap;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class DataInterface {
    public static String getRomVersion() throws ClassNotFoundException {
        String str;
        if (!Build.MANUFACTURER.equals(MobilePhoneModel.XIAOMI)) {
            return Build.VERSION.INCREMENTAL;
        }
        String buildValue = DeviceInfo.getBuildValue("ro.miui.ui.version.name");
        StringBuilder sb = new StringBuilder();
        sb.append(Build.VERSION.INCREMENTAL);
        if (TextUtils.isEmpty(buildValue)) {
            str = "";
        } else {
            str = Operators.SUB + buildValue;
        }
        sb.append(str);
        return sb.toString();
    }

    public static HashMap getStartupUrlBaseData(Context context, String str, String str2, String str3, boolean z) throws Throwable {
        String bundleData = SP.getBundleData(context, "pdr", SP.STARTUP_DEVICE_ID);
        if (PdrUtil.isEmpty(bundleData)) {
            bundleData = TelephonyUtil.getSBBS(context, true, true, false);
        }
        int networkType = NetworkTypeUtil.getNetworkType(context);
        String str4 = Build.MODEL;
        int i = Build.VERSION.SDK_INT;
        String str5 = DeviceInfo.sPackageName;
        if (str5 == null) {
            str5 = AndroidResources.packageName;
        }
        HashMap map = new HashMap();
        map.put("appid", str);
        if (!z) {
            map.put("net", Integer.valueOf(networkType));
        }
        map.put("imei", bundleData);
        map.put("md", str4);
        map.put(WXConfig.os, Integer.valueOf(i));
        map.put("vb", "1.9.9.82603");
        map.put(CreateShortResultReceiver.KEY_SF, Integer.valueOf(StringConst.getIntSF(str2)));
        map.put(ContextChain.TAG_PRODUCT, "a");
        map.put("d1", Long.valueOf(System.currentTimeMillis()));
        map.put(CreateShortResultReceiver.KEY_SFD, str3);
        map.put("vd", Build.MANUFACTURER);
        map.put("pn", str5);
        return map;
    }

    public static String getStreamappFrom(Intent intent) {
        if (intent == null || !intent.hasExtra(IntentConst.RUNING_STREAPP_LAUNCHER)) {
            return null;
        }
        String stringExtra = intent.getStringExtra(IntentConst.RUNING_STREAPP_LAUNCHER);
        if (stringExtra.indexOf("third:") == 0) {
            return stringExtra.substring(6, stringExtra.length());
        }
        return null;
    }

    /* JADX WARN: Not initialized variable reg: 4, insn: 0x0068: MOVE (r2 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:24:0x0068 */
    /* JADX WARN: Removed duplicated region for block: B:36:0x006b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String getSystemProperty() throws java.lang.Throwable {
        /*
            java.lang.String r0 = "Exception while closing InputStream"
            java.lang.String r1 = "Unable to read sysprop ro.miui.ui.version.name"
            r2 = 0
            java.lang.Runtime r3 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f
            java.lang.String r4 = "getprop ro.miui.ui.version.name"
            java.lang.Process r3 = r3.exec(r4)     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f
            java.io.InputStream r3 = r3.getInputStream()     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f
            r5.<init>(r3)     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f
            r3 = 1024(0x400, float:1.435E-42)
            r4.<init>(r5, r3)     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f
            java.lang.String r3 = r4.readLine()     // Catch: java.io.IOException -> L3b java.lang.Throwable -> L67
            r4.close()     // Catch: java.io.IOException -> L3b java.lang.Throwable -> L67
            r4.close()     // Catch: java.io.IOException -> L2a
            return r3
        L2a:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r0)
            r2.append(r1)
            java.lang.String r0 = r2.toString()
            io.dcloud.common.adapter.util.Logger.i(r0)
            return r3
        L3b:
            r3 = move-exception
            goto L41
        L3d:
            r1 = move-exception
            goto L69
        L3f:
            r3 = move-exception
            r4 = r2
        L41:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L67
            r5.<init>(r1)     // Catch: java.lang.Throwable -> L67
            r5.append(r3)     // Catch: java.lang.Throwable -> L67
            java.lang.String r1 = r5.toString()     // Catch: java.lang.Throwable -> L67
            io.dcloud.common.adapter.util.Logger.i(r1)     // Catch: java.lang.Throwable -> L67
            if (r4 == 0) goto L66
            r4.close()     // Catch: java.io.IOException -> L56
            goto L66
        L56:
            r1 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r0)
            r3.append(r1)
            java.lang.String r0 = r3.toString()
            io.dcloud.common.adapter.util.Logger.i(r0)
        L66:
            return r2
        L67:
            r1 = move-exception
            r2 = r4
        L69:
            if (r2 == 0) goto L7f
            r2.close()     // Catch: java.io.IOException -> L6f
            goto L7f
        L6f:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r0)
            r3.append(r2)
            java.lang.String r0 = r3.toString()
            io.dcloud.common.adapter.util.Logger.i(r0)
        L7f:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.constant.DataInterface.getSystemProperty():java.lang.String");
    }

    public static String getTestParam(String str) {
        return "&__am=".concat((TextUtils.isEmpty(str) || !BaseInfo.isTest(str)) ? "r" : "t");
    }
}
