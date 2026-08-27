package uts.sdk.modules.DCloudUniCreateRequestPermissionListener;

import io.dcloud.uts.UTSBridge;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;

/* compiled from: index.kt */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\u001a\u0006\u0010\u0011\u001a\u00020\u0012\"\u001b\u0010\r\u001a\f\u0012\u0004\u0012\u00020\f0\u000bj\u0002`\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010*L\u0010\u0000\"#\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00030\u0002¢\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00070\u00012#\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00030\u0002¢\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00070\u0001*L\u0010\b\"#\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00030\u0002¢\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00070\u00012#\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00030\u0002¢\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00070\u0001*L\u0010\t\"#\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00030\u0002¢\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00070\u00012#\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00030\u0002¢\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00070\u0001*\u0016\u0010\n\"\b\u0012\u0004\u0012\u00020\f0\u000b2\b\u0012\u0004\u0012\u00020\f0\u000b¨\u0006\u0013"}, d2 = {"RequestPermissionListenerRequestCallback", "Lkotlin/Function1;", "Lio/dcloud/uts/UTSArray;", "", "Lkotlin/ParameterName;", "name", "permissions", "", "RequestPermissionListenerConfirmCallback", "RequestPermissionListenerCompleteCallback", "CreateRequestPermissionListener", "Lkotlin/Function0;", "Luts/sdk/modules/DCloudUniCreateRequestPermissionListener/RequestPermissionListener;", "createRequestPermissionListener", "Luts/sdk/modules/DCloudUniCreateRequestPermissionListener/CreateRequestPermissionListener;", "getCreateRequestPermissionListener", "()Lkotlin/jvm/functions/Function0;", "createRequestPermissionListenerByJs", "", "uni-createRequestPermissionListener_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class IndexKt {
    private static final Function0<RequestPermissionListener> createRequestPermissionListener = new Function0() { // from class: uts.sdk.modules.DCloudUniCreateRequestPermissionListener.IndexKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return IndexKt.createRequestPermissionListener$lambda$0();
        }
    };

    public static final Function0<RequestPermissionListener> getCreateRequestPermissionListener() {
        return createRequestPermissionListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final RequestPermissionListener createRequestPermissionListener$lambda$0() {
        return new AndroidPermissionRequestManager();
    }

    public static final int createRequestPermissionListenerByJs() {
        return UTSBridge.INSTANCE.registerJavaScriptClassInstance(new RequestPermissionListenerByJsProxy(createRequestPermissionListener.invoke()));
    }
}
