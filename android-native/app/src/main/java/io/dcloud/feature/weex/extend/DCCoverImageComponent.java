package io.dcloud.feature.weex.extend;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXImage;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.utils.WXUtils;
import io.dcloud.feature.uniapp.layout.UniContentBoxMeasurement;
import io.dcloud.feature.weex.adapter.GlideImageAdapter;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class DCCoverImageComponent extends WXImage {
    private int mBitmapHeight;
    private int mBitmapWidth;

    public DCCoverImageComponent(WXSDKInstance wXSDKInstance, WXVContainer wXVContainer, BasicComponentData basicComponentData) {
        super(wXSDKInstance, wXVContainer, basicComponentData);
        this.mBitmapWidth = 0;
        this.mBitmapHeight = 0;
        setContentBoxMeasurement(new UniContentBoxMeasurement() { // from class: io.dcloud.feature.weex.extend.DCCoverImageComponent.1
            @Override // com.taobao.weex.layout.ContentBoxMeasurement
            public void layoutAfter(float f, float f2) {
            }

            @Override // com.taobao.weex.layout.ContentBoxMeasurement
            public void layoutBefore() {
            }

            @Override // com.taobao.weex.layout.ContentBoxMeasurement
            public void measureInternal(float f, float f2, int i, int i2) {
                if (i == 0 || !DCCoverImageComponent.this.getStyles().containsKey("width")) {
                    this.mMeasureWidth = DCCoverImageComponent.this.mBitmapWidth;
                }
                if (i2 == 0) {
                    this.mMeasureHeight = DCCoverImageComponent.this.mBitmapHeight;
                }
            }
        });
    }

    /* renamed from: lambda$onImageFinish$0$io-dcloud-feature-weex-extend-DCCoverImageComponent, reason: not valid java name */
    /* synthetic */ void m424x2d94e741() {
        WXBridgeManager.getInstance().setStyleWidth(getInstanceId(), getRef(), this.mBitmapWidth);
        WXBridgeManager.getInstance().setStyleHeight(getInstanceId(), getRef(), this.mBitmapHeight);
    }

    @Override // com.taobao.weex.ui.component.WXImage
    public void onImageFinish(boolean z, Map map) {
        super.onImageFinish(z, map);
        this.mBitmapWidth = WXUtils.getInt(map.get("width"));
        this.mBitmapHeight = WXUtils.getInt(map.get("height"));
        if (getStyles().containsKey("height")) {
            return;
        }
        WXBridgeManager.getInstance().post(new Runnable() { // from class: io.dcloud.feature.weex.extend.DCCoverImageComponent$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m424x2d94e741();
            }
        });
    }

    @Override // com.taobao.weex.ui.component.WXImage
    protected void setImage(String str, WXImageStrategy wXImageStrategy) {
        GlideImageAdapter.setImage(str, getHostView(), getImageQuality(), wXImageStrategy);
    }
}
