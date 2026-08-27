package io.dcloud.uts;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;

/* compiled from: Map.kt */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u001aO\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032*\u0010\u0004\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00060\u0005\"\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0006¢\u0006\u0002\u0010\u0007\u001aO\u0010\b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032*\u0010\u0004\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00060\u0005\"\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0006¢\u0006\u0002\u0010\u0007\u001aH\u0010\t\u001a\u0002H\n\"\u0004\b\u0000\u0010\u0003\"\b\b\u0001\u0010\n*\u0002H\u0003*\u0012\u0012\u0006\b\u0000\u0012\u00020\u000b\u0012\u0006\b\u0001\u0012\u0002H\u00030\u00012\b\u0010\f\u001a\u0004\u0018\u00010\r2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000fH\u0086\n¢\u0006\u0002\u0010\u0010\u001aF\u0010\u0011\u001a\u00020\u0012\"\u0004\b\u0000\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u00020\u000b\u0012\u0006\b\u0000\u0012\u0002H\u00030\u00012\b\u0010\f\u001a\u0004\u0018\u00010\r2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000f2\u0006\u0010\u0013\u001a\u0002H\u0003H\u0086\n¢\u0006\u0002\u0010\u0014¨\u0006\u0015"}, d2 = {"_uM", "Lio/dcloud/uts/Map;", "K", "V", "pairs", "", "Lkotlin/Pair;", "([Lkotlin/Pair;)Lio/dcloud/uts/Map;", "utsMapOf", "getValue", "V1", "", "thisRef", "", "property", "Lkotlin/reflect/KProperty;", "(Lio/dcloud/uts/Map;Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "setValue", "", "value", "(Lio/dcloud/uts/Map;Ljava/lang/Object;Lkotlin/reflect/KProperty;Ljava/lang/Object;)V", "utsplugin_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class MapKt {
    public static final <K, V> Map<K, V> _uM(Pair<? extends K, ? extends V>... pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        Map<K, V> map = new Map<>(pairs.length);
        MapsKt.putAll(map, pairs);
        return map;
    }

    public static final <K, V> Map<K, V> utsMapOf(Pair<? extends K, ? extends V>... pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        Map<K, V> map = new Map<>(pairs.length);
        MapsKt.putAll(map, pairs);
        return map;
    }

    public static final <V, V1 extends V> V1 getValue(Map<? super String, ? extends V> map, java.lang.Object obj, KProperty<?> property) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        Intrinsics.checkNotNullParameter(property, "property");
        return map.get(property.getName());
    }

    public static final <V> void setValue(Map<? super String, ? super V> map, java.lang.Object obj, KProperty<?> property, V v) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        Intrinsics.checkNotNullParameter(property, "property");
        map.put(property.getName(), v);
    }
}
