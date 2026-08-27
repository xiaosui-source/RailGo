package com.bun.lib;

import android.content.Context;
import java.lang.reflect.InvocationTargetException;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class b {
    private static Context a;

    public static Context a() {
        try {
            return (Context) Class.forName("android.app.ActivityThread").getDeclaredMethod("currentApplication", new Class[0]).invoke(null, new Object[0]);
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void a(Context context) {
        synchronized (b.class) {
            a = context;
        }
    }

    public static Context b() {
        Context context;
        synchronized (b.class) {
            if (a == null) {
                a = a();
            }
            context = a;
        }
        return context;
    }
}
