package com.dcloud.android.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class DCProgressView extends View {
    int alpha;
    int curProgress;
    float h;
    Paint p;
    int w;
    int webviewProgress;

    public DCProgressView(Context context) {
        super(context);
        this.p = new Paint();
        this.curProgress = 0;
        this.webviewProgress = 0;
        this.alpha = 255;
        this.w = context.getResources().getDisplayMetrics().widthPixels;
    }

    public void fadeDisappear() {
        postDelayed(new Runnable() { // from class: com.dcloud.android.widget.DCProgressView.1
            @Override // java.lang.Runnable
            public void run() {
                DCProgressView dCProgressView = DCProgressView.this;
                int i = dCProgressView.alpha - 5;
                dCProgressView.alpha = i;
                if (i > 0) {
                    dCProgressView.postDelayed(this, 5L);
                } else {
                    ViewGroup viewGroup = (ViewGroup) dCProgressView.getParent();
                    if (viewGroup != null) {
                        viewGroup.removeView(DCProgressView.this);
                    }
                }
                DCProgressView.this.invalidate();
            }
        }, 50L);
    }

    public void finishProgress() {
        updateProgress(100);
    }

    public float getHeightInt() {
        return this.h;
    }

    public boolean isFinish() {
        return this.curProgress >= this.w;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.p.setAlpha(this.alpha);
        canvas.drawRect(0.0f, 0.0f, this.curProgress, this.h, this.p);
    }

    public void setAlphaInt(int i) {
        this.alpha = i;
    }

    public void setColorInt(int i) {
        this.p.setColor(Color.argb(this.alpha, Color.red(i), Color.green(i), Color.blue(i)));
    }

    public void setCurProgress(int i) {
        this.curProgress = i;
    }

    public void setHeightInt(int i) {
        this.h = i;
    }

    public void setMaxProgress(int i) {
        this.w = i;
    }

    public void setWebviewProgress(int i) {
        this.webviewProgress = i;
    }

    public void updateProgress(int i) {
        int i2 = (this.w * i) / 100;
        if (this.curProgress >= this.webviewProgress) {
            postDelayed(new Runnable() { // from class: com.dcloud.android.widget.DCProgressView.2
                @Override // java.lang.Runnable
                public void run() {
                    DCProgressView dCProgressView = DCProgressView.this;
                    int i3 = dCProgressView.webviewProgress;
                    int i4 = dCProgressView.curProgress;
                    int i5 = 10;
                    int i6 = (i3 - i4) / 10;
                    if (i6 <= 10) {
                        i5 = 1;
                        if (i6 >= 1) {
                            i5 = i6;
                        }
                    }
                    int i7 = i4 + i5;
                    dCProgressView.curProgress = i7;
                    if (i3 > i7) {
                        dCProgressView.postDelayed(this, 5L);
                    } else if (i3 >= dCProgressView.w) {
                        dCProgressView.fadeDisappear();
                    }
                    DCProgressView.this.invalidate();
                }
            }, 5L);
        }
        this.webviewProgress = i2;
    }
}
