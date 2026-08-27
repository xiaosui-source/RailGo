package com.bun.miitmdid.c.j;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.bun.lib.sysParamters;
import com.bun.miitmdid.c.j.b.d;
import com.bun.supplier.DefaultSupplier;
import com.bun.supplier.InnerIdSupplier;
import com.bun.supplier.SupplierListener;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class a implements InnerIdSupplier {
    private String a = "";
    private Context b;

    /* renamed from: com.bun.miitmdid.c.j.a$a, reason: collision with other inner class name */
    class RunnableC0006a implements Runnable {
        final /* synthetic */ SupplierListener a;

        RunnableC0006a(SupplierListener supplierListener) {
            this.a = supplierListener;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (this.a != null) {
                    if (Build.VERSION.SDK_INT < 28) {
                        this.a.OnSupport(false, new DefaultSupplier());
                    } else {
                        this.a.OnSupport(a.this.isSupported(), a.this);
                    }
                }
            } catch (Exception e) {
                com.bun.lib.a.a("vivosuplier", "exception", e);
            }
        }
    }

    public a(Context context) {
        this.b = context;
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public void a(SupplierListener supplierListener) {
        new Thread(new RunnableC0006a(supplierListener)).start();
    }

    public void a(String str) {
        this.a = str;
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public boolean a() {
        return false;
    }

    @Override // com.bun.supplier.IdSupplier
    public String getAAID() {
        String strA = d.a(this.b, this.a);
        return TextUtils.isEmpty(strA) ? sysParamters.e() : strA;
    }

    @Override // com.bun.supplier.IdSupplier
    public String getOAID() {
        String strA = d.a(this.b);
        return strA == null ? "" : strA;
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public String getUDID() {
        return "";
    }

    @Override // com.bun.supplier.IdSupplier
    public String getVAID() {
        String strB = d.b(this.b, this.a);
        return strB == null ? "" : strB;
    }

    @Override // com.bun.supplier.IdSupplier
    public boolean isSupported() {
        return d.b(this.b);
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public void shutDown() {
    }
}
