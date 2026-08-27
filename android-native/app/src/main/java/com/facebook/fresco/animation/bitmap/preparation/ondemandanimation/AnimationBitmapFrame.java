package com.facebook.fresco.animation.bitmap.preparation.ondemandanimation;

import android.graphics.Bitmap;
import com.facebook.common.references.CloseableReference;
import io.dcloud.common.constant.AbsoluteConst;
import java.io.Closeable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AnimationBitmapFrame.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u0003J\u0006\u0010\u0011\u001a\u00020\u0010J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0014"}, d2 = {"Lcom/facebook/fresco/animation/bitmap/preparation/ondemandanimation/AnimationBitmapFrame;", "Ljava/io/Closeable;", "frameNumber", "", "bitmap", "Lcom/facebook/common/references/CloseableReference;", "Landroid/graphics/Bitmap;", "<init>", "(ILcom/facebook/common/references/CloseableReference;)V", "getFrameNumber", "()I", "setFrameNumber", "(I)V", "getBitmap", "()Lcom/facebook/common/references/CloseableReference;", "isValidFor", "", "isValid", AbsoluteConst.EVENTS_CLOSE, "", "animated-drawable_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes.dex */
public final class AnimationBitmapFrame implements Closeable {
    private final CloseableReference<Bitmap> bitmap;
    private int frameNumber;

    public AnimationBitmapFrame(int i, CloseableReference<Bitmap> bitmap) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        this.frameNumber = i;
        this.bitmap = bitmap;
    }

    public final CloseableReference<Bitmap> getBitmap() {
        return this.bitmap;
    }

    public final int getFrameNumber() {
        return this.frameNumber;
    }

    public final void setFrameNumber(int i) {
        this.frameNumber = i;
    }

    public final boolean isValidFor(int frameNumber) {
        return this.frameNumber == frameNumber && this.bitmap.isValid();
    }

    public final boolean isValid() {
        return this.bitmap.isValid();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.bitmap.close();
    }
}
