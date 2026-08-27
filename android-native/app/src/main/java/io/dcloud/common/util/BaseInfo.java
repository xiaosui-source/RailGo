package io.dcloud.common.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.URLUtil;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import com.taobao.weex.common.Constants;
import com.taobao.weex.performance.WXInstanceApm;
import io.dcloud.common.DHInterface.IActivityHandler;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IFrameView;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.adapter.io.UnicodeInputStream;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.adapter.util.UEH;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.IntentConst;
import io.dcloud.common.util.XmlUtil;
import io.dcloud.feature.internal.sdk.SDK;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public final class BaseInfo {
    public static final String REL_PRIVATE_DOC_DIR = "_doc";
    public static final String REL_PRIVATE_WWW_DIR = "_www";
    public static final String REL_PUBLIC_DOCUMENTS_DIR = "_documents";
    public static final String REL_PUBLIC_DOWNLOADS_DIR = "_downloads";
    public static boolean isDefense = false;
    public static final boolean isOnlinePackage = true;
    public static boolean isPostChcekShortCut = false;
    public static int mDeStatusBarBackground = -111111;
    public static boolean sAnimationCaptureB = true;
    public static boolean sAnimationCaptureC = true;
    public static final String sBaseVersion = "1.9.9.82603";
    public static String sChannel = "";
    public static String sCurrentAppOriginalAppid = null;
    public static String sDefWebViewUserAgent = "";
    public static String sDefaultBootApp = null;
    public static boolean sDoingAnimation = false;
    public static String sFontScale = "none";
    public static float sFontScaleFloat = 1.0f;
    public static boolean sFullScreenChanged = false;
    public static String sGlobalAuthority = null;
    public static String sGlobalUserAgent = null;
    public static String sLastAppVersionName = null;
    public static String sLastRunApp = null;
    public static int sOpenedCount = 0;
    public static long sProcessId = 0;
    public static int sTimeOutCount = 0;
    public static int sTimeOutMax = 3;
    public static int sTimeoutCapture = 350;
    public static String sWap2AppTemplateVersion;
    public static long splashCloseTime;
    public static long splashCreateTime;
    public static long startTime;
    public static HashMap<String, CmtInfo> mLaunchers = new HashMap<>();
    public static String sSplashExitCondition = AbsoluteConst.EVENTS_LOADED;
    public static boolean sGlobalFullScreen = false;
    public static String sBaseControlPath = DeviceInfo.sBaseResRootPathName + "data/dcloud_control.xml";
    public static String sBaseWap2AppTemplatePath = null;
    public static long sTemplateModifyTime = 0;
    public static ArrayList<String> mWap2appTemplateFiles = new ArrayList<>();
    public static HashMap<String, byte[]> mW2AE = new HashMap<>();
    public static String sBaseConfigTemplatePath = DeviceInfo.sBaseResRootPathName + "data/wap2app/__template.json";
    public static String sBaseWap2AppFilePath = DeviceInfo.sBaseResRootPathName + "data/wap2app/__wap2app.js";
    public static String sRuntimeJsPath = "io/dcloud/all.js";
    public static String sUniNViewServiceJsPath = DeviceInfo.sBaseResRootPathName + "data/dcloud3.dat";
    public static String sURDFilePath = null;
    public static boolean ISDEBUG = false;
    public static boolean SyncDebug = false;
    public static boolean AuxiliaryInput = false;
    public static boolean USE_ACTIVITY_HANDLE_KEYEVENT = true;
    public static boolean sSupportAddByHand = true;
    public static SDK.IntegratedMode sRuntimeMode = null;
    public static String PDR = "pdr";
    public static String WGTU_UPDATE_XML = "update.xml";
    public static String APP_WEB_CHACHE = "webcache/";
    public static String APP_JSDATA = "jsdata/";
    public static String APP_DB_DATA = "dbdata/";
    private static String APPS_NAME = "apps/";
    private static String SITMAP = "sitemap/";
    public static boolean ISAMU = false;
    public static boolean s_Is_DCloud_Packaged = false;
    public static String uniVersionV3 = "";
    public static int s_Webview_Count = 0;
    public static int s_Runing_App_Count = 0;
    public static int s_Runing_App_Count_Max = 3;
    public static int s_Runing_App_Count_Trim = 0;
    public static ArrayList<String> sRunningApp = null;
    public static String sBaseResAppsFullPath = null;
    public static String sBaseResAppsPath = null;
    public static String sCacheFsAppsPath = null;
    public static String sBaseFsAppsPath = null;
    public static String sBaseFsSitMapPath = null;
    public static String sDownloadFullPath = null;
    public static String sDocumentFullPath = "";
    public static boolean sCoverApkRuning = false;
    public static String s_properties = "/data/dcloud_properties.xml";
    public static String sConfigXML = "manifest.json";
    public static String APP_WWW_FS_DIR = "www/";
    public static String sBaseNotificationPath = null;
    public static String REAL_PRIVATE_WWW_DIR = "www/";
    public static String REAL_PRIVATE_DOC_DIR = "doc/";
    public static String REAL_PUBLIC_DOCUMENTS_DIR = "documents/";
    public static String REAL_PUBLIC_DOWNLOADS_DIR = "downloads/";
    public static boolean isDefaultAim = true;
    public static long run5appEndTime = 0;
    public static boolean isUniStatistics = false;
    public static String untrustedca = "accept";
    public static String lia = "";
    public static String renderer = "";
    public static String minUserAgentVersion = "";
    public static int timeOut = PathInterpolatorCompat.MAX_NUM_POINTS;
    public static boolean showTipsWithoutWifi = false;
    public static boolean allowDownloadWithoutWiFi = false;
    private static boolean sParsedControl = false;
    public static String STKEY = "2aSGNEUriKrg4cDH";
    public static HashMap<String, BaseAppInfo> mUnInstalledAppInfoSet = new HashMap<>();
    public static HashMap<String, BaseAppInfo> mInstalledAppInfoSet = new HashMap<>();
    public static HashMap<String, BaseAppInfo> mBaseAppInfoSet = new HashMap<>();
    private static final X500Principal DEBUG_DN = new X500Principal("CN=Android Debug,O=Android,C=US");
    public static boolean isImmersive = false;
    public static boolean injectionGeolocationJS = false;
    public static boolean isFirstRun = false;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class AppIsTestWrapper {
        static final String name = "test_app";
        Context mContext = null;

        public boolean containsKey(String str) {
            return SP.getOrCreateBundle(name, true).contains(str);
        }

        public void init(Context context) {
            this.mContext = context;
        }

        public void put(String str, String str2) {
            SharedPreferences.Editor editorEdit = SP.getOrCreateBundle(name, true).edit();
            editorEdit.putString(str, str2);
            editorEdit.commit();
        }

        public void remove(String str) {
            SP.getOrCreateBundle(name, true).edit().remove(str).commit();
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class BaseAppInfo {
        public String mAppVer;
        String mAppid;
        public boolean mMoreRecent = true;
        public boolean mDeleted = false;

        public BaseAppInfo(String str, String str2) {
            this.mAppid = str;
            this.mAppVer = str2;
        }

        public static final boolean compareVersion(String str, String str2) throws NumberFormatException {
            if (TextUtils.isEmpty(str2)) {
                return true;
            }
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            try {
                String[] strArrSplit = str.split("\\.");
                String[] strArrSplit2 = str2.split("\\.");
                int length = strArrSplit.length;
                int length2 = strArrSplit2.length;
                for (int i = 0; i < length; i++) {
                    if (i >= length2) {
                        return true;
                    }
                    int i2 = Integer.parseInt(strArrSplit[i]);
                    int i3 = Integer.parseInt(strArrSplit2[i]);
                    if (i2 > i3) {
                        return true;
                    }
                    if (i2 < i3) {
                        return false;
                    }
                }
                return false;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }
        }

        public void clearBundleData() {
            SP.removeBundleData(BaseInfo.PDR, this.mAppid + "_appver");
        }

        boolean high(BaseAppInfo baseAppInfo) {
            return compareVersion(this.mAppVer, baseAppInfo.mAppVer);
        }

        public void saveToBundleData(Context context) {
            SP.setBundleData(context, BaseInfo.PDR, this.mAppid + "_appver", this.mAppVer);
            SP.setBundleData(context, BaseInfo.PDR, this.mAppid + "_deleted", String.valueOf(this.mDeleted));
            String strInstallAppMapToString = BaseInfo.installAppMapToString();
            StringBuffer stringBuffer = new StringBuffer();
            if (!PdrUtil.isEmpty(strInstallAppMapToString)) {
                stringBuffer.append(strInstallAppMapToString).append("|");
            }
            stringBuffer.append(this.mAppid);
            SP.setBundleData(context, BaseInfo.PDR, AbsoluteConst.XML_APPS, stringBuffer.toString());
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class CmtInfo {
        public String plusLauncher;
        public String sStartupTime;
        public String sfd;
        public String templateVersion;
        public boolean rptCrs = true;
        public boolean rptJse = true;
        public boolean needUpdate = true;
    }

    public static boolean checkAppIsTest(String str) {
        return new File(sCacheFsAppsPath + str + "/.test").exists();
    }

    private static void checkOrResetTemplate(IApp iApp) {
        JSONObject jSONObject;
        File file = new File(sBaseWap2AppTemplatePath + "wap2app__template/__template.json");
        long jLastModified = file.lastModified();
        if (sTemplateModifyTime != jLastModified) {
            try {
                mWap2appTemplateFiles.clear();
                mW2AE.clear();
                try {
                    jSONObject = new JSONObject(new String(IOUtil.getBytes(new UnicodeInputStream(new FileInputStream(file), Charset.defaultCharset().name()))));
                } catch (Exception e) {
                    e.printStackTrace();
                    jSONObject = null;
                }
                int i = 0;
                if (jSONObject == null || !jSONObject.has("files")) {
                    String[] list = new File(sBaseWap2AppTemplatePath + "wap2app__template/").list();
                    if (list != null) {
                        while (i < list.length) {
                            mWap2appTemplateFiles.add(list[i]);
                            i++;
                        }
                    }
                } else {
                    JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("files");
                    int length = jSONArrayOptJSONArray.length();
                    while (i < length) {
                        mWap2appTemplateFiles.add(jSONArrayOptJSONArray.optString(i));
                        i++;
                    }
                }
                if (jSONObject.has(IApp.ConfigProperty.CONFIG_CONFUSION)) {
                    String strHandleEncryption = iApp.getConfusionMgr().handleEncryption(iApp.getActivity(), Base64.decode2bytes(jSONObject.optString(IApp.ConfigProperty.CONFIG_CONFUSION)));
                    if (PdrUtil.isEmpty(strHandleEncryption)) {
                        strHandleEncryption = "{}";
                    }
                    iApp.getConfusionMgr().removeData("__w2a__template__");
                    iApp.getConfusionMgr().recordEncryptionResources("__w2a__template__", new JSONObject(strHandleEncryption));
                }
                sWap2AppTemplateVersion = jSONObject.optString("version");
            } catch (JSONException e2) {
                e2.printStackTrace();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        sTemplateModifyTime = jLastModified;
    }

    public static boolean checkTestOpenFile() {
        return new File(DeviceInfo.sDeviceRootDir + "/.system/d85a37c6-afdc-11e6-80f5-76304dec7eb7").exists();
    }

    public static void clearData() {
        sParsedControl = false;
        sGlobalFullScreen = false;
        UEH.sInited = false;
        sCurrentAppOriginalAppid = null;
        sLastRunApp = null;
        sRunningApp = null;
        s_Webview_Count = 0;
        s_Runing_App_Count = 0;
        DeviceInfo.DEVICESTATUS_JS = null;
    }

    public static boolean containsInTemplate(IApp iApp, String str) {
        if ((iApp.getActivity() instanceof IActivityHandler) && ((IActivityHandler) iApp.getActivity()).isMultiProcessMode()) {
            checkOrResetTemplate(iApp);
        }
        return mWap2appTemplateFiles.contains(str);
    }

    public static void createAppTestFile(String str) throws IOException {
        File file = new File(sCacheFsAppsPath + str + "/.test");
        if (file.exists()) {
            return;
        }
        try {
            file.mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean existsBase() {
        try {
            return new File(Environment.getExternalStorageDirectory() + "/Android/data/io.dcloud.HBuilder").exists();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean existsLibso() {
        try {
            System.loadLibrary("so");
            return true;
        } catch (UnsatisfiedLinkError unused) {
            return false;
        }
    }

    public static boolean existsStreamEnv() {
        try {
            Class.forName("io.dcloud.appstream.StreamAppMainActivity");
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static String getAnalysisChannel() {
        String metaValue = sChannel;
        if (TextUtils.isEmpty(metaValue)) {
            metaValue = AndroidResources.getMetaValue("DCLOUD_STREAMAPP_CHANNEL");
        }
        if (TextUtils.isEmpty(metaValue)) {
            return "";
        }
        if (metaValue.startsWith("_")) {
            return metaValue.substring(1);
        }
        if (!metaValue.contains("|")) {
            return metaValue;
        }
        String[] strArrSplit = metaValue.split("\\|");
        return strArrSplit.length >= 4 ? strArrSplit[3] : "";
    }

    public static CmtInfo getCmitInfo(String str) {
        CmtInfo cmtInfo = mLaunchers.get(str);
        if (cmtInfo != null) {
            return cmtInfo;
        }
        CmtInfo cmtInfo2 = new CmtInfo();
        mLaunchers.put(str, cmtInfo2);
        return cmtInfo2;
    }

    public static String getCrashLogsPath(Context context) {
        return DeviceInfo.sBaseFsRootPath + "logs/" + context.getPackageName() + "/";
    }

    public static String getLastKey(LinkedHashMap<String, Intent> linkedHashMap) {
        String next = null;
        if (linkedHashMap != null) {
            Iterator<String> it = linkedHashMap.keySet().iterator();
            while (it.hasNext()) {
                next = it.next();
            }
        }
        return next;
    }

    public static String getLauncherData(String str) {
        CmtInfo cmtInfo = mLaunchers.get(str);
        return (cmtInfo == null || TextUtils.isEmpty(cmtInfo.plusLauncher)) ? "default" : cmtInfo.plusLauncher;
    }

    public static String getShortCutActivity(Context context) {
        return null;
    }

    public static String getStackTrace() {
        StringBuffer stringBuffer = new StringBuffer();
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            stringBuffer.append(stackTraceElement).append("\n");
        }
        return stringBuffer.toString();
    }

    public static String getStartupTimeData(String str) {
        CmtInfo cmtInfo = mLaunchers.get(str);
        if (cmtInfo != null) {
            return cmtInfo.sStartupTime;
        }
        return null;
    }

    public static String getUniNViewId(IFrameView iFrameView) {
        return iFrameView.getFrameType() == 2 ? iFrameView.obtainApp().obtainAppId() : iFrameView.obtainWebView().getWebviewUUID();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String installAppMapToString() {
        StringBuffer stringBuffer = new StringBuffer();
        Set<String> setKeySet = mInstalledAppInfoSet.keySet();
        int size = setKeySet.size();
        Iterator<String> it = setKeySet.iterator();
        while (it.hasNext()) {
            stringBuffer.append(it.next()).append("|");
        }
        if (size > 1) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }

    public static boolean isBase(Context context) {
        return SyncDebug || context.getPackageName().equals("io.dcloud.HBuilder");
    }

    public static boolean isChannelGooglePlay() {
        String metaValue = sChannel;
        if (TextUtils.isEmpty(metaValue)) {
            metaValue = AndroidResources.getMetaValue("DCLOUD_STREAMAPP_CHANNEL");
        }
        return !TextUtils.isEmpty(metaValue) && metaValue.endsWith("|google");
    }

    private static boolean isDebugSignature(Context context) {
        try {
            boolean zEquals = false;
            for (Signature signature : context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures) {
                try {
                    zEquals = ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(signature.toByteArray()))).getSubjectX500Principal().equals(DEBUG_DN);
                    if (zEquals) {
                        return zEquals;
                    }
                } catch (PackageManager.NameNotFoundException | CertificateException | Exception unused) {
                    return zEquals;
                }
            }
            return zEquals;
        } catch (PackageManager.NameNotFoundException | CertificateException | Exception unused2) {
            return false;
        }
    }

    public static synchronized boolean isLoadingLaunchePage() {
        return false;
    }

    public static boolean isStandardBase(Context context) {
        return context.getPackageName().equals("io.dcloud.HBuilder");
    }

    public static boolean isTest(String str) {
        return false;
    }

    public static boolean isUniAppAppid(IApp iApp) {
        String str;
        if (iApp != null) {
            String strObtainConfigProperty = iApp.obtainConfigProperty(AbsoluteConst.APP_IS_UNIAPP);
            if (!PdrUtil.isEmpty(strObtainConfigProperty)) {
                return Boolean.valueOf(strObtainConfigProperty).booleanValue();
            }
            String strObtainAppId = iApp.obtainAppId();
            if (!TextUtils.isEmpty(strObtainAppId)) {
                if ("HBuilder".equals(strObtainAppId) && (str = sCurrentAppOriginalAppid) != null) {
                    strObtainAppId = str;
                }
                return strObtainAppId.startsWith("__UNI__");
            }
        }
        return false;
    }

    public static boolean isUniNViewBackgroud() {
        return Boolean.valueOf(AndroidResources.getMetaValue(AbsoluteConst.DCLOUD_UNINVIEW_BACKGROUD)).booleanValue();
    }

    public static boolean isWap2AppAppid(String str) {
        String str2;
        if (str == null) {
            return false;
        }
        if ("HBuilder".equals(str) && (str2 = sCurrentAppOriginalAppid) != null) {
            str = str2;
        }
        String lowerCase = str.toLowerCase(Locale.ENGLISH);
        return lowerCase.startsWith("__w2a__") || "H52588A9C".equalsIgnoreCase(lowerCase) || "H5B5EEFBB".equalsIgnoreCase(lowerCase) || "H5A0B1958".equalsIgnoreCase(lowerCase) || "H5EA885FD".equalsIgnoreCase(lowerCase) || "H592E7F63".equalsIgnoreCase(lowerCase) || "H5BCD03E4".equalsIgnoreCase(lowerCase);
    }

    public static boolean isWeexUniJs(IApp iApp) {
        String strObtainConfigProperty = iApp.obtainConfigProperty(IApp.ConfigProperty.CONFIG_UNIAPP_CONTROL);
        if (TextUtils.isEmpty(strObtainConfigProperty)) {
            return false;
        }
        return (strObtainConfigProperty.equals(Constants.CodeCache.SAVE_PATH) || strObtainConfigProperty.equals(AbsoluteConst.UNI_V3)) && isUniAppAppid(iApp);
    }

    public static boolean isWifi(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.getType() == 1;
    }

    public static void loadInstalledAppInfo(Context context) {
        String[] strArrListFsAppsFiles;
        String bundleData = SP.getBundleData(context, PDR, AbsoluteConst.XML_APPS);
        if (bundleData != null) {
            for (String str : bundleData.split("\\|")) {
                BaseAppInfo baseAppInfo = new BaseAppInfo(str, SP.getBundleData(context, PDR, str + "_appver"));
                boolean z = Boolean.parseBoolean(SP.getBundleData(context, PDR, str + "_deleted"));
                baseAppInfo.mDeleted = z;
                if (z) {
                    mUnInstalledAppInfoSet.put(str, baseAppInfo);
                } else if (!PdrUtil.isEmpty(str)) {
                    mInstalledAppInfoSet.put(str, baseAppInfo);
                }
            }
        }
        if (!sSupportAddByHand || (strArrListFsAppsFiles = PlatformUtil.listFsAppsFiles(sBaseFsAppsPath)) == null) {
            return;
        }
        for (String str2 : strArrListFsAppsFiles) {
            if (!mInstalledAppInfoSet.containsKey(str2)) {
                String str3 = sDefaultBootApp;
                Locale locale = Locale.ENGLISH;
                if (TextUtils.equals(str3.toLowerCase(locale), str2.toLowerCase(locale))) {
                    mInstalledAppInfoSet.put(str2, new BaseAppInfo(str2, WXInstanceApm.VALUE_ERROR_CODE_DEFAULT));
                }
            }
        }
    }

    public static void parseControl() {
        XmlUtil.DHNode dHNodeXML_Parser;
        InputStream resInputStream = PlatformUtil.getResInputStream(sBaseControlPath);
        if (resInputStream == null || (dHNodeXML_Parser = XmlUtil.XML_Parser(resInputStream)) == null) {
            return;
        }
        boolean z = Boolean.parseBoolean(XmlUtil.getAttributeValue(dHNodeXML_Parser, AbsoluteConst.XML_SyncDebug, AbsoluteConst.FALSE));
        SyncDebug = z;
        ISDEBUG = z || Boolean.parseBoolean(XmlUtil.getAttributeValue(dHNodeXML_Parser, "debug", AbsoluteConst.FALSE));
        XmlUtil.DHNode element = XmlUtil.getElement(dHNodeXML_Parser, AbsoluteConst.XML_APPS);
        if (SDK.isUniMPSDK()) {
            return;
        }
        ArrayList<XmlUtil.DHNode> elements = XmlUtil.getElements(element, AbsoluteConst.XML_APP);
        if (elements.size() > 0) {
            sDefaultBootApp = XmlUtil.getAttributeValue(elements.get(0), "appid");
        }
        XmlUtil.DHNode element2 = XmlUtil.getElement(dHNodeXML_Parser, "lia");
        if (element2 != null) {
            lia = XmlUtil.getText(element2);
        }
    }

    public static void putLauncherData(String str, String str2) {
        getCmitInfo(str).plusLauncher = str2;
    }

    public static void putStartupTimeData(String str, String str2) {
        getCmitInfo(str).sStartupTime = str2;
    }

    public static void removeTestFile(String str) {
        File file = new File(sCacheFsAppsPath + str + "/.test");
        if (file.exists()) {
            file.delete();
        }
    }

    public static void saveInstalledAppInfo(Context context) {
        SP.setBundleData(context, PDR, AbsoluteConst.XML_APPS, installAppMapToString());
    }

    public static synchronized void setLoadingLaunchePage(boolean z, String str) {
    }

    public static void setUniVersionV3(String str, Context context) {
        uniVersionV3 = str;
        SP.setBundleData(context, "pdr", "F_V", str);
    }

    public static void updateBaseInfo(boolean z) {
        if (!APPS_NAME.equals("/")) {
            sBaseResAppsFullPath = DeviceInfo.sBaseResRootFullPath + APPS_NAME;
            sBaseResAppsPath = DeviceInfo.sBaseResRootPathName + APPS_NAME;
            sCacheFsAppsPath = DeviceInfo.sBaseFsCachePath + APPS_NAME;
            sBaseFsAppsPath = DeviceInfo.sBaseFsRootPath + APPS_NAME;
            sBaseFsSitMapPath = DeviceInfo.sBaseFsRootPath + SITMAP;
            sBaseWap2AppTemplatePath = DeviceInfo.sBaseFsCachePath + "cnc3ejE5/";
            sURDFilePath = DeviceInfo.sBaseFsCachePath + "cnc3ejE6/eje3cnc";
            if (z) {
                DHFile.createNewFile(sBaseFsAppsPath);
                DHFile.createNewFile(sBaseFsSitMapPath);
            }
        }
        if (z) {
            String str = sDownloadFullPath;
            if (str == null || str.indexOf("sdcard/") <= -1) {
                sDownloadFullPath = DeviceInfo.sBaseFsRootPath + REAL_PUBLIC_DOWNLOADS_DIR;
            } else {
                sDownloadFullPath = PdrUtil.appendByDeviceRootDir(sDownloadFullPath);
            }
            String str2 = sDocumentFullPath;
            if (str2 != null && str2.indexOf("sdcard/") > -1) {
                sDocumentFullPath = PdrUtil.appendByDeviceRootDir(sDocumentFullPath);
                return;
            }
            sDocumentFullPath = DeviceInfo.sBaseFsRootPath + REAL_PUBLIC_DOCUMENTS_DIR;
        }
    }

    public static void updateBaseInfoByApp(String str, String str2) {
        if (str != null) {
            PDR = str;
        }
        if (str2 != null) {
            DeviceInfo.sBaseFsRootPath = str2;
        }
        DeviceInfo.initBaseFsRootPath();
    }

    public static boolean useStreamAppStatistic(Context context) {
        return true;
    }

    public static String getLaunchType(Intent intent) {
        if (intent == null) {
            return "default";
        }
        Uri data = intent.getData();
        String stringExtra = intent.hasExtra(IntentConst.RUNING_STREAPP_LAUNCHER) ? intent.getStringExtra(IntentConst.RUNING_STREAPP_LAUNCHER) : "default";
        if (data != null && !URLUtil.isNetworkUrl(data.toString())) {
            return intent.getBooleanExtra(IntentConst.FROM_BARCODE, false) ? "barcode" : "scheme";
        }
        if (intent.getExtras() != null) {
            if (!TextUtils.isEmpty(intent.getStringExtra(IntentConst.STREAM_LAUNCHER))) {
                return intent.getStringExtra(IntentConst.STREAM_LAUNCHER);
            }
            if (intent.getBooleanExtra(IntentConst.FROM_SHORT_CUT_STRAT, false)) {
                return IApp.ConfigProperty.CONFIG_SHORTCUT;
            }
            if (intent.getBooleanExtra(IntentConst.FROM_BARCODE, false)) {
                return "barcode";
            }
            if (intent.getIntExtra(IntentConst.START_FROM, -1) == 3 || intent.hasExtra("UP-OL-SU")) {
                return "push";
            }
            if (intent.getIntExtra(IntentConst.START_FROM, -1) == 5) {
                return "myapp";
            }
            if (intent.getIntExtra(IntentConst.START_FROM, -1) == 7) {
                return "browser";
            }
            if (intent.getIntExtra(IntentConst.START_FROM, -1) == 8) {
                return "favorite";
            }
            if (intent.getIntExtra(IntentConst.START_FROM, -1) == 9) {
                return "engines";
            }
            if (intent.getIntExtra(IntentConst.START_FROM, -1) == 40) {
                return "apush";
            }
            if (intent.getIntExtra(IntentConst.START_FROM, -1) == 10) {
                return "speech";
            }
        }
        return stringExtra;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x01ec  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0231 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0045  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String[] parseControl(android.content.Context r18, io.dcloud.common.DHInterface.ICore r19, io.dcloud.common.DHInterface.ICore.ICoreStatusListener r20) throws java.io.IOException, java.text.ParseException {
        /*
            Method dump skipped, instructions count: 573
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.util.BaseInfo.parseControl(android.content.Context, io.dcloud.common.DHInterface.ICore, io.dcloud.common.DHInterface.ICore$ICoreStatusListener):java.lang.String[]");
    }
}
