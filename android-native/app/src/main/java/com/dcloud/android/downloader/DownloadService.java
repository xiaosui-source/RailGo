package com.dcloud.android.downloader;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import com.dcloud.android.downloader.callback.DCDownloadManager;
import com.dcloud.android.downloader.config.Config;
import java.util.List;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class DownloadService extends Service {
    private static final String TAG = "DownloadService";
    public static DCDownloadManager downloadManager;

    public static DCDownloadManager getDownloadManager(Context context) {
        return getDownloadManager(context, null);
    }

    private static boolean isServiceRunning(Context context) throws SecurityException {
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE);
        if (runningServices != null && runningServices.size() != 0) {
            for (int i = 0; i < runningServices.size(); i++) {
                if (runningServices.get(i).service.getClassName().equals(DownloadService.class.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onDestroy() {
        DCDownloadManager dCDownloadManager = downloadManager;
        if (dCDownloadManager != null) {
            dCDownloadManager.onDestroy();
            downloadManager = null;
        }
        super.onDestroy();
    }

    public static DCDownloadManager getDownloadManager(Context context, Config config) {
        if (!isServiceRunning(context)) {
            context.startService(new Intent(context, (Class<?>) DownloadService.class));
        }
        if (downloadManager == null) {
            downloadManager = DownloadManagerImpl.getInstance(context, config);
        }
        return downloadManager;
    }
}
