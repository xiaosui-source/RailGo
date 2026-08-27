package io.dcloud.uts.task;

import android.os.Handler;
import android.os.Looper;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UTSTaskDispatcher.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B\u0015\b\u0016\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0004\u0010\u0005J9\u0010\n\u001a\u00020\u000b2#\u0010\f\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0001¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u000b0\r2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u0016R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0005¨\u0006\u0011"}, d2 = {"Lio/dcloud/uts/task/UTSTaskDispatcher;", "", "looper", "Landroid/os/Looper;", "<init>", "(Landroid/os/Looper;)V", "hostLooper", "getHostLooper", "()Landroid/os/Looper;", "setHostLooper", "async", "", "action", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "param", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class UTSTaskDispatcher {
    private Looper hostLooper;

    public final Looper getHostLooper() {
        return this.hostLooper;
    }

    public final void setHostLooper(Looper looper) {
        this.hostLooper = looper;
    }

    public UTSTaskDispatcher(Looper looper) {
        this.hostLooper = looper;
    }

    public /* synthetic */ UTSTaskDispatcher(Looper looper, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : looper);
    }

    public static /* synthetic */ void async$default(UTSTaskDispatcher uTSTaskDispatcher, Function1 function1, Object obj, int i, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: async");
        }
        if ((i & 2) != 0) {
            obj = null;
        }
        uTSTaskDispatcher.async(function1, obj);
    }

    public void async(final Function1<Object, Unit> action, final Object param) {
        Intrinsics.checkNotNullParameter(action, "action");
        if (this.hostLooper == null) {
            action.invoke(param);
            return;
        }
        Looper looper = this.hostLooper;
        Intrinsics.checkNotNull(looper);
        new Handler(looper).post(new Runnable() { // from class: io.dcloud.uts.task.UTSTaskDispatcher$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                action.invoke(param);
            }
        });
    }
}
