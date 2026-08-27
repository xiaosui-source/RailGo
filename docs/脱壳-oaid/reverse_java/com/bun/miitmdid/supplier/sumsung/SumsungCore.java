package com.bun.miitmdid.supplier.sumsung;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Keep;
import android.util.Log;
import com.samsung.android.deviceidservice.IDeviceIdService;

@Keep
/* loaded from: /workspace/39285EFA.decrypted.dex */
public class SumsungCore {
    private static boolean DBG = false;
    private static String SAMSUNGTAG = "Samsung_DeviceIdService";
    private static String TAG = "SumsungCore library";
    private com.bun.miitmdid.c.e.a mCallerCallBack;
    private ServiceConnection mConnection;
    private Context mContext;
    private IDeviceIdService mDeviceidInterface;

    class a implements ServiceConnection {
        a() {
        }

        @Override // android.content.ServiceConnection
        public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            SumsungCore.this.mDeviceidInterface = IDeviceIdService.Stub.a(iBinder);
            if (SumsungCore.this.mCallerCallBack != null) {
                SumsungCore.this.mCallerCallBack.a(true);
            }
            com.bun.lib.a.b(SumsungCore.TAG, "Service onServiceConnected");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            SumsungCore.this.mDeviceidInterface = null;
            com.bun.lib.a.b(SumsungCore.TAG, "Service onServiceDisconnected");
        }
    }

    public SumsungCore(Context context, com.bun.miitmdid.c.e.a aVar) {
        this.mContext = null;
        this.mCallerCallBack = null;
        if (context == null) {
            throw new NullPointerException("Context can not be null.");
        }
        this.mContext = context;
        this.mCallerCallBack = aVar;
        this.mConnection = new a();
        Intent intent = new Intent();
        intent.setClassName("com.samsung.android.deviceidservice", "com.samsung.android.deviceidservice.DeviceIdService");
        if (this.mContext.bindService(intent, this.mConnection, 1)) {
            com.bun.lib.a.b(TAG, "bindService Successful!");
            return;
        }
        this.mContext.unbindService(this.mConnection);
        com.bun.lib.a.b(TAG, "bindService Failed!");
        com.bun.miitmdid.c.e.a aVar2 = this.mCallerCallBack;
        if (aVar2 != null) {
            aVar2.b();
        }
    }

    public String getAAID() {
        Context context = this.mContext;
        if (context == null) {
            com.bun.lib.a.b(TAG, "Context is null.");
            throw new IllegalArgumentException("Context is null, must be new SumsungCore first");
        }
        String packageName = context.getPackageName();
        com.bun.lib.a.b(TAG, "liufeng, getAAID package：" + packageName);
        if (packageName == null || packageName.equals("")) {
            com.bun.lib.a.b(TAG, "input package is null!");
        } else {
            try {
                if (this.mDeviceidInterface != null) {
                    Log.d(SAMSUNGTAG, "getAAID Package: " + packageName);
                    return this.mDeviceidInterface.getAAID(packageName);
                }
            } catch (RemoteException e) {
                com.bun.lib.a.b(TAG, "getAAID error, RemoteException!");
            }
        }
        return null;
    }

    public String getOAID() {
        if (this.mContext == null) {
            com.bun.lib.a.a(TAG, "Context is null.");
            throw new IllegalArgumentException("Context is null, must be new SumsungCore first");
        }
        try {
            if (this.mDeviceidInterface != null) {
                Log.d(SAMSUNGTAG, "getOAID call");
                return this.mDeviceidInterface.getOAID();
            }
        } catch (RemoteException e) {
            com.bun.lib.a.a(TAG, "getOAID error, RemoteException!");
            e.printStackTrace();
        }
        return null;
    }

    public String getUDID() {
        return "";
    }

    public String getVAID() {
        Context context = this.mContext;
        if (context == null) {
            com.bun.lib.a.b(TAG, "Context is null.");
            throw new IllegalArgumentException("Context is null, must be new SumsungCore first");
        }
        String packageName = context.getPackageName();
        com.bun.lib.a.b(TAG, "liufeng, getVAID package：" + packageName);
        if (packageName == null || packageName.equals("")) {
            com.bun.lib.a.b(TAG, "input package is null!");
        } else {
            try {
                if (this.mDeviceidInterface != null) {
                    Log.d(SAMSUNGTAG, "getVAID Package: " + packageName);
                    return this.mDeviceidInterface.getVAID(packageName);
                }
            } catch (RemoteException e) {
                com.bun.lib.a.b(TAG, "getVAID error, RemoteException!");
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean isSupported() {
        try {
            if (this.mDeviceidInterface == null) {
                return false;
            }
            com.bun.lib.a.b(TAG, "Device support opendeviceid");
            return true;
        } catch (Exception e) {
            com.bun.lib.a.b(TAG, "isSupport error, RemoteException!");
            return false;
        }
    }

    public void shutdown() {
        try {
            this.mContext.unbindService(this.mConnection);
            com.bun.lib.a.b(TAG, "unBind Service successful");
        } catch (IllegalArgumentException e) {
            com.bun.lib.a.b(TAG, "unBind Service exception");
        }
        this.mDeviceidInterface = null;
    }
}
