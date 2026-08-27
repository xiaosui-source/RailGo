package com.taobao.weex.ui.component;

import android.animation.ObjectAnimator;
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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.annotation.Component;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.Constants;
import com.taobao.weex.common.ICheckBindingScroller;
import com.taobao.weex.common.OnWXScrollListener;
import com.taobao.weex.common.WXThread;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.ui.ComponentCreator;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.helper.ScrollStartEndHelper;
import com.taobao.weex.ui.view.IWXScroller;
import com.taobao.weex.ui.view.WXBaseRefreshLayout;
import com.taobao.weex.ui.view.refresh.wrapper.BaseBounceView;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.DensityUtils;
import io.dcloud.common.util.StringUtil;
import io.dcloud.feature.uniapp.ui.component.AbsVContainer;
import io.dcloud.feature.weex_scroller.helper.DCScrollStartEndHelper;
import io.dcloud.feature.weex_scroller.view.DCBounceScrollerView;
import io.dcloud.feature.weex_scroller.view.DCWXHorizontalScrollView;
import io.dcloud.feature.weex_scroller.view.DCWXScrollView;
import io.dcloud.feature.weex_scroller.view.WXStickyHelper;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
@Component(lazyload = false)
/* loaded from: classes.dex */
public class DCWXScroller extends WXBaseScroller implements DCWXScrollView.WXScrollViewListener, Scrollable {
    public static final String DIRECTION = "direction";
    private static final int SWIPE_MIN_DISTANCE = 5;
    private static final int SWIPE_THRESHOLD_VELOCITY = 300;
    private boolean canScroll2Top;
    Map<String, WXComponent> childens;
    private Handler handler;
    private boolean isAnimation;
    private boolean isScrollable;
    private AtomicBoolean isViewLayoutFinished;
    private int mActiveFeature;
    private Map<String, AppearanceHelper> mAppearanceComponents;
    private int mChildrenLayoutOffset;
    private int mContentHeight;
    private int mContentWidth;
    private boolean mForceLoadmoreNextTime;
    private GestureDetector mGestureDetector;
    private boolean mHasAddScrollEvent;
    private boolean mIsHostAttachedToWindow;
    private Point mLastReport;
    private int mOffsetAccuracy;
    private View.OnAttachStateChangeListener mOnAttachStateChangeListener;
    protected int mOrientation;
    private FrameLayout mRealView;
    private List<WXComponent> mRefreshs;
    private ScrollStartEndHelper mScrollStartEndHelper;
    private FrameLayout mScrollerView;
    private Map<String, Map<String, WXComponent>> mStickyMap;
    private boolean pageEnable;
    private int pageSize;
    private WXStickyHelper stickyHelper;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class Creator implements ComponentCreator {
        @Override // com.taobao.weex.ui.ComponentCreator
        public WXComponent createInstance(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
            wXSDKInstance.setUseScroller(true);
            return new DCWXScroller(wXSDKInstance, wXVContainer, basicComponentData);
        }
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        private final DCWXHorizontalScrollView scrollView;

        MyGestureDetector(DCWXHorizontalScrollView dCWXHorizontalScrollView) {
            this.scrollView = dCWXHorizontalScrollView;
        }

        public DCWXHorizontalScrollView getScrollView() {
            return this.scrollView;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            int size = ((AbsVContainer) DCWXScroller.this).mChildren.size();
            try {
            } catch (Exception e) {
                WXLogUtils.e("There was an error processing the Fling event:" + e.getMessage());
            }
            if (motionEvent.getX() - motionEvent2.getX() <= 5.0f || Math.abs(f) <= 300.0f) {
                if (motionEvent2.getX() - motionEvent.getX() > 5.0f && Math.abs(f) > 300.0f) {
                    int i = DCWXScroller.this.pageSize;
                    DCWXScroller dCWXScroller = DCWXScroller.this;
                    dCWXScroller.mActiveFeature = dCWXScroller.mActiveFeature > 0 ? DCWXScroller.this.mActiveFeature - 1 : 0;
                    this.scrollView.smoothScrollTo(DCWXScroller.this.mActiveFeature * i, 0);
                    return true;
                }
                return false;
            }
            int i2 = DCWXScroller.this.pageSize;
            DCWXScroller dCWXScroller2 = DCWXScroller.this;
            int i3 = size - 1;
            if (dCWXScroller2.mActiveFeature < i3) {
                i3 = DCWXScroller.this.mActiveFeature + 1;
            }
            dCWXScroller2.mActiveFeature = i3;
            this.scrollView.smoothScrollTo(DCWXScroller.this.mActiveFeature * i2, 0);
            return true;
        }
    }

    @Deprecated
    public DCWXScroller(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, String str, boolean z, BasicComponentData basicComponentData) {
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
            boolean r2 = r5 instanceof com.taobao.weex.ui.component.DCWXScroller
            if (r2 != 0) goto L5e
            com.taobao.weex.ui.component.WXVContainer r2 = r5.getParent()
            boolean r2 = r2 instanceof com.taobao.weex.ui.component.DCWXScroller
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
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.ui.component.DCWXScroller.checkItemVisibleInScroller(com.taobao.weex.ui.component.WXComponent):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean checkRefreshOrLoading(final WXComponent wXComponent) {
        boolean z;
        if (!(wXComponent instanceof WXRefresh) || getHostView() == 0) {
            z = false;
        } else {
            ((BaseBounceView) getHostView()).setOnRefreshListener((WXRefresh) wXComponent);
            this.handler.postDelayed(WXThread.secure(new Runnable() { // from class: com.taobao.weex.ui.component.DCWXScroller.3
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.lang.Runnable
                public void run() {
                    ((BaseBounceView) DCWXScroller.this.getHostView()).setHeaderView(wXComponent);
                }
            }), 100L);
            z = true;
        }
        if (!(wXComponent instanceof WXLoading) || getHostView() == 0) {
            return z;
        }
        ((BaseBounceView) getHostView()).setOnLoadingListener((WXLoading) wXComponent);
        this.handler.postDelayed(WXThread.secure(new Runnable() { // from class: com.taobao.weex.ui.component.DCWXScroller.4
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                ((BaseBounceView) DCWXScroller.this.getHostView()).setFooterView(wXComponent);
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
        fireEvent("scroll", getScrollEvent(i, i2, i3, i4));
    }

    private WXComponent getViewById(String str) {
        if (this.childens.containsKey(str)) {
            return this.childens.get(str);
        }
        for (WXComponent wXComponent : this.childens.values()) {
            if (wXComponent.getAttrs().containsKey("id") && wXComponent.getAttrs().get("id").equals(str)) {
                return wXComponent;
            }
        }
        return null;
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
        if (wXComponent.getAttrs().containsKey("id")) {
            this.childens.put(wXComponent.getAttrs().get("id").toString(), wXComponent);
        }
        if ((wXComponent instanceof WXBaseRefresh) && checkRefreshOrLoading(wXComponent)) {
            this.mRefreshs.add(wXComponent);
        }
        super.addChild(wXComponent, i);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void addEvent(String str) {
        super.addEvent(str);
        if (!DCScrollStartEndHelper.isScrollEvent(str) || getInnerView() == null || this.mHasAddScrollEvent) {
            return;
        }
        this.mHasAddScrollEvent = true;
        if (getInnerView() instanceof DCWXScrollView) {
            ((DCWXScrollView) getInnerView()).addScrollViewListener(new DCWXScrollView.WXScrollViewListener() { // from class: com.taobao.weex.ui.component.DCWXScroller.1
                @Override // io.dcloud.feature.weex_scroller.view.DCWXScrollView.WXScrollViewListener
                public void onScroll(DCWXScrollView dCWXScrollView, int i, int i2) {
                }

                @Override // io.dcloud.feature.weex_scroller.view.DCWXScrollView.WXScrollViewListener
                public void onScrollChanged(DCWXScrollView dCWXScrollView, int i, int i2, int i3, int i4) {
                    DCWXScroller.this.getScrollStartEndHelper().onScrolled(i, i2);
                    if (DCWXScroller.this.getEvents().contains("scroll") && DCWXScroller.this.shouldReport(i, i2)) {
                        DCWXScroller.this.fireScrollEvent(dCWXScrollView.getContentFrame(), i, i2, i3, i4);
                    }
                }

                @Override // io.dcloud.feature.weex_scroller.view.DCWXScrollView.WXScrollViewListener
                public void onScrollStopped(DCWXScrollView dCWXScrollView, int i, int i2) {
                    DCWXScroller.this.getScrollStartEndHelper().onScrolled(i, i2);
                    if (DCWXScroller.this.getEvents().contains("scroll")) {
                        DCWXScroller.this.fireScrollEvent(dCWXScrollView.getContentFrame(), i, i2, 0, 0);
                    }
                }

                @Override // io.dcloud.feature.weex_scroller.view.DCWXScrollView.WXScrollViewListener
                public void onScrollToBottom(DCWXScrollView dCWXScrollView, int i, int i2) {
                    HashMap map = new HashMap(1);
                    map.put("direction", "bottom");
                    HashMap map2 = new HashMap(1);
                    map2.put("detail", map);
                    DCWXScroller.this.fireEvent("scrolltolower", map2);
                }

                @Override // io.dcloud.feature.weex_scroller.view.DCWXScrollView.WXScrollViewListener
                public void onScrollToTop(DCWXScrollView dCWXScrollView, int i, int i2) {
                    HashMap map = new HashMap(1);
                    map.put("direction", "top");
                    HashMap map2 = new HashMap(1);
                    map2.put("detail", map);
                    DCWXScroller.this.fireEvent("scrolltoupper", map2);
                }
            });
        } else if (getInnerView() instanceof DCWXHorizontalScrollView) {
            ((DCWXHorizontalScrollView) getInnerView()).addScrollViewListener(new DCWXHorizontalScrollView.ScrollViewListener() { // from class: com.taobao.weex.ui.component.DCWXScroller.2
                @Override // io.dcloud.feature.weex_scroller.view.DCWXHorizontalScrollView.ScrollViewListener
                public void onScrollChanged(DCWXHorizontalScrollView dCWXHorizontalScrollView, int i, int i2, int i3, int i4) {
                    DCWXScroller.this.getScrollStartEndHelper().onScrolled(i, i2);
                    if (DCWXScroller.this.getEvents().contains("scroll") && DCWXScroller.this.shouldReport(i, i2)) {
                        DCWXScroller.this.fireScrollEvent(dCWXHorizontalScrollView.getContentFrame(), i, i2, i3, i4);
                    }
                }

                @Override // io.dcloud.feature.weex_scroller.view.DCWXHorizontalScrollView.ScrollViewListener
                public void onScrollToBottom() {
                    HashMap map = new HashMap(1);
                    map.put("direction", "bottom");
                    HashMap map2 = new HashMap(1);
                    map2.put("detail", map);
                    DCWXScroller.this.fireEvent("scrolltolower", map2);
                }

                @Override // io.dcloud.feature.weex_scroller.view.DCWXHorizontalScrollView.ScrollViewListener
                public void onScrolltoTop() {
                    HashMap map = new HashMap(1);
                    map.put("direction", "top");
                    HashMap map2 = new HashMap(1);
                    map2.put("detail", map);
                    DCWXScroller.this.fireEvent("scrolltoupper", map2);
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
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
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
        if (getInnerView() != null && (getInnerView() instanceof IWXScroller)) {
            ((IWXScroller) getInnerView()).destroy();
        }
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
        super.destroy();
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
        return getHostView() instanceof DCBounceScrollerView ? ((DCBounceScrollerView) getHostView()).getInnerView() : (ViewGroup) getHostView();
    }

    @Override // com.taobao.weex.ui.component.WXBaseScroller, com.taobao.weex.ui.component.Scrollable
    public int getOrientation() {
        return this.mOrientation;
    }

    public Map<String, Object> getScrollEvent(int i, int i2, int i3, int i4) {
        int scrollX;
        Rect rect = new Rect();
        int scrollY = 0;
        if (!(getInnerView() instanceof DCWXScrollView)) {
            if (getInnerView() instanceof DCWXHorizontalScrollView) {
                rect = ((DCWXHorizontalScrollView) getInnerView()).getContentFrame();
                scrollX = getInnerView().getScrollX();
            }
            HashMap map = new HashMap(2);
            HashMap map2 = new HashMap(6);
            new HashMap(2);
            float instanceViewPortWidthWithFloat = getInstance().getInstanceViewPortWidthWithFloat();
            map2.put("scrollWidth", Float.valueOf(WXViewUtils.getWebPxByWidth(rect.width(), instanceViewPortWidthWithFloat)));
            map2.put("scrollHeight", Float.valueOf(WXViewUtils.getWebPxByWidth(rect.height(), instanceViewPortWidthWithFloat)));
            map2.put(Constants.Name.SCROLL_LEFT, Float.valueOf(WXViewUtils.getWebPxByWidth(scrollX, instanceViewPortWidthWithFloat)));
            map2.put(Constants.Name.SCROLL_TOP, Float.valueOf(WXViewUtils.getWebPxByWidth(scrollY, instanceViewPortWidthWithFloat)));
            map2.put("deltaX", Float.valueOf(WXViewUtils.getWebPxByWidth(i3, instanceViewPortWidthWithFloat)));
            map2.put("deltaY", Float.valueOf(WXViewUtils.getWebPxByWidth(i4, instanceViewPortWidthWithFloat)));
            map.put("detail", map2);
            return map;
        }
        rect = ((DCWXScrollView) getInnerView()).getContentFrame();
        scrollY = getInnerView().getScrollY();
        scrollX = 0;
        HashMap map3 = new HashMap(2);
        HashMap map22 = new HashMap(6);
        new HashMap(2);
        float instanceViewPortWidthWithFloat2 = getInstance().getInstanceViewPortWidthWithFloat();
        map22.put("scrollWidth", Float.valueOf(WXViewUtils.getWebPxByWidth(rect.width(), instanceViewPortWidthWithFloat2)));
        map22.put("scrollHeight", Float.valueOf(WXViewUtils.getWebPxByWidth(rect.height(), instanceViewPortWidthWithFloat2)));
        map22.put(Constants.Name.SCROLL_LEFT, Float.valueOf(WXViewUtils.getWebPxByWidth(scrollX, instanceViewPortWidthWithFloat2)));
        map22.put(Constants.Name.SCROLL_TOP, Float.valueOf(WXViewUtils.getWebPxByWidth(scrollY, instanceViewPortWidthWithFloat2)));
        map22.put("deltaX", Float.valueOf(WXViewUtils.getWebPxByWidth(i3, instanceViewPortWidthWithFloat2)));
        map22.put("deltaY", Float.valueOf(WXViewUtils.getWebPxByWidth(i4, instanceViewPortWidthWithFloat2)));
        map3.put("detail", map22);
        return map3;
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

    @WXComponentProp(name = "enable-back-to-top")
    public void isEnableBackToTop(boolean z) {
        this.canScroll2Top = z;
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

    @Override // com.taobao.weex.ui.component.WXComponent
    protected void onFinishLayout() {
        super.onFinishLayout();
    }

    protected void onLoadMore(FrameLayout frameLayout, int i, int i2) {
        try {
            String loadMoreOffset = getAttrs().getLoadMoreOffset();
            if (TextUtils.isEmpty(loadMoreOffset)) {
                return;
            }
            int realPxByWidth = (int) WXViewUtils.getRealPxByWidth(Float.parseFloat(loadMoreOffset), getInstance().getInstanceViewPortWidth());
            if (frameLayout instanceof DCWXHorizontalScrollView) {
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
            WXLogUtils.d("[DCWXScroller-onScroll] ", e);
        }
    }

    @Override // io.dcloud.feature.weex_scroller.view.DCWXScrollView.WXScrollViewListener
    public void onScroll(DCWXScrollView dCWXScrollView, int i, int i2) {
        onLoadMore(dCWXScrollView, i, i2);
    }

    @Override // io.dcloud.feature.weex_scroller.view.DCWXScrollView.WXScrollViewListener
    public void onScrollChanged(DCWXScrollView dCWXScrollView, int i, int i2, int i3, int i4) {
        procAppear(i, i2, i3, i4);
    }

    @Override // io.dcloud.feature.weex_scroller.view.DCWXScrollView.WXScrollViewListener
    public void onScrollStopped(DCWXScrollView dCWXScrollView, int i, int i2) {
    }

    @Override // io.dcloud.feature.weex_scroller.view.DCWXScrollView.WXScrollViewListener
    public void onScrollToBottom(DCWXScrollView dCWXScrollView, int i, int i2) {
    }

    @Override // io.dcloud.feature.weex_scroller.view.DCWXScrollView.WXScrollViewListener
    public void onScrollToTop(DCWXScrollView dCWXScrollView, int i, int i2) {
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

    @JSMethod
    public void scrollTo(int i) {
        float realPxByWidth = WXViewUtils.getRealPxByWidth(i, getInstance().getInstanceViewPortWidth());
        if (getInnerView() instanceof DCWXScrollView) {
            ((DCWXScrollView) getInnerView()).smoothScrollTo(0, (int) realPxByWidth);
        } else if (getInnerView() instanceof DCWXHorizontalScrollView) {
            ((DCWXHorizontalScrollView) getInnerView()).smoothScrollTo((int) realPxByWidth, 0);
        }
    }

    @JSMethod
    public void scrollToTop() {
        if ((getInnerView() instanceof DCWXScrollView) && this.canScroll2Top) {
            ((DCWXScrollView) getInnerView()).smoothScrollTo(0, 0);
        }
    }

    @WXComponentProp(name = "decelerationRate")
    public void setDecelerationRate(float f) {
        if (getInnerView() instanceof DCWXScrollView) {
            ((DCWXScrollView) getInnerView()).setRate(f);
        }
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

    @WXComponentProp(name = "lowerThreshold")
    public void setLowwerHeight(int i) {
        ViewGroup innerView = getInnerView();
        float realPxByWidth = WXViewUtils.getRealPxByWidth(i, getInstance().getInstanceViewPortWidth());
        if (innerView instanceof DCWXHorizontalScrollView) {
            ((DCWXHorizontalScrollView) innerView).setLowwerLength(realPxByWidth);
        } else if (innerView instanceof DCWXScrollView) {
            ((DCWXScrollView) innerView).setLowwerLength(realPxByWidth);
        }
    }

    @WXComponentProp(name = Constants.Name.OFFSET_ACCURACY)
    public void setOffsetAccuracy(int i) {
        this.mOffsetAccuracy = (int) WXViewUtils.getRealPxByWidth(i, getInstance().getInstanceViewPortWidth());
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
            case "scrollWithAnimation":
                setScrollWithAnimation(WXUtils.getBoolean(obj, Boolean.FALSE).booleanValue());
                break;
            case "offsetAccuracy":
                setOffsetAccuracy(WXUtils.getInteger(obj, 10).intValue());
                return true;
            case "scrollable":
                setScrollable(WXUtils.getBoolean(obj, Boolean.TRUE).booleanValue());
                return true;
            case "scrollX":
            case "scrollY":
                setScrollable(!String.valueOf(obj).equals(AbsoluteConst.FALSE));
                return true;
        }
        return super.setProperty(str, obj);
    }

    @WXComponentProp(name = "scrollIntoView")
    public void setScrollIntoView(final String str) {
        if (!this.isViewLayoutFinished.get()) {
            getInnerView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.taobao.weex.ui.component.DCWXScroller.12
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    if (DCWXScroller.this.getInstance() == null) {
                        return;
                    }
                    WXComponent wXComponentById = WXSDKManager.getInstance().getWXRenderManager().getWXComponentById(DCWXScroller.this.getInstanceId(), str);
                    if (wXComponentById != null) {
                        DCWXScroller.this.scrollTo(wXComponentById, JSONObject.parseObject("{'animated':false}"));
                    }
                    DCWXScroller.this.isViewLayoutFinished.set(true);
                    DCWXScroller.this.handler.postDelayed(new Runnable() { // from class: com.taobao.weex.ui.component.DCWXScroller.12.1
                        @Override // java.lang.Runnable
                        public void run() {
                            DCWXScroller.this.getInnerView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }, 100L);
                }
            });
            return;
        }
        WXComponent wXComponentById = WXSDKManager.getInstance().getWXRenderManager().getWXComponentById(getInstanceId(), str);
        if (wXComponentById != null) {
            scrollTo(wXComponentById, JSONObject.parseObject(StringUtil.format("{'animated':%b}", Boolean.valueOf(this.isAnimation))));
        }
    }

    @WXComponentProp(name = Constants.Name.SCROLL_LEFT)
    public void setScrollLeft(String str) {
        if (getInnerView() instanceof DCWXHorizontalScrollView) {
            final float realPxByWidth = WXViewUtils.getRealPxByWidth(WXUtils.getFloat(str), getInstance().getInstanceViewPortWidth());
            if (!this.isViewLayoutFinished.get()) {
                getInnerView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.taobao.weex.ui.component.DCWXScroller.15
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public void onGlobalLayout() {
                        ObjectAnimator.ofInt(DCWXScroller.this.getInnerView(), "scrollX", DCWXScroller.this.getInnerView().getScrollX(), (int) realPxByWidth).setDuration(1L).start();
                        DCWXScroller.this.isViewLayoutFinished.set(true);
                        DCWXScroller.this.handler.postDelayed(new Runnable() { // from class: com.taobao.weex.ui.component.DCWXScroller.15.1
                            @Override // java.lang.Runnable
                            public void run() {
                                DCWXScroller.this.getInnerView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        }, 100L);
                    }
                });
                return;
            }
            if (this.isAnimation) {
                if (getInnerView() instanceof DCWXHorizontalScrollView) {
                    ((DCWXHorizontalScrollView) getInnerView()).stopScroll();
                }
                ObjectAnimator.ofInt(getInnerView(), "scrollX", getInnerView().getScrollX(), (int) realPxByWidth).setDuration(200L).start();
            } else {
                ((DCWXHorizontalScrollView) getInnerView()).smoothScrollTo((int) realPxByWidth, 0);
            }
            this.isViewLayoutFinished.set(true);
        }
    }

    @WXComponentProp(name = Constants.Name.SCROLL_TOP)
    public void setScrollTop(String str) {
        if (getInnerView() instanceof DCWXScrollView) {
            final float realPxByWidth = WXViewUtils.getRealPxByWidth(WXUtils.getFloat(str), getInstance().getInstanceViewPortWidth());
            if (!this.isViewLayoutFinished.get()) {
                getInnerView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.taobao.weex.ui.component.DCWXScroller.13
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public void onGlobalLayout() {
                        DCWXScroller.this.getInnerView().scrollTo(0, (int) realPxByWidth);
                        DCWXScroller.this.isViewLayoutFinished.set(true);
                        DCWXScroller.this.handler.postDelayed(new Runnable() { // from class: com.taobao.weex.ui.component.DCWXScroller.13.1
                            @Override // java.lang.Runnable
                            public void run() {
                                DCWXScroller.this.getInnerView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        }, 100L);
                    }
                });
                return;
            }
            if (this.isAnimation) {
                if (getInnerView() instanceof DCWXScrollView) {
                    ((DCWXScrollView) getInnerView()).stopScroll();
                }
                ObjectAnimator.ofInt(getInnerView(), "scrollY", getInnerView().getScrollY(), (int) realPxByWidth).setDuration(200L).start();
            } else {
                getInnerView().post(new Runnable() { // from class: com.taobao.weex.ui.component.DCWXScroller.14
                    @Override // java.lang.Runnable
                    public void run() {
                        DCWXScroller.this.getInnerView().scrollTo(0, (int) realPxByWidth);
                    }
                });
            }
            this.isViewLayoutFinished.set(true);
        }
    }

    @WXComponentProp(name = "scrollWithAnimation")
    public void setScrollWithAnimation(boolean z) {
        this.isAnimation = z;
    }

    public void setScrollable(boolean z) {
        this.isScrollable = z;
        ViewGroup innerView = getInnerView();
        if (innerView instanceof DCWXHorizontalScrollView) {
            ((DCWXHorizontalScrollView) innerView).setScrollable(z);
        } else if (innerView instanceof DCWXScrollView) {
            ((DCWXScrollView) innerView).setScrollable(z);
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

    @WXComponentProp(name = "upperThreshold")
    public void setUpperHeight(int i) {
        ViewGroup innerView = getInnerView();
        float realPxByWidth = WXViewUtils.getRealPxByWidth(i, getInstance().getInstanceViewPortWidth());
        if (innerView instanceof DCWXHorizontalScrollView) {
            ((DCWXHorizontalScrollView) innerView).setUpperLength(realPxByWidth);
        } else if (innerView instanceof DCWXScrollView) {
            ((DCWXScrollView) innerView).setUpperLength(realPxByWidth);
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

    public DCWXScroller(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
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
        this.isAnimation = false;
        this.canScroll2Top = false;
        this.mAppearanceComponents = new HashMap();
        this.mStickyMap = new HashMap();
        this.mContentHeight = 0;
        this.mContentWidth = 0;
        this.handler = new Handler(Looper.getMainLooper());
        this.isScrollable = true;
        this.childens = new HashMap();
        this.isViewLayoutFinished = new AtomicBoolean(false);
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
        boolean z;
        boolean z2;
        boolean z3;
        DCBounceScrollerView dCBounceScrollerView;
        scrollDirection = "vertical";
        if (getAttrs().isEmpty()) {
            z = false;
            z3 = true;
            z2 = false;
        } else {
            z = getAttrs().containsKey("scrollX") ? Boolean.parseBoolean(getAttrs().get("scrollX").toString()) : false;
            z2 = getAttrs().containsKey("scrollY") ? Boolean.parseBoolean(getAttrs().get("scrollY").toString()) : false;
            if (z2) {
                z3 = !getAttrs().get("scrollY").equals(AbsoluteConst.FALSE);
            } else if (z) {
                z3 = !getAttrs().get("scrollX").equals(AbsoluteConst.FALSE);
                scrollDirection = Constants.Value.HORIZONTAL;
            } else {
                scrollDirection = getAttrs().containsKey(Constants.Name.SCROLL_DIRECTION) ? getAttrs().getScrollDirection() : "vertical";
                z3 = true;
            }
            Object obj = getAttrs().get(Constants.Name.PAGE_ENABLED);
            this.pageEnable = obj != null && Boolean.parseBoolean(obj.toString());
            Object obj2 = getAttrs().get(Constants.Name.PAGE_SIZE);
            if (obj2 != null) {
                float realPxByWidth = WXViewUtils.getRealPxByWidth(WXUtils.getFloat(obj2), getInstance().getInstanceViewPortWidth());
                if (realPxByWidth != 0.0f) {
                    this.pageSize = (int) realPxByWidth;
                }
            }
        }
        if (Constants.Value.HORIZONTAL.equals(scrollDirection)) {
            this.mOrientation = 0;
            final DCWXHorizontalScrollView dCWXHorizontalScrollView = new DCWXHorizontalScrollView(context);
            dCWXHorizontalScrollView.setWAScroller(this);
            dCWXHorizontalScrollView.setScrollable(z3);
            this.mRealView = new FrameLayout(context);
            dCWXHorizontalScrollView.setScrollViewListener(new DCWXHorizontalScrollView.ScrollViewListener() { // from class: com.taobao.weex.ui.component.DCWXScroller.5
                @Override // io.dcloud.feature.weex_scroller.view.DCWXHorizontalScrollView.ScrollViewListener
                public void onScrollChanged(DCWXHorizontalScrollView dCWXHorizontalScrollView2, int i, int i2, int i3, int i4) {
                    if (DCWXScroller.this.getInstance() == null) {
                        return;
                    }
                    DCWXScroller.this.procAppear(i, i2, i3, i4);
                    DCWXScroller.this.onLoadMore(dCWXHorizontalScrollView2, i, i2);
                }

                @Override // io.dcloud.feature.weex_scroller.view.DCWXHorizontalScrollView.ScrollViewListener
                public void onScrollToBottom() {
                }

                @Override // io.dcloud.feature.weex_scroller.view.DCWXHorizontalScrollView.ScrollViewListener
                public void onScrolltoTop() {
                }
            });
            dCWXHorizontalScrollView.addView(this.mRealView, new FrameLayout.LayoutParams(-1, -1));
            dCWXHorizontalScrollView.setHorizontalScrollBarEnabled(true);
            this.mScrollerView = dCWXHorizontalScrollView;
            dCWXHorizontalScrollView.setScrollBarSize(DensityUtils.dip2px(getInstance().getContext(), 4.0f));
            final View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.taobao.weex.ui.component.DCWXScroller.6
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(final View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    dCWXHorizontalScrollView.post(new Runnable() { // from class: com.taobao.weex.ui.component.DCWXScroller.6.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!DCWXScroller.this.isLayoutRTL()) {
                                AnonymousClass6 anonymousClass6 = AnonymousClass6.this;
                                dCWXHorizontalScrollView.scrollTo(0, this.getScrollY());
                            } else {
                                int measuredWidth = view.getMeasuredWidth();
                                AnonymousClass6 anonymousClass62 = AnonymousClass6.this;
                                dCWXHorizontalScrollView.scrollTo(measuredWidth, this.getScrollY());
                            }
                        }
                    });
                    if (DCWXScroller.this.mRealView != null) {
                        DCWXScroller.this.mRealView.removeOnLayoutChangeListener(this);
                    }
                }
            };
            this.mRealView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.taobao.weex.ui.component.DCWXScroller.7
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(View view) {
                    view.addOnLayoutChangeListener(onLayoutChangeListener);
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(View view) {
                    view.removeOnLayoutChangeListener(onLayoutChangeListener);
                }
            });
            dCBounceScrollerView = dCWXHorizontalScrollView;
            if (this.pageEnable) {
                this.mGestureDetector = new GestureDetector(new MyGestureDetector(dCWXHorizontalScrollView));
                dCWXHorizontalScrollView.setOnTouchListener(new View.OnTouchListener() { // from class: com.taobao.weex.ui.component.DCWXScroller.8
                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (DCWXScroller.this.pageSize == 0) {
                            DCWXScroller.this.pageSize = view.getMeasuredWidth();
                        }
                        if (DCWXScroller.this.mGestureDetector.onTouchEvent(motionEvent)) {
                            return true;
                        }
                        if (motionEvent.getAction() != 1 && motionEvent.getAction() != 3) {
                            return false;
                        }
                        int scrollX = DCWXScroller.this.getScrollX();
                        int i = DCWXScroller.this.pageSize;
                        DCWXScroller.this.mActiveFeature = (scrollX + (i / 2)) / i;
                        dCWXHorizontalScrollView.smoothScrollTo(DCWXScroller.this.mActiveFeature * i, 0);
                        return true;
                    }
                });
                dCBounceScrollerView = dCWXHorizontalScrollView;
            }
        } else {
            this.mOrientation = 1;
            DCBounceScrollerView dCBounceScrollerView2 = new DCBounceScrollerView(context, this.mOrientation, this);
            this.mRealView = new FrameLayout(context);
            DCWXScrollView innerView = dCBounceScrollerView2.getInnerView();
            innerView.addScrollViewListener(this);
            if (z || z2) {
                innerView.setScrollable(z3);
            } else {
                innerView.setScrollable(false);
            }
            innerView.addView(this.mRealView, new FrameLayout.LayoutParams(-1, -1));
            innerView.setVerticalScrollBarEnabled(true);
            this.mScrollerView = innerView;
            innerView.setNestedScrollingEnabled(WXUtils.getBoolean(getAttrs().get(Constants.Name.NEST_SCROLLING_ENABLED), Boolean.TRUE).booleanValue());
            innerView.addScrollViewListener(new DCWXScrollView.WXScrollViewListener() { // from class: com.taobao.weex.ui.component.DCWXScroller.9
                @Override // io.dcloud.feature.weex_scroller.view.DCWXScrollView.WXScrollViewListener
                public void onScroll(DCWXScrollView dCWXScrollView, int i, int i2) {
                    List<OnWXScrollListener> wXScrollListeners;
                    if (DCWXScroller.this.getInstance() == null || (wXScrollListeners = DCWXScroller.this.getInstance().getWXScrollListeners()) == null || wXScrollListeners.size() <= 0) {
                        return;
                    }
                    for (OnWXScrollListener onWXScrollListener : wXScrollListeners) {
                        if (onWXScrollListener != null) {
                            if (!(onWXScrollListener instanceof ICheckBindingScroller)) {
                                onWXScrollListener.onScrolled(dCWXScrollView, i, i2);
                            } else if (((ICheckBindingScroller) onWXScrollListener).isNeedScroller(DCWXScroller.this.getRef(), null)) {
                                onWXScrollListener.onScrolled(dCWXScrollView, i, i2);
                            }
                        }
                    }
                }

                @Override // io.dcloud.feature.weex_scroller.view.DCWXScrollView.WXScrollViewListener
                public void onScrollChanged(DCWXScrollView dCWXScrollView, int i, int i2, int i3, int i4) {
                }

                @Override // io.dcloud.feature.weex_scroller.view.DCWXScrollView.WXScrollViewListener
                public void onScrollStopped(DCWXScrollView dCWXScrollView, int i, int i2) {
                    if (DCWXScroller.this.getInstance() == null) {
                        return;
                    }
                    List<OnWXScrollListener> wXScrollListeners = DCWXScroller.this.getInstance().getWXScrollListeners();
                    if (wXScrollListeners != null && wXScrollListeners.size() > 0) {
                        for (OnWXScrollListener onWXScrollListener : wXScrollListeners) {
                            if (onWXScrollListener != null) {
                                onWXScrollListener.onScrollStateChanged(dCWXScrollView, i, i2, 0);
                            }
                        }
                    }
                    DCWXScroller.this.getScrollStartEndHelper().onScrollStateChanged(0);
                }

                @Override // io.dcloud.feature.weex_scroller.view.DCWXScrollView.WXScrollViewListener
                public void onScrollToBottom(DCWXScrollView dCWXScrollView, int i, int i2) {
                }

                @Override // io.dcloud.feature.weex_scroller.view.DCWXScrollView.WXScrollViewListener
                public void onScrollToTop(DCWXScrollView dCWXScrollView, int i, int i2) {
                }
            });
            dCBounceScrollerView = dCBounceScrollerView2;
        }
        dCBounceScrollerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.taobao.weex.ui.component.DCWXScroller.10
            /* JADX WARN: Type inference failed for: r0v2, types: [android.view.View] */
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                DCWXScroller.this.procAppear(0, 0, 0, 0);
                ?? hostView = DCWXScroller.this.getHostView();
                if (hostView == 0 || DCWXScroller.this.getInstance() == null) {
                    return;
                }
                hostView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        View.OnAttachStateChangeListener onAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: com.taobao.weex.ui.component.DCWXScroller.11
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                DCWXScroller.this.mIsHostAttachedToWindow = true;
                DCWXScroller dCWXScroller = DCWXScroller.this;
                dCWXScroller.procAppear(dCWXScroller.getScrollX(), DCWXScroller.this.getScrollY(), DCWXScroller.this.getScrollX(), DCWXScroller.this.getScrollY());
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                DCWXScroller.this.mIsHostAttachedToWindow = false;
                DCWXScroller.this.dispatchDisappearEvent();
            }
        };
        this.mOnAttachStateChangeListener = onAttachStateChangeListener;
        dCBounceScrollerView.addOnAttachStateChangeListener(onAttachStateChangeListener);
        return dCBounceScrollerView;
    }

    public void scrollBy(final int i, final int i2, final boolean z) {
        if (getInnerView() == null) {
            return;
        }
        getInnerView().postDelayed(new Runnable() { // from class: com.taobao.weex.ui.component.DCWXScroller.16
            @Override // java.lang.Runnable
            public void run() {
                DCWXScroller dCWXScroller = DCWXScroller.this;
                if (dCWXScroller.mOrientation == 1) {
                    if (z) {
                        ((DCWXScrollView) dCWXScroller.getInnerView()).smoothScrollBy(0, i2);
                    } else {
                        ((DCWXScrollView) dCWXScroller.getInnerView()).scrollBy(0, i2);
                    }
                } else if (z) {
                    ((DCWXHorizontalScrollView) dCWXScroller.getInnerView()).smoothScrollBy(i, 0);
                } else {
                    ((DCWXHorizontalScrollView) dCWXScroller.getInnerView()).scrollBy(i, 0);
                }
                DCWXScroller.this.getInnerView().invalidate();
            }
        }, 16L);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void setMarginsSupportRTL(ViewGroup.MarginLayoutParams marginLayoutParams, int i, int i2, int i3, int i4) {
        marginLayoutParams.setMargins(i, i2, i3, i4);
        marginLayoutParams.setMarginStart(i);
        marginLayoutParams.setMarginEnd(i3);
    }

    @JSMethod
    public void scrollTo(String str, JSCallback jSCallback) {
        float realPxByWidth = WXViewUtils.getRealPxByWidth(WXUtils.getFloat(JSON.parseObject(str).getString(Constants.Name.SCROLL_TOP)), getInstance().getInstanceViewPortWidth());
        if (getInnerView() instanceof DCWXScrollView) {
            ((DCWXScrollView) getInnerView()).smoothScrollTo(0, (int) realPxByWidth);
        } else if (getInnerView() instanceof DCWXHorizontalScrollView) {
            ((DCWXHorizontalScrollView) getInnerView()).smoothScrollTo((int) realPxByWidth, 0);
        }
        if (jSCallback != null) {
            HashMap map = new HashMap();
            map.put("type", WXImage.SUCCEED);
            jSCallback.invoke(map);
        }
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
                    realPxByWidth = WXViewUtils.getRealPxByWidth(Float.parseFloat(string), getInstance().getInstanceViewPortWidth());
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
            if (getInnerView().getChildCount() > 0) {
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

    @Override // com.taobao.weex.ui.component.WXBaseScroller
    public Map<String, Object> getScrollEvent(int i, int i2) {
        Rect rect = new Rect();
        if (getInnerView() instanceof DCWXScrollView) {
            rect = ((DCWXScrollView) getInnerView()).getContentFrame();
        } else if (getInnerView() instanceof DCWXHorizontalScrollView) {
            rect = ((DCWXHorizontalScrollView) getInnerView()).getContentFrame();
        }
        HashMap map = new HashMap(2);
        HashMap map2 = new HashMap(2);
        HashMap map3 = new HashMap(2);
        float instanceViewPortWidthWithFloat = getInstance().getInstanceViewPortWidthWithFloat();
        map2.put("width", Float.valueOf(WXViewUtils.getWebPxByWidth(rect.width(), instanceViewPortWidthWithFloat)));
        map2.put("height", Float.valueOf(WXViewUtils.getWebPxByWidth(rect.height(), instanceViewPortWidthWithFloat)));
        map3.put(Constants.Name.X, Float.valueOf(WXViewUtils.getWebPxByWidth(i, instanceViewPortWidthWithFloat)));
        map3.put(Constants.Name.Y, Float.valueOf(WXViewUtils.getWebPxByWidth(i2, instanceViewPortWidthWithFloat)));
        map.put(Constants.Name.CONTENT_SIZE, map2);
        map.put(Constants.Name.CONTENT_OFFSET, map3);
        return map;
    }
}
