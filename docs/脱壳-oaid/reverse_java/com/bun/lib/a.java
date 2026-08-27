package com.bun.lib;

import android.util.Log;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class a {
    private static a b;
    private int a = -1;

    private a() {
    }

    public static void a(String str, String str2) {
        if (a()) {
            Log.d(str, str2);
        }
    }

    public static void a(String str, String str2, Throwable th) {
        if (a()) {
            Log.d(str, str2, th);
        }
    }

    public static void a(boolean z) {
        b().a = z ? 1 : 0;
    }

    public static boolean a() {
        return b().a == 1;
    }

    public static a b() {
        if (b == null) {
            synchronized (a.class) {
                if (b == null) {
                    b = new a();
                }
            }
        }
        return b;
    }

    public static void b(String str, String str2) {
        if (a()) {
            Log.i(str, str2);
        }
    }

    public static void b(String str, String str2, Throwable th) {
        if (a()) {
            Log.e(str, str2, th);
        }
    }
}
