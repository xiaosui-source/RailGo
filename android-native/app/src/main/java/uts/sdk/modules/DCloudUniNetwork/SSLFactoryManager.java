package uts.sdk.modules.DCloudUniNetwork;

import android.text.TextUtils;
import io.dcloud.uts.Map;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000 \n2\u00020\u0001:\u0001\nB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\t\u001a\u00020\u0006H\u0016R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/SSLFactoryManager;", "", "<init>", "()V", "cacheSSLFactory", "Lio/dcloud/uts/Map;", "Luts/sdk/modules/DCloudUniNetwork/SSLConfig;", "Ljavax/net/ssl/SSLSocketFactory;", "getSSLSocketFactory", "sslConfig", "Companion", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class SSLFactoryManager {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static SSLFactoryManager instance;
    private Map<SSLConfig, SSLSocketFactory> cacheSSLFactory = new Map<>();

    public SSLSocketFactory getSSLSocketFactory(SSLConfig sslConfig) throws NoSuchAlgorithmException, KeyStoreException, CertificateException {
        Intrinsics.checkNotNullParameter(sslConfig, "sslConfig");
        if (this.cacheSSLFactory.has(sslConfig)) {
            return this.cacheSSLFactory.get(sslConfig);
        }
        try {
            SSLContext.getInstance("TLS");
            KeyStore.getInstance("PKCS12");
            KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            if (!TextUtils.isEmpty(sslConfig.getKeystore())) {
                TextUtils.isEmpty(sslConfig.getStorePass());
            }
            CertificateFactory.getInstance("X.509");
            KeyStore.getInstance("PKCS12");
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    /* compiled from: index.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0006\u001a\u00020\u0005R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Luts/sdk/modules/DCloudUniNetwork/SSLFactoryManager$Companion;", "", "<init>", "()V", "instance", "Luts/sdk/modules/DCloudUniNetwork/SSLFactoryManager;", "getInstance", "uni-network_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final SSLFactoryManager getInstance() {
            if (SSLFactoryManager.instance == null) {
                SSLFactoryManager.instance = new SSLFactoryManager();
            }
            SSLFactoryManager sSLFactoryManager = SSLFactoryManager.instance;
            Intrinsics.checkNotNull(sSLFactoryManager);
            return sSLFactoryManager;
        }
    }
}
