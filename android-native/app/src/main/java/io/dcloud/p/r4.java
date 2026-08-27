package io.dcloud.p;

import android.text.TextUtils;
import android.util.Base64;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class r4 {
    public static byte[] a(String str) {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (bytes[i] ^ 8);
        }
        return bytes;
    }

    public static String b(String str) {
        return a(f(str));
    }

    public static String c(String str) {
        return TextUtils.isEmpty(str) ? str : a(Base64.decode(str, 2));
    }

    public static String d(String str) throws UnsupportedEncodingException {
        try {
            byte[] bytes = str.getBytes("GBK");
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (bytes[i] ^ 8);
            }
            return new String(bytes, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String e(String str) {
        return TextUtils.isEmpty(str) ? str : Base64.encodeToString(a(str), 2);
    }

    public static byte[] f(String str) {
        if (str == null || str.length() < 2) {
            return new byte[0];
        }
        String lowerCase = str.toLowerCase();
        int length = lowerCase.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) (Integer.parseInt(lowerCase.substring(i2, i2 + 2), 16) & 255);
        }
        return bArr;
    }

    public static String a(byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) (bArr[i] ^ 8);
        }
        return new String(bArr, StandardCharsets.UTF_8);
    }
}
