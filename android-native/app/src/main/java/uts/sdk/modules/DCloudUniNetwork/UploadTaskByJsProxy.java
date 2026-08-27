package uts.sdk.modules.DCloudUniNetwork;

import com.taobao.weex.bridge.WXBridgeManager;
import io.dcloud.uts.UTSCallback;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u001a\u0010\u0006\u001a\u00020\u0003X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0005¨\u0006\u000f"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/UploadTaskByJsProxy;", "", "ins", "Luts/sdk/modules/DCloudUniNetwork/UploadTask;", "<init>", "(Luts/sdk/modules/DCloudUniNetwork/UploadTask;)V", "__instance", "get__instance", "()Luts/sdk/modules/DCloudUniNetwork/UploadTask;", "set__instance", "abortByJs", "", "onProgressUpdateByJs", WXBridgeManager.METHOD_CALLBACK, "Lio/dcloud/uts/UTSCallback;", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class UploadTaskByJsProxy {
    public UploadTask __instance;

    public UploadTask get__instance() {
        UploadTask uploadTask = this.__instance;
        if (uploadTask != null) {
            return uploadTask;
        }
        Intrinsics.throwUninitializedPropertyAccessException("__instance");
        return null;
    }

    public void set__instance(UploadTask uploadTask) {
        Intrinsics.checkNotNullParameter(uploadTask, "<set-?>");
        this.__instance = uploadTask;
    }

    public UploadTaskByJsProxy(UploadTask ins) {
        Intrinsics.checkNotNullParameter(ins, "ins");
        set__instance(ins);
    }

    public void abortByJs() {
        get__instance().abort();
    }

    public void onProgressUpdateByJs(final UTSCallback callback) {
        Object fnJS;
        Intrinsics.checkNotNullParameter(callback, "callback");
        UploadTask uploadTask = get__instance();
        if (callback.getFnJS() != null) {
            fnJS = callback.getFnJS();
        } else {
            callback.setFnJS(new Function1() { // from class: uts.sdk.modules.DCloudUniNetwork.UploadTaskByJsProxy$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object invoke2(Object obj) {
                    return UploadTaskByJsProxy.onProgressUpdateByJs$lambda$0(callback, (OnProgressUpdateResult) obj);
                }
            });
            fnJS = callback.getFnJS();
        }
        Intrinsics.checkNotNull(fnJS, "null cannot be cast to non-null type kotlin.Function1<@[ParameterName(name = \"result\")] uts.sdk.modules.DCloudUniNetwork.OnProgressUpdateResult, kotlin.Unit>");
        uploadTask.onProgressUpdate((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(fnJS, 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onProgressUpdateByJs$lambda$0(UTSCallback uTSCallback, OnProgressUpdateResult onProgressUpdateResult) throws SecurityException {
        uTSCallback.invoke(onProgressUpdateResult);
        return Unit.INSTANCE;
    }
}
