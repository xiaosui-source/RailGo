package com.taobao.weex.ui.component.list.template;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.collection.ArrayMap;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.view.listview.WXRecyclerView;
import com.taobao.weex.ui.view.refresh.wrapper.BounceRecyclerView;
import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class TemplateStickyHelper {
    private WXRecyclerTemplateList recyclerTemplateList;
    private List<Integer> stickyPositions = new ArrayList();
    private ArrayMap<Integer, TemplateViewHolder> stickyHolderCache = new ArrayMap<>();
    private List<String> mStickyTypes = new ArrayList(8);

    public TemplateStickyHelper(WXRecyclerTemplateList wXRecyclerTemplateList) {
        this.recyclerTemplateList = wXRecyclerTemplateList;
    }

    public List<Integer> getStickyPositions() {
        if (this.stickyPositions == null) {
            this.stickyPositions = new ArrayList();
        }
        return this.stickyPositions;
    }

    public List<String> getStickyTypes() {
        return this.mStickyTypes;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onBeforeScroll(int i, int i2) {
        int iFindLastVisibleItemPosition;
        int iFindFirstVisibleItemPosition;
        TemplateViewHolder templateViewHolder;
        List<Integer> list = this.stickyPositions;
        if (list == null || list.size() == 0) {
            return;
        }
        BounceRecyclerView bounceRecyclerView = (BounceRecyclerView) this.recyclerTemplateList.getHostView();
        WXRecyclerView innerView = ((BounceRecyclerView) this.recyclerTemplateList.getHostView()).getInnerView();
        RecyclerView.LayoutManager layoutManager = innerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            iFindFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            iFindLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] iArr = new int[3];
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int i3 = staggeredGridLayoutManager.findFirstVisibleItemPositions(iArr)[0];
            iFindLastVisibleItemPosition = staggeredGridLayoutManager.findLastVisibleItemPositions(iArr)[0];
            iFindFirstVisibleItemPosition = i3;
        } else {
            iFindLastVisibleItemPosition = -1;
            iFindFirstVisibleItemPosition = -1;
        }
        if (iFindFirstVisibleItemPosition >= 0 && (templateViewHolder = (TemplateViewHolder) innerView.findViewHolderForAdapterPosition(iFindFirstVisibleItemPosition)) != null) {
            int iMax = -1;
            for (Integer num : this.stickyPositions) {
                if (num != null) {
                    if (num.intValue() > iFindFirstVisibleItemPosition) {
                        break;
                    } else {
                        iMax = Math.max(iMax, num.intValue());
                    }
                }
            }
            if (iMax < 0) {
                View childAt = bounceRecyclerView.getChildAt(bounceRecyclerView.getChildCount() - 1);
                if (childAt.getTag() instanceof TemplateViewHolder) {
                    TemplateViewHolder templateViewHolder2 = (TemplateViewHolder) childAt.getTag();
                    bounceRecyclerView.removeView(templateViewHolder2.itemView);
                    templateViewHolder2.itemView.setTranslationY(0.0f);
                    if (templateViewHolder2.getComponent() != null && templateViewHolder2.getComponent().getEvents().contains(Constants.Event.UNSTICKY)) {
                        templateViewHolder2.getComponent().fireEvent(Constants.Event.UNSTICKY);
                    }
                }
                for (int i4 = 0; i4 < innerView.getChildCount(); i4++) {
                    View childAt2 = innerView.getChildAt(i4);
                    TemplateViewHolder templateViewHolder3 = (TemplateViewHolder) innerView.getChildViewHolder(childAt2);
                    if (templateViewHolder3 != null) {
                        if (this.stickyPositions.contains(Integer.valueOf(templateViewHolder3.getAdapterPosition())) && childAt2.getVisibility() != 0) {
                            childAt2.setVisibility(0);
                        }
                    }
                }
                return;
            }
            View childAt3 = bounceRecyclerView.getChildAt(bounceRecyclerView.getChildCount() - 1);
            if (!(childAt3.getTag() instanceof TemplateViewHolder) || ((TemplateViewHolder) childAt3.getTag()).getHolderPosition() != iMax) {
                if ((childAt3.getTag() instanceof TemplateViewHolder) && ((TemplateViewHolder) childAt3.getTag()).getHolderPosition() != iMax) {
                    TemplateViewHolder templateViewHolder4 = (TemplateViewHolder) childAt3.getTag();
                    bounceRecyclerView.removeView(templateViewHolder4.itemView);
                    templateViewHolder4.itemView.setTranslationY(0.0f);
                    if (templateViewHolder4.getComponent() != null && templateViewHolder4.getComponent().getEvents().contains(Constants.Event.UNSTICKY)) {
                        templateViewHolder4.getComponent().fireEvent(Constants.Event.UNSTICKY);
                    }
                }
                int itemViewType = this.recyclerTemplateList.getItemViewType(iMax);
                TemplateViewHolder templateViewHolderOnCreateViewHolder = this.stickyHolderCache.get(Integer.valueOf(itemViewType));
                if (templateViewHolderOnCreateViewHolder == null) {
                    templateViewHolderOnCreateViewHolder = this.recyclerTemplateList.onCreateViewHolder((ViewGroup) innerView, itemViewType);
                    this.stickyHolderCache.put(Integer.valueOf(itemViewType), templateViewHolderOnCreateViewHolder);
                }
                this.recyclerTemplateList.onBindViewHolder(templateViewHolderOnCreateViewHolder, iMax);
                templateViewHolderOnCreateViewHolder.itemView.setTranslationY(0.0f);
                templateViewHolderOnCreateViewHolder.itemView.setTag(templateViewHolderOnCreateViewHolder);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
                templateViewHolderOnCreateViewHolder.getComponent().clearPreLayout();
                if (templateViewHolderOnCreateViewHolder.itemView.getParent() != null) {
                    ((ViewGroup) templateViewHolderOnCreateViewHolder.itemView.getParent()).removeView(templateViewHolderOnCreateViewHolder.itemView);
                }
                bounceRecyclerView.addView(templateViewHolderOnCreateViewHolder.itemView, layoutParams);
                templateViewHolderOnCreateViewHolder.getComponent().setLayout(templateViewHolderOnCreateViewHolder.getComponent());
                View view = templateViewHolderOnCreateViewHolder.itemView;
                if (templateViewHolderOnCreateViewHolder.getComponent() != null && templateViewHolderOnCreateViewHolder.getComponent().getEvents().contains("sticky")) {
                    templateViewHolderOnCreateViewHolder.getComponent().fireEvent("sticky");
                }
                childAt3 = view;
            }
            TemplateViewHolder templateViewHolder5 = (TemplateViewHolder) childAt3.getTag();
            for (int i5 = 0; i5 < innerView.getChildCount(); i5++) {
                View childAt4 = innerView.getChildAt(i5);
                TemplateViewHolder templateViewHolder6 = (TemplateViewHolder) innerView.getChildViewHolder(childAt4);
                if (templateViewHolder6 != null) {
                    int adapterPosition = templateViewHolder6.getAdapterPosition();
                    if (this.stickyPositions.contains(Integer.valueOf(adapterPosition))) {
                        if (adapterPosition == templateViewHolder5.getHolderPosition()) {
                            if (childAt4.getVisibility() != 4) {
                                childAt4.setVisibility(4);
                            }
                        } else if (childAt4.getVisibility() != 0) {
                            childAt4.setVisibility(0);
                        }
                    }
                }
            }
            if (templateViewHolder.getComponent().isSticky()) {
                if (templateViewHolder.itemView.getY() < 0.0f) {
                    if (templateViewHolder.itemView.getVisibility() != 4) {
                        templateViewHolder.itemView.setVisibility(4);
                    }
                    if (childAt3.getVisibility() != 0) {
                        childAt3.setVisibility(0);
                    }
                    childAt3.bringToFront();
                } else {
                    if (templateViewHolder.itemView.getVisibility() != 0) {
                        templateViewHolder.itemView.setVisibility(0);
                    }
                    if (childAt3.getVisibility() != 8) {
                        childAt3.setVisibility(8);
                    }
                }
            } else if (childAt3.getVisibility() != 0) {
                childAt3.setVisibility(0);
            }
            int i6 = iFindFirstVisibleItemPosition + 1;
            if (iFindLastVisibleItemPosition > 0) {
                int i7 = i6;
                while (true) {
                    if (i7 > iFindLastVisibleItemPosition) {
                        break;
                    }
                    if (this.stickyPositions.contains(Integer.valueOf(i7))) {
                        i6 = i7;
                        break;
                    }
                    i7++;
                }
            }
            if (!this.stickyPositions.contains(Integer.valueOf(i6))) {
                if (templateViewHolder5.itemView.getTranslationY() < 0.0f) {
                    templateViewHolder5.itemView.setTranslationY(0.0f);
                    return;
                }
                return;
            }
            TemplateViewHolder templateViewHolder7 = (TemplateViewHolder) innerView.findViewHolderForAdapterPosition(i6);
            if (templateViewHolder7 == null || templateViewHolder7.getComponent() == null) {
                return;
            }
            int y = (int) (templateViewHolder7.itemView.getY() - templateViewHolder5.itemView.getMeasuredHeight());
            if (y <= 0) {
                templateViewHolder5.itemView.setTranslationY(y);
            } else {
                templateViewHolder5.itemView.setTranslationY(0.0f);
            }
        }
    }
}
