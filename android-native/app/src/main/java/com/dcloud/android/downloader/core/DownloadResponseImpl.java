package com.dcloud.android.downloader.core;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.dcloud.android.downloader.db.DownloadDBController;
import com.dcloud.android.downloader.domain.DownloadInfo;
import com.dcloud.android.downloader.domain.DownloadThreadInfo;
import com.dcloud.android.downloader.exception.DownloadException;
import java.util.Iterator;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class DownloadResponseImpl implements DownloadResponse {
    private static final String TAG = "DownloadResponseImpl";
    private final DownloadDBController downloadDBController;
    private final Handler handler = new Handler(Looper.getMainLooper()) { // from class: com.dcloud.android.downloader.core.DownloadResponseImpl.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            DownloadInfo downloadInfo = (DownloadInfo) message.obj;
            switch (downloadInfo.getStatus()) {
                case 1:
                    if (downloadInfo.getDownloadListener() != null) {
                        downloadInfo.getDownloadListener().onStart();
                        break;
                    }
                    break;
                case 2:
                    if (downloadInfo.getDownloadListener() != null) {
                        downloadInfo.getDownloadListener().onDownloading(downloadInfo.getProgress(), downloadInfo.getSize());
                        break;
                    }
                    break;
                case 3:
                    if (downloadInfo.getDownloadListener() != null) {
                        downloadInfo.getDownloadListener().onWaited();
                        break;
                    }
                    break;
                case 4:
                    if (downloadInfo.getDownloadListener() != null) {
                        downloadInfo.getDownloadListener().onPaused();
                        break;
                    }
                    break;
                case 5:
                    if (downloadInfo.getDownloadListener() != null) {
                        downloadInfo.getDownloadListener().onDownloadSuccess(downloadInfo);
                        break;
                    }
                    break;
                case 6:
                    if (downloadInfo.getDownloadListener() != null) {
                        downloadInfo.getDownloadListener().onDownloadFailed(downloadInfo, downloadInfo.getException());
                        break;
                    }
                    break;
                case 7:
                    if (downloadInfo.getDownloadListener() != null) {
                        downloadInfo.getDownloadListener().onRemoved();
                        break;
                    }
                    break;
            }
        }
    };

    public DownloadResponseImpl(DownloadDBController downloadDBController) {
        this.downloadDBController = downloadDBController;
    }

    @Override // com.dcloud.android.downloader.core.DownloadResponse
    public void handleException(DownloadException downloadException) {
    }

    @Override // com.dcloud.android.downloader.core.DownloadResponse
    public void onStatusChanged(DownloadInfo downloadInfo) {
        if (downloadInfo.getStatus() != 7) {
            this.downloadDBController.createOrUpdate(downloadInfo);
            if (downloadInfo.getDownloadThreadInfos() != null) {
                Iterator<DownloadThreadInfo> it = downloadInfo.getDownloadThreadInfos().iterator();
                while (it.hasNext()) {
                    this.downloadDBController.createOrUpdate(it.next());
                }
            }
        }
        Message messageObtainMessage = this.handler.obtainMessage(downloadInfo.getId());
        messageObtainMessage.obj = downloadInfo;
        messageObtainMessage.sendToTarget();
        Log.d(TAG, "progress:" + downloadInfo.getProgress() + ",size:" + downloadInfo.getSize());
    }
}
