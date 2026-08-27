package uts.sdk.modules.DCloudUniNetwork;

import androidx.core.app.NotificationCompat;
import io.dcloud.uts.Map;
import io.dcloud.uts.NumberKt;
import io.dcloud.uts.UTSAndroid;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.TypeIntrinsics;
import okhttp3.Call;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0004\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B\u0013\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u001b\u001a\u00020\u0010H\u0016J/\u0010\u001c\u001a\u00020\u00072%\u0010\u001d\u001a!\u0012\u0013\u0012\u00110\f¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u00100\u000bj\u0002`\u0011H\u0016J\u0012\u0010\u001e\u001a\u00020\u00102\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J/\u0010!\u001a\u00020\u00072%\u0010\u001d\u001a!\u0012\u0013\u0012\u00110\u0017¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u00100\u000bj\u0002`\u0018H\u0016J\u0012\u0010\"\u001a\u00020\u00102\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000RE\u0010\t\u001a-\u0012\u0004\u0012\u00020\u0007\u0012#\u0012!\u0012\u0013\u0012\u00110\f¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u00100\u000bj\u0002`\u00110\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015RE\u0010\u0016\u001a-\u0012\u0004\u0012\u00020\u0007\u0012#\u0012!\u0012\u0013\u0012\u00110\u0017¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u00100\u000bj\u0002`\u00180\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0013\"\u0004\b\u001a\u0010\u0015¨\u0006#"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/NetworkRequestTaskImpl;", "Luts/sdk/modules/DCloudUniNetwork/RequestTask;", NotificationCompat.CATEGORY_CALL, "Lokhttp3/Call;", "<init>", "(Lokhttp3/Call;)V", "headersReceivedListenerId", "", "chunkReceivedListenerId", "headersReceivedListeners", "Lio/dcloud/uts/Map;", "Lkotlin/Function1;", "Luts/sdk/modules/DCloudUniNetwork/RequestTaskOnHeadersReceivedListenerResult;", "Lkotlin/ParameterName;", "name", "result", "", "Luts/sdk/modules/DCloudUniNetwork/RequestTaskOnHeadersReceivedCallback;", "getHeadersReceivedListeners", "()Lio/dcloud/uts/Map;", "setHeadersReceivedListeners", "(Lio/dcloud/uts/Map;)V", "chunkReceivedListeners", "Luts/sdk/modules/DCloudUniNetwork/RequestTaskOnChunkReceivedListenerResult;", "Luts/sdk/modules/DCloudUniNetwork/RequestTaskOnChunkReceivedCallback;", "getChunkReceivedListeners", "setChunkReceivedListeners", "abort", "onHeadersReceived", "listener", "offHeadersReceived", "id", "", "onChunkReceived", "offChunkReceived", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class NetworkRequestTaskImpl implements RequestTask {
    private Call call;
    private Number headersReceivedListenerId = (Number) 0;
    private Number chunkReceivedListenerId = (Number) 0;
    private Map<Number, Function1<RequestTaskOnHeadersReceivedListenerResult, Unit>> headersReceivedListeners = new Map<>();
    private Map<Number, Function1<RequestTaskOnChunkReceivedListenerResult, Unit>> chunkReceivedListeners = new Map<>();

    public Map<Number, Function1<RequestTaskOnHeadersReceivedListenerResult, Unit>> getHeadersReceivedListeners() {
        return this.headersReceivedListeners;
    }

    public void setHeadersReceivedListeners(Map<Number, Function1<RequestTaskOnHeadersReceivedListenerResult, Unit>> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        this.headersReceivedListeners = map;
    }

    public Map<Number, Function1<RequestTaskOnChunkReceivedListenerResult, Unit>> getChunkReceivedListeners() {
        return this.chunkReceivedListeners;
    }

    public void setChunkReceivedListeners(Map<Number, Function1<RequestTaskOnChunkReceivedListenerResult, Unit>> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        this.chunkReceivedListeners = map;
    }

    public NetworkRequestTaskImpl(Call call) {
        this.call = call;
    }

    @Override // uts.sdk.modules.DCloudUniNetwork.RequestTask
    public void abort() {
        Call call = this.call;
        if (call == null || call == null) {
            return;
        }
        call.cancel();
    }

    @Override // uts.sdk.modules.DCloudUniNetwork.RequestTask
    public Number onHeadersReceived(Function1<? super RequestTaskOnHeadersReceivedListenerResult, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.headersReceivedListenerId = NumberKt.inc(this.headersReceivedListenerId);
        getHeadersReceivedListeners().put(this.headersReceivedListenerId, listener);
        return this.headersReceivedListenerId;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [T, java.lang.Integer] */
    @Override // uts.sdk.modules.DCloudUniNetwork.RequestTask
    public void offHeadersReceived(final Object id) {
        if (id != null) {
            if (Intrinsics.areEqual(UTSAndroid.INSTANCE.typeof(id), "function")) {
                final Ref.ObjectRef objectRef = new Ref.ObjectRef();
                objectRef.element = -1;
                getHeadersReceivedListeners().forEach(new Function2() { // from class: uts.sdk.modules.DCloudUniNetwork.NetworkRequestTaskImpl$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return NetworkRequestTaskImpl.offHeadersReceived$lambda$0(id, objectRef, (Function1) obj, (Number) obj2);
                    }
                });
                if (NumberKt.numberEquals(objectRef.element, -1)) {
                    return;
                }
                getHeadersReceivedListeners().delete(objectRef.element);
                return;
            }
            getHeadersReceivedListeners().delete((Number) id);
            return;
        }
        setHeadersReceivedListeners(new Map<>());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final Unit offHeadersReceived$lambda$0(Object obj, Ref.ObjectRef<Number> objectRef, Function1<? super RequestTaskOnHeadersReceivedListenerResult, Unit> function1, Number number) {
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Function1<@[ParameterName(name = \"result\")] uts.sdk.modules.DCloudUniNetwork.RequestTaskOnHeadersReceivedListenerResult, kotlin.Unit>");
        if (Intrinsics.areEqual((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(obj, 1), function1)) {
            objectRef.element = number;
        }
        return Unit.INSTANCE;
    }

    @Override // uts.sdk.modules.DCloudUniNetwork.RequestTask
    public Number onChunkReceived(Function1<? super RequestTaskOnChunkReceivedListenerResult, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.chunkReceivedListenerId = NumberKt.inc(this.chunkReceivedListenerId);
        getChunkReceivedListeners().put(this.chunkReceivedListenerId, listener);
        return this.chunkReceivedListenerId;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v2, types: [T, java.lang.Integer] */
    @Override // uts.sdk.modules.DCloudUniNetwork.RequestTask
    public void offChunkReceived(final Object id) {
        if (id != null) {
            if (Intrinsics.areEqual(UTSAndroid.INSTANCE.typeof(id), "function")) {
                final Ref.ObjectRef objectRef = new Ref.ObjectRef();
                objectRef.element = -1;
                getChunkReceivedListeners().forEach(new Function2() { // from class: uts.sdk.modules.DCloudUniNetwork.NetworkRequestTaskImpl$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return NetworkRequestTaskImpl.offChunkReceived$lambda$1(id, objectRef, (Function1) obj, (Number) obj2);
                    }
                });
                getChunkReceivedListeners().delete(objectRef.element);
                return;
            }
            getChunkReceivedListeners().delete((Number) id);
            return;
        }
        setChunkReceivedListeners(new Map<>());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final Unit offChunkReceived$lambda$1(Object obj, Ref.ObjectRef<Number> objectRef, Function1<? super RequestTaskOnChunkReceivedListenerResult, Unit> function1, Number number) {
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Function1<@[ParameterName(name = \"result\")] uts.sdk.modules.DCloudUniNetwork.RequestTaskOnChunkReceivedListenerResult, kotlin.Unit>");
        if (Intrinsics.areEqual((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(obj, 1), function1)) {
            objectRef.element = number;
        }
        return Unit.INSTANCE;
    }
}
