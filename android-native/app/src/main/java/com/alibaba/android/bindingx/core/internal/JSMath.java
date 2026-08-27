package com.alibaba.android.bindingx.core.internal;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.text.TextUtils;
import com.alibaba.android.bindingx.core.PlatformManager;
import com.taobao.weex.common.Constants;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.JSUtil;
import java.util.ArrayList;
import java.util.Map;
import org.json.JSONException;

/* loaded from: classes.dex */
class JSMath {
    private static Object sin = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.1
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) {
            return Double.valueOf(Math.sin(((Double) arrayList.get(0)).doubleValue()));
        }
    };
    private static Object cos = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.2
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) {
            return Double.valueOf(Math.cos(((Double) arrayList.get(0)).doubleValue()));
        }
    };
    private static Object tan = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.3
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) {
            return Double.valueOf(Math.tan(((Double) arrayList.get(0)).doubleValue()));
        }
    };
    private static Object asin = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.4
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) {
            return Double.valueOf(Math.asin(((Double) arrayList.get(0)).doubleValue()));
        }
    };
    private static Object acos = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.5
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) {
            return Double.valueOf(Math.acos(((Double) arrayList.get(0)).doubleValue()));
        }
    };
    private static Object atan = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.6
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) {
            return Double.valueOf(Math.atan(((Double) arrayList.get(0)).doubleValue()));
        }
    };
    private static Object atan2 = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.7
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) {
            return Double.valueOf(Math.atan2(((Double) arrayList.get(0)).doubleValue(), ((Double) arrayList.get(1)).doubleValue()));
        }
    };
    private static Object pow = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.8
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) {
            return Double.valueOf(Math.pow(((Double) arrayList.get(0)).doubleValue(), ((Double) arrayList.get(1)).doubleValue()));
        }
    };
    private static Object exp = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.9
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) {
            return Double.valueOf(Math.exp(((Double) arrayList.get(0)).doubleValue()));
        }
    };
    private static Object sqrt = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.10
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) {
            return Double.valueOf(Math.sqrt(((Double) arrayList.get(0)).doubleValue()));
        }
    };
    private static Object cbrt = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.11
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) {
            return Double.valueOf(Math.cbrt(((Double) arrayList.get(0)).doubleValue()));
        }
    };
    private static Object log = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.12
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) {
            return Double.valueOf(Math.log(((Double) arrayList.get(0)).doubleValue()));
        }
    };
    private static Object abs = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.13
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) {
            return Double.valueOf(Math.abs(((Double) arrayList.get(0)).doubleValue()));
        }
    };
    private static Object sign = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.14
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) {
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            if (dDoubleValue > 0.0d) {
                return 1;
            }
            if (dDoubleValue == 0.0d) {
                return 0;
            }
            if (dDoubleValue < 0.0d) {
                return -1;
            }
            return Double.valueOf(Double.NaN);
        }
    };
    private static Object ceil = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.15
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) {
            return Double.valueOf(Math.ceil(((Double) arrayList.get(0)).doubleValue()));
        }
    };
    private static Object floor = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.16
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) {
            return Double.valueOf(Math.floor(((Double) arrayList.get(0)).doubleValue()));
        }
    };
    private static Object round = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.17
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) {
            return Long.valueOf(Math.round(((Double) arrayList.get(0)).doubleValue()));
        }
    };
    private static Object max = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.18
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) {
            if (arrayList == null) {
                return null;
            }
            if (arrayList.size() < 1) {
                return null;
            }
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            int size = arrayList.size();
            for (int i = 1; i < size; i++) {
                double dDoubleValue2 = ((Double) arrayList.get(i)).doubleValue();
                if (dDoubleValue2 > dDoubleValue) {
                    dDoubleValue = dDoubleValue2;
                }
            }
            return Double.valueOf(dDoubleValue);
        }
    };
    private static Object min = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.19
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) {
            if (arrayList == null) {
                return null;
            }
            if (arrayList.size() < 1) {
                return null;
            }
            double dDoubleValue = ((Double) arrayList.get(0)).doubleValue();
            int size = arrayList.size();
            for (int i = 1; i < size; i++) {
                double dDoubleValue2 = ((Double) arrayList.get(i)).doubleValue();
                if (dDoubleValue2 < dDoubleValue) {
                    dDoubleValue = dDoubleValue2;
                }
            }
            return Double.valueOf(dDoubleValue);
        }
    };
    private static Object PI = Double.valueOf(3.141592653589793d);
    public static Object E = Double.valueOf(2.718281828459045d);
    private static Object translate = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.20
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            if (arrayList == null || arrayList.size() < 2) {
                return null;
            }
            return arrayList;
        }
    };
    private static Object scale = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.21
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            if (arrayList == null || arrayList.size() < 2) {
                return null;
            }
            return arrayList;
        }
    };
    private static Object matrix = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.22
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            if (arrayList == null || arrayList.size() < 6) {
                return null;
            }
            return arrayList;
        }
    };
    private static Object rgb = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.23
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            if (arrayList == null || arrayList.size() < 3) {
                return null;
            }
            return Integer.valueOf(Color.rgb((int) ((Double) arrayList.get(0)).doubleValue(), (int) ((Double) arrayList.get(1)).doubleValue(), (int) ((Double) arrayList.get(2)).doubleValue()));
        }
    };
    private static Object rgba = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.24
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            if (arrayList == null || arrayList.size() < 4) {
                return null;
            }
            return Integer.valueOf(Color.argb((int) (((Double) arrayList.get(3)).doubleValue() * 255.0d), (int) ((Double) arrayList.get(0)).doubleValue(), (int) ((Double) arrayList.get(1)).doubleValue(), (int) ((Double) arrayList.get(2)).doubleValue()));
        }
    };
    private static ArgbEvaluator sArgbEvaluator = new ArgbEvaluator();
    private static Object evaluateColor = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.25
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            int color = JSMath.parseColor((String) arrayList.get(0));
            int color2 = JSMath.parseColor((String) arrayList.get(1));
            return JSMath.sArgbEvaluator.evaluate((float) Math.min(1.0d, Math.max(0.0d, ((Double) arrayList.get(2)).doubleValue())), Integer.valueOf(color), Integer.valueOf(color2));
        }
    };
    private static Object asArray = new JSFunctionInterface() { // from class: com.alibaba.android.bindingx.core.internal.JSMath.26
        @Override // com.alibaba.android.bindingx.core.internal.JSFunctionInterface
        public Object execute(ArrayList<Object> arrayList) throws JSONException, NumberFormatException {
            return arrayList;
        }
    };

    private JSMath() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int parseColor(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Unknown color");
        }
        if (str.startsWith("'") || str.startsWith(JSUtil.QUOTE)) {
            str = str.substring(1, str.length() - 1);
        }
        int color = Color.parseColor(str);
        return Color.argb(255, Color.red(color), Color.green(color), Color.blue(color));
    }

    static void applyXYToScope(Map<String, Object> map, double d, double d2, PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator) {
        map.put(Constants.Name.X, Double.valueOf(iDeviceResolutionTranslator.nativeToWeb(d, new Object[0])));
        map.put(Constants.Name.Y, Double.valueOf(iDeviceResolutionTranslator.nativeToWeb(d2, new Object[0])));
        map.put("internal_x", Double.valueOf(d));
        map.put("internal_y", Double.valueOf(d2));
    }

    static void applyOrientationValuesToScope(Map<String, Object> map, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9) {
        map.put("alpha", Double.valueOf(d));
        map.put("beta", Double.valueOf(d2));
        map.put("gamma", Double.valueOf(d3));
        map.put("dalpha", Double.valueOf(d - d4));
        map.put("dbeta", Double.valueOf(d2 - d5));
        map.put("dgamma", Double.valueOf(d3 - d6));
        map.put(Constants.Name.X, Double.valueOf(d7));
        map.put(Constants.Name.Y, Double.valueOf(d8));
        map.put("z", Double.valueOf(d9));
    }

    static void applyTimingValuesToScope(Map<String, Object> map, double d) {
        map.put("t", Double.valueOf(d));
    }

    static void applyScrollValuesToScope(Map<String, Object> map, double d, double d2, double d3, double d4, double d5, double d6, PlatformManager.IDeviceResolutionTranslator iDeviceResolutionTranslator) {
        map.put(Constants.Name.X, Double.valueOf(iDeviceResolutionTranslator.nativeToWeb(d, new Object[0])));
        map.put(Constants.Name.Y, Double.valueOf(iDeviceResolutionTranslator.nativeToWeb(d2, new Object[0])));
        map.put("dx", Double.valueOf(iDeviceResolutionTranslator.nativeToWeb(d3, new Object[0])));
        map.put(Constants.Name.DISTANCE_Y, Double.valueOf(iDeviceResolutionTranslator.nativeToWeb(d4, new Object[0])));
        map.put("tdx", Double.valueOf(iDeviceResolutionTranslator.nativeToWeb(d5, new Object[0])));
        map.put("tdy", Double.valueOf(iDeviceResolutionTranslator.nativeToWeb(d6, new Object[0])));
        map.put("internal_x", Double.valueOf(d));
        map.put("internal_y", Double.valueOf(d2));
    }

    static void applyToScope(Map<String, Object> map) {
        map.put("sin", sin);
        map.put("cos", cos);
        map.put("tan", tan);
        map.put("asin", asin);
        map.put("acos", acos);
        map.put("atan", atan);
        map.put("atan2", atan2);
        map.put("pow", pow);
        map.put("exp", exp);
        map.put("sqrt", sqrt);
        map.put("cbrt", cbrt);
        map.put("log", log);
        map.put("abs", abs);
        map.put("sign", sign);
        map.put("ceil", ceil);
        map.put("floor", floor);
        map.put(AbsoluteConst.JSON_KEY_ROUND, round);
        map.put("max", max);
        map.put(Constants.Name.MIN, min);
        map.put("PI", PI);
        map.put("E", E);
        map.put("translate", translate);
        map.put("scale", scale);
        map.put("matrix", matrix);
        map.put("rgb", rgb);
        map.put("rgba", rgba);
        map.put("evaluateColor", evaluateColor);
        map.put("asArray", asArray);
    }
}
