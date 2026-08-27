package uts.sdk.modules.DCloudUniExit;

import com.alibaba.android.bindingx.core.internal.BindingXConstants;
import com.facebook.common.util.UriUtil;
import io.dcloud.uts.UTSAndroid;
import io.dcloud.uts.UTSCallback;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000F\n\u0000\n\u0002\u0010\u0004\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0016\u001a\u00020\n2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0017\"2\u0010\u0012\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0010¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\n0\u0005j\u0002`\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015*\n\u0010\u0000\"\u00020\u00012\u00020\u0001*\n\u0010\u0002\"\u00020\u00032\u00020\u0003*@\u0010\u0004\"\u001d\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u00052\u001d\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u0005*S\u0010\u000b\"\u001d\u0012\u0013\u0012\u0011`\f¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u000520\u0012&\u0012$0\u0003j\u0011`\f¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u0005*@\u0010\r\"\u001d\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u00052\u001d\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u0005*D\u0010\u000f\"\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0010¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\n0\u00052\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0010¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\n0\u0005¨\u0006\u0018"}, d2 = {"ExitErrorCode", "", "ExitFail", "Luts/sdk/modules/DCloudUniExit/IExitError;", "ExitSuccessCallback", "Lkotlin/Function1;", "Luts/sdk/modules/DCloudUniExit/ExitSuccess;", "Lkotlin/ParameterName;", "name", UriUtil.LOCAL_RESOURCE_SCHEME, "", "ExitFailCallback", "Luts/sdk/modules/DCloudUniExit/ExitFail;", "ExitCompleteCallback", "", "Exit", "Luts/sdk/modules/DCloudUniExit/ExitOptions;", "options", BindingXConstants.STATE_EXIT, "Luts/sdk/modules/DCloudUniExit/Exit;", "getExit", "()Lkotlin/jvm/functions/Function1;", "exitByJs", "Luts/sdk/modules/DCloudUniExit/ExitOptionsJSONObject;", "uni-exit_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class IndexKt {
    private static final Function1<ExitOptions, Unit> exit = new Function1() { // from class: uts.sdk.modules.DCloudUniExit.IndexKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return IndexKt.exit$lambda$0((ExitOptions) obj);
        }
    };

    public static final Function1<ExitOptions, Unit> getExit() {
        return exit;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit exit$lambda$0(ExitOptions exitOptions) {
        Function1<Object, Unit> complete;
        Function1<ExitSuccess, Unit> success;
        ExitSuccess exitSuccess = new ExitSuccess("exit:ok");
        if (exitOptions != null && (success = exitOptions.getSuccess()) != null) {
            success.invoke(exitSuccess);
        }
        if (exitOptions != null && (complete = exitOptions.getComplete()) != null) {
            complete.invoke(exitSuccess);
        }
        UTSAndroid.INSTANCE.exit();
        return Unit.INSTANCE;
    }

    public static final void exitByJs(final ExitOptionsJSONObject exitOptionsJSONObject) {
        exit.invoke(exitOptionsJSONObject != null ? new ExitOptions(new Function1() { // from class: uts.sdk.modules.DCloudUniExit.IndexKt$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return IndexKt.exitByJs$lambda$1(exitOptionsJSONObject, (ExitSuccess) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniExit.IndexKt$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return IndexKt.exitByJs$lambda$2(exitOptionsJSONObject, (IExitError) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniExit.IndexKt$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return IndexKt.exitByJs$lambda$3(exitOptionsJSONObject, obj);
            }
        }) : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit exitByJs$lambda$1(ExitOptionsJSONObject exitOptionsJSONObject, ExitSuccess exitSuccess) throws SecurityException {
        Intrinsics.checkNotNull(exitOptionsJSONObject);
        UTSCallback success = exitOptionsJSONObject.getSuccess();
        if (success != null) {
            success.invoke(exitSuccess);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit exitByJs$lambda$2(ExitOptionsJSONObject exitOptionsJSONObject, IExitError iExitError) throws SecurityException {
        Intrinsics.checkNotNull(exitOptionsJSONObject);
        UTSCallback fail = exitOptionsJSONObject.getFail();
        if (fail != null) {
            fail.invoke(iExitError);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit exitByJs$lambda$3(ExitOptionsJSONObject exitOptionsJSONObject, Object obj) throws SecurityException {
        Intrinsics.checkNotNull(exitOptionsJSONObject);
        UTSCallback complete = exitOptionsJSONObject.getComplete();
        if (complete != null) {
            complete.invoke(obj);
        }
        return Unit.INSTANCE;
    }
}
