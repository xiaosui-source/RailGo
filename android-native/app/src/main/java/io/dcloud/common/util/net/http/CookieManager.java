package io.dcloud.common.util.net.http;

import android.content.Context;
import android.webkit.CookieSyncManager;
import java.net.CookieHandler;
import java.net.CookiePolicy;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class CookieManager {
    public static void initCookieConfig(Context context) {
        CookieSyncManager.createInstance(context);
        android.webkit.CookieManager.getInstance().setAcceptCookie(true);
        CookieHandler.setDefault(new WebkitCookieManagerProxy(null, CookiePolicy.ACCEPT_ALL));
    }
}
