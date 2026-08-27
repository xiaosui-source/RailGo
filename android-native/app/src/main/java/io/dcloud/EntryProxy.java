package io.dcloud;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.CookieSyncManager;
import android.widget.FrameLayout;
import com.dcloud.android.widget.toast.ToastCompat;
import io.dcloud.application.DCLoudApplicationImpl;
import io.dcloud.common.DHInterface.ICore;
import io.dcloud.common.DHInterface.IOnCreateSplashView;
import io.dcloud.common.DHInterface.ISysEventListener;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.core.ui.DCKeyboardManager;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.RuningAcitvityUtil;
import io.dcloud.feature.internal.sdk.SDK;
import io.dcloud.p.p3;
import java.util.ArrayList;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class EntryProxy {
    public static boolean d = false;
    private static EntryProxy e;
    private ArrayList a = new ArrayList(1);
    boolean b = false;
    p3 c = null;

    private EntryProxy() {
    }

    private void clearData() {
        Logger.d("EntryProxy", " clearData");
        e = null;
        d = false;
        this.b = false;
        AndroidResources.clearData();
        BaseInfo.clearData();
        this.c = null;
    }

    public static EntryProxy getInstnace() {
        return e;
    }

    public static EntryProxy init(Activity activity, ICore.ICoreStatusListener iCoreStatusListener) {
        d = true;
        Context applicationContext = activity.getApplicationContext();
        DCLoudApplicationImpl.self().setContext(applicationContext);
        AndroidResources.initAndroidResources(applicationContext);
        EntryProxy entryProxy = e;
        if (entryProxy != null) {
            entryProxy.c.a().setmCoreListener(iCoreStatusListener);
            if (e.c.b() != applicationContext) {
                e.destroy(activity);
            }
        }
        if (e == null) {
            e = new EntryProxy();
            CookieSyncManager.createInstance(applicationContext);
            e.c = new p3(applicationContext, iCoreStatusListener);
        }
        e.a.add(activity);
        return e;
    }

    public void destroy(Activity activity) {
        onStop(activity);
    }

    public boolean didCreate() {
        return this.b;
    }

    public ICore getCoreHandler() {
        p3 p3Var = this.c;
        if (p3Var != null) {
            return p3Var.a();
        }
        return null;
    }

    public Activity getEntryActivity() {
        if (this.a.size() > 0) {
            return (Activity) this.a.get(0);
        }
        return null;
    }

    public boolean onActivityExecute(Activity activity, ISysEventListener.SysEventType sysEventType, Object obj) {
        p3 p3Var = this.c;
        if (p3Var != null) {
            return p3Var.a(activity, sysEventType, obj);
        }
        return false;
    }

    public void onConfigurationChanged(Activity activity, int i) {
        p3 p3Var = this.c;
        if (p3Var != null) {
            p3Var.a(activity, i);
        }
    }

    @Deprecated
    public boolean onCreate(Bundle bundle, FrameLayout frameLayout, SDK.IntegratedMode integratedMode, IOnCreateSplashView iOnCreateSplashView) {
        return onCreate(bundle, integratedMode, iOnCreateSplashView);
    }

    public void onNewIntent(Activity activity, Intent intent) {
        p3 p3Var = this.c;
        if (p3Var != null) {
            p3Var.a(activity, intent);
        }
    }

    public void onPause(Activity activity) {
        p3 p3Var = this.c;
        if (p3Var != null) {
            p3Var.a(activity);
        }
        CookieSyncManager.getInstance().stopSync();
    }

    public void onResume(Activity activity) {
        p3 p3Var = this.c;
        if (p3Var != null) {
            p3Var.b(activity);
        }
        CookieSyncManager.getInstance().startSync();
    }

    public void onStop(Activity activity) {
        try {
            DCKeyboardManager.getInstance().onStop();
            RuningAcitvityUtil.isRuningActivity = false;
            BaseInfo.isFirstRun = false;
            this.a.remove(activity);
            if (this.a.size() == 0) {
                p3 p3Var = this.c;
                if (p3Var == null) {
                    clearData();
                } else if (p3Var.c(activity)) {
                    clearData();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Deprecated
    public boolean onCreate(Bundle bundle, SDK.IntegratedMode integratedMode, IOnCreateSplashView iOnCreateSplashView) {
        return onCreate((Activity) this.a.get(r0.size() - 1), bundle, integratedMode, iOnCreateSplashView);
    }

    public boolean onCreate(Activity activity, Bundle bundle, SDK.IntegratedMode integratedMode, IOnCreateSplashView iOnCreateSplashView) {
        RuningAcitvityUtil.isRuningActivity = true;
        DCKeyboardManager.getInstance().onCreate(activity);
        AndroidResources.initAndroidResources(activity.getBaseContext());
        this.c.a(activity, bundle, integratedMode, iOnCreateSplashView);
        if (BaseInfo.SyncDebug && !activity.getPackageName().equals(activity.getResources().getString(PdrR.DCLOUD_PACKAGE_NAME_BASE))) {
            ToastCompat.makeText((Context) activity, PdrR.DCLOUD_SYNC_DEBUD_MESSAGE, 0).show();
        }
        return true;
    }

    @Deprecated
    public boolean onCreate(Bundle bundle) {
        return onCreate(bundle, (FrameLayout) null, (SDK.IntegratedMode) null, (IOnCreateSplashView) null);
    }

    public static EntryProxy init(Activity activity) {
        return init(activity, null);
    }
}
