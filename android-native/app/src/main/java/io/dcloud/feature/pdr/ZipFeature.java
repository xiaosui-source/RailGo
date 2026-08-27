package io.dcloud.feature.pdr;

import androidtranscoder.VideoCompressor;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IFeature;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.FileUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import io.dcloud.common.util.ZipUtils;
import io.dcloud.p.h0;
import java.io.File;
import java.util.HashMap;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class ZipFeature implements IFeature {
    HashMap a = null;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements Runnable {
        boolean a = false;
        String b = null;
        String c;
        String d;
        String e;
        IWebview f;

        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (this.a) {
                    Logger.d("compress mUnZipDirPath=" + this.d + ";mZipFilePath" + this.c);
                    File file = new File(this.d);
                    if (!file.exists()) {
                        Deprecated_JSUtil.excCallbackError(this.f, this.e, StringUtil.format(DOMException.JSON_ERROR_INFO, 2, this.b + " open failed:ENOENT (No such file or directory)"), true);
                    }
                    if (JSUtil.checkOperateDirErrorAndCallback(this.f, this.e, this.c)) {
                        return;
                    }
                    File file2 = new File(this.c);
                    File[] fileArrListFiles = file.isDirectory() ? file2.listFiles() : null;
                    if (file.isFile() || fileArrListFiles == null) {
                        fileArrListFiles = new File[]{file};
                    }
                    if (!FileUtil.checkPathAccord(this.f.getContext(), file.getPath(), file2.getPath())) {
                        Deprecated_JSUtil.excCallbackError(this.f, this.e, StringUtil.format(DOMException.JSON_ERROR_INFO, 2, DOMException.MSG_PATH_NOT_PRIVATE_ERROR), true);
                        return;
                    }
                    ZipUtils.zipFiles(fileArrListFiles, file2);
                } else {
                    Logger.d("decompress mUnZipDirPath=" + this.d + ";mZipFilePath" + this.c);
                    if (JSUtil.checkOperateDirErrorAndCallback(this.f, this.e, this.d)) {
                        return;
                    }
                    File file3 = new File(this.c);
                    if (!file3.exists()) {
                        Deprecated_JSUtil.excCallbackError(this.f, this.e, StringUtil.format(DOMException.JSON_ERROR_INFO, 2, this.b + " open failed:ENOENT (No such file or directory)"), true);
                        return;
                    }
                    if (!FileUtil.checkPathAccord(this.f.getContext(), file3.getPath(), this.d)) {
                        Deprecated_JSUtil.excCallbackError(this.f, this.e, StringUtil.format(DOMException.JSON_ERROR_INFO, 2, DOMException.MSG_PATH_NOT_PRIVATE_ERROR), true);
                        return;
                    }
                    ZipUtils.upZipFile(file3, this.d);
                }
                Deprecated_JSUtil.excCallbackSuccess(this.f, this.e, "");
            } catch (Exception e) {
                Deprecated_JSUtil.excCallbackError(this.f, this.e, StringUtil.format(DOMException.JSON_ERROR_INFO, 2, e.getMessage()), true);
            }
        }
    }

    private void a(a aVar) {
        new Thread(aVar).start();
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public void dispose(String str) {
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public String execute(IWebview iWebview, String str, String[] strArr) {
        if (!PdrUtil.isEquals(str, "compress")) {
            if (!PdrUtil.isEquals(str, "decompress")) {
                if (PdrUtil.isEquals(str, "compressImage")) {
                    h0.b(iWebview, strArr);
                    return null;
                }
                if (!PdrUtil.isEquals(str, "compressVideo")) {
                    return null;
                }
                VideoCompressor.getInstance().compressVideo(iWebview, strArr);
                return null;
            }
            a aVar = new a();
            aVar.f = iWebview;
            aVar.a = false;
            IApp iAppObtainApp = iWebview.obtainFrameView().obtainApp();
            aVar.c = iAppObtainApp.convert2AbsFullPath(iWebview.obtainFullUrl(), iAppObtainApp.checkPrivateDirAndCopy2Temp(strArr[0]));
            aVar.d = iAppObtainApp.convert2AbsFullPath(iWebview.obtainFullUrl(), strArr[1]);
            aVar.e = strArr[2];
            a(aVar);
            return null;
        }
        a aVar2 = new a();
        aVar2.f = iWebview;
        aVar2.a = true;
        IApp iAppObtainApp2 = iWebview.obtainFrameView().obtainApp();
        aVar2.b = strArr[0];
        aVar2.d = iAppObtainApp2.convert2AbsFullPath(iWebview.obtainFullUrl(), strArr[0]);
        String str2 = strArr[1];
        if (str2 == null) {
            str2 = AbsoluteConst.MINI_SERVER_APP_DOC + System.currentTimeMillis();
        }
        if (!str2.endsWith(".zip")) {
            str2 = str2 + ".zip";
        }
        aVar2.c = iAppObtainApp2.convert2AbsFullPath(iWebview.obtainFullUrl(), str2);
        aVar2.e = strArr[2];
        a(aVar2);
        return null;
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public void init(AbsMgr absMgr, String str) {
        this.a = new HashMap(1);
    }
}
