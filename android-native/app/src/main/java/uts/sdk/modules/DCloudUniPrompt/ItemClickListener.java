package uts.sdk.modules.DCloudUniPrompt;

import android.app.Dialog;
import android.view.View;
import io.dcloud.common.util.CreateShortResultReceiver;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0004\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B!\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\u0012\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016R\u001a\u0010\n\u001a\u00020\u0003X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0005X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0007X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018¨\u0006\u001d"}, d2 = {"Luts/sdk/modules/DCloudUniPrompt/ItemClickListener;", "Landroid/view/View$OnClickListener;", "dialog", "Landroid/app/Dialog;", "style", "Luts/sdk/modules/DCloudUniPrompt/ShowActionSheetOptions;", "index", "", "<init>", "(Landroid/app/Dialog;Luts/sdk/modules/DCloudUniPrompt/ShowActionSheetOptions;Ljava/lang/Number;)V", "host", "getHost", "()Landroid/app/Dialog;", "setHost", "(Landroid/app/Dialog;)V", "hostStyle", "getHostStyle", "()Luts/sdk/modules/DCloudUniPrompt/ShowActionSheetOptions;", "setHostStyle", "(Luts/sdk/modules/DCloudUniPrompt/ShowActionSheetOptions;)V", "hostIndex", "getHostIndex", "()Ljava/lang/Number;", "setHostIndex", "(Ljava/lang/Number;)V", "onClick", "", CreateShortResultReceiver.KEY_VERSIONNAME, "Landroid/view/View;", "uni-prompt_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class ItemClickListener implements View.OnClickListener {
    public Dialog host;
    public Number hostIndex;
    public ShowActionSheetOptions hostStyle;

    public Dialog getHost() {
        Dialog dialog = this.host;
        if (dialog != null) {
            return dialog;
        }
        Intrinsics.throwUninitializedPropertyAccessException("host");
        return null;
    }

    public void setHost(Dialog dialog) {
        Intrinsics.checkNotNullParameter(dialog, "<set-?>");
        this.host = dialog;
    }

    public ShowActionSheetOptions getHostStyle() {
        ShowActionSheetOptions showActionSheetOptions = this.hostStyle;
        if (showActionSheetOptions != null) {
            return showActionSheetOptions;
        }
        Intrinsics.throwUninitializedPropertyAccessException("hostStyle");
        return null;
    }

    public void setHostStyle(ShowActionSheetOptions showActionSheetOptions) {
        Intrinsics.checkNotNullParameter(showActionSheetOptions, "<set-?>");
        this.hostStyle = showActionSheetOptions;
    }

    public Number getHostIndex() {
        Number number = this.hostIndex;
        if (number != null) {
            return number;
        }
        Intrinsics.throwUninitializedPropertyAccessException("hostIndex");
        return null;
    }

    public void setHostIndex(Number number) {
        Intrinsics.checkNotNullParameter(number, "<set-?>");
        this.hostIndex = number;
    }

    public ItemClickListener(Dialog dialog, ShowActionSheetOptions style, Number index) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        Intrinsics.checkNotNullParameter(style, "style");
        Intrinsics.checkNotNullParameter(index, "index");
        setHost(dialog);
        setHostStyle(style);
        setHostIndex(index);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        ShowActionSheetSuccess showActionSheetSuccess = new ShowActionSheetSuccess(getHostIndex());
        Function1<ShowActionSheetSuccess, Unit> success = getHostStyle().getSuccess();
        if (success != null) {
            success.invoke(showActionSheetSuccess);
        }
        getHostStyle().setSuccess(null);
        Function1<Object, Unit> complete = getHostStyle().getComplete();
        if (complete != null) {
            complete.invoke(showActionSheetSuccess);
        }
        getHostStyle().setComplete(null);
        getHostStyle().setFail(null);
        getHost().dismiss();
    }
}
