package com.bun.miitmdid.c.d;

import android.content.Context;
import android.text.TextUtils;
import com.bun.lib.sysParamters;
import com.bun.supplier.DefaultSupplier;
import com.bun.supplier.InnerIdSupplier;
import com.bun.supplier.SupplierListener;
import com.huawei.android.hms.pps.AdvertisingIdClient;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class a implements InnerIdSupplier {
    private Context a;
    private SupplierListener f;
    private boolean e = false;
    private String b = "";
    private String c = "";
    private String d = "";

    /* renamed from: com.bun.miitmdid.c.d.a$a, reason: collision with other inner class name */
    class RunnableC0003a implements Runnable {
        RunnableC0003a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(a.this.a);
                a.this.b = advertisingIdInfo.getId();
                advertisingIdInfo.isLimitAdTrackingEnabled();
            } catch (Exception e) {
                com.bun.lib.a.a(a.class.getSimpleName(), "thread", e);
            }
            a.this.b();
        }
    }

    public a(Context context) {
        this.a = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        try {
            this.e = !TextUtils.isEmpty(this.b);
            if (this.e) {
                if (this.f != null) {
                    this.f.OnSupport(this.e, this);
                }
            } else if (this.f != null) {
                this.f.OnSupport(this.e, new DefaultSupplier());
            }
        } catch (Exception e) {
            com.bun.lib.a.a(a.class.getSimpleName(), "CallBack", e);
        }
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public void a(SupplierListener supplierListener) {
        this.f = supplierListener;
        new Thread(new RunnableC0003a()).start();
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public boolean a() {
        return false;
    }

    @Override // com.bun.supplier.IdSupplier
    public String getAAID() {
        return TextUtils.isEmpty(this.d) ? sysParamters.e() : this.d;
    }

    @Override // com.bun.supplier.IdSupplier
    public String getOAID() {
        return this.b;
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public String getUDID() {
        return "";
    }

    @Override // com.bun.supplier.IdSupplier
    public String getVAID() {
        return this.c;
    }

    @Override // com.bun.supplier.IdSupplier
    public boolean isSupported() {
        return this.e;
    }

    @Override // com.bun.supplier.InnerIdSupplier
    public void shutDown() {
    }
}
