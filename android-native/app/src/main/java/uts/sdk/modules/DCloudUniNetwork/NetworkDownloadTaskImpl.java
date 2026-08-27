package uts.sdk.modules.DCloudUniNetwork;

import androidx.core.app.NotificationCompat;
import io.dcloud.common.constant.AbsoluteConst;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;

/* compiled from: index.kt */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u001b\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\b\u0010\b\u001a\u00020\tH\u0016J/\u0010\n\u001a\u00020\t2%\u0010\u000b\u001a!\u0012\u0013\u0012\u00110\r¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\t0\fj\u0002`\u0011H\u0016R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/NetworkDownloadTaskImpl;", "Luts/sdk/modules/DCloudUniNetwork/DownloadTask;", NotificationCompat.CATEGORY_CALL, "Lokhttp3/Call;", "listener", "Luts/sdk/modules/DCloudUniNetwork/NetworkDownloadFileListener;", "<init>", "(Lokhttp3/Call;Luts/sdk/modules/DCloudUniNetwork/NetworkDownloadFileListener;)V", "abort", "", "onProgressUpdate", AbsoluteConst.JSON_KEY_OPTION, "Lkotlin/Function1;", "Luts/sdk/modules/DCloudUniNetwork/OnProgressDownloadResult;", "Lkotlin/ParameterName;", "name", "result", "Luts/sdk/modules/DCloudUniNetwork/DownloadFileProgressUpdateCallback;", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class NetworkDownloadTaskImpl implements DownloadTask {
    private Call call;
    private NetworkDownloadFileListener listener;

    public NetworkDownloadTaskImpl(Call call, NetworkDownloadFileListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.call = call;
        this.listener = listener;
    }

    @Override // uts.sdk.modules.DCloudUniNetwork.DownloadTask
    public void abort() {
        Call call = this.call;
        if (call == null || call == null) {
            return;
        }
        call.cancel();
    }

    @Override // uts.sdk.modules.DCloudUniNetwork.DownloadTask
    public void onProgressUpdate(Function1<? super OnProgressDownloadResult, Unit> option) {
        Intrinsics.checkNotNullParameter(option, "option");
        NetworkDownloadFileListener networkDownloadFileListener = this.listener;
        if (networkDownloadFileListener != null) {
            networkDownloadFileListener.getProgressListeners().add(option);
        }
    }
}
