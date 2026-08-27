package io.dcloud.feature.weex.extend;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.utils.WXUtils;
import io.dcloud.common.util.ThrottleUtil;
import io.dcloud.feature.weex.adapter.IWeexMap;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class DCCoverViewComponent extends WXVContainer<ViewGroup> {
    HashMap<String, String> CalloutMarkerIds;
    private final ViewTreeObserver.OnDrawListener onDrawListener;
    private FrameLayout realView;
    private final ThrottleUtil throttle;

    public DCCoverViewComponent(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, basicComponentData);
        this.throttle = new ThrottleUtil();
        this.onDrawListener = new ViewTreeObserver.OnDrawListener() { // from class: io.dcloud.feature.weex.extend.DCCoverViewComponent.1
            @Override // android.view.ViewTreeObserver.OnDrawListener
            public void onDraw() {
                DCCoverViewComponent.this.throttle.throttlePost(new Runnable() { // from class: io.dcloud.feature.weex.extend.DCCoverViewComponent.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        DCCoverViewComponent.this.updateCallout();
                    }
                }, 50L);
            }
        };
        this.CalloutMarkerIds = new HashMap<>();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public void addChild(WXComponent wXComponent, int i) {
        Object obj;
        if ((wXComponent instanceof DCCoverViewComponent) && (obj = wXComponent.getAttrs().get("markerId")) != null) {
            this.CalloutMarkerIds.put(obj.toString(), wXComponent.getRef());
            if (getHostView() != 0) {
                ((ViewGroup) getHostView()).getViewTreeObserver().addOnDrawListener(this.onDrawListener);
            }
        }
        super.addChild(wXComponent, i);
    }

    public HashMap<String, String> getCalloutMarkerIds() {
        return this.CalloutMarkerIds;
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer
    public void remove(WXComponent wXComponent, boolean z) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!(wXComponent instanceof DCCoverViewComponent)) {
            super.remove(wXComponent, z);
            return;
        }
        Object obj = wXComponent.getAttrs().get("marker-id");
        if (obj == null || this.CalloutMarkerIds.get(obj).equals(wXComponent.getRef())) {
            return;
        }
        this.CalloutMarkerIds.remove(obj);
    }

    public void updateCallout() {
        if (!this.CalloutMarkerIds.isEmpty() && (getParent() instanceof IDCCoverViewUpdate) && (getParent() instanceof IWeexMap)) {
            ((IDCCoverViewUpdate) getParent()).update(this.CalloutMarkerIds);
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: io.dcloud.feature.weex.extend.DCCoverViewComponent.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                if (DCCoverViewComponent.this.getHostView() != 0) {
                    ((ViewGroup) DCCoverViewComponent.this.getHostView()).getViewTreeObserver().removeOnDrawListener(DCCoverViewComponent.this.onDrawListener);
                }
            }
        });
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsVContainer, com.taobao.weex.ui.component.WXComponent
    public ViewGroup getRealView() {
        return this.realView;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXComponent
    public ViewGroup initComponentHostView(Context context) {
        FrameLayout frameLayout;
        String string = "";
        if (getStyles().containsKey(Constants.Name.OVERFLOW)) {
            string = WXUtils.getString(getStyles().get(Constants.Name.OVERFLOW), "");
        } else if (getStyles().containsKey("overflowY")) {
            string = WXUtils.getString(getStyles().get("overflowY"), "");
        }
        this.realView = new FrameLayout(context);
        if (string.equals("scroll")) {
            ScrollView scrollView = new ScrollView(context);
            scrollView.addView(this.realView, new FrameLayout.LayoutParams(-1, -1));
            frameLayout = scrollView;
        } else {
            frameLayout = this.realView;
        }
        Object obj = getAttrs().get("slot");
        if (obj != null && obj.equals("callout")) {
            frameLayout.setVisibility(4);
            frameLayout.getViewTreeObserver().addOnDrawListener(this.onDrawListener);
        }
        return frameLayout;
    }
}
