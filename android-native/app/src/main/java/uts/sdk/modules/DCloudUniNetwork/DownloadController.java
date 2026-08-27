package uts.sdk.modules.DCloudUniNetwork;

import android.content.Context;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.uniapp.SourceError;
import io.dcloud.uts.Map;
import io.dcloud.uts.NumberKt;
import io.dcloud.uts.UTSAndroid;
import io.dcloud.uts.UTSIteratorKt;
import io.dcloud.uts.UTSJSONObject;
import io.dcloud.uts.UTSJSONObjectKt;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/* compiled from: index.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\tH\u0002J\u001a\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/DownloadController;", "", "<init>", "()V", "downloadExecutorService", "Ljava/util/concurrent/ExecutorService;", "downloadFile", "Luts/sdk/modules/DCloudUniNetwork/DownloadTask;", "options", "Luts/sdk/modules/DCloudUniNetwork/DownloadFileOptions;", "listener", "Luts/sdk/modules/DCloudUniNetwork/NetworkDownloadFileListener;", "createDownloadClient", "Lokhttp3/OkHttpClient;", AbsoluteConst.JSON_KEY_OPTION, "createDownloadRequest", "Lokhttp3/Request;", "Companion", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class DownloadController {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static DownloadController instance;
    private ExecutorService downloadExecutorService;

    public DownloadTask downloadFile(DownloadFileOptions options, NetworkDownloadFileListener listener) {
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.checkNotNullParameter(listener, "listener");
        OkHttpClient okHttpClientCreateDownloadClient = createDownloadClient(options);
        Request requestCreateDownloadRequest = createDownloadRequest(options, listener);
        if (requestCreateDownloadRequest == null) {
            return new NetworkDownloadTaskImpl(null, listener);
        }
        Call callNewCall = okHttpClientCreateDownloadClient.newCall(requestCreateDownloadRequest);
        Intrinsics.checkNotNullExpressionValue(callNewCall, "newCall(...)");
        String filePath = options.getFilePath();
        if (filePath == null) {
            filePath = "";
        }
        callNewCall.enqueue(new SimpleDownloadCallback(listener, filePath));
        return new NetworkDownloadTaskImpl(callNewCall, listener);
    }

    private final OkHttpClient createDownloadClient(DownloadFileOptions option) {
        long jLongValue;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (option.getTimeout() != null) {
            Number timeout = option.getTimeout();
            Intrinsics.checkNotNull(timeout);
            jLongValue = timeout.longValue();
        } else {
            jLongValue = 120000;
        }
        builder.connectTimeout(jLongValue, TimeUnit.MILLISECONDS);
        builder.readTimeout(jLongValue, TimeUnit.MILLISECONDS);
        builder.writeTimeout(jLongValue, TimeUnit.MILLISECONDS);
        builder.addInterceptor(new CookieInterceptor());
        if (this.downloadExecutorService == null) {
            this.downloadExecutorService = Executors.newFixedThreadPool(10);
        }
        builder.dispatcher(new Dispatcher(this.downloadExecutorService));
        OkHttpClient okHttpClientBuild = builder.build();
        Intrinsics.checkNotNullExpressionValue(okHttpClientBuild, "build(...)");
        return okHttpClientBuild;
    }

    private final Request createDownloadRequest(DownloadFileOptions options, NetworkDownloadFileListener listener) {
        Request.Builder builder = new Request.Builder();
        boolean z = false;
        try {
            builder.url(options.getUrl());
            UTSJSONObject header = options.getHeader();
            Map<String, Object> map = header != null ? header.toMap() : null;
            if (map != null) {
                for (Map.Entry entry : ((java.util.Map) UTSIteratorKt.resolveUTSKeyIterator(map)).entrySet()) {
                    String str = (String) entry.getKey();
                    Object value = entry.getValue();
                    if (!z && StringsKt.equals(str, IWebview.USER_AGENT, true)) {
                        z = true;
                    }
                    if (value != null) {
                        builder.addHeader(str, "" + NumberKt.toString_number_nullable(value, (Number) 10));
                    }
                }
            }
            if (!z) {
                UTSAndroid uTSAndroid = UTSAndroid.INSTANCE;
                Context appContext = UTSAndroid.INSTANCE.getAppContext();
                Intrinsics.checkNotNull(appContext);
                builder.header(IWebview.USER_AGENT, NumberKt.toString_number_nullable$default(uTSAndroid.getWebViewInfo(appContext).get("ua"), (Number) null, 1, (Object) null));
            }
            return builder.build();
        } catch (Exception e) {
            UTSJSONObject uTSJSONObject_uO = UTSJSONObjectKt._uO(new Pair[0]);
            uTSJSONObject_uO.set("statusCode", "-1");
            uTSJSONObject_uO.set("errorCode", "-1");
            uTSJSONObject_uO.set("errorMsg", "invalid URL");
            uTSJSONObject_uO.set("cause", new SourceError(NumberKt.toString_number_nullable$default(e.getCause(), (Number) null, 1, (Object) null)));
            if (listener != null) {
                listener.onComplete(uTSJSONObject_uO);
            }
            return null;
        }
    }

    /* compiled from: index.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0006\u001a\u00020\u0005R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/DownloadController$Companion;", "", "<init>", "()V", "instance", "Luts/sdk/modules/DCloudUniNetwork/DownloadController;", "getInstance", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final DownloadController getInstance() {
            if (DownloadController.instance == null) {
                DownloadController.instance = new DownloadController();
            }
            DownloadController downloadController = DownloadController.instance;
            Intrinsics.checkNotNull(downloadController);
            return downloadController;
        }
    }
}
