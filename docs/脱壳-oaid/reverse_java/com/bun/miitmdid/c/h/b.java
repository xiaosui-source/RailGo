package com.bun.miitmdid.c.h;

import android.content.Context;
import com.bun.lib.sysParamters;
import com.bun.supplier.InnerIdSupplier;
import com.bun.supplier.SupplierListener;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class b implements InnerIdSupplier {
    private Context a;

    class a implements Runnable {
        final /* synthetic */ SupplierListener a;

        a(SupplierListener supplierListener) {
            this.a = supplierListener;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
            } catch (Exception e) {
                com.bun.lib.a.a("buniasuplier", "exception", e);
            }
            if (this.a != null) {
                this.a.OnSupport(b.this.isSupported(), b.this);
                return;
            }
            return;
        }
    }

    public b(Context context) {
        this.a = context;
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public void a(SupplierListener supplierListener) {
        new Thread(new a(supplierListener)).start();
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public boolean a() {
        return false;
    }

    @Override // com.bun.supplier.IdSupplier
    public String getAAID() {
        try {
            return com.bun.miitmdid.c.h.a.a(this.a, sysParamters.g());
        } catch (Exception e) {
            return "";
        }
    }

    @Override // com.bun.supplier.IdSupplier
    public String getOAID() {
        try {
            return com.bun.miitmdid.c.h.a.a(this.a);
        } catch (Exception e) {
            return "";
        }
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public String getUDID() {
        return "";
    }

    @Override // com.bun.supplier.IdSupplier
    public String getVAID() {
        try {
            return com.bun.miitmdid.c.h.a.b(this.a, sysParamters.g());
        } catch (Exception e) {
            return "";
        }
    }

    @Override // com.bun.supplier.IdSupplier
    public boolean isSupported() {
        try {
            return com.bun.miitmdid.c.h.a.b(this.a);
        } catch (Exception e) {
            return false;
        }
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public void shutDown() {
    }
}
