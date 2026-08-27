package io.dcloud.common.adapter.util;

import android.content.Context;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.emulator.EmulatorCheckUtil;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class SecuritySupport {
    public static String getAppId() {
        return BaseInfo.sDefaultBootApp;
    }

    public static String getDeviceId(Context context) {
        return AppRuntime.getDCloudDeviceID(context);
    }

    public static boolean isRoot() {
        return DeviceInfo.hasRootPrivilege();
    }

    public static boolean isSimulator(Context context) {
        return EmulatorCheckUtil.getSingleInstance().emulatorCheck(context);
    }
}
