package com.bun.miitmdid.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.zip.CRC32;

/* loaded from: classes.dex */
public class Utils {
    public static final String CPU_ABI_X86 = "x86";

    private static String CPUABI() {
        try {
            return new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("getprop ro.product.cpu.abi").getInputStream())).readLine().contains("x86") ? "x86" : "arm";
        } catch (Throwable th) {
            th.printStackTrace();
            return "arm";
        }
    }

    public static void PrintClassMethod(Class<?> cls) throws SecurityException {
        for (Method method : cls.getMethods()) {
            System.out.println(method.getName());
        }
    }

    public static void PrintObjectType(Class<?> cls) {
        String name = cls.getName();
        System.out.println("PrintObjectType:" + name);
    }

    public static void PrintObjectType(Object obj) {
        String name = obj.getClass().getName();
        System.out.println("PrintObjectType:" + name);
    }

    public static long getFileCRC(String str) {
        try {
            File file = new File(str);
            if (!file.exists()) {
                return -1L;
            }
            int length = (int) file.length();
            FileInputStream fileInputStream = new FileInputStream(str);
            CRC32 crc32 = new CRC32();
            byte[] bArr = new byte[length];
            for (int i = 0; i < length; i += fileInputStream.read(bArr, i, length - i)) {
            }
            crc32.update(length);
            return crc32.getValue();
        } catch (IOException unused) {
            return -1L;
        }
    }

    public static void getFileListame(String str) {
        File[] fileArrListFiles = new File(str).listFiles();
        if (fileArrListFiles != null) {
            for (int i = 0; i < fileArrListFiles.length; i++) {
                Log.i("Utils", fileArrListFiles[i].getName());
                if (fileArrListFiles[i].isDirectory()) {
                    getFileListame(fileArrListFiles[i].getAbsolutePath());
                    Log.i("Utils", String.valueOf(fileArrListFiles[i].getAbsolutePath()) + fileArrListFiles[i].getName());
                }
            }
        }
    }

    public static String getLibraryDir(Context context) {
        return context.getApplicationInfo().nativeLibraryDir;
    }

    public static String getUserDir(Context context) {
        return context.getFilesDir().getParent();
    }

    public static String getXdataDir(Context context, String str) {
        return new StringBuffer().append(String.valueOf(getUserDir(context)) + "/" + JLibrary.xdata + "/" + str).toString();
    }

    public static String getYdataDir(Context context, String str) {
        return new StringBuffer().append(String.valueOf(getUserDir(context)) + "/" + JLibrary.ydata + "/" + str).toString();
    }

    public static boolean isX86() {
        return Build.CPU_ABI.equals("x86") || CPUABI().equals("x86");
    }

    public static boolean update(Context context) throws Exception {
        long zipCrc = ZipUtils.getZipCrc(new File(context.getApplicationInfo().sourceDir));
        SharedPreferences sharedPreferences = context.getSharedPreferences("update", 0);
        boolean z = zipCrc != sharedPreferences.getLong("crc", 0L);
        sharedPreferences.edit().putLong("crc", zipCrc).commit();
        return z;
    }

    public static void x0xooXdata(InputStream inputStream, String str, Context context) throws Exception {
        File file = new File(str);
        byte[] bArr = new byte[65536];
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
        while (true) {
            int i = bufferedInputStream.read(bArr);
            if (i <= 0) {
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                bufferedInputStream.close();
                return;
            }
            bufferedOutputStream.write(bArr, 0, i);
        }
    }
}
