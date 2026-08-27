package uts.sdk.modules.DCloudUniNetwork;

import com.taobao.weex.adapter.IWXUserTrackAdapter;
import io.dcloud.uniapp.IUniError;
import kotlin.Metadata;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001R\u001c\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004X¦\u000e¢\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\tÀ\u0006\u0003"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/UploadFileFail;", "Lio/dcloud/uniapp/IUniError;", IWXUserTrackAdapter.MONITOR_ERROR_CODE, "", "Luts/sdk/modules/DCloudUniNetwork/RequestErrorCode;", "getErrCode", "()Ljava/lang/Number;", "setErrCode", "(Ljava/lang/Number;)V", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface UploadFileFail extends IUniError {
    @Override // io.dcloud.uniapp.IUniError
    Number getErrCode();

    @Override // io.dcloud.uniapp.IUniError
    void setErrCode(Number number);
}
