package io.dcloud.sdk.core.module;

import android.app.Activity;
import androidx.core.app.NotificationCompat;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.p.b3;
import io.dcloud.sdk.core.entry.DCloudAOLSlot;
import io.dcloud.sdk.core.util.Const;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class a extends DCBaseAOL {
    private JSONObject A;
    private String B;
    private boolean C;
    private long D;
    private long y;
    private int z;

    public a(DCloudAOLSlot dCloudAOLSlot, Activity activity) {
        super(dCloudAOLSlot, activity);
        this.y = 0L;
        this.z = -1;
        this.B = "";
        this.C = true;
        this.D = 0L;
    }

    public void b(int i, String str) {
        this.C = i != -9999;
        b3.b("uniAD", getDCloudId() + ":" + getType() + ":" + i + ":" + str + ";id:" + getSlotId());
        this.z = 0;
        this.y = System.currentTimeMillis() - this.D;
        JSONObject jSONObject = new JSONObject();
        this.A = jSONObject;
        try {
            jSONObject.put("code", i);
            this.A.put(NotificationCompat.CATEGORY_MESSAGE, str);
        } catch (JSONException unused) {
        }
        if (getType().equals(Const.TYPE_GDT) && i == 6000) {
            this.B = getType() + ":" + i + Operators.BRACKET_START_STR + str + Operators.BRACKET_END_STR;
            return;
        }
        if (getType().equals(Const.TYPE_BD) && i == -1) {
            this.B = getType() + ":" + str;
            return;
        }
        this.B = getType() + ":" + i;
    }

    protected void d(int i) {
        this.z = i;
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOL
    public int getAdStatus() {
        return this.z;
    }

    @Override // io.dcloud.sdk.core.module.DCBaseAOL
    public void startLoadTime() {
        this.D = System.currentTimeMillis();
    }

    protected final long t() {
        return this.y;
    }

    protected void u() {
        this.y = System.currentTimeMillis() - this.D;
    }

    public void v() {
        b3.b("uniAD", getDCloudId() + ":" + getType() + ":success;id:" + getSlotId());
        this.z = 1;
        this.y = System.currentTimeMillis() - this.D;
    }
}
