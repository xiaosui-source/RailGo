package net.lingala.zip4j.io.inputstream;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.enums.RandomAccessFileMode;

/* loaded from: classes2.dex */
public class NumberedSplitFileInputStream extends SplitFileInputStream {
    private RandomAccessFile randomAccessFile;

    public NumberedSplitFileInputStream(File file) throws IOException {
        this.randomAccessFile = new NumberedSplitRandomAccessFile(file, RandomAccessFileMode.READ.getValue());
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        return this.randomAccessFile.read();
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        return this.randomAccessFile.read(bArr, i, i2);
    }

    @Override // net.lingala.zip4j.io.inputstream.SplitFileInputStream
    public void prepareExtractionForFileHeader(FileHeader fileHeader) throws IOException {
        this.randomAccessFile.seek(fileHeader.getOffsetLocalHeader());
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        RandomAccessFile randomAccessFile = this.randomAccessFile;
        if (randomAccessFile != null) {
            randomAccessFile.close();
        }
    }
}
