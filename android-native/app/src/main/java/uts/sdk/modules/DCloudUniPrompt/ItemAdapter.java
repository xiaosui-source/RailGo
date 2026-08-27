package uts.sdk.modules.DCloudUniPrompt;

import android.app.Dialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.taobao.weex.ui.component.WXBasicComponentType;
import io.dcloud.uts.UTSAndroid;
import io.dcloud.uts.UTSArray;
import io.dcloud.uts.prompt.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001%B\u0019\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001bH\u0016J\u001c\u0010\u001d\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001bH\u0016J\u001c\u0010!\u001a\u00020\"2\n\u0010#\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u001c\u001a\u00020\u001bH\u0016J\b\u0010$\u001a\u00020\u001bH\u0016R \u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0006X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0004X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019¨\u0006&"}, d2 = {"Luts/sdk/modules/DCloudUniPrompt/ItemAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Luts/sdk/modules/DCloudUniPrompt/ItemAdapter$ViewHolder;", "dialog", "Landroid/app/Dialog;", "style", "Luts/sdk/modules/DCloudUniPrompt/ShowActionSheetOptions;", "<init>", "(Landroid/app/Dialog;Luts/sdk/modules/DCloudUniPrompt/ShowActionSheetOptions;)V", "mItemList", "Lio/dcloud/uts/UTSArray;", "", "getMItemList", "()Lio/dcloud/uts/UTSArray;", "setMItemList", "(Lio/dcloud/uts/UTSArray;)V", "hostStyle", "getHostStyle", "()Luts/sdk/modules/DCloudUniPrompt/ShowActionSheetOptions;", "setHostStyle", "(Luts/sdk/modules/DCloudUniPrompt/ShowActionSheetOptions;)V", "hostDialog", "getHostDialog", "()Landroid/app/Dialog;", "setHostDialog", "(Landroid/app/Dialog;)V", "getItemViewType", "", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "onBindViewHolder", "", "holder", "getItemCount", "ViewHolder", "uni-prompt_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class ItemAdapter extends RecyclerView.Adapter<ViewHolder> {
    public Dialog hostDialog;
    public ShowActionSheetOptions hostStyle;
    public UTSArray<String> mItemList;

    public UTSArray<String> getMItemList() {
        UTSArray<String> uTSArray = this.mItemList;
        if (uTSArray != null) {
            return uTSArray;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mItemList");
        return null;
    }

    public void setMItemList(UTSArray<String> uTSArray) {
        Intrinsics.checkNotNullParameter(uTSArray, "<set-?>");
        this.mItemList = uTSArray;
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

    public Dialog getHostDialog() {
        Dialog dialog = this.hostDialog;
        if (dialog != null) {
            return dialog;
        }
        Intrinsics.throwUninitializedPropertyAccessException("hostDialog");
        return null;
    }

    public void setHostDialog(Dialog dialog) {
        Intrinsics.checkNotNullParameter(dialog, "<set-?>");
        this.hostDialog = dialog;
    }

    public ItemAdapter(Dialog dialog, ShowActionSheetOptions style) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        Intrinsics.checkNotNullParameter(style, "style");
        setHostDialog(dialog);
        setMItemList(style.getItemList());
        setHostStyle(style);
    }

    /* compiled from: index.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0096\u0004\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005R\u001a\u0010\u0006\u001a\u00020\u0007X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Luts/sdk/modules/DCloudUniPrompt/ItemAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", WXBasicComponentType.VIEW, "Landroid/view/View;", "<init>", "(Luts/sdk/modules/DCloudUniPrompt/ItemAdapter;Landroid/view/View;)V", "itemName", "Landroid/widget/TextView;", "getItemName", "()Landroid/widget/TextView;", "setItemName", "(Landroid/widget/TextView;)V", "uni-prompt_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        final /* synthetic */ ItemAdapter this$0;

        public TextView getItemName() {
            TextView textView = this.itemName;
            if (textView != null) {
                return textView;
            }
            Intrinsics.throwUninitializedPropertyAccessException("itemName");
            return null;
        }

        public void setItemName(TextView textView) {
            Intrinsics.checkNotNullParameter(textView, "<set-?>");
            this.itemName = textView;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ViewHolder(ItemAdapter itemAdapter, View view) {
            super(view);
            Intrinsics.checkNotNullParameter(view, "view");
            this.this$0 = itemAdapter;
            View viewFindViewById = view.findViewById(R.id.tvName);
            Intrinsics.checkNotNull(viewFindViewById, "null cannot be cast to non-null type android.widget.TextView");
            setItemName((TextView) viewFindViewById);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        String title = getHostStyle().getTitle();
        return ((title == null || StringsKt.isBlank(title)) && position == 0) ? 1001 : 1002;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewInflate;
        Intrinsics.checkNotNullParameter(parent, "parent");
        if (viewType == 1002) {
            if (UTSAndroid.INSTANCE.getAppDarkMode()) {
                viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.uni_app_uni_prompt_ac_recyclerview_layout_night, parent, false);
                Intrinsics.checkNotNullExpressionValue(viewInflate, "inflate(...)");
            } else {
                viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.uni_app_uni_prompt_ac_recyclerview_layout, parent, false);
                Intrinsics.checkNotNullExpressionValue(viewInflate, "inflate(...)");
            }
        } else if (UTSAndroid.INSTANCE.getAppDarkMode()) {
            viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.uni_app_uni_prompt_ac_recyclerview_layout_top_night, parent, false);
            Intrinsics.checkNotNullExpressionValue(viewInflate, "inflate(...)");
        } else {
            viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.uni_app_uni_prompt_ac_recyclerview_layout_top, parent, false);
            Intrinsics.checkNotNullExpressionValue(viewInflate, "inflate(...)");
        }
        return new ViewHolder(this, viewInflate);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        String str = getMItemList().get(position);
        Intrinsics.checkNotNullExpressionValue(str, "get(...)");
        holder.getItemName().setText(str);
        if (getHostStyle().getItemColor() != null && IndexKt.isValidColor(getHostStyle().getItemColor())) {
            holder.getItemName().setTextColor(Color.parseColor(getHostStyle().getItemColor()));
        }
        holder.getItemName().setOnClickListener(new ItemClickListener(getHostDialog(), getHostStyle(), Integer.valueOf(position)));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return getMItemList().size();
    }
}
