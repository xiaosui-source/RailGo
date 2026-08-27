package io.dcloud.uts;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UTSArray.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\u001a+\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0012\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0004\"\u0002H\u0002¢\u0006\u0002\u0010\u0005\u001a\u0015\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002H\u0086\b\u001a+\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0012\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0004\"\u0002H\u0002¢\u0006\u0002\u0010\u0005\u001a\u0015\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002H\u0086\b¨\u0006\u0007"}, d2 = {"_uA", "Lio/dcloud/uts/UTSArray;", "E", "items", "", "([Ljava/lang/Object;)Lio/dcloud/uts/UTSArray;", "utsArrayOf", "utsplugin_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UTSArrayKt {
    public static final <E> UTSArray<E> _uA(E... items) {
        Intrinsics.checkNotNullParameter(items, "items");
        return new UTSArray<>(Arrays.copyOf(items, items.length));
    }

    public static final <E> UTSArray<E> _uA() {
        return new UTSArray<>();
    }

    public static final <E> UTSArray<E> utsArrayOf(E... items) {
        Intrinsics.checkNotNullParameter(items, "items");
        return new UTSArray<>(Arrays.copyOf(items, items.length));
    }

    public static final <E> UTSArray<E> utsArrayOf() {
        return new UTSArray<>();
    }
}
