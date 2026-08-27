package io.dcloud.common.core.ui;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class TabBarWebviewMgr {
    private static TabBarWebviewMgr mInstance;
    private TabBarWebview mLaunchTabBar;

    public static TabBarWebviewMgr getInstance() {
        if (mInstance == null) {
            mInstance = new TabBarWebviewMgr();
        }
        return mInstance;
    }

    public TabBarWebview getLaunchTabBar() {
        return this.mLaunchTabBar;
    }

    public void setLancheTabBar(TabBarWebview tabBarWebview) {
        this.mLaunchTabBar = tabBarWebview;
    }
}
