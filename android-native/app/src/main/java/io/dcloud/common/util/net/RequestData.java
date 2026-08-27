package io.dcloud.common.util.net;

import android.text.TextUtils;
import io.dcloud.common.util.PdrUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class RequestData {
    public static final String URL_HTTP = "http";
    public static final String URL_HTTPS = "https";
    public String URL_METHOD;
    private String mBody;
    public int mChunkSize;
    private long mContentLength;
    private HashMap<String, String> mHeads;
    private HttpURLConnection mHttpRequest;
    private String mIp;
    private HashMap<String, String> mNameValue;
    private String mPort;
    private String mReqmethod;
    private String mUrl;
    public String unTrustedCAType = "accept";
    public boolean isRedirect = false;
    public int mTimeout = 120000;
    public String mOverrideMimeType = null;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    /* renamed from: io.dcloud.common.util.net.RequestData$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$dcloud$common$util$net$RequestData$HttpOption;

        static {
            int[] iArr = new int[HttpOption.values().length];
            $SwitchMap$io$dcloud$common$util$net$RequestData$HttpOption = iArr;
            try {
                iArr[HttpOption.POST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$dcloud$common$util$net$RequestData$HttpOption[HttpOption.PUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$dcloud$common$util$net$RequestData$HttpOption[HttpOption.DELETE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$io$dcloud$common$util$net$RequestData$HttpOption[HttpOption.HEAD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$io$dcloud$common$util$net$RequestData$HttpOption[HttpOption.TRACE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$io$dcloud$common$util$net$RequestData$HttpOption[HttpOption.OPTIONS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$io$dcloud$common$util$net$RequestData$HttpOption[HttpOption.GET.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    enum HttpOption {
        GET,
        POST,
        PUT,
        DELETE,
        HEAD,
        TRACE,
        OPTIONS
    }

    public RequestData(String str, String str2) {
        this.URL_METHOD = "http";
        this.mUrl = str;
        this.mReqmethod = str2;
        if (str != null && str.startsWith("https")) {
            this.URL_METHOD = "https";
        }
        this.mNameValue = new HashMap<>();
        this.mHeads = new HashMap<>();
    }

    public boolean addBody(String str) {
        if (PdrUtil.isEmpty(str)) {
            return false;
        }
        this.mBody = str;
        return true;
    }

    public boolean addHeader(String str, String str2) {
        if (PdrUtil.isEmpty(str) || PdrUtil.isEmpty(str2) || this.mHeads.containsKey(str)) {
            return false;
        }
        this.mHeads.put(str, str2);
        return true;
    }

    public boolean addParemeter(String str, String str2) {
        if (PdrUtil.isEmpty(str) || PdrUtil.isEmpty(str2) || this.mNameValue.containsKey(str)) {
            return false;
        }
        this.mNameValue.put(str, str2);
        return true;
    }

    public void clearData() {
        this.mHttpRequest.disconnect();
        this.mHttpRequest = null;
    }

    public boolean containHeader(String str) {
        if (str != null && this.mHeads != null && str.equals(NetWork.CONTENT_TYPE)) {
            Iterator<String> it = this.mHeads.keySet().iterator();
            while (it.hasNext()) {
                if (it.next().equalsIgnoreCase(NetWork.CONTENT_TYPE)) {
                    return true;
                }
            }
        }
        HashMap<String, String> map = this.mHeads;
        if (map != null) {
            return map.containsKey(str);
        }
        return false;
    }

    public HttpURLConnection getHttpRequest() throws IOException, IllegalArgumentException {
        URLConnection uRLConnectionOpenConnection;
        if (this.mHttpRequest == null) {
            HttpOption httpOptionValueOf = HttpOption.valueOf(this.mReqmethod.toUpperCase());
            try {
                uRLConnectionOpenConnection = new URL(this.mUrl).openConnection();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            } catch (IllegalArgumentException e3) {
                throw e3;
            }
            if (!(uRLConnectionOpenConnection instanceof HttpURLConnection)) {
                return null;
            }
            this.mHttpRequest = (HttpURLConnection) uRLConnectionOpenConnection;
            switch (AnonymousClass1.$SwitchMap$io$dcloud$common$util$net$RequestData$HttpOption[httpOptionValueOf.ordinal()]) {
                case 1:
                    this.mHttpRequest.setRequestMethod("POST");
                    break;
                case 2:
                    this.mHttpRequest.setRequestMethod("PUT");
                    break;
                case 3:
                    this.mHttpRequest.setRequestMethod("DELETE");
                    break;
                case 4:
                    this.mHttpRequest.setRequestMethod("HEAD");
                    break;
                case 5:
                    this.mHttpRequest.setRequestMethod("TRACE");
                    break;
                case 6:
                    this.mHttpRequest.setRequestMethod("OPTIONS");
                    break;
                default:
                    this.mHttpRequest.setRequestMethod("GET");
                    break;
            }
            addHeader(this.mHttpRequest);
        }
        return this.mHttpRequest;
    }

    public String getIP() {
        return this.mIp;
    }

    public String getPort() {
        return this.mPort;
    }

    public String getReqmethod() {
        return this.mReqmethod;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public void setReqmethod(String str) {
        this.mReqmethod = str;
    }

    public void setUrl(String str) {
        this.mUrl = str;
    }

    public void addBody(HttpURLConnection httpURLConnection) throws IOException {
        if (TextUtils.isEmpty(this.mBody)) {
            return;
        }
        try {
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            String str = this.mBody;
            if (str == null || str.length() <= 0) {
                return;
            }
            outputStream.write(this.mBody.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHeader(HttpURLConnection httpURLConnection) {
        if (httpURLConnection == null) {
            return;
        }
        for (String str : this.mHeads.keySet()) {
            httpURLConnection.addRequestProperty(str, this.mHeads.get(str));
        }
    }
}
