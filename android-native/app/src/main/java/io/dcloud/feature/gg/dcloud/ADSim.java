package io.dcloud.feature.gg.dcloud;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.dcloud.android.downloader.DownloadService;
import com.dcloud.android.downloader.callback.DCDownloadManager;
import com.dcloud.android.downloader.callback.DownloadListener;
import com.dcloud.android.downloader.domain.DownloadInfo;
import com.dcloud.android.downloader.exception.DownloadException;
import io.dcloud.common.DHInterface.ILoadCallBack;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.util.ADUtils;
import io.dcloud.common.util.NetTool;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.ThreadPool;
import io.dcloud.feature.gg.dcloud.ADHandler;
import java.io.File;
import java.util.HashMap;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class ADSim {
    public static final int INTISPLSH = 10000;
    ADHandler.AdData mAdData = null;
    private Context mContext;
    JSONObject mData;
    Handler mHandler;

    public ADSim(Context context, JSONObject jSONObject) {
        this.mHandler = null;
        this.mData = jSONObject;
        this.mContext = context;
        this.mHandler = new Handler(context.getMainLooper()) { // from class: io.dcloud.feature.gg.dcloud.ADSim.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if (message.what != 10000) {
                    return;
                }
                ADSim.this.initSimSplsh();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void click() {
        ADHandler.log("adh", "ADSim---click");
        Context context = this.mContext;
        ADHandler.click(context, this.mAdData, ADHandler.get(context, "adid"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ADHandler.AdData crateAdData(JSONObject jSONObject) {
        ADHandler.AdData adData = new ADHandler.AdData();
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
        if (jSONObjectOptJSONObject != null) {
            adData.mProvider = jSONObject.optString("provider");
            adData.mJsonData = jSONObject;
            adData.mEShow = jSONObject.optInt("es", 0);
            adData.mEClick = jSONObject.optInt("ec", 0);
            adData.mImgSrc = jSONObjectOptJSONObject.optString("src");
            adData.mImgData = "000";
            adData.mOriginalAppid = ADHandler.get("appid");
        }
        return adData;
    }

    public static void dwApp(final Context context, final String str, final String str2, final String str3, String str4, String str5, ILoadCallBack iLoadCallBack, final String str6) {
        ADUtils.downloadCommit(context, str, str2, str3, 29, null, null, str6);
        final DCDownloadManager downloadManager = DownloadService.getDownloadManager(context.getApplicationContext());
        String str7 = DeviceInfo.sDeviceRootDir + "/Download/ADSIM-INFO.io";
        File file = new File(str7);
        if (file.exists()) {
            file.delete();
        }
        for (DownloadInfo downloadInfo : downloadManager.findAllDownloading()) {
            if (downloadInfo.getUri().equals(str4)) {
                downloadManager.remove(downloadInfo);
            }
        }
        DownloadInfo downloadInfoBuild = new DownloadInfo.Builder().setUrl(str4).setPath(str7).build(context);
        if (iLoadCallBack != null) {
            downloadInfoBuild.setTag(iLoadCallBack);
        }
        downloadInfoBuild.setDownloadListener(new DownloadListener() { // from class: io.dcloud.feature.gg.dcloud.ADSim.5
            @Override // com.dcloud.android.downloader.callback.DownloadListener
            public void onDownloadFailed(DownloadInfo downloadInfo2, DownloadException downloadException) {
                ADUtils.downloadCommit(context, str, str2, str3, 32, null, null, str6);
                File file2 = new File(downloadInfo2.getPath());
                if (file2.exists()) {
                    file2.delete();
                }
                downloadManager.remove(downloadInfo2);
            }

            @Override // com.dcloud.android.downloader.callback.DownloadListener
            public void onDownloadSuccess(DownloadInfo downloadInfo2) {
                if (downloadInfo2.getTag() != null && (downloadInfo2.getTag() instanceof ILoadCallBack)) {
                    ((ILoadCallBack) downloadInfo2.getTag()).onCallBack(0, downloadInfo2.getContext(), null);
                }
                ADUtils.downloadCommit(context, str, str2, str3, 30, null, null, str6);
                File file2 = new File(downloadInfo2.getPath());
                if (file2.exists()) {
                    file2.delete();
                }
                downloadManager.remove(downloadInfo2);
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
        });
        downloadManager.download(downloadInfoBuild);
    }

    public static int getRandomInt(int i, int i2) {
        return (int) (i + (Math.random() * ((i2 - i) + 1)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initAdImg(final String str, final String str2) {
        ThreadPool.self().addThreadTask(new Runnable() { // from class: io.dcloud.feature.gg.dcloud.ADSim.3
            @Override // java.lang.Runnable
            public void run() {
                HashMap map = new HashMap();
                if (!PdrUtil.isEmpty(str2) && str2.equalsIgnoreCase("webview")) {
                    map.put(IWebview.USER_AGENT, ADHandler.get("ua-webview"));
                }
                try {
                    if (NetTool.httpGet(str, (HashMap<String, String>) map, true) != null) {
                        ADSim.this.mHandler.sendEmptyMessage(ADSim.INTISPLSH);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    private void initClick() {
        if (this.mAdData.isEClick()) {
            this.mHandler.postDelayed(new Runnable() { // from class: io.dcloud.feature.gg.dcloud.ADSim.4
                @Override // java.lang.Runnable
                public void run() {
                    ADSim.this.click();
                }
            }, getRandomInt(800, 2000));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initSimSplsh() {
        ADHandler.log("adh", "ADSim---view");
        Context context = this.mContext;
        ADHandler.view(context, this.mAdData, ADHandler.get(context, "adid"));
        initClick();
    }

    public static void openUrl(Context context, String str) {
        ADHandler.log("adh", "ADSim---openUrl");
        new AolWebView(context).loadUrl(str);
    }

    public void start() {
        this.mHandler.postDelayed(new Runnable() { // from class: io.dcloud.feature.gg.dcloud.ADSim.2
            @Override // java.lang.Runnable
            public void run() {
                ADSim aDSim = ADSim.this;
                aDSim.mAdData = aDSim.crateAdData(aDSim.mData);
                ADHandler.AdData adData = ADSim.this.mAdData;
                if (adData != null) {
                    String strOptString = adData.full() != null ? ADSim.this.mAdData.full().optString("ua") : "";
                    ADSim aDSim2 = ADSim.this;
                    aDSim2.initAdImg(aDSim2.mAdData.mImgSrc, strOptString);
                }
            }
        }, getRandomInt(250, 350));
    }
}
