package io.dcloud.feature.barcode2.decoding;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.dcloud.zxing2.BinaryBitmap;
import com.dcloud.zxing2.DecodeHintType;
import com.dcloud.zxing2.MultiFormatReader;
import com.dcloud.zxing2.NotFoundException;
import com.dcloud.zxing2.ReaderException;
import com.dcloud.zxing2.Result;
import com.dcloud.zxing2.ResultPointCallback;
import com.dcloud.zxing2.common.GlobalHistogramBinarizer;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.feature.barcode2.BarcodeProxy;
import io.dcloud.feature.barcode2.camera.CameraManager;
import io.dcloud.feature.barcode2.camera.PlanarYUVLuminanceSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
final class DecodeHandler extends Handler {
    private static final String TAG = "DecodeHandler";
    private static boolean mIsVerticalScreen = true;
    private final IBarHandler activity;
    private final MultiFormatReader multiFormatReader;

    DecodeHandler(IBarHandler iBarHandler, Hashtable<DecodeHintType, Object> hashtable) {
        MultiFormatReader multiFormatReader = new MultiFormatReader(this);
        this.multiFormatReader = multiFormatReader;
        multiFormatReader.setHints(hashtable);
        this.activity = iBarHandler;
    }

    private void decode(byte[] bArr, int i, int i2) throws IOException {
        Result resultDecodeWithState;
        PlanarYUVLuminanceSource planarYUVLuminanceSourceBuildLuminanceSource = CameraManager.get().buildLuminanceSource(bArr, i, i2);
        try {
            resultDecodeWithState = this.multiFormatReader.decodeWithState(new BinaryBitmap(new GlobalHistogramBinarizer(planarYUVLuminanceSourceBuildLuminanceSource)));
            this.multiFormatReader.reset();
        } catch (ReaderException unused) {
            this.multiFormatReader.reset();
            resultDecodeWithState = null;
        } catch (Throwable th) {
            this.multiFormatReader.reset();
            throw th;
        }
        Result result = resultDecodeWithState;
        if (BarcodeProxy.save) {
            Camera.Parameters parameters = CameraManager.get().getCameraHandler().getParameters();
            try {
                Camera.Size previewSize = parameters.getPreviewSize();
                YuvImage yuvImage = new YuvImage(bArr, parameters.getPreviewFormat(), previewSize.width, previewSize.height, null);
                yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), 90, new FileOutputStream(new File("/sdcard/1/" + System.currentTimeMillis() + "--" + previewSize.width + "*" + previewSize.height + ".jpg")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmapRenderCroppedGreyscaleBitmap = planarYUVLuminanceSourceBuildLuminanceSource.renderCroppedGreyscaleBitmap(true);
            Rect framingRectInPreview = CameraManager.get().getFramingRectInPreview();
            PdrUtil.saveBitmapToFile(bitmapRenderCroppedGreyscaleBitmap, "/sdcard/1/" + System.currentTimeMillis() + "--" + framingRectInPreview.left + "*" + framingRectInPreview.top + ".png");
            BarcodeProxy.save = false;
            Activity activity = (Activity) BarcodeProxy.context;
            StringBuilder sb = new StringBuilder("成功 left=");
            sb.append(framingRectInPreview.left);
            sb.append("top:");
            sb.append(framingRectInPreview.top);
            PdrUtil.alert(activity, sb.toString(), bitmapRenderCroppedGreyscaleBitmap);
        }
        if (result == null) {
            Message.obtain(this.activity.getHandler(), 1001).sendToTarget();
            return;
        }
        Message messageObtain = Message.obtain(this.activity.getHandler(), 1002, result);
        Bundle bundle = new Bundle();
        Bitmap bitmapRenderCroppedGreyscaleBitmap2 = planarYUVLuminanceSourceBuildLuminanceSource.renderCroppedGreyscaleBitmap(true);
        if (!mIsVerticalScreen) {
            Matrix matrix = new Matrix();
            matrix.postRotate(-90.0f);
            Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapRenderCroppedGreyscaleBitmap2, bitmapRenderCroppedGreyscaleBitmap2.getWidth(), bitmapRenderCroppedGreyscaleBitmap2.getHeight(), true);
            bitmapRenderCroppedGreyscaleBitmap2 = Bitmap.createBitmap(bitmapCreateScaledBitmap, 0, 0, bitmapCreateScaledBitmap.getWidth(), bitmapCreateScaledBitmap.getHeight(), matrix, true);
        }
        bundle.putParcelable(DecodeThread.BARCODE_BITMAP, bitmapRenderCroppedGreyscaleBitmap2);
        messageObtain.setData(bundle);
        messageObtain.sendToTarget();
    }

    private void handleNeedZoom() {
        Camera.Parameters parameters = CameraManager.get().getCameraHandler().getParameters();
        int zoom = parameters.getZoom();
        int maxZoom = (int) (parameters.getMaxZoom() * 0.6d);
        int i = (int) (maxZoom * 0.2d);
        if (zoom < maxZoom) {
            zoom += i;
        }
        if (zoom <= maxZoom) {
            maxZoom = zoom;
        }
        parameters.setZoom(maxZoom);
        CameraManager.get().getCameraHandler().setParameters(parameters);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) throws IOException {
        int i = message.what;
        if (i == 1010) {
            if (CaptureActivityHandler.isAutoZoom) {
                handleNeedZoom();
                return;
            }
            return;
        }
        switch (i) {
            case 1003:
                Looper.myLooper().quit();
                break;
            case 1004:
                decode((byte[]) message.obj, message.arg1, message.arg2);
                break;
            case CaptureActivityHandler.CODE_DECODE_portrait /* 1005 */:
                mIsVerticalScreen = true;
                decode((byte[]) message.obj, message.arg1, message.arg2);
                break;
            case 1006:
                mIsVerticalScreen = false;
                decode((byte[]) message.obj, message.arg1, message.arg2);
                break;
        }
    }

    public static Result decode(Bitmap bitmap, ResultPointCallback resultPointCallback, boolean z) {
        MultiFormatReader multiFormatReader = new MultiFormatReader(null);
        Hashtable hashtable = new Hashtable(4);
        Vector vector = new Vector();
        if (vector.isEmpty()) {
            vector = new Vector();
            vector.addAll(DecodeFormatManager.ONE_D_FORMATS);
            vector.addAll(DecodeFormatManager.QR_CODE_FORMATS);
            vector.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
        }
        hashtable.put(DecodeHintType.POSSIBLE_FORMATS, vector);
        DecodeHintType decodeHintType = DecodeHintType.TRY_HARDER;
        Boolean bool = Boolean.TRUE;
        hashtable.put(decodeHintType, bool);
        if (resultPointCallback != null) {
            hashtable.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, resultPointCallback);
        }
        if (z) {
            hashtable.put(DecodeHintType.autoDecodeCharset, bool);
        }
        multiFormatReader.setHints(hashtable);
        try {
            return multiFormatReader.decodeWithState(new BinaryBitmap(new GlobalHistogramBinarizer(new BitmapLuminanceSource(bitmap))));
        } catch (NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Result decode(Bitmap bitmap, boolean z) {
        return decode(bitmap, (ResultPointCallback) null, z);
    }
}
