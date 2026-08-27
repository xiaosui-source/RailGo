package io.dcloud.feature.pdr;

import com.taobao.weex.common.WXConfig;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IFeature;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.p.y2;
import java.io.IOException;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class LoggerFeatureImpl implements IFeature {
    @Override // io.dcloud.common.DHInterface.IFeature
    public void dispose(String str) {
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public String execute(IWebview iWebview, String str, String[] strArr) throws Throwable {
        if (!str.equals(WXConfig.logLevel)) {
            if (!str.equals("clear")) {
                return null;
            }
            try {
                DHFile.deleteFile(iWebview.obtainFrameView().obtainApp().obtainAppLog());
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        y2.a(iWebview.obtainFrameView().obtainApp().obtainAppLog());
        if (strArr[0].equals("LOG")) {
            y2.d("LOG", strArr[1]);
            return null;
        }
        if (strArr[0].equals("ERROR")) {
            y2.e("ERROR", strArr[1]);
            return null;
        }
        if (strArr[0].equals("WARN")) {
            y2.a("WARN", strArr[1]);
            return null;
        }
        if (strArr[0].equals("INFO")) {
            y2.i("INFO", strArr[1]);
            return null;
        }
        strArr[0].equals("ASSERT");
        return null;
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public void init(AbsMgr absMgr, String str) {
    }
}
