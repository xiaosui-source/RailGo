package io.dcloud.common.util;

import android.text.TextUtils;
import io.dcloud.application.DCLoudApplicationImpl;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class CheckSignatureUtil {
    public static boolean check(String str) {
        String signature = getSignature(str);
        if (TextUtils.isEmpty(signature)) {
            return true;
        }
        String appSignatureMd5 = LoadAppUtils.getAppSignatureMd5(DCLoudApplicationImpl.self().getContext().getApplicationContext(), DCLoudApplicationImpl.self().getContext().getPackageName());
        return TextUtils.isEmpty(appSignatureMd5) || appSignatureMd5.equalsIgnoreCase(signature);
    }

    public static String getSignature(String str) {
        String[] apkFileSignatureAndPackageName = LoadAppUtils.getApkFileSignatureAndPackageName(DCLoudApplicationImpl.self().getContext().getApplicationContext(), str);
        return (apkFileSignatureAndPackageName == null || apkFileSignatureAndPackageName.length <= 0) ? "" : apkFileSignatureAndPackageName[0];
    }
}
