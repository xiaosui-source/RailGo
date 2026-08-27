package io.dcloud.common.constant;

import android.text.TextUtils;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.adapter.util.AndroidResources;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public final class StringConst extends AndroidResources implements AbsoluteConst {
    private static long sChangeTime;

    public static int getIntSF(String str) {
        if (TextUtils.isEmpty(str)) {
            return 1;
        }
        if ("barcode".equals(str)) {
            return 2;
        }
        if ("scheme".equals(str)) {
            return 3;
        }
        if (IApp.ConfigProperty.CONFIG_STREAM.equals(str)) {
            return 6;
        }
        if (IApp.ConfigProperty.CONFIG_SHORTCUT.equals(str)) {
            return 5;
        }
        if ("push".equals(str)) {
            return 4;
        }
        if ("myapp".equals(str)) {
            return 7;
        }
        if ("browser".equals(str)) {
            return 8;
        }
        if (str.indexOf("third:") == 0) {
            return 9;
        }
        if ("favorite".equals(str)) {
            return 10;
        }
        if ("engines".equals(str)) {
            return 11;
        }
        if ("apush".equals(str)) {
            return 40;
        }
        return "speech".equals(str) ? 30 : 1;
    }
}
