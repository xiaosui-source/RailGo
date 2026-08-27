package io.dcloud.uts.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.WXModule;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.feature.internal.sdk.SDK;
import io.dcloud.feature.uniapp.AbsSDKInstance;
import io.dcloud.feature.weex.WeexInstanceMgr;
import io.dcloud.uts.UTSJSONObject;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AndroidUTSContext.kt */
@Metadata(d1 = {"\u0000V\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0004\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u001c\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a \u0010\u0005\u001a\u00020\u00012\u0016\b\u0002\u0010\u0002\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0003H\u0007\u001a\u001c\u0010\u0006\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a \u0010\b\u001a\u00020\u00012\u0016\b\u0002\u0010\u0002\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0003H\u0007\u001a\u0016\u0010\t\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u001a\u001a\u0010\u000b\u001a\u00020\u00012\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\nH\u0007\u001a\u0016\u0010\f\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u001a\u001a\u0010\r\u001a\u00020\u00012\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\nH\u0007\u001a\u0016\u0010\u000e\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u001a\u0016\u0010\u000f\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u001a\u001a\u0010\u0010\u001a\u00020\u00012\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\nH\u0007\u001aW\u0010\u0011\u001a\u00020\u00012M\u0010\u0002\u001aI\u0012\u0013\u0012\u00110\u0013¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0013\u0012\u00110\u0013¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0017\u0012\u0015\u0012\u0013\u0018\u00010\u0018¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\u00010\u0012H\u0007\u001a[\u0010\u001a\u001a\u00020\u00012Q\b\u0002\u0010\u0002\u001aK\u0012\u0013\u0012\u00110\u0013¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0013\u0012\u00110\u0013¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0017\u0012\u0015\u0012\u0013\u0018\u00010\u0018¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0012H\u0007\u001a\u0016\u0010\u001b\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u001a\u001a\u0010\u001c\u001a\u00020\u00012\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\nH\u0007\u001aa\u0010\u001d\u001a\u00020\u00012W\u0010\u0002\u001aS\u0012\u0013\u0012\u00110\u0013¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u001f0\u001e¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b( \u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00070\u001e¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(!\u0012\u0004\u0012\u00020\u00010\u0012H\u0007\u001ae\u0010\"\u001a\u00020\u00012[\b\u0002\u0010\u0002\u001aU\u0012\u0013\u0012\u00110\u0013¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u001f0\u001e¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b( \u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00070\u001e¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(!\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0012H\u0007\u001a\n\u0010#\u001a\u0004\u0018\u00010$H\u0007\u001a\n\u0010%\u001a\u0004\u0018\u00010&H\u0007\u001a\u0010\u0010'\u001a\u00020\u001f2\u0006\u0010(\u001a\u00020\u001fH\u0007¨\u0006)"}, d2 = {"onAppConfigChange", "", WXBridgeManager.METHOD_CALLBACK, "Lkotlin/Function1;", "Lio/dcloud/uts/UTSJSONObject;", "offAppConfigChange", "onAppTrimMemory", "", "offAppTrimMemory", "onAppActivityPause", "Lkotlin/Function0;", "offAppActivityPause", "onAppActivityResume", "offAppActivityResume", "onAppActivityStop", "onAppActivityDestroy", "offAppActivityDestroy", "onAppActivityResult", "Lkotlin/Function3;", "", "Lkotlin/ParameterName;", "name", WXModule.REQUEST_CODE, WXModule.RESULT_CODE, "Landroid/content/Intent;", "data", "offAppActivityResult", "onAppActivityBack", "offAppActivityBack", "onAppActivityRequestPermissionsResult", "", "", "permissions", WXModule.GRANT_RESULTS, "offAppActivityRequestPermissionsResult", "getAppContext", "Landroid/content/Context;", "getUniActivity", "Landroid/app/Activity;", "getResourcePath", "resourceName", "utsplugin_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class AndroidUTSContextKt {
    @Deprecated(message = "当前方法已废弃，未来某个时刻将会被移除", replaceWith = @ReplaceWith(expression = "UTSAndroid.onAppConfigChange()", imports = {"io.dcloud.uts.UTSAndroid"}))
    public static final void onAppConfigChange(Function1<? super UTSJSONObject, Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        AndroidUTSContext.INSTANCE.getOnConfigChangedListenFunc().add(callback);
    }

    public static /* synthetic */ void offAppConfigChange$default(Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = null;
        }
        offAppConfigChange(function1);
    }

    @Deprecated(message = "当前方法已废弃，未来某个时刻将会被移除", replaceWith = @ReplaceWith(expression = "UTSAndroid.offAppConfigChange()", imports = {"io.dcloud.uts.UTSAndroid"}))
    public static final void offAppConfigChange(Function1<? super UTSJSONObject, Unit> function1) {
        if (function1 == null) {
            AndroidUTSContext.INSTANCE.getOnConfigChangedListenFunc().clear();
        } else {
            AndroidUTSContext.INSTANCE.getOnConfigChangedListenFunc().remove(function1);
        }
    }

    @Deprecated(message = "当前方法已废弃，未来某个时刻将会被移除", replaceWith = @ReplaceWith(expression = "UTSAndroid.onAppTrimMemory()", imports = {"io.dcloud.uts.UTSAndroid"}))
    public static final void onAppTrimMemory(Function1<? super Number, Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        AndroidUTSContext.INSTANCE.getOnTrimMemoryListenFunc().add(callback);
    }

    public static /* synthetic */ void offAppTrimMemory$default(Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = null;
        }
        offAppTrimMemory(function1);
    }

    @Deprecated(message = "当前方法已废弃，未来某个时刻将会被移除", replaceWith = @ReplaceWith(expression = "UTSAndroid.offAppTrimMemory()", imports = {"io.dcloud.uts.UTSAndroid"}))
    public static final void offAppTrimMemory(Function1<? super Number, Unit> function1) {
        if (function1 == null) {
            AndroidUTSContext.INSTANCE.getOnTrimMemoryListenFunc().clear();
        } else {
            AndroidUTSContext.INSTANCE.getOnTrimMemoryListenFunc().remove(function1);
        }
    }

    @Deprecated(message = "当前方法已废弃，未来某个时刻将会被移除", replaceWith = @ReplaceWith(expression = "UTSAndroid.onAppActivityPause()", imports = {"io.dcloud.uts.UTSAndroid"}))
    public static final void onAppActivityPause(Function0<Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        AndroidUTSContext.INSTANCE.getPauseListenFunc().add(callback);
    }

    public static /* synthetic */ void offAppActivityPause$default(Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            function0 = null;
        }
        offAppActivityPause(function0);
    }

    @Deprecated(message = "当前方法已废弃，未来某个时刻将会被移除", replaceWith = @ReplaceWith(expression = "UTSAndroid.offAppActivityPause()", imports = {"io.dcloud.uts.UTSAndroid"}))
    public static final void offAppActivityPause(Function0<Unit> function0) {
        if (function0 == null) {
            AndroidUTSContext.INSTANCE.getPauseListenFunc().clear();
        } else {
            AndroidUTSContext.INSTANCE.getPauseListenFunc().remove(function0);
        }
    }

    @Deprecated(message = "当前方法已废弃，未来某个时刻将会被移除", replaceWith = @ReplaceWith(expression = "UTSAndroid.onAppActivityResume()", imports = {"io.dcloud.uts.UTSAndroid"}))
    public static final void onAppActivityResume(Function0<Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        AndroidUTSContext.INSTANCE.getResumeListenFunc().add(callback);
    }

    public static /* synthetic */ void offAppActivityResume$default(Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            function0 = null;
        }
        offAppActivityResume(function0);
    }

    @Deprecated(message = "当前方法已废弃，未来某个时刻将会被移除", replaceWith = @ReplaceWith(expression = "UTSAndroid.offAppActivityResume()", imports = {"io.dcloud.uts.UTSAndroid"}))
    public static final void offAppActivityResume(Function0<Unit> function0) {
        if (function0 == null) {
            AndroidUTSContext.INSTANCE.getResumeListenFunc().clear();
        } else {
            AndroidUTSContext.INSTANCE.getResumeListenFunc().remove(function0);
        }
    }

    @Deprecated(message = "当前方法已废弃，未来某个时刻将会被移除", replaceWith = @ReplaceWith(expression = "UTSAndroid.onAppActivityStop()", imports = {"io.dcloud.uts.UTSAndroid"}))
    public static final void onAppActivityStop(Function0<Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        AndroidUTSContext.INSTANCE.getStopListenFunc().add(callback);
    }

    @Deprecated(message = "当前方法已废弃，未来某个时刻将会被移除", replaceWith = @ReplaceWith(expression = "UTSAndroid.onAppActivityDestroy()", imports = {"io.dcloud.uts.UTSAndroid"}))
    public static final void onAppActivityDestroy(Function0<Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        AndroidUTSContext.INSTANCE.getDestroyListenFunc().add(callback);
    }

    public static /* synthetic */ void offAppActivityDestroy$default(Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            function0 = null;
        }
        offAppActivityDestroy(function0);
    }

    @Deprecated(message = "当前方法已废弃，未来某个时刻将会被移除", replaceWith = @ReplaceWith(expression = "UTSAndroid.offAppActivityDestroy()", imports = {"io.dcloud.uts.UTSAndroid"}))
    public static final void offAppActivityDestroy(Function0<Unit> function0) {
        if (function0 == null) {
            AndroidUTSContext.INSTANCE.getDestroyListenFunc().clear();
        } else {
            AndroidUTSContext.INSTANCE.getDestroyListenFunc().remove(function0);
        }
    }

    @Deprecated(message = "当前方法已废弃，未来某个时刻将会被移除", replaceWith = @ReplaceWith(expression = "UTSAndroid.onAppActivityResult()", imports = {"io.dcloud.uts.UTSAndroid"}))
    public static final void onAppActivityResult(Function3<? super Integer, ? super Integer, ? super Intent, Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        AndroidUTSContext.INSTANCE.getOnActivityResultListenFunc().add(callback);
    }

    public static /* synthetic */ void offAppActivityResult$default(Function3 function3, int i, Object obj) {
        if ((i & 1) != 0) {
            function3 = null;
        }
        offAppActivityResult(function3);
    }

    @Deprecated(message = "当前方法已废弃，未来某个时刻将会被移除", replaceWith = @ReplaceWith(expression = "UTSAndroid.offAppActivityResult()", imports = {"io.dcloud.uts.UTSAndroid"}))
    public static final void offAppActivityResult(Function3<? super Integer, ? super Integer, ? super Intent, Unit> function3) {
        if (function3 == null) {
            AndroidUTSContext.INSTANCE.getOnActivityResultListenFunc().clear();
        } else {
            AndroidUTSContext.INSTANCE.getOnActivityResultListenFunc().remove(function3);
        }
    }

    @Deprecated(message = "当前方法已废弃，未来某个时刻将会被移除", replaceWith = @ReplaceWith(expression = "UTSAndroid.onAppActivityBack()", imports = {"io.dcloud.uts.UTSAndroid"}))
    public static final void onAppActivityBack(Function0<Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        AndroidUTSContext.INSTANCE.getBackListenFunc().add(callback);
    }

    public static /* synthetic */ void offAppActivityBack$default(Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            function0 = null;
        }
        offAppActivityBack(function0);
    }

    @Deprecated(message = "当前方法已废弃，未来某个时刻将会被移除", replaceWith = @ReplaceWith(expression = "UTSAndroid.offAppActivityBack()", imports = {"io.dcloud.uts.UTSAndroid"}))
    public static final void offAppActivityBack(Function0<Unit> function0) {
        if (function0 == null) {
            AndroidUTSContext.INSTANCE.getBackListenFunc().clear();
        } else {
            AndroidUTSContext.INSTANCE.getBackListenFunc().remove(function0);
        }
    }

    @Deprecated(message = "当前方法已废弃，未来某个时刻将会被移除", replaceWith = @ReplaceWith(expression = "UTSAndroid.onAppActivityRequestPermissionsResult()", imports = {"io.dcloud.uts.UTSAndroid"}))
    public static final void onAppActivityRequestPermissionsResult(Function3<? super Integer, ? super List<String>, ? super List<Number>, Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        AndroidUTSContext.INSTANCE.getPermissionsResultListenFunc().add(callback);
    }

    public static /* synthetic */ void offAppActivityRequestPermissionsResult$default(Function3 function3, int i, Object obj) {
        if ((i & 1) != 0) {
            function3 = null;
        }
        offAppActivityRequestPermissionsResult(function3);
    }

    @Deprecated(message = "当前方法已废弃，未来某个时刻将会被移除", replaceWith = @ReplaceWith(expression = "UTSAndroid.offAppActivityRequestPermissionsResult()", imports = {"io.dcloud.uts.UTSAndroid"}))
    public static final void offAppActivityRequestPermissionsResult(Function3<? super Integer, ? super List<String>, ? super List<Number>, Unit> function3) {
        if (function3 == null) {
            AndroidUTSContext.INSTANCE.getPermissionsResultListenFunc().clear();
        } else {
            AndroidUTSContext.INSTANCE.getPermissionsResultListenFunc().remove(function3);
        }
    }

    @Deprecated(message = "当前方法已废弃，未来某个时刻将会被移除", replaceWith = @ReplaceWith(expression = "UTSAndroid.getAppContext()", imports = {"io.dcloud.uts.UTSAndroid"}))
    public static final Context getAppContext() {
        return AndroidUTSContext.INSTANCE.getHostAppContext();
    }

    @Deprecated(message = "当前方法已废弃，未来某个时刻将会被移除", replaceWith = @ReplaceWith(expression = "UTSAndroid.getUniActivity()", imports = {"io.dcloud.uts.UTSAndroid"}))
    public static final Activity getUniActivity() {
        if (AndroidUTSContext.INSTANCE.getInstance() == null) {
            return null;
        }
        WeexInstanceMgr weexInstanceMgrSelf = WeexInstanceMgr.self();
        AbsSDKInstance androidUTSContext = AndroidUTSContext.INSTANCE.getInstance();
        Intrinsics.checkNotNull(androidUTSContext, "null cannot be cast to non-null type com.taobao.weex.WXSDKInstance");
        return weexInstanceMgrSelf.findWebview((WXSDKInstance) androidUTSContext).getActivity();
    }

    @Deprecated(message = "当前方法已废弃，未来某个时刻将会被移除", replaceWith = @ReplaceWith(expression = "UTSAndroid.getResourcePath()", imports = {"io.dcloud.uts.UTSAndroid"}))
    public static final String getResourcePath(String resourceName) {
        Intrinsics.checkNotNullParameter(resourceName, "resourceName");
        boolean zIsAppResourcesInAssetsPath = AppRuntime.isAppResourcesInAssetsPath(getAppContext(), "");
        String str = BaseInfo.sCacheFsAppsPath;
        String str2 = BaseInfo.sDefaultBootApp + "/www/" + resourceName;
        String str3 = SDK.ANDROID_ASSET + str2;
        if (zIsAppResourcesInAssetsPath) {
            return str3;
        }
        return str + str2;
    }
}
