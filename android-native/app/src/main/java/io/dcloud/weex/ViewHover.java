package io.dcloud.weex;

import android.os.Handler;
import android.view.MotionEvent;
import com.alibaba.fastjson.JSONObject;
import com.facebook.common.statfs.StatFsHelper;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.view.gesture.WXGestureType;
import java.util.Map;
import java.util.Set;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class ViewHover {
    public static final String VIEW_HOVER_EVENT = "view_hover_event";
    private WXComponent component;
    private Handler handler;
    private JSONObject hoverClass;
    private int hoverStartTime;
    private int hoverStayTime;
    private boolean hoverStopPropagation;
    private boolean isHover;
    private boolean isReceiveTouch;
    private Map<String, Object> originalStyles;
    public Runnable touchEndRunnable;
    public Runnable touchStartRunnable;

    public ViewHover(WXComponent wXComponent) {
        this(wXComponent, null);
    }

    public void destroy() {
        this.component = null;
        this.originalStyles = null;
    }

    public int getHoverStartTime() {
        return this.hoverStartTime;
    }

    public int getHoverStayTime() {
        return this.hoverStayTime;
    }

    public void handleMotionEvent(WXGestureType wXGestureType, MotionEvent motionEvent) {
        if (this.hoverClass == null || !this.isReceiveTouch) {
            return;
        }
        String string = wXGestureType.toString();
        string.getClass();
        string.hashCode();
        switch (string) {
            case "touchstart":
                this.handler.removeCallbacks(this.touchEndRunnable);
                this.handler.removeCallbacks(this.touchStartRunnable);
                this.handler.postDelayed(this.touchStartRunnable, this.hoverStartTime);
                break;
            case "touchend":
                this.isHover = true;
                this.handler.removeCallbacks(this.touchEndRunnable);
                this.handler.postDelayed(this.touchEndRunnable, this.hoverStayTime);
                break;
            case "touchcancel":
                this.isHover = true;
                this.handler.removeCallbacks(this.touchEndRunnable);
                this.handler.postDelayed(this.touchEndRunnable, this.hoverStayTime);
                break;
        }
    }

    public boolean isHoverStopPropagation() {
        return this.hoverStopPropagation;
    }

    public void setHoverStartTime(int i) {
        this.hoverStartTime = i;
    }

    public void setHoverStayTime(int i) {
        this.hoverStayTime = i;
    }

    public void setHoverStopPropagation(boolean z) {
        this.hoverStopPropagation = z;
    }

    public void setReceiveTouch(boolean z) {
        this.isReceiveTouch = z;
    }

    public void update(JSONObject jSONObject) {
        if (jSONObject == null) {
            return;
        }
        JSONObject jSONObject2 = this.hoverClass;
        if (jSONObject2 == null) {
            this.hoverClass = jSONObject;
        } else {
            jSONObject2.putAll(jSONObject);
        }
    }

    public Map<String, Object> updateStatusAndGetUpdateStyles(boolean z) {
        JSONObject jSONObject = new JSONObject();
        if (z) {
            return this.hoverClass;
        }
        if (this.originalStyles != null) {
            Set<String> setKeySet = this.hoverClass.keySet();
            jSONObject = new JSONObject();
            for (String str : setKeySet) {
                if (this.originalStyles.containsKey(str)) {
                    jSONObject.put((JSONObject) str, (String) this.originalStyles.get(str));
                } else {
                    jSONObject.put((JSONObject) str, "");
                }
            }
        }
        return jSONObject;
    }

    public ViewHover(WXComponent wXComponent, JSONObject jSONObject) {
        this.hoverStopPropagation = false;
        this.hoverClass = null;
        this.hoverStartTime = 50;
        this.hoverStayTime = StatFsHelper.DEFAULT_DISK_YELLOW_LEVEL_IN_MB;
        this.isHover = false;
        this.isReceiveTouch = true;
        this.handler = new Handler();
        this.touchStartRunnable = new Runnable() { // from class: io.dcloud.weex.ViewHover.1
            @Override // java.lang.Runnable
            public void run() {
                if (ViewHover.this.isHover || ViewHover.this.component == null || ViewHover.this.component.getInstance() == null) {
                    return;
                }
                ViewHover viewHover = ViewHover.this;
                viewHover.originalStyles = viewHover.component.getStyles().mo387clone();
                ViewHover.this.component.setHoverClassStatus(true);
            }
        };
        this.touchEndRunnable = new Runnable() { // from class: io.dcloud.weex.ViewHover.2
            @Override // java.lang.Runnable
            public void run() {
                if (ViewHover.this.component == null || ViewHover.this.component.getInstance() == null) {
                    return;
                }
                ViewHover.this.component.setHoverClassStatus(false);
                ViewHover.this.isHover = false;
            }
        };
        update(jSONObject);
        this.component = wXComponent;
    }
}
