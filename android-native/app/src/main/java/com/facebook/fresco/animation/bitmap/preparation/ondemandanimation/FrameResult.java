package com.facebook.fresco.animation.bitmap.preparation.ondemandanimation;

import android.graphics.Bitmap;
import com.facebook.common.references.CloseableReference;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FrameLoader.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0001\rB\u001f\u0012\u000e\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bR\u0019\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u000e"}, d2 = {"Lcom/facebook/fresco/animation/bitmap/preparation/ondemandanimation/FrameResult;", "", "bitmapRef", "Lcom/facebook/common/references/CloseableReference;", "Landroid/graphics/Bitmap;", "type", "Lcom/facebook/fresco/animation/bitmap/preparation/ondemandanimation/FrameResult$FrameType;", "<init>", "(Lcom/facebook/common/references/CloseableReference;Lcom/facebook/fresco/animation/bitmap/preparation/ondemandanimation/FrameResult$FrameType;)V", "getBitmapRef", "()Lcom/facebook/common/references/CloseableReference;", "getType", "()Lcom/facebook/fresco/animation/bitmap/preparation/ondemandanimation/FrameResult$FrameType;", "FrameType", "animated-drawable_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes.dex */
public final class FrameResult {
    private final CloseableReference<Bitmap> bitmapRef;
    private final FrameType type;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: FrameLoader.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lcom/facebook/fresco/animation/bitmap/preparation/ondemandanimation/FrameResult$FrameType;", "", "<init>", "(Ljava/lang/String;I)V", "SUCCESS", "NEAREST", "MISSING", "animated-drawable_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
    public static final class FrameType {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ FrameType[] $VALUES;
        public static final FrameType SUCCESS = new FrameType("SUCCESS", 0);
        public static final FrameType NEAREST = new FrameType("NEAREST", 1);
        public static final FrameType MISSING = new FrameType("MISSING", 2);

        private static final /* synthetic */ FrameType[] $values() {
            return new FrameType[]{SUCCESS, NEAREST, MISSING};
        }

        public static EnumEntries<FrameType> getEntries() {
            return $ENTRIES;
        }

        private FrameType(String str, int i) {
        }

        static {
            FrameType[] frameTypeArr$values = $values();
            $VALUES = frameTypeArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(frameTypeArr$values);
        }

        public static FrameType valueOf(String str) {
            return (FrameType) Enum.valueOf(FrameType.class, str);
        }

        public static FrameType[] values() {
            return (FrameType[]) $VALUES.clone();
        }
    }

    public FrameResult(CloseableReference<Bitmap> closeableReference, FrameType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.bitmapRef = closeableReference;
        this.type = type;
    }

    public final CloseableReference<Bitmap> getBitmapRef() {
        return this.bitmapRef;
    }

    public final FrameType getType() {
        return this.type;
    }
}
