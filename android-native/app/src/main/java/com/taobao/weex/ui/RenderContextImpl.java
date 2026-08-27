package com.taobao.weex.ui;

import android.text.TextUtils;
import android.view.ViewGroup;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.RenderContext;
import com.taobao.weex.ui.component.DCWXScroller;
import com.taobao.weex.ui.component.WXComponent;
import io.dcloud.feature.weex_scroller.view.DCWXScrollView;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
class RenderContextImpl implements RenderContext {
    private WXSDKInstance mWXSDKInstance;
    private Map<String, WXComponent> mRegistry = new ConcurrentHashMap();
    private List<DCWXScroller> mScrollers = new CopyOnWriteArrayList();
    private Map<String, WXComponent> mIdComponent = new ConcurrentHashMap();

    public RenderContextImpl(WXSDKInstance wXSDKInstance) {
        this.mWXSDKInstance = wXSDKInstance;
    }

    public void destroy() {
        this.mWXSDKInstance = null;
        try {
            this.mRegistry.clear();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    @Override // com.taobao.weex.dom.RenderContext
    public WXComponent getComponent(String str) {
        return this.mRegistry.get(str);
    }

    public WXComponent getComponentById(String str) {
        return this.mIdComponent.get(str);
    }

    public int getComponentCount() {
        return this.mRegistry.size();
    }

    @Override // com.taobao.weex.dom.RenderContext
    public WXSDKInstance getInstance() {
        return this.mWXSDKInstance;
    }

    public WXSDKInstance getWXSDKInstance() {
        return this.mWXSDKInstance;
    }

    public void registerComponent(String str, WXComponent wXComponent) {
        if (wXComponent instanceof DCWXScroller) {
            this.mScrollers.add((DCWXScroller) wXComponent);
        }
        if (wXComponent.getAttrs().containsKey("id")) {
            String str2 = (String) wXComponent.getAttrs().get("id");
            if (!TextUtils.isEmpty(str2)) {
                this.mIdComponent.put(str2, wXComponent);
            }
        }
        this.mRegistry.put(str, wXComponent);
    }

    public void setAllScrollerScrollable(boolean z, String str) {
        if (this.mScrollers.size() > 0) {
            Object parentScroller = getComponent(str).getParentScroller();
            while (parentScroller != null) {
                if (parentScroller instanceof DCWXScroller) {
                    DCWXScroller dCWXScroller = (DCWXScroller) parentScroller;
                    ViewGroup innerView = dCWXScroller.getInnerView();
                    if (innerView instanceof DCWXScrollView) {
                        ((DCWXScrollView) innerView).setScrollable(z);
                    }
                    parentScroller = dCWXScroller.getParentScroller();
                } else {
                    parentScroller = ((WXComponent) parentScroller).getParentScroller();
                }
            }
        }
    }

    @Override // com.taobao.weex.dom.RenderContext
    public WXComponent unregisterComponent(String str) {
        return this.mRegistry.remove(str);
    }
}
