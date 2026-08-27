package io.dcloud.feature.gallery.imageedit.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import io.dcloud.base.R;
import io.dcloud.p.l2;
import io.dcloud.p.m2;
import io.dcloud.p.n2;
import io.dcloud.p.o2;
import io.dcloud.p.p2;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public abstract class IMGStickerView extends ViewGroup implements l2, View.OnClickListener {
    private View a;
    private float b;
    private int c;
    private o2 d;
    private n2 e;
    private ImageView f;
    private ImageView g;
    private float h;
    private Paint i;
    private Matrix j;
    private RectF k;
    private Rect l;

    public IMGStickerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private ViewGroup.LayoutParams getAnchorLayoutParams() {
        return new ViewGroup.LayoutParams(48, 48);
    }

    private ViewGroup.LayoutParams getContentLayoutParams() {
        return new ViewGroup.LayoutParams(-2, -2);
    }

    public abstract View a(Context context);

    @Override // io.dcloud.p.s2
    public void a(float f) {
        setScale(getScale() * f);
    }

    public void b(Context context) {
        setBackgroundColor(0);
        View viewA = a(context);
        this.a = viewA;
        addView(viewA, getContentLayoutParams());
        ImageView imageView = new ImageView(context);
        this.f = imageView;
        ImageView.ScaleType scaleType = ImageView.ScaleType.FIT_XY;
        imageView.setScaleType(scaleType);
        this.f.setImageResource(R.mipmap.image_ic_delete);
        addView(this.f, getAnchorLayoutParams());
        this.f.setOnClickListener(this);
        ImageView imageView2 = new ImageView(context);
        this.g = imageView2;
        imageView2.setScaleType(scaleType);
        this.g.setImageResource(R.mipmap.image_ic_adjust);
        addView(this.g, getAnchorLayoutParams());
        new m2(this, this.g);
        this.e = new n2(this);
        this.d = new o2(this);
    }

    public void c() {
    }

    public void d() {
        this.e.c();
    }

    @Override // io.dcloud.p.p2
    public boolean dismiss() {
        return this.e.dismiss();
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        Canvas canvas2;
        if (a()) {
            canvas2 = canvas;
            canvas2.drawRect(24.0f, 24.0f, getWidth() - 24, getHeight() - 24, this.i);
        } else {
            canvas2 = canvas;
        }
        super.draw(canvas2);
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        return a() && super.drawChild(canvas, view, j);
    }

    @Override // io.dcloud.p.p2
    public RectF getFrame() {
        return this.e.getFrame();
    }

    @Override // io.dcloud.p.s2
    public float getScale() {
        return this.b;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this.f) {
            d();
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (a() || motionEvent.getAction() != 0) {
            return a() && super.onInterceptTouchEvent(motionEvent);
        }
        this.c = 0;
        b();
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.k.set(i, i2, i3, i4);
        if (getChildCount() == 0) {
            return;
        }
        ImageView imageView = this.f;
        imageView.layout(0, 0, imageView.getMeasuredWidth(), this.f.getMeasuredHeight());
        ImageView imageView2 = this.g;
        int i5 = i3 - i;
        int i6 = i4 - i2;
        imageView2.layout(i5 - imageView2.getMeasuredWidth(), i6 - this.g.getMeasuredHeight(), i5, i6);
        int i7 = i5 >> 1;
        int i8 = i6 >> 1;
        int measuredWidth = this.a.getMeasuredWidth() >> 1;
        int measuredHeight = this.a.getMeasuredHeight() >> 1;
        this.a.layout(i7 - measuredWidth, i8 - measuredHeight, i7 + measuredWidth, i8 + measuredHeight);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        int iRound = 0;
        int iCombineMeasuredStates = 0;
        int iRound2 = 0;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                childAt.measure(i, i2);
                iRound2 = Math.round(Math.max(iRound2, childAt.getMeasuredWidth() * childAt.getScaleX()));
                iRound = Math.round(Math.max(iRound, childAt.getMeasuredHeight() * childAt.getScaleY()));
                iCombineMeasuredStates = ViewGroup.combineMeasuredStates(iCombineMeasuredStates, childAt.getMeasuredState());
            }
        }
        setMeasuredDimension(ViewGroup.resolveSizeAndState(Math.max(iRound2, getSuggestedMinimumWidth()), i, iCombineMeasuredStates), ViewGroup.resolveSizeAndState(Math.max(iRound, getSuggestedMinimumHeight()), i2, iCombineMeasuredStates << 16));
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean zA = this.d.a(this, motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.c++;
        } else if (actionMasked == 1 && this.c > 1 && motionEvent.getEventTime() - motionEvent.getDownTime() < ViewConfiguration.getTapTimeout()) {
            c();
            return true;
        }
        return super.onTouchEvent(motionEvent) | zA;
    }

    public void setScale(float f) {
        this.b = f;
        this.a.setScaleX(f);
        this.a.setScaleY(this.b);
        float left = (getLeft() + getRight()) >> 1;
        float top = (getTop() + getBottom()) >> 1;
        this.k.set(left, top, left, top);
        this.k.inset(-(this.a.getMeasuredWidth() >> 1), -(this.a.getMeasuredHeight() >> 1));
        Matrix matrix = this.j;
        float f2 = this.b;
        matrix.setScale(f2, f2, this.k.centerX(), this.k.centerY());
        this.j.mapRect(this.k);
        this.k.round(this.l);
        Rect rect = this.l;
        layout(rect.left, rect.top, rect.right, rect.bottom);
    }

    public IMGStickerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.b = 1.0f;
        this.c = 0;
        this.h = 4.0f;
        this.j = new Matrix();
        this.k = new RectF();
        this.l = new Rect();
        Paint paint = new Paint(1);
        this.i = paint;
        paint.setColor(-1);
        this.i.setStyle(Paint.Style.STROKE);
        this.i.setStrokeWidth(3.0f);
        b(context);
    }

    @Override // io.dcloud.p.p2
    public boolean a() {
        return this.e.a();
    }

    @Override // io.dcloud.p.p2
    public void a(Canvas canvas) {
        canvas.translate(this.a.getX(), this.a.getY());
        this.a.draw(canvas);
    }

    @Override // io.dcloud.p.p2
    public void a(p2.a aVar) {
        this.e.a(aVar);
    }

    @Override // io.dcloud.p.p2
    public boolean b() {
        return this.e.b();
    }

    @Override // io.dcloud.p.p2
    public void b(p2.a aVar) {
        this.e.b(aVar);
    }
}
