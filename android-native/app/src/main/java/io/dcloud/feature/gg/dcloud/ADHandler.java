package io.dcloud.feature.gg.dcloud;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MotionEvent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.dcloud.android.widget.dialog.DCloudAlertDialog;
import com.facebook.common.callercontext.ContextChain;
import com.taobao.weex.common.Constants;
import com.taobao.weex.common.WXConfig;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.ui.component.WXBasicComponentType;
import com.taobao.weex.ui.component.WXComponent;
import io.dcloud.application.DCLoudApplicationImpl;
import io.dcloud.common.DHInterface.DAI;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.ADUtils;
import io.dcloud.common.util.AESUtil;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.CreateShortResultReceiver;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.NetTool;
import io.dcloud.common.util.NetworkTypeUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.TelephonyUtil;
import io.dcloud.common.util.TestUtil;
import io.dcloud.common.util.ThreadPool;
import io.dcloud.common.util.ZipUtils;
import io.dcloud.common.util.hostpicker.HostPicker;
import io.dcloud.feature.gg.AolSplashUtil;
import io.dcloud.feature.gg.dcloud.ADResult;
import io.dcloud.feature.uniapp.adapter.AbsURIAdapter;
import io.dcloud.p.b4;
import io.dcloud.p.d0;
import io.dcloud.p.d1;
import io.dcloud.p.l0;
import io.dcloud.p.q1;
import io.dcloud.p.r0;
import io.dcloud.p.s3;
import io.dcloud.p.t3;
import io.dcloud.sdk.core.DCloudAOLManager;
import io.dcloud.sdk.core.util.Const;
import io.dcloud.sdk.poly.base.utils.PrivacyManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class ADHandler {
    public static final String AdTag = "_adio.dcloud.feature.ad.dcloud.ADHandler";
    private static final String File_Data = "data.json";
    private static final String File_Gif = "img.gif";
    private static final String File_Img = "img.png";
    private static final String File_S = "s.txt";
    private static final String File_Tid = "tid.txt";
    private static LinkedList<File> expiresFileList = null;
    private static t3 handler = null;
    static boolean isPullFor360 = false;
    static boolean sNeedShowSkipView = false;
    static long sPullBeginTime;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class ADReceiver implements IAolReceiver {
        Context mContext;
        long mStartTime = System.currentTimeMillis();

        public ADReceiver(Context context) {
            this.mContext = null;
            this.mContext = context;
        }

        private void broadcastADReceive() {
            Intent intent = new Intent();
            intent.setAction("ad_receive");
            intent.putExtra("begin", this.mStartTime);
            intent.putExtra("end", System.currentTimeMillis());
            this.mContext.sendBroadcast(intent);
            ADHandler.log("ADReceive", "broadcastADReceive");
        }

        private void pap(JSONObject jSONObject) {
            if (jSONObject == null || !jSONObject.has("pap")) {
                return;
            }
            ADHandler.handleSplashAdEnable(this.mContext, Boolean.valueOf(jSONObject.optInt("pap") == 1));
        }

        @Override // io.dcloud.feature.gg.dcloud.IAolReceiver
        public void onError(String str, String str2) {
            broadcastADReceive();
        }

        @Override // io.dcloud.feature.gg.dcloud.IAolReceiver
        public void onReceiver(JSONObject jSONObject) {
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("psas");
            boolean z = jSONObject.optInt("pap", 0) == 1;
            SP.setBundleData(this.mContext, ADHandler.AdTag, "dpap", jSONObject.optString("dpap", WXInstanceApm.VALUE_ERROR_CODE_DEFAULT));
            if (jSONArrayOptJSONArray == null || !z) {
                ADHandler.log("ADReceiver", "onReceiver no data = " + jSONObject);
            } else {
                long jCurrentTimeMillis = System.currentTimeMillis();
                int length = jSONArrayOptJSONArray.length();
                ADHandler.log("ADReceiver", "onReceiver psas.length = " + length + "; data=" + jSONObject);
                for (int i = 0; i < length; i++) {
                    ADHandler.handleAdData(this.mContext, jSONArrayOptJSONArray.optJSONObject(i), jCurrentTimeMillis);
                }
            }
            if (ADHandler.isPullFor360) {
                return;
            }
            broadcastADReceive();
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class AdData {
        public Object mImgData;
        String mImgPath;
        String mImgSrc;
        JSONObject mJsonData;
        MotionEvent mMotionEvent_down;
        MotionEvent mMotionEvent_up;
        String mOriginalAppid;
        String mProvider;
        int mEShow = 0;
        int mEClick = 0;

        boolean check() {
            return (this.mJsonData == null || this.mImgData == null) ? false : true;
        }

        JSONObject data() {
            return this.mJsonData.optJSONObject("data");
        }

        JSONObject full() {
            return this.mJsonData;
        }

        boolean isEClick() {
            return this.mEClick == 1;
        }

        boolean isEShow() {
            return this.mEShow == 1;
        }

        void listenADReceive(Context context, final IAolReceiver iAolReceiver) {
            if (iAolReceiver != null) {
                BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: io.dcloud.feature.gg.dcloud.ADHandler.AdData.1
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context2, Intent intent) {
                        try {
                            long longExtra = intent.getLongExtra("end", 0L) - intent.getLongExtra("begin", 0L);
                            ADHandler.log("ADReceive", "useTime=" + longExtra);
                            if (longExtra <= 3000) {
                                iAolReceiver.onReceiver(null);
                            }
                            ADHandler.log("ADReceive", "unregisterReceiver");
                            context2.unregisterReceiver(this);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                try {
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("ad_receive");
                    LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver, intentFilter);
                    ADHandler.log("ADReceive", "registerReceiver");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        JSONObject report() {
            return this.mJsonData.optJSONObject("report");
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    interface AdDataWatcher<E> {
        boolean find();

        void operate(E e);
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    interface ThreadTask {
        void execute();
    }

    public static Boolean SplashAdIsEnable(Context context) {
        if (b4.b()) {
            return Boolean.FALSE;
        }
        if (handler == null) {
            handler = (t3) r0.d().a();
        }
        return Boolean.valueOf(handler.a(context));
    }

    private static void addThreadTask(final ThreadTask threadTask) {
        if (threadTask != null) {
            if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                ThreadPool.self().addThreadTask(new Runnable() { // from class: io.dcloud.feature.gg.dcloud.ADHandler.3
                    @Override // java.lang.Runnable
                    public void run() {
                        threadTask.execute();
                    }
                });
            } else {
                threadTask.execute();
            }
        }
    }

    public static boolean allReady(Context context) {
        return !TextUtils.isEmpty(get(context, "appid"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void analysisPullData(byte[] bArr, String str, IAolReceiver... iAolReceiverArr) throws JSONException {
        t3 t3Var;
        if (bArr == null) {
            return;
        }
        JSONObject jSONObject = new JSONObject(new String(bArr));
        int iOptInt = jSONObject.optInt("ret", -1);
        int i = 0;
        if (iOptInt != 0) {
            int length = iAolReceiverArr.length;
            while (i < length) {
                iAolReceiverArr[i].onError(String.valueOf(iOptInt), jSONObject.optString("desc"));
                i++;
            }
            t3 t3Var2 = handler;
            if (t3Var2 != null) {
                t3Var2.a(-5007, jSONObject.optString("desc"));
                return;
            }
            return;
        }
        if (jSONObject.has("data")) {
            String strDecrypt = AESUtil.decrypt(d1.c(), d1.b(), Base64.decode(jSONObject.optString("data"), 2));
            if (strDecrypt != null) {
                try {
                    try {
                        JSONObject jSONObject2 = new JSONObject(strDecrypt);
                        AolSplashUtil.decodeChannel(jSONObject2);
                        jSONObject.put("data", jSONObject2);
                    } catch (Exception unused) {
                    }
                } catch (Exception unused2) {
                    jSONObject.put("data", new JSONArray(strDecrypt));
                }
            }
        }
        int length2 = iAolReceiverArr.length;
        while (i < length2) {
            iAolReceiverArr[i].onReceiver(jSONObject);
            i++;
        }
        if ((str.equals("ThirdConfig") || str.equals("Splash")) && (t3Var = handler) != null) {
            t3Var.a(jSONObject);
        }
    }

    static String appid(Context context) {
        return get(context, "appid");
    }

    static int bg(Context context) throws NumberFormatException {
        int iStringToColor = PdrUtil.stringToColor(get(context, "bg"));
        if (iStringToColor != -1) {
            return iStringToColor;
        }
        return -1;
    }

    static void click(final Context context, final AdData adData, final String str) {
        final String strOptString = adData.data().optString("tid");
        ThreadPool.self().addThreadTask(new Runnable() { // from class: io.dcloud.feature.gg.dcloud.ADHandler.9
            @Override // java.lang.Runnable
            public void run() throws JSONException {
                JSONObject clickData;
                int i;
                if (adData.isEClick()) {
                    clickData = null;
                    i = 46;
                } else {
                    clickData = ADHandler.getClickData(adData);
                    i = 41;
                }
                JSONObject jSONObject = clickData;
                JSONObject jSONObjectFull = adData.full();
                d0.a(context, adData.mOriginalAppid, strOptString, str, i, null, null, jSONObject, null, null, AolSplashUtil.getSplashAdpId("_adpid_", "UNIAD_SPLASH_ADPID"), (jSONObjectFull == null || !jSONObjectFull.has("ua")) ? "" : jSONObjectFull.optString("ua"), null);
            }
        });
        if ("wanka".equals(adData.mProvider)) {
            ADHandler_wanka.click_wanka(context, adData, str);
            return;
        }
        if ("youdao".equals(adData.mProvider)) {
            ADHandler_youdao.click_youdao(context, adData, str);
        } else if ("common".equals(adData.mProvider)) {
            Aolhandler_common.click_common(context, adData, str);
        } else {
            click_base(context, adData, str);
        }
    }

    static void click_base(final Context context, final AdData adData, final String str) {
        JSONObject jSONObjectData = adData.data();
        final String strOptString = adData.data().optString("tid");
        if (jSONObjectData.has("dplk") && ADUtils.openDeepLink(context, jSONObjectData.optString("dplk"))) {
            if (adData.isEClick()) {
                return;
            }
            ThreadPool.self().addThreadTask(new Runnable() { // from class: io.dcloud.feature.gg.dcloud.ADHandler.8
                @Override // java.lang.Runnable
                public void run() {
                    JSONObject jSONObjectFull = adData.full();
                    TestUtil.PointTime.commitTid(context, adData.mOriginalAppid, strOptString, str, 50, AolSplashUtil.getSplashAdpId("_adpid_", "UNIAD_SPLASH_ADPID"), false, (jSONObjectFull == null || !jSONObjectFull.has("ua")) ? "" : jSONObjectFull.optString("ua"));
                }
            });
            if ("wanka".equals(adData.mProvider)) {
                ADHandler_wanka.dplk_wanka(context, adData, str);
                return;
            } else if ("youdao".equals(adData.mProvider)) {
                ADHandler_youdao.dplk_youdao(context, adData, str);
                return;
            } else {
                if ("common".equals(adData.mProvider)) {
                    Aolhandler_common.handletask_common(context, adData, str, "dptracker");
                    return;
                }
                return;
            }
        }
        String strOptString2 = jSONObjectData.optString("action");
        if (TextUtils.equals("url", strOptString2)) {
            if (adData.isEClick()) {
                ADSim.openUrl(context, jSONObjectData.optString("url"));
                return;
            } else {
                ADUtils.openUrl(context, jSONObjectData.optString("url"));
                return;
            }
        }
        if (TextUtils.equals(AbsoluteConst.SPNAME_DOWNLOAD, strOptString2)) {
            if (jSONObjectData.has("expires")) {
                try {
                    new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(jSONObjectData.optString("expires")).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            String strOptString3 = adData.full() != null ? adData.full().optString("ua") : "";
            if (adData.isEClick()) {
                ADSim.dwApp(context, adData.mOriginalAppid, strOptString, str, jSONObjectData.optString("url"), jSONObjectData.optString(AbsURIAdapter.BUNDLE), null, strOptString3);
                return;
            }
            return;
        }
        if (TextUtils.equals(AbsoluteConst.XML_STREAMAPP, strOptString2) && jSONObjectData.has("appid")) {
            if (adData.isEClick()) {
                return;
            }
            ADUtils.openStreamApp(context, jSONObjectData.optString("appid"), jSONObjectData.optJSONObject("parameters"), -1, jSONObjectData.optString("streamapps"));
        } else {
            if (!TextUtils.equals("browser", strOptString2) || adData.isEClick()) {
                return;
            }
            ADUtils.openBrowser(context, jSONObjectData.optString("url"));
        }
    }

    private static Boolean defAdConfig(Context context) {
        try {
            return Boolean.valueOf(context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getBoolean("DCLOUD_AD_SPLASH", false));
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    private static void exec5Plus(final List<HostPicker.Host> list, String str, final IAolReceiver[] iAolReceiverArr) throws IOException {
        String strEncode;
        try {
            strEncode = URLEncoder.encode(Base64.encodeToString(AESUtil.encrypt(d1.c(), d1.b(), ZipUtils.zipString(str)), 2), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            strEncode = null;
        }
        final String str2 = "edata=" + strEncode;
        final boolean z = !hasOtherAd();
        addThreadTask(new ThreadTask() { // from class: io.dcloud.feature.gg.dcloud.ADHandler.2
            @Override // io.dcloud.feature.gg.dcloud.ADHandler.ThreadTask
            public void execute() {
                List list2 = list;
                if (list2 != null) {
                    ADHandler.pull((List<HostPicker.Host>) list2, "ThirdConfig", str2, z, iAolReceiverArr);
                    return;
                }
                ArrayList arrayList = new ArrayList();
                for (String str3 : q1.b.keySet()) {
                    HostPicker.Host.PriorityEnum priorityEnum = HostPicker.Host.PriorityEnum.BACKUP;
                    int iIntValue = ((Integer) q1.b.get(str3)).intValue();
                    if (iIntValue != -1) {
                        if (iIntValue == 0) {
                            priorityEnum = HostPicker.Host.PriorityEnum.NORMAL;
                        } else if (iIntValue == 1) {
                            priorityEnum = HostPicker.Host.PriorityEnum.FIRST;
                        }
                    }
                    arrayList.add(new HostPicker.Host(str3, priorityEnum));
                }
                ADHandler.pull(arrayList, "Splash", str2, z, iAolReceiverArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void fileAdData(Context context, File file, AdData adData) throws IOException {
        try {
            JSONObject jSONObject = new JSONObject(new String(DHFile.readAll(file.getAbsolutePath() + "/data.json")));
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
            if (jSONObjectOptJSONObject == null) {
                return;
            }
            adData.mProvider = jSONObject.optString("provider");
            adData.mJsonData = jSONObject;
            adData.mEShow = jSONObject.optInt("es", 0);
            adData.mEClick = jSONObject.optInt("ec", 0);
            String strOptString = jSONObjectOptJSONObject.optString("src");
            adData.mImgSrc = strOptString;
            boolean zEndsWith = strOptString.toLowerCase(Locale.ENGLISH).endsWith(".gif");
            StringBuilder sb = new StringBuilder();
            sb.append(file.getAbsolutePath());
            sb.append("/");
            sb.append(zEndsWith ? File_Gif : File_Img);
            String string = sb.toString();
            String str = file.getAbsolutePath() + "/s.txt";
            if (!new File(string).exists() || new File(str).exists()) {
                return;
            }
            if (zEndsWith) {
                adData.mImgData = PlatformUtil.newInstance("pl.droidsonroids.gif.GifDrawable", new Class[]{String.class}, new Object[]{jSONObject.optString("srcPath")});
            } else {
                Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(string);
                if (bitmapDecodeFile != null) {
                    adData.mImgData = bitmapDecodeFile;
                }
            }
            adData.mImgPath = string;
            new File(str).createNewFile();
            DHFile.delete(file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String formatUrl(String str, JSONObject jSONObject) {
        try {
            return str.replace("${User-Agent}", URLEncoder.encode(jSONObject.optString("u-a"), "utf-8")).replace("${click_id}", jSONObject.optString("click_id")).replace("${down_x}", String.valueOf(jSONObject.optInt("down_x", DCloudAlertDialog.DARK_THEME))).replace("${down_y}", String.valueOf(jSONObject.optInt("down_y", DCloudAlertDialog.DARK_THEME))).replace("${up_x}", String.valueOf(jSONObject.optInt("up_x", DCloudAlertDialog.DARK_THEME))).replace("${up_y}", String.valueOf(jSONObject.optInt("up_y", DCloudAlertDialog.DARK_THEME))).replace("${relative_down_x}", String.valueOf(jSONObject.optInt("relative_down_x", DCloudAlertDialog.DARK_THEME))).replace("${relative_down_y}", String.valueOf(jSONObject.optInt("relative_down_y", DCloudAlertDialog.DARK_THEME))).replace("${relative_up_x}", String.valueOf(jSONObject.optInt("relative_up_x", DCloudAlertDialog.DARK_THEME))).replace("${relative_up_y}", String.valueOf(jSONObject.optInt("relative_up_y", DCloudAlertDialog.DARK_THEME)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }

    private static void get(StringBuilder sb, String str, String str2) throws ClassNotFoundException {
        try {
            Class.forName(d1.b(str));
            sb.append(",");
            sb.append(str2);
        } catch (Exception unused) {
        }
    }

    static JSONObject getArgsJsonData(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("ua", jSONObject.optString("ua"));
            jSONObject2.put("down_x", jSONObject.optInt("down_x"));
            jSONObject2.put("down_y", jSONObject.optInt("down_y"));
            jSONObject2.put("up_x", jSONObject.optInt("up_x"));
            jSONObject2.put("up_y", jSONObject.optInt("up_y"));
            jSONObject2.put("relative_down_x", jSONObject.optInt("relative_down_x"));
            jSONObject2.put("relative_down_y", jSONObject.optInt("relative_down_y"));
            jSONObject2.put("relative_up_x", jSONObject.optInt("relative_up_x"));
            jSONObject2.put("relative_up_y", jSONObject.optInt("relative_up_y"));
            return jSONObject2;
        } catch (Exception e) {
            e.printStackTrace();
            return jSONObject2;
        }
    }

    private static String getBId() throws IOException {
        try {
            File file = new File("/proc/sys/kernel/random/boot_id");
            if (!file.exists()) {
                return "";
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bArr = new byte[37];
            fileInputStream.read(bArr);
            String str = new String(bArr);
            try {
                fileInputStream.close();
                return str;
            } catch (Exception unused) {
                return str;
            }
        } catch (Exception unused2) {
            return "";
        }
    }

    public static AdData getBestAdData(Context context, String str) {
        return getBestAdData(context, str, new AdData());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static JSONObject getClickData(AdData adData) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(WXBasicComponentType.IMG, adData.mImgSrc);
            JSONObject jSONObjectFull = adData.full();
            jSONObject.put("dw", jSONObjectFull.optInt("dw"));
            jSONObject.put("dh", jSONObjectFull.optInt("dh"));
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("dx", jSONObjectFull.optInt("down_x"));
            jSONObject2.put(Constants.Name.DISTANCE_Y, jSONObjectFull.optInt("down_y"));
            jSONObject2.put("ux", jSONObjectFull.optInt("up_x"));
            jSONObject2.put("uy", jSONObjectFull.optInt("up_y"));
            jSONObject2.put("rdx", jSONObjectFull.optInt("relative_down_x"));
            jSONObject2.put("rdy", jSONObjectFull.optInt("relative_down_y"));
            jSONObject2.put("rux", jSONObjectFull.optInt("relative_up_x"));
            jSONObject2.put("ruy", jSONObjectFull.optInt("relative_up_y"));
            jSONObject.put("click_coord", jSONObject2);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jSONObject;
        }
    }

    private static String getRootPath(Context context) {
        File externalCacheDir = context.getExternalCacheDir();
        if (externalCacheDir == null) {
            return "/sdcard/Android/data/" + context.getPackageName() + "/cache/ad/";
        }
        return externalCacheDir.getAbsolutePath() + "/ad/";
    }

    private static String getUT() throws InterruptedException, IOException, NumberFormatException {
        try {
            Process processExec = Runtime.getRuntime().exec("stat -c \"%x\" /data/data");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processExec.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            char[] cArr = new char[256];
            while (true) {
                int i = bufferedReader.read(cArr);
                if (i <= 0) {
                    break;
                }
                stringBuffer.append(cArr, 0, i);
            }
            bufferedReader.close();
            processExec.waitFor();
            String[] strArrSplit = stringBuffer.toString().replace(JSUtil.QUOTE, "").split("\\.");
            long time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strArrSplit[0]).getTime();
            String strSubstring = strArrSplit[1];
            if (strSubstring.contains(Operators.PLUS)) {
                strSubstring = strSubstring.substring(0, strSubstring.indexOf(Operators.PLUS));
            }
            return (time / 1000) + Operators.DOT_STR + Long.parseLong(strSubstring.trim());
        } catch (Exception unused) {
            return Operators.DOT_STR;
        }
    }

    private static void handleAdBaseData(final Context context, final JSONObject jSONObject, String str, final String str2, String str3) throws Exception {
        if (jSONObject != null && jSONObject.has("es") && jSONObject.getInt("es") == 1) {
            new ADSim(context, jSONObject).start();
            return;
        }
        System.currentTimeMillis();
        DHFile.writeFile(str.getBytes(), 0, str3 + File_Tid);
        StringBuilder sb = new StringBuilder();
        sb.append(str3);
        sb.append(str2.endsWith(".gif") ? File_Gif : File_Img);
        final String string = sb.toString();
        jSONObject.put("srcPath", string);
        DHFile.writeFile(jSONObject.toString().getBytes(), 0, str3 + File_Data);
        addThreadTask(new ThreadTask() { // from class: io.dcloud.feature.gg.dcloud.ADHandler.12
            @Override // io.dcloud.feature.gg.dcloud.ADHandler.ThreadTask
            public void execute() throws IOException {
                HashMap map;
                byte[] bArrHttpGet = null;
                if (jSONObject.has("ua") && jSONObject.optString("ua").equalsIgnoreCase("webview")) {
                    map = new HashMap();
                    map.put(IWebview.USER_AGENT, ADHandler.get("ua-webview"));
                } else {
                    map = null;
                }
                try {
                    bArrHttpGet = NetTool.httpGet(str2, (HashMap<String, String>) map, true);
                } catch (Exception unused) {
                }
                StringBuilder sb2 = new StringBuilder("download file is nulll");
                sb2.append(bArrHttpGet == null);
                sb2.append("src=");
                sb2.append(str2);
                ADHandler.log("adh", sb2.toString());
                if (bArrHttpGet != null) {
                    DHFile.writeFile(bArrHttpGet, 0, string);
                }
                if (ADHandler.isPullFor360) {
                    return;
                }
                Intent intent = new Intent();
                intent.setAction("ad_img_downlaod_receive");
                intent.putExtra("downloadImage", bArrHttpGet != null);
                intent.putExtra("src", str2);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                ADHandler.log("adh", "handleAdBaseData--downloadC");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void handleAdData(Context context, JSONObject jSONObject, long j) {
        try {
            String strOptString = jSONObject.optString("provider");
            if ("dcloud".equals(strOptString)) {
                handleAdData_dcloud(context, jSONObject, j);
                return;
            }
            if ("wanka".equals(strOptString)) {
                ADHandler_wanka.handleAdData_wanka(context, jSONObject, j);
            } else if ("youdao".equals(strOptString)) {
                ADHandler_youdao.handleAdData_youdao(context, jSONObject, j);
            } else if ("common".equals(strOptString)) {
                Aolhandler_common.handleAdData_common(context, jSONObject, j);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void handleAdData_dcloud(Context context, JSONObject jSONObject, long j) throws Exception {
        String rootPath = getRootPath(context);
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
        Date date = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(jSONObjectOptJSONObject.optString("expires"));
        if (date.getTime() > System.currentTimeMillis()) {
            String strOptString = jSONObjectOptJSONObject.optString("src");
            handleAdBaseData(context, jSONObject, jSONObjectOptJSONObject.optString("tid"), strOptString, rootPath + j + "/" + date.getTime() + "/" + URLEncoder.encode(strOptString, "utf-8").hashCode() + "/");
        }
    }

    public static void handleSplashAdEnable(Context context, Boolean bool) {
        t3 t3Var = handler;
        if (t3Var != null) {
            t3Var.a(context, bool.booleanValue() ? "1" : WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
        }
    }

    private static boolean hasOtherAd() {
        String bundleData = SP.getBundleData(AdTag, "pspType");
        if (TextUtils.isEmpty(bundleData)) {
            return false;
        }
        return bundleData.contains("360") || bundleData.contains(Const.TYPE_GDT) || bundleData.contains(Const.TYPE_CSJ);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static android.graphics.drawable.Drawable img(android.content.Context r2) {
        /*
            java.lang.String r0 = "img"
            java.lang.String r2 = get(r2, r0)
            boolean r0 = android.text.TextUtils.isEmpty(r2)
            r1 = 0
            if (r0 != 0) goto L2d
            boolean r0 = io.dcloud.common.util.PdrUtil.isDeviceRootDir(r2)
            if (r0 == 0) goto L23
            java.io.File r0 = new java.io.File
            r0.<init>(r2)
            boolean r0 = r0.exists()
            if (r0 == 0) goto L2d
            android.graphics.Bitmap r2 = android.graphics.BitmapFactory.decodeFile(r2)
            goto L2e
        L23:
            r0 = 0
            java.io.InputStream r2 = io.dcloud.common.adapter.util.PlatformUtil.getInputStream(r2, r0)
            android.graphics.Bitmap r2 = android.graphics.BitmapFactory.decodeStream(r2)
            goto L2e
        L2d:
            r2 = r1
        L2e:
            if (r2 == 0) goto L36
            android.graphics.drawable.BitmapDrawable r0 = new android.graphics.drawable.BitmapDrawable
            r0.<init>(r2)
            return r0
        L36:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.gg.dcloud.ADHandler.img(android.content.Context):android.graphics.drawable.Drawable");
    }

    private static void listExpiresAdData(Context context, AdDataWatcher<File> adDataWatcher) throws InterruptedException {
        File file = new File(getRootPath(context));
        if (!file.exists()) {
            file.mkdirs();
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            return;
        }
        sortDesc(fileArrListFiles);
        for (File file2 : fileArrListFiles) {
            if (adDataWatcher.find()) {
                DHFile.delete(file2);
            } else {
                for (File file3 : file2.listFiles()) {
                    if (Long.parseLong(file3.getName()) <= System.currentTimeMillis()) {
                        DHFile.delete(file3);
                    } else if (!adDataWatcher.find()) {
                        for (File file4 : file3.listFiles()) {
                            adDataWatcher.operate(file4);
                            if (adDataWatcher.find()) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    static void log(String str, String str2) {
    }

    static String mc(Context context) {
        if (TextUtils.isEmpty(BaseInfo.sChannel) || TextUtils.equals("default", BaseInfo.sChannel)) {
            try {
                return context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString("DCLOUD_STREAMAPP_CHANNEL", BaseInfo.sChannel);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return BaseInfo.sChannel;
    }

    static String name(Context context) {
        return get(context, "name");
    }

    public static String papEnable(Context context) {
        String str = getRootPath(context).replaceAll("/ad/", "/") + "AdEnable.dat";
        try {
            if (DHFile.isExist(str)) {
                return new String(DHFile.readAll(str));
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static void postSplashError(Context context, String str, String str2, JSONArray jSONArray) {
        String str3;
        HashMap map = new HashMap();
        map.put(ContextChain.TAG_PRODUCT, "a");
        map.put(CreateShortResultReceiver.KEY_VERSIONNAME, get(CreateShortResultReceiver.KEY_VERSIONNAME));
        map.put("appid", get("appid"));
        map.put("name", get(context, "name"));
        map.put("pn", context.getPackageName());
        try {
            str3 = context.getPackageManager().getPackageInfo(context.getPackageName(), 1).versionName;
        } catch (Exception unused) {
            str3 = null;
        }
        map.put("pv", str3);
        map.put(ContextChain.TAG_INFRA, Base64.encodeToString(AESUtil.encrypt(d1.c(), d1.b(), ZipUtils.zipString(TelephonyUtil.getIMEI(context, true, true))), 2));
        map.put("md", Build.MODEL);
        map.put("vd", Build.MANUFACTURER);
        map.put(WXConfig.os, Integer.valueOf(Build.VERSION.SDK_INT));
        map.put("vb", PdrUtil.isEmpty("1.9.9.82603") ? "" : "1.9.9.82603");
        map.put("net", Integer.valueOf(NetworkTypeUtil.getNetworkType(context)));
        map.put("mc", mc(context));
        map.put("paid", get(context, "adid"));
        map.put("dw", Integer.valueOf(AolSplashUtil.dw(context)));
        map.put("dh", Integer.valueOf(AolSplashUtil.dh(context)));
        map.put("c", str);
        map.put(WXComponent.PROP_FS_MATCH_PARENT, str2);
        if (jSONArray != null) {
            map.put("d", jSONArray);
        }
        final String string = new JSONObject(map).toString();
        addThreadTask(new ThreadTask() { // from class: io.dcloud.feature.gg.dcloud.ADHandler.6
            @Override // io.dcloud.feature.gg.dcloud.ADHandler.ThreadTask
            public void execute() {
                NetTool.httpPost(PdrUtil.checkIntl() ? "https://er.dcloud.io/sc" : "https://er.dcloud.net.cn/sc", string, new HashMap());
            }
        });
    }

    public static void pr(Context context, Map<String, Object> map) throws UnsupportedEncodingException {
        String str;
        map.put("name", get(context, "name"));
        try {
            str = context.getPackageManager().getPackageInfo(context.getPackageName(), 1).versionName;
        } catch (Exception unused) {
            str = null;
        }
        map.put("pv", str);
        map.put(WXConfig.os, Integer.valueOf(Build.VERSION.SDK_INT));
        map.put("vb", PdrUtil.isEmpty("1.9.9.82603") ? "" : "1.9.9.82603");
        pullRad(context, map, new ADReceiver(context), new ADResult.CADReceiver(context));
    }

    private static String psas(Context context) throws InterruptedException {
        final StringBuffer stringBuffer = new StringBuffer();
        listExpiresAdData(context, new AdDataWatcher<File>() { // from class: io.dcloud.feature.gg.dcloud.ADHandler.10
            @Override // io.dcloud.feature.gg.dcloud.ADHandler.AdDataWatcher
            public boolean find() {
                return false;
            }

            @Override // io.dcloud.feature.gg.dcloud.ADHandler.AdDataWatcher
            public void operate(File file) throws Throwable {
                byte[] all = DHFile.readAll(file.getAbsolutePath() + "/tid.txt");
                if (all != null) {
                    stringBuffer.append(new String(all)).append(",");
                }
            }
        });
        return stringBuffer.length() > 0 ? stringBuffer.substring(0, stringBuffer.length() - 1) : stringBuffer.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void pull(final Context context, String str, boolean z, List<HostPicker.Host> list, IAolReceiver... iAolReceiverArr) throws IOException, ClassNotFoundException {
        if (TextUtils.isEmpty(BaseInfo.sDefaultBootApp)) {
            BaseInfo.parseControl();
        }
        boolean z2 = false;
        boolean zStartsWith = !TextUtils.isEmpty(BaseInfo.sDefaultBootApp) ? BaseInfo.sDefaultBootApp.startsWith("__UNI__") : false;
        String[] strArrSplit = mc(context).split("\\|");
        String str2 = (strArrSplit == null || strArrSplit.length <= 2) ? "" : strArrSplit[2];
        DCloudAOLManager.InitConfig initConfig = new DCloudAOLManager.InitConfig();
        initConfig.setAdId(str2).setAppId(BaseInfo.sDefaultBootApp);
        s3.a(context, initConfig, new t3());
        DCloudAOLManager.setPrivacyConfig(new PrivacyManager.a() { // from class: io.dcloud.feature.gg.dcloud.ADHandler.1
            @Override // io.dcloud.sdk.poly.base.utils.PrivacyManager.a
            public String getAndroidId() {
                return TelephonyUtil.getAId(context);
            }

            @Override // io.dcloud.sdk.poly.base.utils.PrivacyManager.a
            public String[] getImeis() {
                return TelephonyUtil.getMultiIMEI(context);
            }

            @Override // io.dcloud.sdk.poly.base.utils.PrivacyManager.a
            public String getImsi() {
                return TelephonyUtil.getIMSI(context);
            }

            @Override // io.dcloud.sdk.poly.base.utils.PrivacyManager.a
            public boolean isAllowPrivacy() {
                return !AppRuntime.hasPrivacyForNotShown(context);
            }
        });
        t3 t3Var = (t3) r0.d().a();
        handler = t3Var;
        HashMap mapB = t3Var.b(context);
        mapB.put(CreateShortResultReceiver.KEY_VERSIONNAME, get(context, CreateShortResultReceiver.KEY_VERSIONNAME));
        mapB.put("name", get(context, "name"));
        mapB.put("psas", psas(context));
        mapB.put("ps", Integer.valueOf(BaseInfo.existsStreamEnv() ? 1 : 0));
        mapB.put("psd", Integer.valueOf(BaseInfo.ISDEBUG ? 1 : 0));
        Boolean boolSplashAdIsEnable = SplashAdIsEnable(context);
        Boolean boolDefAdConfig = defAdConfig(context);
        mapB.put("pap", boolSplashAdIsEnable.booleanValue() ? "1" : WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
        mapB.put("papi", boolDefAdConfig.booleanValue() ? "1" : WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
        mapB.put("psaf", allReady(context) ? WXInstanceApm.VALUE_ERROR_CODE_DEFAULT : "1");
        String str3 = get("cad");
        if (!TextUtils.isEmpty(str3)) {
            mapB.put("rad", str3);
        }
        String strA = SP.getsOrCreateBundle(context, d1.b("IlKgfnao")).a(d1.b("[xdi{`IlMfijdm"), AbsoluteConst.TRUE);
        mapB.put("mpap", strA != null && strA.equalsIgnoreCase(AbsoluteConst.FALSE) ? WXInstanceApm.VALUE_ERROR_CODE_DEFAULT : "1");
        String string = new JSONObject(mapB).toString();
        if (!zStartsWith) {
            try {
                Class.forName("io.dcloud.common.cs.DA");
                zStartsWith = true;
            } catch (ClassNotFoundException unused) {
            }
        }
        if (zStartsWith) {
            try {
                Object objInvokeMethod = PlatformUtil.invokeMethod("io.dcloud.common.cs.DA", "getInstance", null);
                if (objInvokeMethod != null && (objInvokeMethod instanceof DAI)) {
                    if (list != null) {
                        ((DAI) objInvokeMethod).act(string, new ADResult(iAolReceiverArr));
                    } else {
                        ((DAI) objInvokeMethod).ar(string, new ADResult(iAolReceiverArr));
                    }
                    z2 = true;
                }
            } catch (Exception e) {
                Logger.e("ADHANDLER", e.toString());
            }
        }
        if (z2) {
            return;
        }
        exec5Plus(list, string, iAolReceiverArr);
    }

    static void pullRad(Context context, Map<String, Object> map, final IAolReceiver... iAolReceiverArr) throws UnsupportedEncodingException {
        String strEncode;
        if (TextUtils.isEmpty(BaseInfo.sDefaultBootApp)) {
            BaseInfo.parseControl();
        }
        HashMap map2 = new HashMap();
        map2.put(ContextChain.TAG_PRODUCT, "a");
        map2.put(CreateShortResultReceiver.KEY_VERSIONNAME, get(CreateShortResultReceiver.KEY_VERSIONNAME));
        map2.put("appid", BaseInfo.sDefaultBootApp);
        map2.put("vb", PdrUtil.isEmpty("1.9.9.82603") ? "" : "1.9.9.82603");
        map2.put("imei", TelephonyUtil.getIMEI(context, true, true));
        if (map == null || !map.containsKey("rad") || PdrUtil.isEmpty(map.get("rad"))) {
            return;
        }
        map2.put("pn", context.getPackageName());
        map2.put("mc", mc(context));
        map2.put("paid", get(context, "adid"));
        map2.put("psdk", 0);
        map2.putAll(map);
        try {
            strEncode = URLEncoder.encode(Base64.encodeToString(AESUtil.encrypt(d1.c(), d1.b(), ZipUtils.zipString(new JSONObject(map2).toString())), 2), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            strEncode = null;
        }
        final String str = "edata=" + strEncode;
        final boolean z = !hasOtherAd();
        addThreadTask(new ThreadTask() { // from class: io.dcloud.feature.gg.dcloud.ADHandler.5
            @Override // io.dcloud.feature.gg.dcloud.ADHandler.ThreadTask
            public void execute() {
                ArrayList arrayList = new ArrayList();
                if (PdrUtil.checkIntl()) {
                    arrayList.add(new HostPicker.Host("YHx8eHsyJydvazkmbGtkZ31sJmFnJ2tnZGRta3wneGR9e2l4eCdraWw=", HostPicker.Host.PriorityEnum.FIRST));
                } else if (TextUtils.isEmpty(l0.b()) || "CN".equalsIgnoreCase(l0.b())) {
                    arrayList.add(new HostPicker.Host("YHx8eHsyJydrOSZsa2RnfWwmZm18JmtmJ2tnZGRta3wneGR9e2l4eCdraWw=", HostPicker.Host.PriorityEnum.FIRST));
                } else {
                    arrayList.add(new HostPicker.Host("YHx8eHsyJydvazkmbGtkZ31sJmZtfCZrZidrZ2RkbWt8J3hkfXtpeHgna2ls", HostPicker.Host.PriorityEnum.FIRST));
                }
                ADHandler.pull(arrayList, "CAD", str, z, iAolReceiverArr);
            }
        });
    }

    public static void setSplashAd(Context context, boolean z, IAolReceiver... iAolReceiverArr) throws IOException {
        String str;
        HashMap map = new HashMap();
        map.put(ContextChain.TAG_PRODUCT, "a");
        map.put(CreateShortResultReceiver.KEY_VERSIONNAME, get(context, CreateShortResultReceiver.KEY_VERSIONNAME));
        map.put("appid", BaseInfo.sDefaultBootApp);
        map.put("name", get(context, "name"));
        try {
            map.put("pname", context.getApplicationInfo().loadLabel(context.getPackageManager()));
        } catch (Exception unused) {
        }
        map.put("pn", context.getPackageName());
        try {
            str = context.getPackageManager().getPackageInfo(context.getPackageName(), 1).versionName;
        } catch (Exception e) {
            e.printStackTrace();
            str = null;
        }
        map.put("pv", str);
        map.put(WXConfig.os, Integer.valueOf(Build.VERSION.SDK_INT));
        map.put("vb", PdrUtil.isEmpty("1.9.9.82603") ? "" : "1.9.9.82603");
        map.put("mc", mc(context));
        map.put("psdk", 0);
        map.put("mpap", z ? "1" : WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
        map.put("smpap", "1");
        exec5Plus(null, new JSONObject(map).toString(), iAolReceiverArr);
    }

    private static void sortDesc(File[] fileArr) {
        if (fileArr == null) {
            return;
        }
        for (int i = 0; i < fileArr.length - 1; i++) {
            int i2 = 0;
            while (i2 < (fileArr.length - 1) - i) {
                int i3 = i2 + 1;
                if (Long.parseLong(fileArr[i2].getName()) < Long.parseLong(fileArr[i3].getName())) {
                    File file = fileArr[i2];
                    fileArr[i2] = fileArr[i3];
                    fileArr[i3] = file;
                }
                i2 = i3;
            }
        }
    }

    static void view(final Context context, final AdData adData, final String str) {
        final String strOptString = adData.data().optString("tid");
        ThreadPool.self().addThreadTask(new Runnable() { // from class: io.dcloud.feature.gg.dcloud.ADHandler.7
            @Override // java.lang.Runnable
            public void run() {
                int i = adData.isEShow() ? 45 : 40;
                JSONObject jSONObjectFull = adData.full();
                TestUtil.PointTime.commitTid(context, adData.mOriginalAppid, strOptString, str, i, AolSplashUtil.getSplashAdpId("_adpid_", "UNIAD_SPLASH_ADPID"), false, (jSONObjectFull == null || !jSONObjectFull.has("ua")) ? "" : jSONObjectFull.optString("ua"));
            }
        });
        if ("wanka".equals(adData.mProvider)) {
            ADHandler_wanka.view_wanka(context, adData, str);
        } else if ("youdao".equals(adData.mProvider)) {
            ADHandler_youdao.view_youdao(context, adData, str);
        } else if ("common".equals(adData.mProvider)) {
            Aolhandler_common.handletask_common(context, adData, str, "imptracker");
        }
    }

    static String get(Context context, String str) {
        return SP.getBundleData(context, AdTag, str);
    }

    static AdData getBestAdData(final Context context, String str, final AdData adData) throws InterruptedException, IOException {
        adData.mOriginalAppid = str;
        expiresFileList = new LinkedList<>();
        listExpiresAdData(context, new AdDataWatcher<File>() { // from class: io.dcloud.feature.gg.dcloud.ADHandler.11
            @Override // io.dcloud.feature.gg.dcloud.ADHandler.AdDataWatcher
            public boolean find() {
                return adData.check();
            }

            @Override // io.dcloud.feature.gg.dcloud.ADHandler.AdDataWatcher
            public void operate(File file) throws IOException {
                ADHandler.expiresFileList.add(file);
                ADHandler.fileAdData(context, file, adData);
            }
        });
        if (!adData.check() && expiresFileList.size() != 0) {
            for (int i = 0; i < expiresFileList.size(); i++) {
                new File(expiresFileList.get(i).getAbsolutePath() + "/s.txt").delete();
                if (i == 0) {
                    fileAdData(context, expiresFileList.get(i), adData);
                }
            }
        }
        return adData;
    }

    public static String get(String str) {
        return SP.getBundleData(AdTag, str);
    }

    public static void pull(Context context, String str) throws IOException, ClassNotFoundException {
        ArrayList arrayList = new ArrayList();
        for (String str2 : q1.c.keySet()) {
            HostPicker.Host.PriorityEnum priorityEnum = HostPicker.Host.PriorityEnum.BACKUP;
            int iIntValue = ((Integer) q1.c.get(str2)).intValue();
            if (iIntValue != -1) {
                if (iIntValue == 0) {
                    priorityEnum = HostPicker.Host.PriorityEnum.NORMAL;
                } else if (iIntValue == 1) {
                    priorityEnum = HostPicker.Host.PriorityEnum.FIRST;
                }
            }
            arrayList.add(new HostPicker.Host(str2, priorityEnum));
        }
        pull(context, str, false, (List<HostPicker.Host>) arrayList, new ADReceiver(context));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void pull(List<HostPicker.Host> list, final String str, final String str2, final boolean z, final IAolReceiver... iAolReceiverArr) {
        HostPicker.getInstance().pickSuitHost(DCLoudApplicationImpl.self().getContext(), list, str, new HostPicker.HostPickCallback() { // from class: io.dcloud.feature.gg.dcloud.ADHandler.4
            String[] message = new String[1];

            @Override // io.dcloud.common.util.hostpicker.HostPicker.HostPickCallback
            public boolean doRequest(HostPicker.Host host) {
                byte[] bArrHttpPost = NetTool.httpPost(host.getRealHost(), str2, new HashMap(), false, z, this.message);
                if (bArrHttpPost == null) {
                    return false;
                }
                try {
                    ADHandler.analysisPullData(bArrHttpPost, str, iAolReceiverArr);
                    return true;
                } catch (Exception e) {
                    for (IAolReceiver iAolReceiver : iAolReceiverArr) {
                        iAolReceiver.onError("Exception", e.getMessage());
                    }
                    if (ADHandler.handler == null) {
                        return true;
                    }
                    ADHandler.handler.a(-5007, e.getMessage());
                    return true;
                }
            }

            @Override // io.dcloud.common.util.hostpicker.HostPicker.HostPickCallback
            public void onNoOnePicked() {
                String str3;
                IAolReceiver[] iAolReceiverArr2 = iAolReceiverArr;
                int length = iAolReceiverArr2.length;
                int i = 0;
                while (true) {
                    str3 = "data invalid";
                    if (i >= length) {
                        break;
                    }
                    IAolReceiver iAolReceiver = iAolReceiverArr2[i];
                    String str4 = this.message[0];
                    if (str4 != null) {
                        str3 = str4;
                    }
                    iAolReceiver.onError("NotFountDataError", str3);
                    i++;
                }
                if (ADHandler.handler != null) {
                    t3 t3Var = ADHandler.handler;
                    String str5 = this.message[0];
                    t3Var.a(-5007, str5 != null ? str5 : "data invalid");
                }
            }

            @Override // io.dcloud.common.util.hostpicker.HostPicker.HostPickCallback
            public void onOneSelected(HostPicker.Host host) {
            }
        });
    }
}
