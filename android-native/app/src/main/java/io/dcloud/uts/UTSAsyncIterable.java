package io.dcloud.uts;

import kotlin.Metadata;

/* compiled from: UTSIterator.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00040\u0003H&¨\u0006\u0005À\u0006\u0003"}, d2 = {"Lio/dcloud/uts/UTSAsyncIterable;", "", "asyncIterator", "Lio/dcloud/uts/UTSIterator;", "Lio/dcloud/uts/UTSPromise;", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface UTSAsyncIterable {
    UTSIterator<UTSPromise<java.lang.Object>> asyncIterator();
}
