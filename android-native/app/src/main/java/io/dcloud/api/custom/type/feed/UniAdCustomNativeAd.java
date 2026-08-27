package io.dcloud.api.custom.type.feed;

import android.view.View;
import io.dcloud.p.w1;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public abstract class UniAdCustomNativeAd {
    protected w1 a;
    private int b = 0;

    public void a(w1 w1Var) {
        this.a = w1Var;
        render();
    }

    public abstract void destroy();

    public final int getBidPrice() {
        return this.b;
    }

    public abstract View getNativeAd();

    public final void onAdClicked() {
        w1 w1Var = this.a;
        if (w1Var != null) {
            w1Var.onAdClicked();
        }
    }

    public final void onAdClosed(String str) {
        w1 w1Var = this.a;
        if (w1Var != null) {
            w1Var.a(str);
        }
    }

    public final void onAdShow() {
        w1 w1Var = this.a;
        if (w1Var != null) {
            w1Var.onAdShow();
        }
    }

    public void onBidFail(int i, int i2) {
    }

    public void onBidSuccess(int i, int i2) {
    }

    public void onRenderFail(int i, String str) {
        w1 w1Var = this.a;
        if (w1Var != null) {
            w1Var.onRenderFail();
        }
    }

    public void onRenderSuccess() {
        w1 w1Var = this.a;
        if (w1Var != null) {
            w1Var.onRenderSuccess();
        }
    }

    public final void onShowError(int i, String str) {
        w1 w1Var = this.a;
        if (w1Var != null) {
            w1Var.onShowError(i, str);
        }
    }

    public abstract void render();

    public final void setBidPrice(int i) {
        this.b = i;
    }
}
