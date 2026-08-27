package com.bun.supplier;

import android.support.annotation.Keep;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public interface InnerIdSupplier extends IdSupplier {
    void a(SupplierListener supplierListener);

    boolean a();

    @Keep
    String getUDID();

    @Keep
    void shutDown();
}
