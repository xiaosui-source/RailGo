package io.dcloud.feature.barcode2.decoding;

import android.graphics.Bitmap;
import com.dcloud.zxing2.LuminanceSource;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class BitmapLuminanceSource extends LuminanceSource {
    private byte[] bitmapPixels;

    protected BitmapLuminanceSource(Bitmap bitmap) {
        super(bitmap.getWidth(), bitmap.getHeight());
        int width = bitmap.getWidth() * bitmap.getHeight();
        int[] iArr = new int[width];
        this.bitmapPixels = new byte[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(iArr, 0, getWidth(), 0, 0, getWidth(), getHeight());
        for (int i = 0; i < width; i++) {
            this.bitmapPixels[i] = (byte) iArr[i];
        }
    }

    @Override // com.dcloud.zxing2.LuminanceSource
    public byte[] getMatrix() {
        return this.bitmapPixels;
    }

    @Override // com.dcloud.zxing2.LuminanceSource
    public byte[] getRow(int i, byte[] bArr) {
        System.arraycopy(this.bitmapPixels, i * getWidth(), bArr, 0, getWidth());
        return bArr;
    }
}
