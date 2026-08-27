package io.dcloud.uts;

import com.alibaba.fastjson.asm.Opcodes;
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

/* compiled from: UTSPromiseHelper.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "io.dcloud.uts.UTSPromiseHelperKt$wrapUTSPromise$1$1", f = "UTSPromiseHelper.kt", i = {1}, l = {53, Opcodes.DSTORE}, m = "invokeSuspend", n = {"result"}, s = {"L$0"})
/* loaded from: classes2.dex */
final class UTSPromiseHelperKt$wrapUTSPromise$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, java.lang.Object> {
    final /* synthetic */ Function1<Continuation<java.lang.Object>, java.lang.Object> $fn;
    final /* synthetic */ Function1<java.lang.Object, Unit> $reject;
    final /* synthetic */ Function1<T, Unit> $resolve;
    java.lang.Object L$0;
    java.lang.Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    UTSPromiseHelperKt$wrapUTSPromise$1$1(Function1<? super Continuation<java.lang.Object>, ? extends java.lang.Object> function1, Function1<? super T, Unit> function12, Function1<java.lang.Object, Unit> function13, Continuation<? super UTSPromiseHelperKt$wrapUTSPromise$1$1> continuation) {
        super(2, continuation);
        this.$fn = function1;
        this.$resolve = function12;
        this.$reject = function13;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(java.lang.Object obj, Continuation<?> continuation) {
        return new UTSPromiseHelperKt$wrapUTSPromise$1$1(this.$fn, this.$resolve, this.$reject, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final java.lang.Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((UTSPromiseHelperKt$wrapUTSPromise$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final java.lang.Object invokeSuspend(java.lang.Object obj) {
        Function1 function1;
        Function1 function12;
        java.lang.Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        try {
        } catch (Throwable th) {
            this.$reject.invoke2(th);
        }
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Function1<Continuation<java.lang.Object>, java.lang.Object> function13 = this.$fn;
            this.label = 1;
            obj = function13.invoke2(this);
            if (obj == coroutine_suspended) {
            }
            return coroutine_suspended;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            function12 = (Function1) this.L$1;
            ResultKt.throwOnFailure(obj);
            function1 = function12;
            function1.invoke2(obj);
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        function1 = this.$resolve;
        if (obj instanceof UTSPromise) {
            this.L$0 = SpillingKt.nullOutSpilledVariable(obj);
            this.L$1 = function1;
            this.label = 2;
            obj = UTSPromiseHelperKt.await((UTSPromise) obj, (Continuation) this);
            if (obj != coroutine_suspended) {
                function12 = function1;
                function1 = function12;
            }
            return coroutine_suspended;
        }
        function1.invoke2(obj);
        return Unit.INSTANCE;
    }
}
