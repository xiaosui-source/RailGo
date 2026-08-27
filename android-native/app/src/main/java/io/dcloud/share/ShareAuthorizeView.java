package io.dcloud.share;

import android.webkit.WebView;
import io.dcloud.common.DHInterface.IReflectAble;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaFrameItem;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.util.Deprecated_JSUtil;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class ShareAuthorizeView extends AdaFrameItem implements IReflectAble {
    private String a;
    private WebView b;
    private IWebview c;

    public ShareAuthorizeView(IWebview iWebview, String str) {
        super(iWebview.getContext());
        this.c = iWebview;
        this.a = str;
        WebView webView = new WebView(iWebview.getContext());
        this.b = webView;
        a(webView);
        setMainView(this.b);
    }

    private void a(WebView webView) {
    }

    public void load(a aVar, String str) {
        AbsWebviewClient absWebviewClient = (AbsWebviewClient) PlatformUtil.invokeMethod(aVar.a(str), "getWebviewClient", null, new Class[]{ShareAuthorizeView.class}, new Object[]{this});
        this.b.setWebViewClient(absWebviewClient);
        this.b.loadUrl(absWebviewClient.getInitUrl());
    }

    public void onauthenticated(String str) {
        Deprecated_JSUtil.execCallback(this.c, this.a, "{type:'" + str + "'}", 1, true, true);
    }

    public void onerror(String str) {
        Deprecated_JSUtil.execCallback(this.c, this.a, str, 9, false, true);
    }

    public void onloaded() {
        Deprecated_JSUtil.execCallback(this.c, this.a, "{evt:'load'}", 1, true, true);
    }
}
