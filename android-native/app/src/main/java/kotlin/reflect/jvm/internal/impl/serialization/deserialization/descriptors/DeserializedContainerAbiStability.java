package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: DeserializedContainerSource.kt */
/* loaded from: classes2.dex */
public final class DeserializedContainerAbiStability {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ DeserializedContainerAbiStability[] $VALUES;
    public static final DeserializedContainerAbiStability STABLE = new DeserializedContainerAbiStability("STABLE", 0);
    public static final DeserializedContainerAbiStability UNSTABLE = new DeserializedContainerAbiStability("UNSTABLE", 1);

    private static final /* synthetic */ DeserializedContainerAbiStability[] $values() {
        return new DeserializedContainerAbiStability[]{STABLE, UNSTABLE};
    }

    public static DeserializedContainerAbiStability valueOf(String str) {
        return (DeserializedContainerAbiStability) Enum.valueOf(DeserializedContainerAbiStability.class, str);
    }

    public static DeserializedContainerAbiStability[] values() {
        return (DeserializedContainerAbiStability[]) $VALUES.clone();
    }

    private DeserializedContainerAbiStability(String str, int i) {
    }

    static {
        DeserializedContainerAbiStability[] deserializedContainerAbiStabilityArr$values = $values();
        $VALUES = deserializedContainerAbiStabilityArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(deserializedContainerAbiStabilityArr$values);
    }
}
