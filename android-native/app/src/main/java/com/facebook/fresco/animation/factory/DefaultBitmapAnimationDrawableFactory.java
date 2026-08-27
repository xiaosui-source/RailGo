package com.facebook.fresco.animation.factory;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.internal.Suppliers;
import com.facebook.common.time.MonotonicClock;
import com.facebook.fresco.animation.backend.AnimationBackend;
import com.facebook.fresco.animation.backend.AnimationBackendDelegateWithInactivityCheck;
import com.facebook.fresco.animation.bitmap.BitmapAnimationBackend;
import com.facebook.fresco.animation.bitmap.BitmapFrameCache;
import com.facebook.fresco.animation.bitmap.BitmapFrameRenderer;
import com.facebook.fresco.animation.bitmap.cache.AnimationFrameCacheKey;
import com.facebook.fresco.animation.bitmap.cache.FrescoFrameCache;
import com.facebook.fresco.animation.bitmap.cache.KeepLastFrameCache;
import com.facebook.fresco.animation.bitmap.cache.NoOpCache;
import com.facebook.fresco.animation.bitmap.preparation.BitmapFramePreparationStrategy;
import com.facebook.fresco.animation.bitmap.preparation.BitmapFramePreparer;
import com.facebook.fresco.animation.bitmap.preparation.DefaultBitmapFramePreparer;
import com.facebook.fresco.animation.bitmap.preparation.FixedNumberBitmapFramePreparationStrategy;
import com.facebook.fresco.animation.bitmap.preparation.FrameLoaderStrategy;
import com.facebook.fresco.animation.bitmap.preparation.ondemandanimation.FrameLoaderFactory;
import com.facebook.fresco.animation.bitmap.wrapper.AnimatedDrawableBackendAnimationInformation;
import com.facebook.fresco.animation.bitmap.wrapper.AnimatedDrawableBackendFrameRenderer;
import com.facebook.fresco.animation.drawable.AnimatedDrawable2;
import com.facebook.fresco.animation.drawable.KAnimatedDrawable2;
import com.facebook.fresco.vito.options.ImageOptions;
import com.facebook.fresco.vito.options.ImageOptionsDrawableFactory;
import com.facebook.fresco.vito.options.RoundingOptions;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableBackend;
import com.facebook.imagepipeline.animated.base.AnimatedImage;
import com.facebook.imagepipeline.animated.base.AnimatedImageResult;
import com.facebook.imagepipeline.animated.impl.AnimatedDrawableBackendProvider;
import com.facebook.imagepipeline.animated.impl.AnimatedFrameCache;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.drawable.DrawableFactory;
import com.facebook.imagepipeline.image.CloseableAnimatedImage;
import com.facebook.imagepipeline.image.CloseableImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public class DefaultBitmapAnimationDrawableFactory implements DrawableFactory, ImageOptionsDrawableFactory {
    public static final int CACHING_STRATEGY_FRESCO_CACHE = 1;
    public static final int CACHING_STRATEGY_FRESCO_CACHE_NO_REUSING = 2;
    public static final int CACHING_STRATEGY_KEEP_LAST_CACHE = 3;
    public static final int CACHING_STRATEGY_NO_CACHE = 0;
    private final AnimatedDrawableBackendProvider mAnimatedDrawableBackendProvider;
    private final Supplier<Integer> mAnimationFpsLimit;
    private final CountingMemoryCache<CacheKey, CloseableImage> mBackingCache;
    private final Supplier<Integer> mBufferLengthMilliseconds;
    private final Supplier<Integer> mCachingStrategySupplier;
    private final Supplier<Boolean> mDownscaleFrameToDrawableDimensions;
    private final ExecutorService mExecutorServiceForFramePreparing;
    private final MonotonicClock mMonotonicClock;
    private final Supplier<Integer> mNumberOfFramesToPrepareSupplier;
    private final PlatformBitmapFactory mPlatformBitmapFactory;
    private final ScheduledExecutorService mScheduledExecutorServiceForUiThread;
    private final Supplier<Boolean> mUseDeepEqualsForCacheKey;
    private final Supplier<Boolean> mUseNewBitmapRender;
    private final Supplier<Boolean> useRendererAnimatedDrawable = Suppliers.BOOLEAN_FALSE;

    public DefaultBitmapAnimationDrawableFactory(AnimatedDrawableBackendProvider animatedDrawableBackendProvider, ScheduledExecutorService scheduledExecutorService, ExecutorService executorService, MonotonicClock monotonicClock, PlatformBitmapFactory platformBitmapFactory, CountingMemoryCache<CacheKey, CloseableImage> countingMemoryCache, Supplier<Integer> supplier, Supplier<Integer> supplier2, Supplier<Boolean> supplier3, Supplier<Boolean> supplier4, Supplier<Boolean> supplier5, Supplier<Integer> supplier6, Supplier<Integer> supplier7) {
        this.mAnimatedDrawableBackendProvider = animatedDrawableBackendProvider;
        this.mScheduledExecutorServiceForUiThread = scheduledExecutorService;
        this.mExecutorServiceForFramePreparing = executorService;
        this.mMonotonicClock = monotonicClock;
        this.mPlatformBitmapFactory = platformBitmapFactory;
        this.mBackingCache = countingMemoryCache;
        this.mCachingStrategySupplier = supplier;
        this.mNumberOfFramesToPrepareSupplier = supplier2;
        this.mUseDeepEqualsForCacheKey = supplier3;
        this.mUseNewBitmapRender = supplier4;
        this.mAnimationFpsLimit = supplier6;
        this.mDownscaleFrameToDrawableDimensions = supplier5;
        this.mBufferLengthMilliseconds = supplier7;
    }

    @Override // com.facebook.imagepipeline.drawable.DrawableFactory
    public boolean supportsImageType(CloseableImage closeableImage) {
        return closeableImage instanceof CloseableAnimatedImage;
    }

    @Override // com.facebook.imagepipeline.drawable.DrawableFactory
    public Drawable createDrawable(CloseableImage closeableImage) {
        CloseableAnimatedImage closeableAnimatedImage = (CloseableAnimatedImage) closeableImage;
        AnimatedImage image = closeableAnimatedImage.getImage();
        AnimationBackend animationBackendCreateAnimationBackend = createAnimationBackend((AnimatedImageResult) Preconditions.checkNotNull(closeableAnimatedImage.getImageResult()), image != null ? image.getAnimatedBitmapConfig() : null, null);
        if (this.useRendererAnimatedDrawable.get().booleanValue()) {
            return new KAnimatedDrawable2(animationBackendCreateAnimationBackend);
        }
        return new AnimatedDrawable2(animationBackendCreateAnimationBackend);
    }

    @Override // com.facebook.fresco.vito.options.ImageOptionsDrawableFactory
    public Drawable createDrawable(Resources resources, CloseableImage closeableImage, ImageOptions imageOptions) {
        CloseableAnimatedImage closeableAnimatedImage = (CloseableAnimatedImage) closeableImage;
        AnimatedImage image = closeableAnimatedImage.getImage();
        try {
            AnimationBackend animationBackendCreateAnimationBackend = createAnimationBackend((AnimatedImageResult) Preconditions.checkNotNull(closeableAnimatedImage.getImageResult()), image != null ? image.getAnimatedBitmapConfig() : null, imageOptions);
            if (this.useRendererAnimatedDrawable.get().booleanValue()) {
                return new KAnimatedDrawable2(animationBackendCreateAnimationBackend);
            }
            return new AnimatedDrawable2(animationBackendCreateAnimationBackend);
        } catch (NullPointerException e) {
            Object extra = closeableImage.getExtra("uri_source");
            if (extra != null) {
                throw new NullPointerException(e.getMessage() + " uri=" + extra.toString());
            }
            throw e;
        }
    }

    private AnimationBackend createAnimationBackend(AnimatedImageResult animatedImageResult, @Nullable Bitmap.Config config, @Nullable ImageOptions imageOptions) {
        BitmapFramePreparationStrategy frameLoaderStrategy;
        BitmapFramePreparer bitmapFramePreparerCreateBitmapFramePreparer;
        AnimatedDrawableBackend animatedDrawableBackendCreateAnimatedDrawableBackend = createAnimatedDrawableBackend(animatedImageResult);
        AnimatedDrawableBackendAnimationInformation animatedDrawableBackendAnimationInformation = new AnimatedDrawableBackendAnimationInformation(animatedDrawableBackendCreateAnimatedDrawableBackend);
        BitmapFrameCache bitmapFrameCacheCreateBitmapFrameCache = createBitmapFrameCache(animatedImageResult);
        AnimatedDrawableBackendFrameRenderer animatedDrawableBackendFrameRenderer = new AnimatedDrawableBackendFrameRenderer(bitmapFrameCacheCreateBitmapFrameCache, animatedDrawableBackendCreateAnimatedDrawableBackend, this.mUseNewBitmapRender.get().booleanValue());
        int iIntValue = this.mNumberOfFramesToPrepareSupplier.get().intValue();
        if (iIntValue > 0) {
            frameLoaderStrategy = new FixedNumberBitmapFramePreparationStrategy(iIntValue);
            bitmapFramePreparerCreateBitmapFramePreparer = createBitmapFramePreparer(animatedDrawableBackendFrameRenderer, config);
        } else {
            frameLoaderStrategy = null;
            bitmapFramePreparerCreateBitmapFramePreparer = null;
        }
        RoundingOptions roundingOptions = imageOptions != null ? imageOptions.getRoundingOptions() : null;
        if (this.mUseNewBitmapRender.get().booleanValue()) {
            frameLoaderStrategy = new FrameLoaderStrategy(animatedImageResult.getSource(), animatedDrawableBackendAnimationInformation, animatedDrawableBackendFrameRenderer, new FrameLoaderFactory(this.mPlatformBitmapFactory, this.mAnimationFpsLimit.get().intValue(), this.mBufferLengthMilliseconds.get().intValue()), this.mDownscaleFrameToDrawableDimensions.get().booleanValue());
        }
        return AnimationBackendDelegateWithInactivityCheck.createForBackend(new BitmapAnimationBackend(this.mPlatformBitmapFactory, bitmapFrameCacheCreateBitmapFrameCache, animatedDrawableBackendAnimationInformation, animatedDrawableBackendFrameRenderer, this.mUseNewBitmapRender.get().booleanValue(), frameLoaderStrategy, bitmapFramePreparerCreateBitmapFramePreparer, roundingOptions), this.mMonotonicClock, this.mScheduledExecutorServiceForUiThread);
    }

    private BitmapFramePreparer createBitmapFramePreparer(BitmapFrameRenderer bitmapFrameRenderer, @Nullable Bitmap.Config config) {
        PlatformBitmapFactory platformBitmapFactory = this.mPlatformBitmapFactory;
        if (config == null) {
            config = Bitmap.Config.ARGB_8888;
        }
        return new DefaultBitmapFramePreparer(platformBitmapFactory, bitmapFrameRenderer, config, this.mExecutorServiceForFramePreparing);
    }

    private AnimatedDrawableBackend createAnimatedDrawableBackend(AnimatedImageResult animatedImageResult) {
        AnimatedImage image = animatedImageResult.getImage();
        return this.mAnimatedDrawableBackendProvider.get(animatedImageResult, new Rect(0, 0, image.getWidth(), image.getHeight()));
    }

    private BitmapFrameCache createBitmapFrameCache(AnimatedImageResult animatedImageResult) {
        int iIntValue = this.mCachingStrategySupplier.get().intValue();
        if (iIntValue == 1) {
            return new FrescoFrameCache(createAnimatedFrameCache(animatedImageResult), true);
        }
        if (iIntValue == 2) {
            return new FrescoFrameCache(createAnimatedFrameCache(animatedImageResult), false);
        }
        if (iIntValue == 3) {
            return new KeepLastFrameCache();
        }
        return new NoOpCache();
    }

    private AnimatedFrameCache createAnimatedFrameCache(AnimatedImageResult animatedImageResult) {
        return new AnimatedFrameCache(new AnimationFrameCacheKey(animatedImageResult.hashCode(), this.mUseDeepEqualsForCacheKey.get().booleanValue()), this.mBackingCache);
    }
}
