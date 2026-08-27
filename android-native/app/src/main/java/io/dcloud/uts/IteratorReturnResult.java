package io.dcloud.uts;

import com.taobao.weex.el.parse.Operators;
import kotlin.Metadata;
import kotlin.UByte$$ExternalSyntheticBackport0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: TypedArray.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000f\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0019\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\r\u001a\u00020\u0004HÆ\u0003J\u000e\u0010\u000e\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\u000bJ(\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00028\u0000HÆ\u0001¢\u0006\u0002\u0010\u0010J\u0013\u0010\u0011\u001a\u00020\u00042\b\u0010\u0012\u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0005\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b¨\u0006\u0017"}, d2 = {"Lio/dcloud/uts/IteratorReturnResult;", "T", "", "done", "", "value", "<init>", "(ZLjava/lang/Object;)V", "getDone", "()Z", "getValue", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "component2", "copy", "(ZLjava/lang/Object;)Lio/dcloud/uts/IteratorReturnResult;", "equals", "other", "hashCode", "", "toString", "", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class IteratorReturnResult<T> {
    private final boolean done;
    private final T value;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ IteratorReturnResult copy$default(IteratorReturnResult iteratorReturnResult, boolean z, java.lang.Object obj, int i, java.lang.Object obj2) {
        if ((i & 1) != 0) {
            z = iteratorReturnResult.done;
        }
        if ((i & 2) != 0) {
            obj = iteratorReturnResult.value;
        }
        return iteratorReturnResult.copy(z, obj);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getDone() {
        return this.done;
    }

    public final T component2() {
        return this.value;
    }

    public final IteratorReturnResult<T> copy(boolean done, T value) {
        return new IteratorReturnResult<>(done, value);
    }

    public boolean equals(java.lang.Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IteratorReturnResult)) {
            return false;
        }
        IteratorReturnResult iteratorReturnResult = (IteratorReturnResult) other;
        return this.done == iteratorReturnResult.done && Intrinsics.areEqual(this.value, iteratorReturnResult.value);
    }

    public int hashCode() {
        int iM = UByte$$ExternalSyntheticBackport0.m(this.done) * 31;
        T t = this.value;
        return iM + (t == null ? 0 : t.hashCode());
    }

    public String toString() {
        return "IteratorReturnResult(done=" + this.done + ", value=" + this.value + Operators.BRACKET_END;
    }

    public IteratorReturnResult(boolean z, T t) {
        this.done = z;
        this.value = t;
    }

    public /* synthetic */ IteratorReturnResult(boolean z, java.lang.Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, obj);
    }

    public final boolean getDone() {
        return this.done;
    }

    public final T getValue() {
        return this.value;
    }
}
