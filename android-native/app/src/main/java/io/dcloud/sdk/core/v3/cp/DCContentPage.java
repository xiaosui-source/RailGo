package io.dcloud.sdk.core.v3.cp;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import io.dcloud.p.j0;
import io.dcloud.p.v2;
import io.dcloud.sdk.core.entry.DCloudAOLSlot;
import io.dcloud.sdk.core.interfaces.AOLLoader;
import io.dcloud.sdk.core.util.AOLErrorUtil;
import io.dcloud.sdk.core.v3.base.DCBaseAOL;
import org.json.JSONArray;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class DCContentPage extends DCBaseAOL implements AOLLoader.ContentPageVideoListener {
    private j0 b;
    private DCContentPageVideoListener c;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class ContentPageItem {
        private String a;
        private String b;
        private String c;
        private String d;
        private String e;

        public String getDuration() {
            return this.c;
        }

        public String getErrorCode() {
            return this.d;
        }

        public String getErrorMsg() {
            return this.e;
        }

        public String getId() {
            return this.a;
        }

        public String getType() {
            return this.b;
        }

        public void setDuration(String str) {
            this.c = str;
        }

        public void setErrorCode(String str) {
            this.d = str;
        }

        public void setErrorMsg(String str) {
            this.e = str;
        }

        public void setId(String str) {
            this.a = str;
        }

        public void setType(String str) {
            this.b = str;
        }
    }

    public DCContentPage(Activity activity) {
        super(activity);
        this.b = new j0(activity, 14);
    }

    public Fragment getContentPage() {
        j0 j0Var = this.b;
        if (j0Var == null) {
            return null;
        }
        return j0Var.s();
    }

    @Deprecated
    public void load(DCloudAOLSlot dCloudAOLSlot, final DCContentPageLoadListener dCContentPageLoadListener) throws ClassNotFoundException {
        if (getContext() == null || dCloudAOLSlot == null) {
            if (dCContentPageLoadListener != null) {
                dCContentPageLoadListener.onError(-5014, AOLErrorUtil.getErrorMsg(-5014), null);
                return;
            }
            return;
        }
        j0 j0Var = this.b;
        if (j0Var != null) {
            j0Var.a(dCloudAOLSlot, new v2() { // from class: io.dcloud.sdk.core.v3.cp.DCContentPage.1
                @Override // io.dcloud.p.v2
                public void onError(int i, String str, JSONArray jSONArray) {
                    dCContentPageLoadListener.onError(i, str, jSONArray);
                }

                @Override // io.dcloud.p.v2
                public void onLoaded() {
                    dCContentPageLoadListener.onContentPageLoad();
                }
            });
        } else if (dCContentPageLoadListener != null) {
            dCContentPageLoadListener.onError(-5015, AOLErrorUtil.getErrorMsg(-5015), null);
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onClick() {
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onClose() {
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.ContentPageVideoListener
    public void onComplete(ContentPageItem contentPageItem) {
        DCContentPageVideoListener dCContentPageVideoListener = this.c;
        if (dCContentPageVideoListener != null) {
            dCContentPageVideoListener.onComplete(contentPageItem);
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.ContentPageVideoListener
    public void onError(ContentPageItem contentPageItem) {
        DCContentPageVideoListener dCContentPageVideoListener = this.c;
        if (dCContentPageVideoListener != null) {
            dCContentPageVideoListener.onError(contentPageItem);
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onPaidGet(long j, String str, int i) {
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.ContentPageVideoListener
    public void onPause(ContentPageItem contentPageItem) {
        DCContentPageVideoListener dCContentPageVideoListener = this.c;
        if (dCContentPageVideoListener != null) {
            dCContentPageVideoListener.onPause(contentPageItem);
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.ContentPageVideoListener
    public void onResume(ContentPageItem contentPageItem) {
        DCContentPageVideoListener dCContentPageVideoListener = this.c;
        if (dCContentPageVideoListener != null) {
            dCContentPageVideoListener.onResume(contentPageItem);
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onShow() {
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onShowError(int i, String str) {
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onSkip() {
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.ContentPageVideoListener
    public void onStart(ContentPageItem contentPageItem) {
        DCContentPageVideoListener dCContentPageVideoListener = this.c;
        if (dCContentPageVideoListener != null) {
            dCContentPageVideoListener.onStart(contentPageItem);
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VAOLInteractionListener
    public void onVideoPlayEnd() {
    }

    public void setContentPageVideoListener(DCContentPageVideoListener dCContentPageVideoListener) {
        this.c = dCContentPageVideoListener;
        j0 j0Var = this.b;
        if (j0Var != null) {
            j0Var.a(this);
        }
    }

    public void show(Activity activity) {
        j0 j0Var = this.b;
        if (j0Var != null) {
            j0Var.a(activity);
        }
    }
}
