package io.dcloud.p;

import android.util.Log;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.constant.AbsoluteConst;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
class i3 {
    w2 a;
    Class b;
    Object c;
    String d;

    public i3(IWebview iWebview, w2 w2Var, Class cls, String str, JSONArray jSONArray) {
        this.c = null;
        this.a = w2Var;
        this.b = cls;
        this.d = str;
        this.c = a(iWebview, w2Var, cls, jSONArray);
    }

    public static Object a(IWebview iWebview, w2 w2Var, Class cls, JSONArray jSONArray) throws NoSuchMethodException, SecurityException, ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        Constructor<?> constructor;
        if (jSONArray == null) {
            return cls.newInstance();
        }
        Object[] objArrA = a(iWebview, w2Var, jSONArray);
        int i = 0;
        Class<?>[] clsArr = (Class[]) objArrA[0];
        Object[] objArr = (Object[]) objArrA[1];
        try {
            constructor = cls.getConstructor(clsArr);
        } catch (NoSuchMethodException unused) {
            Constructor<?>[] constructors = cls.getConstructors();
            while (true) {
                if (i >= constructors.length) {
                    constructor = null;
                    break;
                }
                Class<?>[] parameterTypes = constructors[i].getParameterTypes();
                if (parameterTypes.length == clsArr.length && a(parameterTypes, clsArr, objArr)) {
                    constructor = constructors[i];
                    break;
                }
                i++;
            }
        }
        if (constructor != null) {
            return constructor.newInstance(objArr);
        }
        return null;
    }

    public static void b(IWebview iWebview, w2 w2Var, Class cls, Object obj, String str, JSONArray jSONArray) {
        Object[] objArrA = a(iWebview, w2Var, jSONArray);
        try {
            Field field = cls.getField(str);
            if (field != null) {
                field.setAccessible(true);
                field.set(obj, ((Object[]) objArrA[1])[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static boolean c(Class cls, Class[] clsArr, int i) {
        boolean zC = c(cls, clsArr[i]);
        if (zC) {
            clsArr[i] = cls;
        }
        return zC;
    }

    static boolean d(Class cls) {
        return Character.TYPE.equals(cls) || Character.class.equals(cls);
    }

    static boolean e(Class cls) {
        return cls.equals(Byte.TYPE) || cls.equals(Byte.class) || cls.equals(Integer.TYPE) || cls.equals(Integer.class) || cls.equals(Short.TYPE) || cls.equals(Short.class) || cls.equals(Float.TYPE) || cls.equals(Float.class) || cls.equals(Long.TYPE) || cls.equals(Long.class) || cls.equals(Double.TYPE) || cls.equals(Double.class);
    }

    public boolean equals(Object obj) {
        return super.equals(obj) || this.c.equals(obj);
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    static class a {
        private int[] a;

        a(int i) {
            this.a = null;
            this.a = new int[i];
        }

        int[] a(int i) {
            int[] iArr = this.a;
            if (i > iArr.length) {
                return null;
            }
            return Arrays.copyOfRange(iArr, iArr.length - i, iArr.length);
        }

        void a(int i, int i2) {
            int[] iArr = this.a;
            if (i > iArr.length) {
                return;
            }
            iArr[iArr.length - i] = i2;
        }
    }

    static boolean c(Class cls, Class cls2) {
        if (Number.class.isAssignableFrom(cls) && Number.class.isAssignableFrom(cls2)) {
            return true;
        }
        return e(cls) && e(cls2);
    }

    static boolean c(Class cls) {
        return Boolean.TYPE.equals(cls) || Boolean.class.equals(cls);
    }

    private static Method b(Class cls, String str, Class[] clsArr, Object[] objArr) throws SecurityException {
        int i;
        int length;
        try {
            Method[] declaredMethods = cls.getDeclaredMethods();
            while (i < declaredMethods.length) {
                Method method = declaredMethods[i];
                i = (method.getName().equals(str) && (length = method.getParameterTypes().length) == clsArr.length && (length == 0 || a(method.getParameterTypes(), clsArr, objArr))) ? 0 : i + 1;
                return method;
            }
            if (cls != Object.class) {
                return b(cls.getSuperclass(), str, clsArr, objArr);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public i3(w2 w2Var, Class cls, String str, Object obj) {
        this.a = w2Var;
        this.b = cls;
        this.d = str;
        this.c = obj;
    }

    public static Object a(Class cls, Object obj, String str) throws NoSuchFieldException, SecurityException {
        try {
            Field field = cls.getField(str);
            if (field == null) {
                return null;
            }
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static Object[] b(Class cls) throws ClassNotFoundException {
        Class<?> cls2;
        String name = cls.getName();
        Class<?> cls3 = null;
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i >= name.length()) {
                break;
            }
            char cCharAt = name.charAt(i);
            if (cCharAt == '[') {
                i2++;
            } else if (cCharAt == 'B') {
                cls3 = Byte.TYPE;
            } else if (cCharAt == 'I') {
                cls3 = Integer.TYPE;
            } else if (cCharAt == 'F') {
                cls3 = Float.TYPE;
            } else if (cCharAt == 'D') {
                cls3 = Double.TYPE;
            } else if (cCharAt == 'Z') {
                cls3 = Boolean.TYPE;
            } else if (cCharAt == 'J') {
                cls3 = Long.TYPE;
            } else if (cCharAt == 'S') {
                cls3 = Short.TYPE;
            } else if (cCharAt == 'L') {
                try {
                    cls2 = Class.forName(name.substring(i + 1));
                    cls3 = cls2;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    cls2 = Class.forName(name);
                    cls3 = cls2;
                } catch (ClassNotFoundException e2) {
                    e2.printStackTrace();
                }
            }
            i++;
        }
        return new Object[]{Integer.valueOf(i2), cls3};
    }

    String a(IWebview iWebview, w2 w2Var) throws SecurityException {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        for (Field field : this.b.getFields()) {
            j3.a(iWebview, w2Var, this.c, field, false, stringBuffer, stringBuffer2);
        }
        return stringBuffer.toString();
    }

    Object a(IWebview iWebview, String str, JSONArray jSONArray) {
        return a(iWebview, this.a, this.b, this.c, str, jSONArray);
    }

    public static final Object a(IWebview iWebview, w2 w2Var, Class cls, Object obj, String str, JSONArray jSONArray) {
        Object[] objArrA = a(iWebview, w2Var, jSONArray);
        Class[] clsArr = (Class[]) objArrA[0];
        Object[] objArr = (Object[]) objArrA[1];
        return a(obj, a(cls, str, clsArr, objArr), objArr, true);
    }

    private static final Object a(Object obj, Method method, Object[] objArr, boolean z) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            if (method.getReturnType().equals(Void.class)) {
                method.invoke(obj, objArr);
                return null;
            }
            return method.invoke(obj, objArr);
        } catch (IllegalArgumentException e) {
            if (z) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes == null || objArr == null || parameterTypes.length != objArr.length) {
                    return null;
                }
                for (int i = 0; i < parameterTypes.length; i++) {
                    Object obj2 = objArr[i];
                    if (obj2 != null) {
                        Class<?> cls = obj2.getClass();
                        Class<?> cls2 = parameterTypes[i];
                        if (cls.isAssignableFrom(cls2)) {
                            continue;
                        } else if (e(cls) && e(cls2)) {
                            objArr[i] = a(objArr[i], cls2);
                        } else {
                            throw e;
                        }
                    }
                }
                return a(obj, method, objArr, false);
            }
            throw e;
        }
    }

    static Method a(Class cls, String str, Class[] clsArr, Object[] objArr) throws NoSuchMethodException, SecurityException {
        Method method;
        boolean zEquals = "getClass".equals(str);
        Class superclass = cls;
        while (true) {
            if (!zEquals && superclass == Object.class) {
                return b(cls, str, clsArr, objArr);
            }
            try {
                method = cls.getMethod(str, clsArr);
            } catch (NoSuchMethodException unused) {
            }
            if (method != null) {
                method.setAccessible(true);
                return method;
            }
            continue;
            superclass = superclass.getSuperclass();
        }
    }

    static boolean b(Class cls, Class cls2) {
        return d(cls) && d(cls2);
    }

    static boolean b(Class cls, Class[] clsArr, int i) {
        boolean zB = b(cls, clsArr[i]);
        if (zB) {
            clsArr[i] = cls;
        }
        return zB;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean a(Class[] clsArr, Class[] clsArr2, Object[] objArr) {
        boolean z = false;
        for (int i = 0; i < clsArr.length; i++) {
            Class cls = clsArr2[i];
            Class cls2 = clsArr[i];
            z = cls == null ? !(e(cls2) || Boolean.TYPE.equals(cls2) || Boolean.class.equals(cls2) || Character.TYPE.equals(cls2) || Character.class.equals(cls2)) : cls2.isAssignableFrom(cls) || c(cls2, clsArr2, i) || a(cls2, clsArr2, i) || b(cls2, clsArr2, i) || (cls2.isArray() && cls2.isArray() && a(cls2, clsArr2, objArr, i));
            if (!z) {
                return z;
            }
        }
        return z;
    }

    static boolean a(Class cls, Class[] clsArr, Object[] objArr, int i) throws ClassNotFoundException, NumberFormatException {
        if (cls == clsArr[i]) {
            return true;
        }
        Object[] objArrB = b(cls);
        Object[] objArrB2 = b(clsArr[i]);
        int i2 = Integer.parseInt(String.valueOf(objArrB[0]));
        Class<?> cls2 = (Class) objArrB[1];
        int i3 = Integer.parseInt(String.valueOf(objArrB2[0]));
        Class cls3 = (Class) objArrB2[1];
        if (i2 == i3 && (a((Class) cls2, cls3) || b(cls2, cls3) || c(cls2, cls3) || cls3.isAssignableFrom(cls2))) {
            try {
                Object objA = a(objArr[i], cls2, i2, new a(i2));
                if (objA != null) {
                    clsArr[i] = cls;
                    objArr[i] = objA;
                    return true;
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    static Object a(Object obj, Class cls, int i, a aVar) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            Object objNewInstance = null;
            for (int i2 = 0; i2 < length; i2++) {
                try {
                    Object objA = a(Array.get(obj, i2), cls, i - 1, aVar);
                    if (objNewInstance == null) {
                        aVar.a(i, length);
                        if (i == 1) {
                            objNewInstance = Array.newInstance((Class<?>) cls, length);
                        } else {
                            objNewInstance = Array.newInstance((Class<?>) cls, aVar.a(i));
                        }
                    }
                    Array.set(objNewInstance, i2, objA);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.w("test", "i=" + i2 + ";mAw=" + i);
                }
            }
            return objNewInstance;
        }
        return a(obj, cls);
    }

    static boolean a(Class cls, Class cls2) {
        return c(cls) && c(cls2);
    }

    static boolean a(Class cls, Class[] clsArr, int i) {
        boolean zA = a(cls, clsArr[i]);
        if (zA) {
            clsArr[i] = cls;
        }
        return zA;
    }

    static Object a(Object obj, Class cls) {
        if (cls == obj.getClass()) {
            return obj;
        }
        if (cls != Byte.TYPE && cls != Byte.class) {
            if (cls != Integer.TYPE && cls != Integer.class) {
                if (cls != Boolean.TYPE && cls != Boolean.class) {
                    if (cls != Short.TYPE && cls != Short.class) {
                        if (cls != Long.TYPE && cls != Long.class) {
                            if (cls != Double.TYPE && cls != Double.class) {
                                if (cls != Float.TYPE && cls != Float.class) {
                                    if (cls != Character.TYPE && cls != Character.class) {
                                        return cls.cast(obj);
                                    }
                                    return String.valueOf(obj);
                                }
                                return Float.valueOf(Float.parseFloat(String.valueOf(obj)));
                            }
                            return Double.valueOf(Double.parseDouble(String.valueOf(obj)));
                        }
                        return Long.valueOf(Long.parseLong(String.valueOf(obj)));
                    }
                    return Short.valueOf(Short.parseShort(String.valueOf(obj)));
                }
                return Boolean.valueOf(Boolean.parseBoolean(String.valueOf(obj)));
            }
            return Integer.valueOf(Integer.parseInt(String.valueOf(obj)));
        }
        return Byte.valueOf(Byte.parseByte(String.valueOf(obj)));
    }

    static Class a(Class cls) {
        if (cls == Integer.class) {
            return Integer.TYPE;
        }
        return cls == Boolean.class ? Boolean.TYPE : cls;
    }

    static Object[] a(IWebview iWebview, w2 w2Var, Object obj) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        Object[] objArr = new Object[2];
        if (obj instanceof JSONArray) {
            JSONArray jSONArray = (JSONArray) obj;
            int length = jSONArray.length();
            Object objNewInstance = null;
            for (int i = 0; i < length; i++) {
                Object[] objArrA = a(iWebview, w2Var, jSONArray.opt(i));
                Class<?> cls = objArrA[1].getClass();
                if (i == 0) {
                    objNewInstance = Array.newInstance((Class<?>) a(objArrA[1].getClass()), length);
                }
                if (cls == Boolean.class) {
                    Array.setBoolean(objNewInstance, i, ((Boolean) objArrA[1]).booleanValue());
                } else if (cls == Byte.class) {
                    Array.setByte(objNewInstance, i, ((Byte) objArrA[1]).byteValue());
                } else if (cls == Double.class) {
                    Array.setDouble(objNewInstance, i, ((Double) objArrA[1]).doubleValue());
                } else if (cls == Float.class) {
                    Array.setFloat(objNewInstance, i, ((Float) objArrA[1]).floatValue());
                } else if (cls == Integer.class) {
                    Array.setInt(objNewInstance, i, ((Integer) objArrA[1]).intValue());
                } else if (cls == Long.class) {
                    Array.setLong(objNewInstance, i, ((Long) objArrA[1]).longValue());
                } else {
                    Array.set(objNewInstance, i, objArrA[1]);
                }
            }
            if (objNewInstance != null) {
                objArr[0] = objNewInstance.getClass();
                objArr[1] = objNewInstance;
                return objArr;
            }
        } else if (obj instanceof JSONObject) {
            JSONObject jSONObject = (JSONObject) obj;
            String strOptString = jSONObject.optString("type");
            Object objOpt = jSONObject.opt("value");
            if (strOptString.equals("object")) {
                i3 i3VarA = w2Var.a(iWebview, String.valueOf(objOpt));
                objArr[0] = i3VarA.b;
                objArr[1] = i3VarA.c;
                return objArr;
            }
            if (strOptString.equals("string")) {
                objArr[0] = String.class;
                objArr[1] = objOpt;
                return objArr;
            }
            if (strOptString.equals("number")) {
                if (objOpt instanceof Integer) {
                    objArr[0] = Integer.TYPE;
                    objArr[1] = (Integer) objOpt;
                    return objArr;
                }
                if (objOpt instanceof Double) {
                    objArr[0] = Double.class;
                    objArr[1] = (Double) objOpt;
                    return objArr;
                }
                if (objOpt instanceof Float) {
                    objArr[0] = Float.class;
                    objArr[1] = (Float) objOpt;
                    return objArr;
                }
                if (objOpt instanceof Long) {
                    objArr[0] = Long.class;
                    objArr[1] = (Long) objOpt;
                    return objArr;
                }
            } else {
                if (strOptString.equals("boolean")) {
                    objArr[0] = Boolean.class;
                    objArr[1] = (Boolean) objOpt;
                    return objArr;
                }
                if ("JSBObject".equals(jSONObject.optString("__TYPE__"))) {
                    i3 i3VarA2 = w2Var.a(iWebview, jSONObject.optString(AbsoluteConst.JSON_KEY_UUID));
                    objArr[0] = i3VarA2.b;
                    objArr[1] = i3VarA2.c;
                }
            }
        } else {
            objArr[0] = obj.getClass();
            objArr[1] = obj;
            return objArr;
        }
        return objArr;
    }

    protected static Object[] a(IWebview iWebview, w2 w2Var, JSONArray jSONArray) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        int length;
        Object[] objArr = new Object[2];
        if (jSONArray != null) {
            try {
                length = jSONArray.length();
            } catch (JSONException e) {
                e.printStackTrace();
                return objArr;
            }
        } else {
            length = 0;
        }
        Class[] clsArr = new Class[length];
        Object[] objArr2 = new Object[length];
        for (int i = 0; i < length; i++) {
            Object[] objArrA = a(iWebview, w2Var, jSONArray.get(i));
            clsArr[i] = (Class) objArrA[0];
            objArr2[i] = objArrA[1];
        }
        objArr[0] = clsArr;
        objArr[1] = objArr2;
        return objArr;
    }

    public void a() {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
    }
}
