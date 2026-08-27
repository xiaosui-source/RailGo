package com.dcloud.android.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Region;
import android.text.TextUtils;
import android.view.MotionEvent;
import com.dcloud.android.widget.SlideLayout;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaFrameView;
import io.dcloud.common.adapter.util.ViewOptions;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.core.ui.g;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class AbsoluteLayout extends SlideLayout {
    static final String STATE_CHANGED_TEMPLATE = "{status:'%s',offset:'%s'}";
    boolean canDoMaskClickEvent;
    float downX;
    float downY;
    private boolean isAnimate;
    private boolean isCanDrag;
    IApp mAppHandler;
    String mCallBackID;
    g mDrag;
    AdaFrameView mFrameView;
    private int mRegionBottom;
    private int mRegionLeft;
    private RectF mRegionRect;
    private int mRegionRight;
    private int mRegionTop;
    ViewOptions mViewOptions;

    public AbsoluteLayout(Context context, AdaFrameView adaFrameView, IApp iApp) {
        super(context);
        this.mFrameView = null;
        this.mViewOptions = null;
        this.mAppHandler = null;
        this.isCanDrag = false;
        this.canDoMaskClickEvent = true;
        this.mDrag = new g(adaFrameView, context);
        this.mFrameView = adaFrameView;
        this.mAppHandler = iApp;
        this.mViewOptions = adaFrameView.obtainFrameOptions();
        setOnStateChangeListener(new SlideLayout.OnStateChangeListener() { // from class: com.dcloud.android.widget.AbsoluteLayout.1
            @Override // com.dcloud.android.widget.SlideLayout.OnStateChangeListener
            public void onStateChanged(String str, String str2) {
                AbsoluteLayout.this.mFrameView.dispatchFrameViewEvents(AbsoluteConst.EVENTS_SLIDE_BOUNCE, StringUtil.format(AbsoluteLayout.STATE_CHANGED_TEMPLATE, str, str2));
            }
        });
    }

    private void doClickEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.canDoMaskClickEvent = true;
            this.downX = motionEvent.getX();
            this.downY = motionEvent.getY();
            return;
        }
        if (motionEvent.getAction() == 1) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if (this.canDoMaskClickEvent) {
                float f = 10;
                if (Math.abs(this.downX - x) > f || Math.abs(this.downY - y) > f) {
                    return;
                }
                this.mFrameView.dispatchFrameViewEvents(AbsoluteConst.EVENTS_MASK_CLICK, null);
                return;
            }
            return;
        }
        if (motionEvent.getAction() == 2) {
            float x2 = motionEvent.getX();
            float y2 = motionEvent.getY();
            if (this.canDoMaskClickEvent) {
                float f2 = 10;
                if (Math.abs(this.downX - x2) <= f2 || Math.abs(this.downY - y2) <= f2) {
                    return;
                }
                this.canDoMaskClickEvent = false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void endAnimatecallback(IWebview iWebview, String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Deprecated_JSUtil.execCallback(iWebview, str, null, JSUtil.OK, false, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runDrawRectF(final IWebview iWebview, final String str, final int i, final int i2, final int i3, final int i4, final int i5, final int i6, final int i7, final int i8, final int i9) {
        if (!this.isAnimate) {
            endAnimatecallback(iWebview, str);
            return;
        }
        if (this.mRegionRect == null) {
            this.mRegionRect = new RectF();
        }
        RectF rectF = this.mRegionRect;
        rectF.left = i;
        rectF.right = this.mViewOptions.width - i2;
        rectF.top = i3;
        if (i9 == i7) {
            rectF.bottom = (i6 * i9) + i3 + i8;
        } else {
            rectF.bottom = (i6 * i9) + i3;
        }
        postDelayed(new Runnable() { // from class: com.dcloud.android.widget.AbsoluteLayout.2
            @Override // java.lang.Runnable
            public void run() {
                AbsoluteLayout.this.invalidate();
                int i10 = i9;
                int i11 = i7;
                if (i10 == i11) {
                    AbsoluteLayout.this.endAnimatecallback(iWebview, str);
                } else {
                    AbsoluteLayout.this.runDrawRectF(iWebview, str, i, i2, i3, i4, i5, i6, i11, i8, i10 + 1);
                }
            }
        }, i5);
    }

    public void animate(IWebview iWebview, String str, String str2) {
        if (this.mViewOptions == null) {
            endAnimatecallback(iWebview, str2);
            return;
        }
        this.mCallBackID = str2;
        try {
            JSONObject jSONObject = new JSONObject(str);
            String strOptString = jSONObject.optString("type");
            int iOptInt = jSONObject.optInt("duration", 200);
            int iOptInt2 = jSONObject.optInt("frames", 12);
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("region");
            if (jSONObjectOptJSONObject != null) {
                String strOptString2 = jSONObjectOptJSONObject.optString("left");
                ViewOptions viewOptions = this.mViewOptions;
                this.mRegionLeft = PdrUtil.convertToScreenInt(strOptString2, viewOptions.width, 0, viewOptions.mWebviewScale);
                String strOptString3 = jSONObjectOptJSONObject.optString("right");
                ViewOptions viewOptions2 = this.mViewOptions;
                this.mRegionRight = PdrUtil.convertToScreenInt(strOptString3, viewOptions2.width, 0, viewOptions2.mWebviewScale);
                String strOptString4 = jSONObjectOptJSONObject.optString("top");
                ViewOptions viewOptions3 = this.mViewOptions;
                this.mRegionTop = PdrUtil.convertToScreenInt(strOptString4, viewOptions3.height, 0, viewOptions3.mWebviewScale);
                String strOptString5 = jSONObjectOptJSONObject.optString("bottom");
                ViewOptions viewOptions4 = this.mViewOptions;
                this.mRegionBottom = PdrUtil.convertToScreenInt(strOptString5, viewOptions4.height, 0, viewOptions4.mWebviewScale);
            }
            int i = iOptInt / iOptInt2;
            ViewOptions viewOptions5 = this.mViewOptions;
            int i2 = viewOptions5.height - ((this.mRegionTop + viewOptions5.top) + this.mRegionBottom);
            int i3 = i2 / iOptInt2;
            int i4 = i2 - (i3 * iOptInt2);
            if (TextUtils.isEmpty(strOptString) || !strOptString.equals("shrink")) {
                return;
            }
            this.isAnimate = true;
            int i5 = this.mRegionLeft;
            int i6 = this.mRegionRight;
            int i7 = this.mRegionTop;
            ViewOptions viewOptions6 = this.mViewOptions;
            runDrawRectF(iWebview, str2, i5, i6, i7 + viewOptions6.top, viewOptions6.height - this.mRegionBottom, i, i3, iOptInt2, i4, 1);
        } catch (Exception e) {
            e.printStackTrace();
            endAnimatecallback(iWebview, str2);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        RectF rectF = this.mRegionRect;
        if (rectF != null) {
            canvas.clipRect(rectF, Region.Op.DIFFERENCE);
        }
        ViewOptions viewOptions = this.mViewOptions;
        if (viewOptions != null && !viewOptions.isTabHasBg() && !this.mViewOptions.hasBackground() && !this.mViewOptions.isTransparent() && !this.mViewOptions.hasMask() && this.mViewOptions.mUniNViewJson != null) {
            canvas.drawColor(-1);
        }
        this.mFrameView.paint(canvas);
        try {
            super.dispatchDraw(canvas);
            canvas.restore();
            ViewOptions viewOptions2 = this.mViewOptions;
            if (viewOptions2 != null && viewOptions2.hasMask()) {
                canvas.drawColor(this.mViewOptions.maskColor);
            }
            this.mFrameView.onDrawAfter(canvas);
        } catch (Exception unused) {
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        try {
            if (!this.mFrameView.interceptTouchEvent) {
                return false;
            }
            ViewOptions viewOptions = this.mViewOptions;
            if (viewOptions == null || !viewOptions.hasMask()) {
                ViewOptions viewOptions2 = this.mViewOptions;
                if (viewOptions2 == null || !viewOptions2.hasBackground()) {
                    return super.dispatchTouchEvent(motionEvent);
                }
                super.dispatchTouchEvent(motionEvent);
                return true;
            }
            doClickEvent(motionEvent);
            if (motionEvent.getAction() == 0) {
                this.isCanDrag = false;
            }
            if (!this.isCanDrag) {
                this.isCanDrag = this.mDrag.a(motionEvent);
            }
            if (this.isCanDrag) {
                onTouchEvent(motionEvent);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public g getDrag() {
        return this.mDrag;
    }

    public AdaFrameView getFrameView() {
        return this.mFrameView;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mFrameView.onConfigurationChanged();
        if (this.isAnimate) {
            this.isAnimate = false;
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.mRegionRect != null) {
            canvas.save();
            ViewOptions viewOptions = this.mViewOptions;
            int i = viewOptions.left;
            int i2 = viewOptions.top;
            canvas.clipRect(i, i2, viewOptions.width + i, viewOptions.height + i2);
            canvas.restore();
        }
        super.onDraw(canvas);
    }

    @Override // com.dcloud.android.widget.SlideLayout, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.mDrag.a(motionEvent)) {
            return true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // com.dcloud.android.widget.SlideLayout, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean zC = this.mDrag.c(motionEvent);
        if (this.mViewOptions.isTransparent()) {
            return super.onTouchEvent(motionEvent);
        }
        AdaFrameView adaFrameView = this.mFrameView;
        if ((adaFrameView == null || !adaFrameView.isTouchEvent) && !zC) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    public void restore() {
        this.isAnimate = false;
        this.mRegionRect = null;
        invalidate();
    }

    @Override // android.view.View
    public String toString() {
        AdaFrameView adaFrameView = this.mFrameView;
        return adaFrameView != null ? adaFrameView.toString() : super.toString();
    }
}
