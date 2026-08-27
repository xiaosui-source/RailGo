package io.dcloud.common.util.net;

import com.taobao.weex.el.parse.Operators;
import io.dcloud.common.DHInterface.IReqListener;
import io.dcloud.common.DHInterface.IResponseListener;
import io.dcloud.common.adapter.util.DCloudTrustManager;
import io.dcloud.common.util.NetTool;
import io.dcloud.common.util.PdrUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class NetWork implements Runnable {
    public static long AUTO_RECONNECTTIME = 30000;
    public static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_COMMON = "text/plain;charset=utf-8";
    private static final String CONTENT_TYPE_UPLOAD = "application/x-www-form-urlencoded";
    private static final String DEFALUT_CHARSET = "utf-8";
    private static final String PARAM_CHARSET = ";charset=";
    public static final int WORK_COMMON = 3;
    public static final int WORK_DOWNLOAD = 2;
    public static final int WORK_UPLOAD = 1;
    protected boolean isAbort;
    public int mPriority;
    protected IReqListener mReqListener;
    protected HttpURLConnection mRequest;
    protected RequestData mRequestData;
    protected InputStream mResponseInput;
    protected IResponseListener mResponseListener;
    protected String mResponseText;
    private int mWorkType;
    NetWorkLoop mNetWorkLoop = null;
    public int mTimes = 1;
    public int MAX_TIMES = 3;
    protected long mRetryIntervalTime = AUTO_RECONNECTTIME;
    protected Map<String, String> mHeaderList = new HashMap();
    protected String mMainBoundry = getBoundry();

    public NetWork(int i, RequestData requestData, IReqListener iReqListener, IResponseListener iResponseListener) {
        this.mWorkType = i;
        this.mRequestData = requestData;
        this.mReqListener = iReqListener;
        this.mResponseListener = iResponseListener;
    }

    public static String getBoundry() {
        StringBuffer stringBuffer = new StringBuffer("------");
        for (int i = 1; i < 7; i++) {
            long jCurrentTimeMillis = System.currentTimeMillis() + i;
            long j = jCurrentTimeMillis % 3;
            if (j == 0) {
                stringBuffer.append(((char) jCurrentTimeMillis) % '\t');
            } else if (j == 1) {
                stringBuffer.append((char) ((jCurrentTimeMillis % 26) + 65));
            } else {
                stringBuffer.append((char) ((jCurrentTimeMillis % 26) + 97));
            }
        }
        return stringBuffer.toString();
    }

    private String getCharset(String str) {
        if (str == null) {
            return null;
        }
        String strReplace = str.replace(Operators.SPACE_STR, "");
        if (strReplace.contains(PARAM_CHARSET)) {
            return strReplace.substring(strReplace.indexOf(PARAM_CHARSET) + 9);
        }
        return null;
    }

    private void setHeadersAndValues(Map<String, List<String>> map) {
        if (map == null) {
            return;
        }
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            if (!PdrUtil.isEmpty(entry.getValue())) {
                String str = "";
                for (int i = 0; i < entry.getValue().size(); i++) {
                    str = i == 0 ? entry.getValue().get(i) : str + "  " + entry.getValue().get(i);
                }
                if (!PdrUtil.isEmpty(entry.getKey())) {
                    this.mHeaderList.put(entry.getKey(), str);
                }
            }
        }
    }

    public void cancelWork() {
        this.isAbort = true;
        HttpURLConnection httpURLConnection = this.mRequest;
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
            this.mRequest = null;
        }
    }

    public void dispose() {
        this.mReqListener = null;
        this.mResponseListener = null;
    }

    public Map<String, String> getHeadersAndValues() {
        return this.mHeaderList;
    }

    public InputStream getResponseInput() {
        return this.mResponseInput;
    }

    public String getResponseText() {
        return this.mResponseText;
    }

    public void handleResponseText(InputStream inputStream) throws IOException {
        try {
            String charset = getCharset(this.mRequestData.mOverrideMimeType);
            if (charset == null) {
                charset = getCharset(this.mRequest.getContentType());
            }
            if (charset == null) {
                charset = DEFALUT_CHARSET;
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i = 10240;
            int iMin = Math.min(10240, inputStream.available());
            if (iMin > 0) {
                i = iMin;
            }
            byte[] bArr = new byte[i];
            while (true) {
                int i2 = inputStream.read(bArr);
                if (i2 <= 0) {
                    inputStream.close();
                    this.mResponseText = new String(byteArrayOutputStream.toByteArray(), charset);
                    return;
                }
                byteArrayOutputStream.write(bArr, 0, i2);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.mResponseText = "";
        }
    }

    public void initHttpsURLConnectionVel() {
        HttpURLConnection httpURLConnection = this.mRequest;
        if (httpURLConnection != null) {
            if (httpURLConnection instanceof HttpsURLConnection) {
                try {
                    SSLSocketFactory sSLSocketFactory = DCloudTrustManager.getSSLSocketFactory();
                    if (sSLSocketFactory != null) {
                        ((HttpsURLConnection) this.mRequest).setSSLSocketFactory(sSLSocketFactory);
                    }
                    ((HttpsURLConnection) this.mRequest).setHostnameVerifier(NetTool.getDefaultHostnameVerifier());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            this.mRequest.setConnectTimeout(this.mRequestData.mTimeout);
            this.mRequest.setReadTimeout(this.mRequestData.mTimeout);
            this.mRequest.setInstanceFollowRedirects(true);
            this.mRequest.setDoInput(true);
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:57:0x0053
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1178)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    @Override // java.lang.Runnable
    public void run() {
        /*
            Method dump skipped, instructions count: 474
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.util.net.NetWork.run():void");
    }

    public void setRetryIntervalTime(long j) {
        if (j > 0) {
            this.mRetryIntervalTime = j;
        }
    }

    public void startWork() {
        Thread thread = new Thread(this);
        thread.setPriority(1);
        thread.start();
        this.mReqListener.onNetStateChanged(IReqListener.NetState.NET_INIT, this.isAbort);
    }
}
