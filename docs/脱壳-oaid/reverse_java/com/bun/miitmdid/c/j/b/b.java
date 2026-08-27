package com.bun.miitmdid.c.j.b;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class b {
    private static Context a;
    private static boolean b;
    private static b c;
    private static com.bun.miitmdid.c.j.b.a d;
    private static c e;
    private static c f;
    private static c g;
    private static Object h = new Object();
    private static HandlerThread i;
    private static Handler j;
    private static String k;
    private static String l;
    private static String m;
    private static String n;

    static class a extends Handler {
        a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 11) {
                com.bun.lib.a.a("VMS_IDLG_SDK_Client", "message type valid");
                return;
            }
            try {
                String unused = b.k = b.d.a(message.getData().getInt("type"), message.getData().getString("appid"));
            } catch (Exception e) {
                String unused2 = b.k = "";
                com.bun.lib.a.a("VMS_IDLG_SDK_Client", "exception", e);
            }
            synchronized (b.h) {
                b.h.notify();
            }
        }
    }

    private b() {
    }

    public static b a(Context context) {
        if (c == null) {
            c = new b();
            a = context;
            f();
            d = new com.bun.miitmdid.c.j.b.a(a);
            e();
        }
        return c;
    }

    public static String a(String str, String str2) {
        try {
            try {
                Class<?> cls = Class.forName("android.os.SystemProperties");
                str2 = (String) cls.getMethod("get", String.class, String.class).invoke(cls, str, "unknown");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Throwable th) {
        }
        return str2;
    }

    private static void a(Context context, int i2, String str) {
        if (i2 == 0) {
            e = new c(c, 0, null);
            context.getContentResolver().registerContentObserver(Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/OAID"), true, e);
        } else if (i2 == 1) {
            f = new c(c, 1, str);
            context.getContentResolver().registerContentObserver(Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/VAID_" + str), false, f);
        } else {
            if (i2 != 2) {
                return;
            }
            g = new c(c, 2, str);
            context.getContentResolver().registerContentObserver(Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/AAID_" + str), false, g);
        }
    }

    private void b(int i2, String str) {
        Message messageObtainMessage = j.obtainMessage();
        messageObtainMessage.what = 11;
        Bundle bundle = new Bundle();
        bundle.putInt("type", i2);
        if (i2 == 1 || i2 == 2) {
            bundle.putString("appid", str);
        }
        messageObtainMessage.setData(bundle);
        j.sendMessage(messageObtainMessage);
    }

    public static void e() {
        b = "1".equals(a("persist.sys.identifierid.supported", "0"));
    }

    private static void f() {
        i = new HandlerThread("SqlWorkThread");
        i.start();
        j = new a(i.getLooper());
    }

    public String a() {
        if (!b()) {
            return null;
        }
        String str = l;
        if (str != null) {
            return str;
        }
        a(0, (String) null);
        if (e == null) {
            a(a, 0, null);
        }
        return l;
    }

    public String a(String str) {
        if (!b()) {
            return null;
        }
        String str2 = n;
        if (str2 != null) {
            return str2;
        }
        a(2, str);
        if (g == null && n != null) {
            a(a, 2, str);
        }
        return n;
    }

    public void a(int i2, String str) {
        synchronized (h) {
            b(i2, str);
            long jUptimeMillis = SystemClock.uptimeMillis();
            try {
                h.wait(2000L);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            if (SystemClock.uptimeMillis() - jUptimeMillis < 2000) {
                if (i2 != 0) {
                    if (i2 != 1) {
                        if (i2 == 2) {
                            if (k != null) {
                                n = k;
                            } else {
                                com.bun.lib.a.a("VMS_IDLG_SDK_Client", "get aaid failed");
                            }
                        }
                    } else if (k != null) {
                        m = k;
                    } else {
                        com.bun.lib.a.a("VMS_IDLG_SDK_Client", "get vaid failed");
                    }
                }
                l = k;
                k = null;
            } else {
                com.bun.lib.a.a("VMS_IDLG_SDK_Client", "query timeout");
            }
        }
    }

    public String b(String str) {
        if (!b()) {
            return null;
        }
        String str2 = m;
        if (str2 != null) {
            return str2;
        }
        a(1, str);
        if (f == null && m != null) {
            a(a, 1, str);
        }
        return m;
    }

    public boolean b() {
        return b;
    }
}
