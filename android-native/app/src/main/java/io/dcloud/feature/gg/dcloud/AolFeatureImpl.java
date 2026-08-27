package io.dcloud.feature.gg.dcloud;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.taobao.weex.common.WXRequest;
import io.dcloud.WebAppActivity;
import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.hostpicker.HostPicker;
import io.dcloud.feature.gg.AolSplashUtil;
import io.dcloud.feature.gg.dcloud.ADHandler;
import io.dcloud.feature.gg.dcloud.ADResult;
import io.dcloud.feature.gg.dcloud.AolFeatureImpl;
import io.dcloud.p.b4;
import io.dcloud.p.g4;
import io.dcloud.p.k;
import io.dcloud.p.s;
import io.dcloud.p.u;
import io.dcloud.sdk.core.entry.SplashAOLConfig;
import io.dcloud.sdk.core.v3.sp.DCSplashAOLLoadListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class AolFeatureImpl {
    private static volatile boolean isRequestSuccess = false;
    private static boolean isSplashClose = false;
    private static Handler mHandler = new MyHandler(null);
    private static int retryCount = 0;
    private static u splashAd;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    /* renamed from: io.dcloud.feature.gg.dcloud.AolFeatureImpl$1, reason: invalid class name */
    class AnonymousClass1 implements DCSplashAOLLoadListener {
        final /* synthetic */ Context val$context;

        AnonymousClass1(Context context) {
            this.val$context = context;
        }

        static /* synthetic */ void lambda$onSplashAOLLoad$0(Context context) {
            if (context instanceof WebAppActivity) {
                WebAppActivity webAppActivity = (WebAppActivity) context;
                webAppActivity.onCreateAdSplash(context);
                webAppActivity.initBackToFrontSplashAd();
            }
        }

        @Override // io.dcloud.sdk.core.v3.base.DCBaseAOLLoadListener
        public void onError(int i, String str, JSONArray jSONArray) {
            Logger.e("doForFeature", "AolFeatureImpl load fail" + i + ":" + str);
            if (i == -5000 || i == -5001) {
                return;
            }
            AolFeatureImpl.setRequest(this.val$context, "-8003", str, jSONArray);
        }

        @Override // io.dcloud.sdk.core.v3.sp.DCSplashAOLLoadListener
        public void onSplashAOLLoad() {
            Logger.e("doForFeature", "AolFeatureImpl load success");
            if (AolFeatureImpl.isRequestSuccess) {
                Handler handler = new Handler(Looper.getMainLooper());
                final Context context = this.val$context;
                handler.post(new Runnable() { // from class: io.dcloud.feature.gg.dcloud.AolFeatureImpl$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AolFeatureImpl.AnonymousClass1.lambda$onSplashAOLLoad$0(context);
                    }
                });
            }
        }

        @Override // io.dcloud.sdk.core.v3.sp.DCSplashAOLLoadListener
        public void redBag(View view, FrameLayout.LayoutParams layoutParams) {
            ((ViewGroup) ((Activity) this.val$context).getWindow().getDecorView().findViewById(R.id.content)).addView(view, layoutParams);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    protected static class AdReceiver implements IAolReceiver {
        private Object[] _args;
        private String appid;
        private Context context;

        public AdReceiver(Context context, Object[] objArr, String str) {
            this.context = context;
            this._args = objArr;
            this.appid = str;
        }

        public List<String> getActivities() {
            ArrayList arrayList = new ArrayList();
            try {
                ActivityInfo[] activityInfoArr = this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 1).activities;
                if (activityInfoArr != null) {
                    for (ActivityInfo activityInfo : activityInfoArr) {
                        arrayList.add(activityInfo.name);
                    }
                }
                ServiceInfo[] serviceInfoArr = this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 4).services;
                if (serviceInfoArr != null) {
                    for (ServiceInfo serviceInfo : serviceInfoArr) {
                        arrayList.add(serviceInfo.name);
                    }
                }
                ProviderInfo[] providerInfoArr = this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 8).providers;
                if (providerInfoArr != null) {
                    for (ProviderInfo providerInfo : providerInfoArr) {
                        arrayList.add(providerInfo.name);
                    }
                }
                ActivityInfo[] activityInfoArr2 = this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 2).receivers;
                if (activityInfoArr2 != null) {
                    for (ActivityInfo activityInfo2 : activityInfoArr2) {
                        arrayList.add(activityInfo2.name);
                    }
                }
            } catch (Exception unused) {
            }
            return arrayList;
        }

        /* renamed from: lambda$onReceiver$0$io-dcloud-feature-gg-dcloud-AolFeatureImpl$AdReceiver, reason: not valid java name */
        /* synthetic */ void m423x644f7ad6() {
            Context context = this.context;
            if (context instanceof WebAppActivity) {
                ((WebAppActivity) context).onCreateAdSplash(context);
                ((WebAppActivity) this.context).initBackToFrontSplashAd();
            }
        }

        @Override // io.dcloud.feature.gg.dcloud.IAolReceiver
        public void onError(String str, String str2) throws IOException, NumberFormatException {
            int i;
            boolean unused = AolFeatureImpl.isRequestSuccess = false;
            SP.setsBundleData(ADHandler.AdTag, "uniad", "");
            Logger.p("request Fail", "type:" + str + ";message:" + str2);
            if (AolFeatureImpl.retryCount < 3) {
                AolFeatureImpl.access$508();
                Message message = new Message();
                message.what = 1;
                message.obj = new Runnable() { // from class: io.dcloud.feature.gg.dcloud.AolFeatureImpl.AdReceiver.1
                    @Override // java.lang.Runnable
                    public void run() {
                        k.a(AdReceiver.this.context, AdReceiver.this.appid, "pull", "RETRY");
                    }
                };
                AolFeatureImpl.mHandler.sendMessageDelayed(message, AolFeatureImpl.retryCount * WXRequest.DEFAULT_TIMEOUT_MS);
            }
            if (this._args[2] == null) {
                try {
                    i = Integer.parseInt(str2);
                } catch (Exception unused2) {
                    i = -1;
                }
                Context context = this.context;
                if (i != -1) {
                    str2 = "http:" + str2;
                }
                AolFeatureImpl.setRequest(context, "-8001", str2, null);
            }
        }

        @Override // io.dcloud.feature.gg.dcloud.IAolReceiver
        public void onReceiver(JSONObject jSONObject) throws JSONException, ClassNotFoundException {
            char c;
            char c2;
            JSONObject jSONObject2;
            Logger.p("doForFeature", "success when request");
            if (AolFeatureImpl.isSplashClose && this._args[2] == null && ADHandler.SplashAdIsEnable(this.context).booleanValue()) {
                AolFeatureImpl.setRequest(this.context, "-8002", "广告关闭时未请求成功", null);
            }
            boolean unused = AolFeatureImpl.isRequestSuccess = true;
            try {
                JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                HashMap map = new HashMap();
                JSONObject jSONObject3 = new JSONObject();
                g4 g4Var = SP.getsOrCreateBundle(this.context, ADHandler.AdTag);
                if (jSONObjectOptJSONObject != null) {
                    String strOptString = jSONObjectOptJSONObject.has("uniad") ? jSONObjectOptJSONObject.optString("uniad") : "";
                    if (jSONObjectOptJSONObject.has("al")) {
                        g4Var.b("al", jSONObjectOptJSONObject.optString("al"));
                    }
                    if (jSONObjectOptJSONObject.has("cad")) {
                        JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject("cad");
                        if (jSONObjectOptJSONObject2 != null) {
                            List<String> activities = getActivities();
                            Iterator<String> itKeys = jSONObjectOptJSONObject2.keys();
                            while (itKeys.hasNext()) {
                                String next = itKeys.next();
                                JSONObject jSONObject4 = jSONObjectOptJSONObject2.getJSONObject(next);
                                JSONArray jSONArrayOptJSONArray = jSONObject4.optJSONArray("mf-a");
                                if (jSONArrayOptJSONArray == null || jSONArrayOptJSONArray.length() <= 0) {
                                    jSONObject2 = jSONObjectOptJSONObject2;
                                    JSONArray jSONArray = jSONObject4.getJSONArray("cls-a");
                                    for (int i = 0; i < jSONArray.length(); i++) {
                                        try {
                                            Class.forName(jSONArray.getString(i));
                                            JSONObject jSONObject5 = new JSONObject();
                                            jSONObject5.put("r", "1");
                                            jSONObject3.put(next, jSONObject5);
                                            break;
                                        } catch (Exception unused2) {
                                        }
                                    }
                                } else {
                                    jSONObject2 = jSONObjectOptJSONObject2;
                                    int i2 = 0;
                                    while (true) {
                                        if (i2 >= jSONArrayOptJSONArray.length()) {
                                            break;
                                        }
                                        if (activities.contains(jSONArrayOptJSONArray.getString(i2))) {
                                            JSONObject jSONObject6 = new JSONObject();
                                            jSONObject6.put("r", "1");
                                            jSONObject3.put(next, jSONObject6);
                                            break;
                                        }
                                        i2++;
                                    }
                                }
                                jSONObjectOptJSONObject2 = jSONObject2;
                            }
                            c = 0;
                            c2 = 1;
                            map.put("cad", jSONObject3.length() > 0 ? jSONObject3.toString() : "");
                        } else {
                            c = 0;
                            c2 = 1;
                        }
                    } else {
                        c = 0;
                        c2 = 1;
                        map.put("cad", "");
                    }
                    g4Var.b("uniad", strOptString);
                    g4Var.b("cgk", strOptString);
                } else {
                    c = 0;
                    c2 = 1;
                    g4Var.b("uniad", "");
                    map.put("cad", "");
                }
                AolSplashUtil.saveOperate(map);
                if (jSONObject3.length() > 0) {
                    HashMap map2 = new HashMap();
                    map2.put("rad", jSONObject3.toString());
                    Context context = this.context;
                    ADHandler.ADReceiver aDReceiver = new ADHandler.ADReceiver(context);
                    ADResult.CADReceiver cADReceiver = new ADResult.CADReceiver(this.context);
                    IAolReceiver[] iAolReceiverArr = new IAolReceiver[2];
                    iAolReceiverArr[c] = aDReceiver;
                    iAolReceiverArr[c2] = cADReceiver;
                    ADHandler.pullRad(context, map2, iAolReceiverArr);
                }
                if (AolFeatureImpl.splashAd != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: io.dcloud.feature.gg.dcloud.AolFeatureImpl$AdReceiver$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m423x644f7ad6();
                        }
                    });
                    AolFeatureImpl.splashAd.v();
                }
            } catch (Exception unused3) {
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    private static class MyHandler extends Handler {
        private MyHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1) {
                ((Runnable) message.obj).run();
            }
        }

        /* synthetic */ MyHandler(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    static /* synthetic */ int access$508() {
        int i = retryCount;
        retryCount = i + 1;
        return i;
    }

    public static Object doForFeature(String str, Object obj) throws IOException, ClassNotFoundException {
        u uVar;
        if ("onAppCreate".equals(str)) {
            Context context = (Context) obj;
            Logger.p("doForFeature", "AolFeatureImpl onAppCreate");
            String str2 = ADHandler.get("uniad");
            if (!TextUtils.isEmpty(str2)) {
                SP.setsBundleData(context, ADHandler.AdTag, "uniad", str2);
                SP.setsBundleData(context, ADHandler.AdTag, "cgk", str2);
                SP.removeBundleData(context, ADHandler.AdTag, "uniad");
                SP.removeBundleData(context, ADHandler.AdTag, "cgk");
            }
        } else if ("pull".equals(str) || "ba_pull".equals(str)) {
            Object[] objArr = (Object[]) obj;
            Context context2 = (Context) objArr[0];
            String str3 = (String) objArr[1];
            if (str.equals("ba_pull") && b4.b() && !s.d(context2, str3)) {
                return null;
            }
            g4.a(context2, ADHandler.AdTag);
            ADHandler.pull(context2, str3, false, (List<HostPicker.Host>) null, new ADHandler.ADReceiver(context2), new ADResult.CADReceiver(context2), new AdReceiver(context2, objArr, str3));
            if ((PdrUtil.isEmpty(objArr[2]) || !"RETRY".equals(objArr[2])) && !AppRuntime.hasPrivacyForNotShown(context2)) {
                splashAd = null;
                isSplashClose = false;
                isRequestSuccess = false;
                mHandler.removeMessages(1);
                retryCount = 0;
                SplashAOLConfig splashAOLConfigBuild = new SplashAOLConfig.Builder().width(0).height(0).build();
                u uVar2 = new u((Activity) context2);
                splashAd = uVar2;
                uVar2.a(splashAOLConfigBuild, (DCSplashAOLLoadListener) new AnonymousClass1(context2), false);
            }
            AolSplashUtil.setShowInterstitialAd(false);
        } else if ("save".equals(str)) {
            Object[] objArr2 = (Object[]) obj;
            Context context3 = (Context) objArr2[0];
            String str4 = (String) objArr2[1];
            HashMap map = (HashMap) objArr2[2];
            Logger.p("doForFeature", "AolFeatureImpl save");
            AolSplashUtil.saveOperate(context3, str4, map);
        } else {
            if ("formatUrl_wanka".equals(str)) {
                Object[] objArr3 = (Object[]) ((Object[]) obj)[2];
                return ADHandler.formatUrl((String) objArr3[0], (JSONObject) objArr3[1]);
            }
            if ("handleArgs_wanka".equals(str)) {
                return ADHandler.getArgsJsonData((JSONObject) ((Object[]) obj)[2]);
            }
            if ("onWillCloseSplash".equals(str)) {
                isSplashClose = true;
                isRequestSuccess = false;
                Object[] objArr4 = (Object[]) obj;
                Context context4 = (Context) objArr4[0];
                if (context4 instanceof Activity) {
                    Activity activity = (Activity) context4;
                    if (!activity.isDestroyed() && !activity.isFinishing() && (uVar = splashAd) != null) {
                        uVar.a((View) objArr4[2]);
                    }
                }
                return null;
            }
            if ("onCloseSplashNoAd".equals(str)) {
                isSplashClose = true;
                Context context5 = (Context) ((Object[]) obj)[0];
                if (context5 instanceof Activity) {
                    Activity activity2 = (Activity) context5;
                    if (!activity2.isDestroyed() && !activity2.isFinishing()) {
                        Logger.p("doForFeature", "AolFeatureImpl onCloseSplashNoAd");
                        ADHandler.SplashAdIsEnable(context5).booleanValue();
                    }
                }
                return null;
            }
            if ("onCreateAdSplash".equals(str)) {
                if (!isRequestSuccess || splashAd == null || isSplashClose) {
                    return null;
                }
                Object[] objArr5 = (Object[]) obj;
                Context context6 = (Context) objArr5[0];
                if (context6 instanceof Activity) {
                    Activity activity3 = (Activity) context6;
                    if (activity3.isDestroyed() || activity3.isFinishing() || !ADHandler.SplashAdIsEnable(context6).booleanValue()) {
                        return null;
                    }
                    ICallBack iCallBack = (ICallBack) objArr5[1];
                    Logger.p("doForFeature", "AolFeatureImpl onCreateAdSplash");
                    u uVar3 = splashAd;
                    if (uVar3 != null && uVar3.l()) {
                        return splashAd.a(activity3, "", iCallBack);
                    }
                }
                return null;
            }
            if ("onAppAttachBaseContext".equals(str)) {
                Logger.p("doForFeature", "AolFeatureImpl onAppAttachBaseContext");
            } else if ("onSplashclosed".equals(str)) {
                u uVar4 = splashAd;
                if (uVar4 != null) {
                    uVar4.w();
                }
                splashAd = null;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setRequest(Context context, String str, String str2, JSONArray jSONArray) {
        if (context.getPackageName().equals("io.dcloud.HBuilder")) {
            return;
        }
        ADHandler.postSplashError(context, str, str2, jSONArray);
    }
}
