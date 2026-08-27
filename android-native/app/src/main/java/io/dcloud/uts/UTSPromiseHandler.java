package io.dcloud.uts;

import kotlin.Function;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UTSPromise.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\b\u0016\u0018\u00002\u00020\u0001B1\b\u0016\u0012\f\u0010\u0002\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0003\u0012\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006¢\u0006\u0004\b\u0007\u0010\bR \u0010\u0002\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR \u0010\u0004\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\n\"\u0004\b\u000e\u0010\fR\u001e\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lio/dcloud/uts/UTSPromiseHandler;", "", "onFulfilled", "Lkotlin/Function;", "onRejected", "promise", "Lio/dcloud/uts/UTSPromise;", "<init>", "(Lkotlin/Function;Lkotlin/Function;Lio/dcloud/uts/UTSPromise;)V", "getOnFulfilled", "()Lkotlin/Function;", "setOnFulfilled", "(Lkotlin/Function;)V", "getOnRejected", "setOnRejected", "getPromise", "()Lio/dcloud/uts/UTSPromise;", "setPromise", "(Lio/dcloud/uts/UTSPromise;)V", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class UTSPromiseHandler {
    private Function<?> onFulfilled;
    private Function<?> onRejected;
    public UTSPromise<?> promise;

    public Function<?> getOnFulfilled() {
        return this.onFulfilled;
    }

    public void setOnFulfilled(Function<?> function) {
        this.onFulfilled = function;
    }

    public Function<?> getOnRejected() {
        return this.onRejected;
    }

    public void setOnRejected(Function<?> function) {
        this.onRejected = function;
    }

    public UTSPromise<?> getPromise() {
        UTSPromise<?> uTSPromise = this.promise;
        if (uTSPromise != null) {
            return uTSPromise;
        }
        Intrinsics.throwUninitializedPropertyAccessException("promise");
        return null;
    }

    public void setPromise(UTSPromise<?> uTSPromise) {
        Intrinsics.checkNotNullParameter(uTSPromise, "<set-?>");
        this.promise = uTSPromise;
    }

    public UTSPromiseHandler(Function<?> function, Function<?> function2, UTSPromise<?> promise) {
        Intrinsics.checkNotNullParameter(promise, "promise");
        setOnFulfilled(function);
        setOnRejected(function2);
        setPromise(promise);
    }
}
