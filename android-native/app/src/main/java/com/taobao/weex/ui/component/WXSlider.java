package com.taobao.weex.ui.component;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.viewpager.widget.ViewPager;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.annotation.Component;
import com.taobao.weex.common.Constants;
import com.taobao.weex.dom.WXEvent;
import com.taobao.weex.ui.ComponentCreator;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.list.template.TemplateDom;
import com.taobao.weex.ui.view.BaseFrameLayout;
import com.taobao.weex.ui.view.WXCircleIndicator;
import com.taobao.weex.ui.view.WXCirclePageAdapter;
import com.taobao.weex.ui.view.WXCircleViewPager;
import com.taobao.weex.ui.view.gesture.WXGestureType;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.feature.uniapp.ui.component.AbsVContainer;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
@Component(lazyload = false)
/* loaded from: classes.dex */
public class WXSlider extends WXVContainer<FrameLayout> {
    public static final String INDEX = "index";
    public static final String INFINITE = "infinite";
    private int initIndex;
    private Runnable initRunnable;
    private boolean isInfinite;
    private boolean keepIndex;
    protected WXCirclePageAdapter mAdapter;
    protected WXIndicator mIndicator;
    protected ViewPager.OnPageChangeListener mPageChangeListener;
    protected boolean mShowIndicators;
    WXCircleViewPager mViewPager;
    private float offsetXAccuracy;
    Map<String, Object> params;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class Creator implements ComponentCreator {
        @Override // com.taobao.weex.ui.ComponentCreator
        public WXComponent createInstance(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
            return new WXSlider(wXSDKInstance, wXVContainer, basicComponentData);
        }
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    private static class FlingGestureListener extends GestureDetector.SimpleOnGestureListener {
        private WeakReference<WXCircleViewPager> pagerRef;
        private static final int SWIPE_MIN_DISTANCE = WXViewUtils.dip2px(50.0f);
        private static final int SWIPE_MAX_OFF_PATH = WXViewUtils.dip2px(250.0f);
        private static final int SWIPE_THRESHOLD_VELOCITY = WXViewUtils.dip2px(200.0f);

        FlingGestureListener(WXCircleViewPager wXCircleViewPager) {
            this.pagerRef = new WeakReference<>(wXCircleViewPager);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            WXCircleViewPager wXCircleViewPager = this.pagerRef.get();
            if (wXCircleViewPager == null) {
                return false;
            }
            if (Math.abs(motionEvent.getY() - motionEvent2.getY()) > SWIPE_MAX_OFF_PATH) {
                return false;
            }
            float x = motionEvent.getX() - motionEvent2.getX();
            float f3 = SWIPE_MIN_DISTANCE;
            if (x > f3 && Math.abs(f) > SWIPE_THRESHOLD_VELOCITY && wXCircleViewPager.superGetCurrentItem() == 1) {
                wXCircleViewPager.setCurrentItem(0, false);
                return true;
            }
            if (motionEvent2.getX() - motionEvent.getX() > f3 && Math.abs(f) > SWIPE_THRESHOLD_VELOCITY && wXCircleViewPager.superGetCurrentItem() == 0) {
                wXCircleViewPager.setCurrentItem(1, false);
                return true;
            }
            return false;
        }
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    protected static class SliderOnScrollListener implements ViewPager.OnPageChangeListener {
        private float lastPositionOffset = 99.0f;
        private int selectedPosition;
        private WXSlider target;

        public SliderOnScrollListener(WXSlider wXSlider) {
            this.target = wXSlider;
            this.selectedPosition = wXSlider.mViewPager.superGetCurrentItem();
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
            if (i == 0) {
                this.lastPositionOffset = 99.0f;
                this.target.fireEvent(Constants.Event.SCROLL_END);
            } else {
                if (i != 1) {
                    return;
                }
                this.target.fireEvent(Constants.Event.SCROLL_START);
            }
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
            float f2 = this.lastPositionOffset;
            if (f2 == 99.0f) {
                this.lastPositionOffset = f;
                return;
            }
            if (Math.abs(f - f2) >= this.target.offsetXAccuracy) {
                int i3 = this.selectedPosition;
                if (i == i3) {
                    HashMap map = new HashMap(1);
                    map.put(Constants.Name.OFFSET_X_RATIO, Float.valueOf(-f));
                    this.target.fireEvent("scroll", map);
                } else if (i < i3) {
                    HashMap map2 = new HashMap(1);
                    map2.put(Constants.Name.OFFSET_X_RATIO, Float.valueOf(1.0f - f));
                    this.target.fireEvent("scroll", map2);
                }
                this.lastPositionOffset = f;
            }
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            this.selectedPosition = i;
        }
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    protected class SliderPageChangeListener implements ViewPager.OnPageChangeListener {
        private int lastPos = -1;

        protected SliderPageChangeListener() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
            FrameLayout frameLayout = (FrameLayout) WXSlider.this.getHostView();
            if (frameLayout != null) {
                frameLayout.invalidate();
            }
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v13, types: [android.view.View] */
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            if (WXSlider.this.mAdapter.getRealPosition(i) == this.lastPos) {
                return;
            }
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.d("onPageSelected >>>>" + WXSlider.this.mAdapter.getRealPosition(i) + " lastPos: " + this.lastPos);
            }
            WXCirclePageAdapter wXCirclePageAdapter = WXSlider.this.mAdapter;
            if (wXCirclePageAdapter == null || wXCirclePageAdapter.getRealCount() == 0) {
                return;
            }
            int realPosition = WXSlider.this.mAdapter.getRealPosition(i);
            if (((AbsVContainer) WXSlider.this).mChildren == null || realPosition >= ((AbsVContainer) WXSlider.this).mChildren.size() || WXSlider.this.getEvents().size() == 0) {
                return;
            }
            WXEvent events = WXSlider.this.getEvents();
            String ref = WXSlider.this.getRef();
            if (events.contains(Constants.Event.CHANGE) && WXViewUtils.onScreenArea(WXSlider.this.getHostView())) {
                WXSlider.this.params.put("index", Integer.valueOf(realPosition));
                HashMap map = new HashMap();
                HashMap map2 = new HashMap();
                map2.put("index", Integer.valueOf(realPosition));
                map.put(TemplateDom.KEY_ATTRS, map2);
                WXSDKManager.getInstance().fireEvent(WXSlider.this.getInstanceId(), ref, Constants.Event.CHANGE, WXSlider.this.params, map);
            }
            WXSlider.this.mViewPager.requestLayout();
            ((FrameLayout) WXSlider.this.getHostView()).invalidate();
            this.lastPos = WXSlider.this.mAdapter.getRealPosition(i);
        }
    }

    @Deprecated
    public WXSlider(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, String str, boolean z, BasicComponentData basicComponentData) {
        this(wXSDKInstance, wXVContainer, basicComponentData);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getInitIndex() {
        int iIntValue = WXUtils.getInteger(getAttrs().get("index"), Integer.valueOf(this.initIndex)).intValue();
        WXCirclePageAdapter wXCirclePageAdapter = this.mAdapter;
        if (wXCirclePageAdapter == null || wXCirclePageAdapter.getCount() == 0) {
            return 0;
        }
        return iIntValue >= this.mAdapter.getRealCount() ? iIntValue % this.mAdapter.getRealCount() : iIntValue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getRealIndex(int i) {
        if (this.mAdapter.getRealCount() > 0) {
            if (i >= this.mAdapter.getRealCount()) {
                i = this.mAdapter.getRealCount() - 1;
            }
            if (isLayoutRTL()) {
                return (this.mAdapter.getRealCount() - 1) - i;
            }
        }
        return i;
    }

    private void hackTwoItemsInfiniteScroll() {
        WXCirclePageAdapter wXCirclePageAdapter;
        if (this.mViewPager == null || (wXCirclePageAdapter = this.mAdapter) == null || !this.isInfinite) {
            return;
        }
        if (wXCirclePageAdapter.getRealCount() != 2) {
            this.mViewPager.setOnTouchListener(null);
        } else {
            final GestureDetector gestureDetector = new GestureDetector(getContext(), new FlingGestureListener(this.mViewPager));
            this.mViewPager.setOnTouchListener(new View.OnTouchListener() { // from class: com.taobao.weex.ui.component.WXSlider.2
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return gestureDetector.onTouchEvent(motionEvent);
                }
            });
        }
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void addEvent(String str) {
        WXCircleViewPager wXCircleViewPager;
        super.addEvent(str);
        if (!"scroll".equals(str) || (wXCircleViewPager = this.mViewPager) == null) {
            return;
        }
        wXCircleViewPager.addOnPageChangeListener(new SliderOnScrollListener(this));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void addIndicator(WXIndicator wXIndicator) {
        FrameLayout frameLayout = (FrameLayout) getHostView();
        if (frameLayout == null) {
            return;
        }
        this.mIndicator = wXIndicator;
        WXCircleIndicator hostView = wXIndicator.getHostView();
        if (hostView != null) {
            hostView.setCircleViewPager(this.mViewPager);
            frameLayout.addView(hostView);
        }
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public void addSubView(View view, int i) throws Resources.NotFoundException {
        WXCirclePageAdapter wXCirclePageAdapter;
        if (view == null || (wXCirclePageAdapter = this.mAdapter) == null || (view instanceof WXCircleIndicator)) {
            return;
        }
        wXCirclePageAdapter.addPageView(view);
        hackTwoItemsInfiniteScroll();
        if (this.initIndex != -1 && this.mAdapter.getRealCount() > this.initIndex) {
            if (this.initRunnable == null) {
                this.initRunnable = new Runnable() { // from class: com.taobao.weex.ui.component.WXSlider.1
                    @Override // java.lang.Runnable
                    public void run() throws Resources.NotFoundException {
                        WXSlider wXSlider = WXSlider.this;
                        wXSlider.initIndex = wXSlider.getInitIndex();
                        WXSlider wXSlider2 = WXSlider.this;
                        wXSlider2.mViewPager.setCurrentItem(wXSlider2.getRealIndex(wXSlider2.initIndex));
                        WXSlider.this.initIndex = -1;
                        WXSlider.this.initRunnable = null;
                    }
                };
            }
            this.mViewPager.removeCallbacks(this.initRunnable);
            this.mViewPager.postDelayed(this.initRunnable, 50L);
        } else if (!this.keepIndex) {
            this.mViewPager.setCurrentItem(getRealIndex(0));
        }
        WXIndicator wXIndicator = this.mIndicator;
        if (wXIndicator != null) {
            wXIndicator.getHostView().forceLayout();
            this.mIndicator.getHostView().requestLayout();
        }
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public boolean containsGesture(WXGestureType wXGestureType) {
        return super.containsGesture(wXGestureType);
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer, com.taobao.weex.ui.component.WXComponent
    public void destroy() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.destroy();
        WXCircleViewPager wXCircleViewPager = this.mViewPager;
        if (wXCircleViewPager != null) {
            wXCircleViewPager.stopAutoScroll();
            this.mViewPager.removeAllViews();
            this.mViewPager.destory();
        }
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public ViewGroup.LayoutParams getChildLayoutParams(WXComponent wXComponent, View view, int i, int i2, int i3, int i4, int i5, int i6) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new FrameLayout.LayoutParams(i, i2);
        } else {
            layoutParams.width = i;
            layoutParams.height = i2;
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            if (wXComponent instanceof WXIndicator) {
                setMarginsSupportRTL((ViewGroup.MarginLayoutParams) layoutParams, i3, i5, i4, i6);
                return layoutParams;
            }
            setMarginsSupportRTL((ViewGroup.MarginLayoutParams) layoutParams, 0, 0, 0, 0);
        }
        return layoutParams;
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer, com.taobao.weex.ui.component.WXComponent, com.taobao.weex.IWXActivityStateListener
    public void onActivityResume() {
        super.onActivityResume();
        WXCircleViewPager wXCircleViewPager = this.mViewPager;
        if (wXCircleViewPager == null || !wXCircleViewPager.isAutoScroll()) {
            return;
        }
        this.mViewPager.startAutoScroll();
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer, com.taobao.weex.ui.component.WXComponent, com.taobao.weex.IWXActivityStateListener
    public void onActivityStop() {
        super.onActivityStop();
        WXCircleViewPager wXCircleViewPager = this.mViewPager;
        if (wXCircleViewPager != null) {
            wXCircleViewPager.pauseAutoScroll();
        }
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public void remove(WXComponent wXComponent, boolean z) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        WXCirclePageAdapter wXCirclePageAdapter;
        if (wXComponent == null || wXComponent.getHostView() == null || (wXCirclePageAdapter = this.mAdapter) == null) {
            return;
        }
        wXCirclePageAdapter.removePageView(wXComponent.getHostView());
        hackTwoItemsInfiniteScroll();
        super.remove(wXComponent, z);
    }

    @WXComponentProp(name = Constants.Name.AUTO_PLAY)
    public void setAutoPlay(String str) {
        if (TextUtils.isEmpty(str) || str.equals(AbsoluteConst.FALSE)) {
            this.mViewPager.stopAutoScroll();
        } else {
            this.mViewPager.stopAutoScroll();
            this.mViewPager.startAutoScroll();
        }
    }

    @WXComponentProp(name = "index")
    public void setIndex(int i) throws Resources.NotFoundException {
        WXCirclePageAdapter wXCirclePageAdapter;
        WXCirclePageAdapter wXCirclePageAdapter2;
        if (this.mViewPager == null || (wXCirclePageAdapter = this.mAdapter) == null) {
            return;
        }
        if (i >= wXCirclePageAdapter.getRealCount() || i < 0) {
            this.initIndex = i;
            return;
        }
        int realIndex = getRealIndex(i);
        this.mViewPager.setCurrentItem(realIndex);
        WXIndicator wXIndicator = this.mIndicator;
        if (wXIndicator == null || wXIndicator.getHostView() == null || this.mIndicator.getHostView().getRealCurrentItem() == realIndex) {
            return;
        }
        WXLogUtils.d("setIndex >>>> correction indicator to " + realIndex);
        this.mIndicator.getHostView().setRealCurrentItem(realIndex);
        this.mIndicator.getHostView().invalidate();
        ViewPager.OnPageChangeListener onPageChangeListener = this.mPageChangeListener;
        if (onPageChangeListener == null || (wXCirclePageAdapter2 = this.mAdapter) == null) {
            return;
        }
        onPageChangeListener.onPageSelected(wXCirclePageAdapter2.getFirst() + realIndex);
    }

    @WXComponentProp(name = "interval")
    public void setInterval(int i) {
        WXCircleViewPager wXCircleViewPager = this.mViewPager;
        if (wXCircleViewPager == null || i <= 0) {
            return;
        }
        wXCircleViewPager.setIntervalTime(i);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void setLayout(WXComponent wXComponent) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        WXCirclePageAdapter wXCirclePageAdapter = this.mAdapter;
        if (wXCirclePageAdapter != null) {
            wXCirclePageAdapter.setLayoutDirectionRTL(isLayoutRTL());
        }
        super.setLayout(wXComponent);
    }

    @WXComponentProp(name = Constants.Name.OFFSET_X_ACCURACY)
    public void setOffsetXAccuracy(float f) {
        this.offsetXAccuracy = f;
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected boolean setProperty(String str, Object obj) throws Throwable {
        str.getClass();
        str.hashCode();
        switch (str) {
            case "keepIndex":
                this.keepIndex = WXUtils.getBoolean(obj, Boolean.FALSE).booleanValue();
                return true;
            case "scrollable":
                setScrollable(WXUtils.getBoolean(obj, Boolean.TRUE).booleanValue());
                return true;
            case "index":
                Integer integer = WXUtils.getInteger(obj, null);
                if (integer != null) {
                    setIndex(integer.intValue());
                }
                return true;
            case "value":
                String string = WXUtils.getString(obj, null);
                if (string != null) {
                    setValue(string);
                }
                return true;
            case "interval":
                Integer integer2 = WXUtils.getInteger(obj, null);
                if (integer2 != null) {
                    setInterval(integer2.intValue());
                }
                return true;
            case "showIndicators":
                String string2 = WXUtils.getString(obj, null);
                if (string2 != null) {
                    setShowIndicators(string2);
                }
                return true;
            case "autoPlay":
                String string3 = WXUtils.getString(obj, null);
                if (string3 != null) {
                    setAutoPlay(string3);
                }
                return true;
            case "offsetXAccuracy":
                Float f = WXUtils.getFloat(obj, Float.valueOf(0.1f));
                if (f.floatValue() != 0.0f) {
                    setOffsetXAccuracy(f.floatValue());
                }
                return true;
            default:
                return super.setProperty(str, obj);
        }
    }

    @WXComponentProp(name = Constants.Name.SCROLLABLE)
    public void setScrollable(boolean z) {
        WXCircleViewPager wXCircleViewPager = this.mViewPager;
        if (wXCircleViewPager == null || this.mAdapter == null) {
            return;
        }
        wXCircleViewPager.setScrollable(z);
    }

    @WXComponentProp(name = Constants.Name.SHOW_INDICATORS)
    public void setShowIndicators(String str) {
        if (TextUtils.isEmpty(str) || str.equals(AbsoluteConst.FALSE)) {
            this.mShowIndicators = false;
        } else {
            this.mShowIndicators = true;
        }
        WXIndicator wXIndicator = this.mIndicator;
        if (wXIndicator == null) {
            return;
        }
        wXIndicator.setShowIndicators(this.mShowIndicators);
    }

    @WXComponentProp(name = "value")
    @Deprecated
    public void setValue(String str) throws Resources.NotFoundException {
        if (str == null || getHostView() == 0) {
            return;
        }
        try {
            setIndex(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            WXLogUtils.e("", e);
        }
    }

    public WXSlider(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, basicComponentData);
        this.isInfinite = true;
        this.params = new HashMap();
        this.offsetXAccuracy = 0.1f;
        this.initIndex = -1;
        this.keepIndex = false;
        this.mPageChangeListener = new SliderPageChangeListener();
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer, com.taobao.weex.ui.component.WXComponent
    public ViewGroup getRealView() {
        return this.mViewPager;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXComponent
    public BaseFrameLayout initComponentHostView(Context context) throws Resources.NotFoundException {
        BaseFrameLayout baseFrameLayout = new BaseFrameLayout(context);
        if (getAttrs() != null) {
            this.isInfinite = WXUtils.getBoolean(getAttrs().get("infinite"), Boolean.TRUE).booleanValue();
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        WXCircleViewPager wXCircleViewPager = new WXCircleViewPager(context);
        this.mViewPager = wXCircleViewPager;
        wXCircleViewPager.setCircle(this.isInfinite);
        this.mViewPager.setLayoutParams(layoutParams);
        WXCirclePageAdapter wXCirclePageAdapter = new WXCirclePageAdapter(this.isInfinite);
        this.mAdapter = wXCirclePageAdapter;
        this.mViewPager.setAdapter(wXCirclePageAdapter);
        baseFrameLayout.addView(this.mViewPager);
        this.mViewPager.addOnPageChangeListener(this.mPageChangeListener);
        registerActivityStateListener();
        return baseFrameLayout;
    }
}
