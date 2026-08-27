package com.facebook.imagepipeline.animated.util;

import android.graphics.Bitmap;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AnimatedDrawableUtil.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u0007J\u000e\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007J\u0018\u0010\f\u001a\u00020\t2\b\u0010\r\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u000e\u001a\u00020\tJ\u0010\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007¨\u0006\u0013"}, d2 = {"Lcom/facebook/imagepipeline/animated/util/AnimatedDrawableUtil;", "", "<init>", "()V", "fixFrameDurations", "", "frameDurationMs", "", "getTotalDurationFromFrameDurations", "", "getFrameTimeStampsFromDurations", "frameDurationsMs", "getFrameForTimestampMs", "frameTimestampsMs", "timestampMs", "getSizeOfBitmap", "bitmap", "Landroid/graphics/Bitmap;", "Companion", "animated-base_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes.dex */
public final class AnimatedDrawableUtil {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final int FRAME_DURATION_MS_FOR_MIN = 100;
    private static final int MIN_FRAME_DURATION_MS = 11;

    @JvmStatic
    public static final boolean isOutsideRange(int i, int i2, int i3) {
        return INSTANCE.isOutsideRange(i, i2, i3);
    }

    public final void fixFrameDurations(int[] frameDurationMs) {
        Intrinsics.checkNotNullParameter(frameDurationMs, "frameDurationMs");
        int length = frameDurationMs.length;
        for (int i = 0; i < length; i++) {
            if (frameDurationMs[i] < 11) {
                frameDurationMs[i] = 100;
            }
        }
    }

    public final int getTotalDurationFromFrameDurations(int[] frameDurationMs) {
        Intrinsics.checkNotNullParameter(frameDurationMs, "frameDurationMs");
        int i = 0;
        for (int i2 : frameDurationMs) {
            i += i2;
        }
        return i;
    }

    public final int[] getFrameTimeStampsFromDurations(int[] frameDurationsMs) {
        Intrinsics.checkNotNullParameter(frameDurationsMs, "frameDurationsMs");
        int[] iArr = new int[frameDurationsMs.length];
        int length = frameDurationsMs.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = i;
            i += frameDurationsMs[i2];
        }
        return iArr;
    }

    public final int getFrameForTimestampMs(int[] frameTimestampsMs, int timestampMs) {
        int iBinarySearch = Arrays.binarySearch(frameTimestampsMs, timestampMs);
        return iBinarySearch < 0 ? (-iBinarySearch) - 2 : iBinarySearch;
    }

    public final int getSizeOfBitmap(Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        return bitmap.getAllocationByteCount();
    }

    /* compiled from: AnimatedDrawableUtil.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/facebook/imagepipeline/animated/util/AnimatedDrawableUtil$Companion;", "", "<init>", "()V", "MIN_FRAME_DURATION_MS", "", "FRAME_DURATION_MS_FOR_MIN", "isOutsideRange", "", "startFrame", "endFrame", "frameNumber", "animated-base_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final boolean isOutsideRange(int startFrame, int endFrame, int frameNumber) {
            if (startFrame == -1 || endFrame == -1) {
                return true;
            }
            return startFrame <= endFrame ? frameNumber < startFrame || frameNumber > endFrame : frameNumber < startFrame && frameNumber > endFrame;
        }

        private Companion() {
        }
    }
}
