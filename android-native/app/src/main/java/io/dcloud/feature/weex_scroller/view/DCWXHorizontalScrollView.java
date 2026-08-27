package io.dcloud.feature.weex_scroller.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import com.taobao.weex.ui.component.DCWXScroller;
import com.taobao.weex.ui.view.IWXScroller;
import com.taobao.weex.ui.view.gesture.WXGesture;
import com.taobao.weex.ui.view.gesture.WXGestureObservable;
import com.taobao.weex.utils.WXViewUtils;
import io.dcloud.common.ui.blur.AppEventForBlurManager;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class DCWXHorizontalScrollView extends HorizontalScrollView implements IWXScroller, WXGestureObservable {
    private boolean isTouch;
    private float lowwer;
    private ScrollViewListener mScrollViewListener;
    private List<ScrollViewListener> mScrollViewListeners;
    int mScrollX;
    int mScrollY;
    private boolean scrollable;
    private Field scroller;
    private boolean shouldBeTriggerLower;
    private boolean shouldBeTriggerUpper;
    private float upper;
    private WXGesture wxGesture;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface ScrollViewListener {
        void onScrollChanged(DCWXHorizontalScrollView dCWXHorizontalScrollView, int i, int i2, int i3, int i4);

        void onScrollToBottom();

        void onScrolltoTop();
    }

    public DCWXHorizontalScrollView(Context context) throws NoSuchFieldException, SecurityException {
        super(context);
        this.scrollable = true;
        this.scroller = null;
        this.isTouch = false;
        this.shouldBeTriggerUpper = true;
        this.shouldBeTriggerLower = true;
        this.upper = 50.0f;
        this.lowwer = 50.0f;
        init();
    }

    private void init() throws NoSuchFieldException, SecurityException {
        setWillNotDraw(false);
        setOverScrollMode(2);
        try {
            Field declaredField = HorizontalScrollView.class.getDeclaredField("mScroller");
            this.scroller = declaredField;
            declaredField.setAccessible(true);
        } catch (Exception unused) {
        }
    }

    private void scrollToBottom() {
        ScrollViewListener scrollViewListener = this.mScrollViewListener;
        if (scrollViewListener != null) {
            scrollViewListener.onScrollToBottom();
        }
        List<ScrollViewListener> list = this.mScrollViewListeners;
        if (list != null) {
            Iterator<ScrollViewListener> it = list.iterator();
            while (it.hasNext()) {
                it.next().onScrollToBottom();
            }
        }
    }

    private void scrollToTop() {
        ScrollViewListener scrollViewListener = this.mScrollViewListener;
        if (scrollViewListener != null) {
            scrollViewListener.onScrolltoTop();
        }
        List<ScrollViewListener> list = this.mScrollViewListeners;
        if (list != null) {
            Iterator<ScrollViewListener> it = list.iterator();
            while (it.hasNext()) {
                it.next().onScrolltoTop();
            }
        }
    }

    public void addScrollViewListener(ScrollViewListener scrollViewListener) {
        if (this.mScrollViewListeners == null) {
            this.mScrollViewListeners = new ArrayList();
        }
        if (this.mScrollViewListeners.contains(scrollViewListener)) {
            return;
        }
        this.mScrollViewListeners.add(scrollViewListener);
    }

    @Override // com.taobao.weex.ui.view.IWXScroller
    public void destroy() {
        List<ScrollViewListener> list = this.mScrollViewListeners;
        if (list != null) {
            list.clear();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        boolean zDispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        WXGesture wXGesture = this.wxGesture;
        return wXGesture != null ? wXGesture.onTouch(this, motionEvent) | zDispatchTouchEvent : zDispatchTouchEvent;
    }

    public Rect getContentFrame() {
        return new Rect(0, 0, computeHorizontalScrollRange(), computeVerticalScrollRange());
    }

    @Override // com.taobao.weex.ui.view.gesture.WXGestureObservable
    public WXGesture getGestureListener() {
        return this.wxGesture;
    }

    public boolean isScrollable() {
        return this.scrollable;
    }

    @Override // android.view.View
    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        AppEventForBlurManager.onScrollChanged(i, i2);
        this.mScrollX = getScrollX();
        this.mScrollY = getScrollY();
        if (getScrollX() <= this.upper && !this.shouldBeTriggerUpper) {
            scrollToTop();
            this.shouldBeTriggerUpper = true;
        } else if (getScrollX() > this.upper) {
            this.shouldBeTriggerUpper = false;
        }
        View childAt = getChildAt(getChildCount() - 1);
        if (childAt == null) {
            return;
        }
        float right = childAt.getRight() - (getWidth() + this.mScrollX);
        float f = this.lowwer;
        if (right <= f && !this.shouldBeTriggerLower) {
            scrollToBottom();
            this.shouldBeTriggerLower = true;
        } else if (right > f) {
            this.shouldBeTriggerLower = false;
        }
        ScrollViewListener scrollViewListener = this.mScrollViewListener;
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, i, i2, i3 - i, i4 - i2);
        }
        List<ScrollViewListener> list = this.mScrollViewListeners;
        if (list != null) {
            Iterator<ScrollViewListener> it = list.iterator();
            while (it.hasNext()) {
                it.next().onScrollChanged(this, i, i2, i3 - i, i4 - i2);
            }
        }
    }

    @Override // android.widget.HorizontalScrollView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.isTouch = true;
        } else if (action == 1 || action == 3) {
            this.isTouch = false;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // com.taobao.weex.ui.view.gesture.WXGestureObservable
    public void registerGestureListener(WXGesture wXGesture) {
        this.wxGesture = wXGesture;
    }

    public void removeScrollViewListener(ScrollViewListener scrollViewListener) {
        this.mScrollViewListeners.remove(scrollViewListener);
    }

    public void setLowwerLength(float f) {
        this.lowwer = f;
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.mScrollViewListener = scrollViewListener;
    }

    public void setScrollable(boolean z) {
        this.scrollable = z;
    }

    public void setUpperLength(float f) {
        this.upper = f;
    }

    public void setWAScroller(DCWXScroller dCWXScroller) {
        this.upper = WXViewUtils.getRealPxByWidth(50.0f, dCWXScroller.getViewPortWidth());
        this.lowwer = WXViewUtils.getRealPxByWidth(50.0f, dCWXScroller.getViewPortWidth());
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

    public DCWXHorizontalScrollView(Context context, AttributeSet attributeSet) throws NoSuchFieldException, SecurityException {
        super(context, attributeSet);
        this.scrollable = true;
        this.scroller = null;
        this.isTouch = false;
        this.shouldBeTriggerUpper = true;
        this.shouldBeTriggerLower = true;
        this.upper = 50.0f;
        this.lowwer = 50.0f;
        init();
    }
}
