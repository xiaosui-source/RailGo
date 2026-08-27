package io.dcloud.p;

import android.content.Context;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.feature.gg.dcloud.ADHandler;
import io.dcloud.p.s1;
import io.dcloud.sdk.core.util.MainHandlerUtil;
import java.io.IOException;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class t3 extends v {
    s1.a[] a;
    Context b;
    boolean c = false;
    JSONObject d = new JSONObject();

    public t3() {
        if (DHFile.hasFile()) {
            w0.a(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(Context context) throws IOException, ClassNotFoundException {
        ADHandler.pull(context, r0.d().b().getAppId());
    }

    @Override // io.dcloud.p.g3, io.dcloud.p.s1
    public void a(final Context context, int i, s1.a... aVarArr) {
        this.c = true;
        if (i != 1) {
            MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.t3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws IOException, ClassNotFoundException {
                    t3.c(context);
                }
            });
        } else {
            this.a = aVarArr;
            this.b = context;
        }
    }

    public HashMap b(Context context) {
        return w3.a(context);
    }

    public void a(JSONObject jSONObject) {
        String[] strArr;
        Context context;
        JSONArray jSONArrayNames = jSONObject.names();
        p0 p0Var = null;
        if (jSONArrayNames != null) {
            strArr = new String[jSONArrayNames.length()];
            for (int i = 0; i < jSONArrayNames.length(); i++) {
                strArr[i] = jSONArrayNames.optString(i);
            }
        } else {
            strArr = null;
        }
        if (strArr != null) {
            try {
                p0Var = new p0(jSONObject, strArr);
            } catch (JSONException unused) {
            }
        }
        if (p0Var != null && p0Var.has("7C61656D") && (context = this.b) != null) {
            a(p0Var, context);
        }
        s1.a[] aVarArr = this.a;
        if (aVarArr != null) {
            for (s1.a aVar : aVarArr) {
                if (p0Var == null) {
                    aVar.a(-5007, "数据解析异常");
                } else {
                    aVar.a(p0Var);
                }
            }
        }
    }

    public void a(int i, String str) {
        s1.a[] aVarArr = this.a;
        if (aVarArr != null) {
            for (s1.a aVar : aVarArr) {
                aVar.a(i, str);
            }
        }
    }
}
