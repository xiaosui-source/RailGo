package io.dcloud.feature.weex.extend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hjq.permissions.Permission;
import com.taobao.weex.R;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.bridge.SimpleJSCallback;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.Constants;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.ui.component.DCWXInput;
import com.taobao.weex.ui.component.WXImage;
import io.dcloud.application.DCLoudApplicationImpl;
import io.dcloud.common.DHInterface.IActivityHandler;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaUniWebView;
import io.dcloud.common.adapter.ui.webview.WebResUtil;
import io.dcloud.common.adapter.ui.webview.WebViewFactory;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.EventActionInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.PermissionUtil;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.IntentConst;
import io.dcloud.common.ui.blur.DCBlurDraweeView;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.IOUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import io.dcloud.common.util.TelephonyUtil;
import io.dcloud.common.util.ThreadPool;
import io.dcloud.common.util.language.LanguageUtil;
import io.dcloud.feature.internal.sdk.SDK;
import io.dcloud.feature.uniapp.bridge.UniJSCallback;
import io.dcloud.feature.weex.WXBaseWrapper;
import io.dcloud.feature.weex.WXServiceWrapper;
import io.dcloud.feature.weex.WeexInstanceMgr;
import io.dcloud.feature.weex.extend.result.Result;
import io.dcloud.feature.weex.extend.result.SecureNetworkResult;
import io.dcloud.weex.WXDotDataUtil;
import io.src.dcloud.adapter.DCloudAdapterUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONException;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class PlusModule extends WXModule {
    private static final String HELP_LOG_HASH = "HELP_LOG_HASH_";
    private String EVENTS_DOCUMENT_EXECUTE = "javascript:!function(){(window.__html5plus__&&__html5plus__.isReady?__html5plus__:navigator.plus&&navigator.plus.isReady?navigator.plus:window.plus)||window.__load__plus__&&window.__load__plus__();var _=document.createEvent(\"HTMLEvents\");_.initEvent(\"%s\",!1,!0),_.targetId=\"%s\",_.originId=\"%s\",_.data=%s,document.dispatchEvent(_)}();";
    ArrayList<JsData> chs = new ArrayList<>();

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    class JsData {
        public String data;
        public String value;

        JsData(String str, String str2) {
            this.data = str;
            this.value = str2;
        }
    }

    private String getClientKey(String str, String str2) {
        return this.mWXSDKInstance.getContext().getSharedPreferences(HELP_LOG_HASH + BaseInfo.sDefaultBootApp + str2 + str, 0).getString("HELP_LOG_HASH", "");
    }

    private void runChData() throws JSONException {
        if (this.chs.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList<JsData> arrayList2 = this.chs;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            JsData jsData = arrayList2.get(i);
            i++;
            JsData jsData2 = jsData;
            exec(jsData2.data, jsData2.value);
            arrayList.add(jsData2);
        }
        this.chs.removeAll(arrayList);
    }

    @JSMethod(uiThread = false)
    public void decrypt(JSONObject jSONObject, JSCallback jSCallback) {
        if (SDK.isUniMPSDK() || BaseInfo.isStandardBase(this.mWXSDKInstance.getContext())) {
            jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.NOT_SUPPORT_MP_OR_BASE));
            return;
        }
        if (jSONObject == null || jSCallback == null) {
            if (jSCallback != null) {
                jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.PARAMS_IS_NULL));
                return;
            }
            return;
        }
        String metaValue = AndroidResources.getMetaValue("dcloud_sn_appkey");
        String str = (String) jSONObject.get("data");
        String str2 = (String) jSONObject.get(IApp.ConfigProperty.CONFIG_KEY);
        String str3 = (String) jSONObject.get("spaceId");
        String str4 = (String) jSONObject.get("provider");
        if (PdrUtil.isEmpty(str) || PdrUtil.isEmpty(str2) || PdrUtil.isEmpty(str3) || PdrUtil.isEmpty(str4)) {
            jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.PARAMS_IS_NULL));
            return;
        }
        String clientKey = getClientKey(str3, str4);
        if (PdrUtil.isEmpty(metaValue)) {
            jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.APP_KEY_IS_NULL));
            return;
        }
        if (PdrUtil.isEmpty(clientKey)) {
            jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.CLIENT_KEY_IS_NULL));
            return;
        }
        String strDecrypt = WXBridgeManager.getInstance().decrypt(BaseInfo.sDefaultBootApp, jSONObject.toJSONString(), metaValue, clientKey);
        if (PdrUtil.isEmpty(strDecrypt)) {
            jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.DECRYPT_ERROR));
            return;
        }
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("data", (Object) strDecrypt);
        jSCallback.invoke(Result.boxSuccessResult(jSONObject2));
    }

    @JSMethod(uiThread = false)
    public void encrypt(JSONObject jSONObject, JSCallback jSCallback) {
        if (SDK.isUniMPSDK() || BaseInfo.isStandardBase(this.mWXSDKInstance.getContext())) {
            jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.NOT_SUPPORT_MP_OR_BASE));
            return;
        }
        if (jSONObject == null || jSCallback == null) {
            if (jSCallback != null) {
                jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.PARAMS_IS_NULL));
                return;
            }
            return;
        }
        String metaValue = AndroidResources.getMetaValue("dcloud_sn_appkey");
        String str = (String) jSONObject.get("data");
        String str2 = (String) jSONObject.get("spaceId");
        String str3 = (String) jSONObject.get("provider");
        if (PdrUtil.isEmpty(str) || PdrUtil.isEmpty(str2) || PdrUtil.isEmpty(str3)) {
            jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.PARAMS_IS_NULL));
            return;
        }
        String clientKey = getClientKey(str2, str3);
        if (PdrUtil.isEmpty(metaValue)) {
            jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.APP_KEY_IS_NULL));
            return;
        }
        if (PdrUtil.isEmpty(clientKey)) {
            jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.CLIENT_KEY_IS_NULL));
            return;
        }
        String strEncrypt = WXBridgeManager.getInstance().encrypt(BaseInfo.sDefaultBootApp, str, metaValue, clientKey);
        if (PdrUtil.isEmpty(strEncrypt)) {
            jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.ENCRYPT_ERROR));
        } else {
            jSCallback.invoke(Result.boxSuccessResult(JSON.parse(strEncrypt)));
        }
    }

    @JSMethod(uiThread = false)
    public void encryptGetClientKeyPayload(JSONObject jSONObject, JSCallback jSCallback) {
        if (SDK.isUniMPSDK() || BaseInfo.isStandardBase(this.mWXSDKInstance.getContext())) {
            jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.NOT_SUPPORT_MP_OR_BASE));
            return;
        }
        if (jSONObject == null || jSCallback == null) {
            if (jSCallback != null) {
                jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.PARAMS_IS_NULL));
                return;
            }
            return;
        }
        String metaValue = AndroidResources.getMetaValue("dcloud_sn_appkey");
        String jSONString = jSONObject.toJSONString();
        if (PdrUtil.isEmpty(metaValue)) {
            jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.APP_KEY_IS_NULL));
            return;
        }
        if (PdrUtil.isEmpty(jSONString)) {
            jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.JSON_FORMAT_ERROR));
            return;
        }
        String strEncryptGetClientKeyPayload = WXBridgeManager.getInstance().encryptGetClientKeyPayload(BaseInfo.sDefaultBootApp, jSONString, metaValue);
        if (PdrUtil.isEmpty(strEncryptGetClientKeyPayload)) {
            jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.ENCRYPT_CLIENT_KEY_PAYLOAD_ERROR));
            return;
        }
        JSONObject object = JSON.parseObject(strEncryptGetClientKeyPayload);
        if (object != null) {
            jSCallback.invoke(Result.boxSuccessResult(object));
        } else {
            jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.NATIVE_JSON_FORMAT_ERROR));
        }
    }

    @JSMethod(uiThread = true)
    public void evalJSFiles(String str, final JSCallback jSCallback) {
        final HashMap map = new HashMap();
        if (str == null) {
            if (jSCallback != null) {
                map.put("type", -1);
                map.put(NotificationCompat.CATEGORY_MESSAGE, DCLoudApplicationImpl.self().getContext().getString(R.string.dcloud_feature_weex_msg_param_empty));
                jSCallback.invoke(map);
                return;
            }
            return;
        }
        final JSONArray array = JSON.parseArray(str);
        if (array != null) {
            ThreadPool.self().addThreadTask(new Runnable() { // from class: io.dcloud.feature.weex.extend.PlusModule.1
                @Override // java.lang.Runnable
                public void run() {
                    InputStream fileInputStream;
                    IWebview iWebviewFindWebview = WeexInstanceMgr.self().findWebview(PlusModule.this.mWXSDKInstance);
                    if (iWebviewFindWebview == null) {
                        if (jSCallback != null) {
                            map.put("type", -1);
                            map.put(NotificationCompat.CATEGORY_MESSAGE, DCLoudApplicationImpl.self().getContext().getString(R.string.dcloud_feature_weex_msg_page_destroyed));
                            jSCallback.invoke(map);
                            return;
                        }
                        return;
                    }
                    byte bObtainRunningAppMode = iWebviewFindWebview.obtainApp().obtainRunningAppMode();
                    String str2 = "";
                    for (int i = 0; i < array.size(); i++) {
                        String string = array.getString(i);
                        File file = new File(string);
                        if (file.exists()) {
                            try {
                                fileInputStream = new FileInputStream(file);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                fileInputStream = null;
                            }
                        } else {
                            if (string.startsWith("/storage") || bObtainRunningAppMode != 1) {
                                string = iWebviewFindWebview.obtainApp().convert2WebviewFullPath(iWebviewFindWebview.obtainFullUrl(), string);
                            } else {
                                string = iWebviewFindWebview.obtainApp().convert2AbsFullPath(iWebviewFindWebview.obtainFullUrl(), string);
                                if (string.startsWith("/")) {
                                    string = string.substring(1, string.length());
                                }
                            }
                            fileInputStream = WebResUtil.getEncryptionInputStream(string, iWebviewFindWebview.obtainApp());
                        }
                        if (fileInputStream != null) {
                            try {
                                String str3 = new String(IOUtil.toString(fileInputStream));
                                if (!TextUtils.isEmpty(str3)) {
                                    str2 = str2 + str3;
                                }
                            } catch (Exception unused) {
                            }
                        } else if (jSCallback != null) {
                            map.put("type", -1);
                            map.put(NotificationCompat.CATEGORY_MESSAGE, string + DCLoudApplicationImpl.self().getContext().getString(R.string.dcloud_feature_weex_msg_cannot_find_file_by_path));
                            jSCallback.invoke(map);
                        }
                    }
                    if (TextUtils.isEmpty(str2)) {
                        return;
                    }
                    WXBridgeManager.getInstance().syncExecJsOnInstanceWithResult(PlusModule.this.mWXSDKInstance.getInstanceId(), str2, -1);
                    if (jSCallback != null) {
                        map.put("type", 1);
                        jSCallback.invoke(map);
                    }
                }
            }, true);
        } else if (jSCallback != null) {
            map.put("type", -1);
            map.put(NotificationCompat.CATEGORY_MESSAGE, DCLoudApplicationImpl.self().getContext().getString(R.string.dcloud_feature_weex_msg_param_invalid));
            jSCallback.invoke(map);
        }
    }

    @JSMethod(uiThread = true)
    public void exec(String str, String str2) throws JSONException {
        WXSDKInstance wXSDKInstance = this.mWXSDKInstance;
        if (wXSDKInstance == null || !wXSDKInstance.isDestroy()) {
            IWebview iWebviewFindWebview = WeexInstanceMgr.self().findWebview(this.mWXSDKInstance);
            if (iWebviewFindWebview instanceof AdaUniWebView) {
                ((AdaUniWebView) iWebviewFindWebview).prompt(str, str2);
            }
        }
    }

    @JSMethod(uiThread = false)
    public String execSync(String str, String str2) {
        WXSDKInstance wXSDKInstance = this.mWXSDKInstance;
        if (wXSDKInstance != null && wXSDKInstance.isDestroy()) {
            return "";
        }
        IWebview iWebviewFindWebview = WeexInstanceMgr.self().findWebview(this.mWXSDKInstance);
        return iWebviewFindWebview instanceof AdaUniWebView ? ((AdaUniWebView) iWebviewFindWebview).prompt(str, str2) : "";
    }

    @JSMethod(uiThread = false)
    public JSONObject getAppAuthorizeSetting() {
        Context context = this.mWXSDKInstance.getContext();
        JSONObject jSONObject = new JSONObject();
        boolean zCheckPermissions = PermissionUtil.checkPermissions(context, new String[]{Permission.CAMERA});
        String str = IApp.AUTHORITY_AUTHORIZED;
        Object obj = zCheckPermissions ? IApp.AUTHORITY_AUTHORIZED : IApp.AUTHORITY_DENIED;
        if (!zCheckPermissions && !PermissionUtil.hasDefinedInManifest(context, Permission.CAMERA)) {
            obj = "config error";
        }
        jSONObject.put("cameraAuthorized", obj);
        boolean zCheckPermissions2 = PermissionUtil.checkPermissions(context, new String[]{Permission.ACCESS_COARSE_LOCATION});
        Object obj2 = zCheckPermissions2 ? IApp.AUTHORITY_AUTHORIZED : IApp.AUTHORITY_DENIED;
        if (!zCheckPermissions2 && !PermissionUtil.hasDefinedInManifest(context, Permission.ACCESS_COARSE_LOCATION)) {
            obj2 = "config error";
        }
        jSONObject.put("locationAuthorized", obj2);
        boolean zCheckPermissions3 = PermissionUtil.checkPermissions(context, new String[]{Permission.ACCESS_FINE_LOCATION});
        String str2 = zCheckPermissions2 ? "reduced" : "unsupported";
        if (zCheckPermissions2 && zCheckPermissions3) {
            str2 = "full";
        }
        jSONObject.put("locationAccuracy", (Object) str2);
        boolean zCheckPermissions4 = PermissionUtil.checkPermissions(context, new String[]{Permission.RECORD_AUDIO});
        jSONObject.put("microphoneAuthorized", (Object) ((zCheckPermissions4 || PermissionUtil.hasDefinedInManifest(context, Permission.RECORD_AUDIO)) ? zCheckPermissions4 ? IApp.AUTHORITY_AUTHORIZED : IApp.AUTHORITY_DENIED : "config error"));
        if (!NotificationManagerCompat.from(context).areNotificationsEnabled()) {
            str = IApp.AUTHORITY_DENIED;
        }
        jSONObject.put("notificationAuthorized", (Object) str);
        jSONObject.put("albumAuthorized", (Object) Constants.Name.UNDEFINED);
        jSONObject.put("bluetoothAuthorized", (Object) Constants.Name.UNDEFINED);
        jSONObject.put("locationReducedAccuracy", (Object) Constants.Name.UNDEFINED);
        jSONObject.put("notificationAlertAuthorized", (Object) Constants.Name.UNDEFINED);
        jSONObject.put("notificationBadgeAuthorized", (Object) Constants.Name.UNDEFINED);
        jSONObject.put("notificationSoundAuthorized", (Object) Constants.Name.UNDEFINED);
        return jSONObject;
    }

    @JSMethod(uiThread = false)
    public int getAppState() {
        IActivityHandler iActivityHandler;
        IWebview iWebviewFindWebview = WeexInstanceMgr.self().findWebview(this.mWXSDKInstance);
        if (iWebviewFindWebview == null || (iActivityHandler = DCloudAdapterUtil.getIActivityHandler(iWebviewFindWebview.getActivity())) == null) {
            return 0;
        }
        return iActivityHandler.getActivityState();
    }

    @JSMethod(uiThread = false)
    public Object getConfigInfo() {
        IWebview iWebviewFindWebview = WeexInstanceMgr.self().findWebview(this.mWXSDKInstance);
        if (iWebviewFindWebview instanceof AdaUniWebView) {
            return ((AdaUniWebView) iWebviewFindWebview).getConfigInfo();
        }
        return null;
    }

    @JSMethod(uiThread = false)
    public JSONObject getDotData() {
        JSONObject deviceInfo = WXDotDataUtil.getDeviceInfo();
        if (BaseInfo.SyncDebug) {
            deviceInfo.put("maxMemory", (Object) ((Runtime.getRuntime().maxMemory() / 1048576) + "M"));
            deviceInfo.put("totalMemory", (Object) ((Runtime.getRuntime().totalMemory() / 1048576) + "M"));
            deviceInfo.put("appRuningTitme", (Object) Long.valueOf(BaseInfo.splashCloseTime - BaseInfo.startTime));
        }
        return deviceInfo;
    }

    @JSMethod
    public void getHostInfo(JSCallback jSCallback) {
        JSONObject object;
        if (jSCallback == null) {
            return;
        }
        JSONObject jSONObject = new JSONObject();
        IWebview iWebviewFindWebview = WeexInstanceMgr.self().findWebview(this.mWXSDKInstance);
        if (iWebviewFindWebview == null || iWebviewFindWebview.obtainApp() == null) {
            jSCallback.invoke(jSONObject);
            return;
        }
        String strObtainAppInfo = iWebviewFindWebview.obtainApp().obtainAppInfo();
        if (PdrUtil.isEmpty(strObtainAppInfo)) {
            jSCallback.invoke(jSONObject);
            return;
        }
        if (PdrUtil.isUniMPHostForUniApp()) {
            if (!SDK.isUniMP) {
                JSONObject object2 = JSON.parseObject(strObtainAppInfo);
                if (object2 != null) {
                    jSONObject.putAll(object2);
                }
            } else if (!PdrUtil.isEmpty(SDK.mHostInfo) && (object = JSON.parseObject(SDK.mHostInfo)) != null) {
                jSONObject.putAll(object);
            }
        }
        jSONObject.put("nativeName", (Object) AndroidResources.appName);
        jSONObject.put("nativeAppid", (Object) AndroidResources.packageName);
        jSONObject.put("nativeVersionName", (Object) AndroidResources.versionName);
        jSONObject.put("nativeVersionCode", (Object) Integer.valueOf(AndroidResources.versionCode));
        jSCallback.invoke(jSONObject);
    }

    @JSMethod(uiThread = false)
    public String getLanguage() {
        WXSDKInstance wXSDKInstance = this.mWXSDKInstance;
        return (wXSDKInstance == null || wXSDKInstance.getContext() == null) ? "" : LanguageUtil.getCurrentLocaleLanguage(this.mWXSDKInstance.getContext());
    }

    @JSMethod(uiThread = false)
    public Object getRedirectInfo() {
        JSONObject object;
        IWebview iWebviewFindWebview = WeexInstanceMgr.self().findWebview(this.mWXSDKInstance);
        JSONObject jSONObject = null;
        if (iWebviewFindWebview != null) {
            if (Boolean.valueOf(iWebviewFindWebview.obtainApp().obtainConfigProperty(IApp.ConfigProperty.UNI_RESTART_TO_DIRECT)).booleanValue() && (object = JSON.parseObject(iWebviewFindWebview.obtainApp().obtainConfigProperty(AbsoluteConst.JSON_KEY_DEBUG_REFRESH))) != null && object.containsKey("arguments")) {
                try {
                    jSONObject = JSON.parseObject(object.getString("arguments"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                iWebviewFindWebview.obtainApp().setConfigProperty(AbsoluteConst.JSON_KEY_DEBUG_REFRESH, "");
            }
            JSONObject object2 = JSON.parseObject(iWebviewFindWebview.obtainApp().obtainConfigProperty(IntentConst.UNIMP_RUN_EXTRA_INFO));
            if (object2 != null) {
                if (jSONObject == null) {
                    jSONObject = new JSONObject();
                }
                jSONObject.putAll(object2);
                iWebviewFindWebview.obtainApp().setConfigProperty(IntentConst.UNIMP_RUN_EXTRA_INFO, "");
            }
        }
        return jSONObject;
    }

    @JSMethod(uiThread = true)
    public void getSystemInfo(UniJSCallback uniJSCallback) {
        uniJSCallback.invoke(getSystemInfoSync());
    }

    @JSMethod(uiThread = false)
    public JSONObject getSystemInfoSync() {
        JSONObject jSONObject = new JSONObject();
        try {
            IWebview iWebviewFindWebview = WeexInstanceMgr.self().findWebview(this.mWXSDKInstance);
            if (iWebviewFindWebview != null && iWebviewFindWebview.obtainApp() != null) {
                jSONObject = JSON.parseObject(iWebviewFindWebview.obtainApp().getSystemInfo());
                boolean zBooleanValue = DeviceInfo.isSystemNightMode(iWebviewFindWebview.getActivity()).booleanValue();
                String str = DCBlurDraweeView.DARK;
                jSONObject.put("osTheme", zBooleanValue ? DCBlurDraweeView.DARK : DCBlurDraweeView.LIGHT);
                jSONObject.put("ua", (Object) iWebviewFindWebview.getWebviewProperty(IWebview.USER_AGENT));
                String str2 = DeviceInfo.oaids;
                if (str2 != null) {
                    String[] strArrSplit = str2.split("\\|");
                    jSONObject.put("oaid", (Object) (strArrSplit.length > 0 ? strArrSplit[0] : ""));
                } else {
                    jSONObject.put("oaid", (Object) "");
                }
                jSONObject.put("browserVersion", (Object) WebViewFactory.getWebViewUserAgentVersion(iWebviewFindWebview.getContext()));
                jSONObject.put("deviceOrientation", (Object) (iWebviewFindWebview.getActivity().getResources().getConfiguration().orientation == 2 ? "landscape" : "portrait"));
                jSONObject.put("deviceId", (Object) TelephonyUtil.getDCloudDeviceID(iWebviewFindWebview.getActivity()));
                if (SDK.isUniMP) {
                    jSONObject.put("hostPackageName", (Object) iWebviewFindWebview.getContext().getPackageName());
                    jSONObject.put("hostVersion", (Object) AndroidResources.versionName);
                    jSONObject.put("hostName", (Object) AndroidResources.appName);
                    if (!PdrUtil.isEquals("auto", SDK.hostAppThemeDark)) {
                        str = SDK.hostAppThemeDark;
                    } else if (!DeviceInfo.isSystemNightMode(iWebviewFindWebview.getContext()).booleanValue()) {
                        str = DCBlurDraweeView.LIGHT;
                    }
                    jSONObject.put("hostTheme", (Object) str);
                    if (PdrUtil.isUniMPHostForUniApp()) {
                        jSONObject.put("hostLanguage", (Object) LanguageUtil.getDeviceDefLocalLanguage());
                        boolean z = SDK.isUniMP;
                        return jSONObject;
                    }
                    jSONObject.put("hostLanguage", (Object) LanguageUtil.getDeviceDefLocalLanguage());
                }
            }
        } catch (Exception unused) {
        }
        return jSONObject;
    }

    @JSMethod(uiThread = false)
    public JSONObject getSystemSetting() {
        Context context = this.mWXSDKInstance.getContext();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("bluetoothEnabled", (Object) Boolean.valueOf(DeviceInfo.blueToothEnable(context)));
        } catch (Exception e) {
            e.printStackTrace();
            jSONObject.put("bluetoothError", (Object) "Missing permissions required by BluetoothAdapter.isEnabled: android.permission.BLUETOOTH");
        }
        jSONObject.put("locationEnabled", (Object) Boolean.valueOf(DeviceInfo.locationEnable(context)));
        try {
            jSONObject.put("wifiEnabled", (Object) Boolean.valueOf(DeviceInfo.wifiEnable(context)));
        } catch (Exception unused) {
            jSONObject.put("wifiError", (Object) "Missing permissions required by WifiManager.isWifiEnabled: android.permission.ACCESS_WIFI_STATE");
        }
        jSONObject.put("deviceOrientation", (Object) DeviceInfo.deviceOrientation(context));
        return jSONObject;
    }

    @JSMethod(uiThread = false)
    public String getValue(String str) {
        return ((DCWXInput) WXSDKManager.getInstance().getWXRenderManager().getWXComponent(this.mWXSDKInstance.getInstanceId(), str)).getValue();
    }

    @JSMethod(uiThread = false)
    public void hasClientKey(JSONObject jSONObject, JSCallback jSCallback) {
        if (SDK.isUniMPSDK() || BaseInfo.isStandardBase(this.mWXSDKInstance.getContext())) {
            jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.NOT_SUPPORT_MP_OR_BASE));
            return;
        }
        String str = (String) jSONObject.get("spaceId");
        String str2 = (String) jSONObject.get("provider");
        if (PdrUtil.isEmpty(str) || PdrUtil.isEmpty(str2)) {
            jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.PARAMS_IS_NULL));
        } else {
            jSCallback.invoke(Result.boxSuccessResult(Boolean.valueOf(!PdrUtil.isEmpty(getClientKey(str, str2)))));
        }
    }

    @JSMethod(uiThread = true)
    public void log(String str) {
        Logger.d("console", "[LOG] " + str);
    }

    @JSMethod(uiThread = false)
    public void openAppAuthorizeSetting(JSCallback jSCallback) {
        JSONObject jSONObject = new JSONObject();
        try {
            Context context = this.mWXSDKInstance.getContext();
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
            context.startActivity(intent);
            jSONObject.put("type", (Object) WXImage.SUCCEED);
            jSONObject.put("code", (Object) 0);
        } catch (Exception e) {
            e.printStackTrace();
            jSONObject.put("type", (Object) Constants.Event.FAIL);
        }
        jSCallback.invoke(jSONObject);
    }

    @JSMethod
    public void postMessage(String str, String str2) {
        WXServiceWrapper wXServiceWrapperFindWXServiceWrapper;
        IWebview iWebviewFindWebview = WeexInstanceMgr.self().findWebview(this.mWXSDKInstance);
        String str3 = StringUtil.format(this.EVENTS_DOCUMENT_EXECUTE, "uniNViewMessage", "", iWebviewFindWebview != null ? iWebviewFindWebview.obtainFrameId() : "", str);
        if (iWebviewFindWebview == null && (wXServiceWrapperFindWXServiceWrapper = WeexInstanceMgr.self().findWXServiceWrapper(this.mWXSDKInstance)) != null && !BaseInfo.isWeexUniJs(wXServiceWrapperFindWXServiceWrapper.obtanApp())) {
            wXServiceWrapperFindWXServiceWrapper.findWebViewToLoadUrL(str3, str2);
            return;
        }
        if (iWebviewFindWebview != null) {
            IWebview iWebviewFindWebview2 = PdrUtil.isEmpty(str2) ? iWebviewFindWebview : WeexInstanceMgr.self().findWebview(iWebviewFindWebview, iWebviewFindWebview.obtainApp(), iWebviewFindWebview.obtainApp().obtainAppId(), str2);
            if (iWebviewFindWebview2 != null) {
                if (!(iWebviewFindWebview2 instanceof AdaUniWebView)) {
                    iWebviewFindWebview2.loadUrl(str3);
                    return;
                }
                HashMap map = new HashMap();
                map.put("targetId", str2);
                map.put("originId", iWebviewFindWebview.obtainFrameId());
                try {
                    map.put("data", JSONObject.parseObject(str));
                } catch (Exception unused) {
                    map.put("data", str);
                }
                ((AdaUniWebView) iWebviewFindWebview2).fireEvent(new EventActionInfo("uniNViewMessage", map));
            }
        }
    }

    @JSMethod
    public void preloadReady(String str) {
        ICallBack preUniMPCallBack = WeexInstanceMgr.self().getPreUniMPCallBack(str);
        if (preUniMPCallBack != null) {
            preUniMPCallBack.onCallBack(1, str);
        }
    }

    @JSMethod(uiThread = false)
    public JSONObject pushDebugData(Object obj) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("data", obj);
        return jSONObject;
    }

    @JSMethod(uiThread = true)
    public void sendNativeEvent(String str, Object obj, JSCallback jSCallback) {
        IWebview iWebviewFindWebview = WeexInstanceMgr.self().findWebview(this.mWXSDKInstance);
        if (iWebviewFindWebview == null || iWebviewFindWebview.getActivity() == null || !(iWebviewFindWebview.getActivity() instanceof IActivityHandler)) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("event", str);
        bundle.putString("dataType", "String");
        if (obj instanceof String) {
            bundle.putString("data", String.valueOf(obj));
        } else if (obj instanceof JSON) {
            bundle.putString("data", ((JSON) obj).toJSONString());
            bundle.putString("dataType", "JSON");
        }
        if (jSCallback instanceof SimpleJSCallback) {
            bundle.putString("instanceId", this.mWXSDKInstance.getInstanceId());
            bundle.putString(SDK.UNIMP_EVENT_CALLBACKID, ((SimpleJSCallback) jSCallback).getCallbackId());
        }
        ((IActivityHandler) iWebviewFindWebview.getActivity()).callBack(SDK.UNIMP_JS_TO_NATIVE, bundle);
    }

    @JSMethod(uiThread = false)
    public void setClientKey(JSONObject jSONObject, JSCallback jSCallback) {
        if (SDK.isUniMPSDK() || BaseInfo.isStandardBase(this.mWXSDKInstance.getContext())) {
            jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.NOT_SUPPORT_MP_OR_BASE));
            return;
        }
        if (jSONObject == null) {
            if (jSCallback != null) {
                jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.PARAMS_IS_NULL));
                return;
            }
            return;
        }
        String metaValue = AndroidResources.getMetaValue("dcloud_sn_appkey");
        String jSONString = jSONObject.toJSONString();
        String str = (String) jSONObject.get("spaceId");
        String str2 = (String) jSONObject.get("provider");
        if (PdrUtil.isEmpty(str) || PdrUtil.isEmpty(str2)) {
            jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.PARAMS_IS_NULL));
            return;
        }
        if (PdrUtil.isEmpty(metaValue)) {
            jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.APP_KEY_IS_NULL));
            return;
        }
        if (!WXBridgeManager.getInstance().verifyClientKeyPayload(BaseInfo.sDefaultBootApp, metaValue, jSONString)) {
            jSCallback.invoke(Result.boxFailResult(SecureNetworkResult.CLIENT_KEY_ILLEGAL));
            return;
        }
        SharedPreferences.Editor editorEdit = this.mWXSDKInstance.getContext().getSharedPreferences(HELP_LOG_HASH + BaseInfo.sDefaultBootApp + str2 + str, 0).edit();
        editorEdit.putString("HELP_LOG_HASH", jSONString);
        editorEdit.commit();
        jSCallback.invoke(Result.boxSuccessResult(Boolean.TRUE));
    }

    @JSMethod(uiThread = false)
    public void setDefaultFontSize(String str) {
        int iIntValue = Integer.valueOf(str).intValue();
        if (iIntValue > 0) {
            this.mWXSDKInstance.setDefaultFontSize(iIntValue);
        }
    }

    @JSMethod(uiThread = false)
    public void setLanguage(String str) {
        WXSDKInstance wXSDKInstance = this.mWXSDKInstance;
        if (wXSDKInstance == null || wXSDKInstance.getContext() == null || SDK.isUniMPSDK() || this.mWXSDKInstance.isDestroy()) {
            return;
        }
        if ("auto".equalsIgnoreCase(str)) {
            str = "";
        }
        LanguageUtil.updateLanguage(this.mWXSDKInstance.getContext(), str);
        LocalBroadcastManager.getInstance(this.mWXSDKInstance.getContext()).sendBroadcast(new Intent(LanguageUtil.LanguageBroadCastIntent));
    }

    @JSMethod(uiThread = true)
    public void uniReady() {
        WXBaseWrapper wXBaseWrapperFindWXBaseWrapper = WeexInstanceMgr.self().findWXBaseWrapper(this.mWXSDKInstance);
        if (wXBaseWrapperFindWXBaseWrapper != null) {
            wXBaseWrapperFindWXBaseWrapper.onReady();
        }
    }
}
