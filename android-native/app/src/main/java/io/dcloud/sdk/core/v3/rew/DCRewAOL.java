package io.dcloud.sdk.core.v3.rew;

import android.app.Activity;
import io.dcloud.p.v2;
import io.dcloud.p.y3;
import io.dcloud.sdk.core.entry.DCloudAOLSlot;
import io.dcloud.sdk.core.interfaces.AOLLoader;
import io.dcloud.sdk.core.util.AOLErrorUtil;
import io.dcloud.sdk.core.v3.base.DCBaseAOL;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class DCRewAOL extends DCBaseAOL implements AOLLoader.RewVAOLInteractionListener {
    private y3 b;
    private DCRewAOLListener c;

    public DCRewAOL(Activity activity) {
        super(activity);
        this.b = new y3(activity);
    }

    public void destroy() {
        y3 y3Var = this.b;
        if (y3Var != null) {
            y3Var.destroy();
        }
    }

    public void getRetryRewardResult(AOLLoader.GetConvertResultListener getConvertResultListener) {
        this.b.a(getConvertResultListener);
    }

    public String getType() {
        y3 y3Var = this.b;
        return y3Var == null ? "" : y3Var.getType();
    }

    public boolean isValid() {
        y3 y3Var = this.b;
        if (y3Var != null) {
            return y3Var.l();
        }
        return false;
    }

    public void load(DCloudAOLSlot dCloudAOLSlot, final DCRewAOLLoadListener dCRewAOLLoadListener) {
        if (getContext() == null || dCloudAOLSlot == null) {
            if (dCRewAOLLoadListener != null) {
                dCRewAOLLoadListener.onError(-5014, AOLErrorUtil.getErrorMsg(-5014), null);
                return;
            }
            return;
        }
        y3 y3Var = this.b;
        if (y3Var != null) {
            y3Var.a(dCloudAOLSlot, new v2() { // from class: io.dcloud.sdk.core.v3.rew.DCRewAOL.1
                @Override // io.dcloud.p.v2
                public void onError(int i, String str, JSONArray jSONArray) {
                    DCRewAOLLoadListener dCRewAOLLoadListener2 = dCRewAOLLoadListener;
                    if (dCRewAOLLoadListener2 != null) {
                        dCRewAOLLoadListener2.onError(i, str, jSONArray);
                    }
                }

                @Override // io.dcloud.p.v2
                public void onLoaded() {
                    DCRewAOLLoadListener dCRewAOLLoadListener2 = dCRewAOLLoadListener;
                    if (dCRewAOLLoadListener2 != null) {
                        dCRewAOLLoadListener2.onRewardAOLLoad();
                    }
                }
            });
        } else if (dCRewAOLLoadListener != null) {
            dCRewAOLLoadListener.onError(-5015, AOLErrorUtil.getErrorMsg(-5015), null);
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onClick() {
        DCRewAOLListener dCRewAOLListener = this.c;
        if (dCRewAOLListener != null) {
            dCRewAOLListener.onClick();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onClose() {
        DCRewAOLListener dCRewAOLListener = this.c;
        if (dCRewAOLListener != null) {
            dCRewAOLListener.onClose();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onPaidGet(long j, String str, int i) {
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.RewVAOLInteractionListener
    public void onReward(JSONObject jSONObject) {
        DCRewAOLListener dCRewAOLListener = this.c;
        if (dCRewAOLListener != null) {
            dCRewAOLListener.onReward(jSONObject);
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onShow() {
        DCRewAOLListener dCRewAOLListener = this.c;
        if (dCRewAOLListener != null) {
            dCRewAOLListener.onShow();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onShowError(int i, String str) {
        DCRewAOLListener dCRewAOLListener = this.c;
        if (dCRewAOLListener != null) {
            dCRewAOLListener.onShowError(i, str);
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onSkip() {
        DCRewAOLListener dCRewAOLListener = this.c;
        if (dCRewAOLListener != null) {
            dCRewAOLListener.onSkip();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onVideoPlayEnd() {
        DCRewAOLListener dCRewAOLListener = this.c;
        if (dCRewAOLListener != null) {
            dCRewAOLListener.onVideoPlayEnd();
        }
    }

    public void requestRetryRewardConvert(AOLLoader.RequestConvertResultListener requestConvertResultListener) {
        this.b.b(requestConvertResultListener);
    }

    public void setRewardAOLListener(DCRewAOLListener dCRewAOLListener) {
        this.c = dCRewAOLListener;
        y3 y3Var = this.b;
        if (y3Var != null) {
            y3Var.a(this);
        }
    }

    public void show(Activity activity) {
        y3 y3Var = this.b;
        if (y3Var != null) {
            y3Var.a(activity);
        }
    }
}
