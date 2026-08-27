package io.dcloud.common.util;

import android.content.Context;
import com.sample.breakpad.BreakpadInit;
import java.io.File;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class NativeCrashManager {
    public static void initNativeCrash(Context context) {
        try {
            File file = new File(context.getExternalCacheDir(), "dcCrashDump");
            if (!file.exists()) {
                file.mkdirs();
            }
            BreakpadInit.initBreakpad(file.getAbsolutePath());
        } catch (Exception unused) {
        }
    }
}
