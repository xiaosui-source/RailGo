package io.dcloud.uts;

import io.dcloud.uts.json.IJsonStringify;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UTSError.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0016\u0018\u00002\u00060\u0001j\u0002`\u00022\u00020\u0003B\u0007¢\u0006\u0004\b\u0004\u0010\u0005B\u0011\b\u0016\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\u0004\u0010\bB\u0019\b\u0016\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0004\b\u0004\u0010\u000bJ\n\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0007H\u0016R\u001a\u0010\f\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\bR\u001a\u0010\u0006\u001a\u00020\u0007X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u000e\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0000X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016¨\u0006\u001a"}, d2 = {"Lio/dcloud/uts/UTSError;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "Lio/dcloud/uts/json/IJsonStringify;", "<init>", "()V", "message", "", "(Ljava/lang/String;)V", "options", "Lio/dcloud/uts/UTSJSONObject;", "(Ljava/lang/String;Lio/dcloud/uts/UTSJSONObject;)V", "name", "getName", "()Ljava/lang/String;", "setName", "getMessage", "setMessage", "cause", "getCause", "()Lio/dcloud/uts/UTSError;", "setCause", "(Lio/dcloud/uts/UTSError;)V", "toJSON", "", "toString", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class UTSError extends Exception implements IJsonStringify {
    private UTSError cause;
    private String message;
    private String name;

    public UTSError() {
        this.name = "Error";
        this.message = "";
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public UTSError(String message) {
        this();
        Intrinsics.checkNotNullParameter(message, "message");
        setMessage(message);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public UTSError(String message, UTSJSONObject options) {
        this();
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(options, "options");
        setMessage(message);
        java.lang.Object obj = options.get("cause");
        if (obj == null || !(obj instanceof UTSError)) {
            return;
        }
        setCause((UTSError) obj);
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.name = str;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.message = str;
    }

    @Override // java.lang.Throwable
    public UTSError getCause() {
        return this.cause;
    }

    public void setCause(UTSError uTSError) {
        this.cause = uTSError;
    }

    public java.lang.Object toJSON() {
        UTSJSONObject uTSJSONObject = new UTSJSONObject();
        uTSJSONObject.set("name", this.name);
        uTSJSONObject.set("message", getMessage());
        if (getCause() != null) {
            if (getCause() instanceof IJsonStringify) {
                UTSError cause = getCause();
                Intrinsics.checkNotNull(cause, "null cannot be cast to non-null type io.dcloud.uts.json.IJsonStringify");
                uTSJSONObject.set("cause", cause.toJSON());
                return uTSJSONObject;
            }
            uTSJSONObject.set("cause", NumberKt.toString_number_nullable$default(getCause(), (Number) null, 1, (java.lang.Object) null));
        }
        return uTSJSONObject;
    }

    @Override // java.lang.Throwable
    public String toString() {
        return "UTSError(name='" + this.name + "', message='" + getMessage() + "', cause='" + getCause() + "')";
    }
}
