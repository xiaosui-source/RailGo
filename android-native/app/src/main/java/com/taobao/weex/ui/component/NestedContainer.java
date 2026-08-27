package com.taobao.weex.ui.component;

import android.view.ViewGroup;
import com.taobao.weex.WXSDKInstance;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public interface NestedContainer {

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface OnNestedInstanceEventListener {
        void onCreated(NestedContainer nestedContainer, WXSDKInstance wXSDKInstance);

        void onException(NestedContainer nestedContainer, String str, String str2);

        boolean onPreCreate(NestedContainer nestedContainer, String str);

        String transformUrl(String str);
    }

    ViewGroup getViewContainer();

    void reload();

    void renderNewURL(String str);

    void setOnNestEventListener(OnNestedInstanceEventListener onNestedInstanceEventListener);
}
