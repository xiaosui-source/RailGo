package io.dcloud.common.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.dcloud.android.downloader.DownloadService;
import com.dcloud.android.downloader.callback.DCDownloadManager;
import com.dcloud.android.downloader.callback.DownloadListener;
import com.dcloud.android.downloader.domain.DownloadInfo;
import com.dcloud.android.downloader.exception.DownloadException;
import com.dcloud.android.widget.toast.ToastCompat;
import io.dcloud.WebviewActivity;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.ILoadCallBack;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.DownloadUtil;
import io.dcloud.common.adapter.util.MessageHandler;
import io.dcloud.common.adapter.util.MobilePhoneModel;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.IntentConst;
import io.dcloud.p.d0;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class ADUtils {
    private static final DownloadListener DOWNLOAD_DC_LISTENER = new DownloadListener() { // from class: io.dcloud.common.util.ADUtils.1
        @Override // com.dcloud.android.downloader.callback.DownloadListener
        public void onDownloadFailed(DownloadInfo downloadInfo, DownloadException downloadException) {
            downloadException.printStackTrace();
            JSONObject loadData = ADUtils.getLoadData(downloadInfo.getId());
            if (loadData != null) {
                ADUtils.downloadCommit(downloadInfo.getContext(), loadData.optString("appid"), loadData.optString("tid"), loadData.optString("adid"), 32, String.valueOf(downloadException.getCode()), downloadException.getMessage(), loadData.optString("ua"));
            }
        }

        @Override // com.dcloud.android.downloader.callback.DownloadListener
        public void onDownloadSuccess(DownloadInfo downloadInfo) {
            JSONObject jSONObjectRemoveDownlaodData = ADUtils.removeDownlaodData(downloadInfo.getContext(), downloadInfo.getId());
            if (jSONObjectRemoveDownlaodData != null) {
                ADUtils.downloadCommit(downloadInfo.getContext(), jSONObjectRemoveDownlaodData.optString("appid"), jSONObjectRemoveDownlaodData.optString("tid"), jSONObjectRemoveDownlaodData.optString("adid"), 30, null, null, jSONObjectRemoveDownlaodData.optString("ua"));
                ADUtils.saveLoadAppData(downloadInfo.getContext(), jSONObjectRemoveDownlaodData.optString("pname"), jSONObjectRemoveDownlaodData.optString("appid"), jSONObjectRemoveDownlaodData.optString("tid"), jSONObjectRemoveDownlaodData.optString("adid"), downloadInfo.getPath(), jSONObjectRemoveDownlaodData.optString("ua"));
            }
            Intent aPKInstallIntent = DownloadUtil.getAPKInstallIntent(downloadInfo.getContext(), downloadInfo.getPath());
            if (downloadInfo.getTag() == null || !(downloadInfo.getTag() instanceof ILoadCallBack)) {
                downloadInfo.getContext().startActivity(aPKInstallIntent);
            } else {
                ((ILoadCallBack) downloadInfo.getTag()).onCallBack(0, downloadInfo.getContext(), aPKInstallIntent);
            }
            DownloadService.getDownloadManager(downloadInfo.getContext().getApplicationContext()).remove(downloadInfo);
        }

        @Override // com.dcloud.android.downloader.callback.DownloadListener
        public void onDownloading(long j, long j2) {
        }

        @Override // com.dcloud.android.downloader.callback.DownloadListener
        public void onPaused() {
        }

        @Override // com.dcloud.android.downloader.callback.DownloadListener
        public void onRemoved() {
        }

        @Override // com.dcloud.android.downloader.callback.DownloadListener
        public void onStart() {
        }

        @Override // com.dcloud.android.downloader.callback.DownloadListener
        public void onWaited() {
        }
    };
    private static final String TAG = "ADUtils";

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class ADLoadData {
        public String adid;
        public String appid;
        public long expiresTime;
        public long id;
        public String name;
        public String pname;
        public String tid;
        public String type = "default";
        public String ua;
        public String url;
    }

    private ADUtils() {
    }

    public static Object ADHandlerMethod(String str, Object... objArr) {
        try {
            Class[] clsArr = new Class[0];
            if (objArr != null && objArr.length > 0) {
                clsArr = new Class[objArr.length];
                for (int i = 0; i < objArr.length; i++) {
                    clsArr[i] = objArr[i].getClass();
                }
            }
            return PlatformUtil.invokeMethod("io.dcloud.feature.gg.dcloud.ADHandler", str, null, clsArr, objArr);
        } catch (Exception unused) {
            return null;
        }
    }

    public static void downloadCommit(final Context context, final String str, final String str2, final String str3, final int i, final String str4, final String str5, final String str6) {
        ThreadPool.self().addThreadTask(new Runnable() { // from class: io.dcloud.common.util.ADUtils.4
            @Override // java.lang.Runnable
            public void run() {
                d0.a(context, str, str2, str3, i, str4, str5, null, null, null, ADUtils.getSplashAdpId(), str6, null);
            }
        });
    }

    public static JSONObject getDdDataForUrl(String str) {
        JSONObject jSONObject;
        Map<String, ?> all = SP.getOrCreateBundle(AbsoluteConst.AD_DOWNLOAD_DATA).getAll();
        if (all == null || all.size() <= 0) {
            return null;
        }
        Iterator<?> it = all.values().iterator();
        while (it.hasNext()) {
            try {
                jSONObject = new JSONObject((String) it.next());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (str.equals(jSONObject.optString("url"))) {
                return jSONObject;
            }
        }
        return null;
    }

    private static String getDownloadDataKey(long j) {
        return AbsoluteConst.AD_DL_DATA_KEY + String.valueOf(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getLoadAppDataKey(String str) {
        return AbsoluteConst.AD_IA_DATA_KEY + str;
    }

    public static JSONObject getLoadData(long j) {
        try {
            return new JSONObject(SP.getOrCreateBundle(AbsoluteConst.AD_DOWNLOAD_DATA).getString(getDownloadDataKey(j), ""));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getSplashAdpId() {
        return String.valueOf(PlatformUtil.invokeMethod("io.dcloud.feature.gg.AolSplashUtil", "getSplashAdpId", null, new Class[]{IApp.class, String.class, String.class}, new Object[]{null, "_adpid_", "UNIAD_SPLASH_ADPID"}));
    }

    public static long loadADFile(Context context, String str, String str2, String str3, String str4, String str5, String str6, String str7, ILoadCallBack iLoadCallBack, boolean z, String str8) {
        if (z) {
            downloadCommit(context, str, str2, str3, 29, null, null, str8);
        }
        DCDownloadManager downloadManager = DownloadService.getDownloadManager(context.getApplicationContext());
        DownloadInfo downloadInfoBuild = new DownloadInfo.Builder().setUrl(str6).setPath(DeviceInfo.sDeviceRootDir + "/Download/" + str4).build(context);
        downloadInfoBuild.setTag(iLoadCallBack);
        downloadInfoBuild.setDownloadListener(DOWNLOAD_DC_LISTENER);
        downloadManager.download(downloadInfoBuild);
        return downloadInfoBuild.getId();
    }

    public static void loadAppTip(Context context) {
        MessageHandler.sendMessage(new MessageHandler.IMessages() { // from class: io.dcloud.common.util.ADUtils.3
            @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
            public void execute(Object obj) {
                ToastCompat.makeText((Context) obj, R.string.dcloud_common_download_tips1, 0).show();
            }
        }, context);
    }

    public static void openBrowser(Context context, String str) {
        try {
            Intent intent = new Intent();
            if (!Build.BRAND.equalsIgnoreCase(MobilePhoneModel.VIVO) && LoadAppUtils.isAppLoad(context, "com.android.browser")) {
                intent.setPackage("com.android.browser");
            }
            intent.setData(Uri.parse(str));
            intent.setAction("android.intent.action.VIEW");
            intent.setFlags(268435456);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "openBrowser exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static boolean openDeepLink(Context context, String str) throws URISyntaxException {
        try {
            Intent uri = Intent.parseUri(str, 1);
            if (BaseInfo.isDefense) {
                uri.setSelector(null);
                uri.setComponent(null);
                uri.addCategory("android.intent.category.BROWSABLE");
            }
            List<ResolveInfo> listQueryIntentActivities = context.getPackageManager().queryIntentActivities(uri, 65536);
            if (listQueryIntentActivities == null || listQueryIntentActivities.isEmpty()) {
                return false;
            }
            uri.setFlags(268435456);
            context.startActivity(uri);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "openDeepLink exception: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static void openStreamApp(Context context, String str, JSONObject jSONObject, int i, String str2) {
        boolean z;
        try {
            Intent intent = new Intent();
            intent.putExtra(IntentConst.START_FROM, i);
            intent.setAction("android.intent.action.MAIN");
            if (TextUtils.isEmpty(str2)) {
                if (BaseInfo.existsStreamEnv()) {
                    intent.setClassName(context.getPackageName(), "io.dcloud.appstream.StreamAppMainActivity");
                }
            } else if ("com.qihoo.appstore".equals(str2)) {
                intent.setClassName(str2, "io.dcloud.appstream.StreamAppListFakeActivity");
            } else if ("com.aspire.mm".equals(str2)) {
                intent.setClassName(str2, "io.dcloud.StreamAppLauncherActivity");
            } else {
                intent.setClassName(str2, "io.dcloud.appstream.StreamAppMainActivity");
            }
            intent.putExtra("appid", str);
            boolean z2 = true;
            intent.putExtra(IntentConst.IS_STREAM_APP, true);
            if (jSONObject != null) {
                if (jSONObject.has("arguments")) {
                    intent.putExtra(IntentConst.EXTRAS, jSONObject.opt("arguments").toString());
                    z = true;
                } else {
                    z = false;
                }
                if (jSONObject.has("richurl")) {
                    intent.putExtra(IntentConst.DIRECT_PAGE, jSONObject.optString("richurl"));
                    intent.putExtra(IntentConst.IS_START_FIRST_WEB, true);
                } else {
                    z2 = z;
                }
                if (z2) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("action", AbsoluteConst.XML_APP);
                    jSONObject2.put("parameters", jSONObject);
                    intent.putExtra("rules_msg", jSONObject2.toString());
                }
            }
            intent.setFlags(268435456);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "openStreamApp exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void openUrl(Context context, String str) {
        try {
            Intent intent = new Intent();
            intent.setClass(context, WebviewActivity.class);
            intent.putExtra("url", str);
            intent.setData(Uri.parse(str));
            intent.setAction("android.intent.action.VIEW");
            intent.setFlags(268435456);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "openUrl exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static JSONObject removeDownlaodData(Context context, long j) {
        SharedPreferences orCreateBundle = SP.getOrCreateBundle(context, AbsoluteConst.AD_DOWNLOAD_DATA);
        String downloadDataKey = getDownloadDataKey(j);
        String string = orCreateBundle.getString(downloadDataKey, "");
        orCreateBundle.edit().remove(downloadDataKey).commit();
        try {
            return new JSONObject(string);
        } catch (Exception unused) {
            return null;
        }
    }

    public static JSONObject removeLoadAppData(Context context, String str) {
        JSONObject jSONObject;
        SharedPreferences orCreateBundle = SP.getOrCreateBundle(context, AbsoluteConst.AD_INSTALL_DATA);
        String loadAppDataKey = getLoadAppDataKey(str);
        String string = orCreateBundle.getString(loadAppDataKey, "");
        if (TextUtils.isEmpty(string)) {
            jSONObject = null;
        } else {
            try {
                jSONObject = new JSONObject(string);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        orCreateBundle.edit().remove(loadAppDataKey).commit();
        return jSONObject;
    }

    public static void saveLoadAppData(final Context context, final String str, final String str2, final String str3, final String str4, final String str5, final String str6) {
        ThreadPool.self().addThreadTask(new Runnable() { // from class: io.dcloud.common.util.ADUtils.2
            @Override // java.lang.Runnable
            public void run() throws JSONException {
                SharedPreferences orCreateBundle = SP.getOrCreateBundle(context, AbsoluteConst.AD_INSTALL_DATA);
                String loadAppDataKey = ADUtils.getLoadAppDataKey(str);
                JSONObject jSONObject = new JSONObject();
                try {
                    if (PdrUtil.isEmpty(str)) {
                        PackageInfo apkInfo = PlatformUtil.parseApkInfo(context, str5);
                        if (apkInfo == null) {
                            return;
                        }
                        jSONObject.put("packName", apkInfo.packageName);
                        loadAppDataKey = ADUtils.getLoadAppDataKey(apkInfo.packageName);
                    } else {
                        jSONObject.put("packName", str);
                    }
                    jSONObject.put("appid", str2);
                    jSONObject.put("tid", str3);
                    jSONObject.put("adid", str4);
                    jSONObject.put("ua", str6);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                orCreateBundle.edit().putString(loadAppDataKey, jSONObject.toString()).commit();
            }
        });
    }

    public static void saveLoadData(ADLoadData aDLoadData) throws Exception {
        SharedPreferences orCreateBundle = SP.getOrCreateBundle(AbsoluteConst.AD_DOWNLOAD_DATA);
        String downloadDataKey = getDownloadDataKey(aDLoadData.id);
        if (TextUtils.isEmpty(orCreateBundle.getString(downloadDataKey, ""))) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("name", aDLoadData.name);
            jSONObject.put("url", aDLoadData.url);
            jSONObject.put("pname", aDLoadData.pname);
            jSONObject.put("id", aDLoadData.id);
            jSONObject.put("expiresTime", aDLoadData.expiresTime);
            jSONObject.put("tid", aDLoadData.tid);
            jSONObject.put("appid", aDLoadData.appid);
            jSONObject.put("adid", aDLoadData.adid);
            jSONObject.put("type", aDLoadData.type);
            jSONObject.put("ua", aDLoadData.ua);
            orCreateBundle.edit().putString(downloadDataKey, jSONObject.toString()).commit();
        }
    }
}
