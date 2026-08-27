package com.taobao.weex.ui.action;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.core.view.animation.PathInterpolatorCompat;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.animation.BackgroundColorProperty;
import com.taobao.weex.ui.animation.HeightProperty;
import com.taobao.weex.ui.animation.WXAnimationBean;
import com.taobao.weex.ui.animation.WXAnimationModule;
import com.taobao.weex.ui.animation.WidthProperty;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.list.template.TemplateDom;
import com.taobao.weex.ui.view.border.BorderDrawable;
import com.taobao.weex.utils.SingleFunctionParser;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXResourceUtils;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import java.util.HashMap;
import java.util.List;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class GraphicActionAnimation extends BasicGraphicAction {
    private static final String TAG = "GraphicActionAnimation";
    private final String callback;
    private WXAnimationBean mAnimationBean;
    private final boolean styleNeedInit;

    public GraphicActionAnimation(WXSDKInstance wXSDKInstance, String str, WXAnimationBean wXAnimationBean) {
        super(wXSDKInstance, str);
        this.styleNeedInit = false;
        this.callback = null;
        this.mAnimationBean = wXAnimationBean;
    }

    private ObjectAnimator createAnimator(View view, float f) {
        WXAnimationBean.Style style;
        if (view == null || (style = this.mAnimationBean.styles) == null) {
            return null;
        }
        List<PropertyValuesHolder> holders = style.getHolders();
        if (!TextUtils.isEmpty(style.backgroundColor)) {
            BorderDrawable borderDrawable = WXViewUtils.getBorderDrawable(view);
            if (borderDrawable != null) {
                holders.add(PropertyValuesHolder.ofObject(new BackgroundColorProperty(), new ArgbEvaluator(), Integer.valueOf(borderDrawable.getColor()), Integer.valueOf(WXResourceUtils.getColor(style.backgroundColor))));
            } else if (view.getBackground() instanceof ColorDrawable) {
                holders.add(PropertyValuesHolder.ofObject(new BackgroundColorProperty(), new ArgbEvaluator(), Integer.valueOf(((ColorDrawable) view.getBackground()).getColor()), Integer.valueOf(WXResourceUtils.getColor(style.backgroundColor))));
            }
        }
        if (view.getLayoutParams() != null && (!TextUtils.isEmpty(style.width) || !TextUtils.isEmpty(style.height))) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (!TextUtils.isEmpty(style.width)) {
                holders.add(PropertyValuesHolder.ofInt(new WidthProperty(), layoutParams.width, (int) WXViewUtils.getRealPxByWidth(WXUtils.getFloat(style.width), f)));
            }
            if (!TextUtils.isEmpty(style.height)) {
                holders.add(PropertyValuesHolder.ofInt(new HeightProperty(), layoutParams.height, (int) WXViewUtils.getRealPxByWidth(WXUtils.getFloat(style.height), f)));
            }
        }
        if (style.getPivot() != null) {
            Pair<Float, Float> pivot = style.getPivot();
            view.setPivotX(((Float) pivot.first).floatValue());
            view.setPivotY(((Float) pivot.second).floatValue());
        }
        ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, (PropertyValuesHolder[]) holders.toArray(new PropertyValuesHolder[holders.size()]));
        objectAnimatorOfPropertyValuesHolder.setStartDelay(this.mAnimationBean.delay);
        return objectAnimatorOfPropertyValuesHolder;
    }

    private Animator.AnimatorListener createAnimatorListener(final WXSDKInstance wXSDKInstance, final String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return new AnimatorListenerAdapter() { // from class: com.taobao.weex.ui.action.GraphicActionAnimation.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                WXSDKInstance wXSDKInstance2 = wXSDKInstance;
                if (wXSDKInstance2 == null || wXSDKInstance2.isDestroy()) {
                    WXLogUtils.e("RenderContextImpl-onAnimationEnd WXSDKInstance == null NPE or instance is destroyed");
                } else {
                    WXSDKManager.getInstance().callback(wXSDKInstance.getInstanceId(), str, new HashMap());
                }
            }
        };
    }

    private Interpolator createTimeInterpolator() {
        String str = this.mAnimationBean.timingFunction;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        str.getClass();
        str.hashCode();
        switch (str) {
            case "ease-in":
                return new AccelerateInterpolator();
            case "linear":
                return new LinearInterpolator();
            case "ease-out":
                return new DecelerateInterpolator();
            case "ease-in-out":
                return new AccelerateDecelerateInterpolator();
            default:
                try {
                    List list = new SingleFunctionParser(this.mAnimationBean.timingFunction, new SingleFunctionParser.FlatMapper<Float>() { // from class: com.taobao.weex.ui.action.GraphicActionAnimation.2
                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // com.taobao.weex.utils.SingleFunctionParser.FlatMapper
                        public Float map(String str2) {
                            return Float.valueOf(Float.parseFloat(str2));
                        }
                    }).parse("cubic-bezier");
                    if (list != null && list.size() == 4) {
                        return PathInterpolatorCompat.create(((Float) list.get(0)).floatValue(), ((Float) list.get(1)).floatValue(), ((Float) list.get(2)).floatValue(), ((Float) list.get(3)).floatValue());
                    }
                } catch (RuntimeException unused) {
                }
                return null;
        }
    }

    private void startAnimation(WXSDKInstance wXSDKInstance, WXComponent wXComponent) {
        if (wXComponent != null) {
            WXAnimationBean wXAnimationBean = this.mAnimationBean;
            if (wXAnimationBean != null) {
                wXComponent.setNeedLayoutOnAnimation(wXAnimationBean.needLayout);
            }
            if (wXComponent.getHostView() == null) {
                wXComponent.postAnimation(new WXAnimationModule.AnimationHolder(this.mAnimationBean, this.callback));
                return;
            }
            try {
                ObjectAnimator objectAnimatorCreateAnimator = createAnimator(wXComponent.getHostView(), wXSDKInstance.getInstanceViewPortWidthWithFloat());
                if (objectAnimatorCreateAnimator != null) {
                    Animator.AnimatorListener animatorListenerCreateAnimatorListener = createAnimatorListener(wXSDKInstance, this.callback);
                    Interpolator interpolatorCreateTimeInterpolator = createTimeInterpolator();
                    if (animatorListenerCreateAnimatorListener != null) {
                        objectAnimatorCreateAnimator.addListener(animatorListenerCreateAnimatorListener);
                    }
                    if (interpolatorCreateTimeInterpolator != null) {
                        objectAnimatorCreateAnimator.setInterpolator(interpolatorCreateTimeInterpolator);
                    }
                    wXComponent.getHostView().setCameraDistance(this.mAnimationBean.styles.getCameraDistance());
                    objectAnimatorCreateAnimator.setDuration(this.mAnimationBean.duration);
                    objectAnimatorCreateAnimator.start();
                }
            } catch (RuntimeException e) {
                WXLogUtils.e(TAG, WXLogUtils.getStackTrace(e));
            }
        }
    }

    @Override // com.taobao.weex.ui.action.IExecutable
    public void executeAction() {
        WXSDKInstance wXSDKInstance;
        if (this.mAnimationBean == null) {
            return;
        }
        WXComponent wXComponent = WXSDKManager.getInstance().getWXRenderManager().getWXComponent(getPageId(), getRef());
        if ((wXComponent == null && (!TemplateDom.isVirtualDomRef(getRef()) || (wXComponent = TemplateDom.findVirtualComponentByVRef(getPageId(), getRef())) == null)) || (wXSDKInstance = WXSDKManager.getInstance().getWXRenderManager().getWXSDKInstance(getPageId())) == null || this.mAnimationBean.styles == null) {
            return;
        }
        if (this.styleNeedInit) {
            String str = (String) wXComponent.getStyles().get(Constants.Name.TRANSFORM_ORIGIN);
            if (TextUtils.isEmpty(this.mAnimationBean.styles.transformOrigin)) {
                this.mAnimationBean.styles.transformOrigin = str;
            }
            WXAnimationBean.Style style = this.mAnimationBean.styles;
            style.init(style.transformOrigin, style.transform, (int) wXComponent.getLayoutWidth(), (int) wXComponent.getLayoutHeight(), wXSDKInstance.getInstanceViewPortWidthWithFloat(), wXSDKInstance);
        }
        startAnimation(wXSDKInstance, wXComponent);
    }

    public GraphicActionAnimation(WXSDKInstance wXSDKInstance, String str, String str2, String str3) {
        super(wXSDKInstance, str);
        this.styleNeedInit = true;
        this.callback = str3;
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        this.mAnimationBean = (WXAnimationBean) JSONObject.parseObject(str2, WXAnimationBean.class);
    }

    public GraphicActionAnimation(WXSDKInstance wXSDKInstance, String str, WXAnimationBean wXAnimationBean, String str2) {
        super(wXSDKInstance, str);
        this.styleNeedInit = false;
        this.mAnimationBean = wXAnimationBean;
        this.callback = str2;
    }
}
