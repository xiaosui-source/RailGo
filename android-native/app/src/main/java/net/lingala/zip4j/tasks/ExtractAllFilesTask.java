package net.lingala.zip4j.tasks;

import java.io.IOException;
import net.lingala.zip4j.headers.HeaderUtil;
import net.lingala.zip4j.io.inputstream.SplitFileInputStream;
import net.lingala.zip4j.io.inputstream.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.UnzipParameters;
import net.lingala.zip4j.model.Zip4jConfig;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.tasks.AsyncZipTask;
import net.lingala.zip4j.util.UnzipUtil;

/* loaded from: classes2.dex */
public class ExtractAllFilesTask extends AbstractExtractFileTask<ExtractAllFilesTaskParameters> {
    private final char[] password;
    private SplitFileInputStream splitInputStream;

    public ExtractAllFilesTask(ZipModel zipModel, char[] cArr, UnzipParameters unzipParameters, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
        super(zipModel, unzipParameters, asyncTaskParameters);
        this.password = cArr;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:41:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x006c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:? A[Catch: all -> 0x0076, SYNTHETIC, TRY_LEAVE, TryCatch #1 {all -> 0x0076, blocks: (B:34:0x0075, B:33:0x0072, B:20:0x005c, B:29:0x006c), top: B:45:0x0000, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:? A[SYNTHETIC] */
    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void executeTask(net.lingala.zip4j.tasks.ExtractAllFilesTask.ExtractAllFilesTaskParameters r9, net.lingala.zip4j.progress.ProgressMonitor r10) throws java.lang.Throwable {
        /*
            r8 = this;
            net.lingala.zip4j.model.Zip4jConfig r0 = r9.zip4jConfig     // Catch: java.lang.Throwable -> L78
            net.lingala.zip4j.io.inputstream.ZipInputStream r2 = r8.prepareZipInputStream(r0)     // Catch: java.lang.Throwable -> L78
            net.lingala.zip4j.model.ZipModel r0 = r8.getZipModel()     // Catch: java.lang.Throwable -> L67
            net.lingala.zip4j.model.CentralDirectory r0 = r0.getCentralDirectory()     // Catch: java.lang.Throwable -> L67
            java.util.List r0 = r0.getFileHeaders()     // Catch: java.lang.Throwable -> L67
            java.util.Iterator r0 = r0.iterator()     // Catch: java.lang.Throwable -> L67
        L16:
            boolean r1 = r0.hasNext()     // Catch: java.lang.Throwable -> L67
            if (r1 == 0) goto L59
            java.lang.Object r1 = r0.next()     // Catch: java.lang.Throwable -> L67
            r3 = r1
            net.lingala.zip4j.model.FileHeader r3 = (net.lingala.zip4j.model.FileHeader) r3     // Catch: java.lang.Throwable -> L67
            java.lang.String r1 = r3.getFileName()     // Catch: java.lang.Throwable -> L67
            java.lang.String r4 = "__MACOSX"
            boolean r1 = r1.startsWith(r4)     // Catch: java.lang.Throwable -> L67
            if (r1 == 0) goto L3b
            long r3 = r3.getUncompressedSize()     // Catch: java.lang.Throwable -> L37
            r10.updateWorkCompleted(r3)     // Catch: java.lang.Throwable -> L37
            goto L16
        L37:
            r0 = move-exception
            r9 = r0
            r1 = r8
            goto L6a
        L3b:
            net.lingala.zip4j.io.inputstream.SplitFileInputStream r1 = r8.splitInputStream     // Catch: java.lang.Throwable -> L67
            r1.prepareExtractionForFileHeader(r3)     // Catch: java.lang.Throwable -> L67
            net.lingala.zip4j.model.Zip4jConfig r1 = r9.zip4jConfig     // Catch: java.lang.Throwable -> L67
            int r1 = r1.getBufferSize()     // Catch: java.lang.Throwable -> L67
            byte[] r7 = new byte[r1]     // Catch: java.lang.Throwable -> L67
            java.lang.String r4 = net.lingala.zip4j.tasks.ExtractAllFilesTask.ExtractAllFilesTaskParameters.access$000(r9)     // Catch: java.lang.Throwable -> L67
            r5 = 0
            r1 = r8
            r6 = r10
            r1.extractFile(r2, r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L57
            r8.verifyIfTaskIsCancelled()     // Catch: java.lang.Throwable -> L57
            r10 = r6
            goto L16
        L57:
            r0 = move-exception
            goto L69
        L59:
            r1 = r8
            if (r2 == 0) goto L5f
            r2.close()     // Catch: java.lang.Throwable -> L76
        L5f:
            net.lingala.zip4j.io.inputstream.SplitFileInputStream r9 = r1.splitInputStream
            if (r9 == 0) goto L66
            r9.close()
        L66:
            return
        L67:
            r0 = move-exception
            r1 = r8
        L69:
            r9 = r0
        L6a:
            if (r2 == 0) goto L75
            r2.close()     // Catch: java.lang.Throwable -> L70
            goto L75
        L70:
            r0 = move-exception
            r10 = r0
            r9.addSuppressed(r10)     // Catch: java.lang.Throwable -> L76
        L75:
            throw r9     // Catch: java.lang.Throwable -> L76
        L76:
            r0 = move-exception
            goto L7a
        L78:
            r0 = move-exception
            r1 = r8
        L7a:
            r9 = r0
            net.lingala.zip4j.io.inputstream.SplitFileInputStream r10 = r1.splitInputStream
            if (r10 == 0) goto L82
            r10.close()
        L82:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: net.lingala.zip4j.tasks.ExtractAllFilesTask.executeTask(net.lingala.zip4j.tasks.ExtractAllFilesTask$ExtractAllFilesTaskParameters, net.lingala.zip4j.progress.ProgressMonitor):void");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    public long calculateTotalWork(ExtractAllFilesTaskParameters extractAllFilesTaskParameters) {
        return HeaderUtil.getTotalUncompressedSizeOfAllFileHeaders(getZipModel().getCentralDirectory().getFileHeaders());
    }

    private ZipInputStream prepareZipInputStream(Zip4jConfig zip4jConfig) throws IOException {
        this.splitInputStream = UnzipUtil.createSplitInputStream(getZipModel());
        FileHeader firstFileHeader = getFirstFileHeader(getZipModel());
        if (firstFileHeader != null) {
            this.splitInputStream.prepareExtractionForFileHeader(firstFileHeader);
        }
        return new ZipInputStream(this.splitInputStream, this.password, zip4jConfig);
    }

    private FileHeader getFirstFileHeader(ZipModel zipModel) {
        if (zipModel.getCentralDirectory() == null || zipModel.getCentralDirectory().getFileHeaders() == null || zipModel.getCentralDirectory().getFileHeaders().size() == 0) {
            return null;
        }
        return zipModel.getCentralDirectory().getFileHeaders().get(0);
    }

    public static class ExtractAllFilesTaskParameters extends AbstractZipTaskParameters {
        private final String outputPath;

        public ExtractAllFilesTaskParameters(String str, Zip4jConfig zip4jConfig) {
            super(zip4jConfig);
            this.outputPath = str;
        }
    }
}
