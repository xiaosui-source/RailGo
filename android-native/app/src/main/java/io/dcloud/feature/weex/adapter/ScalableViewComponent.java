package io.dcloud.feature.weex.adapter;

import android.content.Context;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.ui.ComponentCreator;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXDiv;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.ui.view.WXFrameLayout;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class ScalableViewComponent extends WXDiv {
    private boolean isScalable;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class Ceator implements ComponentCreator {
        @Override // com.taobao.weex.ui.ComponentCreator
        public WXComponent createInstance(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
            return new ScalableViewComponent(wXSDKInstance, wXVContainer, basicComponentData);
        }
    }

    public ScalableViewComponent(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, basicComponentData);
        this.isScalable = false;
    }

    public boolean isScalable() {
        return this.isScalable;
    }

    public void setScalable(boolean z) {
        this.isScalable = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXDiv, com.taobao.weex.ui.component.WXComponent
    public WXFrameLayout initComponentHostView(Context context) {
        ScalableView scalableView = new ScalableView(context);
        scalableView.holdComponent((WXDiv) this);
        return scalableView;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXComponent
    public void setHostLayoutParams(WXFrameLayout wXFrameLayout, int i, int i2, int i3, int i4, int i5, int i6) {
        if (isScalable()) {
            super.setHostLayoutParams((ScalableViewComponent) wXFrameLayout, -1, -1, i3, i4, i5, i6);
        } else {
            super.setHostLayoutParams((ScalableViewComponent) wXFrameLayout, i, i2, i3, i4, i5, i6);
        }
    }
}
