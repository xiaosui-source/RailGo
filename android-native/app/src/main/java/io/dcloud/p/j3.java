package io.dcloud.p;

import com.taobao.weex.el.parse.Operators;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.StringUtil;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class j3 {
    static HashMap a = new HashMap(2);
    static HashMap b = new HashMap(2);
    private static String c = "cache/invCache/";
    private static String d = "cache/invCache_temp/";

    public static Class a(String str) {
        Class<?> cls = (Class) a.get(str);
        if (cls != null) {
            return cls;
        }
        try {
            cls = Class.forName(str);
            a.put(str, cls);
            return cls;
        } catch (ClassNotFoundException unused) {
            return cls;
        }
    }

    public static String b(String str) {
        return c(a(str));
    }

    public static String c(Class cls) {
        String str = (String) b.get(cls);
        if (str != null) {
            return str.toString();
        }
        StringBuffer stringBuffer = new StringBuffer(Operators.ARRAY_START_STR);
        stringBuffer.append(JSUtil.QUOTE).append(cls.getName()).append("\",");
        for (Class superclass = cls.getSuperclass(); superclass != null; superclass = superclass.getSuperclass()) {
            stringBuffer.append(JSUtil.QUOTE).append(superclass.getName()).append("\",");
            if (superclass == Object.class) {
                break;
            }
        }
        a(stringBuffer);
        stringBuffer.append(Operators.ARRAY_END_STR);
        String string = stringBuffer.toString();
        b.put(cls, string);
        return Deprecated_JSUtil.wrapJsVar(string, false);
    }

    public static String b(Class cls) {
        return cls.getName();
    }

    public static void a(StringBuffer stringBuffer, ArrayList arrayList, StringBuffer stringBuffer2, ArrayList arrayList2, Class cls) throws SecurityException {
        for (Method method : cls.getMethods()) {
            String name = method.getName();
            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers)) {
                if (Modifier.isStatic(modifiers) && !arrayList2.contains(name) && !name.contains(Operators.SUB) && !"toString".equals(name) && !"valueOf".equals(name) && !"propertyIsEnumerable".equals(name) && !"hasOwnProperty".equals(name) && !"isPrototypeOf".equals(name) && !"constructor".equals(name) && !"toLocaleString".equals(name)) {
                    arrayList2.add(name);
                    stringBuffer2.append(JSUtil.QUOTE + name + JSUtil.QUOTE);
                    stringBuffer2.append(",");
                }
                if (!arrayList.contains(name) && !name.contains(Operators.SUB)) {
                    arrayList.add(name);
                    stringBuffer.append(JSUtil.QUOTE + name + JSUtil.QUOTE);
                    stringBuffer.append(",");
                }
            }
        }
    }

    public static void a(IWebview iWebview, w2 w2Var, Object obj, Field field, boolean z, StringBuffer stringBuffer, StringBuffer stringBuffer2) {
        String name = field.getName();
        int modifiers = field.getModifiers();
        if (!(z && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) && (z || Modifier.isStatic(modifiers) || !Modifier.isPublic(modifiers))) {
            return;
        }
        try {
            a(iWebview, w2Var, field.getType(), field.get(obj), name, z, stringBuffer, stringBuffer2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void a(IWebview iWebview, w2 w2Var, Class cls, Object obj, String str, boolean z, StringBuffer stringBuffer, StringBuffer stringBuffer2) {
        if (z) {
            stringBuffer.append(JSUtil.QUOTE + str + JSUtil.QUOTE);
            a(iWebview, w2Var, obj, cls, stringBuffer2);
            stringBuffer.append(",");
            stringBuffer2.append(",");
            return;
        }
        stringBuffer.append("self.").append(str).append("=");
        a(iWebview, w2Var, obj, cls, stringBuffer2);
        stringBuffer.append(stringBuffer2.toString()).append(";");
        stringBuffer2.delete(0, stringBuffer2.length());
    }

    public static void a(IWebview iWebview, w2 w2Var, Object obj, Class cls, StringBuffer stringBuffer) {
        if (obj == null) {
            stringBuffer.append("null");
            return;
        }
        if (a(cls)) {
            stringBuffer.append(String.valueOf(obj));
            return;
        }
        if (!cls.equals(String.class) && !cls.equals(Character.class)) {
            int iA = a(obj);
            if (iA > -1) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append(Operators.ARRAY_START_STR);
                for (int i = 0; i < iA; i++) {
                    Object objA = a(Array.get(obj, i), obj.getClass());
                    a(iWebview, w2Var, objA, objA.getClass(), stringBuffer2);
                    stringBuffer2.append(",");
                }
                a(stringBuffer2);
                stringBuffer2.append(Operators.ARRAY_END_STR);
                stringBuffer.append(stringBuffer2);
                return;
            }
            stringBuffer.append(JSUtil.QUOTE + JSUtil.toJsResponseText(w2.b(iWebview, obj)) + JSUtil.QUOTE);
            return;
        }
        stringBuffer.append(JSUtil.QUOTE + JSUtil.toJsResponseText(JSUtil.QUOTE + String.valueOf(obj) + JSUtil.QUOTE) + JSUtil.QUOTE);
    }

    public static int a(Object obj) {
        if (obj instanceof Object[]) {
            return ((Object[]) obj).length;
        }
        if (obj instanceof boolean[]) {
            return ((boolean[]) obj).length;
        }
        if (obj instanceof byte[]) {
            return ((byte[]) obj).length;
        }
        if (obj instanceof char[]) {
            return ((char[]) obj).length;
        }
        if (obj instanceof short[]) {
            return ((short[]) obj).length;
        }
        if (obj instanceof int[]) {
            return ((int[]) obj).length;
        }
        if (obj instanceof long[]) {
            return ((long[]) obj).length;
        }
        if (obj instanceof float[]) {
            return ((float[]) obj).length;
        }
        if (obj instanceof double[]) {
            return ((double[]) obj).length;
        }
        return -1;
    }

    public static String a(IWebview iWebview, w2 w2Var, String str) throws SecurityException {
        StringBuffer stringBuffer;
        Class clsA = a(str);
        System.currentTimeMillis();
        StringBuffer stringBuffer2 = new StringBuffer();
        StringBuffer stringBuffer3 = new StringBuffer();
        StringBuffer stringBuffer4 = new StringBuffer();
        StringBuffer stringBuffer5 = new StringBuffer();
        if (clsA != null) {
            try {
                a(stringBuffer2, new ArrayList(), stringBuffer3, new ArrayList(), clsA);
                a(stringBuffer2);
                a(stringBuffer3);
                for (Field field : clsA.getFields()) {
                    a(iWebview, w2Var, null, field, true, stringBuffer4, stringBuffer5);
                }
                stringBuffer = stringBuffer5;
                try {
                    a(iWebview, w2Var, Class.class, clsA, "class", true, stringBuffer4, stringBuffer);
                } catch (Exception e) {
                    e = e;
                    stringBuffer4 = stringBuffer4;
                }
            } catch (Exception e2) {
                e = e2;
                stringBuffer = stringBuffer5;
            }
            try {
                a(clsA, str, stringBuffer4, stringBuffer);
                a(stringBuffer4);
                a(stringBuffer);
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
                return StringUtil.format("{InstanceMethod:[%s],ClassMethod:[%s],ClassConstKeys:[%s],ClassConstValues:[%s]}", stringBuffer2.toString(), stringBuffer3.toString(), stringBuffer4.toString(), stringBuffer.toString());
            }
        } else {
            stringBuffer = stringBuffer5;
        }
        return StringUtil.format("{InstanceMethod:[%s],ClassMethod:[%s],ClassConstKeys:[%s],ClassConstValues:[%s]}", stringBuffer2.toString(), stringBuffer3.toString(), stringBuffer4.toString(), stringBuffer.toString());
    }

    private static void a(Class cls, String str, StringBuffer stringBuffer, StringBuffer stringBuffer2) {
        Class<?>[] classes = cls.getClasses();
        if (classes != null) {
            for (Class<?> cls2 : classes) {
                String simpleName = cls2.getSimpleName();
                String name = cls2.getName();
                stringBuffer.append(JSUtil.QUOTE + simpleName + JSUtil.QUOTE);
                stringBuffer2.append(JSUtil.QUOTE + JSUtil.toJsResponseText(StringUtil.format("plus.android.importClass('%s')", name)) + JSUtil.QUOTE);
                stringBuffer.append(",");
                stringBuffer2.append(",");
            }
        }
    }

    public static void a(StringBuffer stringBuffer) {
        if (stringBuffer.length() <= 0 || stringBuffer.charAt(stringBuffer.length() - 1) != ',') {
            return;
        }
        stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
    }

    public static boolean a(Class cls) {
        return cls == Boolean.class || cls == Boolean.TYPE || cls == Double.class || cls == Double.TYPE || cls == Integer.class || cls == Integer.TYPE || cls == Float.class || cls == Float.TYPE || cls == Long.class || cls == Long.TYPE || cls == Byte.class || cls == Byte.TYPE;
    }

    public static Object a(Object obj, Class cls) {
        if (obj != null) {
            return obj;
        }
        String string = cls.toString();
        if (!string.contains("java.lang.Byte") && !string.contains("java.lang.Long") && !string.contains("java.lang.Float") && !string.contains("java.lang.Integer") && !string.contains("java.lang.Double")) {
            if (string.contains("java.lang.Boolean")) {
                return Boolean.FALSE;
            }
            return null;
        }
        return 0;
    }
}
