package io.dcloud.uts;

import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Object.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001f\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u0007\"\u00020\b¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Lio/dcloud/uts/Object;", "", "<init>", "()V", "assign", "Lio/dcloud/uts/UTSJSONObject;", "arguments", "", "Lio/dcloud/uts/IUTSObject;", "([Lio/dcloud/uts/IUTSObject;)Lio/dcloud/uts/UTSJSONObject;", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class Object {
    public static final Object INSTANCE = new Object();

    private Object() {
    }

    public final UTSJSONObject assign(IUTSObject... arguments) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        UTSJSONObject uTSJSONObject = new UTSJSONObject();
        ArrayList arrayList = new ArrayList(arguments.length);
        for (IUTSObject iUTSObject : arguments) {
            uTSJSONObject.mergeOther(iUTSObject);
            arrayList.add(Unit.INSTANCE);
        }
        return uTSJSONObject;
    }
}
