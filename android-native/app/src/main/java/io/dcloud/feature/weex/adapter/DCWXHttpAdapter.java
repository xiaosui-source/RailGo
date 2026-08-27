package io.dcloud.feature.weex.adapter;

import android.net.Uri;
import android.util.Base64;
import com.alibaba.fastjson.JSONArray;
import com.facebook.imagepipeline.producers.HttpUrlConnectionNetworkFetcher;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXHttpAdapter;
import com.taobao.weex.common.WXRequest;
import com.taobao.weex.common.WXResponse;
import com.taobao.weex.http.CertDTO;
import com.taobao.weex.http.WXStreamModule;
import dc.squareup.okhttp3.ConnectionPool;
import dc.squareup.okhttp3.MediaType;
import dc.squareup.okhttp3.OkHttpClient;
import dc.squareup.okhttp3.Protocol;
import dc.squareup.okhttp3.Request;
import dc.squareup.okhttp3.RequestBody;
import dc.squareup.okhttp3.Response;
import dc.squareup.okhttp3.internal.tls.OkHostnameVerifier;
import io.dcloud.common.adapter.util.DCOKDns;
import io.dcloud.common.adapter.util.DCloudTrustManager;
import io.dcloud.common.util.net.NetWork;
import io.dcloud.feature.weex.config.AndroidTlsConfig;
import io.dcloud.feature.weex.config.UserCustomTrustManager;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class DCWXHttpAdapter implements IWXHttpAdapter {
    private static ConnectionPool mConnectPool;
    private static SSLSocketFactory sslSocketFactory;
    private ExecutorService mExecutorService;

    private void execute(Runnable runnable) {
        if (this.mExecutorService == null) {
            this.mExecutorService = Executors.newFixedThreadPool(10);
        }
        this.mExecutorService.execute(runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String readInputStream(InputStream inputStream, IWXHttpAdapter.OnHttpListener onHttpListener) throws IOException {
        if (inputStream == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        char[] cArr = new char[2048];
        while (true) {
            int i = bufferedReader.read(cArr);
            if (i == -1) {
                bufferedReader.close();
                return sb.toString();
            }
            sb.append(cArr, 0, i);
            if (onHttpListener != null) {
                onHttpListener.onHttpResponseProgress(sb.length());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] readInputStreamAsBytes(InputStream inputStream, IWXHttpAdapter.OnHttpListener onHttpListener) throws IOException {
        if (inputStream == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[2048];
        int i = 0;
        while (true) {
            int i2 = inputStream.read(bArr, 0, 2048);
            if (i2 == -1) {
                byteArrayOutputStream.flush();
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, i2);
            i += i2;
            if (onHttpListener != null) {
                onHttpListener.onHttpResponseProgress(i);
            }
        }
    }

    public X509HostnameVerifier getHostnameVerifier(boolean z) {
        return !z ? org.apache.http.conn.ssl.SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER : org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
    }

    public OkHttpClient getImageOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        long j = HttpUrlConnectionNetworkFetcher.HTTP_DEFAULT_TIMEOUT;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        builder.connectTimeout(j, timeUnit).readTimeout(j, timeUnit).writeTimeout(j, timeUnit).callTimeout(j, timeUnit).protocols(Collections.singletonList(Protocol.HTTP_1_1));
        return builder.build();
    }

    public Request getOKRequest(OkHttpClient.Builder builder, WXRequest wXRequest, IWXHttpAdapter.OnHttpListener onHttpListener) {
        long j = wXRequest.timeoutMs;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        builder.connectTimeout(j, timeUnit).readTimeout(wXRequest.timeoutMs, timeUnit).writeTimeout(wXRequest.timeoutMs, timeUnit).callTimeout(wXRequest.timeoutMs, timeUnit).protocols(Collections.singletonList(Protocol.HTTP_1_1));
        if (wXRequest.isFirstIpv4) {
            builder.dns(new DCOKDns());
        }
        String host = Uri.parse(wXRequest.url).getHost();
        if (wXRequest.tls != null) {
            AndroidTlsConfig androidTlsConfig = new AndroidTlsConfig();
            androidTlsConfig.setKeystore(wXRequest.tls.getString("keystore"));
            androidTlsConfig.setStorePass(wXRequest.tls.getString("storePass"));
            JSONArray jSONArray = wXRequest.tls.getJSONArray("ca");
            androidTlsConfig.setCa(jSONArray != null ? (String[]) jSONArray.toArray(new String[0]) : null);
            builder.sslSocketFactory(UserCustomTrustManager.getSSLSocketFactory(androidTlsConfig, WXSDKManager.getInstance().getAllInstanceMap().get(wXRequest.instanceId)));
            builder.hostnameVerifier(OkHostnameVerifier.INSTANCE);
        } else {
            HashMap<String, CertDTO> map = WXStreamModule.certMap;
            if (map != null && map.containsKey(host)) {
                builder.sslSocketFactory(UserCustomTrustManager.getSSLSocketFactory(WXStreamModule.certMap.get(host), WXSDKManager.getInstance().getAllInstanceMap().get(wXRequest.instanceId)));
                builder.hostnameVerifier(OkHostnameVerifier.INSTANCE);
            } else if (wXRequest.sslVerify) {
                try {
                    if (sslSocketFactory == null) {
                        sslSocketFactory = DCloudTrustManager.getSSLSocketFactory();
                    }
                    SSLSocketFactory sSLSocketFactory = sslSocketFactory;
                    if (sSLSocketFactory != null) {
                        builder.sslSocketFactory(sSLSocketFactory);
                    }
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e2) {
                    e2.printStackTrace();
                }
                builder.hostnameVerifier(getHostnameVerifier(wXRequest.sslVerify));
            }
        }
        if (mConnectPool == null) {
            mConnectPool = new ConnectionPool();
        }
        builder.connectionPool(mConnectPool);
        Request.Builder builder2 = new Request.Builder();
        builder2.url(wXRequest.url);
        Map<String, String> map2 = wXRequest.paramMap;
        String str = "application/x-www-form-urlencoded";
        if (map2 != null) {
            for (String str2 : map2.keySet()) {
                if (str2.equalsIgnoreCase(NetWork.CONTENT_TYPE)) {
                    str = wXRequest.paramMap.get(str2);
                }
                builder2.addHeader(str2, wXRequest.paramMap.get(str2));
            }
        }
        if ("POST".equals(wXRequest.method) || "PUT".equals(wXRequest.method) || "PATCH".equals(wXRequest.method) || "DELETE".equals(wXRequest.method)) {
            if (wXRequest.body != null && onHttpListener != null) {
                onHttpListener.onHttpUploadProgress(0);
            }
            if ("BASE64".equalsIgnoreCase(wXRequest.inputType)) {
                builder2.method(wXRequest.method, RequestBody.createWithBytes(MediaType.parse(str), Base64.decode(wXRequest.body, 2)));
                if (onHttpListener != null) {
                    onHttpListener.onHttpUploadProgress(100);
                }
            } else {
                builder2.method(wXRequest.method, RequestBody.create(MediaType.parse(str), wXRequest.body));
                if (onHttpListener != null) {
                    onHttpListener.onHttpUploadProgress(100);
                }
            }
        } else if ("HEAD".equals(wXRequest.method)) {
            builder2.head();
        }
        return builder2.build();
    }

    @Override // com.taobao.weex.adapter.IWXHttpAdapter
    public void sendRequest(final WXRequest wXRequest, final IWXHttpAdapter.OnHttpListener onHttpListener) {
        if (onHttpListener != null) {
            onHttpListener.onHttpStart();
        }
        execute(new Runnable() { // from class: io.dcloud.feature.weex.adapter.DCWXHttpAdapter.1
            @Override // java.lang.Runnable
            public void run() {
                boolean z;
                WXSDKInstance wXSDKInstance = WXSDKManager.getInstance().getAllInstanceMap().get(wXRequest.instanceId);
                if (wXSDKInstance != null && !wXSDKInstance.isDestroy()) {
                    wXSDKInstance.getApmForInstance().actionNetRequest();
                }
                WXResponse wXResponse = new WXResponse();
                boolean z2 = false;
                try {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    Response responseExecute = builder.build().newCall(DCWXHttpAdapter.this.getOKRequest(builder, wXRequest, onHttpListener)).execute();
                    Map<String, List<String>> multimap = responseExecute.headers().toMultimap();
                    int iCode = responseExecute.code();
                    wXResponse.statusCode = String.valueOf(iCode);
                    IWXHttpAdapter.OnHttpListener onHttpListener2 = onHttpListener;
                    if (onHttpListener2 != null) {
                        onHttpListener2.onHeadersReceived(iCode, multimap);
                    }
                    if (responseExecute.isSuccessful()) {
                        wXResponse.originalData = DCWXHttpAdapter.this.readInputStreamAsBytes(responseExecute.body().byteStream(), onHttpListener);
                        z = true;
                    } else {
                        wXResponse.errorMsg = DCWXHttpAdapter.this.readInputStream(responseExecute.body().byteStream(), onHttpListener);
                        z = false;
                    }
                    IWXHttpAdapter.OnHttpListener onHttpListener3 = onHttpListener;
                    if (onHttpListener3 != null) {
                        onHttpListener3.onHttpFinish(wXResponse);
                    }
                    z2 = z;
                } catch (Exception e) {
                    e.printStackTrace();
                    wXResponse.statusCode = "-1";
                    wXResponse.errorCode = "-1";
                    wXResponse.errorMsg = e.getMessage();
                    IWXHttpAdapter.OnHttpListener onHttpListener4 = onHttpListener;
                    if (onHttpListener4 != null) {
                        onHttpListener4.onHttpFinish(wXResponse);
                    }
                }
                if (wXSDKInstance == null || wXSDKInstance.isDestroy()) {
                    return;
                }
                wXSDKInstance.getApmForInstance().actionNetResult(z2, null);
            }
        });
    }
}
