package com.dcloud.android.downloader.config;

import com.dcloud.android.downloader.db.DownloadDBController;
import io.dcloud.feature.gg.dcloud.ADSim;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class Config {
    private DownloadDBController downloadDBController;
    private final String method = "GET";
    private int connectTimeout = ADSim.INTISPLSH;
    private int readTimeout = ADSim.INTISPLSH;
    private int downloadThread = 2;
    private int eachDownloadThread = 1;
    private String databaseName = "download_info.db";
    private int databaseVersion = 2;
    private int retryDownloadCount = 2;

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public String getDatabaseName() {
        return this.databaseName;
    }

    public int getDatabaseVersion() {
        return this.databaseVersion;
    }

    public DownloadDBController getDownloadDBController() {
        return this.downloadDBController;
    }

    public int getDownloadThread() {
        return this.downloadThread;
    }

    public int getEachDownloadThread() {
        return this.eachDownloadThread;
    }

    public String getMethod() {
        return "GET";
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public int getRetryDownloadCount() {
        return this.retryDownloadCount;
    }

    public void setConnectTimeout(int i) {
        this.connectTimeout = i;
    }

    public void setDatabaseName(String str) {
        this.databaseName = str;
    }

    public void setDatabaseVersion(int i) {
        this.databaseVersion = i;
    }

    public void setDownloadDBController(DownloadDBController downloadDBController) {
        this.downloadDBController = downloadDBController;
    }

    public void setDownloadThread(int i) {
        this.downloadThread = i;
    }

    public void setEachDownloadThread(int i) {
        this.eachDownloadThread = i;
    }

    public void setReadTimeout(int i) {
        this.readTimeout = i;
    }

    public void setRetryDownloadCount(int i) {
        this.retryDownloadCount = i;
    }
}
