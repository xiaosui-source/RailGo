package kotlin.reflect.jvm.internal.impl.km;

import com.taobao.weex.el.parse.Operators;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Nodes.kt */
/* loaded from: classes2.dex */
public final class KmFlexibleTypeUpperBound {
    public static final Companion Companion = new Companion(null);
    private KmType type;
    private String typeFlexibilityId;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KmFlexibleTypeUpperBound)) {
            return false;
        }
        KmFlexibleTypeUpperBound kmFlexibleTypeUpperBound = (KmFlexibleTypeUpperBound) obj;
        return Intrinsics.areEqual(this.type, kmFlexibleTypeUpperBound.type) && Intrinsics.areEqual(this.typeFlexibilityId, kmFlexibleTypeUpperBound.typeFlexibilityId);
    }

    public int hashCode() {
        int iHashCode = this.type.hashCode() * 31;
        String str = this.typeFlexibilityId;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    public String toString() {
        return "KmFlexibleTypeUpperBound(type=" + this.type + ", typeFlexibilityId=" + this.typeFlexibilityId + Operators.BRACKET_END;
    }

    public KmFlexibleTypeUpperBound(KmType type, String str) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
        this.typeFlexibilityId = str;
    }

    public final KmType getType() {
        return this.type;
    }

    public final String getTypeFlexibilityId() {
        return this.typeFlexibilityId;
    }

    /* compiled from: Nodes.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
