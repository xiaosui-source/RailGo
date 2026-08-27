package uts.sdk.modules.DCloudUniPrompt;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.android.bindingx.core.internal.BindingXConstants;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.uts.UTSAndroid;
import io.dcloud.uts.prompt.R;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: index.kt */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\b\u0010-\u001a\u00020.H\u0016R\u001a\u0010\b\u001a\u00020\u0003X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u0005X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0013X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0015\"\u0004\b\u001a\u0010\u0017R\u001a\u0010\u001b\u001a\u00020\u001cX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\"X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001a\u0010'\u001a\u00020\"X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010$\"\u0004\b)\u0010&R\u001a\u0010*\u001a\u00020\"X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010$\"\u0004\b,\u0010&¨\u0006/"}, d2 = {"Luts/sdk/modules/DCloudUniPrompt/UniActionSheet;", "Landroid/app/Dialog;", "activity", "Landroid/app/Activity;", "style", "Luts/sdk/modules/DCloudUniPrompt/ShowActionSheetOptions;", "<init>", "(Landroid/app/Activity;Luts/sdk/modules/DCloudUniPrompt/ShowActionSheetOptions;)V", "hostActivity", "getHostActivity", "()Landroid/app/Activity;", "setHostActivity", "(Landroid/app/Activity;)V", "hostStyle", "getHostStyle", "()Luts/sdk/modules/DCloudUniPrompt/ShowActionSheetOptions;", "setHostStyle", "(Luts/sdk/modules/DCloudUniPrompt/ShowActionSheetOptions;)V", AbsoluteConst.JSON_KEY_TITLE, "Landroidx/appcompat/widget/AppCompatTextView;", "getTitle", "()Landroidx/appcompat/widget/AppCompatTextView;", "setTitle", "(Landroidx/appcompat/widget/AppCompatTextView;)V", BindingXConstants.STATE_CANCEL, "getCancel", "setCancel", "myRecyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "getMyRecyclerView", "()Landroidx/recyclerview/widget/RecyclerView;", "setMyRecyclerView", "(Landroidx/recyclerview/widget/RecyclerView;)V", "lineTitle", "Landroidx/appcompat/widget/LinearLayoutCompat;", "getLineTitle", "()Landroidx/appcompat/widget/LinearLayoutCompat;", "setLineTitle", "(Landroidx/appcompat/widget/LinearLayoutCompat;)V", "lineContent", "getLineContent", "setLineContent", "lineCancel", "getLineCancel", "setLineCancel", "dismiss", "", "uni-prompt_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class UniActionSheet extends Dialog {
    public AppCompatTextView cancel;
    public Activity hostActivity;
    public ShowActionSheetOptions hostStyle;
    public LinearLayoutCompat lineCancel;
    public LinearLayoutCompat lineContent;
    public LinearLayoutCompat lineTitle;
    public RecyclerView myRecyclerView;
    public AppCompatTextView title;

    public Activity getHostActivity() {
        Activity activity = this.hostActivity;
        if (activity != null) {
            return activity;
        }
        Intrinsics.throwUninitializedPropertyAccessException("hostActivity");
        return null;
    }

    public void setHostActivity(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "<set-?>");
        this.hostActivity = activity;
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

    public AppCompatTextView getTitle() {
        AppCompatTextView appCompatTextView = this.title;
        if (appCompatTextView != null) {
            return appCompatTextView;
        }
        Intrinsics.throwUninitializedPropertyAccessException(AbsoluteConst.JSON_KEY_TITLE);
        return null;
    }

    public void setTitle(AppCompatTextView appCompatTextView) {
        Intrinsics.checkNotNullParameter(appCompatTextView, "<set-?>");
        this.title = appCompatTextView;
    }

    public AppCompatTextView getCancel() {
        AppCompatTextView appCompatTextView = this.cancel;
        if (appCompatTextView != null) {
            return appCompatTextView;
        }
        Intrinsics.throwUninitializedPropertyAccessException(BindingXConstants.STATE_CANCEL);
        return null;
    }

    public void setCancel(AppCompatTextView appCompatTextView) {
        Intrinsics.checkNotNullParameter(appCompatTextView, "<set-?>");
        this.cancel = appCompatTextView;
    }

    public RecyclerView getMyRecyclerView() {
        RecyclerView recyclerView = this.myRecyclerView;
        if (recyclerView != null) {
            return recyclerView;
        }
        Intrinsics.throwUninitializedPropertyAccessException("myRecyclerView");
        return null;
    }

    public void setMyRecyclerView(RecyclerView recyclerView) {
        Intrinsics.checkNotNullParameter(recyclerView, "<set-?>");
        this.myRecyclerView = recyclerView;
    }

    public LinearLayoutCompat getLineTitle() {
        LinearLayoutCompat linearLayoutCompat = this.lineTitle;
        if (linearLayoutCompat != null) {
            return linearLayoutCompat;
        }
        Intrinsics.throwUninitializedPropertyAccessException("lineTitle");
        return null;
    }

    public void setLineTitle(LinearLayoutCompat linearLayoutCompat) {
        Intrinsics.checkNotNullParameter(linearLayoutCompat, "<set-?>");
        this.lineTitle = linearLayoutCompat;
    }

    public LinearLayoutCompat getLineContent() {
        LinearLayoutCompat linearLayoutCompat = this.lineContent;
        if (linearLayoutCompat != null) {
            return linearLayoutCompat;
        }
        Intrinsics.throwUninitializedPropertyAccessException("lineContent");
        return null;
    }

    public void setLineContent(LinearLayoutCompat linearLayoutCompat) {
        Intrinsics.checkNotNullParameter(linearLayoutCompat, "<set-?>");
        this.lineContent = linearLayoutCompat;
    }

    public LinearLayoutCompat getLineCancel() {
        LinearLayoutCompat linearLayoutCompat = this.lineCancel;
        if (linearLayoutCompat != null) {
            return linearLayoutCompat;
        }
        Intrinsics.throwUninitializedPropertyAccessException("lineCancel");
        return null;
    }

    public void setLineCancel(LinearLayoutCompat linearLayoutCompat) {
        Intrinsics.checkNotNullParameter(linearLayoutCompat, "<set-?>");
        this.lineCancel = linearLayoutCompat;
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        PromptErrorImpl promptErrorImpl = new PromptErrorImpl((Number) 1, "showActionSheet:fail cancel");
        Function1<IPromptError, Unit> fail = getHostStyle().getFail();
        if (fail != null) {
            fail.invoke2(promptErrorImpl);
        }
        getHostStyle().setFail(null);
        Function1<Object, Unit> complete = getHostStyle().getComplete();
        if (complete != null) {
            complete.invoke2(promptErrorImpl);
        }
        getHostStyle().setComplete(null);
        super.dismiss();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UniActionSheet(Activity activity, ShowActionSheetOptions style) {
        super(activity, R.style.uni_app_uni_prompt_ActionsheetDialog);
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(style, "style");
        setHostActivity(activity);
        setHostStyle(style);
        if (UTSAndroid.INSTANCE.getAppDarkMode()) {
            setContentView(R.layout.uni_app_uni_prompt_uts_action_sheet_night);
        } else {
            setContentView(R.layout.uni_app_uni_prompt_uts_action_sheet);
        }
        View viewFindViewById = findViewById(R.id.tvTitle);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        setTitle((AppCompatTextView) viewFindViewById);
        View viewFindViewById2 = findViewById(R.id.tvCancelAction);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        setCancel((AppCompatTextView) viewFindViewById2);
        View viewFindViewById3 = findViewById(R.id.line_title);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
        setLineTitle((LinearLayoutCompat) viewFindViewById3);
        View viewFindViewById4 = findViewById(R.id.line_content);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(...)");
        setLineContent((LinearLayoutCompat) viewFindViewById4);
        View viewFindViewById5 = findViewById(R.id.line_cancel);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(...)");
        setLineCancel((LinearLayoutCompat) viewFindViewById5);
        View viewFindViewById6 = findViewById(R.id.myRecyclerview);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(...)");
        setMyRecyclerView((RecyclerView) viewFindViewById6);
        getMyRecyclerView().setLayoutManager(new LinearLayoutManager(getHostActivity()));
        UniActionSheet uniActionSheet = this;
        getCancel().setOnClickListener(new CancelClickListener(uniActionSheet, getHostStyle()));
        getMyRecyclerView().setAdapter(new ItemAdapter(uniActionSheet, getHostStyle()));
        String title = style.getTitle();
        if (title != null && !StringsKt.isBlank(title)) {
            getLineTitle().setVisibility(0);
            getTitle().setText(style.getTitle());
        }
        if (getWindow() != null) {
            Window window = getWindow();
            Intrinsics.checkNotNull(window);
            window.setLayout(-1, -2);
            Window window2 = getWindow();
            Intrinsics.checkNotNull(window2);
            window2.setGravity(81);
            Window window3 = getWindow();
            Intrinsics.checkNotNull(window3);
            if (window3.getAttributes() != null) {
                Window window4 = getWindow();
                Intrinsics.checkNotNull(window4);
                WindowManager.LayoutParams attributes = window4.getAttributes();
                Intrinsics.checkNotNull(attributes);
                attributes.windowAnimations = R.style.uni_app_uni_prompt_DialogAnimations_slideWindow;
            }
        }
    }
}
