package com.zui.opendeviceidlibrary;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.zui.deviceidservice.IDeviceidInterface;

/* loaded from: classes.dex */
public class OpenDeviceId {
    private static String c = "OpenDeviceId library";
    private static boolean d;
    private IDeviceidInterface a;
    private CallBack b;

    /* renamed from: com.zui.opendeviceidlibrary.OpenDeviceId$1, reason: invalid class name */
    class AnonymousClass1 implements ServiceConnection {
        final /* synthetic */ OpenDeviceId a;

        @Override // android.content.ServiceConnection
        public native synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder);

        @Override // android.content.ServiceConnection
        public native void onServiceDisconnected(ComponentName componentName);
    }

    public interface CallBack {
        void a(OpenDeviceId openDeviceId);
    }

    static native /* synthetic */ IDeviceidInterface a(OpenDeviceId openDeviceId, IDeviceidInterface iDeviceidInterface);

    static native /* synthetic */ CallBack a(OpenDeviceId openDeviceId);

    static native /* synthetic */ void a(OpenDeviceId openDeviceId, String str);

    private native void a(String str);
}
