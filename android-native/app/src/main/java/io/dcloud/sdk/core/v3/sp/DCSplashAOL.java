package io.dcloud.sdk.core.v3.sp;

import android.app.Activity;
import android.view.ViewGroup;
import io.dcloud.p.j4;
import io.dcloud.p.m;
import io.dcloud.p.v2;
import io.dcloud.sdk.core.entry.SplashAOLConfig;
import io.dcloud.sdk.core.interfaces.AOLLoader;
import io.dcloud.sdk.core.util.AOLErrorUtil;
import io.dcloud.sdk.core.v3.base.DCBaseAOL;
import java.lang.reflect.InvocationTargetException;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class DCSplashAOL extends DCBaseAOL implements AOLLoader.VAOLInteractionListener {
    j4 b;
    private DCSplashAOLListener c;

    public DCSplashAOL(Activity activity) {
        super(activity);
        this.b = new j4(activity, 1);
    }

    public boolean isSplashOpen() {
        return m.d(getContext());
    }

    public boolean isValid() {
        j4 j4Var = this.b;
        return j4Var != null && j4Var.l();
    }

    public void load(SplashAOLConfig splashAOLConfig, final DCSplashAOLLoadListener dCSplashAOLLoadListener) {
        if (getContext() == null || splashAOLConfig == null) {
            if (dCSplashAOLLoadListener != null) {
                dCSplashAOLLoadListener.onError(-5014, AOLErrorUtil.getErrorMsg(-5014), null);
                return;
            }
            return;
        }
        j4 j4Var = this.b;
        if (j4Var != null) {
            j4Var.a(splashAOLConfig, new v2() { // from class: io.dcloud.sdk.core.v3.sp.DCSplashAOL.1
                public void getConfig(JSONObject jSONObject) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    try {
                        dCSplashAOLLoadListener.getClass().getDeclaredMethod("getConfig", JSONObject.class).invoke(dCSplashAOLLoadListener, jSONObject);
                    } catch (Exception unused) {
                    }
                }

                @Override // io.dcloud.p.v2
                public void onError(int i, String str, JSONArray jSONArray) {
                    dCSplashAOLLoadListener.onError(i, str, jSONArray);
                }

                @Override // io.dcloud.p.v2
                public void onLoaded() {
                    dCSplashAOLLoadListener.onSplashAOLLoad();
                }
            });
        } else if (dCSplashAOLLoadListener != null) {
            dCSplashAOLLoadListener.onError(-5015, AOLErrorUtil.getErrorMsg(-5015), null);
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onClick() {
        DCSplashAOLListener dCSplashAOLListener = this.c;
        if (dCSplashAOLListener != null) {
            dCSplashAOLListener.onClick();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onClose() {
        DCSplashAOLListener dCSplashAOLListener = this.c;
        if (dCSplashAOLListener != null) {
            dCSplashAOLListener.onClose();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onPaidGet(long j, String str, int i) {
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onShow() {
        DCSplashAOLListener dCSplashAOLListener = this.c;
        if (dCSplashAOLListener != null) {
            dCSplashAOLListener.onShow();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onShowError(int i, String str) {
        DCSplashAOLListener dCSplashAOLListener = this.c;
        if (dCSplashAOLListener != null) {
            dCSplashAOLListener.onShowError(i, str);
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onSkip() {
        DCSplashAOLListener dCSplashAOLListener = this.c;
        if (dCSplashAOLListener != null) {
            dCSplashAOLListener.onSkip();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onVideoPlayEnd() {
        DCSplashAOLListener dCSplashAOLListener = this.c;
        if (dCSplashAOLListener != null) {
            dCSplashAOLListener.onVideoPlayEnd();
        }
    }

    public void setSplashAOLListener(DCSplashAOLListener dCSplashAOLListener) {
        this.c = dCSplashAOLListener;
        j4 j4Var = this.b;
        if (j4Var != null) {
            j4Var.a(this);
        }
    }

    public void show(Activity activity) {
    }

    @Deprecated
    public void showIn(ViewGroup viewGroup) {
        j4 j4Var = this.b;
        if (j4Var != null) {
            j4Var.a(viewGroup);
        }
    }

    public void show(ViewGroup viewGroup) {
        showIn(viewGroup);
    }
}
