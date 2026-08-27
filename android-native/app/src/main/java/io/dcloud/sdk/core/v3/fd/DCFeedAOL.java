package io.dcloud.sdk.core.v3.fd;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import io.dcloud.p.i1;
import io.dcloud.sdk.core.interfaces.AOLLoader;
import io.dcloud.sdk.core.util.MainHandlerUtil;
import java.util.List;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class DCFeedAOL implements AOLLoader.FeedAOLInteractionListener, AOLLoader.NativeAOLInteractionListener {
    private i1 a;
    protected DCFeedAOLListener b;

    public DCFeedAOL(i1 i1Var) {
        this.a = i1Var;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a() {
        this.a.p();
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public void destroy() {
        i1 i1Var = this.a;
        if (i1Var != null) {
            i1Var.destroy();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLListener
    public void dislike(Activity activity) {
        this.a.dislike(activity);
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public Bitmap getAdLogo() {
        return this.a.getAdLogo();
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public AOLLoader.AppInfo getAppInfo() {
        return this.a.getAppInfo();
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public String getButtonText() {
        return this.a.getButtonText();
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public String getDescription() {
        return this.a.getDescription();
    }

    public View getFeedAOLView(Activity activity) {
        i1 i1Var = this.a;
        if (i1Var != null) {
            return i1Var.a(activity);
        }
        return null;
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public String getIconUrl() {
        return this.a.getIconUrl();
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public List<AOLLoader.AdImage> getImageList() {
        return this.a.getImageList();
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public int getInteractionType() {
        return this.a.getInteractionType();
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public int getMaterialType() {
        return this.a.getMaterialType();
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public String getSource() {
        return this.a.getSource();
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public String getTitle() {
        return this.a.getTitle();
    }

    public String getType() {
        i1 i1Var = this.a;
        return i1Var != null ? i1Var.getType() : "";
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public double getVideoDuration() {
        return this.a.getVideoDuration();
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener, io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLListener
    public View getVideoView(Activity activity) {
        if (Thread.currentThread() == MainHandlerUtil.getMainHandler().getLooper().getThread()) {
            return this.a.getVideoView(activity);
        }
        throw new RuntimeException("getVideoView must be called on the main thread");
    }

    public boolean isExpressAd() {
        return this.a.isExpressAd();
    }

    public boolean isValid() {
        i1 i1Var = this.a;
        return i1Var != null && i1Var.c();
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.FeedAOLInteractionListener
    public void onClicked() {
        DCFeedAOLListener dCFeedAOLListener = this.b;
        if (dCFeedAOLListener != null) {
            dCFeedAOLListener.onClick();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.FeedAOLInteractionListener
    public void onClosed(String str) {
        DCFeedAOLListener dCFeedAOLListener = this.b;
        if (dCFeedAOLListener != null) {
            dCFeedAOLListener.onClosed(str);
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.FeedAOLInteractionListener
    public void onPaidGet(long j, String str, int i) {
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.FeedAOLInteractionListener
    public void onRenderFail() {
        DCFeedAOLListener dCFeedAOLListener = this.b;
        if (dCFeedAOLListener != null) {
            dCFeedAOLListener.onRenderFail();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.FeedAOLInteractionListener
    public void onRenderSuccess() {
        DCFeedAOLListener dCFeedAOLListener = this.b;
        if (dCFeedAOLListener != null) {
            dCFeedAOLListener.onRenderSuccess();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.FeedAOLInteractionListener
    public void onShow() {
        DCFeedAOLListener dCFeedAOLListener = this.b;
        if (dCFeedAOLListener != null) {
            dCFeedAOLListener.onShow();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.FeedAOLInteractionListener
    public void onShowError() {
        DCFeedAOLListener dCFeedAOLListener = this.b;
        if (dCFeedAOLListener != null) {
            dCFeedAOLListener.onShowError();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLListener
    public void registerViewForInteraction(Activity activity, FrameLayout frameLayout, List<View> list, List<View> list2, List<ImageView> list3) {
        this.a.a(this);
        this.a.registerViewForInteraction(activity, frameLayout, list, list2, list3);
    }

    public void render() {
        i1 i1Var = this.a;
        if (i1Var != null) {
            i1Var.a(this);
            MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.sdk.core.v3.fd.DCFeedAOL$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.a();
                }
            });
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener, io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLListener
    public void setAppDownloadListener(AOLLoader.AppDownloadListener appDownloadListener) {
        this.a.setAppDownloadListener(appDownloadListener);
    }

    public void setFeedAOLListener(DCFeedAOLListener dCFeedAOLListener) {
        this.b = dCFeedAOLListener;
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener, io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLListener
    public void setVideoAdListener(AOLLoader.VideoAdListener videoAdListener) {
        this.a.setVideoAdListener(videoAdListener);
    }
}
