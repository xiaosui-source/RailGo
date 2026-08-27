package io.dcloud.common.DHInterface;

import android.app.Application;
import android.content.Context;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public interface IUniInstanceMgr {
    void initUniappPlugin(Application application);

    void initWeexEnv(INativeAppInfo iNativeAppInfo);

    boolean isUniAppAssetsRes();

    void loadWeexToAppid(Context context, String str, boolean z);

    void onCreateProcess(Application application, Boolean bool);

    void registerUniappService(Context context, String str);

    void restartWeex(Application application, ICallBack iCallBack, String str);
}
