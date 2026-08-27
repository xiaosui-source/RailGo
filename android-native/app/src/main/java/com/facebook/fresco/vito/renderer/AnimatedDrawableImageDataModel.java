package com.facebook.fresco.vito.renderer;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ImageDataModel.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u000eH\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\f¨\u0006\u0010"}, d2 = {"Lcom/facebook/fresco/vito/renderer/AnimatedDrawableImageDataModel;", "Lcom/facebook/fresco/vito/renderer/DrawableImageDataModel;", "drawable", "Landroid/graphics/drawable/Drawable;", "animatable", "Landroid/graphics/drawable/Animatable;", "isAutoPlay", "", "<init>", "(Landroid/graphics/drawable/Drawable;Landroid/graphics/drawable/Animatable;Z)V", "getAnimatable", "()Landroid/graphics/drawable/Animatable;", "()Z", "onAttach", "", "onDetach", "renderer_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes.dex */
public final class AnimatedDrawableImageDataModel extends DrawableImageDataModel {
    private final Animatable animatable;
    private final boolean isAutoPlay;

    public final Animatable getAnimatable() {
        return this.animatable;
    }

    /* renamed from: isAutoPlay, reason: from getter */
    public final boolean getIsAutoPlay() {
        return this.isAutoPlay;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnimatedDrawableImageDataModel(Drawable drawable, Animatable animatable, boolean z) {
        super(drawable);
        Intrinsics.checkNotNullParameter(drawable, "drawable");
        Intrinsics.checkNotNullParameter(animatable, "animatable");
        this.animatable = animatable;
        this.isAutoPlay = z;
    }

    @Override // com.facebook.fresco.vito.renderer.ImageDataModel
    public void onAttach() {
        if (this.isAutoPlay) {
            this.animatable.start();
        }
    }

    @Override // com.facebook.fresco.vito.renderer.ImageDataModel
    public void onDetach() {
        if (this.isAutoPlay) {
            this.animatable.stop();
        }
    }
}
