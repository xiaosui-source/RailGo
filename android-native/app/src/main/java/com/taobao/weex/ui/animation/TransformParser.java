package com.taobao.weex.ui.animation;

import android.animation.PropertyValuesHolder;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Property;
import android.view.View;
import androidx.collection.ArrayMap;
import com.taobao.weex.common.Constants;
import com.taobao.weex.common.WXErrorCode;
import com.taobao.weex.utils.FunctionParser;
import com.taobao.weex.utils.WXDataStructureUtil;
import com.taobao.weex.utils.WXExceptionUtils;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class TransformParser {
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
    public static final String WX_ROTATE_Z = "rotateZ";
    public static final String WX_SCALE = "scale";
    public static final String WX_SCALE_X = "scaleX";
    public static final String WX_SCALE_Y = "scaleY";
    public static final String WX_TRANSLATE = "translate";
    public static final String WX_TRANSLATE_X = "translateX";
    public static final String WX_TRANSLATE_Y = "translateY";
    private static final String ZERO = "0%";
    public static Map<String, List<Property<View, Float>>> wxToAndroidMap;

    static {
        ArrayMap arrayMap = new ArrayMap();
        wxToAndroidMap = arrayMap;
        Property property = View.TRANSLATION_X;
        Property property2 = View.TRANSLATION_Y;
        arrayMap.put("translate", Arrays.asList(property, property2));
        wxToAndroidMap.put("translateX", Collections.singletonList(property));
        wxToAndroidMap.put("translateY", Collections.singletonList(property2));
        Map<String, List<Property<View, Float>>> map = wxToAndroidMap;
        Property property3 = View.ROTATION;
        map.put("rotate", Collections.singletonList(property3));
        wxToAndroidMap.put(WX_ROTATE_Z, Collections.singletonList(property3));
        wxToAndroidMap.put("rotateX", Collections.singletonList(View.ROTATION_X));
        wxToAndroidMap.put("rotateY", Collections.singletonList(View.ROTATION_Y));
        Map<String, List<Property<View, Float>>> map2 = wxToAndroidMap;
        Property property4 = View.SCALE_X;
        Property property5 = View.SCALE_Y;
        map2.put("scale", Arrays.asList(property4, property5));
        wxToAndroidMap.put("scaleX", Collections.singletonList(property4));
        wxToAndroidMap.put("scaleY", Collections.singletonList(property5));
        wxToAndroidMap.put(Constants.Name.PERSPECTIVE, Collections.singletonList(CameraDistanceProperty.getInstance()));
        wxToAndroidMap = Collections.unmodifiableMap(wxToAndroidMap);
    }

    private static float parsePercent(String str, int i, int i2) {
        return (WXUtils.fastGetFloat(str, i2) / 100.0f) * i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float parsePercentOrPx(String str, int i, float f) {
        int iLastIndexOf = str.lastIndexOf(37);
        if (iLastIndexOf != -1) {
            return parsePercent(str.substring(0, iLastIndexOf), i, 1);
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

    public static Map<Property<View, Float>, Float> parseTransForm(String str, String str2, final int i, final int i2, final float f) {
        try {
            if (!TextUtils.isEmpty(str2)) {
                return new FunctionParser(str2, new FunctionParser.Mapper<Property<View, Float>, Float>() { // from class: com.taobao.weex.ui.animation.TransformParser.1
                    private Map<Property<View, Float>, Float> convertParam(int i3, int i4, float f2, List<Property<View, Float>> list, List<String> list2) {
                        List<Property<View, Float>> list3;
                        HashMap mapNewHashMapWithExpectedSize = WXDataStructureUtil.newHashMapWithExpectedSize(list.size());
                        ArrayList arrayList = new ArrayList(list.size());
                        if (list.contains(View.ROTATION) || list.contains(View.ROTATION_X) || list.contains(View.ROTATION_Y)) {
                            list3 = list;
                            arrayList.addAll(parseRotationZ(list2));
                        } else if (list.contains(View.TRANSLATION_X) || list.contains(View.TRANSLATION_Y)) {
                            list3 = list;
                            arrayList.addAll(parseTranslation(list3, i3, i4, list2, f2));
                        } else {
                            if (list.contains(View.SCALE_X) || list.contains(View.SCALE_Y)) {
                                arrayList.addAll(parseScale(list.size(), list2));
                            } else if (list.contains(CameraDistanceProperty.getInstance())) {
                                arrayList.add(parseCameraDistance(list2));
                            }
                            list3 = list;
                        }
                        if (list3.size() == arrayList.size()) {
                            for (int i5 = 0; i5 < list3.size(); i5++) {
                                mapNewHashMapWithExpectedSize.put(list3.get(i5), (Float) arrayList.get(i5));
                            }
                        }
                        return mapNewHashMapWithExpectedSize;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:9:0x0032  */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct add '--show-bad-code' argument
                    */
                    private java.lang.Float parseCameraDistance(java.util.List<java.lang.String> r3) {
                        /*
                            r2 = this;
                            int r0 = r3.size()
                            r1 = 1
                            if (r0 != r1) goto L32
                            r0 = 0
                            java.lang.Object r3 = r3.get(r0)
                            float r3 = com.taobao.weex.utils.WXUtils.getFloat(r3)
                            float r0 = r3
                            float r3 = com.taobao.weex.utils.WXViewUtils.getRealPxByWidth(r3, r0)
                            android.app.Application r0 = com.taobao.weex.WXEnvironment.getApplication()
                            android.content.res.Resources r0 = r0.getResources()
                            android.util.DisplayMetrics r0 = r0.getDisplayMetrics()
                            float r0 = r0.density
                            boolean r1 = java.lang.Float.isNaN(r3)
                            if (r1 != 0) goto L32
                            r1 = 0
                            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
                            if (r1 <= 0) goto L32
                            float r3 = r3 * r0
                            goto L35
                        L32:
                            r3 = 2139095039(0x7f7fffff, float:3.4028235E38)
                        L35:
                            java.lang.Float r3 = java.lang.Float.valueOf(r3)
                            return r3
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.ui.animation.TransformParser.AnonymousClass1.parseCameraDistance(java.util.List):java.lang.Float");
                    }

                    private void parseDoubleTranslation(int i3, int i4, List<String> list, List<Float> list2, String str3, float f2) {
                        String str4 = list.size() == 1 ? str3 : list.get(1);
                        list2.add(Float.valueOf(TransformParser.parsePercentOrPx(str3, i3, f2)));
                        list2.add(Float.valueOf(TransformParser.parsePercentOrPx(str4, i4, f2)));
                    }

                    private List<Float> parseRotationZ(List<String> list) {
                        ArrayList arrayList = new ArrayList(1);
                        for (String str3 : list) {
                            int iLastIndexOf = str3.lastIndexOf(TransformParser.DEG);
                            if (iLastIndexOf != -1) {
                                arrayList.add(Float.valueOf(WXUtils.fastGetFloat(str3.substring(0, iLastIndexOf))));
                            } else {
                                arrayList.add(Float.valueOf((float) Math.toDegrees(WXUtils.fastGetFloat(str3))));
                            }
                        }
                        return arrayList;
                    }

                    private List<Float> parseScale(int i3, List<String> list) {
                        ArrayList arrayList = new ArrayList(list.size() * 2);
                        ArrayList arrayList2 = new ArrayList(list.size());
                        Iterator<String> it = list.iterator();
                        while (it.hasNext()) {
                            arrayList2.add(Float.valueOf(WXUtils.fastGetFloat(it.next())));
                        }
                        arrayList.addAll(arrayList2);
                        if (i3 != 1 && list.size() == 1) {
                            arrayList.addAll(arrayList2);
                        }
                        return arrayList;
                    }

                    private void parseSingleTranslation(List<Property<View, Float>> list, int i3, int i4, List<Float> list2, String str3, float f2) {
                        if (list.contains(View.TRANSLATION_X)) {
                            list2.add(Float.valueOf(TransformParser.parsePercentOrPx(str3, i3, f2)));
                        } else if (list.contains(View.TRANSLATION_Y)) {
                            list2.add(Float.valueOf(TransformParser.parsePercentOrPx(str3, i4, f2)));
                        }
                    }

                    private List<Float> parseTranslation(List<Property<View, Float>> list, int i3, int i4, List<String> list2, float f2) {
                        ArrayList arrayList = new ArrayList(2);
                        String str3 = list2.get(0);
                        if (list.size() == 1) {
                            parseSingleTranslation(list, i3, i4, arrayList, str3, f2);
                            return arrayList;
                        }
                        parseDoubleTranslation(i3, i4, list2, arrayList, str3, f2);
                        return arrayList;
                    }

                    @Override // com.taobao.weex.utils.FunctionParser.Mapper
                    public Map<Property<View, Float>, Float> map(String str3, List<String> list) {
                        return (list == null || list.isEmpty() || !TransformParser.wxToAndroidMap.containsKey(str3)) ? new HashMap() : convertParam(i, i2, f, TransformParser.wxToAndroidMap.get(str3), list);
                    }
                }).parse();
            }
        } catch (Exception e) {
            WXLogUtils.e("TransformParser", e);
            WXErrorCode wXErrorCode = WXErrorCode.WX_RENDER_ERR_TRANSITION;
            WXExceptionUtils.commitCriticalExceptionRT(str, wXErrorCode, "parse animation transition", wXErrorCode.getErrorMsg() + "parse transition error: " + e.getMessage(), null);
        }
        return new LinkedHashMap();
    }

    public static PropertyValuesHolder[] toHolders(Map<Property<View, Float>, Float> map) {
        PropertyValuesHolder[] propertyValuesHolderArr = new PropertyValuesHolder[map.size()];
        int i = 0;
        for (Map.Entry<Property<View, Float>, Float> entry : map.entrySet()) {
            propertyValuesHolderArr[i] = PropertyValuesHolder.ofFloat(entry.getKey(), entry.getValue().floatValue());
            i++;
        }
        return propertyValuesHolderArr;
    }

    private static Pair<Float, Float> parsePivot(List<String> list, int i, int i2, float f) {
        return new Pair<>(Float.valueOf(parsePivotX(list.get(0), i, f)), Float.valueOf(parsePivotY(list.get(1), i2, f)));
    }
}
