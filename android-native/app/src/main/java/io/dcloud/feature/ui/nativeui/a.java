package io.dcloud.feature.ui.nativeui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.dmcbig.mediapicker.PickerConfig;
import io.dcloud.base.R;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.AppRuntime;
import io.dcloud.common.util.PdrUtil;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class a extends AlertDialog implements View.OnClickListener {
    private Context a;
    public b b;
    private View c;
    private ViewGroup d;
    private View e;
    private List f;
    private String g;
    private String h;
    private int i;
    private boolean j;
    private boolean k;
    private boolean l;
    private float m;
    private int n;
    private boolean o;
    private boolean p;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    /* renamed from: io.dcloud.feature.ui.nativeui.a$a, reason: collision with other inner class name */
    class DialogInterfaceOnCancelListenerC0044a implements DialogInterface.OnCancelListener {
        DialogInterfaceOnCancelListenerC0044a() {
        }

        @Override // android.content.DialogInterface.OnCancelListener
        public void onCancel(DialogInterface dialogInterface) {
            a.this.a(-1);
            b bVar = a.this.b;
            if (bVar != null) {
                bVar.onItemClick(-1);
            }
            a.this.l = false;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface b {
        void initCancelText(TextView textView);

        void initTextItem(int i, TextView textView, String str);

        boolean onDismiss(int i);

        void onItemClick(int i);
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public class c {
        public String a;
        public String b;
        public String c;

        public c(String str, String str2, String str3) {
            if (str2 == null) {
                this.b = "normal";
            } else {
                this.b = str2;
            }
            this.a = str;
            this.c = str3;
        }
    }

    public a(Context context) {
        super(context);
        this.g = "";
        this.h = "";
        this.i = -16777216;
        this.k = true;
        this.l = true;
        this.m = 16.0f;
        this.n = 0;
        this.o = true;
        this.p = false;
        a(context);
    }

    private Animation b() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(300L);
        alphaAnimation.setFillAfter(true);
        return alphaAnimation;
    }

    private void d() throws NumberFormatException {
        int intrinsicHeight = h().getIntrinsicHeight();
        boolean zIsEmpty = TextUtils.isEmpty(this.g);
        boolean z = !zIsEmpty;
        boolean z2 = !TextUtils.isEmpty(this.h);
        String str = this.h;
        if (str != null && TextUtils.isEmpty(str)) {
            this.h = getContext().getResources().getString(R.string.dcloud_common_cancel);
            z2 = true;
        }
        if (z2) {
            TextView textView = new TextView(this.a);
            textView.setGravity(17);
            textView.getPaint().setFakeBoldText(true);
            textView.setTextSize(2, this.m);
            textView.setId(100);
            Drawable drawable = this.o ? this.a.getResources().getDrawable(NativeUIR.SLT_AS_IOS7_CANCEL_BT) : this.a.getResources().getDrawable(NativeUIR.SLT_AS_IOS7_OTHER_BT_MIDDLE);
            if (AppRuntime.getAppDarkMode(this.a)) {
                drawable.setColorFilter(this.a.getResources().getColor(R.color.nightBG), PorterDuff.Mode.SRC_IN);
            }
            textView.setBackgroundDrawable(drawable);
            textView.setText(this.h);
            if (AppRuntime.getAppDarkMode(this.a)) {
                textView.setTextColor(this.a.getResources().getColor(R.color.nightBlueBtnTitle));
            } else {
                textView.setTextColor(this.i);
            }
            textView.setOnClickListener(this);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams.addRule(12);
            layoutParams.topMargin = b(this.n);
            this.d.addView(textView, layoutParams);
            b bVar = this.b;
            if (bVar != null) {
                bVar.initCancelText(textView);
            }
        }
        LinearLayout linearLayout = new LinearLayout(this.a);
        ScrollView scrollView = new ScrollView(this.a);
        linearLayout.setOrientation(1);
        scrollView.setId(300);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -2);
        if (zIsEmpty) {
            intrinsicHeight = 0;
        }
        linearLayout.setPadding(0, intrinsicHeight, 0, 0);
        scrollView.addView(linearLayout, layoutParams2);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-1, -2);
        if (z2) {
            layoutParams3.addRule(2, 100);
        } else {
            layoutParams3.addRule(12);
        }
        this.d.addView(scrollView, layoutParams3);
        if (!zIsEmpty) {
            TextView textView2 = new TextView(this.a);
            textView2.setGravity(17);
            textView2.setId(200);
            textView2.setOnClickListener(this);
            textView2.setBackgroundDrawable(h());
            textView2.setText(this.g);
            if (AppRuntime.getAppDarkMode(this.a)) {
                textView2.setTextColor(this.a.getResources().getColor(R.color.nightLightBtnTitle));
            } else {
                textView2.setTextColor(Color.parseColor("#8C8C8C"));
            }
            textView2.setTextSize(2, this.m);
            RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams4.topMargin = 0;
            layoutParams4.addRule(6, 300);
            this.d.addView(textView2, layoutParams4);
        }
        List list = this.f;
        if (list != null && list.size() > 0) {
            int i = 0;
            while (i < this.f.size()) {
                TextView textView3 = new TextView(this.a);
                textView3.setGravity(17);
                textView3.setId(i + PickerConfig.PICKER_IMAGE_VIDEO);
                textView3.setOnClickListener(this);
                textView3.setBackgroundDrawable(a(this.f.size(), i, z));
                textView3.setText(((c) this.f.get(i)).a);
                if (((c) this.f.get(i)).b.equals("destructive")) {
                    textView3.setTextColor(Color.parseColor("#E8484A"));
                } else {
                    int iStringToColor = PdrUtil.stringToColor(((c) this.f.get(i)).c);
                    if (-1 != iStringToColor) {
                        textView3.setTextColor(iStringToColor);
                    } else if (AppRuntime.getAppDarkMode(this.a)) {
                        textView3.setTextColor(this.a.getResources().getColor(R.color.nightText));
                    } else {
                        textView3.setTextColor(Color.parseColor("#000000"));
                    }
                }
                textView3.setTextSize(2, this.m);
                FrameLayout frameLayout = new FrameLayout(this.a);
                frameLayout.addView(textView3, c());
                boolean z3 = (zIsEmpty && i == 0) ? false : true;
                if (AppRuntime.getAppDarkMode(this.a) && z3) {
                    View view = new View(this.a);
                    view.setBackgroundColor(this.a.getResources().getColor(R.color.nightLine));
                    frameLayout.addView(view, new FrameLayout.LayoutParams(-1, b(1)));
                }
                if (i > 0) {
                    LinearLayout.LayoutParams layoutParamsC = c();
                    layoutParamsC.topMargin = 0;
                    linearLayout.addView(frameLayout, layoutParamsC);
                } else {
                    linearLayout.addView(frameLayout);
                }
                b bVar2 = this.b;
                if (bVar2 != null) {
                    bVar2.initTextItem(i, textView3, ((c) this.f.get(i)).a);
                }
                i++;
            }
        }
        int iB = b(this.n);
        this.d.setBackgroundDrawable(new ColorDrawable(0));
        if (this.p) {
            this.d.setPadding(iB, iB, iB, iB);
        } else {
            this.d.setPadding(0, iB, 0, iB);
        }
    }

    private Animation e() {
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 1.0f, 1, 0.0f);
        translateAnimation.setDuration(300L);
        return translateAnimation;
    }

    private Animation f() {
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 1.0f);
        translateAnimation.setDuration(300L);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }

    private View g() {
        FrameLayout frameLayout = new FrameLayout(this.a);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        layoutParams.gravity = 80;
        frameLayout.setLayoutParams(layoutParams);
        View view = new View(this.a);
        this.e = view;
        view.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        if (this.p) {
            this.e.setBackgroundColor(Color.argb(136, 0, 0, 0));
        }
        this.e.setId(10);
        this.e.setOnClickListener(this);
        this.d = new RelativeLayout(this.a);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -2);
        layoutParams2.gravity = 80;
        this.d.setLayoutParams(layoutParams2);
        frameLayout.addView(this.e);
        frameLayout.addView(this.d);
        return frameLayout;
    }

    private Drawable h() throws Resources.NotFoundException {
        int i = NativeUIR.SLT_AS_IOS7_TITLE;
        if (!this.o) {
            i = NativeUIR.SLT_AS_IOS7_OTHER_BT_MIDDLE;
        }
        Drawable drawable = this.a.getResources().getDrawable(i);
        if (AppRuntime.getAppDarkMode(this.a)) {
            drawable.setColorFilter(this.a.getResources().getColor(R.color.nightBG), PorterDuff.Mode.SRC_IN);
        }
        return drawable;
    }

    public LinearLayout.LayoutParams c() {
        return new LinearLayout.LayoutParams(-1, -2);
    }

    public void i() {
        View currentFocus;
        InputMethodManager inputMethodManager = (InputMethodManager) this.a.getSystemService("input_method");
        if (inputMethodManager.isActive() && (currentFocus = ((Activity) this.a).getCurrentFocus()) != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
        this.c = g();
        this.e.startAnimation(a());
        this.d.startAnimation(e());
    }

    public void j() throws NumberFormatException {
        if (this.k) {
            d();
            show();
            getWindow().setContentView(this.c);
            this.k = false;
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if ((view.getId() != 10 || this.j) && view.getId() != 200) {
            if (view.getId() == 10) {
                a(-1);
                b bVar = this.b;
                if (bVar != null) {
                    bVar.onItemClick(-1);
                }
                this.l = false;
                return;
            }
            int id = view.getId() - 100;
            a(id);
            b bVar2 = this.b;
            if (bVar2 != null) {
                bVar2.onItemClick(id);
            }
            this.l = false;
        }
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = 80;
        attributes.width = -1;
        attributes.height = -2;
        View decorView = getWindow().getDecorView();
        int i = this.n;
        decorView.setPadding(i, 0, i, 0);
        getWindow().setAttributes(attributes);
    }

    private boolean c(int i) {
        b bVar = this.b;
        if (bVar == null) {
            return false;
        }
        boolean zOnDismiss = bVar.onDismiss(i);
        if (!zOnDismiss) {
            this.d.startAnimation(f());
            this.e.startAnimation(b());
        }
        return zOnDismiss;
    }

    public void a(Context context) {
        this.a = context;
        this.n = 10;
        i();
        getWindow().setGravity(80);
        ColorDrawable colorDrawable = new ColorDrawable(0);
        colorDrawable.setAlpha(0);
        getWindow().setBackgroundDrawable(colorDrawable);
        setOnCancelListener(new DialogInterfaceOnCancelListenerC0044a());
    }

    public a e(int i) {
        this.n = b(i);
        return this;
    }

    private int b(int i) {
        return (int) TypedValue.applyDimension(1, i, this.a.getResources().getDisplayMetrics());
    }

    public a b(String str) {
        this.h = str;
        return this;
    }

    public a b(boolean z) {
        this.o = z;
        return this;
    }

    private Animation a() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(300L);
        return alphaAnimation;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00a1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private android.graphics.drawable.Drawable a(int r3, int r4, boolean r5) throws android.content.res.Resources.NotFoundException {
        /*
            r2 = this;
            boolean r0 = r2.o
            if (r0 != 0) goto L12
            android.content.Context r3 = r2.a
            android.content.res.Resources r3 = r3.getResources()
            int r4 = io.dcloud.feature.ui.nativeui.NativeUIR.SLT_AS_IOS7_OTHER_BT_MIDDLE
            android.graphics.drawable.Drawable r3 = r3.getDrawable(r4)
            goto La2
        L12:
            r0 = 1
            if (r3 != r0) goto L33
            if (r5 == 0) goto L25
            android.content.Context r3 = r2.a
            android.content.res.Resources r3 = r3.getResources()
            int r4 = io.dcloud.feature.ui.nativeui.NativeUIR.SLT_AS_IOS7_OTHER_BT_BOTTOM
            android.graphics.drawable.Drawable r3 = r3.getDrawable(r4)
            goto La2
        L25:
            android.content.Context r3 = r2.a
            android.content.res.Resources r3 = r3.getResources()
            int r4 = io.dcloud.feature.ui.nativeui.NativeUIR.SLT_AS_IOS7_OTHER_BT_SINGLE
            android.graphics.drawable.Drawable r3 = r3.getDrawable(r4)
            goto La2
        L33:
            r1 = 2
            if (r3 != r1) goto L64
            if (r4 == 0) goto L48
            if (r4 == r0) goto L3b
            goto La1
        L3b:
            android.content.Context r3 = r2.a
            android.content.res.Resources r3 = r3.getResources()
            int r4 = io.dcloud.feature.ui.nativeui.NativeUIR.SLT_AS_IOS7_OTHER_BT_BOTTOM
            android.graphics.drawable.Drawable r3 = r3.getDrawable(r4)
            goto La2
        L48:
            if (r5 == 0) goto L57
            android.content.Context r3 = r2.a
            android.content.res.Resources r3 = r3.getResources()
            int r4 = io.dcloud.feature.ui.nativeui.NativeUIR.SLT_AS_IOS7_OTHER_BT_MIDDLE
            android.graphics.drawable.Drawable r3 = r3.getDrawable(r4)
            goto La2
        L57:
            android.content.Context r3 = r2.a
            android.content.res.Resources r3 = r3.getResources()
            int r4 = io.dcloud.feature.ui.nativeui.NativeUIR.SLT_AS_IOS7_OTHER_BT_TOP
            android.graphics.drawable.Drawable r3 = r3.getDrawable(r4)
            goto La2
        L64:
            if (r3 <= r1) goto La1
            if (r4 != 0) goto L84
            if (r5 == 0) goto L77
            android.content.Context r3 = r2.a
            android.content.res.Resources r3 = r3.getResources()
            int r4 = io.dcloud.feature.ui.nativeui.NativeUIR.SLT_AS_IOS7_OTHER_BT_MIDDLE
            android.graphics.drawable.Drawable r3 = r3.getDrawable(r4)
            goto La2
        L77:
            android.content.Context r3 = r2.a
            android.content.res.Resources r3 = r3.getResources()
            int r4 = io.dcloud.feature.ui.nativeui.NativeUIR.SLT_AS_IOS7_OTHER_BT_TOP
            android.graphics.drawable.Drawable r3 = r3.getDrawable(r4)
            goto La2
        L84:
            int r3 = r3 - r0
            if (r4 != r3) goto L94
            android.content.Context r3 = r2.a
            android.content.res.Resources r3 = r3.getResources()
            int r4 = io.dcloud.feature.ui.nativeui.NativeUIR.SLT_AS_IOS7_OTHER_BT_BOTTOM
            android.graphics.drawable.Drawable r3 = r3.getDrawable(r4)
            goto La2
        L94:
            android.content.Context r3 = r2.a
            android.content.res.Resources r3 = r3.getResources()
            int r4 = io.dcloud.feature.ui.nativeui.NativeUIR.SLT_AS_IOS7_OTHER_BT_MIDDLE
            android.graphics.drawable.Drawable r3 = r3.getDrawable(r4)
            goto La2
        La1:
            r3 = 0
        La2:
            android.content.Context r4 = r2.a
            boolean r4 = io.dcloud.common.util.AppRuntime.getAppDarkMode(r4)
            if (r4 == 0) goto Lbd
            if (r3 == 0) goto Lbd
            android.content.Context r4 = r2.a
            android.content.res.Resources r4 = r4.getResources()
            int r5 = io.dcloud.base.R.color.nightBG
            int r4 = r4.getColor(r5)
            android.graphics.PorterDuff$Mode r5 = android.graphics.PorterDuff.Mode.SRC_IN
            r3.setColorFilter(r4, r5)
        Lbd:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.ui.nativeui.a.a(int, int, boolean):android.graphics.drawable.Drawable");
    }

    public a(Context context, int i) {
        super(context, i);
        this.g = "";
        this.h = "";
        this.i = -16777216;
        this.k = true;
        this.l = true;
        this.m = 16.0f;
        this.n = 0;
        this.o = true;
        this.p = true;
        a(context);
    }

    public void a(int i) {
        if (this.k || c(i)) {
            return;
        }
        dismiss();
        this.k = true;
        List list = this.f;
        if (list != null) {
            list.clear();
        }
    }

    public a a(float f) {
        this.m = f;
        return this;
    }

    public a a(String str) {
        this.g = str;
        return this;
    }

    public a a(boolean z) {
        this.j = z;
        return this;
    }

    public a a(JSONArray jSONArray) throws JSONException {
        this.f = new ArrayList();
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                if (jSONObject != null) {
                    this.f.add(new c(jSONObject.optString(AbsoluteConst.JSON_KEY_TITLE), jSONObject.optString("style"), jSONObject.optString("color")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public a a(b bVar) {
        this.b = bVar;
        return this;
    }

    public a d(int i) {
        this.i = i;
        return this;
    }
}
