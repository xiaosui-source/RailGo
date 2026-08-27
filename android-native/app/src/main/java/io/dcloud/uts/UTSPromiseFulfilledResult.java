package io.dcloud.uts;

import kotlin.Metadata;

/* compiled from: UTSPromise.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002R\u0018\u0010\u0003\u001a\u00028\u0000X¦\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\bÀ\u0006\u0003"}, d2 = {"Lio/dcloud/uts/UTSPromiseFulfilledResult;", "T", "Lio/dcloud/uts/UTSPromiseSettledResult;", "value", "getValue", "()Ljava/lang/Object;", "setValue", "(Ljava/lang/Object;)V", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface UTSPromiseFulfilledResult<T> extends UTSPromiseSettledResult<T> {
    T getValue();

    void setValue(T t);
}
