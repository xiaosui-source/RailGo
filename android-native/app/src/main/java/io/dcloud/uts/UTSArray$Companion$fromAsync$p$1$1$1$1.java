package io.dcloud.uts;

import io.dcloud.common.DHInterface.IMgr;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: UTSArray.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "io.dcloud.uts.UTSArray$Companion$fromAsync$p$1$1$1$1", f = "UTSArray.kt", i = {0}, l = {IMgr.WindowEvent.WINDOW_ANIMATION_END}, m = "invokeSuspend", n = {"promiseReal"}, s = {"L$0"})
/* loaded from: classes2.dex */
final class UTSArray$Companion$fromAsync$p$1$1$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, java.lang.Object> {
    final /* synthetic */ java.lang.Object $any;
    final /* synthetic */ Function1<java.lang.Object, Unit> $rejectParam;
    final /* synthetic */ UTSArray<T> $utsArray;
    java.lang.Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    UTSArray$Companion$fromAsync$p$1$1$1$1(java.lang.Object obj, UTSArray<T> uTSArray, Function1<java.lang.Object, Unit> function1, Continuation<? super UTSArray$Companion$fromAsync$p$1$1$1$1> continuation) {
        super(2, continuation);
        this.$any = obj;
        this.$utsArray = uTSArray;
        this.$rejectParam = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(java.lang.Object obj, Continuation<?> continuation) {
        return new UTSArray$Companion$fromAsync$p$1$1$1$1(this.$any, this.$utsArray, this.$rejectParam, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final java.lang.Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((UTSArray$Companion$fromAsync$p$1$1$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final java.lang.Object invokeSuspend(java.lang.Object obj) throws Throwable {
        java.lang.Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            UTSPromise uTSPromise = (UTSPromise) this.$any;
            final Function1<java.lang.Object, Unit> function1 = this.$rejectParam;
            UTSPromise uTSPromiseM516catch = uTSPromise.m516catch(new Function1() { // from class: io.dcloud.uts.UTSArray$Companion$fromAsync$p$1$1$1$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final java.lang.Object invoke2(java.lang.Object obj2) {
                    return UTSArray$Companion$fromAsync$p$1$1$1$1.invokeSuspend$lambda$0(function1, obj2);
                }
            });
            this.L$0 = SpillingKt.nullOutSpilledVariable(uTSPromiseM516catch);
            this.label = 1;
            obj = UTSPromiseHelperKt.await(uTSPromiseM516catch, (Continuation) this);
            if (obj == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        this.$utsArray.add(obj);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit invokeSuspend$lambda$0(Function1<java.lang.Object, Unit> function1, java.lang.Object obj) {
        function1.invoke2(obj);
        return Unit.INSTANCE;
    }
}
