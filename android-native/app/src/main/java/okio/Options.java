package okio;

import java.util.AbstractList;
import java.util.List;
import java.util.RandomAccess;

/* loaded from: classes2.dex */
public final class Options extends AbstractList<ByteString> implements RandomAccess {
    final ByteString[] byteStrings;
    final int[] trie;

    private Options(ByteString[] byteStringArr, int[] iArr) {
        this.byteStrings = byteStringArr;
        this.trie = iArr;
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x00b7, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static okio.Options of(okio.ByteString... r11) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Options.of(okio.ByteString[]):okio.Options");
    }

    private static void buildTrieRecursive(long j, Buffer buffer, int i, List<ByteString> list, int i2, int i3, List<Integer> list2) {
        int i4;
        int i5;
        long j2;
        int i6;
        List<Integer> list3;
        long j3;
        int i7;
        List<ByteString> list4 = list;
        List<Integer> list5 = list2;
        if (i2 >= i3) {
            throw new AssertionError();
        }
        for (int i8 = i2; i8 < i3; i8++) {
            if (list4.get(i8).size() < i) {
                throw new AssertionError();
            }
        }
        ByteString byteString = list.get(i2);
        ByteString byteString2 = list4.get(i3 - 1);
        if (i == byteString.size()) {
            int iIntValue = list5.get(i2).intValue();
            int i9 = i2 + 1;
            ByteString byteString3 = list4.get(i9);
            i4 = i9;
            i5 = iIntValue;
            byteString = byteString3;
        } else {
            i4 = i2;
            i5 = -1;
        }
        long j4 = 2;
        if (byteString.getByte(i) != byteString2.getByte(i)) {
            int i10 = 1;
            for (int i11 = i4 + 1; i11 < i3; i11++) {
                if (list4.get(i11 - 1).getByte(i) != list4.get(i11).getByte(i)) {
                    i10++;
                }
            }
            long jIntCount = j + intCount(buffer) + 2 + (i10 * 2);
            buffer.writeInt(i10);
            buffer.writeInt(i5);
            for (int i12 = i4; i12 < i3; i12++) {
                byte b = list4.get(i12).getByte(i);
                if (i12 == i4 || b != list4.get(i12 - 1).getByte(i)) {
                    buffer.writeInt(b & 255);
                }
            }
            Buffer buffer2 = new Buffer();
            int i13 = i4;
            while (i13 < i3) {
                byte b2 = list4.get(i13).getByte(i);
                int i14 = i13 + 1;
                int i15 = i14;
                while (true) {
                    if (i15 >= i3) {
                        i15 = i3;
                        break;
                    } else if (b2 != list4.get(i15).getByte(i)) {
                        break;
                    } else {
                        i15++;
                    }
                }
                if (i14 == i15 && i + 1 == list4.get(i13).size()) {
                    buffer.writeInt(list5.get(i13).intValue());
                    list3 = list5;
                    j3 = jIntCount;
                    i7 = i15;
                } else {
                    buffer.writeInt((int) ((intCount(buffer2) + jIntCount) * (-1)));
                    list3 = list5;
                    j3 = jIntCount;
                    i7 = i15;
                    buildTrieRecursive(j3, buffer2, i + 1, list, i13, i7, list3);
                    list4 = list;
                }
                jIntCount = j3;
                i13 = i7;
                list5 = list3;
            }
            buffer.write(buffer2, buffer2.size());
            return;
        }
        int iMin = Math.min(byteString.size(), byteString2.size());
        int i16 = 0;
        int i17 = i;
        while (true) {
            if (i17 >= iMin) {
                j2 = j4;
                break;
            }
            j2 = j4;
            if (byteString.getByte(i17) != byteString2.getByte(i17)) {
                break;
            }
            i16++;
            i17++;
            j4 = j2;
        }
        long jIntCount2 = j + intCount(buffer) + j2 + i16 + 1;
        buffer.writeInt(-i16);
        buffer.writeInt(i5);
        int i18 = i;
        while (true) {
            i6 = i + i16;
            if (i18 >= i6) {
                break;
            }
            buffer.writeInt(byteString.getByte(i18) & 255);
            i18++;
        }
        if (i4 + 1 == i3) {
            if (i6 != list4.get(i4).size()) {
                throw new AssertionError();
            }
            buffer.writeInt(list5.get(i4).intValue());
        } else {
            Buffer buffer3 = new Buffer();
            buffer.writeInt((int) ((intCount(buffer3) + jIntCount2) * (-1)));
            buildTrieRecursive(jIntCount2, buffer3, i6, list4, i4, i3, list5);
            buffer.write(buffer3, buffer3.size());
        }
    }

    @Override // java.util.AbstractList, java.util.List
    public ByteString get(int i) {
        return this.byteStrings[i];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.byteStrings.length;
    }

    private static int intCount(Buffer buffer) {
        return (int) (buffer.size() / 4);
    }
}
