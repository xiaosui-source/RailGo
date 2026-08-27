package io.dcloud.common.util;

import io.dcloud.common.DHInterface.ISysEventListener;
import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class EventDispatchManager {
    private static EventDispatchManager instance;
    private List<ActivityEventDispatchListener> dispatchListeners = new ArrayList();

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface ActivityEventDispatchListener {
        boolean onExecute(ISysEventListener.SysEventType sysEventType, Object obj);
    }

    private EventDispatchManager() {
    }

    public static EventDispatchManager getInstance() {
        if (instance == null) {
            synchronized (EventDispatchManager.class) {
                if (instance == null) {
                    instance = new EventDispatchManager();
                }
            }
        }
        return instance;
    }

    public void addListener(ActivityEventDispatchListener activityEventDispatchListener) {
        if (activityEventDispatchListener != null) {
            this.dispatchListeners.add(activityEventDispatchListener);
        }
    }

    public boolean dispatchEvent(ISysEventListener.SysEventType sysEventType, Object obj) {
        boolean zOnExecute = false;
        for (int size = this.dispatchListeners.size() - 1; size >= 0; size--) {
            zOnExecute = this.dispatchListeners.get(size).onExecute(sysEventType, obj);
            if (zOnExecute) {
                return zOnExecute;
            }
        }
        return zOnExecute;
    }

    public void removeListener(ActivityEventDispatchListener activityEventDispatchListener) {
        if (activityEventDispatchListener != null) {
            this.dispatchListeners.remove(activityEventDispatchListener);
        }
    }
}
