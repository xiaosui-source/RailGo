package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.RyuDouble;
import com.alibaba.fastjson.util.RyuFloat;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.common.constant.AbsoluteConst;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.List;

/* loaded from: classes.dex */
public final class SerializeWriter extends Writer {
    private static int BUFFER_THRESHOLD;
    static final int nonDirectFeatures;
    protected boolean beanToArray;
    protected boolean browserSecure;
    protected char[] buf;
    protected int count;
    protected boolean disableCircularReferenceDetect;
    protected int features;
    protected char keySeperator;
    protected int maxBufSize;
    protected boolean notWriteDefaultValue;
    protected boolean quoteFieldNames;
    protected long sepcialBits;
    protected boolean sortField;
    protected boolean useSingleQuotes;
    protected boolean writeDirect;
    protected boolean writeEnumUsingName;
    protected boolean writeEnumUsingToString;
    protected boolean writeNonStringValueAsString;
    private final Writer writer;
    private static final ThreadLocal<char[]> bufLocal = new ThreadLocal<>();
    private static final ThreadLocal<byte[]> bytesBufLocal = new ThreadLocal<>();
    private static final char[] VALUE_TRUE = ":true".toCharArray();
    private static final char[] VALUE_FALSE = ":false".toCharArray();

    static {
        int i;
        BUFFER_THRESHOLD = 131072;
        try {
            String stringProperty = IOUtils.getStringProperty("fastjson.serializer_buffer_threshold");
            if (stringProperty != null && stringProperty.length() > 0 && (i = Integer.parseInt(stringProperty)) >= 64 && i <= 65536) {
                BUFFER_THRESHOLD = i * 1024;
            }
        } catch (Throwable unused) {
        }
        nonDirectFeatures = SerializerFeature.UseSingleQuotes.mask | SerializerFeature.BrowserCompatible.mask | SerializerFeature.PrettyFormat.mask | SerializerFeature.WriteEnumUsingToString.mask | SerializerFeature.WriteNonStringValueAsString.mask | SerializerFeature.WriteSlashAsSpecial.mask | SerializerFeature.IgnoreErrorGetter.mask | SerializerFeature.WriteClassName.mask | SerializerFeature.NotWriteDefaultValue.mask;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SerializeWriter() {
        this((Writer) null);
    }

    public SerializeWriter(Writer writer) {
        this(writer, JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.EMPTY);
    }

    public SerializeWriter(SerializerFeature... serializerFeatureArr) {
        this((Writer) null, serializerFeatureArr);
    }

    public SerializeWriter(Writer writer, SerializerFeature... serializerFeatureArr) {
        this(writer, 0, serializerFeatureArr);
    }

    public SerializeWriter(Writer writer, int i, SerializerFeature... serializerFeatureArr) {
        this.maxBufSize = -1;
        this.writer = writer;
        ThreadLocal<char[]> threadLocal = bufLocal;
        char[] cArr = threadLocal.get();
        this.buf = cArr;
        if (cArr != null) {
            threadLocal.set(null);
        } else {
            this.buf = new char[2048];
        }
        for (SerializerFeature serializerFeature : serializerFeatureArr) {
            i |= serializerFeature.getMask();
        }
        this.features = i;
        computeFeatures();
    }

    public int getMaxBufSize() {
        return this.maxBufSize;
    }

    public void setMaxBufSize(int i) {
        if (i < this.buf.length) {
            throw new JSONException("must > " + this.buf.length);
        }
        this.maxBufSize = i;
    }

    public int getBufferLength() {
        return this.buf.length;
    }

    public SerializeWriter(int i) {
        this((Writer) null, i);
    }

    public SerializeWriter(Writer writer, int i) {
        this.maxBufSize = -1;
        this.writer = writer;
        if (i <= 0) {
            throw new IllegalArgumentException("Negative initial size: " + i);
        }
        this.buf = new char[i];
        computeFeatures();
    }

    public void config(SerializerFeature serializerFeature, boolean z) {
        if (z) {
            this.features |= serializerFeature.getMask();
            if (serializerFeature == SerializerFeature.WriteEnumUsingToString) {
                this.features &= ~SerializerFeature.WriteEnumUsingName.getMask();
            } else if (serializerFeature == SerializerFeature.WriteEnumUsingName) {
                this.features &= ~SerializerFeature.WriteEnumUsingToString.getMask();
            }
        } else {
            this.features = (~serializerFeature.getMask()) & this.features;
        }
        computeFeatures();
    }

    protected void computeFeatures() {
        long j;
        this.quoteFieldNames = (this.features & SerializerFeature.QuoteFieldNames.mask) != 0;
        this.useSingleQuotes = (this.features & SerializerFeature.UseSingleQuotes.mask) != 0;
        this.sortField = (this.features & SerializerFeature.SortField.mask) != 0;
        this.disableCircularReferenceDetect = (this.features & SerializerFeature.DisableCircularReferenceDetect.mask) != 0;
        this.beanToArray = (this.features & SerializerFeature.BeanToArray.mask) != 0;
        this.writeNonStringValueAsString = (this.features & SerializerFeature.WriteNonStringValueAsString.mask) != 0;
        this.notWriteDefaultValue = (this.features & SerializerFeature.NotWriteDefaultValue.mask) != 0;
        this.writeEnumUsingName = (this.features & SerializerFeature.WriteEnumUsingName.mask) != 0;
        this.writeEnumUsingToString = (this.features & SerializerFeature.WriteEnumUsingToString.mask) != 0;
        this.writeDirect = this.quoteFieldNames && (this.features & nonDirectFeatures) == 0 && (this.beanToArray || this.writeEnumUsingName);
        this.keySeperator = this.useSingleQuotes ? Operators.SINGLE_QUOTE : '\"';
        boolean z = (this.features & SerializerFeature.BrowserSecure.mask) != 0;
        this.browserSecure = z;
        if (z) {
            j = 5764610843043954687L;
        } else {
            j = (this.features & SerializerFeature.WriteSlashAsSpecial.mask) != 0 ? 140758963191807L : 21474836479L;
        }
        this.sepcialBits = j;
    }

    public boolean isSortField() {
        return this.sortField;
    }

    public boolean isNotWriteDefaultValue() {
        return this.notWriteDefaultValue;
    }

    public boolean isEnabled(SerializerFeature serializerFeature) {
        return (serializerFeature.mask & this.features) != 0;
    }

    public boolean isEnabled(int i) {
        return (i & this.features) != 0;
    }

    @Override // java.io.Writer
    public void write(int i) {
        int i2 = 1;
        int i3 = this.count + 1;
        if (i3 <= this.buf.length) {
            i2 = i3;
        } else if (this.writer == null) {
            expandCapacity(i3);
            i2 = i3;
        } else {
            flush();
        }
        this.buf[this.count] = (char) i;
        this.count = i2;
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) throws IOException {
        int i3;
        if (i < 0 || i > cArr.length || i2 < 0 || (i3 = i + i2) > cArr.length || i3 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (i2 == 0) {
            return;
        }
        int i4 = this.count + i2;
        if (i4 > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(i4);
            } else {
                do {
                    char[] cArr2 = this.buf;
                    int length = cArr2.length;
                    int i5 = this.count;
                    int i6 = length - i5;
                    System.arraycopy(cArr, i, cArr2, i5, i6);
                    this.count = this.buf.length;
                    flush();
                    i2 -= i6;
                    i += i6;
                } while (i2 > this.buf.length);
                i4 = i2;
            }
        }
        System.arraycopy(cArr, i, this.buf, this.count, i2);
        this.count = i4;
    }

    public void expandCapacity(int i) {
        ThreadLocal<char[]> threadLocal;
        char[] cArr;
        int i2 = this.maxBufSize;
        if (i2 != -1 && i >= i2) {
            throw new JSONException("serialize exceeded MAX_OUTPUT_LENGTH=" + this.maxBufSize + ", minimumCapacity=" + i);
        }
        char[] cArr2 = this.buf;
        int length = cArr2.length + (cArr2.length >> 1) + 1;
        if (length >= i) {
            i = length;
        }
        char[] cArr3 = new char[i];
        System.arraycopy(cArr2, 0, cArr3, 0, this.count);
        if (this.buf.length < BUFFER_THRESHOLD && ((cArr = (threadLocal = bufLocal).get()) == null || cArr.length < this.buf.length)) {
            threadLocal.set(this.buf);
        }
        this.buf = cArr3;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public SerializeWriter append(CharSequence charSequence) throws IOException {
        String string = charSequence == null ? "null" : charSequence.toString();
        write(string, 0, string.length());
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public SerializeWriter append(CharSequence charSequence, int i, int i2) throws IOException {
        if (charSequence == null) {
            charSequence = "null";
        }
        String string = charSequence.subSequence(i, i2).toString();
        write(string, 0, string.length());
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public SerializeWriter append(char c) {
        write(c);
        return this;
    }

    @Override // java.io.Writer
    public void write(String str, int i, int i2) throws IOException {
        int i3;
        int i4 = this.count + i2;
        if (i4 > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(i4);
            } else {
                while (true) {
                    char[] cArr = this.buf;
                    int length = cArr.length;
                    int i5 = this.count;
                    int i6 = length - i5;
                    i3 = i + i6;
                    str.getChars(i, i3, cArr, i5);
                    this.count = this.buf.length;
                    flush();
                    i2 -= i6;
                    if (i2 <= this.buf.length) {
                        break;
                    } else {
                        i = i3;
                    }
                }
                i4 = i2;
                i = i3;
            }
        }
        str.getChars(i, i2 + i, this.buf, this.count);
        this.count = i4;
    }

    public void writeTo(Writer writer) throws IOException {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        writer.write(this.buf, 0, this.count);
    }

    public void writeTo(OutputStream outputStream, String str) throws IOException {
        writeTo(outputStream, Charset.forName(str));
    }

    public void writeTo(OutputStream outputStream, Charset charset) throws IOException {
        writeToEx(outputStream, charset);
    }

    public int writeToEx(OutputStream outputStream, Charset charset) throws IOException {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        if (charset == IOUtils.UTF8) {
            return encodeToUTF8(outputStream);
        }
        byte[] bytes = new String(this.buf, 0, this.count).getBytes(charset);
        outputStream.write(bytes);
        return bytes.length;
    }

    public char[] toCharArray() {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        int i = this.count;
        char[] cArr = new char[i];
        System.arraycopy(this.buf, 0, cArr, 0, i);
        return cArr;
    }

    public char[] toCharArrayForSpringWebSocket() {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        int i = this.count;
        char[] cArr = new char[i - 2];
        System.arraycopy(this.buf, 1, cArr, 0, i - 2);
        return cArr;
    }

    public byte[] toBytes(String str) {
        Charset charsetForName;
        if (str == null || "UTF-8".equals(str)) {
            charsetForName = IOUtils.UTF8;
        } else {
            charsetForName = Charset.forName(str);
        }
        return toBytes(charsetForName);
    }

    public byte[] toBytes(Charset charset) {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        if (charset == IOUtils.UTF8) {
            return encodeToUTF8Bytes();
        }
        return new String(this.buf, 0, this.count).getBytes(charset);
    }

    private int encodeToUTF8(OutputStream outputStream) throws IOException {
        int i = (int) (this.count * 3.0d);
        ThreadLocal<byte[]> threadLocal = bytesBufLocal;
        byte[] bArr = threadLocal.get();
        if (bArr == null) {
            bArr = new byte[8192];
            threadLocal.set(bArr);
        }
        byte[] bArr2 = bArr.length < i ? new byte[i] : bArr;
        int iEncodeUTF8 = IOUtils.encodeUTF8(this.buf, 0, this.count, bArr2);
        outputStream.write(bArr2, 0, iEncodeUTF8);
        if (bArr2 != bArr && bArr2.length <= BUFFER_THRESHOLD) {
            threadLocal.set(bArr2);
        }
        return iEncodeUTF8;
    }

    private byte[] encodeToUTF8Bytes() {
        int i = (int) (this.count * 3.0d);
        ThreadLocal<byte[]> threadLocal = bytesBufLocal;
        byte[] bArr = threadLocal.get();
        if (bArr == null) {
            bArr = new byte[8192];
            threadLocal.set(bArr);
        }
        byte[] bArr2 = bArr.length < i ? new byte[i] : bArr;
        int iEncodeUTF8 = IOUtils.encodeUTF8(this.buf, 0, this.count, bArr2);
        byte[] bArr3 = new byte[iEncodeUTF8];
        System.arraycopy(bArr2, 0, bArr3, 0, iEncodeUTF8);
        if (bArr2 != bArr && bArr2.length <= BUFFER_THRESHOLD) {
            threadLocal.set(bArr2);
        }
        return bArr3;
    }

    public int size() {
        return this.count;
    }

    public String toString() {
        return new String(this.buf, 0, this.count);
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.writer != null && this.count > 0) {
            flush();
        }
        char[] cArr = this.buf;
        if (cArr.length <= BUFFER_THRESHOLD) {
            bufLocal.set(cArr);
        }
        this.buf = null;
    }

    @Override // java.io.Writer
    public void write(String str) {
        if (str == null) {
            writeNull();
        } else {
            write(str, 0, str.length());
        }
    }

    public void writeInt(int i) {
        if (i == Integer.MIN_VALUE) {
            write("-2147483648");
            return;
        }
        int iStringSize = i < 0 ? IOUtils.stringSize(-i) + 1 : IOUtils.stringSize(i);
        int i2 = this.count + iStringSize;
        if (i2 > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(i2);
            } else {
                char[] cArr = new char[iStringSize];
                IOUtils.getChars(i, iStringSize, cArr);
                write(cArr, 0, iStringSize);
                return;
            }
        }
        IOUtils.getChars(i, i2, this.buf);
        this.count = i2;
    }

    public void writeByteArray(byte[] bArr) {
        if (isEnabled(SerializerFeature.WriteClassName.mask)) {
            writeHex(bArr);
            return;
        }
        int length = bArr.length;
        boolean z = this.useSingleQuotes;
        char c = z ? Operators.SINGLE_QUOTE : '\"';
        if (length == 0) {
            write(z ? "''" : "\"\"");
            return;
        }
        char[] cArr = IOUtils.CA;
        int i = (length / 3) * 3;
        int i2 = length - 1;
        int i3 = this.count;
        int i4 = (((i2 / 3) + 1) << 2) + i3;
        int i5 = i4 + 2;
        if (i5 > this.buf.length) {
            if (this.writer != null) {
                write(c);
                int i6 = 0;
                while (i6 < i) {
                    int i7 = i6 + 2;
                    int i8 = ((bArr[i6 + 1] & 255) << 8) | ((bArr[i6] & 255) << 16);
                    i6 += 3;
                    int i9 = i8 | (bArr[i7] & 255);
                    write(cArr[(i9 >>> 18) & 63]);
                    write(cArr[(i9 >>> 12) & 63]);
                    write(cArr[(i9 >>> 6) & 63]);
                    write(cArr[i9 & 63]);
                }
                int i10 = length - i;
                if (i10 > 0) {
                    int i11 = ((bArr[i] & 255) << 10) | (i10 == 2 ? (bArr[i2] & 255) << 2 : 0);
                    write(cArr[i11 >> 12]);
                    write(cArr[(i11 >>> 6) & 63]);
                    write(i10 == 2 ? cArr[i11 & 63] : '=');
                    write(61);
                }
                write(c);
                return;
            }
            expandCapacity(i5);
        }
        this.count = i5;
        int i12 = i3 + 1;
        this.buf[i3] = c;
        int i13 = 0;
        while (i13 < i) {
            int i14 = i13 + 2;
            int i15 = ((bArr[i13 + 1] & 255) << 8) | ((bArr[i13] & 255) << 16);
            i13 += 3;
            int i16 = i15 | (bArr[i14] & 255);
            char[] cArr2 = this.buf;
            cArr2[i12] = cArr[(i16 >>> 18) & 63];
            cArr2[i12 + 1] = cArr[(i16 >>> 12) & 63];
            int i17 = i12 + 3;
            cArr2[i12 + 2] = cArr[(i16 >>> 6) & 63];
            i12 += 4;
            cArr2[i17] = cArr[i16 & 63];
        }
        int i18 = length - i;
        if (i18 > 0) {
            int i19 = ((bArr[i] & 255) << 10) | (i18 == 2 ? (bArr[i2] & 255) << 2 : 0);
            char[] cArr3 = this.buf;
            cArr3[i4 - 3] = cArr[i19 >> 12];
            cArr3[i4 - 2] = cArr[(i19 >>> 6) & 63];
            cArr3[i4 - 1] = i18 == 2 ? cArr[i19 & 63] : '=';
            cArr3[i4] = '=';
        }
        this.buf[i4 + 1] = c;
    }

    public void writeHex(byte[] bArr) {
        int length = this.count + (bArr.length * 2) + 3;
        if (length > this.buf.length) {
            expandCapacity(length);
        }
        char[] cArr = this.buf;
        int i = this.count;
        int i2 = i + 1;
        this.count = i2;
        cArr[i] = 'x';
        this.count = i + 2;
        cArr[i2] = Operators.SINGLE_QUOTE;
        for (byte b : bArr) {
            int i3 = (b & 255) >> 4;
            int i4 = b & 15;
            char[] cArr2 = this.buf;
            int i5 = this.count;
            int i6 = i5 + 1;
            this.count = i6;
            int i7 = 48;
            cArr2[i5] = (char) (i3 + (i3 < 10 ? 48 : 55));
            this.count = i5 + 2;
            if (i4 >= 10) {
                i7 = 55;
            }
            cArr2[i6] = (char) (i4 + i7);
        }
        char[] cArr3 = this.buf;
        int i8 = this.count;
        this.count = i8 + 1;
        cArr3[i8] = Operators.SINGLE_QUOTE;
    }

    public void writeFloat(float f, boolean z) throws IOException {
        if (f != f || f == Float.POSITIVE_INFINITY || f == Float.NEGATIVE_INFINITY) {
            writeNull();
            return;
        }
        int i = this.count + 15;
        if (i > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(i);
            } else {
                String string = RyuFloat.toString(f);
                write(string, 0, string.length());
                if (z && isEnabled(SerializerFeature.WriteClassName)) {
                    write(70);
                    return;
                }
                return;
            }
        }
        this.count += RyuFloat.toString(f, this.buf, this.count);
        if (z && isEnabled(SerializerFeature.WriteClassName)) {
            write(70);
        }
    }

    public void writeDouble(double d, boolean z) throws IOException {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            writeNull();
            return;
        }
        int i = this.count + 24;
        if (i > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(i);
            } else {
                String string = RyuDouble.toString(d);
                write(string, 0, string.length());
                if (z && isEnabled(SerializerFeature.WriteClassName)) {
                    write(68);
                    return;
                }
                return;
            }
        }
        this.count += RyuDouble.toString(d, this.buf, this.count);
        if (z && isEnabled(SerializerFeature.WriteClassName)) {
            write(68);
        }
    }

    public void writeEnum(Enum<?> r2) {
        String string;
        if (r2 == null) {
            writeNull();
            return;
        }
        if (this.writeEnumUsingName && !this.writeEnumUsingToString) {
            string = r2.name();
        } else {
            string = this.writeEnumUsingToString ? r2.toString() : null;
        }
        if (string != null) {
            int i = isEnabled(SerializerFeature.UseSingleQuotes) ? 39 : 34;
            write(i);
            write(string);
            write(i);
            return;
        }
        writeInt(r2.ordinal());
    }

    public void writeLongAndChar(long j, char c) throws IOException {
        writeLong(j);
        write(c);
    }

    public void writeLong(long j) {
        boolean z = isEnabled(SerializerFeature.BrowserCompatible) && !isEnabled(SerializerFeature.WriteClassName) && (j > 9007199254740991L || j < -9007199254740991L);
        if (j == Long.MIN_VALUE) {
            if (z) {
                write("\"-9223372036854775808\"");
                return;
            } else {
                write("-9223372036854775808");
                return;
            }
        }
        int iStringSize = j < 0 ? IOUtils.stringSize(-j) + 1 : IOUtils.stringSize(j);
        int i = this.count + iStringSize;
        if (z) {
            i += 2;
        }
        if (i > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(i);
            } else {
                char[] cArr = new char[iStringSize];
                IOUtils.getChars(j, iStringSize, cArr);
                if (z) {
                    write(34);
                    write(cArr, 0, iStringSize);
                    write(34);
                    return;
                }
                write(cArr, 0, iStringSize);
                return;
            }
        }
        if (z) {
            char[] cArr2 = this.buf;
            cArr2[this.count] = '\"';
            int i2 = i - 1;
            IOUtils.getChars(j, i2, cArr2);
            this.buf[i2] = '\"';
        } else {
            IOUtils.getChars(j, i, this.buf);
        }
        this.count = i;
    }

    public void writeNull() {
        write("null");
    }

    public void writeNull(SerializerFeature serializerFeature) {
        writeNull(0, serializerFeature.mask);
    }

    public void writeNull(int i, int i2) {
        if ((i & i2) == 0 && (this.features & i2) == 0) {
            writeNull();
            return;
        }
        if ((SerializerFeature.WriteMapNullValue.mask & i) != 0 && (i & (~SerializerFeature.WriteMapNullValue.mask) & SerializerFeature.WRITE_MAP_NULL_FEATURES) == 0) {
            writeNull();
            return;
        }
        if (i2 == SerializerFeature.WriteNullListAsEmpty.mask) {
            write("[]");
            return;
        }
        if (i2 == SerializerFeature.WriteNullStringAsEmpty.mask) {
            writeString("");
            return;
        }
        if (i2 == SerializerFeature.WriteNullBooleanAsFalse.mask) {
            write(AbsoluteConst.FALSE);
        } else if (i2 == SerializerFeature.WriteNullNumberAsZero.mask) {
            write(48);
        } else {
            writeNull();
        }
    }

    public void writeStringWithDoubleQuote(String str, char c) {
        if (str == null) {
            writeNull();
            if (c != 0) {
                write(c);
                return;
            }
            return;
        }
        int length = str.length();
        int i = this.count + length;
        int i2 = i + 2;
        if (c != 0) {
            i2 = i + 3;
        }
        int i3 = 1;
        if (i2 > this.buf.length) {
            if (this.writer != null) {
                write(34);
                int i4 = 0;
                while (i4 < str.length()) {
                    char cCharAt = str.charAt(i4);
                    if (isEnabled(SerializerFeature.BrowserSecure) && (cCharAt == '(' || cCharAt == ')' || cCharAt == '<' || cCharAt == '>')) {
                        write(92);
                        write(117);
                        write(IOUtils.DIGITS[(cCharAt >>> '\f') & 15]);
                        write(IOUtils.DIGITS[(cCharAt >>> '\b') & 15]);
                        write(IOUtils.DIGITS[(cCharAt >>> 4) & 15]);
                        write(IOUtils.DIGITS[cCharAt & 15]);
                    } else {
                        if (!isEnabled(SerializerFeature.BrowserCompatible)) {
                            if ((cCharAt < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[cCharAt] != 0) || (cCharAt == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                                write(92);
                                if (IOUtils.specicalFlags_doubleQuotes[cCharAt] == 4) {
                                    write(117);
                                    write(IOUtils.DIGITS[(cCharAt >>> '\f') & 15]);
                                    write(IOUtils.DIGITS[(cCharAt >>> '\b') & 15]);
                                    write(IOUtils.DIGITS[(cCharAt >>> 4) & 15]);
                                    write(IOUtils.DIGITS[cCharAt & 15]);
                                } else {
                                    write(IOUtils.replaceChars[cCharAt]);
                                }
                            }
                            i4++;
                            i3 = 1;
                        } else if (cCharAt == '\b' || cCharAt == '\f' || cCharAt == '\n' || cCharAt == '\r' || cCharAt == '\t' || cCharAt == '\"' || cCharAt == '/' || cCharAt == '\\') {
                            write(92);
                            write(IOUtils.replaceChars[cCharAt]);
                        } else if (cCharAt < ' ') {
                            write(92);
                            write(117);
                            write(48);
                            write(48);
                            int i5 = cCharAt * 2;
                            write(IOUtils.ASCII_CHARS[i5]);
                            write(IOUtils.ASCII_CHARS[i5 + i3]);
                        } else if (cCharAt >= 127) {
                            write(92);
                            write(117);
                            write(IOUtils.DIGITS[(cCharAt >>> '\f') & 15]);
                            write(IOUtils.DIGITS[(cCharAt >>> '\b') & 15]);
                            write(IOUtils.DIGITS[(cCharAt >>> 4) & 15]);
                            write(IOUtils.DIGITS[cCharAt & 15]);
                        }
                        write(cCharAt);
                        i4++;
                        i3 = 1;
                    }
                    i4++;
                    i3 = 1;
                }
                write(34);
                if (c != 0) {
                    write(c);
                    return;
                }
                return;
            }
            expandCapacity(i2);
        }
        int i6 = this.count;
        int i7 = i6 + 1;
        int i8 = i7 + length;
        char[] cArr = this.buf;
        cArr[i6] = '\"';
        char c2 = 0;
        str.getChars(0, length, cArr, i7);
        this.count = i2;
        int i9 = -1;
        if (isEnabled(SerializerFeature.BrowserCompatible)) {
            for (int i10 = i7; i10 < i8; i10++) {
                char c3 = this.buf[i10];
                if (c3 == '\"' || c3 == '/' || c3 == '\\' || c3 == '\b' || c3 == '\f' || c3 == '\n' || c3 == '\r' || c3 == '\t') {
                    i2++;
                } else if (c3 < ' ' || c3 >= 127) {
                    i2 += 5;
                }
                i9 = i10;
            }
            if (i2 > this.buf.length) {
                expandCapacity(i2);
            }
            this.count = i2;
            while (i9 >= i7) {
                char[] cArr2 = this.buf;
                char c4 = cArr2[i9];
                if (c4 == '\b' || c4 == '\f' || c4 == '\n' || c4 == '\r' || c4 == '\t') {
                    int i11 = i9 + 1;
                    System.arraycopy(cArr2, i11, cArr2, i9 + 2, (i8 - i9) - 1);
                    char[] cArr3 = this.buf;
                    cArr3[i9] = '\\';
                    cArr3[i11] = IOUtils.replaceChars[c4];
                } else if (c4 == '\"' || c4 == '/' || c4 == '\\') {
                    int i12 = i9 + 1;
                    System.arraycopy(cArr2, i12, cArr2, i9 + 2, (i8 - i9) - 1);
                    char[] cArr4 = this.buf;
                    cArr4[i9] = '\\';
                    cArr4[i12] = c4;
                } else {
                    if (c4 < ' ') {
                        int i13 = i9 + 1;
                        System.arraycopy(cArr2, i13, cArr2, i9 + 6, (i8 - i9) - 1);
                        char[] cArr5 = this.buf;
                        cArr5[i9] = '\\';
                        cArr5[i13] = 'u';
                        cArr5[i9 + 2] = '0';
                        cArr5[i9 + 3] = '0';
                        int i14 = c4 * 2;
                        cArr5[i9 + 4] = IOUtils.ASCII_CHARS[i14];
                        this.buf[i9 + 5] = IOUtils.ASCII_CHARS[i14 + 1];
                    } else if (c4 >= 127) {
                        int i15 = i9 + 1;
                        System.arraycopy(cArr2, i15, cArr2, i9 + 6, (i8 - i9) - 1);
                        char[] cArr6 = this.buf;
                        cArr6[i9] = '\\';
                        cArr6[i15] = 'u';
                        cArr6[i9 + 2] = IOUtils.DIGITS[(c4 >>> '\f') & 15];
                        this.buf[i9 + 3] = IOUtils.DIGITS[(c4 >>> '\b') & 15];
                        this.buf[i9 + 4] = IOUtils.DIGITS[(c4 >>> 4) & 15];
                        this.buf[i9 + 5] = IOUtils.DIGITS[c4 & 15];
                    } else {
                        i9--;
                    }
                    i8 += 5;
                    i9--;
                }
                i8++;
                i9--;
            }
            if (c != 0) {
                char[] cArr7 = this.buf;
                int i16 = this.count;
                cArr7[i16 - 2] = '\"';
                cArr7[i16 - 1] = c;
                return;
            }
            this.buf[this.count - 1] = '\"';
            return;
        }
        int i17 = 0;
        int i18 = -1;
        int i19 = -1;
        for (int i20 = i7; i20 < i8; i20++) {
            char c5 = this.buf[i20];
            if (c5 >= ']') {
                if (c5 >= 127 && (c5 == 8232 || c5 == 8233 || c5 < 160)) {
                    if (i18 == i9) {
                        i18 = i20;
                    }
                    i17++;
                    i2 += 4;
                    i19 = i20;
                }
            } else if ((c5 >= '@' || (this.sepcialBits & (1 << c5)) == 0) && c5 != '\\') {
                i9 = -1;
            } else {
                i17++;
                if (c5 == '(' || c5 == ')' || c5 == '<' || c5 == '>' || (c5 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[c5] == 4)) {
                    i2 += 4;
                }
                i9 = -1;
                if (i18 == -1) {
                    i18 = i20;
                    i19 = i18;
                } else {
                    i19 = i20;
                }
            }
            c2 = c5;
        }
        if (i17 > 0) {
            int i21 = i2 + i17;
            if (i21 > this.buf.length) {
                expandCapacity(i21);
            }
            this.count = i21;
            if (i17 == 1) {
                if (c2 == 8232) {
                    int i22 = i19 + 1;
                    char[] cArr8 = this.buf;
                    System.arraycopy(cArr8, i22, cArr8, i19 + 6, (i8 - i19) - 1);
                    char[] cArr9 = this.buf;
                    cArr9[i19] = '\\';
                    cArr9[i22] = 'u';
                    cArr9[i19 + 2] = '2';
                    cArr9[i19 + 3] = '0';
                    cArr9[i19 + 4] = '2';
                    cArr9[i19 + 5] = '8';
                } else if (c2 == 8233) {
                    int i23 = i19 + 1;
                    char[] cArr10 = this.buf;
                    System.arraycopy(cArr10, i23, cArr10, i19 + 6, (i8 - i19) - 1);
                    char[] cArr11 = this.buf;
                    cArr11[i19] = '\\';
                    cArr11[i23] = 'u';
                    cArr11[i19 + 2] = '2';
                    cArr11[i19 + 3] = '0';
                    cArr11[i19 + 4] = '2';
                    cArr11[i19 + 5] = '9';
                } else if (c2 == '(' || c2 == ')' || c2 == '<' || c2 == '>') {
                    int i24 = i19 + 1;
                    char[] cArr12 = this.buf;
                    System.arraycopy(cArr12, i24, cArr12, i19 + 6, (i8 - i19) - 1);
                    char[] cArr13 = this.buf;
                    cArr13[i19] = '\\';
                    cArr13[i24] = 'u';
                    cArr13[i19 + 2] = IOUtils.DIGITS[(c2 >>> '\f') & 15];
                    this.buf[i19 + 3] = IOUtils.DIGITS[(c2 >>> '\b') & 15];
                    this.buf[i19 + 4] = IOUtils.DIGITS[(c2 >>> 4) & 15];
                    this.buf[i19 + 5] = IOUtils.DIGITS[c2 & 15];
                } else if (c2 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[c2] == 4) {
                    int i25 = i19 + 1;
                    char[] cArr14 = this.buf;
                    System.arraycopy(cArr14, i25, cArr14, i19 + 6, (i8 - i19) - 1);
                    char[] cArr15 = this.buf;
                    cArr15[i19] = '\\';
                    cArr15[i25] = 'u';
                    cArr15[i19 + 2] = IOUtils.DIGITS[(c2 >>> '\f') & 15];
                    this.buf[i19 + 3] = IOUtils.DIGITS[(c2 >>> '\b') & 15];
                    this.buf[i19 + 4] = IOUtils.DIGITS[(c2 >>> 4) & 15];
                    this.buf[i19 + 5] = IOUtils.DIGITS[c2 & 15];
                } else {
                    int i26 = i19 + 1;
                    char[] cArr16 = this.buf;
                    System.arraycopy(cArr16, i26, cArr16, i19 + 2, (i8 - i19) - 1);
                    char[] cArr17 = this.buf;
                    cArr17[i19] = '\\';
                    cArr17[i26] = IOUtils.replaceChars[c2];
                }
            } else if (i17 > 1) {
                for (int i27 = i18 - i7; i27 < str.length(); i27++) {
                    char cCharAt2 = str.charAt(i27);
                    if (this.browserSecure) {
                        if (cCharAt2 != '(' && cCharAt2 != ')') {
                            if (cCharAt2 == '<' || cCharAt2 == '>') {
                            }
                        }
                        char[] cArr18 = this.buf;
                        cArr18[i18] = '\\';
                        cArr18[i18 + 1] = 'u';
                        cArr18[i18 + 2] = IOUtils.DIGITS[(cCharAt2 >>> '\f') & 15];
                        this.buf[i18 + 3] = IOUtils.DIGITS[(cCharAt2 >>> '\b') & 15];
                        int i28 = i18 + 5;
                        this.buf[i18 + 4] = IOUtils.DIGITS[(cCharAt2 >>> 4) & 15];
                        i18 += 6;
                        this.buf[i28] = IOUtils.DIGITS[cCharAt2 & 15];
                    }
                    if ((cCharAt2 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[cCharAt2] != 0) || (cCharAt2 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        int i29 = i18 + 1;
                        this.buf[i18] = '\\';
                        if (IOUtils.specicalFlags_doubleQuotes[cCharAt2] == 4) {
                            char[] cArr19 = this.buf;
                            cArr19[i29] = 'u';
                            cArr19[i18 + 2] = IOUtils.DIGITS[(cCharAt2 >>> '\f') & 15];
                            this.buf[i18 + 3] = IOUtils.DIGITS[(cCharAt2 >>> '\b') & 15];
                            int i30 = i18 + 5;
                            this.buf[i18 + 4] = IOUtils.DIGITS[(cCharAt2 >>> 4) & 15];
                            i18 += 6;
                            this.buf[i30] = IOUtils.DIGITS[cCharAt2 & 15];
                        } else {
                            i18 += 2;
                            this.buf[i29] = IOUtils.replaceChars[cCharAt2];
                        }
                    } else if (cCharAt2 == 8232 || cCharAt2 == 8233) {
                        char[] cArr20 = this.buf;
                        cArr20[i18] = '\\';
                        cArr20[i18 + 1] = 'u';
                        cArr20[i18 + 2] = IOUtils.DIGITS[(cCharAt2 >>> '\f') & 15];
                        this.buf[i18 + 3] = IOUtils.DIGITS[(cCharAt2 >>> '\b') & 15];
                        int i31 = i18 + 5;
                        this.buf[i18 + 4] = IOUtils.DIGITS[(cCharAt2 >>> 4) & 15];
                        i18 += 6;
                        this.buf[i31] = IOUtils.DIGITS[cCharAt2 & 15];
                    } else {
                        this.buf[i18] = cCharAt2;
                        i18++;
                    }
                }
            }
        }
        if (c != 0) {
            char[] cArr21 = this.buf;
            int i32 = this.count;
            cArr21[i32 - 2] = '\"';
            cArr21[i32 - 1] = c;
            return;
        }
        this.buf[this.count - 1] = '\"';
    }

    public void writeStringWithDoubleQuote(char[] cArr, char c) {
        int i;
        if (cArr == null) {
            writeNull();
            if (c != 0) {
                write(c);
                return;
            }
            return;
        }
        int length = cArr.length;
        int i2 = this.count + length;
        int i3 = i2 + 2;
        if (c != 0) {
            i3 = i2 + 3;
        }
        int i4 = 1;
        if (i3 > this.buf.length) {
            if (this.writer != null) {
                write(34);
                int i5 = 0;
                while (i5 < cArr.length) {
                    char c2 = cArr[i5];
                    if (isEnabled(SerializerFeature.BrowserSecure) && (c2 == '(' || c2 == ')' || c2 == '<' || c2 == '>')) {
                        write(92);
                        write(117);
                        write(IOUtils.DIGITS[(c2 >>> '\f') & 15]);
                        write(IOUtils.DIGITS[(c2 >>> '\b') & 15]);
                        write(IOUtils.DIGITS[(c2 >>> 4) & 15]);
                        write(IOUtils.DIGITS[c2 & 15]);
                    } else {
                        if (!isEnabled(SerializerFeature.BrowserCompatible)) {
                            if ((c2 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[c2] != 0) || (c2 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                                write(92);
                                if (IOUtils.specicalFlags_doubleQuotes[c2] == 4) {
                                    write(117);
                                    write(IOUtils.DIGITS[(c2 >>> '\f') & 15]);
                                    write(IOUtils.DIGITS[(c2 >>> '\b') & 15]);
                                    write(IOUtils.DIGITS[(c2 >>> 4) & 15]);
                                    write(IOUtils.DIGITS[c2 & 15]);
                                } else {
                                    write(IOUtils.replaceChars[c2]);
                                }
                            }
                            i5++;
                            i4 = 1;
                        } else if (c2 == '\b' || c2 == '\f' || c2 == '\n' || c2 == '\r' || c2 == '\t' || c2 == '\"' || c2 == '/' || c2 == '\\') {
                            write(92);
                            write(IOUtils.replaceChars[c2]);
                        } else if (c2 < ' ') {
                            write(92);
                            write(117);
                            write(48);
                            write(48);
                            int i6 = c2 * 2;
                            write(IOUtils.ASCII_CHARS[i6]);
                            write(IOUtils.ASCII_CHARS[i6 + i4]);
                        } else if (c2 >= 127) {
                            write(92);
                            write(117);
                            write(IOUtils.DIGITS[(c2 >>> '\f') & 15]);
                            write(IOUtils.DIGITS[(c2 >>> '\b') & 15]);
                            write(IOUtils.DIGITS[(c2 >>> 4) & 15]);
                            write(IOUtils.DIGITS[c2 & 15]);
                        }
                        write(c2);
                        i5++;
                        i4 = 1;
                    }
                    i5++;
                    i4 = 1;
                }
                write(34);
                if (c != 0) {
                    write(c);
                    return;
                }
                return;
            }
            expandCapacity(i3);
        }
        int i7 = this.count;
        int i8 = i7 + 1;
        int i9 = length + i8;
        char[] cArr2 = this.buf;
        cArr2[i7] = '\"';
        char c3 = 0;
        System.arraycopy(cArr, 0, cArr2, i8, cArr.length);
        this.count = i3;
        int i10 = -1;
        if (isEnabled(SerializerFeature.BrowserCompatible)) {
            for (int i11 = i8; i11 < i9; i11++) {
                char c4 = this.buf[i11];
                if (c4 == '\"' || c4 == '/' || c4 == '\\' || c4 == '\b' || c4 == '\f' || c4 == '\n' || c4 == '\r' || c4 == '\t') {
                    i3++;
                } else if (c4 < ' ' || c4 >= 127) {
                    i3 += 5;
                }
                i10 = i11;
            }
            if (i3 > this.buf.length) {
                expandCapacity(i3);
            }
            this.count = i3;
            while (i10 >= i8) {
                char[] cArr3 = this.buf;
                char c5 = cArr3[i10];
                if (c5 == '\b' || c5 == '\f' || c5 == '\n' || c5 == '\r' || c5 == '\t') {
                    int i12 = i10 + 1;
                    System.arraycopy(cArr3, i12, cArr3, i10 + 2, (i9 - i10) - 1);
                    char[] cArr4 = this.buf;
                    cArr4[i10] = '\\';
                    cArr4[i12] = IOUtils.replaceChars[c5];
                } else if (c5 == '\"' || c5 == '/' || c5 == '\\') {
                    int i13 = i10 + 1;
                    System.arraycopy(cArr3, i13, cArr3, i10 + 2, (i9 - i10) - 1);
                    char[] cArr5 = this.buf;
                    cArr5[i10] = '\\';
                    cArr5[i13] = c5;
                } else {
                    if (c5 < ' ') {
                        int i14 = i10 + 1;
                        System.arraycopy(cArr3, i14, cArr3, i10 + 6, (i9 - i10) - 1);
                        char[] cArr6 = this.buf;
                        cArr6[i10] = '\\';
                        cArr6[i14] = 'u';
                        cArr6[i10 + 2] = '0';
                        cArr6[i10 + 3] = '0';
                        int i15 = c5 * 2;
                        cArr6[i10 + 4] = IOUtils.ASCII_CHARS[i15];
                        this.buf[i10 + 5] = IOUtils.ASCII_CHARS[i15 + 1];
                    } else if (c5 >= 127) {
                        int i16 = i10 + 1;
                        System.arraycopy(cArr3, i16, cArr3, i10 + 6, (i9 - i10) - 1);
                        char[] cArr7 = this.buf;
                        cArr7[i10] = '\\';
                        cArr7[i16] = 'u';
                        cArr7[i10 + 2] = IOUtils.DIGITS[(c5 >>> '\f') & 15];
                        this.buf[i10 + 3] = IOUtils.DIGITS[(c5 >>> '\b') & 15];
                        this.buf[i10 + 4] = IOUtils.DIGITS[(c5 >>> 4) & 15];
                        this.buf[i10 + 5] = IOUtils.DIGITS[c5 & 15];
                    } else {
                        i10--;
                    }
                    i9 += 5;
                    i10--;
                }
                i9++;
                i10--;
            }
            if (c != 0) {
                char[] cArr8 = this.buf;
                int i17 = this.count;
                cArr8[i17 - 2] = '\"';
                cArr8[i17 - 1] = c;
                return;
            }
            this.buf[this.count - 1] = '\"';
            return;
        }
        int i18 = i8;
        int i19 = 0;
        int i20 = -1;
        int i21 = -1;
        while (i18 < i9) {
            char c6 = this.buf[i18];
            if (c6 < ']') {
                i = i19;
                if ((c6 >= '@' || (this.sepcialBits & (1 << c6)) == 0) && c6 != '\\') {
                    i19 = i;
                } else {
                    i19 = i + 1;
                    if (c6 == '(' || c6 == ')' || c6 == '<' || c6 == '>' || (c6 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[c6] == 4)) {
                        i3 += 4;
                    }
                    if (i20 == -1) {
                        i20 = i18;
                        i21 = i20;
                    } else {
                        i21 = i18;
                    }
                    c3 = c6;
                }
            } else if (c6 < 127 || !(c6 == 8232 || c6 == 8233 || c6 < 160)) {
                i = i19;
                i19 = i;
            } else {
                if (i20 == i10) {
                    i20 = i18;
                }
                i19++;
                i3 += 4;
                i21 = i18;
                c3 = c6;
            }
            i18++;
            i10 = -1;
        }
        int i22 = i19;
        if (i22 > 0) {
            int i23 = i3 + i22;
            if (i23 > this.buf.length) {
                expandCapacity(i23);
            }
            this.count = i23;
            if (i22 == 1) {
                if (c3 == 8232) {
                    int i24 = i21 + 1;
                    char[] cArr9 = this.buf;
                    System.arraycopy(cArr9, i24, cArr9, i21 + 6, (i9 - i21) - 1);
                    char[] cArr10 = this.buf;
                    cArr10[i21] = '\\';
                    cArr10[i24] = 'u';
                    cArr10[i21 + 2] = '2';
                    cArr10[i21 + 3] = '0';
                    cArr10[i21 + 4] = '2';
                    cArr10[i21 + 5] = '8';
                } else if (c3 == 8233) {
                    int i25 = i21 + 1;
                    char[] cArr11 = this.buf;
                    System.arraycopy(cArr11, i25, cArr11, i21 + 6, (i9 - i21) - 1);
                    char[] cArr12 = this.buf;
                    cArr12[i21] = '\\';
                    cArr12[i25] = 'u';
                    cArr12[i21 + 2] = '2';
                    cArr12[i21 + 3] = '0';
                    cArr12[i21 + 4] = '2';
                    cArr12[i21 + 5] = '9';
                } else if (c3 == '(' || c3 == ')' || c3 == '<' || c3 == '>') {
                    int i26 = i21 + 1;
                    char[] cArr13 = this.buf;
                    System.arraycopy(cArr13, i26, cArr13, i21 + 6, (i9 - i21) - 1);
                    char[] cArr14 = this.buf;
                    cArr14[i21] = '\\';
                    cArr14[i26] = 'u';
                    cArr14[i21 + 2] = IOUtils.DIGITS[(c3 >>> '\f') & 15];
                    this.buf[i21 + 3] = IOUtils.DIGITS[(c3 >>> '\b') & 15];
                    this.buf[i21 + 4] = IOUtils.DIGITS[(c3 >>> 4) & 15];
                    this.buf[i21 + 5] = IOUtils.DIGITS[c3 & 15];
                } else if (c3 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[c3] == 4) {
                    int i27 = i21 + 1;
                    char[] cArr15 = this.buf;
                    System.arraycopy(cArr15, i27, cArr15, i21 + 6, (i9 - i21) - 1);
                    char[] cArr16 = this.buf;
                    cArr16[i21] = '\\';
                    cArr16[i27] = 'u';
                    cArr16[i21 + 2] = IOUtils.DIGITS[(c3 >>> '\f') & 15];
                    this.buf[i21 + 3] = IOUtils.DIGITS[(c3 >>> '\b') & 15];
                    this.buf[i21 + 4] = IOUtils.DIGITS[(c3 >>> 4) & 15];
                    this.buf[i21 + 5] = IOUtils.DIGITS[c3 & 15];
                } else {
                    int i28 = i21 + 1;
                    char[] cArr17 = this.buf;
                    System.arraycopy(cArr17, i28, cArr17, i21 + 2, (i9 - i21) - 1);
                    char[] cArr18 = this.buf;
                    cArr18[i21] = '\\';
                    cArr18[i28] = IOUtils.replaceChars[c3];
                }
            } else if (i22 > 1) {
                for (int i29 = i20 - i8; i29 < cArr.length; i29++) {
                    char c7 = cArr[i29];
                    if (this.browserSecure) {
                        if (c7 != '(' && c7 != ')') {
                            if (c7 == '<' || c7 == '>') {
                            }
                        }
                        char[] cArr19 = this.buf;
                        cArr19[i20] = '\\';
                        cArr19[i20 + 1] = 'u';
                        cArr19[i20 + 2] = IOUtils.DIGITS[(c7 >>> '\f') & 15];
                        this.buf[i20 + 3] = IOUtils.DIGITS[(c7 >>> '\b') & 15];
                        int i30 = i20 + 5;
                        this.buf[i20 + 4] = IOUtils.DIGITS[(c7 >>> 4) & 15];
                        i20 += 6;
                        this.buf[i30] = IOUtils.DIGITS[c7 & 15];
                    }
                    if ((c7 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[c7] != 0) || (c7 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        int i31 = i20 + 1;
                        this.buf[i20] = '\\';
                        if (IOUtils.specicalFlags_doubleQuotes[c7] == 4) {
                            char[] cArr20 = this.buf;
                            cArr20[i31] = 'u';
                            cArr20[i20 + 2] = IOUtils.DIGITS[(c7 >>> '\f') & 15];
                            this.buf[i20 + 3] = IOUtils.DIGITS[(c7 >>> '\b') & 15];
                            int i32 = i20 + 5;
                            this.buf[i20 + 4] = IOUtils.DIGITS[(c7 >>> 4) & 15];
                            i20 += 6;
                            this.buf[i32] = IOUtils.DIGITS[c7 & 15];
                        } else {
                            i20 += 2;
                            this.buf[i31] = IOUtils.replaceChars[c7];
                        }
                    } else if (c7 == 8232 || c7 == 8233) {
                        char[] cArr21 = this.buf;
                        cArr21[i20] = '\\';
                        cArr21[i20 + 1] = 'u';
                        cArr21[i20 + 2] = IOUtils.DIGITS[(c7 >>> '\f') & 15];
                        this.buf[i20 + 3] = IOUtils.DIGITS[(c7 >>> '\b') & 15];
                        int i33 = i20 + 5;
                        this.buf[i20 + 4] = IOUtils.DIGITS[(c7 >>> 4) & 15];
                        i20 += 6;
                        this.buf[i33] = IOUtils.DIGITS[c7 & 15];
                    } else {
                        this.buf[i20] = c7;
                        i20++;
                    }
                }
            }
        }
        if (c != 0) {
            char[] cArr22 = this.buf;
            int i34 = this.count;
            cArr22[i34 - 2] = '\"';
            cArr22[i34 - 1] = c;
            return;
        }
        this.buf[this.count - 1] = '\"';
    }

    public void writeFieldNameDirect(String str) {
        int length = str.length();
        int i = this.count + length;
        int i2 = i + 3;
        if (i2 > this.buf.length) {
            expandCapacity(i2);
        }
        int i3 = this.count;
        char[] cArr = this.buf;
        cArr[i3] = '\"';
        str.getChars(0, length, cArr, i3 + 1);
        this.count = i2;
        char[] cArr2 = this.buf;
        cArr2[i + 1] = '\"';
        cArr2[i + 2] = Operators.CONDITION_IF_MIDDLE;
    }

    public void write(List<String> list) {
        boolean z;
        int i;
        if (list.isEmpty()) {
            write("[]");
            return;
        }
        int i2 = this.count;
        int size = list.size();
        int i3 = i2;
        int i4 = 0;
        while (i4 < size) {
            String str = list.get(i4);
            if (str == null) {
                z = true;
            } else {
                int length = str.length();
                z = false;
                for (int i5 = 0; i5 < length; i5++) {
                    char cCharAt = str.charAt(i5);
                    z = cCharAt < ' ' || cCharAt > '~' || cCharAt == '\"' || cCharAt == '\\';
                    if (z) {
                        break;
                    }
                }
            }
            if (z) {
                this.count = i2;
                write(91);
                for (int i6 = 0; i6 < list.size(); i6++) {
                    String str2 = list.get(i6);
                    if (i6 != 0) {
                        write(44);
                    }
                    if (str2 == null) {
                        write("null");
                    } else {
                        writeStringWithDoubleQuote(str2, (char) 0);
                    }
                }
                write(93);
                return;
            }
            int length2 = str.length() + i3;
            int i7 = length2 + 3;
            if (i4 == list.size() - 1) {
                i7 = length2 + 4;
            }
            if (i7 > this.buf.length) {
                this.count = i3;
                expandCapacity(i7);
            }
            if (i4 == 0) {
                i = i3 + 1;
                this.buf[i3] = Operators.ARRAY_START;
            } else {
                i = i3 + 1;
                this.buf[i3] = Operators.ARRAY_SEPRATOR;
            }
            int i8 = i + 1;
            this.buf[i] = '\"';
            str.getChars(0, str.length(), this.buf, i8);
            int length3 = i8 + str.length();
            this.buf[length3] = '\"';
            i4++;
            i3 = length3 + 1;
        }
        this.buf[i3] = Operators.ARRAY_END;
        this.count = i3 + 1;
    }

    public void writeFieldValue(char c, String str, char c2) {
        write(c);
        writeFieldName(str);
        if (c2 == 0) {
            writeString("\u0000");
        } else {
            writeString(Character.toString(c2));
        }
    }

    public void writeFieldValue(char c, String str, boolean z) {
        if (!this.quoteFieldNames) {
            write(c);
            writeFieldName(str);
            write(z);
            return;
        }
        int i = z ? 4 : 5;
        int length = str.length();
        int i2 = this.count + length + 4 + i;
        if (i2 > this.buf.length) {
            if (this.writer != null) {
                write(c);
                writeString(str);
                write(58);
                write(z);
                return;
            }
            expandCapacity(i2);
        }
        int i3 = this.count;
        this.count = i2;
        char[] cArr = this.buf;
        cArr[i3] = c;
        int i4 = i3 + length;
        cArr[i3 + 1] = this.keySeperator;
        str.getChars(0, length, cArr, i3 + 2);
        char[] cArr2 = this.buf;
        cArr2[i4 + 2] = this.keySeperator;
        if (z) {
            System.arraycopy(VALUE_TRUE, 0, cArr2, i4 + 3, 5);
        } else {
            System.arraycopy(VALUE_FALSE, 0, cArr2, i4 + 3, 6);
        }
    }

    public void write(boolean z) {
        if (z) {
            write(AbsoluteConst.TRUE);
        } else {
            write(AbsoluteConst.FALSE);
        }
    }

    public void writeFieldValue(char c, String str, int i) {
        if (i == Integer.MIN_VALUE || !this.quoteFieldNames) {
            write(c);
            writeFieldName(str);
            writeInt(i);
            return;
        }
        int iStringSize = i < 0 ? IOUtils.stringSize(-i) + 1 : IOUtils.stringSize(i);
        int length = str.length();
        int i2 = this.count + length + 4 + iStringSize;
        if (i2 > this.buf.length) {
            if (this.writer != null) {
                write(c);
                writeFieldName(str);
                writeInt(i);
                return;
            }
            expandCapacity(i2);
        }
        int i3 = this.count;
        this.count = i2;
        char[] cArr = this.buf;
        cArr[i3] = c;
        int i4 = i3 + length;
        cArr[i3 + 1] = this.keySeperator;
        str.getChars(0, length, cArr, i3 + 2);
        char[] cArr2 = this.buf;
        cArr2[i4 + 2] = this.keySeperator;
        cArr2[i4 + 3] = Operators.CONDITION_IF_MIDDLE;
        IOUtils.getChars(i, this.count, cArr2);
    }

    public void writeFieldValue(char c, String str, long j) {
        if (j == Long.MIN_VALUE || !this.quoteFieldNames || isEnabled(SerializerFeature.BrowserCompatible.mask)) {
            write(c);
            writeFieldName(str);
            writeLong(j);
            return;
        }
        int iStringSize = j < 0 ? IOUtils.stringSize(-j) + 1 : IOUtils.stringSize(j);
        int length = str.length();
        int i = this.count + length + 4 + iStringSize;
        if (i > this.buf.length) {
            if (this.writer != null) {
                write(c);
                writeFieldName(str);
                writeLong(j);
                return;
            }
            expandCapacity(i);
        }
        int i2 = this.count;
        this.count = i;
        char[] cArr = this.buf;
        cArr[i2] = c;
        int i3 = i2 + length;
        cArr[i2 + 1] = this.keySeperator;
        str.getChars(0, length, cArr, i2 + 2);
        char[] cArr2 = this.buf;
        cArr2[i3 + 2] = this.keySeperator;
        cArr2[i3 + 3] = Operators.CONDITION_IF_MIDDLE;
        IOUtils.getChars(j, this.count, cArr2);
    }

    public void writeFieldValue(char c, String str, float f) throws IOException {
        write(c);
        writeFieldName(str);
        writeFloat(f, false);
    }

    public void writeFieldValue(char c, String str, double d) throws IOException {
        write(c);
        writeFieldName(str);
        writeDouble(d, false);
    }

    public void writeFieldValue(char c, String str, String str2) {
        if (this.quoteFieldNames) {
            if (this.useSingleQuotes) {
                write(c);
                writeFieldName(str);
                if (str2 == null) {
                    writeNull();
                    return;
                } else {
                    writeString(str2);
                    return;
                }
            }
            if (isEnabled(SerializerFeature.BrowserCompatible)) {
                write(c);
                writeStringWithDoubleQuote(str, Operators.CONDITION_IF_MIDDLE);
                writeStringWithDoubleQuote(str2, (char) 0);
                return;
            }
            writeFieldValueStringWithDoubleQuoteCheck(c, str, str2);
            return;
        }
        write(c);
        writeFieldName(str);
        if (str2 == null) {
            writeNull();
        } else {
            writeString(str2);
        }
    }

    public void writeFieldValueStringWithDoubleQuoteCheck(char c, String str, String str2) {
        int length;
        int i;
        int length2 = str.length();
        int i2 = this.count;
        if (str2 == null) {
            i = i2 + length2 + 8;
            length = 4;
        } else {
            length = str2.length();
            i = i2 + length2 + length + 6;
        }
        int i3 = 0;
        if (i > this.buf.length) {
            if (this.writer != null) {
                write(c);
                writeStringWithDoubleQuote(str, Operators.CONDITION_IF_MIDDLE);
                writeStringWithDoubleQuote(str2, (char) 0);
                return;
            }
            expandCapacity(i);
        }
        char[] cArr = this.buf;
        int i4 = this.count;
        cArr[i4] = c;
        int i5 = i4 + 2;
        int i6 = i5 + length2;
        cArr[i4 + 1] = '\"';
        str.getChars(0, length2, cArr, i5);
        this.count = i;
        char[] cArr2 = this.buf;
        cArr2[i6] = '\"';
        int i7 = i6 + 2;
        cArr2[i6 + 1] = Operators.CONDITION_IF_MIDDLE;
        if (str2 == null) {
            cArr2[i7] = 'n';
            cArr2[i6 + 3] = 'u';
            cArr2[i6 + 4] = 'l';
            cArr2[i6 + 5] = 'l';
            return;
        }
        int i8 = i6 + 3;
        cArr2[i7] = '\"';
        int i9 = i8 + length;
        str2.getChars(0, length, cArr2, i8);
        int i10 = -1;
        int i11 = i8;
        char c2 = 0;
        int i12 = -1;
        int i13 = -1;
        while (i11 < i9) {
            char c3 = this.buf[i11];
            if (c3 < ']') {
                if ((c3 < '@' && (this.sepcialBits & (1 << c3)) != 0) || c3 == '\\') {
                    i3++;
                    if (c3 == '(' || c3 == ')' || c3 == '<' || c3 == '>' || (c3 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[c3] == 4)) {
                        i += 4;
                    }
                    if (i12 == -1) {
                        i12 = i11;
                        i13 = i12;
                    } else {
                        i13 = i11;
                    }
                    c2 = c3;
                }
                i11++;
                i10 = -1;
            } else if (c3 >= 127 && (c3 == 8232 || c3 == 8233 || c3 < 160)) {
                if (i12 == i10) {
                    i12 = i11;
                }
                i3++;
                i += 4;
                i13 = i11;
                c2 = c3;
            }
            i11++;
            i10 = -1;
        }
        if (i3 > 0) {
            int i14 = i + i3;
            if (i14 > this.buf.length) {
                expandCapacity(i14);
            }
            this.count = i14;
            if (i3 == 1) {
                if (c2 == 8232) {
                    int i15 = i13 + 1;
                    char[] cArr3 = this.buf;
                    System.arraycopy(cArr3, i15, cArr3, i13 + 6, (i9 - i13) - 1);
                    char[] cArr4 = this.buf;
                    cArr4[i13] = '\\';
                    cArr4[i15] = 'u';
                    cArr4[i13 + 2] = '2';
                    cArr4[i13 + 3] = '0';
                    cArr4[i13 + 4] = '2';
                    cArr4[i13 + 5] = '8';
                } else if (c2 == 8233) {
                    int i16 = i13 + 1;
                    char[] cArr5 = this.buf;
                    System.arraycopy(cArr5, i16, cArr5, i13 + 6, (i9 - i13) - 1);
                    char[] cArr6 = this.buf;
                    cArr6[i13] = '\\';
                    cArr6[i16] = 'u';
                    cArr6[i13 + 2] = '2';
                    cArr6[i13 + 3] = '0';
                    cArr6[i13 + 4] = '2';
                    cArr6[i13 + 5] = '9';
                } else if (c2 == '(' || c2 == ')' || c2 == '<' || c2 == '>') {
                    int i17 = i13 + 1;
                    char[] cArr7 = this.buf;
                    System.arraycopy(cArr7, i17, cArr7, i13 + 6, (i9 - i13) - 1);
                    char[] cArr8 = this.buf;
                    cArr8[i13] = '\\';
                    cArr8[i17] = 'u';
                    cArr8[i13 + 2] = IOUtils.DIGITS[(c2 >>> '\f') & 15];
                    this.buf[i13 + 3] = IOUtils.DIGITS[(c2 >>> '\b') & 15];
                    this.buf[i13 + 4] = IOUtils.DIGITS[(c2 >>> 4) & 15];
                    this.buf[i13 + 5] = IOUtils.DIGITS[c2 & 15];
                } else if (c2 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[c2] == 4) {
                    int i18 = i13 + 1;
                    char[] cArr9 = this.buf;
                    System.arraycopy(cArr9, i18, cArr9, i13 + 6, (i9 - i13) - 1);
                    char[] cArr10 = this.buf;
                    cArr10[i13] = '\\';
                    cArr10[i18] = 'u';
                    cArr10[i13 + 2] = IOUtils.DIGITS[(c2 >>> '\f') & 15];
                    this.buf[i13 + 3] = IOUtils.DIGITS[(c2 >>> '\b') & 15];
                    this.buf[i13 + 4] = IOUtils.DIGITS[(c2 >>> 4) & 15];
                    this.buf[i13 + 5] = IOUtils.DIGITS[c2 & 15];
                } else {
                    int i19 = i13 + 1;
                    char[] cArr11 = this.buf;
                    System.arraycopy(cArr11, i19, cArr11, i13 + 2, (i9 - i13) - 1);
                    char[] cArr12 = this.buf;
                    cArr12[i13] = '\\';
                    cArr12[i19] = IOUtils.replaceChars[c2];
                }
            } else if (i3 > 1) {
                for (int i20 = i12 - i8; i20 < str2.length(); i20++) {
                    char cCharAt = str2.charAt(i20);
                    if (this.browserSecure) {
                        if (cCharAt != '(' && cCharAt != ')') {
                            if (cCharAt == '<' || cCharAt == '>') {
                            }
                        }
                        char[] cArr13 = this.buf;
                        cArr13[i12] = '\\';
                        cArr13[i12 + 1] = 'u';
                        cArr13[i12 + 2] = IOUtils.DIGITS[(cCharAt >>> '\f') & 15];
                        this.buf[i12 + 3] = IOUtils.DIGITS[(cCharAt >>> '\b') & 15];
                        int i21 = i12 + 5;
                        this.buf[i12 + 4] = IOUtils.DIGITS[(cCharAt >>> 4) & 15];
                        i12 += 6;
                        this.buf[i21] = IOUtils.DIGITS[cCharAt & 15];
                    }
                    if ((cCharAt < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[cCharAt] != 0) || (cCharAt == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        int i22 = i12 + 1;
                        this.buf[i12] = '\\';
                        if (IOUtils.specicalFlags_doubleQuotes[cCharAt] == 4) {
                            char[] cArr14 = this.buf;
                            cArr14[i22] = 'u';
                            cArr14[i12 + 2] = IOUtils.DIGITS[(cCharAt >>> '\f') & 15];
                            this.buf[i12 + 3] = IOUtils.DIGITS[(cCharAt >>> '\b') & 15];
                            int i23 = i12 + 5;
                            this.buf[i12 + 4] = IOUtils.DIGITS[(cCharAt >>> 4) & 15];
                            i12 += 6;
                            this.buf[i23] = IOUtils.DIGITS[cCharAt & 15];
                        } else {
                            i12 += 2;
                            this.buf[i22] = IOUtils.replaceChars[cCharAt];
                        }
                    } else if (cCharAt == 8232 || cCharAt == 8233) {
                        char[] cArr15 = this.buf;
                        cArr15[i12] = '\\';
                        cArr15[i12 + 1] = 'u';
                        cArr15[i12 + 2] = IOUtils.DIGITS[(cCharAt >>> '\f') & 15];
                        this.buf[i12 + 3] = IOUtils.DIGITS[(cCharAt >>> '\b') & 15];
                        int i24 = i12 + 5;
                        this.buf[i12 + 4] = IOUtils.DIGITS[(cCharAt >>> 4) & 15];
                        i12 += 6;
                        this.buf[i24] = IOUtils.DIGITS[cCharAt & 15];
                    } else {
                        this.buf[i12] = cCharAt;
                        i12++;
                    }
                }
            }
        }
        this.buf[this.count - 1] = '\"';
    }

    public void writeFieldValueStringWithDoubleQuote(char c, String str, String str2) {
        int length = str.length();
        int i = this.count;
        int length2 = str2.length();
        int i2 = i + length + length2 + 6;
        if (i2 > this.buf.length) {
            if (this.writer != null) {
                write(c);
                writeStringWithDoubleQuote(str, Operators.CONDITION_IF_MIDDLE);
                writeStringWithDoubleQuote(str2, (char) 0);
                return;
            }
            expandCapacity(i2);
        }
        char[] cArr = this.buf;
        int i3 = this.count;
        cArr[i3] = c;
        int i4 = i3 + 2;
        int i5 = i4 + length;
        cArr[i3 + 1] = '\"';
        str.getChars(0, length, cArr, i4);
        this.count = i2;
        char[] cArr2 = this.buf;
        cArr2[i5] = '\"';
        cArr2[i5 + 1] = Operators.CONDITION_IF_MIDDLE;
        cArr2[i5 + 2] = '\"';
        str2.getChars(0, length2, cArr2, i5 + 3);
        this.buf[this.count - 1] = '\"';
    }

    public void writeFieldValue(char c, String str, Enum<?> r4) {
        if (r4 == null) {
            write(c);
            writeFieldName(str);
            writeNull();
        } else if (this.writeEnumUsingName && !this.writeEnumUsingToString) {
            writeEnumFieldValue(c, str, r4.name());
        } else if (this.writeEnumUsingToString) {
            writeEnumFieldValue(c, str, r4.toString());
        } else {
            writeFieldValue(c, str, r4.ordinal());
        }
    }

    private void writeEnumFieldValue(char c, String str, String str2) {
        if (this.useSingleQuotes) {
            writeFieldValue(c, str, str2);
        } else {
            writeFieldValueStringWithDoubleQuote(c, str, str2);
        }
    }

    public void writeFieldValue(char c, String str, BigDecimal bigDecimal) {
        String string;
        write(c);
        writeFieldName(str);
        if (bigDecimal == null) {
            writeNull();
            return;
        }
        int iScale = bigDecimal.scale();
        if (isEnabled(SerializerFeature.WriteBigDecimalAsPlain) && iScale >= -100 && iScale < 100) {
            string = bigDecimal.toPlainString();
        } else {
            string = bigDecimal.toString();
        }
        write(string);
    }

    public void writeString(String str, char c) {
        if (this.useSingleQuotes) {
            writeStringWithSingleQuote(str);
            write(c);
        } else {
            writeStringWithDoubleQuote(str, c);
        }
    }

    public void writeString(String str) {
        if (this.useSingleQuotes) {
            writeStringWithSingleQuote(str);
        } else {
            writeStringWithDoubleQuote(str, (char) 0);
        }
    }

    public void writeString(char[] cArr) {
        if (this.useSingleQuotes) {
            writeStringWithSingleQuote(cArr);
        } else {
            writeStringWithDoubleQuote(new String(cArr), (char) 0);
        }
    }

    protected void writeStringWithSingleQuote(String str) {
        int i = 0;
        if (str == null) {
            int i2 = this.count + 4;
            if (i2 > this.buf.length) {
                expandCapacity(i2);
            }
            "null".getChars(0, 4, this.buf, this.count);
            this.count = i2;
            return;
        }
        int length = str.length();
        int i3 = this.count + length + 2;
        if (i3 > this.buf.length) {
            if (this.writer != null) {
                write(39);
                while (i < str.length()) {
                    char cCharAt = str.charAt(i);
                    if (cCharAt <= '\r' || cCharAt == '\\' || cCharAt == '\'' || (cCharAt == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        write(92);
                        write(IOUtils.replaceChars[cCharAt]);
                    } else {
                        write(cCharAt);
                    }
                    i++;
                }
                write(39);
                return;
            }
            expandCapacity(i3);
        }
        int i4 = this.count;
        int i5 = i4 + 1;
        int i6 = i5 + length;
        char[] cArr = this.buf;
        cArr[i4] = Operators.SINGLE_QUOTE;
        str.getChars(0, length, cArr, i5);
        this.count = i3;
        char c = 0;
        int i7 = -1;
        for (int i8 = i5; i8 < i6; i8++) {
            char c2 = this.buf[i8];
            if (c2 <= '\r' || c2 == '\\' || c2 == '\'' || (c2 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                i++;
                i7 = i8;
                c = c2;
            }
        }
        int i9 = i3 + i;
        if (i9 > this.buf.length) {
            expandCapacity(i9);
        }
        this.count = i9;
        if (i == 1) {
            char[] cArr2 = this.buf;
            int i10 = i7 + 1;
            System.arraycopy(cArr2, i10, cArr2, i7 + 2, (i6 - i7) - 1);
            char[] cArr3 = this.buf;
            cArr3[i7] = '\\';
            cArr3[i10] = IOUtils.replaceChars[c];
        } else if (i > 1) {
            char[] cArr4 = this.buf;
            int i11 = i7 + 1;
            System.arraycopy(cArr4, i11, cArr4, i7 + 2, (i6 - i7) - 1);
            char[] cArr5 = this.buf;
            cArr5[i7] = '\\';
            cArr5[i11] = IOUtils.replaceChars[c];
            int i12 = i6 + 1;
            for (int i13 = i7 - 1; i13 >= i5; i13--) {
                char c3 = this.buf[i13];
                if (c3 <= '\r' || c3 == '\\' || c3 == '\'' || (c3 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                    char[] cArr6 = this.buf;
                    int i14 = i13 + 1;
                    System.arraycopy(cArr6, i14, cArr6, i13 + 2, (i12 - i13) - 1);
                    char[] cArr7 = this.buf;
                    cArr7[i13] = '\\';
                    cArr7[i14] = IOUtils.replaceChars[c3];
                    i12++;
                }
            }
        }
        this.buf[this.count - 1] = Operators.SINGLE_QUOTE;
    }

    protected void writeStringWithSingleQuote(char[] cArr) {
        int i = 0;
        if (cArr == null) {
            int i2 = this.count + 4;
            if (i2 > this.buf.length) {
                expandCapacity(i2);
            }
            "null".getChars(0, 4, this.buf, this.count);
            this.count = i2;
            return;
        }
        int length = cArr.length;
        int i3 = this.count + length + 2;
        if (i3 > this.buf.length) {
            if (this.writer != null) {
                write(39);
                while (i < cArr.length) {
                    char c = cArr[i];
                    if (c <= '\r' || c == '\\' || c == '\'' || (c == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        write(92);
                        write(IOUtils.replaceChars[c]);
                    } else {
                        write(c);
                    }
                    i++;
                }
                write(39);
                return;
            }
            expandCapacity(i3);
        }
        int i4 = this.count;
        int i5 = i4 + 1;
        int i6 = length + i5;
        char[] cArr2 = this.buf;
        cArr2[i4] = Operators.SINGLE_QUOTE;
        System.arraycopy(cArr, 0, cArr2, i5, cArr.length);
        this.count = i3;
        char c2 = 0;
        int i7 = -1;
        for (int i8 = i5; i8 < i6; i8++) {
            char c3 = this.buf[i8];
            if (c3 <= '\r' || c3 == '\\' || c3 == '\'' || (c3 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                i++;
                i7 = i8;
                c2 = c3;
            }
        }
        int i9 = i3 + i;
        if (i9 > this.buf.length) {
            expandCapacity(i9);
        }
        this.count = i9;
        if (i == 1) {
            char[] cArr3 = this.buf;
            int i10 = i7 + 1;
            System.arraycopy(cArr3, i10, cArr3, i7 + 2, (i6 - i7) - 1);
            char[] cArr4 = this.buf;
            cArr4[i7] = '\\';
            cArr4[i10] = IOUtils.replaceChars[c2];
        } else if (i > 1) {
            char[] cArr5 = this.buf;
            int i11 = i7 + 1;
            System.arraycopy(cArr5, i11, cArr5, i7 + 2, (i6 - i7) - 1);
            char[] cArr6 = this.buf;
            cArr6[i7] = '\\';
            cArr6[i11] = IOUtils.replaceChars[c2];
            int i12 = i6 + 1;
            for (int i13 = i7 - 1; i13 >= i5; i13--) {
                char c4 = this.buf[i13];
                if (c4 <= '\r' || c4 == '\\' || c4 == '\'' || (c4 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                    char[] cArr7 = this.buf;
                    int i14 = i13 + 1;
                    System.arraycopy(cArr7, i14, cArr7, i13 + 2, (i12 - i13) - 1);
                    char[] cArr8 = this.buf;
                    cArr8[i13] = '\\';
                    cArr8[i14] = IOUtils.replaceChars[c4];
                    i12++;
                }
            }
        }
        this.buf[this.count - 1] = Operators.SINGLE_QUOTE;
    }

    public void writeFieldName(String str) {
        writeFieldName(str, false);
    }

    public void writeFieldName(String str, boolean z) {
        if (str == null) {
            write("null:");
            return;
        }
        if (this.useSingleQuotes) {
            if (this.quoteFieldNames) {
                writeStringWithSingleQuote(str);
                write(58);
                return;
            } else {
                writeKeyWithSingleQuoteIfHasSpecial(str);
                return;
            }
        }
        if (this.quoteFieldNames) {
            writeStringWithDoubleQuote(str, Operators.CONDITION_IF_MIDDLE);
            return;
        }
        boolean z2 = true;
        boolean z3 = str.length() == 0;
        int i = 0;
        while (true) {
            if (i >= str.length()) {
                z2 = z3;
                break;
            }
            char cCharAt = str.charAt(i);
            if ((cCharAt < '@' && (this.sepcialBits & (1 << cCharAt)) != 0) || cCharAt == '\\') {
                break;
            } else {
                i++;
            }
        }
        if (z2) {
            writeStringWithDoubleQuote(str, Operators.CONDITION_IF_MIDDLE);
        } else {
            write(str);
            write(58);
        }
    }

    private void writeKeyWithSingleQuoteIfHasSpecial(String str) {
        byte[] bArr = IOUtils.specicalFlags_singleQuotes;
        int length = str.length();
        int i = 1;
        int i2 = this.count + length + 1;
        if (i2 > this.buf.length) {
            if (this.writer != null) {
                if (length == 0) {
                    write(39);
                    write(39);
                    write(58);
                    return;
                }
                int i3 = 0;
                while (true) {
                    if (i3 >= length) {
                        i = 0;
                        break;
                    }
                    char cCharAt = str.charAt(i3);
                    if (cCharAt < bArr.length && bArr[cCharAt] != 0) {
                        break;
                    } else {
                        i3++;
                    }
                }
                if (i != 0) {
                    write(39);
                }
                for (int i4 = 0; i4 < length; i4++) {
                    char cCharAt2 = str.charAt(i4);
                    if (cCharAt2 < bArr.length && bArr[cCharAt2] != 0) {
                        write(92);
                        write(IOUtils.replaceChars[cCharAt2]);
                    } else {
                        write(cCharAt2);
                    }
                }
                if (i != 0) {
                    write(39);
                }
                write(58);
                return;
            }
            expandCapacity(i2);
        }
        if (length == 0) {
            int i5 = this.count;
            if (i5 + 3 > this.buf.length) {
                expandCapacity(i5 + 3);
            }
            char[] cArr = this.buf;
            int i6 = this.count;
            int i7 = i6 + 1;
            this.count = i7;
            cArr[i6] = Operators.SINGLE_QUOTE;
            int i8 = i6 + 2;
            this.count = i8;
            cArr[i7] = Operators.SINGLE_QUOTE;
            this.count = i6 + 3;
            cArr[i8] = Operators.CONDITION_IF_MIDDLE;
            return;
        }
        int i9 = this.count;
        int i10 = i9 + length;
        str.getChars(0, length, this.buf, i9);
        this.count = i2;
        int i11 = i9;
        boolean z = false;
        while (i11 < i10) {
            char[] cArr2 = this.buf;
            char c = cArr2[i11];
            if (c < bArr.length && bArr[c] != 0) {
                if (!z) {
                    i2 += 3;
                    if (i2 > cArr2.length) {
                        expandCapacity(i2);
                    }
                    this.count = i2;
                    char[] cArr3 = this.buf;
                    int i12 = i11 + 1;
                    System.arraycopy(cArr3, i12, cArr3, i11 + 3, (i10 - i11) - i);
                    char[] cArr4 = this.buf;
                    System.arraycopy(cArr4, 0, cArr4, i, i11);
                    char[] cArr5 = this.buf;
                    cArr5[i9] = Operators.SINGLE_QUOTE;
                    cArr5[i12] = '\\';
                    i11 += 2;
                    cArr5[i11] = IOUtils.replaceChars[c];
                    i10 += 2;
                    this.buf[this.count - 2] = Operators.SINGLE_QUOTE;
                    z = true;
                } else {
                    i2++;
                    if (i2 > cArr2.length) {
                        expandCapacity(i2);
                    }
                    this.count = i2;
                    char[] cArr6 = this.buf;
                    int i13 = i11 + 1;
                    System.arraycopy(cArr6, i13, cArr6, i11 + 2, i10 - i11);
                    char[] cArr7 = this.buf;
                    cArr7[i11] = '\\';
                    cArr7[i13] = IOUtils.replaceChars[c];
                    i10++;
                    i11 = i13;
                }
            }
            i11++;
            i = 1;
        }
        this.buf[i2 - 1] = Operators.CONDITION_IF_MIDDLE;
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() throws IOException {
        Writer writer = this.writer;
        if (writer == null) {
            return;
        }
        try {
            writer.write(this.buf, 0, this.count);
            this.writer.flush();
            this.count = 0;
        } catch (IOException e) {
            throw new JSONException(e.getMessage(), e);
        }
    }

    public void reset() {
        this.count = 0;
    }
}
