package com.taobao.weex.ui.view.listview.adapter;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public interface IOnLoadMoreListener {
    void notifyAppearStateChange(int i, int i2, int i3, int i4);

    void onBeforeScroll(int i, int i2);

    void onLoadMore(int i);
}
