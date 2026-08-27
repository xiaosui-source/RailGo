package io.dcloud.p;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;
import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.common.DHInterface.IWaiter;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.language.LanguageUtil;
import io.dcloud.feature.gg.AolFeatureImplMgr;
import io.dcloud.feature.internal.sdk.SDK;
import java.lang.reflect.InvocationTargetException;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class k {
    private static Class a;

    public static void a(Application application) {
        IWaiter iWaiterB = b();
        if (iWaiterB != null) {
            iWaiterB.doForFeature("onAppCreate", application);
        }
    }

    private static IWaiter b() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (c()) {
            try {
                Object objInvoke = a.getMethod("self", null).invoke(null, null);
                if (objInvoke instanceof IWaiter) {
                    return (IWaiter) objInvoke;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean c() {
        if (a != null) {
            return true;
        }
        try {
            int i = AolFeatureImplMgr.a;
            a = AolFeatureImplMgr.class;
        } catch (Exception unused) {
        }
        return a != null;
    }

    public static void a(Context context) {
        IWaiter iWaiterB = b();
        DeviceInfo.sApplicationContext = context;
        if (iWaiterB != null) {
            iWaiterB.doForFeature("onAppAttachBaseContext", context);
        }
    }

    public static Object a(Context context, String str, String str2, Object obj) {
        IWaiter iWaiterB;
        if (SDK.isUniMPSDK()) {
            return null;
        }
        if ((!PdrUtil.checkIntl() || ((PdrUtil.checkIntl() && LanguageUtil.getDeviceDefCountry().equalsIgnoreCase(i0.c().decryptStr("GJ", (byte) 4))) || (PdrUtil.checkIntl() && a()))) && (iWaiterB = b()) != null) {
            return iWaiterB.doForFeature(str2, new Object[]{context, str, obj});
        }
        return null;
    }

    private static boolean a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IWaiter iWaiterB = b();
        if (iWaiterB != null) {
            return ((Boolean) iWaiterB.doForFeature("adc", null)).booleanValue();
        }
        return false;
    }

    public static View a(Activity activity, ICallBack iCallBack, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IWaiter iWaiterB = b();
        if (iWaiterB != null) {
            return (View) iWaiterB.doForFeature("onCreateAdSplash", new Object[]{activity, iCallBack, str});
        }
        return null;
    }
}
