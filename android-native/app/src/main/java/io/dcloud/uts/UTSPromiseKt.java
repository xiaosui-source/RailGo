package io.dcloud.uts;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import kotlin.Function;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;

/* compiled from: UTSPromise.kt */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a \u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u000e\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0004\u001a$\u0010\u0005\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u00072\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n\u001a\u001a\u0010\u000b\u001a\u00020\u00062\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\r\u001a\u00020\u000e\u001a\u001c\u0010\u000f\u001a\u00020\u00062\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001\u001a\u001c\u0010\u0011\u001a\u00020\u00062\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001\u001a\u0012\u0010\u0012\u001a\u00020\u00062\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n¨\u0006\u0013"}, d2 = {"callFunction", "", "fn", "args", "Lio/dcloud/uts/UTSArray;", "doResolveUTSPromise", "", "T", "Lkotlin/Function;", "self", "Lio/dcloud/uts/UTSPromise;", "handleUTSPromise", "promise", "deferred", "Lio/dcloud/uts/UTSPromiseHandler;", "resolveUTSPromise", "newValue", "rejectUTSPromise", "finaleUTSPromise", "utsplugin_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UTSPromiseKt {
    public static final java.lang.Object callFunction(final java.lang.Object fn, final UTSArray<java.lang.Object> args) throws SecurityException {
        Method method;
        Intrinsics.checkNotNullParameter(fn, "fn");
        Intrinsics.checkNotNullParameter(args, "args");
        if (fn instanceof Function) {
            Method[] declaredMethods = fn.getClass().getDeclaredMethods();
            Intrinsics.checkNotNullExpressionValue(declaredMethods, "getDeclaredMethods(...)");
            Method[] methodArr = declaredMethods;
            int length = methodArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    method = null;
                    break;
                }
                method = methodArr[i];
                Method method2 = method;
                if (Intrinsics.areEqual("invoke", method2.getName()) && !method2.isBridge()) {
                    break;
                }
                i++;
            }
            final Method method3 = method;
            if (method3 != null) {
                method3.setAccessible(true);
                return new Function0() { // from class: io.dcloud.uts.UTSPromiseKt$$ExternalSyntheticLambda5
                    @Override // kotlin.jvm.functions.Function0
                    public final java.lang.Object invoke() {
                        return UTSPromiseKt.callFunction$lambda$2(args, method3, fn);
                    }
                }.invoke();
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final java.lang.Object callFunction$lambda$2(UTSArray<java.lang.Object> uTSArray, Method method, java.lang.Object obj) throws Throwable {
        UTSArray<java.lang.Object> uTSArraySlice = uTSArray.slice((Number) 0, Integer.valueOf(method.getParameterTypes().length));
        Intrinsics.checkNotNull(method);
        if (method.isVarArgs()) {
            int length = method.getParameterTypes().length - 1;
            Class<?> cls = method.getParameterTypes()[length];
            Intrinsics.checkNotNull(cls);
            Intrinsics.checkNotNull(cls);
            Class<?> componentType = cls.getComponentType();
            UTSArray uTSArraySlice$default = UTSArray.slice$default(uTSArray, Integer.valueOf(length), null, 2, null);
            Number length2 = uTSArraySlice$default.getLength();
            Intrinsics.checkNotNull(length2, "null cannot be cast to non-null type kotlin.Int");
            final java.lang.Object objNewInstance = Array.newInstance(componentType, ((Integer) length2).intValue());
            uTSArraySlice$default.forEach(new Function2() { // from class: io.dcloud.uts.UTSPromiseKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final java.lang.Object invoke(java.lang.Object obj2, java.lang.Object obj3) {
                    return UTSPromiseKt.callFunction$lambda$2$lambda$1(objNewInstance, obj2, (Number) obj3);
                }
            });
            if (NumberKt.compareTo(uTSArraySlice.getLength(), Integer.valueOf(length)) > 0) {
                uTSArraySlice.set(length, (int) objNewInstance);
            } else {
                uTSArraySlice.push(objNewInstance);
            }
        }
        try {
            java.lang.Object[] array = uTSArraySlice.splice((Number) 0, Integer.valueOf(method.getParameterTypes().length)).toArray(new java.lang.Object[0]);
            return method.invoke(obj, Arrays.copyOf(array, array.length));
        } catch (Throwable th) {
            if (th instanceof InvocationTargetException) {
                InvocationTargetException invocationTargetException = th;
                if (invocationTargetException.getCause() != null) {
                    Throwable cause = invocationTargetException.getCause();
                    Intrinsics.checkNotNull(cause);
                    throw cause;
                }
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit callFunction$lambda$2$lambda$1(java.lang.Object obj, java.lang.Object obj2, Number number) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        Intrinsics.checkNotNull(number, "null cannot be cast to non-null type kotlin.Int");
        Array.set(obj, ((Integer) number).intValue(), obj2);
        return Unit.INSTANCE;
    }

    public static final <T> void doResolveUTSPromise(Function<?> fn, final UTSPromise<?> self) {
        Intrinsics.checkNotNullParameter(fn, "fn");
        Intrinsics.checkNotNullParameter(self, "self");
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        try {
            callFunction(fn, UTSArrayKt.utsArrayOf(new Function1() { // from class: io.dcloud.uts.UTSPromiseKt$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final java.lang.Object invoke2(java.lang.Object obj) {
                    return UTSPromiseKt.doResolveUTSPromise$lambda$3(booleanRef, self, obj);
                }
            }, new Function1() { // from class: io.dcloud.uts.UTSPromiseKt$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final java.lang.Object invoke2(java.lang.Object obj) {
                    return UTSPromiseKt.doResolveUTSPromise$lambda$4(booleanRef, self, obj);
                }
            }));
        } catch (Throwable th) {
            if (booleanRef.element) {
                return;
            }
            booleanRef.element = true;
            rejectUTSPromise(self, th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <T> Unit doResolveUTSPromise$lambda$3(Ref.BooleanRef booleanRef, UTSPromise<?> uTSPromise, T t) {
        if (booleanRef.element) {
            return Unit.INSTANCE;
        }
        booleanRef.element = true;
        resolveUTSPromise(uTSPromise, t);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit doResolveUTSPromise$lambda$4(Ref.BooleanRef booleanRef, UTSPromise<?> uTSPromise, java.lang.Object obj) {
        if (booleanRef.element) {
            return Unit.INSTANCE;
        }
        booleanRef.element = true;
        rejectUTSPromise(uTSPromise, obj);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v18, types: [T, io.dcloud.uts.UTSPromise] */
    public static final void handleUTSPromise(UTSPromise<?> promise, final UTSPromiseHandler deferred) {
        Intrinsics.checkNotNullParameter(promise, "promise");
        Intrinsics.checkNotNullParameter(deferred, "deferred");
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = promise;
        while (Intrinsics.areEqual((java.lang.Object) ((UTSPromise) objectRef.element).get_state(), (java.lang.Object) 3)) {
            java.lang.Object obj = ((UTSPromise) objectRef.element).get_value();
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type io.dcloud.uts.UTSPromise<*>");
            objectRef.element = (UTSPromise) obj;
        }
        if (Intrinsics.areEqual((java.lang.Object) ((UTSPromise) objectRef.element).get_state(), (java.lang.Object) 0)) {
            UTSArray<UTSPromiseHandler> uTSArray = ((UTSPromise) objectRef.element).get_deferreds();
            Intrinsics.checkNotNull(uTSArray);
            uTSArray.push(deferred);
        } else {
            ((UTSPromise) objectRef.element).set_handled(true);
            UTSPromise.INSTANCE._immediateFn(new Function0() { // from class: io.dcloud.uts.UTSPromiseKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final java.lang.Object invoke() {
                    return UTSPromiseKt.handleUTSPromise$lambda$5(objectRef, deferred);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit handleUTSPromise$lambda$5(Ref.ObjectRef<UTSPromise<?>> objectRef, UTSPromiseHandler uTSPromiseHandler) {
        Function<?> onRejected;
        if (Intrinsics.areEqual((java.lang.Object) objectRef.element.get_state(), (java.lang.Object) 1)) {
            onRejected = uTSPromiseHandler.getOnFulfilled();
        } else {
            onRejected = uTSPromiseHandler.getOnRejected();
        }
        if (onRejected == null) {
            if (Intrinsics.areEqual((java.lang.Object) objectRef.element.get_state(), (java.lang.Object) 1)) {
                resolveUTSPromise(uTSPromiseHandler.getPromise(), objectRef.element.get_value());
            } else {
                rejectUTSPromise(uTSPromiseHandler.getPromise(), objectRef.element.get_value());
            }
            return Unit.INSTANCE;
        }
        try {
            resolveUTSPromise(uTSPromiseHandler.getPromise(), callFunction(onRejected, UTSArrayKt.utsArrayOf(objectRef.element.get_value())));
            return Unit.INSTANCE;
        } catch (Throwable th) {
            rejectUTSPromise(uTSPromiseHandler.getPromise(), th);
            return Unit.INSTANCE;
        }
    }

    public static final void resolveUTSPromise(UTSPromise<?> self, java.lang.Object obj) {
        Intrinsics.checkNotNullParameter(self, "self");
        try {
            if (obj == self) {
                throw new UTSError("A promise cannot be resolved with itself.");
            }
            if (obj != null && (obj instanceof UTSPromise)) {
                self.set_state((Number) 3);
                self.set_value(obj);
                finaleUTSPromise(self);
            } else {
                self.set_state((Number) 1);
                self.set_value(obj);
                finaleUTSPromise(self);
            }
        } catch (Throwable th) {
            rejectUTSPromise(self, th);
        }
    }

    public static final void rejectUTSPromise(UTSPromise<?> self, java.lang.Object obj) {
        Intrinsics.checkNotNullParameter(self, "self");
        self.set_state((Number) 2);
        self.set_value(obj);
        finaleUTSPromise(self);
    }

    public static final void finaleUTSPromise(final UTSPromise<?> self) {
        Intrinsics.checkNotNullParameter(self, "self");
        if (Intrinsics.areEqual((java.lang.Object) self.get_state(), (java.lang.Object) 2)) {
            UTSArray<UTSPromiseHandler> uTSArray = self.get_deferreds();
            Intrinsics.checkNotNull(uTSArray);
            if (Intrinsics.areEqual((java.lang.Object) uTSArray.getLength(), (java.lang.Object) 0)) {
                UTSPromise.INSTANCE._immediateFn(new Function0() { // from class: io.dcloud.uts.UTSPromiseKt$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function0
                    public final java.lang.Object invoke() {
                        return UTSPromiseKt.finaleUTSPromise$lambda$6(self);
                    }
                });
            }
        }
        UTSArray<UTSPromiseHandler> uTSArray2 = self.get_deferreds();
        Intrinsics.checkNotNull(uTSArray2);
        Number length = uTSArray2.getLength();
        for (Integer numValueOf = (Number) 0; NumberKt.compareTo(numValueOf, length) < 0; numValueOf = Integer.valueOf(numValueOf.intValue() + 1)) {
            UTSArray<UTSPromiseHandler> uTSArray3 = self.get_deferreds();
            Intrinsics.checkNotNull(uTSArray3);
            UTSPromiseHandler uTSPromiseHandler = uTSArray3.get(numValueOf.intValue());
            Intrinsics.checkNotNullExpressionValue(uTSPromiseHandler, "get(...)");
            handleUTSPromise(self, uTSPromiseHandler);
        }
        self.set_deferreds(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit finaleUTSPromise$lambda$6(UTSPromise<?> uTSPromise) {
        if (!uTSPromise.get_handled()) {
            UTSPromise.INSTANCE._unhandledRejectionFn(uTSPromise.get_value());
        }
        return Unit.INSTANCE;
    }
}
