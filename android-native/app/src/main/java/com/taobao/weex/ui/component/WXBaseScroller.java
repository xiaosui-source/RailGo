package com.taobao.weex.ui.component;

import android.view.ViewGroup;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.Component;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.view.WXScrollView;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
@Component(lazyload = false)
/* loaded from: classes.dex */
public class WXBaseScroller extends WXVContainer<ViewGroup> implements WXScrollView.WXScrollViewListener, Scrollable {
    public WXBaseScroller(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, String str, boolean z, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, str, z, basicComponentData);
    }

    public void bindAppearEvent(WXComponent wXComponent) {
    }

    public void bindDisappearEvent(WXComponent wXComponent) {
    }

    public void bindStickStyle(WXComponent wXComponent) {
    }

    public ViewGroup getInnerView() {
        return null;
    }

    public int getOrientation() {
        return 0;
    }

    public Map<String, Object> getScrollEvent(int i, int i2) {
        return null;
    }

    public int getScrollX() {
        return 0;
    }

    public int getScrollY() {
        return 0;
    }

    public Map<String, Map<String, WXComponent>> getStickMap() {
        return null;
    }

    public boolean isScrollable() {
        return false;
    }

    @Override // com.taobao.weex.ui.view.WXScrollView.WXScrollViewListener
    public void onScroll(WXScrollView wXScrollView, int i, int i2) {
    }

    @Override // com.taobao.weex.ui.view.WXScrollView.WXScrollViewListener
    public void onScrollChanged(WXScrollView wXScrollView, int i, int i2, int i3, int i4) {
    }

    @Override // com.taobao.weex.ui.view.WXScrollView.WXScrollViewListener
    public void onScrollStopped(WXScrollView wXScrollView, int i, int i2) {
    }

    @Override // com.taobao.weex.ui.view.WXScrollView.WXScrollViewListener
    public void onScrollToBottom(WXScrollView wXScrollView, int i, int i2) {
    }

    public void scrollTo(WXComponent wXComponent, Map<String, Object> map) {
    }

    public void unbindAppearEvent(WXComponent wXComponent) {
    }

    public void unbindDisappearEvent(WXComponent wXComponent) {
    }

    public void unbindStickStyle(WXComponent wXComponent) {
    }

    public WXBaseScroller(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, boolean z, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, z, basicComponentData);
    }

    public WXBaseScroller(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, basicComponentData);
    }
}
