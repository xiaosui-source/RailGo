package io.dcloud.common.adapter.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.UiModeManager;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.common.Constants;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.utils.tools.TimeCalculator;
import io.dcloud.application.DCLoudApplicationImpl;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.NetworkTypeUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import io.dcloud.common.util.TelephonyUtil;
import io.dcloud.common.util.language.LanguageUtil;
import io.dcloud.feature.internal.sdk.SDK;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Pattern;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class DeviceInfo {
    private static final String CDMA_DATA_NETWORK = "cdma";
    private static String CONNECTION_CELL2G = null;
    private static String CONNECTION_CELL3G = null;
    private static String CONNECTION_CELL4G = null;
    private static String CONNECTION_CELL5G = null;
    private static String CONNECTION_ETHERNET = null;
    private static String CONNECTION_UNKNOW = null;
    private static final String DEFAULT_DATA_NETWORK = "default_data_network";
    public static float DEFAULT_FONT_SIZE = 0.0f;
    public static String DEVICESTATUS_JS = null;
    public static final String FILE_PROTOCOL = "file://";
    public static int HARDWAREACCELERATED_VIEW = 0;
    public static int HARDWAREACCELERATED_WINDOW = 0;
    public static final String HTTPS_PROTOCOL = "https://";
    public static final String HTTP_PROTOCOL = "http://";
    private static String NETTYPE_NONE = null;
    private static String NETTYPE_WIFI = null;
    private static final String NONE_DATA_NETWORK = "none";
    public static final int OSTYPE_ANDROID = 0;
    public static final int OSTYPE_LEOS10 = 4;
    public static final int OSTYPE_OMS10 = 3;
    public static final int OSTYPE_OMS15 = 2;
    public static final int OSTYPE_OMS20 = 1;
    private static final String SAVED_DATA_NETWORK = "saved_data_network";
    private static final String TAG = "DeviceInfo";
    public static float dpiX;
    public static float dpiY;
    public static volatile boolean isIMEShow;
    public static boolean isPrivateDirectory;
    public static boolean isVolumeButtonEnabled;
    public static String mAppAuthorities;
    public static String oaids;
    public static int osType;
    private static final String[] rootRelatedDirs;
    public static Context sApplicationContext;
    public static String sBaseFsCachePath;
    public static String sBaseFsRootFullPath;
    public static String sBaseFsRootPath;
    public static String sBaseResRootFullPath;
    public static String sBaseResRootPathName;
    public static String sCacheRootDir;
    static ConnectivityManager sConnectMgr;
    public static float sDensity;
    public static String sDeviceRootDir;
    public static String sIMEI;
    public static String sIMSI;
    public static int sInputMethodHeight;
    public static String sMAC;
    public static String sPackageName;
    public static Paint sPaint;
    public static String sPrivateDir;
    public static String sPrivateExternalDir;
    public static String sPublicDCIMDir;
    public static String sPublicDocumentsDir;
    public static String sPublicDownloadDir;
    public static String sPublicMoviesDir;
    public static String sPublicMusicDir;
    public static String sPublicPicturesDir;
    public static String sPublicRingtonesDir;
    public static char sSeparatorChar;
    public static int sStatusBarHeight;
    public static JSONObject sSystemInfo;
    public static int sDeviceSdkVer = Build.VERSION.SDK_INT;
    public static String sModel = Build.MODEL;
    public static String sBrand = Build.BRAND;
    public static long sTotalMem = -1;
    public static int sCoreNums = -1;
    public static String sVendor = Build.MANUFACTURER;
    public static String sVersion_release = Build.VERSION.RELEASE;
    public static String sLanguage = LanguageUtil.getDeviceDefLocalLanguage();
    public static boolean sNetWorkInited = false;
    private static final String GSM_DATA_NETWORK = "gsm";
    public static String sDeftDataNetwork = GSM_DATA_NETWORK;

    static {
        char c = File.separatorChar;
        sSeparatorChar = c;
        sBaseResRootPathName = String.valueOf(c);
        sPackageName = null;
        DEVICESTATUS_JS = null;
        Paint paint = new Paint();
        sPaint = paint;
        DEFAULT_FONT_SIZE = paint.getTextSize();
        osType = 0;
        sApplicationContext = null;
        sConnectMgr = null;
        isPrivateDirectory = false;
        isVolumeButtonEnabled = true;
        sPrivateExternalDir = null;
        sPrivateDir = null;
        sPublicDocumentsDir = null;
        sPublicDCIMDir = null;
        sPublicDownloadDir = null;
        sPublicMoviesDir = null;
        sPublicMusicDir = null;
        sPublicPicturesDir = null;
        sPublicRingtonesDir = null;
        CONNECTION_UNKNOW = WXInstanceApm.VALUE_ERROR_CODE_DEFAULT;
        NETTYPE_NONE = "1";
        CONNECTION_ETHERNET = "2";
        NETTYPE_WIFI = "3";
        CONNECTION_CELL2G = "4";
        CONNECTION_CELL3G = "5";
        CONNECTION_CELL4G = "6";
        CONNECTION_CELL5G = "7";
        sStatusBarHeight = 0;
        sInputMethodHeight = 0;
        HARDWAREACCELERATED_WINDOW = 0;
        HARDWAREACCELERATED_VIEW = 1;
        isIMEShow = false;
        rootRelatedDirs = new String[]{"/su", "/su/bin/su", "/sbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/data/local/su", "/system/xbin/su", "/system/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/system/bin/cufsdosck", "/system/xbin/cufsdosck", "/system/bin/cufsmgr", "/system/xbin/cufsmgr", "/system/bin/cufaevdd", "/system/xbin/cufaevdd", "/system/bin/conbb", "/system/xbin/conbb"};
    }

    public static boolean blueToothEnable(Context context) throws Exception {
        if (Build.VERSION.SDK_INT < 23 || context.checkSelfPermission("android.permission.BLUETOOTH") != -1) {
            return ((BluetoothManager) context.getSystemService("bluetooth")).getAdapter().isEnabled();
        }
        throw new Exception();
    }

    public static boolean checkCoverLoadApp() {
        boolean z;
        String bundleData = SP.getBundleData(BaseInfo.PDR, "last_app_modify_date");
        long jLastModified = new File(sApplicationContext.getPackageCodePath()).lastModified();
        Logger.d(TAG, "old_app_modify_date=" + bundleData);
        if (PdrUtil.isEquals(bundleData, String.valueOf(jLastModified))) {
            z = false;
        } else {
            SP.setBundleData(sApplicationContext, BaseInfo.PDR, "last_app_modify_date", String.valueOf(jLastModified));
            bundleData = Logger.generateTimeStamp(Logger.TIMESTAMP_YYYY_MM_DD_HH_MM_SS_SSS, new Date(jLastModified));
            Logger.d(TAG, "new_app_modify_date=" + jLastModified);
            z = true;
        }
        Logger.d(TAG, "App Modify Date=" + bundleData + ";_ret=" + z);
        return z;
    }

    public static void closeHardwareAccelerated(Activity activity, int i, Object obj) {
        Window window = null;
        if (i != HARDWAREACCELERATED_WINDOW) {
            if (i != HARDWAREACCELERATED_VIEW || sDeviceSdkVer < 11) {
                return;
            }
            ((View) obj).setLayerType(1, null);
            return;
        }
        Window window2 = (Window) obj;
        if (window2 != null) {
            window = window2;
        } else if (activity != null) {
            window = activity.getWindow();
        }
        if (window != null) {
            window.clearFlags(16777216);
        }
    }

    public static String deviceOrientation(Context context) {
        int i = context.getResources().getConfiguration().orientation;
        return i == 1 ? "portrait" : i == 2 ? "landscape" : "";
    }

    public static long getAvailMemory() {
        Context context = sApplicationContext;
        if (context == null) {
            return 0L;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    public static String getBuildValue(String str) throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getDeclaredMethod("get", String.class).invoke(cls, str);
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getCurrentAPN() {
        return NetworkTypeUtil.getCurrentAPN(DCLoudApplicationImpl.self().getContext().getApplicationContext());
    }

    public static int getDeivceSuitablePixel(Activity activity, int i) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return (int) (i * displayMetrics.density);
    }

    public static String getDevicestatus_js(IApp iApp) {
        DEVICESTATUS_JS = "(function(p){p.device.imei='%s';p.device.uuid='%s';p.device.imsi=['%s'];p.device.model='%s';p.device.vendor='%s';p.os.language='%s';p.os.version='%s';p.os.name='%s';p.os.vendor='%s';})(((window.__html5plus__&&__html5plus__.isReady)?__html5plus__:(navigator.plus&&navigator.plus.isReady)?navigator.plus:window.plus));";
        String str = StringUtil.format(DEVICESTATUS_JS, TelephonyUtil.updateIMEI(iApp.getActivity()), TelephonyUtil.getIMEI(iApp.getActivity(), false), sIMSI, sModel, sVendor, sLanguage, sVersion_release, TimeCalculator.PLATFORM_ANDROID, "Google");
        DEVICESTATUS_JS = str;
        return str;
    }

    @Deprecated
    public static String getMac(Context context) {
        try {
            sMAC = TelephonyUtil.getWifiData(context);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return sMAC;
    }

    public static String getNetWorkType(Context context) {
        String str = NETTYPE_NONE;
        if (sConnectMgr == null || ((Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_NETWORK_STATE") != 0) || sConnectMgr.getActiveNetworkInfo() == null)) {
            return str;
        }
        String str2 = CONNECTION_UNKNOW;
        if (sConnectMgr.getActiveNetworkInfo().getType() == 1) {
            return NETTYPE_WIFI;
        }
        if (sConnectMgr.getActiveNetworkInfo().getType() != 0) {
            return str2;
        }
        int subtype = sConnectMgr.getActiveNetworkInfo().getSubtype();
        switch (subtype) {
            case 1:
            case 2:
            case 4:
            case 7:
                return CONNECTION_CELL2G;
            case 3:
            case 8:
                return CONNECTION_CELL3G;
            case 5:
            case 6:
            case 12:
            case 14:
                return CONNECTION_CELL3G;
            case 9:
            case 10:
            case 11:
            case 13:
            case 15:
                return CONNECTION_CELL4G;
            case 16:
            case 19:
            default:
                return "" + subtype;
            case 17:
            case 18:
                return CONNECTION_CELL3G;
            case 20:
                return CONNECTION_CELL5G;
        }
    }

    public static int getNumCores() {
        int i = sCoreNums;
        if (i != -1) {
            return i;
        }
        try {
            File[] fileArrListFiles = new File("/sys/devices/system/cpu/").listFiles(new FileFilter() { // from class: io.dcloud.common.adapter.util.DeviceInfo.1CpuFilter
                @Override // java.io.FileFilter
                public boolean accept(File file) {
                    return Pattern.matches("cpu[0-9]", file.getName());
                }
            });
            sCoreNums = fileArrListFiles.length;
            return fileArrListFiles.length;
        } catch (Exception unused) {
            return 1;
        }
    }

    public static String getPlusCache() {
        return "window.plus.cache = navigator.plus.cache = (function(mkey){return {clear : function(clearCB){var callbackid = mkey.helper.callbackid( function(args){if ( clearCB ) {clearCB()};}, null);mkey.exec('Cache', 'clear', [callbackId]);},calculate : function(calculateCB){var callbackid = mkey.helper.callbackid( function(args){if ( calculateCB ) {calculateCB(args)};}, null);mkey.exec('Cache', 'calculate', [callbackid]);},setMaxSize : function (size) {mkey.exec('Cache', 'setMaxSize', [size]);}};})(window.__Mkey__);";
    }

    public static int getStatusHeight(Context context) throws IllegalAccessException, InstantiationException, ClassNotFoundException, NumberFormatException {
        try {
            if (Build.VERSION.SDK_INT < 29) {
                Class<?> cls = Class.forName("com.android.internal.R$dimen");
                return context.getResources().getDimensionPixelSize(Integer.parseInt(cls.getField("status_bar_height").get(cls.newInstance()).toString()));
            }
            Resources resources = context.getResources();
            int identifier = resources.getIdentifier("status_bar_height", "dimen", WXEnvironment.OS);
            if (identifier > 0) {
                return resources.getDimensionPixelSize(identifier);
            }
            return -1;
        } catch (Exception e) {
            Logger.e(TAG, "getStatusHeight --" + e.getMessage());
            return -1;
        }
    }

    public static String getSystemUIModeType(Activity activity) {
        switch (((UiModeManager) activity.getSystemService("uimode")).getCurrentModeType()) {
            case 0:
                return Constants.Name.UNDEFINED;
            case 1:
                return isTablet(activity) ? "pad" : "phone";
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

    public static long getTotalMemory() throws IOException {
        long j = sTotalMem;
        if (j != -1) {
            return j;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
            long jIntValue = Integer.valueOf(bufferedReader.readLine().split("\\s+")[1]).intValue();
            try {
                bufferedReader.close();
                sTotalMem = jIntValue;
                return jIntValue;
            } catch (Exception unused) {
                return jIntValue;
            }
        } catch (Exception unused2) {
            return 0L;
        }
    }

    public static String getUpdateIMSI() {
        String imsi = TelephonyUtil.getIMSI(sApplicationContext);
        sIMSI = imsi;
        if (imsi == null) {
            sIMSI = "";
        }
        return sIMSI;
    }

    public static boolean hasRootPrivilege() {
        boolean z;
        String[] strArr = rootRelatedDirs;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            }
            if (new File(strArr[i]).exists()) {
                z = true;
                break;
            }
            i++;
        }
        String str = Build.TAGS;
        return (str != null && str.contains("test-keys")) || z;
    }

    public static void hideIME(View view) {
        IBinder windowToken;
        Context context = sApplicationContext;
        if (context != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
            if (view == null || (windowToken = view.getWindowToken()) == null) {
                return;
            }
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
        }
    }

    public static void init(Context context) {
        sDeviceSdkVer = Build.VERSION.SDK_INT;
        String str = Build.MODEL;
        sModel = str;
        if ("OMAP_SS".equals(str)) {
            osType = 1;
        } else if ("OMS1_5".equals(sModel)) {
            osType = 2;
        } else if ("generic".equals(sModel)) {
            osType = 3;
        }
        mAppAuthorities = context.getPackageName() + ".dc.fileprovider";
        sBrand = Build.BRAND;
        sVendor = Build.MANUFACTURER;
        sLanguage = LanguageUtil.getDeviceDefLocalLanguage();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        sDensity = displayMetrics.density;
        dpiX = displayMetrics.xdpi;
        dpiY = displayMetrics.ydpi;
        sConnectMgr = (ConnectivityManager) context.getSystemService("connectivity");
        sStatusBarHeight = getStatusHeight(context);
    }

    public static void initAppDir(Context context) {
        if (TextUtils.isEmpty(sPrivateExternalDir)) {
            try {
                File externalCacheDir = context.getExternalCacheDir();
                if (externalCacheDir == null) {
                    sPrivateExternalDir = Environment.getExternalStorageDirectory().getPath() + "/Android/data/" + context.getPackageName();
                } else {
                    sPrivateExternalDir = externalCacheDir.getParent();
                }
                sPrivateDir = context.getFilesDir().getParent();
            } catch (Exception unused) {
            }
            sPublicDocumentsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath();
            sPublicDCIMDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath();
            sPublicDownloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            sPublicMoviesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getPath();
            sPublicMusicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath();
            sPublicPicturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
            sPublicRingtonesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES).getPath();
        }
    }

    public static void initBaseFsRootPath() {
        initPath(sApplicationContext);
    }

    public static void initGsmCdmaCell() {
        if (sNetWorkInited || AppRuntime.hasPrivacyForNotShown(sApplicationContext)) {
            return;
        }
        String string = Settings.System.getString(sApplicationContext.getContentResolver(), DEFAULT_DATA_NETWORK);
        sDeftDataNetwork = string;
        if (string == null) {
            sDeftDataNetwork = GSM_DATA_NETWORK;
        }
        sIMEI = TelephonyUtil.updateIMEI(sApplicationContext);
        sIMSI = TelephonyUtil.getIMSI(sApplicationContext);
        if ("none".equals(sDeftDataNetwork)) {
            sDeftDataNetwork = GSM_DATA_NETWORK;
        }
        if (sIMEI == null) {
            sIMEI = "";
        }
        if (sIMSI == null) {
            sIMSI = "";
        }
        sNetWorkInited = true;
    }

    public static void initPath(Context context) {
        initPath(context, true);
    }

    private static String intToIp(int i) {
        return (i & 255) + Operators.DOT_STR + ((i >> 8) & 255) + Operators.DOT_STR + ((i >> 16) & 255) + Operators.DOT_STR + ((i >> 24) & 255);
    }

    public static Boolean isAppNightMode(Context context) {
        return AppCompatDelegate.getDefaultNightMode() == 2 ? Boolean.TRUE : AppCompatDelegate.getDefaultNightMode() == 1 ? Boolean.FALSE : isSystemNightMode(context);
    }

    public static boolean isOMS() {
        int i = osType;
        return i == 1 || i == 2 || i == 3;
    }

    public static boolean isSDcardExists() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    private static boolean isStreamMode() throws ClassNotFoundException {
        try {
            Class.forName("io.dcloud.appstream.StreamAppMainActivity");
            return true;
        } catch (ClassNotFoundException e) {
            Logger.i(TAG, "e.getMessage" + e.getMessage());
            return false;
        }
    }

    public static Boolean isSystemNightMode(Context context) {
        return Boolean.valueOf((context.getApplicationContext().getResources().getConfiguration().uiMode & 48) == 32);
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    static /* synthetic */ void lambda$showSoftInput$0(View view) {
        if (view != null) {
            ((InputMethodManager) sApplicationContext.getSystemService("input_method")).showSoftInput(view, 0);
        }
    }

    static /* synthetic */ void lambda$showSoftInput$1(View view, boolean z) {
        Context context;
        if (view != null && z && !view.hasFocus()) {
            view.requestFocus();
        }
        if (isIMEShow || (context = sApplicationContext) == null) {
            return;
        }
        ((InputMethodManager) context.getSystemService("input_method")).toggleSoftInput(0, 2);
    }

    public static boolean locationEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        if (locationManager == null) {
            return false;
        }
        return locationManager.isLocationEnabled();
    }

    public static void openHardwareAccelerated(Activity activity, int i, Object obj) {
        Window window = null;
        if (i != HARDWAREACCELERATED_WINDOW) {
            if (i != HARDWAREACCELERATED_VIEW || sDeviceSdkVer < 11) {
                return;
            }
            ((View) obj).setLayerType(2, null);
            return;
        }
        Window window2 = (Window) obj;
        if (window2 != null) {
            window = window2;
        } else if (activity != null) {
            window = activity.getWindow();
        }
        if (window != null) {
            window.setFlags(16777216, 16777216);
        }
    }

    public static void showIME(View view) {
        showIME(view, false);
    }

    private static void showSoftInput(final View view, final boolean z) {
        if (view == null || !(view instanceof EditText)) {
            MessageHandler.postDelayed(new Runnable() { // from class: io.dcloud.common.adapter.util.DeviceInfo$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    DeviceInfo.lambda$showSoftInput$1(view, z);
                }
            }, 250L);
        } else {
            MessageHandler.postDelayed(new Runnable() { // from class: io.dcloud.common.adapter.util.DeviceInfo$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    DeviceInfo.lambda$showSoftInput$0(view);
                }
            }, 100L);
        }
    }

    public static boolean startsWithSdcard(String str) {
        return str.startsWith(sDeviceRootDir) || str.startsWith("/sdcard/") || str.startsWith("mnt/sdcard/") || str.startsWith(sCacheRootDir);
    }

    public static void updateIMEI() {
        sIMEI = TelephonyUtil.updateIMEI(sApplicationContext);
    }

    public static void updatePath(boolean z) {
        sBaseFsRootFullPath = FILE_PROTOCOL + sBaseFsRootPath;
        sBaseResRootFullPath = SDK.ANDROID_ASSET;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("sPackageName=" + sPackageName).append(";\n");
        stringBuffer.append("sDeviceRootDir=" + sDeviceRootDir).append(";\n");
        stringBuffer.append("sBaseFsRootPath=" + sBaseFsRootPath).append(";\n");
        stringBuffer.append("sBaseFsRootFullPath=" + sBaseFsRootFullPath).append(";\n");
        stringBuffer.append("sBaseResRootFullPath=" + sBaseResRootFullPath).append(";\n");
        Logger.d(TAG, stringBuffer.toString());
        BaseInfo.updateBaseInfo(z);
    }

    public static void updateStatusBarHeight(Activity activity) {
        if (sStatusBarHeight == 0) {
            Rect rect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int i = rect.top;
            sStatusBarHeight = i;
            if (i <= 0) {
                sStatusBarHeight = getStatusHeight(activity);
            }
        }
    }

    public static boolean wifiEnable(Context context) {
        return ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getWifiState() == 3;
    }

    public static void initPath(Context context, boolean z) {
        AndroidResources.initAndroidResources(context);
        sApplicationContext = context.getApplicationContext();
        String packageName = context.getPackageName();
        sPackageName = packageName;
        if (sDeviceRootDir != null) {
            return;
        }
        boolean zEquals = packageName.equals("io.dcloud.HBuilder");
        boolean zIsSDcardExists = isSDcardExists();
        boolean z2 = BaseInfo.ISDEBUG || DHFile.hasFile();
        if (zEquals) {
            z2 = true;
        }
        if (z2 || SDK.isUniMP) {
            z = true;
        }
        if (z) {
            initAppDir(context);
        }
        String str = sBaseFsRootPath;
        if (zIsSDcardExists) {
            if (str == null) {
                try {
                    context.getExternalCacheDir();
                } catch (Exception unused) {
                }
                if (z) {
                    sDeviceRootDir = Environment.getExternalStorageDirectory().getPath();
                    str = sDeviceRootDir + "/Android/data/" + sPackageName + sSeparatorChar + "";
                }
            }
            sBaseFsRootPath = str;
            if (z2) {
                sCacheRootDir = sDeviceRootDir;
                sBaseFsCachePath = str;
            } else {
                isPrivateDirectory = true;
                sCacheRootDir = context.getFilesDir().getParent() + sSeparatorChar;
                sBaseFsCachePath = context.getFilesDir().getAbsolutePath() + sSeparatorChar;
            }
        } else {
            BaseInfo.ISDEBUG = false;
            isPrivateDirectory = true;
            String str2 = context.getFilesDir().getParent() + sSeparatorChar;
            sDeviceRootDir = str2;
            sCacheRootDir = str2;
            String str3 = sDeviceRootDir + "";
            sBaseFsRootPath = str3;
            sBaseFsCachePath = str3;
        }
        updatePath(z);
    }

    public static void showIME(View view, boolean z) {
        if (sApplicationContext != null) {
            showSoftInput(view, z);
        }
    }
}
