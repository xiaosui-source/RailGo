package com.bun.miitmdid.c.d;

import android.content.Context;
import com.bun.supplier.InnerIdSupplier;
import com.bun.supplier.SupplierListener;

/* loaded from: classes.dex */
public class a implements InnerIdSupplier {
    private Context a;
    private SupplierListener f;
    private boolean e = false;
    private String b = "";
    private String c = "";
    private String d = "";

    /* renamed from: com.bun.miitmdid.c.d.a$a, reason: collision with other inner class name */
    class RunnableC0007a implements Runnable {
        RunnableC0007a() {
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    public a(Context context) {
        this.a = context;
    }

    static native /* synthetic */ Context a(a aVar);

    static native /* synthetic */ String a(a aVar, String str);

    private native void b();

    static native /* synthetic */ void b(a aVar);

    @Override // com.bun.supplier.InnerIdSupplier
    public native void a(SupplierListener supplierListener);

    @Override // com.bun.supplier.InnerIdSupplier
    public native boolean a();

    @Override // com.bun.supplier.IdSupplier
    public native String getAAID();

    @Override // com.bun.supplier.IdSupplier
    public native String getOAID();

    @Override // com.bun.supplier.InnerIdSupplier
    public native String getUDID();

    @Override // com.bun.supplier.IdSupplier
    public native String getVAID();

    @Override // com.bun.supplier.IdSupplier
    public native boolean isSupported();

    @Override // com.bun.supplier.InnerIdSupplier
    public native void shutDown();
}
