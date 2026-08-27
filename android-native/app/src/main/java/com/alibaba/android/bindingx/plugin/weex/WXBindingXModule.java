package com.alibaba.android.bindingx.plugin.weex;

import android.content.Context;
import android.util.Log;
import android.view.View;
import com.alibaba.android.bindingx.core.BindingXCore;
import com.alibaba.android.bindingx.core.BindingXEventType;
import com.alibaba.android.bindingx.core.IEventHandler;
import com.alibaba.android.bindingx.core.LogProxy;
import com.alibaba.android.bindingx.core.PlatformManager;
import com.alibaba.android.bindingx.core.internal.BindingXConstants;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.utils.WXViewUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class WXBindingXModule extends WXSDKEngine.DestroyableModule {
    private BindingXCore mBindingXCore;
    private PlatformManager mPlatformManager;

    public WXBindingXModule() {
    }

    WXBindingXModule(BindingXCore bindingXCore) {
        this.mBindingXCore = bindingXCore;
    }

    private void prepareInternal() {
        if (this.mPlatformManager == null) {
            this.mPlatformManager = createPlatformManager(this.mWXSDKInstance);
        }
        if (this.mBindingXCore == null) {
            BindingXCore bindingXCore = new BindingXCore(this.mPlatformManager);
            this.mBindingXCore = bindingXCore;
            bindingXCore.registerEventHandler("scroll", new BindingXCore.ObjectCreator<IEventHandler, Context, PlatformManager>() { // from class: com.alibaba.android.bindingx.plugin.weex.WXBindingXModule.1
                @Override // com.alibaba.android.bindingx.core.BindingXCore.ObjectCreator
                public IEventHandler createWith(Context context, PlatformManager platformManager, Object... objArr) {
                    return new BindingXScrollHandler(context, platformManager, objArr);
                }
            });
            this.mBindingXCore.registerEventHandler("pan", new BindingXCore.ObjectCreator<IEventHandler, Context, PlatformManager>() { // from class: com.alibaba.android.bindingx.plugin.weex.WXBindingXModule.2
                @Override // com.alibaba.android.bindingx.core.BindingXCore.ObjectCreator
                public IEventHandler createWith(Context context, PlatformManager platformManager, Object... objArr) {
                    return new BindingXGestureHandler(context, platformManager, objArr);
                }
            });
        }
    }

    @JSMethod(uiThread = false)
    public void prepare(Map<String, Object> map) {
        prepareInternal();
    }

    @JSMethod(uiThread = false)
    public Map<String, String> bind(Map<String, Object> map, final JSCallback jSCallback) {
        prepareInternal();
        BindingXCore bindingXCore = this.mBindingXCore;
        Context context = this.mWXSDKInstance == null ? null : this.mWXSDKInstance.getContext();
        String instanceId = this.mWXSDKInstance != null ? this.mWXSDKInstance.getInstanceId() : null;
        if (map == null) {
            map = Collections.EMPTY_MAP;
        }
        String strDoBind = bindingXCore.doBind(context, instanceId, map, new BindingXCore.JavaScriptCallback() { // from class: com.alibaba.android.bindingx.plugin.weex.WXBindingXModule.3
            @Override // com.alibaba.android.bindingx.core.BindingXCore.JavaScriptCallback
            public void callback(Object obj) {
                if (jSCallback != null) {
                    Log.e("触发去往前端的回调", WXBridgeManager.METHOD_CALLBACK);
                    jSCallback.invokeAndKeepAlive(obj);
                }
            }
        }, new Object[0]);
        HashMap map2 = new HashMap(2);
        map2.put(BindingXConstants.KEY_TOKEN, strDoBind);
        return map2;
    }

    @JSMethod(uiThread = false)
    public void bindAsync(Map<String, Object> map, JSCallback jSCallback, JSCallback jSCallback2) {
        Map<String, String> mapBind = bind(map, jSCallback);
        if (jSCallback2 == null || mapBind == null) {
            return;
        }
        jSCallback2.invoke(mapBind);
    }

    @JSMethod(uiThread = false)
    public void unbind(Map<String, Object> map) {
        BindingXCore bindingXCore = this.mBindingXCore;
        if (bindingXCore != null) {
            bindingXCore.doUnbind(map);
        }
    }

    @JSMethod(uiThread = false)
    public void unbindAll() {
        BindingXCore bindingXCore = this.mBindingXCore;
        if (bindingXCore != null) {
            bindingXCore.doRelease();
        }
    }

    @JSMethod(uiThread = false)
    public List<String> supportFeatures() {
        return Arrays.asList("pan", "orientation", BindingXEventType.TYPE_TIMING, "scroll", "experimentalGestureFeatures");
    }

    @JSMethod(uiThread = false)
    public void getComputedStyleAsync(String str, JSCallback jSCallback) {
        Map<String, Object> computedStyle = getComputedStyle(str);
        if (jSCallback != null) {
            jSCallback.invoke(computedStyle);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x01e6  */
    @com.taobao.weex.annotation.JSMethod(uiThread = false)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.util.Map<java.lang.String, java.lang.Object> getComputedStyle(java.lang.String r20) {
        /*
            Method dump skipped, instructions count: 780
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.android.bindingx.plugin.weex.WXBindingXModule.getComputedStyle(java.lang.String):java.util.Map");
    }

    @Override // com.taobao.weex.common.Destroyable
    public void destroy() {
        WXBridgeManager.getInstance().post(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXBindingXModule.4
            @Override // java.lang.Runnable
            public void run() {
                if (WXBindingXModule.this.mBindingXCore != null) {
                    WXBindingXModule.this.mBindingXCore.doRelease();
                    WXBindingXModule.this.mBindingXCore = null;
                }
                WXViewUpdateService.clearCallbacks();
            }
        }, null);
    }

    static PlatformManager createPlatformManager(WXSDKInstance wXSDKInstance) {
        final int instanceViewPortWidth = wXSDKInstance == null ? 750 : wXSDKInstance.getInstanceViewPortWidth();
        return new PlatformManager.Builder().withViewFinder(new PlatformManager.IViewFinder() { // from class: com.alibaba.android.bindingx.plugin.weex.WXBindingXModule.7
            @Override // com.alibaba.android.bindingx.core.PlatformManager.IViewFinder
            public View findViewBy(String str, Object... objArr) {
                if (objArr.length <= 0) {
                    return null;
                }
                Object obj = objArr[0];
                if (obj instanceof String) {
                    return WXModuleUtils.findViewByRef((String) obj, str);
                }
                return null;
            }
        }).withViewUpdater(new PlatformManager.IViewUpdater() { // from class: com.alibaba.android.bindingx.plugin.weex.WXBindingXModule.6
            @Override // com.alibaba.android.bindingx.core.PlatformManager.IViewUpdater
            public void synchronouslyUpdateViewOnUIThread(View view, String str, Object obj, PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, Map<String, Object> map, Object... objArr) {
                if (objArr == null || objArr.length < 2) {
                    return;
                }
                Object obj2 = objArr[0];
                if (obj2 instanceof String) {
                    Object obj3 = objArr[1];
                    if (obj3 instanceof String) {
                        String str2 = (String) obj2;
                        String str3 = (String) obj3;
                        WXComponent wXComponentFindComponentByRef = WXModuleUtils.findComponentByRef(str3, str2);
                        if (wXComponentFindComponentByRef == null) {
                            LogProxy.e("unexpected error. component not found [ref:" + str2 + ",instanceId:" + str3 + Operators.ARRAY_END_STR);
                            return;
                        }
                        WXViewUpdateService.findUpdater(str).update(wXComponentFindComponentByRef, view, obj, iDeviceResolutionTranslator, map);
                    }
                }
            }
        }).withDeviceResolutionTranslator(new PlatformManager.IDeviceResolutionTranslator() { // from class: com.alibaba.android.bindingx.plugin.weex.WXBindingXModule.5
            @Override // com.alibaba.android.bindingx.core.PlatformManager.IDeviceResolutionTranslator
            public double webToNative(double d, Object... objArr) {
                return WXViewUtils.getRealPxByWidth((float) d, instanceViewPortWidth);
            }

            @Override // com.alibaba.android.bindingx.core.PlatformManager.IDeviceResolutionTranslator
            public double nativeToWeb(double d, Object... objArr) {
                return WXViewUtils.getWebPxByWidth((float) d, instanceViewPortWidth);
            }
        }).build();
    }

    @Override // com.taobao.weex.common.WXModule
    public void onActivityPause() {
        WXBridgeManager.getInstance().post(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXBindingXModule.8
            @Override // java.lang.Runnable
            public void run() {
                if (WXBindingXModule.this.mBindingXCore != null) {
                    WXBindingXModule.this.mBindingXCore.onActivityPause();
                }
            }
        }, null);
    }

    @Override // com.taobao.weex.common.WXModule
    public void onActivityResume() {
        WXBridgeManager.getInstance().post(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXBindingXModule.9
            @Override // java.lang.Runnable
            public void run() {
                if (WXBindingXModule.this.mBindingXCore != null) {
                    WXBindingXModule.this.mBindingXCore.onActivityResume();
                }
            }
        }, null);
    }
}
