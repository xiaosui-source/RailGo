package com.asus.msa.sdid;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.asus.msa.SupplementaryDID.IDidAidlInterface;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public interface IDIDBinderStatusListener extends IInterface {

    public static abstract class Stub extends Binder implements IDIDBinderStatusListener {

        public static class Proxy implements IDIDBinderStatusListener {
            public IBinder a;

            @Override // com.asus.msa.sdid.IDIDBinderStatusListener
            public void a(IDidAidlInterface iDidAidlInterface) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.asus.msa.sdid.IDIDBinderStatusListener");
                    parcelObtain.writeStrongBinder(iDidAidlInterface != null ? iDidAidlInterface.asBinder() : null);
                    this.a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.asus.msa.sdid.IDIDBinderStatusListener
            public void b() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.asus.msa.sdid.IDIDBinderStatusListener");
                    this.a.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.asus.msa.sdid.IDIDBinderStatusListener");
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i == 1) {
                parcel.enforceInterface("com.asus.msa.sdid.IDIDBinderStatusListener");
                a(IDidAidlInterface.Stub.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            }
            if (i != 2) {
                if (i != 1598968902) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                parcel2.writeString("com.asus.msa.sdid.IDIDBinderStatusListener");
                return true;
            }
            parcel.enforceInterface("com.asus.msa.sdid.IDIDBinderStatusListener");
            b();
            parcel2.writeNoException();
            return true;
        }
    }

    void a(IDidAidlInterface iDidAidlInterface);

    void b();
}
