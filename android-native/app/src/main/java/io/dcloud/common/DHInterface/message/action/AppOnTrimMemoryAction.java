package io.dcloud.common.DHInterface.message.action;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class AppOnTrimMemoryAction implements IAction {
    private int level;

    public AppOnTrimMemoryAction(int i) {
        this.level = i;
    }

    public static AppOnTrimMemoryAction obtain(int i) {
        return new AppOnTrimMemoryAction(i);
    }

    public int getLevel() {
        return this.level;
    }
}
