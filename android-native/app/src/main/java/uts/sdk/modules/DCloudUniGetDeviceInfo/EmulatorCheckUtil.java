package uts.sdk.modules.DCloudUniGetDeviceInfo;

import android.content.Context;
import android.hardware.SensorManager;
import com.taobao.weex.WXEnvironment;
import io.dcloud.uts.NumberKt;
import io.dcloud.uts.StringKt;
import io.dcloud.uts.UTSArray;
import io.dcloud.uts.UTSArrayKt;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: index.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0005\b\u0016\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005H\u0016J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\nH\u0002J\b\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u000e\u001a\u00020\rH\u0002J\b\u0010\u000f\u001a\u00020\rH\u0002J\b\u0010\u0010\u001a\u00020\rH\u0002J\b\u0010\u0011\u001a\u00020\rH\u0002J\b\u0010\u0012\u001a\u00020\rH\u0002J\b\u0010\u0013\u001a\u00020\rH\u0002J\b\u0010\u0014\u001a\u00020\rH\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u0010\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u0010\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u0010\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0002¨\u0006\u001b"}, d2 = {"Luts/sdk/modules/DCloudUniGetDeviceInfo/EmulatorCheckUtil;", "", "<init>", "()V", "emulatorCheck", "", "context", "Landroid/content/Context;", "sampleSensor", "getProperty", "", "propName", "checkFeaturesByHardware", "Luts/sdk/modules/DCloudUniGetDeviceInfo/CheckResult;", "checkFeaturesByFlavor", "checkPkgNameForEmulator", "checkFeaturesByModel", "checkFeaturesByManufacturer", "checkFeaturesByBoard", "checkFeaturesByPlatform", "checkFeaturesByBaseBand", "getSensorNumber", "", "supportCameraFlash", "supportBluetooth", "hasLightSensor", "Companion", "uni-getDeviceInfo_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class EmulatorCheckUtil {
    private static EmulatorCheckUtil INSTANCE;
    private static final int RESULT_MAYBE_EMULATOR = 0;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final int RESULT_EMULATOR = 1;
    private static final int RESULT_UNKNOWN = 2;
    private static UTSArray<String> known_pkgNames = UTSArrayKt._uA("sdcard/Android/data/com.bluestacks.home", "sdcard/Android/data/com.bluestacks.settings", "sdcard/Android/data/com.microvirt.guide", "sdcard/Android/data/com.microvirt.launcher2");

    public boolean emulatorCheck(Context context, boolean sampleSensor) {
        Intrinsics.checkNotNullParameter(context, "context");
        Number numberInc = (Number) 0;
        int result = checkFeaturesByHardware().getResult();
        int i = RESULT_MAYBE_EMULATOR;
        if (result == i) {
            numberInc = NumberKt.inc(numberInc);
        } else if (result == RESULT_EMULATOR) {
            return true;
        }
        int result2 = checkPkgNameForEmulator().getResult();
        if (result2 == i) {
            numberInc = NumberKt.inc(numberInc);
        } else if (result2 == RESULT_EMULATOR) {
            return true;
        }
        int result3 = checkFeaturesByFlavor().getResult();
        if (result3 == i) {
            numberInc = NumberKt.inc(numberInc);
        } else if (result3 == RESULT_EMULATOR) {
            return true;
        }
        int result4 = checkFeaturesByModel().getResult();
        if (result4 == i) {
            numberInc = NumberKt.inc(numberInc);
        } else if (result4 == RESULT_EMULATOR) {
            return true;
        }
        int result5 = checkFeaturesByManufacturer().getResult();
        if (result5 == i) {
            numberInc = NumberKt.inc(numberInc);
        } else if (result5 == RESULT_EMULATOR) {
            return true;
        }
        int result6 = checkFeaturesByBoard().getResult();
        if (result6 == i) {
            numberInc = NumberKt.inc(numberInc);
        } else if (result6 == RESULT_EMULATOR) {
            return true;
        }
        int result7 = checkFeaturesByPlatform().getResult();
        if (result7 == i) {
            numberInc = NumberKt.inc(numberInc);
        } else if (result7 == RESULT_EMULATOR) {
            return true;
        }
        int result8 = checkFeaturesByBaseBand().getResult();
        if (result8 == i) {
            numberInc = NumberKt.plus(numberInc, (Number) 2);
        } else if (result8 == RESULT_EMULATOR) {
            return true;
        }
        if (!supportCameraFlash(context)) {
            numberInc = NumberKt.inc(numberInc);
        }
        if (!supportBluetooth(context)) {
            numberInc = NumberKt.inc(numberInc);
        }
        if (sampleSensor) {
            if (getSensorNumber(context) <= 7) {
                numberInc = NumberKt.inc(numberInc);
            }
            if (!hasLightSensor(context)) {
                numberInc = NumberKt.inc(numberInc);
            }
        }
        return NumberKt.compareTo(numberInc, (Number) 3) > 0;
    }

    private final String getProperty(String propName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            Object objInvoke = Class.forName("android.os.SystemProperties").getMethod("get", Class.forName("java.lang.String")).invoke(null, propName);
            if (objInvoke != null) {
                return (String) objInvoke;
            }
        } catch (Exception unused) {
        }
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x006f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final uts.sdk.modules.DCloudUniGetDeviceInfo.CheckResult checkFeaturesByHardware() throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            r3 = this;
            java.lang.String r0 = "ro.hardware"
            java.lang.String r0 = r3.getProperty(r0)
            if (r0 != 0) goto L11
            uts.sdk.modules.DCloudUniGetDeviceInfo.CheckResult r0 = new uts.sdk.modules.DCloudUniGetDeviceInfo.CheckResult
            int r1 = uts.sdk.modules.DCloudUniGetDeviceInfo.EmulatorCheckUtil.RESULT_MAYBE_EMULATOR
            r2 = 0
            r0.<init>(r1, r2)
            return r0
        L11:
            java.util.Locale r1 = java.util.Locale.ENGLISH
            java.lang.String r2 = "ENGLISH"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r2)
            java.lang.String r1 = r0.toLowerCase(r1)
            java.lang.String r2 = "this as java.lang.String).toLowerCase(locale)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r2)
            int r2 = r1.hashCode()
            switch(r2) {
                case -1367724016: goto L5f;
                case -822798509: goto L56;
                case 109271: goto L4d;
                case 3570999: goto L44;
                case 3613077: goto L3b;
                case 100361430: goto L32;
                case 937844646: goto L29;
                default: goto L28;
            }
        L28:
            goto L6f
        L29:
            java.lang.String r2 = "android_x86"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L68
            goto L6f
        L32:
            java.lang.String r2 = "intel"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L6f
            goto L68
        L3b:
            java.lang.String r2 = "vbox"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L68
            goto L6f
        L44:
            java.lang.String r2 = "ttvm"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L68
            goto L6f
        L4d:
            java.lang.String r2 = "nox"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L68
            goto L6f
        L56:
            java.lang.String r2 = "vbox86"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L68
            goto L6f
        L5f:
            java.lang.String r2 = "cancro"
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L68
            goto L6f
        L68:
            int r1 = uts.sdk.modules.DCloudUniGetDeviceInfo.EmulatorCheckUtil.RESULT_EMULATOR
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            goto L75
        L6f:
            int r1 = uts.sdk.modules.DCloudUniGetDeviceInfo.EmulatorCheckUtil.RESULT_UNKNOWN
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
        L75:
            uts.sdk.modules.DCloudUniGetDeviceInfo.CheckResult r2 = new uts.sdk.modules.DCloudUniGetDeviceInfo.CheckResult
            int r1 = r1.intValue()
            r2.<init>(r1, r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: uts.sdk.modules.DCloudUniGetDeviceInfo.EmulatorCheckUtil.checkFeaturesByHardware():uts.sdk.modules.DCloudUniGetDeviceInfo.CheckResult");
    }

    private final CheckResult checkFeaturesByFlavor() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Integer numValueOf;
        String property = getProperty("ro.build.flavor");
        if (property == null) {
            return new CheckResult(RESULT_MAYBE_EMULATOR, null);
        }
        Locale ENGLISH = Locale.ENGLISH;
        Intrinsics.checkNotNullExpressionValue(ENGLISH, "ENGLISH");
        String lowerCase = property.toLowerCase(ENGLISH);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(locale)");
        if (StringKt.includes$default(lowerCase, "vbox", null, 2, null) || StringKt.includes$default(lowerCase, "sdk_gphone", null, 2, null)) {
            numValueOf = Integer.valueOf(RESULT_EMULATOR);
        } else {
            numValueOf = Integer.valueOf(RESULT_UNKNOWN);
        }
        return new CheckResult(numValueOf.intValue(), property);
    }

    private final CheckResult checkPkgNameForEmulator() {
        int i = RESULT_UNKNOWN;
        Number numberInc = 0;
        for (Number numberInc2 = numberInc; NumberKt.compareTo(numberInc2, known_pkgNames.getLength()) < 0; numberInc2 = NumberKt.inc(numberInc2)) {
            if (new File(known_pkgNames.get(numberInc2)).exists()) {
                numberInc = NumberKt.inc(numberInc);
            } else {
                i = RESULT_MAYBE_EMULATOR;
            }
            if (NumberKt.compareTo(numberInc, (Number) 2) > 0) {
                break;
            }
        }
        Number number = numberInc;
        if (NumberKt.numberEquals(number, 1)) {
            i = RESULT_MAYBE_EMULATOR;
        } else if (NumberKt.numberEquals(number, 2)) {
            i = RESULT_EMULATOR;
        }
        return new CheckResult(i, "PkgName");
    }

    private final CheckResult checkFeaturesByModel() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Integer numValueOf;
        String property = getProperty("ro.product.model");
        if (property == null) {
            return new CheckResult(RESULT_MAYBE_EMULATOR, null);
        }
        Locale ENGLISH = Locale.ENGLISH;
        Intrinsics.checkNotNullExpressionValue(ENGLISH, "ENGLISH");
        String lowerCase = property.toLowerCase(ENGLISH);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(locale)");
        if (StringKt.includes$default(lowerCase, "google_sdk", null, 2, null) || StringKt.includes$default(lowerCase, "emulator", null, 2, null) || StringKt.includes$default(lowerCase, "android sdk built for x86", null, 2, null)) {
            numValueOf = Integer.valueOf(RESULT_EMULATOR);
        } else {
            numValueOf = Integer.valueOf(RESULT_UNKNOWN);
        }
        return new CheckResult(numValueOf.intValue(), property);
    }

    private final CheckResult checkFeaturesByManufacturer() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Integer numValueOf;
        String property = getProperty("ro.product.manufacturer");
        if (property == null) {
            return new CheckResult(RESULT_MAYBE_EMULATOR, null);
        }
        Locale ENGLISH = Locale.ENGLISH;
        Intrinsics.checkNotNullExpressionValue(ENGLISH, "ENGLISH");
        String lowerCase = property.toLowerCase(ENGLISH);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(locale)");
        if (StringKt.includes$default(lowerCase, "genymotion", null, 2, null) || StringKt.includes$default(lowerCase, "netease", null, 2, null)) {
            numValueOf = Integer.valueOf(RESULT_EMULATOR);
        } else {
            numValueOf = Integer.valueOf(RESULT_UNKNOWN);
        }
        return new CheckResult(numValueOf.intValue(), property);
    }

    private final CheckResult checkFeaturesByBoard() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Integer numValueOf;
        String property = getProperty("ro.product.board");
        if (property == null) {
            return new CheckResult(RESULT_MAYBE_EMULATOR, null);
        }
        Locale ENGLISH = Locale.ENGLISH;
        Intrinsics.checkNotNullExpressionValue(ENGLISH, "ENGLISH");
        String lowerCase = property.toLowerCase(ENGLISH);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(locale)");
        if (StringKt.includes$default(lowerCase, WXEnvironment.OS, null, 2, null) || StringKt.includes$default(lowerCase, "goldfish", null, 2, null)) {
            numValueOf = Integer.valueOf(RESULT_EMULATOR);
        } else {
            numValueOf = Integer.valueOf(RESULT_UNKNOWN);
        }
        return new CheckResult(numValueOf.intValue(), property);
    }

    private final CheckResult checkFeaturesByPlatform() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Integer numValueOf;
        String property = getProperty("ro.board.platform");
        if (property == null) {
            return new CheckResult(RESULT_MAYBE_EMULATOR, null);
        }
        Locale ENGLISH = Locale.ENGLISH;
        Intrinsics.checkNotNullExpressionValue(ENGLISH, "ENGLISH");
        String lowerCase = property.toLowerCase(ENGLISH);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(locale)");
        if (StringKt.includes$default(lowerCase, WXEnvironment.OS, null, 2, null)) {
            numValueOf = Integer.valueOf(RESULT_EMULATOR);
        } else {
            numValueOf = Integer.valueOf(RESULT_UNKNOWN);
        }
        return new CheckResult(numValueOf.intValue(), property);
    }

    private final CheckResult checkFeaturesByBaseBand() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Integer numValueOf;
        String property = getProperty("gsm.version.baseband");
        if (property == null) {
            return new CheckResult(RESULT_MAYBE_EMULATOR, null);
        }
        if (StringKt.includes$default(property, "1.0.0.0", null, 2, null)) {
            numValueOf = Integer.valueOf(RESULT_EMULATOR);
        } else {
            numValueOf = Integer.valueOf(RESULT_UNKNOWN);
        }
        return new CheckResult(numValueOf.intValue(), property);
    }

    private final int getSensorNumber(Context context) {
        Object systemService = context.getSystemService("sensor");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.hardware.SensorManager");
        return ((SensorManager) systemService).getSensorList(-1).size();
    }

    private final boolean supportCameraFlash(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.camera.flash");
    }

    private final boolean supportBluetooth(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.bluetooth");
    }

    private final boolean hasLightSensor(Context context) {
        Object systemService = context.getSystemService("sensor");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.hardware.SensorManager");
        return ((SensorManager) systemService).getDefaultSensor(5) != null;
    }

    /* compiled from: index.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0011\u001a\u00020\u0005R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u0007X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u0007X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0014\u0010\f\u001a\u00020\u0007X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\tR\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Luts/sdk/modules/DCloudUniGetDeviceInfo/EmulatorCheckUtil$Companion;", "", "<init>", "()V", "INSTANCE", "Luts/sdk/modules/DCloudUniGetDeviceInfo/EmulatorCheckUtil;", "RESULT_MAYBE_EMULATOR", "", "getRESULT_MAYBE_EMULATOR", "()I", "RESULT_EMULATOR", "getRESULT_EMULATOR", "RESULT_UNKNOWN", "getRESULT_UNKNOWN", "known_pkgNames", "Lio/dcloud/uts/UTSArray;", "", "getSingleInstance", "uni-getDeviceInfo_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int getRESULT_MAYBE_EMULATOR() {
            return EmulatorCheckUtil.RESULT_MAYBE_EMULATOR;
        }

        public final int getRESULT_EMULATOR() {
            return EmulatorCheckUtil.RESULT_EMULATOR;
        }

        public final int getRESULT_UNKNOWN() {
            return EmulatorCheckUtil.RESULT_UNKNOWN;
        }

        public final EmulatorCheckUtil getSingleInstance() {
            if (EmulatorCheckUtil.INSTANCE == null) {
                Companion companion = EmulatorCheckUtil.INSTANCE;
                EmulatorCheckUtil.INSTANCE = new EmulatorCheckUtil();
            }
            EmulatorCheckUtil emulatorCheckUtil = EmulatorCheckUtil.INSTANCE;
            Intrinsics.checkNotNull(emulatorCheckUtil);
            return emulatorCheckUtil;
        }
    }
}
