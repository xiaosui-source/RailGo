package uts.sdk.modules.DCloudUniGetDeviceInfo;

import android.app.Activity;
import android.os.Build;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.common.RenderTypes;
import com.taobao.weex.common.WXConfig;
import io.dcloud.uts.NumberKt;
import io.dcloud.uts.UTSAndroid;
import io.dcloud.uts.UTSArray;
import io.dcloud.uts.UTSArrayKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0014\u0010\u000b\u001a\u00020\u00062\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u001a\u0006\u0010\u000f\u001a\u00020\u0010\u001a\u0010\u0010\u0011\u001a\u00020\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0012\u001a\u0006\u0010\u0013\u001a\u00020\u0010\"2\u0010\u0007\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0002¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001j\u0002`\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n*D\u0010\u0000\"\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0002¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u00012\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0002¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001¨\u0006\u0014"}, d2 = {"GetDeviceInfo", "Lkotlin/Function1;", "Luts/sdk/modules/DCloudUniGetDeviceInfo/GetDeviceInfoOptions;", "Lkotlin/ParameterName;", "name", "options", "Luts/sdk/modules/DCloudUniGetDeviceInfo/GetDeviceInfoResult;", "getDeviceInfo", "Luts/sdk/modules/DCloudUniGetDeviceInfo/GetDeviceInfo;", "getGetDeviceInfo", "()Lkotlin/jvm/functions/Function1;", "getBaseInfo", "filterArray", "Lio/dcloud/uts/UTSArray;", "", "isSimulator", "", "getDeviceInfoByJs", "Luts/sdk/modules/DCloudUniGetDeviceInfo/GetDeviceInfoOptionsJSONObject;", "isSimulatorByJs", "uni-getDeviceInfo_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class IndexKt {
    private static final Function1<GetDeviceInfoOptions, GetDeviceInfoResult> getDeviceInfo = new Function1() { // from class: uts.sdk.modules.DCloudUniGetDeviceInfo.IndexKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return IndexKt.getDeviceInfo$lambda$0((GetDeviceInfoOptions) obj);
        }
    };

    public static final Function1<GetDeviceInfoOptions, GetDeviceInfoResult> getGetDeviceInfo() {
        return getDeviceInfo;
    }

    public static final GetDeviceInfoResult getBaseInfo(UTSArray<String> filterArray) {
        Intrinsics.checkNotNullParameter(filterArray, "filterArray");
        Activity uniActivity = UTSAndroid.INSTANCE.getUniActivity();
        Intrinsics.checkNotNull(uniActivity);
        GetDeviceInfoResult getDeviceInfoResult = new GetDeviceInfoResult(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 4194303, null);
        if (filterArray.indexOf("brand") != -1) {
            getDeviceInfoResult.setBrand(Build.MANUFACTURER);
        }
        if (filterArray.indexOf("deviceBrand") != -1) {
            getDeviceInfoResult.setDeviceBrand(Build.MANUFACTURER);
        }
        if (filterArray.indexOf("model") != -1) {
            getDeviceInfoResult.setModel(Build.MODEL);
        }
        if (filterArray.indexOf("deviceModel") != -1) {
            getDeviceInfoResult.setDeviceModel(Build.MODEL);
        }
        if (filterArray.indexOf("deviceType") != -1) {
            getDeviceInfoResult.setDeviceType(DeviceUtil.INSTANCE.getSystemUIModeType(uniActivity));
        }
        if (filterArray.indexOf("deviceOrientation") != -1) {
            getDeviceInfoResult.setDeviceOrientation(DeviceUtil.INSTANCE.getOrientation(uniActivity));
        }
        if (filterArray.indexOf("deviceId") != -1) {
            getDeviceInfoResult.setDeviceId(DeviceUtil.INSTANCE.getDeviceID(uniActivity));
        }
        if (filterArray.indexOf("devicePixelRatio") != -1) {
            getDeviceInfoResult.setDevicePixelRatio(DeviceUtil.INSTANCE.getScaledDensity(uniActivity));
        }
        if (filterArray.indexOf("system") != -1) {
            getDeviceInfoResult.setSystem("Android " + Build.VERSION.RELEASE);
        }
        if (filterArray.indexOf(RenderTypes.RENDER_TYPE_NATIVE) != -1) {
            getDeviceInfoResult.setPlatform(WXEnvironment.OS);
        }
        if (filterArray.indexOf("isRoot") != -1) {
            getDeviceInfoResult.setRoot(Boolean.valueOf(DeviceUtil.INSTANCE.hasRootPrivilege()));
        }
        if (filterArray.indexOf("isSimulator") != -1) {
            getDeviceInfoResult.setSimulator(Boolean.valueOf(DeviceUtil.INSTANCE.isSimulator(uniActivity, false)));
        }
        if (filterArray.indexOf("isUSBDebugging") != -1) {
            getDeviceInfoResult.setUSBDebugging(Boolean.valueOf(DeviceUtil.INSTANCE.listeningForADB()));
        }
        if (filterArray.indexOf(WXConfig.osName) != -1) {
            getDeviceInfoResult.setOsName(WXEnvironment.OS);
        }
        if (filterArray.indexOf("osVersion") != -1) {
            getDeviceInfoResult.setOsVersion(Build.VERSION.RELEASE);
        }
        if (filterArray.indexOf("osLanguage") != -1) {
            getDeviceInfoResult.setOsLanguage(NumberKt.toString_number_nullable$default(UTSAndroid.INSTANCE.getLanguageInfo(uniActivity).get("osLanguage"), (Number) null, 1, (Object) null));
        }
        if (filterArray.indexOf("osTheme") != -1) {
            getDeviceInfoResult.setOsTheme(UTSAndroid.INSTANCE.getOsTheme());
        }
        if (filterArray.indexOf("osAndroidAPILevel") != -1) {
            getDeviceInfoResult.setOsAndroidAPILevel(Integer.valueOf(Build.VERSION.SDK_INT));
        }
        if (filterArray.indexOf("romName") != -1) {
            getDeviceInfoResult.setRomName(DeviceUtil.INSTANCE.getRomName());
        }
        if (filterArray.indexOf("romVersion") != -1) {
            getDeviceInfoResult.setRomVersion(DeviceUtil.INSTANCE.getRomVersion());
        }
        return getDeviceInfoResult;
    }

    public static final boolean isSimulator() {
        Activity uniActivity = UTSAndroid.INSTANCE.getUniActivity();
        Intrinsics.checkNotNull(uniActivity);
        return DeviceUtil.INSTANCE.isSimulator(uniActivity, false);
    }

    public static final GetDeviceInfoResult getDeviceInfoByJs(GetDeviceInfoOptionsJSONObject getDeviceInfoOptionsJSONObject) {
        return getDeviceInfo.invoke(getDeviceInfoOptionsJSONObject != null ? new GetDeviceInfoOptions(getDeviceInfoOptionsJSONObject.getFilter()) : null);
    }

    public static final boolean isSimulatorByJs() {
        return isSimulator();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final GetDeviceInfoResult getDeviceInfo$lambda$0(GetDeviceInfoOptions getDeviceInfoOptions) {
        UTSArray<String> uTSArray = new UTSArray<>();
        if (getDeviceInfoOptions != null && getDeviceInfoOptions.getFilter() != null) {
            uTSArray = getDeviceInfoOptions.getFilter();
        }
        if (getDeviceInfoOptions == null || NumberKt.numberEquals(uTSArray.getLength(), 0)) {
            uTSArray = UTSArrayKt._uA("brand", "deviceBrand", "deviceId", "model", "deviceModel", "deviceType", "deviceOrientation", "devicePixelRatio", "system", RenderTypes.RENDER_TYPE_NATIVE, "isRoot", "isSimulator", "isUSBDebugging", WXConfig.osName, "osVersion", "osLanguage", "osTheme", "osAndroidAPILevel", "romName", "romVersion");
        }
        return getBaseInfo(uTSArray);
    }
}
