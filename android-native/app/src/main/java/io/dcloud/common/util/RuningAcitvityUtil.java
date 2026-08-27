package io.dcloud.common.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import io.dcloud.application.DCLoudApplicationImpl;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class RuningAcitvityUtil {
    private static HashMap<String, WeakReference<Activity>> RuningActivitys = new HashMap<>();
    public static boolean isRuningActivity = false;

    public static void StartActivity(Context context, Intent intent) {
        Intent intent2 = new Intent(intent);
        intent2.setAction("android.intent.action.VIEW");
        context.startActivity(intent2);
    }

    public static Activity getActivity(String str) {
        WeakReference<Activity> weakReference;
        if (!RuningActivitys.containsKey(str) || (weakReference = RuningActivitys.get(str)) == null) {
            return null;
        }
        return weakReference.get();
    }

    public static String getAppName(Context context) {
        return ProcessUtil.getCurrentProcessName(context);
    }

    public static Activity getTopRuningActivity(Activity activity) {
        Activity activity2;
        Activity activity3 = DCLoudApplicationImpl.self().topActivity;
        if (activity3 != null) {
            String localClassName = activity3.getLocalClassName();
            if (RuningActivitys.containsKey(localClassName)) {
                WeakReference<Activity> weakReference = RuningActivitys.get(localClassName);
                if (weakReference != null) {
                    return weakReference.get();
                }
            } else if ("com.g.gysdk.view.ELoginActivity".equals(localClassName) && (activity2 = DCLoudApplicationImpl.self().topActiveMap.get(localClassName).get()) != null) {
                return activity2;
            }
        }
        return activity;
    }

    public static boolean isRunningProcess(Context context, String str) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        if (activityManager != null && !TextUtils.isEmpty(str) && (runningAppProcesses = activityManager.getRunningAppProcesses()) != null) {
            Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
            while (it.hasNext()) {
                if (TextUtils.equals(it.next().processName, str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void putRuningActivity(Activity activity) {
        String className = activity.getComponentName().getClassName();
        if (RuningActivitys.containsKey(className)) {
            return;
        }
        RuningActivitys.put(className, new WeakReference<>(activity));
    }

    public static void removeRuningActivity(String str) {
        if (RuningActivitys.containsKey(str)) {
            RuningActivitys.remove(str);
        }
    }
}
