package io.dcloud.feature.weex.extend;

import android.util.Base64;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;
import java.security.SecureRandom;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class RandomBytesModule extends WXModule {
    private String getRandomBytes(int i) {
        byte[] bArr = new byte[i];
        new SecureRandom().nextBytes(bArr);
        return Base64.encodeToString(bArr, 2);
    }

    @JSMethod(uiThread = false)
    public String getRandomValues(int i) {
        return getRandomBytes(i);
    }
}
