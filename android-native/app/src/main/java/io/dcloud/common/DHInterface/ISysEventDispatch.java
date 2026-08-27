package io.dcloud.common.DHInterface;

import io.dcloud.common.DHInterface.ISysEventListener;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public interface ISysEventDispatch {
    boolean callSysEventListener(ISysEventListener.SysEventType sysEventType, Object obj);

    void registerSysEventListener(ISysEventListener iSysEventListener, ISysEventListener.SysEventType sysEventType);

    void unRegisterSysEventListener(ISysEventListener iSysEventListener, ISysEventListener.SysEventType sysEventType);
}
