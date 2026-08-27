package com.taobao.weex.ui.component;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.Component;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.Constants;
import com.taobao.weex.common.ICheckBindingScroller;
import com.taobao.weex.common.OnWXScrollListener;
import com.taobao.weex.common.WXThread;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.ui.ComponentCreator;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.helper.ScrollStartEndHelper;
import com.taobao.weex.ui.component.helper.WXStickyHelper;
import com.taobao.weex.ui.view.IWXScroller;
import com.taobao.weex.ui.view.WXBaseRefreshLayout;
import com.taobao.weex.ui.view.WXHorizontalScrollView;
import com.taobao.weex.ui.view.WXScrollView;
import com.taobao.weex.ui.view.refresh.wrapper.BaseBounceView;
import com.taobao.weex.ui.view.refresh.wrapper.BounceScrollerView;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import io.dcloud.feature.uniapp.ui.component.AbsVContainer;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
@Component(lazyload = false)
/* loaded from: classes.dex */
public class WXScroller extends WXBaseScroller {
    public static final String DIRECTION = "direction";
    private static final int SWIPE_MIN_DISTANCE = 5;
    private static final int SWIPE_THRESHOLD_VELOCITY = 300;
    private Handler handler;
    private boolean isScrollable;
    private int mActiveFeature;
    private Map<String, AppearanceHelper> mAppearanceComponents;
    private int mChildrenLayoutOffset;
    private int mContentHeight;
    private int mContentWidth;
    private boolean mForceLoadmoreNextTime;
    private GestureDetector mGestureDetector;
    private boolean mHasAddScrollEvent;
    private boolean mIsHostAttachedToWindow;
    private Boolean mIslastDirectionRTL;
    private Point mLastReport;
    private int mOffsetAccuracy;
    private View.OnAttachStateChangeListener mOnAttachStateChangeListener;
    protected int mOrientation;
    private FrameLayout mRealView;
    private List<WXComponent> mRefreshs;
    private ScrollStartEndHelper mScrollStartEndHelper;
    private FrameLayout mScrollerView;
    private Map<String, Map<String, WXComponent>> mStickyMap;
    private boolean mlastDirectionRTL;
    private boolean pageEnable;
    private int pageSize;
    private WXStickyHelper stickyHelper;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class Creator implements ComponentCreator {
        @Override // com.taobao.weex.ui.ComponentCreator
        public WXComponent createInstance(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
            wXSDKInstance.setUseScroller(true);
            return new WXScroller(wXSDKInstance, wXVContainer, basicComponentData);
        }
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        private final WXHorizontalScrollView scrollView;

        MyGestureDetector(WXHorizontalScrollView wXHorizontalScrollView) {
            this.scrollView = wXHorizontalScrollView;
        }

        public WXHorizontalScrollView getScrollView() {
            return this.scrollView;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            int size = ((AbsVContainer) WXScroller.this).mChildren.size();
            try {
            } catch (Exception e) {
                WXLogUtils.e("There was an error processing the Fling event:" + e.getMessage());
            }
            if (motionEvent.getX() - motionEvent2.getX() <= 5.0f || Math.abs(f) <= 300.0f) {
                if (motionEvent2.getX() - motionEvent.getX() > 5.0f && Math.abs(f) > 300.0f) {
                    int i = WXScroller.this.pageSize;
                    WXScroller wXScroller = WXScroller.this;
                    wXScroller.mActiveFeature = wXScroller.mActiveFeature > 0 ? WXScroller.this.mActiveFeature - 1 : 0;
                    this.scrollView.smoothScrollTo(WXScroller.this.mActiveFeature * i, 0);
                    return true;
                }
                return false;
            }
            int i2 = WXScroller.this.pageSize;
            WXScroller wXScroller2 = WXScroller.this;
            int i3 = size - 1;
            if (wXScroller2.mActiveFeature < i3) {
                i3 = WXScroller.this.mActiveFeature + 1;
            }
            wXScroller2.mActiveFeature = i3;
            this.scrollView.smoothScrollTo(WXScroller.this.mActiveFeature * i2, 0);
            return true;
        }
    }

    @Deprecated
    public WXScroller(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, String str, boolean z, BasicComponentData basicComponentData) {
        this(wXSDKInstance, wXVContainer, basicComponentData);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0058  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean checkItemVisibleInScroller(com.taobao.weex.ui.component.WXComponent r5) {
        /*
            r4 = this;
            r0 = 0
            r1 = 0
        L2:
            if (r5 == 0) goto L5e
            boolean r2 = r5 instanceof com.taobao.weex.ui.component.WXScroller
            if (r2 != 0) goto L5e
            com.taobao.weex.ui.component.WXVContainer r2 = r5.getParent()
            boolean r2 = r2 instanceof com.taobao.weex.ui.component.WXScroller
            if (r2 == 0) goto L59
            int r1 = r4.mOrientation
            r2 = 0
            if (r1 != 0) goto L36
            com.taobao.weex.ui.action.GraphicPosition r1 = r5.getLayoutPosition()
            float r1 = r1.getLeft()
            int r1 = (int) r1
            int r3 = r4.getScrollX()
            int r1 = r1 - r3
            float r1 = (float) r1
            float r3 = r5.getLayoutWidth()
            float r2 = r2 - r3
            int r2 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r2 <= 0) goto L58
            float r2 = r4.getLayoutWidth()
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 >= 0) goto L58
            goto L56
        L36:
            com.taobao.weex.ui.action.GraphicPosition r1 = r5.getLayoutPosition()
            float r1 = r1.getTop()
            int r1 = (int) r1
            int r3 = r4.getScrollY()
            int r1 = r1 - r3
            float r1 = (float) r1
            float r3 = r5.getLayoutHeight()
            float r2 = r2 - r3
            int r2 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r2 <= 0) goto L58
            float r2 = r4.getLayoutHeight()
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 >= 0) goto L58
        L56:
            r1 = 1
            goto L59
        L58:
            r1 = 0
        L59:
            com.taobao.weex.ui.component.WXVContainer r5 = r5.getParent()
            goto L2
        L5e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.ui.component.WXScroller.checkItemVisibleInScroller(com.taobao.weex.ui.component.WXComponent):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean checkRefreshOrLoading(final WXComponent wXComponent) {
        boolean z;
        if (!(wXComponent instanceof WXRefresh) || getHostView() == 0) {
            z = false;
        } else {
            ((BaseBounceView) getHostView()).setOnRefreshListener((WXRefresh) wXComponent);
            this.handler.postDelayed(WXThread.secure(new Runnable() { // from class: com.taobao.weex.ui.component.WXScroller.3
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.lang.Runnable
                public void run() {
                    ((BaseBounceView) WXScroller.this.getHostView()).setHeaderView(wXComponent);
                }
            }), 100L);
            z = true;
        }
        if (!(wXComponent instanceof WXLoading) || getHostView() == 0) {
            return z;
        }
        ((BaseBounceView) getHostView()).setOnLoadingListener((WXLoading) wXComponent);
        this.handler.postDelayed(WXThread.secure(new Runnable() { // from class: com.taobao.weex.ui.component.WXScroller.4
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                ((BaseBounceView) WXScroller.this.getHostView()).setFooterView(wXComponent);
            }
        }), 100L);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchDisappearEvent() {
        int appearStatus;
        Iterator<Map.Entry<String, AppearanceHelper>> it = this.mAppearanceComponents.entrySet().iterator();
        while (it.hasNext()) {
            AppearanceHelper value = it.next().getValue();
            if (value.isWatch() && (appearStatus = value.setAppearStatus(false)) != 0) {
                value.getAwareChild().notifyAppearStateChange(appearStatus == 1 ? Constants.Event.APPEAR : Constants.Event.DISAPPEAR, "");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fireScrollEvent(Rect rect, int i, int i2, int i3, int i4) {
        fireEvent("scroll", getScrollEvent(i, i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void procAppear(int i, int i2, int i3, int i4) {
        int appearStatus;
        if (this.mIsHostAttachedToWindow) {
            int i5 = i2 - i4;
            int i6 = i - i3;
            String str = i5 > 0 ? "up" : i5 < 0 ? "down" : null;
            if (this.mOrientation == 0 && i6 != 0) {
                str = i6 > 0 ? "right" : "left";
            }
            Iterator<Map.Entry<String, AppearanceHelper>> it = this.mAppearanceComponents.entrySet().iterator();
            while (it.hasNext()) {
                AppearanceHelper value = it.next().getValue();
                if (value.isWatch() && (appearStatus = value.setAppearStatus(checkItemVisibleInScroller(value.getAwareChild()))) != 0) {
                    value.getAwareChild().notifyAppearStateChange(appearStatus == 1 ? Constants.Event.APPEAR : Constants.Event.DISAPPEAR, str);
                }
            }
        }
    }

    private void setWatch(int i, WXComponent wXComponent, boolean z) {
        AppearanceHelper appearanceHelper = this.mAppearanceComponents.get(wXComponent.getRef());
        if (appearanceHelper == null) {
            appearanceHelper = new AppearanceHelper(wXComponent);
            this.mAppearanceComponents.put(wXComponent.getRef(), appearanceHelper);
        }
        appearanceHelper.setWatchEvent(i, z);
        procAppear(0, 0, 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldReport(int i, int i2) {
        Point point = this.mLastReport;
        int i3 = point.x;
        if (i3 == -1 && point.y == -1) {
            point.x = i;
            point.y = i2;
            return true;
        }
        if (this.mOrientation == 0 && Math.abs(i - i3) >= this.mOffsetAccuracy) {
            Point point2 = this.mLastReport;
            point2.x = i;
            point2.y = i2;
            return true;
        }
        if (this.mOrientation != 1 || Math.abs(i2 - this.mLastReport.y) < this.mOffsetAccuracy) {
            return false;
        }
        Point point3 = this.mLastReport;
        point3.x = i;
        point3.y = i2;
        return true;
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public void addChild(WXComponent wXComponent, int i) {
        if ((wXComponent instanceof WXBaseRefresh) && checkRefreshOrLoading(wXComponent)) {
            this.mRefreshs.add(wXComponent);
        }
        super.addChild(wXComponent, i);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void addEvent(String str) {
        super.addEvent(str);
        if (!ScrollStartEndHelper.isScrollEvent(str) || getInnerView() == null || this.mHasAddScrollEvent) {
            return;
        }
        this.mHasAddScrollEvent = true;
        if (getInnerView() instanceof WXScrollView) {
            ((WXScrollView) getInnerView()).addScrollViewListener(new WXScrollView.WXScrollViewListener() { // from class: com.taobao.weex.ui.component.WXScroller.1
                @Override // com.taobao.weex.ui.view.WXScrollView.WXScrollViewListener
                public void onScroll(WXScrollView wXScrollView, int i, int i2) {
                }

                @Override // com.taobao.weex.ui.view.WXScrollView.WXScrollViewListener
                public void onScrollChanged(WXScrollView wXScrollView, int i, int i2, int i3, int i4) {
                    WXScroller.this.getScrollStartEndHelper().onScrolled(i, i2);
                    if (WXScroller.this.getEvents().contains("scroll") && WXScroller.this.shouldReport(i, i2)) {
                        WXScroller.this.fireScrollEvent(wXScrollView.getContentFrame(), i, i2, i3, i4);
                    }
                }

                @Override // com.taobao.weex.ui.view.WXScrollView.WXScrollViewListener
                public void onScrollStopped(WXScrollView wXScrollView, int i, int i2) {
                }

                @Override // com.taobao.weex.ui.view.WXScrollView.WXScrollViewListener
                public void onScrollToBottom(WXScrollView wXScrollView, int i, int i2) {
                }
            });
        } else if (getInnerView() instanceof WXHorizontalScrollView) {
            ((WXHorizontalScrollView) getInnerView()).addScrollViewListener(new WXHorizontalScrollView.ScrollViewListener() { // from class: com.taobao.weex.ui.component.WXScroller.2
                @Override // com.taobao.weex.ui.view.WXHorizontalScrollView.ScrollViewListener
                public void onScrollChanged(WXHorizontalScrollView wXHorizontalScrollView, int i, int i2, int i3, int i4) {
                    WXScroller.this.getScrollStartEndHelper().onScrolled(i, i2);
                    if (WXScroller.this.getEvents().contains("scroll") && WXScroller.this.shouldReport(i, i2)) {
                        WXScroller.this.fireScrollEvent(wXHorizontalScrollView.getContentFrame(), i, i2, i3, i4);
                    }
                }
            });
        }
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public void addSubView(View view, int i) {
        FrameLayout frameLayout;
        if (view == null || (frameLayout = this.mRealView) == null || (view instanceof WXBaseRefreshLayout)) {
            return;
        }
        if (i >= frameLayout.getChildCount()) {
            i = -1;
        }
        if (i == -1) {
            this.mRealView.addView(view);
        } else {
            this.mRealView.addView(view, i);
        }
    }

    @Override // com.taobao.weex.ui.component.WXBaseScroller, com.taobao.weex.ui.component.Scrollable
    public void bindAppearEvent(WXComponent wXComponent) {
        setWatch(0, wXComponent, true);
    }

    @Override // com.taobao.weex.ui.component.WXBaseScroller, com.taobao.weex.ui.component.Scrollable
    public void bindDisappearEvent(WXComponent wXComponent) {
        setWatch(1, wXComponent, true);
    }

    @Override // com.taobao.weex.ui.component.WXBaseScroller, com.taobao.weex.ui.component.Scrollable
    public void bindStickStyle(WXComponent wXComponent) {
        this.stickyHelper.bindStickStyle(wXComponent, this.mStickyMap);
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer, com.taobao.weex.ui.component.WXComponent
    public void createViewImpl() {
        super.createViewImpl();
        for (int i = 0; i < this.mRefreshs.size(); i++) {
            WXComponent wXComponent = this.mRefreshs.get(i);
            wXComponent.createViewImpl();
            checkRefreshOrLoading(wXComponent);
        }
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer, com.taobao.weex.ui.component.WXComponent
    public void destroy() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.destroy();
        Map<String, AppearanceHelper> map = this.mAppearanceComponents;
        if (map != null) {
            map.clear();
        }
        Map<String, Map<String, WXComponent>> map2 = this.mStickyMap;
        if (map2 != null) {
            map2.clear();
        }
        if (this.mOnAttachStateChangeListener != null && getInnerView() != null) {
            getInnerView().removeOnAttachStateChangeListener(this.mOnAttachStateChangeListener);
        }
        if (getInnerView() == null || !(getInnerView() instanceof IWXScroller)) {
            return;
        }
        ((IWXScroller) getInnerView()).destroy();
    }

    @Override // com.taobao.weex.ui.component.WXVContainer, io.dcloud.feature.uniapp.ui.component.AbsVContainer
    protected int getChildrenLayoutTopOffset() {
        int size;
        if (this.mChildrenLayoutOffset == 0 && (size = this.mRefreshs.size()) > 0) {
            for (int i = 0; i < size; i++) {
                this.mChildrenLayoutOffset += this.mRefreshs.get(i).getLayoutTopOffsetForSibling();
            }
        }
        return this.mChildrenLayoutOffset;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.taobao.weex.ui.component.WXBaseScroller
    public ViewGroup getInnerView() {
        if (getHostView() == 0) {
            return null;
        }
        return getHostView() instanceof BounceScrollerView ? ((BounceScrollerView) getHostView()).getInnerView() : (ViewGroup) getHostView();
    }

    @Override // com.taobao.weex.ui.component.WXBaseScroller, com.taobao.weex.ui.component.Scrollable
    public int getOrientation() {
        return this.mOrientation;
    }

    @Override // com.taobao.weex.ui.component.WXBaseScroller
    public Map<String, Object> getScrollEvent(int i, int i2) {
        Rect rect = new Rect();
        if (getInnerView() instanceof WXScrollView) {
            rect = ((WXScrollView) getInnerView()).getContentFrame();
        } else if (getInnerView() instanceof WXHorizontalScrollView) {
            rect = ((WXHorizontalScrollView) getInnerView()).getContentFrame();
        }
        HashMap map = new HashMap(2);
        HashMap map2 = new HashMap(2);
        HashMap map3 = new HashMap(2);
        float instanceViewPortWidthWithFloat = getInstance().getInstanceViewPortWidthWithFloat();
        map2.put("width", Float.valueOf(WXViewUtils.getWebPxByWidth(rect.width(), instanceViewPortWidthWithFloat)));
        map2.put("height", Float.valueOf(WXViewUtils.getWebPxByWidth(rect.height(), instanceViewPortWidthWithFloat)));
        map3.put(Constants.Name.X, Float.valueOf(-WXViewUtils.getWebPxByWidth(i, instanceViewPortWidthWithFloat)));
        map3.put(Constants.Name.Y, Float.valueOf(-WXViewUtils.getWebPxByWidth(i2, instanceViewPortWidthWithFloat)));
        map.put(Constants.Name.CONTENT_SIZE, map2);
        map.put(Constants.Name.CONTENT_OFFSET, map3);
        return map;
    }

    public ScrollStartEndHelper getScrollStartEndHelper() {
        if (this.mScrollStartEndHelper == null) {
            this.mScrollStartEndHelper = new ScrollStartEndHelper(this);
        }
        return this.mScrollStartEndHelper;
    }

    @Override // com.taobao.weex.ui.component.WXBaseScroller, com.taobao.weex.ui.component.Scrollable
    public int getScrollX() {
        if (getInnerView() == null) {
            return 0;
        }
        return getInnerView().getScrollX();
    }

    @Override // com.taobao.weex.ui.component.WXBaseScroller, com.taobao.weex.ui.component.Scrollable
    public int getScrollY() {
        if (getInnerView() == null) {
            return 0;
        }
        return getInnerView().getScrollY();
    }

    @Override // com.taobao.weex.ui.component.WXBaseScroller
    public Map<String, Map<String, WXComponent>> getStickMap() {
        return this.mStickyMap;
    }

    @Override // com.taobao.weex.ui.component.WXBaseScroller, com.taobao.weex.ui.component.Scrollable
    public boolean isScrollable() {
        return this.isScrollable;
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected WXComponent.MeasureOutput measure(int i, int i2) {
        WXComponent.MeasureOutput measureOutput = new WXComponent.MeasureOutput();
        if (this.mOrientation == 0) {
            int screenWidth = WXViewUtils.getScreenWidth(WXEnvironment.sApplication);
            int weexWidth = WXViewUtils.getWeexWidth(getInstanceId());
            if (weexWidth < screenWidth) {
                screenWidth = weexWidth;
            }
            if (i > screenWidth) {
                i = -1;
            }
            measureOutput.width = i;
            measureOutput.height = i2;
            return measureOutput;
        }
        int screenHeight = WXViewUtils.getScreenHeight(WXEnvironment.sApplication);
        int weexHeight = WXViewUtils.getWeexHeight(getInstanceId());
        if (weexHeight < screenHeight) {
            screenHeight = weexHeight;
        }
        if (i2 > screenHeight) {
            i2 = -1;
        }
        measureOutput.height = i2;
        measureOutput.width = i;
        return measureOutput;
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer, com.taobao.weex.ui.component.WXComponent
    public void notifyAppearStateChange(String str, String str2) {
        if (containsEvent(Constants.Event.APPEAR) || containsEvent(Constants.Event.DISAPPEAR)) {
            HashMap map = new HashMap();
            map.put("direction", str2);
            fireEvent(str, map);
        }
    }

    protected void onLoadMore(FrameLayout frameLayout, int i, int i2) {
        try {
            String loadMoreOffset = getAttrs().getLoadMoreOffset();
            if (TextUtils.isEmpty(loadMoreOffset)) {
                loadMoreOffset = String.valueOf(1);
            }
            int realPxByWidth = (int) WXViewUtils.getRealPxByWidth(Float.parseFloat(loadMoreOffset), getInstance().getInstanceViewPortWidthWithFloat());
            if (frameLayout instanceof WXHorizontalScrollView) {
                int width = frameLayout.getChildAt(0).getWidth();
                if ((width - i) - frameLayout.getWidth() < realPxByWidth) {
                    if (this.mContentWidth != width || this.mForceLoadmoreNextTime) {
                        fireEvent(Constants.Event.LOADMORE);
                        this.mContentWidth = width;
                        this.mForceLoadmoreNextTime = false;
                        return;
                    }
                    return;
                }
                return;
            }
            int height = frameLayout.getChildAt(0).getHeight();
            int height2 = (height - i2) - frameLayout.getHeight();
            if (height2 < realPxByWidth) {
                if (WXEnvironment.isApkDebugable()) {
                    WXLogUtils.d("[WXScroller-onScroll] offScreenY :" + height2);
                }
                if (this.mContentHeight != height || this.mForceLoadmoreNextTime) {
                    fireEvent(Constants.Event.LOADMORE);
                    this.mContentHeight = height;
                    this.mForceLoadmoreNextTime = false;
                }
            }
        } catch (Exception e) {
            WXLogUtils.d("[WXScroller-onScroll] ", e);
        }
    }

    @Override // com.taobao.weex.ui.component.WXBaseScroller, com.taobao.weex.ui.view.WXScrollView.WXScrollViewListener
    public void onScroll(WXScrollView wXScrollView, int i, int i2) {
        onLoadMore(wXScrollView, i, i2);
    }

    @Override // com.taobao.weex.ui.component.WXBaseScroller, com.taobao.weex.ui.view.WXScrollView.WXScrollViewListener
    public void onScrollChanged(WXScrollView wXScrollView, int i, int i2, int i3, int i4) {
        procAppear(i, i2, i3, i4);
    }

    @Override // com.taobao.weex.ui.component.WXBaseScroller, com.taobao.weex.ui.view.WXScrollView.WXScrollViewListener
    public void onScrollStopped(WXScrollView wXScrollView, int i, int i2) {
    }

    @Override // com.taobao.weex.ui.component.WXBaseScroller, com.taobao.weex.ui.view.WXScrollView.WXScrollViewListener
    public void onScrollToBottom(WXScrollView wXScrollView, int i, int i2) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public void remove(WXComponent wXComponent, boolean z) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.remove(wXComponent, z);
        if (wXComponent instanceof WXLoading) {
            ((BaseBounceView) getHostView()).removeFooterView(wXComponent);
        } else if (wXComponent instanceof WXRefresh) {
            ((BaseBounceView) getHostView()).removeHeaderView(wXComponent);
        }
    }

    @JSMethod
    public void resetLoadmore() {
        this.mForceLoadmoreNextTime = true;
    }

    public void scrollBy(int i, int i2) {
        scrollBy(i, i2, false);
    }

    @Override // com.taobao.weex.ui.component.WXBaseScroller, com.taobao.weex.ui.component.Scrollable
    public void scrollTo(WXComponent wXComponent, Map<String, Object> map) {
        boolean zBooleanValue;
        int absoluteX;
        float realPxByWidth = 0.0f;
        if (map != null) {
            String string = map.get("offset") == null ? WXInstanceApm.VALUE_ERROR_CODE_DEFAULT : map.get("offset").toString();
            zBooleanValue = WXUtils.getBoolean(map.get(Constants.Name.ANIMATED), Boolean.TRUE).booleanValue();
            if (string != null) {
                try {
                    realPxByWidth = WXViewUtils.getRealPxByWidth(Float.parseFloat(string), getInstance().getInstanceViewPortWidthWithFloat());
                } catch (Exception e) {
                    WXLogUtils.e("Float parseFloat error :" + e.getMessage());
                }
            }
        } else {
            zBooleanValue = true;
        }
        if (this.pageEnable) {
            this.mActiveFeature = this.mChildren.indexOf(wXComponent);
        }
        int absoluteY = wXComponent.getAbsoluteY() - getAbsoluteY();
        if (isLayoutRTL()) {
            if (wXComponent.getParent() == null || wXComponent.getParent() != this) {
                absoluteX = ((wXComponent.getAbsoluteX() - getAbsoluteX()) - getInnerView().getMeasuredWidth()) + ((int) wXComponent.getLayoutWidth());
            } else if (getInnerView().getChildCount() > 0) {
                absoluteX = (getInnerView().getChildAt(0).getWidth() - (wXComponent.getAbsoluteX() - getAbsoluteX())) - getInnerView().getMeasuredWidth();
            } else {
                absoluteX = wXComponent.getAbsoluteX() - getAbsoluteX();
            }
            realPxByWidth = -realPxByWidth;
        } else {
            absoluteX = wXComponent.getAbsoluteX() - getAbsoluteX();
        }
        int i = (int) realPxByWidth;
        scrollBy((absoluteX - getScrollX()) + i, (absoluteY - getScrollY()) + i, zBooleanValue);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void setLayout(WXComponent wXComponent) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (TextUtils.isEmpty(wXComponent.getComponentType()) || TextUtils.isEmpty(wXComponent.getRef()) || wXComponent.getLayoutPosition() == null || wXComponent.getLayoutSize() == null) {
            return;
        }
        if (wXComponent.getHostView() != null) {
            ViewCompat.setLayoutDirection(wXComponent.getHostView(), wXComponent.isLayoutRTL() ? 1 : 0);
        }
        super.setLayout(wXComponent);
    }

    @WXComponentProp(name = Constants.Name.OFFSET_ACCURACY)
    public void setOffsetAccuracy(int i) {
        this.mOffsetAccuracy = (int) WXViewUtils.getRealPxByWidth(i, getInstance().getInstanceViewPortWidthWithFloat());
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected boolean setProperty(String str, Object obj) {
        str.getClass();
        str.hashCode();
        switch (str) {
            case "showScrollbar":
                Boolean bool = WXUtils.getBoolean(obj, null);
                if (bool != null) {
                    setShowScrollbar(bool.booleanValue());
                }
                return true;
            case "offsetAccuracy":
                setOffsetAccuracy(WXUtils.getInteger(obj, 10).intValue());
                return true;
            case "scrollable":
                setScrollable(WXUtils.getBoolean(obj, Boolean.TRUE).booleanValue());
                return true;
            default:
                return super.setProperty(str, obj);
        }
    }

    @WXComponentProp(name = Constants.Name.SCROLLABLE)
    public void setScrollable(boolean z) {
        this.isScrollable = z;
        ViewGroup innerView = getInnerView();
        if (innerView instanceof WXHorizontalScrollView) {
            ((WXHorizontalScrollView) innerView).setScrollable(z);
        } else if (innerView instanceof WXScrollView) {
            ((WXScrollView) innerView).setScrollable(z);
        }
    }

    @WXComponentProp(name = Constants.Name.SHOW_SCROLLBAR)
    public void setShowScrollbar(boolean z) {
        if (getInnerView() == null) {
            return;
        }
        if (this.mOrientation == 1) {
            getInnerView().setVerticalScrollBarEnabled(z);
        } else {
            getInnerView().setHorizontalScrollBarEnabled(z);
        }
    }

    @Override // com.taobao.weex.ui.component.WXBaseScroller, com.taobao.weex.ui.component.Scrollable
    public void unbindAppearEvent(WXComponent wXComponent) {
        setWatch(0, wXComponent, false);
    }

    @Override // com.taobao.weex.ui.component.WXBaseScroller, com.taobao.weex.ui.component.Scrollable
    public void unbindDisappearEvent(WXComponent wXComponent) {
        setWatch(1, wXComponent, false);
    }

    @Override // com.taobao.weex.ui.component.WXBaseScroller, com.taobao.weex.ui.component.Scrollable
    public void unbindStickStyle(WXComponent wXComponent) {
        this.stickyHelper.unbindStickStyle(wXComponent, this.mStickyMap);
    }

    public WXScroller(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, basicComponentData);
        this.mOrientation = 1;
        this.mRefreshs = new ArrayList();
        this.mChildrenLayoutOffset = 0;
        this.mForceLoadmoreNextTime = false;
        this.mOffsetAccuracy = 10;
        this.mLastReport = new Point(-1, -1);
        this.mHasAddScrollEvent = false;
        this.mActiveFeature = 0;
        this.pageSize = 0;
        this.pageEnable = false;
        this.mIsHostAttachedToWindow = false;
        this.mlastDirectionRTL = false;
        this.mAppearanceComponents = new HashMap();
        this.mStickyMap = new HashMap();
        this.mContentHeight = 0;
        this.mContentWidth = 0;
        this.handler = new Handler(Looper.getMainLooper());
        this.isScrollable = true;
        this.stickyHelper = new WXStickyHelper(this);
        wXSDKInstance.getApmForInstance().updateDiffStats(WXInstanceApm.KEY_PAGE_STATS_SCROLLER_NUM, 1.0d);
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer, com.taobao.weex.ui.component.WXComponent
    public ViewGroup getRealView() {
        return this.mScrollerView;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.taobao.weex.ui.component.WXComponent
    public ViewGroup initComponentHostView(Context context) {
        String scrollDirection;
        BounceScrollerView bounceScrollerView;
        if (getAttrs().isEmpty()) {
            scrollDirection = "vertical";
        } else {
            scrollDirection = getAttrs().getScrollDirection();
            Object obj = getAttrs().get(Constants.Name.PAGE_ENABLED);
            this.pageEnable = obj != null && Boolean.parseBoolean(obj.toString());
            Object obj2 = getAttrs().get(Constants.Name.PAGE_SIZE);
            if (obj2 != null) {
                float realPxByWidth = WXViewUtils.getRealPxByWidth(WXUtils.getFloat(obj2), getInstance().getInstanceViewPortWidthWithFloat());
                if (realPxByWidth != 0.0f) {
                    this.pageSize = (int) realPxByWidth;
                }
            }
        }
        if (Constants.Value.HORIZONTAL.equals(scrollDirection)) {
            this.mOrientation = 0;
            final WXHorizontalScrollView wXHorizontalScrollView = new WXHorizontalScrollView(context);
            this.mRealView = new FrameLayout(context);
            wXHorizontalScrollView.setScrollViewListener(new WXHorizontalScrollView.ScrollViewListener() { // from class: com.taobao.weex.ui.component.WXScroller.5
                @Override // com.taobao.weex.ui.view.WXHorizontalScrollView.ScrollViewListener
                public void onScrollChanged(WXHorizontalScrollView wXHorizontalScrollView2, int i, int i2, int i3, int i4) {
                    WXScroller.this.procAppear(i, i2, i3, i4);
                    WXScroller.this.onLoadMore(wXHorizontalScrollView2, i, i2);
                }
            });
            wXHorizontalScrollView.addView(this.mRealView, new FrameLayout.LayoutParams(-1, -1));
            wXHorizontalScrollView.setHorizontalScrollBarEnabled(false);
            this.mScrollerView = wXHorizontalScrollView;
            final View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.taobao.weex.ui.component.WXScroller.6
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View view, final int i, int i2, final int i3, int i4, final int i5, int i6, final int i7, int i8) {
                    wXHorizontalScrollView.post(new Runnable() { // from class: com.taobao.weex.ui.component.WXScroller.6.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (WXScroller.this.mIslastDirectionRTL != null && WXScroller.this.isLayoutRTL() != WXScroller.this.mIslastDirectionRTL.booleanValue()) {
                                int scrollX = WXScroller.this.getScrollX();
                                int width = WXScroller.this.getInnerView().getChildAt(0).getWidth();
                                int measuredWidth = WXScroller.this.getInnerView().getMeasuredWidth();
                                AnonymousClass6 anonymousClass6 = AnonymousClass6.this;
                                wXHorizontalScrollView.scrollTo((width - scrollX) - measuredWidth, this.getScrollY());
                            } else if (WXScroller.this.isLayoutRTL()) {
                                int i9 = (i3 - i) - (i7 - i5);
                                if (i9 != 0) {
                                    AnonymousClass6 anonymousClass62 = AnonymousClass6.this;
                                    wXHorizontalScrollView.scrollBy(i9, this.getScrollY());
                                }
                            }
                            WXScroller wXScroller = WXScroller.this;
                            wXScroller.mIslastDirectionRTL = new Boolean(wXScroller.isLayoutRTL());
                        }
                    });
                }
            };
            this.mRealView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.taobao.weex.ui.component.WXScroller.7
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(View view) {
                    view.addOnLayoutChangeListener(onLayoutChangeListener);
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(View view) {
                    view.removeOnLayoutChangeListener(onLayoutChangeListener);
                }
            });
            bounceScrollerView = wXHorizontalScrollView;
            if (this.pageEnable) {
                this.mGestureDetector = new GestureDetector(new MyGestureDetector(wXHorizontalScrollView));
                wXHorizontalScrollView.setOnTouchListener(new View.OnTouchListener() { // from class: com.taobao.weex.ui.component.WXScroller.8
                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (WXScroller.this.pageSize == 0) {
                            WXScroller.this.pageSize = view.getMeasuredWidth();
                        }
                        if (WXScroller.this.mGestureDetector.onTouchEvent(motionEvent)) {
                            return true;
                        }
                        if (motionEvent.getAction() != 1 && motionEvent.getAction() != 3) {
                            return false;
                        }
                        int scrollX = WXScroller.this.getScrollX();
                        int i = WXScroller.this.pageSize;
                        WXScroller.this.mActiveFeature = (scrollX + (i / 2)) / i;
                        wXHorizontalScrollView.smoothScrollTo(WXScroller.this.mActiveFeature * i, 0);
                        return true;
                    }
                });
                bounceScrollerView = wXHorizontalScrollView;
            }
        } else {
            this.mOrientation = 1;
            BounceScrollerView bounceScrollerView2 = new BounceScrollerView(context, this.mOrientation, this);
            this.mRealView = new FrameLayout(context);
            WXScrollView innerView = bounceScrollerView2.getInnerView();
            innerView.addScrollViewListener(this);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
            this.mScrollerView = innerView;
            innerView.addView(this.mRealView, layoutParams);
            innerView.setVerticalScrollBarEnabled(true);
            innerView.setNestedScrollingEnabled(WXUtils.getBoolean(getAttrs().get(Constants.Name.NEST_SCROLLING_ENABLED), Boolean.TRUE).booleanValue());
            innerView.addScrollViewListener(new WXScrollView.WXScrollViewListener() { // from class: com.taobao.weex.ui.component.WXScroller.9
                @Override // com.taobao.weex.ui.view.WXScrollView.WXScrollViewListener
                public void onScroll(WXScrollView wXScrollView, int i, int i2) {
                    List<OnWXScrollListener> wXScrollListeners = WXScroller.this.getInstance().getWXScrollListeners();
                    if (wXScrollListeners == null || wXScrollListeners.size() <= 0) {
                        return;
                    }
                    for (OnWXScrollListener onWXScrollListener : wXScrollListeners) {
                        if (onWXScrollListener != null) {
                            if (!(onWXScrollListener instanceof ICheckBindingScroller)) {
                                onWXScrollListener.onScrolled(wXScrollView, i, i2);
                            } else if (((ICheckBindingScroller) onWXScrollListener).isNeedScroller(WXScroller.this.getRef(), null)) {
                                onWXScrollListener.onScrolled(wXScrollView, i, i2);
                            }
                        }
                    }
                }

                @Override // com.taobao.weex.ui.view.WXScrollView.WXScrollViewListener
                public void onScrollChanged(WXScrollView wXScrollView, int i, int i2, int i3, int i4) {
                }

                @Override // com.taobao.weex.ui.view.WXScrollView.WXScrollViewListener
                public void onScrollStopped(WXScrollView wXScrollView, int i, int i2) {
                    List<OnWXScrollListener> wXScrollListeners = WXScroller.this.getInstance().getWXScrollListeners();
                    if (wXScrollListeners != null && wXScrollListeners.size() > 0) {
                        for (OnWXScrollListener onWXScrollListener : wXScrollListeners) {
                            if (onWXScrollListener != null) {
                                onWXScrollListener.onScrollStateChanged(wXScrollView, i, i2, 0);
                            }
                        }
                    }
                    WXScroller.this.getScrollStartEndHelper().onScrollStateChanged(0);
                }

                @Override // com.taobao.weex.ui.view.WXScrollView.WXScrollViewListener
                public void onScrollToBottom(WXScrollView wXScrollView, int i, int i2) {
                }
            });
            bounceScrollerView = bounceScrollerView2;
        }
        bounceScrollerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.taobao.weex.ui.component.WXScroller.10
            /* JADX WARN: Type inference failed for: r0v2, types: [android.view.View] */
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                WXScroller.this.procAppear(0, 0, 0, 0);
                ?? hostView = WXScroller.this.getHostView();
                if (hostView == 0) {
                    return;
                }
                hostView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        View.OnAttachStateChangeListener onAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: com.taobao.weex.ui.component.WXScroller.11
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                WXScroller.this.mIsHostAttachedToWindow = true;
                WXScroller wXScroller = WXScroller.this;
                wXScroller.procAppear(wXScroller.getScrollX(), WXScroller.this.getScrollY(), WXScroller.this.getScrollX(), WXScroller.this.getScrollY());
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                WXScroller.this.mIsHostAttachedToWindow = false;
                WXScroller.this.dispatchDisappearEvent();
            }
        };
        this.mOnAttachStateChangeListener = onAttachStateChangeListener;
        bounceScrollerView.addOnAttachStateChangeListener(onAttachStateChangeListener);
        return bounceScrollerView;
    }

    public void scrollBy(final int i, final int i2, final boolean z) {
        if (getInnerView() == null) {
            return;
        }
        getInnerView().postDelayed(new Runnable() { // from class: com.taobao.weex.ui.component.WXScroller.12
            @Override // java.lang.Runnable
            public void run() {
                WXScroller wXScroller = WXScroller.this;
                if (wXScroller.mOrientation == 1) {
                    if (z) {
                        ((WXScrollView) wXScroller.getInnerView()).smoothScrollBy(0, i2);
                    } else {
                        ((WXScrollView) wXScroller.getInnerView()).scrollBy(0, i2);
                    }
                } else if (z) {
                    ((WXHorizontalScrollView) wXScroller.getInnerView()).smoothScrollBy(i, 0);
                } else {
                    ((WXHorizontalScrollView) wXScroller.getInnerView()).scrollBy(i, 0);
                }
                WXScroller.this.getInnerView().invalidate();
            }
        }, 16L);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void setMarginsSupportRTL(ViewGroup.MarginLayoutParams marginLayoutParams, int i, int i2, int i3, int i4) {
        marginLayoutParams.setMargins(i, i2, i3, i4);
        marginLayoutParams.setMarginStart(i);
        marginLayoutParams.setMarginEnd(i3);
    }
}
