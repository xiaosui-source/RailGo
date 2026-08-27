package com.bun.miitmdid.supplier.msa;

import android.content.Context;
import android.text.TextUtils;
import com.bun.lib.sysParamters;
import com.bun.supplier.DefaultSupplier;
import com.bun.supplier.InnerIdSupplier;
import com.bun.supplier.SupplierListener;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class b implements InnerIdSupplier, com.bun.miitmdid.c.e.a {
    public SupplierListener a;
    private MsaClient b;

    public b(Context context) {
        if (MsaClient.CheckService(context)) {
            String strG = sysParamters.g();
            if (!TextUtils.isEmpty(strG)) {
                MsaClient.StartMsaKlService(context, strG);
            }
            this.b = new MsaClient(context, this);
        }
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public void a(SupplierListener supplierListener) {
        this.a = supplierListener;
        if (this.b == null) {
            b();
        } else {
            this.b.BindService(sysParamters.g());
        }
    }

    @Override // com.bun.miitmdid.c.e.a
    public void a(boolean z) {
        SupplierListener supplierListener = this.a;
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
        SupplierListener supplierListener = this.a;
        if (supplierListener != null) {
            supplierListener.OnSupport(false, new DefaultSupplier());
        }
    }

    @Override // com.bun.supplier.IdSupplier
    public String getAAID() {
        String aaid;
        return (isSupported() && (aaid = this.b.getAAID()) != null) ? aaid : "";
    }

    @Override // com.bun.supplier.IdSupplier
    public String getOAID() {
        String oaid;
        return (isSupported() && (oaid = this.b.getOAID()) != null) ? oaid : "";
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public String getUDID() {
        String udid;
        return (isSupported() && (udid = this.b.getUDID()) != null) ? udid : "";
    }

    @Override // com.bun.supplier.IdSupplier
    public String getVAID() {
        String vaid;
        return (isSupported() && (vaid = this.b.getVAID()) != null) ? vaid : "";
    }

    @Override // com.bun.supplier.IdSupplier
    public boolean isSupported() {
        MsaClient msaClient = this.b;
        if (msaClient != null) {
            return msaClient.isSupported();
        }
        return false;
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public void shutDown() {
        MsaClient msaClient = this.b;
        if (msaClient != null) {
            msaClient.shutdown();
        }
    }
}
