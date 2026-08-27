package io.dcloud.common.DHInterface;

import android.app.Activity;
import android.content.Context;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.ISysEventListener;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public interface ICore {

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface ICoreEvent {
        public static final int CHECK_IS_IBOOT_SERVICES = 1;
        public static final int GET_SDK_MODE = -1;
        public static final int WEBAPP_QUIT = 0;
        public static final int WEBAPP_START = 2;
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface ICoreStatusListener {
        void onCoreInitEnd(ICore iCore);

        void onCoreReady(ICore iCore);

        boolean onCoreStop();
    }

    Object dispatchEvent(IMgr.MgrType mgrType, int i, Object obj);

    Context obtainActivityContext();

    Context obtainContext();

    boolean onActivityExecute(Activity activity, ISysEventListener.SysEventType sysEventType, Object obj);

    void onRestart(Context context);

    void setmCoreListener(ICoreStatusListener iCoreStatusListener);
}
