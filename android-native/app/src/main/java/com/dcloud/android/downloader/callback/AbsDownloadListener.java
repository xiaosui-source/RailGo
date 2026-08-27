package com.dcloud.android.downloader.callback;

import java.lang.ref.SoftReference;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public abstract class AbsDownloadListener implements DownloadListener {
    private SoftReference<Object> userTag;

    public AbsDownloadListener() {
    }

    public SoftReference<Object> getUserTag() {
        return this.userTag;
    }

    public void setUserTag(SoftReference<Object> softReference) {
        this.userTag = softReference;
    }

    public AbsDownloadListener(SoftReference<Object> softReference) {
        this.userTag = softReference;
    }
}
