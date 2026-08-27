package io.dcloud.js.geolocation.system;

import android.content.Context;
import com.facebook.imagepipeline.producers.HttpUrlConnectionNetworkFetcher;
import com.nostra13.dcloudimageloader.core.download.BaseImageDownloader;
import io.dcloud.common.DHInterface.IEventCallback;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaFrameView;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import io.dcloud.js.geolocation.GeoManagerBase;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class LocalGeoManager extends GeoManagerBase {
    public static final String TAG = "LocalGeoManager";
    private io.dcloud.js.geolocation.system.a a;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements IEventCallback {
        a() {
        }

        @Override // io.dcloud.common.DHInterface.IEventCallback
        public Object onCallBack(String str, Object obj) {
            if ((!PdrUtil.isEquals(str, AbsoluteConst.EVENTS_WINDOW_CLOSE) && !PdrUtil.isEquals(str, AbsoluteConst.EVENTS_CLOSE)) || !(obj instanceof IWebview)) {
                return null;
            }
            if (LocalGeoManager.this.a != null) {
                LocalGeoManager.this.a.c(io.dcloud.js.geolocation.system.a.u);
            }
            ((AdaFrameView) ((IWebview) obj).obtainFrameView()).removeFrameViewListener(this);
            return null;
        }
    }

    public LocalGeoManager(Context context) {
        super(context);
    }

    @Override // io.dcloud.js.geolocation.GeoManagerBase
    public String execute(IWebview iWebview, String str, String[] strArr) throws NumberFormatException {
        if (str.equals("getCurrentPosition")) {
            boolean z = Boolean.parseBoolean(strArr[1]);
            int i = Integer.parseInt(strArr[2]);
            String str2 = strArr.length > 7 ? strArr[6] : "null";
            int i2 = !"null".equals(str2) ? Integer.parseInt(str2) : HttpUrlConnectionNetworkFetcher.HTTP_DEFAULT_TIMEOUT;
            if (!PdrUtil.isEquals(strArr[3], "wgs84") && !PdrUtil.isEmpty(strArr[3])) {
                Deprecated_JSUtil.execCallback(iWebview, strArr[0], StringUtil.format(DOMException.JSON_ERROR_INFO, 17, "only support wgs84"), JSUtil.ERROR, true, false);
                return "";
            }
            getCurrentLocation(iWebview, strArr[0], z, i, i2);
            return "";
        }
        if (!str.equals("watchPosition")) {
            if (str.equals("clearWatch")) {
                stop(strArr[0]);
                return "";
            }
            return "";
        }
        boolean z2 = Boolean.parseBoolean(strArr[2]);
        iWebview.obtainFrameView().addFrameViewListener(new a());
        boolean z3 = PdrUtil.isEquals(strArr[3], "wgs84") || PdrUtil.isEmpty(strArr[3]);
        String str3 = strArr.length > 7 ? strArr[6] : "null";
        int i3 = !"null".equals(str3) ? Integer.parseInt(str3) : Integer.MAX_VALUE;
        String str4 = strArr.length > 8 ? strArr[7] : "5000";
        int i4 = !str4.equals("null") ? Integer.parseInt(str4) : BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT;
        if (i4 < 1000) {
            i4 = 1000;
        }
        if (z3) {
            start(iWebview, strArr[0], strArr[1], z2, i3, i4);
            return "";
        }
        Deprecated_JSUtil.execCallback(iWebview, strArr[0], StringUtil.format(DOMException.JSON_ERROR_INFO, 17, "only support wgs84"), JSUtil.ERROR, true, false);
        return "";
    }

    public void getCurrentLocation(IWebview iWebview, String str, boolean z, int i, int i2) {
        a().a(iWebview, i, str, i2);
    }

    @Override // io.dcloud.js.geolocation.GeoManagerBase
    public void onDestroy() {
        io.dcloud.js.geolocation.system.a aVar = this.a;
        if (aVar != null) {
            aVar.a();
        }
        this.a = null;
    }

    public void start(IWebview iWebview, String str, String str2, boolean z, int i, int i2) {
        if (a().b(iWebview, i2, str, i)) {
            this.keySet.add(str2);
        }
    }

    public void stop(String str) {
        if (this.a == null || !this.keySet.contains(str)) {
            return;
        }
        this.keySet.remove(str);
        this.a.c(io.dcloud.js.geolocation.system.a.u);
    }

    io.dcloud.js.geolocation.system.a a() {
        if (this.a == null) {
            this.a = new io.dcloud.js.geolocation.system.a(this.mContext, "");
        }
        return this.a;
    }
}
