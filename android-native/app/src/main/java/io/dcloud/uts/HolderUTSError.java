package io.dcloud.uts;

import io.dcloud.uts.android.ClassLogWrapper;
import io.dcloud.uts.json.IJsonStringify;
import io.dcloud.uts.log.LogSelfV2Simple;
import java.util.HashSet;
import kotlin.Metadata;

/* compiled from: UTSError.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0011\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\n\u0010\n\u001a\u0004\u0018\u00010\u0005H\u0016J\b\u0010\u000b\u001a\u00020\u0005H\u0016J\b\u0010\f\u001a\u00020\rH\u0016R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u000e"}, d2 = {"Lio/dcloud/uts/HolderUTSError;", "Lio/dcloud/uts/UTSError;", "Lio/dcloud/uts/json/IJsonStringify;", "Lio/dcloud/uts/log/LogSelfV2Simple;", "holder", "", "<init>", "(Ljava/lang/Object;)V", "getHolder", "()Ljava/lang/Object;", "toJSON", "toLogV2Simple", "toString", "", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class HolderUTSError extends UTSError implements IJsonStringify, LogSelfV2Simple {
    private final java.lang.Object holder;

    public HolderUTSError(java.lang.Object obj) {
        this.holder = obj;
    }

    public final java.lang.Object getHolder() {
        return this.holder;
    }

    @Override // io.dcloud.uts.UTSError, io.dcloud.uts.json.IJsonStringify
    public java.lang.Object toJSON() {
        return this.holder;
    }

    @Override // io.dcloud.uts.log.LogSelfV2Simple
    public java.lang.Object toLogV2Simple() {
        return ClassLogWrapper.wrapClass$default(ClassLogWrapper.INSTANCE, this.holder, null, new HashSet(), null, 8, null);
    }

    @Override // io.dcloud.uts.UTSError, java.lang.Throwable
    public String toString() {
        return NumberKt.toString_number_nullable$default(this.holder, (Number) null, 1, (java.lang.Object) null);
    }
}
