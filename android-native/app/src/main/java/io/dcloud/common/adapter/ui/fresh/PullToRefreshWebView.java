package io.dcloud.common.adapter.ui.fresh;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class PullToRefreshWebView extends PullToRefreshBase<View> {
    public PullToRefreshWebView(Context context) {
        super(context);
    }

    @Override // io.dcloud.common.adapter.ui.fresh.PullToRefreshBase
    protected boolean isReadyForPullDown() {
        return this.mRefreshableView.getScrollY() == 0;
    }

    @Override // io.dcloud.common.adapter.ui.fresh.PullToRefreshBase
    protected boolean isReadyForPullUp() {
        float f = this.mRefreshableView.getContext().getResources().getDisplayMetrics().density;
        int measuredHeight = this.mRefreshableView.getMeasuredHeight();
        T t = this.mRefreshableView;
        if (t instanceof WebView) {
            measuredHeight = ((WebView) t).getContentHeight();
        }
        return ((double) this.mRefreshableView.getScrollY()) >= Math.floor(Double.valueOf(String.valueOf(((float) measuredHeight) * f)).doubleValue()) - ((double) this.mRefreshableView.getHeight());
    }

    @Override // android.view.View
    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
    }
}
