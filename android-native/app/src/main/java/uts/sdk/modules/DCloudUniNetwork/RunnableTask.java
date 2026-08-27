package uts.sdk.modules.DCloudUniNetwork;

import android.os.Handler;
import android.os.Looper;
import com.taobao.weex.bridge.WXBridgeManager;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B!\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0004\b\u0007\u0010\bJ\b\u0010\t\u001a\u00020\u0006H\u0016J\b\u0010\n\u001a\u00020\u0006H\u0016R\u0016\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/RunnableTask;", "Ljava/lang/Runnable;", "looper", "Landroid/os/Looper;", WXBridgeManager.METHOD_CALLBACK, "Lkotlin/Function0;", "", "<init>", "(Landroid/os/Looper;Lkotlin/jvm/functions/Function0;)V", "run", "execute", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class RunnableTask implements Runnable {
    private Function0<Unit> callback;
    private Looper looper;

    public RunnableTask(Looper looper, Function0<Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.looper = looper;
        this.callback = callback;
    }

    @Override // java.lang.Runnable
    public void run() {
        Function0<Unit> function0 = this.callback;
        if (function0 != null) {
            function0.invoke();
        }
    }

    public void execute() {
        if (this.looper == null) {
            run();
            return;
        }
        Looper looper = this.looper;
        Intrinsics.checkNotNull(looper);
        Intrinsics.checkNotNull(looper);
        new Handler(looper).post(this);
    }
}
