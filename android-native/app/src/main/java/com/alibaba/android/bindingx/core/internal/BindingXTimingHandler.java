package com.alibaba.android.bindingx.core.internal;

import android.content.Context;
import android.view.animation.AnimationUtils;
import com.alibaba.android.bindingx.core.BindingXCore;
import com.alibaba.android.bindingx.core.BindingXEventType;
import com.alibaba.android.bindingx.core.LogProxy;
import com.alibaba.android.bindingx.core.PlatformManager;
import com.alibaba.android.bindingx.core.internal.AnimationFrame;
import com.taobao.weex.el.parse.Operators;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes.dex */
public class BindingXTimingHandler extends AbstractEventHandler implements AnimationFrame.Callback {
    private boolean isFinish;
    private AnimationFrame mAnimationFrame;
    private long mStartTime;

    @Override // com.alibaba.android.bindingx.core.IEventHandler
    public void onActivityPause() {
    }

    @Override // com.alibaba.android.bindingx.core.IEventHandler
    public void onActivityResume() {
    }

    @Override // com.alibaba.android.bindingx.core.IEventHandler
    public boolean onCreate(String str, String str2) {
        return true;
    }

    @Override // com.alibaba.android.bindingx.core.IEventHandler
    public void onStart(String str, String str2) {
    }

    public BindingXTimingHandler(Context context, PlatformManager platformManager, Object... objArr) {
        super(context, platformManager, objArr);
        this.mStartTime = 0L;
        this.isFinish = false;
        AnimationFrame animationFrame = this.mAnimationFrame;
        if (animationFrame == null) {
            this.mAnimationFrame = AnimationFrame.newInstance();
        } else {
            animationFrame.clear();
        }
    }

    BindingXTimingHandler(Context context, PlatformManager platformManager, AnimationFrame animationFrame, Object... objArr) {
        super(context, platformManager, objArr);
        this.mStartTime = 0L;
        this.isFinish = false;
        this.mAnimationFrame = animationFrame;
    }

    @Override // com.alibaba.android.bindingx.core.internal.AbstractEventHandler, com.alibaba.android.bindingx.core.IEventHandler
    public void onBindExpression(String str, Map<String, Object> map, ExpressionPair expressionPair, List<Map<String, Object>> list, BindingXCore.JavaScriptCallback javaScriptCallback) {
        super.onBindExpression(str, map, expressionPair, list, javaScriptCallback);
        if (this.mAnimationFrame == null) {
            this.mAnimationFrame = AnimationFrame.newInstance();
        }
        fireEventByState("start", 0L, new Object[0]);
        this.mAnimationFrame.clear();
        this.mAnimationFrame.requestAnimationFrame(this);
    }

    private void handleTimingCallback() {
        long jCurrentAnimationTimeMillis = 0;
        if (this.mStartTime == 0) {
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.isFinish = false;
        } else {
            jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis() - this.mStartTime;
        }
        try {
            if (LogProxy.sEnableLog) {
                LogProxy.d(String.format(Locale.getDefault(), "[TimingHandler] timing elapsed. (t:%d)", Long.valueOf(jCurrentAnimationTimeMillis)));
            }
            JSMath.applyTimingValuesToScope(this.mScope, jCurrentAnimationTimeMillis);
            if (!this.isFinish) {
                consumeExpression(this.mExpressionHoldersMap, this.mScope, BindingXEventType.TYPE_TIMING);
            }
            this.isFinish = evaluateExitExpression(this.mExitExpressionPair, this.mScope);
        } catch (Exception e) {
            LogProxy.e("runtime error", e);
        }
    }

    @Override // com.alibaba.android.bindingx.core.IEventHandler
    public boolean onDisable(String str, String str2) {
        fireEventByState("end", System.currentTimeMillis() - this.mStartTime, new Object[0]);
        clearExpressions();
        AnimationFrame animationFrame = this.mAnimationFrame;
        if (animationFrame != null) {
            animationFrame.clear();
        }
        this.mStartTime = 0L;
        return true;
    }

    @Override // com.alibaba.android.bindingx.core.internal.AbstractEventHandler, com.alibaba.android.bindingx.core.IEventHandler
    public void onDestroy() {
        super.onDestroy();
        clearExpressions();
        AnimationFrame animationFrame = this.mAnimationFrame;
        if (animationFrame != null) {
            animationFrame.terminate();
            this.mAnimationFrame = null;
        }
        this.mStartTime = 0L;
    }

    @Override // com.alibaba.android.bindingx.core.internal.AbstractEventHandler
    protected void onExit(Map<String, Object> map) {
        fireEventByState(BindingXConstants.STATE_EXIT, (long) ((Double) map.get("t")).doubleValue(), new Object[0]);
        AnimationFrame animationFrame = this.mAnimationFrame;
        if (animationFrame != null) {
            animationFrame.clear();
        }
        this.mStartTime = 0L;
    }

    @Override // com.alibaba.android.bindingx.core.internal.AbstractEventHandler
    protected void onUserIntercept(String str, Map<String, Object> map) {
        fireEventByState(BindingXConstants.STATE_INTERCEPTOR, (long) ((Double) map.get("t")).doubleValue(), Collections.singletonMap(BindingXConstants.STATE_INTERCEPTOR, str));
    }

    private void fireEventByState(String str, long j, Object... objArr) {
        if (this.mCallback != null) {
            HashMap map = new HashMap();
            map.put("state", str);
            map.put("t", Long.valueOf(j));
            map.put(BindingXConstants.KEY_TOKEN, this.mToken);
            if (objArr != null && objArr.length > 0) {
                Object obj = objArr[0];
                if (obj instanceof Map) {
                    map.putAll((Map) obj);
                }
            }
            this.mCallback.callback(map);
            LogProxy.d(">>>>>>>>>>>fire event:(" + str + "," + j + Operators.BRACKET_END_STR);
        }
    }

    @Override // com.alibaba.android.bindingx.core.internal.AnimationFrame.Callback
    public void doFrame() {
        handleTimingCallback();
    }
}
