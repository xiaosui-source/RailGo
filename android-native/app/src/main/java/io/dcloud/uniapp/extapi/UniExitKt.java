package io.dcloud.uniapp.extapi;

import com.alibaba.android.bindingx.core.internal.BindingXConstants;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import uts.sdk.modules.DCloudUniExit.ExitOptions;
import uts.sdk.modules.DCloudUniExit.IndexKt;

/* compiled from: uniExit.kt */
@Metadata(d1 = {"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0004\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"2\u0010\u000b\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0003¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u00100\fj\u0002`\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013*\n\u0010\u0000\"\u00020\u00012\u00020\u0001*\n\u0010\u0002\"\u00020\u00032\u00020\u0003*\n\u0010\u0004\"\u00020\u00052\u00020\u0005*\u000e\u0010\u0006\"\u0002`\u00072\u00060\bj\u0002`\u0007*\u000e\u0010\t\"\u0002`\n2\u00060\u0005j\u0002`\n¨\u0006\u0014"}, d2 = {"ExitSuccess", "Luts/sdk/modules/DCloudUniExit/ExitSuccess;", "ExitOptions", "Luts/sdk/modules/DCloudUniExit/ExitOptions;", "IExitError", "Luts/sdk/modules/DCloudUniExit/IExitError;", "ExitErrorCode", "Luts/sdk/modules/DCloudUniExit/ExitErrorCode;", "", "ExitFail", "Luts/sdk/modules/DCloudUniExit/ExitFail;", BindingXConstants.STATE_EXIT, "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "options", "", "Luts/sdk/modules/DCloudUniExit/Exit;", "getExit", "()Lkotlin/jvm/functions/Function1;", "uni-exit_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UniExitKt {
    private static final Function1<ExitOptions, Unit> exit = IndexKt.getExit();

    public static final Function1<ExitOptions, Unit> getExit() {
        return exit;
    }
}
