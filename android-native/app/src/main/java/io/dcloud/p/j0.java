package io.dcloud.p;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import io.dcloud.sdk.core.entry.DCloudAOLSlot;
import io.dcloud.sdk.core.interfaces.AOLLoader;
import io.dcloud.sdk.core.module.DCBaseAOL;
import io.dcloud.sdk.core.v3.cp.DCContentPage;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class j0 extends a5 implements AOLLoader.ContentPageVideoListener {
    public j0(Activity activity, int i) {
        super(activity, i);
    }

    @Override // io.dcloud.p.a5
    public void a(DCloudAOLSlot dCloudAOLSlot, v2 v2Var) throws ClassNotFoundException {
        try {
            Class.forName("com.kwad.sdk.api.KsContentPage");
            super.a(dCloudAOLSlot, v2Var);
        } catch (ClassNotFoundException unused) {
            if (v2Var != null) {
                v2Var.onError(-5050, "当前环境没有快手内容联盟SDK", null);
            }
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.ContentPageVideoListener
    public void onComplete(DCContentPage.ContentPageItem contentPageItem) {
        AOLLoader.VAOLInteractionListener vAOLInteractionListener = this.r;
        if (vAOLInteractionListener instanceof AOLLoader.ContentPageVideoListener) {
            ((AOLLoader.ContentPageVideoListener) vAOLInteractionListener).onComplete(contentPageItem);
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.ContentPageVideoListener
    public void onError(DCContentPage.ContentPageItem contentPageItem) {
        AOLLoader.VAOLInteractionListener vAOLInteractionListener = this.r;
        if (vAOLInteractionListener instanceof AOLLoader.ContentPageVideoListener) {
            ((AOLLoader.ContentPageVideoListener) vAOLInteractionListener).onError(contentPageItem);
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.ContentPageVideoListener
    public void onPause(DCContentPage.ContentPageItem contentPageItem) {
        AOLLoader.VAOLInteractionListener vAOLInteractionListener = this.r;
        if (vAOLInteractionListener instanceof AOLLoader.ContentPageVideoListener) {
            ((AOLLoader.ContentPageVideoListener) vAOLInteractionListener).onPause(contentPageItem);
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.ContentPageVideoListener
    public void onResume(DCContentPage.ContentPageItem contentPageItem) {
        AOLLoader.VAOLInteractionListener vAOLInteractionListener = this.r;
        if (vAOLInteractionListener instanceof AOLLoader.ContentPageVideoListener) {
            ((AOLLoader.ContentPageVideoListener) vAOLInteractionListener).onResume(contentPageItem);
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.ContentPageVideoListener
    public void onStart(DCContentPage.ContentPageItem contentPageItem) {
        AOLLoader.VAOLInteractionListener vAOLInteractionListener = this.r;
        if (vAOLInteractionListener instanceof AOLLoader.ContentPageVideoListener) {
            ((AOLLoader.ContentPageVideoListener) vAOLInteractionListener).onStart(contentPageItem);
        }
    }

    public Fragment s() {
        DCBaseAOL dCBaseAOL = this.t;
        if (dCBaseAOL != null) {
            try {
                return (Fragment) dCBaseAOL.getClass().getDeclaredMethod("getContentPage", null).invoke(this.t, null);
            } catch (Exception unused) {
            }
        }
        return null;
    }
}
