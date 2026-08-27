package io.dcloud.uts;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: UTSIterator.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0019\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0005\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b¨\u0006\r"}, d2 = {"Lio/dcloud/uts/UTSIteratorResult;", "T", "", "done", "", "value", "<init>", "(ZLjava/lang/Object;)V", "getDone", "()Z", "getValue", "()Ljava/lang/Object;", "Ljava/lang/Object;", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UTSIteratorResult<T> {
    private final boolean done;
    private final T value;

    public UTSIteratorResult(boolean z, T t) {
        this.done = z;
        this.value = t;
    }

    public /* synthetic */ UTSIteratorResult(boolean z, java.lang.Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, obj);
    }

    public final boolean getDone() {
        return this.done;
    }

    public final T getValue() {
        return this.value;
    }
}
