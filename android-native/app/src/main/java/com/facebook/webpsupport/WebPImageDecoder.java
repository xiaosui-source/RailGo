package com.facebook.webpsupport;

import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.SimpleBitmapReleaser;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.decoder.ImageDecoder;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.image.QualityInfo;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public class WebPImageDecoder implements ImageDecoder {
    @Override // com.facebook.imagepipeline.decoder.ImageDecoder
    @Nullable
    public CloseableImage decode(EncodedImage encodedImage, int i, QualityInfo qualityInfo, ImageDecodeOptions imageDecodeOptions) {
        CloseableReference<PooledByteBuffer> byteBufferRef = encodedImage.getByteBufferRef();
        Preconditions.checkNotNull(byteBufferRef);
        try {
            ByteBuffer byteBuffer = byteBufferRef.get().getByteBuffer();
            if (byteBuffer != null) {
                return decodeByteBuffer(byteBuffer, encodedImage, i, qualityInfo, imageDecodeOptions);
            }
            CloseableReference.closeSafely(byteBufferRef);
            return decodeInputStream(encodedImage, i, qualityInfo, imageDecodeOptions);
        } finally {
            CloseableReference.closeSafely(byteBufferRef);
        }
    }

    @Nullable
    private static CloseableImage decodeByteBuffer(ByteBuffer byteBuffer, EncodedImage encodedImage, int i, QualityInfo qualityInfo, ImageDecodeOptions imageDecodeOptions) {
        return bitmapToCloseableImage(WebpBitmapFactoryImpl.hookDecodeByteArray(byteBuffer.array(), 0, i), encodedImage, qualityInfo);
    }

    @Nullable
    private static CloseableImage decodeInputStream(EncodedImage encodedImage, int i, QualityInfo qualityInfo, ImageDecodeOptions imageDecodeOptions) throws IOException {
        try {
            InputStream inputStreamOrThrow = encodedImage.getInputStreamOrThrow();
            try {
                CloseableImage closeableImageBitmapToCloseableImage = bitmapToCloseableImage(WebpBitmapFactoryImpl.hookDecodeStream(inputStreamOrThrow, null, null), encodedImage, qualityInfo);
                if (inputStreamOrThrow != null) {
                    inputStreamOrThrow.close();
                }
                return closeableImageBitmapToCloseableImage;
            } finally {
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while decoding WebP", e);
        }
    }

    @Nullable
    private static CloseableImage bitmapToCloseableImage(@Nullable Bitmap bitmap, EncodedImage encodedImage, QualityInfo qualityInfo) {
        if (bitmap == null) {
            return null;
        }
        return CloseableStaticBitmap.CC.of(CloseableReference.of(bitmap, SimpleBitmapReleaser.getInstance()), qualityInfo, encodedImage.getRotationAngle());
    }
}
