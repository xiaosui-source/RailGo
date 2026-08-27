package io.dcloud.js.geolocation;

import android.text.TextUtils;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.FeatureMessageDispatcher;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IFeature;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.util.PermissionUtil;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.p.o1;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class GeolocationFeatureImpl implements IFeature {
    private o1 a;
    private boolean b = false;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a extends PermissionUtil.StreamPermissionRequest {
        final /* synthetic */ IWebview a;
        final /* synthetic */ String b;
        final /* synthetic */ String[] c;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(IApp iApp, IWebview iWebview, String str, String[] strArr) {
            super(iApp);
            this.a = iWebview;
            this.b = str;
            this.c = strArr;
        }

        @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
        public void onDenied(String str) {
            Deprecated_JSUtil.execCallback(this.a, this.c[0], DOMException.toJSON(22, DOMException.MSG_GEOLOCATION_PERMISSION_ERROR), JSUtil.ERROR, true, false);
        }

        @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
        public void onGranted(String str) {
            if (GeolocationFeatureImpl.this.b) {
                return;
            }
            GeolocationFeatureImpl.this.b = true;
            GeolocationFeatureImpl.this.a.a(this.a, this.b, this.c);
        }
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public void dispose(String str) {
        if (TextUtils.isEmpty(str)) {
            this.a.a();
        }
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public String execute(IWebview iWebview, String str, String[] strArr) {
        if (FeatureMessageDispatcher.contains("record_address")) {
            this.a.a(iWebview, str, strArr);
            return null;
        }
        AppRuntime.checkPrivacyComplianceAndPrompt(iWebview.getContext(), "Geolocation-" + str);
        this.b = false;
        PermissionUtil.usePermission(iWebview.getActivity(), IFeature.F_GEOLOCATION, PermissionUtil.PMS_LOCATION, 2, new a(iWebview.obtainApp(), iWebview, str, strArr));
        return null;
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public void init(AbsMgr absMgr, String str) {
        this.a = new o1(absMgr);
    }
}
