package com.hjq.permissions;

import android.app.Activity;
import java.util.List;

/* loaded from: classes.dex */
public interface OnPermissionInterceptor {
    void deniedPermissionRequest(Activity activity, List<String> list, List<String> list2, boolean z, OnPermissionCallback onPermissionCallback);

    void finishPermissionRequest(Activity activity, List<String> list, boolean z, OnPermissionCallback onPermissionCallback);

    void grantedPermissionRequest(Activity activity, List<String> list, List<String> list2, boolean z, OnPermissionCallback onPermissionCallback);

    void launchPermissionRequest(Activity activity, List<String> list, OnPermissionCallback onPermissionCallback);

    /* renamed from: com.hjq.permissions.OnPermissionInterceptor$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void $default$finishPermissionRequest(OnPermissionInterceptor _this, Activity activity, List list, boolean z, OnPermissionCallback onPermissionCallback) {
        }

        public static void $default$grantedPermissionRequest(OnPermissionInterceptor _this, Activity activity, List list, List list2, boolean z, OnPermissionCallback onPermissionCallback) {
            if (onPermissionCallback == null) {
                return;
            }
            onPermissionCallback.onGranted(list2, z);
        }

        public static void $default$deniedPermissionRequest(OnPermissionInterceptor _this, Activity activity, List list, List list2, boolean z, OnPermissionCallback onPermissionCallback) {
            if (onPermissionCallback == null) {
                return;
            }
            onPermissionCallback.onDenied(list2, z);
        }
    }
}
