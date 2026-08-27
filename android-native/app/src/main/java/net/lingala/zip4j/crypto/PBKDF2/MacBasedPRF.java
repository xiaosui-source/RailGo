package net.lingala.zip4j.crypto.PBKDF2;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public class MacBasedPRF implements PRF {
    private int hLen;
    private Mac mac;
    private String macAlgorithm;
    private ByteArrayOutputStream macCache = new ByteArrayOutputStream(4096);

    public MacBasedPRF(String str) throws NoSuchAlgorithmException {
        this.macAlgorithm = str;
        try {
            Mac mac = Mac.getInstance(str);
            this.mac = mac;
            this.hLen = mac.getMacLength();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // net.lingala.zip4j.crypto.PBKDF2.PRF
    public byte[] doFinal(byte[] bArr) throws IllegalStateException {
        if (this.macCache.size() > 0) {
            doMacUpdate(0);
        }
        return this.mac.doFinal(bArr);
    }

    public byte[] doFinal() {
        return doFinal(0);
    }

    public byte[] doFinal(int i) throws IllegalStateException {
        if (this.macCache.size() > 0) {
            doMacUpdate(i);
        }
        return this.mac.doFinal();
    }

    @Override // net.lingala.zip4j.crypto.PBKDF2.PRF
    public int getHLen() {
        return this.hLen;
    }

    @Override // net.lingala.zip4j.crypto.PBKDF2.PRF
    public void init(byte[] bArr) throws InvalidKeyException {
        try {
            this.mac.init(new SecretKeySpec(bArr, this.macAlgorithm));
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(byte[] bArr) {
        update(bArr, 0, bArr.length);
    }

    public void update(byte[] bArr, int i, int i2) {
        try {
            if (this.macCache.size() + i2 > 4096) {
                doMacUpdate(0);
            }
            this.macCache.write(bArr, i, i2);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }

    private void doMacUpdate(int i) throws IllegalStateException {
        byte[] byteArray = this.macCache.toByteArray();
        int length = byteArray.length - i;
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 + 16;
            this.mac.update(byteArray, i2, i3 <= length ? 16 : length - i2);
            i2 = i3;
        }
        this.macCache.reset();
    }
}
