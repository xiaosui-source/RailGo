package kotlin.reflect.jvm.internal.impl.km;

import com.taobao.weex.el.parse.Operators;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: ClassName.kt */
/* loaded from: classes2.dex */
public final class ClassNameKt {
    public static final boolean isLocalClassName(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return StringsKt.startsWith$default(str, Operators.DOT_STR, false, 2, (Object) null);
    }
}
