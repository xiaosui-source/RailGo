package com.taobao.weex.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.common.Constants;
import com.taobao.weex.common.WXErrorCode;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXDiv;
import com.taobao.weex.ui.view.gesture.WXGesture;
import com.taobao.weex.ui.view.gesture.WXGestureObservable;
import com.taobao.weex.utils.WXExceptionUtils;
import com.taobao.weex.utils.WXLogUtils;
import io.dcloud.common.adapter.util.PlatformUtil;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXFrameLayout extends BaseFrameLayout implements WXGestureObservable, IRenderStatus<WXDiv>, IRenderResult<WXDiv> {
    private long downTimeMillis;
    private float downX;
    private float downY;
    private Object mOnTouchListener;
    private WeakReference<WXDiv> mWeakReference;
    private float moveX;
    private float moveY;
    private WXGesture wxGesture;

    public WXFrameLayout(Context context) {
        super(context);
    }

    private void addPan(Object obj) {
        this.mOnTouchListener = obj;
    }

    private int calLayerDeep(View view, int i) {
        int i2 = i + 1;
        return (view.getParent() == null || !(view.getParent() instanceof View)) ? i2 : calLayerDeep((View) view.getParent(), i2);
    }

    private void removePan() {
        this.mOnTouchListener = null;
    }

    private int reportLayerOverFlowError() {
        int iCalLayerDeep = calLayerDeep(this, 0);
        if (getComponent() != null) {
            String instanceId = getComponent().getInstanceId();
            WXErrorCode wXErrorCode = WXErrorCode.WX_RENDER_ERR_LAYER_OVERFLOW;
            WXExceptionUtils.commitCriticalExceptionRT(instanceId, wXErrorCode, "draw android view", wXErrorCode.getErrorMsg() + "Layer overflow limit error: " + iCalLayerDeep + " layers!", null);
        }
        return iCalLayerDeep;
    }

    @Override // com.taobao.weex.ui.view.BaseFrameLayout, android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        WXSDKInstance sDKInstance;
        try {
            super.dispatchDrawInterval(canvas);
        } catch (Throwable th) {
            if (getComponent() != null) {
                notifyLayerOverFlow();
                if (getComponent() != null && (sDKInstance = WXSDKManager.getInstance().getSDKInstance(getComponent().getInstanceId())) != null && sDKInstance.getApmForInstance() != null && !sDKInstance.getApmForInstance().hasReportLayerOverDraw) {
                    sDKInstance.getApmForInstance().hasReportLayerOverDraw = true;
                    reportLayerOverFlowError();
                }
            }
            WXLogUtils.e("Layer overflow limit error", WXLogUtils.getStackTrace(th));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        boolean zDispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        WXGesture wXGesture = this.wxGesture;
        return wXGesture != null ? wXGesture.onTouch(this, motionEvent) | zDispatchTouchEvent : zDispatchTouchEvent;
    }

    @Override // com.taobao.weex.ui.view.gesture.WXGestureObservable
    public WXGesture getGestureListener() {
        return this.wxGesture;
    }

    public void notifyLayerOverFlow() {
        WXSDKInstance wXComponent;
        if (getComponent() == null || (wXComponent = getComponent().getInstance()) == null || wXComponent.getLayerOverFlowListeners() == null) {
            return;
        }
        for (String str : wXComponent.getLayerOverFlowListeners()) {
            WXComponent wXComponent2 = WXSDKManager.getInstance().getWXRenderManager().getWXComponent(wXComponent.getInstanceId(), str);
            HashMap map = new HashMap();
            map.put("ref", str);
            map.put(Constants.Weex.INSTANCEID, wXComponent2.getInstanceId());
            wXComponent2.fireEvent(Constants.Event.LAYEROVERFLOW, map);
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        Object obj = this.mOnTouchListener;
        if (obj != null) {
            PlatformUtil.invokeMethod(obj.getClass().getName(), "onTouch", this.mOnTouchListener, new Class[]{View.class, MotionEvent.class}, new Object[]{this, motionEvent});
            int action = motionEvent.getAction();
            if (action == 0) {
                this.downTimeMillis = System.currentTimeMillis();
                this.downX = motionEvent.getRawX();
            } else if (action == 1 || action == 2 || action == 3) {
                this.moveX = motionEvent.getRawX();
            }
            if (Math.abs(this.moveX - this.downX) > 30.0f && System.currentTimeMillis() - this.downTimeMillis > 200) {
                return true;
            }
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // com.taobao.weex.ui.view.gesture.WXGestureObservable
    public void registerGestureListener(WXGesture wXGesture) {
        this.wxGesture = wXGesture;
    }

    @Override // com.taobao.weex.ui.view.IRenderResult
    public WXDiv getComponent() {
        WeakReference<WXDiv> weakReference = this.mWeakReference;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    @Override // com.taobao.weex.ui.view.IRenderStatus
    public void holdComponent(WXDiv wXDiv) {
        this.mWeakReference = new WeakReference<>(wXDiv);
    }
}
