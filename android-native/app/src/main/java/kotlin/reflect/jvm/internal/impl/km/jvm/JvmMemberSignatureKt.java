package kotlin.reflect.jvm.internal.impl.km.jvm;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature;

/* compiled from: JvmMemberSignature.kt */
/* loaded from: classes2.dex */
public final class JvmMemberSignatureKt {
    public static final JvmMethodSignature wrapAsPublic(JvmMemberSignature.Method method) {
        Intrinsics.checkNotNullParameter(method, "<this>");
        return new JvmMethodSignature(method.getName(), method.getDesc());
    }

    public static final JvmFieldSignature wrapAsPublic(JvmMemberSignature.Field field) {
        Intrinsics.checkNotNullParameter(field, "<this>");
        return new JvmFieldSignature(field.getName(), field.getDesc());
    }
}
