package io.dcloud;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.webkit.internal.AssetHelper;
import com.alibaba.fastjson.asm.Opcodes;
import com.dcloud.android.widget.toast.ToastCompat;
import io.dcloud.common.DHInterface.ILoadCallBack;
import io.dcloud.common.adapter.ui.webview.WebViewFactory;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.adapter.util.DownloadUtil;
import io.dcloud.common.adapter.util.PlatformUtil;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.DialogUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.LoadAppUtils;
import io.dcloud.common.util.NotificationUtil;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.feature.ui.nativeui.a;
import io.dcloud.p.d1;
import io.src.dcloud.adapter.DCloudBaseActivity;
import java.io.File;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class WebviewActivity extends DCloudBaseActivity implements View.OnClickListener {
    public static final String isLocalHtmlParam = "isLocalHtml";
    public static final String noPermissionAllowParam = "isNoPermissionAllowParam";
    private TextView a;
    private TextView b;
    private TextView c;
    private TextView d;
    private TextView e;
    private e f;
    private FrameLayout g;
    private WebView h;
    private boolean i;
    private String j = null;
    public ArrayList<String> mAppStreamSchemeWhiteDefaultList = new ArrayList<>();
    private boolean k = false;
    private boolean l = false;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a extends WebChromeClient {
        a() {
        }

        @Override // android.webkit.WebChromeClient
        public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
            super.onGeolocationPermissionsShowPrompt(str, callback);
            if (WebviewActivity.this.l) {
                callback.invoke(str, false, false);
            } else {
                callback.invoke(str, true, false);
            }
        }

        @Override // android.webkit.WebChromeClient
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            if (WebviewActivity.this.k) {
                return;
            }
            if (WebviewActivity.this.f.getParent() == null) {
                WebviewActivity.this.g.addView(WebviewActivity.this.f);
                WebviewActivity.this.f.c();
            } else if (WebviewActivity.this.i) {
                WebviewActivity.this.i = false;
                WebviewActivity.this.f.c();
            }
            WebviewActivity.this.f.setVisibility(0);
            if (WebviewActivity.this.f.b() <= i) {
                WebviewActivity.this.f.a(i);
            }
        }

        @Override // android.webkit.WebChromeClient
        public void onReceivedTitle(WebView webView, String str) {
            super.onReceivedTitle(webView, str);
            if (WebviewActivity.this.c == null || TextUtils.isEmpty(str) || str.startsWith("http") || str.startsWith("https")) {
                return;
            }
            WebviewActivity.this.c.setText(str);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b extends WebViewClient {

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class a implements View.OnClickListener {
            final /* synthetic */ Intent a;

            a(Intent intent) {
                this.a = intent;
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                WebviewActivity.this.startActivity(this.a);
            }
        }

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        /* renamed from: io.dcloud.WebviewActivity$b$b, reason: collision with other inner class name */
        class DialogInterfaceOnClickListenerC0018b implements DialogInterface.OnClickListener {
            final /* synthetic */ AlertDialog a;
            final /* synthetic */ SslError b;
            final /* synthetic */ SslErrorHandler c;

            DialogInterfaceOnClickListenerC0018b(AlertDialog alertDialog, SslError sslError, SslErrorHandler sslErrorHandler) {
                this.a = alertDialog;
                this.b = sslError;
                this.c = sslErrorHandler;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == -2) {
                    this.a.cancel();
                    this.a.dismiss();
                } else if (i == -3) {
                    this.b.getCertificate().getIssuedBy();
                } else if (i == -1) {
                    WebViewFactory.setSslHandlerState(this.c, 1);
                    this.a.dismiss();
                }
            }
        }

        b() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            WebviewActivity.this.b.setVisibility(webView.canGoBack() ? 0 : 4);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            if (sslErrorHandler != null) {
                String str = BaseInfo.untrustedca;
                if (PdrUtil.isEquals(str, "refuse")) {
                    sslErrorHandler.cancel();
                    return;
                }
                if (!PdrUtil.isEquals(str, "warning")) {
                    WebViewFactory.setSslHandlerState(sslErrorHandler, 1);
                    return;
                }
                AlertDialog alertDialogCreate = new AlertDialog.Builder(webView.getContext()).create();
                alertDialogCreate.setIcon(R.drawable.ic_secure);
                alertDialogCreate.setTitle(WebviewActivity.this.getString(io.dcloud.base.R.string.dcloud_common_safety_warning));
                alertDialogCreate.setCanceledOnTouchOutside(false);
                String url = sslError.getUrl();
                String string = WebviewActivity.this.getString(io.dcloud.base.R.string.dcloud_common_certificate_continue);
                if (!TextUtils.isEmpty(url)) {
                    string = url + "\n" + string;
                }
                alertDialogCreate.setMessage(string);
                DialogInterfaceOnClickListenerC0018b dialogInterfaceOnClickListenerC0018b = new DialogInterfaceOnClickListenerC0018b(alertDialogCreate, sslError, sslErrorHandler);
                alertDialogCreate.setButton(-2, WebviewActivity.this.getString(R.string.cancel), dialogInterfaceOnClickListenerC0018b);
                alertDialogCreate.setButton(-1, WebviewActivity.this.getString(R.string.ok), dialogInterfaceOnClickListenerC0018b);
                alertDialogCreate.show();
            }
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) throws URISyntaxException {
            Intent intent;
            String lowerCase = !TextUtils.isEmpty(str) ? str.toLowerCase(Locale.ENGLISH) : "";
            if (TextUtils.isEmpty(lowerCase) || str.startsWith("streamapp://") || lowerCase.startsWith(DeviceInfo.HTTP_PROTOCOL) || lowerCase.startsWith(DeviceInfo.HTTPS_PROTOCOL) || lowerCase.contains("streamapp://")) {
                webView.loadUrl(str);
                WebviewActivity.this.i = true;
                return true;
            }
            if (!TextUtils.isEmpty(lowerCase)) {
                ArrayList<String> arrayList = WebviewActivity.this.mAppStreamSchemeWhiteDefaultList;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    String str2 = arrayList.get(i);
                    i++;
                    if (lowerCase.startsWith(str2 + ":")) {
                        try {
                            Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(str));
                            if (BaseInfo.isDefense) {
                                intent2.setSelector(null);
                                intent2.setComponent(null);
                                intent2.addCategory("android.intent.category.BROWSABLE");
                            }
                            WebviewActivity.this.startActivity(intent2);
                        } catch (Exception unused) {
                        }
                        return true;
                    }
                }
            }
            try {
                if (lowerCase.startsWith("intent://")) {
                    intent = Intent.parseUri(str, 1);
                    intent.addCategory("android.intent.category.BROWSABLE");
                    intent.setComponent(null);
                    intent.setSelector(null);
                } else {
                    intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
                }
                PackageManager packageManager = WebviewActivity.this.getPackageManager();
                List<ResolveInfo> listQueryIntentActivities = packageManager.queryIntentActivities(intent, 0);
                if (listQueryIntentActivities != null && listQueryIntentActivities.size() > 0) {
                    String str3 = WebviewActivity.this.getString(io.dcloud.base.R.string.dcloud_common_soon_open) + "\"Android system\"" + WebviewActivity.this.getString(io.dcloud.base.R.string.dcloud_common_app_open_now);
                    CharSequence charSequenceLoadLabel = 1 == listQueryIntentActivities.size() ? listQueryIntentActivities.get(0).loadLabel(packageManager) : "";
                    if (!TextUtils.isEmpty(charSequenceLoadLabel)) {
                        str3 = WebviewActivity.this.getString(io.dcloud.base.R.string.dcloud_common_soon_open) + JSUtil.QUOTE + ((Object) charSequenceLoadLabel) + JSUtil.QUOTE + WebviewActivity.this.getString(io.dcloud.base.R.string.dcloud_common_app_open_now);
                    }
                    WebviewActivity webviewActivity = WebviewActivity.this;
                    DialogUtil.showAlertDialog(webviewActivity.that, str3, webviewActivity.getString(io.dcloud.base.R.string.dcloud_common_open), WebviewActivity.this.getString(io.dcloud.base.R.string.dcloud_common_cancel), new a(intent), null, null, null, false, 1, 0, (int) (WebviewActivity.this.getResources().getDisplayMetrics().widthPixels * 0.9d));
                }
            } catch (Exception unused2) {
            }
            return true;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c implements DownloadListener {

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class a implements View.OnClickListener {
            final /* synthetic */ String a;
            final /* synthetic */ String b;
            final /* synthetic */ String c;
            final /* synthetic */ String d;

            /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
            /* renamed from: io.dcloud.WebviewActivity$c$a$a, reason: collision with other inner class name */
            class C0019a implements ILoadCallBack {
                C0019a() {
                }

                @Override // io.dcloud.common.DHInterface.ILoadCallBack
                public Object onCallBack(int i, Context context, Object obj) {
                    if (obj == null && i == -1 && context == null) {
                        Intent intent = new Intent();
                        a aVar = a.this;
                        WebviewActivity webviewActivity = WebviewActivity.this;
                        NotificationUtil.showNotification(webviewActivity.that, aVar.d, webviewActivity.getString(io.dcloud.base.R.string.dcloud_common_download_failed), intent, -1, -1, intent.hashCode(), true);
                        return null;
                    }
                    String strValueOf = String.valueOf(obj);
                    String mimeType = PdrUtil.getMimeType(strValueOf);
                    if (strValueOf.startsWith(DeviceInfo.FILE_PROTOCOL)) {
                        strValueOf = strValueOf.substring(7);
                    }
                    if (strValueOf.startsWith("content://")) {
                        strValueOf = PlatformUtil.getFilePathFromContentUri(Uri.parse(strValueOf), WebviewActivity.this.that.getContentResolver());
                        mimeType = PdrUtil.getMimeType(strValueOf);
                    }
                    Intent dataAndTypeIntent = LoadAppUtils.getDataAndTypeIntent(context, strValueOf, mimeType);
                    a aVar2 = a.this;
                    WebviewActivity webviewActivity2 = WebviewActivity.this;
                    NotificationUtil.showNotification(webviewActivity2.that, aVar2.d, webviewActivity2.getString(io.dcloud.base.R.string.dcloud_common_download_complete), dataAndTypeIntent, PdrR.DRAWABLE_DCLOUD_WEBVIEW_DOWNLOAD_PIN_AROUND, PdrR.DRAWABLE_DCLOUD_WEBVIEW_DOWNLOAD_PIN, 1, true);
                    return null;
                }
            }

            a(String str, String str2, String str3, String str4) {
                this.a = str;
                this.b = str2;
                this.c = str3;
                this.d = str4;
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DownloadUtil.getInstance(WebviewActivity.this.that).startRequest(WebviewActivity.this.that, this.a, this.b, this.c, this.d, new C0019a());
            }
        }

        c() {
        }

        @Override // android.webkit.DownloadListener
        public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
            try {
                try {
                    if (DeviceInfo.sDeviceSdkVer <= 8) {
                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.setData(Uri.parse(str));
                        WebviewActivity.this.startActivity(intent);
                        return;
                    }
                    String str5 = Environment.getExternalStorageDirectory().getPath() + File.separator + Environment.DIRECTORY_DOWNLOADS;
                    String downloadFilename = PdrUtil.getDownloadFilename(str3, str4, str);
                    String str6 = WebviewActivity.this.getString(io.dcloud.base.R.string.dcloud_common_download_do_file) + downloadFilename;
                    if (0 < j) {
                        str6 = str6 + "【" + new BigDecimal(j).divide(new BigDecimal(1048576L), 2, 4).floatValue() + "MB】";
                    }
                    WebviewActivity webviewActivity = WebviewActivity.this;
                    DialogUtil.showAlertDialog(webviewActivity.that, str6, webviewActivity.getString(io.dcloud.base.R.string.dcloud_common_download), WebviewActivity.this.getString(io.dcloud.base.R.string.dcloud_common_cancel), new a(str, str4, str5, downloadFilename), null, null, null, false, 0, 80, (int) (WebviewActivity.this.getResources().getDisplayMetrics().widthPixels * 0.9d));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception unused) {
                Intent intent2 = new Intent("android.intent.action.VIEW");
                intent2.setData(Uri.parse(str));
                WebviewActivity.this.startActivity(intent2);
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class d implements a.b {
        d() {
        }

        @Override // io.dcloud.feature.ui.nativeui.a.b
        public void initCancelText(TextView textView) {
        }

        @Override // io.dcloud.feature.ui.nativeui.a.b
        public void initTextItem(int i, TextView textView, String str) {
        }

        @Override // io.dcloud.feature.ui.nativeui.a.b
        public boolean onDismiss(int i) {
            return false;
        }

        @Override // io.dcloud.feature.ui.nativeui.a.b
        public void onItemClick(int i) {
            if (i == 1) {
                if (WebviewActivity.this.h != null) {
                    WebviewActivity.this.i = true;
                    WebviewActivity.this.h.reload();
                    return;
                }
                return;
            }
            if (i == 2) {
                WebviewActivity.this.h();
            } else if (i == 3) {
                WebviewActivity.this.j();
            } else {
                if (i != 4) {
                    return;
                }
                WebviewActivity.this.k();
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    static class e extends View {
        int a;
        float b;
        int c;
        Paint d;
        int e;
        int f;
        int g;
        int h;

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class a implements Runnable {
            a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                e eVar = e.this;
                int i = eVar.g - 5;
                eVar.g = i;
                if (i > 0) {
                    eVar.postDelayed(this, 5L);
                } else {
                    ViewGroup viewGroup = (ViewGroup) eVar.getParent();
                    if (viewGroup != null) {
                        viewGroup.removeView(e.this);
                    }
                }
                e.this.invalidate();
            }
        }

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class b implements Runnable {
            b() {
            }

            @Override // java.lang.Runnable
            public void run() {
                e eVar = e.this;
                int i = eVar.f;
                int i2 = eVar.e;
                int i3 = 10;
                int i4 = (i - i2) / 10;
                if (i4 <= 10) {
                    i3 = 1;
                    if (i4 >= 1) {
                        i3 = i4;
                    }
                }
                int i5 = i2 + i3;
                eVar.e = i5;
                if (i > i5) {
                    eVar.postDelayed(this, 5L);
                } else if (i >= eVar.a) {
                    eVar.a();
                }
                e.this.invalidate();
            }
        }

        e(Context context) {
            super(context);
            this.c = 0;
            this.d = new Paint();
            this.e = 0;
            this.f = 0;
            this.g = 255;
            this.a = context.getResources().getDisplayMetrics().widthPixels;
            this.b = PdrUtil.pxFromDp(2.0f, getResources().getDisplayMetrics());
        }

        void a() {
            postDelayed(new a(), 50L);
        }

        public int b() {
            return this.h;
        }

        public void c() {
            this.g = 255;
            this.e = 0;
            this.f = 0;
            this.h = 0;
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            this.d.setColor(Color.argb(this.g, 0, Opcodes.IFEQ, 68));
            float f = this.c;
            canvas.drawRect(0.0f, f, this.e, f + this.b, this.d);
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            setMeasuredDimension(this.a, this.c + ((int) this.b));
        }

        void a(int i) {
            this.h = i;
            int i2 = (this.a * i) / 100;
            if (this.e >= this.f) {
                postDelayed(new b(), 5L);
            }
            this.f = i2;
        }
    }

    private void l() {
        if (getIntent() != null) {
            if (getIntent().hasExtra("url")) {
                this.j = getIntent().getStringExtra("url");
            }
            if (getIntent().hasExtra(isLocalHtmlParam)) {
                this.k = getIntent().getBooleanExtra(isLocalHtmlParam, false);
            }
            if (getIntent().hasExtra(noPermissionAllowParam)) {
                this.l = getIntent().getBooleanExtra(noPermissionAllowParam, false);
            }
        }
        this.mAppStreamSchemeWhiteDefaultList.add("weixin");
        this.mAppStreamSchemeWhiteDefaultList.add("alipay");
        this.mAppStreamSchemeWhiteDefaultList.add("alipays");
        this.mAppStreamSchemeWhiteDefaultList.add("alipayqr");
    }

    private void m() {
        findViewById(io.dcloud.base.R.id.status_bar_view).setLayoutParams(new LinearLayout.LayoutParams(-1, DeviceInfo.getStatusHeight(this)));
        this.a = (TextView) findViewById(PdrR.WEBVIEW_ACTIVITY_LAYOUT_BACK);
        this.b = (TextView) findViewById(PdrR.WEBVIEW_ACTIVITY_LAYOUT_CLOSE);
        this.c = (TextView) findViewById(PdrR.WEBVIEW_ACTIVITY_LAYOUT_TITLE);
        this.e = (TextView) findViewById(PdrR.WEBVIEW_ACTIVITY_LAYOUT_REFRESH);
        this.d = (TextView) findViewById(PdrR.WEBVIEW_ACTIVITY_LAYOUT_MENU);
        this.g = (FrameLayout) findViewById(PdrR.WEBVIEW_ACTIVITY_LAYOUT_CONTENT);
        e eVar = new e(this.that);
        this.f = eVar;
        if (this.k) {
            eVar.setVisibility(8);
            this.d.setVisibility(4);
        }
        WebView webView = (WebView) findViewById(PdrR.WEBVIEW_ACTIVITY_LAYOUT_WEBVIEW);
        this.h = webView;
        a(webView);
        int iPxFromDp = PdrUtil.pxFromDp(23.0f, getResources().getDisplayMetrics());
        Typeface typefaceCreateFromAsset = Typeface.createFromAsset(getAssets(), "fonts/dcloud_iconfont.ttf");
        this.a.setText("\ue601");
        this.a.setTypeface(typefaceCreateFromAsset);
        float f = iPxFromDp;
        this.a.getPaint().setTextSize(f);
        this.b.setText("\ue650");
        this.b.setTypeface(typefaceCreateFromAsset);
        this.b.getPaint().setTextSize(f);
        this.b.setVisibility(4);
        this.e.setText("\ue606");
        this.e.setTypeface(typefaceCreateFromAsset);
        this.e.getPaint().setTextSize(f);
        this.d.setText("\ue606");
        this.d.setTypeface(typefaceCreateFromAsset);
        this.d.getPaint().setTextSize(f);
        this.a.setOnClickListener(this);
        this.b.setOnClickListener(this);
        this.d.setOnClickListener(this);
    }

    private void n() {
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
        Intent intent = getIntent();
        if (intent == null || !"POP".equals(intent.getStringExtra("ANIM"))) {
            overridePendingTransition(0, PdrR.ANIM_DCLOUD_SLIDE_OUT_TO_RIGHT);
        } else {
            overridePendingTransition(0, io.dcloud.base.R.anim.dcloud_pop_out);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) throws JSONException, NumberFormatException {
        if (view == this.a) {
            if (g()) {
                return;
            }
            finish();
        } else if (view == this.b) {
            finish();
        } else if (view == this.d) {
            i();
        }
    }

    @Override // io.src.dcloud.adapter.DCloudBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException {
        setImmersive(this);
        super.onCreate(bundle);
        int i = Build.VERSION.SDK_INT;
        if (i >= 29) {
            getWindow().setNavigationBarContrastEnforced(false);
        }
        setContentView(PdrR.WEBVIEW_ACTIVITY_LAYOUT);
        l();
        m();
        if (!TextUtils.isEmpty(this.j)) {
            this.h.loadUrl(this.j);
        }
        if (i < 36 || AndroidResources.sAppTargetSdkVersion < 36) {
            return;
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(io.dcloud.base.R.id.webview), new OnApplyWindowInsetsListener() { // from class: io.dcloud.WebviewActivity$$ExternalSyntheticLambda0
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return WebviewActivity.a(view, windowInsetsCompat);
            }
        });
    }

    @Override // io.src.dcloud.adapter.DCloudBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        try {
            WebView webView = this.h;
            if (webView != null) {
                if (webView.getParent() != null) {
                    ((ViewGroup) this.h.getParent()).removeView(this.h);
                }
                this.h.clearHistory();
                this.h.clearCache(true);
                this.h.destroy();
            }
            this.h = null;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        super.onDestroy();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (4 != i) {
            return super.onKeyDown(i, keyEvent);
        }
        boolean zG = g();
        return !zG ? super.onKeyDown(i, keyEvent) : zG;
    }

    public void setImmersive(Activity activity) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException {
        int i;
        if (activity != null) {
            int i2 = Build.VERSION.SDK_INT;
            Window window = activity.getWindow();
            int systemUiVisibility = window.getDecorView().getSystemUiVisibility() | 1280;
            window.setStatusBarColor(0);
            if (i2 >= 23) {
                try {
                    Class<?> cls = Class.forName("android.view.View");
                    i = cls.getField("SYSTEM_UI_FLAG_LIGHT_STATUS_BAR").getInt(cls);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    i = 8192;
                }
                systemUiVisibility |= i;
            }
            window.getDecorView().setSystemUiVisibility(systemUiVisibility);
        }
    }

    private boolean g() {
        WebView webView = this.h;
        if (webView == null) {
            return false;
        }
        if (webView.canGoBack()) {
            this.i = true;
            this.h.goBack();
            this.c.setText(this.h.getTitle());
            return true;
        }
        if (this.h.canGoBack()) {
            return false;
        }
        finish();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        ((ClipboardManager) getSystemService("clipboard")).setText(this.h.getUrl());
        ToastCompat.makeText((Context) this.that, (CharSequence) (getString(io.dcloud.base.R.string.dcloud_common_copy_clipboard) + this.h.getUrl()), 1).show();
    }

    private void i() throws JSONException, NumberFormatException {
        setTheme(PdrR.WEBVIEW_ACTIVITY_LAYOUT_ACTS_STYLE_ActionSheetStyleIOS7);
        io.dcloud.feature.ui.nativeui.a aVar = new io.dcloud.feature.ui.nativeui.a(this.that);
        JSONArray jSONArray = new JSONArray();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(AbsoluteConst.JSON_KEY_TITLE, getString(io.dcloud.base.R.string.dcloud_common_refresh));
            jSONArray.put(jSONObject);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(AbsoluteConst.JSON_KEY_TITLE, getString(io.dcloud.base.R.string.dcloud_common_copy_link));
            jSONArray.put(jSONObject2);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put(AbsoluteConst.JSON_KEY_TITLE, getString(io.dcloud.base.R.string.dcloud_common_open_browser));
            jSONArray.put(jSONObject3);
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put(AbsoluteConst.JSON_KEY_TITLE, getString(io.dcloud.base.R.string.dcloud_common_share_page));
            jSONArray.put(jSONObject4);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        aVar.b(getString(io.dcloud.base.R.string.dcloud_common_cancel));
        aVar.a(jSONArray);
        aVar.a(new d());
        aVar.a(true);
        aVar.j();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j() {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(this.h.getUrl()));
            startActivity(Intent.createChooser(intent, getString(io.dcloud.base.R.string.dcloud_common_open_web)));
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k() {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType(AssetHelper.DEFAULT_MIME_TYPE);
            intent.putExtra("android.intent.extra.SUBJECT", this.h.getTitle());
            intent.putExtra("android.intent.extra.TEXT", this.h.getUrl());
            intent.setFlags(268435456);
            startActivity(Intent.createChooser(intent, getString(io.dcloud.base.R.string.dcloud_common_share)));
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ WindowInsetsCompat a(View view, WindowInsetsCompat windowInsetsCompat) {
        if (windowInsetsCompat != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                Insets insets = windowInsetsCompat.getInsets(WindowInsets.Type.navigationBars());
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                marginLayoutParams.bottomMargin = insets.bottom;
                marginLayoutParams.topMargin = insets.top;
                marginLayoutParams.leftMargin = insets.left;
                marginLayoutParams.rightMargin = insets.right;
            }
        }
        return WindowInsetsCompat.CONSUMED;
    }

    private void a(WebView webView) {
        if (webView != null) {
            WebSettings settings = webView.getSettings();
            settings.setDomStorageEnabled(true);
            Class[] clsArr = {Boolean.TYPE};
            Object[] objArr = {Boolean.TRUE};
            settings.setAllowFileAccess(false);
            PlatformUtil.invokeMethod(settings, d1.a("f2l4TWBgY3tKZWBpTW9vaX9/KjZhM2Q4OGZhLTRiYTAtNDc5Zi05NDIyLWU1YWFiZTE1ODk3Yjc2", true, 12), clsArr, objArr);
            settings.setSavePassword(false);
            WebViewFactory.openJSEnabled(settings, null);
            webView.setFocusable(true);
            webView.removeJavascriptInterface("searchBoxJavaBridge_");
            webView.removeJavascriptInterface("accessibilityTraversal");
            webView.removeJavascriptInterface("accessibility");
            webView.setWebChromeClient(new a());
            webView.setWebViewClient(new b());
        }
        n();
        if (webView == null) {
            return;
        }
        webView.setDownloadListener(new c());
    }
}
