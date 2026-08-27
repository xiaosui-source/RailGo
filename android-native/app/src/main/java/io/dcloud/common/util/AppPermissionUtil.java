package io.dcloud.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.view.Window;
import android.view.WindowManager;
import com.dcloud.android.widget.dialog.DCloudAlertDialog;
import com.dcloud.android.widget.toast.ToastCompat;
import io.dcloud.PdrR;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.adapter.util.MobilePhoneModel;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.ui.PermissionGuideWindow;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class AppPermissionUtil {
    public static final int MODE_ALLOWED = 0;
    public static final int MODE_ASK = 4;
    public static final int MODE_DEFAULT = 3;
    public static final int MODE_ERRORED = 2;
    public static final int MODE_IGNORED = 1;
    public static final int MODE_UNKNOWN = -1;
    public static HashMap<String, Integer> mXiaoMiCode19OPSIDs = new HashMap<>();
    public static HashMap<String, Integer> mXiaoMiCode21OPSIDs = new HashMap<>();
    public static HashMap<String, Integer> mXiaoMiCode23OPSIDs = new HashMap<>();
    public static String OP_INSTALL_SHORTCUT = "op_install_shortcut";

    static {
        mXiaoMiCode19OPSIDs.put("op_install_shortcut", 60);
        mXiaoMiCode21OPSIDs.put(OP_INSTALL_SHORTCUT, 63);
        mXiaoMiCode23OPSIDs.put(OP_INSTALL_SHORTCUT, 10017);
    }

    public static void againShortcutOpsDialog(final IApp iApp, final Activity activity, final String str, String str2) {
        final SharedPreferences orCreateBundle = SP.getOrCreateBundle(activity, "pdr");
        DCloudAlertDialog dCloudAlertDialogInitDialogTheme = DialogUtil.initDialogTheme(activity, true);
        dCloudAlertDialogInitDialogTheme.setTitle(R.string.dcloud_short_cut_set_pms);
        dCloudAlertDialogInitDialogTheme.setMessage(activity.getString(R.string.dcloud_short_cut_create_error_tips));
        dCloudAlertDialogInitDialogTheme.setButton(-1, activity.getString(R.string.dcloud_short_cut_goto_pms), new DialogInterface.OnClickListener() { // from class: io.dcloud.common.util.AppPermissionUtil.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:" + activity.getPackageName()));
                orCreateBundle.edit().putBoolean(str + SP.IS_CREATE_SHORTCUT, true).commit();
                activity.startActivity(intent);
            }
        });
        dCloudAlertDialogInitDialogTheme.setButton(-2, activity.getString(R.string.dcloud_short_cut_not_install), new DialogInterface.OnClickListener() { // from class: io.dcloud.common.util.AppPermissionUtil.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Build.BRAND.equalsIgnoreCase(MobilePhoneModel.MEIZU) || Build.MANUFACTURER.equalsIgnoreCase(MobilePhoneModel.HUAWEI)) {
                    ShortCutUtil.createShortcutToDeskTop(iApp, false);
                }
            }
        });
        dCloudAlertDialogInitDialogTheme.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: io.dcloud.common.util.AppPermissionUtil.6
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialogInterface) {
            }
        });
        dCloudAlertDialogInitDialogTheme.setCanceledOnTouchOutside(false);
        dCloudAlertDialogInitDialogTheme.show();
    }

    public static int checkNoShortcutPermionGuide(Context context, String str, boolean z, IApp iApp, String str2, SharedPreferences sharedPreferences, boolean z2) {
        String str3;
        int i;
        String string = context.getString(R.string.dcloud_short_cut_pms_unauthorized_tips1);
        String str4 = Build.BRAND;
        int iCheckOp = -1;
        if (str4.equalsIgnoreCase(MobilePhoneModel.MEIZU)) {
            if (!isFlymeShortcutallowAllow(context, ShortCutUtil.getHeadShortCutIntent(str))) {
                i = PdrR.DCLOUD_GUIDE_GIF_MEIZU;
                string = context.getString(R.string.dcloud_short_cut_pms_unauthorized_tips2);
                str3 = SP.STAREMAPP_SHORTCUT_GUIDE_IS_FIRST_FLYME;
                iCheckOp = 1;
            }
            str3 = null;
            i = 0;
        } else if (str4.equalsIgnoreCase(MobilePhoneModel.XIAOMI)) {
            iCheckOp = checkOp(context);
            string = context.getString(R.string.dcloud_short_cut_pms_unauthorized_tips2);
            i = PdrR.DCLOUD_GUIDE_GIF_XIAOMI;
            str3 = SP.STAREMAPP_SHORTCUT_GUIDE_IS_FIRST_MIUI;
        } else if (Build.MANUFACTURER.equalsIgnoreCase(MobilePhoneModel.HUAWEI)) {
            if (!isEmuiShortcutallowAllow()) {
                string = context.getString(R.string.dcloud_short_cut_pms_unauthorized_tips3);
                i = PdrR.DCLOUD_GUIDE_GIF_HUAWEI;
                str3 = SP.STAREMAPP_SHORTCUT_GUIDE_IS_FIRST_EMUI;
                iCheckOp = 1;
            }
            str3 = null;
            i = 0;
        } else {
            if (str4.equalsIgnoreCase(MobilePhoneModel.VIVO)) {
                String appVersionName = LoadAppUtils.getAppVersionName(context, "com.iqoo.secure");
                String appName = LoadAppUtils.getAppName(context);
                String strRequestShortCutPermissionVivo = ShortCutUtil.requestShortCutPermissionVivo(context, appName);
                if (!PdrUtil.isEmpty(appVersionName)) {
                    if (appVersionName.startsWith("2") || appVersionName.startsWith("1")) {
                        return -1;
                    }
                    if (PdrUtil.isEquals("1", strRequestShortCutPermissionVivo) || PdrUtil.isEquals("17", strRequestShortCutPermissionVivo)) {
                        if (appVersionName.startsWith("3")) {
                            string = StringUtil.format(context.getString(R.string.dcloud_short_cut_pms_unauthorized_tips4), appName);
                            str3 = SP.STAREMAPP_SHORTCUT_GUIDE_IS_FIRST_VIVO;
                            i = 1;
                        } else {
                            if (appVersionName.startsWith("4")) {
                                string = context.getString(R.string.dcloud_short_cut_open_set_pms);
                            }
                            str3 = SP.STAREMAPP_SHORTCUT_GUIDE_IS_FIRST_VIVO;
                            i = 0;
                        }
                        iCheckOp = 1;
                    }
                }
            }
            str3 = null;
            i = 0;
        }
        if (iCheckOp == 1 && z2) {
            if (PdrUtil.isEquals(iApp.forceShortCut(), "tipOnce")) {
                if (sharedPreferences.getBoolean(str2 + str3, true)) {
                    sharedPreferences.edit().putBoolean(str2 + str3, false).commit();
                    if (z) {
                        showShortCutDialog(iApp, iApp.getActivity(), str2, sharedPreferences, i, string);
                        return iCheckOp;
                    }
                    int i2 = i;
                    String str5 = string;
                    if (LoadAppUtils.startSecuritySettingPage(context)) {
                        ToastCompat.makeText(context, (CharSequence) str5, 1).show();
                        PermissionGuideWindow.getInstance(context).showWindow(str5, i2);
                        return iCheckOp;
                    }
                }
            } else {
                int i3 = i;
                String str6 = string;
                if (z) {
                    showShortCutDialog(iApp, iApp.getActivity(), str2, sharedPreferences, i3, str6);
                    return iCheckOp;
                }
                if (LoadAppUtils.startSecuritySettingPage(context)) {
                    ToastCompat.makeText(context, (CharSequence) str6, 1).show();
                    PermissionGuideWindow.getInstance(context).showWindow(str6, i3);
                }
            }
        }
        return iCheckOp;
    }

    public static int checkPermission(Context context, String str) {
        String str2 = Build.BRAND;
        if (str2.equalsIgnoreCase(MobilePhoneModel.MEIZU)) {
            return !isFlymeShortcutallowAllow(context, ShortCutUtil.getHeadShortCutIntent(str)) ? 1 : 3;
        }
        if (str2.equalsIgnoreCase(MobilePhoneModel.XIAOMI)) {
            int iCheckOp = checkOp(context);
            if (iCheckOp == 0 || iCheckOp == 1) {
                return iCheckOp;
            }
            if (iCheckOp == 3 || iCheckOp == 4) {
                return 2;
            }
        } else if (Build.MANUFACTURER.equalsIgnoreCase(MobilePhoneModel.HUAWEI)) {
            return !isEmuiShortcutallowAllow() ? 1 : 3;
        }
        return 4;
    }

    public static boolean checkShortcutOps(IApp iApp, Activity activity, String str, String str2) {
        SharedPreferences orCreateBundle = SP.getOrCreateBundle(activity, "pdr");
        if (getCheckShortcutOps(activity) != 1) {
            return true;
        }
        showShortCutOpsDialog(iApp, activity, str, orCreateBundle);
        return false;
    }

    public static int getCheckShortcutOps(Activity activity) {
        if (-1 != getShotCutOpId()) {
            return checkOp(activity);
        }
        return 0;
    }

    private static int getFlymePermissionGranted(Context context, int i, Intent intent) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            Class<?> cls = Class.forName("meizu.security.IFlymePermissionService$Stub");
            Class<?> cls2 = Class.forName("android.os.ServiceManager");
            Object objInvoke = cls.getDeclaredMethod("asInterface", IBinder.class).invoke(cls, (IBinder) cls2.getDeclaredMethod("getService", String.class).invoke(cls2, "flyme_permission"));
            Class<?> cls3 = objInvoke.getClass();
            Class<?> cls4 = Integer.TYPE;
            Method method = cls3.getMethod("noteIntentOperation", cls4, cls4, String.class, Intent.class);
            int callingPid = Binder.getCallingPid();
            return ((Integer) method.invoke(objInvoke, Integer.valueOf(i), Integer.valueOf(callingPid), context.getPackageName(), intent)).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static int getFlymeShortcutPid() throws NoSuchFieldException, ClassNotFoundException, SecurityException {
        try {
            Class<?> cls = Class.forName("meizu.security.FlymePermissionManager");
            Field declaredField = cls.getDeclaredField("OP_SEND_SHORTCUT_BROADCAST");
            declaredField.setAccessible(true);
            return ((Integer) declaredField.get(cls)).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int getShotCutOpId() {
        int i;
        if (!Build.BRAND.equalsIgnoreCase(MobilePhoneModel.XIAOMI)) {
            return (Build.MANUFACTURER.equalsIgnoreCase(MobilePhoneModel.HUAWEI) && ((i = Build.VERSION.SDK_INT) == 23 || i == 24)) ? 16777216 : -1;
        }
        switch (Build.VERSION.SDK_INT) {
            case 21:
            case 22:
                return mXiaoMiCode21OPSIDs.get(OP_INSTALL_SHORTCUT).intValue();
            case 23:
                return mXiaoMiCode23OPSIDs.get(OP_INSTALL_SHORTCUT).intValue();
            default:
                return -1;
        }
    }

    public static boolean isEmuiShortcutallowAllow() throws ClassNotFoundException {
        int shotCutOpId;
        try {
            shotCutOpId = getShotCutOpId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (-1 == shotCutOpId) {
            return true;
        }
        Class<?> cls = Class.forName("com.huawei.hsm.permission.StubController");
        Class<?> cls2 = Integer.TYPE;
        return ((Integer) cls.getDeclaredMethod("holdForGetPermissionSelection", cls2, cls2, cls2, String.class).invoke(cls, Integer.valueOf(shotCutOpId), Integer.valueOf(Binder.getCallingUid()), Integer.valueOf(Binder.getCallingPid()), null)).intValue() != 2;
    }

    public static boolean isFlymeShortcutallowAllow(Context context, Intent intent) throws NoSuchFieldException, ClassNotFoundException, SecurityException {
        int flymeShortcutPid = getFlymeShortcutPid();
        return flymeShortcutPid == -1 || getFlymePermissionGranted(context, flymeShortcutPid, intent) != 1;
    }

    public static void showShortCutDialog(IApp iApp, final Activity activity, final String str, final SharedPreferences sharedPreferences, final int i, final String str2) {
        DCloudAlertDialog dCloudAlertDialogInitDialogTheme = DialogUtil.initDialogTheme(activity, true);
        String string = activity.getString(R.string.dcloud_short_cut_create_error);
        dCloudAlertDialogInitDialogTheme.setTitle(activity.getString(R.string.dcloud_short_cut_set_pms));
        dCloudAlertDialogInitDialogTheme.setMessage(string);
        dCloudAlertDialogInitDialogTheme.setButton(-1, activity.getString(R.string.dcloud_short_cut_set_up), new DialogInterface.OnClickListener() { // from class: io.dcloud.common.util.AppPermissionUtil.7
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                if (LoadAppUtils.startSecuritySettingPage(activity)) {
                    sharedPreferences.edit().putBoolean(str + SP.IS_CREATE_SHORTCUT, true).commit();
                    ToastCompat.makeText((Context) activity, (CharSequence) str2, 1).show();
                    PermissionGuideWindow.getInstance(activity).showWindow(str2, i);
                }
            }
        });
        dCloudAlertDialogInitDialogTheme.setButton(-2, activity.getString(R.string.dcloud_short_cut_abandon_install), new DialogInterface.OnClickListener() { // from class: io.dcloud.common.util.AppPermissionUtil.8
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                dialogInterface.dismiss();
            }
        });
        dCloudAlertDialogInitDialogTheme.setCanceledOnTouchOutside(false);
        dCloudAlertDialogInitDialogTheme.show();
        Window window = dCloudAlertDialogInitDialogTheme.getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.x = 0;
            attributes.y = 0;
            window.setGravity(80);
            window.setLayout((int) (activity.getResources().getDisplayMetrics().widthPixels * 0.9d), attributes.height);
        }
    }

    public static void showShortCutOpsDialog(final IApp iApp, final Activity activity, final String str, final SharedPreferences sharedPreferences) {
        DCloudAlertDialog dCloudAlertDialogInitDialogTheme = DialogUtil.initDialogTheme(activity, true);
        String string = activity.getString(R.string.dcloud_short_cut_create_error_tips2);
        if (Build.BRAND.equalsIgnoreCase(MobilePhoneModel.MEIZU)) {
            string = activity.getString(R.string.dcloud_short_cut_create_error_tips3);
        } else if (Build.MANUFACTURER.equalsIgnoreCase(MobilePhoneModel.HUAWEI)) {
            string = activity.getString(R.string.dcloud_short_cut_create_error_tips4);
        }
        dCloudAlertDialogInitDialogTheme.setTitle(R.string.dcloud_short_cut_set_pms);
        dCloudAlertDialogInitDialogTheme.setMessage(string);
        dCloudAlertDialogInitDialogTheme.setButton(-1, activity.getString(R.string.dcloud_short_cut_goto_pms), new DialogInterface.OnClickListener() { // from class: io.dcloud.common.util.AppPermissionUtil.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:" + activity.getPackageName()));
                sharedPreferences.edit().putBoolean(str + SP.IS_CREATE_SHORTCUT, true).commit();
                activity.startActivity(intent);
            }
        });
        dCloudAlertDialogInitDialogTheme.setButton(-2, activity.getString(R.string.dcloud_short_cut_not_install), new DialogInterface.OnClickListener() { // from class: io.dcloud.common.util.AppPermissionUtil.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                IApp iApp2 = iApp;
                AppPermissionUtil.againShortcutOpsDialog(iApp2, activity, str, iApp2.obtainAppName());
            }
        });
        dCloudAlertDialogInitDialogTheme.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: io.dcloud.common.util.AppPermissionUtil.3
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialogInterface) {
                IApp iApp2 = iApp;
                AppPermissionUtil.againShortcutOpsDialog(iApp2, activity, str, iApp2.obtainAppName());
            }
        });
        dCloudAlertDialogInitDialogTheme.setCanceledOnTouchOutside(false);
        dCloudAlertDialogInitDialogTheme.show();
    }

    public static int checkOp(Context context) {
        Object systemService = context.getSystemService("appops");
        Class<?> cls = systemService.getClass();
        try {
            Integer num = (Integer) cls.getDeclaredField("OP_INSTALL_SHORTCUT").get(cls);
            num.intValue();
            Class<?> cls2 = Integer.TYPE;
            return ((Integer) cls.getDeclaredMethod("checkOp", cls2, cls2, String.class).invoke(systemService, num, Integer.valueOf(Binder.getCallingUid()), context.getPackageName())).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
