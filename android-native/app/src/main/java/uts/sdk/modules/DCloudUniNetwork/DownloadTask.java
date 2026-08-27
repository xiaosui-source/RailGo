package uts.sdk.modules.DCloudUniNetwork;

import com.taobao.weex.bridge.WXBridgeManager;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J/\u0010\u0004\u001a\u00020\u00032%\u0010\u0005\u001a!\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u00030\u0006j\u0002`\u000bH&¨\u0006\fÀ\u0006\u0003"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/DownloadTask;", "", "abort", "", "onProgressUpdate", WXBridgeManager.METHOD_CALLBACK, "Lkotlin/Function1;", "Luts/sdk/modules/DCloudUniNetwork/OnProgressDownloadResult;", "Lkotlin/ParameterName;", "name", "result", "Luts/sdk/modules/DCloudUniNetwork/DownloadFileProgressUpdateCallback;", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface DownloadTask {
    void abort();

    void onProgressUpdate(Function1<? super OnProgressDownloadResult, Unit> callback);
}
