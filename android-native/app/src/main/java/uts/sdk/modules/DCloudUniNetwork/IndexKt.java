package uts.sdk.modules.DCloudUniNetwork;

import com.taobao.weex.adapter.IWXUserTrackAdapter;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.feature.uniapp.adapter.AbsURIAdapter;
import io.dcloud.uts.Map;
import io.dcloud.uts.UTSAndroid;
import io.dcloud.uts.UTSArray;
import io.dcloud.uts.UTSArrayKt;
import io.dcloud.uts.UTSBridge;
import io.dcloud.uts.UTSCallback;
import io.dcloud.uts.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000Ä\u0001\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0004\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u00104\u001a\u00060\u0003j\u0002`12\u0006\u00105\u001a\u00020\u0003\u001a\u001f\u0010=\u001a\u00020>\"\u0006\b\u0000\u0010\u0005\u0018\u00012\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00050?H\u0086\b\u001a\u000e\u0010G\u001a\u00020H2\u0006\u0010\u0017\u001a\u00020I\u001a\u000e\u0010J\u001a\u00020H2\u0006\u0010\u0017\u001a\u00020K\"\u0014\u0010,\u001a\u00020\u0001X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.\"!\u0010/\u001a\u0012\u0012\b\u0012\u00060\u0003j\u0002`1\u0012\u0004\u0012\u00020\u000100¢\u0006\b\n\u0000\u001a\u0004\b2\u00103\"\"\u00106\u001a\n 8*\u0004\u0018\u00010707X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<\"0\u0010@\u001a!\u0012\u0013\u0012\u00110\u0016¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0017\u0012\u0004\u0012\u00020\u00180\u0006j\u0002`A¢\u0006\b\n\u0000\u001a\u0004\bB\u0010C\"0\u0010D\u001a!\u0012\u0013\u0012\u00110!¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0017\u0012\u0004\u0012\u00020\"0\u0006j\u0002`E¢\u0006\b\n\u0000\u001a\u0004\bF\u0010C*\n\u0010\u0000\"\u00020\u00012\u00020\u0001*\n\u0010\u0002\"\u00020\u00032\u00020\u0003*R\u0010\u0004\u001a\u0004\b\u0000\u0010\u0005\"#\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u0002H\u00050\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u00062#\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u0002H\u00050\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u0006*@\u0010\f\"\u001d\u0012\u0013\u0012\u00110\r¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u00062\u001d\u0012\u0013\u0012\u00110\r¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u0006*@\u0010\u000e\"\u001d\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u00062\u001d\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u0006*@\u0010\u0010\"\u001d\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b0\u00062\u001d\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b0\u0006*@\u0010\u0013\"\u001d\u0012\u0013\u0012\u00110\u0014¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b0\u00062\u001d\u0012\u0013\u0012\u00110\u0014¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b0\u0006*@\u0010\u0015\"\u001d\u0012\u0013\u0012\u00110\u0016¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0017\u0012\u0004\u0012\u00020\u00180\u00062\u001d\u0012\u0013\u0012\u00110\u0016¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0017\u0012\u0004\u0012\u00020\u00180\u0006*@\u0010\u0019\"\u001d\u0012\u0013\u0012\u00110\u001a¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b0\u00062\u001d\u0012\u0013\u0012\u00110\u001a¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b0\u0006*@\u0010\u001b\"\u001d\u0012\u0013\u0012\u00110\u001c¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b0\u00062\u001d\u0012\u0013\u0012\u00110\u001c¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b0\u0006*@\u0010\u001d\"\u001d\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b0\u00062\u001d\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b0\u0006*@\u0010\u001e\"\u001d\u0012\u0013\u0012\u00110\u001f¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b0\u00062\u001d\u0012\u0013\u0012\u00110\u001f¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b0\u0006*@\u0010 \"\u001d\u0012\u0013\u0012\u00110!¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0017\u0012\u0004\u0012\u00020\"0\u00062\u001d\u0012\u0013\u0012\u00110!¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0017\u0012\u0004\u0012\u00020\"0\u0006*@\u0010#\"\u001d\u0012\u0013\u0012\u00110$¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b0\u00062\u001d\u0012\u0013\u0012\u00110$¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b0\u0006*@\u0010%\"\u001d\u0012\u0013\u0012\u00110&¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b0\u00062\u001d\u0012\u0013\u0012\u00110&¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b0\u0006*\n\u0010'\"\u00020\u000f2\u00020\u000f*S\u0010(\"\u001d\u0012\u0013\u0012\u0011`)¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b0\u000620\u0012&\u0012$0\u000fj\u0011`)¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0012¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b0\u0006*@\u0010*\"\u001d\u0012\u0013\u0012\u00110+¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b0\u00062\u001d\u0012\u0013\u0012\u00110+¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b0\u0006¨\u0006L"}, d2 = {"RequestMethod", "", "RequestErrorCode", "", "RequestSuccessCallback", "T", "Lkotlin/Function1;", "Luts/sdk/modules/DCloudUniNetwork/RequestSuccess;", "Lkotlin/ParameterName;", "name", AbsoluteConst.JSON_KEY_OPTION, "", "RequestFailCallback", "Luts/sdk/modules/DCloudUniNetwork/RequestFail;", "RequestCompleteCallback", "", "RequestTaskOnChunkReceivedCallback", "Luts/sdk/modules/DCloudUniNetwork/RequestTaskOnChunkReceivedListenerResult;", "result", "RequestTaskOnHeadersReceivedCallback", "Luts/sdk/modules/DCloudUniNetwork/RequestTaskOnHeadersReceivedListenerResult;", "UploadFile", "Luts/sdk/modules/DCloudUniNetwork/UploadFileOptions;", "options", "Luts/sdk/modules/DCloudUniNetwork/UploadTask;", "UploadFileSuccessCallback", "Luts/sdk/modules/DCloudUniNetwork/UploadFileSuccess;", "UploadFileFailCallback", "Luts/sdk/modules/DCloudUniNetwork/UploadFileFail;", "UploadFileCompleteCallback", "UploadFileProgressUpdateCallback", "Luts/sdk/modules/DCloudUniNetwork/OnProgressUpdateResult;", "DownloadFile", "Luts/sdk/modules/DCloudUniNetwork/DownloadFileOptions;", "Luts/sdk/modules/DCloudUniNetwork/DownloadTask;", "DownloadFileSuccessCallback", "Luts/sdk/modules/DCloudUniNetwork/DownloadFileSuccess;", "DownloadFileFailCallback", "Luts/sdk/modules/DCloudUniNetwork/DownloadFileFail;", "DownloadFileComplete", "DownloadFileCompleteCallback", "Luts/sdk/modules/DCloudUniNetwork/DownloadFileComplete;", "DownloadFileProgressUpdateCallback", "Luts/sdk/modules/DCloudUniNetwork/OnProgressDownloadResult;", "UniNetWorkErrorSubject", "getUniNetWorkErrorSubject", "()Ljava/lang/String;", "NetWorkUniErrors", "Lio/dcloud/uts/Map;", "Luts/sdk/modules/DCloudUniNetwork/RequestErrorCode;", "getNetWorkUniErrors", "()Lio/dcloud/uts/Map;", "getErrcode", IWXUserTrackAdapter.MONITOR_ERROR_CODE, "charsetPattern", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "getCharsetPattern", "()Ljava/util/regex/Pattern;", "setCharsetPattern", "(Ljava/util/regex/Pattern;)V", AbsURIAdapter.REQUEST, "Luts/sdk/modules/DCloudUniNetwork/RequestTask;", "Luts/sdk/modules/DCloudUniNetwork/RequestOptions;", "uploadFile", "Luts/sdk/modules/DCloudUniNetwork/UploadFile;", "getUploadFile", "()Lkotlin/jvm/functions/Function1;", "downloadFile", "Luts/sdk/modules/DCloudUniNetwork/DownloadFile;", "getDownloadFile", "uploadFileByJs", "", "Luts/sdk/modules/DCloudUniNetwork/UploadFileOptionsJSONObject;", "downloadFileByJs", "Luts/sdk/modules/DCloudUniNetwork/DownloadFileOptionsJSONObject;", "uni-network_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class IndexKt {
    private static final String UniNetWorkErrorSubject = "uni-request";
    private static final Map<Number, String> NetWorkUniErrors = new Map<>((UTSArray<UTSArray<Object>>) UTSArrayKt._uA(UTSArrayKt._uA(5, "time out"), UTSArrayKt._uA(1000, "server system error"), UTSArrayKt._uA(100001, "invalid json"), UTSArrayKt._uA(100002, "error message invalid json"), UTSArrayKt._uA(100003, "json parsing type conversion failed"), UTSArrayKt._uA(600003, "network interrupted error"), UTSArrayKt._uA(600008, "the data parameter type is invalid"), UTSArrayKt._uA(600009, "invalid URL"), UTSArrayKt._uA(602001, "request system error")));
    private static Pattern charsetPattern = Pattern.compile("charset=([a-z0-9-]+)");
    private static final Function1<UploadFileOptions, UploadTask> uploadFile = new Function1() { // from class: uts.sdk.modules.DCloudUniNetwork.IndexKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object invoke2(Object obj) {
            return IndexKt.uploadFile$lambda$0((UploadFileOptions) obj);
        }
    };
    private static final Function1<DownloadFileOptions, DownloadTask> downloadFile = new Function1() { // from class: uts.sdk.modules.DCloudUniNetwork.IndexKt$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object invoke2(Object obj) {
            return IndexKt.downloadFile$lambda$1((DownloadFileOptions) obj);
        }
    };

    public static final String getUniNetWorkErrorSubject() {
        return UniNetWorkErrorSubject;
    }

    public static final Map<Number, String> getNetWorkUniErrors() {
        return NetWorkUniErrors;
    }

    public static final Number getErrcode(Number errCode) {
        Intrinsics.checkNotNullParameter(errCode, "errCode");
        return NetWorkUniErrors.get(errCode) == null ? (Number) 602001 : errCode;
    }

    public static final Pattern getCharsetPattern() {
        return charsetPattern;
    }

    public static final void setCharsetPattern(Pattern pattern) {
        charsetPattern = pattern;
    }

    public static final /* synthetic */ <T> RequestTask request(RequestOptions<T> options) {
        Intrinsics.checkNotNullParameter(options, "options");
        UTSAndroid uTSAndroid = UTSAndroid.INSTANCE;
        Intrinsics.needClassReification();
        Type type = new TypeToken<T>() { // from class: uts.sdk.modules.DCloudUniNetwork.IndexKt$request$$inlined$getGenericType$1
        }.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        UTSAndroid uTSAndroid2 = UTSAndroid.INSTANCE;
        Intrinsics.needClassReification();
        String name = new TypeToken<T>() { // from class: uts.sdk.modules.DCloudUniNetwork.IndexKt$request$$inlined$getGenericClassName$1
        }.getRawType().getName();
        Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
        return NetworkManager.INSTANCE.getInstance().request(options, new RequestNetworkListener(options, type, name));
    }

    public static final Function1<UploadFileOptions, UploadTask> getUploadFile() {
        return uploadFile;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final UploadTask uploadFile$lambda$0(UploadFileOptions uploadFileOptions) {
        return NetworkManager.INSTANCE.getInstance().uploadFile(uploadFileOptions, new UploadNetworkListener(uploadFileOptions));
    }

    public static final Function1<DownloadFileOptions, DownloadTask> getDownloadFile() {
        return downloadFile;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final DownloadTask downloadFile$lambda$1(DownloadFileOptions downloadFileOptions) {
        return NetworkManager.INSTANCE.getInstance().downloadFile(downloadFileOptions, new DownloadNetworkListener(downloadFileOptions));
    }

    public static final int uploadFileByJs(final UploadFileOptionsJSONObject options) {
        Intrinsics.checkNotNullParameter(options, "options");
        return UTSBridge.INSTANCE.registerJavaScriptClassInstance(new UploadTaskByJsProxy(uploadFile.invoke2(new UploadFileOptions(options.getUrl(), options.getFilePath(), options.getName(), options.getFiles(), options.getHeader(), options.getFormData(), options.getTimeout(), new Function1() { // from class: uts.sdk.modules.DCloudUniNetwork.IndexKt$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.uploadFileByJs$lambda$2(options, (UploadFileSuccess) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniNetwork.IndexKt$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.uploadFileByJs$lambda$3(options, (UploadFileFail) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniNetwork.IndexKt$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.uploadFileByJs$lambda$4(options, obj);
            }
        }))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit uploadFileByJs$lambda$2(UploadFileOptionsJSONObject uploadFileOptionsJSONObject, UploadFileSuccess uploadFileSuccess) throws SecurityException {
        UTSCallback success = uploadFileOptionsJSONObject.getSuccess();
        if (success != null) {
            success.invoke(uploadFileSuccess);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit uploadFileByJs$lambda$3(UploadFileOptionsJSONObject uploadFileOptionsJSONObject, UploadFileFail uploadFileFail) throws SecurityException {
        UTSCallback fail = uploadFileOptionsJSONObject.getFail();
        if (fail != null) {
            fail.invoke(uploadFileFail);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit uploadFileByJs$lambda$4(UploadFileOptionsJSONObject uploadFileOptionsJSONObject, Object obj) throws SecurityException {
        UTSCallback complete = uploadFileOptionsJSONObject.getComplete();
        if (complete != null) {
            complete.invoke(obj);
        }
        return Unit.INSTANCE;
    }

    public static final int downloadFileByJs(final DownloadFileOptionsJSONObject options) {
        Intrinsics.checkNotNullParameter(options, "options");
        return UTSBridge.INSTANCE.registerJavaScriptClassInstance(new DownloadTaskByJsProxy(downloadFile.invoke2(new DownloadFileOptions(options.getUrl(), options.getHeader(), options.getFilePath(), options.getTimeout(), new Function1() { // from class: uts.sdk.modules.DCloudUniNetwork.IndexKt$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.downloadFileByJs$lambda$5(options, (DownloadFileSuccess) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniNetwork.IndexKt$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.downloadFileByJs$lambda$6(options, (DownloadFileFail) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniNetwork.IndexKt$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.downloadFileByJs$lambda$7(options, obj);
            }
        }))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit downloadFileByJs$lambda$5(DownloadFileOptionsJSONObject downloadFileOptionsJSONObject, DownloadFileSuccess downloadFileSuccess) throws SecurityException {
        UTSCallback success = downloadFileOptionsJSONObject.getSuccess();
        if (success != null) {
            success.invoke(downloadFileSuccess);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit downloadFileByJs$lambda$6(DownloadFileOptionsJSONObject downloadFileOptionsJSONObject, DownloadFileFail downloadFileFail) throws SecurityException {
        UTSCallback fail = downloadFileOptionsJSONObject.getFail();
        if (fail != null) {
            fail.invoke(downloadFileFail);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit downloadFileByJs$lambda$7(DownloadFileOptionsJSONObject downloadFileOptionsJSONObject, Object obj) throws SecurityException {
        UTSCallback complete = downloadFileOptionsJSONObject.getComplete();
        if (complete != null) {
            complete.invoke(obj);
        }
        return Unit.INSTANCE;
    }
}
