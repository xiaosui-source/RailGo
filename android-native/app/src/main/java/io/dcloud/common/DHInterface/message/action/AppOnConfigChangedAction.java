package io.dcloud.common.DHInterface.message.action;

import android.content.res.Configuration;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class AppOnConfigChangedAction implements IAction {
    private Configuration newConfig;

    public AppOnConfigChangedAction(Configuration configuration) {
        this.newConfig = configuration;
    }

    public static AppOnConfigChangedAction obtain(Configuration configuration) {
        return new AppOnConfigChangedAction(configuration);
    }

    public Configuration getConfig() {
        return this.newConfig;
    }
}
