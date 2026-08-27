package io.dcloud.feature.weex_scroller.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChildHelper;
import com.taobao.weex.common.WXThread;
import com.taobao.weex.ui.component.DCWXScroller;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.view.IWXScroller;
import com.taobao.weex.ui.view.gesture.WXGesture;
import com.taobao.weex.ui.view.gesture.WXGestureObservable;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXReflectionUtils;
import com.taobao.weex.utils.WXViewUtils;
import io.dcloud.common.ui.blur.AppEventForBlurManager;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class DCWXScrollView extends ScrollView implements Handler.Callback, IWXScroller, WXGestureObservable, NestedScrollingChild {
    private NestedScrollingChildHelper childHelper;
    private int[] consumed;
    private float decelerationRate;
    private boolean isExecScrollerTask;
    private boolean isTouch;
    private float lowwer;
    private int mCheckTime;
    private View mCurrentStickyView;
    private boolean mHasNotDoneActionDown;
    private int mInitialPosition;
    private boolean mRedirectTouchToStickyView;
    private Rect mScrollRect;
    private List<WXScrollViewListener> mScrollViewListeners;
    int mScrollX;
    int mScrollY;
    private Handler mScrollerTask;
    private int mStickyOffset;
    private int[] mStickyP;
    private DCWXScroller mWAScroller;
    private int[] offsetInWindow;
    private float ox;
    private float oy;
    private boolean scrollable;
    private Field scroller;
    private boolean shouldBeTriggerLower;
    private boolean shouldBeTriggerUpper;
    private int[] stickyScrollerP;
    private int[] stickyViewP;
    private float upper;
    private WXGesture wxGesture;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface WXScrollViewListener {
        void onScroll(DCWXScrollView dCWXScrollView, int i, int i2);

        void onScrollChanged(DCWXScrollView dCWXScrollView, int i, int i2, int i3, int i4);

        void onScrollStopped(DCWXScrollView dCWXScrollView, int i, int i2);

        void onScrollToBottom(DCWXScrollView dCWXScrollView, int i, int i2);

        void onScrollToTop(DCWXScrollView dCWXScrollView, int i, int i2);
    }

    public DCWXScrollView(Context context) throws NoSuchFieldException, SecurityException {
        super(context);
        this.consumed = new int[2];
        this.offsetInWindow = new int[2];
        this.mHasNotDoneActionDown = true;
        this.mCheckTime = 100;
        this.mStickyP = new int[2];
        this.stickyScrollerP = new int[2];
        this.stickyViewP = new int[2];
        this.scrollable = true;
        this.scroller = null;
        this.isTouch = false;
        this.isExecScrollerTask = false;
        this.decelerationRate = 1.0f;
        this.shouldBeTriggerUpper = true;
        this.shouldBeTriggerLower = true;
        this.upper = 50.0f;
        this.lowwer = 50.0f;
        this.mScrollViewListeners = new ArrayList();
        init();
        try {
            WXReflectionUtils.setValue(this, "mMinimumVelocity", 5);
        } catch (Exception e) {
            WXLogUtils.e("[WXScrollView] WXScrollView: ", e);
        }
    }

    private void init() throws NoSuchFieldException, SecurityException {
        setWillNotDraw(false);
        setOverScrollMode(2);
        NestedScrollingChildHelper nestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        this.childHelper = nestedScrollingChildHelper;
        nestedScrollingChildHelper.setNestedScrollingEnabled(true);
        try {
            Field declaredField = ScrollView.class.getDeclaredField("mScroller");
            this.scroller = declaredField;
            declaredField.setAccessible(true);
        } catch (Exception unused) {
        }
    }

    private View procSticky(Map<String, Map<String, WXComponent>> map) {
        Map<String, WXComponent> map2;
        if (map == null || (map2 = map.get(this.mWAScroller.getRef())) == null) {
            return null;
        }
        Iterator<Map.Entry<String, WXComponent>> it = map2.entrySet().iterator();
        while (it.hasNext()) {
            WXComponent value = it.next().getValue();
            getLocationOnScreen(this.stickyScrollerP);
            value.getHostView().getLocationOnScreen(this.stickyViewP);
            int height = (value.getParent() == null || value.getParent().getRealView() == null) ? 0 : value.getParent().getRealView().getHeight();
            int height2 = value.getHostView().getHeight();
            int i = this.stickyScrollerP[1];
            int i2 = (-height) + i + height2;
            int i3 = this.stickyViewP[1];
            if (i3 <= i && i3 >= i2 - height2) {
                this.mStickyOffset = i3 - i2;
                value.setStickyOffset(i3 - i);
                return value.getHostView();
            }
            value.setStickyOffset(0);
        }
        return null;
    }

    private void showStickyView() {
        DCWXScroller dCWXScroller = this.mWAScroller;
        if (dCWXScroller == null) {
            return;
        }
        View viewProcSticky = procSticky(dCWXScroller.getStickMap());
        if (viewProcSticky != null) {
            this.mCurrentStickyView = viewProcSticky;
        } else {
            this.mCurrentStickyView = null;
        }
    }

    public void addScrollViewListener(WXScrollViewListener wXScrollViewListener) {
        if (this.mScrollViewListeners.contains(wXScrollViewListener)) {
            return;
        }
        this.mScrollViewListeners.add(wXScrollViewListener);
    }

    @Override // com.taobao.weex.ui.view.IWXScroller
    public void destroy() {
        this.mScrollViewListeners.clear();
        Handler handler = this.mScrollerTask;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.mCurrentStickyView != null) {
            canvas.save();
            this.mCurrentStickyView.getLocationOnScreen(this.mStickyP);
            int i = this.mStickyOffset;
            if (i > 0) {
                i = 0;
            }
            canvas.translate(this.mStickyP[0], getScrollY() + i);
            canvas.clipRect(0, i, this.mCurrentStickyView.getWidth(), this.mCurrentStickyView.getHeight());
            this.mCurrentStickyView.draw(canvas);
            canvas.restore();
        }
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedFling(float f, float f2, boolean z) {
        return this.childHelper.dispatchNestedFling(f, f2, z);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedPreFling(float f, float f2) {
        return this.childHelper.dispatchNestedPreFling(f, f2);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) {
        return this.childHelper.dispatchNestedPreScroll(i, i2, iArr, iArr2);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) {
        return this.childHelper.dispatchNestedScroll(i, i2, i3, i4, iArr);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.mRedirectTouchToStickyView = true;
        }
        if (this.mRedirectTouchToStickyView) {
            boolean z = false;
            boolean z2 = this.mCurrentStickyView != null;
            this.mRedirectTouchToStickyView = z2;
            if (z2) {
                if (motionEvent.getY() <= this.mCurrentStickyView.getHeight() && motionEvent.getX() >= this.mCurrentStickyView.getLeft() && motionEvent.getX() <= this.mCurrentStickyView.getRight()) {
                    z = true;
                }
                this.mRedirectTouchToStickyView = z;
            }
        }
        if (this.mRedirectTouchToStickyView) {
            if (this.mScrollRect == null) {
                Rect rect = new Rect();
                this.mScrollRect = rect;
                getGlobalVisibleRect(rect);
            }
            this.mCurrentStickyView.getLocationOnScreen(this.stickyViewP);
            motionEvent.offsetLocation(0.0f, this.stickyViewP[1] - this.mScrollRect.top);
        }
        boolean zDispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        WXGesture wXGesture = this.wxGesture;
        return wXGesture != null ? wXGesture.onTouch(this, motionEvent) | zDispatchTouchEvent : zDispatchTouchEvent;
    }

    @Override // android.widget.ScrollView
    public void fling(int i) {
        super.fling((int) (i * this.decelerationRate));
        Handler handler = this.mScrollerTask;
        if (handler != null) {
            handler.removeMessages(0);
        }
        startScrollerTask();
    }

    public Rect getContentFrame() {
        return new Rect(0, 0, computeHorizontalScrollRange(), computeVerticalScrollRange());
    }

    @Override // com.taobao.weex.ui.view.gesture.WXGestureObservable
    public WXGesture getGestureListener() {
        return this.wxGesture;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (message.what != 0) {
            return true;
        }
        Handler handler = this.mScrollerTask;
        if (handler != null) {
            handler.removeMessages(0);
        }
        if (this.mInitialPosition - getScrollY() == 0) {
            this.isExecScrollerTask = false;
            onScrollStopped(this, getScrollX(), getScrollY());
            return true;
        }
        onScroll(this, getScrollX(), getScrollY());
        this.mInitialPosition = getScrollY();
        Handler handler2 = this.mScrollerTask;
        if (handler2 == null) {
            return true;
        }
        handler2.sendEmptyMessageDelayed(0, this.mCheckTime);
        return true;
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean hasNestedScrollingParent() {
        return this.childHelper.hasNestedScrollingParent();
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean isNestedScrollingEnabled() {
        return this.childHelper.isNestedScrollingEnabled();
    }

    public boolean isScrollable() {
        return this.scrollable;
    }

    @Override // android.widget.ScrollView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (isScrollable()) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return false;
    }

    @Override // android.widget.ScrollView, android.view.ViewGroup, android.view.ViewParent
    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        return dispatchNestedFling(f, f2, z);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onNestedPreFling(View view, float f, float f2) {
        return dispatchNestedPreFling(f, f2);
    }

    protected void onScroll(DCWXScrollView dCWXScrollView, int i, int i2) {
        List<WXScrollViewListener> list = this.mScrollViewListeners;
        int size = list == null ? 0 : list.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.mScrollViewListeners.get(i3).onScroll(this, i, i2);
        }
    }

    @Override // android.view.View
    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        AppEventForBlurManager.onScrollChanged(i, i2);
        startScrollerTask();
        this.mScrollX = getScrollX();
        int scrollY = getScrollY();
        this.mScrollY = scrollY;
        onScroll(this, this.mScrollX, scrollY);
        View childAt = getChildAt(getChildCount() - 1);
        if (childAt == null) {
            return;
        }
        int bottom = childAt.getBottom();
        int height = getHeight();
        int i5 = this.mScrollY;
        float f = bottom - (height + i5);
        float f2 = this.lowwer;
        if (f <= f2 && !this.shouldBeTriggerLower) {
            this.shouldBeTriggerLower = true;
            onScrollToBottom(this.mScrollX, i5);
        } else if (f > f2) {
            this.shouldBeTriggerLower = false;
        }
        if (getScrollY() <= this.upper && !this.shouldBeTriggerUpper) {
            this.shouldBeTriggerUpper = true;
            onScrollToTop(this.mScrollX, this.mScrollY);
        } else if (getScrollY() > this.upper) {
            this.shouldBeTriggerUpper = false;
        }
        List<WXScrollViewListener> list = this.mScrollViewListeners;
        int size = list == null ? 0 : list.size();
        for (int i6 = 0; i6 < size; i6++) {
            this.mScrollViewListeners.get(i6).onScrollChanged(this, i, i2, i3 - i, i4 - i2);
        }
        showStickyView();
    }

    protected void onScrollStopped(DCWXScrollView dCWXScrollView, int i, int i2) {
        List<WXScrollViewListener> list = this.mScrollViewListeners;
        int size = list == null ? 0 : list.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.mScrollViewListeners.get(i3).onScrollStopped(this, i, i2);
        }
    }

    protected void onScrollToBottom(int i, int i2) {
        List<WXScrollViewListener> list = this.mScrollViewListeners;
        int size = list == null ? 0 : list.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.mScrollViewListeners.get(i3).onScrollToBottom(this, i, i2);
        }
    }

    protected void onScrollToTop(int i, int i2) {
        List<WXScrollViewListener> list = this.mScrollViewListeners;
        int size = list == null ? 0 : list.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.mScrollViewListeners.get(i3).onScrollToTop(this, i, i2);
        }
    }

    @Override // android.widget.ScrollView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.scrollable) {
            return true;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            this.isTouch = true;
        } else if (action == 1 || action == 3) {
            this.isTouch = false;
        }
        if (this.mRedirectTouchToStickyView) {
            if (this.mScrollRect == null) {
                Rect rect = new Rect();
                this.mScrollRect = rect;
                getGlobalVisibleRect(rect);
            }
            this.mCurrentStickyView.getLocationOnScreen(this.stickyViewP);
            motionEvent.offsetLocation(0.0f, -(this.stickyViewP[1] - this.mScrollRect.top));
        }
        if (motionEvent.getAction() == 0) {
            this.mHasNotDoneActionDown = false;
        }
        if (this.mHasNotDoneActionDown) {
            MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
            motionEventObtain.setAction(0);
            this.mHasNotDoneActionDown = false;
            motionEventObtain.recycle();
        }
        if (motionEvent.getAction() == 0) {
            this.ox = motionEvent.getX();
            this.oy = motionEvent.getY();
            startNestedScroll(3);
        }
        if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            this.isExecScrollerTask = false;
            this.mHasNotDoneActionDown = true;
            stopNestedScroll();
        }
        motionEvent.getAction();
        return super.onTouchEvent(motionEvent);
    }

    @Override // com.taobao.weex.ui.view.gesture.WXGestureObservable
    public void registerGestureListener(WXGesture wXGesture) {
        this.wxGesture = wXGesture;
    }

    public void removeScrollViewListener(WXScrollViewListener wXScrollViewListener) {
        this.mScrollViewListeners.remove(wXScrollViewListener);
    }

    public void setLowwerLength(float f) {
        this.lowwer = f;
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public void setNestedScrollingEnabled(boolean z) {
        this.childHelper.setNestedScrollingEnabled(z);
    }

    public void setRate(float f) {
        this.decelerationRate = f;
    }

    public void setScrollable(boolean z) {
        this.scrollable = z;
    }

    public void setUpperLength(float f) {
        this.upper = f;
    }

    public void setWAScroller(DCWXScroller dCWXScroller) {
        this.mWAScroller = dCWXScroller;
        this.upper = WXViewUtils.getRealPxByWidth(50.0f, dCWXScroller.getViewPortWidth());
        this.lowwer = WXViewUtils.getRealPxByWidth(50.0f, this.mWAScroller.getViewPortWidth());
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public boolean startNestedScroll(int i) {
        return this.childHelper.startNestedScroll(i);
    }

    public void startScrollerTask() {
        if (this.isExecScrollerTask) {
            return;
        }
        this.isExecScrollerTask = true;
        if (this.mScrollerTask == null) {
            this.mScrollerTask = new Handler(WXThread.secure(this));
        }
        this.mInitialPosition = getScrollY();
        this.mScrollerTask.sendEmptyMessageDelayed(0, this.mCheckTime);
    }

    @Override // android.view.View, androidx.core.view.NestedScrollingChild
    public void stopNestedScroll() {
        this.childHelper.stopNestedScroll();
    }

    public void stopScroll() {
        Field field = this.scroller;
        if (field == null || this.isTouch) {
            return;
        }
        try {
            Method method = field.getType().getMethod("abortAnimation", null);
            if (method != null) {
                method.setAccessible(true);
                method.invoke(this.scroller.get(this), null);
            }
        } catch (Exception unused) {
        }
    }

    public DCWXScrollView(Context context, AttributeSet attributeSet) throws NoSuchFieldException, SecurityException {
        super(context, attributeSet);
        this.consumed = new int[2];
        this.offsetInWindow = new int[2];
        this.mHasNotDoneActionDown = true;
        this.mCheckTime = 100;
        this.mStickyP = new int[2];
        this.stickyScrollerP = new int[2];
        this.stickyViewP = new int[2];
        this.scrollable = true;
        this.scroller = null;
        this.isTouch = false;
        this.isExecScrollerTask = false;
        this.decelerationRate = 1.0f;
        this.shouldBeTriggerUpper = true;
        this.shouldBeTriggerLower = true;
        this.upper = 50.0f;
        this.lowwer = 50.0f;
        init();
    }

    public DCWXScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.consumed = new int[2];
        this.offsetInWindow = new int[2];
        this.mHasNotDoneActionDown = true;
        this.mCheckTime = 100;
        this.mStickyP = new int[2];
        this.stickyScrollerP = new int[2];
        this.stickyViewP = new int[2];
        this.scrollable = true;
        this.scroller = null;
        this.isTouch = false;
        this.isExecScrollerTask = false;
        this.decelerationRate = 1.0f;
        this.shouldBeTriggerUpper = true;
        this.shouldBeTriggerLower = true;
        this.upper = 50.0f;
        this.lowwer = 50.0f;
        setOverScrollMode(2);
    }
}
