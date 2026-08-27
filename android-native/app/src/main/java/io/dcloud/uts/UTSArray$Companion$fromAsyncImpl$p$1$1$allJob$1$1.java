package io.dcloud.uts;

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
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "io.dcloud.uts.UTSArray$Companion$fromAsyncImpl$p$1$1$allJob$1$1", f = "UTSArray.kt", i = {0}, l = {113}, m = "invokeSuspend", n = {"promiseReal"}, s = {"L$0"})
/* loaded from: classes2.dex */
final class UTSArray$Companion$fromAsyncImpl$p$1$1$allJob$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super java.lang.Object>, java.lang.Object> {
    final /* synthetic */ java.lang.Object $any;
    final /* synthetic */ Function1<java.lang.Object, Unit> $rejectParam;
    java.lang.Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    UTSArray$Companion$fromAsyncImpl$p$1$1$allJob$1$1(java.lang.Object obj, Function1<java.lang.Object, Unit> function1, Continuation<? super UTSArray$Companion$fromAsyncImpl$p$1$1$allJob$1$1> continuation) {
        super(2, continuation);
        this.$any = obj;
        this.$rejectParam = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(java.lang.Object obj, Continuation<?> continuation) {
        return new UTSArray$Companion$fromAsyncImpl$p$1$1$allJob$1$1(this.$any, this.$rejectParam, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ java.lang.Object invoke(CoroutineScope coroutineScope, Continuation<? super java.lang.Object> continuation) {
        return invoke2(coroutineScope, (Continuation<java.lang.Object>) continuation);
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final java.lang.Object invoke2(CoroutineScope coroutineScope, Continuation<java.lang.Object> continuation) {
        return ((UTSArray$Companion$fromAsyncImpl$p$1$1$allJob$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final java.lang.Object invokeSuspend(java.lang.Object obj) throws Throwable {
        java.lang.Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        UTSPromise uTSPromise = (UTSPromise) this.$any;
        final Function1<java.lang.Object, Unit> function1 = this.$rejectParam;
        UTSPromise uTSPromiseM516catch = uTSPromise.m516catch(new Function1() { // from class: io.dcloud.uts.UTSArray$Companion$fromAsyncImpl$p$1$1$allJob$1$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final java.lang.Object invoke2(java.lang.Object obj2) {
                return UTSArray$Companion$fromAsyncImpl$p$1$1$allJob$1$1.invokeSuspend$lambda$0(function1, obj2);
            }
        });
        this.L$0 = SpillingKt.nullOutSpilledVariable(uTSPromiseM516catch);
        this.label = 1;
        java.lang.Object objAwait = UTSPromiseHelperKt.await(uTSPromiseM516catch, (Continuation) this);
        return objAwait == coroutine_suspended ? coroutine_suspended : objAwait;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit invokeSuspend$lambda$0(Function1<java.lang.Object, Unit> function1, java.lang.Object obj) {
        function1.invoke2(obj);
        return Unit.INSTANCE;
    }
}
