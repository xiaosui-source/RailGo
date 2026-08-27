package io.dcloud.sdk.activity;

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
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.webkit.internal.AssetHelper;
import com.alibaba.fastjson.asm.Opcodes;
import io.dcloud.WebviewActivity;
import io.dcloud.base.R;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.p.g;
import io.dcloud.p.r4;
import io.dcloud.sdk.core.util.ReflectUtil;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class WebViewActivity extends Activity implements View.OnClickListener {
    public static final /* synthetic */ int o = 0;
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
    public ArrayList k = new ArrayList();
    private boolean l = false;
    private boolean m = false;
    public Map n = new a();

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a extends HashMap {
        a() {
            put("X-Requested-With", "");
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b extends WebChromeClient {
        b() {
        }

        @Override // android.webkit.WebChromeClient
        public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
            super.onGeolocationPermissionsShowPrompt(str, callback);
            if (WebViewActivity.this.m) {
                callback.invoke(str, false, false);
            } else {
                callback.invoke(str, true, false);
            }
        }

        @Override // android.webkit.WebChromeClient
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            if (WebViewActivity.this.l) {
                return;
            }
            if (WebViewActivity.this.f.getParent() == null) {
                WebViewActivity.this.g.addView(WebViewActivity.this.f);
                WebViewActivity.this.f.c();
            } else if (WebViewActivity.this.i) {
                WebViewActivity.this.i = false;
                WebViewActivity.this.f.c();
            }
            WebViewActivity.this.f.setVisibility(0);
            if (WebViewActivity.this.f.b() <= i) {
                WebViewActivity.this.f.a(i);
            }
        }

        @Override // android.webkit.WebChromeClient
        public void onReceivedTitle(WebView webView, String str) {
            super.onReceivedTitle(webView, str);
            if (WebViewActivity.this.c == null || TextUtils.isEmpty(str) || str.startsWith("http") || str.startsWith("https")) {
                return;
            }
            WebViewActivity.this.c.setText(str);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c extends WebViewClient {

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class a implements DialogInterface.OnClickListener {
            final /* synthetic */ Intent a;

            a(Intent intent) {
                this.a = intent;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                WebViewActivity.this.startActivity(this.a);
            }
        }

        c() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            WebViewActivity.this.b.setVisibility(webView.canGoBack() ? 0 : 4);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            if (sslErrorHandler != null) {
                WebViewActivity.this.a(sslErrorHandler, "proceed", new Class[0], new Object[0]);
            }
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) throws URISyntaxException {
            Intent intent;
            String lowerCase = !TextUtils.isEmpty(str) ? str.toLowerCase(Locale.ENGLISH) : "";
            if (TextUtils.isEmpty(lowerCase) || str.startsWith("streamapp://") || lowerCase.startsWith(DeviceInfo.HTTP_PROTOCOL) || lowerCase.startsWith(DeviceInfo.HTTPS_PROTOCOL) || lowerCase.contains("streamapp://")) {
                webView.loadUrl(str, WebViewActivity.this.n);
                WebViewActivity.this.i = true;
                return true;
            }
            if (!TextUtils.isEmpty(lowerCase)) {
                ArrayList arrayList = WebViewActivity.this.k;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    if (lowerCase.startsWith(((String) obj) + ":")) {
                        try {
                            Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(str));
                            intent2.setSelector(null);
                            intent2.setComponent(null);
                            intent2.addCategory("android.intent.category.BROWSABLE");
                            WebViewActivity.this.startActivity(intent2);
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
                PackageManager packageManager = WebViewActivity.this.getPackageManager();
                List<ResolveInfo> listQueryIntentActivities = packageManager.queryIntentActivities(intent, 0);
                if (listQueryIntentActivities != null && listQueryIntentActivities.size() > 0) {
                    String str2 = "即将打开\"Android system\"应用, \n立即打开?";
                    CharSequence charSequenceLoadLabel = 1 == listQueryIntentActivities.size() ? listQueryIntentActivities.get(0).loadLabel(packageManager) : "";
                    if (!TextUtils.isEmpty(charSequenceLoadLabel)) {
                        str2 = "即将打开\"" + ((Object) charSequenceLoadLabel) + "\"应用, \n立即打开?";
                    }
                    new AlertDialog.Builder(WebViewActivity.this).setMessage(str2).setPositiveButton("打开", new a(intent)).setNegativeButton("取消", (DialogInterface.OnClickListener) null).create().show();
                }
            } catch (Exception unused2) {
            }
            return true;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class d implements g.b {
        d() {
        }

        @Override // io.dcloud.p.g.b
        public void initCancelText(TextView textView) {
        }

        @Override // io.dcloud.p.g.b
        public void initTextItem(int i, TextView textView, String str) {
        }

        @Override // io.dcloud.p.g.b
        public boolean onDismiss(int i) {
            return false;
        }

        @Override // io.dcloud.p.g.b
        public void onItemClick(int i) {
            if (i == 1) {
                if (WebViewActivity.this.h != null) {
                    WebViewActivity.this.i = true;
                    WebViewActivity.this.h.reload();
                    return;
                }
                return;
            }
            if (i == 2) {
                WebViewActivity.this.b();
            } else if (i == 3) {
                WebViewActivity.this.d();
            } else {
                if (i != 4) {
                    return;
                }
                WebViewActivity.this.e();
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
            this.b = WebViewActivity.pxFromDp(2.0f, getResources().getDisplayMetrics());
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

    private void h() {
    }

    public static int pxFromDp(float f, DisplayMetrics displayMetrics) {
        return Math.round(TypedValue.applyDimension(1, f, displayMetrics));
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
        getIntent();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) throws JSONException {
        if (view == this.a) {
            if (a()) {
                return;
            }
            finish();
        } else if (view == this.b) {
            finish();
        } else if (view == this.d) {
            c();
        }
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        setContentView(R.layout.dcloud_ad_activity_webview);
        f();
        g();
        if (TextUtils.isEmpty(this.j)) {
            return;
        }
        this.h.loadUrl(this.j, this.n);
    }

    @Override // android.app.Activity
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

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (4 != i) {
            return super.onKeyDown(i, keyEvent);
        }
        boolean zA = a();
        return !zA ? super.onKeyDown(i, keyEvent) : zA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        ((ClipboardManager) getSystemService("clipboard")).setText(this.h.getUrl());
        Toast.makeText(this, "拷贝到剪切板" + this.h.getUrl(), 1).show();
    }

    private void c() throws JSONException {
        g gVar = new g(this);
        JSONArray jSONArray = new JSONArray();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(AbsoluteConst.JSON_KEY_TITLE, "刷新");
            jSONArray.put(jSONObject);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(AbsoluteConst.JSON_KEY_TITLE, "复制链接");
            jSONArray.put(jSONObject2);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put(AbsoluteConst.JSON_KEY_TITLE, "使用浏览器打开");
            jSONArray.put(jSONObject3);
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put(AbsoluteConst.JSON_KEY_TITLE, "分享页面");
            jSONArray.put(jSONObject4);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        gVar.a("取消");
        gVar.a(jSONArray);
        gVar.a(new d());
        gVar.a(true);
        gVar.i();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(this.h.getUrl()));
            startActivity(Intent.createChooser(intent, "打开网页"));
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType(AssetHelper.DEFAULT_MIME_TYPE);
            intent.putExtra("android.intent.extra.SUBJECT", this.h.getTitle());
            intent.putExtra("android.intent.extra.TEXT", this.h.getUrl());
            intent.setFlags(268435456);
            startActivity(Intent.createChooser(intent, "分享"));
        } catch (Exception unused) {
        }
    }

    private void f() {
        if (getIntent() != null) {
            if (getIntent().hasExtra("url")) {
                this.j = getIntent().getStringExtra("url");
            }
            if (getIntent().hasExtra(WebviewActivity.isLocalHtmlParam)) {
                this.l = getIntent().getBooleanExtra(WebviewActivity.isLocalHtmlParam, false);
            }
            if (getIntent().hasExtra(WebviewActivity.noPermissionAllowParam)) {
                this.m = getIntent().getBooleanExtra(WebviewActivity.noPermissionAllowParam, false);
            }
        }
        this.k.add("weixin");
        this.k.add("alipay");
        this.k.add("alipays");
        this.k.add("alipayqr");
    }

    private void g() {
        Typeface typefaceCreateFromAsset;
        this.a = (TextView) findViewById(R.id.back);
        this.b = (TextView) findViewById(R.id.close);
        this.c = (TextView) findViewById(R.id.title);
        this.e = (TextView) findViewById(R.id.refresh);
        this.d = (TextView) findViewById(R.id.menu);
        this.g = (FrameLayout) findViewById(R.id.content);
        e eVar = new e(this);
        this.f = eVar;
        if (this.l) {
            eVar.setVisibility(8);
            this.d.setVisibility(4);
        }
        WebView webView = (WebView) findViewById(R.id.webview);
        this.h = webView;
        a(webView);
        int iPxFromDp = pxFromDp(23.0f, getResources().getDisplayMetrics());
        try {
            typefaceCreateFromAsset = Typeface.createFromAsset(getAssets(), "dcloud_uni_ad/dcloud_iconfont.ttf");
        } catch (Exception unused) {
            typefaceCreateFromAsset = Typeface.createFromAsset(getAssets(), "fonts/dcloud_iconfont.ttf");
        }
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

    private void a(WebView webView) {
        if (webView != null) {
            WebSettings settings = webView.getSettings();
            settings.setDomStorageEnabled(true);
            Class[] clsArr = {Boolean.TYPE};
            Object[] objArr = {Boolean.TRUE};
            settings.setAllowFileAccess(false);
            ReflectUtil.invokeMethod(settings, r4.c("e218SWRkZ39OYWRtSWtrbXt7"), clsArr, objArr);
            settings.setSavePassword(false);
            settings.setJavaScriptEnabled(true);
            webView.setFocusable(true);
            webView.removeJavascriptInterface("searchBoxJavaBridge_");
            webView.removeJavascriptInterface("accessibilityTraversal");
            webView.removeJavascriptInterface("accessibility");
            webView.setWebChromeClient(new b());
            webView.setWebViewClient(new c());
        }
        h();
    }

    private boolean a() {
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

    public Object a(Object obj, String str, Class[] clsArr, Object... objArr) {
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
        } catch (Throwable unused) {
            return null;
        }
    }
}
