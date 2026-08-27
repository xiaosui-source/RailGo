package uts.sdk.modules.DCloudUniGetSystemSetting;

import android.content.Context;
import io.dcloud.uts.UTSAndroid;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import uts.sdk.modules.DCloudUniGetSystemSetting.DeviceUtil;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0006\u0010\u0007\u001a\u00020\u0002\"\u001b\u0010\u0003\u001a\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006*\u0016\u0010\u0000\"\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0001¨\u0006\b"}, d2 = {"GetSystemSetting", "Lkotlin/Function0;", "Luts/sdk/modules/DCloudUniGetSystemSetting/GetSystemSettingResult;", "getSystemSetting", "Luts/sdk/modules/DCloudUniGetSystemSetting/GetSystemSetting;", "getGetSystemSetting", "()Lkotlin/jvm/functions/Function0;", "getSystemSettingByJs", "uni-getSystemSetting_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class IndexKt {
    private static final Function0<GetSystemSettingResult> getSystemSetting = new Function0() { // from class: uts.sdk.modules.DCloudUniGetSystemSetting.IndexKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return IndexKt.getSystemSetting$lambda$0();
        }
    };

    public static final Function0<GetSystemSettingResult> getGetSystemSetting() {
        return getSystemSetting;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final GetSystemSettingResult getSystemSetting$lambda$0() {
        Context appContext = UTSAndroid.INSTANCE.getAppContext();
        DeviceUtil.Companion companion = DeviceUtil.INSTANCE;
        Intrinsics.checkNotNull(appContext);
        GetSystemSettingResult getSystemSettingResult = new GetSystemSettingResult(null, null, DeviceUtil.INSTANCE.locationEnable(appContext), null, null, companion.deviceOrientation(appContext), 27, null);
        try {
            getSystemSettingResult.setBluetoothEnabled(Boolean.valueOf(DeviceUtil.INSTANCE.blueToothEnable(appContext)));
        } catch (Exception unused) {
            getSystemSettingResult.setBluetoothError("Missing permissions required by BluetoothAdapter.isEnabled: android.permission.BLUETOOTH");
        }
        try {
            getSystemSettingResult.setWifiEnabled(Boolean.valueOf(DeviceUtil.INSTANCE.wifiEnable(appContext)));
        } catch (Exception unused2) {
            getSystemSettingResult.setWifiError("Missing permissions required by WifiManager.isWifiEnabled: android.permission.ACCESS_WIFI_STATE");
        }
        return getSystemSettingResult;
    }

    public static final GetSystemSettingResult getSystemSettingByJs() {
        return getSystemSetting.invoke();
    }
}
