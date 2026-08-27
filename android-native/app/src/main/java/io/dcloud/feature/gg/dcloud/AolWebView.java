package io.dcloud.feature.gg.dcloud;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.http.SslError;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import com.nostra13.dcloudimageloader.core.download.BaseImageDownloader;
import io.dcloud.common.adapter.ui.webview.WebViewFactory;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.PdrUtil;
import java.util.Locale;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class AolWebView {
    ViewGroup mRootView;
    WebView mWebView;

    public AolWebView(Context context) {
        if (context instanceof Activity) {
            this.mRootView = (ViewGroup) ((Activity) context).getWindow().getDecorView();
        } else {
            this.mRootView = (ViewGroup) ((Activity) DeviceInfo.sApplicationContext).getWindow().getDecorView();
        }
        WebView webView = new WebView(context);
        this.mWebView = webView;
        webView.setVisibility(4);
        this.mRootView.addView(this.mWebView, new FrameLayout.LayoutParams(-1, -1));
        WebSettings settings = this.mWebView.getSettings();
        WebViewFactory.openJSEnabled(settings, null);
        settings.setDomStorageEnabled(true);
        context.getApplicationContext().getCacheDir().getAbsolutePath();
        settings.setAllowFileAccess(false);
        WebViewFactory.setFileAccess(settings, true);
        settings.setSavePassword(false);
        this.mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
        this.mWebView.removeJavascriptInterface("accessibilityTraversal");
        this.mWebView.removeJavascriptInterface("accessibility");
        this.mWebView.setWebViewClient(new WebViewClient() { // from class: io.dcloud.feature.gg.dcloud.AolWebView.1
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, final String str) {
                super.onPageFinished(webView2, str);
                ADHandler.log("aol", "onPageFinished---url=" + str);
                AolWebView.this.mRootView.postDelayed(new Runnable() { // from class: io.dcloud.feature.gg.dcloud.AolWebView.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (AolWebView.this.mWebView == null) {
                            return;
                        }
                        ADHandler.log("aol", "onPageFinished-remove--url=" + str);
                        AolWebView aolWebView = AolWebView.this;
                        ViewGroup viewGroup = aolWebView.mRootView;
                        if (viewGroup != null) {
                            viewGroup.removeView(aolWebView.mWebView);
                            AolWebView.this.mWebView = null;
                        }
                    }
                }, (long) ADSim.getRandomInt(BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT, 20000));
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedSslError(WebView webView2, final SslErrorHandler sslErrorHandler, final SslError sslError) {
                String str;
                if (sslErrorHandler != null) {
                    String str2 = BaseInfo.untrustedca;
                    if (PdrUtil.isEquals(str2, "refuse")) {
                        sslErrorHandler.cancel();
                        return;
                    }
                    if (!PdrUtil.isEquals(str2, "warning")) {
                        WebViewFactory.setSslHandlerState(sslErrorHandler, 1);
                        return;
                    }
                    Context context2 = webView2.getContext();
                    final AlertDialog alertDialogCreate = new AlertDialog.Builder(context2).create();
                    alertDialogCreate.setIcon(R.drawable.ic_secure);
                    alertDialogCreate.setTitle("安全警告");
                    alertDialogCreate.setCanceledOnTouchOutside(false);
                    String url = sslError.getUrl();
                    if (TextUtils.isEmpty(url)) {
                        str = "此站点安全证书存在问题,是否继续?";
                    } else {
                        str = url + "\n此站点安全证书存在问题,是否继续?";
                    }
                    alertDialogCreate.setMessage(str);
                    DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: io.dcloud.feature.gg.dcloud.AolWebView.1.2
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (i == -2) {
                                alertDialogCreate.cancel();
                                alertDialogCreate.dismiss();
                            } else if (i == -3) {
                                sslError.getCertificate().getIssuedBy();
                            } else if (i == -1) {
                                WebViewFactory.setSslHandlerState(sslErrorHandler, 1);
                                alertDialogCreate.dismiss();
                            }
                        }
                    };
                    alertDialogCreate.setButton(-2, context2.getResources().getString(R.string.cancel), onClickListener);
                    alertDialogCreate.setButton(-1, context2.getResources().getString(R.string.ok), onClickListener);
                    alertDialogCreate.show();
                }
            }

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView2, String str) {
                return TextUtils.isEmpty(str) || !str.toLowerCase(Locale.ENGLISH).startsWith("http");
            }
        });
        removeUnSafeJavascriptInterface();
        this.mWebView.setDownloadListener(new DownloadListener() { // from class: io.dcloud.feature.gg.dcloud.AolWebView.2
            @Override // android.webkit.DownloadListener
            public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
            }
        });
    }

    private void removeUnSafeJavascriptInterface() {
    }

    public void loadUrl(String str) {
        WebView webView = this.mWebView;
        if (webView != null) {
            webView.loadUrl(str);
        }
    }
}
