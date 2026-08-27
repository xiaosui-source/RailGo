package uts.sdk.modules.DCloudUniNetwork;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0004\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J/\u0010\u0004\u001a\u00020\u00052%\u0010\u0006\u001a!\u0012\u0013\u0012\u00110\b¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\u00030\u0007j\u0002`\fH&J\u0012\u0010\r\u001a\u00020\u00032\b\u0010\u0006\u001a\u0004\u0018\u00010\u0001H&J/\u0010\u000e\u001a\u00020\u00052%\u0010\u0006\u001a!\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\u00030\u0007j\u0002`\u0010H&J\u0012\u0010\u0011\u001a\u00020\u00032\b\u0010\u0006\u001a\u0004\u0018\u00010\u0001H&¨\u0006\u0012À\u0006\u0003"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/RequestTask;", "", "abort", "", "onChunkReceived", "", "listener", "Lkotlin/Function1;", "Luts/sdk/modules/DCloudUniNetwork/RequestTaskOnChunkReceivedListenerResult;", "Lkotlin/ParameterName;", "name", "result", "Luts/sdk/modules/DCloudUniNetwork/RequestTaskOnChunkReceivedCallback;", "offChunkReceived", "onHeadersReceived", "Luts/sdk/modules/DCloudUniNetwork/RequestTaskOnHeadersReceivedListenerResult;", "Luts/sdk/modules/DCloudUniNetwork/RequestTaskOnHeadersReceivedCallback;", "offHeadersReceived", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface RequestTask {
    void abort();

    void offChunkReceived(Object listener);

    void offHeadersReceived(Object listener);

    Number onChunkReceived(Function1<? super RequestTaskOnChunkReceivedListenerResult, Unit> listener);

    Number onHeadersReceived(Function1<? super RequestTaskOnHeadersReceivedListenerResult, Unit> listener);
}
