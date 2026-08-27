package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

/* compiled from: EnumEntriesDeserializationSupport.kt */
/* loaded from: classes2.dex */
public interface EnumEntriesDeserializationSupport {
    Boolean canSynthesizeEnumEntries();

    /* compiled from: EnumEntriesDeserializationSupport.kt */
    public static final class Default implements EnumEntriesDeserializationSupport {
        public static final Default INSTANCE = new Default();

        @Override // kotlin.reflect.jvm.internal.impl.serialization.deserialization.EnumEntriesDeserializationSupport
        public Boolean canSynthesizeEnumEntries() {
            return null;
        }

        private Default() {
        }
    }
}
