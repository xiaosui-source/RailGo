package io.dcloud.feature.weex_barcode;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.YuvImage;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.TextureView;
import android.widget.AbsoluteLayout;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.dcloud.zxing2.BarcodeFormat;
import com.dcloud.zxing2.Result;
import com.hjq.permissions.Permission;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXImage;
import io.dcloud.base.R;
import io.dcloud.common.adapter.util.PermissionUtil;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.common.util.ThreadPool;
import io.dcloud.feature.barcode2.camera.CameraManager;
import io.dcloud.feature.barcode2.decoding.CaptureActivityHandler;
import io.dcloud.feature.barcode2.decoding.IBarHandler;
import io.dcloud.feature.barcode2.decoding.InactivityTimer;
import io.dcloud.feature.barcode2.view.DetectorViewConfig;
import io.dcloud.feature.barcode2.view.ViewfinderView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class BarcodeView extends AbsoluteLayout implements IBarHandler, TextureView.SurfaceTextureListener {
    static final int AZTEC = 3;
    private static final float BEEP_VOLUME = 0.8f;
    static final int CODABAR = 7;
    static final int CODE128 = 10;
    static final int CODE39 = 8;
    static final int CODE93 = 9;
    static final int DATAMATRIX = 4;
    static final int EAN13 = 1;
    static final int EAN8 = 2;
    private static final int ID_ADD_VIEW = 201;
    private static final int ID_START_SCAN = 203;
    private static final int ID_UPDATE_VIEW = 202;
    static final int ITF = 11;
    static final int MAXICODE = 12;
    static final int PDF417 = 13;
    static final int QR = 0;
    static final int RSS14 = 14;
    static final int RSSEXPANDED = 15;
    static final int UNKOWN = -1000;
    static final int UPCA = 5;
    static final int UPCE = 6;
    private static final long VIBRATE_DURATION = 200;
    public boolean autoDecodeCharset;
    private final MediaPlayer.OnCompletionListener beepListener;
    private String characterSet;
    private WXComponent component;
    private Context context;
    private Vector<BarcodeFormat> decodeFormats;
    public String errorMsg;
    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private boolean isCancelScan;
    private boolean isSurfaceAvaliable;
    private boolean isVerticalScreen;
    private Bitmap lastBiptmap;
    private boolean mConserve;
    private String mFilename;
    Handler mHandler;
    private WXSDKInstance mInstance;
    int mOrientationState;
    private boolean mRunning;
    private MediaPlayer mediaPlayer;
    private boolean nopermission;
    private boolean playBeep;
    private TextureView surfaceView;
    private boolean vibrate;
    private int viewHeight;
    private int viewWidth;
    private ViewfinderView viewfinderView;

    /* JADX WARN: Removed duplicated region for block: B:12:0x0081  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public BarcodeView(android.content.Context r3, com.taobao.weex.ui.component.WXComponent r4, com.taobao.weex.WXSDKInstance r5) throws java.lang.IllegalStateException, java.io.IOException, java.lang.IllegalArgumentException {
        /*
            r2 = this;
            r2.<init>(r3)
            r0 = 0
            r2.hasSurface = r0
            r1 = 0
            r2.errorMsg = r1
            r2.mConserve = r0
            r2.isSurfaceAvaliable = r0
            r2.isVerticalScreen = r0
            r2.autoDecodeCharset = r0
            io.dcloud.feature.weex_barcode.BarcodeView$1 r1 = new io.dcloud.feature.weex_barcode.BarcodeView$1
            r1.<init>()
            r2.mHandler = r1
            r2.isCancelScan = r0
            r2.mRunning = r0
            io.dcloud.feature.weex_barcode.BarcodeView$5 r1 = new io.dcloud.feature.weex_barcode.BarcodeView$5
            r1.<init>()
            r2.beepListener = r1
            r2.component = r4
            r2.mInstance = r5
            android.view.TextureView r4 = new android.view.TextureView
            r4.<init>(r3)
            r2.surfaceView = r4
            io.dcloud.feature.barcode2.view.ViewfinderView r4 = new io.dcloud.feature.barcode2.view.ViewfinderView
            r4.<init>(r3, r2)
            r2.viewfinderView = r4
            io.dcloud.feature.barcode2.decoding.InactivityTimer r4 = new io.dcloud.feature.barcode2.decoding.InactivityTimer
            r5 = r3
            android.app.Activity r5 = (android.app.Activity) r5
            r4.<init>(r5)
            r2.inactivityTimer = r4
            r2.context = r3
            r2.saveOrientationState()
            android.content.Context r4 = r2.context
            io.dcloud.feature.barcode2.camera.CameraManager.init(r4, r0)
            android.view.WindowManager r4 = r5.getWindowManager()
            android.view.Display r4 = r4.getDefaultDisplay()
            int r4 = r4.getRotation()
            r5 = 1
            if (r4 == 0) goto L81
            if (r4 == r5) goto L72
            r1 = 2
            if (r4 == r1) goto L81
            r3 = 3
            if (r4 == r3) goto L61
            goto L8e
        L61:
            io.dcloud.feature.barcode2.camera.CameraManager r3 = io.dcloud.feature.barcode2.camera.CameraManager.get()
            r3.setHorizontalOrientation(r5)
            android.content.Context r3 = r2.context
            android.app.Activity r3 = (android.app.Activity) r3
            r4 = 8
            r3.setRequestedOrientation(r4)
            goto L8e
        L72:
            io.dcloud.feature.barcode2.camera.CameraManager r3 = io.dcloud.feature.barcode2.camera.CameraManager.get()
            r3.setHorizontalOrientation(r0)
            android.content.Context r3 = r2.context
            android.app.Activity r3 = (android.app.Activity) r3
            r3.setRequestedOrientation(r0)
            goto L8e
        L81:
            android.content.Context r4 = r2.context
            android.app.Activity r4 = (android.app.Activity) r4
            r1 = 7
            r4.setRequestedOrientation(r1)
            io.dcloud.feature.barcode2.camera.CameraManager.init(r3, r5)
            r2.isVerticalScreen = r5
        L8e:
            r2.onResume(r0)
            r2.hasSurface = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.weex_barcode.BarcodeView.<init>(android.content.Context, com.taobao.weex.ui.component.WXComponent, com.taobao.weex.WXSDKInstance):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addBarcodeView() {
        AbsoluteLayout.LayoutParams layoutParams = setLayoutParams();
        if (layoutParams == null) {
            return;
        }
        Message message = new Message();
        message.what = 201;
        message.obj = layoutParams;
        this.mHandler.sendMessage(message);
    }

    private Bitmap byte2bitmap(byte[] bArr, Camera camera) throws IOException {
        Exception exc;
        ByteArrayOutputStream byteArrayOutputStream;
        Bitmap bitmapDecodeByteArray;
        Bitmap bitmap = null;
        try {
            Camera.Size previewSize = camera.getParameters().getPreviewSize();
            YuvImage yuvImage = new YuvImage(bArr, 17, previewSize.width, previewSize.height, null);
            byteArrayOutputStream = new ByteArrayOutputStream();
            yuvImage.compressToJpeg(new Rect(0, 0, previewSize.width, previewSize.height), 80, byteArrayOutputStream);
            bitmapDecodeByteArray = BitmapFactory.decodeByteArray(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size());
        } catch (Exception e) {
            exc = e;
        }
        try {
            byteArrayOutputStream.close();
            Matrix matrix = new Matrix();
            matrix.postRotate(90.0f);
            return Bitmap.createBitmap(bitmapDecodeByteArray, 0, 0, bitmapDecodeByteArray.getWidth(), bitmapDecodeByteArray.getHeight(), matrix, true);
        } catch (Exception e2) {
            exc = e2;
            bitmap = bitmapDecodeByteArray;
            exc.printStackTrace();
            return bitmap;
        }
    }

    private void cancel() {
        if (this.mRunning) {
            CaptureActivityHandler captureActivityHandler = this.handler;
            if (captureActivityHandler != null) {
                captureActivityHandler.stopDecode();
            }
            getViewfinderView().stopUpdateScreenTimer();
            this.mRunning = false;
        }
    }

    private BarcodeFormat convertNumToBarcodeFormat(int i) {
        switch (i) {
            case 0:
                return BarcodeFormat.QR_CODE;
            case 1:
                return BarcodeFormat.EAN_13;
            case 2:
                return BarcodeFormat.EAN_8;
            case 3:
                return BarcodeFormat.AZTEC;
            case 4:
                return BarcodeFormat.DATA_MATRIX;
            case 5:
                return BarcodeFormat.UPC_A;
            case 6:
                return BarcodeFormat.UPC_E;
            case 7:
                return BarcodeFormat.CODABAR;
            case 8:
                return BarcodeFormat.CODE_39;
            case 9:
                return BarcodeFormat.CODE_93;
            case 10:
                return BarcodeFormat.CODE_128;
            case 11:
                return BarcodeFormat.ITF;
            case 12:
                return BarcodeFormat.MAXICODE;
            case 13:
                return BarcodeFormat.PDF_417;
            case 14:
                return BarcodeFormat.RSS_14;
            case 15:
                return BarcodeFormat.RSS_EXPANDED;
            default:
                return null;
        }
    }

    private int convertTypestrToNum(BarcodeFormat barcodeFormat) {
        if (barcodeFormat == BarcodeFormat.QR_CODE) {
            return 0;
        }
        if (barcodeFormat == BarcodeFormat.EAN_13) {
            return 1;
        }
        if (barcodeFormat == BarcodeFormat.EAN_8) {
            return 2;
        }
        if (barcodeFormat == BarcodeFormat.AZTEC) {
            return 3;
        }
        if (barcodeFormat == BarcodeFormat.DATA_MATRIX) {
            return 4;
        }
        if (barcodeFormat == BarcodeFormat.UPC_A) {
            return 5;
        }
        if (barcodeFormat == BarcodeFormat.UPC_E) {
            return 6;
        }
        if (barcodeFormat == BarcodeFormat.CODABAR) {
            return 7;
        }
        if (barcodeFormat == BarcodeFormat.CODE_39) {
            return 8;
        }
        if (barcodeFormat == BarcodeFormat.CODE_93) {
            return 9;
        }
        if (barcodeFormat == BarcodeFormat.CODE_128) {
            return 10;
        }
        if (barcodeFormat == BarcodeFormat.ITF) {
            return 11;
        }
        if (barcodeFormat == BarcodeFormat.MAXICODE) {
            return 12;
        }
        if (barcodeFormat == BarcodeFormat.PDF_417) {
            return 13;
        }
        if (barcodeFormat == BarcodeFormat.RSS_14) {
            return 14;
        }
        return barcodeFormat == BarcodeFormat.RSS_EXPANDED ? 15 : -1000;
    }

    private void fireEvent(String str, Map<String, Object> map) {
        if (this.component.containsEvent(str)) {
            HashMap map2 = new HashMap();
            map2.put("detail", map);
            this.component.fireEvent(str, map2);
        }
    }

    private void initBeepSound() throws IllegalStateException, IOException, IllegalArgumentException {
        if (this.mediaPlayer == null) {
            ((Activity) this.context).setVolumeControlStream(3);
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.mediaPlayer = mediaPlayer;
            mediaPlayer.setAudioStreamType(3);
            this.mediaPlayer.setOnCompletionListener(this.beepListener);
            try {
                AssetFileDescriptor assetFileDescriptorOpenFd = this.context.getResources().getAssets().openFd(AbsoluteConst.RES_BEEP);
                this.mediaPlayer.setDataSource(assetFileDescriptorOpenFd.getFileDescriptor(), assetFileDescriptorOpenFd.getStartOffset(), assetFileDescriptorOpenFd.getLength());
                assetFileDescriptorOpenFd.close();
                this.mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                this.mediaPlayer.prepare();
            } catch (IOException unused) {
                this.mediaPlayer = null;
            }
        }
    }

    private void initCamera() throws RuntimeException {
        try {
            CameraManager.get().openDriver(this.surfaceView.getSurfaceTexture());
            CaptureActivityHandler captureActivityHandler = this.handler;
            if (captureActivityHandler != null) {
                captureActivityHandler.resume();
                return;
            }
            CaptureActivityHandler captureActivityHandler2 = new CaptureActivityHandler(this, this.decodeFormats, this.characterSet, this.autoDecodeCharset);
            this.handler = captureActivityHandler2;
            if (this.mRunning) {
                captureActivityHandler2.restartPreviewAndDecode();
            }
        } catch (IOException e) {
            this.errorMsg = e.getMessage();
        }
    }

    private void playBeepSoundAndVibrate() throws IllegalStateException {
        MediaPlayer mediaPlayer;
        if (this.playBeep && (mediaPlayer = this.mediaPlayer) != null) {
            mediaPlayer.start();
        }
        if (this.vibrate) {
            try {
                ((Vibrator) this.context.getSystemService("vibrator")).vibrate(VIBRATE_DURATION);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void resumeOrientationState() {
        ((Activity) this.context).setRequestedOrientation(this.mOrientationState);
    }

    private void saveOrientationState() {
        this.mOrientationState = ((Activity) this.context).getRequestedOrientation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbsoluteLayout.LayoutParams setLayoutParams() {
        int i;
        int i2;
        int i3;
        CameraManager.sScreenWidth = this.context.getResources().getDisplayMetrics().widthPixels;
        CameraManager.sScreenAllHeight = this.context.getResources().getDisplayMetrics().heightPixels;
        Rect rect = DetectorViewConfig.getInstance().gatherRect;
        int i4 = 0;
        rect.left = 0;
        rect.top = 0;
        rect.right = this.viewWidth;
        rect.bottom = this.viewHeight;
        Point cr = this.isVerticalScreen ? CameraManager.getCR(rect.height(), rect.width()) : CameraManager.getCR(rect.width(), rect.height());
        if (cr == null) {
            cr = new Point(this.viewWidth, this.viewHeight);
        }
        if (this.isVerticalScreen) {
            i = this.viewWidth;
            int i5 = cr.x;
            int i6 = cr.y;
            int i7 = (i * i5) / i6;
            int i8 = this.viewHeight;
            if (i7 < i8) {
                int i9 = (i6 * i8) / i5;
                int i10 = (i - i9) / 2;
                DetectorViewConfig.detectorRectOffestLeft = i10;
                DetectorViewConfig.detectorRectOffestTop = 0;
                i4 = i10;
                i3 = 0;
                i = i9;
                i2 = i8;
            } else {
                int i11 = (i8 - i7) / 2;
                DetectorViewConfig.detectorRectOffestTop = i11;
                DetectorViewConfig.detectorRectOffestLeft = 0;
                i2 = i7;
                i3 = i11;
            }
        } else {
            int i12 = this.viewHeight;
            int i13 = cr.x;
            int i14 = cr.y;
            int i15 = (i12 * i13) / i14;
            int i16 = this.viewWidth;
            if (i15 < i16) {
                i2 = (i14 * i16) / i13;
                i3 = (i12 - i2) / 2;
                DetectorViewConfig.detectorRectOffestTop = i3;
                DetectorViewConfig.detectorRectOffestLeft = 0;
                i = i16;
            } else {
                int i17 = (i16 - i15) / 2;
                DetectorViewConfig.detectorRectOffestLeft = i17;
                DetectorViewConfig.detectorRectOffestTop = 0;
                i = i15;
                i2 = i12;
                i4 = i17;
                i3 = 0;
            }
        }
        AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(i, i2, i4, i3);
        DetectorViewConfig.getInstance().initSurfaceViewRect(i4, i3, i, i2);
        return layoutParams;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startP() throws IOException, RuntimeException {
        initCamera();
        if (this.decodeFormats == null) {
            initDecodeFormats(null);
        }
        if (!TextUtils.isEmpty(this.errorMsg)) {
            HashMap map = new HashMap();
            map.put("code", 8);
            map.put("message", this.errorMsg);
            map.put("type", Constants.Event.FAIL);
            fireEvent("error", map);
            return;
        }
        if (this.mRunning) {
            return;
        }
        getViewfinderView().startUpdateScreenTimer();
        CaptureActivityHandler captureActivityHandler = this.handler;
        if (captureActivityHandler != null) {
            captureActivityHandler.restartPreviewAndDecode();
        } else {
            onResume(false);
        }
        if (this.isCancelScan) {
            this.surfaceView.setBackgroundDrawable(null);
            Bitmap bitmap = this.lastBiptmap;
            if (bitmap != null && !bitmap.isRecycled()) {
                this.lastBiptmap.recycle();
                this.lastBiptmap = null;
            }
            CameraManager.get().clearLastBitmapData();
            this.surfaceView.postInvalidate();
            initCamera();
        }
        this.mRunning = true;
        this.isCancelScan = false;
    }

    @Override // io.dcloud.feature.barcode2.decoding.IBarHandler
    public void autoFocus() {
        this.handler.autoFocus();
    }

    public void cancelScan() throws InterruptedException {
        if (this.mRunning) {
            CaptureActivityHandler captureActivityHandler = this.handler;
            if (captureActivityHandler != null) {
                captureActivityHandler.quitSynchronously();
                this.handler = null;
            }
            getViewfinderView().stopUpdateScreenTimer();
            CameraManager.get().removeAutoFocus();
            CameraManager.get().stopPreview();
            byte[] lastBitmapData = CameraManager.get().getLastBitmapData();
            Camera cameraHandler = CameraManager.get().getCameraHandler();
            if (lastBitmapData != null && cameraHandler != null) {
                this.lastBiptmap = byte2bitmap(lastBitmapData, cameraHandler);
            }
            CameraManager.get().closeDriver();
            this.mRunning = false;
            this.isCancelScan = true;
        }
    }

    public void closeScan() throws InterruptedException {
        onPause();
        CameraManager.get().closeDriver();
        DetectorViewConfig.clearData();
        this.surfaceView = null;
        Bitmap bitmap = this.lastBiptmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.lastBiptmap.recycle();
            this.lastBiptmap = null;
        }
        CameraManager.get().clearLastBitmapData();
        System.gc();
    }

    @Override // io.dcloud.feature.barcode2.decoding.IBarHandler
    public void drawViewfinder() {
        this.viewfinderView.drawViewfinder();
    }

    @Override // android.view.View, io.dcloud.feature.barcode2.decoding.IBarHandler
    public Handler getHandler() {
        return this.handler;
    }

    @Override // io.dcloud.feature.barcode2.decoding.IBarHandler
    public ViewfinderView getViewfinderView() {
        return this.viewfinderView;
    }

    @Override // io.dcloud.feature.barcode2.decoding.IBarHandler
    public void handleDecode(Result result, Bitmap bitmap) throws IllegalStateException, InterruptedException, IOException {
        boolean zSaveBitmapToFile;
        String path;
        this.inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        if (this.mConserve) {
            if (!PdrUtil.isEmpty(this.mFilename) && !PdrUtil.isDeviceRootDir(this.mFilename) && !this.mFilename.startsWith(BaseInfo.REL_PRIVATE_DOC_DIR)) {
                this.mFilename = BaseInfo.REL_PRIVATE_DOC_DIR + this.mFilename;
            }
            path = this.mInstance.rewriteUri(Uri.parse(this.mFilename), "image").getPath();
            zSaveBitmapToFile = PdrUtil.saveBitmapToFile(bitmap, path);
        } else {
            zSaveBitmapToFile = false;
            path = null;
        }
        int iConvertTypestrToNum = convertTypestrToNum(result.getBarcodeFormat());
        Map<String, Object> map = new HashMap<>();
        map.put("code", Integer.valueOf(iConvertTypestrToNum));
        map.put("message", result.getText());
        if (zSaveBitmapToFile && !PdrUtil.isEmpty(path)) {
            map.put("file", path);
        }
        map.put("type", WXImage.SUCCEED);
        map.put("charSet", result.textCharset);
        fireEvent("marked", map);
        cancelScan();
    }

    public void initBarcodeView(int i, int i2) {
        this.viewWidth = i;
        this.viewHeight = i2;
        ThreadPool.self().addThreadTask(new Runnable() { // from class: io.dcloud.feature.weex_barcode.BarcodeView.2
            @Override // java.lang.Runnable
            public void run() {
                BarcodeView.this.addBarcodeView();
            }
        });
    }

    public void initDecodeFormats(JSONArray jSONArray) {
        int iIntValue;
        this.decodeFormats = new Vector<>();
        if (jSONArray == null || jSONArray.size() == 0) {
            this.decodeFormats.add(BarcodeFormat.EAN_13);
            this.decodeFormats.add(BarcodeFormat.EAN_8);
            this.decodeFormats.add(BarcodeFormat.QR_CODE);
            return;
        }
        int size = jSONArray.size();
        for (int i = 0; i < size; i++) {
            try {
                iIntValue = jSONArray.getInteger(i).intValue();
            } catch (JSONException e) {
                e.printStackTrace();
                iIntValue = -1;
            }
            if (iIntValue != -1) {
                this.decodeFormats.add(convertNumToBarcodeFormat(iIntValue));
            }
        }
    }

    @Override // io.dcloud.feature.barcode2.decoding.IBarHandler
    public boolean isRunning() {
        return this.mRunning;
    }

    public void onDestory() {
        resumeOrientationState();
        this.inactivityTimer.shutdown();
        this.hasSurface = false;
        this.decodeFormats = null;
        this.characterSet = null;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) throws Resources.NotFoundException {
        String language;
        super.onDraw(canvas);
        if (this.nopermission) {
            TextPaint textPaint = new TextPaint();
            textPaint.setColor(-1);
            textPaint.setTextSize(PdrUtil.pxFromDp(18.0f, this.context.getResources().getDisplayMetrics()));
            textPaint.setTextAlign(Paint.Align.CENTER);
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            int i = (int) (((this.viewHeight / 2.0f) - (fontMetrics.top / 2.0f)) - (fontMetrics.bottom / 2.0f));
            int i2 = this.viewWidth / 2;
            if (Build.VERSION.SDK_INT >= 24) {
                language = getResources().getConfiguration().getLocales().get(0).getLanguage();
                getResources().getConfiguration().getLocales().get(0).getScript();
            } else {
                language = getResources().getConfiguration().locale.getLanguage();
                getResources().getConfiguration().getLocales().get(0).getScript();
            }
            String string = getResources().getString(R.string.dcloud_scan_no_permission_text);
            if (language.equalsIgnoreCase("ja")) {
                canvas.drawText("カメラ権限がありません", i2, i, textPaint);
            } else if (language.equalsIgnoreCase("ru")) {
                canvas.drawText("Требуется разрешение камеры.", i2, i, textPaint);
            } else {
                canvas.drawText(string, i2, i, textPaint);
            }
        }
    }

    public void onPause() throws InterruptedException {
        CaptureActivityHandler captureActivityHandler = this.handler;
        if (captureActivityHandler != null) {
            captureActivityHandler.quitSynchronously();
            this.handler = null;
        }
        if (!this.nopermission) {
            CameraManager.get().closeDriver();
        }
        boolean z = this.mRunning;
        cancel();
        this.mRunning = z;
    }

    public void onResume(boolean z) throws IllegalStateException, IOException, IllegalArgumentException {
        if (this.lastBiptmap != null && this.isCancelScan && z) {
            this.surfaceView.setBackgroundDrawable(new BitmapDrawable(this.context.getResources(), this.lastBiptmap));
        }
        if (!this.hasSurface) {
            this.surfaceView.setSurfaceTextureListener(this);
        }
        if (((AudioManager) this.context.getSystemService("audio")).getRingerMode() != 2) {
            this.playBeep = false;
        }
        initBeepSound();
        if (z && this.mRunning) {
            this.mRunning = false;
            start();
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        this.isSurfaceAvaliable = true;
        if (this.hasSurface) {
            return;
        }
        this.hasSurface = true;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        this.hasSurface = false;
        return false;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public void setAutoDecodeCharset(boolean z) {
        this.autoDecodeCharset = z;
    }

    public void setAutoZoom(boolean z) {
        CaptureActivityHandler.isAutoZoom = z;
    }

    public void setBackground(int i) {
        if (i == -1) {
            i = DetectorViewConfig.laserColor;
        }
        setBackgroundColor(i);
    }

    public void setConserve(boolean z) {
        this.mConserve = z;
    }

    public void setFilename(String str) {
        this.mFilename = str;
    }

    public void setFlash(boolean z) {
        CameraManager.get().setFlashlight(z);
    }

    public void setFrameColor(int i) {
        if (i == -1) {
            i = DetectorViewConfig.laserColor;
        }
        DetectorViewConfig.cornerColor = i;
    }

    public void setPlayBeep(boolean z) {
        this.playBeep = z;
    }

    public void setScanBarColor(int i) {
        if (i == -1) {
            i = DetectorViewConfig.laserColor;
        }
        DetectorViewConfig.laserColor = i;
    }

    public void setVibrate(boolean z) {
        this.vibrate = z;
    }

    public void start() {
        PermissionUtil.useSystemPermissions((Activity) this.context, new String[]{Permission.CAMERA}, new PermissionUtil.Request() { // from class: io.dcloud.feature.weex_barcode.BarcodeView.4
            @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
            public void onDenied(String str) {
                BarcodeView.this.nopermission = true;
                BarcodeView.this.setBackground(-16777216);
                BarcodeView.this.invalidate();
            }

            @Override // io.dcloud.common.adapter.util.PermissionUtil.Request
            public void onGranted(String str) {
                BarcodeView.this.postDelayed(new Runnable() { // from class: io.dcloud.feature.weex_barcode.BarcodeView.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (!BarcodeView.this.isSurfaceAvaliable) {
                            BarcodeView.this.postDelayed(this, 100L);
                            return;
                        }
                        AbsoluteLayout.LayoutParams layoutParams = BarcodeView.this.setLayoutParams();
                        if (layoutParams == null) {
                            return;
                        }
                        Message message = new Message();
                        message.what = BarcodeView.ID_START_SCAN;
                        message.obj = layoutParams;
                        BarcodeView.this.mHandler.sendMessage(message);
                    }
                }, BarcodeView.this.isSurfaceAvaliable ? 0L : BarcodeView.VIBRATE_DURATION);
            }
        });
    }

    public void updateStyles(int i, int i2) {
        if (this.viewHeight == i2 && this.viewWidth == i) {
            return;
        }
        this.viewWidth = i;
        this.viewHeight = i2;
        ThreadPool.self().addThreadTask(new Runnable() { // from class: io.dcloud.feature.weex_barcode.BarcodeView.3
            @Override // java.lang.Runnable
            public void run() {
                AbsoluteLayout.LayoutParams layoutParams = BarcodeView.this.setLayoutParams();
                if (layoutParams == null) {
                    return;
                }
                Message message = new Message();
                message.what = BarcodeView.ID_UPDATE_VIEW;
                message.obj = layoutParams;
                BarcodeView.this.mHandler.sendMessage(message);
            }
        });
    }
}
