package com.taobao.weex.ui.view.gesture;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.bridge.EventResult;
import com.taobao.weex.common.Constants;
import com.taobao.weex.dom.WXEvent;
import com.taobao.weex.ui.component.Scrollable;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.view.gesture.WXGestureType;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import io.dcloud.common.constant.AbsoluteConst;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXGesture extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {
    private static final int CUR_EVENT = -1;
    public static final String DOWN = "down";
    public static final String END = "end";
    public static final String LEFT = "left";
    public static final String MOVE = "move";
    public static final String RIGHT = "right";
    public static final String START = "start";
    private static final String TAG = "Gesture";
    public static final String UNKNOWN = "unknown";
    public static final String UP = "up";
    private WXComponent component;
    private GestureDetector mGestureDetector;
    private int mParentOrientation;
    private int shouldBubbleInterval;
    private boolean shouldBubbleResult;
    private long swipeDownTime = -1;
    private long panDownTime = -1;
    private WXGestureType mPendingPan = null;
    private boolean mIsPreventMoveEvent = false;
    private boolean mIsTouchEventConsumed = false;
    private boolean requestDisallowInterceptTouchEvent = false;
    private int shouldBubbleCallRemainTimes = 0;
    private final List<View.OnTouchListener> mTouchListeners = new LinkedList();
    private Rect globalRect = new Rect();
    private Point globalOffset = new Point();
    private Point globalEventOffset = new Point();
    private PointF locEventOffset = new PointF();
    private PointF locLeftTop = new PointF();

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    private static class GestureHandler extends Handler {
        public GestureHandler() {
            super(Looper.getMainLooper());
        }
    }

    public WXGesture(WXComponent wXComponent, Context context) {
        this.mParentOrientation = -1;
        this.shouldBubbleResult = true;
        this.shouldBubbleInterval = 0;
        this.component = wXComponent;
        this.mGestureDetector = new GestureDetector(context, this, new GestureHandler());
        Scrollable parentScroller = wXComponent.getParentScroller();
        if (parentScroller != null) {
            this.mParentOrientation = parentScroller.getOrientation();
        }
        this.shouldBubbleResult = WXUtils.getBoolean(wXComponent.getAttrs().get(Constants.Name.SHOULD_STOP_PROPAGATION_INIT_RESULT), Boolean.TRUE).booleanValue();
        this.shouldBubbleInterval = WXUtils.getNumberInt(wXComponent.getAttrs().get(Constants.Name.SHOULD_STOP_PROPAGATION_INTERVAL), 0);
    }

    private boolean containsSimplePan() {
        return this.component.containsGesture(WXGestureType.HighLevelGesture.PAN_START) || this.component.containsGesture(WXGestureType.HighLevelGesture.PAN_MOVE) || this.component.containsGesture(WXGestureType.HighLevelGesture.PAN_END);
    }

    private Map<String, Object> createFireEventParam(MotionEvent motionEvent, int i, String str) {
        JSONArray jSONArray = new JSONArray(motionEvent.getPointerCount());
        if (motionEvent.getActionMasked() == 2) {
            for (int i2 = 0; i2 < motionEvent.getPointerCount(); i2++) {
                jSONArray.add(createJSONObject(motionEvent, i, i2));
            }
        } else if (isPointerNumChanged(motionEvent)) {
            jSONArray.add(createJSONObject(motionEvent, -1, motionEvent.getActionIndex()));
        }
        HashMap map = new HashMap();
        map.put(WXGestureType.GestureInfo.HISTORICAL_XY, jSONArray);
        if (str != null) {
            map.put("state", str);
        }
        return map;
    }

    private JSONObject createJSONObject(MotionEvent motionEvent, int i, int i2) {
        PointF eventLocInScreenCoordinate;
        PointF eventLocInPageCoordinate;
        if (i == -1) {
            eventLocInPageCoordinate = getEventLocInPageCoordinate(motionEvent, i2);
            eventLocInScreenCoordinate = getEventLocInScreenCoordinate(motionEvent, i2);
        } else {
            PointF eventLocInPageCoordinate2 = getEventLocInPageCoordinate(motionEvent, i2, i);
            eventLocInScreenCoordinate = getEventLocInScreenCoordinate(motionEvent, i2, i);
            eventLocInPageCoordinate = eventLocInPageCoordinate2;
        }
        JSONObject jSONObjectCreateJSONObject = createJSONObject(eventLocInScreenCoordinate, eventLocInPageCoordinate, motionEvent.getPointerId(i2));
        float pressure = motionEvent.getPressure();
        if (pressure > 0.0f && pressure < 1.0f) {
            jSONObjectCreateJSONObject.put(AbsoluteConst.INSTALL_OPTIONS_FORCE, (Object) Float.valueOf(motionEvent.getPressure()));
        }
        return jSONObjectCreateJSONObject;
    }

    private List<Map<String, Object>> createMultipleFireEventParam(MotionEvent motionEvent, String str) {
        ArrayList arrayList = new ArrayList(motionEvent.getHistorySize() + 1);
        arrayList.add(createFireEventParam(motionEvent, -1, str));
        return arrayList;
    }

    private void finishDisallowInterceptTouchEvent(View view) {
        if (view.getParent() != null) {
            view.getParent().requestDisallowInterceptTouchEvent(false);
        }
    }

    private PointF getEventLocInPageCoordinate(MotionEvent motionEvent, int i) {
        return getEventLocInPageCoordinate(motionEvent, i, -1);
    }

    private PointF getEventLocInScreenCoordinate(MotionEvent motionEvent, int i) {
        return getEventLocInScreenCoordinate(motionEvent, i, -1);
    }

    private List<Map<String, Object>> getHistoricalEvents(MotionEvent motionEvent) {
        ArrayList arrayList = new ArrayList(motionEvent.getHistorySize());
        if (motionEvent.getActionMasked() == 2) {
            for (int i = 0; i < motionEvent.getHistorySize(); i++) {
                arrayList.add(createFireEventParam(motionEvent, i, null));
            }
        }
        return arrayList;
    }

    private String getPanEventAction(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        return action != 0 ? action != 1 ? action != 2 ? action != 3 ? "unknown" : "end" : MOVE : "end" : "start";
    }

    private boolean handleMotionEvent(WXGestureType wXGestureType, MotionEvent motionEvent) {
        if (this.component.getHover() != null) {
            this.component.getHover().handleMotionEvent(wXGestureType, motionEvent);
        }
        if (!this.component.containsGesture(wXGestureType)) {
            return false;
        }
        Iterator<Map<String, Object>> it = createMultipleFireEventParam(motionEvent, null).iterator();
        while (it.hasNext()) {
            this.component.fireEvent(wXGestureType.toString(), it.next());
        }
        return true;
    }

    private boolean handlePanMotionEvent(MotionEvent motionEvent) {
        WXGestureType wXGestureType = this.mPendingPan;
        if (wXGestureType == null) {
            return false;
        }
        String panEventAction = (wXGestureType == WXGestureType.HighLevelGesture.HORIZONTALPAN || wXGestureType == WXGestureType.HighLevelGesture.VERTICALPAN) ? getPanEventAction(motionEvent) : null;
        if (!this.component.containsGesture(this.mPendingPan)) {
            return false;
        }
        if (this.mIsPreventMoveEvent && MOVE.equals(panEventAction)) {
            return true;
        }
        Iterator<Map<String, Object>> it = createMultipleFireEventParam(motionEvent, panEventAction).iterator();
        while (it.hasNext()) {
            this.component.fireEvent(this.mPendingPan.toString(), it.next());
        }
        if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            this.mPendingPan = null;
        }
        return true;
    }

    private boolean hasSameOrientationWithParent() {
        return (this.mParentOrientation == 0 && this.component.containsGesture(WXGestureType.HighLevelGesture.HORIZONTALPAN)) || (this.mParentOrientation == 1 && this.component.containsGesture(WXGestureType.HighLevelGesture.VERTICALPAN));
    }

    public static boolean hasStopPropagation(WXComponent wXComponent) {
        WXEvent events = wXComponent.getEvents();
        if (events == null) {
            return false;
        }
        int size = events.size();
        for (int i = 0; i < size && i < events.size(); i++) {
            if (isStopPropagation(events.get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean isParentScrollable() {
        Scrollable parentScroller;
        WXComponent wXComponent = this.component;
        return wXComponent == null || (parentScroller = wXComponent.getParentScroller()) == null || parentScroller.isScrollable();
    }

    private boolean isPointerNumChanged(MotionEvent motionEvent) {
        return motionEvent.getActionMasked() == 0 || motionEvent.getActionMasked() == 5 || motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 6 || motionEvent.getActionMasked() == 3;
    }

    public static boolean isStopPropagation(String str) {
        return Constants.Event.STOP_PROPAGATION.equals(str) || Constants.Event.STOP_PROPAGATION_RAX.equals(str);
    }

    private boolean shouldBubbleTouchEvent(MotionEvent motionEvent) throws InterruptedException {
        int i;
        if (!hasStopPropagation(this.component)) {
            return true;
        }
        if (this.shouldBubbleInterval > 0 && (i = this.shouldBubbleCallRemainTimes) > 0) {
            this.shouldBubbleCallRemainTimes = i - 1;
            return this.shouldBubbleResult;
        }
        Map<String, Object> mapCreateFireEventParam = createFireEventParam(motionEvent, -1, null);
        mapCreateFireEventParam.put("type", "touch");
        if (motionEvent.getAction() == 0) {
            mapCreateFireEventParam.put("action", "start");
        } else if (motionEvent.getAction() == 3 || motionEvent.getAction() == 1) {
            mapCreateFireEventParam.put("action", "end");
        } else {
            mapCreateFireEventParam.put("action", MOVE);
        }
        WXEvent events = this.component.getEvents();
        String str = Constants.Event.STOP_PROPAGATION;
        if (!events.contains(Constants.Event.STOP_PROPAGATION)) {
            str = Constants.Event.STOP_PROPAGATION_RAX;
        }
        EventResult eventResultFireEventWait = this.component.fireEventWait(str, mapCreateFireEventParam);
        if (eventResultFireEventWait.isSuccess() && eventResultFireEventWait.getResult() != null) {
            this.shouldBubbleResult = !WXUtils.getBoolean(eventResultFireEventWait.getResult(), Boolean.valueOf(!this.shouldBubbleResult)).booleanValue();
        }
        this.shouldBubbleCallRemainTimes = this.shouldBubbleInterval;
        return this.shouldBubbleResult;
    }

    public void addOnTouchListener(View.OnTouchListener onTouchListener) {
        if (onTouchListener != null) {
            this.mTouchListeners.add(onTouchListener);
        }
    }

    public boolean isRequestDisallowInterceptTouchEvent() {
        return this.requestDisallowInterceptTouchEvent;
    }

    public boolean isTouchEventConsumedByAdvancedGesture() {
        return this.mIsTouchEventConsumed;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public void onLongPress(MotionEvent motionEvent) {
        WXComponent wXComponent = this.component;
        WXGestureType.HighLevelGesture highLevelGesture = WXGestureType.HighLevelGesture.LONG_PRESS;
        if (wXComponent.containsGesture(highLevelGesture)) {
            List<Map<String, Object>> listCreateMultipleFireEventParam = createMultipleFireEventParam(motionEvent, null);
            this.component.getInstance().fireEvent(this.component.getRef(), highLevelGesture.toString(), listCreateMultipleFireEventParam.get(listCreateMultipleFireEventParam.size() - 1));
            this.mIsTouchEventConsumed = true;
        }
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        boolean zHandlePanMotionEvent;
        if (motionEvent == null || motionEvent2 == null) {
            return false;
        }
        WXGestureType.HighLevelGesture highLevelGesture = Math.abs(motionEvent2.getX() - motionEvent.getX()) > Math.abs(motionEvent2.getY() - motionEvent.getY()) ? WXGestureType.HighLevelGesture.HORIZONTALPAN : WXGestureType.HighLevelGesture.VERTICALPAN;
        WXGestureType wXGestureType = this.mPendingPan;
        if (wXGestureType == WXGestureType.HighLevelGesture.HORIZONTALPAN || wXGestureType == WXGestureType.HighLevelGesture.VERTICALPAN) {
            zHandlePanMotionEvent = handlePanMotionEvent(motionEvent2);
        } else {
            if (this.component.containsGesture(highLevelGesture)) {
                ViewParent parent = this.component.getRealView().getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                WXGestureType wXGestureType2 = this.mPendingPan;
                if (wXGestureType2 != null) {
                    handleMotionEvent(wXGestureType2, motionEvent2);
                }
                this.mPendingPan = highLevelGesture;
                this.component.fireEvent(highLevelGesture.toString(), createFireEventParam(motionEvent2, -1, "start"));
            } else if (!containsSimplePan()) {
                WXComponent wXComponent = this.component;
                WXGestureType.HighLevelGesture highLevelGesture2 = WXGestureType.HighLevelGesture.SWIPE;
                if (!wXComponent.containsGesture(highLevelGesture2) || this.swipeDownTime == motionEvent.getEventTime()) {
                    zHandlePanMotionEvent = false;
                } else {
                    this.swipeDownTime = motionEvent.getEventTime();
                    List<Map<String, Object>> listCreateMultipleFireEventParam = createMultipleFireEventParam(motionEvent2, null);
                    Map<String, Object> map = listCreateMultipleFireEventParam.get(listCreateMultipleFireEventParam.size() - 1);
                    if (Math.abs(f) > Math.abs(f2)) {
                        map.put("direction", f > 0.0f ? "left" : "right");
                    } else {
                        map.put("direction", f2 > 0.0f ? "up" : "down");
                    }
                    this.component.getInstance().fireEvent(this.component.getRef(), highLevelGesture2.toString(), map);
                }
            } else if (this.panDownTime != motionEvent.getEventTime()) {
                this.panDownTime = motionEvent.getEventTime();
                this.mPendingPan = WXGestureType.HighLevelGesture.PAN_END;
                this.component.fireEvent(WXGestureType.HighLevelGesture.PAN_START.toString(), createFireEventParam(motionEvent, -1, null));
            } else {
                this.component.fireEvent(WXGestureType.HighLevelGesture.PAN_MOVE.toString(), createFireEventParam(motionEvent2, -1, null));
            }
            zHandlePanMotionEvent = true;
        }
        this.mIsTouchEventConsumed = this.mIsTouchEventConsumed || zHandlePanMotionEvent;
        return zHandlePanMotionEvent;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x007f A[Catch: Exception -> 0x013c, TryCatch #0 {Exception -> 0x013c, blocks: (B:6:0x0008, B:8:0x0012, B:10:0x0018, B:11:0x001e, B:13:0x0024, B:14:0x0030, B:42:0x00f1, B:44:0x00f9, B:46:0x0101, B:48:0x010b, B:50:0x0113, B:51:0x011c, B:53:0x0120, B:55:0x0138, B:25:0x0047, B:27:0x005e, B:28:0x0077, B:29:0x007f, B:31:0x0096, B:32:0x00ae, B:34:0x00b6, B:36:0x00bc, B:38:0x00c8, B:39:0x00cb, B:41:0x00da), top: B:60:0x0008 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00ae A[Catch: Exception -> 0x013c, TryCatch #0 {Exception -> 0x013c, blocks: (B:6:0x0008, B:8:0x0012, B:10:0x0018, B:11:0x001e, B:13:0x0024, B:14:0x0030, B:42:0x00f1, B:44:0x00f9, B:46:0x0101, B:48:0x010b, B:50:0x0113, B:51:0x011c, B:53:0x0120, B:55:0x0138, B:25:0x0047, B:27:0x005e, B:28:0x0077, B:29:0x007f, B:31:0x0096, B:32:0x00ae, B:34:0x00b6, B:36:0x00bc, B:38:0x00c8, B:39:0x00cb, B:41:0x00da), top: B:60:0x0008 }] */
    @Override // android.view.View.OnTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouch(android.view.View r8, android.view.MotionEvent r9) {
        /*
            Method dump skipped, instructions count: 323
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.ui.view.gesture.WXGesture.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }

    public boolean removeTouchListener(View.OnTouchListener onTouchListener) {
        if (onTouchListener != null) {
            return this.mTouchListeners.remove(onTouchListener);
        }
        return false;
    }

    public void setPreventMoveEvent(boolean z) {
        this.mIsPreventMoveEvent = z;
    }

    public void setRequestDisallowInterceptTouchEvent(boolean z) {
        this.requestDisallowInterceptTouchEvent = z;
    }

    private PointF getEventLocInPageCoordinate(MotionEvent motionEvent, int i, int i2) {
        float historicalY;
        float x;
        if (i2 == -1) {
            x = motionEvent.getX(i);
            historicalY = motionEvent.getY(i);
        } else {
            float historicalX = motionEvent.getHistoricalX(i, i2);
            historicalY = motionEvent.getHistoricalY(i, i2);
            x = historicalX;
        }
        return getEventLocInPageCoordinate(x, historicalY);
    }

    private PointF getEventLocInScreenCoordinate(MotionEvent motionEvent, int i, int i2) {
        float historicalY;
        float x;
        if (i2 == -1) {
            x = motionEvent.getX(i);
            historicalY = motionEvent.getY(i);
        } else {
            float historicalX = motionEvent.getHistoricalX(i, i2);
            historicalY = motionEvent.getHistoricalY(i, i2);
            x = historicalX;
        }
        return getEventLocInScreenCoordinate(x, historicalY);
    }

    private PointF getEventLocInPageCoordinate(float f, float f2) {
        this.locEventOffset.set(f, f2);
        this.locLeftTop.set(0.0f, 0.0f);
        this.component.computeVisiblePointInViewCoordinate(this.locLeftTop);
        PointF pointF = this.locEventOffset;
        PointF pointF2 = this.locLeftTop;
        pointF.offset(pointF2.x, pointF2.y);
        return new PointF(WXViewUtils.getWebPxByWidth(this.locEventOffset.x, this.component.getInstance().getInstanceViewPortWidthWithFloat()), WXViewUtils.getWebPxByWidth(this.locEventOffset.y, this.component.getInstance().getInstanceViewPortWidthWithFloat()));
    }

    private PointF getEventLocInScreenCoordinate(float f, float f2) {
        this.globalRect.set(0, 0, 0, 0);
        this.globalOffset.set(0, 0);
        this.globalEventOffset.set((int) f, (int) f2);
        this.component.getRealView().getGlobalVisibleRect(this.globalRect, this.globalOffset);
        Point point = this.globalEventOffset;
        Point point2 = this.globalOffset;
        point.offset(point2.x, point2.y);
        return new PointF(WXViewUtils.getWebPxByWidth(this.globalEventOffset.x, this.component.getInstance().getInstanceViewPortWidthWithFloat()), WXViewUtils.getWebPxByWidth(this.globalEventOffset.y, this.component.getInstance().getInstanceViewPortWidthWithFloat()));
    }

    private JSONObject createJSONObject(PointF pointF, PointF pointF2, float f) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(WXGestureType.GestureInfo.PAGE_X, (Object) Float.valueOf(pointF2.x));
        jSONObject.put(WXGestureType.GestureInfo.PAGE_Y, (Object) Float.valueOf(pointF2.y));
        jSONObject.put(WXGestureType.GestureInfo.SCREEN_X, (Object) Float.valueOf(pointF.x));
        jSONObject.put(WXGestureType.GestureInfo.SCREEN_Y, (Object) Float.valueOf(pointF.y));
        jSONObject.put(WXGestureType.GestureInfo.POINTER_ID, (Object) Float.valueOf(f));
        return jSONObject;
    }
}
