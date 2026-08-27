package io.dcloud.p;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import io.dcloud.p.x0;
import io.dcloud.sdk.base.entry.AdData;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class k4 {
    private static int a = 1;
    private static x0 b;
    private static SharedPreferences c;
    private static k4 d;
    private static Map e = new ConcurrentHashMap();

    k4() {
    }

    private x0 a(Context context) {
        if (b == null) {
            synchronized (k4.class) {
                if (b == null) {
                    try {
                        b = x0.a(new File(context.getFilesDir(), "dcloudcache"), a, 1, 10240L);
                    } catch (IOException unused) {
                    }
                }
            }
        }
        return b;
    }

    private static void c(Context context) {
        if (c == null) {
            synchronized (k4.class) {
                if (c == null) {
                    c = context.getSharedPreferences("dcloudcache", 0);
                }
            }
        }
    }

    public Map b(Context context) {
        c(context);
        Map<String, ?> all = c.getAll();
        if (all != null) {
            for (String str : all.keySet()) {
                if (!TextUtils.isEmpty(str) && !e.containsKey(str)) {
                    io.dcloud.sdk.base.entry.a aVar = new io.dcloud.sdk.base.entry.a(str);
                    aVar.a((String) all.get(str));
                    e.put(str, aVar);
                }
            }
        }
        return e;
    }

    public static k4 a() {
        if (d == null) {
            synchronized (k4.class) {
                if (d == null) {
                    d = new k4();
                    e.clear();
                }
            }
        }
        return d;
    }

    public void b(Context context, String str) {
        e.remove(str);
        c(context);
        SharedPreferences.Editor editorEdit = c.edit();
        editorEdit.remove(str);
        editorEdit.apply();
        try {
            a(context).d(str);
        } catch (IOException unused) {
        }
    }

    private void a(Context context, String str, String str2) {
        c(context);
        SharedPreferences.Editor editorEdit = c.edit();
        editorEdit.putString(str, str2);
        editorEdit.apply();
    }

    public void a(Context context, AdData adData, String str) throws IOException {
        io.dcloud.sdk.base.entry.a aVar = new io.dcloud.sdk.base.entry.a(adData.k());
        aVar.a(adData.h());
        aVar.a(adData);
        e.put(adData.k(), aVar);
        try {
            a(context, adData.k(), adData.h());
            x0.c cVarA = a(context).a(adData.k());
            OutputStream outputStreamA = cVarA.a(0);
            outputStreamA.write(str.getBytes());
            outputStreamA.close();
            cVarA.b();
            a(context).flush();
        } catch (Exception unused) {
        }
    }

    public String a(Context context, String str) {
        try {
            return a(context).b(str).getString(0);
        } catch (Exception unused) {
            return null;
        }
    }
}
