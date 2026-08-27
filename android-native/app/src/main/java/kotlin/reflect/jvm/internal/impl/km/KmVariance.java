package kotlin.reflect.jvm.internal.impl.km;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: Nodes.kt */
/* loaded from: classes2.dex */
public final class KmVariance {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ KmVariance[] $VALUES;
    public static final KmVariance INVARIANT = new KmVariance("INVARIANT", 0);
    public static final KmVariance IN = new KmVariance("IN", 1);
    public static final KmVariance OUT = new KmVariance("OUT", 2);

    private static final /* synthetic */ KmVariance[] $values() {
        return new KmVariance[]{INVARIANT, IN, OUT};
    }

    public static KmVariance valueOf(String str) {
        return (KmVariance) Enum.valueOf(KmVariance.class, str);
    }

    public static KmVariance[] values() {
        return (KmVariance[]) $VALUES.clone();
    }

    private KmVariance(String str, int i) {
    }

    static {
        KmVariance[] kmVarianceArr$values = $values();
        $VALUES = kmVarianceArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(kmVarianceArr$values);
    }
}
