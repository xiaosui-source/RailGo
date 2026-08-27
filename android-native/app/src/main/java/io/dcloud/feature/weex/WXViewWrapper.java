package io.dcloud.feature.weex;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.taobao.weex.IWXInstanceContainerOnSizeListener;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.R;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.bridge.WXParams;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.ui.component.WXBasicComponentType;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import com.taobao.weex.utils.tools.LogDetail;
import io.dcloud.common.DHInterface.IActivityHandler;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IEventCallback;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaFrameView;
import io.dcloud.common.adapter.ui.AdaUniWebView;
import io.dcloud.common.adapter.ui.AdaWebview;
import io.dcloud.common.adapter.ui.webview.WebResUtil;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.ErrorDialogUtil;
import io.dcloud.common.util.IOUtil;
import io.dcloud.common.util.JSONUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import io.dcloud.common.util.ThreadPool;
import io.dcloud.common.util.language.LanguageUtil;
import io.dcloud.feature.internal.sdk.SDK;
import io.dcloud.feature.uniapp.UniSDKInstance;
import io.dcloud.feature.weex.WeexInstanceMgr;
import io.dcloud.weex.WXDotDataUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
class WXViewWrapper extends WXBaseWrapper implements IWXRenderListener, IEventCallback, WeexInstanceMgr.IWXStatisticsCallBack {
    static final int LOAD_JS = 1000;
    String TAG;
    private boolean hasScrollListener;
    private boolean isChlid;
    private boolean isCompilerWithUniapp;
    boolean isDelayRender;
    private boolean isFrameShow;
    private boolean isPre;
    boolean isReady;
    boolean isService;
    JSONObject jsonObject;
    long lastTime;
    List<FireEvent> mFireCaches;
    int mFontSize;
    Handler mHandler;
    IWXInstanceContainerOnSizeListener mInstanceOnSizeListener;
    private float mLastScreenWidth;
    JSONObject mNvueCfgData;
    private List<Message> mRenderCaches;
    LogDetail mServiceLogDetail;
    private String mUniPagePath;
    float mViewPort;
    View mWXSDKView;
    private List<Message> mWaitServiceRenderList;
    private String readyJs;
    long time;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    class FireEvent {
        String key;
        Map<String, Object> params;

        public FireEvent(String str, Map<String, Object> map) {
            this.key = str;
            this.params = map;
        }
    }

    WXViewWrapper(IWebview iWebview, ViewGroup viewGroup, JSONObject jSONObject, String str, int i, boolean z) {
        super(viewGroup.getContext());
        this.TAG = "WXViewWrapper";
        this.lastTime = 0L;
        this.isService = false;
        this.readyJs = ";var plusModule = weex.requireModule('plus'); plusModule.uniReady();";
        this.isReady = false;
        this.mFontSize = -1;
        this.mNvueCfgData = null;
        this.isDelayRender = false;
        this.mRenderCaches = new ArrayList();
        this.mWaitServiceRenderList = new ArrayList();
        this.isFrameShow = false;
        this.isChlid = false;
        this.isPre = false;
        this.isCompilerWithUniapp = true;
        this.mHandler = new Handler() { // from class: io.dcloud.feature.weex.WXViewWrapper.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if (message.what != 1000) {
                    return;
                }
                WXViewWrapper wXViewWrapper = WXViewWrapper.this;
                if (!wXViewWrapper.isService) {
                    wXViewWrapper.delayedRender(message.obj, 10);
                    return;
                }
                IWebview iWebview2 = wXViewWrapper.mWebview;
                if (iWebview2 == null) {
                    return;
                }
                String strObtainConfigProperty = iWebview2.obtainApp().obtainConfigProperty(AbsoluteConst.NVUE_LAUNCH_MODE);
                if (TextUtils.isEmpty(strObtainConfigProperty) || !strObtainConfigProperty.equals("fast") || !WeexInstanceMgr.self().getControl().equals(AbsoluteConst.UNI_V3) || WXEnvironment.sRemoteDebugMode) {
                    WXViewWrapper.this.delayedRender(message.obj, 10L);
                    return;
                }
                JSONObject jSONObjectObtainThridInfo = WXViewWrapper.this.mWebview.obtainApp().obtainThridInfo(IApp.ConfigProperty.ThridInfo.LaunchWebviewJsonData);
                if (jSONObjectObtainThridInfo == null || !jSONObjectObtainThridInfo.has(AbsoluteConst.JSON_KEY_UNINVIEW)) {
                    WXViewWrapper.this.delayedRender(message.obj, 10L);
                } else {
                    WXViewWrapper.this.delayedRender(message.obj, 100L);
                }
            }
        };
        this.mInstanceOnSizeListener = new IWXInstanceContainerOnSizeListener() { // from class: io.dcloud.feature.weex.WXViewWrapper.2
            @Override // com.taobao.weex.IWXInstanceContainerOnSizeListener
            public void onSizeChanged(String str2, float f, float f2, boolean z2, boolean z3) {
                WXViewWrapper.this.updateDeviceDisplay(f, f2, z2, z3);
            }
        };
        this.hasScrollListener = false;
        this.time = 0L;
        this.mLastScreenWidth = 0.0f;
        this.lastTime = System.currentTimeMillis();
        Logger.e(this.TAG, "WXViewWrapper----------isPre=" + z + "  wxid=" + str);
        if (!WeexInstanceMgr.self().isJsFrameworkReady()) {
            WeexInstanceMgr.self().setWXStatisticsCallBack(this);
        }
        this.isCompilerWithUniapp = !WeexInstanceMgr.self().getComplier().equalsIgnoreCase("weex");
        this.mWebview = iWebview;
        this.isPre = z;
        this.mFireCaches = new ArrayList();
        IWebview iWebview2 = this.mWebview;
        if (iWebview2 instanceof AdaUniWebView) {
            this.isService = ((AdaUniWebView) iWebview2).isUniService();
        }
        if (WeexInstanceMgr.self().isJSFKFileNotFound()) {
            ErrorDialogUtil.showErrorTipsAlert(iWebview.getActivity(), StringUtil.format(iWebview.getContext().getString(R.string.dcloud_feature_weex_jsfk_not_found_tips), WeexInstanceMgr.self().getVueVersion() == 3 ? "VUE3" : "VUE2"), new DialogInterface.OnClickListener() { // from class: io.dcloud.feature.weex.WXViewWrapper.3
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                    Process.killProcess(Process.myPid());
                }
            });
            return;
        }
        viewGroup.addView(this, i, new ViewGroup.LayoutParams(-1, -1));
        ((AdaFrameView) this.mWebview.obtainFrameView()).addFrameViewListener(this);
        this.jsonObject = jSONObject;
        this.lastTime = System.currentTimeMillis();
        this.mWxId = str;
        this.mNvueCfgData = JSONUtil.createJSONObject(this.mWebview.obtainApp().obtainConfigProperty(IApp.ConfigProperty.UNI_NVUE_DATA));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void delayedRender(final Object obj, long j) {
        postDelayed(new Runnable() { // from class: io.dcloud.feature.weex.WXViewWrapper.9
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                WXViewWrapper wXViewWrapper = WXViewWrapper.this;
                if (wXViewWrapper.mWebview == null) {
                    return;
                }
                wXViewWrapper.render(obj, wXViewWrapper.getInitOptions(), WXViewWrapper.this.getInitStringJsonData());
            }
        }, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getAllUniService(String str) {
        IWebview iWebview = this.mWebview;
        if (iWebview == null || iWebview.obtainApp() == null) {
            return "";
        }
        IApp iAppObtainApp = this.mWebview.obtainApp();
        String strInitSrcPath = initSrcPath("_www/app-config.js");
        String strInitSrcPath2 = initSrcPath("_www/app-confusion.js");
        String dCloudUts = getDCloudUts();
        InputStream encryptionInputStream = WebResUtil.getEncryptionInputStream(strInitSrcPath, iAppObtainApp);
        InputStream encryptionInputStream2 = WebResUtil.getEncryptionInputStream(strInitSrcPath2, iAppObtainApp);
        try {
            return IOUtil.toString(encryptionInputStream) + IOUtil.toString(encryptionInputStream2) + dCloudUts + str;
        } catch (IOException e) {
            e.printStackTrace();
            return str;
        }
    }

    private String getDCloudUts() {
        InputStream resInputStream = PlatformUtil.getResInputStream("data/dcloud_uts.dat");
        if (resInputStream == null) {
            return "";
        }
        try {
            return new String(Base64.decode(IOUtil.getBytes(resInputStream), 0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Map<String, Object> getInitOptions() {
        int iIndexOf;
        HashMap map = new HashMap();
        IWebview iWebview = this.mWebview;
        if (iWebview != null) {
            map.put("plus_appid", iWebview.obtainApp().obtainAppId());
        }
        IWebview iWebview2 = this.mWebview;
        if (iWebview2 != null) {
            map.put("plus_web_id", iWebview2.obtainFrameId());
        }
        String str = this.mSrcPath;
        if (!TextUtils.isEmpty(this.mPath) && str.indexOf(Operators.CONDITION_IF_STRING) == -1 && (iIndexOf = this.mPath.indexOf(Operators.CONDITION_IF_STRING)) > 0) {
            str = str + this.mPath.substring(iIndexOf);
        }
        map.put("deviceLanguage", LanguageUtil.getDeviceDefLocalLanguage());
        map.put("deviceCountry", LanguageUtil.getDeviceDefCountry());
        map.put("isInternational", Boolean.valueOf(PdrUtil.checkIntl()));
        map.put("bundleUrl", str);
        if (getWxId().equals("__uniapp__service")) {
            map.put("plus_weex_id", "__uniapp__service");
        }
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getInitStringJsonData() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            String originalUrl = this.mWebview.getOriginalUrl();
            if (originalUrl.startsWith(DeviceInfo.FILE_PROTOCOL)) {
                originalUrl = originalUrl.substring(7);
            }
            jSONObject.put("Plus_InitURL", this.mWebview.obtainApp().convert2RelPath(originalUrl));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    private void initFlexDirection() {
        JSONObject jSONObject = this.mNvueCfgData;
        if (jSONObject == null || !jSONObject.has(AbsoluteConst.UNI_NVUE_FLEX_DIRECTION)) {
            return;
        }
        WXBridgeManager.getInstance().setFlexDirectionDef(this.mNvueCfgData.optString(AbsoluteConst.UNI_NVUE_FLEX_DIRECTION));
    }

    private void initTitleNView() {
        AdaFrameView adaFrameView = (AdaFrameView) this.mWebview.obtainFrameView();
        JSONObject jSONObject = adaFrameView.obtainFrameOptions().titleNView;
        if (jSONObject == null || adaFrameView.isChildOfFrameView || !jSONObject.has("type") || !"transparent".equals(jSONObject.optString("type"))) {
            return;
        }
        this.mWXSDKInstance.addOnInstanceVisibleListener(new WXSDKInstance.OnInstanceVisibleListener() { // from class: io.dcloud.feature.weex.WXViewWrapper.4
            @Override // com.taobao.weex.WXSDKInstance.OnInstanceVisibleListener
            public void onAppear() {
                WXViewWrapper.this.addScrollListener(this);
            }

            @Override // com.taobao.weex.WXSDKInstance.OnInstanceVisibleListener
            public void onDisappear() {
            }
        });
    }

    private void initViewPortWidth(boolean z) {
        if (!this.isCompilerWithUniapp || this.mWXSDKInstance == null) {
            return;
        }
        int screenWidth = this.mWebview.obtainApp().getInt(0);
        if (z) {
            screenWidth = WXViewUtils.getScreenWidth(getContext());
        }
        float scale = screenWidth / this.mWebview.getScale();
        this.mViewPort = scale;
        this.mWXSDKInstance.setInstanceViewPortWidth(scale, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void render(Object obj, Map<String, Object> map, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        WXSDKInstance wXSDKInstance;
        if (this.mWXSDKInstance != null) {
            recoveryInstance();
        }
        IWebview iWebview = this.mWebview;
        if (iWebview == null || iWebview.obtainFrameView() == null) {
            return;
        }
        if (this.isService && BaseInfo.SyncDebug) {
            if (this.mServiceLogDetail == null) {
                LogDetail logDetail = new LogDetail();
                this.mServiceLogDetail = logDetail;
                logDetail.name("initV3Service");
            }
            this.mServiceLogDetail.taskStart();
        }
        this.isChlid = ((AdaFrameView) this.mWebview.obtainFrameView()).isChildOfFrameView;
        this.isReady = false;
        if (!this.isPre) {
            String strReplaceFirst = (String) obj;
            if (!WXEnvironment.sRemoteDebugMode) {
                strReplaceFirst = strReplaceFirst.replaceFirst(Pattern.quote("\"use weex:vue\""), Matcher.quoteReplacement(""));
            }
            obj = strReplaceFirst + this.readyJs;
        } else if (WeexInstanceMgr.self().getPreInstanceId() != null) {
            WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(WeexInstanceMgr.self().getPreInstanceId());
            this.mWXSDKInstance = sDKInstance;
            if (sDKInstance != null) {
                sDKInstance.init(this.mWebview.getContext());
                onReady();
                fireGlobalEvent("launchApp", new HashMap());
            }
        }
        if (!this.isPre) {
            if (this.mWebview.getActivity() instanceof IActivityHandler) {
                this.mWXSDKInstance = new UniSDKInstance(this.mWebview.getContext(), ((IActivityHandler) this.mWebview.getActivity()).getOriginalContext());
            } else {
                this.mWXSDKInstance = new UniSDKInstance(this.mWebview.getContext());
            }
        }
        int i = this.mFontSize;
        if (i > 0) {
            this.mWXSDKInstance.setDefaultFontSize(i);
        }
        this.mWXSDKInstance.setPageKeepRawCssStyles();
        this.mWXSDKInstance.setImmersive(this.mWebview.obtainApp().obtainStatusBarMgr().isImmersive);
        this.mWXAnaly = new WXAnalyzerDelegate(this.mWebview.getContext());
        this.mWXSDKInstance.registerRenderListener(this);
        this.mWXSDKInstance.setBundleUrl(this.mSrcPath);
        this.mWXSDKInstance.setUniPagePath(this.isService ? "app-service.js" : this.mUniPagePath);
        this.mWXSDKInstance.setWXInstanceContainerOnSizeListener(this.mInstanceOnSizeListener);
        ((UniSDKInstance) this.mWXSDKInstance).setCompilerWithUniapp(this.isCompilerWithUniapp);
        int frameType = this.mWebview.obtainFrameView().getFrameType();
        if ((this.isFrameShow || frameType == 2 || frameType == 4) && (wXSDKInstance = this.mWXSDKInstance) != null) {
            wXSDKInstance.onShowAnimationEnd();
        }
        if (!this.isPre) {
            this.mWXSDKInstance.render(this.mWxId, String.valueOf(obj), map, str, WXRenderStrategy.APPEND_ASYNC);
        }
        if (!this.isService) {
            IWebview iWebview2 = this.mWebview;
            if (iWebview2 instanceof AdaWebview) {
                ((AdaWebview) iWebview2).dispatchWebviewStateEvent(0, this.mPath);
            }
        }
        initViewPortWidth(false);
        initFlexDirection();
        initTitleNView();
        if (this.isService) {
            WeexInstanceMgr.self().setUniServiceCreated(true, this.mWebview.obtainApp());
        }
    }

    private synchronized void runFireCache() {
        if (this.mFireCaches.isEmpty()) {
            return;
        }
        for (int i = 0; i < this.mFireCaches.size(); i++) {
            FireEvent fireEvent = this.mFireCaches.get(i);
            fireGlobalEvent(fireEvent.key, fireEvent.params);
        }
        this.mFireCaches.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDeviceDisplay(float f, float f2, boolean z, boolean z2) {
        IWebview iWebview;
        if (this.mWXSDKInstance == null || (iWebview = this.mWebview) == null || iWebview.getActivity() == null) {
            return;
        }
        int i = this.mWebview.obtainApp().getInt(2);
        int i2 = this.mWebview.obtainApp().getInt(0);
        int i3 = this.mWebview.obtainApp().getInt(1);
        if (this.mWXSDKInstance.isOnSizeChangedRender() && this.mWXSDKInstance.isOnSizeChangedRender()) {
            HashMap map = new HashMap();
            float scale = this.mWebview.getScale();
            map.put("resolutionHeight", Integer.valueOf((int) (i / scale)));
            float f3 = i2;
            int i4 = (int) (f3 / scale);
            map.put("resolutionWidth", Integer.valueOf(i4));
            map.put("dpiX", Float.valueOf(DeviceInfo.dpiX));
            map.put("dpiY", Float.valueOf(DeviceInfo.dpiY));
            HashMap map2 = new HashMap();
            map2.put("resolutionHeight", Integer.valueOf((int) (i3 / scale)));
            map2.put("resolutionWidth", Integer.valueOf(i4));
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
            this.mWebview.evalJS(sb.toString());
            if (this.isCompilerWithUniapp) {
                this.mWXSDKInstance.setInstanceViewPortWidth(f3 / this.mWebview.getScale(), true);
            }
            if (this.isService) {
                return;
            }
            this.mWXSDKInstance.setDeviceDisplayOfPage(i2, i3);
            this.mWXSDKInstance.reloadPageLayout();
            WXBridgeManager.getInstance().setDefaultRootSize(this.mWXSDKInstance.getInstanceId(), f, f2, z, z2);
        }
    }

    private void updateInitDeviceParams(Context context) {
        WXParams initParams = WXBridgeManager.getInstance().getInitParams();
        if (initParams == null || TextUtils.equals(initParams.getDeviceWidth(), String.valueOf(WXViewUtils.getScreenWidth(context)))) {
            return;
        }
        initParams.setDeviceWidth(String.valueOf(WXViewUtils.getScreenWidth(context)));
        initParams.setDeviceHeight(String.valueOf(WXViewUtils.getScreenHeight(context)));
        float f = WXEnvironment.sApplication.getResources().getDisplayMetrics().density;
        WXEnvironment.addCustomOptions("scale", Float.toString(f));
        WXBridgeManager.getInstance().updateInitDeviceParams(initParams.getDeviceWidth(), initParams.getDeviceHeight(), Float.toString(f), WXViewUtils.getStatusBarHeight(context) > 0 ? String.valueOf(WXViewUtils.getStatusBarHeight(context)) : null);
        WXBridgeManager.getInstance().setDeviceDisplay(this.mWXSDKInstance.getInstanceId(), WXViewUtils.getScreenWidth(context), WXViewUtils.getScreenHeight(context), WXViewUtils.getScreenDensity(context));
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x005f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void addScrollListener(com.taobao.weex.WXSDKInstance.OnInstanceVisibleListener r7) {
        /*
            r6 = this;
            com.taobao.weex.WXSDKInstance r0 = r6.mWXSDKInstance
            if (r0 == 0) goto Lbc
            android.view.View r0 = r0.getRootView()
            if (r0 != 0) goto Lc
            goto Lbc
        Lc:
            boolean r0 = r6.hasScrollListener
            if (r0 == 0) goto L12
            goto Lbc
        L12:
            r0 = 1
            r6.hasScrollListener = r0
            io.dcloud.common.DHInterface.IWebview r1 = r6.mWebview
            io.dcloud.common.DHInterface.IFrameView r1 = r1.obtainFrameView()
            io.dcloud.common.adapter.ui.AdaFrameView r1 = (io.dcloud.common.adapter.ui.AdaFrameView) r1
            io.dcloud.common.adapter.util.ViewOptions r2 = r1.obtainFrameOptions()
            io.dcloud.common.adapter.ui.AdaWebViewParent r3 = r1.obtainWebviewParent()
            io.dcloud.common.adapter.util.ViewOptions r3 = r3.obtainFrameOptions()
            org.json.JSONObject r2 = r2.titleNView
            if (r2 != 0) goto L32
            org.json.JSONObject r3 = r3.titleNView
            if (r3 == 0) goto L32
            r2 = r3
        L32:
            if (r2 == 0) goto Lbc
            java.lang.String r3 = "type"
            boolean r4 = r2.has(r3)
            if (r4 != 0) goto L3e
            goto Lbc
        L3e:
            io.dcloud.common.adapter.ui.AdaContainerFrameItem r4 = r1.getParentFrameItem()
            boolean r5 = r4 instanceof io.dcloud.common.adapter.ui.AdaFrameView
            if (r5 == 0) goto L5f
            io.dcloud.common.adapter.ui.AdaFrameView r4 = (io.dcloud.common.adapter.ui.AdaFrameView) r4
            io.dcloud.common.DHInterface.IWebview r5 = r4.obtainWebView()
            if (r5 == 0) goto L5f
            io.dcloud.common.DHInterface.IWebview r4 = r4.obtainWebView()
            java.lang.String r4 = r4.obtainFrameId()
            java.lang.String r5 = "tab"
            boolean r4 = r5.equals(r4)
            if (r4 == 0) goto L5f
            goto L60
        L5f:
            r0 = 0
        L60:
            boolean r4 = r1.isChildOfFrameView
            if (r4 == 0) goto L66
            if (r0 == 0) goto Lbc
        L66:
            boolean r0 = r2.has(r3)
            if (r0 == 0) goto Lbc
            java.lang.String r0 = r2.optString(r3)
            java.lang.String r3 = "transparent"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto Lbc
            com.taobao.weex.WXSDKInstance r0 = r6.mWXSDKInstance
            if (r0 != 0) goto L7d
            goto Lbc
        L7d:
            android.view.View r0 = r0.getRootView()
            if (r0 == 0) goto L8a
            if (r7 == 0) goto L8a
            com.taobao.weex.WXSDKInstance r3 = r6.mWXSDKInstance
            r3.removeOnInstanceVisibleListener(r7)
        L8a:
            boolean r7 = r0 instanceof io.dcloud.feature.weex_scroller.view.DCWXScrollView
            if (r7 == 0) goto L99
            io.dcloud.feature.weex_scroller.view.DCWXScrollView r0 = (io.dcloud.feature.weex_scroller.view.DCWXScrollView) r0
            io.dcloud.feature.weex.WXViewWrapper$5 r7 = new io.dcloud.feature.weex.WXViewWrapper$5
            r7.<init>()
            r0.addScrollViewListener(r7)
            return
        L99:
            boolean r7 = r0 instanceof com.taobao.weex.ui.view.WXScrollView
            if (r7 == 0) goto La8
            com.taobao.weex.ui.view.WXScrollView r0 = (com.taobao.weex.ui.view.WXScrollView) r0
            io.dcloud.feature.weex.WXViewWrapper$6 r7 = new io.dcloud.feature.weex.WXViewWrapper$6
            r7.<init>()
            r0.addScrollViewListener(r7)
            return
        La8:
            boolean r7 = r0 instanceof com.taobao.weex.ui.view.refresh.wrapper.BounceRecyclerView
            if (r7 == 0) goto Lbc
            com.taobao.weex.ui.view.refresh.wrapper.BounceRecyclerView r0 = (com.taobao.weex.ui.view.refresh.wrapper.BounceRecyclerView) r0
            android.view.View r7 = r0.getInnerView()
            com.taobao.weex.ui.view.listview.WXRecyclerView r7 = (com.taobao.weex.ui.view.listview.WXRecyclerView) r7
            io.dcloud.feature.weex.WXViewWrapper$7 r0 = new io.dcloud.feature.weex.WXViewWrapper$7
            r0.<init>()
            r7.addOnScrollListener(r0)
        Lbc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.weex.WXViewWrapper.addScrollListener(com.taobao.weex.WXSDKInstance$OnInstanceVisibleListener):void");
    }

    @Override // io.dcloud.feature.weex.WXBaseWrapper, io.dcloud.common.DHInterface.IUniNView
    public String evalJs(String str, int i) {
        return this.mWXSDKInstance != null ? WXBridgeManager.getInstance().syncExecJsOnInstanceWithResult(this.mWXSDKInstance.getInstanceId(), str, i) : "";
    }

    @Override // io.dcloud.feature.weex.WXBaseWrapper, io.dcloud.common.DHInterface.IUniNView
    public synchronized boolean fireGlobalEvent(String str, Map<String, Object> map) {
        if (this.isReady) {
            return super.fireGlobalEvent(str, map);
        }
        this.mFireCaches.add(new FireEvent(str, map));
        return true;
    }

    public String getSrcPath() {
        return this.mSrcPath;
    }

    @Override // io.dcloud.feature.weex.WXBaseWrapper, io.dcloud.common.DHInterface.IUniNView
    public String getType() {
        return WXBasicComponentType.VIEW;
    }

    public List<Message> getWaitServiceRenderList() {
        return this.mWaitServiceRenderList;
    }

    public String getWxId() {
        return this.mWxId;
    }

    public String initSrcPath(String str) {
        if (this.mWebview == null) {
            return str;
        }
        int iIndexOf = str.indexOf(Operators.CONDITION_IF_STRING);
        String strSubstring = iIndexOf > 1 ? str.substring(0, iIndexOf) : str;
        if (strSubstring.startsWith("/")) {
            strSubstring = strSubstring.substring(1);
        }
        String[] strArrSplit = str.split("\\.");
        if (strArrSplit.length == 1) {
            strSubstring = strSubstring + ".js";
        } else if (strArrSplit.length == 2) {
            strSubstring = strArrSplit[0] + ".js";
        }
        File file = new File(strSubstring);
        if (file.exists()) {
            return Uri.fromFile(file).toString();
        }
        byte bObtainRunningAppMode = this.mWebview.obtainApp().obtainRunningAppMode();
        IWebview iWebview = this.mWebview;
        String strObtainFullUrl = !(iWebview instanceof AdaUniWebView) ? iWebview.obtainFullUrl() : null;
        if (strSubstring.startsWith("/storage") || bObtainRunningAppMode != 1) {
            return this.mWebview.obtainApp().convert2WebviewFullPath(strObtainFullUrl, strSubstring);
        }
        String strConvert2AbsFullPath = this.mWebview.obtainApp().convert2AbsFullPath(strObtainFullUrl, strSubstring);
        return strConvert2AbsFullPath.startsWith("/") ? strConvert2AbsFullPath.substring(1, strConvert2AbsFullPath.length()) : strConvert2AbsFullPath;
    }

    @Override // io.dcloud.feature.weex.WXBaseWrapper, io.dcloud.common.DHInterface.IUniNView
    public void loadTemplate(JSONObject jSONObject) {
        JSONObject jSONObjectOptJSONObject;
        try {
            this.jsonObject = jSONObject;
            this.mPath = jSONObject.optString("js");
            JSONObject jSONObject2 = this.jsonObject;
            if (jSONObject2 != null && jSONObject2.has("data") && (jSONObjectOptJSONObject = this.jsonObject.optJSONObject("data")) != null) {
                this.mFontSize = jSONObjectOptJSONObject.optInt("defaultFontSize");
                if (jSONObjectOptJSONObject.has("delayRender")) {
                    this.isDelayRender = jSONObjectOptJSONObject.optBoolean("delayRender", this.isDelayRender);
                }
                if (jSONObjectOptJSONObject.has(AbsoluteConst.XML_PATH)) {
                    this.mUniPagePath = jSONObjectOptJSONObject.optString(AbsoluteConst.XML_PATH, "") + ".nvue";
                }
            }
            if (PdrUtil.isNetPath(this.mPath)) {
                return;
            }
            this.mSrcPath = initSrcPath(this.mPath);
            if (this.isPre) {
                render(null, getInitOptions(), getInitStringJsonData());
            } else {
                ThreadPool.self().addThreadTask(new Runnable() { // from class: io.dcloud.feature.weex.WXViewWrapper.8
                    @Override // java.lang.Runnable
                    public void run() throws IOException {
                        InputStream encryptionInputStream;
                        WXViewWrapper wXViewWrapper = WXViewWrapper.this;
                        IWebview iWebview = wXViewWrapper.mWebview;
                        if (iWebview == null || (encryptionInputStream = WebResUtil.getEncryptionInputStream(wXViewWrapper.mSrcPath, iWebview.obtainApp())) == null) {
                            return;
                        }
                        try {
                            String str = new String(IOUtil.toString(encryptionInputStream));
                            WXViewWrapper wXViewWrapper2 = WXViewWrapper.this;
                            if (wXViewWrapper2.isService) {
                                str = wXViewWrapper2.getAllUniService(str);
                            }
                            Message message = new Message();
                            message.obj = str;
                            message.what = 1000;
                            if (WXViewWrapper.this.isService || WeexInstanceMgr.self().isUniServiceCreated(WXViewWrapper.this.mWebview.obtainApp())) {
                                if (WeexInstanceMgr.self().isJsFrameworkReady()) {
                                    WXViewWrapper wXViewWrapper3 = WXViewWrapper.this;
                                    if (!wXViewWrapper3.isDelayRender || wXViewWrapper3.isService || wXViewWrapper3.isFrameShow) {
                                        WXViewWrapper.this.mHandler.sendMessage(message);
                                    } else if (!WXViewWrapper.this.mRenderCaches.contains(message)) {
                                        WXViewWrapper.this.mRenderCaches.add(message);
                                    }
                                } else if (!WXViewWrapper.this.mRenderCaches.contains(message)) {
                                    WXViewWrapper.this.mRenderCaches.add(message);
                                }
                            } else if (!WXViewWrapper.this.mWaitServiceRenderList.contains(message)) {
                                WXViewWrapper.this.mWaitServiceRenderList.add(message);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        IOUtil.close(encryptionInputStream);
                    }
                }, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // io.dcloud.common.DHInterface.IEventCallback
    public Object onCallBack(String str, Object obj) {
        if (PdrUtil.isEquals(str, AbsoluteConst.EVENTS_CLOSE) && (obj instanceof IWebview)) {
            ((AdaFrameView) ((IWebview) obj).obtainFrameView()).removeFrameViewListener(this);
            WeexInstanceMgr.self().removeWeexView(this.mWxId);
            onDestroy();
            return null;
        }
        if ((!PdrUtil.isEquals(str, AbsoluteConst.EVENTS_SHOW_ANIMATION_END) && !PdrUtil.isEquals(str, AbsoluteConst.EVENTS_CHILD_INITIALIZE_SHOW)) || this.isFrameShow) {
            return null;
        }
        this.isFrameShow = true;
        WXSDKInstance wXSDKInstance = this.mWXSDKInstance;
        if (wXSDKInstance != null) {
            wXSDKInstance.onShowAnimationEnd();
        }
        if (!this.isDelayRender) {
            return null;
        }
        runDelayedRenderCaches(this.mRenderCaches);
        return null;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        int i;
        String str;
        super.onConfigurationChanged(configuration);
        if (this.mWXSDKInstance == null || this.mWebview.getActivity() == null) {
            return;
        }
        int rotation = this.mWebview.getActivity().getWindowManager().getDefaultDisplay().getRotation();
        if (rotation == 1) {
            i = 90;
            str = "landscape";
        } else if (rotation == 2) {
            i = 180;
            str = "portraitReverse";
        } else if (rotation != 3) {
            str = "portrait";
            i = 0;
        } else {
            i = -90;
            str = "landscapeReverse";
        }
        WXUtils.getCache().evictAll();
        HashMap map = new HashMap();
        map.put("value", str);
        map.put("orientation", Integer.valueOf(i));
        this.mWXSDKInstance.fireGlobalEventCallback("orientationchange", map);
        if (this.isCompilerWithUniapp) {
            this.mWXSDKInstance.setInstanceViewPortWidth(this.mWebview.obtainApp().getInt(0) / this.mWebview.getScale(), true);
        }
    }

    @Override // io.dcloud.feature.weex.WXBaseWrapper, io.dcloud.common.DHInterface.IUniNView
    public void onDestroy() {
        super.onDestroy();
        WeexInstanceMgr.self().unWXStatisticsCallBack(this);
        this.mWebview = null;
    }

    @Override // com.taobao.weex.IWXRenderListener
    public void onException(WXSDKInstance wXSDKInstance, String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Logger.e(this.TAG, "onException--errCode=" + str + "    msg=" + str2);
        WXAnalyzerDelegate wXAnalyzerDelegate = this.mWXAnaly;
        if (wXAnalyzerDelegate != null) {
            wXAnalyzerDelegate.onException(wXSDKInstance, str, str2);
        }
    }

    @Override // io.dcloud.feature.weex.WeexInstanceMgr.IWXStatisticsCallBack
    public void onJsFrameworkReady() {
        runDelayedRenderCaches(this.mRenderCaches);
        if (this.mWXSDKInstance != null) {
            initViewPortWidth(true);
            initFlexDirection();
        }
    }

    @Override // io.dcloud.feature.weex.WXBaseWrapper
    public void onReady() {
        this.isReady = true;
        runFireCache();
        if (this.isService) {
            if (!SDK.isUniMPSDK()) {
                WeexInstanceMgr.self().doForFeature(IMgr.MgrType.FeatureMgr, 10, new Object[]{this.mWebview.obtainApp(), WeexInstanceMgr.self().getUniMPFeature(), "onUniMPInit", new Object[0]});
            }
            LogDetail logDetail = this.mServiceLogDetail;
            if (logDetail != null) {
                logDetail.taskEnd();
                LogDetail logDetail2 = this.mServiceLogDetail;
                WXDotDataUtil.setValue(logDetail2.info.taskName, Long.valueOf(logDetail2.time.execTime));
            }
        }
    }

    @Override // io.dcloud.feature.weex.WXBaseWrapper
    protected void onRefresh() {
        IWebview iWebview = this.mWebview;
        if (iWebview != null) {
            ((AdaFrameView) iWebview.obtainFrameView()).dispatchFrameViewEvents(AbsoluteConst.EVENTS_PULL_DOWN_EVENT, 3);
            ((AdaFrameView) this.mWebview.obtainFrameView()).dispatchFrameViewEvents(AbsoluteConst.EVENTS_PULL_TO_REFRESH, 3);
        }
    }

    @Override // com.taobao.weex.IWXRenderListener
    public void onRefreshSuccess(WXSDKInstance wXSDKInstance, int i, int i2) {
    }

    @Override // com.taobao.weex.IWXRenderListener
    public void onRenderSuccess(WXSDKInstance wXSDKInstance, int i, int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IWebview iWebview;
        WXAnalyzerDelegate wXAnalyzerDelegate = this.mWXAnaly;
        if (wXAnalyzerDelegate != null) {
            wXAnalyzerDelegate.onWeexRenderSuccess(wXSDKInstance);
        }
        if (this.isService || (iWebview = this.mWebview) == null) {
            return;
        }
        if (iWebview instanceof AdaWebview) {
            ((AdaWebview) iWebview).dispatchWebviewStateEvent(1, this.mPath);
        }
        String strObtainConfigProperty = this.mWebview.obtainApp().obtainConfigProperty(IApp.ConfigProperty.CONFIG_AUTOCLOSE);
        if (PdrUtil.isEmpty(strObtainConfigProperty)) {
            strObtainConfigProperty = AbsoluteConst.TRUE;
        }
        if (Boolean.parseBoolean(strObtainConfigProperty)) {
            this.mWebview.obtainFrameView().obtainWindowMgr().processEvent(IMgr.MgrType.WindowMgr, 11, this.mWebview.obtainFrameView());
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        IWebview iWebview = this.mWebview;
        if (iWebview == null || !(iWebview instanceof AdaUniWebView)) {
            return;
        }
        try {
            ((AdaUniWebView) iWebview).updateScreenAndDisplay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.taobao.weex.IWXRenderListener
    public void onViewCreated(WXSDKInstance wXSDKInstance, View view) {
        WXAnalyzerDelegate wXAnalyzerDelegate = this.mWXAnaly;
        if (wXAnalyzerDelegate != null) {
            wXAnalyzerDelegate.onWeexViewCreated(wXSDKInstance, view);
        }
        this.mWXSDKView = view;
        addView(this.mWXSDKView, new LinearLayout.LayoutParams(-1, -1));
        this.mWXSDKView.layout(0, 0, getWidth(), getHeight());
        if (isFocusableInTouchMode()) {
            this.mWXSDKView.setFocusable(true);
            this.mWXSDKView.setFocusableInTouchMode(true);
        }
        addScrollListener(null);
    }

    @Override // io.dcloud.feature.weex.WXBaseWrapper
    public void recoveryInstance() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        View currentFocus;
        if (this.mWXSDKView != null) {
            if (!this.isChlid && (currentFocus = this.mWebview.getActivity().getCurrentFocus()) != null) {
                DeviceInfo.hideIME(currentFocus);
            }
            this.mWXSDKView.clearFocus();
            clearFocus();
            removeView(this.mWXSDKView);
            this.mWXSDKView = null;
            this.isPre = false;
        }
        super.recoveryInstance();
        this.isFrameShow = false;
    }

    @Override // io.dcloud.feature.weex.WXBaseWrapper, io.dcloud.common.DHInterface.IUniNView
    public void reload() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.time == 0 || System.currentTimeMillis() - this.time >= 600) {
            this.time = System.currentTimeMillis();
            recoveryInstance();
            this.isFrameShow = true;
            if (TextUtils.isEmpty(this.mPath)) {
                return;
            }
            loadTemplate(this.jsonObject);
        }
    }

    public void runDelayedRenderCaches(List<Message> list) {
        if (list.size() > 0) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.mHandler.sendMessage(list.get(size));
            }
            list.clear();
        }
    }

    @Override // android.view.View
    public void setFocusable(int i) {
        super.setFocusable(i);
        View view = this.mWXSDKView;
        if (view != null) {
            view.setFocusable(i);
        }
    }

    @Override // android.view.View
    public void setFocusableInTouchMode(boolean z) {
        super.setFocusableInTouchMode(z);
        View view = this.mWXSDKView;
        if (view != null) {
            view.setFocusableInTouchMode(z);
        }
    }

    @Override // io.dcloud.feature.weex.WXBaseWrapper, io.dcloud.common.DHInterface.IUniNView
    public void titleNViewRefresh() {
        addScrollListener(null);
    }
}
