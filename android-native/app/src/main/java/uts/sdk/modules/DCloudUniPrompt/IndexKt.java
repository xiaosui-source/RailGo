package uts.sdk.modules.DCloudUniPrompt;

import android.app.Activity;
import android.widget.Toast;
import com.facebook.common.util.UriUtil;
import com.taobao.weex.adapter.IWXUserTrackAdapter;
import com.taobao.weex.ui.component.WXImage;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.uts.NumberKt;
import io.dcloud.uts.UTSAndroid;
import io.dcloud.uts.UTSArrayKt;
import io.dcloud.uts.UTSCallback;
import io.dcloud.uts.UTSJSONObject;
import io.dcloud.uts.UTSJSONObjectKt;
import io.dcloud.uts.UTSTimerKt;
import java.lang.ref.WeakReference;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000\u0082\u0002\n\u0000\n\u0002\u0010\u0004\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010@\u001a\u00020\n2\u0006\u0010A\u001a\u00020\u0013\u001a\u0006\u0010B\u001a\u00020\n\u001a\u000e\u0010C\u001a\u00020\n2\u0006\u0010D\u001a\u00020\u001e\u001a\u0006\u0010E\u001a\u00020\n\u001a\u0010\u0010F\u001a\u00020\n2\b\u0010G\u001a\u0004\u0018\u00010\u0010\u001a\u001e\u0010H\u001a\u00020\n2\u0006\u0010A\u001a\u00020\u001e2\u0006\u0010G\u001a\u00020\u00102\u0006\u0010I\u001a\u00020\u0010\u001a\u001e\u0010Q\u001a\u00020\n2\u0006\u0010A\u001a\u00020\u00132\u0006\u0010G\u001a\u00020\u00102\u0006\u0010I\u001a\u00020\u0010\u001a\u000e\u0010X\u001a\u00020\n2\u0006\u0010A\u001a\u00020'\u001a\u000e\u0010_\u001a\u00020\n2\u0006\u0010A\u001a\u00020/\u001a\u0010\u0010`\u001a\u00020a2\b\u0010b\u001a\u0004\u0018\u00010\u0010\u001a\u000e\u0010w\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020x\u001a\u0006\u0010y\u001a\u00020\n\u001a\u000e\u0010z\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020{\u001a\u0006\u0010|\u001a\u00020\n\u001a\u000e\u0010}\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020~\u001a\u000f\u0010\u007f\u001a\u00020\n2\u0007\u0010\u0014\u001a\u00030\u0080\u0001\"\u001c\u00100\u001a\u0004\u0018\u00010\u0001X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u00102\"\u0004\b3\u00104\"\u001c\u00105\u001a\u0004\u0018\u000106X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u00108\"\u0004\b9\u0010:\"\u001c\u0010;\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?\"\"\u0010J\u001a\n\u0012\u0004\u0012\u00020L\u0018\u00010KX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bM\u0010N\"\u0004\bO\u0010P\"\u001c\u0010R\u001a\u0004\u0018\u00010SX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bT\u0010U\"\u0004\bV\u0010W\"\u001c\u0010Y\u001a\u0004\u0018\u00010ZX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b[\u0010\\\"\u0004\b]\u0010^\"0\u0010c\u001a!\u0012\u0013\u0012\u00110\u0013¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\n0\u0005j\u0002`d¢\u0006\b\n\u0000\u001a\u0004\be\u0010f\"\u001b\u0010g\u001a\f\u0012\u0004\u0012\u00020\n0\u0016j\u0002`h¢\u0006\b\n\u0000\u001a\u0004\bi\u0010j\"0\u0010k\u001a!\u0012\u0013\u0012\u00110\u001e¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\n0\u0005j\u0002`l¢\u0006\b\n\u0000\u001a\u0004\bm\u0010f\"\u001b\u0010n\u001a\f\u0012\u0004\u0012\u00020\n0\u0016j\u0002`o¢\u0006\b\n\u0000\u001a\u0004\bp\u0010j\"0\u0010q\u001a!\u0012\u0013\u0012\u00110'¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\n0\u0005j\u0002`r¢\u0006\b\n\u0000\u001a\u0004\bs\u0010f\"0\u0010t\u001a!\u0012\u0013\u0012\u00110/¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\n0\u0005j\u0002`u¢\u0006\b\n\u0000\u001a\u0004\bv\u0010f*\n\u0010\u0000\"\u00020\u00012\u00020\u0001*\n\u0010\u0002\"\u00020\u00032\u00020\u0003*@\u0010\u0004\"\u001d\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u00052\u001d\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u0005*S\u0010\u000b\"\u001d\u0012\u0013\u0012\u0011`\f¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u000520\u0012&\u0012$0\u0003j\u0011`\f¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u0005*@\u0010\r\"\u001d\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u00052\u001d\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u0005*\n\u0010\u000f\"\u00020\u00102\u00020\u0010*\n\u0010\u0011\"\u00020\u00102\u00020\u0010*@\u0010\u0012\"\u001d\u0012\u0013\u0012\u00110\u0013¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\n0\u00052\u001d\u0012\u0013\u0012\u00110\u0013¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\n0\u0005*\u0016\u0010\u0015\"\b\u0012\u0004\u0012\u00020\n0\u00162\b\u0012\u0004\u0012\u00020\n0\u0016*\n\u0010\u0017\"\u00020\u00032\u00020\u0003*@\u0010\u0018\"\u001d\u0012\u0013\u0012\u00110\u0019¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u00052\u001d\u0012\u0013\u0012\u00110\u0019¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u0005*S\u0010\u001a\"\u001d\u0012\u0013\u0012\u0011`\u001b¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u000520\u0012&\u0012$0\u0003j\u0011`\u001b¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u0005*@\u0010\u001c\"\u001d\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u00052\u001d\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u0005*@\u0010\u001d\"\u001d\u0012\u0013\u0012\u00110\u001e¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\n0\u00052\u001d\u0012\u0013\u0012\u00110\u001e¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\n0\u0005*\u0016\u0010\u001f\"\b\u0012\u0004\u0012\u00020\n0\u00162\b\u0012\u0004\u0012\u00020\n0\u0016*\n\u0010 \"\u00020\u00032\u00020\u0003*@\u0010!\"\u001d\u0012\u0013\u0012\u00110\"¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u00052\u001d\u0012\u0013\u0012\u00110\"¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u0005*S\u0010#\"\u001d\u0012\u0013\u0012\u0011`$¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u000520\u0012&\u0012$0\u0003j\u0011`$¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u0005*@\u0010%\"\u001d\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u00052\u001d\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u0005*@\u0010&\"\u001d\u0012\u0013\u0012\u00110'¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\n0\u00052\u001d\u0012\u0013\u0012\u00110'¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\n0\u0005*\n\u0010(\"\u00020\u00032\u00020\u0003*@\u0010)\"\u001d\u0012\u0013\u0012\u00110*¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u00052\u001d\u0012\u0013\u0012\u00110*¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u0005*S\u0010+\"\u001d\u0012\u0013\u0012\u0011`,¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u000520\u0012&\u0012$0\u0003j\u0011`,¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u0005*@\u0010-\"\u001d\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u00052\u001d\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u0005*@\u0010.\"\u001d\u0012\u0013\u0012\u00110/¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\n0\u00052\u001d\u0012\u0013\u0012\u00110/¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\n0\u0005¨\u0006\u0081\u0001"}, d2 = {"PromptErrorCode", "", "ShowToastFail", "Luts/sdk/modules/DCloudUniPrompt/IPromptError;", "ShowToastSuccessCallback", "Lkotlin/Function1;", "Luts/sdk/modules/DCloudUniPrompt/ShowToastSuccess;", "Lkotlin/ParameterName;", "name", UriUtil.LOCAL_RESOURCE_SCHEME, "", "ShowToastFailCallback", "Luts/sdk/modules/DCloudUniPrompt/ShowToastFail;", "ShowToastCompleteCallback", "", "Icon", "", "ShowToastPosition", "ShowToast", "Luts/sdk/modules/DCloudUniPrompt/ShowToastOptions;", "options", "HideToast", "Lkotlin/Function0;", "ShowLoadingFail", "ShowLoadingSuccessCallback", "Luts/sdk/modules/DCloudUniPrompt/ShowLoadingSuccess;", "ShowLoadingFailCallback", "Luts/sdk/modules/DCloudUniPrompt/ShowLoadingFail;", "ShowLoadingCompleteCallback", "ShowLoading", "Luts/sdk/modules/DCloudUniPrompt/ShowLoadingOptions;", "HideLoading", "ShowModalFail", "ShowModalSuccessCallback", "Luts/sdk/modules/DCloudUniPrompt/ShowModalSuccess;", "ShowModalFailCallback", "Luts/sdk/modules/DCloudUniPrompt/ShowModalFail;", "ShowModalCompleteCallback", "ShowModal", "Luts/sdk/modules/DCloudUniPrompt/ShowModalOptions;", "ShowActionSheetFail", "ShowActionSheetSuccessCallback", "Luts/sdk/modules/DCloudUniPrompt/ShowActionSheetSuccess;", "ShowActionSheetFailCallback", "Luts/sdk/modules/DCloudUniPrompt/ShowActionSheetFail;", "ShowActionSheetCompleteCallback", "ShowActionSheet", "Luts/sdk/modules/DCloudUniPrompt/ShowActionSheetOptions;", "timeout", "getTimeout", "()Ljava/lang/Number;", "setTimeout", "(Ljava/lang/Number;)V", "toast", "Luts/sdk/modules/DCloudUniPrompt/WaitingView;", "getToast", "()Luts/sdk/modules/DCloudUniPrompt/WaitingView;", "setToast", "(Luts/sdk/modules/DCloudUniPrompt/WaitingView;)V", "toastType", "getToastType", "()Ljava/lang/String;", "setToastType", "(Ljava/lang/String;)V", "showToastImpl", "style", "hideToastImpl", "showLoadingImpl", AbsoluteConst.JSON_KEY_OPTION, "hideLoadingImpl", "closeToast", "type", "makeLoading", IWXUserTrackAdapter.MONITOR_ERROR_MSG, "androidToastCache", "Ljava/lang/ref/WeakReference;", "Landroid/widget/Toast;", "getAndroidToastCache", "()Ljava/lang/ref/WeakReference;", "setAndroidToastCache", "(Ljava/lang/ref/WeakReference;)V", "makeToast", "utsDialog", "Luts/sdk/modules/DCloudUniPrompt/UTSDialog;", "getUtsDialog", "()Luts/sdk/modules/DCloudUniPrompt/UTSDialog;", "setUtsDialog", "(Luts/sdk/modules/DCloudUniPrompt/UTSDialog;)V", "showModalImpl", "uniActionSheet", "Luts/sdk/modules/DCloudUniPrompt/UniActionSheet;", "getUniActionSheet", "()Luts/sdk/modules/DCloudUniPrompt/UniActionSheet;", "setUniActionSheet", "(Luts/sdk/modules/DCloudUniPrompt/UniActionSheet;)V", "actionSheetImpl", "isValidColor", "", "colorStr", "showToast", "Luts/sdk/modules/DCloudUniPrompt/ShowToast;", "getShowToast", "()Lkotlin/jvm/functions/Function1;", "hideToast", "Luts/sdk/modules/DCloudUniPrompt/HideToast;", "getHideToast", "()Lkotlin/jvm/functions/Function0;", "showLoading", "Luts/sdk/modules/DCloudUniPrompt/ShowLoading;", "getShowLoading", "hideLoading", "Luts/sdk/modules/DCloudUniPrompt/HideLoading;", "getHideLoading", "showModal", "Luts/sdk/modules/DCloudUniPrompt/ShowModal;", "getShowModal", "showActionSheet", "Luts/sdk/modules/DCloudUniPrompt/ShowActionSheet;", "getShowActionSheet", "showToastByJs", "Luts/sdk/modules/DCloudUniPrompt/ShowToastOptionsJSONObject;", "hideToastByJs", "showLoadingByJs", "Luts/sdk/modules/DCloudUniPrompt/ShowLoadingOptionsJSONObject;", "hideLoadingByJs", "showModalByJs", "Luts/sdk/modules/DCloudUniPrompt/ShowModalOptionsJSONObject;", "showActionSheetByJs", "Luts/sdk/modules/DCloudUniPrompt/ShowActionSheetOptionsJSONObject;", "uni-prompt_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class IndexKt {
    private static WeakReference<Toast> androidToastCache;
    private static Number timeout;
    private static WaitingView toast;
    private static String toastType;
    private static UniActionSheet uniActionSheet;
    private static UTSDialog utsDialog;
    private static final Function1<ShowToastOptions, Unit> showToast = new Function1() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda23
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object invoke2(Object obj) {
            return IndexKt.showToast$lambda$6((ShowToastOptions) obj);
        }
    };
    private static final Function0<Unit> hideToast = new Function0() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda24
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return IndexKt.hideToast$lambda$8();
        }
    };
    private static final Function1<ShowLoadingOptions, Unit> showLoading = new Function1() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda25
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object invoke2(Object obj) {
            return IndexKt.showLoading$lambda$10((ShowLoadingOptions) obj);
        }
    };
    private static final Function0<Unit> hideLoading = new Function0() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda26
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return IndexKt.hideLoading$lambda$12();
        }
    };
    private static final Function1<ShowModalOptions, Unit> showModal = new Function1() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda27
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object invoke2(Object obj) {
            return IndexKt.showModal$lambda$14((ShowModalOptions) obj);
        }
    };
    private static final Function1<ShowActionSheetOptions, Unit> showActionSheet = new Function1() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda28
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object invoke2(Object obj) {
            return IndexKt.showActionSheet$lambda$16((ShowActionSheetOptions) obj);
        }
    };

    public static final Number getTimeout() {
        return timeout;
    }

    public static final void setTimeout(Number number) {
        timeout = number;
    }

    public static final WaitingView getToast() {
        return toast;
    }

    public static final void setToast(WaitingView waitingView) {
        toast = waitingView;
    }

    public static final String getToastType() {
        return toastType;
    }

    public static final void setToastType(String str) {
        toastType = str;
    }

    public static final void showToastImpl(ShowToastOptions style) {
        Intrinsics.checkNotNullParameter(style, "style");
        makeToast(style, "toast", "showToast");
    }

    public static final void hideToastImpl() {
        closeToast("toast");
    }

    public static final void showLoadingImpl(ShowLoadingOptions option) {
        Intrinsics.checkNotNullParameter(option, "option");
        makeLoading(option, "loading", "showLoading");
    }

    public static final void hideLoadingImpl() {
        closeToast("loading");
    }

    public static final void closeToast(String str) {
        if (str == null || str == toastType) {
            Number number = timeout;
            if (number != null) {
                Intrinsics.checkNotNull(number, "null cannot be cast to non-null type kotlin.Number");
                if (NumberKt.compareTo(number, (Number) 0) > 0) {
                    Number number2 = timeout;
                    Intrinsics.checkNotNull(number2, "null cannot be cast to non-null type kotlin.Number");
                    UTSTimerKt.clearTimeout(number2);
                    timeout = null;
                }
            }
            WaitingView waitingView = toast;
            if (waitingView != null) {
                Intrinsics.checkNotNull(waitingView, "null cannot be cast to non-null type uts.sdk.modules.DCloudUniPrompt.WaitingView");
                waitingView.close();
                toast = null;
            }
            WaitingView waitingView2 = toast;
            if (waitingView2 != null) {
                Intrinsics.checkNotNull(waitingView2, "null cannot be cast to non-null type uts.sdk.modules.DCloudUniPrompt.WaitingView");
                waitingView2.close();
                toast = null;
            }
            WeakReference<Toast> weakReference = androidToastCache;
            if ((weakReference != null ? weakReference.get() : null) != null) {
                WeakReference<Toast> weakReference2 = androidToastCache;
                Intrinsics.checkNotNull(weakReference2);
                Toast toast2 = weakReference2.get();
                Intrinsics.checkNotNull(toast2);
                toast2.cancel();
            }
            toastType = null;
        }
    }

    public static final void makeLoading(ShowLoadingOptions style, String type, String errMsg) {
        Intrinsics.checkNotNullParameter(style, "style");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(errMsg, "errMsg");
        closeToast(null);
        if (style.getTitle() == null) {
            PromptErrorImpl promptErrorImpl = new PromptErrorImpl((Number) 1001, "showLoading:title is null");
            Function1<IPromptError, Unit> fail = style.getFail();
            if (fail != null) {
                fail.invoke2(promptErrorImpl);
            }
            Function1<Object, Unit> complete = style.getComplete();
            if (complete != null) {
                complete.invoke2(promptErrorImpl);
                return;
            }
            return;
        }
        toastType = type;
        UTSJSONObjectKt._uO(new Pair[0]);
        UTSJSONObject uTSJSONObject_uO = UTSJSONObjectKt._uO(TuplesKt.to("name", style.getTitle()), TuplesKt.to(AbsoluteConst.JSON_KEY_MODAL, style.getMask()), TuplesKt.to("back", "transmit"), TuplesKt.to("padding", "10"), TuplesKt.to(AbsoluteConst.JSON_KEY_SIZE, "16"));
        uTSJSONObject_uO.set("width", "140");
        uTSJSONObject_uO.set("height", "112");
        WaitingView waitingView = new WaitingView(UTSAndroid.INSTANCE.getTopPageActivity(), uTSJSONObject_uO, UTSAndroid.INSTANCE.getTopPageView());
        toast = waitingView;
        waitingView.showWaiting();
        UTSAndroid.INSTANCE.onAppActivityDestroy(new Function0() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return IndexKt.makeLoading$lambda$0();
            }
        });
        ShowLoadingSuccess showLoadingSuccess = new ShowLoadingSuccess();
        Function1<ShowLoadingSuccess, Unit> success = style.getSuccess();
        if (success != null) {
            success.invoke2(showLoadingSuccess);
        }
        Function1<Object, Unit> complete2 = style.getComplete();
        if (complete2 != null) {
            complete2.invoke2(showLoadingSuccess);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit makeLoading$lambda$0() {
        WaitingView waitingView = toast;
        if (waitingView != null) {
            waitingView.close();
        }
        toast = null;
        return Unit.INSTANCE;
    }

    public static final WeakReference<Toast> getAndroidToastCache() {
        return androidToastCache;
    }

    public static final void setAndroidToastCache(WeakReference<Toast> weakReference) {
        androidToastCache = weakReference;
    }

    public static final void makeToast(ShowToastOptions style, String type, String errMsg) {
        String str;
        Intrinsics.checkNotNullParameter(style, "style");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(errMsg, "errMsg");
        closeToast(null);
        if (style.getTitle() == null || style.getTitle().length() == 0) {
            PromptErrorImpl promptErrorImpl = new PromptErrorImpl((Number) 1001, "showLoading:title is null");
            Function1<IPromptError, Unit> fail = style.getFail();
            if (fail != null) {
                fail.invoke2(promptErrorImpl);
            }
            Function1<Object, Unit> complete = style.getComplete();
            if (complete != null) {
                complete.invoke2(promptErrorImpl);
                return;
            }
            return;
        }
        toastType = type;
        if (CollectionsKt.indexOf((List<? extends String>) UTSArrayKt._uA("top", "center", "bottom"), style.getPosition()) >= 0) {
            Toast toastMakeText = Toast.makeText(UTSAndroid.INSTANCE.getAppContext(), style.getTitle(), 0);
            String position = style.getPosition();
            if (position != null) {
                int iHashCode = position.hashCode();
                if (iHashCode != -1383228885) {
                    if (iHashCode != -1364013995) {
                        if (iHashCode == 115029 && position.equals("top")) {
                            Intrinsics.checkNotNull(toastMakeText);
                            toastMakeText.setGravity(48, toastMakeText.getXOffset(), toastMakeText.getYOffset());
                        }
                    } else if (position.equals("center")) {
                        Intrinsics.checkNotNull(toastMakeText);
                        toastMakeText.setGravity(17, 0, 0);
                    }
                } else if (position.equals("bottom")) {
                    Intrinsics.checkNotNull(toastMakeText);
                    toastMakeText.setGravity(80, toastMakeText.getXOffset(), toastMakeText.getYOffset());
                }
            }
            Intrinsics.checkNotNull(toastMakeText);
            toastMakeText.show();
            androidToastCache = new WeakReference<>(toastMakeText);
            ShowToastSuccess showToastSuccess = new ShowToastSuccess();
            Function1<ShowToastSuccess, Unit> success = style.getSuccess();
            if (success != null) {
                success.invoke2(showToastSuccess);
            }
            Function1<Object, Unit> complete2 = style.getComplete();
            if (complete2 != null) {
                complete2.invoke2(showToastSuccess);
                return;
            }
            return;
        }
        UTSJSONObjectKt._uO(new Pair[0]);
        String icon = style.getIcon();
        if (icon == null || UTSArrayKt._uA(WXImage.SUCCEED, "loading", "error", "none").indexOf(icon) < 0) {
            icon = WXImage.SUCCEED;
        }
        UTSJSONObject uTSJSONObject_uO = UTSJSONObjectKt._uO(TuplesKt.to("name", style.getTitle()), TuplesKt.to(AbsoluteConst.JSON_KEY_MODAL, style.getMask()), TuplesKt.to("back", "transmit"), TuplesKt.to("padding", "10"), TuplesKt.to(AbsoluteConst.JSON_KEY_SIZE, "16"));
        if ((style.getImage() == null || Intrinsics.areEqual(style.getImage(), "")) && Intrinsics.areEqual(icon, "none")) {
            uTSJSONObject_uO.set("loading", UTSJSONObjectKt._uO(TuplesKt.to("display", "none")));
        } else {
            uTSJSONObject_uO.set("width", "140");
            uTSJSONObject_uO.set("height", "112");
        }
        if (style.getImage() != null && !Intrinsics.areEqual(style.getImage(), "")) {
            String image = style.getImage();
            Intrinsics.checkNotNull(image);
            uTSJSONObject_uO.set("loading", UTSJSONObjectKt._uO(TuplesKt.to("display", AbsoluteConst.JSON_VALUE_BLOCK), TuplesKt.to("height", "55"), TuplesKt.to(AbsoluteConst.JSON_KEY_ICON, image)));
        } else if (UTSArrayKt._uA(WXImage.SUCCEED, "error").indexOf(icon) >= 0) {
            Pair[] pairArr = new Pair[3];
            pairArr[0] = TuplesKt.to("display", AbsoluteConst.JSON_VALUE_BLOCK);
            pairArr[1] = TuplesKt.to("height", "36");
            if (Intrinsics.areEqual(icon, WXImage.SUCCEED)) {
                str = "successIcon";
            } else {
                str = "errorIcon";
            }
            pairArr[2] = TuplesKt.to(AbsoluteConst.JSON_KEY_ICON, str);
            uTSJSONObject_uO.set("loading", UTSJSONObjectKt._uO(pairArr));
        }
        WaitingView waitingView = new WaitingView(UTSAndroid.INSTANCE.getTopPageActivity(), uTSJSONObject_uO, UTSAndroid.INSTANCE.getTopPageView());
        toast = waitingView;
        waitingView.showWaiting();
        UTSAndroid.INSTANCE.onAppActivityDestroy(new Function0() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return IndexKt.makeToast$lambda$1();
            }
        });
        Number duration = style.getDuration();
        if (duration == null || NumberKt.compareTo(duration, (Number) 0) <= 0) {
            duration = (Number) 1500;
        }
        if (type != "loading") {
            timeout = UTSTimerKt.setTimeout(new Function0() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda21
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return IndexKt.makeToast$lambda$2();
                }
            }, duration);
        }
        ShowToastSuccess showToastSuccess2 = new ShowToastSuccess();
        Function1<ShowToastSuccess, Unit> success2 = style.getSuccess();
        if (success2 != null) {
            success2.invoke2(showToastSuccess2);
        }
        Function1<Object, Unit> complete3 = style.getComplete();
        if (complete3 != null) {
            complete3.invoke2(showToastSuccess2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit makeToast$lambda$1() {
        WaitingView waitingView = toast;
        if (waitingView != null) {
            waitingView.close();
        }
        toast = null;
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit makeToast$lambda$2() {
        WaitingView waitingView = toast;
        if (waitingView != null) {
            waitingView.close();
        }
        return Unit.INSTANCE;
    }

    public static final UTSDialog getUtsDialog() {
        return utsDialog;
    }

    public static final void setUtsDialog(UTSDialog uTSDialog) {
        utsDialog = uTSDialog;
    }

    public static final void showModalImpl(ShowModalOptions style) {
        Intrinsics.checkNotNullParameter(style, "style");
        UTSDialog uTSDialog = utsDialog;
        if (uTSDialog != null) {
            if (uTSDialog != null) {
                uTSDialog.dismiss();
            }
            utsDialog = null;
        }
        if (UTSAndroid.INSTANCE.getTopPageActivity() == null) {
            return;
        }
        Activity topPageActivity = UTSAndroid.INSTANCE.getTopPageActivity();
        Intrinsics.checkNotNull(topPageActivity);
        if (topPageActivity.isFinishing()) {
            return;
        }
        Activity topPageActivity2 = UTSAndroid.INSTANCE.getTopPageActivity();
        Intrinsics.checkNotNull(topPageActivity2);
        UTSDialog uTSDialog2 = new UTSDialog(topPageActivity2);
        utsDialog = uTSDialog2;
        uTSDialog2.initStyle(style);
        UTSDialog uTSDialog3 = utsDialog;
        if (uTSDialog3 != null) {
            uTSDialog3.show();
        }
        UTSAndroid.INSTANCE.onAppActivityDestroy(new Function0() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return IndexKt.showModalImpl$lambda$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showModalImpl$lambda$3() {
        UTSDialog uTSDialog = utsDialog;
        if (uTSDialog != null) {
            uTSDialog.dismiss();
        }
        utsDialog = null;
        return Unit.INSTANCE;
    }

    public static final UniActionSheet getUniActionSheet() {
        return uniActionSheet;
    }

    public static final void setUniActionSheet(UniActionSheet uniActionSheet2) {
        uniActionSheet = uniActionSheet2;
    }

    public static final void actionSheetImpl(ShowActionSheetOptions style) {
        Intrinsics.checkNotNullParameter(style, "style");
        if (style.getItemList() == null || NumberKt.compareTo(style.getItemList().getLength(), (Number) 1) < 0) {
            PromptErrorImpl promptErrorImpl = new PromptErrorImpl((Number) 1001, "showActionSheet:fail parameter error: parameter.itemList should have at least 1 item");
            Function1<IPromptError, Unit> fail = style.getFail();
            if (fail != null) {
                fail.invoke2(promptErrorImpl);
            }
            Function1<Object, Unit> complete = style.getComplete();
            if (complete != null) {
                complete.invoke2(promptErrorImpl);
                return;
            }
            return;
        }
        if (style.getItemList().size() > 6) {
            PromptErrorImpl promptErrorImpl2 = new PromptErrorImpl((Number) 1001, "showActionSheet:fail parameter error: itemList should not be large than 6");
            Function1<IPromptError, Unit> fail2 = style.getFail();
            if (fail2 != null) {
                fail2.invoke2(promptErrorImpl2);
            }
            Function1<Object, Unit> complete2 = style.getComplete();
            if (complete2 != null) {
                complete2.invoke2(promptErrorImpl2);
                return;
            }
            return;
        }
        UniActionSheet uniActionSheet2 = uniActionSheet;
        if (uniActionSheet2 != null) {
            if (uniActionSheet2 != null) {
                uniActionSheet2.dismiss();
            }
            uniActionSheet = null;
        }
        if (UTSAndroid.INSTANCE.getTopPageActivity() == null) {
            return;
        }
        Activity topPageActivity = UTSAndroid.INSTANCE.getTopPageActivity();
        Intrinsics.checkNotNull(topPageActivity);
        if (topPageActivity.isFinishing()) {
            return;
        }
        Activity topPageActivity2 = UTSAndroid.INSTANCE.getTopPageActivity();
        Intrinsics.checkNotNull(topPageActivity2);
        UniActionSheet uniActionSheet3 = new UniActionSheet(topPageActivity2, style);
        uniActionSheet = uniActionSheet3;
        uniActionSheet3.show();
        UTSAndroid.INSTANCE.onAppActivityDestroy(new Function0() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return IndexKt.actionSheetImpl$lambda$4();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit actionSheetImpl$lambda$4() {
        UniActionSheet uniActionSheet2 = uniActionSheet;
        if (uniActionSheet2 != null) {
            uniActionSheet2.dismiss();
        }
        uniActionSheet = null;
        return Unit.INSTANCE;
    }

    public static final boolean isValidColor(String str) {
        return str != null && str.length() == 7 && StringsKt.startsWith$default(str, "#", false, 2, (Object) null);
    }

    public static final Function1<ShowToastOptions, Unit> getShowToast() {
        return showToast;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showToast$lambda$6(final ShowToastOptions showToastOptions) {
        UTSAndroid.INSTANCE.dispatchAsync("main", new Function1() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.showToast$lambda$6$lambda$5(showToastOptions, obj);
            }
        }, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showToast$lambda$6$lambda$5(ShowToastOptions showToastOptions, Object obj) {
        showToastImpl(showToastOptions);
        return Unit.INSTANCE;
    }

    public static final Function0<Unit> getHideToast() {
        return hideToast;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit hideToast$lambda$8() {
        UTSAndroid.INSTANCE.dispatchAsync("main", new Function1() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda20
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.hideToast$lambda$8$lambda$7(obj);
            }
        }, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit hideToast$lambda$8$lambda$7(Object obj) {
        hideToastImpl();
        return Unit.INSTANCE;
    }

    public static final Function1<ShowLoadingOptions, Unit> getShowLoading() {
        return showLoading;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showLoading$lambda$10(final ShowLoadingOptions showLoadingOptions) {
        UTSAndroid.INSTANCE.dispatchAsync("main", new Function1() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda22
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.showLoading$lambda$10$lambda$9(showLoadingOptions, obj);
            }
        }, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showLoading$lambda$10$lambda$9(ShowLoadingOptions showLoadingOptions, Object obj) {
        showLoadingImpl(showLoadingOptions);
        return Unit.INSTANCE;
    }

    public static final Function0<Unit> getHideLoading() {
        return hideLoading;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit hideLoading$lambda$12() {
        UTSAndroid.INSTANCE.dispatchAsync("main", new Function1() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.hideLoading$lambda$12$lambda$11(obj);
            }
        }, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit hideLoading$lambda$12$lambda$11(Object obj) {
        hideLoadingImpl();
        return Unit.INSTANCE;
    }

    public static final Function1<ShowModalOptions, Unit> getShowModal() {
        return showModal;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showModal$lambda$14(final ShowModalOptions showModalOptions) {
        UTSAndroid.INSTANCE.dispatchAsync("main", new Function1() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.showModal$lambda$14$lambda$13(showModalOptions, obj);
            }
        }, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showModal$lambda$14$lambda$13(ShowModalOptions showModalOptions, Object obj) {
        showModalImpl(showModalOptions);
        return Unit.INSTANCE;
    }

    public static final Function1<ShowActionSheetOptions, Unit> getShowActionSheet() {
        return showActionSheet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showActionSheet$lambda$16(final ShowActionSheetOptions showActionSheetOptions) {
        UTSAndroid.INSTANCE.dispatchAsync("main", new Function1() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.showActionSheet$lambda$16$lambda$15(showActionSheetOptions, obj);
            }
        }, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showActionSheet$lambda$16$lambda$15(ShowActionSheetOptions showActionSheetOptions, Object obj) {
        actionSheetImpl(showActionSheetOptions);
        return Unit.INSTANCE;
    }

    public static final void showToastByJs(final ShowToastOptionsJSONObject options) {
        Intrinsics.checkNotNullParameter(options, "options");
        showToast.invoke2(new ShowToastOptions(options.getTitle(), options.getIcon(), options.getImage(), options.getMask(), options.getDuration(), options.getPosition(), new Function1() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.showToastByJs$lambda$17(options, (ShowToastSuccess) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.showToastByJs$lambda$18(options, (IPromptError) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.showToastByJs$lambda$19(options, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showToastByJs$lambda$17(ShowToastOptionsJSONObject showToastOptionsJSONObject, ShowToastSuccess showToastSuccess) throws SecurityException {
        UTSCallback success = showToastOptionsJSONObject.getSuccess();
        if (success != null) {
            success.invoke(showToastSuccess);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showToastByJs$lambda$18(ShowToastOptionsJSONObject showToastOptionsJSONObject, IPromptError iPromptError) throws SecurityException {
        UTSCallback fail = showToastOptionsJSONObject.getFail();
        if (fail != null) {
            fail.invoke(iPromptError);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showToastByJs$lambda$19(ShowToastOptionsJSONObject showToastOptionsJSONObject, Object obj) throws SecurityException {
        UTSCallback complete = showToastOptionsJSONObject.getComplete();
        if (complete != null) {
            complete.invoke(obj);
        }
        return Unit.INSTANCE;
    }

    public static final void hideToastByJs() {
        hideToast.invoke();
    }

    public static final void showLoadingByJs(final ShowLoadingOptionsJSONObject options) {
        Intrinsics.checkNotNullParameter(options, "options");
        showLoading.invoke2(new ShowLoadingOptions(options.getTitle(), options.getMask(), new Function1() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.showLoadingByJs$lambda$20(options, (ShowLoadingSuccess) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.showLoadingByJs$lambda$21(options, (IPromptError) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.showLoadingByJs$lambda$22(options, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showLoadingByJs$lambda$20(ShowLoadingOptionsJSONObject showLoadingOptionsJSONObject, ShowLoadingSuccess showLoadingSuccess) throws SecurityException {
        UTSCallback success = showLoadingOptionsJSONObject.getSuccess();
        if (success != null) {
            success.invoke(showLoadingSuccess);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showLoadingByJs$lambda$21(ShowLoadingOptionsJSONObject showLoadingOptionsJSONObject, IPromptError iPromptError) throws SecurityException {
        UTSCallback fail = showLoadingOptionsJSONObject.getFail();
        if (fail != null) {
            fail.invoke(iPromptError);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showLoadingByJs$lambda$22(ShowLoadingOptionsJSONObject showLoadingOptionsJSONObject, Object obj) throws SecurityException {
        UTSCallback complete = showLoadingOptionsJSONObject.getComplete();
        if (complete != null) {
            complete.invoke(obj);
        }
        return Unit.INSTANCE;
    }

    public static final void hideLoadingByJs() {
        hideLoading.invoke();
    }

    public static final void showModalByJs(final ShowModalOptionsJSONObject options) {
        Intrinsics.checkNotNullParameter(options, "options");
        showModal.invoke2(new ShowModalOptions(options.getTitle(), options.getContent(), options.getShowCancel(), options.getCancelText(), options.getCancelColor(), options.getConfirmText(), options.getConfirmColor(), options.getEditable(), options.getPlaceholderText(), new Function1() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.showModalByJs$lambda$23(options, (ShowModalSuccess) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.showModalByJs$lambda$24(options, (IPromptError) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.showModalByJs$lambda$25(options, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showModalByJs$lambda$23(ShowModalOptionsJSONObject showModalOptionsJSONObject, ShowModalSuccess showModalSuccess) throws SecurityException {
        UTSCallback success = showModalOptionsJSONObject.getSuccess();
        if (success != null) {
            success.invoke(showModalSuccess);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showModalByJs$lambda$24(ShowModalOptionsJSONObject showModalOptionsJSONObject, IPromptError iPromptError) throws SecurityException {
        UTSCallback fail = showModalOptionsJSONObject.getFail();
        if (fail != null) {
            fail.invoke(iPromptError);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showModalByJs$lambda$25(ShowModalOptionsJSONObject showModalOptionsJSONObject, Object obj) throws SecurityException {
        UTSCallback complete = showModalOptionsJSONObject.getComplete();
        if (complete != null) {
            complete.invoke(obj);
        }
        return Unit.INSTANCE;
    }

    public static final void showActionSheetByJs(final ShowActionSheetOptionsJSONObject options) {
        Intrinsics.checkNotNullParameter(options, "options");
        showActionSheet.invoke2(new ShowActionSheetOptions(options.getTitle(), options.getAlertText(), options.getItemList(), options.getItemColor(), options.getPopover(), new Function1() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.showActionSheetByJs$lambda$26(options, (ShowActionSheetSuccess) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.showActionSheetByJs$lambda$27(options, (IPromptError) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniPrompt.IndexKt$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.showActionSheetByJs$lambda$28(options, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showActionSheetByJs$lambda$26(ShowActionSheetOptionsJSONObject showActionSheetOptionsJSONObject, ShowActionSheetSuccess showActionSheetSuccess) throws SecurityException {
        UTSCallback success = showActionSheetOptionsJSONObject.getSuccess();
        if (success != null) {
            success.invoke(showActionSheetSuccess);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showActionSheetByJs$lambda$27(ShowActionSheetOptionsJSONObject showActionSheetOptionsJSONObject, IPromptError iPromptError) throws SecurityException {
        UTSCallback fail = showActionSheetOptionsJSONObject.getFail();
        if (fail != null) {
            fail.invoke(iPromptError);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showActionSheetByJs$lambda$28(ShowActionSheetOptionsJSONObject showActionSheetOptionsJSONObject, Object obj) throws SecurityException {
        UTSCallback complete = showActionSheetOptionsJSONObject.getComplete();
        if (complete != null) {
            complete.invoke(obj);
        }
        return Unit.INSTANCE;
    }
}
