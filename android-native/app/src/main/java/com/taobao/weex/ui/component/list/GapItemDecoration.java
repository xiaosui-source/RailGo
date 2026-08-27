package com.taobao.weex.ui.component.list;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.taobao.weex.common.IWXObject;
import com.taobao.weex.utils.WXViewUtils;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class GapItemDecoration extends RecyclerView.ItemDecoration {
    private WXListComponent listComponent;

    public GapItemDecoration(WXListComponent wXListComponent) {
        this.listComponent = wXListComponent;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        int childAdapterPosition;
        Float[] spanOffsets = this.listComponent.getSpanOffsets();
        if (spanOffsets != null && (childAdapterPosition = recyclerView.getChildAdapterPosition(view)) >= 0 && (view.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams)) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            if (layoutParams.isFullSpan()) {
                return;
            }
            IWXObject listChild = this.listComponent.getListChild(childAdapterPosition);
            if (listChild instanceof WXCell) {
                WXCell wXCell = (WXCell) listChild;
                if (wXCell.isFixed() || wXCell.isSticky() || layoutParams.getSpanIndex() >= spanOffsets.length) {
                    return;
                }
                int iRound = Math.round(WXViewUtils.getRealPxByWidth(this.listComponent.getSpanOffsets()[this.listComponent.isLayoutRTL() ? (spanOffsets.length - layoutParams.getSpanIndex()) - 1 : layoutParams.getSpanIndex()].floatValue(), this.listComponent.getViewPortWidthForFloat()));
                if (this.listComponent.isLayoutRTL()) {
                    rect.left = -iRound;
                    rect.right = iRound;
                } else {
                    rect.left = iRound;
                    rect.right = -iRound;
                }
            }
        }
    }
}
