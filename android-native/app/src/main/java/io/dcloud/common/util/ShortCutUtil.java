package io.dcloud.common.util;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import androidx.appcompat.widget.SearchView$$ExternalSyntheticApiModelOutline0;
import androidx.core.graphics.ColorKt$$ExternalSyntheticApiModelOutline0;
import com.dcloud.android.widget.dialog.DCloudAlertDialog;
import com.dcloud.android.widget.toast.ToastCompat;
import com.taobao.weex.WXEnvironment;
import io.dcloud.PdrR;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.MessageHandler;
import io.dcloud.common.adapter.util.MobilePhoneModel;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.IntentConst;
import io.dcloud.common.ui.PermissionGuideWindow;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class ShortCutUtil {
    public static final String NOPERMISSIONS = "nopermissions";
    private static final String SHORTCUT_SRC_STREAM_APPS = "short_cut_src_stream_apps";
    public static final String SHORT_CUT_EXISTING = "short_cut_existing";
    public static final String SHORT_CUT_NONE = "short_cut_none";
    public static final String TAG = "ShortCutUtil";
    public static final String UNKNOWN = "unknown";
    public static String activityNameSDK = null;
    public static HashMap<String, String> extraProSDK = null;
    public static boolean mAutoCreateShortcut = true;
    static TypeRunnable mRunnable;
    private boolean isChekShortCut;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    interface TypeRunnable extends Runnable {
        String getType();

        void setType(String str);
    }

    private static void addShortCutSrc(Context context, Intent intent, String str) {
        intent.putExtra(IntentConst.SHORT_CUT_SRC, str.hashCode() + "_" + context.getPackageName());
    }

    private static void checkShortcutPermission(IApp iApp, String str, SharedPreferences sharedPreferences, String str2) {
        if (!PdrUtil.isEquals(iApp.forceShortCut(), "tipOnce")) {
            AppPermissionUtil.showShortCutOpsDialog(iApp, iApp.getActivity(), str, sharedPreferences);
            return;
        }
        if (sharedPreferences.getBoolean(str + str2, true)) {
            sharedPreferences.edit().putBoolean(str + str2, false).commit();
            AppPermissionUtil.showShortCutOpsDialog(iApp, iApp.getActivity(), str, sharedPreferences);
        }
    }

    public static void createShortcut(IApp iApp, String str, Bitmap bitmap, boolean z) {
        Bitmap bitmapDecodeResource;
        String strSubstring = str;
        Logger.e("StreamSDK", "come in createShortcut");
        Logger.e("IAN", "createShortcut: BaseInfo.mAutoCreateShortcut" + mAutoCreateShortcut);
        Logger.e("IAN", "createShortcut: ShortCutUtil.activityNameSDK" + activityNameSDK);
        if (iApp == null || TextUtils.isEmpty(strSubstring) || iApp.startFromShortCut() || iApp.forceShortCut().equals("none")) {
            if (iApp != null) {
                Logger.e("IAN", "createShortcut: filePath==" + strSubstring + "app.startFromShortCut()==" + iApp.startFromShortCut() + "app.forceShortCut().equals(none)==" + iApp.forceShortCut().equals("none"));
                return;
            }
            return;
        }
        if (PdrUtil.isEquals(iApp.forceShortCut(), "none")) {
            return;
        }
        Logger.e("StreamSDK", "come out return 1");
        Intent intentObtainWebAppIntent = iApp.obtainWebAppIntent();
        boolean z2 = intentObtainWebAppIntent != null && intentObtainWebAppIntent.getIntExtra(IntentConst.START_FROM, -1) == 5;
        Logger.e("StreamSDK", "isMyRuning" + z2);
        if (z2) {
            return;
        }
        Activity activity = iApp.getActivity();
        String strObtainAppName = iApp.obtainAppName();
        String strObtainAppId = iApp.obtainAppId();
        if (bitmap == null) {
            if (!TextUtils.isEmpty(strSubstring) && strSubstring.startsWith(DeviceInfo.FILE_PROTOCOL)) {
                strSubstring = strSubstring.substring(7);
            }
            bitmapDecodeResource = BitmapFactory.decodeFile(strSubstring);
        } else {
            bitmapDecodeResource = bitmap;
        }
        if (bitmapDecodeResource == null) {
            bitmapDecodeResource = BitmapFactory.decodeResource(activity.getResources(), PdrR.DRAWABLE_ICON);
        }
        Bitmap bitmap2 = bitmapDecodeResource;
        Intent intentObtainWebAppIntent2 = iApp.obtainWebAppIntent();
        String stringExtra = intentObtainWebAppIntent2 != null ? intentObtainWebAppIntent2.getStringExtra(IntentConst.WEBAPP_SHORT_CUT_CLASS_NAME) : "";
        if (hasShortcut(activity, strObtainAppName)) {
            Logger.e("StreamSDK", "ShortCutUtil.hasShortcut(context, name)");
            return;
        }
        SharedPreferences orCreateBundle = SP.getOrCreateBundle(activity, "pdr");
        boolean z3 = orCreateBundle.getBoolean(strObtainAppId + SP.STAREMAPP_FIRST_SHORT_CUT, true);
        if (ShortcutCreateUtil.isDisableShort(iApp.getActivity())) {
            handleDisableShort(iApp.getActivity(), strObtainAppId, z3, orCreateBundle);
            orCreateBundle.edit().putBoolean(strObtainAppId + SP.STAREMAPP_FIRST_SHORT_CUT, false).commit();
            return;
        }
        if (MobilePhoneModel.isSpecialPhone(activity) && showSettingsDialog(iApp, strSubstring, bitmap)) {
            return;
        }
        String string = orCreateBundle.getString(AbsoluteConst.TEST_RUN + strObtainAppId, null);
        if (!TextUtils.isEmpty(string)) {
            string.equals("__am=t");
        }
        boolean z4 = orCreateBundle.getBoolean(strObtainAppId + SP.K_CREATED_SHORTCUT, false);
        if (Build.BRAND.equalsIgnoreCase(MobilePhoneModel.MEIZU) && !AppPermissionUtil.isFlymeShortcutallowAllow(activity, getHeadShortCutIntent(strObtainAppName))) {
            checkShortcutPermission(iApp, strObtainAppId, orCreateBundle, SP.STAREMAPP_SHORTCUT_GUIDE_IS_FIRST_FLYME);
            return;
        }
        if (Build.MANUFACTURER.equalsIgnoreCase(MobilePhoneModel.HUAWEI) && !AppPermissionUtil.isEmuiShortcutallowAllow()) {
            checkShortcutPermission(iApp, strObtainAppId, orCreateBundle, SP.STAREMAPP_SHORTCUT_GUIDE_IS_FIRST_EMUI);
            return;
        }
        if (!ShortcutCreateUtil.isDuplicateLauncher(activity) && iApp.forceShortCut().equals("auto") && z4) {
            return;
        }
        if (!z4) {
            SP.getOrCreateBundle(activity, "streamapp_create_shortcut").getBoolean("is_create_shortcut" + strObtainAppId, false);
        }
        if (orCreateBundle.getBoolean(SP.K_SHORT_CUT_ONE_TIPS, true)) {
            orCreateBundle.edit().putBoolean(SP.K_SHORT_CUT_ONE_TIPS, false).commit();
        }
        orCreateBundle.edit().putBoolean(strObtainAppId + SP.STAREMAPP_FIRST_SHORT_CUT, false).commit();
        if (createShortcutToDeskTop(activity, strObtainAppId, strObtainAppName, bitmap2, stringExtra, null, false)) {
            Logger.e("StreamSDK", "come into createShortcutToDeskTop and return ture already");
            if (z) {
                if (PdrUtil.isEquals(iApp.forceShortCut(), "tipOnce")) {
                    if (orCreateBundle.getBoolean(strObtainAppId + SP.STAREMAPP_SHORTCUT_TIP_IS_FIRST, true)) {
                        orCreateBundle.edit().putBoolean(strObtainAppId + SP.STAREMAPP_SHORTCUT_TIP_IS_FIRST, false).commit();
                        if (showToast(iApp, activity, strObtainAppId, orCreateBundle)) {
                            return;
                        }
                    }
                } else if (showToast(iApp, activity, strObtainAppId, orCreateBundle)) {
                    return;
                }
            } else if (isHasShortCut(iApp, 1000L, "auto")) {
                return;
            } else {
                showCreateShortCutToast(iApp);
            }
        }
        orCreateBundle.edit().putString(strObtainAppId + SP.K_CREATE_SHORTCUT_NAME, strObtainAppName).commit();
        orCreateBundle.edit().putBoolean(strObtainAppId + SP.K_CREATED_SHORTCUT, true).commit();
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x01ac, code lost:
    
        if (showToast(r15, r4, r5, r0) != false) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x01b3, code lost:
    
        if (showToast(r15, r4, r5, r0) != false) goto L65;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int createShortcutGuide(io.dcloud.common.DHInterface.IApp r15, java.lang.String r16, android.graphics.Bitmap r17, boolean r18, boolean r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 497
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.util.ShortCutUtil.createShortcutGuide(io.dcloud.common.DHInterface.IApp, java.lang.String, android.graphics.Bitmap, boolean, boolean, boolean):int");
    }

    public static boolean createShortcutToDeskTop(Context context, String str, String str2, Bitmap bitmap, String str3, JSONObject jSONObject, boolean z) {
        return createShortcutToDeskTop(context, str, str2, bitmap, str3, jSONObject, z, false);
    }

    public static Intent getHeadShortCutIntent(String str) {
        Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        intent.putExtra("android.intent.extra.shortcut.NAME", str);
        intent.putExtra("duplicate", false);
        return intent;
    }

    public static Uri getUriFromLauncher(Context context) {
        StringBuilder sb = new StringBuilder("content://");
        String launcherPackageName = LauncherUtil.getLauncherPackageName(context);
        Logger.e("tag", "getUriFromLauncher: packageName" + launcherPackageName);
        if ("com.nd.android.pandahome2".equals(launcherPackageName)) {
            return Uri.parse("content://com.nd.android.launcher2.settings/com.nd.hilauncherdev/favorites?notify=true");
        }
        String authorityFromPermission = LauncherUtil.getAuthorityFromPermission(context, launcherPackageName + ".permission.READ_SETTINGS");
        StringBuilder sb2 = new StringBuilder("getUriFromLauncher: LauncherUtil.getAuthorityFromPermissionwithpackagename(");
        sb2.append(authorityFromPermission);
        Logger.e("TAG", sb2.toString());
        if (TextUtils.isEmpty(authorityFromPermission)) {
            authorityFromPermission = LauncherUtil.getAuthorityFromPermissionDefault(context);
            Logger.e("TAG", "getUriFromLauncher: LauncherUtil.getAuthorityFromPermissionDefault(" + authorityFromPermission);
        }
        if (TextUtils.isEmpty(authorityFromPermission)) {
            if (Build.MANUFACTURER.equalsIgnoreCase(MobilePhoneModel.QiKU)) {
                return Uri.parse("content://com.yulong.android.launcher3.compound/compoundworkspace?notify=false");
            }
            return null;
        }
        sb.append(authorityFromPermission);
        if (Build.MANUFACTURER.equalsIgnoreCase(MobilePhoneModel.OPPO)) {
            sb.append("/singledesktopitems?notify=true");
        } else {
            sb.append("/favorites?notify=true");
        }
        return Uri.parse(sb.toString());
    }

    public static void handleDisableShort(Activity activity, String str, boolean z, SharedPreferences sharedPreferences) {
    }

    public static boolean hasShortcut(Context context, String str) {
        return SHORT_CUT_EXISTING.equals(requestShortCut(context, str));
    }

    public static boolean isHasShortCut(final IApp iApp, long j, String str) {
        if (!Build.BRAND.equalsIgnoreCase(MobilePhoneModel.XIAOMI)) {
            return false;
        }
        TypeRunnable typeRunnable = mRunnable;
        if (typeRunnable != null) {
            if (typeRunnable.getType().equals("back") && str.equals(mRunnable.getType())) {
                return true;
            }
            removeRunHandler();
        }
        BaseInfo.isPostChcekShortCut = true;
        TypeRunnable typeRunnable2 = new TypeRunnable() { // from class: io.dcloud.common.util.ShortCutUtil.3
            String type;

            @Override // io.dcloud.common.util.ShortCutUtil.TypeRunnable
            public String getType() {
                return this.type;
            }

            @Override // java.lang.Runnable
            public void run() {
                BaseInfo.isPostChcekShortCut = false;
                if (!iApp.getActivity().isFinishing()) {
                    SharedPreferences orCreateBundle = SP.getOrCreateBundle(iApp.getActivity(), "pdr");
                    if (ShortCutUtil.hasShortcut(iApp.getActivity(), iApp.obtainAppName())) {
                        orCreateBundle.edit().putString(iApp.obtainAppId() + SP.K_CREATE_SHORTCUT_NAME, iApp.obtainAppName()).commit();
                        orCreateBundle.edit().putBoolean(iApp.obtainAppId() + SP.K_CREATED_SHORTCUT, true).commit();
                        ShortCutUtil.showCreateShortCutToast(iApp);
                    } else if (AppPermissionUtil.getCheckShortcutOps(iApp.getActivity()) == 0) {
                        ShortCutUtil.createShortcutToDeskTop(iApp, true);
                        ShortCutUtil.showCreateShortCutToast(iApp);
                    } else {
                        IApp iApp2 = iApp;
                        AppPermissionUtil.showShortCutOpsDialog(iApp2, iApp2.getActivity(), iApp.obtainAppId(), orCreateBundle);
                    }
                }
                ShortCutUtil.mRunnable = null;
            }

            @Override // io.dcloud.common.util.ShortCutUtil.TypeRunnable
            public void setType(String str2) {
                this.type = str2;
            }
        };
        mRunnable = typeRunnable2;
        typeRunnable2.setType(str);
        MessageHandler.postDelayed(mRunnable, j);
        return true;
    }

    public static boolean isOpsCreateShortcut(Context context, String str) {
        SharedPreferences orCreateBundle = SP.getOrCreateBundle(context, "pdr");
        boolean z = orCreateBundle.getBoolean(str + SP.IS_CREATE_SHORTCUT, false);
        if (z) {
            orCreateBundle.edit().remove(str + SP.IS_CREATE_SHORTCUT).commit();
        }
        return z;
    }

    public static boolean isRunShortCut(Context context, String str) {
        SharedPreferences orCreateBundle = SP.getOrCreateBundle(context, "pdr");
        if (!orCreateBundle.getString(SP.RECORD_RUN_SHORT_CUT, "").equals(str)) {
            return false;
        }
        orCreateBundle.edit().remove(SP.RECORD_RUN_SHORT_CUT).commit();
        return true;
    }

    public static void onResumeCreateShortcut(IApp iApp) {
        if (AppPermissionUtil.getCheckShortcutOps(iApp.getActivity()) != 1) {
            createShortcut(iApp, null, null, false);
            return;
        }
        AppPermissionUtil.checkShortcutOps(iApp, iApp.getActivity(), iApp.obtainAppId(), iApp.obtainAppName());
        Intent intentObtainWebAppIntent = iApp.obtainWebAppIntent();
        createShortcutToDeskTop(iApp.getActivity(), iApp.obtainAppId(), iApp.obtainAppName(), null, intentObtainWebAppIntent != null ? intentObtainWebAppIntent.getStringExtra(IntentConst.WEBAPP_SHORT_CUT_CLASS_NAME) : "", null, false);
    }

    public static void onResumeCreateShortcutGuide(IApp iApp) {
        PermissionGuideWindow.getInstance(iApp.getActivity()).dismissWindow();
        String str = Build.BRAND;
        if (str.equalsIgnoreCase(MobilePhoneModel.GOOGLE)) {
            str = Build.MANUFACTURER;
        }
        if (MobilePhoneModel.SMARTISAN.equals(str)) {
            createShortcutGuide(iApp, null, null, false, false, false);
            return;
        }
        MobilePhoneModel.XIAOMI.equals(str);
        MobilePhoneModel.MEIZU.equals(str);
        Build.MANUFACTURER.equalsIgnoreCase(MobilePhoneModel.HUAWEI);
        createShortcutGuide(iApp, null, null, false, true, true);
    }

    public static void removeRunHandler() {
        if (mRunnable != null) {
            BaseInfo.isPostChcekShortCut = false;
            MessageHandler.removeCallbacks(mRunnable);
        }
    }

    public static boolean removeShortcutFromDeskTop(Context context, String str, String str2, String str3, JSONObject jSONObject) {
        Intent intent = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        intent.putExtra("android.intent.extra.shortcut.NAME", str2);
        intent.putExtra("duplicate", false);
        Intent intent2 = new Intent();
        if (TextUtils.isEmpty(str3)) {
            intent2 = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        } else {
            if (PdrUtil.isEmpty(activityNameSDK)) {
                intent2.setClassName(context.getPackageName(), str3);
            } else {
                intent2.putExtra(IntentConst.WEBAPP_ACTIVITY_SHORTCUTACTIVITY, activityNameSDK);
                intent2.setClassName(context.getPackageName(), activityNameSDK);
            }
            intent2.setAction("android.intent.action.MAIN");
            intent2.addCategory("android.intent.category.LAUNCHER");
        }
        if (jSONObject != null) {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                try {
                    String next = itKeys.next();
                    intent2.putExtra(next, jSONObject.getString(next));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        addShortCutSrc(context, intent2, str2);
        intent2.putExtra(IntentConst.SHORT_CUT_APPID, str);
        intent2.putExtra(IntentConst.FROM_SHORT_CUT_STRAT, true);
        intent2.setFlags(268435456);
        intent2.setData(Uri.parse("https://m3w.cn/s/" + str));
        intent.putExtra("android.intent.extra.shortcut.INTENT", intent2);
        context.sendBroadcast(intent);
        return true;
    }

    public static String requestShortCut(Context context, String str) {
        String str2 = "unknown";
        if (Build.VERSION.SDK_INT >= 25) {
            List pinnedShortcuts = ColorKt$$ExternalSyntheticApiModelOutline0.m(context.getSystemService(ColorKt$$ExternalSyntheticApiModelOutline0.m45m())).getPinnedShortcuts();
            String str3 = "id_" + str.hashCode();
            for (int i = 0; i < pinnedShortcuts.size(); i++) {
                if (SearchView$$ExternalSyntheticApiModelOutline0.m6m(pinnedShortcuts.get(i)).getId().equals(str3)) {
                    return SHORT_CUT_EXISTING;
                }
            }
        } else {
            ContentResolver contentResolver = context.getContentResolver();
            Uri uriFromLauncher = getUriFromLauncher(context);
            if (uriFromLauncher == null) {
                String shortCutUri = getShortCutUri(context);
                if (!TextUtils.isEmpty(shortCutUri)) {
                    uriFromLauncher = Uri.parse(shortCutUri);
                    Logger.es("shortcututil", context.getString(R.string.dcloud_short_cut_err1) + uriFromLauncher);
                }
            }
            Uri uri = uriFromLauncher;
            Logger.e("shortcututil", "requestShortCut: uri===" + uri);
            if (uri != null) {
                try {
                    Cursor cursorQuery = contentResolver.query(uri, new String[]{AbsoluteConst.JSON_KEY_TITLE, "intent"}, "title=? ", new String[]{str}, null);
                    if (cursorQuery == null || cursorQuery.getCount() <= 0) {
                        str2 = SHORT_CUT_NONE;
                    } else {
                        Logger.e("shortcututil", "c != null && c.getCount() > 0");
                        while (cursorQuery.moveToNext()) {
                            String string = cursorQuery.getString(cursorQuery.getColumnIndex("intent"));
                            if (TextUtils.isEmpty(string)) {
                                str2 = SHORT_CUT_NONE;
                            } else {
                                Logger.e("shortcututil", "intent=====" + string);
                                if (string.contains(IntentConst.SHORT_CUT_APPID)) {
                                    if (!BaseInfo.isBase(context)) {
                                        if (string.contains("io.dcloud.appstream.StreamAppMainActivity")) {
                                            if (string.contains(str.hashCode() + "_" + context.getPackageName())) {
                                            }
                                        }
                                    }
                                    str2 = SHORT_CUT_EXISTING;
                                }
                            }
                        }
                    }
                    if (cursorQuery != null && !cursorQuery.isClosed()) {
                        cursorQuery.close();
                    }
                    return str2;
                } catch (Exception e) {
                    if (e.getMessage() != null && e.getMessage().contains("READ_SETTINGS")) {
                        str2 = NOPERMISSIONS;
                    }
                    Logger.es("shortcututil", e.getMessage() + "URI==" + uri);
                    e.printStackTrace();
                }
            }
        }
        return str2;
    }

    public static String requestShortCutForCommit(Context context, String str) {
        return requestShortCut(context, str);
    }

    public static void showCreateShortCutToast(IApp iApp) {
        String str = StringUtil.format(iApp.getActivity().getString(R.string.dcloud_short_cut_created), iApp.obtainAppName());
        if (!iApp.forceShortCut().equals(AbsoluteConst.INSTALL_OPTIONS_FORCE) || ShortcutCreateUtil.isDuplicateLauncher(iApp.getActivity())) {
            if (ShortcutCreateUtil.needToast(iApp.getActivity())) {
                ToastCompat.makeText(iApp.getActivity().getApplicationContext(), (CharSequence) str, 1).show();
                return;
            }
            return;
        }
        String str2 = "“" + iApp.obtainAppName() + iApp.getActivity().getString(R.string.dcloud_short_cut_created_tip);
        if (ShortcutCreateUtil.needToast(iApp.getActivity())) {
            ToastCompat.makeText(iApp.getActivity().getApplicationContext(), (CharSequence) str2, 1).show();
        }
    }

    public static boolean showSettingsDialog(final IApp iApp, final String str, final Bitmap bitmap) {
        String str2;
        String string;
        String string2;
        final SharedPreferences orCreateBundle = SP.getOrCreateBundle(iApp.getActivity(), "pdr");
        if (!orCreateBundle.getBoolean(SP.K_SHORT_CUT_ONE_TIPS, true) && !Build.BRAND.equals(MobilePhoneModel.SMARTISAN)) {
            return false;
        }
        if (orCreateBundle.getBoolean(SP.K_SHORT_CUT_ONE_TIPS, true)) {
            orCreateBundle.edit().putBoolean(SP.K_SHORT_CUT_ONE_TIPS, false).commit();
        }
        String string3 = iApp.getActivity().getString(R.string.dcloud_short_cut_goto_pms);
        String string4 = iApp.getActivity().getString(R.string.dcloud_short_cut_it_set);
        String str3 = Build.BRAND;
        if (str3.equalsIgnoreCase(MobilePhoneModel.QiKU)) {
            string2 = iApp.getActivity().getString(R.string.dcloud_short_cut_qiku1);
            string = iApp.getActivity().getString(R.string.dcloud_short_cut_goto_run);
        } else {
            if (str3.equalsIgnoreCase(MobilePhoneModel.VIVO)) {
                if (!LoadAppUtils.isAppLoad(iApp.getActivity(), "com.iqoo.secure")) {
                    return false;
                }
                str2 = iApp.getActivity().getString(R.string.dcloud_short_cut_vivo1) + "App" + iApp.getActivity().getString(R.string.dcloud_short_cut_vivo2);
            } else if (str3.equalsIgnoreCase(MobilePhoneModel.SMARTISAN)) {
                string = string3;
                string2 = iApp.getActivity().getString(R.string.dcloud_short_cut_chuizi1);
                string4 = iApp.getActivity().getString(R.string.dcloud_short_cut_not_install);
            } else {
                str2 = "";
            }
            String str4 = str2;
            string = string3;
            string2 = str4;
        }
        DCloudAlertDialog dCloudAlertDialogInitDialogTheme = DialogUtil.initDialogTheme(iApp.getActivity(), true);
        dCloudAlertDialogInitDialogTheme.setMessage(string2);
        dCloudAlertDialogInitDialogTheme.setTitle(iApp.getActivity().getString(R.string.dcloud_short_cut_tips));
        dCloudAlertDialogInitDialogTheme.setButton(-1, string, new DialogInterface.OnClickListener() { // from class: io.dcloud.common.util.ShortCutUtil.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent;
                orCreateBundle.edit().putString(SP.RECORD_RUN_SHORT_CUT, iApp.obtainAppId()).commit();
                String str5 = Build.BRAND;
                if (str5.equalsIgnoreCase(MobilePhoneModel.QiKU) || str5.equalsIgnoreCase(MobilePhoneModel.SMARTISAN)) {
                    if (str5.equalsIgnoreCase(MobilePhoneModel.QiKU)) {
                        intent = new Intent();
                        intent.setClassName("com.yulong.android.launcher3", "com.yulong.android.launcher3.LauncherSettingsActivity");
                    } else {
                        intent = new Intent("android.settings.SETTINGS");
                    }
                    iApp.getActivity().startActivity(intent);
                    return;
                }
                if (str5.equalsIgnoreCase(MobilePhoneModel.VIVO)) {
                    PackageManager packageManager = iApp.getActivity().getPackageManager();
                    new Intent();
                    Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage("com.iqoo.secure");
                    launchIntentForPackage.setFlags(337641472);
                    iApp.getActivity().startActivity(launchIntentForPackage);
                }
            }
        });
        dCloudAlertDialogInitDialogTheme.setButton(-2, string4, new DialogInterface.OnClickListener() { // from class: io.dcloud.common.util.ShortCutUtil.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Build.BRAND.equalsIgnoreCase(MobilePhoneModel.SMARTISAN)) {
                    return;
                }
                ShortCutUtil.createShortcut(iApp, str, bitmap, false);
            }
        });
        dCloudAlertDialogInitDialogTheme.setCanceledOnTouchOutside(false);
        dCloudAlertDialogInitDialogTheme.show();
        return true;
    }

    private static boolean showToast(IApp iApp, Activity activity, String str, SharedPreferences sharedPreferences) {
        if ("12214060304".equals(BaseInfo.sChannel) && "com.aliyun.homeshell".equals(LauncherUtil.getLauncherPackageName(activity))) {
            if (sharedPreferences.getBoolean(str + SP.STAREMAPP_ALIYUN_SHORT_CUT_IS_FIRST_CREATED, true)) {
                showCreateShortCutToast(iApp);
                sharedPreferences.edit().putBoolean(str + SP.STAREMAPP_ALIYUN_SHORT_CUT_IS_FIRST_CREATED, false).commit();
            }
        } else {
            showCreateShortCutToast(iApp);
        }
        return false;
    }

    public static void updateShortcutFromDeskTop(Activity activity, String str, String str2, Bitmap bitmap, String str3) {
        removeShortcutFromDeskTop(activity, str, str2, str3, null);
        createShortcutToDeskTop(activity, str, str2, bitmap, str3, null, false);
    }

    public static String requestShortCutPermissionVivo(Context context, String str) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uriFromLauncher = getUriFromLauncher(context);
        if (uriFromLauncher == null) {
            String shortCutUri = getShortCutUri(context);
            if (!TextUtils.isEmpty(shortCutUri)) {
                uriFromLauncher = Uri.parse(shortCutUri);
            }
        }
        Logger.e("shortcututil", "requestShortCut: uri===" + uriFromLauncher);
        if (uriFromLauncher != null) {
            try {
                Cursor cursorQuery = contentResolver.query(uriFromLauncher, new String[]{AbsoluteConst.JSON_KEY_TITLE, "intent", "shortcutPermission"}, "title=? ", new String[]{str}, null);
                if (cursorQuery != null && cursorQuery.getCount() > 0) {
                    Logger.e("shortcututil", "c != null && c.getCount() > 0");
                    if (cursorQuery.moveToNext()) {
                        return cursorQuery.getString(cursorQuery.getColumnIndex("shortcutPermission"));
                    }
                }
                if (cursorQuery != null && !cursorQuery.isClosed()) {
                    cursorQuery.close();
                    return "-1";
                }
            } catch (Exception e) {
                Logger.es("shortcututil", e.getMessage() + "URI==" + uriFromLauncher);
                e.printStackTrace();
            }
        }
        return "-1";
    }

    public static boolean createShortcutToDeskTop(IApp iApp, boolean z) {
        String stringExtra;
        Intent intentObtainWebAppIntent = iApp.obtainWebAppIntent();
        if (intentObtainWebAppIntent == null) {
            stringExtra = "";
        } else {
            stringExtra = intentObtainWebAppIntent.getStringExtra(IntentConst.WEBAPP_SHORT_CUT_CLASS_NAME);
        }
        return createShortcutToDeskTop(iApp.getActivity(), iApp.obtainAppId(), iApp.obtainAppName(), null, stringExtra, null, false, z);
    }

    public static String getShortCutUri(Context context) {
        ActivityInfo activityInfo;
        if (context == null) {
            return "";
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        ResolveInfo resolveInfoResolveActivity = context.getPackageManager().resolveActivity(intent, 0);
        if (resolveInfoResolveActivity == null || (activityInfo = resolveInfoResolveActivity.activityInfo) == null || activityInfo.packageName.equals(WXEnvironment.OS)) {
            return "";
        }
        String str = resolveInfoResolveActivity.activityInfo.packageName;
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return "content://" + str + ".settings/favorites?notify=true";
    }

    public static boolean createShortcutToDeskTop(Context context, String str, String str2, Bitmap bitmap, String str3, JSONObject jSONObject, boolean z, boolean z2) {
        Intent intent = new Intent();
        if (TextUtils.isEmpty(str3)) {
            intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        } else {
            intent.setClassName(context.getPackageName(), str3);
            intent.setAction("android.intent.action.MAIN");
        }
        if (jSONObject != null) {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                try {
                    String next = itKeys.next();
                    intent.putExtra(next, jSONObject.getString(next));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        addShortCutSrc(context, intent, str2);
        intent.putExtra(IntentConst.SHORT_CUT_APPID, str);
        intent.putExtra(IntentConst.FROM_SHORT_CUT_STRAT, true);
        intent.setFlags(268435456);
        if (Build.VERSION.SDK_INT >= 26) {
            ShortcutManager shortcutManagerM = ColorKt$$ExternalSyntheticApiModelOutline0.m(context.getSystemService(IApp.ConfigProperty.CONFIG_SHORTCUT));
            if (shortcutManagerM != null && shortcutManagerM.isRequestPinShortcutSupported()) {
                SearchView$$ExternalSyntheticApiModelOutline0.m16m();
                ShortcutInfo shortcutInfoBuild = SearchView$$ExternalSyntheticApiModelOutline0.m5m(context, "id_" + str2.hashCode()).setIcon(Icon.createWithBitmap(bitmap)).setIntent(intent).setShortLabel(str2).build();
                shortcutManagerM.requestPinShortcut(shortcutInfoBuild, PendingIntent.getBroadcast(context, 0, shortcutManagerM.createShortcutResultIntent(shortcutInfoBuild), 0).getIntentSender());
            }
        } else {
            Intent headShortCutIntent = getHeadShortCutIntent(str2);
            headShortCutIntent.putExtra("android.intent.extra.shortcut.INTENT", intent);
            headShortCutIntent.putExtra("android.intent.extra.shortcut.ICON", bitmap);
            context.sendBroadcast(headShortCutIntent);
        }
        return true;
    }
}
