package com.taobao.weex.ui.view.listview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.common.WXThread;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.ui.component.list.WXListComponent;
import com.taobao.weex.ui.view.gesture.WXGesture;
import com.taobao.weex.ui.view.gesture.WXGestureObservable;
import com.taobao.weex.ui.view.listview.ExtendedLinearLayoutManager;
import com.taobao.weex.ui.view.refresh.wrapper.BounceRecyclerView;
import io.dcloud.common.ui.blur.AppEventForBlurManager;
import io.dcloud.feature.weex.extend.DCWXSlider;
import io.dcloud.weex.FlingHelper;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXRecyclerView extends RecyclerView implements WXGestureObservable {
    public static final int TYPE_GRID_LAYOUT = 2;
    public static final int TYPE_LINEAR_LAYOUT = 1;
    public static final int TYPE_STAGGERED_GRID_LAYOUT = 3;
    private JSONObject NestInfo;
    private boolean hasTouch;
    private float headerHeight;
    public boolean isNestParent;
    boolean isStartFling;
    private Float lastY;
    RecyclerView.OnScrollListener mChildScrollListener;
    private int mCurrentDy;
    private FlingHelper mFlingHelper;
    private WXGesture mGesture;
    private String mInstanceId;
    RecyclerView.OnScrollListener mParentScrollListener;
    private boolean scrollable;
    private int totalDy;
    private int velocityY;

    public WXRecyclerView(Context context) {
        super(context);
        this.scrollable = true;
        this.hasTouch = false;
        this.NestInfo = null;
        this.isNestParent = false;
        this.mCurrentDy = 0;
        this.lastY = Float.valueOf(0.0f);
        this.totalDy = 0;
        this.velocityY = 0;
        this.headerHeight = 0.0f;
        this.isStartFling = false;
    }

    static /* synthetic */ int access$212(WXRecyclerView wXRecyclerView, int i) {
        int i2 = wXRecyclerView.totalDy + i;
        wXRecyclerView.totalDy = i2;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean canParentScrollVertically(int i, boolean z) {
        if (i == 1 && i == 1) {
            try {
                if (this.NestInfo != null && this.isNestParent) {
                    WXRecyclerView childRecylerView = getChildRecylerView();
                    if (childRecylerView == null) {
                        return true;
                    }
                    Rect rect = new Rect();
                    childRecylerView.getLocalVisibleRect(rect);
                    if (childRecylerView.getHeight() - rect.bottom == 0) {
                        if (!childRecylerView.isScrollTop()) {
                            return false;
                        }
                    }
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return z;
    }

    private void childFling(int i) {
        WXRecyclerView childRecylerView = getChildRecylerView();
        if (childRecylerView != null) {
            childRecylerView.fling(0, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchChildFling() {
        int i;
        if (isScrollEnd() && (i = this.velocityY) != 0) {
            double splineFlingDistance = this.mFlingHelper.getSplineFlingDistance(i);
            double d = this.totalDy;
            if (splineFlingDistance > d) {
                childFling(this.mFlingHelper.getVelocityByDistance(splineFlingDistance - d));
            }
        }
        this.totalDy = 0;
        this.velocityY = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchParentFling() {
        int i;
        WXRecyclerView parentRecyclerView = getParentRecyclerView();
        if (parentRecyclerView == null || !isScrollTop() || (i = this.velocityY) == 0) {
            return;
        }
        double splineFlingDistance = this.mFlingHelper.getSplineFlingDistance(i);
        if (splineFlingDistance > Math.abs(this.totalDy)) {
            parentRecyclerView.fling(0, -this.mFlingHelper.getVelocityByDistance(splineFlingDistance + this.totalDy));
        }
        this.totalDy = 0;
        this.velocityY = 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private WXRecyclerView getChildRecylerView() {
        WXComponent wXComponent;
        WXListComponent listComponent;
        JSONObject jSONObject = this.NestInfo;
        if (jSONObject == null || !this.isNestParent) {
            return null;
        }
        if (jSONObject.getBooleanValue("isSwipelist")) {
            WXComponent wXComponentById = WXSDKManager.getInstance().getWXRenderManager().getWXComponentById(this.mInstanceId, this.NestInfo.getString("swipeId"));
            wXComponent = wXComponentById;
            if (wXComponentById != null) {
                DCWXSlider dCWXSliderComponent = getDCWXSliderComponent(wXComponentById);
                wXComponent = dCWXSliderComponent;
                if (dCWXSliderComponent != null) {
                    wXComponent = dCWXSliderComponent.getChild(dCWXSliderComponent.getCurrentIndex());
                }
            }
        } else {
            wXComponent = WXSDKManager.getInstance().getWXRenderManager().getWXComponent(this.mInstanceId, this.NestInfo.getString("nestChildRef"));
        }
        if (wXComponent == null || (listComponent = getListComponent(wXComponent)) == null || listComponent.getHostView() == 0) {
            return null;
        }
        return ((BounceRecyclerView) listComponent.getHostView()).getInnerView();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private WXRecyclerView getParentRecyclerView() {
        WXListComponent listComponent;
        JSONObject jSONObject = this.NestInfo;
        if (jSONObject == null || this.isNestParent) {
            return null;
        }
        WXComponent wXComponentById = WXSDKManager.getInstance().getWXRenderManager().getWXComponentById(this.mInstanceId, jSONObject.getString("listParentId"));
        if (wXComponentById == null || (listComponent = getListComponent(wXComponentById)) == null || listComponent.getHostView() == 0) {
            return null;
        }
        return ((BounceRecyclerView) listComponent.getHostView()).getInnerView();
    }

    private boolean isScrollEnd() {
        return true ^ canScrollVertically(1);
    }

    public void callBackNestParent(String str, String str2, float f) {
        WXRecyclerView parentRecyclerView = getParentRecyclerView();
        if (parentRecyclerView != null) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("isNestParent", (Object) Boolean.TRUE);
            jSONObject.put("instanceId", (Object) str2);
            jSONObject.put("nestChildRef", (Object) str);
            jSONObject.put("headerHeight", (Object) Float.valueOf(f));
            parentRecyclerView.setNestInfo(jSONObject);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this.hasTouch = true;
        if (this.NestInfo != null) {
            if (this.isNestParent) {
                if (motionEvent != null && motionEvent.getAction() == 0) {
                    this.velocityY = 0;
                    stopScroll();
                }
                if (motionEvent != null && motionEvent.getAction() != 2) {
                    this.lastY = Float.valueOf(0.0f);
                }
            } else if (motionEvent != null && motionEvent.getAction() == 0) {
                this.velocityY = 0;
            }
        }
        boolean zDispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        WXGesture wXGesture = this.mGesture;
        return wXGesture != null ? wXGesture.onTouch(this, motionEvent) | zDispatchTouchEvent : zDispatchTouchEvent;
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public boolean fling(int i, int i2) {
        if (!isAttachedToWindow()) {
            return false;
        }
        boolean zFling = super.fling(i, i2);
        if (this.NestInfo != null) {
            if (this.isNestParent) {
                if (!zFling || i2 <= 0) {
                    this.velocityY = 0;
                    return zFling;
                }
                this.isStartFling = true;
                this.velocityY = i2;
                return zFling;
            }
            if (zFling && i2 < 0) {
                this.isStartFling = true;
                this.velocityY = i2;
                return zFling;
            }
            this.velocityY = 0;
        }
        return zFling;
    }

    public int getCurrentDy() {
        return this.mCurrentDy;
    }

    public DCWXSlider getDCWXSliderComponent(WXComponent wXComponent) {
        if (wXComponent instanceof DCWXSlider) {
            return (DCWXSlider) wXComponent;
        }
        if (!(wXComponent instanceof WXVContainer)) {
            return null;
        }
        WXVContainer wXVContainer = (WXVContainer) wXComponent;
        if (wXVContainer.getChildCount() <= 0) {
            return null;
        }
        for (int i = 0; i < wXVContainer.getChildCount(); i++) {
            DCWXSlider dCWXSliderComponent = getDCWXSliderComponent(wXVContainer.getChild(i));
            if (dCWXSliderComponent != null) {
                return dCWXSliderComponent;
            }
        }
        return null;
    }

    @Override // com.taobao.weex.ui.view.gesture.WXGestureObservable
    public WXGesture getGestureListener() {
        return this.mGesture;
    }

    public WXListComponent getListComponent(WXComponent wXComponent) {
        if (wXComponent instanceof WXListComponent) {
            return (WXListComponent) wXComponent;
        }
        if (!(wXComponent instanceof WXVContainer)) {
            return null;
        }
        WXVContainer wXVContainer = (WXVContainer) wXComponent;
        if (wXVContainer.getChildCount() <= 0) {
            return null;
        }
        for (int i = 0; i < wXVContainer.getChildCount(); i++) {
            WXListComponent listComponent = getListComponent(wXVContainer.getChild(i));
            if (listComponent != null) {
                return listComponent;
            }
        }
        return null;
    }

    public void initView(Context context, int i, int i2) {
        initView(context, i, 1, 32.0f, i2);
    }

    public boolean isNestScroll() {
        return this.NestInfo != null;
    }

    public boolean isScrollTop() {
        return !canScrollVertically(-1);
    }

    public boolean isScrollable() {
        return this.scrollable;
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        if (!this.isNestParent || this.NestInfo == null) {
            return super.onNestedFling(view, f, f2, z);
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onNestedPreFling(View view, float f, float f2) {
        if (!this.isNestParent || this.NestInfo == null) {
            return super.onNestedPreFling(view, f, f2);
        }
        WXRecyclerView childRecylerView = getChildRecylerView();
        boolean z = f2 > 0.0f && !isScrollEnd();
        boolean z2 = f2 < 0.0f && childRecylerView != null && childRecylerView.isScrollTop();
        if (!z && !z2) {
            return false;
        }
        fling(0, (int) f2);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        if (!this.isNestParent || this.NestInfo == null) {
            return;
        }
        WXRecyclerView childRecylerView = getChildRecylerView();
        boolean z = i2 > 0 && !isScrollEnd();
        boolean z2 = i2 < 0 && childRecylerView != null && childRecylerView.isScrollTop();
        if (z || z2) {
            scrollBy(0, i2);
            iArr[1] = i2;
        }
    }

    @Override // android.view.View
    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        AppEventForBlurManager.onScrollChanged(i, i2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onStartNestedScroll(View view, View view2, int i) {
        if (!this.isNestParent || this.NestInfo == null || view2 == null || !(view2 instanceof WXRecyclerView) || ((WXRecyclerView) view2).isNestParent) {
            return super.onStartNestedScroll(view, view2, i);
        }
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        View view;
        WXRecyclerView childRecylerView;
        int iFloatValue;
        if (!this.scrollable) {
            return true;
        }
        if (this.NestInfo != null && this.isNestParent) {
            if (this.lastY.floatValue() == 0.0f) {
                this.lastY = Float.valueOf(motionEvent.getY());
            }
            if (isScrollEnd() && (childRecylerView = getChildRecylerView()) != null && (iFloatValue = (int) (this.lastY.floatValue() - motionEvent.getY())) != 0) {
                childRecylerView.scrollBy(0, iFloatValue);
            }
            this.lastY = Float.valueOf(motionEvent.getY());
        }
        if (motionEvent.getAction() == 1 && (view = (View) getParent().getParent()) != null && view.hasOnClickListeners() && (view instanceof BounceRecyclerView) && pointInView(motionEvent.getX(), motionEvent.getY(), 0.0f) && getScrollState() == 0) {
            view.performClick();
        }
        return super.onTouchEvent(motionEvent);
    }

    public boolean pointInView(float f, float f2, float f3) {
        float f4 = -f3;
        return f >= f4 && f2 >= f4 && f < ((float) (getRight() - getLeft())) + f3 && f2 < ((float) (getBottom() - getTop())) + f3;
    }

    @Override // com.taobao.weex.ui.view.gesture.WXGestureObservable
    public void registerGestureListener(WXGesture wXGesture) {
        this.mGesture = wXGesture;
    }

    public void scrollTo(boolean z, int i, final int i2, final int i3) {
        try {
            RecyclerView.LayoutManager layoutManager = getLayoutManager();
            stopScroll();
            if (!z) {
                if (layoutManager instanceof LinearLayoutManager) {
                    ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(i, -i2);
                    return;
                } else {
                    if (layoutManager instanceof StaggeredGridLayoutManager) {
                        ((StaggeredGridLayoutManager) layoutManager).scrollToPositionWithOffset(i, -i2);
                        return;
                    }
                    return;
                }
            }
            if (i3 == 1) {
                if (layoutManager instanceof LinearLayoutManager) {
                    int iFindFirstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    int iFindLastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    if (i >= iFindFirstVisibleItemPosition && i <= iFindLastVisibleItemPosition) {
                        smoothScrollBy(0, getChildAt(i - iFindFirstVisibleItemPosition).getTop() + i2);
                        return;
                    }
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    int[] iArrFindFirstVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(null);
                    int[] iArrFindLastCompletelyVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastCompletelyVisibleItemPositions(null);
                    int i4 = iArrFindFirstVisibleItemPositions[0];
                    if (i >= i4 && i <= iArrFindLastCompletelyVisibleItemPositions[0]) {
                        smoothScrollBy(0, getChildAt(i - i4).getTop() + i2);
                        return;
                    }
                }
            }
            smoothScrollToPosition(i);
            if (i2 != 0) {
                setOnSmoothScrollEndListener(new ExtendedLinearLayoutManager.OnSmoothScrollEndListener() { // from class: com.taobao.weex.ui.view.listview.WXRecyclerView.6
                    @Override // com.taobao.weex.ui.view.listview.ExtendedLinearLayoutManager.OnSmoothScrollEndListener
                    public void onStop() {
                        WXRecyclerView.this.post(WXThread.secure(new Runnable() { // from class: com.taobao.weex.ui.view.listview.WXRecyclerView.6.1
                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass6 anonymousClass6 = AnonymousClass6.this;
                                if (i3 == 1) {
                                    WXRecyclerView.this.smoothScrollBy(0, i2);
                                } else {
                                    WXRecyclerView.this.smoothScrollBy(i2, 0);
                                }
                            }
                        }));
                    }
                });
            }
        } catch (Exception unused) {
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void scrollToPosition(final int i) {
        if (!this.isNestParent || this.NestInfo == null) {
            super.scrollToPosition(i);
            return;
        }
        WXRecyclerView childRecylerView = getChildRecylerView();
        if (childRecylerView != null) {
            childRecylerView.scrollToPosition(i);
        }
        postDelayed(new Runnable() { // from class: com.taobao.weex.ui.view.listview.WXRecyclerView.9
            @Override // java.lang.Runnable
            public void run() {
                WXRecyclerView.super.scrollToPosition(i);
            }
        }, 50L);
    }

    public void setNestInfo(JSONObject jSONObject) {
        this.NestInfo = jSONObject;
        if (jSONObject != null) {
            this.isNestParent = jSONObject.getBooleanValue("isNestParent");
            this.mInstanceId = jSONObject.getString("instanceId");
            if (this.mFlingHelper == null) {
                this.mFlingHelper = new FlingHelper(getContext());
            }
            if (!this.isNestParent) {
                if (this.mChildScrollListener == null) {
                    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() { // from class: com.taobao.weex.ui.view.listview.WXRecyclerView.2
                        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                            if (i == 0) {
                                WXRecyclerView.this.mCurrentDy = 0;
                                WXRecyclerView.this.dispatchParentFling();
                            }
                            super.onScrollStateChanged(recyclerView, i);
                        }

                        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                            super.onScrolled(recyclerView, i, i2);
                            WXRecyclerView.this.mCurrentDy = i2;
                            WXRecyclerView wXRecyclerView = WXRecyclerView.this;
                            if (wXRecyclerView.isStartFling) {
                                wXRecyclerView.totalDy = 0;
                                WXRecyclerView.this.isStartFling = false;
                            }
                            WXRecyclerView.access$212(WXRecyclerView.this, i2);
                        }
                    };
                    this.mChildScrollListener = onScrollListener;
                    addOnScrollListener(onScrollListener);
                    return;
                }
                return;
            }
            setDescendantFocusability(131072);
            if (this.mParentScrollListener == null) {
                this.headerHeight = jSONObject.getFloat("headerHeight").floatValue();
                RecyclerView.OnScrollListener onScrollListener2 = new RecyclerView.OnScrollListener() { // from class: com.taobao.weex.ui.view.listview.WXRecyclerView.1
                    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                    public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                        super.onScrollStateChanged(recyclerView, i);
                        if (i == 0) {
                            WXRecyclerView.this.mCurrentDy = 0;
                            WXRecyclerView.this.dispatchChildFling();
                        }
                    }

                    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                    public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                        super.onScrolled(recyclerView, i, i2);
                        WXRecyclerView.this.mCurrentDy = i2;
                        WXRecyclerView wXRecyclerView = WXRecyclerView.this;
                        if (wXRecyclerView.isStartFling) {
                            wXRecyclerView.totalDy = 0;
                            WXRecyclerView.this.isStartFling = false;
                        }
                        WXRecyclerView.access$212(WXRecyclerView.this, i2);
                    }
                };
                this.mParentScrollListener = onScrollListener2;
                addOnScrollListener(onScrollListener2);
            }
        }
    }

    public void setOnSmoothScrollEndListener(final ExtendedLinearLayoutManager.OnSmoothScrollEndListener onSmoothScrollEndListener) {
        addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.taobao.weex.ui.view.listview.WXRecyclerView.8
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                if (i == 0) {
                    recyclerView.removeOnScrollListener(this);
                    ExtendedLinearLayoutManager.OnSmoothScrollEndListener onSmoothScrollEndListener2 = onSmoothScrollEndListener;
                    if (onSmoothScrollEndListener2 != null) {
                        onSmoothScrollEndListener2.onStop();
                    }
                }
            }
        });
    }

    public void setScrollable(boolean z) {
        this.scrollable = z;
    }

    public void initView(Context context, int i, int i2, float f, final int i3) {
        if (i == 2) {
            setLayoutManager(new GridLayoutManager(context, i2, i3, false) { // from class: com.taobao.weex.ui.view.listview.WXRecyclerView.3
                @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
                public boolean canScrollVertically() {
                    try {
                        return WXRecyclerView.this.canParentScrollVertically(i3, super.canScrollVertically());
                    } catch (Exception unused) {
                        return true;
                    }
                }

                @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
                public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                    try {
                        super.onLayoutChildren(recycler, state);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else if (i == 3) {
            setLayoutManager(new ExtendedStaggeredGridLayoutManager(i2, i3) { // from class: com.taobao.weex.ui.view.listview.WXRecyclerView.4
                @Override // androidx.recyclerview.widget.StaggeredGridLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
                public boolean canScrollVertically() {
                    try {
                        return WXRecyclerView.this.canParentScrollVertically(i3, super.canScrollVertically());
                    } catch (Exception unused) {
                        return true;
                    }
                }

                @Override // androidx.recyclerview.widget.StaggeredGridLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
                public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                    try {
                        super.onLayoutChildren(recycler, state);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else if (i == 1) {
            setLayoutManager(new LinearLayoutManager(context, i3, false) { // from class: com.taobao.weex.ui.view.listview.WXRecyclerView.5
                @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
                public boolean canScrollVertically() {
                    try {
                        return WXRecyclerView.this.canParentScrollVertically(i3, super.canScrollVertically());
                    } catch (Exception unused) {
                        return true;
                    }
                }

                @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
                public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                    try {
                        super.onLayoutChildren(recycler, state);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public WXRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.scrollable = true;
        this.hasTouch = false;
        this.NestInfo = null;
        this.isNestParent = false;
        this.mCurrentDy = 0;
        this.lastY = Float.valueOf(0.0f);
        this.totalDy = 0;
        this.velocityY = 0;
        this.headerHeight = 0.0f;
        this.isStartFling = false;
    }

    public void scrollTo(final int i, final int i2, final int i3) {
        postDelayed(WXThread.secure(new Runnable() { // from class: com.taobao.weex.ui.view.listview.WXRecyclerView.7
            @Override // java.lang.Runnable
            public void run() {
                RecyclerView.LayoutManager layoutManager = WXRecyclerView.this.getLayoutManager();
                int i4 = i3 == 1 ? i2 : i;
                if (layoutManager instanceof LinearLayoutManager) {
                    ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(0, -i4);
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    ((StaggeredGridLayoutManager) layoutManager).scrollToPositionWithOffset(0, -i4);
                }
            }
        }), 100L);
    }
}
