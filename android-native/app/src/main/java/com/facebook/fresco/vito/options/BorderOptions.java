package com.facebook.fresco.vito.options;

import com.taobao.weex.el.parse.Operators;
import kotlin.Metadata;
import kotlin.UByte$$ExternalSyntheticBackport0;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BorderOptions.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B-\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nJ\u0013\u0010\u000b\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\r\u001a\u00020\u0003H\u0016J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\bHÆ\u0003J1\u0010\u0012\u001a\u00020\u00002\b\b\u0003\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/facebook/fresco/vito/options/BorderOptions;", "", "color", "", "width", "", "padding", "scaleDownInsideBorders", "", "<init>", "(IFFZ)V", "equals", "other", "hashCode", "component1", "component2", "component3", "component4", "copy", "toString", "", "Companion", "options_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes.dex */
public final /* data */ class BorderOptions {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public final int color;
    public final float padding;
    public final boolean scaleDownInsideBorders;
    public final float width;

    public static /* synthetic */ BorderOptions copy$default(BorderOptions borderOptions, int i, float f, float f2, boolean z, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = borderOptions.color;
        }
        if ((i2 & 2) != 0) {
            f = borderOptions.width;
        }
        if ((i2 & 4) != 0) {
            f2 = borderOptions.padding;
        }
        if ((i2 & 8) != 0) {
            z = borderOptions.scaleDownInsideBorders;
        }
        return borderOptions.copy(i, f, f2, z);
    }

    @JvmStatic
    public static final BorderOptions create(int i, float f) {
        return INSTANCE.create(i, f);
    }

    @JvmStatic
    public static final BorderOptions create(int i, float f, float f2) {
        return INSTANCE.create(i, f, f2);
    }

    @JvmStatic
    public static final BorderOptions create(int i, float f, float f2, boolean z) {
        return INSTANCE.create(i, f, f2, z);
    }

    /* renamed from: component1, reason: from getter */
    public final int getColor() {
        return this.color;
    }

    /* renamed from: component2, reason: from getter */
    public final float getWidth() {
        return this.width;
    }

    /* renamed from: component3, reason: from getter */
    public final float getPadding() {
        return this.padding;
    }

    /* renamed from: component4, reason: from getter */
    public final boolean getScaleDownInsideBorders() {
        return this.scaleDownInsideBorders;
    }

    public final BorderOptions copy(int color, float width, float padding, boolean scaleDownInsideBorders) {
        return new BorderOptions(color, width, padding, scaleDownInsideBorders);
    }

    public String toString() {
        return "BorderOptions(color=" + this.color + ", width=" + this.width + ", padding=" + this.padding + ", scaleDownInsideBorders=" + this.scaleDownInsideBorders + Operators.BRACKET_END_STR;
    }

    public BorderOptions(int i, float f, float f2, boolean z) {
        this.color = i;
        this.width = f;
        this.padding = f2;
        this.scaleDownInsideBorders = z;
    }

    public /* synthetic */ BorderOptions(int i, float f, float f2, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, f, (i2 & 4) != 0 ? 0.0f : f2, (i2 & 8) != 0 ? false : z);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type com.facebook.fresco.vito.options.BorderOptions");
        BorderOptions borderOptions = (BorderOptions) other;
        return this.color == borderOptions.color && this.width == borderOptions.width && this.padding == borderOptions.padding && this.scaleDownInsideBorders == borderOptions.scaleDownInsideBorders;
    }

    public int hashCode() {
        return (((((this.color * 31) + Float.floatToIntBits(this.width)) * 31) + Float.floatToIntBits(this.padding)) * 31) + UByte$$ExternalSyntheticBackport0.m(this.scaleDownInsideBorders);
    }

    /* compiled from: BorderOptions.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J.\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0007¨\u0006\r"}, d2 = {"Lcom/facebook/fresco/vito/options/BorderOptions$Companion;", "", "<init>", "()V", "create", "Lcom/facebook/fresco/vito/options/BorderOptions;", "color", "", "width", "", "padding", "scaleDownInsideBorders", "", "options_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final BorderOptions create(int i, float f) {
            return create$default(this, i, f, 0.0f, false, 12, null);
        }

        @JvmStatic
        public final BorderOptions create(int i, float f, float f2) {
            return create$default(this, i, f, f2, false, 8, null);
        }

        private Companion() {
        }

        public static /* synthetic */ BorderOptions create$default(Companion companion, int i, float f, float f2, boolean z, int i2, Object obj) {
            if ((i2 & 4) != 0) {
                f2 = 0.0f;
            }
            if ((i2 & 8) != 0) {
                z = false;
            }
            return companion.create(i, f, f2, z);
        }

        @JvmStatic
        public final BorderOptions create(int color, float width, float padding, boolean scaleDownInsideBorders) {
            return new BorderOptions(color, width, padding, scaleDownInsideBorders);
        }
    }
}
