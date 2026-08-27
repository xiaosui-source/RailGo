package com.heytap.openid;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

/* loaded from: classes.dex */
public interface a extends IInterface {

    /* renamed from: com.heytap.openid.a$a, reason: collision with other inner class name */
    public static abstract class AbstractBinderC0012a extends Binder implements a {

        /* renamed from: com.heytap.openid.a$a$a, reason: collision with other inner class name */
        private static class C0013a implements a {
            public IBinder a;

            public C0013a(IBinder iBinder) {
                this.a = iBinder;
            }

            public native String a(String str, String str2, String str3);

            @Override // android.os.IInterface
            public native IBinder asBinder();
        }

        public static native a a(IBinder iBinder);
    }
}
