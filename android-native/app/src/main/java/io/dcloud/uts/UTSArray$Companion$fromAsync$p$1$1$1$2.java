package io.dcloud.uts;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: UTSArray.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "io.dcloud.uts.UTSArray$Companion$fromAsync$p$1$1$1$2", f = "UTSArray.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes2.dex */
final class UTSArray$Companion$fromAsync$p$1$1$1$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, java.lang.Object> {
    final /* synthetic */ java.lang.Object $any;
    final /* synthetic */ int $index;
    final /* synthetic */ Function2<java.lang.Object, Number, T> $mapFn;
    final /* synthetic */ UTSArray<T> $utsArray;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    UTSArray$Companion$fromAsync$p$1$1$1$2(java.lang.Object obj, Function2<java.lang.Object, ? super Number, ? extends T> function2, int i, UTSArray<T> uTSArray, Continuation<? super UTSArray$Companion$fromAsync$p$1$1$1$2> continuation) {
        super(2, continuation);
        this.$any = obj;
        this.$mapFn = function2;
        this.$index = i;
        this.$utsArray = uTSArray;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(java.lang.Object obj, Continuation<?> continuation) {
        return new UTSArray$Companion$fromAsync$p$1$1$1$2(this.$any, this.$mapFn, this.$index, this.$utsArray, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final java.lang.Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((UTSArray$Companion$fromAsync$p$1$1$1$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final java.lang.Object invokeSuspend(java.lang.Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        java.lang.Object objInvoke = this.$any;
        Function2<java.lang.Object, Number, T> function2 = this.$mapFn;
        if (function2 != 0) {
            objInvoke = function2.invoke(objInvoke, Boxing.boxInt(this.$index));
        }
        this.$utsArray.add(objInvoke);
        return Unit.INSTANCE;
    }
}
