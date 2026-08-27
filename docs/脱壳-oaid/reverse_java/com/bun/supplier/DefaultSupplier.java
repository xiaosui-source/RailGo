package com.bun.supplier;

import android.support.annotation.Keep;

@Keep
/* loaded from: /workspace/39285EFA.decrypted.dex */
public class DefaultSupplier implements IdSupplier {
    @Override // com.bun.supplier.IdSupplier
    @Keep
    public String getAAID() {
        return "";
    }

    @Override // com.bun.supplier.IdSupplier
    @Keep
    public String getOAID() {
        return "";
    }

    @Override // com.bun.supplier.IdSupplier
    @Keep
    public String getVAID() {
        return "";
    }

    @Override // com.bun.supplier.IdSupplier
    @Keep
    public boolean isSupported() {
        return false;
    }
}
