package io.dcloud.p;

import android.content.Context;
import io.dcloud.common.util.Base64;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.LoadAppUtils;
import io.dcloud.common.util.Md5Utils;
import io.dcloud.common.util.PdrUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Locale;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class s {
    private static boolean a(Context context, String str) throws JSONException {
        if (b4.b() && !PdrUtil.isEmpty(b4.a())) {
            try {
                JSONArray jSONArray = new JSONObject(new String(a(Base64.decode2bytes(b4.a()), a(d1.d())))).getJSONArray("appkeys");
                String strB = b(context, str);
                for (int i = 0; i < jSONArray.length(); i++) {
                    String string = jSONArray.getString(i);
                    if (!PdrUtil.isEmpty(string) && PdrUtil.isEquals(string, strB)) {
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static String b(Context context, String str) {
        if (PdrUtil.isEmpty(str)) {
            str = a();
        }
        return Md5Utils.md5((str + a(context)) + b(context) + d1.a(d1.a(), true, 60));
    }

    public static boolean c(Context context) {
        return (b4.b() && a(context, (String) null)) ? false : true;
    }

    public static boolean d(Context context, String str) {
        return (b4.b() && a(context, str)) ? false : true;
    }

    public static boolean c(Context context, String str) {
        return a(context, str);
    }

    private static String b(Context context) {
        return LoadAppUtils.getAppSignatureSHA1(context);
    }

    private static String a(Context context) {
        return context.getPackageName().toLowerCase(Locale.ENGLISH);
    }

    private static String a() {
        return BaseInfo.sDefaultBootApp;
    }

    private static Key a(String str) throws IOException {
        try {
            return KeyFactory.getInstance(d1.a("WltJ")).generatePublic(new X509EncodedKeySpec(Base64.decode2bytes(str)));
        } catch (Exception unused) {
            return null;
        }
    }

    private static byte[] a(byte[] bArr, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        try {
            Cipher cipher = Cipher.getInstance(d1.a("WltJJ01LSidYQ0tbOVhpbGxhZm8="));
            cipher.init(2, key);
            int blockSize = cipher.getBlockSize();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bArr.length);
            int i = 0;
            while (true) {
                int i2 = i * blockSize;
                if (bArr.length - i2 > 0) {
                    byteArrayOutputStream.write(cipher.doFinal(bArr, i2, blockSize));
                    i++;
                } else {
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } catch (Exception unused) {
            return null;
        }
    }
}
