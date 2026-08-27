package io.dcloud.uniapp.extapi;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import uts.sdk.modules.DCloudUniPrompt.IndexKt;
import uts.sdk.modules.DCloudUniPrompt.ShowActionSheetOptions;
import uts.sdk.modules.DCloudUniPrompt.ShowLoadingOptions;
import uts.sdk.modules.DCloudUniPrompt.ShowModalOptions;
import uts.sdk.modules.DCloudUniPrompt.ShowToastOptions;

/* compiled from: uniPrompt.kt */
@Metadata(d1 = {"\u0000´\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0004\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\"0\u0010$\u001a!\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b((\u0012\u0004\u0012\u00020)0%j\u0002`*¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,\"\u001b\u0010-\u001a\f\u0012\u0004\u0012\u00020)0.j\u0002`/¢\u0006\b\n\u0000\u001a\u0004\b0\u00101\"0\u00102\u001a!\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b((\u0012\u0004\u0012\u00020)0%j\u0002`3¢\u0006\b\n\u0000\u001a\u0004\b4\u0010,\"\u001b\u00105\u001a\f\u0012\u0004\u0012\u00020)0.j\u0002`6¢\u0006\b\n\u0000\u001a\u0004\b7\u00101\"0\u00108\u001a!\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b((\u0012\u0004\u0012\u00020)0%j\u0002`9¢\u0006\b\n\u0000\u001a\u0004\b:\u0010,\"0\u0010;\u001a!\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b((\u0012\u0004\u0012\u00020)0%j\u0002`<¢\u0006\b\n\u0000\u001a\u0004\b=\u0010,*\n\u0010\u0000\"\u00020\u00012\u00020\u0001*\n\u0010\u0002\"\u00020\u00032\u00020\u0003*\n\u0010\u0004\"\u00020\u00052\u00020\u0005*\n\u0010\u0006\"\u00020\u00072\u00020\u0007*\n\u0010\b\"\u00020\t2\u00020\t*\n\u0010\n\"\u00020\u000b2\u00020\u000b*\n\u0010\f\"\u00020\r2\u00020\r*\n\u0010\u000e\"\u00020\u000f2\u00020\u000f*\n\u0010\u0010\"\u00020\u00112\u00020\u0011*\n\u0010\u0012\"\u00020\u00132\u00020\u0013*\u000e\u0010\u0014\"\u0002`\u00152\u00060\u0016j\u0002`\u0015*\u000e\u0010\u0017\"\u0002`\u00182\u00060\u0013j\u0002`\u0018*\u000e\u0010\u0019\"\u0002`\u001a2\u00060\u001bj\u0002`\u001a*\u000e\u0010\u001c\"\u0002`\u001d2\u00060\u001bj\u0002`\u001d*\u000e\u0010\u001e\"\u0002`\u001f2\u00060\u0013j\u0002`\u001f*\u000e\u0010 \"\u0002`!2\u00060\u0013j\u0002`!*\u000e\u0010\"\"\u0002`#2\u00060\u0013j\u0002`#¨\u0006>"}, d2 = {"ShowToastSuccess", "Luts/sdk/modules/DCloudUniPrompt/ShowToastSuccess;", "ShowToastOptions", "Luts/sdk/modules/DCloudUniPrompt/ShowToastOptions;", "ShowLoadingSuccess", "Luts/sdk/modules/DCloudUniPrompt/ShowLoadingSuccess;", "ShowLoadingOptions", "Luts/sdk/modules/DCloudUniPrompt/ShowLoadingOptions;", "ShowModalSuccess", "Luts/sdk/modules/DCloudUniPrompt/ShowModalSuccess;", "ShowModalOptions", "Luts/sdk/modules/DCloudUniPrompt/ShowModalOptions;", "ShowActionSheetSuccess", "Luts/sdk/modules/DCloudUniPrompt/ShowActionSheetSuccess;", "Popover", "Luts/sdk/modules/DCloudUniPrompt/Popover;", "ShowActionSheetOptions", "Luts/sdk/modules/DCloudUniPrompt/ShowActionSheetOptions;", "IPromptError", "Luts/sdk/modules/DCloudUniPrompt/IPromptError;", "PromptErrorCode", "Luts/sdk/modules/DCloudUniPrompt/PromptErrorCode;", "", "ShowToastFail", "Luts/sdk/modules/DCloudUniPrompt/ShowToastFail;", "Icon", "Luts/sdk/modules/DCloudUniPrompt/Icon;", "", "ShowToastPosition", "Luts/sdk/modules/DCloudUniPrompt/ShowToastPosition;", "ShowLoadingFail", "Luts/sdk/modules/DCloudUniPrompt/ShowLoadingFail;", "ShowModalFail", "Luts/sdk/modules/DCloudUniPrompt/ShowModalFail;", "ShowActionSheetFail", "Luts/sdk/modules/DCloudUniPrompt/ShowActionSheetFail;", "showToast", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "options", "", "Luts/sdk/modules/DCloudUniPrompt/ShowToast;", "getShowToast", "()Lkotlin/jvm/functions/Function1;", "hideToast", "Lkotlin/Function0;", "Luts/sdk/modules/DCloudUniPrompt/HideToast;", "getHideToast", "()Lkotlin/jvm/functions/Function0;", "showLoading", "Luts/sdk/modules/DCloudUniPrompt/ShowLoading;", "getShowLoading", "hideLoading", "Luts/sdk/modules/DCloudUniPrompt/HideLoading;", "getHideLoading", "showModal", "Luts/sdk/modules/DCloudUniPrompt/ShowModal;", "getShowModal", "showActionSheet", "Luts/sdk/modules/DCloudUniPrompt/ShowActionSheet;", "getShowActionSheet", "uni-prompt_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UniPromptKt {
    private static final Function1<ShowToastOptions, Unit> showToast = IndexKt.getShowToast();
    private static final Function0<Unit> hideToast = IndexKt.getHideToast();
    private static final Function1<ShowLoadingOptions, Unit> showLoading = IndexKt.getShowLoading();
    private static final Function0<Unit> hideLoading = IndexKt.getHideLoading();
    private static final Function1<ShowModalOptions, Unit> showModal = IndexKt.getShowModal();
    private static final Function1<ShowActionSheetOptions, Unit> showActionSheet = IndexKt.getShowActionSheet();

    public static final Function1<ShowToastOptions, Unit> getShowToast() {
        return showToast;
    }

    public static final Function0<Unit> getHideToast() {
        return hideToast;
    }

    public static final Function1<ShowLoadingOptions, Unit> getShowLoading() {
        return showLoading;
    }

    public static final Function0<Unit> getHideLoading() {
        return hideLoading;
    }

    public static final Function1<ShowModalOptions, Unit> getShowModal() {
        return showModal;
    }

    public static final Function1<ShowActionSheetOptions, Unit> getShowActionSheet() {
        return showActionSheet;
    }
}
