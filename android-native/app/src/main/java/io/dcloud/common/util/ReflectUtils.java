package io.dcloud.common.util;

import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class ReflectUtils {
    public static final String CLASSNAME_APPLICATIONINFO = "android.content.pm.ApplicationInfo";
    public static final String CLASSNAME_AUDIOSYSTEM = "android.media.AudioSystem";
    public static final String CLASSNAME_ICONTENTPROVIDER = "android.content.IContentProvider";
    public static final String CLASSNAME_IMOUNTSERVICE_STUB = "android.os.storage.IMountService$Stub";
    public static final String CLASSNAME_IPACKAGEDATAOBSERVER = "android.content.pm.IPackageDataObserver";
    public static final String CLASSNAME_IPACKAGEDELETEOBSERVER = "android.content.pm.IPackageDeleteObserver";
    public static final String CLASSNAME_IPACKAGEINSTALLOBERVER = "android.content.pm.IPackageInstallObserver";
    public static final String CLASSNAME_IPACKAGEMANAGER = "android.content.pm.IPackageManager";
    public static final String CLASSNAME_IPACKAGEMANAGER_STUB = "android.content.pm.IPackageManager$Stub";
    public static final String CLASSNAME_IPACKAGESTATSOBSERVER = "android.content.pm.IPackageStatsObserver";
    public static final String CLASSNAME_PACKAGEINFO = "android.content.pm.PackageInfo";
    public static final String CLASSNAME_PACKAGEMANAGER = "android.content.pm.PackageManager";
    public static final String CLASSNAME_PAGEAGEPARSE = "android.content.pm.PackageParser";
    public static final String CLASSNAME_PAGEAGEPARSE_PACKAGE = "android.content.pm.PackageParser$Package";
    public static final String CLASSNAME_PROCESS = "android.os.Process";
    public static final String CLASSNAME_THREADS = "android.provider.Telephony$Threads";
    public static final String CLASSNAME_THUMBNAILUTILS = "android.media.ThumbnailUtils";

    public static Class classForName(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException unused) {
            throw new RuntimeException(str);
        }
    }

    public static Context getApplicationContext() {
        try {
            return (Context) Class.forName("android.app.ActivityThread").getDeclaredMethod("currentApplication", null).invoke(null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Field getDeclaredField(Object obj, String str) {
        for (Class<?> superclass = obj.getClass(); superclass != Object.class; superclass = superclass.getSuperclass()) {
            try {
                return superclass.getDeclaredField(str);
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static Method getDeclaredMethod(Object obj, String str, Class<?>... clsArr) {
        for (Class<?> superclass = obj.getClass(); superclass != Object.class; superclass = superclass.getSuperclass()) {
            try {
                return superclass.getDeclaredMethod(str, clsArr);
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static Object getField(Object obj, String str) throws IllegalAccessException, NoSuchFieldException {
        return prepareField(obj.getClass(), str).get(obj);
    }

    public static Object getFieldValue(Object obj, String str) throws SecurityException {
        Field declaredField = getDeclaredField(obj, str);
        declaredField.setAccessible(true);
        try {
            return declaredField.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getIntField(Object obj, String str) {
        try {
            return obj.getClass().getDeclaredField(str).getInt(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static Method getMethod(String str, String str2, Class<?>... clsArr) {
        try {
            return Class.forName(str).getDeclaredMethod(str2, clsArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Constructor getObjectConstructor(String str, Class... clsArr) {
        try {
            return Class.forName(str).getConstructor(clsArr);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static Object getObjectField(Object obj, String str) {
        try {
            return obj.getClass().getDeclaredField(str).get(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static Object getObjectFieldNoDeclared(Object obj, String str) {
        try {
            return obj.getClass().getField(str).get(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static int getStaticIntField(String str, String str2) {
        try {
            return Class.forName(str).getDeclaredField(str2).getInt(null);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        } catch (NoSuchFieldException e3) {
            throw new RuntimeException(e3);
        }
    }

    public static Object getStaticObjectField(String str, String str2) {
        try {
            return Class.forName(str).getDeclaredField(str2).get(null);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        } catch (NoSuchFieldException e3) {
            throw new RuntimeException(e3);
        }
    }

    public static String getStaticStringField(String str, String str2) {
        return (String) getStaticObjectField(str, str2);
    }

    public static String getSystemProperties(String str, String str2) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getDeclaredMethod("get", String.class, String.class).invoke(null, str, str2);
        } catch (Exception e) {
            e.printStackTrace();
            return str2;
        }
    }

    public static Object invoke(Object obj, Method method, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object invokeMethod(Object obj, String str, Class<?>[] clsArr, Object[] objArr) {
        Method declaredMethod = getDeclaredMethod(obj, str, clsArr);
        declaredMethod.setAccessible(true);
        try {
            return declaredMethod.invoke(obj, objArr);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused) {
            return null;
        }
    }

    public static void modifyPushBigContentView(Object obj, String str, Object obj2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        try {
            Field declaredField = obj.getClass().getDeclaredField(str);
            declaredField.setAccessible(true);
            declaredField.set(obj, obj2);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
        }
    }

    public static void modifyPushPriority(Object obj, String str, Object obj2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        try {
            Field declaredField = obj.getClass().getDeclaredField(str);
            declaredField.setAccessible(true);
            declaredField.set(obj, obj2);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
        }
    }

    private static Field prepareField(Class<?> cls, String str) throws NoSuchFieldException {
        if (cls == null) {
            throw new NoSuchFieldException();
        }
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField;
        } finally {
            cls.getSuperclass();
        }
    }

    public static void setField(Object obj, String str, Object obj2) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        prepareField(obj.getClass(), str).set(obj, obj2);
    }

    public static void setFieldValue(Object obj, String str, Object obj2) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        Field declaredField = getDeclaredField(obj, str);
        declaredField.setAccessible(true);
        try {
            declaredField.set(obj, obj2);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        }
    }

    public static Object stubAsInterface(String str, IBinder iBinder) {
        return stubAsInterface(classForName(str), iBinder);
    }

    public static void windowDismissed(InputMethodManager inputMethodManager, IBinder iBinder) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            inputMethodManager.getClass().getMethod("windowDismissed", IBinder.class).invoke(inputMethodManager, iBinder);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e2) {
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e3) {
            throw new RuntimeException(e3);
        }
    }

    public static Object stubAsInterface(Class cls, IBinder iBinder) {
        try {
            return cls.getDeclaredMethod("asInterface", IBinder.class).invoke(null, iBinder);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e2) {
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e3) {
            throw new RuntimeException(e3);
        }
    }

    public static Method getMethod(Class<?> cls, String str, Class<?>[] clsArr) {
        if (str != null && str.length() > 0) {
            try {
                return cls.getMethod(str, clsArr);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return null;
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
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.util.ReflectUtils.invokeMethod(java.lang.String, java.lang.String, java.lang.Object, java.lang.Class[], java.lang.Object[]):java.lang.Object");
    }
}
