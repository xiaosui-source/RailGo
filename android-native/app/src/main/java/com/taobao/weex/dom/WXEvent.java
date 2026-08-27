package com.taobao.weex.dom;

import io.dcloud.feature.uniapp.dom.AbsEvent;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXEvent extends AbsEvent {
    @Override // io.dcloud.feature.uniapp.dom.AbsEvent, java.util.ArrayList
    public WXEvent clone() {
        WXEvent wXEvent = new WXEvent();
        wXEvent.addAll(this);
        if (getEventBindingArgs() != null) {
            wXEvent.setEventBindingArgs(getEventBindingArgs());
        }
        wXEvent.setEventBindingArgsValues(null);
        return wXEvent;
    }
}
