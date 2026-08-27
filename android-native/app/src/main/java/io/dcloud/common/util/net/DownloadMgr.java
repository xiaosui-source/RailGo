package io.dcloud.common.util.net;

import io.dcloud.common.adapter.util.Logger;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class DownloadMgr {
    private static DownloadMgr mDownloadMgr;
    private NetWorkLoop mDownloadLoop;

    private DownloadMgr() {
        NetWorkLoop netWorkLoop = new NetWorkLoop();
        this.mDownloadLoop = netWorkLoop;
        netWorkLoop.startThreadPool();
    }

    public static DownloadMgr getDownloadMgr() {
        if (mDownloadMgr == null) {
            mDownloadMgr = new DownloadMgr();
        }
        return mDownloadMgr;
    }

    public void addQuestTask(NetWork netWork) {
        this.mDownloadLoop.addNetWork(netWork);
    }

    public void dispose() {
        Logger.d("DownloadMgr: dispose");
        this.mDownloadLoop.dispose();
        this.mDownloadLoop = null;
        mDownloadMgr = null;
    }

    public void removeTask(NetWork netWork) {
        this.mDownloadLoop.removeNetWork(netWork);
    }
}
