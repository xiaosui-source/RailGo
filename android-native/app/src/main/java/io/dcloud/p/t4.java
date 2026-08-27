package io.dcloud.p;

import android.content.Context;
import android.text.TextUtils;
import com.taobao.weex.el.parse.Operators;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class t4 {
    private static String a = "";
    private static String b;
    private static String c;

    private static boolean a(String str) {
        return TextUtils.isEmpty(str) || str.contains("Unknown") || str.contains("00000000");
    }

    private static StringBuilder b(InputStream inputStream) throws IOException {
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

    /* JADX WARN: Removed duplicated region for block: B:86:0x0115 A[PHI: r1
      0x0115: PHI (r1v3 java.lang.String) = (r1v1 java.lang.String), (r1v4 java.lang.String) binds: [B:84:0x0112, B:76:0x0103] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String a(android.content.Context r11, boolean r12, boolean r13) throws java.lang.NoSuchMethodException, java.lang.SecurityException {
        /*
            Method dump skipped, instructions count: 368
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.p.t4.a(android.content.Context, boolean, boolean):java.lang.String");
    }

    public static String a(Context context) {
        try {
            k1.a(context);
            StringBuilder sb = new StringBuilder();
            sb.append(context.getFilesDir());
            String str = File.separator;
            sb.append(str);
            sb.append(".imei.txt");
            String string = sb.toString();
            File file = new File(string);
            if (!file.exists()) {
                string = context.getFilesDir() + str + ".DC4278477faeb9.txt";
                file = new File(string);
            }
            if (file.isDirectory()) {
                file.delete();
            }
            return a(file, null, string, null, context);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String a(File file, File file2, String str, String str2, Context context) throws IOException {
        String strA;
        if (file.exists() && file.length() > 0) {
            try {
                strA = a(new FileInputStream(file));
                if (file2 != null) {
                    try {
                        if (!k1.a(context)) {
                            if (!file2.getParentFile().exists()) {
                                file2.getParentFile().mkdirs();
                                file2.createNewFile();
                            }
                            v0.a(str, str2);
                            return strA;
                        }
                    } catch (Exception unused) {
                        return strA == null ? a(context, file, file2, ".DC4278477faeb9.txt") : strA;
                    }
                }
                return strA;
            } catch (Exception unused2) {
                strA = null;
            }
        } else {
            return a(context, file, file2, ".DC4278477faeb9.txt");
        }
    }

    private static String a(Context context, File file, File file2, String str) throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        String strReplace = UUID.randomUUID().toString().replaceAll(Operators.SUB, "").replace("\n", "");
        byte[] bytes = strReplace.getBytes();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if (k1.a(context)) {
            return strReplace;
        }
        v0.a(file.getPath(), file2.getPath());
        return strReplace;
    }

    public static String a(InputStream inputStream) {
        if (inputStream == null) {
            return "";
        }
        return b(inputStream).toString();
    }
}
