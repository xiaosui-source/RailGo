package uts.sdk.modules.DCloudUniCreateRequestPermissionListener;

import com.taobao.weex.bridge.WXBridgeManager;
import io.dcloud.uts.UTSArray;
import io.dcloud.uts.UTSCallback;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u0010\u001a\u00020\u000bH\u0016R\u001a\u0010\u0006\u001a\u00020\u0003X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0005¨\u0006\u0011"}, d2 = {"Luts/sdk/modules/DCloudUniCreateRequestPermissionListener/RequestPermissionListenerByJsProxy;", "", "ins", "Luts/sdk/modules/DCloudUniCreateRequestPermissionListener/RequestPermissionListener;", "<init>", "(Luts/sdk/modules/DCloudUniCreateRequestPermissionListener/RequestPermissionListener;)V", "__instance", "get__instance", "()Luts/sdk/modules/DCloudUniCreateRequestPermissionListener/RequestPermissionListener;", "set__instance", "onRequestByJs", "", WXBridgeManager.METHOD_CALLBACK, "Lio/dcloud/uts/UTSCallback;", "onConfirmByJs", "onCompleteByJs", "stopByJs", "uni-createRequestPermissionListener_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class RequestPermissionListenerByJsProxy {
    public RequestPermissionListener __instance;

    public RequestPermissionListener get__instance() {
        RequestPermissionListener requestPermissionListener = this.__instance;
        if (requestPermissionListener != null) {
            return requestPermissionListener;
        }
        Intrinsics.throwUninitializedPropertyAccessException("__instance");
        return null;
    }

    public void set__instance(RequestPermissionListener requestPermissionListener) {
        Intrinsics.checkNotNullParameter(requestPermissionListener, "<set-?>");
        this.__instance = requestPermissionListener;
    }

    public RequestPermissionListenerByJsProxy(RequestPermissionListener ins) {
        Intrinsics.checkNotNullParameter(ins, "ins");
        set__instance(ins);
    }

    public void onRequestByJs(final UTSCallback callback) {
        Object fnJS;
        Intrinsics.checkNotNullParameter(callback, "callback");
        RequestPermissionListener requestPermissionListener = get__instance();
        if (callback.getFnJS() != null) {
            fnJS = callback.getFnJS();
        } else {
            callback.setFnJS(new Function1() { // from class: uts.sdk.modules.DCloudUniCreateRequestPermissionListener.RequestPermissionListenerByJsProxy$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object invoke2(Object obj) {
                    return RequestPermissionListenerByJsProxy.onRequestByJs$lambda$0(callback, (UTSArray) obj);
                }
            });
            fnJS = callback.getFnJS();
        }
        Intrinsics.checkNotNull(fnJS, "null cannot be cast to non-null type kotlin.Function1<@[ParameterName(name = \"permissions\")] io.dcloud.uts.UTSArray<kotlin.String>, kotlin.Unit>");
        requestPermissionListener.onRequest((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(fnJS, 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onRequestByJs$lambda$0(UTSCallback uTSCallback, UTSArray<String> uTSArray) throws SecurityException {
        uTSCallback.invoke(uTSArray);
        return Unit.INSTANCE;
    }

    public void onConfirmByJs(final UTSCallback callback) {
        Object fnJS;
        Intrinsics.checkNotNullParameter(callback, "callback");
        RequestPermissionListener requestPermissionListener = get__instance();
        if (callback.getFnJS() != null) {
            fnJS = callback.getFnJS();
        } else {
            callback.setFnJS(new Function1() { // from class: uts.sdk.modules.DCloudUniCreateRequestPermissionListener.RequestPermissionListenerByJsProxy$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object invoke2(Object obj) {
                    return RequestPermissionListenerByJsProxy.onConfirmByJs$lambda$1(callback, (UTSArray) obj);
                }
            });
            fnJS = callback.getFnJS();
        }
        Intrinsics.checkNotNull(fnJS, "null cannot be cast to non-null type kotlin.Function1<@[ParameterName(name = \"permissions\")] io.dcloud.uts.UTSArray<kotlin.String>, kotlin.Unit>");
        requestPermissionListener.onConfirm((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(fnJS, 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onConfirmByJs$lambda$1(UTSCallback uTSCallback, UTSArray<String> uTSArray) throws SecurityException {
        uTSCallback.invoke(uTSArray);
        return Unit.INSTANCE;
    }

    public void onCompleteByJs(final UTSCallback callback) {
        Object fnJS;
        Intrinsics.checkNotNullParameter(callback, "callback");
        RequestPermissionListener requestPermissionListener = get__instance();
        if (callback.getFnJS() != null) {
            fnJS = callback.getFnJS();
        } else {
            callback.setFnJS(new Function1() { // from class: uts.sdk.modules.DCloudUniCreateRequestPermissionListener.RequestPermissionListenerByJsProxy$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object invoke2(Object obj) {
                    return RequestPermissionListenerByJsProxy.onCompleteByJs$lambda$2(callback, (UTSArray) obj);
                }
            });
            fnJS = callback.getFnJS();
        }
        Intrinsics.checkNotNull(fnJS, "null cannot be cast to non-null type kotlin.Function1<@[ParameterName(name = \"permissions\")] io.dcloud.uts.UTSArray<kotlin.String>, kotlin.Unit>");
        requestPermissionListener.onComplete((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(fnJS, 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCompleteByJs$lambda$2(UTSCallback uTSCallback, UTSArray<String> uTSArray) throws SecurityException {
        uTSCallback.invoke(uTSArray);
        return Unit.INSTANCE;
    }

    public void stopByJs() {
        get__instance().stop();
    }
}
