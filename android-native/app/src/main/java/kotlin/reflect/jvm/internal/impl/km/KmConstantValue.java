package kotlin.reflect.jvm.internal.impl.km;

import com.taobao.weex.el.parse.Operators;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Contracts.kt */
/* loaded from: classes2.dex */
public final class KmConstantValue {
    private final Object value;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof KmConstantValue) && Intrinsics.areEqual(this.value, ((KmConstantValue) obj).value);
    }

    public int hashCode() {
        Object obj = this.value;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public String toString() {
        return "KmConstantValue(value=" + this.value + Operators.BRACKET_END;
    }

    public KmConstantValue(Object obj) {
        this.value = obj;
    }
}
