package io.dcloud.common.core.ui;

import android.content.Context;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.adapter.ui.AdaUniWebView;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class d extends AdaUniWebView {
    protected d(Context context, b bVar, String str, String str2, JSONObject jSONObject, boolean z) {
        super(context, bVar.obtainApp(), bVar, str, str2, jSONObject, z);
        bVar.l = this;
        bVar.m = getWebviewParent();
    }

    @Override // io.dcloud.common.adapter.ui.AdaUniWebView, io.dcloud.common.DHInterface.IWebview
    public IApp obtainApp() {
        if (obtainFrameView() != null) {
            return obtainFrameView().obtainApp();
        }
        return null;
    }
}
