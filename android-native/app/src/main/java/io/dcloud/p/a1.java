package io.dcloud.p;

import android.app.Activity;
import io.dcloud.sdk.core.interfaces.AOLLoader;
import io.dcloud.sdk.core.module.DCBaseAOL;
import io.dcloud.sdk.core.util.MainHandlerUtil;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class a1 extends i1 implements AOLLoader.DrawAOLInteractionListener {
    public a1(DCBaseAOL dCBaseAOL, Activity activity) {
        super(dCBaseAOL, activity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void q() {
        AOLLoader.FeedAOLInteractionListener feedAOLInteractionListener = this.e;
        if (feedAOLInteractionListener instanceof AOLLoader.DrawAOLInteractionListener) {
            ((AOLLoader.DrawAOLInteractionListener) feedAOLInteractionListener).onEnd();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void r() {
        AOLLoader.FeedAOLInteractionListener feedAOLInteractionListener = this.e;
        if (feedAOLInteractionListener instanceof AOLLoader.DrawAOLInteractionListener) {
            feedAOLInteractionListener.onShowError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void s() {
        AOLLoader.FeedAOLInteractionListener feedAOLInteractionListener = this.e;
        if (feedAOLInteractionListener instanceof AOLLoader.DrawAOLInteractionListener) {
            ((AOLLoader.DrawAOLInteractionListener) feedAOLInteractionListener).onPause();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void t() {
        AOLLoader.FeedAOLInteractionListener feedAOLInteractionListener = this.e;
        if (feedAOLInteractionListener instanceof AOLLoader.DrawAOLInteractionListener) {
            ((AOLLoader.DrawAOLInteractionListener) feedAOLInteractionListener).onResume();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void u() {
        AOLLoader.FeedAOLInteractionListener feedAOLInteractionListener = this.e;
        if (feedAOLInteractionListener instanceof AOLLoader.DrawAOLInteractionListener) {
            ((AOLLoader.DrawAOLInteractionListener) feedAOLInteractionListener).onStart();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.DrawAOLInteractionListener
    public void onEnd() {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.a1$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.q();
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.DrawAOLInteractionListener
    public void onError() {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.a1$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.r();
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.DrawAOLInteractionListener
    public void onPause() {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.a1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.s();
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.DrawAOLInteractionListener
    public void onResume() {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.a1$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.t();
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.DrawAOLInteractionListener
    public void onStart() {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.a1$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.u();
            }
        });
    }
}
