package io.dcloud.common.DHInterface.message;

import io.dcloud.common.DHInterface.message.action.IAction;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public abstract class AbsActionObserver {
    IObserveAble observeAble;

    public AbsActionObserver(IObserveAble iObserveAble) {
        this.observeAble = iObserveAble;
    }

    public EnumUniqueID getObserverUniqueID() {
        return this.observeAble.getActionObserverID();
    }

    public abstract boolean handleMessage(IAction iAction);
}
