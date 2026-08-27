package com.dcloud.zxing2.aztec;

import com.dcloud.zxing2.BarcodeFormat;
import com.dcloud.zxing2.EncodeHintType;
import com.dcloud.zxing2.Writer;
import com.dcloud.zxing2.aztec.encoder.AztecCode;
import com.dcloud.zxing2.aztec.encoder.Encoder;
import com.dcloud.zxing2.common.BitMatrix;
import java.nio.charset.Charset;
import java.util.Map;
import net.lingala.zip4j.util.InternalZipConstants;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public final class AztecWriter implements Writer {
    private static final Charset DEFAULT_CHARSET = Charset.forName(InternalZipConstants.AES_HASH_CHARSET);

    private static BitMatrix renderResult(AztecCode aztecCode, int i, int i2) {
        BitMatrix matrix = aztecCode.getMatrix();
        if (matrix == null) {
            throw new IllegalStateException();
        }
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int iMax = Math.max(i, width);
        int iMax2 = Math.max(i2, height);
        int iMin = Math.min(iMax / width, iMax2 / height);
        int i3 = (iMax - (width * iMin)) / 2;
        int i4 = (iMax2 - (height * iMin)) / 2;
        BitMatrix bitMatrix = new BitMatrix(iMax, iMax2);
        int i5 = 0;
        while (i5 < height) {
            int i6 = i3;
            int i7 = 0;
            while (i7 < width) {
                if (matrix.get(i7, i5)) {
                    bitMatrix.setRegion(i6, i4, iMin, iMin);
                }
                i7++;
                i6 += iMin;
            }
            i5++;
            i4 += iMin;
        }
        return bitMatrix;
    }

    @Override // com.dcloud.zxing2.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2) {
        return encode(str, barcodeFormat, i, i2, null);
    }

    @Override // com.dcloud.zxing2.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) throws NumberFormatException {
        String str2;
        BarcodeFormat barcodeFormat2;
        int i3;
        int i4;
        Charset charset;
        int i5;
        int i6;
        Charset charsetForName = DEFAULT_CHARSET;
        if (map != null) {
            EncodeHintType encodeHintType = EncodeHintType.CHARACTER_SET;
            if (map.containsKey(encodeHintType)) {
                charsetForName = Charset.forName(map.get(encodeHintType).toString());
            }
            EncodeHintType encodeHintType2 = EncodeHintType.ERROR_CORRECTION;
            int i7 = map.containsKey(encodeHintType2) ? Integer.parseInt(map.get(encodeHintType2).toString()) : 33;
            EncodeHintType encodeHintType3 = EncodeHintType.AZTEC_LAYERS;
            if (map.containsKey(encodeHintType3)) {
                int i8 = Integer.parseInt(map.get(encodeHintType3).toString());
                str2 = str;
                barcodeFormat2 = barcodeFormat;
                i3 = i;
                i4 = i2;
                charset = charsetForName;
                i5 = i7;
                i6 = i8;
                return encode(str2, barcodeFormat2, i3, i4, charset, i5, i6);
            }
            str2 = str;
            barcodeFormat2 = barcodeFormat;
            i3 = i;
            i4 = i2;
            charset = charsetForName;
            i5 = i7;
        } else {
            str2 = str;
            barcodeFormat2 = barcodeFormat;
            i3 = i;
            i4 = i2;
            charset = charsetForName;
            i5 = 33;
        }
        i6 = 0;
        return encode(str2, barcodeFormat2, i3, i4, charset, i5, i6);
    }

    private static BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Charset charset, int i3, int i4) {
        if (barcodeFormat == BarcodeFormat.AZTEC) {
            return renderResult(Encoder.encode(str.getBytes(charset), i3, i4), i, i2);
        }
        throw new IllegalArgumentException("Can only encode AZTEC, but got " + barcodeFormat);
    }
}
