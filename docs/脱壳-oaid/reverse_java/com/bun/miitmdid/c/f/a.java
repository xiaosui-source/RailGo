package com.bun.miitmdid.c.f;

import android.content.Context;
import com.bun.supplier.InnerIdSupplier;
import com.bun.supplier.SupplierListener;
import com.meizu.flyme.openidsdk.OpenIdHelper;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class a implements InnerIdSupplier {
    private Context a;

    public a(Context context) {
        this.a = context;
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public void a(SupplierListener supplierListener) {
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public boolean a() {
        return true;
    }

    @Override // com.bun.supplier.IdSupplier
    public String getAAID() {
        try {
            return OpenIdHelper.a(this.a);
        } catch (Exception e) {
            return "";
        }
    }

    @Override // com.bun.supplier.IdSupplier
    public String getOAID() {
        try {
            return OpenIdHelper.b(this.a);
        } catch (Exception e) {
            return "";
        }
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public String getUDID() {
        try {
            return OpenIdHelper.c(this.a);
        } catch (Exception e) {
            return "";
        }
    }

    @Override // com.bun.supplier.IdSupplier
    public String getVAID() {
        try {
            return OpenIdHelper.d(this.a);
        } catch (Exception e) {
            return "";
        }
    }

    @Override // com.bun.supplier.IdSupplier
    public boolean isSupported() {
        try {
            return OpenIdHelper.a();
        } catch (Exception e) {
            return false;
        }
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public void shutDown() {
    }
}
