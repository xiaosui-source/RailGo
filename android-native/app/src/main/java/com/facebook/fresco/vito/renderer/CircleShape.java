package com.facebook.fresco.vito.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Shape.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\b\u0010\tJ\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\n¨\u0006\u0011"}, d2 = {"Lcom/facebook/fresco/vito/renderer/CircleShape;", "Lcom/facebook/fresco/vito/renderer/Shape;", "cx", "", "cy", "radius", "antiAliased", "", "<init>", "(FFFLjava/lang/Boolean;)V", "Ljava/lang/Boolean;", "draw", "", "canvas", "Landroid/graphics/Canvas;", "paint", "Landroid/graphics/Paint;", "renderer_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes.dex */
public final class CircleShape extends Shape {
    private final Boolean antiAliased;
    private final float cx;
    private final float cy;
    private final float radius;

    public /* synthetic */ CircleShape(float f, float f2, float f3, Boolean bool, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, f3, (i & 8) != 0 ? null : bool);
    }

    public CircleShape(float f, float f2, float f3, Boolean bool) {
        super(null);
        this.cx = f;
        this.cy = f2;
        this.radius = f3;
        this.antiAliased = bool;
    }

    @Override // com.facebook.fresco.vito.renderer.Shape
    public void draw(Canvas canvas, Paint paint) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(paint, "paint");
        if (this.antiAliased != null) {
            boolean zIsAntiAlias = paint.isAntiAlias();
            paint.setAntiAlias(this.antiAliased.booleanValue());
            canvas.drawCircle(this.cx, this.cy, this.radius, paint);
            paint.setAntiAlias(zIsAntiAlias);
            return;
        }
        canvas.drawCircle(this.cx, this.cy, this.radius, paint);
    }
}
