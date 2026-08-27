package io.dcloud.feature.pdr;

import android.content.SharedPreferences;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IFeature;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.JSUtil;
import java.io.File;
import java.io.IOException;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class CoreCacheFeatureImpl implements IFeature {
    @Override // io.dcloud.common.DHInterface.IFeature
    public void dispose(String str) {
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public String execute(IWebview iWebview, String str, String[] strArr) throws InterruptedException, NumberFormatException {
        if (str.equals("clear")) {
            try {
                DHFile.deleteFile(iWebview.obtainFrameView().obtainApp().obtainAppWebCachePath());
                DHFile.delete(iWebview.getContext().getCacheDir());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Deprecated_JSUtil.excCallbackSuccess(iWebview, strArr[0], "");
            return null;
        }
        if (str.equals("calculate")) {
            JSUtil.execCallback(iWebview, strArr[0], (new File(iWebview.obtainFrameView().obtainApp().obtainAppWebCachePath()).exists() ? DHFile.getFileSize(r12) : 0L) + DHFile.getFileSize(iWebview.getContext().getCacheDir()), JSUtil.OK, false);
            return null;
        }
        if (!str.equals("setMaxSize")) {
            return null;
        }
        long j = Long.parseLong(strArr[0]);
        SharedPreferences.Editor editorEdit = iWebview.getContext().getSharedPreferences(iWebview.obtainFrameView().obtainApp().obtainAppId(), 0).edit();
        editorEdit.putLong("maxSize", j);
        editorEdit.commit();
        return null;
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public void init(AbsMgr absMgr, String str) {
    }
}
