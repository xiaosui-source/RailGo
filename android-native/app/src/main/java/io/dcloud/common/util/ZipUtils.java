package io.dcloud.common.util;

import io.dcloud.common.adapter.util.Logger;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class ZipUtils {
    private static final int BUFF_SIZE = 1048576;

    public static String inflaterString(byte[] bArr) throws IOException {
        InflaterInputStream inflaterInputStream = new InflaterInputStream(new ByteArrayInputStream(bArr), new Inflater(true));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            try {
                try {
                    byte[] bArr2 = new byte[1024];
                    while (true) {
                        int i = inflaterInputStream.read(bArr2);
                        if (i == -1) {
                            String str = new String(byteArrayOutputStream.toByteArray());
                            inflaterInputStream.close();
                            byteArrayOutputStream.close();
                            byteArrayOutputStream.flush();
                            return str;
                        }
                        byteArrayOutputStream.write(bArr2, 0, i);
                    }
                } catch (Exception unused) {
                    return "";
                }
            } catch (Exception unused2) {
                String str2 = new String(byteArrayOutputStream.toByteArray());
                inflaterInputStream.close();
                byteArrayOutputStream.close();
                byteArrayOutputStream.flush();
                return str2;
            } catch (Throwable th) {
                try {
                    new String(byteArrayOutputStream.toByteArray());
                    inflaterInputStream.close();
                    byteArrayOutputStream.close();
                    byteArrayOutputStream.flush();
                } catch (Exception unused3) {
                }
                throw th;
            }
        } catch (Exception unused4) {
            return 1024;
        }
    }

    public static void upZipFile(File file, String str) throws IOException {
        if (!str.endsWith("/")) {
            str = str + File.separatorChar;
        }
        File file2 = new File(str);
        if (!file2.exists()) {
            file2.mkdirs();
        }
        ZipFile zipFile = new ZipFile(file);
        Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
        while (enumerationEntries.hasMoreElements()) {
            ZipEntry zipEntryNextElement = enumerationEntries.nextElement();
            if (zipEntryNextElement.getName().contains("../")) {
                Logger.d("ZIP", "util.ZipUtils Path traversal attack prevented path=" + zipEntryNextElement.getName());
            } else {
                InputStream inputStream = zipFile.getInputStream(zipEntryNextElement);
                String strReplace = new String((str + zipEntryNextElement.getName()).getBytes(), "UTF-8").replace("\\", "/");
                File file3 = new File(strReplace);
                File parentFile = file3.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                if (!strReplace.endsWith("/")) {
                    if (file3.exists()) {
                        file3.delete();
                    }
                    file3.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file3);
                    byte[] bArr = new byte[1048576];
                    while (true) {
                        int i = inputStream.read(bArr);
                        if (i <= 0) {
                            break;
                        } else {
                            fileOutputStream.write(bArr, 0, i);
                        }
                    }
                    inputStream.close();
                    fileOutputStream.close();
                }
            }
        }
        zipFile.close();
    }

    private static void zipFile(File file, ZipOutputStream zipOutputStream, String str) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str.trim().length() == 0 ? "" : File.separator);
        sb.append(file.getName());
        String str2 = new String(sb.toString().getBytes("UTF-8"), "UTF-8");
        if (file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                zipFile(file2, zipOutputStream, str2);
            }
            return;
        }
        byte[] bArr = new byte[1048576];
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file), 1048576);
        zipOutputStream.putNextEntry(new ZipEntry(str2));
        while (true) {
            int i = bufferedInputStream.read(bArr);
            if (i == -1) {
                bufferedInputStream.close();
                zipOutputStream.flush();
                zipOutputStream.closeEntry();
                return;
            }
            zipOutputStream.write(bArr, 0, i);
        }
    }

    public static void zipFiles(File[] fileArr, File file) throws IOException {
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file), 1048576));
        for (File file2 : fileArr) {
            zipFile(file2, zipOutputStream, "");
        }
        zipOutputStream.close();
    }

    public static byte[] zipString(String str) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(str.getBytes());
            gZIPOutputStream.flush();
            gZIPOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }
}
