package com.dcloud.zxing2.datamatrix.encoder;

import androidx.appcompat.app.AppCompatDelegate;
import com.alibaba.fastjson.asm.Opcodes;
import com.dmcbig.mediapicker.PickerConfig;
import com.facebook.imageutils.JfifUtil;
import kotlinx.coroutines.scheduling.WorkQueueKt;
import org.mozilla.universalchardet.prober.CharsetProber;
import org.mozilla.universalchardet.prober.HebrewProber;
import org.mozilla.universalchardet.prober.contextanalysis.EUCJPContextAnalysis;
import org.mozilla.universalchardet.prober.contextanalysis.SJISContextAnalysis;
import org.mozilla.universalchardet.prober.distributionanalysis.EUCTWDistributionAnalysis;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public final class ErrorCorrection {
    private static final int MODULO_VALUE = 301;
    private static final int[] FACTOR_SETS = {5, 7, 10, 11, 12, 14, 18, 20, 24, 28, 36, 42, 48, 56, 62, 68};
    private static final int[][] FACTORS = {new int[]{228, 48, 15, 111, 62}, new int[]{23, 68, 144, 134, HebrewProber.NORMAL_NUN, 92, 254}, new int[]{28, 24, Opcodes.INVOKEINTERFACE, Opcodes.IF_ACMPNE, 223, 248, 116, 255, 110, 61}, new int[]{175, 138, 205, 12, 194, 168, 39, HebrewProber.FINAL_TSADI, 60, 97, 120}, new int[]{41, Opcodes.IFEQ, Opcodes.IFLE, 91, 61, 42, EUCJPContextAnalysis.SINGLE_SHIFT_2, 213, 97, Opcodes.GETSTATIC, 100, 242}, new int[]{156, 97, 192, 252, 95, 9, 157, 119, 138, 45, 18, 186, 83, Opcodes.INVOKEINTERFACE}, new int[]{83, 195, 100, 39, Opcodes.NEWARRAY, 75, 66, 61, SJISContextAnalysis.HIRAGANA_LOWBYTE_END, 213, AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY, 129, 94, 254, JfifUtil.MARKER_APP1, 48, 90, Opcodes.NEWARRAY}, new int[]{15, 195, HebrewProber.NORMAL_PE, 9, 233, 71, 168, 2, Opcodes.NEWARRAY, Opcodes.IF_ICMPNE, Opcodes.IFEQ, 145, 253, 79, AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR, 82, 27, 174, 186, 172}, new int[]{52, 190, 88, 205, AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY, 39, 176, 21, 155, 197, 251, 223, 155, 21, 5, 172, 254, 124, 12, Opcodes.PUTFIELD, Opcodes.INVOKESTATIC, 96, 50, Opcodes.INSTANCEOF}, new int[]{211, 231, 43, 97, 71, 96, 103, 174, 37, Opcodes.DCMPL, 170, 53, 75, 34, 249, 121, 17, 138, 110, 213, 141, 136, 120, Opcodes.DCMPL, 233, 168, 93, 255}, new int[]{HebrewProber.FINAL_TSADI, WorkQueueKt.MASK, 242, JfifUtil.MARKER_SOS, SJISContextAnalysis.HIRAGANA_HIGHBYTE, 250, Opcodes.IF_ICMPGE, Opcodes.PUTFIELD, 102, 120, 84, 179, 220, 251, 80, Opcodes.INVOKEVIRTUAL, 229, 18, 2, 4, 68, 33, PickerConfig.PICKER_IMAGE_VIDEO, 137, 95, 119, 115, 44, 175, Opcodes.INVOKESTATIC, 59, 25, JfifUtil.MARKER_APP1, 98, 81, 112}, new int[]{77, Opcodes.INSTANCEOF, 137, 31, 19, 38, 22, Opcodes.IFEQ, 247, 105, CharsetProber.ASCII_Z, 2, HebrewProber.FINAL_TSADI, 133, 242, 8, 175, 95, 100, 9, Opcodes.GOTO, 105, 214, 111, 57, 121, 21, 1, 253, 57, 54, PickerConfig.PICKER_IMAGE_VIDEO, 248, 202, 69, 50, 150, Opcodes.RETURN, 226, 5, 9, 5}, new int[]{HebrewProber.FINAL_TSADI, 132, 172, 223, 96, 32, 117, 22, HebrewProber.NORMAL_MEM, 133, HebrewProber.NORMAL_MEM, 231, 205, Opcodes.NEWARRAY, HebrewProber.FINAL_MEM, 87, 191, 106, 16, 147, 118, 23, 37, 90, 170, 205, 131, 88, 120, 100, 66, 138, 186, HebrewProber.NORMAL_NUN, 82, 44, 176, 87, Opcodes.NEW, 147, Opcodes.IF_ICMPNE, 175, 69, 213, 92, 253, JfifUtil.MARKER_APP1, 19}, new int[]{175, 9, 223, HebrewProber.NORMAL_MEM, 12, 17, 220, JfifUtil.MARKER_RST0, 100, 29, 175, 170, 230, 192, JfifUtil.MARKER_RST7, HebrewProber.NORMAL_KAF, 150, 159, 36, 223, 38, 200, 132, 54, 228, 146, JfifUtil.MARKER_SOS, HebrewProber.FINAL_KAF, 117, 203, 29, 232, 144, HebrewProber.NORMAL_MEM, 22, 150, PickerConfig.CODE_PICKER_CROP, 117, 62, 207, 164, 13, 137, HebrewProber.FINAL_TSADI, WorkQueueKt.MASK, 67, 247, 28, 155, 43, 203, 107, 233, 53, EUCJPContextAnalysis.SINGLE_SHIFT_3, 46}, new int[]{242, 93, Opcodes.RET, 50, 144, 210, 39, 118, 202, Opcodes.NEWARRAY, PickerConfig.CODE_PICKER_CROP, 189, EUCJPContextAnalysis.SINGLE_SHIFT_3, AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR, EUCTWDistributionAnalysis.HIGHBYTE_BEGIN, 37, Opcodes.INVOKEINTERFACE, 112, 134, 230, HebrewProber.FINAL_TSADI, 63, 197, 190, 250, 106, Opcodes.INVOKEINTERFACE, 221, 175, 64, 114, 71, 161, 44, 147, 6, 27, JfifUtil.MARKER_SOS, 51, 63, 87, 10, 40, SJISContextAnalysis.HIRAGANA_HIGHBYTE, Opcodes.NEWARRAY, 17, Opcodes.IF_ICMPGT, 31, 176, 170, 4, 107, 232, 7, 94, Opcodes.IF_ACMPNE, 224, 124, 86, 47, 11, 204}, new int[]{220, 228, 173, 89, 251, Opcodes.FCMPL, 159, 56, 89, 33, 147, HebrewProber.NORMAL_PE, Opcodes.IFNE, 36, 73, WorkQueueKt.MASK, 213, 136, 248, 180, HebrewProber.FINAL_KAF, 197, Opcodes.IFLE, Opcodes.RETURN, 68, CharsetProber.ASCII_Z, 93, 213, 15, Opcodes.IF_ICMPNE, 227, 236, 66, 139, Opcodes.IFEQ, Opcodes.INVOKEINTERFACE, 202, Opcodes.GOTO, 179, 25, 220, 232, 96, 210, 231, 136, 223, 239, Opcodes.PUTFIELD, SJISContextAnalysis.HIRAGANA_LOWBYTE_END, 59, 52, 172, 25, 49, 232, 211, 189, 64, 54, AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR, Opcodes.IFEQ, 132, 63, 96, 103, 82, 186}};
    private static final int[] LOG = new int[256];
    private static final int[] ALOG = new int[255];

    static {
        int i = 1;
        for (int i2 = 0; i2 < 255; i2++) {
            ALOG[i2] = i;
            LOG[i] = i2;
            i *= 2;
            if (i >= 256) {
                i ^= MODULO_VALUE;
            }
        }
    }

    private ErrorCorrection() {
    }

    private static String createECCBlock(CharSequence charSequence, int i) {
        return createECCBlock(charSequence, 0, charSequence.length(), i);
    }

    public static String encodeECC200(String str, SymbolInfo symbolInfo) {
        if (str.length() != symbolInfo.getDataCapacity()) {
            throw new IllegalArgumentException("The number of codewords does not match the selected symbol");
        }
        StringBuilder sb = new StringBuilder(symbolInfo.getDataCapacity() + symbolInfo.getErrorCodewords());
        sb.append(str);
        int interleavedBlockCount = symbolInfo.getInterleavedBlockCount();
        if (interleavedBlockCount == 1) {
            sb.append(createECCBlock(str, symbolInfo.getErrorCodewords()));
        } else {
            sb.setLength(sb.capacity());
            int[] iArr = new int[interleavedBlockCount];
            int[] iArr2 = new int[interleavedBlockCount];
            int[] iArr3 = new int[interleavedBlockCount];
            int i = 0;
            while (i < interleavedBlockCount) {
                int i2 = i + 1;
                iArr[i] = symbolInfo.getDataLengthForInterleavedBlock(i2);
                iArr2[i] = symbolInfo.getErrorLengthForInterleavedBlock(i2);
                iArr3[i] = 0;
                if (i > 0) {
                    iArr3[i] = iArr3[i - 1] + iArr[i];
                }
                i = i2;
            }
            for (int i3 = 0; i3 < interleavedBlockCount; i3++) {
                StringBuilder sb2 = new StringBuilder(iArr[i3]);
                for (int i4 = i3; i4 < symbolInfo.getDataCapacity(); i4 += interleavedBlockCount) {
                    sb2.append(str.charAt(i4));
                }
                String strCreateECCBlock = createECCBlock(sb2.toString(), iArr2[i3]);
                int i5 = i3;
                int i6 = 0;
                while (i5 < iArr2[i3] * interleavedBlockCount) {
                    sb.setCharAt(symbolInfo.getDataCapacity() + i5, strCreateECCBlock.charAt(i6));
                    i5 += interleavedBlockCount;
                    i6++;
                }
            }
        }
        return sb.toString();
    }

    private static String createECCBlock(CharSequence charSequence, int i, int i2, int i3) {
        int i4;
        int i5;
        int i6 = 0;
        while (true) {
            int[] iArr = FACTOR_SETS;
            if (i6 >= iArr.length) {
                i6 = -1;
                break;
            }
            if (iArr[i6] == i3) {
                break;
            }
            i6++;
        }
        if (i6 < 0) {
            throw new IllegalArgumentException("Illegal number of error correction codewords specified: " + i3);
        }
        int[] iArr2 = FACTORS[i6];
        char[] cArr = new char[i3];
        for (int i7 = 0; i7 < i3; i7++) {
            cArr[i7] = 0;
        }
        for (int i8 = i; i8 < i + i2; i8++) {
            int i9 = i3 - 1;
            int iCharAt = cArr[i9] ^ charSequence.charAt(i8);
            while (i9 > 0) {
                if (iCharAt == 0 || (i5 = iArr2[i9]) == 0) {
                    cArr[i9] = cArr[i9 - 1];
                } else {
                    char c = cArr[i9 - 1];
                    int[] iArr3 = ALOG;
                    int[] iArr4 = LOG;
                    cArr[i9] = (char) (iArr3[(iArr4[iCharAt] + iArr4[i5]) % 255] ^ c);
                }
                i9--;
            }
            if (iCharAt == 0 || (i4 = iArr2[0]) == 0) {
                cArr[0] = 0;
            } else {
                int[] iArr5 = ALOG;
                int[] iArr6 = LOG;
                cArr[0] = (char) iArr5[(iArr6[iCharAt] + iArr6[i4]) % 255];
            }
        }
        char[] cArr2 = new char[i3];
        for (int i10 = 0; i10 < i3; i10++) {
            cArr2[i10] = cArr[(i3 - i10) - 1];
        }
        return String.valueOf(cArr2);
    }
}
