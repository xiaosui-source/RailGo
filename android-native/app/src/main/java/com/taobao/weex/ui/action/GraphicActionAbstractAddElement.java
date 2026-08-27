package com.taobao.weex.ui.action;

import androidx.collection.ArrayMap;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentFactory;
import com.taobao.weex.ui.component.WXVContainer;
import io.dcloud.feature.uniapp.dom.AbsCSSShorthand;
import java.util.Map;
import java.util.Set;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public abstract class GraphicActionAbstractAddElement extends BasicGraphicAction {
    protected Map<String, String> mAttributes;
    protected float[] mBorders;
    protected String mComponentType;
    protected Set<String> mEvents;
    protected int mIndex;
    protected float[] mMargins;
    protected float[] mPaddings;
    protected String mParentRef;
    protected Map<String, String> mStyle;
    private long startTime;

    public GraphicActionAbstractAddElement(WXSDKInstance wXSDKInstance, String str) {
        super(wXSDKInstance, str);
        this.mIndex = -1;
        this.startTime = System.currentTimeMillis();
    }

    protected WXComponent createComponent(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (basicComponentData != null) {
            basicComponentData.addStyle(this.mStyle);
            basicComponentData.addAttr(this.mAttributes);
            basicComponentData.addEvent(this.mEvents);
            basicComponentData.addShorthand(this.mMargins, AbsCSSShorthand.TYPE.MARGIN);
            basicComponentData.addShorthand(this.mPaddings, AbsCSSShorthand.TYPE.PADDING);
            basicComponentData.addShorthand(this.mBorders, AbsCSSShorthand.TYPE.BORDER);
        }
        WXComponent wXComponentNewInstance = WXComponentFactory.newInstance(wXSDKInstance, wXVContainer, basicComponentData);
        WXSDKManager.getInstance().getWXRenderManager().registerComponent(getPageId(), getRef(), wXComponentNewInstance);
        Map<String, String> map = this.mStyle;
        if (map != null && map.containsKey("transform") && wXComponentNewInstance.getTransition() == null) {
            ArrayMap arrayMap = new ArrayMap(2);
            arrayMap.put("transform", this.mStyle.get("transform"));
            arrayMap.put(Constants.Name.TRANSFORM_ORIGIN, this.mStyle.get(Constants.Name.TRANSFORM_ORIGIN));
            wXComponentNewInstance.addAnimationForElement(arrayMap);
        }
        wXSDKInstance.onComponentCreate(wXComponentNewInstance, System.currentTimeMillis() - jCurrentTimeMillis);
        return wXComponentNewInstance;
    }

    @Override // com.taobao.weex.ui.action.IExecutable
    public void executeAction() {
        getWXSDKIntance().callActionAddElementTime(System.currentTimeMillis() - this.startTime);
    }

    public Map<String, String> getAttributes() {
        return this.mAttributes;
    }

    public String getComponentType() {
        return this.mComponentType;
    }

    public Set<String> getEvents() {
        return this.mEvents;
    }

    public int getIndex() {
        return this.mIndex;
    }

    public String getParentRef() {
        return this.mParentRef;
    }

    public Map<String, String> getStyle() {
        return this.mStyle;
    }
}
