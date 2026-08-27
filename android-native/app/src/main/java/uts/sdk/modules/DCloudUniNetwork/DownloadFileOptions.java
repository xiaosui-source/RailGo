package uts.sdk.modules.DCloudUniNetwork;

import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.component.WXBasicComponentType;
import com.taobao.weex.ui.component.WXImage;
import io.dcloud.uts.JsonNotNull;
import io.dcloud.uts.UTSJSONObject;
import io.dcloud.uts.UTSObject;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0004\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0019\b\u0016\u0018\u00002\u00020\u0001BÍ\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012+\b\u0002\u0010\t\u001a%\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\nj\u0004\u0018\u0001`\u0010\u0012+\b\u0002\u0010\u0011\u001a%\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\nj\u0004\u0018\u0001`\u0013\u0012>\b\u0002\u0010\u0014\u001a8\u0012&\u0012$0\u0015j\u0011`\u0016¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\nj\u0004\u0018\u0001`\u0017¢\u0006\u0004\b\u0018\u0010\u0019R\u001e\u0010\u0002\u001a\u00020\u00038\u0016@\u0016X\u0097\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u001b\"\u0004\b#\u0010\u001dR\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R=\u0010\t\u001a%\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\nj\u0004\u0018\u0001`\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R=\u0010\u0011\u001a%\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\nj\u0004\u0018\u0001`\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010)\"\u0004\b-\u0010+RP\u0010\u0014\u001a8\u0012&\u0012$0\u0015j\u0011`\u0016¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\nj\u0004\u0018\u0001`\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010)\"\u0004\b/\u0010+¨\u00060"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/DownloadFileOptions;", "Lio/dcloud/uts/UTSObject;", "url", "", WXBasicComponentType.HEADER, "Lio/dcloud/uts/UTSJSONObject;", "filePath", "timeout", "", WXImage.SUCCEED, "Lkotlin/Function1;", "Luts/sdk/modules/DCloudUniNetwork/DownloadFileSuccess;", "Lkotlin/ParameterName;", "name", "result", "", "Luts/sdk/modules/DCloudUniNetwork/DownloadFileSuccessCallback;", Constants.Event.FAIL, "Luts/sdk/modules/DCloudUniNetwork/DownloadFileFail;", "Luts/sdk/modules/DCloudUniNetwork/DownloadFileFailCallback;", "complete", "", "Luts/sdk/modules/DCloudUniNetwork/DownloadFileComplete;", "Luts/sdk/modules/DCloudUniNetwork/DownloadFileCompleteCallback;", "<init>", "(Ljava/lang/String;Lio/dcloud/uts/UTSJSONObject;Ljava/lang/String;Ljava/lang/Number;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "getUrl", "()Ljava/lang/String;", "setUrl", "(Ljava/lang/String;)V", "getHeader", "()Lio/dcloud/uts/UTSJSONObject;", "setHeader", "(Lio/dcloud/uts/UTSJSONObject;)V", "getFilePath", "setFilePath", "getTimeout", "()Ljava/lang/Number;", "setTimeout", "(Ljava/lang/Number;)V", "getSuccess", "()Lkotlin/jvm/functions/Function1;", "setSuccess", "(Lkotlin/jvm/functions/Function1;)V", "getFail", "setFail", "getComplete", "setComplete", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class DownloadFileOptions extends UTSObject {
    private Function1<Object, Unit> complete;
    private Function1<? super DownloadFileFail, Unit> fail;
    private String filePath;
    private UTSJSONObject header;
    private Function1<? super DownloadFileSuccess, Unit> success;
    private Number timeout;

    @JsonNotNull
    private String url;

    public /* synthetic */ DownloadFileOptions(String str, UTSJSONObject uTSJSONObject, String str2, Number number, Function1 function1, Function1 function12, Function1 function13, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : uTSJSONObject, (i & 4) != 0 ? null : str2, (i & 8) != 0 ? null : number, (i & 16) != 0 ? null : function1, (i & 32) != 0 ? null : function12, (i & 64) != 0 ? null : function13);
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.url = str;
    }

    public UTSJSONObject getHeader() {
        return this.header;
    }

    public void setHeader(UTSJSONObject uTSJSONObject) {
        this.header = uTSJSONObject;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String str) {
        this.filePath = str;
    }

    public Number getTimeout() {
        return this.timeout;
    }

    public void setTimeout(Number number) {
        this.timeout = number;
    }

    public Function1<DownloadFileSuccess, Unit> getSuccess() {
        return this.success;
    }

    public void setSuccess(Function1<? super DownloadFileSuccess, Unit> function1) {
        this.success = function1;
    }

    public Function1<DownloadFileFail, Unit> getFail() {
        return this.fail;
    }

    public void setFail(Function1<? super DownloadFileFail, Unit> function1) {
        this.fail = function1;
    }

    public Function1<Object, Unit> getComplete() {
        return this.complete;
    }

    public void setComplete(Function1<Object, Unit> function1) {
        this.complete = function1;
    }

    public DownloadFileOptions(String url, UTSJSONObject uTSJSONObject, String str, Number number, Function1<? super DownloadFileSuccess, Unit> function1, Function1<? super DownloadFileFail, Unit> function12, Function1<Object, Unit> function13) {
        Intrinsics.checkNotNullParameter(url, "url");
        this.url = url;
        this.header = uTSJSONObject;
        this.filePath = str;
        this.timeout = number;
        this.success = function1;
        this.fail = function12;
        this.complete = function13;
    }
}
