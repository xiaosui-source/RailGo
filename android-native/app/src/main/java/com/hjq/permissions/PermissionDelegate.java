package com.hjq.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/* loaded from: classes.dex */
public interface PermissionDelegate {
    Intent getPermissionIntent(Context context, String str);

    boolean isDoNotAskAgainPermission(Activity activity, String str);

    boolean isGrantedPermission(Context context, String str);
}
