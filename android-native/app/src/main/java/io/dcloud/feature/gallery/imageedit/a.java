package io.dcloud.feature.gallery.imageedit;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.ViewSwitcher;
import io.dcloud.base.R;
import io.dcloud.feature.gallery.imageedit.b;
import io.dcloud.feature.gallery.imageedit.view.IMGColorGroup;
import io.dcloud.feature.gallery.imageedit.view.IMGView;
import io.dcloud.p.i2;
import io.dcloud.p.j2;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
abstract class a extends Activity implements View.OnClickListener, b.a, RadioGroup.OnCheckedChangeListener, DialogInterface.OnShowListener, DialogInterface.OnDismissListener, i2.b {
    protected IMGView a;
    private RadioGroup b;
    private IMGColorGroup c;
    private b d;
    private View e;
    private View f;
    private View g;
    private ViewSwitcher h;
    private ViewSwitcher i;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    /* renamed from: io.dcloud.feature.gallery.imageedit.a$a, reason: collision with other inner class name */
    static /* synthetic */ class C0038a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[j2.values().length];
            a = iArr;
            try {
                iArr[j2.DOODLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[j2.MOSAIC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[j2.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    a() {
    }

    private void d() {
        this.a = (IMGView) findViewById(R.id.image_canvas);
        this.b = (RadioGroup) findViewById(R.id.rg_modes);
        this.a.setDoodleTouchListener(this);
        this.h = (ViewSwitcher) findViewById(R.id.vs_op);
        this.i = (ViewSwitcher) findViewById(R.id.vs_op_sub);
        IMGColorGroup iMGColorGroup = (IMGColorGroup) findViewById(R.id.cg_colors);
        this.c = iMGColorGroup;
        iMGColorGroup.setOnCheckedChangeListener(this);
        this.e = findViewById(R.id.layout_op_sub);
        this.f = findViewById(R.id.dcloud_image_edit_head);
        this.g = findViewById(R.id.dcloud_image_edit_foot);
    }

    public void a() {
        this.f.setVisibility(0);
        this.g.setVisibility(0);
    }

    public abstract void a(int i);

    public abstract void a(j2 j2Var);

    public void b(int i) {
        if (i >= 0) {
            this.h.setDisplayedChild(i);
        }
    }

    public abstract Bitmap c();

    public void c(int i) {
        if (i < 0) {
            this.e.setVisibility(8);
        } else {
            this.i.setDisplayedChild(i);
            this.e.setVisibility(0);
        }
    }

    public abstract void e();

    public abstract void f();

    public abstract void g();

    public abstract void h();

    public abstract void i();

    public abstract void j();

    public abstract void k();

    public void l() {
        if (this.d == null) {
            b bVar = new b(this, this);
            this.d = bVar;
            bVar.setOnShowListener(this);
            this.d.setOnDismissListener(this);
        }
        this.d.show();
    }

    public abstract void m();

    public void n() {
        int i = C0038a.a[this.a.getMode().ordinal()];
        if (i == 1) {
            this.b.check(R.id.rb_doodle);
            c(0);
        } else if (i == 2) {
            this.b.check(R.id.rb_mosaic);
            c(1);
        } else {
            if (i != 3) {
                return;
            }
            this.b.clearCheck();
            c(-1);
        }
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (this.a.getMode() == j2.CLIP) {
            f();
        } else {
            super.onBackPressed();
        }
    }

    @Override // android.widget.RadioGroup.OnCheckedChangeListener
    public final void onCheckedChanged(RadioGroup radioGroup, int i) {
        a(this.c.getCheckColor());
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.rb_doodle) {
            a(j2.DOODLE);
            return;
        }
        if (id == R.id.btn_text) {
            l();
            return;
        }
        if (id == R.id.rb_mosaic) {
            a(j2.MOSAIC);
            return;
        }
        if (id == R.id.btn_clip) {
            a(j2.CLIP);
            return;
        }
        if (id == R.id.btn_undo) {
            m();
            return;
        }
        if (id == R.id.tv_done) {
            h();
            return;
        }
        if (id == R.id.tv_cancel) {
            e();
            return;
        }
        if (id == R.id.ib_clip_cancel) {
            f();
            return;
        }
        if (id == R.id.ib_clip_done) {
            i();
        } else if (id == R.id.tv_clip_reset) {
            j();
        } else if (id == R.id.ib_clip_rotate) {
            k();
        }
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bitmap bitmapC = c();
        if (bitmapC != null) {
            setContentView(R.layout.image_edit_activity);
            d();
            this.a.setImageBitmap(bitmapC);
            g();
        } else {
            finish();
        }
        int i = Build.VERSION.SDK_INT;
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(512);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(0);
        window.setNavigationBarColor(0);
        if (i >= 28) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.layoutInDisplayCutoutMode = 1;
            getWindow().setAttributes(attributes);
        }
    }

    public void onDismiss(DialogInterface dialogInterface) {
        this.h.setVisibility(0);
    }

    public void onShow(DialogInterface dialogInterface) {
        this.h.setVisibility(8);
    }

    public void b() {
        this.f.setVisibility(4);
        this.g.setVisibility(4);
    }
}
