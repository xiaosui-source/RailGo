package io.dcloud.common.DHInterface.message.action;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class BadgeSyncAction implements IAction {
    private ENUM_ACTION_TYPE mActionType;
    public int syncNumVal;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public enum ENUM_ACTION_TYPE {
        SYNC_NUM
    }

    private BadgeSyncAction(ENUM_ACTION_TYPE enum_action_type) {
        this.mActionType = enum_action_type;
    }

    public static BadgeSyncAction obtain(ENUM_ACTION_TYPE enum_action_type) {
        return new BadgeSyncAction(enum_action_type);
    }

    public ENUM_ACTION_TYPE getActionType() {
        return this.mActionType;
    }

    public int getSyncNumVal() {
        return this.syncNumVal;
    }

    public BadgeSyncAction setSyncNum(int i) {
        this.syncNumVal = i;
        return this;
    }
}
