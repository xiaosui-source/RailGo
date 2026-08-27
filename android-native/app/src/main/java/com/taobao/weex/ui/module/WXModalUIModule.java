package com.taobao.weex.ui.module;

import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.Toast;
import com.taobao.weex.WXSDKEngine;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXModalUIModule extends WXSDKEngine.DestroyableModule {
    public static final String CANCEL = "Cancel";
    public static final String CANCEL_TITLE = "cancelTitle";
    public static final String DATA = "data";
    public static final String DEFAULT = "default";
    public static final String DURATION = "duration";
    public static final String GRAVITY = "gravity";
    public static final String MESSAGE = "message";
    public static final String OK = "OK";
    public static final String OK_TITLE = "okTitle";
    public static final String RESULT = "result";
    private Dialog activeDialog;
    private Toast toast;

    private void tracking(Dialog dialog) {
        this.activeDialog = dialog;
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.taobao.weex.ui.module.WXModalUIModule.6
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                WXModalUIModule.this.activeDialog = null;
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0047  */
    @com.taobao.weex.annotation.JSMethod(uiThread = true)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void alert(com.alibaba.fastjson.JSONObject r5, final com.taobao.weex.bridge.JSCallback r6) {
        /*
            r4 = this;
            com.taobao.weex.WXSDKInstance r0 = r4.mWXSDKInstance
            android.content.Context r0 = r0.getContext()
            boolean r0 = r0 instanceof android.app.Activity
            if (r0 == 0) goto L5f
            java.lang.String r0 = "OK"
            java.lang.String r1 = ""
            if (r5 == 0) goto L28
            java.lang.String r2 = "message"
            java.lang.String r2 = r5.getString(r2)     // Catch: java.lang.Exception -> L1f
            java.lang.String r3 = "okTitle"
            java.lang.String r5 = r5.getString(r3)     // Catch: java.lang.Exception -> L1d
            goto L2a
        L1d:
            r5 = move-exception
            goto L21
        L1f:
            r5 = move-exception
            r2 = r1
        L21:
            java.lang.String r3 = "[WXModalUIModule] alert param parse error "
            com.taobao.weex.utils.WXLogUtils.e(r3, r5)
            r5 = r0
            goto L2a
        L28:
            r5 = r0
            r2 = r1
        L2a:
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 == 0) goto L31
            goto L32
        L31:
            r1 = r2
        L32:
            android.app.AlertDialog$Builder r2 = new android.app.AlertDialog$Builder
            com.taobao.weex.WXSDKInstance r3 = r4.mWXSDKInstance
            android.content.Context r3 = r3.getContext()
            r2.<init>(r3)
            r2.setMessage(r1)
            boolean r1 = android.text.TextUtils.isEmpty(r5)
            if (r1 == 0) goto L47
            goto L48
        L47:
            r0 = r5
        L48:
            com.taobao.weex.ui.module.WXModalUIModule$1 r5 = new com.taobao.weex.ui.module.WXModalUIModule$1
            r5.<init>()
            r2.setPositiveButton(r0, r5)
            android.app.AlertDialog r5 = r2.create()
            r6 = 0
            r5.setCanceledOnTouchOutside(r6)
            r5.show()
            r4.tracking(r5)
            return
        L5f:
            java.lang.String r5 = "[WXModalUIModule] when call alert mWXSDKInstance.getContext() must instanceof Activity"
            com.taobao.weex.utils.WXLogUtils.e(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.ui.module.WXModalUIModule.alert(com.alibaba.fastjson.JSONObject, com.taobao.weex.bridge.JSCallback):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x005c  */
    @com.taobao.weex.annotation.JSMethod(uiThread = true)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void confirm(com.alibaba.fastjson.JSONObject r7, final com.taobao.weex.bridge.JSCallback r8) {
        /*
            r6 = this;
            com.taobao.weex.WXSDKInstance r0 = r6.mWXSDKInstance
            android.content.Context r0 = r0.getContext()
            boolean r0 = r0 instanceof android.app.Activity
            if (r0 == 0) goto L7c
            java.lang.String r0 = "Cancel"
            java.lang.String r1 = "OK"
            java.lang.String r2 = ""
            if (r7 == 0) goto L34
            java.lang.String r3 = "message"
            java.lang.String r3 = r7.getString(r3)     // Catch: java.lang.Exception -> L2a
            java.lang.String r4 = "okTitle"
            java.lang.String r4 = r7.getString(r4)     // Catch: java.lang.Exception -> L27
            java.lang.String r5 = "cancelTitle"
            java.lang.String r7 = r7.getString(r5)     // Catch: java.lang.Exception -> L25
            goto L37
        L25:
            r7 = move-exception
            goto L2d
        L27:
            r7 = move-exception
            r4 = r1
            goto L2d
        L2a:
            r7 = move-exception
            r4 = r1
            r3 = r2
        L2d:
            java.lang.String r5 = "[WXModalUIModule] confirm param parse error "
            com.taobao.weex.utils.WXLogUtils.e(r5, r7)
            r7 = r0
            goto L37
        L34:
            r7 = r0
            r4 = r1
            r3 = r2
        L37:
            boolean r5 = android.text.TextUtils.isEmpty(r3)
            if (r5 == 0) goto L3e
            goto L3f
        L3e:
            r2 = r3
        L3f:
            android.app.AlertDialog$Builder r3 = new android.app.AlertDialog$Builder
            com.taobao.weex.WXSDKInstance r5 = r6.mWXSDKInstance
            android.content.Context r5 = r5.getContext()
            r3.<init>(r5)
            r3.setMessage(r2)
            boolean r2 = android.text.TextUtils.isEmpty(r4)
            if (r2 == 0) goto L54
            goto L55
        L54:
            r1 = r4
        L55:
            boolean r2 = android.text.TextUtils.isEmpty(r7)
            if (r2 == 0) goto L5c
            goto L5d
        L5c:
            r0 = r7
        L5d:
            com.taobao.weex.ui.module.WXModalUIModule$2 r7 = new com.taobao.weex.ui.module.WXModalUIModule$2
            r7.<init>()
            r3.setPositiveButton(r1, r7)
            com.taobao.weex.ui.module.WXModalUIModule$3 r7 = new com.taobao.weex.ui.module.WXModalUIModule$3
            r7.<init>()
            r3.setNegativeButton(r0, r7)
            android.app.AlertDialog r7 = r3.create()
            r8 = 0
            r7.setCanceledOnTouchOutside(r8)
            r7.show()
            r6.tracking(r7)
            return
        L7c:
            java.lang.String r7 = "[WXModalUIModule] when call confirm mWXSDKInstance.getContext() must instanceof Activity"
            com.taobao.weex.utils.WXLogUtils.e(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.ui.module.WXModalUIModule.confirm(com.alibaba.fastjson.JSONObject, com.taobao.weex.bridge.JSCallback):void");
    }

    @Override // com.taobao.weex.common.Destroyable
    public void destroy() {
        Dialog dialog = this.activeDialog;
        if (dialog == null || !dialog.isShowing()) {
            return;
        }
        this.activeDialog.dismiss();
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0079  */
    @com.taobao.weex.annotation.JSMethod(uiThread = true)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void prompt(com.alibaba.fastjson.JSONObject r8, final com.taobao.weex.bridge.JSCallback r9) {
        /*
            r7 = this;
            com.taobao.weex.WXSDKInstance r0 = r7.mWXSDKInstance
            android.content.Context r0 = r0.getContext()
            boolean r0 = r0 instanceof android.app.Activity
            if (r0 == 0) goto L9a
            java.lang.String r0 = "Cancel"
            java.lang.String r1 = "OK"
            java.lang.String r2 = ""
            if (r8 == 0) goto L3f
            java.lang.String r3 = "message"
            java.lang.String r3 = r8.getString(r3)     // Catch: java.lang.Exception -> L34
            java.lang.String r4 = "okTitle"
            java.lang.String r4 = r8.getString(r4)     // Catch: java.lang.Exception -> L30
            java.lang.String r5 = "cancelTitle"
            java.lang.String r5 = r8.getString(r5)     // Catch: java.lang.Exception -> L2d
            java.lang.String r6 = "default"
            java.lang.String r8 = r8.getString(r6)     // Catch: java.lang.Exception -> L2b
            goto L43
        L2b:
            r8 = move-exception
            goto L38
        L2d:
            r8 = move-exception
            r5 = r0
            goto L38
        L30:
            r8 = move-exception
            r5 = r0
            r4 = r1
            goto L38
        L34:
            r8 = move-exception
            r5 = r0
            r4 = r1
            r3 = r2
        L38:
            java.lang.String r6 = "[WXModalUIModule] confirm param parse error "
            com.taobao.weex.utils.WXLogUtils.e(r6, r8)
            r8 = r2
            goto L43
        L3f:
            r5 = r0
            r4 = r1
            r8 = r2
            r3 = r8
        L43:
            boolean r6 = android.text.TextUtils.isEmpty(r3)
            if (r6 == 0) goto L4a
            goto L4b
        L4a:
            r2 = r3
        L4b:
            android.app.AlertDialog$Builder r3 = new android.app.AlertDialog$Builder
            com.taobao.weex.WXSDKInstance r6 = r7.mWXSDKInstance
            android.content.Context r6 = r6.getContext()
            r3.<init>(r6)
            r3.setMessage(r2)
            android.widget.EditText r2 = new android.widget.EditText
            com.taobao.weex.WXSDKInstance r6 = r7.mWXSDKInstance
            android.content.Context r6 = r6.getContext()
            r2.<init>(r6)
            r2.setText(r8)
            r3.setView(r2)
            boolean r8 = android.text.TextUtils.isEmpty(r4)
            if (r8 == 0) goto L71
            goto L72
        L71:
            r1 = r4
        L72:
            boolean r8 = android.text.TextUtils.isEmpty(r5)
            if (r8 == 0) goto L79
            goto L7a
        L79:
            r0 = r5
        L7a:
            com.taobao.weex.ui.module.WXModalUIModule$5 r8 = new com.taobao.weex.ui.module.WXModalUIModule$5
            r8.<init>()
            android.app.AlertDialog$Builder r8 = r3.setPositiveButton(r1, r8)
            com.taobao.weex.ui.module.WXModalUIModule$4 r1 = new com.taobao.weex.ui.module.WXModalUIModule$4
            r1.<init>()
            r8.setNegativeButton(r0, r1)
            android.app.AlertDialog r8 = r3.create()
            r9 = 0
            r8.setCanceledOnTouchOutside(r9)
            r8.show()
            r7.tracking(r8)
            return
        L9a:
            java.lang.String r8 = "when call prompt mWXSDKInstance.getContext() must instanceof Activity"
            com.taobao.weex.utils.WXLogUtils.e(r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.ui.module.WXModalUIModule.prompt(com.alibaba.fastjson.JSONObject, com.taobao.weex.bridge.JSCallback):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x004c A[PHI: r1 r4
      0x004c: PHI (r1v3 int) = (r1v2 int), (r1v5 int), (r1v5 int), (r1v5 int) binds: [B:23:0x0047, B:13:0x002e, B:15:0x0036, B:17:0x003e] A[DONT_GENERATE, DONT_INLINE]
      0x004c: PHI (r4v3 java.lang.String) = (r4v2 java.lang.String), (r4v4 java.lang.String), (r4v4 java.lang.String), (r4v4 java.lang.String) binds: [B:23:0x0047, B:13:0x002e, B:15:0x0036, B:17:0x003e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x005e  */
    @com.taobao.weex.annotation.JSMethod(uiThread = true)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void toast(com.alibaba.fastjson.JSONObject r7) {
        /*
            r6 = this;
            java.lang.String r0 = "gravity"
            java.lang.String r1 = "duration"
            com.taobao.weex.WXSDKInstance r2 = r6.mWXSDKInstance
            android.content.Context r2 = r2.getContext()
            if (r2 != 0) goto Ld
            return
        Ld:
            r2 = 17
            r3 = 0
            java.lang.String r4 = ""
            if (r7 == 0) goto L4f
            java.lang.String r5 = "message"
            java.lang.String r4 = r7.getString(r5)     // Catch: java.lang.Exception -> L45
            boolean r5 = r7.containsKey(r1)     // Catch: java.lang.Exception -> L45
            if (r5 == 0) goto L29
            java.lang.Integer r1 = r7.getInteger(r1)     // Catch: java.lang.Exception -> L45
            int r1 = r1.intValue()     // Catch: java.lang.Exception -> L45
            goto L2a
        L29:
            r1 = 0
        L2a:
            boolean r5 = r7.containsKey(r0)     // Catch: java.lang.Exception -> L43
            if (r5 == 0) goto L4c
            java.lang.String r7 = r7.getString(r0)     // Catch: java.lang.Exception -> L43
            boolean r0 = r7 instanceof java.lang.String     // Catch: java.lang.Exception -> L43
            if (r0 == 0) goto L4c
            java.lang.String r0 = "bottom"
            boolean r7 = r7.equals(r0)     // Catch: java.lang.Exception -> L43
            if (r7 == 0) goto L4c
            r7 = 80
            goto L52
        L43:
            r7 = move-exception
            goto L47
        L45:
            r7 = move-exception
            r1 = 0
        L47:
            java.lang.String r0 = "[WXModalUIModule] alert param parse error "
            com.taobao.weex.utils.WXLogUtils.e(r0, r7)
        L4c:
            r7 = 17
            goto L52
        L4f:
            r7 = 17
            r1 = 0
        L52:
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            if (r0 == 0) goto L5e
            java.lang.String r7 = "[WXModalUIModule] toast param parse is null "
            com.taobao.weex.utils.WXLogUtils.e(r7)
            return
        L5e:
            r0 = 3
            if (r1 <= r0) goto L63
            r0 = 1
            goto L64
        L63:
            r0 = 0
        L64:
            android.widget.Toast r1 = r6.toast
            if (r1 != 0) goto L75
            com.taobao.weex.WXSDKInstance r1 = r6.mWXSDKInstance
            android.content.Context r1 = r1.getContext()
            com.dcloud.android.widget.toast.ToastCompat r0 = com.dcloud.android.widget.toast.ToastCompat.makeText(r1, r4, r0)
            r6.toast = r0
            goto L7d
        L75:
            r1.setDuration(r0)
            android.widget.Toast r0 = r6.toast
            r0.setText(r4)
        L7d:
            if (r2 != r7) goto L84
            android.widget.Toast r0 = r6.toast
            r0.setGravity(r7, r3, r3)
        L84:
            android.widget.Toast r7 = r6.toast
            r7.show()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.ui.module.WXModalUIModule.toast(com.alibaba.fastjson.JSONObject):void");
    }
}
