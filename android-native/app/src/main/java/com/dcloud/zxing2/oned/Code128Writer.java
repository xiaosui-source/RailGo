package com.dcloud.zxing2.oned;

import com.dcloud.zxing2.BarcodeFormat;
import com.dcloud.zxing2.EncodeHintType;
import com.dcloud.zxing2.WriterException;
import com.dcloud.zxing2.common.BitMatrix;
import java.util.ArrayList;
import java.util.Map;
import org.mozilla.universalchardet.prober.HebrewProber;
import org.mozilla.universalchardet.prober.contextanalysis.SJISContextAnalysis;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public final class Code128Writer extends OneDimensionalCodeWriter {
    private static final int CODE_CODE_B = 100;
    private static final int CODE_CODE_C = 99;
    private static final int CODE_FNC_1 = 102;
    private static final int CODE_FNC_2 = 97;
    private static final int CODE_FNC_3 = 96;
    private static final int CODE_FNC_4_B = 100;
    private static final int CODE_START_B = 104;
    private static final int CODE_START_C = 105;
    private static final int CODE_STOP = 106;
    private static final char ESCAPE_FNC_1 = 241;
    private static final char ESCAPE_FNC_2 = 242;
    private static final char ESCAPE_FNC_3 = 243;
    private static final char ESCAPE_FNC_4 = 244;

    private static boolean isDigits(CharSequence charSequence, int i, int i2) {
        int i3 = i2 + i;
        int length = charSequence.length();
        while (i < i3 && i < length) {
            char cCharAt = charSequence.charAt(i);
            if (cCharAt < '0' || cCharAt > '9') {
                if (cCharAt != 241) {
                    return false;
                }
                i3++;
            }
            i++;
        }
        return i3 <= length;
    }

    @Override // com.dcloud.zxing2.oned.OneDimensionalCodeWriter, com.dcloud.zxing2.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.CODE_128) {
            return super.encode(str, barcodeFormat, i, i2, map);
        }
        throw new IllegalArgumentException("Can only encode CODE_128, but got " + barcodeFormat);
    }

    @Override // com.dcloud.zxing2.oned.OneDimensionalCodeWriter
    public boolean[] encode(String str) throws NumberFormatException {
        int length = str.length();
        if (length >= 1 && length <= 80) {
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                char cCharAt = str.charAt(i2);
                if (cCharAt < ' ' || cCharAt > '~') {
                    switch (cCharAt) {
                        case SJISContextAnalysis.HIRAGANA_LOWBYTE_END /* 241 */:
                        case 242:
                        case 243:
                        case HebrewProber.NORMAL_PE /* 244 */:
                            break;
                        default:
                            throw new IllegalArgumentException("Bad character in input: " + cCharAt);
                    }
                }
            }
            ArrayList arrayList = new ArrayList();
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            int i6 = 1;
            while (i3 < length) {
                int i7 = CODE_CODE_C;
                int iCharAt = 100;
                if (!isDigits(str, i3, i5 == CODE_CODE_C ? 2 : 4)) {
                    i7 = 100;
                }
                if (i7 == i5) {
                    switch (str.charAt(i3)) {
                        case SJISContextAnalysis.HIRAGANA_LOWBYTE_END /* 241 */:
                            iCharAt = 102;
                            break;
                        case 242:
                            iCharAt = 97;
                            break;
                        case 243:
                            iCharAt = 96;
                            break;
                        case HebrewProber.NORMAL_PE /* 244 */:
                            break;
                        default:
                            if (i5 != 100) {
                                iCharAt = Integer.parseInt(str.substring(i3, i3 + 2));
                                i3++;
                                break;
                            } else {
                                iCharAt = str.charAt(i3) - ' ';
                                break;
                            }
                    }
                    i3++;
                } else {
                    iCharAt = i5 == 0 ? i7 == 100 ? 104 : CODE_START_C : i7;
                    i5 = i7;
                }
                arrayList.add(Code128Reader.CODE_PATTERNS[iCharAt]);
                i4 += iCharAt * i6;
                if (i3 != 0) {
                    i6++;
                }
            }
            int[][] iArr = Code128Reader.CODE_PATTERNS;
            arrayList.add(iArr[i4 % 103]);
            arrayList.add(iArr[CODE_STOP]);
            int size = arrayList.size();
            int i8 = 0;
            int i9 = 0;
            while (i8 < size) {
                Object obj = arrayList.get(i8);
                i8++;
                for (int i10 : (int[]) obj) {
                    i9 += i10;
                }
            }
            boolean[] zArr = new boolean[i9];
            int size2 = arrayList.size();
            int iAppendPattern = 0;
            while (i < size2) {
                Object obj2 = arrayList.get(i);
                i++;
                iAppendPattern += OneDimensionalCodeWriter.appendPattern(zArr, iAppendPattern, (int[]) obj2, true);
            }
            return zArr;
        }
        throw new IllegalArgumentException("Contents length should be between 1 and 80 characters, but got " + length);
    }
}
