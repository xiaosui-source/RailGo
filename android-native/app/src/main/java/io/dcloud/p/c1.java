package io.dcloud.p;

import android.content.Context;
import android.text.TextUtils;
import com.taobao.weex.WXEnvironment;
import java.io.File;
import java.util.Locale;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class c1 {
    private static String[] a = {"sdcard/Android/data/com.bluestacks.home", "sdcard/Android/data/com.bluestacks.settings", "sdcard/Android/data/com.microvirt.guide", "sdcard/Android/data/com.microvirt.launcher2"};

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class b {
        public int a;
        public String b;

        public b(int i, String str) {
            this.a = i;
            this.b = str;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    private static class c {
        private static final c1 a = new c1();
    }

    private String b(String str) {
        String strA = a(str);
        if (TextUtils.isEmpty(strA)) {
            return null;
        }
        return strA;
    }

    private b c() {
        String strB = b("ro.build.flavor");
        if (strB == null) {
            return new b(0, null);
        }
        String lowerCase = strB.toLowerCase(Locale.ENGLISH);
        return new b((lowerCase.contains("vbox") || lowerCase.contains("sdk_gphone")) ? 1 : 2, strB);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private io.dcloud.p.c1.b d() {
        /*
            r7 = this;
            java.lang.String r0 = "ro.hardware"
            java.lang.String r0 = r7.b(r0)
            r1 = 0
            if (r0 != 0) goto L10
            io.dcloud.p.c1$b r0 = new io.dcloud.p.c1$b
            r2 = 0
            r0.<init>(r1, r2)
            return r0
        L10:
            java.util.Locale r2 = java.util.Locale.ENGLISH
            java.lang.String r2 = r0.toLowerCase(r2)
            r2.getClass()
            r2.hashCode()
            int r3 = r2.hashCode()
            r4 = 2
            r5 = 1
            r6 = -1
            switch(r3) {
                case -1367724016: goto L6a;
                case -822798509: goto L5f;
                case 109271: goto L54;
                case 3570999: goto L49;
                case 3613077: goto L3e;
                case 100361430: goto L33;
                case 937844646: goto L28;
                default: goto L26;
            }
        L26:
            r1 = -1
            goto L73
        L28:
            java.lang.String r1 = "android_x86"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L31
            goto L26
        L31:
            r1 = 6
            goto L73
        L33:
            java.lang.String r1 = "intel"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L3c
            goto L26
        L3c:
            r1 = 5
            goto L73
        L3e:
            java.lang.String r1 = "vbox"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L47
            goto L26
        L47:
            r1 = 4
            goto L73
        L49:
            java.lang.String r1 = "ttvm"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L52
            goto L26
        L52:
            r1 = 3
            goto L73
        L54:
            java.lang.String r1 = "nox"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L5d
            goto L26
        L5d:
            r1 = 2
            goto L73
        L5f:
            java.lang.String r1 = "vbox86"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L68
            goto L26
        L68:
            r1 = 1
            goto L73
        L6a:
            java.lang.String r3 = "cancro"
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L73
            goto L26
        L73:
            switch(r1) {
                case 0: goto L77;
                case 1: goto L77;
                case 2: goto L77;
                case 3: goto L77;
                case 4: goto L77;
                case 5: goto L77;
                case 6: goto L77;
                default: goto L76;
            }
        L76:
            goto L78
        L77:
            r4 = 1
        L78:
            io.dcloud.p.c1$b r1 = new io.dcloud.p.c1$b
            r1.<init>(r4, r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.p.c1.d():io.dcloud.p.c1$b");
    }

    private b e() {
        String strB = b("ro.product.manufacturer");
        if (strB == null) {
            return new b(0, null);
        }
        String lowerCase = strB.toLowerCase(Locale.ENGLISH);
        return new b((lowerCase.contains("genymotion") || lowerCase.contains("netease")) ? 1 : 2, strB);
    }

    private b f() {
        String strB = b("ro.product.model");
        if (strB == null) {
            return new b(0, null);
        }
        String lowerCase = strB.toLowerCase(Locale.ENGLISH);
        return new b((lowerCase.contains("google_sdk") || lowerCase.contains("emulator") || lowerCase.contains("android sdk built for x86")) ? 1 : 2, strB);
    }

    private b g() {
        String strB = b("ro.board.platform");
        if (strB == null) {
            return new b(0, null);
        }
        return new b(strB.toLowerCase(Locale.ENGLISH).contains(WXEnvironment.OS) ? 1 : 2, strB);
    }

    public static b h() {
        int i = 0;
        int i2 = 0;
        int i3 = 2;
        while (true) {
            String[] strArr = a;
            if (i >= strArr.length) {
                break;
            }
            if (new File(strArr[i]).exists()) {
                i2++;
            } else {
                i3 = 0;
            }
            if (i2 > 2) {
                break;
            }
            i++;
        }
        return new b(i2 != 1 ? i2 != 2 ? i3 : 1 : 0, "PkgName");
    }

    public static final c1 i() {
        return c.a;
    }

    public boolean a(Context context) {
        int i;
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }
        int i2 = d().a;
        if (i2 == 0) {
            i = 1;
        } else {
            if (i2 == 1) {
                return true;
            }
            i = 0;
        }
        int i3 = h().a;
        if (i3 == 0) {
            i++;
        } else if (i3 == 1) {
            return true;
        }
        int i4 = c().a;
        if (i4 == 0) {
            i++;
        } else if (i4 == 1) {
            return true;
        }
        int i5 = f().a;
        if (i5 == 0) {
            i++;
        } else if (i5 == 1) {
            return true;
        }
        int i6 = e().a;
        if (i6 == 0) {
            i++;
        } else if (i6 == 1) {
            return true;
        }
        int i7 = b().a;
        if (i7 == 0) {
            i++;
        } else if (i7 == 1) {
            return true;
        }
        int i8 = g().a;
        if (i8 == 0) {
            i++;
        } else if (i8 == 1) {
            return true;
        }
        int i9 = a().a;
        if (i9 == 0) {
            i += 2;
        } else if (i9 == 1) {
            return true;
        }
        if (!c(context)) {
            i++;
        }
        if (!b(context)) {
            i++;
        }
        return i > 3;
    }

    private c1() {
    }

    private b b() {
        String strB = b("ro.product.board");
        if (strB == null) {
            return new b(0, null);
        }
        String lowerCase = strB.toLowerCase(Locale.ENGLISH);
        return new b((lowerCase.contains(WXEnvironment.OS) || lowerCase.contains("goldfish")) ? 1 : 2, strB);
    }

    private boolean c(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.camera.flash");
    }

    private boolean b(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.bluetooth");
    }

    private String a(String str) {
        try {
            Object objInvoke = Class.forName("android.os.SystemProperties").getMethod("get", String.class).invoke(null, str);
            if (objInvoke != null) {
                return (String) objInvoke;
            }
        } catch (Throwable unused) {
        }
        return null;
    }

    private b a() {
        String strB = b("gsm.version.baseband");
        if (strB == null) {
            return new b(0, null);
        }
        return new b(strB.contains("1.0.0.0") ? 1 : 2, strB);
    }
}
