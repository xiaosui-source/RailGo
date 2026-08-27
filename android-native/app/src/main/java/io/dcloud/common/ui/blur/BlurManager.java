package io.dcloud.common.ui.blur;

import android.graphics.Bitmap;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class BlurManager {
    private static BlurManager sInstance;
    private NativeBlurProcess nativeBlurProcess;

    private BlurManager() {
        if (this.nativeBlurProcess == null) {
            this.nativeBlurProcess = new NativeBlurProcess();
        }
    }

    public static BlurManager getInstance() {
        if (sInstance == null) {
            sInstance = new BlurManager();
        }
        return sInstance;
    }

    public Bitmap processNatively(Bitmap bitmap, int i, boolean z) {
        if (this.nativeBlurProcess == null) {
            this.nativeBlurProcess = new NativeBlurProcess();
        }
        if (bitmap == null) {
            return null;
        }
        return this.nativeBlurProcess.blur(bitmap, i, z);
    }
}
