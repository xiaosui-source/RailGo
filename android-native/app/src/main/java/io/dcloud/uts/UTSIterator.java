package io.dcloud.uts;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: UTSIterator.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001b\u0012\u0012\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00050\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\u000f\u001a\u00020\u0010H\u0096\u0002J\u000e\u0010\u0003\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0015R\u001d\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\"\u0010\n\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u0016"}, d2 = {"Lio/dcloud/uts/UTSIterator;", "T", "", "next", "Lkotlin/Function0;", "Lio/dcloud/uts/UTSIteratorResult;", "<init>", "(Lkotlin/jvm/functions/Function0;)V", "getNext", "()Lkotlin/jvm/functions/Function0;", "cacheNext", "getCacheNext", "()Lio/dcloud/uts/UTSIteratorResult;", "setCacheNext", "(Lio/dcloud/uts/UTSIteratorResult;)V", "hasNext", "", "getHasNext", "()Z", "setHasNext", "(Z)V", "()Ljava/lang/Object;", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class UTSIterator<T> implements Iterator<T>, KMappedMarker {
    private UTSIteratorResult<T> cacheNext;
    private boolean hasNext;
    private final Function0<UTSIteratorResult<T>> next;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public UTSIterator(Function0<UTSIteratorResult<T>> next) {
        Intrinsics.checkNotNullParameter(next, "next");
        this.next = next;
        Intrinsics.checkNotNull(next);
        UTSIteratorResult<T> uTSIteratorResultInvoke = next.invoke();
        this.cacheNext = uTSIteratorResultInvoke;
        Intrinsics.checkNotNull(uTSIteratorResultInvoke);
        this.hasNext = !uTSIteratorResultInvoke.getDone();
    }

    public final Function0<UTSIteratorResult<T>> getNext() {
        return this.next;
    }

    public final UTSIteratorResult<T> getCacheNext() {
        return this.cacheNext;
    }

    public final void setCacheNext(UTSIteratorResult<T> uTSIteratorResult) {
        this.cacheNext = uTSIteratorResult;
    }

    public final boolean getHasNext() {
        return this.hasNext;
    }

    public final void setHasNext(boolean z) {
        this.hasNext = z;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.hasNext;
    }

    @Override // java.util.Iterator
    public T next() {
        UTSIteratorResult<T> uTSIteratorResult = this.cacheNext;
        if (uTSIteratorResult != null) {
            Intrinsics.checkNotNull(uTSIteratorResult);
            T value = uTSIteratorResult.getValue();
            Function0<UTSIteratorResult<T>> function0 = this.next;
            Intrinsics.checkNotNull(function0);
            UTSIteratorResult<T> uTSIteratorResultInvoke = function0.invoke();
            this.cacheNext = uTSIteratorResultInvoke;
            Intrinsics.checkNotNull(uTSIteratorResultInvoke);
            this.hasNext = !uTSIteratorResultInvoke.getDone();
            return value;
        }
        Function0<UTSIteratorResult<T>> function02 = this.next;
        Intrinsics.checkNotNull(function02);
        UTSIteratorResult<T> uTSIteratorResultInvoke2 = function02.invoke();
        if (uTSIteratorResultInvoke2.getDone()) {
            this.hasNext = false;
        }
        return uTSIteratorResultInvoke2.getValue();
    }
}
