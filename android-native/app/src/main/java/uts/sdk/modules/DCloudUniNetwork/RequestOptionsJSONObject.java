package uts.sdk.modules.DCloudUniNetwork;

import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.component.WXBasicComponentType;
import com.taobao.weex.ui.component.WXImage;
import io.dcloud.uts.UTSCallback;
import io.dcloud.uts.UTSJSONObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0004\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0002X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\"\u0010\u0016\u001a\n\u0018\u00010\u0006j\u0004\u0018\u0001`\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\b\"\u0004\b\u0019\u0010\nR\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001e\u0010 \u001a\u0004\u0018\u00010!X\u0096\u000e¢\u0006\u0010\n\u0002\u0010&\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001c\u0010'\u001a\u0004\u0018\u00010\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\b\"\u0004\b)\u0010\nR\u001c\u0010*\u001a\u0004\u0018\u00010\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\b\"\u0004\b,\u0010\nR\u001e\u0010-\u001a\u0004\u0018\u00010!X\u0096\u000e¢\u0006\u0010\n\u0002\u0010&\u001a\u0004\b.\u0010#\"\u0004\b/\u0010%R\u001e\u00100\u001a\u0004\u0018\u00010!X\u0096\u000e¢\u0006\u0010\n\u0002\u0010&\u001a\u0004\b1\u0010#\"\u0004\b2\u0010%R\u001e\u00103\u001a\u0004\u0018\u00010!X\u0096\u000e¢\u0006\u0010\n\u0002\u0010&\u001a\u0004\b4\u0010#\"\u0004\b5\u0010%R\u001e\u00106\u001a\u0004\u0018\u00010!X\u0096\u000e¢\u0006\u0010\n\u0002\u0010&\u001a\u0004\b7\u0010#\"\u0004\b8\u0010%R\u001c\u00109\u001a\u0004\u0018\u00010:X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010<\"\u0004\b=\u0010>R\u001c\u0010?\u001a\u0004\u0018\u00010:X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010<\"\u0004\bA\u0010>R\u001c\u0010B\u001a\u0004\u0018\u00010:X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010<\"\u0004\bD\u0010>¨\u0006E"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/RequestOptionsJSONObject;", "T", "Lio/dcloud/uts/UTSJSONObject;", "<init>", "()V", "url", "", "getUrl", "()Ljava/lang/String;", "setUrl", "(Ljava/lang/String;)V", "data", "", "getData", "()Ljava/lang/Object;", "setData", "(Ljava/lang/Object;)V", WXBasicComponentType.HEADER, "getHeader", "()Lio/dcloud/uts/UTSJSONObject;", "setHeader", "(Lio/dcloud/uts/UTSJSONObject;)V", "method", "Luts/sdk/modules/DCloudUniNetwork/RequestMethod;", "getMethod", "setMethod", "timeout", "", "getTimeout", "()Ljava/lang/Number;", "setTimeout", "(Ljava/lang/Number;)V", "enableQuic", "", "getEnableQuic", "()Ljava/lang/Boolean;", "setEnableQuic", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "dataType", "getDataType", "setDataType", "responseType", "getResponseType", "setResponseType", "sslVerify", "getSslVerify", "setSslVerify", "withCredentials", "getWithCredentials", "setWithCredentials", "firstIpv4", "getFirstIpv4", "setFirstIpv4", "enableChunked", "getEnableChunked", "setEnableChunked", WXImage.SUCCEED, "Lio/dcloud/uts/UTSCallback;", "getSuccess", "()Lio/dcloud/uts/UTSCallback;", "setSuccess", "(Lio/dcloud/uts/UTSCallback;)V", Constants.Event.FAIL, "getFail", "setFail", "complete", "getComplete", "setComplete", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class RequestOptionsJSONObject<T> extends UTSJSONObject {
    private UTSCallback complete;
    private Object data;
    private String dataType;
    private Boolean enableChunked;
    private Boolean enableQuic;
    private UTSCallback fail;
    private Boolean firstIpv4;
    private UTSJSONObject header;
    private String method;
    private String responseType;
    private Boolean sslVerify;
    private UTSCallback success;
    private Number timeout;
    public String url;
    private Boolean withCredentials;

    public String getUrl() {
        String str = this.url;
        if (str != null) {
            return str;
        }
        Intrinsics.throwUninitializedPropertyAccessException("url");
        return null;
    }

    public void setUrl(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.url = str;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object obj) {
        this.data = obj;
    }

    public UTSJSONObject getHeader() {
        return this.header;
    }

    public void setHeader(UTSJSONObject uTSJSONObject) {
        this.header = uTSJSONObject;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String str) {
        this.method = str;
    }

    public Number getTimeout() {
        return this.timeout;
    }

    public void setTimeout(Number number) {
        this.timeout = number;
    }

    public Boolean getEnableQuic() {
        return this.enableQuic;
    }

    public void setEnableQuic(Boolean bool) {
        this.enableQuic = bool;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String str) {
        this.dataType = str;
    }

    public String getResponseType() {
        return this.responseType;
    }

    public void setResponseType(String str) {
        this.responseType = str;
    }

    public Boolean getSslVerify() {
        return this.sslVerify;
    }

    public void setSslVerify(Boolean bool) {
        this.sslVerify = bool;
    }

    public Boolean getWithCredentials() {
        return this.withCredentials;
    }

    public void setWithCredentials(Boolean bool) {
        this.withCredentials = bool;
    }

    public Boolean getFirstIpv4() {
        return this.firstIpv4;
    }

    public void setFirstIpv4(Boolean bool) {
        this.firstIpv4 = bool;
    }

    public Boolean getEnableChunked() {
        return this.enableChunked;
    }

    public void setEnableChunked(Boolean bool) {
        this.enableChunked = bool;
    }

    public UTSCallback getSuccess() {
        return this.success;
    }

    public void setSuccess(UTSCallback uTSCallback) {
        this.success = uTSCallback;
    }

    public UTSCallback getFail() {
        return this.fail;
    }

    public void setFail(UTSCallback uTSCallback) {
        this.fail = uTSCallback;
    }

    public UTSCallback getComplete() {
        return this.complete;
    }

    public void setComplete(UTSCallback uTSCallback) {
        this.complete = uTSCallback;
    }
}
