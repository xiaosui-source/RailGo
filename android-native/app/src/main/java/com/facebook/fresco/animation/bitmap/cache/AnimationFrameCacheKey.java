package com.facebook.fresco.animation.bitmap.cache;

import android.net.Uri;
import com.facebook.cache.common.CacheKey;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: AnimationFrameCacheKey.kt */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u001b\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\tH\u0016J\b\u0010\u000e\u001a\u00020\u0005H\u0016J\u0013\u0010\u000f\u001a\u00020\u00052\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0003H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/facebook/fresco/animation/bitmap/cache/AnimationFrameCacheKey;", "Lcom/facebook/cache/common/CacheKey;", "imageId", "", "deepEquals", "", "<init>", "(IZ)V", "animationUriString", "", "containsUri", "uri", "Landroid/net/Uri;", "getUriString", "isResourceIdForDebugging", "equals", "o", "", "hashCode", "Companion", "animated-base_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes.dex */
public final class AnimationFrameCacheKey implements CacheKey {
    private static final String URI_PREFIX = "anim://";
    private final String animationUriString;
    private final boolean deepEquals;

    public AnimationFrameCacheKey(int i) {
        this(i, false, 2, null);
    }

    @Override // com.facebook.cache.common.CacheKey
    public boolean isResourceIdForDebugging() {
        return false;
    }

    public AnimationFrameCacheKey(int i, boolean z) {
        this.deepEquals = z;
        this.animationUriString = URI_PREFIX + i;
    }

    public /* synthetic */ AnimationFrameCacheKey(int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? false : z);
    }

    @Override // com.facebook.cache.common.CacheKey
    public boolean containsUri(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        String string = uri.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return StringsKt.startsWith$default(string, this.animationUriString, false, 2, (Object) null);
    }

    @Override // com.facebook.cache.common.CacheKey
    /* renamed from: getUriString, reason: from getter */
    public String getAnimationUriString() {
        return this.animationUriString;
    }

    @Override // com.facebook.cache.common.CacheKey
    public boolean equals(Object o) {
        if (!this.deepEquals) {
            return super.equals(o);
        }
        if (this == o) {
            return true;
        }
        if (o == null || !Intrinsics.areEqual(getClass(), o.getClass())) {
            return false;
        }
        return Intrinsics.areEqual(this.animationUriString, ((AnimationFrameCacheKey) o).animationUriString);
    }

    @Override // com.facebook.cache.common.CacheKey
    public int hashCode() {
        if (!this.deepEquals) {
            return super.hashCode();
        }
        return this.animationUriString.hashCode();
    }
}
