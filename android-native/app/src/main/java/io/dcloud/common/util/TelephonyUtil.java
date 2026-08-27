package io.dcloud.common.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import androidx.core.content.ContextCompat;
import com.hjq.permissions.Permission;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.p.d1;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class TelephonyUtil {
    private static String AIDKEY = "aid";
    private static String AId = "";
    private static String IMEI1_KEY = "II1";
    private static String IMEI2_KEY = "II2";
    private static String[] MultiIMEITemp = null;
    private static String MultiIMEI_KEY = "mmikey";
    private static final String OLD_UUID_FILE_NAME = ".imei.txt";
    public static final String TAG = "TelephonyUtil";
    private static final String UUID_FILE_NAME = ".DC4278477faeb9.txt";
    private static boolean isGetAId = false;
    private static boolean isGetMultiIMEI = false;
    private static String randomId = null;
    private static String sImei = "";
    private static String sOriginalImeiAndBakInfo;
    private static Boolean isGetRdId = Boolean.FALSE;
    private static String sIMSI = null;
    private static boolean isGetIMSI = false;
    private static String IMSI_KEY = "isi";
    private static String sMac = null;
    private static boolean isGetMac = false;
    private static String MAC_KEY = "mc";
    private static String mImei = "";

    private static boolean checkPseudoData(String str) {
        if (PdrUtil.isEmpty(str) || str.contains("000000")) {
            return true;
        }
        str.hashCode();
        switch (str) {
        }
        return true;
    }

    private static String createRandomBSFile(Context context, File file, File file2, String str) throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        String strReplaceAll = UUID.randomUUID().toString().replaceAll(Operators.SUB, "").replaceAll("\n", "");
        byte[] bytes = strReplaceAll.getBytes();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();
            if (!FileUtil.needMediaStoreOpenFile(context) && file2 != null) {
                DHFile.copyFile(file.getPath(), file2.getPath());
                return strReplaceAll;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return strReplaceAll;
    }

    public static String getAId(Context context) {
        if (!isGetAId) {
            if (ContextCompat.checkSelfPermission(context, Permission.READ_PHONE_STATE) != 0) {
                return AId;
            }
            SharedPreferences orCreateBundle = SP.getOrCreateBundle(context, SP.N_DEVICE_INFO);
            if (orCreateBundle.contains(AIDKEY)) {
                String string = orCreateBundle.getString(AIDKEY, null);
                if (!PdrUtil.isEmpty(string)) {
                    AId = Base64.decodeString(string, true, 10);
                }
            } else {
                String string2 = Settings.Secure.getString(context.getContentResolver(), d1.a("aWZsemdhbFdhbA=="));
                AId = string2;
                SP.setBundleData(orCreateBundle, AIDKEY, PdrUtil.isEmpty(string2) ? AId : Base64.encodeString(AId, true, 10));
            }
            isGetAId = true;
        }
        return AId;
    }

    private static String getAPSubId(Context context) {
        if (Build.VERSION.SDK_INT >= 29) {
            return null;
        }
        if (!AppRuntime.hasPrivacyForNotShown(context)) {
            return "";
        }
        try {
            Object objInvokeMethod = ReflectUtils.invokeMethod(context.getSystemService("phone"), d1.a("b218W31qe2t6YWptekFs"), new Class[0], new Object[0]);
            if (objInvokeMethod != null) {
                return (String) objInvokeMethod;
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String getDCloudDeviceID(android.content.Context r4) throws java.security.NoSuchAlgorithmException {
        /*
            java.lang.String r0 = io.dcloud.common.util.BaseInfo.PDR
            java.lang.String r1 = "android_device_dcloud_id"
            java.lang.String r0 = io.dcloud.common.adapter.util.SP.getBundleData(r4, r0, r1)
            boolean r2 = checkPseudoData(r0)
            if (r2 == 0) goto L61
            int r0 = android.os.Build.VERSION.SDK_INT
            r2 = 28
            java.lang.String r3 = ""
            if (r0 <= r2) goto L2d
            java.lang.String r0 = io.dcloud.common.adapter.util.DeviceInfo.oaids
            boolean r0 = io.dcloud.common.util.PdrUtil.isEmpty(r0)
            if (r0 != 0) goto L2d
            java.lang.String r0 = io.dcloud.common.adapter.util.DeviceInfo.oaids
            java.lang.String r2 = "\\|"
            java.lang.String[] r0 = r0.split(r2)
            int r2 = r0.length
            if (r2 <= 0) goto L2d
            r2 = 0
            r0 = r0[r2]
            goto L2e
        L2d:
            r0 = r3
        L2e:
            boolean r2 = checkPseudoData(r0)
            if (r2 == 0) goto L51
            java.lang.String[] r0 = io.dcloud.common.util.TelephonyUtil.MultiIMEITemp
            if (r0 == 0) goto L3f
            java.lang.String r2 = ","
            java.lang.String r0 = android.text.TextUtils.join(r2, r0)
            goto L40
        L3f:
            r0 = r3
        L40:
            boolean r2 = checkPseudoData(r0)
            if (r2 == 0) goto L51
            java.lang.String r0 = getRandomId(r4)
            boolean r2 = checkPseudoData(r0)
            if (r2 == 0) goto L51
            r0 = r3
        L51:
            boolean r2 = io.dcloud.common.util.PdrUtil.isEmpty(r0)
            if (r2 == 0) goto L58
            return r3
        L58:
            java.lang.String r0 = io.dcloud.common.util.Md5Utils.md5LowerCase32Bit(r0)
            java.lang.String r2 = io.dcloud.common.util.BaseInfo.PDR
            io.dcloud.common.adapter.util.SP.setBundleData(r4, r2, r1, r0)
        L61:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.util.TelephonyUtil.getDCloudDeviceID(android.content.Context):java.lang.String");
    }

    public static String getIMEI(Context context) {
        return getIMEI(context, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00b7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String[] getMultiIMEI(android.content.Context r6) throws org.json.JSONException {
        /*
            java.lang.String r0 = "android.permission.READ_PHONE_STATE"
            int r0 = androidx.core.content.ContextCompat.checkSelfPermission(r6, r0)
            r1 = 0
            if (r0 == 0) goto La
            return r1
        La:
            boolean r0 = io.dcloud.common.util.AppRuntime.hasPrivacyForNotShown(r6)
            if (r0 == 0) goto L13
            java.lang.String[] r6 = io.dcloud.common.util.TelephonyUtil.MultiIMEITemp
            return r6
        L13:
            boolean r0 = io.dcloud.common.util.TelephonyUtil.isGetMultiIMEI
            if (r0 == 0) goto L1a
            java.lang.String[] r6 = io.dcloud.common.util.TelephonyUtil.MultiIMEITemp
            return r6
        L1a:
            java.lang.String r0 = "device_info"
            android.content.SharedPreferences r0 = io.dcloud.common.adapter.util.SP.getOrCreateBundle(r6, r0)
            java.lang.String r2 = io.dcloud.common.util.TelephonyUtil.MultiIMEI_KEY
            boolean r2 = r0.contains(r2)
            r3 = 10
            r4 = 1
            if (r2 == 0) goto L55
            java.lang.String r6 = io.dcloud.common.util.TelephonyUtil.MultiIMEI_KEY
            java.lang.String r6 = r0.getString(r6, r1)
            boolean r0 = io.dcloud.common.util.PdrUtil.isEmpty(r6)
            if (r0 != 0) goto L50
            java.lang.String r6 = io.dcloud.common.util.Base64.decodeString(r6, r4, r3)
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch: org.json.JSONException -> L4d
            r0.<init>(r6)     // Catch: org.json.JSONException -> L4d
            java.lang.String r6 = io.dcloud.common.util.TelephonyUtil.IMEI1_KEY     // Catch: org.json.JSONException -> L4d
            java.lang.String r6 = r0.optString(r6)     // Catch: org.json.JSONException -> L4d
            java.lang.String r2 = io.dcloud.common.util.TelephonyUtil.IMEI2_KEY     // Catch: org.json.JSONException -> L4e
            java.lang.String r0 = r0.optString(r2)     // Catch: org.json.JSONException -> L4e
            goto L52
        L4d:
            r6 = r1
        L4e:
            r0 = r1
            goto L52
        L50:
            r6 = r1
            r0 = r6
        L52:
            io.dcloud.common.util.TelephonyUtil.isGetMultiIMEI = r4
            goto L97
        L55:
            java.lang.String r2 = "phone"
            java.lang.Object r6 = r6.getSystemService(r2)
            android.telephony.TelephonyManager r6 = (android.telephony.TelephonyManager) r6
            java.lang.Class r2 = r6.getClass()     // Catch: java.lang.Exception -> L94
            java.lang.String r2 = r2.getName()     // Catch: java.lang.Exception -> L94
            java.lang.String r5 = "getDeviceId"
            java.lang.Object r6 = io.dcloud.common.adapter.util.PlatformUtil.invokeMethod(r2, r5, r6)     // Catch: java.lang.Exception -> L94
            java.lang.String r6 = (java.lang.String) r6     // Catch: java.lang.Exception -> L94
            boolean r2 = isUnValid(r6)     // Catch: java.lang.Exception -> L94
            if (r2 != 0) goto L74
            goto L75
        L74:
            r6 = r1
        L75:
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch: java.lang.Exception -> L92
            r2.<init>()     // Catch: java.lang.Exception -> L92
            java.lang.String r5 = io.dcloud.common.util.TelephonyUtil.IMEI1_KEY     // Catch: java.lang.Exception -> L92
            r2.put(r5, r6)     // Catch: java.lang.Exception -> L92
            java.lang.String r5 = io.dcloud.common.util.TelephonyUtil.IMEI2_KEY     // Catch: java.lang.Exception -> L92
            r2.put(r5, r1)     // Catch: java.lang.Exception -> L92
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Exception -> L92
            java.lang.String r2 = io.dcloud.common.util.Base64.encodeString(r2, r4, r3)     // Catch: java.lang.Exception -> L92
            java.lang.String r3 = io.dcloud.common.util.TelephonyUtil.MultiIMEI_KEY     // Catch: java.lang.Exception -> L92
            io.dcloud.common.adapter.util.SP.setBundleData(r0, r3, r2)     // Catch: java.lang.Exception -> L92
            goto L96
        L92:
            goto L96
        L94:
            r6 = r1
        L96:
            r0 = r1
        L97:
            boolean r2 = isUnValid(r6)
            if (r2 != 0) goto Laa
            boolean r2 = isUnValid(r0)
            if (r2 != 0) goto Laa
            java.lang.String[] r6 = new java.lang.String[]{r6, r0}
            io.dcloud.common.util.TelephonyUtil.MultiIMEITemp = r6
            goto Lc6
        Laa:
            boolean r2 = isUnValid(r6)
            if (r2 != 0) goto Lb7
            java.lang.String[] r6 = new java.lang.String[]{r6}
            io.dcloud.common.util.TelephonyUtil.MultiIMEITemp = r6
            goto Lc6
        Lb7:
            boolean r6 = isUnValid(r0)
            if (r6 != 0) goto Lc4
            java.lang.String[] r6 = new java.lang.String[]{r0}
            io.dcloud.common.util.TelephonyUtil.MultiIMEITemp = r6
            goto Lc6
        Lc4:
            io.dcloud.common.util.TelephonyUtil.MultiIMEITemp = r1
        Lc6:
            io.dcloud.common.util.TelephonyUtil.isGetMultiIMEI = r4
            java.lang.String[] r6 = io.dcloud.common.util.TelephonyUtil.MultiIMEITemp
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.util.TelephonyUtil.getMultiIMEI(android.content.Context):java.lang.String[]");
    }

    private static Object getPhoneInfo(int i, Context context) {
        try {
            Object systemService = context.getSystemService("phone");
            String strA = d1.a("b218W31qe2t6YWptekFs");
            int i2 = Build.VERSION.SDK_INT;
            if (i2 > 21) {
                return ReflectUtils.invokeMethod(systemService, strA, new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(i)});
            }
            if (i2 == 21) {
                return ReflectUtils.invokeMethod(systemService, strA, new Class[]{Long.TYPE}, new Object[]{Integer.valueOf(i)});
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00cb A[Catch: Exception -> 0x010e, all -> 0x011f, TryCatch #0 {Exception -> 0x010e, blocks: (B:30:0x00c4, B:36:0x00e2, B:38:0x00e8, B:40:0x00f2, B:41:0x00fc, B:33:0x00cb, B:35:0x00da), top: B:54:0x00c4 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String getRandomId(android.content.Context r11) {
        /*
            Method dump skipped, instructions count: 300
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.util.TelephonyUtil.getRandomId(android.content.Context):java.lang.String");
    }

    public static String getSBBS(Context context, boolean z, boolean z2) {
        return getSBBS(context, z, z2, true);
    }

    private static int getSubId(int i, Context context) {
        Uri uri = Uri.parse("content://telephony/siminfo");
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursorQuery = null;
        try {
            cursorQuery = contentResolver.query(uri, new String[]{"_id", "sim_id"}, "sim_id = ?", new String[]{String.valueOf(i)}, null);
        } catch (Exception unused) {
            if (cursorQuery == null) {
                return -1;
            }
        } catch (Throwable th) {
            if (cursorQuery == null) {
                throw th;
            }
            cursorQuery.close();
            throw th;
        }
        if (cursorQuery != null && cursorQuery.moveToFirst()) {
            int i2 = cursorQuery.getInt(cursorQuery.getColumnIndex("_id"));
            cursorQuery.close();
            return i2;
        }
        if (cursorQuery == null) {
            return -1;
        }
        cursorQuery.close();
        return -1;
    }

    @Deprecated
    public static String getWifiData(Context context) {
        Object objInvokeMethod;
        String strReplace = null;
        if (AppRuntime.hasPrivacyForNotShown(context)) {
            return null;
        }
        if (isGetMac) {
            return sMac;
        }
        SharedPreferences orCreateBundle = SP.getOrCreateBundle(context, SP.N_DEVICE_INFO);
        if (orCreateBundle.contains(MAC_KEY)) {
            String string = orCreateBundle.getString(MAC_KEY, null);
            if (!PdrUtil.isEmpty(string)) {
                sMac = Base64.decodeString(string, true, 10);
            }
        } else {
            Object systemService = context.getSystemService(d1.a("f2FuYQ=="));
            if (systemService != null && (objInvokeMethod = ReflectUtils.invokeMethod(systemService, d1.a("b218S2dmZm1rfGFnZkFmbmc"), new Class[0], new Object[0])) != null) {
                Object objInvokeMethod2 = ReflectUtils.invokeMethod(objInvokeMethod, d1.a("b218RWlrSWxsem17ew"), new Class[0], new Object[0]);
                String str = objInvokeMethod2 != null ? (String) objInvokeMethod2 : null;
                if (!TextUtils.isEmpty(str)) {
                    strReplace = str.replace(":", "");
                }
            }
            sMac = strReplace;
            SP.setBundleData(orCreateBundle, MAC_KEY, PdrUtil.isEmpty(strReplace) ? sMac : Base64.encodeString(sMac, true, 10));
        }
        isGetMac = true;
        return sMac;
    }

    private static boolean isUnValid(String str) {
        return TextUtils.isEmpty(str) || str.contains("Unknown") || str.contains("00000000");
    }

    private static String savePublicFile(File file, File file2, String str, String str2, Context context) throws IOException {
        String string;
        if (!file.exists() || file.length() <= 0) {
            return createRandomBSFile(context, file, file2, UUID_FILE_NAME);
        }
        try {
            string = IOUtil.toString(new FileInputStream(file));
        } catch (Exception unused) {
            string = null;
        }
        try {
            if (!PdrUtil.isEmpty(string)) {
                string = string.replaceAll("\n", "");
            }
            if (file2 != null && !FileUtil.needMediaStoreOpenFile(context)) {
                if (!file2.getParentFile().exists()) {
                    file2.getParentFile().mkdirs();
                    file2.createNewFile();
                }
                DHFile.copyFile(str, str2);
                return string;
            }
            return string;
        } catch (Exception unused2) {
            return string == null ? createRandomBSFile(context, file, file2, UUID_FILE_NAME) : string;
        }
    }

    public static String updateIMEI(Context context) {
        if (!PdrUtil.isEmpty(mImei)) {
            return mImei;
        }
        String[] multiIMEI = getMultiIMEI(context);
        if (multiIMEI == null) {
            mImei = "";
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String str : multiIMEI) {
            sb.append(str);
            sb.append(",");
        }
        if (sb.lastIndexOf(",") >= sb.length() - 1) {
            String string = sb.deleteCharAt(sb.length() - 1).toString();
            mImei = string;
            return string;
        }
        String string2 = sb.toString();
        mImei = string2;
        return string2;
    }

    public static String getIMEI(Context context, boolean z) {
        return getIMEI(context, z, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:113:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x010e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0097 A[PHI: r5
      0x0097: PHI (r5v4 java.lang.String) = (r5v3 java.lang.String), (r5v7 java.lang.String) binds: [B:49:0x0095, B:38:0x0074] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x010c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0114 A[Catch: all -> 0x011f, Exception -> 0x0121, TRY_LEAVE, TryCatch #8 {Exception -> 0x0121, blocks: (B:95:0x010e, B:97:0x0114), top: B:130:0x010e, outer: #9 }] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0118  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String getSBBS(android.content.Context r9, boolean r10, boolean r11, boolean r12) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 346
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.util.TelephonyUtil.getSBBS(android.content.Context, boolean, boolean, boolean):java.lang.String");
    }

    public static String getSimOperator(Context context) {
        if (AppRuntime.hasPrivacyForNotShown(context)) {
            return "";
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        return telephonyManager.getSimState() == 5 ? telephonyManager.getSimOperator() : "";
    }

    public static String getIMEI(Context context, boolean z, boolean z2) {
        return getSBBS(context, z, z2);
    }

    public static String getIMEIS(Context context) {
        try {
            String[] multiIMEI = getMultiIMEI(context);
            return multiIMEI != null ? TextUtils.join(",", multiIMEI) : "";
        } catch (Exception unused) {
            return "";
        }
    }

    public static String getIMSI(Context context) {
        if (context == null) {
            return "";
        }
        if (ContextCompat.checkSelfPermission(context, Permission.READ_PHONE_STATE) != 0) {
            return "";
        }
        boolean zHasPrivacyForNotShown = AppRuntime.hasPrivacyForNotShown(context);
        if (!isGetIMSI && !zHasPrivacyForNotShown) {
            String str = sIMSI;
            if (str != null) {
                return str;
            }
            SharedPreferences orCreateBundle = SP.getOrCreateBundle(context, SP.N_DEVICE_INFO);
            if (orCreateBundle.contains(IMSI_KEY)) {
                String string = orCreateBundle.getString(IMSI_KEY, null);
                if (!PdrUtil.isEmpty(string)) {
                    sIMSI = Base64.decodeString(string, true, 10);
                }
            } else {
                int subId = getSubId(0, context);
                int subId2 = getSubId(1, context);
                if (subId == -1 && subId2 == -1) {
                    sIMSI = getAPSubId(context);
                } else {
                    String str2 = (String) getPhoneInfo(subId, context);
                    String str3 = (String) getPhoneInfo(subId2, context);
                    if (!PdrUtil.isEmpty(str2)) {
                        sIMSI = str2;
                        if (!PdrUtil.isEmpty(str3) && !str2.equals(str3)) {
                            sIMSI += "," + str3;
                        }
                    } else if (!PdrUtil.isEmpty(str3)) {
                        sIMSI = str3;
                    } else {
                        sIMSI = getAPSubId(context);
                    }
                }
                SP.setBundleData(orCreateBundle, IMSI_KEY, PdrUtil.isEmpty(sIMSI) ? sIMSI : Base64.encodeString(sIMSI, true, 10));
            }
            isGetIMSI = true;
            return sIMSI;
        }
        return sIMSI;
    }
}
