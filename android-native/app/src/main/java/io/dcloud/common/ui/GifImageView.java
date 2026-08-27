package io.dcloud.common.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import io.dcloud.PdrR;
import io.dcloud.common.DHInterface.IReflectAble;
import java.math.BigDecimal;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class GifImageView extends View implements IReflectAble {
    private float a;
    private float b;
    private float c;
    private Movie d;
    private long e;
    private long f;
    private long g;
    float h;
    private int i;
    private volatile boolean j;
    private volatile boolean k;
    private volatile boolean l;
    private boolean m;
    private a n;
    private int o;
    private int p;
    private int q;
    private float r;
    private float s;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface a {
        void a();

        void a(float f);

        void a(boolean z);

        void b();

        void c();
    }

    public GifImageView(Context context) {
        this(context, null);
    }

    private void a(Context context, AttributeSet attributeSet, int i) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, PdrR.STYLE_GIFVIEW, i, 0);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(PdrR.STYLE_GIFVIEW_gifSrc, 0);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(PdrR.STYLE_GIFVIEW_authPlay, true);
        this.i = typedArrayObtainStyledAttributes.getInt(PdrR.STYLE_GIFVIEW_playCount, -1);
        if (resourceId > 0) {
            setGifResource(resourceId, (a) null);
            if (z) {
                play(this.i);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
        setLayerType(1, null);
    }

    private void b() {
        this.j = false;
        this.e = SystemClock.uptimeMillis();
        this.k = false;
        this.l = true;
        this.f = 0L;
        this.g = 0L;
    }

    private int getCurrentFrameTime() {
        if (this.o == 0) {
            return 0;
        }
        long jUptimeMillis = SystemClock.uptimeMillis() - this.g;
        int i = (int) ((jUptimeMillis - this.e) / this.o);
        int i2 = this.i;
        if (i2 != -1 && i >= i2) {
            this.l = false;
            a aVar = this.n;
            if (aVar != null) {
                aVar.c();
            }
        }
        long j = jUptimeMillis - this.e;
        int i3 = this.o;
        float f = j % i3;
        this.h = f / i3;
        if (this.n != null && this.l) {
            double dDoubleValue = new BigDecimal(this.h).setScale(2, 4).doubleValue();
            if (dDoubleValue == 0.99d) {
                dDoubleValue = 1.0d;
            }
            this.n.a((float) dDoubleValue);
        }
        return (int) f;
    }

    public int getDuration() {
        Movie movie = this.d;
        if (movie != null) {
            return movie.duration();
        }
        return 0;
    }

    public boolean isPaused() {
        return this.k;
    }

    public boolean isPlaying() {
        return !this.k && this.l;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.d != null) {
            if (this.k || !this.l) {
                a(canvas);
                return;
            }
            if (this.j) {
                this.d.setTime(this.o - getCurrentFrameTime());
            } else {
                this.d.setTime(getCurrentFrameTime());
            }
            a(canvas);
            a();
        }
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.r = (getWidth() - this.p) / 2.0f;
        this.s = (getHeight() - this.q) / 2.0f;
        this.m = getVisibility() == 0;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        Movie movie = this.d;
        if (movie == null) {
            setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
            return;
        }
        int iWidth = movie.width();
        int iHeight = this.d.height();
        int size = View.MeasureSpec.getSize(i);
        float f = 1.0f / (iWidth / size);
        this.c = f;
        this.p = size;
        int i3 = (int) (iHeight * f);
        this.q = i3;
        setMeasuredDimension(size, i3);
    }

    @Override // android.view.View
    public void onScreenStateChanged(int i) {
        super.onScreenStateChanged(i);
        this.m = i == 1;
        a();
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        this.m = i == 0;
        a();
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        this.m = i == 0;
        a();
    }

    public void pause() {
        if (this.d == null || this.k || !this.l) {
            a aVar = this.n;
            if (aVar != null) {
                aVar.a(false);
                return;
            }
            return;
        }
        this.k = true;
        invalidate();
        this.f = SystemClock.uptimeMillis();
        a aVar2 = this.n;
        if (aVar2 != null) {
            aVar2.a(true);
        }
    }

    public void play(int i) {
        this.i = i;
        b();
        a aVar = this.n;
        if (aVar != null) {
            aVar.b();
        }
        invalidate();
    }

    public void playOver() {
        if (this.d != null) {
            play(-1);
        }
    }

    public void playReverse() {
        if (this.d != null) {
            b();
            this.j = true;
            a aVar = this.n;
            if (aVar != null) {
                aVar.b();
            }
            invalidate();
        }
    }

    public void setGifResource(int i, a aVar) {
        if (aVar != null) {
            this.n = aVar;
        }
        b();
        Movie movieDecodeStream = Movie.decodeStream(getResources().openRawResource(i));
        this.d = movieDecodeStream;
        this.o = movieDecodeStream.duration() == 0 ? 1000 : this.d.duration();
        requestLayout();
    }

    public void setOnPlayListener(a aVar) {
        this.n = aVar;
    }

    public void setPercent(float f) {
        int i;
        Movie movie = this.d;
        if (movie == null || (i = this.o) <= 0) {
            return;
        }
        this.h = f;
        movie.setTime((int) (i * f));
        a();
        a aVar = this.n;
        if (aVar != null) {
            aVar.a(f);
        }
    }

    public GifImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public GifImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = 1.0f;
        this.b = 1.0f;
        this.c = 1.0f;
        this.i = -1;
        this.j = false;
        this.m = true;
        a(context, attributeSet, i);
    }

    public void play() {
        if (this.d == null) {
            return;
        }
        if (this.l) {
            if (!this.k || this.f <= 0) {
                return;
            }
            this.k = false;
            this.g = (this.g + SystemClock.uptimeMillis()) - this.f;
            invalidate();
            a aVar = this.n;
            if (aVar != null) {
                aVar.a();
                return;
            }
            return;
        }
        play(this.i);
    }

    public void setGifResource(int i) {
        setGifResource(i, (a) null);
    }

    public void setGifResource(String str, a aVar) {
        this.d = Movie.decodeFile(str);
        this.n = aVar;
        b();
        this.o = this.d.duration() == 0 ? 1000 : this.d.duration();
        requestLayout();
        a aVar2 = this.n;
        if (aVar2 != null) {
            aVar2.b();
        }
    }

    private void a(Canvas canvas) {
        canvas.save();
        float f = this.c;
        canvas.scale(f, f);
        Movie movie = this.d;
        float f2 = this.r;
        float f3 = this.c;
        movie.draw(canvas, f2 / f3, this.s / f3);
        canvas.restore();
    }

    private void a() {
        if (this.m) {
            postInvalidateOnAnimation();
        }
    }
}
