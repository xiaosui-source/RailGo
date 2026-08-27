package com.taobao.weex.bridge;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import androidx.collection.ArrayMap;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.Script;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXConfigAdapter;
import com.taobao.weex.adapter.IWXJSExceptionAdapter;
import com.taobao.weex.adapter.IWXJsFileLoaderAdapter;
import com.taobao.weex.adapter.IWXJscProcessManager;
import com.taobao.weex.adapter.IWXUserTrackAdapter;
import com.taobao.weex.bridge.WXValidateProcessor;
import com.taobao.weex.common.Constants;
import com.taobao.weex.common.IWXBridge;
import com.taobao.weex.common.WXConfig;
import com.taobao.weex.common.WXErrorCode;
import com.taobao.weex.common.WXJSExceptionInfo;
import com.taobao.weex.common.WXRefreshData;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.common.WXRuntimeException;
import com.taobao.weex.common.WXThread;
import com.taobao.weex.dom.CSSShorthand;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.layout.ContentBoxMeasurement;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.performance.WXStateRecord;
import com.taobao.weex.ui.WXComponentRegistry;
import com.taobao.weex.ui.WXRenderManager;
import com.taobao.weex.ui.action.ActionReloadPage;
import com.taobao.weex.ui.action.GraphicActionAddChildToRichtext;
import com.taobao.weex.ui.action.GraphicActionAddElement;
import com.taobao.weex.ui.action.GraphicActionAddEvent;
import com.taobao.weex.ui.action.GraphicActionAppendTreeCreateFinish;
import com.taobao.weex.ui.action.GraphicActionBatchBegin;
import com.taobao.weex.ui.action.GraphicActionBatchEnd;
import com.taobao.weex.ui.action.GraphicActionCreateBody;
import com.taobao.weex.ui.action.GraphicActionCreateFinish;
import com.taobao.weex.ui.action.GraphicActionLayout;
import com.taobao.weex.ui.action.GraphicActionMoveElement;
import com.taobao.weex.ui.action.GraphicActionRefreshFinish;
import com.taobao.weex.ui.action.GraphicActionRemoveChildFromRichtext;
import com.taobao.weex.ui.action.GraphicActionRemoveElement;
import com.taobao.weex.ui.action.GraphicActionRemoveEvent;
import com.taobao.weex.ui.action.GraphicActionRenderSuccess;
import com.taobao.weex.ui.action.GraphicActionUpdateAttr;
import com.taobao.weex.ui.action.GraphicActionUpdateRichtextAttr;
import com.taobao.weex.ui.action.GraphicActionUpdateRichtextStyle;
import com.taobao.weex.ui.action.GraphicActionUpdateStyle;
import com.taobao.weex.ui.action.GraphicPosition;
import com.taobao.weex.ui.action.GraphicSize;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.module.WXDomModule;
import com.taobao.weex.utils.WXExceptionUtils;
import com.taobao.weex.utils.WXFileUtils;
import com.taobao.weex.utils.WXJsonUtils;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import com.taobao.weex.utils.WXWsonJSONSwitch;
import com.taobao.weex.utils.batch.BactchExecutor;
import com.taobao.weex.utils.batch.Interceptor;
import com.taobao.weex.utils.tools.LogDetail;
import io.dcloud.common.adapter.ui.webview.WebLoadEvent;
import io.dcloud.common.adapter.util.UEH;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.JSONUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.RuningAcitvityUtil;
import io.dcloud.common.util.StringUtil;
import io.dcloud.weex.WXDotDataUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXBridgeManager implements Handler.Callback, BactchExecutor {
    public static final String ARGS = "args";
    private static final boolean BRIDGE_LOG_SWITCH = false;
    private static final String BUNDLE_TYPE = "bundleType";
    public static final String COMPONENT = "component";
    private static final int CRASHREINIT = 50;
    private static String GLOBAL_CONFIG_KEY = "global_switch_config";
    public static final String INITLOGFILE = "/jsserver_start.log";
    private static final int INIT_FRAMEWORK_OK = 1;
    public static final String KEY_ARGS = "args";
    public static final String KEY_METHOD = "method";
    public static final String KEY_PARAMS = "params";
    private static long LOW_MEM_VALUE = 120;
    public static final String METHD_COMPONENT_HOOK_SYNC = "componentHook";
    public static final String METHD_FIRE_EVENT_SYNC = "fireEventSync";
    public static final String METHOD = "method";
    public static final String METHOD_CALLBACK = "callback";
    public static final String METHOD_CALL_JS = "callJS";
    public static final String METHOD_CHECK_APPKEY = "dc_checkappkey";
    public static final String METHOD_CREATE_INSTANCE = "createInstance";
    public static final String METHOD_CREATE_INSTANCE_CONTEXT = "createInstanceContext";
    public static final String METHOD_CREATE_PAGE_WITH_CONTENT = "CreatePageWithContent";
    public static final String METHOD_DESTROY_INSTANCE = "destroyInstance";
    public static final String METHOD_FIRE_EVENT = "fireEvent";
    public static final String METHOD_FIRE_EVENT_ON_DATA_RENDER_NODE = "fireEventOnDataRenderNode";
    private static final String METHOD_JSFM_NOT_INIT_IN_EAGLE_MODE = "JsfmNotInitInEagleMode";
    public static final String METHOD_NOTIFY_SERIALIZE_CODE_CACHE = "notifySerializeCodeCache";
    public static final String METHOD_NOTIFY_TRIM_MEMORY = "notifyTrimMemory";
    private static final String METHOD_POST_TASK_TO_MSG_LOOP = "PostTaskToMsgLoop";
    public static final String METHOD_REFRESH_INSTANCE = "refreshInstance";
    public static final String METHOD_REGISTER_COMPONENTS = "registerComponents";
    public static final String METHOD_REGISTER_MODULES = "registerModules";
    public static final String METHOD_SET_TIMEOUT = "setTimeoutCallback";
    public static final String METHOD_UPDATE_COMPONENT_WITH_DATA = "UpdateComponentData";
    public static final String MODULE = "module";
    private static final String NON_CALLBACK = "-1";
    public static final String OPTIONS = "options";
    public static final String REF = "ref";
    private static final String RENDER_STRATEGY = "renderStrategy";
    private static final String UNDEFINED = "undefined";
    private static Class clazz_debugProxy = null;
    private static String crashUrl = null;
    private static String globalConfig = "none";
    private static volatile boolean isJsEngineMultiThreadEnable = false;
    private static volatile boolean isSandBoxContext = true;
    private static boolean isUseSingleProcess = false;
    private static long lastCrashTime = 0;
    static volatile WXBridgeManager mBridgeManager = null;
    private static volatile boolean mInit = false;
    private static String mRaxApi = null;
    public static volatile int reInitCount = 1;
    public static long sInitFrameWorkTimeOrigin;
    private WXParams mInitParams;
    private Interceptor mInterceptor;
    Handler mJSHandler;
    private WXThread mJSThread;
    private IWXBridge mWXBridge;
    private Object mWxDebugProxy;
    private static Map<String, String> mWeexCoreEnvOptions = new HashMap();
    public static StringBuilder sInitFrameWorkMsg = new StringBuilder();
    private WXHashMap<String, ArrayList<WXHashMap<String, Object>>> mNextTickTasks = new WXHashMap<>();
    private boolean mMock = false;
    private List<Map<String, Object>> mRegisterComponentFailList = new ArrayList(8);
    private List<Map<String, Object>> mRegisterModuleFailList = new ArrayList(8);
    private List<String> mRegisterServiceFailList = new ArrayList(8);
    private HashSet<String> mDestroyedInstanceId = new HashSet<>();
    private StringBuilder mLodBuilder = new StringBuilder(50);

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public enum BundType {
        Vue,
        Rax,
        Others
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public static class TimerInfo {
        public String callbackId;
        public String instanceId;
        public long time;
    }

    private WXBridgeManager() {
        initWXBridge(WXEnvironment.sRemoteDebugMode);
        WXThread wXThread = new WXThread("WeexJSBridgeThread", this);
        this.mJSThread = wXThread;
        this.mJSHandler = wXThread.getHandler();
    }

    private void addJSEventTask(final String str, final String str2, final List<Object> list, final Object... objArr) {
        post(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.13
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                Object[] objArr2 = objArr;
                if (objArr2 == null || objArr2.length == 0) {
                    return;
                }
                ArrayList arrayList = new ArrayList();
                for (Object obj : objArr) {
                    arrayList.add(obj);
                }
                if (list != null) {
                    ArrayMap arrayMap = new ArrayMap(4);
                    arrayMap.put("params", list);
                    arrayList.add(arrayMap);
                }
                WXHashMap wXHashMap = new WXHashMap();
                wXHashMap.put("method", str);
                wXHashMap.put("args", arrayList);
                if (WXBridgeManager.this.mNextTickTasks.get(str2) != 0) {
                    ((ArrayList) WXBridgeManager.this.mNextTickTasks.get(str2)).add(wXHashMap);
                    return;
                }
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(wXHashMap);
                WXBridgeManager.this.mNextTickTasks.put(str2, arrayList2);
            }
        });
    }

    private void addJSTask(String str, String str2, Object... objArr) {
        addJSEventTask(str, str2, null, objArr);
    }

    public static String argsToJSON(WXJSObject[] wXJSObjectArr) {
        StringBuilder sb = new StringBuilder(Operators.ARRAY_START_STR);
        for (WXJSObject wXJSObject : wXJSObjectArr) {
            sb.append(WXWsonJSONSwitch.fromObjectToJSONString(wXJSObject));
            sb.append(",");
        }
        sb.append(Operators.ARRAY_END_STR);
        return sb.toString();
    }

    private WXParams assembleDefaultOptions() {
        checkJsEngineMultiThread();
        Map<String, String> config = WXEnvironment.getConfig();
        WXParams wXParams = new WXParams();
        wXParams.setPlatform(config.get(WXConfig.os));
        wXParams.setCacheDir(config.get(WXConfig.cacheDir));
        wXParams.setOsVersion(config.get(WXConfig.sysVersion));
        wXParams.setAppVersion(config.get(WXConfig.appVersion));
        wXParams.setWeexVersion(config.get(WXConfig.weexVersion));
        wXParams.setDeviceModel(config.get(WXConfig.sysModel));
        wXParams.setShouldInfoCollect(config.get("infoCollect"));
        wXParams.setLogLevel(config.get(WXConfig.logLevel));
        wXParams.setLayoutDirection(config.get(WXConfig.layoutDirection));
        wXParams.setUseSingleProcess(isUseSingleProcess ? AbsoluteConst.TRUE : AbsoluteConst.FALSE);
        wXParams.setCrashFilePath(WXEnvironment.getCrashFilePath(WXEnvironment.getApplication().getApplicationContext()));
        wXParams.setLibJsbPath(WXEnvironment.CORE_JSB_SO_PATH);
        wXParams.setLibJssPath(WXEnvironment.getLibJssRealPath());
        wXParams.setLibIcuPath(WXEnvironment.getLibJssIcuPath());
        wXParams.setLibLdPath(WXEnvironment.getLibLdPath());
        String libJScRealPath = WXEnvironment.getLibJScRealPath();
        wXParams.setLibJscPath(TextUtils.isEmpty(libJScRealPath) ? "" : new File(libJScRealPath).getParent());
        String str = config.get(WXConfig.appName);
        if (!TextUtils.isEmpty(str)) {
            wXParams.setAppName(str);
        }
        wXParams.setDeviceWidth(TextUtils.isEmpty(config.get(WXConfig.deviceWidth)) ? String.valueOf(WXViewUtils.getScreenWidth(WXEnvironment.sApplication)) : config.get(WXConfig.deviceWidth));
        wXParams.setDeviceHeight(TextUtils.isEmpty(config.get(WXConfig.deviceHeight)) ? String.valueOf(WXViewUtils.getScreenHeight(WXEnvironment.sApplication)) : config.get(WXConfig.deviceHeight));
        Map<String, String> customOptions = WXEnvironment.getCustomOptions();
        customOptions.put("enableBackupThread", String.valueOf(jsEngineMultiThreadEnable()));
        IWXJscProcessManager wXJscProcessManager = WXSDKManager.getInstance().getWXJscProcessManager();
        if (wXJscProcessManager != null) {
            customOptions.put("enableBackupThreadCache", String.valueOf(wXJscProcessManager.enableBackUpThreadCache()));
        }
        if (!WXEnvironment.sUseRunTimeApi) {
            customOptions.put("__enable_native_promise__", AbsoluteConst.TRUE);
        }
        wXParams.setOptions(customOptions);
        wXParams.setNeedInitV8(WXSDKManager.getInstance().needInitV8());
        this.mInitParams = wXParams;
        return wXParams;
    }

    private void asyncCallJSEventWithResult(final EventResult eventResult, final String str, final String str2, final List<Object> list, final Object... objArr) {
        post(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.12
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Object[] objArr2 = objArr;
                    if (objArr2 != null && objArr2.length != 0) {
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : objArr) {
                            arrayList.add(obj);
                        }
                        if (list != null) {
                            ArrayMap arrayMap = new ArrayMap(4);
                            arrayMap.put("params", list);
                            arrayList.add(arrayMap);
                        }
                        WXHashMap wXHashMap = new WXHashMap();
                        wXHashMap.put("method", str);
                        wXHashMap.put("args", arrayList);
                        WXJSObject[] wXJSObjectArr = {new WXJSObject(2, str2), WXWsonJSONSwitch.toWsonOrJsonWXJSObject(new Object[]{wXHashMap})};
                        WXBridgeManager.this.invokeExecJSWithCallback(String.valueOf(str2), null, WXBridgeManager.METHOD_CALL_JS, wXJSObjectArr, eventResult != null ? new ResultCallback<byte[]>() { // from class: com.taobao.weex.bridge.WXBridgeManager.12.1
                            @Override // com.taobao.weex.bridge.ResultCallback
                            public void onReceiveResult(byte[] bArr) {
                                JSONArray jSONArray = (JSONArray) WXWsonJSONSwitch.parseWsonOrJSON(bArr);
                                if (jSONArray == null || jSONArray.size() <= 0) {
                                    return;
                                }
                                eventResult.onCallback(jSONArray.get(0));
                            }
                        } : null, true);
                        wXJSObjectArr[0] = null;
                    }
                } catch (Exception e) {
                    WXLogUtils.e("asyncCallJSEventWithResult", e);
                }
            }
        });
    }

    private boolean checkMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00c7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void doReportJSException(java.lang.String r10, java.lang.String r11, com.taobao.weex.common.WXErrorCode r12, java.lang.String r13) {
        /*
            Method dump skipped, instructions count: 269
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.bridge.WXBridgeManager.doReportJSException(java.lang.String, java.lang.String, com.taobao.weex.common.WXErrorCode, java.lang.String):void");
    }

    private void execJSOnInstance(final EventResult eventResult, final String str, final String str2, final int i) {
        post(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.21
            @Override // java.lang.Runnable
            public void run() {
                eventResult.onCallback(WXBridgeManager.this.invokeExecJSOnInstance(str, str2, i));
            }
        });
    }

    private void execRegisterFailTask() {
        if (this.mRegisterModuleFailList.size() > 0) {
            ArrayList arrayList = new ArrayList();
            int size = this.mRegisterModuleFailList.size();
            for (int i = 0; i < size; i++) {
                invokeRegisterModules(this.mRegisterModuleFailList.get(i), arrayList);
            }
            this.mRegisterModuleFailList.clear();
            if (arrayList.size() > 0) {
                this.mRegisterModuleFailList.addAll(arrayList);
            }
        }
        if (this.mRegisterComponentFailList.size() > 0) {
            ArrayList arrayList2 = new ArrayList();
            invokeRegisterComponents(this.mRegisterComponentFailList, arrayList2);
            this.mRegisterComponentFailList.clear();
            if (arrayList2.size() > 0) {
                this.mRegisterComponentFailList.addAll(arrayList2);
            }
        }
        if (this.mRegisterServiceFailList.size() > 0) {
            ArrayList arrayList3 = new ArrayList();
            Iterator<String> it = this.mRegisterServiceFailList.iterator();
            while (it.hasNext()) {
                invokeExecJSService(it.next(), arrayList3);
            }
            this.mRegisterServiceFailList.clear();
            if (arrayList3.size() > 0) {
                this.mRegisterServiceFailList.addAll(arrayList3);
            }
        }
    }

    private Pair<Pair<String, Object>, Boolean> extractCallbackArgs(String str) {
        try {
            JSONObject jSONObject = JSON.parseArray(str).getJSONObject(0);
            JSONArray jSONArray = jSONObject.getJSONArray("args");
            if (jSONArray.size() == 3 && METHOD_CALLBACK.equals(jSONObject.getString("method"))) {
                return new Pair<>(new Pair(jSONArray.getString(0), jSONArray.getJSONObject(1)), Boolean.valueOf(jSONArray.getBooleanValue(2)));
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    private void fireEventOnDataRenderNode(final String str, final String str2, final String str3, final Map<String, Object> map, final Map<String, Object> map2) {
        this.mJSHandler.postDelayed(WXThread.secure(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.14
            @Override // java.lang.Runnable
            public void run() {
                try {
                    WXSDKManager.getInstance().getSDKInstance(str);
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    if (WXEnvironment.isApkDebugable()) {
                        WXLogUtils.d("fireEventOnDataRenderNode >>>> instanceId:" + str + ", data:" + map);
                    }
                    if (WXBridgeManager.this.mWXBridge instanceof WXBridge) {
                        WXBridge wXBridge = (WXBridge) WXBridgeManager.this.mWXBridge;
                        String str4 = str;
                        String str5 = str2;
                        String str6 = str3;
                        Map map3 = map;
                        String jSONString = "{}";
                        String jSONString2 = (map3 == null || map3.isEmpty()) ? "{}" : JSON.toJSONString(map);
                        Map map4 = map2;
                        if (map4 != null && !map4.isEmpty()) {
                            jSONString = JSON.toJSONString(map2);
                        }
                        wXBridge.fireEventOnDataRenderNode(str4, str5, str6, jSONString2, jSONString);
                    }
                    WXLogUtils.renderPerformanceLog(WXBridgeManager.METHOD_FIRE_EVENT_ON_DATA_RENDER_NODE, System.currentTimeMillis() - jCurrentTimeMillis);
                } catch (Throwable th) {
                    String str7 = "[WXBridgeManager] fireEventOnDataRenderNode " + WXLogUtils.getStackTrace(th);
                    WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, WXBridgeManager.METHOD_FIRE_EVENT_ON_DATA_RENDER_NODE, str7, null);
                    WXLogUtils.e(str7);
                }
            }
        }), 0L);
    }

    public static WXBridgeManager getInstance() {
        if (mBridgeManager == null) {
            synchronized (WXBridgeManager.class) {
                if (mBridgeManager == null) {
                    mBridgeManager = new WXBridgeManager();
                }
            }
        }
        return mBridgeManager;
    }

    private void getNextTick(String str, String str2) {
        addJSTask(METHOD_CALLBACK, str, str2, "{}");
        sendMessage(str, 6);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initFramework(String str) {
        LogDetail logDetail = new LogDetail();
        logDetail.name(IWXUserTrackAdapter.INIT_FRAMEWORK);
        logDetail.taskStart();
        if (!WXSDKEngine.isSoInitialized() || isJSFrameworkInit()) {
            return;
        }
        sInitFrameWorkTimeOrigin = System.currentTimeMillis();
        if (TextUtils.isEmpty(str)) {
            WXLogUtils.d("weex JS framework from assets");
            LogDetail logDetail2 = new LogDetail();
            logDetail2.name("loadJSFramework");
            logDetail2.taskStart();
            IWXJsFileLoaderAdapter iWXJsFileLoaderAdapter = WXSDKEngine.getIWXJsFileLoaderAdapter();
            if (isSandBoxContext) {
                if (iWXJsFileLoaderAdapter != null) {
                    str = iWXJsFileLoaderAdapter.loadJsFrameworkForSandBox();
                }
                if (TextUtils.isEmpty(str)) {
                    str = WXFileUtils.loadAsset("weex-main-jsfm.js", WXEnvironment.getApplication());
                }
            } else {
                if (iWXJsFileLoaderAdapter != null) {
                    str = iWXJsFileLoaderAdapter.loadJsFramework();
                }
                if (TextUtils.isEmpty(str)) {
                    str = WXFileUtils.loadAsset("main.js", WXEnvironment.getApplication());
                }
            }
            StringBuilder sb = sInitFrameWorkMsg;
            sb.append("| weex JS framework from assets, isSandBoxContext: ");
            sb.append(isSandBoxContext);
            logDetail2.taskEnd();
            WXDotDataUtil.setValue(logDetail2.info.taskName, Long.valueOf(logDetail2.time.execTime));
        }
        WXDotDataUtil.setValue("jsEngine", Constants.CodeCache.SAVE_PATH);
        if (TextUtils.isEmpty(str)) {
            setJSFrameworkInit(false);
            sInitFrameWorkMsg.append("| framework isEmpty ");
            WXExceptionUtils.commitCriticalExceptionRT(null, WXErrorCode.WX_ERR_JS_FRAMEWORK, IWXUserTrackAdapter.INIT_FRAMEWORK, "framework is empty!! ", null);
            return;
        }
        try {
            if (WXSDKManager.getInstance().getWXStatisticsListener() != null) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                WXSDKManager.getInstance().getWXStatisticsListener().onJsFrameworkStart();
                WXEnvironment.sJSFMStartListenerTime = System.currentTimeMillis() - jCurrentTimeMillis;
                try {
                    IWXUserTrackAdapter iWXUserTrackAdapter = WXSDKManager.getInstance().getIWXUserTrackAdapter();
                    if (iWXUserTrackAdapter != null) {
                        HashMap map = new HashMap(1);
                        map.put(Constants.Value.TIME, String.valueOf(WXEnvironment.sJSFMStartListenerTime));
                        iWXUserTrackAdapter.commit(WXEnvironment.getApplication(), "sJSFMStartListener", IWXUserTrackAdapter.COUNTER, null, map);
                    }
                } catch (Exception e) {
                    WXLogUtils.e(WXLogUtils.getStackTrace(e));
                }
            }
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            String path = "";
            try {
                path = WXEnvironment.getApplication().getApplicationContext().getCacheDir().getPath();
            } catch (Exception e2) {
                WXLogUtils.e(WXLogUtils.getStackTrace(e2));
            }
            StringBuilder sb2 = sInitFrameWorkMsg;
            sb2.append(" | pieSupport:");
            sb2.append(true);
            WXLogUtils.d("[WXBridgeManager] initFrameworkEnv crashFile:" + path + " pieSupport:true");
            LogDetail logDetail3 = new LogDetail();
            logDetail3.name("native initFrameworkEnv");
            logDetail3.taskStart();
            if (this.mWXBridge.initFrameworkEnv(str, assembleDefaultOptions(), path, true) != 1) {
                StringBuilder sb3 = sInitFrameWorkMsg;
                sb3.append(" | ExecuteJavaScript fail, reInitCount");
                sb3.append(reInitCount);
                if (reInitCount > 1) {
                    WXLogUtils.e("[WXBridgeManager] invokeReInitFramework  ExecuteJavaScript fail");
                    return;
                } else {
                    WXLogUtils.e("[WXBridgeManager] invokeInitFramework  ExecuteJavaScript fail");
                    return;
                }
            }
            logDetail3.taskEnd();
            WXDotDataUtil.setValue(logDetail3.info.taskName, Long.valueOf(logDetail3.time.execTime));
            WXEnvironment.sJSLibInitTime = System.currentTimeMillis() - jCurrentTimeMillis2;
            WXEnvironment.sSDKInitTime = System.currentTimeMillis() - WXEnvironment.sSDKInitStart;
            setJSFrameworkInit(true);
            logDetail.taskEnd();
            WXDotDataUtil.setValue(logDetail.info.taskName, Long.valueOf(logDetail.time.execTime));
            if (WXSDKManager.getInstance().getWXStatisticsListener() != null) {
                WXSDKManager.getInstance().getWXStatisticsListener().onJsFrameworkReady();
            }
            execRegisterFailTask();
            WXEnvironment.JsFrameworkInit = true;
            registerDomModule();
            trackComponentAndModulesTime();
        } catch (Throwable th) {
            StringBuilder sb4 = sInitFrameWorkMsg;
            sb4.append(" | invokeInitFramework exception ");
            sb4.append(th.toString());
            if (reInitCount > 1) {
                WXLogUtils.e("[WXBridgeManager] invokeInitFramework ", th);
            } else {
                WXLogUtils.e("[WXBridgeManager] invokeInitFramework ", th);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0047 A[Catch: all -> 0x0080, TryCatch #0 {all -> 0x0080, blocks: (B:11:0x001b, B:13:0x001f, B:14:0x0025, B:16:0x0029, B:18:0x002e, B:20:0x0036, B:24:0x0047, B:26:0x005a, B:28:0x0071, B:30:0x007b), top: B:49:0x001b }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void initWXBridge(boolean r9) {
        /*
            r8 = this;
            r0 = 1
            if (r9 == 0) goto Lb
            boolean r1 = com.taobao.weex.WXEnvironment.isApkDebugable()
            if (r1 == 0) goto Lb
            com.taobao.weex.WXEnvironment.sDebugServerConnectable = r0
        Lb:
            boolean r1 = com.taobao.weex.WXEnvironment.sDebugServerConnectable
            java.lang.String r2 = "com.taobao.weex.devtools.debug.DebugServerProxy"
            r3 = 0
            if (r1 == 0) goto L8b
            com.taobao.weex.WXEnvironment.isApkDebugable()
            android.app.Application r1 = com.taobao.weex.WXEnvironment.getApplication()
            if (r1 == 0) goto L84
            java.lang.Class r1 = com.taobao.weex.bridge.WXBridgeManager.clazz_debugProxy     // Catch: java.lang.Throwable -> L80
            if (r1 != 0) goto L25
            java.lang.Class r1 = java.lang.Class.forName(r2)     // Catch: java.lang.Throwable -> L80
            com.taobao.weex.bridge.WXBridgeManager.clazz_debugProxy = r1     // Catch: java.lang.Throwable -> L80
        L25:
            java.lang.Class r1 = com.taobao.weex.bridge.WXBridgeManager.clazz_debugProxy     // Catch: java.lang.Throwable -> L80
            if (r1 == 0) goto L80
            java.lang.Object r4 = r8.mWxDebugProxy     // Catch: java.lang.Throwable -> L80
            r5 = 0
            if (r4 == 0) goto L47
            java.lang.String r4 = "isActive"
            java.lang.reflect.Method r1 = r1.getMethod(r4, r3)     // Catch: java.lang.Throwable -> L80
            if (r1 == 0) goto L43
            java.lang.Object r4 = r8.mWxDebugProxy     // Catch: java.lang.Throwable -> L80
            java.lang.Object r1 = r1.invoke(r4, r3)     // Catch: java.lang.Throwable -> L80
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch: java.lang.Throwable -> L80
            boolean r1 = r1.booleanValue()     // Catch: java.lang.Throwable -> L80
            goto L44
        L43:
            r1 = 0
        L44:
            if (r1 == 0) goto L47
            goto L80
        L47:
            java.lang.Class r1 = com.taobao.weex.bridge.WXBridgeManager.clazz_debugProxy     // Catch: java.lang.Throwable -> L80
            r4 = 2
            java.lang.Class[] r6 = new java.lang.Class[r4]     // Catch: java.lang.Throwable -> L80
            java.lang.Class<android.content.Context> r7 = android.content.Context.class
            r6[r5] = r7     // Catch: java.lang.Throwable -> L80
            java.lang.Class<com.taobao.weex.common.IWXDebugConfig> r7 = com.taobao.weex.common.IWXDebugConfig.class
            r6[r0] = r7     // Catch: java.lang.Throwable -> L80
            java.lang.reflect.Constructor r1 = r1.getConstructor(r6)     // Catch: java.lang.Throwable -> L80
            if (r1 == 0) goto L80
            android.app.Application r6 = com.taobao.weex.WXEnvironment.getApplication()     // Catch: java.lang.Throwable -> L80
            com.taobao.weex.bridge.WXBridgeManager$4 r7 = new com.taobao.weex.bridge.WXBridgeManager$4     // Catch: java.lang.Throwable -> L80
            r7.<init>()     // Catch: java.lang.Throwable -> L80
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> L80
            r4[r5] = r6     // Catch: java.lang.Throwable -> L80
            r4[r0] = r7     // Catch: java.lang.Throwable -> L80
            java.lang.Object r0 = r1.newInstance(r4)     // Catch: java.lang.Throwable -> L80
            r8.mWxDebugProxy = r0     // Catch: java.lang.Throwable -> L80
            if (r0 == 0) goto L80
            java.lang.Class r0 = com.taobao.weex.bridge.WXBridgeManager.clazz_debugProxy     // Catch: java.lang.Throwable -> L80
            java.lang.String r1 = "start"
            java.lang.reflect.Method r0 = r0.getMethod(r1, r3)     // Catch: java.lang.Throwable -> L80
            if (r0 == 0) goto L80
            java.lang.Object r1 = r8.mWxDebugProxy     // Catch: java.lang.Throwable -> L80
            r0.invoke(r1, r3)     // Catch: java.lang.Throwable -> L80
        L80:
            com.taobao.weex.bridge.WXServiceManager.execAllCacheJsService()
            goto L8b
        L84:
            java.lang.String r0 = "WXBridgeManager"
            java.lang.String r1 = "WXEnvironment.sApplication is null, skip init Inspector"
            com.taobao.weex.utils.WXLogUtils.e(r0, r1)
        L8b:
            if (r9 == 0) goto Lb2
            java.lang.Object r9 = r8.mWxDebugProxy
            if (r9 == 0) goto Lb2
            java.lang.Class r9 = com.taobao.weex.bridge.WXBridgeManager.clazz_debugProxy     // Catch: java.lang.Throwable -> Lb9
            if (r9 != 0) goto L9b
            java.lang.Class r9 = java.lang.Class.forName(r2)     // Catch: java.lang.Throwable -> Lb9
            com.taobao.weex.bridge.WXBridgeManager.clazz_debugProxy = r9     // Catch: java.lang.Throwable -> Lb9
        L9b:
            java.lang.Class r9 = com.taobao.weex.bridge.WXBridgeManager.clazz_debugProxy     // Catch: java.lang.Throwable -> Lb9
            if (r9 == 0) goto Lb9
            java.lang.String r0 = "getWXBridge"
            java.lang.reflect.Method r9 = r9.getMethod(r0, r3)     // Catch: java.lang.Throwable -> Lb9
            if (r9 == 0) goto Lb9
            java.lang.Object r0 = r8.mWxDebugProxy     // Catch: java.lang.Throwable -> Lb9
            java.lang.Object r9 = r9.invoke(r0, r3)     // Catch: java.lang.Throwable -> Lb9
            com.taobao.weex.common.IWXBridge r9 = (com.taobao.weex.common.IWXBridge) r9     // Catch: java.lang.Throwable -> Lb9
            r8.mWXBridge = r9     // Catch: java.lang.Throwable -> Lb9
            goto Lb9
        Lb2:
            com.taobao.weex.bridge.WXBridge r9 = new com.taobao.weex.bridge.WXBridge
            r9.<init>()
            r8.mWXBridge = r9
        Lb9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.bridge.WXBridgeManager.initWXBridge(boolean):void");
    }

    private void invokeCallJSBatch(Message message) {
        if (this.mNextTickTasks.isEmpty() || !isJSFrameworkInit()) {
            if (isJSFrameworkInit()) {
                return;
            }
            WXLogUtils.e("[WXBridgeManager] invokeCallJSBatch: framework.js uninitialized!!  message:" + message.toString());
            return;
        }
        try {
            Object obj = message.obj;
            Stack<String> instanceStack = this.mNextTickTasks.getInstanceStack();
            ArrayList<WXHashMap<String, Object>> arrayListRemove = null;
            for (int size = instanceStack.size() - 1; size >= 0; size--) {
                obj = instanceStack.get(size);
                arrayListRemove = this.mNextTickTasks.remove(obj);
                if (arrayListRemove != null && !arrayListRemove.isEmpty()) {
                    break;
                }
            }
            if (arrayListRemove != null) {
                invokeExecJS(String.valueOf(obj), null, METHOD_CALL_JS, new WXJSObject[]{new WXJSObject(2, obj), WXWsonJSONSwitch.toWsonOrJsonWXJSObject(arrayListRemove.toArray())});
            }
        } catch (Throwable th) {
            WXLogUtils.e("WXBridgeManager", th);
            WXExceptionUtils.commitCriticalExceptionRT(null, WXErrorCode.WX_ERR_JS_FRAMEWORK, "invokeCallJSBatch", "invokeCallJSBatch#" + WXLogUtils.getStackTrace(th), null);
        }
        if (this.mNextTickTasks.isEmpty()) {
            return;
        }
        this.mJSHandler.sendEmptyMessage(6);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0221 A[Catch: all -> 0x02b8, TryCatch #3 {all -> 0x02b8, blocks: (B:16:0x004a, B:52:0x00f0, B:54:0x010a, B:56:0x0110, B:58:0x0119, B:63:0x0126, B:67:0x0134, B:71:0x0144, B:73:0x014b, B:76:0x0154, B:86:0x0186, B:88:0x018e, B:96:0x01c7, B:99:0x01f2, B:100:0x0221, B:103:0x0228, B:105:0x022e, B:107:0x0236, B:110:0x023f, B:111:0x0255, B:113:0x0270, B:89:0x019a, B:91:0x01a4, B:92:0x01b4, B:94:0x01bc, B:77:0x015a, B:79:0x015e, B:81:0x0164, B:82:0x016a, B:84:0x0172, B:85:0x017f, B:66:0x0130, B:62:0x0122, B:51:0x00e9, B:22:0x0084, B:17:0x004c, B:19:0x0062), top: B:124:0x004a, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x010a A[Catch: all -> 0x02b8, TryCatch #3 {all -> 0x02b8, blocks: (B:16:0x004a, B:52:0x00f0, B:54:0x010a, B:56:0x0110, B:58:0x0119, B:63:0x0126, B:67:0x0134, B:71:0x0144, B:73:0x014b, B:76:0x0154, B:86:0x0186, B:88:0x018e, B:96:0x01c7, B:99:0x01f2, B:100:0x0221, B:103:0x0228, B:105:0x022e, B:107:0x0236, B:110:0x023f, B:111:0x0255, B:113:0x0270, B:89:0x019a, B:91:0x01a4, B:92:0x01b4, B:94:0x01bc, B:77:0x015a, B:79:0x015e, B:81:0x0164, B:82:0x016a, B:84:0x0172, B:85:0x017f, B:66:0x0130, B:62:0x0122, B:51:0x00e9, B:22:0x0084, B:17:0x004c, B:19:0x0062), top: B:124:0x004a, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0122 A[Catch: all -> 0x02b8, TRY_ENTER, TryCatch #3 {all -> 0x02b8, blocks: (B:16:0x004a, B:52:0x00f0, B:54:0x010a, B:56:0x0110, B:58:0x0119, B:63:0x0126, B:67:0x0134, B:71:0x0144, B:73:0x014b, B:76:0x0154, B:86:0x0186, B:88:0x018e, B:96:0x01c7, B:99:0x01f2, B:100:0x0221, B:103:0x0228, B:105:0x022e, B:107:0x0236, B:110:0x023f, B:111:0x0255, B:113:0x0270, B:89:0x019a, B:91:0x01a4, B:92:0x01b4, B:94:0x01bc, B:77:0x015a, B:79:0x015e, B:81:0x0164, B:82:0x016a, B:84:0x0172, B:85:0x017f, B:66:0x0130, B:62:0x0122, B:51:0x00e9, B:22:0x0084, B:17:0x004c, B:19:0x0062), top: B:124:0x004a, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0130 A[Catch: all -> 0x02b8, TryCatch #3 {all -> 0x02b8, blocks: (B:16:0x004a, B:52:0x00f0, B:54:0x010a, B:56:0x0110, B:58:0x0119, B:63:0x0126, B:67:0x0134, B:71:0x0144, B:73:0x014b, B:76:0x0154, B:86:0x0186, B:88:0x018e, B:96:0x01c7, B:99:0x01f2, B:100:0x0221, B:103:0x0228, B:105:0x022e, B:107:0x0236, B:110:0x023f, B:111:0x0255, B:113:0x0270, B:89:0x019a, B:91:0x01a4, B:92:0x01b4, B:94:0x01bc, B:77:0x015a, B:79:0x015e, B:81:0x0164, B:82:0x016a, B:84:0x0172, B:85:0x017f, B:66:0x0130, B:62:0x0122, B:51:0x00e9, B:22:0x0084, B:17:0x004c, B:19:0x0062), top: B:124:0x004a, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x014b A[Catch: all -> 0x02b8, TryCatch #3 {all -> 0x02b8, blocks: (B:16:0x004a, B:52:0x00f0, B:54:0x010a, B:56:0x0110, B:58:0x0119, B:63:0x0126, B:67:0x0134, B:71:0x0144, B:73:0x014b, B:76:0x0154, B:86:0x0186, B:88:0x018e, B:96:0x01c7, B:99:0x01f2, B:100:0x0221, B:103:0x0228, B:105:0x022e, B:107:0x0236, B:110:0x023f, B:111:0x0255, B:113:0x0270, B:89:0x019a, B:91:0x01a4, B:92:0x01b4, B:94:0x01bc, B:77:0x015a, B:79:0x015e, B:81:0x0164, B:82:0x016a, B:84:0x0172, B:85:0x017f, B:66:0x0130, B:62:0x0122, B:51:0x00e9, B:22:0x0084, B:17:0x004c, B:19:0x0062), top: B:124:0x004a, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x015a A[Catch: all -> 0x02b8, TryCatch #3 {all -> 0x02b8, blocks: (B:16:0x004a, B:52:0x00f0, B:54:0x010a, B:56:0x0110, B:58:0x0119, B:63:0x0126, B:67:0x0134, B:71:0x0144, B:73:0x014b, B:76:0x0154, B:86:0x0186, B:88:0x018e, B:96:0x01c7, B:99:0x01f2, B:100:0x0221, B:103:0x0228, B:105:0x022e, B:107:0x0236, B:110:0x023f, B:111:0x0255, B:113:0x0270, B:89:0x019a, B:91:0x01a4, B:92:0x01b4, B:94:0x01bc, B:77:0x015a, B:79:0x015e, B:81:0x0164, B:82:0x016a, B:84:0x0172, B:85:0x017f, B:66:0x0130, B:62:0x0122, B:51:0x00e9, B:22:0x0084, B:17:0x004c, B:19:0x0062), top: B:124:0x004a, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x015e A[Catch: all -> 0x02b8, TryCatch #3 {all -> 0x02b8, blocks: (B:16:0x004a, B:52:0x00f0, B:54:0x010a, B:56:0x0110, B:58:0x0119, B:63:0x0126, B:67:0x0134, B:71:0x0144, B:73:0x014b, B:76:0x0154, B:86:0x0186, B:88:0x018e, B:96:0x01c7, B:99:0x01f2, B:100:0x0221, B:103:0x0228, B:105:0x022e, B:107:0x0236, B:110:0x023f, B:111:0x0255, B:113:0x0270, B:89:0x019a, B:91:0x01a4, B:92:0x01b4, B:94:0x01bc, B:77:0x015a, B:79:0x015e, B:81:0x0164, B:82:0x016a, B:84:0x0172, B:85:0x017f, B:66:0x0130, B:62:0x0122, B:51:0x00e9, B:22:0x0084, B:17:0x004c, B:19:0x0062), top: B:124:0x004a, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x018e A[Catch: all -> 0x02b8, TryCatch #3 {all -> 0x02b8, blocks: (B:16:0x004a, B:52:0x00f0, B:54:0x010a, B:56:0x0110, B:58:0x0119, B:63:0x0126, B:67:0x0134, B:71:0x0144, B:73:0x014b, B:76:0x0154, B:86:0x0186, B:88:0x018e, B:96:0x01c7, B:99:0x01f2, B:100:0x0221, B:103:0x0228, B:105:0x022e, B:107:0x0236, B:110:0x023f, B:111:0x0255, B:113:0x0270, B:89:0x019a, B:91:0x01a4, B:92:0x01b4, B:94:0x01bc, B:77:0x015a, B:79:0x015e, B:81:0x0164, B:82:0x016a, B:84:0x0172, B:85:0x017f, B:66:0x0130, B:62:0x0122, B:51:0x00e9, B:22:0x0084, B:17:0x004c, B:19:0x0062), top: B:124:0x004a, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x019a A[Catch: all -> 0x02b8, TryCatch #3 {all -> 0x02b8, blocks: (B:16:0x004a, B:52:0x00f0, B:54:0x010a, B:56:0x0110, B:58:0x0119, B:63:0x0126, B:67:0x0134, B:71:0x0144, B:73:0x014b, B:76:0x0154, B:86:0x0186, B:88:0x018e, B:96:0x01c7, B:99:0x01f2, B:100:0x0221, B:103:0x0228, B:105:0x022e, B:107:0x0236, B:110:0x023f, B:111:0x0255, B:113:0x0270, B:89:0x019a, B:91:0x01a4, B:92:0x01b4, B:94:0x01bc, B:77:0x015a, B:79:0x015e, B:81:0x0164, B:82:0x016a, B:84:0x0172, B:85:0x017f, B:66:0x0130, B:62:0x0122, B:51:0x00e9, B:22:0x0084, B:17:0x004c, B:19:0x0062), top: B:124:0x004a, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01f2 A[Catch: all -> 0x02b8, TRY_ENTER, TryCatch #3 {all -> 0x02b8, blocks: (B:16:0x004a, B:52:0x00f0, B:54:0x010a, B:56:0x0110, B:58:0x0119, B:63:0x0126, B:67:0x0134, B:71:0x0144, B:73:0x014b, B:76:0x0154, B:86:0x0186, B:88:0x018e, B:96:0x01c7, B:99:0x01f2, B:100:0x0221, B:103:0x0228, B:105:0x022e, B:107:0x0236, B:110:0x023f, B:111:0x0255, B:113:0x0270, B:89:0x019a, B:91:0x01a4, B:92:0x01b4, B:94:0x01bc, B:77:0x015a, B:79:0x015e, B:81:0x0164, B:82:0x016a, B:84:0x0172, B:85:0x017f, B:66:0x0130, B:62:0x0122, B:51:0x00e9, B:22:0x0084, B:17:0x004c, B:19:0x0062), top: B:124:0x004a, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void invokeCreateInstance(com.taobao.weex.WXSDKInstance r19, com.taobao.weex.Script r20, java.util.Map<java.lang.String, java.lang.Object> r21, java.lang.String r22) {
        /*
            Method dump skipped, instructions count: 754
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.bridge.WXBridgeManager.invokeCreateInstance(com.taobao.weex.WXSDKInstance, com.taobao.weex.Script, java.util.Map, java.lang.String):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invokeDestroyInstance(String str) {
        String str2;
        try {
            WXEnvironment.isApkDebugable();
            WXJSObject[] wXJSObjectArr = {new WXJSObject(2, str)};
            if (!isSkipFrameworkInit(str)) {
                if (!isJSFrameworkInit()) {
                    return;
                }
            }
            str2 = str;
            try {
                invokeDestoryInstance(str2, null, METHOD_DESTROY_INSTANCE, wXJSObjectArr, true);
            } catch (Throwable th) {
                th = th;
                String str3 = "[WXBridgeManager] invokeDestroyInstance " + th.getCause();
                WXExceptionUtils.commitCriticalExceptionRT(str2, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "invokeDestroyInstance", str3, null);
                WXLogUtils.e(str3);
            }
        } catch (Throwable th2) {
            th = th2;
            str2 = str;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invokeExecJS(String str, String str2, String str3, WXJSObject[] wXJSObjectArr) {
        invokeExecJS(str, str2, str3, wXJSObjectArr, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String invokeExecJSOnInstance(String str, String str2, int i) {
        StringBuilder sb = this.mLodBuilder;
        sb.append("execJSOnInstance >>>> instanceId:");
        sb.append(str);
        WXLogUtils.d(this.mLodBuilder.substring(0));
        this.mLodBuilder.setLength(0);
        if (isSkipFrameworkInit(str) || isJSFrameworkInit()) {
            return this.mWXBridge.execJSOnInstance(str, str2, i);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invokeExecJSService(String str, List<String> list) {
        try {
            if (isJSFrameworkInit()) {
                this.mWXBridge.execJSService(str);
            } else {
                WXLogUtils.e("[WXBridgeManager] invoke execJSService: framework.js uninitialized.");
                list.add(str);
            }
        } catch (Throwable th) {
            WXLogUtils.e("[WXBridgeManager] invokeRegisterService:", th);
            HashMap map = new HashMap();
            map.put("inputParams", str + Operators.OR + list.toString());
            WXErrorCode wXErrorCode = WXErrorCode.WX_KEY_EXCEPTION_INVOKE_JSSERVICE_EXECUTE;
            WXExceptionUtils.commitCriticalExceptionRT("invokeExecJSService", wXErrorCode, "invokeExecJSService", wXErrorCode.getErrorMsg() + "[WXBridgeManager] invokeRegisterService:" + WXLogUtils.getStackTrace(th), map);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invokeExecJSWithCallback(String str, String str2, String str3, WXJSObject[] wXJSObjectArr, ResultCallback resultCallback, boolean z) {
        WXEnvironment.isOpenDebugLog();
        if (isSkipFrameworkInit(str) || isJSFrameworkInit()) {
            this.mWXBridge.execJSWithCallback(str, str2, str3, wXJSObjectArr, resultCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invokeRefreshInstance(String str, WXRefreshData wXRefreshData) {
        try {
            WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
            if (!isSkipFrameworkInit(str) && !isJSFrameworkInit()) {
                if (sDKInstance != null) {
                    WXErrorCode wXErrorCode = WXErrorCode.WX_DEGRAD_ERR_INSTANCE_CREATE_FAILED;
                    sDKInstance.onRenderError(wXErrorCode.getErrorCode(), wXErrorCode.getErrorMsg() + "invokeRefreshInstance FAILED for JSFrameworkInit FAILED, intance will invoke instance.onRenderError");
                }
                WXLogUtils.e("[WXBridgeManager] invokeRefreshInstance: framework.js uninitialized.");
                return;
            }
            System.currentTimeMillis();
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.d("refreshInstance >>>> instanceId:" + str + ", data:" + wXRefreshData.data + ", isDirty:" + wXRefreshData.isDirty);
            }
            if (wXRefreshData.isDirty) {
                return;
            }
            WXJSObject wXJSObject = new WXJSObject(2, str);
            String str2 = wXRefreshData.data;
            if (str2 == null) {
                str2 = "{}";
            }
            this.mWXBridge.refreshInstance(str, null, METHOD_REFRESH_INSTANCE, new WXJSObject[]{wXJSObject, new WXJSObject(3, str2)});
        } catch (Throwable th) {
            String str3 = "[WXBridgeManager] invokeRefreshInstance " + WXLogUtils.getStackTrace(th);
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "invokeRefreshInstance", str3, null);
            WXLogUtils.e(str3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invokeRegisterComponents(List<Map<String, Object>> list, List<Map<String, Object>> list2) {
        String str;
        if (list == list2) {
            throw new RuntimeException("Fail receiver should not use source.");
        }
        if (!isJSFrameworkInit()) {
            Iterator<Map<String, Object>> it = list.iterator();
            while (it.hasNext()) {
                list2.add(it.next());
            }
            return;
        }
        if (list == null) {
            return;
        }
        try {
            IWXBridge iWXBridge = this.mWXBridge;
            if (iWXBridge instanceof WXBridge) {
                ((WXBridge) iWXBridge).registerComponentOnDataRenderNode(WXJsonUtils.fromObjectToJSONString(list));
            }
        } catch (Throwable th) {
            WXLogUtils.e("Weex [data_render register err]", th);
        }
        WXJSObject[] wXJSObjectArr = {WXWsonJSONSwitch.toWsonOrJsonWXJSObject(list)};
        try {
            str = this.mWXBridge.execJS("", null, METHOD_REGISTER_COMPONENTS, wXJSObjectArr) == 0 ? "execJS error" : null;
        } catch (Throwable th2) {
            str = WXErrorCode.WX_KEY_EXCEPTION_INVOKE_REGISTER_COMPONENT + wXJSObjectArr.toString() + WXLogUtils.getStackTrace(th2);
        }
        if (TextUtils.isEmpty(str)) {
            return;
        }
        WXLogUtils.e("[WXBridgeManager] invokeRegisterComponents ", str);
        WXExceptionUtils.commitCriticalExceptionRT(null, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_REGISTER_COMPONENT, METHOD_REGISTER_COMPONENTS, str, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invokeRegisterModules(Map<String, Object> map, List<Map<String, Object>> list) {
        String str;
        if (map == null || !isJSFrameworkInit()) {
            if (!isJSFrameworkInit()) {
                WXLogUtils.d("[WXinvokeRegisterModulesBridgeManager] invokeRegisterModules: framework.js uninitialized.");
            }
            list.add(map);
            return;
        }
        WXJSObject[] wXJSObjectArr = {WXWsonJSONSwitch.toWsonOrJsonWXJSObject(map)};
        try {
            IWXBridge iWXBridge = this.mWXBridge;
            if (iWXBridge instanceof WXBridge) {
                ((WXBridge) iWXBridge).registerModuleOnDataRenderNode(WXJsonUtils.fromObjectToJSONString(map));
            }
        } catch (Throwable th) {
            WXLogUtils.e("Weex [data_render register err]", th);
        }
        try {
            str = this.mWXBridge.execJS("", null, METHOD_REGISTER_MODULES, wXJSObjectArr) == 0 ? "execJS error" : null;
            try {
                for (String str2 : map.keySet()) {
                    if (str2 != null) {
                        WXModuleManager.resetModuleState(str2, true);
                    }
                }
            } catch (Throwable th2) {
                WXLogUtils.e("Weex [invokeRegisterModules]", th2);
            }
        } catch (Throwable th3) {
            str = WXErrorCode.WX_KEY_EXCEPTION_INVOKE_REGISTER_MODULES.getErrorMsg() + " \n " + th3.getMessage() + map.entrySet().toString();
        }
        if (TextUtils.isEmpty(str)) {
            return;
        }
        WXLogUtils.e("[WXBridgeManager] invokeRegisterModules:", str);
        WXExceptionUtils.commitCriticalExceptionRT(null, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_REGISTER_MODULES, "invokeRegisterModules", str, null);
    }

    private boolean isSkipFrameworkInit(String str) {
        return isSkipFrameworkInit(WXSDKManager.getInstance().getSDKInstance(str));
    }

    private void mock(String str) {
    }

    private void onJsFrameWorkInitSuccees() {
        for (Map.Entry<String, String> entry : mWeexCoreEnvOptions.entrySet()) {
            this.mWXBridge.registerCoreEnv(entry.getKey(), entry.getValue());
        }
        mWeexCoreEnvOptions.clear();
    }

    private void registerDomModule() {
        HashMap map = new HashMap();
        map.put(WXDomModule.WXDOM, WXDomModule.METHODS);
        registerModules(map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeTaskByInstance(String str) {
        this.mNextTickTasks.removeFromMapAndStack(str);
    }

    private void sendMessage(String str, int i) {
        Message messageObtain = Message.obtain(this.mJSHandler);
        messageObtain.obj = str;
        messageObtain.what = i;
        messageObtain.sendToTarget();
    }

    private void setExceedGPULimitComponentsInfo(String str, String str2, GraphicSize graphicSize) {
        float openGLRenderLimitValue = WXRenderManager.getOpenGLRenderLimitValue();
        if (openGLRenderLimitValue > 0.0f) {
            if (graphicSize.getHeight() > openGLRenderLimitValue || graphicSize.getWidth() > openGLRenderLimitValue) {
                JSONObject jSONObject = new JSONObject();
                WXComponent wXComponent = WXSDKManager.getInstance().getWXRenderManager().getWXComponent(str, str2);
                jSONObject.put("GPU limit", (Object) String.valueOf(openGLRenderLimitValue));
                jSONObject.put("component.width", (Object) String.valueOf(graphicSize.getWidth()));
                jSONObject.put("component.height", (Object) String.valueOf(graphicSize.getHeight()));
                if (wXComponent.getComponentType() != null && !wXComponent.getComponentType().isEmpty()) {
                    jSONObject.put("component.type", (Object) wXComponent.getComponentType());
                }
                if (wXComponent.getStyles() != null && !wXComponent.getStyles().isEmpty()) {
                    jSONObject.put("component.style", (Object) wXComponent.getStyles().toString());
                }
                if (wXComponent.getAttrs() != null && !wXComponent.getAttrs().isEmpty()) {
                    jSONObject.put("component.attr", (Object) wXComponent.getAttrs().toString());
                }
                if (wXComponent.getEvents() != null && !wXComponent.getEvents().isEmpty()) {
                    jSONObject.put("component.event", (Object) wXComponent.getEvents().toString());
                }
                if (wXComponent.getMargin() != null) {
                    jSONObject.put("component.margin", (Object) wXComponent.getMargin().toString());
                }
                if (wXComponent.getPadding() != null) {
                    jSONObject.put("component.padding", (Object) wXComponent.getPadding().toString());
                }
                if (wXComponent.getBorder() != null) {
                    jSONObject.put("component.border", (Object) wXComponent.getBorder().toString());
                }
                WXSDKManager.getInstance().getSDKInstance(str).setComponentsInfoExceedGPULimit(jSONObject);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setJSFrameworkInit(boolean z) {
        mInit = z;
        if (z) {
            onJsFrameWorkInitSuccees();
        }
    }

    private void trackComponentAndModulesTime() {
        post(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.22
            @Override // java.lang.Runnable
            public void run() {
                WXEnvironment.sComponentsAndModulesReadyTime = System.currentTimeMillis() - WXEnvironment.sSDKInitStart;
            }
        });
    }

    public static void updateGlobalConfig(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "none";
        }
        if (TextUtils.equals(str, globalConfig)) {
            return;
        }
        globalConfig = str;
        WXEnvironment.addCustomOptions(GLOBAL_CONFIG_KEY, str);
        Runnable runnable = new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.26
            @Override // java.lang.Runnable
            public void run() {
                if (WXBridgeManager.mBridgeManager != null && WXBridgeManager.mBridgeManager.isJSFrameworkInit() && (WXBridgeManager.mBridgeManager.mWXBridge instanceof WXBridge)) {
                    ((WXBridge) WXBridgeManager.mBridgeManager.mWXBridge).nativeUpdateGlobalConfig(WXBridgeManager.globalConfig);
                }
                if (WXBridgeManager.globalConfig.contains(WXWsonJSONSwitch.WSON_OFF)) {
                    WXWsonJSONSwitch.USE_WSON = false;
                } else {
                    WXWsonJSONSwitch.USE_WSON = true;
                }
            }
        };
        if (mBridgeManager == null || !mBridgeManager.isJSFrameworkInit()) {
            runnable.run();
        } else {
            mBridgeManager.post(runnable);
        }
    }

    public void asyncCallJSEventVoidResult(final String str, final String str2, final List<Object> list, final Object... objArr) {
        post(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.11
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Object[] objArr2 = objArr;
                    if (objArr2 != null && objArr2.length != 0) {
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : objArr) {
                            arrayList.add(obj);
                        }
                        if (list != null) {
                            ArrayMap arrayMap = new ArrayMap(4);
                            arrayMap.put("params", list);
                            arrayList.add(arrayMap);
                        }
                        WXHashMap wXHashMap = new WXHashMap();
                        wXHashMap.put("method", str);
                        wXHashMap.put("args", arrayList);
                        WXJSObject[] wXJSObjectArr = {new WXJSObject(2, str2), WXWsonJSONSwitch.toWsonOrJsonWXJSObject(new Object[]{wXHashMap})};
                        WXBridgeManager.this.invokeExecJS(String.valueOf(str2), null, WXBridgeManager.METHOD_CALL_JS, wXJSObjectArr, true);
                        wXJSObjectArr[0] = null;
                    }
                } catch (Exception e) {
                    WXLogUtils.e("asyncCallJSEventVoidResult", e);
                }
            }
        });
    }

    public void bindMeasurementToRenderObject(long j) {
        if (isJSFrameworkInit()) {
            this.mWXBridge.bindMeasurementToRenderObject(j);
        }
    }

    public int callAddChildToRichtext(String str, String str2, String str3, String str4, String str5, HashMap<String, String> map, HashMap<String, String> map2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str3)) {
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.d("[WXBridgeManager] call callAddChildToRichtext arguments is null");
            }
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callAddChildToRichtext", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return 0;
        }
        WXEnvironment.isApkDebugable();
        HashSet<String> hashSet = this.mDestroyedInstanceId;
        if (hashSet != null && hashSet.contains(str)) {
            return -1;
        }
        try {
            WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
            if (sDKInstance == null) {
                return 1;
            }
            GraphicActionAddChildToRichtext graphicActionAddChildToRichtext = new GraphicActionAddChildToRichtext(sDKInstance, str2, str3, str4, str5, map, map2);
            WXSDKManager.getInstance().getWXRenderManager().postGraphicAction(graphicActionAddChildToRichtext.getPageId(), graphicActionAddChildToRichtext);
            return 1;
        } catch (Exception e) {
            WXLogUtils.e("[WXBridgeManager] callAddChildToRichtext exception: ", WXLogUtils.getStackTrace(e));
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callAddChildToRichtext", WXLogUtils.getStackTrace(e), null);
            return 1;
        }
    }

    public int callAddElement(String str, String str2, String str3, int i, String str4, HashMap<String, String> map, HashMap<String, String> map2, HashSet<String> hashSet, float[] fArr, float[] fArr2, float[] fArr3, boolean z) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.d("[WXBridgeManager] call callAddElement arguments is null");
            }
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callAddElement", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return 0;
        }
        WXEnvironment.isApkDebugable();
        HashSet<String> hashSet2 = this.mDestroyedInstanceId;
        if (hashSet2 != null && hashSet2.contains(str)) {
            return -1;
        }
        try {
            WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
            if (sDKInstance == null) {
                return 1;
            }
            GraphicActionAddElement graphicActionAddElement = new GraphicActionAddElement(sDKInstance, str3, str2, str4, i, map, map2, hashSet, fArr, fArr2, fArr3);
            if (z) {
                sDKInstance.addInActiveAddElementAction(str3, graphicActionAddElement);
                return 1;
            }
            WXSDKManager.getInstance().getWXRenderManager().postGraphicAction(str, graphicActionAddElement);
            return 1;
        } catch (Exception e) {
            WXLogUtils.e("[WXBridgeManager] callAddElement exception: ", e);
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callAddElement", WXLogUtils.getStackTrace(e), null);
            return 1;
        }
    }

    public int callAddEvent(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.d("[WXBridgeManager] call callAddEvent arguments is null");
            }
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callAddEvent", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return 0;
        }
        WXEnvironment.isApkDebugable();
        HashSet<String> hashSet = this.mDestroyedInstanceId;
        if (hashSet != null && hashSet.contains(str)) {
            return -1;
        }
        try {
            WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
            if (sDKInstance != null) {
                new GraphicActionAddEvent(sDKInstance, str2, str3).executeActionOnRender();
            }
        } catch (Exception e) {
            WXLogUtils.e("[WXBridgeManager] callAddEvent exception: ", e);
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callAddEvent", WXLogUtils.getStackTrace(e), null);
        }
        getNextTick(str);
        return 1;
    }

    public int callAppendTreeCreateFinish(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            WXLogUtils.d("[WXBridgeManager] call callAppendTreeCreateFinish arguments is null");
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callAppendTreeCreateFinish", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return 0;
        }
        WXEnvironment.isApkDebugable();
        HashSet<String> hashSet = this.mDestroyedInstanceId;
        if (hashSet != null && hashSet.contains(str)) {
            return -1;
        }
        try {
            WXSDKManager.getInstance().getWXRenderManager().postGraphicAction(str, new GraphicActionAppendTreeCreateFinish(WXSDKManager.getInstance().getSDKInstance(str), str2));
            return 1;
        } catch (Exception e) {
            WXLogUtils.e("[WXBridgeManager] callAppendTreeCreateFinish exception: ", e);
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callAppendTreeCreateFinish", WXLogUtils.getStackTrace(e), null);
            return 1;
        }
    }

    public void callBacthEnd(String str) {
        if (TextUtils.isEmpty(str)) {
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.d("[WXBridgeManager] call callRemoveChildFromRichtext arguments is null");
            }
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callRemoveChildFromRichtext", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return;
        }
        try {
            WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
            if (sDKInstance != null) {
                GraphicActionBatchEnd graphicActionBatchEnd = new GraphicActionBatchEnd(sDKInstance, "");
                WXSDKManager.getInstance().getWXRenderManager().postGraphicAction(graphicActionBatchEnd.getPageId(), graphicActionBatchEnd);
            }
        } catch (Exception e) {
            WXLogUtils.e("[WXBridgeManager] callRemoveChildFromRichtext exception: ", e);
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callRemoveChildFromRichtext", WXLogUtils.getStackTrace(e), null);
        }
    }

    public void callBacthStart(String str) {
        if (TextUtils.isEmpty(str)) {
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.d("[WXBridgeManager] call callRemoveChildFromRichtext arguments is null");
            }
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callRemoveChildFromRichtext", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return;
        }
        try {
            WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
            if (sDKInstance != null) {
                GraphicActionBatchBegin graphicActionBatchBegin = new GraphicActionBatchBegin(sDKInstance, "");
                WXSDKManager.getInstance().getWXRenderManager().postGraphicAction(graphicActionBatchBegin.getPageId(), graphicActionBatchBegin);
            }
        } catch (Exception e) {
            WXLogUtils.e("[WXBridgeManager] callRemoveChildFromRichtext exception: ", e);
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callRemoveChildFromRichtext", WXLogUtils.getStackTrace(e), null);
        }
    }

    public int callCreateBody(String str, String str2, String str3, HashMap<String, String> map, HashMap<String, String> map2, HashSet<String> hashSet, float[] fArr, float[] fArr2, float[] fArr3) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            WXLogUtils.d("[WXBridgeManager] call callCreateBody arguments is null");
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callCreateBody", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return 0;
        }
        WXEnvironment.isApkDebugable();
        HashSet<String> hashSet2 = this.mDestroyedInstanceId;
        if (hashSet2 != null && hashSet2.contains(str)) {
            return -1;
        }
        try {
            WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
            if (sDKInstance == null) {
                return 1;
            }
            GraphicActionCreateBody graphicActionCreateBody = new GraphicActionCreateBody(sDKInstance, str3, str2, map, map2, hashSet, fArr, fArr2, fArr3);
            WXSDKManager.getInstance().getWXRenderManager().postGraphicAction(graphicActionCreateBody.getPageId(), graphicActionCreateBody);
            return 1;
        } catch (Exception e) {
            WXLogUtils.e("[WXBridgeManager] callCreateBody exception: ", e);
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callCreateBody", WXLogUtils.getStackTrace(e), null);
            return 1;
        }
    }

    public int callCreateFinish(String str) {
        if (TextUtils.isEmpty(str)) {
            WXLogUtils.d("[WXBridgeManager] call callCreateFinish arguments is null");
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callCreateFinish", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return 0;
        }
        WXEnvironment.isApkDebugable();
        HashSet<String> hashSet = this.mDestroyedInstanceId;
        if (hashSet != null && hashSet.contains(str)) {
            return -1;
        }
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
            if (sDKInstance == null) {
                return 1;
            }
            sDKInstance.firstScreenCreateInstanceTime(jCurrentTimeMillis);
            WXSDKManager.getInstance().getWXRenderManager().postGraphicAction(str, new GraphicActionCreateFinish(sDKInstance));
            return 1;
        } catch (Exception e) {
            WXLogUtils.e("[WXBridgeManager] callCreateFinish exception: ", e);
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callCreateFinish", WXLogUtils.getStackTrace(e), null);
            return 1;
        }
    }

    public int callHasTransitionPros(String str, String str2, HashMap<String, String> map) {
        WXComponent wXComponent = WXSDKManager.getInstance().getWXRenderManager().getWXComponent(str, str2);
        if (wXComponent == null || wXComponent.getTransition() == null || wXComponent.getTransition().getProperties() == null) {
            return -1;
        }
        Iterator<String> it = wXComponent.getTransition().getProperties().iterator();
        while (it.hasNext()) {
            if (map.containsKey(it.next())) {
                return 1;
            }
        }
        return 0;
    }

    public int callLayout(String str, String str2, int i, int i2, int i3, int i4, int i5, int i6, boolean z, int i7) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.d("[WXBridgeManager] call callLayout arguments is null");
            }
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callLayout", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return 0;
        }
        WXEnvironment.isApkDebugable();
        HashSet<String> hashSet = this.mDestroyedInstanceId;
        if (hashSet != null && hashSet.contains(str)) {
            return -1;
        }
        try {
            WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
            if (sDKInstance == null) {
                return 1;
            }
            GraphicSize graphicSize = new GraphicSize(i6, i5);
            GraphicPosition graphicPosition = new GraphicPosition(i3, i, i4, i2);
            setExceedGPULimitComponentsInfo(str, str2, graphicSize);
            GraphicActionAddElement inActiveAddElementAction = sDKInstance.getInActiveAddElementAction(str2);
            if (inActiveAddElementAction == null) {
                GraphicActionLayout graphicActionLayout = new GraphicActionLayout(sDKInstance, str2, graphicPosition, graphicSize, z);
                WXSDKManager.getInstance().getWXRenderManager().postGraphicAction(graphicActionLayout.getPageId(), graphicActionLayout);
                return 1;
            }
            inActiveAddElementAction.setRTL(z);
            inActiveAddElementAction.setSize(graphicSize);
            inActiveAddElementAction.setPosition(graphicPosition);
            if (!TextUtils.equals(str2, WXComponent.ROOT)) {
                inActiveAddElementAction.setIndex(i7);
            }
            WXSDKManager.getInstance().getWXRenderManager().postGraphicAction(str, inActiveAddElementAction);
            sDKInstance.removeInActiveAddElmentAction(str2);
            return 1;
        } catch (Exception e) {
            WXLogUtils.e("[WXBridgeManager] callLayout exception: ", e);
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callLayout", WXLogUtils.getStackTrace(e), null);
            return 1;
        }
    }

    public Object callModuleMethod(String str, String str2, String str3, JSONArray jSONArray) {
        return callModuleMethod(str, str2, str3, jSONArray, null);
    }

    public int callMoveElement(String str, String str2, String str3, int i) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.d("[WXBridgeManager] call callMoveElement arguments is null");
            }
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callMoveElement", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return 0;
        }
        WXEnvironment.isApkDebugable();
        HashSet<String> hashSet = this.mDestroyedInstanceId;
        if (hashSet != null && hashSet.contains(str)) {
            return -1;
        }
        try {
            WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
            if (sDKInstance == null) {
                return 1;
            }
            GraphicActionMoveElement graphicActionMoveElement = new GraphicActionMoveElement(sDKInstance, str2, str3, i);
            WXSDKManager.getInstance().getWXRenderManager().postGraphicAction(graphicActionMoveElement.getPageId(), graphicActionMoveElement);
            return 1;
        } catch (Exception e) {
            WXLogUtils.e("[WXBridgeManager] callMoveElement exception: ", e);
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callMoveElement", WXLogUtils.getStackTrace(e), null);
            return 1;
        }
    }

    public int callNative(String str, JSONArray jSONArray, String str2) {
        int i;
        if (TextUtils.isEmpty(str) || jSONArray == null) {
            WXLogUtils.d("[WXBridgeManager] call callNative arguments is null");
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callNative", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return 0;
        }
        WXEnvironment.isApkDebugable();
        HashSet<String> hashSet = this.mDestroyedInstanceId;
        if (hashSet != null && hashSet.contains(str)) {
            return -1;
        }
        long jNanoTime = System.nanoTime() - System.nanoTime();
        if (jSONArray.size() > 0) {
            int size = jSONArray.size();
            for (int i2 = 0; i2 < size; i2++) {
                try {
                    JSONObject jSONObject = (JSONObject) jSONArray.get(i2);
                    if (jSONObject != null && WXSDKManager.getInstance().getSDKInstance(str) != null) {
                        Object obj = jSONObject.get("module");
                        if (obj != null) {
                            i = 0;
                            try {
                                if (WXDomModule.WXDOM.equals(obj)) {
                                    WXModuleManager.getDomModule(str).callDomMethod(jSONObject, jNanoTime);
                                } else {
                                    callModuleMethod(str, (String) obj, (String) jSONObject.get("method"), (JSONArray) jSONObject.get("args"), jSONObject.getJSONObject("options"));
                                }
                            } catch (Exception e) {
                                e = e;
                                WXLogUtils.e("[WXBridgeManager] callNative exception: ", e);
                                WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callNative", WXLogUtils.getStackTrace(e), null);
                                if ("undefined".equals(str2)) {
                                }
                                return i;
                            }
                        } else {
                            i = 0;
                            if (jSONObject.get(COMPONENT) == null) {
                                throw new IllegalArgumentException("unknown callNative");
                            }
                            WXModuleManager.getDomModule(str).invokeMethod((String) jSONObject.get("ref"), (String) jSONObject.get("method"), (JSONArray) jSONObject.get("args"));
                        }
                    }
                } catch (Exception e2) {
                    e = e2;
                    i = 0;
                }
            }
            i = 0;
        } else {
            i = 0;
        }
        if (!"undefined".equals(str2) || NON_CALLBACK.equals(str2)) {
            return i;
        }
        getNextTick(str, str2);
        return 1;
    }

    public Object callNativeComponent(String str, String str2, String str3, JSONArray jSONArray, Object obj) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            WXLogUtils.d("[WXBridgeManager] call callNativeComponent arguments is null");
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callNativeComponent", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return 0;
        }
        WXEnvironment.isApkDebugable();
        try {
            WXDomModule domModule = WXModuleManager.getDomModule(str);
            if (domModule != null) {
                domModule.invokeMethod(str2, str3, jSONArray);
            } else {
                WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
                if (sDKInstance == null || !sDKInstance.isDestroy()) {
                    WXLogUtils.e("WXBridgeManager", "callNativeComponent exception :null == dom ,method:" + str3);
                }
            }
        } catch (Exception e) {
            WXLogUtils.e("[WXBridgeManager] callNativeComponent exception: ", e);
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callNativeComponent", WXLogUtils.getStackTrace(e), null);
        }
        return null;
    }

    public Object callNativeModule(String str, String str2, String str3, JSONArray jSONArray, Object obj) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            WXLogUtils.d("[WXBridgeManager] call callNativeModule arguments is null");
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callNativeModule", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return 0;
        }
        WXEnvironment.isApkDebugable();
        try {
            return WXDomModule.WXDOM.equals(str2) ? WXModuleManager.getDomModule(str).callDomMethod(str3, jSONArray, new long[0]) : callModuleMethod(str, str2, str3, jSONArray);
        } catch (Exception e) {
            String str4 = "[WXBridgeManager] callNative exception: " + WXLogUtils.getStackTrace(e);
            WXLogUtils.e(str4);
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callNativeModule", str4, null);
            return null;
        }
    }

    public int callRefreshFinish(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            WXLogUtils.d("[WXBridgeManager] call callRefreshFinish arguments is null");
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callRefreshFinish", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return 0;
        }
        WXEnvironment.isApkDebugable();
        HashSet<String> hashSet = this.mDestroyedInstanceId;
        if (hashSet != null && hashSet.contains(str)) {
            return -1;
        }
        try {
            WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
            if (sDKInstance != null) {
                WXSDKManager.getInstance().getWXRenderManager().postGraphicAction(str, new GraphicActionRefreshFinish(sDKInstance));
            }
        } catch (Exception e) {
            WXLogUtils.e("[WXBridgeManager] callRefreshFinish exception: ", e);
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callRefreshFinish", WXLogUtils.getStackTrace(e), null);
        }
        if ("undefined".equals(str2) || NON_CALLBACK.equals(str2)) {
            return 0;
        }
        getNextTick(str, str2);
        return 1;
    }

    public int callRemoveChildFromRichtext(String str, String str2, String str3, String str4) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.d("[WXBridgeManager] call callRemoveChildFromRichtext arguments is null");
            }
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callRemoveChildFromRichtext", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return 0;
        }
        WXEnvironment.isApkDebugable();
        HashSet<String> hashSet = this.mDestroyedInstanceId;
        if (hashSet != null && hashSet.contains(str)) {
            return -1;
        }
        try {
            WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
            if (sDKInstance == null) {
                return 1;
            }
            GraphicActionRemoveChildFromRichtext graphicActionRemoveChildFromRichtext = new GraphicActionRemoveChildFromRichtext(sDKInstance, str2, str3, str4);
            WXSDKManager.getInstance().getWXRenderManager().postGraphicAction(graphicActionRemoveChildFromRichtext.getPageId(), graphicActionRemoveChildFromRichtext);
            return 1;
        } catch (Exception e) {
            WXLogUtils.e("[WXBridgeManager] callRemoveChildFromRichtext exception: ", e);
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callRemoveChildFromRichtext", WXLogUtils.getStackTrace(e), null);
            return 1;
        }
    }

    public int callRemoveElement(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.d("[WXBridgeManager] call callRemoveElement arguments is null");
            }
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callRemoveElement", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return 0;
        }
        WXEnvironment.isApkDebugable();
        HashSet<String> hashSet = this.mDestroyedInstanceId;
        if (hashSet != null && hashSet.contains(str)) {
            return -1;
        }
        try {
            WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
            if (sDKInstance == null) {
                return 1;
            }
            GraphicActionRemoveElement graphicActionRemoveElement = new GraphicActionRemoveElement(sDKInstance, str2);
            if (sDKInstance.getInActiveAddElementAction(str2) != null) {
                sDKInstance.removeInActiveAddElmentAction(str2);
                return 1;
            }
            WXSDKManager.getInstance().getWXRenderManager().postGraphicAction(graphicActionRemoveElement.getPageId(), graphicActionRemoveElement);
            return 1;
        } catch (Exception e) {
            WXLogUtils.e("[WXBridgeManager] callRemoveElement exception: ", e);
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callRemoveElement", WXLogUtils.getStackTrace(e), null);
            return 1;
        }
    }

    public int callRemoveEvent(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.d("[WXBridgeManager] call callRemoveEvent arguments is null");
            }
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callRemoveEvent", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return 0;
        }
        WXEnvironment.isApkDebugable();
        HashSet<String> hashSet = this.mDestroyedInstanceId;
        if (hashSet != null && hashSet.contains(str)) {
            return -1;
        }
        try {
            WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
            if (sDKInstance != null) {
                new GraphicActionRemoveEvent(sDKInstance, str2, str3).executeActionOnRender();
            }
        } catch (Exception e) {
            WXLogUtils.e("[WXBridgeManager] callRemoveEvent exception: ", e);
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callRemoveEvent", WXLogUtils.getStackTrace(e), null);
        }
        getNextTick(str);
        return 1;
    }

    public int callRenderSuccess(String str) {
        if (TextUtils.isEmpty(str)) {
            WXLogUtils.d("[WXBridgeManager] call callRenderSuccess arguments is null");
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callRenderSuccess", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return 0;
        }
        WXEnvironment.isApkDebugable();
        HashSet<String> hashSet = this.mDestroyedInstanceId;
        if (hashSet != null && hashSet.contains(str)) {
            return -1;
        }
        try {
            WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
            if (sDKInstance == null) {
                return 1;
            }
            WXSDKManager.getInstance().getWXRenderManager().postGraphicAction(str, new GraphicActionRenderSuccess(sDKInstance));
            return 1;
        } catch (Exception e) {
            WXLogUtils.e("[WXBridgeManager] callRenderSuccess exception: ", e);
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callRenderSuccess", WXLogUtils.getStackTrace(e), null);
            return 1;
        }
    }

    public void callReportCrash(String str, final String str2, final String str3, final Map<String, String> map) {
        final String str4 = str + Operators.DOT_STR + new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(new Date());
        File file = new File(str);
        File file2 = new File(str4);
        if (file.exists()) {
            file.renameTo(file2);
        }
        new Thread(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.7
            @Override // java.lang.Runnable
            public void run() {
                try {
                    File file3 = new File(str4);
                    if (file3.exists()) {
                        if (file3.length() > 0) {
                            StringBuilder sb = new StringBuilder();
                            try {
                                BufferedReader bufferedReader = new BufferedReader(new FileReader(str4));
                                while (true) {
                                    String line = bufferedReader.readLine();
                                    if (line == null) {
                                        break;
                                    }
                                    if (!"".equals(line)) {
                                        sb.append(line + "\n");
                                    }
                                }
                                WXBridgeManager.this.commitJscCrashAlarmMonitor(IWXUserTrackAdapter.JS_BRIDGE, WXErrorCode.WX_ERR_JSC_CRASH, sb.toString(), str2, str3, map);
                                bufferedReader.close();
                            } catch (Exception e) {
                                WXLogUtils.e(WXLogUtils.getStackTrace(e));
                            }
                        } else {
                            WXLogUtils.e("[WXBridgeManager] callReportCrash crash file is empty");
                        }
                        if (WXEnvironment.isApkDebugable()) {
                            return;
                        }
                        file3.delete();
                    }
                } catch (Throwable th) {
                    WXLogUtils.e("[WXBridgeManager] callReportCrash exception: ", th);
                }
            }
        }).start();
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00c0 A[Catch: Exception -> 0x00f2, TryCatch #4 {Exception -> 0x00f2, blocks: (B:3:0x000c, B:5:0x0017, B:7:0x0021, B:25:0x008d, B:29:0x0099, B:35:0x00ba, B:37:0x00c0, B:39:0x00d6, B:41:0x00ea, B:24:0x0086, B:30:0x00a0, B:34:0x00ac), top: B:62:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00d6 A[Catch: Exception -> 0x00f2, TryCatch #4 {Exception -> 0x00f2, blocks: (B:3:0x000c, B:5:0x0017, B:7:0x0021, B:25:0x008d, B:29:0x0099, B:35:0x00ba, B:37:0x00c0, B:39:0x00d6, B:41:0x00ea, B:24:0x0086, B:30:0x00a0, B:34:0x00ac), top: B:62:0x000c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int callReportCrashReloadPage(java.lang.String r15, java.lang.String r16) {
        /*
            Method dump skipped, instructions count: 299
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.bridge.WXBridgeManager.callReportCrashReloadPage(java.lang.String, java.lang.String):int");
    }

    public int callUpdateAttrs(String str, String str2, HashMap<String, String> map) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.d("[WXBridgeManager] call callUpdateAttrs arguments is null");
            }
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callUpdateAttrs", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return 0;
        }
        WXEnvironment.isApkDebugable();
        HashSet<String> hashSet = this.mDestroyedInstanceId;
        if (hashSet != null && hashSet.contains(str)) {
            return -1;
        }
        try {
            WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
            if (sDKInstance == null) {
                return 1;
            }
            GraphicActionUpdateAttr graphicActionUpdateAttr = new GraphicActionUpdateAttr(sDKInstance, str2, map);
            WXSDKManager.getInstance().getWXRenderManager().postGraphicAction(graphicActionUpdateAttr.getPageId(), graphicActionUpdateAttr);
            return 1;
        } catch (Exception e) {
            WXLogUtils.e("[WXBridgeManager] callUpdateAttrs exception: ", e);
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callUpdateAttrs", WXLogUtils.getStackTrace(e), null);
            return 1;
        }
    }

    public int callUpdateFinish(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            WXLogUtils.d("[WXBridgeManager] call callUpdateFinish arguments is null");
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callUpdateFinish", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return 0;
        }
        WXEnvironment.isApkDebugable();
        HashSet<String> hashSet = this.mDestroyedInstanceId;
        if (hashSet != null && hashSet.contains(str)) {
            return -1;
        }
        try {
            WXSDKManager.getInstance().getSDKInstance(str);
        } catch (Exception e) {
            WXLogUtils.e("[WXBridgeManager] callUpdateFinish exception: ", e);
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callUpdateFinish", WXLogUtils.getStackTrace(e), null);
        }
        if (str2 == null || str2.isEmpty() || "undefined".equals(str2) || NON_CALLBACK.equals(str2)) {
            return 0;
        }
        getNextTick(str, str2);
        return 1;
    }

    public int callUpdateRichtextChildAttr(String str, String str2, HashMap<String, String> map, String str3, String str4) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.d("[WXBridgeManager] call callUpdateRichtextChildAttr arguments is null");
            }
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callUpdateRichtextChildAttr", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return 0;
        }
        WXEnvironment.isApkDebugable();
        HashSet<String> hashSet = this.mDestroyedInstanceId;
        if (hashSet != null && hashSet.contains(str)) {
            return -1;
        }
        try {
            WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
            if (sDKInstance == null) {
                return 1;
            }
            GraphicActionUpdateRichtextAttr graphicActionUpdateRichtextAttr = new GraphicActionUpdateRichtextAttr(sDKInstance, str2, map, str3, str4);
            WXSDKManager.getInstance().getWXRenderManager().postGraphicAction(graphicActionUpdateRichtextAttr.getPageId(), graphicActionUpdateRichtextAttr);
            return 1;
        } catch (Exception e) {
            WXLogUtils.e("[WXBridgeManager] callUpdateRichtextChildAttr exception: ", e);
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callUpdateRichtextChildAttr", WXLogUtils.getStackTrace(e), null);
            return 1;
        }
    }

    public int callUpdateRichtextStyle(String str, String str2, HashMap<String, String> map, String str3, String str4) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.d("[WXBridgeManager] call callUpdateRichtextStyle arguments is null");
            }
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callUpdateRichtextStyle", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return 0;
        }
        WXEnvironment.isApkDebugable();
        HashSet<String> hashSet = this.mDestroyedInstanceId;
        if (hashSet != null && hashSet.contains(str)) {
            return -1;
        }
        try {
            WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
            if (sDKInstance == null) {
                return 1;
            }
            GraphicActionUpdateRichtextStyle graphicActionUpdateRichtextStyle = new GraphicActionUpdateRichtextStyle(sDKInstance, str2, map, str3, str4);
            WXSDKManager.getInstance().getWXRenderManager().postGraphicAction(graphicActionUpdateRichtextStyle.getPageId(), graphicActionUpdateRichtextStyle);
            return 1;
        } catch (Exception e) {
            WXLogUtils.e("[WXBridgeManager] callUpdateRichtextStyle exception: ", e);
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callUpdateRichtextStyle", WXLogUtils.getStackTrace(e), null);
            return 1;
        }
    }

    public int callUpdateStyle(String str, String str2, HashMap<String, Object> map, HashMap<String, String> map2, HashMap<String, String> map3, HashMap<String, String> map4) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.d("[WXBridgeManager] call callUpdateStyle arguments is null");
            }
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callUpdateStyle", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
            return 0;
        }
        WXEnvironment.isApkDebugable();
        HashSet<String> hashSet = this.mDestroyedInstanceId;
        if (hashSet != null && hashSet.contains(str)) {
            return -1;
        }
        try {
            WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
            if (sDKInstance == null) {
                return 1;
            }
            GraphicActionUpdateStyle graphicActionUpdateStyle = new GraphicActionUpdateStyle(sDKInstance, str2, map, map2, map3, map4);
            WXSDKManager.getInstance().getWXRenderManager().postGraphicAction(graphicActionUpdateStyle.getPageId(), graphicActionUpdateStyle);
            return 1;
        } catch (Exception e) {
            WXLogUtils.e("[WXBridgeManager] callUpdateStyle exception: ", e);
            WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callUpdateStyle", WXLogUtils.getStackTrace(e), null);
            return 1;
        }
    }

    @Deprecated
    public void callback(String str, String str2, String str3) {
        callback(str, str2, str3, false);
    }

    void callbackJavascript(String str, String str2, Object obj, boolean z) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || this.mJSHandler == null || !RuningAcitvityUtil.isRuningActivity) {
            return;
        }
        WXSDKInstance wXSDKInstance = WXSDKManager.getInstance().getAllInstanceMap().get(str);
        if (wXSDKInstance != null && wXSDKInstance.getRenderStrategy() == WXRenderStrategy.DATA_RENDER_BINARY) {
            callbackJavascriptOnDataRender(str, str2, obj, z);
        } else {
            addJSTask(METHOD_CALLBACK, str, str2, obj, Boolean.valueOf(z));
            sendMessage(str, 6);
        }
    }

    void callbackJavascriptOnDataRender(final String str, final String str2, final Object obj, final boolean z) {
        this.mJSHandler.postDelayed(WXThread.secure(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.15
            @Override // java.lang.Runnable
            public void run() {
                try {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    String jSONString = JSON.toJSONString(obj);
                    if (WXEnvironment.isApkDebugable()) {
                        WXLogUtils.d("callbackJavascriptOnDataRender >>>> instanceId:" + str + ", data:" + jSONString);
                    }
                    if (WXBridgeManager.this.mWXBridge instanceof WXBridge) {
                        ((WXBridge) WXBridgeManager.this.mWXBridge).invokeCallbackOnDataRender(str, str2, jSONString, z);
                    }
                    WXLogUtils.renderPerformanceLog("callbackJavascriptOnDataRender", System.currentTimeMillis() - jCurrentTimeMillis);
                } catch (Throwable th) {
                    String str3 = "[WXBridgeManager] callbackJavascriptOnDataRender " + WXLogUtils.getStackTrace(th);
                    WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_KEY_EXCEPTION_INVOKE_BRIDGE, "callbackJavascriptOnDataRender", str3, null);
                    WXLogUtils.e(str3);
                }
            }
        }), 0L);
    }

    public void checkJsEngineMultiThread() {
        IWXJscProcessManager wXJscProcessManager = WXSDKManager.getInstance().getWXJscProcessManager();
        boolean zEnableBackupThread = wXJscProcessManager != null ? wXJscProcessManager.enableBackupThread() : false;
        if (zEnableBackupThread == isJsEngineMultiThreadEnable) {
            return;
        }
        isJsEngineMultiThreadEnable = zEnableBackupThread;
        if (isJSFrameworkInit()) {
            if (isJSThread()) {
                WXSDKEngine.reload();
            } else {
                post(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.2
                    @Override // java.lang.Runnable
                    public void run() {
                        WXSDKEngine.reload();
                    }
                });
            }
        }
    }

    public void commitJscCrashAlarmMonitor(String str, WXErrorCode wXErrorCode, String str2, String str3, String str4, Map<String, String> map) {
        if (TextUtils.isEmpty(str) || wXErrorCode == null) {
            return;
        }
        Log.d("ReportCrash", " commitJscCrashAlarmMonitor errMsg " + str2);
        HashMap map2 = new HashMap();
        map2.put("jscCrashStack", str2);
        if (map != null) {
            map2.putAll(map);
        }
        IWXJSExceptionAdapter iWXJSExceptionAdapter = WXSDKManager.getInstance().getIWXJSExceptionAdapter();
        if (iWXJSExceptionAdapter != null) {
            WXJSExceptionInfo wXJSExceptionInfo = new WXJSExceptionInfo(str3, str4, wXErrorCode, "callReportCrash", "weex core process crash and restart exception", map2);
            iWXJSExceptionAdapter.onJSException(wXJSExceptionInfo);
            WXLogUtils.e(wXJSExceptionInfo.toString());
        }
    }

    public void createInstance(String str, String str2, Map<String, Object> map, String str3) {
        createInstance(str, new Script(str2), map, str3);
    }

    public String decrypt(String str, String str2, String str3, String str4) {
        IWXBridge iWXBridge = this.mWXBridge;
        return iWXBridge instanceof WXBridge ? ((WXBridge) iWXBridge).decrypt(str, str2, str3, str4) : "";
    }

    public void destroy() {
        WXThread wXThread = this.mJSThread;
        if (wXThread != null) {
            wXThread.quit();
        }
        mBridgeManager = null;
        HashSet<String> hashSet = this.mDestroyedInstanceId;
        if (hashSet != null) {
            hashSet.clear();
        }
    }

    public void destroyInstance(final String str) {
        if (this.mJSHandler == null || TextUtils.isEmpty(str)) {
            return;
        }
        HashSet<String> hashSet = this.mDestroyedInstanceId;
        if (hashSet != null) {
            hashSet.add(str);
        }
        this.mJSHandler.removeCallbacksAndMessages(str);
        post(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.19
            @Override // java.lang.Runnable
            public void run() {
                WXBridgeManager.this.removeTaskByInstance(str);
                WXBridgeManager.this.invokeDestroyInstance(str);
            }
        }, str, null, METHOD_DESTROY_INSTANCE);
    }

    public String dumpIpcPageInfo() {
        IWXBridge iWXBridge = this.mWXBridge;
        return iWXBridge instanceof WXBridge ? ((WXBridge) iWXBridge).nativeDumpIpcPageQueueInfo() : "";
    }

    public String encrypt(String str, String str2, String str3, String str4) {
        IWXBridge iWXBridge = this.mWXBridge;
        return iWXBridge instanceof WXBridge ? ((WXBridge) iWXBridge).encrypt(str, str2, str3, str4) : "";
    }

    public String encryptGetClientKeyPayload(String str, String str2, String str3) {
        IWXBridge iWXBridge = this.mWXBridge;
        return iWXBridge instanceof WXBridge ? ((WXBridge) iWXBridge).encryptGetClientKeyPayload(str, str2, str3) : "";
    }

    public void execJSService(final String str) {
        postWithName(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.25
            @Override // java.lang.Runnable
            public void run() {
                WXBridgeManager wXBridgeManager = WXBridgeManager.this;
                wXBridgeManager.invokeExecJSService(str, wXBridgeManager.mRegisterServiceFailList);
            }
        }, null, "execJSService");
    }

    @Deprecated
    public void fireEvent(String str, String str2, String str3, Map<String, Object> map) {
        fireEvent(str, str2, str3, map, null);
    }

    public void fireEventOnNode(String str, String str2, String str3, Map<String, Object> map, Map<String, Object> map2) {
        fireEventOnNode(str, str2, str3, map, map2, null, null);
    }

    public void forceLayout(String str) {
        if (isSkipFrameworkInit(str) || isJSFrameworkInit()) {
            this.mWXBridge.forceLayout(str);
        }
    }

    public BundType getBundleType(String str, String str2) {
        if (str != null) {
            try {
                String queryParameter = Uri.parse(str).getQueryParameter(BUNDLE_TYPE);
                if (!"Vue".equals(queryParameter) && !"vue".equals(queryParameter)) {
                    if ("Rax".equals(queryParameter) || "rax".equals(queryParameter)) {
                        return BundType.Rax;
                    }
                }
                return BundType.Vue;
            } catch (Throwable th) {
                WXLogUtils.e(WXLogUtils.getStackTrace(th));
                return BundType.Others;
            }
        }
        return str2 != null ? BundType.Vue : BundType.Others;
    }

    public long[] getFirstScreenRenderTime(String str) {
        return isJSFrameworkInit() ? this.mWXBridge.getFirstScreenRenderTime(str) : new long[]{0, 0, 0};
    }

    public WXParams getInitParams() {
        return this.mInitParams;
    }

    public Looper getJSLooper() {
        WXThread wXThread = this.mJSThread;
        if (wXThread != null) {
            return wXThread.getLooper();
        }
        return null;
    }

    public ContentBoxMeasurement getMeasurementFunc(String str, long j) {
        WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
        if (sDKInstance != null) {
            return sDKInstance.getContentBoxMeasurement(j);
        }
        return null;
    }

    public long[] getRenderFinishTime(String str) {
        return isJSFrameworkInit() ? this.mWXBridge.getRenderFinishTime(str) : new long[]{0, 0, 0};
    }

    public String getWeexCoreThreadStackTrace() {
        if (this.mJSThread == null) {
            return "null == mJSThread";
        }
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(StringUtil.format("Thread Name: '%s'\n", this.mJSThread.getName()));
            sb.append(String.format(Locale.ENGLISH, "\"%s\" prio=%d tid=%d %s\n", this.mJSThread.getName(), Integer.valueOf(this.mJSThread.getPriority()), Long.valueOf(this.mJSThread.getId()), this.mJSThread.getState()));
            for (StackTraceElement stackTraceElement : this.mJSThread.getStackTrace()) {
                sb.append(StringUtil.format("\tat %s\n", stackTraceElement.toString()));
            }
        } catch (Exception e) {
            Log.e("weex", "getJSThreadStackTrace error:", e);
        }
        return sb.toString();
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (message == null) {
            return false;
        }
        int i = message.what;
        if (i == 1) {
            TimerInfo timerInfo = (TimerInfo) message.obj;
            if (timerInfo != null) {
                invokeExecJS("", null, METHOD_SET_TIMEOUT, new WXJSObject[]{new WXJSObject(2, timerInfo.callbackId)});
            }
        } else if (i == 13) {
            Object obj = message.obj;
            if (obj != null) {
                this.mWXBridge.takeHeapSnapshot((String) obj);
            }
        } else if (i == 6) {
            invokeCallJSBatch(message);
        } else if (i == 7) {
            invokeInitFramework(message);
        }
        return false;
    }

    public synchronized void initScriptsFramework(String str) {
        Message messageObtainMessage = this.mJSHandler.obtainMessage();
        messageObtainMessage.obj = str;
        messageObtainMessage.what = 7;
        messageObtainMessage.setTarget(this.mJSHandler);
        messageObtainMessage.sendToTarget();
    }

    public int invokeCreateInstanceContext(String str, String str2, String str3, WXJSObject[] wXJSObjectArr, boolean z) {
        WXLogUtils.d("invokeCreateInstanceContext instanceId:" + str + " function:" + str3 + StringUtil.format(" isJSFrameworkInit：%b", Boolean.valueOf(isJSFrameworkInit())));
        StringBuilder sb = this.mLodBuilder;
        sb.append("createInstanceContext >>>> instanceId:");
        sb.append(str);
        sb.append("function:");
        sb.append(str3);
        if (z) {
            StringBuilder sb2 = this.mLodBuilder;
            sb2.append(" tasks:");
            sb2.append(WXJsonUtils.fromObjectToJSONString(wXJSObjectArr));
        }
        WXLogUtils.d(this.mLodBuilder.substring(0));
        this.mLodBuilder.setLength(0);
        return this.mWXBridge.createInstanceContext(str, str2, str3, wXJSObjectArr);
    }

    public void invokeDestoryInstance(String str, String str2, String str3, WXJSObject[] wXJSObjectArr, boolean z) {
        StringBuilder sb = this.mLodBuilder;
        sb.append("callJS >>>> instanceId:");
        sb.append(str);
        sb.append("function:");
        sb.append(str3);
        if (z) {
            StringBuilder sb2 = this.mLodBuilder;
            sb2.append(" tasks:");
            sb2.append(WXJsonUtils.fromObjectToJSONString(wXJSObjectArr));
        }
        WXLogUtils.d(this.mLodBuilder.substring(0));
        this.mLodBuilder.setLength(0);
        this.mWXBridge.removeInstanceRenderType(str);
        this.mWXBridge.destoryInstance(str, str2, str3, wXJSObjectArr);
    }

    public boolean isJSFrameworkInit() {
        return mInit;
    }

    public boolean isJSThread() {
        WXThread wXThread = this.mJSThread;
        return wXThread != null && wXThread.getId() == Thread.currentThread().getId();
    }

    public boolean jsEngineMultiThreadEnable() {
        return isJsEngineMultiThreadEnable;
    }

    public void loadJsBundleInPreInitMode(final String str, final String str2) {
        post(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.9
            @Override // java.lang.Runnable
            public void run() {
                WXBridgeManager.this.invokeExecJSOnInstance(str, str2, -1);
                WXSDKInstance wXSDKInstance = WXSDKManager.getInstance().getAllInstanceMap().get(str);
                if (wXSDKInstance == null || !wXSDKInstance.isPreInitMode()) {
                    return;
                }
                wXSDKInstance.getApmForInstance().onStage(WXInstanceApm.KEY_PAGE_STAGES_LOAD_BUNDLE_END);
                wXSDKInstance.getApmForInstance().onStageWithTime(WXInstanceApm.KEY_PAGE_STAGES_END_EXCUTE_BUNDLE, WXUtils.getFixUnixTime() + 600);
            }
        });
    }

    public void markDirty(String str, String str2, boolean z) {
        if (isSkipFrameworkInit(str) || isJSFrameworkInit()) {
            this.mWXBridge.markDirty(str, str2, z);
        }
    }

    public boolean notifyLayout(String str) {
        if (isSkipFrameworkInit(str) || isJSFrameworkInit()) {
            return this.mWXBridge.notifyLayout(str);
        }
        return false;
    }

    public void notifySerializeCodeCache() {
        post(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.27
            @Override // java.lang.Runnable
            public void run() {
                if (WXBridgeManager.this.isJSFrameworkInit()) {
                    WXBridgeManager.this.invokeExecJS("", null, WXBridgeManager.METHOD_NOTIFY_SERIALIZE_CODE_CACHE, new WXJSObject[0]);
                }
            }
        });
    }

    @Deprecated
    public void notifyTrimMemory() {
    }

    public void onInstanceClose(String str) {
        if (isSkipFrameworkInit(str) || isJSFrameworkInit()) {
            this.mWXBridge.onInstanceClose(str);
        }
    }

    public void onInteractionTimeUpdate(final String str) {
        post(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.1
            @Override // java.lang.Runnable
            public void run() {
                if (WXBridgeManager.this.mWXBridge instanceof WXBridge) {
                    ((WXBridge) WXBridgeManager.this.mWXBridge).nativeOnInteractionTimeUpdate(str);
                }
            }
        });
    }

    public WXJSObject optionObjConvert(boolean z, BundType bundType, WXJSObject wXJSObject) {
        JSONObject jSONObject;
        if (!z) {
            return wXJSObject;
        }
        try {
            JSONObject object = JSON.parseObject(wXJSObject.data.toString());
            JSONObject jSONObject2 = object.getJSONObject("env");
            if (jSONObject2 != null && (jSONObject = jSONObject2.getJSONObject("options")) != null) {
                for (String str : jSONObject.keySet()) {
                    jSONObject2.put(str, (Object) jSONObject.getString(str));
                }
            }
            return new WXJSObject(3, object.toString());
        } catch (Throwable th) {
            WXLogUtils.e(WXLogUtils.getStackTrace(th));
            return wXJSObject;
        }
    }

    @Override // com.taobao.weex.utils.batch.BactchExecutor
    public void post(Runnable runnable) {
        postWithName(runnable, null, null);
    }

    public void postDelay(Runnable runnable, long j) {
        Handler handler = this.mJSHandler;
        if (handler == null) {
            return;
        }
        handler.postDelayed(WXThread.secure(runnable), j);
    }

    public void postWithName(Runnable runnable, WXSDKInstance wXSDKInstance, String str) {
        Handler handler;
        Runnable runnableSecure = WXThread.secure(runnable, wXSDKInstance, str);
        Interceptor interceptor = this.mInterceptor;
        if ((interceptor == null || !interceptor.take(runnableSecure)) && (handler = this.mJSHandler) != null) {
            handler.post(runnableSecure);
        }
    }

    public void refreshInstance(final String str, final WXRefreshData wXRefreshData) {
        if (TextUtils.isEmpty(str) || wXRefreshData == null) {
            return;
        }
        this.mJSHandler.postDelayed(WXThread.secure(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.16
            @Override // java.lang.Runnable
            public void run() {
                WXBridgeManager.this.invokeRefreshInstance(str, wXRefreshData);
            }
        }), 0L);
    }

    public void registerComponents(final List<Map<String, Object>> list) {
        if (this.mJSHandler == null || list == null || list.size() == 0) {
            return;
        }
        Runnable runnable = new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.24
            @Override // java.lang.Runnable
            public void run() {
                WXBridgeManager wXBridgeManager = WXBridgeManager.this;
                wXBridgeManager.invokeRegisterComponents(list, wXBridgeManager.mRegisterComponentFailList);
            }
        };
        if (isJSThread() && isJSFrameworkInit()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }

    public void registerCoreEnv(String str, String str2) {
        if (isJSFrameworkInit()) {
            this.mWXBridge.registerCoreEnv(str, str2);
        } else {
            mWeexCoreEnvOptions.put(str, str2);
        }
    }

    public void registerModules(final Map<String, Object> map) {
        if (map == null || map.size() == 0) {
            return;
        }
        if (isJSThread()) {
            invokeRegisterModules(map, this.mRegisterModuleFailList);
        } else {
            post(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.23
                @Override // java.lang.Runnable
                public void run() {
                    WXBridgeManager wXBridgeManager = WXBridgeManager.this;
                    wXBridgeManager.invokeRegisterModules(map, wXBridgeManager.mRegisterModuleFailList);
                }
            }, null);
        }
    }

    public void reloadPageLayout(String str) {
        if (isSkipFrameworkInit(str) || isJSFrameworkInit()) {
            this.mWXBridge.reloadPageLayout(str);
        }
    }

    public void removeMessage(int i, Object obj) {
        WXThread wXThread;
        if (this.mJSHandler == null || (wXThread = this.mJSThread) == null || !wXThread.isWXThreadAlive() || this.mJSThread.getLooper() == null) {
            return;
        }
        this.mJSHandler.removeMessages(i, obj);
    }

    public void reportJSException(String str, String str2, String str3) {
        WXSDKInstance sDKInstance;
        WXErrorCode wXErrorCode;
        int i;
        String str4;
        if (str2.startsWith("jsscope::")) {
            Context applicationContext = WXEnvironment.getApplication().getApplicationContext();
            if (applicationContext != null && !BaseInfo.isBase(applicationContext)) {
                str.getClass();
                str.hashCode();
                i = 4;
                str4 = "uni-jsframework.js";
                switch (str) {
                    case "jsframework":
                    case "uni-jsframework.js":
                    case "jsfm":
                    case "service":
                        break;
                    case "app-service.js":
                        str4 = str;
                        i = 5;
                        break;
                    default:
                        WXSDKInstance sDKInstance2 = WXSDKManager.getInstance().getSDKInstance(str);
                        if (sDKInstance2 != null) {
                            String uniPagePath = sDKInstance2.getUniPagePath();
                            str4 = uniPagePath;
                            if (uniPagePath == "app-service.js") {
                                i = 5;
                                break;
                            }
                        } else {
                            str4 = str;
                        }
                        i = 6;
                        break;
                }
                org.json.JSONObject jSONObjectCreateJSONObject = JSONUtil.createJSONObject(AppRuntime.getUniStatistics());
                if (jSONObjectCreateJSONObject != null) {
                    String string = JSONUtil.getString(jSONObjectCreateJSONObject, "version");
                    boolean z = JSONUtil.getBoolean(jSONObjectCreateJSONObject, WebLoadEvent.ENABLE);
                    if (PdrUtil.isEquals("2", string) && z) {
                        UEH.commitUncatchException(applicationContext, str4, str3, i);
                    }
                }
            }
            str2 = str2.substring(9);
        }
        WXLogUtils.e("reportJSException >>>> instanceId:" + str + ", exception function:" + str2 + ", exception:" + str3);
        WXErrorCode wXErrorCode2 = WXErrorCode.WX_ERR_JS_EXECUTE;
        if (str != null && (sDKInstance = WXSDKManager.getInstance().getSDKInstance(str)) != null) {
            sDKInstance.setHasException(true);
            if (METHOD_CREATE_INSTANCE.equals(str2) || !sDKInstance.isContentMd5Match()) {
                try {
                    if (!isSkipFrameworkInit(str) && isJSFrameworkInit() && reInitCount > 1 && reInitCount < 10 && !sDKInstance.isNeedReLoad()) {
                        new ActionReloadPage(str, true).executeAction();
                        sDKInstance.setNeedLoad(true);
                        return;
                    }
                    StringBuilder sb = new StringBuilder();
                    WXErrorCode wXErrorCode3 = WXErrorCode.WX_DEGRAD_ERR_INSTANCE_CREATE_FAILED;
                    sb.append(wXErrorCode3.getErrorMsg());
                    sb.append(", reportJSException >>>> instanceId:");
                    sb.append(str);
                    sb.append(", exception function:");
                    sb.append(str2);
                    sb.append(", exception:");
                    sb.append(str3);
                    sb.append(", extInitTime:");
                    sb.append(System.currentTimeMillis() - sInitFrameWorkTimeOrigin);
                    sb.append("ms, extInitErrorMsg:");
                    sb.append(sInitFrameWorkMsg.toString());
                    sDKInstance.onRenderError(wXErrorCode3.getErrorCode(), sb.toString());
                    if (WXEnvironment.sInAliWeex) {
                        return;
                    }
                    WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_JS_CREATE_INSTANCE, str2, str3, null);
                    return;
                } catch (Exception e) {
                    WXLogUtils.e(WXLogUtils.getStackTrace(e));
                }
            }
            if (METHOD_CREATE_INSTANCE.equals(str2) && !sDKInstance.getApmForInstance().hasAddView) {
                wXErrorCode = WXErrorCode.WX_RENDER_ERR_JS_CREATE_INSTANCE;
            } else if (METHOD_CREATE_INSTANCE_CONTEXT.equals(str2) && !sDKInstance.getApmForInstance().hasAddView) {
                wXErrorCode = WXErrorCode.WX_RENDER_ERR_JS_CREATE_INSTANCE_CONTEXT;
            } else if ((METHOD_UPDATE_COMPONENT_WITH_DATA.equals(str2) || METHOD_CREATE_PAGE_WITH_CONTENT.equals(str2) || METHOD_POST_TASK_TO_MSG_LOOP.equals(str2) || METHOD_JSFM_NOT_INIT_IN_EAGLE_MODE.equals(str2)) && !sDKInstance.getApmForInstance().hasAddView) {
                wXErrorCode = WXErrorCode.WX_DEGRAD_EAGLE_RENDER_ERROR;
            } else {
                if (METHOD_CHECK_APPKEY.equals(str2)) {
                    wXErrorCode = WXErrorCode.WX_KEY_EXCEPTION_VALIDAPPKEY;
                }
                sDKInstance.onJSException(wXErrorCode2.getErrorCode(), str2, str3);
            }
            wXErrorCode2 = wXErrorCode;
            sDKInstance.onJSException(wXErrorCode2.getErrorCode(), str2, str3);
        }
        doReportJSException(str, str2, wXErrorCode2, str3);
    }

    public void restart() {
        setJSFrameworkInit(false);
        WXModuleManager.resetAllModuleState();
        initWXBridge(WXEnvironment.sRemoteDebugMode);
        this.mWXBridge.resetWXBridge(WXEnvironment.sRemoteDebugMode);
    }

    public void sendMessageDelayed(Message message, long j) {
        WXThread wXThread;
        if (message == null || this.mJSHandler == null || (wXThread = this.mJSThread) == null || !wXThread.isWXThreadAlive() || this.mJSThread.getLooper() == null) {
            return;
        }
        this.mJSHandler.sendMessageDelayed(message, j);
    }

    public void setDefaultRootSize(String str, float f, float f2, boolean z, boolean z2) {
        if (isSkipFrameworkInit(str) || isJSFrameworkInit()) {
            this.mWXBridge.setDefaultHeightAndWidthIntoRootDom(str, f, f2, z, z2);
        }
    }

    public void setDeviceDisplay(final String str, final float f, final float f2, final float f3) {
        post(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.28
            @Override // java.lang.Runnable
            public void run() {
                WXBridgeManager.this.mWXBridge.setDeviceDisplay(str, f, f2, f3);
            }
        });
    }

    public void setDeviceDisplayOfPage(String str, float f, float f2) {
        if (isSkipFrameworkInit(str) || isJSFrameworkInit()) {
            this.mWXBridge.setDeviceDisplayOfPage(str, f, f2);
        }
    }

    public void setFlexDirectionDef(String str) {
        if (isJSFrameworkInit()) {
            this.mWXBridge.setFlexDirectionDef(str);
        }
    }

    @Override // com.taobao.weex.utils.batch.BactchExecutor
    public void setInterceptor(Interceptor interceptor) {
        this.mInterceptor = interceptor;
    }

    public void setLogLevel(final int i, final boolean z) {
        post(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.6
            @Override // java.lang.Runnable
            public void run() {
                if (WXBridgeManager.this.mWXBridge != null) {
                    WXBridgeManager.this.mWXBridge.setLogType(i, z);
                }
            }
        });
    }

    public void setMargin(String str, String str2, CSSShorthand.EDGE edge, float f) {
        if (isSkipFrameworkInit(str) || isJSFrameworkInit()) {
            this.mWXBridge.setMargin(str, str2, edge, f);
        }
    }

    public void setPadding(String str, String str2, CSSShorthand.EDGE edge, float f) {
        if (isSkipFrameworkInit(str) || isJSFrameworkInit()) {
            this.mWXBridge.setPadding(str, str2, edge, f);
        }
    }

    public void setPageArgument(String str, String str2, String str3) {
        if (isSkipFrameworkInit(str) || isJSFrameworkInit()) {
            this.mWXBridge.setPageArgument(str, str2, str3);
        }
    }

    public void setPosition(String str, String str2, CSSShorthand.EDGE edge, float f) {
        if (isSkipFrameworkInit(str) || isJSFrameworkInit()) {
            this.mWXBridge.setPosition(str, str2, edge, f);
        }
    }

    public void setRenderContentWrapContentToCore(boolean z, String str) {
        if (isJSFrameworkInit()) {
            this.mWXBridge.setRenderContainerWrapContent(z, str);
        }
    }

    public void setSandBoxContext(boolean z) {
        if (z != isSandBoxContext) {
            isSandBoxContext = z;
            if (!isJSThread()) {
                post(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.3
                    @Override // java.lang.Runnable
                    public void run() {
                        WXBridgeManager.this.setJSFrameworkInit(false);
                        WXModuleManager.resetAllModuleState();
                        WXBridgeManager.this.initFramework(!WXBridgeManager.isSandBoxContext ? WXFileUtils.loadAsset("main.js", WXEnvironment.getApplication()) : WXFileUtils.loadAsset("weex-main-jsfm.js", WXEnvironment.getApplication()));
                        WXServiceManager.reload();
                        WXModuleManager.reload();
                        WXComponentRegistry.reload();
                    }
                });
                return;
            }
            setJSFrameworkInit(false);
            WXModuleManager.resetAllModuleState();
            initFramework(!isSandBoxContext ? WXFileUtils.loadAsset("main.js", WXEnvironment.getApplication()) : WXFileUtils.loadAsset("weex-main-jsfm.js", WXEnvironment.getApplication()));
            WXServiceManager.reload();
            WXModuleManager.reload();
            WXComponentRegistry.reload();
        }
    }

    public synchronized void setStackTopInstance(final String str) {
        post(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.5
            @Override // java.lang.Runnable
            public void run() {
                WXBridgeManager.this.mNextTickTasks.setStackTopInstance(str);
            }
        }, str, null, null);
    }

    public void setStyleHeight(String str, String str2, float f) {
        setStyleHeight(str, str2, f, false);
    }

    public void setStyleWidth(String str, String str2, float f) {
        setStyleWidth(str, str2, f, false);
    }

    void setTimeout(String str, String str2) {
        Message messageObtain = Message.obtain();
        messageObtain.what = 1;
        TimerInfo timerInfo = new TimerInfo();
        timerInfo.callbackId = str;
        long j = (long) Float.parseFloat(str2);
        timerInfo.time = j;
        messageObtain.obj = timerInfo;
        this.mJSHandler.sendMessageDelayed(messageObtain, j);
    }

    public void setUseSingleProcess(boolean z) {
        if (z != isUseSingleProcess) {
            isUseSingleProcess = z;
        }
    }

    public void setViewPortWidth(String str, float f) {
        if (isJSFrameworkInit()) {
            this.mWXBridge.setViewPortWidth(str, f);
        }
    }

    public boolean shouldReloadCurrentInstance(String str) {
        Uri uri;
        long jCurrentTimeMillis = System.currentTimeMillis();
        IWXConfigAdapter wxConfigAdapter = WXSDKManager.getInstance().getWxConfigAdapter();
        if (wxConfigAdapter != null) {
            boolean z = Boolean.parseBoolean(wxConfigAdapter.getConfig("android_weex_ext_config", "check_biz_url", AbsoluteConst.TRUE));
            WXLogUtils.e("check_biz_url : " + z);
            if (z && !TextUtils.isEmpty(str) && (uri = Uri.parse(str)) != null) {
                str = uri.buildUpon().clearQuery().build().toString();
            }
        }
        String str2 = crashUrl;
        if (str2 != null && ((str2 == null || str2.equals(str)) && jCurrentTimeMillis - lastCrashTime <= 15000)) {
            lastCrashTime = jCurrentTimeMillis;
            return false;
        }
        crashUrl = str;
        lastCrashTime = jCurrentTimeMillis;
        return true;
    }

    public void stopRemoteDebug() {
        Method method;
        if (this.mWxDebugProxy != null) {
            try {
                if (clazz_debugProxy == null) {
                    clazz_debugProxy = Class.forName("com.taobao.weex.devtools.debug.DebugServerProxy");
                }
                Class cls = clazz_debugProxy;
                if (cls == null || (method = cls.getMethod(Constants.Value.STOP, Boolean.TYPE)) == null) {
                    return;
                }
                method.invoke(this.mWxDebugProxy, Boolean.FALSE);
                this.mWxDebugProxy = null;
            } catch (Throwable unused) {
            }
        }
    }

    public EventResult syncCallJSEventWithResult(String str, String str2, List<Object> list, Object... objArr) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        EventResult eventResult = new EventResult() { // from class: com.taobao.weex.bridge.WXBridgeManager.10
            @Override // com.taobao.weex.bridge.EventResult
            public void onCallback(Object obj) {
                super.onCallback(obj);
                countDownLatch.countDown();
            }
        };
        try {
            asyncCallJSEventWithResult(eventResult, str, str2, list, objArr);
            countDownLatch.await(100L, TimeUnit.MILLISECONDS);
            return eventResult;
        } catch (Exception e) {
            WXLogUtils.e("syncCallJSEventWithResult", e);
            return eventResult;
        }
    }

    public String syncExecJsOnInstanceWithResult(String str, String str2, int i) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        EventResult eventResult = new EventResult() { // from class: com.taobao.weex.bridge.WXBridgeManager.8
            @Override // com.taobao.weex.bridge.EventResult
            public void onCallback(Object obj) {
                super.onCallback(obj);
                countDownLatch.countDown();
            }
        };
        try {
            execJSOnInstance(eventResult, str, str2, i);
            countDownLatch.await(100L, TimeUnit.MILLISECONDS);
            return eventResult.getResult() != null ? eventResult.getResult().toString() : "";
        } catch (Throwable th) {
            WXLogUtils.e("syncCallExecJsOnInstance", th);
            return "";
        }
    }

    public void takeJSHeapSnapshot(String str) {
        Message messageObtainMessage = this.mJSHandler.obtainMessage();
        messageObtainMessage.obj = str;
        messageObtainMessage.what = 13;
        messageObtainMessage.setTarget(this.mJSHandler);
        messageObtainMessage.sendToTarget();
    }

    public void updateInitDeviceParams(final String str, final String str2, final String str3, final String str4) {
        if (isJSFrameworkInit()) {
            post(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.29
                @Override // java.lang.Runnable
                public void run() {
                    WXBridgeManager.this.mWXBridge.updateInitFrameworkParams(WXConfig.deviceWidth, str, WXConfig.deviceWidth);
                }
            });
            post(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.30
                @Override // java.lang.Runnable
                public void run() {
                    WXBridgeManager.this.mWXBridge.updateInitFrameworkParams(WXConfig.deviceHeight, str2, WXConfig.deviceHeight);
                }
            });
            post(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.31
                @Override // java.lang.Runnable
                public void run() {
                    WXBridgeManager.this.mWXBridge.updateInitFrameworkParams("scale", str3, "scale");
                }
            });
            if (str4 != null) {
                post(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.32
                    @Override // java.lang.Runnable
                    public void run() {
                        WXBridgeManager.this.mWXBridge.updateInitFrameworkParams(WXConfig.androidStatusBarHeight, str4, WXConfig.androidStatusBarHeight);
                    }
                });
            }
        }
    }

    public boolean verifyClientKeyPayload(String str, String str2, String str3) {
        IWXBridge iWXBridge = this.mWXBridge;
        if (iWXBridge instanceof WXBridge) {
            return ((WXBridge) iWXBridge).verifyClientKeyPayload(str, str2, str3);
        }
        return false;
    }

    private void invokeInitFramework(Message message) {
        Object obj = message.obj;
        String str = obj != null ? (String) obj : "";
        if (WXUtils.getAvailMemory(WXEnvironment.getApplication()) > LOW_MEM_VALUE) {
            initFramework(str);
        }
    }

    public Object callModuleMethod(String str, String str2, String str3, JSONArray jSONArray, JSONObject jSONObject) {
        WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
        if (sDKInstance == null) {
            return null;
        }
        if (!sDKInstance.isNeedValidate() || WXSDKManager.getInstance().getValidateProcessor() == null) {
            try {
                return WXModuleManager.callModuleMethod(str, str2, str3, jSONArray);
            } catch (NumberFormatException unused) {
                ArrayMap arrayMap = new ArrayMap();
                arrayMap.put("moduleName", str2);
                arrayMap.put("methodName", str3);
                arrayMap.put("args", jSONArray.toJSONString());
                WXLogUtils.e("[WXBridgeManager] callNative : numberFormatException when parsing string to numbers in args", arrayMap.toString());
                return null;
            }
        }
        WXValidateProcessor.WXModuleValidateResult wXModuleValidateResultOnModuleValidate = WXSDKManager.getInstance().getValidateProcessor().onModuleValidate(sDKInstance, str2, str3, jSONArray, jSONObject);
        if (wXModuleValidateResultOnModuleValidate == null) {
            return null;
        }
        if (wXModuleValidateResultOnModuleValidate.isSuccess) {
            return WXModuleManager.callModuleMethod(str, str2, str3, jSONArray);
        }
        JSONObject jSONObject2 = wXModuleValidateResultOnModuleValidate.validateInfo;
        if (jSONObject2 != null) {
            WXLogUtils.e("[WXBridgeManager] module validate fail. >>> " + jSONObject2.toJSONString());
        }
        return jSONObject2;
    }

    @Deprecated
    public void callback(String str, String str2, Map<String, Object> map) {
        callback(str, str2, map, false);
    }

    public void createInstance(final String str, final Script script, final Map<String, Object> map, final String str2) {
        final WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
        if (sDKInstance == null) {
            WXLogUtils.e("WXBridgeManager", "createInstance failed, SDKInstance does not exist");
            return;
        }
        if (TextUtils.isEmpty(str) || script == null || script.isEmpty() || this.mJSHandler == null) {
            WXErrorCode wXErrorCode = WXErrorCode.WX_DEGRAD_ERR_INSTANCE_CREATE_FAILED;
            sDKInstance.onRenderError(wXErrorCode.getErrorCode(), wXErrorCode.getErrorMsg() + " instanceId==" + str + " template ==" + script + " mJSHandler== " + this.mJSHandler.toString());
            return;
        }
        if (isSkipFrameworkInit(str) || isJSFrameworkInit() || reInitCount != 1 || WXEnvironment.sDebugServerConnectable) {
            WXModuleManager.createDomModule(sDKInstance);
            sDKInstance.getApmForInstance().onStage(WXInstanceApm.KEY_PAGE_STAGES_LOAD_BUNDLE_START);
            post(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.18
                @Override // java.lang.Runnable
                public void run() {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    WXBridgeManager.this.mWXBridge.setPageArgument(str, "renderTimeOrigin", String.valueOf(sDKInstance.getWXPerformance().renderTimeOrigin));
                    WXBridgeManager.this.mWXBridge.setInstanceRenderType(sDKInstance.getInstanceId(), sDKInstance.getRenderType());
                    WXBridgeManager.this.invokeCreateInstance(sDKInstance, script, map, str2);
                    sDKInstance.getWXPerformance().callCreateInstanceTime = System.currentTimeMillis() - jCurrentTimeMillis;
                    sDKInstance.getWXPerformance().communicateTime = sDKInstance.getWXPerformance().callCreateInstanceTime;
                }
            }, str, sDKInstance, METHOD_CREATE_INSTANCE);
            return;
        }
        WXErrorCode wXErrorCode2 = WXErrorCode.WX_DEGRAD_ERR_INSTANCE_CREATE_FAILED;
        sDKInstance.onRenderError(wXErrorCode2.getErrorCode(), wXErrorCode2.getErrorMsg() + " isJSFrameworkInit==" + isJSFrameworkInit() + " reInitCount == 1");
        post(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.17
            @Override // java.lang.Runnable
            public void run() {
                WXBridgeManager.this.initFramework("");
            }
        }, str, sDKInstance, "initFrameworkInCreateInstance");
    }

    @Deprecated
    public void fireEvent(String str, String str2, String str3, Map<String, Object> map, Map<String, Object> map2) {
        fireEventOnNode(str, str2, str3, map, map2);
    }

    public void fireEventOnNode(String str, String str2, String str3, Map<String, Object> map, Map<String, Object> map2, List<Object> list) {
        fireEventOnNode(str, str2, str3, map, map2, list, null);
    }

    public void invokeExecJS(final String str, final String str2, final String str3, final WXJSObject[] wXJSObjectArr, boolean z) {
        Pair<Pair<String, Object>, Boolean> pairExtractCallbackArgs;
        WXEnvironment.isOpenDebugLog();
        if (RuningAcitvityUtil.isRuningActivity) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
            if (sDKInstance != null && sDKInstance.getRenderStrategy() == WXRenderStrategy.DATA_RENDER_BINARY) {
                if (wXJSObjectArr.length == 2 && (wXJSObjectArr[0].data instanceof String)) {
                    Object obj = wXJSObjectArr[1].data;
                    if ((obj instanceof String) && (pairExtractCallbackArgs = extractCallbackArgs((String) obj)) != null) {
                        Pair pair = (Pair) pairExtractCallbackArgs.first;
                        callbackJavascriptOnDataRender(str, (String) pair.first, pair.second, ((Boolean) pairExtractCallbackArgs.second).booleanValue());
                    }
                }
                WXLogUtils.w("invokeExecJS on data render that is not a callback call");
                return;
            }
            WXThread.secure(new Runnable() { // from class: com.taobao.weex.bridge.WXBridgeManager.20
                @Override // java.lang.Runnable
                public void run() {
                    WXBridgeManager.this.mWXBridge.execJS(str, str2, str3, wXJSObjectArr);
                }
            }, sDKInstance, "ExecJs").run();
            if (sDKInstance != null) {
                long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
                sDKInstance.getApmForInstance().updateFSDiffStats(WXInstanceApm.KEY_PAGE_STATS_FS_CALL_JS_NUM, 1.0d);
                sDKInstance.getApmForInstance().updateFSDiffStats(WXInstanceApm.KEY_PAGE_STATS_FS_CALL_JS_TIME, jCurrentTimeMillis2);
                sDKInstance.callJsTime(jCurrentTimeMillis2);
            }
        }
    }

    public void post(Runnable runnable, Object obj, WXSDKInstance wXSDKInstance, String str) {
        Handler handler = this.mJSHandler;
        if (handler == null) {
            return;
        }
        Message messageObtain = Message.obtain(handler, WXThread.secure(runnable, wXSDKInstance, str));
        messageObtain.obj = obj;
        messageObtain.sendToTarget();
    }

    public void setStyleHeight(String str, String str2, float f, boolean z) {
        if (isSkipFrameworkInit(str) || isJSFrameworkInit()) {
            this.mWXBridge.setStyleHeight(str, str2, f, z);
        }
    }

    public void setStyleWidth(String str, String str2, float f, boolean z) {
        if (isSkipFrameworkInit(str) || isJSFrameworkInit()) {
            this.mWXBridge.setStyleWidth(str, str2, f, z);
        }
    }

    private void getNextTick(String str) {
        addJSTask(METHOD_CALLBACK, str, "", "{}");
        sendMessage(str, 6);
    }

    private boolean isSkipFrameworkInit(WXSDKInstance wXSDKInstance) {
        if (wXSDKInstance == null) {
            return false;
        }
        return wXSDKInstance.skipFrameworkInit();
    }

    @Deprecated
    public void callback(String str, String str2, Object obj, boolean z) {
        callbackJavascript(str, str2, obj, z);
    }

    public void fireEventOnNode(String str, String str2, String str3, Map<String, Object> map, Map<String, Object> map2, List<Object> list, EventResult eventResult) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || this.mJSHandler == null) {
            return;
        }
        if (checkMainThread()) {
            WXSDKInstance wXSDKInstance = WXSDKManager.getInstance().getAllInstanceMap().get(str);
            if (wXSDKInstance != null && (wXSDKInstance.getRenderStrategy() == WXRenderStrategy.DATA_RENDER || wXSDKInstance.getRenderStrategy() == WXRenderStrategy.DATA_RENDER_BINARY)) {
                fireEventOnDataRenderNode(str, str2, str3, map, map2);
                return;
            } else if (eventResult == null) {
                addJSEventTask(METHOD_FIRE_EVENT, str, list, str2, str3, map, map2);
                sendMessage(str, 6);
                return;
            } else {
                asyncCallJSEventWithResult(eventResult, METHD_FIRE_EVENT_SYNC, str, list, str2, str3, map, map2);
                return;
            }
        }
        throw new WXRuntimeException("fireEvent must be called by main thread");
    }

    public void post(Runnable runnable, Object obj) {
        post(runnable, obj, null, null);
    }

    public Object callNativeModule(String str, String str2, String str3, JSONArray jSONArray, JSONObject jSONObject) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            WXStateRecord.getInstance().recordAction(str, "callNativeModule:" + str2 + Operators.DOT_STR + str3);
            WXEnvironment.isApkDebugable();
            try {
            } catch (Exception e) {
                WXLogUtils.e("[WXBridgeManager] callNativeModule exception: " + WXLogUtils.getStackTrace(e));
            }
            if (WXDomModule.WXDOM.equals(str2)) {
                WXDomModule domModule = WXModuleManager.getDomModule(str);
                if (domModule != null) {
                    return domModule.callDomMethod(str3, jSONArray, new long[0]);
                }
                WXModuleManager.createDomModule(WXSDKManager.getInstance().getSDKInstance(str));
                return null;
            }
            return callModuleMethod(str, str2, str3, jSONArray, jSONObject);
        }
        WXLogUtils.d("[WXBridgeManager] call callNativeModule arguments is null");
        WXExceptionUtils.commitCriticalExceptionRT(str, WXErrorCode.WX_RENDER_ERR_BRIDGE_ARG_NULL, "callNativeModule", "arguments is empty, INSTANCE_RENDERING_ERROR will be set", null);
        return 0;
    }
}
