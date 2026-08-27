package kotlin.collections;

import kotlin.Metadata;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UArraySorting.kt */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0006\u0010\u0007\u001a'\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\n\u0010\u000b\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\r\u0010\u000e\u001a'\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u000f\u0010\u0010\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0012\u0010\u0013\u001a'\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0014\u0010\u0015\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00162\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0017\u0010\u0018\u001a'\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00162\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0019\u0010\u001a\u001a'\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b\u001e\u0010\u000b\u001a'\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b\u001f\u0010\u0010\u001a'\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b \u0010\u0015\u001a'\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b!\u0010\u001a¨\u0006\""}, d2 = {"partition", "", "array", "Lkotlin/UByteArray;", "left", "right", "partition-4UcCI2c", "([BII)I", "quickSort", "", "quickSort-4UcCI2c", "([BII)V", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort-Aa5vz7o", "([SII)V", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "quickSort-oBK06Vg", "([III)V", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "quickSort--nroSd4", "([JII)V", "sortArray", "fromIndex", "toIndex", "sortArray-4UcCI2c", "sortArray-Aa5vz7o", "sortArray-oBK06Vg", "sortArray--nroSd4", "kotlin-stdlib"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UArraySortingKt {
    /* renamed from: partition-4UcCI2c, reason: not valid java name */
    private static final int m996partition4UcCI2c(byte[] bArr, int i, int i2) {
        int i3;
        byte bM612getw2LRezQ = UByteArray.m612getw2LRezQ(bArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                i3 = bM612getw2LRezQ & 255;
                if (Intrinsics.compare(UByteArray.m612getw2LRezQ(bArr, i) & 255, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UByteArray.m612getw2LRezQ(bArr, i2) & 255, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                byte bM612getw2LRezQ2 = UByteArray.m612getw2LRezQ(bArr, i);
                UByteArray.m617setVurrAj0(bArr, i, UByteArray.m612getw2LRezQ(bArr, i2));
                UByteArray.m617setVurrAj0(bArr, i2, bM612getw2LRezQ2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort-4UcCI2c, reason: not valid java name */
    private static final void m1000quickSort4UcCI2c(byte[] bArr, int i, int i2) {
        int iM996partition4UcCI2c = m996partition4UcCI2c(bArr, i, i2);
        int i3 = iM996partition4UcCI2c - 1;
        if (i < i3) {
            m1000quickSort4UcCI2c(bArr, i, i3);
        }
        if (iM996partition4UcCI2c < i2) {
            m1000quickSort4UcCI2c(bArr, iM996partition4UcCI2c, i2);
        }
    }

    /* renamed from: partition-Aa5vz7o, reason: not valid java name */
    private static final int m997partitionAa5vz7o(short[] sArr, int i, int i2) {
        int i3;
        short sM875getMh2AYeg = UShortArray.m875getMh2AYeg(sArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                int iM875getMh2AYeg = UShortArray.m875getMh2AYeg(sArr, i) & UShort.MAX_VALUE;
                i3 = sM875getMh2AYeg & UShort.MAX_VALUE;
                if (Intrinsics.compare(iM875getMh2AYeg, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UShortArray.m875getMh2AYeg(sArr, i2) & UShort.MAX_VALUE, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                short sM875getMh2AYeg2 = UShortArray.m875getMh2AYeg(sArr, i);
                UShortArray.m880set01HTLdE(sArr, i, UShortArray.m875getMh2AYeg(sArr, i2));
                UShortArray.m880set01HTLdE(sArr, i2, sM875getMh2AYeg2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort-Aa5vz7o, reason: not valid java name */
    private static final void m1001quickSortAa5vz7o(short[] sArr, int i, int i2) {
        int iM997partitionAa5vz7o = m997partitionAa5vz7o(sArr, i, i2);
        int i3 = iM997partitionAa5vz7o - 1;
        if (i < i3) {
            m1001quickSortAa5vz7o(sArr, i, i3);
        }
        if (iM997partitionAa5vz7o < i2) {
            m1001quickSortAa5vz7o(sArr, iM997partitionAa5vz7o, i2);
        }
    }

    /* renamed from: partition-oBK06Vg, reason: not valid java name */
    private static final int m998partitionoBK06Vg(int[] iArr, int i, int i2) {
        int iM691getpVg5ArA = UIntArray.m691getpVg5ArA(iArr, (i + i2) / 2);
        while (i <= i2) {
            while (Integer.compare(UIntArray.m691getpVg5ArA(iArr, i) ^ Integer.MIN_VALUE, iM691getpVg5ArA ^ Integer.MIN_VALUE) < 0) {
                i++;
            }
            while (Integer.compare(UIntArray.m691getpVg5ArA(iArr, i2) ^ Integer.MIN_VALUE, iM691getpVg5ArA ^ Integer.MIN_VALUE) > 0) {
                i2--;
            }
            if (i <= i2) {
                int iM691getpVg5ArA2 = UIntArray.m691getpVg5ArA(iArr, i);
                UIntArray.m696setVXSXFK8(iArr, i, UIntArray.m691getpVg5ArA(iArr, i2));
                UIntArray.m696setVXSXFK8(iArr, i2, iM691getpVg5ArA2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort-oBK06Vg, reason: not valid java name */
    private static final void m1002quickSortoBK06Vg(int[] iArr, int i, int i2) {
        int iM998partitionoBK06Vg = m998partitionoBK06Vg(iArr, i, i2);
        int i3 = iM998partitionoBK06Vg - 1;
        if (i < i3) {
            m1002quickSortoBK06Vg(iArr, i, i3);
        }
        if (iM998partitionoBK06Vg < i2) {
            m1002quickSortoBK06Vg(iArr, iM998partitionoBK06Vg, i2);
        }
    }

    /* renamed from: partition--nroSd4, reason: not valid java name */
    private static final int m995partitionnroSd4(long[] jArr, int i, int i2) {
        long jM770getsVKNKU = ULongArray.m770getsVKNKU(jArr, (i + i2) / 2);
        while (i <= i2) {
            while (Long.compare(ULongArray.m770getsVKNKU(jArr, i) ^ Long.MIN_VALUE, jM770getsVKNKU ^ Long.MIN_VALUE) < 0) {
                i++;
            }
            while (Long.compare(ULongArray.m770getsVKNKU(jArr, i2) ^ Long.MIN_VALUE, jM770getsVKNKU ^ Long.MIN_VALUE) > 0) {
                i2--;
            }
            if (i <= i2) {
                long jM770getsVKNKU2 = ULongArray.m770getsVKNKU(jArr, i);
                ULongArray.m775setk8EXiF4(jArr, i, ULongArray.m770getsVKNKU(jArr, i2));
                ULongArray.m775setk8EXiF4(jArr, i2, jM770getsVKNKU2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort--nroSd4, reason: not valid java name */
    private static final void m999quickSortnroSd4(long[] jArr, int i, int i2) {
        int iM995partitionnroSd4 = m995partitionnroSd4(jArr, i, i2);
        int i3 = iM995partitionnroSd4 - 1;
        if (i < i3) {
            m999quickSortnroSd4(jArr, i, i3);
        }
        if (iM995partitionnroSd4 < i2) {
            m999quickSortnroSd4(jArr, iM995partitionnroSd4, i2);
        }
    }

    /* renamed from: sortArray-4UcCI2c, reason: not valid java name */
    public static final void m1004sortArray4UcCI2c(byte[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m1000quickSort4UcCI2c(array, i, i2 - 1);
    }

    /* renamed from: sortArray-Aa5vz7o, reason: not valid java name */
    public static final void m1005sortArrayAa5vz7o(short[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m1001quickSortAa5vz7o(array, i, i2 - 1);
    }

    /* renamed from: sortArray-oBK06Vg, reason: not valid java name */
    public static final void m1006sortArrayoBK06Vg(int[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m1002quickSortoBK06Vg(array, i, i2 - 1);
    }

    /* renamed from: sortArray--nroSd4, reason: not valid java name */
    public static final void m1003sortArraynroSd4(long[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m999quickSortnroSd4(array, i, i2 - 1);
    }
}
