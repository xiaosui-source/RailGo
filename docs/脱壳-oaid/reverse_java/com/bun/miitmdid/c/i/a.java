package com.bun.miitmdid.c.i;

import android.content.Context;
import android.os.Build;
import com.bun.supplier.DefaultSupplier;
import com.bun.supplier.InnerIdSupplier;
import com.bun.supplier.SupplierListener;
import com.heytap.openid.sdk.OpenIDSDK;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class a implements InnerIdSupplier {
    private Context a;

    /* renamed from: com.bun.miitmdid.c.i.a$a, reason: collision with other inner class name */
    class RunnableC0005a implements Runnable {
        final /* synthetic */ SupplierListener a;

        RunnableC0005a(SupplierListener supplierListener) {
            this.a = supplierListener;
        }

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:11:0x0025 -> B:15:0x0015). Please report as a decompilation issue!!! */
        @Override // java.lang.Runnable
        public void run() {
            try {
                if (this.a != null) {
                    if (Build.VERSION.SDK_INT <= 28) {
                        this.a.OnSupport(false, new DefaultSupplier());
                    } else {
                        this.a.OnSupport(a.this.isSupported(), a.this);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public a(Context context) {
        OpenIDSDK.d(context);
        this.a = context;
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public void a(SupplierListener supplierListener) {
        new Thread(new RunnableC0005a(supplierListener)).start();
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public boolean a() {
        return false;
    }

    @Override // com.bun.supplier.IdSupplier
    public String getAAID() {
        try {
            String strA = OpenIDSDK.a(this.a);
            return strA == null ? "" : strA;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override // com.bun.supplier.IdSupplier
    public String getOAID() {
        try {
            String strB = OpenIDSDK.b(this.a);
            return strB == null ? "" : strB;
        } catch (Exception e) {
            e.printStackTrace();
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
            String strC = OpenIDSDK.c(this.a);
            return strC == null ? "" : strC;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override // com.bun.supplier.IdSupplier
    public boolean isSupported() {
        return OpenIDSDK.a();
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public void shutDown() {
    }
}
