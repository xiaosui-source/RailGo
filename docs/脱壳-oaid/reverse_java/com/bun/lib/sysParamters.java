package com.bun.lib;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Keep;
import android.text.TextUtils;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class sysParamters {
    private static volatile sysParamters d;
    private String a;
    private String b;
    private String c;

    @Keep
    private String sdk_version = "10012";

    @Keep
    private String sdk_vname = "1.0.12";

    private sysParamters() {
    }

    private static PackageInfo a(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0);
        } catch (Exception e) {
            return null;
        }
    }

    public static String a(Context context) {
        if (context == null) {
            return "0.1.100";
        }
        PackageInfo packageInfoA = a(context, context.getPackageName());
        if (packageInfoA == null) {
            return null;
        }
        return packageInfoA.versionName;
    }

    public static String a(String str, String str2) {
        try {
            try {
                Class<?> cls = Class.forName("android.os.SystemProperties");
                str2 = (String) cls.getMethod("get", String.class, String.class).invoke(cls, str, "unknown");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Throwable th) {
        }
        return str2;
    }

    public static String e() {
        return "";
    }

    public static sysParamters f() {
        if (d == null) {
            synchronized (sysParamters.class) {
                if (d == null) {
                    d = new sysParamters();
                }
            }
        }
        return d;
    }

    public static String g() {
        ApplicationInfo applicationInfo = b.b().getApplicationInfo();
        return (applicationInfo == null || TextUtils.isEmpty(applicationInfo.packageName)) ? h() : applicationInfo.packageName;
    }

    private static String h() {
        return b.b().getPackageName();
    }

    public String a() {
        if (!TextUtils.isEmpty(this.a)) {
            return this.a;
        }
        this.a = a(b.b());
        return this.a;
    }

    public String b() {
        if (!TextUtils.isEmpty(this.c)) {
            return this.c;
        }
        this.c = Uri.encode(Build.MANUFACTURER);
        return this.c;
    }

    public String c() {
        if (!TextUtils.isEmpty(this.b)) {
            return this.b;
        }
        this.b = Build.MODEL;
        this.b = this.b.replace(" ", "-");
        return this.b;
    }

    public String d() {
        return this.sdk_version;
    }
}
