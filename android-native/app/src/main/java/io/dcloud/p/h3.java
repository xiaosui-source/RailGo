package io.dcloud.p;

import android.app.Application;
import io.dcloud.common.DHInterface.IConfusionMgr;
import io.dcloud.common.DHInterface.INativeAppInfo;
import java.lang.ref.SoftReference;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class h3 implements INativeAppInfo {
    private IConfusionMgr a;
    private SoftReference b;

    public h3(Application application) {
        a(application);
        a(this.a);
    }

    private void a(Application application) {
        this.b = new SoftReference(application);
    }

    @Override // io.dcloud.common.DHInterface.INativeAppInfo
    public Application getApplication() {
        SoftReference softReference = this.b;
        if (softReference != null) {
            return (Application) softReference.get();
        }
        return null;
    }

    @Override // io.dcloud.common.DHInterface.INativeAppInfo
    public IConfusionMgr getCofusionMgr() {
        return this.a;
    }

    private void a(IConfusionMgr iConfusionMgr) {
        if (iConfusionMgr == null) {
            iConfusionMgr = i0.c();
        }
        this.a = iConfusionMgr;
    }
}
