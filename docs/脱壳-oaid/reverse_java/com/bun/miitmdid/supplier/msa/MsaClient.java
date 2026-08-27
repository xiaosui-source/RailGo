package com.bun.miitmdid.supplier.msa;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Keep;
import com.bun.lib.c;

@Keep
/* loaded from: /workspace/39285EFA.decrypted.dex */
public class MsaClient {
    private static String TAG = "MSA Client library";
    private static String TARGET_PACKAGE = "com.mdid.msa";
    private com.bun.miitmdid.c.e.a _BindService;
    private ServiceConnection mConnection;
    private Context mContext;
    private c mDeviceidInterface;

    class a implements ServiceConnection {
        final /* synthetic */ com.bun.miitmdid.c.e.a a;

        a(com.bun.miitmdid.c.e.a aVar) {
            this.a = aVar;
        }

        @Override // android.content.ServiceConnection
        public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MsaClient.this.mDeviceidInterface = c.a.a(iBinder);
            new com.bun.miitmdid.supplier.msa.a(MsaClient.this.mDeviceidInterface, this.a).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            com.bun.lib.a.b(MsaClient.TAG, "Service onServiceConnected");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            MsaClient.this.mDeviceidInterface = null;
            com.bun.lib.a.b(MsaClient.TAG, "Service onServiceDisconnected");
            MsaClient.this.mDeviceidInterface = null;
        }
    }

    public MsaClient(Context context, com.bun.miitmdid.c.e.a aVar) {
        if (context == null) {
            throw new NullPointerException("Context can not be null.");
        }
        this.mContext = context;
        this._BindService = aVar;
        this.mConnection = new a(aVar);
    }

    public static boolean CheckService(Context context) throws PackageManager.NameNotFoundException {
        try {
            context.getPackageManager().getPackageInfo(TARGET_PACKAGE, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void StartMsaKlService(Context context, String str) {
        Intent intent = new Intent();
        intent.setClassName(TARGET_PACKAGE, "com.mdid.msa.service.MsaKlService");
        intent.setAction("com.bun.msa.action.start.service");
        intent.putExtra("com.bun.msa.param.pkgname", str);
        try {
            intent.putExtra("com.bun.msa.param.runinset", true);
            if (context.startService(intent) == null) {
                com.bun.lib.a.a(TAG, "start msa kl service error");
            }
        } catch (Exception e) {
            com.bun.lib.a.a(TAG, "start msa kl service exception", e);
        }
    }

    public void BindService(String str) {
        Intent intent = new Intent();
        intent.setClassName("com.mdid.msa", "com.mdid.msa.service.MsaIdService");
        intent.setAction("com.bun.msa.action.bindto.service");
        intent.putExtra("com.bun.msa.param.pkgname", str);
        if (this.mContext.bindService(intent, this.mConnection, 1)) {
            com.bun.lib.a.b(TAG, "bindService Successful!");
            return;
        }
        com.bun.miitmdid.c.e.a aVar = this._BindService;
        if (aVar != null) {
            aVar.b();
        }
        com.bun.lib.a.b(TAG, "bindService Failed!");
    }

    public String getAAID() {
        try {
            if (this.mDeviceidInterface != null) {
                return this.mDeviceidInterface.getAAID();
            }
        } catch (RemoteException e) {
            com.bun.lib.a.b(TAG, "getAAID error, RemoteException!");
        }
        return "";
    }

    public String getOAID() {
        try {
            if (this.mDeviceidInterface != null) {
                return this.mDeviceidInterface.getOAID();
            }
        } catch (RemoteException e) {
            com.bun.lib.a.a(TAG, "getOAID error, RemoteException!");
            e.printStackTrace();
        }
        return "";
    }

    public String getUDID() {
        return "";
    }

    public String getVAID() {
        try {
            if (this.mDeviceidInterface != null) {
                return this.mDeviceidInterface.getVAID();
            }
        } catch (RemoteException e) {
            com.bun.lib.a.b(TAG, "getVAID error, RemoteException!");
        }
        return "";
    }

    public boolean isSupported() {
        try {
            if (this.mDeviceidInterface == null) {
                return false;
            }
            com.bun.lib.a.b(TAG, "Device support opendeviceid");
            return this.mDeviceidInterface.isSupported();
        } catch (Exception e) {
            com.bun.lib.a.b(TAG, "isSupport error, RemoteException!");
            return false;
        }
    }

    public void shutdown() {
        MsaClient msaClient;
        c cVar = this.mDeviceidInterface;
        try {
            if (cVar == null) {
                return;
            }
            try {
                cVar.shutDown();
                if (this.mConnection != null) {
                    this.mContext.unbindService(this.mConnection);
                }
                com.bun.lib.a.b(TAG, "unBind Service successful");
                msaClient = this;
            } catch (Exception e) {
                com.bun.lib.a.b(TAG, "unBind Service exception");
                msaClient = this;
            }
            this.mConnection = null;
            msaClient.mDeviceidInterface = null;
        } catch (Throwable th) {
            this.mConnection = null;
            this.mDeviceidInterface = null;
            throw th;
        }
    }
}
