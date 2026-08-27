package net.lingala.zip4j.tasks;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.headers.HeaderWriter;
import net.lingala.zip4j.model.Zip4jConfig;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.tasks.AsyncZipTask;
import net.lingala.zip4j.util.FileUtils;

/* loaded from: classes2.dex */
public class AddFilesToZipTask extends AbstractAddFileToZipTask<AddFilesToZipTaskParameters> {
    public AddFilesToZipTask(ZipModel zipModel, char[] cArr, HeaderWriter headerWriter, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
        super(zipModel, cArr, headerWriter, asyncTaskParameters);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    public void executeTask(AddFilesToZipTaskParameters addFilesToZipTaskParameters, ProgressMonitor progressMonitor) throws IOException {
        verifyZipParameters(addFilesToZipTaskParameters.zipParameters);
        addFilesToZip(determineActualFilesToAdd(addFilesToZipTaskParameters), progressMonitor, addFilesToZipTaskParameters.zipParameters, addFilesToZipTaskParameters.zip4jConfig);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    public long calculateTotalWork(AddFilesToZipTaskParameters addFilesToZipTaskParameters) throws ZipException {
        return calculateWorkForFiles(addFilesToZipTaskParameters.filesToAdd, addFilesToZipTaskParameters.zipParameters);
    }

    private List<File> determineActualFilesToAdd(AddFilesToZipTaskParameters addFilesToZipTaskParameters) throws ZipException {
        ArrayList arrayList = new ArrayList();
        for (File file : addFilesToZipTaskParameters.filesToAdd) {
            arrayList.add(file);
            boolean zIsSymbolicLink = FileUtils.isSymbolicLink(file);
            ZipParameters.SymbolicLinkAction symbolicLinkAction = addFilesToZipTaskParameters.zipParameters.getSymbolicLinkAction();
            if (zIsSymbolicLink && !ZipParameters.SymbolicLinkAction.INCLUDE_LINK_ONLY.equals(symbolicLinkAction)) {
                arrayList.addAll(FileUtils.getFilesInDirectoryRecursive(file, addFilesToZipTaskParameters.zipParameters));
            }
        }
        return arrayList;
    }

    @Override // net.lingala.zip4j.tasks.AbstractAddFileToZipTask, net.lingala.zip4j.tasks.AsyncZipTask
    protected ProgressMonitor.Task getTask() {
        return super.getTask();
    }

    public static class AddFilesToZipTaskParameters extends AbstractZipTaskParameters {
        private final List<File> filesToAdd;
        private final ZipParameters zipParameters;

        public AddFilesToZipTaskParameters(List<File> list, ZipParameters zipParameters, Zip4jConfig zip4jConfig) {
            super(zip4jConfig);
            this.filesToAdd = list;
            this.zipParameters = zipParameters;
        }
    }
}
