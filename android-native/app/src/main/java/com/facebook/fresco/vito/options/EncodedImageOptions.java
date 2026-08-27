package com.facebook.fresco.vito.options;

import com.facebook.common.internal.Objects;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import io.dcloud.common.constant.AbsoluteConst;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: EncodedImageOptions.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001:\u0001\u001bB\u0013\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\u0010\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0000H\u0004J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u000fH\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0014R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001c"}, d2 = {"Lcom/facebook/fresco/vito/options/EncodedImageOptions;", "", "builder", "Lcom/facebook/fresco/vito/options/EncodedImageOptions$Builder;", "<init>", "(Lcom/facebook/fresco/vito/options/EncodedImageOptions$Builder;)V", "priority", "Lcom/facebook/imagepipeline/common/Priority;", "getPriority", "()Lcom/facebook/imagepipeline/common/Priority;", "cacheChoice", "Lcom/facebook/imagepipeline/request/ImageRequest$CacheChoice;", "getCacheChoice", "()Lcom/facebook/imagepipeline/request/ImageRequest$CacheChoice;", "diskCacheId", "", "getDiskCacheId", "()Ljava/lang/String;", "equals", "", "other", "equalEncodedOptions", "hashCode", "", "toString", "toStringHelper", "Lcom/facebook/common/internal/Objects$ToStringHelper;", "Builder", "options_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes.dex */
public class EncodedImageOptions {
    private final ImageRequest.CacheChoice cacheChoice;
    private final String diskCacheId;
    private final Priority priority;

    public EncodedImageOptions(Builder<?> builder) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        this.priority = builder.getPriority();
        this.cacheChoice = builder.getCacheChoice();
        String diskCacheId = builder.getDiskCacheId();
        this.diskCacheId = diskCacheId;
        if (builder.getCacheChoice() == ImageRequest.CacheChoice.DYNAMIC) {
            if (diskCacheId == null) {
                throw new ImageRequestBuilder.BuilderException("Disk cache id must be set for dynamic cache choice");
            }
        } else {
            String str = diskCacheId;
            if (str != null && str.length() != 0) {
                throw new ImageRequestBuilder.BuilderException("Ensure that if you want to use a disk cache id, you set the CacheChoice to DYNAMIC");
            }
        }
    }

    public final Priority getPriority() {
        return this.priority;
    }

    public final ImageRequest.CacheChoice getCacheChoice() {
        return this.cacheChoice;
    }

    public final String getDiskCacheId() {
        return this.diskCacheId;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !Intrinsics.areEqual(getClass(), other.getClass())) {
            return false;
        }
        return equalEncodedOptions((EncodedImageOptions) other);
    }

    protected final boolean equalEncodedOptions(EncodedImageOptions other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return Objects.equal(this.priority, other.priority) && Objects.equal(this.cacheChoice, other.cacheChoice) && Objects.equal(this.diskCacheId, other.diskCacheId);
    }

    public int hashCode() {
        Priority priority = this.priority;
        int iHashCode = (priority != null ? priority.hashCode() : 0) * 31;
        ImageRequest.CacheChoice cacheChoice = this.cacheChoice;
        int iHashCode2 = (iHashCode + (cacheChoice != null ? cacheChoice.hashCode() : 0)) * 31;
        String str = this.diskCacheId;
        return iHashCode2 + (str != null ? str.hashCode() : 0);
    }

    public String toString() {
        String string = toStringHelper().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    protected Objects.ToStringHelper toStringHelper() {
        Objects.ToStringHelper toStringHelperAdd = Objects.toStringHelper(this).add("priority", this.priority).add("cacheChoice", this.cacheChoice).add("diskCacheId", this.diskCacheId);
        Intrinsics.checkNotNullExpressionValue(toStringHelperAdd, "add(...)");
        return toStringHelperAdd;
    }

    /* compiled from: EncodedImageOptions.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00002\u00020\u0002B\t\b\u0014¢\u0006\u0004\b\u0003\u0010\u0004B\u0011\b\u0014\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0003\u0010\u0007J\u0015\u0010\b\u001a\u00028\u00002\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\u001aJ\u0015\u0010\u000e\u001a\u00028\u00002\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\u0002\u0010\u001bJ\u0015\u0010\u0014\u001a\u00028\u00002\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015¢\u0006\u0002\u0010\u001cJ\b\u0010\u001d\u001a\u00020\u0006H\u0016J\r\u0010\u001e\u001a\u00028\u0000H\u0004¢\u0006\u0002\u0010\u001fJ-\u0010 \u001a\u00028\u00002\u001d\u0010!\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0000\u0012\u0004\u0012\u00020#0\"¢\u0006\u0002\b$H\u0082\b¢\u0006\u0002\u0010%R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019¨\u0006&"}, d2 = {"Lcom/facebook/fresco/vito/options/EncodedImageOptions$Builder;", "T", "", "<init>", "()V", "defaultOptions", "Lcom/facebook/fresco/vito/options/EncodedImageOptions;", "(Lcom/facebook/fresco/vito/options/EncodedImageOptions;)V", "priority", "Lcom/facebook/imagepipeline/common/Priority;", "getPriority$options_release", "()Lcom/facebook/imagepipeline/common/Priority;", "setPriority$options_release", "(Lcom/facebook/imagepipeline/common/Priority;)V", "cacheChoice", "Lcom/facebook/imagepipeline/request/ImageRequest$CacheChoice;", "getCacheChoice$options_release", "()Lcom/facebook/imagepipeline/request/ImageRequest$CacheChoice;", "setCacheChoice$options_release", "(Lcom/facebook/imagepipeline/request/ImageRequest$CacheChoice;)V", "diskCacheId", "", "getDiskCacheId$options_release", "()Ljava/lang/String;", "setDiskCacheId$options_release", "(Ljava/lang/String;)V", "(Lcom/facebook/imagepipeline/common/Priority;)Lcom/facebook/fresco/vito/options/EncodedImageOptions$Builder;", "(Lcom/facebook/imagepipeline/request/ImageRequest$CacheChoice;)Lcom/facebook/fresco/vito/options/EncodedImageOptions$Builder;", "(Ljava/lang/String;)Lcom/facebook/fresco/vito/options/EncodedImageOptions$Builder;", "build", "getThis", "()Lcom/facebook/fresco/vito/options/EncodedImageOptions$Builder;", "modify", AbsoluteConst.JSON_VALUE_BLOCK, "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function1;)Lcom/facebook/fresco/vito/options/EncodedImageOptions$Builder;", "options_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
    public static class Builder<T extends Builder<T>> {
        private ImageRequest.CacheChoice cacheChoice;
        private String diskCacheId;
        private Priority priority;

        /* renamed from: getPriority$options_release, reason: from getter */
        public final Priority getPriority() {
            return this.priority;
        }

        public final void setPriority$options_release(Priority priority) {
            this.priority = priority;
        }

        /* renamed from: getCacheChoice$options_release, reason: from getter */
        public final ImageRequest.CacheChoice getCacheChoice() {
            return this.cacheChoice;
        }

        public final void setCacheChoice$options_release(ImageRequest.CacheChoice cacheChoice) {
            this.cacheChoice = cacheChoice;
        }

        /* renamed from: getDiskCacheId$options_release, reason: from getter */
        public final String getDiskCacheId() {
            return this.diskCacheId;
        }

        public final void setDiskCacheId$options_release(String str) {
            this.diskCacheId = str;
        }

        protected Builder() {
        }

        protected Builder(EncodedImageOptions defaultOptions) {
            Intrinsics.checkNotNullParameter(defaultOptions, "defaultOptions");
            this.priority = defaultOptions.getPriority();
            this.cacheChoice = defaultOptions.getCacheChoice();
            this.diskCacheId = defaultOptions.getDiskCacheId();
        }

        public EncodedImageOptions build() {
            return new EncodedImageOptions(this);
        }

        protected final T getThis() {
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type T of com.facebook.fresco.vito.options.EncodedImageOptions.Builder");
            return this;
        }

        private final T modify(Function1<? super Builder<T>, Unit> block) {
            block.invoke(this);
            return (T) getThis();
        }

        public final T priority(Priority priority) {
            this.priority = priority;
            return (T) getThis();
        }

        public final T cacheChoice(ImageRequest.CacheChoice cacheChoice) {
            this.cacheChoice = cacheChoice;
            return (T) getThis();
        }

        public final T diskCacheId(String diskCacheId) {
            this.diskCacheId = diskCacheId;
            return (T) getThis();
        }
    }
}
