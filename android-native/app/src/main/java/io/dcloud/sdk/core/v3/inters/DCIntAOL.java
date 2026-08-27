package io.dcloud.sdk.core.v3.inters;

import android.app.Activity;
import io.dcloud.p.a5;
import io.dcloud.p.v2;
import io.dcloud.p.x4;
import io.dcloud.sdk.core.entry.DCloudAOLSlot;
import io.dcloud.sdk.core.interfaces.AOLLoader;
import io.dcloud.sdk.core.util.AOLErrorUtil;
import io.dcloud.sdk.core.v3.base.DCBaseAOL;
import org.json.JSONArray;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class DCIntAOL extends DCBaseAOL implements AOLLoader.VAOLInteractionListener {
    private final a5 b;
    private DCIntAOLListener c;

    public DCIntAOL(Activity activity) {
        super(activity);
        this.b = new x4(activity, 15);
    }

    public String getType() {
        a5 a5Var = this.b;
        return a5Var == null ? "" : a5Var.getType();
    }

    public boolean isValid() {
        a5 a5Var = this.b;
        if (a5Var != null) {
            return a5Var.l();
        }
        return false;
    }

    public void load(DCloudAOLSlot dCloudAOLSlot, final DCIntAOLLoadListener dCIntAOLLoadListener) {
        if (getContext() == null || dCloudAOLSlot == null) {
            if (dCIntAOLLoadListener != null) {
                dCIntAOLLoadListener.onError(-5014, AOLErrorUtil.getErrorMsg(-5014), null);
                return;
            }
            return;
        }
        a5 a5Var = this.b;
        if (a5Var != null) {
            a5Var.a(dCloudAOLSlot, new v2() { // from class: io.dcloud.sdk.core.v3.inters.DCIntAOL.1
                @Override // io.dcloud.p.v2
                public void onError(int i, String str, JSONArray jSONArray) {
                    DCIntAOLLoadListener dCIntAOLLoadListener2 = dCIntAOLLoadListener;
                    if (dCIntAOLLoadListener2 != null) {
                        dCIntAOLLoadListener2.onError(i, str, jSONArray);
                    }
                }

                @Override // io.dcloud.p.v2
                public void onLoaded() {
                    DCIntAOLLoadListener dCIntAOLLoadListener2 = dCIntAOLLoadListener;
                    if (dCIntAOLLoadListener2 != null) {
                        dCIntAOLLoadListener2.onInterstitialAOLLoad();
                    }
                }
            });
        } else if (dCIntAOLLoadListener != null) {
            dCIntAOLLoadListener.onError(-5015, AOLErrorUtil.getErrorMsg(-5015), null);
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onClick() {
        DCIntAOLListener dCIntAOLListener = this.c;
        if (dCIntAOLListener != null) {
            dCIntAOLListener.onClick();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onClose() {
        DCIntAOLListener dCIntAOLListener = this.c;
        if (dCIntAOLListener != null) {
            dCIntAOLListener.onClose();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onPaidGet(long j, String str, int i) {
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onShow() {
        DCIntAOLListener dCIntAOLListener = this.c;
        if (dCIntAOLListener != null) {
            dCIntAOLListener.onShow();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onShowError(int i, String str) {
        DCIntAOLListener dCIntAOLListener = this.c;
        if (dCIntAOLListener != null) {
            dCIntAOLListener.onShowError(i, str);
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onSkip() {
        DCIntAOLListener dCIntAOLListener = this.c;
        if (dCIntAOLListener != null) {
            dCIntAOLListener.onSkip();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onVideoPlayEnd() {
        DCIntAOLListener dCIntAOLListener = this.c;
        if (dCIntAOLListener != null) {
            dCIntAOLListener.onVideoPlayEnd();
        }
    }

    public void setInterstitialAOLListener(DCIntAOLListener dCIntAOLListener) {
        this.c = dCIntAOLListener;
        a5 a5Var = this.b;
        if (a5Var != null) {
            a5Var.a(this);
        }
    }

    public void show(Activity activity) {
        a5 a5Var = this.b;
        if (a5Var != null) {
            a5Var.a(activity);
        }
    }
}
