package com.bun.supplier;

import android.support.annotation.Keep;

@Keep
/* loaded from: /workspace/39285EFA.decrypted.dex */
public interface IdSupplier {
    @Keep
    String getAAID();

    @Keep
    String getOAID();

    @Keep
    String getVAID();

    @Keep
    boolean isSupported();
}
