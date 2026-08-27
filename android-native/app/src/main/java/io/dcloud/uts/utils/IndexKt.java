package io.dcloud.uts.utils;

import io.dcloud.uts.NumberKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0004\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0001¨\u0006\u0005"}, d2 = {"toSliceIndex", "", "value", "", "length", "utsplugin_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class IndexKt {
    public static final int toSliceIndex(Number value, int i) {
        Intrinsics.checkNotNullParameter(value, "value");
        if (NumberKt.compareTo(value, (Number) 0) >= 0) {
            return NumberKt.compareTo(value, Integer.valueOf(i)) > 0 ? i : value.intValue();
        }
        if (NumberKt.compareTo(NumberKt.plus(value, Integer.valueOf(i)), (Number) 0) < 0) {
            return 0;
        }
        return NumberKt.plus(value, Integer.valueOf(i)).intValue();
    }
}
