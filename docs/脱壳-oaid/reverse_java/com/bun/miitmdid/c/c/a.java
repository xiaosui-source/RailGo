package com.bun.miitmdid.c.c;

import android.text.TextUtils;
import com.bun.lib.sysParamters;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class a {
    public static com.bun.miitmdid.c.a a() {
        return b() ? com.bun.miitmdid.c.a.FREEMEOS : c() ? com.bun.miitmdid.c.a.SSUIOS : com.bun.miitmdid.c.a.UNSUPPORT;
    }

    private static boolean b() {
        String strA = sysParamters.a("ro.build.freeme.label", "");
        if (TextUtils.isEmpty(strA)) {
            return false;
        }
        return strA.equalsIgnoreCase("FreemeOS");
    }

    private static boolean c() {
        return !TextUtils.isEmpty(sysParamters.a("ro.ssui.product", ""));
    }
}
