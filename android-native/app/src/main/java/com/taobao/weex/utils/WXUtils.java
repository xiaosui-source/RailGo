package com.taobao.weex.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import androidx.collection.LruCache;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXConfigAdapter;
import com.taobao.weex.common.Constants;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.common.constant.AbsoluteConst;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXUtils {
    private static final int HUNDRED = 100;
    public static final char PERCENT = '%';
    static final LruCache<String, Integer> sCache = new LruCache<>(64);
    private static final long sInterval = System.currentTimeMillis() - SystemClock.uptimeMillis();

    public static boolean checkGreyConfig(String str, String str2, String str3) {
        IWXConfigAdapter wxConfigAdapter = WXSDKManager.getInstance().getWxConfigAdapter();
        if (wxConfigAdapter == null) {
            return false;
        }
        double dDoubleValue = 100.0d;
        double dRandom = Math.random() * 100.0d;
        try {
            dDoubleValue = Double.valueOf(wxConfigAdapter.getConfig(str, str2, str3)).doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dRandom < dDoubleValue;
    }

    public static float fastGetFloat(String str, float f) {
        int i;
        boolean z;
        char cCharAt;
        char cCharAt2;
        float f2 = 0.0f;
        if (TextUtils.isEmpty(str)) {
            return 0.0f;
        }
        if (str.charAt(0) == '-') {
            i = 1;
            z = false;
        } else {
            i = str.charAt(0) == '+' ? 1 : 0;
            z = true;
        }
        while (i < str.length() && (cCharAt2 = str.charAt(i)) >= '0' && cCharAt2 <= '9') {
            f2 = ((f2 * 10.0f) + cCharAt2) - 48.0f;
            i++;
        }
        if (i < str.length() && str.charAt(i) == '.') {
            int i2 = i + 1;
            int i3 = 10;
            for (int i4 = 0; i2 < str.length() && i4 < f && (cCharAt = str.charAt(i2)) >= '0' && cCharAt <= '9'; i4++) {
                f2 += (cCharAt - '0') / i3;
                i3 *= 10;
                i2++;
            }
        }
        return !z ? f2 * (-1.0f) : f2;
    }

    private static float floatByViewport(Object obj, float f) {
        if (obj == null) {
            return Float.NaN;
        }
        String strTrim = obj.toString().trim();
        if ("auto".equals(strTrim) || Constants.Name.UNDEFINED.equals(strTrim) || TextUtils.isEmpty(strTrim)) {
            WXLogUtils.e("Argument Warning ! value is " + strTrim + "And default Value:NaN");
            return Float.NaN;
        }
        if (strTrim.endsWith("wx")) {
            try {
                return transferWx(strTrim, f);
            } catch (NumberFormatException e) {
                WXLogUtils.e("Argument format error! value is " + obj, e);
            } catch (Exception e2) {
                WXLogUtils.e("Argument error! value is " + obj, e2);
            }
        } else if (!strTrim.endsWith("px")) {
            try {
                return Float.parseFloat(strTrim);
            } catch (NumberFormatException e3) {
                WXLogUtils.e("Argument format error! value is " + obj, e3);
            } catch (Exception e4) {
                WXLogUtils.e("Argument error! value is " + obj, e4);
            }
        } else if (strTrim.length() <= 3 || !(strTrim.endsWith("rpx") || strTrim.endsWith("upx"))) {
            try {
                return Float.parseFloat(strTrim.substring(0, strTrim.indexOf("px")));
            } catch (NumberFormatException e5) {
                WXLogUtils.e("Argument format error! value is " + obj, e5);
            } catch (Exception e6) {
                WXLogUtils.e("Argument error! value is " + obj, e6);
            }
        } else {
            try {
                return transferRpxAndUpx(strTrim.substring(0, strTrim.length() - 3), 750.0f);
            } catch (NumberFormatException e7) {
                WXLogUtils.e("Argument format error! value is " + obj, e7);
            } catch (Exception e8) {
                WXLogUtils.e("Argument error! value is " + obj, e8);
            }
        }
        return Float.NaN;
    }

    public static long getAvailMemory(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        WXLogUtils.w("app AvailMemory ---->>>" + (memoryInfo.availMem / 1048576));
        return memoryInfo.availMem / 1048576;
    }

    public static Boolean getBoolean(Object obj, Boolean bool) {
        if (obj != null) {
            if (TextUtils.equals(AbsoluteConst.FALSE, obj.toString())) {
                return Boolean.FALSE;
            }
            if (TextUtils.equals(AbsoluteConst.TRUE, obj.toString())) {
                return Boolean.TRUE;
            }
        }
        return bool;
    }

    public static String getBundleBanner(String str) throws NumberFormatException {
        int i;
        int iIndexLineBreak;
        int iIndexOf = str.indexOf("/*!");
        if (iIndexOf == -1 || (iIndexLineBreak = indexLineBreak(str, (i = iIndexOf + 3))) == -1) {
            return null;
        }
        int i2 = Integer.parseInt(str.substring(i, iIndexLineBreak));
        int i3 = iIndexLineBreak + 1;
        String strSubstring = str.substring(i3, i2 + i3);
        int iLastIndexOf = strSubstring.lastIndexOf("!*/");
        if (iLastIndexOf == -1) {
            return null;
        }
        String strSubstring2 = strSubstring.substring(0, iLastIndexOf);
        StringBuilder sb = new StringBuilder();
        for (String str2 : splitLineBreak(strSubstring2)) {
            sb.append(str2.replaceFirst("\\*", ""));
        }
        return sb.toString();
    }

    public static LruCache getCache() {
        return sCache;
    }

    @Deprecated
    public static double getDouble(Object obj) {
        if (obj == null) {
            return 0.0d;
        }
        String strTrim = obj.toString().trim();
        if (strTrim.endsWith("wx")) {
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.w("the value of " + obj + " use wx unit, which will be not supported soon after.");
            }
            try {
                return transferWx(strTrim, 750.0f);
            } catch (NumberFormatException e) {
                WXLogUtils.e("Argument format error! value is " + obj, e);
            } catch (Exception e2) {
                WXLogUtils.e("Argument error! value is " + obj, e2);
            }
        } else if (!strTrim.endsWith("px")) {
            try {
                return Double.parseDouble(strTrim);
            } catch (NumberFormatException e3) {
                WXLogUtils.e("Argument format error! value is " + obj, e3);
            } catch (Exception e4) {
                WXLogUtils.e("Argument error! value is " + obj, e4);
            }
        } else if (strTrim.length() <= 3 || !(strTrim.endsWith("rpx") || strTrim.endsWith("upx"))) {
            try {
                return Double.parseDouble(strTrim.substring(0, strTrim.indexOf("px")));
            } catch (NumberFormatException e5) {
                WXLogUtils.e("Argument format error! value is " + obj, e5);
            } catch (Exception e6) {
                WXLogUtils.e("Argument error! value is " + obj, e6);
            }
        } else {
            try {
                return transferRpxAndUpx(strTrim.substring(0, strTrim.length() - 3), 750.0f);
            } catch (NumberFormatException e7) {
                WXLogUtils.e("Argument format error! value is " + obj, e7);
            } catch (Exception e8) {
                WXLogUtils.e("Argument error! value is " + obj, e8);
            }
        }
        return 0.0d;
    }

    public static long getFixUnixTime() {
        return sInterval + SystemClock.uptimeMillis();
    }

    public static float getFloat(Object obj) {
        return getFloat(obj, Float.valueOf(Float.NaN)).floatValue();
    }

    public static float getFloatByViewport(Object obj, float f) {
        return floatByViewport(obj, f);
    }

    public static int getInt(Object obj) throws Throwable {
        return getInteger(obj, 0).intValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v16, types: [java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r9v17 */
    /* JADX WARN: Type inference failed for: r9v23, types: [java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r9v24 */
    /* JADX WARN: Type inference failed for: r9v25 */
    public static Integer getInteger(Object obj, Integer num) throws Throwable {
        if (obj == 0) {
            return num;
        }
        String strTrim = obj.toString().trim();
        Integer num2 = sCache.get(strTrim);
        if (num2 != null) {
            return num2;
        }
        String strSubstring = strTrim.length() >= 2 ? strTrim.substring(strTrim.length() - 2, strTrim.length()) : "";
        if (TextUtils.equals("wx", strSubstring)) {
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.w("the value of " + obj + " use wx unit, which will be not supported soon after.");
            }
            try {
                obj = Integer.valueOf((int) transferWx(strTrim, 750.0f));
            } catch (NumberFormatException e) {
                WXLogUtils.e("Argument format error! value is " + obj, e);
            } catch (Exception e2) {
                WXLogUtils.e("Argument error! value is " + obj, e2);
            }
        } else if (!TextUtils.equals("px", strSubstring)) {
            try {
            } catch (NumberFormatException e3) {
                WXLogUtils.e("Argument format error! value is " + obj, e3);
            } catch (Exception e4) {
                WXLogUtils.e("Argument error! value is " + obj, e4);
            }
            if (TextUtils.isEmpty(strTrim)) {
                if (WXEnvironment.isApkDebugable()) {
                    WXLogUtils.e("Argument value is null, df is" + num);
                }
                obj = num;
            } else {
                obj = strTrim.contains(Operators.DOT_STR) ? Integer.valueOf((int) parseFloat(strTrim)) : Integer.valueOf(Integer.parseInt(strTrim));
            }
        } else if (strTrim.length() <= 3 || !(strTrim.endsWith("rpx") || strTrim.endsWith("upx"))) {
            try {
                String strSubstring2 = strTrim.substring(0, strTrim.length() - 2);
                obj = (TextUtils.isEmpty(strSubstring2) || !strSubstring2.contains(Operators.DOT_STR)) ? Integer.valueOf(Integer.parseInt(strSubstring2)) : Integer.valueOf((int) parseFloat(strSubstring2));
            } catch (NumberFormatException e5) {
                WXLogUtils.e("Argument format error! value is " + obj, e5);
            } catch (Exception e6) {
                WXLogUtils.e("Argument error! value is " + obj, e6);
            }
        } else {
            try {
                obj = Integer.valueOf((int) transferRpxAndUpx(strTrim.substring(0, strTrim.length() - 3), 750.0f));
            } catch (NumberFormatException e7) {
                WXLogUtils.e("Argument format error! value is " + obj, e7);
            } catch (Exception e8) {
                WXLogUtils.e("Argument error! value is " + obj, e8);
            }
        }
        if (obj != 0 && !obj.equals(num)) {
            sCache.put(strTrim, obj);
        }
        return obj;
    }

    @Deprecated
    public static long getLong(Object obj) {
        if (obj == null) {
            return 0L;
        }
        String strTrim = obj.toString().trim();
        if (strTrim.endsWith("wx")) {
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.w("the value of " + obj + " use wx unit, which will be not supported soon after.");
            }
            try {
                return (long) transferWx(strTrim, 750.0f);
            } catch (NumberFormatException e) {
                WXLogUtils.e("Argument format error! value is " + obj, e);
            } catch (Exception e2) {
                WXLogUtils.e("Argument error! value is " + obj, e2);
            }
        } else if (!strTrim.endsWith("px")) {
            try {
                return Long.parseLong(strTrim);
            } catch (NumberFormatException e3) {
                WXLogUtils.e("Argument format error! value is " + obj, e3);
            } catch (Exception e4) {
                WXLogUtils.e("Argument error! value is " + obj, e4);
            }
        } else if (strTrim.length() <= 3 || !(strTrim.endsWith("rpx") || strTrim.endsWith("upx"))) {
            try {
                return Long.parseLong(strTrim.substring(0, strTrim.indexOf("px")));
            } catch (NumberFormatException e5) {
                WXLogUtils.e("Argument format error! value is " + obj, e5);
            } catch (Exception e6) {
                WXLogUtils.e("Argument error! value is " + obj, e6);
            }
        } else {
            try {
                return (long) transferRpxAndUpx(strTrim.substring(0, strTrim.length() - 3), 750.0f);
            } catch (NumberFormatException e7) {
                WXLogUtils.e("Argument format error! value is " + obj, e7);
            } catch (Exception e8) {
                WXLogUtils.e("Argument error! value is " + obj, e8);
            }
        }
        return 0L;
    }

    public static int getNumberInt(Object obj, int i) {
        if (obj != null) {
            if (obj instanceof Number) {
                return ((Number) obj).intValue();
            }
            try {
                String string = obj.toString();
                return string.indexOf(46) >= 0 ? (int) Float.parseFloat(obj.toString()) : Integer.parseInt(string);
            } catch (Exception unused) {
            }
        }
        return i;
    }

    public static String getString(Object obj, String str) {
        return obj == null ? str : obj instanceof String ? (String) obj : obj.toString();
    }

    private static int indexLineBreak(String str, int i) {
        int iIndexOf = str.indexOf("\r", i);
        if (iIndexOf == -1) {
            iIndexOf = str.indexOf("\n", i);
        }
        return iIndexOf == -1 ? str.indexOf("\r\n", i) : iIndexOf;
    }

    @Deprecated
    public static boolean isTabletDevice() {
        try {
            return (WXEnvironment.getApplication().getResources().getConfiguration().screenLayout & 15) >= 3;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isUiThread() {
        return Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId();
    }

    public static boolean isUndefined(float f) {
        return Float.isNaN(f);
    }

    public static float parseFloat(Object obj) {
        return parseFloat(String.valueOf(obj));
    }

    public static int parseInt(String str) {
        try {
            if (TextUtils.isEmpty(str) || str.contains(Operators.DOT_STR)) {
                return 0;
            }
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            if (!WXEnvironment.isApkDebugable()) {
                return 0;
            }
            WXLogUtils.e(WXLogUtils.getStackTrace(e));
            return 0;
        }
    }

    private static int parsePercent(String str, int i) {
        return (int) ((Float.parseFloat(str) / 100.0f) * i);
    }

    public static int parseUnitOrPercent(String str, int i) {
        int iLastIndexOf = str.lastIndexOf(37);
        return iLastIndexOf != -1 ? parsePercent(str.substring(0, iLastIndexOf), i) : parseInt(str);
    }

    private static String[] splitLineBreak(String str) {
        String[] strArrSplit = str.split("\r");
        if (strArrSplit.length == 1) {
            strArrSplit = str.split("\n");
        }
        return strArrSplit.length == 1 ? str.split("\r\n") : strArrSplit;
    }

    private static float transferRpxAndUpx(String str, float f) throws NumberFormatException {
        if (str == null) {
            return 0.0f;
        }
        if (str.endsWith("rpx")) {
            str = str.substring(0, str.indexOf("rpx"));
        } else if (str.endsWith("upx")) {
            str = str.substring(0, str.indexOf("upx"));
        }
        return (WXEnvironment.getViewProt() / f) * Float.parseFloat(str);
    }

    private static float transferWx(String str, float f) throws NumberFormatException {
        if (str == null) {
            return 0.0f;
        }
        if (str.endsWith("wx")) {
            str = str.substring(0, str.indexOf("wx"));
        }
        return ((WXEnvironment.sApplication.getResources().getDisplayMetrics().density * Float.parseFloat(str)) * f) / WXViewUtils.getScreenWidth();
    }

    public static Float getFloat(Object obj, Float f) {
        if (obj != null) {
            String strTrim = obj.toString().trim();
            if ("auto".equals(strTrim) || Constants.Name.UNDEFINED.equals(strTrim) || TextUtils.isEmpty(strTrim)) {
                WXLogUtils.e("Argument Warning ! value is " + strTrim + "And default Value:NaN");
                return f;
            }
            if (strTrim.endsWith("wx")) {
                try {
                    return Float.valueOf(transferWx(strTrim, 750.0f));
                } catch (NumberFormatException e) {
                    WXLogUtils.e("Argument format error! value is " + obj, e);
                } catch (Exception e2) {
                    WXLogUtils.e("Argument error! value is " + obj, e2);
                }
            } else if (!strTrim.endsWith("px")) {
                try {
                    return Float.valueOf(Float.parseFloat(strTrim));
                } catch (NumberFormatException e3) {
                    WXLogUtils.e("Argument format error! value is " + obj, e3);
                } catch (Exception e4) {
                    WXLogUtils.e("Argument error! value is " + obj, e4);
                }
            } else if (strTrim.length() <= 3 || !(strTrim.endsWith("rpx") || strTrim.endsWith("upx"))) {
                try {
                    return Float.valueOf(Float.parseFloat(strTrim.substring(0, strTrim.indexOf("px"))));
                } catch (NumberFormatException e5) {
                    WXLogUtils.e("Argument format error! value is " + obj, e5);
                } catch (Exception e6) {
                    WXLogUtils.e("Argument error! value is " + obj, e6);
                }
            } else {
                try {
                    return Float.valueOf(transferRpxAndUpx(strTrim.substring(0, strTrim.length() - 3), 750.0f));
                } catch (NumberFormatException e7) {
                    WXLogUtils.e("Argument format error! value is " + obj, e7);
                } catch (Exception e8) {
                    WXLogUtils.e("Argument error! value is " + obj, e8);
                }
            }
        }
        return f;
    }

    public static float getFloatByViewport(Object obj, int i) {
        return floatByViewport(obj, i);
    }

    public static float parseFloat(String str) throws Throwable {
        try {
            if (!TextUtils.isEmpty(str) && !TextUtils.equals(str, "null")) {
                return Float.parseFloat(str);
            }
            if (!WXEnvironment.isApkDebugable()) {
                return 0.0f;
            }
            WXLogUtils.e("WXUtils parseFloat illegal value is " + str);
            return 0.0f;
        } catch (NumberFormatException e) {
            if (!WXEnvironment.isApkDebugable()) {
                return 0.0f;
            }
            WXLogUtils.e(WXLogUtils.getStackTrace(e));
            return 0.0f;
        }
    }

    public static int parseInt(Object obj) {
        return parseInt(String.valueOf(obj));
    }

    public static float fastGetFloat(String str) {
        return fastGetFloat(str, 2.1474836E9f);
    }
}
