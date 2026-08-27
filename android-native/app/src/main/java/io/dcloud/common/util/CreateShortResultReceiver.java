package io.dcloud.common.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class CreateShortResultReceiver extends BroadcastReceiver {
    public static final String KEY_APPID = "appid";
    public static final String KEY_NAME = "name";
    public static final String KEY_SF = "sf";
    public static final String KEY_SFD = "sfd";
    public static final String KEY_VERSIONNAME = "v";
    private static final String TAG = "CreateShortResultReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive: intent==" + intent);
    }
}
