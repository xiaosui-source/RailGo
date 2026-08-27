package uts.sdk.modules.DCloudUniGetAppBaseInfo;

import android.app.Activity;
import com.taobao.weex.common.WXConfig;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.ui.blur.DCBlurDraweeView;
import io.dcloud.uts.NumberKt;
import io.dcloud.uts.StringKt;
import io.dcloud.uts.UTSAndroid;
import io.dcloud.uts.UTSArray;
import io.dcloud.uts.UTSArrayKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0004\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u000b\u001a\u00020\u00062\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u001a\u0010\u0010\u0012\u001a\u00020\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0013\"2\u0010\u0007\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0002¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001j\u0002`\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\"\u001d\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00100\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\n*D\u0010\u0000\"\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0002¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u00012\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0002¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001¨\u0006\u0014"}, d2 = {"GetAppBaseInfo", "Lkotlin/Function1;", "Luts/sdk/modules/DCloudUniGetAppBaseInfo/GetAppBaseInfoOptions;", "Lkotlin/ParameterName;", "name", "options", "Luts/sdk/modules/DCloudUniGetAppBaseInfo/GetAppBaseInfoResult;", "getAppBaseInfo", "Luts/sdk/modules/DCloudUniGetAppBaseInfo/GetAppBaseInfo;", "getGetAppBaseInfo", "()Lkotlin/jvm/functions/Function1;", "getBaseInfo", "filterArray", "Lio/dcloud/uts/UTSArray;", "", "convertVersionCode", "", "getConvertVersionCode", "getAppBaseInfoByJs", "Luts/sdk/modules/DCloudUniGetAppBaseInfo/GetAppBaseInfoOptionsJSONObject;", "uni-getAppBaseInfo_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class IndexKt {
    private static final Function1<GetAppBaseInfoOptions, GetAppBaseInfoResult> getAppBaseInfo = new Function1() { // from class: uts.sdk.modules.DCloudUniGetAppBaseInfo.IndexKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return IndexKt.getAppBaseInfo$lambda$0((GetAppBaseInfoOptions) obj);
        }
    };
    private static final Function1<String, Number> convertVersionCode = new Function1() { // from class: uts.sdk.modules.DCloudUniGetAppBaseInfo.IndexKt$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return IndexKt.convertVersionCode$lambda$2((String) obj);
        }
    };

    public static final Function1<GetAppBaseInfoOptions, GetAppBaseInfoResult> getGetAppBaseInfo() {
        return getAppBaseInfo;
    }

    public static final GetAppBaseInfoResult getBaseInfo(UTSArray<String> filterArray) {
        String str;
        Intrinsics.checkNotNullParameter(filterArray, "filterArray");
        Activity uniActivity = UTSAndroid.INSTANCE.getUniActivity();
        Intrinsics.checkNotNull(uniActivity);
        GetAppBaseInfoResult getAppBaseInfoResult = new GetAppBaseInfoResult(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 268435455, null);
        if (filterArray.indexOf("appId") != -1) {
            getAppBaseInfoResult.setAppId(AppBaseInfoDeviceUtil.INSTANCE.getAppID());
        }
        if (filterArray.indexOf(WXConfig.appName) != -1) {
            getAppBaseInfoResult.setAppName(UTSAndroid.INSTANCE.getAppName());
        }
        if (UTSAndroid.INSTANCE.isUniMp()) {
            if (filterArray.indexOf("hostPackageName") != -1) {
                getAppBaseInfoResult.setHostPackageName(AppBaseInfoDeviceUtil.INSTANCE.getPackageName(uniActivity));
            }
            if (filterArray.indexOf("hostVersion") != -1) {
                getAppBaseInfoResult.setHostVersion(AppBaseInfoDeviceUtil.INSTANCE.getHostVersion(uniActivity));
            }
            if (filterArray.indexOf("hostName") != -1) {
                getAppBaseInfoResult.setHostName(AppBaseInfoDeviceUtil.INSTANCE.getAppName(uniActivity));
            }
            if (filterArray.indexOf("hostTheme") != -1) {
                if (AppBaseInfoDeviceUtil.INSTANCE.isSystemNightMode(uniActivity)) {
                    str = DCBlurDraweeView.DARK;
                } else {
                    str = DCBlurDraweeView.LIGHT;
                }
                getAppBaseInfoResult.setHostTheme(str);
            }
            if (filterArray.indexOf("hostLanguage") != -1) {
                getAppBaseInfoResult.setHostLanguage(AppBaseInfoDeviceUtil.INSTANCE.getOsLanguage(uniActivity));
            }
            if (filterArray.indexOf(WXConfig.appVersion) != -1) {
                getAppBaseInfoResult.setAppVersion(AppBaseInfoDeviceUtil.INSTANCE.getAppVersionName());
            }
            if (filterArray.indexOf("appVersionCode") != -1) {
                getAppBaseInfoResult.setAppVersionCode(AppBaseInfoDeviceUtil.INSTANCE.getAppVersionCode());
            }
        } else {
            if (filterArray.indexOf(WXConfig.appVersion) != -1) {
                getAppBaseInfoResult.setAppVersion(NumberKt.toString_number_nullable$default(UTSAndroid.INSTANCE.getAppVersion().get("name"), (Number) null, 1, (Object) null));
            }
            if (filterArray.indexOf("appVersionCode") != -1) {
                getAppBaseInfoResult.setAppVersionCode(NumberKt.toString_number_nullable$default(UTSAndroid.INSTANCE.getAppVersion().get("code"), (Number) null, 1, (Object) null));
            }
        }
        if (filterArray.indexOf("appLanguage") != -1) {
            getAppBaseInfoResult.setAppLanguage(AppBaseInfoDeviceUtil.INSTANCE.getOsLanguageNormal(uniActivity));
        }
        if (filterArray.indexOf(IApp.ConfigProperty.CONFIG_LANGUAGE) != -1) {
            getAppBaseInfoResult.setLanguage(AppBaseInfoDeviceUtil.INSTANCE.getOsLanguage(uniActivity));
        }
        if (filterArray.indexOf("version") != -1) {
            getAppBaseInfoResult.setVersion(AppBaseInfoDeviceUtil.INSTANCE.getAppInnerVersion());
        }
        if (filterArray.indexOf("appWgtVersion") != -1) {
            getAppBaseInfoResult.setAppWgtVersion(AppBaseInfoDeviceUtil.INSTANCE.getAppVersionName());
        }
        if (filterArray.indexOf("isUniAppX") != -1) {
            getAppBaseInfoResult.setUniAppX(Boolean.valueOf(UTSAndroid.INSTANCE.isUniAppX()));
        }
        if (filterArray.indexOf("uniCompileVersion") != -1) {
            getAppBaseInfoResult.setUniCompileVersion(UTSAndroid.INSTANCE.getUniCompileVersion());
        }
        if (filterArray.indexOf("uniCompilerVersion") != -1) {
            getAppBaseInfoResult.setUniCompilerVersion(UTSAndroid.INSTANCE.getUniCompileVersion());
        }
        if (filterArray.indexOf("uniPlatform") != -1) {
            getAppBaseInfoResult.setUniPlatform(AbsoluteConst.XML_APP);
        }
        if (filterArray.indexOf("uniRuntimeVersion") != -1) {
            getAppBaseInfoResult.setUniRuntimeVersion(UTSAndroid.INSTANCE.getUniRuntimeVersion());
        }
        if (filterArray.indexOf("uniCompileVersionCode") != -1) {
            getAppBaseInfoResult.setUniCompileVersionCode(convertVersionCode.invoke(UTSAndroid.INSTANCE.getUniCompileVersion()));
        }
        if (filterArray.indexOf("uniCompilerVersionCode") != -1) {
            getAppBaseInfoResult.setUniCompilerVersionCode(convertVersionCode.invoke(UTSAndroid.INSTANCE.getUniCompileVersion()));
        }
        if (filterArray.indexOf("uniRuntimeVersionCode") != -1) {
            getAppBaseInfoResult.setUniRuntimeVersionCode(convertVersionCode.invoke(UTSAndroid.INSTANCE.getUniRuntimeVersion()));
        }
        if (filterArray.indexOf("packageName") != -1) {
            getAppBaseInfoResult.setPackageName(AppBaseInfoDeviceUtil.INSTANCE.getPackageName(uniActivity));
        }
        if (filterArray.indexOf("signature") != -1) {
            getAppBaseInfoResult.setSignature(AppBaseInfoDeviceUtil.INSTANCE.getAppSignatureSHA1(uniActivity));
        }
        if (filterArray.indexOf("appTheme") != -1) {
            getAppBaseInfoResult.setAppTheme(UTSAndroid.INSTANCE.getAppTheme());
        }
        if (filterArray.indexOf(AbsoluteConst.XML_CHANNEL) != -1) {
            getAppBaseInfoResult.setChannel(AppBaseInfoDeviceUtil.INSTANCE.getChannel(uniActivity));
        }
        return getAppBaseInfoResult;
    }

    public static final Function1<String, Number> getConvertVersionCode() {
        return convertVersionCode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Number convertVersionCode$lambda$2(String str) {
        Number numberDec = 2;
        boolean z = false;
        String str2 = "";
        for (Number numberInc = (Number) 0; NumberKt.compareTo(numberInc, Integer.valueOf(str.length())) < 0; numberInc = NumberKt.inc(numberInc)) {
            char c = StringKt.get(str, numberInc);
            if (z) {
                if (Character.isDigit(c)) {
                    str2 = str2 + c;
                }
                numberDec = NumberKt.dec(numberDec);
                if (NumberKt.numberEquals(numberDec, 0)) {
                    break;
                }
            } else if (Character.isDigit(c)) {
                str2 = str2 + c;
            } else if (c == '.') {
                str2 = str2 + c;
                z = true;
            }
        }
        return NumberKt.parseFloat(str2);
    }

    public static final GetAppBaseInfoResult getAppBaseInfoByJs(GetAppBaseInfoOptionsJSONObject getAppBaseInfoOptionsJSONObject) {
        return getAppBaseInfo.invoke(getAppBaseInfoOptionsJSONObject != null ? new GetAppBaseInfoOptions(getAppBaseInfoOptionsJSONObject.getFilter()) : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final GetAppBaseInfoResult getAppBaseInfo$lambda$0(GetAppBaseInfoOptions getAppBaseInfoOptions) {
        UTSArray<String> uTSArray = new UTSArray<>();
        if (getAppBaseInfoOptions != null && getAppBaseInfoOptions.getFilter() != null) {
            uTSArray = getAppBaseInfoOptions.getFilter();
        }
        if (getAppBaseInfoOptions == null || NumberKt.numberEquals(uTSArray.getLength(), 0)) {
            uTSArray = UTSArrayKt._uA("appId", WXConfig.appName, WXConfig.appVersion, "appVersionCode", "appLanguage", IApp.ConfigProperty.CONFIG_LANGUAGE, "version", "appWgtVersion", "hostLanguage", "hostVersion", "hostName", "hostPackageName", "hostSDKVersion", "hostTheme", "isUniAppX", "uniCompileVersion", "uniCompilerVersion", "uniPlatform", "uniRuntimeVersion", "uniCompileVersionCode", "uniCompilerVersionCode", "uniRuntimeVersionCode", "packageName", "signature", "appTheme", AbsoluteConst.XML_CHANNEL);
        }
        return getBaseInfo(uTSArray);
    }
}
