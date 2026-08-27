package io.dcloud.p;

import android.content.Context;
import io.dcloud.p.u2;
import java.io.File;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class s0 {
    private static boolean a = false;

    public static v4 a(u2.c cVar, Context context, String str, String str2, String str3, String str4) {
        a(context);
        return new v4(cVar, context, str, str2, str3, str4);
    }

    private static void a(Context context) {
        File[] fileArrListFiles;
        if (a) {
            return;
        }
        a = true;
        if (context != null) {
            try {
                long jCurrentTimeMillis = System.currentTimeMillis() - 604800000;
                File file = new File(context.getCacheDir().getAbsolutePath() + "/dcloud_ad/img/");
                if (!file.isDirectory() || (fileArrListFiles = file.listFiles()) == null || fileArrListFiles.length <= 0) {
                    return;
                }
                for (File file2 : fileArrListFiles) {
                    if (file2.lastModified() < jCurrentTimeMillis) {
                        file2.delete();
                    }
                }
            } catch (Exception unused) {
            }
        }
    }
}
