package uts.sdk.modules.DCloudUniGetDeviceInfo;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.taobao.weex.common.Constants;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.utils.tools.TimeCalculator;
import io.dcloud.uts.NumberKt;
import io.dcloud.uts.StringKt;
import io.dcloud.uts.UTSAndroid;
import io.dcloud.uts.UTSArray;
import io.dcloud.uts.UTSArrayKt;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0016\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Luts/sdk/modules/DCloudUniGetDeviceInfo/DeviceUtil;", "", "<init>", "()V", "Companion", "uni-getDeviceInfo_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class DeviceUtil {
    private static String customOS;
    private static String customOSVersion;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final UTSArray<String> rootRelatedDirs = UTSArrayKt._uA("/su", "/su/bin/su", "/sbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/data/local/su", "/system/xbin/su", "/system/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/system/bin/cufsdosck", "/system/xbin/cufsdosck", "/system/bin/cufsmgr", "/system/xbin/cufsmgr", "/system/bin/cufaevdd", "/system/xbin/cufaevdd", "/system/bin/conbb", "/system/xbin/conbb");
    private static String KEY_HARMONYOS_VERSION_NAME = "hw_sc.build.platform.version";
    private static String KEY_EMUI_VERSION_NAME = "ro.build.version.emui";
    private static String KEY_MAGICUI_VERSION = "ro.build.version.magic";
    private static String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static String KEY_COLOROS_VERSION_NAME = "ro.build.version.opporom";
    private static String KEY_VIVO_VERSION_NAME = "ro.vivo.os.name";
    private static String KEY_VIVO_VERSION = "ro.vivo.os.version";
    private static String KEY_ONEPLUS_VERSION_NAME = "ro.rom.version";
    private static String KEY_FLYME_VERSION_NAME = "ro.build.display.id";
    private static String KEY_NUBIA_VERSION_NAME = "ro.build.nubia.rom.name";
    private static String KEY_NUBIA_VERSION_CODE = "ro.build.nubia.rom.code";

    /* compiled from: index.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0004\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u0016J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0015\u001a\u00020\u0016J\u000e\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u001a\u001a\u00020\u001bJ\u0016\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001bJ\u000e\u0010 \u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010!\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010\"\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u001eJ\u0006\u0010#\u001a\u00020\u0005J\u0006\u0010$\u001a\u00020\u0005J\u0006\u0010%\u001a\u00020\u0005J\u0006\u0010&\u001a\u00020\u0005J\u000e\u0010'\u001a\u00020\u001b2\u0006\u0010\u0015\u001a\u00020\u0016J\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0005H\u0002J\b\u0010+\u001a\u00020\u001bH\u0002J\u0010\u0010,\u001a\u00020\u00052\b\u0010-\u001a\u0004\u0018\u00010\u0005J\u0012\u0010.\u001a\u0004\u0018\u00010\u00052\u0006\u0010/\u001a\u00020\u0005H\u0002J\u0006\u00100\u001a\u00020\u001bR\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Luts/sdk/modules/DCloudUniGetDeviceInfo/DeviceUtil$Companion;", "", "<init>", "()V", "customOS", "", "customOSVersion", "rootRelatedDirs", "Lio/dcloud/uts/UTSArray;", "KEY_HARMONYOS_VERSION_NAME", "KEY_EMUI_VERSION_NAME", "KEY_MAGICUI_VERSION", "KEY_MIUI_VERSION_NAME", "KEY_COLOROS_VERSION_NAME", "KEY_VIVO_VERSION_NAME", "KEY_VIVO_VERSION", "KEY_ONEPLUS_VERSION_NAME", "KEY_FLYME_VERSION_NAME", "KEY_NUBIA_VERSION_NAME", "KEY_NUBIA_VERSION_CODE", "getOrientation", "activity", "Landroid/app/Activity;", "getScaledDensity", "", "getSystemUIModeType", "hasRootPrivilege", "", "isSimulator", "context", "Landroid/content/Context;", "sampleSensor", "getDeviceID", "getOsLanguage", "getOsLanguageNormal", "getAppInnerVersion", "getOaid", "getRomName", "getRomVersion", "isTablet", "setCustomInfo", "", "phoneBrand", "isHarmonyOS", "deleteSpaceAndToUpperCase", "str", "getSystemPropertyValue", "propName", "listeningForADB", "uni-getDeviceInfo_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getOrientation(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            if (activity.getResources().getConfiguration().orientation == 2) {
                return "landscape";
            }
            return "portrait";
        }

        public final Number getScaledDensity(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            Object systemService = activity.getSystemService("window");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) systemService).getDefaultDisplay().getRealMetrics(displayMetrics);
            return Float.valueOf(displayMetrics.scaledDensity);
        }

        public final String getSystemUIModeType(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            Object systemService = activity.getSystemService("uimode");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.UiModeManager");
            switch (((UiModeManager) systemService).getCurrentModeType()) {
                case 0:
                    return Constants.Name.UNDEFINED;
                case 1:
                    if (DeviceUtil.INSTANCE.isTablet(activity)) {
                        return "pad";
                    }
                    return "phone";
                case 2:
                    return "pc";
                case 3:
                    return "car";
                case 4:
                    return "tv";
                case 5:
                    return "appliance";
                case 6:
                    return "watch";
                case 7:
                    return "vr";
                default:
                    return "unknown";
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x0044  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final boolean hasRootPrivilege() {
            /*
                r7 = this;
                io.dcloud.uts.UTSArray r0 = uts.sdk.modules.DCloudUniGetDeviceInfo.DeviceUtil.access$getRootRelatedDirs$cp()
                java.lang.Number r1 = r0.getLength()
                r2 = 0
                java.lang.Integer r3 = java.lang.Integer.valueOf(r2)
                java.lang.Number r3 = (java.lang.Number) r3
            Lf:
                int r4 = io.dcloud.uts.NumberKt.compareTo(r3, r1)
                r5 = 1
                if (r4 >= 0) goto L2e
                java.lang.Object r4 = r0.get(r3)
                java.lang.String r4 = (java.lang.String) r4
                java.io.File r6 = new java.io.File
                r6.<init>(r4)
                boolean r4 = r6.exists()
                if (r4 == 0) goto L29
                r0 = 1
                goto L2f
            L29:
                java.lang.Number r3 = io.dcloud.uts.NumberKt.inc(r3)
                goto Lf
            L2e:
                r0 = 0
            L2f:
                java.lang.String r1 = android.os.Build.TAGS
                if (r1 == 0) goto L44
                java.lang.String r1 = android.os.Build.TAGS
                java.lang.String r3 = "TAGS"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r3)
                java.lang.String r3 = "test-keys"
                r4 = 2
                r6 = 0
                boolean r1 = io.dcloud.uts.StringKt.includes$default(r1, r3, r6, r4, r6)
                if (r1 != 0) goto L46
            L44:
                if (r0 == 0) goto L47
            L46:
                return r5
            L47:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: uts.sdk.modules.DCloudUniGetDeviceInfo.DeviceUtil.Companion.hasRootPrivilege():boolean");
        }

        public final boolean isSimulator(Context context, boolean sampleSensor) {
            Intrinsics.checkNotNullParameter(context, "context");
            return EmulatorCheckUtil.INSTANCE.getSingleInstance().emulatorCheck(context, sampleSensor);
        }

        public final String getDeviceID(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return UTSAndroid.INSTANCE.getDeviceID(context);
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

        public final String getOaid() {
            return UTSAndroid.INSTANCE.getOAID();
        }

        public final String getRomName() {
            Companion companion = DeviceUtil.INSTANCE;
            String MANUFACTURER = Build.MANUFACTURER;
            Intrinsics.checkNotNullExpressionValue(MANUFACTURER, "MANUFACTURER");
            companion.setCustomInfo(MANUFACTURER);
            String str = DeviceUtil.customOS;
            return str == null ? "" : str;
        }

        public final String getRomVersion() {
            Companion companion = DeviceUtil.INSTANCE;
            String MANUFACTURER = Build.MANUFACTURER;
            Intrinsics.checkNotNullExpressionValue(MANUFACTURER, "MANUFACTURER");
            companion.setCustomInfo(MANUFACTURER);
            String str = DeviceUtil.customOSVersion;
            return str == null ? "" : str;
        }

        public final boolean isTablet(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            return (activity.getResources().getConfiguration().screenLayout & 15) >= 3;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private final void setCustomInfo(String phoneBrand) {
            try {
                String strDeleteSpaceAndToUpperCase = DeviceUtil.INSTANCE.deleteSpaceAndToUpperCase(phoneBrand);
                switch (strDeleteSpaceAndToUpperCase.hashCode()) {
                    case -1881642058:
                        if (!strDeleteSpaceAndToUpperCase.equals("REALME")) {
                            Companion companion = DeviceUtil.INSTANCE;
                            DeviceUtil.customOS = TimeCalculator.PLATFORM_ANDROID;
                            Companion companion2 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOSVersion = Build.VERSION.RELEASE;
                            break;
                        }
                        Companion companion3 = DeviceUtil.INSTANCE;
                        DeviceUtil.customOS = "ColorOS";
                        Companion companion4 = DeviceUtil.INSTANCE;
                        DeviceUtil.customOSVersion = DeviceUtil.INSTANCE.getSystemPropertyValue(DeviceUtil.KEY_COLOROS_VERSION_NAME);
                        break;
                    case -1706170181:
                        if (!strDeleteSpaceAndToUpperCase.equals("XIAOMI")) {
                            Companion companion5 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOS = TimeCalculator.PLATFORM_ANDROID;
                            Companion companion22 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOSVersion = Build.VERSION.RELEASE;
                            break;
                        }
                        Companion companion6 = DeviceUtil.INSTANCE;
                        DeviceUtil.customOS = "MIUI";
                        Companion companion7 = DeviceUtil.INSTANCE;
                        DeviceUtil.customOSVersion = DeviceUtil.INSTANCE.getSystemPropertyValue(DeviceUtil.KEY_MIUI_VERSION_NAME);
                        break;
                    case -602397472:
                        if (!strDeleteSpaceAndToUpperCase.equals("ONEPLUS")) {
                            Companion companion52 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOS = TimeCalculator.PLATFORM_ANDROID;
                            Companion companion222 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOSVersion = Build.VERSION.RELEASE;
                            break;
                        } else {
                            Companion companion8 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOS = "HydrogenOS";
                            Companion companion9 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOSVersion = DeviceUtil.INSTANCE.getSystemPropertyValue(DeviceUtil.KEY_ONEPLUS_VERSION_NAME);
                            break;
                        }
                    case 2432928:
                        if (!strDeleteSpaceAndToUpperCase.equals("OPPO")) {
                            Companion companion522 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOS = TimeCalculator.PLATFORM_ANDROID;
                            Companion companion2222 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOSVersion = Build.VERSION.RELEASE;
                            break;
                        }
                        Companion companion32 = DeviceUtil.INSTANCE;
                        DeviceUtil.customOS = "ColorOS";
                        Companion companion42 = DeviceUtil.INSTANCE;
                        DeviceUtil.customOSVersion = DeviceUtil.INSTANCE.getSystemPropertyValue(DeviceUtil.KEY_COLOROS_VERSION_NAME);
                        break;
                    case 2634924:
                        if (!strDeleteSpaceAndToUpperCase.equals("VIVO")) {
                            Companion companion5222 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOS = TimeCalculator.PLATFORM_ANDROID;
                            Companion companion22222 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOSVersion = Build.VERSION.RELEASE;
                            break;
                        } else {
                            Companion companion10 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOS = "Funtouch";
                            Companion companion11 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOSVersion = DeviceUtil.INSTANCE.getSystemPropertyValue(DeviceUtil.KEY_VIVO_VERSION);
                            break;
                        }
                    case 68924490:
                        if (!strDeleteSpaceAndToUpperCase.equals("HONOR")) {
                            Companion companion52222 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOS = TimeCalculator.PLATFORM_ANDROID;
                            Companion companion222222 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOSVersion = Build.VERSION.RELEASE;
                            break;
                        } else if (!DeviceUtil.INSTANCE.isHarmonyOS()) {
                            if (!TextUtils.isEmpty(DeviceUtil.INSTANCE.getSystemPropertyValue(DeviceUtil.KEY_MAGICUI_VERSION))) {
                                Companion companion12 = DeviceUtil.INSTANCE;
                                DeviceUtil.customOS = "MagicUI";
                                Companion companion13 = DeviceUtil.INSTANCE;
                                DeviceUtil.customOSVersion = DeviceUtil.INSTANCE.getSystemPropertyValue(DeviceUtil.KEY_MAGICUI_VERSION);
                                break;
                            } else {
                                Companion companion14 = DeviceUtil.INSTANCE;
                                DeviceUtil.customOS = "EMUI";
                                Companion companion15 = DeviceUtil.INSTANCE;
                                DeviceUtil.customOSVersion = DeviceUtil.INSTANCE.getSystemPropertyValue(DeviceUtil.KEY_EMUI_VERSION_NAME);
                                break;
                            }
                        } else {
                            Companion companion16 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOS = "HarmonyOS";
                            if (!TextUtils.isEmpty(DeviceUtil.INSTANCE.getSystemPropertyValue(DeviceUtil.KEY_HARMONYOS_VERSION_NAME))) {
                                Companion companion17 = DeviceUtil.INSTANCE;
                                DeviceUtil.customOSVersion = DeviceUtil.INSTANCE.getSystemPropertyValue(DeviceUtil.KEY_HARMONYOS_VERSION_NAME);
                                break;
                            } else {
                                Companion companion18 = DeviceUtil.INSTANCE;
                                DeviceUtil.customOSVersion = "";
                                break;
                            }
                        }
                    case 73239724:
                        if (!strDeleteSpaceAndToUpperCase.equals("MEIZU")) {
                            Companion companion522222 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOS = TimeCalculator.PLATFORM_ANDROID;
                            Companion companion2222222 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOSVersion = Build.VERSION.RELEASE;
                            break;
                        } else {
                            Companion companion19 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOS = "Flyme";
                            Companion companion20 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOSVersion = DeviceUtil.INSTANCE.getSystemPropertyValue(DeviceUtil.KEY_FLYME_VERSION_NAME);
                            break;
                        }
                    case 74632627:
                        if (!strDeleteSpaceAndToUpperCase.equals("NUBIA")) {
                            Companion companion5222222 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOS = TimeCalculator.PLATFORM_ANDROID;
                            Companion companion22222222 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOSVersion = Build.VERSION.RELEASE;
                            break;
                        } else {
                            Companion companion21 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOS = DeviceUtil.INSTANCE.getSystemPropertyValue(DeviceUtil.KEY_NUBIA_VERSION_NAME);
                            Companion companion23 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOSVersion = DeviceUtil.INSTANCE.getSystemPropertyValue(DeviceUtil.KEY_NUBIA_VERSION_CODE);
                            break;
                        }
                    case 77852109:
                        if (!strDeleteSpaceAndToUpperCase.equals("REDMI")) {
                            Companion companion52222222 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOS = TimeCalculator.PLATFORM_ANDROID;
                            Companion companion222222222 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOSVersion = Build.VERSION.RELEASE;
                            break;
                        }
                        Companion companion62 = DeviceUtil.INSTANCE;
                        DeviceUtil.customOS = "MIUI";
                        Companion companion72 = DeviceUtil.INSTANCE;
                        DeviceUtil.customOSVersion = DeviceUtil.INSTANCE.getSystemPropertyValue(DeviceUtil.KEY_MIUI_VERSION_NAME);
                        break;
                    case 2141820391:
                        if (!strDeleteSpaceAndToUpperCase.equals("HUAWEI")) {
                            Companion companion522222222 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOS = TimeCalculator.PLATFORM_ANDROID;
                            Companion companion2222222222 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOSVersion = Build.VERSION.RELEASE;
                            break;
                        } else if (!DeviceUtil.INSTANCE.isHarmonyOS()) {
                            Companion companion24 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOS = "EMUI";
                            Companion companion25 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOSVersion = DeviceUtil.INSTANCE.getSystemPropertyValue(DeviceUtil.KEY_EMUI_VERSION_NAME);
                            break;
                        } else {
                            Companion companion26 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOSVersion = DeviceUtil.INSTANCE.getSystemPropertyValue(DeviceUtil.KEY_HARMONYOS_VERSION_NAME);
                            Companion companion27 = DeviceUtil.INSTANCE;
                            DeviceUtil.customOS = "HarmonyOS";
                            break;
                        }
                    default:
                        Companion companion5222222222 = DeviceUtil.INSTANCE;
                        DeviceUtil.customOS = TimeCalculator.PLATFORM_ANDROID;
                        Companion companion22222222222 = DeviceUtil.INSTANCE;
                        DeviceUtil.customOSVersion = Build.VERSION.RELEASE;
                        break;
                }
            } catch (Exception unused) {
            }
        }

        private final boolean isHarmonyOS() throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
            try {
                Class<?> cls = Class.forName("com.huawei.system.BuildEx");
                Intrinsics.checkNotNull(cls.getMethod("getOsBrand", null).invoke(cls, null), "null cannot be cast to non-null type kotlin.String");
                return !TextUtils.isEmpty((String) r0);
            } catch (Exception unused) {
                return false;
            }
        }

        public final String deleteSpaceAndToUpperCase(String str) {
            if (TextUtils.isEmpty(str)) {
                return "";
            }
            Intrinsics.checkNotNull(str);
            return StringKt.toUpperCase(StringKt.replace(str, Operators.SPACE_STR, ""));
        }

        private final String getSystemPropertyValue(String propName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            try {
                Object objInvoke = Class.forName("android.os.SystemProperties").getMethod("get", Class.forName("java.lang.String")).invoke(null, propName);
                if (objInvoke != null) {
                    return (String) objInvoke;
                }
            } catch (Exception unused) {
            }
            return null;
        }

        public final boolean listeningForADB() throws IOException {
            String[] strArr = {"/bin/sh", "-c", "getprop | grep init.svc.adbd"};
            try {
                Runtime.getRuntime().exec(strArr);
            } catch (Exception unused) {
                strArr = new String[]{"/system/bin/sh", "-c", "getprop | grep init.svc.adbd"};
                try {
                    Runtime.getRuntime().exec(strArr);
                } catch (Exception unused2) {
                    return false;
                }
            }
            Process processExec = Runtime.getRuntime().exec(strArr);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processExec.getInputStream(), "utf-8"));
            char[] cArr = new char[1024];
            boolean zIncludes$default = false;
            while (true) {
                int i = bufferedReader.read(cArr);
                if (i != -1) {
                    zIncludes$default = StringKt.includes$default(new String(cArr, 0, i), "running", null, 2, null);
                } else {
                    processExec.getInputStream().close();
                    bufferedReader.close();
                    return zIncludes$default;
                }
            }
        }
    }
}
