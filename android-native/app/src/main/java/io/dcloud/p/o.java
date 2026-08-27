package io.dcloud.p;

import android.os.Build;
import android.os.Process;
import android.util.Base64;
import com.facebook.imagepipeline.memory.BitmapCounterConfig;
import io.dcloud.common.util.AESUtil;
import io.dcloud.common.util.StringUtil;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.SecureRandomSpi;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
abstract class o {
    static final AtomicBoolean a = new AtomicBoolean(false);

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class a {
        private final byte[] a;
        private final byte[] b;
        private final byte[] c;

        public a(byte[] bArr, byte[] bArr2, byte[] bArr3) {
            byte[] bArr4 = new byte[bArr.length];
            this.a = bArr4;
            System.arraycopy(bArr, 0, bArr4, 0, bArr.length);
            byte[] bArr5 = new byte[bArr2.length];
            this.b = bArr5;
            System.arraycopy(bArr2, 0, bArr5, 0, bArr2.length);
            byte[] bArr6 = new byte[bArr3.length];
            this.c = bArr6;
            System.arraycopy(bArr3, 0, bArr6, 0, bArr3.length);
        }

        public byte[] a() {
            return this.a;
        }

        public byte[] b() {
            return this.b;
        }

        public byte[] c() {
            return this.c;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            a aVar = (a) obj;
            return Arrays.equals(this.a, aVar.a) && Arrays.equals(this.b, aVar.b) && Arrays.equals(this.c, aVar.c);
        }

        public int hashCode() {
            return ((((Arrays.hashCode(this.a) + 31) * 31) + Arrays.hashCode(this.b)) * 31) + Arrays.hashCode(this.c);
        }

        public String toString() {
            String strEncodeToString = Base64.encodeToString(this.b, 2);
            String strEncodeToString2 = Base64.encodeToString(this.a, 2);
            return StringUtil.format(strEncodeToString + ":" + Base64.encodeToString(this.c, 2) + ":" + strEncodeToString2, new Object[0]);
        }

        public static byte[] a(byte[] bArr, byte[] bArr2) {
            byte[] bArr3 = new byte[bArr.length + bArr2.length];
            System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
            System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
            return bArr3;
        }

        public a(String str) {
            String[] strArrSplit = str.split(":");
            if (strArrSplit.length == 3) {
                this.b = Base64.decode(strArrSplit[0], 2);
                this.c = Base64.decode(strArrSplit[1], 2);
                this.a = Base64.decode(strArrSplit[2], 2);
                return;
            }
            throw new IllegalArgumentException("Cannot parse iv:ciphertext:mac");
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static final class b {
        private static final byte[] a = d();

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        public static class a extends SecureRandomSpi {
        }

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        /* renamed from: io.dcloud.p.o$b$b, reason: collision with other inner class name */
        private static class C0067b extends Provider {
            public C0067b() {
                super("LinuxPRNG", 1.0d, "A Linux-specific random number provider that uses /dev/urandom");
                put("SecureRandom.SHA1PRNG", a.class.getName());
                put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
            }
        }

        public static void a() {
            b();
            f();
        }

        private static void b() {
        }

        private static byte[] c() throws IOException {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                dataOutputStream.writeLong(System.currentTimeMillis());
                dataOutputStream.writeLong(System.nanoTime());
                dataOutputStream.writeInt(Process.myPid());
                dataOutputStream.writeInt(Process.myUid());
                dataOutputStream.write(a);
                dataOutputStream.close();
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                throw new SecurityException("Failed to generate seed", e);
            }
        }

        private static byte[] d() {
            StringBuilder sb = new StringBuilder();
            String str = Build.FINGERPRINT;
            if (str != null) {
                sb.append(str);
            }
            String strE = e();
            if (strE != null) {
                sb.append(strE);
            }
            try {
                return sb.toString().getBytes("UTF-8");
            } catch (UnsupportedEncodingException unused) {
                throw new RuntimeException("UTF-8 encoding not supported");
            }
        }

        private static String e() {
            return null;
        }

        private static void f() {
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class c {
        private Key a;
        private Key b;

        public c(Key key, Key key2) {
            a(key);
            b(key2);
        }

        public Key a() {
            return this.a;
        }

        public Key b() {
            return this.b;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            c cVar = (c) obj;
            return this.b.equals(cVar.b) && this.a.equals(cVar.a);
        }

        public int hashCode() {
            return ((this.a.hashCode() + 31) * 31) + this.b.hashCode();
        }

        public String toString() {
            return Base64.encodeToString(a().getEncoded(), 2) + ":" + Base64.encodeToString(b().getEncoded(), 2);
        }

        public void a(Key key) {
            this.a = key;
        }

        public void b(Key key) {
            this.b = key;
        }
    }

    public static c a(String str) throws InvalidKeyException {
        String[] strArrSplit = str.split(":");
        if (strArrSplit.length != 2) {
            throw new IllegalArgumentException("Cannot parse aesKey:hmacKey");
        }
        byte[] bArrDecode = Base64.decode(strArrSplit[0], 2);
        if (bArrDecode.length != 16) {
            throw new InvalidKeyException("Base64 decoded key is not 128 bytes");
        }
        byte[] bArrDecode2 = Base64.decode(strArrSplit[1], 2);
        if (bArrDecode2.length == 32) {
            return new c(new SecretKeySpec(bArrDecode, 0, bArrDecode.length, "AES"), new SecretKeySpec(bArrDecode2, d()));
        }
        throw new InvalidKeyException("Base64 decoded key is not 256 bytes");
    }

    public static byte[] b() {
        return a(16);
    }

    public static c c() throws NoSuchAlgorithmException {
        a();
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return new c(keyGenerator.generateKey(), new SecretKeySpec(a(32), d()));
    }

    private static String d() {
        return io.dcloud.common.util.Base64.decodeString("##U1d4Z1lsSkpRRE0wTnc9PSo2YTNkODhmYS00YmEwLTQ3OWYtOTQyMi1lNWFhYmUxNTg5N2I2NQ==", true, 1);
    }

    public static String b(a aVar, c cVar) {
        return a(aVar, cVar, "UTF-8");
    }

    public static c a(String str, byte[] bArr, int i) {
        a();
        byte[] encoded = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(str.toCharArray(), bArr, i, BitmapCounterConfig.DEFAULT_MAX_BITMAP_COUNT)).getEncoded();
        return new c(new SecretKeySpec(a(encoded, 0, 16), "AES"), new SecretKeySpec(a(encoded, 16, 48), d()));
    }

    private static byte[] a(int i) {
        a();
        byte[] bArr = new byte[i];
        new SecureRandom().nextBytes(bArr);
        return bArr;
    }

    public static a a(String str, c cVar) {
        return a(str, cVar, "UTF-8");
    }

    public static a a(String str, c cVar, String str2) {
        return a(str.getBytes(str2), cVar);
    }

    public static a a(byte[] bArr, c cVar) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] bArrB = b();
        Cipher cipher = Cipher.getInstance(AESUtil.getDefaultTransformation());
        cipher.init(1, cVar.a(), new IvParameterSpec(bArrB));
        byte[] iv = cipher.getIV();
        byte[] bArrDoFinal = cipher.doFinal(bArr);
        return new a(bArrDoFinal, iv, a(a.a(iv, bArrDoFinal), cVar.b()));
    }

    private static void a() {
        AtomicBoolean atomicBoolean = a;
        if (atomicBoolean.get()) {
            return;
        }
        synchronized (b.class) {
            if (!atomicBoolean.get()) {
                b.a();
                atomicBoolean.set(true);
            }
        }
    }

    public static String a(a aVar, c cVar, String str) {
        return new String(a(aVar, cVar), str);
    }

    public static byte[] a(a aVar, c cVar) throws GeneralSecurityException {
        if (a(a(a.a(aVar.b(), aVar.a()), cVar.b()), aVar.c())) {
            Cipher cipher = Cipher.getInstance(AESUtil.getDefaultTransformation());
            cipher.init(2, cVar.a(), new IvParameterSpec(aVar.b()));
            return cipher.doFinal(aVar.a());
        }
        throw new GeneralSecurityException("MAC stored in civ does not match computed MAC.");
    }

    public static byte[] a(byte[] bArr, Key key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(d());
        mac.init(key);
        return mac.doFinal(bArr);
    }

    public static boolean a(byte[] bArr, byte[] bArr2) {
        if (bArr.length != bArr2.length) {
            return false;
        }
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            i |= bArr[i2] ^ bArr2[i2];
        }
        return i == 0;
    }

    private static byte[] a(byte[] bArr, int i, int i2) {
        int i3 = i2 - i;
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, i, bArr2, 0, i3);
        return bArr2;
    }
}
