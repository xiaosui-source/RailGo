package uts.sdk.modules.DCloudUniNetwork;

import io.dcloud.uts.JsonNotNull;
import io.dcloud.uts.UTSObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0004\n\u0002\b\r\b\u0016\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0004\b\u0006\u0010\u0007R\u001e\u0010\u0002\u001a\u00020\u00038\u0016@\u0016X\u0097\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001e\u0010\u0004\u001a\u00020\u00038\u0016@\u0016X\u0097\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR\u001e\u0010\u0005\u001a\u00020\u00038\u0016@\u0016X\u0097\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\t\"\u0004\b\u000f\u0010\u000b¨\u0006\u0010"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/OnProgressUpdateResult;", "Lio/dcloud/uts/UTSObject;", "progress", "", "totalBytesSent", "totalBytesExpectedToSend", "<init>", "(Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;)V", "getProgress", "()Ljava/lang/Number;", "setProgress", "(Ljava/lang/Number;)V", "getTotalBytesSent", "setTotalBytesSent", "getTotalBytesExpectedToSend", "setTotalBytesExpectedToSend", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class OnProgressUpdateResult extends UTSObject {

    @JsonNotNull
    private Number progress;

    @JsonNotNull
    private Number totalBytesExpectedToSend;

    @JsonNotNull
    private Number totalBytesSent;

    public Number getProgress() {
        return this.progress;
    }

    public void setProgress(Number number) {
        Intrinsics.checkNotNullParameter(number, "<set-?>");
        this.progress = number;
    }

    public Number getTotalBytesSent() {
        return this.totalBytesSent;
    }

    public void setTotalBytesSent(Number number) {
        Intrinsics.checkNotNullParameter(number, "<set-?>");
        this.totalBytesSent = number;
    }

    public Number getTotalBytesExpectedToSend() {
        return this.totalBytesExpectedToSend;
    }

    public void setTotalBytesExpectedToSend(Number number) {
        Intrinsics.checkNotNullParameter(number, "<set-?>");
        this.totalBytesExpectedToSend = number;
    }

    public OnProgressUpdateResult(Number progress, Number totalBytesSent, Number totalBytesExpectedToSend) {
        Intrinsics.checkNotNullParameter(progress, "progress");
        Intrinsics.checkNotNullParameter(totalBytesSent, "totalBytesSent");
        Intrinsics.checkNotNullParameter(totalBytesExpectedToSend, "totalBytesExpectedToSend");
        this.progress = progress;
        this.totalBytesSent = totalBytesSent;
        this.totalBytesExpectedToSend = totalBytesExpectedToSend;
    }
}
