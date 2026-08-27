package com.bun.miitmdid.c.g;

import android.content.Context;
import com.bun.supplier.InnerIdSupplier;
import com.bun.supplier.SupplierListener;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class b implements InnerIdSupplier, com.bun.miitmdid.c.e.a {
    private a a;
    private SupplierListener b;

    public b(Context context, SupplierListener supplierListener) {
        this.b = supplierListener;
        this.a = new a(context, this);
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public void a(SupplierListener supplierListener) {
    }

    @Override // com.bun.miitmdid.c.e.a
    public void a(boolean z) {
        SupplierListener supplierListener = this.b;
        if (supplierListener != null) {
            supplierListener.OnSupport(isSupported(), this);
        }
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public boolean a() {
        return false;
    }

    @Override // com.bun.miitmdid.c.e.a
    public void b() {
        SupplierListener supplierListener = this.b;
        if (supplierListener != null) {
            supplierListener.OnSupport(isSupported(), this);
        }
    }

    @Override // com.bun.supplier.IdSupplier
    public String getAAID() {
        String strA;
        return (isSupported() && (strA = this.a.a()) != null) ? strA : "";
    }

    @Override // com.bun.supplier.IdSupplier
    public String getOAID() {
        String strB;
        return (isSupported() && (strB = this.a.b()) != null) ? strB : "";
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public String getUDID() {
        String strC;
        return (isSupported() && (strC = this.a.c()) != null) ? strC : "";
    }

    @Override // com.bun.supplier.IdSupplier
    public String getVAID() {
        String strD;
        return (isSupported() && (strD = this.a.d()) != null) ? strD : "";
    }

    @Override // com.bun.supplier.IdSupplier
    public boolean isSupported() {
        a aVar = this.a;
        if (aVar != null) {
            return aVar.e();
        }
        return false;
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public void shutDown() {
        a aVar = this.a;
        if (aVar != null) {
            aVar.f();
        }
    }
}
