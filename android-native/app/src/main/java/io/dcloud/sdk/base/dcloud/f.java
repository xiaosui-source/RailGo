package io.dcloud.sdk.base.dcloud;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import io.dcloud.sdk.base.dcloud.ADHandler;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class f extends View {
    Bitmap a;
    int b;
    int c;
    ADHandler.e d;
    Paint e;
    Rect f;
    RectF g;
    Bitmap h;
    RectF i;

    public f(Context context, Bitmap bitmap, ADHandler.e eVar) {
        super(context);
        this.a = null;
        this.b = 0;
        this.c = 0;
        this.d = null;
        this.e = new Paint();
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        this.a = bitmap;
        this.d = eVar;
    }

    private void a(Canvas canvas) {
        float height = this.a.getHeight();
        float width = this.a.getWidth();
        float height2 = getHeight() > 0 ? getHeight() : canvas.getHeight();
        float width2 = getWidth() > 0 ? getWidth() : canvas.getWidth();
        float f = height / width;
        float f2 = (height2 / width2) - f;
        if (f2 <= 0.15d) {
            this.g = new RectF(0.0f, 0.0f, width2, height2);
            return;
        }
        float f3 = (height2 - (f * width2)) / 2.0f;
        this.g = new RectF(0.0f, f3, width2, height2 - f3);
        if (this.i == null) {
            int i = (int) width;
            int i2 = (int) height;
            this.h = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(this.h);
            canvas2.drawBitmap(this.a, new Rect(0, 0, i, i2), new Rect(0, 0, i, i2), new Paint());
            canvas2.drawColor(b(this.a) >= 128 ? 1711276032 : 1728053247);
            float f4 = (f2 * width) / 2.0f;
            this.i = new RectF(-f4, 0.0f, width2 + f4, height2);
            a(this.h);
        }
    }

    public int b(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < width; i3++) {
            for (int i4 = 0; i4 < height; i4++) {
                i2++;
                int pixel = bitmap.getPixel(i3, i4);
                i = (int) (i + (((((-16711681) | pixel) >> 16) & 255) * 0.299d) + (((((-65281) | pixel) >> 8) & 255) * 0.587d) + (((pixel | (-256)) & 255) * 0.114d));
            }
        }
        return i / i2;
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.h = null;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = this.a;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        a(canvas);
        Bitmap bitmap2 = this.h;
        if (bitmap2 != null) {
            canvas.drawBitmap(bitmap2, this.f, this.i, this.e);
        }
        canvas.drawBitmap(this.a, this.f, this.g, this.e);
    }

    private void a(Bitmap bitmap) {
        RenderScript renderScriptCreate = RenderScript.create(getContext());
        Allocation allocationCreateFromBitmap = Allocation.createFromBitmap(renderScriptCreate, bitmap);
        ScriptIntrinsicBlur scriptIntrinsicBlurCreate = ScriptIntrinsicBlur.create(renderScriptCreate, allocationCreateFromBitmap.getElement());
        scriptIntrinsicBlurCreate.setInput(allocationCreateFromBitmap);
        scriptIntrinsicBlurCreate.setRadius(20.0f);
        scriptIntrinsicBlurCreate.forEach(allocationCreateFromBitmap);
        allocationCreateFromBitmap.copyTo(bitmap);
        renderScriptCreate.destroy();
    }
}
