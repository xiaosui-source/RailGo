package com.taobao.weex.ui.action;

import android.widget.ScrollView;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.dom.transition.WXTransition;
import com.taobao.weex.ui.component.WXBaseScroller;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.utils.WXLogUtils;
import java.util.Map;
import java.util.Set;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class GraphicActionCreateBody extends GraphicActionAbstractAddElement {
    private WXComponent component;

    public GraphicActionCreateBody(WXSDKInstance wXSDKInstance, String str, String str2, Map<String, String> map, Map<String, String> map2, Set<String> set, float[] fArr, float[] fArr2, float[] fArr3) {
        super(wXSDKInstance, str);
        this.mComponentType = str2;
        this.mStyle = map;
        this.mAttributes = map2;
        this.mEvents = set;
        this.mMargins = fArr;
        this.mPaddings = fArr2;
        this.mBorders = fArr3;
        if (wXSDKInstance.getContext() == null) {
            return;
        }
        WXComponent wXComponentCreateComponent = createComponent(wXSDKInstance, null, new BasicComponentData(getRef(), this.mComponentType, null));
        this.component = wXComponentCreateComponent;
        if (wXComponentCreateComponent == null) {
            return;
        }
        wXComponentCreateComponent.setTransition(WXTransition.fromMap(wXComponentCreateComponent.getStyles(), this.component));
    }

    @Override // com.taobao.weex.ui.action.GraphicActionAbstractAddElement, com.taobao.weex.ui.action.IExecutable
    public void executeAction() {
        super.executeAction();
        try {
            this.component.createView();
            WXComponent wXComponent = this.component;
            wXComponent.applyLayoutAndEvent(wXComponent);
            WXComponent wXComponent2 = this.component;
            wXComponent2.bindData(wXComponent2);
            WXSDKInstance wXSDKIntance = getWXSDKIntance();
            WXComponent wXComponent3 = this.component;
            if (wXComponent3 instanceof WXBaseScroller) {
                WXBaseScroller wXBaseScroller = (WXBaseScroller) wXComponent3;
                if (wXBaseScroller.getInnerView() instanceof ScrollView) {
                    wXSDKIntance.setRootScrollView((ScrollView) wXBaseScroller.getInnerView());
                }
            }
            wXSDKIntance.onRootCreated(this.component);
            if (wXSDKIntance.getRenderStrategy() != WXRenderStrategy.APPEND_ONCE) {
                wXSDKIntance.onCreateFinish();
            }
        } catch (Exception e) {
            WXLogUtils.e("create body failed.", e);
        }
    }
}
