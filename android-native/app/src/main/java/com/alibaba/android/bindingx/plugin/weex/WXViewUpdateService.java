package com.alibaba.android.bindingx.plugin.weex;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;
import com.alibaba.android.bindingx.core.LogProxy;
import com.alibaba.android.bindingx.core.PlatformManager;
import com.alibaba.android.bindingx.core.WeakRunnable;
import com.alibaba.android.bindingx.core.internal.Utils;
import com.taobao.weex.common.Constants;
import com.taobao.weex.dom.CSSShorthand;
import com.taobao.weex.dom.transition.WXTransition;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXScroller;
import com.taobao.weex.ui.component.WXText;
import com.taobao.weex.ui.view.WXTextView;
import com.taobao.weex.ui.view.border.BorderDrawable;
import com.taobao.weex.utils.WXUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
final class WXViewUpdateService {
    private static final NOpUpdater EMPTY_INVOKER;
    private static final String LAYOUT_PROPERTY_HEIGHT = "height";
    private static final String LAYOUT_PROPERTY_PADDING_LEFT = "padding-left";
    private static final String LAYOUT_PROPERTY_PADDING_RIGHT = "padding-right";
    private static final String LAYOUT_PROPERTY_WIDTH = "width";
    private static final String PERSPECTIVE = "perspective";
    private static final String TRANSFORM_ORIGIN = "transformOrigin";
    private static final Map<String, IWXViewUpdater> sTransformPropertyUpdaterMap;
    private static final LayoutUpdater sLayoutUpdater = new LayoutUpdater();
    private static final String LAYOUT_PROPERTY_MARGIN_LEFT = "margin-left";
    private static final String LAYOUT_PROPERTY_MARGIN_RIGHT = "margin-right";
    private static final String LAYOUT_PROPERTY_MARGIN_TOP = "margin-top";
    private static final String LAYOUT_PROPERTY_MARGIN_BOTTOM = "margin-bottom";
    private static final String LAYOUT_PROPERTY_PADDING_TOP = "padding-top";
    private static final String LAYOUT_PROPERTY_PADDING_BOTTOM = "padding-bottom";
    private static final List<String> LAYOUT_PROPERTIES = Arrays.asList("width", "height", LAYOUT_PROPERTY_MARGIN_LEFT, LAYOUT_PROPERTY_MARGIN_RIGHT, LAYOUT_PROPERTY_MARGIN_TOP, LAYOUT_PROPERTY_MARGIN_BOTTOM, "padding-left", "padding-right", LAYOUT_PROPERTY_PADDING_TOP, LAYOUT_PROPERTY_PADDING_BOTTOM);
    private static final Handler sUIHandler = new Handler(Looper.getMainLooper());

    WXViewUpdateService() {
    }

    static {
        EMPTY_INVOKER = new NOpUpdater();
        HashMap map = new HashMap();
        sTransformPropertyUpdaterMap = map;
        map.put("opacity", new OpacityUpdater());
        map.put("transform.translate", new TranslateUpdater());
        map.put("transform.translateX", new TranslateXUpdater());
        map.put("transform.translateY", new TranslateYUpdater());
        map.put("transform.scale", new ScaleUpdater());
        map.put("transform.scaleX", new ScaleXUpdater());
        map.put("transform.scaleY", new ScaleYUpdater());
        map.put("transform.rotate", new RotateUpdater());
        map.put("transform.rotateZ", new RotateUpdater());
        map.put("transform.rotateX", new RotateXUpdater());
        map.put("transform.rotateY", new RotateYUpdater());
        map.put("background-color", new BackgroundUpdater());
        map.put("color", new ColorUpdater());
        map.put("scroll.contentOffset", new ContentOffsetUpdater());
        map.put("scroll.contentOffsetX", new ContentOffsetXUpdater());
        map.put("scroll.contentOffsetY", new ContentOffsetYUpdater());
        map.put("border-top-left-radius", new BorderRadiusTopLeftUpdater());
        map.put("border-top-right-radius", new BorderRadiusTopRightUpdater());
        map.put("border-bottom-left-radius", new BorderRadiusBottomLeftUpdater());
        map.put("border-bottom-right-radius", new BorderRadiusBottomRightUpdater());
        map.put("border-radius", new BorderRadiusUpdater());
    }

    static IWXViewUpdater findUpdater(String str) {
        IWXViewUpdater iWXViewUpdater = sTransformPropertyUpdaterMap.get(str);
        if (iWXViewUpdater != null) {
            return iWXViewUpdater;
        }
        if (LAYOUT_PROPERTIES.contains(str)) {
            LayoutUpdater layoutUpdater = sLayoutUpdater;
            layoutUpdater.setPropertyName(str);
            return layoutUpdater;
        }
        LogProxy.e("unknown property [" + str + Operators.ARRAY_END_STR);
        return EMPTY_INVOKER;
    }

    private static final class NOpUpdater implements IWXViewUpdater {
        @Override // com.alibaba.android.bindingx.plugin.weex.IWXViewUpdater
        public void update(WXComponent wXComponent, View view, Object obj, PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, Map<String, Object> map) {
        }

        private NOpUpdater() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void runOnUIThread(Runnable runnable) {
        sUIHandler.post(new WeakRunnable(runnable));
    }

    public static void clearCallbacks() {
        sUIHandler.removeCallbacksAndMessages(null);
    }

    private static final class ContentOffsetUpdater implements IWXViewUpdater {
        private ContentOffsetUpdater() {
        }

        @Override // com.alibaba.android.bindingx.plugin.weex.IWXViewUpdater
        public void update(WXComponent wXComponent, View view, Object obj, final PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, Map<String, Object> map) {
            final View viewFindScrollTarget = WXViewUpdateService.findScrollTarget(wXComponent);
            if (viewFindScrollTarget == null) {
                return;
            }
            if (obj instanceof Double) {
                final double dDoubleValue = ((Double) obj).doubleValue();
                WXViewUpdateService.runOnUIThread(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXViewUpdateService.ContentOffsetUpdater.1
                    @Override // java.lang.Runnable
                    public void run() {
                        viewFindScrollTarget.setScrollX((int) WXViewUpdateService.getRealSize(dDoubleValue, iDeviceResolutionTranslator));
                        viewFindScrollTarget.setScrollY((int) WXViewUpdateService.getRealSize(dDoubleValue, iDeviceResolutionTranslator));
                    }
                });
            } else if (obj instanceof ArrayList) {
                ArrayList arrayList = (ArrayList) obj;
                if (arrayList.size() >= 2 && (arrayList.get(0) instanceof Double) && (arrayList.get(1) instanceof Double)) {
                    final double dDoubleValue2 = ((Double) arrayList.get(0)).doubleValue();
                    final double dDoubleValue3 = ((Double) arrayList.get(1)).doubleValue();
                    WXViewUpdateService.runOnUIThread(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXViewUpdateService.ContentOffsetUpdater.2
                        @Override // java.lang.Runnable
                        public void run() {
                            viewFindScrollTarget.setScrollX((int) WXViewUpdateService.getRealSize(dDoubleValue2, iDeviceResolutionTranslator));
                            viewFindScrollTarget.setScrollY((int) WXViewUpdateService.getRealSize(dDoubleValue3, iDeviceResolutionTranslator));
                        }
                    });
                }
            }
        }
    }

    private static final class ContentOffsetXUpdater implements IWXViewUpdater {
        private ContentOffsetXUpdater() {
        }

        @Override // com.alibaba.android.bindingx.plugin.weex.IWXViewUpdater
        public void update(WXComponent wXComponent, View view, Object obj, final PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, Map<String, Object> map) {
            final View viewFindScrollTarget = WXViewUpdateService.findScrollTarget(wXComponent);
            if (viewFindScrollTarget != null && (obj instanceof Double)) {
                final double dDoubleValue = ((Double) obj).doubleValue();
                WXViewUpdateService.runOnUIThread(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXViewUpdateService.ContentOffsetXUpdater.1
                    @Override // java.lang.Runnable
                    public void run() {
                        viewFindScrollTarget.setScrollX((int) WXViewUpdateService.getRealSize(dDoubleValue, iDeviceResolutionTranslator));
                    }
                });
            }
        }
    }

    private static final class ContentOffsetYUpdater implements IWXViewUpdater {
        private ContentOffsetYUpdater() {
        }

        @Override // com.alibaba.android.bindingx.plugin.weex.IWXViewUpdater
        public void update(WXComponent wXComponent, View view, Object obj, final PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, Map<String, Object> map) {
            final View viewFindScrollTarget;
            if ((obj instanceof Double) && (viewFindScrollTarget = WXViewUpdateService.findScrollTarget(wXComponent)) != null) {
                final double dDoubleValue = ((Double) obj).doubleValue();
                WXViewUpdateService.runOnUIThread(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXViewUpdateService.ContentOffsetYUpdater.1
                    @Override // java.lang.Runnable
                    public void run() {
                        viewFindScrollTarget.setScrollY((int) WXViewUpdateService.getRealSize(dDoubleValue, iDeviceResolutionTranslator));
                    }
                });
            }
        }
    }

    private static final class OpacityUpdater implements IWXViewUpdater {
        private OpacityUpdater() {
        }

        @Override // com.alibaba.android.bindingx.plugin.weex.IWXViewUpdater
        public void update(WXComponent wXComponent, final View view, Object obj, PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, Map<String, Object> map) {
            if (obj instanceof Double) {
                final float fDoubleValue = (float) ((Double) obj).doubleValue();
                WXViewUpdateService.runOnUIThread(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXViewUpdateService.OpacityUpdater.1
                    @Override // java.lang.Runnable
                    public void run() {
                        view.setAlpha(fDoubleValue);
                    }
                });
            }
        }
    }

    private static final class TranslateUpdater implements IWXViewUpdater {
        private TranslateUpdater() {
        }

        @Override // com.alibaba.android.bindingx.plugin.weex.IWXViewUpdater
        public void update(WXComponent wXComponent, final View view, Object obj, final PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, Map<String, Object> map) {
            if (obj instanceof ArrayList) {
                ArrayList arrayList = (ArrayList) obj;
                if (arrayList.size() >= 2 && (arrayList.get(0) instanceof Double) && (arrayList.get(1) instanceof Double)) {
                    final double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
                    final double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
                    WXViewUpdateService.runOnUIThread(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXViewUpdateService.TranslateUpdater.1
                        @Override // java.lang.Runnable
                        public void run() {
                            view.setTranslationX((float) WXViewUpdateService.getRealSize(dDoubleValue, iDeviceResolutionTranslator));
                            view.setTranslationY((float) WXViewUpdateService.getRealSize(dDoubleValue2, iDeviceResolutionTranslator));
                        }
                    });
                }
            }
        }
    }

    private static final class TranslateXUpdater implements IWXViewUpdater {
        private TranslateXUpdater() {
        }

        @Override // com.alibaba.android.bindingx.plugin.weex.IWXViewUpdater
        public void update(WXComponent wXComponent, final View view, Object obj, final PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, Map<String, Object> map) {
            if (obj instanceof Double) {
                final double dDoubleValue = ((Double) obj).doubleValue();
                WXViewUpdateService.runOnUIThread(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXViewUpdateService.TranslateXUpdater.1
                    @Override // java.lang.Runnable
                    public void run() {
                        view.setTranslationX((float) WXViewUpdateService.getRealSize(dDoubleValue, iDeviceResolutionTranslator));
                    }
                });
            }
        }
    }

    private static final class TranslateYUpdater implements IWXViewUpdater {
        private TranslateYUpdater() {
        }

        @Override // com.alibaba.android.bindingx.plugin.weex.IWXViewUpdater
        public void update(WXComponent wXComponent, final View view, Object obj, final PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, Map<String, Object> map) {
            if (obj instanceof Double) {
                final double dDoubleValue = ((Double) obj).doubleValue();
                WXViewUpdateService.runOnUIThread(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXViewUpdateService.TranslateYUpdater.1
                    @Override // java.lang.Runnable
                    public void run() {
                        view.setTranslationY((float) WXViewUpdateService.getRealSize(dDoubleValue, iDeviceResolutionTranslator));
                    }
                });
            }
        }
    }

    private static final class ScaleUpdater implements IWXViewUpdater {
        private ScaleUpdater() {
        }

        @Override // com.alibaba.android.bindingx.plugin.weex.IWXViewUpdater
        public void update(WXComponent wXComponent, final View view, final Object obj, PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, final Map<String, Object> map) {
            WXViewUpdateService.runOnUIThread(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXViewUpdateService.ScaleUpdater.1
                @Override // java.lang.Runnable
                public void run() throws Throwable {
                    int iNormalizedPerspectiveValue = Utils.normalizedPerspectiveValue(view.getContext(), WXUtils.getInt(map.get("perspective")));
                    Pair<Float, Float> transformOrigin = Utils.parseTransformOrigin(WXUtils.getString(map.get("transformOrigin"), null), view);
                    if (iNormalizedPerspectiveValue != 0) {
                        view.setCameraDistance(iNormalizedPerspectiveValue);
                    }
                    if (transformOrigin != null) {
                        view.setPivotX(((Float) transformOrigin.first).floatValue());
                        view.setPivotY(((Float) transformOrigin.second).floatValue());
                    }
                    Object obj2 = obj;
                    if (obj2 instanceof Double) {
                        float fDoubleValue = (float) ((Double) obj2).doubleValue();
                        view.setScaleX(fDoubleValue);
                        view.setScaleY(fDoubleValue);
                        return;
                    }
                    if (obj2 instanceof ArrayList) {
                        ArrayList arrayList = (ArrayList) obj2;
                        if (arrayList.size() >= 2 && (arrayList.get(0) instanceof Double) && (arrayList.get(1) instanceof Double)) {
                            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
                            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
                            view.setScaleX((float) dDoubleValue);
                            view.setScaleY((float) dDoubleValue2);
                        }
                    }
                }
            });
        }
    }

    private static final class ScaleXUpdater implements IWXViewUpdater {
        private ScaleXUpdater() {
        }

        @Override // com.alibaba.android.bindingx.plugin.weex.IWXViewUpdater
        public void update(WXComponent wXComponent, final View view, final Object obj, PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, final Map<String, Object> map) {
            if (obj instanceof Double) {
                WXViewUpdateService.runOnUIThread(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXViewUpdateService.ScaleXUpdater.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Pair<Float, Float> transformOrigin = Utils.parseTransformOrigin(WXUtils.getString(map.get("transformOrigin"), null), view);
                        if (transformOrigin != null) {
                            view.setPivotX(((Float) transformOrigin.first).floatValue());
                            view.setPivotY(((Float) transformOrigin.second).floatValue());
                        }
                        view.setScaleX((float) ((Double) obj).doubleValue());
                    }
                });
            }
        }
    }

    private static final class ScaleYUpdater implements IWXViewUpdater {
        private ScaleYUpdater() {
        }

        @Override // com.alibaba.android.bindingx.plugin.weex.IWXViewUpdater
        public void update(WXComponent wXComponent, final View view, final Object obj, PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, final Map<String, Object> map) {
            if (obj instanceof Double) {
                WXViewUpdateService.runOnUIThread(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXViewUpdateService.ScaleYUpdater.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Pair<Float, Float> transformOrigin = Utils.parseTransformOrigin(WXUtils.getString(map.get("transformOrigin"), null), view);
                        if (transformOrigin != null) {
                            view.setPivotX(((Float) transformOrigin.first).floatValue());
                            view.setPivotY(((Float) transformOrigin.second).floatValue());
                        }
                        view.setScaleY((float) ((Double) obj).doubleValue());
                    }
                });
            }
        }
    }

    private static final class RotateUpdater implements IWXViewUpdater {
        private RotateUpdater() {
        }

        @Override // com.alibaba.android.bindingx.plugin.weex.IWXViewUpdater
        public void update(WXComponent wXComponent, final View view, final Object obj, PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, final Map<String, Object> map) {
            if (obj instanceof Double) {
                WXViewUpdateService.runOnUIThread(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXViewUpdateService.RotateUpdater.1
                    @Override // java.lang.Runnable
                    public void run() throws Throwable {
                        int iNormalizedPerspectiveValue = Utils.normalizedPerspectiveValue(view.getContext(), WXUtils.getInt(map.get("perspective")));
                        Pair<Float, Float> transformOrigin = Utils.parseTransformOrigin(WXUtils.getString(map.get("transformOrigin"), null), view);
                        if (iNormalizedPerspectiveValue != 0) {
                            view.setCameraDistance(iNormalizedPerspectiveValue);
                        }
                        if (transformOrigin != null) {
                            view.setPivotX(((Float) transformOrigin.first).floatValue());
                            view.setPivotY(((Float) transformOrigin.second).floatValue());
                        }
                        view.setRotation((float) ((Double) obj).doubleValue());
                    }
                });
            }
        }
    }

    private static final class RotateXUpdater implements IWXViewUpdater {
        private RotateXUpdater() {
        }

        @Override // com.alibaba.android.bindingx.plugin.weex.IWXViewUpdater
        public void update(WXComponent wXComponent, final View view, final Object obj, PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, final Map<String, Object> map) {
            if (obj instanceof Double) {
                WXViewUpdateService.runOnUIThread(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXViewUpdateService.RotateXUpdater.1
                    @Override // java.lang.Runnable
                    public void run() throws Throwable {
                        int iNormalizedPerspectiveValue = Utils.normalizedPerspectiveValue(view.getContext(), WXUtils.getInt(map.get("perspective")));
                        Pair<Float, Float> transformOrigin = Utils.parseTransformOrigin(WXUtils.getString(map.get("transformOrigin"), null), view);
                        if (iNormalizedPerspectiveValue != 0) {
                            view.setCameraDistance(iNormalizedPerspectiveValue);
                        }
                        if (transformOrigin != null) {
                            view.setPivotX(((Float) transformOrigin.first).floatValue());
                            view.setPivotY(((Float) transformOrigin.second).floatValue());
                        }
                        view.setRotationX((float) ((Double) obj).doubleValue());
                    }
                });
            }
        }
    }

    private static final class RotateYUpdater implements IWXViewUpdater {
        private RotateYUpdater() {
        }

        @Override // com.alibaba.android.bindingx.plugin.weex.IWXViewUpdater
        public void update(WXComponent wXComponent, final View view, final Object obj, PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, final Map<String, Object> map) {
            if (obj instanceof Double) {
                WXViewUpdateService.runOnUIThread(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXViewUpdateService.RotateYUpdater.1
                    @Override // java.lang.Runnable
                    public void run() throws Throwable {
                        int iNormalizedPerspectiveValue = Utils.normalizedPerspectiveValue(view.getContext(), WXUtils.getInt(map.get("perspective")));
                        Pair<Float, Float> transformOrigin = Utils.parseTransformOrigin(WXUtils.getString(map.get("transformOrigin"), null), view);
                        if (iNormalizedPerspectiveValue != 0) {
                            view.setCameraDistance(iNormalizedPerspectiveValue);
                        }
                        if (transformOrigin != null) {
                            view.setPivotX(((Float) transformOrigin.first).floatValue());
                            view.setPivotY(((Float) transformOrigin.second).floatValue());
                        }
                        view.setRotationY((float) ((Double) obj).doubleValue());
                    }
                });
            }
        }
    }

    static final class LayoutUpdater implements IWXViewUpdater {
        private String propertyName;

        LayoutUpdater() {
        }

        void setPropertyName(String str) {
            this.propertyName = str;
        }

        @Override // com.alibaba.android.bindingx.plugin.weex.IWXViewUpdater
        public void update(WXComponent wXComponent, View view, Object obj, PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, Map<String, Object> map) {
            String str;
            if (!(obj instanceof Double) || TextUtils.isEmpty(this.propertyName)) {
                return;
            }
            double dDoubleValue = ((Double) obj).doubleValue();
            String str2 = this.propertyName;
            str2.hashCode();
            str = "width";
            switch (str2) {
                case "padding-top":
                    str = Constants.Name.PADDING_TOP;
                    break;
                case "height":
                    str = "height";
                    break;
                case "margin-right":
                    str = Constants.Name.MARGIN_RIGHT;
                    break;
                case "padding-right":
                    str = Constants.Name.PADDING_RIGHT;
                    break;
                case "width":
                    break;
                case "padding-bottom":
                    str = Constants.Name.PADDING_BOTTOM;
                    break;
                case "padding-left":
                    str = Constants.Name.PADDING_LEFT;
                    break;
                case "margin-left":
                    str = Constants.Name.MARGIN_LEFT;
                    break;
                case "margin-top":
                    str = Constants.Name.MARGIN_TOP;
                    break;
                case "margin-bottom":
                    str = Constants.Name.MARGIN_BOTTOM;
                    break;
                default:
                    str = null;
                    break;
            }
            if (TextUtils.isEmpty(str)) {
                return;
            }
            WXTransition.asynchronouslyUpdateLayout(wXComponent, str, (float) WXViewUpdateService.getRealSize(dDoubleValue, iDeviceResolutionTranslator));
            this.propertyName = null;
        }
    }

    private static final class BackgroundUpdater implements IWXViewUpdater {
        private BackgroundUpdater() {
        }

        @Override // com.alibaba.android.bindingx.plugin.weex.IWXViewUpdater
        public void update(WXComponent wXComponent, final View view, Object obj, PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, Map<String, Object> map) {
            if (obj instanceof Integer) {
                final int iIntValue = ((Integer) obj).intValue();
                WXViewUpdateService.runOnUIThread(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXViewUpdateService.BackgroundUpdater.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Drawable background = view.getBackground();
                        if (background == null) {
                            view.setBackgroundColor(iIntValue);
                        } else if (background instanceof BorderDrawable) {
                            ((BorderDrawable) background).setColor(iIntValue);
                        } else if (background instanceof ColorDrawable) {
                            ((ColorDrawable) background).setColor(iIntValue);
                        }
                    }
                });
            }
        }
    }

    private static final class ColorUpdater implements IWXViewUpdater {
        private ColorUpdater() {
        }

        @Override // com.alibaba.android.bindingx.plugin.weex.IWXViewUpdater
        public void update(final WXComponent wXComponent, final View view, Object obj, PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, Map<String, Object> map) {
            if (obj instanceof Integer) {
                final int iIntValue = ((Integer) obj).intValue();
                WXViewUpdateService.runOnUIThread(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXViewUpdateService.ColorUpdater.1
                    @Override // java.lang.Runnable
                    public void run() {
                        View view2 = view;
                        if (view2 instanceof TextView) {
                            ((TextView) view2).setTextColor(iIntValue);
                            return;
                        }
                        if ((wXComponent instanceof WXText) && (view2 instanceof WXTextView)) {
                            try {
                                ((WXTextView) view2).setTextColor(iIntValue);
                                view.invalidate();
                            } catch (Throwable th) {
                                LogProxy.e("can not update text color, try fallback to call the old API", th);
                                Layout textLayout = ((WXTextView) view).getTextLayout();
                                if (textLayout != null) {
                                    TextPaint paint = textLayout.getPaint();
                                    if (paint != null) {
                                        paint.setColor(iIntValue);
                                    }
                                    view.invalidate();
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    private static final class BorderRadiusTopLeftUpdater implements IWXViewUpdater {
        private BorderRadiusTopLeftUpdater() {
        }

        @Override // com.alibaba.android.bindingx.plugin.weex.IWXViewUpdater
        public void update(WXComponent wXComponent, final View view, Object obj, final PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, Map<String, Object> map) {
            if (obj instanceof Double) {
                final double dDoubleValue = ((Double) obj).doubleValue();
                WXViewUpdateService.runOnUIThread(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXViewUpdateService.BorderRadiusTopLeftUpdater.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Drawable background = view.getBackground();
                        if (background == null || !(background instanceof BorderDrawable)) {
                            return;
                        }
                        ((BorderDrawable) background).setBorderRadius(CSSShorthand.CORNER.BORDER_TOP_LEFT, (float) WXViewUpdateService.getRealSize(dDoubleValue, iDeviceResolutionTranslator));
                    }
                });
            }
        }
    }

    private static final class BorderRadiusTopRightUpdater implements IWXViewUpdater {
        private BorderRadiusTopRightUpdater() {
        }

        @Override // com.alibaba.android.bindingx.plugin.weex.IWXViewUpdater
        public void update(WXComponent wXComponent, final View view, Object obj, final PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, Map<String, Object> map) {
            if (obj instanceof Double) {
                final double dDoubleValue = ((Double) obj).doubleValue();
                WXViewUpdateService.runOnUIThread(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXViewUpdateService.BorderRadiusTopRightUpdater.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Drawable background = view.getBackground();
                        if (background == null || !(background instanceof BorderDrawable)) {
                            return;
                        }
                        ((BorderDrawable) background).setBorderRadius(CSSShorthand.CORNER.BORDER_TOP_RIGHT, (float) WXViewUpdateService.getRealSize(dDoubleValue, iDeviceResolutionTranslator));
                    }
                });
            }
        }
    }

    private static final class BorderRadiusBottomLeftUpdater implements IWXViewUpdater {
        private BorderRadiusBottomLeftUpdater() {
        }

        @Override // com.alibaba.android.bindingx.plugin.weex.IWXViewUpdater
        public void update(WXComponent wXComponent, final View view, Object obj, final PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, Map<String, Object> map) {
            if (obj instanceof Double) {
                final double dDoubleValue = ((Double) obj).doubleValue();
                WXViewUpdateService.runOnUIThread(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXViewUpdateService.BorderRadiusBottomLeftUpdater.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Drawable background = view.getBackground();
                        if (background == null || !(background instanceof BorderDrawable)) {
                            return;
                        }
                        ((BorderDrawable) background).setBorderRadius(CSSShorthand.CORNER.BORDER_BOTTOM_LEFT, (float) WXViewUpdateService.getRealSize(dDoubleValue, iDeviceResolutionTranslator));
                    }
                });
            }
        }
    }

    private static final class BorderRadiusBottomRightUpdater implements IWXViewUpdater {
        private BorderRadiusBottomRightUpdater() {
        }

        @Override // com.alibaba.android.bindingx.plugin.weex.IWXViewUpdater
        public void update(WXComponent wXComponent, final View view, Object obj, final PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, Map<String, Object> map) {
            if (obj instanceof Double) {
                final double dDoubleValue = ((Double) obj).doubleValue();
                WXViewUpdateService.runOnUIThread(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXViewUpdateService.BorderRadiusBottomRightUpdater.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Drawable background = view.getBackground();
                        if (background == null || !(background instanceof BorderDrawable)) {
                            return;
                        }
                        ((BorderDrawable) background).setBorderRadius(CSSShorthand.CORNER.BORDER_BOTTOM_RIGHT, (float) WXViewUpdateService.getRealSize(dDoubleValue, iDeviceResolutionTranslator));
                    }
                });
            }
        }
    }

    private static final class BorderRadiusUpdater implements IWXViewUpdater {
        private BorderRadiusUpdater() {
        }

        @Override // com.alibaba.android.bindingx.plugin.weex.IWXViewUpdater
        public void update(WXComponent wXComponent, final View view, Object obj, final PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator, Map<String, Object> map) {
            if (obj instanceof ArrayList) {
                final ArrayList arrayList = (ArrayList) obj;
                if (arrayList.size() != 4) {
                    return;
                }
                WXViewUpdateService.runOnUIThread(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXViewUpdateService.BorderRadiusUpdater.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Drawable background = view.getBackground();
                        if (background == null || !(background instanceof BorderDrawable)) {
                            return;
                        }
                        double dDoubleValue = arrayList.get(0) instanceof Double ? ((Double) arrayList.get(0)).doubleValue() : 0.0d;
                        double dDoubleValue2 = arrayList.get(1) instanceof Double ? ((Double) arrayList.get(1)).doubleValue() : 0.0d;
                        double dDoubleValue3 = arrayList.get(2) instanceof Double ? ((Double) arrayList.get(2)).doubleValue() : 0.0d;
                        double dDoubleValue4 = arrayList.get(3) instanceof Double ? ((Double) arrayList.get(3)).doubleValue() : 0.0d;
                        BorderDrawable borderDrawable = (BorderDrawable) background;
                        borderDrawable.setBorderRadius(CSSShorthand.CORNER.BORDER_TOP_LEFT, (float) WXViewUpdateService.getRealSize(dDoubleValue, iDeviceResolutionTranslator));
                        borderDrawable.setBorderRadius(CSSShorthand.CORNER.BORDER_TOP_RIGHT, (float) WXViewUpdateService.getRealSize(dDoubleValue2, iDeviceResolutionTranslator));
                        borderDrawable.setBorderRadius(CSSShorthand.CORNER.BORDER_BOTTOM_LEFT, (float) WXViewUpdateService.getRealSize(dDoubleValue3, iDeviceResolutionTranslator));
                        borderDrawable.setBorderRadius(CSSShorthand.CORNER.BORDER_BOTTOM_RIGHT, (float) WXViewUpdateService.getRealSize(dDoubleValue4, iDeviceResolutionTranslator));
                    }
                });
                return;
            }
            if (obj instanceof Double) {
                final double dDoubleValue = ((Double) obj).doubleValue();
                WXViewUpdateService.runOnUIThread(new Runnable() { // from class: com.alibaba.android.bindingx.plugin.weex.WXViewUpdateService.BorderRadiusUpdater.2
                    @Override // java.lang.Runnable
                    public void run() {
                        Drawable background = view.getBackground();
                        if (background == null || !(background instanceof BorderDrawable)) {
                            return;
                        }
                        BorderDrawable borderDrawable = (BorderDrawable) background;
                        borderDrawable.setBorderRadius(CSSShorthand.CORNER.BORDER_TOP_LEFT, (float) WXViewUpdateService.getRealSize(dDoubleValue, iDeviceResolutionTranslator));
                        borderDrawable.setBorderRadius(CSSShorthand.CORNER.BORDER_TOP_RIGHT, (float) WXViewUpdateService.getRealSize(dDoubleValue, iDeviceResolutionTranslator));
                        borderDrawable.setBorderRadius(CSSShorthand.CORNER.BORDER_BOTTOM_LEFT, (float) WXViewUpdateService.getRealSize(dDoubleValue, iDeviceResolutionTranslator));
                        borderDrawable.setBorderRadius(CSSShorthand.CORNER.BORDER_BOTTOM_RIGHT, (float) WXViewUpdateService.getRealSize(dDoubleValue, iDeviceResolutionTranslator));
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double getRealSize(double d, PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator) {
        return iDeviceResolutionTranslator.webToNative(d, new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static View findScrollTarget(WXComponent wXComponent) {
        if (!(wXComponent instanceof WXScroller)) {
            LogProxy.e("scroll offset only support on Scroller Component");
            return null;
        }
        return ((WXScroller) wXComponent).getInnerView();
    }
}
