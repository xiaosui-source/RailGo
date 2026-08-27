package io.dcloud.p;

import android.app.Application;
import android.content.Context;
import io.dcloud.p.b0;
import io.dcloud.sdk.core.DCloudAOLManager;
import io.dcloud.sdk.core.adapter.IAdAdapter;
import io.dcloud.sdk.core.interfaces.AOLLoader;
import io.dcloud.sdk.core.util.AdUtil;
import io.dcloud.sdk.poly.base.utils.PrivacyManager;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class r0 implements AOLLoader {
    private static volatile r0 f;
    private DCloudAOLManager.InitConfig a;
    private s1 b;
    private Context c;
    private boolean e = false;
    private h d = new h();

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a extends b0.a {
        a(String str) {
            super(str);
        }

        @Override // io.dcloud.p.b0.a
        public void a(int i, String str) {
        }

        @Override // io.dcloud.p.b0.a
        public void a(JSONObject jSONObject) {
        }
    }

    private r0() {
    }

    public static r0 d() {
        if (f == null) {
            synchronized (r0.class) {
                if (f == null) {
                    f = new r0();
                    f.b = new g3();
                }
            }
        }
        return f;
    }

    public void a(DCloudAOLManager.InitConfig initConfig) {
        this.a = initConfig;
    }

    public DCloudAOLManager.InitConfig b() {
        return this.a;
    }

    public Context c() {
        return this.c;
    }

    public void e() {
        Context context = this.c;
        if (context != null) {
            try {
                boolean z = true;
                if (1 != context.getPackageManager().getApplicationInfo(this.c.getPackageName(), 128).metaData.getInt("UNIAD_ENV_TYPE")) {
                    z = false;
                }
                this.e = z;
            } catch (Exception unused) {
            }
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader
    public boolean getPersonalAOL(Context context) {
        return AdUtil.getPersonalAd(context);
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader
    public void setPersonalAOL(Context context, boolean z) {
        AdUtil.setPersonalAd(context, z);
        Map mapA = e.b().a();
        Iterator it = mapA.keySet().iterator();
        while (it.hasNext()) {
            ((IAdAdapter) mapA.get((String) it.next())).setPersonalAd(z);
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader
    public void setPrivacyConfig(DCloudAOLManager.PrivacyConfig privacyConfig) {
        PrivacyManager.getInstance().updateConfig(privacyConfig);
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader
    public void updatePrivacyConfig(Context context, JSONObject jSONObject) {
        if (jSONObject == null || jSONObject.length() <= 0) {
            return;
        }
        AdUtil.setCustomPrivacyConfig(context, jSONObject.toString());
        DCloudAOLManager.PrivacyConfig privacyConfig = AdUtil.getPrivacyConfig(jSONObject);
        Iterator it = e.b().c().iterator();
        while (it.hasNext()) {
            IAdAdapter iAdAdapterB = e.b().b((String) it.next());
            if (iAdAdapterB != null) {
                iAdAdapterB.updatePrivacyConfig(privacyConfig);
            }
        }
    }

    public s1 a() {
        return this.b;
    }

    public void b(Context context) {
        this.c = context;
        if (context instanceof Application) {
            f4.a((Application) context);
        } else if (context != null) {
            f4.a((Application) context.getApplicationContext());
        }
    }

    public void a(s1 s1Var) {
        this.b = s1Var;
    }

    public void a(Context context) {
        b0.a().a(context, 1, new a(""));
    }
}
