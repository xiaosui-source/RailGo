package uts.sdk.modules.DCloudUniNetwork;

import com.taobao.weex.adapter.IWXUserTrackAdapter;
import io.dcloud.uniapp.UniError;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\b\u0016\u0012\n\u0010\u0003\u001a\u00060\u0004j\u0002`\u0005¢\u0006\u0004\b\u0006\u0010\u0007R\u001e\u0010\u0003\u001a\u00060\u0004j\u0002`\u0005X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u0007¨\u0006\u000b"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/UploadFileFailImpl;", "Lio/dcloud/uniapp/UniError;", "Luts/sdk/modules/DCloudUniNetwork/UploadFileFail;", IWXUserTrackAdapter.MONITOR_ERROR_CODE, "", "Luts/sdk/modules/DCloudUniNetwork/RequestErrorCode;", "<init>", "(Ljava/lang/Number;)V", "getErrCode", "()Ljava/lang/Number;", "setErrCode", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class UploadFileFailImpl extends UniError implements UploadFileFail {
    public Number errCode;

    @Override // io.dcloud.uniapp.UniError, io.dcloud.uniapp.IUniError
    public Number getErrCode() {
        Number number = this.errCode;
        if (number != null) {
            return number;
        }
        Intrinsics.throwUninitializedPropertyAccessException(IWXUserTrackAdapter.MONITOR_ERROR_CODE);
        return null;
    }

    @Override // io.dcloud.uniapp.UniError, io.dcloud.uniapp.IUniError
    public void setErrCode(Number number) {
        Intrinsics.checkNotNullParameter(number, "<set-?>");
        this.errCode = number;
    }

    public UploadFileFailImpl(Number errCode) {
        Intrinsics.checkNotNullParameter(errCode, "errCode");
        setErrSubject("uni-uploadFile");
        setErrCode(errCode);
        String str = IndexKt.getNetWorkUniErrors().get(errCode);
        setErrMsg(str == null ? "" : str);
    }
}
