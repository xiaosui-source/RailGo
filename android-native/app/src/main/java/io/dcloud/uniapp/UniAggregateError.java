package io.dcloud.uniapp;

import io.dcloud.uts.UTSArray;
import io.dcloud.uts.UTSError;
import io.dcloud.uts.UTSJSONObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UniError.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003B\u0017\b\u0016\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0004\b\u0002\u0010\u0007B\u001f\b\u0016\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0004\b\u0002\u0010\nB'\b\u0016\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0004\b\u0002\u0010\rR\"\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0007¨\u0006\u0011"}, d2 = {"Lio/dcloud/uniapp/UniAggregateError;", "Lio/dcloud/uniapp/SourceError;", "<init>", "()V", "errors", "Lio/dcloud/uts/UTSArray;", "Lio/dcloud/uts/UTSError;", "(Lio/dcloud/uts/UTSArray;)V", "message", "", "(Lio/dcloud/uts/UTSArray;Ljava/lang/String;)V", "options", "Lio/dcloud/uts/UTSJSONObject;", "(Lio/dcloud/uts/UTSArray;Ljava/lang/String;Lio/dcloud/uts/UTSJSONObject;)V", "getErrors", "()Lio/dcloud/uts/UTSArray;", "setErrors", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UniAggregateError extends SourceError {
    private UTSArray<UTSError> errors;

    public UniAggregateError() {
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public UniAggregateError(UTSArray<UTSError> errors) {
        this();
        Intrinsics.checkNotNullParameter(errors, "errors");
        this.errors = errors;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public UniAggregateError(UTSArray<UTSError> errors, String message) {
        this();
        Intrinsics.checkNotNullParameter(errors, "errors");
        Intrinsics.checkNotNullParameter(message, "message");
        this.errors = errors;
        setMessage(message);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public UniAggregateError(UTSArray<UTSError> errors, String message, UTSJSONObject options) {
        this();
        Intrinsics.checkNotNullParameter(errors, "errors");
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(options, "options");
        this.errors = errors;
        setMessage(message);
        Object obj = options.get("cause");
        if (obj == null || !(obj instanceof UTSError)) {
            return;
        }
        setCause((UTSError) obj);
    }

    public final UTSArray<UTSError> getErrors() {
        return this.errors;
    }

    public final void setErrors(UTSArray<UTSError> uTSArray) {
        this.errors = uTSArray;
    }
}
