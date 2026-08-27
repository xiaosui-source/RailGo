package io.dcloud.sdk.base.dcloud;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import io.dcloud.WebAppActivity;
import io.dcloud.base.R;
import io.dcloud.p.l;
import io.dcloud.p.n;
import io.dcloud.p.r1;
import io.dcloud.sdk.base.dcloud.ADHandler;
import io.dcloud.sdk.core.util.ReflectUtil;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class g {
    n a;
    ADHandler.e b;
    View c;
    View d;
    Context h;
    boolean e = false;
    boolean f = false;
    boolean g = false;
    BroadcastReceiver i = new b();
    private final Runnable j = new Runnable() { // from class: io.dcloud.sdk.base.dcloud.g$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.a();
        }
    };

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements r1 {
        final /* synthetic */ Context a;

        a(Context context) {
            this.a = context;
        }

        @Override // io.dcloud.p.r1
        public void onReceiver(JSONObject jSONObject) throws InterruptedException, IOException {
            ADHandler.a("adh", "listenADReceive----------------onReceiver-");
            g gVar = g.this;
            if (gVar.f) {
                return;
            }
            Context context = gVar.h;
            ADHandler.e eVar = gVar.b;
            ADHandler.a(context, eVar.h, eVar);
            if (g.this.b.a()) {
                g.this.c();
            } else {
                g.this.a(this.a);
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b extends BroadcastReceiver {
        b() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) throws InterruptedException, IOException {
            if (g.this.f) {
                return;
            }
            String stringExtra = intent.getStringExtra("src");
            if (TextUtils.isEmpty(stringExtra) || g.this.b.c() == null || !g.this.b.b().optString("src").equalsIgnoreCase(stringExtra)) {
                return;
            }
            if (!intent.getBooleanExtra("downloadImage", false)) {
                g.this.a();
                return;
            }
            ADHandler.e eVar = g.this.b;
            ADHandler.a(context, eVar.h, eVar);
            if (g.this.b.a()) {
                g.this.c();
            } else {
                g.this.a();
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class c implements View.OnClickListener {
        final /* synthetic */ Context a;

        c(Context context) {
            this.a = context;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) throws JSONException {
            try {
                g.this.b.c().put("down_x", Math.round(g.this.b.a.getX()));
                g.this.b.c().put("down_y", Math.round(g.this.b.a.getY()));
                g.this.b.c().put("up_x", Math.round(g.this.b.b.getX()));
                g.this.b.c().put("up_y", g.this.b.b.getY());
                g.this.b.c().put("relative_down_x", Math.round(g.this.b.a.getX() - view.getX()));
                g.this.b.c().put("relative_down_y", Math.round(g.this.b.a.getY() - view.getY()));
                g.this.b.c().put("relative_up_x", Math.round(g.this.b.b.getX() - view.getX()));
                g.this.b.c().put("relative_up_y", Math.round(g.this.b.b.getY() - view.getY()));
                g.this.b.c().put("dw", l.b(this.a));
                g.this.b.c().put("dh", l.a(this.a));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Context context = this.a;
            ADHandler.a(context, g.this.b, ADHandler.a(context, "adid"));
            view.setOnClickListener(null);
            g gVar = g.this;
            ADHandler.e eVar = gVar.b;
            eVar.a = null;
            eVar.b = null;
            gVar.a.onClicked();
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class d implements View.OnTouchListener {
        d() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0) {
                g.this.b.a = motionEvent;
                return false;
            }
            if (motionEvent.getAction() != 1) {
                return false;
            }
            g.this.b.b = motionEvent;
            return false;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class e implements View.OnClickListener {
        e() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            g gVar = g.this;
            gVar.d.removeCallbacks(gVar.j);
            g.this.a();
        }
    }

    public g(Context context, ADHandler.e eVar, ViewGroup viewGroup, n nVar) {
        this.a = null;
        this.c = null;
        this.d = null;
        this.a = nVar;
        this.b = eVar;
        this.h = context;
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.dcloud_ad_main_container, (ViewGroup) null);
        this.d = viewInflate;
        viewGroup.addView(viewInflate);
        this.c = (TextView) this.d.findViewById(R.id.ad_dcloud_main_skip);
        if (this.b.a()) {
            ADHandler.a("SplashADViewWrapper", "use cache AdData");
            a(context, this.d);
        } else {
            ADHandler.a("adh", "listenADReceive-----------------");
            this.b.a(context, new a(context));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        if (this.e) {
            this.d.removeCallbacks(this.j);
            this.d.postDelayed(this.j, 2000L);
        }
        a(this.h, this.d);
    }

    public void a(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("ad_img_downlaod_receive");
        LocalBroadcastManager.getInstance(context).registerReceiver(this.i, intentFilter);
        this.g = true;
    }

    public void b() {
        ADHandler.a("ADReceive", "onWillCloseSplash ");
        this.e = true;
        if (this.c != null && this.b.a()) {
            this.c.setVisibility(0);
        }
        this.d.postDelayed(this.j, WebAppActivity.SPLASH_SECOND);
    }

    private void a(Context context, View view) {
        ADHandler.a("ADReceive", "initAdMainView ");
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.ad_dcloud_main_img);
        frameLayout.setOnClickListener(new c(context));
        frameLayout.setOnTouchListener(new d());
        Object obj = this.b.e;
        if (obj instanceof Bitmap) {
            ADHandler.e eVar = this.b;
            frameLayout.addView(new f(context, (Bitmap) eVar.e, eVar), -1, -1);
        } else if (obj instanceof Drawable) {
            ImageView imageView = (ImageView) ReflectUtil.newInstance("pl.droidsonroids.gif.GifImageView", new Class[]{Context.class}, new Object[]{context});
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageDrawable((Drawable) this.b.e);
            frameLayout.addView(imageView, -1, -1);
        }
        if (this.e) {
            this.c.setVisibility(0);
        }
        this.c.setOnClickListener(new e());
        ADHandler.c(context, this.b, ADHandler.a(context, "adid"));
        this.a.onShow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        this.a.onFinishShow();
        View view = this.d;
        if (view != null && view.getParent() != null) {
            ((ViewGroup) this.d.getParent()).removeView(this.d);
        }
        this.f = true;
        Object obj = this.b.e;
        if (obj instanceof Bitmap) {
            Bitmap bitmap = (Bitmap) obj;
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        this.e = false;
        if (this.g) {
            LocalBroadcastManager.getInstance(this.h).unregisterReceiver(this.i);
        }
    }
}
