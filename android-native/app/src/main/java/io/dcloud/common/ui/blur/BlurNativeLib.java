package io.dcloud.common.ui.blur;

import android.graphics.Bitmap;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class BlurNativeLib {
    static {
        System.loadLibrary("dcblur");
    }

    public static native void blurBitmap(Bitmap bitmap, int i, int i2, int i3, int i4);
}
