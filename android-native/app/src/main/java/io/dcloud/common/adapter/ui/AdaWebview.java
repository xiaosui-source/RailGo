package io.dcloud.common.adapter.ui;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.dcloud.android.widget.DCProgressView;
import com.dcloud.android.widget.DCWebViewProgressBar;
import com.nostra13.dcloudimageloader.core.ImageLoaderL;
import com.taobao.weex.common.Constants;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.ui.component.WXBasicComponentType;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.common.DHInterface.IDCloudWebviewClientListener;
import io.dcloud.common.DHInterface.IFrameView;
import io.dcloud.common.DHInterface.IJsInterface;
import io.dcloud.common.DHInterface.ITitleNView;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.DHInterface.IWebviewStateListener;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.adapter.ui.ReceiveJSValue;
import io.dcloud.common.adapter.ui.webview.DCWebView;
import io.dcloud.common.adapter.ui.webview.OnPageFinishedCallack;
import io.dcloud.common.adapter.ui.webview.WebResUtil;
import io.dcloud.common.adapter.ui.webview.WebViewFactory;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.MessageHandler;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.IntentConst;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.IOUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import io.dcloud.common.util.TitleNViewUtil;
import io.dcloud.common.util.net.http.WebkitCookieManagerProxy;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.CookieHandler;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public abstract class AdaWebview extends AdaContainerFrameItem implements IWebview {
    public static boolean ScreemOrientationChangedNeedLayout = false;
    public static RecordView mRecordView = null;
    public static String sCustomUserAgent = null;
    public static CustomeizedInputConnection sCustomeizedInputConnection = null;
    public static String sDefalutUserAgent = null;
    public static boolean setedWebViewData = false;
    public String errorPageUrl;
    public MessageHandler.IMessages executeScriptListener;
    private String favoriteOptions;
    public boolean hasErrorPage;
    private boolean isDisposed;
    boolean isPause;
    private boolean isStart;
    public boolean justClearOption;
    String mAppid;
    String mCssString;
    public String mEncoding;
    String[] mEvalJsOptionStack;
    private int mFixBottomHeight;
    private Object mFlag;
    public String mForceAHeadJsFile;
    public boolean mForceAHeadJsFileLoaded;
    private String mFrameId;
    public AdaFrameView mFrameView;
    public String mInjectGEO;
    public boolean mInjectGeoLoaded;
    String mInjectPlusLoadedUrl;
    public String mInjectPlusWidthJs;
    public boolean mIsAdvanceCss;
    IJsInterface mJsInterfaces;
    JSONObject mListenResourceLoadingOptions;
    public boolean mLoadCompleted;
    public boolean mLoaded;
    boolean mLoading;
    public MessageHandler.IMessages mMesssageListener;
    public boolean mNeedInjection;
    boolean mNeedSitemapJson;
    private IWebview mOpener;
    JSONArray mOverrideResourceRequestOptions;
    JSONObject mOverrideUrlLoadingDataOptions;
    public String mPlusInjectTag;
    public boolean mPlusLoaded;
    public boolean mPlusLoading;
    public String mPlusrequire;
    public ArrayList<String> mPreloadJsFile;
    public boolean mPreloadJsLoaded;
    public boolean mPreloadJsLoading;
    public int mProgress;
    public int mProgressIntValue;
    public View mProgressView;
    public ReceiveJSValue mReceiveJSValue_android42;
    public String mRecordLastUrl;
    private boolean mShareable;
    private ArrayList<IWebviewStateListener> mStateListeners;
    private String mVideoFullscreen;
    private DCWebViewProgressBar mWebProgressView;
    DCWebView mWebViewImpl;
    AdaWebViewParent mWebViewParent;
    private String mWebviewANID;
    private String mWebviewUUID;
    private String needTouchEvent;
    String originalUrl;
    private String shareOptions;
    public boolean unReceiveTitle;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface IFExecutePreloadJSContentCallBack {
        void callback(String str, String str2);
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public class OverrideResourceRequestItem {
        public String redirect = null;
        public String mime = null;
        public String encoding = null;
        public JSONObject headerJson = null;

        public OverrideResourceRequestItem() {
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class WebProgressView extends DCProgressView {
        public WebProgressView(Context context) {
            super(context);
        }
    }

    protected AdaWebview(Context context) {
        super(context);
        this.unReceiveTitle = true;
        this.mWebViewParent = null;
        this.mRecordLastUrl = null;
        this.mWebViewImpl = null;
        this.mFrameView = null;
        this.mLoaded = false;
        this.mLoadCompleted = false;
        this.mPreloadJsLoaded = false;
        this.mPreloadJsLoading = false;
        this.mPlusLoaded = false;
        this.mPlusLoading = false;
        this.mPlusInjectTag = "page_finished";
        this.mIsAdvanceCss = false;
        this.mNeedInjection = true;
        this.mEncoding = null;
        this.mWebviewUUID = null;
        this.mWebviewANID = null;
        this.mFrameId = null;
        this.mJsInterfaces = null;
        this.hasErrorPage = false;
        this.errorPageUrl = null;
        this.originalUrl = null;
        this.mVideoFullscreen = "auto";
        this.needTouchEvent = "";
        this.favoriteOptions = "";
        this.shareOptions = "";
        this.mShareable = true;
        this.mPlusrequire = "normal";
        this.mInjectGEO = "none";
        this.mInjectGeoLoaded = false;
        this.mProgressIntValue = 0;
        this.isDisposed = false;
        this.mReceiveJSValue_android42 = null;
        this.isPause = false;
        this.mFlag = null;
        this.mInjectPlusLoadedUrl = null;
        this.mEvalJsOptionStack = null;
        this.mForceAHeadJsFileLoaded = false;
        this.mForceAHeadJsFile = null;
        this.mPreloadJsFile = new ArrayList<>(2);
        this.mCssString = "";
        this.executeScriptListener = new MessageHandler.IMessages() { // from class: io.dcloud.common.adapter.ui.AdaWebview.4
            @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
            public void execute(Object obj) {
                String str = (String) obj;
                DCWebView dCWebView = AdaWebview.this.mWebViewImpl;
                if (dCWebView != null) {
                    if (!str.startsWith(AbsoluteConst.PROTOCOL_JAVASCRIPT)) {
                        str = AbsoluteConst.PROTOCOL_JAVASCRIPT + str;
                    }
                    dCWebView.loadUrl(str);
                }
            }
        };
        this.mMesssageListener = new MessageHandler.IMessages() { // from class: io.dcloud.common.adapter.ui.AdaWebview.5
            @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
            public void execute(Object obj) {
                Object[] objArr = (Object[]) obj;
                AdaWebview.this.mJsInterfaces.exec(String.valueOf(objArr[0]), String.valueOf(objArr[1]), (JSONArray) objArr[2]);
            }
        };
        this.mStateListeners = null;
        this.mProgress = 0;
        this.isStart = false;
        this.justClearOption = false;
        this.mLoading = false;
        this.mNeedSitemapJson = false;
        this.mOverrideResourceRequestOptions = null;
        this.mOverrideUrlLoadingDataOptions = null;
        this.mListenResourceLoadingOptions = null;
        initANID();
    }

    private String checkRedCssline(String str) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes());
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(byteArrayInputStream));
        while (true) {
            try {
                String line = bufferedReader.readLine();
                if (line == null) {
                    return stringBuffer.substring(0, stringBuffer.length() - 1);
                }
                stringBuffer.append(JSUtil.QUOTE + line + "\"\n+");
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static void clearData() {
        setedWebViewData = false;
        sCustomUserAgent = null;
        sDefalutUserAgent = null;
    }

    public static IJsInterface[] combineObj2Array(IJsInterface[] iJsInterfaceArr, IJsInterface iJsInterface) {
        IJsInterface[] iJsInterfaceArr2;
        if (iJsInterfaceArr == null) {
            iJsInterfaceArr2 = new IJsInterface[1];
        } else {
            int length = iJsInterfaceArr.length;
            IJsInterface[] iJsInterfaceArr3 = new IJsInterface[length + 1];
            System.arraycopy(iJsInterfaceArr, 0, iJsInterfaceArr3, 0, length);
            iJsInterfaceArr2 = iJsInterfaceArr3;
        }
        iJsInterfaceArr2[iJsInterfaceArr2.length] = iJsInterface;
        return iJsInterfaceArr2;
    }

    private void pushEvalJsOption(String str) {
        String[] strArr = this.mEvalJsOptionStack;
        if (strArr == null) {
            this.mEvalJsOptionStack = new String[1];
        } else {
            String[] strArr2 = new String[strArr.length + 1];
            this.mEvalJsOptionStack = strArr2;
            System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
        }
        String[] strArr3 = this.mEvalJsOptionStack;
        strArr3[strArr3.length - 1] = str;
        Logger.d("adawebview", "webviewimp=(" + this.mWebViewImpl + ");pushEvalJs=" + str);
    }

    private void startWebProgress() throws NumberFormatException {
        if (this.mFrameView.obtainFrameOptions().mProgressJson == null) {
            DCWebViewProgressBar dCWebViewProgressBar = this.mWebProgressView;
            if (dCWebViewProgressBar != null) {
                dCWebViewProgressBar.setVisibility(8);
                if (this.mWebProgressView.getParent() != null) {
                    ((ViewGroup) this.mWebProgressView.getParent()).removeView(this.mWebProgressView);
                }
                this.mWebProgressView = null;
                return;
            }
            return;
        }
        if (this.mWebProgressView == null) {
            this.mWebProgressView = new DCWebViewProgressBar(getContext());
        }
        JSONObject jSONObject = this.mFrameView.obtainFrameOptions().mProgressJson;
        String strOptString = jSONObject.optString("color", "#00FF00");
        String strOptString2 = jSONObject.optString("height", "2px");
        this.mWebProgressView.setColorInt(PdrUtil.stringToColor(strOptString));
        int iConvertToScreenInt = PdrUtil.convertToScreenInt(strOptString2, obtainWindowView().getMeasuredWidth(), 0, getScale());
        this.mWebProgressView.setVisibility(0);
        this.mWebProgressView.setAlphaInt(255);
        if (this.mWebProgressView.getParent() == null && getWebviewParent() != null) {
            ViewGroup viewGroup = (ViewGroup) getWebviewParent().obtainMainView();
            if (viewGroup == null) {
                return;
            } else {
                viewGroup.addView(this.mWebProgressView, new ViewGroup.LayoutParams(-1, iConvertToScreenInt));
            }
        }
        this.mWebProgressView.startProgress();
    }

    @Override // io.dcloud.common.adapter.ui.AdaContainerFrameItem, io.dcloud.common.DHInterface.IContainerView
    public void addFrameItem(AdaFrameItem adaFrameItem) {
        super.addFrameItem(adaFrameItem);
    }

    public void addJsInterface(String str, String str2) {
        this.mWebViewImpl.addJavascriptInterface(str2, str);
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void addStateListener(IWebviewStateListener iWebviewStateListener) {
        if (this.mStateListeners == null) {
            this.mStateListeners = new ArrayList<>();
        }
        if (iWebviewStateListener != null) {
            this.mStateListeners.add(iWebviewStateListener);
        }
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void appendPreloadJsFile(String str) throws IOException {
        this.mPreloadJsFile.add(str);
        Logger.d("AdaWebview", "appendPreloadJsFile mPreloadJsFile=" + this.mPreloadJsFile + ";this=" + this);
        if (this.mPlusLoaded) {
            Log.d("AdaWebview", "appendPreloadJsFile---=" + str);
            String strLoadFileContent = loadFileContent(str, this.mFrameView.obtainApp().obtainRunningAppMode() == 1 ? 0 : 2);
            if (TextUtils.isEmpty(strLoadFileContent)) {
                return;
            }
            loadUrl(AbsoluteConst.PROTOCOL_JAVASCRIPT + strLoadFileContent + ";");
        }
    }

    public boolean backOrForward(int i) {
        return this.mWebViewImpl.canGoBackOrForward(i);
    }

    public boolean canGoBack() {
        boolean z = !this.justClearOption && this.mWebViewImpl.canGoBack();
        Logger.d("AdaFrameItem", "canGoBack" + this.mWebViewImpl.getUrlStr() + ";" + this.justClearOption + ";" + z);
        return z;
    }

    public boolean canGoForward() {
        return !this.justClearOption && this.mWebViewImpl.canGoForward();
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void checkIfNeedLoadOriginalUrl() {
        if (this.mLoading || this.mLoaded) {
            return;
        }
        loadUrl(getOriginalUrl());
    }

    public void checkInjectSitemap() {
        if (this.mNeedSitemapJson && this.mLoaded && this.mPreloadJsLoaded) {
            StringBuffer stringBuffer = new StringBuffer();
            File file = new File(BaseInfo.sBaseFsSitMapPath + File.separator + obtainApp().obtainAppId() + "_sitemap.json");
            if (file.exists()) {
                try {
                    stringBuffer.append(";window.__wap2app_sitemap=");
                    stringBuffer.append(IOUtil.toString(new FileInputStream(file)));
                    stringBuffer.append(";wap2app&wap2app.initSitemap();\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                executeScript(stringBuffer.toString());
                this.mNeedSitemapJson = false;
            }
        }
    }

    public boolean checkOverrideUrl(String str) {
        DCWebView dCWebView = this.mWebViewImpl;
        if (dCWebView == null) {
            return false;
        }
        return dCWebView.checkOverrideUrl(this.mOverrideUrlLoadingDataOptions, str);
    }

    public void checkPreLoadJsContent() {
        this.mWebViewImpl.onPreloadJSContent("checkPreLoadJsContent " + this);
    }

    public boolean checkResourceLoading(String str) {
        JSONObject jSONObject = this.mListenResourceLoadingOptions;
        if (jSONObject == null || !jSONObject.has("match")) {
            return true;
        }
        try {
            return Pattern.compile(this.mListenResourceLoadingOptions.optString("match")).matcher(str).matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public OverrideResourceRequestItem checkResourceRequestUrl(String str) {
        if (this.mOverrideResourceRequestOptions == null) {
            return null;
        }
        for (int i = 0; i < this.mOverrideResourceRequestOptions.length(); i++) {
            try {
                JSONObject jSONObjectOptJSONObject = this.mOverrideResourceRequestOptions.optJSONObject(i);
                String strOptString = jSONObjectOptJSONObject.optString("match", "");
                if (!TextUtils.isEmpty(strOptString) && Pattern.compile(strOptString).matcher(str).matches()) {
                    String strConvert2AbsFullPath = obtainApp().convert2AbsFullPath(jSONObjectOptJSONObject.optString("redirect"));
                    String strOptString2 = jSONObjectOptJSONObject.optString("mime", PdrUtil.getMimeType(strConvert2AbsFullPath));
                    String strOptString3 = jSONObjectOptJSONObject.optString("encoding", "utf-8");
                    JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject(WXBasicComponentType.HEADER);
                    OverrideResourceRequestItem overrideResourceRequestItem = new OverrideResourceRequestItem();
                    overrideResourceRequestItem.redirect = strConvert2AbsFullPath;
                    overrideResourceRequestItem.encoding = strOptString3;
                    overrideResourceRequestItem.mime = strOptString2;
                    overrideResourceRequestItem.headerJson = jSONObjectOptJSONObject2;
                    return overrideResourceRequestItem;
                }
            } catch (Exception e) {
                Logger.e("AdaWebview", "checkResourceRequestUrl e==" + e.getMessage());
                return null;
            }
        }
        return null;
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public boolean checkWhite(String str) {
        DCWebView dCWebView = this.mWebViewImpl;
        if (dCWebView != null) {
            return dCWebView.checkWhite(str);
        }
        return false;
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void clearHistory() {
        if (this.mWebViewImpl == null) {
            return;
        }
        Logger.d("AdaFrameItem", "clearHistory url=" + this.mWebViewImpl.getUrlStr());
        this.justClearOption = true;
        this.mWebViewImpl.loadData("<html><head><meta charset=\"utf-8\"></head><body></body><html>", "text/html", "utf-8");
        this.mWebViewImpl.setUrlStr("");
    }

    public void dispatchWebviewStateEvent(int i, Object obj) throws NumberFormatException {
        if (i != 1) {
            if (i == 3) {
                int i2 = Integer.parseInt(String.valueOf(obj));
                this.mProgress = i2;
                if (!this.isStart && i2 < 100) {
                    startWebProgress();
                    this.isStart = true;
                }
                if (this.mProgress >= 100 && this.isStart) {
                    this.isStart = false;
                    DCWebViewProgressBar dCWebViewProgressBar = this.mWebProgressView;
                    if (dCWebViewProgressBar != null) {
                        dCWebViewProgressBar.finishProgress();
                    }
                }
            }
        } else if (this.isStart) {
            this.isStart = false;
            DCWebViewProgressBar dCWebViewProgressBar2 = this.mWebProgressView;
            if (dCWebViewProgressBar2 != null) {
                dCWebViewProgressBar2.finishProgress();
            }
        }
        ArrayList<IWebviewStateListener> arrayList = this.mStateListeners;
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                this.mStateListeners.get(size).onCallBack(i, obj);
            }
        }
    }

    @Override // io.dcloud.common.adapter.ui.AdaContainerFrameItem, io.dcloud.common.adapter.ui.AdaFrameItem
    public void dispose() {
        super.dispose();
        if (this.isDisposed) {
            return;
        }
        this.isDisposed = true;
        this.mProgressView = null;
        if (this.mFrameView.getFrameType() == 5 && !this.mLoaded && this.mProgressIntValue >= 50) {
            this.mFrameView.obtainApp().checkOrLoadlaunchWebview();
        }
        BaseInfo.s_Webview_Count--;
        try {
            DCWebView dCWebView = this.mWebViewImpl;
            if (dCWebView != null) {
                dCWebView.stopLoading();
            }
        } catch (Exception unused) {
        }
        MessageHandler.sendMessage(new MessageHandler.IMessages() { // from class: io.dcloud.common.adapter.ui.AdaWebview.3
            @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
            public void execute(Object obj) {
                try {
                    AdaWebview adaWebview = AdaWebview.this;
                    if (adaWebview.mFrameView != null) {
                        adaWebview.mFrameView = null;
                    }
                    DCWebView dCWebView2 = adaWebview.mWebViewImpl;
                    if (dCWebView2 != null) {
                        dCWebView2.clearCache(false);
                        if (AdaWebview.this.mWebViewImpl.getWebView().getParent() != null) {
                            ((ViewGroup) AdaWebview.this.mWebViewImpl.getWebView().getParent()).removeView(AdaWebview.this.mWebViewImpl.getWebView());
                        }
                        AdaWebview.this.mWebViewImpl.destroyWeb();
                        AdaWebview.this.releaseConfigCallback();
                        AdaWebview.this.mWebViewImpl = null;
                    }
                    AdaWebview adaWebview2 = AdaWebview.this;
                    adaWebview2.mJsInterfaces = null;
                    adaWebview2.mMesssageListener = null;
                    adaWebview2.executeScriptListener = null;
                    adaWebview2.mWebViewParent = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (AdaWebview.this.mStateListeners != null) {
                    AdaWebview.this.mStateListeners.clear();
                }
                AdaWebview.this.mStateListeners = null;
                System.gc();
            }
        }, null);
    }

    public void endWebViewEvent(String str) {
        if (this.mWebViewParent == null) {
            return;
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.PULL_DOWN_REFRESH)) {
            this.mWebViewParent.endPullRefresh();
        } else if (PdrUtil.isEquals(str, AbsoluteConst.BOUNCE_REGISTER)) {
            this.mWebViewParent.resetBounce();
        }
    }

    public void evalJS(String str) {
        if (this.mPlusLoaded) {
            executeScript(str);
        } else {
            pushEvalJsOption(str);
        }
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void evalJSSync(String str, ICallBack iCallBack) {
        if (this.mWebViewImpl != null) {
            if (!str.startsWith(AbsoluteConst.PROTOCOL_JAVASCRIPT)) {
                str = AbsoluteConst.PROTOCOL_JAVASCRIPT + str;
            }
            this.mWebViewImpl.evalJSSync(str, iCallBack);
        }
    }

    public String execScript(String str, String str2, JSONArray jSONArray, boolean z) {
        if (!z) {
            return this.mJsInterfaces.exec(str, str2, jSONArray);
        }
        MessageHandler.IMessages iMessages = this.mMesssageListener;
        if (iMessages == null) {
            return null;
        }
        MessageHandler.sendMessage(iMessages, new Object[]{str, str2, jSONArray});
        return null;
    }

    public void executeScript(String str) {
        MessageHandler.IMessages iMessages;
        if (str == null || (iMessages = this.executeScriptListener) == null) {
            return;
        }
        MessageHandler.sendMessage(iMessages, str);
    }

    void execute_eval_js_stack() {
        if (this.mEvalJsOptionStack == null) {
            return;
        }
        Logger.d("adawebview", "webviewimp=" + this.mWebViewImpl + ";execute_eval_js_stack count=" + this.mEvalJsOptionStack.length);
        int i = 0;
        while (true) {
            String[] strArr = this.mEvalJsOptionStack;
            if (i >= strArr.length) {
                this.mEvalJsOptionStack = null;
                return;
            } else {
                executeScript(strArr[i]);
                i++;
            }
        }
    }

    public String getCookie(String str) {
        DCWebView dCWebView = this.mWebViewImpl;
        if (dCWebView != null) {
            return dCWebView.getCookie(str);
        }
        return null;
    }

    public String getCssString() {
        return this.mCssString;
    }

    public DCWebView getDCWebView() {
        return this.mWebViewImpl;
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public int getFixBottom() {
        return this.mFixBottomHeight;
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public Object getFlag() {
        return this.mFlag;
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public IWebview getOpener() {
        return this.mOpener;
    }

    public String getOriginalUrl() {
        return this.originalUrl;
    }

    public float getScale() {
        return this.mWebViewImpl.getScale();
    }

    public float getScaleOfOpenerWebview() {
        return getScale();
    }

    public String getScreenAndDisplayJson(IWebview iWebview) {
        float scale = iWebview.getScale();
        IApp iAppObtainApp = iWebview.obtainApp();
        int i = iAppObtainApp.getInt(2);
        int i2 = iAppObtainApp.getInt(0);
        int i3 = (int) (i2 / scale);
        return StringUtil.format("(function(p){p.screen.scale=%f;p.screen.resolutionHeight=%d;p.screen.resolutionWidth=%d;p.screen.height=%d;p.screen.width=%d;p.screen.dpiX=%f;p.screen.dpiY=%f;p.display.resolutionHeight=%d;p.display.resolutionWidth=%d;})(((window.__html5plus__&&__html5plus__.isReady)?__html5plus__:(navigator.plus&&navigator.plus.isReady)?navigator.plus:window.plus));", Float.valueOf(scale), Integer.valueOf((int) (i / scale)), Integer.valueOf(i3), Integer.valueOf(i), Integer.valueOf(i2), Float.valueOf(DeviceInfo.dpiX), Float.valueOf(DeviceInfo.dpiY), Integer.valueOf((int) (iAppObtainApp.getInt(1) / scale)), Integer.valueOf(i3));
    }

    public String getTitle() {
        return this.mWebViewImpl.getTitle();
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public String getWebviewANID() {
        return this.mWebviewANID;
    }

    protected AdaWebViewParent getWebviewParent() {
        return this.mWebViewParent;
    }

    public String getWebviewProperty(String str) {
        if ("getShareOptions".equals(str)) {
            return this.shareOptions;
        }
        if ("getFavoriteOptions".equals(str)) {
            return this.favoriteOptions;
        }
        if ("needTouchEvent".equals(str)) {
            return String.valueOf(this.needTouchEvent);
        }
        if (IWebview.USER_AGENT.equals(str)) {
            DCWebView dCWebView = this.mWebViewImpl;
            return dCWebView != null ? dCWebView.getUserAgentString() : sDefalutUserAgent;
        }
        if (AbsoluteConst.JSON_KEY_VIDEO_FULL_SCREEN.equals(str)) {
            return this.mVideoFullscreen;
        }
        if ("plusrequire".equals(str)) {
            return this.mPlusrequire;
        }
        if (!AbsoluteConst.JSON_KEY_SHAREABLE.equals(str)) {
            return null;
        }
        return this.mShareable + "";
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public final String getWebviewUUID() {
        return this.mWebviewUUID;
    }

    public String get_eval_js_stack() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.mEvalJsOptionStack != null) {
            int i = 0;
            while (true) {
                String[] strArr = this.mEvalJsOptionStack;
                if (i >= strArr.length) {
                    break;
                }
                String str = strArr[i];
                if (str.endsWith(";")) {
                    stringBuffer.append(str);
                } else {
                    stringBuffer.append(str).append(";");
                }
                i++;
            }
            this.mEvalJsOptionStack = null;
        }
        return stringBuffer.toString();
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void goBackOrForward(int i) {
        DCWebView dCWebView = this.mWebViewImpl;
        if (dCWebView != null) {
            dCWebView.goBackOrForward(i);
        }
    }

    public boolean hadClearHistory(String str) {
        return this.justClearOption && PdrUtil.isEquals(str, "data:text/html,<html><head><meta charset=\"utf-8\"></head><body></body><html>");
    }

    public boolean hasPreLoadJsFile() {
        return this.mPreloadJsFile.size() > 0;
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public boolean hasWebViewEvent(String str) {
        AdaWebViewParent adaWebViewParent;
        if (!PdrUtil.isEquals(str, AbsoluteConst.PULL_DOWN_REFRESH) || (adaWebViewParent = this.mWebViewParent) == null) {
            return false;
        }
        return adaWebViewParent.isSetPull2Refresh;
    }

    public void init() {
        DCWebView dCWebView = this.mWebViewImpl;
        if (dCWebView != null) {
            dCWebView.init();
        }
    }

    protected void initANID() {
        if (TextUtils.isEmpty(this.mWebviewANID)) {
            this.mWebviewANID = "AD_Webview" + System.currentTimeMillis();
        }
    }

    protected void initSitemapState() {
        this.mNeedSitemapJson = (BaseInfo.isWap2AppAppid(this.mAppid) && this.mFrameView.getFrameType() == 2) || this.mFrameView.getFrameType() == 4;
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public final void initWebviewUUID(String str) {
        this.mWebviewUUID = str;
    }

    @Override // io.dcloud.common.adapter.ui.AdaFrameItem
    public boolean isDisposed() {
        return this.isDisposed;
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public boolean isIWebViewFocusable() {
        return obtainWindowView().isFocusable();
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public boolean isLoaded() {
        return this.mLoaded;
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public boolean isPause() {
        return this.isPause;
    }

    public boolean isRealInject(String str) {
        return this.mPlusLoaded && TextUtils.equals(PdrUtil.getUrlPathName(str), PdrUtil.getUrlPathName(this.mInjectPlusLoadedUrl));
    }

    public boolean isUniService() {
        return false;
    }

    public boolean isUniWebView() {
        return false;
    }

    public void loadContentData(String str, String str2, String str3, String str4) {
        this.mWebViewImpl.loadDataWithBaseURL(str, str2, str3, str4, str);
    }

    public boolean loadCssFile() throws IOException {
        if (PdrUtil.isEmpty(this.mCssString)) {
            return false;
        }
        String strReplaceAll = this.mCssString.replaceAll(JSUtil.QUOTE, "'");
        this.mCssString = strReplaceAll;
        loadUrl("javascript:var container = document.getElementsByTagName('head')[0];\nvar addStyle = document.createElement('style');\naddStyle.rel = 'stylesheet';\naddStyle.type = 'text/css';\naddStyle.innerHTML = " + checkRedCssline(strReplaceAll) + ";\ncontainer.appendChild(addStyle);\nfirstNode = container.children[0];\n    container.appendChild(addStyle);\n");
        return true;
    }

    void loadFileContent(IFExecutePreloadJSContentCallBack iFExecutePreloadJSContentCallBack) {
        if (PdrUtil.isEmpty(this.mPreloadJsFile)) {
            return;
        }
        try {
            IApp iAppObtainApp = this.mFrameView.obtainApp();
            if (iAppObtainApp != null) {
                int i = 0;
                int i2 = iAppObtainApp.obtainRunningAppMode() == 1 ? 0 : 2;
                ArrayList<String> arrayList = this.mPreloadJsFile;
                int size = arrayList.size();
                while (i < size) {
                    String str = arrayList.get(i);
                    i++;
                    String str2 = str;
                    String strWrapAppendJsFile = wrapAppendJsFile(str2, i2);
                    if (!TextUtils.isEmpty(strWrapAppendJsFile)) {
                        String str3 = AbsoluteConst.PROTOCOL_JAVASCRIPT + strWrapAppendJsFile + ";";
                        if (iFExecutePreloadJSContentCallBack == null) {
                            loadUrl(str3);
                        } else {
                            iFExecutePreloadJSContentCallBack.callback(this.mWebViewImpl.convertRelPath(str2), str3);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadForceAHeadJs() throws IOException {
        if (PdrUtil.isEmpty(this.mFrameView.obtainApp()) || this.mForceAHeadJsFileLoaded || TextUtils.isEmpty(this.mForceAHeadJsFile)) {
            return;
        }
        String strLoadFileContent = loadFileContent(this.mForceAHeadJsFile, this.mFrameView.obtainApp().obtainRunningAppMode() == 1 ? 0 : 2);
        if (TextUtils.isEmpty(strLoadFileContent)) {
            return;
        }
        loadUrl(AbsoluteConst.PROTOCOL_JAVASCRIPT + strLoadFileContent + ";");
        this.mForceAHeadJsFileLoaded = true;
    }

    public void loadUrl(String str) {
        DCWebView dCWebView = this.mWebViewImpl;
        if (dCWebView != null && PdrUtil.isEmpty(dCWebView.getUrlStr()) && str != null && !str.startsWith(AbsoluteConst.PROTOCOL_JAVASCRIPT)) {
            this.mWebViewImpl.setUrlStr(str);
            this.hasErrorPage = false;
            this.errorPageUrl = null;
        }
        DCWebView dCWebView2 = this.mWebViewImpl;
        if (dCWebView2 != null) {
            dCWebView2.loadUrl(str);
        }
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public String obtainFrameId() {
        return this.mFrameId;
    }

    public IFrameView obtainFrameView() {
        return this.mFrameView;
    }

    public String obtainFullUrl() {
        return !TextUtils.isEmpty(this.mWebViewImpl.getUrlStr()) ? this.mWebViewImpl.getUrlStr() : this.mWebViewImpl.getUrl();
    }

    public String obtainPageTitle() {
        try {
            if (Looper.myLooper() != null && Looper.getMainLooper() == Looper.myLooper()) {
                String title = this.mWebViewImpl.getTitle();
                return TextUtils.isEmpty(title) ? this.mWebViewImpl.getPageTitle() : title;
            }
        } catch (Exception e) {
            Logger.e("AdaWebview", "e.getMessage()==" + e.getMessage());
        }
        return this.mWebViewImpl.getPageTitle();
    }

    public String obtainUrl() {
        DCWebView dCWebView = this.mWebViewImpl;
        if (dCWebView == null) {
            return "";
        }
        if (dCWebView.getUrlStr() == null) {
            return this.mWebViewImpl.getUrl();
        }
        int iIndexOf = this.mWebViewImpl.getUrlStr().indexOf(this.mWebViewImpl.getBaseUrl());
        String urlStr = this.mWebViewImpl.getUrlStr();
        return iIndexOf >= 0 ? urlStr.substring(this.mWebViewImpl.getBaseUrl().length()) : urlStr;
    }

    public WebView obtainWebview() {
        if (this.mWebViewImpl.getWebView() instanceof WebView) {
            return (WebView) this.mWebViewImpl.getWebView();
        }
        return null;
    }

    public ViewGroup obtainWindowView() {
        return this.mWebViewImpl.getWebView();
    }

    @Override // io.dcloud.common.adapter.ui.AdaContainerFrameItem, io.dcloud.common.adapter.ui.AdaFrameItem
    public boolean onDispose() {
        View view = this.mProgressView;
        if (view != null && view.getParent() != null) {
            ((ViewGroup) this.mProgressView.getParent()).removeView(this.mProgressView);
        }
        return super.onDispose();
    }

    public void onPageStarted() {
        this.mLoading = true;
        DCWebView dCWebView = this.mWebViewImpl;
        if (dCWebView != null) {
            dCWebView.onPageStarted();
        }
        try {
            if (this.mFrameView.getFrameType() == 5 && TextUtils.equals(this.mFrameView.obtainApp().obtainWebAppIntent().getStringExtra(IntentConst.DIRECT_PAGE), obtainUrl())) {
                obtainWindowView().postDelayed(new Runnable() { // from class: io.dcloud.common.adapter.ui.AdaWebview.6
                    @Override // java.lang.Runnable
                    public void run() {
                        AdaWebview adaWebview = AdaWebview.this;
                        if (adaWebview.mLoaded || adaWebview.mFrameView.obtainApp() == null) {
                            return;
                        }
                        AdaWebview.this.mFrameView.obtainApp().checkOrLoadlaunchWebview();
                    }
                }, 6000L);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void onRootViewGlobalLayout(View view) {
        try {
            IApp iAppObtainApp = obtainApp();
            if (iAppObtainApp != null) {
                iAppObtainApp.obtainWebAppRootView().onRootViewGlobalLayout(view);
            }
        } catch (Exception unused) {
        }
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void pause() {
        if (this.mWebViewImpl != null) {
            this.isPause = true;
            obtainMainView().post(new Runnable() { // from class: io.dcloud.common.adapter.ui.AdaWebview.1
                @Override // java.lang.Runnable
                public void run() {
                    AdaWebview.this.mWebViewImpl.onPause();
                }
            });
            AdaFrameView adaFrameView = this.mFrameView;
            if (adaFrameView != null) {
                adaFrameView.dispatchFrameViewEvents(AbsoluteConst.EVENTS_WEBVIEW_PAUSE, this);
            }
        }
    }

    public void reload() {
        if (PdrUtil.isEmpty(this.mWebViewImpl.getUrlStr())) {
            return;
        }
        removeAllFrameItem();
        try {
            if (BaseInfo.ISDEBUG) {
                this.mWebViewImpl.clearCache(false);
            }
        } catch (Exception unused) {
        }
        this.mWebViewImpl.setDidTouch(false);
        this.mWebViewImpl.reload();
        StringBuilder sb = new StringBuilder("reload url=");
        DCWebView dCWebView = this.mWebViewImpl;
        sb.append(dCWebView.convertRelPath(dCWebView.getUrlStr()));
        Logger.d("webview", sb.toString());
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void removeAllCookie() {
        DCWebView dCWebView = this.mWebViewImpl;
        if (dCWebView != null) {
            dCWebView.removeAllCookie();
            return;
        }
        CookieHandler cookieHandler = CookieHandler.getDefault();
        if (cookieHandler instanceof WebkitCookieManagerProxy) {
            ((WebkitCookieManagerProxy) cookieHandler).removeAllCookie();
        }
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void removeSessionCookie() {
        DCWebView dCWebView = this.mWebViewImpl;
        if (dCWebView != null) {
            dCWebView.removeSessionCookie();
            return;
        }
        CookieHandler cookieHandler = CookieHandler.getDefault();
        if (cookieHandler instanceof WebkitCookieManagerProxy) {
            ((WebkitCookieManagerProxy) cookieHandler).removeSessionCookie();
        }
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void removeStateListener(IWebviewStateListener iWebviewStateListener) {
        ArrayList<IWebviewStateListener> arrayList = this.mStateListeners;
        if (arrayList != null) {
            arrayList.remove(iWebviewStateListener);
        }
    }

    public void resetPlusLoadSaveData() {
        this.mPlusLoaded = false;
        this.mPlusLoading = false;
        this.mPreloadJsLoaded = false;
        this.mPreloadJsLoading = false;
        this.mInjectPlusWidthJs = null;
        this.mLoaded = false;
        this.mIsAdvanceCss = false;
        this.mInjectGeoLoaded = false;
        this.mForceAHeadJsFileLoaded = false;
        this.mInjectPlusLoadedUrl = null;
        initSitemapState();
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void resume() {
        if (this.mWebViewImpl != null) {
            this.isPause = false;
            obtainMainView().post(new Runnable() { // from class: io.dcloud.common.adapter.ui.AdaWebview.2
                @Override // java.lang.Runnable
                public void run() {
                    AdaWebview.this.mWebViewImpl.onResume();
                }
            });
            AdaFrameView adaFrameView = this.mFrameView;
            if (adaFrameView != null) {
                adaFrameView.dispatchFrameViewEvents(AbsoluteConst.EVENTS_WEBVIEW_RESUME, this);
            }
        }
    }

    public void saveWebViewData(String str) {
        DCWebView dCWebView;
        if (!this.mPlusLoading || (dCWebView = this.mWebViewImpl) == null) {
            return;
        }
        if (TextUtils.isEmpty(dCWebView.getUrlStr())) {
            this.mWebViewImpl.setUrlStr(str);
        } else if (!TextUtils.isEmpty(str) && !TextUtils.equals(str, "about:blank")) {
            this.mWebViewImpl.setUrlStr(str);
        }
        Logger.i("AdaFrameItem", "saveWebViewData url=" + str);
        this.mPlusLoaded = true;
        this.mInjectPlusLoadedUrl = this.mWebViewImpl.getUrlStr();
        this.mPreloadJsLoaded = this.mPreloadJsLoading;
        this.mWebViewImpl.onUpdatePlusData("saveWebViewData");
        this.mWebViewImpl.listenPageFinishTimeout("saveWebViewData");
        if (this.mFrameView.getCircleRefreshView() == null || !this.mFrameView.getCircleRefreshView().isRefreshing()) {
            return;
        }
        this.mFrameView.getCircleRefreshView().endRefresh();
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0028  */
    @Override // io.dcloud.common.DHInterface.IWebview
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void setAssistantType(java.lang.String r4) {
        /*
            r3 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "setAssistantType type="
            r0.<init>(r1)
            r0.append(r4)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "AssistantInput"
            io.dcloud.common.adapter.util.Logger.e(r1, r0)
            int r4 = io.dcloud.common.adapter.ui.RecordView.Utils.convertInt(r4)
            io.dcloud.common.adapter.ui.CustomeizedInputConnection.sDefaultInputType = r4
            io.dcloud.common.adapter.ui.CustomeizedInputConnection r0 = io.dcloud.common.adapter.ui.AdaWebview.sCustomeizedInputConnection
            if (r0 == 0) goto L28
            int r0 = r0.mInputType
            if (r0 == r4) goto L28
            r1 = 1
            if (r0 == r1) goto L28
            r2 = 2
            if (r0 == r2) goto L28
            goto L29
        L28:
            r1 = 0
        L29:
            io.dcloud.common.adapter.ui.RecordView r0 = io.dcloud.common.adapter.ui.AdaWebview.mRecordView
            if (r0 == 0) goto L3a
            if (r1 == 0) goto L3a
            boolean r0 = io.dcloud.common.adapter.util.AndroidResources.sIMEAlive
            if (r0 == 0) goto L3a
            io.dcloud.common.adapter.ui.RecordView r0 = io.dcloud.common.adapter.ui.AdaWebview.mRecordView
            int r1 = r0.mAnchorY
            r0.update(r1, r4)
        L3a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.adapter.ui.AdaWebview.setAssistantType(java.lang.String):void");
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void setCookie(String str, String str2) throws IOException {
        DCWebView dCWebView = this.mWebViewImpl;
        if (dCWebView != null) {
            dCWebView.setCookie(str, str2);
            return;
        }
        try {
            CookieHandler cookieHandler = CookieHandler.getDefault();
            if (cookieHandler != null) {
                HashMap map = new HashMap();
                ArrayList arrayList = new ArrayList();
                arrayList.add(str2);
                map.put(IWebview.SET_COOKIE, arrayList);
                cookieHandler.put(URI.create(str), map);
            }
        } catch (Exception unused) {
        }
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void setCssFile(String str, String str2) {
        if (PdrUtil.isEmpty(str)) {
            this.mCssString = str2;
        } else {
            this.mCssString = loadFileContent(str, this.mFrameView.obtainApp().obtainRunningAppMode() == 1 ? 0 : 2);
        }
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void setFixBottom(int i) {
        this.mFixBottomHeight = i;
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void setFlag(Object obj) {
        this.mFlag = obj;
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void setFrameId(String str) {
        this.mFrameId = str;
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void setIWebViewFocusable(boolean z) {
        ViewGroup viewGroupObtainWindowView = obtainWindowView();
        AdaFrameView adaFrameView = this.mFrameView;
        if (adaFrameView != null) {
            AbsMgr absMgrObtainWindowMgr = adaFrameView.obtainWindowMgr();
            IWebview iWebviewObtainWebView = this.mFrameView.obtainWebView();
            AdaFrameView adaFrameView2 = this.mFrameView;
            Object titleNView = TitleNViewUtil.getTitleNView(absMgrObtainWindowMgr, iWebviewObtainWebView, adaFrameView2, TitleNViewUtil.getTitleNViewId(adaFrameView2));
            if (titleNView instanceof ITitleNView) {
                ((ITitleNView) titleNView).setTitleNViewFocusable(z);
            }
        }
        if (viewGroupObtainWindowView != null) {
            boolean zIsFocusable = viewGroupObtainWindowView.isFocusable();
            if (z && !zIsFocusable) {
                viewGroupObtainWindowView.setFocusable(true);
                viewGroupObtainWindowView.setFocusableInTouchMode(true);
            } else {
                if (z || !zIsFocusable) {
                    return;
                }
                viewGroupObtainWindowView.setFocusable(false);
                viewGroupObtainWindowView.setFocusableInTouchMode(false);
            }
        }
    }

    public void setJsInterface(IJsInterface iJsInterface) {
        if (this.mJsInterfaces == null) {
            this.mJsInterfaces = iJsInterface;
        }
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void setListenResourceLoading(JSONObject jSONObject) {
        this.mListenResourceLoadingOptions = jSONObject;
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void setLoadURLHeads(String str, HashMap<String, String> map) {
        DCWebView dCWebView = this.mWebViewImpl;
        if (dCWebView != null) {
            dCWebView.putHeads(str, map);
        }
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void setOpener(IWebview iWebview) {
        this.mOpener = iWebview;
    }

    public void setOriginalUrl(String str) {
        this.originalUrl = str;
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void setOverrideResourceRequest(JSONArray jSONArray) {
        this.mOverrideResourceRequestOptions = jSONArray;
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void setOverrideUrlLoadingData(JSONObject jSONObject) {
        this.mOverrideUrlLoadingDataOptions = jSONObject;
        Logger.d("AdaFrameItem", "setOverrideUrlLoadingData=" + jSONObject);
        if (this.mFrameView.getFrameType() == 2 || this.mFrameView.getFrameType() == 5) {
            this.mFrameView.obtainApp().setConfigProperty("wap2app_running_mode", AbsoluteConst.FALSE);
            DCWebView dCWebView = this.mWebViewImpl;
            if (dCWebView != null) {
                dCWebView.closeWap2AppBlockDialog(true);
            }
        }
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void setPreloadJsFile(String str, boolean z) throws IOException {
        if (z) {
            this.mForceAHeadJsFileLoaded = false;
            this.mForceAHeadJsFile = str;
            if (this.mPlusLoaded) {
                loadForceAHeadJs();
                return;
            }
            return;
        }
        this.mPreloadJsFile.clear();
        this.mPreloadJsFile.add(str);
        Logger.d("AdaWebview", "setPreloadJsFile mPreloadJsFile=" + this.mPreloadJsFile);
        if (this.mPlusLoaded) {
            Log.d("AdaWebview", "setPreloadJsFile---=" + str);
            loadFileContent(null);
        }
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void setProgressView(View view) {
        this.mProgressView = view;
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void setScrollIndicator(String str) {
        if (this.mWebViewImpl != null) {
            if (PdrUtil.isEquals(str, "none")) {
                this.mWebViewImpl.setHorizontalScrollBarEnabled(false);
                this.mWebViewImpl.setVerticalScrollBarEnabled(false);
            } else if (PdrUtil.isEquals(str, "vertical")) {
                this.mWebViewImpl.setHorizontalScrollBarEnabled(false);
                this.mWebViewImpl.setVerticalScrollBarEnabled(true);
            } else if (PdrUtil.isEquals(str, Constants.Value.HORIZONTAL)) {
                this.mWebViewImpl.setHorizontalScrollBarEnabled(true);
                this.mWebViewImpl.setVerticalScrollBarEnabled(false);
            } else {
                this.mWebViewImpl.setHorizontalScrollBarEnabled(true);
                this.mWebViewImpl.setVerticalScrollBarEnabled(true);
            }
        }
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void setWebViewCacheMode(String str) {
        DCWebView dCWebView = this.mWebViewImpl;
        if (dCWebView != null) {
            dCWebView.setWebViewCacheMode(str);
        }
    }

    public void setWebViewEvent(String str, Object obj) {
        if (this.mWebViewParent == null) {
            return;
        }
        if (PdrUtil.isEquals(str, AbsoluteConst.PULL_DOWN_REFRESH)) {
            this.mWebViewParent.parsePullToReFresh((JSONObject) obj);
        } else if (PdrUtil.isEquals(str, AbsoluteConst.PULL_REFRESH_BEGIN)) {
            this.mWebViewParent.beginPullRefresh();
        } else if (PdrUtil.isEquals(str, AbsoluteConst.BOUNCE_REGISTER)) {
            this.mWebViewParent.parseBounce((JSONObject) obj);
        }
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void setWebviewProperty(String str, String str2) throws JSONException {
        if ("setShareOptions".equals(str)) {
            if (TextUtils.isEmpty(str2)) {
                this.shareOptions = "";
                return;
            }
            this.shareOptions = str2;
            try {
                JSONObject jSONObject = new JSONObject(this.shareOptions);
                if (jSONObject.has(AbsoluteConst.JSON_KEY_ICON)) {
                    String string = jSONObject.getString(AbsoluteConst.JSON_KEY_ICON);
                    if (TextUtils.isEmpty(string)) {
                        return;
                    }
                    ImageLoaderL.getInstance().loadImageSync(string);
                    return;
                }
                return;
            } catch (Exception unused) {
                return;
            }
        }
        if ("setFavoriteOptions".equals(str)) {
            if (TextUtils.isEmpty(str2)) {
                this.favoriteOptions = "";
                return;
            } else {
                this.favoriteOptions = str2;
                return;
            }
        }
        if ("needTouchEvent".equals(str)) {
            if (TextUtils.isEmpty(str2)) {
                this.needTouchEvent = "";
                return;
            } else {
                this.needTouchEvent = str2;
                return;
            }
        }
        if (AbsoluteConst.JSON_KEY_SCALABLE.equals(str)) {
            if (this.mWebViewImpl != null) {
                this.mWebViewImpl.initScalable(PdrUtil.parseBoolean(str2, this.mFrameView.obtainFrameOptions().scalable, false));
                return;
            }
            return;
        }
        if (IWebview.USER_AGENT.equals(str)) {
            if (this.mWebViewImpl != null) {
                if (Boolean.parseBoolean(this.mFrameView.obtainApp().obtainConfigProperty(IApp.ConfigProperty.CONFIG_H5PLUS)) && str2.indexOf(" Html5Plus/") < 0) {
                    str2 = str2 + DCWebView.UserAgentExtInfo;
                }
                sCustomUserAgent = str2;
                this.mWebViewImpl.getWebView().post(new Runnable() { // from class: io.dcloud.common.adapter.ui.AdaWebview.7
                    @Override // java.lang.Runnable
                    public void run() {
                        AdaWebview.this.mWebViewImpl.setUserAgentString(AdaWebview.sCustomUserAgent);
                    }
                });
                return;
            }
            return;
        }
        if (AbsoluteConst.JSON_KEY_BLOCK_NETWORK_IMAGE.equals(str)) {
            if (this.mWebViewImpl != null) {
                this.mWebViewImpl.setBlockNetworkImage(PdrUtil.parseBoolean(str2, false, false));
                return;
            }
            return;
        }
        if ("injection".equals(str)) {
            this.mNeedInjection = PdrUtil.parseBoolean(str2, true, false);
            return;
        }
        if ("bounce".equals(str)) {
            if (this.mWebViewImpl == null || DeviceInfo.sDeviceSdkVer < 9) {
                return;
            }
            JSONObject jSONObject2 = this.mFrameView.obtainFrameOptions().titleNView;
            if (("vertical".equalsIgnoreCase(str2) || Constants.Value.HORIZONTAL.equalsIgnoreCase(str2) || "all".equalsIgnoreCase(str2)) && (jSONObject2 == null || !"transparent".equals(jSONObject2.optString("type")))) {
                this.mWebViewImpl.getWebView().setOverScrollMode(0);
                return;
            } else {
                this.mWebViewImpl.getWebView().setOverScrollMode(2);
                return;
            }
        }
        if (AbsoluteConst.JSON_KEY_VIDEO_FULL_SCREEN.equals(str)) {
            if (TextUtils.isEmpty(str2)) {
                return;
            }
            this.mVideoFullscreen = str2;
        } else if ("plusrequire".equals(str)) {
            if (TextUtils.isEmpty(str2)) {
                return;
            }
            this.mPlusrequire = str2;
        } else if ("geolocation".equals(str)) {
            if (TextUtils.isEmpty(str2)) {
                return;
            }
            this.mInjectGEO = str2;
        } else {
            if (!AbsoluteConst.JSON_KEY_SHAREABLE.equals(str) || TextUtils.isEmpty(str2)) {
                return;
            }
            this.mShareable = PdrUtil.parseBoolean(str2, true, false);
        }
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void setWebviewclientListener(IDCloudWebviewClientListener iDCloudWebviewClientListener) {
        this.mWebViewImpl.setDcloudwebviewclientListener(iDCloudWebviewClientListener);
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void stopLoading() {
        DCWebView dCWebView = this.mWebViewImpl;
        if (dCWebView != null) {
            dCWebView.stopLoading();
        }
    }

    public String toString() {
        try {
            StringBuilder sb = new StringBuilder("<UUID=");
            sb.append(this.mWebviewUUID);
            sb.append(">;");
            sb.append(obtainMainView() != null ? obtainMainView().toString() : "view = null");
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return super.toString();
        }
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public boolean unReceiveTitle() {
        return this.unReceiveTitle;
    }

    String wrapAppendJsFile(String str, int i) {
        if (!BaseInfo.isWap2AppAppid(this.mAppid) || str.endsWith("wap2app.js") || (!BaseInfo.SyncDebug && !BaseInfo.isBase(getContext()))) {
            return loadFileContent(str, i);
        }
        if (str.startsWith(DeviceInfo.FILE_PROTOCOL)) {
            str = str.substring(7);
        }
        if (PdrUtil.isNetPath(this.mWebViewImpl.getUrlStr())) {
            str = "/h5pscript://" + str;
        } else if (!str.startsWith("h5pscript://")) {
            str = "h5pscript://" + str;
        }
        return "javascript:(function(){var container = document.getElementsByTagName('head')[0];\nvar script = document.createElement('script');\nscript.type = 'text/javascript';\nscript.src = '" + str + "';\ncontainer.appendChild(script);\nfirstNode = container.children[0];\nif(firstNode == null || firstNode==undefined)\n{    container.appendChild(script);}\nelse{\n\tcontainer.insertBefore(script,container.children[0]);\n}})();";
    }

    public void addJsInterface(String str, Object obj) {
        this.mWebViewImpl.addJavascriptInterface(obj, str);
    }

    public String getAppName() {
        AdaFrameView adaFrameView = this.mFrameView;
        return (adaFrameView == null || adaFrameView.obtainApp() == null) ? "" : this.mFrameView.obtainApp().obtainAppName();
    }

    public String getPreLoadJsString() {
        IApp iAppObtainApp;
        if (PdrUtil.isEmpty(this.mPreloadJsFile) || (iAppObtainApp = this.mFrameView.obtainApp()) == null || this.mPreloadJsFile.size() <= 0) {
            return "";
        }
        int i = 0;
        int i2 = iAppObtainApp.obtainRunningAppMode() == 1 ? 0 : 2;
        ArrayList<String> arrayList = this.mPreloadJsFile;
        int size = arrayList.size();
        String str = ";";
        while (i < size) {
            String str2 = arrayList.get(i);
            i++;
            String str3 = str2;
            if (!this.mPlusrequire.equals("none") || (!str3.contains("__wap2app.js") && !str3.contains("__wap2appconfig.js"))) {
                String strWrapAppendJsFile = wrapAppendJsFile(str3, i2);
                if (!TextUtils.isEmpty(strWrapAppendJsFile)) {
                    str = str + strWrapAppendJsFile + "\n";
                }
            }
        }
        return str + "\n";
    }

    public String syncUpdateWebViewData(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        String webviewUUID = getWebviewUUID();
        if (PdrUtil.isEmpty(webviewUUID)) {
            webviewUUID = String.valueOf(this.mFrameView.hashCode());
        }
        stringBuffer.append("window.__HtMl_Id__= '" + webviewUUID + "';");
        if (PdrUtil.isEmpty(obtainFrameId())) {
            stringBuffer.append("window.__WebVieW_Id__= undefined;");
        } else {
            stringBuffer.append("window.__WebVieW_Id__= '" + obtainFrameId() + "';");
        }
        Logger.e("WebViewData", "syncUpdateWebViewData url=" + this.mRecordLastUrl);
        stringBuffer.append("try{window.plus.__tag__='" + this.mPlusInjectTag + "';location.__plusready__=true;/*console.log(location);window.plus.__url__='" + str + "';*/}catch(e){console.log(e)}");
        return AbsoluteConst.PROTOCOL_JAVASCRIPT + stringBuffer.toString();
    }

    public void addJsInterface(String str, IJsInterface iJsInterface) {
        this.mWebViewImpl.addJavascriptInterface(iJsInterface, str);
        setJsInterface(iJsInterface);
    }

    public void evalJS(String str, ReceiveJSValue.ReceiveJSValueCallback receiveJSValueCallback) {
        if (receiveJSValueCallback != null) {
            str = ReceiveJSValue.registerCallback(str, receiveJSValueCallback);
        }
        evalJS(str);
    }

    public void reload(String str) {
        Logger.d("webview", "reload loadUrl url=" + str);
        this.mLoaded = false;
        this.mWebViewImpl.setUrlStr(str);
        this.mWebViewImpl.loadUrl(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseConfigCallback() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        try {
            Field declaredField = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
            if (declaredField != null) {
                declaredField.setAccessible(true);
                declaredField.set(null, null);
            }
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException unused) {
        }
    }

    public void reload(boolean z) {
        DCWebView dCWebView = this.mWebViewImpl;
        if (dCWebView != null) {
            dCWebView.webReload(z);
        }
        reload();
    }

    private String loadFileContent(String str, int i) throws IOException {
        byte[] fileContent;
        StringBuffer stringBuffer = new StringBuffer();
        InputStream encryptionInputStream = WebResUtil.getEncryptionInputStream(str, this.mFrameView.obtainApp());
        try {
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(encryptionInputStream);
        }
        if (encryptionInputStream != null) {
            stringBuffer.append(IOUtil.toString(encryptionInputStream));
        } else {
            str = this.mFrameView.obtainApp().convert2AbsFullPath(obtainFullUrl(), str);
            try {
                if (DHFile.isExist(str) && (fileContent = PlatformUtil.getFileContent(str, i)) != null) {
                    stringBuffer.append(new String(fileContent));
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        if (this.mNeedSitemapJson && str.endsWith("__wap2app.js")) {
            File file = new File(BaseInfo.sBaseFsSitMapPath + File.separator + obtainApp().obtainAppId() + "_sitemap.json");
            if (file.exists()) {
                try {
                    stringBuffer.insert(0, IOUtil.toString(new FileInputStream(file)) + ";\n");
                    stringBuffer.insert(0, ";window.__wap2app_sitemap=");
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                this.mNeedSitemapJson = false;
            }
        }
        return stringBuffer.toString();
    }

    protected AdaWebview(Context context, AdaFrameView adaFrameView) {
        super(context);
        this.unReceiveTitle = true;
        this.mWebViewParent = null;
        this.mRecordLastUrl = null;
        this.mWebViewImpl = null;
        this.mFrameView = null;
        this.mLoaded = false;
        this.mLoadCompleted = false;
        this.mPreloadJsLoaded = false;
        this.mPreloadJsLoading = false;
        this.mPlusLoaded = false;
        this.mPlusLoading = false;
        this.mPlusInjectTag = "page_finished";
        this.mIsAdvanceCss = false;
        this.mNeedInjection = true;
        this.mEncoding = null;
        this.mWebviewUUID = null;
        this.mWebviewANID = null;
        this.mFrameId = null;
        this.mJsInterfaces = null;
        this.hasErrorPage = false;
        this.errorPageUrl = null;
        this.originalUrl = null;
        this.mVideoFullscreen = "auto";
        this.needTouchEvent = "";
        this.favoriteOptions = "";
        this.shareOptions = "";
        this.mShareable = true;
        this.mPlusrequire = "normal";
        this.mInjectGEO = "none";
        this.mInjectGeoLoaded = false;
        this.mProgressIntValue = 0;
        this.isDisposed = false;
        this.mReceiveJSValue_android42 = null;
        this.isPause = false;
        this.mFlag = null;
        this.mInjectPlusLoadedUrl = null;
        this.mEvalJsOptionStack = null;
        this.mForceAHeadJsFileLoaded = false;
        this.mForceAHeadJsFile = null;
        this.mPreloadJsFile = new ArrayList<>(2);
        this.mCssString = "";
        this.executeScriptListener = new MessageHandler.IMessages() { // from class: io.dcloud.common.adapter.ui.AdaWebview.4
            @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
            public void execute(Object obj) {
                String str = (String) obj;
                DCWebView dCWebView = AdaWebview.this.mWebViewImpl;
                if (dCWebView != null) {
                    if (!str.startsWith(AbsoluteConst.PROTOCOL_JAVASCRIPT)) {
                        str = AbsoluteConst.PROTOCOL_JAVASCRIPT + str;
                    }
                    dCWebView.loadUrl(str);
                }
            }
        };
        this.mMesssageListener = new MessageHandler.IMessages() { // from class: io.dcloud.common.adapter.ui.AdaWebview.5
            @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
            public void execute(Object obj) {
                Object[] objArr = (Object[]) obj;
                AdaWebview.this.mJsInterfaces.exec(String.valueOf(objArr[0]), String.valueOf(objArr[1]), (JSONArray) objArr[2]);
            }
        };
        this.mStateListeners = null;
        this.mProgress = 0;
        this.isStart = false;
        this.justClearOption = false;
        this.mLoading = false;
        this.mNeedSitemapJson = false;
        this.mOverrideResourceRequestOptions = null;
        this.mOverrideUrlLoadingDataOptions = null;
        this.mListenResourceLoadingOptions = null;
        initANID();
        this.mFrameView = adaFrameView;
        this.mAppid = adaFrameView.obtainApp().obtainAppId();
        initSitemapState();
        Logger.d("AdaWebview");
        try {
            this.mWebViewImpl = WebViewFactory.getWebView(getActivity(), this);
        } catch (Exception e) {
            e.printStackTrace();
            this.mWebViewImpl = WebViewFactory.getWebView(getActivity(), this);
        }
        setMainView(this.mWebViewImpl.getWebView());
        AdaWebViewParent adaWebViewParent = new AdaWebViewParent(context);
        this.mWebViewParent = adaWebViewParent;
        adaWebViewParent.fillsWithWebview(this);
        if (adaFrameView.getFrameType() == 2) {
            this.mOverrideUrlLoadingDataOptions = this.mFrameView.obtainApp().obtainThridInfo(IApp.ConfigProperty.ThridInfo.OverrideUrlJsonData);
            JSONObject jSONObjectObtainThridInfo = this.mFrameView.obtainApp().obtainThridInfo(IApp.ConfigProperty.ThridInfo.OverrideResourceJsonData);
            if (jSONObjectObtainThridInfo != null) {
                this.mOverrideResourceRequestOptions = jSONObjectObtainThridInfo.optJSONArray(WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
            }
            this.mNeedInjection = PdrUtil.parseBoolean(this.mFrameView.obtainApp().obtainConfigProperty("injection"), this.mNeedInjection, false);
            String strObtainConfigProperty = this.mFrameView.obtainApp().obtainConfigProperty(IApp.ConfigProperty.CONFIG_LPLUSERQUIRE);
            if (!TextUtils.isEmpty(strObtainConfigProperty)) {
                this.mPlusrequire = strObtainConfigProperty;
            }
            String strObtainConfigProperty2 = this.mFrameView.obtainApp().obtainConfigProperty(IApp.ConfigProperty.CONFIG_LGEOLOCATION);
            if (TextUtils.isEmpty(strObtainConfigProperty2)) {
                return;
            }
            this.mInjectGEO = strObtainConfigProperty2;
            return;
        }
        if (adaFrameView.getFrameType() == 4) {
            String strObtainConfigProperty3 = this.mFrameView.obtainApp().obtainConfigProperty(IApp.ConfigProperty.CONFIG_SPLUSERQUIRE);
            if (!TextUtils.isEmpty(strObtainConfigProperty3)) {
                this.mPlusrequire = strObtainConfigProperty3;
            }
            String strObtainConfigProperty4 = this.mFrameView.obtainApp().obtainConfigProperty(IApp.ConfigProperty.CONFIG_SGEOLOCATION);
            if (TextUtils.isEmpty(strObtainConfigProperty4)) {
                return;
            }
            this.mInjectGEO = strObtainConfigProperty4;
        }
    }

    protected AdaWebview(Context context, AdaFrameView adaFrameView, IDCloudWebviewClientListener iDCloudWebviewClientListener) {
        super(context);
        this.unReceiveTitle = true;
        this.mWebViewParent = null;
        this.mRecordLastUrl = null;
        this.mWebViewImpl = null;
        this.mFrameView = null;
        this.mLoaded = false;
        this.mLoadCompleted = false;
        this.mPreloadJsLoaded = false;
        this.mPreloadJsLoading = false;
        this.mPlusLoaded = false;
        this.mPlusLoading = false;
        this.mPlusInjectTag = "page_finished";
        this.mIsAdvanceCss = false;
        this.mNeedInjection = true;
        this.mEncoding = null;
        this.mWebviewUUID = null;
        this.mWebviewANID = null;
        this.mFrameId = null;
        this.mJsInterfaces = null;
        this.hasErrorPage = false;
        this.errorPageUrl = null;
        this.originalUrl = null;
        this.mVideoFullscreen = "auto";
        this.needTouchEvent = "";
        this.favoriteOptions = "";
        this.shareOptions = "";
        this.mShareable = true;
        this.mPlusrequire = "normal";
        this.mInjectGEO = "none";
        this.mInjectGeoLoaded = false;
        this.mProgressIntValue = 0;
        this.isDisposed = false;
        this.mReceiveJSValue_android42 = null;
        this.isPause = false;
        this.mFlag = null;
        this.mInjectPlusLoadedUrl = null;
        this.mEvalJsOptionStack = null;
        this.mForceAHeadJsFileLoaded = false;
        this.mForceAHeadJsFile = null;
        this.mPreloadJsFile = new ArrayList<>(2);
        this.mCssString = "";
        this.executeScriptListener = new MessageHandler.IMessages() { // from class: io.dcloud.common.adapter.ui.AdaWebview.4
            @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
            public void execute(Object obj) {
                String str = (String) obj;
                DCWebView dCWebView = AdaWebview.this.mWebViewImpl;
                if (dCWebView != null) {
                    if (!str.startsWith(AbsoluteConst.PROTOCOL_JAVASCRIPT)) {
                        str = AbsoluteConst.PROTOCOL_JAVASCRIPT + str;
                    }
                    dCWebView.loadUrl(str);
                }
            }
        };
        this.mMesssageListener = new MessageHandler.IMessages() { // from class: io.dcloud.common.adapter.ui.AdaWebview.5
            @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
            public void execute(Object obj) {
                Object[] objArr = (Object[]) obj;
                AdaWebview.this.mJsInterfaces.exec(String.valueOf(objArr[0]), String.valueOf(objArr[1]), (JSONArray) objArr[2]);
            }
        };
        this.mStateListeners = null;
        this.mProgress = 0;
        this.isStart = false;
        this.justClearOption = false;
        this.mLoading = false;
        this.mNeedSitemapJson = false;
        this.mOverrideResourceRequestOptions = null;
        this.mOverrideUrlLoadingDataOptions = null;
        this.mListenResourceLoadingOptions = null;
        initANID();
        this.mFrameView = adaFrameView;
        this.mAppid = adaFrameView.obtainApp().obtainAppId();
        initSitemapState();
        Logger.d("AdaWebview");
        try {
            this.mWebViewImpl = WebViewFactory.getWebView(getActivity(), this, iDCloudWebviewClientListener);
        } catch (Exception e) {
            e.printStackTrace();
            this.mWebViewImpl = WebViewFactory.getWebView(getActivity(), this, iDCloudWebviewClientListener);
        }
        setMainView(this.mWebViewImpl.getWebView());
        AdaWebViewParent adaWebViewParent = new AdaWebViewParent(context);
        this.mWebViewParent = adaWebViewParent;
        adaWebViewParent.fillsWithWebview(this);
        if (adaFrameView.getFrameType() == 2) {
            this.mOverrideUrlLoadingDataOptions = this.mFrameView.obtainApp().obtainThridInfo(IApp.ConfigProperty.ThridInfo.OverrideUrlJsonData);
            JSONObject jSONObjectObtainThridInfo = this.mFrameView.obtainApp().obtainThridInfo(IApp.ConfigProperty.ThridInfo.OverrideResourceJsonData);
            if (jSONObjectObtainThridInfo != null) {
                this.mOverrideResourceRequestOptions = jSONObjectObtainThridInfo.optJSONArray(WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
            }
            this.mNeedInjection = PdrUtil.parseBoolean(this.mFrameView.obtainApp().obtainConfigProperty("injection"), this.mNeedInjection, false);
            String strObtainConfigProperty = this.mFrameView.obtainApp().obtainConfigProperty(IApp.ConfigProperty.CONFIG_LPLUSERQUIRE);
            if (!TextUtils.isEmpty(strObtainConfigProperty)) {
                this.mPlusrequire = strObtainConfigProperty;
            }
            String strObtainConfigProperty2 = this.mFrameView.obtainApp().obtainConfigProperty(IApp.ConfigProperty.CONFIG_LGEOLOCATION);
            if (TextUtils.isEmpty(strObtainConfigProperty2)) {
                return;
            }
            this.mInjectGEO = strObtainConfigProperty2;
            return;
        }
        if (adaFrameView.getFrameType() == 4) {
            String strObtainConfigProperty3 = this.mFrameView.obtainApp().obtainConfigProperty(IApp.ConfigProperty.CONFIG_SPLUSERQUIRE);
            if (!TextUtils.isEmpty(strObtainConfigProperty3)) {
                this.mPlusrequire = strObtainConfigProperty3;
            }
            String strObtainConfigProperty4 = this.mFrameView.obtainApp().obtainConfigProperty(IApp.ConfigProperty.CONFIG_SGEOLOCATION);
            if (TextUtils.isEmpty(strObtainConfigProperty4)) {
                return;
            }
            this.mInjectGEO = strObtainConfigProperty4;
        }
    }

    protected AdaWebview(Context context, AdaFrameView adaFrameView, OnPageFinishedCallack onPageFinishedCallack) {
        super(context);
        this.unReceiveTitle = true;
        this.mWebViewParent = null;
        this.mRecordLastUrl = null;
        this.mWebViewImpl = null;
        this.mFrameView = null;
        this.mLoaded = false;
        this.mLoadCompleted = false;
        this.mPreloadJsLoaded = false;
        this.mPreloadJsLoading = false;
        this.mPlusLoaded = false;
        this.mPlusLoading = false;
        this.mPlusInjectTag = "page_finished";
        this.mIsAdvanceCss = false;
        this.mNeedInjection = true;
        this.mEncoding = null;
        this.mWebviewUUID = null;
        this.mWebviewANID = null;
        this.mFrameId = null;
        this.mJsInterfaces = null;
        this.hasErrorPage = false;
        this.errorPageUrl = null;
        this.originalUrl = null;
        this.mVideoFullscreen = "auto";
        this.needTouchEvent = "";
        this.favoriteOptions = "";
        this.shareOptions = "";
        this.mShareable = true;
        this.mPlusrequire = "normal";
        this.mInjectGEO = "none";
        this.mInjectGeoLoaded = false;
        this.mProgressIntValue = 0;
        this.isDisposed = false;
        this.mReceiveJSValue_android42 = null;
        this.isPause = false;
        this.mFlag = null;
        this.mInjectPlusLoadedUrl = null;
        this.mEvalJsOptionStack = null;
        this.mForceAHeadJsFileLoaded = false;
        this.mForceAHeadJsFile = null;
        this.mPreloadJsFile = new ArrayList<>(2);
        this.mCssString = "";
        this.executeScriptListener = new MessageHandler.IMessages() { // from class: io.dcloud.common.adapter.ui.AdaWebview.4
            @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
            public void execute(Object obj) {
                String str = (String) obj;
                DCWebView dCWebView = AdaWebview.this.mWebViewImpl;
                if (dCWebView != null) {
                    if (!str.startsWith(AbsoluteConst.PROTOCOL_JAVASCRIPT)) {
                        str = AbsoluteConst.PROTOCOL_JAVASCRIPT + str;
                    }
                    dCWebView.loadUrl(str);
                }
            }
        };
        this.mMesssageListener = new MessageHandler.IMessages() { // from class: io.dcloud.common.adapter.ui.AdaWebview.5
            @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
            public void execute(Object obj) {
                Object[] objArr = (Object[]) obj;
                AdaWebview.this.mJsInterfaces.exec(String.valueOf(objArr[0]), String.valueOf(objArr[1]), (JSONArray) objArr[2]);
            }
        };
        this.mStateListeners = null;
        this.mProgress = 0;
        this.isStart = false;
        this.justClearOption = false;
        this.mLoading = false;
        this.mNeedSitemapJson = false;
        this.mOverrideResourceRequestOptions = null;
        this.mOverrideUrlLoadingDataOptions = null;
        this.mListenResourceLoadingOptions = null;
        initANID();
        this.mFrameView = adaFrameView;
        Logger.d("AdaWebview");
        try {
            this.mWebViewImpl = WebViewFactory.getWebView(getActivity(), this, onPageFinishedCallack);
        } catch (Exception e) {
            e.printStackTrace();
            this.mWebViewImpl = WebViewFactory.getWebView(getActivity(), this, onPageFinishedCallack);
        }
        setMainView(this.mWebViewImpl.getWebView());
        AdaWebViewParent adaWebViewParent = new AdaWebViewParent(context);
        this.mWebViewParent = adaWebViewParent;
        adaWebViewParent.fillsWithWebview(this);
    }
}
