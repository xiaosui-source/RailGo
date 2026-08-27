package io.dcloud.feature.barcode2.camera;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.feature.barcode2.view.DetectorViewConfig;
import java.util.regex.Pattern;
import kotlinx.coroutines.DebugKt;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
final class CameraConfigurationManager {
    private static final Pattern COMMA_PATTERN = Pattern.compile(",");
    private static final int DESIRED_SHARPNESS = 30;
    private static final String TAG = "CameraConfigurationManager";
    private static final int TEN_DESIRED_ZOOM = 27;
    private Point cameraResolution;
    private final Context context;
    private Camera.Parameters parameters;
    private int previewFormat;
    private String previewFormatString;

    CameraConfigurationManager(Context context) {
        this.context = context;
    }

    private static int findBestMotZoomValue(CharSequence charSequence, int i) throws NumberFormatException {
        int i2 = 0;
        for (String str : COMMA_PATTERN.split(charSequence)) {
            try {
                double d = Double.parseDouble(str.trim());
                int i3 = (int) (10.0d * d);
                if (Math.abs(i - d) < Math.abs(i - i2)) {
                    i2 = i3;
                }
            } catch (NumberFormatException unused) {
                return i;
            }
        }
        return i2;
    }

    private static Point findBestPreviewSizeValue(CharSequence charSequence, Point point) throws NumberFormatException {
        String[] strArrSplit = COMMA_PATTERN.split(charSequence);
        int length = strArrSplit.length;
        int i = Integer.MAX_VALUE;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            String strTrim = strArrSplit[i2].trim();
            int iIndexOf = strTrim.indexOf(120);
            if (iIndexOf < 0) {
                Log.w(TAG, "Bad preview-size: " + strTrim);
            } else {
                try {
                    int i5 = Integer.parseInt(strTrim.substring(0, iIndexOf));
                    int i6 = Integer.parseInt(strTrim.substring(iIndexOf + 1));
                    if (i5 >= i6) {
                        int iAbs = Math.abs(i5 - point.x) + Math.abs(i6 - point.y);
                        if (iAbs == 0) {
                            i4 = i6;
                            i3 = i5;
                            break;
                        }
                        if (iAbs < i) {
                            i4 = i6;
                            i = iAbs;
                            i3 = i5;
                        }
                    } else {
                        continue;
                    }
                } catch (NumberFormatException unused) {
                    Log.w(TAG, "Bad preview-size: " + strTrim);
                }
            }
            i2++;
        }
        if (i3 <= 0 || i4 <= 0) {
            return null;
        }
        return new Point(i3, i4);
    }

    private static Point findBestPreviewSizeValue0(CharSequence charSequence, Point point) throws NumberFormatException {
        String[] strArrSplit = COMMA_PATTERN.split(charSequence);
        int i = 0;
        char c = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i >= strArrSplit.length) {
                break;
            }
            String strTrim = ((c == 0 || c == 1) ? strArrSplit[i] : strArrSplit[(strArrSplit.length - 1) - i]).trim();
            int iIndexOf = strTrim.indexOf(120);
            if (iIndexOf < 0) {
                Log.w(TAG, "Bad preview-size: " + strTrim);
            } else {
                try {
                    int i4 = Integer.parseInt(strTrim.substring(0, iIndexOf));
                    i3 = Integer.parseInt(strTrim.substring(iIndexOf + 1));
                    if (c != 0) {
                        if (i2 > 0 && i4 <= point.y && i3 <= point.x) {
                            i2 = i4;
                            break;
                        }
                    } else if (i2 != 0) {
                        i = -1;
                        c = i4 > i2 ? (char) 65535 : (char) 1;
                        i2 = 0;
                        i3 = 0;
                    }
                    i2 = i4;
                } catch (NumberFormatException unused) {
                    Log.w(TAG, "Bad preview-size: " + strTrim);
                }
            }
            i++;
        }
        if (i2 <= 0 || i3 <= 0) {
            return null;
        }
        return new Point(i2, i3);
    }

    private void setFlash(Camera.Parameters parameters) {
        if (Build.MODEL.contains("Behold II") && CameraManager.SDK_INT == 3) {
            parameters.set("flash-value", 1);
        } else {
            parameters.set("flash-value", 2);
        }
        parameters.set("flash-mode", DebugKt.DEBUG_PROPERTY_VALUE_OFF);
    }

    private void setSharpness(Camera.Parameters parameters) throws NumberFormatException {
        String str = parameters.get("sharpness-max");
        int i = 30;
        if (str != null) {
            try {
                int i2 = Integer.parseInt(str);
                if (30 > i2) {
                    i = i2;
                }
            } catch (NumberFormatException unused) {
                Log.w(TAG, "Bad sharpness-max: " + str);
            }
        }
        parameters.set("sharpness", i);
    }

    private void setZoom(Camera.Parameters parameters) throws NumberFormatException {
        String str = parameters.get("zoom-supported");
        if (str == null || Boolean.parseBoolean(str)) {
            String str2 = parameters.get("max-zoom");
            int iFindBestMotZoomValue = 27;
            if (str2 != null) {
                try {
                    int i = (int) (Double.parseDouble(str2) * 10.0d);
                    if (27 > i) {
                        iFindBestMotZoomValue = i;
                    }
                } catch (NumberFormatException unused) {
                    Log.w(TAG, "Bad max-zoom: " + str2);
                }
            }
            String str3 = parameters.get("taking-picture-zoom-max");
            if (str3 != null) {
                try {
                    int i2 = Integer.parseInt(str3);
                    if (iFindBestMotZoomValue > i2) {
                        iFindBestMotZoomValue = i2;
                    }
                } catch (NumberFormatException unused2) {
                    Log.w(TAG, "Bad taking-picture-zoom-max: " + str3);
                }
            }
            String str4 = parameters.get("mot-zoom-values");
            if (str4 != null) {
                iFindBestMotZoomValue = findBestMotZoomValue(str4, iFindBestMotZoomValue);
            }
            String str5 = parameters.get("mot-zoom-step");
            if (str5 != null) {
                try {
                    int i3 = (int) (Double.parseDouble(str5.trim()) * 10.0d);
                    if (i3 > 1) {
                        iFindBestMotZoomValue -= iFindBestMotZoomValue % i3;
                    }
                } catch (NumberFormatException unused3) {
                }
            }
            if (str2 != null || str4 != null) {
                parameters.set("zoom", String.valueOf(iFindBestMotZoomValue / 10.0d));
            }
            if (str3 != null) {
                parameters.set("taking-picture-zoom", iFindBestMotZoomValue);
            }
        }
    }

    Point getCameraResolution() {
        return this.cameraResolution;
    }

    int getPreviewFormat() {
        return this.previewFormat;
    }

    String getPreviewFormatString() {
        return this.previewFormatString;
    }

    void initFromCameraParameters(Camera camera) {
        if (this.parameters == null) {
            this.parameters = camera.getParameters();
        }
        this.previewFormat = this.parameters.getPreviewFormat();
        this.previewFormatString = this.parameters.get("preview-format");
        Log.d(TAG, "Default preview format: " + this.previewFormat + '/' + this.previewFormatString);
        Rect rect = DetectorViewConfig.getInstance().gatherRect;
        int iWidth = rect.width();
        int iHeight = rect.height();
        if (this.context.getResources().getConfiguration().orientation == 1) {
            this.cameraResolution = getCameraResolution(this.parameters, new Point(iHeight, iWidth));
        } else {
            this.cameraResolution = getCameraResolution(this.parameters, new Point(iWidth, iHeight));
        }
    }

    void setDesiredCameraParameters(Camera camera) {
        if (this.parameters == null) {
            this.parameters = camera.getParameters();
        }
        Log.d(TAG, "Setting preview size: " + this.cameraResolution);
        Camera.Parameters parameters = this.parameters;
        Point point = this.cameraResolution;
        parameters.setPreviewSize(point.x, point.y);
        setFlash(this.parameters);
        setZoom(this.parameters);
        try {
            camera.setParameters(this.parameters);
        } catch (Exception unused) {
        }
    }

    public static Point getCameraResolution(Camera.Parameters parameters, Point point) {
        Point pointFindBestPreviewSizeValue;
        String str = parameters.get("preview-size-values");
        if (str == null) {
            str = parameters.get("preview-size-value");
        }
        if (str != null) {
            Logger.i(TAG, "preview-size-values parameter: " + str);
            pointFindBestPreviewSizeValue = findBestPreviewSizeValue(str, point);
        } else {
            pointFindBestPreviewSizeValue = null;
        }
        return pointFindBestPreviewSizeValue == null ? new Point((point.x >> 3) << 3, (point.y >> 3) << 3) : pointFindBestPreviewSizeValue;
    }
}
