package io.dcloud.uts;

import io.dcloud.uts.gson.ExclusionStrategy;
import io.dcloud.uts.gson.FieldAttributes;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UTSJSONObject.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0016\u0010\b\u001a\u00020\u00052\f\u0010\t\u001a\b\u0012\u0002\b\u0003\u0018\u00010\nH\u0016¨\u0006\u000b"}, d2 = {"Lio/dcloud/uts/DynamicMapStrategy;", "Lio/dcloud/uts/gson/ExclusionStrategy;", "<init>", "()V", "shouldSkipField", "", "f", "Lio/dcloud/uts/gson/FieldAttributes;", "shouldSkipClass", "clazz", "Ljava/lang/Class;", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class DynamicMapStrategy implements ExclusionStrategy {
    @Override // io.dcloud.uts.gson.ExclusionStrategy
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }

    @Override // io.dcloud.uts.gson.ExclusionStrategy
    public boolean shouldSkipField(FieldAttributes f) {
        Intrinsics.checkNotNullParameter(f, "f");
        return f.getAnnotation(GsonTransparent.class) != null;
    }
}
