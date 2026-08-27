package uts.sdk.modules.imaIcons;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.ui.component.WXImage;
import io.dcloud.uts.NumberKt;
import io.dcloud.uts.UTSAndroid;
import io.dcloud.uts.UTSArray;
import io.dcloud.uts.UTSJSONObjectKt;
import io.dcloud.uts.UTSPromise;
import io.dcloud.uts.UTSPromiseHelperKt;
import io.dcloud.uts.console;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Deferred;
import uts.ima.IconLibs.IconUtil;

/* compiled from: index.kt */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0010\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u001a \u0010\u0015\u001a\f\u0012\b\u0012\u00060\u0001j\u0002`\f0\u00162\u0006\u0010\b\u001a\u00020\u0003H\u0086@¢\u0006\u0002\u0010\u0017\u001a\u0018\u0010\u0018\u001a\f\u0012\b\u0012\u00060\u0001j\u0002`\f0\u0016H\u0086@¢\u0006\u0002\u0010\u0019\u001a\u0018\u0010\u001a\u001a\f\u0012\b\u0012\u00060\u0001j\u0002`\f0\u0016H\u0086@¢\u0006\u0002\u0010\u0019\"\u0014\u0010\u0002\u001a\u00020\u0003X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"'\u0010\t\u001a\u0018\u0012\u0004\u0012\u00020\u0003\u0012\u000e\u0012\f\u0012\b\u0012\u00060\u0001j\u0002`\f0\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\"!\u0010\u000f\u001a\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u00060\u0001j\u0002`\f0\u000b0\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"!\u0010\u0013\u001a\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u00060\u0001j\u0002`\f0\u000b0\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012*\n\u0010\u0000\"\u00020\u00012\u00020\u0001¨\u0006\u001b"}, d2 = {"IReturn", "", "MAIN_ACTIVITY", "", "getMAIN_ACTIVITY", "()Ljava/lang/String;", "setAliasStates", "", "aliasName", "switchIcons", "Lkotlin/Function1;", "Lio/dcloud/uts/UTSPromise;", "Luts/sdk/modules/imaIcons/IReturn;", "getSwitchIcons", "()Lkotlin/jvm/functions/Function1;", "restoreIcons", "Lkotlin/Function0;", "getRestoreIcons", "()Lkotlin/jvm/functions/Function0;", "getSwitchList", "getGetSwitchList", "switchIconsByJs", "Lkotlinx/coroutines/Deferred;", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "restoreIconsByJs", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSwitchListByJs", "ima-icons_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class IndexKt {
    private static final String MAIN_ACTIVITY = "io.dcloud.PandoraEntry";
    private static final Function1<String, UTSPromise<Object>> switchIcons = new Function1() { // from class: uts.sdk.modules.imaIcons.IndexKt$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object invoke2(Object obj) {
            return IndexKt.switchIcons$lambda$2((String) obj);
        }
    };
    private static final Function0<UTSPromise<Object>> restoreIcons = new Function0() { // from class: uts.sdk.modules.imaIcons.IndexKt$$ExternalSyntheticLambda3
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return IndexKt.restoreIcons$lambda$4();
        }
    };
    private static final Function0<UTSPromise<Object>> getSwitchList = new Function0() { // from class: uts.sdk.modules.imaIcons.IndexKt$$ExternalSyntheticLambda4
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return IndexKt.getSwitchList$lambda$6();
        }
    };

    public static final String getMAIN_ACTIVITY() {
        return MAIN_ACTIVITY;
    }

    public static final boolean setAliasStates(String str) {
        Context appContext = UTSAndroid.INSTANCE.getAppContext();
        if (appContext == null) {
            console.error("getAppContext() 为空");
            return false;
        }
        UTSArray<String> uTSArray = IconUtil.getsAliasNames(appContext);
        String packageName = appContext.getPackageName();
        PackageManager packageManager = appContext.getPackageManager();
        for (Number numberInc = (Number) 0; NumberKt.compareTo(numberInc, uTSArray.getLength()) < 0; numberInc = NumberKt.inc(numberInc)) {
            String str2 = uTSArray.get(numberInc);
            packageManager.setComponentEnabledSetting(new ComponentName(packageName, "" + packageName + Operators.DOT + str2), Intrinsics.areEqual(str2, str) ? 1 : 2, 1);
        }
        return true;
    }

    public static final Function1<String, UTSPromise<Object>> getSwitchIcons() {
        return switchIcons;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final UTSPromise<Object> switchIcons$lambda$2(final String str) {
        return new UTSPromise<>(new Function2() { // from class: uts.sdk.modules.imaIcons.IndexKt$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return IndexKt.switchIcons$lambda$2$lambda$1(str, (Function1) obj, (Function1) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit switchIcons$lambda$2$lambda$1(String str, Function1<Object, Unit> function1, Function1<Object, Unit> function12) {
        Context appContext;
        try {
            appContext = UTSAndroid.INSTANCE.getAppContext();
        } catch (Throwable th) {
            function12.invoke2(UTSJSONObjectKt._uO(TuplesKt.to(WXImage.SUCCEED, false), TuplesKt.to("message", "切换图标（图标 + 名称）错误: " + th.getMessage()), TuplesKt.to("data", null)));
        }
        if (appContext == null) {
            function12.invoke2(UTSJSONObjectKt._uO(TuplesKt.to(WXImage.SUCCEED, false), TuplesKt.to("message", "无法获取Android上下文"), TuplesKt.to("data", null)));
            return Unit.INSTANCE;
        }
        if (!IconUtil.getsAliasNames(appContext).includes(str)) {
            function12.invoke2(UTSJSONObjectKt._uO(TuplesKt.to(WXImage.SUCCEED, false), TuplesKt.to("message", "无效的图标别名：" + str), TuplesKt.to("data", str)));
            return Unit.INSTANCE;
        }
        String packageName = appContext.getPackageName();
        PackageManager packageManager = appContext.getPackageManager();
        boolean aliasStates = setAliasStates(str);
        packageManager.setComponentEnabledSetting(new ComponentName(packageName, MAIN_ACTIVITY), 2, 1);
        function1.invoke2(UTSJSONObjectKt._uO(TuplesKt.to(WXImage.SUCCEED, true), TuplesKt.to("message", "切换图标（图标 + 名称）成功"), TuplesKt.to("data", Boolean.valueOf(aliasStates))));
        return Unit.INSTANCE;
    }

    public static final Function0<UTSPromise<Object>> getRestoreIcons() {
        return restoreIcons;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final UTSPromise<Object> restoreIcons$lambda$4() {
        return new UTSPromise<>(new Function2() { // from class: uts.sdk.modules.imaIcons.IndexKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return IndexKt.restoreIcons$lambda$4$lambda$3((Function1) obj, (Function1) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit restoreIcons$lambda$4$lambda$3(Function1<Object, Unit> function1, Function1<Object, Unit> function12) {
        Context appContext;
        try {
            appContext = UTSAndroid.INSTANCE.getAppContext();
        } catch (Throwable th) {
            function12.invoke2(UTSJSONObjectKt._uO(TuplesKt.to(WXImage.SUCCEED, false), TuplesKt.to("message", "恢复默认错误: " + th.getMessage()), TuplesKt.to("data", null)));
        }
        if (appContext == null) {
            function12.invoke2(UTSJSONObjectKt._uO(TuplesKt.to(WXImage.SUCCEED, false), TuplesKt.to("message", "无法获取Android上下文"), TuplesKt.to("data", null)));
            return Unit.INSTANCE;
        }
        String packageName = appContext.getPackageName();
        PackageManager packageManager = appContext.getPackageManager();
        String str = MAIN_ACTIVITY;
        packageManager.setComponentEnabledSetting(new ComponentName(packageName, str), 1, 1);
        function1.invoke2(UTSJSONObjectKt._uO(TuplesKt.to(WXImage.SUCCEED, true), TuplesKt.to("message", "恢复默认成功" + str), TuplesKt.to("data", Boolean.valueOf(setAliasStates(null)))));
        return Unit.INSTANCE;
    }

    public static final Function0<UTSPromise<Object>> getGetSwitchList() {
        return getSwitchList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final UTSPromise<Object> getSwitchList$lambda$6() {
        return new UTSPromise<>(new Function2() { // from class: uts.sdk.modules.imaIcons.IndexKt$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return IndexKt.getSwitchList$lambda$6$lambda$5((Function1) obj, (Function1) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit getSwitchList$lambda$6$lambda$5(Function1<Object, Unit> function1, Function1<Object, Unit> function12) {
        Context appContext;
        try {
            appContext = UTSAndroid.INSTANCE.getAppContext();
        } catch (Throwable th) {
            function12.invoke2(UTSJSONObjectKt._uO(TuplesKt.to(WXImage.SUCCEED, false), TuplesKt.to("message", "获取可切换列表错误: " + th.getMessage()), TuplesKt.to("data", null)));
        }
        if (appContext == null) {
            function12.invoke2(UTSJSONObjectKt._uO(TuplesKt.to(WXImage.SUCCEED, false), TuplesKt.to("message", "无法获取Android上下文"), TuplesKt.to("data", null)));
            return Unit.INSTANCE;
        }
        function1.invoke2(UTSJSONObjectKt._uO(TuplesKt.to(WXImage.SUCCEED, true), TuplesKt.to("message", "获取可切换列表成功"), TuplesKt.to("data", IconUtil.getsAliasNames(appContext))));
        return Unit.INSTANCE;
    }

    public static final Object switchIconsByJs(String str, Continuation<? super Deferred<? extends Object>> continuation) {
        return UTSPromiseHelperKt.toDeferred(switchIcons.invoke2(str), continuation);
    }

    public static final Object restoreIconsByJs(Continuation<? super Deferred<? extends Object>> continuation) {
        return UTSPromiseHelperKt.toDeferred(restoreIcons.invoke(), continuation);
    }

    public static final Object getSwitchListByJs(Continuation<? super Deferred<? extends Object>> continuation) {
        return UTSPromiseHelperKt.toDeferred(getSwitchList.invoke(), continuation);
    }
}
