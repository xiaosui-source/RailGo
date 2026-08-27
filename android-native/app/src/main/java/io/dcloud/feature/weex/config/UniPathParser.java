package io.dcloud.feature.weex.config;

import io.dcloud.common.util.BaseInfo;
import java.io.File;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class UniPathParser {
    public static String getAndroidPath(String str) {
        String str2 = BaseInfo.sCacheFsAppsPath;
        String str3 = BaseInfo.sDefaultBootApp + "/www/" + str;
        File file = new File(str2 + str3);
        if (file.exists()) {
            return file.getPath();
        }
        return "file:///android_asset/apps/" + str3;
    }
}
