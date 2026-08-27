package net.lingala.zip4j.tasks;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.headers.HeaderUtil;
import net.lingala.zip4j.io.inputstream.SplitFileInputStream;
import net.lingala.zip4j.io.inputstream.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.UnzipParameters;
import net.lingala.zip4j.model.Zip4jConfig;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.tasks.AsyncZipTask;
import net.lingala.zip4j.util.FileUtils;
import net.lingala.zip4j.util.UnzipUtil;
import net.lingala.zip4j.util.Zip4jUtil;

/* loaded from: classes2.dex */
public class ExtractFileTask extends AbstractExtractFileTask<ExtractFileTaskParameters> {
    private char[] password;
    private SplitFileInputStream splitInputStream;

    public ExtractFileTask(ZipModel zipModel, char[] cArr, UnzipParameters unzipParameters, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
        super(zipModel, unzipParameters, asyncTaskParameters);
        this.password = cArr;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:36:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:50:? A[SYNTHETIC] */
    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void executeTask(net.lingala.zip4j.tasks.ExtractFileTask.ExtractFileTaskParameters r10, net.lingala.zip4j.progress.ProgressMonitor r11) throws java.lang.Throwable {
        /*
            r9 = this;
            java.lang.String r0 = net.lingala.zip4j.tasks.ExtractFileTask.ExtractFileTaskParameters.access$000(r10)
            java.util.List r0 = r9.getFileHeadersToExtract(r0)
            net.lingala.zip4j.model.Zip4jConfig r1 = r10.zip4jConfig     // Catch: java.lang.Throwable -> L64
            net.lingala.zip4j.io.inputstream.ZipInputStream r3 = r9.createZipInputStream(r1)     // Catch: java.lang.Throwable -> L64
            net.lingala.zip4j.model.Zip4jConfig r1 = r10.zip4jConfig     // Catch: java.lang.Throwable -> L53
            int r1 = r1.getBufferSize()     // Catch: java.lang.Throwable -> L53
            byte[] r8 = new byte[r1]     // Catch: java.lang.Throwable -> L53
            java.util.Iterator r0 = r0.iterator()     // Catch: java.lang.Throwable -> L53
        L1a:
            boolean r1 = r0.hasNext()     // Catch: java.lang.Throwable -> L53
            if (r1 == 0) goto L45
            java.lang.Object r1 = r0.next()     // Catch: java.lang.Throwable -> L53
            r4 = r1
            net.lingala.zip4j.model.FileHeader r4 = (net.lingala.zip4j.model.FileHeader) r4     // Catch: java.lang.Throwable -> L53
            net.lingala.zip4j.io.inputstream.SplitFileInputStream r1 = r9.splitInputStream     // Catch: java.lang.Throwable -> L53
            r1.prepareExtractionForFileHeader(r4)     // Catch: java.lang.Throwable -> L53
            java.lang.String r1 = net.lingala.zip4j.tasks.ExtractFileTask.ExtractFileTaskParameters.access$100(r10)     // Catch: java.lang.Throwable -> L53
            java.lang.String r2 = net.lingala.zip4j.tasks.ExtractFileTask.ExtractFileTaskParameters.access$000(r10)     // Catch: java.lang.Throwable -> L53
            java.lang.String r6 = r9.determineNewFileName(r1, r2, r4)     // Catch: java.lang.Throwable -> L53
            java.lang.String r5 = net.lingala.zip4j.tasks.ExtractFileTask.ExtractFileTaskParameters.access$200(r10)     // Catch: java.lang.Throwable -> L53
            r2 = r9
            r7 = r11
            r2.extractFile(r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L43
            r11 = r7
            goto L1a
        L43:
            r0 = move-exception
            goto L55
        L45:
            r2 = r9
            if (r3 == 0) goto L4b
            r3.close()     // Catch: java.lang.Throwable -> L62
        L4b:
            net.lingala.zip4j.io.inputstream.SplitFileInputStream r10 = r2.splitInputStream
            if (r10 == 0) goto L52
            r10.close()
        L52:
            return
        L53:
            r0 = move-exception
            r2 = r9
        L55:
            r10 = r0
            if (r3 == 0) goto L61
            r3.close()     // Catch: java.lang.Throwable -> L5c
            goto L61
        L5c:
            r0 = move-exception
            r11 = r0
            r10.addSuppressed(r11)     // Catch: java.lang.Throwable -> L62
        L61:
            throw r10     // Catch: java.lang.Throwable -> L62
        L62:
            r0 = move-exception
            goto L66
        L64:
            r0 = move-exception
            r2 = r9
        L66:
            r10 = r0
            net.lingala.zip4j.io.inputstream.SplitFileInputStream r11 = r2.splitInputStream
            if (r11 == 0) goto L6e
            r11.close()
        L6e:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: net.lingala.zip4j.tasks.ExtractFileTask.executeTask(net.lingala.zip4j.tasks.ExtractFileTask$ExtractFileTaskParameters, net.lingala.zip4j.progress.ProgressMonitor):void");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    public long calculateTotalWork(ExtractFileTaskParameters extractFileTaskParameters) throws ZipException {
        return HeaderUtil.getTotalUncompressedSizeOfAllFileHeaders(getFileHeadersToExtract(extractFileTaskParameters.fileToExtract));
    }

    private List<FileHeader> getFileHeadersToExtract(String str) throws ZipException {
        if (!FileUtils.isZipEntryDirectory(str)) {
            FileHeader fileHeader = HeaderUtil.getFileHeader(getZipModel(), str);
            if (fileHeader == null) {
                throw new ZipException("No file found with name " + str + " in zip file", ZipException.Type.FILE_NOT_FOUND);
            }
            return Collections.singletonList(fileHeader);
        }
        return HeaderUtil.getFileHeadersUnderDirectory(getZipModel().getCentralDirectory().getFileHeaders(), str);
    }

    private ZipInputStream createZipInputStream(Zip4jConfig zip4jConfig) throws IOException {
        this.splitInputStream = UnzipUtil.createSplitInputStream(getZipModel());
        return new ZipInputStream(this.splitInputStream, this.password, zip4jConfig);
    }

    private String determineNewFileName(String str, String str2, FileHeader fileHeader) {
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(str) || !FileUtils.isZipEntryDirectory(str2)) {
            return str;
        }
        String str3 = "/";
        if (str.endsWith("/")) {
            str3 = "";
        }
        return fileHeader.getFileName().replaceFirst(str2, str + str3);
    }

    public static class ExtractFileTaskParameters extends AbstractZipTaskParameters {
        private String fileToExtract;
        private String newFileName;
        private String outputPath;

        public ExtractFileTaskParameters(String str, String str2, String str3, Zip4jConfig zip4jConfig) {
            super(zip4jConfig);
            this.outputPath = str;
            this.fileToExtract = str2;
            this.newFileName = str3;
        }
    }
}
