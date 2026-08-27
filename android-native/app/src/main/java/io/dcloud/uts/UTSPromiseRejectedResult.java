package io.dcloud.uts;

import kotlin.Metadata;
import kotlin.Unit;

/* compiled from: UTSPromise.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001R\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u0004X¦\u000e¢\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\tÀ\u0006\u0003"}, d2 = {"Lio/dcloud/uts/UTSPromiseRejectedResult;", "Lio/dcloud/uts/UTSPromiseSettledResult;", "", "reason", "", "getReason", "()Ljava/lang/Object;", "setReason", "(Ljava/lang/Object;)V", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface UTSPromiseRejectedResult extends UTSPromiseSettledResult<Unit> {
    java.lang.Object getReason();

    void setReason(java.lang.Object obj);
}
