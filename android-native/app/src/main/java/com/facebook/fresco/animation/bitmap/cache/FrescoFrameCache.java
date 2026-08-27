package com.facebook.fresco.animation.bitmap.cache;

import android.graphics.Bitmap;
import android.util.SparseArray;
import com.facebook.common.logging.FLog;
import com.facebook.common.references.CloseableReference;
import com.facebook.fresco.animation.bitmap.BitmapFrameCache;
import com.facebook.imagepipeline.animated.impl.AnimatedFrameCache;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imageutils.BitmapUtil;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FrescoFrameCache.kt */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\b\u0003\u0018\u0000 )2\u00020\u0001:\u0001)B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0018\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\n2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\n2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J(\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\n2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0010H\u0016J\u0011\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0010H\u0096\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0016J&\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000e0\n2\u0006\u0010\u001d\u001a\u00020\u0010H\u0016J&\u0010\u001e\u001a\u00020\u001a2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000e0\n2\u0006\u0010\u001d\u001a\u00020\u0010H\u0016J\u0012\u0010\u001f\u001a\u00020\u001a2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\u0010\u0010$\u001a\u00020\u001a2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\"\u0010%\u001a\u00020\u00052\u0018\u0010&\u001a\u0014\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\n0'H\u0016J\b\u0010(\u001a\u00020\u0005H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\b\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n0\t8\u0002X\u0083\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n8\u0002@\u0002X\u0083\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\"\u001a\u00020\u00108BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b#\u0010\u0018¨\u0006*"}, d2 = {"Lcom/facebook/fresco/animation/bitmap/cache/FrescoFrameCache;", "Lcom/facebook/fresco/animation/bitmap/BitmapFrameCache;", "animatedFrameCache", "Lcom/facebook/imagepipeline/animated/impl/AnimatedFrameCache;", "enableBitmapReusing", "", "<init>", "(Lcom/facebook/imagepipeline/animated/impl/AnimatedFrameCache;Z)V", "preparedPendingFrames", "Landroid/util/SparseArray;", "Lcom/facebook/common/references/CloseableReference;", "Lcom/facebook/imagepipeline/image/CloseableImage;", "lastRenderedItem", "getCachedFrame", "Landroid/graphics/Bitmap;", "frameNumber", "", "getFallbackFrame", "getBitmapToReuseForFrame", "width", "height", "contains", "sizeInBytes", "getSizeInBytes", "()I", "clear", "", "onFrameRendered", "bitmapReference", "frameType", "onFramePrepared", "setFrameCacheListener", "frameCacheListener", "Lcom/facebook/fresco/animation/bitmap/BitmapFrameCache$FrameCacheListener;", "preparedPendingFramesSizeBytes", "getPreparedPendingFramesSizeBytes", "removePreparedReference", "onAnimationPrepared", "frameBitmaps", "", "isAnimationReady", "Companion", "animated-base_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes.dex */
public final class FrescoFrameCache implements BitmapFrameCache {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Class<?> TAG = FrescoFrameCache.class;
    private final AnimatedFrameCache animatedFrameCache;
    private final boolean enableBitmapReusing;
    private CloseableReference<CloseableImage> lastRenderedItem;
    private final SparseArray<CloseableReference<CloseableImage>> preparedPendingFrames;

    @JvmStatic
    public static final CloseableReference<Bitmap> convertToBitmapReferenceAndClose(CloseableReference<CloseableImage> closeableReference) {
        return INSTANCE.convertToBitmapReferenceAndClose(closeableReference);
    }

    @Override // com.facebook.fresco.animation.bitmap.BitmapFrameCache
    public boolean isAnimationReady() {
        return false;
    }

    @Override // com.facebook.fresco.animation.bitmap.BitmapFrameCache
    public boolean onAnimationPrepared(Map<Integer, ? extends CloseableReference<Bitmap>> frameBitmaps) {
        Intrinsics.checkNotNullParameter(frameBitmaps, "frameBitmaps");
        return true;
    }

    @Override // com.facebook.fresco.animation.bitmap.BitmapFrameCache
    public void setFrameCacheListener(BitmapFrameCache.FrameCacheListener frameCacheListener) {
    }

    public FrescoFrameCache(AnimatedFrameCache animatedFrameCache, boolean z) {
        Intrinsics.checkNotNullParameter(animatedFrameCache, "animatedFrameCache");
        this.animatedFrameCache = animatedFrameCache;
        this.enableBitmapReusing = z;
        this.preparedPendingFrames = new SparseArray<>();
    }

    @Override // com.facebook.fresco.animation.bitmap.BitmapFrameCache
    public synchronized CloseableReference<Bitmap> getCachedFrame(int frameNumber) {
        return INSTANCE.convertToBitmapReferenceAndClose(this.animatedFrameCache.get(frameNumber));
    }

    @Override // com.facebook.fresco.animation.bitmap.BitmapFrameCache
    public synchronized CloseableReference<Bitmap> getFallbackFrame(int frameNumber) {
        return INSTANCE.convertToBitmapReferenceAndClose(CloseableReference.cloneOrNull(this.lastRenderedItem));
    }

    @Override // com.facebook.fresco.animation.bitmap.BitmapFrameCache
    public synchronized CloseableReference<Bitmap> getBitmapToReuseForFrame(int frameNumber, int width, int height) {
        if (!this.enableBitmapReusing) {
            return null;
        }
        return INSTANCE.convertToBitmapReferenceAndClose(this.animatedFrameCache.getForReuse());
    }

    @Override // com.facebook.fresco.animation.bitmap.BitmapFrameCache
    public synchronized boolean contains(int frameNumber) {
        return this.animatedFrameCache.contains(frameNumber);
    }

    @Override // com.facebook.fresco.animation.bitmap.BitmapFrameCache
    public synchronized int getSizeInBytes() {
        return INSTANCE.getBitmapSizeBytes(this.lastRenderedItem) + getPreparedPendingFramesSizeBytes();
    }

    @Override // com.facebook.fresco.animation.bitmap.BitmapFrameCache
    public synchronized void clear() {
        CloseableReference.closeSafely(this.lastRenderedItem);
        this.lastRenderedItem = null;
        int size = this.preparedPendingFrames.size();
        for (int i = 0; i < size; i++) {
            CloseableReference.closeSafely(this.preparedPendingFrames.valueAt(i));
        }
        this.preparedPendingFrames.clear();
    }

    @Override // com.facebook.fresco.animation.bitmap.BitmapFrameCache
    public synchronized void onFrameRendered(int frameNumber, CloseableReference<Bitmap> bitmapReference, int frameType) {
        Intrinsics.checkNotNullParameter(bitmapReference, "bitmapReference");
        removePreparedReference(frameNumber);
        CloseableReference<CloseableImage> closeableReferenceCreateImageReference = null;
        try {
            closeableReferenceCreateImageReference = INSTANCE.createImageReference(bitmapReference);
            if (closeableReferenceCreateImageReference != null) {
                CloseableReference.closeSafely(this.lastRenderedItem);
                this.lastRenderedItem = this.animatedFrameCache.cache(frameNumber, closeableReferenceCreateImageReference);
            }
        } finally {
            CloseableReference.closeSafely(closeableReferenceCreateImageReference);
        }
    }

    @Override // com.facebook.fresco.animation.bitmap.BitmapFrameCache
    public synchronized void onFramePrepared(int frameNumber, CloseableReference<Bitmap> bitmapReference, int frameType) {
        Intrinsics.checkNotNullParameter(bitmapReference, "bitmapReference");
        try {
            CloseableReference<CloseableImage> closeableReferenceCreateImageReference = INSTANCE.createImageReference(bitmapReference);
            if (closeableReferenceCreateImageReference != null) {
                CloseableReference<CloseableImage> closeableReferenceCache = this.animatedFrameCache.cache(frameNumber, closeableReferenceCreateImageReference);
                if (CloseableReference.isValid(closeableReferenceCache)) {
                    CloseableReference.closeSafely(this.preparedPendingFrames.get(frameNumber));
                    this.preparedPendingFrames.put(frameNumber, closeableReferenceCache);
                    FLog.v(TAG, "cachePreparedFrame(%d) cached. Pending frames: %s", Integer.valueOf(frameNumber), this.preparedPendingFrames);
                }
                CloseableReference.closeSafely(closeableReferenceCreateImageReference);
                return;
            }
            CloseableReference.closeSafely(closeableReferenceCreateImageReference);
        } catch (Throwable th) {
            CloseableReference.closeSafely((CloseableReference<?>) null);
            throw th;
        }
    }

    private final synchronized int getPreparedPendingFramesSizeBytes() {
        int bitmapSizeBytes;
        int size = this.preparedPendingFrames.size();
        bitmapSizeBytes = 0;
        for (int i = 0; i < size; i++) {
            bitmapSizeBytes += INSTANCE.getBitmapSizeBytes(this.preparedPendingFrames.valueAt(i));
        }
        return bitmapSizeBytes;
    }

    private final synchronized void removePreparedReference(int frameNumber) {
        CloseableReference<CloseableImage> closeableReference = this.preparedPendingFrames.get(frameNumber);
        if (closeableReference != null) {
            this.preparedPendingFrames.delete(frameNumber);
            CloseableReference.closeSafely(closeableReference);
            FLog.v(TAG, "removePreparedReference(%d) removed. Pending frames: %s", Integer.valueOf(frameNumber), this.preparedPendingFrames);
        }
    }

    /* compiled from: FrescoFrameCache.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J \u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00072\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u0007H\u0007J\u0018\u0010\u000b\u001a\u00020\f2\u000e\u0010\r\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u0007H\u0002J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\u000e\u001a\u0004\u0018\u00010\nH\u0002J \u0010\u000f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\n\u0018\u00010\u00072\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0002R\u0012\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/facebook/fresco/animation/bitmap/cache/FrescoFrameCache$Companion;", "", "<init>", "()V", "TAG", "Ljava/lang/Class;", "convertToBitmapReferenceAndClose", "Lcom/facebook/common/references/CloseableReference;", "Landroid/graphics/Bitmap;", "closeableImage", "Lcom/facebook/imagepipeline/image/CloseableImage;", "getBitmapSizeBytes", "", "imageReference", "image", "createImageReference", "bitmapReference", "animated-base_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final CloseableReference<Bitmap> convertToBitmapReferenceAndClose(CloseableReference<CloseableImage> closeableImage) {
            try {
                if (CloseableReference.isValid(closeableImage)) {
                    Intrinsics.checkNotNull(closeableImage);
                    if (closeableImage.get() instanceof CloseableStaticBitmap) {
                        CloseableImage closeableImage2 = closeableImage.get();
                        Intrinsics.checkNotNull(closeableImage2, "null cannot be cast to non-null type com.facebook.imagepipeline.image.CloseableStaticBitmap");
                        return ((CloseableStaticBitmap) closeableImage2).cloneUnderlyingBitmapReference();
                    }
                }
                CloseableReference.closeSafely(closeableImage);
                return null;
            } finally {
                CloseableReference.closeSafely(closeableImage);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final int getBitmapSizeBytes(CloseableReference<CloseableImage> imageReference) {
            if (!CloseableReference.isValid(imageReference)) {
                return 0;
            }
            Intrinsics.checkNotNull(imageReference);
            return getBitmapSizeBytes(imageReference.get());
        }

        private final int getBitmapSizeBytes(CloseableImage image) {
            if (image instanceof CloseableBitmap) {
                return BitmapUtil.getSizeInBytes(((CloseableBitmap) image).getUnderlyingBitmap());
            }
            return 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final CloseableReference<CloseableImage> createImageReference(CloseableReference<Bitmap> bitmapReference) {
            CloseableStaticBitmap closeableStaticBitmapOf = CloseableStaticBitmap.CC.of(bitmapReference, ImmutableQualityInfo.FULL_QUALITY, 0);
            Intrinsics.checkNotNullExpressionValue(closeableStaticBitmapOf, "of(...)");
            return CloseableReference.of(closeableStaticBitmapOf);
        }
    }
}
