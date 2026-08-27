package com.bun.miitmdid.c.k;

import android.content.Context;
import android.text.TextUtils;
import com.bun.lib.sysParamters;
import com.bun.supplier.InnerIdSupplier;
import com.bun.supplier.SupplierListener;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class b implements InnerIdSupplier {
    private Context a;

    public b(Context context) {
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
        String strA = a.a(this.a);
        return TextUtils.isEmpty(strA) ? sysParamters.e() : strA;
    }

    @Override // com.bun.supplier.IdSupplier
    public String getOAID() {
        String strB = a.b(this.a);
        return strB == null ? "" : strB;
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public String getUDID() {
        return "";
    }

    @Override // com.bun.supplier.IdSupplier
    public String getVAID() {
        String strC = a.c(this.a);
        return strC == null ? "" : strC;
    }

    @Override // com.bun.supplier.IdSupplier
    public boolean isSupported() {
        return a.a();
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public void shutDown() {
    }
}
