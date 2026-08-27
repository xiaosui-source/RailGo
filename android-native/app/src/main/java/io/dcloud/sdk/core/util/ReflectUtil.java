package io.dcloud.sdk.core.util;

import com.taobao.weex.common.RenderTypes;
import io.dcloud.p.b3;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class ReflectUtil {
    public static Object invokeField(String str, String str2) throws NoSuchFieldException, ClassNotFoundException, SecurityException {
        try {
            Class<?> cls = Class.forName(str);
            Field declaredField = cls.getDeclaredField(str2);
            declaredField.setAccessible(true);
            return declaredField.get(cls);
        } catch (Exception unused) {
            return null;
        }
    }

    public static Object invokeMethod(Object obj, String str, Class<?>[] clsArr, Object... objArr) {
        if (obj == null) {
            return null;
        }
        try {
            Method method = obj.getClass().getMethod(str, clsArr);
            method.setAccessible(true);
            if (objArr == null || objArr.length == 0) {
                objArr = null;
            }
            return method.invoke(obj, objArr);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static Object newInstance(String str, Class[] clsArr, Object[] objArr) {
        try {
            return Class.forName(str).getConstructor(clsArr).newInstance(objArr);
        } catch (Throwable th) {
            b3.a(RenderTypes.RENDER_TYPE_NATIVE, th.toString());
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.Object invokeMethod(java.lang.String r2, java.lang.String r3, java.lang.Object r4, java.lang.Class[] r5, java.lang.Object[] r6) throws java.lang.IllegalAccessException, java.lang.NoSuchMethodException, java.lang.SecurityException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            r0 = 0
            java.lang.Class r2 = java.lang.Class.forName(r2)     // Catch: java.lang.Exception -> L16 java.lang.NoSuchMethodException -> L1c java.lang.ClassNotFoundException -> L1f
            java.lang.reflect.Method r2 = r2.getMethod(r3, r5)     // Catch: java.lang.Exception -> L16 java.lang.NoSuchMethodException -> L1c java.lang.ClassNotFoundException -> L1f
            if (r2 == 0) goto L14
            r5 = 1
            r2.setAccessible(r5)     // Catch: java.lang.Exception -> L16 java.lang.NoSuchMethodException -> L1c java.lang.ClassNotFoundException -> L1f
            java.lang.Object r2 = r2.invoke(r4, r6)     // Catch: java.lang.Exception -> L16 java.lang.NoSuchMethodException -> L1c java.lang.ClassNotFoundException -> L1f
            goto L24
        L14:
            r2 = r0
            goto L24
        L16:
            r2 = move-exception
            java.lang.String r2 = r2.getMessage()
            goto L21
        L1c:
            java.lang.String r2 = "NoSuchMethodException"
            goto L21
        L1f:
            java.lang.String r2 = "ClassNotFoundException"
        L21:
            r1 = r0
            r0 = r2
            r2 = r1
        L24:
            if (r0 == 0) goto L2b
            java.lang.String r4 = "getJsContent"
            r4.equals(r3)
        L2b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.sdk.core.util.ReflectUtil.invokeMethod(java.lang.String, java.lang.String, java.lang.Object, java.lang.Class[], java.lang.Object[]):java.lang.Object");
    }

    public static Object invokeMethod(String str, String str2, Object obj) {
        return invokeMethod(str, str2, obj, new Class[0], new Object[0]);
    }
}
