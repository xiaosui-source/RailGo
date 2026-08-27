package io.dcloud.common.core.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.dcloud.android.v4.view.ViewCompat;
import com.taobao.weex.common.Constants;
import io.dcloud.PdrR;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.adapter.ui.AdaFrameView;
import io.dcloud.common.adapter.ui.DHImageView;
import io.dcloud.common.adapter.ui.FrameSwitchView;
import io.dcloud.common.adapter.util.ViewOptions;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.core.ui.j;
import io.dcloud.common.util.BaseInfo;
import java.util.ArrayList;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public abstract class k extends FrameLayout {
    private int a;
    private float b;
    private boolean c;
    private View d;
    private j e;
    private float f;
    private Drawable g;
    private float h;
    private Rect i;
    DHImageView j;
    DHImageView k;
    io.dcloud.common.core.ui.a l;
    private io.dcloud.common.core.ui.b m;
    private io.dcloud.common.core.ui.b n;
    private final int o;
    private final int p;
    private int q;
    private int r;
    private boolean s;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface b {
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    private class c extends j.c {
        private boolean a;

        private c() {
        }

        @Override // io.dcloud.common.core.ui.j.c
        public int a(View view) {
            return 1;
        }

        @Override // io.dcloud.common.core.ui.j.c
        public int b(View view) {
            return 1;
        }

        @Override // io.dcloud.common.core.ui.j.c
        public void c(int i) {
            super.c(i);
            k.j(k.this);
            if (i == 0) {
                k kVar = k.this;
                kVar.a(kVar.m, "end", Boolean.valueOf(k.this.f >= 1.0f));
                k.this.requestLayout();
                k.this.b();
                if (k.this.f >= 1.0f) {
                    if (k.this.n != null) {
                        k.this.n.setSlipping(false);
                    }
                    if (k.this.m != null) {
                        k.this.m.setSlipping(false);
                    }
                    k.this.a();
                } else if (k.this.q == 1 && k.this.f == 0.0f && k.this.m != null && k.this.m.obtainWebView().canGoBack()) {
                    k.this.m.obtainWebView().goBackOrForward(-1);
                }
                k.this.m = null;
                k.this.n = null;
            }
        }

        @Override // io.dcloud.common.core.ui.j.c
        public void a(View view, int i, int i2, int i3, int i4) {
            super.a(view, i, i2, i3, i4);
            if (k.this.q == 1) {
                return;
            }
            if ((k.this.r & 1) != 0) {
                k.this.f = Math.abs(i / (r4.d.getWidth() + k.this.g.getIntrinsicWidth()));
            }
            k.this.invalidate();
            if (k.this.f < k.this.b && !this.a) {
                this.a = true;
            }
            k.j(k.this);
            k kVar = k.this;
            kVar.a(kVar.n);
        }

        @Override // io.dcloud.common.core.ui.j.c
        public boolean b(View view, int i) {
            k.this.d = view;
            if (k.this.e.d(k.this.a, i)) {
                if (k.this.e.d(1, i)) {
                    k.this.r = 1;
                }
                k.j(k.this);
                this.a = true;
            }
            return true;
        }

        @Override // io.dcloud.common.core.ui.j.c
        public void a(View view, float f, float f2) {
            if (k.this.q == 1) {
                return;
            }
            k.this.e.e(((k.this.r & 1) == 0 || (f <= 0.0f && (f != 0.0f || k.this.f <= k.this.b))) ? 0 : view.getWidth() + k.this.g.getIntrinsicWidth() + 10, 0);
            k.this.invalidate();
        }

        @Override // io.dcloud.common.core.ui.j.c
        public int a(View view, int i, int i2) {
            if (k.this.q == 1) {
                return 0;
            }
            return Math.min(view.getWidth(), Math.max(i, 0));
        }

        @Override // io.dcloud.common.core.ui.j.c
        public boolean a(io.dcloud.common.core.ui.b bVar) {
            int i;
            int i2;
            if (bVar == null || 2 == bVar.getFrameType() || BaseInfo.sDoingAnimation || !k.this.s) {
                return false;
            }
            Rect rect = new Rect();
            bVar.obtainWebView().obtainWindowView().getGlobalVisibleRect(rect);
            ViewOptions viewOptionsObtainFrameOptions = bVar.obtainFrameOptions();
            k.this.m = bVar;
            int height = k.this.getHeight();
            if (bVar.obtainApp() != null && bVar.obtainApp().obtainStatusBarMgr() != null && bVar.obtainApp().obtainStatusBarMgr().isHandledWhiteScreen) {
                height--;
            }
            if (rect.left != 0 || (((i = viewOptionsObtainFrameOptions.width) != -1 && i < k.this.getWidth()) || (((i2 = viewOptionsObtainFrameOptions.height) != -1 && i2 < height) || bVar.obtainFrameOptions().popGesture.equals("none")))) {
                return false;
            }
            if ((bVar.obtainFrameOptions().historyBack.equals("all") || bVar.obtainFrameOptions().historyBack.equals("popGesture")) && bVar.obtainWebView() != null && bVar.obtainWebView().canGoBack()) {
                k.this.q = 1;
                return true;
            }
            k.this.c();
            ArrayList arrayList = new ArrayList();
            k.this.l.a(bVar, arrayList);
            bVar.mWindowMgr.processEvent(IMgr.MgrType.WindowMgr, 28, arrayList);
            k.this.n = null;
            if (arrayList.size() == 1) {
                k.this.n = (io.dcloud.common.core.ui.b) arrayList.get(0);
            }
            if (arrayList.size() > 0) {
                if (k.this.n != null) {
                    k.this.n.setSlipping(true);
                    k.this.n.obtainMainView().setVisibility(0);
                    k.this.n.obtainMainView().bringToFront();
                }
                bVar.setSlipping(true);
                bVar.obtainMainView().setVisibility(0);
                bVar.obtainMainView().bringToFront();
                k.this.q = 2;
                FrameSwitchView frameSwitchView = FrameSwitchView.getInstance();
                if (frameSwitchView != null) {
                    frameSwitchView.endRefreshView();
                }
                k.this.a(bVar, "start", Constants.Name.UNDEFINED);
                return true;
            }
            return false;
        }
    }

    public k(Context context, io.dcloud.common.core.ui.a aVar) {
        super(context);
        this.b = 0.3f;
        this.c = true;
        this.i = new Rect();
        this.l = null;
        this.o = 1;
        this.p = 2;
        this.q = 1;
        this.s = false;
        float f = getResources().getDisplayMetrics().density * 400.0f;
        this.l = aVar;
        j jVar = new j(this, new c(), aVar);
        this.e = jVar;
        jVar.b(f);
        setEdgeTrackingEnabled(1);
        this.j = new DHImageView(context);
        this.k = new DHImageView(context);
        addView(this.j, new FrameLayout.LayoutParams(-1, -1));
        addView(this.k, new FrameLayout.LayoutParams(-1, -1));
        a(PdrR.DRAWEBL_SHADOW_LEFT, 1);
        this.k.setVisibility(8);
        this.j.setVisibility(8);
    }

    static /* synthetic */ b j(k kVar) {
        kVar.getClass();
        return null;
    }

    @Override // android.view.View
    public void computeScroll() {
        this.h = 1.0f - this.f;
        if (this.e.a(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        boolean z = view == this.d;
        boolean zDrawChild = super.drawChild(canvas, view, j);
        if (this.h > 0.0f && z && this.e.c() != 0) {
            a(canvas, view);
        }
        return zDrawChild;
    }

    public DHImageView getLeftImageView() {
        return this.j;
    }

    public DHImageView getRightImageView() {
        return this.k;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.c) {
            return false;
        }
        try {
            return this.e.c(motionEvent);
        } catch (Exception unused) {
            return false;
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.c) {
            return false;
        }
        io.dcloud.common.core.ui.b bVar = this.m;
        if (bVar == null || bVar.obtainMainView() == null) {
            return true;
        }
        try {
            this.e.a(motionEvent);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        j jVar = this.e;
        if (jVar == null) {
            super.requestLayout();
        } else if (jVar.c() == 0) {
            super.requestLayout();
        }
    }

    public void setEdgeSize(int i) {
        this.e.e(i);
    }

    public void setEdgeTrackingEnabled(int i) {
        this.a = i;
        this.e.f(i);
    }

    public void setEnableGesture(boolean z) {
        this.c = z;
    }

    public void setScrollThresHold(float f) {
        if (f >= 1.0f || f <= 0.0f) {
            throw new IllegalArgumentException("Threshold value should be between 0 and 1.0");
        }
        this.b = f;
    }

    public void setSwipeListener(b bVar) {
    }

    public void c() {
        DHImageView dHImageView = this.j;
        if (dHImageView != null && dHImageView.getParent() == null) {
            addView(this.j, new FrameLayout.LayoutParams(-1, -1));
        }
        DHImageView dHImageView2 = this.k;
        if (dHImageView2 == null || dHImageView2.getParent() != null) {
            return;
        }
        addView(this.k, new FrameLayout.LayoutParams(-1, -1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        io.dcloud.common.core.ui.b bVar = this.n;
        if (bVar != null) {
            bVar.obtainMainView().setTranslationX(0.0f);
        }
        io.dcloud.common.core.ui.b bVar2 = this.m;
        if (bVar2 != null) {
            bVar2.obtainMainView().setLeft(0);
            this.m.obtainMainView().setTranslationX(0.0f);
        }
    }

    public void a(Drawable drawable, int i) {
        if ((i & 1) != 0) {
            this.g = drawable;
        }
        invalidate();
    }

    public void a(int i, int i2) {
        a(getResources().getDrawable(i), i2);
    }

    private void a(Canvas canvas, View view) {
        Rect rect = this.i;
        view.getHitRect(rect);
        if ((this.a & 1) != 0) {
            this.g.setBounds((int) (rect.left - (r9.getIntrinsicWidth() * 0.6d)), rect.top, rect.left, rect.bottom);
            this.g.setAlpha((int) (this.h * 190.0f));
            this.g.draw(canvas);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(io.dcloud.common.core.ui.b bVar) {
        if (bVar != null) {
            View viewObtainMainView = bVar.obtainMainView();
            float width = (float) ((this.f - 0.95d) * 0.4210526315789474d * viewObtainMainView.getWidth());
            if (width > 0.0f) {
                width = 0.0f;
            }
            viewObtainMainView.setTranslationX(width);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        io.dcloud.common.core.ui.b bVar = this.m;
        if (bVar != null) {
            String str = bVar.obtainFrameOptions().popGesture;
            if (str.equals("hide")) {
                this.m.dispatchFrameViewEvents(AbsoluteConst.EVENTS_WEBAPP_SLIDE_HIDE, null);
                io.dcloud.common.core.ui.b bVar2 = this.m;
                bVar2.mWindowMgr.c(bVar2);
            } else if (str.equals(AbsoluteConst.EVENTS_CLOSE)) {
                this.m.dispatchFrameViewEvents(AbsoluteConst.EVENTS_WEBAPP_SLIDE_CLOSE, null);
                io.dcloud.common.core.ui.b bVar3 = this.m;
                bVar3.mWindowMgr.a(bVar3);
            }
        }
    }

    public void a(AdaFrameView adaFrameView, String str, Object obj) {
        adaFrameView.dispatchFrameViewEvents("popGesture", new Object[]{str, obj, this.m});
    }
}
