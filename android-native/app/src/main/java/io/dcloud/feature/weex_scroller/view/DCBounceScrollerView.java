package io.dcloud.feature.weex_scroller.view;

import android.content.Context;
import com.taobao.weex.ui.component.DCWXScroller;
import com.taobao.weex.ui.view.refresh.wrapper.BaseBounceView;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class DCBounceScrollerView extends BaseBounceView<DCWXScrollView> {
    public DCBounceScrollerView(Context context, int i, DCWXScroller dCWXScroller) {
        super(context, i);
        init(context);
        if (getInnerView() != null) {
            getInnerView().setWAScroller(dCWXScroller);
        }
    }

    @Override // com.taobao.weex.ui.view.refresh.wrapper.BaseBounceView
    public void onLoadmoreComplete() {
    }

    @Override // com.taobao.weex.ui.view.refresh.wrapper.BaseBounceView
    public void onRefreshingComplete() {
    }

    @Override // com.taobao.weex.ui.view.refresh.wrapper.BaseBounceView
    public DCWXScrollView setInnerView(Context context) {
        return new DCWXScrollView(context);
    }
}
