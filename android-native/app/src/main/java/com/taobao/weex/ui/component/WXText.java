package com.taobao.weex.ui.component;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.Layout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.Component;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.Constants;
import com.taobao.weex.layout.ContentBoxMeasurement;
import com.taobao.weex.layout.measurefunc.TextContentBoxMeasurement;
import com.taobao.weex.ui.ComponentCreator;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.flat.FlatComponent;
import com.taobao.weex.ui.flat.widget.TextWidget;
import com.taobao.weex.ui.view.WXTextView;
import com.taobao.weex.utils.FontDO;
import com.taobao.weex.utils.TypefaceUtil;
import com.taobao.weex.utils.WXLogUtils;
import java.lang.reflect.InvocationTargetException;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
@Component(lazyload = false)
/* loaded from: classes.dex */
public class WXText extends WXComponent<WXTextView> implements FlatComponent<TextWidget> {
    private String mFontFamily;
    private TextWidget mTextWidget;
    private BroadcastReceiver mTypefaceObserver;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class Creator implements ComponentCreator {
        @Override // com.taobao.weex.ui.ComponentCreator
        public WXComponent createInstance(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
            return new WXText(wXSDKInstance, wXVContainer, basicComponentData);
        }
    }

    @Deprecated
    public WXText(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, String str, boolean z, BasicComponentData basicComponentData) {
        this(wXSDKInstance, wXVContainer, basicComponentData);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forceRelayout() {
        WXBridgeManager.getInstance().post(new Runnable() { // from class: com.taobao.weex.ui.component.WXText.2
            @Override // java.lang.Runnable
            public void run() {
                ContentBoxMeasurement contentBoxMeasurement = WXText.this.contentBoxMeasurement;
                if (contentBoxMeasurement instanceof TextContentBoxMeasurement) {
                    ((TextContentBoxMeasurement) contentBoxMeasurement).forceRelayout();
                }
            }
        });
    }

    private void registerTypefaceObserver(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (WXEnvironment.getApplication() == null) {
            WXLogUtils.w("WXText", "ApplicationContent is null on register typeface observer");
            return;
        }
        this.mFontFamily = str;
        if (this.mTypefaceObserver != null) {
            return;
        }
        this.mTypefaceObserver = new BroadcastReceiver() { // from class: com.taobao.weex.ui.component.WXText.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                FontDO fontDO;
                String stringExtra = intent.getStringExtra(Constants.Name.FONT_FAMILY);
                if (!WXText.this.mFontFamily.equals(stringExtra) || (fontDO = TypefaceUtil.getFontDO(stringExtra)) == null || fontDO.getTypeface() == null || WXText.this.getHostView() == null) {
                    return;
                }
                Layout textLayout = WXText.this.getHostView().getTextLayout();
                if (textLayout != null) {
                    textLayout.getPaint().setTypeface(fontDO.getTypeface());
                } else {
                    WXLogUtils.d("WXText", "Layout not created");
                }
                WXBridgeManager.getInstance().markDirty(WXText.this.getInstanceId(), WXText.this.getRef(), true);
                WXText.this.forceRelayout();
            }
        };
        LocalBroadcastManager.getInstance(WXEnvironment.getApplication()).registerReceiver(this.mTypefaceObserver, new IntentFilter(TypefaceUtil.ACTION_TYPE_FACE_AVAILABLE));
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected Object convertEmptyProperty(String str, Object obj) {
        str.getClass();
        return !str.equals("color") ? !str.equals(Constants.Name.FONT_SIZE) ? super.convertEmptyProperty(str, obj) : Integer.valueOf(getInstance().getDefaultFontSize()) : "black";
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected void createViewImpl() {
        if (promoteToView(true)) {
            super.createViewImpl();
        }
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void destroy() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.destroy();
        if (WXEnvironment.getApplication() == null || this.mTypefaceObserver == null) {
            return;
        }
        WXLogUtils.d("WXText", "Unregister the typeface observer");
        LocalBroadcastManager.getInstance(WXEnvironment.getApplication()).unregisterReceiver(this.mTypefaceObserver);
        this.mTypefaceObserver = null;
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public boolean isVirtualComponent() {
        return true ^ promoteToView(true);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected void layoutDirectionDidChanged(boolean z) {
        forceRelayout();
    }

    @Override // com.taobao.weex.ui.flat.FlatComponent
    public boolean promoteToView(boolean z) {
        if (getInstance().getFlatUIContext() != null) {
            return getInstance().getFlatUIContext().promoteToView(this, z, WXText.class);
        }
        return false;
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void refreshData(WXComponent wXComponent) {
        super.refreshData(wXComponent);
        if (wXComponent instanceof WXText) {
            updateExtra(wXComponent.getExtra());
        }
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected void setAriaLabel(String str) {
        WXTextView hostView = getHostView();
        if (hostView != null) {
            hostView.setAriaLabel(str);
        }
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected boolean setProperty(String str, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        str.getClass();
        str.hashCode();
        switch (str) {
            case "fontFamily":
                if (obj != null) {
                    registerTypefaceObserver(obj.toString());
                }
            case "fontStyle":
            case "textAlign":
            case "textDecoration":
            case "fontWeight":
            case "lineHeight":
            case "color":
            case "lines":
            case "value":
            case "textOverflow":
            case "fontSize":
                return true;
            default:
                return super.setProperty(str, obj);
        }
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsBasicComponent
    public void updateExtra(Object obj) {
        super.updateExtra(obj);
        if (obj instanceof Layout) {
            Layout layout = (Layout) obj;
            if (!promoteToView(true)) {
                getOrCreateFlatWidget().updateTextDrawable(layout);
            } else {
                if (getHostView() == null || obj.equals(getHostView().getTextLayout())) {
                    return;
                }
                getHostView().setTextLayout(layout);
                getHostView().invalidate();
            }
        }
    }

    public WXText(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, basicComponentData);
        setContentBoxMeasurement(new TextContentBoxMeasurement(this));
    }

    @Override // com.taobao.weex.ui.flat.FlatComponent
    public TextWidget getOrCreateFlatWidget() {
        if (this.mTextWidget == null) {
            this.mTextWidget = new TextWidget(getInstance().getFlatUIContext());
        }
        return this.mTextWidget;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXComponent
    public WXTextView initComponentHostView(Context context) {
        WXTextView wXTextView = new WXTextView(context);
        wXTextView.holdComponent(this);
        return wXTextView;
    }
}
