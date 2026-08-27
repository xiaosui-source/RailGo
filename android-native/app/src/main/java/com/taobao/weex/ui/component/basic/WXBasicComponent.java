package com.taobao.weex.ui.component.basic;

import android.view.View;
import com.taobao.weex.dom.CSSShorthand;
import com.taobao.weex.dom.WXAttr;
import com.taobao.weex.dom.WXEvent;
import com.taobao.weex.dom.WXStyle;
import com.taobao.weex.ui.action.BasicComponentData;
import io.dcloud.feature.uniapp.ui.action.AbsComponentData;
import io.dcloud.feature.uniapp.ui.component.AbsBasicComponent;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public abstract class WXBasicComponent<T extends View> extends AbsBasicComponent {
    public WXBasicComponent(AbsComponentData absComponentData) {
        super(absComponentData);
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsBasicComponent
    public final WXAttr getAttrs() {
        return (WXAttr) super.getAttrs();
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsBasicComponent
    public BasicComponentData getBasicComponentData() {
        return (BasicComponentData) super.getBasicComponentData();
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsBasicComponent
    public final CSSShorthand getBorder() {
        return (CSSShorthand) super.getBorder();
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsBasicComponent
    public final WXEvent getEvents() {
        return (WXEvent) super.getEvents();
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsBasicComponent
    public final CSSShorthand getMargin() {
        return (CSSShorthand) super.getMargin();
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsBasicComponent
    public final CSSShorthand getPadding() {
        return (CSSShorthand) super.getPadding();
    }

    @Override // io.dcloud.feature.uniapp.ui.component.AbsBasicComponent
    public final WXStyle getStyles() {
        return (WXStyle) super.getStyles();
    }
}
