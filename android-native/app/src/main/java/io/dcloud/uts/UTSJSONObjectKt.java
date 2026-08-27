package io.dcloud.uts;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UTSJSONObject.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u001a;\u0010\u0000\u001a\u00020\u00012.\u0010\u0002\u001a\u0018\u0012\u0014\b\u0001\u0012\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00040\u0003\"\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004¢\u0006\u0002\u0010\u0006\u001a;\u0010\u0007\u001a\u00020\u00012.\u0010\u0002\u001a\u0018\u0012\u0014\b\u0001\u0012\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00040\u0003\"\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004¢\u0006\u0002\u0010\u0006¨\u0006\b"}, d2 = {"_uO", "Lio/dcloud/uts/UTSJSONObject;", "pairs", "", "Lkotlin/Pair;", "", "([Lkotlin/Pair;)Lio/dcloud/uts/UTSJSONObject;", "utsObjOf", "utsplugin_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UTSJSONObjectKt {
    public static final UTSJSONObject _uO(Pair<? extends java.lang.Object, ? extends java.lang.Object>... pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        UTSJSONObject uTSJSONObject = new UTSJSONObject();
        int length = pairs.length;
        for (int i = 0; i < length; i++) {
            Pair<? extends java.lang.Object, ? extends java.lang.Object> pair = pairs[i];
            java.lang.Object objComponent1 = pair.component1();
            java.lang.Object objComponent2 = pair.component2();
            if (i == 0 && Intrinsics.areEqual(objComponent1, "__$originalPosition")) {
                uTSJSONObject.set__$hostSourceMap(objComponent2 instanceof UTSSourceMapPosition ? (UTSSourceMapPosition) objComponent2 : null);
            } else {
                uTSJSONObject.set(objComponent1, objComponent2);
            }
        }
        return uTSJSONObject;
    }

    public static final UTSJSONObject utsObjOf(Pair<? extends java.lang.Object, ? extends java.lang.Object>... pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        UTSJSONObject uTSJSONObject = new UTSJSONObject();
        int length = pairs.length;
        for (int i = 0; i < length; i++) {
            Pair<? extends java.lang.Object, ? extends java.lang.Object> pair = pairs[i];
            java.lang.Object objComponent1 = pair.component1();
            java.lang.Object objComponent2 = pair.component2();
            if (i == 0 && Intrinsics.areEqual(objComponent1, "__$originalPosition")) {
                uTSJSONObject.set__$hostSourceMap(objComponent2 instanceof UTSSourceMapPosition ? (UTSSourceMapPosition) objComponent2 : null);
            } else {
                uTSJSONObject.set(objComponent1, objComponent2);
            }
        }
        return uTSJSONObject;
    }
}
