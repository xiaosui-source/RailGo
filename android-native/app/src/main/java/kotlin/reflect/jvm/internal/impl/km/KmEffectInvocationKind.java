package kotlin.reflect.jvm.internal.impl.km;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: Contracts.kt */
/* loaded from: classes2.dex */
public final class KmEffectInvocationKind {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ KmEffectInvocationKind[] $VALUES;
    public static final KmEffectInvocationKind AT_MOST_ONCE = new KmEffectInvocationKind("AT_MOST_ONCE", 0);
    public static final KmEffectInvocationKind EXACTLY_ONCE = new KmEffectInvocationKind("EXACTLY_ONCE", 1);
    public static final KmEffectInvocationKind AT_LEAST_ONCE = new KmEffectInvocationKind("AT_LEAST_ONCE", 2);

    private static final /* synthetic */ KmEffectInvocationKind[] $values() {
        return new KmEffectInvocationKind[]{AT_MOST_ONCE, EXACTLY_ONCE, AT_LEAST_ONCE};
    }

    public static KmEffectInvocationKind valueOf(String str) {
        return (KmEffectInvocationKind) Enum.valueOf(KmEffectInvocationKind.class, str);
    }

    public static KmEffectInvocationKind[] values() {
        return (KmEffectInvocationKind[]) $VALUES.clone();
    }

    private KmEffectInvocationKind(String str, int i) {
    }

    static {
        KmEffectInvocationKind[] kmEffectInvocationKindArr$values = $values();
        $VALUES = kmEffectInvocationKindArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(kmEffectInvocationKindArr$values);
    }
}
