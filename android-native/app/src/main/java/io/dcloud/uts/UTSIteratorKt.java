package io.dcloud.uts;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UTSIterator.kt */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a#\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\b\b\u0000\u0010\u0003*\u00020\u00042\u0006\u0010\u0005\u001a\u0002H\u0003¢\u0006\u0002\u0010\u0006\u001a\u0019\u0010\u0000\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u00032\u0006\u0010\u0005\u001a\u0002H\u0003¢\u0006\u0002\u0010\u0007\u001a/\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\t0\u0001\"\u0004\b\u0000\u0010\t\"\u000e\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\t0\n2\u0006\u0010\u0005\u001a\u0002H\u0003¢\u0006\u0002\u0010\u000b\u001a\u0019\u0010\b\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u00032\u0006\u0010\u0005\u001a\u0002H\u0003¢\u0006\u0002\u0010\u0007\u001a\u001d\u0010\b\u001a\u00020\f\"\b\b\u0000\u0010\u0003*\u00020\r2\u0006\u0010\u0005\u001a\u0002H\u0003¢\u0006\u0002\u0010\u000e¨\u0006\u000f"}, d2 = {"resolveUTSKeyIterator", "Lio/dcloud/uts/UTSIterator;", "", "T", "Lio/dcloud/uts/UTSKeyIterable;", "iterator", "(Lio/dcloud/uts/UTSKeyIterable;)Lio/dcloud/uts/UTSIterator;", "(Ljava/lang/Object;)Ljava/lang/Object;", "resolveUTSValueIterator", "R", "Lio/dcloud/uts/UTSValueIterable;", "(Lio/dcloud/uts/UTSValueIterable;)Lio/dcloud/uts/UTSIterator;", "", "Lio/dcloud/uts/IUTSObject;", "(Lio/dcloud/uts/IUTSObject;)Ljava/lang/Object;", "utsplugin_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UTSIteratorKt {
    public static final <T> T resolveUTSKeyIterator(T t) {
        return t;
    }

    public static final <T extends IUTSObject> java.lang.Object resolveUTSValueIterator(T iterator) {
        Intrinsics.checkNotNullParameter(iterator, "iterator");
        return iterator;
    }

    public static final <T> T resolveUTSValueIterator(T t) {
        return t;
    }

    public static final <T extends UTSKeyIterable> UTSIterator<String> resolveUTSKeyIterator(T iterator) {
        Intrinsics.checkNotNullParameter(iterator, "iterator");
        return iterator.keyIterator();
    }

    public static final <R, T extends UTSValueIterable<R>> UTSIterator<R> resolveUTSValueIterator(T iterator) {
        Intrinsics.checkNotNullParameter(iterator, "iterator");
        return iterator.valueIterator();
    }
}
