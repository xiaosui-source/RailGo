package com.taobao.weex.ui.animation;

import android.animation.PropertyValuesHolder;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Property;
import android.view.View;
import androidx.collection.ArrayMap;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.Constants;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXAnimationBean {
    public static final String CUBIC_BEZIER = "cubic-bezier";
    public static final String EASE_IN = "ease-in";
    public static final String EASE_IN_OUT = "ease-in-out";
    public static final String EASE_OUT = "ease-out";
    public static final String LINEAR = "linear";
    public static final int NUM_CUBIC_PARAM = 4;
    public long delay;
    public long duration;
    public boolean needLayout;
    public Style styles;
    public String timingFunction;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class Style {
        public static final String BACKGROUND_COLOR = "backgroundColor";
        public static final String BOTTOM = "bottom";
        public static final String CENTER = "center";
        private static final String DEG = "deg";
        private static final String FULL = "100%";
        private static final String HALF = "50%";
        public static final String HEIGHT = "height";
        public static final String LEFT = "left";
        private static final String PX = "px";
        public static final String RIGHT = "right";
        public static final String TOP = "top";
        public static final String WIDTH = "width";
        public static final String WX_ROTATE = "rotate";
        public static final String WX_ROTATE_X = "rotateX";
        public static final String WX_ROTATE_Y = "rotateY";
        public static final String WX_SCALE = "scale";
        public static final String WX_SCALE_X = "scaleX";
        public static final String WX_SCALE_Y = "scaleY";
        public static final String WX_TRANSLATE = "translate";
        public static final String WX_TRANSLATE_X = "translateX";
        public static final String WX_TRANSLATE_Y = "translateY";
        private static final String ZERO = "0%";
        private static Map<Property<View, Float>, Float> defaultMap = new ArrayMap();
        public static Map<String, List<Property<View, Float>>> wxToAndroidMap;
        public String backgroundColor;
        public String height;
        public String opacity;
        private Pair<Float, Float> pivot;
        public String transform;
        public String transformOrigin;
        public String width;
        private Map<Property<View, Float>, Float> transformMap = new LinkedHashMap();
        private List<PropertyValuesHolder> holders = new LinkedList();
        private float cameraDistance = Float.MAX_VALUE;

        static {
            wxToAndroidMap = new ArrayMap();
            Map<String, List<Property<View, Float>>> map = wxToAndroidMap;
            Property<View, Float> property = View.TRANSLATION_X;
            Property<View, Float> property2 = View.TRANSLATION_Y;
            map.put("translate", Arrays.asList(property, property2));
            wxToAndroidMap.put("translateX", Collections.singletonList(property));
            wxToAndroidMap.put("translateY", Collections.singletonList(property2));
            Map<String, List<Property<View, Float>>> map2 = wxToAndroidMap;
            Property<View, Float> property3 = View.ROTATION;
            map2.put("rotate", Collections.singletonList(property3));
            Map<String, List<Property<View, Float>>> map3 = wxToAndroidMap;
            Property<View, Float> property4 = View.ROTATION_X;
            map3.put("rotateX", Collections.singletonList(property4));
            Map<String, List<Property<View, Float>>> map4 = wxToAndroidMap;
            Property<View, Float> property5 = View.ROTATION_Y;
            map4.put("rotateY", Collections.singletonList(property5));
            Map<String, List<Property<View, Float>>> map5 = wxToAndroidMap;
            Property<View, Float> property6 = View.SCALE_X;
            Property<View, Float> property7 = View.SCALE_Y;
            map5.put("scale", Arrays.asList(property6, property7));
            wxToAndroidMap.put("scaleX", Collections.singletonList(property6));
            wxToAndroidMap.put("scaleY", Collections.singletonList(property7));
            wxToAndroidMap.put(Constants.Name.PERSPECTIVE, Collections.singletonList(CameraDistanceProperty.getInstance()));
            wxToAndroidMap = Collections.unmodifiableMap(wxToAndroidMap);
            Map<Property<View, Float>, Float> map6 = defaultMap;
            Float fValueOf = Float.valueOf(0.0f);
            map6.put(property, fValueOf);
            defaultMap.put(property2, fValueOf);
            Map<Property<View, Float>, Float> map7 = defaultMap;
            Float fValueOf2 = Float.valueOf(1.0f);
            map7.put(property6, fValueOf2);
            defaultMap.put(property7, fValueOf2);
            defaultMap.put(property3, fValueOf);
            defaultMap.put(property4, fValueOf);
            defaultMap.put(property5, fValueOf);
        }

        private void initHolders() {
            for (Map.Entry<Property<View, Float>, Float> entry : this.transformMap.entrySet()) {
                this.holders.add(PropertyValuesHolder.ofFloat(entry.getKey(), entry.getValue().floatValue()));
            }
            if (TextUtils.isEmpty(this.opacity)) {
                return;
            }
            this.holders.add(PropertyValuesHolder.ofFloat((Property<?, Float>) View.ALPHA, WXUtils.fastGetFloat(this.opacity, 3.0f)));
        }

        private static float parsePercent(String str, int i, float f) {
            return (WXUtils.fastGetFloat(str, f) / 100.0f) * i;
        }

        private static float parsePercentOrPx(String str, int i, float f) {
            int iLastIndexOf = str.lastIndexOf(37);
            if (iLastIndexOf != -1) {
                return parsePercent(str.substring(0, iLastIndexOf), i, 1.0f);
            }
            int iLastIndexOf2 = str.lastIndexOf(PX);
            return iLastIndexOf2 != -1 ? WXViewUtils.getRealPxByWidth(WXUtils.fastGetFloat(str.substring(0, iLastIndexOf2), 1.0f), f) : WXViewUtils.getRealPxByWidth(WXUtils.fastGetFloat(str, 1.0f), f);
        }

        private static Pair<Float, Float> parsePivot(String str, int i, int i2, float f) {
            int iIndexOf;
            if (TextUtils.isEmpty(str) || (iIndexOf = str.indexOf(32)) == -1) {
                return null;
            }
            int i3 = iIndexOf;
            while (i3 < str.length() && str.charAt(i3) == ' ') {
                i3++;
            }
            if (i3 >= str.length() || str.charAt(i3) == ' ') {
                return null;
            }
            ArrayList arrayList = new ArrayList(2);
            arrayList.add(str.substring(0, iIndexOf).trim());
            arrayList.add(str.substring(i3, str.length()).trim());
            return parsePivot(arrayList, i, i2, f);
        }

        private static float parsePivotX(String str, int i, float f) {
            if ("left".equals(str)) {
                str = ZERO;
            } else if ("right".equals(str)) {
                str = FULL;
            } else if ("center".equals(str)) {
                str = HALF;
            }
            return parsePercentOrPx(str, i, f);
        }

        private static float parsePivotY(String str, int i, float f) {
            if ("top".equals(str)) {
                str = ZERO;
            } else if ("bottom".equals(str)) {
                str = FULL;
            } else if ("center".equals(str)) {
                str = HALF;
            }
            return parsePercentOrPx(str, i, f);
        }

        private void resetToDefaultIfAbsent() {
            for (Map.Entry<Property<View, Float>, Float> entry : defaultMap.entrySet()) {
                if (!this.transformMap.containsKey(entry.getKey())) {
                    this.transformMap.put(entry.getKey(), entry.getValue());
                }
            }
        }

        public float getCameraDistance() {
            return this.cameraDistance;
        }

        public List<PropertyValuesHolder> getHolders() {
            return this.holders;
        }

        public Pair<Float, Float> getPivot() {
            return this.pivot;
        }

        public void init(String str, String str2, int i, int i2, float f, WXSDKInstance wXSDKInstance) {
            this.pivot = parsePivot(str, i, i2, f);
            this.transformMap.putAll(TransformParser.parseTransForm(wXSDKInstance.getInstanceId(), str2, i, i2, f));
            resetToDefaultIfAbsent();
            if (this.transformMap.containsKey(CameraDistanceProperty.getInstance())) {
                this.cameraDistance = this.transformMap.remove(CameraDistanceProperty.getInstance()).floatValue();
            }
            initHolders();
        }

        public void init(Map<Property<View, Float>, Pair<Float, Float>> map) {
            for (Map.Entry<Property<View, Float>, Pair<Float, Float>> entry : map.entrySet()) {
                this.holders.add(PropertyValuesHolder.ofFloat(entry.getKey(), ((Float) entry.getValue().first).floatValue(), ((Float) entry.getValue().second).floatValue()));
            }
        }

        private static Pair<Float, Float> parsePivot(List<String> list, int i, int i2, float f) {
            return new Pair<>(Float.valueOf(parsePivotX(list.get(0), i, f)), Float.valueOf(parsePivotY(list.get(1), i2, f)));
        }
    }
}
