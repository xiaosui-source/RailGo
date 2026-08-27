package io.dcloud.common.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import com.taobao.weex.WXEnvironment;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class LauncherUtil {
    public static String getAuthorityFromPermission(Context context, String str) {
        TextUtils.isEmpty(str);
        return "";
    }

    public static String getAuthorityFromPermissionDefault(Context context) {
        String authorityFromPermission = getAuthorityFromPermission(context, "com.android.launcher.permission.READ_SETTINGS");
        return TextUtils.isEmpty(authorityFromPermission) ? getAuthorityFromPermission(context, "com.android.launcher3.permission.READ_SETTINGS") : authorityFromPermission;
    }

    public static String getLauncherPackageName(Context context) {
        ActivityInfo activityInfo;
        Context applicationContext = context.getApplicationContext();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        ResolveInfo resolveInfoResolveActivity = applicationContext.getPackageManager().resolveActivity(intent, 0);
        return (resolveInfoResolveActivity == null || (activityInfo = resolveInfoResolveActivity.activityInfo) == null || activityInfo.packageName.equals(WXEnvironment.OS)) ? "" : resolveInfoResolveActivity.activityInfo.packageName;
    }
}
