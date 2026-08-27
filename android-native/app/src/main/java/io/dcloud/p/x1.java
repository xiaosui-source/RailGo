package io.dcloud.p;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import androidx.core.content.ContextCompat;
import com.hjq.permissions.Permission;
import io.dcloud.sdk.poly.base.utils.PrivacyManager;
import java.lang.reflect.Method;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class x1 {
    private static boolean a = false;
    private static String[] b = null;
    private static boolean c = false;
    private static String d = null;
    private static boolean e = false;
    private static String f = null;
    private static boolean g = false;
    private static String h;

    public static String a(Context context) {
        if (!PrivacyManager.getInstance().d()) {
            return f;
        }
        if (!b0.a().a(context)) {
            return f;
        }
        if (PrivacyManager.getInstance().e()) {
            String strC = PrivacyManager.getInstance().c();
            f = strC;
            return strC;
        }
        if (!e) {
            f = Settings.System.getString(context.getContentResolver(), "android_id");
            e = true;
        }
        return f;
    }

    private static String b(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getSubscriberId();
        } catch (Exception unused) {
            return null;
        }
    }

    public static String c(Context context) {
        String str;
        String strB;
        if (context == null) {
            return "";
        }
        try {
        } catch (Exception unused) {
            str = null;
        }
        if (ContextCompat.checkSelfPermission(context, Permission.READ_PHONE_STATE) != 0) {
            return d;
        }
        if (!PrivacyManager.getInstance().d()) {
            return d;
        }
        if (!b0.a().a(context)) {
            return d;
        }
        if (PrivacyManager.getInstance().e()) {
            String strB2 = PrivacyManager.getInstance().b();
            d = strB2;
            return strB2;
        }
        if (c) {
            return d;
        }
        int iB = b(0, context);
        int iB2 = b(1, context);
        if (iB != -1 || iB2 != -1) {
            str = (String) a(iB, context);
            String str2 = (String) a(iB2, context);
            if (!TextUtils.isEmpty(str)) {
                try {
                    if (!TextUtils.isEmpty(str2) && !str.equals(str2)) {
                        strB = str + "," + str2;
                    }
                } catch (Exception unused2) {
                }
            } else if (TextUtils.isEmpty(str2)) {
                strB = b(context);
            } else {
                str = str2;
            }
            c = true;
            d = str;
            return str;
        }
        strB = b(context);
        str = strB;
        c = true;
        d = str;
        return str;
    }

    public static String d(Context context) {
        if (ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_WIFI_STATE") != 0) {
            return h;
        }
        if (!PrivacyManager.getInstance().d()) {
            return h;
        }
        if (!b0.a().a(context)) {
            return h;
        }
        if (g) {
            return h;
        }
        WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
        String macAddress = connectionInfo == null ? null : connectionInfo.getMacAddress();
        h = macAddress;
        g = true;
        return macAddress;
    }

    public static String[] e(Context context) {
        String deviceId;
        String deviceId2;
        if (context == null) {
            return null;
        }
        if (ContextCompat.checkSelfPermission(context, Permission.READ_PHONE_STATE) != 0) {
            return b;
        }
        if (!PrivacyManager.getInstance().d()) {
            return b;
        }
        if (!b0.a().a(context)) {
            return b;
        }
        if (PrivacyManager.getInstance().e()) {
            String[] strArrA = PrivacyManager.getInstance().a();
            b = strArrA;
            return strArrA;
        }
        if (a) {
            return b;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        try {
            deviceId = telephonyManager.getDeviceId();
            deviceId2 = telephonyManager.getDeviceId(1);
            if (a(deviceId)) {
                deviceId = null;
            }
        } catch (Exception unused) {
            deviceId = null;
        }
        if (a(deviceId2)) {
            deviceId2 = null;
        }
        if (!a(deviceId) && !a(deviceId2)) {
            b = new String[]{deviceId, deviceId2};
        } else if (!a(deviceId)) {
            b = new String[]{deviceId};
        } else if (a(deviceId2)) {
            b = null;
        } else {
            b = new String[]{deviceId2};
        }
        a = true;
        return b;
    }

    private static int b(int i, Context context) {
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
            if (cursorQuery != null) {
                cursorQuery.close();
                throw th;
            }
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

    private static Object a(int i, Context context) {
        try {
            Object systemService = context.getSystemService("phone");
            int i2 = Build.VERSION.SDK_INT;
            if (i2 > 21) {
                return a(systemService.getClass().getName(), "getSubscriberId", systemService, new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(i)});
            }
            if (i2 == 21) {
                return a(systemService.getClass().getName(), "getSubscriberId", systemService, new Class[]{Long.TYPE}, new Object[]{Integer.valueOf(i)});
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    private static Object a(String str, String str2, Object obj, Class[] clsArr, Object[] objArr) throws NoSuchMethodException, SecurityException {
        try {
            Method method = Class.forName(str).getMethod(str2, clsArr);
            if (method == null) {
                return null;
            }
            method.setAccessible(true);
            return method.invoke(obj, objArr);
        } catch (ClassNotFoundException | NoSuchMethodException | Exception unused) {
            return null;
        }
    }

    private static boolean a(String str) {
        return TextUtils.isEmpty(str) || str.contains("Unknown") || str.contains("00000000");
    }
}
