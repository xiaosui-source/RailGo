package io.dcloud.p;

import android.app.Activity;
import io.dcloud.p.b0;
import io.dcloud.sdk.core.entry.DCloudAOLSlot;
import io.dcloud.sdk.core.util.AOLErrorUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class h4 extends x3 implements Runnable {
    protected DCloudAOLSlot b;
    protected final Activity c;
    protected int d;
    private b0.a e;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a extends b0.a {
        a(String str) {
            super(str);
        }

        @Override // io.dcloud.p.b0.a
        public void a(JSONObject jSONObject) {
            h4.this.a(jSONObject);
        }

        @Override // io.dcloud.p.b0.a
        public void a(int i, String str) {
            h4.this.b(i, str);
        }
    }

    public h4(Activity activity) {
        this.c = activity;
    }

    private void d() throws JSONException {
        if (this.e == null) {
            this.e = new a(this.b.getAdpid());
        }
        b0.a().a(this.c, c(), this.e);
    }

    protected abstract void a(int i, String str);

    protected abstract void a(t0 t0Var);

    protected final void a(DCloudAOLSlot dCloudAOLSlot) {
        this.b = dCloudAOLSlot;
        dCloudAOLSlot.setType(this.d);
    }

    protected void b(int i, String str) {
        a(i, str);
    }

    protected int c() {
        return 2;
    }

    @Override // java.lang.Runnable
    public void run() throws JSONException {
        d();
    }

    protected void a(JSONObject jSONObject) {
        if (jSONObject != null && jSONObject.length() > 0) {
            a(new t0().a(jSONObject, c()));
        } else {
            a(-5001, AOLErrorUtil.getErrorMsg(-5001));
        }
    }

    protected void a(b0.a aVar) {
        this.e = aVar;
    }
}
