package com.taobao.weex.ui.view.listview.adapter;

import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.taobao.weex.utils.WXLogUtils;
import java.lang.ref.WeakReference;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXRecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {
    protected LAYOUT_MANAGER_TYPE layoutManagerType;
    private WeakReference<IOnLoadMoreListener> listener;
    private int[] mFirstPositions;
    private int mFirstVisibleItemPosition;
    private int[] mLastPositions;
    private int mLastVisibleItemPosition;
    private int mCurrentScrollState = 0;
    private int mDy = 0;
    private boolean isLayoutRTL = false;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    public WXRecyclerViewOnScrollListener(IOnLoadMoreListener iOnLoadMoreListener) {
        this.listener = new WeakReference<>(iOnLoadMoreListener);
    }

    private int findMax(int[] iArr) {
        int i = iArr[0];
        for (int i2 : iArr) {
            if (i2 > i) {
                i = i2;
            }
        }
        return i;
    }

    private int findMin(int[] iArr) {
        int i = iArr[0];
        for (int i2 : iArr) {
            if (i2 < i) {
                i = i2;
            }
        }
        return i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
    public void onScrollStateChanged(RecyclerView recyclerView, int i) {
        WeakReference<IOnLoadMoreListener> weakReference;
        WeakReference<IOnLoadMoreListener> weakReference2;
        super.onScrollStateChanged(recyclerView, i);
        this.mCurrentScrollState = i;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int childCount = layoutManager.getChildCount();
        int itemCount = layoutManager.getItemCount();
        if (childCount != 0) {
            int height = (((itemCount - this.mLastVisibleItemPosition) - 1) * recyclerView.getHeight()) / childCount;
            if (childCount <= 0 || this.mCurrentScrollState != 0) {
                return;
            }
            if (!recyclerView.isHorizontalScrollBarEnabled()) {
                View childAt = layoutManager.getChildAt(this.mLastVisibleItemPosition);
                if (childAt != null) {
                    int decoratedBottom = layoutManager.getDecoratedBottom(childAt);
                    int height2 = recyclerView.getHeight();
                    if (decoratedBottom + 50 < height2 || decoratedBottom > height2) {
                        return;
                    }
                } else {
                    int i2 = this.mDy;
                    if (i2 == 0 || i2 < 0) {
                        return;
                    }
                }
                WeakReference<IOnLoadMoreListener> weakReference3 = this.listener;
                if (weakReference3 != null && weakReference3.get() != null) {
                    this.listener.get().onLoadMore(height);
                }
                this.mDy = 0;
                return;
            }
            View childAt2 = recyclerView.getLayoutManager().getChildAt(childCount - 1);
            int position = recyclerView.getLayoutManager().getPosition(childAt2);
            if (childAt2 == null || position != itemCount - 1) {
                return;
            }
            if (this.isLayoutRTL) {
                if (layoutManager.getDecoratedLeft(childAt2) < -50 || (weakReference2 = this.listener) == null || weakReference2.get() == null) {
                    return;
                }
                this.listener.get().onLoadMore(height);
                return;
            }
            if (layoutManager.getDecoratedRight(childAt2) > recyclerView.getWidth() || (weakReference = this.listener) == null || weakReference.get() == null) {
                return;
            }
            this.listener.get().onLoadMore(height);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
    public void onScrolled(RecyclerView recyclerView, int i, int i2) {
        IOnLoadMoreListener iOnLoadMoreListener;
        super.onScrolled(recyclerView, i, i2);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        WeakReference<IOnLoadMoreListener> weakReference = this.listener;
        if (weakReference == null || (iOnLoadMoreListener = weakReference.get()) == null) {
            return;
        }
        iOnLoadMoreListener.onBeforeScroll(i, i2);
        this.mDy = i2;
        if (layoutManager instanceof LinearLayoutManager) {
            this.layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            this.mLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            iOnLoadMoreListener.notifyAppearStateChange(linearLayoutManager.findFirstVisibleItemPosition(), this.mLastVisibleItemPosition, i, i2);
            return;
        }
        if (layoutManager instanceof GridLayoutManager) {
            this.layoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            this.mLastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();
            iOnLoadMoreListener.notifyAppearStateChange(gridLayoutManager.findFirstVisibleItemPosition(), this.mLastVisibleItemPosition, i, i2);
            return;
        }
        if (!(layoutManager instanceof StaggeredGridLayoutManager)) {
            throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
        }
        this.layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        int spanCount = staggeredGridLayoutManager.getSpanCount();
        int[] iArr = this.mLastPositions;
        if (iArr == null || spanCount != iArr.length) {
            this.mLastPositions = new int[spanCount];
        }
        int[] iArr2 = this.mFirstPositions;
        if (iArr2 == null || spanCount != iArr2.length) {
            this.mFirstPositions = new int[spanCount];
        }
        try {
            staggeredGridLayoutManager.findFirstVisibleItemPositions(this.mFirstPositions);
            this.mFirstVisibleItemPosition = findMin(this.mFirstPositions);
            staggeredGridLayoutManager.findLastVisibleItemPositions(this.mLastPositions);
            int iFindMax = findMax(this.mLastPositions);
            this.mLastVisibleItemPosition = iFindMax;
            iOnLoadMoreListener.notifyAppearStateChange(this.mFirstVisibleItemPosition, iFindMax, i, i2);
        } catch (Exception e) {
            e.printStackTrace();
            WXLogUtils.e(e.toString());
        }
    }

    public void setLayoutRTL(boolean z) {
        this.isLayoutRTL = z;
    }
}
