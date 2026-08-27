package com.facebook.soloader;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ClosedByInterruptException;
import kotlin.UShort;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes.dex */
public final class MinElf {
    public static final int DT_NEEDED = 1;
    public static final int DT_NULL = 0;
    public static final int DT_STRTAB = 5;
    public static final int ELF_MAGIC = 1179403647;
    public static final int PN_XNUM = 65535;
    public static final int PT_DYNAMIC = 2;
    public static final int PT_LOAD = 1;
    private static final String TAG = "MinElf";

    public interface ISA {
        public static final String AARCH64 = "arm64-v8a";
        public static final String ARM = "armeabi-v7a";
        public static final String X86 = "x86";
        public static final String X86_64 = "x86_64";
    }

    public static String[] extract_DT_NEEDED(File file) throws IOException {
        ElfFileChannel elfFileChannel = new ElfFileChannel(file);
        try {
            String[] strArrExtract_DT_NEEDED = extract_DT_NEEDED(elfFileChannel);
            elfFileChannel.close();
            return strArrExtract_DT_NEEDED;
        } catch (Throwable th) {
            try {
                elfFileChannel.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static String[] extract_DT_NEEDED_with_retries(ElfFileChannel elfFileChannel) throws IOException {
        int i = 0;
        while (true) {
            try {
                return extract_DT_NEEDED_no_retries(elfFileChannel);
            } catch (ClosedByInterruptException e) {
                i++;
                if (i > 4) {
                    throw e;
                }
                Thread.interrupted();
                LogUtil.e(TAG, "retrying extract_DT_NEEDED due to ClosedByInterruptException", e);
                elfFileChannel.openChannel();
            }
        }
    }

    public static String[] extract_DT_NEEDED(ElfByteChannel elfByteChannel) throws IOException {
        if (elfByteChannel instanceof ElfFileChannel) {
            return extract_DT_NEEDED_with_retries((ElfFileChannel) elfByteChannel);
        }
        return extract_DT_NEEDED_no_retries(elfByteChannel);
    }

    private static String[] extract_DT_NEEDED_no_retries(ElfByteChannel elfByteChannel) throws IOException {
        long j;
        long j2;
        long j3;
        long j4;
        long j5;
        long j6;
        int i;
        long j7;
        long j8;
        long j9;
        long j10;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(8);
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        long j11 = getu32(elfByteChannel, byteBufferAllocate, 0L);
        if (j11 != 1179403647) {
            throw new ElfError("file is not ELF: magic is 0x" + Long.toHexString(j11) + ", it should be " + Long.toHexString(1179403647L));
        }
        boolean z = getu8(elfByteChannel, byteBufferAllocate, 4L) == 1;
        long j12 = 5;
        if (getu8(elfByteChannel, byteBufferAllocate, 5L) == 2) {
            byteBufferAllocate.order(ByteOrder.BIG_ENDIAN);
        }
        long j13 = z ? getu32(elfByteChannel, byteBufferAllocate, 28L) : get64(elfByteChannel, byteBufferAllocate, 32L);
        if (z) {
            j = 44;
            j2 = getu16(elfByteChannel, byteBufferAllocate, 44L);
        } else {
            j = 44;
            j2 = getu16(elfByteChannel, byteBufferAllocate, 56L);
        }
        int i2 = getu16(elfByteChannel, byteBufferAllocate, z ? 42L : 54L);
        long j14 = 40;
        if (j2 == 65535) {
            long j15 = z ? getu32(elfByteChannel, byteBufferAllocate, 32L) : get64(elfByteChannel, byteBufferAllocate, 40L);
            if (z) {
                j2 = getu32(elfByteChannel, byteBufferAllocate, j15 + 28);
            } else {
                j2 = getu32(elfByteChannel, byteBufferAllocate, j15 + j);
            }
        }
        long j16 = j13;
        long j17 = 0;
        while (true) {
            if (j17 >= j2) {
                j3 = 0;
                break;
            }
            if (z) {
                j10 = getu32(elfByteChannel, byteBufferAllocate, j16);
            } else {
                j10 = getu32(elfByteChannel, byteBufferAllocate, j16);
            }
            if (j10 != 2) {
                j16 += i2;
                j17++;
                j14 = j14;
            } else if (z) {
                j3 = getu32(elfByteChannel, byteBufferAllocate, j16 + 4);
            } else {
                j3 = get64(elfByteChannel, byteBufferAllocate, j16 + 8);
            }
        }
        long j18 = j14;
        if (j3 == 0) {
            throw new ElfError("ELF file does not contain dynamic linking information");
        }
        long j19 = j3;
        long j20 = 0;
        int i3 = 0;
        while (true) {
            long j21 = z ? getu32(elfByteChannel, byteBufferAllocate, j19) : get64(elfByteChannel, byteBufferAllocate, j19);
            if (j21 == 1) {
                if (i3 == Integer.MAX_VALUE) {
                    throw new ElfError("malformed DT_NEEDED section");
                }
                i3++;
            } else if (j21 == j12) {
                j20 = z ? getu32(elfByteChannel, byteBufferAllocate, j19 + 4) : get64(elfByteChannel, byteBufferAllocate, j19 + 8);
            }
            j19 += z ? 8L : 16L;
            if (j21 == 0) {
                if (j20 == 0) {
                    throw new ElfError("Dynamic section string-table not found");
                }
                long j22 = j13;
                int i4 = 0;
                while (true) {
                    if (i4 >= j2) {
                        j4 = 0;
                        break;
                    }
                    if (z) {
                        j6 = getu32(elfByteChannel, byteBufferAllocate, j22);
                    } else {
                        j6 = getu32(elfByteChannel, byteBufferAllocate, j22);
                    }
                    if (j6 == 1) {
                        if (z) {
                            j7 = getu32(elfByteChannel, byteBufferAllocate, j22 + 8);
                        } else {
                            j7 = get64(elfByteChannel, byteBufferAllocate, j22 + 16);
                        }
                        if (z) {
                            i = i4;
                            j8 = getu32(elfByteChannel, byteBufferAllocate, j22 + 20);
                        } else {
                            i = i4;
                            j8 = get64(elfByteChannel, byteBufferAllocate, j22 + j18);
                        }
                        if (j7 <= j20 && j20 < j8 + j7) {
                            if (z) {
                                j9 = getu32(elfByteChannel, byteBufferAllocate, j22 + 4);
                            } else {
                                j9 = get64(elfByteChannel, byteBufferAllocate, j22 + 8);
                            }
                            j4 = j9 + (j20 - j7);
                        }
                    } else {
                        i = i4;
                    }
                    j22 += i2;
                    i4 = i + 1;
                }
                if (j4 == 0) {
                    throw new ElfError("did not find file offset of DT_STRTAB table");
                }
                String[] strArr = new String[i3];
                int i5 = 0;
                do {
                    j5 = z ? getu32(elfByteChannel, byteBufferAllocate, j3) : get64(elfByteChannel, byteBufferAllocate, j3);
                    if (j5 == 1) {
                        strArr[i5] = getSz(elfByteChannel, byteBufferAllocate, (z ? getu32(elfByteChannel, byteBufferAllocate, j3 + 4) : get64(elfByteChannel, byteBufferAllocate, j3 + 8)) + j4);
                        if (i5 == Integer.MAX_VALUE) {
                            throw new ElfError("malformed DT_NEEDED section");
                        }
                        i5++;
                    }
                    j3 += z ? 8L : 16L;
                } while (j5 != 0);
                if (i5 == i3) {
                    return strArr;
                }
                throw new ElfError("malformed DT_NEEDED section");
            }
            j12 = 5;
        }
    }

    private static String getSz(ElfByteChannel elfByteChannel, ByteBuffer byteBuffer, long j) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            long j2 = 1 + j;
            short u8Var = getu8(elfByteChannel, byteBuffer, j);
            if (u8Var != 0) {
                sb.append((char) u8Var);
                j = j2;
            } else {
                return sb.toString();
            }
        }
    }

    private static void read(ElfByteChannel elfByteChannel, ByteBuffer byteBuffer, int i, long j) throws IOException {
        int i2;
        byteBuffer.position(0);
        byteBuffer.limit(i);
        while (byteBuffer.remaining() > 0 && (i2 = elfByteChannel.read(byteBuffer, j)) != -1) {
            j += i2;
        }
        if (byteBuffer.remaining() > 0) {
            throw new ElfError("ELF file truncated");
        }
        byteBuffer.position(0);
    }

    private static long get64(ElfByteChannel elfByteChannel, ByteBuffer byteBuffer, long j) throws IOException {
        read(elfByteChannel, byteBuffer, 8, j);
        return byteBuffer.getLong();
    }

    private static long getu32(ElfByteChannel elfByteChannel, ByteBuffer byteBuffer, long j) throws IOException {
        read(elfByteChannel, byteBuffer, 4, j);
        return byteBuffer.getInt() & InternalZipConstants.ZIP_64_SIZE_LIMIT;
    }

    private static int getu16(ElfByteChannel elfByteChannel, ByteBuffer byteBuffer, long j) throws IOException {
        read(elfByteChannel, byteBuffer, 2, j);
        return byteBuffer.getShort() & UShort.MAX_VALUE;
    }

    private static short getu8(ElfByteChannel elfByteChannel, ByteBuffer byteBuffer, long j) throws IOException {
        read(elfByteChannel, byteBuffer, 1, j);
        return (short) (byteBuffer.get() & 255);
    }

    protected static class ElfError extends UnsatisfiedLinkError {
        ElfError(String str) {
            super(str);
        }
    }
}
