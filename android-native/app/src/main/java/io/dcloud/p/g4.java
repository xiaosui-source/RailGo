package io.dcloud.p;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.util.Md5Utils;
import io.dcloud.p.o;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class g4 {
    private static g4 d;
    Context a;
    private o.c b;
    String c;

    private g4(Context context, String str) {
        this.a = context;
        this.c = str;
        if (this.b == null) {
            try {
                String strA = a(context);
                SharedPreferences orCreateBundle = SP.getOrCreateBundle(context, str);
                String string = orCreateBundle.getString(strA, null);
                if (string != null) {
                    this.b = o.a(string);
                } else {
                    this.b = o.c();
                    orCreateBundle.edit().putString(strA, this.b.toString()).commit();
                }
            } catch (GeneralSecurityException unused) {
            }
        }
    }

    public static g4 a(Context context, String str) {
        if (d == null) {
            synchronized (g4.class) {
                if (d == null) {
                    d = new g4(context, str);
                }
            }
        }
        return d;
    }

    private String c(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            return o.a(str, this.b).toString();
        } catch (UnsupportedEncodingException | GeneralSecurityException unused) {
            return null;
        }
    }

    public static String d(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            messageDigest.update(bytes, 0, bytes.length);
            return Base64.encodeToString(messageDigest.digest(), 2);
        } catch (NoSuchAlgorithmException unused) {
            return null;
        }
    }

    public void b(String str, String str2) throws IOException {
        try {
            File dir = this.a.getDir(Md5Utils.md5(this.c), 0);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, Md5Utils.md5(str));
            if (!file.exists()) {
                try {
                    dir.createNewFile();
                } catch (IOException unused) {
                }
            }
            try {
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(c(str2));
                bufferedWriter.flush();
                bufferedWriter.close();
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException unused2) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String a(Context context) {
        return d(o.a(context.getPackageName(), context.getPackageName().getBytes(), 1000).toString());
    }

    public boolean a(String str) {
        return new File(this.a.getDir(Md5Utils.md5(this.c), 0), Md5Utils.md5(str)).exists();
    }

    public String a(String str, String str2) {
        File file = new File(this.a.getDir(Md5Utils.md5(this.c), 0).getAbsolutePath() + "/" + Md5Utils.md5(str));
        if (file.exists()) {
            try {
                return b(new String(DHFile.readAll(file)));
            } catch (Exception unused) {
            }
        }
        return str2;
    }

    private String b(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            return o.b(new o.a(str), this.b);
        } catch (UnsupportedEncodingException | GeneralSecurityException unused) {
            return null;
        }
    }
}
