package com.taobao.weex.ui.component;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.Constants;
import com.taobao.weex.dom.CSSConstants;
import com.taobao.weex.dom.WXAttr;
import com.taobao.weex.layout.ContentBoxMeasurement;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.view.WXEditText;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import io.dcloud.feature.weex.WeexInstanceMgr;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class DCTextArea extends DCWXInput {
    private WXAttr attr;
    private boolean isAutoHeight;
    private boolean isLineChange;
    boolean isShowConfirm;
    private WXComponent.OnFocusChangeListener mOnFocusChangeListener;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    /* renamed from: com.taobao.weex.ui.component.DCTextArea$2, reason: invalid class name */
    class AnonymousClass2 implements WXComponent.OnFocusChangeListener {
        int count = 0;

        AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void fireEventForFocus(final TextView textView) {
            DCTextArea.this.getHostView().postDelayed(new Runnable() { // from class: com.taobao.weex.ui.component.DCTextArea.2.1
                @Override // java.lang.Runnable
                public void run() {
                    AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                    if (DCTextArea.this.keyboardHeight != 0.0f) {
                        anonymousClass2.count = 0;
                        HashMap map = new HashMap(1);
                        HashMap map2 = new HashMap(1);
                        map2.put("value", textView.getText().toString());
                        map2.put("height", Float.valueOf(DCTextArea.this.keyboardHeight));
                        map.put("detail", map2);
                        DCTextArea.this.fireEvent(Constants.Event.FOCUS, map);
                        return;
                    }
                    int i = anonymousClass2.count + 1;
                    anonymousClass2.count = i;
                    if (i <= 3) {
                        anonymousClass2.fireEventForFocus(textView);
                        return;
                    }
                    HashMap map3 = new HashMap(1);
                    HashMap map4 = new HashMap(1);
                    map4.put("value", textView.getText().toString());
                    map4.put("height", Float.valueOf(DCTextArea.this.keyboardHeight));
                    map3.put("detail", map4);
                    DCTextArea.this.fireEvent(Constants.Event.FOCUS, map3);
                }
            }, 200L);
        }

        @Override // com.taobao.weex.ui.component.WXComponent.OnFocusChangeListener
        public void onFocusChange(boolean z) {
            WXEditText hostView = DCTextArea.this.getHostView();
            if (hostView == null) {
                return;
            }
            HashMap map = new HashMap(1);
            HashMap map2 = new HashMap(1);
            map2.put("value", hostView.getText().toString());
            if (!z) {
                map2.put("cursor", Integer.valueOf(hostView.getSelectionStart()));
                map.put("detail", map2);
                DCTextArea.this.fireEvent(Constants.Event.BLUR, map);
                return;
            }
            float f = DCTextArea.this.keyboardHeight;
            if (f == 0.0f) {
                fireEventForFocus(hostView);
                return;
            }
            map2.put("height", Float.valueOf(f));
            map2.put("value", hostView.getText().toString());
            map.put("detail", map2);
            DCTextArea.this.fireEvent(Constants.Event.FOCUS, map);
        }
    }

    public DCTextArea(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, boolean z, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, z, basicComponentData);
        this.isAutoHeight = false;
        this.isLineChange = false;
        this.mOnFocusChangeListener = new AnonymousClass2();
        this.isShowConfirm = true;
        this.attr = basicComponentData.getAttrs();
        setContentBoxMeasurement(new ContentBoxMeasurement() { // from class: com.taobao.weex.ui.component.DCTextArea.1
            @Override // com.taobao.weex.layout.ContentBoxMeasurement
            public void layoutAfter(float f, float f2) {
            }

            @Override // com.taobao.weex.layout.ContentBoxMeasurement
            public void layoutBefore() {
            }

            @Override // com.taobao.weex.layout.ContentBoxMeasurement
            public void measureInternal(float f, float f2, int i, int i2) {
                if (CSSConstants.isUndefined(f)) {
                    this.mMeasureWidth = WXViewUtils.getRealPxByWidth(300.0f, DCTextArea.this.getInstance().getInstanceViewPortWidthWithFloat());
                }
                if (CSSConstants.isUndefined(f2)) {
                    if (DCTextArea.this.attr.containsKey("autoHeight") && WXUtils.getBoolean(DCTextArea.this.attr.get("autoHeight"), Boolean.FALSE).booleanValue()) {
                        this.mMeasureHeight = WXViewUtils.getRealPxByWidth(DCTextArea.this.getInstance().getDefaultFontSize() * 1.4f, DCTextArea.this.getInstance().getInstanceViewPortWidthWithFloat());
                    } else {
                        this.mMeasureHeight = WXViewUtils.getRealPxByWidth(150.0f, DCTextArea.this.getInstance().getInstanceViewPortWidthWithFloat());
                    }
                }
            }
        });
    }

    private void watchLine() {
        addTextChangedListener(new TextWatcher() { // from class: com.taobao.weex.ui.component.DCTextArea.3
            int line = 0;

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (DCTextArea.this.getHostView().getLineCount() != this.line) {
                    this.line = DCTextArea.this.getHostView().getLineCount();
                    int extendedPaddingTop = DCTextArea.this.getHostView().getExtendedPaddingTop() + DCTextArea.this.getHostView().getExtendedPaddingBottom();
                    final float lineTop = DCTextArea.this.getHostView().getLayout().getLineTop(DCTextArea.this.getHostView().getLineCount()) + extendedPaddingTop;
                    if (extendedPaddingTop == 0 && DCTextArea.this.isAutoHeight) {
                        lineTop = WXViewUtils.getRealPxByWidth(DCTextArea.this.getInstance().getDefaultFontSize() * 1.4f, DCTextArea.this.getInstance().getInstanceViewPortWidthWithFloat()) + DCTextArea.this.getHostView().getLayout().getLineTop(DCTextArea.this.getHostView().getLineCount() - 1);
                    }
                    if (DCTextArea.this.isAutoHeight && lineTop > 0.0f) {
                        WXSDKManager.getInstance().getWXBridgeManager().post(new Runnable() { // from class: com.taobao.weex.ui.component.DCTextArea.3.1
                            @Override // java.lang.Runnable
                            public void run() {
                                WXBridgeManager.getInstance().setStyleHeight(DCTextArea.this.getInstanceId(), DCTextArea.this.getRef(), lineTop);
                                WXBridgeManager.getInstance().notifyLayout(DCTextArea.this.getInstanceId());
                                WXBridgeManager.getInstance().forceLayout(DCTextArea.this.getInstanceId());
                            }
                        });
                    }
                    if (DCTextArea.this.isLineChange) {
                        HashMap map = new HashMap(3);
                        map.put("lineCount", Integer.valueOf(this.line));
                        map.put("height", Float.valueOf(WXViewUtils.getWebPxByWidth(lineTop, DCTextArea.this.getInstance().getInstanceViewPortWidthWithFloat())));
                        map.put("heightRpx", Float.valueOf(lineTop));
                        HashMap map2 = new HashMap(1);
                        map2.put("detail", map);
                        DCTextArea.this.fireEvent("linechange", map2);
                    }
                }
            }
        });
    }

    @Override // com.taobao.weex.ui.component.DCWXInput, com.taobao.weex.ui.component.WXComponent
    public void addEvent(String str) {
        super.addEvent(str);
        if (str.equals("linechange")) {
            this.isLineChange = true;
        }
    }

    @Override // com.taobao.weex.ui.component.DCWXInput
    protected void appleStyleAfterCreated(WXEditText wXEditText) {
        super.appleStyleAfterCreated(wXEditText);
        wXEditText.setSingleLine(false);
        wXEditText.setMinLines(1);
        wXEditText.setMaxLines(100);
        wXEditText.setInputType(131073);
    }

    @Override // com.taobao.weex.ui.component.DCWXInput, com.taobao.weex.ui.component.WXComponent
    public void destroy() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.destroy();
        ConfirmBar.getInstance().removeComponent(this);
    }

    @Override // com.taobao.weex.ui.component.DCWXInput
    protected float getMeasureHeight() {
        return this.isAutoHeight ? getMeasuredLineHeight() : super.getMeasureHeight();
    }

    @Override // com.taobao.weex.ui.component.DCWXInput
    protected int getVerticalGravity() {
        return 48;
    }

    @WXComponentProp(name = "autoHeight")
    public void setAutoHeight(boolean z) {
        this.isAutoHeight = z;
    }

    @Override // com.taobao.weex.ui.component.DCWXInput
    protected void setFocusAndBlur() {
        if (ismHasFocusChangeListener(this.mOnFocusChangeListener)) {
            return;
        }
        addFocusChangeListener(this.mOnFocusChangeListener);
    }

    @Override // com.taobao.weex.ui.component.DCWXInput, com.taobao.weex.ui.component.WXComponent
    protected boolean setProperty(String str, Object obj) {
        this.isConfirmHold = WXUtils.getBoolean(obj, Boolean.TRUE).booleanValue();
        return super.setProperty(str, obj);
    }

    @WXComponentProp(name = "showConfirmBar")
    public void setShowConfirmBar(boolean z) {
        this.isShowConfirm = z;
    }

    @Override // com.taobao.weex.ui.component.DCWXInput
    public void setSingleLine(boolean z) {
        getHostView().setSingleLine(false);
    }

    @Override // com.taobao.weex.ui.component.DCWXInput
    public void setType(String str) {
        if (getHostView() == null || str == null || getHostView() == null || ((DCWXInput) this).mType.equals(str)) {
            return;
        }
        getHostView().setInputType(131073);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.DCWXInput, com.taobao.weex.ui.component.WXComponent
    public void onHostViewInitialized(WXEditText wXEditText) {
        this.isNeedConfirm = false;
        wXEditText.setAllowDisableMovement(false);
        super.onHostViewInitialized(wXEditText);
        try {
            ConfirmBar.getInstance().createConfirmBar(getContext(), WeexInstanceMgr.self().findWebview(getInstance()).obtainApp());
            ConfirmBar.getInstance().addComponent(this);
        } catch (Exception unused) {
        }
        if (this.attr.containsKey("autoHeight") && WXUtils.getBoolean(this.attr.get("autoHeight"), Boolean.FALSE).booleanValue()) {
            WXBridgeManager.getInstance().setStyleHeight(getInstanceId(), getRef(), WXViewUtils.getRealPxByWidth(getInstance().getDefaultFontSize() * 1.4f, getInstance().getInstanceViewPortWidthWithFloat()));
        }
        watchLine();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.DCWXInput, com.taobao.weex.ui.component.WXComponent
    public void setHostLayoutParams(WXEditText wXEditText, int i, int i2, int i3, int i4, int i5, int i6) {
        super.setHostLayoutParams(wXEditText, i, i2, i3, i4, i5, i6);
    }
}
