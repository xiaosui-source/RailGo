package io.dcloud.uts;

import io.dcloud.common.DHInterface.IApp;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UTSObject.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a!\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006\u001a)\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007¢\u0006\u0004\b\u0007\u0010\u0006\u001a\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u0005*\n\u0010\f\"\u00020\u00032\u00020\u0003¨\u0006\r"}, d2 = {"resolveUTSObjectProperty", "T", "obj", "Lio/dcloud/uts/UTSObject;", IApp.ConfigProperty.CONFIG_KEY, "", "(Lio/dcloud/uts/UTSObject;Ljava/lang/String;)Ljava/lang/Object;", "resolveUTSObjectNullableProperty", "resolveInOperator", "", "Lio/dcloud/uts/UTSJSONObject;", "Lio/dcloud/uts/UTSKeyIterable;", "UTSUnionTypeObject", "utsplugin_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UTSObjectKt {
    public static final <T> T resolveUTSObjectProperty(UTSObject obj, String key) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(key, "key");
        return (T) obj.get(key);
    }

    public static final <T> T resolveUTSObjectNullableProperty(UTSObject uTSObject, String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        if (uTSObject == null) {
            return null;
        }
        return (T) uTSObject.get(key);
    }

    public static final boolean resolveInOperator(UTSObject obj, String key) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(key, "key");
        return UTSObject.INSTANCE.hasOwn(obj, key);
    }

    public static final boolean resolveInOperator(UTSJSONObject obj, String key) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(key, "key");
        return UTSJSONObject.INSTANCE.keys(obj).includes(key);
    }

    public static final boolean resolveInOperator(UTSKeyIterable obj, String key) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(key, "key");
        UTSIterator<String> uTSIteratorKeyIterator = obj.keyIterator();
        while (uTSIteratorKeyIterator.hasNext()) {
            if (Intrinsics.areEqual(uTSIteratorKeyIterator.next(), key)) {
                return true;
            }
        }
        return false;
    }
}
