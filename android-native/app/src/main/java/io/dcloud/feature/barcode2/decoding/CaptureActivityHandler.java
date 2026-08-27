package io.dcloud.feature.barcode2.decoding;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.dcloud.zxing2.BarcodeFormat;
import com.dcloud.zxing2.Result;
import com.dcloud.zxing2.ResultPoint;
import com.dcloud.zxing2.ResultPointCallback;
import io.dcloud.feature.barcode2.camera.CameraManager;
import io.dcloud.feature.barcode2.view.ViewfinderResultPointCallback;
import java.util.ArrayList;
import java.util.Vector;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public final class CaptureActivityHandler extends Handler {
    public static final int CODE_AUTO_FOCUS = 1000;
    public static final int CODE_DECODE = 1004;
    public static final int CODE_DECODE_FAILED = 1001;
    public static final int CODE_DECODE_NEED_ZOOM = 1010;
    public static final int CODE_DECODE_SUCCEEDED = 1002;
    public static final int CODE_DECODE_landscape = 1006;
    public static final int CODE_DECODE_portrait = 1005;
    public static final int CODE_QUIT = 1003;
    private static final String TAG = "CaptureActivityHandler";
    public static boolean isAutoZoom = true;
    private final IBarHandler activity;
    private final DecodeThread decodeThread;
    private State state;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    private enum State {
        PREVIEW,
        SUCCESS,
        DONE
    }

    public CaptureActivityHandler(IBarHandler iBarHandler, Vector<BarcodeFormat> vector, String str, boolean z) {
        this.activity = iBarHandler;
        DecodeThread decodeThread = new DecodeThread(iBarHandler, vector, str, new ViewfinderResultPointCallback(iBarHandler.getViewfinderView()), z);
        this.decodeThread = decodeThread;
        decodeThread.start();
        this.state = State.SUCCESS;
        resume();
    }

    public static Bitmap convertToBMW(Bitmap bitmap, int i, int i2, int i3) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        for (int i4 = 0; i4 < height; i4++) {
            for (int i5 = 0; i5 < width; i5++) {
                int i6 = (width * i4) + i5;
                int i7 = iArr[i6];
                int i8 = (i7 & (-16777216)) >> 24;
                int i9 = (16711680 & i7) >> 16;
                int i10 = (65280 & i7) >> 8;
                int i11 = ((i7 & 255) > i3 ? 255 : 0) | (i8 << 24) | ((i9 > i3 ? 255 : 0) << 16) | ((i10 <= i3 ? 0 : 255) << 8);
                iArr[i6] = i11;
                if (i11 == -1) {
                    iArr[i6] = -1;
                } else {
                    iArr[i6] = -16777216;
                }
            }
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmapCreateBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
        return ThumbnailUtils.extractThumbnail(bitmapCreateBitmap, i, i2);
    }

    public static Result decode(Bitmap bitmap, boolean z) {
        Result resultDecode = DecodeHandler.decode(bitmap, z);
        if (resultDecode != null) {
            return resultDecode;
        }
        Result resultTrySmallerBitmap = trySmallerBitmap(bitmap, true, z);
        return resultTrySmallerBitmap != null ? resultTrySmallerBitmap : tryBiggerBitmap(bitmap, z);
    }

    public static Result tryBiggerBitmap(Bitmap bitmap, boolean z) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width < 1200 && height < 1200) {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width + 200, height + 200, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            canvas.drawColor(-1);
            canvas.drawBitmap(bitmap, 100.0f, 100.0f, new Paint());
            bitmap = bitmapCreateBitmap;
        }
        return DecodeHandler.decode(convertToBMW(bitmap, bitmap.getWidth(), bitmap.getHeight(), 180), z);
    }

    public static Result trySmallerBitmap(Bitmap bitmap, boolean z, boolean z2) {
        int size;
        Bitmap bitmapCreateBitmap;
        int size2;
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width >= 200 && height >= 200) {
            boolean z3 = width <= height;
            Bitmap bitmapCreateBitmap2 = z3 ? Bitmap.createBitmap(bitmap, 0, 0, width, height / 2) : Bitmap.createBitmap(bitmap, 0, 0, width / 2, height);
            final ArrayList arrayList = new ArrayList();
            Result resultDecode = z ? DecodeHandler.decode(bitmapCreateBitmap2, new ResultPointCallback() { // from class: io.dcloud.feature.barcode2.decoding.CaptureActivityHandler.1
                @Override // com.dcloud.zxing2.ResultPointCallback
                public void foundPossibleResultPoint(ResultPoint resultPoint) {
                    arrayList.add(resultPoint);
                }
            }, z2) : DecodeHandler.decode(bitmapCreateBitmap2, z2);
            if (resultDecode != null) {
                return resultDecode;
            }
            if (z) {
                size = arrayList.size();
                arrayList.clear();
            } else {
                size = 0;
            }
            if (z3) {
                int i = height / 2;
                bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, i, width, i);
            } else {
                int i2 = width / 2;
                bitmapCreateBitmap = Bitmap.createBitmap(bitmap, i2, 0, i2, height);
            }
            Result resultDecode2 = z ? DecodeHandler.decode(bitmapCreateBitmap, new ResultPointCallback() { // from class: io.dcloud.feature.barcode2.decoding.CaptureActivityHandler.2
                @Override // com.dcloud.zxing2.ResultPointCallback
                public void foundPossibleResultPoint(ResultPoint resultPoint) {
                    arrayList.add(resultPoint);
                }
            }, z2) : DecodeHandler.decode(bitmapCreateBitmap, z2);
            if (resultDecode2 != null) {
                return resultDecode2;
            }
            if (z) {
                size2 = arrayList.size();
                arrayList.clear();
            } else {
                size2 = 0;
            }
            Bitmap bitmapCreateBitmap3 = z3 ? Bitmap.createBitmap(bitmap, 0, height / 8, width, (height / 4) * 3) : Bitmap.createBitmap(bitmap, width / 8, 0, (width / 4) * 3, height);
            Result resultDecode3 = z ? DecodeHandler.decode(bitmapCreateBitmap3, new ResultPointCallback() { // from class: io.dcloud.feature.barcode2.decoding.CaptureActivityHandler.3
                @Override // com.dcloud.zxing2.ResultPointCallback
                public void foundPossibleResultPoint(ResultPoint resultPoint) {
                    arrayList.add(resultPoint);
                }
            }, z2) : DecodeHandler.decode(bitmapCreateBitmap3, z2);
            if (resultDecode3 != null) {
                return resultDecode3;
            }
            if (z) {
                int size3 = arrayList.size();
                arrayList.clear();
                if (size > size3 && size > size2) {
                    return trySmallerBitmap(bitmapCreateBitmap2, false, z2);
                }
                if (size3 > size2 && size3 > size) {
                    return trySmallerBitmap(bitmapCreateBitmap3, false, z2);
                }
                if (size2 > size3 && size2 > size) {
                    return trySmallerBitmap(bitmapCreateBitmap, false, z2);
                }
            }
        }
        return null;
    }

    public void autoFocus() {
        CameraManager.get().requestAutoFocus(this, 1000);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        switch (message.what) {
            case 1000:
                Log.d(TAG, "Got auto-focus message");
                if (this.state == State.PREVIEW) {
                    CameraManager.get().requestAutoFocus(this, 1000);
                    break;
                }
                break;
            case 1001:
                Log.d(TAG, "CODE_DECODE_FAILED");
                this.state = State.PREVIEW;
                CameraManager.get().requestPreviewFrame(this.activity, this.decodeThread.getHandler(), 1004);
                break;
            case 1002:
                Log.d(TAG, "Got decode succeeded message");
                this.state = State.SUCCESS;
                Bundle data = message.getData();
                Bitmap bitmap = data == null ? null : (Bitmap) data.getParcelable(DecodeThread.BARCODE_BITMAP);
                this.activity.handleDecode((Result) message.obj, bitmap);
                if (bitmap != null) {
                    bitmap.recycle();
                    System.out.println("barcode.recycle");
                    break;
                }
                break;
        }
    }

    public void quitSynchronously() throws InterruptedException {
        this.state = State.DONE;
        CameraManager.get().stopPreview();
        Message.obtain(this.decodeThread.getHandler(), 1003).sendToTarget();
        try {
            this.decodeThread.join();
        } catch (InterruptedException unused) {
        }
        stopDecode();
    }

    public void restartPreviewAndDecode() {
        if (this.state == State.SUCCESS) {
            this.state = State.PREVIEW;
            CameraManager.get().requestPreviewFrame(this.activity, this.decodeThread.getHandler(), 1004);
            autoFocus();
        }
    }

    public void resume() {
        CameraManager.get().startPreview();
        this.activity.drawViewfinder();
        restartPreviewAndDecode();
    }

    public void stopDecode() {
        removeMessages(1002);
        removeMessages(1001);
        CameraManager.get().removeAutoFocus();
        this.state = State.SUCCESS;
    }
}
