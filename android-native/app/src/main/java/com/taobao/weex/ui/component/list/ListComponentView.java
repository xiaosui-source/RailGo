package com.taobao.weex.ui.component.list;

import com.taobao.weex.ui.view.listview.WXRecyclerView;
import com.taobao.weex.ui.view.listview.adapter.RecyclerViewBaseAdapter;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public interface ListComponentView {
    WXRecyclerView getInnerView();

    RecyclerViewBaseAdapter getRecyclerViewBaseAdapter();

    void notifyStickyRemove(WXCell wXCell);

    void notifyStickyShow(WXCell wXCell);

    void setRecyclerViewBaseAdapter(RecyclerViewBaseAdapter recyclerViewBaseAdapter);

    void updateStickyView(int i);
}
