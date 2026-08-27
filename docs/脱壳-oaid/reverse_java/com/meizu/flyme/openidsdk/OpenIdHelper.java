package com.meizu.flyme.openidsdk;

import android.content.Context;
import android.util.Log;
import java.lang.reflect.Method;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class OpenIdHelper {
    public static String a(Context context) {
        b bVarA = b.a();
        return bVarA.a(context.getApplicationContext(), bVarA.c);
    }

    public static final boolean a() throws NoSuchMethodException, SecurityException {
        Context context;
        try {
            Method method = Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class[0]);
            method.setAccessible(true);
            context = (Context) method.invoke(null, new Object[0]);
        } catch (Exception e) {
            Log.e("OpenIdHelper", "ActivityThread:currentApplication --> " + e.toString());
            context = null;
        }
        if (context == null) {
            return false;
        }
        return b.a().a(context, false);
    }

    public static String b(Context context) {
        b bVarA = b.a();
        return bVarA.a(context.getApplicationContext(), bVarA.b);
    }

    public static String c(Context context) {
        b bVarA = b.a();
        return bVarA.a(context.getApplicationContext(), bVarA.a);
    }

    public static String d(Context context) {
        b bVarA = b.a();
        return bVarA.a(context.getApplicationContext(), bVarA.d);
    }
}
