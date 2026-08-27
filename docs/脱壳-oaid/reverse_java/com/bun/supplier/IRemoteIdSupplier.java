package com.bun.supplier;

import android.support.annotation.Keep;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public interface IRemoteIdSupplier extends InnerIdSupplier {
    @Keep
    String getAAID(String str);

    @Keep
    String getVAID(String str);
}
