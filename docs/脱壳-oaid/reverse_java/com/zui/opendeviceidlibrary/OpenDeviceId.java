package com.zui.opendeviceidlibrary;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import com.zui.deviceidservice.IDeviceidInterface;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class OpenDeviceId {
    private static String c = "OpenDeviceId library";
    private static boolean d;
    private IDeviceidInterface a;
    private CallBack b;

    /* renamed from: com.zui.opendeviceidlibrary.OpenDeviceId$1, reason: invalid class name */
    class AnonymousClass1 implements ServiceConnection {
        final /* synthetic */ OpenDeviceId a;

        @Override // android.content.ServiceConnection
        public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            this.a.a = IDeviceidInterface.Stub.a(iBinder);
            if (this.a.b != null) {
                this.a.b.a(this.a);
            }
            this.a.a("Service onServiceConnected");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            this.a.a = null;
            this.a.a("Service onServiceDisconnected");
        }
    }

    public interface CallBack {
        void a(OpenDeviceId openDeviceId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        if (d) {
            Log.i(c, str);
        }
    }
}
