package com.alibaba.fastjson.util;

import com.taobao.weex.el.parse.Operators;
import kotlin.time.InstantKt;

/* loaded from: classes.dex */
public final class RyuFloat {
    private static final int[][] POW5_SPLIT = {new int[]{536870912, 0}, new int[]{671088640, 0}, new int[]{838860800, 0}, new int[]{1048576000, 0}, new int[]{655360000, 0}, new int[]{819200000, 0}, new int[]{1024000000, 0}, new int[]{640000000, 0}, new int[]{800000000, 0}, new int[]{InstantKt.NANOS_PER_SECOND, 0}, new int[]{625000000, 0}, new int[]{781250000, 0}, new int[]{976562500, 0}, new int[]{610351562, 1073741824}, new int[]{762939453, 268435456}, new int[]{953674316, 872415232}, new int[]{596046447, 1619001344}, new int[]{745058059, 1486880768}, new int[]{931322574, 1321730048}, new int[]{582076609, 289210368}, new int[]{727595761, 898383872}, new int[]{909494701, 1659850752}, new int[]{568434188, 1305842176}, new int[]{710542735, 1632302720}, new int[]{888178419, 1503507488}, new int[]{555111512, 671256724}, new int[]{693889390, 839070905}, new int[]{867361737, 2122580455}, new int[]{542101086, 521306416}, new int[]{677626357, 1725374844}, new int[]{847032947, 546105819}, new int[]{1058791184, 145761362}, new int[]{661744490, 91100851}, new int[]{827180612, 1187617888}, new int[]{1033975765, 1484522360}, new int[]{646234853, 1196261931}, new int[]{807793566, 2032198326}, new int[]{1009741958, 1466506084}, new int[]{631088724, 379695390}, new int[]{788860905, 474619238}, new int[]{986076131, 1130144959}, new int[]{616297582, 437905143}, new int[]{770371977, 1621123253}, new int[]{962964972, 415791331}, new int[]{601853107, 1333611405}, new int[]{752316384, 1130143345}, new int[]{940395480, 1412679181}};
    private static final int[][] POW5_INV_SPLIT = {new int[]{268435456, 1}, new int[]{214748364, 1717986919}, new int[]{171798691, 1803886265}, new int[]{137438953, 1013612282}, new int[]{219902325, 1192282922}, new int[]{175921860, 953826338}, new int[]{140737488, 763061070}, new int[]{225179981, 791400982}, new int[]{180143985, 203624056}, new int[]{144115188, 162899245}, new int[]{230584300, 1978625710}, new int[]{184467440, 1582900568}, new int[]{147573952, 1266320455}, new int[]{236118324, 308125809}, new int[]{188894659, 675997377}, new int[]{151115727, 970294631}, new int[]{241785163, 1981968139}, new int[]{193428131, 297084323}, new int[]{154742504, 1955654377}, new int[]{247588007, 1840556814}, new int[]{198070406, 613451992}, new int[]{158456325, 61264864}, new int[]{253530120, 98023782}, new int[]{202824096, 78419026}, new int[]{162259276, 1780722139}, new int[]{259614842, 1990161963}, new int[]{207691874, 733136111}, new int[]{166153499, 1016005619}, new int[]{265845599, 337118801}, new int[]{212676479, 699191770}, new int[]{170141183, 988850146}};

    public static String toString(float f) {
        char[] cArr = new char[15];
        return new String(cArr, 0, toString(f, cArr, 0));
    }

    public static int toString(float f, char[] cArr, int i) {
        int i2;
        boolean z;
        char c;
        char c2;
        int i3;
        int i4;
        int i5;
        int i6;
        boolean z2;
        boolean z3;
        int i7;
        boolean z4;
        int i8;
        int i9;
        int i10;
        long j;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        if (!Float.isNaN(f)) {
            if (f == Float.POSITIVE_INFINITY) {
                cArr[i] = 'I';
                cArr[i + 1] = 'n';
                cArr[i + 2] = 'f';
                cArr[i + 3] = 'i';
                cArr[i + 4] = 'n';
                cArr[i + 5] = 'i';
                cArr[i + 6] = 't';
                i15 = i + 8;
                cArr[i + 7] = 'y';
            } else if (f == Float.NEGATIVE_INFINITY) {
                cArr[i] = '-';
                cArr[i + 1] = 'I';
                cArr[i + 2] = 'n';
                cArr[i + 3] = 'f';
                cArr[i + 4] = 'i';
                cArr[i + 5] = 'n';
                cArr[i + 6] = 'i';
                cArr[i + 7] = 't';
                i16 = i + 9;
                cArr[i + 8] = 'y';
            } else {
                int iFloatToIntBits = Float.floatToIntBits(f);
                if (iFloatToIntBits == 0) {
                    cArr[i] = '0';
                    cArr[i + 1] = Operators.DOT;
                    i16 = i + 3;
                    cArr[i + 2] = '0';
                } else if (iFloatToIntBits == Integer.MIN_VALUE) {
                    cArr[i] = '-';
                    cArr[i + 1] = '0';
                    cArr[i + 2] = Operators.DOT;
                    i15 = i + 4;
                    cArr[i + 3] = '0';
                } else {
                    int i17 = (iFloatToIntBits >> 23) & 255;
                    int i18 = 8388607 & iFloatToIntBits;
                    if (i17 == 0) {
                        i2 = -149;
                    } else {
                        i2 = i17 - 150;
                        i18 |= 8388608;
                    }
                    boolean z5 = iFloatToIntBits < 0;
                    boolean z6 = (i18 & 1) == 0;
                    int i19 = i18 * 4;
                    int i20 = i19 + 2;
                    int i21 = i19 - ((((long) i18) != 8388608 || i17 <= 1) ? 2 : 1);
                    int i22 = i2 - 2;
                    if (i22 >= 0) {
                        c = '-';
                        i6 = (int) ((i22 * 3010299) / 10000000);
                        if (i6 == 0) {
                            i11 = 1;
                            j = 9999999;
                        } else {
                            j = 9999999;
                            i11 = (int) (((i6 * 23219280) + 9999999) / 10000000);
                        }
                        int[][] iArr = POW5_INV_SPLIT;
                        int[] iArr2 = iArr[i6];
                        c2 = '0';
                        long j2 = iArr2[0];
                        long j3 = iArr2[1];
                        long j4 = i19;
                        int i23 = ((i11 + 58) + ((-i22) + i6)) - 31;
                        int i24 = (int) (((j4 * j2) + ((j4 * j3) >> 31)) >> i23);
                        z = z5;
                        long j5 = i20;
                        int i25 = (int) (((j5 * j2) + ((j5 * j3) >> 31)) >> i23);
                        long j6 = i21;
                        int i26 = (int) (((j2 * j6) + ((j6 * j3) >> 31)) >> i23);
                        if (i6 == 0 || (i25 - 1) / 10 > i26 / 10) {
                            i12 = i26;
                            i13 = i25;
                            i14 = 0;
                        } else {
                            int i27 = i6 - 1;
                            i12 = i26;
                            i13 = i25;
                            int i28 = i27 == 0 ? 1 : (int) (((i27 * 23219280) + j) / 10000000);
                            int[] iArr3 = iArr[i27];
                            i14 = (int) ((((iArr3[0] * j4) + ((j4 * iArr3[1]) >> 31)) >> (((r6 - 1) + (i28 + 58)) - 31)) % 10);
                        }
                        int i29 = 0;
                        while (i20 > 0 && i20 % 5 == 0) {
                            i20 /= 5;
                            i29++;
                        }
                        int i30 = 0;
                        while (i19 > 0 && i19 % 5 == 0) {
                            i19 /= 5;
                            i30++;
                        }
                        int i31 = 0;
                        while (i21 > 0 && i21 % 5 == 0) {
                            i21 /= 5;
                            i31++;
                        }
                        z4 = i29 >= i6;
                        boolean z7 = i30 >= i6;
                        z3 = i31 >= i6;
                        i3 = i24;
                        z2 = z7;
                        i7 = i14;
                        i8 = i13;
                        i4 = i12;
                    } else {
                        z = z5;
                        c = '-';
                        c2 = '0';
                        int i32 = -i22;
                        int i33 = (int) ((i32 * 6989700) / 10000000);
                        int i34 = i32 - i33;
                        int i35 = i34 == 0 ? 1 : (int) (((i34 * 23219280) + 9999999) / 10000000);
                        int[][] iArr4 = POW5_SPLIT;
                        int[] iArr5 = iArr4[i34];
                        long j7 = iArr5[0];
                        long j8 = iArr5[1];
                        int i36 = (i33 - (i35 - 61)) - 31;
                        long j9 = i19;
                        i3 = (int) (((j9 * j7) + ((j9 * j8) >> 31)) >> i36);
                        long j10 = i20;
                        int i37 = (int) (((j10 * j7) + ((j10 * j8) >> 31)) >> i36);
                        long j11 = i21;
                        int i38 = (int) (((j7 * j11) + ((j11 * j8) >> 31)) >> i36);
                        if (i33 == 0 || (i37 - 1) / 10 > i38 / 10) {
                            i4 = i38;
                            i5 = 0;
                        } else {
                            int i39 = i34 + 1;
                            int i40 = i33 - 1;
                            int i41 = i39 == 0 ? 1 : (int) (((i39 * 23219280) + 9999999) / 10000000);
                            int[] iArr6 = iArr4[i39];
                            i4 = i38;
                            i5 = (int) ((((iArr6[0] * j9) + ((iArr6[1] * j9) >> 31)) >> ((i40 - (i41 - 61)) - 31)) % 10);
                        }
                        i6 = i33 + i22;
                        boolean z8 = 1 >= i33;
                        z2 = i33 < 23 && (((1 << (i33 + (-1))) - 1) & i19) == 0;
                        z3 = (i21 % 2 == 1 ? 0 : 1) >= i33;
                        i7 = i5;
                        z4 = z8;
                        i8 = i37;
                    }
                    int i42 = InstantKt.NANOS_PER_SECOND;
                    int i43 = 10;
                    while (i43 > 0 && i8 < i42) {
                        i42 /= 10;
                        i43--;
                    }
                    int i44 = i6 + i43;
                    int i45 = i44 - 1;
                    boolean z9 = i45 < -3 || i45 >= 7;
                    if (z4 && !z6) {
                        i8--;
                    }
                    int i46 = 0;
                    while (true) {
                        int i47 = i8 / 10;
                        int i48 = i4 / 10;
                        if (i47 <= i48 || (i8 < 100 && z9)) {
                            break;
                        }
                        z3 &= i4 % 10 == 0;
                        i7 = i3 % 10;
                        i3 /= 10;
                        i46++;
                        i8 = i47;
                        i4 = i48;
                    }
                    if (z3 && z6) {
                        while (i4 % 10 == 0 && (i8 >= 100 || !z9)) {
                            i8 /= 10;
                            i7 = i3 % 10;
                            i3 /= 10;
                            i4 /= 10;
                            i46++;
                        }
                    }
                    if (z2 && i7 == 5 && i3 % 2 == 0) {
                        i7 = 4;
                    }
                    int i49 = i3 + (((i3 != i4 || (z3 && z6)) && i7 < 5) ? 0 : 1);
                    int i50 = i43 - i46;
                    if (z) {
                        i9 = i + 1;
                        cArr[i] = c;
                    } else {
                        i9 = i;
                    }
                    if (z9) {
                        for (int i51 = 0; i51 < i50 - 1; i51++) {
                            int i52 = i49 % 10;
                            i49 /= 10;
                            cArr[(i9 + i50) - i51] = (char) (i52 + 48);
                        }
                        cArr[i9] = (char) ((i49 % 10) + 48);
                        cArr[i9 + 1] = Operators.DOT;
                        int i53 = i9 + i50 + 1;
                        if (i50 == 1) {
                            cArr[i53] = c2;
                            i53++;
                        }
                        int i54 = i53 + 1;
                        cArr[i53] = 'E';
                        if (i45 < 0) {
                            cArr[i54] = c;
                            i45 = -i45;
                            i54 = i53 + 2;
                        }
                        if (i45 >= 10) {
                            cArr[i54] = (char) ((i45 / 10) + 48);
                            i54++;
                        }
                        i10 = i54 + 1;
                        cArr[i54] = (char) ((i45 % 10) + 48);
                    } else if (i45 < 0) {
                        int i55 = i9 + 1;
                        cArr[i9] = c2;
                        int i56 = i9 + 2;
                        cArr[i55] = Operators.DOT;
                        int i57 = -1;
                        while (i57 > i45) {
                            cArr[i56] = c2;
                            i57--;
                            i56++;
                        }
                        int i58 = i56;
                        for (int i59 = 0; i59 < i50; i59++) {
                            cArr[((i56 + i50) - i59) - 1] = (char) ((i49 % 10) + 48);
                            i49 /= 10;
                            i58++;
                        }
                        i10 = i58;
                    } else if (i44 >= i50) {
                        for (int i60 = 0; i60 < i50; i60++) {
                            cArr[((i9 + i50) - i60) - 1] = (char) ((i49 % 10) + 48);
                            i49 /= 10;
                        }
                        int i61 = i9 + i50;
                        while (i50 < i44) {
                            cArr[i61] = c2;
                            i50++;
                            i61++;
                        }
                        int i62 = i61 + 1;
                        cArr[i61] = Operators.DOT;
                        i10 = i61 + 2;
                        cArr[i62] = c2;
                    } else {
                        int i63 = i9 + 1;
                        for (int i64 = 0; i64 < i50; i64++) {
                            if ((i50 - i64) - 1 == i45) {
                                cArr[((i63 + i50) - i64) - 1] = Operators.DOT;
                                i63--;
                            }
                            cArr[((i63 + i50) - i64) - 1] = (char) ((i49 % 10) + 48);
                            i49 /= 10;
                        }
                        i10 = i9 + i50 + 1;
                    }
                    return i10 - i;
                }
            }
            return i15 - i;
        }
        cArr[i] = 'N';
        cArr[i + 1] = 'a';
        i16 = i + 3;
        cArr[i + 2] = 'N';
        return i16 - i;
    }
}
