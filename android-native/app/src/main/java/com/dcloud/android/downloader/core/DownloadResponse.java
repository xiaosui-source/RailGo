package com.dcloud.android.downloader.core;

import com.dcloud.android.downloader.domain.DownloadInfo;
import com.dcloud.android.downloader.exception.DownloadException;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public interface DownloadResponse {
    void handleException(DownloadException downloadException);

    void onStatusChanged(DownloadInfo downloadInfo);
}
