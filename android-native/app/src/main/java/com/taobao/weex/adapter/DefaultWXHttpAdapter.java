package com.taobao.weex.adapter;

import android.text.TextUtils;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXHttpAdapter;
import com.taobao.weex.common.WXRequest;
import com.taobao.weex.common.WXResponse;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class DefaultWXHttpAdapter implements IWXHttpAdapter {
    private static final IEventReporterDelegate DEFAULT_DELEGATE = new NOPEventReportDelegate();
    private ExecutorService mExecutorService;

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    public interface IEventReporterDelegate {
        void httpExchangeFailed(IOException iOException);

        InputStream interpretResponseStream(InputStream inputStream);

        void postConnect();

        void preConnect(HttpURLConnection httpURLConnection, String str);
    }

    /* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
    private static class NOPEventReportDelegate implements IEventReporterDelegate {
        private NOPEventReportDelegate() {
        }

        @Override // com.taobao.weex.adapter.DefaultWXHttpAdapter.IEventReporterDelegate
        public void httpExchangeFailed(IOException iOException) {
        }

        @Override // com.taobao.weex.adapter.DefaultWXHttpAdapter.IEventReporterDelegate
        public InputStream interpretResponseStream(InputStream inputStream) {
            return inputStream;
        }

        @Override // com.taobao.weex.adapter.DefaultWXHttpAdapter.IEventReporterDelegate
        public void postConnect() {
        }

        @Override // com.taobao.weex.adapter.DefaultWXHttpAdapter.IEventReporterDelegate
        public void preConnect(HttpURLConnection httpURLConnection, String str) {
        }
    }

    private void execute(Runnable runnable) {
        if (this.mExecutorService == null) {
            this.mExecutorService = Executors.newFixedThreadPool(3);
        }
        this.mExecutorService.execute(runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public HttpURLConnection openConnection(WXRequest wXRequest, IWXHttpAdapter.OnHttpListener onHttpListener) throws IOException {
        HttpURLConnection httpURLConnectionCreateConnection = createConnection(new URL(wXRequest.url));
        httpURLConnectionCreateConnection.setConnectTimeout(wXRequest.timeoutMs);
        httpURLConnectionCreateConnection.setReadTimeout(wXRequest.timeoutMs);
        httpURLConnectionCreateConnection.setUseCaches(false);
        httpURLConnectionCreateConnection.setDoInput(true);
        Map<String, String> map = wXRequest.paramMap;
        if (map != null) {
            for (String str : map.keySet()) {
                httpURLConnectionCreateConnection.addRequestProperty(str, wXRequest.paramMap.get(str));
            }
        }
        if (!"POST".equals(wXRequest.method) && !"PUT".equals(wXRequest.method) && !"PATCH".equals(wXRequest.method)) {
            if (TextUtils.isEmpty(wXRequest.method)) {
                httpURLConnectionCreateConnection.setRequestMethod("GET");
                return httpURLConnectionCreateConnection;
            }
            httpURLConnectionCreateConnection.setRequestMethod(wXRequest.method);
            return httpURLConnectionCreateConnection;
        }
        httpURLConnectionCreateConnection.setRequestMethod(wXRequest.method);
        if (wXRequest.body != null) {
            if (onHttpListener != null) {
                onHttpListener.onHttpUploadProgress(0);
            }
            httpURLConnectionCreateConnection.setDoOutput(true);
            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnectionCreateConnection.getOutputStream());
            dataOutputStream.write(wXRequest.body.getBytes());
            dataOutputStream.close();
            if (onHttpListener != null) {
                onHttpListener.onHttpUploadProgress(100);
            }
        }
        return httpURLConnectionCreateConnection;
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

    protected HttpURLConnection createConnection(URL url) {
        return (HttpURLConnection) url.openConnection();
    }

    public IEventReporterDelegate getEventReporterDelegate() {
        return DEFAULT_DELEGATE;
    }

    @Override // com.taobao.weex.adapter.IWXHttpAdapter
    public void sendRequest(final WXRequest wXRequest, final IWXHttpAdapter.OnHttpListener onHttpListener) {
        if (onHttpListener != null) {
            onHttpListener.onHttpStart();
        }
        execute(new Runnable() { // from class: com.taobao.weex.adapter.DefaultWXHttpAdapter.1
            @Override // java.lang.Runnable
            public void run() throws IOException {
                boolean z;
                WXSDKInstance wXSDKInstance = WXSDKManager.getInstance().getAllInstanceMap().get(wXRequest.instanceId);
                if (wXSDKInstance != null && !wXSDKInstance.isDestroy()) {
                    wXSDKInstance.getApmForInstance().actionNetRequest();
                }
                WXResponse wXResponse = new WXResponse();
                IEventReporterDelegate eventReporterDelegate = DefaultWXHttpAdapter.this.getEventReporterDelegate();
                boolean z2 = false;
                try {
                    HttpURLConnection httpURLConnectionOpenConnection = DefaultWXHttpAdapter.this.openConnection(wXRequest, onHttpListener);
                    eventReporterDelegate.preConnect(httpURLConnectionOpenConnection, wXRequest.body);
                    Map<String, List<String>> headerFields = httpURLConnectionOpenConnection.getHeaderFields();
                    int responseCode = httpURLConnectionOpenConnection.getResponseCode();
                    IWXHttpAdapter.OnHttpListener onHttpListener2 = onHttpListener;
                    if (onHttpListener2 != null) {
                        onHttpListener2.onHeadersReceived(responseCode, headerFields);
                    }
                    eventReporterDelegate.postConnect();
                    wXResponse.statusCode = String.valueOf(responseCode);
                    if (responseCode < 200 || responseCode > 299) {
                        wXResponse.errorMsg = DefaultWXHttpAdapter.this.readInputStream(httpURLConnectionOpenConnection.getErrorStream(), onHttpListener);
                        z = false;
                    } else {
                        wXResponse.originalData = DefaultWXHttpAdapter.this.readInputStreamAsBytes(eventReporterDelegate.interpretResponseStream(httpURLConnectionOpenConnection.getInputStream()), onHttpListener);
                        z = true;
                    }
                    IWXHttpAdapter.OnHttpListener onHttpListener3 = onHttpListener;
                    if (onHttpListener3 != null) {
                        onHttpListener3.onHttpFinish(wXResponse);
                    }
                    z2 = z;
                } catch (IOException | IllegalArgumentException e) {
                    e.printStackTrace();
                    wXResponse.statusCode = "-1";
                    wXResponse.errorCode = "-1";
                    wXResponse.errorMsg = e.getMessage();
                    IWXHttpAdapter.OnHttpListener onHttpListener4 = onHttpListener;
                    if (onHttpListener4 != null) {
                        onHttpListener4.onHttpFinish(wXResponse);
                    }
                    if (e instanceof IOException) {
                        try {
                            eventReporterDelegate.httpExchangeFailed((IOException) e);
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
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
