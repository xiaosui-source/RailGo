package io.dcloud.common.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Process;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.app.ActivityOptionsCompat;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.dcloud.PdrR;
import io.dcloud.WebviewActivity;
import io.dcloud.base.R;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.ui.Info.AndroidPrivacyResponse;
import io.dcloud.common.ui.PrivacyManager;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.PdrUtil;
import java.io.File;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class a {
    Context a;
    c b;
    private PrivacyManager.b d;
    private boolean e;
    private d h;
    private AndroidPrivacyResponse c = null;
    private int f = PdrR.UNI_CUSTOM_PRIVACY_DIALOG_LAYOUT;
    private boolean g = true;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    /* renamed from: io.dcloud.common.ui.a$a, reason: collision with other inner class name */
    class C0034a extends b {
        final /* synthetic */ URLSpan c;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C0034a(e eVar, URLSpan uRLSpan) {
            super(eVar);
            this.c = uRLSpan;
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(View view) {
            String string;
            try {
                if ("system".equalsIgnoreCase(a.this.c.hrefLoader)) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.setData(Uri.parse(this.c.getURL()));
                    a.this.a.startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent();
                intent2.setClass(a.this.a, WebviewActivity.class);
                String url = this.c.getURL();
                if (!TextUtils.isEmpty(url) && !url.startsWith(DeviceInfo.HTTP_PROTOCOL) && !url.startsWith(DeviceInfo.HTTPS_PROTOCOL) && !url.startsWith("HTTP://") && !url.startsWith("HTTPS://")) {
                    if (url.startsWith("./")) {
                        url = url.substring(2);
                    }
                    String str = BaseInfo.sDefaultBootApp + "/www/" + url;
                    if (PrivacyManager.getInstance().getIsAppAsset()) {
                        string = "file:///android_asset/apps/" + str;
                    } else {
                        File file = new File(BaseInfo.sCacheFsAppsPath + str);
                        StringBuilder sb = new StringBuilder(DeviceInfo.FILE_PROTOCOL);
                        sb.append(file.getPath());
                        string = sb.toString();
                    }
                    url = string;
                    intent2.putExtra(WebviewActivity.isLocalHtmlParam, true);
                    intent2.putExtra(WebviewActivity.noPermissionAllowParam, true);
                }
                intent2.putExtra("url", url);
                intent2.setData(Uri.parse(url));
                intent2.setAction("android.intent.action.VIEW");
                intent2.setFlags(268435456);
                intent2.putExtra("ANIM", "POP");
                a.this.a.startActivity(intent2, ActivityOptionsCompat.makeCustomAnimation(a.this.a, R.anim.dcloud_pop_in, R.anim.dcloud_pop_in_out).toBundle());
            } catch (Exception unused) {
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public abstract class b extends ClickableSpan {
        e a;

        b(e eVar) {
            this.a = eVar;
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            super.updateDrawState(textPaint);
            e eVar = this.a;
            if (eVar != null) {
                textPaint.setUnderlineText(eVar.b);
                textPaint.setColor(this.a.a);
            } else {
                textPaint.setUnderlineText(false);
                textPaint.setColor(-16776961);
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c extends Dialog {
        Context a;
        private TextView b;
        private TextView c;
        private Button d;
        private Button e;
        private Button f;
        private LinearLayout g;

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        /* renamed from: io.dcloud.common.ui.a$c$a, reason: collision with other inner class name */
        class ViewOnClickListenerC0035a implements View.OnClickListener {
            ViewOnClickListenerC0035a() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                c.this.dismiss();
                a.this.h.a(a.this.c.version);
            }
        }

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class b implements View.OnClickListener {
            b() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                c.this.dismiss();
                a.this.h.a();
            }
        }

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        /* renamed from: io.dcloud.common.ui.a$c$c, reason: collision with other inner class name */
        class ViewOnClickListenerC0036c implements View.OnClickListener {
            ViewOnClickListenerC0036c() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                c.this.dismiss();
                a.this.h.onCancel();
            }
        }

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class d implements View.OnClickListener {
            d() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                c.this.dismiss();
                a.this.h.onCancel();
            }
        }

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class e implements DialogInterface.OnKeyListener {
            e() {
            }

            @Override // android.content.DialogInterface.OnKeyListener
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() != 4) {
                    return false;
                }
                c.this.dismiss();
                Context context = c.this.a;
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
                Process.killProcess(Process.myPid());
                return true;
            }
        }

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class f implements View.OnClickListener {
            f() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                c.this.cancel();
            }
        }

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class g implements View.OnClickListener {
            g() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                c.this.cancel();
            }
        }

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        class h implements View.OnClickListener {
            h() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                c.this.cancel();
            }
        }

        public c(Context context) {
            super(context);
            requestWindowFeature(1);
            this.a = context;
            c();
            a();
            if (f.a(this.g.getTag() != null ? this.g.getTag().toString() : "").a) {
                getWindow().setLayout(-1, -1);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b() {
            int color;
            int color2;
            int color3;
            int color4;
            int color5;
            this.d.setOnClickListener(new ViewOnClickListenerC0035a());
            Button button = this.f;
            if (button != null) {
                button.setOnClickListener(new b());
            }
            if (a.this.g) {
                if (!TextUtils.isEmpty(a.this.c.second.title)) {
                    this.b.setText(a.this.c.second.title);
                }
                if (!TextUtils.isEmpty(a.this.c.second.message)) {
                    e eVarA = e.a(this.c.getTag() != null ? this.c.getTag().toString() : "");
                    this.c.setMovementMethod(LinkMovementMethod.getInstance());
                    this.c.setAutoLinkMask(1);
                    TextView textView = this.c;
                    a aVar = a.this;
                    textView.setText(aVar.a(aVar.c.second.message, eVarA));
                    this.c.setGravity(a.this.a("left"));
                }
                if (!TextUtils.isEmpty(a.this.c.second.buttonAccept)) {
                    this.d.setVisibility(0);
                    this.d.setText(a.this.c.second.buttonAccept);
                }
                if (TextUtils.isEmpty(a.this.c.second.buttonRefuse)) {
                    this.e.setVisibility(8);
                } else {
                    this.e.setText(a.this.c.second.buttonRefuse);
                    this.e.setVisibility(0);
                    this.e.setOnClickListener(new ViewOnClickListenerC0036c());
                }
            } else {
                if (!TextUtils.isEmpty(a.this.c.title)) {
                    this.b.setText(a.this.c.title);
                }
                if (!TextUtils.isEmpty(a.this.c.message)) {
                    e eVarA2 = e.a(this.c.getTag() != null ? this.c.getTag().toString() : "");
                    this.c.setMovementMethod(LinkMovementMethod.getInstance());
                    this.c.setAutoLinkMask(1);
                    TextView textView2 = this.c;
                    a aVar2 = a.this;
                    textView2.setText(aVar2.a(aVar2.c.message, eVarA2));
                    this.c.setGravity(a.this.a("left"));
                }
                if (!TextUtils.isEmpty(a.this.c.buttonAccept)) {
                    this.d.setVisibility(0);
                    this.d.setText(a.this.c.buttonAccept);
                }
                if (TextUtils.isEmpty(a.this.c.buttonRefuse)) {
                    this.e.setVisibility(8);
                } else {
                    this.e.setText(a.this.c.buttonRefuse);
                    this.e.setVisibility(0);
                    this.e.setOnClickListener(new d());
                }
            }
            if (a.this.c.styles != null) {
                if (!TextUtils.isEmpty(a.this.c.styles.backgroundColor)) {
                    String str = !TextUtils.isEmpty(a.this.c.styles.borderRadius) ? a.this.c.styles.borderRadius : "10px";
                    try {
                        color5 = Color.parseColor(a.this.c.styles.backgroundColor);
                    } catch (Exception unused) {
                        color5 = -1;
                    }
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(color5);
                    gradientDrawable.setCornerRadius(a.this.a(this.a, PdrUtil.parseInt(str, 1, 10)));
                    this.g.setBackground(gradientDrawable);
                }
                int color6 = -16777216;
                if (a.this.c.styles.title != null) {
                    try {
                        color = Color.parseColor(a.this.c.styles.title.color);
                    } catch (Exception unused2) {
                        color = -16777216;
                    }
                    this.b.setTextColor(color);
                }
                if (a.this.c.styles.content != null) {
                    try {
                        color2 = Color.parseColor(a.this.c.styles.content.color);
                    } catch (Exception unused3) {
                        color2 = -16777216;
                    }
                    this.c.setTextColor(color2);
                }
                if (a.this.c.styles.buttonAccept != null && !TextUtils.isEmpty(a.this.c.styles.buttonAccept.color)) {
                    try {
                        color4 = Color.parseColor(a.this.c.styles.buttonAccept.color);
                    } catch (Exception unused4) {
                        color4 = -16777216;
                    }
                    this.d.setTextColor(color4);
                }
                if (a.this.c.styles.buttonRefuse != null && !TextUtils.isEmpty(a.this.c.styles.buttonRefuse.color)) {
                    try {
                        color3 = Color.parseColor(a.this.c.styles.buttonRefuse.color);
                    } catch (Exception unused5) {
                        color3 = -16777216;
                    }
                    this.e.setTextColor(color3);
                }
                if (a.this.c.styles.buttonVisitor != null && !TextUtils.isEmpty(a.this.c.styles.buttonVisitor.color)) {
                    try {
                        color6 = Color.parseColor(a.this.c.styles.buttonVisitor.color);
                    } catch (Exception unused6) {
                    }
                    this.f.setTextColor(color6);
                }
            }
            if (a.this.c.backToExit) {
                setOnKeyListener(new e());
            }
        }

        private void c() {
            View viewInflate = LayoutInflater.from(this.a).inflate(a.this.f, (ViewGroup) null);
            setContentView(viewInflate);
            this.e = (Button) viewInflate.findViewById(R.id.btn_custom_privacy_cancel);
            this.d = (Button) viewInflate.findViewById(R.id.btn_custom_privacy_sure);
            this.f = (Button) viewInflate.findViewById(R.id.btn_custom_privacy_visitor);
            this.c = (TextView) viewInflate.findViewById(R.id.tv_privacy_content);
            this.b = (TextView) viewInflate.findViewById(R.id.tv_custom_privacy_title);
            this.g = (LinearLayout) viewInflate.findViewById(R.id.ll_content_layout);
            getWindow().setBackgroundDrawable(new ColorDrawable(0));
            d();
            if (this.f != null) {
                if (a.this.c.disagreeMode.visitorEntry) {
                    this.f.setVisibility(0);
                } else {
                    this.f.setVisibility(8);
                }
            }
        }

        public void d() {
            Display defaultDisplay = ((Activity) this.a).getWindowManager().getDefaultDisplay();
            if (this.a.getResources().getConfiguration().orientation == 1) {
                this.c.setMaxHeight((int) (defaultDisplay.getHeight() * 0.6d));
            } else {
                this.c.setMaxHeight((int) (defaultDisplay.getHeight() * 0.2d));
            }
        }

        private void a() {
            this.e.setOnClickListener(new f());
            this.d.setOnClickListener(new g());
            Button button = this.f;
            if (button != null) {
                button.setOnClickListener(new h());
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface d {
        void a();

        void a(String str);

        void onCancel();
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class e {
        public int a;
        public boolean b;

        public static e a(String str) {
            e eVar = new e();
            if (TextUtils.isEmpty(str)) {
                return eVar;
            }
            JSONObject object = JSON.parseObject(str);
            eVar.b = object.getBoolean("linkLine").booleanValue();
            eVar.a = Color.parseColor(object.getString("linkColor"));
            return eVar;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class f {
        public boolean a = false;

        public static f a(String str) {
            f fVar = new f();
            if (!TextUtils.isEmpty(str)) {
                JSONObject object = JSON.parseObject(str);
                if (object.containsKey(IApp.ConfigProperty.CONFIG_FULLSCREEN)) {
                    fVar.a = object.getBoolean(IApp.ConfigProperty.CONFIG_FULLSCREEN).booleanValue();
                }
            }
            return fVar;
        }
    }

    public a(Context context) {
        this.a = context;
    }

    public void e() {
        c cVar = new c(this.a);
        this.b = cVar;
        cVar.setCanceledOnTouchOutside(false);
        this.b.setCancelable(false);
        this.b.b();
        this.b.show();
    }

    public PrivacyManager.b b() {
        return this.d;
    }

    public boolean c() {
        return this.e;
    }

    public boolean d() {
        c cVar = this.b;
        return cVar != null && cVar.isShowing();
    }

    public void a(PrivacyManager.b bVar) {
        this.d = bVar;
    }

    public void a(boolean z) {
        this.e = z;
    }

    public void a(int i) {
        if (i != 0) {
            this.f = i;
        }
    }

    public void a(AndroidPrivacyResponse androidPrivacyResponse, boolean z, d dVar) {
        this.c = androidPrivacyResponse;
        this.g = z;
        this.h = dVar;
    }

    public CharSequence a(String str, e eVar) {
        Spanned spannedFromHtml = Html.fromHtml(str);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spannedFromHtml);
        for (URLSpan uRLSpan : (URLSpan[]) spannableStringBuilder.getSpans(0, spannedFromHtml.length(), URLSpan.class)) {
            a(spannableStringBuilder, uRLSpan, eVar);
        }
        return spannableStringBuilder;
    }

    private void a(SpannableStringBuilder spannableStringBuilder, URLSpan uRLSpan, e eVar) {
        spannableStringBuilder.setSpan(new C0034a(eVar, uRLSpan), spannableStringBuilder.getSpanStart(uRLSpan), spannableStringBuilder.getSpanEnd(uRLSpan), spannableStringBuilder.getSpanFlags(uRLSpan));
    }

    public void a() {
        c cVar = this.b;
        if (cVar != null) {
            cVar.dismiss();
            this.b = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(Context context, float f2) {
        return (int) ((f2 * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(String str) {
        if (TextUtils.isEmpty(str)) {
            return 17;
        }
        str.getClass();
        str.hashCode();
        switch (str) {
            case "bottom":
                return 80;
            case "left":
                return 3;
            case "right":
                return 5;
            default:
                return 17;
        }
    }
}
