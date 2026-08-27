package uts.sdk.modules.DCloudUniNetwork;

import com.taobao.weex.http.Status;
import com.taobao.weex.ui.module.WXModalUIModule;
import io.dcloud.uts.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0016\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/StatusCode;", "", "<init>", "()V", "Companion", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class StatusCode {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static Map<String, String> statusCodeMap;

    /* compiled from: index.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u000b\u001a\u00020\fH\u0002J\u000e\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0006R(\u0010\u0004\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/StatusCode$Companion;", "", "<init>", "()V", "statusCodeMap", "Lio/dcloud/uts/Map;", "", "getStatusCodeMap", "()Lio/dcloud/uts/Map;", "setStatusCodeMap", "(Lio/dcloud/uts/Map;)V", "initStatusCodeMap", "", "getStatus", "code", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Map<String, String> getStatusCodeMap() {
            return StatusCode.statusCodeMap;
        }

        public final void setStatusCodeMap(Map<String, String> map) {
            StatusCode.statusCodeMap = map;
        }

        private final void initStatusCodeMap() {
            Map<String, String> map = new Map<>();
            setStatusCodeMap(map);
            map.set("100", "Continue");
            map.set("101", "Switching Protocol");
            map.set("200", WXModalUIModule.OK);
            map.set("201", "Created");
            map.set("202", "Accepted");
            map.set("203", "Non-Authoritative Information");
            map.set("204", "No Content");
            map.set("205", "Reset Content");
            map.set("206", "Partial Content");
            map.set("300", "Multiple Choice");
            map.set("301", "Moved Permanently");
            map.set("302", "Found");
            map.set("303", "See Other");
            map.set("304", "Not Modified");
            map.set("305", "Use Proxy");
            map.set("306", "unused");
            map.set("307", "Temporary Redirect");
            map.set("308", "Permanent Redirect");
            map.set("400", "Bad Request");
            map.set("401", "Unauthorized");
            map.set("402", "Payment Required");
            map.set("403", "Forbidden");
            map.set("404", "Not Found");
            map.set("405", "Method Not Allowed");
            map.set("406", "Not Acceptable");
            map.set("407", "Proxy Authentication Required");
            map.set("408", "Request Timeout");
            map.set("409", "Conflict");
            map.set("410", "Gone");
            map.set("411", "Length Required");
            map.set("412", "Precondition Failed");
            map.set("413", "Payload Too Large");
            map.set("414", "URI Too Long");
            map.set("415", "Unsupported Media Type");
            map.set("416", "Requested Range Not Satisfiable");
            map.set("417", "Expectation Failed");
            map.set("418", "I'm a teapot");
            map.set("421", "Misdirected Request");
            map.set("426", "Upgrade Required");
            map.set("428", "Precondition Required");
            map.set("429", "Too Many Requests");
            map.set("431", "Request Header Fields Too Large");
            map.set("500", "Internal Server Error");
            map.set("501", "Not Implemented");
            map.set("502", "Bad Gateway");
            map.set("503", "Service Unavailable");
            map.set("504", "Gateway Timeout");
            map.set("505", "HTTP Version Not Supported");
            map.set("506", "Variant Also Negotiates");
            map.set("507", "Variant Also Negotiates");
            map.set("511", "Network Authentication Required");
        }

        public final String getStatus(String code) {
            Intrinsics.checkNotNullParameter(code, "code");
            if (getStatusCodeMap() == null) {
                initStatusCodeMap();
            }
            Map<String, String> statusCodeMap = getStatusCodeMap();
            Intrinsics.checkNotNull(statusCodeMap);
            if (!statusCodeMap.has(code)) {
                return Status.UNKNOWN_STATUS;
            }
            String str = statusCodeMap.get(code);
            Intrinsics.checkNotNull(str);
            return str;
        }
    }
}
