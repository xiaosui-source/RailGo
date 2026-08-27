package com.taobao.weex.dom.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Property;
import android.view.View;
import android.view.animation.Interpolator;
import androidx.collection.ArrayMap;
import androidx.core.view.animation.PathInterpolatorCompat;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.Constants;
import com.taobao.weex.dom.CSSShorthand;
import com.taobao.weex.ui.animation.BackgroundColorProperty;
import com.taobao.weex.ui.animation.TransformParser;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.utils.SingleFunctionParser;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXResourceUtils;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXTransition {
    private static final Set<String> LAYOUT_PROPERTIES;
    public static final Pattern PROPERTY_SPLIT_PATTERN = Pattern.compile("\\||,");
    private static final Set<String> TRANSFORM_PROPERTIES;
    public static final String TRANSITION_DELAY = "transitionDelay";
    public static final String TRANSITION_DURATION = "transitionDuration";
    public static final String TRANSITION_PROPERTY = "transitionProperty";
    public static final String TRANSITION_TIMING_FUNCTION = "transitionTimingFunction";
    private Runnable animationRunnable;
    private long delay;
    private long duration;
    private Interpolator interpolator;
    private ValueAnimator layoutValueAnimator;
    private WXComponent mWXComponent;
    private Runnable transformAnimationRunnable;
    private ObjectAnimator transformAnimator;
    private Runnable transitionEndEvent;
    private volatile AtomicInteger lockToken = new AtomicInteger(0);
    private List<String> properties = new ArrayList(4);
    private Handler handler = new Handler();
    private Map<String, Object> layoutPendingUpdates = new ArrayMap();
    private Map<String, Object> transformPendingUpdates = new ArrayMap();
    private Map<String, Object> targetStyles = new ArrayMap();

    static {
        HashSet hashSet = new HashSet();
        LAYOUT_PROPERTIES = hashSet;
        hashSet.add("width");
        hashSet.add("height");
        hashSet.add(Constants.Name.MARGIN_TOP);
        hashSet.add(Constants.Name.MARGIN_BOTTOM);
        hashSet.add(Constants.Name.MARGIN_LEFT);
        hashSet.add(Constants.Name.MARGIN_RIGHT);
        hashSet.add("left");
        hashSet.add("right");
        hashSet.add("top");
        hashSet.add("bottom");
        hashSet.add(Constants.Name.PADDING_LEFT);
        hashSet.add(Constants.Name.PADDING_RIGHT);
        hashSet.add(Constants.Name.PADDING_TOP);
        hashSet.add(Constants.Name.PADDING_BOTTOM);
        HashSet hashSet2 = new HashSet();
        TRANSFORM_PROPERTIES = hashSet2;
        hashSet2.add("opacity");
        hashSet2.add("backgroundColor");
        hashSet2.add("transform");
    }

    public static void asynchronouslyUpdateLayout(WXComponent wXComponent, final String str, final float f) {
        if (wXComponent == null || wXComponent.getInstance() == null) {
            return;
        }
        final String ref = wXComponent.getRef();
        final String instanceId = wXComponent.getInstanceId();
        if (TextUtils.isEmpty(ref) || TextUtils.isEmpty(instanceId)) {
            return;
        }
        WXSDKManager.getInstance().getWXBridgeManager().post(new Runnable() { // from class: com.taobao.weex.dom.transition.WXTransition.7
            @Override // java.lang.Runnable
            public void run() {
                String str2 = str;
                str2.getClass();
                str2.hashCode();
                switch (str2) {
                    case "paddingLeft":
                        WXBridgeManager.getInstance().setPadding(instanceId, ref, CSSShorthand.EDGE.LEFT, f);
                        break;
                    case "bottom":
                        WXBridgeManager.getInstance().setPosition(instanceId, ref, CSSShorthand.EDGE.BOTTOM, f);
                        break;
                    case "height":
                        WXBridgeManager.getInstance().setStyleHeight(instanceId, ref, f);
                        break;
                    case "marginTop":
                        WXBridgeManager.getInstance().setMargin(instanceId, ref, CSSShorthand.EDGE.TOP, f);
                        break;
                    case "marginBottom":
                        WXBridgeManager.getInstance().setMargin(instanceId, ref, CSSShorthand.EDGE.BOTTOM, f);
                        break;
                    case "top":
                        WXBridgeManager.getInstance().setPosition(instanceId, ref, CSSShorthand.EDGE.TOP, f);
                        break;
                    case "left":
                        WXBridgeManager.getInstance().setPosition(instanceId, ref, CSSShorthand.EDGE.LEFT, f);
                        break;
                    case "paddingTop":
                        WXBridgeManager.getInstance().setPadding(instanceId, ref, CSSShorthand.EDGE.TOP, f);
                        break;
                    case "right":
                        WXBridgeManager.getInstance().setPosition(instanceId, ref, CSSShorthand.EDGE.RIGHT, f);
                        break;
                    case "width":
                        WXBridgeManager.getInstance().setStyleWidth(instanceId, ref, f);
                        break;
                    case "paddingBottom":
                        WXBridgeManager.getInstance().setPadding(instanceId, ref, CSSShorthand.EDGE.BOTTOM, f);
                        break;
                    case "paddingRight":
                        WXBridgeManager.getInstance().setPadding(instanceId, ref, CSSShorthand.EDGE.RIGHT, f);
                        break;
                    case "marginRight":
                        WXBridgeManager.getInstance().setMargin(instanceId, ref, CSSShorthand.EDGE.RIGHT, f);
                        break;
                    case "marginLeft":
                        WXBridgeManager.getInstance().setMargin(instanceId, ref, CSSShorthand.EDGE.LEFT, f);
                        break;
                }
            }
        });
    }

    private PropertyValuesHolder createLayoutPropertyValueHolder(String str, Object obj) {
        PropertyValuesHolder propertyValuesHolderOfFloat;
        WXTransition wXTransition;
        PropertyValuesHolder propertyValuesHolderOfFloat2;
        str.getClass();
        str.hashCode();
        switch (str) {
            case "paddingLeft":
                propertyValuesHolderOfFloat = PropertyValuesHolder.ofFloat(Constants.Name.PADDING_LEFT, this.mWXComponent.getPadding().get(CSSShorthand.EDGE.LEFT), WXViewUtils.getRealPxByWidth(WXUtils.getFloatByViewport(obj, this.mWXComponent.getViewPortWidthForFloat()), this.mWXComponent.getViewPortWidthForFloat()));
                break;
            case "bottom":
                wXTransition = this;
                propertyValuesHolderOfFloat2 = PropertyValuesHolder.ofFloat("bottom", wXTransition.mWXComponent.getLayoutPosition().getBottom(), WXViewUtils.getRealPxByWidth(WXUtils.getFloatByViewport(obj, wXTransition.mWXComponent.getViewPortWidthForFloat()), wXTransition.mWXComponent.getViewPortWidthForFloat()));
                propertyValuesHolderOfFloat = propertyValuesHolderOfFloat2;
                break;
            case "height":
                wXTransition = this;
                propertyValuesHolderOfFloat2 = PropertyValuesHolder.ofFloat("height", wXTransition.mWXComponent.getLayoutHeight(), WXViewUtils.getRealPxByWidth(WXUtils.getFloat(obj, Float.valueOf(0.0f)).floatValue(), wXTransition.mWXComponent.getViewPortWidthForFloat()));
                propertyValuesHolderOfFloat = propertyValuesHolderOfFloat2;
                break;
            case "marginTop":
                wXTransition = this;
                propertyValuesHolderOfFloat2 = PropertyValuesHolder.ofFloat(Constants.Name.MARGIN_TOP, wXTransition.mWXComponent.getMargin().get(CSSShorthand.EDGE.TOP), WXViewUtils.getRealPxByWidth(WXUtils.getFloatByViewport(obj, wXTransition.mWXComponent.getViewPortWidthForFloat()), wXTransition.mWXComponent.getViewPortWidthForFloat()));
                propertyValuesHolderOfFloat = propertyValuesHolderOfFloat2;
                break;
            case "marginBottom":
                wXTransition = this;
                propertyValuesHolderOfFloat2 = PropertyValuesHolder.ofFloat(Constants.Name.MARGIN_BOTTOM, wXTransition.mWXComponent.getMargin().get(CSSShorthand.EDGE.BOTTOM), WXViewUtils.getRealPxByWidth(WXUtils.getFloatByViewport(obj, wXTransition.mWXComponent.getViewPortWidthForFloat()), wXTransition.mWXComponent.getViewPortWidthForFloat()));
                propertyValuesHolderOfFloat = propertyValuesHolderOfFloat2;
                break;
            case "top":
                wXTransition = this;
                propertyValuesHolderOfFloat2 = PropertyValuesHolder.ofFloat("top", wXTransition.mWXComponent.getLayoutPosition().getTop(), WXViewUtils.getRealPxByWidth(WXUtils.getFloatByViewport(obj, wXTransition.mWXComponent.getViewPortWidthForFloat()), wXTransition.mWXComponent.getViewPortWidthForFloat()));
                propertyValuesHolderOfFloat = propertyValuesHolderOfFloat2;
                break;
            case "left":
                wXTransition = this;
                propertyValuesHolderOfFloat2 = PropertyValuesHolder.ofFloat("left", wXTransition.mWXComponent.getLayoutPosition().getLeft(), WXViewUtils.getRealPxByWidth(WXUtils.getFloatByViewport(obj, wXTransition.mWXComponent.getViewPortWidthForFloat()), wXTransition.mWXComponent.getViewPortWidthForFloat()));
                propertyValuesHolderOfFloat = propertyValuesHolderOfFloat2;
                break;
            case "paddingTop":
                wXTransition = this;
                propertyValuesHolderOfFloat2 = PropertyValuesHolder.ofFloat(Constants.Name.PADDING_TOP, wXTransition.mWXComponent.getPadding().get(CSSShorthand.EDGE.TOP), WXViewUtils.getRealPxByWidth(WXUtils.getFloatByViewport(obj, wXTransition.mWXComponent.getViewPortWidthForFloat()), wXTransition.mWXComponent.getViewPortWidthForFloat()));
                propertyValuesHolderOfFloat = propertyValuesHolderOfFloat2;
                break;
            case "right":
                wXTransition = this;
                propertyValuesHolderOfFloat2 = PropertyValuesHolder.ofFloat("right", wXTransition.mWXComponent.getLayoutPosition().getRight(), WXViewUtils.getRealPxByWidth(WXUtils.getFloatByViewport(obj, wXTransition.mWXComponent.getViewPortWidthForFloat()), wXTransition.mWXComponent.getViewPortWidthForFloat()));
                propertyValuesHolderOfFloat = propertyValuesHolderOfFloat2;
                break;
            case "width":
                wXTransition = this;
                propertyValuesHolderOfFloat2 = PropertyValuesHolder.ofFloat("width", wXTransition.mWXComponent.getLayoutWidth(), WXViewUtils.getRealPxByWidth(WXUtils.getFloat(obj, Float.valueOf(0.0f)).floatValue(), wXTransition.mWXComponent.getViewPortWidthForFloat()));
                propertyValuesHolderOfFloat = propertyValuesHolderOfFloat2;
                break;
            case "paddingBottom":
                wXTransition = this;
                propertyValuesHolderOfFloat2 = PropertyValuesHolder.ofFloat(Constants.Name.PADDING_BOTTOM, wXTransition.mWXComponent.getPadding().get(CSSShorthand.EDGE.BOTTOM), WXViewUtils.getRealPxByWidth(WXUtils.getFloatByViewport(obj, wXTransition.mWXComponent.getViewPortWidthForFloat()), wXTransition.mWXComponent.getViewPortWidthForFloat()));
                propertyValuesHolderOfFloat = propertyValuesHolderOfFloat2;
                break;
            case "paddingRight":
                wXTransition = this;
                propertyValuesHolderOfFloat2 = PropertyValuesHolder.ofFloat(Constants.Name.PADDING_RIGHT, wXTransition.mWXComponent.getPadding().get(CSSShorthand.EDGE.RIGHT), WXViewUtils.getRealPxByWidth(WXUtils.getFloatByViewport(obj, wXTransition.mWXComponent.getViewPortWidthForFloat()), wXTransition.mWXComponent.getViewPortWidthForFloat()));
                propertyValuesHolderOfFloat = propertyValuesHolderOfFloat2;
                break;
            case "marginRight":
                wXTransition = this;
                propertyValuesHolderOfFloat2 = PropertyValuesHolder.ofFloat(Constants.Name.MARGIN_RIGHT, wXTransition.mWXComponent.getMargin().get(CSSShorthand.EDGE.RIGHT), WXViewUtils.getRealPxByWidth(WXUtils.getFloatByViewport(obj, wXTransition.mWXComponent.getViewPortWidthForFloat()), wXTransition.mWXComponent.getViewPortWidthForFloat()));
                propertyValuesHolderOfFloat = propertyValuesHolderOfFloat2;
                break;
            case "marginLeft":
                wXTransition = this;
                propertyValuesHolderOfFloat2 = PropertyValuesHolder.ofFloat(Constants.Name.MARGIN_LEFT, wXTransition.mWXComponent.getMargin().get(CSSShorthand.EDGE.LEFT), WXViewUtils.getRealPxByWidth(WXUtils.getFloatByViewport(obj, wXTransition.mWXComponent.getViewPortWidthForFloat()), wXTransition.mWXComponent.getViewPortWidthForFloat()));
                propertyValuesHolderOfFloat = propertyValuesHolderOfFloat2;
                break;
            default:
                propertyValuesHolderOfFloat = null;
                break;
        }
        return propertyValuesHolderOfFloat == null ? PropertyValuesHolder.ofFloat(str, 1.0f, 1.0f) : propertyValuesHolderOfFloat;
    }

    private static Interpolator createTimeInterpolator(String str) {
        if (!TextUtils.isEmpty(str)) {
            str.getClass();
            str.hashCode();
            switch (str) {
                case "ease-in":
                    return PathInterpolatorCompat.create(0.42f, 0.0f, 1.0f, 1.0f);
                case "linear":
                    return PathInterpolatorCompat.create(0.0f, 0.0f, 1.0f, 1.0f);
                case "ease-out":
                    return PathInterpolatorCompat.create(0.0f, 0.0f, 0.58f, 1.0f);
                case "ease-in-out":
                    return PathInterpolatorCompat.create(0.42f, 0.0f, 0.58f, 1.0f);
                case "ease":
                    return PathInterpolatorCompat.create(0.25f, 0.1f, 0.25f, 1.0f);
                default:
                    try {
                        List list = new SingleFunctionParser(str, new SingleFunctionParser.FlatMapper<Float>() { // from class: com.taobao.weex.dom.transition.WXTransition.8
                            /* JADX WARN: Can't rename method to resolve collision */
                            @Override // com.taobao.weex.utils.SingleFunctionParser.FlatMapper
                            public Float map(String str2) {
                                return Float.valueOf(Float.parseFloat(str2));
                            }
                        }).parse("cubic-bezier");
                        if (list != null && list.size() == 4) {
                            return PathInterpolatorCompat.create(((Float) list.get(0)).floatValue(), ((Float) list.get(1)).floatValue(), ((Float) list.get(2)).floatValue(), ((Float) list.get(3)).floatValue());
                        }
                    } catch (RuntimeException e) {
                        if (WXEnvironment.isApkDebugable()) {
                            WXLogUtils.e("WXTransition", e);
                            break;
                        }
                    }
                    break;
            }
        }
        return PathInterpolatorCompat.create(0.25f, 0.1f, 0.25f, 1.0f);
    }

    private void doLayoutPropertyValuesHolderAnimation(PropertyValuesHolder[] propertyValuesHolderArr) {
        ValueAnimator valueAnimatorOfPropertyValuesHolder = ValueAnimator.ofPropertyValuesHolder(propertyValuesHolderArr);
        this.layoutValueAnimator = valueAnimatorOfPropertyValuesHolder;
        valueAnimatorOfPropertyValuesHolder.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.taobao.weex.dom.transition.WXTransition.5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                for (PropertyValuesHolder propertyValuesHolder : valueAnimator.getValues()) {
                    String propertyName = propertyValuesHolder.getPropertyName();
                    WXTransition.asynchronouslyUpdateLayout(WXTransition.this.mWXComponent, propertyName, ((Float) valueAnimator.getAnimatedValue(propertyName)).floatValue());
                }
            }
        });
        this.layoutValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.taobao.weex.dom.transition.WXTransition.6
            boolean hasCancel = false;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                this.hasCancel = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (this.hasCancel) {
                    return;
                }
                super.onAnimationEnd(animator);
                WXTransition.this.onTransitionAnimationEnd();
            }
        });
        Interpolator interpolator = this.interpolator;
        if (interpolator != null) {
            this.layoutValueAnimator.setInterpolator(interpolator);
        }
        this.layoutValueAnimator.setStartDelay(this.delay);
        this.layoutValueAnimator.setDuration(this.duration);
        this.layoutValueAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doPendingTransformAnimation(int i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        View targetView;
        ObjectAnimator objectAnimator = this.transformAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.transformAnimator = null;
        }
        if (this.transformPendingUpdates.size() == 0 || (targetView = getTargetView()) == null || this.mWXComponent.getInstance() == null) {
            return;
        }
        ArrayList arrayList = new ArrayList(8);
        String string = WXUtils.getString(this.transformPendingUpdates.remove("transform"), null);
        if (!TextUtils.isEmpty(string)) {
            for (PropertyValuesHolder propertyValuesHolder : TransformParser.toHolders(TransformParser.parseTransForm(this.mWXComponent.getInstanceId(), string, (int) this.mWXComponent.getLayoutWidth(), (int) this.mWXComponent.getLayoutHeight(), this.mWXComponent.getViewPortWidthForFloat()))) {
                arrayList.add(propertyValuesHolder);
            }
            synchronized (this.targetStyles) {
                this.targetStyles.put("transform", string);
            }
        }
        for (String str : this.properties) {
            if (TRANSFORM_PROPERTIES.contains(str) && this.transformPendingUpdates.containsKey(str)) {
                Object objRemove = this.transformPendingUpdates.remove(str);
                synchronized (this.targetStyles) {
                    this.targetStyles.put(str, objRemove);
                }
                str.getClass();
                if (str.equals("opacity")) {
                    arrayList.add(PropertyValuesHolder.ofFloat((Property<?, Float>) View.ALPHA, targetView.getAlpha(), WXUtils.getFloat(objRemove, Float.valueOf(1.0f)).floatValue()));
                    targetView.setLayerType(1, null);
                } else if (str.equals("backgroundColor")) {
                    int color = WXResourceUtils.getColor(WXUtils.getString(this.mWXComponent.getStyles().getBackgroundColor(), null), 0);
                    int color2 = WXResourceUtils.getColor(WXUtils.getString(objRemove, null), 0);
                    if (WXViewUtils.getBorderDrawable(targetView) != null) {
                        color = WXViewUtils.getBorderDrawable(targetView).getColor();
                    } else if (targetView.getBackground() instanceof ColorDrawable) {
                        color = ((ColorDrawable) targetView.getBackground()).getColor();
                    }
                    arrayList.add(PropertyValuesHolder.ofObject(new BackgroundColorProperty(), new ArgbEvaluator(), Integer.valueOf(color), Integer.valueOf(color2)));
                }
            }
        }
        if (i == this.lockToken.get()) {
            this.transformPendingUpdates.clear();
        }
        ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(targetView, (PropertyValuesHolder[]) arrayList.toArray(new PropertyValuesHolder[arrayList.size()]));
        this.transformAnimator = objectAnimatorOfPropertyValuesHolder;
        objectAnimatorOfPropertyValuesHolder.setDuration(this.duration);
        long j = this.delay;
        if (j > 0) {
            this.transformAnimator.setStartDelay(j);
        }
        Interpolator interpolator = this.interpolator;
        if (interpolator != null) {
            this.transformAnimator.setInterpolator(interpolator);
        }
        this.transformAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.taobao.weex.dom.transition.WXTransition.4
            boolean hasCancel = false;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                this.hasCancel = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (this.hasCancel) {
                    return;
                }
                super.onAnimationEnd(animator);
                WXTransition.this.onTransitionAnimationEnd();
            }
        });
        this.transformAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doTransitionAnimation(final int i) {
        View targetView = getTargetView();
        if (targetView == null) {
            return;
        }
        if (this.targetStyles.size() > 0) {
            for (String str : this.properties) {
                if (LAYOUT_PROPERTIES.contains(str) || TRANSFORM_PROPERTIES.contains(str)) {
                    if (!this.layoutPendingUpdates.containsKey(str) && !this.transformPendingUpdates.containsKey(str)) {
                        synchronized (this.targetStyles) {
                            if (this.targetStyles.containsKey(str)) {
                                this.mWXComponent.getStyles().put(str, this.targetStyles.remove(str));
                            }
                        }
                    }
                }
            }
        }
        Runnable runnable = this.transitionEndEvent;
        if (runnable != null) {
            targetView.removeCallbacks(runnable);
        }
        if (this.transitionEndEvent == null && this.duration > Float.MIN_NORMAL) {
            this.transitionEndEvent = new Runnable() { // from class: com.taobao.weex.dom.transition.WXTransition.2
                @Override // java.lang.Runnable
                public void run() {
                    WXTransition.this.transitionEndEvent = null;
                    if (WXTransition.this.duration >= Float.MIN_NORMAL && WXTransition.this.mWXComponent != null && WXTransition.this.mWXComponent.getEvents().contains(Constants.Event.ON_TRANSITION_END)) {
                        WXTransition.this.mWXComponent.fireEvent(Constants.Event.ON_TRANSITION_END);
                    }
                }
            };
        }
        Runnable runnable2 = this.transformAnimationRunnable;
        if (runnable2 != null) {
            targetView.removeCallbacks(runnable2);
        }
        Runnable runnable3 = new Runnable() { // from class: com.taobao.weex.dom.transition.WXTransition.3
            @Override // java.lang.Runnable
            public void run() {
                synchronized (WXTransition.this.lockToken) {
                    if (i == WXTransition.this.lockToken.get()) {
                        WXTransition.this.doPendingTransformAnimation(i);
                    }
                }
            }
        };
        this.transformAnimationRunnable = runnable3;
        targetView.post(runnable3);
        doPendingLayoutAnimation();
    }

    public static WXTransition fromMap(Map<String, Object> map, WXComponent wXComponent) {
        String string;
        if (map.get(TRANSITION_PROPERTY) == null || (string = WXUtils.getString(map.get(TRANSITION_PROPERTY), null)) == null) {
            return null;
        }
        WXTransition wXTransition = new WXTransition();
        updateTransitionProperties(wXTransition, string);
        if (wXTransition.properties.isEmpty()) {
            return null;
        }
        wXTransition.duration = parseTimeMillis(map, TRANSITION_DURATION, 0L);
        wXTransition.delay = parseTimeMillis(map, TRANSITION_DELAY, 0L);
        wXTransition.interpolator = createTimeInterpolator(WXUtils.getString(map.get(TRANSITION_TIMING_FUNCTION), null));
        wXTransition.mWXComponent = wXComponent;
        return wXTransition;
    }

    private View getTargetView() {
        WXComponent wXComponent = this.mWXComponent;
        if (wXComponent != null) {
            return wXComponent.getHostView();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void onTransitionAnimationEnd() {
        Runnable runnable;
        if (this.duration > 0 && this.transitionEndEvent != null) {
            View targetView = getTargetView();
            if (targetView != null && (runnable = this.transitionEndEvent) != null) {
                targetView.post(runnable);
            }
            this.transitionEndEvent = null;
        }
        synchronized (this.targetStyles) {
            if (this.targetStyles.size() > 0) {
                for (String str : this.properties) {
                    if (this.targetStyles.containsKey(str)) {
                        this.mWXComponent.getStyles().put(str, this.targetStyles.remove(str));
                    }
                }
                this.targetStyles.clear();
            }
        }
    }

    private static long parseTimeMillis(Map<String, Object> map, String str, long j) {
        String string = WXUtils.getString(map.get(str), null);
        if (string != null) {
            string = string.replaceAll("ms", "");
        }
        if (string != null) {
            if (WXEnvironment.isApkDebugable() && string.contains("px")) {
                WXLogUtils.w("Transition Duration Unit Only Support ms, " + string + " is not ms Unit");
            }
            string = string.replaceAll("px", "");
        }
        if (TextUtils.isEmpty(string)) {
            return j;
        }
        try {
            return (long) Float.parseFloat(string);
        } catch (NumberFormatException unused) {
            return j;
        }
    }

    private static void updateTransitionProperties(WXTransition wXTransition, String str) {
        if (str == null) {
            return;
        }
        wXTransition.properties.clear();
        for (String str2 : PROPERTY_SPLIT_PATTERN.split(str)) {
            String strTrim = str2.trim();
            if (!TextUtils.isEmpty(strTrim)) {
                if (LAYOUT_PROPERTIES.contains(strTrim) || TRANSFORM_PROPERTIES.contains(strTrim)) {
                    wXTransition.properties.add(strTrim);
                } else if (WXEnvironment.isApkDebugable()) {
                    WXLogUtils.e("WXTransition Property Not Supported" + strTrim + " in " + str);
                }
            }
        }
    }

    public void doPendingLayoutAnimation() {
        ValueAnimator valueAnimator = this.layoutValueAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.layoutValueAnimator = null;
        }
        if (this.layoutPendingUpdates.size() == 0) {
            return;
        }
        PropertyValuesHolder[] propertyValuesHolderArr = new PropertyValuesHolder[this.layoutPendingUpdates.size()];
        if (this.properties.size() == 0) {
            return;
        }
        int i = 0;
        for (String str : this.properties) {
            if (LAYOUT_PROPERTIES.contains(str) && this.layoutPendingUpdates.containsKey(str)) {
                Object objRemove = this.layoutPendingUpdates.remove(str);
                synchronized (this.targetStyles) {
                    this.targetStyles.put(str, objRemove);
                }
                propertyValuesHolderArr[i] = createLayoutPropertyValueHolder(str, objRemove);
                i++;
            }
        }
        this.layoutPendingUpdates.clear();
        doLayoutPropertyValuesHolderAnimation(propertyValuesHolderArr);
    }

    public List<String> getProperties() {
        return this.properties;
    }

    public boolean hasTransitionProperty(Map<String, Object> map) {
        Iterator<String> it = this.properties.iterator();
        while (it.hasNext()) {
            if (map.containsKey(it.next())) {
                return true;
            }
        }
        return false;
    }

    public void startTransition(Map<String, Object> map) {
        synchronized (this.lockToken) {
            if (getTargetView() == null) {
                return;
            }
            final int iIncrementAndGet = this.lockToken.incrementAndGet();
            for (String str : this.properties) {
                if (map.containsKey(str)) {
                    Object objRemove = map.remove(str);
                    if (LAYOUT_PROPERTIES.contains(str)) {
                        this.layoutPendingUpdates.put(str, objRemove);
                    } else if (TRANSFORM_PROPERTIES.contains(str)) {
                        this.transformPendingUpdates.put(str, objRemove);
                    }
                }
            }
            int numberInt = WXUtils.getNumberInt(this.mWXComponent.getAttrs().get("actionDelay"), 16);
            long j = numberInt;
            long j2 = this.duration;
            if (j > j2) {
                numberInt = (int) j2;
            }
            Runnable runnable = this.animationRunnable;
            if (runnable != null) {
                this.handler.removeCallbacks(runnable);
            }
            Runnable runnable2 = new Runnable() { // from class: com.taobao.weex.dom.transition.WXTransition.1
                @Override // java.lang.Runnable
                public void run() {
                    if (iIncrementAndGet == WXTransition.this.lockToken.get()) {
                        WXTransition.this.doTransitionAnimation(iIncrementAndGet);
                    }
                    WXTransition.this.animationRunnable = null;
                }
            };
            this.animationRunnable = runnable2;
            if (numberInt > 0) {
                this.handler.postDelayed(runnable2, numberInt);
            } else {
                runnable2.run();
            }
        }
    }

    public void updateTranstionParams(Map<String, Object> map) {
        if (map.containsKey(TRANSITION_DELAY)) {
            this.mWXComponent.getStyles().put(TRANSITION_DELAY, map.remove(TRANSITION_DELAY));
            this.delay = parseTimeMillis(this.mWXComponent.getStyles(), TRANSITION_DELAY, 0L);
        }
        if (map.containsKey(TRANSITION_TIMING_FUNCTION) && map.get(TRANSITION_TIMING_FUNCTION) != null) {
            this.mWXComponent.getStyles().put(TRANSITION_TIMING_FUNCTION, map.remove(TRANSITION_TIMING_FUNCTION));
            this.interpolator = createTimeInterpolator(this.mWXComponent.getStyles().get(TRANSITION_TIMING_FUNCTION).toString());
        }
        if (map.containsKey(TRANSITION_DURATION)) {
            this.mWXComponent.getStyles().put(TRANSITION_DURATION, map.remove(TRANSITION_DURATION));
            this.duration = parseTimeMillis(this.mWXComponent.getStyles(), TRANSITION_DURATION, 0L);
        }
        if (map.containsKey(TRANSITION_PROPERTY)) {
            this.mWXComponent.getStyles().put(TRANSITION_PROPERTY, map.remove(TRANSITION_PROPERTY));
            updateTransitionProperties(this, WXUtils.getString(this.mWXComponent.getStyles().get(TRANSITION_PROPERTY), null));
        }
    }
}
