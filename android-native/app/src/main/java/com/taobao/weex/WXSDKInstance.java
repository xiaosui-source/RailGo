package com.taobao.weex;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import androidx.collection.ArrayMap;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.adapter.IDrawableLoader;
import com.taobao.weex.adapter.IWXConfigAdapter;
import com.taobao.weex.adapter.IWXHttpAdapter;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.adapter.IWXJscProcessManager;
import com.taobao.weex.adapter.IWXUserTrackAdapter;
import com.taobao.weex.adapter.URIAdapter;
import com.taobao.weex.appfram.websocket.IWebSocketAdapter;
import com.taobao.weex.bridge.EventResult;
import com.taobao.weex.bridge.NativeInvokeHelper;
import com.taobao.weex.bridge.SimpleJSCallback;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.bridge.WXModuleManager;
import com.taobao.weex.bridge.WXParams;
import com.taobao.weex.common.Constants;
import com.taobao.weex.common.Destroyable;
import com.taobao.weex.common.OnWXScrollListener;
import com.taobao.weex.common.RenderTypes;
import com.taobao.weex.common.WXErrorCode;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.common.WXPerformance;
import com.taobao.weex.common.WXRefreshData;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.common.WXRequest;
import com.taobao.weex.dom.WXEvent;
import com.taobao.weex.http.WXHttpUtil;
import com.taobao.weex.instance.InstanceOnFireEventInterceptor;
import com.taobao.weex.layout.ContentBoxMeasurement;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.performance.WXStateRecord;
import com.taobao.weex.performance.WhiteScreenUtils;
import com.taobao.weex.render.WXAbstractRenderContainer;
import com.taobao.weex.tracing.WXTracing;
import com.taobao.weex.ui.action.GraphicActionAddElement;
import com.taobao.weex.ui.component.NestedContainer;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXEmbed;
import com.taobao.weex.ui.flat.FlatGUIContext;
import com.taobao.weex.ui.view.WXScrollView;
import com.taobao.weex.utils.Trace;
import com.taobao.weex.utils.WXExceptionUtils;
import com.taobao.weex.utils.WXFileUtils;
import com.taobao.weex.utils.WXJsonUtils;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXReflectionUtils;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import com.taobao.weex.utils.cache.RegisterCache;
import com.taobao.weex.utils.tools.LogDetail;
import com.taobao.weex.utils.tools.TimeCalculator;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.ErrorDialogUtil;
import io.dcloud.feature.uniapp.AbsSDKInstance;
import io.dcloud.feature.uniapp.adapter.AbsURIAdapter;
import io.dcloud.feature.weex.WeexInstanceMgr;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXSDKInstance implements AbsSDKInstance {
    public static String ACTION_DEBUG_INSTANCE_REFRESH = "DEBUG_INSTANCE_REFRESH";
    public static String ACTION_INSTANCE_RELOAD = "INSTANCE_RELOAD";
    public static final String BUNDLE_URL = "bundleUrl";
    private static final String SOURCE_TEMPLATE_BASE64_MD5 = "templateSourceBase64MD5";
    public static String requestUrl = "requestUrl";
    static int sScreenHeight = -1;
    public WXBridgeManager.BundType bundleType;
    private List<JSONObject> componentsInfoExceedGPULimit;
    private boolean createInstanceHeartBeat;
    private boolean enableFullScreenHeight;
    private boolean enableLayerType;
    List<FrameViewEventListener> frameViewEventListeners;
    private boolean hasException;
    public PriorityQueue<WXEmbed> hiddenEmbeds;
    private Map<String, GraphicActionAddElement> inactiveAddElementAction;
    private boolean isCommit;
    private boolean isDestroy;
    private boolean isFrameShow;
    private boolean isImmersive;
    public boolean isNewFsEnd;
    private boolean isOnSizeChangedRender;
    private boolean isPreDownLoad;
    private boolean isPreInit;
    private volatile boolean isPreRenderMode;
    private boolean isRenderSuccess;
    private boolean isViewDisAppear;
    private WXInstanceApm mApmForInstance;
    private boolean mAutoAdjustDeviceWidth;
    private String mBundleUrl;
    private ComponentObserver mComponentObserver;
    private Map<String, String> mContainerInfo;
    private Map<Long, ContentBoxMeasurement> mContentBoxMeasurements;
    protected Context mContext;
    private boolean mCreateInstance;
    private boolean mCurrentGround;
    private CustomFontNetworkHandler mCustomFontNetworkHandler;
    private int mDefaultFontSize;
    private boolean mDisableSkipFrameworkInit;
    public boolean mEnd;
    public int mExecJSTraceId;
    private FlatGUIContext mFlatGUIContext;
    private WXGlobalEventReceiver mGlobalEventReceiver;
    private HashMap<String, List<String>> mGlobalEvents;
    public boolean mHasCreateFinish;
    private WXHttpListener mHttpListener;
    private ImageNetworkHandler mImageNetworkHandler;
    private final String mInstanceId;
    private List<InstanceOnFireEventInterceptor> mInstanceOnFireEventInterceptorList;
    private float mInstanceViewPortWidth;
    private WXRefreshData mLastRefreshData;
    private List<String> mLayerOverFlowListeners;
    private int mMaxDeepLayer;
    private NativeInvokeHelper mNativeInvokeHelper;
    private boolean mNeedReLoad;
    private boolean mNeedValidate;
    private NestedInstanceInterceptor mNestedInstanceInterceptor;
    IWXInstanceContainerOnSizeListener mOnSizeListener;
    private Context mOriginalContext;
    private WXSDKInstance mParentInstance;
    private long mRefreshStartTime;
    private WXAbstractRenderContainer mRenderContainer;
    private IWXRenderListener mRenderListener;
    public long mRenderStartNanos;
    public long mRenderStartTime;
    private WXRenderStrategy mRenderStrategy;
    private String mRenderType;
    private boolean mRendered;
    private WXComponent mRootComp;
    private ScrollView mScrollView;
    private IWXStatisticsListener mStatisticsListener;
    private StreamNetworkHandler mStreamNetworkHandler;
    public TimeCalculator mTimeCalculator;
    private String mUniPagePath;
    private boolean mUseScroller;
    private IWXUserTrackAdapter mUserTrackAdapter;
    private Map<String, Serializable> mUserTrackParams;
    private List<OnInstanceVisibleListener> mVisibleListeners;
    private List<ActionBarHandler> mWXActionbarHandlers;
    private List<OnBackPressedHandler> mWXBackPressedHandlers;
    private WXPerformance mWXPerformance;
    private List<OnWXScrollListener> mWXScrollListeners;
    private WXScrollView.WXScrollViewListener mWXScrollViewListener;
    private int maxHiddenEmbedsNum;
    public long[] measureTimes;
    public String[] mwxDims;
    public Map<String, List<String>> responseHeaders;
    public WeakReference<String> templateRef;
    private boolean trackComponent;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface ActionBarHandler {
        boolean onSupportNavigateUp();
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface CustomFontNetworkHandler {
        String fetchLocal(String str);
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface FrameViewEventListener {
        void onShowAnimationEnd();
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface ImageNetworkHandler {
        String fetchLocal(String str);
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface NestedInstanceInterceptor {
        void onCreateNestInstance(WXSDKInstance wXSDKInstance, NestedContainer nestedContainer);
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface OnBackPressedHandler {
        boolean onBackPressed();
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface OnInstanceVisibleListener {
        void onAppear();

        void onDisappear();
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface StreamNetworkHandler {
        String fetchLocal(String str);
    }

    public WXSDKInstance(Context context) {
        this.mEnd = false;
        this.mHasCreateFinish = false;
        this.mBundleUrl = "";
        this.mUniPagePath = "";
        this.isDestroy = false;
        this.hasException = false;
        this.isRenderSuccess = false;
        this.createInstanceHeartBeat = false;
        this.isCommit = false;
        this.mGlobalEventReceiver = null;
        this.enableLayerType = true;
        this.mNeedValidate = false;
        this.mNeedReLoad = false;
        this.mUseScroller = false;
        this.mInstanceViewPortWidth = 750.0f;
        this.enableFullScreenHeight = false;
        this.mFlatGUIContext = new FlatGUIContext();
        this.isNewFsEnd = false;
        this.componentsInfoExceedGPULimit = new LinkedList();
        this.mExecJSTraceId = WXTracing.nextId();
        this.isViewDisAppear = false;
        this.mwxDims = new String[5];
        this.measureTimes = new long[5];
        this.responseHeaders = new HashMap();
        this.mRenderStrategy = WXRenderStrategy.APPEND_ASYNC;
        this.mDisableSkipFrameworkInit = false;
        this.mRenderType = RenderTypes.RENDER_TYPE_NATIVE;
        this.mAutoAdjustDeviceWidth = WXEnvironment.AUTO_ADJUST_ENV_DEVICE_WIDTH;
        this.mCurrentGround = false;
        this.inactiveAddElementAction = new HashMap();
        this.mContentBoxMeasurements = new ArrayMap();
        this.maxHiddenEmbedsNum = -1;
        this.mVisibleListeners = new ArrayList();
        this.isPreInit = false;
        this.isPreDownLoad = false;
        this.mHttpListener = null;
        this.mCreateInstance = true;
        this.mGlobalEvents = new HashMap<>();
        this.mDefaultFontSize = 32;
        this.frameViewEventListeners = new ArrayList();
        this.isFrameShow = false;
        this.isImmersive = true;
        this.isOnSizeChangedRender = true;
        this.mInstanceId = WXSDKManager.getInstance().generateInstanceId();
        init(context);
    }

    private String assembleFilePath(Uri uri) {
        return (uri == null || uri.getPath() == null) ? "" : uri.getPath().replaceFirst("/", "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkWhiteScreen() {
        if (!this.isDestroy && WhiteScreenUtils.doWhiteScreenCheck() && WhiteScreenUtils.isWhiteScreen(this)) {
            WXErrorCode wXErrorCode = WXErrorCode.WX_ERROR_WHITE_SCREEN;
            HashMap map = new HashMap(1);
            String strTakeViewTreeSnapShot = WhiteScreenUtils.takeViewTreeSnapShot(this);
            if (strTakeViewTreeSnapShot == null) {
                strTakeViewTreeSnapShot = "null viewTreeMsg";
            }
            map.put("viewTree", strTakeViewTreeSnapShot);
            map.put("weexCoreThreadStackTrace", WXBridgeManager.getInstance().getWeexCoreThreadStackTrace());
            for (Map.Entry<String, String> entry : WXStateRecord.getInstance().getStateInfo().entrySet()) {
                map.put(entry.getKey(), entry.getValue());
            }
            WXExceptionUtils.commitCriticalExceptionRT(getInstanceId(), wXErrorCode, "checkEmptyScreen", wXErrorCode.getErrorMsg(), map);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void destroyView(View view) {
        try {
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    destroyView(viewGroup.getChildAt(i));
                }
                viewGroup.removeViews(0, ((ViewGroup) view).getChildCount());
                WXReflectionUtils.setValue(view, "mChildrenCount", 0);
            }
            if (view instanceof Destroyable) {
                ((Destroyable) view).destroy();
            }
        } catch (Exception e) {
            WXLogUtils.e("WXSDKInstance destroyView Exception: ", e);
        }
    }

    private void ensureRenderArchor() {
        if (this.mRenderContainer != null || getContext() == null) {
            return;
        }
        setRenderContainer(new RenderContainer(getContext()));
        this.mRenderContainer.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.mRenderContainer.setBackgroundColor(0);
        this.mRenderContainer.setSDKInstance(this);
        this.mRenderContainer.addOnLayoutChangeListener(this);
    }

    private boolean isDataRender() {
        return getRenderStrategy() == WXRenderStrategy.DATA_RENDER_BINARY || getRenderStrategy() == WXRenderStrategy.DATA_RENDER;
    }

    private static boolean isDisableSkipFrameworkInDataRender() {
        IWXConfigAdapter wxConfigAdapter = WXSDKManager.getInstance().getWxConfigAdapter();
        if (wxConfigAdapter == null) {
            return false;
        }
        return AbsoluteConst.TRUE.equals(wxConfigAdapter.getConfig("wxeagle", "disable_skip_framework_init", AbsoluteConst.FALSE));
    }

    private void onInterceptInstanceEvent(String str, String str2, String str3, Map<String, Object> map, Map<String, Object> map2) {
        List<InstanceOnFireEventInterceptor> list = this.mInstanceOnFireEventInterceptorList;
        if (list == null) {
            return;
        }
        Iterator<InstanceOnFireEventInterceptor> it = list.iterator();
        while (it.hasNext()) {
            it.next().onInterceptFireEvent(str, str2, str3, map, map2);
        }
    }

    private void renderByUrlInternal(String str, String str2, Map<String, Object> map, String str3, WXRenderStrategy wXRenderStrategy) {
        LogDetail logDetailCreateLogDetail = this.mTimeCalculator.createLogDetail("renderByUrlInternal");
        logDetailCreateLogDetail.taskStart();
        ensureRenderArchor();
        String strWrapPageName = wrapPageName(str, str2);
        this.mBundleUrl = str2;
        this.mRenderStrategy = wXRenderStrategy;
        if (WXSDKManager.getInstance().getValidateProcessor() != null) {
            this.mNeedValidate = WXSDKManager.getInstance().getValidateProcessor().needValidate(this.mBundleUrl);
        }
        if (map == null) {
            map = new HashMap<>();
        }
        Map<String, Object> map2 = map;
        if (!map2.containsKey("bundleUrl")) {
            map2.put("bundleUrl", str2);
        }
        getWXPerformance().pageName = strWrapPageName;
        this.mApmForInstance.doInit();
        this.mApmForInstance.setPageName(strWrapPageName);
        Uri uri = Uri.parse(str2);
        if (uri != null && TextUtils.equals(uri.getScheme(), "file")) {
            this.mApmForInstance.onStage(WXInstanceApm.KEY_PAGE_STAGES_DOWN_BUNDLE_START);
            String strLoadFileOrAsset = WXFileUtils.loadFileOrAsset(assembleFilePath(uri), this.mContext);
            this.mApmForInstance.onStage(WXInstanceApm.KEY_PAGE_STAGES_DOWN_BUNDLE_END);
            render(strWrapPageName, strLoadFileOrAsset, map2, str3, wXRenderStrategy);
            return;
        }
        WXRenderStrategy wXRenderStrategy2 = wXRenderStrategy;
        if (uri != null && uri.getPath() != null && uri.getPath().endsWith(".wlasm")) {
            wXRenderStrategy2 = WXRenderStrategy.DATA_RENDER_BINARY;
        }
        IWXHttpAdapter iWXHttpAdapter = WXSDKManager.getInstance().getIWXHttpAdapter();
        WXRequest wXRequest = new WXRequest();
        String string = rewriteUri(Uri.parse(str2), AbsURIAdapter.BUNDLE).toString();
        wXRequest.url = string;
        if (TextUtils.isEmpty(string)) {
            requestUrl = strWrapPageName;
        } else {
            requestUrl = wXRequest.url;
        }
        if (wXRequest.paramMap == null) {
            wXRequest.paramMap = new HashMap();
        }
        wXRequest.instanceId = getInstanceId();
        wXRequest.paramMap.put(WXHttpUtil.KEY_USER_AGENT, WXHttpUtil.assembleUserAgent(this.mContext, WXEnvironment.getConfig()));
        wXRequest.paramMap.put("isBundleRequest", AbsoluteConst.TRUE);
        WXHttpListener wXHttpListener = new WXHttpListener(this, strWrapPageName, map2, str3, wXRenderStrategy2, System.currentTimeMillis());
        this.mHttpListener = wXHttpListener;
        wXHttpListener.isPreDownLoadMode = this.isPreDownLoad;
        wXHttpListener.setSDKInstance(this);
        this.mApmForInstance.onStage(WXInstanceApm.KEY_PAGE_STAGES_DOWN_BUNDLE_START);
        iWXHttpAdapter.sendRequest(wXRequest, this.mHttpListener);
        logDetailCreateLogDetail.taskEnd();
    }

    private void renderInternal(String str, String str2, Map<String, Object> map, String str3, WXRenderStrategy wXRenderStrategy) {
        if (this.mRendered || TextUtils.isEmpty(str2)) {
            return;
        }
        renderInternal(str, new Script(str2), map, str3, wXRenderStrategy);
    }

    private void setDeviceDisplay(float f, float f2, float f3) {
        WXBridgeManager.getInstance().setDeviceDisplay(getInstanceId(), f, f2, f3);
    }

    private String wrapPageName(String str, String str2) {
        if (!TextUtils.equals(str, "default")) {
            return str;
        }
        WXExceptionUtils.degradeUrl = str2;
        try {
            Uri uri = Uri.parse(str2);
            if (uri != null) {
                Uri.Builder builder = new Uri.Builder();
                builder.scheme(uri.getScheme());
                builder.authority(uri.getAuthority());
                builder.path(uri.getPath());
                return builder.toString();
            }
        } catch (Exception unused) {
        }
        return str2;
    }

    public void OnVSync() {
        if (WXBridgeManager.getInstance().notifyLayout(getInstanceId())) {
            WXBridgeManager.getInstance().post(new Runnable() { // from class: com.taobao.weex.WXSDKInstance.11
                @Override // java.lang.Runnable
                public void run() {
                    WXBridgeManager.getInstance().forceLayout(WXSDKInstance.this.getInstanceId());
                }
            });
        }
    }

    public void addContentBoxMeasurement(long j, ContentBoxMeasurement contentBoxMeasurement) {
        this.mContentBoxMeasurements.put(Long.valueOf(j), contentBoxMeasurement);
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public void addEventListener(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        List<String> arrayList = this.mGlobalEvents.get(str);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.mGlobalEvents.put(str, arrayList);
        }
        arrayList.add(str2);
    }

    public void addFrameViewEventListener(FrameViewEventListener frameViewEventListener) {
        if (this.frameViewEventListeners.contains(frameViewEventListener)) {
            return;
        }
        this.frameViewEventListeners.add(frameViewEventListener);
    }

    public void addInActiveAddElementAction(String str, GraphicActionAddElement graphicActionAddElement) {
        this.inactiveAddElementAction.put(str, graphicActionAddElement);
    }

    public void addInstanceOnFireEventInterceptor(InstanceOnFireEventInterceptor instanceOnFireEventInterceptor) {
        if (instanceOnFireEventInterceptor == null || getInstanceOnFireEventInterceptorList().contains(instanceOnFireEventInterceptor)) {
            return;
        }
        getInstanceOnFireEventInterceptorList().add(instanceOnFireEventInterceptor);
    }

    public void addLayerOverFlowListener(String str) {
        if (this.mLayerOverFlowListeners == null) {
            this.mLayerOverFlowListeners = new ArrayList();
        }
        this.mLayerOverFlowListeners.add(str);
    }

    public void addOnInstanceVisibleListener(OnInstanceVisibleListener onInstanceVisibleListener) {
        this.mVisibleListeners.add(onInstanceVisibleListener);
    }

    public void addUserTrackParameter(String str, Serializable serializable) {
        if (this.mUserTrackParams == null) {
            this.mUserTrackParams = new ConcurrentHashMap();
        }
        this.mUserTrackParams.put(str, serializable);
    }

    public void callActionAddElementTime(long j) {
        this.mWXPerformance.mActionAddElementSumTime = (int) (r0.mActionAddElementSumTime + j);
    }

    public void callJsTime(long j) {
        if (this.mEnd) {
            return;
        }
        WXPerformance wXPerformance = this.mWXPerformance;
        wXPerformance.fsCallJsTotalTime += j;
        wXPerformance.fsCallJsTotalNum++;
    }

    public boolean checkModuleEventRegistered(String str, WXModule wXModule) {
        List<String> eventCallbacks;
        return (wXModule == null || (eventCallbacks = wXModule.getEventCallbacks(str)) == null || eventCallbacks.size() <= 0) ? false : true;
    }

    public void clearUserTrackParameters() {
        Map<String, Serializable> map = this.mUserTrackParams;
        if (map != null) {
            map.clear();
        }
    }

    public void createInstanceFuncHeartBeat() {
        WXLogUtils.d("createInstanceFuncHeartBeat: " + this.mInstanceId);
        this.createInstanceHeartBeat = true;
    }

    public final WXSDKInstance createNestedInstance(NestedContainer nestedContainer) {
        WXSDKInstance wXSDKInstanceNewNestedInstance = newNestedInstance();
        NestedInstanceInterceptor nestedInstanceInterceptor = this.mNestedInstanceInterceptor;
        if (nestedInstanceInterceptor != null) {
            nestedInstanceInterceptor.onCreateNestInstance(wXSDKInstanceNewNestedInstance, nestedContainer);
        }
        if (wXSDKInstanceNewNestedInstance != null) {
            wXSDKInstanceNewNestedInstance.setComponentObserver(getComponentObserver());
        }
        return wXSDKInstanceNewNestedInstance;
    }

    public synchronized void destroy() {
        if (isDestroy()) {
            return;
        }
        if (this.mParentInstance != null) {
            this.mParentInstance = null;
        }
        this.mApmForInstance.onEnd();
        if (this.mRendered) {
            WXSDKManager.getInstance().destroyInstance(this.mInstanceId);
        }
        if (this.mGlobalEventReceiver != null) {
            getContext().unregisterReceiver(this.mGlobalEventReceiver);
            this.mGlobalEventReceiver = null;
        }
        WXComponent wXComponent = this.mRootComp;
        if (wXComponent != null) {
            wXComponent.destroy();
            this.mRootComp = null;
        }
        WXAbstractRenderContainer wXAbstractRenderContainer = this.mRenderContainer;
        if (wXAbstractRenderContainer != null) {
            destroyView(wXAbstractRenderContainer);
        }
        HashMap<String, List<String>> map = this.mGlobalEvents;
        if (map != null) {
            map.clear();
        }
        if (this.mComponentObserver != null) {
            this.mComponentObserver = null;
        }
        List<String> list = this.mLayerOverFlowListeners;
        if (list != null) {
            list.clear();
        }
        getFlatUIContext().destroy();
        this.mFlatGUIContext = null;
        this.mInstanceOnFireEventInterceptorList = null;
        this.mWXScrollListeners = null;
        this.mWXActionbarHandlers = null;
        this.mWXBackPressedHandlers = null;
        this.mRenderContainer = null;
        this.mNestedInstanceInterceptor = null;
        this.mUserTrackAdapter = null;
        this.mScrollView = null;
        this.mContext = null;
        this.mRenderListener = null;
        this.isDestroy = true;
        this.mStatisticsListener = null;
        Map<String, List<String>> map2 = this.responseHeaders;
        if (map2 != null) {
            map2.clear();
        }
        if (this.templateRef != null) {
            this.templateRef = null;
        }
        Map<Long, ContentBoxMeasurement> map3 = this.mContentBoxMeasurements;
        if (map3 != null) {
            map3.clear();
        }
        this.mWXPerformance.afterInstanceDestroy(this.mInstanceId);
        WXBridgeManager.getInstance().post(new Runnable() { // from class: com.taobao.weex.WXSDKInstance.8
            @Override // java.lang.Runnable
            public void run() {
                WXBridgeManager.getInstance().onInstanceClose(WXSDKInstance.this.getInstanceId());
                WXSDKInstance.this.inactiveAddElementAction.clear();
            }
        });
        WXBridgeManager.getInstance().postDelay(new Runnable() { // from class: com.taobao.weex.WXSDKInstance.9
            @Override // java.lang.Runnable
            public void run() {
                WXSDKManager.getInstance().getAllInstanceMap().remove(WXSDKInstance.this.mInstanceId);
            }
        }, 1000L);
    }

    public void enableLayerType(boolean z) {
        this.enableLayerType = z;
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public void fireEvent(String str, String str2, Map<String, Object> map, Map<String, Object> map2, List<Object> list) {
        fireEvent(str, str2, map, map2, list, null);
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public void fireGlobalEventCallback(String str, Map<String, Object> map) {
        List<String> list = this.mGlobalEvents.get(str);
        if (list != null) {
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                WXSDKManager.getInstance().callback(this.mInstanceId, it.next(), map, true);
            }
        }
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public void fireModuleEvent(String str, WXModule wXModule, Map<String, Object> map) {
        if (TextUtils.isEmpty(str) || wXModule == null) {
            return;
        }
        HashMap map2 = new HashMap();
        map2.put("type", str);
        map2.put("module", wXModule.getModuleName());
        map2.put("data", map);
        List<String> eventCallbacks = wXModule.getEventCallbacks(str);
        if (eventCallbacks != null) {
            for (String str2 : eventCallbacks) {
                SimpleJSCallback simpleJSCallback = new SimpleJSCallback(this.mInstanceId, str2);
                if (wXModule.isOnce(str2)) {
                    simpleJSCallback.invoke(map2);
                } else {
                    simpleJSCallback.invokeAndKeepAlive(map2);
                }
            }
        }
    }

    public void firstScreenCreateInstanceTime(long j) {
        if (this.mCreateInstance) {
            this.mWXPerformance.firstScreenJSFExecuteTime = j - this.mRenderStartTime;
            this.mCreateInstance = false;
        }
    }

    public WXInstanceApm getApmForInstance() {
        return this.mApmForInstance;
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public String getBundleUrl() {
        return this.mBundleUrl;
    }

    public ComponentObserver getComponentObserver() {
        return this.mComponentObserver;
    }

    public List<JSONObject> getComponentsExceedGPULimit() {
        return this.componentsInfoExceedGPULimit;
    }

    public Map<String, String> getContainerInfo() {
        return this.mContainerInfo;
    }

    public View getContainerView() {
        return this.mRenderContainer;
    }

    public ContentBoxMeasurement getContentBoxMeasurement(long j) {
        return this.mContentBoxMeasurements.get(Long.valueOf(j));
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public Context getContext() {
        return this.mContext;
    }

    public CustomFontNetworkHandler getCustomFontNetworkHandler() {
        return this.mCustomFontNetworkHandler;
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public int getDefaultFontSize() {
        return this.mDefaultFontSize;
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public IDrawableLoader getDrawableLoader() {
        return WXSDKManager.getInstance().getDrawableLoader();
    }

    public FlatGUIContext getFlatUIContext() {
        return this.mFlatGUIContext;
    }

    public ImageNetworkHandler getImageNetworkHandler() {
        return this.mImageNetworkHandler;
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public IWXImgLoaderAdapter getImgLoaderAdapter() {
        return WXSDKManager.getInstance().getIWXImgLoaderAdapter();
    }

    public GraphicActionAddElement getInActiveAddElementAction(String str) {
        return this.inactiveAddElementAction.get(str);
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public String getInstanceId() {
        return this.mInstanceId;
    }

    public List<InstanceOnFireEventInterceptor> getInstanceOnFireEventInterceptorList() {
        if (this.mInstanceOnFireEventInterceptorList == null) {
            this.mInstanceOnFireEventInterceptorList = new ArrayList();
        }
        return this.mInstanceOnFireEventInterceptorList;
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public int getInstanceViewPortWidth() {
        return Math.round(this.mInstanceViewPortWidth);
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public float getInstanceViewPortWidthWithFloat() {
        return this.mInstanceViewPortWidth;
    }

    public List<String> getLayerOverFlowListeners() {
        return this.mLayerOverFlowListeners;
    }

    public int getMaxDeepLayer() {
        return this.mMaxDeepLayer;
    }

    public int getMaxHiddenEmbedsNum() {
        return this.maxHiddenEmbedsNum;
    }

    public NativeInvokeHelper getNativeInvokeHelper() {
        return this.mNativeInvokeHelper;
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public Context getOriginalContext() {
        return this.mOriginalContext;
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public WXSDKInstance getParentInstance() {
        return this.mParentInstance;
    }

    public int getRenderContainerPaddingLeft() {
        WXAbstractRenderContainer wXAbstractRenderContainer = this.mRenderContainer;
        if (wXAbstractRenderContainer != null) {
            return wXAbstractRenderContainer.getPaddingLeft();
        }
        return 0;
    }

    public int getRenderContainerPaddingRight() {
        WXAbstractRenderContainer wXAbstractRenderContainer = this.mRenderContainer;
        if (wXAbstractRenderContainer != null) {
            return wXAbstractRenderContainer.getPaddingRight();
        }
        return 0;
    }

    public int getRenderContainerPaddingTop() {
        WXAbstractRenderContainer wXAbstractRenderContainer = this.mRenderContainer;
        if (wXAbstractRenderContainer != null) {
            return wXAbstractRenderContainer.getPaddingTop();
        }
        return 0;
    }

    public WXRenderStrategy getRenderStrategy() {
        return this.mRenderStrategy;
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public String getRenderType() {
        return this.mRenderType;
    }

    public WXComponent getRootComponent() {
        return this.mRootComp;
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public View getRootView() {
        WXComponent wXComponent = this.mRootComp;
        if (wXComponent == null) {
            return null;
        }
        return wXComponent.getRealView();
    }

    public ScrollView getScrollView() {
        return this.mScrollView;
    }

    @Deprecated
    public WXScrollView.WXScrollViewListener getScrollViewListener() {
        return this.mWXScrollViewListener;
    }

    public StreamNetworkHandler getStreamNetworkHandler() {
        return this.mStreamNetworkHandler;
    }

    public String getTemplate() {
        WeakReference<String> weakReference = this.templateRef;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    public String getTemplateInfo() throws UnsupportedEncodingException {
        String template = getTemplate();
        if (template == null) {
            return " template md5 null ,httpHeader:" + JSONObject.toJSONString(this.responseHeaders);
        }
        if (TextUtils.isEmpty(template)) {
            return " template md5  length 0 ,httpHeader" + JSONObject.toJSONString(this.responseHeaders);
        }
        try {
            byte[] bytes = template.getBytes("UTF-8");
            String strMd5 = WXFileUtils.md5(bytes);
            String strBase64Md5 = WXFileUtils.base64Md5(bytes);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            arrayList.add(strMd5);
            arrayList2.add(strBase64Md5);
            this.responseHeaders.put("templateSourceMD5", arrayList);
            this.responseHeaders.put(SOURCE_TEMPLATE_BASE64_MD5, arrayList2);
            return " template md5 " + strMd5 + " length " + bytes.length + " base64 md5 " + strBase64Md5 + " response header " + JSONObject.toJSONString(this.responseHeaders);
        } catch (Exception unused) {
            return "template md5 getBytes error";
        }
    }

    public Context getUIContext() {
        return this.mContext;
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public URIAdapter getURIAdapter() {
        return WXSDKManager.getInstance().getURIAdapter();
    }

    public String getUniPagePath() {
        return this.mUniPagePath;
    }

    public Map<String, Serializable> getUserTrackParams() {
        return this.mUserTrackParams;
    }

    public IWXHttpAdapter getWXHttpAdapter() {
        return WXSDKManager.getInstance().getIWXHttpAdapter();
    }

    public WXPerformance getWXPerformance() {
        return this.mWXPerformance;
    }

    public synchronized List<OnWXScrollListener> getWXScrollListeners() {
        return this.mWXScrollListeners;
    }

    public IWXStatisticsListener getWXStatisticsListener() {
        return this.mStatisticsListener;
    }

    public IWebSocketAdapter getWXWebSocketAdapter() {
        return WXSDKManager.getInstance().getIWXWebSocketAdapter();
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public int getWeexHeight() {
        WXAbstractRenderContainer wXAbstractRenderContainer = this.mRenderContainer;
        if (wXAbstractRenderContainer == null) {
            return 0;
        }
        return wXAbstractRenderContainer.getHeight();
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public int getWeexWidth() {
        WXAbstractRenderContainer wXAbstractRenderContainer = this.mRenderContainer;
        if (wXAbstractRenderContainer == null) {
            return 0;
        }
        return wXAbstractRenderContainer.getWidth();
    }

    public void init(Context context) {
        RegisterCache.getInstance().idle(true);
        this.mContext = context;
        this.mContainerInfo = new HashMap(4);
        this.mNativeInvokeHelper = new NativeInvokeHelper(this.mInstanceId);
        if (this.mWXPerformance == null) {
            this.mWXPerformance = new WXPerformance(this.mInstanceId);
        }
        if (this.mApmForInstance == null) {
            this.mApmForInstance = new WXInstanceApm(this.mInstanceId);
        }
        WXPerformance wXPerformance = this.mWXPerformance;
        wXPerformance.WXSDKVersion = WXEnvironment.WXSDK_VERSION;
        wXPerformance.JSLibInitTime = WXEnvironment.sJSLibInitTime;
        this.mUserTrackAdapter = WXSDKManager.getInstance().getIWXUserTrackAdapter();
        WXSDKManager.getInstance().getAllInstanceMap().put(this.mInstanceId, this);
        this.mContainerInfo.put(WXInstanceApm.KEY_PAGE_PROPERTIES_CONTAINER_NAME, context instanceof Activity ? context.getClass().getSimpleName() : "unKnowContainer");
        this.mContainerInfo.put(WXInstanceApm.KEY_PAGE_PROPERTIES_INSTANCE_TYPE, "page");
        this.mDisableSkipFrameworkInit = isDisableSkipFrameworkInDataRender();
        this.mTimeCalculator = new TimeCalculator(this);
    }

    public boolean isAutoAdjustDeviceWidth() {
        return this.mAutoAdjustDeviceWidth;
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public boolean isCompilerWithUniapp() {
        return false;
    }

    public boolean isContentMd5Match() throws UnsupportedEncodingException {
        Map<String, List<String>> map = this.responseHeaders;
        if (map == null) {
            return true;
        }
        List<String> list = map.get("Content-Md5");
        if (list == null) {
            list = this.responseHeaders.get("content-md5");
        }
        if (list != null && list.size() > 0) {
            String str = list.get(0);
            List<String> list2 = this.responseHeaders.get(SOURCE_TEMPLATE_BASE64_MD5);
            if (list2 == null) {
                getTemplateInfo();
                list2 = this.responseHeaders.get(SOURCE_TEMPLATE_BASE64_MD5);
            }
            if (list2 != null && list2.size() != 0) {
                return str.equals(list2.get(0));
            }
        }
        return true;
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public boolean isDestroy() {
        return this.isDestroy;
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public boolean isFrameViewShow() {
        return this.isFrameShow;
    }

    public boolean isFullScreenHeightEnabled() {
        return this.enableFullScreenHeight;
    }

    public boolean isHasException() {
        return this.hasException;
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public boolean isImmersive() {
        return this.isImmersive;
    }

    public boolean isLayerTypeEnabled() {
        return this.enableLayerType;
    }

    public boolean isNeedReLoad() {
        return this.mNeedReLoad;
    }

    public boolean isNeedValidate() {
        return this.mNeedValidate;
    }

    public boolean isOnSizeChangedRender() {
        return this.isOnSizeChangedRender;
    }

    public boolean isPreDownLoad() {
        return this.isPreDownLoad;
    }

    public boolean isPreInitMode() {
        return this.isPreInit;
    }

    public boolean isPreRenderMode() {
        return this.isPreRenderMode;
    }

    public boolean isTrackComponent() {
        return this.trackComponent;
    }

    public boolean isUseScroller() {
        return this.mUseScroller;
    }

    public boolean isViewDisAppear() {
        return this.isViewDisAppear;
    }

    public void moveFixedView(View view) {
        if (this.mRenderContainer != null) {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            if (viewGroup == null) {
                this.mRenderContainer.addView(view);
            } else if (viewGroup != this.mRenderContainer) {
                viewGroup.removeView(view);
                this.mRenderContainer.addView(view);
            }
        }
    }

    protected WXSDKInstance newNestedInstance() {
        return new WXSDKInstance(this.mContext);
    }

    @Override // com.taobao.weex.IWXActivityStateListener
    public boolean onActivityBack() {
        WXModuleManager.onActivityBack(getInstanceId());
        WXComponent wXComponent = this.mRootComp;
        if (wXComponent != null) {
            return wXComponent.onActivityBack();
        }
        if (!WXEnvironment.isApkDebugable()) {
            return false;
        }
        WXLogUtils.w("Warning :Component tree has not build completely, onActivityBack can not be call!");
        return false;
    }

    @Override // com.taobao.weex.IWXActivityStateListener
    public void onActivityCreate() {
        WXModuleManager.onActivityCreate(getInstanceId());
        WXComponent wXComponent = this.mRootComp;
        if (wXComponent != null) {
            wXComponent.onActivityCreate();
        } else if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.w("Warning :Component tree has not build completely,onActivityCreate can not be call!");
        }
        this.mGlobalEventReceiver = new WXGlobalEventReceiver(this);
        try {
            if (Build.VERSION.SDK_INT >= 33) {
                getContext().registerReceiver(this.mGlobalEventReceiver, new IntentFilter(WXGlobalEventReceiver.EVENT_ACTION), 4);
            } else {
                getContext().registerReceiver(this.mGlobalEventReceiver, new IntentFilter(WXGlobalEventReceiver.EVENT_ACTION));
            }
        } catch (Throwable th) {
            WXLogUtils.e(th.getMessage());
            this.mGlobalEventReceiver = null;
        }
    }

    @Override // com.taobao.weex.IWXActivityStateListener
    public void onActivityDestroy() {
        WXModuleManager.onActivityDestroy(getInstanceId());
        WXComponent wXComponent = this.mRootComp;
        if (wXComponent != null) {
            wXComponent.onActivityDestroy();
        } else if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.w("Warning :Component tree has not build completely, onActivityDestroy can not be call!");
        }
        this.mTimeCalculator.println();
        destroy();
    }

    @Override // com.taobao.weex.IWXActivityStateListener
    public void onActivityPause() {
        onViewDisappear();
        if (!this.isCommit) {
            if (this.mUseScroller) {
                this.mWXPerformance.useScroller = 1;
            }
            this.mWXPerformance.maxDeepViewLayer = getMaxDeepLayer();
            WXPerformance wXPerformance = this.mWXPerformance;
            wXPerformance.wxDims = this.mwxDims;
            wXPerformance.measureTimes = this.measureTimes;
            IWXUserTrackAdapter iWXUserTrackAdapter = this.mUserTrackAdapter;
            if (iWXUserTrackAdapter != null) {
                iWXUserTrackAdapter.commit(this.mContext, null, "load", wXPerformance, getUserTrackParams());
            }
            this.isCommit = true;
        }
        WXModuleManager.onActivityPause(getInstanceId());
        WXComponent wXComponent = this.mRootComp;
        if (wXComponent != null) {
            wXComponent.onActivityPause();
        } else if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.w("Warning :Component tree has not build completely,onActivityPause can not be call!");
        }
        if (!this.mCurrentGround) {
            WXLogUtils.i("Application to be in the backround");
            Intent intent = new Intent(WXGlobalEventReceiver.EVENT_ACTION);
            intent.putExtra(WXGlobalEventReceiver.EVENT_NAME, Constants.Event.PAUSE_EVENT);
            intent.putExtra(WXGlobalEventReceiver.EVENT_WX_INSTANCEID, getInstanceId());
            Context context = this.mContext;
            if (context != null) {
                intent.setPackage(context.getPackageName());
                this.mContext.sendBroadcast(intent);
            } else {
                try {
                    WXEnvironment.getApplication().sendBroadcast(intent);
                } catch (Exception e) {
                    WXLogUtils.e("weex", e);
                }
            }
            this.mCurrentGround = true;
        }
        if ((WXEnvironment.isApkDebugable() || WXEnvironment.isPerf()) && this.mApmForInstance != null) {
            WXLogUtils.e("PerformanceData " + this.mApmForInstance.toPerfString());
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        WXModuleManager.onActivityResult(getInstanceId(), i, i2, intent);
        WXComponent wXComponent = this.mRootComp;
        if (wXComponent != null) {
            wXComponent.onActivityResult(i, i2, intent);
        } else if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.w("Warning :Component tree has not build completely, onActivityResult can not be call!");
        }
    }

    @Override // com.taobao.weex.IWXActivityStateListener
    public void onActivityResume() {
        WXModuleManager.onActivityResume(getInstanceId());
        WXComponent wXComponent = this.mRootComp;
        if (wXComponent != null) {
            wXComponent.onActivityResume();
        } else if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.w("Warning :Component tree has not build completely, onActivityResume can not be call!");
        }
        if (this.mCurrentGround) {
            WXLogUtils.i("Application  to be in the foreground");
            Intent intent = new Intent(WXGlobalEventReceiver.EVENT_ACTION);
            intent.putExtra(WXGlobalEventReceiver.EVENT_NAME, Constants.Event.RESUME_EVENT);
            intent.putExtra(WXGlobalEventReceiver.EVENT_WX_INSTANCEID, getInstanceId());
            Context context = this.mContext;
            if (context != null) {
                intent.setPackage(context.getPackageName());
                this.mContext.sendBroadcast(intent);
            } else {
                WXEnvironment.getApplication().sendBroadcast(intent);
            }
            this.mCurrentGround = false;
        }
        onViewAppear();
    }

    @Override // com.taobao.weex.IWXActivityStateListener
    public void onActivityStart() {
        WXModuleManager.onActivityStart(getInstanceId());
        WXComponent wXComponent = this.mRootComp;
        if (wXComponent != null) {
            wXComponent.onActivityStart();
        } else if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.w("Warning :Component tree has not build completely,onActivityStart can not be call!");
        }
    }

    @Override // com.taobao.weex.IWXActivityStateListener
    public void onActivityStop() {
        WXModuleManager.onActivityStop(getInstanceId());
        WXComponent wXComponent = this.mRootComp;
        if (wXComponent != null) {
            wXComponent.onActivityStop();
        } else if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.w("Warning :Component tree has not build completely, onActivityStop can not be call!");
        }
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public boolean onBackPressed() {
        List<OnBackPressedHandler> list = this.mWXBackPressedHandlers;
        if (list != null) {
            Iterator<OnBackPressedHandler> it = list.iterator();
            while (it.hasNext()) {
                if (it.next().onBackPressed()) {
                    return true;
                }
            }
        }
        WXComponent rootComponent = getRootComponent();
        if (rootComponent == null) {
            return false;
        }
        WXEvent events = rootComponent.getEvents();
        if (events.contains(Constants.Event.NATIVE_BACK) && WXUtils.getBoolean(rootComponent.fireEventWait(Constants.Event.NATIVE_BACK, null).getResult(), Boolean.FALSE).booleanValue()) {
            return true;
        }
        boolean zContains = events.contains(Constants.Event.CLICKBACKITEM);
        if (zContains) {
            fireEvent(rootComponent.getRef(), Constants.Event.CLICKBACKITEM, null, null);
        }
        return zContains;
    }

    public void onChangeElement(WXComponent wXComponent, boolean z) {
        WXAbstractRenderContainer wXAbstractRenderContainer;
        if (isDestroy() || (wXAbstractRenderContainer = this.mRenderContainer) == null || this.mWXPerformance == null || wXComponent == null || wXComponent.isIgnoreInteraction || wXAbstractRenderContainer.hasConsumeEvent()) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (!this.mHasCreateFinish || jCurrentTimeMillis - this.mWXPerformance.renderTimeOrigin <= 8000) {
            if (wXComponent.mIsAddElementToTree) {
                getWXPerformance().localInteractionViewAddCount++;
                if (!z) {
                    getWXPerformance().interactionViewAddLimitCount++;
                }
                wXComponent.mIsAddElementToTree = false;
            }
            if (z) {
                return;
            }
            this.mApmForInstance.arriveInteraction(wXComponent);
        }
    }

    public void onComponentCreate(WXComponent wXComponent, long j) {
        WXPerformance wXPerformance = this.mWXPerformance;
        wXPerformance.mActionAddElementCount++;
        wXPerformance.mActionAddElementSumTime = (int) (wXPerformance.mActionAddElementSumTime + j);
        if (!this.mEnd) {
            wXPerformance.fsComponentCreateTime = (int) (wXPerformance.fsComponentCreateTime + j);
            wXPerformance.fsComponentCount++;
        }
        wXPerformance.componentCount++;
        wXPerformance.componentCreateTime += j;
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public void onCreateFinish() {
        if (this.mHasCreateFinish || this.mContext == null) {
            return;
        }
        onViewAppear();
        WXAbstractRenderContainer wXAbstractRenderContainer = this.mRenderContainer;
        IWXRenderListener iWXRenderListener = this.mRenderListener;
        if (iWXRenderListener != null) {
            iWXRenderListener.onViewCreated(this, wXAbstractRenderContainer);
        }
        IWXStatisticsListener iWXStatisticsListener = this.mStatisticsListener;
        if (iWXStatisticsListener != null) {
            iWXStatisticsListener.onFirstView();
        }
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public boolean onCreateOptionsMenu(Menu menu) {
        WXModuleManager.onCreateOptionsMenu(getInstanceId(), menu);
        WXComponent wXComponent = this.mRootComp;
        if (wXComponent != null) {
            wXComponent.onCreateOptionsMenu(menu);
            return true;
        }
        if (!WXEnvironment.isApkDebugable()) {
            return true;
        }
        WXLogUtils.w("Warning :Component tree has not build completely,onActivityStart can not be call!");
        return true;
    }

    public void onHttpStart() {
        if (this.mEnd) {
            return;
        }
        this.mWXPerformance.fsRequestNum++;
    }

    public void onInstanceReady() {
        WXLogUtils.e("test->", "onInstanceReady");
        this.mApmForInstance.onStage(WXInstanceApm.KEY_PAGE_STAGES_CONTAINER_READY);
        if (this.isPreInit || this.isPreDownLoad) {
            this.mApmForInstance.onInstanceReady(this.isPreDownLoad);
            if (this.isPreDownLoad) {
                this.mHttpListener.onInstanceReady();
            }
        }
    }

    public void onJSException(final String str, final String str2, final String str3) {
        this.hasException = true;
        if (this.mRenderListener == null || this.mContext == null) {
            return;
        }
        WXLogUtils.e("onJSException " + str + "," + str3);
        runOnUiThread(new Runnable() { // from class: com.taobao.weex.WXSDKInstance.6
            @Override // java.lang.Runnable
            public void run() {
                if (str.equals(WXErrorCode.WX_KEY_EXCEPTION_VALIDAPPKEY.getErrorCode())) {
                    Context context = WXSDKInstance.this.mContext;
                    if (context instanceof Activity) {
                        ErrorDialogUtil.checkAppKeyErrorTips((Activity) context);
                        return;
                    }
                    return;
                }
                if (WXSDKInstance.this.mRenderListener == null || WXSDKInstance.this.mContext == null) {
                    return;
                }
                WXSDKInstance.this.mRenderListener.onException(WXSDKInstance.this, str, str2 + str3);
            }
        });
    }

    public void onLayoutChange(View view) {
    }

    public void onOldFsRenderTimeLogic() {
        if (this.mEnd) {
            return;
        }
        this.mEnd = true;
        if (this.mStatisticsListener != null && this.mContext != null) {
            runOnUiThread(new Runnable() { // from class: com.taobao.weex.WXSDKInstance.7
                @Override // java.lang.Runnable
                public void run() {
                    if (WXSDKInstance.this.mStatisticsListener == null || WXSDKInstance.this.mContext == null) {
                        return;
                    }
                    Trace.beginSection("onFirstScreen");
                    WXSDKInstance.this.mStatisticsListener.onFirstScreen();
                    Trace.endSection();
                }
            });
        }
        this.mApmForInstance.arriveFSRenderTime();
        this.mWXPerformance.fsRenderTime = System.currentTimeMillis();
        this.mWXPerformance.screenRenderTime = System.currentTimeMillis() - this.mRenderStartTime;
    }

    public void onRefreshSuccess(int i, int i2) {
        IWXRenderListener iWXRenderListener = this.mRenderListener;
        if (iWXRenderListener == null || this.mContext == null) {
            return;
        }
        iWXRenderListener.onRefreshSuccess(this, i, i2);
    }

    public void onRenderError(final String str, final String str2) {
        if (this.mRenderListener == null || this.mContext == null) {
            return;
        }
        WXLogUtils.e("onRenderError " + str + "," + str2);
        runOnUiThread(new Runnable() { // from class: com.taobao.weex.WXSDKInstance.5
            @Override // java.lang.Runnable
            public void run() {
                if (WXSDKInstance.this.mRenderListener != null) {
                    WXSDKInstance wXSDKInstance = WXSDKInstance.this;
                    if (wXSDKInstance.mContext != null) {
                        wXSDKInstance.mRenderListener.onException(WXSDKInstance.this, str, str2);
                    }
                }
            }
        });
    }

    public void onRenderSuccess(int i, int i2) {
        this.isRenderSuccess = true;
        if (!this.isNewFsEnd) {
            getApmForInstance().arriveNewFsRenderTime();
        }
        long jCurrentTimeMillis = System.currentTimeMillis() - this.mRenderStartTime;
        long[] renderFinishTime = WXBridgeManager.getInstance().getRenderFinishTime(getInstanceId());
        WXPerformance wXPerformance = this.mWXPerformance;
        wXPerformance.callBridgeTime = renderFinishTime[0];
        wXPerformance.cssLayoutTime = renderFinishTime[1];
        wXPerformance.parseJsonTime = renderFinishTime[2];
        wXPerformance.totalTime = jCurrentTimeMillis;
        if (wXPerformance.screenRenderTime < 0.001d) {
            wXPerformance.screenRenderTime = jCurrentTimeMillis;
        }
        IWXRenderListener iWXRenderListener = this.mRenderListener;
        if (iWXRenderListener != null && this.mContext != null) {
            iWXRenderListener.onRenderSuccess(this, i, i2);
            if (this.mUserTrackAdapter != null) {
                WXPerformance wXPerformance2 = new WXPerformance(this.mInstanceId);
                wXPerformance2.errCode = WXErrorCode.WX_SUCCESS.getErrorCode();
                wXPerformance2.args = getBundleUrl();
                this.mUserTrackAdapter.commit(this.mContext, null, IWXUserTrackAdapter.JS_BRIDGE, wXPerformance2, getUserTrackParams());
            }
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.d(WXLogUtils.WEEX_PERF_TAG, this.mWXPerformance.toString());
            }
        }
        if (WXEnvironment.isPerf()) {
            WXLogUtils.e(WXLogUtils.WEEX_PERF_TAG, this.mWXPerformance.getPerfData());
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        WXModuleManager.onRequestPermissionsResult(getInstanceId(), i, strArr, iArr);
        WXComponent wXComponent = this.mRootComp;
        if (wXComponent != null) {
            wXComponent.onRequestPermissionsResult(i, strArr, iArr);
        } else if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.w("Warning :Component tree has not build completely, onRequestPermissionsResult can not be call!");
        }
    }

    public void onRootCreated(WXComponent wXComponent) {
        this.mRootComp = wXComponent;
        wXComponent.mDeepInComponentTree = 1;
        this.mRenderContainer.addView(wXComponent.getHostView());
        setSize(this.mRenderContainer.getWidth(), this.mRenderContainer.getHeight());
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public synchronized void onShowAnimationEnd() {
        this.isFrameShow = true;
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.frameViewEventListeners);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((FrameViewEventListener) obj).onShowAnimationEnd();
        }
        arrayList.clear();
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public boolean onSupportNavigateUp() {
        List<ActionBarHandler> list = this.mWXActionbarHandlers;
        if (list == null) {
            return false;
        }
        Iterator<ActionBarHandler> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().onSupportNavigateUp()) {
                return true;
            }
        }
        return false;
    }

    public void onUpdateFinish() {
        if (WXEnvironment.isApkDebugable()) {
            WXLogUtils.d("Instance onUpdateSuccess");
        }
    }

    public void onViewAppear() {
        this.isViewDisAppear = true;
        this.mApmForInstance.onAppear();
        WXComponent rootComponent = getRootComponent();
        if (rootComponent != null) {
            fireEvent(rootComponent.getRef(), Constants.Event.VIEWAPPEAR, null, null);
            Iterator<OnInstanceVisibleListener> it = this.mVisibleListeners.iterator();
            while (it.hasNext()) {
                it.next().onAppear();
            }
        }
    }

    public void onViewDisappear() {
        this.isViewDisAppear = false;
        this.mApmForInstance.onDisAppear();
        WXComponent rootComponent = getRootComponent();
        if (rootComponent != null) {
            fireEvent(rootComponent.getRef(), Constants.Event.VIEWDISAPPEAR, null, null);
            Iterator<OnInstanceVisibleListener> it = this.mVisibleListeners.iterator();
            while (it.hasNext()) {
                it.next().onDisappear();
            }
        }
    }

    public void preDownLoad(String str, Map<String, Object> map, String str2, WXRenderStrategy wXRenderStrategy) {
        this.isPreDownLoad = true;
        this.mRenderStrategy = wXRenderStrategy;
        this.mApmForInstance.isReady = false;
        renderByUrl(str, str, map, str2, wXRenderStrategy);
    }

    public void preInit(String str, String str2, Map<String, Object> map, String str3, WXRenderStrategy wXRenderStrategy) {
        this.isPreInit = true;
        this.mRenderStrategy = wXRenderStrategy;
        if (map == null) {
            map = new HashMap<>();
        }
        this.mApmForInstance.isReady = false;
        WXSDKManager.getInstance().createInstance(this, new Script(str2), map, str3);
    }

    public void refreshInstance(Map<String, Object> map) {
        if (map == null) {
            return;
        }
        refreshInstance(WXJsonUtils.fromObjectToJSONString(map));
    }

    public synchronized void registerActionbarHandler(ActionBarHandler actionBarHandler) {
        if (actionBarHandler == null) {
            return;
        }
        if (this.mWXActionbarHandlers == null) {
            this.mWXActionbarHandlers = new ArrayList();
        }
        this.mWXActionbarHandlers.add(actionBarHandler);
    }

    @Deprecated
    public void registerActivityStateListener(IWXActivityStateListener iWXActivityStateListener) {
    }

    public synchronized void registerBackPressedHandler(OnBackPressedHandler onBackPressedHandler) {
        if (onBackPressedHandler == null) {
            return;
        }
        if (this.mWXBackPressedHandlers == null) {
            this.mWXBackPressedHandlers = new ArrayList();
        }
        this.mWXBackPressedHandlers.add(onBackPressedHandler);
    }

    public synchronized void registerOnWXScrollListener(OnWXScrollListener onWXScrollListener) {
        if (this.mWXScrollListeners == null) {
            this.mWXScrollListeners = new ArrayList();
        }
        this.mWXScrollListeners.add(onWXScrollListener);
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public void registerRenderListener(IWXRenderListener iWXRenderListener) {
        this.mRenderListener = iWXRenderListener;
    }

    @Deprecated
    public void registerScrollViewListener(WXScrollView.WXScrollViewListener wXScrollViewListener) {
        this.mWXScrollViewListener = wXScrollViewListener;
    }

    public void registerStatisticsListener(IWXStatisticsListener iWXStatisticsListener) {
        this.mStatisticsListener = iWXStatisticsListener;
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    @Deprecated
    public void reloadImages() {
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public void reloadPage(boolean z) {
        WXSDKEngine.reload();
        if (z) {
            if (this.mContext != null) {
                Intent intent = new Intent();
                intent.setAction(ACTION_INSTANCE_RELOAD);
                intent.putExtra("url", this.mBundleUrl);
                this.mContext.sendBroadcast(intent);
                return;
            }
            return;
        }
        IWXConfigAdapter wxConfigAdapter = WXSDKManager.getInstance().getWxConfigAdapter();
        if (wxConfigAdapter != null) {
            boolean z2 = Boolean.parseBoolean(wxConfigAdapter.getConfig("android_weex_ext_config", "degrade_to_h5_if_not_reload", AbsoluteConst.TRUE));
            WXLogUtils.e("degrade : " + z2);
            if (z2) {
                onJSException(String.valueOf(WXErrorCode.WX_ERR_RELOAD_PAGE.getErrorCode()), "Do not reloadPage", "Do not reloadPage degradeToH5");
                WXLogUtils.e("Do not reloadPage degradeToH5");
            }
        }
    }

    public void reloadPageLayout() {
        WXBridgeManager.getInstance().reloadPageLayout(getInstanceId());
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public void removeEventListener(String str, String str2) {
        List<String> list;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || (list = this.mGlobalEvents.get(str)) == null) {
            return;
        }
        list.remove(str2);
    }

    public void removeFixedView(View view) {
        WXAbstractRenderContainer wXAbstractRenderContainer = this.mRenderContainer;
        if (wXAbstractRenderContainer != null) {
            wXAbstractRenderContainer.removeView(view);
        }
    }

    public synchronized void removeFrameViewEventListener(FrameViewEventListener frameViewEventListener) {
        if (this.frameViewEventListeners.contains(frameViewEventListener)) {
            this.frameViewEventListeners.remove(frameViewEventListener);
        }
    }

    public void removeInActiveAddElmentAction(String str) {
        this.inactiveAddElementAction.remove(str);
    }

    public void removeLayerOverFlowListener(String str) {
        List<String> list = this.mLayerOverFlowListeners;
        if (list != null) {
            list.remove(str);
        }
    }

    public void removeOnInstanceVisibleListener(OnInstanceVisibleListener onInstanceVisibleListener) {
        this.mVisibleListeners.remove(onInstanceVisibleListener);
    }

    public void removeUserTrackParameter(String str) {
        Map<String, Serializable> map = this.mUserTrackParams;
        if (map != null) {
            map.remove(str);
        }
    }

    public void render(String str, Map<String, Object> map, String str2) {
        render(str, map, str2, WXRenderStrategy.APPEND_ASYNC);
    }

    @Deprecated
    public void renderByUrl(String str, String str2, Map<String, Object> map, String str3, int i, int i2, WXRenderStrategy wXRenderStrategy) {
        renderByUrl(str, str2, map, str3, wXRenderStrategy);
    }

    public void resetDeviceDisplayOfPage() {
        WXBridgeManager.getInstance().setDeviceDisplayOfPage(getInstanceId(), WXViewUtils.getScreenWidth(getContext()), WXViewUtils.getScreenHeight(getContext()));
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public Uri rewriteUri(Uri uri, String str) {
        return getURIAdapter().rewrite(this, str, uri);
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public void runOnUiThread(Runnable runnable) {
        WXSDKManager.getInstance().postOnUiThread(runnable, 0L);
    }

    public void setAutoAdjustDeviceWidth(boolean z) {
        this.mAutoAdjustDeviceWidth = z;
    }

    @Deprecated
    public void setBizType(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mWXPerformance.bizType = str;
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    @Deprecated
    public void setBundleUrl(String str) {
        this.mBundleUrl = str;
        if (WXSDKManager.getInstance().getValidateProcessor() != null) {
            this.mNeedValidate = WXSDKManager.getInstance().getValidateProcessor().needValidate(this.mBundleUrl);
        }
    }

    public void setComponentObserver(ComponentObserver componentObserver) {
        this.mComponentObserver = componentObserver;
    }

    public void setComponentsInfoExceedGPULimit(JSONObject jSONObject) {
        if (jSONObject == null || jSONObject.isEmpty()) {
            return;
        }
        this.componentsInfoExceedGPULimit.add(jSONObject);
    }

    public void setContainerInfo(String str, String str2) {
        this.mContainerInfo.put(str, str2);
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setCustomFontNetworkHandler(CustomFontNetworkHandler customFontNetworkHandler) {
        this.mCustomFontNetworkHandler = customFontNetworkHandler;
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public void setDefaultFontSize(int i) {
        this.mDefaultFontSize = i;
    }

    public void setDeviceDisplayOfPage(int i, int i2) {
        WXBridgeManager.getInstance().setDeviceDisplayOfPage(getInstanceId(), i, i2);
    }

    public void setEnableFullScreenHeight(boolean z) {
        this.enableFullScreenHeight = z;
    }

    public void setHasException(boolean z) {
        this.hasException = z;
    }

    @Deprecated
    public void setIWXUserTrackAdapter(IWXUserTrackAdapter iWXUserTrackAdapter) {
    }

    public void setImageNetworkHandler(ImageNetworkHandler imageNetworkHandler) {
        this.mImageNetworkHandler = imageNetworkHandler;
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public void setImmersive(boolean z) {
        this.isImmersive = z;
    }

    public void setInstanceViewPortWidth(float f) {
        setInstanceViewPortWidth(f, false);
    }

    public void setMaxDeepLayer(int i) {
        this.mMaxDeepLayer = i;
        this.mApmForInstance.updateMaxStats(WXInstanceApm.KEY_PAGE_STATS_MAX_DEEP_VIEW, i);
    }

    public void setMaxDomDeep(int i) {
        this.mApmForInstance.updateMaxStats(WXInstanceApm.KEY_PAGE_STATS_MAX_DEEP_DOM, i);
        WXPerformance wXPerformance = this.mWXPerformance;
        if (wXPerformance != null && wXPerformance.maxDeepVDomLayer <= i) {
            wXPerformance.maxDeepVDomLayer = i;
        }
    }

    public void setMaxHiddenEmbedsNum(int i) {
        this.maxHiddenEmbedsNum = i;
    }

    public void setNeedLoad(boolean z) {
        this.mNeedReLoad = z;
    }

    public void setNestedInstanceInterceptor(NestedInstanceInterceptor nestedInstanceInterceptor) {
        this.mNestedInstanceInterceptor = nestedInstanceInterceptor;
    }

    public void setPageKeepRawCssStyles() {
        WXBridgeManager.getInstance().setPageArgument(getInstanceId(), "reserveCssStyles", AbsoluteConst.TRUE);
    }

    public void setParentInstance(WXSDKInstance wXSDKInstance) {
        this.mParentInstance = wXSDKInstance;
    }

    public void setPreRenderMode(final boolean z) {
        WXSDKManager.getInstance().getWXRenderManager().postOnUiThread(new Runnable() { // from class: com.taobao.weex.WXSDKInstance.4
            @Override // java.lang.Runnable
            public void run() {
                WXSDKInstance.this.isPreRenderMode = z;
            }
        }, 0L);
    }

    public void setRenderContainer(RenderContainer renderContainer) {
        setWXAbstractRenderContainer(renderContainer);
    }

    public void setRenderStartTime(long j) {
        this.mRenderStartTime = j;
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public void setRenderType(String str) {
        this.mRenderType = str;
    }

    public void setRootScrollView(ScrollView scrollView) {
        this.mScrollView = scrollView;
        WXScrollView.WXScrollViewListener wXScrollViewListener = this.mWXScrollViewListener;
        if (wXScrollViewListener == null || !(scrollView instanceof WXScrollView)) {
            return;
        }
        ((WXScrollView) scrollView).addScrollViewListener(wXScrollViewListener);
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public void setSize(int i, int i2) {
        if (i > 0) {
            if ((!(i2 > 0) || !(!this.isDestroy)) || !this.mRendered || this.mRenderContainer == null) {
                return;
            }
            if (sScreenHeight < 0) {
                sScreenHeight = WXViewUtils.getScreenHeight(getContext());
            }
            int i3 = sScreenHeight;
            if (i3 > 0) {
                double d = (i2 / i3) * 100.0d;
                getApmForInstance().addStats(WXInstanceApm.KEY_PAGE_STATS_BODY_RATIO, d <= 100.0d ? d : 100.0d);
            }
            ViewGroup.LayoutParams layoutParams = this.mRenderContainer.getLayoutParams();
            if (layoutParams != null) {
                final float f = i;
                final float f2 = i2;
                WXViewUtils.getScreenDensity(this.mContext);
                if (this.mRenderContainer.getWidth() != i || this.mRenderContainer.getHeight() != i2) {
                    layoutParams.width = i;
                    layoutParams.height = i2;
                    this.mRenderContainer.setLayoutParams(layoutParams);
                }
                if (this.mRootComp != null) {
                    final boolean z = layoutParams.width == -2;
                    final boolean z2 = layoutParams.height == -2;
                    WXBridgeManager.getInstance().post(new Runnable() { // from class: com.taobao.weex.WXSDKInstance.10
                        @Override // java.lang.Runnable
                        public void run() {
                            WXSDKInstance wXSDKInstance = WXSDKInstance.this;
                            IWXInstanceContainerOnSizeListener iWXInstanceContainerOnSizeListener = wXSDKInstance.mOnSizeListener;
                            if (iWXInstanceContainerOnSizeListener != null) {
                                iWXInstanceContainerOnSizeListener.onSizeChanged(wXSDKInstance.getInstanceId(), f, f2, z, z2);
                            } else {
                                WXBridgeManager.getInstance().setDefaultRootSize(WXSDKInstance.this.getInstanceId(), f, f2, z, z2);
                            }
                        }
                    });
                }
            }
        }
    }

    public void setStreamNetworkHandler(StreamNetworkHandler streamNetworkHandler) {
        this.mStreamNetworkHandler = streamNetworkHandler;
    }

    public void setTemplate(String str) {
        this.templateRef = new WeakReference<>(str);
    }

    public void setTrackComponent(boolean z) {
        this.trackComponent = z;
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public void setUniPagePath(String str) {
        this.mUniPagePath = str;
    }

    public void setUseSandBox(boolean z) {
        WXBridgeManager.getInstance().setSandBoxContext(z);
    }

    public void setUseScroller(boolean z) {
        this.mUseScroller = z;
    }

    public void setWXAbstractRenderContainer(WXAbstractRenderContainer wXAbstractRenderContainer) {
        if (wXAbstractRenderContainer != null) {
            wXAbstractRenderContainer.setSDKInstance(this);
            wXAbstractRenderContainer.addOnLayoutChangeListener(this);
        }
        this.mRenderContainer = wXAbstractRenderContainer;
        if (wXAbstractRenderContainer == null || wXAbstractRenderContainer.getLayoutParams() == null || this.mRenderContainer.getLayoutParams().width != -2) {
            WXBridgeManager.getInstance().post(new Runnable() { // from class: com.taobao.weex.WXSDKInstance.2
                @Override // java.lang.Runnable
                public void run() {
                    WXBridgeManager.getInstance().setRenderContentWrapContentToCore(false, WXSDKInstance.this.getInstanceId());
                }
            });
        } else {
            WXBridgeManager.getInstance().post(new Runnable() { // from class: com.taobao.weex.WXSDKInstance.1
                @Override // java.lang.Runnable
                public void run() {
                    WXBridgeManager.getInstance().setRenderContentWrapContentToCore(true, WXSDKInstance.this.getInstanceId());
                }
            });
        }
    }

    public void setWXInstanceContainerOnSizeListener(IWXInstanceContainerOnSizeListener iWXInstanceContainerOnSizeListener) {
        this.mOnSizeListener = iWXInstanceContainerOnSizeListener;
    }

    public void setonSizeChangedRnder(boolean z) {
        this.isOnSizeChangedRender = z;
    }

    public boolean skipFrameworkInit() {
        return isDataRender() && !this.mDisableSkipFrameworkInit;
    }

    public synchronized void unRegisterActionbarHandler(ActionBarHandler actionBarHandler) {
        List<ActionBarHandler> list = this.mWXActionbarHandlers;
        if (list == null || actionBarHandler == null) {
            return;
        }
        list.remove(actionBarHandler);
    }

    public synchronized void unRegisterBackPressedHandler(OnBackPressedHandler onBackPressedHandler) {
        List<OnBackPressedHandler> list = this.mWXBackPressedHandlers;
        if (list == null || onBackPressedHandler == null) {
            return;
        }
        list.remove(onBackPressedHandler);
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public void fireEvent(String str, String str2, Map<String, Object> map, Map<String, Object> map2, List<Object> list, EventResult eventResult) {
        int i;
        onInterceptInstanceEvent(getInstanceId(), str, str2, map, map2);
        WXPerformance wXPerformance = this.mWXPerformance;
        if (wXPerformance != null && (i = wXPerformance.fsCallEventTotalNum) < Integer.MAX_VALUE) {
            wXPerformance.fsCallEventTotalNum = i + 1;
        }
        this.mApmForInstance.updateFSDiffStats(WXInstanceApm.KEY_PAGE_STATS_FS_CALL_EVENT_NUM, 1.0d);
        WXBridgeManager.getInstance().fireEventOnNode(getInstanceId(), str, str2, map, map2, list, eventResult);
    }

    @Override // android.view.View.OnLayoutChangeListener
    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (i == i5 && i2 == i6 && i3 == i7 && i4 == i8) {
            return;
        }
        onLayoutChange(view);
    }

    public void refreshInstance(String str) {
        if (str == null) {
            return;
        }
        this.mRefreshStartTime = System.currentTimeMillis();
        WXRefreshData wXRefreshData = this.mLastRefreshData;
        if (wXRefreshData != null) {
            wXRefreshData.isDirty = true;
        }
        this.mLastRefreshData = new WXRefreshData(str, false);
        WXSDKManager.getInstance().refreshInstance(this.mInstanceId, this.mLastRefreshData);
    }

    @Deprecated
    public void render(String str, Map<String, Object> map, String str2, WXRenderStrategy wXRenderStrategy) {
        render("default", str, map, str2, wXRenderStrategy);
    }

    public void renderByUrl(String str, String str2, Map<String, Object> map, String str3, WXRenderStrategy wXRenderStrategy) {
        renderByUrlInternal(str, str2, map, str3, wXRenderStrategy);
    }

    public void setInstanceViewPortWidth(float f, boolean z) {
        this.mInstanceViewPortWidth = f;
        WXEnvironment.setViewProt(f);
        if (z) {
            WXBridgeManager.getInstance().setViewPortWidth(getInstanceId(), this.mInstanceViewPortWidth);
        }
    }

    public void render(String str, String str2, Map<String, Object> map, String str3, WXRenderStrategy wXRenderStrategy) {
        render(str, new Script(str2), map, str3, wXRenderStrategy);
    }

    public void render(String str, Script script, Map<String, Object> map, String str2, WXRenderStrategy wXRenderStrategy) {
        this.mWXPerformance.beforeInstanceRender(this.mInstanceId);
        if (WXEnvironment.isApkDebugable() && "default".equals(str)) {
            if (getUIContext() != null) {
                new AlertDialog.Builder(getUIContext()).setTitle("Error: Missing pageName").setMessage("We highly recommend you to set pageName. Call\nWXSDKInstance#render(String pageName, String template, Map<String, Object> options, String jsonInitData, WXRenderStrategy flag)\nto fix it.").show();
                return;
            }
            return;
        }
        renderInternal(str, script, map, str2, wXRenderStrategy);
    }

    private void renderInternal(String str, Script script, Map<String, Object> map, String str2, WXRenderStrategy wXRenderStrategy) {
        if (this.mRendered || script == null || script.isEmpty()) {
            return;
        }
        LogDetail logDetailCreateLogDetail = this.mTimeCalculator.createLogDetail("renderInternal");
        this.mRenderStrategy = wXRenderStrategy;
        if (!this.mApmForInstance.hasInit()) {
            this.mApmForInstance.doInit();
        }
        this.mApmForInstance.setPageName(str);
        this.mApmForInstance.onStage(WXInstanceApm.KEY_PAGE_STAGES_RENDER_ORGIGIN);
        this.mApmForInstance.doDelayCollectData();
        this.mWXPerformance.pageName = TextUtils.isEmpty(str) ? "defaultBundleUrl" : str;
        if (TextUtils.isEmpty(this.mBundleUrl)) {
            this.mBundleUrl = this.mWXPerformance.pageName;
        }
        if (WXTracing.isAvailable()) {
            WXTracing.TraceEvent traceEventNewEvent = WXTracing.newEvent("executeBundleJS", this.mInstanceId, -1);
            traceEventNewEvent.traceId = this.mExecJSTraceId;
            traceEventNewEvent.iid = this.mInstanceId;
            traceEventNewEvent.tname = "JSThread";
            traceEventNewEvent.ph = "B";
            traceEventNewEvent.submit();
            this.mRenderStartNanos = System.nanoTime();
        }
        ensureRenderArchor();
        if (map == null) {
            map = new HashMap<>();
        }
        Map<String, Object> map2 = map;
        if (WXEnvironment.sDynamicMode && !TextUtils.isEmpty(WXEnvironment.sDynamicUrl) && map2.get("dynamicMode") == null) {
            map2.put("dynamicMode", AbsoluteConst.TRUE);
            renderByUrl(str, WXEnvironment.sDynamicUrl, map2, str2, wXRenderStrategy);
            return;
        }
        this.mWXPerformance.JSTemplateSize = script.length() / 1024.0f;
        this.mApmForInstance.addStats(WXInstanceApm.KEY_PAGE_STATS_BUNDLE_SIZE, this.mWXPerformance.JSTemplateSize);
        this.mRenderStartTime = System.currentTimeMillis();
        WXSDKManager.getInstance().setCrashInfo(WXEnvironment.WEEX_CURRENT_KEY, str);
        WXParams initParams = WXBridgeManager.getInstance().getInitParams();
        float f = WXEnvironment.sApplication.getResources().getDisplayMetrics().density;
        int screenWidth = WXViewUtils.getScreenWidth(this.mContext);
        if (!WeexInstanceMgr.self().getComplier().equalsIgnoreCase("weex")) {
            setInstanceViewPortWidth(screenWidth / f, true);
        }
        if (initParams != null && !TextUtils.equals(initParams.getDeviceWidth(), String.valueOf(screenWidth))) {
            initParams.setDeviceWidth(String.valueOf(screenWidth));
            initParams.setDeviceHeight(String.valueOf(WXViewUtils.getScreenHeight(this.mContext)));
            WXEnvironment.addCustomOptions("scale", Float.toString(f));
            WXBridgeManager.getInstance().updateInitDeviceParams(initParams.getDeviceWidth(), initParams.getDeviceHeight(), Float.toString(f), WXViewUtils.getStatusBarHeight(this.mContext) > 0 ? String.valueOf(WXViewUtils.getStatusBarHeight(this.mContext)) : null);
            setDeviceDisplay(WXViewUtils.getScreenWidth(this.mContext), WXViewUtils.getScreenHeight(this.mContext), WXViewUtils.getScreenDensity(this.mContext));
        }
        logDetailCreateLogDetail.taskStart();
        if (isPreInitMode()) {
            getApmForInstance().onStage(WXInstanceApm.KEY_PAGE_STAGES_LOAD_BUNDLE_START);
            WXBridgeManager.getInstance().loadJsBundleInPreInitMode(getInstanceId(), script.getContent());
        } else {
            WXSDKManager.getInstance().createInstance(this, script, map2, str2);
        }
        logDetailCreateLogDetail.taskEnd();
        this.mRendered = true;
        final IWXJscProcessManager wXJscProcessManager = WXSDKManager.getInstance().getWXJscProcessManager();
        if (wXJscProcessManager == null || !wXJscProcessManager.shouldReboot()) {
            return;
        }
        WXSDKManager.getInstance().postOnUiThread(new Runnable() { // from class: com.taobao.weex.WXSDKInstance.3
            @Override // java.lang.Runnable
            public void run() {
                WXSDKInstance.this.checkWhiteScreen();
                if (WXSDKInstance.this.isDestroy || WXSDKInstance.this.hasException || WXSDKInstance.this.isRenderSuccess) {
                    return;
                }
                View containerView = WXSDKInstance.this.getContainerView();
                if ((containerView instanceof ViewGroup) && ((ViewGroup) containerView).getChildCount() == 0) {
                    if (wXJscProcessManager.withException(WXSDKInstance.this)) {
                        WXSDKInstance.this.onJSException(String.valueOf(WXErrorCode.WX_ERR_RELOAD_PAGE), "jsc reboot", "jsc reboot");
                    }
                    if (WXSDKInstance.this.createInstanceHeartBeat) {
                        return;
                    }
                    WXBridgeManager.getInstance().callReportCrashReloadPage(WXSDKInstance.this.mInstanceId, null);
                    WXLogUtils.e("callReportCrashReloadPage with jsc reboot");
                }
            }
        }, wXJscProcessManager.rebootTimeout());
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public void removeEventListener(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mGlobalEvents.remove(str);
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public void fireEvent(String str, String str2, Map<String, Object> map, Map<String, Object> map2) {
        fireEvent(str, str2, map, map2, null);
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public void fireEvent(String str, String str2, Map<String, Object> map) {
        fireEvent(str, str2, map, null);
    }

    @Override // io.dcloud.feature.uniapp.AbsSDKInstance
    public void fireEvent(String str, String str2) {
        fireEvent(str, str2, new HashMap());
    }

    public void render(String str, byte[] bArr, Map<String, Object> map, String str2) {
        render(str, new Script(bArr), map, str2, WXRenderStrategy.DATA_RENDER_BINARY);
    }

    @Deprecated
    public void render(String str, String str2, Map<String, Object> map, String str3, int i, int i2, WXRenderStrategy wXRenderStrategy) {
        render(str, str2, map, str3, wXRenderStrategy);
    }

    public void render(String str) {
        render("default", str, (Map<String, Object>) null, (String) null, this.mRenderStrategy);
    }

    @Deprecated
    public void render(String str, int i, int i2) {
        render(str);
    }

    public WXSDKInstance(Context context, Context context2) {
        this.mEnd = false;
        this.mHasCreateFinish = false;
        this.mBundleUrl = "";
        this.mUniPagePath = "";
        this.isDestroy = false;
        this.hasException = false;
        this.isRenderSuccess = false;
        this.createInstanceHeartBeat = false;
        this.isCommit = false;
        this.mGlobalEventReceiver = null;
        this.enableLayerType = true;
        this.mNeedValidate = false;
        this.mNeedReLoad = false;
        this.mUseScroller = false;
        this.mInstanceViewPortWidth = 750.0f;
        this.enableFullScreenHeight = false;
        this.mFlatGUIContext = new FlatGUIContext();
        this.isNewFsEnd = false;
        this.componentsInfoExceedGPULimit = new LinkedList();
        this.mExecJSTraceId = WXTracing.nextId();
        this.isViewDisAppear = false;
        this.mwxDims = new String[5];
        this.measureTimes = new long[5];
        this.responseHeaders = new HashMap();
        this.mRenderStrategy = WXRenderStrategy.APPEND_ASYNC;
        this.mDisableSkipFrameworkInit = false;
        this.mRenderType = RenderTypes.RENDER_TYPE_NATIVE;
        this.mAutoAdjustDeviceWidth = WXEnvironment.AUTO_ADJUST_ENV_DEVICE_WIDTH;
        this.mCurrentGround = false;
        this.inactiveAddElementAction = new HashMap();
        this.mContentBoxMeasurements = new ArrayMap();
        this.maxHiddenEmbedsNum = -1;
        this.mVisibleListeners = new ArrayList();
        this.isPreInit = false;
        this.isPreDownLoad = false;
        this.mHttpListener = null;
        this.mCreateInstance = true;
        this.mGlobalEvents = new HashMap<>();
        this.mDefaultFontSize = 32;
        this.frameViewEventListeners = new ArrayList();
        this.isFrameShow = false;
        this.isImmersive = true;
        this.isOnSizeChangedRender = true;
        this.mInstanceId = WXSDKManager.getInstance().generateInstanceId();
        this.mOriginalContext = context2;
        init(context);
    }

    public WXSDKInstance() {
        this.mEnd = false;
        this.mHasCreateFinish = false;
        this.mBundleUrl = "";
        this.mUniPagePath = "";
        this.isDestroy = false;
        this.hasException = false;
        this.isRenderSuccess = false;
        this.createInstanceHeartBeat = false;
        this.isCommit = false;
        this.mGlobalEventReceiver = null;
        this.enableLayerType = true;
        this.mNeedValidate = false;
        this.mNeedReLoad = false;
        this.mUseScroller = false;
        this.mInstanceViewPortWidth = 750.0f;
        this.enableFullScreenHeight = false;
        this.mFlatGUIContext = new FlatGUIContext();
        this.isNewFsEnd = false;
        this.componentsInfoExceedGPULimit = new LinkedList();
        this.mExecJSTraceId = WXTracing.nextId();
        this.isViewDisAppear = false;
        this.mwxDims = new String[5];
        this.measureTimes = new long[5];
        this.responseHeaders = new HashMap();
        this.mRenderStrategy = WXRenderStrategy.APPEND_ASYNC;
        this.mDisableSkipFrameworkInit = false;
        this.mRenderType = RenderTypes.RENDER_TYPE_NATIVE;
        this.mAutoAdjustDeviceWidth = WXEnvironment.AUTO_ADJUST_ENV_DEVICE_WIDTH;
        this.mCurrentGround = false;
        this.inactiveAddElementAction = new HashMap();
        this.mContentBoxMeasurements = new ArrayMap();
        this.maxHiddenEmbedsNum = -1;
        this.mVisibleListeners = new ArrayList();
        this.isPreInit = false;
        this.isPreDownLoad = false;
        this.mHttpListener = null;
        this.mCreateInstance = true;
        this.mGlobalEvents = new HashMap<>();
        this.mDefaultFontSize = 32;
        this.frameViewEventListeners = new ArrayList();
        this.isFrameShow = false;
        this.isImmersive = true;
        this.isOnSizeChangedRender = true;
        String strGenerateInstanceId = WXSDKManager.getInstance().generateInstanceId();
        this.mInstanceId = strGenerateInstanceId;
        this.mWXPerformance = new WXPerformance(strGenerateInstanceId);
        this.mApmForInstance = new WXInstanceApm(strGenerateInstanceId);
        WXSDKManager.getInstance().getAllInstanceMap().put(strGenerateInstanceId, this);
    }

    public WXSDKInstance(Context context, String str) {
        this.mEnd = false;
        this.mHasCreateFinish = false;
        this.mBundleUrl = "";
        this.mUniPagePath = "";
        this.isDestroy = false;
        this.hasException = false;
        this.isRenderSuccess = false;
        this.createInstanceHeartBeat = false;
        this.isCommit = false;
        this.mGlobalEventReceiver = null;
        this.enableLayerType = true;
        this.mNeedValidate = false;
        this.mNeedReLoad = false;
        this.mUseScroller = false;
        this.mInstanceViewPortWidth = 750.0f;
        this.enableFullScreenHeight = false;
        this.mFlatGUIContext = new FlatGUIContext();
        this.isNewFsEnd = false;
        this.componentsInfoExceedGPULimit = new LinkedList();
        this.mExecJSTraceId = WXTracing.nextId();
        this.isViewDisAppear = false;
        this.mwxDims = new String[5];
        this.measureTimes = new long[5];
        this.responseHeaders = new HashMap();
        this.mRenderStrategy = WXRenderStrategy.APPEND_ASYNC;
        this.mDisableSkipFrameworkInit = false;
        this.mRenderType = RenderTypes.RENDER_TYPE_NATIVE;
        this.mAutoAdjustDeviceWidth = WXEnvironment.AUTO_ADJUST_ENV_DEVICE_WIDTH;
        this.mCurrentGround = false;
        this.inactiveAddElementAction = new HashMap();
        this.mContentBoxMeasurements = new ArrayMap();
        this.maxHiddenEmbedsNum = -1;
        this.mVisibleListeners = new ArrayList();
        this.isPreInit = false;
        this.isPreDownLoad = false;
        this.mHttpListener = null;
        this.mCreateInstance = true;
        this.mGlobalEvents = new HashMap<>();
        this.mDefaultFontSize = 32;
        this.frameViewEventListeners = new ArrayList();
        this.isFrameShow = false;
        this.isImmersive = true;
        this.isOnSizeChangedRender = true;
        this.mInstanceId = str;
        init(context);
    }
}
