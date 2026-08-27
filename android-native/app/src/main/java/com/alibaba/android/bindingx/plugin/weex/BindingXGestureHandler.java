package com.alibaba.android.bindingx.plugin.weex;

import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.ViewGroup;
import com.alibaba.android.bindingx.core.LogProxy;
import com.alibaba.android.bindingx.core.PlatformManager;
import com.alibaba.android.bindingx.core.internal.BindingXTouchHandler;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.view.gesture.WXGesture;
import com.taobao.weex.ui.view.gesture.WXGestureObservable;
import com.taobao.weex.utils.WXUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/* loaded from: classes.dex */
public class BindingXGestureHandler extends BindingXTouchHandler {
    private boolean experimental;
    private WXGesture mWeexGestureHandler;

    public BindingXGestureHandler(Context context, PlatformManager platformManager, Object... objArr) {
        super(context, platformManager, objArr);
        this.experimental = false;
        this.mWeexGestureHandler = null;
    }

    @Override // com.alibaba.android.bindingx.core.internal.AbstractEventHandler, com.alibaba.android.bindingx.core.IEventHandler
    public void setGlobalConfig(Map<String, Object> map) {
        super.setGlobalConfig(map);
        if (map != null) {
            this.experimental = WXUtils.getBoolean(map.get("experimentalGestureFeatures"), false).booleanValue();
        }
    }

    @Override // com.alibaba.android.bindingx.core.internal.BindingXTouchHandler, com.alibaba.android.bindingx.core.IEventHandler
    public boolean onCreate(String str, String str2) {
        if (!this.experimental) {
            return super.onCreate(str, str2);
        }
        WXComponent wXComponentFindComponentByRef = WXModuleUtils.findComponentByRef(TextUtils.isEmpty(this.mAnchorInstanceId) ? this.mInstanceId : this.mAnchorInstanceId, str);
        if (wXComponentFindComponentByRef == null) {
            return super.onCreate(str, str2);
        }
        KeyEvent.Callback hostView = wXComponentFindComponentByRef.getHostView();
        if (!(hostView instanceof ViewGroup) || !(hostView instanceof WXGestureObservable)) {
            return super.onCreate(str, str2);
        }
        try {
            WXGesture gestureListener = ((WXGestureObservable) hostView).getGestureListener();
            this.mWeexGestureHandler = gestureListener;
            if (gestureListener != null) {
                gestureListener.addOnTouchListener(this);
                LogProxy.d("[ExpressionGestureHandler] onCreate success. {source:" + str + ",type:" + str2 + Operators.BLOCK_END_STR);
                return true;
            }
            return super.onCreate(str, str2);
        } catch (Throwable th) {
            LogProxy.e("experimental gesture features open failed." + th.getMessage());
            return super.onCreate(str, str2);
        }
    }

    @Override // com.alibaba.android.bindingx.core.internal.BindingXTouchHandler, com.alibaba.android.bindingx.core.IEventHandler
    public boolean onDisable(String str, String str2) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        WXGesture wXGesture;
        boolean zOnDisable = super.onDisable(str, str2);
        if (this.experimental && (wXGesture = this.mWeexGestureHandler) != null) {
            try {
                return zOnDisable | wXGesture.removeTouchListener(this);
            } catch (Throwable th) {
                LogProxy.e("[ExpressionGestureHandler]  disabled failed." + th.getMessage());
            }
        }
        return zOnDisable;
    }
}
