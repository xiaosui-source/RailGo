package com.dcloud.android.downloader.callback;

import com.dcloud.android.downloader.domain.DownloadInfo;
import com.dcloud.android.downloader.exception.DownloadException;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public interface DownloadListener {
    void onDownloadFailed(DownloadInfo downloadInfo, DownloadException downloadException);

    void onDownloadSuccess(DownloadInfo downloadInfo);

    void onDownloading(long j, long j2);

    void onPaused();

    void onRemoved();

    void onStart();

    void onWaited();
}
