package io.dcloud.p;

import android.text.TextUtils;
import com.nostra13.dcloudimageloader.core.download.BaseImageDownloader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class k3 {
    public static byte[] a(String str, HashMap map, boolean z) {
        return a(str, null, map, "GET", BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT, z);
    }

    public static byte[] a(String str, HashMap map, boolean z, String[] strArr) {
        return a(str, null, map, "GET", BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT, z, false, strArr);
    }

    private static byte[] a(String str, String str2, HashMap map, String str3, int i, boolean z) {
        return a(str, str2, map, str3, i, z, false, null);
    }

    private static byte[] a(String str, String str2, HashMap map, String str3, int i, boolean z, boolean z2, String[] strArr) throws IOException {
        if (str != null && str.length() != 0) {
            if (map == null) {
                try {
                    map = new HashMap();
                } catch (Exception e) {
                    if (strArr != null) {
                        strArr[0] = e.getMessage();
                    }
                }
            }
            HttpURLConnection httpURLConnectionA = a(new URL(str), str3, i, z, z2);
            if (!map.isEmpty()) {
                for (String str4 : map.keySet()) {
                    httpURLConnectionA.setRequestProperty(str4, (String) map.get(str4));
                }
            }
            if (!TextUtils.isEmpty(str3) && TextUtils.equals(str3.toLowerCase(Locale.ENGLISH), "post")) {
                a(httpURLConnectionA.getOutputStream(), str2);
            }
            int responseCode = httpURLConnectionA.getResponseCode();
            if (responseCode == 200) {
                return a(httpURLConnectionA.getInputStream());
            }
            if (strArr != null) {
                strArr[0] = String.valueOf(responseCode);
            }
            return null;
        }
        return null;
    }

    public static InputStream a(String str, int i, boolean z, String[] strArr) {
        return a(str, null, "GET", i, z, false, strArr);
    }

    private static InputStream a(String str, String str2, String str3, int i, boolean z, boolean z2, String[] strArr) throws IOException {
        if (str != null && str.length() != 0) {
            try {
                HashMap map = new HashMap();
                HttpURLConnection httpURLConnectionA = a(new URL(str), str3, i, z, z2);
                if (!map.isEmpty()) {
                    for (String str4 : map.keySet()) {
                        httpURLConnectionA.setRequestProperty(str4, (String) map.get(str4));
                    }
                }
                if (!TextUtils.isEmpty(str3) && TextUtils.equals(str3.toLowerCase(Locale.ENGLISH), "post")) {
                    a(httpURLConnectionA.getOutputStream(), str2);
                }
                int responseCode = httpURLConnectionA.getResponseCode();
                if (responseCode == 200) {
                    return httpURLConnectionA.getInputStream();
                }
                if (strArr != null) {
                    strArr[0] = String.valueOf(responseCode);
                }
                return null;
            } catch (Exception e) {
                if (strArr != null) {
                    strArr[0] = e.getMessage();
                }
            }
        }
        return null;
    }

    private static void a(OutputStream outputStream, String str) throws IOException {
        if (str != null) {
            try {
                if (str.length() > 0) {
                    outputStream.write(str.getBytes("UTF-8"));
                }
            } catch (IOException unused) {
            }
        }
    }

    private static byte[] a(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            int i = 10240;
            int iMin = Math.min(10240, inputStream.available());
            if (iMin > 0) {
                i = iMin;
            }
            byte[] bArr = new byte[i];
            while (true) {
                int i2 = inputStream.read(bArr);
                if (i2 <= 0) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, i2);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static HttpURLConnection a(URL url, String str, int i, boolean z, boolean z2) throws ProtocolException {
        HttpURLConnection httpURLConnection;
        try {
            if (w0.a) {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
            }
            httpURLConnection.setConnectTimeout(i);
            httpURLConnection.setReadTimeout(i);
            httpURLConnection.setRequestMethod(str);
            httpURLConnection.setDoInput(true);
            return httpURLConnection;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] a(String str, String str2, HashMap map) {
        return a(str, str2, map, "POST", BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT, true);
    }

    public static byte[] a(String str, String str2, HashMap map, String[] strArr) {
        return a(str, str2, map, "POST", BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT, true, false, strArr);
    }
}
