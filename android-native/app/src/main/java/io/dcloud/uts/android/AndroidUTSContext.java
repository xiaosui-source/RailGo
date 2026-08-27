package io.dcloud.uts.android;

import android.content.Context;
import android.content.Intent;
import androidtranscoder.format.MediaFormatExtraConstants;
import com.alibaba.android.bindingx.core.internal.BindingXConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.common.WXModule;
import io.dcloud.common.DHInterface.message.AbsActionObserver;
import io.dcloud.common.DHInterface.message.ActionBus;
import io.dcloud.common.DHInterface.message.EnumUniqueID;
import io.dcloud.common.DHInterface.message.IObserveAble;
import io.dcloud.common.DHInterface.message.action.AppOnConfigChangedAction;
import io.dcloud.common.DHInterface.message.action.AppOnTrimMemoryAction;
import io.dcloud.common.DHInterface.message.action.IAction;
import io.dcloud.common.DHInterface.message.action.PermissionRequestAction;
import io.dcloud.common.DHInterface.message.action.WebActivityOnDestroyAction;
import io.dcloud.feature.uniapp.AbsSDKInstance;
import io.dcloud.uts.UTSArray;
import io.dcloud.uts.UTSJSONObject;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AndroidUTSContext.kt */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0004\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0011\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010J\u001a\u00020\u001a2\u0006\u0010\n\u001a\u00020\u000bJ\u0006\u0010K\u001a\u00020\u001aJ\u001c\u0010L\u001a\u00020\u001a2\u0006\u0010M\u001a\u0002012\f\u00102\u001a\b\u0012\u0004\u0012\u00020100R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fRg\u0010\u0010\u001aO\u0012K\u0012I\u0012\u0013\u0012\u00110\u0013¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0013\u0012\u00110\u0013¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0017\u0012\u0015\u0012\u0013\u0018\u00010\u0018¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\u001a0\u00120\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR&\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0 0\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u001c\"\u0004\b\"\u0010\u001eR&\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0 0\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u001c\"\u0004\b%\u0010\u001eR&\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0 0\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\u001c\"\u0004\b(\u0010\u001eR&\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0 0\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u001c\"\u0004\b+\u0010\u001eR&\u0010,\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0 0\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\u001c\"\u0004\b.\u0010\u001eRq\u0010/\u001aY\u0012U\u0012S\u0012\u0013\u0012\u00110\u0013¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020100¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(2\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020300¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(4\u0012\u0004\u0012\u00020\u001a0\u00120\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u0010\u001c\"\u0004\b6\u0010\u001eR;\u00107\u001a#\u0012\u001f\u0012\u001d\u0012\u0013\u0012\u001103¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(9\u0012\u0004\u0012\u00020\u001a080\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010\u001c\"\u0004\b;\u0010\u001eR;\u0010<\u001a#\u0012\u001f\u0012\u001d\u0012\u0013\u0012\u00110=¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(>\u0012\u0004\u0012\u00020\u001a080\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010\u001c\"\u0004\b@\u0010\u001eR&\u0010A\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0 0\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010\u001c\"\u0004\bC\u0010\u001eR)\u0010D\u001a\u001a\u0012\u0016\u0012\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020100\u0012\u0004\u0012\u00020\u001a080\u0011¢\u0006\b\n\u0000\u001a\u0004\bE\u0010\u001cR)\u0010F\u001a\u001a\u0012\u0016\u0012\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020100\u0012\u0004\u0012\u00020\u001a080\u0011¢\u0006\b\n\u0000\u001a\u0004\bG\u0010\u001cR)\u0010H\u001a\u001a\u0012\u0016\u0012\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020100\u0012\u0004\u0012\u00020\u001a080\u0011¢\u0006\b\n\u0000\u001a\u0004\bI\u0010\u001c¨\u0006N"}, d2 = {"Lio/dcloud/uts/android/AndroidUTSContext;", "", "<init>", "()V", "hostAppContext", "Landroid/content/Context;", "getHostAppContext", "()Landroid/content/Context;", "setHostAppContext", "(Landroid/content/Context;)V", "instance", "Lio/dcloud/feature/uniapp/AbsSDKInstance;", "getInstance", "()Lio/dcloud/feature/uniapp/AbsSDKInstance;", "setInstance", "(Lio/dcloud/feature/uniapp/AbsSDKInstance;)V", "onActivityResultListenFunc", "Ljava/util/concurrent/CopyOnWriteArrayList;", "Lkotlin/Function3;", "", "Lkotlin/ParameterName;", "name", WXModule.REQUEST_CODE, WXModule.RESULT_CODE, "Landroid/content/Intent;", "data", "", "getOnActivityResultListenFunc", "()Ljava/util/concurrent/CopyOnWriteArrayList;", "setOnActivityResultListenFunc", "(Ljava/util/concurrent/CopyOnWriteArrayList;)V", "destroyListenFunc", "Lkotlin/Function0;", "getDestroyListenFunc", "setDestroyListenFunc", "pauseListenFunc", "getPauseListenFunc", "setPauseListenFunc", "resumeListenFunc", "getResumeListenFunc", "setResumeListenFunc", "stopListenFunc", "getStopListenFunc", "setStopListenFunc", "backListenFunc", "getBackListenFunc", "setBackListenFunc", "permissionsResultListenFunc", "Lio/dcloud/uts/UTSArray;", "", "permissions", "", WXModule.GRANT_RESULTS, "getPermissionsResultListenFunc", "setPermissionsResultListenFunc", "onTrimMemoryListenFunc", "Lkotlin/Function1;", MediaFormatExtraConstants.KEY_LEVEL, "getOnTrimMemoryListenFunc", "setOnTrimMemoryListenFunc", "onConfigChangedListenFunc", "Lio/dcloud/uts/UTSJSONObject;", BindingXConstants.KEY_CONFIG, "getOnConfigChangedListenFunc", "setOnConfigChangedListenFunc", "onCreateListenFunc", "getOnCreateListenFunc", "setOnCreateListenFunc", "permissionRequestFunc", "getPermissionRequestFunc", "permissionConfirmFunc", "getPermissionConfirmFunc", "permissionRequestFinishedFunc", "getPermissionRequestFinishedFunc", "initContext", "initApp", "permission", "type", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class AndroidUTSContext {
    private static Context hostAppContext;
    private static AbsSDKInstance instance;
    public static final AndroidUTSContext INSTANCE = new AndroidUTSContext();
    private static CopyOnWriteArrayList<Function3<Integer, Integer, Intent, Unit>> onActivityResultListenFunc = new CopyOnWriteArrayList<>();
    private static CopyOnWriteArrayList<Function0<Unit>> destroyListenFunc = new CopyOnWriteArrayList<>();
    private static CopyOnWriteArrayList<Function0<Unit>> pauseListenFunc = new CopyOnWriteArrayList<>();
    private static CopyOnWriteArrayList<Function0<Unit>> resumeListenFunc = new CopyOnWriteArrayList<>();
    private static CopyOnWriteArrayList<Function0<Unit>> stopListenFunc = new CopyOnWriteArrayList<>();
    private static CopyOnWriteArrayList<Function0<Unit>> backListenFunc = new CopyOnWriteArrayList<>();
    private static CopyOnWriteArrayList<Function3<Integer, UTSArray<String>, UTSArray<Number>, Unit>> permissionsResultListenFunc = new CopyOnWriteArrayList<>();
    private static CopyOnWriteArrayList<Function1<Number, Unit>> onTrimMemoryListenFunc = new CopyOnWriteArrayList<>();
    private static CopyOnWriteArrayList<Function1<UTSJSONObject, Unit>> onConfigChangedListenFunc = new CopyOnWriteArrayList<>();
    private static CopyOnWriteArrayList<Function0<Unit>> onCreateListenFunc = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<Function1<UTSArray<String>, Unit>> permissionRequestFunc = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<Function1<UTSArray<String>, Unit>> permissionConfirmFunc = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<Function1<UTSArray<String>, Unit>> permissionRequestFinishedFunc = new CopyOnWriteArrayList<>();

    private AndroidUTSContext() {
    }

    public final Context getHostAppContext() {
        return hostAppContext;
    }

    public final void setHostAppContext(Context context) {
        hostAppContext = context;
    }

    public final AbsSDKInstance getInstance() {
        return instance;
    }

    public final void setInstance(AbsSDKInstance absSDKInstance) {
        instance = absSDKInstance;
    }

    public final CopyOnWriteArrayList<Function3<Integer, Integer, Intent, Unit>> getOnActivityResultListenFunc() {
        return onActivityResultListenFunc;
    }

    public final void setOnActivityResultListenFunc(CopyOnWriteArrayList<Function3<Integer, Integer, Intent, Unit>> copyOnWriteArrayList) {
        Intrinsics.checkNotNullParameter(copyOnWriteArrayList, "<set-?>");
        onActivityResultListenFunc = copyOnWriteArrayList;
    }

    public final CopyOnWriteArrayList<Function0<Unit>> getDestroyListenFunc() {
        return destroyListenFunc;
    }

    public final void setDestroyListenFunc(CopyOnWriteArrayList<Function0<Unit>> copyOnWriteArrayList) {
        Intrinsics.checkNotNullParameter(copyOnWriteArrayList, "<set-?>");
        destroyListenFunc = copyOnWriteArrayList;
    }

    public final CopyOnWriteArrayList<Function0<Unit>> getPauseListenFunc() {
        return pauseListenFunc;
    }

    public final void setPauseListenFunc(CopyOnWriteArrayList<Function0<Unit>> copyOnWriteArrayList) {
        Intrinsics.checkNotNullParameter(copyOnWriteArrayList, "<set-?>");
        pauseListenFunc = copyOnWriteArrayList;
    }

    public final CopyOnWriteArrayList<Function0<Unit>> getResumeListenFunc() {
        return resumeListenFunc;
    }

    public final void setResumeListenFunc(CopyOnWriteArrayList<Function0<Unit>> copyOnWriteArrayList) {
        Intrinsics.checkNotNullParameter(copyOnWriteArrayList, "<set-?>");
        resumeListenFunc = copyOnWriteArrayList;
    }

    public final CopyOnWriteArrayList<Function0<Unit>> getStopListenFunc() {
        return stopListenFunc;
    }

    public final void setStopListenFunc(CopyOnWriteArrayList<Function0<Unit>> copyOnWriteArrayList) {
        Intrinsics.checkNotNullParameter(copyOnWriteArrayList, "<set-?>");
        stopListenFunc = copyOnWriteArrayList;
    }

    public final CopyOnWriteArrayList<Function0<Unit>> getBackListenFunc() {
        return backListenFunc;
    }

    public final void setBackListenFunc(CopyOnWriteArrayList<Function0<Unit>> copyOnWriteArrayList) {
        Intrinsics.checkNotNullParameter(copyOnWriteArrayList, "<set-?>");
        backListenFunc = copyOnWriteArrayList;
    }

    public final CopyOnWriteArrayList<Function3<Integer, UTSArray<String>, UTSArray<Number>, Unit>> getPermissionsResultListenFunc() {
        return permissionsResultListenFunc;
    }

    public final void setPermissionsResultListenFunc(CopyOnWriteArrayList<Function3<Integer, UTSArray<String>, UTSArray<Number>, Unit>> copyOnWriteArrayList) {
        Intrinsics.checkNotNullParameter(copyOnWriteArrayList, "<set-?>");
        permissionsResultListenFunc = copyOnWriteArrayList;
    }

    public final CopyOnWriteArrayList<Function1<Number, Unit>> getOnTrimMemoryListenFunc() {
        return onTrimMemoryListenFunc;
    }

    public final void setOnTrimMemoryListenFunc(CopyOnWriteArrayList<Function1<Number, Unit>> copyOnWriteArrayList) {
        Intrinsics.checkNotNullParameter(copyOnWriteArrayList, "<set-?>");
        onTrimMemoryListenFunc = copyOnWriteArrayList;
    }

    public final CopyOnWriteArrayList<Function1<UTSJSONObject, Unit>> getOnConfigChangedListenFunc() {
        return onConfigChangedListenFunc;
    }

    public final void setOnConfigChangedListenFunc(CopyOnWriteArrayList<Function1<UTSJSONObject, Unit>> copyOnWriteArrayList) {
        Intrinsics.checkNotNullParameter(copyOnWriteArrayList, "<set-?>");
        onConfigChangedListenFunc = copyOnWriteArrayList;
    }

    public final CopyOnWriteArrayList<Function0<Unit>> getOnCreateListenFunc() {
        return onCreateListenFunc;
    }

    public final void setOnCreateListenFunc(CopyOnWriteArrayList<Function0<Unit>> copyOnWriteArrayList) {
        Intrinsics.checkNotNullParameter(copyOnWriteArrayList, "<set-?>");
        onCreateListenFunc = copyOnWriteArrayList;
    }

    public final CopyOnWriteArrayList<Function1<UTSArray<String>, Unit>> getPermissionRequestFunc() {
        return permissionRequestFunc;
    }

    public final CopyOnWriteArrayList<Function1<UTSArray<String>, Unit>> getPermissionConfirmFunc() {
        return permissionConfirmFunc;
    }

    public final CopyOnWriteArrayList<Function1<UTSArray<String>, Unit>> getPermissionRequestFinishedFunc() {
        return permissionRequestFinishedFunc;
    }

    public final void initContext(AbsSDKInstance instance2) {
        Intrinsics.checkNotNullParameter(instance2, "instance");
        hostAppContext = instance2.getContext().getApplicationContext();
        instance = instance2;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [io.dcloud.uts.android.AndroidUTSContext$initApp$2] */
    public final void initApp() {
        ActionBus.getInstance().observeAction(new AbsActionObserver(new IObserveAble() { // from class: io.dcloud.uts.android.AndroidUTSContext.initApp.2
            @Override // io.dcloud.common.DHInterface.message.IObserveAble
            public EnumUniqueID getActionObserverID() {
                return EnumUniqueID.FEATURE_UTS;
            }
        }) { // from class: io.dcloud.uts.android.AndroidUTSContext.initApp.1
            {
                super(anonymousClass2);
            }

            @Override // io.dcloud.common.DHInterface.message.AbsActionObserver
            public boolean handleMessage(IAction t) {
                if (t instanceof AppOnTrimMemoryAction) {
                    Iterator<Function1<Number, Unit>> it = AndroidUTSContext.INSTANCE.getOnTrimMemoryListenFunc().iterator();
                    Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
                    while (it.hasNext()) {
                        it.next().invoke2(Integer.valueOf(((AppOnTrimMemoryAction) t).getLevel()));
                    }
                    return true;
                }
                if (t instanceof AppOnConfigChangedAction) {
                    Iterator<Function1<UTSJSONObject, Unit>> it2 = AndroidUTSContext.INSTANCE.getOnConfigChangedListenFunc().iterator();
                    Intrinsics.checkNotNullExpressionValue(it2, "iterator(...)");
                    while (it2.hasNext()) {
                        Function1<UTSJSONObject, Unit> next = it2.next();
                        UTSJSONObject uTSJSONObject = new UTSJSONObject();
                        JSONObject object = JSONObject.parseObject(JSON.toJSONString(((AppOnConfigChangedAction) t).getConfig()));
                        Intrinsics.checkNotNullExpressionValue(object, "parseObject(...)");
                        uTSJSONObject.fillJSON(object);
                        next.invoke2(uTSJSONObject);
                    }
                    return true;
                }
                if (t instanceof WebActivityOnDestroyAction) {
                    Iterator<Function0<Unit>> it3 = AndroidUTSContext.INSTANCE.getDestroyListenFunc().iterator();
                    Intrinsics.checkNotNullExpressionValue(it3, "iterator(...)");
                    while (it3.hasNext()) {
                        it3.next().invoke();
                    }
                    AndroidUTSContext.INSTANCE.getDestroyListenFunc().clear();
                    AndroidUTSContext.INSTANCE.getPauseListenFunc().clear();
                    AndroidUTSContext.INSTANCE.getResumeListenFunc().clear();
                    AndroidUTSContext.INSTANCE.getStopListenFunc().clear();
                    AndroidUTSContext.INSTANCE.getBackListenFunc().clear();
                    AndroidUTSContext.INSTANCE.getPermissionsResultListenFunc().clear();
                    AndroidUTSContext.INSTANCE.getOnTrimMemoryListenFunc().clear();
                    AndroidUTSContext.INSTANCE.getOnConfigChangedListenFunc().clear();
                    AndroidUTSContext.INSTANCE.getPermissionRequestFunc().clear();
                    AndroidUTSContext.INSTANCE.getPermissionConfirmFunc().clear();
                    AndroidUTSContext.INSTANCE.getPermissionRequestFinishedFunc().clear();
                    ActionBus.getInstance().stopObserve(EnumUniqueID.FEATURE_UTS);
                    return true;
                }
                if (!(t instanceof PermissionRequestAction)) {
                    return false;
                }
                AndroidUTSContext androidUTSContext = AndroidUTSContext.INSTANCE;
                PermissionRequestAction permissionRequestAction = (PermissionRequestAction) t;
                String type = permissionRequestAction.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                UTSArray.Companion companion = UTSArray.INSTANCE;
                String[] permissions = permissionRequestAction.getPermissions();
                Intrinsics.checkNotNullExpressionValue(permissions, "getPermissions(...)");
                androidUTSContext.permission(type, companion.fromNative(permissions));
                return false;
            }
        });
    }

    public final void permission(String type, UTSArray<String> permissions) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        if (Intrinsics.areEqual(type, PermissionRequestAction.TYPE_REQUEST)) {
            Iterator<Function1<UTSArray<String>, Unit>> it = permissionRequestFunc.iterator();
            Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
            while (it.hasNext()) {
                it.next().invoke2(permissions);
            }
            return;
        }
        if (Intrinsics.areEqual(type, PermissionRequestAction.TYPE_CONFIRM)) {
            Iterator<Function1<UTSArray<String>, Unit>> it2 = permissionConfirmFunc.iterator();
            Intrinsics.checkNotNullExpressionValue(it2, "iterator(...)");
            while (it2.hasNext()) {
                it2.next().invoke2(permissions);
            }
            return;
        }
        if (Intrinsics.areEqual(type, PermissionRequestAction.TYPE_COMPLETE)) {
            Iterator<Function1<UTSArray<String>, Unit>> it3 = permissionRequestFinishedFunc.iterator();
            Intrinsics.checkNotNullExpressionValue(it3, "iterator(...)");
            while (it3.hasNext()) {
                it3.next().invoke2(permissions);
            }
        }
    }
}
