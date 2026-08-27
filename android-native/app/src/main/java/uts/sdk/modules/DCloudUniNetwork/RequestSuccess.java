package uts.sdk.modules.DCloudUniNetwork;

import com.taobao.weex.ui.component.WXBasicComponentType;
import io.dcloud.uts.JsonNotNull;
import io.dcloud.uts.UTSArray;
import io.dcloud.uts.UTSObject;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0004\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0012\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B1\u0012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00018\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0004\b\u000b\u0010\fR\u001e\u0010\u0003\u001a\u0004\u0018\u00018\u0000X\u0096\u000e¢\u0006\u0010\n\u0002\u0010\u0011\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001e\u0010\u0004\u001a\u00020\u00058\u0016@\u0016X\u0097\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001e\u0010\u0006\u001a\u00020\u00078\u0016@\u0016X\u0097\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u000e\"\u0004\b\u0017\u0010\u0010R$\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8\u0016@\u0016X\u0097\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001b¨\u0006\u001c"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/RequestSuccess;", "T", "Lio/dcloud/uts/UTSObject;", "data", "statusCode", "", WXBasicComponentType.HEADER, "", "cookies", "Lio/dcloud/uts/UTSArray;", "", "<init>", "(Ljava/lang/Object;Ljava/lang/Number;Ljava/lang/Object;Lio/dcloud/uts/UTSArray;)V", "getData", "()Ljava/lang/Object;", "setData", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "getStatusCode", "()Ljava/lang/Number;", "setStatusCode", "(Ljava/lang/Number;)V", "getHeader", "setHeader", "getCookies", "()Lio/dcloud/uts/UTSArray;", "setCookies", "(Lio/dcloud/uts/UTSArray;)V", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class RequestSuccess<T> extends UTSObject {

    @JsonNotNull
    private UTSArray<String> cookies;
    private T data;

    @JsonNotNull
    private Object header;

    @JsonNotNull
    private Number statusCode;

    public /* synthetic */ RequestSuccess(Object obj, Number number, Object obj2, UTSArray uTSArray, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : obj, number, obj2, uTSArray);
    }

    public T getData() {
        return this.data;
    }

    public void setData(T t) {
        this.data = t;
    }

    public Number getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(Number number) {
        Intrinsics.checkNotNullParameter(number, "<set-?>");
        this.statusCode = number;
    }

    public Object getHeader() {
        return this.header;
    }

    public void setHeader(Object obj) {
        Intrinsics.checkNotNullParameter(obj, "<set-?>");
        this.header = obj;
    }

    public UTSArray<String> getCookies() {
        return this.cookies;
    }

    public void setCookies(UTSArray<String> uTSArray) {
        Intrinsics.checkNotNullParameter(uTSArray, "<set-?>");
        this.cookies = uTSArray;
    }

    public RequestSuccess(T t, Number statusCode, Object header, UTSArray<String> cookies) {
        Intrinsics.checkNotNullParameter(statusCode, "statusCode");
        Intrinsics.checkNotNullParameter(header, "header");
        Intrinsics.checkNotNullParameter(cookies, "cookies");
        this.data = t;
        this.statusCode = statusCode;
        this.header = header;
        this.cookies = cookies;
    }
}
