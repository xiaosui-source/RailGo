package io.dcloud.sdk.base.dcloud;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.feature.gg.dcloud.ADSim;
import io.dcloud.p.c0;
import io.dcloud.p.k3;
import io.dcloud.p.w4;
import io.dcloud.p.y0;
import io.dcloud.p.z0;
import io.dcloud.p.z1;
import io.dcloud.sdk.base.dcloud.ADHandler;
import java.io.File;
import java.util.HashMap;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class c {
    ADHandler.e a = null;
    private Context b;
    Handler c;
    JSONObject d;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a extends Handler {
        a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what != 10000) {
                return;
            }
            c.this.c();
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements Runnable {
        b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            c cVar = c.this;
            cVar.a = cVar.a(cVar.d);
            ADHandler.e eVar = c.this.a;
            if (eVar != null) {
                String strOptString = eVar.c() != null ? c.this.a.c().optString("ua") : "";
                c cVar2 = c.this;
                cVar2.a(cVar2.a.g, strOptString);
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    /* renamed from: io.dcloud.sdk.base.dcloud.c$c, reason: collision with other inner class name */
    class RunnableC0071c implements Runnable {
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        RunnableC0071c(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        @Override // java.lang.Runnable
        public void run() {
            HashMap map = new HashMap();
            if (!TextUtils.isEmpty(this.a) && this.a.equalsIgnoreCase("webview")) {
                map.put(IWebview.USER_AGENT, ADHandler.a("ua-webview"));
            }
            try {
                if (k3.a(this.b, map, true) != null) {
                    c.this.c.sendEmptyMessage(ADSim.INTISPLSH);
                }
            } catch (Exception unused) {
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class d implements Runnable {
        d() {
        }

        @Override // java.lang.Runnable
        public void run() {
            c.this.a();
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class e implements y0.a {
        final /* synthetic */ Context a;
        final /* synthetic */ String b;
        final /* synthetic */ String c;
        final /* synthetic */ String d;
        final /* synthetic */ String e;
        final /* synthetic */ z0 f;

        e(z1 z1Var, Context context, String str, String str2, String str3, String str4, z0 z0Var) {
            this.a = context;
            this.b = str;
            this.c = str2;
            this.d = str3;
            this.e = str4;
            this.f = z0Var;
        }

        @Override // io.dcloud.p.y0.a
        public void a(y0 y0Var) {
            y0Var.a();
            throw null;
        }

        @Override // io.dcloud.p.y0.a
        public void b(y0 y0Var) {
            c.b(this.a, this.b, this.c, this.d, this.e, 32);
            File file = new File(y0Var.b());
            if (file.exists()) {
                file.delete();
            }
            this.f.b(y0Var);
        }
    }

    public c(Context context, JSONObject jSONObject) {
        this.c = null;
        this.d = jSONObject;
        this.b = context;
        this.c = new a(context.getMainLooper());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        ADHandler.a("adh", "ADSim---view");
        Context context = this.b;
        ADHandler.c(context, this.a, ADHandler.a(context, "adid"));
        b();
    }

    public void d() {
        this.c.postDelayed(new b(), a(250, 350));
    }

    private void b() {
        if (this.a.d()) {
            this.c.postDelayed(new d(), a(800, 2000));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, String str2) {
        w4.a().a(new RunnableC0071c(str2, str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(final Context context, final String str, final String str2, final String str3, final String str4, final int i) {
        w4.a().a(new Runnable() { // from class: io.dcloud.sdk.base.dcloud.c$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                c0.a(context, str, str2, str3, i, str4);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ADHandler.e a(JSONObject jSONObject) {
        ADHandler.e eVar = new ADHandler.e();
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
        if (jSONObjectOptJSONObject != null) {
            eVar.c = jSONObject.optString("provider");
            eVar.d = jSONObject;
            eVar.i = jSONObject.optInt("es", 0);
            eVar.j = jSONObject.optInt("ec", 0);
            eVar.g = jSONObjectOptJSONObject.optString("src");
            eVar.e = "000";
            eVar.h = ADHandler.a("appid");
        }
        return eVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        ADHandler.a("adh", "ADSim---click");
        Context context = this.b;
        ADHandler.a(context, this.a, ADHandler.a(context, "adid"));
    }

    public static int a(int i, int i2) {
        return (int) (i + (Math.random() * ((i2 - i) + 1)));
    }

    public static void a(Context context, String str) {
        new io.dcloud.sdk.base.dcloud.d(context).a(str);
    }

    public static void a(Context context, String str, String str2, String str3, String str4, String str5, z1 z1Var, String str6, String str7) {
        b(context, str, str2, str3, str7, 29);
        z0 z0VarA = z0.a(context.getApplicationContext());
        String absolutePath = context.getExternalFilesDir(null).getAbsolutePath();
        StringBuilder sb = new StringBuilder();
        sb.append(absolutePath);
        sb.append(absolutePath.endsWith("/") ? "" : "/");
        String str8 = sb.toString() + "/Download/ADSIM-INFO.io";
        File file = new File(str8);
        if (file.exists()) {
            file.delete();
        }
        for (y0 y0Var : z0VarA.a()) {
            if (y0Var.c().equals(str4)) {
                z0VarA.b(y0Var);
            }
        }
        y0 y0Var2 = new y0();
        y0Var2.a(context, str4, str8);
        y0Var2.a(new e(z1Var, context, str, str2, str3, str7, z0VarA));
        z0VarA.a(y0Var2);
    }
}
