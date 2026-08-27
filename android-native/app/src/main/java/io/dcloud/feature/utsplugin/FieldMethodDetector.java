package io.dcloud.feature.utsplugin;

import io.dcloud.feature.utsplugin.ProxyModule;
import java.lang.reflect.Method;
import kotlin.Function;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KCallable;
import kotlin.reflect.KFunction;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.ReflectLambdaKt;

/* compiled from: FieldMethodDetector.kt */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u001d\b\u0016\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0006\u0010\u001a\u001a\u00020\u001bJ\u0006\u0010\u001c\u001a\u00020\u001dR \u0010\b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR \u0010\u000e\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0012\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lio/dcloud/feature/utsplugin/FieldMethodDetector;", "", "javaClazz", "Ljava/lang/Class;", "inputOption", "Lio/dcloud/feature/utsplugin/ProxyModule$InputOption;", "<init>", "(Ljava/lang/Class;Lio/dcloud/feature/utsplugin/ProxyModule$InputOption;)V", "hostFunction", "Lkotlin/Function;", "getHostFunction", "()Lkotlin/Function;", "setHostFunction", "(Lkotlin/Function;)V", "targetFunction", "Lkotlin/reflect/KFunction;", "getTargetFunction", "()Lkotlin/reflect/KFunction;", "setTargetFunction", "(Lkotlin/reflect/KFunction;)V", "fieldFunMethod", "Ljava/lang/reflect/Method;", "getFieldFunMethod", "()Ljava/lang/reflect/Method;", "setFieldFunMethod", "(Ljava/lang/reflect/Method;)V", "init", "", "isFieldMethod", "", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes.dex */
public final class FieldMethodDetector {
    private Method fieldFunMethod;
    private Function<?> hostFunction;
    private final ProxyModule.InputOption inputOption;
    private final Class<?> javaClazz;
    private KFunction<?> targetFunction;

    public final Function<?> getHostFunction() {
        return this.hostFunction;
    }

    public final void setHostFunction(Function<?> function) {
        this.hostFunction = function;
    }

    public final KFunction<?> getTargetFunction() {
        return this.targetFunction;
    }

    public final void setTargetFunction(KFunction<?> kFunction) {
        this.targetFunction = kFunction;
    }

    public final Method getFieldFunMethod() {
        return this.fieldFunMethod;
    }

    public final void setFieldFunMethod(Method method) {
        this.fieldFunMethod = method;
    }

    public FieldMethodDetector(Class<?> javaClazz, ProxyModule.InputOption inputOption) {
        Intrinsics.checkNotNullParameter(javaClazz, "javaClazz");
        Intrinsics.checkNotNullParameter(inputOption, "inputOption");
        this.javaClazz = javaClazz;
        this.inputOption = inputOption;
    }

    public final void init() throws SecurityException {
        Method method;
        KCallable<?> kCallableFindTargetField = ProxyModule.INSTANCE.findTargetField(this.javaClazz, this.inputOption.getIsCompanion(), this.inputOption.getMethodName());
        if (kCallableFindTargetField == null || !(kCallableFindTargetField instanceof KProperty)) {
            return;
        }
        int i = 0;
        V vCall = ((KProperty) kCallableFindTargetField).call(new Object[0]);
        Intrinsics.checkNotNull(vCall, "null cannot be cast to non-null type kotlin.Function<*>");
        Function<?> function = (Function) vCall;
        this.hostFunction = function;
        Intrinsics.checkNotNull(function);
        KFunction<?> kFunctionReflect = ReflectLambdaKt.reflect(function);
        if (kFunctionReflect != null) {
            this.targetFunction = kFunctionReflect;
        }
        Function<?> function2 = this.hostFunction;
        Intrinsics.checkNotNull(function2);
        Method[] methods = function2.getClass().getMethods();
        Intrinsics.checkNotNullExpressionValue(methods, "getMethods(...)");
        Method[] methodArr = methods;
        int length = methodArr.length;
        while (true) {
            if (i >= length) {
                method = null;
                break;
            }
            method = methodArr[i];
            if (Intrinsics.areEqual(method.getName(), "invoke")) {
                break;
            } else {
                i++;
            }
        }
        this.fieldFunMethod = method;
    }

    public final boolean isFieldMethod() {
        return (this.hostFunction == null || this.targetFunction == null || this.fieldFunMethod == null) ? false : true;
    }
}
