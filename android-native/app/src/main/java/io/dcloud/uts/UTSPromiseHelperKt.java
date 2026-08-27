package io.dcloud.uts;

import androidx.core.app.NotificationCompat;
import io.dcloud.uts.UTSPromise;
import kotlin.Function;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Deferred;

/* compiled from: UTSPromiseHelper.kt */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0012\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u0002\u001a\u001c\u0010\u0004\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u0006\u001a\u0002H\u0005H\u0086@¢\u0006\u0002\u0010\u0007\u001a\"\u0010\u0004\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00050\bH\u0086@¢\u0006\u0002\u0010\t\u001a\"\u0010\u0004\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u00052\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00050\u000bH\u0086@¢\u0006\u0002\u0010\f\u001a(\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00050\u000b\"\u0004\b\u0000\u0010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00050\bH\u0086@¢\u0006\u0002\u0010\t\u001a \u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00050\u000b\"\u0004\b\u0000\u0010\u00052\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00050\u000b\u001a7\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00050\b\"\u0004\b\u0000\u0010\u00052\u001e\u0010\u000f\u001a\u001a\b\u0001\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0011\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0010¢\u0006\u0002\u0010\u0012¨\u0006\u0013"}, d2 = {"wrapError", "Lio/dcloud/uts/UTSError;", NotificationCompat.CATEGORY_ERROR, "", "await", "T", "promise", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lio/dcloud/uts/UTSPromise;", "(Lio/dcloud/uts/UTSPromise;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "def", "Lkotlinx/coroutines/Deferred;", "(Lkotlinx/coroutines/Deferred;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toDeferred", "wrapUTSPromise", "fn", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "(Lkotlin/jvm/functions/Function1;)Lio/dcloud/uts/UTSPromise;", "utsplugin_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UTSPromiseHelperKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> Deferred<T> toDeferred(Deferred<? extends T> def) {
        Intrinsics.checkNotNullParameter(def, "def");
        return def;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final UTSError wrapError(java.lang.Object obj) {
        if (obj instanceof UTSError) {
            return (UTSError) obj;
        }
        return new HolderUTSError(obj);
    }

    public static final <T> java.lang.Object await(T t, Continuation<? super T> continuation) throws Throwable {
        SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        final SafeContinuation safeContinuation2 = safeContinuation;
        UTSPromise.then$default(UTSPromise.INSTANCE.resolve((UTSPromise.Companion) t), new Function1<java.lang.Object, Unit>() { // from class: io.dcloud.uts.UTSPromiseHelperKt$await$2$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public /* bridge */ /* synthetic */ Unit invoke2(java.lang.Object obj) {
                invoke2(obj);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(java.lang.Object obj) {
                Continuation<T> continuation2 = safeContinuation2;
                Result.Companion companion = Result.INSTANCE;
                continuation2.resumeWith(Result.m534constructorimpl(obj));
            }
        }, (Function) null, 2, (java.lang.Object) null).m516catch(new Function1<java.lang.Object, Unit>() { // from class: io.dcloud.uts.UTSPromiseHelperKt$await$2$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public /* bridge */ /* synthetic */ Unit invoke2(java.lang.Object obj) {
                invoke2(obj);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(java.lang.Object obj) {
                Continuation<T> continuation2 = safeContinuation2;
                Result.Companion companion = Result.INSTANCE;
                continuation2.resumeWith(Result.m534constructorimpl(ResultKt.createFailure(UTSPromiseHelperKt.wrapError(obj))));
            }
        });
        java.lang.Object orThrow = safeContinuation.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }

    public static final <T> java.lang.Object await(UTSPromise<T> uTSPromise, Continuation<? super T> continuation) throws Throwable {
        SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        final SafeContinuation safeContinuation2 = safeContinuation;
        Intrinsics.checkNotNull(uTSPromise);
        UTSPromise.then$default(uTSPromise, new Function1<T, Unit>() { // from class: io.dcloud.uts.UTSPromiseHelperKt$await$4$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public /* bridge */ /* synthetic */ Unit invoke2(java.lang.Object obj) {
                invoke2((UTSPromiseHelperKt$await$4$1<T>) obj);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(T t) {
                Continuation<T> continuation2 = safeContinuation2;
                Result.Companion companion = Result.INSTANCE;
                continuation2.resumeWith(Result.m534constructorimpl(t));
            }
        }, (Function) null, 2, (java.lang.Object) null).m516catch(new Function1<java.lang.Object, Unit>() { // from class: io.dcloud.uts.UTSPromiseHelperKt$await$4$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public /* bridge */ /* synthetic */ Unit invoke2(java.lang.Object obj) {
                invoke2(obj);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(java.lang.Object obj) {
                Continuation<T> continuation2 = safeContinuation2;
                Result.Companion companion = Result.INSTANCE;
                continuation2.resumeWith(Result.m534constructorimpl(ResultKt.createFailure(UTSPromiseHelperKt.wrapError(obj))));
            }
        });
        java.lang.Object orThrow = safeContinuation.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> java.lang.Object await(Deferred<? extends T> deferred, Continuation<? super T> continuation) {
        return deferred.await(continuation);
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* compiled from: UTSPromiseHelper.kt */
    @Metadata(d1 = {"\u0000\b\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "T", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "io.dcloud.uts.UTSPromiseHelperKt$toDeferred$2", f = "UTSPromiseHelper.kt", i = {}, l = {41}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: io.dcloud.uts.UTSPromiseHelperKt$toDeferred$2, reason: invalid class name */
    static final class AnonymousClass2<T> extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super T>, java.lang.Object> {
        final /* synthetic */ UTSPromise<T> $promise;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(UTSPromise<T> uTSPromise, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$promise = uTSPromise;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(java.lang.Object obj, Continuation<?> continuation) {
            return new AnonymousClass2(this.$promise, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final java.lang.Object invoke(CoroutineScope coroutineScope, Continuation<? super T> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
            this.label = 1;
            java.lang.Object objAwait = UTSPromiseHelperKt.await((UTSPromise) this.$promise, (Continuation) this);
            return objAwait == coroutine_suspended ? coroutine_suspended : objAwait;
        }
    }

    public static final <T> java.lang.Object toDeferred(UTSPromise<T> uTSPromise, Continuation<? super Deferred<? extends T>> continuation) {
        return BuildersKt__Builders_commonKt.async$default(CoroutineScopeKt.CoroutineScope(UTSAndroid.INSTANCE.getDomCoroutineDispatcher()), null, null, new AnonymousClass2(uTSPromise, null), 3, null);
    }

    public static final <T> UTSPromise<T> wrapUTSPromise(final Function1<? super Continuation<java.lang.Object>, ? extends java.lang.Object> fn) {
        Intrinsics.checkNotNullParameter(fn, "fn");
        return new UTSPromise<>(new Function2() { // from class: io.dcloud.uts.UTSPromiseHelperKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final java.lang.Object invoke(java.lang.Object obj, java.lang.Object obj2) {
                return UTSPromiseHelperKt.wrapUTSPromise$lambda$2(fn, (Function1) obj, (Function1) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit wrapUTSPromise$lambda$2(Function1 function1, Function1 resolve, Function1 reject) {
        Intrinsics.checkNotNullParameter(resolve, "resolve");
        Intrinsics.checkNotNullParameter(reject, "reject");
        BuildersKt__Builders_commonKt.async$default(CoroutineScopeKt.CoroutineScope(UTSAndroid.INSTANCE.getDomCoroutineDispatcher()), null, null, new UTSPromiseHelperKt$wrapUTSPromise$1$1(function1, resolve, reject, null), 3, null);
        return Unit.INSTANCE;
    }
}
