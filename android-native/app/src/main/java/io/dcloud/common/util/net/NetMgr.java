package io.dcloud.common.util.net;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.ICore;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.ISysEventListener;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.net.http.IServer;
import io.dcloud.feature.internal.sdk.SDK;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class NetMgr extends AbsMgr implements IMgr.NetEvent {
    DownloadMgr mDownloadMgr;
    IServer mLocalServer;
    NetCheckReceiver mNetCheckReceiver;
    UploadMgr mUploadMgr;

    public NetMgr(ICore iCore) throws Throwable {
        super(iCore, "netmgr", IMgr.MgrType.NetMgr);
        this.mNetCheckReceiver = null;
        this.mLocalServer = null;
        startMiniServer();
        this.mUploadMgr = UploadMgr.getUploadMgr();
        this.mDownloadMgr = DownloadMgr.getDownloadMgr();
        this.mNetCheckReceiver = new NetCheckReceiver(this);
        IntentFilter intentFilter = new IntentFilter(NetCheckReceiver.netACTION);
        intentFilter.addAction(NetCheckReceiver.simACTION);
        getContext().registerReceiver(this.mNetCheckReceiver, intentFilter);
    }

    private IServer initLocalServer() throws Throwable {
        Object objNewInstance;
        Object objNewInstance2;
        final ArrayList arrayList = new ArrayList();
        if (BaseInfo.SyncDebug && (objNewInstance2 = PlatformUtil.newInstance("io.dcloud.common.util.net.http.LocalServer", new Class[]{AbsMgr.class, Integer.TYPE}, new Object[]{this, 13131})) != null && (objNewInstance2 instanceof IServer)) {
            arrayList.add((IServer) objNewInstance2);
        }
        if (BaseInfo.SyncDebug) {
            Activity activity = (Activity) this.mCore.obtainActivityContext();
            Intent intent = activity.getIntent();
            String stringExtra = intent.getStringExtra("ip");
            String stringExtra2 = intent.getStringExtra("port");
            if (PdrUtil.isEmpty(stringExtra) || PdrUtil.isEmpty(stringExtra2)) {
                byte[] all = DHFile.readAll(activity.getExternalCacheDir().getPath() + File.separator + "debug_info");
                if (all != null) {
                    JSONObject object = JSON.parseObject(new String(all));
                    stringExtra = (String) object.get("ip");
                    stringExtra2 = (String) object.get("port");
                }
            }
            if (!PdrUtil.isEmpty(stringExtra) && !PdrUtil.isEmpty(stringExtra2) && (objNewInstance = PlatformUtil.newInstance("io.dcloud.common.util.net.http.LocalServer2", new Class[]{AbsMgr.class, Activity.class, String.class, String.class}, new Object[]{this, activity, stringExtra, stringExtra2})) != null && (objNewInstance instanceof IServer)) {
                arrayList.add((IServer) objNewInstance);
            }
        }
        return new IServer() { // from class: io.dcloud.common.util.net.NetMgr.1
            @Override // io.dcloud.common.util.net.http.IServer
            public void start() {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((IServer) it.next()).start();
                }
            }

            @Override // io.dcloud.common.util.net.http.IServer
            public void stop() {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((IServer) it.next()).stop();
                }
            }
        };
    }

    @Override // io.dcloud.common.DHInterface.AbsMgr
    public void dispose() {
        IServer iServer = this.mLocalServer;
        if (iServer != null) {
            iServer.stop();
        }
        UploadMgr uploadMgr = this.mUploadMgr;
        if (uploadMgr != null) {
            uploadMgr.dispose();
        }
        getContext().unregisterReceiver(this.mNetCheckReceiver);
    }

    @Override // io.dcloud.common.DHInterface.AbsMgr
    public void onExecute(ISysEventListener.SysEventType sysEventType, Object obj) throws Throwable {
        if (sysEventType == ISysEventListener.SysEventType.onStop) {
            IServer iServer = this.mLocalServer;
            if (iServer != null) {
                iServer.stop();
                this.mLocalServer = null;
                return;
            }
            return;
        }
        if ((sysEventType == ISysEventListener.SysEventType.onResume || sysEventType == ISysEventListener.SysEventType.onNewIntent) && this.mLocalServer == null) {
            startMiniServer();
        }
    }

    @Override // io.dcloud.common.DHInterface.IMgr
    public Object processEvent(IMgr.MgrType mgrType, int i, Object obj) {
        try {
            if (checkMgrId(mgrType)) {
                return null;
            }
            return this.mCore.dispatchEvent(mgrType, i, obj);
        } catch (Throwable th) {
            Logger.w("NetMgr.processEvent", th);
            return null;
        }
    }

    protected void startMiniServer() throws Throwable {
        if (!BaseInfo.ISDEBUG || SDK.isUniMPSDK()) {
            return;
        }
        IServer iServerInitLocalServer = initLocalServer();
        this.mLocalServer = iServerInitLocalServer;
        iServerInitLocalServer.start();
    }
}
