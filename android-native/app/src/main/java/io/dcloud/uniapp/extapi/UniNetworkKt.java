package io.dcloud.uniapp.extapi;

import io.dcloud.feature.uniapp.adapter.AbsURIAdapter;
import io.dcloud.uts.UTSAndroid;
import io.dcloud.uts.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import uts.sdk.modules.DCloudUniNetwork.DownloadFileOptions;
import uts.sdk.modules.DCloudUniNetwork.DownloadTask;
import uts.sdk.modules.DCloudUniNetwork.IndexKt;
import uts.sdk.modules.DCloudUniNetwork.NetworkManager;
import uts.sdk.modules.DCloudUniNetwork.RequestNetworkListener;
import uts.sdk.modules.DCloudUniNetwork.RequestOptions;
import uts.sdk.modules.DCloudUniNetwork.RequestTask;
import uts.sdk.modules.DCloudUniNetwork.UploadFileOptions;
import uts.sdk.modules.DCloudUniNetwork.UploadTask;

/* compiled from: uniNetwork.kt */
@Metadata(d1 = {"\u0000®\u0001\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0004\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a-\u0010,\u001a\u00060\u001aj\u0002`-\"\u0006\b\u0000\u0010\u0001\u0018\u00012\u0016\u0010.\u001a\u0012\u0012\u0004\u0012\u0002H\u00010\u0002j\b\u0012\u0004\u0012\u0002H\u0001`/H\u0086\b\"0\u00100\u001a!\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b2\u0012\b\b3\u0012\u0004\b\b(.\u0012\u0004\u0012\u00020\u001e01j\u0002`4¢\u0006\b\n\u0000\u001a\u0004\b5\u00106\"0\u00107\u001a!\u0012\u0013\u0012\u00110\u0014¢\u0006\f\b2\u0012\b\b3\u0012\u0004\b\b(.\u0012\u0004\u0012\u00020\"01j\u0002`8¢\u0006\b\n\u0000\u001a\u0004\b9\u00106*\u001c\u0010\u0000\u001a\u0004\b\u0000\u0010\u0001\"\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u001c\u0010\u0003\u001a\u0004\b\u0000\u0010\u0001\"\b\u0012\u0004\u0012\u0002H\u00010\u00042\b\u0012\u0004\u0012\u0002H\u00010\u0004*\n\u0010\u0005\"\u00020\u00062\u00020\u0006*\n\u0010\u0007\"\u00020\b2\u00020\b*\n\u0010\t\"\u00020\n2\u00020\n*\n\u0010\u000b\"\u00020\f2\u00020\f*\n\u0010\r\"\u00020\u000e2\u00020\u000e*\n\u0010\u000f\"\u00020\u00102\u00020\u0010*\n\u0010\u0011\"\u00020\u00122\u00020\u0012*\n\u0010\u0013\"\u00020\u00142\u00020\u0014*\n\u0010\u0015\"\u00020\u00162\u00020\u0016*\n\u0010\u0017\"\u00020\u00182\u00020\u0018*\n\u0010\u0019\"\u00020\u001a2\u00020\u001a*\n\u0010\u001b\"\u00020\u001c2\u00020\u001c*\n\u0010\u001d\"\u00020\u001e2\u00020\u001e*\n\u0010\u001f\"\u00020 2\u00020 *\n\u0010!\"\u00020\"2\u00020\"*\u000e\u0010#\"\u0002`$2\u00060%j\u0002`$*\u000e\u0010&\"\u0002`'2\u00060(j\u0002`'*\u000e\u0010)\"\u0002`*2\u00060+j\u0002`*¨\u0006:"}, d2 = {"RequestOptions", "T", "Luts/sdk/modules/DCloudUniNetwork/RequestOptions;", "RequestSuccess", "Luts/sdk/modules/DCloudUniNetwork/RequestSuccess;", "RequestTaskOnChunkReceivedListenerResult", "Luts/sdk/modules/DCloudUniNetwork/RequestTaskOnChunkReceivedListenerResult;", "RequestTaskOnHeadersReceivedListenerResult", "Luts/sdk/modules/DCloudUniNetwork/RequestTaskOnHeadersReceivedListenerResult;", "UploadFileOptionFiles", "Luts/sdk/modules/DCloudUniNetwork/UploadFileOptionFiles;", "UploadFileSuccess", "Luts/sdk/modules/DCloudUniNetwork/UploadFileSuccess;", "UploadFileOptions", "Luts/sdk/modules/DCloudUniNetwork/UploadFileOptions;", "OnProgressUpdateResult", "Luts/sdk/modules/DCloudUniNetwork/OnProgressUpdateResult;", "DownloadFileSuccess", "Luts/sdk/modules/DCloudUniNetwork/DownloadFileSuccess;", "DownloadFileOptions", "Luts/sdk/modules/DCloudUniNetwork/DownloadFileOptions;", "OnProgressDownloadResult", "Luts/sdk/modules/DCloudUniNetwork/OnProgressDownloadResult;", "RequestFail", "Luts/sdk/modules/DCloudUniNetwork/RequestFail;", "RequestTask", "Luts/sdk/modules/DCloudUniNetwork/RequestTask;", "UploadFileFail", "Luts/sdk/modules/DCloudUniNetwork/UploadFileFail;", "UploadTask", "Luts/sdk/modules/DCloudUniNetwork/UploadTask;", "DownloadFileFail", "Luts/sdk/modules/DCloudUniNetwork/DownloadFileFail;", "DownloadTask", "Luts/sdk/modules/DCloudUniNetwork/DownloadTask;", "RequestMethod", "Luts/sdk/modules/DCloudUniNetwork/RequestMethod;", "", "RequestErrorCode", "Luts/sdk/modules/DCloudUniNetwork/RequestErrorCode;", "", "DownloadFileComplete", "Luts/sdk/modules/DCloudUniNetwork/DownloadFileComplete;", "", AbsURIAdapter.REQUEST, "Lio/dcloud/uniapp/extapi/RequestTask;", "options", "Lio/dcloud/uniapp/extapi/RequestOptions;", "uploadFile", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Luts/sdk/modules/DCloudUniNetwork/UploadFile;", "getUploadFile", "()Lkotlin/jvm/functions/Function1;", "downloadFile", "Luts/sdk/modules/DCloudUniNetwork/DownloadFile;", "getDownloadFile", "uni-network_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UniNetworkKt {
    private static final Function1<UploadFileOptions, UploadTask> uploadFile = IndexKt.getUploadFile();
    private static final Function1<DownloadFileOptions, DownloadTask> downloadFile = IndexKt.getDownloadFile();

    public static final /* synthetic */ <T> RequestTask request(RequestOptions<T> options) {
        Intrinsics.checkNotNullParameter(options, "options");
        UTSAndroid uTSAndroid = UTSAndroid.INSTANCE;
        Intrinsics.needClassReification();
        Type type = new TypeToken<T>() { // from class: io.dcloud.uniapp.extapi.UniNetworkKt$request$$inlined$getGenericType$1
        }.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        UTSAndroid uTSAndroid2 = UTSAndroid.INSTANCE;
        Intrinsics.needClassReification();
        String name = new TypeToken<T>() { // from class: io.dcloud.uniapp.extapi.UniNetworkKt$request$$inlined$getGenericClassName$1
        }.getRawType().getName();
        Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
        return NetworkManager.INSTANCE.getInstance().request(options, new RequestNetworkListener(options, type, name));
    }

    public static final Function1<UploadFileOptions, UploadTask> getUploadFile() {
        return uploadFile;
    }

    public static final Function1<DownloadFileOptions, DownloadTask> getDownloadFile() {
        return downloadFile;
    }
}
