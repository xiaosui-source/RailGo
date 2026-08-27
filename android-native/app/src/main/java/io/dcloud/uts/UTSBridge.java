package io.dcloud.uts;

import io.dcloud.feature.utsplugin.ProxyModule;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UTSBridge.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0001¨\u0006\u0007"}, d2 = {"Lio/dcloud/uts/UTSBridge;", "", "<init>", "()V", "registerJavaScriptClassInstance", "", "nativeInstance", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UTSBridge {
    public static final UTSBridge INSTANCE = new UTSBridge();

    private UTSBridge() {
    }

    public final int registerJavaScriptClassInstance(java.lang.Object nativeInstance) {
        Intrinsics.checkNotNullParameter(nativeInstance, "nativeInstance");
        ProxyModule.INSTANCE.setInstanceDynamicId(ProxyModule.INSTANCE.getInstanceDynamicId() + 1);
        ProxyModule.INSTANCE.getUtsInstances().put(Integer.valueOf(ProxyModule.INSTANCE.getInstanceDynamicId()), nativeInstance);
        return ProxyModule.INSTANCE.getInstanceDynamicId();
    }
}
