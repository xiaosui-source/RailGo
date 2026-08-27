package uts.sdk.modules.DCloudUniGetAppBaseInfo;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import io.dcloud.uts.NumberKt;
import io.dcloud.uts.StringKt;
import io.dcloud.uts.UTSAndroid;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0016\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Luts/sdk/modules/DCloudUniGetAppBaseInfo/AppBaseInfoDeviceUtil;", "", "<init>", "()V", "Companion", "uni-getAppBaseInfo_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class AppBaseInfoDeviceUtil {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* compiled from: index.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0004\u001a\u00020\u0005J\u000e\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\n\u001a\u00020\u0005J\u0006\u0010\u000b\u001a\u00020\u0005J\u000e\u0010\f\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\r\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\u0014\u001a\u00020\u0005J\u000e\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bJ\u0018\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0005H\u0002¨\u0006\u001b"}, d2 = {"Luts/sdk/modules/DCloudUniGetAppBaseInfo/AppBaseInfoDeviceUtil$Companion;", "", "<init>", "()V", "getAppID", "", "getAppName", "context", "Landroid/content/Context;", "getPackageName", "getAppVersionName", "getAppVersionCode", "getHostVersion", "getHostCode", "isSystemNightMode", "", "activity", "Landroid/app/Activity;", "getOsLanguage", "getOsLanguageNormal", "getAppInnerVersion", "getAppSignatureSHA1", "getChannel", "getSignatureString", "sign", "Landroid/content/pm/Signature;", "type", "uni-getAppBaseInfo_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getAppID() {
            return UTSAndroid.INSTANCE.getAppId();
        }

        public final String getAppName(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return NumberKt.toString(context.getPackageManager().getApplicationLabel(context.getApplicationInfo()), (Number) 10);
        }

        public final String getPackageName(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            String packageName = context.getPackageName();
            Intrinsics.checkNotNullExpressionValue(packageName, "getPackageName(...)");
            return packageName;
        }

        public final String getAppVersionName() {
            return NumberKt.toString_number_nullable$default(UTSAndroid.INSTANCE.getAppVersion().get("name"), (Number) null, 1, (Object) null);
        }

        public final String getAppVersionCode() {
            return NumberKt.toString_number_nullable$default(UTSAndroid.INSTANCE.getAppVersion().get("code"), (Number) null, 1, (Object) null);
        }

        public final String getHostVersion(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            String str = context.getPackageManager().getPackageInfo(context.getPackageName(), 1).versionName;
            return str == null ? "" : str;
        }

        public final String getHostCode(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return NumberKt.plus(Integer.valueOf(context.getPackageManager().getPackageInfo(context.getPackageName(), 1).versionCode), "");
        }

        public final boolean isSystemNightMode(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            Object systemService = activity.getSystemService("uimode");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.UiModeManager");
            return ((UiModeManager) systemService).getNightMode() == 2;
        }

        public final String getOsLanguage(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return NumberKt.toString_number_nullable$default(UTSAndroid.INSTANCE.getLanguageInfo(context).get("osLanguage"), (Number) null, 1, (Object) null);
        }

        public final String getOsLanguageNormal(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            String string_number_nullable$default = NumberKt.toString_number_nullable$default(UTSAndroid.INSTANCE.getLanguageInfo(context).get("appLanguage"), (Number) null, 1, (Object) null);
            if (!NumberKt.numberEquals(StringKt.indexOf$default(string_number_nullable$default, "zh", null, 2, null), 0)) {
                return string_number_nullable$default;
            }
            if (NumberKt.compareTo(StringKt.indexOf$default(string_number_nullable$default, "-hans", null, 2, null), (Number) (-1)) > 0) {
                return "zh-Hans";
            }
            if (NumberKt.compareTo(StringKt.indexOf$default(string_number_nullable$default, "-hant", null, 2, null), (Number) (-1)) <= 0 && !StringKt.includes$default(string_number_nullable$default, "-tw", null, 2, null) && !StringKt.includes$default(string_number_nullable$default, "-hk", null, 2, null) && !StringKt.includes$default(string_number_nullable$default, "-mo", null, 2, null) && !StringKt.includes$default(string_number_nullable$default, "-cht", null, 2, null)) {
                return "zh-Hans";
            }
            return "zh-Hant";
        }

        public final String getAppInnerVersion() {
            return UTSAndroid.INSTANCE.getInnerVersion();
        }

        public final String getAppSignatureSHA1(Context context) throws PackageManager.NameNotFoundException {
            Intrinsics.checkNotNullParameter(context, "context");
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 64);
                Intrinsics.checkNotNull(packageInfo, "null cannot be cast to non-null type android.content.pm.PackageInfo");
                Signature[] signatureArr = packageInfo.signatures;
                if (signatureArr != null) {
                    String signatureString = "";
                    for (Signature signature : signatureArr) {
                        Companion companion = AppBaseInfoDeviceUtil.INSTANCE;
                        Intrinsics.checkNotNull(signature);
                        signatureString = companion.getSignatureString(signature, "SHA1");
                    }
                    return signatureString;
                }
            } catch (Exception unused) {
            }
            return "";
        }

        public final String getChannel(Context context) throws PackageManager.NameNotFoundException {
            Intrinsics.checkNotNullParameter(context, "context");
            try {
                ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
                Intrinsics.checkNotNullExpressionValue(applicationInfo, "getApplicationInfo(...)");
                String string = applicationInfo.metaData.getString("DCLOUD_CHANNEL");
                return string == null ? "" : string;
            } catch (Exception unused) {
                return "";
            }
        }

        private final String getSignatureString(Signature sign, String type) throws NoSuchAlgorithmException {
            byte[] byteArray = sign.toByteArray();
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(type);
                if (messageDigest != null) {
                    byte[] bArrDigest = messageDigest.digest(byteArray);
                    StringBuffer stringBuffer = new StringBuffer();
                    Intrinsics.checkNotNull(bArrDigest);
                    for (byte b : bArrDigest) {
                        String hexString = Integer.toHexString(NumberKt.or(NumberKt.and(Byte.valueOf(b), (Number) 255), (Number) 256).intValue());
                        Intrinsics.checkNotNullExpressionValue(hexString, "toHexString(...)");
                        stringBuffer.append(StringKt.substring(hexString, (Number) 1, (Number) 3));
                    }
                    String string = stringBuffer.toString();
                    Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                    return string;
                }
                return "error!";
            } catch (Exception unused) {
                return "error!";
            }
        }
    }
}
