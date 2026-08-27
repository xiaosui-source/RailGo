package io.dcloud.common.DHInterface;

import android.content.Context;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.ISysEventListener;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public abstract class AbsMgr implements IMgr {
    protected Context mContextWrapper;
    protected ICore mCore;
    protected boolean mIsStreamAppMode = false;
    protected String mMgrName;
    protected IMgr.MgrType mMgrType;

    protected AbsMgr(ICore iCore, String str, IMgr.MgrType mgrType) {
        this.mContextWrapper = null;
        this.mCore = iCore;
        this.mMgrName = str;
        this.mMgrType = mgrType;
        this.mContextWrapper = iCore.obtainContext();
    }

    public final boolean checkMgrId(IMgr.MgrType mgrType) {
        return this.mMgrType == mgrType;
    }

    public void dispose() {
    }

    public final Context getContext() {
        return this.mContextWrapper;
    }

    public final boolean isStreamAppMode() {
        return this.mIsStreamAppMode;
    }

    public void onExecute(ISysEventListener.SysEventType sysEventType, Object obj) {
    }
}
