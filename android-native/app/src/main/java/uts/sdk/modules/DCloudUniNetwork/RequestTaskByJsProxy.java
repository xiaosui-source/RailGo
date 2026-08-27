package uts.sdk.modules.DCloudUniNetwork;

import io.dcloud.uts.UTSCallback;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0004\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0012\u0010\u0010\u001a\u00020\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u0016J\u0010\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0012\u0010\u0012\u001a\u00020\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u0016R\u001a\u0010\u0006\u001a\u00020\u0003X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0005¨\u0006\u0013"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/RequestTaskByJsProxy;", "", "ins", "Luts/sdk/modules/DCloudUniNetwork/RequestTask;", "<init>", "(Luts/sdk/modules/DCloudUniNetwork/RequestTask;)V", "__instance", "get__instance", "()Luts/sdk/modules/DCloudUniNetwork/RequestTask;", "set__instance", "abortByJs", "", "onChunkReceivedByJs", "", "listener", "Lio/dcloud/uts/UTSCallback;", "offChunkReceivedByJs", "onHeadersReceivedByJs", "offHeadersReceivedByJs", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class RequestTaskByJsProxy {
    public RequestTask __instance;

    public RequestTask get__instance() {
        RequestTask requestTask = this.__instance;
        if (requestTask != null) {
            return requestTask;
        }
        Intrinsics.throwUninitializedPropertyAccessException("__instance");
        return null;
    }

    public void set__instance(RequestTask requestTask) {
        Intrinsics.checkNotNullParameter(requestTask, "<set-?>");
        this.__instance = requestTask;
    }

    public RequestTaskByJsProxy(RequestTask ins) {
        Intrinsics.checkNotNullParameter(ins, "ins");
        set__instance(ins);
    }

    public void abortByJs() {
        get__instance().abort();
    }

    public Number onChunkReceivedByJs(final UTSCallback listener) {
        Object fnJS;
        Intrinsics.checkNotNullParameter(listener, "listener");
        RequestTask requestTask = get__instance();
        if (listener.getFnJS() != null) {
            fnJS = listener.getFnJS();
        } else {
            listener.setFnJS(new Function1() { // from class: uts.sdk.modules.DCloudUniNetwork.RequestTaskByJsProxy$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object invoke2(Object obj) {
                    return RequestTaskByJsProxy.onChunkReceivedByJs$lambda$0(listener, (RequestTaskOnChunkReceivedListenerResult) obj);
                }
            });
            fnJS = listener.getFnJS();
        }
        Intrinsics.checkNotNull(fnJS, "null cannot be cast to non-null type kotlin.Function1<@[ParameterName(name = \"result\")] uts.sdk.modules.DCloudUniNetwork.RequestTaskOnChunkReceivedListenerResult, kotlin.Unit>");
        return requestTask.onChunkReceived((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(fnJS, 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onChunkReceivedByJs$lambda$0(UTSCallback uTSCallback, RequestTaskOnChunkReceivedListenerResult requestTaskOnChunkReceivedListenerResult) throws SecurityException {
        uTSCallback.invoke(requestTaskOnChunkReceivedListenerResult);
        return Unit.INSTANCE;
    }

    public void offChunkReceivedByJs(Object listener) {
        get__instance().offChunkReceived(listener);
    }

    public Number onHeadersReceivedByJs(final UTSCallback listener) {
        Object fnJS;
        Intrinsics.checkNotNullParameter(listener, "listener");
        RequestTask requestTask = get__instance();
        if (listener.getFnJS() != null) {
            fnJS = listener.getFnJS();
        } else {
            listener.setFnJS(new Function1() { // from class: uts.sdk.modules.DCloudUniNetwork.RequestTaskByJsProxy$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object invoke2(Object obj) {
                    return RequestTaskByJsProxy.onHeadersReceivedByJs$lambda$1(listener, (RequestTaskOnHeadersReceivedListenerResult) obj);
                }
            });
            fnJS = listener.getFnJS();
        }
        Intrinsics.checkNotNull(fnJS, "null cannot be cast to non-null type kotlin.Function1<@[ParameterName(name = \"result\")] uts.sdk.modules.DCloudUniNetwork.RequestTaskOnHeadersReceivedListenerResult, kotlin.Unit>");
        return requestTask.onHeadersReceived((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(fnJS, 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onHeadersReceivedByJs$lambda$1(UTSCallback uTSCallback, RequestTaskOnHeadersReceivedListenerResult requestTaskOnHeadersReceivedListenerResult) throws SecurityException {
        uTSCallback.invoke(requestTaskOnHeadersReceivedListenerResult);
        return Unit.INSTANCE;
    }

    public void offHeadersReceivedByJs(Object listener) {
        get__instance().offHeadersReceived(listener);
    }
}
