package io.dcloud.feature.uniapp.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXResourceUtils;
import com.taobao.weex.utils.WXUtils;
import com.taobao.weex.utils.WXViewUtils;
import io.dcloud.common.adapter.util.Logger;
import io.dcloud.common.util.StringUtil;
import io.dcloud.feature.uniapp.ui.shadow.UniBoxShadowData;
import io.dcloud.feature.uniapp.ui.shadow.UniBoxShadowOptions;
import io.dcloud.feature.uniapp.ui.shadow.UniInsetBoxShadowDrawable;
import io.dcloud.feature.uniapp.ui.shadow.UniInsetBoxShadowLayer;
import io.dcloud.feature.uniapp.ui.shadow.UniNormalBoxShadowDrawable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class UniBoxShadowUtil {
    private static final String TAG = "UniBoxShadowUtil";
    private static boolean sBoxShadowEnabled = true;
    private static Pattern sColorPattern;

    private static void drawShadow(Canvas canvas, UniBoxShadowOptions uniBoxShadowOptions, float f, float f2, float f3) {
        float f4 = uniBoxShadowOptions.viewWidth;
        float f5 = uniBoxShadowOptions.spread * 2.0f;
        RectF rectF = new RectF(0.0f, 0.0f, f4 + f5, uniBoxShadowOptions.viewHeight + f5);
        float f6 = uniBoxShadowOptions.blur;
        float f7 = uniBoxShadowOptions.hShadow;
        float f8 = f7 >= 0.0f ? (f7 * 2.0f) + f6 : 0.0f;
        float f9 = uniBoxShadowOptions.vShadow;
        rectF.offset(f8, f9 >= 0.0f ? f6 + (f9 * 2.0f) : 0.0f);
        Path path = new Path();
        RectF rectF2 = new RectF(0.0f, 0.0f, uniBoxShadowOptions.viewWidth, uniBoxShadowOptions.viewHeight);
        rectF2.offset(f2 > 0.0f ? (f2 * f3) / 2.0f : 0.0f, f > 0.0f ? (f * f3) / 2.0f : 0.0f);
        rectF2.inset(1.0f, 1.0f);
        path.addRoundRect(rectF2, uniBoxShadowOptions.radii, Path.Direction.CCW);
        canvas.clipPath(path, Region.Op.DIFFERENCE);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(uniBoxShadowOptions.color);
        paint.setStyle(Paint.Style.FILL);
        if (uniBoxShadowOptions.blur > 0.0f) {
            paint.setMaskFilter(new BlurMaskFilter(uniBoxShadowOptions.blur, BlurMaskFilter.Blur.NORMAL));
        }
        float[] fArr = uniBoxShadowOptions.radii;
        int i = 0;
        float f10 = fArr[0];
        if (f10 == fArr[2] && f10 == fArr[4] && f10 == fArr[6]) {
            canvas.drawRoundRect(rectF, f10, f10, paint);
            return;
        }
        Path path2 = new Path();
        float[] fArr2 = new float[8];
        while (true) {
            float[] fArr3 = uniBoxShadowOptions.radii;
            if (i >= fArr3.length) {
                path2.addRoundRect(rectF, fArr2, Path.Direction.CW);
                canvas.drawPath(path2, paint);
                return;
            } else {
                float f11 = fArr3[i];
                if (f11 == 0.0f) {
                    fArr2[i] = 0.0f;
                } else {
                    fArr2[i] = f11 + uniBoxShadowOptions.spread;
                }
                i++;
            }
        }
    }

    public static UniInsetBoxShadowLayer getInsetBoxShadowDrawable(UniBoxShadowData uniBoxShadowData) {
        if (uniBoxShadowData.getInsetShadows().size() <= 0) {
            return null;
        }
        Drawable[] drawableArr = new Drawable[uniBoxShadowData.getInsetShadows().size()];
        for (int i = 0; i < uniBoxShadowData.getInsetShadows().size(); i++) {
            UniBoxShadowOptions uniBoxShadowOptions = uniBoxShadowData.getInsetShadows().get(i);
            drawableArr[i] = new UniInsetBoxShadowDrawable(uniBoxShadowData.getContentWidth(), uniBoxShadowData.getContentHeight(), uniBoxShadowOptions.hShadow, uniBoxShadowOptions.vShadow, uniBoxShadowOptions.blur, uniBoxShadowOptions.spread, uniBoxShadowOptions.color, uniBoxShadowData.getRadii());
        }
        return new UniInsetBoxShadowLayer(drawableArr);
    }

    public static UniNormalBoxShadowDrawable getNormalBoxShadowDrawable(UniBoxShadowData uniBoxShadowData, Resources resources) {
        if (uniBoxShadowData.getNormalShadows().size() <= 0) {
            return null;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(uniBoxShadowData.getCanvasWidth(), uniBoxShadowData.getCanvasHeight(), Bitmap.Config.ARGB_4444);
        Logger.e(TAG, "Allocation memory for box-shadow: " + (bitmapCreateBitmap.getAllocationByteCount() / 1024) + " KB");
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        Iterator<UniBoxShadowOptions> it = uniBoxShadowData.getNormalShadows().iterator();
        while (it.hasNext()) {
            drawShadow(canvas, it.next().scale(uniBoxShadowData.getQuality()), uniBoxShadowData.getNormalTop(), uniBoxShadowData.getNormalLeft(), uniBoxShadowData.getQuality());
        }
        return new UniNormalBoxShadowDrawable(resources, bitmapCreateBitmap);
    }

    public static boolean isBoxShadowEnabled() {
        return sBoxShadowEnabled;
    }

    public static UniBoxShadowData parseBoxShadow(int i, int i2, String str, float[] fArr, float f, float f2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!sBoxShadowEnabled) {
            WXLogUtils.w(TAG, "box-shadow was disabled by config");
            return null;
        }
        UniBoxShadowOptions[] boxShadows = parseBoxShadows(str, f);
        if (boxShadows == null || boxShadows.length == 0) {
            WXLogUtils.w(TAG, "Failed to parse box-shadow: " + str);
            return null;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int i3 = 0;
        for (UniBoxShadowOptions uniBoxShadowOptions : boxShadows) {
            if (uniBoxShadowOptions != null) {
                if (uniBoxShadowOptions.isInset) {
                    arrayList2.add(0, uniBoxShadowOptions);
                } else {
                    arrayList.add(0, uniBoxShadowOptions);
                }
            }
        }
        if (fArr != null && fArr.length != 8) {
            WXLogUtils.w(TAG, "Length of radii must be 8");
        }
        UniBoxShadowData uniBoxShadowData = new UniBoxShadowData(arrayList, arrayList2, fArr, f2);
        uniBoxShadowData.setStyle(str);
        if (arrayList.size() > 0) {
            int size = arrayList.size();
            int iHeight = 0;
            int iWidth = 0;
            while (i3 < size) {
                Object obj = arrayList.get(i3);
                i3++;
                UniBoxShadowOptions uniBoxShadowOptions2 = (UniBoxShadowOptions) obj;
                uniBoxShadowOptions2.viewWidth = i;
                uniBoxShadowOptions2.viewHeight = i2;
                uniBoxShadowOptions2.radii = fArr;
                Rect targetCanvasRect = uniBoxShadowOptions2.getTargetCanvasRect();
                if (iWidth < targetCanvasRect.width()) {
                    iWidth = targetCanvasRect.width();
                }
                if (iHeight < targetCanvasRect.height()) {
                    iHeight = targetCanvasRect.height();
                }
            }
            uniBoxShadowData.setNormalMaxWidth(iWidth);
            uniBoxShadowData.setNormalMaxHeight(iHeight);
            uniBoxShadowData.setNormalLeft(iWidth - i);
            uniBoxShadowData.setNormalTop(iHeight - i2);
        }
        uniBoxShadowData.setContentHeight(i2);
        uniBoxShadowData.setContentWidth(i);
        return uniBoxShadowData;
    }

    public static UniBoxShadowOptions[] parseBoxShadows(String str, float f) {
        int i;
        if (sColorPattern == null) {
            sColorPattern = Pattern.compile("([rR][gG][bB][aA]?)\\((\\d+\\s*),\\s*(\\d+\\s*),\\s*(\\d+\\s*)(?:,\\s*(\\d+(?:\\.\\d+)?))?\\)");
        }
        Matcher matcher = sColorPattern.matcher(str);
        while (true) {
            if (!matcher.find()) {
                break;
            }
            String strGroup = matcher.group();
            str = str.replace(strGroup, "#" + StringUtil.format("%8s", Integer.toHexString(WXResourceUtils.getColor(strGroup, -16777216))).replaceAll("\\s", WXInstanceApm.VALUE_ERROR_CODE_DEFAULT));
        }
        String[] strArrSplit = str.split(",");
        if (strArrSplit == null || strArrSplit.length <= 0) {
            return null;
        }
        UniBoxShadowOptions[] uniBoxShadowOptionsArr = new UniBoxShadowOptions[strArrSplit.length];
        for (i = 0; i < strArrSplit.length; i++) {
            uniBoxShadowOptionsArr[i] = parseBoxShadow(strArrSplit[i], f);
        }
        return uniBoxShadowOptionsArr;
    }

    public static void setBoxShadowEnabled(boolean z) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        sBoxShadowEnabled = z;
        WXLogUtils.w(TAG, "Switch box-shadow status: " + z);
    }

    private static UniBoxShadowOptions parseBoxShadow(String str, float f) {
        UniBoxShadowOptions uniBoxShadowOptions = new UniBoxShadowOptions(f);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String strReplaceAll = str.replaceAll("\\s*,\\s+", ",");
        if (strReplaceAll.contains("inset")) {
            uniBoxShadowOptions.isInset = true;
            strReplaceAll = strReplaceAll.replace("inset", "");
        }
        ArrayList arrayList = new ArrayList(Arrays.asList(strReplaceAll.trim().split("\\s+")));
        String str2 = (String) arrayList.get(arrayList.size() - 1);
        if (!TextUtils.isEmpty(str2) && (str2.startsWith("#") || str2.startsWith("rgb") || WXResourceUtils.isNamedColor(str2))) {
            uniBoxShadowOptions.color = WXResourceUtils.getColor(str2, -16777216);
            arrayList.remove(arrayList.size() - 1);
        }
        try {
            if (arrayList.size() < 2) {
                return null;
            }
            if (!TextUtils.isEmpty((CharSequence) arrayList.get(0))) {
                uniBoxShadowOptions.hShadow = WXViewUtils.getRealSubPxByWidth(WXUtils.getFloat(((String) arrayList.get(0)).trim(), Float.valueOf(0.0f)).floatValue(), f);
            }
            if (!TextUtils.isEmpty((CharSequence) arrayList.get(1))) {
                uniBoxShadowOptions.vShadow = WXViewUtils.getRealPxByWidth(WXUtils.getFloat(((String) arrayList.get(1)).trim(), Float.valueOf(0.0f)).floatValue(), f);
            }
            for (int i = 2; i < arrayList.size(); i++) {
                uniBoxShadowOptions.optionParamParsers.get(i - 2).parse((String) arrayList.get(i));
            }
            return uniBoxShadowOptions;
        } catch (Throwable th) {
            th.printStackTrace();
            return uniBoxShadowOptions;
        }
    }
}
