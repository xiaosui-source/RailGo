package uts.sdk.modules.DCloudUniNetwork;

import com.taobao.weex.bridge.WXBridgeManager;
import io.dcloud.uts.UTSCallback;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u001a\u0010\u0006\u001a\u00020\u0003X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0005¨\u0006\u000f"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/DownloadTaskByJsProxy;", "", "ins", "Luts/sdk/modules/DCloudUniNetwork/DownloadTask;", "<init>", "(Luts/sdk/modules/DCloudUniNetwork/DownloadTask;)V", "__instance", "get__instance", "()Luts/sdk/modules/DCloudUniNetwork/DownloadTask;", "set__instance", "abortByJs", "", "onProgressUpdateByJs", WXBridgeManager.METHOD_CALLBACK, "Lio/dcloud/uts/UTSCallback;", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class DownloadTaskByJsProxy {
    public DownloadTask __instance;

    public DownloadTask get__instance() {
        DownloadTask downloadTask = this.__instance;
        if (downloadTask != null) {
            return downloadTask;
        }
        Intrinsics.throwUninitializedPropertyAccessException("__instance");
        return null;
    }

    public void set__instance(DownloadTask downloadTask) {
        Intrinsics.checkNotNullParameter(downloadTask, "<set-?>");
        this.__instance = downloadTask;
    }

    public DownloadTaskByJsProxy(DownloadTask ins) {
        Intrinsics.checkNotNullParameter(ins, "ins");
        set__instance(ins);
    }

    public void abortByJs() {
        get__instance().abort();
    }

    public void onProgressUpdateByJs(final UTSCallback callback) {
        Object fnJS;
        Intrinsics.checkNotNullParameter(callback, "callback");
        DownloadTask downloadTask = get__instance();
        if (callback.getFnJS() != null) {
            fnJS = callback.getFnJS();
        } else {
            callback.setFnJS(new Function1() { // from class: uts.sdk.modules.DCloudUniNetwork.DownloadTaskByJsProxy$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object invoke2(Object obj) {
                    return DownloadTaskByJsProxy.onProgressUpdateByJs$lambda$0(callback, (OnProgressDownloadResult) obj);
                }
            });
            fnJS = callback.getFnJS();
        }
        Intrinsics.checkNotNull(fnJS, "null cannot be cast to non-null type kotlin.Function1<@[ParameterName(name = \"result\")] uts.sdk.modules.DCloudUniNetwork.OnProgressDownloadResult, kotlin.Unit>");
        downloadTask.onProgressUpdate((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(fnJS, 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onProgressUpdateByJs$lambda$0(UTSCallback uTSCallback, OnProgressDownloadResult onProgressDownloadResult) throws SecurityException {
        uTSCallback.invoke(onProgressDownloadResult);
        return Unit.INSTANCE;
    }
}
