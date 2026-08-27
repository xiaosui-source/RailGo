package io.dcloud.feature.gallery.imageedit.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import io.dcloud.p.f2;
import io.dcloud.p.g2;
import io.dcloud.p.i2;
import io.dcloud.p.j2;
import io.dcloud.p.k2;
import io.dcloud.p.l2;
import io.dcloud.p.o4;
import io.dcloud.p.p2;
import io.dcloud.p.q2;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class IMGView extends FrameLayout implements Runnable, ScaleGestureDetector.OnScaleGestureListener, ValueAnimator.AnimatorUpdateListener, p2.a, Animator.AnimatorListener {
    private j2 a;
    private i2 b;
    private GestureDetector c;
    private ScaleGestureDetector d;
    private g2 e;
    private c f;
    private int g;
    private Paint h;
    private Paint i;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements i2.c {
        a() {
        }

        @Override // io.dcloud.p.i2.c
        public void a() {
            IMGView.this.e();
            IMGView.this.b.b(this);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    private class b extends GestureDetector.SimpleOnGestureListener {
        private b() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return super.onFling(motionEvent, motionEvent2, f, f2);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return IMGView.this.a(f, f2);
        }

        /* synthetic */ b(IMGView iMGView, a aVar) {
            this();
        }
    }

    public IMGView(Context context) {
        this(context, null, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        invalidate();
        j();
        a(this.b.c(getScrollX(), getScrollY()), this.b.b(getScrollX(), getScrollY()));
    }

    private boolean f(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            return b(motionEvent);
        }
        if (actionMasked != 1) {
            if (actionMasked == 2) {
                return c(motionEvent);
            }
            if (actionMasked != 3) {
                return false;
            }
        }
        return this.f.b(motionEvent.getPointerId(0)) && f();
    }

    private void j() {
        g2 g2Var = this.e;
        if (g2Var != null) {
            g2Var.cancel();
        }
    }

    public void c() {
        if (d()) {
            return;
        }
        this.b.a(-90);
        e();
    }

    boolean d() {
        g2 g2Var = this.e;
        return g2Var != null && g2Var.isRunning();
    }

    boolean g() {
        Log.d("IMGView", "onSteady: isHoming=" + d());
        if (d()) {
            return false;
        }
        this.b.e(getScrollX(), getScrollY());
        e();
        return true;
    }

    public j2 getMode() {
        return this.b.c();
    }

    public void h() {
        this.b.s();
        e();
    }

    public Bitmap i() {
        this.b.t();
        float fE = 1.0f / this.b.e();
        RectF rectF = new RectF(this.b.b());
        Matrix matrix = new Matrix();
        matrix.setRotate(this.b.d(), rectF.centerX(), rectF.centerY());
        matrix.mapRect(rectF);
        matrix.setScale(fE, fE, rectF.left, rectF.top);
        matrix.mapRect(rectF);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(Math.round(rectF.width()), Math.round(rectF.height()), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.translate(-rectF.left, -rectF.top);
        canvas.scale(fE, fE, rectF.left, rectF.top);
        a(canvas);
        return bitmapCreateBitmap;
    }

    public void k() {
        this.b.w();
        invalidate();
    }

    public void l() {
        this.b.x();
        invalidate();
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        Log.d("IMGView", "onAnimationCancel");
        this.b.a(this.e.a());
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        Log.d("IMGView", "onAnimationEnd");
        if (this.b.a(getScrollX(), getScrollY(), this.e.a())) {
            a(this.b.a(getScrollX(), getScrollY()));
        }
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(Animator animator) {
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        Log.d("IMGView", "onAnimationStart");
        this.b.b(this.e.a());
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.b.a(valueAnimator.getAnimatedFraction());
        a((f2) valueAnimator.getAnimatedValue());
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this);
        this.b.r();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        a(canvas);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return motionEvent.getActionMasked() == 0 ? a(motionEvent) || super.onInterceptTouchEvent(motionEvent) : super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            this.b.h(i3 - i, i4 - i2);
        }
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        if (this.g <= 1) {
            return false;
        }
        this.b.a(scaleGestureDetector.getScaleFactor(), getScrollX() + scaleGestureDetector.getFocusX(), getScrollY() + scaleGestureDetector.getFocusY());
        invalidate();
        return true;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        if (this.g <= 1) {
            return false;
        }
        this.b.p();
        return true;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        this.b.q();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            removeCallbacks(this);
        } else if (actionMasked == 1 || actionMasked == 3) {
            postDelayed(this, 1200L);
        }
        return d(motionEvent);
    }

    @Override // java.lang.Runnable
    public void run() {
        if (g()) {
            return;
        }
        postDelayed(this, 500L);
    }

    public void setDoodleTouchListener(i2.b bVar) {
        this.b.a(bVar);
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.b.a(bitmap);
        invalidate();
    }

    public void setMode(j2 j2Var) {
        this.a = this.b.c();
        this.b.a(j2Var);
        this.f.a(j2Var);
        if (this.b.j()) {
            e();
        } else {
            this.b.a(new a());
        }
    }

    public void setPenColor(int i) {
        this.f.a(i);
    }

    public IMGView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void b() {
        this.b.a(getScrollX(), getScrollY());
        setMode(this.a);
        e();
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    private static class c extends k2 {
        private int e;

        private c() {
            this.e = Integer.MIN_VALUE;
        }

        void a(float f, float f2) {
            this.a.lineTo(f, f2);
        }

        void b(float f, float f2) {
            this.a.reset();
            this.a.moveTo(f, f2);
            this.e = Integer.MIN_VALUE;
        }

        void c(int i) {
            this.e = i;
        }

        boolean e() {
            return this.a.isEmpty();
        }

        void f() {
            this.a.reset();
            this.e = Integer.MIN_VALUE;
        }

        k2 g() {
            if (b() == j2.DOODLE) {
                a(14.0f);
            } else {
                a(72.0f);
            }
            return new k2(new Path(this.a), b(), a(), d());
        }

        /* synthetic */ c(a aVar) {
            this();
        }

        boolean b(int i) {
            return this.e == i;
        }
    }

    public IMGView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = j2.NONE;
        this.b = new i2();
        this.f = new c(null);
        this.g = 0;
        this.h = new Paint(1);
        this.i = new Paint(1);
        Paint paint = this.h;
        Paint.Style style = Paint.Style.STROKE;
        paint.setStyle(style);
        this.h.setStrokeWidth(14.0f);
        this.h.setColor(-65536);
        this.h.setPathEffect(new CornerPathEffect(14.0f));
        Paint paint2 = this.h;
        Paint.Cap cap = Paint.Cap.ROUND;
        paint2.setStrokeCap(cap);
        Paint paint3 = this.h;
        Paint.Join join = Paint.Join.ROUND;
        paint3.setStrokeJoin(join);
        this.i.setStyle(style);
        this.i.setStrokeWidth(72.0f);
        this.i.setColor(-16777216);
        this.i.setPathEffect(new CornerPathEffect(72.0f));
        this.i.setStrokeCap(cap);
        this.i.setStrokeJoin(join);
        a(context);
        this.b.b(o4.a(getContext()));
    }

    private void a(Context context) {
        this.f.a(this.b.c());
        GestureDetector gestureDetector = new GestureDetector(context, new b(this, null));
        this.c = gestureDetector;
        gestureDetector.setIsLongpressEnabled(false);
        this.d = new ScaleGestureDetector(context, this);
    }

    boolean d(MotionEvent motionEvent) {
        boolean zE;
        if (d()) {
            return false;
        }
        this.g = motionEvent.getPointerCount();
        boolean zOnTouchEvent = this.d.onTouchEvent(motionEvent);
        j2 j2VarC = this.b.c();
        if (j2VarC != j2.NONE && j2VarC != j2.CLIP) {
            if (this.g > 1) {
                f();
                zE = e(motionEvent);
            } else {
                zE = f(motionEvent);
            }
        } else {
            zE = e(motionEvent);
        }
        boolean z = zOnTouchEvent | zE;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.b.f(motionEvent.getX(), motionEvent.getY());
            if (this.b.c() == j2.CLIP) {
                invalidate();
            }
        } else if (actionMasked == 1 || actionMasked == 3) {
            this.b.g(getScrollX(), getScrollY());
            e();
            return z;
        }
        return z;
    }

    private boolean c(MotionEvent motionEvent) {
        if (!this.f.b(motionEvent.getPointerId(0))) {
            return false;
        }
        this.f.a(motionEvent.getX(), motionEvent.getY());
        invalidate();
        return true;
    }

    public void b(int i, int i2) {
        i2 i2Var = this.b;
        if (i2Var != null) {
            i2Var.a(i, i2);
        }
    }

    private boolean e(MotionEvent motionEvent) {
        return this.c.onTouchEvent(motionEvent);
    }

    private void a(f2 f2Var, f2 f2Var2) {
        if (this.e == null) {
            g2 g2Var = new g2();
            this.e = g2Var;
            g2Var.addUpdateListener(this);
            this.e.addListener(this);
        }
        this.e.a(f2Var, f2Var2);
        this.e.start();
    }

    private boolean b(MotionEvent motionEvent) {
        this.f.b(motionEvent.getX(), motionEvent.getY());
        this.f.c(motionEvent.getPointerId(0));
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.dcloud.p.p2.a
    public void c(View view) {
        this.b.d((l2) view);
        invalidate();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.dcloud.p.p2.a
    public boolean b(View view) {
        i2 i2Var = this.b;
        if (i2Var != null) {
            i2Var.e((l2) view);
        }
        ((p2) view).b(this);
        ViewParent parent = view.getParent();
        if (parent == null) {
            return true;
        }
        ((ViewGroup) parent).removeView(view);
        return true;
    }

    private boolean f() {
        if (this.f.e()) {
            return false;
        }
        this.b.a(this.f.g(), getScrollX(), getScrollY());
        this.f.f();
        invalidate();
        return true;
    }

    public void a() {
        this.b.u();
        setMode(this.a);
    }

    private void a(Canvas canvas) {
        canvas.save();
        RectF rectFB = this.b.b();
        canvas.rotate(this.b.d(), rectFB.centerX(), rectFB.centerY());
        this.b.b(canvas);
        if (!this.b.k() || (this.b.c() == j2.MOSAIC && !this.f.e())) {
            int iC = this.b.c(canvas);
            if (this.b.c() == j2.MOSAIC && !this.f.e()) {
                this.h.setStrokeWidth(72.0f);
                canvas.save();
                RectF rectFB2 = this.b.b();
                canvas.rotate(-this.b.d(), rectFB2.centerX(), rectFB2.centerY());
                canvas.translate(getScrollX(), getScrollY());
                canvas.drawPath(this.f.c(), this.h);
                canvas.restore();
            }
            this.b.a(canvas, iC);
        }
        this.b.a(canvas);
        if (this.b.c() == j2.DOODLE && !this.f.e()) {
            this.h.setColor(this.f.a());
            this.h.setStrokeWidth(14.0f);
            canvas.save();
            RectF rectFB3 = this.b.b();
            canvas.rotate(-this.b.d(), rectFB3.centerX(), rectFB3.centerY());
            canvas.translate(getScrollX(), getScrollY());
            canvas.drawPath(this.f.c(), this.h);
            canvas.restore();
        }
        if (this.b.i()) {
            this.b.f(canvas);
        }
        this.b.d(canvas);
        canvas.restore();
        if (!this.b.i()) {
            this.b.e(canvas);
            this.b.f(canvas);
        }
        if (this.b.c() == j2.CLIP) {
            canvas.save();
            canvas.translate(getScrollX(), getScrollY());
            this.b.a(canvas, getScrollX(), getScrollY());
            canvas.restore();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void a(View view, FrameLayout.LayoutParams layoutParams) {
        if (view != 0) {
            addView(view, layoutParams);
            ((p2) view).a(this);
            this.b.a((l2) view);
        }
    }

    public void a(q2 q2Var) {
        IMGStickerTextView iMGStickerTextView = new IMGStickerTextView(getContext());
        iMGStickerTextView.setText(q2Var);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        iMGStickerTextView.setX(getScrollX());
        iMGStickerTextView.setY(getScrollY());
        a(iMGStickerTextView, layoutParams);
    }

    boolean a(MotionEvent motionEvent) {
        if (!d()) {
            return this.b.c() == j2.CLIP;
        }
        j();
        return true;
    }

    private void a(f2 f2Var) {
        this.b.d(f2Var.c);
        this.b.c(f2Var.d);
        if (a(Math.round(f2Var.a), Math.round(f2Var.b))) {
            return;
        }
        invalidate();
    }

    private boolean a(int i, int i2) {
        if (getScrollX() == i && getScrollY() == i2) {
            return false;
        }
        scrollTo(i, i2);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.dcloud.p.p2.a
    public void a(View view) {
        this.b.f((l2) view);
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(float f, float f2) {
        f2 f2VarA = this.b.a(getScrollX(), getScrollY(), -f, -f2);
        if (f2VarA != null) {
            a(f2VarA);
            return true;
        }
        return a(getScrollX() + Math.round(f), getScrollY() + Math.round(f2));
    }
}
