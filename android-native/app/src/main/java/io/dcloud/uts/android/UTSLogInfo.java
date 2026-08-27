package io.dcloud.uts.android;

import com.taobao.weex.el.parse.Operators;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UTSLogInfo.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0001HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0004HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0004HÆ\u0003J)\u0010\u0010\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00012\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0004HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0004HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0017"}, d2 = {"Lio/dcloud/uts/android/UTSLogInfo;", "", "value", "type", "", "subtype", "<init>", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)V", "getValue", "()Ljava/lang/Object;", "getType", "()Ljava/lang/String;", "getSubtype", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class UTSLogInfo {
    private final String subtype;
    private final String type;
    private final Object value;

    public static /* synthetic */ UTSLogInfo copy$default(UTSLogInfo uTSLogInfo, Object obj, String str, String str2, int i, Object obj2) {
        if ((i & 1) != 0) {
            obj = uTSLogInfo.value;
        }
        if ((i & 2) != 0) {
            str = uTSLogInfo.type;
        }
        if ((i & 4) != 0) {
            str2 = uTSLogInfo.subtype;
        }
        return uTSLogInfo.copy(obj, str, str2);
    }

    /* renamed from: component1, reason: from getter */
    public final Object getValue() {
        return this.value;
    }

    /* renamed from: component2, reason: from getter */
    public final String getType() {
        return this.type;
    }

    /* renamed from: component3, reason: from getter */
    public final String getSubtype() {
        return this.subtype;
    }

    public final UTSLogInfo copy(Object value, String type, String subtype) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(subtype, "subtype");
        return new UTSLogInfo(value, type, subtype);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UTSLogInfo)) {
            return false;
        }
        UTSLogInfo uTSLogInfo = (UTSLogInfo) other;
        return Intrinsics.areEqual(this.value, uTSLogInfo.value) && Intrinsics.areEqual(this.type, uTSLogInfo.type) && Intrinsics.areEqual(this.subtype, uTSLogInfo.subtype);
    }

    public int hashCode() {
        Object obj = this.value;
        return ((((obj == null ? 0 : obj.hashCode()) * 31) + this.type.hashCode()) * 31) + this.subtype.hashCode();
    }

    public String toString() {
        return "UTSLogInfo(value=" + this.value + ", type=" + this.type + ", subtype=" + this.subtype + Operators.BRACKET_END;
    }

    public UTSLogInfo(Object obj, String type, String subtype) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(subtype, "subtype");
        this.value = obj;
        this.type = type;
        this.subtype = subtype;
    }

    public final String getSubtype() {
        return this.subtype;
    }

    public final String getType() {
        return this.type;
    }

    public final Object getValue() {
        return this.value;
    }
}
