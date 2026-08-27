package io.dcloud.sdk.core.interfaces;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import io.dcloud.sdk.core.DCloudAOLManager;
import io.dcloud.sdk.core.entry.DCRetryRewardEntry;
import io.dcloud.sdk.core.v3.cp.DCContentPage;
import java.util.List;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public interface AOLLoader {

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface AdImage {
        int getHeight();

        String getUrl();

        int getWidth();

        boolean isValid();
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface AdInteractionListener {
        void onAdClicked();

        void onAdShow();
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface AdVideo {
        String getVideoUrl();

        void reportVideoAutoStart();

        void reportVideoBreak(long j);

        void reportVideoContinue(long j);

        void reportVideoError(long j, int i, int i2);

        void reportVideoFinish();

        void reportVideoPause(long j);

        void reportVideoStart();

        void reportVideoStartError(int i, int i2);
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface AppDownloadListener {
        void onDownloadActive(long j, long j2, String str, String str2);

        void onDownloadFailed(long j, long j2, String str, String str2);

        void onDownloadFinished(long j, String str, String str2);

        void onDownloadPaused(long j, long j2, String str, String str2);

        void onIdle();

        void onInstalled(String str, String str2);
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface AppInfo {
        String getApkSize();

        String getAppDownloadCount();

        String getAppName();

        String getAppPackageName();

        String getAppScore();

        String getAppVersion();

        String getDeveloperName();

        String getFunctionDescUrl();

        String getPermissionInfoUrl();

        String getPrivacyUrl();
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface ContentPageVideoListener extends VAOLInteractionListener {
        void onComplete(DCContentPage.ContentPageItem contentPageItem);

        void onError(DCContentPage.ContentPageItem contentPageItem);

        void onPause(DCContentPage.ContentPageItem contentPageItem);

        void onResume(DCContentPage.ContentPageItem contentPageItem);

        void onStart(DCContentPage.ContentPageItem contentPageItem);
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface DrawAOLInteractionListener extends FeedAOLInteractionListener {
        void onEnd();

        void onError();

        void onPause();

        void onResume();

        void onStart();
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface FeedAOLInteractionListener {
        void onClicked();

        void onClosed(String str);

        void onPaidGet(long j, String str, int i);

        void onRenderFail();

        void onRenderSuccess();

        void onShow();

        void onShowError();
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface GetConvertResultListener {
        void onError(int i, String str);

        void onSuccess(DCRetryRewardEntry dCRetryRewardEntry, int i);
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface NativeAOLInteractionListener extends NativeAOLListener {
        void destroy();

        Bitmap getAdLogo();

        AppInfo getAppInfo();

        String getButtonText();

        String getDescription();

        String getIconUrl();

        List<AdImage> getImageList();

        int getInteractionType();

        int getMaterialType();

        String getSource();

        String getTitle();

        double getVideoDuration();

        @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLListener
        View getVideoView(Activity activity);

        boolean isExpressAd();

        @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLListener
        void setAppDownloadListener(AppDownloadListener appDownloadListener);

        @Override // io.dcloud.sdk.core.interfaces.AOLLoader.NativeAOLListener
        void setVideoAdListener(VideoAdListener videoAdListener);
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface NativeAOLListener {
        void dislike(Activity activity);

        View getVideoView(Activity activity);

        void registerViewForInteraction(Activity activity, FrameLayout frameLayout, List<View> list, List<View> list2, List<ImageView> list3);

        void setAppDownloadListener(AppDownloadListener appDownloadListener);

        void setVideoAdListener(VideoAdListener videoAdListener);
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface RequestConvertResultListener {
        void onError(int i, String str);

        void onSuccess();
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface RewVAOLInteractionListener extends VAOLInteractionListener {
        void onReward(JSONObject jSONObject);
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface SplashAOLLoadListener {
        void onError(int i, String str);

        void redBag(View view, FrameLayout.LayoutParams layoutParams);
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface VAOLInteractionListener {
        void onClick();

        void onClose();

        void onPaidGet(long j, String str, int i);

        void onShow();

        void onShowError(int i, String str);

        void onSkip();

        void onVideoPlayEnd();
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface VideoAdListener {
        void onProgressUpdate(long j, long j2);

        void onVideoAdComplete();

        void onVideoAdPaused();

        void onVideoAdResume();

        void onVideoAdStartPlay();

        void onVideoError(int i, String str);

        void onVideoLoad();
    }

    boolean getPersonalAOL(Context context);

    void setPersonalAOL(Context context, boolean z);

    void setPrivacyConfig(DCloudAOLManager.PrivacyConfig privacyConfig);

    void updatePrivacyConfig(Context context, JSONObject jSONObject);
}
