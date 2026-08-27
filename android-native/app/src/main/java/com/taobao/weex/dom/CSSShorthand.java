package com.taobao.weex.dom;

import io.dcloud.feature.uniapp.dom.AbsCSSShorthand;
import java.lang.Enum;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class CSSShorthand<T extends Enum<? extends WXCSSProperty>> extends AbsCSSShorthand {

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public enum CORNER implements WXCSSProperty {
        BORDER_TOP_LEFT,
        BORDER_TOP_RIGHT,
        BORDER_BOTTOM_RIGHT,
        BORDER_BOTTOM_LEFT,
        ALL
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public enum EDGE implements WXCSSProperty {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        ALL
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    interface WXCSSProperty extends AbsCSSShorthand.CSSProperty {
    }

    public CSSShorthand(float[] fArr) {
        super(fArr);
    }

    public CSSShorthand() {
    }

    @Override // io.dcloud.feature.uniapp.dom.AbsCSSShorthand
    /* renamed from: clone */
    public CSSShorthand mo385clone() {
        return (CSSShorthand) super.mo385clone();
    }
}
