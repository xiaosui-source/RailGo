package com.dcloud.android.downloader.callback;

import com.dcloud.android.downloader.db.DownloadDBController;
import com.dcloud.android.downloader.domain.DownloadInfo;
import java.util.List;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public interface DCDownloadManager {
    void download(DownloadInfo downloadInfo);

    List<DownloadInfo> findAllDownloaded();

    List<DownloadInfo> findAllDownloading();

    DownloadInfo getDownloadById(int i);

    DownloadDBController getDownloadDBController();

    void onDestroy();

    void pause(DownloadInfo downloadInfo);

    void remove(DownloadInfo downloadInfo);

    void resume(DownloadInfo downloadInfo);
}
