package kotlin.reflect.jvm.internal.impl.builtins;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: UnsignedType.kt */
/* loaded from: classes2.dex */
public final class UnsignedType {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ UnsignedType[] $VALUES;
    private final ClassId arrayClassId;
    private final ClassId classId;
    private final Name typeName;
    public static final UnsignedType UBYTE = new UnsignedType("UBYTE", 0, ClassId.Companion.fromString$default(ClassId.Companion, "kotlin/UByte", false, 2, null));
    public static final UnsignedType USHORT = new UnsignedType("USHORT", 1, ClassId.Companion.fromString$default(ClassId.Companion, "kotlin/UShort", false, 2, null));
    public static final UnsignedType UINT = new UnsignedType("UINT", 2, ClassId.Companion.fromString$default(ClassId.Companion, "kotlin/UInt", false, 2, null));
    public static final UnsignedType ULONG = new UnsignedType("ULONG", 3, ClassId.Companion.fromString$default(ClassId.Companion, "kotlin/ULong", false, 2, null));

    private static final /* synthetic */ UnsignedType[] $values() {
        return new UnsignedType[]{UBYTE, USHORT, UINT, ULONG};
    }

    public static UnsignedType valueOf(String str) {
        return (UnsignedType) Enum.valueOf(UnsignedType.class, str);
    }

    public static UnsignedType[] values() {
        return (UnsignedType[]) $VALUES.clone();
    }

    private UnsignedType(String str, int i, ClassId classId) {
        this.classId = classId;
        Name shortClassName = classId.getShortClassName();
        this.typeName = shortClassName;
        FqName packageFqName = classId.getPackageFqName();
        Name nameIdentifier = Name.identifier(shortClassName.asString() + "Array");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        this.arrayClassId = new ClassId(packageFqName, nameIdentifier);
    }

    public final ClassId getClassId() {
        return this.classId;
    }

    static {
        UnsignedType[] unsignedTypeArr$values = $values();
        $VALUES = unsignedTypeArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(unsignedTypeArr$values);
    }

    public final Name getTypeName() {
        return this.typeName;
    }

    public final ClassId getArrayClassId() {
        return this.arrayClassId;
    }
}
