package io.dcloud.common.adapter.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.utils.tools.TimeCalculator;
import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.adapter.ui.webview.WebViewFactory;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.AppStatus;
import io.dcloud.common.util.Base64;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.HarmonyUtils;
import io.dcloud.common.util.NetTool;
import io.dcloud.common.util.NetworkTypeUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.TelephonyUtil;
import io.dcloud.common.util.ThreadPool;
import io.dcloud.common.util.ZipUtils;
import io.dcloud.common.util.net.NetWork;
import io.dcloud.feature.internal.sdk.SDK;
import io.dcloud.p.d1;
import io.dcloud.p.d3;
import io.dcloud.p.s;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.Thread;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class UEH {
    private static final String CRASH_DIRECTORY = "crash/";
    private static final boolean SAVE_CRASH_LOG = true;
    private static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINESE);
    public static boolean sInited = false;

    public static void catchUncaughtException(final Context context) {
        if (sInited) {
            return;
        }
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: io.dcloud.common.adapter.util.UEH.1
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public void uncaughtException(Thread thread, Throwable th) throws InterruptedException, SecurityException, IOException {
                UEH.handleUncaughtException(context, th);
                try {
                    if (BaseInfo.getCmitInfo(BaseInfo.sLastRunApp).rptCrs) {
                        UEH.commitUncatchException(context, th);
                    }
                    Thread.sleep(3000L);
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.e("UncaughtExceptionHandler", "commitUncatchException");
                }
                th.printStackTrace();
                Logger.e("UncaughtExceptionHandler", th.toString());
                Process.killProcess(Process.myPid());
            }
        });
        sInited = true;
    }

    private static void commitBaseUncatchInfo(Context context, StringBuffer stringBuffer) throws UnsupportedEncodingException {
        String strEncode;
        try {
            strEncode = URLEncoder.encode(Build.MODEL, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            strEncode = null;
        }
        int i = Build.VERSION.SDK_INT;
        stringBuffer.append("s=99");
        stringBuffer.append("&p=a");
        if (!AppRuntime.hasPrivacyForNotShown(context)) {
            stringBuffer.append("&net=" + NetworkTypeUtil.getNetworkType(context));
            stringBuffer.append("&dcid=" + AppRuntime.getDCloudDeviceID(context));
            stringBuffer.append("&carrierid=" + TelephonyUtil.getSimOperator(context));
            String defWebViewUA = BaseInfo.sDefWebViewUserAgent;
            if (PdrUtil.isEmpty(defWebViewUA) && Thread.currentThread() == Looper.getMainLooper().getThread()) {
                defWebViewUA = WebViewFactory.getDefWebViewUA(context);
            }
            if (!PdrUtil.isEmpty(defWebViewUA)) {
                stringBuffer.append("&ua=" + defWebViewUA);
            }
        }
        String str = BaseInfo.sLastRunApp;
        if (str == null) {
            str = BaseInfo.sDefaultBootApp;
        }
        if (PdrUtil.isEmpty(str) && !SDK.isUniMPSDK()) {
            BaseInfo.parseControl();
            str = BaseInfo.sDefaultBootApp;
        }
        d3.a aVarA = d3.a(context);
        int iRound = Math.round((System.currentTimeMillis() - BaseInfo.startTime) / 1000.0f);
        stringBuffer.append("&pv=" + AndroidResources.versionName);
        stringBuffer.append("&root=" + (DeviceInfo.hasRootPrivilege() ? 1 : 0));
        stringBuffer.append("&t=" + System.currentTimeMillis());
        stringBuffer.append("&duration=" + iRound);
        StringBuilder sb = new StringBuilder("&fore=");
        sb.append(AppStatus.getAppStatus(BaseInfo.sLastRunApp) == 2 ? 0 : 1);
        stringBuffer.append(sb.toString());
        stringBuffer.append("&osn=".concat(HarmonyUtils.isHarmonyOs() ? "HarmonyOS" : TimeCalculator.PLATFORM_ANDROID));
        stringBuffer.append("&batlevel=" + AppRuntime.getBatteryLevel());
        stringBuffer.append("&battemp=" + AppRuntime.getTemperature());
        stringBuffer.append("&channel=" + BaseInfo.getAnalysisChannel());
        stringBuffer.append("&md=" + strEncode);
        stringBuffer.append("&os=" + i);
        stringBuffer.append("&osv=" + Build.VERSION.RELEASE);
        stringBuffer.append("&appid=" + str);
        stringBuffer.append("&vb=1.9.9.82603");
        stringBuffer.append("&appcount=" + BaseInfo.s_Runing_App_Count);
        stringBuffer.append("&wvcount=" + BaseInfo.s_Webview_Count);
        stringBuffer.append("&pn=" + context.getPackageName());
        stringBuffer.append("&memuse=" + (aVarA.a - aVarA.b));
        stringBuffer.append("&memtotal=" + aVarA.a);
        stringBuffer.append("&diskuse=" + (aVarA.d - aVarA.c));
        stringBuffer.append("&disktotal=" + aVarA.d);
        stringBuffer.append("&vd=" + PdrUtil.encodeURL(Build.MANUFACTURER));
        stringBuffer.append("&abis=" + getAbis());
        stringBuffer.append("&psdk=0");
        stringBuffer.append("&it=" + (SDK.isUniMPSDK() ? 1 : 0));
        StringBuilder sb2 = new StringBuilder("&fv=");
        sb2.append(TextUtils.isEmpty(BaseInfo.uniVersionV3) ? SP.getBundleData(context, "pdr", "F_V") : BaseInfo.uniVersionV3);
        stringBuffer.append(sb2.toString());
        if (!TextUtils.isEmpty(BaseInfo.sLastAppVersionName)) {
            stringBuffer.append("&v=" + BaseInfo.sLastAppVersionName);
        }
        if (TextUtils.isEmpty(AppRuntime.getUniStatistics())) {
            return;
        }
        stringBuffer.append("&us=" + AppRuntime.getUniStatistics());
    }

    private static void commitErrorLog(final String str) {
        ThreadPool.self().addThreadTask(new Runnable() { // from class: io.dcloud.common.adapter.util.UEH.3
            @Override // java.lang.Runnable
            public void run() {
                HashMap map = new HashMap();
                map.put(NetWork.CONTENT_TYPE, "application/x-www-form-urlencoded");
                NetTool.httpPost(d1.b(Base64.decode2String("YHx8eHsyJydreiZsa2RnfWwmZm18JmtmJw==")) + "collect/crash", str, map);
            }
        });
    }

    public static void commitUncatchException(Context context, String str, String str2, int i) {
        StringBuffer stringBuffer = new StringBuffer();
        commitBaseUncatchInfo(context, stringBuffer);
        stringBuffer.append("&etype=" + i);
        stringBuffer.append("&log=" + PdrUtil.encodeURL(str2));
        stringBuffer.append("&eurl=" + PdrUtil.encodeURL(str));
        commitErrorLog(stringBuffer.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static File handleUncaughtException(Context context, Throwable th) throws SecurityException, IOException {
        File file = null;
        try {
            Field[] declaredFields = Build.class.getDeclaredFields();
            StringBuffer stringBuffer = new StringBuffer();
            for (Field field : declaredFields) {
                try {
                    field.setAccessible(true);
                    stringBuffer.append(field.getName() + ":" + field.get(null) + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            StringWriter stringWriter = new StringWriter();
            th.printStackTrace(new PrintWriter(stringWriter));
            String string = stringWriter.toString();
            File file2 = "mounted".equalsIgnoreCase(Environment.getExternalStorageState()) ? new File(BaseInfo.getCrashLogsPath(context) + CRASH_DIRECTORY) : new File(context.getCacheDir().getAbsolutePath() + CRASH_DIRECTORY);
            if (!file2.exists()) {
                file2.mkdirs();
            }
            File file3 = new File(file2.getAbsolutePath(), "crash_" + System.currentTimeMillis() + "_" + formatter.format(new Date()) + ".log");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file3);
                stringBuffer.append(string);
                fileOutputStream.write(stringBuffer.toString().getBytes());
                fileOutputStream.flush();
                fileOutputStream.close();
                return file3;
            } catch (Exception e2) {
                e = e2;
                file = file3;
                e.printStackTrace();
                return file;
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            return file;
        }
    }

    public static void uploadNativeUncaughtException(final Context context) {
        final File[] fileArrListFiles;
        final File file = new File(context.getExternalCacheDir(), "dcCrashDump");
        if (file.exists() && (fileArrListFiles = file.listFiles()) != null) {
            ThreadPool.self().addThreadTask(new Runnable() { // from class: io.dcloud.common.adapter.util.UEH.2
                @Override // java.lang.Runnable
                public void run() throws InterruptedException {
                    ICallBack iCallBack = new ICallBack() { // from class: io.dcloud.common.adapter.util.UEH.2.1
                        @Override // io.dcloud.common.DHInterface.ICallBack
                        public Object onCallBack(int i, Object obj) throws InterruptedException {
                            File file2 = (File) obj;
                            UEH.commitUncatchException(context, "", Base64.encode(DHFile.readAll(file2)), 3);
                            DHFile.delete(file2);
                            return null;
                        }
                    };
                    for (File file2 : fileArrListFiles) {
                        String[] strArrSplit = file2.getName().split("\\.");
                        if (strArrSplit.length >= 2 && strArrSplit[strArrSplit.length - 1].equals("zip")) {
                            iCallBack.onCallBack(0, file2);
                        }
                    }
                    for (File file3 : fileArrListFiles) {
                        String[] strArrSplit2 = file3.getName().split("\\.");
                        if (strArrSplit2.length < 2) {
                            return;
                        }
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < strArrSplit2.length - 1; i++) {
                            sb.append(strArrSplit2[i]);
                        }
                        String str = ((Object) sb) + ".zip";
                        File[] fileArr = {file3};
                        File file4 = new File(file + File.separator + str);
                        try {
                            ZipUtils.zipFiles(fileArr, file4);
                            DHFile.delete(file3);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        iCallBack.onCallBack(0, file4);
                    }
                }
            });
        }
    }

    private static String getAbis() {
        String[] strArr = Build.SUPPORTED_ABIS;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strArr.length; i++) {
            sb.append(strArr[i]);
            if (i < strArr.length - 1) {
                sb.append(Operators.ARRAY_SEPRATOR);
            }
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void commitUncatchException(Context context, Throwable th) throws UnsupportedEncodingException {
        if (s.c(context)) {
            StringWriter stringWriter = new StringWriter();
            th.printStackTrace(new PrintWriter(stringWriter));
            String string = stringWriter.toString();
            StringBuffer stringBuffer = new StringBuffer();
            commitBaseUncatchInfo(context, stringBuffer);
            stringBuffer.append("&etype=1");
            stringBuffer.append("&log=" + PdrUtil.encodeURL(string));
            commitErrorLog(stringBuffer.toString());
        }
    }
}
