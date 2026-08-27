package io.dcloud.feature.uniapp.ui.component;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.ui.action.GraphicActionAnimation;
import com.taobao.weex.ui.animation.WXAnimationBean;
import io.dcloud.feature.uniapp.AbsSDKInstance;
import io.dcloud.feature.uniapp.ui.AbsAnimationHolder;
import io.dcloud.feature.uniapp.ui.animation.UniAnimationBean;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class UniAnimationHolder implements AbsAnimationHolder {
    private String callback;
    private WXAnimationBean wxAnimationBean;

    public UniAnimationHolder(UniAnimationBean uniAnimationBean, String str) {
        this.wxAnimationBean = uniAnimationBean;
        this.callback = str;
    }

    @Override // io.dcloud.feature.uniapp.ui.AbsAnimationHolder
    public void execute(AbsSDKInstance absSDKInstance, AbsBasicComponent absBasicComponent) {
        if (absSDKInstance == null || absBasicComponent == null) {
            return;
        }
        GraphicActionAnimation graphicActionAnimation = new GraphicActionAnimation((WXSDKInstance) absSDKInstance, absBasicComponent.getRef(), this.wxAnimationBean, this.callback);
        WXSDKManager.getInstance().getWXRenderManager().postGraphicAction(graphicActionAnimation.getPageId(), graphicActionAnimation);
    }
}
