package io.dcloud.p;

import android.text.TextUtils;
import androidtranscoder.format.MediaFormatExtraConstants;
import androidx.core.app.NotificationCompat;
import com.facebook.common.callercontext.ContextChain;
import com.taobao.weex.adapter.IWXUserTrackAdapter;
import com.taobao.weex.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class m1 {
    private String b;
    private String c;
    private String d;
    private String e;
    private int f;
    private int g;
    private String h;
    private JSONObject i;
    private int k;
    private String m;
    private String n;
    private String o;
    private long a = 0;
    private boolean j = true;
    private boolean l = false;

    public m1 a(long j) {
        this.a = j;
        return this;
    }

    public m1 b(int i) {
        this.k = i;
        return this;
    }

    public m1 c(String str) {
        this.c = str;
        return this;
    }

    public m1 d(String str) {
        this.b = str;
        return this;
    }

    public m1 e(String str) {
        this.d = str;
        return this;
    }

    public m1 f(String str) {
        this.e = str;
        return this;
    }

    public JSONObject g() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(Constants.Value.TIME, this.a);
            jSONObject.put("ret", this.f);
            if (this.f == 0) {
                jSONObject.put(IWXUserTrackAdapter.MONITOR_ERROR_MSG, this.i);
            }
            jSONObject.put("tid", this.e);
            jSONObject.put("mediaId", this.c);
            jSONObject.put("slotId", this.d);
            jSONObject.put("provider", this.b);
            jSONObject.put(MediaFormatExtraConstants.KEY_LEVEL, this.k);
            if (this.l) {
                jSONObject.put("use", 1);
            }
            if (!TextUtils.isEmpty(this.m)) {
                jSONObject.put("cav", this.m);
            }
            if (!TextUtils.isEmpty(this.n)) {
                jSONObject.put("csv", this.n);
            }
            String str = this.o;
            if (str != null) {
                jSONObject.put("cpm", str);
            }
        } catch (JSONException unused) {
        }
        return jSONObject;
    }

    public String toString() {
        return g().toString();
    }

    public m1 a(String str) {
        this.m = str;
        return this;
    }

    public m1 b(String str) {
        this.n = str;
        return this;
    }

    public m1 c(int i) {
        this.f = i;
        return this;
    }

    public int d() {
        return this.f;
    }

    public boolean e() {
        return this.j;
    }

    public void f() {
        this.l = true;
    }

    public m1 a(int i) {
        this.o = String.valueOf(i);
        return this;
    }

    public JSONObject b() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(ContextChain.TAG_PRODUCT, this.b);
            jSONObject.put("id", this.d);
            jSONObject.put("code", this.g);
            jSONObject.put(NotificationCompat.CATEGORY_MESSAGE, this.h);
        } catch (JSONException unused) {
        }
        return jSONObject;
    }

    public String c() {
        return this.d;
    }

    public m1 a(int i, String str) throws JSONException {
        this.j = i != -9999;
        this.g = i;
        this.h = str;
        JSONObject jSONObject = new JSONObject();
        this.i = jSONObject;
        try {
            jSONObject.put("code", i);
            this.i.put(NotificationCompat.CATEGORY_MESSAGE, str);
        } catch (JSONException unused) {
        }
        return this;
    }

    public int a() {
        return this.g;
    }
}
