package com.bun.miitmdid.core;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public class JLibrary {
    public static String ASSET = "assets/";
    public static String SeriailizationString = "stub_liu_0_dex_so:39285EFA@com/jdog;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;";
    public static final String TAG = "JLibrary";
    public static ClassLoader classLoader = null;
    public static Context context = null;
    public static String o00o0a0odod = null;
    public static String o00o0a0odou = null;
    public static String xdata = ".00000000000";
    public static String ydata = ".11111111111";

    enum ReturnStatus {
        RETURN_OK,
        RETURN_ERR;

        /* renamed from: values, reason: to resolve conflict with enum method */
        public static ReturnStatus[] valuesCustom() {
            ReturnStatus[] returnStatusArrValuesCustom = values();
            int length = returnStatusArrValuesCustom.length;
            ReturnStatus[] returnStatusArr = new ReturnStatus[length];
            System.arraycopy(returnStatusArrValuesCustom, 0, returnStatusArr, 0, length);
            return returnStatusArr;
        }
    }

    public static ReturnStatus InitEntry(Context context2) throws Exception {
        if (context2 == null) {
            throw new ExceptionInInitializerError("pass InitEntry arg(context) is null");
        }
        context = context2;
        classLoader = JLibrary.class.getClassLoader();
        String str = SeriailizationString;
        System.loadLibrary(str.substring(str.lastIndexOf(58) + 1, SeriailizationString.indexOf(64)));
        a();
        return ReturnStatus.RETURN_OK;
    }

    public static ByteBuffer ReadByteBuffer(String str) throws IOException {
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(fileInputStream.available());
            for (int i = 0; i < fileInputStream.available(); i += fileInputStream.read(byteBufferAllocate.array(), i, fileInputStream.available() - i)) {
            }
            fileInputStream.close();
            return byteBufferAllocate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static native boolean a();

    public static Object[] o0o0o0o0o0(Object obj, String str, String str2, List<IOException> list) throws IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        String[] strArrSplit = str.split(";");
        ArrayList arrayList = new ArrayList();
        for (String str3 : strArrSplit) {
            arrayList.add(ReadByteBuffer(str3));
        }
        return (Object[]) o0o0o0o0o0o0(obj, str2, ByteBuffer[].class, List.class).invoke(obj, arrayList.toArray(new ByteBuffer[arrayList.size()]), list);
    }

    private static Method o0o0o0o0o0o0(Object obj, String str, Class<?>... clsArr) throws NoSuchMethodException, SecurityException {
        for (Class<?> superclass = obj.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
            try {
                Method declaredMethod = superclass.getDeclaredMethod(str, clsArr);
                if (!declaredMethod.isAccessible()) {
                    declaredMethod.setAccessible(true);
                }
                return declaredMethod;
            } catch (NoSuchMethodException unused) {
            }
        }
        throw new NoSuchMethodException("Method " + str + " with parameters " + Arrays.asList(clsArr) + " not found in " + obj.getClass());
    }

    public static void o0oo0o0(Context context2, String str) throws Exception {
        int i;
        int i2;
        try {
            InputStream inputStreamOpen = context2.getAssets().open(str);
            o00o0a0odod = Utils.getXdataDir(context2, "");
            o00o0a0odou = Utils.getYdataDir(context2, "");
            File file = new File(o00o0a0odod);
            File file2 = new File(o00o0a0odou);
            int i3 = 3;
            if (!file.exists()) {
                int i4 = 3;
                while (true) {
                    i = i4 - 1;
                    if (i4 <= 0 || file.mkdir()) {
                        break;
                    } else {
                        i4 = i;
                    }
                }
            } else {
                i = 3;
            }
            if (i == -1) {
                throw new IllegalStateException("User dir cannot be created: " + file.getAbsolutePath());
            }
            if (!file2.exists()) {
                while (true) {
                    i2 = i3 - 1;
                    if (i3 <= 0 || file2.mkdir()) {
                        break;
                    } else {
                        i3 = i2;
                    }
                }
                i3 = i2;
            }
            if (i3 == -1) {
                throw new IllegalStateException("User dir cannot be created: " + file2.getAbsolutePath());
            }
            if (!Utils.update(context2)) {
                if (new File(String.valueOf(o00o0a0odod) + str).exists()) {
                    return;
                }
            }
            Utils.x0xooXdata(inputStreamOpen, String.valueOf(o00o0a0odod) + str, context2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
