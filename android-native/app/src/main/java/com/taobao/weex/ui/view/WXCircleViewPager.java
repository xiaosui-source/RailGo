package com.taobao.weex.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import androidx.viewpager.widget.ViewPager;
import com.taobao.weex.ui.view.gesture.WXGesture;
import com.taobao.weex.ui.view.gesture.WXGestureObservable;
import com.taobao.weex.utils.WXLogUtils;
import java.lang.reflect.Field;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXCircleViewPager extends ViewPager implements WXGestureObservable {
    private final int SCROLL_TO_NEXT;
    private long intervalTime;
    private boolean isAutoScroll;
    private Handler mAutoScrollHandler;
    private WXSmoothScroller mScroller;
    private int mState;
    private boolean needLoop;
    private boolean scrollable;
    private WXGesture wxGesture;

    public WXCircleViewPager(Context context) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        super(context);
        this.SCROLL_TO_NEXT = 1;
        this.intervalTime = 3000L;
        this.needLoop = true;
        this.scrollable = true;
        this.mState = 0;
        this.mAutoScrollHandler = new Handler(Looper.getMainLooper()) { // from class: com.taobao.weex.ui.view.WXCircleViewPager.1
            @Override // android.os.Handler
            public void handleMessage(Message message) throws Resources.NotFoundException {
                if (message.what != 1) {
                    super.handleMessage(message);
                    return;
                }
                WXLogUtils.d("[CircleViewPager] trigger auto play action");
                WXCircleViewPager.this.showNextItem();
                sendEmptyMessageDelayed(1, WXCircleViewPager.this.intervalTime);
            }
        };
        init();
    }

    private void init() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        setOverScrollMode(2);
        addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.taobao.weex.ui.view.WXCircleViewPager.2
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) throws Resources.NotFoundException {
                WXCircleViewPager.this.mState = i;
                WXCirclePageAdapter circlePageAdapter = WXCircleViewPager.this.getCirclePageAdapter();
                int currentItem = WXCircleViewPager.super.getCurrentItem();
                if (WXCircleViewPager.this.needLoop && i == 0 && circlePageAdapter.getCount() > 1) {
                    if (currentItem == circlePageAdapter.getCount() - 1) {
                        WXCircleViewPager.this.superSetCurrentItem(1, false);
                        circlePageAdapter.getViews().get(0).setTranslationY(0.0f);
                        circlePageAdapter.getViews().get(0).setTranslationX(0.0f);
                    } else if (currentItem == 0) {
                        WXCircleViewPager.this.superSetCurrentItem(circlePageAdapter.getCount() - 2, false);
                        try {
                            circlePageAdapter.getViews().get(circlePageAdapter.getCount() - 3).setTranslationY(0.0f);
                            circlePageAdapter.getViews().get(circlePageAdapter.getCount() - 3).setTranslationX(0.0f);
                        } catch (Exception unused) {
                        }
                    }
                }
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
            }
        });
        postInitViewPager();
    }

    private void postInitViewPager() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (isInEditMode()) {
            return;
        }
        try {
            Field declaredField = ViewPager.class.getDeclaredField("mScroller");
            declaredField.setAccessible(true);
            Field declaredField2 = ViewPager.class.getDeclaredField("sInterpolator");
            declaredField2.setAccessible(true);
            WXSmoothScroller wXSmoothScroller = new WXSmoothScroller(getContext(), (Interpolator) declaredField2.get(null));
            this.mScroller = wXSmoothScroller;
            declaredField.set(this, wXSmoothScroller);
        } catch (Exception e) {
            WXLogUtils.e("[CircleViewPager] postInitViewPager: ", e);
        }
    }

    private void setRealCurrentItem(int i, boolean z) throws Resources.NotFoundException {
        superSetCurrentItem(((WXCirclePageAdapter) getAdapter()).getFirst() + i, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNextItem() throws Resources.NotFoundException {
        if (getCirclePageAdapter() == null || !getCirclePageAdapter().isRTL) {
            if (this.needLoop || superGetCurrentItem() != getRealCount() - 1) {
                if (getRealCount() == 2 && superGetCurrentItem() == 1) {
                    superSetCurrentItem(0, true);
                    return;
                } else {
                    superSetCurrentItem(superGetCurrentItem() + 1, true);
                    return;
                }
            }
            return;
        }
        if (this.needLoop || superGetCurrentItem() != 0) {
            if (getRealCount() == 2 && superGetCurrentItem() == 0) {
                superSetCurrentItem(1, true);
            } else {
                superSetCurrentItem(superGetCurrentItem() - 1, true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void superSetCurrentItem(int i, boolean z) throws Resources.NotFoundException {
        try {
            if (Math.abs(i - getRealCurrentItem()) > 3 && this.needLoop && getRealCount() > 2 && i == getCirclePageAdapter().getCount() - 2) {
                super.setCurrentItem(i - 2, false);
            }
        } catch (Exception unused) {
        }
        try {
            super.setCurrentItem(i, z);
        } catch (IllegalStateException e) {
            WXLogUtils.e(e.toString());
            if (getAdapter() != null) {
                getAdapter().notifyDataSetChanged();
                super.setCurrentItem(i, z);
            }
        }
    }

    public void destory() {
        this.mAutoScrollHandler.removeCallbacksAndMessages(null);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0010  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x001e  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean dispatchTouchEvent(android.view.MotionEvent r5) {
        /*
            r4 = this;
            int r0 = r5.getAction()
            if (r0 == 0) goto L1e
            r1 = 1
            if (r0 == r1) goto L10
            r2 = 2
            if (r0 == r2) goto L1e
            r2 = 3
            if (r0 == r2) goto L10
            goto L24
        L10:
            boolean r0 = r4.isAutoScroll()
            if (r0 == 0) goto L24
            android.os.Handler r0 = r4.mAutoScrollHandler
            long r2 = r4.intervalTime
            r0.sendEmptyMessageDelayed(r1, r2)
            goto L24
        L1e:
            android.os.Handler r0 = r4.mAutoScrollHandler
            r1 = 0
            r0.removeCallbacksAndMessages(r1)
        L24:
            boolean r0 = super.dispatchTouchEvent(r5)     // Catch: java.lang.Exception -> L33
            com.taobao.weex.ui.view.gesture.WXGesture r1 = r4.wxGesture     // Catch: java.lang.Exception -> L33
            if (r1 == 0) goto L32
            boolean r5 = r1.onTouch(r4, r5)     // Catch: java.lang.Exception -> L33
            r5 = r5 | r0
            return r5
        L32:
            return r0
        L33:
            r5 = 0
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.ui.view.WXCircleViewPager.dispatchTouchEvent(android.view.MotionEvent):boolean");
    }

    public WXCirclePageAdapter getCirclePageAdapter() {
        return (WXCirclePageAdapter) getAdapter();
    }

    @Override // androidx.viewpager.widget.ViewPager
    public int getCurrentItem() {
        return getRealCurrentItem();
    }

    @Override // com.taobao.weex.ui.view.gesture.WXGestureObservable
    public WXGesture getGestureListener() {
        return this.wxGesture;
    }

    public long getIntervalTime() {
        return this.intervalTime;
    }

    public int getRealCount() {
        return ((WXCirclePageAdapter) getAdapter()).getRealCount();
    }

    public int getRealCurrentItem() {
        return ((WXCirclePageAdapter) getAdapter()).getRealPosition(super.getCurrentItem());
    }

    public WXSmoothScroller getmScroller() {
        return this.mScroller;
    }

    public boolean isAutoScroll() {
        return this.isAutoScroll;
    }

    public boolean isScrollable() {
        return this.scrollable;
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        try {
            if (this.scrollable) {
                if (super.onInterceptTouchEvent(motionEvent)) {
                    return true;
                }
            }
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    protected void onMeasure(int i, int i2) throws Resources.NotFoundException {
        try {
            super.onMeasure(i, i2);
        } catch (IllegalStateException e) {
            WXLogUtils.e(e.toString());
            if (getAdapter() != null) {
                getAdapter().notifyDataSetChanged();
                super.onMeasure(i, i2);
            }
        }
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.scrollable) {
            return super.onTouchEvent(motionEvent);
        }
        return false;
    }

    public void pauseAutoScroll() {
        this.mAutoScrollHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.taobao.weex.ui.view.gesture.WXGestureObservable
    public void registerGestureListener(WXGesture wXGesture) {
        this.wxGesture = wXGesture;
    }

    @Override // android.view.View
    public void scrollTo(int i, int i2) {
        if (this.scrollable || this.mState != 1) {
            super.scrollTo(i, i2);
        }
    }

    public void setCircle(boolean z) {
        this.needLoop = z;
    }

    public void setCirclePageAdapter(WXCirclePageAdapter wXCirclePageAdapter) throws Resources.NotFoundException {
        setAdapter(wXCirclePageAdapter);
    }

    @Override // androidx.viewpager.widget.ViewPager
    public void setCurrentItem(int i) throws Resources.NotFoundException {
        setRealCurrentItem(i, true);
    }

    public void setIntervalTime(long j) {
        this.intervalTime = j;
    }

    public void setScrollable(boolean z) {
        this.scrollable = z;
    }

    public void startAutoScroll() {
        this.isAutoScroll = true;
        this.mAutoScrollHandler.removeCallbacksAndMessages(null);
        this.mAutoScrollHandler.sendEmptyMessageDelayed(1, this.intervalTime);
    }

    public void stopAutoScroll() {
        this.isAutoScroll = false;
        this.mAutoScrollHandler.removeCallbacksAndMessages(null);
    }

    public int superGetCurrentItem() {
        return super.getCurrentItem();
    }

    @Override // androidx.viewpager.widget.ViewPager
    public void setCurrentItem(int i, boolean z) throws Resources.NotFoundException {
        setRealCurrentItem(i, z);
    }

    public WXCircleViewPager(Context context, AttributeSet attributeSet) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        super(context, attributeSet);
        this.SCROLL_TO_NEXT = 1;
        this.intervalTime = 3000L;
        this.needLoop = true;
        this.scrollable = true;
        this.mState = 0;
        this.mAutoScrollHandler = new Handler(Looper.getMainLooper()) { // from class: com.taobao.weex.ui.view.WXCircleViewPager.1
            @Override // android.os.Handler
            public void handleMessage(Message message) throws Resources.NotFoundException {
                if (message.what != 1) {
                    super.handleMessage(message);
                    return;
                }
                WXLogUtils.d("[CircleViewPager] trigger auto play action");
                WXCircleViewPager.this.showNextItem();
                sendEmptyMessageDelayed(1, WXCircleViewPager.this.intervalTime);
            }
        };
        init();
    }
}
