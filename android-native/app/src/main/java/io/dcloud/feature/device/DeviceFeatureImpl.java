package io.dcloud.feature.device;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.PowerManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import com.hjq.permissions.Permission;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IFeature;
import io.dcloud.common.DHInterface.ISysEventListener;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.PermissionUtil;
import io.dcloud.common.constant.DOMException;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.NetworkTypeUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import io.dcloud.common.util.TelephonyUtil;
import io.dcloud.feature.internal.sdk.SDK;
import io.dcloud.p.e3;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class DeviceFeatureImpl implements IFeature, ISysEventListener {
    static int e = 255;
    private Context c;
    private PowerManager.WakeLock a = null;
    private boolean b = false;
    int d = -1;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a extends PermissionUtil.StreamPermissionRequest {
        final /* synthetic */ IWebview a;
        final /* synthetic */ String[] b;
        final /* synthetic */ boolean c;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(IApp iApp, IWebview iWebview, String[] strArr, boolean z) {
            super(iApp);
            this.a = iWebview;
            this.b = strArr;
            this.c = z;
        }

        @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
        public void onDenied(String str) {
        }

        @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
        public void onGranted(String str) {
            DeviceFeatureImpl.this.a(this.a, this.b[0], this.c);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b extends PermissionUtil.Request {
        final /* synthetic */ IWebview a;
        final /* synthetic */ String b;

        b(IWebview iWebview, String str) {
            this.a = iWebview;
            this.b = str;
        }

        @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
        public void onDenied(String str) {
            Deprecated_JSUtil.execCallback(this.a, this.b, "{'imei':'','imsi':[],'uuid':'" + TelephonyUtil.getIMEI(this.a.getContext(), false) + "'}", JSUtil.ERROR, true, false);
        }

        @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
        public void onGranted(String str) {
            DeviceInfo.updateIMEI();
            DeviceInfo.getUpdateIMSI();
            Deprecated_JSUtil.execCallback(this.a, this.b, StringUtil.format("{'imei':'%s','imsi':['%s'],'uuid':'%s'}", DeviceInfo.sIMEI, DeviceInfo.sIMSI, TelephonyUtil.getIMEI(this.a.getContext(), false)), JSUtil.OK, true, false);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c implements e3.b {
        final /* synthetic */ IWebview a;
        final /* synthetic */ String b;
        final /* synthetic */ String c;

        c(IWebview iWebview, String str, String str2) {
            this.a = iWebview;
            this.b = str;
            this.c = str2;
        }

        @Override // io.dcloud.p.e3.b
        public void a(String str, boolean z) {
            if (z) {
                DeviceFeatureImpl.this.a(this.a, this.b, this.c, str);
            } else {
                Deprecated_JSUtil.execCallback(this.a, this.c, DOMException.toJSON(401, "not support"), JSUtil.ERROR, true, false);
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class d implements Runnable {
        final /* synthetic */ IWebview a;
        final /* synthetic */ float b;

        d(IWebview iWebview, float f) {
            this.a = iWebview;
            this.b = f;
        }

        @Override // java.lang.Runnable
        public void run() {
            DeviceFeatureImpl.this.a(this.a, this.b);
        }
    }

    static {
        try {
            Resources system = Resources.getSystem();
            int identifier = system.getIdentifier("config_screenBrightnessSettingMaximum", "integer", WXEnvironment.OS);
            if (identifier != 0) {
                e = system.getInteger(identifier);
            }
        } catch (Exception unused) {
            e = 255;
        }
    }

    private void b(IWebview iWebview, float f) {
        iWebview.obtainWindowView().post(new d(iWebview, f));
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public void dispose(String str) {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // io.dcloud.common.DHInterface.IFeature
    public String execute(IWebview iWebview, String str, String[] strArr) throws IllegalAccessException, InterruptedException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        int i;
        int i2;
        long j;
        str.getClass();
        str.hashCode();
        i = 1;
        switch (str) {
            case "setWakelock":
                if (PdrUtil.parseBoolean(strArr[0], false, false)) {
                    this.a.acquire();
                } else {
                    this.a.release();
                }
                return null;
            case "d.resolutionWidth":
                return Deprecated_JSUtil.wrapJsVar(String.valueOf(iWebview.obtainApp().getInt(0) / iWebview.getScale()), false);
            case "getDCloudID":
            case "getDeviceId":
                AppRuntime.checkPrivacyComplianceAndPrompt(iWebview.getContext(), "Device-" + str);
                return JSUtil.wrapJsVar(AppRuntime.getDCloudDeviceID(iWebview.getContext()));
            case "s.resolutionHeight":
                return Deprecated_JSUtil.wrapJsVar(String.valueOf(iWebview.obtainApp().getInt(2) / iWebview.getScale()), false);
            case "getCurrentAPN":
                String currentAPN = DeviceInfo.getCurrentAPN();
                if (TextUtils.isEmpty(currentAPN)) {
                    return null;
                }
                try {
                    return JSUtil.wrapJsVar(new JSONObject("{name:" + currentAPN + Operators.BLOCK_END_STR));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                    return null;
                }
            case "isWakelock":
                return Deprecated_JSUtil.wrapJsVar(String.valueOf(this.a.isHeld()), false);
            case "getAAID":
            case "getOAID":
            case "getVAID":
                AppRuntime.checkPrivacyComplianceAndPrompt(iWebview.getContext(), "Device-" + str);
                String str2 = strArr[0];
                if (!SDK.isUniMPSDK() || TextUtils.isEmpty(SDK.customOAID)) {
                    String str3 = DeviceInfo.oaids;
                    if (str3 == null || str3.equalsIgnoreCase(Operators.OR)) {
                        try {
                            Method declaredMethod = Class.forName("com.bun.miitmdid.core.JLibrary").getDeclaredMethod("InitEntry", Context.class);
                            if (declaredMethod != null) {
                                declaredMethod.setAccessible(true);
                                declaredMethod.invoke(null, iWebview.getContext());
                            }
                        } catch (Exception unused) {
                        }
                        if (!new e3(new c(iWebview, str, str2)).b(iWebview.getActivity())) {
                            Deprecated_JSUtil.execCallback(iWebview, str2, DOMException.toJSON(401, "not support"), JSUtil.ERROR, true, false);
                        }
                    } else {
                        a(iWebview, str, str2, DeviceInfo.oaids);
                    }
                } else {
                    Deprecated_JSUtil.execCallback(iWebview, str2, DOMException.toJSON(401, "not support"), JSUtil.ERROR, true, false);
                }
                return null;
            case "getInfo":
                String str4 = strArr[0];
                AppRuntime.checkPrivacyComplianceAndPrompt(iWebview.getContext(), "Device-" + str);
                PermissionUtil.usePermission(iWebview.getActivity(), IFeature.F_DEVICE, Permission.READ_PHONE_STATE, 2, new b(iWebview, str4));
                return null;
            case "getCurrentSize":
                return Deprecated_JSUtil.wrapJsVar(a(iWebview.obtainApp(), iWebview.getScale()), false);
            case "getCurrentType":
                return DeviceInfo.getNetWorkType(iWebview.getContext());
            case "beep":
                ToneGenerator toneGenerator = new ToneGenerator(5, 100);
                try {
                    int i3 = Integer.parseInt(strArr[0]);
                    if (i3 > 0) {
                        i = i3;
                    }
                } catch (NumberFormatException e3) {
                    e3.printStackTrace();
                }
                for (i2 = 0; i2 < i; i2++) {
                    toneGenerator.startTone(88);
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e4) {
                        e4.printStackTrace();
                    }
                }
                return null;
            case "dial":
                AppRuntime.checkPrivacyComplianceAndPrompt(iWebview.getContext(), "Device-" + str);
                PermissionUtil.usePermission(iWebview.getActivity(), IFeature.F_DEVICE, PermissionUtil.PMS_PHONE, 2, new a(iWebview.obtainApp(), iWebview, strArr, PdrUtil.parseBoolean(strArr[1], true, false)));
                return null;
            case "getBrightness":
                return Deprecated_JSUtil.wrapJsVar(String.valueOf(a(iWebview.getActivity()) / e), false);
            case "__isWakelockNative__":
                return this.a.isHeld() + "";
            case "vibrate":
                try {
                    j = Long.parseLong(strArr[0]);
                } catch (NumberFormatException e5) {
                    e5.printStackTrace();
                }
                if (j <= 0) {
                    j = 500;
                }
                ((Vibrator) this.c.getSystemService("vibrator")).vibrate(j);
                return null;
            case "d.resolutionHeight":
                return Deprecated_JSUtil.wrapJsVar(String.valueOf(iWebview.obtainApp().getInt(1) / iWebview.getScale()), false);
            case "unlockOrientation":
                iWebview.obtainApp().setRequestedOrientation((String) null);
                return null;
            case "isSetProxy":
                return JSUtil.wrapJsVar(NetworkTypeUtil.isWifiProxy(iWebview.getContext()));
            case "setVolume":
                float f = Float.parseFloat(strArr[0]);
                AudioManager audioManager = (AudioManager) iWebview.getContext().getSystemService("audio");
                int iA = a(f);
                audioManager.setStreamVolume(4, iA, 8);
                audioManager.setStreamVolume(8, iA, 8);
                audioManager.setStreamVolume(3, iA, 8);
                audioManager.setStreamVolume(5, iA, 8);
                audioManager.setStreamVolume(2, iA, 8);
                audioManager.setStreamVolume(1, iA, 8);
                audioManager.setStreamVolume(0, iA, 8);
                return null;
            case "getVolume":
                return Deprecated_JSUtil.wrapJsVar(String.valueOf(((AudioManager) iWebview.getContext().getSystemService("audio")).getStreamVolume(3) / this.d), false);
            case "setBrightness":
                b(iWebview, Float.parseFloat(strArr[0]));
                return null;
            case "s.resolutionWidth":
                return Deprecated_JSUtil.wrapJsVar(String.valueOf(iWebview.obtainApp().getInt(0) / iWebview.getScale()), false);
            case "lockOrientation":
                iWebview.obtainApp().setRequestedOrientation(strArr[0]);
                return null;
            default:
                return null;
        }
    }

    @Override // io.dcloud.common.DHInterface.IFeature
    public void init(AbsMgr absMgr, String str) {
        Context context = absMgr.getContext();
        this.c = context;
        PowerManager.WakeLock wakeLockNewWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(10, "My Lock");
        this.a = wakeLockNewWakeLock;
        wakeLockNewWakeLock.setReferenceCounted(false);
        this.d = ((AudioManager) absMgr.getContext().getSystemService("audio")).getStreamMaxVolume(3);
    }

    @Override // io.dcloud.common.DHInterface.ISysEventListener
    public boolean onExecute(ISysEventListener.SysEventType sysEventType, Object obj) {
        return false;
    }

    private String a(IApp iApp, float f) throws JSONException {
        int i = iApp.getInt(0);
        int i2 = iApp.getInt(2);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("width", i);
            jSONObject.put("height", i2);
            jSONObject.put("resolutionWidth", i / f);
            jSONObject.put("resolutionHeight", i2 / f);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(IWebview iWebview, String str, String str2, String str3) {
        String[] strArrSplit = str3.split("\\|");
        try {
            int iHashCode = str.hashCode();
            if (iHashCode == -75727471) {
                if (str.equals("getAAID")) {
                    JSUtil.execCallback(iWebview, str2, new JSONObject("{'aaid':'" + (strArrSplit.length > 2 ? strArrSplit[2] : "") + "'}"), JSUtil.OK, false);
                    return;
                }
                return;
            }
            if (iHashCode == -75310397) {
                if (str.equals("getOAID")) {
                    JSUtil.execCallback(iWebview, str2, new JSONObject("{'oaid':'" + (strArrSplit.length > 0 ? strArrSplit[0] : "") + "'}"), JSUtil.OK, false);
                    return;
                }
                return;
            }
            if (iHashCode == -75101860 && str.equals("getVAID")) {
                JSUtil.execCallback(iWebview, str2, new JSONObject("{'vaid':'" + (strArrSplit.length > 1 ? strArrSplit[1] : "") + "'}"), JSUtil.OK, false);
            }
        } catch (Exception e2) {
            Deprecated_JSUtil.execCallback(iWebview, str2, DOMException.toJSON(401, e2.getMessage()), JSUtil.ERROR, true, false);
        }
    }

    private int a(Activity activity) throws Resources.NotFoundException {
        int i;
        float f = activity.getWindow().getAttributes().screenBrightness;
        if (f < 0.0f) {
            ContentResolver contentResolver = activity.getContentResolver();
            try {
                int integer = activity.getResources().getInteger(activity.getResources().getIdentifier("config_screenBrightnessSettingMaximum", "integer", WXEnvironment.OS));
                int i2 = Settings.System.getInt(contentResolver, "screen_brightness", 125);
                if (integer > 255) {
                    f = i2 / integer;
                    if (f > 0.0f) {
                        i = e;
                    }
                } else if (i2 >= 0) {
                    return i2;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return 125;
        }
        i = e;
        return (int) (f * i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(IWebview iWebview, float f) {
        Window window = iWebview.getActivity().getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (f <= 1.0f && f > 0.0f) {
            attributes.screenBrightness = f;
        } else {
            attributes.screenBrightness = -1.0f;
        }
        if (f == -1.0f) {
            attributes.screenBrightness = -1.0f;
            window.setAttributes(attributes);
        } else {
            if (f > 1.0f || f <= 0.0f) {
                return;
            }
            attributes.screenBrightness = f;
            window.setAttributes(attributes);
        }
    }

    private int a(float f) {
        if (f > 1.0f || f < 0.0f) {
            return 0;
        }
        return (int) (f * this.d);
    }

    protected void a(IWebview iWebview, String str, boolean z) {
        String str2;
        Uri uri = Uri.parse("tel:" + str);
        if (z) {
            str2 = "android.intent.action.DIAL";
        } else {
            str2 = "android.intent.action.CALL";
        }
        iWebview.getActivity().startActivity(new Intent(str2, uri));
    }
}
