package io.dcloud.uts;

import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: UTSArray.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "io.dcloud.uts.UTSArray$Companion$fromAsyncImpl$p$1$1", f = "UTSArray.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes2.dex */
final class UTSArray$Companion$fromAsyncImpl$p$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, java.lang.Object> {
    final /* synthetic */ Iterable<?> $list;
    final /* synthetic */ Function2<java.lang.Object, Number, T> $mapFn;
    final /* synthetic */ Function1<java.lang.Object, Unit> $rejectParam;
    final /* synthetic */ Function1<UTSArray<T>, Unit> $resolveParam;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    UTSArray$Companion$fromAsyncImpl$p$1$1(Iterable<?> iterable, Function1<? super UTSArray<T>, Unit> function1, Function1<java.lang.Object, Unit> function12, Function2<java.lang.Object, ? super Number, ? extends T> function2, Continuation<? super UTSArray$Companion$fromAsyncImpl$p$1$1> continuation) {
        super(2, continuation);
        this.$list = iterable;
        this.$resolveParam = function1;
        this.$rejectParam = function12;
        this.$mapFn = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(java.lang.Object obj, Continuation<?> continuation) {
        return new UTSArray$Companion$fromAsyncImpl$p$1$1(this.$list, this.$resolveParam, this.$rejectParam, this.$mapFn, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final java.lang.Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((UTSArray$Companion$fromAsyncImpl$p$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final java.lang.Object invokeSuspend(java.lang.Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Iterable<?> iterable = this.$list;
        Function1<java.lang.Object, Unit> function1 = this.$rejectParam;
        Function2<java.lang.Object, Number, T> function2 = this.$mapFn;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        int i = 0;
        for (java.lang.Object obj2 : iterable) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            arrayList.add(obj2 instanceof UTSPromise ? BuildersKt__BuildersKt.runBlocking$default(null, new UTSArray$Companion$fromAsyncImpl$p$1$1$allJob$1$1(obj2, function1, null), 1, null) : BuildersKt__BuildersKt.runBlocking$default(null, new UTSArray$Companion$fromAsyncImpl$p$1$1$allJob$1$2(obj2, function2, i, null), 1, null));
            i = i2;
        }
        Function1<UTSArray<T>, Unit> function12 = this.$resolveParam;
        UTSArray uTSArrayFromNative = UTSArray.INSTANCE.fromNative(arrayList);
        Intrinsics.checkNotNull(uTSArrayFromNative, "null cannot be cast to non-null type io.dcloud.uts.UTSArray<T of io.dcloud.uts.UTSArray.Companion.fromAsyncImpl>");
        function12.invoke2(uTSArrayFromNative);
        return Unit.INSTANCE;
    }
}
