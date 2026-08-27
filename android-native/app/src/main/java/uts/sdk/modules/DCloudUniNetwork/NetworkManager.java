package uts.sdk.modules.DCloudUniNetwork;

import android.content.Context;
import android.os.Looper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.util.net.NetWork;
import io.dcloud.feature.uniapp.adapter.AbsURIAdapter;
import io.dcloud.uts.ArrayBuffer;
import io.dcloud.uts.Map;
import io.dcloud.uts.NumberKt;
import io.dcloud.uts.ObjectKt;
import io.dcloud.uts.StringKt;
import io.dcloud.uts.UTSAndroid;
import io.dcloud.uts.UTSArray;
import io.dcloud.uts.UTSIteratorKt;
import io.dcloud.uts.UTSJSONObject;
import io.dcloud.uts.UTSJSONObjectKt;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J$\u0010\u0006\u001a\u00020\u0007\"\u0004\b\u0000\u0010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\b0\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u001c\u0010\r\u001a\u00020\u000e\"\u0004\b\u0000\u0010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\b0\nH\u0016J&\u0010\u000f\u001a\u0004\u0018\u00010\u0010\"\u0004\b\u0000\u0010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\b0\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u000b\u001a\u00020\u001aH\u0016J\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0018\u001a\u00020\u001d2\u0006\u0010\u000b\u001a\u00020\u001eH\u0016R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/NetworkManager;", "", "<init>", "()V", "requestExecutorService", "Ljava/util/concurrent/ExecutorService;", AbsURIAdapter.REQUEST, "Luts/sdk/modules/DCloudUniNetwork/RequestTask;", "T", "param", "Luts/sdk/modules/DCloudUniNetwork/RequestOptions;", "listener", "Luts/sdk/modules/DCloudUniNetwork/NetworkRequestListener;", "createRequestClient", "Lokhttp3/OkHttpClient;", "createRequest", "Lokhttp3/Request;", "stringifyQuery", "", "url", "data", "Lio/dcloud/uts/UTSJSONObject;", "uploadFile", "Luts/sdk/modules/DCloudUniNetwork/UploadTask;", "options", "Luts/sdk/modules/DCloudUniNetwork/UploadFileOptions;", "Luts/sdk/modules/DCloudUniNetwork/NetworkUploadFileListener;", "downloadFile", "Luts/sdk/modules/DCloudUniNetwork/DownloadTask;", "Luts/sdk/modules/DCloudUniNetwork/DownloadFileOptions;", "Luts/sdk/modules/DCloudUniNetwork/NetworkDownloadFileListener;", "Companion", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class NetworkManager {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static ConnectionPool connectPool;
    private static NetworkManager instance;
    private ExecutorService requestExecutorService;

    public <T> RequestTask request(RequestOptions<T> param, NetworkRequestListener listener) throws IllegalAccessException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(param, "param");
        Intrinsics.checkNotNullParameter(listener, "listener");
        if (param.getData() != null && !(param.getData() instanceof UTSJSONObject) && !Intrinsics.areEqual(UTSAndroid.INSTANCE.typeof(param.getData()), "string") && !(param.getData() instanceof ArrayBuffer)) {
            UTSJSONObject uTSJSONObject_uO = UTSJSONObjectKt._uO(new Pair[0]);
            uTSJSONObject_uO.set("statusCode", "-1");
            uTSJSONObject_uO.set("errorCode", "600008");
            uTSJSONObject_uO.set("errorMsg", "the data parameter type is invalid");
            uTSJSONObject_uO.set("cause", new Exception("he data parameter type is invalid"));
            listener.onComplete(uTSJSONObject_uO);
            return new NetworkRequestTaskImpl(null);
        }
        listener.onStart();
        OkHttpClient okHttpClientCreateRequestClient = createRequestClient(param);
        Request requestCreateRequest = createRequest(param, listener);
        if (requestCreateRequest == null) {
            return new NetworkRequestTaskImpl(null);
        }
        Call callNewCall = okHttpClientCreateRequestClient.newCall(requestCreateRequest);
        Intrinsics.checkNotNullExpressionValue(callNewCall, "newCall(...)");
        NetworkRequestTaskImpl networkRequestTaskImpl = new NetworkRequestTaskImpl(callNewCall);
        Boolean enableChunked = param.getEnableChunked();
        callNewCall.enqueue(new SimpleCallback(listener, networkRequestTaskImpl, enableChunked != null ? enableChunked.booleanValue() : false, Looper.myLooper()));
        return networkRequestTaskImpl;
    }

    public <T> OkHttpClient createRequestClient(RequestOptions<T> param) {
        long jLongValue;
        Intrinsics.checkNotNullParameter(param, "param");
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (param.getTimeout() != null) {
            Number timeout = param.getTimeout();
            Intrinsics.checkNotNull(timeout);
            jLongValue = timeout.longValue();
        } else {
            jLongValue = 60000;
        }
        builder.connectTimeout(jLongValue, TimeUnit.MILLISECONDS);
        builder.readTimeout(jLongValue, TimeUnit.MILLISECONDS);
        builder.writeTimeout(jLongValue, TimeUnit.MILLISECONDS);
        builder.protocols(Collections.singletonList(Protocol.HTTP_1_1));
        builder.addInterceptor(new CookieInterceptor());
        if (param.getFirstIpv4() != null) {
            Boolean firstIpv4 = param.getFirstIpv4();
            Intrinsics.checkNotNull(firstIpv4);
            if (firstIpv4.booleanValue()) {
                builder.dns(new OKDns());
            }
        }
        if (connectPool == null) {
            connectPool = new ConnectionPool();
        }
        builder.connectionPool(connectPool);
        if (this.requestExecutorService == null) {
            this.requestExecutorService = Executors.newFixedThreadPool(10);
        }
        builder.dispatcher(new Dispatcher(this.requestExecutorService));
        Boolean enableQuic = param.getEnableQuic();
        if (enableQuic != null) {
            enableQuic.booleanValue();
        }
        OkHttpClient okHttpClientBuild = builder.build();
        Intrinsics.checkNotNullExpressionValue(okHttpClientBuild, "build(...)");
        return okHttpClientBuild;
    }

    public <T> Request createRequest(RequestOptions<T> param, NetworkRequestListener listener) throws IllegalAccessException, IllegalArgumentException {
        Object objArray;
        Object data;
        UTSJSONObject object;
        boolean z;
        Intrinsics.checkNotNullParameter(param, "param");
        Intrinsics.checkNotNullParameter(listener, "listener");
        Request.Builder builder = new Request.Builder();
        try {
            builder.url(param.getUrl());
            if (param.getHeader() == null) {
                param.setHeader(UTSJSONObjectKt._uO(new Pair[0]));
            }
            UTSJSONObject header = param.getHeader();
            Intrinsics.checkNotNull(header);
            JSON jSONObject = header.toJSONObject();
            Intrinsics.checkNotNull(jSONObject, "null cannot be cast to non-null type com.alibaba.fastjson.JSONObject");
            JSONObject jSONObject2 = (JSONObject) jSONObject;
            Iterator it = ((Set) UTSIteratorKt.resolveUTSKeyIterator(jSONObject2.keySet())).iterator();
            String string = "application/x-www-form-urlencoded; charset=UTF-8";
            boolean z2 = false;
            boolean z3 = false;
            while (true) {
                objArray = "";
                if (!it.hasNext()) {
                    break;
                }
                Iterator it2 = it;
                String str = (String) it.next();
                String str2 = string;
                if (StringsKt.equals(str, "Accept-Encoding", true)) {
                    StringBuilder sb = new StringBuilder("");
                    z = z2;
                    sb.append(NumberKt.toString_number_nullable(jSONObject2.get(str), (Number) 10));
                    if (StringKt.includes$default(StringKt.toLowerCase(sb.toString()), "gzip", null, 2, null)) {
                        string = str2;
                        it = it2;
                        z2 = z;
                    }
                } else {
                    z = z2;
                }
                if (StringsKt.equals(str, NetWork.CONTENT_TYPE, true)) {
                    StringBuilder sb2 = new StringBuilder("");
                    Object obj = jSONObject2.get(str);
                    Intrinsics.checkNotNull(obj);
                    sb2.append(NumberKt.toString(obj, (Number) 10));
                    string = sb2.toString();
                    z2 = true;
                } else {
                    string = str2;
                    z2 = z;
                }
                if (!z3 && StringsKt.equals(str, IWebview.USER_AGENT, true)) {
                    z3 = true;
                }
                builder.header(str, "" + NumberKt.toString_number_nullable(jSONObject2.get(str), (Number) 10));
                it = it2;
            }
            String str3 = (z2 || "GET".equals(param.getMethod())) ? string : "application/json";
            if (!z3) {
                UTSAndroid uTSAndroid = UTSAndroid.INSTANCE;
                Context appContext = UTSAndroid.INSTANCE.getAppContext();
                Intrinsics.checkNotNull(appContext);
                builder.header(IWebview.USER_AGENT, NumberKt.toString_number_nullable$default(uTSAndroid.getWebViewInfo(appContext).get("ua"), (Number) null, 1, (Object) null));
            }
            if ("POST".equals(param.getMethod()) || "PUT".equals(param.getMethod()) || "PATCH".equals(param.getMethod()) || "DELETE".equals(param.getMethod())) {
                if (param.getData() != null) {
                    listener.onProgress((Number) 0);
                }
                if (param.getData() != null) {
                    if (Intrinsics.areEqual(UTSAndroid.INSTANCE.typeof(param.getData()), "string")) {
                        Object data2 = param.getData();
                        Intrinsics.checkNotNull(data2, "null cannot be cast to non-null type kotlin.String");
                        objArray = (String) data2;
                    } else if (param.getData() instanceof ArrayBuffer) {
                        try {
                            Object data3 = param.getData();
                            Intrinsics.checkNotNull(data3, "null cannot be cast to non-null type io.dcloud.uts.ArrayBuffer");
                            objArray = ((ArrayBuffer) data3).toByteBuffer().array();
                            Intrinsics.checkNotNullExpressionValue(objArray, "array(...)");
                        } catch (Exception e) {
                            UTSJSONObject uTSJSONObject_uO = UTSJSONObjectKt._uO(new Pair[0]);
                            uTSJSONObject_uO.set("statusCode", "-1");
                            uTSJSONObject_uO.set("errorCode", "602001");
                            uTSJSONObject_uO.set("errorMsg", "request system error");
                            uTSJSONObject_uO.set("cause", e);
                            listener.onComplete(uTSJSONObject_uO);
                            return null;
                        }
                    } else if (param.getData() instanceof UTSJSONObject) {
                        if (NumberKt.numberEquals(StringKt.indexOf$default(str3, "application/x-www-form-urlencoded", null, 2, null), 0)) {
                            Object data4 = param.getData();
                            Intrinsics.checkNotNull(data4, "null cannot be cast to non-null type io.dcloud.uts.UTSJSONObject");
                            Map<String, Object> map = ((UTSJSONObject) data4).toMap();
                            final UTSArray uTSArray = new UTSArray();
                            map.forEach(new Function2() { // from class: uts.sdk.modules.DCloudUniNetwork.NetworkManager$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj2, Object obj3) {
                                    return NetworkManager.createRequest$lambda$0(uTSArray, obj2, (String) obj3);
                                }
                            });
                            objArray = uTSArray.join("&");
                        } else {
                            objArray = io.dcloud.uts.JSON.stringify(param.getData());
                        }
                    }
                }
                if (objArray instanceof byte[]) {
                    builder.method(param.getMethod(), RequestBody.create(MediaType.parse(str3), (byte[]) objArray));
                } else {
                    MediaType mediaType = MediaType.parse(str3);
                    Intrinsics.checkNotNull(objArray, "null cannot be cast to non-null type kotlin.String");
                    builder.method(param.getMethod(), RequestBody.create(mediaType, (String) objArray));
                }
                listener.onProgress((Number) 100);
            } else if ("HEAD".equals(param.getMethod())) {
                builder.head();
            } else if ("OPTIONS".equals(param.getMethod())) {
                builder.method(param.getMethod(), null);
            } else if ((param.getMethod() == null || "GET".equals(param.getMethod())) && (data = param.getData()) != null) {
                if (Intrinsics.areEqual(UTSAndroid.INSTANCE.typeof(data), "string")) {
                    object = io.dcloud.uts.JSON.parseObject((String) data);
                } else {
                    object = data instanceof UTSJSONObject ? (UTSJSONObject) data : null;
                }
                if (object != null) {
                    try {
                        builder.url(stringifyQuery(param.getUrl(), object));
                    } catch (Exception e2) {
                        UTSJSONObject uTSJSONObject_uO2 = UTSJSONObjectKt._uO(new Pair[0]);
                        uTSJSONObject_uO2.set("statusCode", "-1");
                        uTSJSONObject_uO2.set("errorCode", "600009");
                        uTSJSONObject_uO2.set("errorMsg", "invalid URL");
                        uTSJSONObject_uO2.set("cause", e2);
                        listener.onComplete(uTSJSONObject_uO2);
                        return null;
                    }
                }
            }
            return builder.build();
        } catch (Exception e3) {
            UTSJSONObject uTSJSONObject_uO3 = UTSJSONObjectKt._uO(new Pair[0]);
            uTSJSONObject_uO3.set("statusCode", "-1");
            uTSJSONObject_uO3.set("errorCode", "600009");
            uTSJSONObject_uO3.set("errorMsg", "invalid URL");
            uTSJSONObject_uO3.set("cause", e3);
            listener.onComplete(uTSJSONObject_uO3);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit createRequest$lambda$0(UTSArray<String> uTSArray, Object obj, String str) {
        uTSArray.push(str + '=' + NumberKt.toString_number_nullable(obj, (Number) 10));
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v8, types: [T, java.lang.String] */
    private final String stringifyQuery(String url, UTSJSONObject data) {
        String str;
        String str2;
        UTSArray<String> uTSArraySplit = StringKt.split(url, "#");
        if (NumberKt.compareTo(uTSArraySplit.getLength(), (Number) 1) > 0) {
            String str3 = uTSArraySplit.get(1);
            Intrinsics.checkNotNullExpressionValue(str3, "get(...)");
            str = str3;
        } else {
            str = "";
        }
        String str4 = uTSArraySplit.get(0);
        Intrinsics.checkNotNullExpressionValue(str4, "get(...)");
        UTSArray<String> uTSArraySplit2 = StringKt.split(str4, Operators.CONDITION_IF_STRING);
        if (NumberKt.compareTo(uTSArraySplit2.getLength(), (Number) 1) > 0) {
            String str5 = uTSArraySplit2.get(1);
            Intrinsics.checkNotNullExpressionValue(str5, "get(...)");
            str2 = str5;
        } else {
            str2 = "";
        }
        String str6 = uTSArraySplit2.get(0);
        Intrinsics.checkNotNullExpressionValue(str6, "get(...)");
        String str7 = str6;
        UTSArray<String> uTSArraySplit3 = StringKt.split(str2, "&");
        final Map map = new Map();
        uTSArraySplit3.forEach(new Function2() { // from class: uts.sdk.modules.DCloudUniNetwork.NetworkManager$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return NetworkManager.stringifyQuery$lambda$1(map, (String) obj, (Number) obj2);
            }
        });
        data.toMap().forEach(new Function2() { // from class: uts.sdk.modules.DCloudUniNetwork.NetworkManager$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return NetworkManager.stringifyQuery$lambda$2(map, obj, (String) obj2);
            }
        });
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = "";
        map.forEach(new Function2() { // from class: uts.sdk.modules.DCloudUniNetwork.NetworkManager$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return NetworkManager.stringifyQuery$lambda$3(objectRef, (String) obj, (String) obj2);
            }
        });
        objectRef.element = StringKt.slice((String) objectRef.element, (Number) 0, (Number) (-1));
        if (((String) objectRef.element).length() > 0) {
            str7 = str7 + Operators.CONDITION_IF + ((String) objectRef.element);
        }
        if (str.length() <= 0) {
            return str7;
        }
        return str7 + '#' + str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit stringifyQuery$lambda$1(Map<String, String> map, String str, Number number) {
        UTSArray<String> uTSArraySplit = StringKt.split(str, "=");
        if (NumberKt.compareTo(uTSArraySplit.getLength(), (Number) 1) > 0) {
            map.put(uTSArraySplit.get(0), uTSArraySplit.get(1));
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit stringifyQuery$lambda$2(Map<String, String> map, Object obj, String str) {
        if (obj == null) {
            obj = "";
        }
        String strEncodeURIComponent = ObjectKt.encodeURIComponent(str);
        Intrinsics.checkNotNull(strEncodeURIComponent);
        if (!(obj instanceof UTSJSONObject) && !(obj instanceof UTSArray)) {
            String strEncodeURIComponent2 = ObjectKt.encodeURIComponent("" + NumberKt.toString_number_nullable(obj, (Number) 10));
            Intrinsics.checkNotNull(strEncodeURIComponent2);
            map.put(strEncodeURIComponent, strEncodeURIComponent2);
        } else {
            String strEncodeURIComponent3 = ObjectKt.encodeURIComponent(io.dcloud.uts.JSON.stringify(obj));
            Intrinsics.checkNotNull(strEncodeURIComponent3);
            map.put(strEncodeURIComponent, strEncodeURIComponent3);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r3v2, types: [T, java.lang.String] */
    public static final Unit stringifyQuery$lambda$3(Ref.ObjectRef<String> objectRef, String str, String str2) {
        objectRef.element += str2 + '=' + str + Typography.amp;
        return Unit.INSTANCE;
    }

    public UploadTask uploadFile(UploadFileOptions options, NetworkUploadFileListener listener) {
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.checkNotNullParameter(listener, "listener");
        return UploadController.INSTANCE.getInstance().uploadFile(options, listener);
    }

    public DownloadTask downloadFile(DownloadFileOptions options, NetworkDownloadFileListener listener) {
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.checkNotNullParameter(listener, "listener");
        return DownloadController.INSTANCE.getInstance().downloadFile(options, listener);
    }

    /* compiled from: index.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\b\u001a\u00020\u0005R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/NetworkManager$Companion;", "", "<init>", "()V", "instance", "Luts/sdk/modules/DCloudUniNetwork/NetworkManager;", "connectPool", "Lokhttp3/ConnectionPool;", "getInstance", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final NetworkManager getInstance() {
            if (NetworkManager.instance == null) {
                NetworkManager.instance = new NetworkManager();
            }
            NetworkManager networkManager = NetworkManager.instance;
            Intrinsics.checkNotNull(networkManager);
            return networkManager;
        }
    }
}
