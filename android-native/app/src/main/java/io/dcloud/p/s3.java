package io.dcloud.p;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import io.dcloud.sdk.core.DCloudAOLManager;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class s3 extends DCloudAOLManager {
    public static void a(Context context, DCloudAOLManager.InitConfig initConfig, s1 s1Var) {
        if (context == null || initConfig == null) {
            throw new NullPointerException("context or config is null");
        }
        if (((context instanceof Activity) || (context instanceof Application)) && !DCloudAOLManager.c.get()) {
            DCloudAOLManager.c.set(true);
            if (DCloudAOLManager.b == null) {
                r0 r0VarD = r0.d();
                r0VarD.b(context);
                r0VarD.a(initConfig);
                r0VarD.a(s1Var);
                DCloudAOLManager.b = r0VarD;
                r0VarD.e();
                r0VarD.a(context);
            }
        }
    }

    public static boolean a() {
        return e.b().c().size() > 1;
    }
}
