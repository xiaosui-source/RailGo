package io.dcloud.feature.uniapp.ui.component;

import android.view.View;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.ui.IFComponentHolder;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXVContainer;
import io.dcloud.feature.uniapp.UniSDKInstance;
import io.dcloud.feature.uniapp.ui.AbsAnimationHolder;
import io.dcloud.feature.uniapp.ui.AbsIComponentHolder;
import io.dcloud.feature.uniapp.ui.action.AbsComponentData;
import java.lang.reflect.InvocationTargetException;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class UniComponent<T extends View> extends WXComponent<T> {
    public UniSDKInstance mUniSDKInstance;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface OnClickListener extends WXComponent.OnClickListener {
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface OnFocusChangeListener extends WXComponent.OnFocusChangeListener {
    }

    public UniComponent(UniSDKInstance uniSDKInstance, AbsVContainer absVContainer, int i, AbsComponentData absComponentData) {
        super(uniSDKInstance, (WXVContainer) absVContainer, i, (BasicComponentData) absComponentData);
        this.mUniSDKInstance = uniSDKInstance;
    }

    protected final void addClickListener(OnClickListener onClickListener) {
        super.addClickListener((WXComponent.OnClickListener) onClickListener);
    }

    protected final void addFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        super.addFocusChangeListener((WXComponent.OnFocusChangeListener) onFocusChangeListener);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public final void bindData(WXComponent wXComponent) {
        super.bindData(wXComponent);
    }

    public void bindHolder(AbsIComponentHolder absIComponentHolder) {
        super.bindHolder((IFComponentHolder) absIComponentHolder);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public void destroy() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.destroy();
        this.mUniSDKInstance = null;
    }

    public final AbsBasicComponent findUniComponent(String str) {
        if (getInstance() == null || str == null) {
            return null;
        }
        return WXSDKManager.getInstance().getWXRenderManager().getWXComponent(getInstanceId(), str);
    }

    public UniSDKInstance getUniInstance() {
        return this.mUniSDKInstance;
    }

    public void postAnimation(UniAnimationHolder uniAnimationHolder) {
        super.postAnimation((AbsAnimationHolder) uniAnimationHolder);
    }

    protected void updateStyles(UniComponent uniComponent) {
        updateStyles(uniComponent);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public final void bindHolder(IFComponentHolder iFComponentHolder) {
        super.bindHolder(iFComponentHolder);
    }

    @Override // com.taobao.weex.ui.component.WXComponent
    public final void updateStyles(WXComponent wXComponent) {
        super.updateStyles(wXComponent);
    }

    public UniComponent(UniSDKInstance uniSDKInstance, AbsVContainer absVContainer, AbsComponentData absComponentData) {
        super(uniSDKInstance, (WXVContainer) absVContainer, (BasicComponentData) absComponentData);
        this.mUniSDKInstance = uniSDKInstance;
    }
}
