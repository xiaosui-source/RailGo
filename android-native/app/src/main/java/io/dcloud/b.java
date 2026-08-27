package io.dcloud;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import io.dcloud.common.DHInterface.IActivityHandler;
import io.dcloud.common.DHInterface.IReflectAble;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.feature.internal.reflect.BroadcastReceiver;
import io.src.dcloud.adapter.DCloudBaseActivity;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
abstract class b extends DCloudBaseActivity implements IActivityHandler, IReflectAble {
    private int a = 0;
    private HashMap b = new HashMap();
    private HashMap c = new HashMap();

    b() {
    }

    private void g() {
        Iterator it = this.b.values().iterator();
        while (it.hasNext()) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver((c) it.next());
        }
        this.b.clear();
        Iterator it2 = this.c.values().iterator();
        while (it2.hasNext()) {
            unregisterReceiver((c) it2.next());
        }
        this.c.clear();
    }

    public void callBack(String str, Bundle bundle) {
    }

    public int getActivityState() {
        return this.a;
    }

    public Context getContext() {
        return this.that;
    }

    public String getUrlByFilePath(String str, String str2) {
        return "";
    }

    public boolean isMultiProcessMode() {
        return false;
    }

    public void onAsyncStartAppEnd(String str, Object obj) {
    }

    public Object onAsyncStartAppStart(String str) {
        return null;
    }

    @Override // io.src.dcloud.adapter.DCloudBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        this.a = 1;
        super.onCreate(bundle);
        AndroidResources.initAndroidResources(this.that);
    }

    @Override // io.src.dcloud.adapter.DCloudBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        this.a = 0;
        super.onDestroy();
        try {
            g();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        this.a = 2;
        super.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        this.a = 1;
        super.onResume();
    }

    public void registerLocalReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        c cVar = new c(broadcastReceiver, intentFilter);
        try {
            LocalBroadcastManager.getInstance(this).registerReceiver(cVar, intentFilter);
            this.b.put(broadcastReceiver.toString(), cVar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String str, Handler handler) {
        Intent intentRegisterReceiver;
        c cVar = new c(broadcastReceiver, intentFilter);
        try {
            intentRegisterReceiver = registerReceiver(cVar, intentFilter, str, handler);
        } catch (Exception e) {
            e = e;
            intentRegisterReceiver = null;
        }
        try {
            this.c.put(broadcastReceiver.toString(), cVar);
            return intentRegisterReceiver;
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            return intentRegisterReceiver;
        }
    }

    public void sendLocalBroadcast(Intent intent) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        c cVar = (c) this.b.remove(broadcastReceiver.toString());
        if (cVar != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(cVar);
        }
        c cVar2 = (c) this.c.remove(broadcastReceiver.toString());
        if (cVar2 != null) {
            unregisterReceiver(cVar2);
        }
    }

    public void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        c cVar = new c(broadcastReceiver, intentFilter);
        try {
            registerReceiver(cVar, intentFilter);
            this.c.put(broadcastReceiver.toString(), cVar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
