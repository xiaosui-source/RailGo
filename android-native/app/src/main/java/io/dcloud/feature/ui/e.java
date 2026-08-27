package io.dcloud.feature.ui;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import io.dcloud.common.DHInterface.AbsMgr;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.IFrameView;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaFrameItem;
import io.dcloud.common.adapter.ui.AdaFrameView;
import io.dcloud.common.adapter.ui.webview.WebResUtil;
import io.dcloud.common.adapter.util.AnimOptions;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.MessageHandler;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.adapter.util.ViewOptions;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.JSONUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.StringUtil;
import io.dcloud.nineoldandroids.animation.Animator;
import io.dcloud.nineoldandroids.animation.ValueAnimator;
import io.dcloud.nineoldandroids.view.ViewHelper;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class e {
    private static HashMap e;
    AbsMgr a;
    HashMap b = new HashMap(1);
    final boolean c = false;
    String d;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements MessageHandler.IMessages {
        final /* synthetic */ io.dcloud.feature.ui.a a;
        final /* synthetic */ io.dcloud.feature.ui.c b;

        a(io.dcloud.feature.ui.a aVar, io.dcloud.feature.ui.c cVar) {
            this.a = aVar;
            this.b = cVar;
        }

        @Override // io.dcloud.common.adapter.util.MessageHandler.IMessages
        public void execute(Object obj) {
            this.a.g(this.b);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements ValueAnimator.AnimatorUpdateListener {
        final /* synthetic */ View a;

        b(View view) {
            this.a = view;
        }

        @Override // io.dcloud.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            if (!(this.a.getLayoutParams() instanceof AbsoluteLayout.LayoutParams)) {
                if (this.a.getLayoutParams() instanceof FrameLayout.LayoutParams) {
                    ViewHelper.setX(this.a, ((Float) valueAnimator.getAnimatedValue()).floatValue());
                    return;
                }
                return;
            }
            AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) this.a.getLayoutParams();
            layoutParams.height = this.a.getHeight();
            layoutParams.width = this.a.getWidth();
            try {
                ViewHelper.setX(this.a, ((Integer) valueAnimator.getAnimatedValue()).intValue());
            } catch (Exception unused) {
                ViewHelper.setX(this.a, ((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
            this.a.requestLayout();
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c implements Animator.AnimatorListener {
        final /* synthetic */ View a;
        final /* synthetic */ io.dcloud.feature.ui.c b;
        final /* synthetic */ IWebview c;
        final /* synthetic */ String d;
        final /* synthetic */ String e;

        c(View view, io.dcloud.feature.ui.c cVar, IWebview iWebview, String str, String str2) {
            this.a = view;
            this.b = cVar;
            this.c = iWebview;
            this.d = str;
            this.e = str2;
        }

        @Override // io.dcloud.nineoldandroids.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
        }

        @Override // io.dcloud.nineoldandroids.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) throws JSONException {
            io.dcloud.feature.ui.c cVar;
            if (this.a == null || (cVar = this.b) == null || cVar.r() == null) {
                return;
            }
            int iA = e.this.a(this.a);
            int width = this.a.getWidth();
            if (iA >= PlatformUtil.SCREEN_WIDTH(this.a.getContext()) || iA <= (-width)) {
                this.b.y.popFromViewStack();
            }
            if (this.c != null && !TextUtils.isEmpty(this.d)) {
                String strM = this.b.m();
                if (TextUtils.isEmpty(strM)) {
                    strM = "";
                }
                Deprecated_JSUtil.execCallback(this.c, this.d, StringUtil.format("{\"id\":\"%s\",\"target\":%s}", strM, this.b.h()), JSUtil.OK, true, true);
            }
            if (TextUtils.isEmpty(this.e)) {
                return;
            }
            if ("hide".equals(this.e)) {
                io.dcloud.feature.ui.c cVar2 = this.b;
                cVar2.a(cVar2.r(), "hide", JSONUtil.createJSONArray("[null,null,null]"));
            } else if (AbsoluteConst.EVENTS_CLOSE.equals(this.e)) {
                io.dcloud.feature.ui.c cVar3 = this.b;
                cVar3.a(cVar3.r(), AbsoluteConst.EVENTS_CLOSE, JSONUtil.createJSONArray("[null,null,null]"));
            }
        }

        @Override // io.dcloud.nineoldandroids.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // io.dcloud.nineoldandroids.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    static /* synthetic */ class d {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[EnumC0043e.values().length];
            a = iArr;
            try {
                iArr[EnumC0043e.findWindowByName.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[EnumC0043e.getTopWebview.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[EnumC0043e.prefetchURL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[EnumC0043e.prefetchURLs.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[EnumC0043e.enumWindow.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[EnumC0043e.getWapLaunchWebview.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[EnumC0043e.getLaunchWebview.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[EnumC0043e.getSecondWebview.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                a[EnumC0043e.currentWebview.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                a[EnumC0043e.createView.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                a[EnumC0043e.setcallbackid.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                a[EnumC0043e.debug.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                a[EnumC0043e.defaultHardwareAccelerated.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                a[EnumC0043e.startAnimation.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                a[EnumC0043e.getDisplayWebview.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                a[EnumC0043e.__callNativeModuleSync.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                a[EnumC0043e.postMessageToUniNView.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    /* renamed from: io.dcloud.feature.ui.e$e, reason: collision with other inner class name */
    private enum EnumC0043e {
        findWindowByName,
        enumWindow,
        getLaunchWebview,
        getWapLaunchWebview,
        currentWebview,
        getTopWebview,
        createView,
        setcallbackid,
        debug,
        setLogs,
        isLogs,
        defaultHardwareAccelerated,
        startAnimation,
        getSecondWebview,
        getDisplayWebview,
        updateAppFrameViews,
        prefetchURL,
        prefetchURLs,
        postMessageToUniNView,
        __callNativeModuleSync
    }

    e(AbsMgr absMgr, String str) {
        this.a = null;
        this.a = absMgr;
        this.d = str;
        a();
    }

    public static String c(String str) {
        return TextUtils.isEmpty(str) ? "" : str.startsWith("./") ? str.substring(2) : str.startsWith("../") ? str.substring(3) : str.startsWith(".../") ? str.substring(4) : str;
    }

    public synchronized String b(IWebview iWebview, String str, JSONArray jSONArray) {
        return a(iWebview, str, jSONArray);
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    public java.lang.String a(io.dcloud.common.DHInterface.IWebview r29, java.lang.String r30, org.json.JSONArray r31) {
        /*
            Method dump skipped, instructions count: 2446
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.ui.e.a(io.dcloud.common.DHInterface.IWebview, java.lang.String, org.json.JSONArray):java.lang.String");
    }

    public void b(String str, io.dcloud.feature.ui.a aVar, IFrameView iFrameView) {
        String strValueOf = String.valueOf(iFrameView.hashCode());
        IWebview iWebviewObtainWebView = iFrameView.obtainWebView();
        String strObtainUrl = iWebviewObtainWebView.obtainUrl();
        AdaFrameView adaFrameView = (AdaFrameView) iFrameView;
        JSONObject jSONObject = adaFrameView.obtainFrameOptions() != null ? adaFrameView.obtainFrameOptions().mJsonViewOption : null;
        String strObtainFrameId = iWebviewObtainWebView.obtainFrameId();
        String str2 = !PdrUtil.isEmpty(strObtainFrameId) ? strObtainFrameId : iFrameView.getFrameType() == 2 ? str : strObtainUrl;
        io.dcloud.feature.ui.c cVar = new io.dcloud.feature.ui.c(aVar, strObtainUrl, str2, strValueOf, jSONObject);
        String str3 = str2;
        cVar.a(iFrameView.getContext(), aVar, iFrameView.obtainWebView(), strValueOf, jSONObject);
        cVar.F = iFrameView.getFrameType() != 2 || adaFrameView.obtainMainView().getVisibility() == 0;
        cVar.I = true;
        adaFrameView.addFrameViewListener(cVar);
        cVar.a(iFrameView, str3);
        aVar.e(cVar);
        aVar.a(str, cVar, 0);
        MessageHandler.sendMessage(new a(aVar, cVar), null);
    }

    public void b(String str) {
        if (PdrUtil.isEmpty(str)) {
            Iterator it = this.b.values().iterator();
            while (it.hasNext()) {
                ((io.dcloud.feature.ui.a) it.next()).a();
            }
            this.b.clear();
            return;
        }
        io.dcloud.feature.ui.a aVar = (io.dcloud.feature.ui.a) this.b.get(str);
        if (aVar != null) {
            Logger.d(Logger.MAIN_TAG, "UIWidgetMgr.dispose pAppid=" + str);
            aVar.a();
        }
        this.b.remove(str);
    }

    private void a(String str, io.dcloud.feature.ui.a aVar, IFrameView iFrameView) {
        IWebview iWebviewObtainWebView = iFrameView.obtainWebView();
        String strValueOf = String.valueOf(iWebviewObtainWebView.obtainFrameId());
        String strObtainUrl = iWebviewObtainWebView.obtainUrl();
        String strObtainFrameId = iWebviewObtainWebView.obtainFrameId();
        String str2 = !PdrUtil.isEmpty(strObtainFrameId) ? strObtainFrameId : strObtainUrl;
        io.dcloud.feature.ui.c cVar = new io.dcloud.feature.ui.c(aVar, strObtainUrl, str2, strValueOf, null);
        cVar.a(iFrameView.getContext(), aVar, iWebviewObtainWebView, strValueOf, null);
        cVar.F = false;
        cVar.I = false;
        cVar.a(true);
        iFrameView.addFrameViewListener(cVar);
        cVar.a(iFrameView, str2);
        aVar.e(cVar);
        aVar.a(str, cVar, 0);
    }

    private io.dcloud.feature.ui.c a(io.dcloud.feature.ui.a aVar, IWebview iWebview, JSONArray jSONArray, IApp iApp, String str, boolean z) throws NumberFormatException {
        String string;
        io.dcloud.feature.ui.c cVarA = aVar.a(iWebview.obtainFrameView());
        String strOptString = jSONArray.optString(0);
        Log.e("UIWidgetMgr", "new -- JSNWindow=" + strOptString);
        JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(1);
        String strOptString2 = jSONArray.optString(2);
        JSONObject jSONObjectOptJSONObject2 = jSONArray.optJSONObject(4);
        JSONArray jSONArrayOptJSONArray = jSONArray.optJSONArray(5);
        if (jSONObjectOptJSONObject == null) {
            jSONObjectOptJSONObject = new JSONObject("{}");
            string = "";
        } else {
            string = JSONUtil.getString(jSONObjectOptJSONObject, "name");
            if (TextUtils.isEmpty(string)) {
                string = JSONUtil.getString(jSONObjectOptJSONObject, "webviewid");
            }
        }
        io.dcloud.feature.ui.c cVarA2 = a(aVar, iWebview, iApp, strOptString, string, str, jSONObjectOptJSONObject, jSONObjectOptJSONObject2, jSONArrayOptJSONArray, z);
        if (cVarA != null) {
            cVarA.b(cVarA2);
        }
        if (strOptString2 != null) {
            cVarA2.b.put(iWebview.getWebviewANID(), strOptString2);
        }
        AnimOptions animOptions = ((AdaFrameItem) cVarA2.y).getAnimOptions();
        ViewOptions viewOptionsObtainFrameOptions = ((AdaFrameItem) cVarA2.y).obtainFrameOptions();
        cVarA2.K = viewOptionsObtainFrameOptions.hasBackground();
        animOptions.parseTransition(viewOptionsObtainFrameOptions.transition);
        animOptions.parseTransform(viewOptionsObtainFrameOptions.transform);
        return cVarA2;
    }

    private io.dcloud.feature.ui.c a(io.dcloud.feature.ui.a aVar, IWebview iWebview, IApp iApp, String str, String str2, String str3, JSONObject jSONObject, JSONObject jSONObject2, JSONArray jSONArray, boolean z) throws NumberFormatException {
        String str4;
        String str5;
        io.dcloud.feature.ui.c cVarA;
        String str6;
        String str7;
        char c2;
        io.dcloud.feature.ui.c cVar;
        String strOptString;
        String str8;
        String strConvert2LocalFullPath;
        String str9;
        Logger.e("createNWindow pUrl=" + str);
        boolean zOptBoolean = jSONObject.optBoolean("directPage", false);
        int iOptInt = jSONObject.optInt("winType", 0);
        String strConvert2WebviewFullPath = iApp.convert2WebviewFullPath(iOptInt == 0 ? iWebview.obtainFullUrl() : null, str);
        int i = zOptBoolean ? 5 : iOptInt;
        if (z) {
            str5 = strConvert2WebviewFullPath;
            str4 = null;
        } else {
            str4 = strConvert2WebviewFullPath;
            str5 = null;
        }
        iApp.obtainWebviewBaseUrl();
        a(iWebview, iApp, str4);
        String strObtainAppId = iApp.obtainAppId();
        boolean zIsEmpty = PdrUtil.isEmpty(str);
        if (i == 4) {
            cVarA = aVar.a(4);
        } else {
            cVarA = i == 5 ? aVar.a(5) : null;
        }
        if (cVarA == null) {
            str7 = strObtainAppId;
            c2 = 0;
            cVar = new io.dcloud.feature.ui.c(aVar, str4, str2, str3, jSONObject);
            str6 = str2;
        } else {
            str6 = str2;
            str7 = strObtainAppId;
            c2 = 0;
            cVar = cVarA;
        }
        cVar.x = jSONObject2;
        AbsMgr absMgr = this.a;
        IMgr.MgrType mgrType = IMgr.MgrType.WindowMgr;
        Integer numValueOf = Integer.valueOf(i);
        Object[] objArr = new Object[3];
        objArr[c2] = str;
        objArr[1] = jSONObject;
        objArr[2] = str3;
        IFrameView iFrameViewObtainFrameView = iWebview.obtainFrameView();
        Object[] objArr2 = new Object[5];
        objArr2[c2] = numValueOf;
        objArr2[1] = iApp;
        objArr2[2] = objArr;
        objArr2[3] = iFrameViewObtainFrameView;
        objArr2[4] = cVar;
        IFrameView iFrameView = (IFrameView) absMgr.processEvent(mgrType, 3, objArr2);
        if (z) {
            iFrameView.obtainWebView().setOriginalUrl(str5);
        }
        if (jSONArray != null) {
            cVar.v = jSONArray;
            cVar.w = iWebview;
        }
        cVar.a(iFrameView, str6);
        IWebview iWebviewObtainWebView = cVar.y.obtainWebView();
        if (jSONObject.has("plusrequire")) {
            iWebviewObtainWebView.setWebviewProperty("plusrequire", jSONObject.optString("plusrequire"));
        }
        if (jSONObject.has("replacewebapi")) {
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("replacewebapi");
            if (jSONObjectOptJSONObject != null && jSONObjectOptJSONObject.has("geolocation")) {
                iWebviewObtainWebView.setWebviewProperty("geolocation", jSONObjectOptJSONObject.optString("geolocation"));
            }
        } else if (jSONObject.has("geolocation")) {
            iWebviewObtainWebView.setWebviewProperty("geolocation", jSONObject.optString("geolocation"));
        }
        if (jSONObject.has("injection")) {
            iWebviewObtainWebView.setWebviewProperty("injection", String.valueOf(jSONObject.optBoolean("injection")));
        }
        if (jSONObject.has(IApp.ConfigProperty.CONFIG_OVERRIDE_RESOURCE)) {
            iWebviewObtainWebView.setOverrideResourceRequest(jSONObject.optJSONArray(IApp.ConfigProperty.CONFIG_OVERRIDE_RESOURCE));
        }
        if (jSONObject.has(IApp.ConfigProperty.CONFIG_OVERRIDEURL)) {
            iWebviewObtainWebView.setOverrideUrlLoadingData(jSONObject.optJSONObject(IApp.ConfigProperty.CONFIG_OVERRIDEURL));
        }
        if (BaseInfo.isWap2AppAppid(str7) && (iWebviewObtainWebView.obtainFrameView().getFrameType() == 4 || iWebviewObtainWebView.obtainFrameView().getFrameType() == 5)) {
            if (iWebviewObtainWebView.getWebviewProperty("plusrequire").equals("none")) {
                str9 = null;
            } else {
                str9 = null;
                iWebviewObtainWebView.appendPreloadJsFile(iApp.convert2AbsFullPath(null, "_www/__wap2app.js"));
                iWebviewObtainWebView.appendPreloadJsFile(iApp.convert2AbsFullPath(null, "_www/__wap2appconfig.js"));
            }
            iWebviewObtainWebView.setPreloadJsFile(iApp.convert2AbsFullPath(iWebview.obtainFullUrl(), "_www/server_index_append.js"), true);
            String strConvert2AbsFullPath = iApp.convert2AbsFullPath(str9, "_www/server_index_append.css");
            if (new File(strConvert2AbsFullPath).exists()) {
                iWebviewObtainWebView.setCssFile(strConvert2AbsFullPath, str9);
            } else {
                String strConvert2AbsFullPath2 = iApp.convert2AbsFullPath(str9, "_www/__wap2app.css");
                if (new File(strConvert2AbsFullPath2).exists()) {
                    iWebviewObtainWebView.setCssFile(strConvert2AbsFullPath2, str9);
                }
            }
        }
        if (jSONObject.has("appendCss")) {
            strOptString = jSONObject.optString("appendCss");
        } else {
            strOptString = jSONObject.has("preloadcss") ? jSONObject.optString("preloadcss") : null;
        }
        if (TextUtils.isEmpty(strOptString)) {
            str8 = null;
        } else {
            str8 = null;
            iWebviewObtainWebView.setCssFile(null, strOptString);
        }
        if (jSONObject.has("appendJs")) {
            strConvert2LocalFullPath = iApp.convert2LocalFullPath(str8, jSONObject.optString("appendJs"));
        } else {
            strConvert2LocalFullPath = jSONObject.has("preloadjs") ? iApp.convert2LocalFullPath(str8, jSONObject.optString("preloadjs")) : str8;
        }
        if (!TextUtils.isEmpty(strConvert2LocalFullPath)) {
            iWebviewObtainWebView.appendPreloadJsFile(strConvert2LocalFullPath);
        }
        if (!zIsEmpty) {
            if (i == 6 && jSONObject.has(IApp.ConfigProperty.CONFIG_ADDITIONAL_HTTPHEADERS)) {
                cVar.y.obtainWebView().setLoadURLHeads(str4, JSONUtil.toMap(jSONObject.optJSONObject(IApp.ConfigProperty.CONFIG_ADDITIONAL_HTTPHEADERS)));
            }
            cVar.y.obtainWebView().loadUrl(str4);
        }
        String str10 = str6;
        cVar.a(iWebview.getContext(), aVar, iFrameView.obtainWebView(), str3, jSONObject);
        iFrameView.obtainMainView().setVisibility(4);
        if (DeviceInfo.sDeviceSdkVer >= 11) {
            aVar.b(iFrameView);
        }
        aVar.e(cVar);
        cVar.a(jSONObject, false);
        Logger.d(Logger.VIEW_VISIBLE_TAG, str7 + " createNWindow webview_name=" + str10);
        return cVar;
    }

    private void a(IWebview iWebview, IApp iApp, String str) {
        if (!BaseInfo.isBase(iWebview.getContext()) || TextUtils.isEmpty(str)) {
            return;
        }
        String strObtainUrl = iWebview.obtainUrl();
        if (str.startsWith(DeviceInfo.HTTP_PROTOCOL) || strObtainUrl.startsWith(DeviceInfo.HTTP_PROTOCOL) || str.startsWith(DeviceInfo.HTTPS_PROTOCOL) || strObtainUrl.startsWith(DeviceInfo.HTTPS_PROTOCOL)) {
            return;
        }
        String originalUrl = WebResUtil.getOriginalUrl(strObtainUrl);
        String originalUrl2 = WebResUtil.getOriginalUrl(str);
        Log.i(AbsoluteConst.HBUILDER_TAG, StringUtil.format(AbsoluteConst.OPENLOG, c(WebResUtil.getHBuilderPrintUrl(iApp.convert2RelPath(originalUrl))), c(WebResUtil.getHBuilderPrintUrl(iApp.convert2RelPath(originalUrl2)))));
    }

    public static io.dcloud.feature.ui.b a(String str) throws IllegalAccessException, InstantiationException {
        if (PdrUtil.isEmpty(str)) {
            return null;
        }
        try {
            Object objNewInstance = Class.forName((String) e.get(str.toLowerCase(Locale.ENGLISH))).newInstance();
            if (objNewInstance instanceof io.dcloud.feature.ui.b) {
                return (io.dcloud.feature.ui.b) objNewInstance;
            }
            return null;
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
            return null;
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
            return null;
        } catch (InstantiationException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    private void a() {
        e = (HashMap) this.a.processEvent(IMgr.MgrType.FeatureMgr, 4, this.d);
    }

    private ValueAnimator a(View view, int i, int i2, String str, IWebview iWebview, String str2, io.dcloud.feature.ui.c cVar) {
        ValueAnimator valueAnimatorOfFloat;
        if (view.getLayoutParams() instanceof AbsoluteLayout.LayoutParams) {
            valueAnimatorOfFloat = ValueAnimator.ofInt(i, i2);
        } else {
            valueAnimatorOfFloat = view.getLayoutParams() instanceof FrameLayout.LayoutParams ? ValueAnimator.ofFloat(i, i2) : null;
        }
        valueAnimatorOfFloat.setDuration(200L);
        valueAnimatorOfFloat.addUpdateListener(new b(view));
        valueAnimatorOfFloat.addListener(new c(view, cVar, iWebview, str2, str));
        return valueAnimatorOfFloat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(View view) {
        if (view == null) {
            return 0;
        }
        if (!(view.getLayoutParams() instanceof AbsoluteLayout.LayoutParams) && !(view.getLayoutParams() instanceof FrameLayout.LayoutParams)) {
            return 0;
        }
        float x = ViewHelper.getX(view);
        return (int) x;
    }
}
