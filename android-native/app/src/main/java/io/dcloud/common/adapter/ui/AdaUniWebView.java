package io.dcloud.common.adapter.ui;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.webkit.CookieManager;
import android.webkit.WebView;
import com.taobao.weex.common.WXConfig;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.utils.tools.TimeCalculator;
import dc.squareup.HttpConstants;
import dc.squareup.cookie.CookieCenter;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IFeature;
import io.dcloud.common.DHInterface.IFrameView;
import io.dcloud.common.DHInterface.IJsInterface;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.IUniNView;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.ReceiveJSValue;
import io.dcloud.common.adapter.ui.webview.DCWebView;
import io.dcloud.common.adapter.ui.webview.WebViewFactory;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.EventActionInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.MessageHandler;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.core.permission.PermissionControler;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.JSONUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.TelephonyUtil;
import io.dcloud.common.util.language.LanguageUtil;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class AdaUniWebView extends AdaWebview {
    MessageHandler.IMessages iMessages;
    boolean isUniService;
    IApp mApp;
    String mFullUrl;
    float mScale;
    IUniNView mUniNView;
    String mUrl;
    private String mUserAgent;
    ViewGroup mViewImpl;
    AbsMgr mWinMgr;

    public AdaUniWebView(Context context, IApp iApp, AdaFrameView adaFrameView, String str, String str2, JSONObject jSONObject, boolean z) throws JSONException {
        super(context);
        this.mWinMgr = null;
        this.mScale = 3.0f;
        this.mUserAgent = null;
        this.isUniService = false;
        this.iMessages = new MessageHandler.IMessages() { // from class: io.dcloud.common.adapter.ui.AdaUniWebView.1
            @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
            public void execute(Object obj) {
                Object[] objArr = (Object[]) obj;
                AdaUniWebView.this.exec(String.valueOf(objArr[0]), String.valueOf(objArr[1]), (JSONArray) objArr[2]);
            }
        };
        str = str == null ? "" : str;
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("js", str);
            jSONObject2.put("data", jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.isUniService = z;
        this.mApp = iApp;
        this.mFrameView = adaFrameView;
        this.mAppid = iApp.obtainAppId();
        initSitemapState();
        this.mUrl = str;
        this.mFullUrl = TextUtils.isEmpty(str) ? this.mUrl : this.mApp.convert2WebviewFullPath(null, this.mUrl);
        this.mWinMgr = adaFrameView.obtainWindowMgr();
        this.mScale = context.getResources().getDisplayMetrics().density;
        this.mWebViewParent = new AdaWebViewParent(context, false);
        initUserAgent(iApp);
        IUniNView iUniNView = (IUniNView) this.mWinMgr.processEvent(IMgr.MgrType.FeatureMgr, 10, new Object[]{iApp, "weex,io.dcloud.feature.weex.WeexFeature", "createUniNView", new Object[]{this, this.mWebViewParent.obtainMainViewGroup(), jSONObject2, str2}});
        this.mUniNView = iUniNView;
        if (iUniNView != null) {
            ViewGroup viewGroupObtainMainView = iUniNView.obtainMainView();
            this.mViewImpl = viewGroupObtainMainView;
            setMainView(viewGroupObtainMainView);
        }
        this.mWebViewParent.fillsWithWebview(this);
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public void addJsInterface(String str, IJsInterface iJsInterface) {
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview
    public void addJsInterface(String str, Object obj) {
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public void addJsInterface(String str, String str2) {
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public boolean canGoBack() {
        return false;
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public boolean canGoForward() {
        return false;
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.adapter.ui.AdaContainerFrameItem, io.dcloud.common.adapter.ui.AdaFrameItem
    public void dispose() {
        super.dispose();
        this.mUniNView = null;
        this.mViewImpl = null;
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public void endWebViewEvent(String str) {
        if (!PdrUtil.isEquals(str, AbsoluteConst.PULL_DOWN_REFRESH)) {
            super.endWebViewEvent(str);
            return;
        }
        IUniNView iUniNView = this.mUniNView;
        if (iUniNView != null) {
            iUniNView.endPullToRefresh();
        }
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public void evalJS(String str) {
        evalJSToUniNative(str);
    }

    public void evalJSToUniNative(String str) {
        if (this.mUniNView == null || PdrUtil.isEmpty(str)) {
            return;
        }
        HashMap map = new HashMap();
        map.put("data", str);
        this.mUniNView.fireGlobalEvent("nativeToUniJs", map);
    }

    public String exec(String str, String str2, JSONArray jSONArray) {
        if (getContext() == null) {
            return "";
        }
        try {
            if ("syncExecMethod".equalsIgnoreCase(str2)) {
                str2 = AbsoluteConst.UNI_SYNC_EXEC_METHOD;
            }
            return String.valueOf(this.mWinMgr.processEvent(IMgr.MgrType.FeatureMgr, 1, new Object[]{this, str, str2, jSONArray}));
        } catch (Exception e) {
            Logger.w("JsInterfaceImpl.exec pApiFeatureName=" + str + ";pActionName=" + str2 + ";pArgs=" + String.valueOf(jSONArray), e);
            return null;
        }
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public void executeScript(String str) {
        evalJS(str);
    }

    public void fireEvent(EventActionInfo eventActionInfo) {
        if (this.mUniNView == null || eventActionInfo == null) {
            return;
        }
        if (!TextUtils.isEmpty(eventActionInfo.getEvalJs())) {
            evalJSToUniNative(eventActionInfo.getEvalJs());
        }
        this.mUniNView.fireGlobalEvent(eventActionInfo.getEventAction(), eventActionInfo.getParams());
    }

    public Object getConfigInfo() {
        if (DeviceInfo.sNetWorkInited) {
            DeviceInfo.initGsmCdmaCell();
        }
        HashMap map = new HashMap();
        map.put("__HtMl_Id__", getWebviewUUID());
        if (PermissionControler.checkPermission(this.mApp.obtainAppId(), IFeature.F_DEVICE.toLowerCase(Locale.ENGLISH)) || !this.mApp.manifestBeParsed()) {
            HashMap map2 = new HashMap();
            map2.put("imei", DeviceInfo.sIMEI);
            map2.put("imsi", DeviceInfo.sIMSI);
            map2.put("model", DeviceInfo.sModel);
            map2.put("vendor", DeviceInfo.sVendor);
            map2.put("uuid", TelephonyUtil.getIMEI(this.mApp.getActivity(), false));
            map.put("device", map2);
            HashMap map3 = new HashMap();
            map3.put(IApp.ConfigProperty.CONFIG_LANGUAGE, LanguageUtil.getDeviceDefLocalLanguage());
            map3.put("version", Build.VERSION.RELEASE);
            map3.put("name", TimeCalculator.PLATFORM_ANDROID);
            map3.put("vendor", "Google");
            map.put(WXConfig.os, map3);
            int i = this.mApp.getInt(2);
            int i2 = this.mApp.getInt(0);
            int i3 = this.mApp.getInt(1);
            HashMap map4 = new HashMap();
            float scale = getScale();
            map4.put("resolutionHeight", Integer.valueOf((int) (i / scale)));
            int i4 = (int) (i2 / scale);
            map4.put("resolutionWidth", Integer.valueOf(i4));
            map4.put("scale", Float.valueOf(scale));
            map4.put("dpiX", Float.valueOf(DeviceInfo.dpiX));
            map4.put("dpiY", Float.valueOf(DeviceInfo.dpiY));
            map4.put("height", Integer.valueOf(i));
            map4.put("width", Integer.valueOf(i2));
            map.put("screen", map4);
            HashMap map5 = new HashMap();
            map5.put("resolutionHeight", Integer.valueOf((int) (i3 / scale)));
            map5.put("resolutionWidth", Integer.valueOf(i4));
            map.put("display", map5);
        }
        if (PermissionControler.checkPermission(this.mApp.obtainAppId(), IFeature.F_RUNTIME) || !this.mApp.manifestBeParsed()) {
            String strObtainConfigProperty = this.mApp.obtainConfigProperty(IApp.ConfigProperty.CONFIG_LOADED_TIME);
            HashMap map6 = new HashMap();
            map6.put("innerVersion", "1.9.9.82603");
            map6.put("appid", this.mApp.obtainAppId());
            map6.put("launchLoadedTime", strObtainConfigProperty);
            if (BaseInfo.ISAMU) {
                map6.put("version", this.mApp.obtainAppVersionName());
            } else {
                map6.put("version", AndroidResources.mApplicationInfo.versionName);
            }
            map6.put("arguments", this.mApp.obtainRuntimeArgs(false));
            map6.put("launcher", BaseInfo.getLauncherData(this.mApp.obtainAppId()));
            map6.put(AbsoluteConst.XML_CHANNEL, BaseInfo.getAnalysisChannel());
            map6.put("startupTime", String.valueOf(BaseInfo.getStartupTimeData(this.mApp.obtainAppId())));
            map6.put("processId", Long.valueOf(BaseInfo.sProcessId));
            map6.put("uniVersion", BaseInfo.uniVersionV3);
            map6.put("versionCode", Integer.valueOf(AndroidResources.versionCode));
            String bundleData = SP.getBundleData(this.mApp.getActivity(), "pdr", this.mApp.obtainAppId() + AbsoluteConst.LAUNCHTYPE);
            if (TextUtils.isEmpty(bundleData)) {
                bundleData = "default";
            }
            map6.put("origin", bundleData);
            map.put("runtime", map6);
        }
        HashMap map7 = new HashMap();
        map7.put("__isUniPush__", Boolean.valueOf(AndroidResources.getMetaValue("DCLOUD_UNIPUSH")));
        map.put("push", map7);
        return map;
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public String getCookie(String str) {
        return WebViewFactory.isIsOtherInitSuccess() ? CookieCenter.getCookies(str) : CookieManager.getInstance().getCookie(str);
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public String getOriginalUrl() {
        return this.mUrl;
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public float getScale() {
        return this.mScale;
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public float getScaleOfOpenerWebview() {
        return 0.0f;
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public String getTitle() {
        return "";
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public String getWebviewProperty(String str) {
        return IWebview.USER_AGENT.equals(str) ? this.mUserAgent : super.getWebviewProperty(str);
    }

    void initUserAgent(IApp iApp) {
        String str;
        if (!PdrUtil.isEmpty(AdaWebview.sCustomUserAgent)) {
            this.mUserAgent = AdaWebview.sCustomUserAgent;
            return;
        }
        this.mUserAgent = WebViewFactory.getDefWebViewUA(getContext());
        String strObtainConfigProperty = iApp.obtainConfigProperty(IApp.ConfigProperty.CONFIG_USER_AGENT);
        boolean z = Boolean.parseBoolean(iApp.obtainConfigProperty(IApp.ConfigProperty.CONFIG_CONCATENATE));
        if (Boolean.parseBoolean(iApp.obtainConfigProperty(IApp.ConfigProperty.CONFIG_funSetUA))) {
            z = false;
        }
        if (!z && !PdrUtil.isEmpty(strObtainConfigProperty)) {
            this.mUserAgent = strObtainConfigProperty;
        } else if (!PdrUtil.isEmpty(strObtainConfigProperty)) {
            this.mUserAgent += Operators.SPACE_STR + strObtainConfigProperty.trim();
        }
        boolean z2 = Boolean.parseBoolean(iApp.obtainConfigProperty(IApp.ConfigProperty.CONFIG_H5PLUS));
        boolean zBooleanValue = Boolean.valueOf(iApp.obtainConfigProperty(AbsoluteConst.JSONKEY_STATUSBAR_IMMERSED)).booleanValue();
        if (iApp.obtainStatusBarMgr() == null || !iApp.obtainStatusBarMgr().checkImmersedStatusBar(getActivity(), zBooleanValue)) {
            str = "";
        } else {
            str = " (Immersed/" + (DeviceInfo.sStatusBarHeight / this.mScale) + Operators.BRACKET_END_STR;
        }
        if (z2 && this.mUserAgent.indexOf(DCWebView.UserAgentExtInfo) < 0) {
            if (BaseInfo.ISAMU && BaseInfo.isBase(getContext())) {
                this.mUserAgent += " Html5Plus/1.0 StreamApp/1.0" + str;
            } else {
                this.mUserAgent += DCWebView.UserAgentExtInfo + str;
            }
        }
        HttpConstants.setUA(this.mUserAgent);
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public boolean isUniService() {
        return this.isUniService;
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public boolean isUniWebView() {
        return true;
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public void loadContentData(String str, String str2, String str3, String str4) {
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public void loadUrl(String str) {
        if (PdrUtil.isNetPath(str) || PdrUtil.isFilePath(str)) {
            return;
        }
        evalJS(str);
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public IApp obtainApp() {
        return this.mApp;
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public IFrameView obtainFrameView() {
        return this.mFrameView;
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public String obtainFullUrl() {
        return this.mFullUrl;
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public String obtainPageTitle() {
        return "";
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public String obtainUrl() {
        return this.mUrl;
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public WebView obtainWebview() {
        return null;
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public ViewGroup obtainWindowView() {
        return this.mViewImpl;
    }

    public String prompt(String str, String str2) throws JSONException {
        if (str2 != null && str2.length() > 3 && str2.substring(0, 4).equals("pdr:")) {
            try {
                JSONArray jSONArray = new JSONArray(str2.substring(4));
                String string = jSONArray.getString(0);
                String string2 = jSONArray.getString(1);
                boolean z = jSONArray.getBoolean(2);
                JSONArray jSONArrayCreateJSONArray = JSONUtil.createJSONArray(str);
                if (!z) {
                    return exec(string, string2, jSONArrayCreateJSONArray);
                }
                MessageHandler.sendMessage(this.iMessages, new Object[]{string, string2, jSONArrayCreateJSONArray});
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public void reload(String str) {
    }

    public void setNVuePath(String str) {
        this.mUrl = str;
        this.mFullUrl = TextUtils.isEmpty(str) ? this.mUrl : this.mApp.convert2WebviewFullPath(null, this.mUrl);
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public void setOriginalUrl(String str) {
        this.mUrl = str;
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public void setWebViewEvent(String str, Object obj) {
        if (PdrUtil.isEquals(str, AbsoluteConst.PULL_DOWN_REFRESH)) {
            JSONObject jSONObject = (JSONObject) obj;
            IUniNView iUniNView = this.mUniNView;
            if (iUniNView != null) {
                iUniNView.initRefresh(jSONObject);
                return;
            }
            return;
        }
        if (!PdrUtil.isEquals(str, AbsoluteConst.PULL_REFRESH_BEGIN)) {
            super.setWebViewEvent(str, obj);
            return;
        }
        IUniNView iUniNView2 = this.mUniNView;
        if (iUniNView2 != null) {
            iUniNView2.beginPullRefresh();
        }
    }

    @Override // io.dcloud.common.DHInterface.IWebview
    public void show(Animation animation) {
    }

    public void titleNViewRefresh() {
        IUniNView iUniNView = this.mUniNView;
        if (iUniNView != null) {
            iUniNView.titleNViewRefresh();
        }
    }

    public void updateScreenAndDisplay() {
        if (obtainApp() == null || this.mUniNView == null) {
            return;
        }
        int i = obtainApp().getInt(2);
        int i2 = obtainApp().getInt(0);
        int i3 = obtainApp().getInt(1);
        HashMap map = new HashMap();
        float scale = getScale();
        map.put("resolutionHeight", Double.valueOf(Math.ceil(i / scale)));
        double d = i2 / scale;
        map.put("resolutionWidth", Double.valueOf(Math.ceil(d)));
        map.put("dpiX", Float.valueOf(DeviceInfo.dpiX));
        map.put("dpiY", Float.valueOf(DeviceInfo.dpiY));
        HashMap map2 = new HashMap();
        map2.put("resolutionHeight", Double.valueOf(Math.ceil(i3 / scale)));
        map2.put("resolutionWidth", Double.valueOf(Math.ceil(d)));
        StringBuilder sb = new StringBuilder();
        for (String str : map.keySet()) {
            sb.append("plus.screen.");
            sb.append(str);
            sb.append("=");
            sb.append(map.get(str));
            sb.append(";");
        }
        for (String str2 : map2.keySet()) {
            sb.append("plus.display.");
            sb.append(str2);
            sb.append("=");
            sb.append(map2.get(str2));
            sb.append(";");
        }
        evalJSToUniNative(sb.toString());
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public void evalJS(String str, ReceiveJSValue.ReceiveJSValueCallback receiveJSValueCallback) {
        evalJS(str);
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public void reload() {
        IUniNView iUniNView = this.mUniNView;
        if (iUniNView != null) {
            iUniNView.reload();
        }
    }

    @Override // io.dcloud.common.adapter.ui.AdaWebview, io.dcloud.common.DHInterface.IWebview
    public void reload(boolean z) {
        reload();
    }
}
