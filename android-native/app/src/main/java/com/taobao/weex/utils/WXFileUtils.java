package com.taobao.weex.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import io.dcloud.common.util.Md5Utils;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.weex.DCFileUtils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXFileUtils {
    public static void closeIo(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void copyFile(File file, File file2) throws IOException {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                byte[] bArr = new byte[1024];
                fileOutputStream = new FileOutputStream(file2);
                while (fileInputStream2.read(bArr) != -1) {
                    try {
                        fileOutputStream.write(bArr);
                    } catch (Exception e) {
                        e = e;
                        fileInputStream = fileInputStream2;
                        WXLogUtils.e("copyFile " + e.getMessage() + ": " + file.getAbsolutePath() + ": " + file2.getAbsolutePath());
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                                return;
                            } catch (IOException e3) {
                                e3.printStackTrace();
                                return;
                            }
                        }
                        return;
                    }
                }
                fileInputStream2.close();
                fileOutputStream.close();
            } catch (Exception e4) {
                fileOutputStream = null;
                fileInputStream = fileInputStream2;
                e = e4;
            }
        } catch (Exception e5) {
            e = e5;
            fileOutputStream = null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.io.Closeable, java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r2v8 */
    public static void copyFileWithException(File file, File file2) throws Throwable {
        ?? fileOutputStream;
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (Exception e) {
            e = e;
            fileOutputStream = 0;
        } catch (Throwable th) {
            th = th;
            fileOutputStream = 0;
            fileInputStream = fileInputStream2;
            fileInputStream2 = fileOutputStream;
            closeIo(fileInputStream);
            closeIo(fileInputStream2);
            throw th;
        }
        try {
            byte[] bArr = new byte[1024];
            fileOutputStream = new FileOutputStream(file2);
            while (fileInputStream.read(bArr) != -1) {
                try {
                    fileOutputStream.write(bArr);
                } catch (Exception e2) {
                    e = e2;
                    fileInputStream2 = fileInputStream;
                    fileOutputStream = fileOutputStream;
                    try {
                        throw e;
                    } catch (Throwable th2) {
                        th = th2;
                        fileInputStream = fileInputStream2;
                        fileInputStream2 = fileOutputStream;
                        closeIo(fileInputStream);
                        closeIo(fileInputStream2);
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fileInputStream2 = fileOutputStream;
                    closeIo(fileInputStream);
                    closeIo(fileInputStream2);
                    throw th;
                }
            }
            closeIo(fileInputStream);
            closeIo(fileOutputStream);
        } catch (Exception e3) {
            e = e3;
            fileOutputStream = 0;
        } catch (Throwable th4) {
            th = th4;
            closeIo(fileInputStream);
            closeIo(fileInputStream2);
            throw th;
        }
    }

    public static boolean extractSo(String str, String str2) throws IOException {
        boolean z = false;
        if (PdrUtil.isSafeEntryName(str) && PdrUtil.isSafeEntryName(str2)) {
            HashMap map = new HashMap();
            ZipFile zipFile = new ZipFile(str);
            ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(str)));
            Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
            while (enumerationEntries.hasMoreElements()) {
                ZipEntry zipEntryNextElement = enumerationEntries.nextElement();
                if (!zipEntryNextElement.getName().contains("../") && !zipEntryNextElement.isDirectory() && validLibPath(zipEntryNextElement.getName()) && (zipEntryNextElement.getName().contains("weex") || zipEntryNextElement.getName().equals("libjsc.so") || zipEntryNextElement.getName().equals("libJavaScriptCore.so"))) {
                    String[] strArrSplit = zipEntryNextElement.getName().split("/");
                    String str3 = strArrSplit[strArrSplit.length - 1];
                    File file = new File(str2 + "/" + str3);
                    if (replaceLib(zipEntryNextElement.getName(), (String) map.get(str3))) {
                        map.put(str3, zipEntryNextElement.getName());
                        if (file.exists()) {
                            file.delete();
                        }
                        InputStream inputStream = zipFile.getInputStream(zipEntryNextElement);
                        byte[] bArr = new byte[1024];
                        file.createNewFile();
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        while (inputStream.read(bArr) != -1) {
                            fileOutputStream.write(bArr);
                        }
                        fileOutputStream.close();
                    }
                    z = true;
                }
            }
            zipInputStream.closeEntry();
        }
        return z;
    }

    public static String loadAsset(String str, Context context) {
        if (context == null || TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            String assetPath = DCFileUtils.getAssetPath(str);
            InputStream inputStreamLoadWeexAsset = DCFileUtils.loadWeexAsset(assetPath, context);
            if (inputStreamLoadWeexAsset == null) {
                inputStreamLoadWeexAsset = context.getAssets().open(assetPath);
            }
            return readStreamToString(inputStreamLoadWeexAsset);
        } catch (IOException unused) {
            return "";
        }
    }

    public static String loadFileOrAsset(String str, Context context) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        File file = new File(str);
        if (!file.exists()) {
            return loadAsset(str, context);
        }
        try {
            return readStreamToString(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static byte[] readBytesFromAssets(String str, Context context) throws IOException {
        if (context != null && !TextUtils.isEmpty(str)) {
            try {
                InputStream inputStreamOpen = context.getAssets().open(str);
                byte[] bArr = new byte[4096];
                int i = inputStreamOpen.read(bArr);
                byte[] bArr2 = new byte[i];
                System.arraycopy(bArr, 0, bArr2, 0, i);
                return bArr2;
            } catch (IOException e) {
                WXLogUtils.e("", e);
            }
        }
        return null;
    }

    private static String readStreamToString(InputStream inputStream) throws Throwable {
        StringBuilder sb;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                sb = new StringBuilder(inputStream.available() + 10);
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            } catch (IOException e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            char[] cArr = new char[4096];
            while (true) {
                int i = bufferedReader.read(cArr);
                if (i <= 0) {
                    break;
                }
                sb.append(cArr, 0, i);
            }
            String string = sb.toString();
            try {
                bufferedReader.close();
            } catch (IOException e2) {
                WXLogUtils.e("WXFileUtils loadAsset: ", e2);
            }
            try {
                inputStream.close();
            } catch (IOException e3) {
                WXLogUtils.e("WXFileUtils loadAsset: ", e3);
            }
            return string;
        } catch (IOException e4) {
            e = e4;
            bufferedReader2 = bufferedReader;
            WXLogUtils.e("", e);
            if (bufferedReader2 != null) {
                try {
                    bufferedReader2.close();
                } catch (IOException e5) {
                    WXLogUtils.e("WXFileUtils loadAsset: ", e5);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e6) {
                    WXLogUtils.e("WXFileUtils loadAsset: ", e6);
                }
            }
            return "";
        } catch (Throwable th2) {
            th = th2;
            bufferedReader2 = bufferedReader;
            if (bufferedReader2 != null) {
                try {
                    bufferedReader2.close();
                } catch (IOException e7) {
                    WXLogUtils.e("WXFileUtils loadAsset: ", e7);
                }
            }
            if (inputStream == null) {
                throw th;
            }
            try {
                inputStream.close();
                throw th;
            } catch (IOException e8) {
                WXLogUtils.e("WXFileUtils loadAsset: ", e8);
                throw th;
            }
        }
    }

    private static boolean replaceLib(String str, String str2) {
        if (str == null || str2 == null) {
            return true;
        }
        boolean z = false;
        for (String str3 : validCPUABIS()) {
            if (str2.contains(str3) && z) {
                return true;
            }
            if (str.contains(str3)) {
                z = true;
            }
        }
        return false;
    }

    public static boolean saveFile(String str, byte[] bArr, Context context) {
        FileOutputStream fileOutputStream;
        if (TextUtils.isEmpty(str) || bArr == null || context == null) {
            return false;
        }
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(str);
            } catch (Throwable th) {
                th = th;
                fileOutputStream = fileOutputStream2;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            fileOutputStream.write(bArr);
            try {
                fileOutputStream.close();
                return true;
            } catch (IOException e2) {
                e2.printStackTrace();
                return true;
            }
        } catch (Exception e3) {
            e = e3;
            fileOutputStream2 = fileOutputStream;
            WXLogUtils.e("WXFileUtils saveFile: " + WXLogUtils.getStackTrace(e));
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            return false;
        } catch (Throwable th2) {
            th = th2;
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            throw th;
        }
    }

    private static boolean validLibPath(String str) {
        for (String str2 : validCPUABIS()) {
            if (str.contains(str2)) {
                return true;
            }
        }
        return false;
    }

    private static String[] validCPUABIS() {
        String[] strArr = Build.SUPPORTED_ABIS;
        return (strArr == null || strArr.length == 0) ? new String[]{Build.CPU_ABI} : strArr;
    }

    public static String base64Md5(String str) {
        if (str == null) {
            return "";
        }
        try {
            return base64Md5(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException unused) {
            return "";
        }
    }

    public static String md5(String str) {
        if (str == null) {
            return "";
        }
        try {
            return md5(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException unused) {
            return "";
        }
    }

    public static String base64Md5(byte[] bArr) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(Md5Utils.ALGORITHM);
            messageDigest.update(bArr);
            return Base64.encodeToString(messageDigest.digest(), 2);
        } catch (NoSuchAlgorithmException unused) {
            return "";
        }
    }

    public static String md5(byte[] bArr) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(Md5Utils.ALGORITHM);
            messageDigest.update(bArr);
            return new BigInteger(1, messageDigest.digest()).toString(16);
        } catch (NoSuchAlgorithmException unused) {
            return "";
        }
    }
}
