package io.dcloud.uts;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UTSEnumInt.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J\u0011\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0000H\u0096\u0004J\u0011\u0010\b\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0000H\u0096\u0004R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\tÀ\u0006\u0003"}, d2 = {"Lio/dcloud/uts/UTSEnumInt;", "", "value", "", "getValue", "()I", "and", "other", "or", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface UTSEnumInt {
    int and(UTSEnumInt other);

    int getValue();

    int or(UTSEnumInt other);

    /* compiled from: UTSEnumInt.kt */
    /* renamed from: io.dcloud.uts.UTSEnumInt$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static int $default$and(UTSEnumInt _this, UTSEnumInt other) {
            Intrinsics.checkNotNullParameter(other, "other");
            return other.getValue() & _this.getValue();
        }

        public static int $default$or(UTSEnumInt _this, UTSEnumInt other) {
            Intrinsics.checkNotNullParameter(other, "other");
            return other.getValue() | _this.getValue();
        }
    }

    /* compiled from: UTSEnumInt.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final class DefaultImpls {
        @Deprecated
        public static int and(UTSEnumInt uTSEnumInt, UTSEnumInt other) {
            Intrinsics.checkNotNullParameter(other, "other");
            return CC.$default$and(uTSEnumInt, other);
        }

        @Deprecated
        public static int or(UTSEnumInt uTSEnumInt, UTSEnumInt other) {
            Intrinsics.checkNotNullParameter(other, "other");
            return CC.$default$or(uTSEnumInt, other);
        }
    }
}
