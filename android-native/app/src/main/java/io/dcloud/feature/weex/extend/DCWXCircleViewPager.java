package io.dcloud.feature.weex.extend;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.viewpager.widget.ViewPager;
import com.taobao.weex.ui.view.WXCircleViewPager;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class DCWXCircleViewPager extends WXCircleViewPager {
    private boolean isVertical;
    private int pointCounr;

    public DCWXCircleViewPager(Context context) {
        super(context);
        this.isVertical = false;
        this.pointCounr = 0;
    }

    private MotionEvent swapTouchEvent(MotionEvent motionEvent) {
        float width = getWidth();
        float height = getHeight();
        motionEvent.setLocation((motionEvent.getY() / height) * width, (motionEvent.getX() / width) * height);
        return motionEvent;
    }

    public int getPointCounr() {
        return this.pointCounr;
    }

    public boolean isVertical() {
        return this.isVertical;
    }

    @Override // com.taobao.weex.ui.view.WXCircleViewPager, androidx.viewpager.widget.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        try {
            if (!this.isVertical) {
                return super.onInterceptTouchEvent(motionEvent);
            }
            boolean zOnInterceptTouchEvent = super.onInterceptTouchEvent(swapTouchEvent(motionEvent));
            swapTouchEvent(motionEvent);
            return zOnInterceptTouchEvent;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return super.onInterceptTouchEvent(motionEvent);
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            return super.onInterceptTouchEvent(motionEvent);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0031  */
    @Override // com.taobao.weex.ui.view.WXCircleViewPager, androidx.viewpager.widget.ViewPager, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            int r0 = r7.getAction()
            r1 = 3
            r2 = 0
            r3 = 1
            if (r0 == 0) goto L11
            if (r0 == r3) goto Le
            if (r0 == r1) goto Le
            goto L17
        Le:
            r6.pointCounr = r2
            goto L17
        L11:
            int r0 = r7.getPointerCount()
            r6.pointCounr = r0
        L17:
            boolean r0 = r6.isVertical
            if (r0 == 0) goto L3d
            android.view.ViewParent r0 = r6.getParent()
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            int r4 = r7.getAction()
            if (r4 == r3) goto L31
            r5 = 2
            if (r4 == r5) goto L2d
            if (r4 == r1) goto L31
            goto L34
        L2d:
            r0.requestDisallowInterceptTouchEvent(r3)
            goto L34
        L31:
            r0.requestDisallowInterceptTouchEvent(r2)
        L34:
            android.view.MotionEvent r7 = r6.swapTouchEvent(r7)
            boolean r7 = super.onTouchEvent(r7)
            return r7
        L3d:
            boolean r7 = super.onTouchEvent(r7)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.weex.extend.DCWXCircleViewPager.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void setVertical(boolean z) throws Resources.NotFoundException {
        this.isVertical = z;
        if (z) {
            setPageTransformer(false, new ViewPager.PageTransformer() { // from class: io.dcloud.feature.weex.extend.DCWXCircleViewPager.1
                @Override // androidx.viewpager.widget.ViewPager.PageTransformer
                public void transformPage(View view, float f) {
                    view.setTranslationX(view.getWidth() * (-f));
                    view.setTranslationY(f * view.getHeight());
                }
            });
        } else {
            setPageTransformer(false, null);
        }
    }

    public DCWXCircleViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isVertical = false;
        this.pointCounr = 0;
    }
}
