package io.dcloud.p;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public class l4 extends Drawable {
    Paint a = new Paint();
    RectF b = null;
    Bitmap c = null;
    RectF d = null;
    Bitmap e;
    Context f;

    public l4(Bitmap bitmap, Context context) {
        this.e = bitmap;
        this.f = context;
    }

    private void a() {
        Rect bounds = getBounds();
        float height = this.e.getHeight();
        float width = this.e.getWidth();
        float fHeight = bounds.height();
        float fWidth = bounds.width();
        float f = height / width;
        float f2 = (fHeight / fWidth) - f;
        if (f2 <= 0.15d) {
            this.b = new RectF(0.0f, 0.0f, fWidth, fHeight);
            return;
        }
        float f3 = (fHeight - (f * fWidth)) / 2.0f;
        this.b = new RectF(0.0f, f3, fWidth, fHeight - f3);
        if (this.d == null) {
            this.c = this.e.copy(Bitmap.Config.ARGB_8888, true);
            Canvas canvas = new Canvas(this.c);
            canvas.scale(0.25f, 0.25f, width / 2.0f, height / 2.0f);
            canvas.drawColor(1711276032);
            float f4 = (f2 * width) / 2.0f;
            this.d = new RectF(-f4, 0.0f, fWidth + f4, fHeight);
            a(this.c);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Bitmap bitmap = this.e;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        if (this.c == null) {
            a();
        }
        Bitmap bitmap2 = this.c;
        if (bitmap2 != null) {
            canvas.drawBitmap(bitmap2, (Rect) null, this.d, this.a);
        }
        canvas.drawBitmap(this.e, (Rect) null, this.b, this.a);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    private void a(Bitmap bitmap) {
        RenderScript renderScriptCreate = RenderScript.create(this.f);
        Allocation allocationCreateFromBitmap = Allocation.createFromBitmap(renderScriptCreate, bitmap);
        ScriptIntrinsicBlur scriptIntrinsicBlurCreate = ScriptIntrinsicBlur.create(renderScriptCreate, allocationCreateFromBitmap.getElement());
        scriptIntrinsicBlurCreate.setInput(allocationCreateFromBitmap);
        scriptIntrinsicBlurCreate.setRadius(20.0f);
        scriptIntrinsicBlurCreate.forEach(allocationCreateFromBitmap);
        allocationCreateFromBitmap.copyTo(bitmap);
        renderScriptCreate.destroy();
    }
}
