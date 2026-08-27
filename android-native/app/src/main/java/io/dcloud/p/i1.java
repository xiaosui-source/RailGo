package io.dcloud.p;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.view.PointerIconCompat;
import io.dcloud.feature.barcode2.decoding.CaptureActivityHandler;
import io.dcloud.sdk.core.interfaces.AOLLoader;
import io.dcloud.sdk.core.module.DCBaseAOL;
import io.dcloud.sdk.core.util.MainHandlerUtil;
import java.util.Collections;
import java.util.List;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class i1 extends x3 implements AOLLoader.FeedAOLInteractionListener, AOLLoader.NativeAOLInteractionListener, AOLLoader.AppDownloadListener, AOLLoader.VideoAdListener {
    private final DCBaseAOL b;
    private View c;
    private final Activity d;
    protected AOLLoader.FeedAOLInteractionListener e;
    private SparseArray f;
    private AOLLoader.AppDownloadListener g;
    private AOLLoader.VideoAdListener h;

    public i1(DCBaseAOL dCBaseAOL, Activity activity) {
        this.f = new SparseArray();
        this.b = dCBaseAOL;
        this.d = activity;
        dCBaseAOL.setFeedAdCallback(this);
        this.f = dCBaseAOL.getNativeAdInfo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str) {
        AOLLoader.FeedAOLInteractionListener feedAOLInteractionListener = this.e;
        if (feedAOLInteractionListener != null) {
            feedAOLInteractionListener.onClosed(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d() {
        AOLLoader.FeedAOLInteractionListener feedAOLInteractionListener = this.e;
        if (feedAOLInteractionListener != null) {
            feedAOLInteractionListener.onClicked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e() {
        AOLLoader.AppDownloadListener appDownloadListener = this.g;
        if (appDownloadListener != null) {
            appDownloadListener.onIdle();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void f() {
        AOLLoader.FeedAOLInteractionListener feedAOLInteractionListener = this.e;
        if (feedAOLInteractionListener != null) {
            feedAOLInteractionListener.onRenderFail();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void g() {
        AOLLoader.FeedAOLInteractionListener feedAOLInteractionListener = this.e;
        if (feedAOLInteractionListener != null) {
            feedAOLInteractionListener.onRenderSuccess();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void h() {
        AOLLoader.FeedAOLInteractionListener feedAOLInteractionListener = this.e;
        if (feedAOLInteractionListener != null) {
            feedAOLInteractionListener.onShow();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void j() {
        AOLLoader.FeedAOLInteractionListener feedAOLInteractionListener = this.e;
        if (feedAOLInteractionListener != null) {
            feedAOLInteractionListener.onShowError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void k() {
        AOLLoader.VideoAdListener videoAdListener = this.h;
        if (videoAdListener != null) {
            videoAdListener.onVideoAdComplete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void l() {
        AOLLoader.VideoAdListener videoAdListener = this.h;
        if (videoAdListener != null) {
            videoAdListener.onVideoAdPaused();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void m() {
        AOLLoader.VideoAdListener videoAdListener = this.h;
        if (videoAdListener != null) {
            videoAdListener.onVideoAdResume();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void n() {
        AOLLoader.VideoAdListener videoAdListener = this.h;
        if (videoAdListener != null) {
            videoAdListener.onVideoAdStartPlay();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void o() {
        AOLLoader.VideoAdListener videoAdListener = this.h;
        if (videoAdListener != null) {
            videoAdListener.onVideoLoad();
        }
    }

    @Override // io.dcloud.p.x3
    protected boolean b() {
        return false;
    }

    public boolean c() {
        DCBaseAOL dCBaseAOL = this.b;
        return dCBaseAOL != null && dCBaseAOL.isValid();
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public void destroy() {
        DCBaseAOL dCBaseAOL = this.b;
        if (dCBaseAOL != null) {
            dCBaseAOL.destroy();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLListener
    public void dislike(Activity activity) {
        Object obj = this.b;
        if (obj instanceof AOLLoader.NativeAOLListener) {
            ((AOLLoader.NativeAOLListener) obj).dislike(activity);
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public Bitmap getAdLogo() {
        return null;
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public AOLLoader.AppInfo getAppInfo() {
        Object obj = this.f.get(1006, null);
        if (obj instanceof AOLLoader.AppInfo) {
            return (AOLLoader.AppInfo) obj;
        }
        return null;
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public String getButtonText() {
        return this.f.get(1000, "").toString();
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public String getDescription() {
        return this.f.get(1001, "").toString();
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public String getIconUrl() {
        return this.f.get(1002, "").toString();
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public List getImageList() {
        return (List) this.f.get(PointerIconCompat.TYPE_VERTICAL_TEXT, Collections.EMPTY_LIST);
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public int getInteractionType() {
        return ((Integer) this.f.get(PointerIconCompat.TYPE_CROSSHAIR, 0)).intValue();
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public int getMaterialType() {
        return ((Integer) this.f.get(PointerIconCompat.TYPE_TEXT, 0)).intValue();
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public String getSource() {
        return this.f.get(1003, "").toString();
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public String getTitle() {
        return this.f.get(1004, "").toString();
    }

    public String getType() {
        return this.b != null ? e.b().c(this.b.getType()) : "";
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public double getVideoDuration() {
        return Double.parseDouble(this.f.get(1010, 0).toString());
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener, io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLListener
    public View getVideoView(Activity activity) {
        Object obj = this.b;
        if (obj instanceof AOLLoader.NativeAOLListener) {
            return ((AOLLoader.NativeAOLListener) obj).getVideoView(activity);
        }
        return null;
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener
    public boolean isExpressAd() {
        SparseArray sparseArray = this.f;
        return sparseArray == null || ((Boolean) sparseArray.get(CaptureActivityHandler.CODE_DECODE_portrait, Boolean.TRUE)).booleanValue();
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.FeedAOLInteractionListener
    public void onClicked() {
        a(this.d, this.b);
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.i1$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.d();
            }
        });
        View view = this.c;
        Activity activity = this.d;
        StringBuilder sb = new StringBuilder("4_");
        DCBaseAOL dCBaseAOL = this.b;
        sb.append(dCBaseAOL != null ? dCBaseAOL.getType() : "");
        f4.a(view, activity, sb.toString());
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.FeedAOLInteractionListener
    public void onClosed(final String str) {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.i1$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.a(str);
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.AppDownloadListener
    public void onDownloadActive(final long j, final long j2, final String str, final String str2) {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.i1$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.a(j, j2, str, str2);
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.AppDownloadListener
    public void onDownloadFailed(final long j, final long j2, final String str, final String str2) {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.i1$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.b(j, j2, str, str2);
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.AppDownloadListener
    public void onDownloadFinished(final long j, final String str, final String str2) {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.i1$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.a(j, str, str2);
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.AppDownloadListener
    public void onDownloadPaused(final long j, final long j2, final String str, final String str2) {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.i1$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.c(j, j2, str, str2);
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.AppDownloadListener
    public void onIdle() {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.i1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.e();
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.AppDownloadListener
    public void onInstalled(final String str, final String str2) {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.i1$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.a(str, str2);
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.FeedAOLInteractionListener
    public void onPaidGet(long j, String str, int i) {
        a(a(), this.b, j, str, i);
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VideoAdListener
    public void onProgressUpdate(final long j, final long j2) {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.i1$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.a(j, j2);
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.FeedAOLInteractionListener
    public void onRenderFail() {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.i1$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.f();
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.FeedAOLInteractionListener
    public void onRenderSuccess() {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.i1$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.g();
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.FeedAOLInteractionListener
    public void onShow() {
        b(this.d, this.b);
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.i1$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.h();
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.FeedAOLInteractionListener
    public void onShowError() {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.i1$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.j();
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VideoAdListener
    public void onVideoAdComplete() {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.i1$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.k();
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VideoAdListener
    public void onVideoAdPaused() {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.i1$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.l();
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VideoAdListener
    public void onVideoAdResume() {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.i1$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m();
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VideoAdListener
    public void onVideoAdStartPlay() {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.i1$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.n();
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VideoAdListener
    public void onVideoError(final int i, final String str) {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.i1$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.a(i, str);
            }
        });
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.VideoAdListener
    public void onVideoLoad() {
        MainHandlerUtil.getMainHandler().post(new Runnable() { // from class: io.dcloud.p.i1$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.o();
            }
        });
    }

    public void p() {
        DCBaseAOL dCBaseAOL = this.b;
        if (dCBaseAOL != null) {
            dCBaseAOL.render();
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLListener
    public void registerViewForInteraction(Activity activity, FrameLayout frameLayout, List list, List list2, List list3) {
        this.c = frameLayout;
        Object obj = this.b;
        if (obj instanceof AOLLoader.NativeAOLListener) {
            ((AOLLoader.NativeAOLListener) obj).registerViewForInteraction(activity, frameLayout, list, list2, list3);
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener, io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLListener
    public void setAppDownloadListener(AOLLoader.AppDownloadListener appDownloadListener) {
        Object obj = this.b;
        if (obj instanceof AOLLoader.NativeAOLListener) {
            ((AOLLoader.NativeAOLListener) obj).setAppDownloadListener(this);
            this.g = appDownloadListener;
        }
    }

    @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLInteractionListener, io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLListener
    public void setVideoAdListener(AOLLoader.VideoAdListener videoAdListener) {
        Object obj = this.b;
        if (obj instanceof AOLLoader.NativeAOLListener) {
            ((AOLLoader.NativeAOLListener) obj).setVideoAdListener(this);
            this.h = videoAdListener;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(long j, long j2, String str, String str2) {
        AOLLoader.AppDownloadListener appDownloadListener = this.g;
        if (appDownloadListener != null) {
            appDownloadListener.onDownloadFailed(j, j2, str, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(long j, long j2, String str, String str2) {
        AOLLoader.AppDownloadListener appDownloadListener = this.g;
        if (appDownloadListener != null) {
            appDownloadListener.onDownloadPaused(j, j2, str, str2);
        }
    }

    public View a(Activity activity) {
        DCBaseAOL dCBaseAOL = this.b;
        View expressAdView = dCBaseAOL != null ? dCBaseAOL.getExpressAdView(activity) : null;
        this.c = expressAdView;
        return expressAdView;
    }

    @Override // io.dcloud.p.x3
    protected Activity a() {
        return this.d;
    }

    public void a(AOLLoader.FeedAOLInteractionListener feedAOLInteractionListener) {
        this.e = feedAOLInteractionListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(long j, long j2, String str, String str2) {
        AOLLoader.AppDownloadListener appDownloadListener = this.g;
        if (appDownloadListener != null) {
            appDownloadListener.onDownloadActive(j, j2, str, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(long j, String str, String str2) {
        AOLLoader.AppDownloadListener appDownloadListener = this.g;
        if (appDownloadListener != null) {
            appDownloadListener.onDownloadFinished(j, str, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str, String str2) {
        AOLLoader.AppDownloadListener appDownloadListener = this.g;
        if (appDownloadListener != null) {
            appDownloadListener.onInstalled(str, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, String str) {
        AOLLoader.VideoAdListener videoAdListener = this.h;
        if (videoAdListener != null) {
            videoAdListener.onVideoError(i, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(long j, long j2) {
        AOLLoader.VideoAdListener videoAdListener = this.h;
        if (videoAdListener != null) {
            videoAdListener.onProgressUpdate(j, j2);
        }
    }
}
