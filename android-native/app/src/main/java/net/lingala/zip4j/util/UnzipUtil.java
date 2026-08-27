package net.lingala.zip4j.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.inputstream.NumberedSplitFileInputStream;
import net.lingala.zip4j.io.inputstream.SplitFileInputStream;
import net.lingala.zip4j.io.inputstream.ZipInputStream;
import net.lingala.zip4j.io.inputstream.ZipStandardSplitFileInputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipModel;

/* loaded from: classes2.dex */
public class UnzipUtil {
    public static ZipInputStream createZipInputStream(ZipModel zipModel, FileHeader fileHeader, char[] cArr) throws IOException {
        SplitFileInputStream splitFileInputStreamCreateSplitInputStream;
        try {
            splitFileInputStreamCreateSplitInputStream = createSplitInputStream(zipModel);
        } catch (IOException e) {
            e = e;
            splitFileInputStreamCreateSplitInputStream = null;
        }
        try {
            splitFileInputStreamCreateSplitInputStream.prepareExtractionForFileHeader(fileHeader);
            ZipInputStream zipInputStream = new ZipInputStream(splitFileInputStreamCreateSplitInputStream, cArr);
            if (zipInputStream.getNextEntry(fileHeader, false) != null) {
                return zipInputStream;
            }
            throw new ZipException("Could not locate local file header for corresponding file header");
        } catch (IOException e2) {
            e = e2;
            if (splitFileInputStreamCreateSplitInputStream != null) {
                splitFileInputStreamCreateSplitInputStream.close();
            }
            throw e;
        }
    }

    public static void applyFileAttributes(FileHeader fileHeader, File file) throws IOException {
        try {
            Path path = file.toPath();
            FileUtils.setFileAttributes(path, fileHeader.getExternalFileAttributes());
            FileUtils.setFileLastModifiedTime(path, fileHeader.getLastModifiedTime());
        } catch (NoSuchMethodError unused) {
            FileUtils.setFileLastModifiedTimeWithoutNio(file, fileHeader.getLastModifiedTime());
        }
    }

    public static SplitFileInputStream createSplitInputStream(ZipModel zipModel) throws IOException {
        if (zipModel.getZipFile().getName().endsWith(InternalZipConstants.SEVEN_ZIP_SPLIT_FILE_EXTENSION_PATTERN)) {
            return new NumberedSplitFileInputStream(zipModel.getZipFile());
        }
        return new ZipStandardSplitFileInputStream(zipModel.getZipFile(), zipModel.isSplitArchive(), zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk());
    }
}
