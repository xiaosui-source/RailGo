package io.dcloud.p;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.TextView;
import io.dcloud.common.adapter.util.CanvasHelper;
import io.dcloud.common.util.AESUtil;
import io.dcloud.common.util.Base64;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.language.LanguageUtil;
import io.dcloud.feature.internal.sdk.SDK;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class b {
    private static b d;
    private volatile Context a;
    private boolean b = false;
    private Handler c = new a();

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a extends Handler {
        a() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                b.this.a();
            } catch (Exception unused) {
            }
            sendEmptyMessageDelayed(1, Double.valueOf((Math.random() + 1.0d) * 1000.0d * 60.0d).longValue());
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    /* renamed from: io.dcloud.p.b$b, reason: collision with other inner class name */
    class HandlerC0065b extends Handler {
        HandlerC0065b() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                b.this.a();
            } catch (Exception e) {
                e.printStackTrace();
            }
            sendEmptyMessageDelayed(1, Double.valueOf((Math.random() + 1.0d) * 1000.0d * 60.0d).longValue());
        }
    }

    private b() {
        if (d != null) {
            throw new IllegalStateException();
        }
    }

    private String b(String str) {
        Bundle bundle;
        try {
            ApplicationInfo applicationInfo = this.a.getPackageManager().getPackageInfo(this.a.getPackageName(), 128).applicationInfo;
            return (applicationInfo == null || (bundle = applicationInfo.metaData) == null) ? "" : bundle.getString(str);
        } catch (Exception unused) {
            return "";
        }
    }

    private String c() throws IllegalAccessException, IOException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        byte[] bArrDecode2bytes = Base64.decode2bytes(q4.a(2));
        if (bArrDecode2bytes == null) {
            a(4);
            return "";
        }
        String strDecrypt = AESUtil.decrypt(d1.e(), d1.b(), bArrDecode2bytes);
        if (strDecrypt == null) {
            a(4);
            return "";
        }
        for (List list : q4.b()) {
            try {
                Class<?> cls = Class.forName(d1.b((String) list.get(0)));
                return (String) cls.getMethod(d1.b((String) list.get(2)), null).invoke(cls.getDeclaredMethod(d1.b((String) list.get(1)), null).invoke(null, null), null);
            } catch (Exception unused) {
            }
        }
        try {
            Class<?> cls2 = Class.forName(strDecrypt);
            Object objInvoke = cls2.getMethod("getAppStatus", null).invoke(cls2.getMethod("getInstance", null).invoke(null, null), null);
            if (objInvoke != null) {
                return (String) objInvoke.getClass().getMethod("getAPPID", null).invoke(objInvoke, null);
            }
        } catch (Exception unused2) {
        }
        return "";
    }

    private String d() throws IOException {
        byte[] bArrDecode2bytes = Base64.decode2bytes(q4.a(10));
        if (bArrDecode2bytes == null) {
            a(4);
            return "";
        }
        String strDecrypt = AESUtil.decrypt(d1.e(), d1.b(), bArrDecode2bytes);
        if (strDecrypt == null) {
            a(4);
            return "";
        }
        try {
            return (String) Class.forName(strDecrypt).getMethod("getAppId", null).invoke(null, null);
        } catch (Exception unused) {
            return "";
        }
    }

    private String e() throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            for (List list : q4.c()) {
                try {
                    Class<?> cls = Class.forName(d1.b((String) list.get(0)));
                    Method declaredMethod = cls.getDeclaredMethod(d1.b((String) list.get(1)), null);
                    declaredMethod.setAccessible(true);
                    return (String) cls.getMethod(d1.b((String) list.get(2)), null).invoke(declaredMethod.invoke(null, null), null);
                } catch (Exception unused) {
                }
            }
            return "";
        } catch (Exception unused2) {
            return "";
        }
    }

    public static b g() {
        if (d == null) {
            synchronized (b.class) {
                if (d == null) {
                    d = new b();
                }
            }
        }
        return d;
    }

    public List f() {
        ArrayList arrayList = new ArrayList();
        try {
            ActivityInfo[] activityInfoArr = this.a.getPackageManager().getPackageInfo(this.a.getPackageName(), 1).activities;
            if (activityInfoArr != null) {
                for (ActivityInfo activityInfo : activityInfoArr) {
                    arrayList.add(activityInfo.name);
                }
            }
        } catch (Exception unused) {
        }
        return arrayList;
    }

    public void h() {
        Handler handler = this.c;
        if (handler != null) {
            this.b = false;
            handler.removeMessages(1);
            this.c = null;
        }
    }

    public void a(Context context) {
        Handler handler;
        if (!PdrUtil.checkIntl() || LanguageUtil.getDeviceDefCountry().equalsIgnoreCase(d1.b("KF"))) {
            this.a = context;
            try {
                if (SDK.isUniMPSDK()) {
                    return;
                }
                if (Math.abs(Math.random() * 5.0d) == 5.0d && (handler = this.c) != null) {
                    handler.removeMessages(1);
                    this.c = null;
                }
                if (this.c == null) {
                    this.c = new HandlerC0065b();
                }
                if (this.c.hasMessages(1)) {
                    return;
                }
                this.c.sendEmptyMessage(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0079 A[Catch: Exception -> 0x007e, TRY_LEAVE, TryCatch #1 {Exception -> 0x007e, blocks: (B:3:0x000d, B:5:0x0018, B:7:0x001c, B:9:0x002a, B:13:0x0033, B:16:0x005e, B:18:0x0079), top: B:22:0x000d }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.util.Map b() throws java.lang.IllegalAccessException, java.lang.InstantiationException, java.lang.ClassNotFoundException {
        /*
            r6 = this;
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            java.lang.String r1 = ""
            java.lang.String r2 = "app_id"
            r0.put(r2, r1)
            r1 = 1
            java.lang.String r1 = io.dcloud.p.q4.a(r1)     // Catch: java.lang.Exception -> L7e
            byte[] r1 = io.dcloud.common.util.Base64.decode2bytes(r1)     // Catch: java.lang.Exception -> L7e
            r3 = 4
            if (r1 != 0) goto L1c
            r6.a(r3)     // Catch: java.lang.Exception -> L7e
            return r0
        L1c:
            java.lang.String r4 = io.dcloud.p.d1.e()     // Catch: java.lang.Exception -> L7e
            java.lang.String r5 = io.dcloud.p.d1.b()     // Catch: java.lang.Exception -> L7e
            java.lang.String r1 = io.dcloud.common.util.AESUtil.decrypt(r4, r5, r1)     // Catch: java.lang.Exception -> L7e
            if (r1 != 0) goto L2e
            r6.a(r3)     // Catch: java.lang.Exception -> L7e
            return r0
        L2e:
            r3 = 0
            java.lang.Class r1 = java.lang.Class.forName(r1)     // Catch: java.lang.Exception -> L44
            java.lang.Object r2 = r1.newInstance()     // Catch: java.lang.Exception -> L7e
            java.lang.String r4 = "NM_getCustomInfo"
            java.lang.reflect.Method r1 = r1.getMethod(r4, r3)     // Catch: java.lang.Exception -> L7e
            java.lang.Object r1 = r1.invoke(r2, r3)     // Catch: java.lang.Exception -> L7e
            java.util.Map r1 = (java.util.Map) r1     // Catch: java.lang.Exception -> L7e
            return r1
        L44:
            java.lang.String r1 = io.dcloud.p.d1.e()     // Catch: java.lang.Exception -> L5e
            java.lang.String r4 = io.dcloud.p.d1.b()     // Catch: java.lang.Exception -> L5e
            r5 = 9
            java.lang.String r5 = io.dcloud.p.q4.a(r5)     // Catch: java.lang.Exception -> L5e
            byte[] r5 = io.dcloud.common.util.Base64.decode2bytes(r5)     // Catch: java.lang.Exception -> L5e
            java.lang.String r1 = io.dcloud.common.util.AESUtil.decrypt(r1, r4, r5)     // Catch: java.lang.Exception -> L5e
            java.lang.Class r3 = java.lang.Class.forName(r1)     // Catch: java.lang.Exception -> L5e
        L5e:
            java.lang.String r1 = io.dcloud.p.d1.e()     // Catch: java.lang.Exception -> L7e
            java.lang.String r4 = io.dcloud.p.d1.b()     // Catch: java.lang.Exception -> L7e
            r5 = 8
            java.lang.String r5 = io.dcloud.p.q4.a(r5)     // Catch: java.lang.Exception -> L7e
            byte[] r5 = io.dcloud.common.util.Base64.decode2bytes(r5)     // Catch: java.lang.Exception -> L7e
            java.lang.String r1 = io.dcloud.common.util.AESUtil.decrypt(r1, r4, r5)     // Catch: java.lang.Exception -> L7e
            java.lang.Class.forName(r1)     // Catch: java.lang.Exception -> L7e
            if (r3 != 0) goto L7e
            java.lang.String r1 = "unknow"
            r0.put(r2, r1)     // Catch: java.lang.Exception -> L7e
        L7e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.p.b.b():java.util.Map");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(30:0|2|(1:10)(1:9)|11|(1:17)(1:16)|18|(1:24)(1:23)|25|(12:(32:27|(0)(1:30)|33|149|34|(1:48)(2:38|(5:(1:41)(1:43)|169|44|(1:46)|47)(0))|153|49|(1:58)(2:53|(4:55|159|56|57)(0))|157|59|(4:61|(5:63|151|64|(7:66|161|67|68|147|69|70)|73)(1:71)|72|73)(0)|173|76|(3:78|(4:80|155|81|(4:83|163|84|85)(1:87))(1:89)|90)(0)|93|(6:165|95|96|177|97|(2:99|(5:101|167|102|103|(11:175|117|(1:(2:119|(2:180|121)(1:122))(2:179|123))|124|(2:171|126)|(1:128)|(1:130)|(1:132)|(1:134)|135|136)(1:182))))(1:107)|108|109|(0)|115|175|117|(2:(0)(0)|122)|124|(0)|(0)|(0)|(0)|(0)|135|136)(1:31)|175|117|(2:(0)(0)|122)|124|(0)|(0)|(0)|(0)|(0)|135|136)|32|33|149|34|(2:36|48)(0)|153|49|(2:51|58)(0)|157|59|(0)(0)|173|76|(0)(0)|93|(0)(0)|108|109|(0)|115|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x01b6, code lost:
    
        r12 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0203, code lost:
    
        r19 = r9;
        r9 = io.dcloud.sdk.core.util.Const.TYPE_PG;
        r15 = r3;
        r20 = r11;
     */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0268  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0271 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:119:0x029e A[Catch: Exception -> 0x0302, TryCatch #14 {Exception -> 0x0302, blocks: (B:117:0x027d, B:119:0x029e, B:124:0x02b1, B:135:0x02e6, B:122:0x02ad), top: B:175:0x027d }] */
    /* JADX WARN: Removed duplicated region for block: B:128:0x02d0 A[Catch: Exception -> 0x02e6, TryCatch #12 {Exception -> 0x02e6, blocks: (B:126:0x02c6, B:128:0x02d0, B:130:0x02d5, B:132:0x02da, B:134:0x02df), top: B:171:0x02c6 }] */
    /* JADX WARN: Removed duplicated region for block: B:130:0x02d5 A[Catch: Exception -> 0x02e6, TryCatch #12 {Exception -> 0x02e6, blocks: (B:126:0x02c6, B:128:0x02d0, B:130:0x02d5, B:132:0x02da, B:134:0x02df), top: B:171:0x02c6 }] */
    /* JADX WARN: Removed duplicated region for block: B:132:0x02da A[Catch: Exception -> 0x02e6, TryCatch #12 {Exception -> 0x02e6, blocks: (B:126:0x02c6, B:128:0x02d0, B:130:0x02d5, B:132:0x02da, B:134:0x02df), top: B:171:0x02c6 }] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x02df A[Catch: Exception -> 0x02e6, TRY_LEAVE, TryCatch #12 {Exception -> 0x02e6, blocks: (B:126:0x02c6, B:128:0x02d0, B:130:0x02d5, B:132:0x02da, B:134:0x02df), top: B:171:0x02c6 }] */
    /* JADX WARN: Removed duplicated region for block: B:165:0x0223 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:171:0x02c6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:179:0x02b0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x017e A[Catch: Exception -> 0x01b6, TRY_LEAVE, TryCatch #5 {Exception -> 0x01b6, blocks: (B:59:0x0178, B:61:0x017e), top: B:157:0x0178 }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01c1 A[Catch: Exception -> 0x0203, TRY_LEAVE, TryCatch #13 {Exception -> 0x0203, blocks: (B:76:0x01bb, B:78:0x01c1), top: B:173:0x01bb }] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01f9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void a() throws org.json.JSONException, java.lang.IllegalAccessException, java.lang.NoSuchMethodException, java.lang.InstantiationException, java.lang.SecurityException, java.lang.ClassNotFoundException, java.io.IOException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 771
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.p.b.a():void");
    }

    private boolean a(String str, JSONArray jSONArray) {
        if (jSONArray != null && jSONArray.length() > 0) {
            for (int i = 0; i < jSONArray.length(); i++) {
                if (str.equals(jSONArray.optString(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    private String a(String str, String str2) throws IOException {
        byte[] bArrDecode2bytes = Base64.decode2bytes(q4.a(3));
        if (bArrDecode2bytes == null) {
            a(4);
            return "";
        }
        String strDecrypt = AESUtil.decrypt(d1.e(), d1.b(), bArrDecode2bytes);
        if (strDecrypt == null) {
            a(4);
            return "";
        }
        try {
            return (String) Class.forName(strDecrypt).getMethod("da", String.class, String.class).invoke(null, str, str2);
        } catch (Exception unused) {
            return "";
        }
    }

    private JSONArray a(String str) throws IOException {
        byte[] bArrDecode2bytes = Base64.decode2bytes(q4.a(3));
        if (bArrDecode2bytes == null) {
            a(4);
            return null;
        }
        String strDecrypt = AESUtil.decrypt(d1.e(), d1.b(), bArrDecode2bytes);
        if (strDecrypt == null) {
            a(4);
            return null;
        }
        try {
            return (JSONArray) Class.forName(strDecrypt).getMethod("dah", String.class).invoke(null, str);
        } catch (Exception unused) {
            return null;
        }
    }

    private void a(int i) {
        if (this.a == null) {
            return;
        }
        g0 g0Var = new g0((Activity) this.a, "");
        TextView textView = new TextView(this.a);
        textView.setAutoLinkMask(1);
        textView.setClickable(true);
        textView.setText(z3.a(this.a).a(AESUtil.decrypt(d1.e(), d1.b(), Base64.decode2bytes(q4.a(i)))));
        LinearLayout linearLayout = new LinearLayout(this.a);
        linearLayout.addView(textView);
        g0Var.a(linearLayout, textView);
        g0Var.setDuration(1);
        g0Var.setGravity(80, g0Var.getXOffset(), g0Var.getYOffset());
        int iDip2px = CanvasHelper.dip2px(this.a, 10.0f);
        int iDip2px2 = CanvasHelper.dip2px(this.a, 8.0f);
        linearLayout.setPadding(iDip2px, iDip2px2, iDip2px, iDip2px2);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(iDip2px2);
        gradientDrawable.setShape(0);
        gradientDrawable.setColor(-1308622848);
        linearLayout.setBackground(gradientDrawable);
        textView.setGravity(17);
        textView.setTextColor(Color.parseColor("#ffffffff"));
        g0Var.show();
    }
}
