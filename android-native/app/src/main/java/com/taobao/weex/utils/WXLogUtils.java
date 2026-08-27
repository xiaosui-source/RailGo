package com.taobao.weex.utils;

import android.text.TextUtils;
import android.util.Log;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.performance.WXStateRecord;
import io.dcloud.feature.uniapp.utils.AbsLogLevel;
import io.dcloud.weex.ConsoleLogUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXLogUtils {
    private static final String CLAZZ_NAME_LOG_UTIL = "com.taobao.weex.devtools.common.LogUtil";
    public static final String WEEX_PERF_TAG = "weex_perf";
    public static final String WEEX_TAG = "weex";
    private static StringBuilder builder = new StringBuilder(50);
    private static HashMap<String, Class> clazzMaps;
    private static boolean isDebug;
    private static List<JsLogWatcher> jsLogWatcherList;
    private static LogWatcher sLogWatcher;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface JsLogWatcher {
        void onJsLog(int i, String str);
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface LogWatcher {
        void onLog(String str, String str2, String str3);
    }

    static {
        HashMap<String, Class> map = new HashMap<>(2);
        clazzMaps = map;
        isDebug = true;
        map.put(CLAZZ_NAME_LOG_UTIL, loadClass(CLAZZ_NAME_LOG_UTIL));
        jsLogWatcherList = new ArrayList();
    }

    public static void d(String str) {
        d("weex", str);
    }

    public static void e(String str) {
        e("weex", str);
    }

    public static void eTag(String str, Throwable th) {
        if (WXEnvironment.isApkDebugable()) {
            e(str, getStackTrace(th));
        }
    }

    private static LogLevel getLogLevel(String str) {
        String strTrim = str.trim();
        strTrim.getClass();
        strTrim.hashCode();
        switch (strTrim) {
            case "__INFO":
                return LogLevel.INFO;
            case "__WARN":
                return LogLevel.WARN;
            case "__LOG":
                return LogLevel.INFO;
            case "__DEBUG":
                return LogLevel.DEBUG;
            case "__ERROR":
                return LogLevel.ERROR;
            default:
                return LogLevel.DEBUG;
        }
    }

    public static String getStackTrace(Throwable th) throws Throwable {
        PrintWriter printWriter;
        if (th == null) {
            return "";
        }
        StringWriter stringWriter = null;
        try {
            StringWriter stringWriter2 = new StringWriter();
            try {
                printWriter = new PrintWriter(stringWriter2);
                try {
                    th.printStackTrace(printWriter);
                    printWriter.flush();
                    stringWriter2.flush();
                    try {
                        stringWriter2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    printWriter.close();
                    return stringWriter2.toString();
                } catch (Throwable th2) {
                    th = th2;
                    stringWriter = stringWriter2;
                    if (stringWriter != null) {
                        try {
                            stringWriter.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (printWriter == null) {
                        throw th;
                    }
                    printWriter.close();
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                printWriter = null;
            }
        } catch (Throwable th4) {
            th = th4;
            printWriter = null;
        }
    }

    public static void i(String str) {
        i("weex", str);
    }

    public static void info(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        i("weex", str);
    }

    private static Class loadClass(String str) throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName(str);
            try {
                clazzMaps.put(str, cls);
                return cls;
            } catch (ClassNotFoundException unused) {
                return cls;
            }
        } catch (ClassNotFoundException unused2) {
            return null;
        }
    }

    private static void log(String str, String str2, AbsLogLevel absLogLevel) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str) || absLogLevel == null || TextUtils.isEmpty(absLogLevel.getName()) || !isDebug) {
            return;
        }
        if (absLogLevel == LogLevel.ERROR && !TextUtils.isEmpty(str2) && str2.contains("IPCException")) {
            WXStateRecord.getInstance().recordIPCException("ipc", str2);
        }
        ConsoleLogUtils.consoleLog(str, str2, absLogLevel);
        LogWatcher logWatcher = sLogWatcher;
        if (logWatcher != null) {
            logWatcher.onLog(absLogLevel.getName(), str, str2);
        }
        if (WXEnvironment.isApkDebugable()) {
            if (absLogLevel.getValue() - WXEnvironment.sLogLevel.getValue() >= 0) {
                Log.println(absLogLevel.getPriority(), str, str2);
                writeConsoleLog(absLogLevel.getName(), str2);
                return;
            }
            return;
        }
        if (absLogLevel.getValue() - LogLevel.WARN.getValue() < 0 || absLogLevel.getValue() - WXEnvironment.sLogLevel.getValue() < 0) {
            return;
        }
        Log.println(absLogLevel.getPriority(), str, str2);
    }

    public static void p(String str) {
        d(WEEX_PERF_TAG, str);
    }

    public static void performance(String str, byte[] bArr) {
    }

    public static void renderPerformanceLog(String str, long j) {
        if (WXEnvironment.isApkDebugable()) {
            return;
        }
        WXEnvironment.isPerf();
    }

    public static void setIsDebug(boolean z) {
        isDebug = z;
    }

    public static void setJsLogWatcher(JsLogWatcher jsLogWatcher) {
        if (jsLogWatcherList.contains(jsLogWatcher)) {
            return;
        }
        jsLogWatcherList.add(jsLogWatcher);
    }

    public static void setLogWatcher(LogWatcher logWatcher) {
        sLogWatcher = logWatcher;
    }

    public static void v(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        v("weex", str);
    }

    public static void w(String str) {
        w("weex", str);
    }

    private static void writeConsoleLog(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (WXEnvironment.isApkDebugable()) {
            try {
                Class cls = clazzMaps.get(CLAZZ_NAME_LOG_UTIL);
                if (cls != null) {
                    cls.getMethod("log", String.class, String.class).invoke(cls, str, str2);
                }
            } catch (Exception unused) {
                Log.d("weex", "LogUtil not found!");
            }
        }
    }

    public static void wtf(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        wtf("weex", str);
    }

    public static void d(String str, byte[] bArr) {
        d(str, new String(bArr));
    }

    public static void e(String str, byte[] bArr) {
        e(str, new String(bArr));
    }

    public static void i(String str, byte[] bArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        i(str, new String(bArr));
    }

    public static void p(String str, Throwable th) {
        if (WXEnvironment.isApkDebugable()) {
            p(str + getStackTrace(th));
        }
    }

    public static void v(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        log(str, str2, LogLevel.VERBOSE);
    }

    public static void w(String str, byte[] bArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        w(str, new String(bArr));
    }

    public static void wtf(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        log(str, str2, LogLevel.WTF);
    }

    public static void d(String str, String str2) {
        List<JsLogWatcher> list;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        log(str, str2, LogLevel.DEBUG);
        if (!WXEnvironment.isApkDebugable() || !"jsLog".equals(str) || (list = jsLogWatcherList) == null || list.size() <= 0) {
            return;
        }
        for (JsLogWatcher jsLogWatcher : jsLogWatcherList) {
            if (str2.endsWith("__DEBUG")) {
                jsLogWatcher.onJsLog(3, str2.replace("__DEBUG", ""));
            } else if (str2.endsWith("__INFO")) {
                jsLogWatcher.onJsLog(3, str2.replace("__INFO", ""));
            } else if (str2.endsWith("__WARN")) {
                jsLogWatcher.onJsLog(3, str2.replace("__WARN", ""));
            } else if (str2.endsWith("__ERROR")) {
                jsLogWatcher.onJsLog(3, str2.replace("__ERROR", ""));
            } else {
                jsLogWatcher.onJsLog(3, str2);
            }
        }
    }

    public static void e(String str, String str2) {
        log(str, str2, LogLevel.ERROR);
    }

    public static void i(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        log(str, str2, LogLevel.INFO);
    }

    public static void v(String str, Throwable th) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (WXEnvironment.isApkDebugable()) {
            v(str + getStackTrace(th));
        }
    }

    public static void w(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        log(str, str2, LogLevel.WARN);
    }

    public static void wtf(String str, Throwable th) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (WXEnvironment.isApkDebugable()) {
            wtf(str + getStackTrace(th));
        }
    }

    public static void e(String str, Throwable th) {
        e(str + getStackTrace(th));
    }

    public static void i(String str, Throwable th) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (WXEnvironment.isApkDebugable()) {
            info(str + getStackTrace(th));
        }
    }

    public static void w(String str, Throwable th) {
        w(str + getStackTrace(th));
    }

    public static void d(String str, Throwable th) {
        if (WXEnvironment.isApkDebugable()) {
            d(str + getStackTrace(th));
        }
    }
}
