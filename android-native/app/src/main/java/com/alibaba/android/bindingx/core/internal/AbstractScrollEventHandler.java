package com.alibaba.android.bindingx.core.internal;

import android.content.Context;
import com.alibaba.android.bindingx.core.LogProxy;
import com.alibaba.android.bindingx.core.PlatformManager;
import com.taobao.weex.common.Constants;
import com.taobao.weex.el.parse.Operators;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class AbstractScrollEventHandler extends AbstractEventHandler {
    private boolean isStart;
    protected int mContentOffsetX;
    protected int mContentOffsetY;

    public AbstractScrollEventHandler(Context context, PlatformManager platformManager, Object... objArr) {
        super(context, platformManager, objArr);
        this.isStart = false;
    }

    @Override // com.alibaba.android.bindingx.core.IEventHandler
    public boolean onDisable(String str, String str2) {
        clearExpressions();
        this.isStart = false;
        fireEventByState("end", this.mContentOffsetX, this.mContentOffsetY, 0.0d, 0.0d, 0.0d, 0.0d, new Object[0]);
        return true;
    }

    @Override // com.alibaba.android.bindingx.core.internal.AbstractEventHandler
    protected void onExit(Map<String, Object> map) {
        fireEventByState(BindingXConstants.STATE_EXIT, ((Double) map.get("internal_x")).doubleValue(), ((Double) map.get("internal_y")).doubleValue(), 0.0d, 0.0d, 0.0d, 0.0d, new Object[0]);
    }

    @Override // com.alibaba.android.bindingx.core.internal.AbstractEventHandler
    protected void onUserIntercept(String str, Map<String, Object> map) {
        fireEventByState(BindingXConstants.STATE_INTERCEPTOR, ((Double) map.get("internal_x")).doubleValue(), ((Double) map.get("internal_y")).doubleValue(), ((Double) map.get("dx")).doubleValue(), ((Double) map.get(Constants.Name.DISTANCE_Y)).doubleValue(), ((Double) map.get("tdx")).doubleValue(), ((Double) map.get("tdy")).doubleValue(), Collections.singletonMap(BindingXConstants.STATE_INTERCEPTOR, str));
    }

    @Override // com.alibaba.android.bindingx.core.internal.AbstractEventHandler, com.alibaba.android.bindingx.core.IEventHandler
    public void onDestroy() {
        super.onDestroy();
        this.isStart = false;
    }

    protected void handleScrollEvent(int i, int i2, int i3, int i4, int i5, int i6) {
        if (LogProxy.sEnableLog) {
            LogProxy.d(String.format(Locale.getDefault(), "[ScrollHandler] scroll changed. (contentOffsetX:%d,contentOffsetY:%d,dx:%d,dy:%d,tdx:%d,tdy:%d)", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6)));
        }
        this.mContentOffsetX = i;
        this.mContentOffsetY = i2;
        if (!this.isStart) {
            this.isStart = true;
            fireEventByState("start", i, i2, i3, i4, i5, i6, new Object[0]);
        }
        try {
            JSMath.applyScrollValuesToScope(this.mScope, i, i2, i3, i4, i5, i6, this.mPlatformManager.getResolutionTranslator());
            if (evaluateExitExpression(this.mExitExpressionPair, this.mScope)) {
                return;
            }
            consumeExpression(this.mExpressionHoldersMap, this.mScope, "scroll");
        } catch (Exception e) {
            LogProxy.e("runtime error", e);
        }
    }

    protected void fireEventByState(String str, double d, double d2, double d3, double d4, double d5, double d6, Object... objArr) {
        if (this.mCallback != null) {
            HashMap map = new HashMap();
            map.put("state", str);
            double dNativeToWeb = this.mPlatformManager.getResolutionTranslator().nativeToWeb(d, new Object[0]);
            double dNativeToWeb2 = this.mPlatformManager.getResolutionTranslator().nativeToWeb(d2, new Object[0]);
            map.put(Constants.Name.X, Double.valueOf(dNativeToWeb));
            map.put(Constants.Name.Y, Double.valueOf(dNativeToWeb2));
            double dNativeToWeb3 = this.mPlatformManager.getResolutionTranslator().nativeToWeb(d3, new Object[0]);
            double dNativeToWeb4 = this.mPlatformManager.getResolutionTranslator().nativeToWeb(d4, new Object[0]);
            map.put("dx", Double.valueOf(dNativeToWeb3));
            map.put(Constants.Name.DISTANCE_Y, Double.valueOf(dNativeToWeb4));
            double dNativeToWeb5 = this.mPlatformManager.getResolutionTranslator().nativeToWeb(d5, new Object[0]);
            double dNativeToWeb6 = this.mPlatformManager.getResolutionTranslator().nativeToWeb(d6, new Object[0]);
            map.put("tdx", Double.valueOf(dNativeToWeb5));
            map.put("tdy", Double.valueOf(dNativeToWeb6));
            map.put(BindingXConstants.KEY_TOKEN, this.mToken);
            if (objArr != null && objArr.length > 0) {
                Object obj = objArr[0];
                if (obj instanceof Map) {
                    map.putAll((Map) obj);
                }
            }
            this.mCallback.callback(map);
            LogProxy.d(">>>>>>>>>>>fire event:(" + str + "," + dNativeToWeb + "," + dNativeToWeb2 + "," + dNativeToWeb3 + "," + dNativeToWeb4 + "," + dNativeToWeb5 + "," + dNativeToWeb6 + Operators.BRACKET_END_STR);
        }
    }
}
