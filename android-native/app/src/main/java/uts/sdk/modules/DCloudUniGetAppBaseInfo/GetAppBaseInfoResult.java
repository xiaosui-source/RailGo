package uts.sdk.modules.DCloudUniGetAppBaseInfo;

import androidx.core.view.accessibility.AccessibilityEventCompat;
import com.taobao.weex.common.WXConfig;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.uts.UTSObject;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0004\n\u0002\bI\b\u0016\u0018\u00002\u00020\u0001B×\u0002\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0018\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0018\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u0018\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b!\u0010\"R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010$\"\u0004\b(\u0010&R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010$\"\u0004\b*\u0010&R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010$\"\u0004\b,\u0010&R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010$\"\u0004\b.\u0010&R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010$\"\u0004\b0\u0010&R\u001c\u0010\t\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010$\"\u0004\b2\u0010&R\u001c\u0010\n\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010$\"\u0004\b4\u0010&R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u0010$\"\u0004\b6\u0010&R\u001c\u0010\f\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u0010$\"\u0004\b8\u0010&R\u001c\u0010\r\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010$\"\u0004\b:\u0010&R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010$\"\u0004\b<\u0010&R\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010$\"\u0004\b>\u0010&R\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010$\"\u0004\b@\u0010&R\u001e\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0096\u000e¢\u0006\u0010\n\u0002\u0010D\u001a\u0004\b\u0011\u0010A\"\u0004\bB\u0010CR\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010$\"\u0004\bF\u0010&R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bG\u0010$\"\u0004\bH\u0010&R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bI\u0010$\"\u0004\bJ\u0010&R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bK\u0010$\"\u0004\bL\u0010&R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bM\u0010N\"\u0004\bO\u0010PR\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u0018X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010N\"\u0004\bR\u0010PR\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u0018X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bS\u0010N\"\u0004\bT\u0010PR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bU\u0010$\"\u0004\bV\u0010&R\u001c\u0010\u001c\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bW\u0010$\"\u0004\bX\u0010&R\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bY\u0010$\"\u0004\bZ\u0010&R\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b[\u0010$\"\u0004\b\\\u0010&R\u001c\u0010\u001f\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b]\u0010$\"\u0004\b^\u0010&R\u001c\u0010 \u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b_\u0010$\"\u0004\b`\u0010&¨\u0006a"}, d2 = {"Luts/sdk/modules/DCloudUniGetAppBaseInfo/GetAppBaseInfoResult;", "Lio/dcloud/uts/UTSObject;", "appId", "", WXConfig.appName, WXConfig.appVersion, "appVersionCode", "appLanguage", IApp.ConfigProperty.CONFIG_LANGUAGE, "version", "appWgtVersion", "hostLanguage", "hostVersion", "hostName", "hostPackageName", "hostSDKVersion", "hostTheme", "isUniAppX", "", "uniCompileVersion", "uniCompilerVersion", "uniPlatform", "uniRuntimeVersion", "uniCompileVersionCode", "", "uniCompilerVersionCode", "uniRuntimeVersionCode", "packageName", "bundleName", "bundleId", "signature", "appTheme", AbsoluteConst.XML_CHANNEL, "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAppId", "()Ljava/lang/String;", "setAppId", "(Ljava/lang/String;)V", "getAppName", "setAppName", "getAppVersion", "setAppVersion", "getAppVersionCode", "setAppVersionCode", "getAppLanguage", "setAppLanguage", "getLanguage", "setLanguage", "getVersion", "setVersion", "getAppWgtVersion", "setAppWgtVersion", "getHostLanguage", "setHostLanguage", "getHostVersion", "setHostVersion", "getHostName", "setHostName", "getHostPackageName", "setHostPackageName", "getHostSDKVersion", "setHostSDKVersion", "getHostTheme", "setHostTheme", "()Ljava/lang/Boolean;", "setUniAppX", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "getUniCompileVersion", "setUniCompileVersion", "getUniCompilerVersion", "setUniCompilerVersion", "getUniPlatform", "setUniPlatform", "getUniRuntimeVersion", "setUniRuntimeVersion", "getUniCompileVersionCode", "()Ljava/lang/Number;", "setUniCompileVersionCode", "(Ljava/lang/Number;)V", "getUniCompilerVersionCode", "setUniCompilerVersionCode", "getUniRuntimeVersionCode", "setUniRuntimeVersionCode", "getPackageName", "setPackageName", "getBundleName", "setBundleName", "getBundleId", "setBundleId", "getSignature", "setSignature", "getAppTheme", "setAppTheme", "getChannel", "setChannel", "uni-getAppBaseInfo_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class GetAppBaseInfoResult extends UTSObject {
    private String appId;
    private String appLanguage;
    private String appName;
    private String appTheme;
    private String appVersion;
    private String appVersionCode;
    private String appWgtVersion;
    private String bundleId;
    private String bundleName;
    private String channel;
    private String hostLanguage;
    private String hostName;
    private String hostPackageName;
    private String hostSDKVersion;
    private String hostTheme;
    private String hostVersion;
    private Boolean isUniAppX;
    private String language;
    private String packageName;
    private String signature;
    private String uniCompileVersion;
    private Number uniCompileVersionCode;
    private String uniCompilerVersion;
    private Number uniCompilerVersionCode;
    private String uniPlatform;
    private String uniRuntimeVersion;
    private Number uniRuntimeVersionCode;
    private String version;

    public GetAppBaseInfoResult() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 268435455, null);
    }

    public /* synthetic */ GetAppBaseInfoResult(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, Boolean bool, String str15, String str16, String str17, String str18, Number number, Number number2, Number number3, String str19, String str20, String str21, String str22, String str23, String str24, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : str4, (i & 16) != 0 ? null : str5, (i & 32) != 0 ? null : str6, (i & 64) != 0 ? null : str7, (i & 128) != 0 ? null : str8, (i & 256) != 0 ? null : str9, (i & 512) != 0 ? null : str10, (i & 1024) != 0 ? null : str11, (i & 2048) != 0 ? null : str12, (i & 4096) != 0 ? null : str13, (i & 8192) != 0 ? null : str14, (i & 16384) != 0 ? null : bool, (i & 32768) != 0 ? null : str15, (i & 65536) != 0 ? null : str16, (i & 131072) != 0 ? null : str17, (i & 262144) != 0 ? null : str18, (i & 524288) != 0 ? null : number, (i & 1048576) != 0 ? null : number2, (i & 2097152) != 0 ? null : number3, (i & 4194304) != 0 ? null : str19, (i & 8388608) != 0 ? null : str20, (i & 16777216) != 0 ? null : str21, (i & 33554432) != 0 ? null : str22, (i & AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL) != 0 ? null : str23, (i & 134217728) != 0 ? null : str24);
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String str) {
        this.appName = str;
    }

    public String getAppVersion() {
        return this.appVersion;
    }

    public void setAppVersion(String str) {
        this.appVersion = str;
    }

    public String getAppVersionCode() {
        return this.appVersionCode;
    }

    public void setAppVersionCode(String str) {
        this.appVersionCode = str;
    }

    public String getAppLanguage() {
        return this.appLanguage;
    }

    public void setAppLanguage(String str) {
        this.appLanguage = str;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public String getAppWgtVersion() {
        return this.appWgtVersion;
    }

    public void setAppWgtVersion(String str) {
        this.appWgtVersion = str;
    }

    public String getHostLanguage() {
        return this.hostLanguage;
    }

    public void setHostLanguage(String str) {
        this.hostLanguage = str;
    }

    public String getHostVersion() {
        return this.hostVersion;
    }

    public void setHostVersion(String str) {
        this.hostVersion = str;
    }

    public String getHostName() {
        return this.hostName;
    }

    public void setHostName(String str) {
        this.hostName = str;
    }

    public String getHostPackageName() {
        return this.hostPackageName;
    }

    public void setHostPackageName(String str) {
        this.hostPackageName = str;
    }

    public String getHostSDKVersion() {
        return this.hostSDKVersion;
    }

    public void setHostSDKVersion(String str) {
        this.hostSDKVersion = str;
    }

    public String getHostTheme() {
        return this.hostTheme;
    }

    public void setHostTheme(String str) {
        this.hostTheme = str;
    }

    /* renamed from: isUniAppX, reason: from getter */
    public Boolean getIsUniAppX() {
        return this.isUniAppX;
    }

    public void setUniAppX(Boolean bool) {
        this.isUniAppX = bool;
    }

    public String getUniCompileVersion() {
        return this.uniCompileVersion;
    }

    public void setUniCompileVersion(String str) {
        this.uniCompileVersion = str;
    }

    public String getUniCompilerVersion() {
        return this.uniCompilerVersion;
    }

    public void setUniCompilerVersion(String str) {
        this.uniCompilerVersion = str;
    }

    public String getUniPlatform() {
        return this.uniPlatform;
    }

    public void setUniPlatform(String str) {
        this.uniPlatform = str;
    }

    public String getUniRuntimeVersion() {
        return this.uniRuntimeVersion;
    }

    public void setUniRuntimeVersion(String str) {
        this.uniRuntimeVersion = str;
    }

    public Number getUniCompileVersionCode() {
        return this.uniCompileVersionCode;
    }

    public void setUniCompileVersionCode(Number number) {
        this.uniCompileVersionCode = number;
    }

    public Number getUniCompilerVersionCode() {
        return this.uniCompilerVersionCode;
    }

    public void setUniCompilerVersionCode(Number number) {
        this.uniCompilerVersionCode = number;
    }

    public Number getUniRuntimeVersionCode() {
        return this.uniRuntimeVersionCode;
    }

    public void setUniRuntimeVersionCode(Number number) {
        this.uniRuntimeVersionCode = number;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public String getBundleName() {
        return this.bundleName;
    }

    public void setBundleName(String str) {
        this.bundleName = str;
    }

    public String getBundleId() {
        return this.bundleId;
    }

    public void setBundleId(String str) {
        this.bundleId = str;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setSignature(String str) {
        this.signature = str;
    }

    public String getAppTheme() {
        return this.appTheme;
    }

    public void setAppTheme(String str) {
        this.appTheme = str;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String str) {
        this.channel = str;
    }

    public GetAppBaseInfoResult(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, Boolean bool, String str15, String str16, String str17, String str18, Number number, Number number2, Number number3, String str19, String str20, String str21, String str22, String str23, String str24) {
        this.appId = str;
        this.appName = str2;
        this.appVersion = str3;
        this.appVersionCode = str4;
        this.appLanguage = str5;
        this.language = str6;
        this.version = str7;
        this.appWgtVersion = str8;
        this.hostLanguage = str9;
        this.hostVersion = str10;
        this.hostName = str11;
        this.hostPackageName = str12;
        this.hostSDKVersion = str13;
        this.hostTheme = str14;
        this.isUniAppX = bool;
        this.uniCompileVersion = str15;
        this.uniCompilerVersion = str16;
        this.uniPlatform = str17;
        this.uniRuntimeVersion = str18;
        this.uniCompileVersionCode = number;
        this.uniCompilerVersionCode = number2;
        this.uniRuntimeVersionCode = number3;
        this.packageName = str19;
        this.bundleName = str20;
        this.bundleId = str21;
        this.signature = str22;
        this.appTheme = str23;
        this.channel = str24;
    }
}
