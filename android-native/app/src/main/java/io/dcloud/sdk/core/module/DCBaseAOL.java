package io.dcloud.sdk.core.module;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;
import io.dcloud.p.b3;
import io.dcloud.sdk.core.entry.DCloudAOLSlot;
import io.dcloud.sdk.core.interfaces.AOLLoader;
import io.dcloud.sdk.core.util.TidUtil;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class DCBaseAOL {
    private final DCloudAOLSlot a;
    private final Activity b;
    protected int g;
    private String h;
    private String i;
    private String j;
    private String k;
    private int l;
    private String m;
    private String n;
    private int o;
    private boolean p;
    private JSONObject q;
    private int r;
    protected String s;
    protected AOLLoader.VAOLInteractionListener v;
    protected AOLLoader.FeedAOLInteractionListener w;
    private DCBaseAOL x;
    private int c = -1;
    protected int d = -1;
    protected int e = -1;
    private boolean f = false;
    private int t = 1;
    private boolean u = true;

    public DCBaseAOL(DCloudAOLSlot dCloudAOLSlot, Activity activity) {
        this.a = dCloudAOLSlot;
        this.b = activity;
    }

    public void a(boolean z) {
        this.p = z;
    }

    public final void b(String str) {
        this.i = str;
    }

    public void biddingFail(int i, int i2, int i3) {
        b3.b("uniAd", "bidding fail:" + getType() + ",Win:" + i + ",second:" + i2 + ",slot:" + getSlotId());
    }

    public void biddingSuccess(int i, int i2) {
        b3.b("uniAd", "bidding success:" + getType() + ",Win:" + i + ",second:" + i2 + ",slot:" + getSlotId());
    }

    public void c(String str) {
        this.j = str;
    }

    public void d(String str) {
        this.k = str;
    }

    public abstract void destroy();

    public void e(String str) {
        this.m = str;
    }

    public void f(String str) {
        this.n = str;
    }

    public final void g(String str) {
        this.h = str;
    }

    public int getAcpt() {
        return this.r;
    }

    public Activity getActivity() {
        return this.b;
    }

    public int getAdStatus() {
        return -1;
    }

    public int getAdType() {
        return this.g;
    }

    public int getBiddingECPM() {
        return this.c;
    }

    public String getDCloudId() {
        return this.a.getAdpid();
    }

    public View getExpressAdView(Activity activity) {
        return null;
    }

    public AOLLoader.FeedAOLInteractionListener getFeedAdCallback() {
        return this.w;
    }

    public int getFeedType() {
        return this.l;
    }

    public int getIntersType() {
        return this.t;
    }

    public String getMiniRequestType() {
        return this.m;
    }

    public int getMiniType() {
        return this.o;
    }

    public SparseArray<Object> getNativeAdInfo() {
        return null;
    }

    public JSONObject getParams() {
        return this.q;
    }

    public String getPath() {
        return this.n;
    }

    public DCloudAOLSlot getSlot() {
        return this.a;
    }

    public String getSlotId() {
        return this.h;
    }

    public DCBaseAOL getSubAd() {
        return this.x;
    }

    public String getTid() {
        return TidUtil.getTid(getType(), getAdType());
    }

    public String getType() {
        return this.s;
    }

    public AOLLoader.VAOLInteractionListener getVideoAdCallback() {
        return this.v;
    }

    public boolean isEnd() {
        return this.p;
    }

    public boolean isExpressAd() {
        return this.u;
    }

    public boolean isSlotSupportBidding() {
        return this.f;
    }

    public abstract boolean isValid();

    public String n() {
        return this.i;
    }

    protected String o() {
        return this.j;
    }

    public String p() {
        return this.a.getEI();
    }

    public String q() {
        return this.k;
    }

    public int r() {
        return this.d;
    }

    public void render() {
    }

    public boolean s() {
        return false;
    }

    public final void setBiddingECPM(int i) {
        if (i > 0) {
            b3.c(getType() + " current cpm:" + i);
            this.c = i;
        }
    }

    public void setExpressAd(boolean z) {
        this.u = z;
    }

    public final void setFeedAdCallback(AOLLoader.FeedAOLInteractionListener feedAOLInteractionListener) {
        this.w = feedAOLInteractionListener;
    }

    public void setFeedType(int i) {
        this.l = i;
    }

    public void setSubAd(DCBaseAOL dCBaseAOL) {
        this.x = dCBaseAOL;
    }

    public final void setVideoAdCallback(AOLLoader.VAOLInteractionListener vAOLInteractionListener) {
        this.v = vAOLInteractionListener;
    }

    public void startLoadTime() {
    }

    public void a(JSONObject jSONObject) {
        this.q = jSONObject;
    }

    public void b(boolean z) {
        this.f = z;
    }

    public void c(int i) {
        this.o = i;
    }

    public void a(int i) {
        this.r = i;
    }

    public void b(int i) {
        this.t = i;
    }

    public void a(AOLLoader.GetConvertResultListener getConvertResultListener) {
        if (getConvertResultListener != null) {
            getConvertResultListener.onError(-5100, "type:" + getType() + ";code:-1;message:暂无缓存任务");
        }
    }

    public void a(AOLLoader.RequestConvertResultListener requestConvertResultListener) {
        if (requestConvertResultListener != null) {
            requestConvertResultListener.onError(-5100, "type:" + getType() + ";code:-1;message:暂无缓存任务");
        }
    }
}
