package io.dcloud;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import com.hjq.permissions.Permission;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.performance.WXInstanceApm;
import io.dcloud.application.DCLoudApplicationImpl;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.IActivityHandler;
import io.dcloud.common.DHInterface.ICallBack;
import io.dcloud.common.DHInterface.ICore;
import io.dcloud.common.DHInterface.IKeyHandler;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.DHInterface.IOnCreateSplashView;
import io.dcloud.common.DHInterface.ISysEventListener;
import io.dcloud.common.DHInterface.IWebViewFactory;
import io.dcloud.common.DHInterface.IWebViewInstallListener;
import io.dcloud.common.adapter.ui.webview.WebViewFactory;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.adapter.util.MessageHandler;
import io.dcloud.common.adapter.util.PermissionUtil;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.adapter.util.UEH;
import io.dcloud.common.constant.IntentConst;
import io.dcloud.common.core.permission.PermissionControler;
import io.dcloud.common.ui.Info.AndroidPrivacyResponse;
import io.dcloud.common.ui.PrivacyManager;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.DensityUtils;
import io.dcloud.common.util.DisplayManagerChangeListener;
import io.dcloud.common.util.ErrorDialogUtil;
import io.dcloud.common.util.ImageLoaderUtil;
import io.dcloud.common.util.NativeCrashManager;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.TestUtil;
import io.dcloud.common.util.net.http.CookieManager;
import io.dcloud.feature.internal.sdk.SDK;
import io.dcloud.p.e3;
import io.dcloud.p.e4;
import io.dcloud.p.q3;
import io.dcloud.p.r3;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
abstract class a extends io.dcloud.b implements IOnCreateSplashView, IKeyHandler {
    private String g;
    private String h;
    AlertDialog i;
    private DisplayManagerChangeListener m;
    Runnable n;
    String d = null;
    String e = "Main_App";
    EntryProxy f = null;
    int j = 20;
    private int k = 9101;
    private int l = 9102;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    /* renamed from: io.dcloud.a$a, reason: collision with other inner class name */
    class RunnableC0020a implements Runnable {
        final /* synthetic */ Bundle a;

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        /* renamed from: io.dcloud.a$a$a, reason: collision with other inner class name */
        class RunnableC0021a implements Runnable {
            RunnableC0021a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                RunnableC0020a runnableC0020a = RunnableC0020a.this;
                a.this.onRuntimeCreate(runnableC0020a.a);
            }
        }

        RunnableC0020a(Bundle bundle) {
            this.a = bundle;
        }

        @Override // java.lang.Runnable
        public void run() throws ClassNotFoundException {
            DeviceInfo.initPath(a.this.that);
            ImageLoaderUtil.initImageLoader(a.this.that);
            ImageLoaderUtil.initImageLoaderL(a.this.that);
            io.dcloud.p.k.a(a.this, null, "ba_pull", null);
            a aVar = a.this;
            aVar.a(aVar.getIntent());
            a.this.d = "Main_Path_" + a.this.e;
            io.dcloud.p.i.a("Main_App");
            Logger.d(a.this.d, "onCreate appid=" + a.this.e);
            a.this.a(new RunnableC0021a());
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b implements ICallBack {

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        /* renamed from: io.dcloud.a$b$a, reason: collision with other inner class name */
        class ViewOnClickListenerC0022a implements View.OnClickListener {
            ViewOnClickListenerC0022a() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Process.killProcess(Process.myPid());
            }
        }

        b() {
        }

        @Override // io.dcloud.common.DHInterface.ICallBack
        public Object onCallBack(int i, Object obj) {
            e4 e4Var = new e4(a.this.that);
            e4Var.b(a.this.getContext().getString(R.string.dcloud_common_tips));
            e4Var.a(a.this.getContext().getString(R.string.dcloud_ua_version_verify_fail_tips));
            e4Var.b(a.this.that.getString(android.R.string.ok), new ViewOnClickListenerC0022a());
            e4Var.show();
            e4Var.b((int) (a.this.getResources().getDisplayMetrics().widthPixels * 0.9d));
            e4Var.a(17);
            return null;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c implements ICallBack {
        final /* synthetic */ ICallBack a;
        final /* synthetic */ ICallBack b;
        final /* synthetic */ Runnable c;

        c(ICallBack iCallBack, ICallBack iCallBack2, Runnable runnable) {
            this.a = iCallBack;
            this.b = iCallBack2;
            this.c = runnable;
        }

        @Override // io.dcloud.common.DHInterface.ICallBack
        public Object onCallBack(int i, Object obj) {
            int iIntValue = ((Integer) this.a.onCallBack(i, obj)).intValue();
            if (iIntValue == 1) {
                this.b.onCallBack(i, null);
                return Boolean.FALSE;
            }
            if (iIntValue == 2) {
                Runnable runnable = this.c;
                if (runnable != null) {
                    runnable.run();
                }
                return Boolean.FALSE;
            }
            if (iIntValue != 3) {
                return Boolean.FALSE;
            }
            Runnable runnable2 = this.c;
            if (runnable2 != null) {
                runnable2.run();
            }
            return Boolean.TRUE;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class d implements ICallBack {
        final /* synthetic */ ICallBack a;

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        /* renamed from: io.dcloud.a$d$a, reason: collision with other inner class name */
        class C0023a implements ICallBack {
            final /* synthetic */ TextView[] a;

            C0023a(TextView[] textViewArr) {
                this.a = textViewArr;
            }

            @Override // io.dcloud.common.DHInterface.ICallBack
            public Object onCallBack(int i, Object obj) {
                if (obj == null) {
                    return null;
                }
                this.a[0] = (TextView) obj;
                return null;
            }
        }

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class b implements ICallBack {
            final /* synthetic */ Dialog a;

            b(Dialog dialog) {
                this.a = dialog;
            }

            @Override // io.dcloud.common.DHInterface.ICallBack
            public Object onCallBack(int i, Object obj) {
                this.a.dismiss();
                return d.this.a.onCallBack(i, obj);
            }
        }

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class c implements IWebViewInstallListener {
            final /* synthetic */ TextView[] a;

            /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
            /* renamed from: io.dcloud.a$d$c$a, reason: collision with other inner class name */
            class RunnableC0024a implements Runnable {
                final /* synthetic */ int a;

                RunnableC0024a(int i) {
                    this.a = i;
                }

                @Override // java.lang.Runnable
                public void run() {
                    c cVar = c.this;
                    cVar.a[0].setText(String.format(a.this.getContext().getString(R.string.dcloud_x5_download_progress), Integer.valueOf(this.a)));
                }
            }

            c(TextView[] textViewArr) {
                this.a = textViewArr;
            }

            @Override // io.dcloud.common.DHInterface.IWebViewInstallListener
            public void onDownloadFinish(int i) {
            }

            @Override // io.dcloud.common.DHInterface.IWebViewInstallListener
            public void onDownloadProgress(int i) {
                if (this.a[0] != null) {
                    new Handler(Looper.getMainLooper()).post(new RunnableC0024a(i));
                }
            }

            @Override // io.dcloud.common.DHInterface.IWebViewInstallListener
            public void onInstallFinish(int i) {
            }
        }

        d(ICallBack iCallBack) {
            this.a = iCallBack;
        }

        @Override // io.dcloud.common.DHInterface.ICallBack
        public Object onCallBack(int i, Object obj) {
            TextView[] textViewArr = {null};
            a aVar = a.this;
            Dialog dialogA = aVar.a(aVar, new C0023a(textViewArr));
            dialogA.show();
            WebViewFactory.setOtherCallBack(new b(dialogA));
            WebViewFactory.setWebViewInstallListener(new c(textViewArr));
            return null;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class e implements ICallBack {
        final /* synthetic */ ICallBack a;
        final /* synthetic */ boolean b;
        final /* synthetic */ ICallBack c;

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        /* renamed from: io.dcloud.a$e$a, reason: collision with other inner class name */
        class ViewOnClickListenerC0025a implements View.OnClickListener {
            ViewOnClickListenerC0025a() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AppRuntime.initX5(a.this.getApplication(), true, null);
                e.this.a.onCallBack(1, null);
            }
        }

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class b implements View.OnClickListener {
            b() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                e.this.c.onCallBack(-1, null);
            }
        }

        e(ICallBack iCallBack, boolean z, ICallBack iCallBack2) {
            this.a = iCallBack;
            this.b = z;
            this.c = iCallBack2;
        }

        @Override // io.dcloud.common.DHInterface.ICallBack
        public Object onCallBack(int i, Object obj) {
            if (i == 0) {
                this.a.onCallBack(0, null);
            } else if (i == 1) {
                if (this.b) {
                    e4 e4Var = new e4(a.this.that);
                    e4Var.b(a.this.getContext().getString(R.string.dcloud_common_tips));
                    e4Var.a(a.this.getContext().getString(R.string.dcloud_x5_download_without_wifi));
                    e4Var.b(a.this.that.getString(R.string.dcloud_common_allow), new ViewOnClickListenerC0025a());
                    e4Var.a(a.this.that.getString(R.string.dcloud_common_no_allow), new b());
                    e4Var.show();
                } else {
                    this.c.onCallBack(-1, null);
                }
            }
            return null;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class f implements DialogInterface.OnClickListener {
        f() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            Process.killProcess(Process.myPid());
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class h implements e3.b {
        h() {
        }

        @Override // io.dcloud.p.e3.b
        public void a(String str, boolean z) {
            DeviceInfo.oaids = str;
            SP.setBundleData(a.this.getContext(), BaseInfo.PDR, "android_ten_ids", str);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class j extends PermissionUtil.Request {
        j() {
        }

        @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
        public void onDenied(String str) {
            if (str.equals(Permission.READ_PHONE_STATE) && a.this.g != null && a.this.g.equalsIgnoreCase("ALWAYS")) {
                int i = PdrR.getInt(a.this, "string", "dcloud_permission_read_phone_state_message");
                AlertDialog alertDialog = a.this.i;
                if (alertDialog == null || !alertDialog.isShowing()) {
                    a.this.a(str, i);
                    return;
                }
                return;
            }
            if (str.equals(PermissionUtil.PMS_STORAGE)) {
                if (a.this.h != null && a.this.h.equals("once")) {
                    a.this.checkAndRequestPhoneState();
                    new Handler().postDelayed(a.this.n, r0.j);
                    return;
                }
                int i2 = PdrR.getInt(a.this, "string", "dcloud_permission_write_external_storage_message");
                AlertDialog alertDialog2 = a.this.i;
                if (alertDialog2 == null || !alertDialog2.isShowing()) {
                    a.this.a(PermissionUtil.convert2SystemPermission(str), i2);
                }
            }
        }

        @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
        public void onGranted(String str) {
            if (str.equals(PermissionUtil.PMS_STORAGE)) {
                DeviceInfo.initPath(a.this.that);
                a.this.checkAndRequestPhoneState();
                new Handler().postDelayed(a.this.n, r0.j);
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class k implements DialogInterface.OnClickListener {
        k() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            a.this.finish();
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class l implements DialogInterface.OnClickListener {
        final /* synthetic */ String a;

        l(String str) {
            this.a = str;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(a.this, PermissionUtil.convert2SystemPermission(this.a))) {
                a.this.a(new String[]{this.a});
                return;
            }
            try {
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", a.this.getPackageName(), null));
                int i2 = a.this.k;
                if (!this.a.equalsIgnoreCase(Permission.READ_PHONE_STATE)) {
                    i2 = a.this.l;
                }
                a.this.startActivityForResult(intent, i2);
            } catch (Exception unused) {
                a.this.finish();
                Process.killProcess(Process.myPid());
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class m implements ICallBack {
        m() {
        }

        @Override // io.dcloud.common.DHInterface.ICallBack
        public Object onCallBack(int i, Object obj) {
            String str = BaseInfo.minUserAgentVersion;
            if (PdrUtil.isEmpty(str)) {
                str = WXInstanceApm.VALUE_ERROR_CODE_DEFAULT;
            }
            WebViewFactory.resetSysWebViewState();
            WebViewFactory.resetUA();
            int i2 = WebViewFactory.verifyVersion(WebViewFactory.getWebViewUserAgentVersion(a.this.getContext()), str) ? 2 : 1;
            if (i == 1 && obj != null && WebViewFactory.verifyVersion(WebViewFactory.getWebViewUserAgentVersion(a.this.getApplication(), ((IWebViewFactory) obj).getDefWebViewUA(a.this.getApplication())), str)) {
                i2 = 3;
            }
            return Integer.valueOf(i2);
        }
    }

    a() {
    }

    private void h() {
        String metaValue = AndroidResources.getMetaValue("DClOUD_SECURITY_POLICY");
        if (TextUtils.isEmpty(metaValue) || !metaValue.equals("safe")) {
            BaseInfo.isDefense = false;
        } else {
            BaseInfo.isDefense = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        if ((!SDK.isUniMPSDK() || TextUtils.isEmpty(SDK.customOAID)) && PdrUtil.isSupportOaid() && !AppRuntime.hasPrivacyForNotShown(this)) {
            if (PdrUtil.isEmpty(DeviceInfo.oaids) || DeviceInfo.oaids.equals(Operators.OR)) {
                DeviceInfo.oaids = SP.getBundleData(getContext(), BaseInfo.PDR, "android_ten_ids");
                new e3(new h()).b(this);
            }
        }
    }

    private void j() {
        q3.a().a(new r3().getPdrModuleMap());
    }

    public void checkAndRequestPhoneState() {
        String str = this.g;
        if (str != null) {
            if (!str.equalsIgnoreCase("once")) {
                if (this.g.equalsIgnoreCase("always")) {
                    a(new String[]{Permission.READ_PHONE_STATE});
                }
            } else {
                if (SP.getBundleData(getContext(), "dcloud_phone_read_state", "isshow").equals("1")) {
                    return;
                }
                SP.setBundleData(getContext(), "dcloud_phone_read_state", "isshow", "1");
                a(new String[]{Permission.READ_PHONE_STATE});
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0053  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void checkAndRequestStoragePermission() {
        /*
            r7 = this;
            boolean r0 = io.dcloud.feature.internal.sdk.SDK.isUniMPSDK()
            if (r0 == 0) goto L14
            android.os.Handler r0 = new android.os.Handler
            r0.<init>()
            java.lang.Runnable r1 = r7.n
            int r2 = r7.j
            long r2 = (long) r2
            r0.postDelayed(r1, r2)
            return
        L14:
            java.lang.String r0 = r7.h
            java.lang.String r1 = "android.permission.WRITE_EXTERNAL_STORAGE"
            r2 = 0
            r3 = 1
            if (r0 == 0) goto L55
            java.lang.String r4 = "once"
            boolean r0 = r0.equalsIgnoreCase(r4)
            if (r0 == 0) goto L44
            android.content.Context r0 = r7.getContext()
            java.lang.String r4 = "dcloud_phone_read_state"
            java.lang.String r5 = "isStorageRequest"
            java.lang.String r0 = io.dcloud.common.adapter.util.SP.getBundleData(r0, r4, r5)
            java.lang.String r6 = "1"
            boolean r0 = r0.equals(r6)
            if (r0 != 0) goto L53
            java.lang.String[] r0 = new java.lang.String[r3]
            r0[r2] = r1
            android.content.Context r1 = r7.getContext()
            io.dcloud.common.adapter.util.SP.setBundleData(r1, r4, r5, r6)
            goto L59
        L44:
            java.lang.String r0 = r7.h
            java.lang.String r4 = "always"
            boolean r0 = r0.equalsIgnoreCase(r4)
            if (r0 == 0) goto L53
            java.lang.String[] r0 = new java.lang.String[r3]
            r0[r2] = r1
            goto L59
        L53:
            r0 = 0
            goto L59
        L55:
            java.lang.String[] r0 = new java.lang.String[r3]
            r0[r2] = r1
        L59:
            if (r0 == 0) goto L5f
            r7.a(r0)
            return
        L5f:
            r7.checkAndRequestPhoneState()
            android.os.Handler r0 = new android.os.Handler
            r0.<init>()
            java.lang.Runnable r1 = r7.n
            int r2 = r7.j
            long r2 = (long) r2
            r0.postDelayed(r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.a.checkAndRequestStoragePermission():void");
    }

    protected void displayBriefMemory() {
        ((ActivityManager) getSystemService("activity")).getMemoryInfo(new ActivityManager.MemoryInfo());
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = resources.getConfiguration();
        try {
            if (!"none".equals(BaseInfo.sFontScale)) {
                float f2 = configuration.fontScale;
                float f3 = BaseInfo.sFontScaleFloat;
                if (f2 != f3) {
                    configuration.fontScale = f3;
                }
            } else if (configuration.fontScale != 1.0f) {
                configuration.fontScale = 1.0f;
                return resources;
            }
        } catch (Exception unused) {
        }
        return resources;
    }

    protected void handleNewIntent(Intent intent) {
        EntryProxy entryProxy;
        setIntent(intent);
        a(intent);
        StringBuilder sb = new StringBuilder("BaseActivity handleNewIntent =");
        sb.append(this.e);
        sb.append(";");
        sb.append(intent.getFlags() != 274726912);
        Logger.d("syncStartApp", sb.toString());
        if (intent.getFlags() != 274726912 && (entryProxy = this.f) != null) {
            entryProxy.onNewIntent(this.that, intent);
        }
        if (BaseInfo.SyncDebug && intent.getBooleanExtra("debug_restart", false)) {
            EntryProxy entryProxy2 = this.f;
            if (entryProxy2 == null || entryProxy2.getCoreHandler() == null) {
                intent.setFlags(335544320);
                startActivity(intent);
                Runtime.getRuntime().exit(0);
            } else {
                String stringExtra = intent.getStringExtra("appid");
                ICore coreHandler = this.f.getCoreHandler();
                IMgr.MgrType mgrType = IMgr.MgrType.AppMgr;
                if (PdrUtil.isEmpty(stringExtra)) {
                    stringExtra = "snc:CID";
                }
                coreHandler.dispatchEvent(mgrType, 3, stringExtra);
            }
        }
    }

    public boolean hasAdService() {
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i2, int i3, Intent intent) {
        String str;
        AlertDialog alertDialog;
        Logger.d(this.d, "onActivityResult");
        PermissionUtil.onActivityResult(this.that, i2, i3, intent);
        EntryProxy entryProxy = this.f;
        if (entryProxy != null) {
            entryProxy.onActivityExecute(this.that, ISysEventListener.SysEventType.onActivityResult, new Object[]{Integer.valueOf(i2), Integer.valueOf(i3), intent});
        }
        if (i2 == this.k && (str = this.g) != null && str.equalsIgnoreCase("always") && (alertDialog = this.i) != null && !alertDialog.isShowing()) {
            a(new String[]{Permission.READ_PHONE_STATE});
        }
        if (i2 == this.l) {
            String str2 = this.h;
            if (str2 == null || !(str2.equalsIgnoreCase("once") || this.h.equalsIgnoreCase("none"))) {
                a(new String[]{Permission.WRITE_EXTERNAL_STORAGE});
            }
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        EntryProxy entryProxy;
        if (!BaseInfo.USE_ACTIVITY_HANDLE_KEYEVENT) {
            super.onBackPressed();
        } else {
            if (onKeyEventExecute(ISysEventListener.SysEventType.onKeyUp, 4, null) || (entryProxy = this.f) == null) {
                return;
            }
            entryProxy.destroy(this.that);
            super.onBackPressed();
        }
    }

    public void onCloseSplash() {
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        try {
            Logger.d(this.d, "onConfigurationChanged");
            int i2 = getResources().getConfiguration().orientation;
            EntryProxy entryProxy = this.f;
            if (entryProxy != null) {
                entryProxy.onConfigurationChanged(this.that, i2);
            }
            super.onConfigurationChanged(configuration);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        PrivacyManager.getInstance().restartPrivacyDialogIfNeed(this);
    }

    @Override // io.dcloud.b, io.src.dcloud.adapter.DCloudBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Build.VERSION.SDK_INT >= 29) {
            getWindow().setNavigationBarContrastEnforced(false);
        }
        if (PrivacyManager.isDebugMode()) {
            PrivacyManager.getInstance().reInit(getContext(), null);
        }
        j();
        h();
        String metaValue = AndroidResources.getMetaValue("DCLOUD_READ_PHONE_STATE");
        this.g = metaValue;
        if (metaValue == null) {
            this.g = "none";
        }
        this.n = new RunnableC0020a(bundle);
        String metaValue2 = AndroidResources.getMetaValue("DCLOUD_UNISTATISTICS");
        BaseInfo.isUniStatistics = false;
        if (!TextUtils.isEmpty(metaValue2) && Boolean.parseBoolean(metaValue2)) {
            BaseInfo.isUniStatistics = true;
        }
        if (!DCLoudApplicationImpl.self().isInit()) {
            String string = getString(R.string.dcloud_Init_fail_tips);
            BaseInfo.USE_ACTIVITY_HANDLE_KEYEVENT = true;
            ErrorDialogUtil.showErrorTipsAlert(this, string, new f());
            return;
        }
        String metaValue3 = AndroidResources.getMetaValue("DCLOUD_WRITE_EXTERNAL_STORAGE");
        this.h = metaValue3;
        if (metaValue3 == null) {
            this.h = "none";
        }
        PrivacyManager.getInstance().showPrivacyDialog(this, new g(), false, false);
        UEH.catchUncaughtException(this.that.getApplicationContext());
        Log.d("download_manager", "BaseActivity onCreate");
        TestUtil.print(TestUtil.START_STREAM_APP, "BaseActivity onCreate");
        onRuntimePreCreate(bundle);
        onCreateSplash(this.that);
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        Logger.d(this.d, "onCreateOptionsMenu appid=" + this.e);
        EntryProxy entryProxy = this.f;
        return entryProxy != null ? entryProxy.onActivityExecute(this.that, ISysEventListener.SysEventType.onCreateOptionMenu, menu) : super.onCreateOptionsMenu(menu);
    }

    public abstract Object onCreateSplash(Context context);

    @Override // io.dcloud.b, io.src.dcloud.adapter.DCloudBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        DisplayManagerChangeListener displayManagerChangeListener = this.m;
        if (displayManagerChangeListener != null) {
            displayManagerChangeListener.unregister();
        }
        io.dcloud.p.i.b("Main_App");
        Logger.d(this.d, "onDestroy appid=" + this.e);
        EntryProxy entryProxy = this.f;
        if (entryProxy != null) {
            entryProxy.onStop(this.that);
        }
        HashMap<String, BaseInfo.CmtInfo> map = BaseInfo.mLaunchers;
        if (map != null) {
            map.clear();
        }
        MessageHandler.removeCallbacksAndMessages();
        PermissionControler.clearCRequestPermissionsCache();
        q3.a().b();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i2, KeyEvent keyEvent) {
        Logger.e("back", "BaseActivity onKeyDown");
        if (!BaseInfo.USE_ACTIVITY_HANDLE_KEYEVENT) {
            return super.onKeyDown(i2, keyEvent);
        }
        boolean zOnKeyEventExecute = keyEvent.getRepeatCount() == 0 ? onKeyEventExecute(ISysEventListener.SysEventType.onKeyDown, i2, keyEvent) : onKeyEventExecute(ISysEventListener.SysEventType.onKeyLongPress, i2, keyEvent);
        if (zOnKeyEventExecute && i2 == 4) {
            onBackPressed();
        }
        return zOnKeyEventExecute ? zOnKeyEventExecute : super.onKeyDown(i2, keyEvent);
    }

    public boolean onKeyEventExecute(ISysEventListener.SysEventType sysEventType, int i2, KeyEvent keyEvent) {
        EntryProxy entryProxy = this.f;
        if (entryProxy != null) {
            return entryProxy.onActivityExecute(this.that, sysEventType, new Object[]{Integer.valueOf(i2), keyEvent});
        }
        return false;
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i2, KeyEvent keyEvent) {
        if (!BaseInfo.USE_ACTIVITY_HANDLE_KEYEVENT) {
            return super.onKeyLongPress(i2, keyEvent);
        }
        EntryProxy entryProxy = this.f;
        boolean zOnActivityExecute = entryProxy != null ? entryProxy.onActivityExecute(this.that, ISysEventListener.SysEventType.onKeyLongPress, new Object[]{Integer.valueOf(i2), keyEvent}) : false;
        return zOnActivityExecute ? zOnActivityExecute : super.onKeyLongPress(i2, keyEvent);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i2, KeyEvent keyEvent) {
        EntryProxy entryProxy;
        if (!BaseInfo.USE_ACTIVITY_HANDLE_KEYEVENT) {
            return super.onKeyUp(i2, keyEvent);
        }
        Logger.d(this.d, "onKeyUp");
        boolean zOnActivityExecute = false;
        if (i2 != 4 && (entryProxy = this.f) != null) {
            zOnActivityExecute = entryProxy.onActivityExecute(this.that, ISysEventListener.SysEventType.onKeyUp, new Object[]{Integer.valueOf(i2), keyEvent});
        }
        return zOnActivityExecute ? zOnActivityExecute : super.onKeyUp(i2, keyEvent);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        Logger.d(this.d, "onLowMemory");
        displayBriefMemory();
    }

    @Override // io.src.dcloud.adapter.DCloudBaseActivity
    public void onNewIntentImpl(Intent intent) {
        super.onNewIntentImpl(intent);
        Logger.d("syncStartApp", "BaseActivity onNewIntent appid=" + this.e);
        handleNewIntent(intent);
    }

    @Override // io.dcloud.b, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        Logger.d(this.d, "onPause appid=" + this.e);
        EntryProxy entryProxy = this.f;
        if (entryProxy != null) {
            entryProxy.onPause(this.that);
        }
    }

    public void onPrivacySureAction() {
        i();
        checkAndRequestStoragePermission();
        AppRuntime.initUniappPlugin(getApplication());
        AppRuntime.initUTS();
        CookieManager.initCookieConfig(getApplication());
        NativeCrashManager.initNativeCrash(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity, androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
    public void onRequestPermissionsResult(int i2, String[] strArr, int[] iArr) {
        PermissionUtil.onSystemPermissionsResult(this.that, i2, strArr, iArr);
        EntryProxy entryProxy = this.f;
        if (entryProxy != null) {
            entryProxy.onActivityExecute(this.that, ISysEventListener.SysEventType.onRequestPermissionsResult, new Object[]{Integer.valueOf(i2), strArr, iArr});
        }
        PermissionControler.runNextRequestPermission(this, i2);
    }

    @Override // io.dcloud.b, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        a(getIntent());
        PermissionUtil.onRequestSysPermissionResume(this.that);
        Logger.d(this.d, "onResume appid=" + this.e);
        EntryProxy entryProxy = this.f;
        if (entryProxy != null) {
            entryProxy.onResume(this.that);
        }
        if (BaseInfo.mDeStatusBarBackground == -111111) {
            BaseInfo.mDeStatusBarBackground = getWindow().getStatusBarColor();
        }
    }

    protected void onRuntimeCreate(Bundle bundle) {
        Logger.d(this.d, "onRuntimeCreate appid=" + this.e);
        EntryProxy entryProxyInit = EntryProxy.init(this.that);
        this.f = entryProxyInit;
        entryProxyInit.onCreate(this.that, bundle, BaseInfo.sRuntimeMode, (IOnCreateSplashView) null);
        if (this.m == null) {
            this.m = new DisplayManagerChangeListener((DisplayManager) getSystemService("display"), this.that, this.f);
        }
        this.m.register();
    }

    protected void onRuntimePreCreate(Bundle bundle) {
        Log.d(this.d, "onRuntimePreCreate appid=" + this.e);
        this.that.getWindow().setFormat(-3);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        if (bundle != null && getIntent() != null && getIntent().getExtras() != null) {
            bundle.putAll(getIntent().getExtras());
        }
        Logger.d(this.d, "onSaveInstanceState");
        EntryProxy entryProxy = this.f;
        if (entryProxy != null) {
            entryProxy.onActivityExecute(this.that, ISysEventListener.SysEventType.onSaveInstanceState, new Object[]{bundle});
        }
        super.onSaveInstanceState(bundle);
    }

    public void setSecondPrivacyAlert() {
        PrivacyManager.getInstance().showPrivacyDialog(this, new i(), true, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void updateParam(String str, Object obj) {
        if ("tab_change".equals(str)) {
            Logger.d("BaseActivity updateParam newintent value(appid)=" + obj);
            this.f.getCoreHandler().dispatchEvent(IMgr.MgrType.AppMgr, 21, obj);
            return;
        }
        if ("closewebapp".equals(str)) {
            Logger.e("IAN", "updateParam closewebapp");
            Activity activity = (Activity) obj;
            Bundle extras = activity.getIntent().getExtras();
            String string = (extras == null || !extras.containsKey("appid")) ? null : extras.getString("appid");
            if (TextUtils.isEmpty(string)) {
                string = BaseInfo.sDefaultBootApp;
            }
            if (activity instanceof IActivityHandler) {
                ((IActivityHandler) activity).closeAppStreamSplash(string);
            }
            this.f.getCoreHandler().dispatchEvent(null, 0, new Object[]{activity, activity.getIntent(), string});
            Logger.e("IAN", "updateParam closewebapp WEBAPP_QUIT");
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class i implements PrivacyManager.b {
        i() {
        }

        @Override // io.dcloud.common.ui.PrivacyManager.b
        public void a(String str) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
            a.this.i();
            AppRuntime.initUTS();
            CookieManager.initCookieConfig(a.this.getApplication());
            NativeCrashManager.initNativeCrash(a.this);
        }

        @Override // io.dcloud.common.ui.PrivacyManager.b
        public void b(AndroidPrivacyResponse androidPrivacyResponse) {
            new Handler().postDelayed(a.this.n, r1.j);
            if (androidPrivacyResponse.disagreeMode.loadNativePlugins) {
                AppRuntime.initUniappPlugin(a.this.getApplication());
            }
            CookieManager.initCookieConfig(a.this.getApplication());
        }

        @Override // io.dcloud.common.ui.PrivacyManager.b
        public void a() throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
            a.this.i();
            a.this.checkAndRequestStoragePermission();
            AppRuntime.initUniappPlugin(a.this.getApplication());
            AppRuntime.initUTS();
            CookieManager.initCookieConfig(a.this.getApplication());
            NativeCrashManager.initNativeCrash(a.this);
        }

        @Override // io.dcloud.common.ui.PrivacyManager.b
        public void a(AndroidPrivacyResponse androidPrivacyResponse) {
            if (!androidPrivacyResponse.disagreeMode.support) {
                a.this.finish();
                Process.killProcess(Process.myPid());
                return;
            }
            new Handler().postDelayed(a.this.n, r1.j);
            if (androidPrivacyResponse.disagreeMode.loadNativePlugins) {
                AppRuntime.initUniappPlugin(a.this.getApplication());
            }
            CookieManager.initCookieConfig(a.this.getApplication());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String[] strArr) {
        PermissionUtil.useSystemPermissions(this.that, strArr, new j());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, int i2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.that);
        if (i2 == 0) {
            i2 = PdrR.getInt(this, "string", IntentConst.WEBAPP_ACTIVITY_APPNAME);
        }
        AlertDialog alertDialogCreate = builder.setMessage(i2).setPositiveButton(android.R.string.ok, new l(str)).setNegativeButton(android.R.string.cancel, new k()).create();
        this.i = alertDialogCreate;
        alertDialogCreate.setCanceledOnTouchOutside(false);
        this.i.setCancelable(false);
        this.i.show();
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class g implements PrivacyManager.b {
        g() {
        }

        @Override // io.dcloud.common.ui.PrivacyManager.b
        public void a(String str) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
            a.this.i();
            if (str.equalsIgnoreCase("custom")) {
                new Handler().postDelayed(a.this.n, r0.j);
            } else {
                a.this.checkAndRequestStoragePermission();
            }
            CookieManager.initCookieConfig(a.this.getApplication());
            AppRuntime.initUTS();
            NativeCrashManager.initNativeCrash(a.this);
        }

        @Override // io.dcloud.common.ui.PrivacyManager.b
        public void b(AndroidPrivacyResponse androidPrivacyResponse) {
            new Handler().postDelayed(a.this.n, r1.j);
            if (androidPrivacyResponse.disagreeMode.loadNativePlugins) {
                AppRuntime.initUniappPlugin(a.this.getApplication());
            }
            CookieManager.initCookieConfig(a.this.getApplication());
        }

        @Override // io.dcloud.common.ui.PrivacyManager.b
        public void a() {
            a.this.onPrivacySureAction();
        }

        @Override // io.dcloud.common.ui.PrivacyManager.b
        public void a(AndroidPrivacyResponse androidPrivacyResponse) {
            if (TextUtils.isEmpty(androidPrivacyResponse.second.message)) {
                if (!androidPrivacyResponse.disagreeMode.support) {
                    a.this.finish();
                    Process.killProcess(Process.myPid());
                    return;
                }
                new Handler().postDelayed(a.this.n, r1.j);
                if (androidPrivacyResponse.disagreeMode.loadNativePlugins) {
                    AppRuntime.initUniappPlugin(a.this.getApplication());
                }
                CookieManager.initCookieConfig(a.this.getApplication());
                return;
            }
            a.this.setSecondPrivacyAlert();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras == null || !extras.containsKey("appid")) {
            return;
        }
        this.e = extras.getString("appid");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Runnable runnable) throws ClassNotFoundException {
        if (PdrUtil.isEquals(BaseInfo.renderer.toLowerCase(), "native")) {
            if (runnable != null) {
                runnable.run();
                return;
            }
            return;
        }
        m mVar = new m();
        c cVar = new c(mVar, new b(), runnable);
        d dVar = new d(cVar);
        try {
            Class.forName("io.dcloud.feature.x5.X5InitImpl");
            AppRuntime.preInitX5(getApplication());
            if (!WebViewFactory.isOtherInitialised() && !WebViewFactory.isIsLoadOtherTimeOut()) {
                int iIntValue = ((Integer) mVar.onCallBack(-1, null)).intValue();
                if (iIntValue == 1) {
                    boolean z = BaseInfo.showTipsWithoutWifi;
                    boolean z2 = BaseInfo.allowDownloadWithoutWiFi;
                    if (!WebViewFactory.isOther()) {
                        AppRuntime.initX5(getApplication(), z2, new e(dVar, z, cVar));
                        return;
                    } else {
                        dVar.onCallBack(z ? 1 : 0, null);
                        return;
                    }
                }
                if (iIntValue == 2) {
                    AppRuntime.initX5(getApplication(), BaseInfo.allowDownloadWithoutWiFi, null);
                    if (runnable != null) {
                        runnable.run();
                        return;
                    }
                    return;
                }
                return;
            }
            cVar.onCallBack(-1, null);
        } catch (ClassNotFoundException unused) {
            cVar.onCallBack(-1, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Dialog a(Context context, ICallBack iCallBack) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog_transparent);
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.dcloud_dialog_loading, (ViewGroup) null);
        viewGroup.findViewById(R.id.loading_background).setBackgroundColor(0);
        viewGroup.findViewById(R.id.bg).setLayoutParams(new LinearLayout.LayoutParams(DensityUtils.dp2px(context, 150.0f), -2));
        float fDp2px = DensityUtils.dp2px(context, 8.0f);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{fDp2px, fDp2px, fDp2px, fDp2px, fDp2px, fDp2px, fDp2px, fDp2px}, null, null));
        shapeDrawable.getPaint().setColor(-16777216);
        viewGroup.findViewById(R.id.bg).setBackground(shapeDrawable);
        iCallBack.onCallBack(0, (TextView) viewGroup.findViewById(R.id.title));
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.setCanceledOnTouchOutside(false);
        alertDialogCreate.setView(viewGroup, 0, 0, 0, 0);
        return alertDialogCreate;
    }
}
