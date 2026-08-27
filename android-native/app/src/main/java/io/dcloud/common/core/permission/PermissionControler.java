package io.dcloud.common.core.permission;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import io.dcloud.application.DCLoudApplicationImpl;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.DHInterface.message.ActionBus;
import io.dcloud.common.DHInterface.message.action.PermissionRequestAction;
import io.dcloud.common.adapter.util.MobilePhoneModel;
import io.dcloud.common.adapter.util.PermissionUtil;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.util.AppPermissionUtil;
import io.dcloud.common.util.ShortCutUtil;
import io.dcloud.feature.internal.sdk.SDK;
import io.src.dcloud.adapter.DCloudBaseActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public final class PermissionControler {
    public static final int PERMISSION_DENIED = -1;
    public static final int PERMISSION_GRANTED = 0;
    static HashMap<String, ArrayList<String>> mAppPermissions = new HashMap<>(2);
    static ArrayList<String> mRootAppList = new ArrayList<>();
    private static LinkedList<a> mRtPnInfos = new LinkedList<>();

    public static boolean checkPermission(String str, String str2) {
        return true;
    }

    public static boolean checkSafePermission(String str, String str2) {
        if ("console".equals(str2)) {
            return true;
        }
        Locale locale = Locale.ENGLISH;
        return "events".equals(str2.toLowerCase(locale)) || "uninview".equals(str2.toLowerCase(locale)) || "webview-x5".equals(str2.toLowerCase(locale)) || "uiwebview".equals(str2.toLowerCase(locale)) || "faceid".equals(str2.toLowerCase(locale)) || "canvas".equals(str2.toLowerCase(locale)) || "record".equals(str2.toLowerCase(locale));
    }

    public static void clearCRequestPermissionsCache() {
        mRtPnInfos.clear();
    }

    public static String convert5PlusValue(int i) {
        return i != -1 ? i != 0 ? "unknown" : IApp.AUTHORITY_AUTHORIZED : IApp.AUTHORITY_UNDETERMINED;
    }

    public static String convertNativePermission(String str) {
        return PermissionUtil.convert2SystemPermission(str);
    }

    public static void destroy() {
        mAppPermissions.clear();
        mAppPermissions = null;
    }

    public static ArrayList<String> getPermissionList(String str) {
        return mAppPermissions.get(str);
    }

    public static String getPermissionsErrorDesp(String str) {
        str.getClass();
        if (str.equals("invocation")) {
            return DCLoudApplicationImpl.self().getContext().getString(R.string.dcloud_permissions_njs_tips1);
        }
        return null;
    }

    public static void invokeUTSAndroidPermissionRequest(String str, String[] strArr) {
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String str2 : strArr) {
            arrayList.add(PermissionUtil.convert2SystemPermission(str2));
        }
        ActionBus.getInstance().sendToBus(PermissionRequestAction.obtain(str, (String[]) arrayList.toArray(new String[0])));
    }

    public static void registerPermission(String str, ArrayList<String> arrayList) {
        mAppPermissions.remove(str);
        mAppPermissions.put(str, arrayList);
    }

    public static void registerRootPermission(String str) {
        mRootAppList.add(str);
    }

    private static void removeRequestPermissionForCode(int i) {
        Iterator<a> it = mRtPnInfos.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (next.b == i) {
                mRtPnInfos.remove(next);
                return;
            }
        }
    }

    public static void requestPermissions(Activity activity, String[] strArr, int i) {
        if (activity == null || strArr == null || strArr.length <= 0) {
            return;
        }
        if (!(activity instanceof DCloudBaseActivity)) {
            runRequestPermissions(activity, strArr, i);
            return;
        }
        mRtPnInfos.offer(new a(strArr, i));
        if (mRtPnInfos.size() == 1) {
            runRequestPermissions(activity, strArr, i);
        }
    }

    public static void runNextRequestPermission(Activity activity, int i) {
        a first;
        removeRequestPermissionForCode(i);
        if (mRtPnInfos.size() <= 0 || (first = mRtPnInfos.getFirst()) == null) {
            return;
        }
        runRequestPermissions(activity, first.a, first.b);
    }

    private static void runRequestPermissions(Activity activity, String[] strArr, int i) {
        if (strArr == null || strArr.length <= 0) {
            return;
        }
        int i2 = 0;
        if (SDK.isUniMPSDK()) {
            PlatformUtil.invokeMethod(activity.getClass().getName(), "requestUniMPPermissions", activity, new Class[]{strArr.getClass(), Integer.TYPE}, new Object[]{strArr, Integer.valueOf(i)});
            return;
        }
        int length = strArr.length;
        while (true) {
            if (i2 >= length) {
                break;
            }
            if (!PermissionUtil.checkPermissionDenied(activity, strArr[i2])) {
                invokeUTSAndroidPermissionRequest(PermissionRequestAction.TYPE_CONFIRM, strArr);
                break;
            }
            i2++;
        }
        ActivityCompat.requestPermissions(activity, strArr, i);
    }

    public static void unregisterRootPermission(String str) {
        mRootAppList.remove(str);
    }

    public static String checkPermission(IWebview iWebview, String[] strArr) {
        String str = strArr[0];
        iWebview.obtainApp().obtainAppId();
        String strObtainAppName = iWebview.obtainApp().obtainAppName();
        Context context = iWebview.getContext();
        if (!str.equals("SHORTCUT")) {
            return String.valueOf(convert5PlusValue(iWebview.obtainApp().checkSelfPermission(convertNativePermission(str), iWebview.obtainApp().obtainAppName())));
        }
        String str2 = Build.BRAND;
        if (str2.equalsIgnoreCase(MobilePhoneModel.MEIZU)) {
            return !AppPermissionUtil.isFlymeShortcutallowAllow(context, ShortCutUtil.getHeadShortCutIntent(strObtainAppName)) ? IApp.AUTHORITY_DENIED : "notdeny";
        }
        String str3 = Build.MANUFACTURER;
        if (str3.equalsIgnoreCase(MobilePhoneModel.SMARTISAN)) {
            return MobilePhoneModel.isSmartisanLauncherPhone(context) ? IApp.AUTHORITY_DENIED : "notdeny";
        }
        if (AppPermissionUtil.getShotCutOpId() == -1) {
            return "unknown";
        }
        AppPermissionUtil.getShotCutOpId();
        if (!str2.equalsIgnoreCase(MobilePhoneModel.XIAOMI)) {
            return str3.equalsIgnoreCase(MobilePhoneModel.HUAWEI) ? !AppPermissionUtil.isEmuiShortcutallowAllow() ? IApp.AUTHORITY_DENIED : "notdeny" : "unknown";
        }
        int iCheckOp = AppPermissionUtil.checkOp(context);
        return iCheckOp != -1 ? iCheckOp != 0 ? iCheckOp != 1 ? (iCheckOp == 3 || iCheckOp == 4) ? IApp.AUTHORITY_UNDETERMINED : "unknown" : IApp.AUTHORITY_DENIED : IApp.AUTHORITY_AUTHORIZED : "unsupported";
    }
}
