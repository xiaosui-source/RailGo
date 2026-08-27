package com.alibaba.android.bindingx.plugin.weex;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.android.bindingx.core.BindingXCore;
import com.alibaba.android.bindingx.core.LogProxy;
import com.alibaba.android.bindingx.core.PlatformManager;
import com.alibaba.android.bindingx.core.internal.AbstractScrollEventHandler;
import com.alibaba.android.bindingx.core.internal.BindingXConstants;
import com.alibaba.android.bindingx.core.internal.ExpressionPair;
import com.google.android.material.appbar.AppBarLayout;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXScroller;
import com.taobao.weex.ui.component.list.WXListComponent;
import com.taobao.weex.ui.view.WXHorizontalScrollView;
import com.taobao.weex.ui.view.WXScrollView;
import com.taobao.weex.ui.view.listview.WXRecyclerView;
import com.taobao.weex.ui.view.refresh.core.WXSwipeLayout;
import com.taobao.weex.ui.view.refresh.wrapper.BounceRecyclerView;
import com.taobao.weex.ui.view.refresh.wrapper.BounceScrollerView;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class BindingXScrollHandler extends AbstractScrollEventHandler {
    private static HashMap<String, ContentOffsetHolder> sOffsetHolderMap = new HashMap<>();
    private WXHorizontalScrollView.ScrollViewListener mHorizontalViewScrollListener;
    private RecyclerView.OnScrollListener mListOnScrollListener;
    private AppBarLayout.OnOffsetChangedListener mOnOffsetChangedListener;
    private WXSwipeLayout.OnRefreshOffsetChangedListener mRefreshOffsetChangedListener;
    private String mSourceRef;
    private WXScrollView.WXScrollViewListener mWxScrollViewListener;

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSameDirection(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            return i < 0 && i2 < 0;
        }
        return true;
    }

    @Override // com.alibaba.android.bindingx.core.IEventHandler
    public void onActivityPause() {
    }

    @Override // com.alibaba.android.bindingx.core.IEventHandler
    public void onActivityResume() {
    }

    @Override // com.alibaba.android.bindingx.core.IEventHandler
    public void onStart(String str, String str2) {
    }

    public BindingXScrollHandler(Context context, PlatformManager platformManager, Object... objArr) {
        super(context, platformManager, objArr);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.alibaba.android.bindingx.core.IEventHandler
    public boolean onCreate(String str, String str2) {
        WXSwipeLayout swipeLayout;
        WXComponent wXComponentFindComponentByRef = WXModuleUtils.findComponentByRef(TextUtils.isEmpty(this.mAnchorInstanceId) ? this.mInstanceId : this.mAnchorInstanceId, str);
        if (wXComponentFindComponentByRef == null) {
            LogProxy.e("[ExpressionScrollHandler]source component not found.");
            return false;
        }
        this.mSourceRef = str;
        if (wXComponentFindComponentByRef instanceof WXScroller) {
            WXScroller wXScroller = (WXScroller) wXComponentFindComponentByRef;
            ViewGroup viewGroup = (ViewGroup) wXScroller.getHostView();
            if (viewGroup != null && (viewGroup instanceof BounceScrollerView) && (swipeLayout = ((BounceScrollerView) viewGroup).getSwipeLayout()) != null) {
                InnerSwipeOffsetListener innerSwipeOffsetListener = new InnerSwipeOffsetListener();
                this.mRefreshOffsetChangedListener = innerSwipeOffsetListener;
                swipeLayout.addOnRefreshOffsetChangedListener(innerSwipeOffsetListener);
            }
            ViewGroup innerView = wXScroller.getInnerView();
            if (innerView != null && (innerView instanceof WXScrollView)) {
                InnerScrollViewListener innerScrollViewListener = new InnerScrollViewListener();
                this.mWxScrollViewListener = innerScrollViewListener;
                ((WXScrollView) innerView).addScrollViewListener(innerScrollViewListener);
                return true;
            }
            if (innerView != null && (innerView instanceof WXHorizontalScrollView)) {
                InnerScrollViewListener innerScrollViewListener2 = new InnerScrollViewListener();
                this.mHorizontalViewScrollListener = innerScrollViewListener2;
                ((WXHorizontalScrollView) innerView).addScrollViewListener(innerScrollViewListener2);
                return true;
            }
        } else if (wXComponentFindComponentByRef instanceof WXListComponent) {
            WXListComponent wXListComponent = (WXListComponent) wXComponentFindComponentByRef;
            BounceRecyclerView bounceRecyclerView = (BounceRecyclerView) wXListComponent.getHostView();
            if (bounceRecyclerView != null) {
                WXSwipeLayout swipeLayout2 = bounceRecyclerView.getSwipeLayout();
                if (swipeLayout2 != null) {
                    InnerSwipeOffsetListener innerSwipeOffsetListener2 = new InnerSwipeOffsetListener();
                    this.mRefreshOffsetChangedListener = innerSwipeOffsetListener2;
                    swipeLayout2.addOnRefreshOffsetChangedListener(innerSwipeOffsetListener2);
                }
                WXRecyclerView wXRecyclerView = (WXRecyclerView) bounceRecyclerView.getInnerView();
                boolean z = wXListComponent.getOrientation() == 1;
                if (wXRecyclerView != null) {
                    HashMap<String, ContentOffsetHolder> map = sOffsetHolderMap;
                    if (map != null && map.get(str) == null) {
                        sOffsetHolderMap.put(str, new ContentOffsetHolder(0, 0));
                    }
                    InnerListScrollListener innerListScrollListener = new InnerListScrollListener(z, new WeakReference(wXListComponent));
                    this.mListOnScrollListener = innerListScrollListener;
                    wXRecyclerView.addOnScrollListener(innerListScrollListener);
                    return true;
                }
            }
        } else if (wXComponentFindComponentByRef.getHostView() != null && (wXComponentFindComponentByRef.getHostView() instanceof AppBarLayout)) {
            AppBarLayout hostView = wXComponentFindComponentByRef.getHostView();
            InnerAppBarOffsetChangedListener innerAppBarOffsetChangedListener = new InnerAppBarOffsetChangedListener();
            this.mOnOffsetChangedListener = innerAppBarOffsetChangedListener;
            hostView.addOnOffsetChangedListener(innerAppBarOffsetChangedListener);
            return true;
        }
        return false;
    }

    @Override // com.alibaba.android.bindingx.core.internal.AbstractEventHandler, com.alibaba.android.bindingx.core.IEventHandler
    public void onBindExpression(String str, Map<String, Object> map, ExpressionPair expressionPair, List<Map<String, Object>> list, BindingXCore.JavaScriptCallback javaScriptCallback) {
        super.onBindExpression(str, map, expressionPair, list, javaScriptCallback);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.alibaba.android.bindingx.core.internal.AbstractScrollEventHandler, com.alibaba.android.bindingx.core.IEventHandler
    public boolean onDisable(String str, String str2) {
        BounceRecyclerView bounceRecyclerView;
        RecyclerView.OnScrollListener onScrollListener;
        WXHorizontalScrollView.ScrollViewListener scrollViewListener;
        WXScrollView.WXScrollViewListener wXScrollViewListener;
        WXSwipeLayout swipeLayout;
        WXSwipeLayout.OnRefreshOffsetChangedListener onRefreshOffsetChangedListener;
        ContentOffsetHolder contentOffsetHolder;
        super.onDisable(str, str2);
        if (sOffsetHolderMap != null && !TextUtils.isEmpty(this.mSourceRef) && (contentOffsetHolder = sOffsetHolderMap.get(this.mSourceRef)) != null) {
            contentOffsetHolder.x = this.mContentOffsetX;
            contentOffsetHolder.y = this.mContentOffsetY;
        }
        WXComponent wXComponentFindComponentByRef = WXModuleUtils.findComponentByRef(TextUtils.isEmpty(this.mAnchorInstanceId) ? this.mInstanceId : this.mAnchorInstanceId, str);
        if (wXComponentFindComponentByRef == null) {
            LogProxy.e("[ExpressionScrollHandler]source component not found.");
            return false;
        }
        if (wXComponentFindComponentByRef instanceof WXScroller) {
            WXScroller wXScroller = (WXScroller) wXComponentFindComponentByRef;
            ViewGroup viewGroup = (ViewGroup) wXScroller.getHostView();
            if (viewGroup != null && (viewGroup instanceof BounceScrollerView) && (swipeLayout = ((BounceScrollerView) viewGroup).getSwipeLayout()) != null && (onRefreshOffsetChangedListener = this.mRefreshOffsetChangedListener) != null) {
                swipeLayout.removeOnRefreshOffsetChangedListener(onRefreshOffsetChangedListener);
            }
            ViewGroup innerView = wXScroller.getInnerView();
            if (innerView != null && (innerView instanceof WXScrollView) && (wXScrollViewListener = this.mWxScrollViewListener) != null) {
                ((WXScrollView) innerView).removeScrollViewListener(wXScrollViewListener);
                return true;
            }
            if (innerView != null && (innerView instanceof WXHorizontalScrollView) && (scrollViewListener = this.mHorizontalViewScrollListener) != null) {
                ((WXHorizontalScrollView) innerView).removeScrollViewListener(scrollViewListener);
                return true;
            }
        } else if ((wXComponentFindComponentByRef instanceof WXListComponent) && (bounceRecyclerView = (BounceRecyclerView) ((WXListComponent) wXComponentFindComponentByRef).getHostView()) != null) {
            if (bounceRecyclerView.getSwipeLayout() != null && this.mRefreshOffsetChangedListener != null) {
                bounceRecyclerView.getSwipeLayout().removeOnRefreshOffsetChangedListener(this.mRefreshOffsetChangedListener);
            }
            WXRecyclerView wXRecyclerView = (WXRecyclerView) bounceRecyclerView.getInnerView();
            if (wXRecyclerView != null && (onScrollListener = this.mListOnScrollListener) != null) {
                wXRecyclerView.removeOnScrollListener(onScrollListener);
                return true;
            }
        }
        return false;
    }

    @Override // com.alibaba.android.bindingx.core.internal.AbstractScrollEventHandler, com.alibaba.android.bindingx.core.internal.AbstractEventHandler, com.alibaba.android.bindingx.core.IEventHandler
    public void onDestroy() {
        super.onDestroy();
        this.mListOnScrollListener = null;
        this.mWxScrollViewListener = null;
        this.mOnOffsetChangedListener = null;
        HashMap<String, ContentOffsetHolder> map = sOffsetHolderMap;
        if (map != null) {
            map.clear();
        }
    }

    private class InnerAppBarOffsetChangedListener implements AppBarLayout.OnOffsetChangedListener {
        private int mContentOffsetY;
        private int mLastDy;
        private int mTy;

        private InnerAppBarOffsetChangedListener() {
            this.mContentOffsetY = 0;
            this.mTy = 0;
            this.mLastDy = 0;
        }

        public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            boolean z;
            int i2 = -i;
            final int i3 = i2 - this.mContentOffsetY;
            this.mContentOffsetY = i2;
            if (i3 == 0) {
                return;
            }
            if (BindingXScrollHandler.this.isSameDirection(i3, this.mLastDy)) {
                z = false;
            } else {
                this.mTy = this.mContentOffsetY;
                z = true;
            }
            int i4 = this.mContentOffsetY;
            final int i5 = i4 - this.mTy;
            this.mLastDy = i3;
            if (z) {
                BindingXScrollHandler.super.fireEventByState(BindingXConstants.STATE_TURNING, 0.0d, i4, 0.0d, i3, 0.0d, i5, new Object[0]);
            }
            WXBridgeManager.getInstance().post(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.BindingXScrollHandler.InnerAppBarOffsetChangedListener.1
                @Override // java.lang.Runnable
                public void run() {
                    BindingXScrollHandler.super.handleScrollEvent(0, InnerAppBarOffsetChangedListener.this.mContentOffsetY, 0, i3, 0, i5);
                }
            }, BindingXScrollHandler.this.mInstanceId);
        }
    }

    private class InnerScrollViewListener implements WXScrollView.WXScrollViewListener, WXHorizontalScrollView.ScrollViewListener {
        private int mContentOffsetX;
        private int mContentOffsetY;
        private int mLastDx;
        private int mLastDy;
        private int mTx;
        private int mTy;

        @Override // com.taobao.weex.ui.view.WXScrollView.WXScrollViewListener
        public void onScrollChanged(WXScrollView wXScrollView, int i, int i2, int i3, int i4) {
        }

        @Override // com.taobao.weex.ui.view.WXScrollView.WXScrollViewListener
        public void onScrollStopped(WXScrollView wXScrollView, int i, int i2) {
        }

        @Override // com.taobao.weex.ui.view.WXScrollView.WXScrollViewListener
        public void onScrollToBottom(WXScrollView wXScrollView, int i, int i2) {
        }

        private InnerScrollViewListener() {
            this.mContentOffsetX = 0;
            this.mContentOffsetY = 0;
            this.mTx = 0;
            this.mTy = 0;
            this.mLastDx = 0;
            this.mLastDy = 0;
        }

        @Override // com.taobao.weex.ui.view.WXScrollView.WXScrollViewListener
        public void onScroll(WXScrollView wXScrollView, int i, int i2) {
            onScrollInternal(i, i2);
        }

        @Override // com.taobao.weex.ui.view.WXHorizontalScrollView.ScrollViewListener
        public void onScrollChanged(WXHorizontalScrollView wXHorizontalScrollView, int i, int i2, int i3, int i4) {
            onScrollInternal(i, i2);
        }

        private void onScrollInternal(int i, int i2) {
            boolean z;
            int i3;
            int i4 = i - this.mContentOffsetX;
            final int i5 = i2 - this.mContentOffsetY;
            this.mContentOffsetX = i;
            this.mContentOffsetY = i2;
            if (i4 == 0 && i5 == 0) {
                return;
            }
            if (BindingXScrollHandler.this.isSameDirection(i5, this.mLastDy)) {
                z = false;
            } else {
                this.mTy = this.mContentOffsetY;
                z = true;
            }
            int i6 = this.mContentOffsetX;
            final int i7 = i6 - this.mTx;
            int i8 = this.mContentOffsetY;
            final int i9 = i8 - this.mTy;
            this.mLastDx = i4;
            this.mLastDy = i5;
            if (z) {
                i3 = i4;
                BindingXScrollHandler.super.fireEventByState(BindingXConstants.STATE_TURNING, i6, i8, i4, i5, i7, i9, new Object[0]);
            } else {
                i3 = i4;
            }
            final int i10 = i3;
            WXBridgeManager.getInstance().post(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.BindingXScrollHandler.InnerScrollViewListener.1
                @Override // java.lang.Runnable
                public void run() {
                    BindingXScrollHandler.super.handleScrollEvent(InnerScrollViewListener.this.mContentOffsetX, InnerScrollViewListener.this.mContentOffsetY, i10, i5, i7, i9);
                }
            }, BindingXScrollHandler.this.mInstanceId);
        }
    }

    private class InnerSwipeOffsetListener implements WXSwipeLayout.OnRefreshOffsetChangedListener {
        private int mContentOffsetY;
        private int mLastDy;
        private int mTy;

        private InnerSwipeOffsetListener() {
            this.mContentOffsetY = 0;
            this.mTy = 0;
            this.mLastDy = 0;
        }

        @Override // com.taobao.weex.ui.view.refresh.core.WXSwipeLayout.OnRefreshOffsetChangedListener
        public void onOffsetChanged(int i) {
            boolean z;
            int i2 = -i;
            final int i3 = i2 - this.mContentOffsetY;
            this.mContentOffsetY = i2;
            if (i3 == 0) {
                return;
            }
            if (BindingXScrollHandler.this.isSameDirection(i3, this.mLastDy)) {
                z = false;
            } else {
                this.mTy = this.mContentOffsetY;
                z = true;
            }
            final int i4 = this.mContentOffsetY - this.mTy;
            this.mLastDy = i3;
            if (z) {
                BindingXScrollHandler.super.fireEventByState(BindingXConstants.STATE_TURNING, r5.mContentOffsetX, this.mContentOffsetY, 0.0d, i3, 0.0d, i4, new Object[0]);
            }
            WXBridgeManager.getInstance().post(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.BindingXScrollHandler.InnerSwipeOffsetListener.1
                @Override // java.lang.Runnable
                public void run() {
                    BindingXScrollHandler.super.handleScrollEvent(BindingXScrollHandler.this.mContentOffsetX, InnerSwipeOffsetListener.this.mContentOffsetY, 0, i3, 0, i4);
                }
            }, BindingXScrollHandler.this.mInstanceId);
        }
    }

    private class InnerListScrollListener extends RecyclerView.OnScrollListener {
        private boolean isVertical;
        private WeakReference<WXListComponent> mComponentRef;
        private int mContentOffsetX;
        private int mContentOffsetY;
        private int mTx = 0;
        private int mTy = 0;
        private int mLastDx = 0;
        private int mLastDy = 0;

        InnerListScrollListener(boolean z, WeakReference<WXListComponent> weakReference) {
            ContentOffsetHolder contentOffsetHolder;
            this.mContentOffsetX = 0;
            this.mContentOffsetY = 0;
            this.isVertical = z;
            this.mComponentRef = weakReference;
            if (TextUtils.isEmpty(BindingXScrollHandler.this.mSourceRef) || BindingXScrollHandler.sOffsetHolderMap == null || (contentOffsetHolder = (ContentOffsetHolder) BindingXScrollHandler.sOffsetHolderMap.get(BindingXScrollHandler.this.mSourceRef)) == null) {
                return;
            }
            this.mContentOffsetX = contentOffsetHolder.x;
            this.mContentOffsetY = contentOffsetHolder.y;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, final int i, final int i2) {
            boolean z;
            WeakReference<WXListComponent> weakReference;
            if (ViewCompat.isInLayout(recyclerView) && (weakReference = this.mComponentRef) != null && weakReference.get() != null) {
                this.mContentOffsetY = Math.abs(this.mComponentRef.get().calcContentOffset(recyclerView));
            } else {
                this.mContentOffsetY += i2;
            }
            this.mContentOffsetX += i;
            boolean z2 = true;
            if (BindingXScrollHandler.this.isSameDirection(i, this.mLastDx) || this.isVertical) {
                z = false;
            } else {
                this.mTx = this.mContentOffsetX;
                z = true;
            }
            if (BindingXScrollHandler.this.isSameDirection(i2, this.mLastDy) || !this.isVertical) {
                z2 = z;
            } else {
                this.mTy = this.mContentOffsetY;
            }
            int i3 = this.mContentOffsetX;
            final int i4 = i3 - this.mTx;
            int i5 = this.mContentOffsetY;
            final int i6 = i5 - this.mTy;
            this.mLastDx = i;
            this.mLastDy = i2;
            if (z2) {
                BindingXScrollHandler.this.fireEventByState(BindingXConstants.STATE_TURNING, i3, i5, i, i2, i4, i6, new Object[0]);
            }
            WXBridgeManager.getInstance().post(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.BindingXScrollHandler.InnerListScrollListener.1
                @Override // java.lang.Runnable
                public void run() {
                    BindingXScrollHandler.super.handleScrollEvent(InnerListScrollListener.this.mContentOffsetX, InnerListScrollListener.this.mContentOffsetY, i, i2, i4, i6);
                }
            }, BindingXScrollHandler.this.mInstanceId);
        }
    }

    private static class ContentOffsetHolder {
        int x;
        int y;

        ContentOffsetHolder(int i, int i2) {
            this.x = i;
            this.y = i2;
        }
    }
}
