package uts.sdk.modules.DCloudUniGetDeviceInfo;

import com.taobao.weex.common.Constants;
import io.dcloud.uts.JsonNotNull;
import io.dcloud.uts.UTSArray;
import io.dcloud.uts.UTSObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0004\b\u0005\u0010\u0006R$\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038\u0016@\u0016X\u0097\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0006¨\u0006\n"}, d2 = {"Luts/sdk/modules/DCloudUniGetDeviceInfo/GetDeviceInfoOptions;", "Lio/dcloud/uts/UTSObject;", Constants.Name.FILTER, "Lio/dcloud/uts/UTSArray;", "", "<init>", "(Lio/dcloud/uts/UTSArray;)V", "getFilter", "()Lio/dcloud/uts/UTSArray;", "setFilter", "uni-getDeviceInfo_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class GetDeviceInfoOptions extends UTSObject {

    @JsonNotNull
    private UTSArray<String> filter;

    public UTSArray<String> getFilter() {
        return this.filter;
    }

    public void setFilter(UTSArray<String> uTSArray) {
        Intrinsics.checkNotNullParameter(uTSArray, "<set-?>");
        this.filter = uTSArray;
    }

    public GetDeviceInfoOptions(UTSArray<String> filter) {
        Intrinsics.checkNotNullParameter(filter, "filter");
        this.filter = filter;
    }
}
