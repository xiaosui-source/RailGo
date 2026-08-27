package net.lingala.zip4j.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Calendar;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.AbstractFileHeader;
import net.lingala.zip4j.model.enums.CompressionMethod;

/* loaded from: classes2.dex */
public class Zip4jUtil {
    private static final long DOSTIME_BEFORE_1980 = 2162688;
    private static final int MAX_RAW_READ_FULLY_RETRY_ATTEMPTS = 15;

    public static boolean isStringNullOrEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isStringNotNullAndNotEmpty(String str) {
        return str != null && str.trim().length() > 0;
    }

    public static boolean createDirectoryIfNotExists(File file) throws ZipException {
        if (file == null) {
            throw new ZipException("output path is null");
        }
        if (file.exists()) {
            if (file.isDirectory()) {
                return true;
            }
            throw new ZipException("output directory is not valid");
        }
        if (file.mkdirs()) {
            return true;
        }
        throw new ZipException("Cannot create output directories");
    }

    public static long epochToExtendedDosTime(long j) {
        if (j < 0) {
            return DOSTIME_BEFORE_1980;
        }
        long jEpochToDosTime = epochToDosTime(j);
        return jEpochToDosTime != DOSTIME_BEFORE_1980 ? jEpochToDosTime + ((j % 2000) << 32) : DOSTIME_BEFORE_1980;
    }

    private static long epochToDosTime(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        if (calendar.get(1) < 1980) {
            return DOSTIME_BEFORE_1980;
        }
        return (calendar.get(13) >> 1) | ((r5 - 1980) << 25) | ((calendar.get(2) + 1) << 21) | (calendar.get(5) << 16) | (calendar.get(11) << 11) | (calendar.get(12) << 5);
    }

    public static long dosToExtendedEpochTme(long j) {
        return dosToEpochTime(j) + (j >> 32);
    }

    private static long dosToEpochTime(long j) {
        int i = (int) ((j >> 5) & 63);
        int i2 = (int) ((j >> 11) & 31);
        int i3 = (int) ((j >> 16) & 31);
        int i4 = (int) (((j >> 21) & 15) - 1);
        Calendar calendar = Calendar.getInstance();
        calendar.set((int) (((j >> 25) & 127) + 1980), i4, i3, i2, i, (int) ((j << 1) & 62));
        calendar.set(14, 0);
        return calendar.getTime().getTime();
    }

    public static byte[] convertCharArrayToByteArray(char[] cArr, boolean z) {
        if (z) {
            return convertCharArrayToByteArrayUsingUtf8(cArr);
        }
        return convertCharArrayToByteArrayUsingDefaultCharset(cArr);
    }

    public static CompressionMethod getCompressionMethod(AbstractFileHeader abstractFileHeader) throws ZipException {
        if (abstractFileHeader.getCompressionMethod() != CompressionMethod.AES_INTERNAL_ONLY) {
            return abstractFileHeader.getCompressionMethod();
        }
        if (abstractFileHeader.getAesExtraDataRecord() == null) {
            throw new ZipException("AesExtraDataRecord not present in local header for aes encrypted data");
        }
        return abstractFileHeader.getAesExtraDataRecord().getCompressionMethod();
    }

    public static int readFully(InputStream inputStream, byte[] bArr) throws IOException {
        int i = inputStream.read(bArr);
        if (i == -1) {
            throw new IOException("Unexpected EOF reached when trying to read stream");
        }
        if (i == bArr.length) {
            return i;
        }
        int untilBufferIsFull = readUntilBufferIsFull(inputStream, bArr, i);
        if (untilBufferIsFull == bArr.length) {
            return untilBufferIsFull;
        }
        throw new IOException("Cannot read fully into byte buffer");
    }

    public static int readFully(InputStream inputStream, byte[] bArr, int i, int i2) throws IOException {
        if (i < 0) {
            throw new IllegalArgumentException("Negative offset");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("Negative length");
        }
        int i3 = 0;
        if (i2 == 0) {
            return 0;
        }
        if (i + i2 > bArr.length) {
            throw new IllegalArgumentException("Length greater than buffer size");
        }
        while (true) {
            if (i3 == i2) {
                break;
            }
            int i4 = inputStream.read(bArr, i + i3, i2 - i3);
            if (i4 != -1) {
                i3 += i4;
            } else if (i3 == 0) {
                return -1;
            }
        }
        return i3;
    }

    private static int readUntilBufferIsFull(InputStream inputStream, byte[] bArr, int i) throws IOException {
        if (i < 0) {
            throw new IOException("Invalid readLength");
        }
        int i2 = 0;
        if (i == 0) {
            return 0;
        }
        int length = bArr.length - i;
        for (int i3 = 1; i < bArr.length && i2 != -1 && i3 < 15; i3++) {
            i2 = inputStream.read(bArr, i, length);
            if (i2 > 0) {
                i += i2;
                length -= i2;
            }
        }
        return i;
    }

    private static byte[] convertCharArrayToByteArrayUsingUtf8(char[] cArr) {
        try {
            ByteBuffer byteBufferEncode = InternalZipConstants.CHARSET_UTF_8.encode(CharBuffer.wrap(cArr));
            byte[] bArr = new byte[byteBufferEncode.limit()];
            byteBufferEncode.get(bArr);
            return bArr;
        } catch (Exception unused) {
            return convertCharArrayToByteArrayUsingDefaultCharset(cArr);
        }
    }

    private static byte[] convertCharArrayToByteArrayUsingDefaultCharset(char[] cArr) {
        byte[] bArr = new byte[cArr.length];
        for (int i = 0; i < cArr.length; i++) {
            bArr[i] = (byte) cArr[i];
        }
        return bArr;
    }
}
