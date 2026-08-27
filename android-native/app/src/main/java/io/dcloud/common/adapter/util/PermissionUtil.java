package io.dcloud.common.adapter.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.widget.CheckBox;
import androidx.core.app.ActivityCompat;
import com.dcloud.android.widget.dialog.DCloudAlertDialog;
import com.facebook.common.callercontext.ContextChain;
import com.hjq.permissions.Permission;
import io.dcloud.WebAppActivity;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IReflectAble;
import io.dcloud.common.DHInterface.ISysEventListener;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.DHInterface.message.action.PermissionRequestAction;
import io.dcloud.common.constant.IntentConst;
import io.dcloud.common.core.permission.PermissionControler;
import io.dcloud.common.util.AppPermissionUtil;
import io.dcloud.common.util.DialogUtil;
import io.dcloud.common.util.IOUtil;
import io.dcloud.common.util.StringUtil;
import io.dcloud.feature.gg.dcloud.ADSim;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class PermissionUtil implements IReflectAble {
    public static final String PMS_CAMERA = "CAMERA";
    public static final String PMS_CONTACTS = "CONTACTS";
    public static final String PMS_GALLERY = "GALLERY";
    public static final String PMS_LOCATION = "LOCATION";
    public static final String PMS_NATIVEJS = "NATIVE.JS";
    public static final String PMS_PHONE = "PHONE";
    public static final String PMS_PUSH = "PUSH";
    public static final String PMS_RECORD = "RECORD";
    public static final String PMS_SHORTCUT = "SHORTCUT";
    public static final String PMS_SMS = "SMS";
    public static final String PMS_STORAGE = "STORAGE";
    public static final String PMS_STORAGE_IMAGE = "STORAGE_IMAGE";
    private static List<String> alwaysDeniedPer = null;
    public static boolean isCheckPermissionDisabled = false;
    private static int sDefQequestCode;
    private static HashMap<String, PermissionData> sPermissionData;
    private static int sRequestCodeCounter;
    private static HashMap<String, HashMap<String, Integer>> useRejectedCache = new HashMap<>();
    private static HashMap<Integer, Object[]> sActivityResultCallBacks = new HashMap<>();
    private static LinkedList<ShowDialogData> sUseStreamAppPermissionDialogs = new LinkedList<>();
    public static int sUseStreamAppPermissionDialogCount = 0;
    private static HashMap<Integer, HashMap<Request, String[]>> sRequestCallBacks = new HashMap<>();

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    static class PermissionData {
        static final int CB_NOSHOW = -1;
        static final int CB_SELECTED = 1;
        static final int CB_SHOW = 0;
        static final int GT_DENIED = -1;
        static final int GT_GRANTED = 1;
        static final int GT_ONCE = 0;
        int checkbox;
        int grantType;
        int messageId;
        String name;

        PermissionData(String str, int i, int i2, int i3) {
            this.name = str;
            this.messageId = i;
            this.checkbox = i2;
            this.grantType = i3;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static abstract class Request {
        public static final int PERMISSION_ASK = 1;
        public static final int PERMISSION_DENIED = -1;
        public static final int PERMISSION_GRANTED = 0;
        private int mRequestCode = PermissionUtil.sDefQequestCode;
        private boolean isTriggerRequestEvent = false;

        public String getAppName() {
            return null;
        }

        public int getRequestCode() {
            return this.mRequestCode;
        }

        public abstract void onDenied(String str);

        public abstract void onGranted(String str);

        public void setRequestCode(int i) {
            this.mRequestCode = i;
        }

        public void setTriggerRequestEvent(boolean z) {
            this.isTriggerRequestEvent = z;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static abstract class StreamPermissionRequest extends Request {
        IApp mApp;
        public Object mTag = null;
        public String mAppid = null;
        private String mAppName = null;
        private String[] mPermission = null;
        private String[] mOriginalPermisson = null;

        public StreamPermissionRequest(IApp iApp) {
            setApp(iApp);
        }

        @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
        public String getAppName() {
            return this.mAppName;
        }

        String[] getStreamRequestPermission() {
            return this.mOriginalPermisson;
        }

        protected String[] getSystemRequestPermission() {
            return this.mPermission;
        }

        public void setApp(IApp iApp) {
            this.mApp = iApp;
            this.mAppid = iApp.obtainAppId();
            this.mAppName = iApp.obtainAppName();
        }

        public StreamPermissionRequest setRequestPermission(String... strArr) {
            this.mOriginalPermisson = strArr;
            this.mPermission = new String[strArr.length];
            int i = 0;
            while (true) {
                String[] strArr2 = this.mPermission;
                if (i >= strArr2.length) {
                    return this;
                }
                strArr2[i] = PermissionUtil.convert2SystemPermission(strArr[i]);
                i++;
            }
        }
    }

    static {
        HashMap<String, PermissionData> map = new HashMap<>();
        sPermissionData = map;
        map.put(PMS_LOCATION, new PermissionData(PMS_LOCATION, R.string.dcloud_permissions_whether_allow, -1, 1));
        sPermissionData.put(PMS_RECORD, new PermissionData(PMS_RECORD, R.string.dcloud_permissions_record_whether_allow, -1, 1));
        sPermissionData.put(PMS_CAMERA, new PermissionData(PMS_CAMERA, R.string.dcloud_permissions_camera_whether_allow, -1, 1));
        sPermissionData.put(PMS_GALLERY, new PermissionData(PMS_GALLERY, R.string.dcloud_permissions_album_whether_allow, -1, 1));
        sPermissionData.put(PMS_PUSH, new PermissionData(PMS_PUSH, R.string.dcloud_permissions_informs_whether_allow, -1, 1));
        sPermissionData.put("SHORTCUT", new PermissionData("SHORTCUT", R.string.dcloud_permissions_short_cut_close_tips, 1, 0));
        sPermissionData.put(PMS_SMS, new PermissionData(PMS_SMS, R.string.dcloud_permissions_sms_whether_allow, -1, 1));
        sPermissionData.put(PMS_PHONE, new PermissionData(PMS_PHONE, R.string.dcloud_permissions_phone_call_whether_allow, -1, 1));
        sPermissionData.put(PMS_NATIVEJS, new PermissionData(PMS_NATIVEJS, R.string.dcloud_permissions_njs_whether_allow, 1, 0));
        sDefQequestCode = 60505;
        sRequestCodeCounter = 60505;
    }

    private PermissionUtil() {
    }

    private static boolean caseVersion(Activity activity) {
        if (Build.VERSION.SDK_INT < 23 || activity == null || activity.getApplicationInfo().targetSdkVersion < 23) {
            return false;
        }
        String str = Build.BRAND;
        return (str.equalsIgnoreCase(MobilePhoneModel.GIONEE) || str.equalsIgnoreCase(MobilePhoneModel.QIHU360)) ? false : true;
    }

    public static boolean checkLocationPermission(Activity activity) throws IllegalAccessException, IllegalArgumentException {
        if (isEMUIRom(activity)) {
            return checkPermission_EMUI(activity);
        }
        if (isMiuiRom(activity)) {
            int iIsMiui = isMiui(activity, Permission.ACCESS_COARSE_LOCATION, (String) null);
            return iIsMiui == -100 || iIsMiui == 0;
        }
        if (isFlymeRom(activity)) {
            return checkPermission_Flyme(activity);
        }
        return true;
    }

    public static boolean checkLocationService(Activity activity) {
        try {
            LocationManager locationManager = (LocationManager) activity.getSystemService("location");
            return locationManager.isProviderEnabled("gps") || locationManager.isProviderEnabled("network");
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static String checkPermission(IWebview iWebview, String[] strArr) {
        return strArr[0].equals("SHORTCUT") ? PermissionControler.checkPermission(iWebview, strArr) : caseVersion(iWebview.getActivity()) ? PermissionControler.checkPermission(iWebview, strArr) : checkSelfPermission(iWebview.getActivity(), convert2SystemPermission(strArr[0]), iWebview.obtainApp().obtainAppName()) == -1 ? IApp.AUTHORITY_DENIED : "notdeny";
    }

    public static boolean checkPermissionDenied(Activity activity, String str) {
        initAlwaysDenied(activity);
        if (alwaysDeniedPer.contains(str)) {
            return isCheckPermissionDisabled || !ActivityCompat.shouldShowRequestPermissionRationale(activity, str);
        }
        return false;
    }

    private static boolean checkPermission_EMUI(Activity activity) {
        try {
            return ((Integer) PlatformUtil.invokeMethod("com.huawei.android.app.AppOpsManagerEx", "getMode", null, new Class[]{Integer.TYPE, String.class}, new Object[]{8, activity.getPackageName()})).intValue() == 1;
        } catch (Throwable th) {
            th.printStackTrace();
            return true;
        }
    }

    private static boolean checkPermission_Flyme(Activity activity) {
        try {
            return ((Boolean) PlatformUtil.invokeMethod("meizu.security.FlymePermissionManager", "isFlymePermissionGranted", null, new Class[]{Integer.TYPE}, new Object[]{75})).booleanValue();
        } catch (Throwable th) {
            th.printStackTrace();
            return true;
        }
    }

    public static boolean checkPermissions(Context context, String[] strArr) {
        if (strArr == null || strArr.length <= 0) {
            return true;
        }
        for (String str : strArr) {
            if (checkSelfPermission((Activity) context, str, "") == -1) {
                return false;
            }
        }
        return true;
    }

    public static int checkSelfPermission(Activity activity, String str, String str2) {
        return "android.permission.INSTALL_SHORTCUT".equals(str) ? 1 != AppPermissionUtil.checkPermission(activity, str2) ? 0 : -1 : (!caseVersion(activity) || str == null) ? trycatchGetPermission(activity, str, str2) : ActivityCompat.checkSelfPermission(activity, str);
    }

    public static int checkStreamAppPermission(Context context, String str, String str2) {
        return context.getSharedPreferences("stream_permission", 0).getInt(str + "_" + str2, 1);
    }

    public static void clearPermission(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("stream_permission", 0);
        Iterator<String> it = sPermissionData.keySet().iterator();
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        while (it.hasNext()) {
            editorEdit.remove(str + "_" + it.next());
        }
        editorEdit.commit();
    }

    public static void clearUseRejectedCache() {
        useRejectedCache.clear();
    }

    private static synchronized boolean continueShowStreamAppPermissionDialog(ShowDialogData showDialogData) {
        if (showDialogData.force) {
            return true;
        }
        if (sUseStreamAppPermissionDialogCount != 0) {
            sUseStreamAppPermissionDialogs.add(showDialogData);
        }
        sUseStreamAppPermissionDialogCount++;
        return sUseStreamAppPermissionDialogs.isEmpty();
    }

    public static String convert2StreamPermission(String str) {
        if (Permission.CAMERA.equals(str)) {
            return PMS_CAMERA;
        }
        if (Permission.RECORD_AUDIO.equals(str)) {
            return PMS_RECORD;
        }
        if (Permission.ACCESS_COARSE_LOCATION.equals(str)) {
            return PMS_LOCATION;
        }
        if (Permission.WRITE_CONTACTS.equals(str)) {
            return PMS_CONTACTS;
        }
        if (Permission.SEND_SMS.equals(str)) {
            return PMS_SMS;
        }
        if (Permission.CALL_PHONE.equals(str)) {
            return PMS_PHONE;
        }
        if (Permission.WRITE_EXTERNAL_STORAGE.equals(str)) {
            return PMS_STORAGE;
        }
        if ("android.permission.INSTALL_SHORTCUT".equals(str)) {
            return "SHORTCUT";
        }
        if (PMS_GALLERY.equals(str) || PMS_NATIVEJS.equals(str)) {
            return str;
        }
        PMS_PUSH.equals(str);
        return str;
    }

    public static String convert2SystemPermission(String str) {
        return PMS_CAMERA.equalsIgnoreCase(str) ? Permission.CAMERA : PMS_RECORD.equalsIgnoreCase(str) ? Permission.RECORD_AUDIO : PMS_LOCATION.equalsIgnoreCase(str) ? Permission.ACCESS_COARSE_LOCATION : PMS_CONTACTS.equalsIgnoreCase(str) ? Permission.WRITE_CONTACTS : PMS_STORAGE.equalsIgnoreCase(str) ? Permission.WRITE_EXTERNAL_STORAGE : PMS_STORAGE_IMAGE.equalsIgnoreCase(str) ? Build.VERSION.SDK_INT < 33 ? Permission.WRITE_EXTERNAL_STORAGE : Permission.READ_MEDIA_IMAGES : PMS_SMS.equalsIgnoreCase(str) ? Permission.SEND_SMS : PMS_PHONE.equalsIgnoreCase(str) ? Permission.CALL_PHONE : "SHORTCUT".equalsIgnoreCase(str) ? "android.permission.INSTALL_SHORTCUT" : str;
    }

    public static String convert5PlusValue(int i) {
        return PermissionControler.convert5PlusValue(i);
    }

    public static String convertNativePermission(String str) {
        return PermissionControler.convertNativePermission(str);
    }

    private static int getDeivceSuitablePixel(Activity activity, int i) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return (int) (i * displayMetrics.density);
    }

    public static int getRequestCode() {
        if (sRequestCodeCounter >= 65535) {
            restRequstCode();
        }
        int i = sRequestCodeCounter;
        sRequestCodeCounter = i + 1;
        return i;
    }

    public static void goPermissionCenter(Activity activity, String str, String str2, Request request) {
        if (SafeCenter.goSafeCenter(activity, str2, request)) {
            return;
        }
        request.onDenied(str2);
    }

    public static void goSafeCenter(Activity activity) {
        SafeCenter.goSafeCenter(activity);
    }

    public static boolean hasDefinedInManifest(Context context, String str) throws PackageManager.NameNotFoundException {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getApplicationInfo().packageName, 4096);
            if (packageInfo != null) {
                int i = 0;
                while (true) {
                    String[] strArr = packageInfo.requestedPermissions;
                    if (i >= strArr.length) {
                        break;
                    }
                    if (str.equals(strArr[i])) {
                        return true;
                    }
                    i++;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void initAlwaysDenied(Activity activity) {
        if (alwaysDeniedPer == null) {
            String bundleData = SP.getBundleData(activity, "ALWAYS_DENIED_PERMISSION", "permissions");
            if (TextUtils.isEmpty(bundleData)) {
                alwaysDeniedPer = new ArrayList();
            } else {
                alwaysDeniedPer = new ArrayList(Arrays.asList(bundleData.split(",")));
            }
        }
    }

    private static boolean isAndroid(Activity activity, String str, Request request) {
        Intent intent = new Intent();
        intent.setClassName("com.android.Setting", "com.android.SubSetting");
        intent.putExtra("package", activity.getPackageName());
        int requestCode = getRequestCode();
        activity.startActivityForResult(intent, requestCode);
        saveCallabckData(activity, str, request, requestCode);
        return true;
    }

    public static boolean isEMUIRom(Activity activity) {
        return Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("huawei");
    }

    private static boolean isFlyme(Activity activity, String str, Request request) {
        if (!Build.BRAND.contains("Meizu")) {
            return false;
        }
        Intent intent = new Intent();
        intent.setClassName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity");
        intent.putExtra("packageName", activity.getPackageName());
        int requestCode = getRequestCode();
        activity.startActivityForResult(intent, requestCode);
        saveCallabckData(activity, str, request, requestCode);
        return true;
    }

    private static boolean isFlymeRom(Activity activity) {
        return Build.BRAND.toLowerCase(Locale.ENGLISH).contains("meizu");
    }

    public static boolean isMainStreamPermission(String str) {
        return PMS_LOCATION.equalsIgnoreCase(str) || "SHORTCUT".equalsIgnoreCase(str) || PMS_RECORD.equalsIgnoreCase(str);
    }

    private static int isMiui(Activity activity, String str, String str2) throws IllegalAccessException, IllegalArgumentException {
        Object systemService;
        try {
            if (!isMiuiRom(activity) || (systemService = activity.getSystemService("appops")) == null) {
                return -100;
            }
            int i = systemService.getClass().getField("OP_GPS").getInt(null);
            Class<?> cls = systemService.getClass();
            Class<?> cls2 = Integer.TYPE;
            int iIntValue = ((Integer) cls.getMethod("checkOp", cls2, cls2, String.class).invoke(systemService, Integer.valueOf(i), Integer.valueOf(Binder.getCallingUid()), activity.getPackageName())).intValue();
            if (iIntValue == systemService.getClass().getField("MODE_IGNORED").getInt(null)) {
                return -1;
            }
            return iIntValue == systemService.getClass().getField("MODE_ALLOWED").getInt(null) ? 0 : -100;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return -100;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -100;
        }
    }

    public static void onActivityResult(Activity activity, int i, int i2, Intent intent) {
        HashMap<Integer, Object[]> map = sActivityResultCallBacks;
        int i3 = i % ADSim.INTISPLSH;
        Object[] objArr = map.get(Integer.valueOf(i3));
        if (objArr != null) {
            long longExtra = activity.getIntent().getLongExtra(IntentConst.PER_GO_CENTER_TIME, System.currentTimeMillis());
            String str = (String) objArr[0];
            if (System.currentTimeMillis() - longExtra > 1000) {
                sActivityResultCallBacks.remove(Integer.valueOf(i3));
                String strConvert2SystemPermission = convert2SystemPermission(str);
                Request request = (Request) objArr[1];
                activity.getIntent().removeExtra(IntentConst.PER_GO_CENTER_TIME);
                if (checkSelfPermission(activity, strConvert2SystemPermission, request.getAppName()) == 0) {
                    request.onGranted(str);
                } else {
                    request.onDenied(str);
                }
            }
            PermissionControler.invokeUTSAndroidPermissionRequest(PermissionRequestAction.TYPE_COMPLETE, new String[]{str});
        }
    }

    public static void onRequestSysPermissionResume(Activity activity) {
        int intExtra = activity.getIntent().getIntExtra(IntentConst.PER_GO_CENTER_REQUESTCODE, 0);
        if (intExtra != 0) {
            onActivityResult(activity, intExtra, 0, null);
        }
    }

    public static void onSystemPermissionsResult(Activity activity, int i, String[] strArr, int[] iArr) {
        String[] strArr2;
        HashMap<Request, String[]> mapRemove = sRequestCallBacks.remove(Integer.valueOf(i));
        if (mapRemove != null && mapRemove.size() > 0) {
            Request[] requestArr = (Request[]) mapRemove.keySet().toArray(new Request[0]);
            Request request = requestArr.length > 0 ? requestArr[0] : null;
            initAlwaysDenied(activity);
            for (int i2 = 0; i2 < strArr.length; i2++) {
                int i3 = iArr[i2];
                String strConvert2StreamPermission = convert2StreamPermission(strArr[i2]);
                if (i3 == -1) {
                    try {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, strArr[i2])) {
                            alwaysDeniedPer.add(strArr[i2]);
                        }
                    } catch (RuntimeException unused) {
                    }
                    if (request != null) {
                        request.onDenied(strConvert2StreamPermission);
                    }
                } else if (i3 == 0) {
                    if (alwaysDeniedPer.contains(strArr[i2])) {
                        alwaysDeniedPer.remove(strArr[i2]);
                    }
                    if (request != null) {
                        request.onGranted(strConvert2StreamPermission);
                    }
                }
            }
            if (alwaysDeniedPer.size() > 0) {
                SP.setBundleData(activity, "ALWAYS_DENIED_PERMISSION", "permissions", TextUtils.join(",", (String[]) alwaysDeniedPer.toArray(new String[0])));
            }
            if (strArr.length == 0 && iArr.length == 0 && request != null && (strArr2 = mapRemove.get(request)) != null) {
                for (String str : strArr2) {
                    request.onDenied(convert2StreamPermission(str));
                }
            }
        }
        PermissionControler.invokeUTSAndroidPermissionRequest(PermissionRequestAction.TYPE_COMPLETE, strArr);
    }

    public static void putStreamAppPermission(Context context, String str, String str2, int i) {
        context.getSharedPreferences("stream_permission", 0).edit().putInt(str + "_" + str2, i).commit();
    }

    public static void removeStreamAppPermission(Context context, String str, String str2) {
        context.getSharedPreferences("stream_permission", 0).edit().remove(str + "_" + str2).commit();
    }

    public static void removeTempPermission(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("stream_permission", 0);
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        for (String str2 : sPermissionData.keySet()) {
            String str3 = str + "_" + str2;
            if (sharedPreferences.contains(str3) && (sharedPreferences.getInt(str3, 1) != 0 || "SHORTCUT".equals(str2) || PMS_NATIVEJS.equals(str2))) {
                editorEdit.remove(str3);
            }
        }
        editorEdit.commit();
    }

    public static void requestPermissions(Activity activity, String[] strArr, int i) {
        PermissionControler.invokeUTSAndroidPermissionRequest(PermissionRequestAction.TYPE_REQUEST, strArr);
        PermissionControler.requestPermissions(activity, strArr, i);
    }

    public static void requestSystemPermissions(Activity activity, String[] strArr, int i, Request request) {
        requestSystemPermissions(activity, strArr, i, request, true);
    }

    public static void restRequstCode() {
        sRequestCodeCounter = sDefQequestCode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void saveCallabckData(Activity activity, String str, Request request, int i) {
        sActivityResultCallBacks.put(Integer.valueOf(i), new Object[]{str, request});
        activity.getIntent().putExtra(IntentConst.PER_GO_CENTER_REQUESTCODE, i);
        activity.getIntent().putExtra(IntentConst.PER_GO_CENTER_TIME, System.currentTimeMillis());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void showStreamAppPermissionDialog() {
        int i = sUseStreamAppPermissionDialogCount;
        if (i > 0) {
            sUseStreamAppPermissionDialogCount = i - 1;
        }
        if (!sUseStreamAppPermissionDialogs.isEmpty()) {
            ShowDialogData showDialogDataPop = sUseStreamAppPermissionDialogs.pop();
            showDialogDataPop.force = true;
            if (1 != useStreamPermission(showDialogDataPop)) {
                showStreamAppPermissionDialog();
            }
        }
    }

    private static int trycatchGetPermission(Activity activity, String str, String str2) throws IllegalStateException, IOException {
        Camera cameraOpen;
        int i = -1;
        try {
            try {
                if (Permission.CAMERA.equals(str)) {
                    int i2 = 0;
                    while (true) {
                        try {
                            if (i2 >= Camera.getNumberOfCameras()) {
                                cameraOpen = null;
                                break;
                            }
                            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                            Camera.getCameraInfo(i2, cameraInfo);
                            if (cameraInfo.facing == 1) {
                                cameraOpen = Camera.open(i2);
                                break;
                            }
                            i2++;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (cameraOpen == null) {
                        cameraOpen = Camera.open();
                    }
                    if (cameraOpen != null) {
                        Camera.Parameters parameters = cameraOpen.getParameters();
                        if (parameters != null) {
                            parameters.getSupportedVideoSizes();
                        }
                        cameraOpen.release();
                        return 0;
                    }
                } else {
                    try {
                        if (Permission.RECORD_AUDIO.equals(str)) {
                            try {
                                MediaRecorder mediaRecorder = new MediaRecorder();
                                mediaRecorder.reset();
                                mediaRecorder.setAudioSource(0);
                                mediaRecorder.setOutputFile(activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/temp.3gp");
                                mediaRecorder.setAudioSamplingRate(96000);
                                mediaRecorder.setOutputFormat(1);
                                mediaRecorder.setAudioEncoder(3);
                                mediaRecorder.prepare();
                                mediaRecorder.start();
                                mediaRecorder.stop();
                                mediaRecorder.release();
                                if (new File(activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/temp.3gp").length() > 0) {
                                    try {
                                        new File(activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/temp.3gp").delete();
                                        return 0;
                                    } catch (IOException e2) {
                                        e = e2;
                                        i = 0;
                                        if ((e.getMessage() == null || (!e.getMessage().contains("Permission deny") && !e.getMessage().contains("Permission denied"))) && !Build.BRAND.equalsIgnoreCase(MobilePhoneModel.GIONEE)) {
                                            return 0;
                                        }
                                        return i;
                                    } catch (Exception e3) {
                                        e = e3;
                                        i = 0;
                                        String message = e.getMessage();
                                        if (message == null) {
                                            return 0;
                                        }
                                        if (!message.contains("start failed") && !message.contains("setAudioSource failed")) {
                                            return 0;
                                        }
                                        return i;
                                    }
                                }
                            } catch (IOException e4) {
                                e = e4;
                            } catch (Exception e5) {
                                e = e5;
                            }
                        } else {
                            if (Permission.ACCESS_COARSE_LOCATION.equals(str)) {
                                LocationManager locationManager = (LocationManager) activity.getSystemService("location");
                                boolean zIsProviderEnabled = locationManager.isProviderEnabled("gps");
                                boolean zIsProviderEnabled2 = locationManager.isProviderEnabled("network");
                                boolean zEquals = TextUtils.equals("ZTE B880", Build.MODEL);
                                if (!zIsProviderEnabled && !zIsProviderEnabled2) {
                                    if (zEquals) {
                                        return 0;
                                    }
                                }
                                int iIsMiui = isMiui(activity, str, str2);
                                if (iIsMiui != -100) {
                                    return iIsMiui;
                                }
                                return 0;
                            }
                            if (!Permission.WRITE_CONTACTS.equals(str) && !Permission.SEND_SMS.equals(str) && !Permission.CALL_PHONE.equals(str)) {
                                if (Permission.WRITE_EXTERNAL_STORAGE.equals(str)) {
                                    try {
                                        File file = new File(activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/temp.arm");
                                        if (!file.getParentFile().exists()) {
                                            file.getParentFile().mkdirs();
                                        }
                                        if (file.exists()) {
                                            return 0;
                                        }
                                        file.createNewFile();
                                        return 0;
                                    } catch (Exception e6) {
                                        e6.printStackTrace();
                                    }
                                } else if ("android.permission.INSTALL_SHORTCUT".equals(str)) {
                                    if (1 != AppPermissionUtil.checkPermission(activity, str2)) {
                                        return 0;
                                    }
                                } else if (!PMS_GALLERY.equals(str) && !PMS_NATIVEJS.equals(str)) {
                                    PMS_PUSH.equals(str);
                                    return -1;
                                }
                            }
                        }
                    } catch (SecurityException unused) {
                    }
                }
            } catch (IllegalArgumentException | SecurityException unused2) {
            }
            return -1;
        } catch (Throwable unused3) {
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void unregisterWebAppReStartEvent(IApp iApp, ISysEventListener iSysEventListener) {
        iApp.unregisterSysEventListener(iSysEventListener, ISysEventListener.SysEventType.onWebAppReStart);
    }

    public static synchronized void usePermission(Activity activity, final String str, String str2, int i, final Request request) {
        HashMap<String, Integer> map;
        if (!request.isTriggerRequestEvent) {
            PermissionControler.invokeUTSAndroidPermissionRequest(PermissionRequestAction.TYPE_REQUEST, new String[]{str2});
        }
        if (str2.equals(PMS_STORAGE) && activity.getApplicationInfo().targetSdkVersion >= 33 && Build.VERSION.SDK_INT >= 33) {
            request.onGranted(str2);
            PermissionControler.invokeUTSAndroidPermissionRequest(PermissionRequestAction.TYPE_COMPLETE, new String[]{str2});
            return;
        }
        String strConvert2SystemPermission = convert2SystemPermission(str2);
        if (checkSelfPermission(activity, strConvert2SystemPermission, request.getAppName()) == 0) {
            request.onGranted(str2);
            PermissionControler.invokeUTSAndroidPermissionRequest(PermissionRequestAction.TYPE_COMPLETE, new String[]{str2});
            return;
        }
        if (useRejectedCache.containsKey(str) && i > 0 && (map = useRejectedCache.get(str)) != null && map.containsKey(str2) && map.get(str2).intValue() >= i) {
            request.onDenied(str2);
            PermissionControler.invokeUTSAndroidPermissionRequest(PermissionRequestAction.TYPE_COMPLETE, new String[]{str2});
        } else {
            Request request2 = new Request() { // from class: io.dcloud.common.adapter.util.PermissionUtil.1
                @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
                public void onDenied(String str3) {
                    try {
                        if (PermissionUtil.useRejectedCache.containsKey(str)) {
                            HashMap map2 = (HashMap) PermissionUtil.useRejectedCache.get(str);
                            if (map2 == null) {
                                map2 = new HashMap();
                            }
                            if (map2.containsKey(str3)) {
                                map2.put(str3, Integer.valueOf(((Integer) map2.get(str3)).intValue() + 1));
                            } else {
                                map2.put(str3, 1);
                            }
                            PermissionUtil.useRejectedCache.put(str3, map2);
                        } else {
                            HashMap map3 = new HashMap();
                            map3.put(str3, 1);
                            PermissionUtil.useRejectedCache.put(str, map3);
                        }
                    } catch (Exception unused) {
                    }
                    request.onDenied(str3);
                }

                @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
                public void onGranted(String str3) {
                    try {
                        if (PermissionUtil.useRejectedCache.containsKey(str)) {
                            ((HashMap) PermissionUtil.useRejectedCache.get(str)).remove(str3);
                        }
                    } catch (Exception unused) {
                    }
                    request.onGranted(str3);
                }
            };
            request2.isTriggerRequestEvent = true;
            useSystemPermission(activity, strConvert2SystemPermission, request2);
        }
    }

    private static int useStreamPermission(ShowDialogData showDialogData) {
        Activity activity = showDialogData.activity;
        String str = showDialogData.appid;
        String str2 = showDialogData.streamPerName;
        Request request = showDialogData.request;
        int iCheckStreamAppPermission = checkStreamAppPermission(activity, str, str2);
        if (iCheckStreamAppPermission == -1) {
            request.onDenied(str2);
            return iCheckStreamAppPermission;
        }
        if (iCheckStreamAppPermission == 0) {
            request.onGranted(str2);
            return iCheckStreamAppPermission;
        }
        if (iCheckStreamAppPermission != 1) {
            return iCheckStreamAppPermission;
        }
        showStreamAppPermissionDialog(showDialogData);
        return iCheckStreamAppPermission;
    }

    public static boolean useSystemPermission(Activity activity, String str, Request request) {
        boolean z = activity.getApplicationInfo().targetSdkVersion >= 23 && str != null && str.contains("android.permission") && Build.VERSION.SDK_INT >= 23;
        String strConvert2StreamPermission = convert2StreamPermission(str);
        if (!request.isTriggerRequestEvent) {
            PermissionControler.invokeUTSAndroidPermissionRequest(PermissionRequestAction.TYPE_REQUEST, new String[]{str});
        }
        request.isTriggerRequestEvent = true;
        if (!z) {
            request.onGranted(strConvert2StreamPermission);
            PermissionControler.invokeUTSAndroidPermissionRequest(PermissionRequestAction.TYPE_COMPLETE, new String[]{str});
            return true;
        }
        request.setRequestCode(getRequestCode());
        int iCheckSelfPermission = checkSelfPermission(activity, str, request.getAppName());
        if (iCheckSelfPermission != -1) {
            if (iCheckSelfPermission != 0) {
                return false;
            }
            request.onGranted(strConvert2StreamPermission);
            PermissionControler.invokeUTSAndroidPermissionRequest(PermissionRequestAction.TYPE_COMPLETE, new String[]{str});
            return true;
        }
        if (checkPermissionDenied(activity, str)) {
            request.onDenied(strConvert2StreamPermission);
            PermissionControler.invokeUTSAndroidPermissionRequest(PermissionRequestAction.TYPE_COMPLETE, new String[]{str});
            return true;
        }
        if (str.equals(Permission.ACCESS_COARSE_LOCATION)) {
            requestSystemPermissions(activity, new String[]{str, Permission.ACCESS_FINE_LOCATION}, request.getRequestCode(), request);
            return false;
        }
        if (str.equalsIgnoreCase(Permission.READ_MEDIA_IMAGES)) {
            requestSystemPermissions(activity, new String[]{str, Permission.READ_MEDIA_VIDEO, Permission.WRITE_EXTERNAL_STORAGE}, request.getRequestCode(), request);
            return false;
        }
        requestSystemPermissions(activity, new String[]{str}, request.getRequestCode(), request);
        return false;
    }

    public static void useSystemPermissions(Activity activity, String[] strArr, Request request) {
        boolean z = activity.getApplicationInfo().targetSdkVersion >= 23 && strArr != null && strArr.length > 0 && Build.VERSION.SDK_INT >= 23;
        ArrayList arrayList = new ArrayList(Arrays.asList(strArr));
        if (!request.isTriggerRequestEvent) {
            PermissionControler.invokeUTSAndroidPermissionRequest(PermissionRequestAction.TYPE_REQUEST, strArr);
        }
        if (!z) {
            for (int i = 0; i < arrayList.size(); i++) {
                request.onGranted(convert2StreamPermission((String) arrayList.get(i)));
            }
            PermissionControler.invokeUTSAndroidPermissionRequest(PermissionRequestAction.TYPE_COMPLETE, strArr);
            return;
        }
        request.setRequestCode(getRequestCode());
        ArrayList arrayList2 = new ArrayList();
        boolean z2 = alwaysDeniedPer == null;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            String strConvert2SystemPermission = convert2SystemPermission((String) arrayList.get(i2));
            if (checkSelfPermission(activity, strConvert2SystemPermission, request.getAppName()) == 0) {
                arrayList2.add(strConvert2SystemPermission);
                request.onGranted(convert2StreamPermission(strConvert2SystemPermission));
            } else if (checkPermissionDenied(activity, strConvert2SystemPermission) && !z2) {
                arrayList2.add(strConvert2SystemPermission);
                request.onDenied(convert2StreamPermission(strConvert2SystemPermission));
            }
        }
        PermissionControler.invokeUTSAndroidPermissionRequest(PermissionRequestAction.TYPE_COMPLETE, (String[]) arrayList2.toArray(new String[0]));
        if (arrayList2.size() > 0) {
            arrayList.removeAll(arrayList2);
        }
        if (arrayList.size() > 0) {
            requestSystemPermissions(activity, (String[]) arrayList.toArray(new String[0]), request.getRequestCode(), request);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    private static class ShowDialogData {
        Activity activity;
        String appName;
        String appid;
        boolean force;
        IApp mApp;
        Request request;
        String streamPerName;
        int tryTimes;

        ShowDialogData(Activity activity, IApp iApp, String str, String str2, String str3, Request request) {
            this(activity, iApp, str, str2, str3);
            setRequestHandler(request);
        }

        void setRequestHandler(Request request) {
            this.request = request;
        }

        ShowDialogData(Activity activity, IApp iApp, String str, String str2, String str3) {
            this.tryTimes = 0;
            this.activity = activity;
            this.mApp = iApp;
            this.streamPerName = str;
            this.appid = str2;
            this.appName = str3;
        }
    }

    private static boolean isMiuiRom(Activity activity) {
        String property = System.getProperty("http.agent");
        return !TextUtils.isEmpty(property) && property.toLowerCase(Locale.ENGLISH).contains("miui");
    }

    public static void requestSystemPermissions(Activity activity, String[] strArr, int i, Request request, boolean z) {
        if (!request.isTriggerRequestEvent) {
            PermissionControler.invokeUTSAndroidPermissionRequest(PermissionRequestAction.TYPE_REQUEST, strArr);
        }
        request.isTriggerRequestEvent = true;
        if (!caseVersion(activity) || strArr == null) {
            if (strArr != null) {
                try {
                    for (String str : strArr) {
                        request.onGranted(convert2StreamPermission(str));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            PermissionControler.invokeUTSAndroidPermissionRequest(PermissionRequestAction.TYPE_COMPLETE, strArr);
            return;
        }
        HashMap<Request, String[]> map = new HashMap<>();
        map.put(request, strArr);
        sRequestCallBacks.put(Integer.valueOf(i), map);
        ArrayList arrayList = new ArrayList();
        if (z) {
            for (String str2 : strArr) {
                if (checkPermissionDenied(activity, str2)) {
                    if (request != null) {
                        request.onDenied(str2);
                        PermissionControler.invokeUTSAndroidPermissionRequest(PermissionRequestAction.TYPE_COMPLETE, new String[]{str2});
                        return;
                    }
                    return;
                }
                arrayList.add(str2);
            }
            if (arrayList.size() > 0) {
                strArr = (String[]) arrayList.toArray(new String[0]);
            }
        }
        requestPermissions(activity, strArr, i, false);
    }

    public static void requestPermissions(Activity activity, String[] strArr, int i, boolean z) {
        PermissionControler.requestPermissions(activity, strArr, i);
    }

    private static void showStreamAppPermissionDialog(final ShowDialogData showDialogData) {
        final DCloudAlertDialog dCloudAlertDialog;
        int i;
        CheckBox checkBox;
        String string;
        final Activity activity = showDialogData.activity;
        final String str = showDialogData.appid;
        String str2 = showDialogData.appName;
        final String str3 = showDialogData.streamPerName;
        final Request request = showDialogData.request;
        Logger.e("Permission", "showStreamAppPermissionDialog streamPerName=" + str3 + ";count=" + sUseStreamAppPermissionDialogCount);
        PermissionData permissionData = sPermissionData.get(str3);
        if (permissionData == null) {
            request.onGranted(str3);
            return;
        }
        if (continueShowStreamAppPermissionDialog(showDialogData)) {
            int i2 = showDialogData.tryTimes + 1;
            showDialogData.tryTimes = i2;
            boolean z = i2 == 1;
            DCloudAlertDialog dCloudAlertDialogInitDialogTheme = DialogUtil.initDialogTheme(activity, true);
            if (activity != null && (activity instanceof WebAppActivity)) {
                ((WebAppActivity) activity).recordDialog(dCloudAlertDialogInitDialogTheme);
            }
            dCloudAlertDialogInitDialogTheme.setCanceledOnTouchOutside(false);
            if (z) {
                if (Build.BRAND.equalsIgnoreCase(MobilePhoneModel.QiKU)) {
                    dCloudAlertDialogInitDialogTheme.setMessage(activity.getString(R.string.dcloud_permissions_short_cut_tips));
                } else if (TextUtils.isEmpty(str2)) {
                    dCloudAlertDialogInitDialogTheme.setMessage(StringUtil.format(activity.getString(permissionData.messageId), "App"));
                } else {
                    dCloudAlertDialogInitDialogTheme.setMessage(StringUtil.format(activity.getString(permissionData.messageId), str2));
                }
            } else {
                if (Build.BRAND.equalsIgnoreCase(MobilePhoneModel.QiKU)) {
                    dCloudAlertDialogInitDialogTheme.setMessage(activity.getString(R.string.dcloud_permissions_short_cut_tips2));
                }
                if (PMS_LOCATION.equalsIgnoreCase(str3)) {
                    dCloudAlertDialogInitDialogTheme.setMessage(activity.getString(R.string.dcloud_permissions_geo_retry_tips));
                } else {
                    dCloudAlertDialogInitDialogTheme.setMessage(StringUtil.format(activity.getString(R.string.dcloud_permissions_retry_tips), activity.getPackageManager().getApplicationLabel(activity.getApplicationInfo())));
                }
            }
            if (permissionData.checkbox == -1 || !z) {
                dCloudAlertDialog = dCloudAlertDialogInitDialogTheme;
                i = -1;
                checkBox = null;
            } else {
                checkBox = new CheckBox(activity);
                checkBox.setText(R.string.dcloud_permissions_checkbox_close_tips);
                checkBox.setTextColor(-65536);
                checkBox.setChecked(permissionData.checkbox == 1);
                int deivceSuitablePixel = getDeivceSuitablePixel(activity, 20);
                i = -1;
                dCloudAlertDialogInitDialogTheme.setView(checkBox, deivceSuitablePixel, deivceSuitablePixel, 0, 0);
                dCloudAlertDialog = dCloudAlertDialogInitDialogTheme;
            }
            final ISysEventListener iSysEventListener = new ISysEventListener() { // from class: io.dcloud.common.adapter.util.PermissionUtil.3
                @Override // io.dcloud.common.DHInterface.ISysEventListener
                public boolean onExecute(ISysEventListener.SysEventType sysEventType, Object obj) {
                    Logger.e("Permission", "unregisterSysEventListener registerSysEventListener pEventType=" + sysEventType);
                    if (sysEventType != ISysEventListener.SysEventType.onWebAppReStart) {
                        return false;
                    }
                    dCloudAlertDialog.dismiss();
                    PermissionUtil.unregisterWebAppReStartEvent(showDialogData.mApp, this);
                    try {
                        request.onDenied(str3);
                        PermissionUtil.showStreamAppPermissionDialog();
                        return false;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            };
            if (showDialogData.mApp != null) {
                Logger.e("Permission", "showStreamAppPermissionDialog registerSysEventListener");
                showDialogData.mApp.registerSysEventListener(iSysEventListener, ISysEventListener.SysEventType.onWebAppReStart);
            }
            final CheckBox checkBox2 = checkBox;
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: io.dcloud.common.adapter.util.PermissionUtil.4
                /* JADX WARN: Multi-variable type inference failed */
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i3) {
                    dCloudAlertDialog.dismiss();
                    IApp iApp = showDialogData.mApp;
                    if (iApp != null) {
                        PermissionUtil.unregisterWebAppReStartEvent(iApp, iSysEventListener);
                    }
                    if (i3 == -1) {
                        PermissionUtil.putStreamAppPermission(activity, str, str3, !(checkBox2 == null ? 1 : r4.isChecked()));
                        request.onGranted(str3);
                    } else if (i3 == -2) {
                        CheckBox checkBox3 = checkBox2;
                        if (checkBox3 != null && checkBox3.isChecked()) {
                            PermissionUtil.putStreamAppPermission(activity, str, str3, -1);
                        }
                        request.onDenied(str3);
                    }
                    PermissionUtil.showStreamAppPermissionDialog();
                }
            };
            String string2 = activity.getString(z ? R.string.dcloud_common_no_allow : R.string.dcloud_common_cancel);
            if (z) {
                string = activity.getString(R.string.dcloud_common_allow);
            } else if (!Build.BRAND.equalsIgnoreCase(MobilePhoneModel.QiKU) && !PMS_LOCATION.equalsIgnoreCase(str3)) {
                string = activity.getString(R.string.dcloud_permissions_reauthorization);
            } else {
                string = activity.getString(R.string.dcloud_permissions_reopened);
            }
            dCloudAlertDialog.setButton(-2, string2, onClickListener);
            dCloudAlertDialog.setButton(i, string, onClickListener);
            dCloudAlertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: io.dcloud.common.adapter.util.PermissionUtil.5
                @Override // android.content.DialogInterface.OnKeyListener
                public boolean onKey(DialogInterface dialogInterface, int i3, KeyEvent keyEvent) {
                    if (keyEvent.getAction() != 1 || i3 != 4) {
                        return false;
                    }
                    dCloudAlertDialog.dismiss();
                    IApp iApp = showDialogData.mApp;
                    if (iApp != null) {
                        PermissionUtil.unregisterWebAppReStartEvent(iApp, iSysEventListener);
                    }
                    request.onDenied(str3);
                    PermissionUtil.showStreamAppPermissionDialog();
                    return true;
                }
            });
            try {
                dCloudAlertDialog.show();
                dCloudAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: io.dcloud.common.adapter.util.PermissionUtil.6
                    @Override // android.content.DialogInterface.OnDismissListener
                    public void onDismiss(DialogInterface dialogInterface) {
                        Activity activity2 = activity;
                        if (activity2 == null || !(activity2 instanceof WebAppActivity)) {
                            return;
                        }
                        ((WebAppActivity) activity2).removeFromRecord(dCloudAlertDialog);
                    }
                });
            } catch (Exception e) {
                Logger.e("ian", "try dialog");
                e.printStackTrace();
            }
        }
    }

    public static int checkSelfPermission(Activity activity, String str) {
        if (activity != null && str != null) {
            Object objInvokeMethod = PlatformUtil.invokeMethod(activity.getClass().getName(), "checkSelfPermission", activity, new Class[]{str.getClass()}, new Object[]{str});
            if (objInvokeMethod instanceof Integer) {
                return ((Integer) objInvokeMethod).intValue();
            }
        }
        return 0;
    }

    private static boolean isMiui(Activity activity, String str, Request request) {
        String property = System.getProperty("http.agent");
        if (TextUtils.isEmpty(property)) {
            if (!Build.BRAND.contains("Xiaomi")) {
                return false;
            }
        } else if (!property.toLowerCase(Locale.ENGLISH).contains("miui")) {
            return false;
        }
        int requestCode = getRequestCode();
        Intent intent = new Intent();
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
        intent.putExtra("extra_pkgname", activity.getPackageName());
        try {
            activity.startActivityForResult(intent, requestCode);
            saveCallabckData(activity, str, request, requestCode);
            return true;
        } catch (ActivityNotFoundException unused) {
            intent.setComponent(null);
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            try {
                activity.startActivityForResult(intent, requestCode);
                saveCallabckData(activity, str, request, requestCode);
            } catch (ActivityNotFoundException unused2) {
                intent.setComponent(null);
                intent.setPackage("com.android.Setting");
                activity.startActivityForResult(intent, requestCode);
                saveCallabckData(activity, str, request, requestCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    static class SafeCenter {
        private static ArrayList<Item> datas = new ArrayList<>();

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        static class Item {
            String action;
            String clsName;
            String extParamName;
            String pname;

            Item(String str, String str2, String str3, String str4) {
                this.pname = str;
                this.clsName = str2;
                this.extParamName = str3;
                this.action = str4;
            }
        }

        SafeCenter() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void goSafeCenter(Activity activity) {
            init(activity);
            PackageManager packageManager = activity.getPackageManager();
            for (int i = 0; i < datas.size(); i++) {
                Intent intent = new Intent();
                Item item = datas.get(i);
                try {
                    if (packageManager.getPackageInfo(item.pname, 0) != null) {
                        if (!TextUtils.isEmpty(item.clsName)) {
                            intent.setClassName(item.pname, item.clsName);
                        } else if (!TextUtils.isEmpty(item.pname)) {
                            intent.setPackage(item.pname);
                        }
                        if (!TextUtils.isEmpty(item.action)) {
                            intent.setAction(item.action);
                        }
                        if (!TextUtils.isEmpty(item.extParamName)) {
                            intent.putExtra(item.extParamName, activity.getPackageName());
                        }
                        try {
                            intent.setFlags(268435456);
                            activity.startActivity(intent);
                            Logger.i("Permission", "successful " + Build.MODEL + "intent=" + intent);
                        } catch (ActivityNotFoundException e) {
                            Logger.e("Permission", "ActivityNotFoundException =" + e);
                            e.printStackTrace();
                        } catch (Exception e2) {
                            Logger.e("Permission", "Exception =" + e2);
                        }
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                }
            }
        }

        static void init(Context context) {
            if (datas.isEmpty()) {
                try {
                    JSONArray jSONArray = new JSONArray(new String(IOUtil.toString(new FileInputStream(new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/temp.j")))));
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i);
                        String strOptString = jSONObjectOptJSONObject.optString(ContextChain.TAG_PRODUCT);
                        if (!TextUtils.isEmpty(strOptString)) {
                            datas.add(new Item(strOptString, jSONObjectOptJSONObject.optString("c"), jSONObjectOptJSONObject.optString("e"), jSONObjectOptJSONObject.optString("a")));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (datas.isEmpty()) {
                    datas.add(new Item("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity", "extra_pkgname", null));
                    datas.add(new Item("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity", "extra_pkgname", null));
                    datas.add(new Item("com.meizu.safe", "com.meizu.safe.security.AppSecActivity", "packageName", null));
                    datas.add(new Item("com.aliyun.mobile.permission", "com.aliyun.mobile.permission.ExternalAppDetailActivity", "packageName", null));
                    datas.add(new Item("com.iqoo.secure", "com.iqoo.secure.MainActivity", "packageName", null));
                    datas.add(new Item("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity", "package", null));
                    datas.add(new Item("com.mediatek.security", "com.mediatek.security.ui.PermissionControlPageActivity", "package", null));
                    datas.add(new Item("com.yulong.android.launcher3", "com.yulong.android.launcher3.LauncherSettingsActivity", "package", null));
                    datas.add(new Item("com.android.settings", "com.android.settings.Settings$ManageApplicationsActivity", "package", null));
                    datas.add(new Item(null, null, "package", "android.settings.MANAGE_APPLICATIONS_SETTINGS"));
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean goSafeCenter(Activity activity, String str, Request request) {
            boolean z;
            int requestCode = PermissionUtil.getRequestCode();
            if (PermissionUtil.PMS_LOCATION.equalsIgnoreCase(str)) {
                LocationManager locationManager = (LocationManager) activity.getSystemService("location");
                boolean zIsProviderEnabled = locationManager.isProviderEnabled("gps");
                boolean zIsProviderEnabled2 = locationManager.isProviderEnabled("network");
                if (!zIsProviderEnabled && !zIsProviderEnabled2) {
                    Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
                    try {
                        activity.startActivity(intent);
                        PermissionUtil.saveCallabckData(activity, str, request, requestCode);
                        Logger.i("Permission", "successful " + Build.MODEL + "intent=" + intent);
                        return true;
                    } catch (ActivityNotFoundException e) {
                        Logger.e("Permission", "ActivityNotFoundException =" + e);
                        e.printStackTrace();
                    } catch (Exception e2) {
                        Logger.e("Permission", "Exception =" + e2);
                    }
                }
            }
            init(activity);
            PackageManager packageManager = activity.getPackageManager();
            for (int i = 0; i < datas.size(); i++) {
                Intent intent2 = new Intent();
                Item item = datas.get(i);
                if (packageManager.getPackageInfo(item.pname, 0) != null) {
                    if (!TextUtils.isEmpty(item.clsName)) {
                        z = true;
                        intent2.setClassName(item.pname, item.clsName);
                    } else {
                        z = true;
                        if (!TextUtils.isEmpty(item.pname)) {
                            intent2.setPackage(item.pname);
                        }
                    }
                    if (!TextUtils.isEmpty(item.action)) {
                        intent2.setAction(item.action);
                    }
                    if (!TextUtils.isEmpty(item.extParamName)) {
                        intent2.putExtra(item.extParamName, activity.getPackageName());
                    }
                    try {
                        activity.startActivityForResult(intent2, requestCode);
                        PermissionUtil.saveCallabckData(activity, str, request, requestCode);
                        Logger.i("Permission", "successful " + Build.MODEL + "intent=" + intent2);
                        return z;
                    } catch (ActivityNotFoundException e3) {
                        Logger.e("Permission", "ActivityNotFoundException =" + e3);
                        e3.printStackTrace();
                    } catch (Exception e4) {
                        Logger.e("Permission", "Exception =" + e4);
                    }
                }
            }
            return true;
        }
    }

    public static void usePermission(Activity activity, String str, final Request request) {
        useSystemPermission(activity, convert2SystemPermission(str), new Request() { // from class: io.dcloud.common.adapter.util.PermissionUtil.2
            @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
            public void onDenied(String str2) {
                request.onDenied(str2);
            }

            @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
            public void onGranted(String str2) {
                request.onGranted(str2);
            }
        });
    }
}
