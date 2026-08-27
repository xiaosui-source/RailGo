package com.bun.miitmdid.d;

import android.text.TextUtils;
import android.util.Base64;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Iterator;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class b {
    public static final byte[] a = "#PART#".getBytes();

    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return new String(Base64.encode(b(str.getBytes(), Base64.decode("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC6ZetPcgLCvLsvDWzA3TYpRhEO\nfncquhfl9utVX+VApfrknKvYInYzmxjhdAEay+Nn6NPJKGPkCt1D7VWbf0YPiLmo\noCTIsuc7czZOu9pBJYjOrqCZhhJsJucc3+T/un8KioD2CjkXy0EhNMJSuvo+tHJg\nTbiR4QuPcIU1YBLX4wIDAQAB".getBytes(), 0)), 2));
    }

    private static byte[] a(byte[] bArr, byte[] bArr2) throws InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        PublicKey publicKeyGeneratePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bArr2));
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(1, publicKeyGeneratePublic);
        return cipher.doFinal(bArr);
    }

    public static byte[] b(byte[] bArr, byte[] bArr2) throws InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        int i = 0;
        int length = bArr.length;
        if (length <= 117) {
            return a(bArr, bArr2);
        }
        ArrayList arrayList = new ArrayList(2048);
        byte[] bArr3 = new byte[117];
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            bArr3[i3] = bArr[i4];
            i3++;
            if (i3 == 117 || i4 == length - 1) {
                i2++;
                if (i2 != 1) {
                    for (byte b : a) {
                        arrayList.add(Byte.valueOf(b));
                    }
                }
                byte[] bArrA = a(bArr3, bArr2);
                for (byte b2 : bArrA) {
                    arrayList.add(Byte.valueOf(b2));
                }
                if (i4 == length - 1) {
                    bArr3 = null;
                    i3 = 0;
                } else {
                    bArr3 = new byte[Math.min(117, (length - i4) - 1)];
                    i3 = 0;
                }
            }
        }
        byte[] bArr4 = new byte[arrayList.size()];
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            bArr4[i] = ((Byte) it.next()).byteValue();
            i++;
        }
        return bArr4;
    }
}
