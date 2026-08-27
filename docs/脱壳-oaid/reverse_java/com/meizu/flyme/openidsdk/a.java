package com.meizu.flyme.openidsdk;

import android.content.BroadcastReceiver;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class a extends BroadcastReceiver {
    /* JADX WARN: Removed duplicated region for block: B:20:0x0061  */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onReceive(android.content.Context r6, android.content.Intent r7) {
        /*
            r5 = this;
            r0 = 1
            r1 = 0
            if (r6 == 0) goto L6
            if (r7 != 0) goto L7
        L6:
            return
        L7:
            java.lang.String r2 = "openIdNotifyFlag"
            int r2 = r7.getIntExtra(r2, r1)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "shouldUpdateId, notifyFlag : "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r2)
            java.lang.String r3 = r3.toString()
            com.meizu.flyme.openidsdk.b.b(r3)
            if (r2 != r0) goto L4b
            java.lang.String r2 = "openIdPackage"
            java.lang.String r2 = r7.getStringExtra(r2)
            java.lang.String r3 = r6.getPackageName()
            boolean r2 = android.text.TextUtils.equals(r2, r3)
            if (r2 == 0) goto L61
        L35:
            if (r0 == 0) goto L6
            java.lang.String r0 = "openIdType"
            java.lang.String r0 = r7.getStringExtra(r0)
            com.meizu.flyme.openidsdk.b r1 = com.meizu.flyme.openidsdk.b.a()
            com.meizu.flyme.openidsdk.OpenId r0 = r1.a(r0)
            if (r0 == 0) goto L6
            r0.b()
            goto L6
        L4b:
            r3 = 2
            if (r2 != r3) goto L5f
            java.lang.String r0 = "openIdPackageList"
            java.util.ArrayList r0 = r7.getStringArrayListExtra(r0)
            if (r0 == 0) goto L61
            java.lang.String r1 = r6.getPackageName()
            boolean r0 = r0.contains(r1)
            goto L35
        L5f:
            if (r2 == 0) goto L35
        L61:
            r0 = r1
            goto L35
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.flyme.openidsdk.a.onReceive(android.content.Context, android.content.Intent):void");
    }
}
