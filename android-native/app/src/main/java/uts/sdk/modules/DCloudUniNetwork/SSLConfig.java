package uts.sdk.modules.DCloudUniNetwork;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\n\b\u0016\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\n\u0010\n\u001a\u0004\u0018\u00010\u0005H\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0005H\u0016J\n\u0010\u000e\u001a\u0004\u0018\u00010\u0005H\u0016J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0005H\u0016J\u0015\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\bH\u0016¢\u0006\u0002\u0010\u0012J\u001b\u0010\u0013\u001a\u00020\f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\bH\u0016¢\u0006\u0002\u0010\u0015R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\t¨\u0006\u0016"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/SSLConfig;", "", "<init>", "()V", "keystore", "", "storePass", "ca", "", "[Ljava/lang/String;", "getKeystore", "setKeystore", "", "reassignedKs", "getStorePass", "setStorePass", "reassignedSp", "getCa", "()[Ljava/lang/String;", "setCa", "reassignedCa", "([Ljava/lang/String;)V", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class SSLConfig {
    private String[] ca;
    private String keystore;
    private String storePass;

    public String getKeystore() {
        return this.keystore;
    }

    public void setKeystore(String reassignedKs) {
        Intrinsics.checkNotNullParameter(reassignedKs, "reassignedKs");
        this.keystore = reassignedKs;
    }

    public String getStorePass() {
        return this.storePass;
    }

    public void setStorePass(String reassignedSp) {
        Intrinsics.checkNotNullParameter(reassignedSp, "reassignedSp");
        this.storePass = reassignedSp;
    }

    public String[] getCa() {
        return this.ca;
    }

    public void setCa(String[] reassignedCa) {
        Intrinsics.checkNotNullParameter(reassignedCa, "reassignedCa");
        this.ca = reassignedCa;
    }
}
