package com.taobao.weex.ui.component;

import android.content.Context;
import android.text.TextUtils;
import android.widget.FrameLayout;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.Component;
import com.taobao.weex.common.Constants;
import com.taobao.weex.common.WXRuntimeException;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.view.WXCircleIndicator;
import com.taobao.weex.utils.WXResourceUtils;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import io.dcloud.feature.weex.extend.DCWXSlider;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
@Component(lazyload = false)
/* loaded from: classes.dex */
public class WXIndicator extends WXComponent<WXCircleIndicator> {
    @Deprecated
    public WXIndicator(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, String str, boolean z, BasicComponentData basicComponentData) {
        this(wXSDKInstance, wXVContainer, z, basicComponentData);
    }

    @WXComponentProp(name = Constants.Name.ITEM_COLOR)
    public void setItemColor(String str) {
        int color;
        if (TextUtils.isEmpty(str) || (color = WXResourceUtils.getColor(str)) == Integer.MIN_VALUE) {
            return;
        }
        getHostView().setPageColor(color);
        getHostView().forceLayout();
        getHostView().requestLayout();
    }

    @WXComponentProp(name = Constants.Name.ITEM_SELECTED_COLOR)
    public void setItemSelectedColor(String str) {
        int color;
        if (TextUtils.isEmpty(str) || (color = WXResourceUtils.getColor(str)) == Integer.MIN_VALUE) {
            return;
        }
        getHostView().setFillColor(color);
        getHostView().forceLayout();
        getHostView().requestLayout();
    }

    @WXComponentProp(name = Constants.Name.ITEM_SIZE)
    public void setItemSize(int i) {
        if (i < 0) {
            return;
        }
        getHostView().setRadius(WXViewUtils.getRealPxByWidth(i, getInstance().getInstanceViewPortWidthWithFloat()) / 2.0f);
        getHostView().forceLayout();
        getHostView().requestLayout();
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected boolean setProperty(String str, Object obj) throws Throwable {
        str.getClass();
        str.hashCode();
        switch (str) {
            case "itemSize":
                Integer integer = WXUtils.getInteger(obj, null);
                if (integer != null) {
                    setItemSize(integer.intValue());
                }
                return true;
            case "itemSelectedColor":
                String string = WXUtils.getString(obj, null);
                if (string != null) {
                    setItemSelectedColor(string);
                }
                return true;
            case "itemColor":
                String string2 = WXUtils.getString(obj, null);
                if (string2 != null) {
                    setItemColor(string2);
                }
                return true;
            default:
                return super.setProperty(str, obj);
        }
    }

    public void setShowIndicators(boolean z) {
        if (getHostView() == null) {
            return;
        }
        if (z) {
            getHostView().setVisibility(0);
        } else {
            getHostView().setVisibility(8);
        }
    }

    public WXIndicator(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, boolean z, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, z, basicComponentData);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXComponent
    public WXCircleIndicator initComponentHostView(Context context) {
        WXCircleIndicator wXCircleIndicator = new WXCircleIndicator(context);
        if ((getParent() instanceof WXSlider) || (getParent() instanceof DCWXSlider)) {
            return wXCircleIndicator;
        }
        if (WXEnvironment.isApkDebugable()) {
            throw new WXRuntimeException("WXIndicator initView error.");
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXComponent
    public void onHostViewInitialized(WXCircleIndicator wXCircleIndicator) {
        super.onHostViewInitialized((WXIndicator) wXCircleIndicator);
        if (getParent() instanceof WXSlider) {
            ((WXSlider) getParent()).addIndicator(this);
        } else if (getParent() instanceof DCWXSlider) {
            ((DCWXSlider) getParent()).addIndicator(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXComponent
    public void setHostLayoutParams(WXCircleIndicator wXCircleIndicator, int i, int i2, int i3, int i4, int i5, int i6) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i, i2);
        setMarginsSupportRTL(layoutParams, i3, i5, i4, i6);
        wXCircleIndicator.setLayoutParams(layoutParams);
    }
}
