package com.meizu.flyme.openidsdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.util.Log;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class b {
    public static volatile b g;
    public static boolean h;
    public Boolean e;
    public BroadcastReceiver f;
    public OpenId a = new OpenId("udid");
    public OpenId b = new OpenId("oaid");
    public OpenId d = new OpenId("vaid");
    public OpenId c = new OpenId("aaid");

    public static ValueData a(Cursor cursor) {
        ValueData valueData = new ValueData(null, 0);
        if (cursor == null) {
            b("parseValue fail, cursor is null.");
        } else if (cursor.isClosed()) {
            b("parseValue fail, cursor is closed.");
        } else {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex("value");
            if (columnIndex >= 0) {
                valueData.a = cursor.getString(columnIndex);
            } else {
                b("parseValue fail, index < 0.");
            }
            int columnIndex2 = cursor.getColumnIndex("code");
            if (columnIndex2 >= 0) {
                valueData.b = cursor.getInt(columnIndex2);
            } else {
                b("parseCode fail, index < 0.");
            }
            int columnIndex3 = cursor.getColumnIndex("expired");
            if (columnIndex3 >= 0) {
                valueData.c = cursor.getLong(columnIndex3);
            } else {
                b("parseExpired fail, index < 0.");
            }
        }
        return valueData;
    }

    public static final b a() {
        if (g == null) {
            synchronized (b.class) {
                g = new b();
            }
        }
        return g;
    }

    public static void b(String str) {
        if (h) {
            Log.d("OpenIdManager", str);
        }
    }

    public OpenId a(String str) {
        if ("oaid".equals(str)) {
            return this.b;
        }
        if ("vaid".equals(str)) {
            return this.d;
        }
        if ("aaid".equals(str)) {
            return this.c;
        }
        if ("udid".equals(str)) {
            return this.a;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v13, types: [android.content.ContentResolver] */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v2, types: [android.net.Uri] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r1v9, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.String a(android.content.Context r9, com.meizu.flyme.openidsdk.OpenId r10) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 242
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.flyme.openidsdk.b.a(android.content.Context, com.meizu.flyme.openidsdk.OpenId):java.lang.String");
    }

    public final synchronized void a(Context context) {
        if (this.f == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.meizu.flyme.openid.ACTION_OPEN_ID_CHANGE");
            this.f = new a();
            context.registerReceiver(this.f, intentFilter, "com.meizu.flyme.openid.permission.OPEN_ID_CHANGE", null);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x007e A[PHI: r0
      0x007e: PHI (r0v15 android.database.Cursor) = (r0v8 android.database.Cursor), (r0v17 android.database.Cursor) binds: [B:29:0x00a0, B:23:0x007c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00a6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean a(android.content.Context r9, boolean r10) throws java.lang.Throwable {
        /*
            r8 = this;
            r1 = 1
            r6 = 0
            r0 = 0
            java.lang.Boolean r2 = r8.e
            if (r2 == 0) goto Le
            if (r10 != 0) goto Le
            boolean r0 = r2.booleanValue()
        Ld:
            return r0
        Le:
            java.lang.String r2 = "com.meizu.flyme.openidsdk"
            if (r9 == 0) goto L18
            android.content.pm.PackageManager r3 = r9.getPackageManager()
            if (r3 != 0) goto L27
        L18:
            r1 = r0
        L19:
            if (r1 != 0) goto L2e
            java.lang.String r1 = "is not Supported, for isLegalProvider : false"
            b(r1)
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r0)
            r8.e = r1
            goto Ld
        L27:
            android.content.pm.ProviderInfo r2 = r3.resolveContentProvider(r2, r0)
            if (r2 == 0) goto L18
            goto L19
        L2e:
            java.lang.String r0 = "content://com.meizu.flyme.openidsdk/"
            android.net.Uri r1 = android.net.Uri.parse(r0)
            android.content.ContentResolver r0 = r9.getContentResolver()     // Catch: java.lang.Exception -> L83 java.lang.Throwable -> La3
            r2 = 0
            r3 = 0
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch: java.lang.Exception -> L83 java.lang.Throwable -> La3
            r5 = 0
            java.lang.String r7 = "supported"
            r4[r5] = r7     // Catch: java.lang.Exception -> L83 java.lang.Throwable -> La3
            r5 = 0
            android.database.Cursor r0 = r0.query(r1, r2, r3, r4, r5)     // Catch: java.lang.Exception -> L83 java.lang.Throwable -> La3
            if (r0 == 0) goto L7c
            com.meizu.flyme.openidsdk.ValueData r1 = a(r0)     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lac
            java.lang.String r1 = r1.a     // Catch: java.lang.Exception -> Laa java.lang.Throwable -> Lac
            r0.close()
            r0 = r1
        L53:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "querySupport, result : "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r0)
            java.lang.String r1 = r1.toString()
            b(r1)
            java.lang.String r1 = "0"
            boolean r0 = r1.equals(r0)
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            r8.e = r0
            java.lang.Boolean r0 = r8.e
            boolean r0 = r0.booleanValue()
            goto Ld
        L7c:
            if (r0 == 0) goto L81
        L7e:
            r0.close()
        L81:
            r0 = r6
            goto L53
        L83:
            r0 = move-exception
            r1 = r0
            r0 = r6
        L86:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lac
            r2.<init>()     // Catch: java.lang.Throwable -> Lac
            java.lang.String r3 = "querySupport, Exception : "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch: java.lang.Throwable -> Lac
            java.lang.String r1 = r1.getMessage()     // Catch: java.lang.Throwable -> Lac
            java.lang.StringBuilder r1 = r2.append(r1)     // Catch: java.lang.Throwable -> Lac
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> Lac
            b(r1)     // Catch: java.lang.Throwable -> Lac
            if (r0 == 0) goto L81
            goto L7e
        La3:
            r0 = move-exception
        La4:
            if (r6 == 0) goto La9
            r6.close()
        La9:
            throw r0
        Laa:
            r1 = move-exception
            goto L86
        Lac:
            r1 = move-exception
            r6 = r0
            r0 = r1
            goto La4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.flyme.openidsdk.b.a(android.content.Context, boolean):boolean");
    }
}
