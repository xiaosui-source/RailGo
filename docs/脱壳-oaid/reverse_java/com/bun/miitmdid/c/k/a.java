package com.bun.miitmdid.c.k;

import android.content.Context;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class a {
    private static Object a;
    private static Class<?> b;
    private static Method c;
    private static Method d;
    private static Method e;

    static {
        try {
            b = Class.forName("com.android.id.impl.IdProviderImpl");
            a = b.newInstance();
        } catch (Exception e2) {
            com.bun.lib.a.a("IdentifierManager", "reflect exception!", e2);
        }
        try {
            if (b != null) {
                c = b.getMethod("getOAID", Context.class);
            }
        } catch (Exception e3) {
            com.bun.lib.a.a("IdentifierManager", "reflect exception!", e3);
        }
        try {
            if (b != null) {
                d = b.getMethod("getVAID", Context.class);
            }
        } catch (Exception e4) {
            com.bun.lib.a.a("IdentifierManager", "reflect exception!", e4);
        }
        try {
            if (b != null) {
                e = b.getMethod("getAAID", Context.class);
            }
        } catch (Exception e5) {
            com.bun.lib.a.a("IdentifierManager", "reflect exception!", e5);
        }
    }

    public static String a(Context context) {
        return a(context, e);
    }

    private static String a(Context context, Method method) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object obj = a;
        if (obj != null && method != null) {
            try {
                Object objInvoke = method.invoke(obj, context);
                if (objInvoke != null) {
                    return (String) objInvoke;
                }
            } catch (Exception e2) {
                com.bun.lib.a.a("IdentifierManager", "invoke exception!", e2);
            }
        }
        return null;
    }

    public static boolean a() {
        return (b == null || a == null) ? false : true;
    }

    public static String b(Context context) {
        return a(context, c);
    }

    public static String c(Context context) {
        return a(context, d);
    }
}
