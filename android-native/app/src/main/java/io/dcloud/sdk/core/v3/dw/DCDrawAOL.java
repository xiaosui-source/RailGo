package io.dcloud.sdk.core.v3.dw;

import android.app.Activity;
import android.view.View;
import io.dcloud.p.i1;
import io.dcloud.sdk.core.interfaces.AOLLoader;
import io.dcloud.sdk.core.v3.fd.DCFeedAOL;
import io.dcloud.sdk.core.v3.fd.DCFeedAOLListener;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class DCDrawAOL extends DCFeedAOL implements AOLLoader.DrawAOLInteractionListener {
    public DCDrawAOL(i1 i1Var) {
        super(i1Var);
    }

    public View getDrawAOLView(Activity activity) {
        return getFeedAOLView(activity);
    }

    @Override // io.dcloud.sdk.core.v3.fd.DCFeedAOL, io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public boolean isExpressAd() {
        return true;
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.DrawAOLInteractionListener
    public void onEnd() {
        DCFeedAOLListener dCFeedAOLListener = this.b;
        if (dCFeedAOLListener instanceof DCDrawAOLListener) {
            ((DCDrawAOLListener) dCFeedAOLListener).onEnd();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.DrawAOLInteractionListener
    public void onError() {
        DCFeedAOLListener dCFeedAOLListener = this.b;
        if (dCFeedAOLListener instanceof DCDrawAOLListener) {
            dCFeedAOLListener.onShowError();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.DrawAOLInteractionListener
    public void onPause() {
        DCFeedAOLListener dCFeedAOLListener = this.b;
        if (dCFeedAOLListener instanceof DCDrawAOLListener) {
            ((DCDrawAOLListener) dCFeedAOLListener).onPause();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.DrawAOLInteractionListener
    public void onResume() {
        DCFeedAOLListener dCFeedAOLListener = this.b;
        if (dCFeedAOLListener instanceof DCDrawAOLListener) {
            ((DCDrawAOLListener) dCFeedAOLListener).onResume();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.DrawAOLInteractionListener
    public void onStart() {
        DCFeedAOLListener dCFeedAOLListener = this.b;
        if (dCFeedAOLListener instanceof DCDrawAOLListener) {
            ((DCDrawAOLListener) dCFeedAOLListener).onStart();
        }
    }

    public void setDrawAOLListener(DCDrawAOLListener dCDrawAOLListener) {
        this.b = dCDrawAOLListener;
    }
}
