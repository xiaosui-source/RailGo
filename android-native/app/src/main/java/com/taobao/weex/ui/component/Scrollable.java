package com.taobao.weex.ui.component;

import android.view.ViewGroup;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public interface Scrollable {
    void bindAppearEvent(WXComponent wXComponent);

    void bindDisappearEvent(WXComponent wXComponent);

    void bindStickStyle(WXComponent wXComponent);

    int getOrientation();

    String getRef();

    int getScrollX();

    int getScrollY();

    ViewGroup getView();

    boolean isScrollable();

    void scrollTo(WXComponent wXComponent, Map<String, Object> map);

    void unbindAppearEvent(WXComponent wXComponent);

    void unbindDisappearEvent(WXComponent wXComponent);

    void unbindStickStyle(WXComponent wXComponent);
}
