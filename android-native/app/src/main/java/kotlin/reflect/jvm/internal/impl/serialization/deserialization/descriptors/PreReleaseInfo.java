package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import com.taobao.weex.el.parse.Operators;
import java.util.List;
import kotlin.UByte$$ExternalSyntheticBackport0;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DeserializedContainerSource.kt */
/* loaded from: classes2.dex */
public final class PreReleaseInfo {
    public static final Companion Companion = new Companion(null);
    private static final PreReleaseInfo DEFAULT_VISIBLE = new PreReleaseInfo(false, 0 == true ? 1 : 0, 2, 0 == true ? 1 : 0);
    private final boolean isInvisible;
    private final List<String> poisoningFeatures;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PreReleaseInfo)) {
            return false;
        }
        PreReleaseInfo preReleaseInfo = (PreReleaseInfo) obj;
        return this.isInvisible == preReleaseInfo.isInvisible && Intrinsics.areEqual(this.poisoningFeatures, preReleaseInfo.poisoningFeatures);
    }

    public int hashCode() {
        return (UByte$$ExternalSyntheticBackport0.m(this.isInvisible) * 31) + this.poisoningFeatures.hashCode();
    }

    public String toString() {
        return "PreReleaseInfo(isInvisible=" + this.isInvisible + ", poisoningFeatures=" + this.poisoningFeatures + Operators.BRACKET_END;
    }

    /* compiled from: DeserializedContainerSource.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public PreReleaseInfo(boolean z, List<String> poisoningFeatures) {
        Intrinsics.checkNotNullParameter(poisoningFeatures, "poisoningFeatures");
        this.isInvisible = z;
        this.poisoningFeatures = poisoningFeatures;
    }

    public /* synthetic */ PreReleaseInfo(boolean z, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, (i & 2) != 0 ? CollectionsKt.emptyList() : list);
    }
}
