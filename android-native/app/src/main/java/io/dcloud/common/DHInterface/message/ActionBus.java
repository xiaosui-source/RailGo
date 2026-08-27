package io.dcloud.common.DHInterface.message;

import io.dcloud.common.DHInterface.message.action.AppOnConfigChangedAction;
import io.dcloud.common.DHInterface.message.action.AppOnCreateAction;
import io.dcloud.common.DHInterface.message.action.AppOnTrimMemoryAction;
import io.dcloud.common.DHInterface.message.action.BadgeSyncAction;
import io.dcloud.common.DHInterface.message.action.IAction;
import io.dcloud.common.DHInterface.message.action.PermissionRequestAction;
import io.dcloud.common.DHInterface.message.action.WebActivityOnDestroyAction;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class ActionBus {
    private static ActionBus instance = new ActionBus();
    Set<Class<? extends IAction>> supportMessageType = new CopyOnWriteArraySet();
    Map<EnumUniqueID, AbsActionObserver> observers = new ConcurrentHashMap();

    private ActionBus() {
        this.supportMessageType.add(BadgeSyncAction.class);
        this.supportMessageType.add(AppOnTrimMemoryAction.class);
        this.supportMessageType.add(AppOnConfigChangedAction.class);
        this.supportMessageType.add(AppOnCreateAction.class);
        this.supportMessageType.add(WebActivityOnDestroyAction.class);
        this.supportMessageType.add(PermissionRequestAction.class);
    }

    public static ActionBus getInstance() {
        return instance;
    }

    public boolean observeAction(AbsActionObserver absActionObserver) {
        if (absActionObserver == null || this.observers.containsKey(absActionObserver.getObserverUniqueID())) {
            return false;
        }
        this.observers.put(absActionObserver.getObserverUniqueID(), absActionObserver);
        return true;
    }

    public boolean sendToBus(IAction iAction) {
        if (iAction == null || !this.supportMessageType.contains(iAction.getClass())) {
            return false;
        }
        for (AbsActionObserver absActionObserver : this.observers.values()) {
            if (absActionObserver != null) {
                absActionObserver.handleMessage(iAction);
            }
        }
        return true;
    }

    public void stopObserve(EnumUniqueID enumUniqueID) {
        this.observers.remove(enumUniqueID);
    }
}
