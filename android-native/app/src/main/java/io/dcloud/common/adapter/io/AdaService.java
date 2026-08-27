package io.dcloud.common.adapter.io;

import android.content.Context;
import java.util.HashMap;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public abstract class AdaService {
    static final String TAG = "AdaService";
    static HashMap<String, AdaService> mServicesHandler = new HashMap<>(2);
    protected Context mContextWrapper;
    private String mServiceName;

    protected AdaService(Context context, String str) {
        this.mContextWrapper = context;
        this.mServiceName = str;
    }

    public static final AdaService getServiceListener(String str) {
        return mServicesHandler.get(str);
    }

    public static final void removeServiceListener(String str) {
        mServicesHandler.remove(str);
    }

    public abstract void onDestroy();

    public abstract void onExecute();
}
