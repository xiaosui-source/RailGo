package com.bun.miitmdid.c.g;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.zui.deviceidservice.IDeviceidInterface;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class a {
    private static String e = "OpenDeviceId library";
    private static boolean f;
    private Context a;
    private IDeviceidInterface b;
    private ServiceConnection c;
    private com.bun.miitmdid.c.e.a d;

    /* renamed from: com.bun.miitmdid.c.g.a$a, reason: collision with other inner class name */
    class ServiceConnectionC0004a implements ServiceConnection {
        ServiceConnectionC0004a() {
        }

        @Override // android.content.ServiceConnection
        public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            a.this.b = IDeviceidInterface.Stub.a(iBinder);
            if (a.this.d != null) {
                a.this.d.a(true);
            }
            a.this.b("Service onServiceConnected");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            a.this.b = null;
            a.this.b("Service onServiceDisconnected");
        }
    }

    public a(Context context, com.bun.miitmdid.c.e.a aVar) {
        this.a = null;
        if (context == null) {
            throw new NullPointerException("Context can not be null.");
        }
        this.a = context;
        this.d = aVar;
        this.c = new ServiceConnectionC0004a();
        Intent intent = new Intent();
        intent.setClassName("com.zui.deviceidservice", "com.zui.deviceidservice.DeviceidService");
        if (this.a.bindService(intent, this.c, 1)) {
            b("bindService Successful!");
            return;
        }
        b("bindService Failed!");
        com.bun.miitmdid.c.e.a aVar2 = this.d;
        if (aVar2 != null) {
            aVar2.b();
        }
    }

    private void a(String str) {
        if (f) {
            Log.e(e, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str) {
        if (f) {
            Log.i(e, str);
        }
    }

    public String a() {
        Context context = this.a;
        if (context == null) {
            b("Context is null.");
            throw new IllegalArgumentException("Context is null, must be new OpenDeviceId first");
        }
        String packageName = context.getPackageName();
        b("liufeng, getAAID package：" + packageName);
        if (packageName == null || packageName.equals("")) {
            b("input package is null!");
        } else {
            try {
                if (this.b != null) {
                    return this.b.getAAID(packageName);
                }
            } catch (RemoteException e2) {
                a("getAAID error, RemoteException!");
            }
        }
        return null;
    }

    public String b() {
        if (this.a == null) {
            a("Context is null.");
            throw new IllegalArgumentException("Context is null, must be new OpenDeviceId first");
        }
        try {
            if (this.b != null) {
                return this.b.getOAID();
            }
        } catch (RemoteException e2) {
            a("getOAID error, RemoteException!");
            e2.printStackTrace();
        }
        return null;
    }

    public String c() {
        if (this.a == null) {
            a("Context is null.");
            throw new IllegalArgumentException("Context is null, must be new OpenDeviceId first");
        }
        try {
            if (this.b != null) {
                return this.b.getUDID();
            }
        } catch (RemoteException e2) {
            a("getUDID error, RemoteException!");
            e2.printStackTrace();
        }
        return null;
    }

    public String d() {
        Context context = this.a;
        if (context == null) {
            b("Context is null.");
            throw new IllegalArgumentException("Context is null, must be new OpenDeviceId first");
        }
        String packageName = context.getPackageName();
        b("liufeng, getVAID package：" + packageName);
        if (packageName == null || packageName.equals("")) {
            b("input package is null!");
        } else {
            try {
                if (this.b != null) {
                    return this.b.getVAID(packageName);
                }
            } catch (RemoteException e2) {
                a("getVAID error, RemoteException!");
                e2.printStackTrace();
            }
        }
        return null;
    }

    public boolean e() {
        try {
            if (this.b == null) {
                return false;
            }
            b("Device support opendeviceid");
            return this.b.a();
        } catch (RemoteException e2) {
            a("isSupport error, RemoteException!");
            return false;
        }
    }

    public void f() {
        try {
            this.a.unbindService(this.c);
            b("unBind Service successful");
        } catch (IllegalArgumentException e2) {
            a("unBind Service exception");
        }
        this.b = null;
    }
}
