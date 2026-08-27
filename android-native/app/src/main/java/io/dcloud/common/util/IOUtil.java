package io.dcloud.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class IOUtil {
    static final int BUF_SIZE = 20480;

    public static final InputStream byte2InputStream(byte[] bArr) {
        return new ByteArrayInputStream(bArr);
    }

    public static void close(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[BUF_SIZE];
        while (true) {
            int i = inputStream.read(bArr);
            if (i == -1) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                close(byteArrayOutputStream);
                return byteArray;
            }
            byteArrayOutputStream.write(bArr, 0, i);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0029 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String readStringFile(java.lang.String r3) throws java.lang.Throwable {
        /*
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L17 java.io.IOException -> L19
            r1.<init>(r3)     // Catch: java.lang.Throwable -> L17 java.io.IOException -> L19
            byte[] r3 = getBytes(r1)     // Catch: java.lang.Throwable -> L13 java.io.IOException -> L15
            java.lang.String r2 = new java.lang.String     // Catch: java.lang.Throwable -> L13 java.io.IOException -> L15
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L13 java.io.IOException -> L15
            r1.close()     // Catch: java.io.IOException -> L12
        L12:
            return r2
        L13:
            r3 = move-exception
            goto L27
        L15:
            r3 = move-exception
            goto L1b
        L17:
            r3 = move-exception
            goto L26
        L19:
            r3 = move-exception
            r1 = r0
        L1b:
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L24
            if (r1 == 0) goto L23
            r1.close()     // Catch: java.io.IOException -> L23
        L23:
            return r0
        L24:
            r3 = move-exception
            r0 = r1
        L26:
            r1 = r0
        L27:
            if (r1 == 0) goto L2c
            r1.close()     // Catch: java.io.IOException -> L2c
        L2c:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.util.IOUtil.readStringFile(java.lang.String):java.lang.String");
    }

    public static String toString(InputStream inputStream) throws IOException {
        return inputStream == null ? "" : toStringBuffer(inputStream).toString();
    }

    private static StringBuilder toStringBuffer(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                inputStream.close();
                return sb;
            }
            sb.append(line);
            sb.append("\n");
        }
    }

    public static boolean writeStringFile(String str, String str2) throws Throwable {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(str);
            } catch (IOException e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
            fileOutputStream = fileOutputStream2;
        }
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");
            outputStreamWriter.write(str2, 0, str2.length());
            outputStreamWriter.flush();
            outputStreamWriter.close();
            close(fileOutputStream);
            return true;
        } catch (IOException e2) {
            e = e2;
            fileOutputStream2 = fileOutputStream;
            e.printStackTrace();
            close(fileOutputStream2);
            return false;
        } catch (Throwable th2) {
            th = th2;
            close(fileOutputStream);
            throw th;
        }
    }

    public static void close(OutputStream outputStream) throws IOException {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
