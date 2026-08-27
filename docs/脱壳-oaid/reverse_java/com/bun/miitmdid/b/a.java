package com.bun.miitmdid.b;

import android.text.TextUtils;
import com.bun.lib.sysParamters;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class a {
    private static String a;

    public static String a() {
        if (!TextUtils.isEmpty(a)) {
            return a;
        }
        a = "miitmdid(sdkv_" + sysParamters.f().d() + ")";
        return a;
    }
}
