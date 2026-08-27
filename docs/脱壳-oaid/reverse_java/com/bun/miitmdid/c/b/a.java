package com.bun.miitmdid.c.b;

import android.content.Context;
import android.os.IBinder;
import com.asus.msa.SupplementaryDID.IDidAidlInterface;
import com.asus.msa.sdid.IDIDBinderStatusListener;
import com.asus.msa.sdid.SupplementaryDIDManager;
import com.bun.supplier.InnerIdSupplier;
import com.bun.supplier.SupplierListener;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class a implements InnerIdSupplier, IDIDBinderStatusListener {
    private SupplierListener a;
    private SupplementaryDIDManager f;
    private String b = "";
    private String c = "";
    private String d = "";
    private String e = "";
    private boolean g = false;
    private boolean h = false;

    public a(Context context, SupplierListener supplierListener) {
        this.a = supplierListener;
        this.f = new SupplementaryDIDManager(context);
    }

    @Override // com.asus.msa.sdid.IDIDBinderStatusListener
    public void a(IDidAidlInterface iDidAidlInterface) {
        try {
            this.b = iDidAidlInterface.getUDID();
            if (this.b == null) {
                this.b = "";
            }
        } catch (Exception e) {
        }
        try {
            this.c = iDidAidlInterface.getOAID();
            if (this.c == null) {
                this.c = "";
            }
        } catch (Exception e2) {
        }
        try {
            this.d = iDidAidlInterface.getVAID();
            if (this.d == null) {
                this.d = "";
            }
        } catch (Exception e3) {
        }
        try {
            this.e = iDidAidlInterface.getAAID();
            if (this.e == null) {
                this.e = "";
            }
        } catch (Exception e4) {
        }
        try {
            this.h = iDidAidlInterface.a();
        } catch (Exception e5) {
        }
        this.g = true;
        SupplierListener supplierListener = this.a;
        if (supplierListener != null) {
            supplierListener.OnSupport(this.h, this);
        }
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public void a(SupplierListener supplierListener) {
        this.f.init(this);
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public boolean a() {
        return false;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return null;
    }

    @Override // com.asus.msa.sdid.IDIDBinderStatusListener
    public void b() {
        SupplierListener supplierListener = this.a;
        if (supplierListener != null) {
            supplierListener.OnSupport(false, this);
        }
    }

    @Override // com.bun.supplier.IdSupplier
    public String getAAID() {
        return this.e;
    }

    @Override // com.bun.supplier.IdSupplier
    public String getOAID() {
        return this.c;
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public String getUDID() {
        return this.b;
    }

    @Override // com.bun.supplier.IdSupplier
    public String getVAID() {
        return this.d;
    }

    @Override // com.bun.supplier.IdSupplier
    public boolean isSupported() {
        return this.h;
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public void shutDown() {
        SupplementaryDIDManager supplementaryDIDManager;
        if (!this.g || (supplementaryDIDManager = this.f) == null) {
            return;
        }
        supplementaryDIDManager.deInit();
    }
}
