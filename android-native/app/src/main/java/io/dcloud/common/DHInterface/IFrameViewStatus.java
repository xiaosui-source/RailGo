package io.dcloud.common.DHInterface;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public interface IFrameViewStatus {
    public static final byte STATUS_ON_DESTROY = 4;
    public static final byte STATUS_ON_INIT = 0;
    public static final byte STATUS_ON_LOADING = 2;
    public static final byte STATUS_ON_PAGE_CHANGED = 5;
    public static final byte STATUS_ON_PRESHOW = 3;
    public static final byte STATUS_ON_PRE_LOADING = 1;

    void addFrameViewListener(IEventCallback iEventCallback);

    byte obtainStatus();

    void onDestroy();

    void onInit();

    void onLoading();

    void onPreLoading();

    void onPreShow(IFrameView iFrameView);

    void removeFrameViewListener(IEventCallback iEventCallback);
}
