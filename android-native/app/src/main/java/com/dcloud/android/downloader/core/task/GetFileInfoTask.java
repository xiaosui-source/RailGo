package com.dcloud.android.downloader.core.task;

import android.os.Process;
import android.text.TextUtils;
import com.dcloud.android.downloader.core.DownloadResponse;
import com.dcloud.android.downloader.domain.DownloadInfo;
import com.dcloud.android.downloader.exception.DownloadException;
import com.taobao.weex.performance.WXInstanceApm;
import io.dcloud.common.adapter.util.DCloudTrustManager;
import io.dcloud.feature.gg.dcloud.ADSim;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class GetFileInfoTask implements Runnable {
    private final DownloadInfo downloadInfo;
    private final DownloadResponse downloadResponse;
    private final OnGetFileInfoListener onGetFileInfoListener;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface OnGetFileInfoListener {
        void onFailed(DownloadException downloadException);

        void onSuccess(long j, boolean z);
    }

    public GetFileInfoTask(DownloadResponse downloadResponse, DownloadInfo downloadInfo, OnGetFileInfoListener onGetFileInfoListener) {
        this.downloadResponse = downloadResponse;
        this.downloadInfo = downloadInfo;
        this.onGetFileInfoListener = onGetFileInfoListener;
    }

    private void checkIfPause() {
        if (this.downloadInfo.isPause()) {
            throw new DownloadException(7);
        }
    }

    private void executeConnection() throws DownloadException {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.downloadInfo.getDownloadUrl()).openConnection();
            if (httpURLConnection instanceof HttpsURLConnection) {
                SSLSocketFactory sSLSocketFactory = DCloudTrustManager.getSSLSocketFactory();
                if (sSLSocketFactory != null) {
                    ((HttpsURLConnection) httpURLConnection).setSSLSocketFactory(sSLSocketFactory);
                }
                ((HttpsURLConnection) httpURLConnection).setHostnameVerifier(DCloudTrustManager.getHostnameVerifier(false));
            }
            httpURLConnection.setConnectTimeout(ADSim.INTISPLSH);
            httpURLConnection.setReadTimeout(ADSim.INTISPLSH);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Range", "bytes=0-");
            httpURLConnection.setInstanceFollowRedirects(false);
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                parseHttpResponse(httpURLConnection, false);
                return;
            }
            if (responseCode == 206) {
                parseHttpResponse(httpURLConnection, true);
                return;
            }
            if (responseCode != 302 && 301 != responseCode) {
                throw new DownloadException(3, "UnSupported response code:" + responseCode);
            }
            this.downloadInfo.setLocation(httpURLConnection.getHeaderField("Location"));
            executeConnection();
        } catch (MalformedURLException e) {
            throw new DownloadException(2, "Bad url.", e);
        } catch (ProtocolException e2) {
            throw new DownloadException(4, "Protocol error", e2);
        } catch (IOException e3) {
            throw new DownloadException(5, "IO error", e3);
        } catch (Exception e4) {
            throw new DownloadException(5, "Unknown error", e4);
        }
    }

    private void parseHttpResponse(HttpURLConnection httpURLConnection, boolean z) throws DownloadException {
        String headerField = httpURLConnection.getHeaderField("Content-Length");
        long contentLength = (TextUtils.isEmpty(headerField) || headerField.equals(WXInstanceApm.VALUE_ERROR_CODE_DEFAULT) || headerField.equals("-1")) ? httpURLConnection.getContentLength() : Long.parseLong(headerField);
        if (contentLength <= 0) {
            throw new DownloadException(6, "length <= 0");
        }
        checkIfPause();
        this.onGetFileInfoListener.onSuccess(contentLength, z);
    }

    @Override // java.lang.Runnable
    public void run() throws SecurityException, IllegalArgumentException {
        Process.setThreadPriority(10);
        try {
            executeConnection();
        } catch (DownloadException e) {
            this.downloadResponse.handleException(e);
        } catch (Exception e2) {
            this.downloadResponse.handleException(new DownloadException(9, e2));
        }
    }
}
