package uts.sdk.modules.DCloudUniGetDeviceInfo;

import com.taobao.weex.common.RenderTypes;
import com.taobao.weex.common.WXConfig;
import io.dcloud.uts.UTSObject;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0004\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b>\b\u0016\u0018\u00002\u00020\u0001B\u008f\u0002\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u000f\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000f\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u001b\u0010\u001cR\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u001e\"\u0004\b\"\u0010 R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u001e\"\u0004\b$\u0010 R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u001e\"\u0004\b&\u0010 R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\u001e\"\u0004\b(\u0010 R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u001e\"\u0004\b*\u0010 R\u001c\u0010\t\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u001e\"\u0004\b,\u0010 R\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u001c\u0010\f\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\u001e\"\u0004\b2\u0010 R\u001c\u0010\r\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\u001e\"\u0004\b4\u0010 R\u001e\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0096\u000e¢\u0006\u0010\n\u0002\u00108\u001a\u0004\b\u000e\u00105\"\u0004\b6\u00107R\u001e\u0010\u0010\u001a\u0004\u0018\u00010\u000fX\u0096\u000e¢\u0006\u0010\n\u0002\u00108\u001a\u0004\b\u0010\u00105\"\u0004\b9\u00107R\u001e\u0010\u0011\u001a\u0004\u0018\u00010\u000fX\u0096\u000e¢\u0006\u0010\n\u0002\u00108\u001a\u0004\b\u0011\u00105\"\u0004\b:\u00107R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010\u001e\"\u0004\b<\u0010 R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010\u001e\"\u0004\b>\u0010 R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010\u001e\"\u0004\b@\u0010 R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bA\u0010\u001e\"\u0004\bB\u0010 R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u000bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010.\"\u0004\bD\u00100R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u000bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010.\"\u0004\bF\u00100R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bG\u0010\u001e\"\u0004\bH\u0010 R\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bI\u0010\u001e\"\u0004\bJ\u0010 R\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bK\u0010\u001e\"\u0004\bL\u0010 ¨\u0006M"}, d2 = {"Luts/sdk/modules/DCloudUniGetDeviceInfo/GetDeviceInfoResult;", "Lio/dcloud/uts/UTSObject;", "brand", "", "deviceBrand", "deviceId", "model", "deviceModel", "deviceType", "deviceOrientation", "devicePixelRatio", "", "system", RenderTypes.RENDER_TYPE_NATIVE, "isRoot", "", "isSimulator", "isUSBDebugging", WXConfig.osName, "osVersion", "osLanguage", "osTheme", "osAndroidAPILevel", "osHarmonySDKAPIVersion", "osHarmonyDisplayVersion", "romName", "romVersion", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Number;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getBrand", "()Ljava/lang/String;", "setBrand", "(Ljava/lang/String;)V", "getDeviceBrand", "setDeviceBrand", "getDeviceId", "setDeviceId", "getModel", "setModel", "getDeviceModel", "setDeviceModel", "getDeviceType", "setDeviceType", "getDeviceOrientation", "setDeviceOrientation", "getDevicePixelRatio", "()Ljava/lang/Number;", "setDevicePixelRatio", "(Ljava/lang/Number;)V", "getSystem", "setSystem", "getPlatform", "setPlatform", "()Ljava/lang/Boolean;", "setRoot", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "setSimulator", "setUSBDebugging", "getOsName", "setOsName", "getOsVersion", "setOsVersion", "getOsLanguage", "setOsLanguage", "getOsTheme", "setOsTheme", "getOsAndroidAPILevel", "setOsAndroidAPILevel", "getOsHarmonySDKAPIVersion", "setOsHarmonySDKAPIVersion", "getOsHarmonyDisplayVersion", "setOsHarmonyDisplayVersion", "getRomName", "setRomName", "getRomVersion", "setRomVersion", "uni-getDeviceInfo_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class GetDeviceInfoResult extends UTSObject {
    private String brand;
    private String deviceBrand;
    private String deviceId;
    private String deviceModel;
    private String deviceOrientation;
    private Number devicePixelRatio;
    private String deviceType;
    private Boolean isRoot;
    private Boolean isSimulator;
    private Boolean isUSBDebugging;
    private String model;
    private Number osAndroidAPILevel;
    private String osHarmonyDisplayVersion;
    private Number osHarmonySDKAPIVersion;
    private String osLanguage;
    private String osName;
    private String osTheme;
    private String osVersion;
    private String platform;
    private String romName;
    private String romVersion;
    private String system;

    public GetDeviceInfoResult() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 4194303, null);
    }

    public /* synthetic */ GetDeviceInfoResult(String str, String str2, String str3, String str4, String str5, String str6, String str7, Number number, String str8, String str9, Boolean bool, Boolean bool2, Boolean bool3, String str10, String str11, String str12, String str13, Number number2, Number number3, String str14, String str15, String str16, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : str4, (i & 16) != 0 ? null : str5, (i & 32) != 0 ? null : str6, (i & 64) != 0 ? null : str7, (i & 128) != 0 ? null : number, (i & 256) != 0 ? null : str8, (i & 512) != 0 ? null : str9, (i & 1024) != 0 ? null : bool, (i & 2048) != 0 ? null : bool2, (i & 4096) != 0 ? null : bool3, (i & 8192) != 0 ? null : str10, (i & 16384) != 0 ? null : str11, (i & 32768) != 0 ? null : str12, (i & 65536) != 0 ? null : str13, (i & 131072) != 0 ? null : number2, (i & 262144) != 0 ? null : number3, (i & 524288) != 0 ? null : str14, (i & 1048576) != 0 ? null : str15, (i & 2097152) != 0 ? null : str16);
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String str) {
        this.brand = str;
    }

    public String getDeviceBrand() {
        return this.deviceBrand;
    }

    public void setDeviceBrand(String str) {
        this.deviceBrand = str;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String str) {
        this.model = str;
    }

    public String getDeviceModel() {
        return this.deviceModel;
    }

    public void setDeviceModel(String str) {
        this.deviceModel = str;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String str) {
        this.deviceType = str;
    }

    public String getDeviceOrientation() {
        return this.deviceOrientation;
    }

    public void setDeviceOrientation(String str) {
        this.deviceOrientation = str;
    }

    public Number getDevicePixelRatio() {
        return this.devicePixelRatio;
    }

    public void setDevicePixelRatio(Number number) {
        this.devicePixelRatio = number;
    }

    public String getSystem() {
        return this.system;
    }

    public void setSystem(String str) {
        this.system = str;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String str) {
        this.platform = str;
    }

    /* renamed from: isRoot, reason: from getter */
    public Boolean getIsRoot() {
        return this.isRoot;
    }

    public void setRoot(Boolean bool) {
        this.isRoot = bool;
    }

    /* renamed from: isSimulator, reason: from getter */
    public Boolean getIsSimulator() {
        return this.isSimulator;
    }

    public void setSimulator(Boolean bool) {
        this.isSimulator = bool;
    }

    /* renamed from: isUSBDebugging, reason: from getter */
    public Boolean getIsUSBDebugging() {
        return this.isUSBDebugging;
    }

    public void setUSBDebugging(Boolean bool) {
        this.isUSBDebugging = bool;
    }

    public String getOsName() {
        return this.osName;
    }

    public void setOsName(String str) {
        this.osName = str;
    }

    public String getOsVersion() {
        return this.osVersion;
    }

    public void setOsVersion(String str) {
        this.osVersion = str;
    }

    public String getOsLanguage() {
        return this.osLanguage;
    }

    public void setOsLanguage(String str) {
        this.osLanguage = str;
    }

    public String getOsTheme() {
        return this.osTheme;
    }

    public void setOsTheme(String str) {
        this.osTheme = str;
    }

    public Number getOsAndroidAPILevel() {
        return this.osAndroidAPILevel;
    }

    public void setOsAndroidAPILevel(Number number) {
        this.osAndroidAPILevel = number;
    }

    public Number getOsHarmonySDKAPIVersion() {
        return this.osHarmonySDKAPIVersion;
    }

    public void setOsHarmonySDKAPIVersion(Number number) {
        this.osHarmonySDKAPIVersion = number;
    }

    public String getOsHarmonyDisplayVersion() {
        return this.osHarmonyDisplayVersion;
    }

    public void setOsHarmonyDisplayVersion(String str) {
        this.osHarmonyDisplayVersion = str;
    }

    public String getRomName() {
        return this.romName;
    }

    public void setRomName(String str) {
        this.romName = str;
    }

    public String getRomVersion() {
        return this.romVersion;
    }

    public void setRomVersion(String str) {
        this.romVersion = str;
    }

    public GetDeviceInfoResult(String str, String str2, String str3, String str4, String str5, String str6, String str7, Number number, String str8, String str9, Boolean bool, Boolean bool2, Boolean bool3, String str10, String str11, String str12, String str13, Number number2, Number number3, String str14, String str15, String str16) {
        this.brand = str;
        this.deviceBrand = str2;
        this.deviceId = str3;
        this.model = str4;
        this.deviceModel = str5;
        this.deviceType = str6;
        this.deviceOrientation = str7;
        this.devicePixelRatio = number;
        this.system = str8;
        this.platform = str9;
        this.isRoot = bool;
        this.isSimulator = bool2;
        this.isUSBDebugging = bool3;
        this.osName = str10;
        this.osVersion = str11;
        this.osLanguage = str12;
        this.osTheme = str13;
        this.osAndroidAPILevel = number2;
        this.osHarmonySDKAPIVersion = number3;
        this.osHarmonyDisplayVersion = str14;
        this.romName = str15;
        this.romVersion = str16;
    }
}
