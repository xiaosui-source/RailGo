package io.dcloud.common.adapter.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;
import com.dcloud.android.widget.AbsoluteLayout;
import com.facebook.common.callercontext.ContextChain;
import com.taobao.weex.common.RenderTypes;
import com.taobao.weex.performance.WXInstanceApm;
import io.dcloud.application.DCLoudApplicationImpl;
import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.adapter.ui.AdaFrameView;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.FileUtil;
import io.dcloud.common.util.IOUtil;
import io.dcloud.common.util.LoadAppUtils;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import io.dcloud.feature.internal.sdk.SDK;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class PlatformUtil extends SP {
    private static final String ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    public static boolean APS_COVER = false;
    public static final byte ASSETS_RESOUCE = 0;
    private static final String EXTRA_SHORTCUT_DUPLICATE = "duplicate";
    public static final byte FILE_RESOUCE = 2;
    public static final byte SRC_RESOUCE = 1;
    private static final String URI_HTC_LAUNCER = "content://com.htc.launcher.settings/favorites?notify=true";
    private static final String URI_SAMSUNG_LAUNCER = "content://com.sec.android.app.twlauncher.settings/favorites?notify=true";
    private static int _SCREEN_CONTENT_HEIGHT;
    private static int _SCREEN_HEIGHT;
    private static int _SCREEN_STATUSBAR_HEIGHT;
    private static int _SCREEN_WIDTH;
    private static int[] _blackpixels;
    private static int[] _pixels;
    private static final String[] PROJECTION = {"_id", AbsoluteConst.JSON_KEY_TITLE, "iconResource"};
    private static int MAX_SPAN_IN_ONE_SCREEN = 16;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class APKInfo {
        public String mAppName;
        public Drawable mIcon;
    }

    private static int[] GET_BLACK_LINE(int i) {
        int[] iArr = _blackpixels;
        if (iArr == null || iArr.length != i) {
            _blackpixels = new int[i];
            int i2 = 0;
            while (true) {
                int[] iArr2 = _blackpixels;
                if (i2 >= iArr2.length) {
                    break;
                }
                iArr2[i2] = -16777216;
                i2++;
            }
        }
        return _blackpixels;
    }

    private static int[] GET_WIHTE_LINE(int i) {
        int[] iArr = _pixels;
        if (iArr == null || iArr.length != i) {
            _pixels = new int[i];
            int i2 = 0;
            while (true) {
                int[] iArr2 = _pixels;
                if (i2 >= iArr2.length) {
                    break;
                }
                iArr2[i2] = -1;
                i2++;
            }
        }
        return _pixels;
    }

    public static int MESURE_SCREEN_CONTENT_HEIGHT(Activity activity) {
        if (_SCREEN_CONTENT_HEIGHT == 0) {
            Rect rect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            _SCREEN_STATUSBAR_HEIGHT = rect.top;
            int iHeight = rect.height();
            _SCREEN_CONTENT_HEIGHT = iHeight;
            if (_SCREEN_STATUSBAR_HEIGHT < 0 || iHeight > SCREEN_HEIGHT(activity)) {
                _SCREEN_STATUSBAR_HEIGHT = 0;
                _SCREEN_CONTENT_HEIGHT = SCREEN_HEIGHT(activity);
            }
        }
        return _SCREEN_CONTENT_HEIGHT;
    }

    public static int MESURE_SCREEN_STATUSBAR_HEIGHT(Activity activity) {
        if (_SCREEN_STATUSBAR_HEIGHT == 0) {
            Rect rect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            _SCREEN_STATUSBAR_HEIGHT = rect.top;
            int iHeight = rect.height();
            _SCREEN_CONTENT_HEIGHT = iHeight;
            if (_SCREEN_STATUSBAR_HEIGHT < 0 || iHeight > SCREEN_HEIGHT(activity)) {
                _SCREEN_STATUSBAR_HEIGHT = 0;
                _SCREEN_CONTENT_HEIGHT = SCREEN_HEIGHT(activity);
            }
        }
        return _SCREEN_STATUSBAR_HEIGHT;
    }

    public static void RESET_H_W() {
        _SCREEN_WIDTH = 0;
        _SCREEN_HEIGHT = 0;
        _SCREEN_STATUSBAR_HEIGHT = 0;
        _SCREEN_CONTENT_HEIGHT = 0;
        _pixels = null;
    }

    public static int SCREEN_HEIGHT(Context context) {
        if (_SCREEN_HEIGHT == 0) {
            _SCREEN_HEIGHT = context.getResources().getDisplayMetrics().heightPixels;
        }
        return _SCREEN_HEIGHT;
    }

    public static int SCREEN_WIDTH(Context context) {
        if (_SCREEN_WIDTH == 0) {
            _SCREEN_WIDTH = context.getResources().getDisplayMetrics().widthPixels;
        }
        return _SCREEN_WIDTH;
    }

    public static Bitmap captureView(View view) {
        return captureView(view, true, true, null, "ARGB");
    }

    public static Bitmap captureWebViewContent(View view, boolean z, boolean z2, Rect rect, String str) {
        WebView webView;
        try {
            webView = (WebView) ((AdaFrameView) ((AbsoluteLayout) view).getFrameView().mWindowMgr.processEvent(IMgr.MgrType.WindowMgr, 18, null)).obtainWebView().obtainWindowView();
        } catch (Exception e) {
            e.printStackTrace();
            webView = null;
        }
        if (webView == null) {
            return null;
        }
        if (z2 && AndroidResources.sIMEAlive) {
            return null;
        }
        int iWidth = rect != null ? rect.width() : view.getMeasuredWidth();
        int iHeight = rect != null ? rect.height() : Math.round(webView.getContentHeight() * webView.getScale());
        if (iWidth == 0) {
            return null;
        }
        Bitmap.Config config = Bitmap.Config.RGB_565;
        if (str.equals("ARGB")) {
            config = Bitmap.Config.ARGB_4444;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iWidth, iHeight, config);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        if (rect != null) {
            canvas.translate(-rect.left, -rect.top);
        }
        webView.draw(canvas);
        if (!z || !isWhiteBitmap(bitmapCreateBitmap)) {
            return bitmapCreateBitmap;
        }
        bitmapCreateBitmap.recycle();
        return null;
    }

    public static boolean checkClass(String str) throws ClassNotFoundException {
        try {
            Class.forName(str);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean checkGTAndYoumeng() {
        return checkClass("io.dcloud.feature.apsGt.GTPushService") || checkClass("io.dcloud.feature.statistics.UmengStatisticsMgr");
    }

    public static boolean checkLauncherScreenSpace(Context context) throws IllegalArgumentException {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse(URI_SAMSUNG_LAUNCER), new String[]{"screen", "spanX", "spanY"}, null, null, null);
        if (cursorQuery == null) {
            return true;
        }
        int iQueryMaxLauncherScreenCount = queryMaxLauncherScreenCount(context);
        Logger.e("PlatformUtil", "Samsung Launcher: max screen num = " + iQueryMaxLauncherScreenCount);
        int columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("spanX");
        int columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("spanY");
        int i = iQueryMaxLauncherScreenCount * MAX_SPAN_IN_ONE_SCREEN;
        while (cursorQuery.moveToNext()) {
            try {
                try {
                    i -= cursorQuery.getInt(columnIndexOrThrow) * cursorQuery.getInt(columnIndexOrThrow2);
                } catch (Exception e) {
                    Logger.e("PlatformUtil", "Check Launcher space" + e);
                    cursorQuery.close();
                    i = 0;
                }
            } finally {
                cursorQuery.close();
            }
        }
        return i > 0;
    }

    public static void createShortut(Context context, String str, String str2, int i, boolean z) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setClassName(context, str2);
        Intent intent2 = new Intent(ACTION_INSTALL_SHORTCUT);
        intent2.putExtra("android.intent.extra.shortcut.INTENT", intent);
        intent2.putExtra("android.intent.extra.shortcut.NAME", str);
        intent2.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", Intent.ShortcutIconResource.fromContext(context, i));
        intent2.putExtra(EXTRA_SHORTCUT_DUPLICATE, z);
        context.sendBroadcast(intent2);
    }

    public static void destroyDrawingCache(View view) {
        view.destroyDrawingCache();
    }

    public static void disableWebViewMultiProcess(Context context) {
        Object objInvokeFieldValue;
        Object objInvokeFieldValue2;
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                if (Settings.Global.getInt(context.getContentResolver(), "webview_multiprocess", 0) != 1 || (objInvokeFieldValue = invokeFieldValue(Settings.Global.class.getName(), "sNameValueCache", Settings.Global.class.newInstance())) == null || (objInvokeFieldValue2 = invokeFieldValue(objInvokeFieldValue.getClass().getName(), "mValues", objInvokeFieldValue)) == null || !(objInvokeFieldValue2 instanceof HashMap)) {
                    return;
                }
                ((HashMap) objInvokeFieldValue2).put("webview_multiprocess", WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static APKInfo getApkFileInfo(Context context, String str) {
        String str2;
        PackageInfo packageArchiveInfo;
        ApplicationInfo applicationInfo;
        APKInfo aPKInfo = new APKInfo();
        Drawable drawable = null;
        try {
            packageArchiveInfo = context.getPackageManager().getPackageArchiveInfo(str, 1);
        } catch (Throwable th) {
            th = th;
            str2 = null;
        }
        if (packageArchiveInfo != null && (applicationInfo = packageArchiveInfo.applicationInfo) != null) {
            Class<?> cls = Class.forName("android.content.res.AssetManager");
            AssetManager assetManager = (AssetManager) cls.getConstructor(null).newInstance(null);
            cls.getDeclaredMethod("addAssetPath", String.class).invoke(assetManager, str);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            displayMetrics.setToDefaults();
            Resources resources = new Resources(assetManager, displayMetrics, context.getResources().getConfiguration());
            int i = applicationInfo.icon;
            if (i == 0) {
                str2 = null;
            } else {
                str2 = (String) resources.getText(applicationInfo.labelRes);
                try {
                    drawable = resources.getDrawable(i);
                } catch (Throwable th2) {
                    th = th2;
                    th.printStackTrace();
                    aPKInfo.mIcon = drawable;
                    aPKInfo.mAppName = str2;
                    return aPKInfo;
                }
            }
            aPKInfo.mIcon = drawable;
            aPKInfo.mAppName = str2;
            return aPKInfo;
        }
        return aPKInfo;
    }

    private static ActivityInfo getBestActivityInfo(List<ResolveInfo> list, LinkedList<String> linkedList) {
        LinkedList linkedList2 = new LinkedList();
        for (int i = 0; i < list.size(); i++) {
            ResolveInfo resolveInfo = list.get(i);
            if (linkedList.contains(resolveInfo.activityInfo.packageName)) {
                linkedList2.add(resolveInfo.activityInfo);
            }
        }
        int i2 = Integer.MAX_VALUE;
        ActivityInfo activityInfo = null;
        for (int i3 = 0; i3 < linkedList2.size(); i3++) {
            ActivityInfo activityInfo2 = (ActivityInfo) linkedList2.get(i3);
            int iIndexOf = linkedList.indexOf(activityInfo2.packageName);
            if (iIndexOf < i2) {
                activityInfo = activityInfo2;
                i2 = iIndexOf;
            }
        }
        return activityInfo;
    }

    public static byte[] getFileContent(String str, int i) throws IOException {
        InputStream inputStream = getInputStream(str, i);
        try {
            return inputStream != null ? IOUtil.getBytes(inputStream) : null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtil.close(inputStream);
        }
    }

    public static String getFileContent4S(String str) {
        return new String(getFileContent(str, 0)).replace(ContextChain.TAG_PRODUCT, "");
    }

    public static String getFilePathFromContentUri(Uri uri, ContentResolver contentResolver) {
        String[] strArr = {"_data"};
        Cursor cursorQuery = contentResolver.query(uri, strArr, null, null, null);
        cursorQuery.moveToFirst();
        String string = cursorQuery.getString(cursorQuery.getColumnIndex(strArr[0]));
        cursorQuery.close();
        return string;
    }

    public static InputStream getInputStream(String str, int i) {
        try {
            String str2 = DeviceInfo.sDeviceRootDir;
            if (str2 != null && str.startsWith(str2)) {
                i = 2;
            }
            if (i == 0) {
                return getResInputStream(str);
            }
            if (i == 1) {
                return PlatformUtil.class.getClassLoader().getResourceAsStream(str);
            }
            if (i == 2) {
                File file = new File(str);
                if (file.exists()) {
                    return FileUtil.getFileInputStream(DCLoudApplicationImpl.self().getContext(), file);
                }
            }
            return null;
        } catch (Exception e) {
            Logger.e(RenderTypes.RENDER_TYPE_NATIVE, e.toString());
            return null;
        }
    }

    public static InputStream getResInputStream(String str) {
        try {
            String strUseAndroidPath = useAndroidPath(str);
            if (!TextUtils.isEmpty(strUseAndroidPath)) {
                if (strUseAndroidPath.startsWith("assets://")) {
                    strUseAndroidPath = strUseAndroidPath.replace("assets://", "");
                } else if (strUseAndroidPath.startsWith("android_asset/")) {
                    strUseAndroidPath = strUseAndroidPath.replace("android_asset/", "");
                } else if (strUseAndroidPath.startsWith(SDK.ANDROID_ASSET)) {
                    strUseAndroidPath = strUseAndroidPath.replace(SDK.ANDROID_ASSET, "");
                }
            }
            return AndroidResources.sAssetMgr.open(strUseAndroidPath, 2);
        } catch (FileNotFoundException unused) {
            Logger.e("PlatformUtil.getResInputStream FileNotFoundException pFilePath=" + str);
            return null;
        } catch (IOException unused2) {
            Logger.e("PlatformUtil.getResInputStream IOException pFilePath=" + str);
            return null;
        } catch (RuntimeException unused3) {
            Logger.e("PlatformUtil.getResInputStream RuntimeException pFilePath=" + str);
            return null;
        } catch (Exception unused4) {
            Logger.e("PlatformUtil.getResInputStream Exception pFilePath=" + str);
            return null;
        }
    }

    public static boolean hasAppInstalled(Context context, String str) throws PackageManager.NameNotFoundException {
        try {
            context.getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return new File("/sdcard/Android/data/" + str).exists();
        }
    }

    public static void init(Context context) {
        DeviceInfo.sApplicationContext = context;
    }

    public static Object invokeFieldValue(String str, String str2, Object obj) {
        Class<?> superclass;
        if (obj != null) {
            try {
                try {
                    superclass = obj.getClass();
                } catch (Throwable th) {
                    Logger.i(RenderTypes.RENDER_TYPE_NATIVE, th.toString());
                }
            } catch (ClassNotFoundException e) {
                Logger.i(RenderTypes.RENDER_TYPE_NATIVE, e.toString());
            }
        } else {
            superclass = null;
        }
        if (superclass == null) {
            superclass = Class.forName(str);
        }
        Field declaredField = null;
        while (superclass != null) {
            try {
                declaredField = superclass.getField(str2);
            } catch (Exception unused) {
            }
            if (declaredField != null) {
                break;
            }
            if (declaredField == null) {
                try {
                    declaredField = superclass.getDeclaredField(str2);
                    if (declaredField != null) {
                        break;
                    }
                } catch (Exception unused2) {
                    continue;
                }
            }
            superclass = superclass.getSuperclass();
        }
        if (declaredField != null) {
            declaredField.setAccessible(true);
            return declaredField.get(obj);
        }
        return null;
    }

    public static Object invokeMethod(String str, String str2) {
        return invokeMethod(str, str2, null, new Class[0], new Object[0]);
    }

    public static boolean invokeSetFieldValue(Object obj, String str, Object obj2) {
        if (obj == null) {
            return false;
        }
        try {
            Field declaredField = null;
            for (Class<?> superclass = obj.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
                try {
                    declaredField = superclass.getField(str);
                } catch (Exception unused) {
                }
                if (declaredField != null) {
                    break;
                }
                if (declaredField == null) {
                    try {
                        declaredField = superclass.getDeclaredField(str);
                        if (declaredField != null) {
                            break;
                        }
                    } catch (Exception unused2) {
                        continue;
                    }
                }
            }
            if (declaredField != null) {
                declaredField.setAccessible(true);
                declaredField.set(obj, obj2);
                return true;
            }
        } catch (Throwable th) {
            Logger.i(RenderTypes.RENDER_TYPE_NATIVE, th.toString());
        }
        return false;
    }

    public static boolean isAppInstalled(Context context, String str) {
        return LoadAppUtils.isAppLoad(context, str);
    }

    public static boolean isEmulator() {
        String str = Build.MODEL;
        return str.equals("sdk") || str.equals("google_sdk");
    }

    public static boolean isLineWhiteBitmap(Bitmap bitmap, boolean z) {
        int width = bitmap.getWidth();
        int[] iArr = new int[width];
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), 1);
        boolean zEquals = Arrays.equals(iArr, GET_WIHTE_LINE(width));
        return z ? zEquals | Arrays.equals(iArr, GET_BLACK_LINE(width)) : zEquals;
    }

    private static boolean isPureColor(int[] iArr) {
        int i = iArr[0];
        boolean z = true;
        for (int i2 : iArr) {
            if (i != i2) {
                z = false;
            }
        }
        return z;
    }

    public static boolean isResFileExists(String str) throws IOException {
        try {
            InputStream inputStreamOpen = AndroidResources.sAssetMgr.open(str);
            if (inputStreamOpen != null) {
                inputStreamOpen.close();
                return true;
            }
        } catch (IOException unused) {
        }
        return false;
    }

    public static boolean isWhiteBitmap(Bitmap bitmap) {
        return isWhiteBitmap(bitmap, false, false);
    }

    public static void launchApplication(Context context, String str, String str2, HashMap map, boolean z) throws Exception {
        Intent intent = PdrUtil.isEmpty(str2) ? new Intent("android.intent.action.MAIN") : new Intent(str2);
        if (!PdrUtil.isEmpty(str) && !setPackageName(context, intent, str)) {
            throw new RuntimeException();
        }
        if (z) {
            intent.setFlags(268435456);
        }
        if (map != null && !map.isEmpty()) {
            for (String str3 : map.keySet()) {
                Object obj = map.get(str3);
                if (obj instanceof Integer) {
                    intent.putExtra(str3, ((Integer) obj).intValue());
                } else if (obj instanceof String) {
                    intent.putExtra(str3, (String) obj);
                } else if (obj instanceof Boolean) {
                    intent.putExtra(str3, ((Boolean) obj).booleanValue());
                }
            }
        }
        context.startActivity(intent);
    }

    public static String[] listFsAppsFiles(String str) {
        try {
            return new File(str).list();
        } catch (Exception e) {
            Logger.w("PlatformUtil.listResFiles pPath=" + str, e);
            return null;
        }
    }

    public static String[] listResFiles(String str) {
        try {
            return AndroidResources.sAssetMgr.list(useAndroidPath(str));
        } catch (IOException e) {
            Logger.w("PlatformUtil.listResFiles pPath=" + str, e);
            return null;
        }
    }

    public static Object newInstance(String str, Class[] clsArr, Object[] objArr) {
        try {
            return Class.forName(str).getConstructor(clsArr).newInstance(objArr);
        } catch (Exception e) {
            Logger.i(RenderTypes.RENDER_TYPE_NATIVE, e.toString());
            return null;
        } catch (Throwable th) {
            Logger.i(RenderTypes.RENDER_TYPE_NATIVE, th.toString());
            return null;
        }
    }

    public static void openFileBySystem(Context context, String str, String str2, String str3, ICallBack iCallBack) {
        if (Build.VERSION.SDK_INT >= 24) {
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
        }
        boolean zHasAppInstalled = !TextUtils.isEmpty(str2) ? hasAppInstalled(context, str2) : false;
        try {
            String mimeType = PdrUtil.getMimeType(str);
            if (str.startsWith(DeviceInfo.FILE_PROTOCOL)) {
                str = str.substring(7);
            }
            if (str.startsWith("content://")) {
                str = getFilePathFromContentUri(Uri.parse(str), context.getContentResolver());
                mimeType = PdrUtil.getMimeType(str);
            }
            if (PdrUtil.isEmpty(str3)) {
                str3 = mimeType;
            }
            Intent dataAndTypeIntent = LoadAppUtils.getDataAndTypeIntent(context, str, str3);
            if (zHasAppInstalled) {
                dataAndTypeIntent.setPackage(str2);
            }
            if (!new File(str).exists()) {
                if (iCallBack != null) {
                    iCallBack.onCallBack(-1, StringUtil.format(DOMException.JSON_ERROR_INFO, 0, DOMException.MSG_NOT_FOUND_FILE));
                }
            } else {
                context.startActivity(dataAndTypeIntent);
                if (iCallBack != null) {
                    iCallBack.onCallBack(1, "{}");
                }
            }
        } catch (ActivityNotFoundException e) {
            Logger.w(e);
            if (iCallBack != null) {
                iCallBack.onCallBack(-1, StringUtil.format(DOMException.JSON_ERROR_INFO, 1, DOMException.MSG_NOT_FOUND_3TH));
            }
        }
    }

    public static void openURL(Context context, String str, String str2) throws Exception {
        Intent uri = Intent.parseUri(str, 0);
        if (!PdrUtil.isEmpty(str2)) {
            uri.setPackage(str2);
        }
        uri.setSelector(null);
        if (BaseInfo.isDefense) {
            uri.setComponent(null);
            uri.addCategory("android.intent.category.BROWSABLE");
        }
        uri.setFlags(268435456);
        context.startActivity(uri);
    }

    public static PackageInfo parseApkInfo(Context context, String str) throws Exception {
        return context.getPackageManager().getPackageArchiveInfo(str, 1);
    }

    private static boolean queryDefaultShortcut(String str) {
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = DeviceInfo.sApplicationContext.getContentResolver().query(Uri.parse("content://com.android.launcher2.settings/favorites?notify=false"), PROJECTION, "title=?", new String[]{str}, null);
            } catch (Exception e) {
                Logger.e("PlatformUtil", "queryHTCShortCut error:" + e.getMessage());
                if (cursorQuery == null) {
                    return false;
                }
            }
            if (cursorQuery != null && cursorQuery.moveToFirst()) {
                cursorQuery.close();
                cursorQuery.close();
                return true;
            }
            if (cursorQuery == null) {
                return false;
            }
            cursorQuery.close();
            return false;
        } finally {
        }
    }

    private static boolean queryHTCShortCut(String str) {
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = DeviceInfo.sApplicationContext.getContentResolver().query(Uri.parse(URI_HTC_LAUNCER), PROJECTION, "title=?", new String[]{str}, null);
            } catch (Exception e) {
                Logger.e("PlatformUtil", "queryHTCShortCut error:" + e.getMessage());
                if (cursorQuery == null) {
                    return false;
                }
            }
            if (cursorQuery != null && cursorQuery.moveToFirst()) {
                cursorQuery.close();
                cursorQuery.close();
                return true;
            }
            if (cursorQuery == null) {
                return false;
            }
            cursorQuery.close();
            return false;
        } finally {
        }
    }

    private static int queryMaxLauncherScreenCount(Context context) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse(URI_SAMSUNG_LAUNCER), new String[]{"MAX(screen)"}, null, null, null);
        if (cursorQuery == null) {
            return -1;
        }
        try {
            cursorQuery.moveToNext();
            return cursorQuery.getInt(0) + 1;
        } catch (Exception e) {
            Logger.e("PlatformUtil", "Samsung Launcher" + e);
            return -1;
        } finally {
            cursorQuery.close();
        }
    }

    public static boolean setPackageName(Context context, Intent intent, String str) {
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(str);
        if (launchIntentForPackage == null) {
            return false;
        }
        intent.setClassName(str, launchIntentForPackage.getComponent().getClassName());
        return true;
    }

    private static String useAndroidPath(String str) {
        return StringUtil.trimString(StringUtil.trimString(str, '/'), '\\');
    }

    public void delShortcut(String str, String str2, String str3) {
        Intent intent = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        intent.putExtra("android.intent.extra.shortcut.NAME", str);
        Intent intent2 = new Intent("android.intent.action.MAIN");
        intent2.addCategory("android.intent.category.DEFAULT");
        intent2.setComponent(new ComponentName(str2, str3));
        intent.putExtra("android.intent.extra.shortcut.INTENT", intent2);
        DeviceInfo.sApplicationContext.sendBroadcast(intent);
    }

    public static Bitmap captureView(View view, boolean z, boolean z2, Rect rect, String str) {
        if (z2 && AndroidResources.sIMEAlive) {
            return null;
        }
        int iWidth = rect != null ? rect.width() : view.getMeasuredWidth();
        int iHeight = rect != null ? rect.height() : view.getMeasuredHeight();
        if (iWidth == 0) {
            return null;
        }
        Bitmap.Config config = Bitmap.Config.RGB_565;
        if (str.equals("ARGB")) {
            config = Bitmap.Config.ARGB_4444;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iWidth, iHeight, config);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        if (rect != null) {
            canvas.translate(-rect.left, -rect.top);
        }
        view.draw(canvas);
        if (!z || !isWhiteBitmap(bitmapCreateBitmap)) {
            return bitmapCreateBitmap;
        }
        bitmapCreateBitmap.recycle();
        return null;
    }

    public static Object invokeMethod(String str, String str2, Object obj) {
        return invokeMethod(str, str2, obj, new Class[0], new Object[0]);
    }

    public static boolean isWhiteBitmap(Bitmap bitmap, boolean z, boolean z2) {
        int height = bitmap.getHeight();
        int[] iArr = new int[height];
        bitmap.getPixels(iArr, 0, 1, bitmap.getWidth() / 4, 0, 1, height);
        boolean zEquals = Arrays.equals(iArr, GET_WIHTE_LINE(height));
        if (z2) {
            zEquals = isPureColor(iArr);
        }
        bitmap.getPixels(iArr, 0, 1, bitmap.getWidth() / 2, 0, 1, height);
        boolean zEquals2 = zEquals & Arrays.equals(iArr, GET_WIHTE_LINE(height));
        if (z2) {
            zEquals2 = isPureColor(iArr);
        }
        bitmap.getPixels(iArr, 0, 1, (bitmap.getWidth() * 3) / 4, 0, 1, height);
        boolean zEquals3 = zEquals2 & Arrays.equals(iArr, GET_WIHTE_LINE(height));
        if (z2) {
            zEquals3 = isPureColor(iArr);
        }
        return z ? zEquals3 | Arrays.equals(iArr, GET_BLACK_LINE(height)) : zEquals3;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.Object invokeMethod(java.lang.String r3, java.lang.String r4, java.lang.Object r5, java.lang.Class[] r6, java.lang.Object[] r7) {
        /*
            r0 = 0
            java.lang.Class r1 = java.lang.Class.forName(r3)     // Catch: java.lang.Exception -> L16 java.lang.NoSuchMethodException -> L1c java.lang.ClassNotFoundException -> L1f
            java.lang.reflect.Method r6 = r1.getMethod(r4, r6)     // Catch: java.lang.Exception -> L16 java.lang.NoSuchMethodException -> L1c java.lang.ClassNotFoundException -> L1f
            if (r6 == 0) goto L14
            r1 = 1
            r6.setAccessible(r1)     // Catch: java.lang.Exception -> L16 java.lang.NoSuchMethodException -> L1c java.lang.ClassNotFoundException -> L1f
            java.lang.Object r5 = r6.invoke(r5, r7)     // Catch: java.lang.Exception -> L16 java.lang.NoSuchMethodException -> L1c java.lang.ClassNotFoundException -> L1f
            goto L24
        L14:
            r5 = r0
            goto L24
        L16:
            r5 = move-exception
            java.lang.String r5 = r5.getMessage()
            goto L21
        L1c:
            java.lang.String r5 = "NoSuchMethodException"
            goto L21
        L1f:
            java.lang.String r5 = "ClassNotFoundException"
        L21:
            r2 = r0
            r0 = r5
            r5 = r2
        L24:
            if (r0 == 0) goto L4d
            java.lang.String r6 = "getJsContent"
            boolean r6 = r6.equals(r4)
            if (r6 != 0) goto L4d
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r0)
            java.lang.String r7 = " "
            r6.append(r7)
            r6.append(r3)
            r6.append(r7)
            r6.append(r4)
            java.lang.String r3 = r6.toString()
            java.lang.String r4 = "platform"
            io.dcloud.common.adapter.util.Logger.i(r4, r3)
        L4d:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.common.adapter.util.PlatformUtil.invokeMethod(java.lang.String, java.lang.String, java.lang.Object, java.lang.Class[], java.lang.Object[]):java.lang.Object");
    }

    public static void createShortut(Context context, String str, String str2, Bitmap bitmap, boolean z) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setClassName(context, str2);
        Intent intent2 = new Intent(ACTION_INSTALL_SHORTCUT);
        intent2.putExtra("android.intent.extra.shortcut.INTENT", intent);
        intent2.putExtra("android.intent.extra.shortcut.NAME", str);
        intent2.putExtra("android.intent.extra.shortcut.ICON", bitmap);
        intent2.putExtra(EXTRA_SHORTCUT_DUPLICATE, z);
        context.sendBroadcast(intent2);
    }

    public static InputStream getInputStream(String str) {
        if (str == null) {
            return null;
        }
        return getInputStream(str, PdrUtil.isDeviceRootDir(str) ? 2 : 0);
    }

    public static Bitmap captureView(View view, String str) {
        return captureView(view, null, str);
    }

    public static Bitmap captureView(View view, Rect rect, String str) {
        try {
            int width = view.getWidth();
            int height = view.getHeight();
            boolean z = rect != null;
            if (z) {
                int i = rect.left;
                int i2 = rect.top;
                view.layout(i, i2, rect.right - i, rect.bottom - i2);
            } else {
                view.layout(0, 0, width, height);
            }
            view.setDrawingCacheEnabled(true);
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getDrawingCache());
            bitmapCreateBitmap.setDensity(view.getContext().getResources().getDisplayMetrics().densityDpi);
            if (!PdrUtil.isEmpty(str)) {
                PdrUtil.saveBitmapToFile(bitmapCreateBitmap, str);
            }
            view.setDrawingCacheEnabled(false);
            if (z) {
                view.layout(0, 0, width, height);
            }
            return bitmapCreateBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object invokeMethod(Object obj, String str, Class<?>[] clsArr, Object... objArr) {
        if (obj == null) {
            return null;
        }
        try {
            Method method = obj.getClass().getMethod(str, clsArr);
            method.setAccessible(true);
            if (objArr.length == 0) {
                objArr = null;
            }
            return method.invoke(obj, objArr);
        } catch (Throwable th) {
            Logger.i(RenderTypes.RENDER_TYPE_NATIVE, th.toString());
            return null;
        }
    }
}
