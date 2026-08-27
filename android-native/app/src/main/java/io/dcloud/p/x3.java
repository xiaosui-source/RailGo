package io.dcloud.p;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import io.dcloud.sdk.core.module.DCBaseAOL;
import io.dcloud.sdk.core.util.Const;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class x3 {
    private boolean a = false;

    protected abstract Activity a();

    protected void a(int i, final String str, String str2, String str3, JSONArray jSONArray, long j) {
        if (jSONArray == null || jSONArray.length() == 0 || i == 14) {
            return;
        }
        this.a = true;
        if (b()) {
            final HashMap map = new HashMap();
            map.put("type", i != 1 ? i != 7 ? i != 15 ? i != 4 ? i != 5 ? i != 9 ? i != 10 ? "" : "draw_flow" : "rewarded" : "video_paste" : "template" : "interstitial" : "full_screen_video" : "splash");
            map.put("adpid", str);
            map.put("ord", str2);
            if (!TextUtils.isEmpty(str3)) {
                map.put("ext", str3);
            }
            map.put("rsp", jSONArray);
            map.put("tid", 60);
            map.put("lt", Long.valueOf(j));
            w4.a().a(new Runnable() { // from class: io.dcloud.p.x3$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() throws UnsupportedEncodingException {
                    this.f$0.a(str, map);
                }
            });
        }
    }

    protected void b(Activity activity, DCBaseAOL dCBaseAOL) {
        b3.a("on ad show");
        if (dCBaseAOL == null) {
            return;
        }
        if (dCBaseAOL.getSubAd() != null) {
            dCBaseAOL = dCBaseAOL.getSubAd();
        }
        DCBaseAOL dCBaseAOL2 = dCBaseAOL;
        a(activity, 40, dCBaseAOL2, dCBaseAOL2.q(), String.valueOf(dCBaseAOL2.getBiddingECPM()), m.b(dCBaseAOL2), m.a(dCBaseAOL2));
    }

    protected abstract boolean b();

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str, HashMap map) throws UnsupportedEncodingException {
        c0.a(a(), r0.d().b().getAppId(), r0.d().b().getAdId(), 60, str, map);
    }

    private void a(final Context context, final int i, final DCBaseAOL dCBaseAOL, final String str, final String str2, final String str3, final String str4) throws JSONException {
        if (i == 40) {
            m.a(context, dCBaseAOL.getDCloudId(), dCBaseAOL.getType());
        }
        final HashMap map = new HashMap();
        if (!TextUtils.isEmpty(dCBaseAOL.p())) {
            map.put("ext", dCBaseAOL.p());
        }
        w4.a().a(new Runnable() { // from class: io.dcloud.p.x3$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() throws NoSuchMethodException, SecurityException, UnsupportedEncodingException {
                x3.a(context, dCBaseAOL, i, map, str, str2, str3, str4);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Context context, DCBaseAOL dCBaseAOL, int i, HashMap map, String str, String str2, String str3, String str4) throws NoSuchMethodException, SecurityException, UnsupportedEncodingException {
        c0.a(context, dCBaseAOL.n(), dCBaseAOL.getSlotId(), dCBaseAOL.getTid(), r0.d().b().getAppId(), r0.d().b().getAdId(), i, dCBaseAOL.getDCloudId(), map, str, str2, str3, str4);
    }

    protected void a(Activity activity, DCBaseAOL dCBaseAOL) {
        b3.a("on ad click");
        if (dCBaseAOL == null) {
            return;
        }
        if (dCBaseAOL.getSubAd() != null) {
            dCBaseAOL = dCBaseAOL.getSubAd();
        }
        DCBaseAOL dCBaseAOL2 = dCBaseAOL;
        a(activity, 41, dCBaseAOL2, null, null, m.b(dCBaseAOL2), m.a(dCBaseAOL2));
    }

    protected void a(final Activity activity, final DCBaseAOL dCBaseAOL, long j, final String str, final int i) {
        if (dCBaseAOL != null && Const.TYPE_GG.equals(dCBaseAOL.getType())) {
            final float f = j / 1000000.0f;
            b3.a("on ad paid");
            w4.a().a(new Runnable() { // from class: io.dcloud.p.x3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws UnsupportedEncodingException {
                    x3.a(activity, dCBaseAOL, f, str, i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Activity activity, DCBaseAOL dCBaseAOL, float f, String str, int i) throws UnsupportedEncodingException {
        c0.a(activity, r0.d().b().getAppId(), dCBaseAOL.getDCloudId(), r0.d().b().getAdId(), dCBaseAOL.n(), dCBaseAOL.getSlotId(), String.format(Locale.ENGLISH, "%f", Float.valueOf(f)), str, i, dCBaseAOL.q());
    }
}
