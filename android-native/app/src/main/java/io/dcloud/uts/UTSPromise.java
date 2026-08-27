package io.dcloud.uts;

import androidx.core.app.NotificationCompat;
import com.facebook.common.util.UriUtil;
import com.taobao.weex.bridge.WXBridgeManager;
import io.dcloud.uts.UTSPromise;
import java.util.Iterator;
import kotlin.Function;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;

/* compiled from: UTSPromise.kt */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0004\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u0000 =*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0001=BG\b\u0016\u0012<\u0010\u0003\u001a8\u0012.\u0012,\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u0004¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\b0\u0004¢\u0006\u0004\b\n\u0010\u000bBy\b\u0016\u0012n\u0010\u0003\u001aj\u0012.\u0012,\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u0004¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\t\u00120\u0012.\u0012\u0015\u0012\u0013\u0018\u00010\u0002¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\b0\u0004¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\b0\f¢\u0006\u0004\b\n\u0010\u000fJ\u0014\u0010\u0010\u001a\u00020\b2\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0011H\u0016J\u000e\u0010*\u001a\b\u0012\u0004\u0012\u00028\u00000\u0000H\u0016J\u0019\u0010*\u001a\b\u0012\u0004\u0012\u00028\u00000\u0000\"\u0004\b\u0001\u0010+H\u0017¢\u0006\u0002\b,J2\u0010*\u001a\b\u0012\u0004\u0012\u0002H+0\u0000\"\u0004\b\u0001\u0010+2\f\u0010-\u001a\b\u0012\u0004\u0012\u0002H+0.2\u000e\b\u0002\u0010/\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0011H\u0016J=\u0010*\u001a\b\u0012\u0004\u0012\u0002H+0\u0000\"\u0004\b\u0001\u0010+2\u0012\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H+0\u00000.2\u000e\b\u0002\u0010/\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0011H\u0017¢\u0006\u0002\b0JG\u0010*\u001a\b\u0012\u0004\u0012\u0002H+0\u0000\"\u0004\b\u0001\u0010+2!\u0010-\u001a\u001d\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(1\u0012\u0004\u0012\u0002H+0\u00042\u000e\b\u0002\u0010/\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0011H\u0016JR\u0010*\u001a\b\u0012\u0004\u0012\u0002H+0\u0000\"\u0004\b\u0001\u0010+2'\u0010-\u001a#\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(1\u0012\n\u0012\b\u0012\u0004\u0012\u0002H+0\u00000\u00042\u000e\b\u0002\u0010/\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0011H\u0017¢\u0006\u0002\b2J4\u00103\u001a\b\u0012\u0004\u0012\u0002H+0\u0000\"\u0004\b\u0001\u0010+2\u000e\b\u0002\u0010-\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00112\u000e\b\u0002\u0010/\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0011H\u0016J4\u00104\u001a\b\u0012\u0004\u0012\u0002H+0\u0000\"\u0004\b\u0001\u0010+2\u000e\b\u0002\u0010-\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00112\u000e\b\u0002\u0010/\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0011H\u0002J\u000e\u00105\u001a\b\u0012\u0004\u0012\u00028\u00000\u0000H\u0016J\u0019\u00105\u001a\b\u0012\u0004\u0012\u00028\u00000\u0000\"\u0004\b\u0001\u0010+H\u0017¢\u0006\u0002\b6J\"\u00105\u001a\b\u0012\u0004\u0012\u0002H+0\u0000\"\u0004\b\u0001\u0010+2\f\u0010/\u001a\b\u0012\u0004\u0012\u0002H+0.H\u0016J-\u00105\u001a\b\u0012\u0004\u0012\u0002H+0\u0000\"\u0004\b\u0001\u0010+2\u0012\u0010/\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H+0\u00000.H\u0017¢\u0006\u0002\b7J9\u00105\u001a\b\u0012\u0004\u0012\u0002H+0\u0000\"\u0004\b\u0001\u0010+2#\u0010/\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0002¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(1\u0012\u0004\u0012\u0002H+0\u0004H\u0016JD\u00105\u001a\b\u0012\u0004\u0012\u0002H+0\u0000\"\u0004\b\u0001\u0010+2)\u0010/\u001a%\u0012\u0015\u0012\u0013\u0018\u00010\u0002¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(1\u0012\n\u0012\b\u0012\u0004\u0012\u0002H+0\u00000\u0004H\u0017¢\u0006\u0002\b8J$\u00109\u001a\b\u0012\u0004\u0012\u0002H+0\u0000\"\u0004\b\u0001\u0010+2\u000e\b\u0002\u0010/\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0011H\u0016J\"\u0010:\u001a\b\u0012\u0004\u0012\u0002H+0\u0000\"\u0004\b\u0001\u0010+2\f\u0010/\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0011H\u0002J\u001a\u0010;\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\n\u0010<\u001a\u0006\u0012\u0002\b\u00030\u0011H\u0016R\u001a\u0010\u0012\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0019X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0002X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\"\u0010#\u001a\n\u0012\u0004\u0012\u00020%\u0018\u00010$X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)¨\u0006>"}, d2 = {"Lio/dcloud/uts/UTSPromise;", "T", "", "fn", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "value", "", "resolve", "<init>", "(Lkotlin/jvm/functions/Function1;)V", "Lkotlin/Function2;", "reason", "reject", "(Lkotlin/jvm/functions/Function2;)V", "constructor_origin", "Lkotlin/Function;", "_state", "", "get_state", "()Ljava/lang/Number;", "set_state", "(Ljava/lang/Number;)V", "_handled", "", "get_handled", "()Z", "set_handled", "(Z)V", "_value", "get_value", "()Ljava/lang/Object;", "set_value", "(Ljava/lang/Object;)V", "_deferreds", "Lio/dcloud/uts/UTSArray;", "Lio/dcloud/uts/UTSPromiseHandler;", "get_deferreds", "()Lio/dcloud/uts/UTSArray;", "set_deferreds", "(Lio/dcloud/uts/UTSArray;)V", "then", "R", "then0", "onFulfilled", "Lkotlin/Function0;", "onRejected", "then1", UriUtil.LOCAL_RESOURCE_SCHEME, "then2", "then_origin", "_then", "catch", "catch0", "catch1", "catch2", "catch_origin", "_catch", "finally", WXBridgeManager.METHOD_CALLBACK, "Companion", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UTSPromise<T> {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private UTSArray<UTSPromiseHandler> _deferreds;
    private boolean _handled;
    private Number _state;
    private java.lang.Object _value;

    /* JADX INFO: Access modifiers changed from: private */
    public static final java.lang.Object finally$lambda$2$lambda$1(java.lang.Object obj) {
        return obj;
    }

    public UTSPromise(Function1<? super Function1<? super T, Unit>, Unit> fn) {
        Intrinsics.checkNotNullParameter(fn, "fn");
        this._state = (Number) 0;
        this._deferreds = new UTSArray<>();
        constructor_origin(fn);
    }

    public UTSPromise(Function2<? super Function1<? super T, Unit>, ? super Function1<java.lang.Object, Unit>, Unit> fn) {
        Intrinsics.checkNotNullParameter(fn, "fn");
        this._state = (Number) 0;
        this._deferreds = new UTSArray<>();
        constructor_origin(fn);
    }

    public void constructor_origin(Function<?> fn) {
        Intrinsics.checkNotNullParameter(fn, "fn");
        Intrinsics.checkNotNull(this, "null cannot be cast to non-null type io.dcloud.uts.UTSPromise<*>");
        UTSPromiseKt.doResolveUTSPromise(fn, this);
    }

    public Number get_state() {
        return this._state;
    }

    public void set_state(Number number) {
        Intrinsics.checkNotNullParameter(number, "<set-?>");
        this._state = number;
    }

    public boolean get_handled() {
        return this._handled;
    }

    public void set_handled(boolean z) {
        this._handled = z;
    }

    public java.lang.Object get_value() {
        return this._value;
    }

    public void set_value(java.lang.Object obj) {
        this._value = obj;
    }

    public UTSArray<UTSPromiseHandler> get_deferreds() {
        return this._deferreds;
    }

    public void set_deferreds(UTSArray<UTSPromiseHandler> uTSArray) {
        this._deferreds = uTSArray;
    }

    public UTSPromise<T> then() {
        return (UTSPromise<T>) then_origin(null, null);
    }

    public <R> UTSPromise<T> then0() {
        return then_origin(null, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ UTSPromise then$default(UTSPromise uTSPromise, Function0 function0, Function function, int i, java.lang.Object obj) {
        if ((i & 2) != 0) {
            function = null;
        }
        return uTSPromise.then(function0, (Function<?>) function);
    }

    public <R> UTSPromise<R> then(Function0<? extends R> onFulfilled, Function<?> onRejected) {
        Intrinsics.checkNotNullParameter(onFulfilled, "onFulfilled");
        return then_origin(onFulfilled, onRejected);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ UTSPromise then1$default(UTSPromise uTSPromise, Function0 function0, Function function, int i, java.lang.Object obj) {
        if ((i & 2) != 0) {
            function = null;
        }
        return uTSPromise.then1(function0, function);
    }

    public <R> UTSPromise<R> then1(Function0<UTSPromise<R>> onFulfilled, Function<?> onRejected) {
        Intrinsics.checkNotNullParameter(onFulfilled, "onFulfilled");
        return then_origin(onFulfilled, onRejected);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ UTSPromise then$default(UTSPromise uTSPromise, Function1 function1, Function function, int i, java.lang.Object obj) {
        if ((i & 2) != 0) {
            function = null;
        }
        return uTSPromise.then(function1, (Function<?>) function);
    }

    public <R> UTSPromise<R> then(Function1<? super T, ? extends R> onFulfilled, Function<?> onRejected) {
        Intrinsics.checkNotNullParameter(onFulfilled, "onFulfilled");
        return then_origin(onFulfilled, onRejected);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ UTSPromise then2$default(UTSPromise uTSPromise, Function1 function1, Function function, int i, java.lang.Object obj) {
        if ((i & 2) != 0) {
            function = null;
        }
        return uTSPromise.then2(function1, function);
    }

    public <R> UTSPromise<R> then2(Function1<? super T, UTSPromise<R>> onFulfilled, Function<?> onRejected) {
        Intrinsics.checkNotNullParameter(onFulfilled, "onFulfilled");
        return then_origin(onFulfilled, onRejected);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ UTSPromise then_origin$default(UTSPromise uTSPromise, Function function, Function function2, int i, java.lang.Object obj) {
        if ((i & 1) != 0) {
            function = null;
        }
        if ((i & 2) != 0) {
            function2 = null;
        }
        return uTSPromise.then_origin(function, function2);
    }

    public <R> UTSPromise<R> then_origin(Function<?> onFulfilled, Function<?> onRejected) {
        return _then(onFulfilled, onRejected);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ UTSPromise _then$default(UTSPromise uTSPromise, Function function, Function function2, int i, java.lang.Object obj) {
        if ((i & 1) != 0) {
            function = null;
        }
        if ((i & 2) != 0) {
            function2 = null;
        }
        return uTSPromise._then(function, function2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <R> UTSPromise<R> _then(Function<?> onFulfilled, Function<?> onRejected) {
        UTSPromise<R> uTSPromise = new UTSPromise<>((Function2<? super Function1<? super R, Unit>, ? super Function1<java.lang.Object, Unit>, Unit>) new Function2() { // from class: io.dcloud.uts.UTSPromise$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final java.lang.Object invoke(java.lang.Object obj, java.lang.Object obj2) {
                return UTSPromise._then$lambda$0((Function1) obj, (Function1) obj2);
            }
        });
        Intrinsics.checkNotNull(this, "null cannot be cast to non-null type io.dcloud.uts.UTSPromise<*>");
        UTSPromiseKt.handleUTSPromise(this, new UTSPromiseHandler(onFulfilled, onRejected, uTSPromise));
        return uTSPromise;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <R> Unit _then$lambda$0(Function1<? super R, Unit> function1, Function1<java.lang.Object, Unit> function12) {
        return Unit.INSTANCE;
    }

    /* renamed from: catch, reason: not valid java name */
    public UTSPromise<T> m514catch() {
        return catch_origin$default(this, null, 1, null);
    }

    public <R> UTSPromise<T> catch0() {
        return catch_origin$default(this, null, 1, null);
    }

    /* renamed from: catch, reason: not valid java name */
    public <R> UTSPromise<R> m515catch(Function0<? extends R> onRejected) {
        Intrinsics.checkNotNullParameter(onRejected, "onRejected");
        return catch_origin(onRejected);
    }

    public <R> UTSPromise<R> catch1(Function0<UTSPromise<R>> onRejected) {
        Intrinsics.checkNotNullParameter(onRejected, "onRejected");
        return catch_origin(onRejected);
    }

    /* renamed from: catch, reason: not valid java name */
    public <R> UTSPromise<R> m516catch(Function1<java.lang.Object, ? extends R> onRejected) {
        Intrinsics.checkNotNullParameter(onRejected, "onRejected");
        return catch_origin(onRejected);
    }

    public <R> UTSPromise<R> catch2(Function1<java.lang.Object, UTSPromise<R>> onRejected) {
        Intrinsics.checkNotNullParameter(onRejected, "onRejected");
        return catch_origin(onRejected);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ UTSPromise catch_origin$default(UTSPromise uTSPromise, Function function, int i, java.lang.Object obj) {
        if ((i & 1) != 0) {
            function = null;
        }
        return uTSPromise.catch_origin(function);
    }

    public <R> UTSPromise<R> catch_origin(Function<?> onRejected) {
        return _catch(onRejected);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <R> UTSPromise<R> _catch(Function<?> onRejected) {
        return _then(null, onRejected);
    }

    /* renamed from: finally, reason: not valid java name */
    public UTSPromise<T> m517finally(final Function<?> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        return (UTSPromise<T>) _then(new Function1() { // from class: io.dcloud.uts.UTSPromise$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final java.lang.Object invoke(java.lang.Object obj) {
                return UTSPromise.finally$lambda$2(callback, obj);
            }
        }, new Function1() { // from class: io.dcloud.uts.UTSPromise$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final java.lang.Object invoke(java.lang.Object obj) {
                return UTSPromise.finally$lambda$4(callback, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <T> java.lang.Object finally$lambda$2(Function<?> function, final java.lang.Object obj) {
        return _then$default(INSTANCE._resolve(UTSPromiseKt.callFunction(function, new UTSArray())), new Function0() { // from class: io.dcloud.uts.UTSPromise$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final java.lang.Object invoke() {
                return UTSPromise.finally$lambda$2$lambda$1(obj);
            }
        }, null, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <T> java.lang.Object finally$lambda$4(Function<?> function, final java.lang.Object obj) {
        return _then$default(INSTANCE._resolve(UTSPromiseKt.callFunction(function, new UTSArray())), new Function0() { // from class: io.dcloud.uts.UTSPromise$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final java.lang.Object invoke() {
                return UTSPromise.finally$lambda$4$lambda$3(obj);
            }
        }, null, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final java.lang.Object finally$lambda$4$lambda$3(java.lang.Object obj) {
        return INSTANCE.reject(obj);
    }

    /* compiled from: UTSPromise.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005J\u001f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0005\"\u0004\b\u0001\u0010\u00072\u0006\u0010\b\u001a\u0002H\u0007¢\u0006\u0002\u0010\tJ \u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0005\"\u0004\b\u0001\u0010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0005J\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0005\"\u0004\b\u0001\u0010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0001J\u001e\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0005\"\u0004\b\u0001\u0010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0001H\u0002J\u0018\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0001J,\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00070\u000e0\u0005\"\u0004\b\u0001\u0010\u00072\u0012\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00070\u00050\u000eJ\u001e\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0005\"\u0004\b\u0001\u0010\u00072\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u000eJ&\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0005\"\u0004\b\u0001\u0010\u00072\u0012\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00070\u00050\u000eJ\u001e\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0005\"\u0004\b\u0001\u0010\u00072\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u000eJ&\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0005\"\u0004\b\u0001\u0010\u00072\u0012\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00070\u00050\u000eJ\u001e\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0005\"\u0004\b\u0001\u0010\u00072\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u000eJ2\u0010\u0015\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00070\u00160\u000e0\u0005\"\u0004\b\u0001\u0010\u00072\u0012\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00070\u00050\u000eJ*\u0010\u0017\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00070\u00160\u000e0\u0005\"\u0004\b\u0001\u0010\u00072\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u000eJ\u0012\u0010\u0018\u001a\u00020\u00062\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001aJ\u0010\u0010\u001b\u001a\u00020\u00062\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001¨\u0006\u001d"}, d2 = {"Lio/dcloud/uts/UTSPromise$Companion;", "", "<init>", "()V", "resolve", "Lio/dcloud/uts/UTSPromise;", "", "T", "value", "(Ljava/lang/Object;)Lio/dcloud/uts/UTSPromise;", "resolve_origin", "_resolve", "reject", "all", "Lio/dcloud/uts/UTSArray;", "arr", "all_origin", "race", "race_origin", "any", "any_origin", "allSettled", "Lio/dcloud/uts/UTSPromiseSettledResult;", "allSettled_origin", "_immediateFn", "fn", "Lkotlin/Function;", "_unhandledRejectionFn", NotificationCompat.CATEGORY_ERROR, "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final UTSPromise<Unit> resolve() {
            return resolve_origin$default(UTSPromise.INSTANCE, null, 1, null);
        }

        public final <T> UTSPromise<T> resolve(T value) {
            return UTSPromise.INSTANCE.resolve_origin(value);
        }

        public final <T> UTSPromise<T> resolve(UTSPromise<T> value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return UTSPromise.INSTANCE.resolve_origin(value);
        }

        public static /* synthetic */ UTSPromise resolve_origin$default(Companion companion, java.lang.Object obj, int i, java.lang.Object obj2) {
            if ((i & 1) != 0) {
                obj = null;
            }
            return companion.resolve_origin(obj);
        }

        public final <T> UTSPromise<T> resolve_origin(java.lang.Object value) {
            return UTSPromise.INSTANCE._resolve(value);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final <T> UTSPromise<T> _resolve(final java.lang.Object value) {
            if (value != null && (value instanceof UTSPromise)) {
                return (UTSPromise) value;
            }
            return new UTSPromise<>(new Function2() { // from class: io.dcloud.uts.UTSPromise$Companion$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function2
                public final java.lang.Object invoke(java.lang.Object obj, java.lang.Object obj2) {
                    return UTSPromise.Companion._resolve$lambda$0(value, (Function1) obj, (Function1) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final <T> Unit _resolve$lambda$0(java.lang.Object obj, Function1<? super T, Unit> function1, Function1<java.lang.Object, Unit> function12) throws SecurityException {
            UTSPromiseKt.callFunction(function1, UTSArrayKt.utsArrayOf(obj));
            return Unit.INSTANCE;
        }

        public static /* synthetic */ UTSPromise reject$default(Companion companion, java.lang.Object obj, int i, java.lang.Object obj2) {
            if ((i & 1) != 0) {
                obj = null;
            }
            return companion.reject(obj);
        }

        public final UTSPromise<Unit> reject(final java.lang.Object value) {
            return new UTSPromise<>(new Function2() { // from class: io.dcloud.uts.UTSPromise$Companion$$ExternalSyntheticLambda9
                @Override // kotlin.jvm.functions.Function2
                public final java.lang.Object invoke(java.lang.Object obj, java.lang.Object obj2) {
                    return UTSPromise.Companion.reject$lambda$1(value, (Function1) obj, (Function1) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit reject$lambda$1(java.lang.Object obj, Function1<? super Unit, Unit> function1, Function1<java.lang.Object, Unit> function12) throws SecurityException {
            UTSPromiseKt.callFunction(function12, UTSArrayKt.utsArrayOf(obj));
            return Unit.INSTANCE;
        }

        public final <T> UTSPromise<UTSArray<T>> all(UTSArray<UTSPromise<T>> arr) {
            Intrinsics.checkNotNullParameter(arr, "arr");
            return UTSPromise.INSTANCE.all_origin(arr);
        }

        public final <T> UTSPromise<T> all_origin(final UTSArray<?> arr) {
            Intrinsics.checkNotNullParameter(arr, "arr");
            return new UTSPromise<>(new Function2() { // from class: io.dcloud.uts.UTSPromise$Companion$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final java.lang.Object invoke(java.lang.Object obj, java.lang.Object obj2) {
                    return UTSPromise.Companion.all_origin$lambda$5(arr, (Function1) obj, (Function1) obj2);
                }
            });
        }

        private static final <T> void all_origin$lambda$5$res(final Function1<java.lang.Object, Unit> function1, final UTSArray<java.lang.Object> uTSArray, final Ref.ObjectRef<Number> objectRef, final Function1<? super T, Unit> function12, final Number number, java.lang.Object obj) throws SecurityException {
            try {
                if (obj != null) {
                    try {
                        if (obj instanceof UTSPromise) {
                            ((UTSPromise) obj)._then(new Function1() { // from class: io.dcloud.uts.UTSPromise$Companion$$ExternalSyntheticLambda8
                                @Override // kotlin.jvm.functions.Function1
                                public final java.lang.Object invoke(java.lang.Object obj2) {
                                    return UTSPromise.Companion.all_origin$lambda$5$res$lambda$3(number, function1, uTSArray, objectRef, function12, obj2);
                                }
                            }, function1);
                            return;
                        }
                    } catch (Throwable th) {
                        th = th;
                        UTSPromiseKt.callFunction(function1, UTSArrayKt.utsArrayOf(th));
                        return;
                    }
                }
                Intrinsics.checkNotNull(number, "null cannot be cast to non-null type kotlin.Int");
                uTSArray.set(((Integer) number).intValue(), (int) obj);
                objectRef.element = (T) NumberKt.dec(objectRef.element);
                if (Intrinsics.areEqual((java.lang.Object) objectRef.element, (java.lang.Object) 0)) {
                    UTSPromiseKt.callFunction(function12, UTSArrayKt.utsArrayOf(uTSArray));
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final <T> Unit all_origin$lambda$5$res$lambda$3(Number number, Function1<java.lang.Object, Unit> function1, UTSArray<java.lang.Object> uTSArray, Ref.ObjectRef<Number> objectRef, Function1<? super T, Unit> function12, java.lang.Object obj) throws SecurityException {
            all_origin$lambda$5$res(function1, uTSArray, objectRef, function12, number, obj);
            return Unit.INSTANCE;
        }

        public final <T> UTSPromise<T> race(UTSArray<UTSPromise<T>> arr) {
            Intrinsics.checkNotNullParameter(arr, "arr");
            return UTSPromise.INSTANCE.race_origin(arr);
        }

        public final <T> UTSPromise<T> race_origin(final UTSArray<?> arr) {
            Intrinsics.checkNotNullParameter(arr, "arr");
            return new UTSPromise<>(new Function2() { // from class: io.dcloud.uts.UTSPromise$Companion$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final java.lang.Object invoke(java.lang.Object obj, java.lang.Object obj2) {
                    return UTSPromise.Companion.race_origin$lambda$7(arr, (Function1) obj, (Function1) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final <T> Unit race_origin$lambda$7(UTSArray<?> uTSArray, Function1<? super T, Unit> function1, Function1<java.lang.Object, Unit> function12) {
            Companion companion = UTSPromise.INSTANCE;
            Number length = uTSArray.getLength();
            for (Integer numValueOf = (Number) 0; NumberKt.compareTo(numValueOf, length) < 0; numValueOf = Integer.valueOf(numValueOf.intValue() + 1)) {
                UTSPromise.INSTANCE._resolve(uTSArray.get(numValueOf.intValue()))._then(function1, function12);
            }
            return Unit.INSTANCE;
        }

        public final <T> UTSPromise<T> any(UTSArray<UTSPromise<T>> arr) {
            Intrinsics.checkNotNullParameter(arr, "arr");
            return UTSPromise.INSTANCE.any_origin(arr);
        }

        public final <T> UTSPromise<T> any_origin(final UTSArray<?> arr) {
            Intrinsics.checkNotNullParameter(arr, "arr");
            return new UTSPromise<>(new Function2() { // from class: io.dcloud.uts.UTSPromise$Companion$$ExternalSyntheticLambda7
                @Override // kotlin.jvm.functions.Function2
                public final java.lang.Object invoke(java.lang.Object obj, java.lang.Object obj2) {
                    return UTSPromise.Companion.any_origin$lambda$11(arr, (Function1) obj, (Function1) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final <T> Unit any_origin$lambda$11(UTSArray<?> uTSArray, Function1<? super T, Unit> function1, final Function1<java.lang.Object, Unit> function12) throws SecurityException {
            if (Intrinsics.areEqual((java.lang.Object) uTSArray.getLength(), (java.lang.Object) 0)) {
                UTSPromiseKt.callFunction(function12, new UTSArray());
                return Unit.INSTANCE;
            }
            final UTSArray uTSArray2 = new UTSArray();
            Iterator<?> it = uTSArray.iterator();
            while (it.hasNext()) {
                uTSArray2.push(it.next());
            }
            final UTSArray uTSArray3 = new UTSArray();
            Companion companion = UTSPromise.INSTANCE;
            for (Number numberInc = (Number) 0; NumberKt.compareTo(numberInc, uTSArray2.getLength()) < 0; numberInc = NumberKt.inc(numberInc)) {
                try {
                    Companion companion2 = UTSPromise.INSTANCE;
                    Intrinsics.checkNotNull(numberInc, "null cannot be cast to non-null type kotlin.Int");
                    UTSPromise._then$default(companion2._resolve(uTSArray2.get(((Integer) numberInc).intValue())), function1, null, 2, null)._catch(new Function1() { // from class: io.dcloud.uts.UTSPromise$Companion$$ExternalSyntheticLambda6
                        @Override // kotlin.jvm.functions.Function1
                        public final java.lang.Object invoke(java.lang.Object obj) {
                            return UTSPromise.Companion.any_origin$lambda$11$lambda$10$lambda$9(uTSArray3, uTSArray2, function12, obj);
                        }
                    });
                } catch (Throwable th) {
                    UTSPromiseKt.callFunction(function12, UTSArrayKt.utsArrayOf(th));
                }
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit any_origin$lambda$11$lambda$10$lambda$9(UTSArray<java.lang.Object> uTSArray, UTSArray<java.lang.Object> uTSArray2, Function1<java.lang.Object, Unit> function1, java.lang.Object obj) throws SecurityException {
            uTSArray.push(obj);
            if (Intrinsics.areEqual(uTSArray.getLength(), uTSArray2.getLength())) {
                UTSPromiseKt.callFunction(function1, UTSArrayKt.utsArrayOf(new UTSPromiseAggregateError(uTSArray, "All promises were rejected")));
            }
            return Unit.INSTANCE;
        }

        public final <T> UTSPromise<UTSArray<UTSPromiseSettledResult<T>>> allSettled(UTSArray<UTSPromise<T>> arr) {
            Intrinsics.checkNotNullParameter(arr, "arr");
            return UTSPromise.INSTANCE.allSettled_origin(arr);
        }

        public final <T> UTSPromise<UTSArray<UTSPromiseSettledResult<T>>> allSettled_origin(final UTSArray<?> arr) {
            Intrinsics.checkNotNullParameter(arr, "arr");
            return new UTSPromise<>(new Function2() { // from class: io.dcloud.uts.UTSPromise$Companion$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function2
                public final java.lang.Object invoke(java.lang.Object obj, java.lang.Object obj2) {
                    return UTSPromise.Companion.allSettled_origin$lambda$17(arr, (Function) obj, (Function) obj2);
                }
            });
        }

        private static final <T> void allSettled_origin$lambda$17$res$15(final UTSArray<java.lang.Object> uTSArray, final Ref.ObjectRef<Number> objectRef, final Function<?> function, final Number number, java.lang.Object obj) throws SecurityException {
            if (obj != null && (obj instanceof UTSPromise)) {
                ((UTSPromise) obj)._then(new Function1() { // from class: io.dcloud.uts.UTSPromise$Companion$$ExternalSyntheticLambda10
                    @Override // kotlin.jvm.functions.Function1
                    public final java.lang.Object invoke(java.lang.Object obj2) {
                        return UTSPromise.Companion.allSettled_origin$lambda$17$res$15$lambda$13(number, uTSArray, objectRef, function, obj2);
                    }
                }, new Function1() { // from class: io.dcloud.uts.UTSPromise$Companion$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    public final java.lang.Object invoke(java.lang.Object obj2) {
                        return UTSPromise.Companion.allSettled_origin$lambda$17$res$15$lambda$14(uTSArray, number, objectRef, function, obj2);
                    }
                });
                return;
            }
            Intrinsics.checkNotNull(number, "null cannot be cast to non-null type kotlin.Int");
            uTSArray.set(((Integer) number).intValue(), (int) new UTSPromiseFulfilledResultImpl(obj));
            objectRef.element = (T) NumberKt.dec(objectRef.element);
            if (Intrinsics.areEqual((java.lang.Object) objectRef.element, (java.lang.Object) 0)) {
                UTSPromiseKt.callFunction(function, UTSArrayKt.utsArrayOf(uTSArray));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final <T> Unit allSettled_origin$lambda$17$res$15$lambda$13(Number number, UTSArray<java.lang.Object> uTSArray, Ref.ObjectRef<Number> objectRef, Function<?> function, java.lang.Object obj) throws SecurityException {
            allSettled_origin$lambda$17$res$15(uTSArray, objectRef, function, number, obj);
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit allSettled_origin$lambda$17$res$15$lambda$14(UTSArray<java.lang.Object> uTSArray, Number number, Ref.ObjectRef<Number> objectRef, Function<?> function, java.lang.Object obj) throws SecurityException {
            Intrinsics.checkNotNull(number, "null cannot be cast to non-null type kotlin.Int");
            uTSArray.set(((Integer) number).intValue(), (int) new UTSPromiseRejectedResultImpl(obj));
            objectRef.element = (T) NumberKt.dec(objectRef.element);
            if (Intrinsics.areEqual((java.lang.Object) objectRef.element, (java.lang.Object) 0)) {
                UTSPromiseKt.callFunction(function, UTSArrayKt.utsArrayOf(uTSArray));
            }
            return Unit.INSTANCE;
        }

        public final void _immediateFn(final Function<?> fn) {
            Intrinsics.checkNotNullParameter(fn, "fn");
            UTSTimerKt.setTimeout(new Function0() { // from class: io.dcloud.uts.UTSPromise$Companion$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function0
                public final java.lang.Object invoke() {
                    return UTSPromise.Companion._immediateFn$lambda$18(fn);
                }
            }, (Number) 0);
        }

        public final void _unhandledRejectionFn(java.lang.Object err) {
            console.warn("Possible Unhandled Promise Rejection:", err);
            if (err instanceof Throwable) {
                console.INSTANCE.errorV1WithStack(err);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final <T> Unit all_origin$lambda$5(UTSArray<?> uTSArray, Function1<? super T, Unit> function1, Function1<java.lang.Object, Unit> function12) throws SecurityException {
            UTSArray uTSArray2 = new UTSArray();
            if (Intrinsics.areEqual((java.lang.Object) uTSArray.getLength(), (java.lang.Object) 0)) {
                UTSPromiseKt.callFunction(function1, UTSArrayKt.utsArrayOf(uTSArray2));
                return Unit.INSTANCE;
            }
            Iterator<?> it = uTSArray.iterator();
            while (it.hasNext()) {
                uTSArray2.push(it.next());
            }
            Ref.ObjectRef objectRef = new Ref.ObjectRef();
            objectRef.element = (T) uTSArray2.getLength();
            Companion companion = UTSPromise.INSTANCE;
            for (Integer numValueOf = (Number) 0; NumberKt.compareTo(numValueOf, uTSArray2.getLength()) < 0; numValueOf = Integer.valueOf(numValueOf.intValue() + 1)) {
                all_origin$lambda$5$res(function12, uTSArray2, objectRef, function1, numValueOf, uTSArray2.get(numValueOf.intValue()));
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final <T> Unit allSettled_origin$lambda$17(UTSArray<?> uTSArray, Function<?> function, Function<?> function2) throws SecurityException {
            UTSArray uTSArray2 = new UTSArray();
            Integer numValueOf = 0;
            if (Intrinsics.areEqual(uTSArray.getLength(), numValueOf)) {
                UTSPromiseKt.callFunction(function, UTSArrayKt.utsArrayOf(uTSArray2));
                return Unit.INSTANCE;
            }
            Iterator<?> it = uTSArray.iterator();
            while (it.hasNext()) {
                uTSArray2.push(it.next());
            }
            Ref.ObjectRef objectRef = new Ref.ObjectRef();
            objectRef.element = (T) uTSArray2.getLength();
            Companion companion = UTSPromise.INSTANCE;
            while (true) {
                Integer num = numValueOf;
                if (NumberKt.compareTo(num, uTSArray2.getLength()) < 0) {
                    allSettled_origin$lambda$17$res$15(uTSArray2, objectRef, function, num, uTSArray2.get(num.intValue()));
                    numValueOf = Integer.valueOf(num.intValue() + 1);
                } else {
                    return Unit.INSTANCE;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit _immediateFn$lambda$18(Function<?> function) throws SecurityException {
            UTSPromiseKt.callFunction(function, new UTSArray());
            return Unit.INSTANCE;
        }
    }
}
