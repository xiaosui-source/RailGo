package com.taobao.weex.http;

import android.net.Uri;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXHttpAdapter;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.common.WXRequest;
import com.taobao.weex.common.WXResponse;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.http.Options;
import com.taobao.weex.ui.component.WXBasicComponentType;
import com.taobao.weex.utils.WXLogUtils;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.util.net.NetWork;
import io.dcloud.feature.uniapp.adapter.AbsURIAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class WXStreamModule extends WXModule {
    public static final String STATUS = "status";
    public static final String STATUS_TEXT = "statusText";
    final IWXHttpAdapter mAdapter;
    static final Pattern CHARSET_PATTERN = Pattern.compile("charset=([a-z0-9-]+)");
    public static HashMap<String, CertDTO> certMap = new HashMap<>();

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    private interface ResponseCallback {
        void onResponse(WXResponse wXResponse, Map<String, String> map);
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    private static class StreamHttpListener implements IWXHttpAdapter.OnHttpListener {
        private ResponseCallback mCallback;
        private JSCallback mProgressCallback;
        private Map<String, String> mRespHeaders;
        private Map<String, Object> mResponse;

        @Override // com.taobao.weex.adapter.IWXHttpAdapter.OnHttpListener
        public void onHeadersReceived(int i, Map<String, List<String>> map) {
            this.mResponse.put("readyState", 2);
            this.mResponse.put("status", Integer.valueOf(i));
            HashMap map2 = new HashMap();
            if (map != null) {
                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                    if (entry.getValue().size() != 0) {
                        if (entry.getValue().size() == 1) {
                            map2.put(entry.getKey() != null ? entry.getKey() : "_", entry.getValue().get(0));
                        } else {
                            map2.put(entry.getKey() != null ? entry.getKey() : "_", entry.getValue().toString());
                        }
                    }
                }
            }
            this.mResponse.put("headers", map2);
            this.mRespHeaders = map2;
            JSCallback jSCallback = this.mProgressCallback;
            if (jSCallback != null) {
                jSCallback.invokeAndKeepAlive(new HashMap(this.mResponse));
            }
        }

        @Override // com.taobao.weex.adapter.IWXHttpAdapter.OnHttpListener
        public void onHttpFinish(WXResponse wXResponse) {
            byte[] bArr;
            ResponseCallback responseCallback = this.mCallback;
            if (responseCallback != null) {
                responseCallback.onResponse(wXResponse, this.mRespHeaders);
            }
            if (WXEnvironment.isApkDebugable()) {
                WXLogUtils.d("WXStreamModule", (wXResponse == null || (bArr = wXResponse.originalData) == null) ? "response data is NUll!" : new String(bArr));
            }
        }

        @Override // com.taobao.weex.adapter.IWXHttpAdapter.OnHttpListener
        public void onHttpResponseProgress(int i) {
            this.mResponse.put("length", Integer.valueOf(i));
            JSCallback jSCallback = this.mProgressCallback;
            if (jSCallback != null) {
                jSCallback.invokeAndKeepAlive(new HashMap(this.mResponse));
            }
        }

        @Override // com.taobao.weex.adapter.IWXHttpAdapter.OnHttpListener
        public void onHttpStart() {
            if (this.mProgressCallback != null) {
                this.mResponse.put("readyState", 1);
                this.mResponse.put("length", 0);
                this.mProgressCallback.invokeAndKeepAlive(new HashMap(this.mResponse));
            }
        }

        @Override // com.taobao.weex.adapter.IWXHttpAdapter.OnHttpListener
        public void onHttpUploadProgress(int i) {
        }

        private StreamHttpListener(ResponseCallback responseCallback, JSCallback jSCallback) {
            this.mResponse = new HashMap();
            this.mCallback = responseCallback;
            this.mProgressCallback = jSCallback;
        }
    }

    public WXStreamModule() {
        this(null);
    }

    private void extractHeaders(JSONObject jSONObject, Options.Builder builder) {
        String strAssembleUserAgent = WXHttpUtil.assembleUserAgent(WXEnvironment.getApplication(), WXEnvironment.getConfig());
        if (jSONObject != null) {
            for (String str : jSONObject.keySet()) {
                if (str.equals(WXHttpUtil.KEY_USER_AGENT)) {
                    strAssembleUserAgent = jSONObject.getString(str);
                } else {
                    builder.putHeader(str, jSONObject.getString(str));
                }
            }
        }
        builder.putHeader(WXHttpUtil.KEY_USER_AGENT, strAssembleUserAgent);
    }

    static String getHeader(Map<String, String> map, String str) {
        if (map == null || str == null) {
            return null;
        }
        return map.containsKey(str) ? map.get(str) : map.get(str.toLowerCase(Locale.ENGLISH));
    }

    private void sendRequest(Options options, ResponseCallback responseCallback, JSCallback jSCallback, String str, String str2) {
        WXRequest wXRequest = new WXRequest();
        wXRequest.method = options.getMethod();
        wXRequest.url = WXSDKManager.getInstance().getURIAdapter().rewrite(str2, AbsURIAdapter.REQUEST, Uri.parse(options.getUrl())).toString();
        wXRequest.body = options.getBody();
        wXRequest.timeoutMs = options.getTimeout();
        wXRequest.instanceId = str;
        wXRequest.sslVerify = options.getSslVerify();
        wXRequest.isFirstIpv4 = options.isFirstIpv4();
        wXRequest.tls = options.getTlsConfig();
        if ("BASE64".equalsIgnoreCase(options.getInputType())) {
            wXRequest.inputType = "BASE64";
        } else {
            wXRequest.inputType = "";
        }
        if (options.getHeaders() != null) {
            Map<String, String> map = wXRequest.paramMap;
            if (map == null) {
                wXRequest.paramMap = options.getHeaders();
            } else {
                map.putAll(options.getHeaders());
            }
        }
        IWXHttpAdapter iWXHttpAdapter = this.mAdapter;
        if (iWXHttpAdapter == null) {
            iWXHttpAdapter = WXSDKManager.getInstance().getIWXHttpAdapter();
        }
        if (iWXHttpAdapter != null) {
            iWXHttpAdapter.sendRequest(wXRequest, new StreamHttpListener(responseCallback, jSCallback));
        } else {
            WXLogUtils.e("WXStreamModule", "No HttpAdapter found,request failed.");
        }
    }

    @JSMethod(uiThread = false)
    public void configMTLS(JSONArray jSONArray, JSCallback jSCallback) {
        if (jSCallback == null) {
            return;
        }
        if (jSONArray == null || jSONArray.isEmpty()) {
            jSCallback.invoke(CertJSResponse.obtainFail(-1, DOMException.MSG_PARAMETER_ERROR));
            return;
        }
        certMap.clear();
        for (int i = 0; i < jSONArray.size(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            if (jSONObject.containsKey("host")) {
                CertDTO certDTO = new CertDTO();
                String string = jSONObject.getString("host");
                certDTO.host = string;
                certDTO.client = jSONObject.getString("client");
                certDTO.clientPassword = jSONObject.getString("clientPassword");
                certDTO.server = (String[]) jSONObject.getJSONArray("server").toArray(new String[0]);
                certMap.put(string, certDTO);
            }
        }
        jSCallback.invoke(CertJSResponse.obtainSuccess());
    }

    @JSMethod(uiThread = false)
    public void fetch(JSONObject jSONObject, JSCallback jSCallback, JSCallback jSCallback2) {
        fetch(jSONObject, jSCallback, jSCallback2, this.mWXSDKInstance.getInstanceId(), this.mWXSDKInstance.getBundleUrl());
    }

    @JSMethod(uiThread = false)
    public void fetchWithArrayBuffer(JSONObject jSONObject, JSONObject jSONObject2, JSCallback jSCallback, JSCallback jSCallback2) {
        if (jSONObject == null) {
            if (jSCallback != null) {
                HashMap map = new HashMap();
                map.put("ok", Boolean.FALSE);
                map.put(STATUS_TEXT, Status.ERR_INVALID_REQUEST);
                jSCallback.invoke(map);
                return;
            }
            return;
        }
        String string = jSONObject.getString("@type");
        if (TextUtils.isEmpty(string) || !"binary".equalsIgnoreCase(string)) {
            if (jSCallback != null) {
                HashMap map2 = new HashMap();
                map2.put("ok", Boolean.FALSE);
                map2.put(STATUS_TEXT, Status.ERR_INVALID_REQUEST);
                jSCallback.invoke(map2);
                return;
            }
            return;
        }
        String string2 = jSONObject.getString("base64");
        if (!TextUtils.isEmpty(string2)) {
            jSONObject2.put("inputType", "base64");
            jSONObject2.put("body", (Object) string2);
            fetch(jSONObject2, jSCallback, jSCallback2, this.mWXSDKInstance.getInstanceId(), this.mWXSDKInstance.getBundleUrl());
        } else if (jSCallback != null) {
            HashMap map3 = new HashMap();
            map3.put("ok", Boolean.FALSE);
            map3.put(STATUS_TEXT, Status.ERR_INVALID_REQUEST);
            jSCallback.invoke(map3);
        }
    }

    Object parseData(String str, Options.Type type) {
        if (type == Options.Type.json) {
            return JSONObject.parse(str);
        }
        if (type != Options.Type.jsonp) {
            return str;
        }
        if (str == null || str.isEmpty()) {
            return new JSONObject();
        }
        int iIndexOf = str.indexOf(Operators.BRACKET_START_STR) + 1;
        int iLastIndexOf = str.lastIndexOf(Operators.BRACKET_END_STR);
        return (iIndexOf == 0 || iIndexOf >= iLastIndexOf || iLastIndexOf <= 0) ? new JSONObject() : JSONObject.parse(str.substring(iIndexOf, iLastIndexOf));
    }

    @JSMethod(uiThread = false)
    @Deprecated
    public void sendHttp(JSONObject jSONObject, final String str) {
        String string = jSONObject.getString("method");
        String string2 = jSONObject.getString("url");
        JSONObject jSONObject2 = jSONObject.getJSONObject(WXBasicComponentType.HEADER);
        String string3 = jSONObject.getString("body");
        int intValue = jSONObject.getIntValue("timeout");
        boolean booleanValue = jSONObject.getBooleanValue("sslVerify");
        boolean booleanValue2 = jSONObject.getBooleanValue("firstIpv4");
        JSONObject jSONObject3 = jSONObject.getJSONObject("tls");
        if (string != null) {
            string = string.toUpperCase();
        }
        Options.Builder builder = new Options.Builder();
        if (!"GET".equals(string) && !"POST".equals(string) && !"PUT".equals(string) && !"DELETE".equals(string) && !"HEAD".equals(string) && !"PATCH".equals(string)) {
            string = "GET";
        }
        Options.Builder firstIpv4 = builder.setMethod(string).setUrl(string2).setBody(string3).setTimeout(intValue).setSslVerify(booleanValue).setAndroidTlsConfig(jSONObject3).setFirstIpv4(booleanValue2);
        extractHeaders(jSONObject2, firstIpv4);
        final Options optionsCreateOptions = firstIpv4.createOptions();
        sendRequest(firstIpv4.createOptions(), new ResponseCallback() { // from class: com.taobao.weex.http.WXStreamModule.1
            @Override // com.taobao.weex.http.WXStreamModule.ResponseCallback
            public void onResponse(WXResponse wXResponse, Map<String, String> map) {
                String asString;
                byte[] bArr;
                if (str == null || WXStreamModule.this.mWXSDKInstance == null) {
                    return;
                }
                WXBridgeManager wXBridgeManager = WXBridgeManager.getInstance();
                String instanceId = WXStreamModule.this.mWXSDKInstance.getInstanceId();
                String str2 = str;
                if (wXResponse == null || (bArr = wXResponse.originalData) == null) {
                    asString = "{}";
                } else {
                    asString = WXStreamModule.readAsString(bArr, map != null ? WXStreamModule.getHeader(map, NetWork.CONTENT_TYPE) : "", optionsCreateOptions.getType());
                }
                wXBridgeManager.callback(instanceId, str2, asString);
            }
        }, null, this.mWXSDKInstance.getInstanceId(), this.mWXSDKInstance.getBundleUrl());
    }

    public WXStreamModule(IWXHttpAdapter iWXHttpAdapter) {
        this.mAdapter = iWXHttpAdapter;
    }

    public void fetch(JSONObject jSONObject, final JSCallback jSCallback, JSCallback jSCallback2, String str, String str2) {
        if (jSONObject == null || jSONObject.getString("url") == null) {
            if (jSCallback != null) {
                HashMap map = new HashMap();
                map.put("ok", Boolean.FALSE);
                map.put(STATUS_TEXT, Status.ERR_INVALID_REQUEST);
                jSCallback.invoke(map);
                return;
            }
            return;
        }
        String string = jSONObject.getString("method");
        String string2 = jSONObject.getString("url");
        JSONObject jSONObject2 = jSONObject.getJSONObject("headers");
        String string3 = jSONObject.getString("body");
        String string4 = jSONObject.getString("type");
        String string5 = jSONObject.getString("inputType");
        int intValue = jSONObject.getIntValue("timeout");
        JSONObject jSONObject3 = jSONObject.getJSONObject("tls");
        boolean booleanValue = jSONObject.getBooleanValue("sslVerify");
        boolean booleanValue2 = jSONObject.getBooleanValue("firstIpv4");
        WXSDKInstance sDKInstance = WXSDKManager.getInstance().getSDKInstance(str);
        if (sDKInstance != null && sDKInstance.getStreamNetworkHandler() != null) {
            String strFetchLocal = sDKInstance.getStreamNetworkHandler().fetchLocal(string2);
            if (!TextUtils.isEmpty(strFetchLocal)) {
                string2 = strFetchLocal;
            }
        }
        if (string != null) {
            string = string.toUpperCase();
        }
        Options.Builder builder = new Options.Builder();
        if (!"GET".equals(string) && !"POST".equals(string) && !"PUT".equals(string) && !"DELETE".equals(string) && !"HEAD".equals(string) && !"PATCH".equals(string)) {
            string = "GET";
        }
        Options.Builder firstIpv4 = builder.setMethod(string).setUrl(string2).setBody(string3).setType(string4).setInputTypes(string5).setTimeout(intValue).setSslVerify(booleanValue).setAndroidTlsConfig(jSONObject3).setFirstIpv4(booleanValue2);
        extractHeaders(jSONObject2, firstIpv4);
        final Options optionsCreateOptions = firstIpv4.createOptions();
        sendRequest(optionsCreateOptions, new ResponseCallback() { // from class: com.taobao.weex.http.WXStreamModule.2
            @Override // com.taobao.weex.http.WXStreamModule.ResponseCallback
            public void onResponse(WXResponse wXResponse, Map<String, String> map2) throws NumberFormatException {
                if (jSCallback != null) {
                    HashMap map3 = new HashMap();
                    if (wXResponse == null || "-1".equals(wXResponse.statusCode)) {
                        map3.put("status", -1);
                        map3.put(WXStreamModule.STATUS_TEXT, Status.ERR_CONNECT_FAILED);
                        if (wXResponse != null) {
                            map3.put("errorMsg", wXResponse.errorMsg);
                        } else {
                            map3.put("errorMsg", "response 为空");
                        }
                    } else {
                        int i = Integer.parseInt(wXResponse.statusCode);
                        map3.put("status", Integer.valueOf(i));
                        map3.put("ok", Boolean.valueOf(i >= 200 && i <= 299));
                        byte[] bArr = wXResponse.originalData;
                        if (bArr == null) {
                            map3.put("data", wXResponse.errorMsg);
                        } else {
                            try {
                                map3.put("data", WXStreamModule.this.parseData(WXStreamModule.readAsString(bArr, map2 != null ? WXStreamModule.getHeader(map2, NetWork.CONTENT_TYPE) : "", optionsCreateOptions.getType()), optionsCreateOptions.getType()));
                            } catch (JSONException e) {
                                WXLogUtils.e("", e);
                                map3.put("ok", Boolean.FALSE);
                                map3.put("data", "{'err':'Data parse failed!'}");
                            }
                        }
                        map3.put(WXStreamModule.STATUS_TEXT, Status.getStatusText(wXResponse.statusCode));
                    }
                    map3.put("headers", map2);
                    jSCallback.invoke(map3);
                }
            }
        }, jSCallback2, str, str2);
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x001a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static java.lang.String readAsString(byte[] r2, java.lang.String r3, com.taobao.weex.http.Options.Type r4) {
        /*
            if (r3 == 0) goto L1a
            java.util.regex.Pattern r0 = com.taobao.weex.http.WXStreamModule.CHARSET_PATTERN
            java.util.Locale r1 = java.util.Locale.ENGLISH
            java.lang.String r3 = r3.toLowerCase(r1)
            java.util.regex.Matcher r3 = r0.matcher(r3)
            boolean r0 = r3.find()
            if (r0 == 0) goto L1a
            r0 = 1
            java.lang.String r3 = r3.group(r0)
            goto L1d
        L1a:
            java.lang.String r3 = "utf-8"
        L1d:
            com.taobao.weex.http.Options$Type r0 = com.taobao.weex.http.Options.Type.base64     // Catch: java.lang.Exception -> L2d
            if (r4 != r0) goto L27
            r3 = 2
            java.lang.String r2 = android.util.Base64.encodeToString(r2, r3)     // Catch: java.lang.Exception -> L2d
            return r2
        L27:
            java.lang.String r4 = new java.lang.String     // Catch: java.lang.Exception -> L2d
            r4.<init>(r2, r3)     // Catch: java.lang.Exception -> L2d
            return r4
        L2d:
            r3 = move-exception
            java.lang.String r4 = ""
            com.taobao.weex.utils.WXLogUtils.e(r4, r3)
            java.lang.String r3 = new java.lang.String
            r3.<init>(r2)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.weex.http.WXStreamModule.readAsString(byte[], java.lang.String, com.taobao.weex.http.Options$Type):java.lang.String");
    }
}
