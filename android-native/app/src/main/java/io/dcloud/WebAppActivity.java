package io.dcloud;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidtranscoder.format.MediaFormatExtraConstants;
import com.bumptech.glide.Glide;
import io.dcloud.application.DCLoudApplicationImpl;
import io.dcloud.application.DCloudApplication;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.common.DHInterface.ISysEventListener;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.DHInterface.SplashView;
import io.dcloud.common.DHInterface.message.ActionBus;
import io.dcloud.common.DHInterface.message.action.WebActivityOnDestroyAction;
import io.dcloud.common.adapter.io.DHFile;
import io.dcloud.common.adapter.ui.FrameSwitchView;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.MobilePhoneModel;
import io.dcloud.common.adapter.util.PermissionUtil;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.constant.IntentConst;
import io.dcloud.common.constant.StringConst;
import io.dcloud.common.core.ui.DCKeyboardManager;
import io.dcloud.common.core.ui.TabBarWebviewMgr;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.ImageLoaderUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.ShortCutUtil;
import io.dcloud.common.util.TestUtil;
import io.dcloud.common.util.language.LanguageUtil;
import io.dcloud.feature.gg.dcloud.dcmgr.SIAolManager;
import io.dcloud.feature.internal.sdk.SDK;
import io.dcloud.feature.internal.splash.ISplash;
import io.dcloud.feature.internal.splash.SplashViewDBackground;
import io.dcloud.p.k;
import io.src.dcloud.adapter.DCloudAdapterUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class WebAppActivity extends io.dcloud.a {
    public static final long ONE_SECOND = 1000;
    public static final long SPLASH_SECOND = 5000;
    protected boolean mSplashShowing;
    private AlertDialog o;
    private boolean p;
    BroadcastReceiver s;
    private f t;
    private HomeKeyEventBroadcastReceiver u;
    private Context v;
    private ArrayList y;
    private Handler q = new Handler();
    private final String r = "remove-app_action";
    Bitmap w = null;
    protected View mSplashView = null;
    long x = 0;
    boolean z = false;
    View A = null;
    boolean B = false;
    boolean C = true;
    FrameLayout D = null;
    LinearLayout E = null;
    FrameLayout F = null;
    ICallBack G = null;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class HomeKeyEventBroadcastReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String stringExtra;
            if (!intent.getAction().equals("android.intent.action.CLOSE_SYSTEM_DIALOGS") || (stringExtra = intent.getStringExtra("reason")) == null) {
                return;
            }
            if (stringExtra.equals("homekey") || stringExtra.equals("recentapps")) {
                DCLoudApplicationImpl.self().onApp2Back();
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a extends BroadcastReceiver {
        a() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action;
            int i;
            action = intent.getAction();
            action.getClass();
            action.hashCode();
            i = 0;
            switch (action) {
                case "android.intent.action.BATTERY_CHANGED":
                    int intExtra = intent.getIntExtra(MediaFormatExtraConstants.KEY_LEVEL, 0);
                    AppRuntime.setBatteryLevel(intExtra);
                    AppRuntime.setTemperature((int) (intent.getIntExtra("temperature", 0) / 10.0d));
                    break;
                case "android.intent.action.LOCALE_CHANGED":
                    LanguageUtil.updateDeviceDefLocalLanguage(Locale.getDefault());
                    break;
                case "com.huawei.intent.action.CLICK_STATUSBAR":
                    WebAppActivity webAppActivity = WebAppActivity.this;
                    if (webAppActivity.D != null && webAppActivity.y != null) {
                        ArrayList arrayList = WebAppActivity.this.y;
                        int size = arrayList.size();
                        while (i < size) {
                            Object obj = arrayList.get(i);
                            i++;
                            ((ICallBack) obj).onCallBack(1, action);
                        }
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements ICallBack {
        b() {
        }

        @Override // io.dcloud.common.DHInterface.ICallBack
        public Object onCallBack(int i, Object obj) {
            if (i != 1) {
                return null;
            }
            WebAppActivity webAppActivity = WebAppActivity.this;
            if (webAppActivity.G == null) {
                webAppActivity.B = true;
                return null;
            }
            webAppActivity.closeAppStreamSplash((String) obj);
            WebAppActivity.this.B = false;
            return null;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c implements DialogInterface.OnClickListener {
        final /* synthetic */ String a;

        c(String str) {
            this.a = str;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            WebAppActivity.this.b(this.a, 1);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class d implements DialogInterface.OnClickListener {
        final /* synthetic */ String a;

        d(String str) {
            this.a = str;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            WebAppActivity webAppActivity = WebAppActivity.this;
            webAppActivity.updateParam("closewebapp", webAppActivity.that);
            WebAppActivity.this.b(this.a, 0);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class e implements DialogInterface.OnKeyListener {
        final /* synthetic */ String a;

        e(String str) {
            this.a = str;
        }

        @Override // android.content.DialogInterface.OnKeyListener
        public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
            if (keyEvent.getAction() != 1 || i != 4) {
                return false;
            }
            WebAppActivity webAppActivity = WebAppActivity.this;
            webAppActivity.updateParam("closewebapp", webAppActivity.that);
            WebAppActivity.this.b(this.a, 0);
            return true;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    private class f implements DCLoudApplicationImpl.ActivityStatusListener {
        private SoftReference a;

        public f(Activity activity) {
            this.a = new SoftReference(activity);
        }

        @Override // io.dcloud.application.DCLoudApplicationImpl.ActivityStatusListener
        public void onBack() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (AppRuntime.hasPrivacyForNotShown((Context) this.a.get())) {
                return;
            }
            Activity activity = (Activity) this.a.get();
            if (activity.isDestroyed() || activity.isFinishing()) {
                return;
            }
            try {
                SIAolManager.class.getMethod("load", Activity.class).invoke(SIAolManager.class.getMethod("getInstance", null).invoke(null, null), activity);
            } catch (Exception unused) {
            }
        }

        @Override // io.dcloud.application.DCLoudApplicationImpl.ActivityStatusListener
        public void onFront() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (AppRuntime.hasPrivacyForNotShown((Context) this.a.get())) {
                return;
            }
            Activity activity = (Activity) this.a.get();
            if (activity.isDestroyed() || activity.isFinishing()) {
                return;
            }
            try {
                SIAolManager.class.getMethod(AbsoluteConst.EVENTS_WEBVIEW_SHOW, Activity.class).invoke(SIAolManager.class.getMethod("getInstance", null).invoke(null, null), activity);
            } catch (Exception unused) {
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class g extends RelativeLayout {
        int a;
        float b;
        int c;
        Paint d;
        int e;
        int f;
        int g;

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class a implements Runnable {
            a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                g gVar = g.this;
                int i = gVar.g - 5;
                gVar.g = i;
                if (i > 0) {
                    gVar.postDelayed(this, 5L);
                } else {
                    ViewGroup viewGroup = (ViewGroup) gVar.getParent();
                    if (viewGroup != null) {
                        viewGroup.removeView(g.this);
                    }
                }
                g.this.invalidate();
            }
        }

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class b implements Runnable {
            b() {
            }

            @Override // java.lang.Runnable
            public void run() {
                g gVar = g.this;
                int i = gVar.f;
                int i2 = gVar.e;
                int i3 = 10;
                int i4 = (i - i2) / 10;
                if (i4 <= 10) {
                    i3 = 1;
                    if (i4 >= 1) {
                        i3 = i4;
                    }
                }
                int i5 = i2 + i3;
                gVar.e = i5;
                if (i > i5) {
                    gVar.postDelayed(this, 5L);
                } else if (i >= gVar.a) {
                    gVar.a();
                }
                g.this.invalidate();
            }
        }

        g(Context context) {
            super(context);
            this.c = 0;
            this.d = new Paint();
            this.e = 0;
            this.f = 0;
            this.g = 255;
            this.a = context.getResources().getDisplayMetrics().widthPixels;
            int i = context.getResources().getDisplayMetrics().heightPixels;
            if (i == 1280) {
                this.b = 6.0f;
            } else if (i != 1920) {
                this.b = context.getResources().getDisplayMetrics().heightPixels * 0.0045f;
            } else {
                this.b = 9.0f;
            }
        }

        void a() {
            postDelayed(new a(), 50L);
        }

        void b() {
            a(100);
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            this.d.setColor(Color.argb(this.g, 26, 173, 25));
            float f = this.c;
            canvas.drawRect(0.0f, f, this.e, f + this.b, this.d);
        }

        @Override // android.widget.RelativeLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            setMeasuredDimension(this.a, this.c + ((int) this.b));
        }

        void a(int i) {
            int i2 = (this.a * i) / 100;
            if (this.e >= this.f) {
                postDelayed(new b(), 5L);
            }
            this.f = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str, int i) {
        Intent intent = new Intent(AbsoluteConst.ACTION_APP_DOWNLOAD_ERROR_DIALOG_CLICKED);
        intent.putExtra("type", i);
        intent.putExtra("appid", str);
        intent.setPackage(getPackageName());
        sendBroadcast(intent);
    }

    public static void deviceInjectionGeoLocationJs(Context context) {
        BaseInfo.injectionGeolocationJS = !MobilePhoneModel.checkDeviceHtml5Geo();
    }

    private void k() {
        Intent intent = getIntent();
        boolean booleanExtra = intent != null ? intent.getBooleanExtra(IntentConst.PL_AUTO_HIDE, false) : false;
        Log.d("WebAppActivity", "checkAutoHide " + booleanExtra);
        if (booleanExtra) {
            Intent intent2 = new Intent();
            String stringExtra = intent.getStringExtra(IntentConst.PL_AUTO_HIDE_SHOW_PN);
            String stringExtra2 = intent.getStringExtra(IntentConst.PL_AUTO_HIDE_SHOW_ACTIVITY);
            intent2.putExtra(IntentConst.PL_AUTO_HIDE_SHOW_PN, true);
            intent2.setClassName(stringExtra, stringExtra2);
            this.that.startActivity(intent2);
            this.that.overridePendingTransition(0, 0);
            Log.d("WebAppActivity", "checkAutoHide return mini package " + stringExtra2);
        }
    }

    private String l() {
        return this.p ? getIntent().getStringExtra("appid") : BaseInfo.sDefaultBootApp;
    }

    private void m() {
        Intent intent = getIntent();
        if (intent != null) {
            boolean booleanExtra = intent.getBooleanExtra(IntentConst.IS_STREAM_APP, false);
            this.p = booleanExtra;
            if (booleanExtra) {
                return;
            }
            intent.removeExtra("appid");
        }
    }

    private void n() {
        IntentFilter intentFilter = new IntentFilter("com.huawei.intent.action.CLICK_STATUSBAR");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        if (this.s == null) {
            this.s = new a();
        }
        if (Build.VERSION.SDK_INT >= 33) {
            registerReceiver(this.s, intentFilter, "huawei.permission.CLICK_STATUSBAR_BROADCAST", null, 2);
        } else {
            registerReceiver(this.s, intentFilter, "huawei.permission.CLICK_STATUSBAR_BROADCAST", (Handler) null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void o() {
        View view = this.A;
        if (view == null || this.mSplashView == null) {
            return;
        }
        if (view.getHeight() == 0 || this.A.getWidth() == 0) {
            this.A.measure(View.MeasureSpec.makeMeasureSpec(this.mSplashView.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(this.mSplashView.getHeight(), 1073741824));
            this.A.layout(0, 0, this.mSplashView.getWidth(), this.mSplashView.getHeight());
        }
    }

    @Override // io.dcloud.common.DHInterface.IActivityHandler
    public void addClickStatusbarCallBack(ICallBack iCallBack) {
        if (this.y == null) {
            this.y = new ArrayList();
        }
        if (this.y.contains(iCallBack)) {
            return;
        }
        this.y.add(iCallBack);
    }

    protected void addViewToContentView(View view) {
        int iIndexOfChild = this.D.indexOfChild(this.mSplashView);
        int childCount = this.D.getChildCount();
        if (childCount <= 0) {
            this.D.addView(view);
            return;
        }
        for (int i = childCount - 1; i >= 0; i--) {
            View childAt = this.D.getChildAt(i);
            if (childAt != view) {
                if ("AppRootView".equals(childAt.getTag())) {
                    this.D.addView(view, i);
                    this.D.removeView(childAt);
                    return;
                } else if (i == 0) {
                    if (childAt == this.mSplashView) {
                        this.D.addView(view, 0);
                    } else if (iIndexOfChild > 0) {
                        this.D.addView(view, iIndexOfChild - 1);
                    } else {
                        this.D.addView(view);
                    }
                }
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        this.v = context;
        if (Build.VERSION.SDK_INT < 26) {
            super.attachBaseContext(context);
        } else {
            super.attachBaseContext(LanguageUtil.updateContextLanguageAfterO(context, false));
        }
    }

    public int backPressed() {
        return 0;
    }

    @Override // io.dcloud.b, io.dcloud.common.DHInterface.IActivityHandler
    public /* bridge */ /* synthetic */ void callBack(String str, Bundle bundle) {
        super.callBack(str, bundle);
    }

    @Override // io.dcloud.a
    public /* bridge */ /* synthetic */ void checkAndRequestPhoneState() {
        super.checkAndRequestPhoneState();
    }

    @Override // io.dcloud.a
    public /* bridge */ /* synthetic */ void checkAndRequestStoragePermission() {
        super.checkAndRequestStoragePermission();
    }

    @Override // io.dcloud.common.DHInterface.IActivityHandler
    public void closeAppStreamSplash(String str) {
        Logger.d("webappActivity closeAppStreamSplash");
        DCloudAdapterUtil.Plugin2Host_closeAppStreamSplash(str);
        Bitmap bitmap = this.w;
        if (bitmap != null && !bitmap.isRecycled()) {
            try {
                this.w.recycle();
                this.w = null;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (this.mSplashView != null) {
            Logger.d("webappActivity removeView mSplashView");
            View view = this.mSplashView;
            if (view instanceof g) {
                ((g) view).b();
            } else {
                this.D.removeView(view);
            }
            this.mSplashView = null;
            this.A = null;
        }
        this.z = false;
        this.mSplashShowing = false;
        this.x = 0L;
        ICallBack iCallBack = this.G;
        if (iCallBack != null) {
            iCallBack.onCallBack(1, null);
            this.G = null;
        }
        initBackToFrontSplashAd();
        if (TextUtils.isEmpty(str)) {
            str = l();
        }
        k.a(this, str, "onCloseSplashNoAd", null);
    }

    @Override // io.dcloud.common.DHInterface.IActivityHandler
    public void closeSideBar() {
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
    }

    @Override // io.dcloud.b, io.dcloud.common.DHInterface.IActivityHandler
    public /* bridge */ /* synthetic */ int getActivityState() {
        return super.getActivityState();
    }

    @Override // io.dcloud.b, io.dcloud.common.DHInterface.IActivityHandler
    public /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @Override // io.dcloud.common.DHInterface.IActivityHandler
    public Context getOriginalContext() {
        return this.v;
    }

    public View getProgressView() {
        View view = this.mSplashView;
        if (view == null || !(view instanceof g)) {
            return null;
        }
        return view;
    }

    @Override // io.dcloud.a, androidx.appcompat.app.AppCompatActivity, android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public /* bridge */ /* synthetic */ Resources getResources() {
        return super.getResources();
    }

    @Override // io.dcloud.b, io.dcloud.common.DHInterface.IActivityHandler
    public /* bridge */ /* synthetic */ String getUrlByFilePath(String str, String str2) {
        return super.getUrlByFilePath(str, str2);
    }

    @Override // io.dcloud.a, io.dcloud.common.DHInterface.IActivityHandler
    public boolean hasAdService() {
        return super.hasAdService() && this.z;
    }

    public void initBackToFrontSplashAd() {
        if (!SDK.isUniMPSDK() && (getApplication() instanceof DCloudApplication) && this.t == null) {
            HomeKeyEventBroadcastReceiver homeKeyEventBroadcastReceiver = new HomeKeyEventBroadcastReceiver();
            this.u = homeKeyEventBroadcastReceiver;
            if (Build.VERSION.SDK_INT >= 33) {
                registerReceiver(homeKeyEventBroadcastReceiver, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"), 2);
            } else {
                registerReceiver(homeKeyEventBroadcastReceiver, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
            }
            this.t = new f(this);
            ((DCloudApplication) getApplication()).addActivityStatusListener(this.t);
        }
    }

    @Override // io.dcloud.b, io.dcloud.common.DHInterface.IActivityHandler
    public /* bridge */ /* synthetic */ boolean isMultiProcessMode() {
        return super.isMultiProcessMode();
    }

    public boolean isSideBarCanRefresh() {
        if (this.D == null) {
            return false;
        }
        for (int i = 0; i < this.D.getChildCount(); i++) {
            View childAt = this.D.getChildAt(i);
            if (childAt != null && (childAt instanceof SplashView)) {
                return false;
            }
        }
        return true;
    }

    @Override // io.dcloud.common.DHInterface.IActivityDelegate
    public FrameLayout obtainActivityContentView() {
        return this.D;
    }

    @Override // io.dcloud.a, androidx.fragment.app.FragmentActivity, android.app.Activity
    public /* bridge */ /* synthetic */ void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }

    public void onAppActive(IApp iApp) {
    }

    public void onAppActive(String str) {
    }

    public void onAppStart(IApp iApp) {
    }

    public void onAppStart(String str) {
    }

    public void onAppStop(String str) {
    }

    @Override // io.dcloud.b, io.dcloud.common.DHInterface.IActivityHandler
    public /* bridge */ /* synthetic */ void onAsyncStartAppEnd(String str, Object obj) {
        super.onAsyncStartAppEnd(str, obj);
    }

    @Override // io.dcloud.b, io.dcloud.common.DHInterface.IActivityHandler
    public /* bridge */ /* synthetic */ Object onAsyncStartAppStart(String str) {
        return super.onAsyncStartAppStart(str);
    }

    @Override // io.dcloud.a, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        int iBackPressed;
        Logger.e("back", "WebAppActivity onBackPressed");
        if (isMultiProcessMode() || !(2 == (iBackPressed = backPressed()) || 1 == iBackPressed)) {
            k.a(this, TextUtils.isEmpty(this.e) ? l() : this.e, "onBack", this.A);
            super.onBackPressed();
        }
    }

    @Override // io.dcloud.a, io.dcloud.common.DHInterface.IOnCreateSplashView
    public /* bridge */ /* synthetic */ void onCloseSplash() {
        super.onCloseSplash();
    }

    @Override // io.dcloud.a, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public /* bridge */ /* synthetic */ void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // io.dcloud.a, io.dcloud.b, io.src.dcloud.adapter.DCloudBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        BaseInfo.startTime = System.currentTimeMillis();
        AppRuntime.restartWeex(getApplication(), null, getIntent().getStringExtra("appid"));
        LanguageUtil.updateDeviceDefLocalLanguage(this);
        Log.e("Html5Plus-onCreate", System.currentTimeMillis() + "");
        ShortCutUtil.activityNameSDK = getIntent().getStringExtra(IntentConst.WEBAPP_ACTIVITY_SHORTCUTACTIVITY);
        ShortCutUtil.mAutoCreateShortcut = getIntent().getBooleanExtra(IntentConst.WEBAPP_ACTIVITY_AUTOCREATESHORTCUT, true);
        Serializable serializableExtra = getIntent().getSerializableExtra(IntentConst.WEBAPP_ACTIVITY_EXTRAPRO);
        if (PdrUtil.isEmpty(serializableExtra)) {
            ShortCutUtil.extraProSDK = (HashMap) serializableExtra;
        }
        if (getIntent().hasExtra(IntentConst.INTENT_ORIENTATION)) {
            setRequestedOrientation(getIntent().getIntExtra(IntentConst.INTENT_ORIENTATION, 2));
        }
        deviceInjectionGeoLocationJs(getApplicationContext());
        Log.d("WebAppActivity", "onCreate");
        m();
        if (!this.p) {
            TestUtil.record(AbsoluteConst.RUN_5AP_TIME_KEY);
        }
        super.onCreate(bundle);
        BaseInfo.isFirstRun = false;
        FrameSwitchView frameSwitchView = FrameSwitchView.getInstance(this.that);
        if (!frameSwitchView.isInit()) {
            frameSwitchView.initView();
        }
        k();
    }

    public void onCreateAdSplash(Context context) {
        if (this.p || this.mSplashView == null || this.A != null) {
            return;
        }
        if (super.hasAdService()) {
            this.A = k.a(this.that, new b(), BaseInfo.sDefaultBootApp);
        }
        View view = this.A;
        this.z = view != null;
        if (view != null) {
            if (view.getParent() != null) {
                ((ViewGroup) this.A.getParent()).removeView(this.A);
            }
            View view2 = this.mSplashView;
            if (view2 instanceof ViewGroup) {
                ((ViewGroup) view2).addView(this.A, new ViewGroup.LayoutParams(-1, -1));
            }
            this.mSplashView.post(new Runnable() { // from class: io.dcloud.WebAppActivity$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.o();
                }
            });
        }
    }

    @Override // io.dcloud.a, android.app.Activity
    public /* bridge */ /* synthetic */ boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override // io.dcloud.a, io.dcloud.common.DHInterface.IOnCreateSplashView
    public Object onCreateSplash(Context context) throws IOException {
        Window window = getWindow();
        int i = Build.VERSION.SDK_INT;
        if (i <= 25) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.flags |= 1024;
            window.setAttributes(attributes);
        } else if (i > 25) {
            window.getDecorView().setSystemUiVisibility(window.getDecorView().getSystemUiVisibility() | 1280);
            window.setStatusBarColor(0);
            if (i >= 28 && window.getDecorView().getRootWindowInsets() != null) {
                WindowManager.LayoutParams attributes2 = window.getAttributes();
                attributes2.flags |= 1024;
                attributes2.layoutInDisplayCutoutMode = 1;
                window.setAttributes(attributes2);
            }
        }
        if (this.mSplashView != null) {
            return null;
        }
        BaseInfo.splashCreateTime = System.currentTimeMillis();
        a(context);
        return null;
    }

    @Override // io.dcloud.a, io.dcloud.b, io.src.dcloud.adapter.DCloudBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        ActionBus.getInstance().sendToBus(WebActivityOnDestroyAction.obtain());
        super.onDestroy();
        try {
            unregisterReceiver(this.s);
            this.y.clear();
            PermissionUtil.clearUseRejectedCache();
            ImageLoaderUtil.clearCache();
        } catch (Exception unused) {
        }
        Handler handler = this.q;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (getApplication() instanceof DCloudApplication) {
            ((DCloudApplication) getApplication()).removeActivityStatusListener(this.t);
        }
        HomeKeyEventBroadcastReceiver homeKeyEventBroadcastReceiver = this.u;
        if (homeKeyEventBroadcastReceiver != null) {
            unregisterReceiver(homeKeyEventBroadcastReceiver);
        }
        FrameSwitchView.getInstance(this.that).clearData();
        TabBarWebviewMgr.getInstance().setLancheTabBar(null);
        AlertDialog alertDialog = this.o;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.o.dismiss();
        }
        PlatformUtil.invokeMethod("io.dcloud.feature.weex.WeexDevtoolImpl", "unregisterReceiver", null, new Class[]{Context.class}, new Object[]{getContext()});
        this.o = null;
        this.v = null;
        Glide.get(this).clearMemory();
    }

    @Override // io.dcloud.a, androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public /* bridge */ /* synthetic */ boolean onKeyDown(int i, KeyEvent keyEvent) {
        return super.onKeyDown(i, keyEvent);
    }

    @Override // io.dcloud.a, io.dcloud.common.DHInterface.IKeyHandler
    public /* bridge */ /* synthetic */ boolean onKeyEventExecute(ISysEventListener.SysEventType sysEventType, int i, KeyEvent keyEvent) {
        return super.onKeyEventExecute(sysEventType, i, keyEvent);
    }

    @Override // io.dcloud.a, android.app.Activity, android.view.KeyEvent.Callback
    public /* bridge */ /* synthetic */ boolean onKeyLongPress(int i, KeyEvent keyEvent) {
        return super.onKeyLongPress(i, keyEvent);
    }

    @Override // io.dcloud.a, android.app.Activity, android.view.KeyEvent.Callback
    public /* bridge */ /* synthetic */ boolean onKeyUp(int i, KeyEvent keyEvent) {
        return super.onKeyUp(i, keyEvent);
    }

    @Override // io.dcloud.a, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public /* bridge */ /* synthetic */ void onLowMemory() {
        super.onLowMemory();
    }

    @Override // io.dcloud.a, io.src.dcloud.adapter.DCloudBaseActivity
    public void onNewIntentImpl(Intent intent) {
        super.onNewIntentImpl(intent);
    }

    @Override // io.dcloud.a, io.dcloud.b, androidx.fragment.app.FragmentActivity, android.app.Activity
    public /* bridge */ /* synthetic */ void onPause() {
        super.onPause();
    }

    @Override // io.dcloud.a
    public /* bridge */ /* synthetic */ void onPrivacySureAction() {
        super.onPrivacySureAction();
    }

    @Override // io.dcloud.a, androidx.fragment.app.FragmentActivity, android.app.Activity, androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
    public /* bridge */ /* synthetic */ void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
    }

    @Override // io.dcloud.a, io.dcloud.b, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        Log.e("Html5Plus-onResume", System.currentTimeMillis() + "");
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#333333")));
    }

    @Override // io.dcloud.a
    protected void onRuntimeCreate(Bundle bundle) {
        n();
        super.onRuntimeCreate(bundle);
    }

    @Override // io.dcloud.a, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public /* bridge */ /* synthetic */ void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        PlatformUtil.SCREEN_WIDTH(this.that);
        PlatformUtil.SCREEN_HEIGHT(this.that);
        PlatformUtil.MESURE_SCREEN_STATUSBAR_HEIGHT(this.that);
    }

    public void recordDialog(Dialog dialog) {
    }

    @Override // io.dcloud.b
    public /* bridge */ /* synthetic */ void registerLocalReceiver(io.dcloud.feature.internal.reflect.BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        super.registerLocalReceiver(broadcastReceiver, intentFilter);
    }

    @Override // io.dcloud.b
    public /* bridge */ /* synthetic */ Intent registerReceiver(io.dcloud.feature.internal.reflect.BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String str, Handler handler) {
        return super.registerReceiver(broadcastReceiver, intentFilter, str, handler);
    }

    @Override // io.dcloud.common.DHInterface.IActivityHandler
    public void removeClickStatusbarCallBack(ICallBack iCallBack) {
        ArrayList arrayList = this.y;
        if (arrayList == null || !arrayList.contains(iCallBack)) {
            return;
        }
        this.y.remove(iCallBack);
    }

    public void removeFromRecord(Dialog dialog) {
    }

    @Override // io.dcloud.b, io.dcloud.common.DHInterface.IActivityHandler
    public /* bridge */ /* synthetic */ void sendLocalBroadcast(Intent intent) {
        super.sendLocalBroadcast(intent);
    }

    public void setProgressView() {
        int i = 0;
        while (true) {
            if (i < this.D.getChildCount()) {
                View childAt = this.D.getChildAt(i);
                if (childAt != null && childAt == this.mSplashView) {
                    this.D.removeViewAt(i);
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        g gVar = new g(this.that);
        this.mSplashView = gVar;
        this.D.addView(gVar);
    }

    @Override // io.dcloud.a
    public /* bridge */ /* synthetic */ void setSecondPrivacyAlert() {
        super.setSecondPrivacyAlert();
    }

    @Override // io.dcloud.common.DHInterface.IActivityHandler
    public void setSideBarVisibility(int i) {
    }

    @Override // io.dcloud.common.DHInterface.IActivityHandler
    public void setSplashCloseListener(String str, ICallBack iCallBack) {
        this.G = iCallBack;
        if (this.mSplashView == null) {
            iCallBack.onCallBack(1, null);
            this.G = null;
        }
        if (this.B) {
            closeAppStreamSplash(str);
        }
        if (TextUtils.isEmpty(str)) {
            str = l();
        }
        k.a(this, str, "onWillCloseSplash", this.A);
        this.B = false;
    }

    @Override // io.dcloud.common.DHInterface.IActivityHandler
    public void setViewAsContentView(View view, FrameLayout.LayoutParams layoutParams) {
        if (this.D == null) {
            FrameLayout frameLayout = new FrameLayout(this.that);
            this.D = frameLayout;
            if (this.E != null) {
                this.D.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
                this.E.addView(this.D);
                setContentView(this.E);
            } else {
                setContentView(frameLayout);
            }
            DCKeyboardManager.getInstance().setContentView(this);
        }
        PlatformUtil.invokeMethod("io.dcloud.appstream.actionbar.StreamAppActionBarUtil", "checkNeedTitleView", null, new Class[]{Activity.class, String.class}, new Object[]{this.that, getIntent().getStringExtra("appid")});
        addViewToContentView(view);
        if (layoutParams != null) {
            view.setLayoutParams(layoutParams);
        }
        TestUtil.print(TestUtil.START_APP_SET_ROOTVIEW, "start" + view);
    }

    @Override // io.dcloud.common.DHInterface.IActivityHandler
    public void setWebViewIntoPreloadView(View view) {
        if (this.F == null) {
            FrameLayout frameLayout = new FrameLayout(this.that);
            this.F = frameLayout;
            this.D.addView(frameLayout, 0);
        }
        this.F.addView(view);
    }

    public void showDownloadDialog(String str, String str2) {
        if (this.mSplashShowing) {
            AlertDialog alertDialog = this.o;
            if (alertDialog == null || !alertDialog.isShowing()) {
                if (this.o == null) {
                    this.o = new AlertDialog.Builder(this.that).create();
                }
                this.o.setTitle(getString(R.string.dcloud_common_tips));
                if (TestUtil.PointTime.mEc == 20) {
                    this.o.setMessage(getString(R.string.dcloud_common_no_network_tips));
                } else if (str != null) {
                    this.o.setMessage(getString(R.string.dcloud_common_into) + str + getString(R.string.dcloud_common_fail) + getErrorTipMsg());
                } else {
                    this.o.setMessage(getString(R.string.dcloud_common_run_app_failed) + getErrorTipMsg());
                }
                this.o.setCanceledOnTouchOutside(false);
                this.o.setButton(-1, getString(R.string.dcloud_common_retry), new c(str2));
                this.o.setButton(-2, getString(R.string.dcloud_common_close), new d(str2));
                this.o.setOnKeyListener(new e(str2));
                this.o.show();
            }
        }
    }

    @Override // io.dcloud.common.DHInterface.IActivityHandler
    public void showSplashWaiting() {
        if (this.C) {
            View view = this.mSplashView;
            if (view instanceof SplashView) {
                ((SplashView) view).showWaiting();
            }
        }
    }

    @Override // io.dcloud.common.DHInterface.IActivityHandler
    public void sideBarHideMenu() {
    }

    @Override // io.dcloud.common.DHInterface.IActivityHandler
    public void sideBarShowMenu(String str, String str2, IWebview iWebview, String str3) {
    }

    @Override // io.dcloud.b, io.dcloud.common.DHInterface.IActivityHandler
    public /* bridge */ /* synthetic */ void unregisterReceiver(io.dcloud.feature.internal.reflect.BroadcastReceiver broadcastReceiver) {
        super.unregisterReceiver(broadcastReceiver);
    }

    @Override // io.dcloud.a, io.dcloud.common.DHInterface.IActivityHandler
    public void updateParam(String str, Object obj) {
        if ("progress".equals(str)) {
            View view = this.mSplashView;
            if (view instanceof g) {
                ((g) view).a(((Integer) obj).intValue());
                return;
            }
            return;
        }
        if ("setProgressView".equals(str)) {
            setProgressView();
        } else {
            super.updateParam(str, obj);
        }
    }

    @Override // io.dcloud.common.DHInterface.IActivityHandler
    public void updateSplash(String str) {
        KeyEvent.Callback callback = this.mSplashView;
        if (callback == null || !(callback instanceof ISplash)) {
            return;
        }
        ((ISplash) callback).setNameText(str);
    }

    public String getErrorTipMsg() {
        int i = TestUtil.PointTime.mEc;
        if (i == 4) {
            return "" + getString(R.string.dcloud_common_not_sd_card);
        }
        if (i == 9) {
            return "" + getString(R.string.dcloud_common_sd_not_space);
        }
        int i2 = TestUtil.PointTime.mEt;
        if (i2 == 1) {
            return "" + getString(R.string.dcloud_common_setting_download_failed);
        }
        if (i2 != 3 && i2 != 2) {
            return "";
        }
        return "" + getString(R.string.dcloud_common_app_res_download_failed);
    }

    @Override // io.dcloud.b, io.dcloud.common.DHInterface.IActivityHandler
    public /* bridge */ /* synthetic */ void registerReceiver(io.dcloud.feature.internal.reflect.BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        super.registerReceiver(broadcastReceiver, intentFilter);
    }

    private Bitmap a(String str, String str2, String str3) {
        Bitmap bitmapDecodeFile = null;
        try {
            if (!TextUtils.isEmpty(str) && new File(str).exists()) {
                Logger.d(Logger.MAIN_TAG, "use splashPath=" + str);
                bitmapDecodeFile = BitmapFactory.decodeFile(str);
                if (bitmapDecodeFile != null) {
                    this.C = false;
                    try {
                        DHFile.deleteFile(str);
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    Logger.d(Logger.MAIN_TAG, "use splashPath=" + str);
                }
            }
            if (bitmapDecodeFile == null && !TextUtils.isEmpty(str2) && new File(str2).exists()) {
                Logger.d(Logger.MAIN_TAG, "use splashPath=" + str2);
                bitmapDecodeFile = BitmapFactory.decodeFile(str2);
            }
            if (bitmapDecodeFile != null || TextUtils.isEmpty(str3) || !new File(str3).exists()) {
                return bitmapDecodeFile;
            }
            Logger.d(Logger.MAIN_TAG, "use splashPath=" + str3);
            return BitmapFactory.decodeFile(str3);
        } catch (Exception e3) {
            e3.printStackTrace();
            return bitmapDecodeFile;
        }
    }

    private Object a(Context context) throws IOException {
        boolean booleanExtra;
        KeyEvent.Callback callback;
        KeyEvent.Callback callback2;
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra("appid");
        Logger.d("splash", "WebAppActivity.onCreateSplash().appid=" + stringExtra + " this.appid=" + this.e + " this=" + this.that);
        StringBuilder sb = new StringBuilder("WebAppActivity.onCreateSplash().mSplashView is Null =");
        sb.append(this.mSplashView == null);
        Logger.d("splash", sb.toString());
        View view = this.mSplashView;
        if (view != null && view.getTag() != null) {
            if (this.mSplashView.getTag().equals(stringExtra)) {
                return null;
            }
            closeAppStreamSplash(this.mSplashView.getTag().toString());
        }
        this.C = true;
        Logger.d("WebAppActivity", "onCreateSplash;intent=" + intent);
        boolean booleanExtra2 = intent.getBooleanExtra(IntentConst.IS_START_FIRST_WEB, false);
        boolean z = intent.hasExtra(IntentConst.DIRECT_PAGE) && BaseInfo.isWap2AppAppid(stringExtra);
        if ((booleanExtra2 && !z) || !(booleanExtra = intent.getBooleanExtra(IntentConst.SPLASH_VIEW, true))) {
            return null;
        }
        Logger.d("WebAppActivity", "onCreateSplash hasSplash=" + booleanExtra);
        if (intent.getBooleanExtra(IntentConst.PL_AUTO_HIDE, false)) {
            return null;
        }
        String stringExtra2 = intent.getStringExtra(IntentConst.WEBAPP_ACTIVITY_SPLASH_MODE);
        intent.removeExtra(IntentConst.WEBAPP_ACTIVITY_SPLASH_MODE);
        if (stringExtra2 == null || "".equals(stringExtra2.trim()) || (!"auto".equals(stringExtra2) && !"default".equals(stringExtra2))) {
            stringExtra2 = "auto";
        }
        Logger.d("WebAppActivity", "onCreateSplash __splash_mode__=" + stringExtra2);
        if (intent.getBooleanExtra(IntentConst.WEBAPP_ACTIVITY_HIDE_STREAM_SPLASH, false)) {
            setViewAsContentView(new View(context), null);
            this.x = System.currentTimeMillis();
            this.mSplashShowing = true;
            return null;
        }
        boolean z2 = z;
        if (intent.getBooleanExtra(IntentConst.WEBAPP_ACTIVITY_HAS_STREAM_SPLASH, false)) {
            if (this.mSplashView == null) {
                if ("auto".equals(stringExtra2)) {
                    if (!z2) {
                        StringBuilder sb2 = new StringBuilder();
                        String str = StringConst.STREAMAPP_KEY_ROOTPATH;
                        sb2.append(str);
                        sb2.append("splash_temp/");
                        sb2.append(stringExtra);
                        sb2.append(".png");
                        this.w = a(sb2.toString(), intent.getStringExtra(IntentConst.APP_SPLASH_PATH), str + "splash/" + stringExtra + ".png");
                    }
                    if (this.w != null) {
                        SplashView splashView = new SplashView(this.that, this.w);
                        this.mSplashView = splashView;
                        if (!this.C) {
                            splashView.showWaiting(SplashView.STYLE_BLACK);
                        }
                    }
                }
                if (this.mSplashView == null) {
                    String stringExtra3 = intent.getStringExtra(IntentConst.WEBAPP_ACTIVITY_APPICON);
                    if (!TextUtils.isEmpty(stringExtra3) && new File(stringExtra3).exists()) {
                        this.w = BitmapFactory.decodeFile(stringExtra3);
                    }
                    this.mSplashView = a(context, this.w, intent.getStringExtra(IntentConst.NAME), stringExtra);
                    if (this.w == null && PdrUtil.isEmpty(stringExtra) && (callback2 = this.mSplashView) != null && (callback2 instanceof ISplash)) {
                        ((ISplash) callback2).setImageBitmap(BitmapFactory.decodeResource(getResources(), PdrR.getInt(getContext(), "drawable", AbsoluteConst.JSON_KEY_ICON)));
                    }
                }
            }
            this.mSplashView.setTag(stringExtra);
            setViewAsContentView(this.mSplashView, null);
            this.x = System.currentTimeMillis();
            this.mSplashShowing = true;
            Logger.e("IAN", "onCreateSplash aliyun ended");
            return null;
        }
        if (this.mSplashView != null) {
            return null;
        }
        try {
            if ("auto".equals(stringExtra2)) {
                if (!z2) {
                    StringBuilder sb3 = new StringBuilder();
                    String str2 = StringConst.STREAMAPP_KEY_ROOTPATH;
                    sb3.append(str2);
                    sb3.append("splash_temp/");
                    sb3.append(stringExtra);
                    sb3.append(".png");
                    this.w = a(sb3.toString(), intent.getStringExtra(IntentConst.APP_SPLASH_PATH), str2 + "splash/" + stringExtra + ".png");
                }
                if (this.w == null) {
                    String string = SP.getOrCreateBundle(getContext(), "pdr").getString(SP.UPDATE_SPLASH_IMG_PATH, "");
                    if (!TextUtils.isEmpty(string)) {
                        try {
                            if (PdrUtil.isDeviceRootDir(string)) {
                                this.w = BitmapFactory.decodeFile(string);
                            } else {
                                InputStream inputStreamOpen = getResources().getAssets().open(string);
                                this.w = BitmapFactory.decodeStream(inputStreamOpen);
                                inputStreamOpen.close();
                            }
                        } catch (Exception unused) {
                            this.w = null;
                        }
                    }
                    if (this.w == null && !this.p) {
                        this.w = BitmapFactory.decodeResource(getResources(), PdrR.getInt(context, "drawable", "splash"));
                    }
                }
                if (this.w != null) {
                    SplashView splashView2 = new SplashView(this.that, this.w);
                    this.mSplashView = splashView2;
                    if (!this.C) {
                        splashView2.showWaiting(SplashView.STYLE_BLACK);
                    }
                }
            }
            if (this.mSplashView == null) {
                String stringExtra4 = intent.getStringExtra(IntentConst.WEBAPP_ACTIVITY_APPICON);
                if (!TextUtils.isEmpty(stringExtra4) && new File(stringExtra4).exists()) {
                    this.w = BitmapFactory.decodeFile(stringExtra4);
                }
                Log.d(Logger.MAIN_TAG, "use defaultSplash");
                this.mSplashView = a(context, this.w, intent.getStringExtra(IntentConst.NAME), stringExtra);
                if (this.w == null && PdrUtil.isEmpty(stringExtra) && (callback = this.mSplashView) != null && (callback instanceof ISplash)) {
                    ((ISplash) callback).setImageBitmap(BitmapFactory.decodeResource(getResources(), PdrR.getInt(getContext(), "drawable", AbsoluteConst.JSON_KEY_ICON)));
                }
            }
            this.mSplashView.setTag(stringExtra);
            setViewAsContentView(this.mSplashView, null);
            this.x = System.currentTimeMillis();
            this.mSplashShowing = true;
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private String a(String str) throws PackageManager.NameNotFoundException {
        if (DCloudAdapterUtil.isPlugin()) {
            return null;
        }
        try {
            ApplicationInfo applicationInfo = this.that.getPackageManager().getApplicationInfo(getPackageName(), 128);
            Bundle bundle = applicationInfo != null ? applicationInfo.metaData : null;
            if (bundle == null || PdrUtil.isEmpty(bundle.get(str))) {
                return null;
            }
            return String.valueOf(bundle.get(str));
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private View a(Context context, Bitmap bitmap, String str, String str2) throws PackageManager.NameNotFoundException {
        boolean z;
        String strA = a("DCLOUD_STREAMAPP_CHANNEL");
        if (PdrUtil.isEmpty(str2) && PdrUtil.isEmpty(str)) {
            str = getString(PdrR.getInt(context, "string", IntentConst.WEBAPP_ACTIVITY_APPNAME));
            z = true;
        } else {
            z = false;
        }
        return "_12214060304".equals(strA) ? (View) PlatformUtil.newInstance("io.dcloud.html5pframework.splash.SplashView4Yunos", new Class[]{Context.class, Bitmap.class, String.class}, new Object[]{context, bitmap, str}) : new SplashViewDBackground(context, bitmap, str, z);
    }
}
