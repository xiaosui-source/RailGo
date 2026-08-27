package uts.sdk.modules.DCloudUniNetwork;

import io.dcloud.uts.JsonNotNull;
import io.dcloud.uts.UTSObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0004\n\u0002\b\u000b\b\u0016\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007R\u001e\u0010\u0002\u001a\u00020\u00038\u0016@\u0016X\u0097\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001e\u0010\u0004\u001a\u00020\u00058\u0016@\u0016X\u0097\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/UploadFileSuccess;", "Lio/dcloud/uts/UTSObject;", "data", "", "statusCode", "", "<init>", "(Ljava/lang/String;Ljava/lang/Number;)V", "getData", "()Ljava/lang/String;", "setData", "(Ljava/lang/String;)V", "getStatusCode", "()Ljava/lang/Number;", "setStatusCode", "(Ljava/lang/Number;)V", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class UploadFileSuccess extends UTSObject {

    @JsonNotNull
    private String data;

    @JsonNotNull
    private Number statusCode;

    public String getData() {
        return this.data;
    }

    public void setData(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.data = str;
    }

    public Number getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(Number number) {
        Intrinsics.checkNotNullParameter(number, "<set-?>");
        this.statusCode = number;
    }

    public UploadFileSuccess(String data, Number statusCode) {
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(statusCode, "statusCode");
        this.data = data;
        this.statusCode = statusCode;
    }
}
