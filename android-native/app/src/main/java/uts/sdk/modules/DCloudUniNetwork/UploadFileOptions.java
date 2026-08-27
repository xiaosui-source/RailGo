package uts.sdk.modules.DCloudUniNetwork;

import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.component.WXBasicComponentType;
import com.taobao.weex.ui.component.WXImage;
import io.dcloud.uts.JsonNotNull;
import io.dcloud.uts.UTSArray;
import io.dcloud.uts.UTSJSONObject;
import io.dcloud.uts.UTSObject;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0004\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b!\b\u0016\u0018\u00002\u00020\u0001Bä\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\u0010\b\u0002\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012+\b\u0002\u0010\u000e\u001a%\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0011\u0012\b\b\u0005\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u000fj\u0004\u0018\u0001`\u0014\u0012+\b\u0002\u0010\u0015\u001a%\u0012\u0013\u0012\u00110\u0016¢\u0006\f\b\u0011\u0012\b\b\u0005\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u000fj\u0004\u0018\u0001`\u0017\u0012+\b\u0002\u0010\u0018\u001a%\u0012\u0013\u0012\u00110\u0019¢\u0006\f\b\u0011\u0012\b\b\u0005\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u000fj\u0004\u0018\u0001`\u001a¢\u0006\u0004\b\u001b\u0010\u001cR\u001e\u0010\u0002\u001a\u00020\u00038\u0016@\u0016X\u0097\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u001e\"\u0004\b\"\u0010 R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u001e\"\u0004\b$\u0010 R\"\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010*\"\u0004\b.\u0010,R\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u00100\"\u0004\b1\u00102R=\u0010\u000e\u001a%\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0011\u0012\b\b\u0005\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u000fj\u0004\u0018\u0001`\u0014X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u00104\"\u0004\b5\u00106R=\u0010\u0015\u001a%\u0012\u0013\u0012\u00110\u0016¢\u0006\f\b\u0011\u0012\b\b\u0005\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u000fj\u0004\u0018\u0001`\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u00104\"\u0004\b8\u00106R=\u0010\u0018\u001a%\u0012\u0013\u0012\u00110\u0019¢\u0006\f\b\u0011\u0012\b\b\u0005\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u000fj\u0004\u0018\u0001`\u001aX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u00104\"\u0004\b:\u00106¨\u0006;"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/UploadFileOptions;", "Lio/dcloud/uts/UTSObject;", "url", "", "filePath", "name", "files", "Lio/dcloud/uts/UTSArray;", "Luts/sdk/modules/DCloudUniNetwork/UploadFileOptionFiles;", WXBasicComponentType.HEADER, "Lio/dcloud/uts/UTSJSONObject;", "formData", "timeout", "", WXImage.SUCCEED, "Lkotlin/Function1;", "Luts/sdk/modules/DCloudUniNetwork/UploadFileSuccess;", "Lkotlin/ParameterName;", "result", "", "Luts/sdk/modules/DCloudUniNetwork/UploadFileSuccessCallback;", Constants.Event.FAIL, "Luts/sdk/modules/DCloudUniNetwork/UploadFileFail;", "Luts/sdk/modules/DCloudUniNetwork/UploadFileFailCallback;", "complete", "", "Luts/sdk/modules/DCloudUniNetwork/UploadFileCompleteCallback;", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lio/dcloud/uts/UTSArray;Lio/dcloud/uts/UTSJSONObject;Lio/dcloud/uts/UTSJSONObject;Ljava/lang/Number;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "getUrl", "()Ljava/lang/String;", "setUrl", "(Ljava/lang/String;)V", "getFilePath", "setFilePath", "getName", "setName", "getFiles", "()Lio/dcloud/uts/UTSArray;", "setFiles", "(Lio/dcloud/uts/UTSArray;)V", "getHeader", "()Lio/dcloud/uts/UTSJSONObject;", "setHeader", "(Lio/dcloud/uts/UTSJSONObject;)V", "getFormData", "setFormData", "getTimeout", "()Ljava/lang/Number;", "setTimeout", "(Ljava/lang/Number;)V", "getSuccess", "()Lkotlin/jvm/functions/Function1;", "setSuccess", "(Lkotlin/jvm/functions/Function1;)V", "getFail", "setFail", "getComplete", "setComplete", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class UploadFileOptions extends UTSObject {
    private Function1<Object, Unit> complete;
    private Function1<? super UploadFileFail, Unit> fail;
    private String filePath;
    private UTSArray<UploadFileOptionFiles> files;
    private UTSJSONObject formData;
    private UTSJSONObject header;
    private String name;
    private Function1<? super UploadFileSuccess, Unit> success;
    private Number timeout;

    @JsonNotNull
    private String url;

    public /* synthetic */ UploadFileOptions(String str, String str2, String str3, UTSArray uTSArray, UTSJSONObject uTSJSONObject, UTSJSONObject uTSJSONObject2, Number number, Function1 function1, Function1 function12, Function1 function13, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : uTSArray, (i & 16) != 0 ? null : uTSJSONObject, (i & 32) != 0 ? null : uTSJSONObject2, (i & 64) != 0 ? null : number, (i & 128) != 0 ? null : function1, (i & 256) != 0 ? null : function12, (i & 512) != 0 ? null : function13);
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.url = str;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String str) {
        this.filePath = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public UTSArray<UploadFileOptionFiles> getFiles() {
        return this.files;
    }

    public void setFiles(UTSArray<UploadFileOptionFiles> uTSArray) {
        this.files = uTSArray;
    }

    public UTSJSONObject getHeader() {
        return this.header;
    }

    public void setHeader(UTSJSONObject uTSJSONObject) {
        this.header = uTSJSONObject;
    }

    public UTSJSONObject getFormData() {
        return this.formData;
    }

    public void setFormData(UTSJSONObject uTSJSONObject) {
        this.formData = uTSJSONObject;
    }

    public Number getTimeout() {
        return this.timeout;
    }

    public void setTimeout(Number number) {
        this.timeout = number;
    }

    public Function1<UploadFileSuccess, Unit> getSuccess() {
        return this.success;
    }

    public void setSuccess(Function1<? super UploadFileSuccess, Unit> function1) {
        this.success = function1;
    }

    public Function1<UploadFileFail, Unit> getFail() {
        return this.fail;
    }

    public void setFail(Function1<? super UploadFileFail, Unit> function1) {
        this.fail = function1;
    }

    public Function1<Object, Unit> getComplete() {
        return this.complete;
    }

    public void setComplete(Function1<Object, Unit> function1) {
        this.complete = function1;
    }

    public UploadFileOptions(String url, String str, String str2, UTSArray<UploadFileOptionFiles> uTSArray, UTSJSONObject uTSJSONObject, UTSJSONObject uTSJSONObject2, Number number, Function1<? super UploadFileSuccess, Unit> function1, Function1<? super UploadFileFail, Unit> function12, Function1<Object, Unit> function13) {
        Intrinsics.checkNotNullParameter(url, "url");
        this.url = url;
        this.filePath = str;
        this.name = str2;
        this.files = uTSArray;
        this.header = uTSJSONObject;
        this.formData = uTSJSONObject2;
        this.timeout = number;
        this.success = function1;
        this.fail = function12;
        this.complete = function13;
    }
}
