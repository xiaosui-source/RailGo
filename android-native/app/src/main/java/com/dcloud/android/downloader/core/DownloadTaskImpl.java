package com.dcloud.android.downloader.core;

import com.dcloud.android.downloader.config.Config;
import com.dcloud.android.downloader.core.task.DownloadTask;
import com.dcloud.android.downloader.core.task.GetFileInfoTask;
import com.dcloud.android.downloader.core.thread.DownloadThread;
import com.dcloud.android.downloader.domain.DownloadInfo;
import com.dcloud.android.downloader.domain.DownloadThreadInfo;
import com.dcloud.android.downloader.exception.DownloadException;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class DownloadTaskImpl implements DownloadTask, GetFileInfoTask.OnGetFileInfoListener, DownloadThread.DownloadProgressListener {
    private final Config config;
    private final DownloadInfo downloadInfo;
    private final DownloadResponse downloadResponse;
    private final DownloadTaskListener downloadTaskListener;
    private final ExecutorService executorService;
    private long progress;
    private long lastRefreshTime = System.currentTimeMillis();
    private volatile AtomicBoolean isComputerDownload = new AtomicBoolean(false);
    private final List<DownloadThread> downloadThreads = new ArrayList();

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface DownloadTaskListener {
        void onDownloadSuccess(DownloadInfo downloadInfo);
    }

    public DownloadTaskImpl(ExecutorService executorService, DownloadResponse downloadResponse, DownloadInfo downloadInfo, Config config, DownloadTaskListener downloadTaskListener) {
        this.executorService = executorService;
        this.downloadResponse = downloadResponse;
        this.downloadInfo = downloadInfo;
        this.config = config;
        this.downloadTaskListener = downloadTaskListener;
    }

    private void computerDownloadProgress() {
        this.progress = 0L;
        Iterator<DownloadThreadInfo> it = this.downloadInfo.getDownloadThreadInfos().iterator();
        while (it.hasNext()) {
            this.progress += it.next().getProgress();
        }
        this.downloadInfo.setProgress(this.progress);
    }

    private void getFileInfo() {
        this.executorService.submit(new GetFileInfoTask(this.downloadResponse, this.downloadInfo, this));
    }

    private void removeDownlaodFile() {
        File file = new File(this.downloadInfo.getPath());
        if (file.exists()) {
            file.delete();
        }
    }

    @Override // com.dcloud.android.downloader.core.thread.DownloadThread.DownloadProgressListener
    public void onDownloadSuccess() {
        computerDownloadProgress();
        if (this.downloadInfo.getProgress() == this.downloadInfo.getSize()) {
            this.downloadInfo.setStatus(5);
            this.downloadResponse.onStatusChanged(this.downloadInfo);
            DownloadTaskListener downloadTaskListener = this.downloadTaskListener;
            if (downloadTaskListener != null) {
                downloadTaskListener.onDownloadSuccess(this.downloadInfo);
            }
        }
    }

    @Override // com.dcloud.android.downloader.core.task.GetFileInfoTask.OnGetFileInfoListener
    public void onFailed(DownloadException downloadException) {
    }

    @Override // com.dcloud.android.downloader.core.thread.DownloadThread.DownloadProgressListener
    public void onProgress() {
        if (this.isComputerDownload.get()) {
            return;
        }
        synchronized (this) {
            if (!this.isComputerDownload.get()) {
                this.isComputerDownload.set(true);
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis - this.lastRefreshTime > 1000) {
                    computerDownloadProgress();
                    this.downloadResponse.onStatusChanged(this.downloadInfo);
                    this.lastRefreshTime = jCurrentTimeMillis;
                }
                this.isComputerDownload.set(false);
            }
        }
    }

    @Override // com.dcloud.android.downloader.core.task.GetFileInfoTask.OnGetFileInfoListener
    public void onSuccess(long j, boolean z) {
        this.downloadInfo.setSupportRanges(z);
        this.downloadInfo.setSize(j);
        removeDownlaodFile();
        ArrayList arrayList = new ArrayList();
        if (z) {
            long size = this.downloadInfo.getSize();
            int eachDownloadThread = this.config.getEachDownloadThread();
            long j2 = size / eachDownloadThread;
            int i = 0;
            while (i < eachDownloadThread) {
                long j3 = j2 * i;
                DownloadThreadInfo downloadThreadInfo = new DownloadThreadInfo(i, this.downloadInfo.getId(), this.downloadInfo.getDownloadUrl(), j3, i == eachDownloadThread + (-1) ? size : (j3 + j2) - 1);
                arrayList.add(downloadThreadInfo);
                DownloadThread downloadThread = new DownloadThread(downloadThreadInfo, this.downloadResponse, this.config, this.downloadInfo, this);
                this.executorService.submit(downloadThread);
                this.downloadThreads.add(downloadThread);
                i++;
            }
        } else {
            DownloadThreadInfo downloadThreadInfo2 = new DownloadThreadInfo(0, this.downloadInfo.getId(), this.downloadInfo.getDownloadUrl(), 0L, this.downloadInfo.getSize());
            arrayList.add(downloadThreadInfo2);
            DownloadThread downloadThread2 = new DownloadThread(downloadThreadInfo2, this.downloadResponse, this.config, this.downloadInfo, this);
            this.executorService.submit(downloadThread2);
            this.downloadThreads.add(downloadThread2);
        }
        this.downloadInfo.setDownloadThreadInfos(arrayList);
        this.downloadInfo.setStatus(2);
        this.downloadResponse.onStatusChanged(this.downloadInfo);
    }

    @Override // com.dcloud.android.downloader.core.task.DownloadTask
    public void start() {
        if (this.downloadInfo.getSize() <= 0) {
            getFileInfo();
            return;
        }
        Iterator<DownloadThreadInfo> it = this.downloadInfo.getDownloadThreadInfos().iterator();
        while (it.hasNext()) {
            DownloadThread downloadThread = new DownloadThread(it.next(), this.downloadResponse, this.config, this.downloadInfo, this);
            this.executorService.submit(downloadThread);
            this.downloadThreads.add(downloadThread);
        }
        this.downloadInfo.setStatus(2);
        this.downloadResponse.onStatusChanged(this.downloadInfo);
    }
}
