package com.taobao.weex.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXReflectionUtils {
    public static Field getDeclaredField(Object obj, String str) {
        for (Class<?> superclass = obj.getClass(); superclass != Object.class; superclass = superclass.getSuperclass()) {
            try {
                return superclass.getDeclaredField(str);
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static Object parseArgument(Type type, Object obj) {
        if (obj == null || (obj.getClass() != type && (!(type instanceof Class) || !((Class) type).isAssignableFrom(obj.getClass())))) {
            if (type != String.class) {
                Class<?> cls = Integer.TYPE;
                if (type != cls) {
                    Class<?> cls2 = Long.TYPE;
                    if (type != cls2) {
                        Class<?> cls3 = Double.TYPE;
                        if (type != cls3) {
                            Class<?> cls4 = Float.TYPE;
                            if (type == cls4) {
                                if (!obj.getClass().isAssignableFrom(cls4)) {
                                    return Float.valueOf(WXUtils.getFloat(obj));
                                }
                            } else if ((type != JSONArray.class || obj == null || obj.getClass() != JSONArray.class) && (type != JSONObject.class || obj == null || obj.getClass() != JSONObject.class)) {
                                return JSON.parseObject(obj instanceof String ? (String) obj : JSON.toJSONString(obj), type, new Feature[0]);
                            }
                        } else if (!obj.getClass().isAssignableFrom(cls3)) {
                            return Double.valueOf(WXUtils.getDouble(obj));
                        }
                    } else if (!obj.getClass().isAssignableFrom(cls2)) {
                        return Long.valueOf(WXUtils.getLong(obj));
                    }
                } else if (!obj.getClass().isAssignableFrom(cls)) {
                    return Integer.valueOf(WXUtils.getInt(obj));
                }
            } else if (!(obj instanceof String)) {
                return JSON.toJSONString(obj);
            }
        }
        return obj;
    }

    public static void setProperty(Object obj, Field field, Object obj2) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        if (obj == null || field == null) {
            return;
        }
        try {
            field.setAccessible(true);
            field.set(obj, obj2);
        } catch (Exception unused) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x005e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void setValue(java.lang.Object r3, java.lang.String r4, java.lang.Object r5) {
        /*
            if (r3 == 0) goto Lad
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            if (r0 == 0) goto La
            goto Lad
        La:
            java.lang.reflect.Field r4 = getDeclaredField(r3, r4)     // Catch: java.lang.Exception -> Lad
            boolean r0 = r5 instanceof java.math.BigDecimal     // Catch: java.lang.Exception -> Lad
            if (r0 != 0) goto L1a
            boolean r0 = r5 instanceof java.lang.Number     // Catch: java.lang.Exception -> Lad
            if (r0 != 0) goto L1a
            boolean r0 = r5 instanceof java.lang.String     // Catch: java.lang.Exception -> Lad
            if (r0 == 0) goto L5e
        L1a:
            java.lang.Class r0 = r4.getType()     // Catch: java.lang.Exception -> Lad
            java.lang.Class<java.lang.Float> r1 = java.lang.Float.class
            if (r0 == r1) goto L84
            java.lang.Class r0 = r4.getType()     // Catch: java.lang.Exception -> Lad
            java.lang.Class r1 = java.lang.Float.TYPE     // Catch: java.lang.Exception -> Lad
            if (r0 != r1) goto L2b
            goto L84
        L2b:
            java.lang.Class r0 = r4.getType()     // Catch: java.lang.Exception -> Lad
            java.lang.Class<java.lang.Double> r1 = java.lang.Double.class
            if (r0 == r1) goto L77
            java.lang.Class r0 = r4.getType()     // Catch: java.lang.Exception -> Lad
            java.lang.Class r1 = java.lang.Double.TYPE     // Catch: java.lang.Exception -> Lad
            if (r0 != r1) goto L3c
            goto L77
        L3c:
            java.lang.Class r0 = r4.getType()     // Catch: java.lang.Exception -> Lad
            java.lang.Class<java.lang.Integer> r1 = java.lang.Integer.class
            if (r0 == r1) goto L69
            java.lang.Class r0 = r4.getType()     // Catch: java.lang.Exception -> Lad
            java.lang.Class r1 = java.lang.Integer.TYPE     // Catch: java.lang.Exception -> Lad
            if (r0 != r1) goto L4d
            goto L69
        L4d:
            java.lang.Class r0 = r4.getType()     // Catch: java.lang.Exception -> Lad
            java.lang.Class<java.lang.Boolean> r1 = java.lang.Boolean.class
            if (r0 == r1) goto L60
            java.lang.Class r0 = r4.getType()     // Catch: java.lang.Exception -> Lad
            java.lang.Class r1 = java.lang.Boolean.TYPE     // Catch: java.lang.Exception -> Lad
            if (r0 != r1) goto L5e
            goto L60
        L5e:
            r0 = r5
            goto L90
        L60:
            java.lang.String r0 = r5.toString()     // Catch: java.lang.Exception -> Lad
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch: java.lang.Exception -> Lad
            goto L90
        L69:
            java.lang.String r0 = r5.toString()     // Catch: java.lang.Exception -> Lad
            double r0 = java.lang.Double.parseDouble(r0)     // Catch: java.lang.Exception -> Lad
            int r0 = (int) r0     // Catch: java.lang.Exception -> Lad
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch: java.lang.Exception -> Lad
            goto L90
        L77:
            java.lang.String r0 = r5.toString()     // Catch: java.lang.Exception -> Lad
            double r0 = java.lang.Double.parseDouble(r0)     // Catch: java.lang.Exception -> Lad
            java.lang.Double r0 = java.lang.Double.valueOf(r0)     // Catch: java.lang.Exception -> Lad
            goto L90
        L84:
            java.lang.String r0 = r5.toString()     // Catch: java.lang.Exception -> Lad
            float r0 = java.lang.Float.parseFloat(r0)     // Catch: java.lang.Exception -> Lad
            java.lang.Float r0 = java.lang.Float.valueOf(r0)     // Catch: java.lang.Exception -> Lad
        L90:
            java.lang.Class r1 = r4.getType()     // Catch: java.lang.Exception -> Lad
            java.lang.Class r2 = java.lang.Boolean.TYPE     // Catch: java.lang.Exception -> Lad
            if (r1 == r2) goto La0
            java.lang.Class r1 = r4.getType()     // Catch: java.lang.Exception -> Lad
            java.lang.Class<java.lang.Boolean> r2 = java.lang.Boolean.class
            if (r1 != r2) goto Laa
        La0:
            if (r5 == 0) goto Laa
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Exception -> Lad
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r5)     // Catch: java.lang.Exception -> Lad
        Laa:
            setProperty(r3, r4, r0)     // Catch: java.lang.Exception -> Lad
        Lad:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.utils.WXReflectionUtils.setValue(java.lang.Object, java.lang.String, java.lang.Object):void");
    }
}
