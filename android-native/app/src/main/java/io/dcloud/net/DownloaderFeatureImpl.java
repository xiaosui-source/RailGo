package io.dcloud.net;

import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IFeature;
import io.dcloud.common.DHInterface.IWebview;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class DownloaderFeatureImpl implements IFeature {
    DownloadJSMgr mDownloadMgr = null;

    @Override // io.dcloud.common.DHInterface.IFeature
    public void dispose(String str) {
        DownloadJSMgr downloadJSMgr = this.mDownloadMgr;
        if (downloadJSMgr != null) {
            downloadJSMgr.dispose();
        }
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public String execute(IWebview iWebview, String str, String[] strArr) {
        return this.mDownloadMgr.execute(iWebview, str, strArr);
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public void init(AbsMgr absMgr, String str) {
        this.mDownloadMgr = DownloadJSMgr.getInstance();
    }
}
