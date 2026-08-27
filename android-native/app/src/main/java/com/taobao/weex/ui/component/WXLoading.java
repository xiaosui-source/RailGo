package com.taobao.weex.ui.component;

import android.content.Context;
import android.text.TextUtils;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.Component;
import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.list.WXListComponent;
import com.taobao.weex.ui.view.WXFrameLayout;
import com.taobao.weex.ui.view.WXLoadingLayout;
import com.taobao.weex.ui.view.refresh.core.WXSwipeLayout;
import com.taobao.weex.ui.view.refresh.wrapper.BaseBounceView;
import com.taobao.weex.utils.WXUtils;
import java.util.HashMap;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
@Component(lazyload = false)
/* loaded from: classes.dex */
public class WXLoading extends WXBaseRefresh implements WXSwipeLayout.WXOnLoadingListener {
    public static final String HIDE = "hide";

    public WXLoading(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, boolean z, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, z, basicComponentData);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public boolean canRecycled() {
        return false;
    }

    @Override // com.taobao.weex.ui.view.refresh.core.WXSwipeLayout.WXOnLoadingListener
    public void onLoading() {
        if (getEvents().contains("loading")) {
            fireEvent("loading");
        }
    }

    @Override // com.taobao.weex.ui.view.refresh.core.WXSwipeLayout.WXOnLoadingListener
    public void onPullingUp(float f, int i, float f2) {
        if (getEvents().contains(Constants.Event.ONPULLING_UP)) {
            HashMap map = new HashMap();
            map.put(Constants.Name.DISTANCE_Y, Float.valueOf(f));
            map.put(Constants.Name.PULLING_DISTANCE, Integer.valueOf(i));
            map.put(Constants.Name.VIEW_HEIGHT, Float.valueOf(f2));
            fireEvent(Constants.Event.ONPULLING_UP, map);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @WXComponentProp(name = "display")
    public void setDisplay(String str) {
        if (TextUtils.isEmpty(str) || !str.equals("hide")) {
            return;
        }
        if (((getParent() instanceof WXListComponent) || (getParent() instanceof WXBaseScroller)) && ((BaseBounceView) getParent().getHostView()).getSwipeLayout().isRefreshing()) {
            ((BaseBounceView) getParent().getHostView()).finishPullLoad();
            ((BaseBounceView) getParent().getHostView()).onLoadmoreComplete();
        }
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected boolean setProperty(String str, Object obj) {
        str.getClass();
        if (!str.equals("display")) {
            return super.setProperty(str, obj);
        }
        String string = WXUtils.getString(obj, null);
        if (string == null) {
            return true;
        }
        setDisplay(string);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXBaseRefresh, com.taobao.weex.ui.component.WXComponent
    public WXFrameLayout initComponentHostView(Context context) {
        return new WXLoadingLayout(context);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXComponent
    public void setHostLayoutParams(WXFrameLayout wXFrameLayout, int i, int i2, int i3, int i4, int i5, int i6) {
        super.setHostLayoutParams((WXLoading) wXFrameLayout, i, i2, 0, 0, 0, 0);
    }
}
