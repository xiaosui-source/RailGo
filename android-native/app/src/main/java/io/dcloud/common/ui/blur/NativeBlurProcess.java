package io.dcloud.common.ui.blur;

import android.graphics.Bitmap;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class NativeBlurProcess {
    final ExecutorService EXECUTOR;
    final int EXECUTOR_THREADS;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    private static class NativeTask implements Callable<Void> {
        private final Bitmap _bitmapOut;
        private final int _coreIndex;
        private final int _radius;
        private final int _round;
        private final int _totalCores;

        public NativeTask(Bitmap bitmap, int i, int i2, int i3, int i4) {
            this._bitmapOut = bitmap;
            this._radius = i;
            this._totalCores = i2;
            this._coreIndex = i3;
            this._round = i4;
        }

        @Override // java.util.concurrent.Callable
        public Void call() throws Exception {
            BlurNativeLib.blurBitmap(this._bitmapOut, this._radius, this._totalCores, this._coreIndex, this._round);
            return null;
        }
    }

    public NativeBlurProcess() {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        this.EXECUTOR_THREADS = iAvailableProcessors;
        this.EXECUTOR = Executors.newFixedThreadPool(iAvailableProcessors);
    }

    public Bitmap blur(Bitmap bitmap, float f, boolean z) throws InterruptedException {
        if (z) {
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        }
        Bitmap bitmap2 = bitmap;
        int i = this.EXECUTOR_THREADS;
        ArrayList arrayList = new ArrayList(i);
        ArrayList arrayList2 = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = (int) f;
            arrayList.add(new NativeTask(bitmap2, i3, i, i2, 1));
            arrayList2.add(new NativeTask(bitmap2, i3, i, i2, 2));
        }
        try {
            this.EXECUTOR.invokeAll(arrayList);
            this.EXECUTOR.invokeAll(arrayList2);
        } catch (InterruptedException unused) {
        }
        return bitmap2;
    }
}
