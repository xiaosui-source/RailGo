package io.dcloud.feature.weex_text;

import android.content.Context;
import android.text.Layout;
import android.text.Spannable;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.ui.ComponentCreator;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXText;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.ui.component.richtext.WXRichTextView;
import com.taobao.weex.ui.component.richtext.node.RichTextNode;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class DCWXRichText extends WXText {

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class Creator implements ComponentCreator {
        @Override // com.taobao.weex.ui.ComponentCreator
        public WXComponent createInstance(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
            return new DCWXRichText(wXSDKInstance, wXVContainer, basicComponentData);
        }
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    static class RichTextContentBoxMeasurement extends DCTextContentBoxMeasurement {
        public RichTextContentBoxMeasurement(WXComponent wXComponent) {
            super(wXComponent);
        }

        @Override // io.dcloud.feature.weex_text.DCTextContentBoxMeasurement, com.taobao.weex.layout.measurefunc.TextContentBoxMeasurement
        protected Spanned createSpanned(String str) {
            if ((!(this.mComponent.getInstance() != null) || !(this.mComponent.getInstance().getUIContext() != null)) || TextUtils.isEmpty(this.mComponent.getInstanceId())) {
                return new SpannedString("");
            }
            Spannable spannable = RichTextNode.parse(this.mComponent.getInstance().getUIContext(), this.mComponent.getInstanceId(), this.mComponent.getRef(), str);
            updateSpannable(spannable, RichTextNode.createSpanFlag(0));
            return spannable;
        }
    }

    public DCWXRichText(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, basicComponentData);
        setContentBoxMeasurement(new RichTextContentBoxMeasurement(this));
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void updateAttrs(Map<String, Object> map) {
        super.updateAttrs(map);
        if (map.containsKey("value")) {
            WXBridgeManager.getInstance().post(new Runnable() { // from class: io.dcloud.feature.weex_text.DCWXRichText.1
                @Override // java.lang.Runnable
                public void run() {
                    if (((WXComponent) DCWXRichText.this).contentBoxMeasurement instanceof RichTextContentBoxMeasurement) {
                        ((RichTextContentBoxMeasurement) ((WXComponent) DCWXRichText.this).contentBoxMeasurement).forceRelayout();
                    }
                }
            });
        }
    }

    @Override // com.taobao.weex.ui.component.WXText, io.dcloud.feature.uniapp.ui.component.AbsBasicComponent
    public void updateExtra(Object obj) {
        super.updateExtra(obj);
        if (obj instanceof Layout) {
            Layout layout = (Layout) obj;
            if (getStyles().containsKey("height")) {
                return;
            }
            WXBridgeManager.getInstance().setStyleHeight(getInstanceId(), getRef(), layout.getHeight());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.WXText, com.taobao.weex.ui.component.WXComponent
    public WXRichTextView initComponentHostView(Context context) {
        return new WXRichTextView(context);
    }
}
