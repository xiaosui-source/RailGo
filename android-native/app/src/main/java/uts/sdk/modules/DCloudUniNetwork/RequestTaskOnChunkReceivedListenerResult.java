package uts.sdk.modules.DCloudUniNetwork;

import io.dcloud.uts.ArrayBuffer;
import io.dcloud.uts.JsonNotNull;
import io.dcloud.uts.UTSObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005R\u001e\u0010\u0002\u001a\u00020\u00038\u0016@\u0016X\u0097\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\u0005¨\u0006\t"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/RequestTaskOnChunkReceivedListenerResult;", "Lio/dcloud/uts/UTSObject;", "data", "Lio/dcloud/uts/ArrayBuffer;", "<init>", "(Lio/dcloud/uts/ArrayBuffer;)V", "getData", "()Lio/dcloud/uts/ArrayBuffer;", "setData", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class RequestTaskOnChunkReceivedListenerResult extends UTSObject {

    @JsonNotNull
    private ArrayBuffer data;

    public ArrayBuffer getData() {
        return this.data;
    }

    public void setData(ArrayBuffer arrayBuffer) {
        Intrinsics.checkNotNullParameter(arrayBuffer, "<set-?>");
        this.data = arrayBuffer;
    }

    public RequestTaskOnChunkReceivedListenerResult(ArrayBuffer data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.data = data;
    }
}
