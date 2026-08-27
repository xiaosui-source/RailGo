package io.dcloud.feature.ui.nativeui;

import android.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import io.dcloud.PandoraEntryActivity;
import io.dcloud.PdrR;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.DHInterface.ISysEventListener;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.adapter.ui.AdaFrameItem;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.Deprecated_JSUtil;
import io.dcloud.common.util.EventDispatchManager;
import io.dcloud.common.util.JSONUtil;
import io.dcloud.common.util.JSUtil;
import io.dcloud.common.util.PdrUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
class b implements PopupWindow.OnDismissListener, ISysEventListener, EventDispatchManager.ActivityEventDispatchListener {
    private int A;
    private long C;
    private String D;
    private String F;
    private AnimationDrawable G;
    private Bitmap H;
    private int I;
    LinearLayout L;
    private Activity a;
    private NativeUIFeatureImpl b;
    private IWebview c;
    private IApp d;
    private String e;
    public String f;
    private PopupWindow h;
    private TextView i;
    private View j;
    private ProgressBar k;
    private ImageView l;
    private String m;
    private String n;
    private LinearLayout o;
    private String p;
    private String q;
    private String s;
    private String t;
    private int u;
    private int v;
    private View x;
    private int y;
    private int z;
    private String g = AbsoluteConst.EVENTS_CLOSE;
    private boolean r = true;
    private int w = -1308622848;
    private boolean B = false;
    private int E = -1;
    private int J = -2;
    private int K = -2;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements View.OnTouchListener {
        a() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return b.this.r;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    /* renamed from: io.dcloud.feature.ui.nativeui.b$b, reason: collision with other inner class name */
    class ViewOnKeyListenerC0045b implements View.OnKeyListener {
        ViewOnKeyListenerC0045b() {
        }

        @Override // android.view.View.OnKeyListener
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (keyEvent.getAction() == 0 && i == 4) {
                if (AbsoluteConst.EVENTS_CLOSE.equalsIgnoreCase(b.this.g)) {
                    b.this.a();
                    b.this.c();
                    return true;
                }
                if ("transmit".equalsIgnoreCase(b.this.g)) {
                    if (!(b.this.a instanceof PandoraEntryActivity)) {
                        b.this.a.onBackPressed();
                    } else if (b.this.c.canGoBack()) {
                        b.this.c.goBackOrForward(-1);
                    } else {
                        b.this.c.getActivity().onBackPressed();
                    }
                    return false;
                }
                if ("none".equalsIgnoreCase(b.this.g)) {
                    return true;
                }
            }
            return false;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c implements View.OnClickListener {
        c() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            b.this.a();
            b.this.c();
        }
    }

    b(NativeUIFeatureImpl nativeUIFeatureImpl, IWebview iWebview, String str, JSONObject jSONObject, String str2, Activity activity) {
        this.b = nativeUIFeatureImpl;
        this.c = iWebview;
        this.d = iWebview.obtainApp();
        this.e = str2;
        this.a = activity;
        if (activity instanceof PandoraEntryActivity) {
            this.x = ((AdaFrameItem) iWebview.obtainFrameView()).obtainMainView();
        } else {
            this.x = ((ViewGroup) activity.findViewById(R.id.content)).getChildAt(0);
        }
        this.y = this.d.getInt(0);
        this.z = this.d.getInt(1);
        a(iWebview, jSONObject);
        b();
        a(this.o);
        a(str);
        d();
    }

    private void e() {
        this.o.setFocusable(true);
        this.o.setFocusableInTouchMode(true);
        this.o.setOnKeyListener(new ViewOnKeyListenerC0045b());
    }

    private void f() {
        if (AbsoluteConst.JSON_VALUE_BLOCK.equalsIgnoreCase(this.q)) {
            this.o.setOrientation(1);
            return;
        }
        if (AbsoluteConst.JSON_VALUE_INLINE.equalsIgnoreCase(this.q)) {
            this.o.setOrientation(0);
        } else if ("none".equalsIgnoreCase(this.q)) {
            this.j.setVisibility(8);
            this.k.setVisibility(8);
        }
    }

    private void g() {
        if (TextUtils.isEmpty(this.F)) {
            return;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        byte[] bArrA = new byte[0];
        try {
            bArrA = a(this.d.obtainResInStream(this.c.obtainFullUrl(), this.F));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapFactory.decodeByteArray(bArrA, 0, bArrA.length, options);
        int i = options.outWidth;
        int i2 = options.outHeight;
        if (i == 0 || i2 == 0) {
            return;
        }
        options.inSampleSize = a(i2);
        options.inJustDecodeBounds = false;
        Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(bArrA, 0, bArrA.length, options);
        this.H = bitmapDecodeByteArray;
        if (i % i2 != 0) {
            if (bitmapDecodeByteArray != null) {
                this.k.setVisibility(8);
                this.l.setVisibility(0);
                this.l.setImageBitmap(this.H);
                return;
            }
            return;
        }
        if (bitmapDecodeByteArray != null) {
            int width = bitmapDecodeByteArray.getWidth();
            int height = this.H.getHeight();
            int i3 = width / height;
            if (this.C <= 0) {
                this.C = 100L;
            }
            AnimationDrawable animationDrawable = new AnimationDrawable();
            for (int i4 = 0; i4 < i3; i4++) {
                animationDrawable.addFrame(new BitmapDrawable(Bitmap.createBitmap(this.H, i4 * height, 0, height, height)), (int) this.C);
            }
            animationDrawable.setOneShot(false);
            ViewGroup.LayoutParams layoutParams = this.l.getLayoutParams();
            if (layoutParams != null) {
                int i5 = this.E;
                if (i5 > 0) {
                    height = i5;
                }
                layoutParams.width = height;
                layoutParams.height = height;
                this.l.setLayoutParams(layoutParams);
            }
            this.k.setVisibility(8);
            this.l.setVisibility(0);
            this.l.setBackground(animationDrawable);
            this.G = (AnimationDrawable) this.l.getBackground();
        }
    }

    private void h() {
        Drawable drawable = PdrUtil.isEquals(this.t, "snow") ? PdrUtil.isEquals(this.s, "black") ? this.a.getResources().getDrawable(PdrR.DRAWBLE_PROGRESSBAR_BLACK_SNOW) : this.a.getResources().getDrawable(PdrR.DRAWBLE_PROGRESSBAR_WHITE_SNOW) : PdrUtil.isEquals(this.s, "black") ? this.a.getResources().getDrawable(PdrR.DRAWBLE_PROGRESSBAR_BLACK_CIRCLE) : this.a.getResources().getDrawable(PdrR.DRAWBLE_PROGRESSBAR_WHITE_CIRCLE);
        if (this.E > 0) {
            ProgressBar progressBar = this.k;
            int i = this.E;
            progressBar.setLayoutParams(new LinearLayout.LayoutParams(i, i));
        } else {
            int intrinsicHeight = (int) (drawable.getIntrinsicHeight() * 0.3d);
            this.k.setLayoutParams(new LinearLayout.LayoutParams(intrinsicHeight, intrinsicHeight));
        }
        this.k.setIndeterminateDrawable(drawable);
    }

    private void i() {
        LinearLayout linearLayout = this.o;
        int i = this.u;
        int i2 = this.v;
        linearLayout.setPadding(i, i2, i, i2);
        GradientDrawable gradientDrawable = (GradientDrawable) this.o.getBackground();
        int i3 = this.A;
        if (i3 > 0) {
            gradientDrawable.setCornerRadius(i3);
        }
        gradientDrawable.setColor(this.w);
        if (this.B) {
            this.o.setOnClickListener(new c());
        }
    }

    private void j() {
        this.i.setTextColor(!PdrUtil.isEmpty(this.m) ? PdrUtil.stringToColor(this.m) : -1);
        if (PdrUtil.isEmpty(this.n)) {
            this.i.setGravity(17);
        } else if ("left".equals(this.n)) {
            this.i.setGravity(3);
        } else if ("right".equals(this.n)) {
            this.i.setGravity(5);
        } else {
            this.i.setGravity(17);
        }
        int i = this.I;
        if (i > 0) {
            this.i.setTextSize(0, i);
        }
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        Deprecated_JSUtil.execCallback(this.c, this.e, null, JSUtil.OK, false, false);
        this.h = null;
        if (!this.r || (!TextUtils.isEmpty(this.g) && !AbsoluteConst.EVENTS_CLOSE.equalsIgnoreCase(this.g))) {
            this.d.unregisterSysEventListener(this, ISysEventListener.SysEventType.onKeyUp);
            EventDispatchManager.getInstance().removeListener(this);
        }
        Bitmap bitmap = this.H;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        this.H.recycle();
        System.gc();
    }

    @Override // io.dcloud.common.DHInterface.ISysEventListener
    public boolean onExecute(ISysEventListener.SysEventType sysEventType, Object obj) {
        if (sysEventType != ISysEventListener.SysEventType.onKeyUp || ((Integer) ((Object[]) obj)[0]).intValue() != 4) {
            return false;
        }
        if ("none".equalsIgnoreCase(this.g)) {
            return true;
        }
        if ("transmit".equalsIgnoreCase(this.g)) {
            return false;
        }
        a();
        c();
        return true;
    }

    private void a(IWebview iWebview, JSONObject jSONObject) {
        if (!JSONUtil.isNull(jSONObject, "background")) {
            this.w = PdrUtil.stringToColor(JSONUtil.getString(jSONObject, "background"));
        }
        if (!PdrUtil.isEmpty(JSONUtil.getString(jSONObject, AbsoluteConst.JSON_KEY_MODAL))) {
            this.r = !PdrUtil.isEquals(AbsoluteConst.FALSE, r0);
        }
        float scale = iWebview.getScale();
        this.A = (int) (PdrUtil.parseInt(JSONUtil.getString(jSONObject, AbsoluteConst.JSON_KEY_ROUND), 10) * scale);
        String string = JSONUtil.getString(jSONObject, AbsoluteConst.JSON_KEY_PADLOCK);
        if (!PdrUtil.isEmpty(string)) {
            this.B = Boolean.valueOf(string).booleanValue() | this.B;
        }
        this.J = PdrUtil.convertToScreenInt(JSONUtil.getString(jSONObject, "width"), this.y, this.J, scale);
        this.K = PdrUtil.convertToScreenInt(JSONUtil.getString(jSONObject, "height"), this.z, this.K, scale);
        String string2 = JSONUtil.getString(jSONObject, "back");
        if (!TextUtils.isEmpty(string2)) {
            this.g = string2;
        }
        this.s = JSONUtil.getString(jSONObject, "style");
        if (!JSONUtil.isNull(jSONObject, "color")) {
            this.m = JSONUtil.getString(jSONObject, "color");
        }
        this.p = JSONUtil.getString(jSONObject, AbsoluteConst.JSON_KEY_PADDIN);
        if (JSONUtil.isNull(jSONObject, "padding")) {
            int i = PdrUtil.parseInt(this.p, this.y, PdrUtil.parseInt("3%", this.y, 0));
            this.v = i;
            this.u = i;
        } else {
            String string3 = JSONUtil.getString(jSONObject, "padding");
            if (string3.indexOf(37) >= 0) {
                this.u = PdrUtil.convertToScreenInt(string3, this.y, this.u, scale);
                this.v = PdrUtil.convertToScreenInt(string3, this.z, this.v, scale);
            } else {
                int iConvertToScreenInt = PdrUtil.convertToScreenInt(string3, this.y, this.v, scale);
                this.v = iConvertToScreenInt;
                this.u = iConvertToScreenInt;
            }
        }
        if (!JSONUtil.isNull(jSONObject, AbsoluteConst.JSON_KEY_TEXTALIGN)) {
            this.n = JSONUtil.getString(jSONObject, AbsoluteConst.JSON_KEY_TEXTALIGN);
        }
        JSONObject jSONObject2 = JSONUtil.getJSONObject(jSONObject, "loading");
        if (jSONObject2 != null) {
            this.q = JSONUtil.getString(jSONObject2, "display");
            this.C = JSONUtil.getLong(jSONObject2, "interval");
            this.D = JSONUtil.getString(jSONObject2, AbsoluteConst.JSON_KEY_ICON);
            this.E = PdrUtil.convertToScreenInt(JSONUtil.getString(jSONObject2, "height"), this.y, -1, scale);
            this.t = JSONUtil.getString(jSONObject2, "type");
        }
        if (!TextUtils.isEmpty(this.D)) {
            this.F = this.D;
        }
        this.I = PdrUtil.convertToScreenInt(JSONUtil.getString(jSONObject, AbsoluteConst.JSON_KEY_SIZE), this.y, 0, scale);
    }

    private void b() {
        LinearLayout linearLayout = (LinearLayout) ((LayoutInflater) this.a.getSystemService("layout_inflater")).inflate(NativeUIR.LAYOUT_DIALOG_LAYOUT_LOADING_DCLOUD, (ViewGroup) null, false);
        this.L = linearLayout;
        LinearLayout linearLayout2 = (LinearLayout) linearLayout.findViewById(NativeUIR.DCLOUD_LOADING_LAYOUT_ROOT);
        this.o = linearLayout2;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
        layoutParams.width = this.J;
        layoutParams.height = this.K;
        this.o.setLayoutParams(layoutParams);
    }

    private void d() {
        int i;
        int i2;
        if (Build.VERSION.SDK_INT < 23 || !this.r) {
            i = -2;
            i2 = -2;
        } else {
            this.r = false;
            i = -1;
            i2 = -1;
        }
        if (!this.r || (!TextUtils.isEmpty(this.g) && !AbsoluteConst.EVENTS_CLOSE.equalsIgnoreCase(this.g))) {
            if (this.a instanceof PandoraEntryActivity) {
                this.d.registerSysEventListener(this, ISysEventListener.SysEventType.onKeyUp);
            } else {
                EventDispatchManager.getInstance().addListener(this);
            }
        }
        PopupWindow popupWindow = new PopupWindow(this.L, i, i2, this.r);
        this.h = popupWindow;
        popupWindow.showAtLocation(this.x, 17, 0, 0);
        this.h.setOnDismissListener(this);
        this.h.setBackgroundDrawable(new BitmapDrawable());
        this.h.setOutsideTouchable(true);
        this.h.setTouchInterceptor(new a());
        AnimationDrawable animationDrawable = this.G;
        if (animationDrawable == null || animationDrawable.isRunning()) {
            return;
        }
        this.G.start();
    }

    public void c() {
        this.b.a(this.f);
        this.d.unregisterSysEventListener(this, ISysEventListener.SysEventType.onKeyUp);
        EventDispatchManager.getInstance().removeListener(this);
    }

    void b(String str) {
        String strTrim = this.i.getText().toString().trim();
        if (!TextUtils.isEmpty(strTrim)) {
            if (strTrim.length() == str.length()) {
                if (this.i.getLayoutParams() != null) {
                    this.i.setLayoutParams(new LinearLayout.LayoutParams(this.i.getWidth(), this.i.getHeight()));
                }
            } else {
                this.i.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
            }
        }
        if (TextUtils.isEmpty(str)) {
            this.j.setVisibility(8);
            this.i.setVisibility(8);
        } else {
            this.i.setText(str);
        }
    }

    private void a(View view) {
        this.i = (TextView) view.findViewById(NativeUIR.ID_TEXT_LOADING_DCLOUD);
        this.k = (ProgressBar) view.findViewById(NativeUIR.ID_PROGRESSBAR_LOADING_DCLOUD);
        this.l = (ImageView) view.findViewById(NativeUIR.ID_IMAGE_LOADING_DCLOUD);
        this.j = view.findViewById(NativeUIR.ID_WAITING_SEPARATOR_DCLOUD);
    }

    private void a(String str) {
        f();
        j();
        h();
        g();
        b(str);
        e();
        i();
    }

    private int a(int i) {
        int iMin = Math.min(this.y, this.z);
        int i2 = this.u;
        int i3 = (iMin - i2) - i2;
        if (i3 <= 0 || i <= i3) {
            return 1;
        }
        return i / i3;
    }

    private byte[] a(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int i = inputStream.read(bArr);
            if (i != -1) {
                byteArrayOutputStream.write(bArr, 0, i);
            } else {
                inputStream.close();
                byteArrayOutputStream.close();
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    void a() {
        PopupWindow popupWindow = this.h;
        if (popupWindow == null || !popupWindow.isShowing()) {
            return;
        }
        try {
            this.h.dismiss();
        } catch (Exception unused) {
            onDismiss();
        }
    }
}
