package com.alibaba.android.bindingx.plugin.weex;

import android.content.Context;
import com.alibaba.android.bindingx.core.BindingXCore;
import com.alibaba.android.bindingx.core.IEventHandler;
import com.alibaba.android.bindingx.core.PlatformManager;
import com.alibaba.android.bindingx.core.internal.ExpressionPair;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import java.util.List;
import java.util.Map;

@Deprecated
/* loaded from: classes.dex */
public final class WXExpressionBindingModule extends WXSDKEngine.DestroyableModule {
    private BindingXCore mExpressionBindingCore;
    private PlatformManager mPlatformManager;

    @JSMethod
    @Deprecated
    public void enableBinding(String str, String str2) {
        if (this.mPlatformManager == null) {
            this.mPlatformManager = WXBindingXModule.createPlatformManager(this.mWXSDKInstance);
        }
        if (this.mExpressionBindingCore == null) {
            BindingXCore bindingXCore = new BindingXCore(this.mPlatformManager);
            this.mExpressionBindingCore = bindingXCore;
            bindingXCore.registerEventHandler("scroll", new BindingXCore.ObjectCreator<IEventHandler, Context, PlatformManager>() { // from class: com.alibaba.android.bindingx.plugin.weex.WXExpressionBindingModule.1
                @Override // com.alibaba.android.bindingx.core.BindingXCore.ObjectCreator
                public IEventHandler createWith(Context context, PlatformManager platformManager, Object... objArr) {
                    return new BindingXScrollHandler(context, platformManager, objArr);
                }
            });
        }
    }

    @JSMethod
    @Deprecated
    public void createBinding(String str, String str2, String str3, List<Map<String, Object>> list, final JSCallback jSCallback) {
        enableBinding(null, null);
        this.mExpressionBindingCore.doBind(str, null, str2, null, ExpressionPair.create(null, str3), list, null, new BindingXCore.JavaScriptCallback() { // from class: com.alibaba.android.bindingx.plugin.weex.WXExpressionBindingModule.2
            @Override // com.alibaba.android.bindingx.core.BindingXCore.JavaScriptCallback
            public void callback(Object obj) {
                JSCallback jSCallback2 = jSCallback;
                if (jSCallback2 != null) {
                    jSCallback2.invokeAndKeepAlive(obj);
                }
            }
        }, this.mWXSDKInstance == null ? null : this.mWXSDKInstance.getContext(), this.mWXSDKInstance != null ? this.mWXSDKInstance.getInstanceId() : null, new Object[0]);
    }

    @JSMethod
    @Deprecated
    public void disableBinding(String str, String str2) {
        BindingXCore bindingXCore = this.mExpressionBindingCore;
        if (bindingXCore != null) {
            bindingXCore.doUnbind(str, str2);
        }
    }

    @JSMethod
    @Deprecated
    public void disableAll() {
        BindingXCore bindingXCore = this.mExpressionBindingCore;
        if (bindingXCore != null) {
            bindingXCore.doRelease();
        }
    }

    @Override // com.taobao.weex.common.Destroyable
    public void destroy() {
        BindingXCore bindingXCore = this.mExpressionBindingCore;
        if (bindingXCore != null) {
            bindingXCore.doRelease();
            this.mExpressionBindingCore = null;
        }
    }

    @Override // com.taobao.weex.common.WXModule
    public void onActivityPause() {
        BindingXCore bindingXCore = this.mExpressionBindingCore;
        if (bindingXCore != null) {
            bindingXCore.onActivityPause();
        }
    }

    @Override // com.taobao.weex.common.WXModule
    public void onActivityResume() {
        BindingXCore bindingXCore = this.mExpressionBindingCore;
        if (bindingXCore != null) {
            bindingXCore.onActivityResume();
        }
    }
}
