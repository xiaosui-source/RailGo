package com.facebook.fresco.vito.renderer;

import android.graphics.Bitmap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ImageDataModel.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\nR\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0014\u0010\u0011\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000e¨\u0006\u0013"}, d2 = {"Lcom/facebook/fresco/vito/renderer/BitmapImageDataModel;", "Lcom/facebook/fresco/vito/renderer/ImageDataModel;", "bitmap", "Landroid/graphics/Bitmap;", "isBitmapCircular", "", "<init>", "(Landroid/graphics/Bitmap;Z)V", "getBitmap", "()Landroid/graphics/Bitmap;", "()Z", "width", "", "getWidth", "()I", "height", "getHeight", "defaultPaintFlags", "getDefaultPaintFlags", "renderer_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes.dex */
public final class BitmapImageDataModel extends ImageDataModel {
    private final Bitmap bitmap;
    private final int defaultPaintFlags;
    private final int height;
    private final boolean isBitmapCircular;
    private final int width;

    public /* synthetic */ BitmapImageDataModel(Bitmap bitmap, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(bitmap, (i & 2) != 0 ? false : z);
    }

    public final Bitmap getBitmap() {
        return this.bitmap;
    }

    /* renamed from: isBitmapCircular, reason: from getter */
    public final boolean getIsBitmapCircular() {
        return this.isBitmapCircular;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BitmapImageDataModel(Bitmap bitmap, boolean z) {
        super(null);
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        this.bitmap = bitmap;
        this.isBitmapCircular = z;
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        this.defaultPaintFlags = 6;
    }

    @Override // com.facebook.fresco.vito.renderer.ImageDataModel
    public int getWidth() {
        return this.width;
    }

    @Override // com.facebook.fresco.vito.renderer.ImageDataModel
    public int getHeight() {
        return this.height;
    }

    @Override // com.facebook.fresco.vito.renderer.ImageDataModel
    public int getDefaultPaintFlags() {
        return this.defaultPaintFlags;
    }
}
