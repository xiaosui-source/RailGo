package io.dcloud.p;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.facebook.common.callercontext.ContextChain;
import com.taobao.weex.common.WXConfig;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.ui.component.WXBasicComponentType;
import com.taobao.weex.ui.component.WXComponent;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.DataInterface;
import io.dcloud.common.util.ADUtils;
import io.dcloud.common.util.AESUtil;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.CreateShortResultReceiver;
import io.dcloud.common.util.IOUtil;
import io.dcloud.common.util.Md5Utils;
import io.dcloud.common.util.NetTool;
import io.dcloud.common.util.NetworkTypeUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.TelephonyUtil;
import io.dcloud.common.util.ZipUtils;
import io.dcloud.common.util.hostpicker.HostPicker;
import io.dcloud.feature.internal.sdk.SDK;
import io.dcloud.sdk.core.util.Const;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class d0 {

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements HostPicker.HostPickCallback {
        final /* synthetic */ String a;
        final /* synthetic */ HashMap b;

        a(String str, HashMap map) {
            this.a = str;
            this.b = map;
        }

        @Override // io.dcloud.common.util.hostpicker.HostPicker.HostPickCallback
        public boolean doRequest(HostPicker.Host host) {
            JSONObject jSONObject;
            byte[] bArrHttpPost = NetTool.httpPost(host.getRealHost(), this.a, (HashMap<String, String>) this.b, false);
            if (bArrHttpPost == null) {
                return false;
            }
            try {
                jSONObject = new JSONObject(new String(bArrHttpPost, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                jSONObject = null;
                Logger.d("commitTid", jSONObject);
                return true;
            } catch (JSONException e2) {
                e2.printStackTrace();
                jSONObject = null;
                Logger.d("commitTid", jSONObject);
                return true;
            }
            Logger.d("commitTid", jSONObject);
            return true;
        }

        @Override // io.dcloud.common.util.hostpicker.HostPicker.HostPickCallback
        public void onNoOnePicked() {
        }

        @Override // io.dcloud.common.util.hostpicker.HostPicker.HostPickCallback
        public void onOneSelected(HostPicker.Host host) {
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements HostPicker.HostPickCallback {
        final /* synthetic */ String a;
        final /* synthetic */ HashMap b;

        b(String str, HashMap map) {
            this.a = str;
            this.b = map;
        }

        @Override // io.dcloud.common.util.hostpicker.HostPicker.HostPickCallback
        public boolean doRequest(HostPicker.Host host) {
            JSONObject jSONObject;
            byte[] bArrHttpPost = NetTool.httpPost(host.getRealHost(), this.a, (HashMap<String, String>) this.b, false);
            if (bArrHttpPost == null) {
                return false;
            }
            try {
                jSONObject = new JSONObject(new String(bArrHttpPost, StandardCharsets.UTF_8));
            } catch (JSONException unused) {
                jSONObject = null;
            }
            Logger.d("commitTid", jSONObject);
            return true;
        }

        @Override // io.dcloud.common.util.hostpicker.HostPicker.HostPickCallback
        public void onNoOnePicked() {
        }

        @Override // io.dcloud.common.util.hostpicker.HostPicker.HostPickCallback
        public void onOneSelected(HostPicker.Host host) {
        }
    }

    public static HashMap a(IApp iApp, SharedPreferences sharedPreferences) throws JSONException {
        IApp iApp2;
        HashMap map = new HashMap();
        try {
            Activity activity = iApp.getActivity();
            boolean zHasPrivacyForNotShown = AppRuntime.hasPrivacyForNotShown(activity);
            String metaValue = null;
            map.putAll(DataInterface.getStartupUrlBaseData(activity, iApp.obtainAppId(), BaseInfo.getLaunchType(activity.getIntent()), null, zHasPrivacyForNotShown));
            map.put("st", Long.valueOf(BaseInfo.run5appEndTime));
            map.put("pn", activity.getPackageName());
            map.put(CreateShortResultReceiver.KEY_VERSIONNAME, iApp.obtainAppVersionName());
            map.put("pv", AndroidResources.versionName);
            map.put("uat", Integer.valueOf(BaseInfo.isUniAppAppid(iApp) ? 1 : 0));
            if (SDK.isUniMPSDK()) {
                try {
                    map.put("name", activity.getApplicationInfo().loadLabel(activity.getPackageManager()));
                } catch (Exception unused) {
                    map.put("name", iApp.obtainAppName());
                }
            } else {
                map.put("name", iApp.obtainAppName());
            }
            try {
                map.put("pname", activity.getApplicationInfo().loadLabel(activity.getPackageManager()));
            } catch (Exception unused2) {
            }
            map.put("it", Integer.valueOf(SDK.isUniMPSDK() ? 1 : 0));
            if (SDK.isUniMPSDK()) {
                boolean zCheckClass = PlatformUtil.checkClass(iApp.getConfusionMgr().getGDTClassName());
                boolean zCheckClass2 = PlatformUtil.checkClass(iApp.getConfusionMgr().getCSJClassName());
                boolean zCheckClass3 = PlatformUtil.checkClass(iApp.getConfusionMgr().getKSClassName());
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                String str = "1";
                jSONObject2.put("r", zCheckClass2 ? "1" : WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
                jSONObject.put(Const.TYPE_CSJ, jSONObject2);
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("r", zCheckClass ? "1" : WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
                jSONObject.put(Const.TYPE_GDT, jSONObject3);
                JSONObject jSONObject4 = new JSONObject();
                if (!zCheckClass3) {
                    str = WXInstanceApm.VALUE_ERROR_CODE_DEFAULT;
                }
                jSONObject4.put("r", str);
                jSONObject.put(Const.TYPE_KS, jSONObject4);
                map.put("cad", jSONObject.toString());
            } else {
                map.put("psdk", 0);
            }
            String bundleData = SP.getBundleData(activity, "pdr", SP.REPORT_UNI_VERIFY_GYUID);
            if (!TextUtils.isEmpty(bundleData)) {
                map.put("uvs", bundleData);
            }
            if (PdrUtil.isSupportOaid()) {
                String str2 = DeviceInfo.oaids;
                if (str2 == null) {
                    str2 = Operators.OR;
                }
                map.put("oaid", str2);
            }
            if (BaseInfo.isUniAppAppid(iApp)) {
                iApp2 = iApp;
                a(iApp2, map);
            } else {
                iApp2 = iApp;
            }
            map.put("dcid", AppRuntime.getDCloudDeviceID(activity));
            if (TextUtils.isEmpty(BaseInfo.sChannel)) {
                try {
                    metaValue = AndroidResources.getMetaValue("DCLOUD_STREAMAPP_CHANNEL");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (TextUtils.isEmpty(metaValue)) {
                    map.put("mc", "");
                } else {
                    map.put("mc", metaValue);
                }
            } else {
                map.put("mc", BaseInfo.sChannel);
            }
            try {
                String bundleData2 = SP.getBundleData(iApp2.getActivity(), "pdr", "packdata");
                if (!TextUtils.isEmpty(bundleData2)) {
                    map.put("jg", bundleData2);
                    SP.setBundleData(iApp2.getActivity(), "pdr", "packdata", "");
                }
                o3.a(iApp2.getActivity());
            } catch (Exception unused3) {
            }
            if (!zHasPrivacyForNotShown) {
                if ((System.currentTimeMillis() - sharedPreferences.getLong(AbsoluteConst.COMMIT_APP_LIST_TIME, 0L)) / 100000 >= 26000 && !TextUtils.isEmpty(sharedPreferences.getString(AbsoluteConst.GEO_DATA, ""))) {
                    map.put("pos", sharedPreferences.getString(AbsoluteConst.GEO_DATA, ""));
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return map;
    }

    private static Map b(Context context, String str, String str2, int i, String str3, HashMap map) throws UnsupportedEncodingException {
        String strEncode;
        String str4;
        String metaValue;
        try {
            strEncode = URLEncoder.encode(Build.MODEL, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            strEncode = "";
        }
        try {
            str4 = DeviceInfo.sApplicationContext.getPackageManager().getPackageInfo(DeviceInfo.sApplicationContext.getPackageName(), 0).versionName;
        } catch (Exception e2) {
            e2.printStackTrace();
            str4 = "";
        }
        String imei = TelephonyUtil.getIMEI(context, true, true);
        HashMap map2 = new HashMap();
        map2.put(ContextChain.TAG_PRODUCT, "a");
        map2.put("appid", str);
        map2.put(CreateShortResultReceiver.KEY_VERSIONNAME, str4);
        map2.put("at", Integer.valueOf(i));
        map2.put(WXConfig.os, Integer.valueOf(Build.VERSION.SDK_INT));
        map2.put("adpid", str3);
        if (imei.endsWith("&ie=1")) {
            imei = imei.replace("&ie=1", "");
            map2.put("ie", 1);
        } else if (imei.endsWith("&ie=0")) {
            imei = imei.replace("&ie=0", "");
            map2.put("ie", 0);
        }
        map2.put("imei", imei);
        map2.put("md", strEncode);
        map2.put("vd", Build.MANUFACTURER);
        map2.put("net", Integer.valueOf(NetworkTypeUtil.getNetworkType(DeviceInfo.sApplicationContext)));
        map2.put("vb", "1.9.9.82603");
        map2.put("t", Long.valueOf(System.currentTimeMillis()));
        if (TextUtils.isEmpty(BaseInfo.sChannel)) {
            try {
                metaValue = AndroidResources.getMetaValue("DCLOUD_STREAMAPP_CHANNEL");
            } catch (Exception e3) {
                e3.printStackTrace();
                metaValue = null;
            }
            if (TextUtils.isEmpty(metaValue)) {
                map2.put("mc", "");
            } else {
                map2.put("mc", metaValue);
            }
        } else {
            map2.put("mc", BaseInfo.sChannel);
        }
        map2.put("paid", str2);
        if (map != null) {
            map2.putAll(map);
        }
        return map2;
    }

    private static void a(IApp iApp, HashMap map) {
        String string;
        JSONArray jSONArray;
        StringBuilder sb = new StringBuilder();
        try {
            string = IOUtil.toString(iApp.getActivity().getAssets().open("dcloud_uniplugins.json"));
        } catch (Exception unused) {
            string = null;
        }
        if (!PdrUtil.isEmpty(string)) {
            try {
                JSONArray jSONArray2 = JSON.parseObject(string).getJSONArray("nativePlugins");
                if (jSONArray2 != null && jSONArray2.size() > 0) {
                    for (int i = 0; i < jSONArray2.size(); i++) {
                        com.alibaba.fastjson.JSONObject jSONObject = jSONArray2.getJSONObject(i);
                        if (jSONObject != null && (jSONArray = jSONObject.getJSONArray("plugins")) != null && jSONArray.size() > 0) {
                            for (int i2 = 0; i2 < jSONArray.size(); i2++) {
                                com.alibaba.fastjson.JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                                if (jSONObject2 != null && jSONObject2.containsKey("name")) {
                                    sb.append(jSONObject2.getString("name"));
                                    sb.append(",");
                                }
                            }
                        }
                    }
                }
            } catch (Exception unused2) {
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            map.put("ups", sb.toString());
        }
    }

    public static void a(Context context, String str, String str2, String str3, int i, String str4, String str5, JSONObject jSONObject, String str6, String str7, String str8, String str9, HashMap map) {
        String strEncode;
        try {
            URLEncoder.encode(Build.MODEL, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            String str10 = DeviceInfo.sApplicationContext.getPackageManager().getPackageInfo(DeviceInfo.sApplicationContext.getPackageName(), 0).versionName;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        TelephonyUtil.getIMEI(context, true, true);
        ArrayList arrayList = new ArrayList();
        if (i == 1) {
            arrayList.add(new HostPicker.Host("YHx8eHsyJyd8OSZsa2RnfWwmZm18JmtmJ2tnZGRta3wneGR9e2l4eCdpa3xhZ2Y=", HostPicker.Host.PriorityEnum.FIRST));
            arrayList.add(new HostPicker.Host("YHx8eHsyJyd8OiZsa2RnfWwmZm18JmtmJ2tnZGRta3wneGR9e2l4eCdpa3xhZ2Y=", HostPicker.Host.PriorityEnum.NORMAL));
            arrayList.add(new HostPicker.Host("YHx8eHsyJydqfDkmbGtkZ31sJmZtfCZrZidgfHx4J2tpaQ==", HostPicker.Host.PriorityEnum.BACKUP));
        } else {
            arrayList.add(new HostPicker.Host("YHx8eHsyJydpezkmbGtkZ31sJmZtfCZrZidrZ2RkbWt8J3hkfXtpeHgnaWt8YWdm", HostPicker.Host.PriorityEnum.FIRST));
            arrayList.add(new HostPicker.Host("YHx8eHsyJydpezombGtkZ31sJmZtfCZrZidrZ2RkbWt8J3hkfXtpeHgnaWt8YWdm", HostPicker.Host.PriorityEnum.NORMAL));
            arrayList.add(new HostPicker.Host("YHx8eHsyJydqaXs5JmxrZGd9bCZmbXwma2YnYHx8eCdraWk=", HostPicker.Host.PriorityEnum.BACKUP));
        }
        Map mapB = b(context, str, str3, i, str8, map);
        if (str6 != null) {
            mapB.put("mediaId", str6);
        }
        if (str7 != null) {
            mapB.put("slotId", str7);
        }
        mapB.put("tid", str2);
        if (i == 32) {
            mapB.put("dec", str4);
            mapB.put("dem", str5);
        }
        if (i == 41 && jSONObject != null && str6 == null) {
            if (jSONObject.has(WXBasicComponentType.IMG)) {
                mapB.put(WXBasicComponentType.IMG, Md5Utils.md5(jSONObject.optString(WXBasicComponentType.IMG)).toLowerCase(Locale.ENGLISH));
            }
            if (jSONObject.has("dw")) {
                mapB.put("dw", jSONObject.optString("dw"));
            }
            if (jSONObject.has("dh")) {
                mapB.put("dh", jSONObject.optString("dh"));
            }
            if (jSONObject.has("click_coord")) {
                mapB.put("click_coord", jSONObject.optJSONObject("click_coord").toString());
            }
        }
        try {
            strEncode = URLEncoder.encode(Base64.encodeToString(AESUtil.encrypt(d1.c(), d1.b(), ZipUtils.zipString(new JSONObject(mapB).toString())), 2), "utf-8");
        } catch (UnsupportedEncodingException e3) {
            e3.printStackTrace();
            strEncode = null;
        }
        String str11 = "edata=" + strEncode;
        try {
            HashMap map2 = new HashMap();
            try {
                if (!PdrUtil.isEmpty(str9) && str9.equalsIgnoreCase("webview")) {
                    Object objADHandlerMethod = ADUtils.ADHandlerMethod("get", "ua-webview");
                    if (objADHandlerMethod instanceof String) {
                        map2.put(IWebview.USER_AGENT, (String) objADHandlerMethod);
                    }
                }
            } catch (Exception unused) {
            }
            HostPicker.getInstance().pickSuitHost(context, arrayList, "CAA_" + i, new a(str11, map2));
        } catch (Exception e4) {
            Logger.p("CommitDataUtil", e4.getMessage());
        }
    }

    public static void a(Context context, String str, String str2, int i, String str3, HashMap map) {
        String strEncode;
        Map mapB = b(context, str, str2, i, str3, map);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new HostPicker.Host("YHx8eHsyJydpejkmbGtkZ31sJmZtfCZrZidrZ2RkbWt8J3hkfXtpeHgnent4", HostPicker.Host.PriorityEnum.FIRST));
        arrayList.add(new HostPicker.Host("YHx8eHsyJydpejombGtkZ31sJmZtfCZrZidrZ2RkbWt8J3hkfXtpeHgnent4", HostPicker.Host.PriorityEnum.NORMAL));
        arrayList.add(new HostPicker.Host("YHx8eHsyJydqaXo6JmxrZGd9bCZmbXwma2YnYHx8eCdraXo=", HostPicker.Host.PriorityEnum.BACKUP));
        try {
            strEncode = URLEncoder.encode(Base64.encodeToString(AESUtil.encrypt(d1.c(), d1.b(), ZipUtils.zipString(new JSONObject(mapB).toString())), 2), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            strEncode = null;
        }
        HostPicker.getInstance().pickSuitHost(context, arrayList, "RSP", new b("edata=" + strEncode, new HashMap()));
    }

    public static void a(IApp iApp, String str, String str2, String str3, String str4, org.json.JSONArray jSONArray) {
        String metaValue;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(ContextChain.TAG_PRODUCT, "a");
            jSONObject.put("t", str4);
            jSONObject.put("a", str);
            jSONObject.put("c", str2);
            jSONObject.put(WXComponent.PROP_FS_MATCH_PARENT, str3);
            if (jSONArray != null) {
                jSONObject.put("d", jSONArray);
            }
            jSONObject.put("pn", iApp.getActivity().getPackageName());
            jSONObject.put(CreateShortResultReceiver.KEY_VERSIONNAME, iApp.obtainAppVersionName());
            jSONObject.put("pv", AndroidResources.versionName);
            jSONObject.put("appid", iApp.obtainAppId());
            jSONObject.put(WXConfig.os, Build.VERSION.SDK_INT);
            jSONObject.put("md", Build.MODEL);
            jSONObject.put("vd", Build.MANUFACTURER);
            jSONObject.put("vb", "1.9.9.82603");
            if (TextUtils.isEmpty(BaseInfo.sChannel)) {
                try {
                    metaValue = AndroidResources.getMetaValue("DCLOUD_STREAMAPP_CHANNEL");
                } catch (Exception e) {
                    e.printStackTrace();
                    metaValue = null;
                }
                if (!TextUtils.isEmpty(metaValue)) {
                    jSONObject.put("mc", metaValue);
                } else {
                    jSONObject.put("mc", "");
                }
            } else {
                jSONObject.put("mc", BaseInfo.sChannel);
            }
            NetTool.httpPost(PdrUtil.checkIntl() ? "https://er.dcloud.io/rv" : "https://er.dcloud.net.cn/rv", jSONObject.toString(), null);
        } catch (JSONException unused) {
        }
    }
}
