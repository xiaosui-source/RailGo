package io.dcloud.common.DHInterface;

import android.view.View;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public interface IWebAppRootView {
    boolean didCloseSplash();

    IFrameView findFrameViewB(IFrameView iFrameView);

    void goHome(IFrameView iFrameView);

    View obtainMainView();

    void onAppActive(IApp iApp);

    void onAppStart(IApp iApp);

    void onAppStop(IApp iApp);

    void onAppUnActive(IApp iApp);

    void onRootViewGlobalLayout(View view);
}
