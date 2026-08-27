package io.dcloud.uts;

import com.taobao.weex.el.parse.Operators;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.UByte$$ExternalSyntheticBackport0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Date2.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\r\u001a\u00020\u00052\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\u0016"}, d2 = {"Lio/dcloud/uts/ISODateResult;", "", "fields", "", "isLocal", "", "<init>", "([IZ)V", "getFields", "()[I", "()Z", "setLocal", "(Z)V", "equals", "other", "hashCode", "", "component1", "component2", "copy", "toString", "", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class ISODateResult {
    private final int[] fields;
    private boolean isLocal;

    /* JADX WARN: Multi-variable type inference failed */
    public ISODateResult() {
        this(null, false, 3, 0 == true ? 1 : 0);
    }

    public static /* synthetic */ ISODateResult copy$default(ISODateResult iSODateResult, int[] iArr, boolean z, int i, java.lang.Object obj) {
        if ((i & 1) != 0) {
            iArr = iSODateResult.fields;
        }
        if ((i & 2) != 0) {
            z = iSODateResult.isLocal;
        }
        return iSODateResult.copy(iArr, z);
    }

    /* renamed from: component1, reason: from getter */
    public final int[] getFields() {
        return this.fields;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getIsLocal() {
        return this.isLocal;
    }

    public final ISODateResult copy(int[] fields, boolean isLocal) {
        Intrinsics.checkNotNullParameter(fields, "fields");
        return new ISODateResult(fields, isLocal);
    }

    public String toString() {
        return "ISODateResult(fields=" + Arrays.toString(this.fields) + ", isLocal=" + this.isLocal + Operators.BRACKET_END;
    }

    public ISODateResult(int[] fields, boolean z) {
        Intrinsics.checkNotNullParameter(fields, "fields");
        this.fields = fields;
        this.isLocal = z;
    }

    public /* synthetic */ ISODateResult(int[] iArr, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new int[9] : iArr, (i & 2) != 0 ? false : z);
    }

    public final int[] getFields() {
        return this.fields;
    }

    public final boolean isLocal() {
        return this.isLocal;
    }

    public final void setLocal(boolean z) {
        this.isLocal = z;
    }

    public boolean equals(java.lang.Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type io.dcloud.uts.ISODateResult");
        ISODateResult iSODateResult = (ISODateResult) other;
        return Arrays.equals(this.fields, iSODateResult.fields) && this.isLocal == iSODateResult.isLocal;
    }

    public int hashCode() {
        return (Arrays.hashCode(this.fields) * 31) + UByte$$ExternalSyntheticBackport0.m(this.isLocal);
    }
}
