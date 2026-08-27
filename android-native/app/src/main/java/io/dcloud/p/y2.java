package io.dcloud.p;

import android.util.Log;
import io.dcloud.common.adapter.util.Logger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class y2 extends Logger {
    private static String a;
    private static File b;
    private static Boolean c = Boolean.TRUE;

    private static void WriteLogToSDcard(String str, String str2, String str3) throws Throwable {
        FileOutputStream fileOutputStream;
        String strGenerateLog = Logger.generateLog(str, str2, str3);
        if (b == null || strGenerateLog == null) {
            return;
        }
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                try {
                    fileOutputStream = new FileOutputStream(b, true);
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                }
            } catch (Exception e) {
                e = e;
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        try {
            fileOutputStream.write(strGenerateLog.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e3) {
            e = e3;
            fileOutputStream2 = fileOutputStream;
            e.printStackTrace();
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
            }
        } catch (Throwable th2) {
            th = th2;
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static void a(String str) throws IOException, ParseException {
        if (c.booleanValue()) {
            a = str;
            storeLogToSDcard();
            c = Boolean.FALSE;
        }
    }

    public static void d(String str, String str2) throws Throwable {
        Log.d(str, str2);
        WriteLogToSDcard(Logger.D, str, str2);
    }

    public static void e(String str, String str2) throws Throwable {
        Log.e(str, str2);
        WriteLogToSDcard(Logger.E, str, str2);
    }

    public static void i(String str, String str2) throws Throwable {
        Log.i(str, str2);
        WriteLogToSDcard(Logger.I, str, str2);
    }

    public static void storeLogToSDcard() throws IOException, ParseException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(a).append(File.separatorChar).append(Logger.generateTimeStamp(Boolean.FALSE)).append(".log");
        File file = new File(a);
        b = new File(stringBuffer.toString());
        if (file.exists()) {
            Logger.deleteOldLog(file);
        } else {
            file.mkdirs();
        }
        if (b.exists()) {
            return;
        }
        try {
            b.createNewFile();
        } catch (IOException e) {
            b = null;
            e.printStackTrace();
        }
    }

    public static void a(String str, String str2) throws Throwable {
        Log.i(str, str2);
        WriteLogToSDcard(Logger.W, str, str2);
    }
}
