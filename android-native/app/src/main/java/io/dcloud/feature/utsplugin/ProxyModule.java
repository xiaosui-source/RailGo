package io.dcloud.feature.utsplugin;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.common.DHInterface.message.EnumUniqueID;
import io.dcloud.common.DHInterface.message.IObserveAble;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.AppConsoleLogUtil;
import io.dcloud.common.util.ErrorDialogUtil;
import io.dcloud.common.util.StringUtil;
import io.dcloud.common.util.TestUtil;
import io.dcloud.feature.uniapp.AbsSDKInstance;
import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.common.UniModule;
import io.dcloud.uts.UTSAndroid;
import io.dcloud.uts.UTSArray;
import io.dcloud.uts.UTSJSONObject;
import io.dcloud.uts.android.AndroidUTSContext;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.UByte$$ExternalSyntheticBackport0;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.reflect.KCallable;
import kotlin.reflect.KClass;
import kotlin.reflect.KFunction;
import kotlin.reflect.KProperty1;
import kotlin.reflect.full.KClasses;
import kotlin.reflect.jvm.ReflectJvmMapping;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineExceptionHandler;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;

/* compiled from: UTSProxyModule.kt */
@Metadata(d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 ;2\u00020\u00012\u00020\u0002:\u0003;<=B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\"\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\b\u0010\r\u001a\u00020\u0006H\u0016J\b\u0010\u000e\u001a\u00020\u0006H\u0016J\b\u0010\u000f\u001a\u00020\u0006H\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J+\u0010\u0012\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0016\u001a\u00020\u0017H\u0016¢\u0006\u0002\u0010\u0018J&\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\n\u0010\u001b\u001a\u0006\u0012\u0002\b\u00030\u001c2\u0006\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J2\u0010 \u001a\b\u0012\u0002\b\u0003\u0018\u00010!2\n\u0010\u001b\u001a\u0006\u0012\u0002\b\u00030\u001c2\u0006\u0010\"\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J,\u0010#\u001a\u0004\u0018\u00010$2\n\u0010%\u001a\u0006\u0012\u0002\b\u00030!2\u000e\u0010&\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010$0\u0014H\u0086@¢\u0006\u0002\u0010'J:\u0010(\u001a\u0004\u0018\u00010)2\u0006\u0010*\u001a\u00020\u001f2\n\u0010\u001b\u001a\u0006\u0012\u0002\b\u00030\u001c2\b\u0010+\u001a\u0004\u0018\u00010$2\b\u0010,\u001a\u0004\u0018\u00010-2\u0006\u0010.\u001a\u00020/H\u0002J\"\u00100\u001a\u0004\u0018\u00010$2\u0006\u00101\u001a\u00020)2\b\u0010,\u001a\u0004\u0018\u00010-H\u0087@¢\u0006\u0002\u00102J6\u00103\u001a\u0002H4\"\u0004\b\u0000\u00104*\b\u0012\u0004\u0012\u0002H4052\u0016\u00106\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010$0\u0014\"\u0004\u0018\u00010$H\u0087@¢\u0006\u0002\u00107J \u00108\u001a\u00020\u00062\u0006\u00101\u001a\u00020)2\b\u0010,\u001a\u0004\u0018\u00010-H\u0097@¢\u0006\u0002\u00102J\b\u00109\u001a\u00020:H\u0016¨\u0006>"}, d2 = {"Lio/dcloud/feature/utsplugin/ProxyModule;", "Lio/dcloud/feature/uniapp/common/UniModule;", "Lio/dcloud/common/DHInterface/message/IObserveAble;", "<init>", "()V", "onActivityPause", "", "onActivityResult", WXModule.REQUEST_CODE, "", WXModule.RESULT_CODE, "data", "Landroid/content/Intent;", "onActivityResume", "onActivityStop", "onActivityDestroy", "onActivityBack", "", "onRequestPermissionsResult", "permissions", "", "", WXModule.GRANT_RESULTS, "", "(I[Ljava/lang/String;[I)V", "findTargetMethod", "Ljava/lang/reflect/Method;", "javaClazz", "Ljava/lang/Class;", "methodName", AbsoluteConst.JSON_KEY_OPTION, "Lio/dcloud/feature/utsplugin/ProxyModule$InputOption;", "findTargetFunc", "Lkotlin/reflect/KFunction;", "isCompanion", "getFunctionExecuteRet", "", "targetFunction", "paramArray", "(Lkotlin/reflect/KFunction;[Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "wrapDoTypeAction", "Lcom/alibaba/fastjson/JSONObject;", "inputOption", "targetInstance", WXBridgeManager.METHOD_CALLBACK, "Lcom/taobao/weex/bridge/JSCallback;", "errRet", "Lio/dcloud/feature/utsplugin/ReturnResult;", "invokeSync", "options", "(Lcom/alibaba/fastjson/JSONObject;Lcom/taobao/weex/bridge/JSCallback;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "callSuspend", "R", "Lkotlin/reflect/KCallable;", "args", "(Lkotlin/reflect/KCallable;[Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "invokeAsync", "getActionObserverID", "Lio/dcloud/common/DHInterface/message/EnumUniqueID;", "Companion", "InputOption", "ModuleChecker", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes.dex */
public final class ProxyModule extends UniModule implements IObserveAble {
    private static int instanceDynamicId;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static Map<Integer, Object> utsInstances = new LinkedHashMap();

    /* compiled from: UTSProxyModule.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "io.dcloud.feature.utsplugin.ProxyModule", f = "UTSProxyModule.kt", i = {0, 0}, l = {1054}, m = "callSuspend", n = {"$this$callSuspend", "args"}, s = {"L$0", "L$1"})
    /* renamed from: io.dcloud.feature.utsplugin.ProxyModule$callSuspend$1, reason: invalid class name */
    static final class AnonymousClass1<R> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ProxyModule.this.callSuspend(null, null, this);
        }
    }

    /* compiled from: UTSProxyModule.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "io.dcloud.feature.utsplugin.ProxyModule", f = "UTSProxyModule.kt", i = {0, 0, 1, 1, 1, 2, 2}, l = {646, 647, 649}, m = "getFunctionExecuteRet", n = {"targetFunction", "paramArray", "targetFunction", "paramArray", "suspendRet", "targetFunction", "paramArray"}, s = {"L$0", "L$1", "L$0", "L$1", "L$2", "L$0", "L$1"})
    /* renamed from: io.dcloud.feature.utsplugin.ProxyModule$getFunctionExecuteRet$1, reason: invalid class name and case insensitive filesystem */
    static final class C01001 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        C01001(Continuation<? super C01001> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ProxyModule.this.getFunctionExecuteRet(null, null, this);
        }
    }

    @Override // com.taobao.weex.common.WXModule
    public void onActivityDestroy() {
    }

    @Override // com.taobao.weex.common.WXModule
    public void onActivityPause() {
        Iterator<Function0<Unit>> it = AndroidUTSContext.INSTANCE.getPauseListenFunc().iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            it.next().invoke();
        }
    }

    @Override // com.taobao.weex.common.WXModule
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Iterator<Function3<Integer, Integer, Intent, Unit>> it = AndroidUTSContext.INSTANCE.getOnActivityResultListenFunc().iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            it.next().invoke(Integer.valueOf(requestCode), Integer.valueOf(resultCode), data);
        }
    }

    @Override // com.taobao.weex.common.WXModule
    public void onActivityResume() {
        Iterator<Function0<Unit>> it = AndroidUTSContext.INSTANCE.getResumeListenFunc().iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            it.next().invoke();
        }
    }

    @Override // com.taobao.weex.common.WXModule
    public void onActivityStop() {
        super.onActivityStop();
        Iterator<Function0<Unit>> it = AndroidUTSContext.INSTANCE.getStopListenFunc().iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            it.next().invoke();
        }
    }

    @Override // com.taobao.weex.common.WXModule
    public boolean onActivityBack() {
        Iterator<Function0<Unit>> it = AndroidUTSContext.INSTANCE.getBackListenFunc().iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            it.next().invoke();
        }
        return false;
    }

    @Override // com.taobao.weex.common.WXModule
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        List listMutableListOf = CollectionsKt.mutableListOf(Arrays.copyOf(permissions, permissions.length));
        ArrayList arrayList = new ArrayList();
        for (int i : grantResults) {
            arrayList.add(Integer.valueOf(i));
        }
        Iterator<Function3<Integer, UTSArray<String>, UTSArray<Number>, Unit>> it = AndroidUTSContext.INSTANCE.getPermissionsResultListenFunc().iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            it.next().invoke(Integer.valueOf(requestCode), UTSArray.INSTANCE.fromNative(listMutableListOf), UTSArray.INSTANCE.fromNative(arrayList));
        }
    }

    private final Method findTargetMethod(Class<?> javaClazz, String methodName, InputOption option) {
        Iterator it = ArrayIteratorKt.iterator(javaClazz.getMethods());
        Method method = null;
        while (it.hasNext()) {
            Method method2 = (Method) it.next();
            if (Intrinsics.areEqual(methodName, method2.getName()) && option.getParamArray().size() == UByte$$ExternalSyntheticBackport0.m(method2)) {
                int iM = UByte$$ExternalSyntheticBackport0.m(method2);
                boolean z = true;
                for (int i = 0; i < iM; i++) {
                    if (!method2.getParameterTypes()[i].isInstance(option.getParamArray().get(i))) {
                        z = false;
                    }
                }
                if (z) {
                    return method2;
                }
                method = method2;
            }
        }
        return method;
    }

    /* JADX WARN: Code restructure failed: missing block: B:60:0x00d9, code lost:
    
        continue;
     */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00a6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final kotlin.reflect.KFunction<?> findTargetFunc(java.lang.Class<?> r9, boolean r10, java.lang.String r11, io.dcloud.feature.utsplugin.ProxyModule.InputOption r12) throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            r8 = this;
            r0 = 0
            if (r10 == 0) goto L2d
            kotlin.reflect.KClass r9 = kotlin.jvm.JvmClassMappingKt.getKotlinClass(r9)
            kotlin.reflect.KClass r9 = kotlin.reflect.full.KClasses.getCompanionObject(r9)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r9)
            java.util.Collection r9 = kotlin.reflect.full.KClasses.getMemberFunctions(r9)
            java.util.Iterator r9 = r9.iterator()
        L16:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto Led
            java.lang.Object r10 = r9.next()
            kotlin.reflect.KFunction r10 = (kotlin.reflect.KFunction) r10
            java.lang.String r12 = r10.getName()
            boolean r12 = kotlin.jvm.internal.Intrinsics.areEqual(r11, r12)
            if (r12 == 0) goto L16
            return r10
        L2d:
            java.lang.reflect.Method[] r9 = r9.getMethods()
            java.util.Iterator r9 = kotlin.jvm.internal.ArrayIteratorKt.iterator(r9)
            r10 = r0
        L36:
            r1 = r10
        L37:
            boolean r2 = r9.hasNext()
            if (r2 == 0) goto Ldf
            java.lang.Object r2 = r9.next()
            java.lang.reflect.Method r2 = (java.lang.reflect.Method) r2
            java.lang.String r3 = r2.getName()
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r11, r3)
            if (r3 == 0) goto L37
            com.alibaba.fastjson.JSONArray r1 = r12.getParamArray()
            int r1 = r1.size()
            r3 = 0
            if (r1 != 0) goto La6
            kotlin.jvm.internal.Intrinsics.checkNotNull(r2)
            kotlin.reflect.KFunction r1 = kotlin.reflect.jvm.ReflectJvmMapping.getKotlinFunction(r2)
            if (r1 == 0) goto La2
            java.util.List r1 = r1.getParameters()
            if (r1 == 0) goto La2
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            java.util.ArrayList r4 = new java.util.ArrayList
            r5 = 10
            int r5 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r1, r5)
            r4.<init>(r5)
            java.util.Collection r4 = (java.util.Collection) r4
            java.util.Iterator r1 = r1.iterator()
            r5 = 0
        L7b:
            boolean r6 = r1.hasNext()
            if (r6 == 0) goto L9f
            java.lang.Object r6 = r1.next()
            kotlin.reflect.KParameter r6 = (kotlin.reflect.KParameter) r6
            kotlin.reflect.KType r7 = r6.getType()
            boolean r7 = r7.isMarkedNullable()
            if (r7 != 0) goto L99
            boolean r6 = r6.isOptional()
            if (r6 != 0) goto L99
            int r5 = r5 + 1
        L99:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            r4.add(r6)
            goto L7b
        L9f:
            java.util.List r4 = (java.util.List) r4
            goto La3
        La2:
            r5 = 0
        La3:
            if (r5 != 0) goto La6
            goto Ld9
        La6:
            com.alibaba.fastjson.JSONArray r1 = r12.getParamArray()
            int r1 = r1.size()
            int r4 = kotlin.UByte$$ExternalSyntheticBackport0.m(r2)
            if (r1 != r4) goto Ldc
            int r10 = kotlin.UByte$$ExternalSyntheticBackport0.m(r2)
            r1 = 1
            r4 = 0
        Lba:
            if (r4 >= r10) goto Ld4
            java.lang.Class[] r5 = r2.getParameterTypes()
            r5 = r5[r4]
            com.alibaba.fastjson.JSONArray r6 = r12.getParamArray()
            java.lang.Object r6 = r6.get(r4)
            boolean r5 = r5.isInstance(r6)
            if (r5 != 0) goto Ld1
            r1 = 0
        Ld1:
            int r4 = r4 + 1
            goto Lba
        Ld4:
            if (r1 == 0) goto Ld9
            r10 = r2
            r1 = r10
            goto Ldf
        Ld9:
            r10 = r2
            goto L36
        Ldc:
            r1 = r2
            goto L37
        Ldf:
            if (r10 == 0) goto Le6
            kotlin.reflect.KFunction r9 = kotlin.reflect.jvm.ReflectJvmMapping.getKotlinFunction(r10)
            return r9
        Le6:
            if (r1 == 0) goto Led
            kotlin.reflect.KFunction r9 = kotlin.reflect.jvm.ReflectJvmMapping.getKotlinFunction(r1)
            return r9
        Led:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.utsplugin.ProxyModule.findTargetFunc(java.lang.Class, boolean, java.lang.String, io.dcloud.feature.utsplugin.ProxyModule$InputOption):kotlin.reflect.KFunction");
    }

    /* compiled from: UTSProxyModule.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J(\u0010\u0010\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00112\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR&\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0018"}, d2 = {"Lio/dcloud/feature/utsplugin/ProxyModule$Companion;", "", "<init>", "()V", "instanceDynamicId", "", "getInstanceDynamicId", "()I", "setInstanceDynamicId", "(I)V", "utsInstances", "", "getUtsInstances", "()Ljava/util/Map;", "setUtsInstances", "(Ljava/util/Map;)V", "findTargetField", "Lkotlin/reflect/KCallable;", "javaClazz", "Ljava/lang/Class;", "isCompanion", "", "methodName", "", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int getInstanceDynamicId() {
            return ProxyModule.instanceDynamicId;
        }

        public final void setInstanceDynamicId(int i) {
            ProxyModule.instanceDynamicId = i;
        }

        public final Map<Integer, Object> getUtsInstances() {
            return ProxyModule.utsInstances;
        }

        public final void setUtsInstances(Map<Integer, Object> map) {
            Intrinsics.checkNotNullParameter(map, "<set-?>");
            ProxyModule.utsInstances = map;
        }

        public final KCallable<?> findTargetField(Class<?> javaClazz, boolean isCompanion, String methodName) {
            Field[] declaredFields;
            Collection<KProperty1> memberProperties;
            Intrinsics.checkNotNullParameter(javaClazz, "javaClazz");
            Intrinsics.checkNotNullParameter(methodName, "methodName");
            if (isCompanion) {
                KClass<?> companionObject = KClasses.getCompanionObject(JvmClassMappingKt.getKotlinClass(javaClazz));
                Intrinsics.checkNotNull(companionObject);
                for (KCallable<?> kCallable : companionObject.getMembers()) {
                    if (Intrinsics.areEqual(methodName, kCallable.getName())) {
                        return kCallable;
                    }
                }
                return null;
            }
            try {
                memberProperties = KClasses.getMemberProperties(JvmClassMappingKt.getKotlinClass(javaClazz));
                declaredFields = null;
            } catch (Exception unused) {
                declaredFields = javaClazz.getDeclaredFields();
                memberProperties = null;
            }
            if (memberProperties != null) {
                for (KProperty1 kProperty1 : memberProperties) {
                    if (Intrinsics.areEqual(methodName, kProperty1.getName())) {
                        return kProperty1;
                    }
                }
                return null;
            }
            if (declaredFields == null || declaredFields.length == 0) {
                return null;
            }
            Iterator it = ArrayIteratorKt.iterator(declaredFields);
            while (it.hasNext()) {
                Field field = (Field) it.next();
                if (Intrinsics.areEqual(methodName, field.getName())) {
                    return ReflectJvmMapping.getKotlinProperty(field);
                }
            }
            return null;
        }
    }

    /* compiled from: UTSProxyModule.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0011\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J*\u0010;\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010<2\u0006\u0010=\u001a\u00020>2\b\u0010?\u001a\u0004\u0018\u00010@2\b\b\u0002\u0010\"\u001a\u00020\u001cJ0\u0010;\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010<2\n\u0010=\u001a\u0006\u0012\u0002\b\u00030A2\b\u0010?\u001a\u0004\u0018\u00010@2\b\b\u0002\u0010\"\u001a\u00020\u001cH\u0016J\u0006\u0010B\u001a\u00020\u0007J\u0006\u0010D\u001a\u00020\u001cJ\u0006\u0010E\u001a\u00020\u0007R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\t\"\u0004\b\u000e\u0010\u000bR\u001a\u0010\u000f\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\t\"\u0004\b\u0011\u0010\u000bR\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0015\"\u0004\b\u001a\u0010\u0017R\u001a\u0010\u001b\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u001d\"\u0004\b!\u0010\u001fR\u001a\u0010\"\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u001d\"\u0004\b#\u0010\u001fR\u001a\u0010$\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\t\"\u0004\b&\u0010\u000bR\u001a\u0010'\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\u001d\"\u0004\b(\u0010\u001fR\u001a\u0010)\u001a\u00020*X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u001a\u0010/\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\t\"\u0004\b1\u0010\u000bR\u001a\u00102\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\t\"\u0004\b4\u0010\u000bR\u001a\u00105\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u0010\u001d\"\u0004\b7\u0010\u001fR\u001a\u00108\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010\t\"\u0004\b:\u0010\u000bR\u000e\u0010C\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006F"}, d2 = {"Lio/dcloud/feature/utsplugin/ProxyModule$InputOption;", "", "options", "Lcom/alibaba/fastjson/JSONObject;", "<init>", "(Lcom/alibaba/fastjson/JSONObject;)V", "packageName", "", "getPackageName", "()Ljava/lang/String;", "setPackageName", "(Ljava/lang/String;)V", "className", "getClassName", "setClassName", "methodName", "getMethodName", "setMethodName", "paramArray", "Lcom/alibaba/fastjson/JSONArray;", "getParamArray", "()Lcom/alibaba/fastjson/JSONArray;", "setParamArray", "(Lcom/alibaba/fastjson/JSONArray;)V", "methodArray", "getMethodArray", "setMethodArray", "isConstructor", "", "()Z", "setConstructor", "(Z)V", "isCompanion", "setCompanion", "isField", "setField", "moduleName", "getModuleName", "setModuleName", "isInstanceAction", "setInstanceAction", "instanceId", "", "getInstanceId", "()I", "setInstanceId", "(I)V", "inputModuleName", "getInputModuleName", "setInputModuleName", "inputModuleType", "getInputModuleType", "setInputModuleType", "keepAlive", "getKeepAlive", "setKeepAlive", "type", "getType", "setType", "obtainParamsWithDefault", "", "targetFunction", "Ljava/lang/reflect/Method;", WXBridgeManager.METHOD_CALLBACK, "Lcom/taobao/weex/bridge/JSCallback;", "Lkotlin/reflect/KFunction;", "getInputFlag", "checkErrorMsg", "isValid", "getErrorMsg", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class InputOption {
        private String checkErrorMsg;
        private String className;
        private String inputModuleName;
        private String inputModuleType;
        private int instanceId;
        private boolean isCompanion;
        private boolean isConstructor;
        private boolean isField;
        private boolean isInstanceAction;
        private boolean keepAlive;
        private JSONArray methodArray;
        private String methodName;
        private String moduleName;
        private String packageName;
        private JSONArray paramArray;
        private String type;

        public final String getPackageName() {
            return this.packageName;
        }

        public final void setPackageName(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.packageName = str;
        }

        public final String getClassName() {
            return this.className;
        }

        public final void setClassName(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.className = str;
        }

        public final String getMethodName() {
            return this.methodName;
        }

        public final void setMethodName(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.methodName = str;
        }

        public final JSONArray getParamArray() {
            return this.paramArray;
        }

        public final void setParamArray(JSONArray jSONArray) {
            Intrinsics.checkNotNullParameter(jSONArray, "<set-?>");
            this.paramArray = jSONArray;
        }

        public final JSONArray getMethodArray() {
            return this.methodArray;
        }

        public final void setMethodArray(JSONArray jSONArray) {
            Intrinsics.checkNotNullParameter(jSONArray, "<set-?>");
            this.methodArray = jSONArray;
        }

        /* renamed from: isConstructor, reason: from getter */
        public final boolean getIsConstructor() {
            return this.isConstructor;
        }

        public final void setConstructor(boolean z) {
            this.isConstructor = z;
        }

        /* renamed from: isCompanion, reason: from getter */
        public final boolean getIsCompanion() {
            return this.isCompanion;
        }

        public final void setCompanion(boolean z) {
            this.isCompanion = z;
        }

        /* renamed from: isField, reason: from getter */
        public final boolean getIsField() {
            return this.isField;
        }

        public final void setField(boolean z) {
            this.isField = z;
        }

        public final String getModuleName() {
            return this.moduleName;
        }

        public final void setModuleName(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.moduleName = str;
        }

        /* renamed from: isInstanceAction, reason: from getter */
        public final boolean getIsInstanceAction() {
            return this.isInstanceAction;
        }

        public final void setInstanceAction(boolean z) {
            this.isInstanceAction = z;
        }

        public final int getInstanceId() {
            return this.instanceId;
        }

        public final void setInstanceId(int i) {
            this.instanceId = i;
        }

        public final String getInputModuleName() {
            return this.inputModuleName;
        }

        public final void setInputModuleName(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.inputModuleName = str;
        }

        public final String getInputModuleType() {
            return this.inputModuleType;
        }

        public final void setInputModuleType(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.inputModuleType = str;
        }

        public final boolean getKeepAlive() {
            return this.keepAlive;
        }

        public final void setKeepAlive(boolean z) {
            this.keepAlive = z;
        }

        public final String getType() {
            return this.type;
        }

        public final void setType(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.type = str;
        }

        public InputOption(JSONObject options) {
            Intrinsics.checkNotNullParameter(options, "options");
            this.packageName = "";
            this.className = "";
            this.methodName = "";
            this.paramArray = new JSONArray();
            this.methodArray = new JSONArray();
            this.moduleName = "";
            this.inputModuleName = "";
            this.inputModuleType = "";
            this.type = "";
            this.checkErrorMsg = "";
            if (options.containsKey("moduleName")) {
                Object obj = options.get("moduleName");
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
                this.inputModuleName = (String) obj;
            }
            if (options.containsKey("moduleType")) {
                Object obj2 = options.get("moduleType");
                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
                this.inputModuleType = (String) obj2;
            }
            if (options.containsKey("keepAlive")) {
                Object obj3 = options.get("keepAlive");
                Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Boolean");
                this.keepAlive = ((Boolean) obj3).booleanValue();
            }
            if (options.containsKey("package")) {
                Object obj4 = options.get("package");
                Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.String");
                this.packageName = (String) obj4;
            }
            if (options.containsKey("class")) {
                Object obj5 = options.get("class");
                Intrinsics.checkNotNull(obj5, "null cannot be cast to non-null type kotlin.String");
                this.className = (String) obj5;
            }
            if (options.containsKey("params")) {
                Object obj6 = options.get("params");
                Intrinsics.checkNotNull(obj6, "null cannot be cast to non-null type com.alibaba.fastjson.JSONArray");
                this.paramArray = (JSONArray) obj6;
                this.isField = false;
            } else {
                this.isField = true;
            }
            if (options.containsKey("name")) {
                Object obj7 = options.get("name");
                Intrinsics.checkNotNull(obj7, "null cannot be cast to non-null type kotlin.String");
                this.methodName = (String) obj7;
            }
            if (options.containsKey("id")) {
                Object obj8 = options.get("id");
                Intrinsics.checkNotNull(obj8, "null cannot be cast to non-null type kotlin.Int");
                this.instanceId = ((Integer) obj8).intValue();
                this.isInstanceAction = true;
                this.moduleName = this.methodName;
            } else {
                this.isInstanceAction = false;
                if (options.containsKey("companion")) {
                    Object obj9 = options.get("companion");
                    Intrinsics.checkNotNull(obj9, "null cannot be cast to non-null type kotlin.Boolean");
                    this.isCompanion = ((Boolean) obj9).booleanValue();
                } else {
                    this.isCompanion = false;
                }
                if (!this.isField && Intrinsics.areEqual("constructor", this.methodName)) {
                    this.isConstructor = true;
                }
                this.moduleName = String.valueOf(this.packageName);
                if (!TextUtils.isEmpty(this.className)) {
                    this.moduleName = this.packageName + Operators.DOT + this.className;
                }
            }
            if (options.containsKey("method")) {
                JSONArray jSONArray = options.getJSONArray("method");
                Intrinsics.checkNotNullExpressionValue(jSONArray, "getJSONArray(...)");
                this.methodArray = jSONArray;
            }
            if (options.containsKey("type")) {
                String string = options.getString("type");
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                this.type = string;
            }
        }

        public static /* synthetic */ List obtainParamsWithDefault$default(InputOption inputOption, Method method, JSCallback jSCallback, boolean z, int i, Object obj) {
            if ((i & 4) != 0) {
                z = false;
            }
            return inputOption.obtainParamsWithDefault(method, jSCallback, z);
        }

        /* JADX WARN: Removed duplicated region for block: B:17:0x0086  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x00ae  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.util.List<java.lang.Object> obtainParamsWithDefault(java.lang.reflect.Method r10, com.taobao.weex.bridge.JSCallback r11, boolean r12) {
            /*
                r9 = this;
                java.lang.String r0 = "targetFunction"
                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r10, r0)
                java.util.ArrayList r0 = new java.util.ArrayList
                r0.<init>()
                java.util.List r0 = (java.util.List) r0
                java.lang.reflect.Parameter[] r1 = io.dcloud.p.f4$$ExternalSyntheticApiModelOutline0.m(r10)
                int r1 = r1.length
                if (r1 <= 0) goto Lc1
                java.lang.reflect.Parameter[] r10 = io.dcloud.p.f4$$ExternalSyntheticApiModelOutline0.m(r10)
                java.util.Iterator r10 = kotlin.jvm.internal.ArrayIteratorKt.iterator(r10)
                r1 = 0
                r2 = 0
            L1e:
                boolean r3 = r10.hasNext()
                if (r3 == 0) goto Lc1
                java.lang.Object r3 = r10.next()
                java.lang.reflect.Parameter r3 = io.dcloud.p.f4$$ExternalSyntheticApiModelOutline0.m(r3)
                r4 = 0
                if (r12 == 0) goto L3f
                java.lang.Class r3 = io.dcloud.p.f4$$ExternalSyntheticApiModelOutline0.m(r3)
                java.lang.String r3 = r3.toString()
                java.lang.String r5 = "toString(...)"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r5)
            L3d:
                r5 = r4
                goto L77
            L3f:
                java.lang.Class r5 = io.dcloud.p.f4$$ExternalSyntheticApiModelOutline0.m(r3)
                if (r5 == 0) goto L58
                java.lang.Class r3 = io.dcloud.p.f4$$ExternalSyntheticApiModelOutline0.m(r3)
                java.lang.String r5 = "null cannot be cast to non-null type java.lang.Class<*>"
                kotlin.jvm.internal.Intrinsics.checkNotNull(r3, r5)
                java.lang.String r3 = r3.getName()
                java.lang.String r5 = "getName(...)"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r5)
                goto L3d
            L58:
                java.lang.Class r5 = io.dcloud.p.f4$$ExternalSyntheticApiModelOutline0.m(r3)
                java.lang.String r5 = io.dcloud.p.f4$$ExternalSyntheticApiModelOutline0.m(r5)
                java.lang.String r6 = "getTypeName(...)"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r6)
                java.lang.Class r3 = io.dcloud.p.f4$$ExternalSyntheticApiModelOutline0.m(r3)
                java.lang.String r6 = "null cannot be cast to non-null type java.lang.reflect.ParameterizedType"
                kotlin.jvm.internal.Intrinsics.checkNotNull(r3, r6)
                java.lang.reflect.ParameterizedType r3 = (java.lang.reflect.ParameterizedType) r3
                java.lang.reflect.Type[] r3 = r3.getActualTypeArguments()
                r8 = r5
                r5 = r3
                r3 = r8
            L77:
                io.dcloud.feature.utsplugin.ParamConvertHelper r6 = new io.dcloud.feature.utsplugin.ParamConvertHelper
                boolean r7 = r9.keepAlive
                r6.<init>(r3, r5, r11, r7)
                com.alibaba.fastjson.JSONArray r3 = r9.paramArray
                int r3 = r3.size()
                if (r1 < r3) goto Lae
                com.alibaba.fastjson.JSONArray r3 = r9.methodArray
                java.lang.Object r3 = r3.get(r2)
                java.lang.String r5 = "null cannot be cast to non-null type com.alibaba.fastjson.JSONObject"
                kotlin.jvm.internal.Intrinsics.checkNotNull(r3, r5)
                com.alibaba.fastjson.JSONObject r3 = (com.alibaba.fastjson.JSONObject) r3
                java.lang.String r5 = "default"
                java.lang.Object r7 = r3.get(r5)
                if (r7 != 0) goto L9f
                r0.add(r4)
                goto Lbd
            L9f:
                java.lang.Object r3 = r3.get(r5)
                kotlin.jvm.internal.Intrinsics.checkNotNull(r3)
                java.lang.Object r3 = r6.getInstance(r3)
                r0.add(r3)
                goto Lbd
            Lae:
                com.alibaba.fastjson.JSONArray r3 = r9.paramArray
                java.lang.Object r3 = r3.get(r1)
                java.lang.Object r3 = r6.getInstance(r3)
                r0.add(r3)
                int r1 = r1 + 1
            Lbd:
                int r2 = r2 + 1
                goto L1e
            Lc1:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.utsplugin.ProxyModule.InputOption.obtainParamsWithDefault(java.lang.reflect.Method, com.taobao.weex.bridge.JSCallback, boolean):java.util.List");
        }

        public static /* synthetic */ List obtainParamsWithDefault$default(InputOption inputOption, KFunction kFunction, JSCallback jSCallback, boolean z, int i, Object obj) {
            if ((i & 4) != 0) {
                z = false;
            }
            return inputOption.obtainParamsWithDefault((KFunction<?>) kFunction, jSCallback, z);
        }

        /* JADX WARN: Removed duplicated region for block: B:24:0x00ab  */
        /* JADX WARN: Removed duplicated region for block: B:28:0x00d3  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public java.util.List<java.lang.Object> obtainParamsWithDefault(kotlin.reflect.KFunction<?> r10, com.taobao.weex.bridge.JSCallback r11, boolean r12) {
            /*
                r9 = this;
                java.lang.String r0 = "targetFunction"
                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r10, r0)
                java.util.ArrayList r0 = new java.util.ArrayList
                r0.<init>()
                java.util.List r0 = (java.util.List) r0
                java.util.List r1 = r10.getParameters()
                int r1 = r1.size()
                if (r1 <= 0) goto Le6
                java.util.List r10 = r10.getParameters()
                java.util.Iterator r10 = r10.iterator()
                r1 = 0
                r2 = 0
            L21:
                boolean r3 = r10.hasNext()
                if (r3 == 0) goto Le6
                java.lang.Object r3 = r10.next()
                kotlin.reflect.KParameter r3 = (kotlin.reflect.KParameter) r3
                kotlin.reflect.KParameter$Kind r4 = r3.getKind()
                kotlin.reflect.KParameter$Kind r5 = kotlin.reflect.KParameter.Kind.INSTANCE
                if (r4 != r5) goto L43
                java.lang.String r4 = r3.getName()
                if (r4 != 0) goto Le2
                boolean r3 = r3.isOptional()
                if (r3 == 0) goto L21
                goto Le2
            L43:
                r4 = 0
                if (r12 == 0) goto L50
                kotlin.reflect.KType r3 = r3.getType()
                java.lang.String r3 = r3.toString()
            L4e:
                r5 = r4
                goto L9c
            L50:
                kotlin.reflect.KType r5 = r3.getType()
                java.lang.reflect.Type r5 = kotlin.reflect.jvm.ReflectJvmMapping.getJavaType(r5)
                boolean r5 = r5 instanceof java.lang.Class
                if (r5 == 0) goto L75
                kotlin.reflect.KType r3 = r3.getType()
                java.lang.reflect.Type r3 = kotlin.reflect.jvm.ReflectJvmMapping.getJavaType(r3)
                java.lang.String r5 = "null cannot be cast to non-null type java.lang.Class<*>"
                kotlin.jvm.internal.Intrinsics.checkNotNull(r3, r5)
                java.lang.Class r3 = (java.lang.Class) r3
                java.lang.String r3 = r3.getName()
                java.lang.String r5 = "getName(...)"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r5)
                goto L4e
            L75:
                kotlin.reflect.KType r5 = r3.getType()
                java.lang.reflect.Type r5 = kotlin.reflect.jvm.ReflectJvmMapping.getJavaType(r5)
                java.lang.String r5 = io.dcloud.p.f4$$ExternalSyntheticApiModelOutline0.m(r5)
                java.lang.String r6 = "getTypeName(...)"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r6)
                kotlin.reflect.KType r3 = r3.getType()
                java.lang.reflect.Type r3 = kotlin.reflect.jvm.ReflectJvmMapping.getJavaType(r3)
                java.lang.String r6 = "null cannot be cast to non-null type java.lang.reflect.ParameterizedType"
                kotlin.jvm.internal.Intrinsics.checkNotNull(r3, r6)
                java.lang.reflect.ParameterizedType r3 = (java.lang.reflect.ParameterizedType) r3
                java.lang.reflect.Type[] r3 = r3.getActualTypeArguments()
                r8 = r5
                r5 = r3
                r3 = r8
            L9c:
                io.dcloud.feature.utsplugin.ParamConvertHelper r6 = new io.dcloud.feature.utsplugin.ParamConvertHelper
                boolean r7 = r9.keepAlive
                r6.<init>(r3, r5, r11, r7)
                com.alibaba.fastjson.JSONArray r3 = r9.paramArray
                int r3 = r3.size()
                if (r1 < r3) goto Ld3
                com.alibaba.fastjson.JSONArray r3 = r9.methodArray
                java.lang.Object r3 = r3.get(r2)
                java.lang.String r5 = "null cannot be cast to non-null type com.alibaba.fastjson.JSONObject"
                kotlin.jvm.internal.Intrinsics.checkNotNull(r3, r5)
                com.alibaba.fastjson.JSONObject r3 = (com.alibaba.fastjson.JSONObject) r3
                java.lang.String r5 = "default"
                java.lang.Object r7 = r3.get(r5)
                if (r7 != 0) goto Lc4
                r0.add(r4)
                goto Le2
            Lc4:
                java.lang.Object r3 = r3.get(r5)
                kotlin.jvm.internal.Intrinsics.checkNotNull(r3)
                java.lang.Object r3 = r6.getInstance(r3)
                r0.add(r3)
                goto Le2
            Ld3:
                com.alibaba.fastjson.JSONArray r3 = r9.paramArray
                java.lang.Object r3 = r3.get(r1)
                java.lang.Object r3 = r6.getInstance(r3)
                r0.add(r3)
                int r1 = r1 + 1
            Le2:
                int r2 = r2 + 1
                goto L21
            Le6:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.utsplugin.ProxyModule.InputOption.obtainParamsWithDefault(kotlin.reflect.KFunction, com.taobao.weex.bridge.JSCallback, boolean):java.util.List");
        }

        public final String getInputFlag() {
            return Operators.ARRAY_START_STR + this.moduleName + '-' + this.methodName + Operators.ARRAY_END;
        }

        public final boolean isValid() {
            if (!TextUtils.isEmpty(this.packageName) && !StringsKt.startsWith$default(this.packageName, "uts.sdk.modules", false, 2, (Object) null) && !StringsKt.startsWith$default(this.packageName, "uts.sdk", false, 2, (Object) null) && !StringsKt.startsWith$default(this.packageName, "uts.modules", false, 2, (Object) null)) {
                return false;
            }
            if (this.isInstanceAction) {
                return true;
            }
            if (!TextUtils.isEmpty(this.moduleName) && !TextUtils.isEmpty(this.methodName)) {
                return true;
            }
            this.checkErrorMsg = "param is null " + this.moduleName + ' ' + this.methodName;
            return false;
        }

        /* renamed from: getErrorMsg, reason: from getter */
        public final String getCheckErrorMsg() {
            return this.checkErrorMsg;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object getFunctionExecuteRet(kotlin.reflect.KFunction<?> r7, java.lang.Object[] r8, kotlin.coroutines.Continuation<java.lang.Object> r9) {
        /*
            r6 = this;
            boolean r0 = r9 instanceof io.dcloud.feature.utsplugin.ProxyModule.C01001
            if (r0 == 0) goto L14
            r0 = r9
            io.dcloud.feature.utsplugin.ProxyModule$getFunctionExecuteRet$1 r0 = (io.dcloud.feature.utsplugin.ProxyModule.C01001) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L19
        L14:
            io.dcloud.feature.utsplugin.ProxyModule$getFunctionExecuteRet$1 r0 = new io.dcloud.feature.utsplugin.ProxyModule$getFunctionExecuteRet$1
            r0.<init>(r9)
        L19:
            java.lang.Object r9 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 3
            r4 = 2
            r5 = 1
            if (r2 == 0) goto L52
            if (r2 == r5) goto L45
            if (r2 == r4) goto L40
            if (r2 != r3) goto L38
        L2c:
            java.lang.Object r7 = r0.L$1
            java.lang.Object[] r7 = (java.lang.Object[]) r7
            java.lang.Object r7 = r0.L$0
            kotlin.reflect.KFunction r7 = (kotlin.reflect.KFunction) r7
            kotlin.ResultKt.throwOnFailure(r9)
            return r9
        L38:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L40:
            java.lang.Object r7 = r0.L$2
            kotlinx.coroutines.Deferred r7 = (kotlinx.coroutines.Deferred) r7
            goto L2c
        L45:
            java.lang.Object r7 = r0.L$1
            r8 = r7
            java.lang.Object[] r8 = (java.lang.Object[]) r8
            java.lang.Object r7 = r0.L$0
            kotlin.reflect.KFunction r7 = (kotlin.reflect.KFunction) r7
            kotlin.ResultKt.throwOnFailure(r9)
            goto L78
        L52:
            kotlin.ResultKt.throwOnFailure(r9)
            boolean r9 = r7.isSuspend()
            if (r9 == 0) goto L9b
            r9 = r7
            kotlin.reflect.KCallable r9 = (kotlin.reflect.KCallable) r9
            int r2 = r8.length
            java.lang.Object[] r2 = java.util.Arrays.copyOf(r8, r2)
            java.lang.Object r3 = kotlin.coroutines.jvm.internal.SpillingKt.nullOutSpilledVariable(r7)
            r0.L$0 = r3
            java.lang.Object r3 = kotlin.coroutines.jvm.internal.SpillingKt.nullOutSpilledVariable(r8)
            r0.L$1 = r3
            r0.label = r5
            java.lang.Object r9 = r6.callSuspend(r9, r2, r0)
            if (r9 != r1) goto L78
            goto Lb7
        L78:
            java.lang.String r2 = "null cannot be cast to non-null type kotlinx.coroutines.Deferred<kotlin.Any?>"
            kotlin.jvm.internal.Intrinsics.checkNotNull(r9, r2)
            kotlinx.coroutines.Deferred r9 = (kotlinx.coroutines.Deferred) r9
            java.lang.Object r7 = kotlin.coroutines.jvm.internal.SpillingKt.nullOutSpilledVariable(r7)
            r0.L$0 = r7
            java.lang.Object r7 = kotlin.coroutines.jvm.internal.SpillingKt.nullOutSpilledVariable(r8)
            r0.L$1 = r7
            java.lang.Object r7 = kotlin.coroutines.jvm.internal.SpillingKt.nullOutSpilledVariable(r9)
            r0.L$2 = r7
            r0.label = r4
            java.lang.Object r7 = r9.await(r0)
            if (r7 != r1) goto L9a
            goto Lb7
        L9a:
            return r7
        L9b:
            r9 = r7
            kotlin.reflect.KCallable r9 = (kotlin.reflect.KCallable) r9
            int r2 = r8.length
            java.lang.Object[] r2 = java.util.Arrays.copyOf(r8, r2)
            java.lang.Object r7 = kotlin.coroutines.jvm.internal.SpillingKt.nullOutSpilledVariable(r7)
            r0.L$0 = r7
            java.lang.Object r7 = kotlin.coroutines.jvm.internal.SpillingKt.nullOutSpilledVariable(r8)
            r0.L$1 = r7
            r0.label = r3
            java.lang.Object r7 = r6.callSuspend(r9, r2, r0)
            if (r7 != r1) goto Lb8
        Lb7:
            return r1
        Lb8:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.utsplugin.ProxyModule.getFunctionExecuteRet(kotlin.reflect.KFunction, java.lang.Object[], kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final JSONObject wrapDoTypeAction(InputOption inputOption, Class<?> javaClazz, Object targetInstance, JSCallback callback, ReturnResult errRet) {
        String strValueOf;
        String methodName = inputOption.getMethodName();
        if (StringsKt.equals("setter", inputOption.getType(), true)) {
            StringBuilder sb = new StringBuilder("set");
            if (methodName.length() > 0) {
                StringBuilder sb2 = new StringBuilder();
                char cCharAt = methodName.charAt(0);
                if (Character.isLowerCase(cCharAt)) {
                    Locale locale = Locale.getDefault();
                    Intrinsics.checkNotNullExpressionValue(locale, "getDefault(...)");
                    strValueOf = CharsKt.titlecase(cCharAt, locale);
                } else {
                    strValueOf = String.valueOf(cCharAt);
                }
                sb2.append((Object) strValueOf);
                String strSubstring = methodName.substring(1);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                sb2.append(strSubstring);
                methodName = sb2.toString();
            }
            sb.append(methodName);
            Method methodFindTargetMethod = findTargetMethod(javaClazz, sb.toString(), inputOption);
            if (methodFindTargetMethod == null) {
                errRet.updateError("method not found:" + inputOption.getInputFlag());
                return errRet.toJSON();
            }
            List listObtainParamsWithDefault$default = InputOption.obtainParamsWithDefault$default(inputOption, methodFindTargetMethod, callback, false, 4, (Object) null);
            Ref.ObjectRef objectRef = new Ref.ObjectRef();
            try {
                BuildersKt__BuildersKt.runBlocking$default(null, new C01022(objectRef, errRet, methodFindTargetMethod, targetInstance, listObtainParamsWithDefault$default, null), 1, null);
            } catch (Exception e) {
                objectRef.element = null;
                if (e.getCause() != null) {
                    errRet.updateError("targetMethod error::" + e.getCause());
                } else {
                    errRet.updateError("targetMethod error::" + e);
                }
            }
            if (objectRef.element != 0) {
                if (objectRef.element instanceof UTSJSONObject) {
                    T t = objectRef.element;
                    Intrinsics.checkNotNull(t, "null cannot be cast to non-null type io.dcloud.uts.UTSJSONObject");
                    errRet.updateJSON(((UTSJSONObject) t).toJSONObject());
                } else {
                    errRet.updateJSON(objectRef.element);
                }
                errRet.toJSON();
            }
        }
        return null;
    }

    /* compiled from: UTSProxyModule.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Lkotlinx/coroutines/Job;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "io.dcloud.feature.utsplugin.ProxyModule$wrapDoTypeAction$2", f = "UTSProxyModule.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: io.dcloud.feature.utsplugin.ProxyModule$wrapDoTypeAction$2, reason: invalid class name and case insensitive filesystem */
    static final class C01022 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Job>, Object> {
        final /* synthetic */ ReturnResult $errRet;
        final /* synthetic */ Ref.ObjectRef<Object> $executeRet;
        final /* synthetic */ List<Object> $paramList;
        final /* synthetic */ Object $targetInstance;
        final /* synthetic */ Method $targetMethod;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01022(Ref.ObjectRef<Object> objectRef, ReturnResult returnResult, Method method, Object obj, List<Object> list, Continuation<? super C01022> continuation) {
            super(2, continuation);
            this.$executeRet = objectRef;
            this.$errRet = returnResult;
            this.$targetMethod = method;
            this.$targetInstance = obj;
            this.$paramList = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C01022 c01022 = new C01022(this.$executeRet, this.$errRet, this.$targetMethod, this.$targetInstance, this.$paramList, continuation);
            c01022.L$0 = obj;
            return c01022;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Job> continuation) {
            return ((C01022) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return BuildersKt__Builders_commonKt.launch$default(coroutineScope, new ProxyModule$wrapDoTypeAction$2$invokeSuspend$$inlined$CoroutineExceptionHandler$1(CoroutineExceptionHandler.INSTANCE, this.$executeRet, this.$errRet), null, new C00472(this.$executeRet, this.$targetMethod, this.$targetInstance, this.$paramList, null), 2, null);
        }

        /* compiled from: UTSProxyModule.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "io.dcloud.feature.utsplugin.ProxyModule$wrapDoTypeAction$2$2", f = "UTSProxyModule.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: io.dcloud.feature.utsplugin.ProxyModule$wrapDoTypeAction$2$2, reason: invalid class name and collision with other inner class name */
        static final class C00472 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Ref.ObjectRef<Object> $executeRet;
            final /* synthetic */ List<Object> $paramList;
            final /* synthetic */ Object $targetInstance;
            final /* synthetic */ Method $targetMethod;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00472(Ref.ObjectRef<Object> objectRef, Method method, Object obj, List<Object> list, Continuation<? super C00472> continuation) {
                super(2, continuation);
                this.$executeRet = objectRef;
                this.$targetMethod = method;
                this.$targetInstance = obj;
                this.$paramList = list;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00472(this.$executeRet, this.$targetMethod, this.$targetInstance, this.$paramList, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00472) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Type inference failed for: r0v3, types: [T, java.lang.Object] */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Ref.ObjectRef<Object> objectRef = this.$executeRet;
                Method method = this.$targetMethod;
                Object obj2 = this.$targetInstance;
                Object[] array = this.$paramList.toArray(new Object[0]);
                objectRef.element = method.invoke(obj2, Arrays.copyOf(array, array.length));
                return Unit.INSTANCE;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:135:0x041c A[Catch: Exception -> 0x0448, TryCatch #9 {Exception -> 0x0448, blocks: (B:133:0x0418, B:135:0x041c, B:137:0x0422, B:138:0x0435, B:139:0x043f, B:127:0x03c1, B:129:0x03c9, B:131:0x03f4, B:130:0x03e1, B:132:0x03f7), top: B:166:0x03ab }] */
    /* JADX WARN: Removed duplicated region for block: B:139:0x043f A[Catch: Exception -> 0x0448, TRY_LEAVE, TryCatch #9 {Exception -> 0x0448, blocks: (B:133:0x0418, B:135:0x041c, B:137:0x0422, B:138:0x0435, B:139:0x043f, B:127:0x03c1, B:129:0x03c9, B:131:0x03f4, B:130:0x03e1, B:132:0x03f7), top: B:166:0x03ab }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x01af A[Catch: Exception -> 0x044a, TryCatch #6 {Exception -> 0x044a, blocks: (B:52:0x01ab, B:54:0x01af, B:56:0x01b5, B:57:0x01c8, B:59:0x01d4, B:48:0x0178, B:50:0x0180, B:51:0x0198, B:63:0x01e1, B:64:0x01e9, B:78:0x0209, B:80:0x0214, B:82:0x0219, B:84:0x025d, B:83:0x0241, B:86:0x0266, B:88:0x026c, B:90:0x027c, B:92:0x029c, B:94:0x02a2, B:96:0x02bb, B:95:0x02b3, B:98:0x02cb, B:100:0x02d1, B:102:0x02df, B:104:0x0312, B:103:0x02e9, B:106:0x0330, B:108:0x034c, B:110:0x035b, B:112:0x035f, B:114:0x037f, B:116:0x0397, B:117:0x03a6, B:72:0x01fd, B:74:0x0201, B:69:0x01f2), top: B:161:0x0050, inners: #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01d4 A[Catch: Exception -> 0x044a, TryCatch #6 {Exception -> 0x044a, blocks: (B:52:0x01ab, B:54:0x01af, B:56:0x01b5, B:57:0x01c8, B:59:0x01d4, B:48:0x0178, B:50:0x0180, B:51:0x0198, B:63:0x01e1, B:64:0x01e9, B:78:0x0209, B:80:0x0214, B:82:0x0219, B:84:0x025d, B:83:0x0241, B:86:0x0266, B:88:0x026c, B:90:0x027c, B:92:0x029c, B:94:0x02a2, B:96:0x02bb, B:95:0x02b3, B:98:0x02cb, B:100:0x02d1, B:102:0x02df, B:104:0x0312, B:103:0x02e9, B:106:0x0330, B:108:0x034c, B:110:0x035b, B:112:0x035f, B:114:0x037f, B:116:0x0397, B:117:0x03a6, B:72:0x01fd, B:74:0x0201, B:69:0x01f2), top: B:161:0x0050, inners: #10 }] */
    /* JADX WARN: Type inference failed for: r0v0, types: [T, io.dcloud.feature.utsplugin.ReturnResult] */
    /* JADX WARN: Type inference failed for: r0v92, types: [T, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v18, types: [T, java.util.List] */
    /* JADX WARN: Type inference failed for: r3v13, types: [T, kotlin.reflect.KFunction] */
    /* JADX WARN: Type inference failed for: r7v4, types: [T, kotlin.reflect.KFunction] */
    /* JADX WARN: Type inference failed for: r9v4, types: [T, java.util.List] */
    @io.dcloud.feature.uniapp.annotation.UniJSMethod(uiThread = false)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSync(com.alibaba.fastjson.JSONObject r18, com.taobao.weex.bridge.JSCallback r19, kotlin.coroutines.Continuation<java.lang.Object> r20) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 1123
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.utsplugin.ProxyModule.invokeSync(com.alibaba.fastjson.JSONObject, com.taobao.weex.bridge.JSCallback, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* compiled from: UTSProxyModule.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Lkotlinx/coroutines/Job;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "io.dcloud.feature.utsplugin.ProxyModule$invokeSync$2", f = "UTSProxyModule.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: io.dcloud.feature.utsplugin.ProxyModule$invokeSync$2, reason: invalid class name and case insensitive filesystem */
    static final class C01012 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Job>, Object> {
        final /* synthetic */ Ref.ObjectRef<ReturnResult> $errRet;
        final /* synthetic */ Ref.ObjectRef<Object> $executeRet;
        final /* synthetic */ InputOption $inputOption;
        final /* synthetic */ Class<Object> $javaClazz;
        final /* synthetic */ Ref.ObjectRef<List<Object>> $paramList;
        final /* synthetic */ KFunction<?> $targetFunction;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ ProxyModule this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01012(Ref.ObjectRef<Object> objectRef, Ref.ObjectRef<ReturnResult> objectRef2, InputOption inputOption, Ref.ObjectRef<List<Object>> objectRef3, Class<Object> cls, ProxyModule proxyModule, KFunction<?> kFunction, Continuation<? super C01012> continuation) {
            super(2, continuation);
            this.$executeRet = objectRef;
            this.$errRet = objectRef2;
            this.$inputOption = inputOption;
            this.$paramList = objectRef3;
            this.$javaClazz = cls;
            this.this$0 = proxyModule;
            this.$targetFunction = kFunction;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C01012 c01012 = new C01012(this.$executeRet, this.$errRet, this.$inputOption, this.$paramList, this.$javaClazz, this.this$0, this.$targetFunction, continuation);
            c01012.L$0 = obj;
            return c01012;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Job> continuation) {
            return ((C01012) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return BuildersKt__Builders_commonKt.launch$default(coroutineScope, new ProxyModule$invokeSync$2$invokeSuspend$$inlined$CoroutineExceptionHandler$1(CoroutineExceptionHandler.INSTANCE, this.$executeRet, this.$errRet), null, new C00462(this.$inputOption, this.$paramList, this.$javaClazz, this.$executeRet, this.this$0, this.$targetFunction, null), 2, null);
        }

        /* compiled from: UTSProxyModule.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "io.dcloud.feature.utsplugin.ProxyModule$invokeSync$2$2", f = "UTSProxyModule.kt", i = {0}, l = {846}, m = "invokeSuspend", n = {"paramArray"}, s = {"L$0"})
        /* renamed from: io.dcloud.feature.utsplugin.ProxyModule$invokeSync$2$2, reason: invalid class name and collision with other inner class name */
        static final class C00462 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Ref.ObjectRef<Object> $executeRet;
            final /* synthetic */ InputOption $inputOption;
            final /* synthetic */ Class<Object> $javaClazz;
            final /* synthetic */ Ref.ObjectRef<List<Object>> $paramList;
            final /* synthetic */ KFunction<?> $targetFunction;
            Object L$0;
            Object L$1;
            int label;
            final /* synthetic */ ProxyModule this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00462(InputOption inputOption, Ref.ObjectRef<List<Object>> objectRef, Class<Object> cls, Ref.ObjectRef<Object> objectRef2, ProxyModule proxyModule, KFunction<?> kFunction, Continuation<? super C00462> continuation) {
                super(2, continuation);
                this.$inputOption = inputOption;
                this.$paramList = objectRef;
                this.$javaClazz = cls;
                this.$executeRet = objectRef2;
                this.this$0 = proxyModule;
                this.$targetFunction = kFunction;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00462(this.$inputOption, this.$paramList, this.$javaClazz, this.$executeRet, this.this$0, this.$targetFunction, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00462) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Ref.ObjectRef<Object> objectRef;
                T t;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    if (this.$inputOption.getIsCompanion()) {
                        List<Object> list = this.$paramList.element;
                        Object companionObject = KClasses.getCompanionObject(JvmClassMappingKt.getKotlinClass(this.$javaClazz));
                        Intrinsics.checkNotNull(companionObject, "null cannot be cast to non-null type kotlin.Nothing");
                        list.add(0, (Void) companionObject);
                    }
                    Object[] array = this.$paramList.element.toArray(new Object[0]);
                    Ref.ObjectRef<Object> objectRef2 = this.$executeRet;
                    ProxyModule proxyModule = this.this$0;
                    KFunction<?> kFunction = this.$targetFunction;
                    Intrinsics.checkNotNull(kFunction);
                    this.L$0 = SpillingKt.nullOutSpilledVariable(array);
                    this.L$1 = objectRef2;
                    this.label = 1;
                    Object functionExecuteRet = proxyModule.getFunctionExecuteRet(kFunction, array, this);
                    if (functionExecuteRet == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    objectRef = objectRef2;
                    t = functionExecuteRet;
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    objectRef = (Ref.ObjectRef) this.L$1;
                    ResultKt.throwOnFailure(obj);
                    t = obj;
                }
                objectRef.element = t;
                return Unit.INSTANCE;
            }
        }
    }

    /* compiled from: UTSProxyModule.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Lkotlinx/coroutines/Job;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "io.dcloud.feature.utsplugin.ProxyModule$invokeSync$3", f = "UTSProxyModule.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: io.dcloud.feature.utsplugin.ProxyModule$invokeSync$3, reason: invalid class name */
    static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Job>, Object> {
        final /* synthetic */ Ref.ObjectRef<ReturnResult> $errRet;
        final /* synthetic */ Ref.ObjectRef<Object> $executeRet;
        final /* synthetic */ Ref.ObjectRef<List<Object>> $paramList;
        final /* synthetic */ Ref.ObjectRef<KFunction<?>> $targetFunction;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ ProxyModule this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(Ref.ObjectRef<Object> objectRef, Ref.ObjectRef<ReturnResult> objectRef2, Ref.ObjectRef<List<Object>> objectRef3, ProxyModule proxyModule, Ref.ObjectRef<KFunction<?>> objectRef4, Continuation<? super AnonymousClass3> continuation) {
            super(2, continuation);
            this.$executeRet = objectRef;
            this.$errRet = objectRef2;
            this.$paramList = objectRef3;
            this.this$0 = proxyModule;
            this.$targetFunction = objectRef4;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$executeRet, this.$errRet, this.$paramList, this.this$0, this.$targetFunction, continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Job> continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return BuildersKt__Builders_commonKt.launch$default(coroutineScope, new ProxyModule$invokeSync$3$invokeSuspend$$inlined$CoroutineExceptionHandler$1(CoroutineExceptionHandler.INSTANCE, this.$executeRet, this.$errRet), null, new AnonymousClass2(this.$paramList, this.$executeRet, this.this$0, this.$targetFunction, null), 2, null);
        }

        /* compiled from: UTSProxyModule.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "io.dcloud.feature.utsplugin.ProxyModule$invokeSync$3$2", f = "UTSProxyModule.kt", i = {0}, l = {1001}, m = "invokeSuspend", n = {"paramArray"}, s = {"L$0"})
        /* renamed from: io.dcloud.feature.utsplugin.ProxyModule$invokeSync$3$2, reason: invalid class name */
        static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Ref.ObjectRef<Object> $executeRet;
            final /* synthetic */ Ref.ObjectRef<List<Object>> $paramList;
            final /* synthetic */ Ref.ObjectRef<KFunction<?>> $targetFunction;
            Object L$0;
            Object L$1;
            int label;
            final /* synthetic */ ProxyModule this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass2(Ref.ObjectRef<List<Object>> objectRef, Ref.ObjectRef<Object> objectRef2, ProxyModule proxyModule, Ref.ObjectRef<KFunction<?>> objectRef3, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.$paramList = objectRef;
                this.$executeRet = objectRef2;
                this.this$0 = proxyModule;
                this.$targetFunction = objectRef3;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.$paramList, this.$executeRet, this.this$0, this.$targetFunction, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Ref.ObjectRef<Object> objectRef;
                T t;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Object[] array = this.$paramList.element.toArray(new Object[0]);
                    Ref.ObjectRef<Object> objectRef2 = this.$executeRet;
                    this.L$0 = SpillingKt.nullOutSpilledVariable(array);
                    this.L$1 = objectRef2;
                    this.label = 1;
                    Object functionExecuteRet = this.this$0.getFunctionExecuteRet(this.$targetFunction.element, array, this);
                    if (functionExecuteRet == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    objectRef = objectRef2;
                    t = functionExecuteRet;
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    objectRef = (Ref.ObjectRef) this.L$1;
                    ResultKt.throwOnFailure(obj);
                    t = obj;
                }
                objectRef.element = t;
                return Unit.INSTANCE;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final <R> java.lang.Object callSuspend(kotlin.reflect.KCallable<? extends R> r5, java.lang.Object[] r6, kotlin.coroutines.Continuation<? super R> r7) {
        /*
            r4 = this;
            boolean r0 = r7 instanceof io.dcloud.feature.utsplugin.ProxyModule.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r7
            io.dcloud.feature.utsplugin.ProxyModule$callSuspend$1 r0 = (io.dcloud.feature.utsplugin.ProxyModule.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            io.dcloud.feature.utsplugin.ProxyModule$callSuspend$1 r0 = new io.dcloud.feature.utsplugin.ProxyModule$callSuspend$1
            r0.<init>(r7)
        L19:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3a
            if (r2 != r3) goto L32
            java.lang.Object r5 = r0.L$1
            java.lang.Object[] r5 = (java.lang.Object[]) r5
            java.lang.Object r5 = r0.L$0
            kotlin.reflect.KCallable r5 = (kotlin.reflect.KCallable) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L7e
        L32:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L3a:
            kotlin.ResultKt.throwOnFailure(r7)
            boolean r7 = r5.isSuspend()
            if (r7 != 0) goto L4d
            int r7 = r6.length
            java.lang.Object[] r6 = java.util.Arrays.copyOf(r6, r7)
            java.lang.Object r5 = r5.call(r6)
            return r5
        L4d:
            boolean r7 = r5 instanceof kotlin.reflect.KFunction
            if (r7 == 0) goto La4
            r0.L$0 = r5
            r0.L$1 = r6
            r0.label = r3
            kotlin.coroutines.Continuation r0 = (kotlin.coroutines.Continuation) r0
            java.util.List r6 = kotlin.collections.ArraysKt.toMutableList(r6)
            r6.add(r0)
            java.util.Collection r6 = (java.util.Collection) r6
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]
            java.lang.Object[] r6 = r6.toArray(r7)
            int r7 = r6.length
            java.lang.Object[] r6 = java.util.Arrays.copyOf(r6, r7)
            java.lang.Object r7 = r5.call(r6)
            java.lang.Object r6 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r7 != r6) goto L7b
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r0)
        L7b:
            if (r7 != r1) goto L7e
            return r1
        L7e:
            kotlin.reflect.KFunction r5 = (kotlin.reflect.KFunction) r5
            kotlin.reflect.KType r6 = r5.getReturnType()
            kotlin.reflect.KClassifier r6 = r6.getClassifier()
            java.lang.Class<kotlin.Unit> r0 = kotlin.Unit.class
            kotlin.reflect.KClass r0 = kotlin.jvm.internal.Reflection.getOrCreateKotlinClass(r0)
            boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r0)
            if (r6 == 0) goto La3
            kotlin.reflect.KType r5 = r5.getReturnType()
            boolean r5 = r5.isMarkedNullable()
            if (r5 != 0) goto La3
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            java.lang.Object r5 = (java.lang.Object) r5
            return r5
        La3:
            return r7
        La4:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r0 = "Cannot callSuspend on a property "
            r7.<init>(r0)
            r7.append(r5)
            java.lang.String r5 = ": suspend properties are not supported yet"
            r7.append(r5)
            java.lang.String r5 = r7.toString()
            r6.<init>(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.utsplugin.ProxyModule.callSuspend(kotlin.reflect.KCallable, java.lang.Object[], kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* compiled from: UTSProxyModule.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\u0006\u001a\u00020\u0007J\u0006\u0010\b\u001a\u00020\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lio/dcloud/feature/utsplugin/ProxyModule$ModuleChecker;", "", "inputOption", "Lio/dcloud/feature/utsplugin/ProxyModule$InputOption;", "<init>", "(Lio/dcloud/feature/utsplugin/ProxyModule$InputOption;)V", "shallShowErrorDialog", "", "showErrorDialog", "", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class ModuleChecker {
        private final InputOption inputOption;

        public ModuleChecker(InputOption inputOption) {
            Intrinsics.checkNotNullParameter(inputOption, "inputOption");
            this.inputOption = inputOption;
        }

        public final boolean shallShowErrorDialog() {
            if (StringsKt.equals("built-in", this.inputOption.getInputModuleType(), true)) {
                return !Intrinsics.areEqual("uni-getLocation-tencent-uni1", this.inputOption.getInputModuleName());
            }
            return false;
        }

        public final void showErrorDialog() {
            if (UTSAndroid.INSTANCE.getUniActivity() != null) {
                StringBuilder sb = new StringBuilder();
                Activity uniActivity = UTSAndroid.INSTANCE.getUniActivity();
                Intrinsics.checkNotNull(uniActivity);
                sb.append(uniActivity.getString(io.dcloud.base.R.string.dcloud_feature_error_tips2));
                sb.append("https://ask.dcloud.net.cn/article/283");
                ErrorDialogUtil.getLossDialog(UTSAndroid.INSTANCE.getUniActivity(), StringUtil.format(sb.toString(), this.inputOption.getInputModuleName()), "https://ask.dcloud.net.cn/article/283", this.inputOption.getInputModuleName()).show();
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v24, types: [T, kotlin.reflect.KFunction] */
    /* JADX WARN: Type inference failed for: r0v34, types: [T, kotlin.reflect.KFunction] */
    /* JADX WARN: Type inference failed for: r0v9, types: [T, kotlin.reflect.KFunction] */
    /* JADX WARN: Type inference failed for: r6v3, types: [T, java.util.List] */
    /* JADX WARN: Type inference failed for: r7v3, types: [T, java.util.List] */
    @UniJSMethod(uiThread = false)
    public Object invokeAsync(JSONObject jSONObject, JSCallback jSCallback, Continuation<? super Unit> continuation) throws ClassNotFoundException {
        Class<?> cls;
        AndroidUTSContext androidUTSContext = AndroidUTSContext.INSTANCE;
        AbsSDKInstance mUniSDKInstance = this.mUniSDKInstance;
        Intrinsics.checkNotNullExpressionValue(mUniSDKInstance, "mUniSDKInstance");
        androidUTSContext.initContext(mUniSDKInstance);
        InputOption inputOption = new InputOption(jSONObject);
        try {
        } catch (Exception e) {
            AppConsoleLogUtil.DCLog("UTS: targetFunction invoke error - " + (e.getCause() != null ? String.valueOf(e.getCause()) : e.toString()), "ERROR");
        }
        if (!inputOption.isValid()) {
            AppConsoleLogUtil.DCLog("UTS: " + inputOption.getCheckErrorMsg(), "ERROR");
            return Unit.INSTANCE;
        }
        if (!inputOption.getIsInstanceAction()) {
            try {
                cls = Class.forName(inputOption.getModuleName());
            } catch (ClassNotFoundException unused) {
                cls = null;
            }
            if (cls == null) {
                ModuleChecker moduleChecker = new ModuleChecker(inputOption);
                if (moduleChecker.shallShowErrorDialog()) {
                    moduleChecker.showErrorDialog();
                }
                AppConsoleLogUtil.DCLog("error: " + inputOption.getInputModuleName() + " not found.", "ERROR");
                return Unit.INSTANCE;
            }
            Ref.ObjectRef objectRef = new Ref.ObjectRef();
            objectRef.element = findTargetFunc(cls, inputOption.getIsCompanion(), inputOption.getMethodName(), inputOption);
            FieldMethodDetector fieldMethodDetector = new FieldMethodDetector(cls, inputOption);
            Ref.BooleanRef booleanRef = new Ref.BooleanRef();
            if (objectRef.element == 0) {
                fieldMethodDetector.init();
                booleanRef.element = fieldMethodDetector.isFieldMethod();
                objectRef.element = fieldMethodDetector.getTargetFunction();
            }
            if (objectRef.element == 0) {
                AppConsoleLogUtil.DCLog("UTS: targetFunction not exists", "ERROR");
                return Unit.INSTANCE;
            }
            Ref.BooleanRef booleanRef2 = new Ref.BooleanRef();
            booleanRef2.element = !Intrinsics.areEqual(((KFunction) objectRef.element).getReturnType(), Void.TYPE);
            Class<?> cls2 = cls;
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = inputOption.obtainParamsWithDefault((KFunction<?>) objectRef.element, jSCallback, booleanRef.element);
            if (inputOption.getIsCompanion()) {
                ((List) objectRef2.element).add(0, KClasses.getCompanionObjectInstance(JvmClassMappingKt.getKotlinClass(cls2)));
            }
            try {
                BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), new ProxyModule$invokeAsync$$inlined$CoroutineExceptionHandler$1(CoroutineExceptionHandler.INSTANCE), null, new AnonymousClass4(objectRef2, booleanRef2, booleanRef, this, objectRef, fieldMethodDetector, jSCallback, null), 2, null);
            } catch (Exception e2) {
                AppConsoleLogUtil.DCLog("UTS: targetFunction invoke error - " + (e2.getCause() != null ? String.valueOf(e2.getCause()) : e2.toString()), "ERROR");
                Unit unit = Unit.INSTANCE;
            }
        } else {
            if (utsInstances.get(Boxing.boxInt(inputOption.getInstanceId())) == null) {
                AppConsoleLogUtil.DCLog("UTS: instance does not exists", "ERROR");
                return Unit.INSTANCE;
            }
            Object obj = utsInstances.get(Boxing.boxInt(inputOption.getInstanceId()));
            Intrinsics.checkNotNull(obj);
            Class<?> cls3 = obj.getClass();
            Object obj2 = utsInstances.get(Boxing.boxInt(inputOption.getInstanceId()));
            Ref.ObjectRef objectRef3 = new Ref.ObjectRef();
            objectRef3.element = findTargetFunc(cls3, false, inputOption.getMethodName(), inputOption);
            if (objectRef3.element == 0) {
                AppConsoleLogUtil.DCLog("UTS: targetFunction does not exists", "ERROR");
                return Unit.INSTANCE;
            }
            Ref.BooleanRef booleanRef3 = new Ref.BooleanRef();
            booleanRef3.element = !Intrinsics.areEqual(((KFunction) objectRef3.element).getReturnType(), Void.TYPE);
            Ref.ObjectRef objectRef4 = new Ref.ObjectRef();
            objectRef4.element = InputOption.obtainParamsWithDefault$default(inputOption, (KFunction) objectRef3.element, jSCallback, false, 4, (Object) null);
            ((List) objectRef4.element).add(0, obj2);
            try {
                BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new AnonymousClass2(objectRef4, booleanRef3, this, objectRef3, jSCallback, null), 3, null);
            } catch (Exception e3) {
                AppConsoleLogUtil.DCLog("UTS: targetFunction invoke error - " + (e3.getCause() != null ? String.valueOf(e3.getCause()) : e3.toString()), "ERROR");
                Unit unit2 = Unit.INSTANCE;
            }
        }
        return Unit.INSTANCE;
    }

    /* compiled from: UTSProxyModule.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "io.dcloud.feature.utsplugin.ProxyModule$invokeAsync$2", f = "UTSProxyModule.kt", i = {0, 1, 1, 1, 2}, l = {1171, 1175, 1187}, m = "invokeSuspend", n = {"paramArray", "paramArray", "deferredRet", "ret", "paramArray"}, s = {"L$0", "L$0", "L$1", "L$2", "L$0"})
    /* renamed from: io.dcloud.feature.utsplugin.ProxyModule$invokeAsync$2, reason: invalid class name */
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ JSCallback $callback;
        final /* synthetic */ Ref.BooleanRef $needReturn;
        final /* synthetic */ Ref.ObjectRef<List<Object>> $paramList;
        final /* synthetic */ Ref.ObjectRef<KFunction<?>> $targetFunction;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        final /* synthetic */ ProxyModule this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(Ref.ObjectRef<List<Object>> objectRef, Ref.BooleanRef booleanRef, ProxyModule proxyModule, Ref.ObjectRef<KFunction<?>> objectRef2, JSCallback jSCallback, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$paramList = objectRef;
            this.$needReturn = booleanRef;
            this.this$0 = proxyModule;
            this.$targetFunction = objectRef2;
            this.$callback = jSCallback;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass2(this.$paramList, this.$needReturn, this.this$0, this.$targetFunction, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:27:0x00d2, code lost:
        
            if (r9.callSuspend(r3, java.util.Arrays.copyOf(r1, r1.length), r8) == r0) goto L28;
         */
        /* JADX WARN: Removed duplicated region for block: B:20:0x0098  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x009d  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x00a7  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r9) {
            /*
                Method dump skipped, instructions count: 242
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.utsplugin.ProxyModule.AnonymousClass2.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* compiled from: UTSProxyModule.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "io.dcloud.feature.utsplugin.ProxyModule$invokeAsync$4", f = "UTSProxyModule.kt", i = {0, 1, 1, 2}, l = {1277, 1282, TestUtil.PointTime.AC_TYPE_1_3}, m = "invokeSuspend", n = {"paramArray", "paramArray", "deferredRet", "paramArray"}, s = {"L$0", "L$0", "L$1", "L$0"})
    /* renamed from: io.dcloud.feature.utsplugin.ProxyModule$invokeAsync$4, reason: invalid class name */
    static final class AnonymousClass4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ JSCallback $callback;
        final /* synthetic */ FieldMethodDetector $fieldMethodDetector;
        final /* synthetic */ Ref.BooleanRef $isFieldMethod;
        final /* synthetic */ Ref.BooleanRef $needReturn;
        final /* synthetic */ Ref.ObjectRef<List<Object>> $paramList;
        final /* synthetic */ Ref.ObjectRef<KFunction<?>> $targetFunction;
        Object L$0;
        Object L$1;
        int label;
        final /* synthetic */ ProxyModule this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass4(Ref.ObjectRef<List<Object>> objectRef, Ref.BooleanRef booleanRef, Ref.BooleanRef booleanRef2, ProxyModule proxyModule, Ref.ObjectRef<KFunction<?>> objectRef2, FieldMethodDetector fieldMethodDetector, JSCallback jSCallback, Continuation<? super AnonymousClass4> continuation) {
            super(2, continuation);
            this.$paramList = objectRef;
            this.$needReturn = booleanRef;
            this.$isFieldMethod = booleanRef2;
            this.this$0 = proxyModule;
            this.$targetFunction = objectRef2;
            this.$fieldMethodDetector = fieldMethodDetector;
            this.$callback = jSCallback;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass4(this.$paramList, this.$needReturn, this.$isFieldMethod, this.this$0, this.$targetFunction, this.$fieldMethodDetector, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass4) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:17:0x0071, code lost:
        
            if (r10 == r0) goto L32;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x00ad, code lost:
        
            if (r10 != r0) goto L24;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x00ed, code lost:
        
            if (r9.this$0.callSuspend(r9.$targetFunction.element, java.util.Arrays.copyOf(r1, r1.length), r9) == r0) goto L32;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r10) throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
            /*
                Method dump skipped, instructions count: 292
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.utsplugin.ProxyModule.AnonymousClass4.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @Override // io.dcloud.common.DHInterface.message.IObserveAble
    public EnumUniqueID getActionObserverID() {
        return EnumUniqueID.FEATURE_UTS;
    }
}
