package io.dcloud.api.custom.type.draw;

import io.dcloud.api.custom.type.feed.UniAdCustomNativeAd;
import io.dcloud.p.w1;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public abstract class UniAdCustomDrawAd extends UniAdCustomNativeAd {
    public void onVideoPlayEnd() {
        w1 w1Var = this.a;
        if (w1Var instanceof w1.a) {
            ((w1.a) w1Var).onVideoPlayEnd();
        }
    }

    public void onVideoPlayError() {
        w1 w1Var = this.a;
        if (w1Var instanceof w1.a) {
            ((w1.a) w1Var).a();
        }
    }

    public void onVideoPlayPause() {
        w1 w1Var = this.a;
        if (w1Var instanceof w1.a) {
            ((w1.a) w1Var).c();
        }
    }

    public void onVideoPlayResume() {
        w1 w1Var = this.a;
        if (w1Var instanceof w1.a) {
            ((w1.a) w1Var).i();
        }
    }

    public void onVideoPlayStart() {
        w1 w1Var = this.a;
        if (w1Var instanceof w1.a) {
            ((w1.a) w1Var).e();
        }
    }
}
