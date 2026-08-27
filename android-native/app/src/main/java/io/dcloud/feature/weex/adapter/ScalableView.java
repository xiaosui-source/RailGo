package io.dcloud.feature.weex.adapter;

import android.content.Context;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.ui.view.WXFrameLayout;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class ScalableView extends WXFrameLayout {
    int mHeight;
    int mWidth;

    public ScalableView(Context context) {
        super(context);
        this.mWidth = 0;
        this.mHeight = 0;
    }

    @Override // android.view.View
    protected void onSizeChanged(final int i, final int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        try {
            if ((getComponent() instanceof ScalableViewComponent) && ((ScalableViewComponent) getComponent()).isScalable()) {
                if (this.mHeight == i2 && this.mWidth == i) {
                    return;
                }
                WXBridgeManager.getInstance().post(new Runnable() { // from class: io.dcloud.feature.weex.adapter.ScalableView.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (ScalableView.this.getComponent() == null || ScalableView.this.getComponent().getInstance() == null) {
                            return;
                        }
                        WXBridgeManager.getInstance().setStyleHeight(ScalableView.this.getComponent().getInstanceId(), ScalableView.this.getComponent().getRef(), i2, true);
                        WXBridgeManager.getInstance().setStyleWidth(ScalableView.this.getComponent().getInstanceId(), ScalableView.this.getComponent().getRef(), i, true);
                        ScalableView scalableView = ScalableView.this;
                        scalableView.mWidth = i;
                        scalableView.mHeight = i2;
                    }
                });
            }
        } catch (Exception unused) {
        }
    }
}
