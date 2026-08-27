package com.alibaba.android.bindingx.core.internal;

import android.view.animation.Interpolator;
import androidx.core.view.animation.PathInterpolatorCompat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Map;
import org.json.JSONException;

/* loaded from: classes.dex */
class TimingFunctions {
    private static Object linear = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.1
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            return Double.valueOf((dDoubleValue3 * (Math.min(dDoubleValue, dDoubleValue4) / dDoubleValue4)) + dDoubleValue2);
        }
    };
    private static Object cubicBezier = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.2
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dDoubleValue5 = ((Double) arrayList.get(4)).doubleValue();
            double dDoubleValue6 = ((Double) arrayList.get(5)).doubleValue();
            double dDoubleValue7 = ((Double) arrayList.get(6)).doubleValue();
            double dDoubleValue8 = ((Double) arrayList.get(7)).doubleValue();
            if (Math.min(dDoubleValue, dDoubleValue4) != dDoubleValue4) {
                float f = (float) dDoubleValue5;
                float f2 = (float) dDoubleValue6;
                float f3 = (float) dDoubleValue7;
                float f4 = (float) dDoubleValue8;
                BezierInterpolatorWrapper bezierInterpolatorWrapperIsCacheHit = TimingFunctions.isCacheHit(f, f2, f3, f4);
                if (bezierInterpolatorWrapperIsCacheHit == null) {
                    bezierInterpolatorWrapperIsCacheHit = new BezierInterpolatorWrapper(f, f2, f3, f4);
                    TimingFunctions.cache.add(bezierInterpolatorWrapperIsCacheHit);
                }
                return Double.valueOf((dDoubleValue3 * bezierInterpolatorWrapperIsCacheHit.getInterpolation((float) (r0 / dDoubleValue4))) + dDoubleValue2);
            }
            return Double.valueOf(dDoubleValue2 + dDoubleValue3);
        }
    };
    private static final InnerCache<BezierInterpolatorWrapper> cache = new InnerCache<>(4);
    private static Object easeInQuad = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.3
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = Math.min(dDoubleValue, dDoubleValue4) / dDoubleValue4;
            return Double.valueOf((dDoubleValue3 * dMin * dMin) + dDoubleValue2);
        }
    };
    private static Object easeOutQuad = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.4
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = Math.min(dDoubleValue, dDoubleValue4) / dDoubleValue4;
            return Double.valueOf(((-dDoubleValue3) * dMin * (dMin - 2.0d)) + dDoubleValue2);
        }
    };
    private static Object easeInOutQuad = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.5
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = Math.min(dDoubleValue, dDoubleValue4) / (dDoubleValue4 / 2.0d);
            if (dMin < 1.0d) {
                return Double.valueOf(((dDoubleValue3 / 2.0d) * dMin * dMin) + dDoubleValue2);
            }
            double d = dMin - 1.0d;
            return Double.valueOf((((-dDoubleValue3) / 2.0d) * ((d * (d - 2.0d)) - 1.0d)) + dDoubleValue2);
        }
    };
    private static Object easeInCubic = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.6
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = Math.min(dDoubleValue, dDoubleValue4) / dDoubleValue4;
            return Double.valueOf((dDoubleValue3 * dMin * dMin * dMin) + dDoubleValue2);
        }
    };
    private static Object easeOutCubic = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.7
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = (Math.min(dDoubleValue, dDoubleValue4) / dDoubleValue4) - 1.0d;
            return Double.valueOf((dDoubleValue3 * ((dMin * dMin * dMin) + 1.0d)) + dDoubleValue2);
        }
    };
    private static Object easeInOutCubic = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.8
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = Math.min(dDoubleValue, dDoubleValue4) / (dDoubleValue4 / 2.0d);
            if (dMin < 1.0d) {
                return Double.valueOf(((dDoubleValue3 / 2.0d) * dMin * dMin * dMin) + dDoubleValue2);
            }
            double d = dMin - 2.0d;
            return Double.valueOf(((dDoubleValue3 / 2.0d) * ((d * d * d) + 2.0d)) + dDoubleValue2);
        }
    };
    private static Object easeInQuart = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.9
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = Math.min(dDoubleValue, dDoubleValue4) / dDoubleValue4;
            return Double.valueOf((dDoubleValue3 * dMin * dMin * dMin * dMin) + dDoubleValue2);
        }
    };
    private static Object easeOutQuart = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.10
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = (Math.min(dDoubleValue, dDoubleValue4) / dDoubleValue4) - 1.0d;
            return Double.valueOf(((-dDoubleValue3) * ((((dMin * dMin) * dMin) * dMin) - 1.0d)) + dDoubleValue2);
        }
    };
    private static Object easeInOutQuart = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.11
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = Math.min(dDoubleValue, dDoubleValue4) / (dDoubleValue4 / 2.0d);
            if (dMin < 1.0d) {
                return Double.valueOf(((dDoubleValue3 / 2.0d) * dMin * dMin * dMin * dMin) + dDoubleValue2);
            }
            double d = dMin - 2.0d;
            return Double.valueOf((((-dDoubleValue3) / 2.0d) * ((((d * d) * d) * d) - 2.0d)) + dDoubleValue2);
        }
    };
    private static Object easeInQuint = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.12
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = Math.min(dDoubleValue, dDoubleValue4) / dDoubleValue4;
            return Double.valueOf((dDoubleValue3 * dMin * dMin * dMin * dMin * dMin) + dDoubleValue2);
        }
    };
    private static Object easeOutQuint = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.13
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = (Math.min(dDoubleValue, dDoubleValue4) / dDoubleValue4) - 1.0d;
            return Double.valueOf((dDoubleValue3 * ((dMin * dMin * dMin * dMin * dMin) + 1.0d)) + dDoubleValue2);
        }
    };
    private static Object easeInOutQuint = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.14
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = Math.min(dDoubleValue, dDoubleValue4) / (dDoubleValue4 / 2.0d);
            if (dMin < 1.0d) {
                return Double.valueOf(((dDoubleValue3 / 2.0d) * dMin * dMin * dMin * dMin * dMin) + dDoubleValue2);
            }
            double d = dMin - 2.0d;
            return Double.valueOf(((dDoubleValue3 / 2.0d) * ((d * d * d * d * d) + 2.0d)) + dDoubleValue2);
        }
    };
    private static Object easeInSine = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.15
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            return Double.valueOf(((-dDoubleValue3) * Math.cos((Math.min(dDoubleValue, dDoubleValue4) / dDoubleValue4) * 1.5707963267948966d)) + dDoubleValue3 + dDoubleValue2);
        }
    };
    private static Object easeOutSine = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.16
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            return Double.valueOf((dDoubleValue3 * Math.sin((Math.min(dDoubleValue, dDoubleValue4) / dDoubleValue4) * 1.5707963267948966d)) + dDoubleValue2);
        }
    };
    private static Object easeInOutSine = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.17
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            return Double.valueOf((((-dDoubleValue3) / 2.0d) * (Math.cos((Math.min(dDoubleValue, dDoubleValue4) * 3.141592653589793d) / dDoubleValue4) - 1.0d)) + dDoubleValue2);
        }
    };
    private static Object easeInExpo = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.18
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = Math.min(dDoubleValue, dDoubleValue4);
            if (dMin != 0.0d) {
                dDoubleValue2 += dDoubleValue3 * Math.pow(2.0d, ((dMin / dDoubleValue4) - 1.0d) * 10.0d);
            }
            return Double.valueOf(dDoubleValue2);
        }
    };
    private static Object easeOutExpo = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.19
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = Math.min(dDoubleValue, dDoubleValue4);
            return Double.valueOf(dMin == dDoubleValue4 ? dDoubleValue2 + dDoubleValue3 : dDoubleValue2 + (dDoubleValue3 * ((-Math.pow(2.0d, (dMin * (-10.0d)) / dDoubleValue4)) + 1.0d)));
        }
    };
    private static Object easeInOutExpo = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.20
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            Double d = (Double) arrayList.get(1);
            double dDoubleValue2 = d.doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = Math.min(dDoubleValue, dDoubleValue4);
            if (dMin == 0.0d) {
                return d;
            }
            if (dMin == dDoubleValue4) {
                return Double.valueOf(dDoubleValue2 + dDoubleValue3);
            }
            double d2 = dMin / (dDoubleValue4 / 2.0d);
            if (d2 < 1.0d) {
                return Double.valueOf(((dDoubleValue3 / 2.0d) * Math.pow(2.0d, (d2 - 1.0d) * 10.0d)) + dDoubleValue2);
            }
            return Double.valueOf(((dDoubleValue3 / 2.0d) * ((-Math.pow(2.0d, (d2 - 1.0d) * (-10.0d))) + 2.0d)) + dDoubleValue2);
        }
    };
    private static Object easeInCirc = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.21
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = Math.min(dDoubleValue, dDoubleValue4) / dDoubleValue4;
            return Double.valueOf(((-dDoubleValue3) * (Math.sqrt(1.0d - (dMin * dMin)) - 1.0d)) + dDoubleValue2);
        }
    };
    private static Object easeOutCirc = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.22
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = (Math.min(dDoubleValue, dDoubleValue4) / dDoubleValue4) - 1.0d;
            return Double.valueOf((dDoubleValue3 * Math.sqrt(1.0d - (dMin * dMin))) + dDoubleValue2);
        }
    };
    private static Object easeInOutCirc = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.23
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = Math.min(dDoubleValue, dDoubleValue4) / (dDoubleValue4 / 2.0d);
            if (dMin < 1.0d) {
                return Double.valueOf((((-dDoubleValue3) / 2.0d) * (Math.sqrt(1.0d - (dMin * dMin)) - 1.0d)) + dDoubleValue2);
            }
            double d = dMin - 2.0d;
            return Double.valueOf(((dDoubleValue3 / 2.0d) * (Math.sqrt(1.0d - (d * d)) + 1.0d)) + dDoubleValue2);
        }
    };
    private static Object easeInElastic = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.24
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            Double d = (Double) arrayList.get(1);
            double dDoubleValue2 = d.doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = Math.min(dDoubleValue, dDoubleValue4);
            if (dMin == 0.0d) {
                return d;
            }
            double d2 = dMin / dDoubleValue4;
            if (d2 == 1.0d) {
                return Double.valueOf(dDoubleValue2 + dDoubleValue3);
            }
            double d3 = 0.3d * dDoubleValue4;
            double d4 = d2 - 1.0d;
            return Double.valueOf((-(dDoubleValue3 * Math.pow(2.0d, 10.0d * d4) * Math.sin((((d4 * dDoubleValue4) - (dDoubleValue3 < Math.abs(dDoubleValue3) ? d3 / 4.0d : (d3 / 6.283185307179586d) * Math.asin(dDoubleValue3 / dDoubleValue3))) * 6.283185307179586d) / d3))) + dDoubleValue2);
        }
    };
    private static Object easeOutElastic = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.25
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            Double d = (Double) arrayList.get(1);
            double dDoubleValue2 = d.doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = Math.min(dDoubleValue, dDoubleValue4);
            if (dMin == 0.0d) {
                return d;
            }
            double d2 = dMin / dDoubleValue4;
            if (d2 == 1.0d) {
                return Double.valueOf(dDoubleValue2 + dDoubleValue3);
            }
            double d3 = 0.3d * dDoubleValue4;
            return Double.valueOf((Math.pow(2.0d, (-10.0d) * d2) * dDoubleValue3 * Math.sin((((d2 * dDoubleValue4) - (dDoubleValue3 < Math.abs(dDoubleValue3) ? d3 / 4.0d : (d3 / 6.283185307179586d) * Math.asin(dDoubleValue3 / dDoubleValue3))) * 6.283185307179586d) / d3)) + dDoubleValue3 + dDoubleValue2);
        }
    };
    private static Object easeInOutElastic = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.26
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            Double d = (Double) arrayList.get(1);
            double dDoubleValue2 = d.doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = Math.min(dDoubleValue, dDoubleValue4);
            if (dMin == 0.0d) {
                return d;
            }
            double d2 = dMin / (dDoubleValue4 / 2.0d);
            if (d2 == 2.0d) {
                return Double.valueOf(dDoubleValue2 + dDoubleValue3);
            }
            double d3 = 0.44999999999999996d * dDoubleValue4;
            double dAsin = dDoubleValue3 < Math.abs(dDoubleValue3) ? d3 / 4.0d : (d3 / 6.283185307179586d) * Math.asin(dDoubleValue3 / dDoubleValue3);
            if (d2 < 1.0d) {
                double d4 = d2 - 1.0d;
                return Double.valueOf((dDoubleValue3 * Math.pow(2.0d, d4 * 10.0d) * Math.sin((((d4 * dDoubleValue4) - dAsin) * 6.283185307179586d) / d3) * (-0.5d)) + dDoubleValue2);
            }
            double d5 = d2 - 1.0d;
            return Double.valueOf((Math.pow(2.0d, (-10.0d) * d5) * dDoubleValue3 * Math.sin((((d5 * dDoubleValue4) - dAsin) * 6.283185307179586d) / d3) * 0.5d) + dDoubleValue3 + dDoubleValue2);
        }
    };
    private static Object easeInBack = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.27
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = Math.min(dDoubleValue, dDoubleValue4) / dDoubleValue4;
            return Double.valueOf((dDoubleValue3 * dMin * dMin * ((2.70158d * dMin) - 1.70158d)) + dDoubleValue2);
        }
    };
    private static Object easeOutBack = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.28
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = (Math.min(dDoubleValue, dDoubleValue4) / dDoubleValue4) - 1.0d;
            return Double.valueOf((dDoubleValue3 * ((dMin * dMin * ((2.70158d * dMin) + 1.70158d)) + 1.0d)) + dDoubleValue2);
        }
    };
    private static Object easeInOutBack = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.29
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = Math.min(dDoubleValue, dDoubleValue4) / (dDoubleValue4 / 2.0d);
            if (dMin < 1.0d) {
                return Double.valueOf(((dDoubleValue3 / 2.0d) * dMin * dMin * ((3.5949095d * dMin) - 2.5949095d)) + dDoubleValue2);
            }
            double d = dMin - 2.0d;
            return Double.valueOf(((dDoubleValue3 / 2.0d) * ((d * d * ((3.5949095d * d) + 2.5949095d)) + 2.0d)) + dDoubleValue2);
        }
    };
    private static Object easeInBounce = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.30
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            return Double.valueOf(TimingFunctions.easeInBounce(Math.min(dDoubleValue, dDoubleValue4), dDoubleValue2, dDoubleValue3, dDoubleValue4));
        }
    };
    private static Object easeOutBounce = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.31
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            return Double.valueOf(TimingFunctions.easeOutBounce(Math.min(dDoubleValue, dDoubleValue4), dDoubleValue2, dDoubleValue3, dDoubleValue4));
        }
    };
    private static Object easeInOutBounce = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.TimingFunctions.32
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            double dDoubleValue2 = ((Double) arrayList.get(1)).doubleValue();
            double dDoubleValue3 = ((Double) arrayList.get(2)).doubleValue();
            double dDoubleValue4 = ((Double) arrayList.get(3)).doubleValue();
            double dMin = Math.min(dDoubleValue, dDoubleValue4);
            return dMin < dDoubleValue4 / 2.0d ? Double.valueOf((TimingFunctions.easeInBounce(dMin * 2.0d, 0.0d, dDoubleValue3, dDoubleValue4) * 0.5d) + dDoubleValue2) : Double.valueOf((TimingFunctions.easeOutBounce((dMin * 2.0d) - dDoubleValue4, 0.0d, dDoubleValue3, dDoubleValue4) * 0.5d) + (dDoubleValue3 * 0.5d) + dDoubleValue2);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public static double easeOutBounce(double d, double d2, double d3, double d4) {
        double d5;
        double d6;
        double d7;
        double d8 = d / d4;
        if (d8 < 0.36363636363636365d) {
            d7 = 7.5625d * d8 * d8;
        } else {
            if (d8 < 0.7272727272727273d) {
                double d9 = d8 - 0.5454545454545454d;
                d5 = 7.5625d * d9 * d9;
                d6 = 0.75d;
            } else if (d8 < 0.9090909090909091d) {
                double d10 = d8 - 0.8181818181818182d;
                d5 = 7.5625d * d10 * d10;
                d6 = 0.9375d;
            } else {
                double d11 = d8 - 0.9545454545454546d;
                d5 = 7.5625d * d11 * d11;
                d6 = 0.984375d;
            }
            d7 = d5 + d6;
        }
        return (d3 * d7) + d2;
    }

    private TimingFunctions() {
    }

    static void applyToScope(Map<String, Object> map) {
        map.put("linear", linear);
        map.put("easeInQuad", easeInQuad);
        map.put("easeOutQuad", easeOutQuad);
        map.put("easeInOutQuad", easeInOutQuad);
        map.put("easeInCubic", easeInCubic);
        map.put("easeOutCubic", easeOutCubic);
        map.put("easeInOutCubic", easeInOutCubic);
        map.put("easeInQuart", easeInQuart);
        map.put("easeOutQuart", easeOutQuart);
        map.put("easeInOutQuart", easeInOutQuart);
        map.put("easeInQuint", easeInQuint);
        map.put("easeOutQuint", easeOutQuint);
        map.put("easeInOutQuint", easeInOutQuint);
        map.put("easeInSine", easeInSine);
        map.put("easeOutSine", easeOutSine);
        map.put("easeInOutSine", easeInOutSine);
        map.put("easeInExpo", easeInExpo);
        map.put("easeOutExpo", easeOutExpo);
        map.put("easeInOutExpo", easeInOutExpo);
        map.put("easeInCirc", easeInCirc);
        map.put("easeOutCirc", easeOutCirc);
        map.put("easeInOutCirc", easeInOutCirc);
        map.put("easeInElastic", easeInElastic);
        map.put("easeOutElastic", easeOutElastic);
        map.put("easeInOutElastic", easeInOutElastic);
        map.put("easeInBack", easeInBack);
        map.put("easeOutBack", easeOutBack);
        map.put("easeInOutBack", easeInOutBack);
        map.put("easeInBounce", easeInBounce);
        map.put("easeOutBounce", easeOutBounce);
        map.put("easeInOutBounce", easeInOutBounce);
        map.put("cubicBezier", cubicBezier);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static BezierInterpolatorWrapper isCacheHit(float f, float f2, float f3, float f4) {
        for (BezierInterpolatorWrapper bezierInterpolatorWrapper : cache.getAll()) {
            if (Float.compare(bezierInterpolatorWrapper.x1, f) == 0 && Float.compare(bezierInterpolatorWrapper.x2, f3) == 0 && Float.compare(bezierInterpolatorWrapper.y1, f2) == 0 && Float.compare(bezierInterpolatorWrapper.y2, f4) == 0) {
                return bezierInterpolatorWrapper;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double easeInBounce(double d, double d2, double d3, double d4) {
        return (d3 - easeOutBounce(d4 - d, 0.0d, d3, d4)) + d2;
    }

    private static class InnerCache<T> {
        private final ArrayDeque<T> deque;

        InnerCache(int i) {
            this.deque = new ArrayDeque<>(i);
        }

        void add(T t) {
            if (this.deque.size() >= 4) {
                this.deque.removeFirst();
                this.deque.addLast(t);
            } else {
                this.deque.addLast(t);
            }
        }

        Deque<T> getAll() {
            return this.deque;
        }
    }

    private static class BezierInterpolatorWrapper implements Interpolator {
        private Interpolator mInnerInterpolator;
        float x1;
        float x2;
        float y1;
        float y2;

        BezierInterpolatorWrapper(float f, float f2, float f3, float f4) {
            this.x1 = f;
            this.y1 = f2;
            this.x2 = f3;
            this.y2 = f4;
            this.mInnerInterpolator = PathInterpolatorCompat.create(f, f2, f3, f4);
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            return this.mInnerInterpolator.getInterpolation(f);
        }
    }
}
