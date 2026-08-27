package kotlin.reflect.jvm.internal.impl.load.java.structure;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: javaElements.kt */
/* loaded from: classes2.dex */
public final class LightClassOriginKind {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ LightClassOriginKind[] $VALUES;
    public static final LightClassOriginKind SOURCE = new LightClassOriginKind("SOURCE", 0);
    public static final LightClassOriginKind BINARY = new LightClassOriginKind("BINARY", 1);

    private static final /* synthetic */ LightClassOriginKind[] $values() {
        return new LightClassOriginKind[]{SOURCE, BINARY};
    }

    public static LightClassOriginKind valueOf(String str) {
        return (LightClassOriginKind) Enum.valueOf(LightClassOriginKind.class, str);
    }

    public static LightClassOriginKind[] values() {
        return (LightClassOriginKind[]) $VALUES.clone();
    }

    private LightClassOriginKind(String str, int i) {
    }

    static {
        LightClassOriginKind[] lightClassOriginKindArr$values = $values();
        $VALUES = lightClassOriginKindArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(lightClassOriginKindArr$values);
    }
}
