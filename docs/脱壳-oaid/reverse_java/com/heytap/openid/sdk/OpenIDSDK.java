package com.heytap.openid.sdk;

import android.content.Context;
import com.heytap.openid.sdk.c;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class OpenIDSDK {
    public static String a(Context context) {
        if (a.a) {
            return c.a.a.a(a.a(context), "AUID");
        }
        throw new RuntimeException("SDK Need Init First!");
    }

    public static boolean a() {
        if (a.a) {
            return a.b;
        }
        throw new RuntimeException("SDK Need Init First!");
    }

    public static String b(Context context) {
        if (a.a) {
            return c.a.a.a(a.a(context), "OUID");
        }
        throw new RuntimeException("SDK Need Init First!");
    }

    public static String c(Context context) {
        if (a.a) {
            return c.a.a.a(a.a(context), "DUID");
        }
        throw new RuntimeException("SDK Need Init First!");
    }

    public static void d(Context context) {
        a.b = c.a.a.a(a.a(context));
        a.a = true;
    }
}
