package com.taobao.weex.ui.component;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.Component;
import com.taobao.weex.common.Constants;
import com.taobao.weex.layout.ContentBoxMeasurement;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.list.template.TemplateDom;
import com.taobao.weex.ui.view.WXSwitchView;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXUtils;
import java.util.HashMap;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
@Component(lazyload = false)
/* loaded from: classes.dex */
public class WXSwitch extends WXComponent<WXSwitchView> {
    private CompoundButton.OnCheckedChangeListener mListener;

    @Deprecated
    public WXSwitch(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, String str, boolean z, BasicComponentData basicComponentData) {
        this(wXSDKInstance, wXVContainer, z, basicComponentData);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void addEvent(String str) {
        super.addEvent(str);
        if (str == null || !str.equals(Constants.Event.CHANGE) || getHostView() == null) {
            return;
        }
        if (this.mListener == null) {
            this.mListener = new CompoundButton.OnCheckedChangeListener() { // from class: com.taobao.weex.ui.component.WXSwitch.2
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    HashMap map = new HashMap(2);
                    map.put("value", Boolean.valueOf(z));
                    HashMap map2 = new HashMap();
                    HashMap map3 = new HashMap();
                    map3.put(Constants.Name.CHECKED, Boolean.toString(z));
                    map2.put(TemplateDom.KEY_ATTRS, map3);
                    WXSwitch.this.fireEvent(Constants.Event.CHANGE, map, map2);
                }
            };
        }
        getHostView().setOnCheckedChangeListener(this.mListener);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected void removeEventFromView(String str) {
        super.removeEventFromView(str);
        if (getHostView() == null || !Constants.Event.CHANGE.equals(str)) {
            return;
        }
        getHostView().setOnCheckedChangeListener(null);
    }

    @WXComponentProp(name = Constants.Name.CHECKED)
    public void setChecked(boolean z) {
        getHostView().setOnCheckedChangeListener(null);
        getHostView().setChecked(z);
        getHostView().setOnCheckedChangeListener(this.mListener);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    protected boolean setProperty(String str, Object obj) {
        str.getClass();
        if (!str.equals(Constants.Name.CHECKED)) {
            return super.setProperty(str, obj);
        }
        Boolean bool = WXUtils.getBoolean(obj, null);
        if (bool == null) {
            return true;
        }
        setChecked(bool.booleanValue());
        return true;
    }

    public WXSwitch(final WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, boolean z, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, z, basicComponentData);
        setContentBoxMeasurement(new ContentBoxMeasurement() { // from class: com.taobao.weex.ui.component.WXSwitch.1
            @Override // com.taobao.weex.layout.ContentBoxMeasurement
            public void layoutAfter(float f, float f2) {
            }

            @Override // com.taobao.weex.layout.ContentBoxMeasurement
            public void layoutBefore() {
            }

            @Override // com.taobao.weex.layout.ContentBoxMeasurement
            public void measureInternal(float f, float f2, int i, int i2) {
                this.mMeasureWidth = 0.0f;
                this.mMeasureHeight = 0.0f;
                try {
                    new WXSwitchView(wXSDKInstance.getContext()).measure(Float.isNaN(f) ? View.MeasureSpec.makeMeasureSpec(0, 0) : View.MeasureSpec.makeMeasureSpec((int) f, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, 0));
                    this.mMeasureWidth = r3.getMeasuredWidth();
                    this.mMeasureHeight = r3.getMeasuredHeight();
                } catch (RuntimeException e) {
                    WXLogUtils.e(WXLogUtils.getStackTrace(e));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXComponent
    public WXSwitchView initComponentHostView(Context context) {
        return new WXSwitchView(context);
    }
}
