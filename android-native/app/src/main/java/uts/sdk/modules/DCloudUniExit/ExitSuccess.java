package uts.sdk.modules.DCloudUniExit;

import com.taobao.weex.adapter.IWXUserTrackAdapter;
import io.dcloud.uts.JsonNotNull;
import io.dcloud.uts.UTSObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005R\u001e\u0010\u0002\u001a\u00020\u00038\u0016@\u0016X\u0097\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\u0005¨\u0006\t"}, d2 = {"Luts/sdk/modules/DCloudUniExit/ExitSuccess;", "Lio/dcloud/uts/UTSObject;", IWXUserTrackAdapter.MONITOR_ERROR_MSG, "", "<init>", "(Ljava/lang/String;)V", "getErrMsg", "()Ljava/lang/String;", "setErrMsg", "uni-exit_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class ExitSuccess extends UTSObject {

    @JsonNotNull
    private String errMsg;

    public String getErrMsg() {
        return this.errMsg;
    }

    public void setErrMsg(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.errMsg = str;
    }

    public ExitSuccess(String errMsg) {
        Intrinsics.checkNotNullParameter(errMsg, "errMsg");
        this.errMsg = errMsg;
    }
}
