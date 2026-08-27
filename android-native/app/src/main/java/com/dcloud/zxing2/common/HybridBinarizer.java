package com.dcloud.zxing2.common;

import com.dcloud.zxing2.Binarizer;
import com.dcloud.zxing2.LuminanceSource;
import com.dcloud.zxing2.NotFoundException;
import java.lang.reflect.Array;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public final class HybridBinarizer extends GlobalHistogramBinarizer {
    private static final int BLOCK_SIZE = 8;
    private static final int BLOCK_SIZE_MASK = 7;
    private static final int BLOCK_SIZE_POWER = 3;
    private static final int MINIMUM_DIMENSION = 40;
    private static final int MIN_DYNAMIC_RANGE = 24;
    private BitMatrix matrix;

    public HybridBinarizer(LuminanceSource luminanceSource) {
        super(luminanceSource);
    }

    private static int[][] calculateBlackPoints(byte[] bArr, int i, int i2, int i3, int i4) {
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, i2, i);
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = i5 << 3;
            int i7 = i4 - 8;
            if (i6 > i7) {
                i6 = i7;
            }
            for (int i8 = 0; i8 < i; i8++) {
                int i9 = i8 << 3;
                int i10 = i3 - 8;
                if (i9 > i10) {
                    i9 = i10;
                }
                int i11 = (i6 * i3) + i9;
                int i12 = 0;
                int i13 = 0;
                int i14 = 255;
                int i15 = 0;
                while (i12 < 8) {
                    for (int i16 = 0; i16 < 8; i16++) {
                        int i17 = bArr[i11 + i16] & 255;
                        i15 += i17;
                        if (i17 < i14) {
                            i14 = i17;
                        }
                        if (i17 > i13) {
                            i13 = i17;
                        }
                    }
                    if (i13 - i14 > 24) {
                        while (true) {
                            i12++;
                            i11 += i3;
                            if (i12 < 8) {
                                for (int i18 = 0; i18 < 8; i18++) {
                                    i15 += bArr[i11 + i18] & 255;
                                }
                            }
                        }
                    }
                    i12++;
                    i11 += i3;
                }
                int i19 = i15 >> 6;
                if (i13 - i14 <= 24) {
                    i19 = i14 / 2;
                    if (i5 > 0 && i8 > 0) {
                        int[] iArr2 = iArr[i5 - 1];
                        int i20 = i8 - 1;
                        int i21 = ((iArr2[i8] + (iArr[i5][i20] * 2)) + iArr2[i20]) / 4;
                        if (i14 < i21) {
                            i19 = i21;
                        }
                    }
                }
                iArr[i5][i8] = i19;
            }
        }
        return iArr;
    }

    private static void calculateThresholdForBlock(byte[] bArr, int i, int i2, int i3, int i4, int[][] iArr, BitMatrix bitMatrix) {
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = i5 << 3;
            int i7 = i4 - 8;
            int i8 = i6 > i7 ? i7 : i6;
            for (int i9 = 0; i9 < i; i9++) {
                int i10 = i9 << 3;
                int i11 = i3 - 8;
                int i12 = i10 > i11 ? i11 : i10;
                int iCap = cap(i9, 2, i - 3);
                int iCap2 = cap(i5, 2, i2 - 3);
                int i13 = 0;
                for (int i14 = -2; i14 <= 2; i14++) {
                    int[] iArr2 = iArr[iCap2 + i14];
                    i13 += iArr2[iCap - 2] + iArr2[iCap - 1] + iArr2[iCap] + iArr2[iCap + 1] + iArr2[iCap + 2];
                }
                thresholdBlock(bArr, i12, i8, i13 / 25, i3, bitMatrix);
            }
        }
    }

    private static int cap(int i, int i2, int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i;
    }

    private static void thresholdBlock(byte[] bArr, int i, int i2, int i3, int i4, BitMatrix bitMatrix) {
        int i5 = (i2 * i4) + i;
        int i6 = 0;
        while (i6 < 8) {
            for (int i7 = 0; i7 < 8; i7++) {
                if ((bArr[i5 + i7] & 255) <= i3) {
                    bitMatrix.set(i + i7, i2 + i6);
                }
            }
            i6++;
            i5 += i4;
        }
    }

    @Override // com.dcloud.zxing2.common.GlobalHistogramBinarizer, com.dcloud.zxing2.Binarizer
    public Binarizer createBinarizer(LuminanceSource luminanceSource) {
        return new HybridBinarizer(luminanceSource);
    }

    @Override // com.dcloud.zxing2.common.GlobalHistogramBinarizer, com.dcloud.zxing2.Binarizer
    public BitMatrix getBlackMatrix() throws NotFoundException {
        BitMatrix bitMatrix = this.matrix;
        if (bitMatrix != null) {
            return bitMatrix;
        }
        LuminanceSource luminanceSource = getLuminanceSource();
        int width = luminanceSource.getWidth();
        int height = luminanceSource.getHeight();
        if (width < 40 || height < 40) {
            this.matrix = super.getBlackMatrix();
        } else {
            byte[] matrix = luminanceSource.getMatrix();
            int i = width >> 3;
            if ((width & 7) != 0) {
                i++;
            }
            int i2 = i;
            int i3 = height >> 3;
            if ((height & 7) != 0) {
                i3++;
            }
            int i4 = i3;
            int[][] iArrCalculateBlackPoints = calculateBlackPoints(matrix, i2, i4, width, height);
            BitMatrix bitMatrix2 = new BitMatrix(width, height);
            calculateThresholdForBlock(matrix, i2, i4, width, height, iArrCalculateBlackPoints, bitMatrix2);
            this.matrix = bitMatrix2;
        }
        return this.matrix;
    }
}
