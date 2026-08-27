package com.taobao.weex.ui.component.list;

import android.content.Context;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXVContainer;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class SimpleListComponent extends BasicListComponent<SimpleRecyclerView> {
    public SimpleListComponent(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, basicComponentData);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.taobao.weex.ui.component.list.BasicListComponent
    public SimpleRecyclerView generateListView(Context context, int i) {
        SimpleRecyclerView simpleRecyclerView = new SimpleRecyclerView(context);
        simpleRecyclerView.initView(context, 1, i);
        return simpleRecyclerView;
    }
}
