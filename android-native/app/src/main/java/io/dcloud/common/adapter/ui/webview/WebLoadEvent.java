package io.dcloud.common.adapter.ui.webview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.CookieSyncManager;
import android.webkit.MimeTypeMap;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsoluteLayout;
import android.widget.ProgressBar;
import androidx.webkit.internal.AssetHelper;
import com.nostra13.dcloudimageloader.core.download.BaseImageDownloader;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IDCloudWebviewClientListener;
import io.dcloud.common.DHInterface.IFrameView;
import io.dcloud.common.DHInterface.ISysEventListener;
import io.dcloud.common.DHInterface.ITitleNView;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.adapter.ui.AdaFrameView;
import io.dcloud.common.adapter.ui.AdaWebview;
import io.dcloud.common.adapter.ui.ReceiveJSValue;
import io.dcloud.common.adapter.ui.WaitingView;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.common.adapter.util.DCloudTrustManager;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.InvokeExecutorHelper;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.MessageHandler;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.IntentConst;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.DLGeolocation;
import io.dcloud.common.util.IOUtil;
import io.dcloud.common.util.Md5Utils;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import io.dcloud.common.util.TitleNViewUtil;
import io.src.dcloud.adapter.DCloudAdapterUtil;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public final class WebLoadEvent extends WebViewClient {
    private static final String DIFFERENT_VERSION_JS = "window.plus && (plus.android.import=plus.android.importClass);";
    public static final String ENABLE = "enable";
    private static final String ERROR_TEMPLATE = "javascript:(function(){var b=document.createEvent('HTMLEvents');var a='%s';b.url='%s';b.href='%s';b.initEvent(a,false,true);console.error(a);document.dispatchEvent(b);})();";
    private static final String IF_LOAD_TEMPLATE = "(function(){/*console.log('eval js loading href=' + location.href);*/if(location.__page__load__over__){return 2}if(location.__plusready__||window.__html5plus__){return 1}return 0})();";
    private static final String IF_PLUSREADY_EVENT_TEMPLATE = "(function(){/*console.log('plusready event loading href=' + location.href);*/if(location.__page__load__over__){return 2}if(location.__plusready__||window.__html5plus__){if(!location.__plusready__event__){location.__plusready__event__=true;return 1}else{return 2}}return 0})();";
    private static final String IF_PLUSREADY_TEMPLATE = "(function(){/*console.log('all.js loading href=' + location.href);*/if(location.__page__load__over__){return 2}if(!location.__plusready__){location.__plusready__=true;return 1}else{return 2}return 0})();";
    private static final String IF_PRELOAD_TEMPLATE = "(function(){/*console.log( 'preload js loading href=' + location.href);*/if(location.__page__load__over__){return 2}var jsfile='%s';if(location.__plusready__||window.__html5plus__){location.__preload__=location.__preload__||[];if(location.__preload__.indexOf(jsfile)<0){location.__preload__.push(jsfile);return 1}else{return 2}}return 0})();";
    private static final int LOADABLE = 1;
    private static final int LOADED = 2;
    private static final int NOLOAD = 0;
    public static String PAGE_FINISHED_FLAG = "javascript:setTimeout(function(){location.__page__load__over__ = true;},2000);";
    public static final String PLUSREADY = "html5plus://ready";
    static final String TAG = "WebLoadEvent";
    static final int Timeout_Page_Finish = 6000;
    static final int Timeout_Plus_Inject = 3000;
    public static final String UNIAPP_READY = "uniapp://ready";
    boolean isInitAmapGEO;
    AdaWebview mAdaWebview;
    String mAppid;
    private boolean mClearCache;
    long mShowLoadingTime;
    boolean printLog = true;
    private OnPageFinishedCallack mPageFinishedCallack = null;
    private IDCloudWebviewClientListener mdcloudwebviewclientlister = null;
    private String mLastPageUrl = "";
    WaitingView mWap2AppBlockDialog = null;
    ISysEventListener mWap2AppBlockDialogSysEventListener = null;
    String mPlusJS = null;
    String TYPE_JS = "type_js";
    String TYPE_CSS = "type_css";
    private Runnable mTitleNViewProgressStop = null;
    boolean needResponseRedirect = true;
    ProgressBar mWaitingForWapPage = null;
    Runnable Timeout_Plus_Inject_Runnable = null;
    Runnable Timeout_Page_Finish_Runnable = null;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class CatchFile {
        File mFile = null;
        String mEncoding = null;
        String mContentType = null;
        boolean mExist = false;

        CatchFile() {
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    static class TitleNViewProgressStop implements Runnable {
        private WeakReference<AdaWebview> mAdaWebview;

        public TitleNViewProgressStop(AdaWebview adaWebview) {
            this.mAdaWebview = new WeakReference<>(adaWebview);
        }

        @Override // java.lang.Runnable
        public void run() {
            WeakReference<AdaWebview> weakReference = this.mAdaWebview;
            if (weakReference == null || weakReference.get() == null || this.mAdaWebview.get().obtainFrameView() == null) {
                return;
            }
            Object titleNView = TitleNViewUtil.getTitleNView(this.mAdaWebview.get().obtainFrameView().obtainWindowMgr(), this.mAdaWebview.get().obtainFrameView().obtainWebView(), this.mAdaWebview.get().obtainFrameView(), TitleNViewUtil.getTitleNViewId(this.mAdaWebview.get().obtainFrameView()));
            if (titleNView instanceof ITitleNView) {
                TitleNViewUtil.stopProcess((ITitleNView) titleNView);
            }
        }
    }

    public WebLoadEvent(AdaWebview adaWebview) {
        this.mClearCache = false;
        this.mAppid = null;
        this.isInitAmapGEO = false;
        this.mAdaWebview = adaWebview;
        this.mAppid = adaWebview.obtainApp().obtainAppId();
        String strObtainConfigProperty = adaWebview.obtainApp().obtainConfigProperty(IApp.ConfigProperty.CONFIG_RAM_CACHE_MODE);
        if (BaseInfo.isBase(adaWebview.getContext()) && !ENABLE.equalsIgnoreCase(strObtainConfigProperty)) {
            this.mClearCache = true;
        }
        reset();
        this.isInitAmapGEO = DLGeolocation.checkGeo(adaWebview.getContext());
    }

    private boolean checkCssFile(String str) {
        return !TextUtils.isEmpty(str) && str.contains(".css");
    }

    private boolean checkJsFile(String str) {
        return (TextUtils.isEmpty(str) || !str.contains(".js") || str.contains(".jsp")) ? false : true;
    }

    private WebResourceResponse checkWebResourceResponseRedirect(WebView webView, String str) {
        AdaWebview adaWebview;
        JSONObject jSONObjectObtainThridInfo;
        String strOptString;
        if (!this.needResponseRedirect) {
            return null;
        }
        try {
            if (URLUtil.isNetworkUrl(str) && BaseInfo.existsStreamEnv() && (adaWebview = this.mAdaWebview) != null && adaWebview.obtainFrameView().obtainApp() != null && (jSONObjectObtainThridInfo = this.mAdaWebview.obtainFrameView().obtainApp().obtainThridInfo(IApp.ConfigProperty.ThridInfo.URDJsonData)) != null) {
                JSONArray jSONArrayOptJSONArray = jSONObjectObtainThridInfo.optJSONObject("data").optJSONArray(InvokeExecutorHelper.create("io.dcloud.appstream.rules.util.Tools").invoke("getTopDomainInHost", new URL(str).getHost()));
                if (jSONArrayOptJSONArray != null) {
                    int i = 0;
                    boolean z = false;
                    while (true) {
                        if (i >= jSONArrayOptJSONArray.length()) {
                            strOptString = null;
                            break;
                        }
                        JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
                        JSONArray jSONArrayOptJSONArray2 = jSONObjectOptJSONObject.optJSONArray("match");
                        strOptString = jSONObjectOptJSONObject.optString("redirect");
                        int i2 = 0;
                        while (true) {
                            if (i2 >= jSONArrayOptJSONArray2.length()) {
                                break;
                            }
                            if (Pattern.compile(jSONArrayOptJSONArray2.optString(i)).matcher(str).matches()) {
                                z = true;
                                break;
                            }
                            i2++;
                        }
                        if (z) {
                            break;
                        }
                        i++;
                    }
                    return downloadResponse(webView, strOptString);
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void completeLoadJs(final WebView webView, final String str, final String str2, final String[] strArr, final String str3, final Object... objArr) {
        final SoftReference softReference = new SoftReference(this.mAdaWebview);
        this.mAdaWebview.executeScript(ReceiveJSValue.registerCallback((AdaWebview) softReference.get(), StringUtil.format(str3, objArr), new ReceiveJSValue.ReceiveJSValueCallback() { // from class: io.dcloud.common.adapter.ui.webview.WebLoadEvent.9
            @Override // io.dcloud.common.adapter.ui.ReceiveJSValue.ReceiveJSValueCallback
            public String callback(JSONArray jSONArray) throws JSONException {
                try {
                    int i = jSONArray.getInt(1);
                    if (i == 0 && !PdrUtil.isEquals(str2, "onPageFinished")) {
                        WebLoadEvent.this.completeLoadJs(webView, str, str2, strArr, str3, objArr);
                        return null;
                    }
                    if (1 != i) {
                        return null;
                    }
                    for (String str4 : strArr) {
                        if (softReference.get() != null) {
                            ((AdaWebview) softReference.get()).executeScript(str4);
                        }
                    }
                    return null;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }));
    }

    private boolean directPageIsLaunchPage(IApp iApp) {
        return (iApp == null || TextUtils.isEmpty(iApp.getOriginalDirectPage()) || iApp.obtainWebAppIntent().hasExtra(IntentConst.DIRECT_PAGE)) ? false : true;
    }

    private WebResourceResponse downloadResponse(final WebView webView, final String str) {
        if (!URLUtil.isNetworkUrl(str)) {
            return null;
        }
        try {
            MessageHandler.sendMessage(new MessageHandler.IMessages() { // from class: io.dcloud.common.adapter.ui.webview.WebLoadEvent.1
                @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
                public void execute(Object obj) {
                    webView.stopLoading();
                    WebLoadEvent.this.needResponseRedirect = false;
                    webView.loadUrl(str);
                }
            }, null);
            return new WebResourceResponse(null, null, new ByteArrayInputStream("".getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x015f A[Catch: Exception -> 0x01e4, TryCatch #4 {Exception -> 0x01e4, blocks: (B:21:0x0060, B:23:0x0066, B:25:0x006c, B:28:0x0072, B:30:0x0078, B:32:0x007f, B:33:0x0086, B:36:0x0090, B:38:0x009a, B:39:0x00a9, B:41:0x00af, B:43:0x00b9, B:44:0x00d3, B:46:0x00d7, B:48:0x00e1, B:50:0x00eb, B:51:0x00fa, B:53:0x00fe, B:54:0x0107, B:56:0x010d, B:57:0x0111, B:61:0x0119, B:63:0x0121, B:65:0x0126, B:67:0x012e, B:69:0x0133, B:71:0x0146, B:83:0x01ad, B:85:0x01b5, B:60:0x0116, B:73:0x015f, B:75:0x0167, B:77:0x016f, B:78:0x019c, B:80:0x01a2), top: B:124:0x0060, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0167 A[Catch: Exception -> 0x01e4, TryCatch #4 {Exception -> 0x01e4, blocks: (B:21:0x0060, B:23:0x0066, B:25:0x006c, B:28:0x0072, B:30:0x0078, B:32:0x007f, B:33:0x0086, B:36:0x0090, B:38:0x009a, B:39:0x00a9, B:41:0x00af, B:43:0x00b9, B:44:0x00d3, B:46:0x00d7, B:48:0x00e1, B:50:0x00eb, B:51:0x00fa, B:53:0x00fe, B:54:0x0107, B:56:0x010d, B:57:0x0111, B:61:0x0119, B:63:0x0121, B:65:0x0126, B:67:0x012e, B:69:0x0133, B:71:0x0146, B:83:0x01ad, B:85:0x01b5, B:60:0x0116, B:73:0x015f, B:75:0x0167, B:77:0x016f, B:78:0x019c, B:80:0x01a2), top: B:124:0x0060, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x019c A[Catch: Exception -> 0x01e4, TryCatch #4 {Exception -> 0x01e4, blocks: (B:21:0x0060, B:23:0x0066, B:25:0x006c, B:28:0x0072, B:30:0x0078, B:32:0x007f, B:33:0x0086, B:36:0x0090, B:38:0x009a, B:39:0x00a9, B:41:0x00af, B:43:0x00b9, B:44:0x00d3, B:46:0x00d7, B:48:0x00e1, B:50:0x00eb, B:51:0x00fa, B:53:0x00fe, B:54:0x0107, B:56:0x010d, B:57:0x0111, B:61:0x0119, B:63:0x0121, B:65:0x0126, B:67:0x012e, B:69:0x0133, B:71:0x0146, B:83:0x01ad, B:85:0x01b5, B:60:0x0116, B:73:0x015f, B:75:0x0167, B:77:0x016f, B:78:0x019c, B:80:0x01a2), top: B:124:0x0060, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01ad A[Catch: Exception -> 0x01e4, TryCatch #4 {Exception -> 0x01e4, blocks: (B:21:0x0060, B:23:0x0066, B:25:0x006c, B:28:0x0072, B:30:0x0078, B:32:0x007f, B:33:0x0086, B:36:0x0090, B:38:0x009a, B:39:0x00a9, B:41:0x00af, B:43:0x00b9, B:44:0x00d3, B:46:0x00d7, B:48:0x00e1, B:50:0x00eb, B:51:0x00fa, B:53:0x00fe, B:54:0x0107, B:56:0x010d, B:57:0x0111, B:61:0x0119, B:63:0x0121, B:65:0x0126, B:67:0x012e, B:69:0x0133, B:71:0x0146, B:83:0x01ad, B:85:0x01b5, B:60:0x0116, B:73:0x015f, B:75:0x0167, B:77:0x016f, B:78:0x019c, B:80:0x01a2), top: B:124:0x0060, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01b5 A[Catch: Exception -> 0x01e4, TRY_LEAVE, TryCatch #4 {Exception -> 0x01e4, blocks: (B:21:0x0060, B:23:0x0066, B:25:0x006c, B:28:0x0072, B:30:0x0078, B:32:0x007f, B:33:0x0086, B:36:0x0090, B:38:0x009a, B:39:0x00a9, B:41:0x00af, B:43:0x00b9, B:44:0x00d3, B:46:0x00d7, B:48:0x00e1, B:50:0x00eb, B:51:0x00fa, B:53:0x00fe, B:54:0x0107, B:56:0x010d, B:57:0x0111, B:61:0x0119, B:63:0x0121, B:65:0x0126, B:67:0x012e, B:69:0x0133, B:71:0x0146, B:83:0x01ad, B:85:0x01b5, B:60:0x0116, B:73:0x015f, B:75:0x0167, B:77:0x016f, B:78:0x019c, B:80:0x01a2), top: B:124:0x0060, inners: #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private android.webkit.WebResourceResponse downloadResponseInjection(android.webkit.WebResourceResponse r15, java.lang.String r16, java.lang.String r17, java.lang.String r18, java.lang.String r19) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 644
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.adapter.ui.webview.WebLoadEvent.downloadResponseInjection(android.webkit.WebResourceResponse, java.lang.String, java.lang.String, java.lang.String, java.lang.String):android.webkit.WebResourceResponse");
    }

    private String getCacheLocalFilePath(String str, String str2) {
        IApp iAppObtainApp;
        AdaWebview adaWebview = this.mAdaWebview;
        if (adaWebview == null || (iAppObtainApp = adaWebview.obtainApp()) == null) {
            return null;
        }
        if (this.TYPE_JS.equals(str2)) {
            return iAppObtainApp.obtainAppTempPath() + "__plus__cache__/" + Md5Utils.md5(str) + ".js";
        }
        return iAppObtainApp.obtainAppTempPath() + "__plus__cache__/" + Md5Utils.md5(str) + ".css";
    }

    public static String getMimeType(String str) {
        String fileExtensionFromUrl = MimeTypeMap.getFileExtensionFromUrl(str);
        String mimeTypeFromExtension = fileExtensionFromUrl != null ? MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtensionFromUrl) : null;
        return TextUtils.isEmpty(mimeTypeFromExtension) ? AssetHelper.DEFAULT_MIME_TYPE : mimeTypeFromExtension;
    }

    private CatchFile getUrlFile(String str, String str2) throws Exception {
        String cacheLocalFilePath = getCacheLocalFilePath(str, str2);
        try {
            if (DHFile.isExist(cacheLocalFilePath)) {
                CatchFile catchFile = new CatchFile();
                File file = new File(cacheLocalFilePath);
                catchFile.mFile = file;
                catchFile.mExist = file.exists();
                return catchFile;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            URL url = new URL(str);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            if (httpURLConnection instanceof HttpsURLConnection) {
                try {
                    SSLSocketFactory sSLSocketFactory = DCloudTrustManager.getSSLSocketFactory();
                    if (sSLSocketFactory != null) {
                        ((HttpsURLConnection) httpURLConnection).setSSLSocketFactory(sSLSocketFactory);
                    }
                    ((HttpsURLConnection) httpURLConnection).setHostnameVerifier(DCloudTrustManager.getHostnameVerifier(true));
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
            httpURLConnection.setConnectTimeout(BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT);
            httpURLConnection.setReadTimeout(BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            int responseCode = httpURLConnection.getResponseCode();
            String contentType = httpURLConnection.getContentType();
            if (!TextUtils.isEmpty(contentType) && (((str2.equals(this.TYPE_JS) && contentType.contains("javascript")) || (str2.equals(this.TYPE_CSS) && (contentType.contains("text/css") || url.getPath().endsWith(".css")))) && (responseCode == 200 || responseCode == 206))) {
                InputStream inputStream = httpURLConnection.getInputStream();
                boolean zWriteFile = DHFile.writeFile(inputStream, cacheLocalFilePath);
                IOUtil.close(inputStream);
                if (zWriteFile) {
                    CatchFile catchFile2 = new CatchFile();
                    File file2 = new File(cacheLocalFilePath);
                    catchFile2.mFile = file2;
                    catchFile2.mExist = file2.exists();
                    catchFile2.mEncoding = httpURLConnection.getContentEncoding();
                    catchFile2.mContentType = contentType;
                    return catchFile2;
                }
                File file3 = new File(cacheLocalFilePath);
                if (file3.exists()) {
                    file3.delete();
                }
            }
            return null;
        } catch (IOException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    private WebResourceResponse handleDecode(String str, WebResourceResponse webResourceResponse) {
        InputStream encryptionInputStream;
        return (TextUtils.isEmpty(str) || (encryptionInputStream = WebResUtil.getEncryptionInputStream(str, this.mAdaWebview.obtainApp())) == null) ? webResourceResponse : new WebResourceResponse(getMimeType(str), "UTF-8", encryptionInputStream);
    }

    private void hideLoading() {
        this.mAdaWebview.obtainMainView().post(new Runnable() { // from class: io.dcloud.common.adapter.ui.webview.WebLoadEvent.12
            @Override // java.lang.Runnable
            public void run() {
                if (WebLoadEvent.this.mAdaWebview == null) {
                    return;
                }
                long jCurrentTimeMillis = System.currentTimeMillis();
                WebLoadEvent webLoadEvent = WebLoadEvent.this;
                if (jCurrentTimeMillis - webLoadEvent.mShowLoadingTime < 1000) {
                    webLoadEvent.mAdaWebview.getDCWebView().getWebView().postDelayed(this, jCurrentTimeMillis - WebLoadEvent.this.mShowLoadingTime);
                } else {
                    AdaFrameView adaFrameView = webLoadEvent.mAdaWebview.mFrameView;
                    adaFrameView.dispatchFrameViewEvents(AbsoluteConst.EVENTS_HIDE_LOADING, adaFrameView);
                }
            }
        });
    }

    private void listenPlusInjectTimeout(final WebView webView, final String str, final String str2) {
        AdaWebview adaWebview = this.mAdaWebview;
        if (adaWebview == null && adaWebview.mPlusrequire.equals("none")) {
            return;
        }
        Runnable runnable = this.Timeout_Plus_Inject_Runnable;
        if (runnable != null) {
            MessageHandler.removeCallbacks(runnable);
        }
        Runnable runnable2 = new Runnable() { // from class: io.dcloud.common.adapter.ui.webview.WebLoadEvent.7
            @Override // java.lang.Runnable
            public void run() {
                AdaWebview adaWebview2 = WebLoadEvent.this.mAdaWebview;
                if (adaWebview2 == null || adaWebview2.isRealInject(str)) {
                    return;
                }
                Logger.i("WebViewData", "listenPlusInjectTimeout url=" + str);
                WebLoadEvent.this.onLoadPlusJSContent(webView, str, "plus_inject_timeout_" + str2);
                WebLoadEvent webLoadEvent = WebLoadEvent.this;
                webLoadEvent.mAdaWebview.mPreloadJsLoading = false;
                webLoadEvent.Timeout_Plus_Inject_Runnable = null;
            }
        };
        this.Timeout_Plus_Inject_Runnable = runnable2;
        MessageHandler.postDelayed(runnable2, 3000L);
    }

    private void loadAllJSContent(WebView webView, String str, String str2) {
        if (onLoadPlusJSContent(webView, str, str2)) {
            injectScript(webView, str, str2);
        }
    }

    private void onExecuteEvalJSStatck(WebView webView, String str, String str2) {
        String str3 = this.mAdaWebview.get_eval_js_stack();
        if (PdrUtil.isEmpty(str3)) {
            return;
        }
        completeLoadJs(webView, str, str2, new String[]{str3}, IF_LOAD_TEMPLATE, str);
    }

    private void onLoadCssContent() {
        AdaWebview adaWebview = this.mAdaWebview;
        if (adaWebview.mIsAdvanceCss) {
            Logger.i(TAG, "已经提前注入CSS完成。不需要再注入了" + this.mAdaWebview.getOriginalUrl());
        } else if (adaWebview.loadCssFile()) {
            Logger.i(TAG, "提前注入CSS完成" + this.mAdaWebview.getOriginalUrl());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onLoadPlusJSContent(final WebView webView, final String str, final String str2) {
        if (this.mAdaWebview.mPlusrequire.equals("none")) {
            return false;
        }
        if (this.mAdaWebview.isRealInject(str)) {
            Logger.i(TAG, "all.js已经注入完成。不需要再注入了" + this.mAdaWebview.getOriginalUrl());
            return true;
        }
        Logger.i(TAG, "onLoadPlusJSContent all.js注入 " + this.mAdaWebview.getOriginalUrl() + ";tag=" + str2 + ";mAdaWebview.mPlusrequire=" + this.mAdaWebview.mPlusrequire);
        if (this.mAdaWebview.mPlusrequire.equals("later") && str2.equals("onPageFinished")) {
            webView.postDelayed(new Runnable() { // from class: io.dcloud.common.adapter.ui.webview.WebLoadEvent.6
                @Override // java.lang.Runnable
                public void run() {
                    WebLoadEvent webLoadEvent = WebLoadEvent.this;
                    AdaWebview adaWebview = webLoadEvent.mAdaWebview;
                    if (adaWebview != null) {
                        String str3 = str2;
                        adaWebview.mPlusInjectTag = str3;
                        adaWebview.mPlusLoading = true;
                        WebView webView2 = webView;
                        String str4 = str;
                        webLoadEvent.completeLoadJs(webView2, str4, str3, new String[]{webLoadEvent.mPlusJS, WebLoadEvent.DIFFERENT_VERSION_JS}, WebLoadEvent.IF_PLUSREADY_TEMPLATE, str4);
                    }
                }
            }, 2000L);
        } else {
            AdaWebview adaWebview = this.mAdaWebview;
            adaWebview.mPlusInjectTag = str2;
            adaWebview.mPlusLoading = true;
            completeLoadJs(webView, str, str2, new String[]{this.mPlusJS, DIFFERENT_VERSION_JS}, IF_PLUSREADY_TEMPLATE, str);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPlusreadyEvent(WebView webView, String str, String str2) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(StringUtil.format(AbsoluteConst.EVENTS_DOCUMENT_EXECUTE_TEMPLATE, AbsoluteConst.EVENTS_PLUSREADY));
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(StringUtil.format(AbsoluteConst.EVENTS_IFRAME_DOUCMENT_EXECUTE_TEMPLATE, AbsoluteConst.EVENTS_PLUSREADY));
        completeLoadJs(webView, str, str2, new String[]{stringBuffer.toString(), stringBuffer2.toString(), "plus.webview.currentWebview().__needTouchEvent__()"}, IF_PLUSREADY_EVENT_TEMPLATE, str);
    }

    private void printOpenLog(WebView webView, String str) {
        IApp iAppObtainApp;
        String url = webView.getUrl();
        if (!BaseInfo.isBase(webView.getContext()) || TextUtils.isEmpty(str) || TextUtils.isEmpty(url) || (iAppObtainApp = this.mAdaWebview.mFrameView.obtainApp()) == null || str.startsWith(DeviceInfo.HTTP_PROTOCOL) || url.startsWith(DeviceInfo.HTTP_PROTOCOL) || str.startsWith(DeviceInfo.HTTPS_PROTOCOL) || url.startsWith(DeviceInfo.HTTPS_PROTOCOL)) {
            return;
        }
        Log.i(AbsoluteConst.HBUILDER_TAG, StringUtil.format(AbsoluteConst.OPENLOG, WebResUtil.getHBuilderPrintUrl(iAppObtainApp.convert2RelPath(WebResUtil.getOriginalUrl(url))), WebResUtil.getHBuilderPrintUrl(iAppObtainApp.convert2RelPath(WebResUtil.getOriginalUrl(str)))));
    }

    private void printResourceLog(WebView webView, IApp iApp, String str, String str2) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str) || webView == null || iApp == null || !BaseInfo.isBase(webView.getContext()) || str.equalsIgnoreCase(str2) || TextUtils.isEmpty(str2)) {
            return;
        }
        if (this.mClearCache && !this.mLastPageUrl.equalsIgnoreCase(str)) {
            webView.clearCache(true);
        }
        this.mLastPageUrl = str;
        String originalUrl = WebResUtil.getOriginalUrl(str);
        if (str2.startsWith(DeviceInfo.HTTP_PROTOCOL) || str2.startsWith(DeviceInfo.HTTPS_PROTOCOL)) {
            return;
        }
        Log.i(AbsoluteConst.HBUILDER_TAG, StringUtil.format(AbsoluteConst.RESOURCELOG, WebResUtil.getHBuilderPrintUrl(iApp.convert2RelPath(originalUrl)), WebResUtil.getHBuilderPrintUrl(iApp.convert2RelPath(WebResUtil.getOriginalUrl(str2)))));
    }

    private boolean shouldRuntimeHandle(String str) {
        return PdrUtil.isDeviceRootDir(str) || PdrUtil.isNetPath(str) || str.startsWith(DeviceInfo.FILE_PROTOCOL);
    }

    private void showLoading() {
        this.mAdaWebview.getDCWebView().getWebView().post(new Runnable() { // from class: io.dcloud.common.adapter.ui.webview.WebLoadEvent.11
            @Override // java.lang.Runnable
            public void run() {
                AdaWebview adaWebview = WebLoadEvent.this.mAdaWebview;
                if (adaWebview != null) {
                    AdaFrameView adaFrameView = adaWebview.mFrameView;
                    adaFrameView.dispatchFrameViewEvents(AbsoluteConst.EVENTS_SHOW_LOADING, adaFrameView);
                }
            }
        });
        this.mShowLoadingTime = System.currentTimeMillis();
    }

    private void startTryLoadAllJSContent(WebView webView, String str, String str2) {
        loadAllJSContent(webView, str, str2);
    }

    public void closeWap2AppBlockDialog(boolean z) {
        WaitingView waitingView = this.mWap2AppBlockDialog;
        if (waitingView != null) {
            waitingView.close();
            this.mAdaWebview.obtainApp().unregisterSysEventListener(this.mWap2AppBlockDialogSysEventListener, ISysEventListener.SysEventType.onKeyUp);
            this.mWap2AppBlockDialog = null;
            this.mWap2AppBlockDialogSysEventListener = null;
            if (z) {
                AdaWebview adaWebview = this.mAdaWebview;
                adaWebview.loadUrl(adaWebview.mRecordLastUrl);
            }
        }
    }

    public void destroy() {
        this.mAdaWebview = null;
        this.mPlusJS = null;
        this.mTitleNViewProgressStop = null;
        this.mWap2AppBlockDialog = null;
        this.mWaitingForWapPage = null;
    }

    @Override // android.webkit.WebViewClient
    public void doUpdateVisitedHistory(WebView webView, String str, boolean z) {
        if (PdrUtil.isEmpty(this.mdcloudwebviewclientlister)) {
            return;
        }
        this.mdcloudwebviewclientlister.doUpdateVisitedHistory(webView, str, z);
    }

    String getErrorPage() {
        String strHandleWap2appTemplateFilePath = this.mAdaWebview.mFrameView.obtainFrameOptions().errorPage;
        if (!URLUtil.isNetworkUrl(strHandleWap2appTemplateFilePath)) {
            if (TextUtils.isEmpty(strHandleWap2appTemplateFilePath)) {
                String strObtainConfigProperty = this.mAdaWebview.obtainApp().obtainConfigProperty("error");
                return !"none".equals(strObtainConfigProperty) ? this.mAdaWebview.obtainApp().convert2WebviewFullPath(null, strObtainConfigProperty) : "none";
            }
            IApp iAppObtainApp = this.mAdaWebview.obtainApp();
            if (!"none".equals(strHandleWap2appTemplateFilePath)) {
                String strConvert2AbsFullPath = iAppObtainApp.convert2AbsFullPath(this.mAdaWebview.obtainFullUrl(), strHandleWap2appTemplateFilePath);
                File file = new File(strConvert2AbsFullPath);
                if (file.exists()) {
                    return iAppObtainApp.convert2WebviewFullPath(this.mAdaWebview.obtainFullUrl(), strHandleWap2appTemplateFilePath);
                }
                if (BaseInfo.isWap2AppAppid(iAppObtainApp.obtainAppId())) {
                    String relPath = WebResUtil.getRelPath(PdrUtil.stripQuery(PdrUtil.stripAnchor(strConvert2AbsFullPath)), iAppObtainApp);
                    if (WebResUtil.isWap2appTemplateFile(iAppObtainApp, relPath)) {
                        strHandleWap2appTemplateFilePath = WebResUtil.handleWap2appTemplateFilePath(relPath);
                        file = new File(strHandleWap2appTemplateFilePath);
                    }
                }
                if (!file.exists()) {
                    String strObtainConfigProperty2 = iAppObtainApp.obtainConfigProperty("error");
                    return !"none".equals(strObtainConfigProperty2) ? iAppObtainApp.convert2WebviewFullPath(null, strObtainConfigProperty2) : "none";
                }
                return DeviceInfo.FILE_PROTOCOL + strHandleWap2appTemplateFilePath;
            }
        }
        return strHandleWap2appTemplateFilePath;
    }

    void injectScript(final WebView webView, final String str, final String str2) {
        if (str2.equals("onPageFinished") && this.mAdaWebview.mPlusrequire.equals("later")) {
            webView.postDelayed(new Runnable() { // from class: io.dcloud.common.adapter.ui.webview.WebLoadEvent.10
                @Override // java.lang.Runnable
                public void run() {
                    WebLoadEvent webLoadEvent = WebLoadEvent.this;
                    if (webLoadEvent.mAdaWebview != null) {
                        webLoadEvent.onPreloadJSContent(webView, str, str2);
                        WebLoadEvent.this.onPlusreadyEvent(webView, str, str2);
                    }
                }
            }, 2000L);
        } else {
            onPreloadJSContent(webView, str, str2);
            onPlusreadyEvent(webView, str, str2);
        }
        onLoadCssContent();
    }

    public void listenPageFinishTimeout(final WebView webView, final String str, final String str2) {
        AdaWebview adaWebview = this.mAdaWebview;
        if (adaWebview.mLoaded && adaWebview.isRealInject(str)) {
            injectScript(webView, str, str2);
            return;
        }
        Runnable runnable = this.Timeout_Page_Finish_Runnable;
        if (runnable != null) {
            MessageHandler.removeCallbacks(runnable);
        }
        Runnable runnable2 = new Runnable() { // from class: io.dcloud.common.adapter.ui.webview.WebLoadEvent.8
            @Override // java.lang.Runnable
            public void run() {
                AdaWebview adaWebview2 = WebLoadEvent.this.mAdaWebview;
                if (adaWebview2 == null || adaWebview2.mLoaded || !adaWebview2.isRealInject(str)) {
                    return;
                }
                WebLoadEvent.this.injectScript(webView, str, "page_finished_timeout_" + str2);
                WebLoadEvent.this.Timeout_Page_Finish_Runnable = null;
            }
        };
        this.Timeout_Page_Finish_Runnable = runnable2;
        MessageHandler.postDelayed(runnable2, 6000L);
    }

    @Override // android.webkit.WebViewClient
    public void onLoadResource(WebView webView, String str) throws NumberFormatException {
        if (this.mAdaWebview == null) {
            return;
        }
        if (this.printLog) {
            Logger.i(TAG, "onLoadResource url=" + str);
        }
        this.needResponseRedirect = true;
        printResourceLog(webView, this.mAdaWebview.mFrameView.obtainApp(), webView.getUrl(), str);
        IFrameView iFrameViewObtainFrameView = this.mAdaWebview.obtainFrameView();
        if (iFrameViewObtainFrameView.obtainStatus() != 3) {
            iFrameViewObtainFrameView.onLoading();
        }
        if (this.mAdaWebview.checkResourceLoading(str)) {
            this.mAdaWebview.mFrameView.dispatchFrameViewEvents(AbsoluteConst.EVENTS_LISTEN_RESOURCE_LOADING, "{url:'" + str + "'}");
        }
        this.mAdaWebview.dispatchWebviewStateEvent(2, str);
        super.onLoadResource(webView, str);
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView webView, String str) throws NumberFormatException, IOException {
        boolean z;
        if (this.mAdaWebview == null) {
            return;
        }
        Logger.d(TAG, "onPageFinished=" + str);
        if (PdrUtil.isEmpty(this.mAdaWebview.mFrameView.obtainApp())) {
            Logger.e(TAG, "mAdaWebview.mFrameView.obtainApp()===null");
            return;
        }
        if (this.mAdaWebview.hadClearHistory(str)) {
            this.mAdaWebview.hasErrorPage = false;
            return;
        }
        if (this.mAdaWebview.hasErrorPage) {
            String errorPage = getErrorPage();
            if (!PdrUtil.isEquals(str, errorPage) && (!"data:text/html,chromewebdata".equals(str) || !"none".equals(errorPage))) {
                return;
            } else {
                z = true;
            }
        } else {
            z = false;
        }
        if (this.mAdaWebview.unReceiveTitle) {
            Logger.i(TAG, "onPageFinished will exe titleUpdate =" + str);
            AdaWebview adaWebview = this.mAdaWebview;
            adaWebview.mFrameView.dispatchFrameViewEvents(AbsoluteConst.EVENTS_TITLE_UPDATE, adaWebview.getDCWebView().getTitle());
            this.mAdaWebview.unReceiveTitle = false;
        }
        CookieSyncManager.getInstance().sync();
        Logger.i(TAG, "onPageFinished" + this.mAdaWebview.getOriginalUrl());
        this.mAdaWebview.dispatchWebviewStateEvent(1, str);
        this.mAdaWebview.loadForceAHeadJs();
        onLoadPlusJSContent(webView, str, "onPageFinished");
        if (this.mAdaWebview.isRealInject(str)) {
            injectScript(webView, str, "onPageFinished");
        }
        AdaWebview adaWebview2 = this.mAdaWebview;
        adaWebview2.mFrameView.dispatchFrameViewEvents(AbsoluteConst.EVENTS_LOADED, adaWebview2);
        if (z) {
            this.mAdaWebview.executeScript(StringUtil.format(ERROR_TEMPLATE, "error", this.mAdaWebview.getOriginalUrl(), this.mAdaWebview.errorPageUrl));
            AdaWebview adaWebview3 = this.mAdaWebview;
            adaWebview3.errorPageUrl = null;
            adaWebview3.hasErrorPage = false;
        }
        AdaFrameView adaFrameView = this.mAdaWebview.mFrameView;
        if (adaFrameView.obtainStatus() != 3) {
            adaFrameView.onPreShow(null);
        }
        AdaWebview adaWebview4 = this.mAdaWebview;
        if (!adaWebview4.mLoaded) {
            adaWebview4.mLoaded = true;
            adaWebview4.mPlusLoaded = true;
        }
        super.onPageFinished(webView, str);
        if (this.mAdaWebview.justClearOption && !str.startsWith("data:")) {
            Logger.d(TAG, "onPageFinished mWebViewImpl.clearHistory url=" + str);
            this.mAdaWebview.getDCWebView().clearHistory();
            this.mAdaWebview.justClearOption = false;
        }
        this.mAdaWebview.getDCWebView().webReload(false);
        OnPageFinishedCallack onPageFinishedCallack = this.mPageFinishedCallack;
        if (onPageFinishedCallack != null) {
            onPageFinishedCallack.onLoad();
        }
        if (this.mWaitingForWapPage != null) {
            try {
                ((ViewGroup) this.mAdaWebview.obtainFrameView().obtainMainView()).removeView(this.mWaitingForWapPage);
                this.mWaitingForWapPage = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.mAdaWebview.checkInjectSitemap();
        if (PdrUtil.isEmpty(this.mdcloudwebviewclientlister)) {
            return;
        }
        this.mdcloudwebviewclientlister.onPageFinished(webView, str);
    }

    @Override // android.webkit.WebViewClient
    public void onPageStarted(WebView webView, String str, Bitmap bitmap) throws NumberFormatException {
        AdaWebview adaWebview = this.mAdaWebview;
        if (adaWebview == null) {
            return;
        }
        if (adaWebview.hasErrorPage) {
            String errorPage = getErrorPage();
            if (!PdrUtil.isEquals(str, errorPage) && ((!"data:text/html,chromewebdata".equals(str) || !"none".equals(errorPage)) && (PdrUtil.isEmpty(this.mAdaWebview.errorPageUrl) || !this.mAdaWebview.errorPageUrl.equals(str)))) {
                AdaWebview adaWebview2 = this.mAdaWebview;
                adaWebview2.hasErrorPage = false;
                adaWebview2.errorPageUrl = null;
            }
        }
        Logger.i(TAG, "onPageStarted url=" + str);
        this.mAdaWebview.onPageStarted();
        printOpenLog(webView, str);
        if (this.mAdaWebview.hadClearHistory(str)) {
            return;
        }
        if (this.mAdaWebview.mPlusrequire.equals("ahead")) {
            listenPlusInjectTimeout(webView, str, "onPageStarted");
        }
        if (!str.startsWith("data:")) {
            this.mAdaWebview.getDCWebView().setUrlStr(str);
        }
        this.mAdaWebview.resetPlusLoadSaveData();
        if (!PdrUtil.isEmpty(this.mAdaWebview.getDCWebView().getUrlStr())) {
            AdaWebview adaWebview3 = this.mAdaWebview;
            adaWebview3.mFrameView.dispatchFrameViewEvents(AbsoluteConst.EVENTS_WINDOW_CLOSE, adaWebview3);
        }
        this.mAdaWebview.dispatchWebviewStateEvent(0, str);
        AdaWebview adaWebview4 = this.mAdaWebview;
        AdaFrameView adaFrameView = adaWebview4.mFrameView;
        adaFrameView.dispatchFrameViewEvents("loading", adaWebview4);
        if (adaFrameView.obtainStatus() != 3) {
            adaFrameView.onPreLoading();
        }
        super.onPageStarted(webView, str, bitmap);
        if (this.mAdaWebview.mFrameView.getFrameType() == 3) {
            try {
                if (this.mWaitingForWapPage == null) {
                    this.mWaitingForWapPage = new ProgressBar(this.mAdaWebview.getContext());
                    int i = AndroidResources.mResources.getDisplayMetrics().widthPixels;
                    int i2 = AndroidResources.mResources.getDisplayMetrics().heightPixels;
                    int i3 = PdrUtil.parseInt("7%", i, -1);
                    ((ViewGroup) this.mAdaWebview.obtainFrameView().obtainMainView()).addView(this.mWaitingForWapPage, new AbsoluteLayout.LayoutParams(i3, i3, (i - i3) / 2, (i2 - i3) / 2));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Object titleNView = TitleNViewUtil.getTitleNView(this.mAdaWebview.obtainFrameView().obtainWindowMgr(), this.mAdaWebview.obtainFrameView().obtainWebView(), this.mAdaWebview.obtainFrameView(), TitleNViewUtil.getTitleNViewId(this.mAdaWebview.obtainFrameView()));
        if (titleNView instanceof ITitleNView) {
            if (this.mTitleNViewProgressStop != null) {
                TitleNViewUtil.stopProcess((ITitleNView) titleNView);
                this.mAdaWebview.obtainWindowView().removeCallbacks(this.mTitleNViewProgressStop);
                this.mTitleNViewProgressStop = null;
            }
            this.mTitleNViewProgressStop = new TitleNViewProgressStop(this.mAdaWebview);
            TitleNViewUtil.startProcess((ITitleNView) titleNView);
            this.mAdaWebview.obtainWindowView().postDelayed(this.mTitleNViewProgressStop, 6000L);
        }
        if (PdrUtil.isEmpty(this.mdcloudwebviewclientlister)) {
            return;
        }
        this.mdcloudwebviewclientlister.onPageStarted(webView, str, bitmap);
    }

    public void onPreloadJSContent(WebView webView, String str, String str2) {
        if (this.mAdaWebview.obtainFrameView().obtainApp() == null || this.mAdaWebview.obtainFrameView().obtainApp().manifestBeParsed()) {
            AdaWebview adaWebview = this.mAdaWebview;
            if (adaWebview.mPreloadJsLoaded) {
                Logger.i(TAG, "mPreloadJs 已经提前注入JS完成。不需要再注入了" + this.mAdaWebview.getOriginalUrl());
                return;
            }
            String preLoadJsString = adaWebview.getPreLoadJsString();
            if (!PdrUtil.isEmpty(preLoadJsString)) {
                this.mAdaWebview.mPreloadJsLoading = true;
                Logger.i(TAG, " tag=" + str2 + ";url=" + str);
                completeLoadJs(webView, str, str2, new String[]{preLoadJsString}, IF_PRELOAD_TEMPLATE, this.mAdaWebview.mPreloadJsFile);
                this.mAdaWebview.mPreloadJsLoaded = true;
            }
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(WebView webView, int i, String str, final String str2) throws NumberFormatException {
        if (this.mAdaWebview == null) {
            return;
        }
        Logger.e(TAG, "onReceivedError description=" + str + ";failingUrl=" + str2 + ";errorCode=" + i);
        this.mAdaWebview.dispatchWebviewStateEvent(5, str);
        AdaWebview adaWebview = this.mAdaWebview;
        adaWebview.mFrameView.dispatchFrameViewEvents(AbsoluteConst.EVENTS_FAILED, adaWebview);
        AdaWebview adaWebview2 = this.mAdaWebview;
        adaWebview2.hasErrorPage = true;
        adaWebview2.errorPageUrl = str2;
        final IApp iAppObtainApp = adaWebview2.mFrameView.obtainApp();
        if (iAppObtainApp != null) {
            try {
                if (BaseInfo.isWap2AppAppid(iAppObtainApp.obtainAppId()) && this.mAdaWebview.mFrameView.getFrameType() == 2 && !TextUtils.equals("none", iAppObtainApp.obtainConfigProperty("launchError"))) {
                    Context context = this.mAdaWebview.getContext();
                    final AlertDialog alertDialogCreate = new AlertDialog.Builder(context).create();
                    alertDialogCreate.setTitle(R.string.dcloud_common_tips);
                    alertDialogCreate.setCanceledOnTouchOutside(false);
                    alertDialogCreate.setMessage(context.getString(R.string.dcloud_common_no_network_tips));
                    DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: io.dcloud.common.adapter.ui.webview.WebLoadEvent.3
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i2) {
                            if (i2 == -2) {
                                WebLoadEvent.this.mAdaWebview.getActivity().startActivity(new Intent("android.settings.SETTINGS"));
                            } else if (i2 == -3) {
                                Logger.e(WebLoadEvent.TAG, "onReceivedError try again");
                                DCloudAdapterUtil.getIActivityHandler(WebLoadEvent.this.mAdaWebview.getActivity());
                                WebLoadEvent.this.mAdaWebview.loadUrl(str2);
                            } else if (i2 == -1) {
                                Activity activity = WebLoadEvent.this.mAdaWebview.getActivity();
                                DCloudAdapterUtil.getIActivityHandler(activity).updateParam("closewebapp", activity);
                            }
                            alertDialogCreate.dismiss();
                        }
                    };
                    alertDialogCreate.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: io.dcloud.common.adapter.ui.webview.WebLoadEvent.4
                        @Override // android.content.DialogInterface.OnKeyListener
                        public boolean onKey(DialogInterface dialogInterface, int i2, KeyEvent keyEvent) {
                            if (i2 != 4) {
                                return false;
                            }
                            alertDialogCreate.dismiss();
                            Activity activity = WebLoadEvent.this.mAdaWebview.getActivity();
                            DCloudAdapterUtil.getIActivityHandler(activity).updateParam("closewebapp", activity);
                            return false;
                        }
                    });
                    alertDialogCreate.setButton(-2, context.getString(R.string.dcloud_common_set_network), onClickListener);
                    alertDialogCreate.setButton(-3, context.getString(R.string.dcloud_common_retry), onClickListener);
                    alertDialogCreate.setButton(-1, context.getString(R.string.dcloud_common_exit), onClickListener);
                    alertDialogCreate.show();
                    iAppObtainApp.registerSysEventListener(new ISysEventListener() { // from class: io.dcloud.common.adapter.ui.webview.WebLoadEvent.5
                        @Override // io.dcloud.common.DHInterface.ISysEventListener
                        public boolean onExecute(ISysEventListener.SysEventType sysEventType, Object obj) {
                            AdaWebview adaWebview3;
                            if (ISysEventListener.SysEventType.onResume != sysEventType || (adaWebview3 = WebLoadEvent.this.mAdaWebview) == null) {
                                return false;
                            }
                            adaWebview3.obtainMainView().postDelayed(new Runnable() { // from class: io.dcloud.common.adapter.ui.webview.WebLoadEvent.5.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    Logger.e(WebLoadEvent.TAG, "onReceivedError 500ms retry after the onResume");
                                    DCloudAdapterUtil.getIActivityHandler(WebLoadEvent.this.mAdaWebview.getActivity());
                                    AnonymousClass5 anonymousClass5 = AnonymousClass5.this;
                                    WebLoadEvent.this.mAdaWebview.loadUrl(str2);
                                }
                            }, 500L);
                            iAppObtainApp.unregisterSysEventListener(this, sysEventType);
                            return false;
                        }
                    }, ISysEventListener.SysEventType.onResume);
                    Logger.e(TAG, "onReceivedError do clearHistory");
                    this.mAdaWebview.clearHistory();
                } else {
                    String errorPage = getErrorPage();
                    if ("none".equals(errorPage)) {
                        this.mAdaWebview.hasErrorPage = false;
                    } else {
                        Logger.e(TAG, "onReceivedError  load errorPage " + errorPage);
                        this.mAdaWebview.loadUrl(errorPage);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (PdrUtil.isEmpty(this.mdcloudwebviewclientlister)) {
            return;
        }
        this.mdcloudwebviewclientlister.onReceivedError(webView, i, str, str2);
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedSslError(WebView webView, final SslErrorHandler sslErrorHandler, final SslError sslError) {
        AdaWebview adaWebview = this.mAdaWebview;
        if (adaWebview == null || adaWebview.obtainApp() == null) {
            return;
        }
        String strObtainConfigProperty = this.mAdaWebview.obtainApp().obtainConfigProperty(IApp.ConfigProperty.CONFIG_UNTRUSTEDCA);
        Logger.i("onReceivedSslError", "onReceivedSslError++type====" + strObtainConfigProperty);
        if (PdrUtil.isEquals(strObtainConfigProperty, "refuse")) {
            sslErrorHandler.cancel();
        } else if (PdrUtil.isEquals(strObtainConfigProperty, "warning")) {
            Context context = webView.getContext();
            final AlertDialog alertDialogCreate = new AlertDialog.Builder(context).create();
            alertDialogCreate.setIcon(android.R.drawable.ic_secure);
            alertDialogCreate.setTitle(R.string.dcloud_common_safety_warning);
            alertDialogCreate.setCanceledOnTouchOutside(false);
            String url = sslError.getUrl();
            String string = context.getString(R.string.dcloud_common_certificate_continue);
            if (!TextUtils.isEmpty(url)) {
                string = url + "\n" + string;
            }
            alertDialogCreate.setMessage(string);
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: io.dcloud.common.adapter.ui.webview.WebLoadEvent.2
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
            alertDialogCreate.setButton(-2, context.getResources().getString(android.R.string.cancel), onClickListener);
            alertDialogCreate.setButton(-1, context.getResources().getString(android.R.string.ok), onClickListener);
            alertDialogCreate.show();
        } else {
            WebViewFactory.setSslHandlerState(sslErrorHandler, 1);
        }
        if (PdrUtil.isEmpty(this.mdcloudwebviewclientlister)) {
            return;
        }
        this.mdcloudwebviewclientlister.onReceivedSslError(webView, sslErrorHandler, sslError);
    }

    @Override // android.webkit.WebViewClient
    public void onUnhandledKeyEvent(WebView webView, KeyEvent keyEvent) {
        super.onUnhandledKeyEvent(webView, keyEvent);
        if (webView instanceof SysWebView) {
            SysWebView sysWebView = (SysWebView) webView;
            if (keyEvent.getAction() == 0) {
                sysWebView.doKeyDownAction(keyEvent.getKeyCode(), keyEvent);
            } else if (keyEvent.getAction() == 1) {
                sysWebView.doKeyUpAction(keyEvent.getKeyCode(), keyEvent);
            }
        }
    }

    public void onUpdatePlusData(WebView webView, String str, String str2) {
        AdaWebview adaWebview = this.mAdaWebview;
        adaWebview.executeScript(adaWebview.getScreenAndDisplayJson(adaWebview));
        onExecuteEvalJSStatck(webView, str, str2);
    }

    public void reset() {
        this.mPlusJS = "(function(){/*console.log('all.js loading href=' + location.href);*/if(location.__page__load__over__){return 2}if(!location.__plusready__){location.__plusready__=true;return 1}else{return 2}return 0})();\n" + this.mAdaWebview.mFrameView.obtainPrePlusreadyJs() + "\nwindow.plus && (plus.android.import=plus.android.importClass);";
    }

    public void setDcloudwebviewclientListener(IDCloudWebviewClientListener iDCloudWebviewClientListener) {
        this.mdcloudwebviewclientlister = iDCloudWebviewClientListener;
    }

    public void setPageFinishedCallack(OnPageFinishedCallack onPageFinishedCallack) {
        this.mPageFinishedCallack = onPageFinishedCallack;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:111:0x01e0 A[Catch: Exception -> 0x023e, TRY_ENTER, TryCatch #0 {Exception -> 0x023e, blocks: (B:100:0x01bb, B:102:0x01c7, B:104:0x01cf, B:111:0x01e0, B:113:0x01e6, B:115:0x01ec), top: B:193:0x01bb }] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x01fb  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x0305  */
    /* JADX WARN: Type inference failed for: r5v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v16 */
    /* JADX WARN: Type inference failed for: r5v17 */
    /* JADX WARN: Type inference failed for: r5v18, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v19 */
    /* JADX WARN: Type inference failed for: r5v25 */
    @Override // android.webkit.WebViewClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public android.webkit.WebResourceResponse shouldInterceptRequest(android.webkit.WebView r13, java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 781
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.adapter.ui.webview.WebLoadEvent.shouldInterceptRequest(android.webkit.WebView, java.lang.String):android.webkit.WebResourceResponse");
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent) {
        return false;
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView webView, String str) throws URISyntaxException {
        if (this.mAdaWebview == null) {
            return false;
        }
        Logger.e(TAG, "shouldOverrideUrlLoading url=" + str);
        AdaWebview adaWebview = this.mAdaWebview;
        adaWebview.mProgressIntValue = 0;
        adaWebview.mRecordLastUrl = str;
        if (adaWebview.checkOverrideUrl(str)) {
            Logger.e(TAG, "检测拦截回调shouldOverrideUrlLoading url=" + str);
            this.mAdaWebview.mFrameView.dispatchFrameViewEvents(AbsoluteConst.EVENTS_OVERRIDE_URL_LOADING, "{url:'" + str + "'}");
            return true;
        }
        if (this.mAdaWebview.mFrameView.getFrameType() == 5 || (this.mAdaWebview.mFrameView.getFrameType() == 2 && directPageIsLaunchPage(this.mAdaWebview.obtainApp()))) {
            this.mAdaWebview.obtainApp().updateDirectPage(str);
        }
        if (shouldRuntimeHandle(str) || this.mAdaWebview.mFrameView.getFrameType() == 6) {
            if (PdrUtil.isEmpty(this.mdcloudwebviewclientlister)) {
                return false;
            }
            return this.mdcloudwebviewclientlister.shouldOverrideUrlLoading(webView, str);
        }
        try {
            if (str.startsWith("sms:")) {
                int iIndexOf = str.indexOf("sms:");
                int iIndexOf2 = str.indexOf(Operators.CONDITION_IF_STRING);
                if (iIndexOf2 == -1) {
                    this.mAdaWebview.getActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                    return true;
                }
                String strSubstring = str.substring(iIndexOf + 4, iIndexOf2);
                String strSubstring2 = str.substring(iIndexOf2 + 1);
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("sms:" + strSubstring));
                intent.putExtra("address", strSubstring);
                intent.putExtra("sms_body", strSubstring2);
                this.mAdaWebview.getActivity().startActivity(intent);
            } else if (str.startsWith("intent://")) {
                Intent uri = Intent.parseUri(str, 1);
                uri.addCategory("android.intent.category.BROWSABLE");
                uri.setComponent(null);
                uri.setSelector(null);
                if (this.mAdaWebview.getActivity().getPackageManager().queryIntentActivities(uri, 0).size() > 0) {
                    this.mAdaWebview.getActivity().startActivityIfNeeded(uri, -1);
                }
            } else {
                AdaWebview adaWebview2 = this.mAdaWebview;
                if (adaWebview2 != null && adaWebview2.getActivity() != null && this.mAdaWebview.obtainApp().checkSchemeWhite(str)) {
                    this.mAdaWebview.getActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                }
            }
        } catch (Exception unused) {
            Logger.e(TAG, "ActivityNotFoundException url=" + str);
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0040 A[PHI: r1
      0x0040: PHI (r1v13 ??) = (r1v12 ??), (r1v16 ??) binds: [B:15:0x003a, B:17:0x003e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004d A[Catch: all -> 0x007a, Exception -> 0x007d, PHI: r1
      0x004d: PHI (r1v15 ??) = (r1v13 ??), (r1v18 ?? I:??[int, float, short, byte, char]) binds: [B:18:0x0040, B:17:0x003e] A[DONT_GENERATE, DONT_INLINE], TryCatch #4 {Exception -> 0x007d, all -> 0x007a, blocks: (B:9:0x001c, B:20:0x004d, B:19:0x0042, B:21:0x0051, B:23:0x005f, B:25:0x0065, B:26:0x0070), top: B:49:0x001c }] */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r1v24 */
    /* JADX WARN: Type inference failed for: r1v25 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.lang.String] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private android.webkit.WebResourceResponse downloadResponse(android.webkit.WebView r10, java.lang.String r11, java.lang.String r12, android.webkit.WebResourceResponse r13, java.io.File r14, boolean r15) throws java.lang.Throwable {
        /*
            r9 = this;
            boolean r0 = android.webkit.URLUtil.isNetworkUrl(r12)
            if (r0 == 0) goto L9c
            if (r14 != 0) goto La
            goto L9c
        La:
            r9.showLoading()
            r1 = 0
            java.net.URL r0 = new java.net.URL     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L82
            r0.<init>(r12)     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L82
            java.net.URLConnection r0 = r0.openConnection()     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L82
            r8 = r0
            java.net.HttpURLConnection r8 = (java.net.HttpURLConnection) r8     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L82
            r0 = 5000(0x1388, float:7.006E-42)
            r8.setConnectTimeout(r0)     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L7d
            r8.setReadTimeout(r0)     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L7d
            java.lang.String r0 = "GET"
            r8.setRequestMethod(r0)     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L7d
            r0 = 1
            r8.setDoInput(r0)     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L7d
            int r0 = r8.getResponseCode()     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L7d
            r1 = 200(0xc8, float:2.8E-43)
            if (r0 == r1) goto L51
            r1 = 206(0xce, float:2.89E-43)
            if (r0 != r1) goto L38
            goto L51
        L38:
            r1 = 400(0x190, float:5.6E-43)
            if (r0 < r1) goto L40
            r1 = 500(0x1f4, float:7.0E-43)
            if (r0 < r1) goto L4d
        L40:
            if (r15 == 0) goto L4d
            r7 = 0
            r1 = r9
            r2 = r10
            r3 = r11
            r4 = r12
            r5 = r13
            r6 = r14
            r1.downloadResponse(r2, r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L7d
            goto L73
        L4d:
            r9.hideLoading()     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L7d
            goto L73
        L51:
            java.io.InputStream r0 = r8.getInputStream()     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L7d
            java.lang.String r1 = r14.getAbsolutePath()     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L7d
            boolean r0 = io.dcloud.common.adapter.io.DHFile.writeFile(r0, r1)     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L7d
            if (r0 == 0) goto L63
            r9.hideLoading()     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L7d
            goto L73
        L63:
            if (r15 == 0) goto L70
            r7 = 0
            r1 = r9
            r2 = r10
            r3 = r11
            r4 = r12
            r5 = r13
            r6 = r14
            r1.downloadResponse(r2, r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L7d
            goto L73
        L70:
            r9.hideLoading()     // Catch: java.lang.Throwable -> L7a java.lang.Exception -> L7d
        L73:
            r8.disconnect()
            r9.hideLoading()
            goto L8e
        L7a:
            r0 = move-exception
            r1 = r8
            goto L93
        L7d:
            r0 = move-exception
            r1 = r8
            goto L83
        L80:
            r0 = move-exception
            goto L93
        L82:
            r0 = move-exception
        L83:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L80
            if (r1 == 0) goto L8b
            r1.disconnect()
        L8b:
            r9.hideLoading()
        L8e:
            android.webkit.WebResourceResponse r0 = r9.handleDecode(r11, r13)
            return r0
        L93:
            if (r1 == 0) goto L98
            r1.disconnect()
        L98:
            r9.hideLoading()
            throw r0
        L9c:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.adapter.ui.webview.WebLoadEvent.downloadResponse(android.webkit.WebView, java.lang.String, java.lang.String, android.webkit.WebResourceResponse, java.io.File, boolean):android.webkit.WebResourceResponse");
    }
}
