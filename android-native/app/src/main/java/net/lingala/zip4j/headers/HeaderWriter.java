package net.lingala.zip4j.headers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.outputstream.CountingOutputStream;
import net.lingala.zip4j.io.outputstream.OutputStreamWithSplitZipSupport;
import net.lingala.zip4j.io.outputstream.SplitOutputStream;
import net.lingala.zip4j.model.AESExtraDataRecord;
import net.lingala.zip4j.model.ExtraDataRecord;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.Zip64EndOfCentralDirectoryLocator;
import net.lingala.zip4j.model.Zip64EndOfCentralDirectoryRecord;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.util.FileUtils;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.RawIO;
import net.lingala.zip4j.util.Zip4jUtil;

/* loaded from: classes2.dex */
public class HeaderWriter {
    private static final short AES_EXTRA_DATA_RECORD_SIZE = 11;
    private static final short ZIP64_EXTRA_DATA_RECORD_SIZE_FH = 28;
    private static final short ZIP64_EXTRA_DATA_RECORD_SIZE_LFH = 16;
    private final RawIO rawIO = new RawIO();
    private final byte[] longBuff = new byte[8];
    private final byte[] intBuff = new byte[4];

    public void writeLocalFileHeader(ZipModel zipModel, LocalFileHeader localFileHeader, OutputStream outputStream, Charset charset) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            this.rawIO.writeIntLittleEndian(byteArrayOutputStream, (int) localFileHeader.getSignature().getValue());
            this.rawIO.writeShortLittleEndian(byteArrayOutputStream, localFileHeader.getVersionNeededToExtract());
            byteArrayOutputStream.write(localFileHeader.getGeneralPurposeFlag());
            this.rawIO.writeShortLittleEndian(byteArrayOutputStream, localFileHeader.getCompressionMethod().getCode());
            this.rawIO.writeLongLittleEndian(this.longBuff, 0, localFileHeader.getLastModifiedTime());
            byteArrayOutputStream.write(this.longBuff, 0, 4);
            this.rawIO.writeLongLittleEndian(this.longBuff, 0, localFileHeader.getCrc());
            byteArrayOutputStream.write(this.longBuff, 0, 4);
            boolean z = localFileHeader.getCompressedSize() >= InternalZipConstants.ZIP_64_SIZE_LIMIT || localFileHeader.getUncompressedSize() >= InternalZipConstants.ZIP_64_SIZE_LIMIT;
            if (z) {
                this.rawIO.writeLongLittleEndian(this.longBuff, 0, InternalZipConstants.ZIP_64_SIZE_LIMIT);
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                zipModel.setZip64Format(true);
                localFileHeader.setWriteCompressedSizeInZip64ExtraRecord(true);
            } else {
                this.rawIO.writeLongLittleEndian(this.longBuff, 0, localFileHeader.getCompressedSize());
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                this.rawIO.writeLongLittleEndian(this.longBuff, 0, localFileHeader.getUncompressedSize());
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                localFileHeader.setWriteCompressedSizeInZip64ExtraRecord(false);
            }
            byte[] bytesFromString = new byte[0];
            if (Zip4jUtil.isStringNotNullAndNotEmpty(localFileHeader.getFileName())) {
                bytesFromString = HeaderUtil.getBytesFromString(localFileHeader.getFileName(), charset);
            }
            this.rawIO.writeShortLittleEndian(byteArrayOutputStream, bytesFromString.length);
            int i = z ? 20 : 0;
            if (localFileHeader.getAesExtraDataRecord() != null) {
                i += 11;
            }
            this.rawIO.writeShortLittleEndian(byteArrayOutputStream, i);
            if (bytesFromString.length > 0) {
                byteArrayOutputStream.write(bytesFromString);
            }
            if (z) {
                this.rawIO.writeShortLittleEndian(byteArrayOutputStream, (int) HeaderSignature.ZIP64_EXTRA_FIELD_SIGNATURE.getValue());
                this.rawIO.writeShortLittleEndian(byteArrayOutputStream, 16);
                this.rawIO.writeLongLittleEndian(byteArrayOutputStream, localFileHeader.getUncompressedSize());
                this.rawIO.writeLongLittleEndian(byteArrayOutputStream, localFileHeader.getCompressedSize());
            }
            if (localFileHeader.getAesExtraDataRecord() != null) {
                AESExtraDataRecord aesExtraDataRecord = localFileHeader.getAesExtraDataRecord();
                this.rawIO.writeShortLittleEndian(byteArrayOutputStream, (int) aesExtraDataRecord.getSignature().getValue());
                this.rawIO.writeShortLittleEndian(byteArrayOutputStream, aesExtraDataRecord.getDataSize());
                this.rawIO.writeShortLittleEndian(byteArrayOutputStream, aesExtraDataRecord.getAesVersion().getVersionNumber());
                byteArrayOutputStream.write(HeaderUtil.getBytesFromString(aesExtraDataRecord.getVendorID(), charset));
                byteArrayOutputStream.write(new byte[]{(byte) aesExtraDataRecord.getAesKeyStrength().getRawCode()});
                this.rawIO.writeShortLittleEndian(byteArrayOutputStream, aesExtraDataRecord.getCompressionMethod().getCode());
            }
            outputStream.write(byteArrayOutputStream.toByteArray());
            byteArrayOutputStream.close();
        } catch (Throwable th) {
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public void writeExtendedLocalHeader(LocalFileHeader localFileHeader, OutputStream outputStream) throws IOException {
        if (localFileHeader == null || outputStream == null) {
            throw new ZipException("input parameters is null, cannot write extended local header");
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            this.rawIO.writeIntLittleEndian(byteArrayOutputStream, (int) HeaderSignature.EXTRA_DATA_RECORD.getValue());
            this.rawIO.writeLongLittleEndian(this.longBuff, 0, localFileHeader.getCrc());
            byteArrayOutputStream.write(this.longBuff, 0, 4);
            if (localFileHeader.isWriteCompressedSizeInZip64ExtraRecord()) {
                this.rawIO.writeLongLittleEndian(byteArrayOutputStream, localFileHeader.getCompressedSize());
                this.rawIO.writeLongLittleEndian(byteArrayOutputStream, localFileHeader.getUncompressedSize());
            } else {
                this.rawIO.writeLongLittleEndian(this.longBuff, 0, localFileHeader.getCompressedSize());
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                this.rawIO.writeLongLittleEndian(this.longBuff, 0, localFileHeader.getUncompressedSize());
                byteArrayOutputStream.write(this.longBuff, 0, 4);
            }
            outputStream.write(byteArrayOutputStream.toByteArray());
            byteArrayOutputStream.close();
        } catch (Throwable th) {
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x003f A[Catch: all -> 0x00b8, TRY_ENTER, TRY_LEAVE, TryCatch #1 {all -> 0x00b8, blocks: (B:5:0x0009, B:28:0x00a3, B:15:0x003f, B:18:0x004d, B:21:0x005b, B:27:0x008e, B:26:0x007f), top: B:47:0x0009 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void finalizeZipFile(net.lingala.zip4j.model.ZipModel r10, java.io.OutputStream r11, java.nio.charset.Charset r12) throws java.lang.Throwable {
        /*
            r9 = this;
            if (r10 == 0) goto Lc5
            if (r11 == 0) goto Lc5
            java.io.ByteArrayOutputStream r6 = new java.io.ByteArrayOutputStream
            r6.<init>()
            r9.processHeaderData(r10, r11)     // Catch: java.lang.Throwable -> Lb8
            long r4 = r9.getOffsetOfCentralDirectory(r10)     // Catch: java.lang.Throwable -> Lb8
            net.lingala.zip4j.util.RawIO r0 = r9.rawIO     // Catch: java.lang.Throwable -> Lb8
            r9.writeCentralDirectory(r10, r6, r0, r12)     // Catch: java.lang.Throwable -> Lb8
            int r3 = r6.size()     // Catch: java.lang.Throwable -> Lb8
            boolean r0 = r10.isZip64Format()     // Catch: java.lang.Throwable -> Lb8
            if (r0 != 0) goto L3f
            r0 = 4294967295(0xffffffff, double:2.1219957905E-314)
            int r2 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r2 >= 0) goto L3f
            net.lingala.zip4j.model.CentralDirectory r0 = r10.getCentralDirectory()     // Catch: java.lang.Throwable -> L3a
            java.util.List r0 = r0.getFileHeaders()     // Catch: java.lang.Throwable -> L3a
            int r0 = r0.size()     // Catch: java.lang.Throwable -> L3a
            r1 = 65535(0xffff, float:9.1834E-41)
            if (r0 < r1) goto La3
            goto L3f
        L3a:
            r0 = move-exception
            r10 = r0
            r1 = r9
            goto Lbb
        L3f:
            net.lingala.zip4j.model.Zip64EndOfCentralDirectoryRecord r0 = r10.getZip64EndOfCentralDirectoryRecord()     // Catch: java.lang.Throwable -> Lb8
            if (r0 != 0) goto L4d
            net.lingala.zip4j.model.Zip64EndOfCentralDirectoryRecord r0 = new net.lingala.zip4j.model.Zip64EndOfCentralDirectoryRecord     // Catch: java.lang.Throwable -> L3a
            r0.<init>()     // Catch: java.lang.Throwable -> L3a
            r10.setZip64EndOfCentralDirectoryRecord(r0)     // Catch: java.lang.Throwable -> L3a
        L4d:
            net.lingala.zip4j.model.Zip64EndOfCentralDirectoryLocator r0 = r10.getZip64EndOfCentralDirectoryLocator()     // Catch: java.lang.Throwable -> Lb8
            if (r0 != 0) goto L5b
            net.lingala.zip4j.model.Zip64EndOfCentralDirectoryLocator r0 = new net.lingala.zip4j.model.Zip64EndOfCentralDirectoryLocator     // Catch: java.lang.Throwable -> L3a
            r0.<init>()     // Catch: java.lang.Throwable -> L3a
            r10.setZip64EndOfCentralDirectoryLocator(r0)     // Catch: java.lang.Throwable -> L3a
        L5b:
            net.lingala.zip4j.model.Zip64EndOfCentralDirectoryLocator r0 = r10.getZip64EndOfCentralDirectoryLocator()     // Catch: java.lang.Throwable -> Lb8
            long r1 = (long) r3     // Catch: java.lang.Throwable -> Lb8
            long r1 = r1 + r4
            r0.setOffsetZip64EndOfCentralDirectoryRecord(r1)     // Catch: java.lang.Throwable -> Lb8
            boolean r0 = r9.isSplitZipFile(r11)     // Catch: java.lang.Throwable -> Lb8
            r1 = 1
            if (r0 == 0) goto L7f
            int r0 = r9.getCurrentSplitFileCounter(r11)     // Catch: java.lang.Throwable -> L3a
            net.lingala.zip4j.model.Zip64EndOfCentralDirectoryLocator r2 = r10.getZip64EndOfCentralDirectoryLocator()     // Catch: java.lang.Throwable -> L3a
            r2.setNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord(r0)     // Catch: java.lang.Throwable -> L3a
            net.lingala.zip4j.model.Zip64EndOfCentralDirectoryLocator r2 = r10.getZip64EndOfCentralDirectoryLocator()     // Catch: java.lang.Throwable -> L3a
            int r0 = r0 + r1
            r2.setTotalNumberOfDiscs(r0)     // Catch: java.lang.Throwable -> L3a
            goto L8e
        L7f:
            net.lingala.zip4j.model.Zip64EndOfCentralDirectoryLocator r0 = r10.getZip64EndOfCentralDirectoryLocator()     // Catch: java.lang.Throwable -> Lb8
            r2 = 0
            r0.setNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord(r2)     // Catch: java.lang.Throwable -> Lb8
            net.lingala.zip4j.model.Zip64EndOfCentralDirectoryLocator r0 = r10.getZip64EndOfCentralDirectoryLocator()     // Catch: java.lang.Throwable -> Lb8
            r0.setTotalNumberOfDiscs(r1)     // Catch: java.lang.Throwable -> Lb8
        L8e:
            net.lingala.zip4j.model.Zip64EndOfCentralDirectoryRecord r0 = r9.buildZip64EndOfCentralDirectoryRecord(r10, r3, r4)     // Catch: java.lang.Throwable -> Lb8
            r10.setZip64EndOfCentralDirectoryRecord(r0)     // Catch: java.lang.Throwable -> Lb8
            net.lingala.zip4j.util.RawIO r1 = r9.rawIO     // Catch: java.lang.Throwable -> Lb8
            r9.writeZip64EndOfCentralDirectoryRecord(r0, r6, r1)     // Catch: java.lang.Throwable -> Lb8
            net.lingala.zip4j.model.Zip64EndOfCentralDirectoryLocator r0 = r10.getZip64EndOfCentralDirectoryLocator()     // Catch: java.lang.Throwable -> Lb8
            net.lingala.zip4j.util.RawIO r1 = r9.rawIO     // Catch: java.lang.Throwable -> Lb8
            r9.writeZip64EndOfCentralDirectoryLocator(r0, r6, r1)     // Catch: java.lang.Throwable -> Lb8
        La3:
            net.lingala.zip4j.util.RawIO r7 = r9.rawIO     // Catch: java.lang.Throwable -> Lb8
            r1 = r9
            r2 = r10
            r8 = r12
            r1.writeEndOfCentralDirectoryRecord(r2, r3, r4, r6, r7, r8)     // Catch: java.lang.Throwable -> Lb6
            byte[] r10 = r6.toByteArray()     // Catch: java.lang.Throwable -> Lb6
            r9.writeZipHeaderBytes(r2, r11, r10, r8)     // Catch: java.lang.Throwable -> Lb6
            r6.close()
            return
        Lb6:
            r0 = move-exception
            goto Lba
        Lb8:
            r0 = move-exception
            r1 = r9
        Lba:
            r10 = r0
        Lbb:
            r6.close()     // Catch: java.lang.Throwable -> Lbf
            goto Lc4
        Lbf:
            r0 = move-exception
            r11 = r0
            r10.addSuppressed(r11)
        Lc4:
            throw r10
        Lc5:
            r1 = r9
            net.lingala.zip4j.exception.ZipException r10 = new net.lingala.zip4j.exception.ZipException
            java.lang.String r11 = "input parameters is null, cannot finalize zip file"
            r10.<init>(r11)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: net.lingala.zip4j.headers.HeaderWriter.finalizeZipFile(net.lingala.zip4j.model.ZipModel, java.io.OutputStream, java.nio.charset.Charset):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x003b A[Catch: all -> 0x008a, TRY_ENTER, TRY_LEAVE, TryCatch #3 {all -> 0x008a, blocks: (B:5:0x0009, B:22:0x0075, B:15:0x003b, B:18:0x0049, B:21:0x0057), top: B:45:0x0009 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void finalizeZipFileWithoutValidations(net.lingala.zip4j.model.ZipModel r10, java.io.OutputStream r11, java.nio.charset.Charset r12) throws java.lang.Throwable {
        /*
            r9 = this;
            if (r10 == 0) goto L97
            if (r11 == 0) goto L97
            java.io.ByteArrayOutputStream r6 = new java.io.ByteArrayOutputStream
            r6.<init>()
            long r4 = r9.getOffsetOfCentralDirectory(r10)     // Catch: java.lang.Throwable -> L8a
            net.lingala.zip4j.util.RawIO r0 = r9.rawIO     // Catch: java.lang.Throwable -> L8a
            r9.writeCentralDirectory(r10, r6, r0, r12)     // Catch: java.lang.Throwable -> L8a
            int r3 = r6.size()     // Catch: java.lang.Throwable -> L8a
            boolean r0 = r10.isZip64Format()     // Catch: java.lang.Throwable -> L8a
            if (r0 != 0) goto L3b
            r0 = 4294967295(0xffffffff, double:2.1219957905E-314)
            int r2 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r2 >= 0) goto L3b
            net.lingala.zip4j.model.CentralDirectory r0 = r10.getCentralDirectory()     // Catch: java.lang.Throwable -> L37
            java.util.List r0 = r0.getFileHeaders()     // Catch: java.lang.Throwable -> L37
            int r0 = r0.size()     // Catch: java.lang.Throwable -> L37
            r1 = 65535(0xffff, float:9.1834E-41)
            if (r0 < r1) goto L75
            goto L3b
        L37:
            r0 = move-exception
            r10 = r0
            r1 = r9
            goto L8d
        L3b:
            net.lingala.zip4j.model.Zip64EndOfCentralDirectoryRecord r0 = r10.getZip64EndOfCentralDirectoryRecord()     // Catch: java.lang.Throwable -> L8a
            if (r0 != 0) goto L49
            net.lingala.zip4j.model.Zip64EndOfCentralDirectoryRecord r0 = new net.lingala.zip4j.model.Zip64EndOfCentralDirectoryRecord     // Catch: java.lang.Throwable -> L37
            r0.<init>()     // Catch: java.lang.Throwable -> L37
            r10.setZip64EndOfCentralDirectoryRecord(r0)     // Catch: java.lang.Throwable -> L37
        L49:
            net.lingala.zip4j.model.Zip64EndOfCentralDirectoryLocator r0 = r10.getZip64EndOfCentralDirectoryLocator()     // Catch: java.lang.Throwable -> L8a
            if (r0 != 0) goto L57
            net.lingala.zip4j.model.Zip64EndOfCentralDirectoryLocator r0 = new net.lingala.zip4j.model.Zip64EndOfCentralDirectoryLocator     // Catch: java.lang.Throwable -> L37
            r0.<init>()     // Catch: java.lang.Throwable -> L37
            r10.setZip64EndOfCentralDirectoryLocator(r0)     // Catch: java.lang.Throwable -> L37
        L57:
            net.lingala.zip4j.model.Zip64EndOfCentralDirectoryLocator r0 = r10.getZip64EndOfCentralDirectoryLocator()     // Catch: java.lang.Throwable -> L8a
            long r1 = (long) r3     // Catch: java.lang.Throwable -> L8a
            long r1 = r1 + r4
            r0.setOffsetZip64EndOfCentralDirectoryRecord(r1)     // Catch: java.lang.Throwable -> L8a
            net.lingala.zip4j.model.Zip64EndOfCentralDirectoryRecord r0 = r9.buildZip64EndOfCentralDirectoryRecord(r10, r3, r4)     // Catch: java.lang.Throwable -> L8a
            r10.setZip64EndOfCentralDirectoryRecord(r0)     // Catch: java.lang.Throwable -> L8a
            net.lingala.zip4j.util.RawIO r1 = r9.rawIO     // Catch: java.lang.Throwable -> L8a
            r9.writeZip64EndOfCentralDirectoryRecord(r0, r6, r1)     // Catch: java.lang.Throwable -> L8a
            net.lingala.zip4j.model.Zip64EndOfCentralDirectoryLocator r0 = r10.getZip64EndOfCentralDirectoryLocator()     // Catch: java.lang.Throwable -> L8a
            net.lingala.zip4j.util.RawIO r1 = r9.rawIO     // Catch: java.lang.Throwable -> L8a
            r9.writeZip64EndOfCentralDirectoryLocator(r0, r6, r1)     // Catch: java.lang.Throwable -> L8a
        L75:
            net.lingala.zip4j.util.RawIO r7 = r9.rawIO     // Catch: java.lang.Throwable -> L8a
            r1 = r9
            r2 = r10
            r8 = r12
            r1.writeEndOfCentralDirectoryRecord(r2, r3, r4, r6, r7, r8)     // Catch: java.lang.Throwable -> L88
            byte[] r10 = r6.toByteArray()     // Catch: java.lang.Throwable -> L88
            r9.writeZipHeaderBytes(r2, r11, r10, r8)     // Catch: java.lang.Throwable -> L88
            r6.close()
            return
        L88:
            r0 = move-exception
            goto L8c
        L8a:
            r0 = move-exception
            r1 = r9
        L8c:
            r10 = r0
        L8d:
            r6.close()     // Catch: java.lang.Throwable -> L91
            goto L96
        L91:
            r0 = move-exception
            r11 = r0
            r10.addSuppressed(r11)
        L96:
            throw r10
        L97:
            r1 = r9
            net.lingala.zip4j.exception.ZipException r10 = new net.lingala.zip4j.exception.ZipException
            java.lang.String r11 = "input parameters is null, cannot finalize zip file without validations"
            r10.<init>(r11)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: net.lingala.zip4j.headers.HeaderWriter.finalizeZipFileWithoutValidations(net.lingala.zip4j.model.ZipModel, java.io.OutputStream, java.nio.charset.Charset):void");
    }

    public void updateLocalFileHeader(FileHeader fileHeader, ZipModel zipModel, SplitOutputStream splitOutputStream) throws IOException {
        SplitOutputStream splitOutputStream2;
        boolean z;
        String str;
        String str2;
        if (fileHeader == null || zipModel == null) {
            throw new ZipException("invalid input parameters, cannot update local file header");
        }
        if (fileHeader.getDiskNumberStart() != splitOutputStream.getCurrentSplitFileCounter()) {
            String parent = zipModel.getZipFile().getParent();
            String zipFileNameWithoutExtension = FileUtils.getZipFileNameWithoutExtension(zipModel.getZipFile().getName());
            if (parent == null) {
                str = "";
            } else {
                str = parent + System.getProperty("file.separator");
            }
            z = true;
            if (fileHeader.getDiskNumberStart() < 9) {
                str2 = str + zipFileNameWithoutExtension + ".z0" + (fileHeader.getDiskNumberStart() + 1);
            } else {
                str2 = str + zipFileNameWithoutExtension + ".z" + (fileHeader.getDiskNumberStart() + 1);
            }
            splitOutputStream2 = new SplitOutputStream(new File(str2));
        } else {
            splitOutputStream2 = splitOutputStream;
            z = false;
        }
        long filePointer = splitOutputStream2.getFilePointer();
        splitOutputStream2.seek(fileHeader.getOffsetLocalHeader() + 14);
        this.rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getCrc());
        splitOutputStream2.write(this.longBuff, 0, 4);
        updateFileSizesInLocalFileHeader(splitOutputStream2, fileHeader);
        if (z) {
            splitOutputStream2.close();
        } else {
            splitOutputStream.seek(filePointer);
        }
    }

    private void updateFileSizesInLocalFileHeader(SplitOutputStream splitOutputStream, FileHeader fileHeader) throws IOException {
        if (fileHeader.getUncompressedSize() >= InternalZipConstants.ZIP_64_SIZE_LIMIT) {
            this.rawIO.writeLongLittleEndian(this.longBuff, 0, InternalZipConstants.ZIP_64_SIZE_LIMIT);
            splitOutputStream.write(this.longBuff, 0, 4);
            splitOutputStream.write(this.longBuff, 0, 4);
            int fileNameLength = fileHeader.getFileNameLength() + 8;
            if (splitOutputStream.skipBytes(fileNameLength) != fileNameLength) {
                throw new ZipException("Unable to skip " + fileNameLength + " bytes to update LFH");
            }
            this.rawIO.writeLongLittleEndian(splitOutputStream, fileHeader.getUncompressedSize());
            this.rawIO.writeLongLittleEndian(splitOutputStream, fileHeader.getCompressedSize());
            return;
        }
        this.rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getCompressedSize());
        splitOutputStream.write(this.longBuff, 0, 4);
        this.rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getUncompressedSize());
        splitOutputStream.write(this.longBuff, 0, 4);
    }

    private boolean isSplitZipFile(OutputStream outputStream) {
        if (outputStream instanceof SplitOutputStream) {
            return ((SplitOutputStream) outputStream).isSplitZipFile();
        }
        if (outputStream instanceof CountingOutputStream) {
            return ((CountingOutputStream) outputStream).isSplitZipFile();
        }
        return false;
    }

    private int getCurrentSplitFileCounter(OutputStream outputStream) {
        if (outputStream instanceof SplitOutputStream) {
            return ((SplitOutputStream) outputStream).getCurrentSplitFileCounter();
        }
        return ((CountingOutputStream) outputStream).getCurrentSplitFileCounter();
    }

    private void writeZipHeaderBytes(ZipModel zipModel, OutputStream outputStream, byte[] bArr, Charset charset) throws Throwable {
        if (bArr == null) {
            throw new ZipException("invalid buff to write as zip headers");
        }
        if ((outputStream instanceof CountingOutputStream) && ((CountingOutputStream) outputStream).checkBuffSizeAndStartNextSplitFile(bArr.length)) {
            finalizeZipFile(zipModel, outputStream, charset);
        } else {
            outputStream.write(bArr);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void processHeaderData(ZipModel zipModel, OutputStream outputStream) throws IOException {
        int currentSplitFileCounter;
        if (outputStream instanceof OutputStreamWithSplitZipSupport) {
            OutputStreamWithSplitZipSupport outputStreamWithSplitZipSupport = (OutputStreamWithSplitZipSupport) outputStream;
            zipModel.getEndOfCentralDirectoryRecord().setOffsetOfStartOfCentralDirectory(outputStreamWithSplitZipSupport.getFilePointer());
            currentSplitFileCounter = outputStreamWithSplitZipSupport.getCurrentSplitFileCounter();
        } else {
            currentSplitFileCounter = 0;
        }
        if (zipModel.isZip64Format()) {
            if (zipModel.getZip64EndOfCentralDirectoryRecord() == null) {
                zipModel.setZip64EndOfCentralDirectoryRecord(new Zip64EndOfCentralDirectoryRecord());
            }
            if (zipModel.getZip64EndOfCentralDirectoryLocator() == null) {
                zipModel.setZip64EndOfCentralDirectoryLocator(new Zip64EndOfCentralDirectoryLocator());
            }
            zipModel.getZip64EndOfCentralDirectoryRecord().setOffsetStartCentralDirectoryWRTStartDiskNumber(zipModel.getEndOfCentralDirectoryRecord().getOffsetOfStartOfCentralDirectory());
            zipModel.getZip64EndOfCentralDirectoryLocator().setNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord(currentSplitFileCounter);
            zipModel.getZip64EndOfCentralDirectoryLocator().setTotalNumberOfDiscs(currentSplitFileCounter + 1);
        }
        zipModel.getEndOfCentralDirectoryRecord().setNumberOfThisDisk(currentSplitFileCounter);
        zipModel.getEndOfCentralDirectoryRecord().setNumberOfThisDiskStartOfCentralDir(currentSplitFileCounter);
    }

    private void writeCentralDirectory(ZipModel zipModel, ByteArrayOutputStream byteArrayOutputStream, RawIO rawIO, Charset charset) throws ZipException {
        if (zipModel.getCentralDirectory() == null || zipModel.getCentralDirectory().getFileHeaders() == null || zipModel.getCentralDirectory().getFileHeaders().size() <= 0) {
            return;
        }
        Iterator<FileHeader> it = zipModel.getCentralDirectory().getFileHeaders().iterator();
        while (it.hasNext()) {
            writeFileHeader(zipModel, it.next(), byteArrayOutputStream, rawIO, charset);
        }
    }

    private void writeFileHeader(ZipModel zipModel, FileHeader fileHeader, ByteArrayOutputStream byteArrayOutputStream, RawIO rawIO, Charset charset) throws ZipException {
        byte[] bArr;
        if (fileHeader == null) {
            throw new ZipException("input parameters is null, cannot write local file header");
        }
        try {
            byte[] bArr2 = {0, 0};
            boolean zIsZip64Entry = isZip64Entry(fileHeader);
            rawIO.writeIntLittleEndian(byteArrayOutputStream, (int) fileHeader.getSignature().getValue());
            rawIO.writeShortLittleEndian(byteArrayOutputStream, fileHeader.getVersionMadeBy());
            rawIO.writeShortLittleEndian(byteArrayOutputStream, fileHeader.getVersionNeededToExtract());
            byteArrayOutputStream.write(fileHeader.getGeneralPurposeFlag());
            rawIO.writeShortLittleEndian(byteArrayOutputStream, fileHeader.getCompressionMethod().getCode());
            rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getLastModifiedTime());
            byteArrayOutputStream.write(this.longBuff, 0, 4);
            rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getCrc());
            byteArrayOutputStream.write(this.longBuff, 0, 4);
            if (zIsZip64Entry) {
                rawIO.writeLongLittleEndian(this.longBuff, 0, InternalZipConstants.ZIP_64_SIZE_LIMIT);
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                zipModel.setZip64Format(true);
                bArr = bArr2;
            } else {
                bArr = bArr2;
                rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getCompressedSize());
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getUncompressedSize());
                byteArrayOutputStream.write(this.longBuff, 0, 4);
            }
            byte[] bytesFromString = new byte[0];
            if (Zip4jUtil.isStringNotNullAndNotEmpty(fileHeader.getFileName())) {
                bytesFromString = HeaderUtil.getBytesFromString(fileHeader.getFileName(), charset);
            }
            rawIO.writeShortLittleEndian(byteArrayOutputStream, bytesFromString.length);
            byte[] bArr3 = new byte[4];
            if (zIsZip64Entry) {
                rawIO.writeLongLittleEndian(this.longBuff, 0, InternalZipConstants.ZIP_64_SIZE_LIMIT);
                System.arraycopy(this.longBuff, 0, bArr3, 0, 4);
            } else {
                rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getOffsetLocalHeader());
                System.arraycopy(this.longBuff, 0, bArr3, 0, 4);
            }
            rawIO.writeShortLittleEndian(byteArrayOutputStream, calculateExtraDataRecordsSize(fileHeader, zIsZip64Entry));
            String fileComment = fileHeader.getFileComment();
            byte[] bytesFromString2 = new byte[0];
            if (Zip4jUtil.isStringNotNullAndNotEmpty(fileComment)) {
                bytesFromString2 = HeaderUtil.getBytesFromString(fileComment, charset);
            }
            rawIO.writeShortLittleEndian(byteArrayOutputStream, bytesFromString2.length);
            if (zIsZip64Entry) {
                rawIO.writeIntLittleEndian(this.intBuff, 0, 65535);
                byteArrayOutputStream.write(this.intBuff, 0, 2);
            } else {
                rawIO.writeShortLittleEndian(byteArrayOutputStream, fileHeader.getDiskNumberStart());
            }
            byteArrayOutputStream.write(bArr);
            byteArrayOutputStream.write(fileHeader.getExternalFileAttributes());
            byteArrayOutputStream.write(bArr3);
            if (bytesFromString.length > 0) {
                byteArrayOutputStream.write(bytesFromString);
            }
            if (zIsZip64Entry) {
                zipModel.setZip64Format(true);
                rawIO.writeShortLittleEndian(byteArrayOutputStream, (int) HeaderSignature.ZIP64_EXTRA_FIELD_SIGNATURE.getValue());
                rawIO.writeShortLittleEndian(byteArrayOutputStream, 28);
                rawIO.writeLongLittleEndian(byteArrayOutputStream, fileHeader.getUncompressedSize());
                rawIO.writeLongLittleEndian(byteArrayOutputStream, fileHeader.getCompressedSize());
                rawIO.writeLongLittleEndian(byteArrayOutputStream, fileHeader.getOffsetLocalHeader());
                rawIO.writeIntLittleEndian(byteArrayOutputStream, fileHeader.getDiskNumberStart());
            }
            if (fileHeader.getAesExtraDataRecord() != null) {
                AESExtraDataRecord aesExtraDataRecord = fileHeader.getAesExtraDataRecord();
                rawIO.writeShortLittleEndian(byteArrayOutputStream, (int) aesExtraDataRecord.getSignature().getValue());
                rawIO.writeShortLittleEndian(byteArrayOutputStream, aesExtraDataRecord.getDataSize());
                rawIO.writeShortLittleEndian(byteArrayOutputStream, aesExtraDataRecord.getAesVersion().getVersionNumber());
                byteArrayOutputStream.write(HeaderUtil.getBytesFromString(aesExtraDataRecord.getVendorID(), charset));
                byteArrayOutputStream.write(new byte[]{(byte) aesExtraDataRecord.getAesKeyStrength().getRawCode()});
                rawIO.writeShortLittleEndian(byteArrayOutputStream, aesExtraDataRecord.getCompressionMethod().getCode());
            }
            writeRemainingExtraDataRecordsIfPresent(fileHeader, byteArrayOutputStream);
            if (bytesFromString2.length > 0) {
                byteArrayOutputStream.write(bytesFromString2);
            }
        } catch (Exception e) {
            throw new ZipException(e);
        }
    }

    private int calculateExtraDataRecordsSize(FileHeader fileHeader, boolean z) {
        int sizeOfData = z ? 32 : 0;
        if (fileHeader.getAesExtraDataRecord() != null) {
            sizeOfData += 11;
        }
        if (fileHeader.getExtraDataRecords() != null) {
            for (ExtraDataRecord extraDataRecord : fileHeader.getExtraDataRecords()) {
                if (extraDataRecord.getHeader() != HeaderSignature.AES_EXTRA_DATA_RECORD.getValue() && extraDataRecord.getHeader() != HeaderSignature.ZIP64_EXTRA_FIELD_SIGNATURE.getValue()) {
                    sizeOfData += extraDataRecord.getSizeOfData() + 4;
                }
            }
        }
        return sizeOfData;
    }

    private void writeRemainingExtraDataRecordsIfPresent(FileHeader fileHeader, OutputStream outputStream) throws IOException {
        if (fileHeader.getExtraDataRecords() == null || fileHeader.getExtraDataRecords().size() == 0) {
            return;
        }
        for (ExtraDataRecord extraDataRecord : fileHeader.getExtraDataRecords()) {
            if (extraDataRecord.getHeader() != HeaderSignature.AES_EXTRA_DATA_RECORD.getValue() && extraDataRecord.getHeader() != HeaderSignature.ZIP64_EXTRA_FIELD_SIGNATURE.getValue()) {
                this.rawIO.writeShortLittleEndian(outputStream, (int) extraDataRecord.getHeader());
                this.rawIO.writeShortLittleEndian(outputStream, extraDataRecord.getSizeOfData());
                if (extraDataRecord.getSizeOfData() > 0 && extraDataRecord.getData() != null) {
                    outputStream.write(extraDataRecord.getData());
                }
            }
        }
    }

    private void writeZip64EndOfCentralDirectoryRecord(Zip64EndOfCentralDirectoryRecord zip64EndOfCentralDirectoryRecord, ByteArrayOutputStream byteArrayOutputStream, RawIO rawIO) throws IOException {
        rawIO.writeIntLittleEndian(byteArrayOutputStream, (int) zip64EndOfCentralDirectoryRecord.getSignature().getValue());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getSizeOfZip64EndCentralDirectoryRecord());
        rawIO.writeShortLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getVersionMadeBy());
        rawIO.writeShortLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getVersionNeededToExtract());
        rawIO.writeIntLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getNumberOfThisDisk());
        rawIO.writeIntLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getNumberOfThisDiskStartOfCentralDirectory());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getTotalNumberOfEntriesInCentralDirectoryOnThisDisk());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getTotalNumberOfEntriesInCentralDirectory());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getSizeOfCentralDirectory());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getOffsetStartCentralDirectoryWRTStartDiskNumber());
    }

    private void writeZip64EndOfCentralDirectoryLocator(Zip64EndOfCentralDirectoryLocator zip64EndOfCentralDirectoryLocator, ByteArrayOutputStream byteArrayOutputStream, RawIO rawIO) throws IOException {
        rawIO.writeIntLittleEndian(byteArrayOutputStream, (int) HeaderSignature.ZIP64_END_CENTRAL_DIRECTORY_LOCATOR.getValue());
        rawIO.writeIntLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryLocator.getNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryLocator.getOffsetZip64EndOfCentralDirectoryRecord());
        rawIO.writeIntLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryLocator.getTotalNumberOfDiscs());
    }

    private void writeEndOfCentralDirectoryRecord(ZipModel zipModel, int i, long j, ByteArrayOutputStream byteArrayOutputStream, RawIO rawIO, Charset charset) throws IOException {
        byte[] bArr = new byte[8];
        rawIO.writeIntLittleEndian(byteArrayOutputStream, (int) HeaderSignature.END_OF_CENTRAL_DIRECTORY.getValue());
        rawIO.writeShortLittleEndian(byteArrayOutputStream, zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk());
        rawIO.writeShortLittleEndian(byteArrayOutputStream, zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDiskStartOfCentralDir());
        long size = zipModel.getCentralDirectory().getFileHeaders().size();
        long jCountNumberOfFileHeaderEntriesOnDisk = zipModel.isSplitArchive() ? countNumberOfFileHeaderEntriesOnDisk(zipModel.getCentralDirectory().getFileHeaders(), zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk()) : size;
        if (jCountNumberOfFileHeaderEntriesOnDisk > 65535) {
            jCountNumberOfFileHeaderEntriesOnDisk = 65535;
        }
        rawIO.writeShortLittleEndian(byteArrayOutputStream, (int) jCountNumberOfFileHeaderEntriesOnDisk);
        if (size > 65535) {
            size = 65535;
        }
        rawIO.writeShortLittleEndian(byteArrayOutputStream, (int) size);
        rawIO.writeIntLittleEndian(byteArrayOutputStream, i);
        if (j > InternalZipConstants.ZIP_64_SIZE_LIMIT) {
            rawIO.writeLongLittleEndian(bArr, 0, InternalZipConstants.ZIP_64_SIZE_LIMIT);
            byteArrayOutputStream.write(bArr, 0, 4);
        } else {
            rawIO.writeLongLittleEndian(bArr, 0, j);
            byteArrayOutputStream.write(bArr, 0, 4);
        }
        String comment = zipModel.getEndOfCentralDirectoryRecord().getComment();
        if (Zip4jUtil.isStringNotNullAndNotEmpty(comment)) {
            byte[] bytesFromString = HeaderUtil.getBytesFromString(comment, charset);
            rawIO.writeShortLittleEndian(byteArrayOutputStream, bytesFromString.length);
            byteArrayOutputStream.write(bytesFromString);
            return;
        }
        rawIO.writeShortLittleEndian(byteArrayOutputStream, 0);
    }

    private long countNumberOfFileHeaderEntriesOnDisk(List<FileHeader> list, int i) throws ZipException {
        if (list == null) {
            throw new ZipException("file headers are null, cannot calculate number of entries on this disk");
        }
        Iterator<FileHeader> it = list.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if (it.next().getDiskNumberStart() == i) {
                i2++;
            }
        }
        return i2;
    }

    private boolean isZip64Entry(FileHeader fileHeader) {
        return fileHeader.getCompressedSize() >= InternalZipConstants.ZIP_64_SIZE_LIMIT || fileHeader.getUncompressedSize() >= InternalZipConstants.ZIP_64_SIZE_LIMIT || fileHeader.getOffsetLocalHeader() >= InternalZipConstants.ZIP_64_SIZE_LIMIT || fileHeader.getDiskNumberStart() >= 65535;
    }

    private long getOffsetOfCentralDirectory(ZipModel zipModel) {
        if (zipModel.isZip64Format() && zipModel.getZip64EndOfCentralDirectoryRecord() != null && zipModel.getZip64EndOfCentralDirectoryRecord().getOffsetStartCentralDirectoryWRTStartDiskNumber() != -1) {
            return zipModel.getZip64EndOfCentralDirectoryRecord().getOffsetStartCentralDirectoryWRTStartDiskNumber();
        }
        return zipModel.getEndOfCentralDirectoryRecord().getOffsetOfStartOfCentralDirectory();
    }

    private Zip64EndOfCentralDirectoryRecord buildZip64EndOfCentralDirectoryRecord(ZipModel zipModel, int i, long j) throws ZipException {
        Zip64EndOfCentralDirectoryRecord zip64EndOfCentralDirectoryRecord = new Zip64EndOfCentralDirectoryRecord();
        zip64EndOfCentralDirectoryRecord.setSignature(HeaderSignature.ZIP64_END_CENTRAL_DIRECTORY_RECORD);
        zip64EndOfCentralDirectoryRecord.setSizeOfZip64EndCentralDirectoryRecord(44L);
        if (zipModel.getCentralDirectory() != null && zipModel.getCentralDirectory().getFileHeaders() != null && zipModel.getCentralDirectory().getFileHeaders().size() > 0) {
            FileHeader fileHeader = zipModel.getCentralDirectory().getFileHeaders().get(0);
            zip64EndOfCentralDirectoryRecord.setVersionMadeBy(fileHeader.getVersionMadeBy());
            zip64EndOfCentralDirectoryRecord.setVersionNeededToExtract(fileHeader.getVersionNeededToExtract());
        }
        zip64EndOfCentralDirectoryRecord.setNumberOfThisDisk(zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk());
        zip64EndOfCentralDirectoryRecord.setNumberOfThisDiskStartOfCentralDirectory(zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDiskStartOfCentralDir());
        long size = zipModel.getCentralDirectory().getFileHeaders().size();
        zip64EndOfCentralDirectoryRecord.setTotalNumberOfEntriesInCentralDirectoryOnThisDisk(zipModel.isSplitArchive() ? countNumberOfFileHeaderEntriesOnDisk(zipModel.getCentralDirectory().getFileHeaders(), zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk()) : size);
        zip64EndOfCentralDirectoryRecord.setTotalNumberOfEntriesInCentralDirectory(size);
        zip64EndOfCentralDirectoryRecord.setSizeOfCentralDirectory(i);
        zip64EndOfCentralDirectoryRecord.setOffsetStartCentralDirectoryWRTStartDiskNumber(j);
        return zip64EndOfCentralDirectoryRecord;
    }
}
