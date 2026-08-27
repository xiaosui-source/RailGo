package com.hjq.permissions;

/* loaded from: classes.dex */
public interface OnPermissionPageCallback {

    /* renamed from: com.hjq.permissions.OnPermissionPageCallback$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void $default$onDenied(OnPermissionPageCallback _this) {
        }
    }

    void onDenied();

    void onGranted();
}
