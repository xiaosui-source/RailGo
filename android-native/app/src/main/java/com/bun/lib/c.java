package com.bun.lib;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

/* loaded from: classes.dex */
public interface c extends IInterface {

    public static abstract class a extends Binder implements c {

        /* renamed from: com.bun.lib.c$a$a, reason: collision with other inner class name */
        private static class C0004a implements c {
            private IBinder a;

            C0004a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public native IBinder asBinder();

            @Override // com.bun.lib.c
            public native boolean c();

            @Override // com.bun.lib.c
            public native String getAAID();

            @Override // com.bun.lib.c
            public native String getOAID();

            @Override // com.bun.lib.c
            public native String getVAID();

            @Override // com.bun.lib.c
            public native boolean isSupported();

            @Override // com.bun.lib.c
            public native void shutDown();
        }

        public static native c a(IBinder iBinder);
    }

    boolean c();

    String getAAID();

    String getOAID();

    String getVAID();

    boolean isSupported();

    void shutDown();
}
