package io.dcloud.common.adapter.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import io.dcloud.common.adapter.util.EventActionInfo;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class WebParentView extends FrameLayout {
    private boolean mIsBeingDragged;
    float mLastMotionX;
    float mLastMotionY;
    AdaWebview mWebView;

    public WebParentView(Context context) {
        super(context);
        this.mIsBeingDragged = true;
    }

    private boolean isCanCircleRefresh() {
        return this.mWebView.obtainFrameView() != null && ((AdaFrameView) this.mWebView.obtainFrameView()).getCircleRefreshView() != null && ((AdaFrameView) this.mWebView.obtainFrameView()).getCircleRefreshView().isRefreshEnable() && this.mWebView.getDCWebView().getWebViewScrollY() <= 0;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        AdaWebview adaWebview = this.mWebView;
        if (adaWebview instanceof AdaUniWebView) {
            ((AdaUniWebView) this.mWebView).fireEvent(new EventActionInfo(AbsoluteConst.EVENTS_PLUS_ORIENTATI_ONCHANGE));
        } else {
            adaWebview.executeScript(StringUtil.format(AbsoluteConst.EVENTS_DOCUMENT_EXECUTE_TEMPLATE, AbsoluteConst.EVENTS_PLUS_ORIENTATI_ONCHANGE));
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!isCanCircleRefresh()) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        float rawX = motionEvent.getRawX();
        float rawY = motionEvent.getRawY();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mLastMotionY = rawY;
            this.mLastMotionX = rawX;
            this.mIsBeingDragged = false;
        } else if (action == 2) {
            float f = rawX - this.mLastMotionX;
            float f2 = rawY - this.mLastMotionY;
            if (f2 > 20.0f && Math.abs(f2) > Math.abs(f)) {
                this.mIsBeingDragged = true;
                motionEvent.setAction(0);
                onTouchEvent(motionEvent);
            }
        }
        return this.mIsBeingDragged;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) throws JSONException {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.mWebView != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("width", i / this.mWebView.getScale());
                jSONObject.put("height", i2 / this.mWebView.getScale());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.mWebView.mFrameView.dispatchFrameViewEvents("resize", jSONObject.toString());
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean zIsCanCircleRefresh = isCanCircleRefresh();
        if (zIsCanCircleRefresh && ((AdaFrameView) this.mWebView.obtainFrameView()).getCircleRefreshView().onSelfTouchEvent(motionEvent)) {
            return true;
        }
        if (zIsCanCircleRefresh) {
            return false;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setWebView(AdaWebview adaWebview) {
        this.mWebView = adaWebview;
    }
}
