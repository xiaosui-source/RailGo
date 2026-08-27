package io.dcloud.common.adapter.util;

import android.text.TextUtils;
import io.dcloud.application.DCLoudApplicationImpl;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.PdrUtil;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class DCloudTrustManager {
    private DCloudTrustManager() {
    }

    public static SecureRandom createSecureRandom() {
        return new SecureRandom();
    }

    public static X509HostnameVerifier getHostnameVerifier(boolean z) {
        return (z || !(PdrUtil.isEquals(BaseInfo.untrustedca, "refuse") || PdrUtil.isEquals(BaseInfo.untrustedca, "warning"))) ? SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER : SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
    }

    private static SSLContext getSSLSocketContext(String str) {
        if (!PdrUtil.isEquals(str, "TLSv1") || AndroidResources.sAppTargetSdkVersion < 36) {
            try {
                return SSLContext.getInstance(str);
            } catch (NoSuchAlgorithmException unused) {
            }
        }
        try {
            try {
                return SSLContext.getInstance("TLSv1.2");
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException("No TLS provider", e);
            }
        } catch (NoSuchAlgorithmException unused2) {
            return SSLContext.getInstance("TLS");
        }
    }

    public static javax.net.ssl.SSLSocketFactory getSSLSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
        return getSSLSocketFactory(getSSLVersion());
    }

    private static String getSSLVersion() {
        return AndroidResources.sAppTargetSdkVersion >= 36 ? "TLSv1.2" : "TLSv1";
    }

    public static javax.net.ssl.SSLSocketFactory getSSLSocketFactory(String str) throws NoSuchAlgorithmException, KeyManagementException {
        Object objNewInstance;
        if (DCLoudApplicationImpl.self().getContext() != null) {
            String str2 = DCLoudApplicationImpl.self().getContext().getPackageName() + ".CustomTrustMgr";
            if (PlatformUtil.checkClass(str2) && (objNewInstance = PlatformUtil.newInstance(str2, null, null)) != null) {
                if (TextUtils.isEmpty(str)) {
                    str = getSSLVersion();
                }
                SSLContext sSLSocketContext = getSSLSocketContext(str);
                sSLSocketContext.init(null, new TrustManager[]{(TrustManager) objNewInstance}, createSecureRandom());
                return sSLSocketContext.getSocketFactory();
            }
        }
        return null;
    }
}
